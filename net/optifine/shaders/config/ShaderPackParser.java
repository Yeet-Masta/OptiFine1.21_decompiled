package net.optifine.shaders.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.expr.ExpressionFloatArrayCached;
import net.optifine.expr.ExpressionFloatCached;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IExpressionFloatArray;
import net.optifine.expr.ParseException;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.Program;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.ShaderUtils;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersCompatibility;
import net.optifine.shaders.uniform.CustomUniform;
import net.optifine.shaders.uniform.CustomUniforms;
import net.optifine.shaders.uniform.ShaderExpressionResolver;
import net.optifine.shaders.uniform.UniformType;
import net.optifine.util.DynamicDimension;
import net.optifine.util.LineBuffer;
import net.optifine.util.StrUtils;

public class ShaderPackParser {
   public static final Pattern PATTERN_VERSION = Pattern.compile("^\\s*#version\\s+(\\d+).*$");
   public static final Pattern PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
   private static final Set setConstNames = makeSetConstNames();
   private static final Map mapAlphaFuncs = makeMapAlphaFuncs();
   private static final Map mapBlendFactors = makeMapBlendFactors();

   public static ShaderOption[] parseShaderPackOptions(IShaderPack shaderPack, String[] programNames, List listDimensions) {
      if (shaderPack == null) {
         return new ShaderOption[0];
      } else {
         Map mapOptions = new HashMap();
         collectShaderOptions(shaderPack, "/shaders", programNames, mapOptions);
         Iterator it = listDimensions.iterator();

         while(it.hasNext()) {
            int dimId = (Integer)it.next();
            String dirWorld = "/shaders/world" + dimId;
            collectShaderOptions(shaderPack, dirWorld, programNames, mapOptions);
         }

         Collection options = mapOptions.values();
         ShaderOption[] sos = (ShaderOption[])options.toArray(new ShaderOption[options.size()]);
         Comparator comp = new Comparator() {
            public int compare(ShaderOption o1, ShaderOption o2) {
               return o1.getName().compareToIgnoreCase(o2.getName());
            }
         };
         Arrays.sort(sos, comp);
         return sos;
      }
   }

   private static void collectShaderOptions(IShaderPack shaderPack, String dir, String[] programNames, Map mapOptions) {
      for(int i = 0; i < programNames.length; ++i) {
         String programName = programNames[i];
         if (!programName.equals("")) {
            String csh = dir + "/" + programName + ".csh";
            String vsh = dir + "/" + programName + ".vsh";
            String gsh = dir + "/" + programName + ".gsh";
            String fsh = dir + "/" + programName + ".fsh";
            collectShaderOptions(shaderPack, csh, mapOptions);
            collectShaderOptions(shaderPack, vsh, mapOptions);
            collectShaderOptions(shaderPack, gsh, mapOptions);
            collectShaderOptions(shaderPack, fsh, mapOptions);
         }
      }

   }

   private static void collectShaderOptions(IShaderPack sp, String path, Map mapOptions) {
      String[] lines = getLines(sp, path);

      for(int i = 0; i < lines.length; ++i) {
         String line = lines[i];
         ShaderOption so = getShaderOption(line, path);
         if (so != null && !so.getName().startsWith(ShaderMacros.getPrefixMacro())) {
            String key = so.getName();
            ShaderOption so2 = (ShaderOption)mapOptions.get(key);
            if (so2 != null) {
               if (!Config.equals(so2.getValueDefault(), so.getValueDefault())) {
                  if (so2.isEnabled()) {
                     Config.warn("Ambiguous shader option: " + so.getName());
                     String var10000 = Config.arrayToString((Object[])so2.getPaths());
                     Config.warn(" - in " + var10000 + ": " + so2.getValueDefault());
                     var10000 = Config.arrayToString((Object[])so.getPaths());
                     Config.warn(" - in " + var10000 + ": " + so.getValueDefault());
                  }

                  so2.setEnabled(false);
               }

               if (so2.getDescription() == null || so2.getDescription().length() <= 0) {
                  so2.setDescription(so.getDescription());
               }

               so2.addPaths(so.getPaths());
            } else if (!so.checkUsed() || isOptionUsed(so, lines)) {
               mapOptions.put(key, so);
            }
         }
      }

   }

   private static boolean isOptionUsed(ShaderOption so, String[] lines) {
      for(int i = 0; i < lines.length; ++i) {
         String line = lines[i];
         if (so.isUsedInLine(line)) {
            return true;
         }
      }

      return false;
   }

   private static String[] getLines(IShaderPack sp, String path) {
      try {
         List listFiles = new ArrayList();
         LineBuffer lb = loadFile(path, sp, 0, listFiles, 0);
         return lb == null ? new String[0] : lb.getLines();
      } catch (IOException var4) {
         String var10000 = var4.getClass().getName();
         Config.dbg(var10000 + ": " + var4.getMessage());
         return new String[0];
      }
   }

   private static ShaderOption getShaderOption(String line, String path) {
      ShaderOption so = null;
      if (so == null) {
         so = ShaderOptionSwitch.parseOption(line, path);
      }

      if (so == null) {
         so = ShaderOptionVariable.parseOption(line, path);
      }

      if (so != null) {
         return so;
      } else {
         if (so == null) {
            so = ShaderOptionSwitchConst.parseOption(line, path);
         }

         if (so == null) {
            so = ShaderOptionVariableConst.parseOption(line, path);
         }

         return so != null && setConstNames.contains(so.getName()) ? so : null;
      }
   }

   private static Set makeSetConstNames() {
      Set set = new HashSet();
      set.add("shadowMapResolution");
      set.add("shadowMapFov");
      set.add("shadowDistance");
      set.add("shadowDistanceRenderMul");
      set.add("shadowIntervalSize");
      set.add("generateShadowMipmap");
      set.add("generateShadowColorMipmap");
      set.add("shadowHardwareFiltering");
      set.add("shadowHardwareFiltering0");
      set.add("shadowHardwareFiltering1");
      set.add("shadowtex0Mipmap");
      set.add("shadowtexMipmap");
      set.add("shadowtex1Mipmap");
      set.add("shadowcolor0Mipmap");
      set.add("shadowColor0Mipmap");
      set.add("shadowcolor1Mipmap");
      set.add("shadowColor1Mipmap");
      set.add("shadowtex0Nearest");
      set.add("shadowtexNearest");
      set.add("shadow0MinMagNearest");
      set.add("shadowtex1Nearest");
      set.add("shadow1MinMagNearest");
      set.add("shadowcolor0Nearest");
      set.add("shadowColor0Nearest");
      set.add("shadowColor0MinMagNearest");
      set.add("shadowcolor1Nearest");
      set.add("shadowColor1Nearest");
      set.add("shadowColor1MinMagNearest");
      set.add("wetnessHalflife");
      set.add("drynessHalflife");
      set.add("eyeBrightnessHalflife");
      set.add("centerDepthHalflife");
      set.add("sunPathRotation");
      set.add("ambientOcclusionLevel");
      set.add("superSamplingLevel");
      set.add("noiseTextureResolution");
      return set;
   }

   public static ShaderProfile[] parseProfiles(Properties props, ShaderOption[] shaderOptions) {
      String PREFIX_PROFILE = "profile.";
      List list = new ArrayList();
      Set keys = props.keySet();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         if (key.startsWith(PREFIX_PROFILE)) {
            String name = key.substring(PREFIX_PROFILE.length());
            props.getProperty(key);
            Set parsedProfiles = new HashSet();
            ShaderProfile p = parseProfile(name, props, parsedProfiles, shaderOptions);
            if (p != null) {
               list.add(p);
            }
         }
      }

      if (list.size() <= 0) {
         return null;
      } else {
         ShaderProfile[] profs = (ShaderProfile[])list.toArray(new ShaderProfile[list.size()]);
         return profs;
      }
   }

   public static Map parseProgramConditions(Properties props, ShaderOption[] shaderOptions) {
      String PREFIX_PROGRAM = "program.";
      Pattern pattern = Pattern.compile("program\\.([^.]+)\\.enabled");
      Map map = new HashMap();
      Set keys = props.keySet();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         Matcher matcher = pattern.matcher(key);
         if (matcher.matches()) {
            String name = matcher.group(1);
            String val = props.getProperty(key).trim();
            IExpressionBool expr = parseOptionExpression(val, shaderOptions);
            if (expr == null) {
               SMCLog.severe("Error parsing program condition: " + key);
            } else {
               map.put(name, expr);
            }
         }
      }

      return map;
   }

   private static IExpressionBool parseOptionExpression(String val, ShaderOption[] shaderOptions) {
      try {
         ShaderOptionResolver sor = new ShaderOptionResolver(shaderOptions);
         ExpressionParser parser = new ExpressionParser(sor);
         IExpressionBool expr = parser.parseBool(val);
         return expr;
      } catch (ParseException var5) {
         String var10000 = var5.getClass().getName();
         SMCLog.warning(var10000 + ": " + var5.getMessage());
         return null;
      }
   }

   public static Set parseOptionSliders(Properties props, ShaderOption[] shaderOptions) {
      Set sliders = new HashSet();
      String value = props.getProperty("sliders");
      if (value == null) {
         return sliders;
      } else {
         String[] names = Config.tokenize(value, " ");

         for(int i = 0; i < names.length; ++i) {
            String name = names[i];
            ShaderOption so = ShaderUtils.getShaderOption(name, shaderOptions);
            if (so == null) {
               Config.warn("Invalid shader option: " + name);
            } else {
               sliders.add(name);
            }
         }

         return sliders;
      }
   }

   private static ShaderProfile parseProfile(String name, Properties props, Set parsedProfiles, ShaderOption[] shaderOptions) {
      String PREFIX_PROFILE = "profile.";
      String key = PREFIX_PROFILE + name;
      if (parsedProfiles.contains(key)) {
         Config.warn("[Shaders] Profile already parsed: " + name);
         return null;
      } else {
         parsedProfiles.add(name);
         ShaderProfile prof = new ShaderProfile(name);
         String val = props.getProperty(key);
         String[] parts = Config.tokenize(val, " ");

         for(int i = 0; i < parts.length; ++i) {
            String part = parts[i];
            if (part.startsWith(PREFIX_PROFILE)) {
               String nameParent = part.substring(PREFIX_PROFILE.length());
               ShaderProfile profParent = parseProfile(nameParent, props, parsedProfiles, shaderOptions);
               if (prof != null) {
                  prof.addOptionValues(profParent);
                  prof.addDisabledPrograms(profParent.getDisabledPrograms());
               }
            } else {
               String[] tokens = Config.tokenize(part, ":=");
               String option;
               if (tokens.length == 1) {
                  option = tokens[0];
                  boolean on = true;
                  if (option.startsWith("!")) {
                     on = false;
                     option = option.substring(1);
                  }

                  String PREFIX_PROGRAM = "program.";
                  if (option.startsWith(PREFIX_PROGRAM)) {
                     String program = option.substring(PREFIX_PROGRAM.length());
                     if (!Shaders.isProgramPath(program)) {
                        Config.warn("Invalid program: " + program + " in profile: " + prof.getName());
                     } else if (on) {
                        prof.removeDisabledProgram(program);
                     } else {
                        prof.addDisabledProgram(program);
                     }
                  } else {
                     ShaderOption so = ShaderUtils.getShaderOption(option, shaderOptions);
                     if (!(so instanceof ShaderOptionSwitch)) {
                        Config.warn("[Shaders] Invalid option: " + option);
                     } else {
                        prof.addOptionValue(option, String.valueOf(on));
                        so.setVisible(true);
                     }
                  }
               } else if (tokens.length != 2) {
                  Config.warn("[Shaders] Invalid option value: " + part);
               } else {
                  option = tokens[0];
                  String value = tokens[1];
                  ShaderOption so = ShaderUtils.getShaderOption(option, shaderOptions);
                  if (so == null) {
                     Config.warn("[Shaders] Invalid option: " + part);
                  } else if (!so.isValidValue(value)) {
                     Config.warn("[Shaders] Invalid value: " + part);
                  } else {
                     so.setVisible(true);
                     prof.addOptionValue(option, value);
                  }
               }
            }
         }

         return prof;
      }
   }

   public static Map parseGuiScreens(Properties props, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
      Map map = new HashMap();
      parseGuiScreen("screen", props, map, shaderProfiles, shaderOptions);
      return map.isEmpty() ? null : map;
   }

   private static boolean parseGuiScreen(String key, Properties props, Map map, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
      String val = props.getProperty(key);
      if (val == null) {
         return false;
      } else {
         String keyParent = key + "$parent$";
         if (map.containsKey(keyParent)) {
            Config.warn("[Shaders] Screen circular reference: " + key + " = " + val);
            return false;
         } else {
            List list = new ArrayList();
            Set setNames = new HashSet();
            String[] opNames = Config.tokenize(val, " ");

            String opName;
            for(int i = 0; i < opNames.length; ++i) {
               opName = opNames[i];
               if (opName.equals("<empty>")) {
                  list.add((Object)null);
               } else if (setNames.contains(opName)) {
                  Config.warn("[Shaders] Duplicate option: " + opName + ", key: " + key);
               } else {
                  setNames.add(opName);
                  if (opName.equals("<profile>")) {
                     if (shaderProfiles == null) {
                        Config.warn("[Shaders] Option profile can not be used, no profiles defined: " + opName + ", key: " + key);
                     } else {
                        ShaderOptionProfile optionProfile = new ShaderOptionProfile(shaderProfiles, shaderOptions);
                        list.add(optionProfile);
                     }
                  } else if (opName.equals("*")) {
                     ShaderOption soRest = new ShaderOptionRest("<rest>");
                     list.add(soRest);
                  } else if (opName.startsWith("[") && opName.endsWith("]")) {
                     String screen = StrUtils.removePrefixSuffix(opName, "[", "]");
                     if (!screen.matches("^[a-zA-Z0-9_]+$")) {
                        Config.warn("[Shaders] Invalid screen: " + opName + ", key: " + key);
                     } else {
                        map.put(keyParent, (Object)null);
                        boolean parseScreen = parseGuiScreen("screen." + screen, props, map, shaderProfiles, shaderOptions);
                        map.remove(keyParent);
                        if (!parseScreen) {
                           Config.warn("[Shaders] Invalid screen: " + opName + ", key: " + key);
                        } else {
                           ShaderOptionScreen optionScreen = new ShaderOptionScreen(screen);
                           list.add(optionScreen);
                        }
                     }
                  } else {
                     ShaderOption so = ShaderUtils.getShaderOption(opName, shaderOptions);
                     if (so == null) {
                        Config.warn("[Shaders] Invalid option: " + opName + ", key: " + key);
                        list.add((Object)null);
                     } else {
                        so.setVisible(true);
                        list.add(so);
                     }
                  }
               }
            }

            ShaderOption[] scrOps = (ShaderOption[])list.toArray(new ShaderOption[list.size()]);
            opName = props.getProperty(key + ".columns");
            int columns = Config.parseInt(opName, 2);
            ScreenShaderOptions sso = new ScreenShaderOptions(key, scrOps, columns);
            map.put(key, sso);
            return true;
         }
      }
   }

   public static LineBuffer loadShader(Program program, ShaderType shaderType, InputStream is, String filePath, IShaderPack shaderPack, List listFiles, ShaderOption[] activeOptions) throws IOException {
      LineBuffer reader = LineBuffer.readAll(new InputStreamReader(is));
      reader = resolveIncludes(reader, filePath, shaderPack, 0, listFiles, 0);
      reader = addMacros(reader, 0);
      reader = remapTextureUnits(reader);
      LineBuffer writer = new LineBuffer();
      Iterator it = reader.iterator();

      while(it.hasNext()) {
         String line = (String)it.next();
         line = applyOptions(line, activeOptions);
         writer.add(line);
      }

      writer = ShadersCompatibility.remap(program, shaderType, writer);
      return writer;
   }

   private static String applyOptions(String line, ShaderOption[] ops) {
      if (ops != null && ops.length > 0) {
         for(int i = 0; i < ops.length; ++i) {
            ShaderOption op = ops[i];
            if (op.matchesLine(line)) {
               line = op.getSourceLine();
               break;
            }
         }

         return line;
      } else {
         return line;
      }
   }

   public static LineBuffer resolveIncludes(LineBuffer reader, String filePath, IShaderPack shaderPack, int fileIndex, List listFiles, int includeLevel) throws IOException {
      String fileDir = "/";
      int pos = filePath.lastIndexOf("/");
      if (pos >= 0) {
         fileDir = filePath.substring(0, pos);
      }

      LineBuffer writer = new LineBuffer();
      int lineNumber = 0;
      Iterator it = reader.iterator();

      while(it.hasNext()) {
         String line = (String)it.next();
         ++lineNumber;
         Matcher mi = PATTERN_INCLUDE.matcher(line);
         if (mi.matches()) {
            String fileInc = mi.group(1);
            boolean absolute = fileInc.startsWith("/");
            String filePathInc = absolute ? "/shaders" + fileInc : fileDir + "/" + fileInc;
            if (!listFiles.contains(filePathInc)) {
               listFiles.add(filePathInc);
            }

            int includeFileIndex = listFiles.indexOf(filePathInc) + 1;
            LineBuffer lbInc = loadFile(filePathInc, shaderPack, includeFileIndex, listFiles, includeLevel);
            if (lbInc == null) {
               throw new IOException("Included file not found: " + filePath);
            }

            if (lbInc.indexMatch(PATTERN_VERSION) < 0) {
               writer.add("#line 1 " + includeFileIndex);
            }

            writer.add(lbInc.getLines());
            writer.add("#line " + (lineNumber + 1) + " " + fileIndex);
         } else {
            writer.add(line);
         }
      }

      return writer;
   }

   public static LineBuffer addMacros(LineBuffer reader, int fileIndex) throws IOException {
      LineBuffer writer = new LineBuffer(reader.getLines());
      int macroInsertPosition = writer.indexMatch(PATTERN_VERSION);
      if (macroInsertPosition < 0) {
         Config.warn("Macro insert position not found");
         return reader;
      } else {
         ++macroInsertPosition;
         String lineMacro = "#line " + (macroInsertPosition + 1) + " " + fileIndex;
         String[] headerMacros = ShaderMacros.getHeaderMacroLines();
         writer.insert(macroInsertPosition, headerMacros);
         macroInsertPosition += headerMacros.length;
         ShaderMacro[] customMacros = getCustomMacros(writer, macroInsertPosition);
         if (customMacros.length > 0) {
            LineBuffer lb = new LineBuffer();

            for(int i = 0; i < customMacros.length; ++i) {
               ShaderMacro macro = customMacros[i];
               lb.add(macro.getSourceLine());
            }

            writer.insert(macroInsertPosition, lb.getLines());
            macroInsertPosition += lb.size();
         }

         writer.insert(macroInsertPosition, lineMacro);
         return writer;
      }
   }

   private static ShaderMacro[] getCustomMacros(LineBuffer lines, int startPos) {
      Set setMacros = new LinkedHashSet();

      for(int i = startPos; i < lines.size(); ++i) {
         String line = lines.get(i);
         if (line.contains(ShaderMacros.getPrefixMacro())) {
            ShaderMacro[] lineExts = findMacros(line, ShaderMacros.getExtensions());
            setMacros.addAll(Arrays.asList(lineExts));
            ShaderMacro[] lineConsts = findMacros(line, ShaderMacros.getConstantMacros());
            setMacros.addAll(Arrays.asList(lineConsts));
         }
      }

      ShaderMacro[] macros = (ShaderMacro[])setMacros.toArray(new ShaderMacro[setMacros.size()]);
      return macros;
   }

   public static LineBuffer remapTextureUnits(LineBuffer reader) throws IOException {
      if (!Shaders.isRemapLightmap()) {
         return reader;
      } else {
         LineBuffer writer = new LineBuffer();

         String line;
         for(Iterator it = reader.iterator(); it.hasNext(); writer.add(line)) {
            line = (String)it.next();
            String lineNew = line.replace("gl_TextureMatrix[1]", "gl_TextureMatrix[2]");
            lineNew = lineNew.replace("gl_MultiTexCoord1", "gl_MultiTexCoord2");
            if (!lineNew.equals(line)) {
               lineNew = lineNew + " // Legacy fix, replaced TU 1 with 2";
               line = lineNew;
            }
         }

         return writer;
      }
   }

   private static ShaderMacro[] findMacros(String line, ShaderMacro[] macros) {
      List list = new ArrayList();

      for(int i = 0; i < macros.length; ++i) {
         ShaderMacro ext = macros[i];
         if (line.contains(ext.getName())) {
            list.add(ext);
         }
      }

      ShaderMacro[] exts = (ShaderMacro[])list.toArray(new ShaderMacro[list.size()]);
      return exts;
   }

   private static LineBuffer loadFile(String filePath, IShaderPack shaderPack, int fileIndex, List listFiles, int includeLevel) throws IOException {
      if (includeLevel >= 10) {
         throw new IOException("#include depth exceeded: " + includeLevel + ", file: " + filePath);
      } else {
         ++includeLevel;
         InputStream in = shaderPack.getResourceAsStream(filePath);
         if (in == null) {
            return null;
         } else {
            InputStreamReader isr = new InputStreamReader(in, "ASCII");
            LineBuffer br = LineBuffer.readAll(isr);
            br = resolveIncludes(br, filePath, shaderPack, fileIndex, listFiles, includeLevel);
            return br;
         }
      }
   }

   public static CustomUniforms parseCustomUniforms(Properties props) {
      String UNIFORM = "uniform";
      String VARIABLE = "variable";
      String PREFIX_UNIFORM = UNIFORM + ".";
      String PREFIX_VARIABLE = VARIABLE + ".";
      Map mapExpressions = new HashMap();
      List listUniforms = new ArrayList();
      Set keys = props.keySet();
      Iterator it = keys.iterator();

      while(true) {
         while(true) {
            String key;
            String[] keyParts;
            do {
               if (!it.hasNext()) {
                  if (listUniforms.size() <= 0) {
                     return null;
                  }

                  CustomUniform[] cusArr = (CustomUniform[])listUniforms.toArray(new CustomUniform[listUniforms.size()]);
                  CustomUniforms cus = new CustomUniforms(cusArr, mapExpressions);
                  return cus;
               }

               key = (String)it.next();
               keyParts = Config.tokenize(key, ".");
            } while(keyParts.length != 3);

            String kind = keyParts[0];
            String type = keyParts[1];
            String name = keyParts[2];
            String src = props.getProperty(key).trim();
            if (mapExpressions.containsKey(name)) {
               SMCLog.warning("Expression already defined: " + name);
            } else if (kind.equals(UNIFORM) || kind.equals(VARIABLE)) {
               SMCLog.info("Custom " + kind + ": " + name);
               CustomUniform cu = parseCustomUniform(kind, name, type, src, mapExpressions);
               if (cu != null) {
                  mapExpressions.put(name, cu.getExpression());
                  if (!kind.equals(VARIABLE)) {
                     listUniforms.add(cu);
                  }
               }
            }
         }
      }
   }

   private static CustomUniform parseCustomUniform(String kind, String name, String type, String src, Map mapExpressions) {
      try {
         UniformType uniformType = UniformType.parse(type);
         if (uniformType == null) {
            SMCLog.warning("Unknown " + kind + " type: " + String.valueOf(uniformType));
            return null;
         } else {
            ShaderExpressionResolver resolver = new ShaderExpressionResolver(mapExpressions);
            ExpressionParser parser = new ExpressionParser(resolver);
            IExpression expr = parser.parse(src);
            ExpressionType expressionType = expr.getExpressionType();
            if (!uniformType.matchesExpressionType(expressionType)) {
               SMCLog.warning("Expression type does not match " + kind + " type, expression: " + String.valueOf(expressionType) + ", " + kind + ": " + String.valueOf(uniformType) + " " + name);
               return null;
            } else {
               expr = makeExpressionCached(expr);
               CustomUniform cu = new CustomUniform(name, uniformType, expr);
               return cu;
            }
         }
      } catch (ParseException var11) {
         String var10000 = var11.getClass().getName();
         SMCLog.warning(var10000 + ": " + var11.getMessage());
         return null;
      }
   }

   private static IExpression makeExpressionCached(IExpression expr) {
      if (expr instanceof IExpressionFloat) {
         return new ExpressionFloatCached((IExpressionFloat)expr);
      } else {
         return (IExpression)(expr instanceof IExpressionFloatArray ? new ExpressionFloatArrayCached((IExpressionFloatArray)expr) : expr);
      }
   }

   public static void parseAlphaStates(Properties props) {
      Set keys = props.keySet();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         String[] keyParts = Config.tokenize(key, ".");
         if (keyParts.length == 2) {
            String type = keyParts[0];
            String programName = keyParts[1];
            if (type.equals("alphaTest")) {
               Program program = Shaders.getProgram(programName);
               if (program == null) {
                  SMCLog.severe("Invalid program name: " + programName);
               } else {
                  String val = props.getProperty(key).trim();
                  GlAlphaState state = parseAlphaState(val);
                  if (state != null) {
                     program.setAlphaState(state);
                  }
               }
            }
         }
      }

   }

   public static GlAlphaState parseAlphaState(String str) {
      if (str == null) {
         return null;
      } else {
         String[] parts = Config.tokenize(str, " ");
         String str0;
         if (parts.length == 1) {
            str0 = parts[0];
            if (str0.equals("off") || str0.equals("false")) {
               return new GlAlphaState(false);
            }
         } else if (parts.length == 2) {
            str0 = parts[0];
            String str1 = parts[1];
            Integer func = (Integer)mapAlphaFuncs.get(str0);
            float ref = Config.parseFloat(str1, -1.0F);
            if (func != null && ref >= 0.0F) {
               return new GlAlphaState(true, func, ref);
            }
         }

         SMCLog.severe("Invalid alpha test: " + str);
         return null;
      }
   }

   public static void parseBlendStates(Properties props) {
      Set keys = props.keySet();
      Iterator it = keys.iterator();

      while(true) {
         while(true) {
            String key;
            String type;
            String programName;
            String bufferName;
            do {
               String[] keyParts;
               do {
                  do {
                     if (!it.hasNext()) {
                        return;
                     }

                     key = (String)it.next();
                     keyParts = Config.tokenize(key, ".");
                  } while(keyParts.length < 2);
               } while(keyParts.length > 3);

               type = keyParts[0];
               programName = keyParts[1];
               bufferName = keyParts.length == 3 ? keyParts[2] : null;
            } while(!type.equals("blend"));

            Program program = Shaders.getProgram(programName);
            if (program == null) {
               SMCLog.severe("Invalid program name: " + programName);
            } else {
               String val = props.getProperty(key).trim();
               GlBlendState state = parseBlendState(val);
               if (state != null) {
                  if (bufferName != null) {
                     int index = program.getProgramStage().isAnyShadow() ? ShaderParser.getShadowColorIndex(bufferName) : Shaders.getBufferIndex(bufferName);
                     int maxColorIndex = program.getProgramStage().isAnyShadow() ? 2 : 16;
                     if (index >= 0 && index < maxColorIndex) {
                        program.setBlendStateColorIndexed(index, state);
                        SMCLog.info("Blend " + programName + "." + bufferName + "=" + val);
                     } else {
                        SMCLog.severe("Invalid buffer name: " + bufferName);
                     }
                  } else {
                     program.setBlendState(state);
                  }
               }
            }
         }
      }
   }

   public static GlBlendState parseBlendState(String str) {
      if (str == null) {
         return null;
      } else {
         String[] parts = Config.tokenize(str, " ");
         String str0;
         if (parts.length == 1) {
            str0 = parts[0];
            if (str0.equals("off") || str0.equals("false")) {
               return new GlBlendState(false);
            }
         } else if (parts.length == 2 || parts.length == 4) {
            str0 = parts[0];
            String str1 = parts[1];
            String str2 = str0;
            String str3 = str1;
            if (parts.length == 4) {
               str2 = parts[2];
               str3 = parts[3];
            }

            Integer src = (Integer)mapBlendFactors.get(str0);
            Integer dst = (Integer)mapBlendFactors.get(str1);
            Integer srcAlpha = (Integer)mapBlendFactors.get(str2);
            Integer dstAlpha = (Integer)mapBlendFactors.get(str3);
            if (src != null && dst != null && srcAlpha != null && dstAlpha != null) {
               return new GlBlendState(true, src, dst, srcAlpha, dstAlpha);
            }
         }

         SMCLog.severe("Invalid blend mode: " + str);
         return null;
      }
   }

   public static void parseRenderScales(Properties props) {
      Set keys = props.keySet();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         String[] keyParts = Config.tokenize(key, ".");
         if (keyParts.length == 2) {
            String type = keyParts[0];
            String programName = keyParts[1];
            if (type.equals("scale")) {
               Program program = Shaders.getProgram(programName);
               if (program == null) {
                  SMCLog.severe("Invalid program name: " + programName);
               } else {
                  String val = props.getProperty(key).trim();
                  RenderScale scale = parseRenderScale(val);
                  if (scale != null) {
                     program.setRenderScale(scale);
                  }
               }
            }
         }
      }

   }

   private static RenderScale parseRenderScale(String str) {
      if (str == null) {
         return null;
      } else {
         String[] parts = Config.tokenize(str, " ");
         float scale = Config.parseFloat(parts[0], -1.0F);
         float offsetX = 0.0F;
         float offsetY = 0.0F;
         if (parts.length > 1) {
            if (parts.length != 3) {
               SMCLog.severe("Invalid render scale: " + str);
               return null;
            }

            offsetX = Config.parseFloat(parts[1], -1.0F);
            offsetY = Config.parseFloat(parts[2], -1.0F);
         }

         if (Config.between(scale, 0.0F, 1.0F) && Config.between(offsetX, 0.0F, 1.0F) && Config.between(offsetY, 0.0F, 1.0F)) {
            return new RenderScale(scale, offsetX, offsetY);
         } else {
            SMCLog.severe("Invalid render scale: " + str);
            return null;
         }
      }
   }

   public static void parseBuffersFlip(Properties props) {
      Set keys = props.keySet();
      Iterator it = keys.iterator();

      while(true) {
         while(true) {
            String key;
            String type;
            String programName;
            String bufferName;
            do {
               String[] keyParts;
               do {
                  if (!it.hasNext()) {
                     return;
                  }

                  key = (String)it.next();
                  keyParts = Config.tokenize(key, ".");
               } while(keyParts.length != 3);

               type = keyParts[0];
               programName = keyParts[1];
               bufferName = keyParts[2];
            } while(!type.equals("flip"));

            Program program = Shaders.getProgram(programName);
            if (program == null) {
               SMCLog.severe("Invalid program name: " + programName);
            } else {
               Boolean[] buffersFlip = program.getBuffersFlip();
               int buffer = Shaders.getBufferIndex(bufferName);
               if (buffer >= 0 && buffer < buffersFlip.length) {
                  String valStr = props.getProperty(key).trim();
                  Boolean val = Config.parseBoolean(valStr, (Boolean)null);
                  if (val == null) {
                     SMCLog.severe("Invalid boolean value: " + valStr);
                  } else {
                     buffersFlip[buffer] = val;
                  }
               } else {
                  SMCLog.severe("Invalid buffer name: " + bufferName);
               }
            }
         }
      }
   }

   private static Map makeMapAlphaFuncs() {
      Map map = new HashMap();
      map.put("NEVER", new Integer(512));
      map.put("LESS", new Integer(513));
      map.put("EQUAL", new Integer(514));
      map.put("LEQUAL", new Integer(515));
      map.put("GREATER", new Integer(516));
      map.put("NOTEQUAL", new Integer(517));
      map.put("GEQUAL", new Integer(518));
      map.put("ALWAYS", new Integer(519));
      return Collections.unmodifiableMap(map);
   }

   private static Map makeMapBlendFactors() {
      Map map = new HashMap();
      map.put("ZERO", new Integer(0));
      map.put("ONE", new Integer(1));
      map.put("SRC_COLOR", new Integer(768));
      map.put("ONE_MINUS_SRC_COLOR", new Integer(769));
      map.put("DST_COLOR", new Integer(774));
      map.put("ONE_MINUS_DST_COLOR", new Integer(775));
      map.put("SRC_ALPHA", new Integer(770));
      map.put("ONE_MINUS_SRC_ALPHA", new Integer(771));
      map.put("DST_ALPHA", new Integer(772));
      map.put("ONE_MINUS_DST_ALPHA", new Integer(773));
      map.put("CONSTANT_COLOR", new Integer(32769));
      map.put("ONE_MINUS_CONSTANT_COLOR", new Integer(32770));
      map.put("CONSTANT_ALPHA", new Integer(32771));
      map.put("ONE_MINUS_CONSTANT_ALPHA", new Integer(32772));
      map.put("SRC_ALPHA_SATURATE", new Integer(776));
      return Collections.unmodifiableMap(map);
   }

   public static DynamicDimension[] parseBufferSizes(Properties props, int countBuffers) {
      DynamicDimension[] bufferSizes = new DynamicDimension[countBuffers];
      Set keys = props.keySet();
      Iterator it = keys.iterator();

      while(true) {
         while(true) {
            String key;
            String[] keyParts;
            do {
               do {
                  if (!it.hasNext()) {
                     return bufferSizes;
                  }

                  key = (String)it.next();
               } while(!key.startsWith("size.buffer."));

               keyParts = Config.tokenize(key, ".");
            } while(keyParts.length != 3);

            String bufferName = keyParts[2];
            int buffer = Shaders.getBufferIndex(bufferName);
            if (buffer >= 0 && buffer < bufferSizes.length) {
               String val = props.getProperty(key).trim();
               DynamicDimension dim = parseDynamicDimension(val);
               if (dim == null) {
                  SMCLog.severe("Invalid buffer size: " + key + "=" + val);
               } else {
                  bufferSizes[buffer] = dim;
                  if (dim.isRelative()) {
                     SMCLog.info("Relative size " + bufferName + ": " + dim.getWidth() + " " + dim.getHeight());
                  } else {
                     SMCLog.info("Fixed size " + bufferName + ": " + (int)dim.getWidth() + " " + (int)dim.getHeight());
                  }
               }
            } else {
               SMCLog.severe("Invalid buffer name: " + key);
            }
         }
      }
   }

   private static DynamicDimension parseDynamicDimension(String str) {
      if (str == null) {
         return null;
      } else {
         String[] parts = Config.tokenize(str, " ");
         if (parts.length != 2) {
            return null;
         } else {
            int width = Config.parseInt(parts[0], -1);
            int height = Config.parseInt(parts[1], -1);
            if (width >= 0 && height >= 0) {
               return new DynamicDimension(false, (float)width, (float)height);
            } else {
               float widthRel = Config.parseFloat(parts[0], -1.0F);
               float heightRel = Config.parseFloat(parts[1], -1.0F);
               return widthRel >= 0.0F && heightRel >= 0.0F ? new DynamicDimension(true, widthRel, heightRel) : null;
            }
         }
      }
   }
}
