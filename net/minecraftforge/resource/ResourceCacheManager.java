package net.minecraftforge.resource;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import net.minecraft.src.C_51_;
import net.minecraftforge.common.ForgeConfigSpec;

public class ResourceCacheManager {
   public ResourceCacheManager(boolean supportsReloading, ForgeConfigSpec.BooleanValue indexOffThreadConfig, BiFunction pathBuilder) {
   }

   public ResourceCacheManager(boolean supportsReloading, String indexOnThreadConfigurationKey, BiFunction pathBuilder) {
   }

   public static boolean shouldUseCache() {
      return false;
   }

   public boolean hasCached(C_51_ packType, String namespace) {
      return false;
   }

   public Collection getResources(C_51_ type, String resourceNamespace, Path inputPath, Predicate filter) {
      return null;
   }

   public void index(String nameSpace) {
   }
}
