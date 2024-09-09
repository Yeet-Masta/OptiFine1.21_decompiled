package net.minecraftforge.fml.common.registry;

public interface RegistryDelegate<T> {
   T get();

   ResourceLocation name();

   Class<T> type();
}
