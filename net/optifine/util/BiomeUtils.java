package net.optifine.util;

import com.google.common.collect.Lists;
import com.mojang.serialization.Lifecycle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.BiomeSpecialEffects.Builder;
import net.optifine.config.BiomeId;
import net.optifine.override.ChunkCacheOF;

public class BiomeUtils {
   private static Registry<Biome> defaultBiomeRegistry = makeDefaultBiomeRegistry();
   private static Registry<Biome> biomeRegistry = getBiomeRegistry(Minecraft.m_91087_().f_91073_);
   private static Level biomeWorld = Minecraft.m_91087_().f_91073_;
   public static Biome PLAINS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48202_);
   public static Biome SUNFLOWER_PLAINS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48176_);
   public static Biome SNOWY_PLAINS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_186761_);
   public static Biome ICE_SPIKES = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48182_);
   public static Biome DESERT = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48203_);
   public static Biome WINDSWEPT_HILLS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_186765_);
   public static Biome WINDSWEPT_GRAVELLY_HILLS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_186766_);
   public static Biome MUSHROOM_FIELDS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48215_);
   public static Biome SWAMP = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48207_);
   public static Biome MANGROVE_SWAMP = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_220595_);
   public static Biome THE_VOID = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48173_);

   public static void onWorldChanged(Level worldIn) {
      biomeRegistry = getBiomeRegistry(worldIn);
      biomeWorld = worldIn;
      PLAINS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48202_);
      SUNFLOWER_PLAINS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48176_);
      SNOWY_PLAINS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_186761_);
      ICE_SPIKES = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48182_);
      DESERT = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48203_);
      WINDSWEPT_HILLS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_186765_);
      WINDSWEPT_GRAVELLY_HILLS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_186766_);
      MUSHROOM_FIELDS = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48215_);
      SWAMP = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48207_);
      MANGROVE_SWAMP = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_220595_);
      THE_VOID = (Biome)biomeRegistry.m_6246_(net.minecraft.world.level.biome.Biomes.f_48173_);
   }

   private static Biome getBiomeSafe(Registry<Biome> registry, ResourceKey<Biome> biomeKey, Supplier<Biome> biomeDefault) {
      Biome biome = (Biome)registry.m_6246_(biomeKey);
      if (biome == null) {
         biome = (Biome)biomeDefault.get();
      }

      return biome;
   }

   public static Registry<Biome> getBiomeRegistry(Level worldIn) {
      if (worldIn != null) {
         if (worldIn == biomeWorld) {
            return biomeRegistry;
         } else {
            Registry<Biome> worldBiomeRegistry = worldIn.m_9598_().m_175515_(Registries.f_256952_);
            return fixBiomeIds(defaultBiomeRegistry, worldBiomeRegistry);
         }
      } else {
         return defaultBiomeRegistry;
      }
   }

   private static Registry<Biome> makeDefaultBiomeRegistry() {
      MappedRegistry<Biome> registry = new MappedRegistry(
         ResourceKey.m_135788_(new net.minecraft.resources.ResourceLocation("biomes")), Lifecycle.stable(), true
      );

      for (ResourceKey<Biome> biomeKey : net.minecraft.world.level.biome.Biomes.getBiomeKeys()) {
         BiomeBuilder bb = new BiomeBuilder();
         bb.m_264558_(false);
         bb.m_47609_(0.0F);
         bb.m_47611_(0.0F);
         bb.m_47603_(new Builder().m_48019_(0).m_48034_(0).m_48037_(0).m_48040_(0).m_48018_());
         bb.m_47605_(new net.minecraft.world.level.biome.MobSpawnSettings.Builder().m_48381_());
         bb.m_47601_(new net.minecraft.world.level.biome.BiomeGenerationSettings.Builder(null, null).m_255380_());
         Biome biome = bb.m_47592_();
         registry.m_203693_(biome);
         Reference var6 = registry.m_255290_(biomeKey, biome, RegistrationInfo.f_316022_);
      }

      return registry;
   }

   private static Registry<Biome> fixBiomeIds(Registry<Biome> idRegistry, Registry<Biome> valueRegistry) {
      MappedRegistry<Biome> registry = new MappedRegistry(
         ResourceKey.m_135788_(new net.minecraft.resources.ResourceLocation("biomes")), Lifecycle.stable(), true
      );

      for (ResourceKey<Biome> biomeKey : net.minecraft.world.level.biome.Biomes.getBiomeKeys()) {
         Biome biome = (Biome)valueRegistry.m_6246_(biomeKey);
         if (biome == null) {
            biome = (Biome)idRegistry.m_6246_(biomeKey);
         }

         int id = idRegistry.m_7447_((Biome)idRegistry.m_6246_(biomeKey));
         registry.m_203693_(biome);
         Reference var8 = registry.m_255290_(biomeKey, biome, RegistrationInfo.f_316022_);
      }

      for (ResourceKey<Biome> biomeKey : valueRegistry.m_214010_()) {
         if (!registry.m_142003_(biomeKey)) {
            Biome biome = (Biome)valueRegistry.m_6246_(biomeKey);
            registry.m_203693_(biome);
            Reference var13 = registry.m_255290_(biomeKey, biome, RegistrationInfo.f_316022_);
         }
      }

      return registry;
   }

   public static Registry<Biome> getBiomeRegistry() {
      return biomeRegistry;
   }

   public static net.minecraft.resources.ResourceLocation getLocation(Biome biome) {
      return getBiomeRegistry().m_7981_(biome);
   }

   public static int getId(Biome biome) {
      return getBiomeRegistry().m_7447_(biome);
   }

   public static int getId(net.minecraft.resources.ResourceLocation loc) {
      Biome biome = getBiome(loc);
      return getBiomeRegistry().m_7447_(biome);
   }

   public static BiomeId getBiomeId(net.minecraft.resources.ResourceLocation loc) {
      return BiomeId.make(loc);
   }

   public static Biome getBiome(net.minecraft.resources.ResourceLocation loc) {
      return (Biome)getBiomeRegistry().m_7745_(loc);
   }

   public static Set<net.minecraft.resources.ResourceLocation> getLocations() {
      return getBiomeRegistry().m_6566_();
   }

   public static List<Biome> getBiomes() {
      return Lists.newArrayList(biomeRegistry);
   }

   public static List<BiomeId> getBiomeIds() {
      return getBiomeIds(getLocations());
   }

   public static List<BiomeId> getBiomeIds(Collection<net.minecraft.resources.ResourceLocation> locations) {
      List<BiomeId> biomeIds = new ArrayList();

      for (net.minecraft.resources.ResourceLocation loc : locations) {
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

   public static BiomeCategory getBiomeCategory(Holder<Biome> holder) {
      if (holder.m_203334_() == THE_VOID) {
         return BiomeCategory.NONE;
      } else if (holder.m_203656_(BiomeTags.f_207609_)) {
         return BiomeCategory.TAIGA;
      } else if (holder.m_203334_() == WINDSWEPT_HILLS || holder.m_203334_() == WINDSWEPT_GRAVELLY_HILLS) {
         return BiomeCategory.EXTREME_HILLS;
      } else if (holder.m_203656_(BiomeTags.f_207610_)) {
         return BiomeCategory.JUNGLE;
      } else if (holder.m_203656_(BiomeTags.f_207607_)) {
         return BiomeCategory.MESA;
      } else if (holder.m_203334_() == PLAINS || holder.m_203334_() == PLAINS) {
         return BiomeCategory.PLAINS;
      } else if (holder.m_203656_(BiomeTags.f_215816_)) {
         return BiomeCategory.SAVANNA;
      } else if (holder.m_203334_() == SNOWY_PLAINS || holder.m_203334_() == ICE_SPIKES) {
         return BiomeCategory.ICY;
      } else if (holder.m_203656_(BiomeTags.f_215818_)) {
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
      } else if (holder.m_203334_() == SWAMP || holder.m_203334_() == MANGROVE_SWAMP) {
         return BiomeCategory.SWAMP;
      } else if (holder.m_203334_() == MUSHROOM_FIELDS) {
         return BiomeCategory.MUSHROOM;
      } else if (holder.m_203656_(BiomeTags.f_207612_)) {
         return BiomeCategory.NETHER;
      } else if (holder.m_203656_(BiomeTags.f_215801_)) {
         return BiomeCategory.UNDERGROUND;
      } else {
         return holder.m_203656_(BiomeTags.f_207606_) ? BiomeCategory.MOUNTAIN : BiomeCategory.PLAINS;
      }
   }

   public static float getDownfall(Biome biome) {
      return net.minecraft.world.level.biome.Biomes.getDownfall(biome);
   }

   public static Precipitation getPrecipitation(Biome biome) {
      if (!biome.m_264473_()) {
         return Precipitation.NONE;
      } else {
         return (double)biome.m_47554_() < 0.1 ? Precipitation.SNOW : Precipitation.RAIN;
      }
   }
}
