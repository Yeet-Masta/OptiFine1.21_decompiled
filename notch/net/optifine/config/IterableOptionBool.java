package net.optifine.config;

import net.minecraft.src.C_3401_;
import net.minecraft.src.C_4996_;
import net.optifine.Lang;

public class IterableOptionBool extends IteratableOptionOF implements IPersitentOption {
   private String resourceKey;
   private ToBooleanFunction<C_3401_> getter;
   private ObjBooleanConsumer<C_3401_> setter;
   private String saveKey;

   public IterableOptionBool(String resourceKey, ToBooleanFunction<C_3401_> getter, ObjBooleanConsumer<C_3401_> setter, String saveKey) {
      super(resourceKey);
      this.resourceKey = resourceKey;
      this.getter = getter;
      this.setter = setter;
      this.saveKey = saveKey;
   }

   @Override
   public void nextOptionValue(int dirIn) {
      C_3401_ opts = this.getOptions();
      boolean val = this.getter.applyAsBool(opts);
      val = !val;
      this.setter.accept(opts, val);
   }

   public C_4996_ getOptionText() {
      C_3401_ opts = this.getOptions();
      String optionLabel = Lang.get(this.resourceKey) + ": ";
      boolean val = this.getter.applyAsBool(opts);
      String valueLabel = val ? Lang.getOn() : Lang.getOff();
      String label = optionLabel + valueLabel;
      C_4996_ comp = C_4996_.m_237113_(label);
      return comp;
   }

   @Override
   public String getSaveKey() {
      return this.saveKey;
   }

   public void loadValue(C_3401_ opts, String s) {
      boolean val = Boolean.valueOf(s);
      this.setter.accept(opts, val);
   }

   public String getSaveText(C_3401_ opts) {
      boolean val = this.getter.applyAsBool(opts);
      return Boolean.toString(val);
   }
}
