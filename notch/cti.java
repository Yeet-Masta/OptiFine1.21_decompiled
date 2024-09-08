package net.minecraft.src;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.src.C_174_.C_175_;
import net.minecraft.src.C_197_.C_212981_;
import net.minecraft.src.C_262716_.C_262714_;
import net.optifine.reflect.Reflector;

public enum C_1353_ implements C_197_ {
   WHITE(0, "white", 16383998, C_283734_.f_283811_, 15790320, 16777215),
   ORANGE(1, "orange", 16351261, C_283734_.f_283750_, 15435844, 16738335),
   MAGENTA(2, "magenta", 13061821, C_283734_.f_283931_, 12801229, 16711935),
   LIGHT_BLUE(3, "light_blue", 3847130, C_283734_.f_283869_, 6719955, 10141901),
   YELLOW(4, "yellow", 16701501, C_283734_.f_283832_, 14602026, 16776960),
   LIME(5, "lime", 8439583, C_283734_.f_283916_, 4312372, 12582656),
   PINK(6, "pink", 15961002, C_283734_.f_283765_, 14188952, 16738740),
   GRAY(7, "gray", 4673362, C_283734_.f_283818_, 4408131, 8421504),
   LIGHT_GRAY(8, "light_gray", 10329495, C_283734_.f_283779_, 11250603, 13882323),
   CYAN(9, "cyan", 1481884, C_283734_.f_283772_, 2651799, 65535),
   PURPLE(10, "purple", 8991416, C_283734_.f_283889_, 8073150, 10494192),
   BLUE(11, "blue", 3949738, C_283734_.f_283743_, 2437522, 255),
   BROWN(12, "brown", 8606770, C_283734_.f_283748_, 5320730, 9127187),
   GREEN(13, "green", 6192150, C_283734_.f_283784_, 3887386, 65280),
   RED(14, "red", 11546150, C_283734_.f_283913_, 11743532, 16711680),
   BLACK(15, "black", 1908001, C_283734_.f_283927_, 1973019, 0);

   private static final IntFunction<C_1353_> f_41032_ = C_262716_.m_262839_(C_1353_::m_41060_, values(), C_262714_.ZERO);
   private static final Int2ObjectOpenHashMap<C_1353_> f_41033_ = new Int2ObjectOpenHashMap(
      (Map)Arrays.stream(values()).collect(Collectors.toMap(dyeColorIn -> dyeColorIn.f_41040_, p_41055_0_ -> p_41055_0_))
   );
   public static final C_212981_<C_1353_> f_262211_ = C_197_.m_216439_(C_1353_::values);
   public static final C_313866_<ByteBuf, C_1353_> f_313960_ = C_313613_.m_321301_(f_41032_, C_1353_::m_41060_);
   private final int f_41034_;
   private final String f_41035_;
   private final C_283734_ f_283766_;
   private int f_337674_;
   private final int f_41040_;
   private final C_203208_<C_1381_> tag;
   private final int f_41041_;

   private C_1353_(
      final int idIn, final String translationKeyIn, final int colorValueIn, final C_283734_ mapColorIn, final int fireworkColorIn, final int textColorIn
   ) {
      this.f_41034_ = idIn;
      this.f_41035_ = translationKeyIn;
      this.f_283766_ = mapColorIn;
      this.f_41041_ = textColorIn;
      this.f_337674_ = C_175_.m_321570_(colorValueIn);
      this.f_41040_ = fireworkColorIn;
      this.tag = (C_203208_<C_1381_>)Reflector.ForgeItemTags_create.call(new C_5265_("forge", "dyes/" + translationKeyIn));
   }

   public int m_41060_() {
      return this.f_41034_;
   }

   public String m_41065_() {
      return this.f_41035_;
   }

   public int m_340318_() {
      return this.f_337674_;
   }

   public C_283734_ m_284406_() {
      return this.f_283766_;
   }

   public int m_41070_() {
      return this.f_41040_;
   }

   public int m_41071_() {
      return this.f_41041_;
   }

   public static C_1353_ m_41053_(int colorId) {
      return (C_1353_)f_41032_.apply(colorId);
   }

   @Nullable
   public static C_1353_ m_41057_(String translationKeyIn, @Nullable C_1353_ fallback) {
      C_1353_ dyecolor = (C_1353_)f_262211_.m_216455_(translationKeyIn);
      return dyecolor != null ? dyecolor : fallback;
   }

   @Nullable
   public static C_1353_ m_41061_(int fireworkColorIn) {
      return (C_1353_)f_41033_.get(fireworkColorIn);
   }

   public String toString() {
      return this.f_41035_;
   }

   public String m_7912_() {
      return this.f_41035_;
   }

   public void setTextureDiffuseColor(int color) {
      this.f_337674_ = color;
   }

   public C_203208_<C_1381_> getTag() {
      return this.tag;
   }

   @Nullable
   public static C_1353_ getColor(C_1391_ stack) {
      if (stack.m_41720_() instanceof C_1354_) {
         return ((C_1354_)stack.m_41720_()).m_41089_();
      } else {
         for (int x = 0; x < BLACK.m_41060_(); x++) {
            C_1353_ color = m_41053_(x);
            if (stack.m_204117_(color.getTag())) {
               return color;
            }
         }

         return null;
      }
   }
}
