package net.optifine;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Properties;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class CustomLoadingScreen {
   private ResourceLocation locationTexture;
   private int scaleMode = 0;
   private int scale = 2;
   private boolean center;
   private static int SCALE_DEFAULT;
   private static int SCALE_MODE_FIXED;
   private static int SCALE_MODE_FULL;
   private static int SCALE_MODE_STRETCH;

   public CustomLoadingScreen(ResourceLocation locationTexture, int scaleMode, int scale, boolean center) {
      this.locationTexture = locationTexture;
      this.scaleMode = scaleMode;
      this.scale = scale;
      this.center = center;
   }

   public static CustomLoadingScreen parseScreen(String path, int dimId, Properties props) {
      ResourceLocation loc = new ResourceLocation(path);
      int scaleMode = parseScaleMode(getProperty("scaleMode", dimId, props));
      int scaleDef = scaleMode == 0 ? 2 : 1;
      int scale = parseScale(getProperty("scale", dimId, props), scaleDef);
      boolean center = Config.parseBoolean(getProperty("center", dimId, props), false);
      return new CustomLoadingScreen(loc, scaleMode, scale, center);
   }

   private static String getProperty(String key, int dim, Properties props) {
      if (props == null) {
         return null;
      } else {
         String val = props.getProperty("dim" + dim + "." + key);
         return val != null ? val : props.getProperty(key);
      }
   }

   private static int parseScaleMode(String str) {
      if (str == null) {
         return 0;
      } else {
         str = str.toLowerCase().trim();
         if (str.equals("fixed")) {
            return 0;
         } else if (str.equals("full")) {
            return 1;
         } else if (str.equals("stretch")) {
            return 2;
         } else {
            CustomLoadingScreens.warn("Invalid scale mode: " + str);
            return 0;
         }
      }
   }

   private static int parseScale(String str, int def) {
      if (str == null) {
         return def;
      } else {
         str = str.trim();
         int val = Config.parseInt(str, -1);
         if (val < 1) {
            CustomLoadingScreens.warn("Invalid scale: " + str);
            return def;
         } else {
            return val;
         }
      }
   }

   public void drawBackground(int width, int height) {
      Tesselator tessellator = Tesselator.m_85913_();
      RenderSystem.setShader(GameRenderer::m_172820_);
      RenderSystem.setShaderTexture(0, this.locationTexture);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      float div = (float)(16 * this.scale);
      float uMax = (float)width / div;
      float vMax = (float)height / div;
      float du = 0.0F;
      float dv = 0.0F;
      if (this.center) {
         du = (div - (float)width) / (div * 2.0F);
         dv = (div - (float)height) / (div * 2.0F);
      }

      switch (this.scaleMode) {
         case 1:
            div = (float)Math.max(width, height);
            uMax = (float)(this.scale * width) / div;
            vMax = (float)(this.scale * height) / div;
            if (this.center) {
               du = (float)this.scale * (div - (float)width) / (div * 2.0F);
               dv = (float)this.scale * (div - (float)height) / (div * 2.0F);
            }
            break;
         case 2:
            uMax = (float)this.scale;
            vMax = (float)this.scale;
            du = 0.0F;
            dv = 0.0F;
      }

      BufferBuilder bufferbuilder = tessellator.m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
      bufferbuilder.m_167146_(0.0F, (float)height, 0.0F).m_167083_(du, dv + vMax).m_167129_(255, 255, 255, 255);
      bufferbuilder.m_167146_((float)width, (float)height, 0.0F).m_167083_(du + uMax, dv + vMax).m_167129_(255, 255, 255, 255);
      bufferbuilder.m_167146_((float)width, 0.0F, 0.0F).m_167083_(du + uMax, dv).m_167129_(255, 255, 255, 255);
      bufferbuilder.m_167146_(0.0F, 0.0F, 0.0F).m_167083_(du, dv).m_167129_(255, 255, 255, 255);
      BufferUploader.m_231202_(bufferbuilder.m_339905_());
   }
}
