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
import net.minecraft.src.C_1353_;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_1752_;
import net.minecraft.src.C_1818_;
import net.minecraft.src.C_1884_;
import net.minecraft.src.C_1918_;
import net.minecraft.src.C_1919_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4196_;
import net.minecraft.src.C_4473_;
import net.minecraft.src.C_4484_;
import net.minecraft.src.C_4486_;
import net.minecraft.src.C_4528_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_50_;
import net.minecraft.src.C_51_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_4687_.C_4689_;
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
   public static final C_2064_ AIR_DEFAULT_STATE = C_1710_.f_50016_.m_49966_();
   private static C_4486_ emptySprite = null;
   public static C_5265_ LOCATION_SPRITE_EMPTY = TextureUtils.LOCATION_SPRITE_EMPTY;
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
   public static final C_4486_ SPRITE_DEFAULT = new C_4486_(C_4484_.f_118259_, new C_5265_("default"));
   private static final C_212974_ RANDOM = RandomUtils.makeThreadSafeRandomSource(0);

   public static C_4196_[] getConnectedTexture(C_1557_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4196_ quad, RenderEnv renderEnv) {
      C_4486_ spriteIn = quad.m_173410_();
      if (spriteIn == null) {
         return renderEnv.getArrayQuadsCtm(quad);
      } else if (skipConnectedTexture(blockAccess, blockState, blockPos, quad, renderEnv)) {
         quad = getQuad(emptySprite, quad);
         return renderEnv.getArrayQuadsCtm(quad);
      } else {
         C_4687_ side = quad.m_111306_();
         return getConnectedTextureMultiPass(blockAccess, blockState, blockPos, side, quad, renderEnv);
      }
   }

   private static boolean skipConnectedTexture(C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4196_ quad, RenderEnv renderEnv) {
      C_1706_ block = blockState.b();
      if (block instanceof C_1818_) {
         C_4687_ face = quad.m_111306_();
         if (face != C_4687_.UP && face != C_4687_.DOWN) {
            return false;
         }

         if (!quad.isFaceQuad()) {
            return false;
         }

         C_4675_ posNeighbour = blockPos.m_121945_(quad.m_111306_());
         C_2064_ stateNeighbour = blockAccess.m_8055_(posNeighbour);
         if (stateNeighbour.b() != block) {
            return false;
         }

         C_1706_ blockNeighbour = stateNeighbour.b();
         if (block instanceof C_1919_ && blockNeighbour instanceof C_1919_) {
            C_1353_ color = ((C_1919_)block).m_7988_();
            C_1353_ colorNeighbour = ((C_1919_)blockNeighbour).m_7988_();
            if (color != colorNeighbour) {
               return false;
            }
         }

         double midX = (double)quad.getMidX();
         if (midX < 0.4) {
            if ((Boolean)stateNeighbour.c(C_1752_.f_52312_)) {
               return true;
            }
         } else if (midX > 0.6) {
            if ((Boolean)stateNeighbour.c(C_1752_.f_52310_)) {
               return true;
            }
         } else {
            double midZ = quad.getMidZ();
            if (midZ < 0.4) {
               if ((Boolean)stateNeighbour.c(C_1752_.f_52309_)) {
                  return true;
               }
            } else {
               if (!(midZ > 0.6)) {
                  return true;
               }

               if ((Boolean)stateNeighbour.c(C_1752_.f_52311_)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   protected static C_4196_[] getQuads(C_4486_ sprite, C_4196_ quadIn, RenderEnv renderEnv) {
      if (sprite == null) {
         return null;
      } else if (sprite == SPRITE_DEFAULT) {
         return renderEnv.getArrayQuadsCtm(quadIn);
      } else {
         C_4196_ quad = getQuad(sprite, quadIn);
         return renderEnv.getArrayQuadsCtm(quad);
      }
   }

   private static synchronized C_4196_ getQuad(C_4486_ sprite, C_4196_ quadIn) {
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

            C_4196_ quad = (C_4196_)quadMap.get(quadIn);
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

   private static synchronized C_4196_ getQuadFull(C_4486_ sprite, C_4196_ quadIn, int tintIndex) {
      if (spriteQuadFullMaps == null) {
         return null;
      } else if (sprite == null) {
         return null;
      } else {
         int spriteIndex = sprite.getIndexInMap();
         if (spriteIndex >= 0 && spriteIndex < spriteQuadFullMaps.length) {
            Map quadMap = spriteQuadFullMaps[spriteIndex];
            if (quadMap == null) {
               quadMap = new EnumMap(C_4687_.class);
               spriteQuadFullMaps[spriteIndex] = quadMap;
            }

            C_4687_ face = quadIn.m_111306_();
            C_4196_ quad = (C_4196_)quadMap.get(face);
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

   private static C_4196_ makeSpriteQuad(C_4196_ quad, C_4486_ sprite) {
      int[] data = (int[])quad.m_111303_().clone();
      C_4486_ spriteFrom = quad.m_173410_();

      for (int i = 0; i < 4; i++) {
         fixVertex(data, i, spriteFrom, sprite);
      }

      return new C_4196_(data, quad.m_111305_(), quad.m_111306_(), sprite, quad.m_111307_());
   }

   private static void fixVertex(int[] data, int vertex, C_4486_ spriteFrom, C_4486_ spriteTo) {
      int mul = data.length / 4;
      int pos = mul * vertex;
      float u = Float.intBitsToFloat(data[pos + 4]);
      float v = Float.intBitsToFloat(data[pos + 4 + 1]);
      double su16 = spriteFrom.getSpriteU16(u);
      double sv16 = spriteFrom.getSpriteV16(v);
      data[pos + 4] = Float.floatToRawIntBits(spriteTo.getInterpolatedU16(su16));
      data[pos + 4 + 1] = Float.floatToRawIntBits(spriteTo.getInterpolatedV16(sv16));
   }

   private static C_4196_[] getConnectedTextureMultiPass(
      C_1557_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4687_ side, C_4196_ quad, RenderEnv renderEnv
   ) {
      C_4196_[] quads = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, quad, true, 0, renderEnv);
      if (!multipass) {
         return quads;
      } else if (quads.length == 1 && quads[0] == quad) {
         return quads;
      } else {
         List<C_4196_> listQuads = renderEnv.getListQuadsCtmMultipass(quads);

         for (int q = 0; q < listQuads.size(); q++) {
            C_4196_ newQuad = (C_4196_)listQuads.get(q);
            C_4196_ mpQuad = newQuad;

            for (int i = 0; i < 3; i++) {
               C_4196_[] newMpQuads = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, mpQuad, false, i + 1, renderEnv);
               if (newMpQuads.length != 1 || newMpQuads[0] == mpQuad) {
                  break;
               }

               mpQuad = newMpQuads[0];
            }

            listQuads.set(q, mpQuad);
         }

         for (int i = 0; i < quads.length; i++) {
            quads[i] = (C_4196_)listQuads.get(i);
         }

         return quads;
      }
   }

   public static C_4196_[] getConnectedTextureSingle(
      C_1557_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4687_ facing, C_4196_ quad, boolean checkBlocks, int pass, RenderEnv renderEnv
   ) {
      C_1706_ block = blockState.b();
      C_4486_ icon = quad.m_173410_();
      if (tileProperties != null) {
         int iconId = icon.getIndexInMap();
         if (iconId >= 0 && iconId < tileProperties.length) {
            ConnectedProperties[] cps = tileProperties[iconId];
            if (cps != null) {
               int side = getSide(facing);

               for (int i = 0; i < cps.length; i++) {
                  ConnectedProperties cp = cps[i];
                  if (cp != null && cp.matchesBlockId(blockState.getBlockId())) {
                     C_4196_[] newQuads = getConnectedTexture(cp, blockAccess, blockState, blockPos, side, quad, pass, renderEnv);
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
                     C_4196_[] newQuads = getConnectedTexture(cp, blockAccess, blockState, blockPos, side, quad, pass, renderEnv);
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

   public static int getSide(C_4687_ facing) {
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

   private static C_4687_ getFacing(int side) {
      switch (side) {
         case 0:
            return C_4687_.DOWN;
         case 1:
            return C_4687_.UP;
         case 2:
            return C_4687_.NORTH;
         case 3:
            return C_4687_.SOUTH;
         case 4:
            return C_4687_.WEST;
         case 5:
            return C_4687_.EAST;
         default:
            return C_4687_.UP;
      }
   }

   private static C_4196_[] getConnectedTexture(
      ConnectedProperties cp, C_1557_ blockAccess, C_2064_ blockState, C_4675_ blockPos, int side, C_4196_ quad, int pass, RenderEnv renderEnv
   ) {
      int vertAxis = 0;
      int metadata = blockState.getMetadata();
      C_1706_ block = blockState.b();
      if (block instanceof C_1884_) {
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

         int y = blockPos.v();
         if (cp.heights != null && !cp.heights.isInRange(y)) {
            return null;
         } else {
            if (cp.biomes != null) {
               C_1629_ blockBiome = BiomeUtils.getBiome(blockAccess, blockPos);
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

            C_4486_ icon = quad.m_173410_();
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

   private static int getPillarAxis(C_2064_ blockState) {
      C_4689_ axis = (C_4689_)blockState.c(C_1884_.f_55923_);
      switch (axis) {
         case X:
            return 2;
         case Z:
            return 1;
         default:
            return 0;
      }
   }

   private static C_4486_ getConnectedTextureRandom(ConnectedProperties cp, C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, int side) {
      if (cp.tileIcons.length == 1) {
         return cp.tileIcons[0];
      } else {
         int face = side / cp.symmetry * cp.symmetry;
         if (cp.linked) {
            C_4675_ posDown = blockPos.m_7495_();

            for (C_2064_ bsDown = blockAccess.m_8055_(posDown); bsDown.b() == blockState.b(); bsDown = blockAccess.m_8055_(posDown)) {
               blockPos = posDown;
               posDown = posDown.m_7495_();
               if (posDown.v() < 0) {
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

   private static C_4486_ getConnectedTextureFixed(ConnectedProperties cp) {
      return cp.tileIcons[0];
   }

   private static C_4486_ getConnectedTextureRepeat(ConnectedProperties cp, C_4675_ blockPos, int side) {
      if (cp.tileIcons.length == 1) {
         return cp.tileIcons[0];
      } else {
         int x = blockPos.u();
         int y = blockPos.v();
         int z = blockPos.w();
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

   private static C_4486_ getConnectedTextureCtm(
      ConnectedProperties cp,
      C_1559_ blockAccess,
      C_2064_ blockState,
      C_4675_ blockPos,
      int vertAxis,
      int side,
      C_4486_ icon,
      int metadata,
      RenderEnv renderEnv
   ) {
      int index = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
      return cp.tileIcons[index];
   }

   private static synchronized C_4196_[] getConnectedTextureCtmCompact(
      ConnectedProperties cp,
      C_1559_ blockAccess,
      C_2064_ blockState,
      C_4675_ blockPos,
      int vertAxis,
      int side,
      C_4196_ quad,
      int metadata,
      RenderEnv renderEnv
   ) {
      C_4486_ icon = quad.m_173410_();
      int index = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
      return ConnectedTexturesCompact.getConnectedTextureCtmCompact(index, cp, side, quad, renderEnv);
   }

   private static C_4196_[] getConnectedTextureOverlay(
      ConnectedProperties cp,
      C_1559_ blockAccess,
      C_2064_ blockState,
      C_4675_ blockPos,
      int vertAxis,
      int side,
      C_4196_ quad,
      int metadata,
      RenderEnv renderEnv
   ) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         C_4486_ icon = quad.m_173410_();
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

         return (C_4196_[])dirEdges;
      }
   }

   private static C_4196_[] getConnectedTextureOverlayFixed(ConnectedProperties cp, C_4196_ quad, RenderEnv renderEnv) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var5;
         try {
            C_4486_ sprite = getConnectedTextureFixed(cp);
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var5 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }
         }

         return (C_4196_[])var5;
      }
   }

   private static C_4196_[] getConnectedTextureOverlayRandom(
      ConnectedProperties cp, C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, int side, C_4196_ quad, RenderEnv renderEnv
   ) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var9;
         try {
            C_4486_ sprite = getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side);
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var9 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }
         }

         return (C_4196_[])var9;
      }
   }

   private static C_4196_[] getConnectedTextureOverlayRepeat(ConnectedProperties cp, C_4675_ blockPos, int side, C_4196_ quad, RenderEnv renderEnv) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var7;
         try {
            C_4486_ sprite = getConnectedTextureRepeat(cp, blockPos, side);
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var7 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }
         }

         return (C_4196_[])var7;
      }
   }

   private static C_4196_[] getConnectedTextureOverlayCtm(
      ConnectedProperties cp,
      C_1559_ blockAccess,
      C_2064_ blockState,
      C_4675_ blockPos,
      int vertAxis,
      int side,
      C_4196_ quad,
      int metadata,
      RenderEnv renderEnv
   ) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var11;
         try {
            C_4486_ sprite = getConnectedTextureCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, quad.m_173410_(), metadata, renderEnv);
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var11 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }
         }

         return (C_4196_[])var11;
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
      C_1559_ blockAccess,
      C_2064_ blockState,
      C_4675_ blockPos,
      int vertAxis,
      int side,
      C_4486_ icon,
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
               C_4675_ posFront = blockPos.m_7495_();
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
               C_4675_ posFront = blockPos.m_7494_();
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
               C_4675_ posFront = blockPos.m_122012_();
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
               C_4675_ posFront = blockPos.m_122019_();
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
               C_4675_ posFront = blockPos.m_122024_();
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
               C_4675_ posFront = blockPos.m_122029_();
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
                  C_4675_ posFront = blockPos.m_7495_();
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
                  C_4675_ posFront = blockPos.m_7494_();
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
                  C_4675_ posFront = blockPos.m_122012_();
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
                  C_4675_ posFront = blockPos.m_122019_();
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
                  C_4675_ posFront = blockPos.m_122024_();
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
                  C_4675_ posFront = blockPos.m_122029_();
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
      ConnectedProperties cp, C_1559_ worldReader, C_2064_ blockState, C_4675_ blockPos, int side, C_4486_ icon, int metadata
   ) {
      C_2064_ neighbourState = worldReader.m_8055_(blockPos);
      if (!isFullCubeModel(neighbourState, worldReader, blockPos)) {
         return false;
      } else if (cp.connectBlocks != null && !Matches.block(neighbourState.getBlockId(), neighbourState.getMetadata(), cp.connectBlocks)) {
         return false;
      } else {
         if (cp.connectTileIcons != null) {
            C_4486_ neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
            if (!Config.isSameOne(neighbourIcon, cp.connectTileIcons)) {
               return false;
            }
         }

         C_4675_ posNeighbourStateAbove = blockPos.m_121945_(getFacing(side));
         C_2064_ neighbourStateAbove = worldReader.m_8055_(posNeighbourStateAbove);
         if (neighbourStateAbove.i(worldReader, posNeighbourStateAbove)) {
            return false;
         } else {
            return side == 1 && neighbourStateAbove.b() == C_1710_.f_50125_
               ? false
               : !isNeighbour(cp, worldReader, blockState, blockPos, neighbourState, side, icon, metadata);
         }
      }
   }

   private static boolean isFullCubeModel(C_2064_ state, C_1559_ blockReader, C_4675_ pos) {
      if (BlockUtils.isFullCube(state, blockReader, pos)) {
         return true;
      } else {
         C_1706_ block = state.b();
         return block == C_1710_.f_50058_ ? true : block instanceof C_1918_;
      }
   }

   private static boolean isNeighbourMatching(
      ConnectedProperties cp, C_1559_ worldReader, C_2064_ blockState, C_4675_ blockPos, int side, C_4486_ icon, int metadata
   ) {
      C_2064_ neighbourState = worldReader.m_8055_(blockPos);
      if (neighbourState == AIR_DEFAULT_STATE) {
         return false;
      } else if (cp.matchBlocks != null && !cp.matchesBlock(neighbourState.getBlockId(), neighbourState.getMetadata())) {
         return false;
      } else {
         if (cp.matchTileIcons != null) {
            C_4486_ neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
            if (neighbourIcon != icon) {
               return false;
            }
         }

         C_4675_ posNeighbourAbove = blockPos.m_121945_(getFacing(side));
         C_2064_ neighbourStateAbove = worldReader.m_8055_(posNeighbourAbove);
         return neighbourStateAbove.i(worldReader, posNeighbourAbove) ? false : side != 1 || neighbourStateAbove.b() != C_1710_.f_50125_;
      }
   }

   private static boolean isNeighbour(ConnectedProperties cp, C_1559_ worldReader, C_2064_ blockState, C_4675_ blockPos, int side, C_4486_ icon, int metadata) {
      C_2064_ neighbourState = worldReader.m_8055_(blockPos);
      return isNeighbour(cp, worldReader, blockState, blockPos, neighbourState, side, icon, metadata);
   }

   private static boolean isNeighbour(
      ConnectedProperties cp, C_1559_ worldReader, C_2064_ blockState, C_4675_ blockPos, C_2064_ neighbourState, int side, C_4486_ icon, int metadata
   ) {
      if (blockState == neighbourState) {
         return true;
      } else if (cp.connect == 2) {
         if (neighbourState == null) {
            return false;
         } else if (neighbourState == AIR_DEFAULT_STATE) {
            return false;
         } else {
            C_4486_ neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
            return neighbourIcon == icon;
         }
      } else if (cp.connect == 1) {
         C_1706_ block = blockState.b();
         C_1706_ neighbourBlock = neighbourState.b();
         return neighbourBlock == block;
      } else {
         return false;
      }
   }

   private static C_4486_ getNeighbourIcon(C_1559_ worldReader, C_2064_ blockState, C_4675_ blockPos, C_2064_ neighbourState, int side) {
      C_4528_ model = C_3391_.m_91087_().m_91289_().m_110907_().m_110893_(neighbourState);
      if (model == null) {
         return null;
      } else {
         C_4687_ facing = getFacing(side);
         List quads = model.m_213637_(neighbourState, facing, RANDOM);
         if (quads == null) {
            return null;
         } else {
            if (Config.isBetterGrass()) {
               quads = BetterGrass.getFaceQuads(worldReader, neighbourState, blockPos, facing, quads);
            }

            if (quads.size() > 0) {
               C_4196_ quad = (C_4196_)quads.get(0);
               return quad.m_173410_();
            } else {
               List quadsGeneral = model.m_213637_(neighbourState, null, RANDOM);
               if (quadsGeneral == null) {
                  return null;
               } else {
                  for (int i = 0; i < quadsGeneral.size(); i++) {
                     C_4196_ quad = (C_4196_)quadsGeneral.get(i);
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

   private static C_4486_ getConnectedTextureHorizontal(
      ConnectedProperties cp, C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, int vertAxis, int side, C_4486_ icon, int metadata
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

   private static C_4486_ getConnectedTextureVertical(
      ConnectedProperties cp, C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, int vertAxis, int side, C_4486_ icon, int metadata
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

   private static C_4486_ getConnectedTextureHorizontalVertical(
      ConnectedProperties cp, C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, int vertAxis, int side, C_4486_ icon, int metadata
   ) {
      C_4486_[] tileIcons = cp.tileIcons;
      C_4486_ iconH = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
      if (iconH != null && iconH != icon && iconH != tileIcons[3]) {
         return iconH;
      } else {
         C_4486_ iconV = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
         if (iconV == tileIcons[0]) {
            return tileIcons[4];
         } else if (iconV == tileIcons[1]) {
            return tileIcons[5];
         } else {
            return iconV == tileIcons[2] ? tileIcons[6] : iconV;
         }
      }
   }

   private static C_4486_ getConnectedTextureVerticalHorizontal(
      ConnectedProperties cp, C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, int vertAxis, int side, C_4486_ icon, int metadata
   ) {
      C_4486_[] tileIcons = cp.tileIcons;
      C_4486_ iconV = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
      if (iconV != null && iconV != icon && iconV != tileIcons[3]) {
         return iconV;
      } else {
         C_4486_ iconH = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
         if (iconH == tileIcons[0]) {
            return tileIcons[4];
         } else if (iconH == tileIcons[1]) {
            return tileIcons[5];
         } else {
            return iconH == tileIcons[2] ? tileIcons[6] : iconH;
         }
      }
   }

   private static C_4486_ getConnectedTextureTop(
      ConnectedProperties cp, C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, int vertAxis, int side, C_4486_ icon, int metadata
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

   public static void updateIcons(C_4484_ textureMap) {
      blockProperties = null;
      tileProperties = null;
      spriteQuadMaps = null;
      spriteQuadCompactMaps = null;
      if (Config.isConnectedTextures()) {
         C_50_[] rps = Config.getResourcePacks();

         for (int i = rps.length - 1; i >= 0; i--) {
            C_50_ rp = rps[i];
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

   public static void updateIcons(C_4484_ textureMap, C_50_ rp) {
      String[] names = ResUtils.collectFiles(rp, "optifine/ctm/", ".properties", getDefaultCtmPaths());
      Arrays.sort(names);
      List tileList = makePropertyList(tileProperties);
      List blockList = makePropertyList(blockProperties);

      for (int i = 0; i < names.length; i++) {
         String name = names[i];
         Config.dbg("ConnectedTextures: " + name);

         try {
            C_5265_ locFile = new C_5265_(name);
            InputStream in = Config.getResourceStream(rp, C_51_.CLIENT_RESOURCES, locFile);
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

   public static void refreshIcons(C_4484_ textureMap) {
      refreshIcons(blockProperties, textureMap);
      refreshIcons(tileProperties, textureMap);
      emptySprite = getSprite(textureMap, LOCATION_SPRITE_EMPTY);
   }

   private static C_4486_ getSprite(C_4484_ textureMap, C_5265_ loc) {
      C_4486_ sprite = textureMap.m_118316_(loc);
      if (sprite == null || C_4473_.isMisingSprite(sprite)) {
         Config.warn("Missing CTM sprite: " + loc);
      }

      return sprite;
   }

   private static void refreshIcons(ConnectedProperties[][] propertiesArray, C_4484_ textureMap) {
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
            C_4486_ icon = cp.matchTileIcons[i];
            if (!(icon instanceof C_4486_)) {
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
      C_5265_ loc = new C_5265_(locBase);
      C_50_ rp = Config.getDefiningResourcePack(loc);
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
