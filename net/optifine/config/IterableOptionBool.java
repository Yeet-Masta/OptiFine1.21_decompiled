package net.optifine.config;

import net.minecraft.network.chat.Component;
import net.optifine.Lang;

public class IterableOptionBool extends IteratableOptionOF implements IPersitentOption {
   private String resourceKey;
   private ToBooleanFunction<net.minecraft.client.Options> getter;
   private ObjBooleanConsumer<net.minecraft.client.Options> setter;
   private String saveKey;

   public IterableOptionBool(
      String resourceKey, ToBooleanFunction<net.minecraft.client.Options> getter, ObjBooleanConsumer<net.minecraft.client.Options> setter, String saveKey
   ) {
      super(resourceKey);
      this.resourceKey = resourceKey;
      this.getter = getter;
      this.setter = setter;
      this.saveKey = saveKey;
   }

   @Override
   public void nextOptionValue(int dirIn) {
      net.minecraft.client.Options opts = this.getOptions();
      boolean val = this.getter.applyAsBool(opts);
      val = !val;
      this.setter.accept(opts, val);
   }

   @Override
   public Component getOptionText() {
      net.minecraft.client.Options opts = this.getOptions();
      String optionLabel = Lang.get(this.resourceKey) + ": ";
      boolean val = this.getter.applyAsBool(opts);
      String valueLabel = val ? Lang.getOn() : Lang.getOff();
      String label = optionLabel + valueLabel;
      Component comp = Component.m_237113_(label);
      return comp;
   }

   @Override
   public String getSaveKey() {
      return this.saveKey;
   }

   @Override
   public void loadValue(net.minecraft.client.Options opts, String s) {
      boolean val = Boolean.valueOf(s);
      this.setter.accept(opts, val);
   }

   @Override
   public String getSaveText(net.minecraft.client.Options opts) {
      boolean val = this.getter.applyAsBool(opts);
      return Boolean.toString(val);
   }
}
