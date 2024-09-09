package net.optifine.reflect;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.BeeModel;
import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.ChestedHorseModel;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.EvokerFangsModel;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.HoglinModel;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.LeashKnotModel;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.LlamaSpitModel;
import net.minecraft.client.model.OcelotModel;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.model.PiglinHeadModel;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.ShulkerBulletModel;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.TadpoleModel;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.TurtleModel;
import net.minecraft.client.model.VexModel;
import net.minecraft.client.model.WindChargeModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.dragon.DragonHeadModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.LecternRenderer;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.LeashKnotRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.client.renderer.entity.ShulkerBulletRenderer;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.client.renderer.entity.WindChargeRenderer;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.client.renderer.entity.layers.TropicalFishPatternLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
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
   public static ReflectorMethod BrandingControl_getBrandings;
   public static ReflectorMethod BrandingControl_getClientBranding;
   public static ReflectorMethod BrandingControl_forEachLine;
   public static ReflectorMethod BrandingControl_forEachAboveCopyrightLine;
   public static ReflectorClass IClientBlockExtensions;
   public static ReflectorMethod IClientBlockExtensions_ofBS;
   public static ReflectorClass IClientItemExtensions;
   public static ReflectorMethod IClientItemExtensions_ofIS;
   public static ReflectorMethod IClientItemExtensions_ofI;
   public static ReflectorMethod IClientItemExtensions_getFont;
   public static ReflectorClass IClientItemExtensions_FontContext;
   public static ReflectorField IClientItemExtensions_FontContext_SELECTED_ITEM_NAME;
   public static ReflectorClass CapabilityProvider;
   public static ReflectorMethod CapabilityProvider_gatherCapabilities;
   public static ReflectorClass ClientModLoader;
   public static ReflectorMethod ClientModLoader_isLoading;
   public static ReflectorClass ChunkDataEvent_Save;
   public static ReflectorConstructor ChunkDataEvent_Save_Constructor;
   public static ReflectorClass ChunkEvent_Load;
   public static ReflectorConstructor ChunkEvent_Load_Constructor;
   public static ReflectorClass ChunkEvent_Unload;
   public static ReflectorConstructor ChunkEvent_Unload_Constructor;
   public static ReflectorClass ColorResolverManager;
   public static ReflectorMethod ColorResolverManager_registerBlockTintCaches;
   public static ReflectorClass CrashReportAnalyser;
   public static ReflectorMethod CrashReportAnalyser_appendSuspectedMods;
   public static ReflectorClass CrashReportExtender;
   public static ReflectorMethod CrashReportExtender_extendSystemReport;
   public static ReflectorMethod CrashReportExtender_generateEnhancedStackTraceT;
   public static ReflectorMethod CrashReportExtender_generateEnhancedStackTraceSTE;
   public static ReflectorClass EntityRenderersEvent_AddLayers;
   public static ReflectorConstructor EntityRenderersEvent_AddLayers_Constructor;
   public static ReflectorClass EntityRenderersEvent_CreateSkullModels;
   public static ReflectorConstructor EntityRenderersEvent_CreateSkullModels_Constructor;
   public static ReflectorClass EntityLeaveLevelEvent;
   public static ReflectorConstructor EntityLeaveLevelEvent_Constructor;
   public static ReflectorClass ViewportEvent_ComputeCameraAngles;
   public static ReflectorMethod ViewportEvent_ComputeCameraAngles_getYaw;
   public static ReflectorMethod ViewportEvent_ComputeCameraAngles_getPitch;
   public static ReflectorMethod ViewportEvent_ComputeCameraAngles_getRoll;
   public static ReflectorClass EntityJoinLevelEvent;
   public static ReflectorConstructor EntityJoinLevelEvent_Constructor;
   public static ReflectorClass Event;
   public static ReflectorMethod Event_isCanceled;
   public static ReflectorMethod Event_getResult;
   public static ReflectorClass EventBus;
   public static ReflectorMethod EventBus_post;
   public static ReflectorClass Event_Result;
   public static ReflectorField Event_Result_DENY;
   public static ReflectorField Event_Result_ALLOW;
   public static ReflectorField Event_Result_DEFAULT;
   public static ReflectorClass FluidType;
   public static ReflectorMethod FluidType_isAir;
   public static ReflectorClass ForgeModelBlockRenderer;
   public static ReflectorConstructor ForgeModelBlockRenderer_Constructor;
   public static ReflectorClass ForgeBlockModelShapes;
   public static ReflectorMethod ForgeBlockModelShapes_getTexture3;
   public static ReflectorClass ForgeBlockElementFace;
   public static ReflectorMethod ForgeBlockElementFace_getFaceData;
   public static ReflectorClass IForgeBlockState;
   public static ReflectorMethod IForgeBlockState_getLightEmission;
   public static ReflectorMethod IForgeBlockState_getSoundType3;
   public static ReflectorMethod IForgeBlockState_getStateAtViewpoint;
   public static ReflectorMethod IForgeBlockState_shouldDisplayFluidOverlay;
   public static ReflectorClass IForgeEntity;
   public static ReflectorMethod IForgeEntity_canUpdate;
   public static ReflectorMethod IForgeEntity_getEyeInFluidType;
   public static ReflectorMethod IForgeEntity_getParts;
   public static ReflectorMethod IForgeEntity_hasCustomOutlineRendering;
   public static ReflectorMethod IForgeEntity_isMultipartEntity;
   public static ReflectorMethod IForgeEntity_onAddedToWorld;
   public static ReflectorMethod IForgeEntity_onRemovedFromWorld;
   public static ReflectorMethod IForgeEntity_shouldRiderSit;
   public static ReflectorClass IForgePlayer;
   public static ReflectorMethod IForgePlayer_getEntityReach;
   public static ReflectorMethod IForgePlayer_getBlockReach;
   public static ReflectorClass ForgeChunkHolder;
   public static ReflectorField ForgeChunkHolder_currentlyLoading;
   public static ReflectorClass ForgeEventFactory;
   public static ReflectorMethod ForgeEventFactory_canEntityDespawn;
   public static ReflectorMethod ForgeEventFactory_fireChunkTicketLevelUpdated;
   public static ReflectorMethod ForgeEventFactory_fireChunkWatch;
   public static ReflectorMethod ForgeEventFactory_fireChunkUnWatch;
   public static ReflectorMethod ForgeEventFactory_getMaxSpawnPackSize;
   public static ReflectorMethod ForgeEventFactory_getMobGriefingEvent;
   public static ReflectorMethod ForgeEventFactory_onChunkDataSave;
   public static ReflectorMethod ForgeEventFactory_onChunkLoad;
   public static ReflectorMethod ForgeEventFactory_onChunkUnload;
   public static ReflectorMethod ForgeEventFactory_onPlaySoundAtEntity;
   public static ReflectorMethod ForgeEventFactory_onPlaySoundAtPosition;
   public static ReflectorClass ForgeEventFactoryClient;
   public static ReflectorMethod ForgeEventFactoryClient_gatherLayers;
   public static ReflectorClass ForgeFaceData;
   public static ReflectorMethod ForgeFaceData_calculateNormals;
   public static ReflectorClass ForgeHooks;
   public static ReflectorMethod ForgeHooks_onDifficultyChange;
   public static ReflectorMethod ForgeHooks_onLivingAttack;
   public static ReflectorMethod ForgeHooks_onLivingChangeTarget;
   public static ReflectorMethod ForgeHooks_onLivingDeath;
   public static ReflectorMethod ForgeHooks_onLivingDrops;
   public static ReflectorMethod ForgeHooks_onLivingHurt;
   public static ReflectorMethod ForgeHooks_onLivingJump;
   public static ReflectorClass ForgeHooksClient;
   public static ReflectorMethod ForgeHooksClient_calculateFaceWithoutAO;
   public static ReflectorMethod ForgeHooksClient_onCustomizeBossEventProgress;
   public static ReflectorMethod ForgeHooksClient_onRenderTooltipColor;
   public static ReflectorMethod ForgeHooksClient_dispatchRenderStageRT;
   public static ReflectorMethod ForgeHooksClient_drawScreen;
   public static ReflectorMethod ForgeHooksClient_fillNormal;
   public static ReflectorMethod ForgeHooksClient_gatherTooltipComponents6;
   public static ReflectorMethod ForgeHooksClient_gatherTooltipComponents7;
   public static ReflectorMethod ForgeHooksClient_onKeyInput;
   public static ReflectorMethod ForgeHooksClient_getFogColor;
   public static ReflectorMethod ForgeHooksClient_handleCameraTransforms;
   public static ReflectorMethod ForgeHooksClient_getArmorModel;
   public static ReflectorMethod ForgeHooksClient_getArmorTexture;
   public static ReflectorMethod ForgeHooksClient_getFluidSprites;
   public static ReflectorMethod ForgeHooksClient_getFieldOfViewModifier;
   public static ReflectorMethod ForgeHooksClient_getFieldOfView;
   public static ReflectorMethod ForgeHooksClient_getGuiFarPlane;
   public static ReflectorMethod ForgeHooksClient_getShaderImportLocation;
   public static ReflectorMethod ForgeHooksClient_isNameplateInRenderDistance;
   public static ReflectorMethod ForgeHooksClient_loadEntityShader;
   public static ReflectorMethod ForgeHooksClient_loadTextureAtlasSprite;
   public static ReflectorMethod ForgeHooksClient_loadSpriteContents;
   public static ReflectorMethod ForgeHooksClient_makeParticleRenderTypeComparator;
   public static ReflectorMethod ForgeHooksClient_onCameraSetup;
   public static ReflectorMethod ForgeHooksClient_onDrawHighlight;
   public static ReflectorMethod ForgeHooksClient_onFogRender;
   public static ReflectorMethod ForgeHooksClient_onRegisterAdditionalModels;
   public static ReflectorMethod ForgeHooksClient_onRenderTooltipPre;
   public static ReflectorMethod ForgeHooksClient_onScreenCharTypedPre;
   public static ReflectorMethod ForgeHooksClient_onScreenCharTypedPost;
   public static ReflectorMethod ForgeHooksClient_onScreenKeyPressedPre;
   public static ReflectorMethod ForgeHooksClient_onScreenKeyPressedPost;
   public static ReflectorMethod ForgeHooksClient_onScreenKeyReleasedPre;
   public static ReflectorMethod ForgeHooksClient_onScreenKeyReleasedPost;
   public static ReflectorMethod ForgeHooksClient_onScreenshot;
   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost;
   public static ReflectorMethod ForgeHooksClient_renderBlockOverlay;
   public static ReflectorMethod ForgeHooksClient_renderFireOverlay;
   public static ReflectorMethod ForgeHooksClient_renderWaterOverlay;
   public static ReflectorMethod ForgeHooksClient_renderMainMenu;
   public static ReflectorMethod ForgeHooksClient_renderSpecificFirstPersonHand;
   public static ReflectorMethod ForgeHooksClient_shouldCauseReequipAnimation;
   public static ReflectorClass ForgeConfig;
   public static ReflectorField ForgeConfig_CLIENT;
   public static ReflectorClass ForgeConfig_Client;
   public static ReflectorField ForgeConfig_Client_calculateAllNormals;
   public static ReflectorField ForgeConfig_Client_forgeLightPipelineEnabled;
   public static ReflectorField ForgeConfig_Client_useCombinedDepthStencilAttachment;
   public static ReflectorClass ForgeConfigSpec;
   public static ReflectorField ForgeConfigSpec_childConfig;
   public static ReflectorClass ForgeConfigSpec_ConfigValue;
   public static ReflectorField ForgeConfigSpec_ConfigValue_defaultSupplier;
   public static ReflectorField ForgeConfigSpec_ConfigValue_spec;
   public static ReflectorMethod ForgeConfigSpec_ConfigValue_get;
   public static ReflectorClass ForgeIChunk;
   public static ReflectorMethod ForgeIChunk_getWorldForge;
   public static ReflectorClass IForgeItem;
   public static ReflectorMethod IForgeItem_getEquipmentSlot;
   public static ReflectorMethod IForgeItem_isDamageable1;
   public static ReflectorMethod IForgeItem_onEntitySwing;
   public static ReflectorMethod IForgeItem_shouldCauseReequipAnimation;
   public static ReflectorClass IForgeItemStack;
   public static ReflectorMethod IForgeItemStack_canDisableShield;
   public static ReflectorMethod IForgeItemStack_getEquipmentSlot;
   public static ReflectorMethod IForgeItemStack_getShareTag;
   public static ReflectorMethod IForgeItemStack_getHighlightTip;
   public static ReflectorMethod IForgeItemStack_readShareTag;
   public static ReflectorClass ForgeItemTags;
   public static ReflectorMethod ForgeItemTags_create;
   public static ReflectorClass ForgeI18n;
   public static ReflectorMethod ForgeI18n_loadLanguageData;
   public static ReflectorClass ForgeKeyBinding;
   public static ReflectorMethod ForgeKeyBinding_setKeyConflictContext;
   public static ReflectorMethod ForgeKeyBinding_setKeyModifierAndCode;
   public static ReflectorMethod ForgeKeyBinding_getKeyModifier;
   public static ReflectorClass ForgeRarity;
   public static ReflectorMethod ForgeRarity_getStyleModifier;
   public static ReflectorClass ForgeTicket;
   public static ReflectorField ForgeTicket_forceTicks;
   public static ReflectorMethod ForgeTicket_isForceTicks;
   public static ReflectorClass IForgeBlockEntity;
   public static ReflectorMethod IForgeBlockEntity_getRenderBoundingBox;
   public static ReflectorMethod IForgeBlockEntity_hasCustomOutlineRendering;
   public static ReflectorClass IForgeDimensionSpecialEffects;
   public static ReflectorMethod IForgeDimensionSpecialEffects_adjustLightmapColors;
   public static ReflectorMethod IForgeDimensionSpecialEffects_renderClouds;
   public static ReflectorMethod IForgeDimensionSpecialEffects_renderSky;
   public static ReflectorMethod IForgeDimensionSpecialEffects_tickRain;
   public static ReflectorMethod IForgeDimensionSpecialEffects_renderSnowAndRain;
   public static ReflectorClass ForgeVersion;
   public static ReflectorMethod ForgeVersion_getVersion;
   public static ReflectorMethod ForgeVersion_getSpec;
   public static ReflectorClass ImmediateWindowHandler;
   public static ReflectorMethod ImmediateWindowHandler_positionWindow;
   public static ReflectorMethod ImmediateWindowHandler_setupMinecraftWindow;
   public static ReflectorMethod ImmediateWindowHandler_updateFBSize;
   public static ReflectorClass ItemDecoratorHandler;
   public static ReflectorMethod ItemDecoratorHandler_of;
   public static ReflectorMethod ItemDecoratorHandler_render;
   public static ReflectorClass ForgeItemModelShaper;
   public static ReflectorConstructor ForgeItemModelShaper_Constructor;
   public static ReflectorClass GeometryLoaderManager;
   public static ReflectorMethod GeometryLoaderManager_init;
   public static ReflectorClass KeyConflictContext;
   public static ReflectorField KeyConflictContext_IN_GAME;
   public static ReflectorClass KeyModifier;
   public static ReflectorMethod KeyModifier_valueFromString;
   public static ReflectorField KeyModifier_NONE;
   public static ReflectorClass Launch;
   public static ReflectorField Launch_blackboard;
   public static ReflectorClass MinecraftForge;
   public static ReflectorField MinecraftForge_EVENT_BUS;
   public static ReflectorClass ModContainer;
   public static ReflectorMethod ModContainer_getModId;
   public static ReflectorClass ModList;
   public static ReflectorField ModList_mods;
   public static ReflectorMethod ModList_get;
   public static ReflectorClass ModListScreen;
   public static ReflectorConstructor ModListScreen_Constructor;
   public static ReflectorClass ModLoader;
   public static ReflectorMethod ModLoader_get;
   public static ReflectorMethod ModLoader_postEvent;
   public static ReflectorClass TitleScreenModUpdateIndicator;
   public static ReflectorMethod TitleScreenModUpdateIndicator_init;
   public static ReflectorClass PartEntity;
   public static ReflectorClass PlayLevelSoundEvent;
   public static ReflectorMethod PlayLevelSoundEvent_getSound;
   public static ReflectorMethod PlayLevelSoundEvent_getSource;
   public static ReflectorMethod PlayLevelSoundEvent_getNewVolume;
   public static ReflectorMethod PlayLevelSoundEvent_getNewPitch;
   public static ReflectorClass QuadBakingVertexConsumer;
   public static ReflectorField QuadBakingVertexConsumer_QUAD_DATA_SIZE;
   public static ReflectorClass QuadTransformers;
   public static ReflectorMethod QuadTransformers_applyingLightmap;
   public static ReflectorMethod QuadTransformers_applyingColor;
   public static ReflectorClass IQuadTransformer;
   public static ReflectorField IQuadTransformer_STRIDE;
   public static ReflectorMethod IQuadTransformer_processInPlace;
   public static ReflectorClass RegisterShadersEvent;
   public static ReflectorConstructor RegisterShadersEvent_Constructor;
   public static ReflectorClass RenderBlockScreenEffectEvent_OverlayType;
   public static ReflectorField RenderBlockScreenEffectEvent_OverlayType_BLOCK;
   public static ReflectorClass CustomizeGuiOverlayEvent_BossEventProgress;
   public static ReflectorMethod CustomizeGuiOverlayEvent_BossEventProgress_getIncrement;
   public static ReflectorClass RenderItemInFrameEvent;
   public static ReflectorConstructor RenderItemInFrameEvent_Constructor;
   public static ReflectorClass RenderLevelStageEvent_Stage;
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_SKY;
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_SOLID_BLOCKS;
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_CUTOUT_MIPPED_BLOCKS_BLOCKS;
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_CUTOUT_BLOCKS;
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_ENTITIES;
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_BLOCK_ENTITIES;
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_TRANSLUCENT_BLOCKS;
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_TRIPWIRE_BLOCKS;
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_PARTICLES;
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_WEATHER;
   public static ReflectorField RenderLevelStageEvent_Stage_AFTER_LEVEL;
   public static ReflectorMethod RenderLevelStageEvent_dispatch;
   public static ReflectorClass RenderLivingEvent_Pre;
   public static ReflectorConstructor RenderLivingEvent_Pre_Constructor;
   public static ReflectorClass RenderLivingEvent_Post;
   public static ReflectorConstructor RenderLivingEvent_Post_Constructor;
   public static ReflectorClass RenderNameTagEvent;
   public static ReflectorConstructor RenderNameTagEvent_Constructor;
   public static ReflectorMethod RenderNameTagEvent_getContent;
   public static ReflectorClass RenderTooltipEvent;
   public static ReflectorMethod RenderTooltipEvent_getFont;
   public static ReflectorMethod RenderTooltipEvent_getX;
   public static ReflectorMethod RenderTooltipEvent_getY;
   public static ReflectorClass RenderTooltipEvent_Color;
   public static ReflectorMethod RenderTooltipEvent_Color_getBackgroundStart;
   public static ReflectorMethod RenderTooltipEvent_Color_getBackgroundEnd;
   public static ReflectorMethod RenderTooltipEvent_Color_getBorderStart;
   public static ReflectorMethod RenderTooltipEvent_Color_getBorderEnd;
   public static ReflectorClass ScreenshotEvent;
   public static ReflectorMethod ScreenshotEvent_getCancelMessage;
   public static ReflectorMethod ScreenshotEvent_getScreenshotFile;
   public static ReflectorMethod ScreenshotEvent_getResultMessage;
   public static ReflectorClass ServerLifecycleHooks;
   public static ReflectorMethod ServerLifecycleHooks_handleServerAboutToStart;
   public static ReflectorMethod ServerLifecycleHooks_handleServerStarting;
   public static ReflectorClass TerrainParticle;
   public static ReflectorMethod TerrainParticle_updateSprite;
   public static ReflectorClass TooltipRenderUtil;
   public static ReflectorMethod TooltipRenderUtil_renderTooltipBackground10;
   public static ReflectorClass LevelEvent_Load;
   public static ReflectorConstructor LevelEvent_Load_Constructor;
   private static boolean logVanilla;
   public static ReflectorClass AbstractArrow;
   public static ReflectorField AbstractArrow_inGround;
   public static ReflectorClass BannerBlockEntity;
   public static ReflectorField BannerBlockEntity_customName;
   public static ReflectorClass BaseContainerBlockEntity;
   public static ReflectorField BaseContainerBlockEntity_customName;
   public static ReflectorClass Enchantments;
   public static ReflectorFields Enchantments_ResourceKeys;
   public static ReflectorClass EntityItem;
   public static ReflectorField EntityItem_ITEM;
   public static ReflectorClass EnderDragonRenderer;
   public static ReflectorField EnderDragonRenderer_model;
   public static ReflectorClass GuiEnchantment;
   public static ReflectorField GuiEnchantment_bookModel;
   public static ReflectorClass ItemOverride;
   public static ReflectorField ItemOverride_listResourceValues;
   public static ReflectorClass ItemStack;
   public static ReflectorField ItemStack_components;
   public static ReflectorClass LayerLlamaDecor;
   public static ReflectorField LayerLlamaDecor_model;
   public static ReflectorClass Minecraft;
   public static ReflectorField Minecraft_debugFPS;
   public static ReflectorField Minecraft_fontResourceManager;
   public static ReflectorClass ModelArmorStand;
   public static ReflectorFields ModelArmorStand_ModelRenderers;
   public static ReflectorClass ModelBee;
   public static ReflectorFields ModelBee_ModelRenderers;
   public static ReflectorClass ModelBlaze;
   public static ReflectorField ModelBlaze_blazeHead;
   public static ReflectorField ModelBlaze_blazeSticks;
   public static ReflectorClass ModelBoar;
   public static ReflectorFields ModelBoar_ModelRenderers;
   public static ReflectorClass ModelBook;
   public static ReflectorField ModelBook_root;
   public static ReflectorClass ModelChicken;
   public static ReflectorFields ModelChicken_ModelRenderers;
   public static ReflectorClass ModelDragon;
   public static ReflectorFields ModelDragon_ModelRenderers;
   public static ReflectorClass RenderEnderCrystal;
   public static ReflectorFields RenderEnderCrystal_modelRenderers;
   public static ReflectorClass ModelEvokerFangs;
   public static ReflectorFields ModelEvokerFangs_ModelRenderers;
   public static ReflectorClass ModelGuardian;
   public static ReflectorField ModelGuardian_spines;
   public static ReflectorField ModelGuardian_tail;
   public static ReflectorClass ModelDragonHead;
   public static ReflectorField ModelDragonHead_head;
   public static ReflectorField ModelDragonHead_jaw;
   public static ReflectorClass ModelHorse;
   public static ReflectorFields ModelHorse_ModelRenderers;
   public static ReflectorClass ModelHorseChests;
   public static ReflectorFields ModelHorseChests_ModelRenderers;
   public static ReflectorClass ModelIllager;
   public static ReflectorFields ModelIllager_ModelRenderers;
   public static ReflectorClass ModelIronGolem;
   public static ReflectorFields ModelIronGolem_ModelRenderers;
   public static ReflectorClass ModelAxolotl;
   public static ReflectorFields ModelAxolotl_ModelRenderers;
   public static ReflectorClass ModelFox;
   public static ReflectorFields ModelFox_ModelRenderers;
   public static ReflectorClass ModelLeashKnot;
   public static ReflectorField ModelLeashKnot_knotRenderer;
   public static ReflectorClass RenderLeashKnot;
   public static ReflectorField RenderLeashKnot_leashKnotModel;
   public static ReflectorClass ModelLlama;
   public static ReflectorFields ModelLlama_ModelRenderers;
   public static ReflectorClass ModelOcelot;
   public static ReflectorFields ModelOcelot_ModelRenderers;
   public static ReflectorClass ModelPhantom;
   public static ReflectorFields ModelPhantom_ModelRenderers;
   public static ReflectorClass ModelPiglin;
   public static ReflectorFields ModelPiglin_ModelRenderers;
   public static ReflectorClass ModelPiglinHead;
   public static ReflectorFields ModelPiglinHead_ModelRenderers;
   public static ReflectorClass ModelQuadruped;
   public static ReflectorFields ModelQuadruped_ModelRenderers;
   public static ReflectorClass ModelRabbit;
   public static ReflectorFields ModelRabbit_ModelRenderers;
   public static ReflectorClass ModelShulker;
   public static ReflectorFields ModelShulker_ModelRenderers;
   public static ReflectorClass ModelShield;
   public static ReflectorFields ModelShield_ModelRenderers;
   public static ReflectorClass ModelSkull;
   public static ReflectorFields ModelSkull_renderers;
   public static ReflectorClass ModelTadpole;
   public static ReflectorFields ModelTadpole_ModelRenderers;
   public static ReflectorClass ModelTrident;
   public static ReflectorField ModelTrident_root;
   public static ReflectorClass ModelTurtle;
   public static ReflectorField ModelTurtle_body2;
   public static ReflectorClass ModelVex;
   public static ReflectorField ModelVex_leftWing;
   public static ReflectorField ModelVex_rightWing;
   public static ReflectorClass ModelWolf;
   public static ReflectorFields ModelWolf_ModelRenderers;
   public static ReflectorClass OptiFineResourceLocator;
   public static ReflectorMethod OptiFineResourceLocator_getOptiFineResourceStream;
   public static ReflectorClass Potion;
   public static ReflectorField Potion_baseName;
   public static ReflectorClass RenderBoat;
   public static ReflectorField RenderBoat_boatResources;
   public static ReflectorClass RenderEvokerFangs;
   public static ReflectorField RenderEvokerFangs_model;
   public static ReflectorClass RenderLlamaSpit;
   public static ReflectorField RenderLlamaSpit_model;
   public static ReflectorClass RenderPufferfish;
   public static ReflectorField RenderPufferfish_modelSmall;
   public static ReflectorField RenderPufferfish_modelMedium;
   public static ReflectorField RenderPufferfish_modelBig;
   public static ReflectorClass RenderMinecart;
   public static ReflectorField RenderMinecart_modelMinecart;
   public static ReflectorClass RenderShulkerBullet;
   public static ReflectorField RenderShulkerBullet_model;
   public static ReflectorClass RenderTrident;
   public static ReflectorField RenderTrident_modelTrident;
   public static ReflectorClass RenderTropicalFish;
   public static ReflectorField RenderTropicalFish_modelA;
   public static ReflectorField RenderTropicalFish_modelB;
   public static ReflectorClass RenderWindCharge;
   public static ReflectorField RenderWindCharge_model;
   public static ReflectorClass TropicalFishPatternLayer;
   public static ReflectorField TropicalFishPatternLayer_modelA;
   public static ReflectorField TropicalFishPatternLayer_modelB;
   public static ReflectorClass RenderWitherSkull;
   public static ReflectorField RenderWitherSkull_model;
   public static ReflectorClass SimpleBakedModel;
   public static ReflectorField SimpleBakedModel_generalQuads;
   public static ReflectorField SimpleBakedModel_faceQuads;
   public static ReflectorClass TileEntityBannerRenderer;
   public static ReflectorFields TileEntityBannerRenderer_modelRenderers;
   public static ReflectorClass TileEntityBedRenderer;
   public static ReflectorField TileEntityBedRenderer_headModel;
   public static ReflectorField TileEntityBedRenderer_footModel;
   public static ReflectorClass TileEntityBellRenderer;
   public static ReflectorField TileEntityBellRenderer_modelRenderer;
   public static ReflectorClass TileEntityBeacon;
   public static ReflectorField TileEntityBeacon_customName;
   public static ReflectorField TileEntityBeacon_levels;
   public static ReflectorClass TileEntityChestRenderer;
   public static ReflectorFields TileEntityChestRenderer_modelRenderers;
   public static ReflectorClass TileEntityConduitRenderer;
   public static ReflectorFields TileEntityConduitRenderer_modelRenderers;
   public static ReflectorClass TileEntityDecoratedPotRenderer;
   public static ReflectorFields TileEntityDecoratedPotRenderer_modelRenderers;
   public static ReflectorClass TileEntityEnchantmentTableRenderer;
   public static ReflectorField TileEntityEnchantmentTableRenderer_modelBook;
   public static ReflectorClass TileEntityHangingSignRenderer;
   public static ReflectorField TileEntityHangingSignRenderer_hangingSignModels;
   public static ReflectorClass TileEntityLecternRenderer;
   public static ReflectorField TileEntityLecternRenderer_modelBook;
   public static ReflectorClass TileEntityShulkerBoxRenderer;
   public static ReflectorField TileEntityShulkerBoxRenderer_model;
   public static ReflectorClass TileEntitySignRenderer;
   public static ReflectorField TileEntitySignRenderer_signModels;

   public static void callVoid(ReflectorMethod refMethod, Object... params) {
      try {
         Method m = refMethod.getTargetMethod();
         if (m == null) {
            return;
         }

         m.invoke((Object)null, params);
      } catch (Throwable var3) {
         handleException(var3, (Object)null, refMethod, params);
      }

   }

   public static boolean callBoolean(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return false;
         } else {
            Boolean retVal = (Boolean)method.invoke((Object)null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return false;
      }
   }

   public static int callInt(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0;
         } else {
            Integer retVal = (Integer)method.invoke((Object)null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return 0;
      }
   }

   public static long callLong(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0L;
         } else {
            Long retVal = (Long)method.invoke((Object)null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return 0L;
      }
   }

   public static float callFloat(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0.0F;
         } else {
            Float retVal = (Float)method.invoke((Object)null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return 0.0F;
      }
   }

   public static double callDouble(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0.0;
         } else {
            Double retVal = (Double)method.invoke((Object)null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return 0.0;
      }
   }

   public static String callString(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return null;
         } else {
            String retVal = (String)method.invoke((Object)null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return null;
      }
   }

   public static Object call(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return null;
         } else {
            Object retVal = method.invoke((Object)null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
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
         if (method == null) {
            return null;
         } else {
            String retVal = (String)method.invoke(obj, params);
            return retVal;
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return null;
      }
   }

   public static Object call(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return null;
         } else {
            Object retVal = method.invoke(obj, params);
            return retVal;
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return null;
      }
   }

   public static Object getFieldValue(ReflectorField refField) {
      return getFieldValue((Object)null, refField);
   }

   public static Object getFieldValue(Object obj, ReflectorField refField) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return null;
         } else {
            Object value = field.get(obj);
            return value;
         }
      } catch (Throwable var4) {
         Log.error("", var4);
         return null;
      }
   }

   public static boolean getFieldValueBoolean(Object obj, ReflectorField refField, boolean def) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return def;
         } else {
            boolean value = field.getBoolean(obj);
            return value;
         }
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
         if (field == null) {
            return def;
         } else {
            float value = field.getFloat(obj);
            return value;
         }
      } catch (Throwable var5) {
         Log.error("", var5);
         return def;
      }
   }

   public static int getFieldValueInt(ReflectorField refField, int def) {
      return getFieldValueInt((Object)null, refField, def);
   }

   public static int getFieldValueInt(Object obj, ReflectorField refField, int def) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return def;
         } else {
            int value = field.getInt(obj);
            return value;
         }
      } catch (Throwable var5) {
         Log.error("", var5);
         return def;
      }
   }

   public static long getFieldValueLong(Object obj, ReflectorField refField, long def) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return def;
         } else {
            long value = field.getLong(obj);
            return value;
         }
      } catch (Throwable var7) {
         Log.error("", var7);
         return def;
      }
   }

   public static boolean setFieldValue(ReflectorField refField, Object value) {
      return setFieldValue((Object)null, refField, value);
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
      return setFieldValueInt((Object)null, refField, value);
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
            Object ret = call(eventBus, EventBus_post, event);
            if (!(ret instanceof Boolean)) {
               return false;
            } else {
               Boolean retBool = (Boolean)ret;
               return retBool;
            }
         }
      }
   }

   public static Object newInstance(ReflectorConstructor constr, Object... params) {
      Constructor c = constr.getTargetConstructor();
      if (c == null) {
         return null;
      } else {
         try {
            Object obj = c.newInstance(params);
            return obj;
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
         for(int i = 0; i < cTypes.length; ++i) {
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

      Log.dbg(callType + staticStr + " " + className + "." + methodName + "(" + ArrayUtils.arrayToString(params) + ") => " + String.valueOf(retVal));
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

      Log.dbg(accessType + staticStr + " " + className + "." + fieldName + " => " + String.valueOf(val));
   }

   private static void handleException(Throwable e, Object obj, ReflectorMethod refMethod, Object[] params) {
      if (e instanceof InvocationTargetException) {
         Throwable cause = e.getCause();
         if (cause instanceof RuntimeException) {
            RuntimeException causeRuntime = (RuntimeException)cause;
            throw causeRuntime;
         } else {
            Log.error("", e);
         }
      } else {
         Log.warn("*** Exception outside of method ***");
         Log.warn("Method deactivated: " + String.valueOf(refMethod.getTargetMethod()));
         refMethod.deactivate();
         if (e instanceof IllegalArgumentException) {
            Log.warn("*** IllegalArgumentException ***");
            Log.warn("Method: " + String.valueOf(refMethod.getTargetMethod()));
            Log.warn("Object: " + String.valueOf(obj));
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
         Log.warn("Constructor deactivated: " + String.valueOf(refConstr.getTargetConstructor()));
         refConstr.deactivate();
         if (e instanceof IllegalArgumentException) {
            Log.warn("*** IllegalArgumentException ***");
            Log.warn("Constructor: " + String.valueOf(refConstr.getTargetConstructor()));
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

         for(int i = 0; i < classes.length; ++i) {
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

      for(int i = 0; i < rfs.length; ++i) {
         rfs[i] = new ReflectorField(parentClass, fieldType, i);
      }

      return rfs;
   }

   private static boolean registerResolvable(final String str) {
      IResolvable ir = new IResolvable() {
         public void resolve() {
            Reflector.LOGGER.info("[OptiFine] " + str);
         }
      };
      ReflectorResolver.register(ir);
      return true;
   }

   static {
      BrandingControl_getBrandings = new ReflectorMethod(BrandingControl, "getBrandings");
      BrandingControl_getClientBranding = new ReflectorMethod(BrandingControl, "getClientBranding");
      BrandingControl_forEachLine = new ReflectorMethod(BrandingControl, "forEachLine");
      BrandingControl_forEachAboveCopyrightLine = new ReflectorMethod(BrandingControl, "forEachAboveCopyrightLine");
      IClientBlockExtensions = new ReflectorClass("net.minecraftforge.client.extensions.common.IClientBlockExtensions");
      IClientBlockExtensions_ofBS = IClientBlockExtensions.makeMethod("of", new Class[]{BlockState.class});
      IClientItemExtensions = new ReflectorClass("net.minecraftforge.client.extensions.common.IClientItemExtensions");
      IClientItemExtensions_ofIS = IClientItemExtensions.makeMethod("of", new Class[]{ItemStack.class});
      IClientItemExtensions_ofI = IClientItemExtensions.makeMethod("of", new Class[]{Item.class});
      IClientItemExtensions_getFont = IClientItemExtensions.makeMethod("getFont");
      IClientItemExtensions_FontContext = new ReflectorClass("net.minecraftforge.client.extensions.common.IClientItemExtensions$FontContext");
      IClientItemExtensions_FontContext_SELECTED_ITEM_NAME = IClientItemExtensions_FontContext.makeField("SELECTED_ITEM_NAME");
      CapabilityProvider = new ReflectorClass("net.minecraftforge.common.capabilities.CapabilityProvider");
      CapabilityProvider_gatherCapabilities = new ReflectorMethod(CapabilityProvider, "gatherCapabilities", new Class[0]);
      ClientModLoader = new ReflectorClass("net.minecraftforge.client.loading.ClientModLoader");
      ClientModLoader_isLoading = new ReflectorMethod(ClientModLoader, "isLoading");
      ChunkDataEvent_Save = new ReflectorClass("net.minecraftforge.event.level.ChunkDataEvent$Save");
      ChunkDataEvent_Save_Constructor = new ReflectorConstructor(ChunkDataEvent_Save, new Class[]{ChunkAccess.class, LevelAccessor.class, CompoundTag.class});
      ChunkEvent_Load = new ReflectorClass("net.minecraftforge.event.level.ChunkEvent$Load");
      ChunkEvent_Load_Constructor = new ReflectorConstructor(ChunkEvent_Load, new Class[]{ChunkAccess.class, Boolean.TYPE});
      ChunkEvent_Unload = new ReflectorClass("net.minecraftforge.event.level.ChunkEvent$Unload");
      ChunkEvent_Unload_Constructor = new ReflectorConstructor(ChunkEvent_Unload, new Class[]{ChunkAccess.class});
      ColorResolverManager = new ReflectorClass("net.minecraftforge.client.ColorResolverManager");
      ColorResolverManager_registerBlockTintCaches = ColorResolverManager.makeMethod("registerBlockTintCaches");
      CrashReportAnalyser = new ReflectorClass("net.minecraftforge.logging.CrashReportAnalyser");
      CrashReportAnalyser_appendSuspectedMods = new ReflectorMethod(CrashReportAnalyser, "appendSuspectedMods");
      CrashReportExtender = new ReflectorClass("net.minecraftforge.logging.CrashReportExtender");
      CrashReportExtender_extendSystemReport = new ReflectorMethod(CrashReportExtender, "extendSystemReport");
      CrashReportExtender_generateEnhancedStackTraceT = new ReflectorMethod(CrashReportExtender, "generateEnhancedStackTrace", new Class[]{Throwable.class});
      CrashReportExtender_generateEnhancedStackTraceSTE = new ReflectorMethod(CrashReportExtender, "generateEnhancedStackTrace", new Class[]{StackTraceElement[].class});
      EntityRenderersEvent_AddLayers = new ReflectorClass("net.minecraftforge.client.event.EntityRenderersEvent$AddLayers");
      EntityRenderersEvent_AddLayers_Constructor = EntityRenderersEvent_AddLayers.makeConstructor(new Class[]{Map.class, Map.class, EntityRendererProvider.Context.class});
      EntityRenderersEvent_CreateSkullModels = new ReflectorClass("net.minecraftforge.client.event.EntityRenderersEvent$CreateSkullModels");
      EntityRenderersEvent_CreateSkullModels_Constructor = EntityRenderersEvent_CreateSkullModels.makeConstructor(new Class[]{ImmutableMap.Builder.class, EntityModelSet.class});
      EntityLeaveLevelEvent = new ReflectorClass("net.minecraftforge.event.entity.EntityLeaveLevelEvent");
      EntityLeaveLevelEvent_Constructor = new ReflectorConstructor(EntityLeaveLevelEvent, new Class[]{Entity.class, Level.class});
      ViewportEvent_ComputeCameraAngles = new ReflectorClass("net.minecraftforge.client.event.ViewportEvent$ComputeCameraAngles");
      ViewportEvent_ComputeCameraAngles_getYaw = new ReflectorMethod(ViewportEvent_ComputeCameraAngles, "getYaw");
      ViewportEvent_ComputeCameraAngles_getPitch = new ReflectorMethod(ViewportEvent_ComputeCameraAngles, "getPitch");
      ViewportEvent_ComputeCameraAngles_getRoll = new ReflectorMethod(ViewportEvent_ComputeCameraAngles, "getRoll");
      EntityJoinLevelEvent = new ReflectorClass("net.minecraftforge.event.entity.EntityJoinLevelEvent");
      EntityJoinLevelEvent_Constructor = new ReflectorConstructor(EntityJoinLevelEvent, new Class[]{Entity.class, Level.class});
      Event = new ReflectorClass("net.minecraftforge.eventbus.api.Event");
      Event_isCanceled = new ReflectorMethod(Event, "isCanceled");
      Event_getResult = new ReflectorMethod(Event, "getResult");
      EventBus = new ReflectorClass("net.minecraftforge.eventbus.api.IEventBus");
      EventBus_post = new ReflectorMethod(EventBus, "post", new Class[]{Event.class});
      Event_Result = new ReflectorClass("net.minecraftforge.eventbus.api.Event$Result");
      Event_Result_DENY = new ReflectorField(Event_Result, "DENY");
      Event_Result_ALLOW = new ReflectorField(Event_Result, "ALLOW");
      Event_Result_DEFAULT = new ReflectorField(Event_Result, "DEFAULT");
      FluidType = new ReflectorClass("net.minecraftforge.fluids.FluidType");
      FluidType_isAir = FluidType.makeMethod("isAir");
      ForgeModelBlockRenderer = new ReflectorClass("net.minecraftforge.client.model.lighting.ForgeModelBlockRenderer");
      ForgeModelBlockRenderer_Constructor = new ReflectorConstructor(ForgeModelBlockRenderer, new Class[]{BlockColors.class});
      ForgeBlockModelShapes = new ReflectorClass(BlockModelShaper.class);
      ForgeBlockModelShapes_getTexture3 = new ReflectorMethod(ForgeBlockModelShapes, "getTexture", new Class[]{BlockState.class, Level.class, BlockPos.class});
      ForgeBlockElementFace = new ReflectorClass(BlockElementFace.class);
      ForgeBlockElementFace_getFaceData = ForgeBlockElementFace.makeMethod("getFaceData");
      IForgeBlockState = new ReflectorClass("net.minecraftforge.common.extensions.IForgeBlockState");
      IForgeBlockState_getLightEmission = new ReflectorMethod(IForgeBlockState, "getLightEmission", new Class[]{BlockGetter.class, BlockPos.class});
      IForgeBlockState_getSoundType3 = new ReflectorMethod(IForgeBlockState, "getSoundType", new Class[]{LevelReader.class, BlockPos.class, Entity.class});
      IForgeBlockState_getStateAtViewpoint = new ReflectorMethod(IForgeBlockState, "getStateAtViewpoint");
      IForgeBlockState_shouldDisplayFluidOverlay = new ReflectorMethod(IForgeBlockState, "shouldDisplayFluidOverlay");
      IForgeEntity = new ReflectorClass("net.minecraftforge.common.extensions.IForgeEntity");
      IForgeEntity_canUpdate = new ReflectorMethod(IForgeEntity, "canUpdate", new Class[0]);
      IForgeEntity_getEyeInFluidType = new ReflectorMethod(IForgeEntity, "getEyeInFluidType");
      IForgeEntity_getParts = new ReflectorMethod(IForgeEntity, "getParts");
      IForgeEntity_hasCustomOutlineRendering = new ReflectorMethod(IForgeEntity, "hasCustomOutlineRendering");
      IForgeEntity_isMultipartEntity = new ReflectorMethod(IForgeEntity, "isMultipartEntity");
      IForgeEntity_onAddedToWorld = new ReflectorMethod(IForgeEntity, "onAddedToWorld");
      IForgeEntity_onRemovedFromWorld = new ReflectorMethod(IForgeEntity, "onRemovedFromWorld");
      IForgeEntity_shouldRiderSit = new ReflectorMethod(IForgeEntity, "shouldRiderSit");
      IForgePlayer = new ReflectorClass("net.minecraftforge.common.extensions.IForgePlayer");
      IForgePlayer_getEntityReach = IForgePlayer.makeMethod("getEntityReach");
      IForgePlayer_getBlockReach = IForgePlayer.makeMethod("getBlockReach");
      ForgeChunkHolder = new ReflectorClass(ChunkHolder.class);
      ForgeChunkHolder_currentlyLoading = new ReflectorField(ForgeChunkHolder, "currentlyLoading");
      ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
      ForgeEventFactory_canEntityDespawn = new ReflectorMethod(ForgeEventFactory, "canEntityDespawn");
      ForgeEventFactory_fireChunkTicketLevelUpdated = new ReflectorMethod(ForgeEventFactory, "fireChunkTicketLevelUpdated");
      ForgeEventFactory_fireChunkWatch = new ReflectorMethod(ForgeEventFactory, "fireChunkWatch");
      ForgeEventFactory_fireChunkUnWatch = new ReflectorMethod(ForgeEventFactory, "fireChunkUnWatch");
      ForgeEventFactory_getMaxSpawnPackSize = new ReflectorMethod(ForgeEventFactory, "getMaxSpawnPackSize");
      ForgeEventFactory_getMobGriefingEvent = new ReflectorMethod(ForgeEventFactory, "getMobGriefingEvent");
      ForgeEventFactory_onChunkDataSave = new ReflectorMethod(ForgeEventFactory, "onChunkDataSave");
      ForgeEventFactory_onChunkLoad = new ReflectorMethod(ForgeEventFactory, "onChunkLoad");
      ForgeEventFactory_onChunkUnload = new ReflectorMethod(ForgeEventFactory, "onChunkUnload");
      ForgeEventFactory_onPlaySoundAtEntity = new ReflectorMethod(ForgeEventFactory, "onPlaySoundAtEntity");
      ForgeEventFactory_onPlaySoundAtPosition = new ReflectorMethod(ForgeEventFactory, "onPlaySoundAtPosition");
      ForgeEventFactoryClient = new ReflectorClass("net.minecraftforge.client.event.ForgeEventFactoryClient");
      ForgeEventFactoryClient_gatherLayers = new ReflectorMethod(ForgeEventFactoryClient, "gatherLayers");
      ForgeFaceData = new ReflectorClass(ForgeFaceData.class);
      ForgeFaceData_calculateNormals = ForgeFaceData.makeMethod("calculateNormals");
      ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
      ForgeHooks_onDifficultyChange = new ReflectorMethod(ForgeHooks, "onDifficultyChange");
      ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
      ForgeHooks_onLivingChangeTarget = new ReflectorMethod(ForgeHooks, "onLivingChangeTarget");
      ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
      ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
      ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
      ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
      ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
      ForgeHooksClient_calculateFaceWithoutAO = new ReflectorMethod(ForgeHooksClient, "calculateFaceWithoutAO");
      ForgeHooksClient_onCustomizeBossEventProgress = new ReflectorMethod(ForgeHooksClient, "onCustomizeBossEventProgress");
      ForgeHooksClient_onRenderTooltipColor = new ReflectorMethod(ForgeHooksClient, "onRenderTooltipColor");
      ForgeHooksClient_dispatchRenderStageRT = new ReflectorMethod(ForgeHooksClient, "dispatchRenderStage", new Class[]{RenderType.class, LevelRenderer.class, PoseStack.class, Matrix4f.class, Integer.TYPE, Camera.class, Frustum.class});
      ForgeHooksClient_drawScreen = new ReflectorMethod(ForgeHooksClient, "drawScreen");
      ForgeHooksClient_fillNormal = new ReflectorMethod(ForgeHooksClient, "fillNormal", new Class[]{int[].class, Direction.class});
      ForgeHooksClient_gatherTooltipComponents6 = new ReflectorMethod(ForgeHooksClient, "gatherTooltipComponents", new Class[]{ItemStack.class, List.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Font.class});
      ForgeHooksClient_gatherTooltipComponents7 = new ReflectorMethod(ForgeHooksClient, "gatherTooltipComponents", new Class[]{ItemStack.class, List.class, Optional.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Font.class});
      ForgeHooksClient_onKeyInput = new ReflectorMethod(ForgeHooksClient, "onKeyInput");
      ForgeHooksClient_getFogColor = new ReflectorMethod(ForgeHooksClient, "getFogColor");
      ForgeHooksClient_handleCameraTransforms = new ReflectorMethod(ForgeHooksClient, "handleCameraTransforms");
      ForgeHooksClient_getArmorModel = new ReflectorMethod(ForgeHooksClient, "getArmorModel");
      ForgeHooksClient_getArmorTexture = new ReflectorMethod(ForgeHooksClient, "getArmorTexture");
      ForgeHooksClient_getFluidSprites = new ReflectorMethod(ForgeHooksClient, "getFluidSprites");
      ForgeHooksClient_getFieldOfViewModifier = new ReflectorMethod(ForgeHooksClient, "getFieldOfViewModifier");
      ForgeHooksClient_getFieldOfView = new ReflectorMethod(ForgeHooksClient, "getFieldOfView");
      ForgeHooksClient_getGuiFarPlane = new ReflectorMethod(ForgeHooksClient, "getGuiFarPlane");
      ForgeHooksClient_getShaderImportLocation = new ReflectorMethod(ForgeHooksClient, "getShaderImportLocation");
      ForgeHooksClient_isNameplateInRenderDistance = new ReflectorMethod(ForgeHooksClient, "isNameplateInRenderDistance");
      ForgeHooksClient_loadEntityShader = new ReflectorMethod(ForgeHooksClient, "loadEntityShader");
      ForgeHooksClient_loadTextureAtlasSprite = new ReflectorMethod(ForgeHooksClient, "loadTextureAtlasSprite");
      ForgeHooksClient_loadSpriteContents = new ReflectorMethod(ForgeHooksClient, "loadSpriteContents");
      ForgeHooksClient_makeParticleRenderTypeComparator = new ReflectorMethod(ForgeHooksClient, "makeParticleRenderTypeComparator");
      ForgeHooksClient_onCameraSetup = new ReflectorMethod(ForgeHooksClient, "onCameraSetup");
      ForgeHooksClient_onDrawHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawHighlight");
      ForgeHooksClient_onFogRender = new ReflectorMethod(ForgeHooksClient, "onFogRender");
      ForgeHooksClient_onRegisterAdditionalModels = new ReflectorMethod(ForgeHooksClient, "onRegisterAdditionalModels");
      ForgeHooksClient_onRenderTooltipPre = new ReflectorMethod(ForgeHooksClient, "onRenderTooltipPre");
      ForgeHooksClient_onScreenCharTypedPre = new ReflectorMethod(ForgeHooksClient, "onScreenCharTypedPre");
      ForgeHooksClient_onScreenCharTypedPost = new ReflectorMethod(ForgeHooksClient, "onScreenCharTypedPost");
      ForgeHooksClient_onScreenKeyPressedPre = new ReflectorMethod(ForgeHooksClient, "onScreenKeyPressedPre");
      ForgeHooksClient_onScreenKeyPressedPost = new ReflectorMethod(ForgeHooksClient, "onScreenKeyPressedPost");
      ForgeHooksClient_onScreenKeyReleasedPre = new ReflectorMethod(ForgeHooksClient, "onScreenKeyReleasedPre");
      ForgeHooksClient_onScreenKeyReleasedPost = new ReflectorMethod(ForgeHooksClient, "onScreenKeyReleasedPost");
      ForgeHooksClient_onScreenshot = new ReflectorMethod(ForgeHooksClient, "onScreenshot");
      ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
      ForgeHooksClient_renderBlockOverlay = new ReflectorMethod(ForgeHooksClient, "renderBlockOverlay");
      ForgeHooksClient_renderFireOverlay = new ReflectorMethod(ForgeHooksClient, "renderFireOverlay");
      ForgeHooksClient_renderWaterOverlay = new ReflectorMethod(ForgeHooksClient, "renderWaterOverlay");
      ForgeHooksClient_renderMainMenu = new ReflectorMethod(ForgeHooksClient, "renderMainMenu");
      ForgeHooksClient_renderSpecificFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderSpecificFirstPersonHand");
      ForgeHooksClient_shouldCauseReequipAnimation = new ReflectorMethod(ForgeHooksClient, "shouldCauseReequipAnimation");
      ForgeConfig = new ReflectorClass("net.minecraftforge.common.ForgeConfig");
      ForgeConfig_CLIENT = new ReflectorField(ForgeConfig, "CLIENT");
      ForgeConfig_Client = new ReflectorClass("net.minecraftforge.common.ForgeConfig$Client");
      ForgeConfig_Client_calculateAllNormals = new ReflectorField(ForgeConfig_Client, "calculateAllNormals");
      ForgeConfig_Client_forgeLightPipelineEnabled = new ReflectorField(ForgeConfig_Client, "experimentalForgeLightPipelineEnabled");
      ForgeConfig_Client_useCombinedDepthStencilAttachment = new ReflectorField(ForgeConfig_Client, "useCombinedDepthStencilAttachment");
      ForgeConfigSpec = new ReflectorClass("net.minecraftforge.common.ForgeConfigSpec");
      ForgeConfigSpec_childConfig = new ReflectorField(ForgeConfigSpec, "childConfig");
      ForgeConfigSpec_ConfigValue = new ReflectorClass("net.minecraftforge.common.ForgeConfigSpec$ConfigValue");
      ForgeConfigSpec_ConfigValue_defaultSupplier = new ReflectorField(ForgeConfigSpec_ConfigValue, "defaultSupplier");
      ForgeConfigSpec_ConfigValue_spec = new ReflectorField(ForgeConfigSpec_ConfigValue, "spec");
      ForgeConfigSpec_ConfigValue_get = new ReflectorMethod(ForgeConfigSpec_ConfigValue, "get");
      ForgeIChunk = new ReflectorClass(ChunkAccess.class);
      ForgeIChunk_getWorldForge = new ReflectorMethod(ForgeIChunk, "getWorldForge");
      IForgeItem = new ReflectorClass("net.minecraftforge.common.extensions.IForgeItem");
      IForgeItem_getEquipmentSlot = new ReflectorMethod(IForgeItem, "getEquipmentSlot");
      IForgeItem_isDamageable1 = new ReflectorMethod(IForgeItem, "isDamageable", new Class[]{ItemStack.class});
      IForgeItem_onEntitySwing = new ReflectorMethod(IForgeItem, "onEntitySwing");
      IForgeItem_shouldCauseReequipAnimation = new ReflectorMethod(IForgeItem, "shouldCauseReequipAnimation");
      IForgeItemStack = new ReflectorClass("net.minecraftforge.common.extensions.IForgeItemStack");
      IForgeItemStack_canDisableShield = new ReflectorMethod(IForgeItemStack, "canDisableShield");
      IForgeItemStack_getEquipmentSlot = new ReflectorMethod(IForgeItemStack, "getEquipmentSlot");
      IForgeItemStack_getShareTag = new ReflectorMethod(IForgeItemStack, "getShareTag");
      IForgeItemStack_getHighlightTip = new ReflectorMethod(IForgeItemStack, "getHighlightTip");
      IForgeItemStack_readShareTag = new ReflectorMethod(IForgeItemStack, "readShareTag");
      ForgeItemTags = new ReflectorClass(ItemTags.class);
      ForgeItemTags_create = ForgeItemTags.makeMethod("create", new Class[]{ResourceLocation.class});
      ForgeI18n = new ReflectorClass("net.minecraftforge.common.ForgeI18n");
      ForgeI18n_loadLanguageData = new ReflectorMethod(ForgeI18n, "loadLanguageData");
      ForgeKeyBinding = new ReflectorClass(KeyMapping.class);
      ForgeKeyBinding_setKeyConflictContext = new ReflectorMethod(ForgeKeyBinding, "setKeyConflictContext");
      ForgeKeyBinding_setKeyModifierAndCode = new ReflectorMethod(ForgeKeyBinding, "setKeyModifierAndCode");
      ForgeKeyBinding_getKeyModifier = new ReflectorMethod(ForgeKeyBinding, "getKeyModifier");
      ForgeRarity = new ReflectorClass(Rarity.class);
      ForgeRarity_getStyleModifier = ForgeRarity.makeMethod("getStyleModifier");
      ForgeTicket = new ReflectorClass(Ticket.class);
      ForgeTicket_forceTicks = ForgeTicket.makeField("forceTicks");
      ForgeTicket_isForceTicks = ForgeTicket.makeMethod("isForceTicks");
      IForgeBlockEntity = new ReflectorClass("net.minecraftforge.common.extensions.IForgeBlockEntity");
      IForgeBlockEntity_getRenderBoundingBox = new ReflectorMethod(IForgeBlockEntity, "getRenderBoundingBox");
      IForgeBlockEntity_hasCustomOutlineRendering = new ReflectorMethod(IForgeBlockEntity, "hasCustomOutlineRendering");
      IForgeDimensionSpecialEffects = new ReflectorClass("net.minecraftforge.client.extensions.IForgeDimensionSpecialEffects");
      IForgeDimensionSpecialEffects_adjustLightmapColors = IForgeDimensionSpecialEffects.makeMethod("adjustLightmapColors");
      IForgeDimensionSpecialEffects_renderClouds = IForgeDimensionSpecialEffects.makeMethod("renderClouds");
      IForgeDimensionSpecialEffects_renderSky = IForgeDimensionSpecialEffects.makeMethod("renderSky");
      IForgeDimensionSpecialEffects_tickRain = IForgeDimensionSpecialEffects.makeMethod("tickRain");
      IForgeDimensionSpecialEffects_renderSnowAndRain = IForgeDimensionSpecialEffects.makeMethod("renderSnowAndRain");
      ForgeVersion = new ReflectorClass("net.minecraftforge.versions.forge.ForgeVersion");
      ForgeVersion_getVersion = ForgeVersion.makeMethod("getVersion");
      ForgeVersion_getSpec = ForgeVersion.makeMethod("getSpec");
      ImmediateWindowHandler = new ReflectorClass("net.minecraftforge.fml.loading.ImmediateWindowHandler");
      ImmediateWindowHandler_positionWindow = ImmediateWindowHandler.makeMethod("positionWindow");
      ImmediateWindowHandler_setupMinecraftWindow = ImmediateWindowHandler.makeMethod("setupMinecraftWindow");
      ImmediateWindowHandler_updateFBSize = ImmediateWindowHandler.makeMethod("updateFBSize");
      ItemDecoratorHandler = new ReflectorClass("net.minecraftforge.client.ItemDecoratorHandler");
      ItemDecoratorHandler_of = ItemDecoratorHandler.makeMethod("of", new Class[]{ItemStack.class});
      ItemDecoratorHandler_render = ItemDecoratorHandler.makeMethod("render");
      ForgeItemModelShaper = new ReflectorClass("net.minecraftforge.client.model.ForgeItemModelShaper");
      ForgeItemModelShaper_Constructor = new ReflectorConstructor(ForgeItemModelShaper, new Class[]{ModelManager.class});
      GeometryLoaderManager = new ReflectorClass("net.minecraftforge.client.model.geometry.GeometryLoaderManager");
      GeometryLoaderManager_init = GeometryLoaderManager.makeMethod("init");
      KeyConflictContext = new ReflectorClass("net.minecraftforge.client.settings.KeyConflictContext");
      KeyConflictContext_IN_GAME = new ReflectorField(KeyConflictContext, "IN_GAME");
      KeyModifier = new ReflectorClass("net.minecraftforge.client.settings.KeyModifier");
      KeyModifier_valueFromString = new ReflectorMethod(KeyModifier, "valueFromString");
      KeyModifier_NONE = new ReflectorField(KeyModifier, "NONE");
      Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
      Launch_blackboard = new ReflectorField(Launch, "blackboard");
      MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
      MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
      ModContainer = new ReflectorClass("net.minecraftforge.fml.ModContainer");
      ModContainer_getModId = new ReflectorMethod(ModContainer, "getModId");
      ModList = new ReflectorClass("net.minecraftforge.fml.ModList");
      ModList_mods = ModList.makeField("mods");
      ModList_get = ModList.makeMethod("get");
      ModListScreen = new ReflectorClass("net.minecraftforge.client.gui.ModListScreen");
      ModListScreen_Constructor = new ReflectorConstructor(ModListScreen, new Class[]{Screen.class});
      ModLoader = new ReflectorClass("net.minecraftforge.fml.ModLoader");
      ModLoader_get = ModLoader.makeMethod("get");
      ModLoader_postEvent = ModLoader.makeMethod("postEvent");
      TitleScreenModUpdateIndicator = new ReflectorClass("net.minecraftforge.client.gui.TitleScreenModUpdateIndicator");
      TitleScreenModUpdateIndicator_init = TitleScreenModUpdateIndicator.makeMethod("init", new Class[]{TitleScreen.class, Button.class});
      PartEntity = new ReflectorClass("net.minecraftforge.entity.PartEntity");
      PlayLevelSoundEvent = new ReflectorClass("net.minecraftforge.event.PlayLevelSoundEvent");
      PlayLevelSoundEvent_getSound = new ReflectorMethod(PlayLevelSoundEvent, "getSound");
      PlayLevelSoundEvent_getSource = new ReflectorMethod(PlayLevelSoundEvent, "getSource");
      PlayLevelSoundEvent_getNewVolume = new ReflectorMethod(PlayLevelSoundEvent, "getNewVolume");
      PlayLevelSoundEvent_getNewPitch = new ReflectorMethod(PlayLevelSoundEvent, "getNewPitch");
      QuadBakingVertexConsumer = new ReflectorClass("net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer");
      QuadBakingVertexConsumer_QUAD_DATA_SIZE = QuadBakingVertexConsumer.makeField("QUAD_DATA_SIZE");
      QuadTransformers = new ReflectorClass("net.minecraftforge.client.model.QuadTransformers");
      QuadTransformers_applyingLightmap = QuadTransformers.makeMethod("applyingLightmap", new Class[]{Integer.TYPE, Integer.TYPE});
      QuadTransformers_applyingColor = QuadTransformers.makeMethod("applyingColor", new Class[]{Integer.TYPE});
      IQuadTransformer = new ReflectorClass("net.minecraftforge.client.model.IQuadTransformer");
      IQuadTransformer_STRIDE = IQuadTransformer.makeField("STRIDE");
      IQuadTransformer_processInPlace = IQuadTransformer.makeMethod("processInPlace", new Class[]{BakedQuad.class});
      RegisterShadersEvent = new ReflectorClass("net.minecraftforge.client.event.RegisterShadersEvent");
      RegisterShadersEvent_Constructor = RegisterShadersEvent.makeConstructor(new Class[]{ResourceProvider.class, List.class});
      RenderBlockScreenEffectEvent_OverlayType = new ReflectorClass("net.minecraftforge.client.event.RenderBlockScreenEffectEvent$OverlayType");
      RenderBlockScreenEffectEvent_OverlayType_BLOCK = new ReflectorField(RenderBlockScreenEffectEvent_OverlayType, "BLOCK");
      CustomizeGuiOverlayEvent_BossEventProgress = new ReflectorClass("net.minecraftforge.client.event.CustomizeGuiOverlayEvent$BossEventProgress");
      CustomizeGuiOverlayEvent_BossEventProgress_getIncrement = CustomizeGuiOverlayEvent_BossEventProgress.makeMethod("getIncrement");
      RenderItemInFrameEvent = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
      RenderItemInFrameEvent_Constructor = new ReflectorConstructor(RenderItemInFrameEvent, new Class[]{ItemFrame.class, ItemFrameRenderer.class, PoseStack.class, MultiBufferSource.class, Integer.TYPE});
      RenderLevelStageEvent_Stage = new ReflectorClass(RenderLevelStageEvent.Stage.class);
      RenderLevelStageEvent_Stage_AFTER_SKY = RenderLevelStageEvent_Stage.makeField("AFTER_SKY");
      RenderLevelStageEvent_Stage_AFTER_SOLID_BLOCKS = RenderLevelStageEvent_Stage.makeField("AFTER_SOLID_BLOCKS");
      RenderLevelStageEvent_Stage_AFTER_CUTOUT_MIPPED_BLOCKS_BLOCKS = RenderLevelStageEvent_Stage.makeField("AFTER_CUTOUT_MIPPED_BLOCKS_BLOCKS");
      RenderLevelStageEvent_Stage_AFTER_CUTOUT_BLOCKS = RenderLevelStageEvent_Stage.makeField("AFTER_CUTOUT_BLOCKS");
      RenderLevelStageEvent_Stage_AFTER_ENTITIES = RenderLevelStageEvent_Stage.makeField("AFTER_ENTITIES");
      RenderLevelStageEvent_Stage_AFTER_BLOCK_ENTITIES = RenderLevelStageEvent_Stage.makeField("AFTER_BLOCK_ENTITIES");
      RenderLevelStageEvent_Stage_AFTER_TRANSLUCENT_BLOCKS = RenderLevelStageEvent_Stage.makeField("AFTER_TRANSLUCENT_BLOCKS");
      RenderLevelStageEvent_Stage_AFTER_TRIPWIRE_BLOCKS = RenderLevelStageEvent_Stage.makeField("AFTER_TRIPWIRE_BLOCKS");
      RenderLevelStageEvent_Stage_AFTER_PARTICLES = RenderLevelStageEvent_Stage.makeField("AFTER_PARTICLES");
      RenderLevelStageEvent_Stage_AFTER_WEATHER = RenderLevelStageEvent_Stage.makeField("AFTER_WEATHER");
      RenderLevelStageEvent_Stage_AFTER_LEVEL = RenderLevelStageEvent_Stage.makeField("AFTER_LEVEL");
      RenderLevelStageEvent_dispatch = RenderLevelStageEvent_Stage.makeMethod("dispatch");
      RenderLivingEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
      RenderLivingEvent_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Pre, new Class[]{LivingEntity.class, LivingEntityRenderer.class, Float.TYPE, PoseStack.class, MultiBufferSource.class, Integer.TYPE});
      RenderLivingEvent_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
      RenderLivingEvent_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Post, new Class[]{LivingEntity.class, LivingEntityRenderer.class, Float.TYPE, PoseStack.class, MultiBufferSource.class, Integer.TYPE});
      RenderNameTagEvent = new ReflectorClass("net.minecraftforge.client.event.RenderNameTagEvent");
      RenderNameTagEvent_Constructor = new ReflectorConstructor(RenderNameTagEvent, new Class[]{Entity.class, Component.class, EntityRenderer.class, PoseStack.class, MultiBufferSource.class, Integer.TYPE, Float.TYPE});
      RenderNameTagEvent_getContent = new ReflectorMethod(RenderNameTagEvent, "getContent");
      RenderTooltipEvent = new ReflectorClass("net.minecraftforge.client.event.RenderTooltipEvent");
      RenderTooltipEvent_getFont = RenderTooltipEvent.makeMethod("getFont");
      RenderTooltipEvent_getX = RenderTooltipEvent.makeMethod("getX");
      RenderTooltipEvent_getY = RenderTooltipEvent.makeMethod("getY");
      RenderTooltipEvent_Color = new ReflectorClass("net.minecraftforge.client.event.RenderTooltipEvent$Color");
      RenderTooltipEvent_Color_getBackgroundStart = RenderTooltipEvent_Color.makeMethod("getBackgroundStart");
      RenderTooltipEvent_Color_getBackgroundEnd = RenderTooltipEvent_Color.makeMethod("getBackgroundEnd");
      RenderTooltipEvent_Color_getBorderStart = RenderTooltipEvent_Color.makeMethod("getBorderStart");
      RenderTooltipEvent_Color_getBorderEnd = RenderTooltipEvent_Color.makeMethod("getBorderEnd");
      ScreenshotEvent = new ReflectorClass("net.minecraftforge.client.event.ScreenshotEvent");
      ScreenshotEvent_getCancelMessage = new ReflectorMethod(ScreenshotEvent, "getCancelMessage");
      ScreenshotEvent_getScreenshotFile = new ReflectorMethod(ScreenshotEvent, "getScreenshotFile");
      ScreenshotEvent_getResultMessage = new ReflectorMethod(ScreenshotEvent, "getResultMessage");
      ServerLifecycleHooks = new ReflectorClass("net.minecraftforge.server.ServerLifecycleHooks");
      ServerLifecycleHooks_handleServerAboutToStart = new ReflectorMethod(ServerLifecycleHooks, "handleServerAboutToStart");
      ServerLifecycleHooks_handleServerStarting = new ReflectorMethod(ServerLifecycleHooks, "handleServerStarting");
      TerrainParticle = new ReflectorClass(TerrainParticle.class);
      TerrainParticle_updateSprite = TerrainParticle.makeMethod("updateSprite");
      TooltipRenderUtil = new ReflectorClass(TooltipRenderUtil.class);
      TooltipRenderUtil_renderTooltipBackground10 = TooltipRenderUtil.makeMethod("renderTooltipBackground");
      LevelEvent_Load = new ReflectorClass("net.minecraftforge.event.level.LevelEvent$Load");
      LevelEvent_Load_Constructor = new ReflectorConstructor(LevelEvent_Load, new Class[]{LevelAccessor.class});
      logVanilla = registerResolvable("*** Reflector Vanilla ***");
      AbstractArrow = new ReflectorClass(AbstractArrow.class);
      AbstractArrow_inGround = new ReflectorField(new FieldLocatorTypes(AbstractArrow.class, new Class[]{BlockState.class}, Boolean.TYPE, new Class[]{Integer.TYPE}, "AbstractArrow.inGround"));
      BannerBlockEntity = new ReflectorClass(BannerBlockEntity.class);
      BannerBlockEntity_customName = BannerBlockEntity.makeField(Component.class);
      BaseContainerBlockEntity = new ReflectorClass(BaseContainerBlockEntity.class);
      BaseContainerBlockEntity_customName = BaseContainerBlockEntity.makeField(Component.class);
      Enchantments = new ReflectorClass(Enchantments.class);
      Enchantments_ResourceKeys = new ReflectorFields(Enchantments, ResourceKey.class, -1);
      EntityItem = new ReflectorClass(ItemEntity.class);
      EntityItem_ITEM = new ReflectorField(EntityItem, EntityDataAccessor.class);
      EnderDragonRenderer = new ReflectorClass(EnderDragonRenderer.class);
      EnderDragonRenderer_model = new ReflectorField(EnderDragonRenderer, EnderDragonRenderer.DragonModel.class);
      GuiEnchantment = new ReflectorClass(EnchantmentScreen.class);
      GuiEnchantment_bookModel = new ReflectorField(GuiEnchantment, BookModel.class);
      ItemOverride = new ReflectorClass(ItemOverride.class);
      ItemOverride_listResourceValues = new ReflectorField(ItemOverride, List.class);
      ItemStack = new ReflectorClass(ItemStack.class);
      ItemStack_components = ItemStack.makeField(PatchedDataComponentMap.class);
      LayerLlamaDecor = new ReflectorClass(LlamaDecorLayer.class);
      LayerLlamaDecor_model = new ReflectorField(LayerLlamaDecor, LlamaModel.class);
      Minecraft = new ReflectorClass(Minecraft.class);
      Minecraft_debugFPS = new ReflectorField(new FieldLocatorTypes(Minecraft.class, new Class[]{Supplier.class}, Integer.TYPE, new Class[]{String.class}, "debugFPS"));
      Minecraft_fontResourceManager = new ReflectorField(Minecraft, FontManager.class);
      ModelArmorStand = new ReflectorClass(ArmorStandModel.class);
      ModelArmorStand_ModelRenderers = new ReflectorFields(ModelArmorStand, ModelPart.class, 4);
      ModelBee = new ReflectorClass(BeeModel.class);
      ModelBee_ModelRenderers = new ReflectorFields(ModelBee, ModelPart.class, 2);
      ModelBlaze = new ReflectorClass(BlazeModel.class);
      ModelBlaze_blazeHead = new ReflectorField(ModelBlaze, ModelPart.class);
      ModelBlaze_blazeSticks = new ReflectorField(ModelBlaze, ModelPart[].class);
      ModelBoar = new ReflectorClass(HoglinModel.class);
      ModelBoar_ModelRenderers = new ReflectorFields(ModelBoar, ModelPart.class, 9);
      ModelBook = new ReflectorClass(BookModel.class);
      ModelBook_root = new ReflectorField(ModelBook, ModelPart.class);
      ModelChicken = new ReflectorClass(ChickenModel.class);
      ModelChicken_ModelRenderers = new ReflectorFields(ModelChicken, ModelPart.class, 8);
      ModelDragon = new ReflectorClass(EnderDragonRenderer.DragonModel.class);
      ModelDragon_ModelRenderers = new ReflectorFields(ModelDragon, ModelPart.class, 20);
      RenderEnderCrystal = new ReflectorClass(EndCrystalRenderer.class);
      RenderEnderCrystal_modelRenderers = new ReflectorFields(RenderEnderCrystal, ModelPart.class, 3);
      ModelEvokerFangs = new ReflectorClass(EvokerFangsModel.class);
      ModelEvokerFangs_ModelRenderers = new ReflectorFields(ModelEvokerFangs, ModelPart.class, 3);
      ModelGuardian = new ReflectorClass(GuardianModel.class);
      ModelGuardian_spines = new ReflectorField(ModelGuardian, ModelPart[].class, 0);
      ModelGuardian_tail = new ReflectorField(ModelGuardian, ModelPart[].class, 1);
      ModelDragonHead = new ReflectorClass(DragonHeadModel.class);
      ModelDragonHead_head = new ReflectorField(ModelDragonHead, ModelPart.class, 0);
      ModelDragonHead_jaw = new ReflectorField(ModelDragonHead, ModelPart.class, 1);
      ModelHorse = new ReflectorClass(HorseModel.class);
      ModelHorse_ModelRenderers = new ReflectorFields(ModelHorse, ModelPart.class, 11);
      ModelHorseChests = new ReflectorClass(ChestedHorseModel.class);
      ModelHorseChests_ModelRenderers = new ReflectorFields(ModelHorseChests, ModelPart.class, 2);
      ModelIllager = new ReflectorClass(IllagerModel.class);
      ModelIllager_ModelRenderers = new ReflectorFields(ModelIllager, ModelPart.class, 8);
      ModelIronGolem = new ReflectorClass(IronGolemModel.class);
      ModelIronGolem_ModelRenderers = new ReflectorFields(ModelIronGolem, ModelPart.class, 6);
      ModelAxolotl = new ReflectorClass(AxolotlModel.class);
      ModelAxolotl_ModelRenderers = new ReflectorFields(ModelAxolotl, ModelPart.class, 10);
      ModelFox = new ReflectorClass(FoxModel.class);
      ModelFox_ModelRenderers = new ReflectorFields(ModelFox, ModelPart.class, 7);
      ModelLeashKnot = new ReflectorClass(LeashKnotModel.class);
      ModelLeashKnot_knotRenderer = new ReflectorField(ModelLeashKnot, ModelPart.class);
      RenderLeashKnot = new ReflectorClass(LeashKnotRenderer.class);
      RenderLeashKnot_leashKnotModel = new ReflectorField(RenderLeashKnot, LeashKnotModel.class);
      ModelLlama = new ReflectorClass(LlamaModel.class);
      ModelLlama_ModelRenderers = new ReflectorFields(ModelLlama, ModelPart.class, 8);
      ModelOcelot = new ReflectorClass(OcelotModel.class);
      ModelOcelot_ModelRenderers = new ReflectorFields(ModelOcelot, ModelPart.class, 8);
      ModelPhantom = new ReflectorClass(PhantomModel.class);
      ModelPhantom_ModelRenderers = new ReflectorFields(ModelPhantom, ModelPart.class, 7);
      ModelPiglin = new ReflectorClass(PiglinModel.class);
      ModelPiglin_ModelRenderers = new ReflectorFields(ModelPiglin, ModelPart.class, 2);
      ModelPiglinHead = new ReflectorClass(PiglinHeadModel.class);
      ModelPiglinHead_ModelRenderers = new ReflectorFields(ModelPiglinHead, ModelPart.class, 3);
      ModelQuadruped = new ReflectorClass(QuadrupedModel.class);
      ModelQuadruped_ModelRenderers = new ReflectorFields(ModelQuadruped, ModelPart.class, 6);
      ModelRabbit = new ReflectorClass(RabbitModel.class);
      ModelRabbit_ModelRenderers = new ReflectorFields(ModelRabbit, ModelPart.class, 12);
      ModelShulker = new ReflectorClass(ShulkerModel.class);
      ModelShulker_ModelRenderers = new ReflectorFields(ModelShulker, ModelPart.class, 3);
      ModelShield = new ReflectorClass(ShieldModel.class);
      ModelShield_ModelRenderers = new ReflectorFields(ModelShield, ModelPart.class, 3);
      ModelSkull = new ReflectorClass(SkullModel.class);
      ModelSkull_renderers = new ReflectorFields(ModelSkull, ModelPart.class, 2);
      ModelTadpole = new ReflectorClass(TadpoleModel.class);
      ModelTadpole_ModelRenderers = new ReflectorFields(ModelTadpole, ModelPart.class, 2);
      ModelTrident = new ReflectorClass(TridentModel.class);
      ModelTrident_root = new ReflectorField(ModelTrident, ModelPart.class);
      ModelTurtle = new ReflectorClass(TurtleModel.class);
      ModelTurtle_body2 = new ReflectorField(ModelTurtle, ModelPart.class, 0);
      ModelVex = new ReflectorClass(VexModel.class);
      ModelVex_leftWing = new ReflectorField(ModelVex, ModelPart.class, 0);
      ModelVex_rightWing = new ReflectorField(ModelVex, ModelPart.class, 1);
      ModelWolf = new ReflectorClass(WolfModel.class);
      ModelWolf_ModelRenderers = new ReflectorFields(ModelWolf, ModelPart.class, 10);
      OptiFineResourceLocator = ReflectorForge.getReflectorClassOptiFineResourceLocator();
      OptiFineResourceLocator_getOptiFineResourceStream = new ReflectorMethod(OptiFineResourceLocator, "getOptiFineResourceStream");
      Potion = new ReflectorClass(Potion.class);
      Potion_baseName = Potion.makeField(String.class);
      RenderBoat = new ReflectorClass(BoatRenderer.class);
      RenderBoat_boatResources = new ReflectorField(RenderBoat, Map.class);
      RenderEvokerFangs = new ReflectorClass(EvokerFangsRenderer.class);
      RenderEvokerFangs_model = new ReflectorField(RenderEvokerFangs, EvokerFangsModel.class);
      RenderLlamaSpit = new ReflectorClass(LlamaSpitRenderer.class);
      RenderLlamaSpit_model = new ReflectorField(RenderLlamaSpit, LlamaSpitModel.class);
      RenderPufferfish = new ReflectorClass(PufferfishRenderer.class);
      RenderPufferfish_modelSmall = new ReflectorField(RenderPufferfish, EntityModel.class, 0);
      RenderPufferfish_modelMedium = new ReflectorField(RenderPufferfish, EntityModel.class, 1);
      RenderPufferfish_modelBig = new ReflectorField(RenderPufferfish, EntityModel.class, 2);
      RenderMinecart = new ReflectorClass(MinecartRenderer.class);
      RenderMinecart_modelMinecart = new ReflectorField(RenderMinecart, EntityModel.class);
      RenderShulkerBullet = new ReflectorClass(ShulkerBulletRenderer.class);
      RenderShulkerBullet_model = new ReflectorField(RenderShulkerBullet, ShulkerBulletModel.class);
      RenderTrident = new ReflectorClass(ThrownTridentRenderer.class);
      RenderTrident_modelTrident = new ReflectorField(RenderTrident, TridentModel.class);
      RenderTropicalFish = new ReflectorClass(TropicalFishRenderer.class);
      RenderTropicalFish_modelA = new ReflectorField(RenderTropicalFish, ColorableHierarchicalModel.class, 0);
      RenderTropicalFish_modelB = new ReflectorField(RenderTropicalFish, ColorableHierarchicalModel.class, 1);
      RenderWindCharge = new ReflectorClass(WindChargeRenderer.class);
      RenderWindCharge_model = new ReflectorField(RenderWindCharge, WindChargeModel.class);
      TropicalFishPatternLayer = new ReflectorClass(TropicalFishPatternLayer.class);
      TropicalFishPatternLayer_modelA = new ReflectorField(TropicalFishPatternLayer, TropicalFishModelA.class);
      TropicalFishPatternLayer_modelB = new ReflectorField(TropicalFishPatternLayer, TropicalFishModelB.class);
      RenderWitherSkull = new ReflectorClass(WitherSkullRenderer.class);
      RenderWitherSkull_model = new ReflectorField(RenderWitherSkull, SkullModel.class);
      SimpleBakedModel = new ReflectorClass(SimpleBakedModel.class);
      SimpleBakedModel_generalQuads = SimpleBakedModel.makeField(List.class);
      SimpleBakedModel_faceQuads = SimpleBakedModel.makeField(Map.class);
      TileEntityBannerRenderer = new ReflectorClass(BannerRenderer.class);
      TileEntityBannerRenderer_modelRenderers = new ReflectorFields(TileEntityBannerRenderer, ModelPart.class, 3);
      TileEntityBedRenderer = new ReflectorClass(BedRenderer.class);
      TileEntityBedRenderer_headModel = new ReflectorField(TileEntityBedRenderer, ModelPart.class, 0);
      TileEntityBedRenderer_footModel = new ReflectorField(TileEntityBedRenderer, ModelPart.class, 1);
      TileEntityBellRenderer = new ReflectorClass(BellRenderer.class);
      TileEntityBellRenderer_modelRenderer = new ReflectorField(TileEntityBellRenderer, ModelPart.class);
      TileEntityBeacon = new ReflectorClass(BeaconBlockEntity.class);
      TileEntityBeacon_customName = new ReflectorField(TileEntityBeacon, Component.class);
      TileEntityBeacon_levels = new ReflectorField(new FieldLocatorTypes(BeaconBlockEntity.class, new Class[]{List.class}, Integer.TYPE, new Class[]{Integer.TYPE}, "BeaconBlockEntity.levels"));
      TileEntityChestRenderer = new ReflectorClass(ChestRenderer.class);
      TileEntityChestRenderer_modelRenderers = new ReflectorFields(TileEntityChestRenderer, ModelPart.class, 9);
      TileEntityConduitRenderer = new ReflectorClass(ConduitRenderer.class);
      TileEntityConduitRenderer_modelRenderers = new ReflectorFields(TileEntityConduitRenderer, ModelPart.class, 4);
      TileEntityDecoratedPotRenderer = new ReflectorClass(DecoratedPotRenderer.class);
      TileEntityDecoratedPotRenderer_modelRenderers = new ReflectorFields(TileEntityDecoratedPotRenderer, ModelPart.class, 7);
      TileEntityEnchantmentTableRenderer = new ReflectorClass(EnchantTableRenderer.class);
      TileEntityEnchantmentTableRenderer_modelBook = new ReflectorField(TileEntityEnchantmentTableRenderer, BookModel.class);
      TileEntityHangingSignRenderer = new ReflectorClass(HangingSignRenderer.class);
      TileEntityHangingSignRenderer_hangingSignModels = new ReflectorField(TileEntityHangingSignRenderer, Map.class);
      TileEntityLecternRenderer = new ReflectorClass(LecternRenderer.class);
      TileEntityLecternRenderer_modelBook = new ReflectorField(TileEntityLecternRenderer, BookModel.class);
      TileEntityShulkerBoxRenderer = new ReflectorClass(ShulkerBoxRenderer.class);
      TileEntityShulkerBoxRenderer_model = new ReflectorField(TileEntityShulkerBoxRenderer, ShulkerModel.class);
      TileEntitySignRenderer = new ReflectorClass(SignRenderer.class);
      TileEntitySignRenderer_signModels = new ReflectorField(TileEntitySignRenderer, Map.class);
   }
}
