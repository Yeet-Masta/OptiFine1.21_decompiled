package net.optifine.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import net.minecraft.src.C_1505_;
import net.minecraft.src.C_254578_;
import net.minecraft.src.C_254583_;
import net.minecraft.src.C_256686_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5264_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_203228_.C_203231_;
import net.minecraft.src.C_213466_.C_254607_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class EnchantmentUtils {
   private static final C_5264_[] ENCHANTMENT_KEYS = makeEnchantmentKeys();
   private static final Map<String, Integer> TRANSLATION_KEY_IDS = new HashMap();
   private static final Map<String, C_1505_> MAP_ENCHANTMENTS = new HashMap();
   private static final Map<Integer, String> LEGACY_ID_NAMES = makeLegacyIdsMap();
   private static final Pattern PATTERN_NUMBER = Pattern.compile("\\d+");

   public static C_1505_ getEnchantment(String name) {
      if (PATTERN_NUMBER.matcher(name).matches()) {
         int id = Config.parseInt(name, -1);
         String legacyName = (String)LEGACY_ID_NAMES.get(id);
         if (legacyName != null) {
            name = legacyName;
         }
      }

      C_1505_ enchantment = (C_1505_)MAP_ENCHANTMENTS.get(name);
      if (enchantment == null) {
         C_254607_ holderlookup$provider = C_254578_.m_255371_();
         C_254583_<C_1505_> holdergetter = holderlookup$provider.m_255025_(C_256686_.f_256762_);
         C_5265_ loc = new C_5265_(name);
         C_5264_<C_1505_> key = C_5264_.m_135785_(C_256686_.f_256762_, loc);
         Optional<C_203231_<C_1505_>> optRef = holdergetter.m_254926_(key);
         if (optRef.isPresent()) {
            enchantment = (C_1505_)((C_203231_)optRef.get()).m_203334_();
         }

         MAP_ENCHANTMENTS.put(name, enchantment);
      }

      return enchantment;
   }

   private static C_5264_[] makeEnchantmentKeys() {
      return (C_5264_[])Reflector.Enchantments_ResourceKeys.getFieldValues(null);
   }

   private static Map<Integer, String> makeLegacyIdsMap() {
      Map<Integer, String> map = new HashMap();
      map.put(0, "protection");
      map.put(1, "fire_protection");
      map.put(2, "feather_falling");
      map.put(3, "blast_protection");
      map.put(4, "projectile_protection");
      map.put(5, "respiration");
      map.put(6, "aqua_affinity");
      map.put(7, "thorns");
      map.put(8, "depth_strider");
      map.put(9, "frost_walker");
      map.put(10, "binding_curse");
      map.put(16, "sharpness");
      map.put(17, "smite");
      map.put(18, "bane_of_arthropods");
      map.put(19, "knockback");
      map.put(20, "fire_aspect");
      map.put(21, "looting");
      map.put(32, "efficiency");
      map.put(33, "silk_touch");
      map.put(34, "unbreaking");
      map.put(35, "fortune");
      map.put(48, "power");
      map.put(49, "punch");
      map.put(50, "flame");
      map.put(51, "infinity");
      map.put(61, "luck_of_the_sea");
      map.put(62, "lure");
      map.put(65, "loyalty");
      map.put(66, "impaling");
      map.put(67, "riptide");
      map.put(68, "channeling");
      map.put(70, "mending");
      map.put(71, "vanishing_curse");
      return map;
   }

   public static int getId(C_1505_ en) {
      C_4996_ desc = en.f_337607_();
      String tranKey = ComponentUtils.getTranslationKey(desc);
      if (tranKey == null) {
         return -1;
      } else {
         Integer id = (Integer)TRANSLATION_KEY_IDS.get(tranKey);
         if (id == null) {
            C_5264_ resKey = getResourceKeyByTranslation(tranKey);
            if (resKey == null) {
               return -1;
            }

            int index = ArrayUtils.indexOf(ENCHANTMENT_KEYS, resKey);
            if (index < 0) {
               return -1;
            }

            id = index;
            TRANSLATION_KEY_IDS.put(tranKey, id);
         }

         return id;
      }
   }

   private static C_5264_ getResourceKeyByTranslation(String tranKey) {
      String[] parts = Config.tokenize(tranKey, ".");
      if (parts.length != 3) {
         return null;
      } else {
         String name = parts[2];

         for (int i = 0; i < ENCHANTMENT_KEYS.length; i++) {
            C_5264_<C_1505_> rk = ENCHANTMENT_KEYS[i];
            if (Config.equals(rk.m_135782_().m_135815_(), name)) {
               return rk;
            }
         }

         return null;
      }
   }

   public static int getMaxEnchantmentId() {
      return ENCHANTMENT_KEYS.length;
   }
}
