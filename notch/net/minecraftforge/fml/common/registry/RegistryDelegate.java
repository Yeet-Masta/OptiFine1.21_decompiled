package net.minecraftforge.fml.common.registry;

import net.minecraft.src.C_5265_;

public interface RegistryDelegate<T> {
   T get();

   C_5265_ name();

   Class<T> type();
}
