import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.minecraft.src.C_123_;
import net.minecraft.src.C_1462_;
import net.minecraft.src.C_203228_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_1313_.C_265803_;

public record ArmorMaterial(Map<C_265803_, Integer> b, int c, C_203228_<C_123_> d, Supplier<C_1462_> e, List<ArmorMaterial.a> f, float g, float h) {
   public static final Codec<C_203228_<ArmorMaterial>> a = C_256712_.f_315942_.m_206110_();

   public int a(C_265803_ p_323068_1_) {
      return (Integer)this.b.getOrDefault(p_323068_1_, 0);
   }

   public Map<C_265803_, Integer> a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public C_203228_<C_123_> c() {
      return this.d;
   }

   public Supplier<C_1462_> d() {
      return this.e;
   }

   public List<ArmorMaterial.a> e() {
      return this.f;
   }

   public float f() {
      return this.g;
   }

   public float g() {
      return this.h;
   }

   public static final class a {
      private final ResourceLocation a;
      private final String b;
      private final boolean c;
      private final ResourceLocation d;
      private final ResourceLocation e;

      public a(ResourceLocation p_i322033_1_, String p_i322033_2_, boolean p_i322033_3_) {
         this.a = p_i322033_1_;
         this.b = p_i322033_2_;
         this.c = p_i322033_3_;
         this.d = this.b(true);
         this.e = this.b(false);
      }

      public a(ResourceLocation p_i320417_1_) {
         this(p_i320417_1_, "", false);
      }

      private ResourceLocation b(boolean p_320920_1_) {
         return this.a.a((UnaryOperator<String>)(p_319090_2_ -> "textures/models/armor/" + this.a.a() + "_layer_" + (p_320920_1_ ? 2 : 1) + this.b + ".png"));
      }

      public ResourceLocation a(boolean p_318738_1_) {
         return p_318738_1_ ? this.d : this.e;
      }

      public boolean a() {
         return this.c;
      }

      public ResourceLocation getAssetName() {
         return this.a;
      }

      public String getSuffix() {
         return this.b;
      }
   }
}
