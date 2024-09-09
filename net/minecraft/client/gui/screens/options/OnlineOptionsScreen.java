package net.minecraft.client.gui.screens.options;

import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.Optionull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Difficulty;

public class OnlineOptionsScreen extends OptionsSubScreen {
   private static final Component f_337665_ = Component.m_237115_("options.online.title");
   @Nullable
   private net.minecraft.client.OptionInstance<Unit> f_337239_;

   public OnlineOptionsScreen(Screen p_i338645_1_, net.minecraft.client.Options p_i338645_2_) {
      super(p_i338645_1_, p_i338645_2_, f_337665_);
   }

   protected void m_7856_() {
      super.m_7856_();
      if (this.f_337239_ != null) {
         AbstractWidget abstractwidget = this.f_337426_.m_232535_(this.f_337239_);
         if (abstractwidget != null) {
            abstractwidget.f_93623_ = false;
         }
      }
   }

   private net.minecraft.client.OptionInstance<?>[] m_339916_(net.minecraft.client.Options p_339916_1_, Minecraft p_339916_2_) {
      List<net.minecraft.client.OptionInstance<?>> list = new ArrayList();
      list.add(p_339916_1_.m_231822_());
      list.add(p_339916_1_.m_231823_());
      net.minecraft.client.OptionInstance<Unit> optioninstance = (net.minecraft.client.OptionInstance<Unit>)Optionull.m_269382_(
         p_339916_2_.f_91073_,
         p_338840_0_ -> {
            Difficulty difficulty = p_338840_0_.m_46791_();
            return new net.minecraft.client.OptionInstance<>(
               "options.difficulty.online",
               net.minecraft.client.OptionInstance.m_231498_(),
               (p_339077_1_, p_339077_2_) -> difficulty.m_19033_(),
               new net.minecraft.client.OptionInstance.Enum<>(List.of(Unit.INSTANCE), Codec.EMPTY.codec()),
               Unit.INSTANCE,
               p_338410_0_ -> {
               }
            );
         }
      );
      if (optioninstance != null) {
         this.f_337239_ = optioninstance;
         list.add(optioninstance);
      }

      return (net.minecraft.client.OptionInstance<?>[])list.toArray(new net.minecraft.client.OptionInstance[0]);
   }

   protected void m_338523_() {
      this.f_337426_.m_232533_(this.m_339916_(this.f_337734_, this.f_96541_));
   }
}
