package net.optifine.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_3429_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5000_;
import net.minecraft.src.C_5020_;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.gui.GuiButtonShaderOption;
import net.optifine.util.StrUtils;

public class TooltipProviderShaderOptions extends TooltipProviderOptions {
   public String[] getTooltipLines(C_3449_ btn, int width) {
      if (!(btn instanceof GuiButtonShaderOption btnSo)) {
         return null;
      } else {
         ShaderOption so = btnSo.getShaderOption();
         return this.makeTooltipLines(so, width);
      }
   }

   private String[] makeTooltipLines(ShaderOption so, int width) {
      String name = so.getNameText();
      String desc = Config.normalize(so.getDescriptionText()).trim();
      String[] descs = this.splitDescription(desc);
      C_3401_ settings = Config.getGameSettings();
      String id = null;
      if (!name.equals(so.getName()) && settings.f_92125_) {
         id = "\u00a78" + Lang.get("of.general.id") + ": " + so.getName();
      }

      String source = null;
      if (so.getPaths() != null && settings.f_92125_) {
         source = "\u00a78" + Lang.get("of.general.from") + ": " + Config.arrayToString((Object[])so.getPaths());
      }

      String def = null;
      if (so.getValueDefault() != null && settings.f_92125_) {
         String defVal = so.isEnabled() ? so.getValueText(so.getValueDefault()) : Lang.get("of.general.ambiguous");
         def = "\u00a78" + Lang.getDefault() + ": " + defVal;
      }

      List<String> list = new ArrayList();
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

      return this.makeTooltipLines(width, list);
   }

   private String[] splitDescription(String desc) {
      if (desc.length() <= 0) {
         return new String[0];
      } else {
         desc = StrUtils.removePrefix(desc, "//");
         String[] descs = desc.split("\\. ");

         for (int i = 0; i < descs.length; i++) {
            descs[i] = "- " + descs[i].trim();
            descs[i] = StrUtils.removeSuffix(descs[i], ".");
         }

         return descs;
      }
   }

   private String[] makeTooltipLines(int width, List<String> args) {
      C_3429_ fr = Config.getMinecraft().f_91062_;
      List<String> list = new ArrayList();

      for (int i = 0; i < args.size(); i++) {
         String arg = (String)args.get(i);
         if (arg != null && arg.length() > 0) {
            C_5000_ argComp = C_4996_.m_237113_(arg);

            for (C_5000_ part : fr.m_92865_().m_92414_(argComp, width, C_5020_.f_131099_)) {
               list.add(part.getString());
            }
         }
      }

      return (String[])list.toArray(new String[list.size()]);
   }
}
