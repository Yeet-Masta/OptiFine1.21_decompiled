import net.minecraft.src.C_1141_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_470_;
import net.minecraft.src.C_472_;
import net.minecraft.src.C_976_;

public class ItemUtils {
   public static C_472_<C_1391_> a(C_1596_ p_150959_0_, C_1141_ p_150959_1_, C_470_ p_150959_2_) {
      p_150959_1_.m_6672_(p_150959_2_);
      return C_472_.m_19096_(p_150959_1_.m_21120_(p_150959_2_));
   }

   public static C_1391_ a(C_1391_ p_41817_0_, C_1141_ p_41817_1_, C_1391_ p_41817_2_, boolean p_41817_3_) {
      boolean flag = p_41817_1_.m_322042_();
      if (p_41817_3_ && flag) {
         if (!p_41817_1_.m_150109_().m_36063_(p_41817_2_)) {
            p_41817_1_.m_150109_().m_36054_(p_41817_2_);
         }

         return p_41817_0_;
      } else {
         p_41817_0_.m_321439_(1, p_41817_1_);
         if (p_41817_0_.m_41619_()) {
            return p_41817_2_;
         } else {
            if (!p_41817_1_.m_150109_().m_36054_(p_41817_2_)) {
               p_41817_1_.m_36176_(p_41817_2_, false);
            }

            return p_41817_0_;
         }
      }
   }

   public static C_1391_ a(C_1391_ p_41813_0_, C_1141_ p_41813_1_, C_1391_ p_41813_2_) {
      return a(p_41813_0_, p_41813_1_, p_41813_2_, true);
   }

   public static void a(C_976_ p_150952_0_, Iterable<C_1391_> p_150952_1_) {
      C_1596_ level = p_150952_0_.m_9236_();
      if (!level.f_46443_) {
         p_150952_1_.forEach(
            p_338184_2_ -> level.m_7967_(new C_976_(level, p_150952_0_.m_20185_(), p_150952_0_.m_20186_(), p_150952_0_.m_20189_(), p_338184_2_))
         );
      }
   }
}
