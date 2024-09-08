package net.optifine;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import net.minecraft.src.C_1118_;
import net.minecraft.src.C_1119_;
import net.minecraft.src.C_1121_;
import net.minecraft.src.C_1353_;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_1599_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1728_;
import net.minecraft.src.C_1981_;
import net.minecraft.src.C_1991_;
import net.minecraft.src.C_1997_;
import net.minecraft.src.C_2004_;
import net.minecraft.src.C_2005_;
import net.minecraft.src.C_2007_;
import net.minecraft.src.C_2021_;
import net.minecraft.src.C_2035_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_2085_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_926_;
import net.minecraft.src.C_927_;
import net.minecraft.src.C_928_;
import net.minecraft.src.C_930_;
import net.minecraft.src.C_936_;
import net.optifine.config.BiomeId;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchProfession;
import net.optifine.config.Matches;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeListInt;
import net.optifine.reflect.Reflector;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;

public class CustomGuiProperties {
   private String fileName = null;
   private String basePath = null;
   private CustomGuiProperties.EnumContainer container = null;
   private Map<C_5265_, C_5265_> textureLocations = null;
   private NbtTagValue nbtName = null;
   private BiomeId[] biomes = null;
   private RangeListInt heights = null;
   private Boolean large = null;
   private Boolean trapped = null;
   private Boolean christmas = null;
   private Boolean ender = null;
   private RangeListInt levels = null;
   private MatchProfession[] professions = null;
   private CustomGuiProperties.EnumVariant[] variants = null;
   private C_1353_[] colors = null;
   private static final CustomGuiProperties.EnumVariant[] VARIANTS_HORSE = new CustomGuiProperties.EnumVariant[]{
      CustomGuiProperties.EnumVariant.HORSE,
      CustomGuiProperties.EnumVariant.DONKEY,
      CustomGuiProperties.EnumVariant.MULE,
      CustomGuiProperties.EnumVariant.LLAMA
   };
   private static final CustomGuiProperties.EnumVariant[] VARIANTS_DISPENSER = new CustomGuiProperties.EnumVariant[]{
      CustomGuiProperties.EnumVariant.DISPENSER, CustomGuiProperties.EnumVariant.DROPPER
   };
   private static final CustomGuiProperties.EnumVariant[] VARIANTS_INVALID = new CustomGuiProperties.EnumVariant[0];
   private static final C_1353_[] COLORS_INVALID = new C_1353_[0];
   private static final C_5265_ ANVIL_GUI_TEXTURE = new C_5265_("textures/gui/container/anvil.png");
   private static final C_5265_ BEACON_GUI_TEXTURE = new C_5265_("textures/gui/container/beacon.png");
   private static final C_5265_ BREWING_STAND_GUI_TEXTURE = new C_5265_("textures/gui/container/brewing_stand.png");
   private static final C_5265_ CHEST_GUI_TEXTURE = new C_5265_("textures/gui/container/generic_54.png");
   private static final C_5265_ CRAFTING_TABLE_GUI_TEXTURE = new C_5265_("textures/gui/container/crafting_table.png");
   private static final C_5265_ HORSE_GUI_TEXTURE = new C_5265_("textures/gui/container/horse.png");
   private static final C_5265_ DISPENSER_GUI_TEXTURE = new C_5265_("textures/gui/container/dispenser.png");
   private static final C_5265_ ENCHANTMENT_TABLE_GUI_TEXTURE = new C_5265_("textures/gui/container/enchanting_table.png");
   private static final C_5265_ FURNACE_GUI_TEXTURE = new C_5265_("textures/gui/container/furnace.png");
   private static final C_5265_ HOPPER_GUI_TEXTURE = new C_5265_("textures/gui/container/hopper.png");
   private static final C_5265_ INVENTORY_GUI_TEXTURE = new C_5265_("textures/gui/container/inventory.png");
   private static final C_5265_ SHULKER_BOX_GUI_TEXTURE = new C_5265_("textures/gui/container/shulker_box.png");
   private static final C_5265_ VILLAGER_GUI_TEXTURE = new C_5265_("textures/gui/container/villager2.png");

   public CustomGuiProperties(Properties props, String path) {
      ConnectedParser cp = new ConnectedParser("CustomGuis");
      this.fileName = cp.parseName(path);
      this.basePath = cp.parseBasePath(path);
      this.container = (CustomGuiProperties.EnumContainer)cp.parseEnum(props.getProperty("container"), CustomGuiProperties.EnumContainer.values(), "container");
      this.textureLocations = parseTextureLocations(props, "texture", this.container, "textures/gui/", this.basePath);
      this.nbtName = cp.parseNbtTagValue("name", props.getProperty("name"));
      this.biomes = cp.parseBiomes(props.getProperty("biomes"));
      this.heights = cp.parseRangeListIntNeg(props.getProperty("heights"));
      this.large = cp.parseBooleanObject(props.getProperty("large"));
      this.trapped = cp.parseBooleanObject(props.getProperty("trapped"));
      this.christmas = cp.parseBooleanObject(props.getProperty("christmas"));
      this.ender = cp.parseBooleanObject(props.getProperty("ender"));
      this.levels = cp.parseRangeListInt(props.getProperty("levels"));
      this.professions = cp.parseProfessions(props.getProperty("professions"));
      CustomGuiProperties.EnumVariant[] vars = getContainerVariants(this.container);
      this.variants = (CustomGuiProperties.EnumVariant[])cp.parseEnums(props.getProperty("variants"), vars, "variants", VARIANTS_INVALID);
      this.colors = parseEnumDyeColors(props.getProperty("colors"));
   }

   private static CustomGuiProperties.EnumVariant[] getContainerVariants(CustomGuiProperties.EnumContainer cont) {
      if (cont == CustomGuiProperties.EnumContainer.HORSE) {
         return VARIANTS_HORSE;
      } else {
         return cont == CustomGuiProperties.EnumContainer.DISPENSER ? VARIANTS_DISPENSER : new CustomGuiProperties.EnumVariant[0];
      }
   }

   private static C_1353_[] parseEnumDyeColors(String str) {
      if (str == null) {
         return null;
      } else {
         str = str.toLowerCase();
         String[] tokens = Config.tokenize(str, " ");
         C_1353_[] cols = new C_1353_[tokens.length];

         for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            C_1353_ col = parseEnumDyeColor(token);
            if (col == null) {
               warn("Invalid color: " + token);
               return COLORS_INVALID;
            }

            cols[i] = col;
         }

         return cols;
      }
   }

   private static C_1353_ parseEnumDyeColor(String str) {
      if (str == null) {
         return null;
      } else {
         C_1353_[] colors = C_1353_.values();

         for (int i = 0; i < colors.length; i++) {
            C_1353_ enumDyeColor = colors[i];
            if (enumDyeColor.m_7912_().equals(str)) {
               return enumDyeColor;
            }

            if (enumDyeColor.m_41065_().equals(str)) {
               return enumDyeColor;
            }
         }

         return null;
      }
   }

   private static C_5265_ parseTextureLocation(String str, String basePath) {
      if (str == null) {
         return null;
      } else {
         str = str.trim();
         String tex = TextureUtils.fixResourcePath(str, basePath);
         if (!tex.endsWith(".png")) {
            tex = tex + ".png";
         }

         return new C_5265_(basePath + "/" + tex);
      }
   }

   private static Map<C_5265_, C_5265_> parseTextureLocations(
      Properties props, String property, CustomGuiProperties.EnumContainer container, String pathPrefix, String basePath
   ) {
      Map<C_5265_, C_5265_> map = new HashMap();
      String propVal = props.getProperty(property);
      if (propVal != null) {
         C_5265_ locKey = getGuiTextureLocation(container);
         C_5265_ locVal = parseTextureLocation(propVal, basePath);
         if (locKey != null && locVal != null) {
            map.put(locKey, locVal);
         }
      }

      String keyPrefix = property + ".";

      for (String key : props.keySet()) {
         if (key.startsWith(keyPrefix)) {
            String pathRel = key.substring(keyPrefix.length());
            pathRel = pathRel.replace('\\', '/');
            pathRel = StrUtils.removePrefixSuffix(pathRel, "/", ".png");
            String path = pathPrefix + pathRel + ".png";
            String val = props.getProperty(key);
            C_5265_ locKey = new C_5265_(path);
            C_5265_ locVal = parseTextureLocation(val, basePath);
            map.put(locKey, locVal);
         }
      }

      return map;
   }

   private static C_5265_ getGuiTextureLocation(CustomGuiProperties.EnumContainer container) {
      if (container == null) {
         return null;
      } else {
         switch (container) {
            case ANVIL:
               return ANVIL_GUI_TEXTURE;
            case BEACON:
               return BEACON_GUI_TEXTURE;
            case BREWING_STAND:
               return BREWING_STAND_GUI_TEXTURE;
            case CHEST:
               return CHEST_GUI_TEXTURE;
            case CRAFTING:
               return CRAFTING_TABLE_GUI_TEXTURE;
            case DISPENSER:
               return DISPENSER_GUI_TEXTURE;
            case ENCHANTMENT:
               return ENCHANTMENT_TABLE_GUI_TEXTURE;
            case FURNACE:
               return FURNACE_GUI_TEXTURE;
            case HOPPER:
               return HOPPER_GUI_TEXTURE;
            case HORSE:
               return HORSE_GUI_TEXTURE;
            case VILLAGER:
               return VILLAGER_GUI_TEXTURE;
            case SHULKER_BOX:
               return SHULKER_BOX_GUI_TEXTURE;
            case CREATIVE:
               return null;
            case INVENTORY:
               return INVENTORY_GUI_TEXTURE;
            default:
               return null;
         }
      }
   }

   public boolean isValid(String path) {
      if (this.fileName == null || this.fileName.length() <= 0) {
         warn("No name found: " + path);
         return false;
      } else if (this.basePath == null) {
         warn("No base path found: " + path);
         return false;
      } else if (this.container == null) {
         warn("No container found: " + path);
         return false;
      } else if (this.textureLocations.isEmpty()) {
         warn("No texture found: " + path);
         return false;
      } else if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
         warn("Invalid professions or careers: " + path);
         return false;
      } else if (this.variants == VARIANTS_INVALID) {
         warn("Invalid variants: " + path);
         return false;
      } else if (this.colors == COLORS_INVALID) {
         warn("Invalid colors: " + path);
         return false;
      } else {
         return true;
      }
   }

   private static void warn(String str) {
      Config.warn("[CustomGuis] " + str);
   }

   private boolean matchesGeneral(CustomGuiProperties.EnumContainer ec, C_4675_ pos, C_1599_ blockAccess) {
      if (this.container != ec) {
         return false;
      } else {
         if (this.biomes != null) {
            C_1629_ biome = (C_1629_)blockAccess.m_204166_(pos).m_203334_();
            if (!Matches.biome(biome, this.biomes)) {
               return false;
            }
         }

         return this.heights == null || this.heights.isInRange(pos.v());
      }
   }

   public boolean matchesPos(CustomGuiProperties.EnumContainer ec, C_4675_ pos, C_1599_ blockAccess, C_3583_ screen) {
      if (!this.matchesGeneral(ec, pos, blockAccess)) {
         return false;
      } else {
         if (this.nbtName != null) {
            String name = getName(screen);
            if (!this.nbtName.matchesValue(name)) {
               return false;
            }
         }

         switch (ec) {
            case BEACON:
               return this.matchesBeacon(pos, blockAccess);
            case CHEST:
               return this.matchesChest(pos, blockAccess);
            case DISPENSER:
               return this.matchesDispenser(pos, blockAccess);
            case SHULKER_BOX:
               return this.matchesShulker(pos, blockAccess);
            default:
               return true;
         }
      }
   }

   public static String getName(C_3583_ screen) {
      C_4996_ itc = screen.m_96636_();
      return itc == null ? null : itc.getString();
   }

   private boolean matchesBeacon(C_4675_ pos, C_1557_ blockAccess) {
      if (!(blockAccess.c_(pos) instanceof C_1981_ teb)) {
         return false;
      } else {
         if (this.levels != null) {
            if (!Reflector.TileEntityBeacon_levels.exists()) {
               return false;
            }

            int l = Reflector.getFieldValueInt(teb, Reflector.TileEntityBeacon_levels, -1);
            if (!this.levels.isInRange(l)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean matchesChest(C_4675_ pos, C_1557_ blockAccess) {
      C_1991_ te = blockAccess.c_(pos);
      if (te instanceof C_1997_ tec) {
         return this.matchesChest(tec, pos, blockAccess);
      } else {
         return te instanceof C_2007_ teec ? this.matchesEnderChest(teec, pos, blockAccess) : false;
      }
   }

   private boolean matchesChest(C_1997_ tec, C_4675_ pos, C_1557_ blockAccess) {
      C_2064_ blockState = blockAccess.a_(pos);
      C_2085_ chestType = blockState.b(C_1728_.f_51479_) ? (C_2085_)blockState.c(C_1728_.f_51479_) : C_2085_.SINGLE;
      boolean isLarge = chestType != C_2085_.SINGLE;
      boolean isTrapped = tec instanceof C_2035_;
      boolean isChristmas = CustomGuis.isChristmas;
      boolean isEnder = false;
      return this.matchesChest(isLarge, isTrapped, isChristmas, isEnder);
   }

   private boolean matchesEnderChest(C_2007_ teec, C_4675_ pos, C_1557_ blockAccess) {
      return this.matchesChest(false, false, false, true);
   }

   private boolean matchesChest(boolean isLarge, boolean isTrapped, boolean isChristmas, boolean isEnder) {
      if (this.large != null && this.large != isLarge) {
         return false;
      } else if (this.trapped != null && this.trapped != isTrapped) {
         return false;
      } else {
         return this.christmas != null && this.christmas != isChristmas ? false : this.ender == null || this.ender == isEnder;
      }
   }

   private boolean matchesDispenser(C_4675_ pos, C_1557_ blockAccess) {
      if (!(blockAccess.c_(pos) instanceof C_2004_ ted)) {
         return false;
      } else {
         if (this.variants != null) {
            CustomGuiProperties.EnumVariant var = this.getDispenserVariant(ted);
            if (!Config.equalsOne(var, this.variants)) {
               return false;
            }
         }

         return true;
      }
   }

   private CustomGuiProperties.EnumVariant getDispenserVariant(C_2004_ ted) {
      return ted instanceof C_2005_ ? CustomGuiProperties.EnumVariant.DROPPER : CustomGuiProperties.EnumVariant.DISPENSER;
   }

   private boolean matchesShulker(C_4675_ pos, C_1557_ blockAccess) {
      if (!(blockAccess.c_(pos) instanceof C_2021_ tesb)) {
         return false;
      } else {
         if (this.colors != null) {
            C_1353_ col = tesb.m_59701_();
            if (!Config.equalsOne(col, this.colors)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean matchesEntity(CustomGuiProperties.EnumContainer ec, C_507_ entity, C_1599_ blockAccess) {
      if (!this.matchesGeneral(ec, entity.m_20183_(), blockAccess)) {
         return false;
      } else {
         if (this.nbtName != null) {
            String entityName = entity.m_6302_();
            if (!this.nbtName.matchesValue(entityName)) {
               return false;
            }
         }

         switch (ec) {
            case HORSE:
               return this.matchesHorse(entity, blockAccess);
            case VILLAGER:
               return this.matchesVillager(entity, blockAccess);
            default:
               return true;
         }
      }
   }

   private boolean matchesVillager(C_507_ entity, C_1557_ blockAccess) {
      if (!(entity instanceof C_1118_ entityVillager)) {
         return false;
      } else {
         if (this.professions != null) {
            C_1119_ vd = entityVillager.m_7141_();
            C_1121_ vp = vd.m_35571_();
            int level = vd.m_35576_();
            if (!MatchProfession.matchesOne(vp, level, this.professions)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean matchesHorse(C_507_ entity, C_1557_ blockAccess) {
      if (!(entity instanceof C_926_ ah)) {
         return false;
      } else {
         if (this.variants != null) {
            CustomGuiProperties.EnumVariant var = this.getHorseVariant(ah);
            if (!Config.equalsOne(var, this.variants)) {
               return false;
            }
         }

         if (this.colors != null && ah instanceof C_930_ el) {
            C_1353_ col = el.m_30826_();
            if (!Config.equalsOne(col, this.colors)) {
               return false;
            }
         }

         return true;
      }
   }

   private CustomGuiProperties.EnumVariant getHorseVariant(C_926_ entity) {
      if (entity instanceof C_928_) {
         return CustomGuiProperties.EnumVariant.HORSE;
      } else if (entity instanceof C_927_) {
         return CustomGuiProperties.EnumVariant.DONKEY;
      } else if (entity instanceof C_936_) {
         return CustomGuiProperties.EnumVariant.MULE;
      } else {
         return entity instanceof C_930_ ? CustomGuiProperties.EnumVariant.LLAMA : null;
      }
   }

   public CustomGuiProperties.EnumContainer getContainer() {
      return this.container;
   }

   public C_5265_ getTextureLocation(C_5265_ loc) {
      C_5265_ locNew = (C_5265_)this.textureLocations.get(loc);
      return locNew == null ? loc : locNew;
   }

   public String toString() {
      return "name: " + this.fileName + ", container: " + this.container + ", textures: " + this.textureLocations;
   }

   public static enum EnumContainer {
      ANVIL,
      BEACON,
      BREWING_STAND,
      CHEST,
      CRAFTING,
      DISPENSER,
      ENCHANTMENT,
      FURNACE,
      HOPPER,
      HORSE,
      VILLAGER,
      SHULKER_BOX,
      CREATIVE,
      INVENTORY;
   }

   private static enum EnumVariant {
      HORSE,
      DONKEY,
      MULE,
      LLAMA,
      DISPENSER,
      DROPPER;
   }
}
