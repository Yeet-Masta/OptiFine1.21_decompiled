package net.minecraft.src;

import javax.annotation.Nullable;

public class C_286930_ {
   private static final int f_287007_ = 33;
   private static final int f_286976_ = 32;
   private static final int f_286937_ = 31;
   private static final C_336565_ f_337362_ = C_336544_.f_336884_.m_338623_(C_313554_.f_315432_);
   public static final int f_337478_ = f_337362_.f_337162_().m_340327_();
   public static final int f_286967_ = 33 + f_337478_ + 32;

   @Nullable
   public static C_313554_ m_287158_(int levelIn) {
      return m_339814_(levelIn - 33, null);
   }

   @Nullable
   public static C_313554_ m_339814_(int levelIn, @Nullable C_313554_ statusIn) {
      if (levelIn > f_337478_) {
         return statusIn;
      } else {
         return levelIn <= 0 ? C_313554_.f_315432_ : f_337362_.f_337162_().m_338674_(levelIn);
      }
   }

   public static C_313554_ m_339977_(int levelIn) {
      return m_339814_(levelIn, C_313554_.f_314297_);
   }

   public static int m_287141_(C_313554_ statusIn) {
      return 33 + f_337362_.m_340555_(statusIn);
   }

   public static C_286921_ m_287264_(int levelIn) {
      if (levelIn <= 31) {
         return C_286921_.ENTITY_TICKING;
      } else if (levelIn <= 32) {
         return C_286921_.BLOCK_TICKING;
      } else {
         return levelIn <= 33 ? C_286921_.FULL : C_286921_.INACCESSIBLE;
      }
   }

   public static int m_287154_(C_286921_ statusIn) {
      return switch (statusIn) {
         case INACCESSIBLE -> f_286967_;
         case FULL -> 33;
         case BLOCK_TICKING -> 32;
         case ENTITY_TICKING -> 31;
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
