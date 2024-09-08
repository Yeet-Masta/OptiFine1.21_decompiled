package net.optifine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1991_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3899_;
import net.minecraft.src.C_4102_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4486_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5247_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_899_;
import net.minecraft.src.C_928_;
import net.optifine.reflect.ReflectorRaw;
import net.optifine.util.ArrayUtils;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;

public class RandomEntities {
   private static Map<String, RandomEntityProperties<C_5265_>> mapProperties = new HashMap();
   private static Map<String, RandomEntityProperties<C_5265_>> mapSpriteProperties = new HashMap();
   private static boolean active = false;
   private static C_4330_ entityRenderDispatcher;
   private static RandomEntity randomEntity = new RandomEntity();
   private static C_4243_ tileEntityRendererDispatcher;
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
   private static final String[] DEPENDANT_SUFFIXES = new String[]{
      "_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar"
   };
   private static final String PREFIX_DYNAMIC_TEXTURE_HORSE = "horse/";
   private static final String[] HORSE_TEXTURES = (String[])ReflectorRaw.getFieldValue(null, C_928_.class, String[].class, 0);
   private static final String[] HORSE_TEXTURES_ABBR = (String[])ReflectorRaw.getFieldValue(null, C_928_.class, String[].class, 1);

   public static void entityLoaded(C_507_ entity, C_1596_ world) {
      if (world != null) {
         C_5247_ edm = entity.m_20088_();
         edm.spawnPosition = entity.m_20183_();
         edm.spawnBiome = (C_1629_)world.t(edm.spawnPosition).m_203334_();
         if (entity instanceof C_899_ esr) {
            checkEntityShoulder(esr, false);
         }
      }
   }

   public static void entityUnloaded(C_507_ entity, C_1596_ world) {
      if (entity instanceof C_899_ esr) {
         checkEntityShoulder(esr, true);
      }
   }

   public static void checkEntityShoulder(C_899_ entity, boolean attach) {
      C_524_ owner = entity.T_();
      if (owner == null) {
         owner = Config.getMinecraft().f_91074_;
      }

      if (owner instanceof C_4102_ player) {
         UUID entityUuid = entity.cz();
         if (attach) {
            player.lastAttachedEntity = entity;
            C_4917_ nbtLeft = player.gp();
            if (nbtLeft != null && nbtLeft.m_128441_("UUID") && Config.equals(nbtLeft.m_128342_("UUID"), entityUuid)) {
               player.entityShoulderLeft = entity;
               player.lastAttachedEntity = null;
            }

            C_4917_ nbtRight = player.gq();
            if (nbtRight != null && nbtRight.m_128441_("UUID") && Config.equals(nbtRight.m_128342_("UUID"), entityUuid)) {
               player.entityShoulderRight = entity;
               player.lastAttachedEntity = null;
            }
         } else {
            C_5247_ edm = entity.ar();
            if (player.entityShoulderLeft != null && Config.equals(player.entityShoulderLeft.cz(), entityUuid)) {
               C_5247_ edmShoulderLeft = player.entityShoulderLeft.ar();
               edm.spawnPosition = edmShoulderLeft.spawnPosition;
               edm.spawnBiome = edmShoulderLeft.spawnBiome;
               player.entityShoulderLeft = null;
            }

            if (player.entityShoulderRight != null && Config.equals(player.entityShoulderRight.cz(), entityUuid)) {
               C_5247_ edmShoulderRight = player.entityShoulderRight.ar();
               edm.spawnPosition = edmShoulderRight.spawnPosition;
               edm.spawnBiome = edmShoulderRight.spawnBiome;
               player.entityShoulderRight = null;
            }
         }
      }
   }

   public static void worldChanged(C_1596_ oldWorld, C_1596_ newWorld) {
      if (newWorld instanceof C_3899_ newWorldClient) {
         for (C_507_ entity : newWorldClient.m_104735_()) {
            entityLoaded(entity, newWorld);
         }
      }

      randomEntity.setEntity(null);
      randomTileEntity.setTileEntity(null);
   }

   public static C_5265_ getTextureLocation(C_5265_ loc) {
      if (!active) {
         return loc;
      } else {
         IRandomEntity re = getRandomEntityRendered();
         if (re == null) {
            return loc;
         } else if (working) {
            return loc;
         } else {
            C_5265_ props;
            try {
               working = true;
               String name = loc.m_135815_();
               if (name.startsWith("horse/")) {
                  name = getHorseTexturePath(name, "horse/".length());
               }

               if (name.startsWith("textures/entity/") || name.startsWith("textures/painting/")) {
                  RandomEntityProperties<C_5265_> propsx = (RandomEntityProperties<C_5265_>)mapProperties.get(name);
                  if (propsx != null) {
                     return propsx.getResource(re, loc);
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
         if (C_4243_.tileEntityRendered != null) {
            C_1991_ te = C_4243_.tileEntityRendered;
            if (te.m_58904_() != null) {
               randomTileEntity.setTileEntity(te);
               return randomTileEntity;
            }
         }

         return null;
      }
   }

   public static IRandomEntity getRandomEntity(C_507_ entityIn) {
      randomEntity.setEntity(entityIn);
      return randomEntity;
   }

   public static IRandomEntity getRandomBlockEntity(C_1991_ tileEntityIn) {
      randomTileEntity.setTileEntity(tileEntityIn);
      return randomTileEntity;
   }

   private static RandomEntityProperties<C_5265_> makeProperties(C_5265_ loc, RandomEntityContext.Textures context) {
      String path = loc.m_135815_();
      C_5265_ locProps = getLocationProperties(loc, context.isLegacy());
      if (locProps != null) {
         RandomEntityProperties props = RandomEntityProperties.parse(locProps, loc, context);
         if (props != null) {
            return props;
         }
      }

      int[] variants = getLocationsVariants(loc, context.isLegacy(), context);
      return variants == null ? null : new RandomEntityProperties(path, loc, variants, context);
   }

   private static C_5265_ getLocationProperties(C_5265_ loc, boolean legacy) {
      C_5265_ locMcp = getLocationRandom(loc, legacy);
      if (locMcp == null) {
         return null;
      } else {
         String domain = locMcp.m_135827_();
         String path = locMcp.m_135815_();
         String pathBase = StrUtils.removeSuffix(path, ".png");
         String pathProps = pathBase + ".properties";
         C_5265_ locProps = new C_5265_(domain, pathProps);
         if (Config.hasResource(locProps)) {
            return locProps;
         } else {
            String pathParent = getParentTexturePath(pathBase);
            if (pathParent == null) {
               return null;
            } else {
               C_5265_ locParentProps = new C_5265_(domain, pathParent + ".properties");
               return Config.hasResource(locParentProps) ? locParentProps : null;
            }
         }
      }
   }

   protected static C_5265_ getLocationRandom(C_5265_ loc, boolean legacy) {
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
            return new C_5265_(domain, pathRandom);
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

   protected static C_5265_ getLocationIndexed(C_5265_ loc, int index) {
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
            return new C_5265_(loc.m_135827_(), pathNew);
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

   public static int[] getLocationsVariants(C_5265_ loc, boolean legacy, RandomEntityContext context) {
      List<Integer> list = new ArrayList();
      list.add(1);
      C_5265_ locRandom = getLocationRandom(loc, legacy);
      if (locRandom == null) {
         return null;
      } else {
         for (int i = 1; i < list.size() + 10; i++) {
            int index = i + 1;
            C_5265_ locIndex = getLocationIndexed(locRandom, index);
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

   public static void update() {
      entityRenderDispatcher = Config.getEntityRenderDispatcher();
      tileEntityRendererDispatcher = C_3391_.m_91087_().m_167982_();
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
         if (!basePathsChecked.contains(pathBase)) {
            basePathsChecked.add(pathBase);
            C_5265_ locBase = new C_5265_(pathBase);
            if (Config.hasResource(locBase)) {
               RandomEntityProperties<C_5265_> props = (RandomEntityProperties<C_5265_>)mapProperties.get(pathBase);
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

   public static synchronized void registerSprites(C_5265_ atlasLocation, Set<C_5265_> spriteLocations) {
      if (!mapProperties.isEmpty()) {
         String prefix = getTexturePrefix(atlasLocation);
         Set<C_5265_> newLocations = new HashSet();

         for (C_5265_ loc : spriteLocations) {
            String pathFull = "textures/" + prefix + loc.m_135815_() + ".png";
            RandomEntityProperties<C_5265_> props = (RandomEntityProperties<C_5265_>)mapProperties.get(pathFull);
            if (props != null) {
               mapSpriteProperties.put(loc.m_135815_(), props);
               List<C_5265_> locs = props.getAllResources();
               if (locs != null) {
                  for (int i = 0; i < locs.size(); i++) {
                     C_5265_ propLoc = (C_5265_)locs.get(i);
                     C_5265_ locSprite = TextureUtils.getSpriteLocation(propLoc);
                     newLocations.add(locSprite);
                     mapSpriteProperties.put(locSprite.m_135815_(), props);
                  }
               }
            }
         }

         spriteLocations.addAll(newLocations);
      }
   }

   private static String getTexturePrefix(C_5265_ atlasLocation) {
      return atlasLocation.m_135815_().endsWith("/paintings.png") ? "painting/" : "";
   }

   public static C_4486_ getRandomSprite(C_4486_ spriteIn) {
      if (!active) {
         return spriteIn;
      } else {
         IRandomEntity re = getRandomEntityRendered();
         if (re == null) {
            return spriteIn;
         } else if (working) {
            return spriteIn;
         } else {
            C_4486_ locSprite;
            try {
               working = true;
               C_5265_ locSpriteIn = spriteIn.getName();
               String name = locSpriteIn.m_135815_();
               RandomEntityProperties<C_5265_> props = (RandomEntityProperties<C_5265_>)mapSpriteProperties.get(name);
               if (props == null) {
                  return spriteIn;
               }

               C_5265_ loc = props.getResource(re, locSpriteIn);
               if (loc != locSpriteIn) {
                  C_5265_ locSpritex = TextureUtils.getSpriteLocation(loc);
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
