package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.optifine.config.Matches;
import net.optifine.model.BlockModelUtils;
import net.optifine.model.ListQuadsOverlay;
import net.optifine.render.RenderEnv;
import net.optifine.util.BiomeUtils;
import net.optifine.util.BlockUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.RandomUtils;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import net.optifine.util.TileEntityUtils;

public class ConnectedTextures {
   private static Map[] spriteQuadMaps = null;
   private static Map[] spriteQuadFullMaps = null;
   private static Map[][] spriteQuadCompactMaps = null;
   private static ConnectedProperties[][] blockProperties = null;
   private static ConnectedProperties[][] tileProperties = null;
   private static boolean multipass = false;
   protected static final int UNKNOWN = -1;
   protected static final int Y_NEG_DOWN = 0;
   protected static final int Y_POS_UP = 1;
   protected static final int Z_NEG_NORTH = 2;
   protected static final int Z_POS_SOUTH = 3;
   protected static final int X_NEG_WEST = 4;
   protected static final int X_POS_EAST = 5;
   private static final int Y_AXIS = 0;
   private static final int Z_AXIS = 1;
   private static final int X_AXIS = 2;
   public static final net.minecraft.world.level.block.state.BlockState AIR_DEFAULT_STATE = Blocks.f_50016_.m_49966_();
   private static net.minecraft.client.renderer.texture.TextureAtlasSprite emptySprite = null;
   public static net.minecraft.resources.ResourceLocation LOCATION_SPRITE_EMPTY = TextureUtils.LOCATION_SPRITE_EMPTY;
   private static final BlockDir[] SIDES_Y_NEG_DOWN = new BlockDir[]{BlockDir.WEST, BlockDir.EAST, BlockDir.NORTH, BlockDir.SOUTH};
   private static final BlockDir[] SIDES_Y_POS_UP = new BlockDir[]{BlockDir.WEST, BlockDir.EAST, BlockDir.SOUTH, BlockDir.NORTH};
   private static final BlockDir[] SIDES_Z_NEG_NORTH = new BlockDir[]{BlockDir.EAST, BlockDir.WEST, BlockDir.DOWN, BlockDir.UP};
   private static final BlockDir[] SIDES_Z_POS_SOUTH = new BlockDir[]{BlockDir.WEST, BlockDir.EAST, BlockDir.DOWN, BlockDir.UP};
   private static final BlockDir[] SIDES_X_NEG_WEST = new BlockDir[]{BlockDir.NORTH, BlockDir.SOUTH, BlockDir.DOWN, BlockDir.UP};
   private static final BlockDir[] SIDES_X_POS_EAST = new BlockDir[]{BlockDir.SOUTH, BlockDir.NORTH, BlockDir.DOWN, BlockDir.UP};
   private static final BlockDir[] EDGES_Y_NEG_DOWN = new BlockDir[]{BlockDir.NORTH_EAST, BlockDir.NORTH_WEST, BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST};
   private static final BlockDir[] EDGES_Y_POS_UP = new BlockDir[]{BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST, BlockDir.NORTH_EAST, BlockDir.NORTH_WEST};
   private static final BlockDir[] EDGES_Z_NEG_NORTH = new BlockDir[]{BlockDir.DOWN_WEST, BlockDir.DOWN_EAST, BlockDir.UP_WEST, BlockDir.UP_EAST};
   private static final BlockDir[] EDGES_Z_POS_SOUTH = new BlockDir[]{BlockDir.DOWN_EAST, BlockDir.DOWN_WEST, BlockDir.UP_EAST, BlockDir.UP_WEST};
   private static final BlockDir[] EDGES_X_NEG_WEST = new BlockDir[]{BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH, BlockDir.UP_SOUTH, BlockDir.UP_NORTH};
   private static final BlockDir[] EDGES_X_POS_EAST = new BlockDir[]{BlockDir.DOWN_NORTH, BlockDir.DOWN_SOUTH, BlockDir.UP_NORTH, BlockDir.UP_SOUTH};
   public static final net.minecraft.client.renderer.texture.TextureAtlasSprite SPRITE_DEFAULT = new net.minecraft.client.renderer.texture.TextureAtlasSprite(
      net.minecraft.client.renderer.texture.TextureAtlas.f_118259_, new net.minecraft.resources.ResourceLocation("default")
   );
   private static final RandomSource RANDOM = RandomUtils.makeThreadSafeRandomSource(0);

   public static net.minecraft.client.renderer.block.model.BakedQuad[] getConnectedTexture(
      BlockAndTintGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      net.minecraft.client.renderer.block.model.BakedQuad quad,
      RenderEnv renderEnv
   ) {
      net.minecraft.client.renderer.texture.TextureAtlasSprite spriteIn = quad.m_173410_();
      if (spriteIn == null) {
         return renderEnv.getArrayQuadsCtm(quad);
      } else if (skipConnectedTexture(blockAccess, blockState, blockPos, quad, renderEnv)) {
         quad = getQuad(emptySprite, quad);
         return renderEnv.getArrayQuadsCtm(quad);
      } else {
         net.minecraft.core.Direction side = quad.m_111306_();
         return getConnectedTextureMultiPass(blockAccess, blockState, blockPos, side, quad, renderEnv);
      }
   }

   private static boolean skipConnectedTexture(
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      net.minecraft.client.renderer.block.model.BakedQuad quad,
      RenderEnv renderEnv
   ) {
      Block block = blockState.m_60734_();
      if (block instanceof IronBarsBlock) {
         net.minecraft.core.Direction face = quad.m_111306_();
         if (face != net.minecraft.core.Direction.UP && face != net.minecraft.core.Direction.DOWN) {
            return false;
         }

         if (!quad.isFaceQuad()) {
            return false;
         }

         BlockPos posNeighbour = blockPos.m_121945_(quad.m_111306_());
         net.minecraft.world.level.block.state.BlockState stateNeighbour = blockAccess.m_8055_(posNeighbour);
         if (stateNeighbour.m_60734_() != block) {
            return false;
         }

         Block blockNeighbour = stateNeighbour.m_60734_();
         if (block instanceof StainedGlassPaneBlock && blockNeighbour instanceof StainedGlassPaneBlock) {
            net.minecraft.world.item.DyeColor color = ((StainedGlassPaneBlock)block).m_7988_();
            net.minecraft.world.item.DyeColor colorNeighbour = ((StainedGlassPaneBlock)blockNeighbour).m_7988_();
            if (color != colorNeighbour) {
               return false;
            }
         }

         double midX = (double)quad.getMidX();
         if (midX < 0.4) {
            if ((Boolean)stateNeighbour.m_61143_(CrossCollisionBlock.f_52312_)) {
               return true;
            }
         } else if (midX > 0.6) {
            if ((Boolean)stateNeighbour.m_61143_(CrossCollisionBlock.f_52310_)) {
               return true;
            }
         } else {
            double midZ = quad.getMidZ();
            if (midZ < 0.4) {
               if ((Boolean)stateNeighbour.m_61143_(CrossCollisionBlock.f_52309_)) {
                  return true;
               }
            } else {
               if (!(midZ > 0.6)) {
                  return true;
               }

               if ((Boolean)stateNeighbour.m_61143_(CrossCollisionBlock.f_52311_)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   protected static net.minecraft.client.renderer.block.model.BakedQuad[] getQuads(
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite, net.minecraft.client.renderer.block.model.BakedQuad quadIn, RenderEnv renderEnv
   ) {
      if (sprite == null) {
         return null;
      } else if (sprite == SPRITE_DEFAULT) {
         return renderEnv.getArrayQuadsCtm(quadIn);
      } else {
         net.minecraft.client.renderer.block.model.BakedQuad quad = getQuad(sprite, quadIn);
         return renderEnv.getArrayQuadsCtm(quad);
      }
   }

   private static synchronized net.minecraft.client.renderer.block.model.BakedQuad getQuad(
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite, net.minecraft.client.renderer.block.model.BakedQuad quadIn
   ) {
      if (spriteQuadMaps == null) {
         return quadIn;
      } else {
         int spriteIndex = sprite.getIndexInMap();
         if (spriteIndex >= 0 && spriteIndex < spriteQuadMaps.length) {
            Map quadMap = spriteQuadMaps[spriteIndex];
            if (quadMap == null) {
               quadMap = new IdentityHashMap(1);
               spriteQuadMaps[spriteIndex] = quadMap;
            }

            net.minecraft.client.renderer.block.model.BakedQuad quad = (net.minecraft.client.renderer.block.model.BakedQuad)quadMap.get(quadIn);
            if (quad == null) {
               quad = makeSpriteQuad(quadIn, sprite);
               quadMap.put(quadIn, quad);
            }

            return quad;
         } else {
            return quadIn;
         }
      }
   }

   private static synchronized net.minecraft.client.renderer.block.model.BakedQuad getQuadFull(
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite, net.minecraft.client.renderer.block.model.BakedQuad quadIn, int tintIndex
   ) {
      if (spriteQuadFullMaps == null) {
         return null;
      } else if (sprite == null) {
         return null;
      } else {
         int spriteIndex = sprite.getIndexInMap();
         if (spriteIndex >= 0 && spriteIndex < spriteQuadFullMaps.length) {
            Map quadMap = spriteQuadFullMaps[spriteIndex];
            if (quadMap == null) {
               quadMap = new EnumMap(net.minecraft.core.Direction.class);
               spriteQuadFullMaps[spriteIndex] = quadMap;
            }

            net.minecraft.core.Direction face = quadIn.m_111306_();
            net.minecraft.client.renderer.block.model.BakedQuad quad = (net.minecraft.client.renderer.block.model.BakedQuad)quadMap.get(face);
            if (quad == null) {
               quad = BlockModelUtils.makeBakedQuad(face, sprite, tintIndex);
               quadMap.put(face, quad);
            }

            return quad;
         } else {
            return null;
         }
      }
   }

   private static net.minecraft.client.renderer.block.model.BakedQuad makeSpriteQuad(
      net.minecraft.client.renderer.block.model.BakedQuad quad, net.minecraft.client.renderer.texture.TextureAtlasSprite sprite
   ) {
      int[] data = (int[])quad.m_111303_().clone();
      net.minecraft.client.renderer.texture.TextureAtlasSprite spriteFrom = quad.m_173410_();

      for (int i = 0; i < 4; i++) {
         fixVertex(data, i, spriteFrom, sprite);
      }

      return new net.minecraft.client.renderer.block.model.BakedQuad(data, quad.m_111305_(), quad.m_111306_(), sprite, quad.m_111307_());
   }

   private static void fixVertex(
      int[] data,
      int vertex,
      net.minecraft.client.renderer.texture.TextureAtlasSprite spriteFrom,
      net.minecraft.client.renderer.texture.TextureAtlasSprite spriteTo
   ) {
      int mul = data.length / 4;
      int pos = mul * vertex;
      float u = Float.intBitsToFloat(data[pos + 4]);
      float v = Float.intBitsToFloat(data[pos + 4 + 1]);
      double su16 = spriteFrom.getSpriteU16(u);
      double sv16 = spriteFrom.getSpriteV16(v);
      data[pos + 4] = Float.floatToRawIntBits(spriteTo.getInterpolatedU16(su16));
      data[pos + 4 + 1] = Float.floatToRawIntBits(spriteTo.getInterpolatedV16(sv16));
   }

   private static net.minecraft.client.renderer.block.model.BakedQuad[] getConnectedTextureMultiPass(
      BlockAndTintGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      net.minecraft.core.Direction side,
      net.minecraft.client.renderer.block.model.BakedQuad quad,
      RenderEnv renderEnv
   ) {
      net.minecraft.client.renderer.block.model.BakedQuad[] quads = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, quad, true, 0, renderEnv);
      if (!multipass) {
         return quads;
      } else if (quads.length == 1 && quads[0] == quad) {
         return quads;
      } else {
         List<net.minecraft.client.renderer.block.model.BakedQuad> listQuads = renderEnv.getListQuadsCtmMultipass(quads);

         for (int q = 0; q < listQuads.size(); q++) {
            net.minecraft.client.renderer.block.model.BakedQuad newQuad = (net.minecraft.client.renderer.block.model.BakedQuad)listQuads.get(q);
            net.minecraft.client.renderer.block.model.BakedQuad mpQuad = newQuad;

            for (int i = 0; i < 3; i++) {
               net.minecraft.client.renderer.block.model.BakedQuad[] newMpQuads = getConnectedTextureSingle(
                  blockAccess, blockState, blockPos, side, mpQuad, false, i + 1, renderEnv
               );
               if (newMpQuads.length != 1 || newMpQuads[0] == mpQuad) {
                  break;
               }

               mpQuad = newMpQuads[0];
            }

            listQuads.set(q, mpQuad);
         }

         for (int i = 0; i < quads.length; i++) {
            quads[i] = (net.minecraft.client.renderer.block.model.BakedQuad)listQuads.get(i);
         }

         return quads;
      }
   }

   public static net.minecraft.client.renderer.block.model.BakedQuad[] getConnectedTextureSingle(
      BlockAndTintGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      net.minecraft.core.Direction facing,
      net.minecraft.client.renderer.block.model.BakedQuad quad,
      boolean checkBlocks,
      int pass,
      RenderEnv renderEnv
   ) {
      Block block = blockState.m_60734_();
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon = quad.m_173410_();
      if (tileProperties != null) {
         int iconId = icon.getIndexInMap();
         if (iconId >= 0 && iconId < tileProperties.length) {
            ConnectedProperties[] cps = tileProperties[iconId];
            if (cps != null) {
               int side = getSide(facing);

               for (int i = 0; i < cps.length; i++) {
                  ConnectedProperties cp = cps[i];
                  if (cp != null && cp.matchesBlockId(blockState.getBlockId())) {
                     net.minecraft.client.renderer.block.model.BakedQuad[] newQuads = getConnectedTexture(
                        cp, blockAccess, blockState, blockPos, side, quad, pass, renderEnv
                     );
                     if (newQuads != null) {
                        return newQuads;
                     }
                  }
               }
            }
         }
      }

      if (blockProperties != null && checkBlocks) {
         int blockId = renderEnv.getBlockId();
         if (blockId >= 0 && blockId < blockProperties.length) {
            ConnectedProperties[] cps = blockProperties[blockId];
            if (cps != null) {
               int side = getSide(facing);

               for (int ix = 0; ix < cps.length; ix++) {
                  ConnectedProperties cp = cps[ix];
                  if (cp != null && cp.matchesIcon(icon)) {
                     net.minecraft.client.renderer.block.model.BakedQuad[] newQuads = getConnectedTexture(
                        cp, blockAccess, blockState, blockPos, side, quad, pass, renderEnv
                     );
                     if (newQuads != null) {
                        return newQuads;
                     }
                  }
               }
            }
         }
      }

      return renderEnv.getArrayQuadsCtm(quad);
   }

   public static int getSide(net.minecraft.core.Direction facing) {
      if (facing == null) {
         return -1;
      } else {
         switch (facing) {
            case DOWN:
               return 0;
            case UP:
               return 1;
            case EAST:
               return 5;
            case WEST:
               return 4;
            case NORTH:
               return 2;
            case SOUTH:
               return 3;
            default:
               return -1;
         }
      }
   }

   private static net.minecraft.core.Direction getFacing(int side) {
      switch (side) {
         case 0:
            return net.minecraft.core.Direction.DOWN;
         case 1:
            return net.minecraft.core.Direction.UP;
         case 2:
            return net.minecraft.core.Direction.NORTH;
         case 3:
            return net.minecraft.core.Direction.SOUTH;
         case 4:
            return net.minecraft.core.Direction.WEST;
         case 5:
            return net.minecraft.core.Direction.EAST;
         default:
            return net.minecraft.core.Direction.UP;
      }
   }

   private static net.minecraft.client.renderer.block.model.BakedQuad[] getConnectedTexture(
      ConnectedProperties cp,
      BlockAndTintGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int side,
      net.minecraft.client.renderer.block.model.BakedQuad quad,
      int pass,
      RenderEnv renderEnv
   ) {
      int vertAxis = 0;
      int metadata = blockState.getMetadata();
      Block block = blockState.m_60734_();
      if (block instanceof RotatedPillarBlock) {
         vertAxis = getPillarAxis(blockState);
      }

      if (!cp.matchesBlock(blockState.getBlockId(), metadata)) {
         return null;
      } else {
         if (side >= 0 && cp.faces != 63) {
            int sideCheck = side;
            if (vertAxis != 0) {
               sideCheck = fixSideByAxis(side, vertAxis);
            }

            if ((1 << sideCheck & cp.faces) == 0) {
               return null;
            }
         }

         int y = blockPos.m_123342_();
         if (cp.heights != null && !cp.heights.isInRange(y)) {
            return null;
         } else {
            if (cp.biomes != null) {
               Biome blockBiome = BiomeUtils.getBiome(blockAccess, blockPos);
               if (!cp.matchesBiome(blockBiome)) {
                  return null;
               }
            }

            if (cp.nbtName != null) {
               String name = TileEntityUtils.getTileEntityName(blockAccess, blockPos);
               if (!cp.nbtName.matchesValue(name)) {
                  return null;
               }
            }

            net.minecraft.client.renderer.texture.TextureAtlasSprite icon = quad.m_173410_();
            switch (cp.method) {
               case 1:
                  return getQuads(getConnectedTextureCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv), quad, renderEnv);
               case 2:
                  return getQuads(getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
               case 3:
                  return getQuads(getConnectedTextureTop(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
               case 4:
                  return getQuads(getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side), quad, renderEnv);
               case 5:
                  return getQuads(getConnectedTextureRepeat(cp, blockPos, side), quad, renderEnv);
               case 6:
                  return getQuads(getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
               case 7:
                  return getQuads(getConnectedTextureFixed(cp), quad, renderEnv);
               case 8:
                  return getQuads(getConnectedTextureHorizontalVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
               case 9:
                  return getQuads(getConnectedTextureVerticalHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
               case 10:
                  if (pass == 0) {
                     return getConnectedTextureCtmCompact(cp, blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
                  }
               default:
                  return null;
               case 11:
                  return getConnectedTextureOverlay(cp, blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
               case 12:
                  return getConnectedTextureOverlayFixed(cp, quad, renderEnv);
               case 13:
                  return getConnectedTextureOverlayRandom(cp, blockAccess, blockState, blockPos, side, quad, renderEnv);
               case 14:
                  return getConnectedTextureOverlayRepeat(cp, blockPos, side, quad, renderEnv);
               case 15:
                  return getConnectedTextureOverlayCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
            }
         }
      }
   }

   private static int fixSideByAxis(int side, int vertAxis) {
      switch (vertAxis) {
         case 0:
            return side;
         case 1:
            switch (side) {
               case 0:
                  return 2;
               case 1:
                  return 3;
               case 2:
                  return 1;
               case 3:
                  return 0;
               default:
                  return side;
            }
         case 2:
            switch (side) {
               case 0:
                  return 4;
               case 1:
                  return 5;
               case 2:
               case 3:
               default:
                  return side;
               case 4:
                  return 1;
               case 5:
                  return 0;
            }
         default:
            return side;
      }
   }

   private static int getPillarAxis(net.minecraft.world.level.block.state.BlockState blockState) {
      net.minecraft.core.Direction.Axis axis = (net.minecraft.core.Direction.Axis)blockState.m_61143_(RotatedPillarBlock.f_55923_);
      switch (axis) {
         case X:
            return 2;
         case Z:
            return 1;
         default:
            return 0;
      }
   }

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getConnectedTextureRandom(
      ConnectedProperties cp, BlockGetter blockAccess, net.minecraft.world.level.block.state.BlockState blockState, BlockPos blockPos, int side
   ) {
      if (cp.tileIcons.length == 1) {
         return cp.tileIcons[0];
      } else {
         int face = side / cp.symmetry * cp.symmetry;
         if (cp.linked) {
            BlockPos posDown = blockPos.m_7495_();

            for (net.minecraft.world.level.block.state.BlockState bsDown = blockAccess.m_8055_(posDown);
               bsDown.m_60734_() == blockState.m_60734_();
               bsDown = blockAccess.m_8055_(posDown)
            ) {
               blockPos = posDown;
               posDown = posDown.m_7495_();
               if (posDown.m_123342_() < 0) {
                  break;
               }
            }
         }

         int rand = Config.getRandom(blockPos, face) & 2147483647;

         for (int i = 0; i < cp.randomLoops; i++) {
            rand = Config.intHash(rand);
         }

         int index = 0;
         if (cp.weights == null) {
            index = rand % cp.tileIcons.length;
         } else {
            int randWeight = rand % cp.sumAllWeights;
            int[] sumWeights = cp.sumWeights;

            for (int i = 0; i < sumWeights.length; i++) {
               if (randWeight < sumWeights[i]) {
                  index = i;
                  break;
               }
            }
         }

         return cp.tileIcons[index];
      }
   }

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getConnectedTextureFixed(ConnectedProperties cp) {
      return cp.tileIcons[0];
   }

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getConnectedTextureRepeat(ConnectedProperties cp, BlockPos blockPos, int side) {
      if (cp.tileIcons.length == 1) {
         return cp.tileIcons[0];
      } else {
         int x = blockPos.m_123341_();
         int y = blockPos.m_123342_();
         int z = blockPos.m_123343_();
         int nx = 0;
         int ny = 0;
         switch (side) {
            case 0:
               nx = x;
               ny = -z - 1;
               break;
            case 1:
               nx = x;
               ny = z;
               break;
            case 2:
               nx = -x - 1;
               ny = -y;
               break;
            case 3:
               nx = x;
               ny = -y;
               break;
            case 4:
               nx = z;
               ny = -y;
               break;
            case 5:
               nx = -z - 1;
               ny = -y;
         }

         nx %= cp.width;
         ny %= cp.height;
         if (nx < 0) {
            nx += cp.width;
         }

         if (ny < 0) {
            ny += cp.height;
         }

         int index = ny * cp.width + nx;
         return cp.tileIcons[index];
      }
   }

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getConnectedTextureCtm(
      ConnectedProperties cp,
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int vertAxis,
      int side,
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon,
      int metadata,
      RenderEnv renderEnv
   ) {
      int index = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
      return cp.tileIcons[index];
   }

   private static synchronized net.minecraft.client.renderer.block.model.BakedQuad[] getConnectedTextureCtmCompact(
      ConnectedProperties cp,
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int vertAxis,
      int side,
      net.minecraft.client.renderer.block.model.BakedQuad quad,
      int metadata,
      RenderEnv renderEnv
   ) {
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon = quad.m_173410_();
      int index = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
      return ConnectedTexturesCompact.getConnectedTextureCtmCompact(index, cp, side, quad, renderEnv);
   }

   private static net.minecraft.client.renderer.block.model.BakedQuad[] getConnectedTextureOverlay(
      ConnectedProperties cp,
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int vertAxis,
      int side,
      net.minecraft.client.renderer.block.model.BakedQuad quad,
      int metadata,
      RenderEnv renderEnv
   ) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         net.minecraft.client.renderer.texture.TextureAtlasSprite icon = quad.m_173410_();
         BlockDir[] dirSides = getSideDirections(side, vertAxis);
         boolean[] sides = renderEnv.getBorderFlags();

         for (int i = 0; i < 4; i++) {
            sides[i] = isNeighbourOverlay(cp, blockAccess, blockState, dirSides[i].offset(blockPos), side, icon, metadata);
         }

         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object dirEdges;
         try {
            if (!sides[0] || !sides[1] || !sides[2] || !sides[3]) {
               if (sides[0] && sides[1] && sides[2]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[5], quad, cp.tintIndex), cp.tintBlockState);
                  return null;
               }

               if (sides[0] && sides[2] && sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[6], quad, cp.tintIndex), cp.tintBlockState);
                  return null;
               }

               if (sides[1] && sides[2] && sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[12], quad, cp.tintIndex), cp.tintBlockState);
                  return null;
               }

               if (sides[0] && sides[1] && sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[13], quad, cp.tintIndex), cp.tintBlockState);
                  return null;
               }

               BlockDir[] dirEdgesx = getEdgeDirections(side, vertAxis);
               boolean[] edges = renderEnv.getBorderFlags2();

               for (int i = 0; i < 4; i++) {
                  edges[i] = isNeighbourOverlay(cp, blockAccess, blockState, dirEdgesx[i].offset(blockPos), side, icon, metadata);
               }

               if (sides[1] && sides[2]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[3], quad, cp.tintIndex), cp.tintBlockState);
                  if (edges[3]) {
                     listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
                  }

                  return null;
               }

               if (sides[0] && sides[2]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[4], quad, cp.tintIndex), cp.tintBlockState);
                  if (edges[2]) {
                     listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
                  }

                  return null;
               }

               if (sides[1] && sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[10], quad, cp.tintIndex), cp.tintBlockState);
                  if (edges[1]) {
                     listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
                  }

                  return null;
               }

               if (sides[0] && sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[11], quad, cp.tintIndex), cp.tintBlockState);
                  if (edges[0]) {
                     listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
                  }

                  return null;
               }

               boolean[] sidesMatch = renderEnv.getBorderFlags3();

               for (int i = 0; i < 4; i++) {
                  sidesMatch[i] = isNeighbourMatching(cp, blockAccess, blockState, dirSides[i].offset(blockPos), side, icon, metadata);
               }

               if (sides[0]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[9], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (sides[1]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[7], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (sides[2]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[1], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[15], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (edges[0] && (sidesMatch[1] || sidesMatch[2]) && !sides[1] && !sides[2]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (edges[1] && (sidesMatch[0] || sidesMatch[2]) && !sides[0] && !sides[2]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (edges[2] && (sidesMatch[1] || sidesMatch[3]) && !sides[1] && !sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (edges[3] && (sidesMatch[0] || sidesMatch[3]) && !sides[0] && !sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
               }

               return null;
            }

            listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[8], quad, cp.tintIndex), cp.tintBlockState);
            dirEdges = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }
         }

         return (net.minecraft.client.renderer.block.model.BakedQuad[])dirEdges;
      }
   }

   private static net.minecraft.client.renderer.block.model.BakedQuad[] getConnectedTextureOverlayFixed(
      ConnectedProperties cp, net.minecraft.client.renderer.block.model.BakedQuad quad, RenderEnv renderEnv
   ) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var5;
         try {
            net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = getConnectedTextureFixed(cp);
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var5 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }
         }

         return (net.minecraft.client.renderer.block.model.BakedQuad[])var5;
      }
   }

   private static net.minecraft.client.renderer.block.model.BakedQuad[] getConnectedTextureOverlayRandom(
      ConnectedProperties cp,
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int side,
      net.minecraft.client.renderer.block.model.BakedQuad quad,
      RenderEnv renderEnv
   ) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var9;
         try {
            net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side);
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var9 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }
         }

         return (net.minecraft.client.renderer.block.model.BakedQuad[])var9;
      }
   }

   private static net.minecraft.client.renderer.block.model.BakedQuad[] getConnectedTextureOverlayRepeat(
      ConnectedProperties cp, BlockPos blockPos, int side, net.minecraft.client.renderer.block.model.BakedQuad quad, RenderEnv renderEnv
   ) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var7;
         try {
            net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = getConnectedTextureRepeat(cp, blockPos, side);
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var7 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }
         }

         return (net.minecraft.client.renderer.block.model.BakedQuad[])var7;
      }
   }

   private static net.minecraft.client.renderer.block.model.BakedQuad[] getConnectedTextureOverlayCtm(
      ConnectedProperties cp,
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int vertAxis,
      int side,
      net.minecraft.client.renderer.block.model.BakedQuad quad,
      int metadata,
      RenderEnv renderEnv
   ) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var11;
         try {
            net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = getConnectedTextureCtm(
               cp, blockAccess, blockState, blockPos, vertAxis, side, quad.m_173410_(), metadata, renderEnv
            );
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var11 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }
         }

         return (net.minecraft.client.renderer.block.model.BakedQuad[])var11;
      }
   }

   private static BlockDir[] getSideDirections(int side, int vertAxis) {
      switch (side) {
         case 0:
            return SIDES_Y_NEG_DOWN;
         case 1:
            return SIDES_Y_POS_UP;
         case 2:
            return SIDES_Z_NEG_NORTH;
         case 3:
            return SIDES_Z_POS_SOUTH;
         case 4:
            return SIDES_X_NEG_WEST;
         case 5:
            return SIDES_X_POS_EAST;
         default:
            throw new IllegalArgumentException("Unknown side: " + side);
      }
   }

   private static BlockDir[] getEdgeDirections(int side, int vertAxis) {
      switch (side) {
         case 0:
            return EDGES_Y_NEG_DOWN;
         case 1:
            return EDGES_Y_POS_UP;
         case 2:
            return EDGES_Z_NEG_NORTH;
         case 3:
            return EDGES_Z_POS_SOUTH;
         case 4:
            return EDGES_X_NEG_WEST;
         case 5:
            return EDGES_X_POS_EAST;
         default:
            throw new IllegalArgumentException("Unknown side: " + side);
      }
   }

   protected static Map[][] getSpriteQuadCompactMaps() {
      return spriteQuadCompactMaps;
   }

   private static int getConnectedTextureCtmIndex(
      ConnectedProperties cp,
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int vertAxis,
      int side,
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon,
      int metadata,
      RenderEnv renderEnv
   ) {
      boolean[] borders = renderEnv.getBorderFlags();
      switch (side) {
         case 0:
            borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
            borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
            borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
            borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
            if (cp.innerSeams) {
               BlockPos posFront = blockPos.m_7495_();
               borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122024_(), side, icon, metadata);
               borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122029_(), side, icon, metadata);
               borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122012_(), side, icon, metadata);
               borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122019_(), side, icon, metadata);
            }
            break;
         case 1:
            borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
            borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
            borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
            borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
            if (cp.innerSeams) {
               BlockPos posFront = blockPos.m_7494_();
               borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122024_(), side, icon, metadata);
               borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122029_(), side, icon, metadata);
               borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122019_(), side, icon, metadata);
               borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122012_(), side, icon, metadata);
            }
            break;
         case 2:
            borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
            borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
            borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
            borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
            if (cp.innerSeams) {
               BlockPos posFront = blockPos.m_122012_();
               borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122029_(), side, icon, metadata);
               borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122024_(), side, icon, metadata);
               borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.m_7495_(), side, icon, metadata);
               borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.m_7494_(), side, icon, metadata);
            }
            break;
         case 3:
            borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
            borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
            borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
            borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
            if (cp.innerSeams) {
               BlockPos posFront = blockPos.m_122019_();
               borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122024_(), side, icon, metadata);
               borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122029_(), side, icon, metadata);
               borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.m_7495_(), side, icon, metadata);
               borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.m_7494_(), side, icon, metadata);
            }
            break;
         case 4:
            borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
            borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
            borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
            borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
            if (cp.innerSeams) {
               BlockPos posFront = blockPos.m_122024_();
               borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122012_(), side, icon, metadata);
               borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122019_(), side, icon, metadata);
               borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.m_7495_(), side, icon, metadata);
               borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.m_7494_(), side, icon, metadata);
            }
            break;
         case 5:
            borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
            borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
            borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
            borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
            if (cp.innerSeams) {
               BlockPos posFront = blockPos.m_122029_();
               borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122019_(), side, icon, metadata);
               borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.m_122012_(), side, icon, metadata);
               borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.m_7495_(), side, icon, metadata);
               borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.m_7494_(), side, icon, metadata);
            }
      }

      int index = 0;
      if (borders[0] & !borders[1] & !borders[2] & !borders[3]) {
         index = 3;
      } else if (!borders[0] & borders[1] & !borders[2] & !borders[3]) {
         index = 1;
      } else if (!borders[0] & !borders[1] & borders[2] & !borders[3]) {
         index = 12;
      } else if (!borders[0] & !borders[1] & !borders[2] & borders[3]) {
         index = 36;
      } else if (borders[0] & borders[1] & !borders[2] & !borders[3]) {
         index = 2;
      } else if (!borders[0] & !borders[1] & borders[2] & borders[3]) {
         index = 24;
      } else if (borders[0] & !borders[1] & borders[2] & !borders[3]) {
         index = 15;
      } else if (borders[0] & !borders[1] & !borders[2] & borders[3]) {
         index = 39;
      } else if (!borders[0] & borders[1] & borders[2] & !borders[3]) {
         index = 13;
      } else if (!borders[0] & borders[1] & !borders[2] & borders[3]) {
         index = 37;
      } else if (!borders[0] & borders[1] & borders[2] & borders[3]) {
         index = 25;
      } else if (borders[0] & !borders[1] & borders[2] & borders[3]) {
         index = 27;
      } else if (borders[0] & borders[1] & !borders[2] & borders[3]) {
         index = 38;
      } else if (borders[0] & borders[1] & borders[2] & !borders[3]) {
         index = 14;
      } else if (borders[0] & borders[1] & borders[2] & borders[3]) {
         index = 26;
      }

      if (index == 0) {
         return index;
      } else if (!Config.isConnectedTexturesFancy()) {
         return index;
      } else {
         switch (side) {
            case 0:
               borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_().m_122012_(), side, icon, metadata);
               borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_().m_122012_(), side, icon, metadata);
               borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_().m_122019_(), side, icon, metadata);
               borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_().m_122019_(), side, icon, metadata);
               if (cp.innerSeams) {
                  BlockPos posFront = blockPos.m_7495_();
                  borders[0] = borders[0] || isNeighbour(cp, blockAccess, blockState, posFront.m_122029_().m_122012_(), side, icon, metadata);
                  borders[1] = borders[1] || isNeighbour(cp, blockAccess, blockState, posFront.m_122024_().m_122012_(), side, icon, metadata);
                  borders[2] = borders[2] || isNeighbour(cp, blockAccess, blockState, posFront.m_122029_().m_122019_(), side, icon, metadata);
                  borders[3] = borders[3] || isNeighbour(cp, blockAccess, blockState, posFront.m_122024_().m_122019_(), side, icon, metadata);
               }
               break;
            case 1:
               borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_().m_122019_(), side, icon, metadata);
               borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_().m_122019_(), side, icon, metadata);
               borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_().m_122012_(), side, icon, metadata);
               borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_().m_122012_(), side, icon, metadata);
               if (cp.innerSeams) {
                  BlockPos posFront = blockPos.m_7494_();
                  borders[0] = borders[0] || isNeighbour(cp, blockAccess, blockState, posFront.m_122029_().m_122019_(), side, icon, metadata);
                  borders[1] = borders[1] || isNeighbour(cp, blockAccess, blockState, posFront.m_122024_().m_122019_(), side, icon, metadata);
                  borders[2] = borders[2] || isNeighbour(cp, blockAccess, blockState, posFront.m_122029_().m_122012_(), side, icon, metadata);
                  borders[3] = borders[3] || isNeighbour(cp, blockAccess, blockState, posFront.m_122024_().m_122012_(), side, icon, metadata);
               }
               break;
            case 2:
               borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_().m_7495_(), side, icon, metadata);
               borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_().m_7495_(), side, icon, metadata);
               borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_().m_7494_(), side, icon, metadata);
               borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_().m_7494_(), side, icon, metadata);
               if (cp.innerSeams) {
                  BlockPos posFront = blockPos.m_122012_();
                  borders[0] = borders[0] || isNeighbour(cp, blockAccess, blockState, posFront.m_122024_().m_7495_(), side, icon, metadata);
                  borders[1] = borders[1] || isNeighbour(cp, blockAccess, blockState, posFront.m_122029_().m_7495_(), side, icon, metadata);
                  borders[2] = borders[2] || isNeighbour(cp, blockAccess, blockState, posFront.m_122024_().m_7494_(), side, icon, metadata);
                  borders[3] = borders[3] || isNeighbour(cp, blockAccess, blockState, posFront.m_122029_().m_7494_(), side, icon, metadata);
               }
               break;
            case 3:
               borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_().m_7495_(), side, icon, metadata);
               borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_().m_7495_(), side, icon, metadata);
               borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_().m_7494_(), side, icon, metadata);
               borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_().m_7494_(), side, icon, metadata);
               if (cp.innerSeams) {
                  BlockPos posFront = blockPos.m_122019_();
                  borders[0] = borders[0] || isNeighbour(cp, blockAccess, blockState, posFront.m_122029_().m_7495_(), side, icon, metadata);
                  borders[1] = borders[1] || isNeighbour(cp, blockAccess, blockState, posFront.m_122024_().m_7495_(), side, icon, metadata);
                  borders[2] = borders[2] || isNeighbour(cp, blockAccess, blockState, posFront.m_122029_().m_7494_(), side, icon, metadata);
                  borders[3] = borders[3] || isNeighbour(cp, blockAccess, blockState, posFront.m_122024_().m_7494_(), side, icon, metadata);
               }
               break;
            case 4:
               borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_().m_122019_(), side, icon, metadata);
               borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_().m_122012_(), side, icon, metadata);
               borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_().m_122019_(), side, icon, metadata);
               borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_().m_122012_(), side, icon, metadata);
               if (cp.innerSeams) {
                  BlockPos posFront = blockPos.m_122024_();
                  borders[0] = borders[0] || isNeighbour(cp, blockAccess, blockState, posFront.m_7495_().m_122019_(), side, icon, metadata);
                  borders[1] = borders[1] || isNeighbour(cp, blockAccess, blockState, posFront.m_7495_().m_122012_(), side, icon, metadata);
                  borders[2] = borders[2] || isNeighbour(cp, blockAccess, blockState, posFront.m_7494_().m_122019_(), side, icon, metadata);
                  borders[3] = borders[3] || isNeighbour(cp, blockAccess, blockState, posFront.m_7494_().m_122012_(), side, icon, metadata);
               }
               break;
            case 5:
               borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_().m_122012_(), side, icon, metadata);
               borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_().m_122019_(), side, icon, metadata);
               borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_().m_122012_(), side, icon, metadata);
               borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_().m_122019_(), side, icon, metadata);
               if (cp.innerSeams) {
                  BlockPos posFront = blockPos.m_122029_();
                  borders[0] = borders[0] || isNeighbour(cp, blockAccess, blockState, posFront.m_7495_().m_122012_(), side, icon, metadata);
                  borders[1] = borders[1] || isNeighbour(cp, blockAccess, blockState, posFront.m_7495_().m_122019_(), side, icon, metadata);
                  borders[2] = borders[2] || isNeighbour(cp, blockAccess, blockState, posFront.m_7494_().m_122012_(), side, icon, metadata);
                  borders[3] = borders[3] || isNeighbour(cp, blockAccess, blockState, posFront.m_7494_().m_122019_(), side, icon, metadata);
               }
         }

         if (index == 13 && borders[0]) {
            index = 4;
         } else if (index == 15 && borders[1]) {
            index = 5;
         } else if (index == 37 && borders[2]) {
            index = 16;
         } else if (index == 39 && borders[3]) {
            index = 17;
         } else if (index == 14 && borders[0] && borders[1]) {
            index = 7;
         } else if (index == 25 && borders[0] && borders[2]) {
            index = 6;
         } else if (index == 27 && borders[3] && borders[1]) {
            index = 19;
         } else if (index == 38 && borders[3] && borders[2]) {
            index = 18;
         } else if (index == 14 && !borders[0] && borders[1]) {
            index = 31;
         } else if (index == 25 && borders[0] && !borders[2]) {
            index = 30;
         } else if (index == 27 && !borders[3] && borders[1]) {
            index = 41;
         } else if (index == 38 && borders[3] && !borders[2]) {
            index = 40;
         } else if (index == 14 && borders[0] && !borders[1]) {
            index = 29;
         } else if (index == 25 && !borders[0] && borders[2]) {
            index = 28;
         } else if (index == 27 && borders[3] && !borders[1]) {
            index = 43;
         } else if (index == 38 && !borders[3] && borders[2]) {
            index = 42;
         } else if (index == 26 && borders[0] && borders[1] && borders[2] && borders[3]) {
            index = 46;
         } else if (index == 26 && !borders[0] && borders[1] && borders[2] && borders[3]) {
            index = 9;
         } else if (index == 26 && borders[0] && !borders[1] && borders[2] && borders[3]) {
            index = 21;
         } else if (index == 26 && borders[0] && borders[1] && !borders[2] && borders[3]) {
            index = 8;
         } else if (index == 26 && borders[0] && borders[1] && borders[2] && !borders[3]) {
            index = 20;
         } else if (index == 26 && borders[0] && borders[1] && !borders[2] && !borders[3]) {
            index = 11;
         } else if (index == 26 && !borders[0] && !borders[1] && borders[2] && borders[3]) {
            index = 22;
         } else if (index == 26 && !borders[0] && borders[1] && !borders[2] && borders[3]) {
            index = 23;
         } else if (index == 26 && borders[0] && !borders[1] && borders[2] && !borders[3]) {
            index = 10;
         } else if (index == 26 && borders[0] && !borders[1] && !borders[2] && borders[3]) {
            index = 34;
         } else if (index == 26 && !borders[0] && borders[1] && borders[2] && !borders[3]) {
            index = 35;
         } else if (index == 26 && borders[0] && !borders[1] && !borders[2] && !borders[3]) {
            index = 32;
         } else if (index == 26 && !borders[0] && borders[1] && !borders[2] && !borders[3]) {
            index = 33;
         } else if (index == 26 && !borders[0] && !borders[1] && borders[2] && !borders[3]) {
            index = 44;
         } else if (index == 26 && !borders[0] && !borders[1] && !borders[2] && borders[3]) {
            index = 45;
         }

         return index;
      }
   }

   private static void switchValues(int ix1, int ix2, boolean[] arr) {
      boolean prev1 = arr[ix1];
      arr[ix1] = arr[ix2];
      arr[ix2] = prev1;
   }

   private static boolean isNeighbourOverlay(
      ConnectedProperties cp,
      BlockGetter worldReader,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int side,
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon,
      int metadata
   ) {
      net.minecraft.world.level.block.state.BlockState neighbourState = worldReader.m_8055_(blockPos);
      if (!isFullCubeModel(neighbourState, worldReader, blockPos)) {
         return false;
      } else if (cp.connectBlocks != null && !Matches.block(neighbourState.getBlockId(), neighbourState.getMetadata(), cp.connectBlocks)) {
         return false;
      } else {
         if (cp.connectTileIcons != null) {
            net.minecraft.client.renderer.texture.TextureAtlasSprite neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
            if (!Config.isSameOne(neighbourIcon, cp.connectTileIcons)) {
               return false;
            }
         }

         BlockPos posNeighbourStateAbove = blockPos.m_121945_(getFacing(side));
         net.minecraft.world.level.block.state.BlockState neighbourStateAbove = worldReader.m_8055_(posNeighbourStateAbove);
         if (neighbourStateAbove.m_60804_(worldReader, posNeighbourStateAbove)) {
            return false;
         } else {
            return side == 1 && neighbourStateAbove.m_60734_() == Blocks.f_50125_
               ? false
               : !isNeighbour(cp, worldReader, blockState, blockPos, neighbourState, side, icon, metadata);
         }
      }
   }

   private static boolean isFullCubeModel(net.minecraft.world.level.block.state.BlockState state, BlockGetter blockReader, BlockPos pos) {
      if (BlockUtils.isFullCube(state, blockReader, pos)) {
         return true;
      } else {
         Block block = state.m_60734_();
         return block == Blocks.f_50058_ ? true : block instanceof StainedGlassBlock;
      }
   }

   private static boolean isNeighbourMatching(
      ConnectedProperties cp,
      BlockGetter worldReader,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int side,
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon,
      int metadata
   ) {
      net.minecraft.world.level.block.state.BlockState neighbourState = worldReader.m_8055_(blockPos);
      if (neighbourState == AIR_DEFAULT_STATE) {
         return false;
      } else if (cp.matchBlocks != null && !cp.matchesBlock(neighbourState.getBlockId(), neighbourState.getMetadata())) {
         return false;
      } else {
         if (cp.matchTileIcons != null) {
            net.minecraft.client.renderer.texture.TextureAtlasSprite neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
            if (neighbourIcon != icon) {
               return false;
            }
         }

         BlockPos posNeighbourAbove = blockPos.m_121945_(getFacing(side));
         net.minecraft.world.level.block.state.BlockState neighbourStateAbove = worldReader.m_8055_(posNeighbourAbove);
         return neighbourStateAbove.m_60804_(worldReader, posNeighbourAbove) ? false : side != 1 || neighbourStateAbove.m_60734_() != Blocks.f_50125_;
      }
   }

   private static boolean isNeighbour(
      ConnectedProperties cp,
      BlockGetter worldReader,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int side,
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon,
      int metadata
   ) {
      net.minecraft.world.level.block.state.BlockState neighbourState = worldReader.m_8055_(blockPos);
      return isNeighbour(cp, worldReader, blockState, blockPos, neighbourState, side, icon, metadata);
   }

   private static boolean isNeighbour(
      ConnectedProperties cp,
      BlockGetter worldReader,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      net.minecraft.world.level.block.state.BlockState neighbourState,
      int side,
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon,
      int metadata
   ) {
      if (blockState == neighbourState) {
         return true;
      } else if (cp.connect == 2) {
         if (neighbourState == null) {
            return false;
         } else if (neighbourState == AIR_DEFAULT_STATE) {
            return false;
         } else {
            net.minecraft.client.renderer.texture.TextureAtlasSprite neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
            return neighbourIcon == icon;
         }
      } else if (cp.connect == 1) {
         Block block = blockState.m_60734_();
         Block neighbourBlock = neighbourState.m_60734_();
         return neighbourBlock == block;
      } else {
         return false;
      }
   }

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getNeighbourIcon(
      BlockGetter worldReader,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      net.minecraft.world.level.block.state.BlockState neighbourState,
      int side
   ) {
      net.minecraft.client.resources.model.BakedModel model = Minecraft.m_91087_().m_91289_().m_110907_().m_110893_(neighbourState);
      if (model == null) {
         return null;
      } else {
         net.minecraft.core.Direction facing = getFacing(side);
         List quads = model.m_213637_(neighbourState, facing, RANDOM);
         if (quads == null) {
            return null;
         } else {
            if (Config.isBetterGrass()) {
               quads = BetterGrass.getFaceQuads(worldReader, neighbourState, blockPos, facing, quads);
            }

            if (quads.size() > 0) {
               net.minecraft.client.renderer.block.model.BakedQuad quad = (net.minecraft.client.renderer.block.model.BakedQuad)quads.get(0);
               return quad.m_173410_();
            } else {
               List quadsGeneral = model.m_213637_(neighbourState, null, RANDOM);
               if (quadsGeneral == null) {
                  return null;
               } else {
                  for (int i = 0; i < quadsGeneral.size(); i++) {
                     net.minecraft.client.renderer.block.model.BakedQuad quad = (net.minecraft.client.renderer.block.model.BakedQuad)quadsGeneral.get(i);
                     if (quad.m_111306_() == facing) {
                        return quad.m_173410_();
                     }
                  }

                  return null;
               }
            }
         }
      }
   }

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getConnectedTextureHorizontal(
      ConnectedProperties cp,
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int vertAxis,
      int side,
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon,
      int metadata
   ) {
      boolean left;
      boolean right;
      left = false;
      right = false;
      label46:
      switch (vertAxis) {
         case 0:
            switch (side) {
               case 0:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
                  break label46;
               case 1:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
                  break label46;
               case 2:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
                  break label46;
               case 3:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
                  break label46;
               case 4:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
                  break label46;
               case 5:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
               default:
                  break label46;
            }
         case 1:
            switch (side) {
               case 0:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
                  break label46;
               case 1:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
                  break label46;
               case 2:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
                  break label46;
               case 3:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
                  break label46;
               case 4:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
                  break label46;
               case 5:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
               default:
                  break label46;
            }
         case 2:
            switch (side) {
               case 0:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
                  break;
               case 1:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
                  break;
               case 2:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
                  break;
               case 3:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
                  break;
               case 4:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
                  break;
               case 5:
                  left = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
                  right = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
            }
      }

      int index = 3;
      byte var11;
      if (left) {
         if (right) {
            var11 = 1;
         } else {
            var11 = 2;
         }
      } else if (right) {
         var11 = 0;
      } else {
         var11 = 3;
      }

      return cp.tileIcons[var11];
   }

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getConnectedTextureVertical(
      ConnectedProperties cp,
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int vertAxis,
      int side,
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon,
      int metadata
   ) {
      boolean bottom = false;
      boolean top = false;
      switch (vertAxis) {
         case 0:
            if (side == 1) {
               bottom = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
               top = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
            } else if (side == 0) {
               bottom = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
               top = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
            } else {
               bottom = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
               top = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
            }
            break;
         case 1:
            if (side == 3) {
               bottom = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
               top = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
            } else if (side == 2) {
               bottom = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
               top = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
            } else {
               bottom = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
               top = isNeighbour(cp, blockAccess, blockState, blockPos.m_122012_(), side, icon, metadata);
            }
            break;
         case 2:
            if (side == 5) {
               bottom = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
               top = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
            } else if (side == 4) {
               bottom = isNeighbour(cp, blockAccess, blockState, blockPos.m_7495_(), side, icon, metadata);
               top = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
            } else {
               bottom = isNeighbour(cp, blockAccess, blockState, blockPos.m_122024_(), side, icon, metadata);
               top = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
            }
      }

      int index = 3;
      byte var11;
      if (bottom) {
         if (top) {
            var11 = 1;
         } else {
            var11 = 2;
         }
      } else if (top) {
         var11 = 0;
      } else {
         var11 = 3;
      }

      return cp.tileIcons[var11];
   }

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getConnectedTextureHorizontalVertical(
      ConnectedProperties cp,
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int vertAxis,
      int side,
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon,
      int metadata
   ) {
      net.minecraft.client.renderer.texture.TextureAtlasSprite[] tileIcons = cp.tileIcons;
      net.minecraft.client.renderer.texture.TextureAtlasSprite iconH = getConnectedTextureHorizontal(
         cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata
      );
      if (iconH != null && iconH != icon && iconH != tileIcons[3]) {
         return iconH;
      } else {
         net.minecraft.client.renderer.texture.TextureAtlasSprite iconV = getConnectedTextureVertical(
            cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata
         );
         if (iconV == tileIcons[0]) {
            return tileIcons[4];
         } else if (iconV == tileIcons[1]) {
            return tileIcons[5];
         } else {
            return iconV == tileIcons[2] ? tileIcons[6] : iconV;
         }
      }
   }

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getConnectedTextureVerticalHorizontal(
      ConnectedProperties cp,
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int vertAxis,
      int side,
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon,
      int metadata
   ) {
      net.minecraft.client.renderer.texture.TextureAtlasSprite[] tileIcons = cp.tileIcons;
      net.minecraft.client.renderer.texture.TextureAtlasSprite iconV = getConnectedTextureVertical(
         cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata
      );
      if (iconV != null && iconV != icon && iconV != tileIcons[3]) {
         return iconV;
      } else {
         net.minecraft.client.renderer.texture.TextureAtlasSprite iconH = getConnectedTextureHorizontal(
            cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata
         );
         if (iconH == tileIcons[0]) {
            return tileIcons[4];
         } else if (iconH == tileIcons[1]) {
            return tileIcons[5];
         } else {
            return iconH == tileIcons[2] ? tileIcons[6] : iconH;
         }
      }
   }

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getConnectedTextureTop(
      ConnectedProperties cp,
      BlockGetter blockAccess,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      int vertAxis,
      int side,
      net.minecraft.client.renderer.texture.TextureAtlasSprite icon,
      int metadata
   ) {
      boolean top = false;
      switch (vertAxis) {
         case 0:
            if (side == 1 || side == 0) {
               return null;
            }

            top = isNeighbour(cp, blockAccess, blockState, blockPos.m_7494_(), side, icon, metadata);
            break;
         case 1:
            if (side == 3 || side == 2) {
               return null;
            }

            top = isNeighbour(cp, blockAccess, blockState, blockPos.m_122019_(), side, icon, metadata);
            break;
         case 2:
            if (side == 5 || side == 4) {
               return null;
            }

            top = isNeighbour(cp, blockAccess, blockState, blockPos.m_122029_(), side, icon, metadata);
      }

      return top ? cp.tileIcons[0] : null;
   }

   public static void updateIcons(net.minecraft.client.renderer.texture.TextureAtlas textureMap) {
      blockProperties = null;
      tileProperties = null;
      spriteQuadMaps = null;
      spriteQuadCompactMaps = null;
      if (Config.isConnectedTextures()) {
         PackResources[] rps = Config.getResourcePacks();

         for (int i = rps.length - 1; i >= 0; i--) {
            PackResources rp = rps[i];
            updateIcons(textureMap, rp);
         }

         updateIcons(textureMap, Config.getDefaultResourcePack());
         emptySprite = textureMap.registerSprite(LOCATION_SPRITE_EMPTY);
         spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
         spriteQuadFullMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
         spriteQuadCompactMaps = new Map[textureMap.getCountRegisteredSprites() + 1][];
         if (blockProperties.length <= 0) {
            blockProperties = null;
         }

         if (tileProperties.length <= 0) {
            tileProperties = null;
         }
      }
   }

   public static void updateIcons(net.minecraft.client.renderer.texture.TextureAtlas textureMap, PackResources rp) {
      String[] names = ResUtils.collectFiles(rp, "optifine/ctm/", ".properties", getDefaultCtmPaths());
      Arrays.sort(names);
      List tileList = makePropertyList(tileProperties);
      List blockList = makePropertyList(blockProperties);

      for (int i = 0; i < names.length; i++) {
         String name = names[i];
         Config.dbg("ConnectedTextures: " + name);

         try {
            net.minecraft.resources.ResourceLocation locFile = new net.minecraft.resources.ResourceLocation(name);
            InputStream in = Config.getResourceStream(rp, PackType.CLIENT_RESOURCES, locFile);
            if (in == null) {
               Config.warn("ConnectedTextures file not found: " + name);
            } else {
               Properties props = new PropertiesOrdered();
               props.load(in);
               in.close();
               ConnectedProperties cp = new ConnectedProperties(props, name);
               if (cp.isValid(name)) {
                  cp.updateIcons(textureMap);
                  addToTileList(cp, tileList);
                  addToBlockList(cp, blockList);
               }
            }
         } catch (FileNotFoundException var11) {
            Config.warn("ConnectedTextures file not found: " + name);
         } catch (Exception var12) {
            var12.printStackTrace();
         }
      }

      blockProperties = propertyListToArray(blockList);
      tileProperties = propertyListToArray(tileList);
      multipass = detectMultipass();
      Config.dbg("Multipass connected textures: " + multipass);
   }

   public static void refreshIcons(net.minecraft.client.renderer.texture.TextureAtlas textureMap) {
      refreshIcons(blockProperties, textureMap);
      refreshIcons(tileProperties, textureMap);
      emptySprite = getSprite(textureMap, LOCATION_SPRITE_EMPTY);
   }

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getSprite(
      net.minecraft.client.renderer.texture.TextureAtlas textureMap, net.minecraft.resources.ResourceLocation loc
   ) {
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = textureMap.m_118316_(loc);
      if (sprite == null || net.minecraft.client.renderer.texture.MissingTextureAtlasSprite.isMisingSprite(sprite)) {
         Config.warn("Missing CTM sprite: " + loc);
      }

      return sprite;
   }

   private static void refreshIcons(ConnectedProperties[][] propertiesArray, net.minecraft.client.renderer.texture.TextureAtlas textureMap) {
      if (propertiesArray != null) {
         for (int i = 0; i < propertiesArray.length; i++) {
            ConnectedProperties[] properties = propertiesArray[i];
            if (properties != null) {
               for (int c = 0; c < properties.length; c++) {
                  ConnectedProperties cp = properties[c];
                  if (cp != null) {
                     cp.refreshIcons(textureMap);
                  }
               }
            }
         }
      }
   }

   private static List makePropertyList(ConnectedProperties[][] propsArr) {
      List list = new ArrayList();
      if (propsArr != null) {
         for (int i = 0; i < propsArr.length; i++) {
            ConnectedProperties[] props = propsArr[i];
            List propList = null;
            if (props != null) {
               propList = new ArrayList(Arrays.asList(props));
            }

            list.add(propList);
         }
      }

      return list;
   }

   private static boolean detectMultipass() {
      List propList = new ArrayList();

      for (int i = 0; i < tileProperties.length; i++) {
         ConnectedProperties[] cps = tileProperties[i];
         if (cps != null) {
            propList.addAll(Arrays.asList(cps));
         }
      }

      for (int ix = 0; ix < blockProperties.length; ix++) {
         ConnectedProperties[] cps = blockProperties[ix];
         if (cps != null) {
            propList.addAll(Arrays.asList(cps));
         }
      }

      ConnectedProperties[] props = (ConnectedProperties[])propList.toArray(new ConnectedProperties[propList.size()]);
      Set matchIconSet = new HashSet();
      Set tileIconSet = new HashSet();

      for (int ixx = 0; ixx < props.length; ixx++) {
         ConnectedProperties cp = props[ixx];
         if (cp.matchTileIcons != null) {
            matchIconSet.addAll(Arrays.asList(cp.matchTileIcons));
         }

         if (cp.tileIcons != null) {
            tileIconSet.addAll(Arrays.asList(cp.tileIcons));
         }
      }

      matchIconSet.retainAll(tileIconSet);
      return !matchIconSet.isEmpty();
   }

   private static ConnectedProperties[][] propertyListToArray(List list) {
      ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];

      for (int i = 0; i < list.size(); i++) {
         List subList = (List)list.get(i);
         if (subList != null) {
            ConnectedProperties[] subArr = (ConnectedProperties[])subList.toArray(new ConnectedProperties[subList.size()]);
            propArr[i] = subArr;
         }
      }

      return propArr;
   }

   private static void addToTileList(ConnectedProperties cp, List tileList) {
      if (cp.matchTileIcons != null) {
         for (int i = 0; i < cp.matchTileIcons.length; i++) {
            net.minecraft.client.renderer.texture.TextureAtlasSprite icon = cp.matchTileIcons[i];
            if (!(icon instanceof net.minecraft.client.renderer.texture.TextureAtlasSprite)) {
               Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + icon + ", name: " + icon.getName());
            } else {
               int tileId = icon.getIndexInMap();
               if (tileId < 0) {
                  Config.warn("Invalid tile ID: " + tileId + ", icon: " + icon.getName());
               } else {
                  addToList(cp, tileList, tileId);
               }
            }
         }
      }
   }

   private static void addToBlockList(ConnectedProperties cp, List blockList) {
      if (cp.matchBlocks != null) {
         for (int i = 0; i < cp.matchBlocks.length; i++) {
            int blockId = cp.matchBlocks[i].getBlockId();
            if (blockId < 0) {
               Config.warn("Invalid block ID: " + blockId);
            } else {
               addToList(cp, blockList, blockId);
            }
         }
      }
   }

   private static void addToList(ConnectedProperties cp, List list, int id) {
      while (id >= list.size()) {
         list.add(null);
      }

      List subList = (List)list.get(id);
      if (subList == null) {
         subList = new ArrayList();
         list.set(id, subList);
      }

      subList.add(cp);
   }

   private static String[] getDefaultCtmPaths() {
      List list = new ArrayList();
      addDefaultLocation(list, "textures/block/glass.png", "20_glass/glass.properties");
      addDefaultLocation(list, "textures/block/glass.png", "20_glass/glass_pane.properties");
      addDefaultLocation(list, "textures/block/tinted_glass.png", "21_tinted_glass/tinted_glass.properties");
      addDefaultLocation(list, "textures/block/bookshelf.png", "30_bookshelf/bookshelf.properties");
      addDefaultLocation(list, "textures/block/sandstone.png", "40_sandstone/sandstone.properties");
      addDefaultLocation(list, "textures/block/red_sandstone.png", "41_red_sandstone/red_sandstone.properties");
      String[] colors = new String[]{
         "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
      };

      for (int i = 0; i < colors.length; i++) {
         String color = colors[i];
         String prefix = StrUtils.fillLeft(i + "", 2, '0');
         addDefaultLocation(list, "textures/block/" + color + "_stained_glass.png", prefix + "_glass_" + color + "/glass_" + color + ".properties");
         addDefaultLocation(list, "textures/block/" + color + "_stained_glass.png", prefix + "_glass_" + color + "/glass_pane_" + color + ".properties");
      }

      return (String[])list.toArray(new String[list.size()]);
   }

   private static void addDefaultLocation(List list, String locBase, String pathSuffix) {
      String defPath = "optifine/ctm/default/";
      net.minecraft.resources.ResourceLocation loc = new net.minecraft.resources.ResourceLocation(locBase);
      PackResources rp = Config.getDefiningResourcePack(loc);
      if (rp != null) {
         if (rp.m_5542_().equals("programmer_art")) {
            String defPathPa = defPath + "programmer_art/";
            list.add(defPathPa + pathSuffix);
         } else {
            if (rp == Config.getDefaultResourcePack()) {
               list.add(defPath + pathSuffix);
            }
         }
      }
   }
}
