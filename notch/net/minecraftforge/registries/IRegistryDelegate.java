package net.minecraftforge.registries;

import net.minecraft.src.C_5265_;

public interface IRegistryDelegate<T> {
   T get();

   C_5265_ name();

   Class<T> type();
}
