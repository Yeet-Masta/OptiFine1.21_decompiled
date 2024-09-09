package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.optifine.Lang;
import net.optifine.gui.GuiScreenCapeOF;

public class C_336453_ extends C_336424_ {
   private static final C_4996_ f_336923_ = C_4996_.m_237115_("options.skinCustomisation.title");
   private C_3451_ btnCape;
   private int countButtons;

   public C_336453_(C_3583_ screenIn, C_3401_ optionsIn) {
      super(screenIn, optionsIn, f_336923_);
   }

   protected void m_338523_() {
      List list = new ArrayList();
      C_1144_[] var2 = C_1144_.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         C_1144_ playermodelpart = var2[var4];
         list.add(C_141591_.m_168916_(this.f_337734_.m_168416_(playermodelpart)).m_323445_(playermodelpart.m_36447_(), (p_338916_2_, p_338916_3_) -> {
            this.f_337734_.m_168418_(playermodelpart, p_338916_3_);
         }));
      }

      list.add(this.f_337734_.m_232107_().m_324463_(this.f_337734_));
      this.f_337426_.m_324569_(list);
      this.countButtons = list.size();
      this.btnCape = C_3451_.m_253074_(Lang.getComponent("of.options.skinCustomisation.ofCape"), (button) -> {
         this.l.m_91152_(new GuiScreenCapeOF(this));
      }).m_253046_(200, 20).m_253136_();
      this.btnCape.c(this.m / 2 - 100, this.f_337270_.m_269355_() + 8 + 24 * (this.countButtons / 2));
      this.c(this.btnCape);
   }

   protected void m_267719_() {
      super.m_267719_();
      if (this.btnCape != null) {
         this.e(this.btnCape);
         this.c(this.btnCape);
         this.btnCape.c(this.m / 2 - 100, this.f_337270_.m_269355_() + 8 + 24 * (this.countButtons / 2));
      }

   }
}
