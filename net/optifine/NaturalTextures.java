package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.util.TextureUtils;

public class NaturalTextures {
   private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];

   public static void update() {
      propertiesByIndex = new NaturalProperties[0];
      if (Config.isNaturalTextures()) {
         String fileName = "optifine/natural.properties";

         try {
            ResourceLocation loc = new ResourceLocation(fileName);
            if (!Config.hasResource(loc)) {
               Config.dbg("NaturalTextures: configuration \"" + fileName + "\" not found");
               return;
            }

            boolean defaultConfig = Config.isFromDefaultResourcePack(loc);
            InputStream in = Config.getResourceStream(loc);
            ArrayList list = new ArrayList(256);
            String configStr = Config.readInputStream(in);
            in.close();
            String[] configLines = Config.tokenize(configStr, "\n\r");
            if (defaultConfig) {
               Config.dbg("Natural Textures: Parsing default configuration \"" + fileName + "\"");
               Config.dbg("Natural Textures: Valid only for textures from default resource pack");
            } else {
               Config.dbg("Natural Textures: Parsing configuration \"" + fileName + "\"");
            }

            int countTextures = 0;
            TextureAtlas textureMapBlocks = TextureUtils.getTextureMapBlocks();

            for(int i = 0; i < configLines.length; ++i) {
               String line = configLines[i].trim();
               if (!line.startsWith("#")) {
                  String[] strs = Config.tokenize(line, "=");
                  if (strs.length != 2) {
                     Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
                  } else {
                     String key = strs[0].trim();
                     String type = strs[1].trim();
                     TextureAtlasSprite ts = textureMapBlocks.getUploadedSprite("minecraft:block/" + key);
                     if (ts == null) {
                        Config.warn("Natural Textures: Texture not found: \"" + fileName + "\" line: " + line);
                     } else {
                        int tileNum = ts.getIndexInMap();
                        if (tileNum < 0) {
                           Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
                        } else {
                           if (defaultConfig && !Config.isFromDefaultResourcePack(new ResourceLocation("textures/block/" + key + ".png"))) {
                              return;
                           }

                           NaturalProperties props = new NaturalProperties(type);
                           if (props.isValid()) {
                              while(list.size() <= tileNum) {
                                 list.add((Object)null);
                              }

                              list.set(tileNum, props);
                              ++countTextures;
                           }
                        }
                     }
                  }
               }
            }

            propertiesByIndex = (NaturalProperties[])list.toArray(new NaturalProperties[list.size()]);
            if (countTextures > 0) {
               Config.dbg("NaturalTextures: " + countTextures);
            }
         } catch (FileNotFoundException var18) {
            Config.warn("NaturalTextures: configuration \"" + fileName + "\" not found");
            return;
         } catch (Exception var19) {
            var19.printStackTrace();
         }

      }
   }

   public static BakedQuad getNaturalTexture(BlockState stateIn, BlockPos blockPosIn, BakedQuad quad) {
      if (stateIn.m_60734_() instanceof WallBlock) {
         return quad;
      } else {
         TextureAtlasSprite sprite = quad.m_173410_();
         if (sprite == null) {
            return quad;
         } else {
            NaturalProperties nps = getNaturalProperties(sprite);
            if (nps == null) {
               return quad;
            } else {
               int side = ConnectedTextures.getSide(quad.m_111306_());
               int rand = Config.getRandom(blockPosIn, side);
               int rotate = 0;
               boolean flipU = false;
               if (nps.rotation > 1) {
                  rotate = rand & 3;
               }

               if (nps.rotation == 2) {
                  rotate = rotate / 2 * 2;
               }

               if (nps.flip) {
                  flipU = (rand & 4) != 0;
               }

               return nps.getQuad(quad, rotate, flipU);
            }
         }
      }
   }

   public static NaturalProperties getNaturalProperties(TextureAtlasSprite icon) {
      if (!(icon instanceof TextureAtlasSprite)) {
         return null;
      } else {
         int tileNum = icon.getIndexInMap();
         if (tileNum >= 0 && tileNum < propertiesByIndex.length) {
            NaturalProperties props = propertiesByIndex[tileNum];
            return props;
         } else {
            return null;
         }
      }
   }
}
