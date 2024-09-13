package net.minecraft.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ItemUtils {
   public static InteractionResultHolder<ItemStack> m_150959_(Level p_150959_0_, Player p_150959_1_, InteractionHand p_150959_2_) {
      p_150959_1_.m_6672_(p_150959_2_);
      return InteractionResultHolder.m_19096_(p_150959_1_.m_21120_(p_150959_2_));
   }

   public static ItemStack m_41817_(ItemStack p_41817_0_, Player p_41817_1_, ItemStack p_41817_2_, boolean p_41817_3_) {
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

   public static ItemStack m_41813_(ItemStack p_41813_0_, Player p_41813_1_, ItemStack p_41813_2_) {
      return m_41817_(p_41813_0_, p_41813_1_, p_41813_2_, true);
   }

   public static void m_150952_(ItemEntity p_150952_0_, Iterable<ItemStack> p_150952_1_) {
      Level level = p_150952_0_.m_9236_();
      if (!level.f_46443_) {
         p_150952_1_.forEach(
            p_338184_2_ -> level.m_7967_(new ItemEntity(level, p_150952_0_.m_20185_(), p_150952_0_.m_20186_(), p_150952_0_.m_20189_(), p_338184_2_))
         );
      }
   }
}
