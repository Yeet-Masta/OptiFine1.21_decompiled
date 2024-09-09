package net.minecraftforge.registries;

import net.minecraft.src.C_5265_;

public interface IRegistryDelegate {
   Object get();

   C_5265_ name();

   Class type();
}
