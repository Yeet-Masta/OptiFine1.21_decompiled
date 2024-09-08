package net.optifine;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.datafixers.util.Either;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import net.minecraft.src.C_1313_;
import net.minecraft.src.C_1318_;
import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1394_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4196_;
import net.minecraft.src.C_4197_;
import net.minecraft.src.C_4200_;
import net.minecraft.src.C_4205_;
import net.minecraft.src.C_4211_;
import net.minecraft.src.C_4213_;
import net.minecraft.src.C_4219_;
import net.minecraft.src.C_4468_;
import net.minecraft.src.C_4473_;
import net.minecraft.src.C_4484_;
import net.minecraft.src.C_4486_;
import net.minecraft.src.C_4490_;
import net.minecraft.src.C_4528_;
import net.minecraft.src.C_4529_;
import net.minecraft.src.C_4531_;
import net.minecraft.src.C_4532_;
import net.minecraft.src.C_4535_;
import net.minecraft.src.C_4536_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_516_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_4540_.C_4541_;
import net.optifine.config.IParserInt;
import net.optifine.config.NbtTagValue;
import net.optifine.config.ParserEnchantmentId;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import net.optifine.render.Blender;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;

public class CustomItemProperties {
   public String name = null;
   public String basePath = null;
   public int type = 1;
   public int[] items = null;
   public String texture = null;
   public Map<String, String> mapTextures = null;
   public String model = null;
   public Map<String, String> mapModels = null;
   public RangeListInt damage = null;
   public boolean damagePercent = false;
   public int damageMask = 0;
   public RangeListInt stackSize = null;
   public int[] enchantmentIds = null;
   public RangeListInt enchantmentLevels = null;
   public NbtTagValue[] nbtTagValues = null;
   public int hand = 0;
   public int blend = 1;
   public float speed = 0.0F;
   public float rotation = 0.0F;
   public int layer = 0;
   public float duration = 1.0F;
   public int weight = 0;
   public C_5265_ textureLocation = null;
   public Map mapTextureLocations = null;
   public C_4486_ sprite = null;
   public Map mapSprites = null;
   public C_4528_ bakedModelTexture = null;
   public Map<String, C_4528_> mapBakedModelsTexture = null;
   public C_4528_ bakedModelFull = null;
   public Map<String, C_4528_> mapBakedModelsFull = null;
   public Set<C_5265_> modelSpriteLocations = null;
   private int textureWidth = 0;
   private int textureHeight = 0;
   public static final int TYPE_UNKNOWN = 0;
   public static final int TYPE_ITEM = 1;
   public static final int TYPE_ENCHANTMENT = 2;
   public static final int TYPE_ARMOR = 3;
   public static final int TYPE_ELYTRA = 4;
   public static final int HAND_ANY = 0;
   public static final int HAND_MAIN = 1;
   public static final int HAND_OFF = 2;
   public static final String INVENTORY = "inventory";

   public CustomItemProperties(Properties props, String path) {
      this.name = parseName(path);
      this.basePath = parseBasePath(path);
      this.type = this.parseType(props.getProperty("type"));
      this.items = this.parseItems(props.getProperty("items"), props.getProperty("matchItems"));
      this.mapModels = parseModels(props, this.basePath);
      this.model = parseModel(props.getProperty("model"), path, this.basePath, this.type, this.mapModels);
      this.mapTextures = parseTextures(props, this.basePath);
      boolean textureFromPath = this.mapModels == null && this.model == null;
      this.texture = parseTexture(
         props.getProperty("texture"),
         props.getProperty("tile"),
         props.getProperty("source"),
         path,
         this.basePath,
         this.type,
         this.mapTextures,
         textureFromPath
      );
      String damageStr = props.getProperty("damage");
      if (damageStr != null) {
         this.damagePercent = damageStr.contains("%");
         damageStr = damageStr.replace("%", "");
         this.damage = this.parseRangeListInt(damageStr);
         this.damageMask = this.parseInt(props.getProperty("damageMask"), 0);
      }

      this.stackSize = this.parseRangeListInt(props.getProperty("stackSize"));
      this.enchantmentIds = this.parseInts(getProperty(props, "enchantmentIDs", "enchantments"), new ParserEnchantmentId());
      this.enchantmentLevels = this.parseRangeListInt(props.getProperty("enchantmentLevels"));
      this.nbtTagValues = this.parseNbtTagValues(props);
      this.hand = this.parseHand(props.getProperty("hand"));
      this.blend = Blender.parseBlend(props.getProperty("blend"));
      this.speed = this.parseFloat(props.getProperty("speed"), 0.0F);
      this.rotation = this.parseFloat(props.getProperty("rotation"), 0.0F);
      this.layer = this.parseInt(props.getProperty("layer"), 0);
      this.weight = this.parseInt(props.getProperty("weight"), 0);
      this.duration = this.parseFloat(props.getProperty("duration"), 1.0F);
   }

   private static String getProperty(Properties props, String... names) {
      for (int i = 0; i < names.length; i++) {
         String name = names[i];
         String val = props.getProperty(name);
         if (val != null) {
            return val;
         }
      }

      return null;
   }

   private static String parseName(String path) {
      String str = path;
      int pos = path.lastIndexOf(47);
      if (pos >= 0) {
         str = path.substring(pos + 1);
      }

      int pos2 = str.lastIndexOf(46);
      if (pos2 >= 0) {
         str = str.substring(0, pos2);
      }

      return str;
   }

   private static String parseBasePath(String path) {
      int pos = path.lastIndexOf(47);
      return pos < 0 ? "" : path.substring(0, pos);
   }

   private int parseType(String str) {
      if (str == null) {
         return 1;
      } else if (str.equals("item")) {
         return 1;
      } else if (str.equals("enchantment")) {
         return 2;
      } else if (str.equals("armor")) {
         return 3;
      } else if (str.equals("elytra")) {
         return 4;
      } else {
         Config.warn("Unknown method: " + str);
         return 0;
      }
   }

   private int[] parseItems(String str, String str2) {
      if (str == null) {
         str = str2;
      }

      if (str == null) {
         return null;
      } else {
         str = str.trim();
         Set setItemIds = new TreeSet();
         String[] tokens = Config.tokenize(str, " ");

         for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            C_1381_ item = this.getItemByName(token);
            if (item == null) {
               Config.warn("Item not found: " + token);
            } else {
               int id = C_1381_.m_41393_(item);
               if (id < 0) {
                  Config.warn("Item ID not found: " + token);
               } else {
                  setItemIds.add(new Integer(id));
               }
            }
         }

         Integer[] integers = (Integer[])setItemIds.toArray(new Integer[setItemIds.size()]);
         int[] ints = new int[integers.length];

         for (int ix = 0; ix < ints.length; ix++) {
            ints[ix] = integers[ix];
         }

         return ints;
      }
   }

   private C_1381_ getItemByName(String name) {
      C_5265_ loc = new C_5265_(name);
      return !C_256712_.f_257033_.d(loc) ? null : (C_1381_)C_256712_.f_257033_.m_7745_(loc);
   }

   private static String parseTexture(
      String texStr, String texStr2, String texStr3, String path, String basePath, int type, Map<String, String> mapTexs, boolean textureFromPath
   ) {
      if (texStr == null) {
         texStr = texStr2;
      }

      if (texStr == null) {
         texStr = texStr3;
      }

      if (texStr != null) {
         String png = ".png";
         if (texStr.endsWith(png)) {
            texStr = texStr.substring(0, texStr.length() - png.length());
         }

         return fixTextureName(texStr, basePath);
      } else if (type == 3) {
         return null;
      } else {
         if (mapTexs != null) {
            String bowStandbyTex = (String)mapTexs.get("texture.bow_standby");
            if (bowStandbyTex != null) {
               return bowStandbyTex;
            }
         }

         if (!textureFromPath) {
            return null;
         } else {
            String str = path;
            int pos = path.lastIndexOf(47);
            if (pos >= 0) {
               str = path.substring(pos + 1);
            }

            int pos2 = str.lastIndexOf(46);
            if (pos2 >= 0) {
               str = str.substring(0, pos2);
            }

            return fixTextureName(str, basePath);
         }
      }
   }

   private static Map parseTextures(Properties props, String basePath) {
      String prefix = "texture.";
      Map mapProps = getMatchingProperties(props, prefix);
      if (mapProps.size() <= 0) {
         return null;
      } else {
         Set keySet = mapProps.keySet();
         Map mapTex = new LinkedHashMap();

         for (String key : keySet) {
            String val = (String)mapProps.get(key);
            val = fixTextureName(val, basePath);
            mapTex.put(key, val);
         }

         return mapTex;
      }
   }

   private static String fixTextureName(String iconName, String basePath) {
      iconName = TextureUtils.fixResourcePath(iconName, basePath);
      if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("optifine/")) {
         iconName = basePath + "/" + iconName;
      }

      if (iconName.endsWith(".png")) {
         iconName = iconName.substring(0, iconName.length() - 4);
      }

      if (iconName.startsWith("/")) {
         iconName = iconName.substring(1);
      }

      return iconName;
   }

   private static String parseModel(String modelStr, String path, String basePath, int type, Map<String, String> mapModelNames) {
      if (modelStr != null) {
         String json = ".json";
         if (modelStr.endsWith(json)) {
            modelStr = modelStr.substring(0, modelStr.length() - json.length());
         }

         return fixModelName(modelStr, basePath);
      } else if (type == 3) {
         return null;
      } else {
         if (mapModelNames != null) {
            String bowStandbyModel = (String)mapModelNames.get("model.bow_standby");
            if (bowStandbyModel != null) {
               return bowStandbyModel;
            }
         }

         return modelStr;
      }
   }

   private static Map parseModels(Properties props, String basePath) {
      String prefix = "model.";
      Map mapProps = getMatchingProperties(props, prefix);
      if (mapProps.size() <= 0) {
         return null;
      } else {
         Set keySet = mapProps.keySet();
         Map mapTex = new LinkedHashMap();

         for (String key : keySet) {
            String val = (String)mapProps.get(key);
            val = fixModelName(val, basePath);
            mapTex.put(key, val);
         }

         return mapTex;
      }
   }

   private static String fixModelName(String modelName, String basePath) {
      modelName = TextureUtils.fixResourcePath(modelName, basePath);
      boolean isVanilla = modelName.startsWith("block/") || modelName.startsWith("item/");
      if (!modelName.startsWith(basePath) && !isVanilla && !modelName.startsWith("optifine/")) {
         modelName = basePath + "/" + modelName;
      }

      String json = ".json";
      if (modelName.endsWith(json)) {
         modelName = modelName.substring(0, modelName.length() - json.length());
      }

      if (modelName.startsWith("/")) {
         modelName = modelName.substring(1);
      }

      return modelName;
   }

   private int parseInt(String str, int defVal) {
      if (str == null) {
         return defVal;
      } else {
         str = str.trim();
         int val = Config.parseInt(str, Integer.MIN_VALUE);
         if (val == Integer.MIN_VALUE) {
            Config.warn("Invalid integer: " + str);
            return defVal;
         } else {
            return val;
         }
      }
   }

   private float parseFloat(String str, float defVal) {
      if (str == null) {
         return defVal;
      } else {
         str = str.trim();
         float val = Config.parseFloat(str, Float.MIN_VALUE);
         if (val == Float.MIN_VALUE) {
            Config.warn("Invalid float: " + str);
            return defVal;
         } else {
            return val;
         }
      }
   }

   private int[] parseInts(String str, IParserInt parser) {
      if (str == null) {
         return null;
      } else {
         String[] tokens = Config.tokenize(str, " ");
         List<Integer> list = new ArrayList();

         for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            int val = parser.parse(token, Integer.MIN_VALUE);
            if (val == Integer.MIN_VALUE) {
               Config.warn("Invalid value: " + token);
            } else {
               list.add(val);
            }
         }

         Integer[] intArr = (Integer[])list.toArray(new Integer[list.size()]);
         return Config.toPrimitive(intArr);
      }
   }

   private RangeListInt parseRangeListInt(String str) {
      if (str == null) {
         return null;
      } else {
         String[] tokens = Config.tokenize(str, " ");
         RangeListInt rangeList = new RangeListInt();

         for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            RangeInt range = this.parseRangeInt(token);
            if (range == null) {
               Config.warn("Invalid range list: " + str);
               return null;
            }

            rangeList.addRange(range);
         }

         return rangeList;
      }
   }

   private RangeInt parseRangeInt(String str) {
      if (str == null) {
         return null;
      } else {
         str = str.trim();
         int countMinus = str.length() - str.replace("-", "").length();
         if (countMinus > 1) {
            Config.warn("Invalid range: " + str);
            return null;
         } else {
            String[] tokens = Config.tokenize(str, "- ");
            int[] vals = new int[tokens.length];

            for (int i = 0; i < tokens.length; i++) {
               String token = tokens[i];
               int val = Config.parseInt(token, -1);
               if (val < 0) {
                  Config.warn("Invalid range: " + str);
                  return null;
               }

               vals[i] = val;
            }

            if (vals.length == 1) {
               int val = vals[0];
               if (str.startsWith("-")) {
                  return new RangeInt(0, val);
               } else {
                  return str.endsWith("-") ? new RangeInt(val, 65535) : new RangeInt(val, val);
               }
            } else if (vals.length == 2) {
               int min = Math.min(vals[0], vals[1]);
               int max = Math.max(vals[0], vals[1]);
               return new RangeInt(min, max);
            } else {
               Config.warn("Invalid range: " + str);
               return null;
            }
         }
      }
   }

   private NbtTagValue[] parseNbtTagValues(Properties props) {
      String PREFIX_NBT = "nbt.";
      String PREFIX_COMPONENTS = "components.";
      Map<String, String> mapComponents = getMatchingProperties(props, PREFIX_COMPONENTS);
      Map<String, String> mapNbt = getMatchingProperties(props, PREFIX_NBT);

      for (Entry<String, String> entry : mapNbt.entrySet()) {
         String key = (String)entry.getKey();
         String val = (String)entry.getValue();
         String id = key.substring(PREFIX_NBT.length());
         if (id.equals("display.Name")) {
            mapComponents.putIfAbsent(PREFIX_COMPONENTS + "minecraft:custom_name", val);
         } else if (id.equals("display.Lore")) {
            mapComponents.putIfAbsent(PREFIX_COMPONENTS + "minecraft:lore", val);
         }

         Config.warn("Deprecated NBT check: " + key + "=" + val);
      }

      if (mapComponents.size() <= 0) {
         return null;
      } else {
         List<NbtTagValue> listNbts = new ArrayList();

         for (Entry<String, String> entry : mapComponents.entrySet()) {
            String key = (String)entry.getKey();
            String val = (String)entry.getValue();
            String id = key.substring(PREFIX_COMPONENTS.length());
            id = this.fixNamespaces(id);
            NbtTagValue nbt = new NbtTagValue(id, val);
            listNbts.add(nbt);
         }

         return (NbtTagValue[])listNbts.toArray(new NbtTagValue[listNbts.size()]);
      }
   }

   private String fixNamespaces(String id) {
      int posPoint = id.indexOf(46);
      int posNameEnd = posPoint >= 0 ? posPoint : id.length();
      if (id.indexOf(58, 0, posNameEnd) < 0) {
         id = "minecraft:" + id;
      }

      return id.replace("~", "minecraft:");
   }

   private static Map<String, String> getMatchingProperties(Properties props, String keyPrefix) {
      Map map = new LinkedHashMap();

      for (String key : props.keySet()) {
         String val = props.getProperty(key);
         if (key.startsWith(keyPrefix)) {
            map.put(key, val);
         }
      }

      return map;
   }

   private int parseHand(String str) {
      if (str == null) {
         return 0;
      } else {
         str = str.toLowerCase();
         if (str.equals("any")) {
            return 0;
         } else if (str.equals("main")) {
            return 1;
         } else if (str.equals("off")) {
            return 2;
         } else {
            Config.warn("Invalid hand: " + str);
            return 0;
         }
      }
   }

   public boolean isValid(String path) {
      if (this.name == null || this.name.length() <= 0) {
         Config.warn("No name found: " + path);
         return false;
      } else if (this.basePath == null) {
         Config.warn("No base path found: " + path);
         return false;
      } else if (this.type == 0) {
         Config.warn("No type defined: " + path);
         return false;
      } else {
         if (this.type == 4 && this.items == null) {
            this.items = new int[]{C_1381_.m_41393_(C_1394_.f_42741_)};
         }

         if (this.type == 1 || this.type == 3 || this.type == 4) {
            if (this.items == null) {
               this.items = this.detectItems();
            }

            if (this.items == null) {
               Config.warn("No items defined: " + path);
               return false;
            }
         }

         if (this.texture == null && this.mapTextures == null && this.model == null && this.mapModels == null) {
            Config.warn("No texture or model specified: " + path);
            return false;
         } else if (this.type == 2 && this.enchantmentIds == null) {
            Config.warn("No enchantmentIDs specified: " + path);
            return false;
         } else {
            return true;
         }
      }
   }

   private int[] detectItems() {
      C_1381_ item = this.getItemByName(this.name);
      if (item == null) {
         return null;
      } else {
         int id = C_1381_.m_41393_(item);
         return id < 0 ? null : new int[]{id};
      }
   }

   public void updateIcons(C_4484_ textureMap) {
      if (this.texture != null) {
         this.textureLocation = this.getTextureLocation(this.texture);
         if (this.type == 1) {
            C_5265_ spriteLocation = this.getSpriteLocation(this.textureLocation);
            this.sprite = textureMap.registerSprite(spriteLocation);
         }
      }

      if (this.mapTextures != null) {
         this.mapTextureLocations = new HashMap();
         this.mapSprites = new HashMap();

         for (String key : this.mapTextures.keySet()) {
            String val = (String)this.mapTextures.get(key);
            C_5265_ locTex = this.getTextureLocation(val);
            this.mapTextureLocations.put(key, locTex);
            if (this.type == 1) {
               C_5265_ locSprite = this.getSpriteLocation(locTex);
               C_4486_ icon = textureMap.registerSprite(locSprite);
               this.mapSprites.put(key, icon);
            }
         }
      }

      for (C_5265_ loc : this.modelSpriteLocations) {
         textureMap.registerSprite(loc);
      }
   }

   public void refreshIcons(C_4484_ textureMap) {
      if (this.sprite != null) {
         this.sprite = textureMap.m_118316_(this.sprite.getName());
      }

      if (this.mapSprites != null) {
         for (String key : this.mapSprites.keySet()) {
            C_4486_ sprite = (C_4486_)this.mapSprites.get(key);
            if (sprite != null) {
               C_5265_ loc = sprite.getName();
               C_4486_ spriteNew = textureMap.m_118316_(loc);
               if (spriteNew == null || C_4473_.isMisingSprite(spriteNew)) {
                  Config.warn("Missing CIT sprite: " + loc + ", properties: " + this.basePath);
               }

               this.mapSprites.put(key, spriteNew);
            }
         }
      }
   }

   private C_5265_ getTextureLocation(String texName) {
      if (texName == null) {
         return null;
      } else {
         C_5265_ resLoc = new C_5265_(texName);
         String domain = resLoc.m_135827_();
         String path = resLoc.m_135815_();
         if (!path.contains("/")) {
            path = "textures/item/" + path;
         }

         String filePath = path + ".png";
         C_5265_ locFile = new C_5265_(domain, filePath);
         boolean exists = Config.hasResource(locFile);
         if (!exists) {
            Config.warn("File not found: " + filePath);
         }

         return locFile;
      }
   }

   private C_5265_ getSpriteLocation(C_5265_ resLoc) {
      String pathTex = resLoc.m_135815_();
      pathTex = StrUtils.removePrefix(pathTex, "textures/");
      pathTex = StrUtils.removeSuffix(pathTex, ".png");
      return new C_5265_(resLoc.m_135827_(), pathTex);
   }

   public void updateModelTexture(C_4484_ textureMap, C_4213_ itemModelGenerator) {
      if (this.texture != null || this.mapTextures != null) {
         String[] textures = this.getModelTextures();
         boolean useTint = this.isUseTint();
         this.bakedModelTexture = makeBakedModel(textureMap, itemModelGenerator, textures, useTint);
         if (this.type == 1 && this.mapTextures != null) {
            for (String key : this.mapTextures.keySet()) {
               String tex = (String)this.mapTextures.get(key);
               String path = StrUtils.removePrefix(key, "texture.");
               if (this.isSubTexture(path)) {
                  String[] texNames = new String[]{tex};
                  C_4528_ modelTex = makeBakedModel(textureMap, itemModelGenerator, texNames, useTint);
                  if (this.mapBakedModelsTexture == null) {
                     this.mapBakedModelsTexture = new HashMap();
                  }

                  String location = "item/" + path;
                  this.mapBakedModelsTexture.put(location, modelTex);
               }
            }
         }
      }
   }

   private boolean isSubTexture(String path) {
      return path.startsWith("bow") || path.startsWith("crossbow") || path.startsWith("fishing_rod") || path.startsWith("shield");
   }

   private boolean isUseTint() {
      return true;
   }

   private static C_4528_ makeBakedModel(C_4484_ textureMap, C_4213_ itemModelGenerator, String[] textures, boolean useTint) {
      String[] spriteNames = new String[textures.length];

      for (int i = 0; i < spriteNames.length; i++) {
         String texture = textures[i];
         spriteNames[i] = StrUtils.removePrefix(texture, "textures/");
      }

      C_4205_ modelBlockBase = makeModelBlock(spriteNames);
      C_4205_ modelBlock = itemModelGenerator.m_111670_(CustomItemProperties::getSprite, modelBlockBase);
      return bakeModel(textureMap, modelBlock, useTint);
   }

   public static C_4486_ getSprite(C_4531_ material) {
      C_4484_ atlasTexture = C_3391_.m_91087_().m_91304_().m_119428_(material.m_119193_());
      return atlasTexture.m_118316_(material.m_119203_());
   }

   private String[] getModelTextures() {
      if (this.type == 1 && this.items.length == 1) {
         C_1381_ item = C_1381_.m_41445_(this.items[0]);
         boolean isPotionItem = item == C_1394_.f_42589_ || item == C_1394_.f_42736_ || item == C_1394_.f_42739_;
         if (isPotionItem && this.damage != null && this.damage.getCountRanges() > 0) {
            RangeInt range = this.damage.getRange(0);
            int valDamage = range.getMin();
            boolean splash = (valDamage & 16384) != 0;
            String texOverlay = this.getMapTexture(this.mapTextures, "texture.potion_overlay", "item/potion_overlay");
            String texMain = null;
            if (splash) {
               texMain = this.getMapTexture(this.mapTextures, "texture.potion_bottle_splash", "item/potion_bottle_splash");
            } else {
               texMain = this.getMapTexture(this.mapTextures, "texture.potion_bottle_drinkable", "item/potion_bottle_drinkable");
            }

            return new String[]{texOverlay, texMain};
         }

         if (item instanceof C_1313_ itemArmor && itemArmor.m_40401_() == C_1318_.f_40453_) {
            String material = "leather";
            String type = "helmet";
            C_516_ equipmentSlot = itemArmor.m_40402_();
            if (equipmentSlot == C_516_.HEAD) {
               type = "helmet";
            }

            if (equipmentSlot == C_516_.CHEST) {
               type = "chestplate";
            }

            if (equipmentSlot == C_516_.LEGS) {
               type = "leggings";
            }

            if (equipmentSlot == C_516_.FEET) {
               type = "boots";
            }

            String key = material + "_" + type;
            String texMain = this.getMapTexture(this.mapTextures, "texture." + key, "item/" + key);
            String texOverlay = this.getMapTexture(this.mapTextures, "texture." + key + "_overlay", "item/" + key + "_overlay");
            return new String[]{texMain, texOverlay};
         }
      }

      return new String[]{this.texture};
   }

   private String getMapTexture(Map<String, String> map, String key, String def) {
      if (map == null) {
         return def;
      } else {
         String str = (String)map.get(key);
         return str == null ? def : str;
      }
   }

   private static C_4205_ makeModelBlock(String[] modelTextures) {
      StringBuffer sb = new StringBuffer();
      sb.append("{\"parent\": \"builtin/generated\",\"textures\": {");

      for (int i = 0; i < modelTextures.length; i++) {
         String modelTexture = modelTextures[i];
         if (i > 0) {
            sb.append(", ");
         }

         sb.append("\"layer" + i + "\": \"" + modelTexture + "\"");
      }

      sb.append("}}");
      String modelStr = sb.toString();
      return C_4205_.m_111463_(modelStr);
   }

   private static C_4528_ bakeModel(C_4484_ textureMap, C_4205_ modelBlockIn, boolean useTint) {
      C_4529_ modelRotationIn = C_4529_.X0_Y0;
      C_4531_ materialParticle = modelBlockIn.m_111480_("particle");
      C_4486_ var4 = materialParticle.m_119204_();
      C_4541_ var5 = new C_4541_(modelBlockIn, C_4219_.f_111734_, false).m_119528_(var4);

      for (C_4197_ blockPart : modelBlockIn.m_111436_()) {
         for (C_4687_ direction : blockPart.f_111310_.keySet()) {
            C_4200_ blockPartFace = (C_4200_)blockPart.f_111310_.get(direction);
            if (!useTint) {
               blockPartFace = new C_4200_(blockPartFace.f_111354_(), -1, blockPartFace.f_111356_(), blockPartFace.f_111357_());
            }

            C_4531_ material = modelBlockIn.m_111480_(blockPartFace.f_111356_());
            C_4486_ sprite = material.m_119204_();
            C_4196_ quad = makeBakedQuad(blockPart, blockPartFace, sprite, direction, modelRotationIn);
            if (blockPartFace.f_111354_() == null) {
               var5.m_119526_(quad);
            } else {
               var5.m_119530_(C_4687_.m_252919_(modelRotationIn.m_6189_().m_252783_(), blockPartFace.f_111354_()), quad);
            }
         }
      }

      return var5.m_119533_();
   }

   private static C_4196_ makeBakedQuad(C_4197_ blockPart, C_4200_ blockPartFace, C_4486_ textureAtlasSprite, C_4687_ enumFacing, C_4529_ modelRotation) {
      C_4211_ faceBakery = new C_4211_();
      return faceBakery.m_111600_(
         blockPart.f_111308_, blockPart.f_111309_, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, blockPart.f_111311_, blockPart.f_111312_
      );
   }

   public String toString() {
      return this.basePath + "/" + this.name + ", type: " + this.type + ", items: [" + Config.arrayToString(this.items) + "], texture: " + this.texture;
   }

   public float getTextureWidth(C_4490_ textureManager) {
      if (this.textureWidth <= 0) {
         if (this.textureLocation != null) {
            C_4468_ tex = textureManager.m_118506_(this.textureLocation);
            int texId = tex.m_117963_();
            int prevTexId = GlStateManager.getBoundTexture();
            GlStateManager._bindTexture(texId);
            this.textureWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096);
            GlStateManager._bindTexture(prevTexId);
         }

         if (this.textureWidth <= 0) {
            this.textureWidth = 16;
         }
      }

      return (float)this.textureWidth;
   }

   public float getTextureHeight(C_4490_ textureManager) {
      if (this.textureHeight <= 0) {
         if (this.textureLocation != null) {
            C_4468_ tex = textureManager.m_118506_(this.textureLocation);
            int texId = tex.m_117963_();
            int prevTexId = GlStateManager.getBoundTexture();
            GlStateManager._bindTexture(texId);
            this.textureHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097);
            GlStateManager._bindTexture(prevTexId);
         }

         if (this.textureHeight <= 0) {
            this.textureHeight = 16;
         }
      }

      return (float)this.textureHeight;
   }

   public C_4528_ getBakedModel(C_5265_ modelLocation, boolean fullModel) {
      C_4528_ bakedModel;
      Map<String, C_4528_> mapBakedModels;
      if (fullModel) {
         bakedModel = this.bakedModelFull;
         mapBakedModels = this.mapBakedModelsFull;
      } else {
         bakedModel = this.bakedModelTexture;
         mapBakedModels = this.mapBakedModelsTexture;
      }

      if (modelLocation != null && mapBakedModels != null) {
         String modelPath = modelLocation.m_135815_();
         C_4528_ customModel = (C_4528_)mapBakedModels.get(modelPath);
         if (customModel != null) {
            return customModel;
         }
      }

      return bakedModel;
   }

   public void loadModels(C_4532_ modelBakery) {
      this.modelSpriteLocations = new LinkedHashSet();
      if (this.model != null) {
         this.loadItemModel(modelBakery, this.model);
      }

      if (this.type == 1 && this.mapModels != null) {
         for (String key : this.mapModels.keySet()) {
            String mod = (String)this.mapModels.get(key);
            String path = StrUtils.removePrefix(key, "model.");
            if (this.isSubTexture(path)) {
               this.loadItemModel(modelBakery, mod);
            }
         }
      }
   }

   public void updateModelsFull() {
      C_4535_ modelManager = Config.getModelManager();
      C_4528_ missingModel = modelManager.m_119409_();
      if (this.model != null) {
         C_5265_ locItem = getModelLocation(this.model);
         C_4536_ mrl = new C_4536_(locItem, "inventory");
         this.bakedModelFull = modelManager.m_119422_(mrl);
         if (this.bakedModelFull == missingModel) {
            Config.warn("Custom Items: Model not found " + mrl.toString());
            this.bakedModelFull = null;
         }
      }

      if (this.type == 1 && this.mapModels != null) {
         for (String key : this.mapModels.keySet()) {
            String mod = (String)this.mapModels.get(key);
            String path = StrUtils.removePrefix(key, "model.");
            if (this.isSubTexture(path)) {
               C_5265_ locItem = getModelLocation(mod);
               C_4536_ mrl = new C_4536_(locItem, "inventory");
               C_4528_ bm = modelManager.m_119422_(mrl);
               if (bm == missingModel) {
                  Config.warn("Custom Items: Model not found " + mrl.toString());
               } else {
                  if (this.mapBakedModelsFull == null) {
                     this.mapBakedModelsFull = new HashMap();
                  }

                  String location = "item/" + path;
                  this.mapBakedModelsFull.put(location, bm);
               }
            }
         }
      }
   }

   private void loadItemModel(C_4532_ modelBakery, String model) {
      C_5265_ locModel = getModelLocation(model);
      modelBakery.m_339007_(locModel);
      if (modelBakery.m_119341_(locModel) instanceof C_4205_ modelBlock && C_4211_.getTextures(modelBlock) != null) {
         for (Entry<String, Either<C_4531_, String>> entry : C_4211_.getTextures(modelBlock).entrySet()) {
            Either<C_4531_, String> value = (Either<C_4531_, String>)entry.getValue();
            Optional<C_4531_> optionalMaterial = value.left();
            if (optionalMaterial.isPresent()) {
               C_4531_ material = (C_4531_)optionalMaterial.get();
               C_5265_ textureLocation = material.m_119203_();
               this.modelSpriteLocations.add(textureLocation);
            }
         }
      }
   }

   private static C_5265_ getModelLocation(String modelName) {
      return new C_5265_(modelName);
   }
}
