import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.src.C_141050_;
import net.minecraft.src.C_141051_;
import net.minecraft.src.C_212974_;

public class ConstantFloat extends C_141050_ {
   public static final ConstantFloat a = new ConstantFloat(0.0F);
   public static final MapCodec<ConstantFloat> b = Codec.FLOAT.fieldOf("value").xmap(ConstantFloat::a, ConstantFloat::d);
   private final float d;

   public static ConstantFloat a(float p_146458_0_) {
      return p_146458_0_ == 0.0F ? a : new ConstantFloat(p_146458_0_);
   }

   private ConstantFloat(float p_i146455_1_) {
      this.d = p_i146455_1_;
   }

   public float d() {
      return this.d;
   }

   public float m_214084_(C_212974_ p_214084_1_) {
      return this.d;
   }

   public float m_142735_() {
      return this.d;
   }

   public float m_142734_() {
      return this.d;
   }

   public C_141051_<?> m_141961_() {
      return C_141051_.f_146519_;
   }

   public String toString() {
      return Float.toString(this.d);
   }
}
