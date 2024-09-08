package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4196_;
import net.minecraft.src.C_4468_;
import net.minecraft.src.C_4476_;
import net.minecraft.src.C_4484_;
import net.minecraft.src.C_4486_;
import net.minecraft.src.C_4490_;
import net.minecraft.src.C_5265_;
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
   private static final C_5265_ LOCATION_TEXTURE_EMPTY = TextureUtils.LOCATION_TEXTURE_EMPTY;
   private static final C_5265_ LOCATION_SPRITE_EMPTY = TextureUtils.LOCATION_SPRITE_EMPTY;
   private static C_4490_ textureManager;
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

   public static C_5265_ getEmissiveTexture(C_5265_ locationIn) {
      if (!render) {
         return locationIn;
      } else {
         C_4468_ texture = textureManager.m_118506_(locationIn);
         if (texture instanceof C_4484_) {
            return locationIn;
         } else {
            C_5265_ locationEmissive = null;
            if (texture instanceof C_4476_) {
               locationEmissive = ((C_4476_)texture).locationEmissive;
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

   public static C_4486_ getEmissiveSprite(C_4486_ sprite) {
      if (!render) {
         return sprite;
      } else {
         C_4486_ spriteEmissive = sprite.spriteEmissive;
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

   public static C_4196_ getEmissiveQuad(C_4196_ quad) {
      if (!render) {
         return quad;
      } else {
         C_4196_ quadEmissive = quad.getQuadEmissive();
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
      textureManager = C_3391_.m_91087_().m_91097_();
      active = false;
      suffixEmissive = null;
      suffixEmissivePng = null;
      if (Config.isEmissiveTextures()) {
         try {
            String fileName = "optifine/emissive.properties";
            C_5265_ loc = new C_5265_(fileName);
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

   public static void updateIcons(C_4484_ textureMap, Set<C_5265_> locations) {
      if (active) {
         for (C_5265_ loc : locations) {
            checkEmissive(textureMap, loc);
         }
      }
   }

   private static void checkEmissive(C_4484_ textureMap, C_5265_ locSprite) {
      String suffixEm = getSuffixEmissive();
      if (suffixEm != null) {
         if (!locSprite.m_135815_().endsWith(suffixEm)) {
            C_5265_ locSpriteEm = new C_5265_(locSprite.m_135827_(), locSprite.m_135815_() + suffixEm);
            C_5265_ locPngEm = textureMap.getSpritePath(locSpriteEm);
            if (Config.hasResource(locPngEm)) {
               C_4486_ sprite = textureMap.registerSprite(locSprite);
               C_4486_ spriteEmissive = textureMap.registerSprite(locSpriteEm);
               spriteEmissive.isSpriteEmissive = true;
               sprite.spriteEmissive = spriteEmissive;
               textureMap.registerSprite(LOCATION_SPRITE_EMPTY);
            }
         }
      }
   }

   public static void refreshIcons(C_4484_ textureMap) {
      for (C_4486_ sprite : textureMap.getRegisteredSprites()) {
         refreshIcon(sprite, textureMap);
      }
   }

   private static void refreshIcon(C_4486_ sprite, C_4484_ textureMap) {
      if (sprite.spriteEmissive != null) {
         C_4486_ spriteNew = textureMap.getUploadedSprite(sprite.getName());
         if (spriteNew != null) {
            C_4486_ spriteEmissiveNew = textureMap.getUploadedSprite(sprite.spriteEmissive.getName());
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

   public static boolean isEmissive(C_5265_ loc) {
      return suffixEmissivePng == null ? false : loc.m_135815_().endsWith(suffixEmissivePng);
   }

   public static void loadTexture(C_5265_ loc, C_4476_ tex) {
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
                  C_5265_ locEmPng = new C_5265_(loc.m_135827_(), pathEmPng);
                  if (Config.hasResource(locEmPng)) {
                     tex.locationEmissive = locEmPng;
                  }
               }
            }
         }
      }
   }
}
