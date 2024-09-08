package net.optifine.util;

import com.google.common.collect.Lists;
import com.mojang.serialization.Lifecycle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1599_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1655_;
import net.minecraft.src.C_203228_;
import net.minecraft.src.C_206957_;
import net.minecraft.src.C_256686_;
import net.minecraft.src.C_313696_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4700_;
import net.minecraft.src.C_4705_;
import net.minecraft.src.C_5264_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_1629_.C_1631_;
import net.minecraft.src.C_1629_.C_1635_;
import net.minecraft.src.C_1639_.C_1641_;
import net.minecraft.src.C_1645_.C_1647_;
import net.minecraft.src.C_1660_.C_1662_;
import net.minecraft.src.C_203228_.C_203231_;
import net.optifine.config.BiomeId;
import net.optifine.override.ChunkCacheOF;

public class BiomeUtils {
   private static C_4705_<C_1629_> defaultBiomeRegistry = makeDefaultBiomeRegistry();
   private static C_4705_<C_1629_> biomeRegistry = getBiomeRegistry(C_3391_.m_91087_().f_91073_);
   private static C_1596_ biomeWorld = C_3391_.m_91087_().f_91073_;
   public static C_1629_ PLAINS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48202_);
   public static C_1629_ SUNFLOWER_PLAINS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48176_);
   public static C_1629_ SNOWY_PLAINS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_186761_);
   public static C_1629_ ICE_SPIKES = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48182_);
   public static C_1629_ DESERT = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48203_);
   public static C_1629_ WINDSWEPT_HILLS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_186765_);
   public static C_1629_ WINDSWEPT_GRAVELLY_HILLS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_186766_);
   public static C_1629_ MUSHROOM_FIELDS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48215_);
   public static C_1629_ SWAMP = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48207_);
   public static C_1629_ MANGROVE_SWAMP = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_220595_);
   public static C_1629_ THE_VOID = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48173_);

   public static void onWorldChanged(C_1596_ worldIn) {
      biomeRegistry = getBiomeRegistry(worldIn);
      biomeWorld = worldIn;
      PLAINS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48202_);
      SUNFLOWER_PLAINS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48176_);
      SNOWY_PLAINS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_186761_);
      ICE_SPIKES = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48182_);
      DESERT = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48203_);
      WINDSWEPT_HILLS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_186765_);
      WINDSWEPT_GRAVELLY_HILLS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_186766_);
      MUSHROOM_FIELDS = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48215_);
      SWAMP = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48207_);
      MANGROVE_SWAMP = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_220595_);
      THE_VOID = (C_1629_)biomeRegistry.m_6246_(C_1655_.f_48173_);
   }

   private static C_1629_ getBiomeSafe(C_4705_<C_1629_> registry, C_5264_<C_1629_> biomeKey, Supplier<C_1629_> biomeDefault) {
      C_1629_ biome = (C_1629_)registry.m_6246_(biomeKey);
      if (biome == null) {
         biome = (C_1629_)biomeDefault.get();
      }

      return biome;
   }

   public static C_4705_<C_1629_> getBiomeRegistry(C_1596_ worldIn) {
      if (worldIn != null) {
         if (worldIn == biomeWorld) {
            return biomeRegistry;
         } else {
            C_4705_<C_1629_> worldBiomeRegistry = worldIn.m_9598_().m_175515_(C_256686_.f_256952_);
            return fixBiomeIds(defaultBiomeRegistry, worldBiomeRegistry);
         }
      } else {
         return defaultBiomeRegistry;
      }
   }

   private static C_4705_<C_1629_> makeDefaultBiomeRegistry() {
      C_4700_<C_1629_> registry = new C_4700_(C_5264_.m_135788_(new C_5265_("biomes")), Lifecycle.stable(), true);

      for (C_5264_<C_1629_> biomeKey : C_1655_.getBiomeKeys()) {
         C_1631_ bb = new C_1631_();
         bb.m_264558_(false);
         bb.m_47609_(0.0F);
         bb.m_47611_(0.0F);
         bb.m_47603_(new C_1647_().m_48019_(0).m_48034_(0).m_48037_(0).m_48040_(0).m_48018_());
         bb.m_47605_(new C_1662_().m_48381_());
         bb.m_47601_(new C_1641_(null, null).a());
         C_1629_ biome = bb.m_47592_();
         registry.m_203693_(biome);
         C_203231_ var6 = registry.m_255290_(biomeKey, biome, C_313696_.f_316022_);
      }

      return registry;
   }

   private static C_4705_<C_1629_> fixBiomeIds(C_4705_<C_1629_> idRegistry, C_4705_<C_1629_> valueRegistry) {
      C_4700_<C_1629_> registry = new C_4700_(C_5264_.m_135788_(new C_5265_("biomes")), Lifecycle.stable(), true);

      for (C_5264_<C_1629_> biomeKey : C_1655_.getBiomeKeys()) {
         C_1629_ biome = (C_1629_)valueRegistry.m_6246_(biomeKey);
         if (biome == null) {
            biome = (C_1629_)idRegistry.m_6246_(biomeKey);
         }

         int id = idRegistry.m_7447_((C_1629_)idRegistry.m_6246_(biomeKey));
         registry.m_203693_(biome);
         C_203231_ var8 = registry.m_255290_(biomeKey, biome, C_313696_.f_316022_);
      }

      for (C_5264_<C_1629_> biomeKey : valueRegistry.m_214010_()) {
         if (!registry.m_142003_(biomeKey)) {
            C_1629_ biome = (C_1629_)valueRegistry.m_6246_(biomeKey);
            registry.m_203693_(biome);
            C_203231_ var13 = registry.m_255290_(biomeKey, biome, C_313696_.f_316022_);
         }
      }

      return registry;
   }

   public static C_4705_<C_1629_> getBiomeRegistry() {
      return biomeRegistry;
   }

   public static C_5265_ getLocation(C_1629_ biome) {
      return getBiomeRegistry().m_7981_(biome);
   }

   public static int getId(C_1629_ biome) {
      return getBiomeRegistry().m_7447_(biome);
   }

   public static int getId(C_5265_ loc) {
      C_1629_ biome = getBiome(loc);
      return getBiomeRegistry().m_7447_(biome);
   }

   public static BiomeId getBiomeId(C_5265_ loc) {
      return BiomeId.make(loc);
   }

   public static C_1629_ getBiome(C_5265_ loc) {
      return (C_1629_)getBiomeRegistry().m_7745_(loc);
   }

   public static Set<C_5265_> getLocations() {
      return getBiomeRegistry().m_6566_();
   }

   public static List<C_1629_> getBiomes() {
      return Lists.newArrayList(biomeRegistry);
   }

   public static List<BiomeId> getBiomeIds() {
      return getBiomeIds(getLocations());
   }

   public static List<BiomeId> getBiomeIds(Collection<C_5265_> locations) {
      List<BiomeId> biomeIds = new ArrayList();

      for (C_5265_ loc : locations) {
         BiomeId bi = BiomeId.make(loc);
         if (bi != null) {
            biomeIds.add(bi);
         }
      }

      return biomeIds;
   }

   public static C_1629_ getBiome(C_1557_ lightReader, C_4675_ blockPos) {
      C_1629_ biome = PLAINS;
      if (lightReader instanceof ChunkCacheOF) {
         biome = ((ChunkCacheOF)lightReader).getBiome(blockPos);
      } else if (lightReader instanceof C_1599_) {
         biome = (C_1629_)((C_1599_)lightReader).m_204166_(blockPos).m_203334_();
      }

      return biome;
   }

   public static BiomeCategory getBiomeCategory(C_203228_<C_1629_> holder) {
      if (holder.m_203334_() == THE_VOID) {
         return BiomeCategory.NONE;
      } else if (holder.m_203656_(C_206957_.f_207609_)) {
         return BiomeCategory.TAIGA;
      } else if (holder.m_203334_() == WINDSWEPT_HILLS || holder.m_203334_() == WINDSWEPT_GRAVELLY_HILLS) {
         return BiomeCategory.EXTREME_HILLS;
      } else if (holder.m_203656_(C_206957_.f_207610_)) {
         return BiomeCategory.JUNGLE;
      } else if (holder.m_203656_(C_206957_.f_207607_)) {
         return BiomeCategory.MESA;
      } else if (holder.m_203334_() == PLAINS || holder.m_203334_() == PLAINS) {
         return BiomeCategory.PLAINS;
      } else if (holder.m_203656_(C_206957_.f_215816_)) {
         return BiomeCategory.SAVANNA;
      } else if (holder.m_203334_() == SNOWY_PLAINS || holder.m_203334_() == ICE_SPIKES) {
         return BiomeCategory.ICY;
      } else if (holder.m_203656_(C_206957_.f_215818_)) {
         return BiomeCategory.THEEND;
      } else if (holder.m_203656_(C_206957_.f_207604_)) {
         return BiomeCategory.BEACH;
      } else if (holder.m_203656_(C_206957_.f_207611_)) {
         return BiomeCategory.FOREST;
      } else if (holder.m_203656_(C_206957_.f_207603_)) {
         return BiomeCategory.OCEAN;
      } else if (holder.m_203334_() == DESERT) {
         return BiomeCategory.DESERT;
      } else if (holder.m_203656_(C_206957_.f_207605_)) {
         return BiomeCategory.RIVER;
      } else if (holder.m_203334_() == SWAMP || holder.m_203334_() == MANGROVE_SWAMP) {
         return BiomeCategory.SWAMP;
      } else if (holder.m_203334_() == MUSHROOM_FIELDS) {
         return BiomeCategory.MUSHROOM;
      } else if (holder.m_203656_(C_206957_.f_207612_)) {
         return BiomeCategory.NETHER;
      } else if (holder.m_203656_(C_206957_.f_215801_)) {
         return BiomeCategory.UNDERGROUND;
      } else {
         return holder.m_203656_(C_206957_.f_207606_) ? BiomeCategory.MOUNTAIN : BiomeCategory.PLAINS;
      }
   }

   public static float getDownfall(C_1629_ biome) {
      return C_1655_.getDownfall(biome);
   }

   public static C_1635_ getPrecipitation(C_1629_ biome) {
      if (!biome.m_264473_()) {
         return C_1635_.NONE;
      } else {
         return (double)biome.m_47554_() < 0.1 ? C_1635_.SNOW : C_1635_.RAIN;
      }
   }
}
