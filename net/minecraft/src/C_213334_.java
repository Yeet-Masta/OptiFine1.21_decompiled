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

public class C_213334_ {
   private static final Logger f_231472_ = LogUtils.getLogger();
   public static final C_213340_ f_231471_;
   public static final C_213336_ f_260471_;
   private final C_213348_ f_231474_;
   final Function f_231475_;
   private final C_213352_ f_231476_;
   private final Codec f_231477_;
   private final Object f_231478_;
   private final Consumer f_231479_;
   final C_4996_ f_231480_;
   Object f_231481_;
   private String resourceKey;
   public static final Map OPTIONS_BY_KEY;

   public static C_213334_ m_231528_(String keyIn, boolean initValueIn, Consumer onUpdateIn) {
      return m_257874_(keyIn, m_231498_(), initValueIn, onUpdateIn);
   }

   public static C_213334_ m_231525_(String keyIn, boolean initValueIn) {
      return m_257874_(keyIn, m_231498_(), initValueIn, (onUpdateIn) -> {
      });
   }

   public static C_213334_ m_257536_(String keyIn, C_213348_ tooltipIn, boolean initValueIn) {
      return m_257874_(keyIn, tooltipIn, initValueIn, (onUpdateIn) -> {
      });
   }

   public static C_213334_ m_257874_(String keyIn, C_213348_ tooltipIn, boolean initValueIn, Consumer onUpdateIn) {
      return m_260965_(keyIn, tooltipIn, f_260471_, initValueIn, onUpdateIn);
   }

   public static C_213334_ m_260965_(String keyIn, C_213348_ tooltipIn, C_213336_ toStringIn, boolean initValueIn, Consumer onUpdateIn) {
      return new C_213334_(keyIn, tooltipIn, toStringIn, f_231471_, initValueIn, onUpdateIn);
   }

   public C_213334_(String keyIn, C_213348_ tooltipIn, C_213336_ toStringIn, C_213352_ valuesIn, Object initValueIn, Consumer onUpdateIn) {
      this(keyIn, tooltipIn, toStringIn, valuesIn, valuesIn.m_213664_(), initValueIn, onUpdateIn);
   }

   public C_213334_(String keyIn, C_213348_ tooltipIn, C_213336_ toStringIn, C_213352_ valuesIn, Codec codecIn, Object initValueIn, Consumer onUpdateIn) {
      this.f_231480_ = C_4996_.m_237115_(keyIn);
      this.f_231474_ = tooltipIn;
      this.f_231475_ = (objIn) -> {
         return toStringIn.m_231580_(this.f_231480_, objIn);
      };
      this.f_231476_ = valuesIn;
      this.f_231477_ = codecIn;
      this.f_231478_ = initValueIn;
      this.f_231479_ = onUpdateIn;
      this.f_231481_ = this.f_231478_;
      this.resourceKey = keyIn;
      OPTIONS_BY_KEY.put(this.resourceKey, this);
   }

   public static C_213348_ m_231498_() {
      return (objIn) -> {
         return null;
      };
   }

   public static C_213348_ m_231535_(C_4996_ componentIn) {
      return (objIn) -> {
         return C_256714_.m_257550_(componentIn);
      };
   }

   public static C_213336_ m_231546_() {
      return (compIn, objIn) -> {
         return objIn.m_216301_();
      };
   }

   public C_3449_ m_324463_(C_3401_ optionsIn) {
      return this.m_231507_(optionsIn, 0, 0, 150);
   }

   public C_3449_ m_231507_(C_3401_ optionsIn, int x, int y, int width) {
      return this.m_261194_(optionsIn, x, y, width, (objIn) -> {
      });
   }

   public C_3449_ m_261194_(C_3401_ optionsIn, int x, int y, int width, Consumer onUpdateIn) {
      return (C_3449_)this.f_231476_.m_213823_(this.f_231474_, optionsIn, x, y, width, onUpdateIn).apply(this);
   }

   public Object m_231551_() {
      if (this instanceof SliderPercentageOptionOF spo) {
         if (this.f_231481_ instanceof Integer) {
            return (int)spo.getOptionValue();
         }

         if (this.f_231481_ instanceof Double) {
            return spo.getOptionValue();
         }
      }

      return this.f_231481_;
   }

   public Codec m_231554_() {
      return this.f_231477_;
   }

   public String toString() {
      return this.f_231480_.getString();
   }

   public void m_231514_(Object valueIn) {
      Object t = this.f_231476_.m_214064_(valueIn).orElseGet(() -> {
         Logger var10000 = f_231472_;
         String var10001 = String.valueOf(valueIn);
         var10000.error("Illegal option value " + var10001 + " for " + String.valueOf(this.f_231480_));
         return this.f_231478_;
      });
      if (!C_3391_.m_91087_().m_91396_()) {
         this.f_231481_ = t;
      } else if (!Objects.equals(this.f_231481_, t)) {
         this.f_231481_ = t;
         this.f_231479_.accept(this.f_231481_);
      }

   }

   public C_213352_ m_231555_() {
      return this.f_231476_;
   }

   public String getResourceKey() {
      return this.resourceKey;
   }

   public C_4996_ getCaption() {
      return this.f_231480_;
   }

   public Object getMinValue() {
      C_213342_ intRange = this.getIntRangeBase();
      if (intRange != null) {
         Integer val = intRange.m_214123_();
         return val;
      } else {
         throw new IllegalArgumentException("Min value not supported: " + this.getResourceKey());
      }
   }

   public Object getMaxValue() {
      C_213342_ intRange = this.getIntRangeBase();
      if (intRange != null) {
         Integer val = intRange.m_214118_();
         return val;
      } else {
         throw new IllegalArgumentException("Max value not supported: " + this.getResourceKey());
      }
   }

   public C_213342_ getIntRangeBase() {
      C_213342_ intRange;
      if (this.f_231476_ instanceof C_213342_) {
         intRange = (C_213342_)this.f_231476_;
         return intRange;
      } else if (this.f_231476_ instanceof SliderableValueSetInt) {
         intRange = ((SliderableValueSetInt)this.f_231476_).getIntRange();
         return intRange;
      } else {
         return null;
      }
   }

   public boolean isProgressOption() {
      return this.f_231476_ instanceof C_213347_;
   }

   static {
      f_231471_ = new C_213340_(ImmutableList.of(Boolean.TRUE, Boolean.FALSE), Codec.BOOL);
      f_260471_ = (compIn, boolIn) -> {
         return boolIn ? C_4995_.f_130653_ : C_4995_.f_130654_;
      };
      OPTIONS_BY_KEY = new LinkedHashMap();
   }

   @FunctionalInterface
   public interface C_213348_ {
      @Nullable
      C_256714_ m_257630_(Object var1);
   }

   public interface C_213336_ {
      C_4996_ m_231580_(C_4996_ var1, Object var2);
   }

   public static record C_213340_(List f_231625_, Codec f_231626_) implements C_213338_ {
      public C_213340_(List values, Codec codec) {
         this.f_231625_ = values;
         this.f_231626_ = codec;
      }

      public Optional m_214064_(Object valueIn) {
         return this.f_231625_.contains(valueIn) ? Optional.of(valueIn) : Optional.empty();
      }

      public C_141591_.C_141595_ m_213889_() {
         return C_141591_.C_141595_.m_232504_(this.f_231625_);
      }

      public Codec m_213664_() {
         return this.f_231626_;
      }

      public List f_231625_() {
         return this.f_231625_;
      }

      public Codec codec() {
         return this.f_231626_;
      }
   }

   public interface C_213352_ {
      Function m_213823_(C_213348_ var1, C_3401_ var2, int var3, int var4, int var5, Consumer var6);

      Optional m_214064_(Object var1);

      Codec m_213664_();
   }

   public interface C_213342_ extends C_213347_ {
      int m_214123_();

      int m_214118_();

      default double m_213640_(Integer valueIn) {
         if (valueIn == this.m_214123_()) {
            return 0.0;
         } else {
            return valueIn == this.m_214118_() ? 1.0 : C_188_.m_144914_((double)valueIn + 0.5, (double)this.m_214123_(), (double)this.m_214118_() + 1.0, 0.0, 1.0);
         }
      }

      default Integer m_213729_(double valueIn) {
         if (valueIn >= 1.0) {
            valueIn = 0.9999899864196777;
         }

         return C_188_.m_14107_(C_188_.m_144914_(valueIn, 0.0, 1.0, (double)this.m_214123_(), (double)this.m_214118_() + 1.0));
      }

      default C_213347_ m_231657_(final IntFunction intFuncIn, final ToIntFunction toIntFuncIn) {
         return new SliderableValueSetInt() {
            public Optional m_214064_(Object valueIn) {
               Optional var10000 = C_213342_.this.m_214064_(toIntFuncIn.applyAsInt(valueIn));
               IntFunction var10001 = intFuncIn;
               Objects.requireNonNull(var10001);
               return var10000.map(var10001::apply);
            }

            public double m_213640_(Object valueIn) {
               return C_213342_.this.m_213640_(toIntFuncIn.applyAsInt(valueIn));
            }

            public Object m_213729_(double valueIn) {
               return intFuncIn.apply(C_213342_.this.m_213729_(valueIn));
            }

            public Codec m_213664_() {
               Codec var10000 = C_213342_.this.m_213664_();
               IntFunction var10001 = intFuncIn;
               Objects.requireNonNull(var10001);
               Function var1 = var10001::apply;
               ToIntFunction var10002 = toIntFuncIn;
               Objects.requireNonNull(var10002);
               return var10000.xmap(var1, var10002::applyAsInt);
            }

            public C_213342_ getIntRange() {
               return C_213342_.this;
            }
         };
      }
   }

   public interface C_213347_ extends C_213352_ {
      double m_213640_(Object var1);

      Object m_213729_(double var1);

      default boolean m_320795_() {
         return true;
      }

      default Function m_213823_(C_213348_ tooltipIn, C_3401_ optionsIn, int x, int y, int width, Consumer onUpdateIn) {
         return (optionIn) -> {
            return new C_213345_(optionsIn, x, y, width, 20, optionIn, this, tooltipIn, onUpdateIn, this.m_320795_());
         };
      }
   }

   public static enum C_213350_ implements C_213347_ {
      INSTANCE;

      public Optional m_214064_(Double valueIn) {
         return valueIn >= 0.0 && valueIn <= 1.0 ? Optional.of(valueIn) : Optional.empty();
      }

      public double m_213640_(Double valueIn) {
         return valueIn;
      }

      public Double m_213729_(double valueIn) {
         return valueIn;
      }

      public C_213347_ m_231750_(final DoubleFunction doubleFuncIn, final ToDoubleFunction toDoubleFuncIn) {
         return new C_213347_() {
            public Optional m_214064_(Object valueIn) {
               Optional var10000 = C_213350_.this.m_214064_(toDoubleFuncIn.applyAsDouble(valueIn));
               DoubleFunction var10001 = doubleFuncIn;
               Objects.requireNonNull(var10001);
               return var10000.map(var10001::apply);
            }

            public double m_213640_(Object valueIn) {
               return C_213350_.this.m_213640_(toDoubleFuncIn.applyAsDouble(valueIn));
            }

            public Object m_213729_(double valueIn) {
               return doubleFuncIn.apply(C_213350_.this.m_213729_(valueIn));
            }

            public Codec m_213664_() {
               Codec var10000 = C_213350_.this.m_213664_();
               DoubleFunction var10001 = doubleFuncIn;
               Objects.requireNonNull(var10001);
               Function var1 = var10001::apply;
               ToDoubleFunction var10002 = toDoubleFuncIn;
               Objects.requireNonNull(var10002);
               return var10000.xmap(var1, var10002::applyAsDouble);
            }
         };
      }

      public Codec m_213664_() {
         return Codec.withAlternative(Codec.doubleRange(0.0, 1.0), Codec.BOOL, (flagIn) -> {
            return flagIn ? 1.0 : 0.0;
         });
      }

      // $FF: synthetic method
      private static C_213350_[] $values() {
         return new C_213350_[]{INSTANCE};
      }
   }

   interface C_213346_ extends C_213338_, C_213347_ {
      boolean m_214105_();

      default Function m_213823_(C_213348_ tooltipIn, C_3401_ optionsIn, int x, int y, int width, Consumer onUpdateIn) {
         return this.m_214105_() ? C_213334_.C_213338_.super.m_213823_(tooltipIn, optionsIn, x, y, width, onUpdateIn) : C_213334_.C_213347_.super.m_213823_(tooltipIn, optionsIn, x, y, width, onUpdateIn);
      }
   }

   public static final class C_213345_ extends C_3442_ implements IOptionControl {
      private final C_213334_ f_231697_;
      private final C_213347_ f_231698_;
      private final C_213348_ f_256889_;
      private final Consumer f_260531_;
      @Nullable
      private Long f_316943_;
      private final boolean f_316343_;
      private boolean supportAdjusting;
      private boolean adjusting;

      C_213345_(C_3401_ optionsIn, int xIn, int yIn, int widthIn, int heightIn, C_213334_ optionIn, C_213347_ valuesIn, C_213348_ tooltipIn, Consumer onUpdateIn, boolean applyImmediatelyIn) {
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
            if (this.f_231697_ instanceof SliderPercentageOptionOF) {
               SliderPercentageOptionOF spo = (SliderPercentageOptionOF)this.f_231697_;
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
            Object valOld = this.f_231697_.m_231551_();
            Object valNew = this.f_231698_.m_213729_(this.c);
            if (!valNew.equals(valOld)) {
               if (this.f_231697_ instanceof SliderPercentageOptionOF) {
                  SliderPercentageOptionOF spo = (SliderPercentageOptionOF)this.f_231697_;
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
         Object n = this.f_231698_.m_213729_(this.c);
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

      // $FF: renamed from: a (double, double) void
      public void method_9(double mouseX, double mouseY) {
         if (this.supportAdjusting) {
            this.adjusting = true;
         }

         super.a(mouseX, mouseY);
      }

      // $FF: renamed from: b (double, double, double, double) void
      protected void method_10(double mouseX, double mouseY, double mouseDX, double mouseDY) {
         if (this.supportAdjusting) {
            this.adjusting = true;
         }

         super.b(mouseX, mouseY, mouseDX, mouseDY);
      }

      // $FF: renamed from: a_ (double, double) void
      public void method_11(double mouseX, double mouseY) {
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

   public static record C_213344_(Supplier f_231680_, Function f_231681_, Codec f_231682_) implements C_213338_ {
      public C_213344_(Supplier values, Function validateValue, Codec codec) {
         this.f_231680_ = values;
         this.f_231681_ = validateValue;
         this.f_231682_ = codec;
      }

      public Optional m_214064_(Object valueIn) {
         return (Optional)this.f_231681_.apply(valueIn);
      }

      public C_141591_.C_141595_ m_213889_() {
         return C_141591_.C_141595_.m_232504_((Collection)this.f_231680_.get());
      }

      public Codec m_213664_() {
         return this.f_231682_;
      }

      public Supplier f_231680_() {
         return this.f_231680_;
      }

      public Function f_231681_() {
         return this.f_231681_;
      }

      public Codec codec() {
         return this.f_231682_;
      }
   }

   public static record C_213341_(int f_231639_, int f_231640_, boolean f_316444_) implements C_213342_ {
      public C_213341_(int minInclusive, int maxInclusive) {
         this(minInclusive, maxInclusive, true);
      }

      public C_213341_(int minInclusive, int maxInclusive, boolean applyValueImmediately) {
         this.f_231639_ = minInclusive;
         this.f_231640_ = maxInclusive;
         this.f_316444_ = applyValueImmediately;
      }

      public Optional m_214064_(Integer valueIn) {
         return valueIn.compareTo(this.m_214123_()) >= 0 && valueIn.compareTo(this.m_214118_()) <= 0 ? Optional.of(valueIn) : Optional.empty();
      }

      public Codec m_213664_() {
         return Codec.intRange(this.f_231639_, this.f_231640_ + 1);
      }

      public int m_214123_() {
         return this.f_231639_;
      }

      public int m_214118_() {
         return this.f_231640_;
      }

      public boolean m_320795_() {
         return this.f_316444_;
      }
   }

   interface C_213338_ extends C_213352_ {
      C_141591_.C_141595_ m_213889_();

      default C_213339_ m_213569_() {
         return C_213334_::m_231514_;
      }

      default Function m_213823_(C_213348_ tooltipIn, C_3401_ optionsIn, int x, int y, int width, Consumer onUpdateIn) {
         return (optionIn) -> {
            return C_141591_.m_168894_(optionIn.f_231475_).m_232500_(this.m_213889_()).m_232498_(tooltipIn).m_168948_(optionIn.f_231481_).m_168936_(x, y, width, 20, optionIn.f_231480_, (btnIn, valIn) -> {
               this.m_213569_().m_231622_(optionIn, valIn);
               optionsIn.m_92169_();
               onUpdateIn.accept(valIn);
            });
         };
      }

      public interface C_213339_ {
         void m_231622_(C_213334_ var1, Object var2);
      }
   }

   public static record C_213337_(int f_231583_, IntSupplier f_231584_, int f_276069_) implements C_213342_, C_213346_ {
      public C_213337_(int minInclusive, IntSupplier maxSupplier, int encodableMaxInclusive) {
         this.f_231583_ = minInclusive;
         this.f_231584_ = maxSupplier;
         this.f_276069_ = encodableMaxInclusive;
      }

      public Optional m_214064_(Integer valueIn) {
         return Optional.of(C_188_.m_14045_(valueIn, this.m_214123_(), this.m_214118_()));
      }

      public int m_214118_() {
         return this.f_231584_.getAsInt();
      }

      public Codec m_213664_() {
         return Codec.INT.validate((valIn) -> {
            int i = this.f_276069_ + 1;
            return valIn.compareTo(this.f_231583_) >= 0 && valIn.compareTo(i) <= 0 ? DataResult.success(valIn) : DataResult.error(() -> {
               return "Value " + valIn + " outside of range [" + this.f_231583_ + ":" + i + "]";
            }, valIn);
         });
      }

      public boolean m_214105_() {
         return true;
      }

      public C_141591_.C_141595_ m_213889_() {
         return C_141591_.C_141595_.m_232504_(IntStream.range(this.f_231583_, this.m_214118_() + 1).boxed().toList());
      }

      public int m_214123_() {
         return this.f_231583_;
      }

      public IntSupplier f_231584_() {
         return this.f_231584_;
      }

      public int f_276069_() {
         return this.f_276069_;
      }
   }

   public static record C_213335_(List f_231557_, List f_231558_, BooleanSupplier f_231559_, C_213338_.C_213339_ f_231560_, Codec f_231561_) implements C_213338_ {
      public C_213335_(List values, List altValues, BooleanSupplier altCondition, C_213338_.C_213339_ valueSetter, Codec codec) {
         this.f_231557_ = values;
         this.f_231558_ = altValues;
         this.f_231559_ = altCondition;
         this.f_231560_ = valueSetter;
         this.f_231561_ = codec;
      }

      public C_141591_.C_141595_ m_213889_() {
         return C_141591_.C_141595_.m_168970_(this.f_231559_, this.f_231557_, this.f_231558_);
      }

      public Optional m_214064_(Object valueIn) {
         return (this.f_231559_.getAsBoolean() ? this.f_231558_ : this.f_231557_).contains(valueIn) ? Optional.of(valueIn) : Optional.empty();
      }

      public C_213338_.C_213339_ m_213569_() {
         return this.f_231560_;
      }

      public Codec m_213664_() {
         return this.f_231561_;
      }

      public List f_231557_() {
         return this.f_231557_;
      }

      public List f_231558_() {
         return this.f_231558_;
      }

      public BooleanSupplier f_231559_() {
         return this.f_231559_;
      }

      public C_213338_.C_213339_ valueSetter() {
         return this.f_231560_;
      }

      public Codec codec() {
         return this.f_231561_;
      }
   }
}
