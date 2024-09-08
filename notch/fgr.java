package net.minecraft.src;

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
import net.optifine.config.FloatOptions;
import net.optifine.config.SliderPercentageOptionOF;
import net.optifine.config.SliderableValueSetInt;
import net.optifine.gui.IOptionControl;
import org.slf4j.Logger;

public class C_213334_<T> {
   private static final Logger f_231472_ = LogUtils.getLogger();
   public static final C_213334_.C_213340_<Boolean> f_231471_ = new C_213334_.C_213340_<>(ImmutableList.of(Boolean.TRUE, Boolean.FALSE), Codec.BOOL);
   public static final C_213334_.C_213336_<Boolean> f_260471_ = (compIn, boolIn) -> boolIn ? C_4995_.f_130653_ : C_4995_.f_130654_;
   private final C_213334_.C_213348_<T> f_231474_;
   final Function<T, C_4996_> f_231475_;
   private final C_213334_.C_213352_<T> f_231476_;
   private final Codec<T> f_231477_;
   private final T f_231478_;
   private final Consumer<T> f_231479_;
   final C_4996_ f_231480_;
   T f_231481_;
   private String resourceKey;
   public static final Map<String, C_213334_> OPTIONS_BY_KEY = new LinkedHashMap();

   public static C_213334_<Boolean> m_231528_(String keyIn, boolean initValueIn, Consumer<Boolean> onUpdateIn) {
      return m_257874_(keyIn, m_231498_(), initValueIn, onUpdateIn);
   }

   public static C_213334_<Boolean> m_231525_(String keyIn, boolean initValueIn) {
      return m_257874_(keyIn, m_231498_(), initValueIn, onUpdateIn -> {
      });
   }

   public static C_213334_<Boolean> m_257536_(String keyIn, C_213334_.C_213348_<Boolean> tooltipIn, boolean initValueIn) {
      return m_257874_(keyIn, tooltipIn, initValueIn, onUpdateIn -> {
      });
   }

   public static C_213334_<Boolean> m_257874_(String keyIn, C_213334_.C_213348_<Boolean> tooltipIn, boolean initValueIn, Consumer<Boolean> onUpdateIn) {
      return m_260965_(keyIn, tooltipIn, f_260471_, initValueIn, onUpdateIn);
   }

   public static C_213334_<Boolean> m_260965_(
      String keyIn, C_213334_.C_213348_<Boolean> tooltipIn, C_213334_.C_213336_<Boolean> toStringIn, boolean initValueIn, Consumer<Boolean> onUpdateIn
   ) {
      return new C_213334_<>(keyIn, tooltipIn, toStringIn, f_231471_, initValueIn, onUpdateIn);
   }

   public C_213334_(
      String keyIn, C_213334_.C_213348_<T> tooltipIn, C_213334_.C_213336_<T> toStringIn, C_213334_.C_213352_<T> valuesIn, T initValueIn, Consumer<T> onUpdateIn
   ) {
      this(keyIn, tooltipIn, toStringIn, valuesIn, valuesIn.m_213664_(), initValueIn, onUpdateIn);
   }

   public C_213334_(
      String keyIn,
      C_213334_.C_213348_<T> tooltipIn,
      C_213334_.C_213336_<T> toStringIn,
      C_213334_.C_213352_<T> valuesIn,
      Codec<T> codecIn,
      T initValueIn,
      Consumer<T> onUpdateIn
   ) {
      this.f_231480_ = C_4996_.m_237115_(keyIn);
      this.f_231474_ = tooltipIn;
      this.f_231475_ = objIn -> toStringIn.m_231580_(this.f_231480_, (T)objIn);
      this.f_231476_ = valuesIn;
      this.f_231477_ = codecIn;
      this.f_231478_ = initValueIn;
      this.f_231479_ = onUpdateIn;
      this.f_231481_ = this.f_231478_;
      this.resourceKey = keyIn;
      OPTIONS_BY_KEY.put(this.resourceKey, this);
   }

   public static <T> C_213334_.C_213348_<T> m_231498_() {
      return objIn -> null;
   }

   public static <T> C_213334_.C_213348_<T> m_231535_(C_4996_ componentIn) {
      return objIn -> C_256714_.m_257550_(componentIn);
   }

   public static <T extends C_212973_> C_213334_.C_213336_<T> m_231546_() {
      return (compIn, objIn) -> objIn.m_216301_();
   }

   public C_3449_ m_324463_(C_3401_ optionsIn) {
      return this.m_231507_(optionsIn, 0, 0, 150);
   }

   public C_3449_ m_231507_(C_3401_ optionsIn, int x, int y, int width) {
      return this.m_261194_(optionsIn, x, y, width, objIn -> {
      });
   }

   public C_3449_ m_261194_(C_3401_ optionsIn, int x, int y, int width, Consumer<T> onUpdateIn) {
      return (C_3449_)this.f_231476_.m_213823_(this.f_231474_, optionsIn, x, y, width, onUpdateIn).apply(this);
   }

   public T m_231551_() {
      if (this instanceof SliderPercentageOptionOF spo) {
         if (this.f_231481_ instanceof Integer) {
            return (T)(int)spo.getOptionValue();
         }

         if (this.f_231481_ instanceof Double) {
            return (T)spo.getOptionValue();
         }
      }

      return this.f_231481_;
   }

   public Codec<T> m_231554_() {
      return this.f_231477_;
   }

   public String toString() {
      return this.f_231480_.getString();
   }

   public void m_231514_(T valueIn) {
      T t = (T)this.f_231476_.m_214064_(valueIn).orElseGet(() -> {
         f_231472_.error("Illegal option value " + valueIn + " for " + this.f_231480_);
         return this.f_231478_;
      });
      if (!C_3391_.m_91087_().m_91396_()) {
         this.f_231481_ = t;
      } else if (!Objects.equals(this.f_231481_, t)) {
         this.f_231481_ = t;
         this.f_231479_.accept(this.f_231481_);
      }
   }

   public C_213334_.C_213352_<T> m_231555_() {
      return this.f_231476_;
   }

   public String getResourceKey() {
      return this.resourceKey;
   }

   public C_4996_ getCaption() {
      return this.f_231480_;
   }

   public T getMinValue() {
      C_213334_.C_213342_ intRange = this.getIntRangeBase();
      if (intRange != null) {
         return (T)intRange.m_214123_();
      } else {
         throw new IllegalArgumentException("Min value not supported: " + this.getResourceKey());
      }
   }

   public T getMaxValue() {
      C_213334_.C_213342_ intRange = this.getIntRangeBase();
      if (intRange != null) {
         return (T)intRange.m_214118_();
      } else {
         throw new IllegalArgumentException("Max value not supported: " + this.getResourceKey());
      }
   }

   public C_213334_.C_213342_ getIntRangeBase() {
      if (this.f_231476_ instanceof C_213334_.C_213342_) {
         return (C_213334_.C_213342_)this.f_231476_;
      } else {
         return this.f_231476_ instanceof SliderableValueSetInt ? ((SliderableValueSetInt)this.f_231476_).getIntRange() : null;
      }
   }

   public boolean isProgressOption() {
      return this.f_231476_ instanceof C_213334_.C_213347_;
   }

   public static record C_213335_<T>(
      List<T> f_231557_, List<T> f_231558_, BooleanSupplier f_231559_, C_213334_.C_213338_.C_213339_<T> f_231560_, Codec<T> f_231561_
   ) implements C_213334_.C_213338_<T> {
      @Override
      public C_141591_.C_141595_<T> m_213889_() {
         return C_141591_.C_141595_.m_168970_(this.f_231559_, this.f_231557_, this.f_231558_);
      }

      @Override
      public Optional<T> m_214064_(T valueIn) {
         return (this.f_231559_.getAsBoolean() ? this.f_231558_ : this.f_231557_).contains(valueIn) ? Optional.of(valueIn) : Optional.empty();
      }

      @Override
      public C_213334_.C_213338_.C_213339_<T> m_213569_() {
         return this.f_231560_;
      }

      @Override
      public Codec<T> m_213664_() {
         return this.f_231561_;
      }

      public C_213334_.C_213338_.C_213339_<T> valueSetter() {
         return this.f_231560_;
      }

      public Codec<T> codec() {
         return this.f_231561_;
      }
   }

   public interface C_213336_<T> {
      C_4996_ m_231580_(C_4996_ var1, T var2);
   }

   public static record C_213337_(int f_231583_, IntSupplier f_231584_, int f_276069_) implements C_213334_.C_213342_, C_213334_.C_213346_<Integer> {
      public Optional<Integer> m_214064_(Integer valueIn) {
         return Optional.of(C_188_.m_14045_(valueIn, this.m_214123_(), this.m_214118_()));
      }

      @Override
      public int m_214118_() {
         return this.f_231584_.getAsInt();
      }

      @Override
      public Codec<Integer> m_213664_() {
         return Codec.INT
            .validate(
               valIn -> {
                  int i = this.f_276069_ + 1;
                  return valIn.compareTo(this.f_231583_) >= 0 && valIn.compareTo(i) <= 0
                     ? DataResult.success(valIn)
                     : DataResult.error(() -> "Value " + valIn + " outside of range [" + this.f_231583_ + ":" + i + "]", valIn);
               }
            );
      }

      @Override
      public boolean m_214105_() {
         return true;
      }

      @Override
      public C_141591_.C_141595_<Integer> m_213889_() {
         return C_141591_.C_141595_.m_232504_(IntStream.range(this.f_231583_, this.m_214118_() + 1).boxed().toList());
      }

      @Override
      public int m_214123_() {
         return this.f_231583_;
      }
   }

   interface C_213338_<T> extends C_213334_.C_213352_<T> {
      C_141591_.C_141595_<T> m_213889_();

      default C_213334_.C_213338_.C_213339_<T> m_213569_() {
         return C_213334_::m_231514_;
      }

      @Override
      default Function<C_213334_<T>, C_3449_> m_213823_(C_213334_.C_213348_<T> tooltipIn, C_3401_ optionsIn, int x, int y, int width, Consumer<T> onUpdateIn) {
         return optionIn -> C_141591_.<T>m_168894_(optionIn.f_231475_)
               .m_232500_(this.m_213889_())
               .m_232498_(tooltipIn)
               .m_168948_(optionIn.f_231481_)
               .m_168936_(x, y, width, 20, optionIn.f_231480_, (btnIn, valIn) -> {
                  this.m_213569_().m_231622_(optionIn, valIn);
                  optionsIn.m_92169_();
                  onUpdateIn.accept(valIn);
               });
      }

      public interface C_213339_<T> {
         void m_231622_(C_213334_<T> var1, T var2);
      }
   }

   public static record C_213340_<T>(List<T> f_231625_, Codec<T> f_231626_) implements C_213334_.C_213338_<T> {
      @Override
      public Optional<T> m_214064_(T valueIn) {
         return this.f_231625_.contains(valueIn) ? Optional.of(valueIn) : Optional.empty();
      }

      @Override
      public C_141591_.C_141595_<T> m_213889_() {
         return C_141591_.C_141595_.m_232504_(this.f_231625_);
      }

      @Override
      public Codec<T> m_213664_() {
         return this.f_231626_;
      }

      public Codec<T> codec() {
         return this.f_231626_;
      }
   }

   public static record C_213341_(int f_231639_, int f_231640_, boolean f_316444_) implements C_213334_.C_213342_ {
      public C_213341_(int minInclusive, int maxInclusive) {
         this(minInclusive, maxInclusive, true);
      }

      public Optional<Integer> m_214064_(Integer valueIn) {
         return valueIn.compareTo(this.m_214123_()) >= 0 && valueIn.compareTo(this.m_214118_()) <= 0 ? Optional.of(valueIn) : Optional.empty();
      }

      @Override
      public Codec<Integer> m_213664_() {
         return Codec.intRange(this.f_231639_, this.f_231640_ + 1);
      }

      @Override
      public int m_214123_() {
         return this.f_231639_;
      }

      @Override
      public int m_214118_() {
         return this.f_231640_;
      }

      @Override
      public boolean m_320795_() {
         return this.f_316444_;
      }
   }

   public interface C_213342_ extends C_213334_.C_213347_<Integer> {
      int m_214123_();

      int m_214118_();

      default double m_213640_(Integer valueIn) {
         if (valueIn == this.m_214123_()) {
            return 0.0;
         } else {
            return valueIn == this.m_214118_()
               ? 1.0
               : C_188_.m_144914_((double)valueIn.intValue() + 0.5, (double)this.m_214123_(), (double)this.m_214118_() + 1.0, 0.0, 1.0);
         }
      }

      default Integer m_213729_(double valueIn) {
         if (valueIn >= 1.0) {
            valueIn = 0.99999F;
         }

         return C_188_.m_14107_(C_188_.m_144914_(valueIn, 0.0, 1.0, (double)this.m_214123_(), (double)this.m_214118_() + 1.0));
      }

      default <R> C_213334_.C_213347_<R> m_231657_(final IntFunction<? extends R> intFuncIn, final ToIntFunction<? super R> toIntFuncIn) {
         return new SliderableValueSetInt<R>() {
            @Override
            public Optional<R> m_214064_(R valueIn) {
               return C_213342_.this.m_214064_(Integer.valueOf(toIntFuncIn.applyAsInt(valueIn))).map(intFuncIn::apply);
            }

            @Override
            public double m_213640_(R valueIn) {
               return C_213342_.this.m_213640_(toIntFuncIn.applyAsInt(valueIn));
            }

            @Override
            public R m_213729_(double valueIn) {
               return (R)intFuncIn.apply(C_213342_.this.m_213729_(valueIn));
            }

            @Override
            public Codec<R> m_213664_() {
               return C_213342_.this.m_213664_().xmap(intFuncIn::apply, toIntFuncIn::applyAsInt);
            }

            public C_213334_.C_213342_ getIntRange() {
               return C_213342_.this;
            }
         };
      }
   }

   public static record C_213344_<T>(Supplier<List<T>> f_231680_, Function<T, Optional<T>> f_231681_, Codec<T> f_231682_) implements C_213334_.C_213338_<T> {
      @Override
      public Optional<T> m_214064_(T valueIn) {
         return (Optional<T>)this.f_231681_.apply(valueIn);
      }

      @Override
      public C_141591_.C_141595_<T> m_213889_() {
         return C_141591_.C_141595_.m_232504_((Collection<T>)this.f_231680_.get());
      }

      @Override
      public Codec<T> m_213664_() {
         return this.f_231682_;
      }

      public Codec<T> codec() {
         return this.f_231682_;
      }
   }

   public static final class C_213345_<N> extends C_3442_ implements IOptionControl {
      private final C_213334_<N> f_231697_;
      private final C_213334_.C_213347_<N> f_231698_;
      private final C_213334_.C_213348_<N> f_256889_;
      private final Consumer<N> f_260531_;
      @Nullable
      private Long f_316943_;
      private final boolean f_316343_;
      private boolean supportAdjusting;
      private boolean adjusting;

      C_213345_(
         C_3401_ optionsIn,
         int xIn,
         int yIn,
         int widthIn,
         int heightIn,
         C_213334_<N> optionIn,
         C_213334_.C_213347_<N> valuesIn,
         C_213334_.C_213348_<N> tooltipIn,
         Consumer<N> onUpdateIn,
         boolean applyImmediatelyIn
      ) {
         super(optionsIn, xIn, yIn, widthIn, heightIn, valuesIn.m_213640_(optionIn.m_231551_()));
         this.f_231697_ = optionIn;
         this.f_231698_ = valuesIn;
         this.f_256889_ = tooltipIn;
         this.f_260531_ = onUpdateIn;
         this.f_316343_ = applyImmediatelyIn;
         this.m_5695_();
         this.supportAdjusting = FloatOptions.supportAdjusting(this.f_231697_);
         this.adjusting = false;
      }

      protected void m_5695_() {
         if (this.adjusting) {
            double denormValue = ((Number)this.f_231698_.m_213729_(this.c)).doubleValue();
            C_4996_ text = FloatOptions.getTextComponent(this.f_231697_, denormValue);
            if (text != null) {
               this.b(text);
            }
         } else {
            if (this.f_231697_ instanceof SliderPercentageOptionOF spo) {
               C_4996_ comp = spo.getOptionText();
               if (comp != null) {
                  this.b(comp);
               }
            } else {
               this.b((C_4996_)this.f_231697_.f_231475_.apply(this.f_231698_.m_213729_(this.c)));
            }

            this.a(this.f_256889_.m_257630_(this.f_231698_.m_213729_(this.c)));
         }
      }

      protected void m_5697_() {
         if (!this.adjusting) {
            N valOld = this.f_231697_.m_231551_();
            N valNew = this.f_231698_.m_213729_(this.c);
            if (!valNew.equals(valOld)) {
               if (this.f_231697_ instanceof SliderPercentageOptionOF spo) {
                  spo.setOptionValue(((Number)valNew).doubleValue());
               }

               if (this.f_316343_) {
                  this.m_323527_();
               } else {
                  this.f_316943_ = C_5322_.m_137550_() + 600L;
               }
            }
         }
      }

      public void m_323527_() {
         N n = this.f_231698_.m_213729_(this.c);
         if (!Objects.equals(n, this.f_231697_.m_231551_())) {
            this.f_231697_.m_231514_(n);
            this.f_93377_.m_92169_();
            this.f_260531_.accept(this.f_231697_.m_231551_());
         }
      }

      public void m_87963_(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
         super.b(graphicsIn, mouseX, mouseY, partialTicks);
         if (this.f_316943_ != null && C_5322_.m_137550_() >= this.f_316943_) {
            this.f_316943_ = null;
            this.m_323527_();
         }
      }

      public void a(double mouseX, double mouseY) {
         if (this.supportAdjusting) {
            this.adjusting = true;
         }

         super.a(mouseX, mouseY);
      }

      protected void b(double mouseX, double mouseY, double mouseDX, double mouseDY) {
         if (this.supportAdjusting) {
            this.adjusting = true;
         }

         super.b(mouseX, mouseY, mouseDX, mouseDY);
      }

      public void a_(double mouseX, double mouseY) {
         if (this.adjusting) {
            this.adjusting = false;
            this.m_5697_();
            this.m_5695_();
         }

         super.a_(mouseX, mouseY);
      }

      public C_213334_ getControlOption() {
         return this.f_231697_;
      }
   }

   interface C_213346_<T> extends C_213334_.C_213338_<T>, C_213334_.C_213347_<T> {
      boolean m_214105_();

      @Override
      default Function<C_213334_<T>, C_3449_> m_213823_(C_213334_.C_213348_<T> tooltipIn, C_3401_ optionsIn, int x, int y, int width, Consumer<T> onUpdateIn) {
         return this.m_214105_()
            ? C_213334_.C_213338_.super.m_213823_(tooltipIn, optionsIn, x, y, width, onUpdateIn)
            : C_213334_.C_213347_.super.m_213823_(tooltipIn, optionsIn, x, y, width, onUpdateIn);
      }
   }

   public interface C_213347_<T> extends C_213334_.C_213352_<T> {
      double m_213640_(T var1);

      T m_213729_(double var1);

      default boolean m_320795_() {
         return true;
      }

      @Override
      default Function<C_213334_<T>, C_3449_> m_213823_(C_213334_.C_213348_<T> tooltipIn, C_3401_ optionsIn, int x, int y, int width, Consumer<T> onUpdateIn) {
         return optionIn -> new C_213334_.C_213345_<T>(optionsIn, x, y, width, 20, optionIn, this, tooltipIn, onUpdateIn, this.m_320795_());
      }
   }

   @FunctionalInterface
   public interface C_213348_<T> {
      @Nullable
      C_256714_ m_257630_(T var1);
   }

   public static enum C_213350_ implements C_213334_.C_213347_<Double> {
      INSTANCE;

      public Optional<Double> m_214064_(Double valueIn) {
         return valueIn >= 0.0 && valueIn <= 1.0 ? Optional.of(valueIn) : Optional.empty();
      }

      public double m_213640_(Double valueIn) {
         return valueIn;
      }

      public Double m_213729_(double valueIn) {
         return valueIn;
      }

      public <R> C_213334_.C_213347_<R> m_231750_(final DoubleFunction<? extends R> doubleFuncIn, final ToDoubleFunction<? super R> toDoubleFuncIn) {
         return new C_213334_.C_213347_<R>() {
            @Override
            public Optional<R> m_214064_(R valueIn) {
               return C_213350_.this.m_214064_(toDoubleFuncIn.applyAsDouble(valueIn)).map(doubleFuncIn::apply);
            }

            @Override
            public double m_213640_(R valueIn) {
               return C_213350_.this.m_213640_(toDoubleFuncIn.applyAsDouble(valueIn));
            }

            @Override
            public R m_213729_(double valueIn) {
               return (R)doubleFuncIn.apply(C_213350_.this.m_213729_(valueIn));
            }

            @Override
            public Codec<R> m_213664_() {
               return C_213350_.this.m_213664_().xmap(doubleFuncIn::apply, toDoubleFuncIn::applyAsDouble);
            }
         };
      }

      @Override
      public Codec<Double> m_213664_() {
         return Codec.withAlternative(Codec.doubleRange(0.0, 1.0), Codec.BOOL, flagIn -> flagIn ? 1.0 : 0.0);
      }
   }

   public interface C_213352_<T> {
      Function<C_213334_<T>, C_3449_> m_213823_(C_213334_.C_213348_<T> var1, C_3401_ var2, int var3, int var4, int var5, Consumer<T> var6);

      Optional<T> m_214064_(T var1);

      Codec<T> m_213664_();
   }
}
