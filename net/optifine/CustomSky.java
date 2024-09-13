package net.optifine;

import com.mojang.blaze3d.vertex.PoseStack;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.optifine.render.Blender;
import net.optifine.shaders.RenderStage;
import net.optifine.shaders.Shaders;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import net.optifine.util.WorldUtils;

public class CustomSky {
   private static CustomSkyLayer[][] worldSkyLayers = null;

   public static void reset() {
      worldSkyLayers = null;
   }

   public static void m_252999_() {
      reset();
      if (Config.isCustomSky()) {
         worldSkyLayers = readCustomSkies();
      }
   }

   private static CustomSkyLayer[][] readCustomSkies() {
      CustomSkyLayer[][] wsls = new CustomSkyLayer[10][0];
      String prefix = "optifine/sky/world";
      int lastWorldId = -1;

      for (int w = 0; w < wsls.length; w++) {
         String worldPrefix = prefix + w;
         List listSkyLayers = new ArrayList();

         for (int i = 0; i < 1000; i++) {
            String path = worldPrefix + "/sky" + i + ".properties";
            int countMissing = 0;

            try {
               ResourceLocation locPath = new ResourceLocation(path);
               InputStream in = Config.getResourceStream(locPath);
               if (in == null) {
                  if (++countMissing > 10) {
                     break;
                  }
               }

               Properties props = new PropertiesOrdered();
               props.load(in);
               in.close();
               Config.dbg("CustomSky properties: " + path);
               String defSource = i + ".png";
               CustomSkyLayer sl = new CustomSkyLayer(props, defSource);
               if (sl.isValid(path)) {
                  String srcPath = StrUtils.addSuffixCheck(sl.source, ".png");
                  ResourceLocation locSource = new ResourceLocation(srcPath);
                  AbstractTexture tex = TextureUtils.getTexture(locSource);
                  if (tex == null) {
                     Config.m_260877_("CustomSky: Texture not found: " + locSource);
                  } else {
                     sl.textureId = tex.m_117963_();
                     listSkyLayers.add(sl);
                     in.close();
                  }
               }
            } catch (FileNotFoundException var17) {
               if (++countMissing > 10) {
                  break;
               }
            } catch (IOException var18) {
               var18.printStackTrace();
            }
         }

         if (listSkyLayers.size() > 0) {
            CustomSkyLayer[] sls = (CustomSkyLayer[])listSkyLayers.toArray(new CustomSkyLayer[listSkyLayers.size()]);
            wsls[w] = sls;
            lastWorldId = w;
         }
      }

      if (lastWorldId < 0) {
         return null;
      } else {
         int worldCount = lastWorldId + 1;
         CustomSkyLayer[][] wslsTrim = new CustomSkyLayer[worldCount][0];

         for (int i = 0; i < wslsTrim.length; i++) {
            wslsTrim[i] = wsls[i];
         }

         return wslsTrim;
      }
   }

   public static void renderSky(Level world, PoseStack matrixStackIn, float partialTicks) {
      if (worldSkyLayers != null) {
         if (Config.isShaders()) {
            Shaders.setRenderStage(RenderStage.CUSTOM_SKY);
         }

         int dimId = WorldUtils.getDimensionId(world);
         if (dimId >= 0 && dimId < worldSkyLayers.length) {
            CustomSkyLayer[] sls = worldSkyLayers[dimId];
            if (sls != null) {
               long time = world.m_46468_();
               int timeOfDay = (int)(time % 24000L);
               float celestialAngle = world.m_46942_(partialTicks);
               float rainStrength = world.m_46722_(partialTicks);
               float thunderStrength = world.m_46661_(partialTicks);
               if (rainStrength > 0.0F) {
                  thunderStrength /= rainStrength;
               }

               for (int i = 0; i < sls.length; i++) {
                  CustomSkyLayer sl = sls[i];
                  if (sl.isActive(world, timeOfDay)) {
                     sl.m_324219_(world, matrixStackIn, timeOfDay, celestialAngle, rainStrength, thunderStrength);
                  }
               }

               float rainBrightness = 1.0F - rainStrength;
               Blender.clearBlend(rainBrightness);
            }
         }
      }
   }

   public static boolean hasSkyLayers(Level world) {
      if (worldSkyLayers == null) {
         return false;
      } else {
         int dimId = WorldUtils.getDimensionId(world);
         if (dimId >= 0 && dimId < worldSkyLayers.length) {
            CustomSkyLayer[] sls = worldSkyLayers[dimId];
            return sls == null ? false : sls.length > 0;
         } else {
            return false;
         }
      }
   }
}
