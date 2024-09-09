import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.src.C_268411_;
import net.minecraft.src.C_336424_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_468_;
import net.minecraft.src.C_4996_;

public class OnlineOptionsScreen extends C_336424_ {
   private static final C_4996_ a = C_4996_.m_237115_("options.online.title");
   @Nullable
   private OptionInstance<Unit> u;

   public OnlineOptionsScreen(C_3583_ p_i338645_1_, Options p_i338645_2_) {
      super(p_i338645_1_, p_i338645_2_, a);
   }

   protected void m_7856_() {
      super.m_7856_();
      if (this.u != null) {
         C_3449_ abstractwidget = this.f_337426_.b(this.u);
         if (abstractwidget != null) {
            abstractwidget.f_93623_ = false;
         }
      }
   }

   private OptionInstance<?>[] a(Options p_339916_1_, C_3391_ p_339916_2_) {
      List<OptionInstance<?>> list = new ArrayList();
      list.add(p_339916_1_.T());
      list.add(p_339916_1_.U());
      OptionInstance<Unit> optioninstance = (OptionInstance<Unit>)C_268411_.m_269382_(
         p_339916_2_.r,
         p_338840_0_ -> {
            C_468_ difficulty = p_338840_0_.m_46791_();
            return new OptionInstance<>(
               "options.difficulty.online",
               OptionInstance.a(),
               (p_339077_1_, p_339077_2_) -> difficulty.m_19033_(),
               new OptionInstance.e<>(List.of(Unit.INSTANCE), Codec.EMPTY.codec()),
               Unit.INSTANCE,
               p_338410_0_ -> {
               }
            );
         }
      );
      if (optioninstance != null) {
         this.u = optioninstance;
         list.add(optioninstance);
      }

      return (OptionInstance<?>[])list.toArray(new OptionInstance[0]);
   }

   protected void m_338523_() {
      this.f_337426_.a(this.a(this.c, this.f_96541_));
   }
}
