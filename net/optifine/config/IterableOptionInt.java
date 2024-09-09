package net.optifine.config;

import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import net.minecraft.network.chat.Component;
import net.optifine.Config;
import net.optifine.Lang;

public class IterableOptionInt extends IteratableOptionOF implements IPersitentOption {
   private String resourceKey;
   private OptionValueInt[] values;
   private ToIntFunction<net.minecraft.client.Options> getter;
   private ObjIntConsumer<net.minecraft.client.Options> setter;
   private String saveKey;

   public IterableOptionInt(
      String resourceKey,
      OptionValueInt[] values,
      ToIntFunction<net.minecraft.client.Options> getter,
      ObjIntConsumer<net.minecraft.client.Options> setter,
      String saveKey
   ) {
      super(resourceKey);
      this.resourceKey = resourceKey;
      this.values = values;
      this.getter = getter;
      this.setter = setter;
      this.saveKey = saveKey;
   }

   @Override
   public void nextOptionValue(int dirIn) {
      net.minecraft.client.Options opts = this.getOptions();
      int value = this.getter.applyAsInt(opts);
      int index = this.getValueIndex(value);
      int indexNext = index + dirIn;
      if (indexNext < this.getIndexMin() || indexNext > this.getIndexMax()) {
         indexNext = dirIn > 0 ? this.getIndexMin() : this.getIndexMax();
      }

      int valueNext = this.values[indexNext].getValue();
      this.setter.accept(opts, valueNext);
   }

   @Override
   public Component getOptionText() {
      net.minecraft.client.Options opts = this.getOptions();
      String optionLabel = Lang.get(this.resourceKey) + ": ";
      int value = this.getter.applyAsInt(opts);
      OptionValueInt optionValue = this.getOptionValue(value);
      if (optionValue == null) {
         return Component.m_237113_(optionLabel + "???");
      } else {
         String valueLabel = Lang.get(optionValue.getResourceKey());
         String label = optionLabel + valueLabel;
         Component comp = Component.m_237113_(label);
         return comp;
      }
   }

   @Override
   public String getSaveKey() {
      return this.saveKey;
   }

   @Override
   public void loadValue(net.minecraft.client.Options opts, String s) {
      int val = Config.parseInt(s, -1);
      if (this.getOptionValue(val) == null) {
         val = this.values[0].getValue();
      }

      this.setter.accept(opts, val);
   }

   @Override
   public String getSaveText(net.minecraft.client.Options opts) {
      int value = this.getter.applyAsInt(opts);
      return Integer.toString(value);
   }

   private OptionValueInt getOptionValue(int value) {
      int index = this.getValueIndex(value);
      return index < 0 ? null : this.values[index];
   }

   private int getValueIndex(int value) {
      for (int i = 0; i < this.values.length; i++) {
         OptionValueInt ovi = this.values[i];
         if (ovi.getValue() == value) {
            return i;
         }
      }

      return -1;
   }

   private int getIndexMin() {
      return 0;
   }

   private int getIndexMax() {
      return this.values.length - 1;
   }
}
