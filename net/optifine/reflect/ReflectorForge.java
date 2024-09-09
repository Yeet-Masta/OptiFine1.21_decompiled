package net.optifine.reflect;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraftforge.client.model.ForgeFaceData;
import net.optifine.Log;
import net.optifine.util.StrUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class ReflectorForge {
   public static Object EVENT_RESULT_ALLOW = Reflector.getFieldValue(Reflector.Event_Result_ALLOW);
   public static Object EVENT_RESULT_DENY = Reflector.getFieldValue(Reflector.Event_Result_DENY);
   public static Object EVENT_RESULT_DEFAULT = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
   public static final boolean FORGE_ENTITY_CAN_UPDATE = Reflector.IForgeEntity_canUpdate.exists();

   public static void putLaunchBlackboard(String key, Object value) {
      Map blackboard = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);
      if (blackboard != null) {
         blackboard.put(key, value);
      }
   }

   public static InputStream getOptiFineResourceStream(String path) {
      if (!Reflector.OptiFineResourceLocator.exists()) {
         return null;
      } else {
         path = StrUtils.removePrefix(path, "/");
         return (InputStream)Reflector.call(Reflector.OptiFineResourceLocator_getOptiFineResourceStream, path);
      }
   }

   public static ReflectorClass getReflectorClassOptiFineResourceLocator() {
      String className = "optifine.OptiFineResourceLocator";
      return System.getProperties().get(className + ".class") instanceof Class cls ? new ReflectorClass(cls) : new ReflectorClass(className);
   }

   public static boolean calculateFaceWithoutAO(
      BlockAndTintGetter getter,
      net.minecraft.world.level.block.state.BlockState state,
      BlockPos pos,
      net.minecraft.client.renderer.block.model.BakedQuad quad,
      boolean isFaceCubic,
      float[] brightness,
      int[] lightmap
   ) {
      if (quad.hasAmbientOcclusion()) {
         return false;
      } else {
         BlockPos lightmapPos = isFaceCubic ? pos.m_121945_(quad.m_111306_()) : pos;
         brightness[0] = brightness[1] = brightness[2] = brightness[3] = getter.m_7717_(quad.m_111306_(), quad.m_111307_());
         lightmap[0] = lightmap[1] = lightmap[2] = lightmap[3] = net.minecraft.client.renderer.LevelRenderer.m_109537_(getter, state, lightmapPos);
         return true;
      }
   }

   public static MapItemSavedData getMapData(ItemStack stack, Level world) {
      if (Reflector.ForgeHooksClient.exists()) {
         MapItem var10000 = (MapItem)stack.m_41720_();
         return MapItem.m_42853_(stack, world);
      } else {
         return MapItem.m_42853_(stack, world);
      }
   }

   public static String[] getForgeModIds() {
      if (!Reflector.ModList.exists()) {
         return new String[0];
      } else {
         Object modList = Reflector.ModList_get.call();
         List listMods = (List)Reflector.getFieldValue(modList, Reflector.ModList_mods);
         if (listMods == null) {
            return new String[0];
         } else {
            List<String> listModIds = new ArrayList();

            for (Object modContainer : listMods) {
               if (Reflector.ModContainer.isInstance(modContainer)) {
                  String modId = Reflector.callString(modContainer, Reflector.ModContainer_getModId);
                  if (modId != null && !modId.equals("minecraft") && !modId.equals("forge")) {
                     listModIds.add(modId);
                  }
               }
            }

            return (String[])listModIds.toArray(new String[listModIds.size()]);
         }
      }
   }

   public static boolean canDisableShield(ItemStack itemstack, ItemStack itemstack1, Player entityplayer, net.minecraft.world.entity.Mob entityLiving) {
      return Reflector.IForgeItemStack_canDisableShield.exists()
         ? Reflector.callBoolean(itemstack, Reflector.IForgeItemStack_canDisableShield, itemstack1, entityplayer, entityLiving)
         : itemstack.m_41720_() instanceof AxeItem;
   }

   public static Button makeButtonMods(net.minecraft.client.gui.screens.TitleScreen guiMainMenu, int yIn, int rowHeightIn) {
      return !Reflector.ModListScreen_Constructor.exists() ? null : Button.m_253074_(Component.m_237115_("fml.menu.mods"), button -> {
         Screen modListScreen = (Screen)Reflector.ModListScreen_Constructor.newInstance(guiMainMenu);
         Minecraft.m_91087_().m_91152_(modListScreen);
      }).m_252794_(guiMainMenu.f_96543_ / 2 - 100, yIn + rowHeightIn * 2).m_253046_(98, 20).m_253136_();
   }

   public static void setForgeLightPipelineEnabled(boolean value) {
      if (Reflector.ForgeConfig_Client_forgeLightPipelineEnabled.exists()) {
         setConfigClientBoolean(Reflector.ForgeConfig_Client_forgeLightPipelineEnabled, value);
      }
   }

   public static boolean getForgeUseCombinedDepthStencilAttachment() {
      return Reflector.ForgeConfig_Client_useCombinedDepthStencilAttachment.exists()
         ? getConfigClientBoolean(Reflector.ForgeConfig_Client_useCombinedDepthStencilAttachment, false)
         : false;
   }

   public static boolean getForgeCalculateAllNormals() {
      return Reflector.ForgeConfig_Client_calculateAllNormals.exists()
         ? getConfigClientBoolean(Reflector.ForgeConfig_Client_calculateAllNormals, false)
         : false;
   }

   public static boolean getConfigClientBoolean(ReflectorField configField, boolean def) {
      if (!configField.exists()) {
         return def;
      } else {
         Object client = Reflector.ForgeConfig_CLIENT.getValue();
         if (client == null) {
            return def;
         } else {
            Object configValue = Reflector.getFieldValue(client, configField);
            return configValue == null ? def : Reflector.callBoolean(configValue, Reflector.ForgeConfigSpec_ConfigValue_get);
         }
      }
   }

   private static void setConfigClientBoolean(ReflectorField clientField, final boolean value) {
      if (clientField.exists()) {
         Object client = Reflector.ForgeConfig_CLIENT.getValue();
         if (client != null) {
            Object configValue = Reflector.getFieldValue(client, clientField);
            if (configValue != null) {
               Supplier<Boolean> bs = new Supplier<Boolean>() {
                  public Boolean get() {
                     return value;
                  }
               };
               Reflector.setFieldValue(configValue, Reflector.ForgeConfigSpec_ConfigValue_defaultSupplier, bs);
               Object spec = Reflector.getFieldValue(configValue, Reflector.ForgeConfigSpec_ConfigValue_spec);
               if (spec != null) {
                  Reflector.setFieldValue(spec, Reflector.ForgeConfigSpec_childConfig, null);
               }

               Log.dbg("Set ForgeConfig.CLIENT." + clientField.getTargetField().getName() + "=" + value);
            }
         }
      }
   }

   public static boolean canUpdate(Entity entity) {
      return FORGE_ENTITY_CAN_UPDATE ? Reflector.callBoolean(entity, Reflector.IForgeEntity_canUpdate) : true;
   }

   public static boolean isDamageable(Item item, ItemStack stack) {
      return Reflector.IForgeItem_isDamageable1.exists() ? Reflector.callBoolean(item, Reflector.IForgeItem_isDamageable1, stack) : stack.m_41763_();
   }

   public static void fillNormal(int[] faceData, net.minecraft.core.Direction facing, ForgeFaceData data) {
      boolean calculateNormals = false;
      if (Reflector.ForgeFaceData_calculateNormals.exists()) {
         calculateNormals = Reflector.callBoolean(data, Reflector.ForgeFaceData_calculateNormals);
      }

      Vector3f v2;
      if (!calculateNormals && !getForgeCalculateAllNormals()) {
         v2 = new Vector3f((float)facing.m_122436_().m_123341_(), (float)facing.m_122436_().m_123342_(), (float)facing.m_122436_().m_123343_());
      } else {
         Vector3f v1 = getVertexPos(faceData, 3);
         Vector3f t1 = getVertexPos(faceData, 1);
         v2 = getVertexPos(faceData, 2);
         Vector3f t2 = getVertexPos(faceData, 0);
         v1.sub(t1);
         v2.sub(t2);
         v2.cross(v1);
         v2.normalize();
      }

      int x = (byte)Math.round(v2.x() * 127.0F) & 255;
      int y = (byte)Math.round(v2.y() * 127.0F) & 255;
      int z = (byte)Math.round(v2.z() * 127.0F) & 255;
      int normal = x | y << 8 | z << 16;
      int step = faceData.length / 4;

      for (int i = 0; i < 4; i++) {
         faceData[i * step + 7] = normal;
      }
   }

   private static Vector3f getVertexPos(int[] data, int vertex) {
      int step = data.length / 4;
      int idx = vertex * step;
      float x = Float.intBitsToFloat(data[idx]);
      float y = Float.intBitsToFloat(data[idx + 1]);
      float z = Float.intBitsToFloat(data[idx + 2]);
      return new Vector3f(x, y, z);
   }

   public static void postModLoaderEvent(ReflectorConstructor constr, Object... params) {
      Object event = Reflector.newInstance(constr, params);
      if (event != null) {
         postModLoaderEvent(event);
      }
   }

   public static void postModLoaderEvent(Object event) {
      if (event != null) {
         Object modLoader = Reflector.ModLoader_get.call();
         if (modLoader != null) {
            Reflector.callVoid(modLoader, Reflector.ModLoader_postEvent, event);
         }
      }
   }

   public static void dispatchRenderStageS(
      ReflectorField stageField,
      net.minecraft.client.renderer.LevelRenderer levelRenderer,
      Matrix4f matrixView,
      Matrix4f matrixProjection,
      int ticks,
      net.minecraft.client.Camera camera,
      net.minecraft.client.renderer.culling.Frustum frustum
   ) {
      if (Reflector.RenderLevelStageEvent_dispatch.exists()) {
         if (stageField.exists()) {
            Object stage = stageField.getValue();
            Reflector.call(stage, Reflector.RenderLevelStageEvent_dispatch, levelRenderer, matrixView, matrixProjection, ticks, camera, frustum);
         }
      }
   }
}
