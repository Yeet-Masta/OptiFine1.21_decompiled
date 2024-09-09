import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.src.C_1354_;
import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_197_;
import net.minecraft.src.C_203208_;
import net.minecraft.src.C_262716_;
import net.minecraft.src.C_313613_;
import net.minecraft.src.C_313866_;
import net.minecraft.src.C_174_.C_175_;
import net.minecraft.src.C_197_.C_212981_;
import net.minecraft.src.C_262716_.C_262714_;
import net.optifine.reflect.Reflector;

public enum DyeColor implements C_197_ {
   a(0, "white", 16383998, MapColor.i, 15790320, 16777215),
   b(1, "orange", 16351261, MapColor.p, 15435844, 16738335),
   c(2, "magenta", 13061821, MapColor.q, 12801229, 16711935),
   d(3, "light_blue", 3847130, MapColor.r, 6719955, 10141901),
   e(4, "yellow", 16701501, MapColor.s, 14602026, 16776960),
   f(5, "lime", 8439583, MapColor.t, 4312372, 12582656),
   g(6, "pink", 15961002, MapColor.u, 14188952, 16738740),
   h(7, "gray", 4673362, MapColor.v, 4408131, 8421504),
   i(8, "light_gray", 10329495, MapColor.w, 11250603, 13882323),
   j(9, "cyan", 1481884, MapColor.x, 2651799, 65535),
   k(10, "purple", 8991416, MapColor.y, 8073150, 10494192),
   l(11, "blue", 3949738, MapColor.z, 2437522, 255),
   m(12, "brown", 8606770, MapColor.A, 5320730, 9127187),
   n(13, "green", 6192150, MapColor.B, 3887386, 65280),
   o(14, "red", 11546150, MapColor.C, 11743532, 16711680),
   p(15, "black", 1908001, MapColor.D, 1973019, 0);

   private static final IntFunction<DyeColor> s = C_262716_.m_262839_(DyeColor::a, values(), C_262714_.ZERO);
   private static final Int2ObjectOpenHashMap<DyeColor> t = new Int2ObjectOpenHashMap(
      (Map)Arrays.stream(values()).collect(Collectors.toMap(dyeColorIn -> dyeColorIn.y, p_41055_0_ -> p_41055_0_))
   );
   public static final C_212981_<DyeColor> q = C_197_.m_216439_(DyeColor::values);
   public static final C_313866_<ByteBuf, DyeColor> r = C_313613_.m_321301_(s, DyeColor::a);
   private final int u;
   private final String v;
   private final MapColor w;
   private int x;
   private final int y;
   private final C_203208_<C_1381_> tag;
   private final int z;

   private DyeColor(
      final int idIn, final String translationKeyIn, final int colorValueIn, final MapColor mapColorIn, final int fireworkColorIn, final int textColorIn
   ) {
      this.u = idIn;
      this.v = translationKeyIn;
      this.w = mapColorIn;
      this.z = textColorIn;
      this.x = C_175_.m_321570_(colorValueIn);
      this.y = fireworkColorIn;
      this.tag = (C_203208_<C_1381_>)Reflector.ForgeItemTags_create.call(new ResourceLocation("forge", "dyes/" + translationKeyIn));
   }

   public int a() {
      return this.u;
   }

   public String b() {
      return this.v;
   }

   public int d() {
      return this.x;
   }

   public MapColor e() {
      return this.w;
   }

   public int f() {
      return this.y;
   }

   public int g() {
      return this.z;
   }

   public static DyeColor a(int colorId) {
      return (DyeColor)s.apply(colorId);
   }

   @Nullable
   public static DyeColor a(String translationKeyIn, @Nullable DyeColor fallback) {
      DyeColor dyecolor = (DyeColor)q.m_216455_(translationKeyIn);
      return dyecolor != null ? dyecolor : fallback;
   }

   @Nullable
   public static DyeColor b(int fireworkColorIn) {
      return (DyeColor)t.get(fireworkColorIn);
   }

   public String toString() {
      return this.v;
   }

   public String m_7912_() {
      return this.v;
   }

   public void setTextureDiffuseColor(int color) {
      this.x = color;
   }

   public C_203208_<C_1381_> getTag() {
      return this.tag;
   }

   @Nullable
   public static DyeColor getColor(C_1391_ stack) {
      if (stack.m_41720_() instanceof C_1354_) {
         return ((C_1354_)stack.m_41720_()).c();
      } else {
         for (int x = 0; x < p.a(); x++) {
            DyeColor color = a(x);
            if (stack.m_204117_(color.getTag())) {
               return color;
            }
         }

         return null;
      }
   }
}
