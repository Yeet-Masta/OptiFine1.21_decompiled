package net.minecraft.client;

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
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;
import net.optifine.config.FloatOptions;
import net.optifine.config.SliderPercentageOptionOF;
import net.optifine.config.SliderableValueSetInt;
import net.optifine.gui.IOptionControl;
import org.slf4j.Logger;

public class OptionInstance<T> {
   private static final Logger f_231472_ = LogUtils.getLogger();
   public static final OptionInstance.Enum<Boolean> f_231471_ = new OptionInstance.Enum<>(ImmutableList.of(Boolean.TRUE, Boolean.FALSE), Codec.BOOL);
   public static final OptionInstance.CaptionBasedToString<Boolean> f_260471_ = (compIn, boolIn) -> boolIn
         ? CommonComponents.f_130653_
         : CommonComponents.f_130654_;
   private final OptionInstance.TooltipSupplier<T> f_231474_;
   final Function<T, Component> f_231475_;
   private final OptionInstance.ValueSet<T> f_231476_;
   private final Codec<T> f_231477_;
   private final T f_231478_;
   private final Consumer<T> f_231479_;
   final Component f_231480_;
   T f_231481_;
   private String resourceKey;
   public static final Map<String, OptionInstance> OPTIONS_BY_KEY = new LinkedHashMap();

   public static OptionInstance<Boolean> m_231528_(String keyIn, boolean initValueIn, Consumer<Boolean> onUpdateIn) {
      return m_257874_(keyIn, m_231498_(), initValueIn, onUpdateIn);
   }

   public static OptionInstance<Boolean> m_231525_(String keyIn, boolean initValueIn) {
      return m_257874_(keyIn, m_231498_(), initValueIn, onUpdateIn -> {
      });
   }

   public static OptionInstance<Boolean> m_257536_(String keyIn, OptionInstance.TooltipSupplier<Boolean> tooltipIn, boolean initValueIn) {
      return m_257874_(keyIn, tooltipIn, initValueIn, onUpdateIn -> {
      });
   }

   public static OptionInstance<Boolean> m_257874_(
      String keyIn, OptionInstance.TooltipSupplier<Boolean> tooltipIn, boolean initValueIn, Consumer<Boolean> onUpdateIn
   ) {
      return m_260965_(keyIn, tooltipIn, f_260471_, initValueIn, onUpdateIn);
   }

   public static OptionInstance<Boolean> m_260965_(
      String keyIn,
      OptionInstance.TooltipSupplier<Boolean> tooltipIn,
      OptionInstance.CaptionBasedToString<Boolean> toStringIn,
      boolean initValueIn,
      Consumer<Boolean> onUpdateIn
   ) {
      return new OptionInstance<>(keyIn, tooltipIn, toStringIn, f_231471_, initValueIn, onUpdateIn);
   }

   public OptionInstance(
      String keyIn,
      OptionInstance.TooltipSupplier<T> tooltipIn,
      OptionInstance.CaptionBasedToString<T> toStringIn,
      OptionInstance.ValueSet<T> valuesIn,
      T initValueIn,
      Consumer<T> onUpdateIn
   ) {
      this(keyIn, tooltipIn, toStringIn, valuesIn, valuesIn.m_213664_(), initValueIn, onUpdateIn);
   }

   public OptionInstance(
      String keyIn,
      OptionInstance.TooltipSupplier<T> tooltipIn,
      OptionInstance.CaptionBasedToString<T> toStringIn,
      OptionInstance.ValueSet<T> valuesIn,
      Codec<T> codecIn,
      T initValueIn,
      Consumer<T> onUpdateIn
   ) {
      this.f_231480_ = Component.m_237115_(keyIn);
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

   public static <T> OptionInstance.TooltipSupplier<T> m_231498_() {
      return objIn -> null;
   }

   public static <T> OptionInstance.TooltipSupplier<T> m_231535_(Component componentIn) {
      return objIn -> Tooltip.m_257550_(componentIn);
   }

   public static <T extends OptionEnum> OptionInstance.CaptionBasedToString<T> m_231546_() {
      return (compIn, objIn) -> objIn.m_216301_();
   }

   public AbstractWidget m_324463_(Options optionsIn) {
      return this.m_231507_(optionsIn, 0, 0, 150);
   }

   public AbstractWidget m_231507_(Options optionsIn, int x, int y, int width) {
      return this.m_261194_(optionsIn, x, y, width, objIn -> {
      });
   }

   public AbstractWidget m_261194_(Options optionsIn, int x, int y, int width, Consumer<T> onUpdateIn) {
      return (AbstractWidget)this.f_231476_.m_213823_(this.f_231474_, optionsIn, x, y, width, onUpdateIn).apply(this);
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
      if (!Minecraft.m_91087_().m_91396_()) {
         this.f_231481_ = t;
      } else if (!Objects.equals(this.f_231481_, t)) {
         this.f_231481_ = t;
         this.f_231479_.accept(this.f_231481_);
      }
   }

   public OptionInstance.ValueSet<T> m_231555_() {
      return this.f_231476_;
   }

   public String getResourceKey() {
      return this.resourceKey;
   }

   public Component getCaption() {
      return this.f_231480_;
   }

   public T getMinValue() {
      OptionInstance.IntRangeBase intRange = this.getIntRangeBase();
      if (intRange != null) {
         return (T)intRange.m_214123_();
      } else {
         throw new IllegalArgumentException("Min value not supported: " + this.getResourceKey());
      }
   }

   public T getMaxValue() {
      OptionInstance.IntRangeBase intRange = this.getIntRangeBase();
      if (intRange != null) {
         return (T)intRange.m_214118_();
      } else {
         throw new IllegalArgumentException("Max value not supported: " + this.getResourceKey());
      }
   }

   public OptionInstance.IntRangeBase getIntRangeBase() {
      if (this.f_231476_ instanceof OptionInstance.IntRangeBase) {
         return (OptionInstance.IntRangeBase)this.f_231476_;
      } else {
         return this.f_231476_ instanceof SliderableValueSetInt ? ((SliderableValueSetInt)this.f_231476_).getIntRange() : null;
      }
   }

   public boolean isProgressOption() {
      return this.f_231476_ instanceof OptionInstance.SliderableValueSet;
   }

   public static record AltEnum<T>(
      List<T> f_231557_, List<T> f_231558_, BooleanSupplier f_231559_, OptionInstance.CycleableValueSet.ValueSetter<T> f_231560_, Codec<T> f_231561_
   ) implements OptionInstance.CycleableValueSet<T> {
      @Override
      public CycleButton.ValueListSupplier<T> m_213889_() {
         return CycleButton.ValueListSupplier.m_168970_(this.f_231559_, this.f_231557_, this.f_231558_);
      }

      @Override
      public Optional<T> m_214064_(T valueIn) {
         return (this.f_231559_.getAsBoolean() ? this.f_231558_ : this.f_231557_).contains(valueIn) ? Optional.of(valueIn) : Optional.empty();
      }

      @Override
      public OptionInstance.CycleableValueSet.ValueSetter<T> m_213569_() {
         return this.f_231560_;
      }

      @Override
      public Codec<T> m_213664_() {
         return this.f_231561_;
      }

      public OptionInstance.CycleableValueSet.ValueSetter<T> valueSetter() {
         return this.f_231560_;
      }

      public Codec<T> codec() {
         return this.f_231561_;
      }
   }

   public interface CaptionBasedToString<T> {
      Component m_231580_(Component var1, T var2);
   }

   public static record ClampingLazyMaxIntRange(int f_231583_, IntSupplier f_231584_, int f_276069_)
      implements OptionInstance.IntRangeBase,
      OptionInstance.SliderableOrCyclableValueSet<Integer> {
      public Optional<Integer> m_214064_(Integer valueIn) {
         return Optional.of(Mth.m_14045_(valueIn, this.m_214123_(), this.m_214118_()));
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
      public CycleButton.ValueListSupplier<Integer> m_213889_() {
         return CycleButton.ValueListSupplier.m_232504_(IntStream.range(this.f_231583_, this.m_214118_() + 1).boxed().toList());
      }

      @Override
      public int m_214123_() {
         return this.f_231583_;
      }
   }

   interface CycleableValueSet<T> extends OptionInstance.ValueSet<T> {
      CycleButton.ValueListSupplier<T> m_213889_();

      default OptionInstance.CycleableValueSet.ValueSetter<T> m_213569_() {
         return OptionInstance::m_231514_;
      }

      @Override
      default Function<OptionInstance<T>, AbstractWidget> m_213823_(
         OptionInstance.TooltipSupplier<T> tooltipIn, Options optionsIn, int x, int y, int width, Consumer<T> onUpdateIn
      ) {
         return optionIn -> CycleButton.<T>m_168894_(optionIn.f_231475_)
               .m_232500_(this.m_213889_())
               .m_232498_(tooltipIn)
               .m_168948_(optionIn.f_231481_)
               .m_168936_(x, y, width, 20, optionIn.f_231480_, (btnIn, valIn) -> {
                  this.m_213569_().m_231622_(optionIn, valIn);
                  optionsIn.m_92169_();
                  onUpdateIn.accept(valIn);
               });
      }

      public interface ValueSetter<T> {
         void m_231622_(OptionInstance<T> var1, T var2);
      }
   }

   public static record Enum<T>(List<T> f_231625_, Codec<T> f_231626_) implements OptionInstance.CycleableValueSet<T> {
      @Override
      public Optional<T> m_214064_(T valueIn) {
         return this.f_231625_.contains(valueIn) ? Optional.of(valueIn) : Optional.empty();
      }

      @Override
      public CycleButton.ValueListSupplier<T> m_213889_() {
         return CycleButton.ValueListSupplier.m_232504_(this.f_231625_);
      }

      @Override
      public Codec<T> m_213664_() {
         return this.f_231626_;
      }

      public Codec<T> codec() {
         return this.f_231626_;
      }
   }

   public static record IntRange(int f_231639_, int f_231640_, boolean f_316444_) implements OptionInstance.IntRangeBase {
      public IntRange(int minInclusive, int maxInclusive) {
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

   public interface IntRangeBase extends OptionInstance.SliderableValueSet<Integer> {
      int m_214123_();

      int m_214118_();

      default double m_213640_(Integer valueIn) {
         if (valueIn == this.m_214123_()) {
            return 0.0;
         } else {
            return valueIn == this.m_214118_()
               ? 1.0
               : Mth.m_144914_((double)valueIn.intValue() + 0.5, (double)this.m_214123_(), (double)this.m_214118_() + 1.0, 0.0, 1.0);
         }
      }

      default Integer m_213729_(double valueIn) {
         if (valueIn >= 1.0) {
            valueIn = 0.99999F;
         }

         return Mth.m_14107_(Mth.m_144914_(valueIn, 0.0, 1.0, (double)this.m_214123_(), (double)this.m_214118_() + 1.0));
      }

      default <R> OptionInstance.SliderableValueSet<R> m_231657_(final IntFunction<? extends R> intFuncIn, final ToIntFunction<? super R> toIntFuncIn) {
         return new SliderableValueSetInt<R>() {
            @Override
            public Optional<R> m_214064_(R valueIn) {
               return IntRangeBase.this.m_214064_(Integer.valueOf(toIntFuncIn.applyAsInt(valueIn))).map(intFuncIn::apply);
            }

            @Override
            public double m_213640_(R valueIn) {
               return IntRangeBase.this.m_213640_(toIntFuncIn.applyAsInt(valueIn));
            }

            @Override
            public R m_213729_(double valueIn) {
               return (R)intFuncIn.apply(IntRangeBase.this.m_213729_(valueIn));
            }

            @Override
            public Codec<R> m_213664_() {
               return IntRangeBase.this.m_213664_().xmap(intFuncIn::apply, toIntFuncIn::applyAsInt);
            }

            @Override
            public OptionInstance.IntRangeBase getIntRange() {
               return IntRangeBase.this;
            }
         };
      }
   }

   public static record LazyEnum<T>(Supplier<List<T>> f_231680_, Function<T, Optional<T>> f_231681_, Codec<T> f_231682_)
      implements OptionInstance.CycleableValueSet<T> {
      @Override
      public Optional<T> m_214064_(T valueIn) {
         return (Optional<T>)this.f_231681_.apply(valueIn);
      }

      @Override
      public CycleButton.ValueListSupplier<T> m_213889_() {
         return CycleButton.ValueListSupplier.m_232504_((Collection<T>)this.f_231680_.get());
      }

      @Override
      public Codec<T> m_213664_() {
         return this.f_231682_;
      }

      public Codec<T> codec() {
         return this.f_231682_;
      }
   }

   public static final class OptionInstanceSliderButton<N> extends AbstractOptionSliderButton implements IOptionControl {
      private final OptionInstance<N> f_231697_;
      private final OptionInstance.SliderableValueSet<N> f_231698_;
      private final OptionInstance.TooltipSupplier<N> f_256889_;
      private final Consumer<N> f_260531_;
      @Nullable
      private Long f_316943_;
      private final boolean f_316343_;
      private boolean supportAdjusting;
      private boolean adjusting;

      OptionInstanceSliderButton(
         Options optionsIn,
         int xIn,
         int yIn,
         int widthIn,
         int heightIn,
         OptionInstance<N> optionIn,
         OptionInstance.SliderableValueSet<N> valuesIn,
         OptionInstance.TooltipSupplier<N> tooltipIn,
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
            double denormValue = ((Number)this.f_231698_.m_213729_(this.f_93577_)).doubleValue();
            Component text = FloatOptions.getTextComponent(this.f_231697_, denormValue);
            if (text != null) {
               this.m_93666_(text);
            }
         } else {
            if (this.f_231697_ instanceof SliderPercentageOptionOF spo) {
               Component comp = spo.getOptionText();
               if (comp != null) {
                  this.m_93666_(comp);
               }
            } else {
               this.m_93666_((Component)this.f_231697_.f_231475_.apply(this.f_231698_.m_213729_(this.f_93577_)));
            }

            this.m_257544_(this.f_256889_.m_257630_(this.f_231698_.m_213729_(this.f_93577_)));
         }
      }

      protected void m_5697_() {
         if (!this.adjusting) {
            N valOld = this.f_231697_.m_231551_();
            N valNew = this.f_231698_.m_213729_(this.f_93577_);
            if (!valNew.equals(valOld)) {
               if (this.f_231697_ instanceof SliderPercentageOptionOF spo) {
                  spo.setOptionValue(((Number)valNew).doubleValue());
               }

               if (this.f_316343_) {
                  this.m_323527_();
               } else {
                  this.f_316943_ = Util.m_137550_() + 600L;
               }
            }
         }
      }

      public void m_323527_() {
         N n = this.f_231698_.m_213729_(this.f_93577_);
         if (!Objects.equals(n, this.f_231697_.m_231551_())) {
            this.f_231697_.m_231514_(n);
            this.f_93377_.m_92169_();
            this.f_260531_.accept(this.f_231697_.m_231551_());
         }
      }

      public void m_87963_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
         super.m_87963_(graphicsIn, mouseX, mouseY, partialTicks);
         if (this.f_316943_ != null && Util.m_137550_() >= this.f_316943_) {
            this.f_316943_ = null;
            this.m_323527_();
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

      @Override
      public OptionInstance getControlOption() {
         return this.f_231697_;
      }
   }

   interface SliderableOrCyclableValueSet<T> extends OptionInstance.CycleableValueSet<T>, OptionInstance.SliderableValueSet<T> {
      boolean m_214105_();

      @Override
      default Function<OptionInstance<T>, AbstractWidget> m_213823_(
         OptionInstance.TooltipSupplier<T> tooltipIn, Options optionsIn, int x, int y, int width, Consumer<T> onUpdateIn
      ) {
         return this.m_214105_()
            ? OptionInstance.CycleableValueSet.super.m_213823_(tooltipIn, optionsIn, x, y, width, onUpdateIn)
            : OptionInstance.SliderableValueSet.super.m_213823_(tooltipIn, optionsIn, x, y, width, onUpdateIn);
      }
   }

   public interface SliderableValueSet<T> extends OptionInstance.ValueSet<T> {
      double m_213640_(T var1);

      T m_213729_(double var1);

      default boolean m_320795_() {
         return true;
      }

      @Override
      default Function<OptionInstance<T>, AbstractWidget> m_213823_(
         OptionInstance.TooltipSupplier<T> tooltipIn, Options optionsIn, int x, int y, int width, Consumer<T> onUpdateIn
      ) {
         return optionIn -> new OptionInstance.OptionInstanceSliderButton<T>(
               optionsIn, x, y, width, 20, optionIn, this, tooltipIn, onUpdateIn, this.m_320795_()
            );
      }
   }

   @FunctionalInterface
   public interface TooltipSupplier<T> {
      @Nullable
      Tooltip m_257630_(T var1);
   }

   public static enum UnitDouble implements OptionInstance.SliderableValueSet<Double> {
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

      public <R> OptionInstance.SliderableValueSet<R> m_231750_(
         final DoubleFunction<? extends R> doubleFuncIn, final ToDoubleFunction<? super R> toDoubleFuncIn
      ) {
         return new OptionInstance.SliderableValueSet<R>() {
            @Override
            public Optional<R> m_214064_(R valueIn) {
               return UnitDouble.this.m_214064_(toDoubleFuncIn.applyAsDouble(valueIn)).map(doubleFuncIn::apply);
            }

            @Override
            public double m_213640_(R valueIn) {
               return UnitDouble.this.m_213640_(toDoubleFuncIn.applyAsDouble(valueIn));
            }

            @Override
            public R m_213729_(double valueIn) {
               return (R)doubleFuncIn.apply(UnitDouble.this.m_213729_(valueIn));
            }

            @Override
            public Codec<R> m_213664_() {
               return UnitDouble.this.m_213664_().xmap(doubleFuncIn::apply, toDoubleFuncIn::applyAsDouble);
            }
         };
      }

      @Override
      public Codec<Double> m_213664_() {
         return Codec.withAlternative(Codec.doubleRange(0.0, 1.0), Codec.BOOL, flagIn -> flagIn ? 1.0 : 0.0);
      }
   }

   public interface ValueSet<T> {
      Function<OptionInstance<T>, AbstractWidget> m_213823_(
         OptionInstance.TooltipSupplier<T> var1, Options var2, int var3, int var4, int var5, Consumer<T> var6
      );

      Optional<T> m_214064_(T var1);

      Codec<T> m_213664_();
   }
}
