import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.src.C_141607_;
import net.minecraft.src.C_141608_;
import net.minecraft.src.C_213523_;
import net.minecraft.src.C_3441_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4995_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5012_;
import net.optifine.config.IteratableOptionOF;
import net.optifine.gui.IOptionControl;

public class CycleButton<T> extends C_3441_ implements IOptionControl {
   public static final BooleanSupplier a = C_3583_::m_96639_;
   private static final List<Boolean> b = ImmutableList.of(Boolean.TRUE, Boolean.FALSE);
   private final C_4996_ c;
   private int f_93621_;
   private T f;
   private final CycleButton.c<T> m;
   private final Function<T, C_4996_> n;
   private final Function<CycleButton<T>, C_5012_> o;
   private final CycleButton.b<T> p;
   private final boolean q;
   private final OptionInstance.l<T> r;
   private OptionInstance option;

   public OptionInstance getControlOption() {
      return this.option;
   }

   CycleButton(
      int xIn,
      int yIn,
      int widthIn,
      int heightIn,
      C_4996_ messageIn,
      C_4996_ nameIn,
      int indexIn,
      T valueIn,
      CycleButton.c<T> valuesIn,
      Function<T, C_4996_> valStringIn,
      Function<CycleButton<T>, C_5012_> narrationIn,
      CycleButton.b<T> onChangeIn,
      OptionInstance.l<T> tooltipIn,
      boolean displayOnlyIn
   ) {
      super(xIn, yIn, widthIn, heightIn, messageIn);
      this.c = nameIn;
      this.f_93621_ = indexIn;
      this.f = valueIn;
      this.m = valuesIn;
      this.n = valStringIn;
      this.o = narrationIn;
      this.p = onChangeIn;
      this.q = displayOnlyIn;
      this.r = tooltipIn;
      this.f();
      if (this.c != null && this.c.m_214077_() instanceof C_213523_) {
         C_213523_ nameTrans = (C_213523_)this.c.m_214077_();
         this.option = (OptionInstance)OptionInstance.OPTIONS_BY_KEY.get(nameTrans.m_237508_());
      }

      this.b(this.f);
   }

   private void f() {
      this.m_257544_(this.r.apply(this.f));
   }

   public void m_5691_() {
      if (C_3583_.m_96638_()) {
         this.a(-1);
      } else {
         this.a(1);
      }
   }

   private void a(int dirIn) {
      if (this.option instanceof IteratableOptionOF iopt) {
         iopt.nextOptionValue(dirIn);
      }

      List<T> list = this.m.a();
      this.f_93621_ = Mth.b(this.f_93621_ + dirIn, list.size());
      T t = (T)list.get(this.f_93621_);
      this.b(t);
      this.p.onValueChange(this, t);
   }

   private T b(int offsetIn) {
      List<T> list = this.m.a();
      return (T)list.get(Mth.b(this.f_93621_ + offsetIn, list.size()));
   }

   public boolean m_6050_(double mouseX, double mouseY, double deltaX, double deltaY) {
      if (deltaY > 0.0) {
         this.a(-1);
      } else if (deltaY < 0.0) {
         this.a(1);
      }

      return true;
   }

   public void a(T valueIn) {
      List<T> list = this.m.a();
      int i = list.indexOf(valueIn);
      if (i != -1) {
         this.f_93621_ = i;
      }

      this.b(valueIn);
   }

   private void b(T valueIn) {
      C_4996_ component = this.c(valueIn);
      this.m_93666_(component);
      this.f = valueIn;
      this.f();
   }

   private C_4996_ c(T valueIn) {
      if (this.option instanceof IteratableOptionOF iopt) {
         C_4996_ comp = iopt.getOptionText();
         if (comp != null) {
            return comp;
         }
      }

      return (C_4996_)(this.q ? (C_4996_)this.n.apply(valueIn) : this.d(valueIn));
   }

   private C_5012_ d(T valueIn) {
      return C_4995_.m_178393_(this.c, (C_4996_)this.n.apply(valueIn));
   }

   public T a() {
      return this.f;
   }

   protected C_5012_ m_5646_() {
      return (C_5012_)this.o.apply(this);
   }

   public void m_168797_(C_141608_ outputIn) {
      outputIn.m_169146_(C_141607_.TITLE, this.m_5646_());
      if (this.f_93623_) {
         T t = this.b(1);
         C_4996_ component = this.c(t);
         if (this.m_93696_()) {
            outputIn.m_169146_(C_141607_.USAGE, C_4996_.m_237110_("narration.cycle_button.usage.focused", new Object[]{component}));
         } else {
            outputIn.m_169146_(C_141607_.USAGE, C_4996_.m_237110_("narration.cycle_button.usage.hovered", new Object[]{component}));
         }
      }
   }

   public C_5012_ c() {
      return m_168799_((C_4996_)(this.q ? this.d(this.f) : this.m_6035_()));
   }

   public static <T> CycleButton.a<T> a(Function<T, C_4996_> stringifierIn) {
      return new CycleButton.a<>(stringifierIn);
   }

   public static CycleButton.a<Boolean> a(C_4996_ p_168896_0_, C_4996_ p_168896_1_) {
      return new CycleButton.a<Boolean>(p_168899_2_ -> p_168899_2_ ? p_168896_0_ : p_168896_1_).a(b);
   }

   public static CycleButton.a<Boolean> e() {
      return new CycleButton.a<Boolean>(p_168890_0_ -> p_168890_0_ ? C_4995_.f_130653_ : C_4995_.f_130654_).a(b);
   }

   public static CycleButton.a<Boolean> b(boolean p_168916_0_) {
      return e().a(p_168916_0_);
   }

   public static class a<T> {
      private int a;
      @Nullable
      private T b;
      private final Function<T, C_4996_> c;
      private OptionInstance.l<T> d = p_257070_0_ -> null;
      private Function<CycleButton<T>, C_5012_> e = CycleButton::c;
      private CycleButton.c<T> f = CycleButton.c.a(ImmutableList.of());
      private boolean g;

      public a(Function<T, C_4996_> stringifierIn) {
         this.c = stringifierIn;
      }

      public CycleButton.a<T> a(Collection<T> valuesIn) {
         return this.a(CycleButton.c.a(valuesIn));
      }

      @SafeVarargs
      public final CycleButton.a<T> a(T... valuesIn) {
         return this.a(ImmutableList.copyOf(valuesIn));
      }

      public CycleButton.a<T> a(List<T> p_168952_1_, List<T> p_168952_2_) {
         return this.a(CycleButton.c.a(CycleButton.a, p_168952_1_, p_168952_2_));
      }

      public CycleButton.a<T> a(BooleanSupplier p_168955_1_, List<T> p_168955_2_, List<T> p_168955_3_) {
         return this.a(CycleButton.c.a(p_168955_1_, p_168955_2_, p_168955_3_));
      }

      public CycleButton.a<T> a(CycleButton.c<T> p_232500_1_) {
         this.f = p_232500_1_;
         return this;
      }

      public CycleButton.a<T> a(OptionInstance.l<T> p_232498_1_) {
         this.d = p_232498_1_;
         return this;
      }

      public CycleButton.a<T> a(T p_168948_1_) {
         this.b = p_168948_1_;
         int i = this.f.b().indexOf(p_168948_1_);
         if (i != -1) {
            this.a = i;
         }

         return this;
      }

      public CycleButton.a<T> a(Function<CycleButton<T>, C_5012_> p_168959_1_) {
         this.e = p_168959_1_;
         return this;
      }

      public CycleButton.a<T> a() {
         this.g = true;
         return this;
      }

      public CycleButton<T> a(C_4996_ p_323445_1_, CycleButton.b<T> p_323445_2_) {
         return this.a(0, 0, 150, 20, p_323445_1_, p_323445_2_);
      }

      public CycleButton<T> a(int p_168930_1_, int p_168930_2_, int p_168930_3_, int p_168930_4_, C_4996_ p_168930_5_) {
         return this.a(p_168930_1_, p_168930_2_, p_168930_3_, p_168930_4_, p_168930_5_, (p_168945_0_, p_168945_1_) -> {
         });
      }

      public CycleButton<T> a(int p_168936_1_, int p_168936_2_, int p_168936_3_, int p_168936_4_, C_4996_ p_168936_5_, CycleButton.b<T> p_168936_6_) {
         List<T> list = this.f.b();
         if (list.isEmpty()) {
            throw new IllegalStateException("No values for cycle button");
         } else {
            T t = (T)(this.b != null ? this.b : list.get(this.a));
            C_4996_ component = (C_4996_)this.c.apply(t);
            C_4996_ component1 = (C_4996_)(this.g ? component : C_4995_.m_178393_(p_168936_5_, component));
            return new CycleButton<>(
               p_168936_1_, p_168936_2_, p_168936_3_, p_168936_4_, component1, p_168936_5_, this.a, t, this.f, this.c, this.e, p_168936_6_, this.d, this.g
            );
         }
      }
   }

   public interface b<T> {
      void onValueChange(CycleButton<T> var1, T var2);
   }

   public interface c<T> {
      List<T> a();

      List<T> b();

      static <T> CycleButton.c<T> a(Collection<T> p_232504_0_) {
         final List<T> list = ImmutableList.copyOf(p_232504_0_);
         return new CycleButton.c<T>() {
            @Override
            public List<T> a() {
               return list;
            }

            @Override
            public List<T> b() {
               return list;
            }
         };
      }

      static <T> CycleButton.c<T> a(final BooleanSupplier p_168970_0_, List<T> p_168970_1_, List<T> p_168970_2_) {
         final List<T> list = ImmutableList.copyOf(p_168970_1_);
         final List<T> list1 = ImmutableList.copyOf(p_168970_2_);
         return new CycleButton.c<T>() {
            @Override
            public List<T> a() {
               return p_168970_0_.getAsBoolean() ? list1 : list;
            }

            @Override
            public List<T> b() {
               return list;
            }
         };
      }
   }
}
