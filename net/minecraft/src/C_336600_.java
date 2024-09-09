package net.minecraft.src;

import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class C_336600_ extends C_336424_ {
   private static final C_4996_ f_337665_ = C_4996_.m_237115_("options.online.title");
   @Nullable
   private C_213334_ f_337239_;

   public C_336600_(C_3583_ p_i338645_1_, C_3401_ p_i338645_2_) {
      super(p_i338645_1_, p_i338645_2_, f_337665_);
   }

   protected void m_7856_() {
      super.m_7856_();
      if (this.f_337239_ != null) {
         C_3449_ abstractwidget = this.f_337426_.m_232535_(this.f_337239_);
         if (abstractwidget != null) {
            abstractwidget.f_93623_ = false;
         }
      }

   }

   private C_213334_[] m_339916_(C_3401_ p_339916_1_, C_3391_ p_339916_2_) {
      List list = new ArrayList();
      list.add(p_339916_1_.m_231822_());
      list.add(p_339916_1_.m_231823_());
      C_213334_ optioninstance = (C_213334_)C_268411_.m_269382_(p_339916_2_.f_91073_, (p_338840_0_) -> {
         C_468_ difficulty = p_338840_0_.al();
         return new C_213334_("options.difficulty.online", C_213334_.m_231498_(), (p_339077_1_, p_339077_2_) -> {
            return difficulty.m_19033_();
         }, new C_213334_.C_213340_(List.of(Unit.INSTANCE), Codec.EMPTY.codec()), Unit.INSTANCE, (p_338410_0_) -> {
         });
      });
      if (optioninstance != null) {
         this.f_337239_ = optioninstance;
         list.add(optioninstance);
      }

      return (C_213334_[])list.toArray(new C_213334_[0]);
   }

   protected void m_338523_() {
      this.f_337426_.m_232533_(this.m_339916_(this.f_337734_, this.l));
   }
}
