import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3812_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4432_;
import net.minecraft.src.C_988_;

public class CreeperPowerLayer extends C_4432_<C_988_, C_3812_<C_988_>> {
   private static final ResourceLocation a = ResourceLocation.b("textures/entity/creeper/creeper_armor.png");
   public C_3812_<C_988_> b;
   public ResourceLocation customTextureLocation;

   public CreeperPowerLayer(C_4382_<C_988_, C_3812_<C_988_>> p_i174470_1_, C_141653_ p_i174470_2_) {
      super(p_i174470_1_);
      this.b = new C_3812_(p_i174470_2_.a(C_141656_.f_171129_));
   }

   protected float m_7631_(float ticksIn) {
      return ticksIn * 0.01F;
   }

   protected ResourceLocation a() {
      return this.customTextureLocation != null ? this.customTextureLocation : a;
   }

   protected C_3819_<C_988_> m_7193_() {
      return this.b;
   }
}
