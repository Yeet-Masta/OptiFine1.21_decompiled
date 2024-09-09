package net.optifine.shaders;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.shaders.config.HeaderLine;
import net.optifine.shaders.config.HeaderLineFunction;
import net.optifine.shaders.config.HeaderLineText;
import net.optifine.shaders.config.HeaderLineVariable;
import net.optifine.shaders.config.ShaderPackParser;
import net.optifine.shaders.config.ShaderType;
import net.optifine.util.ArrayUtils;
import net.optifine.util.LineBuffer;
import net.optifine.util.StrUtils;

public class ShadersCompatibility {
   public static Pattern PATTERN_UNIFORM = Pattern.compile("(\\s*layout\\s*\\(.*\\)|)\\s*uniform\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_IN = Pattern.compile("(\\s*layout\\s*\\(.*\\)|)\\s*in\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_OUT = Pattern.compile("(\\s*layout\\s*\\(.*\\)|)\\s*out\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_VARYING = Pattern.compile("\\s*varying\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_CONST = Pattern.compile("\\s*const\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_FUNCTION = Pattern.compile("\\s*\\w+\\s+(\\w+)\\s*\\(.*\\).*", 32);
   public static HeaderLine MODEL_VIEW_MATRIX = makeHeaderLine("uniform mat4 modelViewMatrix;");
   public static HeaderLine MODEL_VIEW_MATRIX_INVERSE = makeHeaderLine("uniform mat4 modelViewMatrixInverse;");
   public static HeaderLine PROJECTION_MATRIX = makeHeaderLine("uniform mat4 projectionMatrix;");
   public static HeaderLine PROJECTION_MATRIX_INVERSE = makeHeaderLine("uniform mat4 projectionMatrixInverse;");
   public static HeaderLine TEXTURE_MATRIX = makeHeaderLine("uniform mat4 textureMatrix = mat4(1.0);");
   public static HeaderLine NORMAL_MATRIX = makeHeaderLine("uniform mat3 normalMatrix;");
   public static HeaderLine CHUNK_OFFSET = makeHeaderLine("uniform vec3 chunkOffset;");
   public static HeaderLine ALPHA_TEST_REF = makeHeaderLine("uniform float alphaTestRef;");
   public static HeaderLine TEXTURE_MATRIX_2 = makeHeaderLine("const mat4 TEXTURE_MATRIX_2 = mat4(vec4(0.00390625, 0.0, 0.0, 0.0), vec4(0.0, 0.00390625, 0.0, 0.0), vec4(0.0, 0.0, 0.00390625, 0.0), vec4(0.03125, 0.03125, 0.03125, 1.0));");
   public static HeaderLine FTRANSORM_BASIC = makeHeaderLine(makeFtransformBasic());
   public static HeaderLine FOG_DENSITY = makeHeaderLine("uniform float fogDensity;");
   public static HeaderLine FOG_START = makeHeaderLine("uniform float fogStart;");
   public static HeaderLine FOG_END = makeHeaderLine("uniform float fogEnd;");
   public static HeaderLine FOG_COLOR = makeHeaderLine("uniform vec3 fogColor;");
   public static HeaderLine VIEW_WIDTH = makeHeaderLine("uniform float viewWidth;");
   public static HeaderLine VIEW_HEIGHT = makeHeaderLine("uniform float viewHeight;");
   public static HeaderLine RENDER_STAGE = makeHeaderLine("uniform int renderStage;");
   public static HeaderLine FOG_FRAG_COORD_OUT = makeHeaderLine("out float varFogFragCoord;");
   public static HeaderLine FOG_FRAG_COORD_IN = makeHeaderLine("in float varFogFragCoord;");
   public static HeaderLine FRONT_COLOR_OUT = makeHeaderLine("out vec4 varFrontColor;");
   public static HeaderLine FRONT_COLOR_IN = makeHeaderLine("in vec4 varFrontColor;");
   public static HeaderLine POSITION = makeHeaderLine("in vec3 vaPosition;");
   public static HeaderLine COLOR = makeHeaderLine("in vec4 vaColor;");
   public static HeaderLine UV0 = makeHeaderLine("in vec2 vaUV0;");
   public static HeaderLine UV1 = makeHeaderLine("in ivec2 vaUV1;");
   public static HeaderLine UV2 = makeHeaderLine("in ivec2 vaUV2;");
   public static HeaderLine NORMAL = makeHeaderLine("in vec3 vaNormal;");
   private static final Pattern PATTERN_VERSION;
   public static final Pattern PATTERN_EXTENSION;
   public static final Pattern PATTERN_LINE;
   private static final Pattern PATTERN_TEXTURE2D_TEXCOORD;
   private static final Pattern PATTERN_FRAG_DATA_SET;
   private static final Pattern PATTERN_FRAG_DATA_GET;
   private static final Pattern PATTERN_FRAG_DATA;
   private static final String COMMENT_COMPATIBILITY = "// Compatibility (auto-generated)";

   public static LineBuffer remap(Program program, ShaderType shaderType, LineBuffer lines) {
      if (program == null) {
         return lines;
      } else {
         int version = 120;
         LineBuffer writer = new LineBuffer();
         Set headerLines = new LinkedHashSet();
         Iterator var6 = lines.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if (line.equals("// Compatibility (auto-generated)")) {
               return lines;
            }

            if (line.trim().startsWith("//")) {
               writer.add(line);
            } else {
               if (matches(line, PATTERN_VERSION)) {
                  version = Math.max(version, getVersion(line, version));
                  line = replace(line, (String)"#version 110", "#version 150", headerLines);
                  line = replace(line, (String)"#version 120", "#version 150", headerLines);
                  line = replace(line, (String)"#version 130", "#version 150", headerLines);
                  line = replace(line, (String)"#version 140", "#version 150", headerLines);
                  line = replace(line, (String)"compatibility", "", headerLines);
               }

               if (shaderType == ShaderType.VERTEX) {
                  if (program == Shaders.ProgramBasic) {
                     line = replace(line, (Pattern)Pattern.compile("(\\W)gl_ProjectionMatrix\\s*\\*\\s*gl_ModelViewMatrix\\s*\\*\\s*gl_Vertex(\\W)"), "$1ftransform()$2", headerLines);
                     line = replace(line, (Pattern)Pattern.compile("(\\W)gl_ModelViewProjectionMatrix\\s*\\*\\s*gl_Vertex(\\W)"), "$1ftransform()$2", headerLines);
                     line = replace(line, (String)"ftransform()", "ftransformBasic()", headerLines, RENDER_STAGE, VIEW_WIDTH, VIEW_HEIGHT, PROJECTION_MATRIX, MODEL_VIEW_MATRIX, POSITION, NORMAL, FTRANSORM_BASIC);
                  }

                  if (program.getProgramStage().isAnyComposite()) {
                     line = replace(line, (String)"ftransform()", "(projectionMatrix * modelViewMatrix * vec4(vaPosition, 1.0))", headerLines, PROJECTION_MATRIX, MODEL_VIEW_MATRIX, POSITION);
                     line = replace(line, (String)"gl_Vertex", "vec4(vaPosition, 1.0)", headerLines, POSITION);
                  } else {
                     line = replace(line, (String)"ftransform()", "(projectionMatrix * modelViewMatrix * vec4(vaPosition + chunkOffset, 1.0))", headerLines, PROJECTION_MATRIX, MODEL_VIEW_MATRIX, POSITION, CHUNK_OFFSET);
                     line = replace(line, (String)"gl_Vertex", "vec4(vaPosition + chunkOffset, 1.0)", headerLines, POSITION, CHUNK_OFFSET);
                  }

                  line = replace(line, (String)"gl_Color", "vaColor", headerLines, COLOR);
                  line = replace(line, (String)"gl_Normal", "vaNormal", headerLines, NORMAL);
                  line = replace(line, (String)"gl_MultiTexCoord0", "vec4(vaUV0, 0.0, 1.0)", headerLines, UV0);
                  line = replace(line, (String)"gl_MultiTexCoord1", "vec4(vaUV1, 0.0, 1.0)", headerLines, UV1);
                  line = replace(line, (String)"gl_MultiTexCoord2", "vec4(vaUV2, 0.0, 1.0)", headerLines, UV2);
                  line = replace(line, (String)"gl_MultiTexCoord3", "vec4(0.0, 0.0, 0.0, 1.0)", headerLines);
               }

               line = replace(line, (String)"gl_ProjectionMatrix", "projectionMatrix", headerLines, PROJECTION_MATRIX);
               line = replace(line, (String)"gl_ProjectionMatrixInverse", "projectionMatrixInverse", headerLines, PROJECTION_MATRIX_INVERSE);
               line = replace(line, (String)"gl_ModelViewMatrix", "modelViewMatrix", headerLines, MODEL_VIEW_MATRIX);
               line = replace(line, (String)"gl_ModelViewMatrixInverse", "modelViewMatrixInverse", headerLines, MODEL_VIEW_MATRIX_INVERSE);
               line = replace(line, (String)"gl_ModelViewProjectionMatrix", "(projectionMatrix * modelViewMatrix)", headerLines, PROJECTION_MATRIX, MODEL_VIEW_MATRIX);
               line = replace(line, (String)"gl_NormalMatrix", "normalMatrix", headerLines, NORMAL_MATRIX);
               if (shaderType == ShaderType.VERTEX) {
                  line = replace(line, (String)"attribute", "in", headerLines);
                  line = replace(line, (String)"varying", "out", headerLines);
                  line = replace(line, (String)"gl_FogFragCoord", "varFogFragCoord", headerLines, FOG_FRAG_COORD_OUT);
                  line = replace(line, (String)"gl_FrontColor", "varFrontColor", headerLines, FRONT_COLOR_OUT);
               }

               if (shaderType == ShaderType.GEOMETRY) {
                  line = replace(line, (String)"varying in", "in", headerLines);
                  line = replace(line, (String)"varying out", "out", headerLines);
               }

               if (shaderType == ShaderType.FRAGMENT) {
                  line = replace(line, (String)"varying", "in", headerLines);
                  line = replace(line, (String)"gl_FogFragCoord", "varFogFragCoord", headerLines, FOG_FRAG_COORD_IN);
                  line = replace(line, (String)"gl_FrontColor", "varFrontColor", headerLines, FRONT_COLOR_IN);
               }

               line = replace(line, (String)"gl_TextureMatrix[0]", "textureMatrix", headerLines, TEXTURE_MATRIX);
               line = replace(line, (String)"gl_TextureMatrix[1]", "mat4(1.0)", headerLines);
               line = replace(line, (String)"gl_TextureMatrix[2]", "TEXTURE_MATRIX_2", headerLines, TEXTURE_MATRIX_2);
               line = replace(line, (String)"gl_Fog.density", "fogDensity", headerLines, FOG_DENSITY);
               line = replace(line, (String)"gl_Fog.start", "fogStart", headerLines, FOG_START);
               line = replace(line, (String)"gl_Fog.end", "fogEnd", headerLines, FOG_END);
               line = replace(line, (String)"gl_Fog.scale", "(1.0 / (fogEnd - fogStart))", headerLines, FOG_START, FOG_END);
               line = replace(line, (String)"gl_Fog.color", "vec4(fogColor, 1.0)", headerLines, FOG_COLOR);
               if (program.getName().contains("entities")) {
                  line = replace(line, (Pattern)PATTERN_TEXTURE2D_TEXCOORD, "$1clamp($4, 0.0, 1.0)$5", headerLines);
               }

               if (shaderType == ShaderType.FRAGMENT) {
                  line = replace(line, (String)"gl_FragColor", "gl_FragData[0]", headerLines);
                  line = addAlphaTest(program, line, headerLines);
               }

               if (line.contains("texture")) {
                  line = replace(line, (Pattern)Pattern.compile("(sampler2D\\s+)texture(\\W)"), "$1gtexture$2", headerLines);
                  line = replace(line, (Pattern)Pattern.compile("(\\(\\s*)texture(\\s*,)"), "$1gtexture$2", headerLines);
               }

               line = replace(line, (String)"texture2D", "texture", headerLines);
               line = replace(line, (String)"texture2DLod", "textureLod", headerLines);
               line = replace(line, (String)"texture2DGrad", "textureGrad", headerLines);
               line = replace(line, (String)"texture2DGradARB", "textureGrad", headerLines);
               line = replace(line, (String)"texture3D", "texture", headerLines);
               line = replace(line, (String)"texture3DLod", "textureLod", headerLines);
               line = replaceShadow2D(line, "shadow2D", "texture", headerLines);
               line = replaceShadow2D(line, "shadow2DLod", "textureLod", headerLines);
               line = replace(line, (String)"texelFetch2D", "texelFetch", headerLines);
               line = replace(line, (String)"texelFetch3D", "texelFetch", headerLines);
               line = replaceFragData(line, headerLines);
               if (version <= 120) {
                  line = replace(line, (String)"common", "commonX", headerLines);
                  line = replace(line, (String)"smooth", "smoothX", headerLines);
               }

               line = replace(line, (String)"gl_ModelViewProjectionMatrixInverse", "gl_ModelViewProjectionMatrixInverse_TODO", headerLines);
               line = replace(line, (String)"gl_TextureMatrixInverse", "gl_TextureMatrixInverse_TODO", headerLines);
               line = replace(line, (String)"gl_ModelViewMatrixTranspose", "gl_ModelViewMatrixTranspose_TODO", headerLines);
               line = replace(line, (String)"gl_ProjectionMatrixTranspose", "gl_ProjectionMatrixTranspose_TODO", headerLines);
               line = replace(line, (String)"gl_ModelViewProjectionMatrixTranspose", "gl_ModelViewProjectionMatrixTranspose_TODO", headerLines);
               line = replace(line, (String)"gl_TextureMatrixTranspose", "gl_TextureMatrixTranspose_TODO", headerLines);
               line = replace(line, (String)"gl_ModelViewMatrixInverseTranspose", "gl_ModelViewMatrixInverseTranspose_TODO", headerLines);
               line = replace(line, (String)"gl_ProjectionMatrixInverseTranspose", "gl_ProjectionMatrixInverseTranspose_TODO", headerLines);
               line = replace(line, (String)"gl_ModelViewProjectionMatrixInverseTranspose", "gl_ModelViewProjectionMatrixInverseTranspose_TODO", headerLines);
               line = replace(line, (String)"gl_TextureMatrixInverseTranspose", "gl_TextureMatrixInverseTranspose_TODO", headerLines);
               if (line.contains("\n")) {
                  String[] parts = Config.tokenize(line, "\n\r");
                  writer.add(parts);
               } else {
                  writer.add(line);
               }
            }
         }

         if (headerLines.isEmpty()) {
            return writer;
         } else {
            writer = removeExisting(writer, headerLines);
            writer = moveExtensionsToHeader(writer, headerLines);
            String[] newHeaderLinesArr = (String[])headerLines.stream().map((x) -> {
               return x.getText();
            }).toArray((x$0) -> {
               return new String[x$0];
            });
            Arrays.sort(newHeaderLinesArr, getComparatorHeaderLines());
            newHeaderLinesArr = (String[])ArrayUtils.addObjectToArray(newHeaderLinesArr, "// Compatibility (auto-generated)", 0);
            int indexInsert = getIndexInsertHeader(writer, version);
            if (indexInsert >= 0) {
               writer.insert(indexInsert, newHeaderLinesArr);
            }

            return writer;
         }
      }
   }

   private static HeaderLine makeHeaderLine(String line) {
      Matcher mu = PATTERN_UNIFORM.matcher(line);
      if (mu.matches()) {
         return new HeaderLineVariable("uniform", mu.group(2), line);
      } else {
         Matcher mi = PATTERN_IN.matcher(line);
         if (mi.matches()) {
            return new HeaderLineVariable("in", mi.group(2), line);
         } else {
            Matcher mo = PATTERN_OUT.matcher(line);
            if (mo.matches()) {
               return new HeaderLineVariable("out", mo.group(2), line);
            } else {
               Matcher mv = PATTERN_VARYING.matcher(line);
               if (mv.matches()) {
                  return new HeaderLineVariable("varying", mv.group(1), line);
               } else {
                  Matcher mc = PATTERN_CONST.matcher(line);
                  if (mc.matches()) {
                     return new HeaderLineVariable("const", mc.group(1), line);
                  } else {
                     Matcher mf = PATTERN_FUNCTION.matcher(line);
                     if (mf.matches()) {
                        return new HeaderLineFunction(mf.group(1), line);
                     } else {
                        throw new IllegalArgumentException("Unknown header line: " + line);
                     }
                  }
               }
            }
         }
      }
   }

   private static String makeFtransformBasic() {
      StringBuilder buf = new StringBuilder();
      addLine(buf, "vec4 ftransformBasic()                                                                                           ");
      addLine(buf, "{                                                                                                                ");
      addLine(buf, "  if(renderStage != MC_RENDER_STAGE_OUTLINE)   // Render stage outline                                           ");
      addLine(buf, "    return projectionMatrix * modelViewMatrix * vec4(vaPosition, 1.0);                                           ");
      addLine(buf, "  float lineWidth = 2.5;                                                                                         ");
      addLine(buf, "  vec2 screenSize = vec2(viewWidth, viewHeight);                                                                 ");
      addLine(buf, "  const mat4 VIEW_SCALE = mat4(mat3(1.0 - (1.0 / 256.0)));                                                       ");
      addLine(buf, "  vec4 linePosStart = projectionMatrix * VIEW_SCALE * modelViewMatrix * vec4(vaPosition, 1.0);                   ");
      addLine(buf, "  vec4 linePosEnd = projectionMatrix * VIEW_SCALE * modelViewMatrix * (vec4(vaPosition + vaNormal, 1.0));        ");
      addLine(buf, "  vec3 ndc1 = linePosStart.xyz / linePosStart.w;                                                                 ");
      addLine(buf, "  vec3 ndc2 = linePosEnd.xyz / linePosEnd.w;                                                                     ");
      addLine(buf, "  vec2 lineScreenDirection = normalize((ndc2.xy - ndc1.xy) * screenSize);                                        ");
      addLine(buf, "  vec2 lineOffset = vec2(-lineScreenDirection.y, lineScreenDirection.x) * lineWidth / screenSize;                ");
      addLine(buf, "  if (lineOffset.x < 0.0)                                                                                        ");
      addLine(buf, "    lineOffset *= -1.0;                                                                                          ");
      addLine(buf, "  if (gl_VertexID % 2 == 0)                                                                                      ");
      addLine(buf, "    return vec4((ndc1 + vec3(lineOffset, 0.0)) * linePosStart.w, linePosStart.w);                                ");
      addLine(buf, "  else                                                                                                           ");
      addLine(buf, "    return vec4((ndc1 - vec3(lineOffset, 0.0)) * linePosStart.w, linePosStart.w);                                ");
      addLine(buf, "}                                                                                                                ");
      String src = buf.toString();
      src = src.replace("MC_RENDER_STAGE_OUTLINE", "" + RenderStage.OUTLINE.ordinal());
      return src;
   }

   private static void addLine(StringBuilder buf, String line) {
      buf.append(StrUtils.trimTrailing(line, " \t") + "\n");
   }

   private static LineBuffer removeExisting(LineBuffer lines, Set headerLines) {
      if (headerLines.isEmpty()) {
         return lines;
      } else {
         LineBuffer linesNew = new LineBuffer(lines.getLines());
         Iterator var3 = headerLines.iterator();

         while(var3.hasNext()) {
            HeaderLine headerLine = (HeaderLine)var3.next();

            for(int i = 0; i < linesNew.size(); ++i) {
               String lineNew = linesNew.get(i);
               if (headerLine.matches(lineNew)) {
                  String lineNew2 = headerLine.removeFrom(lineNew);
                  if (lineNew2 == null) {
                     lineNew2 = "// Moved up";
                  }

                  linesNew.set(i, lineNew2);
               }
            }
         }

         return linesNew;
      }
   }

   private static LineBuffer moveExtensionsToHeader(LineBuffer lines, Set headerLines) {
      LineBuffer linesNew = new LineBuffer(lines.getLines());

      for(int i = 0; i < lines.size(); ++i) {
         String line = lines.get(i);
         if (PATTERN_EXTENSION.matcher(line).matches()) {
            String lineHeader = line.trim();
            lineHeader = replaceWord(lineHeader, "require", "enable");
            HeaderLine hl = new HeaderLineText(lineHeader);
            headerLines.add(hl);
            line = "//" + line;
         }

         linesNew.set(i, line);
      }

      return linesNew;
   }

   private static int getVersion(String line, int def) {
      Matcher m = PATTERN_VERSION.matcher(line);
      if (!m.matches()) {
         return def;
      } else {
         String verStr = m.group(1);
         int ver = Config.parseInt(verStr, -1);
         return ver < def ? def : ver;
      }
   }

   private static int getIndexInsertHeader(LineBuffer lines, int version) {
      int indexVersion = lines.indexMatch(PATTERN_VERSION);
      int indexLine = lines.indexMatch(PATTERN_LINE, indexVersion);
      if (indexLine < 0) {
         Config.warn("Header insert line not found");
      }

      return indexLine;
   }

   private static String addAlphaTest(Program program, String line, Set headerLines) {
      if (program.getProgramStage().isAnyComposite()) {
         return line;
      } else {
         Matcher m = PATTERN_FRAG_DATA_SET.matcher(line);
         if (m.matches()) {
            String index = m.group(2);
            if (!index.equals("0")) {
               return line;
            }

            HeaderLine hl = new HeaderLineText("vec4 temp_FragData" + index + ";");
            headerLines.add(hl);
            headerLines.add(ALPHA_TEST_REF);
            String line2 = m.replaceAll("$1{\n$1  temp_FragData$2$3 = $4\n$1  if(temp_FragData$2.a < alphaTestRef) discard;\n$1  gl_FragData[$2] = temp_FragData$2;\n$1}");
            line = line2;
         }

         Matcher m2 = PATTERN_FRAG_DATA_GET.matcher(line);
         if (m2.find()) {
            String index = m2.group(1);
            if (!index.equals("0")) {
               return line;
            }

            HeaderLine hl = new HeaderLineText("vec4 temp_FragData" + index + ";");
            headerLines.add(hl);
            String line2 = m2.replaceAll("temp_FragData$1$2");
            line = line2;
         }

         return line;
      }
   }

   private static String replaceShadow2D(String line, String name, String nameNew, Set headerLines) {
      if (line.indexOf(name) < 0) {
         return line;
      } else {
         String line2 = line.replaceAll(name + "\\((([^()]*+|\\(([^()]*+|\\([^()]*+\\))*\\))*)\\)\\.[xyzrgb]{3}", "vec3(" + nameNew + "($1))");
         line2 = line2.replaceAll(name + "\\((([^()]*+|\\(([^()]*+|\\([^()]*+\\))*\\))*)\\)\\.[xyzrgb]", nameNew + "($1)");
         line2 = line2.replaceAll(name + "\\((([^()]*+|\\(([^()]*+|\\([^()]*+\\))*\\))*)\\)([^.])", "vec4(vec3(" + nameNew + "($1)), 1.0)$4");
         return line2;
      }
   }

   private static String replaceFragData(String line, Set headerLines) {
      Matcher m = PATTERN_FRAG_DATA.matcher(line);
      if (m.find()) {
         String line2 = m.replaceAll("outColor$1");

         for(int i = 0; i < 8; ++i) {
            if (line2.contains("outColor" + i)) {
               headerLines.add(new HeaderLineText("out vec4 outColor" + i + ";"));
            }
         }

         return line2;
      } else {
         return line;
      }
   }

   private static Comparator getComparatorHeaderLines() {
      Comparator comp = new Comparator() {
         private static final int UNKNOWN = Integer.MAX_VALUE;

         public int compare(String o1, String o2) {
            int i1;
            int i2;
            if (o1.startsWith("in ") && o2.startsWith("in ")) {
               i1 = this.getAttributeIndex(o1);
               i2 = this.getAttributeIndex(o2);
               if (i1 != Integer.MAX_VALUE || i2 != Integer.MAX_VALUE) {
                  return i1 - i2;
               }
            }

            if (o1.startsWith("uniform ") && o2.startsWith("uniform ")) {
               i1 = this.getUniformIndex(o1);
               i2 = this.getUniformIndex(o2);
               if (i1 != Integer.MAX_VALUE || i2 != Integer.MAX_VALUE) {
                  return i1 - i2;
               }
            }

            return o1.compareTo(o2);
         }

         private int getAttributeIndex(String line) {
            if (line.equals(ShadersCompatibility.POSITION.getText())) {
               return 0;
            } else if (line.equals(ShadersCompatibility.COLOR.getText())) {
               return 1;
            } else if (line.equals(ShadersCompatibility.UV0.getText())) {
               return 2;
            } else if (line.equals(ShadersCompatibility.UV1.getText())) {
               return 3;
            } else if (line.equals(ShadersCompatibility.UV2.getText())) {
               return 4;
            } else {
               return line.equals(ShadersCompatibility.NORMAL.getText()) ? 5 : Integer.MAX_VALUE;
            }
         }

         private int getUniformIndex(String line) {
            if (line.equals(ShadersCompatibility.MODEL_VIEW_MATRIX.getText())) {
               return 0;
            } else if (line.equals(ShadersCompatibility.MODEL_VIEW_MATRIX_INVERSE.getText())) {
               return 1;
            } else if (line.equals(ShadersCompatibility.PROJECTION_MATRIX.getText())) {
               return 2;
            } else if (line.equals(ShadersCompatibility.PROJECTION_MATRIX_INVERSE.getText())) {
               return 3;
            } else if (line.equals(ShadersCompatibility.TEXTURE_MATRIX.getText())) {
               return 4;
            } else if (line.equals(ShadersCompatibility.NORMAL_MATRIX.getText())) {
               return 5;
            } else if (line.equals(ShadersCompatibility.CHUNK_OFFSET.getText())) {
               return 6;
            } else {
               return line.equals(ShadersCompatibility.ALPHA_TEST_REF.getText()) ? 7 : Integer.MAX_VALUE;
            }
         }
      };
      return comp;
   }

   private static String replace(String line, String find, String replace, Set newLines, HeaderLine... headerLines) {
      String line2 = replaceWord(line, find, replace);
      if (!line2.equals(line) && headerLines.length > 0) {
         newLines.addAll(Arrays.asList(headerLines));
      }

      return line2;
   }

   private static String replaceWord(String line, String find, String replace) {
      String line2 = line;
      int pos = line.length();

      while(true) {
         int posEnd;
         char charNext;
         do {
            do {
               do {
                  if (pos <= 0) {
                     return line2;
                  }

                  pos = line2.lastIndexOf(find, pos - 1);
               } while(pos < 0);

               posEnd = pos + find.length();
               if (pos - 1 < 0) {
                  break;
               }

               charNext = line2.charAt(pos - 1);
            } while(Character.isLetter(charNext) || Character.isDigit(charNext) || charNext == '_');

            if (posEnd >= line2.length()) {
               break;
            }

            charNext = line2.charAt(posEnd);
         } while(Character.isLetter(charNext) || Character.isDigit(charNext) || charNext == '_');

         line2 = line2.substring(0, pos) + replace + line2.substring(posEnd);
      }
   }

   private static String replace(String line, Pattern pattern, String replace, Set newLines, HeaderLine... headerLines) {
      Matcher m = pattern.matcher(line);
      if (!m.find()) {
         return line;
      } else {
         String line2 = m.replaceAll(replace);
         if (!line2.equals(line) && headerLines.length > 0) {
            newLines.addAll(Arrays.asList(headerLines));
         }

         return line2;
      }
   }

   private static boolean matches(String line, Pattern pattern) {
      Matcher m = pattern.matcher(line);
      return m.matches();
   }

   static {
      PATTERN_VERSION = ShaderPackParser.PATTERN_VERSION;
      PATTERN_EXTENSION = Pattern.compile("\\s*#\\s*extension\\s+(\\w+)(.*)");
      PATTERN_LINE = Pattern.compile("\\s*#\\s*line\\s+(\\d+)\\s+(\\d+)(.*)");
      PATTERN_TEXTURE2D_TEXCOORD = Pattern.compile("(.*texture(2D)?\\s*\\(\\s*(texture|colortex0)\\s*,\\s*)(\\w+)(\\s*\\).*)");
      PATTERN_FRAG_DATA_SET = Pattern.compile("(\\s*)gl_FragData\\[(\\d+)\\](\\S*)\\s*=\\s*(.*)");
      PATTERN_FRAG_DATA_GET = Pattern.compile("gl_FragData\\[(\\d+)\\]([^ ][^=])");
      PATTERN_FRAG_DATA = Pattern.compile("gl_FragData\\[(\\d+)\\]");
   }
}
