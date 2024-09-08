package net.optifine.reflect;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_1321_;
import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1398_;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_2771_;
import net.minecraft.src.C_3373_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3451_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_3588_;
import net.minecraft.src.C_4134_;
import net.minecraft.src.C_4196_;
import net.minecraft.src.C_4273_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_526_;
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
      C_1557_ getter, C_2064_ state, C_4675_ pos, C_4196_ quad, boolean isFaceCubic, float[] brightness, int[] lightmap
   ) {
      if (quad.hasAmbientOcclusion()) {
         return false;
      } else {
         C_4675_ lightmapPos = isFaceCubic ? pos.m_121945_(quad.m_111306_()) : pos;
         brightness[0] = brightness[1] = brightness[2] = brightness[3] = getter.m_7717_(quad.m_111306_(), quad.m_111307_());
         lightmap[0] = lightmap[1] = lightmap[2] = lightmap[3] = C_4134_.m_109537_(getter, state, lightmapPos);
         return true;
      }
   }

   public static C_2771_ getMapData(C_1391_ stack, C_1596_ world) {
      if (Reflector.ForgeHooksClient.exists()) {
         C_1398_ var10000 = (C_1398_)stack.m_41720_();
         return C_1398_.m_42853_(stack, world);
      } else {
         return C_1398_.m_42853_(stack, world);
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

   public static boolean canDisableShield(C_1391_ itemstack, C_1391_ itemstack1, C_1141_ entityplayer, C_526_ entityLiving) {
      return Reflector.IForgeItemStack_canDisableShield.exists()
         ? Reflector.callBoolean(itemstack, Reflector.IForgeItemStack_canDisableShield, itemstack1, entityplayer, entityLiving)
         : itemstack.m_41720_() instanceof C_1321_;
   }

   public static C_3451_ makeButtonMods(C_3588_ guiMainMenu, int yIn, int rowHeightIn) {
      return !Reflector.ModListScreen_Constructor.exists() ? null : C_3451_.m_253074_(C_4996_.m_237115_("fml.menu.mods"), button -> {
         C_3583_ modListScreen = (C_3583_)Reflector.ModListScreen_Constructor.newInstance(guiMainMenu);
         C_3391_.m_91087_().m_91152_(modListScreen);
      }).m_252794_(guiMainMenu.m / 2 - 100, yIn + rowHeightIn * 2).m_253046_(98, 20).m_253136_();
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

   public static boolean canUpdate(C_507_ entity) {
      return FORGE_ENTITY_CAN_UPDATE ? Reflector.callBoolean(entity, Reflector.IForgeEntity_canUpdate) : true;
   }

   public static boolean isDamageable(C_1381_ item, C_1391_ stack) {
      return Reflector.IForgeItem_isDamageable1.exists() ? Reflector.callBoolean(item, Reflector.IForgeItem_isDamageable1, stack) : stack.m_41763_();
   }

   public static void fillNormal(int[] faceData, C_4687_ facing, ForgeFaceData data) {
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
      ReflectorField stageField, C_4134_ levelRenderer, Matrix4f matrixView, Matrix4f matrixProjection, int ticks, C_3373_ camera, C_4273_ frustum
   ) {
      if (Reflector.RenderLevelStageEvent_dispatch.exists()) {
         if (stageField.exists()) {
            Object stage = stageField.getValue();
            Reflector.call(stage, Reflector.RenderLevelStageEvent_dispatch, levelRenderer, matrixView, matrixProjection, ticks, camera, frustum);
         }
      }
   }
}
