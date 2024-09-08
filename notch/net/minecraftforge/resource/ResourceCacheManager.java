package net.minecraftforge.resource;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import net.minecraft.src.C_51_;
import net.minecraft.src.C_5265_;
import net.minecraftforge.common.ForgeConfigSpec;

public class ResourceCacheManager {
   public ResourceCacheManager(boolean supportsReloading, ForgeConfigSpec.BooleanValue indexOffThreadConfig, BiFunction<C_51_, String, Path> pathBuilder) {
   }

   public ResourceCacheManager(boolean supportsReloading, String indexOnThreadConfigurationKey, BiFunction<C_51_, String, Path> pathBuilder) {
   }

   public static boolean shouldUseCache() {
      return false;
   }

   public boolean hasCached(C_51_ packType, String namespace) {
      return false;
   }

   public Collection<C_5265_> getResources(C_51_ type, String resourceNamespace, Path inputPath, Predicate<C_5265_> filter) {
      return null;
   }

   public void index(String nameSpace) {
   }
}
