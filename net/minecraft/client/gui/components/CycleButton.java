package net.minecraft.client.gui.components;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.util.Mth;
import net.optifine.config.IteratableOptionOF;
import net.optifine.gui.IOptionControl;

public class CycleButton<T> extends AbstractButton implements IOptionControl {
   public static BooleanSupplier f_168856_ = Screen::m_96639_;
   private static List<Boolean> f_168857_ = ImmutableList.m_253057_(Boolean.TRUE, Boolean.FALSE);
   private Component f_168858_;
   private int f_168859_;
   private T f_168860_;
   private CycleButton.ValueListSupplier<T> f_168861_;
   private Function<T, Component> f_168862_;
   private Function<CycleButton<T>, MutableComponent> f_168863_;
   private CycleButton.OnValueChange<T> f_168864_;
   private boolean f_168866_;
   private OptionInstance.TooltipSupplier<T> f_168865_;
   private OptionInstance option;

   @Override
   public OptionInstance getControlOption() {
      return this.option;
   }

   CycleButton(
      int xIn,
      int yIn,
      int widthIn,
      int heightIn,
      Component messageIn,
      Component nameIn,
      int indexIn,
      T valueIn,
      CycleButton.ValueListSupplier<T> valuesIn,
      Function<T, Component> valStringIn,
      Function<CycleButton<T>, MutableComponent> narrationIn,
      CycleButton.OnValueChange<T> onChangeIn,
      OptionInstance.TooltipSupplier<T> tooltipIn,
      boolean displayOnlyIn
   ) {
      super(xIn, yIn, widthIn, heightIn, messageIn);
      this.f_168858_ = nameIn;
      this.f_168859_ = indexIn;
      this.f_168860_ = valueIn;
      this.f_168861_ = valuesIn;
      this.f_168862_ = valStringIn;
      this.f_168863_ = narrationIn;
      this.f_168864_ = onChangeIn;
      this.f_168866_ = displayOnlyIn;
      this.f_168865_ = tooltipIn;
      this.m_257795_();
      if (this.f_168858_ != null && this.f_168858_.m_214077_() instanceof TranslatableContents) {
         TranslatableContents nameTrans = (TranslatableContents)this.f_168858_.m_214077_();
         this.option = (OptionInstance)OptionInstance.OPTIONS_BY_KEY.get(nameTrans.m_237508_());
      }

      this.m_168905_(this.f_168860_);
   }

   private void m_257795_() {
      this.m_257544_(this.f_168865_.m_257630_(this.f_168860_));
   }

   public void m_5691_() {
      if (Screen.m_96638_()) {
         this.m_168908_(-1);
      } else {
         this.m_168908_(1);
      }
   }

   private void m_168908_(int dirIn) {
      if (this.option instanceof IteratableOptionOF iopt) {
         iopt.nextOptionValue(dirIn);
      }

      List<T> list = this.f_168861_.m_142477_();
      this.f_168859_ = Mth.m_14100_(this.f_168859_ + dirIn, list.size());
      T t = (T)list.get(this.f_168859_);
      this.m_168905_(t);
      this.f_168864_.m_168965_(this, t);
   }

   private T m_168914_(int offsetIn) {
      List<T> list = this.f_168861_.m_142477_();
      return (T)list.get(Mth.m_14100_(this.f_168859_ + offsetIn, list.size()));
   }

   public boolean m_6050_(double mouseX, double mouseY, double deltaX, double deltaY) {
      if (deltaY > 0.0) {
         this.m_168908_(-1);
      } else if (deltaY < 0.0) {
         this.m_168908_(1);
      }

      return true;
   }

   public void m_168892_(T valueIn) {
      List<T> list = this.f_168861_.m_142477_();
      int i = list.indexOf(valueIn);
      if (i != -1) {
         this.f_168859_ = i;
      }

      this.m_168905_(valueIn);
   }

   private void m_168905_(T valueIn) {
      Component component = this.m_168910_(valueIn);
      this.m_93666_(component);
      this.f_168860_ = valueIn;
      this.m_257795_();
   }

   private Component m_168910_(T valueIn) {
      if (this.option instanceof IteratableOptionOF iopt) {
         Component comp = iopt.getOptionText();
         if (comp != null) {
            return comp;
         }
      }

      return (Component)(this.f_168866_ ? (Component)this.f_168862_.apply(valueIn) : this.m_168912_(valueIn));
   }

   private MutableComponent m_168912_(T valueIn) {
      return CommonComponents.m_178393_(this.f_168858_, (Component)this.f_168862_.apply(valueIn));
   }

   public T m_168883_() {
      return this.f_168860_;
   }

   protected MutableComponent m_5646_() {
      return (MutableComponent)this.f_168863_.apply(this);
   }

   public void m_168797_(NarrationElementOutput outputIn) {
      outputIn.m_169146_(NarratedElementType.TITLE, this.m_5646_());
      if (this.f_93623_) {
         T t = this.m_168914_(1);
         Component component = this.m_168910_(t);
         if (this.m_93696_()) {
            outputIn.m_169146_(NarratedElementType.USAGE, Component.m_237110_("narration.cycle_button.usage.focused", new Object[]{component}));
         } else {
            outputIn.m_169146_(NarratedElementType.USAGE, Component.m_237110_("narration.cycle_button.usage.hovered", new Object[]{component}));
         }
      }
   }

   public MutableComponent m_168904_() {
      return m_168799_((Component)(this.f_168866_ ? this.m_168912_(this.f_168860_) : this.m_6035_()));
   }

   public static <T> CycleButton.Builder<T> m_168894_(Function<T, Component> stringifierIn) {
      return new CycleButton.Builder<>(stringifierIn);
   }

   public static CycleButton.Builder<Boolean> m_168896_(Component p_168896_0_, Component p_168896_1_) {
      return new CycleButton.Builder<Boolean>(p_168899_2_ -> p_168899_2_ ? p_168896_0_ : p_168896_1_).m_232502_(f_168857_);
   }

   public static CycleButton.Builder<Boolean> m_168919_() {
      return new CycleButton.Builder<Boolean>(p_168890_0_ -> p_168890_0_ ? CommonComponents.f_130653_ : CommonComponents.f_130654_).m_232502_(f_168857_);
   }

   public static CycleButton.Builder<Boolean> m_168916_(boolean p_168916_0_) {
      return m_168919_().m_168948_(p_168916_0_);
   }

   public static class Builder<T> {
      private int f_168920_;
      @Nullable
      private T f_168921_;
      private Function<T, Component> f_168922_;
      private OptionInstance.TooltipSupplier<T> f_168923_ = p_257070_0_ -> null;
      private Function<CycleButton<T>, MutableComponent> f_168924_ = CycleButton::m_168904_;
      private CycleButton.ValueListSupplier<T> f_168925_ = CycleButton.ValueListSupplier.m_232504_(ImmutableList.m_253057_());
      private boolean f_168926_;

      public Builder(Function<T, Component> stringifierIn) {
         this.f_168922_ = stringifierIn;
      }

      public CycleButton.Builder<T> m_232502_(Collection<T> valuesIn) {
         return this.m_232500_(CycleButton.ValueListSupplier.m_232504_(valuesIn));
      }

      @SafeVarargs
      public CycleButton.Builder<T> m_168961_(T... valuesIn) {
         return this.m_232502_(ImmutableList.copyOf(valuesIn));
      }

      public CycleButton.Builder<T> m_168952_(List<T> p_168952_1_, List<T> p_168952_2_) {
         return this.m_232500_(CycleButton.ValueListSupplier.m_168970_(CycleButton.f_168856_, p_168952_1_, p_168952_2_));
      }

      public CycleButton.Builder<T> m_168955_(BooleanSupplier p_168955_1_, List<T> p_168955_2_, List<T> p_168955_3_) {
         return this.m_232500_(CycleButton.ValueListSupplier.m_168970_(p_168955_1_, p_168955_2_, p_168955_3_));
      }

      public CycleButton.Builder<T> m_232500_(CycleButton.ValueListSupplier<T> p_232500_1_) {
         this.f_168925_ = p_232500_1_;
         return this;
      }

      public CycleButton.Builder<T> m_232498_(OptionInstance.TooltipSupplier<T> p_232498_1_) {
         this.f_168923_ = p_232498_1_;
         return this;
      }

      public CycleButton.Builder<T> m_168948_(T p_168948_1_) {
         this.f_168921_ = p_168948_1_;
         int i = this.f_168925_.m_142478_().indexOf(p_168948_1_);
         if (i != -1) {
            this.f_168920_ = i;
         }

         return this;
      }

      public CycleButton.Builder<T> m_168959_(Function<CycleButton<T>, MutableComponent> p_168959_1_) {
         this.f_168924_ = p_168959_1_;
         return this;
      }

      public CycleButton.Builder<T> m_168929_() {
         this.f_168926_ = true;
         return this;
      }

      public CycleButton<T> m_323445_(Component p_323445_1_, CycleButton.OnValueChange<T> p_323445_2_) {
         return this.m_168936_(0, 0, 150, 20, p_323445_1_, p_323445_2_);
      }

      public CycleButton<T> m_168930_(int p_168930_1_, int p_168930_2_, int p_168930_3_, int p_168930_4_, Component p_168930_5_) {
         return this.m_168936_(p_168930_1_, p_168930_2_, p_168930_3_, p_168930_4_, p_168930_5_, (p_168945_0_, p_168945_1_) -> {
         });
      }

      public CycleButton<T> m_168936_(
         int p_168936_1_, int p_168936_2_, int p_168936_3_, int p_168936_4_, Component p_168936_5_, CycleButton.OnValueChange<T> p_168936_6_
      ) {
         List<T> list = this.f_168925_.m_142478_();
         if (list.isEmpty()) {
            throw new IllegalStateException("No values for cycle button");
         } else {
            T t = (T)(this.f_168921_ != null ? this.f_168921_ : list.get(this.f_168920_));
            Component component = (Component)this.f_168922_.apply(t);
            Component component1 = (Component)(this.f_168926_ ? component : CommonComponents.m_178393_(p_168936_5_, component));
            return new CycleButton<>(
               p_168936_1_,
               p_168936_2_,
               p_168936_3_,
               p_168936_4_,
               component1,
               p_168936_5_,
               this.f_168920_,
               t,
               this.f_168925_,
               this.f_168922_,
               this.f_168924_,
               p_168936_6_,
               this.f_168923_,
               this.f_168926_
            );
         }
      }
   }

   public interface OnValueChange<T> {
      void m_168965_(CycleButton<T> var1, T var2);
   }

   public interface ValueListSupplier<T> {
      List<T> m_142477_();

      List<T> m_142478_();

      static <T> CycleButton.ValueListSupplier<T> m_232504_(Collection<T> p_232504_0_) {
         final List<T> list = ImmutableList.copyOf(p_232504_0_);
         return new CycleButton.ValueListSupplier<T>() {
            @Override
            public List<T> m_142477_() {
               return list;
            }

            @Override
            public List<T> m_142478_() {
               return list;
            }
         };
      }

      static <T> CycleButton.ValueListSupplier<T> m_168970_(final BooleanSupplier p_168970_0_, List<T> p_168970_1_, List<T> p_168970_2_) {
         final List<T> list = ImmutableList.copyOf(p_168970_1_);
         final List<T> list1 = ImmutableList.copyOf(p_168970_2_);
         return new CycleButton.ValueListSupplier<T>() {
            @Override
            public List<T> m_142477_() {
               return p_168970_0_.getAsBoolean() ? list1 : list;
            }

            @Override
            public List<T> m_142478_() {
               return list;
            }
         };
      }
   }
}
