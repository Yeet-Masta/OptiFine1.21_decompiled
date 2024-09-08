package net.optifine;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.minecraft.src.C_243587_;
import net.minecraft.src.C_290152_;
import net.minecraft.src.C_313429_;
import net.minecraft.src.C_3139_;
import net.minecraft.src.C_3383_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_3565_;
import net.minecraft.src.C_4124_;
import net.minecraft.src.C_4134_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4484_;
import net.minecraft.src.C_4490_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4535_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_50_;
import net.minecraft.src.C_51_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_5322_;
import net.minecraft.src.C_53_;
import net.minecraft.src.C_58_;
import net.minecraft.src.C_62_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
import net.optifine.config.GlVersion;
import net.optifine.gui.GuiMessage;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.Shaders;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.TextureUtils;
import net.optifine.util.TimedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;

public class Config {
   public static final String OF_NAME = "OptiFine";
   public static final String MC_VERSION = "1.21";
   public static final String OF_EDITION = "HD_U";
   public static final String OF_RELEASE = "J1_pre9";
   public static final String VERSION = "OptiFine_1.21_HD_U_J1_pre9";
   private static String build = null;
   private static String newRelease = null;
   private static boolean notify64BitJava = false;
   public static String openGlVersion = null;
   public static String openGlRenderer = null;
   public static String openGlVendor = null;
   public static String[] openGlExtensions = null;
   public static GlVersion glVersion = null;
   public static GlVersion glslVersion = null;
   public static int minecraftVersionInt = -1;
   private static C_3401_ gameSettings = null;
   private static C_3391_ minecraft = C_3391_.m_91087_();
   private static boolean initialized = false;
   private static Thread minecraftThread = null;
   private static int antialiasingLevel = 0;
   private static int availableProcessors = 0;
   public static boolean zoomMode = false;
   public static boolean zoomSmoothCamera = false;
   private static int texturePackClouds = 0;
   private static boolean fullscreenModeChecked = false;
   private static boolean desktopModeChecked = false;
   public static final Float DEF_ALPHA_FUNC_LEVEL = 0.1F;
   private static final Logger LOGGER = LogManager.getLogger();
   public static final boolean logDetail = System.getProperty("log.detail", "false").equals("true");
   private static String mcDebugLast = null;
   private static int fpsMinLast = 0;
   private static int chunkUpdatesLast = 0;
   private static C_4484_ textureMapTerrain;
   private static long timeLastFrameMs;
   private static long averageFrameTimeMs;
   private static long lastFrameTimeMs;
   private static boolean showFrameTime = Boolean.getBoolean("frame.time");

   private Config() {
   }

   public static String getVersion() {
      return "OptiFine_1.21_HD_U_J1_pre9";
   }

   public static String getVersionDebug() {
      StringBuffer sb = new StringBuffer(32);
      if (isDynamicLights()) {
         sb.append("DL: ");
         sb.append(String.valueOf(DynamicLights.getCount()));
         sb.append(", ");
      }

      sb.append("OptiFine_1.21_HD_U_J1_pre9");
      String shaderPack = Shaders.getShaderPackName();
      if (shaderPack != null) {
         sb.append(", ");
         sb.append(shaderPack);
      }

      return sb.toString();
   }

   public static void initGameSettings(C_3401_ settings) {
      if (gameSettings == null) {
         gameSettings = settings;
         updateAvailableProcessors();
         ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
         String forgeVer = (String)Reflector.ForgeVersion_getVersion.call();
         if (forgeVer != null) {
            dbg("Forge version: " + forgeVer);
            ComparableVersion cv = new ComparableVersion(forgeVer);
            ComparableVersion cvMax = new ComparableVersion("47.0.3");
            if (cv.compareTo(cvMax) > 1) {
               dbg("Forge version above " + cvMax + ", antialiasing disabled");
               gameSettings.ofAaLevel = 0;
            }
         }

         antialiasingLevel = gameSettings.ofAaLevel;
      }
   }

   public static void initDisplay() {
      checkInitialized();
      minecraftThread = Thread.currentThread();
      updateThreadPriorities();
      Shaders.startup(C_3391_.m_91087_());
   }

   public static void checkInitialized() {
      if (!initialized) {
         if (C_3391_.m_91087_().m_91268_() != null) {
            initialized = true;
            checkOpenGlCaps();
            startVersionCheckThread();
         }
      }
   }

   private static void checkOpenGlCaps() {
      log("");
      log(getVersion());
      log("Build: " + getBuild());
      log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
      log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
      log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
      log("LWJGL: " + GLFW.glfwGetVersionString());
      openGlVersion = GL11.glGetString(7938);
      openGlRenderer = GL11.glGetString(7937);
      openGlVendor = GL11.glGetString(7936);
      log("OpenGL: " + openGlRenderer + ", version " + openGlVersion + ", " + openGlVendor);
      log("OpenGL Version: " + getOpenGlVersionString());
      int maxTexSize = TextureUtils.getGLMaximumTextureSize();
      dbg("Maximum texture size: " + maxTexSize + "x" + maxTexSize);
   }

   public static String getBuild() {
      if (build == null) {
         try {
            InputStream in = getOptiFineResourceStream("/buildof.txt");
            if (in == null) {
               return null;
            }

            build = readLines(in)[0];
         } catch (Exception var1) {
            warn(var1.getClass().getName() + ": " + var1.getMessage());
            build = "";
         }
      }

      return build;
   }

   public static InputStream getOptiFineResourceStream(String name) {
      InputStream in = ReflectorForge.getOptiFineResourceStream(name);
      return in != null ? in : Config.class.getResourceAsStream(name);
   }

   public static int getMinecraftVersionInt() {
      if (minecraftVersionInt < 0) {
         String mcVer = "1.21";
         if (mcVer.contains("-")) {
            mcVer = tokenize(mcVer, "-")[0];
         }

         String[] verStrs = tokenize(mcVer, ".");
         int ver = 0;
         if (verStrs.length > 0) {
            ver += 10000 * parseInt(verStrs[0], 0);
         }

         if (verStrs.length > 1) {
            ver += 100 * parseInt(verStrs[1], 0);
         }

         if (verStrs.length > 2) {
            ver += 1 * parseInt(verStrs[2], 0);
         }

         minecraftVersionInt = ver;
      }

      return minecraftVersionInt;
   }

   public static String getOpenGlVersionString() {
      GlVersion ver = getGlVersion();
      return ver.getMajor() + "." + ver.getMinor() + "." + ver.getRelease();
   }

   private static GlVersion getGlVersionLwjgl() {
      GLCapabilities glCapabilities = GL.getCapabilities();
      if (glCapabilities.OpenGL44) {
         return new GlVersion(4, 4);
      } else if (glCapabilities.OpenGL43) {
         return new GlVersion(4, 3);
      } else if (glCapabilities.OpenGL42) {
         return new GlVersion(4, 2);
      } else if (glCapabilities.OpenGL41) {
         return new GlVersion(4, 1);
      } else if (glCapabilities.OpenGL40) {
         return new GlVersion(4, 0);
      } else if (glCapabilities.OpenGL33) {
         return new GlVersion(3, 3);
      } else if (glCapabilities.OpenGL32) {
         return new GlVersion(3, 2);
      } else if (glCapabilities.OpenGL31) {
         return new GlVersion(3, 1);
      } else if (glCapabilities.OpenGL30) {
         return new GlVersion(3, 0);
      } else if (glCapabilities.OpenGL21) {
         return new GlVersion(2, 1);
      } else if (glCapabilities.OpenGL20) {
         return new GlVersion(2, 0);
      } else if (glCapabilities.OpenGL15) {
         return new GlVersion(1, 5);
      } else if (glCapabilities.OpenGL14) {
         return new GlVersion(1, 4);
      } else if (glCapabilities.OpenGL13) {
         return new GlVersion(1, 3);
      } else if (glCapabilities.OpenGL12) {
         return new GlVersion(1, 2);
      } else {
         return glCapabilities.OpenGL11 ? new GlVersion(1, 1) : new GlVersion(1, 0);
      }
   }

   public static GlVersion getGlVersion() {
      if (glVersion == null) {
         String verStr = GL11.glGetString(7938);
         glVersion = parseGlVersion(verStr, null);
         if (glVersion == null) {
            glVersion = getGlVersionLwjgl();
         }

         if (glVersion == null) {
            glVersion = new GlVersion(1, 0);
         }
      }

      return glVersion;
   }

   public static GlVersion getGlslVersion() {
      if (glslVersion == null) {
         String verStr = GL11.glGetString(35724);
         glslVersion = parseGlVersion(verStr, null);
         if (glslVersion == null) {
            glslVersion = new GlVersion(1, 10);
         }
      }

      return glslVersion;
   }

   public static GlVersion parseGlVersion(String versionString, GlVersion def) {
      try {
         if (versionString == null) {
            return def;
         } else {
            Pattern REGEXP_VERSION = Pattern.compile("([0-9]+)\\.([0-9]+)(\\.([0-9]+))?(.+)?");
            Matcher matcher = REGEXP_VERSION.matcher(versionString);
            if (!matcher.matches()) {
               return def;
            } else {
               int major = Integer.parseInt(matcher.group(1));
               int minor = Integer.parseInt(matcher.group(2));
               int release = matcher.group(4) != null ? Integer.parseInt(matcher.group(4)) : 0;
               String suffix = matcher.group(5);
               return new GlVersion(major, minor, release, suffix);
            }
         }
      } catch (Exception var8) {
         error("", var8);
         return def;
      }
   }

   public static String[] getOpenGlExtensions() {
      if (openGlExtensions == null) {
         openGlExtensions = detectOpenGlExtensions();
      }

      return openGlExtensions;
   }

   private static String[] detectOpenGlExtensions() {
      try {
         GlVersion ver = getGlVersion();
         if (ver.getMajor() >= 3) {
            int countExt = GL11.glGetInteger(33309);
            if (countExt > 0) {
               String[] exts = new String[countExt];

               for (int i = 0; i < countExt; i++) {
                  exts[i] = GL30.glGetStringi(7939, i);
               }

               return exts;
            }
         }
      } catch (Exception var5) {
         error("", var5);
      }

      try {
         String extStr = GL11.glGetString(7939);
         return extStr.split(" ");
      } catch (Exception var4) {
         error("", var4);
         return new String[0];
      }
   }

   public static void updateThreadPriorities() {
      updateAvailableProcessors();
      int ELEVATED_PRIORITY = 8;
      if (isSingleProcessor()) {
         if (isSmoothWorld()) {
            minecraftThread.setPriority(10);
            setThreadPriority("Server thread", 1);
         } else {
            minecraftThread.setPriority(5);
            setThreadPriority("Server thread", 5);
         }
      } else {
         minecraftThread.setPriority(10);
         setThreadPriority("Server thread", 5);
      }
   }

   private static void setThreadPriority(String prefix, int priority) {
      try {
         ThreadGroup tg = Thread.currentThread().getThreadGroup();
         if (tg == null) {
            return;
         }

         int num = (tg.activeCount() + 10) * 2;
         Thread[] ts = new Thread[num];
         tg.enumerate(ts, false);

         for (int i = 0; i < ts.length; i++) {
            Thread t = ts[i];
            if (t != null && t.getName().startsWith(prefix)) {
               t.setPriority(priority);
            }
         }
      } catch (Throwable var7) {
         warn(var7.getClass().getName() + ": " + var7.getMessage());
      }
   }

   public static boolean isMinecraftThread() {
      return Thread.currentThread() == minecraftThread;
   }

   private static void startVersionCheckThread() {
      VersionCheckThread vct = new VersionCheckThread();
      vct.start();
   }

   public static boolean isMipmaps() {
      return (Integer)gameSettings.m_232119_().m_231551_() > 0;
   }

   public static int getMipmapLevels() {
      return (Integer)gameSettings.m_232119_().m_231551_();
   }

   public static int getMipmapType() {
      switch (gameSettings.ofMipmapType) {
         case 0:
            return 9986;
         case 1:
            return 9986;
         case 2:
            if (isMultiTexture()) {
               return 9985;
            }

            return 9986;
         case 3:
            if (isMultiTexture()) {
               return 9987;
            }

            return 9986;
         default:
            return 9986;
      }
   }

   public static boolean isUseAlphaFunc() {
      float alphaFuncLevel = getAlphaFuncLevel();
      return alphaFuncLevel > DEF_ALPHA_FUNC_LEVEL + 1.0E-5F;
   }

   public static float getAlphaFuncLevel() {
      return DEF_ALPHA_FUNC_LEVEL;
   }

   public static boolean isFogOff() {
      return gameSettings.ofFogType == 3;
   }

   public static boolean isFogOn() {
      return gameSettings.ofFogType != 3;
   }

   public static float getFogStart() {
      return gameSettings.ofFogStart;
   }

   public static void detail(String s) {
      if (logDetail) {
         LOGGER.info("[OptiFine] " + s);
      }
   }

   public static void dbg(String s) {
      LOGGER.info("[OptiFine] " + s);
   }

   public static void warn(String s) {
      LOGGER.warn("[OptiFine] " + s);
   }

   public static void warn(String s, Throwable t) {
      LOGGER.warn("[OptiFine] " + s, t);
   }

   public static void error(String s) {
      LOGGER.error("[OptiFine] " + s);
   }

   public static void error(String s, Throwable t) {
      LOGGER.error("[OptiFine] " + s, t);
   }

   public static void log(String s) {
      dbg(s);
   }

   public static int getUpdatesPerFrame() {
      return gameSettings.ofChunkUpdates;
   }

   public static boolean isDynamicUpdates() {
      return gameSettings.ofChunkUpdatesDynamic;
   }

   public static boolean isGraphicsFancy() {
      return gameSettings.m_232060_().m_231551_() != C_3383_.FAST;
   }

   public static boolean isGraphicsFabulous() {
      return gameSettings.m_232060_().m_231551_() == C_3383_.FABULOUS;
   }

   public static boolean isRainFancy() {
      return gameSettings.ofRain == 0 ? isGraphicsFancy() : gameSettings.ofRain == 2;
   }

   public static boolean isRainOff() {
      return gameSettings.ofRain == 3;
   }

   public static boolean isCloudsFancy() {
      if (gameSettings.ofClouds != 0) {
         return gameSettings.ofClouds == 2;
      } else if (isShaders() && !Shaders.shaderPackClouds.isDefault()) {
         return Shaders.shaderPackClouds.isFancy();
      } else {
         return texturePackClouds != 0 ? texturePackClouds == 2 : isGraphicsFancy();
      }
   }

   public static boolean isCloudsOff() {
      if (gameSettings.ofClouds != 0) {
         return gameSettings.ofClouds == 3;
      } else if (isShaders() && !Shaders.shaderPackClouds.isDefault()) {
         return Shaders.shaderPackClouds.isOff();
      } else {
         return texturePackClouds != 0 ? texturePackClouds == 3 : false;
      }
   }

   public static void updateTexturePackClouds() {
      texturePackClouds = 0;
      C_77_ rm = getResourceManager();
      if (rm != null) {
         try {
            InputStream in = rm.getResourceOrThrow(new C_5265_("optifine/color.properties")).m_215507_();
            if (in == null) {
               return;
            }

            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            String cloudStr = props.getProperty("clouds");
            if (cloudStr == null) {
               return;
            }

            dbg("Texture pack clouds: " + cloudStr);
            cloudStr = cloudStr.toLowerCase();
            if (cloudStr.equals("fast")) {
               texturePackClouds = 1;
            }

            if (cloudStr.equals("fancy")) {
               texturePackClouds = 2;
            }

            if (cloudStr.equals("off") || cloudStr.equals("none")) {
               texturePackClouds = 3;
            }
         } catch (Exception var4) {
         }
      }
   }

   public static C_4535_ getModelManager() {
      return minecraft.m_91291_().modelManager;
   }

   public static boolean isTreesFancy() {
      return gameSettings.ofTrees == 0 ? isGraphicsFancy() : gameSettings.ofTrees != 1;
   }

   public static boolean isTreesSmart() {
      return gameSettings.ofTrees == 4;
   }

   public static boolean isCullFacesLeaves() {
      return gameSettings.ofTrees == 0 ? !isGraphicsFancy() : gameSettings.ofTrees == 4;
   }

   public static int limit(int val, int min, int max) {
      if (val < min) {
         return min;
      } else {
         return val > max ? max : val;
      }
   }

   public static long limit(long val, long min, long max) {
      if (val < min) {
         return min;
      } else {
         return val > max ? max : val;
      }
   }

   public static float limit(float val, float min, float max) {
      if (val < min) {
         return min;
      } else {
         return val > max ? max : val;
      }
   }

   public static double limit(double val, double min, double max) {
      if (val < min) {
         return min;
      } else {
         return val > max ? max : val;
      }
   }

   public static float limitTo1(float val) {
      if (val < 0.0F) {
         return 0.0F;
      } else {
         return val > 1.0F ? 1.0F : val;
      }
   }

   public static boolean isAnimatedWater() {
      return gameSettings.ofAnimatedWater != 2;
   }

   public static boolean isGeneratedWater() {
      return gameSettings.ofAnimatedWater == 1;
   }

   public static boolean isAnimatedPortal() {
      return gameSettings.ofAnimatedPortal;
   }

   public static boolean isAnimatedLava() {
      return gameSettings.ofAnimatedLava != 2;
   }

   public static boolean isGeneratedLava() {
      return gameSettings.ofAnimatedLava == 1;
   }

   public static boolean isAnimatedFire() {
      return gameSettings.ofAnimatedFire;
   }

   public static boolean isAnimatedRedstone() {
      return gameSettings.ofAnimatedRedstone;
   }

   public static boolean isAnimatedExplosion() {
      return gameSettings.ofAnimatedExplosion;
   }

   public static boolean isAnimatedFlame() {
      return gameSettings.ofAnimatedFlame;
   }

   public static boolean isAnimatedSmoke() {
      return gameSettings.ofAnimatedSmoke;
   }

   public static boolean isVoidParticles() {
      return gameSettings.ofVoidParticles;
   }

   public static boolean isWaterParticles() {
      return gameSettings.ofWaterParticles;
   }

   public static boolean isRainSplash() {
      return gameSettings.ofRainSplash;
   }

   public static boolean isPortalParticles() {
      return gameSettings.ofPortalParticles;
   }

   public static boolean isPotionParticles() {
      return gameSettings.ofPotionParticles;
   }

   public static boolean isFireworkParticles() {
      return gameSettings.ofFireworkParticles;
   }

   public static float getAmbientOcclusionLevel() {
      return isShaders() && Shaders.aoLevel >= 0.0F ? Shaders.aoLevel : (float)gameSettings.ofAoLevel;
   }

   public static String listToString(List list) {
      return listToString(list, ", ");
   }

   public static String listToString(List list, String separator) {
      if (list == null) {
         return "";
      } else {
         StringBuffer buf = new StringBuffer(list.size() * 5);

         for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (i > 0) {
               buf.append(separator);
            }

            buf.append(String.valueOf(obj));
         }

         return buf.toString();
      }
   }

   public static String arrayToString(Object[] arr) {
      return arrayToString(arr, ", ");
   }

   public static String arrayToString(Object[] arr, String separator) {
      if (arr == null) {
         return "";
      } else {
         StringBuffer buf = new StringBuffer(arr.length * 5);

         for (int i = 0; i < arr.length; i++) {
            Object obj = arr[i];
            if (i > 0) {
               buf.append(separator);
            }

            buf.append(String.valueOf(obj));
         }

         return buf.toString();
      }
   }

   public static String arrayToString(int[] arr) {
      return arrayToString(arr, ", ");
   }

   public static String arrayToString(int[] arr, String separator) {
      if (arr == null) {
         return "";
      } else {
         StringBuffer buf = new StringBuffer(arr.length * 5);

         for (int i = 0; i < arr.length; i++) {
            int x = arr[i];
            if (i > 0) {
               buf.append(separator);
            }

            buf.append(String.valueOf(x));
         }

         return buf.toString();
      }
   }

   public static String arrayToString(float[] arr) {
      return arrayToString(arr, ", ");
   }

   public static String arrayToString(float[] arr, String separator) {
      if (arr == null) {
         return "";
      } else {
         StringBuffer buf = new StringBuffer(arr.length * 5);

         for (int i = 0; i < arr.length; i++) {
            float x = arr[i];
            if (i > 0) {
               buf.append(separator);
            }

            buf.append(String.valueOf(x));
         }

         return buf.toString();
      }
   }

   public static C_3391_ getMinecraft() {
      return minecraft;
   }

   public static C_4490_ getTextureManager() {
      return minecraft.m_91097_();
   }

   public static C_77_ getResourceManager() {
      return minecraft.m_91098_();
   }

   public static InputStream getResourceStream(C_5265_ location) throws IOException {
      return getResourceStream(minecraft.m_91098_(), location);
   }

   public static InputStream getResourceStream(C_77_ resourceManager, C_5265_ location) throws IOException {
      C_76_ res = resourceManager.getResourceOrThrow(location);
      return res == null ? null : res.m_215507_();
   }

   public static C_76_ getResource(C_5265_ location) throws IOException {
      return minecraft.m_91098_().getResourceOrThrow(location);
   }

   public static boolean hasResource(C_5265_ location) {
      if (location == null) {
         return false;
      } else {
         C_50_ rp = getDefiningResourcePack(location);
         return rp != null;
      }
   }

   public static boolean hasResource(C_77_ resourceManager, C_5265_ location) {
      try {
         C_76_ res = resourceManager.getResourceOrThrow(location);
         return res != null;
      } catch (IOException var3) {
         return false;
      }
   }

   public static boolean hasResource(C_50_ rp, C_5265_ loc) {
      if (rp != null && loc != null) {
         C_243587_<InputStream> supplier = rp.m_214146_(C_51_.CLIENT_RESOURCES, loc);
         return supplier != null;
      } else {
         return false;
      }
   }

   public static C_50_[] getResourcePacks() {
      C_62_ rep = minecraft.m_91099_();
      Collection<C_58_> packInfos = rep.m_10524_();
      List list = new ArrayList();

      for (C_58_ rpic : packInfos) {
         C_50_ rp = rpic.m_10445_();
         if (rp != getDefaultResourcePack()) {
            list.add(rp);
         }
      }

      return (C_50_[])list.toArray(new C_50_[list.size()]);
   }

   public static String getResourcePackNames() {
      if (minecraft.m_91098_() == null) {
         return "";
      } else {
         C_50_[] rps = getResourcePacks();
         if (rps.length <= 0) {
            return getDefaultResourcePack().b();
         } else {
            String[] names = new String[rps.length];

            for (int i = 0; i < rps.length; i++) {
               names[i] = rps[i].m_5542_();
            }

            return arrayToString((Object[])names);
         }
      }
   }

   public static C_53_ getDefaultResourcePack() {
      return minecraft.m_246804_();
   }

   public static boolean isFromDefaultResourcePack(C_5265_ loc) {
      return getDefiningResourcePack(loc) == getDefaultResourcePack();
   }

   public static C_50_ getDefiningResourcePack(C_5265_ location) {
      C_62_ rep = minecraft.m_91099_();
      Collection<C_58_> packInfos = rep.m_10524_();
      List<C_58_> entries = (List<C_58_>)packInfos;

      for (int i = entries.size() - 1; i >= 0; i--) {
         C_58_ entry = (C_58_)entries.get(i);
         C_50_ rp = entry.m_10445_();
         if (rp.m_214146_(C_51_.CLIENT_RESOURCES, location) != null) {
            return rp;
         }
      }

      return null;
   }

   public static InputStream getResourceStream(C_50_ rp, C_51_ type, C_5265_ location) throws IOException {
      C_243587_<InputStream> supplier = rp.m_214146_(type, location);
      return supplier == null ? null : (InputStream)supplier.m_247737_();
   }

   public static C_4134_ getRenderGlobal() {
      return minecraft.f_91060_;
   }

   public static C_4134_ getWorldRenderer() {
      return minecraft.f_91060_;
   }

   public static C_4124_ getGameRenderer() {
      return minecraft.f_91063_;
   }

   public static C_4330_ getEntityRenderDispatcher() {
      return minecraft.m_91290_();
   }

   public static boolean isBetterGrass() {
      return gameSettings.ofBetterGrass != 3;
   }

   public static boolean isBetterGrassFancy() {
      return gameSettings.ofBetterGrass == 2;
   }

   public static boolean isWeatherEnabled() {
      return gameSettings.ofWeather;
   }

   public static boolean isSkyEnabled() {
      return gameSettings.ofSky;
   }

   public static boolean isSunMoonEnabled() {
      return gameSettings.ofSunMoon;
   }

   public static boolean isSunTexture() {
      return !isSunMoonEnabled() ? false : !isShaders() || Shaders.isSun();
   }

   public static boolean isMoonTexture() {
      return !isSunMoonEnabled() ? false : !isShaders() || Shaders.isMoon();
   }

   public static boolean isVignetteEnabled() {
      if (isShaders() && !Shaders.isVignette()) {
         return false;
      } else {
         return gameSettings.ofVignette == 0 ? isGraphicsFancy() : gameSettings.ofVignette == 2;
      }
   }

   public static boolean isStarsEnabled() {
      return gameSettings.ofStars;
   }

   public static void sleep(long ms) {
      try {
         Thread.sleep(ms);
      } catch (InterruptedException var3) {
         error("", var3);
      }
   }

   public static boolean isTimeDayOnly() {
      return gameSettings.ofTime == 1;
   }

   public static boolean isTimeDefault() {
      return gameSettings.ofTime == 0;
   }

   public static boolean isTimeNightOnly() {
      return gameSettings.ofTime == 2;
   }

   public static int getAnisotropicFilterLevel() {
      return gameSettings.ofAfLevel;
   }

   public static boolean isAnisotropicFiltering() {
      return getAnisotropicFilterLevel() > 1;
   }

   public static int getAntialiasingLevel() {
      return antialiasingLevel;
   }

   public static boolean isAntialiasing() {
      return getAntialiasingLevel() > 0;
   }

   public static boolean isAntialiasingConfigured() {
      return getGameSettings().ofAaLevel > 0;
   }

   public static boolean isMultiTexture() {
      return getAnisotropicFilterLevel() > 1 ? true : getAntialiasingLevel() > 0;
   }

   public static boolean between(int val, int min, int max) {
      return val >= min && val <= max;
   }

   public static boolean between(float val, float min, float max) {
      return val >= min && val <= max;
   }

   public static boolean between(double val, double min, double max) {
      return val >= min && val <= max;
   }

   public static boolean isDrippingWaterLava() {
      return gameSettings.ofDrippingWaterLava;
   }

   public static boolean isBetterSnow() {
      return gameSettings.ofBetterSnow;
   }

   public static int parseInt(String str, int defVal) {
      try {
         if (str == null) {
            return defVal;
         } else {
            str = str.trim();
            return Integer.parseInt(str);
         }
      } catch (NumberFormatException var3) {
         return defVal;
      }
   }

   public static int parseHexInt(String str, int defVal) {
      try {
         if (str == null) {
            return defVal;
         } else {
            str = str.trim();
            if (str.startsWith("0x")) {
               str = str.substring(2);
            }

            return Integer.parseInt(str, 16);
         }
      } catch (NumberFormatException var3) {
         return defVal;
      }
   }

   public static float parseFloat(String str, float defVal) {
      try {
         if (str == null) {
            return defVal;
         } else {
            str = str.trim();
            return Float.parseFloat(str);
         }
      } catch (NumberFormatException var3) {
         return defVal;
      }
   }

   public static boolean parseBoolean(String str, boolean defVal) {
      try {
         if (str == null) {
            return defVal;
         } else {
            str = str.trim();
            return Boolean.parseBoolean(str);
         }
      } catch (NumberFormatException var3) {
         return defVal;
      }
   }

   public static Boolean parseBoolean(String str, Boolean defVal) {
      try {
         if (str == null) {
            return defVal;
         } else {
            str = str.trim().toLowerCase();
            if (str.equals("true")) {
               return Boolean.TRUE;
            } else {
               return str.equals("false") ? Boolean.FALSE : defVal;
            }
         }
      } catch (NumberFormatException var3) {
         return defVal;
      }
   }

   public static String[] tokenize(String str, String delim) {
      StringTokenizer tok = new StringTokenizer(str, delim);
      List list = new ArrayList();

      while (tok.hasMoreTokens()) {
         String token = tok.nextToken();
         list.add(token);
      }

      return (String[])list.toArray(new String[list.size()]);
   }

   public static boolean isAnimatedTerrain() {
      return gameSettings.ofAnimatedTerrain;
   }

   public static boolean isAnimatedTextures() {
      return gameSettings.ofAnimatedTextures;
   }

   public static boolean isSwampColors() {
      return gameSettings.ofSwampColors;
   }

   public static boolean isRandomEntities() {
      return gameSettings.ofRandomEntities;
   }

   public static void checkGlError(String loc) {
      int errorCode = GlStateManager._getError();
      if (errorCode != 0 && GlErrors.isEnabled(errorCode)) {
         String errorText = getGlErrorString(errorCode);
         String messageLog = String.format("OpenGL error: %s (%s), at: %s", errorCode, errorText, loc);
         error(messageLog);
         if (isShowGlErrors() && TimedEvent.isActive("ShowGlError", 10000L)) {
            String message = C_4513_.m_118938_("of.message.openglError", new Object[]{errorCode, errorText});
            minecraft.f_91065_.m_93076_().m_93785_(C_4996_.m_237113_(message));
         }
      }
   }

   public static boolean isSmoothBiomes() {
      return (Integer)gameSettings.m_232121_().m_231551_() > 0;
   }

   public static int getBiomeBlendRadius() {
      return (Integer)gameSettings.m_232121_().m_231551_();
   }

   public static boolean isCustomColors() {
      return gameSettings.ofCustomColors;
   }

   public static boolean isCustomSky() {
      return gameSettings.ofCustomSky;
   }

   public static boolean isCustomFonts() {
      return gameSettings.ofCustomFonts;
   }

   public static boolean isShowCapes() {
      return gameSettings.ofShowCapes;
   }

   public static boolean isConnectedTextures() {
      return gameSettings.ofConnectedTextures != 3;
   }

   public static boolean isNaturalTextures() {
      return gameSettings.ofNaturalTextures;
   }

   public static boolean isEmissiveTextures() {
      return gameSettings.ofEmissiveTextures;
   }

   public static boolean isConnectedTexturesFancy() {
      return gameSettings.ofConnectedTextures == 2;
   }

   public static boolean isFastRender() {
      return gameSettings.ofFastRender;
   }

   public static boolean isShaders() {
      return Shaders.shaderPackLoaded;
   }

   public static boolean isShadersShadows() {
      return isShaders() && Shaders.hasShadowMap;
   }

   public static String[] readLines(File file) throws IOException {
      FileInputStream fis = new FileInputStream(file);
      return readLines(fis);
   }

   public static String[] readLines(InputStream is) throws IOException {
      List list = new ArrayList();
      InputStreamReader isr = new InputStreamReader(is, "ASCII");
      BufferedReader br = new BufferedReader(isr);

      while (true) {
         String line = br.readLine();
         if (line == null) {
            return (String[])list.toArray(new String[list.size()]);
         }

         list.add(line);
      }
   }

   public static String readFile(File file) throws IOException {
      FileInputStream fin = new FileInputStream(file);
      return readInputStream(fin, "ASCII");
   }

   public static String readInputStream(InputStream in) throws IOException {
      return readInputStream(in, "ASCII");
   }

   public static String readInputStream(InputStream in, String encoding) throws IOException {
      InputStreamReader inr = new InputStreamReader(in, encoding);
      BufferedReader br = new BufferedReader(inr);
      StringBuffer sb = new StringBuffer();

      while (true) {
         String line = br.readLine();
         if (line == null) {
            in.close();
            return sb.toString();
         }

         sb.append(line);
         sb.append("\n");
      }
   }

   public static byte[] readAll(InputStream is) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] buf = new byte[1024];

      while (true) {
         int len = is.read(buf);
         if (len < 0) {
            is.close();
            return baos.toByteArray();
         }

         baos.write(buf, 0, len);
      }
   }

   public static C_3401_ getGameSettings() {
      return gameSettings;
   }

   public static String getNewRelease() {
      return newRelease;
   }

   public static void setNewRelease(String newRelease) {
      Config.newRelease = newRelease;
   }

   public static int compareRelease(String rel1, String rel2) {
      String[] rels1 = splitRelease(rel1);
      String[] rels2 = splitRelease(rel2);
      String branch1 = rels1[0];
      String branch2 = rels2[0];
      if (!branch1.equals(branch2)) {
         return branch1.compareTo(branch2);
      } else {
         int rev1 = parseInt(rels1[1], -1);
         int rev2 = parseInt(rels2[1], -1);
         if (rev1 != rev2) {
            return rev1 - rev2;
         } else {
            String suf1 = rels1[2];
            String suf2 = rels2[2];
            if (!suf1.equals(suf2)) {
               if (suf1.isEmpty()) {
                  return 1;
               }

               if (suf2.isEmpty()) {
                  return -1;
               }
            }

            return suf1.compareTo(suf2);
         }
      }
   }

   private static String[] splitRelease(String relStr) {
      if (relStr != null && relStr.length() > 0) {
         Pattern p = Pattern.compile("([A-Z])([0-9]+)(.*)");
         Matcher m = p.matcher(relStr);
         if (!m.matches()) {
            return new String[]{"", "", ""};
         } else {
            String branch = normalize(m.group(1));
            String revision = normalize(m.group(2));
            String suffix = normalize(m.group(3));
            return new String[]{branch, revision, suffix};
         }
      } else {
         return new String[]{"", "", ""};
      }
   }

   public static int intHash(int x) {
      x = x ^ 61 ^ x >> 16;
      x += x << 3;
      x ^= x >> 4;
      x *= 668265261;
      return x ^ x >> 15;
   }

   public static int getRandom(C_4675_ blockPos, int face) {
      int rand = intHash(face + 37);
      rand = intHash(rand + blockPos.u());
      rand = intHash(rand + blockPos.w());
      return intHash(rand + blockPos.v());
   }

   public static int getAvailableProcessors() {
      return availableProcessors;
   }

   public static void updateAvailableProcessors() {
      availableProcessors = Runtime.getRuntime().availableProcessors();
   }

   public static boolean isSingleProcessor() {
      return getAvailableProcessors() <= 1;
   }

   public static boolean isSmoothWorld() {
      return gameSettings.ofSmoothWorld;
   }

   public static boolean isLazyChunkLoading() {
      return gameSettings.ofLazyChunkLoading;
   }

   public static boolean isDynamicFov() {
      return gameSettings.ofDynamicFov;
   }

   public static boolean isAlternateBlocks() {
      return gameSettings.ofAlternateBlocks;
   }

   public static int getChunkViewDistance() {
      return gameSettings == null ? 10 : (Integer)gameSettings.m_231984_().m_231551_();
   }

   public static boolean equals(Object o1, Object o2) {
      if (o1 == o2) {
         return true;
      } else {
         return o1 == null ? false : o1.equals(o2);
      }
   }

   public static boolean equalsOne(Object a, Object[] bs) {
      if (bs == null) {
         return false;
      } else {
         for (int i = 0; i < bs.length; i++) {
            Object b = bs[i];
            if (equals(a, b)) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean equalsOne(int val, int[] vals) {
      for (int i = 0; i < vals.length; i++) {
         if (vals[i] == val) {
            return true;
         }
      }

      return false;
   }

   public static boolean isSameOne(Object a, Object[] bs) {
      if (bs == null) {
         return false;
      } else {
         for (int i = 0; i < bs.length; i++) {
            Object b = bs[i];
            if (a == b) {
               return true;
            }
         }

         return false;
      }
   }

   public static String normalize(String s) {
      return s == null ? "" : s;
   }

   private static ByteBuffer readIconImage(InputStream is) throws IOException {
      BufferedImage var2 = ImageIO.read(is);
      int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), (int[])null, 0, var2.getWidth());
      ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);

      for (int var8 : var3) {
         var4.putInt(var8 << 8 | var8 >> 24 & 0xFF);
      }

      var4.flip();
      return var4;
   }

   public static Object[] addObjectToArray(Object[] arr, Object obj) {
      if (arr == null) {
         throw new NullPointerException("The given array is NULL");
      } else {
         int arrLen = arr.length;
         int newLen = arrLen + 1;
         Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
         System.arraycopy(arr, 0, newArr, 0, arrLen);
         newArr[arrLen] = obj;
         return newArr;
      }
   }

   public static Object[] addObjectToArray(Object[] arr, Object obj, int index) {
      List list = new ArrayList(Arrays.asList(arr));
      list.add(index, obj);
      Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), list.size());
      return list.toArray(newArr);
   }

   public static Object[] addObjectsToArray(Object[] arr, Object[] objs) {
      if (arr == null) {
         throw new NullPointerException("The given array is NULL");
      } else if (objs.length == 0) {
         return arr;
      } else {
         int arrLen = arr.length;
         int newLen = arrLen + objs.length;
         Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
         System.arraycopy(arr, 0, newArr, 0, arrLen);
         System.arraycopy(objs, 0, newArr, arrLen, objs.length);
         return newArr;
      }
   }

   public static Object[] removeObjectFromArray(Object[] arr, Object obj) {
      List list = new ArrayList(Arrays.asList(arr));
      list.remove(obj);
      return collectionToArray(list, arr.getClass().getComponentType());
   }

   public static Object[] collectionToArray(Collection coll, Class elementClass) {
      if (coll == null) {
         return null;
      } else if (elementClass == null) {
         return null;
      } else if (elementClass.isPrimitive()) {
         throw new IllegalArgumentException("Can not make arrays with primitive elements (int, double), element class: " + elementClass);
      } else {
         Object[] array = (Object[])Array.newInstance(elementClass, coll.size());
         return coll.toArray(array);
      }
   }

   public static boolean isCustomItems() {
      return gameSettings.ofCustomItems;
   }

   public static String getFpsString() {
      int fps = getFpsAverage();
      int fpsMin = getFpsMin();
      if (showFrameTime) {
         String timeMs = String.format("%.1f", 1000.0 / (double)limit(fps, 1, Integer.MAX_VALUE));
         String timeMaxMs = String.format("%.1f", 1000.0 / (double)limit(fpsMin, 1, Integer.MAX_VALUE));
         return timeMs + "/" + timeMaxMs + " ms";
      } else {
         return fps + "/" + fpsMin + " fps";
      }
   }

   public static boolean isShowFrameTime() {
      return showFrameTime;
   }

   public static int getFpsAverage() {
      return Reflector.getFieldValueInt(Reflector.Minecraft_debugFPS, -1);
   }

   public static int getFpsMin() {
      return fpsMinLast;
   }

   public static int getChunkUpdates() {
      return chunkUpdatesLast;
   }

   public static void updateFpsMin() {
      C_313429_ sl = minecraft.m_293199_().getFrameTimeLogger();
      if (sl.m_322219_() > 0) {
         int fps = Reflector.getFieldValueInt(Reflector.Minecraft_debugFPS, -1);
         if (fps <= 0) {
            fps = 1;
         }

         long timeAvgNs = (long)(1.0 / (double)fps * 1.0E9);
         long timeMaxNs = timeAvgNs;
         long timeTotalNs = 0L;

         for (int ix = sl.m_322219_() - 1; ix > 0 && (double)timeTotalNs < 1.0E9; ix--) {
            long timeNs = sl.m_318870_(ix);
            if (timeNs > timeMaxNs) {
               timeMaxNs = timeNs;
            }

            timeTotalNs += timeNs;
         }

         double timeMaxSec = (double)timeMaxNs / 1.0E9;
         fpsMinLast = (int)(1.0 / timeMaxSec);
      }
   }

   private static void updateChunkUpdates() {
      chunkUpdatesLast = C_290152_.renderChunksUpdated;
      C_290152_.renderChunksUpdated = 0;
   }

   public static int getBitsOs() {
      String progFiles86 = System.getenv("ProgramFiles(X86)");
      return progFiles86 != null ? 64 : 32;
   }

   public static int getBitsJre() {
      String[] propNames = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

      for (int i = 0; i < propNames.length; i++) {
         String propName = propNames[i];
         String propVal = System.getProperty(propName);
         if (propVal != null && propVal.contains("64")) {
            return 64;
         }
      }

      return 32;
   }

   public static boolean isNotify64BitJava() {
      return notify64BitJava;
   }

   public static void setNotify64BitJava(boolean flag) {
      notify64BitJava = flag;
   }

   public static boolean isConnectedModels() {
      return false;
   }

   public static void showGuiMessage(String line1, String line2) {
      GuiMessage gui = new GuiMessage(minecraft.f_91080_, line1, line2);
      minecraft.m_91152_(gui);
   }

   public static int[] addIntToArray(int[] intArray, int intValue) {
      return addIntsToArray(intArray, new int[]{intValue});
   }

   public static int[] addIntsToArray(int[] intArray, int[] copyFrom) {
      if (intArray != null && copyFrom != null) {
         int arrLen = intArray.length;
         int newLen = arrLen + copyFrom.length;
         int[] newArray = new int[newLen];
         System.arraycopy(intArray, 0, newArray, 0, arrLen);

         for (int index = 0; index < copyFrom.length; index++) {
            newArray[index + arrLen] = copyFrom[index];
         }

         return newArray;
      } else {
         throw new NullPointerException("The given array is NULL");
      }
   }

   public static void writeFile(File file, String str) throws IOException {
      FileOutputStream fos = new FileOutputStream(file);
      byte[] bytes = str.getBytes("ASCII");
      fos.write(bytes);
      fos.close();
   }

   public static void setTextureMap(C_4484_ textureMapTerrain) {
      Config.textureMapTerrain = textureMapTerrain;
   }

   public static C_4484_ getTextureMap() {
      return textureMapTerrain;
   }

   public static boolean isDynamicLights() {
      return gameSettings.ofDynamicLights != 3;
   }

   public static boolean isDynamicLightsFast() {
      return gameSettings.ofDynamicLights == 1;
   }

   public static boolean isDynamicHandLight() {
      if (!isDynamicLights()) {
         return false;
      } else {
         return isShaders() ? Shaders.isDynamicHandLight() : true;
      }
   }

   public static boolean isCustomEntityModels() {
      return gameSettings.ofCustomEntityModels;
   }

   public static boolean isCustomGuis() {
      return gameSettings.ofCustomGuis;
   }

   public static int getScreenshotSize() {
      return gameSettings.ofScreenshotSize;
   }

   public static int[] toPrimitive(Integer[] arr) {
      if (arr == null) {
         return null;
      } else if (arr.length == 0) {
         return new int[0];
      } else {
         int[] intArr = new int[arr.length];

         for (int i = 0; i < intArr.length; i++) {
            intArr[i] = arr[i];
         }

         return intArr;
      }
   }

   public static boolean isRenderRegions() {
      return isMultiTexture() ? false : gameSettings.ofRenderRegions && GlStateManager.vboRegions;
   }

   public static boolean isVbo() {
      return GLX.useVbo();
   }

   public static boolean isSmoothFps() {
      return gameSettings.ofSmoothFps;
   }

   public static boolean openWebLink(URI uri) {
      C_5322_.setExceptionOpenUrl(null);
      C_5322_.m_137581_().m_137648_(uri);
      Exception error = C_5322_.getExceptionOpenUrl();
      return error == null;
   }

   public static boolean isShowGlErrors() {
      return gameSettings.ofShowGlErrors;
   }

   public static String arrayToString(boolean[] arr, String separator) {
      if (arr == null) {
         return "";
      } else {
         StringBuffer buf = new StringBuffer(arr.length * 5);

         for (int i = 0; i < arr.length; i++) {
            boolean x = arr[i];
            if (i > 0) {
               buf.append(separator);
            }

            buf.append(String.valueOf(x));
         }

         return buf.toString();
      }
   }

   public static boolean isIntegratedServerRunning() {
      return minecraft.m_91092_() == null ? false : minecraft.m_91090_();
   }

   public static IntBuffer createDirectIntBuffer(int capacity) {
      return C_3139_.m_166247_(capacity << 2).asIntBuffer();
   }

   public static PointerBuffer createDirectPointerBuffer(int capacity) {
      return PointerBuffer.allocateDirect(capacity);
   }

   public static String getGlErrorString(int err) {
      switch (err) {
         case 0:
            return "No error";
         case 1280:
            return "Invalid enum";
         case 1281:
            return "Invalid value";
         case 1282:
            return "Invalid operation";
         case 1283:
            return "Stack overflow";
         case 1284:
            return "Stack underflow";
         case 1285:
            return "Out of memory";
         case 1286:
            return "Invalid framebuffer operation";
         default:
            return "Unknown";
      }
   }

   public static boolean isKeyDown(int key) {
      return GLFW.glfwGetKey(minecraft.m_91268_().m_85439_(), key) == 1;
   }

   public static boolean isTrue(Boolean val) {
      return val != null && val;
   }

   public static boolean isFalse(Boolean val) {
      return val != null && !val;
   }

   public static boolean isReloadingResources() {
      if (minecraft.m_91265_() == null) {
         return false;
      } else {
         if (minecraft.m_91265_() instanceof C_3565_) {
            C_3565_ rlpg = (C_3565_)minecraft.m_91265_();
            if (rlpg.isFadeOut()) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean isQuadsToTriangles() {
      return !isShaders() ? false : !Shaders.canRenderQuads();
   }

   public static void frameStart() {
      long timeNowMs = System.currentTimeMillis();
      long frameTimeMs = timeNowMs - timeLastFrameMs;
      timeLastFrameMs = timeNowMs;
      frameTimeMs = limit(frameTimeMs, 1L, 1000L);
      averageFrameTimeMs = (averageFrameTimeMs + frameTimeMs) / 2L;
      averageFrameTimeMs = limit(averageFrameTimeMs, 1L, 1000L);
      lastFrameTimeMs = frameTimeMs;
      if (minecraft.f_90977_ != mcDebugLast) {
         mcDebugLast = minecraft.f_90977_;
         updateFpsMin();
         updateChunkUpdates();
      }
   }

   public static long getLastFrameTimeMs() {
      return lastFrameTimeMs;
   }

   public static long getAverageFrameTimeMs() {
      return averageFrameTimeMs;
   }

   public static float getAverageFrameTimeSec() {
      return (float)getAverageFrameTimeMs() / 1000.0F;
   }

   public static long getAverageFrameFps() {
      return 1000L / getAverageFrameTimeMs();
   }

   public static void checkNull(Object obj, String msg) throws NullPointerException {
      if (obj == null) {
         throw new NullPointerException(msg);
      }
   }

   public static boolean isTelemetryOn() {
      return gameSettings.ofTelemetry != 2;
   }

   public static boolean isTelemetryAnonymous() {
      return gameSettings.ofTelemetry == 1;
   }
}
