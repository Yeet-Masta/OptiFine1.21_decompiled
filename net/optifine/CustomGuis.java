package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.DispenserScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.client.gui.screens.inventory.HopperScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.optifine.override.PlayerControllerOF;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;

public class CustomGuis {
   private static Minecraft f_303183_ = Config.getMinecraft();
   private static PlayerControllerOF playerControllerOF = null;
   private static CustomGuiProperties[][] guiProperties = null;
   public static boolean isChristmas = isChristmas();

   public static ResourceLocation getTextureLocation(ResourceLocation loc) {
      if (guiProperties == null) {
         return loc;
      } else {
         Screen screen = f_303183_.f_91080_;
         if (!(screen instanceof AbstractContainerScreen)) {
            return loc;
         } else if (!loc.m_135827_().equals("minecraft") || !loc.m_135815_().startsWith("textures/gui/")) {
            return loc;
         } else if (playerControllerOF == null) {
            return loc;
         } else {
            LevelReader world = f_303183_.f_91073_;
            if (world == null) {
               return loc;
            } else if (screen instanceof CreativeModeInventoryScreen) {
               return getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, f_303183_.f_91074_.m_20183_(), world, loc, screen);
            } else if (screen instanceof InventoryScreen) {
               return getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, f_303183_.f_91074_.m_20183_(), world, loc, screen);
            } else {
               BlockPos pos = playerControllerOF.getLastClickBlockPos();
               if (pos != null) {
                  if (screen instanceof AnvilScreen) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, pos, world, loc, screen);
                  }

                  if (screen instanceof BeaconScreen) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.BEACON, pos, world, loc, screen);
                  }

                  if (screen instanceof BrewingStandScreen) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, pos, world, loc, screen);
                  }

                  if (screen instanceof ContainerScreen) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.CHEST, pos, world, loc, screen);
                  }

                  if (screen instanceof CraftingScreen) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, pos, world, loc, screen);
                  }

                  if (screen instanceof DispenserScreen) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, pos, world, loc, screen);
                  }

                  if (screen instanceof EnchantmentScreen) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, pos, world, loc, screen);
                  }

                  if (screen instanceof FurnaceScreen) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, pos, world, loc, screen);
                  }

                  if (screen instanceof HopperScreen) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, pos, world, loc, screen);
                  }

                  if (screen instanceof ShulkerBoxScreen) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.SHULKER_BOX, pos, world, loc, screen);
                  }
               }

               Entity entity = playerControllerOF.getLastClickEntity();
               if (entity != null) {
                  if (screen instanceof HorseInventoryScreen) {
                     return getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, entity, world, loc);
                  }

                  if (screen instanceof MerchantScreen) {
                     return getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, entity, world, loc);
                  }
               }

               return loc;
            }
         }
      }
   }

   private static ResourceLocation getTexturePos(
      CustomGuiProperties.EnumContainer container, BlockPos pos, LevelReader blockAccess, ResourceLocation loc, Screen screen
   ) {
      CustomGuiProperties[] props = guiProperties[container.ordinal()];
      if (props == null) {
         return loc;
      } else {
         for (int i = 0; i < props.length; i++) {
            CustomGuiProperties prop = props[i];
            if (prop.matchesPos(container, pos, blockAccess, screen)) {
               return prop.getTextureLocation(loc);
            }
         }

         return loc;
      }
   }

   private static ResourceLocation getTextureEntity(CustomGuiProperties.EnumContainer container, Entity entity, LevelReader blockAccess, ResourceLocation loc) {
      CustomGuiProperties[] props = guiProperties[container.ordinal()];
      if (props == null) {
         return loc;
      } else {
         for (int i = 0; i < props.length; i++) {
            CustomGuiProperties prop = props[i];
            if (prop.matchesEntity(container, entity, blockAccess)) {
               return prop.getTextureLocation(loc);
            }
         }

         return loc;
      }
   }

   public static void m_252999_() {
      guiProperties = null;
      if (Config.isCustomGuis()) {
         List<List<CustomGuiProperties>> listProps = new ArrayList();
         PackResources[] rps = Config.getResourcePacks();

         for (int i = rps.length - 1; i >= 0; i--) {
            PackResources rp = rps[i];
            m_252999_(rp, listProps);
         }

         guiProperties = propertyListToArray(listProps);
      }
   }

   private static CustomGuiProperties[][] propertyListToArray(List<List<CustomGuiProperties>> listProps) {
      if (listProps.isEmpty()) {
         return null;
      } else {
         CustomGuiProperties[][] cgps = new CustomGuiProperties[CustomGuiProperties.EnumContainer.values().length][];

         for (int i = 0; i < cgps.length; i++) {
            if (listProps.size() > i) {
               List<CustomGuiProperties> subList = (List<CustomGuiProperties>)listProps.get(i);
               if (subList != null) {
                  CustomGuiProperties[] subArr = (CustomGuiProperties[])subList.toArray(new CustomGuiProperties[subList.size()]);
                  cgps[i] = subArr;
               }
            }
         }

         return cgps;
      }
   }

   private static void m_252999_(PackResources rp, List<List<CustomGuiProperties>> listProps) {
      String[] paths = ResUtils.collectFiles(rp, "optifine/gui/container/", ".properties", null);
      Arrays.m_277065_(paths);

      for (int i = 0; i < paths.length; i++) {
         String name = paths[i];
         Config.dbg("CustomGuis: " + name);

         try {
            ResourceLocation locFile = new ResourceLocation(name);
            InputStream in = Config.getResourceStream(rp, PackType.CLIENT_RESOURCES, locFile);
            if (in == null) {
               Config.warn("CustomGuis file not found: " + name);
            } else {
               Properties props = new PropertiesOrdered();
               props.load(in);
               in.close();
               CustomGuiProperties cgp = new CustomGuiProperties(props, name);
               if (cgp.isValid(name)) {
                  addToList(cgp, listProps);
               }
            }
         } catch (FileNotFoundException var9) {
            Config.warn("CustomGuis file not found: " + name);
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }
   }

   private static void addToList(CustomGuiProperties cgp, List<List<CustomGuiProperties>> listProps) {
      if (cgp.getContainer() == null) {
         warn("Invalid container: " + cgp.getContainer());
      } else {
         int indexContainer = cgp.getContainer().ordinal();

         while (listProps.size() <= indexContainer) {
            listProps.add(null);
         }

         List<CustomGuiProperties> subList = (List<CustomGuiProperties>)listProps.get(indexContainer);
         if (subList == null) {
            subList = new ArrayList();
            listProps.set(indexContainer, subList);
         }

         subList.add(cgp);
      }
   }

   public static PlayerControllerOF getPlayerControllerOF() {
      return playerControllerOF;
   }

   public static void setPlayerControllerOF(PlayerControllerOF playerControllerOF) {
      CustomGuis.playerControllerOF = playerControllerOF;
   }

   private static boolean isChristmas() {
      Calendar calendar = Calendar.getInstance();
      return calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26;
   }

   private static void warn(String str) {
      Config.warn("[CustomGuis] " + str);
   }
}
