package net.minecraftforge.registries;

public interface IRegistryDelegate<T> {
   T get();

   ResourceLocation name();

   Class<T> type();
}
