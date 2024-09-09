package net.optifine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
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
   private EnumContainer container = null;
   private Map textureLocations = null;
   private NbtTagValue nbtName = null;
   private BiomeId[] biomes = null;
   private RangeListInt heights = null;
   private Boolean large = null;
   private Boolean trapped = null;
   private Boolean christmas = null;
   private Boolean ender = null;
   private RangeListInt levels = null;
   private MatchProfession[] professions = null;
   private EnumVariant[] variants = null;
   private DyeColor[] colors = null;
   private static final EnumVariant[] VARIANTS_HORSE;
   private static final EnumVariant[] VARIANTS_DISPENSER;
   private static final EnumVariant[] VARIANTS_INVALID;
   private static final DyeColor[] COLORS_INVALID;
   private static final ResourceLocation ANVIL_GUI_TEXTURE;
   private static final ResourceLocation BEACON_GUI_TEXTURE;
   private static final ResourceLocation BREWING_STAND_GUI_TEXTURE;
   private static final ResourceLocation CHEST_GUI_TEXTURE;
   private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURE;
   private static final ResourceLocation HORSE_GUI_TEXTURE;
   private static final ResourceLocation DISPENSER_GUI_TEXTURE;
   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE;
   private static final ResourceLocation FURNACE_GUI_TEXTURE;
   private static final ResourceLocation HOPPER_GUI_TEXTURE;
   private static final ResourceLocation INVENTORY_GUI_TEXTURE;
   private static final ResourceLocation SHULKER_BOX_GUI_TEXTURE;
   private static final ResourceLocation VILLAGER_GUI_TEXTURE;

   public CustomGuiProperties(Properties props, String path) {
      ConnectedParser cp = new ConnectedParser("CustomGuis");
      this.fileName = cp.parseName(path);
      this.basePath = cp.parseBasePath(path);
      this.container = (EnumContainer)cp.parseEnum(props.getProperty("container"), CustomGuiProperties.EnumContainer.values(), "container");
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
      EnumVariant[] vars = getContainerVariants(this.container);
      this.variants = (EnumVariant[])cp.parseEnums(props.getProperty("variants"), vars, "variants", VARIANTS_INVALID);
      this.colors = parseEnumDyeColors(props.getProperty("colors"));
   }

   private static EnumVariant[] getContainerVariants(EnumContainer cont) {
      if (cont == CustomGuiProperties.EnumContainer.HORSE) {
         return VARIANTS_HORSE;
      } else {
         return cont == CustomGuiProperties.EnumContainer.DISPENSER ? VARIANTS_DISPENSER : new EnumVariant[0];
      }
   }

   private static DyeColor[] parseEnumDyeColors(String str) {
      if (str == null) {
         return null;
      } else {
         str = str.toLowerCase();
         String[] tokens = Config.tokenize(str, " ");
         DyeColor[] cols = new DyeColor[tokens.length];

         for(int i = 0; i < tokens.length; ++i) {
            String token = tokens[i];
            DyeColor col = parseEnumDyeColor(token);
            if (col == null) {
               warn("Invalid color: " + token);
               return COLORS_INVALID;
            }

            cols[i] = col;
         }

         return cols;
      }
   }

   private static DyeColor parseEnumDyeColor(String str) {
      if (str == null) {
         return null;
      } else {
         DyeColor[] colors = DyeColor.values();

         for(int i = 0; i < colors.length; ++i) {
            DyeColor enumDyeColor = colors[i];
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

   private static ResourceLocation parseTextureLocation(String str, String basePath) {
      if (str == null) {
         return null;
      } else {
         str = str.trim();
         String tex = TextureUtils.fixResourcePath(str, basePath);
         if (!tex.endsWith(".png")) {
            tex = tex + ".png";
         }

         return new ResourceLocation(basePath + "/" + tex);
      }
   }

   private static Map parseTextureLocations(Properties props, String property, EnumContainer container, String pathPrefix, String basePath) {
      Map map = new HashMap();
      String propVal = props.getProperty(property);
      if (propVal != null) {
         ResourceLocation locKey = getGuiTextureLocation(container);
         ResourceLocation locVal = parseTextureLocation(propVal, basePath);
         if (locKey != null && locVal != null) {
            map.put(locKey, locVal);
         }
      }

      String keyPrefix = property + ".";
      Set keys = props.keySet();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         if (key.startsWith(keyPrefix)) {
            String pathRel = key.substring(keyPrefix.length());
            pathRel = pathRel.replace('\\', '/');
            pathRel = StrUtils.removePrefixSuffix(pathRel, "/", ".png");
            String path = pathPrefix + pathRel + ".png";
            String val = props.getProperty(key);
            ResourceLocation locKey = new ResourceLocation(path);
            ResourceLocation locVal = parseTextureLocation(val, basePath);
            map.put(locKey, locVal);
         }
      }

      return map;
   }

   private static ResourceLocation getGuiTextureLocation(EnumContainer container) {
      if (container == null) {
         return null;
      } else {
         switch (container.ordinal()) {
            case 0:
               return ANVIL_GUI_TEXTURE;
            case 1:
               return BEACON_GUI_TEXTURE;
            case 2:
               return BREWING_STAND_GUI_TEXTURE;
            case 3:
               return CHEST_GUI_TEXTURE;
            case 4:
               return CRAFTING_TABLE_GUI_TEXTURE;
            case 5:
               return DISPENSER_GUI_TEXTURE;
            case 6:
               return ENCHANTMENT_TABLE_GUI_TEXTURE;
            case 7:
               return FURNACE_GUI_TEXTURE;
            case 8:
               return HOPPER_GUI_TEXTURE;
            case 9:
               return HORSE_GUI_TEXTURE;
            case 10:
               return VILLAGER_GUI_TEXTURE;
            case 11:
               return SHULKER_BOX_GUI_TEXTURE;
            case 12:
               return null;
            case 13:
               return INVENTORY_GUI_TEXTURE;
            default:
               return null;
         }
      }
   }

   public boolean isValid(String path) {
      if (this.fileName != null && this.fileName.length() > 0) {
         if (this.basePath == null) {
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
      } else {
         warn("No name found: " + path);
         return false;
      }
   }

   private static void warn(String str) {
      Config.warn("[CustomGuis] " + str);
   }

   private boolean matchesGeneral(EnumContainer ec, BlockPos pos, LevelReader blockAccess) {
      if (this.container != ec) {
         return false;
      } else {
         if (this.biomes != null) {
            Biome biome = (Biome)blockAccess.m_204166_(pos).m_203334_();
            if (!Matches.biome(biome, this.biomes)) {
               return false;
            }
         }

         return this.heights == null || this.heights.isInRange(pos.m_123342_());
      }
   }

   public boolean matchesPos(EnumContainer ec, BlockPos pos, LevelReader blockAccess, Screen screen) {
      if (!this.matchesGeneral(ec, pos, blockAccess)) {
         return false;
      } else {
         if (this.nbtName != null) {
            String name = getName(screen);
            if (!this.nbtName.matchesValue(name)) {
               return false;
            }
         }

         switch (ec.ordinal()) {
            case 1:
               return this.matchesBeacon(pos, blockAccess);
            case 3:
               return this.matchesChest(pos, blockAccess);
            case 5:
               return this.matchesDispenser(pos, blockAccess);
            case 11:
               return this.matchesShulker(pos, blockAccess);
            default:
               return true;
         }
      }
   }

   public static String getName(Screen screen) {
      Component itc = screen.m_96636_();
      return itc == null ? null : itc.getString();
   }

   private boolean matchesBeacon(BlockPos pos, BlockAndTintGetter blockAccess) {
      BlockEntity te = blockAccess.m_7702_(pos);
      if (!(te instanceof BeaconBlockEntity teb)) {
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

   private boolean matchesChest(BlockPos pos, BlockAndTintGetter blockAccess) {
      BlockEntity te = blockAccess.m_7702_(pos);
      if (te instanceof ChestBlockEntity tec) {
         return this.matchesChest(tec, pos, blockAccess);
      } else if (te instanceof EnderChestBlockEntity teec) {
         return this.matchesEnderChest(teec, pos, blockAccess);
      } else {
         return false;
      }
   }

   private boolean matchesChest(ChestBlockEntity tec, BlockPos pos, BlockAndTintGetter blockAccess) {
      BlockState blockState = blockAccess.m_8055_(pos);
      ChestType chestType = blockState.m_61138_(ChestBlock.f_51479_) ? (ChestType)blockState.m_61143_(ChestBlock.f_51479_) : ChestType.SINGLE;
      boolean isLarge = chestType != ChestType.SINGLE;
      boolean isTrapped = tec instanceof TrappedChestBlockEntity;
      boolean isChristmas = CustomGuis.isChristmas;
      boolean isEnder = false;
      return this.matchesChest(isLarge, isTrapped, isChristmas, isEnder);
   }

   private boolean matchesEnderChest(EnderChestBlockEntity teec, BlockPos pos, BlockAndTintGetter blockAccess) {
      return this.matchesChest(false, false, false, true);
   }

   private boolean matchesChest(boolean isLarge, boolean isTrapped, boolean isChristmas, boolean isEnder) {
      if (this.large != null && this.large != isLarge) {
         return false;
      } else if (this.trapped != null && this.trapped != isTrapped) {
         return false;
      } else if (this.christmas != null && this.christmas != isChristmas) {
         return false;
      } else {
         return this.ender == null || this.ender == isEnder;
      }
   }

   private boolean matchesDispenser(BlockPos pos, BlockAndTintGetter blockAccess) {
      BlockEntity te = blockAccess.m_7702_(pos);
      if (!(te instanceof DispenserBlockEntity ted)) {
         return false;
      } else {
         if (this.variants != null) {
            EnumVariant var = this.getDispenserVariant(ted);
            if (!Config.equalsOne(var, this.variants)) {
               return false;
            }
         }

         return true;
      }
   }

   private EnumVariant getDispenserVariant(DispenserBlockEntity ted) {
      return ted instanceof DropperBlockEntity ? CustomGuiProperties.EnumVariant.DROPPER : CustomGuiProperties.EnumVariant.DISPENSER;
   }

   private boolean matchesShulker(BlockPos pos, BlockAndTintGetter blockAccess) {
      BlockEntity te = blockAccess.m_7702_(pos);
      if (!(te instanceof ShulkerBoxBlockEntity tesb)) {
         return false;
      } else {
         if (this.colors != null) {
            DyeColor col = tesb.m_59701_();
            if (!Config.equalsOne(col, this.colors)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean matchesEntity(EnumContainer ec, Entity entity, LevelReader blockAccess) {
      if (!this.matchesGeneral(ec, entity.m_20183_(), blockAccess)) {
         return false;
      } else {
         if (this.nbtName != null) {
            String entityName = entity.m_6302_();
            if (!this.nbtName.matchesValue(entityName)) {
               return false;
            }
         }

         switch (ec.ordinal()) {
            case 9:
               return this.matchesHorse(entity, blockAccess);
            case 10:
               return this.matchesVillager(entity, blockAccess);
            default:
               return true;
         }
      }
   }

   private boolean matchesVillager(Entity entity, BlockAndTintGetter blockAccess) {
      if (!(entity instanceof Villager entityVillager)) {
         return false;
      } else {
         if (this.professions != null) {
            VillagerData vd = entityVillager.m_7141_();
            VillagerProfession vp = vd.m_35571_();
            int level = vd.m_35576_();
            if (!MatchProfession.matchesOne(vp, level, this.professions)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean matchesHorse(Entity entity, BlockAndTintGetter blockAccess) {
      if (!(entity instanceof AbstractHorse ah)) {
         return false;
      } else {
         if (this.variants != null) {
            EnumVariant var = this.getHorseVariant(ah);
            if (!Config.equalsOne(var, this.variants)) {
               return false;
            }
         }

         if (this.colors != null && ah instanceof Llama el) {
            DyeColor col = el.m_30826_();
            if (!Config.equalsOne(col, this.colors)) {
               return false;
            }
         }

         return true;
      }
   }

   private EnumVariant getHorseVariant(AbstractHorse entity) {
      if (entity instanceof Horse) {
         return CustomGuiProperties.EnumVariant.HORSE;
      } else if (entity instanceof Donkey) {
         return CustomGuiProperties.EnumVariant.DONKEY;
      } else if (entity instanceof Mule) {
         return CustomGuiProperties.EnumVariant.MULE;
      } else {
         return entity instanceof Llama ? CustomGuiProperties.EnumVariant.LLAMA : null;
      }
   }

   public EnumContainer getContainer() {
      return this.container;
   }

   public ResourceLocation getTextureLocation(ResourceLocation loc) {
      ResourceLocation locNew = (ResourceLocation)this.textureLocations.get(loc);
      return locNew == null ? loc : locNew;
   }

   public String toString() {
      String var10000 = this.fileName;
      return "name: " + var10000 + ", container: " + String.valueOf(this.container) + ", textures: " + String.valueOf(this.textureLocations);
   }

   static {
      VARIANTS_HORSE = new EnumVariant[]{CustomGuiProperties.EnumVariant.HORSE, CustomGuiProperties.EnumVariant.DONKEY, CustomGuiProperties.EnumVariant.MULE, CustomGuiProperties.EnumVariant.LLAMA};
      VARIANTS_DISPENSER = new EnumVariant[]{CustomGuiProperties.EnumVariant.DISPENSER, CustomGuiProperties.EnumVariant.DROPPER};
      VARIANTS_INVALID = new EnumVariant[0];
      COLORS_INVALID = new DyeColor[0];
      ANVIL_GUI_TEXTURE = new ResourceLocation("textures/gui/container/anvil.png");
      BEACON_GUI_TEXTURE = new ResourceLocation("textures/gui/container/beacon.png");
      BREWING_STAND_GUI_TEXTURE = new ResourceLocation("textures/gui/container/brewing_stand.png");
      CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
      CRAFTING_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");
      HORSE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/horse.png");
      DISPENSER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");
      ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
      FURNACE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
      HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
      INVENTORY_GUI_TEXTURE = new ResourceLocation("textures/gui/container/inventory.png");
      SHULKER_BOX_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
      VILLAGER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager2.png");
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

      // $FF: synthetic method
      private static EnumContainer[] $values() {
         return new EnumContainer[]{ANVIL, BEACON, BREWING_STAND, CHEST, CRAFTING, DISPENSER, ENCHANTMENT, FURNACE, HOPPER, HORSE, VILLAGER, SHULKER_BOX, CREATIVE, INVENTORY};
      }
   }

   private static enum EnumVariant {
      HORSE,
      DONKEY,
      MULE,
      LLAMA,
      DISPENSER,
      DROPPER;

      // $FF: synthetic method
      private static EnumVariant[] $values() {
         return new EnumVariant[]{HORSE, DONKEY, MULE, LLAMA, DISPENSER, DROPPER};
      }
   }
}
