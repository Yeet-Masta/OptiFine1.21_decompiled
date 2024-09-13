package net.optifine.shaders;

import java.util.Arrays;
import java.util.Comparator;
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
   public static Pattern PATTERN_UNIFORM = Pattern.m_289905_("(\\s*layout\\s*\\(.*\\)|)\\s*uniform\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_IN = Pattern.m_289905_("(\\s*layout\\s*\\(.*\\)|)\\s*in\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_OUT = Pattern.m_289905_("(\\s*layout\\s*\\(.*\\)|)\\s*out\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_VARYING = Pattern.m_289905_("\\s*varying\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_CONST = Pattern.m_289905_("\\s*const\\s+\\w+\\s+(\\w+).*");
   public static Pattern PATTERN_FUNCTION = Pattern.m_289905_("\\s*\\w+\\s+(\\w+)\\s*\\(.*\\).*", 32);
   public static HeaderLine MODEL_VIEW_MATRIX = makeHeaderLine("uniform mat4 modelViewMatrix;");
   public static HeaderLine MODEL_VIEW_MATRIX_INVERSE = makeHeaderLine("uniform mat4 modelViewMatrixInverse;");
   public static HeaderLine PROJECTION_MATRIX = makeHeaderLine("uniform mat4 projectionMatrix;");
   public static HeaderLine PROJECTION_MATRIX_INVERSE = makeHeaderLine("uniform mat4 projectionMatrixInverse;");
   public static HeaderLine TEXTURE_MATRIX = makeHeaderLine("uniform mat4 textureMatrix = mat4(1.0);");
   public static HeaderLine NORMAL_MATRIX = makeHeaderLine("uniform mat3 normalMatrix;");
   public static HeaderLine CHUNK_OFFSET = makeHeaderLine("uniform vec3 chunkOffset;");
   public static HeaderLine ALPHA_TEST_REF = makeHeaderLine("uniform float alphaTestRef;");
   public static HeaderLine TEXTURE_MATRIX_2 = makeHeaderLine(
      "const mat4 TEXTURE_MATRIX_2 = mat4(vec4(0.00390625, 0.0, 0.0, 0.0), vec4(0.0, 0.00390625, 0.0, 0.0), vec4(0.0, 0.0, 0.00390625, 0.0), vec4(0.03125, 0.03125, 0.03125, 1.0));"
   );
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
   private static Pattern PATTERN_VERSION = ShaderPackParser.PATTERN_VERSION;
   public static Pattern PATTERN_EXTENSION = Pattern.m_289905_("\\s*#\\s*extension\\s+(\\w+)(.*)");
   public static Pattern PATTERN_LINE = Pattern.m_289905_("\\s*#\\s*line\\s+(\\d+)\\s+(\\d+)(.*)");
   private static Pattern PATTERN_TEXTURE2D_TEXCOORD = Pattern.m_289905_("(.*texture(2D)?\\s*\\(\\s*(texture|colortex0)\\s*,\\s*)(\\w+)(\\s*\\).*)");
   private static Pattern PATTERN_FRAG_DATA_SET = Pattern.m_289905_("(\\s*)gl_FragData\\[(\\d+)\\](\\S*)\\s*=\\s*(.*)");
   private static Pattern PATTERN_FRAG_DATA_GET = Pattern.m_289905_("gl_FragData\\[(\\d+)\\]([^ ][^=])");
   private static Pattern PATTERN_FRAG_DATA = Pattern.m_289905_("gl_FragData\\[(\\d+)\\]");
   private static String COMMENT_COMPATIBILITY;

   public static LineBuffer remap(Program program, ShaderType shaderType, LineBuffer lines) {
      if (program == null) {
         return lines;
      } else {
         int version = 120;
         LineBuffer writer = new LineBuffer();
         Set<HeaderLine> headerLines = new LinkedHashSet();

         for (String line : lines) {
            if (line.equals("// Compatibility (auto-generated)")) {
               return lines;
            }

            if (line.trim().startsWith("//")) {
               writer.add(line);
            } else {
               if (matches(line, PATTERN_VERSION)) {
                  version = Math.max(version, getVersion(line, version));
                  line = replace(line, "#version 110", "#version 150", headerLines);
                  line = replace(line, "#version 120", "#version 150", headerLines);
                  line = replace(line, "#version 130", "#version 150", headerLines);
                  line = replace(line, "#version 140", "#version 150", headerLines);
                  line = replace(line, "compatibility", "", headerLines);
               }

               if (shaderType == ShaderType.VERTEX) {
                  if (program == Shaders.ProgramBasic) {
                     line = replace(
                        line,
                        Pattern.m_289905_("(\\W)gl_ProjectionMatrix\\s*\\*\\s*gl_ModelViewMatrix\\s*\\*\\s*gl_Vertex(\\W)"),
                        "$1ftransform()$2",
                        headerLines
                     );
                     line = replace(line, Pattern.m_289905_("(\\W)gl_ModelViewProjectionMatrix\\s*\\*\\s*gl_Vertex(\\W)"), "$1ftransform()$2", headerLines);
                     line = replace(
                        line,
                        "ftransform()",
                        "ftransformBasic()",
                        headerLines,
                        RENDER_STAGE,
                        VIEW_WIDTH,
                        VIEW_HEIGHT,
                        PROJECTION_MATRIX,
                        MODEL_VIEW_MATRIX,
                        POSITION,
                        NORMAL,
                        FTRANSORM_BASIC
                     );
                  }

                  if (program.getProgramStage().isAnyComposite()) {
                     line = replace(
                        line,
                        "ftransform()",
                        "(projectionMatrix * modelViewMatrix * vec4(vaPosition, 1.0))",
                        headerLines,
                        PROJECTION_MATRIX,
                        MODEL_VIEW_MATRIX,
                        POSITION
                     );
                     line = replace(line, "gl_Vertex", "vec4(vaPosition, 1.0)", headerLines, POSITION);
                  } else {
                     line = replace(
                        line,
                        "ftransform()",
                        "(projectionMatrix * modelViewMatrix * vec4(vaPosition + chunkOffset, 1.0))",
                        headerLines,
                        PROJECTION_MATRIX,
                        MODEL_VIEW_MATRIX,
                        POSITION,
                        CHUNK_OFFSET
                     );
                     line = replace(line, "gl_Vertex", "vec4(vaPosition + chunkOffset, 1.0)", headerLines, POSITION, CHUNK_OFFSET);
                  }

                  line = replace(line, "gl_Color", "vaColor", headerLines, COLOR);
                  line = replace(line, "gl_Normal", "vaNormal", headerLines, NORMAL);
                  line = replace(line, "gl_MultiTexCoord0", "vec4(vaUV0, 0.0, 1.0)", headerLines, UV0);
                  line = replace(line, "gl_MultiTexCoord1", "vec4(vaUV1, 0.0, 1.0)", headerLines, UV1);
                  line = replace(line, "gl_MultiTexCoord2", "vec4(vaUV2, 0.0, 1.0)", headerLines, UV2);
                  line = replace(line, "gl_MultiTexCoord3", "vec4(0.0, 0.0, 0.0, 1.0)", headerLines);
               }

               line = replace(line, "gl_ProjectionMatrix", "projectionMatrix", headerLines, PROJECTION_MATRIX);
               line = replace(line, "gl_ProjectionMatrixInverse", "projectionMatrixInverse", headerLines, PROJECTION_MATRIX_INVERSE);
               line = replace(line, "gl_ModelViewMatrix", "modelViewMatrix", headerLines, MODEL_VIEW_MATRIX);
               line = replace(line, "gl_ModelViewMatrixInverse", "modelViewMatrixInverse", headerLines, MODEL_VIEW_MATRIX_INVERSE);
               line = replace(line, "gl_ModelViewProjectionMatrix", "(projectionMatrix * modelViewMatrix)", headerLines, PROJECTION_MATRIX, MODEL_VIEW_MATRIX);
               line = replace(line, "gl_NormalMatrix", "normalMatrix", headerLines, NORMAL_MATRIX);
               if (shaderType == ShaderType.VERTEX) {
                  line = replace(line, "attribute", "in", headerLines);
                  line = replace(line, "varying", "out", headerLines);
                  line = replace(line, "gl_FogFragCoord", "varFogFragCoord", headerLines, FOG_FRAG_COORD_OUT);
                  line = replace(line, "gl_FrontColor", "varFrontColor", headerLines, FRONT_COLOR_OUT);
               }

               if (shaderType == ShaderType.GEOMETRY) {
                  line = replace(line, "varying in", "in", headerLines);
                  line = replace(line, "varying out", "out", headerLines);
               }

               if (shaderType == ShaderType.FRAGMENT) {
                  line = replace(line, "varying", "in", headerLines);
                  line = replace(line, "gl_FogFragCoord", "varFogFragCoord", headerLines, FOG_FRAG_COORD_IN);
                  line = replace(line, "gl_FrontColor", "varFrontColor", headerLines, FRONT_COLOR_IN);
               }

               line = replace(line, "gl_TextureMatrix[0]", "textureMatrix", headerLines, TEXTURE_MATRIX);
               line = replace(line, "gl_TextureMatrix[1]", "mat4(1.0)", headerLines);
               line = replace(line, "gl_TextureMatrix[2]", "TEXTURE_MATRIX_2", headerLines, TEXTURE_MATRIX_2);
               line = replace(line, "gl_Fog.density", "fogDensity", headerLines, FOG_DENSITY);
               line = replace(line, "gl_Fog.start", "fogStart", headerLines, FOG_START);
               line = replace(line, "gl_Fog.end", "fogEnd", headerLines, FOG_END);
               line = replace(line, "gl_Fog.scale", "(1.0 / (fogEnd - fogStart))", headerLines, FOG_START, FOG_END);
               line = replace(line, "gl_Fog.color", "vec4(fogColor, 1.0)", headerLines, FOG_COLOR);
               if (program.getName().m_274455_("entities")) {
                  line = replace(line, PATTERN_TEXTURE2D_TEXCOORD, "$1clamp($4, 0.0, 1.0)$5", headerLines);
               }

               if (shaderType == ShaderType.FRAGMENT) {
                  line = replace(line, "gl_FragColor", "gl_FragData[0]", headerLines);
                  line = addAlphaTest(program, line, headerLines);
               }

               if (line.m_274455_("texture")) {
                  line = replace(line, Pattern.m_289905_("(sampler2D\\s+)texture(\\W)"), "$1gtexture$2", headerLines);
                  line = replace(line, Pattern.m_289905_("(\\(\\s*)texture(\\s*,)"), "$1gtexture$2", headerLines);
               }

               line = replace(line, "texture2D", "texture", headerLines);
               line = replace(line, "texture2DLod", "textureLod", headerLines);
               line = replace(line, "texture2DGrad", "textureGrad", headerLines);
               line = replace(line, "texture2DGradARB", "textureGrad", headerLines);
               line = replace(line, "texture3D", "texture", headerLines);
               line = replace(line, "texture3DLod", "textureLod", headerLines);
               line = replaceShadow2D(line, "shadow2D", "texture", headerLines);
               line = replaceShadow2D(line, "shadow2DLod", "textureLod", headerLines);
               line = replace(line, "texelFetch2D", "texelFetch", headerLines);
               line = replace(line, "texelFetch3D", "texelFetch", headerLines);
               line = replaceFragData(line, headerLines);
               if (version <= 120) {
                  line = replace(line, "common", "commonX", headerLines);
                  line = replace(line, "smooth", "smoothX", headerLines);
               }

               line = replace(line, "gl_ModelViewProjectionMatrixInverse", "gl_ModelViewProjectionMatrixInverse_TODO", headerLines);
               line = replace(line, "gl_TextureMatrixInverse", "gl_TextureMatrixInverse_TODO", headerLines);
               line = replace(line, "gl_ModelViewMatrixTranspose", "gl_ModelViewMatrixTranspose_TODO", headerLines);
               line = replace(line, "gl_ProjectionMatrixTranspose", "gl_ProjectionMatrixTranspose_TODO", headerLines);
               line = replace(line, "gl_ModelViewProjectionMatrixTranspose", "gl_ModelViewProjectionMatrixTranspose_TODO", headerLines);
               line = replace(line, "gl_TextureMatrixTranspose", "gl_TextureMatrixTranspose_TODO", headerLines);
               line = replace(line, "gl_ModelViewMatrixInverseTranspose", "gl_ModelViewMatrixInverseTranspose_TODO", headerLines);
               line = replace(line, "gl_ProjectionMatrixInverseTranspose", "gl_ProjectionMatrixInverseTranspose_TODO", headerLines);
               line = replace(line, "gl_ModelViewProjectionMatrixInverseTranspose", "gl_ModelViewProjectionMatrixInverseTranspose_TODO", headerLines);
               line = replace(line, "gl_TextureMatrixInverseTranspose", "gl_TextureMatrixInverseTranspose_TODO", headerLines);
               if (line.m_274455_("\n")) {
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
            String[] newHeaderLinesArr = (String[])headerLines.stream().map(x -> x.getText()).toArray(String[]::new);
            Arrays.m_277065_(newHeaderLinesArr, getComparatorHeaderLines());
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
      return src.replace("MC_RENDER_STAGE_OUTLINE", RenderStage.OUTLINE.ordinal() + "");
   }

   private static void addLine(StringBuilder buf, String line) {
      buf.append(StrUtils.trimTrailing(line, " \t") + "\n");
   }

   private static LineBuffer removeExisting(LineBuffer lines, Set<HeaderLine> headerLines) {
      if (headerLines.isEmpty()) {
         return lines;
      } else {
         LineBuffer linesNew = new LineBuffer(lines.getLines());

         for (HeaderLine headerLine : headerLines) {
            for (int i = 0; i < linesNew.size(); i++) {
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

   private static LineBuffer moveExtensionsToHeader(LineBuffer lines, Set<HeaderLine> headerLines) {
      LineBuffer linesNew = new LineBuffer(lines.getLines());

      for (int i = 0; i < lines.size(); i++) {
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

   private static String addAlphaTest(Program program, String line, Set<HeaderLine> headerLines) {
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
            String line2 = m.replaceAll(
               "$1{\n$1  temp_FragData$2$3 = $4\n$1  if(temp_FragData$2.a < alphaTestRef) discard;\n$1  gl_FragData[$2] = temp_FragData$2;\n$1}"
            );
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

   private static String replaceShadow2D(String line, String name, String nameNew, Set<HeaderLine> headerLines) {
      if (line.indexOf(name) < 0) {
         return line;
      } else {
         String line2 = line.replaceAll(name + "\\((([^()]*+|\\(([^()]*+|\\([^()]*+\\))*\\))*)\\)\\.[xyzrgb]{3}", "vec3(" + nameNew + "($1))");
         line2 = line2.replaceAll(name + "\\((([^()]*+|\\(([^()]*+|\\([^()]*+\\))*\\))*)\\)\\.[xyzrgb]", nameNew + "($1)");
         return line2.replaceAll(name + "\\((([^()]*+|\\(([^()]*+|\\([^()]*+\\))*\\))*)\\)([^.])", "vec4(vec3(" + nameNew + "($1)), 1.0)$4");
      }
   }

   private static String replaceFragData(String line, Set<HeaderLine> headerLines) {
      Matcher m = PATTERN_FRAG_DATA.matcher(line);
      if (m.find()) {
         String line2 = m.replaceAll("outColor$1");

         for (int i = 0; i < 8; i++) {
            if (line2.m_274455_("outColor" + i)) {
               headerLines.add(new HeaderLineText("out vec4 outColor" + i + ";"));
            }
         }

         return line2;
      } else {
         return line;
      }
   }

   private static Comparator<String> getComparatorHeaderLines() {
      return new Comparator<String>() {
         private static int UNKNOWN;

         public int compare(String o1, String o2) {
            if (o1.startsWith("in ") && o2.startsWith("in ")) {
               int i1 = this.getAttributeIndex(o1);
               int i2 = this.getAttributeIndex(o2);
               if (i1 != Integer.MAX_VALUE || i2 != Integer.MAX_VALUE) {
                  return i1 - i2;
               }
            }

            if (o1.startsWith("uniform ") && o2.startsWith("uniform ")) {
               int i1 = this.getUniformIndex(o1);
               int i2 = this.getUniformIndex(o2);
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
   }

   private static String replace(String line, String find, String replace, Set<HeaderLine> newLines, HeaderLine... headerLines) {
      String line2 = replaceWord(line, find, replace);
      if (!line2.equals(line) && headerLines.length > 0) {
         newLines.addAll(Arrays.asList(headerLines));
      }

      return line2;
   }

   private static String replaceWord(String line, String find, String replace) {
      String line2 = line;
      int pos = line.length();

      while (pos > 0) {
         pos = line2.lastIndexOf(find, pos - 1);
         if (pos >= 0) {
            int posEnd = pos + find.length();
            if (pos - 1 >= 0) {
               char charPrev = line2.charAt(pos - 1);
               if (Character.isLetter(charPrev) || Character.isDigit(charPrev) || charPrev == '_') {
                  continue;
               }
            }

            if (posEnd < line2.length()) {
               char charNext = line2.charAt(posEnd);
               if (Character.isLetter(charNext) || Character.isDigit(charNext) || charNext == '_') {
                  continue;
               }
            }

            line2 = line2.substring(0, pos) + replace + line2.substring(posEnd);
         }
      }

      return line2;
   }

   private static String replace(String line, Pattern pattern, String replace, Set<HeaderLine> newLines, HeaderLine... headerLines) {
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
}
