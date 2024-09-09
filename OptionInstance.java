import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.src.C_212973_;
import net.minecraft.src.C_256714_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3442_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_4995_;
import net.minecraft.src.C_4996_;
import net.optifine.config.FloatOptions;
import net.optifine.config.SliderPercentageOptionOF;
import net.optifine.config.SliderableValueSetInt;
import net.optifine.gui.IOptionControl;
import org.slf4j.Logger;

public class OptionInstance<T> {
   private static final Logger c = LogUtils.getLogger();
   public static final OptionInstance.e<Boolean> a = new OptionInstance.e<>(ImmutableList.of(Boolean.TRUE, Boolean.FALSE), Codec.BOOL);
   public static final OptionInstance.b<Boolean> b = (compIn, boolIn) -> boolIn ? C_4995_.f_130653_ : C_4995_.f_130654_;
   private final OptionInstance.l<T> d;
   final Function<T, C_4996_> e;
   private final OptionInstance.n<T> f;
   private final Codec<T> g;
   private final T h;
   private final Consumer<T> i;
   final C_4996_ j;
   T k;
   private String resourceKey;
   public static final Map<String, OptionInstance> OPTIONS_BY_KEY = new LinkedHashMap();

   public static OptionInstance<Boolean> a(String keyIn, boolean initValueIn, Consumer<Boolean> onUpdateIn) {
      return a(keyIn, a(), initValueIn, onUpdateIn);
   }

   public static OptionInstance<Boolean> a(String keyIn, boolean initValueIn) {
      return a(keyIn, a(), initValueIn, onUpdateIn -> {
      });
   }

   public static OptionInstance<Boolean> a(String keyIn, OptionInstance.l<Boolean> tooltipIn, boolean initValueIn) {
      return a(keyIn, tooltipIn, initValueIn, onUpdateIn -> {
      });
   }

   public static OptionInstance<Boolean> a(String keyIn, OptionInstance.l<Boolean> tooltipIn, boolean initValueIn, Consumer<Boolean> onUpdateIn) {
      return a(keyIn, tooltipIn, b, initValueIn, onUpdateIn);
   }

   public static OptionInstance<Boolean> a(
      String keyIn, OptionInstance.l<Boolean> tooltipIn, OptionInstance.b<Boolean> toStringIn, boolean initValueIn, Consumer<Boolean> onUpdateIn
   ) {
      return new OptionInstance<>(keyIn, tooltipIn, toStringIn, a, initValueIn, onUpdateIn);
   }

   public OptionInstance(
      String keyIn, OptionInstance.l<T> tooltipIn, OptionInstance.b<T> toStringIn, OptionInstance.n<T> valuesIn, T initValueIn, Consumer<T> onUpdateIn
   ) {
      this(keyIn, tooltipIn, toStringIn, valuesIn, valuesIn.f(), initValueIn, onUpdateIn);
   }

   public OptionInstance(
      String keyIn,
      OptionInstance.l<T> tooltipIn,
      OptionInstance.b<T> toStringIn,
      OptionInstance.n<T> valuesIn,
      Codec<T> codecIn,
      T initValueIn,
      Consumer<T> onUpdateIn
   ) {
      this.j = C_4996_.m_237115_(keyIn);
      this.d = tooltipIn;
      this.e = objIn -> toStringIn.toString(this.j, (T)objIn);
      this.f = valuesIn;
      this.g = codecIn;
      this.h = initValueIn;
      this.i = onUpdateIn;
      this.k = this.h;
      this.resourceKey = keyIn;
      OPTIONS_BY_KEY.put(this.resourceKey, this);
   }

   public static <T> OptionInstance.l<T> a() {
      return objIn -> null;
   }

   public static <T> OptionInstance.l<T> a(C_4996_ componentIn) {
      return objIn -> C_256714_.m_257550_(componentIn);
   }

   public static <T extends C_212973_> OptionInstance.b<T> b() {
      return (compIn, objIn) -> objIn.m_216301_();
   }

   public C_3449_ a(Options optionsIn) {
      return this.a(optionsIn, 0, 0, 150);
   }

   public C_3449_ a(Options optionsIn, int x, int y, int width) {
      return this.a(optionsIn, x, y, width, objIn -> {
      });
   }

   public C_3449_ a(Options optionsIn, int x, int y, int width, Consumer<T> onUpdateIn) {
      return (C_3449_)this.f.a(this.d, optionsIn, x, y, width, onUpdateIn).apply(this);
   }

   public T c() {
      if (this instanceof SliderPercentageOptionOF spo) {
         if (this.k instanceof Integer) {
            return (T)(int)spo.getOptionValue();
         }

         if (this.k instanceof Double) {
            return (T)spo.getOptionValue();
         }
      }

      return this.k;
   }

   public Codec<T> d() {
      return this.g;
   }

   public String toString() {
      return this.j.getString();
   }

   public void a(T valueIn) {
      T t = (T)this.f.a(valueIn).orElseGet(() -> {
         c.error("Illegal option value " + valueIn + " for " + this.j);
         return this.h;
      });
      if (!C_3391_.m_91087_().m_91396_()) {
         this.k = t;
      } else if (!Objects.equals(this.k, t)) {
         this.k = t;
         this.i.accept(this.k);
      }
   }

   public OptionInstance.n<T> e() {
      return this.f;
   }

   public String getResourceKey() {
      return this.resourceKey;
   }

   public C_4996_ getCaption() {
      return this.j;
   }

   public T getMinValue() {
      OptionInstance.g intRange = this.getIntRangeBase();
      if (intRange != null) {
         return (T)intRange.d();
      } else {
         throw new IllegalArgumentException("Min value not supported: " + this.getResourceKey());
      }
   }

   public T getMaxValue() {
      OptionInstance.g intRange = this.getIntRangeBase();
      if (intRange != null) {
         return (T)intRange.b();
      } else {
         throw new IllegalArgumentException("Max value not supported: " + this.getResourceKey());
      }
   }

   public OptionInstance.g getIntRangeBase() {
      if (this.f instanceof OptionInstance.g) {
         return (OptionInstance.g)this.f;
      } else {
         return this.f instanceof SliderableValueSetInt ? ((SliderableValueSetInt)this.f).getIntRange() : null;
      }
   }

   public boolean isProgressOption() {
      return this.f instanceof OptionInstance.k;
   }

   public static record a<T>(List<T> a, List<T> b, BooleanSupplier c, OptionInstance.d.a<T> d, Codec<T> e) implements OptionInstance.d<T> {
      @Override
      public CycleButton.c<T> a() {
         return CycleButton.c.a(this.c, this.a, this.b);
      }

      @Override
      public Optional<T> a(T valueIn) {
         return (this.c.getAsBoolean() ? this.b : this.a).contains(valueIn) ? Optional.of(valueIn) : Optional.empty();
      }

      @Override
      public OptionInstance.d.a<T> e() {
         return this.d;
      }

      @Override
      public Codec<T> f() {
         return this.e;
      }

      public List<T> b() {
         return this.a;
      }

      public List<T> c() {
         return this.b;
      }

      public BooleanSupplier d() {
         return this.c;
      }

      public OptionInstance.d.a<T> valueSetter() {
         return this.d;
      }

      public Codec<T> codec() {
         return this.e;
      }
   }

   public interface b<T> {
      C_4996_ toString(C_4996_ var1, T var2);
   }

   public static record c(int a, IntSupplier b, int c) implements OptionInstance.g, OptionInstance.j<Integer> {
      public Optional<Integer> a(Integer valueIn) {
         return Optional.of(Mth.a(valueIn, this.d(), this.b()));
      }

      @Override
      public int b() {
         return this.b.getAsInt();
      }

      @Override
      public Codec<Integer> f() {
         return Codec.INT
            .validate(
               valIn -> {
                  int i = this.c + 1;
                  return valIn.compareTo(this.a) >= 0 && valIn.compareTo(i) <= 0
                     ? DataResult.success(valIn)
                     : DataResult.error(() -> "Value " + valIn + " outside of range [" + this.a + ":" + i + "]", valIn);
               }
            );
      }

      @Override
      public boolean c() {
         return true;
      }

      @Override
      public CycleButton.c<Integer> a() {
         return CycleButton.c.a(IntStream.range(this.a, this.b() + 1).boxed().toList());
      }

      @Override
      public int d() {
         return this.a;
      }

      public IntSupplier g() {
         return this.b;
      }

      public int h() {
         return this.c;
      }
   }

   interface d<T> extends OptionInstance.n<T> {
      CycleButton.c<T> a();

      default OptionInstance.d.a<T> e() {
         return OptionInstance::a;
      }

      @Override
      default Function<OptionInstance<T>, C_3449_> a(OptionInstance.l<T> tooltipIn, Options optionsIn, int x, int y, int width, Consumer<T> onUpdateIn) {
         return optionIn -> CycleButton.<T>a(optionIn.e).a(this.a()).a(tooltipIn).a(optionIn.k).a(x, y, width, 20, optionIn.j, (btnIn, valIn) -> {
               this.e().set(optionIn, valIn);
               optionsIn.aw();
               onUpdateIn.accept(valIn);
            });
      }

      public interface a<T> {
         void set(OptionInstance<T> var1, T var2);
      }
   }

   public static record e<T>(List<T> a, Codec<T> b) implements OptionInstance.d<T> {
      @Override
      public Optional<T> a(T valueIn) {
         return this.a.contains(valueIn) ? Optional.of(valueIn) : Optional.empty();
      }

      @Override
      public CycleButton.c<T> a() {
         return CycleButton.c.a(this.a);
      }

      @Override
      public Codec<T> f() {
         return this.b;
      }

      public List<T> b() {
         return this.a;
      }

      public Codec<T> codec() {
         return this.b;
      }
   }

   public static record f(int a, int b, boolean c) implements OptionInstance.g {
      public f(int minInclusive, int maxInclusive) {
         this(minInclusive, maxInclusive, true);
      }

      public Optional<Integer> a(Integer valueIn) {
         return valueIn.compareTo(this.d()) >= 0 && valueIn.compareTo(this.b()) <= 0 ? Optional.of(valueIn) : Optional.empty();
      }

      @Override
      public Codec<Integer> f() {
         return Codec.intRange(this.a, this.b + 1);
      }

      @Override
      public int d() {
         return this.a;
      }

      @Override
      public boolean aR_() {
         return this.c;
      }
   }

   public interface g extends OptionInstance.k<Integer> {
      int d();

      int b();

      default double b(Integer valueIn) {
         if (valueIn == this.d()) {
            return 0.0;
         } else {
            return valueIn == this.b() ? 1.0 : Mth.b((double)valueIn.intValue() + 0.5, (double)this.d(), (double)this.b() + 1.0, 0.0, 1.0);
         }
      }

      default Integer a(double valueIn) {
         if (valueIn >= 1.0) {
            valueIn = 0.99999F;
         }

         return Mth.a(Mth.b(valueIn, 0.0, 1.0, (double)this.d(), (double)this.b() + 1.0));
      }

      default <R> OptionInstance.k<R> a(final IntFunction<? extends R> intFuncIn, final ToIntFunction<? super R> toIntFuncIn) {
         return new SliderableValueSetInt<R>() {
            public Optional<R> a(R valueIn) {
               return g.this.a(Integer.valueOf(toIntFuncIn.applyAsInt(valueIn))).map(intFuncIn::apply);
            }

            public double b(R valueIn) {
               return g.this.b(toIntFuncIn.applyAsInt(valueIn));
            }

            public R b(double valueIn) {
               return (R)intFuncIn.apply(g.this.a(valueIn));
            }

            public Codec<R> f() {
               return g.this.f().xmap(intFuncIn::apply, toIntFuncIn::applyAsInt);
            }

            public OptionInstance.g getIntRange() {
               return g.this;
            }
         };
      }
   }

   public static record h<T>(Supplier<List<T>> a, Function<T, Optional<T>> b, Codec<T> c) implements OptionInstance.d<T> {
      @Override
      public Optional<T> a(T valueIn) {
         return (Optional<T>)this.b.apply(valueIn);
      }

      @Override
      public CycleButton.c<T> a() {
         return CycleButton.c.a((Collection<T>)this.a.get());
      }

      @Override
      public Codec<T> f() {
         return this.c;
      }

      public Supplier<List<T>> b() {
         return this.a;
      }

      public Function<T, Optional<T>> c() {
         return this.b;
      }

      public Codec<T> codec() {
         return this.c;
      }
   }

   public static final class i<N> extends C_3442_ implements IOptionControl {
      private final OptionInstance<N> d;
      private final OptionInstance.k<N> e;
      private final OptionInstance.l<N> f;
      private final Consumer<N> m;
      @Nullable
      private Long n;
      private final boolean f_263772_;
      private boolean supportAdjusting;
      private boolean adjusting;

      i(
         Options optionsIn,
         int xIn,
         int yIn,
         int widthIn,
         int heightIn,
         OptionInstance<N> optionIn,
         OptionInstance.k<N> valuesIn,
         OptionInstance.l<N> tooltipIn,
         Consumer<N> onUpdateIn,
         boolean applyImmediatelyIn
      ) {
         super(optionsIn, xIn, yIn, widthIn, heightIn, valuesIn.b(optionIn.c()));
         this.d = optionIn;
         this.e = valuesIn;
         this.f = tooltipIn;
         this.m = onUpdateIn;
         this.f_263772_ = applyImmediatelyIn;
         this.m_5695_();
         this.supportAdjusting = FloatOptions.supportAdjusting(this.d);
         this.adjusting = false;
      }

      protected void m_5695_() {
         if (this.adjusting) {
            double denormValue = ((Number)this.e.b(this.f_93577_)).doubleValue();
            C_4996_ text = FloatOptions.getTextComponent(this.d, denormValue);
            if (text != null) {
               this.m_93666_(text);
            }
         } else {
            if (this.d instanceof SliderPercentageOptionOF spo) {
               C_4996_ comp = spo.getOptionText();
               if (comp != null) {
                  this.m_93666_(comp);
               }
            } else {
               this.m_93666_((C_4996_)this.d.e.apply(this.e.b(this.f_93577_)));
            }

            this.m_257544_(this.f.apply(this.e.b(this.f_93577_)));
         }
      }

      protected void m_5697_() {
         if (!this.adjusting) {
            N valOld = this.d.c();
            N valNew = this.e.b(this.f_93577_);
            if (!valNew.equals(valOld)) {
               if (this.d instanceof SliderPercentageOptionOF spo) {
                  spo.setOptionValue(((Number)valNew).doubleValue());
               }

               if (this.f_263772_) {
                  this.c();
               } else {
                  this.n = Util.c() + 600L;
               }
            }
         }
      }

      public void c() {
         N n = this.e.b(this.f_93577_);
         if (!Objects.equals(n, this.d.c())) {
            this.d.a(n);
            this.a.aw();
            this.m.accept(this.d.c());
         }
      }

      public void b(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
         super.b(graphicsIn, mouseX, mouseY, partialTicks);
         if (this.n != null && Util.c() >= this.n) {
            this.n = null;
            this.c();
         }
      }

      public void m_5716_(double mouseX, double mouseY) {
         if (this.supportAdjusting) {
            this.adjusting = true;
         }

         super.m_5716_(mouseX, mouseY);
      }

      protected void m_7212_(double mouseX, double mouseY, double mouseDX, double mouseDY) {
         if (this.supportAdjusting) {
            this.adjusting = true;
         }

         super.m_7212_(mouseX, mouseY, mouseDX, mouseDY);
      }

      public void m_7691_(double mouseX, double mouseY) {
         if (this.adjusting) {
            this.adjusting = false;
            this.m_5697_();
            this.m_5695_();
         }

         super.m_7691_(mouseX, mouseY);
      }

      public OptionInstance getControlOption() {
         return this.d;
      }
   }

   interface j<T> extends OptionInstance.d<T>, OptionInstance.k<T> {
      boolean c();

      @Override
      default Function<OptionInstance<T>, C_3449_> a(OptionInstance.l<T> tooltipIn, Options optionsIn, int x, int y, int width, Consumer<T> onUpdateIn) {
         return this.c()
            ? OptionInstance.d.super.a(tooltipIn, optionsIn, x, y, width, onUpdateIn)
            : OptionInstance.k.super.a(tooltipIn, optionsIn, x, y, width, onUpdateIn);
      }
   }

   public interface k<T> extends OptionInstance.n<T> {
      double b(T var1);

      T b(double var1);

      default boolean aR_() {
         return true;
      }

      @Override
      default Function<OptionInstance<T>, C_3449_> a(OptionInstance.l<T> tooltipIn, Options optionsIn, int x, int y, int width, Consumer<T> onUpdateIn) {
         return optionIn -> new OptionInstance.i<T>(optionsIn, x, y, width, 20, optionIn, this, tooltipIn, onUpdateIn, this.aR_());
      }
   }

   @FunctionalInterface
   public interface l<T> {
      @Nullable
      C_256714_ apply(T var1);
   }

   public static enum m implements OptionInstance.k<Double> {
      a;

      public Optional<Double> a(Double valueIn) {
         return valueIn >= 0.0 && valueIn <= 1.0 ? Optional.of(valueIn) : Optional.empty();
      }

      public double b(Double valueIn) {
         return valueIn;
      }

      public Double a(double valueIn) {
         return valueIn;
      }

      public <R> OptionInstance.k<R> a(final DoubleFunction<? extends R> doubleFuncIn, final ToDoubleFunction<? super R> toDoubleFuncIn) {
         return new OptionInstance.k<R>() {
            @Override
            public Optional<R> a(R valueIn) {
               return m.this.a(Double.valueOf(toDoubleFuncIn.applyAsDouble(valueIn))).map(doubleFuncIn::apply);
            }

            @Override
            public double b(R valueIn) {
               return m.this.b(Double.valueOf(toDoubleFuncIn.applyAsDouble(valueIn)));
            }

            @Override
            public R b(double valueIn) {
               return (R)doubleFuncIn.apply(m.this.a(valueIn));
            }

            @Override
            public Codec<R> f() {
               return m.this.f().xmap(doubleFuncIn::apply, toDoubleFuncIn::applyAsDouble);
            }
         };
      }

      @Override
      public Codec<Double> f() {
         return Codec.withAlternative(Codec.doubleRange(0.0, 1.0), Codec.BOOL, flagIn -> flagIn ? 1.0 : 0.0);
      }
   }

   public interface n<T> {
      Function<OptionInstance<T>, C_3449_> a(OptionInstance.l<T> var1, Options var2, int var3, int var4, int var5, Consumer<T> var6);

      Optional<T> a(T var1);

      Codec<T> f();
   }
}
