package net.minecraft.world.level.biome;

import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome.ClimateSettings;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorField;

public abstract class Biomes {
   private static Set<ResourceKey<Biome>> biomeKeys = new LinkedHashSet();
   public static final ResourceKey<Biome> f_48173_ = m_48228_("the_void");
   public static final ResourceKey<Biome> f_48202_ = m_48228_("plains");
   public static final ResourceKey<Biome> f_48176_ = m_48228_("sunflower_plains");
   public static final ResourceKey<Biome> f_186761_ = m_48228_("snowy_plains");
   public static final ResourceKey<Biome> f_48182_ = m_48228_("ice_spikes");
   public static final ResourceKey<Biome> f_48203_ = m_48228_("desert");
   public static final ResourceKey<Biome> f_48207_ = m_48228_("swamp");
   public static final ResourceKey<Biome> f_220595_ = m_48228_("mangrove_swamp");
   public static final ResourceKey<Biome> f_48205_ = m_48228_("forest");
   public static final ResourceKey<Biome> f_48179_ = m_48228_("flower_forest");
   public static final ResourceKey<Biome> f_48149_ = m_48228_("birch_forest");
   public static final ResourceKey<Biome> f_48151_ = m_48228_("dark_forest");
   public static final ResourceKey<Biome> f_186762_ = m_48228_("old_growth_birch_forest");
   public static final ResourceKey<Biome> f_186763_ = m_48228_("old_growth_pine_taiga");
   public static final ResourceKey<Biome> f_186764_ = m_48228_("old_growth_spruce_taiga");
   public static final ResourceKey<Biome> f_48206_ = m_48228_("taiga");
   public static final ResourceKey<Biome> f_48152_ = m_48228_("snowy_taiga");
   public static final ResourceKey<Biome> f_48157_ = m_48228_("savanna");
   public static final ResourceKey<Biome> f_48158_ = m_48228_("savanna_plateau");
   public static final ResourceKey<Biome> f_186765_ = m_48228_("windswept_hills");
   public static final ResourceKey<Biome> f_186766_ = m_48228_("windswept_gravelly_hills");
   public static final ResourceKey<Biome> f_186767_ = m_48228_("windswept_forest");
   public static final ResourceKey<Biome> f_186768_ = m_48228_("windswept_savanna");
   public static final ResourceKey<Biome> f_48222_ = m_48228_("jungle");
   public static final ResourceKey<Biome> f_186769_ = m_48228_("sparse_jungle");
   public static final ResourceKey<Biome> f_48197_ = m_48228_("bamboo_jungle");
   public static final ResourceKey<Biome> f_48159_ = m_48228_("badlands");
   public static final ResourceKey<Biome> f_48194_ = m_48228_("eroded_badlands");
   public static final ResourceKey<Biome> f_186753_ = m_48228_("wooded_badlands");
   public static final ResourceKey<Biome> f_186754_ = m_48228_("meadow");
   public static final ResourceKey<Biome> f_271432_ = m_48228_("cherry_grove");
   public static final ResourceKey<Biome> f_186755_ = m_48228_("grove");
   public static final ResourceKey<Biome> f_186756_ = m_48228_("snowy_slopes");
   public static final ResourceKey<Biome> f_186757_ = m_48228_("frozen_peaks");
   public static final ResourceKey<Biome> f_186758_ = m_48228_("jagged_peaks");
   public static final ResourceKey<Biome> f_186759_ = m_48228_("stony_peaks");
   public static final ResourceKey<Biome> f_48208_ = m_48228_("river");
   public static final ResourceKey<Biome> f_48212_ = m_48228_("frozen_river");
   public static final ResourceKey<Biome> f_48217_ = m_48228_("beach");
   public static final ResourceKey<Biome> f_48148_ = m_48228_("snowy_beach");
   public static final ResourceKey<Biome> f_186760_ = m_48228_("stony_shore");
   public static final ResourceKey<Biome> f_48166_ = m_48228_("warm_ocean");
   public static final ResourceKey<Biome> f_48167_ = m_48228_("lukewarm_ocean");
   public static final ResourceKey<Biome> f_48170_ = m_48228_("deep_lukewarm_ocean");
   public static final ResourceKey<Biome> f_48174_ = m_48228_("ocean");
   public static final ResourceKey<Biome> f_48225_ = m_48228_("deep_ocean");
   public static final ResourceKey<Biome> f_48168_ = m_48228_("cold_ocean");
   public static final ResourceKey<Biome> f_48171_ = m_48228_("deep_cold_ocean");
   public static final ResourceKey<Biome> f_48211_ = m_48228_("frozen_ocean");
   public static final ResourceKey<Biome> f_48172_ = m_48228_("deep_frozen_ocean");
   public static final ResourceKey<Biome> f_48215_ = m_48228_("mushroom_fields");
   public static final ResourceKey<Biome> f_151784_ = m_48228_("dripstone_caves");
   public static final ResourceKey<Biome> f_151785_ = m_48228_("lush_caves");
   public static final ResourceKey<Biome> f_220594_ = m_48228_("deep_dark");
   public static final ResourceKey<Biome> f_48209_ = m_48228_("nether_wastes");
   public static final ResourceKey<Biome> f_48201_ = m_48228_("warped_forest");
   public static final ResourceKey<Biome> f_48200_ = m_48228_("crimson_forest");
   public static final ResourceKey<Biome> f_48199_ = m_48228_("soul_sand_valley");
   public static final ResourceKey<Biome> f_48175_ = m_48228_("basalt_deltas");
   public static final ResourceKey<Biome> f_48210_ = m_48228_("the_end");
   public static final ResourceKey<Biome> f_48164_ = m_48228_("end_highlands");
   public static final ResourceKey<Biome> f_48163_ = m_48228_("end_midlands");
   public static final ResourceKey<Biome> f_48162_ = m_48228_("small_end_islands");
   public static final ResourceKey<Biome> f_48165_ = m_48228_("end_barrens");
   public static ReflectorClass Biome = new ReflectorClass(Biome.class);
   public static ReflectorField Biome_climateSettings = Biome.makeField(ClimateSettings.class);

   private static ResourceKey<Biome> m_48228_(String nameIn) {
      ResourceKey<Biome> key = ResourceKey.m_135785_(Registries.f_256952_, net.minecraft.resources.ResourceLocation.m_340282_(nameIn));
      biomeKeys.add(key);
      return key;
   }

   public static Set<ResourceKey<Biome>> getBiomeKeys() {
      return new LinkedHashSet(biomeKeys);
   }

   public static float getDownfall(Biome biome) {
      ClimateSettings bcs = (ClimateSettings)Biome_climateSettings.getValue(biome);
      return bcs == null ? 0.0F : bcs.f_47683_();
   }
}
