package net.optifine.config;

import net.minecraft.src.C_213334_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4995_;
import net.minecraft.src.C_4996_;
import net.optifine.Config;
import net.optifine.Lang;

public class FloatOptions {
   public static C_4996_ getTextComponent(C_213334_ option, double val) {
      C_3401_ settings = C_3391_.m_91087_().f_91066_;
      String s = C_4513_.m_118938_(option.getResourceKey(), new Object[0]) + ": ";
      if (option == settings.RENDER_DISTANCE) {
         return C_3401_.genericValueLabel(option.getResourceKey(), "options.chunks", (int)val);
      } else if (option == settings.MIPMAP_LEVELS) {
         if (val >= 4.0) {
            return C_3401_.genericValueLabel(option.getResourceKey(), "of.general.max");
         } else {
            return (C_4996_)(val == 0.0 ? C_4995_.m_130663_(option.getCaption(), false) : C_3401_.genericValueLabel(option.getResourceKey(), (int)val));
         }
      } else if (option == settings.BIOME_BLEND_RADIUS) {
         int i = (int)val * 2 + 1;
         return C_3401_.genericValueLabel(option.getResourceKey(), "options.biomeBlendRadius." + i);
      } else {
         String text = getText(option, val);
         return text != null ? C_4996_.m_237113_(text) : null;
      }
   }

   public static String getText(C_213334_ option, double val) {
      String s = C_4513_.m_118938_(option.getResourceKey(), new Object[0]) + ": ";
      if (option == Option.AO_LEVEL) {
         return val == 0.0 ? s + C_4513_.m_118938_("options.off", new Object[0]) : s + (int)(val * 100.0) + "%";
      } else if (option == Option.MIPMAP_TYPE) {
         int valInt = (int)val;
         switch (valInt) {
            case 0:
               return s + Lang.get("of.options.mipmap.nearest");
            case 1:
               return s + Lang.get("of.options.mipmap.linear");
            case 2:
               return s + Lang.get("of.options.mipmap.bilinear");
            case 3:
               return s + Lang.get("of.options.mipmap.trilinear");
            default:
               return s + "of.options.mipmap.nearest";
         }
      } else if (option == Option.AA_LEVEL) {
         int ofAaLevel = (int)val;
         String suffix = "";
         if (ofAaLevel != Config.getAntialiasingLevel()) {
            suffix = " (" + Lang.get("of.general.restart") + ")";
         }

         return ofAaLevel == 0 ? s + Lang.getOff() + suffix : s + ofAaLevel + suffix;
      } else if (option == Option.AF_LEVEL) {
         int ofAfLevel = (int)val;
         return ofAfLevel == 1 ? s + Lang.getOff() : s + ofAfLevel;
      } else {
         return null;
      }
   }

   public static boolean supportAdjusting(C_213334_ option) {
      C_4996_ text = getTextComponent(option, 0.0);
      return text != null;
   }
}
