package net.minecraft.server.level;

import javax.annotation.Nullable;
import net.minecraft.world.level.chunk.status.ChunkPyramid;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.chunk.status.ChunkStep;

public class ChunkLevel {
   private static int f_287007_;
   private static int f_286976_;
   private static int f_286937_;
   private static ChunkStep f_337362_ = ChunkPyramid.f_336884_.m_338623_(ChunkStatus.f_315432_);
   public static int f_337478_ = f_337362_.f_337162_().m_340327_();
   public static int f_286967_ = 33 + f_337478_ + 32;

   @Nullable
   public static ChunkStatus m_287158_(int levelIn) {
      return m_339814_(levelIn - 33, null);
   }

   @Nullable
   public static ChunkStatus m_339814_(int levelIn, @Nullable ChunkStatus statusIn) {
      if (levelIn > f_337478_) {
         return statusIn;
      } else {
         return levelIn <= 0 ? ChunkStatus.f_315432_ : f_337362_.f_337162_().m_338674_(levelIn);
      }
   }

   public static ChunkStatus m_339977_(int levelIn) {
      return m_339814_(levelIn, ChunkStatus.f_314297_);
   }

   public static int m_287141_(ChunkStatus statusIn) {
      return 33 + f_337362_.m_340555_(statusIn);
   }

   public static FullChunkStatus m_287264_(int levelIn) {
      if (levelIn <= 31) {
         return FullChunkStatus.ENTITY_TICKING;
      } else if (levelIn <= 32) {
         return FullChunkStatus.BLOCK_TICKING;
      } else {
         return levelIn <= 33 ? FullChunkStatus.FULL : FullChunkStatus.INACCESSIBLE;
      }
   }

   public static int m_287154_(FullChunkStatus statusIn) {
      return switch (<unrepresentable>.$SwitchMap$net$minecraft$server$level$FullChunkStatus[statusIn.ordinal()]) {
         case 1 -> f_286967_;
         case 2 -> 33;
         case 3 -> 32;
         case 4 -> 31;
         default -> throw new MatchException(null, null);
      };
   }

   public static boolean m_287155_(int levelIn) {
      return levelIn <= 31;
   }

   public static boolean m_287283_(int levelIn) {
      return levelIn <= 32;
   }

   public static boolean m_287217_(int levelIn) {
      return levelIn <= f_286967_;
   }
}
