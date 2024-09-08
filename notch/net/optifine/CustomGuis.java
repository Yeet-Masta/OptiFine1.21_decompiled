package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import net.minecraft.src.C_1599_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_3624_;
import net.minecraft.src.C_3626_;
import net.minecraft.src.C_3627_;
import net.minecraft.src.C_3644_;
import net.minecraft.src.C_3648_;
import net.minecraft.src.C_3650_;
import net.minecraft.src.C_3652_;
import net.minecraft.src.C_3656_;
import net.minecraft.src.C_3659_;
import net.minecraft.src.C_3660_;
import net.minecraft.src.C_3662_;
import net.minecraft.src.C_3663_;
import net.minecraft.src.C_3664_;
import net.minecraft.src.C_3673_;
import net.minecraft.src.C_3677_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_50_;
import net.minecraft.src.C_51_;
import net.minecraft.src.C_5265_;
import net.optifine.override.PlayerControllerOF;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;

public class CustomGuis {
   private static C_3391_ mc = Config.getMinecraft();
   private static PlayerControllerOF playerControllerOF = null;
   private static CustomGuiProperties[][] guiProperties = null;
   public static boolean isChristmas = isChristmas();

   public static C_5265_ getTextureLocation(C_5265_ loc) {
      if (guiProperties == null) {
         return loc;
      } else {
         C_3583_ screen = mc.f_91080_;
         if (!(screen instanceof C_3624_)) {
            return loc;
         } else if (!loc.m_135827_().equals("minecraft") || !loc.m_135815_().startsWith("textures/gui/")) {
            return loc;
         } else if (playerControllerOF == null) {
            return loc;
         } else {
            C_1599_ world = mc.f_91073_;
            if (world == null) {
               return loc;
            } else if (screen instanceof C_3652_) {
               return getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, mc.f_91074_.do(), world, loc, screen);
            } else if (screen instanceof C_3664_) {
               return getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, mc.f_91074_.do(), world, loc, screen);
            } else {
               C_4675_ pos = playerControllerOF.getLastClickBlockPos();
               if (pos != null) {
                  if (screen instanceof C_3626_) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, pos, world, loc, screen);
                  }

                  if (screen instanceof C_3627_) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.BEACON, pos, world, loc, screen);
                  }

                  if (screen instanceof C_3644_) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, pos, world, loc, screen);
                  }

                  if (screen instanceof C_3648_) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.CHEST, pos, world, loc, screen);
                  }

                  if (screen instanceof C_3650_) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, pos, world, loc, screen);
                  }

                  if (screen instanceof C_3656_) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, pos, world, loc, screen);
                  }

                  if (screen instanceof C_3659_) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, pos, world, loc, screen);
                  }

                  if (screen instanceof C_3660_) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, pos, world, loc, screen);
                  }

                  if (screen instanceof C_3662_) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, pos, world, loc, screen);
                  }

                  if (screen instanceof C_3677_) {
                     return getTexturePos(CustomGuiProperties.EnumContainer.SHULKER_BOX, pos, world, loc, screen);
                  }
               }

               C_507_ entity = playerControllerOF.getLastClickEntity();
               if (entity != null) {
                  if (screen instanceof C_3663_) {
                     return getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, entity, world, loc);
                  }

                  if (screen instanceof C_3673_) {
                     return getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, entity, world, loc);
                  }
               }

               return loc;
            }
         }
      }
   }

   private static C_5265_ getTexturePos(CustomGuiProperties.EnumContainer container, C_4675_ pos, C_1599_ blockAccess, C_5265_ loc, C_3583_ screen) {
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

   private static C_5265_ getTextureEntity(CustomGuiProperties.EnumContainer container, C_507_ entity, C_1599_ blockAccess, C_5265_ loc) {
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

   public static void update() {
      guiProperties = null;
      if (Config.isCustomGuis()) {
         List<List<CustomGuiProperties>> listProps = new ArrayList();
         C_50_[] rps = Config.getResourcePacks();

         for (int i = rps.length - 1; i >= 0; i--) {
            C_50_ rp = rps[i];
            update(rp, listProps);
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

   private static void update(C_50_ rp, List<List<CustomGuiProperties>> listProps) {
      String[] paths = ResUtils.collectFiles(rp, "optifine/gui/container/", ".properties", null);
      Arrays.sort(paths);

      for (int i = 0; i < paths.length; i++) {
         String name = paths[i];
         Config.dbg("CustomGuis: " + name);

         try {
            C_5265_ locFile = new C_5265_(name);
            InputStream in = Config.getResourceStream(rp, C_51_.CLIENT_RESOURCES, locFile);
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
