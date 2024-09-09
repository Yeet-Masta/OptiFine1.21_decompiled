package net.optifine.config;

public interface SliderableValueSetInt<T> extends net.minecraft.client.OptionInstance.SliderableValueSet<T> {
   net.minecraft.client.OptionInstance.IntRangeBase getIntRange();
}
