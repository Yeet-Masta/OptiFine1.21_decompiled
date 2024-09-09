package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.optifine.render.RenderUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.TextureUtils;

public class EmissiveTextures {
   private static String suffixEmissive = null;
   private static String suffixEmissivePng = null;
   private static boolean active = false;
   private static boolean render = false;
   private static boolean hasEmissive = false;
   private static boolean renderEmissive = false;
   private static final String SUFFIX_PNG = ".png";
   private static final net.minecraft.resources.ResourceLocation LOCATION_TEXTURE_EMPTY = TextureUtils.LOCATION_TEXTURE_EMPTY;
   private static final net.minecraft.resources.ResourceLocation LOCATION_SPRITE_EMPTY = TextureUtils.LOCATION_SPRITE_EMPTY;
   private static net.minecraft.client.renderer.texture.TextureManager textureManager;
   private static int countRecursive = 0;

   public static boolean isActive() {
      return active;
   }

   public static String getSuffixEmissive() {
      return suffixEmissive;
   }

   public static void beginRender() {
      if (render) {
         countRecursive++;
      } else {
         render = true;
         hasEmissive = false;
      }
   }

   public static net.minecraft.resources.ResourceLocation getEmissiveTexture(net.minecraft.resources.ResourceLocation locationIn) {
      if (!render) {
         return locationIn;
      } else {
         net.minecraft.client.renderer.texture.AbstractTexture texture = textureManager.m_118506_(locationIn);
         if (texture instanceof net.minecraft.client.renderer.texture.TextureAtlas) {
            return locationIn;
         } else {
            net.minecraft.resources.ResourceLocation locationEmissive = null;
            if (texture instanceof net.minecraft.client.renderer.texture.SimpleTexture) {
               locationEmissive = ((net.minecraft.client.renderer.texture.SimpleTexture)texture).locationEmissive;
            }

            if (!renderEmissive) {
               if (locationEmissive != null) {
                  hasEmissive = true;
               }

               return locationIn;
            } else {
               if (locationEmissive == null) {
                  locationEmissive = LOCATION_TEXTURE_EMPTY;
               }

               return locationEmissive;
            }
         }
      }
   }

   public static net.minecraft.client.renderer.texture.TextureAtlasSprite getEmissiveSprite(net.minecraft.client.renderer.texture.TextureAtlasSprite sprite) {
      if (!render) {
         return sprite;
      } else {
         net.minecraft.client.renderer.texture.TextureAtlasSprite spriteEmissive = sprite.spriteEmissive;
         if (!renderEmissive) {
            if (spriteEmissive != null) {
               hasEmissive = true;
            }

            return sprite;
         } else {
            if (spriteEmissive == null) {
               spriteEmissive = sprite.getTextureAtlas().m_118316_(LOCATION_SPRITE_EMPTY);
            }

            return spriteEmissive;
         }
      }
   }

   public static net.minecraft.client.renderer.block.model.BakedQuad getEmissiveQuad(net.minecraft.client.renderer.block.model.BakedQuad quad) {
      if (!render) {
         return quad;
      } else {
         net.minecraft.client.renderer.block.model.BakedQuad quadEmissive = quad.getQuadEmissive();
         if (!renderEmissive) {
            if (quadEmissive != null) {
               hasEmissive = true;
            }

            return quad;
         } else {
            return quadEmissive;
         }
      }
   }

   public static boolean hasEmissive() {
      return countRecursive > 0 ? false : hasEmissive;
   }

   public static void beginRenderEmissive() {
      renderEmissive = true;
   }

   public static boolean isRenderEmissive() {
      return renderEmissive;
   }

   public static void endRenderEmissive() {
      RenderUtils.flushRenderBuffers();
      renderEmissive = false;
   }

   public static void endRender() {
      if (countRecursive > 0) {
         countRecursive--;
      } else {
         render = false;
         hasEmissive = false;
      }
   }

   public static void update() {
      textureManager = Minecraft.m_91087_().m_91097_();
      active = false;
      suffixEmissive = null;
      suffixEmissivePng = null;
      if (Config.isEmissiveTextures()) {
         try {
            String fileName = "optifine/emissive.properties";
            net.minecraft.resources.ResourceLocation loc = new net.minecraft.resources.ResourceLocation(fileName);
            InputStream in = Config.getResourceStream(loc);
            if (in == null) {
               return;
            }

            dbg("Loading " + fileName);
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            suffixEmissive = props.getProperty("suffix.emissive");
            if (suffixEmissive != null) {
               suffixEmissivePng = suffixEmissive + ".png";
            }

            active = suffixEmissive != null;
         } catch (FileNotFoundException var4) {
            return;
         } catch (IOException var5) {
            var5.printStackTrace();
         }
      }
   }

   public static void updateIcons(net.minecraft.client.renderer.texture.TextureAtlas textureMap, Set<net.minecraft.resources.ResourceLocation> locations) {
      if (active) {
         for (net.minecraft.resources.ResourceLocation loc : locations) {
            checkEmissive(textureMap, loc);
         }
      }
   }

   private static void checkEmissive(net.minecraft.client.renderer.texture.TextureAtlas textureMap, net.minecraft.resources.ResourceLocation locSprite) {
      String suffixEm = getSuffixEmissive();
      if (suffixEm != null) {
         if (!locSprite.m_135815_().endsWith(suffixEm)) {
            net.minecraft.resources.ResourceLocation locSpriteEm = new net.minecraft.resources.ResourceLocation(
               locSprite.m_135827_(), locSprite.m_135815_() + suffixEm
            );
            net.minecraft.resources.ResourceLocation locPngEm = textureMap.getSpritePath(locSpriteEm);
            if (Config.hasResource(locPngEm)) {
               net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = textureMap.registerSprite(locSprite);
               net.minecraft.client.renderer.texture.TextureAtlasSprite spriteEmissive = textureMap.registerSprite(locSpriteEm);
               spriteEmissive.isSpriteEmissive = true;
               sprite.spriteEmissive = spriteEmissive;
               textureMap.registerSprite(LOCATION_SPRITE_EMPTY);
            }
         }
      }
   }

   public static void refreshIcons(net.minecraft.client.renderer.texture.TextureAtlas textureMap) {
      for (net.minecraft.client.renderer.texture.TextureAtlasSprite sprite : textureMap.getRegisteredSprites()) {
         refreshIcon(sprite, textureMap);
      }
   }

   private static void refreshIcon(
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite, net.minecraft.client.renderer.texture.TextureAtlas textureMap
   ) {
      if (sprite.spriteEmissive != null) {
         net.minecraft.client.renderer.texture.TextureAtlasSprite spriteNew = textureMap.getUploadedSprite(sprite.getName());
         if (spriteNew != null) {
            net.minecraft.client.renderer.texture.TextureAtlasSprite spriteEmissiveNew = textureMap.getUploadedSprite(sprite.spriteEmissive.getName());
            if (spriteEmissiveNew != null) {
               spriteEmissiveNew.isSpriteEmissive = true;
               spriteNew.spriteEmissive = spriteEmissiveNew;
            }
         }
      }
   }

   private static void dbg(String str) {
      Config.dbg("EmissiveTextures: " + str);
   }

   private static void warn(String str) {
      Config.warn("EmissiveTextures: " + str);
   }

   public static boolean isEmissive(net.minecraft.resources.ResourceLocation loc) {
      return suffixEmissivePng == null ? false : loc.m_135815_().endsWith(suffixEmissivePng);
   }

   public static void loadTexture(net.minecraft.resources.ResourceLocation loc, net.minecraft.client.renderer.texture.SimpleTexture tex) {
      if (loc != null && tex != null) {
         tex.isEmissive = false;
         tex.locationEmissive = null;
         if (suffixEmissivePng != null) {
            String path = loc.m_135815_();
            if (path.endsWith(".png")) {
               if (path.endsWith(suffixEmissivePng)) {
                  tex.isEmissive = true;
               } else {
                  String pathEmPng = path.substring(0, path.length() - ".png".length()) + suffixEmissivePng;
                  net.minecraft.resources.ResourceLocation locEmPng = new net.minecraft.resources.ResourceLocation(loc.m_135827_(), pathEmPng);
                  if (Config.hasResource(locEmPng)) {
                     tex.locationEmissive = locEmPng;
                  }
               }
            }
         }
      }
   }
}
