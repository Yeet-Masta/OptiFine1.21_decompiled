package net.optifine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
   private static Map mapProperties = new HashMap();
   private static Map mapSpriteProperties = new HashMap();
   private static boolean active = false;
   private static EntityRenderDispatcher entityRenderDispatcher;
   private static RandomEntity randomEntity = new RandomEntity();
   private static BlockEntityRenderDispatcher tileEntityRendererDispatcher;
   private static RandomTileEntity randomTileEntity = new RandomTileEntity();
   private static boolean working = false;
   public static final String SUFFIX_PNG = ".png";
   public static final String SUFFIX_PROPERTIES = ".properties";
   public static final String SEPARATOR_DIGITS = ".";
   public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
   public static final String PREFIX_TEXTURES_PAINTING = "textures/painting/";
   public static final String PREFIX_TEXTURES = "textures/";
   public static final String PREFIX_OPTIFINE_RANDOM = "optifine/random/";
   public static final String PREFIX_OPTIFINE = "optifine/";
   public static final String PREFIX_OPTIFINE_MOB = "optifine/mob/";
   private static final String[] DEPENDANT_SUFFIXES = new String[]{"_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar"};
   private static final String PREFIX_DYNAMIC_TEXTURE_HORSE = "horse/";
   private static final String[] HORSE_TEXTURES = (String[])ReflectorRaw.getFieldValue((Object)null, Horse.class, String[].class, 0);
   private static final String[] HORSE_TEXTURES_ABBR = (String[])ReflectorRaw.getFieldValue((Object)null, Horse.class, String[].class, 1);

   public static void entityLoaded(Entity entity, Level world) {
      if (world != null) {
         SynchedEntityData edm = entity.m_20088_();
         edm.spawnPosition = entity.m_20183_();
         edm.spawnBiome = (Biome)world.m_204166_(edm.spawnPosition).m_203334_();
         if (entity instanceof ShoulderRidingEntity) {
            ShoulderRidingEntity esr = (ShoulderRidingEntity)entity;
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
            SynchedEntityData edmShoulderRight;
            if (player.entityShoulderLeft != null && Config.equals(player.entityShoulderLeft.m_20148_(), entityUuid)) {
               edmShoulderRight = player.entityShoulderLeft.m_20088_();
               edm.spawnPosition = edmShoulderRight.spawnPosition;
               edm.spawnBiome = edmShoulderRight.spawnBiome;
               player.entityShoulderLeft = null;
            }

            if (player.entityShoulderRight != null && Config.equals(player.entityShoulderRight.m_20148_(), entityUuid)) {
               edmShoulderRight = player.entityShoulderRight.m_20088_();
               edm.spawnPosition = edmShoulderRight.spawnPosition;
               edm.spawnBiome = edmShoulderRight.spawnBiome;
               player.entityShoulderRight = null;
            }
         }

      }
   }

   public static void worldChanged(Level oldWorld, Level newWorld) {
      if (newWorld instanceof ClientLevel newWorldClient) {
         Iterable entities = newWorldClient.m_104735_();
         Iterator var4 = entities.iterator();

         while(var4.hasNext()) {
            Entity entity = (Entity)var4.next();
            entityLoaded(entity, newWorld);
         }
      }

      randomEntity.setEntity((Entity)null);
      randomTileEntity.setTileEntity((BlockEntity)null);
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
            ResourceLocation var4;
            try {
               working = true;
               String name = loc.m_135815_();
               if (name.startsWith("horse/")) {
                  name = getHorseTexturePath(name, "horse/".length());
               }

               if (!name.startsWith("textures/entity/") && !name.startsWith("textures/painting/")) {
                  ResourceLocation var8 = loc;
                  return var8;
               }

               RandomEntityProperties props = (RandomEntityProperties)mapProperties.get(name);
               if (props == null) {
                  var4 = loc;
                  return var4;
               }

               var4 = (ResourceLocation)props.getResource(re, loc);
            } finally {
               working = false;
            }

            return var4;
         }
      }
   }

   private static String getHorseTexturePath(String path, int pos) {
      if (HORSE_TEXTURES != null && HORSE_TEXTURES_ABBR != null) {
         for(int i = 0; i < HORSE_TEXTURES_ABBR.length; ++i) {
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
         BlockEntityRenderDispatcher var10000 = tileEntityRendererDispatcher;
         if (BlockEntityRenderDispatcher.tileEntityRendered != null) {
            var10000 = tileEntityRendererDispatcher;
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

   private static RandomEntityProperties makeProperties(ResourceLocation loc, RandomEntityContext.Textures context) {
      String path = loc.m_135815_();
      ResourceLocation locProps = getLocationProperties(loc, context.isLegacy());
      if (locProps != null) {
         RandomEntityProperties props = RandomEntityProperties.parse(locProps, loc, context);
         if (props != null) {
            return props;
         }
      }

      int[] variants = getLocationsVariants(loc, context.isLegacy(), context);
      return variants == null ? null : new RandomEntityProperties(path, loc, variants, context);
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
            ResourceLocation locNew = new ResourceLocation(loc.m_135827_(), pathNew);
            return locNew;
         }
      }
   }

   private static String getParentTexturePath(String path) {
      for(int i = 0; i < DEPENDANT_SUFFIXES.length; ++i) {
         String suffix = DEPENDANT_SUFFIXES[i];
         if (path.endsWith(suffix)) {
            String pathParent = StrUtils.removeSuffix(path, suffix);
            return pathParent;
         }
      }

      return null;
   }

   public static int[] getLocationsVariants(ResourceLocation loc, boolean legacy, RandomEntityContext context) {
      List list = new ArrayList();
      list.add(1);
      ResourceLocation locRandom = getLocationRandom(loc, legacy);
      if (locRandom == null) {
         return null;
      } else {
         for(int i = 1; i < list.size() + 10; ++i) {
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
            String var10000 = context.getName();
            Config.dbg(var10000 + ": " + loc.m_135815_() + ", variants: " + intArr.length);
            return intArr;
         }
      }
   }

   public static void update() {
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

      for(int i = 0; i < pathsRandom.length; ++i) {
         String path = pathsRandom[i];
         path = StrUtils.removeSuffix(path, suffixes);
         path = StrUtils.trimTrailing(path, "0123456789");
         path = StrUtils.removeSuffix(path, ".");
         path = path + ".png";
         String pathBase = getPathBase(path);
         if (!basePathsChecked.contains(pathBase)) {
            basePathsChecked.add(pathBase);
            ResourceLocation locBase = new ResourceLocation(pathBase);
            if (Config.hasResource(locBase)) {
               RandomEntityProperties props = (RandomEntityProperties)mapProperties.get(pathBase);
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

   public static synchronized void registerSprites(ResourceLocation atlasLocation, Set spriteLocations) {
      if (!mapProperties.isEmpty()) {
         String prefix = getTexturePrefix(atlasLocation);
         Set newLocations = new HashSet();
         Iterator var4 = spriteLocations.iterator();

         while(true) {
            RandomEntityProperties props;
            List locs;
            do {
               ResourceLocation loc;
               do {
                  if (!var4.hasNext()) {
                     spriteLocations.addAll(newLocations);
                     return;
                  }

                  loc = (ResourceLocation)var4.next();
                  String pathFull = "textures/" + prefix + loc.m_135815_() + ".png";
                  props = (RandomEntityProperties)mapProperties.get(pathFull);
               } while(props == null);

               mapSpriteProperties.put(loc.m_135815_(), props);
               locs = props.getAllResources();
            } while(locs == null);

            for(int i = 0; i < locs.size(); ++i) {
               ResourceLocation propLoc = (ResourceLocation)locs.get(i);
               ResourceLocation locSprite = TextureUtils.getSpriteLocation(propLoc);
               newLocations.add(locSprite);
               mapSpriteProperties.put(locSprite.m_135815_(), props);
            }
         }
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
            TextureAtlasSprite var6;
            try {
               working = true;
               ResourceLocation locSpriteIn = spriteIn.getName();
               String name = locSpriteIn.m_135815_();
               RandomEntityProperties props = (RandomEntityProperties)mapSpriteProperties.get(name);
               if (props == null) {
                  TextureAtlasSprite var12 = spriteIn;
                  return var12;
               }

               ResourceLocation loc = (ResourceLocation)props.getResource(re, locSpriteIn);
               if (loc != locSpriteIn) {
                  ResourceLocation locSprite = TextureUtils.getSpriteLocation(loc);
                  TextureAtlasSprite sprite = spriteIn.getTextureAtlas().m_118316_(locSprite);
                  TextureAtlasSprite var8 = sprite;
                  return var8;
               }

               var6 = spriteIn;
            } finally {
               working = false;
            }

            return var6;
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
