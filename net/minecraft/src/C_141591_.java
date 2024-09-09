package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.optifine.config.IteratableOptionOF;
import net.optifine.gui.IOptionControl;

public class C_141591_ extends C_3441_ implements IOptionControl {
   public static final BooleanSupplier f_168856_ = C_3583_::m_96639_;
   private static final List f_168857_;
   private final C_4996_ f_168858_;
   private int f_168859_;
   private Object f_168860_;
   private final C_141595_ f_168861_;
   private final Function f_168862_;
   private final Function f_168863_;
   private final C_141593_ f_168864_;
   private final boolean f_168866_;
   private final C_213334_.C_213348_ f_168865_;
   private C_213334_ option;

   public C_213334_ getControlOption() {
      return this.option;
   }

   C_141591_(int xIn, int yIn, int widthIn, int heightIn, C_4996_ messageIn, C_4996_ nameIn, int indexIn, Object valueIn, C_141595_ valuesIn, Function valStringIn, Function narrationIn, C_141593_ onChangeIn, C_213334_.C_213348_ tooltipIn, boolean displayOnlyIn) {
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
      if (this.f_168858_ != null && this.f_168858_.m_214077_() instanceof C_213523_) {
         C_213523_ nameTrans = (C_213523_)this.f_168858_.m_214077_();
         this.option = (C_213334_)C_213334_.OPTIONS_BY_KEY.get(nameTrans.m_237508_());
      }

      this.m_168905_(this.f_168860_);
   }

   private void m_257795_() {
      this.a(this.f_168865_.m_257630_(this.f_168860_));
   }

   public void m_5691_() {
      if (C_3583_.m_96638_()) {
         this.m_168908_(-1);
      } else {
         this.m_168908_(1);
      }

   }

   private void m_168908_(int dirIn) {
      if (this.option instanceof IteratableOptionOF) {
         IteratableOptionOF iopt = (IteratableOptionOF)this.option;
         iopt.nextOptionValue(dirIn);
      }

      List list = this.f_168861_.m_142477_();
      this.f_168859_ = C_188_.m_14100_(this.f_168859_ + dirIn, list.size());
      Object t = list.get(this.f_168859_);
      this.m_168905_(t);
      this.f_168864_.m_168965_(this, t);
   }

   private Object m_168914_(int offsetIn) {
      List list = this.f_168861_.m_142477_();
      return list.get(C_188_.m_14100_(this.f_168859_ + offsetIn, list.size()));
   }

   public boolean m_6050_(double mouseX, double mouseY, double deltaX, double deltaY) {
      if (deltaY > 0.0) {
         this.m_168908_(-1);
      } else if (deltaY < 0.0) {
         this.m_168908_(1);
      }

      return true;
   }

   public void m_168892_(Object valueIn) {
      List list = this.f_168861_.m_142477_();
      int i = list.indexOf(valueIn);
      if (i != -1) {
         this.f_168859_ = i;
      }

      this.m_168905_(valueIn);
   }

   private void m_168905_(Object valueIn) {
      C_4996_ component = this.m_168910_(valueIn);
      this.b(component);
      this.f_168860_ = valueIn;
      this.m_257795_();
   }

   private C_4996_ m_168910_(Object valueIn) {
      if (this.option instanceof IteratableOptionOF) {
         IteratableOptionOF iopt = (IteratableOptionOF)this.option;
         C_4996_ comp = iopt.getOptionText();
         if (comp != null) {
            return comp;
         }
      }

      return (C_4996_)(this.f_168866_ ? (C_4996_)this.f_168862_.apply(valueIn) : this.m_168912_(valueIn));
   }

   private C_5012_ m_168912_(Object valueIn) {
      return C_4995_.m_178393_(this.f_168858_, (C_4996_)this.f_168862_.apply(valueIn));
   }

   public Object m_168883_() {
      return this.f_168860_;
   }

   protected C_5012_ m_5646_() {
      return (C_5012_)this.f_168863_.apply(this);
   }

   public void m_168797_(C_141608_ outputIn) {
      outputIn.m_169146_(C_141607_.TITLE, this.m_5646_());
      if (this.j) {
         Object t = this.m_168914_(1);
         C_4996_ component = this.m_168910_(t);
         if (this.aO_()) {
            outputIn.m_169146_(C_141607_.USAGE, C_4996_.m_237110_("narration.cycle_button.usage.focused", new Object[]{component}));
         } else {
            outputIn.m_169146_(C_141607_.USAGE, C_4996_.m_237110_("narration.cycle_button.usage.hovered", new Object[]{component}));
         }
      }

   }

   public C_5012_ m_168904_() {
      return a_((C_4996_)(this.f_168866_ ? this.m_168912_(this.f_168860_) : this.z()));
   }

   public static C_141592_ m_168894_(Function stringifierIn) {
      return new C_141592_(stringifierIn);
   }

   public static C_141592_ m_168896_(C_4996_ p_168896_0_, C_4996_ p_168896_1_) {
      return (new C_141592_((p_168899_2_) -> {
         return p_168899_2_ ? p_168896_0_ : p_168896_1_;
      })).m_232502_(f_168857_);
   }

   public static C_141592_ m_168919_() {
      return (new C_141592_((p_168890_0_) -> {
         return p_168890_0_ ? C_4995_.f_130653_ : C_4995_.f_130654_;
      })).m_232502_(f_168857_);
   }

   public static C_141592_ m_168916_(boolean p_168916_0_) {
      return m_168919_().m_168948_(p_168916_0_);
   }

   static {
      f_168857_ = ImmutableList.of(Boolean.TRUE, Boolean.FALSE);
   }

   public interface C_141595_ {
      List m_142477_();

      List m_142478_();

      static C_141595_ m_232504_(Collection p_232504_0_) {
         final List list = ImmutableList.copyOf(p_232504_0_);
         return new C_141595_() {
            public List m_142477_() {
               return list;
            }

            public List m_142478_() {
               return list;
            }
         };
      }

      static C_141595_ m_168970_(final BooleanSupplier p_168970_0_, List p_168970_1_, List p_168970_2_) {
         final List list = ImmutableList.copyOf(p_168970_1_);
         final List list1 = ImmutableList.copyOf(p_168970_2_);
         return new C_141595_() {
            public List m_142477_() {
               return p_168970_0_.getAsBoolean() ? list1 : list;
            }

            public List m_142478_() {
               return list;
            }
         };
      }
   }

   public interface C_141593_ {
      void m_168965_(C_141591_ var1, Object var2);
   }

   public static class C_141592_ {
      private int f_168920_;
      @Nullable
      private Object f_168921_;
      private final Function f_168922_;
      private C_213334_.C_213348_ f_168923_ = (p_257070_0_) -> {
         return null;
      };
      private Function f_168924_ = C_141591_::m_168904_;
      private C_141595_ f_168925_ = C_141591_.C_141595_.m_232504_(ImmutableList.of());
      private boolean f_168926_;

      public C_141592_(Function stringifierIn) {
         this.f_168922_ = stringifierIn;
      }

      public C_141592_ m_232502_(Collection valuesIn) {
         return this.m_232500_(C_141591_.C_141595_.m_232504_(valuesIn));
      }

      @SafeVarargs
      public final C_141592_ m_168961_(Object... valuesIn) {
         return this.m_232502_(ImmutableList.copyOf(valuesIn));
      }

      public C_141592_ m_168952_(List p_168952_1_, List p_168952_2_) {
         return this.m_232500_(C_141591_.C_141595_.m_168970_(C_141591_.f_168856_, p_168952_1_, p_168952_2_));
      }

      public C_141592_ m_168955_(BooleanSupplier p_168955_1_, List p_168955_2_, List p_168955_3_) {
         return this.m_232500_(C_141591_.C_141595_.m_168970_(p_168955_1_, p_168955_2_, p_168955_3_));
      }

      public C_141592_ m_232500_(C_141595_ p_232500_1_) {
         this.f_168925_ = p_232500_1_;
         return this;
      }

      public C_141592_ m_232498_(C_213334_.C_213348_ p_232498_1_) {
         this.f_168923_ = p_232498_1_;
         return this;
      }

      public C_141592_ m_168948_(Object p_168948_1_) {
         this.f_168921_ = p_168948_1_;
         int i = this.f_168925_.m_142478_().indexOf(p_168948_1_);
         if (i != -1) {
            this.f_168920_ = i;
         }

         return this;
      }

      public C_141592_ m_168959_(Function p_168959_1_) {
         this.f_168924_ = p_168959_1_;
         return this;
      }

      public C_141592_ m_168929_() {
         this.f_168926_ = true;
         return this;
      }

      public C_141591_ m_323445_(C_4996_ p_323445_1_, C_141593_ p_323445_2_) {
         return this.m_168936_(0, 0, 150, 20, p_323445_1_, p_323445_2_);
      }

      public C_141591_ m_168930_(int p_168930_1_, int p_168930_2_, int p_168930_3_, int p_168930_4_, C_4996_ p_168930_5_) {
         return this.m_168936_(p_168930_1_, p_168930_2_, p_168930_3_, p_168930_4_, p_168930_5_, (p_168945_0_, p_168945_1_) -> {
         });
      }

      public C_141591_ m_168936_(int p_168936_1_, int p_168936_2_, int p_168936_3_, int p_168936_4_, C_4996_ p_168936_5_, C_141593_ p_168936_6_) {
         List list = this.f_168925_.m_142478_();
         if (list.isEmpty()) {
            throw new IllegalStateException("No values for cycle button");
         } else {
            Object t = this.f_168921_ != null ? this.f_168921_ : list.get(this.f_168920_);
            C_4996_ component = (C_4996_)this.f_168922_.apply(t);
            C_4996_ component1 = this.f_168926_ ? component : C_4995_.m_178393_(p_168936_5_, component);
            return new C_141591_(p_168936_1_, p_168936_2_, p_168936_3_, p_168936_4_, (C_4996_)component1, p_168936_5_, this.f_168920_, t, this.f_168925_, this.f_168922_, this.f_168924_, p_168936_6_, this.f_168923_, this.f_168926_);
         }
      }
   }
}
