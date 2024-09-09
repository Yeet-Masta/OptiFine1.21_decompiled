package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
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
   private static final ResourceLocation LOCATION_TEXTURE_EMPTY;
   private static final ResourceLocation LOCATION_SPRITE_EMPTY;
   private static TextureManager textureManager;
   private static int countRecursive;

   public static boolean isActive() {
      return active;
   }

   public static String getSuffixEmissive() {
      return suffixEmissive;
   }

   public static void beginRender() {
      if (render) {
         ++countRecursive;
      } else {
         render = true;
         hasEmissive = false;
      }
   }

   public static ResourceLocation getEmissiveTexture(ResourceLocation locationIn) {
      if (!render) {
         return locationIn;
      } else {
         AbstractTexture texture = textureManager.m_118506_(locationIn);
         if (texture instanceof TextureAtlas) {
            return locationIn;
         } else {
            ResourceLocation locationEmissive = null;
            if (texture instanceof SimpleTexture) {
               locationEmissive = ((SimpleTexture)texture).locationEmissive;
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

   public static TextureAtlasSprite getEmissiveSprite(TextureAtlasSprite sprite) {
      if (!render) {
         return sprite;
      } else {
         TextureAtlasSprite spriteEmissive = sprite.spriteEmissive;
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

   public static BakedQuad getEmissiveQuad(BakedQuad quad) {
      if (!render) {
         return quad;
      } else {
         BakedQuad quadEmissive = quad.getQuadEmissive();
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
         --countRecursive;
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
            ResourceLocation loc = new ResourceLocation(fileName);
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

   public static void updateIcons(TextureAtlas textureMap, Set locations) {
      if (active) {
         Iterator var2 = locations.iterator();

         while(var2.hasNext()) {
            ResourceLocation loc = (ResourceLocation)var2.next();
            checkEmissive(textureMap, loc);
         }

      }
   }

   private static void checkEmissive(TextureAtlas textureMap, ResourceLocation locSprite) {
      String suffixEm = getSuffixEmissive();
      if (suffixEm != null) {
         if (!locSprite.m_135815_().endsWith(suffixEm)) {
            String var10002 = locSprite.m_135827_();
            String var10003 = locSprite.m_135815_();
            ResourceLocation locSpriteEm = new ResourceLocation(var10002, var10003 + suffixEm);
            ResourceLocation locPngEm = textureMap.getSpritePath(locSpriteEm);
            if (Config.hasResource(locPngEm)) {
               TextureAtlasSprite sprite = textureMap.registerSprite(locSprite);
               TextureAtlasSprite spriteEmissive = textureMap.registerSprite(locSpriteEm);
               spriteEmissive.isSpriteEmissive = true;
               sprite.spriteEmissive = spriteEmissive;
               textureMap.registerSprite(LOCATION_SPRITE_EMPTY);
            }
         }
      }
   }

   public static void refreshIcons(TextureAtlas textureMap) {
      Collection sprites = textureMap.getRegisteredSprites();
      Iterator var2 = sprites.iterator();

      while(var2.hasNext()) {
         TextureAtlasSprite sprite = (TextureAtlasSprite)var2.next();
         refreshIcon(sprite, textureMap);
      }

   }

   private static void refreshIcon(TextureAtlasSprite sprite, TextureAtlas textureMap) {
      if (sprite.spriteEmissive != null) {
         TextureAtlasSprite spriteNew = textureMap.getUploadedSprite(sprite.getName());
         if (spriteNew != null) {
            TextureAtlasSprite spriteEmissiveNew = textureMap.getUploadedSprite(sprite.spriteEmissive.getName());
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

   public static boolean isEmissive(ResourceLocation loc) {
      return suffixEmissivePng == null ? false : loc.m_135815_().endsWith(suffixEmissivePng);
   }

   public static void loadTexture(ResourceLocation loc, SimpleTexture tex) {
      if (loc != null && tex != null) {
         tex.isEmissive = false;
         tex.locationEmissive = null;
         if (suffixEmissivePng != null) {
            String path = loc.m_135815_();
            if (path.endsWith(".png")) {
               if (path.endsWith(suffixEmissivePng)) {
                  tex.isEmissive = true;
               } else {
                  String var10000 = path.substring(0, path.length() - ".png".length());
                  String pathEmPng = var10000 + suffixEmissivePng;
                  ResourceLocation locEmPng = new ResourceLocation(loc.m_135827_(), pathEmPng);
                  if (Config.hasResource(locEmPng)) {
                     tex.locationEmissive = locEmPng;
                  }
               }
            }
         }
      }
   }

   static {
      LOCATION_TEXTURE_EMPTY = TextureUtils.LOCATION_TEXTURE_EMPTY;
      LOCATION_SPRITE_EMPTY = TextureUtils.LOCATION_SPRITE_EMPTY;
      countRecursive = 0;
   }
}
