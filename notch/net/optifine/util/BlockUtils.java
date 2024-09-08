package net.optifine.util;

import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.Collection;
import java.util.List;
import net.minecraft.src.C_141221_;
import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_2065_;
import net.minecraft.src.C_2084_;
import net.minecraft.src.C_2097_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_3050_;
import net.minecraft.src.C_3068_;
import net.minecraft.src.C_3072_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;
import net.optifine.render.RenderEnv;

public class BlockUtils {
   private static final ThreadLocal<BlockUtils.RenderSideCacheKey> threadLocalKey = ThreadLocal.withInitial(
      () -> new BlockUtils.RenderSideCacheKey(null, null, null)
   );
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<BlockUtils.RenderSideCacheKey>> threadLocalMap = ThreadLocal.withInitial(
      () -> {
         Object2ByteLinkedOpenHashMap<BlockUtils.RenderSideCacheKey> object2bytelinkedopenhashmap = new Object2ByteLinkedOpenHashMap<BlockUtils.RenderSideCacheKey>(
            200
         ) {
            protected void rehash(int p_rehash_1_) {
            }
         };
         object2bytelinkedopenhashmap.defaultReturnValue((byte)127);
         return object2bytelinkedopenhashmap;
      }
   );

   public static boolean shouldSideBeRendered(C_2064_ blockStateIn, C_1559_ blockReaderIn, C_4675_ blockPosIn, C_4687_ facingIn, RenderEnv renderEnv) {
      C_4675_ posNeighbour = blockPosIn.m_121945_(facingIn);
      C_2064_ stateNeighbour = blockReaderIn.m_8055_(posNeighbour);
      if (stateNeighbour.isCacheOpaqueCube() && !(blockStateIn.m_60734_() instanceof C_141221_)) {
         return false;
      } else if (blockStateIn.m_60719_(stateNeighbour, facingIn)) {
         return false;
      } else {
         return stateNeighbour.m_60815_()
            ? shouldSideBeRenderedCached(blockStateIn, blockReaderIn, blockPosIn, facingIn, renderEnv, stateNeighbour, posNeighbour)
            : true;
      }
   }

   public static boolean shouldSideBeRenderedCached(
      C_2064_ blockStateIn, C_1559_ blockReaderIn, C_4675_ blockPosIn, C_4687_ facingIn, RenderEnv renderEnv, C_2064_ stateNeighbourIn, C_4675_ posNeighbourIn
   ) {
      long key = (long)blockStateIn.getBlockStateId() << 36 | (long)stateNeighbourIn.getBlockStateId() << 4 | (long)facingIn.ordinal();
      Long2ByteLinkedOpenHashMap map = renderEnv.getRenderSideMap();
      byte b0 = map.getAndMoveToFirst(key);
      if (b0 != 0) {
         return b0 > 0;
      } else {
         C_3072_ voxelshape = blockStateIn.m_60655_(blockReaderIn, blockPosIn, facingIn);
         if (voxelshape.m_83281_()) {
            return true;
         } else {
            C_3072_ voxelshape1 = stateNeighbourIn.m_60655_(blockReaderIn, posNeighbourIn, facingIn.m_122424_());
            boolean flag = C_3068_.m_83157_(voxelshape, voxelshape1, C_3050_.f_82685_);
            if (map.size() > 400) {
               map.removeLastByte();
            }

            map.putAndMoveToFirst(key, (byte)(flag ? 1 : -1));
            return flag;
         }
      }
   }

   public static int getBlockId(C_1706_ block) {
      return C_256712_.f_256975_.a(block);
   }

   public static C_1706_ getBlock(C_5265_ loc) {
      return !C_256712_.f_256975_.d(loc) ? null : (C_1706_)C_256712_.f_256975_.m_7745_(loc);
   }

   public static int getMetadata(C_2064_ blockState) {
      C_1706_ block = blockState.m_60734_();
      C_2065_<C_1706_, C_2064_> stateContainer = block.m_49965_();
      List<C_2064_> validStates = stateContainer.m_61056_();
      return validStates.indexOf(blockState);
   }

   public static int getMetadataCount(C_1706_ block) {
      C_2065_<C_1706_, C_2064_> stateContainer = block.m_49965_();
      List<C_2064_> validStates = stateContainer.m_61056_();
      return validStates.size();
   }

   public static C_2064_ getBlockState(C_1706_ block, int metadata) {
      C_2065_<C_1706_, C_2064_> stateContainer = block.m_49965_();
      List<C_2064_> validStates = stateContainer.m_61056_();
      return metadata >= 0 && metadata < validStates.size() ? (C_2064_)validStates.get(metadata) : null;
   }

   public static List<C_2064_> getBlockStates(C_1706_ block) {
      C_2065_<C_1706_, C_2064_> stateContainer = block.m_49965_();
      List<C_2064_> validStates = stateContainer.m_61056_();
      return validStates;
   }

   public static boolean isFullCube(C_2064_ stateIn, C_1559_ blockReaderIn, C_4675_ posIn) {
      return stateIn.isCacheOpaqueCollisionShape();
   }

   public static Collection<C_2097_> getProperties(C_2064_ blockState) {
      return blockState.B();
   }

   public static boolean isPropertyTrue(C_2064_ blockState, C_2084_ prop) {
      Boolean value = (Boolean)blockState.C().get(prop);
      return Config.isTrue(value);
   }

   public static boolean isPropertyFalse(C_2064_ blockState, C_2084_ prop) {
      Boolean value = (Boolean)blockState.C().get(prop);
      return Config.isFalse(value);
   }

   public static final class RenderSideCacheKey {
      private C_2064_ blockState1;
      private C_2064_ blockState2;
      private C_4687_ facing;
      private int hashCode;

      private RenderSideCacheKey(C_2064_ blockState1In, C_2064_ blockState2In, C_4687_ facingIn) {
         this.blockState1 = blockState1In;
         this.blockState2 = blockState2In;
         this.facing = facingIn;
      }

      private void init(C_2064_ blockState1In, C_2064_ blockState2In, C_4687_ facingIn) {
         this.blockState1 = blockState1In;
         this.blockState2 = blockState2In;
         this.facing = facingIn;
         this.hashCode = 0;
      }

      public BlockUtils.RenderSideCacheKey duplicate() {
         return new BlockUtils.RenderSideCacheKey(this.blockState1, this.blockState2, this.facing);
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else {
            return !(p_equals_1_ instanceof BlockUtils.RenderSideCacheKey block$rendersidecachekey)
               ? false
               : this.blockState1 == block$rendersidecachekey.blockState1
                  && this.blockState2 == block$rendersidecachekey.blockState2
                  && this.facing == block$rendersidecachekey.facing;
         }
      }

      public int hashCode() {
         if (this.hashCode == 0) {
            this.hashCode = 31 * this.hashCode + this.blockState1.hashCode();
            this.hashCode = 31 * this.hashCode + this.blockState2.hashCode();
            this.hashCode = 31 * this.hashCode + this.facing.hashCode();
         }

         return this.hashCode;
      }
   }
}
