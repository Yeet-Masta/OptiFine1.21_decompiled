package net.optifine.config;

import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.optifine.Lang;

public class IterableOptionBool extends IteratableOptionOF implements IPersitentOption {
   private String resourceKey;
   private ToBooleanFunction getter;
   private ObjBooleanConsumer setter;
   private String saveKey;

   public IterableOptionBool(String resourceKey, ToBooleanFunction getter, ObjBooleanConsumer setter, String saveKey) {
      super(resourceKey);
      this.resourceKey = resourceKey;
      this.getter = getter;
      this.setter = setter;
      this.saveKey = saveKey;
   }

   public void nextOptionValue(int dirIn) {
      Options opts = this.getOptions();
      boolean val = this.getter.applyAsBool(opts);
      val = !val;
      this.setter.accept(opts, val);
   }

   public Component getOptionText() {
      Options opts = this.getOptions();
      String var10000 = Lang.get(this.resourceKey);
      String optionLabel = var10000 + ": ";
      boolean val = this.getter.applyAsBool(opts);
      String valueLabel = val ? Lang.getOn() : Lang.getOff();
      String label = optionLabel + valueLabel;
      Component comp = Component.m_237113_(label);
      return comp;
   }

   public String getSaveKey() {
      return this.saveKey;
   }

   public void loadValue(Options opts, String s) {
      boolean val = Boolean.valueOf(s);
      this.setter.accept(opts, val);
   }

   public String getSaveText(Options opts) {
      boolean val = this.getter.applyAsBool(opts);
      return Boolean.toString(val);
   }
}
