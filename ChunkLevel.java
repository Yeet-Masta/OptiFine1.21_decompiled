import javax.annotation.Nullable;
import net.minecraft.src.C_286921_;
import net.minecraft.src.C_313554_;
import net.minecraft.src.C_336544_;
import net.minecraft.src.C_336565_;

public class ChunkLevel {
   private static final int c = 33;
   private static final int d = 32;
   private static final int e = 31;
   private static final C_336565_ f = C_336544_.f_336884_.m_338623_(C_313554_.f_315432_);
   public static final int a = f.f_337162_().m_340327_();
   public static final int b = 33 + a + 32;

   @Nullable
   public static C_313554_ a(int levelIn) {
      return a(levelIn - 33, null);
   }

   @Nullable
   public static C_313554_ a(int levelIn, @Nullable C_313554_ statusIn) {
      if (levelIn > a) {
         return statusIn;
      } else {
         return levelIn <= 0 ? C_313554_.f_315432_ : f.f_337162_().m_338674_(levelIn);
      }
   }

   public static C_313554_ b(int levelIn) {
      return a(levelIn, C_313554_.f_314297_);
   }

   public static int a(C_313554_ statusIn) {
      return 33 + f.m_340555_(statusIn);
   }

   public static C_286921_ c(int levelIn) {
      if (levelIn <= 31) {
         return C_286921_.ENTITY_TICKING;
      } else if (levelIn <= 32) {
         return C_286921_.BLOCK_TICKING;
      } else {
         return levelIn <= 33 ? C_286921_.FULL : C_286921_.INACCESSIBLE;
      }
   }

   public static int a(C_286921_ statusIn) {
      return switch (statusIn) {
         case INACCESSIBLE -> b;
         case FULL -> 33;
         case BLOCK_TICKING -> 32;
         case ENTITY_TICKING -> 31;
         default -> throw new MatchException(null, null);
      };
   }

   public static boolean d(int levelIn) {
      return levelIn <= 31;
   }

   public static boolean e(int levelIn) {
      return levelIn <= 32;
   }

   public static boolean f(int levelIn) {
      return levelIn <= b;
   }
}
