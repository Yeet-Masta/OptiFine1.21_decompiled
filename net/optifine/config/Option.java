package net.optifine.config;

import net.minecraft.client.OptionInstance;

public class Option {
   public static OptionValueInt DEFAULT = new OptionValueInt(0, "generator.minecraft.normal");
   public static OptionValueInt FAST = new OptionValueInt(1, "options.graphics.fast");
   public static OptionValueInt FANCY = new OptionValueInt(2, "options.graphics.fancy");
   public static OptionValueInt OFF = new OptionValueInt(3, "options.off");
   public static OptionValueInt SMART = new OptionValueInt(4, "of.general.smart");
   public static OptionValueInt COMPACT = new OptionValueInt(5, "of.general.compact");
   public static OptionValueInt FULL = new OptionValueInt(6, "of.general.full");
   public static OptionValueInt DETAILED = new OptionValueInt(7, "of.general.detailed");
   public static OptionValueInt[] OFF_COMPACT_FULL = new OptionValueInt[]{OFF, COMPACT, FULL};
   public static OptionValueInt[] COMPACT_FULL_DETAILED = new OptionValueInt[]{COMPACT, FULL, DETAILED};
   public static OptionValueInt ANIM_ON = new OptionValueInt(0, "options.on");
   public static OptionValueInt ANIM_GENERATED = new OptionValueInt(1, "of.options.animation.dynamic");
   public static OptionValueInt ANIM_OFF = new OptionValueInt(2, "options.off");
   public static OptionInstance FOG_FANCY = new IteratableOptionOF("of.options.FOG_FANCY");
   public static OptionInstance FOG_START = new IteratableOptionOF("of.options.FOG_START");
   public static OptionInstance MIPMAP_TYPE = new SliderPercentageOptionOF("of.options.MIPMAP_TYPE", 0, 3, 1, 3);
   public static OptionInstance SMOOTH_FPS = new IteratableOptionOF("of.options.SMOOTH_FPS");
   public static OptionInstance CLOUDS = new IteratableOptionOF("of.options.CLOUDS");
   public static OptionInstance CLOUD_HEIGHT = new SliderPercentageOptionOF("of.options.CLOUD_HEIGHT", 0.0);
   public static OptionInstance TREES = new IteratableOptionOF("of.options.TREES");
   public static OptionInstance RAIN = new IteratableOptionOF("of.options.RAIN");
   public static OptionInstance ANIMATED_WATER = new IteratableOptionOF("of.options.ANIMATED_WATER");
   public static OptionInstance ANIMATED_LAVA = new IteratableOptionOF("of.options.ANIMATED_LAVA");
   public static OptionInstance ANIMATED_FIRE = new IteratableOptionOF("of.options.ANIMATED_FIRE");
   public static OptionInstance ANIMATED_PORTAL = new IteratableOptionOF("of.options.ANIMATED_PORTAL");
   public static OptionInstance AO_LEVEL = new SliderPercentageOptionOF("of.options.AO_LEVEL", 1.0);
   public static OptionInstance LAGOMETER = new IteratableOptionOF("of.options.LAGOMETER");
   public static OptionInstance AUTOSAVE_TICKS = new IteratableOptionOF("of.options.AUTOSAVE_TICKS");
   public static OptionInstance BETTER_GRASS = new IteratableOptionOF("of.options.BETTER_GRASS");
   public static OptionInstance ANIMATED_REDSTONE = new IteratableOptionOF("of.options.ANIMATED_REDSTONE");
   public static OptionInstance ANIMATED_EXPLOSION = new IteratableOptionOF("of.options.ANIMATED_EXPLOSION");
   public static OptionInstance ANIMATED_FLAME = new IteratableOptionOF("of.options.ANIMATED_FLAME");
   public static OptionInstance ANIMATED_SMOKE = new IteratableOptionOF("of.options.ANIMATED_SMOKE");
   public static OptionInstance WEATHER = new IteratableOptionOF("of.options.WEATHER");
   public static OptionInstance SKY = new IteratableOptionOF("of.options.SKY");
   public static OptionInstance STARS = new IteratableOptionOF("of.options.STARS");
   public static OptionInstance SUN_MOON = new IteratableOptionOF("of.options.SUN_MOON");
   public static OptionInstance VIGNETTE = new IteratableOptionOF("of.options.VIGNETTE");
   public static OptionInstance CHUNK_UPDATES = new IteratableOptionOF("of.options.CHUNK_UPDATES");
   public static OptionInstance CHUNK_UPDATES_DYNAMIC = new IteratableOptionOF("of.options.CHUNK_UPDATES_DYNAMIC");
   public static OptionInstance TIME = new IteratableOptionOF("of.options.TIME");
   public static OptionInstance SMOOTH_WORLD = new IteratableOptionOF("of.options.SMOOTH_WORLD");
   public static OptionInstance VOID_PARTICLES = new IteratableOptionOF("of.options.VOID_PARTICLES");
   public static OptionInstance WATER_PARTICLES = new IteratableOptionOF("of.options.WATER_PARTICLES");
   public static OptionInstance RAIN_SPLASH = new IteratableOptionOF("of.options.RAIN_SPLASH");
   public static OptionInstance PORTAL_PARTICLES = new IteratableOptionOF("of.options.PORTAL_PARTICLES");
   public static OptionInstance POTION_PARTICLES = new IteratableOptionOF("of.options.POTION_PARTICLES");
   public static OptionInstance FIREWORK_PARTICLES = new IteratableOptionOF("of.options.FIREWORK_PARTICLES");
   public static OptionInstance PROFILER = new IteratableOptionOF("of.options.PROFILER");
   public static OptionInstance DRIPPING_WATER_LAVA = new IteratableOptionOF("of.options.DRIPPING_WATER_LAVA");
   public static OptionInstance BETTER_SNOW = new IteratableOptionOF("of.options.BETTER_SNOW");
   public static OptionInstance ANIMATED_TERRAIN = new IteratableOptionOF("of.options.ANIMATED_TERRAIN");
   public static OptionInstance SWAMP_COLORS = new IteratableOptionOF("of.options.SWAMP_COLORS");
   public static OptionInstance RANDOM_ENTITIES = new IteratableOptionOF("of.options.RANDOM_ENTITIES");
   public static OptionInstance SMOOTH_BIOMES = new IteratableOptionOF("of.options.SMOOTH_BIOMES");
   public static OptionInstance CUSTOM_FONTS = new IteratableOptionOF("of.options.CUSTOM_FONTS");
   public static OptionInstance CUSTOM_COLORS = new IteratableOptionOF("of.options.CUSTOM_COLORS");
   public static OptionInstance SHOW_CAPES = new IteratableOptionOF("of.options.SHOW_CAPES");
   public static OptionInstance CONNECTED_TEXTURES = new IteratableOptionOF("of.options.CONNECTED_TEXTURES");
   public static OptionInstance CUSTOM_ITEMS = new IteratableOptionOF("of.options.CUSTOM_ITEMS");
   public static OptionInstance AA_LEVEL = new SliderPercentageOptionOF("of.options.AA_LEVEL", 0, 16, new int[]{0, 2, 4, 6, 8, 12, 16}, 0);
   public static OptionInstance AF_LEVEL = new SliderPercentageOptionOF("of.options.AF_LEVEL", 1, 16, new int[]{1, 2, 4, 8, 16}, 1);
   public static OptionInstance ANIMATED_TEXTURES = new IteratableOptionOF("of.options.ANIMATED_TEXTURES");
   public static OptionInstance NATURAL_TEXTURES = new IteratableOptionOF("of.options.NATURAL_TEXTURES");
   public static OptionInstance EMISSIVE_TEXTURES = new IteratableOptionOF("of.options.EMISSIVE_TEXTURES");
   public static OptionInstance HELD_ITEM_TOOLTIPS = new IteratableOptionOF("of.options.HELD_ITEM_TOOLTIPS");
   public static OptionInstance LAZY_CHUNK_LOADING = new IteratableOptionOF("of.options.LAZY_CHUNK_LOADING");
   public static OptionInstance CUSTOM_SKY = new IteratableOptionOF("of.options.CUSTOM_SKY");
   public static OptionInstance FAST_MATH = new IteratableOptionOF("of.options.FAST_MATH");
   public static OptionInstance FAST_RENDER = new IteratableOptionOF("of.options.FAST_RENDER");
   public static OptionInstance DYNAMIC_FOV = new IteratableOptionOF("of.options.DYNAMIC_FOV");
   public static OptionInstance DYNAMIC_LIGHTS = new IteratableOptionOF("of.options.DYNAMIC_LIGHTS");
   public static OptionInstance ALTERNATE_BLOCKS = new IteratableOptionOF("of.options.ALTERNATE_BLOCKS");
   public static OptionInstance CUSTOM_ENTITY_MODELS = new IteratableOptionOF("of.options.CUSTOM_ENTITY_MODELS");
   public static OptionInstance ADVANCED_TOOLTIPS = new IteratableOptionOF("of.options.ADVANCED_TOOLTIPS");
   public static OptionInstance SCREENSHOT_SIZE = new IteratableOptionOF("of.options.SCREENSHOT_SIZE");
   public static OptionInstance CUSTOM_GUIS = new IteratableOptionOF("of.options.CUSTOM_GUIS");
   public static OptionInstance RENDER_REGIONS = new IteratableOptionOF("of.options.RENDER_REGIONS");
   public static OptionInstance SHOW_GL_ERRORS = new IteratableOptionOF("of.options.SHOW_GL_ERRORS");
   public static OptionInstance SMART_ANIMATIONS = new IteratableOptionOF("of.options.SMART_ANIMATIONS");
   public static OptionInstance CHAT_BACKGROUND = new IteratableOptionOF("of.options.CHAT_BACKGROUND");
   public static OptionInstance CHAT_SHADOW = new IteratableOptionOF("of.options.CHAT_SHADOW");
   public static OptionInstance TELEMETRY = new IteratableOptionOF("of.options.TELEMETRY");
   public static OptionInstance QUICK_INFO = new IterableOptionBool(
      "of.options.QUICK_INFO", opts -> opts.ofQuickInfo, (opts, val) -> opts.ofQuickInfo = val, "ofQuickInfo"
   );
   public static OptionInstance QUICK_INFO_FPS = new IterableOptionInt(
      "of.options.QUICK_INFO_FPS", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoFps, (opts, val) -> opts.ofQuickInfoFps = val, "ofQuickInfoFps"
   );
   public static OptionInstance QUICK_INFO_CHUNKS = new IterableOptionBool(
      "of.options.QUICK_INFO_CHUNKS", opts -> opts.ofQuickInfoChunks, (opts, val) -> opts.ofQuickInfoChunks = val, "ofQuickInfoChunks"
   );
   public static OptionInstance QUICK_INFO_ENTITIES = new IterableOptionBool(
      "of.options.QUICK_INFO_ENTITIES", opts -> opts.ofQuickInfoEntities, (opts, val) -> opts.ofQuickInfoEntities = val, "ofQuickInfoEntities"
   );
   public static OptionInstance QUICK_INFO_PARTICLES = new IterableOptionBool(
      "of.options.QUICK_INFO_PARTICLES", opts -> opts.ofQuickInfoParticles, (opts, val) -> opts.ofQuickInfoParticles = val, "ofQuickInfoParticles"
   );
   public static OptionInstance QUICK_INFO_UPDATES = new IterableOptionBool(
      "of.options.QUICK_INFO_UPDATES", opts -> opts.ofQuickInfoUpdates, (opts, val) -> opts.ofQuickInfoUpdates = val, "ofQuickInfoUpdates"
   );
   public static OptionInstance QUICK_INFO_GPU = new IterableOptionBool(
      "of.options.QUICK_INFO_GPU", opts -> opts.ofQuickInfoGpu, (opts, val) -> opts.ofQuickInfoGpu = val, "ofQuickInfoGpu"
   );
   public static OptionInstance QUICK_INFO_POS = new IterableOptionInt(
      "of.options.QUICK_INFO_POS", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoPos, (opts, val) -> opts.ofQuickInfoPos = val, "ofQuickInfoPos"
   );
   public static OptionInstance QUICK_INFO_FACING = new IterableOptionInt(
      "of.options.QUICK_INFO_FACING", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoFacing, (opts, val) -> opts.ofQuickInfoFacing = val, "ofQuickInfoFacing"
   );
   public static OptionInstance QUICK_INFO_BIOME = new IterableOptionBool(
      "of.options.QUICK_INFO_BIOME", opts -> opts.ofQuickInfoBiome, (opts, val) -> opts.ofQuickInfoBiome = val, "ofQuickInfoBiome"
   );
   public static OptionInstance QUICK_INFO_LIGHT = new IterableOptionBool(
      "of.options.QUICK_INFO_LIGHT", opts -> opts.ofQuickInfoLight, (opts, val) -> opts.ofQuickInfoLight = val, "ofQuickInfoLight"
   );
   public static OptionInstance QUICK_INFO_MEMORY = new IterableOptionInt(
      "of.options.QUICK_INFO_MEMORY", OFF_COMPACT_FULL, opts -> opts.ofQuickInfoMemory, (opts, val) -> opts.ofQuickInfoMemory = val, "ofQuickInfoMemory"
   );
   public static OptionInstance QUICK_INFO_NATIVE_MEMORY = new IterableOptionInt(
      "of.options.QUICK_INFO_NATIVE_MEMORY",
      OFF_COMPACT_FULL,
      opts -> opts.ofQuickInfoNativeMemory,
      (opts, val) -> opts.ofQuickInfoNativeMemory = val,
      "ofQuickInfoNativeMemory"
   );
   public static OptionInstance QUICK_INFO_TARGET_BLOCK = new IterableOptionInt(
      "of.options.QUICK_INFO_TARGET_BLOCK",
      OFF_COMPACT_FULL,
      opts -> opts.ofQuickInfoTargetBlock,
      (opts, val) -> opts.ofQuickInfoTargetBlock = val,
      "ofQuickInfoTargetBlock"
   );
   public static OptionInstance QUICK_INFO_TARGET_FLUID = new IterableOptionInt(
      "of.options.QUICK_INFO_TARGET_FLUID",
      OFF_COMPACT_FULL,
      opts -> opts.ofQuickInfoTargetFluid,
      (opts, val) -> opts.ofQuickInfoTargetFluid = val,
      "ofQuickInfoTargetFluid"
   );
   public static OptionInstance QUICK_INFO_TARGET_ENTITY = new IterableOptionInt(
      "of.options.QUICK_INFO_TARGET_ENTITY",
      OFF_COMPACT_FULL,
      opts -> opts.ofQuickInfoTargetEntity,
      (opts, val) -> opts.ofQuickInfoTargetEntity = val,
      "ofQuickInfoTargetEntity"
   );
   public static OptionInstance QUICK_INFO_LABELS = new IterableOptionInt(
      "of.options.QUICK_INFO_LABELS", COMPACT_FULL_DETAILED, opts -> opts.ofQuickInfoLabels, (opts, val) -> opts.ofQuickInfoLabels = val, "ofQuickInfoLabels"
   );
   public static OptionInstance QUICK_INFO_BACKGROUND = new IterableOptionBool(
      "of.options.QUICK_INFO_BACKGROUND", opts -> opts.ofQuickInfoBackground, (opts, val) -> opts.ofQuickInfoBackground = val, "ofQuickInfoBackground"
   );

   public static boolean isDefault(int value) {
      return value == DEFAULT.getValue();
   }

   public static boolean isFast(int value) {
      return value == FAST.getValue();
   }

   public static boolean isFancy(int value) {
      return value == FANCY.getValue();
   }

   public static boolean isOff(int value) {
      return value == OFF.getValue();
   }

   public static boolean isSmart(int value) {
      return value == SMART.getValue();
   }

   public static boolean isCompact(int value) {
      return value == COMPACT.getValue();
   }

   public static boolean isFull(int value) {
      return value == FULL.getValue();
   }

   public static boolean isDetailed(int value) {
      return value == DETAILED.getValue();
   }
}
