package net.optifine.shaders.config;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ShaderProfile {
   private String name = null;
   private Map<String, String> mapOptionValues = new LinkedHashMap();
   private Set<String> disabledPrograms = new LinkedHashSet();

   public ShaderProfile(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void addOptionValue(String option, String value) {
      this.mapOptionValues.put(option, value);
   }

   public void addOptionValues(ShaderProfile prof) {
      if (prof != null) {
         this.mapOptionValues.putAll(prof.mapOptionValues);
      }
   }

   public void applyOptionValues(ShaderOption[] options) {
      for (int i = 0; i < options.length; i++) {
         ShaderOption so = options[i];
         String key = so.getName();
         String val = (String)this.mapOptionValues.get(key);
         if (val != null) {
            so.setValue(val);
         }
      }
   }

   public String[] getOptions() {
      Set<String> keys = this.mapOptionValues.keySet();
      return (String[])keys.toArray(new String[keys.size()]);
   }

   public String getValue(String key) {
      return (String)this.mapOptionValues.get(key);
   }

   public void addDisabledProgram(String program) {
      this.disabledPrograms.add(program);
   }

   public void removeDisabledProgram(String program) {
      this.disabledPrograms.remove(program);
   }

   public Collection<String> getDisabledPrograms() {
      return new LinkedHashSet(this.disabledPrograms);
   }

   public void addDisabledPrograms(Collection<String> programs) {
      this.disabledPrograms.addAll(programs);
   }

   public boolean isProgramDisabled(String program) {
      return this.disabledPrograms.m_274455_(program);
   }
}
