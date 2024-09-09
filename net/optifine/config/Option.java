package net.optifine.config;

import net.minecraft.client.OptionInstance;

public class Option {
   public static final OptionValueInt DEFAULT = new OptionValueInt(0, "generator.minecraft.normal");
   public static final OptionValueInt FAST = new OptionValueInt(1, "options.graphics.fast");
   public static final OptionValueInt FANCY = new OptionValueInt(2, "options.graphics.fancy");
   public static final OptionValueInt OFF = new OptionValueInt(3, "options.off");
   public static final OptionValueInt SMART = new OptionValueInt(4, "of.general.smart");
   public static final OptionValueInt COMPACT = new OptionValueInt(5, "of.general.compact");
   public static final OptionValueInt FULL = new OptionValueInt(6, "of.general.full");
   public static final OptionValueInt DETAILED = new OptionValueInt(7, "of.general.detailed");
   public static final OptionValueInt[] OFF_COMPACT_FULL;
   public static final OptionValueInt[] COMPACT_FULL_DETAILED;
   public static final OptionValueInt ANIM_ON;
   public static final OptionValueInt ANIM_GENERATED;
   public static final OptionValueInt ANIM_OFF;
   public static final OptionInstance FOG_FANCY;
   public static final OptionInstance FOG_START;
   public static final OptionInstance MIPMAP_TYPE;
   public static final OptionInstance SMOOTH_FPS;
   public static final OptionInstance CLOUDS;
   public static final OptionInstance CLOUD_HEIGHT;
   public static final OptionInstance TREES;
   public static final OptionInstance RAIN;
   public static final OptionInstance ANIMATED_WATER;
   public static final OptionInstance ANIMATED_LAVA;
   public static final OptionInstance ANIMATED_FIRE;
   public static final OptionInstance ANIMATED_PORTAL;
   public static final OptionInstance AO_LEVEL;
   public static final OptionInstance LAGOMETER;
   public static final OptionInstance AUTOSAVE_TICKS;
   public static final OptionInstance BETTER_GRASS;
   public static final OptionInstance ANIMATED_REDSTONE;
   public static final OptionInstance ANIMATED_EXPLOSION;
   public static final OptionInstance ANIMATED_FLAME;
   public static final OptionInstance ANIMATED_SMOKE;
   public static final OptionInstance WEATHER;
   public static final OptionInstance SKY;
   public static final OptionInstance STARS;
   public static final OptionInstance SUN_MOON;
   public static final OptionInstance VIGNETTE;
   public static final OptionInstance CHUNK_UPDATES;
   public static final OptionInstance CHUNK_UPDATES_DYNAMIC;
   public static final OptionInstance TIME;
   public static final OptionInstance SMOOTH_WORLD;
   public static final OptionInstance VOID_PARTICLES;
   public static final OptionInstance WATER_PARTICLES;
   public static final OptionInstance RAIN_SPLASH;
   public static final OptionInstance PORTAL_PARTICLES;
   public static final OptionInstance POTION_PARTICLES;
   public static final OptionInstance FIREWORK_PARTICLES;
   public static final OptionInstance PROFILER;
   public static final OptionInstance DRIPPING_WATER_LAVA;
   public static final OptionInstance BETTER_SNOW;
   public static final OptionInstance ANIMATED_TERRAIN;
   public static final OptionInstance SWAMP_COLORS;
   public static final OptionInstance RANDOM_ENTITIES;
   public static final OptionInstance SMOOTH_BIOMES;
   public static final OptionInstance CUSTOM_FONTS;
   public static final OptionInstance CUSTOM_COLORS;
   public static final OptionInstance SHOW_CAPES;
   public static final OptionInstance CONNECTED_TEXTURES;
   public static final OptionInstance CUSTOM_ITEMS;
   public static final OptionInstance AA_LEVEL;
   public static final OptionInstance AF_LEVEL;
   public static final OptionInstance ANIMATED_TEXTURES;
   public static final OptionInstance NATURAL_TEXTURES;
   public static final OptionInstance EMISSIVE_TEXTURES;
   public static final OptionInstance HELD_ITEM_TOOLTIPS;
   public static final OptionInstance LAZY_CHUNK_LOADING;
   public static final OptionInstance CUSTOM_SKY;
   public static final OptionInstance FAST_MATH;
   public static final OptionInstance FAST_RENDER;
   public static final OptionInstance DYNAMIC_FOV;
   public static final OptionInstance DYNAMIC_LIGHTS;
   public static final OptionInstance ALTERNATE_BLOCKS;
   public static final OptionInstance CUSTOM_ENTITY_MODELS;
   public static final OptionInstance ADVANCED_TOOLTIPS;
   public static final OptionInstance SCREENSHOT_SIZE;
   public static final OptionInstance CUSTOM_GUIS;
   public static final OptionInstance RENDER_REGIONS;
   public static final OptionInstance SHOW_GL_ERRORS;
   public static final OptionInstance SMART_ANIMATIONS;
   public static final OptionInstance CHAT_BACKGROUND;
   public static final OptionInstance CHAT_SHADOW;
   public static final OptionInstance TELEMETRY;
   public static final OptionInstance QUICK_INFO;
   public static final OptionInstance QUICK_INFO_FPS;
   public static final OptionInstance QUICK_INFO_CHUNKS;
   public static final OptionInstance QUICK_INFO_ENTITIES;
   public static final OptionInstance QUICK_INFO_PARTICLES;
   public static final OptionInstance QUICK_INFO_UPDATES;
   public static final OptionInstance QUICK_INFO_GPU;
   public static final OptionInstance QUICK_INFO_POS;
   public static final OptionInstance QUICK_INFO_FACING;
   public static final OptionInstance QUICK_INFO_BIOME;
   public static final OptionInstance QUICK_INFO_LIGHT;
   public static final OptionInstance QUICK_INFO_MEMORY;
   public static final OptionInstance QUICK_INFO_NATIVE_MEMORY;
   public static final OptionInstance QUICK_INFO_TARGET_BLOCK;
   public static final OptionInstance QUICK_INFO_TARGET_FLUID;
   public static final OptionInstance QUICK_INFO_TARGET_ENTITY;
   public static final OptionInstance QUICK_INFO_LABELS;
   public static final OptionInstance QUICK_INFO_BACKGROUND;

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

   static {
      OFF_COMPACT_FULL = new OptionValueInt[]{OFF, COMPACT, FULL};
      COMPACT_FULL_DETAILED = new OptionValueInt[]{COMPACT, FULL, DETAILED};
      ANIM_ON = new OptionValueInt(0, "options.on");
      ANIM_GENERATED = new OptionValueInt(1, "of.options.animation.dynamic");
      ANIM_OFF = new OptionValueInt(2, "options.off");
      FOG_FANCY = new IteratableOptionOF("of.options.FOG_FANCY");
      FOG_START = new IteratableOptionOF("of.options.FOG_START");
      MIPMAP_TYPE = new SliderPercentageOptionOF("of.options.MIPMAP_TYPE", 0, 3, 1, 3);
      SMOOTH_FPS = new IteratableOptionOF("of.options.SMOOTH_FPS");
      CLOUDS = new IteratableOptionOF("of.options.CLOUDS");
      CLOUD_HEIGHT = new SliderPercentageOptionOF("of.options.CLOUD_HEIGHT", 0.0);
      TREES = new IteratableOptionOF("of.options.TREES");
      RAIN = new IteratableOptionOF("of.options.RAIN");
      ANIMATED_WATER = new IteratableOptionOF("of.options.ANIMATED_WATER");
      ANIMATED_LAVA = new IteratableOptionOF("of.options.ANIMATED_LAVA");
      ANIMATED_FIRE = new IteratableOptionOF("of.options.ANIMATED_FIRE");
      ANIMATED_PORTAL = new IteratableOptionOF("of.options.ANIMATED_PORTAL");
      AO_LEVEL = new SliderPercentageOptionOF("of.options.AO_LEVEL", 1.0);
      LAGOMETER = new IteratableOptionOF("of.options.LAGOMETER");
      AUTOSAVE_TICKS = new IteratableOptionOF("of.options.AUTOSAVE_TICKS");
      BETTER_GRASS = new IteratableOptionOF("of.options.BETTER_GRASS");
      ANIMATED_REDSTONE = new IteratableOptionOF("of.options.ANIMATED_REDSTONE");
      ANIMATED_EXPLOSION = new IteratableOptionOF("of.options.ANIMATED_EXPLOSION");
      ANIMATED_FLAME = new IteratableOptionOF("of.options.ANIMATED_FLAME");
      ANIMATED_SMOKE = new IteratableOptionOF("of.options.ANIMATED_SMOKE");
      WEATHER = new IteratableOptionOF("of.options.WEATHER");
      SKY = new IteratableOptionOF("of.options.SKY");
      STARS = new IteratableOptionOF("of.options.STARS");
      SUN_MOON = new IteratableOptionOF("of.options.SUN_MOON");
      VIGNETTE = new IteratableOptionOF("of.options.VIGNETTE");
      CHUNK_UPDATES = new IteratableOptionOF("of.options.CHUNK_UPDATES");
      CHUNK_UPDATES_DYNAMIC = new IteratableOptionOF("of.options.CHUNK_UPDATES_DYNAMIC");
      TIME = new IteratableOptionOF("of.options.TIME");
      SMOOTH_WORLD = new IteratableOptionOF("of.options.SMOOTH_WORLD");
      VOID_PARTICLES = new IteratableOptionOF("of.options.VOID_PARTICLES");
      WATER_PARTICLES = new IteratableOptionOF("of.options.WATER_PARTICLES");
      RAIN_SPLASH = new IteratableOptionOF("of.options.RAIN_SPLASH");
      PORTAL_PARTICLES = new IteratableOptionOF("of.options.PORTAL_PARTICLES");
      POTION_PARTICLES = new IteratableOptionOF("of.options.POTION_PARTICLES");
      FIREWORK_PARTICLES = new IteratableOptionOF("of.options.FIREWORK_PARTICLES");
      PROFILER = new IteratableOptionOF("of.options.PROFILER");
      DRIPPING_WATER_LAVA = new IteratableOptionOF("of.options.DRIPPING_WATER_LAVA");
      BETTER_SNOW = new IteratableOptionOF("of.options.BETTER_SNOW");
      ANIMATED_TERRAIN = new IteratableOptionOF("of.options.ANIMATED_TERRAIN");
      SWAMP_COLORS = new IteratableOptionOF("of.options.SWAMP_COLORS");
      RANDOM_ENTITIES = new IteratableOptionOF("of.options.RANDOM_ENTITIES");
      SMOOTH_BIOMES = new IteratableOptionOF("of.options.SMOOTH_BIOMES");
      CUSTOM_FONTS = new IteratableOptionOF("of.options.CUSTOM_FONTS");
      CUSTOM_COLORS = new IteratableOptionOF("of.options.CUSTOM_COLORS");
      SHOW_CAPES = new IteratableOptionOF("of.options.SHOW_CAPES");
      CONNECTED_TEXTURES = new IteratableOptionOF("of.options.CONNECTED_TEXTURES");
      CUSTOM_ITEMS = new IteratableOptionOF("of.options.CUSTOM_ITEMS");
      AA_LEVEL = new SliderPercentageOptionOF("of.options.AA_LEVEL", 0, 16, new int[]{0, 2, 4, 6, 8, 12, 16}, 0);
      AF_LEVEL = new SliderPercentageOptionOF("of.options.AF_LEVEL", 1, 16, new int[]{1, 2, 4, 8, 16}, 1);
      ANIMATED_TEXTURES = new IteratableOptionOF("of.options.ANIMATED_TEXTURES");
      NATURAL_TEXTURES = new IteratableOptionOF("of.options.NATURAL_TEXTURES");
      EMISSIVE_TEXTURES = new IteratableOptionOF("of.options.EMISSIVE_TEXTURES");
      HELD_ITEM_TOOLTIPS = new IteratableOptionOF("of.options.HELD_ITEM_TOOLTIPS");
      LAZY_CHUNK_LOADING = new IteratableOptionOF("of.options.LAZY_CHUNK_LOADING");
      CUSTOM_SKY = new IteratableOptionOF("of.options.CUSTOM_SKY");
      FAST_MATH = new IteratableOptionOF("of.options.FAST_MATH");
      FAST_RENDER = new IteratableOptionOF("of.options.FAST_RENDER");
      DYNAMIC_FOV = new IteratableOptionOF("of.options.DYNAMIC_FOV");
      DYNAMIC_LIGHTS = new IteratableOptionOF("of.options.DYNAMIC_LIGHTS");
      ALTERNATE_BLOCKS = new IteratableOptionOF("of.options.ALTERNATE_BLOCKS");
      CUSTOM_ENTITY_MODELS = new IteratableOptionOF("of.options.CUSTOM_ENTITY_MODELS");
      ADVANCED_TOOLTIPS = new IteratableOptionOF("of.options.ADVANCED_TOOLTIPS");
      SCREENSHOT_SIZE = new IteratableOptionOF("of.options.SCREENSHOT_SIZE");
      CUSTOM_GUIS = new IteratableOptionOF("of.options.CUSTOM_GUIS");
      RENDER_REGIONS = new IteratableOptionOF("of.options.RENDER_REGIONS");
      SHOW_GL_ERRORS = new IteratableOptionOF("of.options.SHOW_GL_ERRORS");
      SMART_ANIMATIONS = new IteratableOptionOF("of.options.SMART_ANIMATIONS");
      CHAT_BACKGROUND = new IteratableOptionOF("of.options.CHAT_BACKGROUND");
      CHAT_SHADOW = new IteratableOptionOF("of.options.CHAT_SHADOW");
      TELEMETRY = new IteratableOptionOF("of.options.TELEMETRY");
      QUICK_INFO = new IterableOptionBool("of.options.QUICK_INFO", (opts) -> {
         return opts.ofQuickInfo;
      }, (opts, val) -> {
         opts.ofQuickInfo = val;
      }, "ofQuickInfo");
      QUICK_INFO_FPS = new IterableOptionInt("of.options.QUICK_INFO_FPS", OFF_COMPACT_FULL, (opts) -> {
         return opts.ofQuickInfoFps;
      }, (opts, val) -> {
         opts.ofQuickInfoFps = val;
      }, "ofQuickInfoFps");
      QUICK_INFO_CHUNKS = new IterableOptionBool("of.options.QUICK_INFO_CHUNKS", (opts) -> {
         return opts.ofQuickInfoChunks;
      }, (opts, val) -> {
         opts.ofQuickInfoChunks = val;
      }, "ofQuickInfoChunks");
      QUICK_INFO_ENTITIES = new IterableOptionBool("of.options.QUICK_INFO_ENTITIES", (opts) -> {
         return opts.ofQuickInfoEntities;
      }, (opts, val) -> {
         opts.ofQuickInfoEntities = val;
      }, "ofQuickInfoEntities");
      QUICK_INFO_PARTICLES = new IterableOptionBool("of.options.QUICK_INFO_PARTICLES", (opts) -> {
         return opts.ofQuickInfoParticles;
      }, (opts, val) -> {
         opts.ofQuickInfoParticles = val;
      }, "ofQuickInfoParticles");
      QUICK_INFO_UPDATES = new IterableOptionBool("of.options.QUICK_INFO_UPDATES", (opts) -> {
         return opts.ofQuickInfoUpdates;
      }, (opts, val) -> {
         opts.ofQuickInfoUpdates = val;
      }, "ofQuickInfoUpdates");
      QUICK_INFO_GPU = new IterableOptionBool("of.options.QUICK_INFO_GPU", (opts) -> {
         return opts.ofQuickInfoGpu;
      }, (opts, val) -> {
         opts.ofQuickInfoGpu = val;
      }, "ofQuickInfoGpu");
      QUICK_INFO_POS = new IterableOptionInt("of.options.QUICK_INFO_POS", OFF_COMPACT_FULL, (opts) -> {
         return opts.ofQuickInfoPos;
      }, (opts, val) -> {
         opts.ofQuickInfoPos = val;
      }, "ofQuickInfoPos");
      QUICK_INFO_FACING = new IterableOptionInt("of.options.QUICK_INFO_FACING", OFF_COMPACT_FULL, (opts) -> {
         return opts.ofQuickInfoFacing;
      }, (opts, val) -> {
         opts.ofQuickInfoFacing = val;
      }, "ofQuickInfoFacing");
      QUICK_INFO_BIOME = new IterableOptionBool("of.options.QUICK_INFO_BIOME", (opts) -> {
         return opts.ofQuickInfoBiome;
      }, (opts, val) -> {
         opts.ofQuickInfoBiome = val;
      }, "ofQuickInfoBiome");
      QUICK_INFO_LIGHT = new IterableOptionBool("of.options.QUICK_INFO_LIGHT", (opts) -> {
         return opts.ofQuickInfoLight;
      }, (opts, val) -> {
         opts.ofQuickInfoLight = val;
      }, "ofQuickInfoLight");
      QUICK_INFO_MEMORY = new IterableOptionInt("of.options.QUICK_INFO_MEMORY", OFF_COMPACT_FULL, (opts) -> {
         return opts.ofQuickInfoMemory;
      }, (opts, val) -> {
         opts.ofQuickInfoMemory = val;
      }, "ofQuickInfoMemory");
      QUICK_INFO_NATIVE_MEMORY = new IterableOptionInt("of.options.QUICK_INFO_NATIVE_MEMORY", OFF_COMPACT_FULL, (opts) -> {
         return opts.ofQuickInfoNativeMemory;
      }, (opts, val) -> {
         opts.ofQuickInfoNativeMemory = val;
      }, "ofQuickInfoNativeMemory");
      QUICK_INFO_TARGET_BLOCK = new IterableOptionInt("of.options.QUICK_INFO_TARGET_BLOCK", OFF_COMPACT_FULL, (opts) -> {
         return opts.ofQuickInfoTargetBlock;
      }, (opts, val) -> {
         opts.ofQuickInfoTargetBlock = val;
      }, "ofQuickInfoTargetBlock");
      QUICK_INFO_TARGET_FLUID = new IterableOptionInt("of.options.QUICK_INFO_TARGET_FLUID", OFF_COMPACT_FULL, (opts) -> {
         return opts.ofQuickInfoTargetFluid;
      }, (opts, val) -> {
         opts.ofQuickInfoTargetFluid = val;
      }, "ofQuickInfoTargetFluid");
      QUICK_INFO_TARGET_ENTITY = new IterableOptionInt("of.options.QUICK_INFO_TARGET_ENTITY", OFF_COMPACT_FULL, (opts) -> {
         return opts.ofQuickInfoTargetEntity;
      }, (opts, val) -> {
         opts.ofQuickInfoTargetEntity = val;
      }, "ofQuickInfoTargetEntity");
      QUICK_INFO_LABELS = new IterableOptionInt("of.options.QUICK_INFO_LABELS", COMPACT_FULL_DETAILED, (opts) -> {
         return opts.ofQuickInfoLabels;
      }, (opts, val) -> {
         opts.ofQuickInfoLabels = val;
      }, "ofQuickInfoLabels");
      QUICK_INFO_BACKGROUND = new IterableOptionBool("of.options.QUICK_INFO_BACKGROUND", (opts) -> {
         return opts.ofQuickInfoBackground;
      }, (opts, val) -> {
         opts.ofQuickInfoBackground = val;
      }, "ofQuickInfoBackground");
   }
}
