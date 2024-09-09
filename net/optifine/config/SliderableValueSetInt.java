package net.optifine.config;

import net.minecraft.client.OptionInstance;

public interface SliderableValueSetInt extends OptionInstance.SliderableValueSet {
   OptionInstance.IntRangeBase getIntRange();
}
