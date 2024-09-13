package net.optifine.shaders.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.optifine.Config;
import net.optifine.shaders.ITextureFormat;
import net.optifine.shaders.RenderStage;
import net.optifine.shaders.Shaders;

public class ShaderMacros {
   private static String PREFIX_MACRO = "MC_";
   public static String MC_VERSION;
   public static String MC_GL_VERSION;
   public static String MC_GLSL_VERSION;
   public static String MC_OS_WINDOWS;
   public static String MC_OS_MAC;
   public static String MC_OS_LINUX;
   public static String MC_OS_OTHER;
   public static String MC_GL_VENDOR_AMD;
   public static String MC_GL_VENDOR_ATI;
   public static String MC_GL_VENDOR_INTEL;
   public static String MC_GL_VENDOR_MESA;
   public static String MC_GL_VENDOR_NVIDIA;
   public static String MC_GL_VENDOR_XORG;
   public static String MC_GL_VENDOR_OTHER;
   public static String MC_GL_RENDERER_RADEON;
   public static String MC_GL_RENDERER_GEFORCE;
   public static String MC_GL_RENDERER_QUADRO;
   public static String MC_GL_RENDERER_INTEL;
   public static String MC_GL_RENDERER_GALLIUM;
   public static String MC_GL_RENDERER_MESA;
   public static String MC_GL_RENDERER_OTHER;
   public static String MC_FXAA_LEVEL;
   public static String MC_NORMAL_MAP;
   public static String MC_SPECULAR_MAP;
   public static String MC_RENDER_QUALITY;
   public static String MC_SHADOW_QUALITY;
   public static String MC_HAND_DEPTH;
   public static String MC_OLD_HAND_LIGHT;
   public static String MC_OLD_LIGHTING;
   public static String MC_ANISOTROPIC_FILTERING;
   public static String MC_TEXTURE_FORMAT_;
   private static ShaderMacro[] extensionMacros;
   private static ShaderMacro[] constantMacros;

   public static String getOs() {
      Util.OS os = Util.m_137581_();
      switch (<unrepresentable>.$SwitchMap$net$minecraft$Util$OS[os.ordinal()]) {
         case 1:
            return "MC_OS_WINDOWS";
         case 2:
            return "MC_OS_MAC";
         case 3:
            return "MC_OS_LINUX";
         default:
            return "MC_OS_OTHER";
      }
   }

   public static String getVendor() {
      String version = Config.openGlVersion;
      if (version != null && version.m_274455_("Mesa")) {
         return "MC_GL_VENDOR_MESA";
      } else {
         String vendor = Config.openGlVendor;
         if (vendor == null) {
            return "MC_GL_VENDOR_OTHER";
         } else {
            vendor = vendor.toLowerCase();
            if (vendor.startsWith("amd")) {
               return "MC_GL_VENDOR_AMD";
            } else if (vendor.startsWith("ati")) {
               return "MC_GL_VENDOR_ATI";
            } else if (vendor.startsWith("intel")) {
               return "MC_GL_VENDOR_INTEL";
            } else if (vendor.startsWith("nvidia")) {
               return "MC_GL_VENDOR_NVIDIA";
            } else {
               return vendor.startsWith("x.org") ? "MC_GL_VENDOR_XORG" : "MC_GL_VENDOR_OTHER";
            }
         }
      }
   }

   public static String getRenderer() {
      String renderer = Config.openGlRenderer;
      if (renderer == null) {
         return "MC_GL_RENDERER_OTHER";
      } else {
         renderer = renderer.toLowerCase();
         if (renderer.startsWith("amd")) {
            return "MC_GL_RENDERER_RADEON";
         } else if (renderer.startsWith("ati")) {
            return "MC_GL_RENDERER_RADEON";
         } else if (renderer.startsWith("radeon")) {
            return "MC_GL_RENDERER_RADEON";
         } else if (renderer.startsWith("gallium")) {
            return "MC_GL_RENDERER_GALLIUM";
         } else if (renderer.startsWith("intel")) {
            return "MC_GL_RENDERER_INTEL";
         } else if (renderer.startsWith("geforce")) {
            return "MC_GL_RENDERER_GEFORCE";
         } else if (renderer.startsWith("nvidia")) {
            return "MC_GL_RENDERER_GEFORCE";
         } else if (renderer.startsWith("quadro")) {
            return "MC_GL_RENDERER_QUADRO";
         } else if (renderer.startsWith("nvs")) {
            return "MC_GL_RENDERER_QUADRO";
         } else {
            return renderer.startsWith("mesa") ? "MC_GL_RENDERER_MESA" : "MC_GL_RENDERER_OTHER";
         }
      }
   }

   public static String getPrefixMacro() {
      return PREFIX_MACRO;
   }

   public static ShaderMacro[] getExtensions() {
      if (extensionMacros == null) {
         String[] exts = Config.getOpenGlExtensions();
         ShaderMacro[] extMacros = new ShaderMacro[exts.length];

         for (int i = 0; i < exts.length; i++) {
            extMacros[i] = new ShaderMacro(PREFIX_MACRO + exts[i], "");
         }

         extensionMacros = extMacros;
      }

      return extensionMacros;
   }

   public static ShaderMacro[] getConstantMacros() {
      if (constantMacros == null) {
         List<ShaderMacro> list = new ArrayList();
         list.addAll(Arrays.asList(getRenderStages()));
         constantMacros = (ShaderMacro[])list.toArray(new ShaderMacro[list.size()]);
      }

      return constantMacros;
   }

   private static ShaderMacro[] getRenderStages() {
      RenderStage[] rss = RenderStage.values();
      ShaderMacro[] rsMacros = new ShaderMacro[rss.length];

      for (int i = 0; i < rss.length; i++) {
         RenderStage rs = rss[i];
         rsMacros[i] = new ShaderMacro(PREFIX_MACRO + "RENDER_STAGE_" + rs.name(), rs.ordinal() + "");
      }

      return rsMacros;
   }

   public static String getFixedMacroLines() {
      StringBuilder sb = new StringBuilder();
      addMacroLine(sb, "MC_VERSION", Config.getMinecraftVersionInt());
      addMacroLine(sb, "MC_GL_VERSION " + Config.getGlVersion().toInt());
      addMacroLine(sb, "MC_GLSL_VERSION " + Config.getGlslVersion().toInt());
      addMacroLine(sb, getOs());
      addMacroLine(sb, getVendor());
      addMacroLine(sb, getRenderer());
      return sb.toString();
   }

   public static String getOptionMacroLines() {
      StringBuilder sb = new StringBuilder();
      if (Shaders.configAntialiasingLevel > 0) {
         addMacroLine(sb, "MC_FXAA_LEVEL", Shaders.configAntialiasingLevel);
      }

      if (Shaders.configNormalMap) {
         addMacroLine(sb, "MC_NORMAL_MAP");
      }

      if (Shaders.configSpecularMap) {
         addMacroLine(sb, "MC_SPECULAR_MAP");
      }

      addMacroLine(sb, "MC_RENDER_QUALITY", Shaders.configRenderResMul);
      addMacroLine(sb, "MC_SHADOW_QUALITY", Shaders.configShadowResMul);
      addMacroLine(sb, "MC_HAND_DEPTH", Shaders.configHandDepthMul);
      if (Shaders.isOldHandLight()) {
         addMacroLine(sb, "MC_OLD_HAND_LIGHT");
      }

      if (Shaders.isOldLighting()) {
         addMacroLine(sb, "MC_OLD_LIGHTING");
      }

      if (Config.isAnisotropicFiltering()) {
         addMacroLine(sb, "MC_ANISOTROPIC_FILTERING", Config.getAnisotropicFilterLevel());
      }

      return sb.toString();
   }

   public static String getTextureMacroLines() {
      TextureAtlas textureMap = Config.getTextureMap();
      if (textureMap == null) {
         return "";
      } else {
         ITextureFormat textureFormat = textureMap.getTextureFormat();
         if (textureFormat == null) {
            return "";
         } else {
            StringBuilder sb = new StringBuilder();
            String name = textureFormat.getMacroName();
            if (name != null) {
               addMacroLine(sb, "MC_TEXTURE_FORMAT_" + name);
               String ver = textureFormat.getMacroVersion();
               if (ver != null) {
                  addMacroLine(sb, "MC_TEXTURE_FORMAT_" + name + "_" + ver);
               }
            }

            return sb.toString();
         }
      }
   }

   public static String[] getHeaderMacroLines() {
      String str = getFixedMacroLines() + getOptionMacroLines() + getTextureMacroLines();
      return Config.tokenize(str, "\n\r");
   }

   private static void addMacroLine(StringBuilder sb, String name, int value) {
      sb.append("#define ");
      sb.append(name);
      sb.append(" ");
      sb.append(value);
      sb.append("\n");
   }

   private static void addMacroLine(StringBuilder sb, String name, float value) {
      sb.append("#define ");
      sb.append(name);
      sb.append(" ");
      sb.append(value);
      sb.append("\n");
   }

   private static void addMacroLine(StringBuilder sb, String name) {
      sb.append("#define ");
      sb.append(name);
      sb.append("\n");
   }
}
