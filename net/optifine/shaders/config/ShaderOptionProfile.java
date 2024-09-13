package net.optifine.shaders.config;

import java.util.ArrayList;
import java.util.List;
import net.optifine.Lang;
import net.optifine.shaders.ShaderUtils;
import net.optifine.shaders.Shaders;

public class ShaderOptionProfile extends ShaderOption {
   private ShaderProfile[] profiles = null;
   private ShaderOption[] options = null;
   private static String NAME_PROFILE;
   private static String VALUE_CUSTOM;

   public ShaderOptionProfile(ShaderProfile[] profiles, ShaderOption[] options) {
      super("<profile>", "", detectProfileName(profiles, options), getProfileNames(profiles), detectProfileName(profiles, options, true), null);
      this.profiles = profiles;
      this.options = options;
   }

   @Override
   public void nextValue() {
      super.nextValue();
      if (this.getValue().equals("<custom>")) {
         super.nextValue();
      }

      this.applyProfileOptions();
   }

   public void updateProfile() {
      ShaderProfile prof = this.getProfile(this.getValue());
      if (prof == null || !ShaderUtils.matchProfile(prof, this.options, false)) {
         String val = detectProfileName(this.profiles, this.options);
         this.setValue(val);
      }
   }

   private void applyProfileOptions() {
      ShaderProfile prof = this.getProfile(this.getValue());
      if (prof != null) {
         String[] opts = prof.getOptions();

         for (int i = 0; i < opts.length; i++) {
            String name = opts[i];
            ShaderOption so = this.getOption(name);
            if (so != null) {
               String val = prof.getValue(name);
               so.setValue(val);
            }
         }
      }
   }

   private ShaderOption getOption(String name) {
      for (int i = 0; i < this.options.length; i++) {
         ShaderOption so = this.options[i];
         if (so.getName().equals(name)) {
            return so;
         }
      }

      return null;
   }

   private ShaderProfile getProfile(String name) {
      for (int i = 0; i < this.profiles.length; i++) {
         ShaderProfile prof = this.profiles[i];
         if (prof.getName().equals(name)) {
            return prof;
         }
      }

      return null;
   }

   @Override
   public String getNameText() {
      return Lang.get("of.shaders.profile");
   }

   @Override
   public String getValueText(String val) {
      return val.equals("<custom>") ? Lang.get("of.general.custom", "<custom>") : Shaders.translate("profile." + val, val);
   }

   @Override
   public String getValueColor(String val) {
      return val.equals("<custom>") ? "\u00a7c" : "\u00a7a";
   }

   @Override
   public String getDescriptionText() {
      String text = Shaders.translate("profile.comment", null);
      if (text != null) {
         return text;
      } else {
         StringBuffer sb = new StringBuffer();

         for (int i = 0; i < this.profiles.length; i++) {
            String name = this.profiles[i].getName();
            if (name != null) {
               String profText = Shaders.translate("profile." + name + ".comment", null);
               if (profText != null) {
                  sb.append(profText);
                  if (!profText.endsWith(". ")) {
                     sb.append(". ");
                  }
               }
            }
         }

         return sb.toString();
      }
   }

   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts) {
      return detectProfileName(profs, opts, false);
   }

   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
      ShaderProfile prof = ShaderUtils.detectProfile(profs, opts, def);
      return prof == null ? "<custom>" : prof.getName();
   }

   private static String[] getProfileNames(ShaderProfile[] profs) {
      List<String> list = new ArrayList();

      for (int i = 0; i < profs.length; i++) {
         ShaderProfile prof = profs[i];
         list.add(prof.getName());
      }

      list.add("<custom>");
      return (String[])list.toArray(new String[list.size()]);
   }
}
