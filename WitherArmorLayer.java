import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_3884_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4432_;
import net.minecraft.src.C_964_;

public class WitherArmorLayer extends C_4432_<C_964_, C_3884_<C_964_>> {
   private static final ResourceLocation a = ResourceLocation.b("textures/entity/wither/wither_armor.png");
   public C_3884_<C_964_> b;
   public ResourceLocation customTextureLocation;

   public WitherArmorLayer(C_4382_<C_964_, C_3884_<C_964_>> p_i174553_1_, C_141653_ p_i174553_2_) {
      super(p_i174553_1_);
      this.b = new C_3884_(p_i174553_2_.a(C_141656_.f_171215_));
   }

   protected float m_7631_(float ticksIn) {
      return Mth.b(ticksIn * 0.02F) * 3.0F;
   }

   protected ResourceLocation a() {
      return this.customTextureLocation != null ? this.customTextureLocation : a;
   }

   protected C_3819_<C_964_> m_7193_() {
      return this.b;
   }
}
