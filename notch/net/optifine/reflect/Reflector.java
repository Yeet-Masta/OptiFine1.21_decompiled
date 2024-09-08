package net.optifine.reflect;

import com.google.common.collect.ImmutableMap.Builder;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.src.C_1151_;
import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1407_;
import net.minecraft.src.C_140974_;
import net.minecraft.src.C_140_;
import net.minecraft.src.C_141647_;
import net.minecraft.src.C_141648_;
import net.minecraft.src.C_141653_;
import net.minecraft.src.C_1440_;
import net.minecraft.src.C_1525_;
import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1598_;
import net.minecraft.src.C_1599_;
import net.minecraft.src.C_17_;
import net.minecraft.src.C_1976_;
import net.minecraft.src.C_1980_;
import net.minecraft.src.C_1981_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_2116_;
import net.minecraft.src.C_213398_;
import net.minecraft.src.C_243526_;
import net.minecraft.src.C_260365_;
import net.minecraft.src.C_262715_;
import net.minecraft.src.C_271025_;
import net.minecraft.src.C_301905_;
import net.minecraft.src.C_301983_;
import net.minecraft.src.C_313555_;
import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3373_;
import net.minecraft.src.C_3387_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3423_;
import net.minecraft.src.C_3429_;
import net.minecraft.src.C_3451_;
import net.minecraft.src.C_3509_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_3588_;
import net.minecraft.src.C_3659_;
import net.minecraft.src.C_3799_;
import net.minecraft.src.C_3801_;
import net.minecraft.src.C_3802_;
import net.minecraft.src.C_3804_;
import net.minecraft.src.C_3806_;
import net.minecraft.src.C_3807_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_3820_;
import net.minecraft.src.C_3821_;
import net.minecraft.src.C_3824_;
import net.minecraft.src.C_3826_;
import net.minecraft.src.C_3827_;
import net.minecraft.src.C_3832_;
import net.minecraft.src.C_3833_;
import net.minecraft.src.C_3835_;
import net.minecraft.src.C_3837_;
import net.minecraft.src.C_3838_;
import net.minecraft.src.C_3842_;
import net.minecraft.src.C_3850_;
import net.minecraft.src.C_3852_;
import net.minecraft.src.C_3858_;
import net.minecraft.src.C_3859_;
import net.minecraft.src.C_3864_;
import net.minecraft.src.C_3865_;
import net.minecraft.src.C_3866_;
import net.minecraft.src.C_3869_;
import net.minecraft.src.C_3875_;
import net.minecraft.src.C_3877_;
import net.minecraft.src.C_3878_;
import net.minecraft.src.C_3879_;
import net.minecraft.src.C_3880_;
import net.minecraft.src.C_3885_;
import net.minecraft.src.C_3888_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4085_;
import net.minecraft.src.C_4134_;
import net.minecraft.src.C_4139_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4182_;
import net.minecraft.src.C_4196_;
import net.minecraft.src.C_4200_;
import net.minecraft.src.C_4217_;
import net.minecraft.src.C_4238_;
import net.minecraft.src.C_4241_;
import net.minecraft.src.C_4242_;
import net.minecraft.src.C_4247_;
import net.minecraft.src.C_4248_;
import net.minecraft.src.C_4249_;
import net.minecraft.src.C_4250_;
import net.minecraft.src.C_4252_;
import net.minecraft.src.C_4253_;
import net.minecraft.src.C_4273_;
import net.minecraft.src.C_4313_;
import net.minecraft.src.C_4325_;
import net.minecraft.src.C_4326_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4332_;
import net.minecraft.src.C_4353_;
import net.minecraft.src.C_4355_;
import net.minecraft.src.C_4357_;
import net.minecraft.src.C_4360_;
import net.minecraft.src.C_4362_;
import net.minecraft.src.C_4379_;
import net.minecraft.src.C_4385_;
import net.minecraft.src.C_4397_;
import net.minecraft.src.C_4402_;
import net.minecraft.src.C_4413_;
import net.minecraft.src.C_4442_;
import net.minecraft.src.C_4457_;
import net.minecraft.src.C_4535_;
import net.minecraft.src.C_4540_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5225_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_5264_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_5422_;
import net.minecraft.src.C_970_;
import net.minecraft.src.C_976_;
import net.minecraft.src.C_141742_.C_141743_;
import net.minecraft.src.C_4326_.C_4327_;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.ForgeFaceData;
import net.minecraftforge.eventbus.api.Event;
import net.optifine.Log;
import net.optifine.util.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;

public class Reflector {
   private static final Logger LOGGER = LogManager.getLogger();
   private static boolean logForge = registerResolvable("*** Reflector Forge ***");
   public static ReflectorClass BrandingControl = new ReflectorClass("net.minecraftforge.internal.BrandingControl");
   public static ReflectorMethod BrandingControl_getBrandings = new ReflectorMethod(BrandingControl, "getBrandings");
   public static ReflectorMethod BrandingControl_getClientBranding = new ReflectorMethod(BrandingControl, "getClientBranding");
   public static ReflectorMethod BrandingControl_forEachLine = new ReflectorMethod(BrandingControl, "forEachLine");
   public static ReflectorMethod BrandingControl_forEachAboveCopyrightLine = new ReflectorMethod(BrandingControl, "forEachAboveCopyrightLine");
   public static ReflectorClass IClientBlockExtensions = new ReflectorClass("net.minecraftforge.client.extensions.common.IClientBlockExtensions");
   public static ReflectorMethod IClientBlockExtensions_ofBS = IClientBlockExtensions.makeMethod("of", new Class[]{C_2064_.class});
   public static ReflectorClass IClientItemExtensions = new ReflectorClass("net.minecraftforge.client.extensions.common.IClientItemExtensions");
   public static ReflectorMethod IClientItemExtensions_ofIS = IClientItemExtensions.makeMethod("of", new Class[]{C_1391_.class});
   public static ReflectorMethod IClientItemExtensions_ofI = IClientItemExtensions.makeMethod("of", new Class[]{C_1381_.class});
   public static ReflectorMethod IClientItemExtensions_getFont = IClientItemExtensions.makeMethod("getFont");
   public static ReflectorClass IClientItemExtensions_FontContext = new ReflectorClass(
      "net.minecraftforge.client.extensions.common.IClientItemExtensions$FontContext"
   );
   public static ReflectorField IClientItemExtensions_FontContext_SELECTED_ITEM_NAME = IClientItemExtensions_FontContext.makeField("SELECTED_ITEM_NAME");
   public static ReflectorClass CapabilityProvider = new ReflectorClass("net.minecraftforge.common.capabilities.CapabilityProvider");
   public static ReflectorMethod CapabilityProvider_gatherCapabilities = new ReflectorMethod(CapabilityProvider, "gatherCapabilities", new Class[0]);
   public static ReflectorClass ClientModLoader = new ReflectorClass("net.minecraftforge.client.loading.ClientModLoader");
   public static ReflectorMethod ClientModLoader_isLoading = new ReflectorMethod(ClientModLoader, "isLoading");
   public static ReflectorClass ChunkDataEvent_Save = new ReflectorClass("net.minecraftforge.event.level.ChunkDataEvent$Save");
   public static ReflectorConstructor ChunkDataEvent_Save_Constructor = new ReflectorConstructor(
      ChunkDataEvent_Save, new Class[]{C_2116_.class, C_1598_.class, C_4917_.class}
   );
   public static ReflectorClass ChunkEvent_Load = new ReflectorClass("net.minecraftforge.event.level.ChunkEvent$Load");
   public static ReflectorConstructor ChunkEvent_Load_Constructor = new ReflectorConstructor(ChunkEvent_Load, new Class[]{C_2116_.class, boolean.class});
   public static ReflectorClass ChunkEvent_Unload = new ReflectorClass("net.minecraftforge.event.level.ChunkEvent$Unload");
   public static ReflectorConstructor ChunkEvent_Unload_Constructor = new ReflectorConstructor(ChunkEvent_Unload, new Class[]{C_2116_.class});
   public static ReflectorClass ColorResolverManager = new ReflectorClass("net.minecraftforge.client.ColorResolverManager");
   public static ReflectorMethod ColorResolverManager_registerBlockTintCaches = ColorResolverManager.makeMethod("registerBlockTintCaches");
   public static ReflectorClass CrashReportAnalyser = new ReflectorClass("net.minecraftforge.logging.CrashReportAnalyser");
   public static ReflectorMethod CrashReportAnalyser_appendSuspectedMods = new ReflectorMethod(CrashReportAnalyser, "appendSuspectedMods");
   public static ReflectorClass CrashReportExtender = new ReflectorClass("net.minecraftforge.logging.CrashReportExtender");
   public static ReflectorMethod CrashReportExtender_extendSystemReport = new ReflectorMethod(CrashReportExtender, "extendSystemReport");
   public static ReflectorMethod CrashReportExtender_generateEnhancedStackTraceT = new ReflectorMethod(
      CrashReportExtender, "generateEnhancedStackTrace", new Class[]{Throwable.class}
   );
   public static ReflectorMethod CrashReportExtender_generateEnhancedStackTraceSTE = new ReflectorMethod(
      CrashReportExtender, "generateEnhancedStackTrace", new Class[]{StackTraceElement[].class}
   );
   public static ReflectorClass EntityRenderersEvent_AddLayers = new ReflectorClass("net.minecraftforge.client.event.EntityRenderersEvent$AddLayers");
   public static ReflectorConstructor EntityRenderersEvent_AddLayers_Constructor = EntityRenderersEvent_AddLayers.makeConstructor(
      new Class[]{Map.class, Map.class, C_141743_.class}
   );
   public static ReflectorClass EntityRenderersEvent_CreateSkullModels = new ReflectorClass(
      "net.minecraftforge.client.event.EntityRenderersEvent$CreateSkullModels"
   );
   public static ReflectorConstructor EntityRenderersEvent_CreateSkullModels_Constructor = EntityRenderersEvent_CreateSkullModels.makeConstructor(
      new Class[]{Builder.class, C_141653_.class}
   );
   public static ReflectorClass EntityLeaveLevelEvent = new ReflectorClass("net.minecraftforge.event.entity.EntityLeaveLevelEvent");
   public static ReflectorConstructor EntityLeaveLevelEvent_Constructor = new ReflectorConstructor(
      EntityLeaveLevelEvent, new Class[]{C_507_.class, C_1596_.class}
   );
   public static ReflectorClass ViewportEvent_ComputeCameraAngles = new ReflectorClass("net.minecraftforge.client.event.ViewportEvent$ComputeCameraAngles");
   public static ReflectorMethod ViewportEvent_ComputeCameraAngles_getYaw = new ReflectorMethod(ViewportEvent_ComputeCameraAngles, "getYaw");
   public static ReflectorMethod ViewportEvent_ComputeCameraAngles_getPitch = new ReflectorMethod(ViewportEvent_ComputeCameraAngles, "getPitch");
   public static ReflectorMethod ViewportEvent_ComputeCameraAngles_getRoll = new ReflectorMethod(ViewportEvent_ComputeCameraAngles, "getRoll");
   public static ReflectorClass EntityJoinLevelEvent = new ReflectorClass("net.minecraftforge.event.entity.EntityJoinLevelEvent");
   public static ReflectorConstructor EntityJoinLevelEvent_Constructor = new ReflectorConstructor(
      EntityJoinLevelEvent, new Class[]{C_507_.class, C_1596_.class}
   );
   public static ReflectorClass Event = new ReflectorClass("net.minecraftforge.eventbus.api.Event");
   public static ReflectorMethod Event_isCanceled = new ReflectorMethod(Event, "isCanceled");
   public static ReflectorMethod Event_getResult = new ReflectorMethod(Event, "getResult");
   public static ReflectorClass EventBus = new ReflectorClass("net.minecraftforge.eventbus.api.IEventBus");
   public static ReflectorMethod EventBus_post = new ReflectorMethod(EventBus, "post", new Class[]{Event.class});
   public static ReflectorClass Event_Result = new ReflectorClass("net.minecraftforge.eventbus.api.Event$Result");
   public static ReflectorField Event_Result_DENY = new ReflectorField(Event_Result, "DENY");
   public static ReflectorField Event_Result_ALLOW = new ReflectorField(Event_Result, "ALLOW");
   public static ReflectorField Event_Result_DEFAULT = new ReflectorField(Event_Result, "DEFAULT");
   public static ReflectorClass FluidType = new ReflectorClass("net.minecraftforge.fluids.FluidType");
   public static ReflectorMethod FluidType_isAir = FluidType.makeMethod("isAir");
   public static ReflectorClass ForgeModelBlockRenderer = new ReflectorClass("net.minecraftforge.client.model.lighting.ForgeModelBlockRenderer");
   public static ReflectorConstructor ForgeModelBlockRenderer_Constructor = new ReflectorConstructor(ForgeModelBlockRenderer, new Class[]{C_3423_.class});
   public static ReflectorClass ForgeBlockModelShapes = new ReflectorClass(C_4182_.class);
   public static ReflectorMethod ForgeBlockModelShapes_getTexture3 = new ReflectorMethod(
      ForgeBlockModelShapes, "getTexture", new Class[]{C_2064_.class, C_1596_.class, C_4675_.class}
   );
   public static ReflectorClass ForgeBlockElementFace = new ReflectorClass(C_4200_.class);
   public static ReflectorMethod ForgeBlockElementFace_getFaceData = ForgeBlockElementFace.makeMethod("getFaceData");
   public static ReflectorClass IForgeBlockState = new ReflectorClass("net.minecraftforge.common.extensions.IForgeBlockState");
   public static ReflectorMethod IForgeBlockState_getLightEmission = new ReflectorMethod(
      IForgeBlockState, "getLightEmission", new Class[]{C_1559_.class, C_4675_.class}
   );
   public static ReflectorMethod IForgeBlockState_getSoundType3 = new ReflectorMethod(
      IForgeBlockState, "getSoundType", new Class[]{C_1599_.class, C_4675_.class, C_507_.class}
   );
   public static ReflectorMethod IForgeBlockState_getStateAtViewpoint = new ReflectorMethod(IForgeBlockState, "getStateAtViewpoint");
   public static ReflectorMethod IForgeBlockState_shouldDisplayFluidOverlay = new ReflectorMethod(IForgeBlockState, "shouldDisplayFluidOverlay");
   public static ReflectorClass IForgeEntity = new ReflectorClass("net.minecraftforge.common.extensions.IForgeEntity");
   public static ReflectorMethod IForgeEntity_canUpdate = new ReflectorMethod(IForgeEntity, "canUpdate", new Class[0]);
   public static ReflectorMethod IForgeEntity_getEyeInFluidType = new ReflectorMethod(IForgeEntity, "getEyeInFluidType");
   public static ReflectorMethod IForgeEntity_getParts = new ReflectorMethod(IForgeEntity, "getParts");
   public static ReflectorMethod IForgeEntity_hasCustomOutlineRendering = new ReflectorMethod(IForgeEntity, "hasCustomOutlineRendering");
   public static ReflectorMethod IForgeEntity_isMultipartEntity = new ReflectorMethod(IForgeEntity, "isMultipartEntity");
   public static ReflectorMethod IForgeEntity_onAddedToWorld = new ReflectorMethod(IForgeEntity, "onAddedToWorld");
   public static ReflectorMethod IForgeEntity_onRemovedFromWorld = new ReflectorMethod(IForgeEntity, "onRemovedFromWorld");
   public static ReflectorMethod IForgeEntity_shouldRiderSit = new ReflectorMethod(IForgeEntity, "shouldRiderSit");
   public static ReflectorClass IForgePlayer = new ReflectorClass("net.minecraftforge.common.extensions.IForgePlayer");
   public static ReflectorMethod IForgePlayer_getEntityReach = IForgePlayer.makeMethod("getEntityReach");
   public static ReflectorMethod IForgePlayer_getBlockReach = IForgePlayer.makeMethod("getBlockReach");
   public static ReflectorClass ForgeChunkHolder = new ReflectorClass(C_5422_.class);
   public static ReflectorField ForgeChunkHolder_currentlyLoading = new ReflectorField(ForgeChunkHolder, "currentlyLoading");
   public static ReflectorClass ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
   public static ReflectorMethod ForgeEventFactory_canEntityDespawn = new ReflectorMethod(ForgeEventFactory, "canEntityDespawn");
   public static ReflectorMethod ForgeEventFactory_fireChunkTicketLevelUpdated = new ReflectorMethod(ForgeEventFactory, "fireChunkTicketLevelUpdated");
   public static ReflectorMethod ForgeEventFactory_fireChunkWatch = new ReflectorMethod(ForgeEventFactory, "fireChunkWatch");
   public static ReflectorMethod ForgeEventFactory_fireChunkUnWatch = new ReflectorMethod(ForgeEventFactory, "fireChunkUnWatch");
   public static ReflectorMethod ForgeEventFactory_getMaxSpawnPackSize = new ReflectorMethod(ForgeEventFactory, "getMaxSpawnPackSize");
   public static ReflectorMethod ForgeEventFactory_getMobGriefingEvent = new ReflectorMethod(ForgeEventFactory, "getMobGriefingEvent");
   public static ReflectorMethod ForgeEventFactory_onChunkDataSave = new ReflectorMethod(ForgeEventFactory, "onChunkDataSave");
   public static ReflectorMethod ForgeEventFactory_onChunkLoad = new ReflectorMethod(ForgeEventFactory, "onChunkLoad");
   public static ReflectorMethod ForgeEventFactory_onChunkUnload = new ReflectorMethod(ForgeEventFactory, "onChunkUnload");
   public static ReflectorMethod ForgeEventFactory_onPlaySoundAtEntity = new ReflectorMethod(ForgeEventFactory, "onPlaySoundAtEntity");
   public static ReflectorMethod ForgeEventFactory_onPlaySoundAtPosition = new ReflectorMethod(ForgeEventFactory, "onPlaySoundAtPosition");
   public static ReflectorClass ForgeEventFactoryClient = new ReflectorClass("net.minecraftforge.client.event.ForgeEventFactoryClient");
   public static ReflectorMethod ForgeEventFactoryClient_gatherLayers = new ReflectorMethod(ForgeEventFactoryClient, "gatherLayers");
   public static ReflectorClass ForgeFaceData = new ReflectorClass(ForgeFaceData.class);
   public static ReflectorMethod ForgeFaceData_calculateNormals = ForgeFaceData.makeMethod("calculateNormals");
   public static ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
   public static ReflectorMethod ForgeHooks_onDifficultyChange = new ReflectorMethod(ForgeHooks, "onDifficultyChange");
   public static ReflectorMethod ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
   public static ReflectorMethod ForgeHooks_onLivingChangeTarget = new ReflectorMethod(ForgeHooks, "onLivingChangeTarget");
   public static ReflectorMethod ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
   public static ReflectorMethod ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
   public static ReflectorMethod ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
   public static ReflectorMethod ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
   public static ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
   public static ReflectorMethod ForgeHooksClient_calculateFaceWithoutAO = new ReflectorMethod(ForgeHooksClient, "calculateFaceWithoutAO");
   public static ReflectorMethod ForgeHooksClient_onCustomizeBossEventProgress = new ReflectorMethod(ForgeHooksClient, "onCustomizeBossEventProgress");
   public static ReflectorMethod ForgeHooksClient_onRenderTooltipColor = new ReflectorMethod(ForgeHooksClient, "onRenderTooltipColor");
   public static ReflectorMethod ForgeHooksClient_dispatchRenderStageRT = new ReflectorMethod(
      ForgeHooksClient,
      "dispatchRenderStage",
      new Class[]{C_4168_.class, C_4134_.class, C_3181_.class, Matrix4f.class, int.class, C_3373_.class, C_4273_.class}
   );
   public static ReflectorMethod ForgeHooksClient_drawScreen = new ReflectorMethod(ForgeHooksClient, "drawScreen");
   public static ReflectorMethod ForgeHooksClient_fillNormal = new ReflectorMethod(ForgeHooksClient, "fillNormal", new Class[]{int[].class, C_4687_.class});
   public static ReflectorMethod ForgeHooksClient_gatherTooltipComponents6 = new ReflectorMethod(
      ForgeHooksClient, "gatherTooltipComponents", new Class[]{C_1391_.class, List.class, int.class, int.class, int.class, C_3429_.class}
   );
   public static ReflectorMethod ForgeHooksClient_gatherTooltipComponents7 = new ReflectorMethod(
      ForgeHooksClient, "gatherTooltipComponents", new Class[]{C_1391_.class, List.class, Optional.class, int.class, int.class, int.class, C_3429_.class}
   );
   public static ReflectorMethod ForgeHooksClient_onKeyInput = new ReflectorMethod(ForgeHooksClient, "onKeyInput");
   public static ReflectorMethod ForgeHooksClient_getFogColor = new ReflectorMethod(ForgeHooksClient, "getFogColor");
   public static ReflectorMethod ForgeHooksClient_handleCameraTransforms = new ReflectorMethod(ForgeHooksClient, "handleCameraTransforms");
   public static ReflectorMethod ForgeHooksClient_getArmorModel = new ReflectorMethod(ForgeHooksClient, "getArmorModel");
   public static ReflectorMethod ForgeHooksClient_getArmorTexture = new ReflectorMethod(ForgeHooksClient, "getArmorTexture");
   public static ReflectorMethod ForgeHooksClient_getFluidSprites = new ReflectorMethod(ForgeHooksClient, "getFluidSprites");
   public static ReflectorMethod ForgeHooksClient_getFieldOfViewModifier = new ReflectorMethod(ForgeHooksClient, "getFieldOfViewModifier");
   public static ReflectorMethod ForgeHooksClient_getFieldOfView = new ReflectorMethod(ForgeHooksClient, "getFieldOfView");
   public static ReflectorMethod ForgeHooksClient_getGuiFarPlane = new ReflectorMethod(ForgeHooksClient, "getGuiFarPlane");
   public static ReflectorMethod ForgeHooksClient_getShaderImportLocation = new ReflectorMethod(ForgeHooksClient, "getShaderImportLocation");
   public static ReflectorMethod ForgeHooksClient_isNameplateInRenderDistance = new ReflectorMethod(ForgeHooksClient, "isNameplateInRenderDistance");
   public static ReflectorMethod ForgeHooksClient_loadEntityShader = new ReflectorMethod(ForgeHooksClient, "loadEntityShader");
   public static ReflectorMethod ForgeHooksClient_loadTextureAtlasSprite = new ReflectorMethod(ForgeHooksClient, "loadTextureAtlasSprite");
   public static ReflectorMethod ForgeHooksClient_loadSpriteContents = new ReflectorMethod(ForgeHooksClient, "loadSpriteContents");
   public static ReflectorMethod ForgeHooksClient_makeParticleRenderTypeComparator = new ReflectorMethod(ForgeHooksClient, "makeParticleRenderTypeComparator");
   public static ReflectorMethod ForgeHooksClient_onCameraSetup = new ReflectorMethod(ForgeHooksClient, "onCameraSetup");
   public static ReflectorMethod ForgeHooksClient_onDrawHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawHighlight");
   public static ReflectorMethod ForgeHooksClient_onFogRender = new ReflectorMethod(ForgeHooksClient, "onFogRender");
   public static ReflectorMethod ForgeHooksClient_onRegisterAdditionalModels = new ReflectorMethod(ForgeHooksClient, "onRegisterAdditionalModels");
   public static ReflectorMethod ForgeHooksClient_onRenderTooltipPre = new ReflectorMethod(ForgeHooksClient, "onRenderTooltipPre");
   public static ReflectorMethod ForgeHooksClient_onScreenCharTypedPre = new ReflectorMethod(ForgeHooksClient, "onScreenCharTypedPre");
   public static ReflectorMethod ForgeHooksClient_onScreenCharTypedPost = new ReflectorMethod(ForgeHooksClient, "onScreenCharTypedPost");
   public static ReflectorMethod ForgeHooksClient_onScreenKeyPressedPre = new ReflectorMethod(ForgeHooksClient, "onScreenKeyPressedPre");
   public static ReflectorMethod ForgeHooksClient_onScreenKeyPressedPost = new ReflectorMethod(ForgeHooksClient, "onScreenKeyPressedPost");
   public static ReflectorMethod ForgeHooksClient_onScreenKeyReleasedPre = new ReflectorMethod(ForgeHooksClient, "onScreenKeyReleasedPre");
   public static ReflectorMethod ForgeHooksClient_onScreenKeyReleasedPost = new ReflectorMethod(ForgeHooksClient, "onScreenKeyReleasedPost");
   public static ReflectorMethod ForgeHooksClient_onScreenshot = new ReflectorMethod(ForgeHooksClient, "onScreenshot");
   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
   public static ReflectorMethod ForgeHooksClient_renderBlockOverlay = new ReflectorMethod(ForgeHooksClient, "renderBlockOverlay");
   public static ReflectorMethod ForgeHooksClient_renderFireOverlay = new ReflectorMethod(ForgeHooksClient, "renderFireOverlay");
   public static ReflectorMethod ForgeHooksClient_renderWaterOverlay = new ReflectorMethod(ForgeHooksClient, "renderWaterOverlay");
   public static ReflectorMethod ForgeHooksClient_renderMainMenu = new ReflectorMethod(ForgeHooksClient, "renderMainMenu");
   public static ReflectorMethod ForgeHooksClient_renderSpecificFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderSpecificFirstPersonHand");
   public static ReflectorMethod ForgeHooksClient_shouldCauseReequipAnimation = new ReflectorMethod(ForgeHooksClient, "shouldCauseReequipAnimation");
   public static ReflectorClass ForgeConfig = new ReflectorClass("net.minecraftforge.common.ForgeConfig");
   public static ReflectorField ForgeConfig_CLIENT = new ReflectorField(ForgeConfig, "CLIENT");
   public static ReflectorClass ForgeConfig_Client = new ReflectorClass("net.minecraftforge.common.ForgeConfig$Client");
   public static ReflectorField ForgeConfig_Client_calculateAllNormals = new ReflectorField(ForgeConfig_Client, "calculateAllNormals");
   public static ReflectorField ForgeConfig_Client_forgeLightPipelineEnabled = new ReflectorField(ForgeConfig_Client, "experimentalForgeLightPipelineEnabled");
   public static ReflectorField ForgeConfig_Client_useCombinedDepthStencilAttachment = new ReflectorField(
      ForgeConfig_Client, "useCombinedDepthStencilAttachment"
   );
   public static ReflectorClass ForgeConfigSpec = new ReflectorClass("net.minecraftforge.common.ForgeConfigSpec");
   public static ReflectorField ForgeConfigSpec_childConfig = new ReflectorField(ForgeConfigSpec, "childConfig");
   public static ReflectorClass ForgeConfigSpec_ConfigValue = new ReflectorClass("net.minecraftforge.common.ForgeConfigSpec$ConfigValue");
   public static ReflectorField ForgeConfigSpec_ConfigValue_defaultSupplier = new ReflectorField(ForgeConfigSpec_ConfigValue, "defaultSupplier");
   public static ReflectorField ForgeConfigSpec_ConfigValue_spec = new ReflectorField(ForgeConfigSpec_ConfigValue, "spec");
   public static ReflectorMethod ForgeConfigSpec_ConfigValue_get = new ReflectorMethod(ForgeConfigSpec_ConfigValue, "get");
   public static ReflectorClass ForgeIChunk = new ReflectorClass(C_2116_.class);
   public static ReflectorMethod ForgeIChunk_getWorldForge = new ReflectorMethod(ForgeIChunk, "getWorldForge");
   public static ReflectorClass IForgeItem = new ReflectorClass("net.minecraftforge.common.extensions.IForgeItem");
   public static ReflectorMethod IForgeItem_getEquipmentSlot = new ReflectorMethod(IForgeItem, "getEquipmentSlot");
   public static ReflectorMethod IForgeItem_isDamageable1 = new ReflectorMethod(IForgeItem, "isDamageable", new Class[]{C_1391_.class});
   public static ReflectorMethod IForgeItem_onEntitySwing = new ReflectorMethod(IForgeItem, "onEntitySwing");
   public static ReflectorMethod IForgeItem_shouldCauseReequipAnimation = new ReflectorMethod(IForgeItem, "shouldCauseReequipAnimation");
   public static ReflectorClass IForgeItemStack = new ReflectorClass("net.minecraftforge.common.extensions.IForgeItemStack");
   public static ReflectorMethod IForgeItemStack_canDisableShield = new ReflectorMethod(IForgeItemStack, "canDisableShield");
   public static ReflectorMethod IForgeItemStack_getEquipmentSlot = new ReflectorMethod(IForgeItemStack, "getEquipmentSlot");
   public static ReflectorMethod IForgeItemStack_getShareTag = new ReflectorMethod(IForgeItemStack, "getShareTag");
   public static ReflectorMethod IForgeItemStack_getHighlightTip = new ReflectorMethod(IForgeItemStack, "getHighlightTip");
   public static ReflectorMethod IForgeItemStack_readShareTag = new ReflectorMethod(IForgeItemStack, "readShareTag");
   public static ReflectorClass ForgeItemTags = new ReflectorClass(C_140_.class);
   public static ReflectorMethod ForgeItemTags_create = ForgeItemTags.makeMethod("create", new Class[]{C_5265_.class});
   public static ReflectorClass ForgeI18n = new ReflectorClass("net.minecraftforge.common.ForgeI18n");
   public static ReflectorMethod ForgeI18n_loadLanguageData = new ReflectorMethod(ForgeI18n, "loadLanguageData");
   public static ReflectorClass ForgeKeyBinding = new ReflectorClass(C_3387_.class);
   public static ReflectorMethod ForgeKeyBinding_setKeyConflictContext = new ReflectorMethod(ForgeKeyBinding, "setKeyConflictContext");
   public static ReflectorMethod ForgeKeyBinding_setKeyModifierAndCode = new ReflectorMethod(ForgeKeyBinding, "setKeyModifierAndCode");
   public static ReflectorMethod ForgeKeyBinding_getKeyModifier = new ReflectorMethod(ForgeKeyBinding, "getKeyModifier");
   public static ReflectorClass ForgeRarity = new ReflectorClass(C_1407_.class);
   public static ReflectorMethod ForgeRarity_getStyleModifier = ForgeRarity.makeMethod("getStyleModifier");
   public static ReflectorClass ForgeTicket = new ReflectorClass(C_17_.class);
   public static ReflectorField ForgeTicket_forceTicks = ForgeTicket.makeField("forceTicks");
   public static ReflectorMethod ForgeTicket_isForceTicks = ForgeTicket.makeMethod("isForceTicks");
   public static ReflectorClass IForgeBlockEntity = new ReflectorClass("net.minecraftforge.common.extensions.IForgeBlockEntity");
   public static ReflectorMethod IForgeBlockEntity_getRenderBoundingBox = new ReflectorMethod(IForgeBlockEntity, "getRenderBoundingBox");
   public static ReflectorMethod IForgeBlockEntity_hasCustomOutlineRendering = new ReflectorMethod(IForgeBlockEntity, "hasCustomOutlineRendering");
   public static ReflectorClass IForgeDimensionSpecialEffects = new ReflectorClass("net.minecraftforge.client.extensions.IForgeDimensionSpecialEffects");
   public static ReflectorMethod IForgeDimensionSpecialEffects_adjustLightmapColors = IForgeDimensionSpecialEffects.makeMethod("adjustLightmapColors");
   public static ReflectorMethod IForgeDimensionSpecialEffects_renderClouds = IForgeDimensionSpecialEffects.makeMethod("renderClouds");
   public static ReflectorMethod IForgeDimensionSpecialEffects_renderSky = IForgeDimensionSpecialEffects.makeMethod("renderSky");
   public static ReflectorMethod IForgeDimensionSpecialEffects_tickRain = IForgeDimensionSpecialEffects.makeMethod("tickRain");
   public static ReflectorMethod IForgeDimensionSpecialEffects_renderSnowAndRain = IForgeDimensionSpecialEffects.makeMethod("renderSnowAndRain");
   public static ReflectorClass ForgeVersion = new ReflectorClass("net.minecraftforge.versions.forge.ForgeVersion");
   public static ReflectorMethod ForgeVersion_getVersion = ForgeVersion.makeMethod("getVersion");
   public static ReflectorMethod ForgeVersion_getSpec = ForgeVersion.makeMethod("getSpec");
   public static ReflectorClass ImmediateWindowHandler = new ReflectorClass("net.minecraftforge.fml.loading.ImmediateWindowHandler");
   public static ReflectorMethod ImmediateWindowHandler_positionWindow = ImmediateWindowHandler.makeMethod("positionWindow");
   public static ReflectorMethod ImmediateWindowHandler_setupMinecraftWindow = ImmediateWindowHandler.makeMethod("setupMinecraftWindow");
   public static ReflectorMethod ImmediateWindowHandler_updateFBSize = ImmediateWindowHandler.makeMethod("updateFBSize");
   public static ReflectorClass ItemDecoratorHandler = new ReflectorClass("net.minecraftforge.client.ItemDecoratorHandler");
   public static ReflectorMethod ItemDecoratorHandler_of = ItemDecoratorHandler.makeMethod("of", new Class[]{C_1391_.class});
   public static ReflectorMethod ItemDecoratorHandler_render = ItemDecoratorHandler.makeMethod("render");
   public static ReflectorClass ForgeItemModelShaper = new ReflectorClass("net.minecraftforge.client.model.ForgeItemModelShaper");
   public static ReflectorConstructor ForgeItemModelShaper_Constructor = new ReflectorConstructor(ForgeItemModelShaper, new Class[]{C_4535_.class});
   public static ReflectorClass GeometryLoaderManager = new ReflectorClass("net.minecraftforge.client.model.geometry.GeometryLoaderManager");
   public static ReflectorMethod GeometryLoaderManager_init = GeometryLoaderManager.makeMethod("init");
   public static ReflectorClass KeyConflictContext = new ReflectorClass("net.minecraftforge.client.settings.KeyConflictContext");
   public static ReflectorField KeyConflictContext_IN_GAME = new ReflectorField(KeyConflictContext, "IN_GAME");
   public static ReflectorClass KeyModifier = new ReflectorClass("net.minecraftforge.client.settings.KeyModifier");
   public static ReflectorMethod KeyModifier_valueFromString = new ReflectorMethod(KeyModifier, "valueFromString");
   public static ReflectorField KeyModifier_NONE = new ReflectorField(KeyModifier, "NONE");
   public static ReflectorClass Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
   public static ReflectorField Launch_blackboard = new ReflectorField(Launch, "blackboard");
   public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
   public static ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
   public static ReflectorClass ModContainer = new ReflectorClass("net.minecraftforge.fml.ModContainer");
   public static ReflectorMethod ModContainer_getModId = new ReflectorMethod(ModContainer, "getModId");
   public static ReflectorClass ModList = new ReflectorClass("net.minecraftforge.fml.ModList");
   public static ReflectorField ModList_mods = ModList.makeField("mods");
   public static ReflectorMethod ModList_get = ModList.makeMethod("get");
   public static ReflectorClass ModListScreen = new ReflectorClass("net.minecraftforge.client.gui.ModListScreen");
   public static ReflectorConstructor ModListScreen_Constructor = new ReflectorConstructor(ModListScreen, new Class[]{C_3583_.class});
   public static ReflectorClass ModLoader = new ReflectorClass("net.minecraftforge.fml.ModLoader");
   public static ReflectorMethod ModLoader_get = ModLoader.makeMethod("get");
   public static ReflectorMethod ModLoader_postEvent = ModLoader.makeMethod("postEvent");
   public static ReflectorClass TitleScreenModUpdateIndicator = new ReflectorClass("net.minecraftforge.client.gui.TitleScreenModUpdateIndicator");
   public static ReflectorMethod TitleScreenModUpdateIndicator_init = TitleScreenModUpdateIndicator.makeMethod(
      "init", new Class[]{C_3588_.class, C_3451_.class}
   );
   public static ReflectorClass PartEntity = new ReflectorClass("net.minecraftforge.entity.PartEntity");
   public static ReflectorClass PlayLevelSoundEvent = new ReflectorClass("net.minecraftforge.event.PlayLevelSoundEvent");
   public static ReflectorMethod PlayLevelSoundEvent_getSound = new ReflectorMethod(PlayLevelSoundEvent, "getSound");
   public static ReflectorMethod PlayLevelSoundEvent_getSource = new ReflectorMethod(PlayLevelSoundEvent, "getSource");
   public static ReflectorMethod PlayLevelSoundEvent_getNewVolume = new ReflectorMethod(PlayLevelSoundEvent, "getNewVolume");
   public static ReflectorMethod PlayLevelSoundEvent_getNewPitch = new ReflectorMethod(PlayLevelSoundEvent, "getNewPitch");
   public static ReflectorClass QuadBakingVertexConsumer = new ReflectorClass("net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer");
   public static ReflectorField QuadBakingVertexConsumer_QUAD_DATA_SIZE = QuadBakingVertexConsumer.makeField("QUAD_DATA_SIZE");
   public static ReflectorClass QuadTransformers = new ReflectorClass("net.minecraftforge.client.model.QuadTransformers");
   public static ReflectorMethod QuadTransformers_applyingLightmap = QuadTransformers.makeMethod("applyingLightmap", new Class[]{int.class, int.class});
   public static ReflectorMethod QuadTransformers_applyingColor = QuadTransformers.makeMethod("applyingColor", new Class[]{int.class});
   public static ReflectorClass IQuadTransformer = new ReflectorClass("net.minecraftforge.client.model.IQuadTransformer");
   public static ReflectorField IQuadTransformer_STRIDE = IQuadTransformer.makeField("STRIDE");
   public static ReflectorMethod IQuadTransformer_processInPlace = IQuadTransformer.makeMethod("processInPlace", new Class[]{C_4196_.class});
   public static ReflectorClass RegisterShadersEvent = new ReflectorClass("net.minecraftforge.client.event.RegisterShadersEvent");
   public static ReflectorConstructor RegisterShadersEvent_Constructor = RegisterShadersEvent.makeConstructor(new Class[]{C_140974_.class, List.class});
   public static ReflectorClass RenderBlockScreenEffectEvent_OverlayType = new ReflectorClass(
      "net.minecraftforge.client.event.RenderBlockScreenEffectEvent$OverlayType"
   );
   public static ReflectorField RenderBlockScreenEffectEvent_OverlayType_BLOCK = new ReflectorField(RenderBlockScreenEffectEvent_OverlayType, "BLOCK");
   public static ReflectorClass CustomizeGuiOverlayEvent_BossEventProgress = new ReflectorClass(
      "net.minecraftforge.client.event.CustomizeGuiOverlayEvent$BossEventProgress"
   );
   public static ReflectorMethod CustomizeGuiOverlayEvent_BossEventProgress_getIncrement = CustomizeGuiOverlayEvent_BossEventProgress.makeMethod("getIncrement");
   public static ReflectorClass RenderItemInFrameEvent = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
   public static ReflectorConstructor RenderItemInFrameEvent_Constructor = new ReflectorConstructor(
      RenderItemInFrameEvent, new Class[]{C_970_.class, C_4353_.class, C_3181_.class, C_4139_.class, int.class}
   );
   public static ReflectorClass RenderLevelStageEvent_Stage = new ReflectorClass(RenderLevelStageEvent.Stage.class);
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_SKY = RenderLevelStageEvent_Stage.makeField("AFTER_SKY");
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_SOLID_BLOCKS = RenderLevelStageEvent_Stage.makeField("AFTER_SOLID_BLOCKS");
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_CUTOUT_MIPPED_BLOCKS_BLOCKS = RenderLevelStageEvent_Stage.makeField(
      "AFTER_CUTOUT_MIPPED_BLOCKS_BLOCKS"
   );
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_CUTOUT_BLOCKS = RenderLevelStageEvent_Stage.makeField("AFTER_CUTOUT_BLOCKS");
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_ENTITIES = RenderLevelStageEvent_Stage.makeField("AFTER_ENTITIES");
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_BLOCK_ENTITIES = RenderLevelStageEvent_Stage.makeField("AFTER_BLOCK_ENTITIES");
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_TRANSLUCENT_BLOCKS = RenderLevelStageEvent_Stage.makeField("AFTER_TRANSLUCENT_BLOCKS");
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_TRIPWIRE_BLOCKS = RenderLevelStageEvent_Stage.makeField("AFTER_TRIPWIRE_BLOCKS");
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_PARTICLES = RenderLevelStageEvent_Stage.makeField("AFTER_PARTICLES");
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_WEATHER = RenderLevelStageEvent_Stage.makeField("AFTER_WEATHER");
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_LEVEL = RenderLevelStageEvent_Stage.makeField("AFTER_LEVEL");
   public static ReflectorMethod RenderLevelStageEvent_dispatch = RenderLevelStageEvent_Stage.makeMethod("dispatch");
   public static ReflectorClass RenderLivingEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
   public static ReflectorConstructor RenderLivingEvent_Pre_Constructor = new ReflectorConstructor(
      RenderLivingEvent_Pre, new Class[]{C_524_.class, C_4357_.class, float.class, C_3181_.class, C_4139_.class, int.class}
   );
   public static ReflectorClass RenderLivingEvent_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
   public static ReflectorConstructor RenderLivingEvent_Post_Constructor = new ReflectorConstructor(
      RenderLivingEvent_Post, new Class[]{C_524_.class, C_4357_.class, float.class, C_3181_.class, C_4139_.class, int.class}
   );
   public static ReflectorClass RenderNameTagEvent = new ReflectorClass("net.minecraftforge.client.event.RenderNameTagEvent");
   public static ReflectorConstructor RenderNameTagEvent_Constructor = new ReflectorConstructor(
      RenderNameTagEvent, new Class[]{C_507_.class, C_4996_.class, C_4331_.class, C_3181_.class, C_4139_.class, int.class, float.class}
   );
   public static ReflectorMethod RenderNameTagEvent_getContent = new ReflectorMethod(RenderNameTagEvent, "getContent");
   public static ReflectorClass RenderTooltipEvent = new ReflectorClass("net.minecraftforge.client.event.RenderTooltipEvent");
   public static ReflectorMethod RenderTooltipEvent_getFont = RenderTooltipEvent.makeMethod("getFont");
   public static ReflectorMethod RenderTooltipEvent_getX = RenderTooltipEvent.makeMethod("getX");
   public static ReflectorMethod RenderTooltipEvent_getY = RenderTooltipEvent.makeMethod("getY");
   public static ReflectorClass RenderTooltipEvent_Color = new ReflectorClass("net.minecraftforge.client.event.RenderTooltipEvent$Color");
   public static ReflectorMethod RenderTooltipEvent_Color_getBackgroundStart = RenderTooltipEvent_Color.makeMethod("getBackgroundStart");
   public static ReflectorMethod RenderTooltipEvent_Color_getBackgroundEnd = RenderTooltipEvent_Color.makeMethod("getBackgroundEnd");
   public static ReflectorMethod RenderTooltipEvent_Color_getBorderStart = RenderTooltipEvent_Color.makeMethod("getBorderStart");
   public static ReflectorMethod RenderTooltipEvent_Color_getBorderEnd = RenderTooltipEvent_Color.makeMethod("getBorderEnd");
   public static ReflectorClass ScreenshotEvent = new ReflectorClass("net.minecraftforge.client.event.ScreenshotEvent");
   public static ReflectorMethod ScreenshotEvent_getCancelMessage = new ReflectorMethod(ScreenshotEvent, "getCancelMessage");
   public static ReflectorMethod ScreenshotEvent_getScreenshotFile = new ReflectorMethod(ScreenshotEvent, "getScreenshotFile");
   public static ReflectorMethod ScreenshotEvent_getResultMessage = new ReflectorMethod(ScreenshotEvent, "getResultMessage");
   public static ReflectorClass ServerLifecycleHooks = new ReflectorClass("net.minecraftforge.server.ServerLifecycleHooks");
   public static ReflectorMethod ServerLifecycleHooks_handleServerAboutToStart = new ReflectorMethod(ServerLifecycleHooks, "handleServerAboutToStart");
   public static ReflectorMethod ServerLifecycleHooks_handleServerStarting = new ReflectorMethod(ServerLifecycleHooks, "handleServerStarting");
   public static ReflectorClass TerrainParticle = new ReflectorClass(C_4085_.class);
   public static ReflectorMethod TerrainParticle_updateSprite = TerrainParticle.makeMethod("updateSprite");
   public static ReflectorClass TooltipRenderUtil = new ReflectorClass(C_262715_.class);
   public static ReflectorMethod TooltipRenderUtil_renderTooltipBackground10 = TooltipRenderUtil.makeMethod("renderTooltipBackground");
   public static ReflectorClass LevelEvent_Load = new ReflectorClass("net.minecraftforge.event.level.LevelEvent$Load");
   public static ReflectorConstructor LevelEvent_Load_Constructor = new ReflectorConstructor(LevelEvent_Load, new Class[]{C_1598_.class});
   private static boolean logVanilla = registerResolvable("*** Reflector Vanilla ***");
   public static ReflectorClass AbstractArrow = new ReflectorClass(C_1151_.class);
   public static ReflectorField AbstractArrow_inGround = new ReflectorField(
      new FieldLocatorTypes(C_1151_.class, new Class[]{C_2064_.class}, boolean.class, new Class[]{int.class}, "AbstractArrow.inGround")
   );
   public static ReflectorClass BannerBlockEntity = new ReflectorClass(C_1976_.class);
   public static ReflectorField BannerBlockEntity_customName = BannerBlockEntity.makeField(C_4996_.class);
   public static ReflectorClass BaseContainerBlockEntity = new ReflectorClass(C_1980_.class);
   public static ReflectorField BaseContainerBlockEntity_customName = BaseContainerBlockEntity.makeField(C_4996_.class);
   public static ReflectorClass Enchantments = new ReflectorClass(C_1525_.class);
   public static ReflectorFields Enchantments_ResourceKeys = new ReflectorFields(Enchantments, C_5264_.class, -1);
   public static ReflectorClass EntityItem = new ReflectorClass(C_976_.class);
   public static ReflectorField EntityItem_ITEM = new ReflectorField(EntityItem, C_5225_.class);
   public static ReflectorClass EnderDragonRenderer = new ReflectorClass(C_4326_.class);
   public static ReflectorField EnderDragonRenderer_model = new ReflectorField(EnderDragonRenderer, C_4327_.class);
   public static ReflectorClass GuiEnchantment = new ReflectorClass(C_3659_.class);
   public static ReflectorField GuiEnchantment_bookModel = new ReflectorField(GuiEnchantment, C_3804_.class);
   public static ReflectorClass ItemOverride = new ReflectorClass(C_4217_.class);
   public static ReflectorField ItemOverride_listResourceValues = new ReflectorField(ItemOverride, List.class);
   public static ReflectorClass ItemStack = new ReflectorClass(C_1391_.class);
   public static ReflectorField ItemStack_components = ItemStack.makeField(C_313555_.class);
   public static ReflectorClass LayerLlamaDecor = new ReflectorClass(C_4442_.class);
   public static ReflectorField LayerLlamaDecor_model = new ReflectorField(LayerLlamaDecor, C_3837_.class);
   public static ReflectorClass Minecraft = new ReflectorClass(C_3391_.class);
   public static ReflectorField Minecraft_debugFPS = new ReflectorField(
      new FieldLocatorTypes(C_3391_.class, new Class[]{Supplier.class}, int.class, new Class[]{String.class}, "debugFPS")
   );
   public static ReflectorField Minecraft_fontResourceManager = new ReflectorField(Minecraft, C_3509_.class);
   public static ReflectorClass ModelArmorStand = new ReflectorClass(C_3799_.class);
   public static ReflectorFields ModelArmorStand_ModelRenderers = new ReflectorFields(ModelArmorStand, C_3889_.class, 4);
   public static ReflectorClass ModelBee = new ReflectorClass(C_3801_.class);
   public static ReflectorFields ModelBee_ModelRenderers = new ReflectorFields(ModelBee, C_3889_.class, 2);
   public static ReflectorClass ModelBlaze = new ReflectorClass(C_3802_.class);
   public static ReflectorField ModelBlaze_blazeHead = new ReflectorField(ModelBlaze, C_3889_.class);
   public static ReflectorField ModelBlaze_blazeSticks = new ReflectorField(ModelBlaze, C_3889_[].class);
   public static ReflectorClass ModelBoar = new ReflectorClass(C_3826_.class);
   public static ReflectorFields ModelBoar_ModelRenderers = new ReflectorFields(ModelBoar, C_3889_.class, 9);
   public static ReflectorClass ModelBook = new ReflectorClass(C_3804_.class);
   public static ReflectorField ModelBook_root = new ReflectorField(ModelBook, C_3889_.class);
   public static ReflectorClass ModelChicken = new ReflectorClass(C_3807_.class);
   public static ReflectorFields ModelChicken_ModelRenderers = new ReflectorFields(ModelChicken, C_3889_.class, 8);
   public static ReflectorClass ModelDragon = new ReflectorClass(C_4327_.class);
   public static ReflectorFields ModelDragon_ModelRenderers = new ReflectorFields(ModelDragon, C_3889_.class, 20);
   public static ReflectorClass RenderEnderCrystal = new ReflectorClass(C_4325_.class);
   public static ReflectorFields RenderEnderCrystal_modelRenderers = new ReflectorFields(RenderEnderCrystal, C_3889_.class, 3);
   public static ReflectorClass ModelEvokerFangs = new ReflectorClass(C_3820_.class);
   public static ReflectorFields ModelEvokerFangs_ModelRenderers = new ReflectorFields(ModelEvokerFangs, C_3889_.class, 3);
   public static ReflectorClass ModelGuardian = new ReflectorClass(C_3824_.class);
   public static ReflectorField ModelGuardian_spines = new ReflectorField(ModelGuardian, C_3889_[].class, 0);
   public static ReflectorField ModelGuardian_tail = new ReflectorField(ModelGuardian, C_3889_[].class, 1);
   public static ReflectorClass ModelDragonHead = new ReflectorClass(C_3888_.class);
   public static ReflectorField ModelDragonHead_head = new ReflectorField(ModelDragonHead, C_3889_.class, 0);
   public static ReflectorField ModelDragonHead_jaw = new ReflectorField(ModelDragonHead, C_3889_.class, 1);
   public static ReflectorClass ModelHorse = new ReflectorClass(C_3827_.class);
   public static ReflectorFields ModelHorse_ModelRenderers = new ReflectorFields(ModelHorse, C_3889_.class, 11);
   public static ReflectorClass ModelHorseChests = new ReflectorClass(C_3806_.class);
   public static ReflectorFields ModelHorseChests_ModelRenderers = new ReflectorFields(ModelHorseChests, C_3889_.class, 2);
   public static ReflectorClass ModelIllager = new ReflectorClass(C_3832_.class);
   public static ReflectorFields ModelIllager_ModelRenderers = new ReflectorFields(ModelIllager, C_3889_.class, 8);
   public static ReflectorClass ModelIronGolem = new ReflectorClass(C_3833_.class);
   public static ReflectorFields ModelIronGolem_ModelRenderers = new ReflectorFields(ModelIronGolem, C_3889_.class, 6);
   public static ReflectorClass ModelAxolotl = new ReflectorClass(C_141647_.class);
   public static ReflectorFields ModelAxolotl_ModelRenderers = new ReflectorFields(ModelAxolotl, C_3889_.class, 10);
   public static ReflectorClass ModelFox = new ReflectorClass(C_3821_.class);
   public static ReflectorFields ModelFox_ModelRenderers = new ReflectorFields(ModelFox, C_3889_.class, 7);
   public static ReflectorClass ModelLeashKnot = new ReflectorClass(C_3835_.class);
   public static ReflectorField ModelLeashKnot_knotRenderer = new ReflectorField(ModelLeashKnot, C_3889_.class);
   public static ReflectorClass RenderLeashKnot = new ReflectorClass(C_4355_.class);
   public static ReflectorField RenderLeashKnot_leashKnotModel = new ReflectorField(RenderLeashKnot, C_3835_.class);
   public static ReflectorClass ModelLlama = new ReflectorClass(C_3837_.class);
   public static ReflectorFields ModelLlama_ModelRenderers = new ReflectorFields(ModelLlama, C_3889_.class, 8);
   public static ReflectorClass ModelOcelot = new ReflectorClass(C_3842_.class);
   public static ReflectorFields ModelOcelot_ModelRenderers = new ReflectorFields(ModelOcelot, C_3889_.class, 8);
   public static ReflectorClass ModelPhantom = new ReflectorClass(C_3850_.class);
   public static ReflectorFields ModelPhantom_ModelRenderers = new ReflectorFields(ModelPhantom, C_3889_.class, 7);
   public static ReflectorClass ModelPiglin = new ReflectorClass(C_3852_.class);
   public static ReflectorFields ModelPiglin_ModelRenderers = new ReflectorFields(ModelPiglin, C_3889_.class, 2);
   public static ReflectorClass ModelPiglinHead = new ReflectorClass(C_260365_.class);
   public static ReflectorFields ModelPiglinHead_ModelRenderers = new ReflectorFields(ModelPiglinHead, C_3889_.class, 3);
   public static ReflectorClass ModelQuadruped = new ReflectorClass(C_3858_.class);
   public static ReflectorFields ModelQuadruped_ModelRenderers = new ReflectorFields(ModelQuadruped, C_3889_.class, 6);
   public static ReflectorClass ModelRabbit = new ReflectorClass(C_3859_.class);
   public static ReflectorFields ModelRabbit_ModelRenderers = new ReflectorFields(ModelRabbit, C_3889_.class, 12);
   public static ReflectorClass ModelShulker = new ReflectorClass(C_3866_.class);
   public static ReflectorFields ModelShulker_ModelRenderers = new ReflectorFields(ModelShulker, C_3889_.class, 3);
   public static ReflectorClass ModelShield = new ReflectorClass(C_3864_.class);
   public static ReflectorFields ModelShield_ModelRenderers = new ReflectorFields(ModelShield, C_3889_.class, 3);
   public static ReflectorClass ModelSkull = new ReflectorClass(C_3869_.class);
   public static ReflectorFields ModelSkull_renderers = new ReflectorFields(ModelSkull, C_3889_.class, 2);
   public static ReflectorClass ModelTadpole = new ReflectorClass(C_213398_.class);
   public static ReflectorFields ModelTadpole_ModelRenderers = new ReflectorFields(ModelTadpole, C_3889_.class, 2);
   public static ReflectorClass ModelTrident = new ReflectorClass(C_3875_.class);
   public static ReflectorField ModelTrident_root = new ReflectorField(ModelTrident, C_3889_.class);
   public static ReflectorClass ModelTurtle = new ReflectorClass(C_3879_.class);
   public static ReflectorField ModelTurtle_body2 = new ReflectorField(ModelTurtle, C_3889_.class, 0);
   public static ReflectorClass ModelVex = new ReflectorClass(C_3880_.class);
   public static ReflectorField ModelVex_leftWing = new ReflectorField(ModelVex, C_3889_.class, 0);
   public static ReflectorField ModelVex_rightWing = new ReflectorField(ModelVex, C_3889_.class, 1);
   public static ReflectorClass ModelWolf = new ReflectorClass(C_3885_.class);
   public static ReflectorFields ModelWolf_ModelRenderers = new ReflectorFields(ModelWolf, C_3889_.class, 10);
   public static ReflectorClass OptiFineResourceLocator = ReflectorForge.getReflectorClassOptiFineResourceLocator();
   public static ReflectorMethod OptiFineResourceLocator_getOptiFineResourceStream = new ReflectorMethod(OptiFineResourceLocator, "getOptiFineResourceStream");
   public static ReflectorClass Potion = new ReflectorClass(C_1440_.class);
   public static ReflectorField Potion_baseName = Potion.makeField(String.class);
   public static ReflectorClass RenderBoat = new ReflectorClass(C_4313_.class);
   public static ReflectorField RenderBoat_boatResources = new ReflectorField(RenderBoat, Map.class);
   public static ReflectorClass RenderEvokerFangs = new ReflectorClass(C_4332_.class);
   public static ReflectorField RenderEvokerFangs_model = new ReflectorField(RenderEvokerFangs, C_3820_.class);
   public static ReflectorClass RenderLlamaSpit = new ReflectorClass(C_4360_.class);
   public static ReflectorField RenderLlamaSpit_model = new ReflectorField(RenderLlamaSpit, C_3838_.class);
   public static ReflectorClass RenderPufferfish = new ReflectorClass(C_4379_.class);
   public static ReflectorField RenderPufferfish_modelSmall = new ReflectorField(RenderPufferfish, C_3819_.class, 0);
   public static ReflectorField RenderPufferfish_modelMedium = new ReflectorField(RenderPufferfish, C_3819_.class, 1);
   public static ReflectorField RenderPufferfish_modelBig = new ReflectorField(RenderPufferfish, C_3819_.class, 2);
   public static ReflectorClass RenderMinecart = new ReflectorClass(C_4362_.class);
   public static ReflectorField RenderMinecart_modelMinecart = new ReflectorField(RenderMinecart, C_3819_.class);
   public static ReflectorClass RenderShulkerBullet = new ReflectorClass(C_4385_.class);
   public static ReflectorField RenderShulkerBullet_model = new ReflectorField(RenderShulkerBullet, C_3865_.class);
   public static ReflectorClass RenderTrident = new ReflectorClass(C_4397_.class);
   public static ReflectorField RenderTrident_modelTrident = new ReflectorField(RenderTrident, C_3875_.class);
   public static ReflectorClass RenderTropicalFish = new ReflectorClass(C_4402_.class);
   public static ReflectorField RenderTropicalFish_modelA = new ReflectorField(RenderTropicalFish, C_141648_.class, 0);
   public static ReflectorField RenderTropicalFish_modelB = new ReflectorField(RenderTropicalFish, C_141648_.class, 1);
   public static ReflectorClass RenderWindCharge = new ReflectorClass(C_301983_.class);
   public static ReflectorField RenderWindCharge_model = new ReflectorField(RenderWindCharge, C_301905_.class);
   public static ReflectorClass TropicalFishPatternLayer = new ReflectorClass(C_4457_.class);
   public static ReflectorField TropicalFishPatternLayer_modelA = new ReflectorField(TropicalFishPatternLayer, C_3877_.class);
   public static ReflectorField TropicalFishPatternLayer_modelB = new ReflectorField(TropicalFishPatternLayer, C_3878_.class);
   public static ReflectorClass RenderWitherSkull = new ReflectorClass(C_4413_.class);
   public static ReflectorField RenderWitherSkull_model = new ReflectorField(RenderWitherSkull, C_3869_.class);
   public static ReflectorClass SimpleBakedModel = new ReflectorClass(C_4540_.class);
   public static ReflectorField SimpleBakedModel_generalQuads = SimpleBakedModel.makeField(List.class);
   public static ReflectorField SimpleBakedModel_faceQuads = SimpleBakedModel.makeField(Map.class);
   public static ReflectorClass TileEntityBannerRenderer = new ReflectorClass(C_4238_.class);
   public static ReflectorFields TileEntityBannerRenderer_modelRenderers = new ReflectorFields(TileEntityBannerRenderer, C_3889_.class, 3);
   public static ReflectorClass TileEntityBedRenderer = new ReflectorClass(C_4241_.class);
   public static ReflectorField TileEntityBedRenderer_headModel = new ReflectorField(TileEntityBedRenderer, C_3889_.class, 0);
   public static ReflectorField TileEntityBedRenderer_footModel = new ReflectorField(TileEntityBedRenderer, C_3889_.class, 1);
   public static ReflectorClass TileEntityBellRenderer = new ReflectorClass(C_4242_.class);
   public static ReflectorField TileEntityBellRenderer_modelRenderer = new ReflectorField(TileEntityBellRenderer, C_3889_.class);
   public static ReflectorClass TileEntityBeacon = new ReflectorClass(C_1981_.class);
   public static ReflectorField TileEntityBeacon_customName = new ReflectorField(TileEntityBeacon, C_4996_.class);
   public static ReflectorField TileEntityBeacon_levels = new ReflectorField(
      new FieldLocatorTypes(C_1981_.class, new Class[]{List.class}, int.class, new Class[]{int.class}, "BeaconBlockEntity.levels")
   );
   public static ReflectorClass TileEntityChestRenderer = new ReflectorClass(C_4247_.class);
   public static ReflectorFields TileEntityChestRenderer_modelRenderers = new ReflectorFields(TileEntityChestRenderer, C_3889_.class, 9);
   public static ReflectorClass TileEntityConduitRenderer = new ReflectorClass(C_4248_.class);
   public static ReflectorFields TileEntityConduitRenderer_modelRenderers = new ReflectorFields(TileEntityConduitRenderer, C_3889_.class, 4);
   public static ReflectorClass TileEntityDecoratedPotRenderer = new ReflectorClass(C_271025_.class);
   public static ReflectorFields TileEntityDecoratedPotRenderer_modelRenderers = new ReflectorFields(TileEntityDecoratedPotRenderer, C_3889_.class, 7);
   public static ReflectorClass TileEntityEnchantmentTableRenderer = new ReflectorClass(C_4249_.class);
   public static ReflectorField TileEntityEnchantmentTableRenderer_modelBook = new ReflectorField(TileEntityEnchantmentTableRenderer, C_3804_.class);
   public static ReflectorClass TileEntityHangingSignRenderer = new ReflectorClass(C_243526_.class);
   public static ReflectorField TileEntityHangingSignRenderer_hangingSignModels = new ReflectorField(TileEntityHangingSignRenderer, Map.class);
   public static ReflectorClass TileEntityLecternRenderer = new ReflectorClass(C_4250_.class);
   public static ReflectorField TileEntityLecternRenderer_modelBook = new ReflectorField(TileEntityLecternRenderer, C_3804_.class);
   public static ReflectorClass TileEntityShulkerBoxRenderer = new ReflectorClass(C_4252_.class);
   public static ReflectorField TileEntityShulkerBoxRenderer_model = new ReflectorField(TileEntityShulkerBoxRenderer, C_3866_.class);
   public static ReflectorClass TileEntitySignRenderer = new ReflectorClass(C_4253_.class);
   public static ReflectorField TileEntitySignRenderer_signModels = new ReflectorField(TileEntitySignRenderer, Map.class);

   public static void callVoid(ReflectorMethod refMethod, Object... params) {
      try {
         Method m = refMethod.getTargetMethod();
         if (m == null) {
            return;
         }

         m.invoke(null, params);
      } catch (Throwable var3) {
         handleException(var3, null, refMethod, params);
      }
   }

   public static boolean callBoolean(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return false;
         } else {
            Boolean retVal = (Boolean)method.invoke(null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, null, refMethod, params);
         return false;
      }
   }

   public static int callInt(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0;
         } else {
            Integer retVal = (Integer)method.invoke(null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, null, refMethod, params);
         return 0;
      }
   }

   public static long callLong(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0L;
         } else {
            Long retVal = (Long)method.invoke(null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, null, refMethod, params);
         return 0L;
      }
   }

   public static float callFloat(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0.0F;
         } else {
            Float retVal = (Float)method.invoke(null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, null, refMethod, params);
         return 0.0F;
      }
   }

   public static double callDouble(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0.0;
         } else {
            Double retVal = (Double)method.invoke(null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, null, refMethod, params);
         return 0.0;
      }
   }

   public static String callString(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         return method == null ? null : (String)method.invoke(null, params);
      } catch (Throwable var4) {
         handleException(var4, null, refMethod, params);
         return null;
      }
   }

   public static Object call(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         return method == null ? null : method.invoke(null, params);
      } catch (Throwable var4) {
         handleException(var4, null, refMethod, params);
         return null;
      }
   }

   public static void callVoid(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         if (obj == null) {
            return;
         }

         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return;
         }

         method.invoke(obj, params);
      } catch (Throwable var4) {
         handleException(var4, obj, refMethod, params);
      }
   }

   public static boolean callBoolean(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return false;
         } else {
            Boolean retVal = (Boolean)method.invoke(obj, params);
            return retVal;
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return false;
      }
   }

   public static int callInt(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0;
         } else {
            Integer retVal = (Integer)method.invoke(obj, params);
            return retVal;
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return 0;
      }
   }

   public static long callLong(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0L;
         } else {
            Long retVal = (Long)method.invoke(obj, params);
            return retVal;
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return 0L;
      }
   }

   public static float callFloat(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0.0F;
         } else {
            Float retVal = (Float)method.invoke(obj, params);
            return retVal;
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return 0.0F;
      }
   }

   public static double callDouble(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0.0;
         } else {
            Double retVal = (Double)method.invoke(obj, params);
            return retVal;
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return 0.0;
      }
   }

   public static String callString(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         return method == null ? null : (String)method.invoke(obj, params);
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return null;
      }
   }

   public static Object call(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         return method == null ? null : method.invoke(obj, params);
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return null;
      }
   }

   public static Object getFieldValue(ReflectorField refField) {
      return getFieldValue(null, refField);
   }

   public static Object getFieldValue(Object obj, ReflectorField refField) {
      try {
         Field field = refField.getTargetField();
         return field == null ? null : field.get(obj);
      } catch (Throwable var4) {
         Log.error("", var4);
         return null;
      }
   }

   public static boolean getFieldValueBoolean(Object obj, ReflectorField refField, boolean def) {
      try {
         Field field = refField.getTargetField();
         return field == null ? def : field.getBoolean(obj);
      } catch (Throwable var5) {
         Log.error("", var5);
         return def;
      }
   }

   public static Object getFieldValue(ReflectorFields refFields, int index) {
      ReflectorField refField = refFields.getReflectorField(index);
      return refField == null ? null : getFieldValue(refField);
   }

   public static Object getFieldValue(Object obj, ReflectorFields refFields, int index) {
      ReflectorField refField = refFields.getReflectorField(index);
      return refField == null ? null : getFieldValue(obj, refField);
   }

   public static float getFieldValueFloat(Object obj, ReflectorField refField, float def) {
      try {
         Field field = refField.getTargetField();
         return field == null ? def : field.getFloat(obj);
      } catch (Throwable var5) {
         Log.error("", var5);
         return def;
      }
   }

   public static int getFieldValueInt(ReflectorField refField, int def) {
      return getFieldValueInt(null, refField, def);
   }

   public static int getFieldValueInt(Object obj, ReflectorField refField, int def) {
      try {
         Field field = refField.getTargetField();
         return field == null ? def : field.getInt(obj);
      } catch (Throwable var5) {
         Log.error("", var5);
         return def;
      }
   }

   public static long getFieldValueLong(Object obj, ReflectorField refField, long def) {
      try {
         Field field = refField.getTargetField();
         return field == null ? def : field.getLong(obj);
      } catch (Throwable var7) {
         Log.error("", var7);
         return def;
      }
   }

   public static boolean setFieldValue(ReflectorField refField, Object value) {
      return setFieldValue(null, refField, value);
   }

   public static boolean setFieldValue(Object obj, ReflectorFields refFields, int index, Object value) {
      ReflectorField refField = refFields.getReflectorField(index);
      if (refField == null) {
         return false;
      } else {
         setFieldValue(obj, refField, value);
         return true;
      }
   }

   public static boolean setFieldValue(Object obj, ReflectorField refField, Object value) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return false;
         } else {
            field.set(obj, value);
            return true;
         }
      } catch (Throwable var4) {
         Log.error("", var4);
         return false;
      }
   }

   public static boolean setFieldValueInt(ReflectorField refField, int value) {
      return setFieldValueInt(null, refField, value);
   }

   public static boolean setFieldValueInt(Object obj, ReflectorField refField, int value) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return false;
         } else {
            field.setInt(obj, value);
            return true;
         }
      } catch (Throwable var4) {
         Log.error("", var4);
         return false;
      }
   }

   public static boolean postForgeBusEvent(ReflectorConstructor constr, Object... params) {
      Object event = newInstance(constr, params);
      return event == null ? false : postForgeBusEvent(event);
   }

   public static boolean postForgeBusEvent(Object event) {
      if (event == null) {
         return false;
      } else {
         Object eventBus = getFieldValue(MinecraftForge_EVENT_BUS);
         if (eventBus == null) {
            return false;
         } else {
            return !(call(eventBus, EventBus_post, event) instanceof Boolean retBool) ? false : retBool;
         }
      }
   }

   public static Object newInstance(ReflectorConstructor constr, Object... params) {
      Constructor c = constr.getTargetConstructor();
      if (c == null) {
         return null;
      } else {
         try {
            return c.newInstance(params);
         } catch (Throwable var4) {
            handleException(var4, constr, params);
            return null;
         }
      }
   }

   public static boolean matchesTypes(Class[] pTypes, Class[] cTypes) {
      if (pTypes.length != cTypes.length) {
         return false;
      } else {
         for (int i = 0; i < cTypes.length; i++) {
            Class pType = pTypes[i];
            Class cType = cTypes[i];
            if (pType != cType) {
               return false;
            }
         }

         return true;
      }
   }

   private static void dbgCall(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params, Object retVal) {
      String className = refMethod.getTargetMethod().getDeclaringClass().getName();
      String methodName = refMethod.getTargetMethod().getName();
      String staticStr = "";
      if (isStatic) {
         staticStr = " static";
      }

      Log.dbg(callType + staticStr + " " + className + "." + methodName + "(" + ArrayUtils.arrayToString(params) + ") => " + retVal);
   }

   private static void dbgCallVoid(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params) {
      String className = refMethod.getTargetMethod().getDeclaringClass().getName();
      String methodName = refMethod.getTargetMethod().getName();
      String staticStr = "";
      if (isStatic) {
         staticStr = " static";
      }

      Log.dbg(callType + staticStr + " " + className + "." + methodName + "(" + ArrayUtils.arrayToString(params) + ")");
   }

   private static void dbgFieldValue(boolean isStatic, String accessType, ReflectorField refField, Object val) {
      String className = refField.getTargetField().getDeclaringClass().getName();
      String fieldName = refField.getTargetField().getName();
      String staticStr = "";
      if (isStatic) {
         staticStr = " static";
      }

      Log.dbg(accessType + staticStr + " " + className + "." + fieldName + " => " + val);
   }

   private static void handleException(Throwable e, Object obj, ReflectorMethod refMethod, Object[] params) {
      if (e instanceof InvocationTargetException) {
         if (e.getCause() instanceof RuntimeException causeRuntime) {
            throw causeRuntime;
         } else {
            Log.error("", e);
         }
      } else {
         Log.warn("*** Exception outside of method ***");
         Log.warn("Method deactivated: " + refMethod.getTargetMethod());
         refMethod.deactivate();
         if (e instanceof IllegalArgumentException) {
            Log.warn("*** IllegalArgumentException ***");
            Log.warn("Method: " + refMethod.getTargetMethod());
            Log.warn("Object: " + obj);
            Log.warn("Parameter classes: " + ArrayUtils.arrayToString(getClasses(params)));
            Log.warn("Parameters: " + ArrayUtils.arrayToString(params));
         }

         Log.warn("", e);
      }
   }

   private static void handleException(Throwable e, ReflectorConstructor refConstr, Object[] params) {
      if (e instanceof InvocationTargetException) {
         Log.error("", e);
      } else {
         Log.warn("*** Exception outside of constructor ***");
         Log.warn("Constructor deactivated: " + refConstr.getTargetConstructor());
         refConstr.deactivate();
         if (e instanceof IllegalArgumentException) {
            Log.warn("*** IllegalArgumentException ***");
            Log.warn("Constructor: " + refConstr.getTargetConstructor());
            Log.warn("Parameter classes: " + ArrayUtils.arrayToString(getClasses(params)));
            Log.warn("Parameters: " + ArrayUtils.arrayToString(params));
         }

         Log.warn("", e);
      }
   }

   private static Object[] getClasses(Object[] objs) {
      if (objs == null) {
         return new Class[0];
      } else {
         Class[] classes = new Class[objs.length];

         for (int i = 0; i < classes.length; i++) {
            Object obj = objs[i];
            if (obj != null) {
               classes[i] = obj.getClass();
            }
         }

         return classes;
      }
   }

   private static ReflectorField[] getReflectorFields(ReflectorClass parentClass, Class fieldType, int count) {
      ReflectorField[] rfs = new ReflectorField[count];

      for (int i = 0; i < rfs.length; i++) {
         rfs[i] = new ReflectorField(parentClass, fieldType, i);
      }

      return rfs;
   }

   private static boolean registerResolvable(final String str) {
      String msg = str;
      IResolvable ir = new IResolvable() {
         @Override
         public void resolve() {
            Reflector.LOGGER.info("[OptiFine] " + str);
         }
      };
      ReflectorResolver.register(ir);
      return true;
   }
}
