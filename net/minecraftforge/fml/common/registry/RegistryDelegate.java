package net.minecraftforge.fml.common.registry;

import net.minecraft.src.C_5265_;

public interface RegistryDelegate {
   Object get();

   C_5265_ name();

   Class type();
}
