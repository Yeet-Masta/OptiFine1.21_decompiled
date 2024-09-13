package net.optifine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.optifine.reflect.ReflectorRaw;
import net.optifine.util.ArrayUtils;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;

public class RandomEntities {
   private static Map<String, RandomEntityProperties<ResourceLocation>> mapProperties = new HashMap();
   private static Map<String, RandomEntityProperties<ResourceLocation>> mapSpriteProperties = new HashMap();
   private static boolean active = false;
   private static EntityRenderDispatcher entityRenderDispatcher;
   private static RandomEntity randomEntity = new RandomEntity();
   private static BlockEntityRenderDispatcher tileEntityRendererDispatcher;
   private static RandomTileEntity randomTileEntity = new RandomTileEntity();
   private static boolean working = false;
   public static String SUFFIX_PNG;
   public static String SUFFIX_PROPERTIES;
   public static String SEPARATOR_DIGITS;
   public static String PREFIX_TEXTURES_ENTITY;
   public static String PREFIX_TEXTURES_PAINTING;
   public static String PREFIX_TEXTURES;
   public static String PREFIX_OPTIFINE_RANDOM;
   public static String PREFIX_OPTIFINE;
   public static String PREFIX_OPTIFINE_MOB;
   private static String[] DEPENDANT_SUFFIXES = new String[]{
      "_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar"
   };
   private static String PREFIX_DYNAMIC_TEXTURE_HORSE;
   private static String[] HORSE_TEXTURES = (String[])ReflectorRaw.getFieldValue(null, Horse.class, String[].class, 0);
   private static String[] HORSE_TEXTURES_ABBR = (String[])ReflectorRaw.getFieldValue(null, Horse.class, String[].class, 1);

   public static void entityLoaded(Entity entity, Level world) {
      if (world != null) {
         SynchedEntityData edm = entity.m_20088_();
         edm.spawnPosition = entity.m_20183_();
         edm.spawnBiome = (Biome)world.m_204166_(edm.spawnPosition).m_203334_();
         if (entity instanceof ShoulderRidingEntity esr) {
            checkEntityShoulder(esr, false);
         }
      }
   }

   public static void entityUnloaded(Entity entity, Level world) {
      if (entity instanceof ShoulderRidingEntity esr) {
         checkEntityShoulder(esr, true);
      }
   }

   public static void checkEntityShoulder(ShoulderRidingEntity entity, boolean attach) {
      LivingEntity owner = entity.m_269323_();
      if (owner == null) {
         owner = Config.getMinecraft().f_91074_;
      }

      if (owner instanceof AbstractClientPlayer player) {
         UUID entityUuid = entity.m_20148_();
         if (attach) {
            player.lastAttachedEntity = entity;
            CompoundTag nbtLeft = player.m_36331_();
            if (nbtLeft != null && nbtLeft.m_128441_("UUID") && Config.equals(nbtLeft.m_128342_("UUID"), entityUuid)) {
               player.entityShoulderLeft = entity;
               player.lastAttachedEntity = null;
            }

            CompoundTag nbtRight = player.m_36332_();
            if (nbtRight != null && nbtRight.m_128441_("UUID") && Config.equals(nbtRight.m_128342_("UUID"), entityUuid)) {
               player.entityShoulderRight = entity;
               player.lastAttachedEntity = null;
            }
         } else {
            SynchedEntityData edm = entity.m_20088_();
            if (player.entityShoulderLeft != null && Config.equals(player.entityShoulderLeft.m_20148_(), entityUuid)) {
               SynchedEntityData edmShoulderLeft = player.entityShoulderLeft.m_20088_();
               edm.spawnPosition = edmShoulderLeft.spawnPosition;
               edm.spawnBiome = edmShoulderLeft.spawnBiome;
               player.entityShoulderLeft = null;
            }

            if (player.entityShoulderRight != null && Config.equals(player.entityShoulderRight.m_20148_(), entityUuid)) {
               SynchedEntityData edmShoulderRight = player.entityShoulderRight.m_20088_();
               edm.spawnPosition = edmShoulderRight.spawnPosition;
               edm.spawnBiome = edmShoulderRight.spawnBiome;
               player.entityShoulderRight = null;
            }
         }
      }
   }

   public static void worldChanged(Level oldWorld, Level newWorld) {
      if (newWorld instanceof ClientLevel newWorldClient) {
         for (Entity entity : newWorldClient.m_104735_()) {
            entityLoaded(entity, newWorld);
         }
      }

      randomEntity.setEntity(null);
      randomTileEntity.setTileEntity(null);
   }

   public static ResourceLocation getTextureLocation(ResourceLocation loc) {
      if (!active) {
         return loc;
      } else {
         IRandomEntity re = getRandomEntityRendered();
         if (re == null) {
            return loc;
         } else if (working) {
            return loc;
         } else {
            ResourceLocation props;
            try {
               working = true;
               String name = loc.m_135815_();
               if (name.startsWith("horse/")) {
                  name = getHorseTexturePath(name, "horse/".length());
               }

               if (name.startsWith("textures/entity/") || name.startsWith("textures/painting/")) {
                  RandomEntityProperties<ResourceLocation> propsx = (RandomEntityProperties<ResourceLocation>)mapProperties.get(name);
                  if (propsx != null) {
                     return propsx.m_213713_(re, loc);
                  }

                  return loc;
               }

               props = loc;
            } finally {
               working = false;
            }

            return props;
         }
      }
   }

   private static String getHorseTexturePath(String path, int pos) {
      if (HORSE_TEXTURES != null && HORSE_TEXTURES_ABBR != null) {
         for (int i = 0; i < HORSE_TEXTURES_ABBR.length; i++) {
            String abbr = HORSE_TEXTURES_ABBR[i];
            if (path.startsWith(abbr, pos)) {
               return HORSE_TEXTURES[i];
            }
         }

         return path;
      } else {
         return path;
      }
   }

   public static IRandomEntity getRandomEntityRendered() {
      if (entityRenderDispatcher.getRenderedEntity() != null) {
         randomEntity.setEntity(entityRenderDispatcher.getRenderedEntity());
         return randomEntity;
      } else {
         if (BlockEntityRenderDispatcher.tileEntityRendered != null) {
            BlockEntity te = BlockEntityRenderDispatcher.tileEntityRendered;
            if (te.m_58904_() != null) {
               randomTileEntity.setTileEntity(te);
               return randomTileEntity;
            }
         }

         return null;
      }
   }

   public static IRandomEntity getRandomEntity(Entity entityIn) {
      randomEntity.setEntity(entityIn);
      return randomEntity;
   }

   public static IRandomEntity getRandomBlockEntity(BlockEntity tileEntityIn) {
      randomTileEntity.setTileEntity(tileEntityIn);
      return randomTileEntity;
   }

   private static RandomEntityProperties<ResourceLocation> makeProperties(ResourceLocation loc, RandomEntityContext.Textures context) {
      String path = loc.m_135815_();
      ResourceLocation locProps = getLocationProperties(loc, context.isLegacy());
      if (locProps != null) {
         RandomEntityProperties props = RandomEntityProperties.m_82160_(locProps, loc, context);
         if (props != null) {
            return props;
         }
      }

      int[] variants = getLocationsVariants(loc, context.isLegacy(), context);
      return variants == null ? null : new RandomEntityProperties<>(path, loc, variants, context);
   }

   private static ResourceLocation getLocationProperties(ResourceLocation loc, boolean legacy) {
      ResourceLocation locMcp = getLocationRandom(loc, legacy);
      if (locMcp == null) {
         return null;
      } else {
         String domain = locMcp.m_135827_();
         String path = locMcp.m_135815_();
         String pathBase = StrUtils.removeSuffix(path, ".png");
         String pathProps = pathBase + ".properties";
         ResourceLocation locProps = new ResourceLocation(domain, pathProps);
         if (Config.hasResource(locProps)) {
            return locProps;
         } else {
            String pathParent = getParentTexturePath(pathBase);
            if (pathParent == null) {
               return null;
            } else {
               ResourceLocation locParentProps = new ResourceLocation(domain, pathParent + ".properties");
               return Config.hasResource(locParentProps) ? locParentProps : null;
            }
         }
      }
   }

   protected static ResourceLocation getLocationRandom(ResourceLocation loc, boolean legacy) {
      String domain = loc.m_135827_();
      String path = loc.m_135815_();
      if (path.startsWith("optifine/")) {
         return loc;
      } else {
         String prefixTextures = "textures/";
         String prefixRandom = "optifine/random/";
         if (legacy) {
            prefixTextures = "textures/entity/";
            prefixRandom = "optifine/mob/";
         }

         if (!path.startsWith(prefixTextures)) {
            return null;
         } else {
            String pathRandom = StrUtils.replacePrefix(path, prefixTextures, prefixRandom);
            return new ResourceLocation(domain, pathRandom);
         }
      }
   }

   private static String getPathBase(String pathRandom) {
      if (pathRandom.startsWith("optifine/random/")) {
         return StrUtils.replacePrefix(pathRandom, "optifine/random/", "textures/");
      } else {
         return pathRandom.startsWith("optifine/mob/") ? StrUtils.replacePrefix(pathRandom, "optifine/mob/", "textures/entity/") : null;
      }
   }

   protected static ResourceLocation getLocationIndexed(ResourceLocation loc, int index) {
      if (loc == null) {
         return null;
      } else {
         String path = loc.m_135815_();
         int pos = path.lastIndexOf(46);
         if (pos < 0) {
            return null;
         } else {
            String prefix = path.substring(0, pos);
            String suffix = path.substring(pos);
            String separator = StrUtils.endsWithDigit(prefix) ? "." : "";
            String pathNew = prefix + separator + index + suffix;
            return new ResourceLocation(loc.m_135827_(), pathNew);
         }
      }
   }

   private static String getParentTexturePath(String path) {
      for (int i = 0; i < DEPENDANT_SUFFIXES.length; i++) {
         String suffix = DEPENDANT_SUFFIXES[i];
         if (path.endsWith(suffix)) {
            return StrUtils.removeSuffix(path, suffix);
         }
      }

      return null;
   }

   public static int[] getLocationsVariants(ResourceLocation loc, boolean legacy, RandomEntityContext context) {
      List<Integer> list = new ArrayList();
      list.add(1);
      ResourceLocation locRandom = getLocationRandom(loc, legacy);
      if (locRandom == null) {
         return null;
      } else {
         for (int i = 1; i < list.size() + 10; i++) {
            int index = i + 1;
            ResourceLocation locIndex = getLocationIndexed(locRandom, index);
            if (Config.hasResource(locIndex)) {
               list.add(index);
            }
         }

         if (list.size() <= 1) {
            return null;
         } else {
            Integer[] arr = (Integer[])list.toArray(new Integer[list.size()]);
            int[] intArr = ArrayUtils.toPrimitive(arr);
            Config.dbg(context.getName() + ": " + loc.m_135815_() + ", variants: " + intArr.length);
            return intArr;
         }
      }
   }

   public static void m_252999_() {
      entityRenderDispatcher = Config.getEntityRenderDispatcher();
      tileEntityRendererDispatcher = Minecraft.m_91087_().m_167982_();
      mapProperties.clear();
      mapSpriteProperties.clear();
      active = false;
      if (Config.isRandomEntities()) {
         initialize();
      }
   }

   private static void initialize() {
      String[] prefixes = new String[]{"optifine/random/", "optifine/mob/"};
      String[] suffixes = new String[]{".png", ".properties"};
      String[] pathsRandom = ResUtils.collectFiles(prefixes, suffixes);
      Set basePathsChecked = new HashSet();

      for (int i = 0; i < pathsRandom.length; i++) {
         String path = pathsRandom[i];
         path = StrUtils.removeSuffix(path, suffixes);
         path = StrUtils.trimTrailing(path, "0123456789");
         path = StrUtils.removeSuffix(path, ".");
         path = path + ".png";
         String pathBase = getPathBase(path);
         if (!basePathsChecked.m_274455_(pathBase)) {
            basePathsChecked.add(pathBase);
            ResourceLocation locBase = new ResourceLocation(pathBase);
            if (Config.hasResource(locBase)) {
               RandomEntityProperties<ResourceLocation> props = (RandomEntityProperties<ResourceLocation>)mapProperties.get(pathBase);
               if (props == null) {
                  props = makeProperties(locBase, new RandomEntityContext.Textures(false));
                  if (props == null) {
                     props = makeProperties(locBase, new RandomEntityContext.Textures(true));
                  }

                  if (props != null) {
                     mapProperties.put(pathBase, props);
                  }
               }
            }
         }
      }

      active = !mapProperties.isEmpty();
   }

   public static synchronized void registerSprites(ResourceLocation atlasLocation, Set<ResourceLocation> spriteLocations) {
      if (!mapProperties.isEmpty()) {
         String prefix = getTexturePrefix(atlasLocation);
         Set<ResourceLocation> newLocations = new HashSet();

         for (ResourceLocation loc : spriteLocations) {
            String pathFull = "textures/" + prefix + loc.m_135815_() + ".png";
            RandomEntityProperties<ResourceLocation> props = (RandomEntityProperties<ResourceLocation>)mapProperties.get(pathFull);
            if (props != null) {
               mapSpriteProperties.put(loc.m_135815_(), props);
               List<ResourceLocation> locs = props.getAllResources();
               if (locs != null) {
                  for (int i = 0; i < locs.size(); i++) {
                     ResourceLocation propLoc = (ResourceLocation)locs.get(i);
                     ResourceLocation locSprite = TextureUtils.getSpriteLocation(propLoc);
                     newLocations.add(locSprite);
                     mapSpriteProperties.put(locSprite.m_135815_(), props);
                  }
               }
            }
         }

         spriteLocations.addAll(newLocations);
      }
   }

   private static String getTexturePrefix(ResourceLocation atlasLocation) {
      return atlasLocation.m_135815_().endsWith("/paintings.png") ? "painting/" : "";
   }

   public static TextureAtlasSprite getRandomSprite(TextureAtlasSprite spriteIn) {
      if (!active) {
         return spriteIn;
      } else {
         IRandomEntity re = getRandomEntityRendered();
         if (re == null) {
            return spriteIn;
         } else if (working) {
            return spriteIn;
         } else {
            TextureAtlasSprite locSprite;
            try {
               working = true;
               ResourceLocation locSpriteIn = spriteIn.getName();
               String name = locSpriteIn.m_135815_();
               RandomEntityProperties<ResourceLocation> props = (RandomEntityProperties<ResourceLocation>)mapSpriteProperties.get(name);
               if (props == null) {
                  return spriteIn;
               }

               ResourceLocation loc = props.m_213713_(re, locSpriteIn);
               if (loc != locSpriteIn) {
                  ResourceLocation locSpritex = TextureUtils.getSpriteLocation(loc);
                  return spriteIn.getTextureAtlas().m_118316_(locSpritex);
               }

               locSprite = spriteIn;
            } finally {
               working = false;
            }

            return locSprite;
         }
      }
   }

   public static void dbg(String str) {
      Config.dbg("RandomEntities: " + str);
   }

   public static void warn(String str) {
      Config.warn("RandomEntities: " + str);
   }
}
