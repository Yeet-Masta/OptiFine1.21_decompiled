package net.optifine;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.optifine.config.NbtTagValue;
import net.optifine.shaders.Shaders;
import net.optifine.util.EnchantmentUtils;
import net.optifine.util.PotionUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;

public class CustomItems {
   private static CustomItemProperties[][] itemProperties = null;
   private static CustomItemProperties[][] enchantmentProperties = null;
   private static Map mapPotionIds = null;
   private static ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
   private static boolean useGlint = true;
   private static boolean renderOffHand = false;
   private static AtomicBoolean modelsLoaded = new AtomicBoolean(false);
   public static final int MASK_POTION_SPLASH = 16384;
   public static final int MASK_POTION_NAME = 63;
   public static final int MASK_POTION_EXTENDED = 64;
   public static final String KEY_TEXTURE_OVERLAY = "texture.potion_overlay";
   public static final String KEY_TEXTURE_SPLASH = "texture.potion_bottle_splash";
   public static final String KEY_TEXTURE_DRINKABLE = "texture.potion_bottle_drinkable";
   public static final String DEFAULT_TEXTURE_OVERLAY = "item/potion_overlay";
   public static final String DEFAULT_TEXTURE_SPLASH = "item/potion_bottle_splash";
   public static final String DEFAULT_TEXTURE_DRINKABLE = "item/potion_bottle_drinkable";
   private static final int[][] EMPTY_INT2_ARRAY = new int[0][];
   private static final Map<String, Integer> mapPotionDamages = makeMapPotionDamages();
   private static final String TYPE_POTION_NORMAL = "normal";
   private static final String TYPE_POTION_SPLASH = "splash";
   private static final String TYPE_POTION_LINGER = "linger";

   public static void update() {
      itemProperties = null;
      enchantmentProperties = null;
      useGlint = true;
      modelsLoaded.set(false);
      if (Config.isCustomItems()) {
         readCitProperties("optifine/cit.properties");
         PackResources[] rps = Config.getResourcePacks();

         for (int i = rps.length - 1; i >= 0; i--) {
            PackResources rp = rps[i];
            update(rp);
         }

         update(Config.getDefaultResourcePack());
         if (itemProperties.length <= 0) {
            itemProperties = null;
         }

         if (enchantmentProperties.length <= 0) {
            enchantmentProperties = null;
         }
      }
   }

   private static void readCitProperties(String fileName) {
      try {
         net.minecraft.resources.ResourceLocation loc = new net.minecraft.resources.ResourceLocation(fileName);
         InputStream in = Config.getResourceStream(loc);
         if (in == null) {
            return;
         }

         Config.dbg("CustomItems: Loading " + fileName);
         Properties props = new PropertiesOrdered();
         props.load(in);
         in.close();
         useGlint = Config.parseBoolean(props.getProperty("useGlint"), true);
      } catch (FileNotFoundException var4) {
         return;
      } catch (IOException var5) {
         var5.printStackTrace();
      }
   }

   private static void update(PackResources rp) {
      String[] names = ResUtils.collectFiles(rp, "optifine/cit/", ".properties", null);
      Map<String, CustomItemProperties> mapAutoProperties = makeAutoImageProperties(rp);
      if (mapAutoProperties.size() > 0) {
         Set<String> keySetAuto = mapAutoProperties.keySet();
         String[] keysAuto = (String[])keySetAuto.toArray(new String[keySetAuto.size()]);
         names = (String[])Config.addObjectsToArray(names, keysAuto);
      }

      Arrays.sort(names);
      List<List<CustomItemProperties>> itemList = makePropertyList(itemProperties);
      List<List<CustomItemProperties>> enchantmentList = makePropertyList(enchantmentProperties);

      for (int i = 0; i < names.length; i++) {
         String name = names[i];
         Config.dbg("CustomItems: " + name);

         try {
            CustomItemProperties cip = null;
            if (mapAutoProperties.containsKey(name)) {
               cip = (CustomItemProperties)mapAutoProperties.get(name);
            }

            if (cip == null) {
               net.minecraft.resources.ResourceLocation locFile = new net.minecraft.resources.ResourceLocation(name);
               InputStream in = Config.getResourceStream(rp, PackType.CLIENT_RESOURCES, locFile);
               if (in == null) {
                  Config.warn("CustomItems file not found: " + name);
                  continue;
               }

               Properties props = new PropertiesOrdered();
               props.load(in);
               in.close();
               cip = new CustomItemProperties(props, name);
            }

            if (cip.isValid(name)) {
               addToItemList(cip, itemList);
               addToEnchantmentList(cip, enchantmentList);
            }
         } catch (FileNotFoundException var11) {
            Config.warn("CustomItems file not found: " + name);
         } catch (Exception var12) {
            var12.printStackTrace();
         }
      }

      itemProperties = propertyListToArray(itemList);
      enchantmentProperties = propertyListToArray(enchantmentList);
      Comparator comp = getPropertiesComparator();

      for (int i = 0; i < itemProperties.length; i++) {
         CustomItemProperties[] cips = itemProperties[i];
         if (cips != null) {
            Arrays.sort(cips, comp);
         }
      }

      for (int ix = 0; ix < enchantmentProperties.length; ix++) {
         CustomItemProperties[] cips = enchantmentProperties[ix];
         if (cips != null) {
            Arrays.sort(cips, comp);
         }
      }
   }

   private static Comparator getPropertiesComparator() {
      return new Comparator() {
         public int compare(Object o1, Object o2) {
            CustomItemProperties cip1 = (CustomItemProperties)o1;
            CustomItemProperties cip2 = (CustomItemProperties)o2;
            if (cip1.layer != cip2.layer) {
               return cip1.layer - cip2.layer;
            } else if (cip1.weight != cip2.weight) {
               return cip2.weight - cip1.weight;
            } else {
               return !cip1.basePath.equals(cip2.basePath) ? cip1.basePath.compareTo(cip2.basePath) : cip1.name.compareTo(cip2.name);
            }
         }
      };
   }

   public static void updateIcons(net.minecraft.client.renderer.texture.TextureAtlas textureMap) {
      while (!modelsLoaded.get()) {
         Config.sleep(100L);
      }

      for (CustomItemProperties cip : getAllProperties()) {
         cip.updateIcons(textureMap);
      }
   }

   public static void refreshIcons(net.minecraft.client.renderer.texture.TextureAtlas textureMap) {
      for (CustomItemProperties cip : getAllProperties()) {
         cip.refreshIcons(textureMap);
      }
   }

   public static void loadModels(net.minecraft.client.resources.model.ModelBakery modelBakery) {
      for (CustomItemProperties cip : getAllProperties()) {
         cip.loadModels(modelBakery);
      }

      modelsLoaded.set(true);
   }

   public static void updateModels() {
      for (CustomItemProperties cip : getAllProperties()) {
         if (cip.type == 1) {
            net.minecraft.client.renderer.texture.TextureAtlas textureMap = Config.getTextureMap();
            cip.updateModelTexture(textureMap, itemModelGenerator);
            cip.updateModelsFull();
         }
      }
   }

   private static List<CustomItemProperties> getAllProperties() {
      List<CustomItemProperties> list = new ArrayList();
      addAll(itemProperties, list);
      addAll(enchantmentProperties, list);
      return list;
   }

   private static void addAll(CustomItemProperties[][] cipsArr, List<CustomItemProperties> list) {
      if (cipsArr != null) {
         for (int i = 0; i < cipsArr.length; i++) {
            CustomItemProperties[] cips = cipsArr[i];
            if (cips != null) {
               for (int k = 0; k < cips.length; k++) {
                  CustomItemProperties cip = cips[k];
                  if (cip != null) {
                     list.add(cip);
                  }
               }
            }
         }
      }
   }

   private static Map<String, CustomItemProperties> makeAutoImageProperties(PackResources rp) {
      Map<String, CustomItemProperties> map = new HashMap();
      map.putAll(makePotionImageProperties(rp, "normal", BuiltInRegistries.f_257033_.m_7981_(Items.f_42589_)));
      map.putAll(makePotionImageProperties(rp, "splash", BuiltInRegistries.f_257033_.m_7981_(Items.f_42736_)));
      map.putAll(makePotionImageProperties(rp, "linger", BuiltInRegistries.f_257033_.m_7981_(Items.f_42739_)));
      return map;
   }

   private static Map<String, CustomItemProperties> makePotionImageProperties(PackResources rp, String type, net.minecraft.resources.ResourceLocation itemId) {
      Map<String, CustomItemProperties> map = new HashMap();
      String typePrefix = type + "/";
      String[] prefixes = new String[]{"optifine/cit/potion/" + typePrefix, "optifine/cit/Potion/" + typePrefix};
      String[] suffixes = new String[]{".png"};
      String[] names = ResUtils.collectFiles(rp, prefixes, suffixes);

      for (int i = 0; i < names.length; i++) {
         String path = names[i];
         String name = StrUtils.removePrefixSuffix(path, prefixes, suffixes);
         Properties props = makePotionProperties(name, type, itemId, path);
         if (props != null) {
            String pathProp = StrUtils.removeSuffix(path, suffixes) + ".properties";
            CustomItemProperties cip = new CustomItemProperties(props, pathProp);
            map.put(pathProp, cip);
         }
      }

      return map;
   }

   private static Properties makePotionProperties(String name, String type, net.minecraft.resources.ResourceLocation itemId, String path) {
      if (StrUtils.endsWith(name, new String[]{"_n", "_s"})) {
         return null;
      } else if (name.equals("empty") && type.equals("normal")) {
         itemId = BuiltInRegistries.f_257033_.m_7981_(Items.f_42590_);
         Properties props = new PropertiesOrdered();
         props.put("type", "item");
         props.put("items", itemId.toString());
         return props;
      } else {
         int[] damages = (int[])getMapPotionIds().get(name);
         if (damages == null) {
            Config.warn("Potion not found for image: " + path);
            return null;
         } else {
            StringBuffer bufDamage = new StringBuffer();

            for (int i = 0; i < damages.length; i++) {
               int damage = damages[i];
               if (type.equals("splash")) {
                  damage |= 16384;
               }

               if (i > 0) {
                  bufDamage.append(" ");
               }

               bufDamage.append(damage);
            }

            int damageMask = 16447;
            if (name.equals("water") || name.equals("mundane")) {
               damageMask |= 64;
            }

            Properties props = new PropertiesOrdered();
            props.put("type", "item");
            props.put("items", itemId.toString());
            props.put("damage", bufDamage.toString());
            props.put("damageMask", damageMask + "");
            if (type.equals("splash")) {
               props.put("texture.potion_bottle_splash", name);
            } else {
               props.put("texture.potion_bottle_drinkable", name);
            }

            return props;
         }
      }
   }

   private static Map getMapPotionIds() {
      if (mapPotionIds == null) {
         mapPotionIds = new LinkedHashMap();
         mapPotionIds.put("water", getPotionId(0, 0));
         mapPotionIds.put("awkward", getPotionId(0, 1));
         mapPotionIds.put("thick", getPotionId(0, 2));
         mapPotionIds.put("potent", getPotionId(0, 3));
         mapPotionIds.put("regeneration", getPotionIds(1));
         mapPotionIds.put("movespeed", getPotionIds(2));
         mapPotionIds.put("fireresistance", getPotionIds(3));
         mapPotionIds.put("poison", getPotionIds(4));
         mapPotionIds.put("heal", getPotionIds(5));
         mapPotionIds.put("nightvision", getPotionIds(6));
         mapPotionIds.put("clear", getPotionId(7, 0));
         mapPotionIds.put("bungling", getPotionId(7, 1));
         mapPotionIds.put("charming", getPotionId(7, 2));
         mapPotionIds.put("rank", getPotionId(7, 3));
         mapPotionIds.put("weakness", getPotionIds(8));
         mapPotionIds.put("damageboost", getPotionIds(9));
         mapPotionIds.put("moveslowdown", getPotionIds(10));
         mapPotionIds.put("leaping", getPotionIds(11));
         mapPotionIds.put("harm", getPotionIds(12));
         mapPotionIds.put("waterbreathing", getPotionIds(13));
         mapPotionIds.put("invisibility", getPotionIds(14));
         mapPotionIds.put("thin", getPotionId(15, 0));
         mapPotionIds.put("debonair", getPotionId(15, 1));
         mapPotionIds.put("sparkling", getPotionId(15, 2));
         mapPotionIds.put("stinky", getPotionId(15, 3));
         mapPotionIds.put("mundane", getPotionId(0, 4));
         mapPotionIds.put("speed", mapPotionIds.get("movespeed"));
         mapPotionIds.put("fire_resistance", mapPotionIds.get("fireresistance"));
         mapPotionIds.put("instant_health", mapPotionIds.get("heal"));
         mapPotionIds.put("night_vision", mapPotionIds.get("nightvision"));
         mapPotionIds.put("strength", mapPotionIds.get("damageboost"));
         mapPotionIds.put("slowness", mapPotionIds.get("moveslowdown"));
         mapPotionIds.put("instant_damage", mapPotionIds.get("harm"));
         mapPotionIds.put("water_breathing", mapPotionIds.get("waterbreathing"));
      }

      return mapPotionIds;
   }

   private static int[] getPotionIds(int baseId) {
      return new int[]{baseId, baseId + 16, baseId + 32, baseId + 48};
   }

   private static int[] getPotionId(int baseId, int subId) {
      return new int[]{baseId + subId * 16};
   }

   private static int getPotionNameDamage(String name) {
      String fullName = "effect." + name;

      for (net.minecraft.resources.ResourceLocation rl : BuiltInRegistries.f_256974_.m_6566_()) {
         if (BuiltInRegistries.f_256974_.m_7804_(rl)) {
            MobEffect potion = (MobEffect)BuiltInRegistries.f_256974_.m_7745_(rl);
            String potionName = potion.m_19481_();
            if (fullName.equals(potionName)) {
               return PotionUtils.getId(potion);
            }
         }
      }

      return -1;
   }

   private static List<List<CustomItemProperties>> makePropertyList(CustomItemProperties[][] propsArr) {
      List<List<CustomItemProperties>> list = new ArrayList();
      if (propsArr != null) {
         for (int i = 0; i < propsArr.length; i++) {
            CustomItemProperties[] props = propsArr[i];
            List<CustomItemProperties> propList = null;
            if (props != null) {
               propList = new ArrayList(Arrays.asList(props));
            }

            list.add(propList);
         }
      }

      return list;
   }

   private static CustomItemProperties[][] propertyListToArray(List list) {
      CustomItemProperties[][] propArr = new CustomItemProperties[list.size()][];

      for (int i = 0; i < list.size(); i++) {
         List subList = (List)list.get(i);
         if (subList != null) {
            CustomItemProperties[] subArr = (CustomItemProperties[])subList.toArray(new CustomItemProperties[subList.size()]);
            Arrays.sort(subArr, new CustomItemsComparator());
            propArr[i] = subArr;
         }
      }

      return propArr;
   }

   private static void addToItemList(CustomItemProperties cp, List<List<CustomItemProperties>> itemList) {
      if (cp.items != null) {
         for (int i = 0; i < cp.items.length; i++) {
            int itemId = cp.items[i];
            if (itemId <= 0) {
               Config.warn("Invalid item ID: " + itemId);
            } else {
               addToList(cp, itemList, itemId);
            }
         }
      }
   }

   private static void addToEnchantmentList(CustomItemProperties cp, List<List<CustomItemProperties>> enchantmentList) {
      if (cp.type == 2) {
         if (cp.enchantmentIds != null) {
            int countIds = getMaxEnchantmentId() + 1;

            for (int i = 0; i < countIds; i++) {
               if (Config.equalsOne(i, cp.enchantmentIds)) {
                  addToList(cp, enchantmentList, i);
               }
            }
         }
      }
   }

   private static int getMaxEnchantmentId() {
      return EnchantmentUtils.getMaxEnchantmentId();
   }

   private static void addToList(CustomItemProperties cp, List<List<CustomItemProperties>> list, int id) {
      while (id >= list.size()) {
         list.add(null);
      }

      List<CustomItemProperties> subList = (List<CustomItemProperties>)list.get(id);
      if (subList == null) {
         subList = new ArrayList();
         list.set(id, subList);
      }

      subList.add(cp);
   }

   public static net.minecraft.client.resources.model.BakedModel getCustomItemModel(
      ItemStack itemStack, net.minecraft.client.resources.model.BakedModel model, net.minecraft.resources.ResourceLocation modelLocation, boolean fullModel
   ) {
      if (!fullModel && model.m_7539_()) {
         return model;
      } else if (itemProperties == null) {
         return model;
      } else {
         CustomItemProperties props = getCustomItemProperties(itemStack, 1);
         if (props == null) {
            return model;
         } else {
            net.minecraft.client.resources.model.BakedModel customModel = props.getBakedModel(modelLocation, fullModel);
            return customModel != null ? customModel : model;
         }
      }
   }

   public static net.minecraft.resources.ResourceLocation getCustomArmorTexture(
      ItemStack itemStack, EquipmentSlot slot, String overlay, net.minecraft.resources.ResourceLocation locArmor
   ) {
      if (itemProperties == null) {
         return locArmor;
      } else {
         net.minecraft.resources.ResourceLocation loc = getCustomArmorLocation(itemStack, slot, overlay);
         return loc == null ? locArmor : loc;
      }
   }

   private static net.minecraft.resources.ResourceLocation getCustomArmorLocation(ItemStack itemStack, EquipmentSlot slot, String overlay) {
      CustomItemProperties props = getCustomItemProperties(itemStack, 3);
      if (props == null) {
         return null;
      } else if (props.mapTextureLocations == null) {
         return props.textureLocation;
      } else if (!(itemStack.m_41720_() instanceof ArmorItem itemArmor)) {
         return null;
      } else {
         List<net.minecraft.world.item.ArmorMaterial.Layer> layers = ((net.minecraft.world.item.ArmorMaterial)itemArmor.m_40401_().m_203334_()).f_315892_();
         if (layers.isEmpty()) {
            return null;
         } else {
            String material = ((net.minecraft.world.item.ArmorMaterial.Layer)layers.get(0)).getAssetName().m_135815_();
            int layer = slot == EquipmentSlot.LEGS ? 2 : 1;
            StringBuffer sb = new StringBuffer();
            sb.append("texture.");
            sb.append(material);
            sb.append("_layer_");
            sb.append(layer);
            if (overlay != null) {
               sb.append("_");
               sb.append(overlay);
            }

            String key = sb.toString();
            net.minecraft.resources.ResourceLocation loc = (net.minecraft.resources.ResourceLocation)props.mapTextureLocations.get(key);
            return loc == null ? props.textureLocation : loc;
         }
      }
   }

   public static net.minecraft.resources.ResourceLocation getCustomElytraTexture(ItemStack itemStack, net.minecraft.resources.ResourceLocation locElytra) {
      if (itemProperties == null) {
         return locElytra;
      } else {
         CustomItemProperties props = getCustomItemProperties(itemStack, 4);
         if (props == null) {
            return locElytra;
         } else {
            return props.textureLocation == null ? locElytra : props.textureLocation;
         }
      }
   }

   private static CustomItemProperties getCustomItemProperties(ItemStack itemStack, int type) {
      CustomItemProperties[][] propertiesLocal = itemProperties;
      if (propertiesLocal == null) {
         return null;
      } else if (itemStack == null) {
         return null;
      } else {
         Item item = itemStack.m_41720_();
         int itemId = Item.m_41393_(item);
         if (itemId >= 0 && itemId < propertiesLocal.length) {
            CustomItemProperties[] cips = propertiesLocal[itemId];
            if (cips != null) {
               for (int i = 0; i < cips.length; i++) {
                  CustomItemProperties cip = cips[i];
                  if (cip.type == type && matchesProperties(cip, itemStack, null)) {
                     return cip;
                  }
               }
            }
         }

         return null;
      }
   }

   private static boolean matchesProperties(CustomItemProperties cip, ItemStack itemStack, int[][] enchantmentIdLevels) {
      Item item = itemStack.m_41720_();
      if (cip.damage != null) {
         int damage = getItemStackDamage(itemStack);
         if (damage < 0) {
            return false;
         }

         if (cip.damageMask != 0) {
            damage &= cip.damageMask;
         }

         if (cip.damagePercent) {
            int damageMax = itemStack.m_41776_();
            damage = (int)((double)(damage * 100) / (double)damageMax);
         }

         if (!cip.damage.isInRange(damage)) {
            return false;
         }
      }

      if (cip.stackSize != null && !cip.stackSize.isInRange(itemStack.m_41613_())) {
         return false;
      } else {
         int[][] idLevels = enchantmentIdLevels;
         if (cip.enchantmentIds != null) {
            if (enchantmentIdLevels == null) {
               idLevels = getEnchantmentIdLevels(itemStack);
            }

            boolean idMatch = false;

            for (int i = 0; i < idLevels.length; i++) {
               int id = idLevels[i][0];
               if (Config.equalsOne(id, cip.enchantmentIds)) {
                  idMatch = true;
                  break;
               }
            }

            if (!idMatch) {
               return false;
            }
         }

         if (cip.enchantmentLevels != null) {
            if (idLevels == null) {
               idLevels = getEnchantmentIdLevels(itemStack);
            }

            boolean levelMatch = false;

            for (int ix = 0; ix < idLevels.length; ix++) {
               int level = idLevels[ix][1];
               if (cip.enchantmentLevels.isInRange(level)) {
                  levelMatch = true;
                  break;
               }
            }

            if (!levelMatch) {
               return false;
            }
         }

         if (cip.nbtTagValues != null) {
            CompoundTag nbt = net.optifine.util.ItemUtils.getTag(itemStack);

            for (int ixx = 0; ixx < cip.nbtTagValues.length; ixx++) {
               NbtTagValue ntv = cip.nbtTagValues[ixx];
               if (!ntv.matches(nbt)) {
                  return false;
               }
            }
         }

         if (cip.hand != 0) {
            if (cip.hand == 1 && renderOffHand) {
               return false;
            }

            if (cip.hand == 2 && !renderOffHand) {
               return false;
            }
         }

         return true;
      }
   }

   private static int getItemStackDamage(ItemStack itemStack) {
      Item item = itemStack.m_41720_();
      return item instanceof PotionItem ? getPotionDamage(itemStack) : itemStack.m_41773_();
   }

   private static int getPotionDamage(ItemStack itemStack) {
      Potion p = PotionUtils.getPotion(itemStack);
      if (p == null) {
         return 0;
      } else {
         String name = PotionUtils.getPotionBaseName(p);
         if (name != null && !name.equals("")) {
            Integer value = (Integer)mapPotionDamages.get(name);
            if (value == null) {
               return -1;
            } else {
               int val = value;
               if (itemStack.m_41720_() == Items.f_42736_) {
                  val |= 16384;
               }

               return val;
            }
         } else {
            return 0;
         }
      }
   }

   private static Map<String, Integer> makeMapPotionDamages() {
      Map<String, Integer> map = new HashMap();
      addPotion("water", 0, false, map);
      addPotion("awkward", 16, false, map);
      addPotion("thick", 32, false, map);
      addPotion("mundane", 64, false, map);
      addPotion("regeneration", 1, true, map);
      addPotion("swiftness", 2, true, map);
      addPotion("fire_resistance", 3, true, map);
      addPotion("poison", 4, true, map);
      addPotion("healing", 5, true, map);
      addPotion("night_vision", 6, true, map);
      addPotion("weakness", 8, true, map);
      addPotion("strength", 9, true, map);
      addPotion("slowness", 10, true, map);
      addPotion("leaping", 11, true, map);
      addPotion("harming", 12, true, map);
      addPotion("water_breathing", 13, true, map);
      addPotion("invisibility", 14, true, map);
      return map;
   }

   private static void addPotion(String name, int value, boolean extended, Map<String, Integer> map) {
      if (extended) {
         value |= 8192;
      }

      map.put("minecraft:" + name, value);
      if (extended) {
         int valueStrong = value | 32;
         map.put("minecraft:strong_" + name, valueStrong);
         int valueLong = value | 64;
         map.put("minecraft:long_" + name, valueLong);
      }
   }

   private static int[][] getEnchantmentIdLevels(ItemStack itemStack) {
      ItemEnchantments enchantments = (ItemEnchantments)itemStack.m_322304_(DataComponents.f_314658_, ItemEnchantments.f_314789_);
      if (enchantments.m_324000_()) {
         enchantments = (ItemEnchantments)itemStack.m_322304_(DataComponents.f_314515_, ItemEnchantments.f_314789_);
      }

      if (enchantments.m_324000_()) {
         return EMPTY_INT2_ARRAY;
      } else {
         Set<Entry<Holder<Enchantment>>> entries = enchantments.m_320130_();
         int[][] arr = new int[entries.size()][2];
         int i = 0;

         for (Entry<Holder<Enchantment>> entry : entries) {
            Holder<Enchantment> holder = (Holder<Enchantment>)entry.getKey();
            if (holder.m_203633_()) {
               Enchantment en = (Enchantment)holder.m_203334_();
               int id = EnchantmentUtils.getId(en);
               int lvl = entry.getIntValue();
               arr[i][0] = id;
               arr[i][1] = lvl;
               i++;
            }
         }

         return arr;
      }
   }

   public static boolean renderCustomEffect(
      net.minecraft.client.renderer.entity.ItemRenderer renderItem, ItemStack itemStack, net.minecraft.client.resources.model.BakedModel model
   ) {
      CustomItemProperties[][] propertiesLocal = enchantmentProperties;
      if (propertiesLocal == null) {
         return false;
      } else if (itemStack == null) {
         return false;
      } else {
         int[][] idLevels = getEnchantmentIdLevels(itemStack);
         if (idLevels.length <= 0) {
            return false;
         } else {
            Set layersRendered = null;
            return false;
         }
      }
   }

   public static boolean renderCustomArmorEffect(
      LivingEntity entity,
      ItemStack itemStack,
      EntityModel model,
      float limbSwing,
      float prevLimbSwing,
      float partialTicks,
      float timeLimbSwing,
      float yaw,
      float pitch,
      float scale
   ) {
      CustomItemProperties[][] propertiesLocal = enchantmentProperties;
      if (propertiesLocal == null) {
         return false;
      } else if (Config.isShaders() && Shaders.isShadowPass) {
         return false;
      } else if (itemStack == null) {
         return false;
      } else {
         int[][] idLevels = getEnchantmentIdLevels(itemStack);
         if (idLevels.length <= 0) {
            return false;
         } else {
            Set layersRendered = null;
            return false;
         }
      }
   }

   public static boolean isUseGlint() {
      return useGlint;
   }

   public static void setRenderOffHand(boolean renderOffHand) {
      CustomItems.renderOffHand = renderOffHand;
   }
}
