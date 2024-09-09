import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.C_1144_;
import net.minecraft.src.C_336424_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3451_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4996_;
import net.optifine.Lang;
import net.optifine.gui.GuiScreenCapeOF;

public class SkinCustomizationScreen extends C_336424_ {
   private static final C_4996_ a = C_4996_.m_237115_("options.skinCustomisation.title");
   private C_3451_ btnCape;
   private int countButtons;

   public SkinCustomizationScreen(C_3583_ screenIn, Options optionsIn) {
      super(screenIn, optionsIn, a);
   }

   protected void m_338523_() {
      List<C_3449_> list = new ArrayList();

      for (C_1144_ playermodelpart : C_1144_.values()) {
         list.add(CycleButton.b(this.c.a(playermodelpart)).a(playermodelpart.m_36447_(), (p_338916_2_, p_338916_3_) -> this.c.a(playermodelpart, p_338916_3_)));
      }

      list.add(this.c.v().a(this.c));
      this.f_337426_.m_324569_(list);
      this.countButtons = list.size();
      this.btnCape = C_3451_.m_253074_(Lang.getComponent("of.options.skinCustomisation.ofCape"), button -> this.f_96541_.m_91152_(new GuiScreenCapeOF(this)))
         .m_253046_(200, 20)
         .m_253136_();
      this.btnCape.m_264152_(this.f_96543_ / 2 - 100, this.f_337270_.m_269355_() + 8 + 24 * (this.countButtons / 2));
      this.m_142416_(this.btnCape);
   }

   protected void m_267719_() {
      super.m_267719_();
      if (this.btnCape != null) {
         this.m_169411_(this.btnCape);
         this.m_142416_(this.btnCape);
         this.btnCape.m_264152_(this.f_96543_ / 2 - 100, this.f_337270_.m_269355_() + 8 + 24 * (this.countButtons / 2));
      }
   }
}
