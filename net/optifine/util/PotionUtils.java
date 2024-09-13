package net.optifine.util;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.optifine.reflect.Reflector;

public class PotionUtils {
   public static MobEffect getPotion(ResourceLocation loc) {
      return !BuiltInRegistries.f_256974_.m_7804_(loc) ? null : (MobEffect)BuiltInRegistries.f_256974_.m_7745_(loc);
   }

   public static MobEffect getPotion(int potionID) {
      return (MobEffect)BuiltInRegistries.f_256974_.m_7942_(potionID);
   }

   public static int getId(MobEffect potionIn) {
      return BuiltInRegistries.f_256974_.m_7447_(potionIn);
   }

   public static String getPotionBaseName(Potion p) {
      return p == null ? null : (String)Reflector.Potion_baseName.getValue(p);
   }

   public static Potion getPotion(ItemStack itemStack) {
      PotionContents pc = (PotionContents)itemStack.m_323252_(DataComponents.f_314188_);
      if (pc == null) {
         return null;
      } else {
         Optional<Holder<Potion>> opt = pc.f_317059_();
         if (opt.isEmpty()) {
            return null;
         } else {
            Holder<Potion> holder = (Holder<Potion>)opt.get();
            return !holder.m_203633_() ? null : (Potion)holder.m_203334_();
         }
      }
   }
}
