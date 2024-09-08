package net.minecraft.client.gui.screens.options;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.optifine.Lang;
import net.optifine.gui.GuiScreenCapeOF;

public class SkinCustomizationScreen extends OptionsSubScreen {
   private static final Component f_336923_ = Component.m_237115_("options.skinCustomisation.title");
   private Button btnCape;
   private int countButtons;

   public SkinCustomizationScreen(Screen screenIn, Options optionsIn) {
      super(screenIn, optionsIn, f_336923_);
   }

   protected void m_338523_() {
      List<AbstractWidget> list = new ArrayList();

      for (PlayerModelPart playermodelpart : PlayerModelPart.values()) {
         list.add(
            CycleButton.m_168916_(this.f_337734_.m_168416_(playermodelpart))
               .m_323445_(playermodelpart.m_36447_(), (p_338916_2_, p_338916_3_) -> this.f_337734_.m_168418_(playermodelpart, p_338916_3_))
         );
      }

      list.add(this.f_337734_.m_232107_().m_324463_(this.f_337734_));
      this.f_337426_.m_324569_(list);
      this.countButtons = list.size();
      this.btnCape = Button.m_253074_(Lang.getComponent("of.options.skinCustomisation.ofCape"), button -> this.f_96541_.m_91152_(new GuiScreenCapeOF(this)))
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
