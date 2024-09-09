package net.optifine.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.gui.GuiButtonShaderOption;
import net.optifine.util.StrUtils;

public class TooltipProviderShaderOptions extends TooltipProviderOptions {
   public String[] getTooltipLines(AbstractWidget btn, int width) {
      if (!(btn instanceof GuiButtonShaderOption btnSo)) {
         return null;
      } else {
         ShaderOption so = btnSo.getShaderOption();
         String[] lines = this.makeTooltipLines(so, width);
         return lines;
      }
   }

   private String[] makeTooltipLines(ShaderOption so, int width) {
      String name = so.getNameText();
      String desc = Config.normalize(so.getDescriptionText()).trim();
      String[] descs = this.splitDescription(desc);
      Options settings = Config.getGameSettings();
      String id = null;
      String var10000;
      if (!name.equals(so.getName()) && settings.f_92125_) {
         var10000 = Lang.get("of.general.id");
         id = "§8" + var10000 + ": " + so.getName();
      }

      String source = null;
      if (so.getPaths() != null && settings.f_92125_) {
         var10000 = Lang.get("of.general.from");
         source = "§8" + var10000 + ": " + Config.arrayToString((Object[])so.getPaths());
      }

      String def = null;
      if (so.getValueDefault() != null && settings.f_92125_) {
         String defVal = so.isEnabled() ? so.getValueText(so.getValueDefault()) : Lang.get("of.general.ambiguous");
         var10000 = Lang.getDefault();
         def = "§8" + var10000 + ": " + defVal;
      }

      List list = new ArrayList();
      list.add(name);
      list.addAll(Arrays.asList(descs));
      if (id != null) {
         list.add(id);
      }

      if (source != null) {
         list.add(source);
      }

      if (def != null) {
         list.add(def);
      }

      String[] lines = this.makeTooltipLines(width, list);
      return lines;
   }

   private String[] splitDescription(String desc) {
      if (desc.length() <= 0) {
         return new String[0];
      } else {
         desc = StrUtils.removePrefix(desc, "//");
         String[] descs = desc.split("\\. ");

         for(int i = 0; i < descs.length; ++i) {
            descs[i] = "- " + descs[i].trim();
            descs[i] = StrUtils.removeSuffix(descs[i], ".");
         }

         return descs;
      }
   }

   private String[] makeTooltipLines(int width, List args) {
      Font fr = Config.getMinecraft().f_91062_;
      List list = new ArrayList();

      for(int i = 0; i < args.size(); ++i) {
         String arg = (String)args.get(i);
         if (arg != null && arg.length() > 0) {
            FormattedText argComp = Component.m_237113_(arg);
            List parts = fr.m_92865_().m_92414_(argComp, width, Style.f_131099_);
            Iterator it = parts.iterator();

            while(it.hasNext()) {
               FormattedText part = (FormattedText)it.next();
               list.add(part.getString());
            }
         }
      }

      String[] lines = (String[])list.toArray(new String[list.size()]);
      return lines;
   }
}
