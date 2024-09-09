package net.optifine.util;

import com.google.common.collect.Lists;
import com.mojang.serialization.Lifecycle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.optifine.config.BiomeId;
import net.optifine.override.ChunkCacheOF;

public class BiomeUtils {
   private static Registry defaultBiomeRegistry = makeDefaultBiomeRegistry();
   private static Registry biomeRegistry;
   private static Level biomeWorld;
   public static Biome PLAINS;
   public static Biome SUNFLOWER_PLAINS;
   public static Biome SNOWY_PLAINS;
   public static Biome ICE_SPIKES;
   public static Biome DESERT;
   public static Biome WINDSWEPT_HILLS;
   public static Biome WINDSWEPT_GRAVELLY_HILLS;
   public static Biome MUSHROOM_FIELDS;
   public static Biome SWAMP;
   public static Biome MANGROVE_SWAMP;
   public static Biome THE_VOID;

   public static void onWorldChanged(Level worldIn) {
      biomeRegistry = getBiomeRegistry(worldIn);
      biomeWorld = worldIn;
      PLAINS = (Biome)biomeRegistry.m_6246_(Biomes.f_48202_);
      SUNFLOWER_PLAINS = (Biome)biomeRegistry.m_6246_(Biomes.f_48176_);
      SNOWY_PLAINS = (Biome)biomeRegistry.m_6246_(Biomes.f_186761_);
      ICE_SPIKES = (Biome)biomeRegistry.m_6246_(Biomes.f_48182_);
      DESERT = (Biome)biomeRegistry.m_6246_(Biomes.f_48203_);
      WINDSWEPT_HILLS = (Biome)biomeRegistry.m_6246_(Biomes.f_186765_);
      WINDSWEPT_GRAVELLY_HILLS = (Biome)biomeRegistry.m_6246_(Biomes.f_186766_);
      MUSHROOM_FIELDS = (Biome)biomeRegistry.m_6246_(Biomes.f_48215_);
      SWAMP = (Biome)biomeRegistry.m_6246_(Biomes.f_48207_);
      MANGROVE_SWAMP = (Biome)biomeRegistry.m_6246_(Biomes.f_220595_);
      THE_VOID = (Biome)biomeRegistry.m_6246_(Biomes.f_48173_);
   }

   private static Biome getBiomeSafe(Registry registry, ResourceKey biomeKey, Supplier biomeDefault) {
      Biome biome = (Biome)registry.m_6246_(biomeKey);
      if (biome == null) {
         biome = (Biome)biomeDefault.get();
      }

      return biome;
   }

   public static Registry getBiomeRegistry(Level worldIn) {
      if (worldIn != null) {
         if (worldIn == biomeWorld) {
            return biomeRegistry;
         } else {
            Registry worldBiomeRegistry = worldIn.m_9598_().m_175515_(Registries.f_256952_);
            Registry fixedBiomeRegistry = fixBiomeIds(defaultBiomeRegistry, worldBiomeRegistry);
            return fixedBiomeRegistry;
         }
      } else {
         return defaultBiomeRegistry;
      }
   }

   private static Registry makeDefaultBiomeRegistry() {
      MappedRegistry registry = new MappedRegistry(ResourceKey.m_135788_(new ResourceLocation("biomes")), Lifecycle.stable(), true);
      Set biomeKeys = Biomes.getBiomeKeys();
      Iterator var2 = biomeKeys.iterator();

      while(var2.hasNext()) {
         ResourceKey biomeKey = (ResourceKey)var2.next();
         Biome.BiomeBuilder bb = new Biome.BiomeBuilder();
         bb.m_264558_(false);
         bb.m_47609_(0.0F);
         bb.m_47611_(0.0F);
         bb.m_47603_((new BiomeSpecialEffects.Builder()).m_48019_(0).m_48034_(0).m_48037_(0).m_48040_(0).m_48018_());
         bb.m_47605_((new MobSpawnSettings.Builder()).m_48381_());
         bb.m_47601_((new BiomeGenerationSettings.Builder((HolderGetter)null, (HolderGetter)null)).m_255380_());
         Biome biome = bb.m_47592_();
         registry.m_203693_(biome);
         registry.m_255290_(biomeKey, biome, RegistrationInfo.f_316022_);
      }

      return registry;
   }

   private static Registry fixBiomeIds(Registry idRegistry, Registry valueRegistry) {
      MappedRegistry registry = new MappedRegistry(ResourceKey.m_135788_(new ResourceLocation("biomes")), Lifecycle.stable(), true);
      Set biomeKeys = Biomes.getBiomeKeys();
      Iterator var4 = biomeKeys.iterator();

      while(var4.hasNext()) {
         ResourceKey biomeKey = (ResourceKey)var4.next();
         Biome biome = (Biome)valueRegistry.m_6246_(biomeKey);
         if (biome == null) {
            biome = (Biome)idRegistry.m_6246_(biomeKey);
         }

         idRegistry.m_7447_((Biome)idRegistry.m_6246_(biomeKey));
         registry.m_203693_(biome);
         registry.m_255290_(biomeKey, biome, RegistrationInfo.f_316022_);
      }

      Set biomeValueKeys = valueRegistry.m_214010_();
      Iterator var10 = biomeValueKeys.iterator();

      while(var10.hasNext()) {
         ResourceKey biomeKey = (ResourceKey)var10.next();
         if (!registry.m_142003_(biomeKey)) {
            Biome biome = (Biome)valueRegistry.m_6246_(biomeKey);
            registry.m_203693_(biome);
            registry.m_255290_(biomeKey, biome, RegistrationInfo.f_316022_);
         }
      }

      return registry;
   }

   public static Registry getBiomeRegistry() {
      return biomeRegistry;
   }

   public static ResourceLocation getLocation(Biome biome) {
      return getBiomeRegistry().m_7981_(biome);
   }

   public static int getId(Biome biome) {
      return getBiomeRegistry().m_7447_(biome);
   }

   public static int getId(ResourceLocation loc) {
      Biome biome = getBiome(loc);
      return getBiomeRegistry().m_7447_(biome);
   }

   public static BiomeId getBiomeId(ResourceLocation loc) {
      return BiomeId.make(loc);
   }

   public static Biome getBiome(ResourceLocation loc) {
      return (Biome)getBiomeRegistry().m_7745_(loc);
   }

   public static Set getLocations() {
      return getBiomeRegistry().m_6566_();
   }

   public static List getBiomes() {
      return Lists.newArrayList(biomeRegistry);
   }

   public static List getBiomeIds() {
      return getBiomeIds(getLocations());
   }

   public static List getBiomeIds(Collection locations) {
      List biomeIds = new ArrayList();
      Iterator var2 = locations.iterator();

      while(var2.hasNext()) {
         ResourceLocation loc = (ResourceLocation)var2.next();
         BiomeId bi = BiomeId.make(loc);
         if (bi != null) {
            biomeIds.add(bi);
         }
      }

      return biomeIds;
   }

   public static Biome getBiome(BlockAndTintGetter lightReader, BlockPos blockPos) {
      Biome biome = PLAINS;
      if (lightReader instanceof ChunkCacheOF) {
         biome = ((ChunkCacheOF)lightReader).getBiome(blockPos);
      } else if (lightReader instanceof LevelReader) {
         biome = (Biome)((LevelReader)lightReader).m_204166_(blockPos).m_203334_();
      }

      return biome;
   }

   public static BiomeCategory getBiomeCategory(Holder holder) {
      if (holder.m_203334_() == THE_VOID) {
         return BiomeCategory.NONE;
      } else if (holder.m_203656_(BiomeTags.f_207609_)) {
         return BiomeCategory.TAIGA;
      } else if (holder.m_203334_() != WINDSWEPT_HILLS && holder.m_203334_() != WINDSWEPT_GRAVELLY_HILLS) {
         if (holder.m_203656_(BiomeTags.f_207610_)) {
            return BiomeCategory.JUNGLE;
         } else if (holder.m_203656_(BiomeTags.f_207607_)) {
            return BiomeCategory.MESA;
         } else if (holder.m_203334_() != PLAINS && holder.m_203334_() != PLAINS) {
            if (holder.m_203656_(BiomeTags.f_215816_)) {
               return BiomeCategory.SAVANNA;
            } else if (holder.m_203334_() != SNOWY_PLAINS && holder.m_203334_() != ICE_SPIKES) {
               if (holder.m_203656_(BiomeTags.f_215818_)) {
                  return BiomeCategory.THEEND;
               } else if (holder.m_203656_(BiomeTags.f_207604_)) {
                  return BiomeCategory.BEACH;
               } else if (holder.m_203656_(BiomeTags.f_207611_)) {
                  return BiomeCategory.FOREST;
               } else if (holder.m_203656_(BiomeTags.f_207603_)) {
                  return BiomeCategory.OCEAN;
               } else if (holder.m_203334_() == DESERT) {
                  return BiomeCategory.DESERT;
               } else if (holder.m_203656_(BiomeTags.f_207605_)) {
                  return BiomeCategory.RIVER;
               } else if (holder.m_203334_() != SWAMP && holder.m_203334_() != MANGROVE_SWAMP) {
                  if (holder.m_203334_() == MUSHROOM_FIELDS) {
                     return BiomeCategory.MUSHROOM;
                  } else if (holder.m_203656_(BiomeTags.f_207612_)) {
                     return BiomeCategory.NETHER;
                  } else if (holder.m_203656_(BiomeTags.f_215801_)) {
                     return BiomeCategory.UNDERGROUND;
                  } else {
                     return holder.m_203656_(BiomeTags.f_207606_) ? BiomeCategory.MOUNTAIN : BiomeCategory.PLAINS;
                  }
               } else {
                  return BiomeCategory.SWAMP;
               }
            } else {
               return BiomeCategory.ICY;
            }
         } else {
            return BiomeCategory.PLAINS;
         }
      } else {
         return BiomeCategory.EXTREME_HILLS;
      }
   }

   public static float getDownfall(Biome biome) {
      return Biomes.getDownfall(biome);
   }

   public static Biome.Precipitation getPrecipitation(Biome biome) {
      if (!biome.m_264473_()) {
         return Precipitation.NONE;
      } else {
         return (double)biome.m_47554_() < 0.1 ? Precipitation.SNOW : Precipitation.RAIN;
      }
   }

   static {
      biomeRegistry = getBiomeRegistry(Minecraft.m_91087_().f_91073_);
      biomeWorld = Minecraft.m_91087_().f_91073_;
      PLAINS = (Biome)biomeRegistry.m_6246_(Biomes.f_48202_);
      SUNFLOWER_PLAINS = (Biome)biomeRegistry.m_6246_(Biomes.f_48176_);
      SNOWY_PLAINS = (Biome)biomeRegistry.m_6246_(Biomes.f_186761_);
      ICE_SPIKES = (Biome)biomeRegistry.m_6246_(Biomes.f_48182_);
      DESERT = (Biome)biomeRegistry.m_6246_(Biomes.f_48203_);
      WINDSWEPT_HILLS = (Biome)biomeRegistry.m_6246_(Biomes.f_186765_);
      WINDSWEPT_GRAVELLY_HILLS = (Biome)biomeRegistry.m_6246_(Biomes.f_186766_);
      MUSHROOM_FIELDS = (Biome)biomeRegistry.m_6246_(Biomes.f_48215_);
      SWAMP = (Biome)biomeRegistry.m_6246_(Biomes.f_48207_);
      MANGROVE_SWAMP = (Biome)biomeRegistry.m_6246_(Biomes.f_220595_);
      THE_VOID = (Biome)biomeRegistry.m_6246_(Biomes.f_48173_);
   }
}
