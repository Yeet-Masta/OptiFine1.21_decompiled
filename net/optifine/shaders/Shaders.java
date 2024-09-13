package net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.optifine.Config;
import net.optifine.CustomBlockLayers;
import net.optifine.CustomColors;
import net.optifine.GlErrors;
import net.optifine.Lang;
import net.optifine.config.ConnectedParser;
import net.optifine.expr.IExpressionBool;
import net.optifine.render.GLConst;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.render.RenderTypes;
import net.optifine.render.RenderUtils;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.shaders.config.MacroState;
import net.optifine.shaders.config.PropertyDefaultFastFancyOff;
import net.optifine.shaders.config.PropertyDefaultTrueFalse;
import net.optifine.shaders.config.RenderScale;
import net.optifine.shaders.config.ScreenShaderOptions;
import net.optifine.shaders.config.ShaderLine;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionProfile;
import net.optifine.shaders.config.ShaderOptionRest;
import net.optifine.shaders.config.ShaderPackParser;
import net.optifine.shaders.config.ShaderParser;
import net.optifine.shaders.config.ShaderProfile;
import net.optifine.shaders.config.ShaderType;
import net.optifine.shaders.uniform.CustomUniforms;
import net.optifine.shaders.uniform.ShaderUniform1f;
import net.optifine.shaders.uniform.ShaderUniform1i;
import net.optifine.shaders.uniform.ShaderUniform2i;
import net.optifine.shaders.uniform.ShaderUniform3f;
import net.optifine.shaders.uniform.ShaderUniform4f;
import net.optifine.shaders.uniform.ShaderUniform4i;
import net.optifine.shaders.uniform.ShaderUniformM3;
import net.optifine.shaders.uniform.ShaderUniformM4;
import net.optifine.shaders.uniform.ShaderUniforms;
import net.optifine.shaders.uniform.Smoother;
import net.optifine.texture.InternalFormat;
import net.optifine.texture.PixelFormat;
import net.optifine.texture.PixelType;
import net.optifine.texture.TextureType;
import net.optifine.util.ArrayUtils;
import net.optifine.util.DynamicDimension;
import net.optifine.util.EntityUtils;
import net.optifine.util.LineBuffer;
import net.optifine.util.MathUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;
import net.optifine.util.TimedEvent;
import net.optifine.util.WorldUtils;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBGeometryShader4;
import org.lwjgl.opengl.EXTGeometryShader4;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.KHRDebug;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class Shaders {
   static Minecraft f_303183_;
   static GameRenderer entityRenderer;
   public static boolean isInitializedOnce = false;
   public static boolean isShaderPackInitialized = false;
   public static GLCapabilities capabilities;
   public static String glVersionString;
   public static String glVendorString;
   public static String glRendererString;
   public static boolean hasGlGenMipmap = false;
   public static int countResetDisplayLists = 0;
   private static int renderDisplayWidth = 0;
   private static int renderDisplayHeight = 0;
   public static int renderWidth = 0;
   public static int renderHeight = 0;
   public static boolean isRenderingWorld = false;
   public static boolean isRenderingSky = false;
   public static boolean isCompositeRendered = false;
   public static boolean isRenderingDfb = false;
   public static boolean isShadowPass = false;
   public static boolean isSleeping;
   private static boolean isRenderingFirstPersonHand;
   private static boolean isHandRenderedMain;
   private static boolean isHandRenderedOff;
   private static boolean skipRenderHandMain;
   private static boolean skipRenderHandOff;
   public static boolean renderItemKeepDepthMask = false;
   public static boolean itemToRenderMainTranslucent = false;
   public static boolean itemToRenderOffTranslucent = false;
   static float[] sunPosition = new float[4];
   static float[] moonPosition = new float[4];
   static float[] shadowLightPosition = new float[4];
   static float[] upPosition = new float[4];
   static float[] shadowLightPositionVector = new float[4];
   static float[] upPosModelView = new float[]{0.0F, 100.0F, 0.0F, 0.0F};
   static float[] sunPosModelView = new float[]{0.0F, 100.0F, 0.0F, 0.0F};
   static float[] moonPosModelView = new float[]{0.0F, -100.0F, 0.0F, 0.0F};
   private static float[] tempMat = new float[16];
   static Vector4f clearColor = new Vector4f();
   static float skyColorR;
   static float skyColorG;
   static float skyColorB;
   static long worldTime = 0L;
   static long lastWorldTime = 0L;
   static long diffWorldTime = 0L;
   static float celestialAngle = 0.0F;
   static float sunAngle = 0.0F;
   static float shadowAngle = 0.0F;
   static int moonPhase = 0;
   static long systemTime = 0L;
   static long lastSystemTime = 0L;
   static long diffSystemTime = 0L;
   static int frameCounter = 0;
   static float frameTime = 0.0F;
   static float frameTimeCounter = 0.0F;
   static int systemTimeInt32 = 0;
   public static CameraType pointOfView = CameraType.FIRST_PERSON;
   public static boolean pointOfViewChanged = false;
   static float rainStrength = 0.0F;
   static float wetness = 0.0F;
   public static float wetnessHalfLife = 600.0F;
   public static float drynessHalfLife = 200.0F;
   public static float eyeBrightnessHalflife = 10.0F;
   static boolean usewetness = false;
   static int isEyeInWater = 0;
   static int eyeBrightness = 0;
   static float eyeBrightnessFadeX = 0.0F;
   static float eyeBrightnessFadeY = 0.0F;
   static float eyePosY = 0.0F;
   static float centerDepth = 0.0F;
   static float centerDepthSmooth = 0.0F;
   static float centerDepthSmoothHalflife = 1.0F;
   static boolean centerDepthSmoothEnabled = false;
   static int superSamplingLevel = 1;
   static float nightVision = 0.0F;
   static float blindness = 0.0F;
   static boolean lightmapEnabled = false;
   static boolean fogEnabled = true;
   static boolean fogAllowed = true;
   static RenderStage renderStage = RenderStage.NONE;
   static int bossBattle = 0;
   static float darknessFactor = 0.0F;
   static float darknessLightFactor = 0.0F;
   private static int baseAttribId = 11;
   public static int entityAttrib = baseAttribId + 0;
   public static int midTexCoordAttrib = baseAttribId + 1;
   public static int tangentAttrib = baseAttribId + 2;
   public static int velocityAttrib = baseAttribId + 3;
   public static int midBlockAttrib = baseAttribId + 4;
   public static boolean useEntityAttrib = false;
   public static boolean useMidTexCoordAttrib = false;
   public static boolean useTangentAttrib = false;
   public static boolean useVelocityAttrib = false;
   public static boolean useMidBlockAttrib = false;
   public static boolean progUseEntityAttrib = false;
   public static boolean progUseMidTexCoordAttrib = false;
   public static boolean progUseTangentAttrib = false;
   public static boolean progUseVelocityAttrib = false;
   public static boolean progUseMidBlockAttrib = false;
   private static boolean progArbGeometryShader4 = false;
   private static boolean progExtGeometryShader4 = false;
   private static int progMaxVerticesOut = 3;
   private static boolean hasGeometryShaders = false;
   public static boolean hasShadowGeometryShaders = false;
   public static boolean hasShadowInstancing = false;
   public static int atlasSizeX = 0;
   public static int atlasSizeY = 0;
   private static ShaderUniforms shaderUniforms = new ShaderUniforms();
   public static ShaderUniform4f uniform_entityColor = shaderUniforms.make4f("entityColor");
   public static ShaderUniform1i uniform_entityId = shaderUniforms.make1i("entityId");
   public static ShaderUniform1i uniform_blockEntityId = shaderUniforms.make1i("blockEntityId");
   public static ShaderUniform1i uniform_gtexture = shaderUniforms.make1i("gtexture");
   public static ShaderUniform1i uniform_lightmap = shaderUniforms.make1i("lightmap");
   public static ShaderUniform1i uniform_normals = shaderUniforms.make1i("normals");
   public static ShaderUniform1i uniform_specular = shaderUniforms.make1i("specular");
   public static ShaderUniform1i uniform_shadow = shaderUniforms.make1i("shadow");
   public static ShaderUniform1i uniform_watershadow = shaderUniforms.make1i("watershadow");
   public static ShaderUniform1i uniform_shadowtex0 = shaderUniforms.make1i("shadowtex0");
   public static ShaderUniform1i uniform_shadowtex1 = shaderUniforms.make1i("shadowtex1");
   public static ShaderUniform1i uniform_depthtex0 = shaderUniforms.make1i("depthtex0");
   public static ShaderUniform1i uniform_depthtex1 = shaderUniforms.make1i("depthtex1");
   public static ShaderUniform1i uniform_shadowcolor = shaderUniforms.make1i("shadowcolor");
   public static ShaderUniform1i uniform_shadowcolor0 = shaderUniforms.make1i("shadowcolor0");
   public static ShaderUniform1i uniform_shadowcolor1 = shaderUniforms.make1i("shadowcolor1");
   public static ShaderUniform1i uniform_noisetex = shaderUniforms.make1i("noisetex");
   public static ShaderUniform1i uniform_gcolor = shaderUniforms.make1i("gcolor");
   public static ShaderUniform1i uniform_gdepth = shaderUniforms.make1i("gdepth");
   public static ShaderUniform1i uniform_gnormal = shaderUniforms.make1i("gnormal");
   public static ShaderUniform1i uniform_composite = shaderUniforms.make1i("composite");
   public static ShaderUniform1i uniform_gaux1 = shaderUniforms.make1i("gaux1");
   public static ShaderUniform1i uniform_gaux2 = shaderUniforms.make1i("gaux2");
   public static ShaderUniform1i uniform_gaux3 = shaderUniforms.make1i("gaux3");
   public static ShaderUniform1i uniform_gaux4 = shaderUniforms.make1i("gaux4");
   public static ShaderUniform1i uniform_colortex0 = shaderUniforms.make1i("colortex0");
   public static ShaderUniform1i uniform_colortex1 = shaderUniforms.make1i("colortex1");
   public static ShaderUniform1i uniform_colortex2 = shaderUniforms.make1i("colortex2");
   public static ShaderUniform1i uniform_colortex3 = shaderUniforms.make1i("colortex3");
   public static ShaderUniform1i uniform_colortex4 = shaderUniforms.make1i("colortex4");
   public static ShaderUniform1i uniform_colortex5 = shaderUniforms.make1i("colortex5");
   public static ShaderUniform1i uniform_colortex6 = shaderUniforms.make1i("colortex6");
   public static ShaderUniform1i uniform_colortex7 = shaderUniforms.make1i("colortex7");
   public static ShaderUniform1i uniform_gdepthtex = shaderUniforms.make1i("gdepthtex");
   public static ShaderUniform1i uniform_depthtex2 = shaderUniforms.make1i("depthtex2");
   public static ShaderUniform1i uniform_colortex8 = shaderUniforms.make1i("colortex8");
   public static ShaderUniform1i uniform_colortex9 = shaderUniforms.make1i("colortex9");
   public static ShaderUniform1i uniform_colortex10 = shaderUniforms.make1i("colortex10");
   public static ShaderUniform1i uniform_colortex11 = shaderUniforms.make1i("colortex11");
   public static ShaderUniform1i uniform_colortex12 = shaderUniforms.make1i("colortex12");
   public static ShaderUniform1i uniform_colortex13 = shaderUniforms.make1i("colortex13");
   public static ShaderUniform1i uniform_colortex14 = shaderUniforms.make1i("colortex14");
   public static ShaderUniform1i uniform_colortex15 = shaderUniforms.make1i("colortex15");
   public static ShaderUniform1i uniform_colorimg0 = shaderUniforms.make1i("colorimg0");
   public static ShaderUniform1i uniform_colorimg1 = shaderUniforms.make1i("colorimg1");
   public static ShaderUniform1i uniform_colorimg2 = shaderUniforms.make1i("colorimg2");
   public static ShaderUniform1i uniform_colorimg3 = shaderUniforms.make1i("colorimg3");
   public static ShaderUniform1i uniform_colorimg4 = shaderUniforms.make1i("colorimg4");
   public static ShaderUniform1i uniform_colorimg5 = shaderUniforms.make1i("colorimg5");
   public static ShaderUniform1i uniform_shadowcolorimg0 = shaderUniforms.make1i("shadowcolorimg0");
   public static ShaderUniform1i uniform_shadowcolorimg1 = shaderUniforms.make1i("shadowcolorimg1");
   public static ShaderUniform1i uniform_tex = shaderUniforms.make1i("tex");
   public static ShaderUniform1i uniform_heldItemId = shaderUniforms.make1i("heldItemId");
   public static ShaderUniform1i uniform_heldBlockLightValue = shaderUniforms.make1i("heldBlockLightValue");
   public static ShaderUniform1i uniform_heldItemId2 = shaderUniforms.make1i("heldItemId2");
   public static ShaderUniform1i uniform_heldBlockLightValue2 = shaderUniforms.make1i("heldBlockLightValue2");
   public static ShaderUniform1i uniform_fogMode = shaderUniforms.make1i("fogMode");
   public static ShaderUniform1f uniform_fogDensity = shaderUniforms.make1f("fogDensity");
   public static ShaderUniform1f uniform_fogStart = shaderUniforms.make1f("fogStart");
   public static ShaderUniform1f uniform_fogEnd = shaderUniforms.make1f("fogEnd");
   public static ShaderUniform1i uniform_fogShape = shaderUniforms.make1i("fogShape");
   public static ShaderUniform3f uniform_fogColor = shaderUniforms.make3f("fogColor");
   public static ShaderUniform3f uniform_skyColor = shaderUniforms.make3f("skyColor");
   public static ShaderUniform1i uniform_worldTime = shaderUniforms.make1i("worldTime");
   public static ShaderUniform1i uniform_worldDay = shaderUniforms.make1i("worldDay");
   public static ShaderUniform1i uniform_moonPhase = shaderUniforms.make1i("moonPhase");
   public static ShaderUniform1i uniform_frameCounter = shaderUniforms.make1i("frameCounter");
   public static ShaderUniform1f uniform_frameTime = shaderUniforms.make1f("frameTime");
   public static ShaderUniform1f uniform_frameTimeCounter = shaderUniforms.make1f("frameTimeCounter");
   public static ShaderUniform1f uniform_sunAngle = shaderUniforms.make1f("sunAngle");
   public static ShaderUniform1f uniform_shadowAngle = shaderUniforms.make1f("shadowAngle");
   public static ShaderUniform1f uniform_rainStrength = shaderUniforms.make1f("rainStrength");
   public static ShaderUniform1f uniform_aspectRatio = shaderUniforms.make1f("aspectRatio");
   public static ShaderUniform1f uniform_viewWidth = shaderUniforms.make1f("viewWidth");
   public static ShaderUniform1f uniform_viewHeight = shaderUniforms.make1f("viewHeight");
   public static ShaderUniform1f uniform_near = shaderUniforms.make1f("near");
   public static ShaderUniform1f uniform_far = shaderUniforms.make1f("far");
   public static ShaderUniform3f uniform_sunPosition = shaderUniforms.make3f("sunPosition");
   public static ShaderUniform3f uniform_moonPosition = shaderUniforms.make3f("moonPosition");
   public static ShaderUniform3f uniform_shadowLightPosition = shaderUniforms.make3f("shadowLightPosition");
   public static ShaderUniform3f uniform_upPosition = shaderUniforms.make3f("upPosition");
   public static ShaderUniform3f uniform_previousCameraPosition = shaderUniforms.make3f("previousCameraPosition");
   public static ShaderUniform3f uniform_cameraPosition = shaderUniforms.make3f("cameraPosition");
   public static ShaderUniformM4 uniform_gbufferModelView = shaderUniforms.makeM4("gbufferModelView");
   public static ShaderUniformM4 uniform_gbufferModelViewInverse = shaderUniforms.makeM4("gbufferModelViewInverse");
   public static ShaderUniformM4 uniform_gbufferPreviousProjection = shaderUniforms.makeM4("gbufferPreviousProjection");
   public static ShaderUniformM4 uniform_gbufferProjection = shaderUniforms.makeM4("gbufferProjection");
   public static ShaderUniformM4 uniform_gbufferProjectionInverse = shaderUniforms.makeM4("gbufferProjectionInverse");
   public static ShaderUniformM4 uniform_gbufferPreviousModelView = shaderUniforms.makeM4("gbufferPreviousModelView");
   public static ShaderUniformM4 uniform_shadowProjection = shaderUniforms.makeM4("shadowProjection");
   public static ShaderUniformM4 uniform_shadowProjectionInverse = shaderUniforms.makeM4("shadowProjectionInverse");
   public static ShaderUniformM4 uniform_shadowModelView = shaderUniforms.makeM4("shadowModelView");
   public static ShaderUniformM4 uniform_shadowModelViewInverse = shaderUniforms.makeM4("shadowModelViewInverse");
   public static ShaderUniform1f uniform_wetness = shaderUniforms.make1f("wetness");
   public static ShaderUniform1f uniform_eyeAltitude = shaderUniforms.make1f("eyeAltitude");
   public static ShaderUniform2i uniform_eyeBrightness = shaderUniforms.make2i("eyeBrightness");
   public static ShaderUniform2i uniform_eyeBrightnessSmooth = shaderUniforms.make2i("eyeBrightnessSmooth");
   public static ShaderUniform2i uniform_terrainTextureSize = shaderUniforms.make2i("terrainTextureSize");
   public static ShaderUniform1i uniform_terrainIconSize = shaderUniforms.make1i("terrainIconSize");
   public static ShaderUniform1i uniform_isEyeInWater = shaderUniforms.make1i("isEyeInWater");
   public static ShaderUniform1f uniform_nightVision = shaderUniforms.make1f("nightVision");
   public static ShaderUniform1f uniform_blindness = shaderUniforms.make1f("blindness");
   public static ShaderUniform1f uniform_screenBrightness = shaderUniforms.make1f("screenBrightness");
   public static ShaderUniform1i uniform_hideGUI = shaderUniforms.make1i("hideGUI");
   public static ShaderUniform1f uniform_centerDepthSmooth = shaderUniforms.make1f("centerDepthSmooth");
   public static ShaderUniform2i uniform_atlasSize = shaderUniforms.make2i("atlasSize");
   public static ShaderUniform4f uniform_spriteBounds = shaderUniforms.make4f("spriteBounds");
   public static ShaderUniform4i uniform_blendFunc = shaderUniforms.make4i("blendFunc");
   public static ShaderUniform1i uniform_instanceId = shaderUniforms.make1i("instanceId");
   public static ShaderUniform1f uniform_playerMood = shaderUniforms.make1f("playerMood");
   public static ShaderUniform1i uniform_renderStage = shaderUniforms.make1i("renderStage");
   public static ShaderUniform1i uniform_bossBattle = shaderUniforms.make1i("bossBattle");
   public static ShaderUniformM4 uniform_modelViewMatrix = shaderUniforms.makeM4("modelViewMatrix");
   public static ShaderUniformM4 uniform_modelViewMatrixInverse = shaderUniforms.makeM4("modelViewMatrixInverse");
   public static ShaderUniformM4 uniform_projectionMatrix = shaderUniforms.makeM4("projectionMatrix");
   public static ShaderUniformM4 uniform_projectionMatrixInverse = shaderUniforms.makeM4("projectionMatrixInverse");
   public static ShaderUniformM4 uniform_textureMatrix = shaderUniforms.makeM4("textureMatrix");
   public static ShaderUniformM3 uniform_normalMatrix = shaderUniforms.makeM3("normalMatrix");
   public static ShaderUniform3f uniform_chunkOffset = shaderUniforms.make3f("chunkOffset");
   public static ShaderUniform4f uniform_colorModulator = shaderUniforms.make4f("colorModulator");
   public static ShaderUniform1f uniform_alphaTestRef = shaderUniforms.make1f("alphaTestRef");
   public static ShaderUniform1f uniform_darknessFactor = shaderUniforms.make1f("darknessFactor");
   public static ShaderUniform1f uniform_darknessLightFactor = shaderUniforms.make1f("darknessLightFactor");
   static double previousCameraPositionX;
   static double previousCameraPositionY;
   static double previousCameraPositionZ;
   static double cameraPositionX;
   static double cameraPositionY;
   static double cameraPositionZ;
   static int cameraOffsetX;
   static int cameraOffsetZ;
   public static boolean hasShadowMap = false;
   public static boolean needResizeShadow = false;
   static int shadowMapWidth = 1024;
   static int shadowMapHeight = 1024;
   static int spShadowMapWidth = 1024;
   static int spShadowMapHeight = 1024;
   static float shadowMapFOV = 90.0F;
   static float shadowMapHalfPlane = 160.0F;
   static boolean shadowMapIsOrtho = true;
   static float shadowDistanceRenderMul = -1.0F;
   public static boolean shouldSkipDefaultShadow = false;
   static boolean waterShadowEnabled = false;
   public static int MaxDrawBuffers;
   public static int MaxColorBuffers;
   public static int MaxDepthBuffers;
   public static int MaxShadowColorBuffers;
   public static int MaxShadowDepthBuffers;
   static int usedColorBuffers = 0;
   static int usedDepthBuffers = 0;
   static int usedShadowColorBuffers = 0;
   static int usedShadowDepthBuffers = 0;
   static int usedColorAttachs = 0;
   static int usedDrawBuffers = 0;
   static boolean bindImageTextures = false;
   static ShadersFramebuffer dfb;
   static ShadersFramebuffer sfb;
   private static int[] gbuffersFormat = new int[16];
   public static boolean[] gbuffersClear = new boolean[16];
   public static Vector4f[] gbuffersClearColor = new Vector4f[16];
   private static Vector4f CLEAR_COLOR_0 = new Vector4f(0.0F, 0.0F, 0.0F, 0.0F);
   private static Vector4f CLEAR_COLOR_1 = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
   private static int[] shadowBuffersFormat = new int[2];
   public static boolean[] shadowBuffersClear = new boolean[2];
   public static Vector4f[] shadowBuffersClearColor = new Vector4f[2];
   private static Programs programs = new Programs();
   public static Program ProgramNone = programs.getProgramNone();
   public static Program ProgramShadow = programs.makeShadow("shadow", ProgramNone);
   public static Program ProgramShadowSolid = programs.makeShadow("shadow_solid", ProgramShadow);
   public static Program ProgramShadowCutout = programs.makeShadow("shadow_cutout", ProgramShadow);
   public static Program[] ProgramsShadowcomp = programs.makeShadowcomps("shadowcomp", 100);
   public static Program[] ProgramsPrepare = programs.makePrepares("prepare", 100);
   public static Program ProgramBasic = programs.makeGbuffers("gbuffers_basic", ProgramNone);
   public static Program ProgramLine = programs.makeGbuffers("gbuffers_line", ProgramBasic);
   public static Program ProgramTextured = programs.makeGbuffers("gbuffers_textured", ProgramBasic);
   public static Program ProgramTexturedLit = programs.makeGbuffers("gbuffers_textured_lit", ProgramTextured);
   public static Program ProgramSkyBasic = programs.makeGbuffers("gbuffers_skybasic", ProgramBasic);
   public static Program ProgramSkyTextured = programs.makeGbuffers("gbuffers_skytextured", ProgramTextured);
   public static Program ProgramClouds = programs.makeGbuffers("gbuffers_clouds", ProgramTextured);
   public static Program ProgramTerrain = programs.makeGbuffers("gbuffers_terrain", ProgramTexturedLit);
   public static Program ProgramTerrainSolid = programs.makeGbuffers("gbuffers_terrain_solid", ProgramTerrain);
   public static Program ProgramTerrainCutoutMip = programs.makeGbuffers("gbuffers_terrain_cutout_mip", ProgramTerrain);
   public static Program ProgramTerrainCutout = programs.makeGbuffers("gbuffers_terrain_cutout", ProgramTerrain);
   public static Program ProgramDamagedBlock = programs.makeGbuffers("gbuffers_damagedblock", ProgramTerrain);
   public static Program ProgramBlock = programs.makeGbuffers("gbuffers_block", ProgramTerrain);
   public static Program ProgramBeaconBeam = programs.makeGbuffers("gbuffers_beaconbeam", ProgramTextured);
   public static Program ProgramItem = programs.makeGbuffers("gbuffers_item", ProgramTexturedLit);
   public static Program ProgramEntities = programs.makeGbuffers("gbuffers_entities", ProgramTexturedLit);
   public static Program ProgramEntitiesGlowing = programs.makeGbuffers("gbuffers_entities_glowing", ProgramEntities);
   public static Program ProgramArmorGlint = programs.makeGbuffers("gbuffers_armor_glint", ProgramTextured);
   public static Program ProgramSpiderEyes = programs.makeGbuffers("gbuffers_spidereyes", ProgramTextured);
   public static Program ProgramHand = programs.makeGbuffers("gbuffers_hand", ProgramTexturedLit);
   public static Program ProgramWeather = programs.makeGbuffers("gbuffers_weather", ProgramTexturedLit);
   public static Program ProgramDeferredPre = programs.makeVirtual("deferred_pre");
   public static Program[] ProgramsDeferred = programs.makeDeferreds("deferred", 100);
   public static Program ProgramDeferred = ProgramsDeferred[0];
   public static Program ProgramWater = programs.makeGbuffers("gbuffers_water", ProgramTerrain);
   public static Program ProgramHandWater = programs.makeGbuffers("gbuffers_hand_water", ProgramHand);
   public static Program ProgramCompositePre = programs.makeVirtual("composite_pre");
   public static Program[] ProgramsComposite = programs.makeComposites("composite", 100);
   public static Program ProgramComposite = ProgramsComposite[0];
   public static Program ProgramFinal = programs.makeComposite("final");
   public static int ProgramCount = programs.getCount();
   public static Program[] ProgramsAll = programs.getPrograms();
   public static Program activeProgram = ProgramNone;
   public static int activeProgramID = 0;
   private static ProgramStack programStack = new ProgramStack();
   private static boolean hasDeferredPrograms = false;
   public static boolean hasShadowcompPrograms = false;
   public static boolean hasPreparePrograms = false;
   public static Properties loadedShaders = null;
   public static Properties shadersConfig = null;
   public static AbstractTexture defaultTexture = null;
   public static boolean[] shadowHardwareFilteringEnabled = new boolean[2];
   public static boolean[] shadowMipmapEnabled = new boolean[2];
   public static boolean[] shadowFilterNearest = new boolean[2];
   public static boolean[] shadowColorMipmapEnabled = new boolean[2];
   public static boolean[] shadowColorFilterNearest = new boolean[2];
   public static boolean configTweakBlockDamage = false;
   public static boolean configCloudShadow = false;
   public static float configHandDepthMul = 0.125F;
   public static float configRenderResMul = 1.0F;
   public static float configShadowResMul = 1.0F;
   public static int configTexMinFilB = 0;
   public static int configTexMinFilN = 0;
   public static int configTexMinFilS = 0;
   public static int configTexMagFilB = 0;
   public static int configTexMagFilN = 0;
   public static int configTexMagFilS = 0;
   public static boolean configShadowClipFrustrum = true;
   public static boolean configNormalMap = true;
   public static boolean configSpecularMap = true;
   public static PropertyDefaultTrueFalse configOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
   public static PropertyDefaultTrueFalse configOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
   public static int configAntialiasingLevel = 0;
   public static int texMinFilRange;
   public static int texMagFilRange;
   public static String[] texMinFilDesc = new String[]{"Nearest", "Nearest-Nearest", "Nearest-Linear"};
   public static String[] texMagFilDesc = new String[]{"Nearest", "Linear"};
   public static int[] texMinFilValue = new int[]{9728, 9984, 9986};
   public static int[] texMagFilValue = new int[]{9728, 9729};
   private static IShaderPack shaderPack = null;
   public static boolean shaderPackLoaded = false;
   public static String currentShaderName;
   public static String SHADER_PACK_NAME_NONE;
   public static String SHADER_PACK_NAME_DEFAULT;
   public static String SHADER_PACKS_DIR_NAME;
   public static String OPTIONS_FILE_NAME;
   public static File shaderPacksDir = new File(Minecraft.m_91087_().f_91069_, "shaderpacks");
   static File configFile = new File(Minecraft.m_91087_().f_91069_, "optionsshaders.txt");
   private static ShaderOption[] shaderPackOptions = null;
   private static Set<String> shaderPackOptionSliders = null;
   static ShaderProfile[] shaderPackProfiles = null;
   static Map<String, ScreenShaderOptions> shaderPackGuiScreens = null;
   static Map<String, IExpressionBool> shaderPackProgramConditions = new HashMap();
   public static String PATH_SHADERS_PROPERTIES;
   public static PropertyDefaultFastFancyOff shaderPackClouds = new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
   public static PropertyDefaultTrueFalse shaderPackOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
   public static PropertyDefaultTrueFalse shaderPackOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
   public static PropertyDefaultTrueFalse shaderPackDynamicHandLight = new PropertyDefaultTrueFalse("dynamicHandLight", "Dynamic Hand Light", 0);
   public static PropertyDefaultTrueFalse shaderPackShadowTerrain = new PropertyDefaultTrueFalse("shadowTerrain", "Shadow Terrain", 0);
   public static PropertyDefaultTrueFalse shaderPackShadowTranslucent = new PropertyDefaultTrueFalse("shadowTranslucent", "Shadow Translucent", 0);
   public static PropertyDefaultTrueFalse shaderPackShadowEntities = new PropertyDefaultTrueFalse("shadowEntities", "Shadow Entities", 0);
   public static PropertyDefaultTrueFalse shaderPackShadowBlockEntities = new PropertyDefaultTrueFalse("shadowBlockEntities", "Shadow Block Entities", 0);
   public static PropertyDefaultTrueFalse shaderPackUnderwaterOverlay = new PropertyDefaultTrueFalse("underwaterOverlay", "Underwater Overlay", 0);
   public static PropertyDefaultTrueFalse shaderPackSun = new PropertyDefaultTrueFalse("sun", "Sun", 0);
   public static PropertyDefaultTrueFalse shaderPackMoon = new PropertyDefaultTrueFalse("moon", "Moon", 0);
   public static PropertyDefaultTrueFalse shaderPackVignette = new PropertyDefaultTrueFalse("vignette", "Vignette", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceSolid = new PropertyDefaultTrueFalse("backFace.solid", "Back-face Solid", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceCutout = new PropertyDefaultTrueFalse("backFace.cutout", "Back-face Cutout", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceCutoutMipped = new PropertyDefaultTrueFalse("backFace.cutoutMipped", "Back-face Cutout Mipped", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceTranslucent = new PropertyDefaultTrueFalse("backFace.translucent", "Back-face Translucent", 0);
   public static PropertyDefaultTrueFalse shaderPackRainDepth = new PropertyDefaultTrueFalse("rain.depth", "Rain Depth", 0);
   public static PropertyDefaultTrueFalse shaderPackBeaconBeamDepth = new PropertyDefaultTrueFalse("beacon.beam.depth", "Rain Depth", 0);
   public static PropertyDefaultTrueFalse shaderPackSeparateAo = new PropertyDefaultTrueFalse("separateAo", "Separate AO", 0);
   public static PropertyDefaultTrueFalse shaderPackFrustumCulling = new PropertyDefaultTrueFalse("frustum.culling", "Frustum Culling", 0);
   public static PropertyDefaultTrueFalse shaderPackShadowCulling = new PropertyDefaultTrueFalse("shadow.culling", "Shadow Culling", 0);
   public static PropertyDefaultTrueFalse shaderPackParticlesBeforeDeferred = new PropertyDefaultTrueFalse(
      "particles.before.deferred", "Particles before deferred", 0
   );
   private static Map<String, String> shaderPackResources = new HashMap();
   private static ClientLevel currentWorld = null;
   private static List<Integer> shaderPackDimensions = new ArrayList();
   private static ICustomTexture[] customTexturesGbuffers = null;
   private static ICustomTexture[] customTexturesComposite = null;
   private static ICustomTexture[] customTexturesDeferred = null;
   private static ICustomTexture[] customTexturesShadowcomp = null;
   private static ICustomTexture[] customTexturesPrepare = null;
   private static String noiseTexturePath = null;
   private static DynamicDimension[] colorBufferSizes = new DynamicDimension[16];
   private static CustomUniforms customUniforms = null;
   public static boolean saveFinalShaders = System.getProperty("shaders.debug.save", "false").equals("true");
   public static float blockLightLevel05 = 0.5F;
   public static float blockLightLevel06 = 0.6F;
   public static float blockLightLevel08 = 0.8F;
   public static float aoLevel = -1.0F;
   public static float sunPathRotation = 0.0F;
   public static float shadowAngleInterval = 0.0F;
   public static int fogMode = 0;
   public static int fogShape = 0;
   public static float fogDensity = 0.0F;
   public static float fogStart = 0.0F;
   public static float fogEnd = 0.0F;
   public static float fogColorR;
   public static float fogColorG;
   public static float fogColorB;
   public static float shadowIntervalSize = 2.0F;
   public static int terrainIconSize = 16;
   public static int[] terrainTextureSize = new int[2];
   private static ICustomTexture noiseTexture;
   private static boolean noiseTextureEnabled = false;
   private static int noiseTextureResolution = 256;
   static int[] colorTextureImageUnit = new int[]{0, 1, 2, 3, 7, 8, 9, 10, 16, 17, 18, 19, 20, 21, 22, 23};
   static int[] depthTextureImageUnit = new int[]{6, 11, 12};
   static int[] shadowColorTextureImageUnit = new int[]{13, 14};
   static int[] shadowDepthTextureImageUnit = new int[]{4, 5};
   static int[] colorImageUnit = new int[]{0, 1, 2, 3, 4, 5};
   static int[] shadowColorImageUnit = new int[]{6, 7};
   private static int bigBufferSize = (295 + 8 * ProgramCount) * 4;
   private static ByteBuffer bigBuffer = BufferUtils.createByteBuffer(bigBufferSize).limit(0);
   static float[] faProjection = new float[16];
   static float[] faProjectionInverse = new float[16];
   static float[] faModelView = new float[16];
   static float[] faModelViewInverse = new float[16];
   static float[] faShadowProjection = new float[16];
   static float[] faShadowProjectionInverse = new float[16];
   static float[] faShadowModelView = new float[16];
   static float[] faShadowModelViewInverse = new float[16];
   static FloatBuffer projection = nextFloatBuffer(16);
   static FloatBuffer projectionInverse = nextFloatBuffer(16);
   static FloatBuffer modelView = nextFloatBuffer(16);
   static FloatBuffer modelViewInverse = nextFloatBuffer(16);
   static FloatBuffer shadowProjection = nextFloatBuffer(16);
   static FloatBuffer shadowProjectionInverse = nextFloatBuffer(16);
   static FloatBuffer shadowModelView = nextFloatBuffer(16);
   static FloatBuffer shadowModelViewInverse = nextFloatBuffer(16);
   static Matrix4f lastModelView = new Matrix4f();
   static Matrix4f lastProjection = new Matrix4f();
   static FloatBuffer previousProjection = nextFloatBuffer(16);
   static FloatBuffer previousModelView = nextFloatBuffer(16);
   static FloatBuffer tempMatrixDirectBuffer = nextFloatBuffer(16);
   static FloatBuffer tempDirectFloatBuffer = nextFloatBuffer(16);
   static DrawBuffers dfbDrawBuffers = new DrawBuffers("dfbDrawBuffers", 16, 8);
   static DrawBuffers sfbDrawBuffers = new DrawBuffers("sfbDrawBuffers", 16, 8);
   static DrawBuffers drawBuffersNone = new DrawBuffers("drawBuffersNone", 16, 8).limit(0);
   static DrawBuffers[] drawBuffersColorAtt = makeDrawBuffersColorSingle(16);
   static boolean glDebugGroups;
   static boolean glDebugGroupProgram;
   public static Matrix4f MATRIX_IDENTITY = MathUtils.makeMatrixIdentity();
   static Map<Block, Integer> mapBlockToEntityData;
   private static String[] formatNames = new String[]{
      "R8",
      "RG8",
      "RGB8",
      "RGBA8",
      "R8_SNORM",
      "RG8_SNORM",
      "RGB8_SNORM",
      "RGBA8_SNORM",
      "R8I",
      "RG8I",
      "RGB8I",
      "RGBA8I",
      "R8UI",
      "RG8UI",
      "RGB8UI",
      "RGBA8UI",
      "R16",
      "RG16",
      "RGB16",
      "RGBA16",
      "R16_SNORM",
      "RG16_SNORM",
      "RGB16_SNORM",
      "RGBA16_SNORM",
      "R16F",
      "RG16F",
      "RGB16F",
      "RGBA16F",
      "R16I",
      "RG16I",
      "RGB16I",
      "RGBA16I",
      "R16UI",
      "RG16UI",
      "RGB16UI",
      "RGBA16UI",
      "R32F",
      "RG32F",
      "RGB32F",
      "RGBA32F",
      "R32I",
      "RG32I",
      "RGB32I",
      "RGBA32I",
      "R32UI",
      "RG32UI",
      "RGB32UI",
      "RGBA32UI",
      "R3_G3_B2",
      "RGB5_A1",
      "RGB10_A2",
      "R11F_G11F_B10F",
      "RGB9_E5"
   };
   private static int[] formatIds = new int[]{
      33321,
      33323,
      32849,
      32856,
      36756,
      36757,
      36758,
      36759,
      33329,
      33335,
      36239,
      36238,
      33330,
      33336,
      36221,
      36220,
      33322,
      33324,
      32852,
      32859,
      36760,
      36761,
      36762,
      36763,
      33325,
      33327,
      34843,
      34842,
      33331,
      33337,
      36233,
      36232,
      33332,
      33338,
      36215,
      36214,
      33326,
      33328,
      34837,
      34836,
      33333,
      33339,
      36227,
      36226,
      33334,
      33340,
      36209,
      36208,
      10768,
      32855,
      32857,
      35898,
      35901
   };
   private static Pattern patternLoadEntityDataMap = Pattern.m_289905_("\\s*([\\w:]+)\\s*=\\s*([-]?\\d+)\\s*");
   public static int[] entityData = new int[32];
   public static int entityDataIndex = 0;

   private Shaders() {
   }

   private static ByteBuffer nextByteBuffer(int size) {
      ByteBuffer buffer = bigBuffer;
      int pos = buffer.limit();
      buffer.position(pos).limit(pos + size);
      return buffer.slice();
   }

   public static IntBuffer nextIntBuffer(int size) {
      ByteBuffer buffer = bigBuffer;
      int pos = buffer.limit();
      buffer.position(pos).limit(pos + size * 4);
      return buffer.asIntBuffer();
   }

   private static FloatBuffer nextFloatBuffer(int size) {
      ByteBuffer buffer = bigBuffer;
      int pos = buffer.limit();
      buffer.position(pos).limit(pos + size * 4);
      return buffer.asFloatBuffer();
   }

   private static IntBuffer[] nextIntBufferArray(int count, int size) {
      IntBuffer[] aib = new IntBuffer[count];

      for (int i = 0; i < count; i++) {
         aib[i] = nextIntBuffer(size);
      }

      return aib;
   }

   private static DrawBuffers[] makeDrawBuffersColorSingle(int count) {
      DrawBuffers[] dbs = new DrawBuffers[count];

      for (int i = 0; i < dbs.length; i++) {
         DrawBuffers db = new DrawBuffers("single" + i, 16, 8);
         db.put(36064 + i);
         db.position(0);
         db.limit(1);
         dbs[i] = db;
      }

      return dbs;
   }

   public static void loadConfig() {
      SMCLog.info("Load shaders configuration.");

      try {
         if (!shaderPacksDir.exists()) {
            shaderPacksDir.mkdir();
         }
      } catch (Exception var8) {
         SMCLog.severe("Failed to open the shaderpacks directory: " + shaderPacksDir);
      }

      shadersConfig = new PropertiesOrdered();
      shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "");
      if (configFile.exists()) {
         try {
            FileReader reader = new FileReader(configFile);
            shadersConfig.load(reader);
            reader.close();
         } catch (Exception var7) {
         }
      }

      if (!configFile.exists()) {
         try {
            storeConfig();
         } catch (Exception var6) {
         }
      }

      EnumShaderOption[] ops = EnumShaderOption.values();

      for (int i = 0; i < ops.length; i++) {
         EnumShaderOption op = ops[i];
         String key = op.getPropertyKey();
         String def = op.getValueDefault();
         String val = shadersConfig.getProperty(key, def);
         setEnumShaderOption(op, val);
      }

      loadShaderPack();
   }

   private static void setEnumShaderOption(EnumShaderOption eso, String str) {
      if (str == null) {
         str = eso.getValueDefault();
      }

      switch (<unrepresentable>.$SwitchMap$net$optifine$shaders$config$EnumShaderOption[eso.ordinal()]) {
         case 1:
            configAntialiasingLevel = Config.parseInt(str, 0);
            break;
         case 2:
            configNormalMap = Config.parseBoolean(str, true);
            break;
         case 3:
            configSpecularMap = Config.parseBoolean(str, true);
            break;
         case 4:
            configRenderResMul = Config.parseFloat(str, 1.0F);
            break;
         case 5:
            configShadowResMul = Config.parseFloat(str, 1.0F);
            break;
         case 6:
            configHandDepthMul = Config.parseFloat(str, 0.125F);
            break;
         case 7:
            configCloudShadow = Config.parseBoolean(str, true);
            break;
         case 8:
            configOldHandLight.setPropertyValue(str);
            break;
         case 9:
            configOldLighting.setPropertyValue(str);
            break;
         case 10:
            currentShaderName = str;
            break;
         case 11:
            configTweakBlockDamage = Config.parseBoolean(str, true);
            break;
         case 12:
            configShadowClipFrustrum = Config.parseBoolean(str, true);
            break;
         case 13:
            configTexMinFilB = Config.parseInt(str, 0);
            break;
         case 14:
            configTexMinFilN = Config.parseInt(str, 0);
            break;
         case 15:
            configTexMinFilS = Config.parseInt(str, 0);
            break;
         case 16:
            configTexMagFilB = Config.parseInt(str, 0);
            break;
         case 17:
            configTexMagFilB = Config.parseInt(str, 0);
            break;
         case 18:
            configTexMagFilB = Config.parseInt(str, 0);
            break;
         default:
            throw new IllegalArgumentException("Unknown option: " + eso);
      }
   }

   public static void storeConfig() {
      SMCLog.info("Save shaders configuration.");
      if (shadersConfig == null) {
         shadersConfig = new PropertiesOrdered();
      }

      EnumShaderOption[] ops = EnumShaderOption.values();

      for (int i = 0; i < ops.length; i++) {
         EnumShaderOption op = ops[i];
         String key = op.getPropertyKey();
         String val = getEnumShaderOption(op);
         shadersConfig.setProperty(key, val);
      }

      try {
         FileWriter writer = new FileWriter(configFile);
         shadersConfig.store(writer, null);
         writer.close();
      } catch (Exception var5) {
         SMCLog.severe("Error saving configuration: " + var5.getClass().getName() + ": " + var5.getMessage());
      }
   }

   public static String getEnumShaderOption(EnumShaderOption eso) {
      switch (<unrepresentable>.$SwitchMap$net$optifine$shaders$config$EnumShaderOption[eso.ordinal()]) {
         case 1:
            return Integer.toString(configAntialiasingLevel);
         case 2:
            return Boolean.toString(configNormalMap);
         case 3:
            return Boolean.toString(configSpecularMap);
         case 4:
            return Float.toString(configRenderResMul);
         case 5:
            return Float.toString(configShadowResMul);
         case 6:
            return Float.toString(configHandDepthMul);
         case 7:
            return Boolean.toString(configCloudShadow);
         case 8:
            return configOldHandLight.getPropertyValue();
         case 9:
            return configOldLighting.getPropertyValue();
         case 10:
            return currentShaderName;
         case 11:
            return Boolean.toString(configTweakBlockDamage);
         case 12:
            return Boolean.toString(configShadowClipFrustrum);
         case 13:
            return Integer.toString(configTexMinFilB);
         case 14:
            return Integer.toString(configTexMinFilN);
         case 15:
            return Integer.toString(configTexMinFilS);
         case 16:
            return Integer.toString(configTexMagFilB);
         case 17:
            return Integer.toString(configTexMagFilB);
         case 18:
            return Integer.toString(configTexMagFilB);
         default:
            throw new IllegalArgumentException("Unknown option: " + eso);
      }
   }

   public static void setShaderPack(String par1name) {
      currentShaderName = par1name;
      shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), par1name);
      loadShaderPack();
   }

   public static void loadShaderPack() {
      boolean shaderPackLoadedPrev = shaderPackLoaded;
      boolean oldLightingPrev = isOldLighting();
      if (f_303183_.f_91060_ != null) {
         f_303183_.f_91060_.pauseChunkUpdates();
      }

      shaderPackLoaded = false;
      if (shaderPack != null) {
         shaderPack.close();
         shaderPack = null;
         shaderPackResources.clear();
         shaderPackDimensions.clear();
         shaderPackOptions = null;
         shaderPackOptionSliders = null;
         shaderPackProfiles = null;
         shaderPackGuiScreens = null;
         shaderPackProgramConditions.clear();
         shaderPackClouds.resetValue();
         shaderPackOldHandLight.resetValue();
         shaderPackDynamicHandLight.resetValue();
         shaderPackOldLighting.resetValue();
         resetCustomTextures();
         noiseTexturePath = null;
      }

      boolean shadersBlocked = false;
      if (Config.isAntialiasing()) {
         SMCLog.info("Shaders can not be loaded, Antialiasing is enabled: " + Config.getAntialiasingLevel() + "x");
         shadersBlocked = true;
      }

      if (Config.isGraphicsFabulous()) {
         SMCLog.info("Shaders can not be loaded, Fabulous Graphics is enabled.");
         shadersBlocked = true;
      }

      String packName = shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "(internal)");
      if (!shadersBlocked) {
         shaderPack = getShaderPack(packName);
         shaderPackLoaded = shaderPack != null;
      }

      if (shaderPackLoaded) {
         SMCLog.info("Loaded shaderpack: " + getShaderPackName());
      } else {
         SMCLog.info("No shaderpack loaded.");
         shaderPack = new ShaderPackNone();
      }

      if (saveFinalShaders) {
         clearDirectory(new File(shaderPacksDir, "debug"));
      }

      loadShaderPackResources();
      loadShaderPackDimensions();
      shaderPackOptions = loadShaderPackOptions();
      loadShaderPackFixedProperties();
      loadShaderPackDynamicProperties();
      boolean formatChanged = shaderPackLoaded != shaderPackLoadedPrev;
      boolean oldLightingChanged = isOldLighting() != oldLightingPrev;
      if (formatChanged || oldLightingChanged) {
         DefaultVertexFormat.updateVertexFormats();
         updateBlockLightLevel();
      }

      if (f_303183_.m_91098_() != null) {
         CustomBlockLayers.m_252999_();
      }

      if (f_303183_.f_91060_ != null) {
         f_303183_.f_91060_.resumeChunkUpdates();
      }

      if ((formatChanged || oldLightingChanged) && f_303183_.m_91098_() != null) {
         f_303183_.m_91088_();
      }
   }

   public static IShaderPack getShaderPack(String name) {
      if (name == null) {
         return null;
      } else {
         name = name.trim();
         if (name.isEmpty() || name.equals("OFF")) {
            return null;
         } else if (name.equals("(internal)")) {
            return new ShaderPackDefault();
         } else {
            try {
               File packFile = new File(shaderPacksDir, name);
               if (packFile.isDirectory()) {
                  return new ShaderPackFolder(name, packFile);
               } else {
                  return packFile.isFile() && name.toLowerCase().endsWith(".zip") ? new ShaderPackZip(name, packFile) : null;
               }
            } catch (Exception var2) {
               var2.printStackTrace();
               return null;
            }
         }
      }
   }

   public static IShaderPack getShaderPack() {
      return shaderPack;
   }

   private static void loadShaderPackDimensions() {
      shaderPackDimensions.clear();

      for (int i = -128; i <= 128; i++) {
         String worldDir = "/shaders/world" + i;
         if (shaderPack.hasDirectory(worldDir)) {
            shaderPackDimensions.add(i);
         }
      }

      if (shaderPackDimensions.size() > 0) {
         Integer[] ids = (Integer[])shaderPackDimensions.toArray(new Integer[shaderPackDimensions.size()]);
         Config.dbg("[Shaders] Worlds: " + Config.arrayToString((Object[])ids));
      }
   }

   private static void loadShaderPackFixedProperties() {
      shaderPackOldLighting.resetValue();
      shaderPackSeparateAo.resetValue();
      if (shaderPack != null) {
         String path = "/shaders/shaders.properties";

         try {
            InputStream in = shaderPack.getResourceAsStream(path);
            if (in == null) {
               return;
            }

            in = MacroProcessor.m_288227_(in, path, false);
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            shaderPackOldLighting.loadFrom(props);
            shaderPackSeparateAo.loadFrom(props);
            shaderPackOptionSliders = ShaderPackParser.parseOptionSliders(props, shaderPackOptions);
            shaderPackProfiles = ShaderPackParser.parseProfiles(props, shaderPackOptions);
            shaderPackGuiScreens = ShaderPackParser.parseGuiScreens(props, shaderPackProfiles, shaderPackOptions);
         } catch (IOException var3) {
            Config.warn("[Shaders] Error reading: " + path);
         }
      }
   }

   private static void loadShaderPackDynamicProperties() {
      shaderPackClouds.resetValue();
      shaderPackOldHandLight.resetValue();
      shaderPackDynamicHandLight.resetValue();
      shaderPackShadowTerrain.resetValue();
      shaderPackShadowTranslucent.resetValue();
      shaderPackShadowEntities.resetValue();
      shaderPackShadowBlockEntities.resetValue();
      shaderPackUnderwaterOverlay.resetValue();
      shaderPackSun.resetValue();
      shaderPackMoon.resetValue();
      shaderPackVignette.resetValue();
      shaderPackBackFaceSolid.resetValue();
      shaderPackBackFaceCutout.resetValue();
      shaderPackBackFaceCutoutMipped.resetValue();
      shaderPackBackFaceTranslucent.resetValue();
      shaderPackRainDepth.resetValue();
      shaderPackBeaconBeamDepth.resetValue();
      shaderPackFrustumCulling.resetValue();
      shaderPackShadowCulling.resetValue();
      shaderPackParticlesBeforeDeferred.resetValue();
      BlockAliases.reset();
      ItemAliases.reset();
      EntityAliases.reset();
      customUniforms = null;

      for (int i = 0; i < ProgramsAll.length; i++) {
         Program p = ProgramsAll[i];
         p.resetProperties();
      }

      Arrays.fill(colorBufferSizes, null);
      if (shaderPack != null) {
         BlockAliases.m_252999_(shaderPack);
         ItemAliases.m_252999_(shaderPack);
         EntityAliases.m_252999_(shaderPack);
         String path = "/shaders/shaders.properties";

         try {
            InputStream in = shaderPack.getResourceAsStream(path);
            if (in == null) {
               return;
            }

            in = MacroProcessor.m_288227_(in, path, true);
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            shaderPackClouds.loadFrom(props);
            shaderPackOldHandLight.loadFrom(props);
            shaderPackDynamicHandLight.loadFrom(props);
            shaderPackShadowTerrain.loadFrom(props);
            shaderPackShadowTranslucent.loadFrom(props);
            shaderPackShadowEntities.loadFrom(props);
            shaderPackShadowBlockEntities.loadFrom(props);
            shaderPackUnderwaterOverlay.loadFrom(props);
            shaderPackSun.loadFrom(props);
            shaderPackVignette.loadFrom(props);
            shaderPackMoon.loadFrom(props);
            shaderPackBackFaceSolid.loadFrom(props);
            shaderPackBackFaceCutout.loadFrom(props);
            shaderPackBackFaceCutoutMipped.loadFrom(props);
            shaderPackBackFaceTranslucent.loadFrom(props);
            shaderPackRainDepth.loadFrom(props);
            shaderPackBeaconBeamDepth.loadFrom(props);
            shaderPackFrustumCulling.loadFrom(props);
            shaderPackShadowCulling.loadFrom(props);
            shaderPackParticlesBeforeDeferred.loadFrom(props);
            shaderPackProgramConditions = ShaderPackParser.parseProgramConditions(props, shaderPackOptions);
            customTexturesGbuffers = loadCustomTextures(props, ProgramStage.GBUFFERS);
            customTexturesComposite = loadCustomTextures(props, ProgramStage.COMPOSITE);
            customTexturesDeferred = loadCustomTextures(props, ProgramStage.DEFERRED);
            customTexturesShadowcomp = loadCustomTextures(props, ProgramStage.SHADOWCOMP);
            customTexturesPrepare = loadCustomTextures(props, ProgramStage.PREPARE);
            noiseTexturePath = props.getProperty("texture.noise");
            if (noiseTexturePath != null) {
               noiseTextureEnabled = true;
            }

            customUniforms = ShaderPackParser.parseCustomUniforms(props);
            ShaderPackParser.parseAlphaStates(props);
            ShaderPackParser.parseBlendStates(props);
            ShaderPackParser.parseRenderScales(props);
            ShaderPackParser.parseBuffersFlip(props);
            colorBufferSizes = ShaderPackParser.parseBufferSizes(props, 16);
         } catch (IOException var3) {
            Config.warn("[Shaders] Error reading: " + path);
         }
      }
   }

   private static ICustomTexture[] loadCustomTextures(Properties props, ProgramStage stage) {
      String PREFIX_TEXTURE = "texture." + stage.getName() + ".";
      Set keys = props.keySet();
      List<ICustomTexture> list = new ArrayList();

      for (String key : keys) {
         if (key.startsWith(PREFIX_TEXTURE)) {
            String name = StrUtils.removePrefix(key, PREFIX_TEXTURE);
            name = StrUtils.removeSuffix(name, new String[]{".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9"});
            String path = props.getProperty(key).trim();
            int index = getTextureIndex(stage, name);
            if (index < 0) {
               SMCLog.warning("Invalid texture name: " + key);
            } else {
               ICustomTexture ct = loadCustomTexture(index, path);
               if (ct != null) {
                  SMCLog.info("Custom texture: " + key + " = " + path);
                  list.add(ct);
               }
            }
         }
      }

      return list.size() <= 0 ? null : (ICustomTexture[])list.toArray(new ICustomTexture[list.size()]);
   }

   private static ICustomTexture loadCustomTexture(int textureUnit, String path) {
      if (path == null) {
         return null;
      } else {
         path = path.trim();
         if (path.indexOf(58) >= 0) {
            return loadCustomTextureLocation(textureUnit, path);
         } else {
            return path.indexOf(32) >= 0 ? loadCustomTextureRaw(textureUnit, path) : loadCustomTextureShaders(textureUnit, path);
         }
      }
   }

   private static ICustomTexture loadCustomTextureLocation(int textureUnit, String path) {
      String pathFull = path.trim();
      int variant = 0;
      if (pathFull.startsWith("minecraft:textures/")) {
         pathFull = StrUtils.addSuffixCheck(pathFull, ".png");
         if (pathFull.endsWith("_n.png")) {
            pathFull = StrUtils.replaceSuffix(pathFull, "_n.png", ".png");
            variant = 1;
         } else if (pathFull.endsWith("_s.png")) {
            pathFull = StrUtils.replaceSuffix(pathFull, "_s.png", ".png");
            variant = 2;
         }
      }

      if (pathFull.startsWith("minecraft:dynamic/lightmap_")) {
         pathFull = pathFull.replace("lightmap", "light_map");
      }

      ResourceLocation loc = new ResourceLocation(pathFull);
      return new CustomTextureLocation(textureUnit, loc, variant);
   }

   private static void reloadCustomTexturesLocation(ICustomTexture[] cts) {
      if (cts != null) {
         for (int i = 0; i < cts.length; i++) {
            if (cts[i] instanceof CustomTextureLocation ctl) {
               ctl.reloadTexture();
            }
         }
      }
   }

   private static ICustomTexture loadCustomTextureRaw(int textureUnit, String line) {
      ConnectedParser cp = new ConnectedParser("Shaders");
      String[] parts = Config.tokenize(line, " ");
      Deque<String> params = new ArrayDeque(Arrays.asList(parts));
      String path = (String)params.poll();
      TextureType type = (TextureType)cp.parseEnum((String)params.poll(), TextureType.values(), "texture type");
      if (type == null) {
         SMCLog.warning("Invalid raw texture type: " + line);
         return null;
      } else {
         InternalFormat internalFormat = (InternalFormat)cp.parseEnum((String)params.poll(), InternalFormat.values(), "internal format");
         if (internalFormat == null) {
            SMCLog.warning("Invalid raw texture internal format: " + line);
            return null;
         } else {
            int width = 0;
            int height = 0;
            int depth = 0;
            switch (<unrepresentable>.$SwitchMap$net$optifine$texture$TextureType[type.ordinal()]) {
               case 1:
                  width = cp.parseInt((String)params.poll(), -1);
                  break;
               case 2:
                  width = cp.parseInt((String)params.poll(), -1);
                  height = cp.parseInt((String)params.poll(), -1);
                  break;
               case 3:
                  width = cp.parseInt((String)params.poll(), -1);
                  height = cp.parseInt((String)params.poll(), -1);
                  depth = cp.parseInt((String)params.poll(), -1);
                  break;
               case 4:
                  width = cp.parseInt((String)params.poll(), -1);
                  height = cp.parseInt((String)params.poll(), -1);
                  break;
               default:
                  SMCLog.warning("Invalid raw texture type: " + type);
                  return null;
            }

            if (width >= 0 && height >= 0 && depth >= 0) {
               PixelFormat pixelFormat = (PixelFormat)cp.parseEnum((String)params.poll(), PixelFormat.values(), "pixel format");
               if (pixelFormat == null) {
                  SMCLog.warning("Invalid raw texture pixel format: " + line);
                  return null;
               } else {
                  PixelType pixelType = (PixelType)cp.parseEnum((String)params.poll(), PixelType.values(), "pixel type");
                  if (pixelType == null) {
                     SMCLog.warning("Invalid raw texture pixel type: " + line);
                     return null;
                  } else if (!params.isEmpty()) {
                     SMCLog.warning("Invalid raw texture, too many parameters: " + line);
                     return null;
                  } else {
                     return loadCustomTextureRaw(textureUnit, line, path, type, internalFormat, width, height, depth, pixelFormat, pixelType);
                  }
               }
            } else {
               SMCLog.warning("Invalid raw texture size: " + line);
               return null;
            }
         }
      }
   }

   private static ICustomTexture loadCustomTextureRaw(
      int textureUnit,
      String line,
      String path,
      TextureType type,
      InternalFormat internalFormat,
      int width,
      int height,
      int depth,
      PixelFormat pixelFormat,
      PixelType pixelType
   ) {
      try {
         String pathFull = "shaders/" + StrUtils.removePrefix(path, "/");
         InputStream in = shaderPack.getResourceAsStream(pathFull);
         if (in == null) {
            SMCLog.warning("Raw texture not found: " + path);
            return null;
         } else {
            byte[] bytes = Config.readAll(in);
            IOUtils.closeQuietly(in);
            ByteBuffer bb = GlUtil.m_166247_(bytes.length);
            bb.put(bytes);
            bb.flip();
            TextureMetadataSection tms = SimpleShaderTexture.loadTextureMetadataSection(pathFull, new TextureMetadataSection(true, true));
            return new CustomTextureRaw(type, internalFormat, width, height, depth, pixelFormat, pixelType, bb, textureUnit, tms.m_119115_(), tms.m_119116_());
         }
      } catch (IOException var16) {
         SMCLog.warning("Error loading raw texture: " + path);
         SMCLog.warning(var16.getClass().getName() + ": " + var16.getMessage());
         return null;
      }
   }

   private static ICustomTexture loadCustomTextureShaders(int textureUnit, String path) {
      path = path.trim();
      if (path.indexOf(46) < 0) {
         path = path + ".png";
      }

      try {
         String pathFull = "shaders/" + StrUtils.removePrefix(path, "/");
         InputStream in = shaderPack.getResourceAsStream(pathFull);
         if (in == null) {
            SMCLog.warning("Texture not found: " + path);
            return null;
         } else {
            IOUtils.closeQuietly(in);
            SimpleShaderTexture tex = new SimpleShaderTexture(pathFull);
            tex.m_6704_(f_303183_.m_91098_());
            return new CustomTexture(textureUnit, pathFull, tex);
         }
      } catch (IOException var6) {
         SMCLog.warning("Error loading texture: " + path);
         SMCLog.warning(var6.getClass().getName() + ": " + var6.getMessage());
         return null;
      }
   }

   private static int getTextureIndex(ProgramStage stage, String name) {
      if (stage == ProgramStage.GBUFFERS) {
         int colortexIndex = ShaderParser.getIndex(name, "colortex", 4, 15);
         if (colortexIndex >= 0) {
            return colorTextureImageUnit[colortexIndex];
         }

         if (name.equals("texture")) {
            return 0;
         }

         if (name.equals("lightmap")) {
            return 1;
         }

         if (name.equals("normals")) {
            return 2;
         }

         if (name.equals("specular")) {
            return 3;
         }

         if (name.equals("shadowtex0") || name.equals("watershadow")) {
            return 4;
         }

         if (name.equals("shadow")) {
            return waterShadowEnabled ? 5 : 4;
         }

         if (name.equals("shadowtex1")) {
            return 5;
         }

         if (name.equals("depthtex0")) {
            return 6;
         }

         if (name.equals("gaux1")) {
            return 7;
         }

         if (name.equals("gaux2")) {
            return 8;
         }

         if (name.equals("gaux3")) {
            return 9;
         }

         if (name.equals("gaux4")) {
            return 10;
         }

         if (name.equals("depthtex1")) {
            return 12;
         }

         if (name.equals("shadowcolor0") || name.equals("shadowcolor")) {
            return 13;
         }

         if (name.equals("shadowcolor1")) {
            return 14;
         }

         if (name.equals("noisetex")) {
            return 15;
         }
      }

      if (stage.isAnyComposite()) {
         int colortexIndexx = ShaderParser.getIndex(name, "colortex", 0, 15);
         if (colortexIndexx >= 0) {
            return colorTextureImageUnit[colortexIndexx];
         }

         if (name.equals("colortex0")) {
            return 0;
         }

         if (name.equals("gdepth")) {
            return 1;
         }

         if (name.equals("gnormal")) {
            return 2;
         }

         if (name.equals("composite")) {
            return 3;
         }

         if (name.equals("shadowtex0") || name.equals("watershadow")) {
            return 4;
         }

         if (name.equals("shadow")) {
            return waterShadowEnabled ? 5 : 4;
         }

         if (name.equals("shadowtex1")) {
            return 5;
         }

         if (name.equals("depthtex0") || name.equals("gdepthtex")) {
            return 6;
         }

         if (name.equals("gaux1")) {
            return 7;
         }

         if (name.equals("gaux2")) {
            return 8;
         }

         if (name.equals("gaux3")) {
            return 9;
         }

         if (name.equals("gaux4")) {
            return 10;
         }

         if (name.equals("depthtex1")) {
            return 11;
         }

         if (name.equals("depthtex2")) {
            return 12;
         }

         if (name.equals("shadowcolor0") || name.equals("shadowcolor")) {
            return 13;
         }

         if (name.equals("shadowcolor1")) {
            return 14;
         }

         if (name.equals("noisetex")) {
            return 15;
         }
      }

      return -1;
   }

   private static void bindCustomTextures(ICustomTexture[] cts) {
      if (cts != null) {
         for (int i = 0; i < cts.length; i++) {
            ICustomTexture ct = cts[i];
            GlStateManager._activeTexture(33984 + ct.getTextureUnit());
            int texId = ct.getTextureId();
            int target = ct.getTarget();
            if (target == 3553) {
               GlStateManager._bindTexture(texId);
            } else {
               GL11.glBindTexture(target, texId);
            }
         }

         GlStateManager._activeTexture(33984);
      }
   }

   private static void resetCustomTextures() {
      deleteCustomTextures(customTexturesGbuffers);
      deleteCustomTextures(customTexturesComposite);
      deleteCustomTextures(customTexturesDeferred);
      deleteCustomTextures(customTexturesShadowcomp);
      deleteCustomTextures(customTexturesPrepare);
      customTexturesGbuffers = null;
      customTexturesComposite = null;
      customTexturesDeferred = null;
      customTexturesShadowcomp = null;
      customTexturesPrepare = null;
   }

   private static void deleteCustomTextures(ICustomTexture[] cts) {
      if (cts != null) {
         for (int i = 0; i < cts.length; i++) {
            ICustomTexture ct = cts[i];
            ct.deleteTexture();
         }
      }
   }

   public static ShaderOption[] getShaderPackOptions(String screenName) {
      ShaderOption[] ops = (ShaderOption[])shaderPackOptions.clone();
      if (shaderPackGuiScreens == null) {
         if (shaderPackProfiles != null) {
            ShaderOptionProfile optionProfile = new ShaderOptionProfile(shaderPackProfiles, ops);
            ops = (ShaderOption[])Config.addObjectToArray(ops, optionProfile, 0);
         }

         return getVisibleOptions(ops);
      } else {
         String key = screenName != null ? "screen." + screenName : "screen";
         ScreenShaderOptions sso = (ScreenShaderOptions)shaderPackGuiScreens.get(key);
         if (sso == null) {
            return new ShaderOption[0];
         } else {
            ShaderOption[] sos = sso.getShaderOptions();
            List<ShaderOption> list = new ArrayList();

            for (int i = 0; i < sos.length; i++) {
               ShaderOption so = sos[i];
               if (so == null) {
                  list.add(null);
               } else if (so instanceof ShaderOptionRest) {
                  ShaderOption[] restOps = getShaderOptionsRest(shaderPackGuiScreens, ops);
                  list.addAll(Arrays.asList(restOps));
               } else {
                  list.add(so);
               }
            }

            return (ShaderOption[])list.toArray(new ShaderOption[list.size()]);
         }
      }
   }

   public static int getShaderPackColumns(String screenName, int def) {
      String key = screenName != null ? "screen." + screenName : "screen";
      if (shaderPackGuiScreens == null) {
         return def;
      } else {
         ScreenShaderOptions sso = (ScreenShaderOptions)shaderPackGuiScreens.get(key);
         return sso == null ? def : sso.getColumns();
      }
   }

   private static ShaderOption[] getShaderOptionsRest(Map<String, ScreenShaderOptions> mapScreens, ShaderOption[] ops) {
      Set<String> setNames = new HashSet();

      for (String key : mapScreens.keySet()) {
         ScreenShaderOptions sso = (ScreenShaderOptions)mapScreens.get(key);
         ShaderOption[] sos = sso.getShaderOptions();

         for (int v = 0; v < sos.length; v++) {
            ShaderOption so = sos[v];
            if (so != null) {
               setNames.add(so.getName());
            }
         }
      }

      List<ShaderOption> list = new ArrayList();

      for (int i = 0; i < ops.length; i++) {
         ShaderOption so = ops[i];
         if (so.isVisible()) {
            String name = so.getName();
            if (!setNames.m_274455_(name)) {
               list.add(so);
            }
         }
      }

      return (ShaderOption[])list.toArray(new ShaderOption[list.size()]);
   }

   public static ShaderOption getShaderOption(String name) {
      return ShaderUtils.getShaderOption(name, shaderPackOptions);
   }

   public static ShaderOption[] getShaderPackOptions() {
      return shaderPackOptions;
   }

   public static boolean isShaderPackOptionSlider(String name) {
      return shaderPackOptionSliders == null ? false : shaderPackOptionSliders.m_274455_(name);
   }

   private static ShaderOption[] getVisibleOptions(ShaderOption[] ops) {
      List<ShaderOption> list = new ArrayList();

      for (int i = 0; i < ops.length; i++) {
         ShaderOption so = ops[i];
         if (so.isVisible()) {
            list.add(so);
         }
      }

      return (ShaderOption[])list.toArray(new ShaderOption[list.size()]);
   }

   public static void saveShaderPackOptions() {
      saveShaderPackOptions(shaderPackOptions, shaderPack);
   }

   private static void saveShaderPackOptions(ShaderOption[] sos, IShaderPack sp) {
      Properties props = new PropertiesOrdered();
      if (shaderPackOptions != null) {
         for (int i = 0; i < sos.length; i++) {
            ShaderOption so = sos[i];
            if (so.isChanged() && so.isEnabled()) {
               props.setProperty(so.getName(), so.getValue());
            }
         }
      }

      try {
         saveOptionProperties(sp, props);
      } catch (IOException var5) {
         Config.warn("[Shaders] Error saving configuration for " + shaderPack.getName());
         var5.printStackTrace();
      }
   }

   private static void saveOptionProperties(IShaderPack sp, Properties props) throws IOException {
      String path = "shaderpacks/" + sp.getName() + ".txt";
      File propFile = new File(Minecraft.m_91087_().f_91069_, path);
      if (props.isEmpty()) {
         propFile.delete();
      } else {
         FileOutputStream fos = new FileOutputStream(propFile);
         props.store(fos, null);
         fos.flush();
         fos.close();
      }
   }

   private static ShaderOption[] loadShaderPackOptions() {
      try {
         String[] programNames = programs.getProgramNames();
         Properties props = loadOptionProperties(shaderPack);
         ShaderOption[] sos = ShaderPackParser.parseShaderPackOptions(shaderPack, programNames, shaderPackDimensions);

         for (int i = 0; i < sos.length; i++) {
            ShaderOption so = sos[i];
            String val = props.getProperty(so.getName());
            if (val != null) {
               so.resetValue();
               if (!so.setValue(val)) {
                  Config.warn("[Shaders] Invalid value, option: " + so.getName() + ", value: " + val);
               }
            }
         }

         return sos;
      } catch (IOException var6) {
         Config.warn("[Shaders] Error reading configuration for " + shaderPack.getName());
         var6.printStackTrace();
         return null;
      }
   }

   private static Properties loadOptionProperties(IShaderPack sp) throws IOException {
      Properties props = new PropertiesOrdered();
      String path = "shaderpacks/" + sp.getName() + ".txt";
      File propFile = new File(Minecraft.m_91087_().f_91069_, path);
      if (propFile.exists() && propFile.isFile() && propFile.canRead()) {
         FileInputStream fis = new FileInputStream(propFile);
         props.load(fis);
         fis.close();
         return props;
      } else {
         return props;
      }
   }

   public static ShaderOption[] getChangedOptions(ShaderOption[] ops) {
      List<ShaderOption> list = new ArrayList();

      for (int i = 0; i < ops.length; i++) {
         ShaderOption op = ops[i];
         if (op.isEnabled() && op.isChanged()) {
            list.add(op);
         }
      }

      return (ShaderOption[])list.toArray(new ShaderOption[list.size()]);
   }

   public static ArrayList listOfShaders() {
      ArrayList<String> listDir = new ArrayList();
      ArrayList<String> listZip = new ArrayList();

      try {
         if (!shaderPacksDir.exists()) {
            shaderPacksDir.mkdir();
         }

         File[] listOfFiles = shaderPacksDir.listFiles();

         for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            String name = file.getName();
            if (file.isDirectory()) {
               if (!name.equals("debug")) {
                  File subDir = new File(file, "shaders");
                  if (subDir.exists() && subDir.isDirectory()) {
                     listDir.add(name);
                  }
               }
            } else if (file.isFile() && name.toLowerCase().endsWith(".zip")) {
               listZip.add(name);
            }
         }
      } catch (Exception var7) {
      }

      Collections.m_277065_(listDir, String.CASE_INSENSITIVE_ORDER);
      Collections.m_277065_(listZip, String.CASE_INSENSITIVE_ORDER);
      ArrayList<String> list = new ArrayList();
      list.add("OFF");
      list.add("(internal)");
      list.addAll(listDir);
      list.addAll(listZip);
      return list;
   }

   public static int checkFramebufferStatus(String location) {
      int status = GL43.glCheckFramebufferStatus(36160);
      if (status != 36053) {
         SMCLog.severe("FramebufferStatus 0x%04X at '%s'", status, location);
      }

      return status;
   }

   public static int checkGLError(String location) {
      int errorCode = GlStateManager._getError();
      if (errorCode != 0 && GlErrors.isEnabled(errorCode)) {
         String errorText = Config.getGlErrorString(errorCode);
         String shadersInfo = getErrorInfo(errorCode, location);
         String messageLog = String.m_12886_("OpenGL error: %s (%s)%s, at: %s", new Object[]{errorCode, errorText, shadersInfo, location});
         SMCLog.severe(messageLog);
         if (Config.isShowGlErrors() && TimedEvent.isActive("ShowGlErrorShaders", 10000L)) {
            String messageChat = I18n.m_118938_("of.message.openglError", new Object[]{errorCode, errorText});
            printChat(messageChat);
         }
      }

      return errorCode;
   }

   private static String getErrorInfo(int errorCode, String location) {
      StringBuilder sb = new StringBuilder();
      if (errorCode == 1286) {
         int statusCode = GL43.glCheckFramebufferStatus(36160);
         String statusText = getFramebufferStatusText(statusCode);
         String info = ", fbStatus: " + statusCode + " (" + statusText + ")";
         sb.append(info);
      }

      String programName = activeProgram.getName();
      if (programName.isEmpty()) {
         programName = "none";
      }

      sb.append(", program: " + programName);
      Program activeProgramReal = getProgramById(activeProgramID);
      if (activeProgramReal != activeProgram) {
         String programRealName = activeProgramReal.getName();
         if (programRealName.isEmpty()) {
            programRealName = "none";
         }

         sb.append(" (" + programRealName + ")");
      }

      if (location.equals("setDrawBuffers")) {
         sb.append(", drawBuffers: " + ArrayUtils.arrayToString((Object[])activeProgram.getDrawBufSettings()));
      }

      return sb.toString();
   }

   private static Program getProgramById(int programID) {
      for (int i = 0; i < ProgramsAll.length; i++) {
         Program pi = ProgramsAll[i];
         if (pi.getId() == programID) {
            return pi;
         }
      }

      return ProgramNone;
   }

   private static String getFramebufferStatusText(int fbStatusCode) {
      switch (fbStatusCode) {
         case 33305:
            return "Undefined";
         case 36053:
            return "Complete";
         case 36054:
            return "Incomplete attachment";
         case 36055:
            return "Incomplete missing attachment";
         case 36059:
            return "Incomplete draw buffer";
         case 36060:
            return "Incomplete read buffer";
         case 36061:
            return "Unsupported";
         case 36182:
            return "Incomplete multisample";
         case 36264:
            return "Incomplete layer targets";
         default:
            return "Unknown";
      }
   }

   private static void printChat(String str) {
      f_303183_.f_91065_.m_93076_().m_93785_(Component.m_237113_(str));
   }

   public static void printChatAndLogError(String str) {
      SMCLog.severe(str);
      f_303183_.f_91065_.m_93076_().m_93785_(Component.m_237113_(str));
   }

   public static void printIntBuffer(String title, IntBuffer buf) {
      StringBuilder sb = new StringBuilder(128);
      sb.append(title).append(" [pos ").append(buf.position()).append(" lim ").append(buf.limit()).append(" cap ").append(buf.capacity()).append(" :");
      int lim = buf.limit();

      for (int i = 0; i < lim; i++) {
         sb.append(" ").append(buf.get(i));
      }

      sb.append("]");
      SMCLog.info(sb.toString());
   }

   public static void startup(Minecraft mc) {
      checkShadersModInstalled();
      f_303183_ = mc;
      mc = Minecraft.m_91087_();
      capabilities = GL.getCapabilities();
      glVersionString = GL11.glGetString(7938);
      glVendorString = GL11.glGetString(7936);
      glRendererString = GL11.glGetString(7937);
      SMCLog.info("OpenGL Version: " + glVersionString);
      SMCLog.info("Vendor:  " + glVendorString);
      SMCLog.info("Renderer: " + glRendererString);
      SMCLog.info(
         "Capabilities: "
            + (capabilities.OpenGL20 ? " 2.0 " : " - ")
            + (capabilities.OpenGL21 ? " 2.1 " : " - ")
            + (capabilities.OpenGL30 ? " 3.0 " : " - ")
            + (capabilities.OpenGL32 ? " 3.2 " : " - ")
            + (capabilities.OpenGL40 ? " 4.0 " : " - ")
      );
      SMCLog.info("GL_MAX_DRAW_BUFFERS: " + GL43.glGetInteger(34852));
      SMCLog.info("GL_MAX_COLOR_ATTACHMENTS: " + GL43.glGetInteger(36063));
      SMCLog.info("GL_MAX_TEXTURE_IMAGE_UNITS: " + GL43.glGetInteger(34930));
      hasGlGenMipmap = capabilities.OpenGL30;
      glDebugGroups = Boolean.getBoolean("gl.debug.groups") && capabilities.GL_KHR_debug;
      if (glDebugGroups) {
         SMCLog.info("glDebugGroups: true");
      }

      loadConfig();
   }

   public static void updateBlockLightLevel() {
      if (isOldLighting()) {
         blockLightLevel05 = 0.5F;
         blockLightLevel06 = 0.6F;
         blockLightLevel08 = 0.8F;
      } else {
         blockLightLevel05 = 1.0F;
         blockLightLevel06 = 1.0F;
         blockLightLevel08 = 1.0F;
      }
   }

   public static boolean isOldHandLight() {
      if (!configOldHandLight.isDefault()) {
         return configOldHandLight.isTrue();
      } else {
         return !shaderPackOldHandLight.isDefault() ? shaderPackOldHandLight.isTrue() : true;
      }
   }

   public static boolean isDynamicHandLight() {
      return !shaderPackDynamicHandLight.isDefault() ? shaderPackDynamicHandLight.isTrue() : true;
   }

   public static boolean isOldLighting() {
      if (!configOldLighting.isDefault()) {
         return configOldLighting.isTrue();
      } else {
         return !shaderPackOldLighting.isDefault() ? shaderPackOldLighting.isTrue() : true;
      }
   }

   public static boolean isRenderShadowTerrain() {
      return !shaderPackShadowTerrain.isFalse();
   }

   public static boolean isRenderShadowTranslucent() {
      return !shaderPackShadowTranslucent.isFalse();
   }

   public static boolean isRenderShadowEntities() {
      return !shaderPackShadowEntities.isFalse();
   }

   public static boolean isRenderShadowBlockEntities() {
      return !shaderPackShadowBlockEntities.isFalse();
   }

   public static boolean isUnderwaterOverlay() {
      return !shaderPackUnderwaterOverlay.isFalse();
   }

   public static boolean isSun() {
      return !shaderPackSun.isFalse();
   }

   public static boolean isMoon() {
      return !shaderPackMoon.isFalse();
   }

   public static boolean isVignette() {
      return !shaderPackVignette.isFalse();
   }

   public static boolean isRenderBackFace(RenderType blockLayerIn) {
      if (blockLayerIn == RenderTypes.SOLID) {
         return shaderPackBackFaceSolid.isTrue();
      } else if (blockLayerIn == RenderTypes.CUTOUT) {
         return shaderPackBackFaceCutout.isTrue();
      } else if (blockLayerIn == RenderTypes.CUTOUT_MIPPED) {
         return shaderPackBackFaceCutoutMipped.isTrue();
      } else {
         return blockLayerIn == RenderTypes.TRANSLUCENT ? shaderPackBackFaceTranslucent.isTrue() : false;
      }
   }

   public static boolean isRainDepth() {
      return shaderPackRainDepth.isTrue();
   }

   public static boolean isBeaconBeamDepth() {
      return shaderPackBeaconBeamDepth.isTrue();
   }

   public static boolean isSeparateAo() {
      return shaderPackSeparateAo.isTrue();
   }

   public static boolean isFrustumCulling() {
      return !shaderPackFrustumCulling.isFalse();
   }

   public static boolean isShadowCulling() {
      return !shaderPackShadowCulling.isDefault() ? shaderPackShadowCulling.isTrue() : !hasShadowGeometryShaders && !hasShadowInstancing;
   }

   public static boolean isParticlesBeforeDeferred() {
      return shaderPackParticlesBeforeDeferred.isTrue();
   }

   public static void init() {
      boolean firstInit;
      if (!isInitializedOnce) {
         isInitializedOnce = true;
         firstInit = true;
      } else {
         firstInit = false;
      }

      if (!isShaderPackInitialized) {
         checkGLError("Shaders.init pre");
         if (getShaderPackName() != null) {
         }

         dfbDrawBuffers.position(0).limit(8);
         sfbDrawBuffers.position(0).limit(8);
         usedColorBuffers = 4;
         usedDepthBuffers = 1;
         usedShadowColorBuffers = 0;
         usedShadowDepthBuffers = 0;
         usedColorAttachs = 1;
         usedDrawBuffers = 1;
         bindImageTextures = false;
         Arrays.fill(gbuffersFormat, 6408);
         Arrays.fill(gbuffersClear, true);
         Arrays.fill(gbuffersClearColor, null);
         Arrays.fill(shadowBuffersFormat, 6408);
         Arrays.fill(shadowBuffersClear, true);
         Arrays.fill(shadowBuffersClearColor, null);
         Arrays.fill(shadowHardwareFilteringEnabled, false);
         Arrays.fill(shadowMipmapEnabled, false);
         Arrays.fill(shadowFilterNearest, false);
         Arrays.fill(shadowColorMipmapEnabled, false);
         Arrays.fill(shadowColorFilterNearest, false);
         centerDepthSmoothEnabled = false;
         noiseTextureEnabled = false;
         sunPathRotation = 0.0F;
         shadowIntervalSize = 2.0F;
         shadowMapWidth = 1024;
         shadowMapHeight = 1024;
         spShadowMapWidth = 1024;
         spShadowMapHeight = 1024;
         shadowMapFOV = 90.0F;
         shadowMapHalfPlane = 160.0F;
         shadowMapIsOrtho = true;
         shadowDistanceRenderMul = -1.0F;
         aoLevel = -1.0F;
         useEntityAttrib = false;
         useMidTexCoordAttrib = false;
         useTangentAttrib = false;
         useVelocityAttrib = false;
         waterShadowEnabled = false;
         hasGeometryShaders = false;
         hasShadowGeometryShaders = false;
         hasShadowInstancing = false;
         updateBlockLightLevel();
         Smoother.resetValues();
         shaderUniforms.reset();
         if (customUniforms != null) {
            customUniforms.reset();
         }

         ShaderProfile activeProfile = ShaderUtils.detectProfile(shaderPackProfiles, shaderPackOptions, false);
         String worldPrefix = "";
         if (currentWorld != null) {
            int dimId = WorldUtils.getDimensionId(currentWorld.m_46472_());
            if (shaderPackDimensions.m_274455_(dimId)) {
               worldPrefix = "world" + dimId + "/";
            }
         }

         loadShaderPackDynamicProperties();
         int vaoPrev = GL43.glGetInteger(34229);
         int vaoTemp = GlStateManager._glGenVertexArrays();
         GlStateManager._glBindVertexArray(vaoTemp);

         for (int i = 0; i < ProgramsAll.length; i++) {
            Program p = ProgramsAll[i];
            p.resetId();
            p.resetConfiguration();
            if (p.getProgramStage() != ProgramStage.NONE) {
               String programName = p.getName();
               String programPath = worldPrefix + programName;
               boolean enabled = true;
               if (shaderPackProgramConditions.containsKey(programPath)) {
                  enabled = enabled && ((IExpressionBool)shaderPackProgramConditions.get(programPath)).eval();
               }

               if (activeProfile != null) {
                  enabled = enabled && !activeProfile.isProgramDisabled(programPath);
               }

               if (!enabled) {
                  SMCLog.info("Program disabled: " + programPath);
                  programName = "<disabled>";
                  programPath = worldPrefix + programName;
               }

               String programFullPath = "/shaders/" + programPath;
               String programFullPathVertex = programFullPath + ".vsh";
               String programFullPathGeometry = programFullPath + ".gsh";
               String programFullPathFragment = programFullPath + ".fsh";
               ComputeProgram[] cps = setupComputePrograms(p, "/shaders/", programPath, ".csh");
               p.setComputePrograms(cps);
               setupProgram(p, programFullPathVertex, programFullPathGeometry, programFullPathFragment);
               int pr = p.getId();
               if (pr > 0) {
                  SMCLog.info("Program loaded: " + programPath);
               }

               initDrawBuffers(p);
               initBlendStatesIndexed(p);
               updateToggleBuffers(p);
               updateProgramSize(p);
            }
         }

         GlStateManager._glBindVertexArray(vaoPrev);
         GlStateManager._glDeleteVertexArrays(vaoTemp);
         hasDeferredPrograms = ProgramUtils.hasActive(ProgramsDeferred);
         hasShadowcompPrograms = ProgramUtils.hasActive(ProgramsShadowcomp);
         hasPreparePrograms = ProgramUtils.hasActive(ProgramsPrepare);
         usedColorAttachs = usedColorBuffers;
         if (usedShadowDepthBuffers > 0 || usedShadowColorBuffers > 0) {
            hasShadowMap = true;
            usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, 1);
         }

         shouldSkipDefaultShadow = hasShadowMap;
         SMCLog.info("usedColorBuffers: " + usedColorBuffers);
         SMCLog.info("usedDepthBuffers: " + usedDepthBuffers);
         SMCLog.info("usedShadowColorBuffers: " + usedShadowColorBuffers);
         SMCLog.info("usedShadowDepthBuffers: " + usedShadowDepthBuffers);
         SMCLog.info("usedColorAttachs: " + usedColorAttachs);
         SMCLog.info("usedDrawBuffers: " + usedDrawBuffers);
         SMCLog.info("bindImageTextures: " + bindImageTextures);
         int maxDrawBuffers = GL43.glGetInteger(34852);
         if (usedDrawBuffers > maxDrawBuffers) {
            printChatAndLogError("[Shaders] Error: Not enough draw buffers, needed: " + usedDrawBuffers + ", available: " + maxDrawBuffers);
            usedDrawBuffers = maxDrawBuffers;
         }

         dfbDrawBuffers.position(0).limit(usedDrawBuffers);

         for (int ix = 0; ix < usedDrawBuffers; ix++) {
            dfbDrawBuffers.put(ix, 36064 + ix);
         }

         sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);

         for (int ix = 0; ix < usedShadowColorBuffers; ix++) {
            sfbDrawBuffers.put(ix, 36064 + ix);
         }

         for (int ix = 0; ix < ProgramsAll.length; ix++) {
            Program pi = ProgramsAll[ix];
            Program pn = pi;

            while (pn.getId() == 0 && pn.getProgramBackup() != pn) {
               pn = pn.getProgramBackup();
            }

            if (pn != pi && pi != ProgramShadow) {
               pi.copyFrom(pn);
            }
         }

         resize();
         resizeShadow();
         if (noiseTextureEnabled) {
            setupNoiseTexture();
         }

         if (defaultTexture == null) {
            defaultTexture = ShadersTex.createDefaultTexture();
         }

         PoseStack matrixStack = new PoseStack();
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(-90.0F));
         preCelestialRotate(matrixStack);
         postCelestialRotate(matrixStack);
         isShaderPackInitialized = true;
         loadEntityDataMap();
         resetDisplayLists();
         if (!firstInit) {
         }

         checkGLError("Shaders.init");
      }
   }

   private static void initDrawBuffers(Program p) {
      int maxDrawBuffers = GL43.glGetInteger(34852);
      Arrays.fill(p.getToggleColorTextures(), false);
      if (p == ProgramFinal) {
         p.setDrawBuffers(null);
      } else if (p.getId() == 0) {
         if (p == ProgramShadow) {
            p.setDrawBuffers(drawBuffersNone);
         } else {
            p.setDrawBuffers(drawBuffersColorAtt[0]);
         }
      } else {
         String[] drawBufSettings = p.getDrawBufSettings();
         if (drawBufSettings == null) {
            if (p != ProgramShadow && p != ProgramShadowSolid && p != ProgramShadowCutout) {
               p.setDrawBuffers(dfbDrawBuffers);
               usedDrawBuffers = Math.min(usedColorBuffers, maxDrawBuffers);
               Arrays.fill(p.getToggleColorTextures(), 0, usedColorBuffers, true);
            } else {
               p.setDrawBuffers(sfbDrawBuffers);
            }
         } else {
            DrawBuffers drawBuffers = p.getDrawBuffersCustom();
            int numDB = drawBufSettings.length;
            usedDrawBuffers = Math.max(usedDrawBuffers, numDB);
            numDB = Math.min(numDB, maxDrawBuffers);
            p.setDrawBuffers(drawBuffers);
            drawBuffers.limit(numDB);

            for (int i = 0; i < numDB; i++) {
               int drawBuffer = getDrawBuffer(p, drawBufSettings[i]);
               drawBuffers.put(i, drawBuffer);
            }

            String infoBuffers = drawBuffers.getInfo(false);
            String infoGlBuffers = drawBuffers.getInfo(true);
            if (!Config.equals(infoBuffers, infoGlBuffers)) {
               SMCLog.info("Draw buffers: " + infoBuffers + " -> " + infoGlBuffers);
            }
         }
      }
   }

   private static void initBlendStatesIndexed(Program p) {
      GlBlendState[] blendStatesColorIndexed = p.getBlendStatesColorIndexed();
      if (blendStatesColorIndexed != null) {
         for (int i = 0; i < blendStatesColorIndexed.length; i++) {
            GlBlendState blendState = blendStatesColorIndexed[i];
            if (blendState != null) {
               String bufferName = Integer.toHexString(i).toUpperCase();
               int colAtt = 36064 + i;
               int drawBufferIndex = p.getDrawBuffers().indexOf(colAtt);
               if (drawBufferIndex < 0) {
                  SMCLog.warning("Blend buffer not used in draw buffers: " + bufferName);
               } else {
                  p.setBlendStateIndexed(drawBufferIndex, blendState);
                  SMCLog.info("Blend buffer: " + bufferName);
               }
            }
         }
      }
   }

   private static int getDrawBuffer(Program p, String str) {
      int drawBuffer = 0;
      int ca = Config.parseInt(str, -1);
      if (p == ProgramShadow) {
         if (ca >= 0 && ca < 2) {
            drawBuffer = 36064 + ca;
            usedShadowColorBuffers = Math.max(usedShadowColorBuffers, ca + 1);
         }

         return drawBuffer;
      } else {
         if (ca >= 0 && ca < 16) {
            p.getToggleColorTextures()[ca] = true;
            drawBuffer = 36064 + ca;
            usedColorAttachs = Math.max(usedColorAttachs, ca + 1);
            usedColorBuffers = Math.max(usedColorBuffers, ca + 1);
         }

         return drawBuffer;
      }
   }

   private static void updateToggleBuffers(Program p) {
      boolean[] toggleBuffers = p.getToggleColorTextures();
      Boolean[] flipBuffers = p.getBuffersFlip();

      for (int i = 0; i < flipBuffers.length; i++) {
         Boolean flip = flipBuffers[i];
         if (flip != null) {
            toggleBuffers[i] = flip;
         }
      }
   }

   private static void updateProgramSize(Program p) {
      if (p.getProgramStage().isMainComposite()) {
         DynamicDimension drawSize = null;
         int countFixed = 0;
         int countMatching = 0;
         DrawBuffers db = p.getDrawBuffers();
         if (db != null) {
            for (int i = 0; i < db.limit(); i++) {
               int att = db.get(i);
               int ix = att - 36064;
               if (ix >= 0 && ix < colorBufferSizes.length) {
                  DynamicDimension dim = colorBufferSizes[ix];
                  if (dim != null) {
                     countFixed++;
                     if (drawSize == null) {
                        drawSize = dim;
                     }

                     if (dim.equals(drawSize)) {
                        countMatching++;
                     }
                  }
               }
            }

            if (countFixed != 0) {
               if (countMatching != db.limit()) {
                  SMCLog.severe("Program " + p.getName() + " draws to buffers with different sizes");
               } else {
                  p.setDrawSize(drawSize);
               }
            }
         }
      }
   }

   public static void resetDisplayLists() {
      SMCLog.info("Reset model renderers");
      countResetDisplayLists++;
      SMCLog.info("Reset world renderers");
      f_303183_.f_91060_.m_109818_();
   }

   private static void setupProgram(Program program, String vShaderPath, String gShaderPath, String fShaderPath) {
      checkGLError("pre setupProgram");
      progUseEntityAttrib = false;
      progUseMidTexCoordAttrib = false;
      progUseTangentAttrib = false;
      progUseVelocityAttrib = false;
      progUseMidBlockAttrib = false;
      int vShader = createVertShader(program, vShaderPath);
      int gShader = createGeomShader(program, gShaderPath);
      int fShader = createFragShader(program, fShaderPath);
      if (vShader != 0 || gShader != 0 || fShader != 0) {
         Config.sleep(1L);
         checkGLError("create");
         int programid = GL43.glCreateProgram();
         checkGLError("create");
         if (vShader != 0) {
            GL43.glAttachShader(programid, vShader);
            checkGLError("attach");
            if (program.getProgramStage() == ProgramStage.SHADOW && program.getCountInstances() > 1) {
               hasShadowInstancing = true;
            }
         }

         if (gShader != 0) {
            GL43.glAttachShader(programid, gShader);
            checkGLError("attach");
            if (progArbGeometryShader4) {
               ARBGeometryShader4.glProgramParameteriARB(programid, 36315, 4);
               ARBGeometryShader4.glProgramParameteriARB(programid, 36316, 5);
               ARBGeometryShader4.glProgramParameteriARB(programid, 36314, progMaxVerticesOut);
               checkGLError("arbGeometryShader4");
            }

            if (progExtGeometryShader4) {
               EXTGeometryShader4.glProgramParameteriEXT(programid, 36315, 4);
               EXTGeometryShader4.glProgramParameteriEXT(programid, 36316, 5);
               EXTGeometryShader4.glProgramParameteriEXT(programid, 36314, progMaxVerticesOut);
               checkGLError("extGeometryShader4");
            }

            hasGeometryShaders = true;
            if (program.getProgramStage() == ProgramStage.SHADOW) {
               hasShadowGeometryShaders = true;
            }
         }

         if (fShader != 0) {
            GL43.glAttachShader(programid, fShader);
            checkGLError("attach");

            for (int i = 0; i < 8; i++) {
               GL43.glBindFragDataLocation(programid, i, "outColor" + i);
            }

            checkGLError("bindDataLocation");
         }

         VertexFormat vertexFormat = DefaultVertexFormat.ENTITY_VANILLA;
         List<String> attributeNames = vertexFormat.m_166911_();

         for (int i = 0; i < attributeNames.size(); i++) {
            String name = (String)attributeNames.get(i);
            VertexFormatElement element = (VertexFormatElement)vertexFormat.getElementMapping().get(name);
            int attributeIndex = element.getAttributeIndex();
            if (attributeIndex >= 0) {
               String nameOf = "va" + name;
               Uniform.m_166710_(programid, attributeIndex, nameOf);
            }
         }

         if (progUseEntityAttrib) {
            GL43.glBindAttribLocation(programid, entityAttrib, "mc_Entity");
            checkGLError("mc_Entity");
         }

         if (progUseMidTexCoordAttrib) {
            GL43.glBindAttribLocation(programid, midTexCoordAttrib, "mc_midTexCoord");
            checkGLError("mc_midTexCoord");
         }

         if (progUseTangentAttrib) {
            GL43.glBindAttribLocation(programid, tangentAttrib, "at_tangent");
            checkGLError("at_tangent");
         }

         if (progUseVelocityAttrib) {
            GL43.glBindAttribLocation(programid, velocityAttrib, "at_velocity");
            checkGLError("at_velocity");
         }

         if (progUseMidBlockAttrib) {
            GL43.glBindAttribLocation(programid, midBlockAttrib, "at_midBlock");
            checkGLError("at_midBlock");
         }

         GL43.glLinkProgram(programid);
         if (GL43.glGetProgrami(programid, 35714) != 1) {
            SMCLog.severe("Error linking program: " + programid + " (" + program.getName() + ")");
         }

         printProgramLogInfo(programid, program.getName());
         if (vShader != 0) {
            GL43.glDetachShader(programid, vShader);
            GL43.glDeleteShader(vShader);
         }

         if (gShader != 0) {
            GL43.glDetachShader(programid, gShader);
            GL43.glDeleteShader(gShader);
         }

         if (fShader != 0) {
            GL43.glDetachShader(programid, fShader);
            GL43.glDeleteShader(fShader);
         }

         program.setId(programid);
         program.setRef(programid);
         useProgram(program);
         GL43.glValidateProgram(programid);
         useProgram(ProgramNone);
         printProgramLogInfo(programid, program.getName());
         int valid = GL43.glGetProgrami(programid, 35715);
         if (valid != 1) {
            String Q = "\"";
            printChatAndLogError("[Shaders] Error: Invalid program " + Q + program.getName() + Q);
            GL43.glDeleteProgram(programid);
            int var15 = false;
            program.resetId();
         }
      }
   }

   private static VertexFormat getVertexFormat(Program program) {
      if (isTerrain(program)) {
         return DefaultVertexFormat.f_85811_;
      } else if (program == ProgramSkyTextured) {
         return DefaultVertexFormat.f_85817_;
      } else {
         return program == ProgramClouds ? DefaultVertexFormat.f_85822_ : DefaultVertexFormat.f_85812_;
      }
   }

   public static boolean isTerrain(Program program) {
      if (program.getName().m_274455_("terrain")) {
         return true;
      } else {
         return program == ProgramWater ? true : program.getProgramStage() == ProgramStage.SHADOW;
      }
   }

   private static ComputeProgram[] setupComputePrograms(Program program, String prefixShaders, String programPath, String shaderExt) {
      if (program.getProgramStage() == ProgramStage.GBUFFERS) {
         return new ComputeProgram[0];
      } else {
         List<ComputeProgram> list = new ArrayList();
         int count = 27;

         for (int i = 0; i < count; i++) {
            String suffix = i > 0 ? "_" + (char)(97 + i - 1) : "";
            String computePath = programPath + suffix;
            String computeShaderFullPath = prefixShaders + computePath + shaderExt;
            ComputeProgram cp = new ComputeProgram(program.getName(), program.getProgramStage());
            setupComputeProgram(cp, computeShaderFullPath);
            if (cp.getId() > 0) {
               list.add(cp);
               SMCLog.info("Compute program loaded: " + computePath);
            }
         }

         return (ComputeProgram[])list.toArray(new ComputeProgram[list.size()]);
      }
   }

   private static void setupComputeProgram(ComputeProgram program, String cShaderPath) {
      checkGLError("pre setupProgram");
      int cShader = createCompShader(program, cShaderPath);
      checkGLError("create");
      if (cShader != 0) {
         int programid = GL43.glCreateProgram();
         checkGLError("create");
         if (cShader != 0) {
            GL43.glAttachShader(programid, cShader);
            checkGLError("attach");
         }

         GL43.glLinkProgram(programid);
         if (GL43.glGetProgrami(programid, 35714) != 1) {
            SMCLog.severe("Error linking program: " + programid + " (" + program.getName() + ")");
         }

         printProgramLogInfo(programid, program.getName());
         if (cShader != 0) {
            GL43.glDetachShader(programid, cShader);
            GL43.glDeleteShader(cShader);
         }

         program.setId(programid);
         program.setRef(programid);
         useComputeProgram(program);
         GL43.glValidateProgram(programid);
         GlStateManager._glUseProgram(0);
         printProgramLogInfo(programid, program.getName());
         int valid = GL43.glGetProgrami(programid, 35715);
         if (valid != 1) {
            String Q = "\"";
            printChatAndLogError("[Shaders] Error: Invalid program " + Q + program.getName() + Q);
            GL43.glDeleteProgram(programid);
            int var6 = false;
            program.resetId();
         }
      }
   }

   private static int createCompShader(ComputeProgram program, String filename) {
      InputStream is = shaderPack.getResourceAsStream(filename);
      if (is == null) {
         return 0;
      } else {
         int compShader = GL43.glCreateShader(37305);
         if (compShader == 0) {
            return 0;
         } else {
            ShaderOption[] activeOptions = getChangedOptions(shaderPackOptions);
            List<String> listFiles = new ArrayList();
            LineBuffer lines = new LineBuffer();
            if (is != null) {
               try {
                  lines = ShaderPackParser.loadShader(null, ShaderType.COMPUTE, is, filename, shaderPack, listFiles, activeOptions);
                  MacroState macroState = new MacroState();

                  for (String line : lines) {
                     if (macroState.processLine(line)) {
                        ShaderLine sl = ShaderParser.parseLine(line, ShaderType.COMPUTE);
                        if (sl != null) {
                           if (sl.isUniform()) {
                              String uniform = sl.getName();
                              int index;
                              if ((index = ShaderParser.getShadowDepthIndex(uniform)) >= 0) {
                                 usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, index + 1);
                              } else if ((index = ShaderParser.getShadowColorIndex(uniform)) >= 0) {
                                 usedShadowColorBuffers = Math.max(usedShadowColorBuffers, index + 1);
                              } else if ((index = ShaderParser.getShadowColorImageIndex(uniform)) >= 0) {
                                 usedShadowColorBuffers = Math.max(usedShadowColorBuffers, index + 1);
                                 bindImageTextures = true;
                              } else if ((index = ShaderParser.getDepthIndex(uniform)) >= 0) {
                                 usedDepthBuffers = Math.max(usedDepthBuffers, index + 1);
                              } else if ((index = ShaderParser.getColorIndex(uniform)) >= 0) {
                                 usedColorBuffers = Math.max(usedColorBuffers, index + 1);
                              } else if ((index = ShaderParser.getColorImageIndex(uniform)) >= 0) {
                                 usedColorBuffers = Math.max(usedColorBuffers, index + 1);
                                 bindImageTextures = true;
                              }
                           } else if (sl.isLayout("in")) {
                              Vec3i localSize = ShaderParser.parseLocalSize(sl.getValue());
                              if (localSize != null) {
                                 program.setLocalSize(localSize);
                              } else {
                                 SMCLog.severe("Invalid local size: " + line);
                              }
                           } else if (sl.isConstIVec3("workGroups")) {
                              Vec3i workGroups = sl.getValueIVec3();
                              if (workGroups != null) {
                                 program.setWorkGroups(workGroups);
                              } else {
                                 SMCLog.severe("Invalid workGroups: " + line);
                              }
                           } else if (sl.isConstVec2("workGroupsRender")) {
                              Vec2 workGroupsRender = sl.getValueVec2();
                              if (workGroupsRender != null) {
                                 program.setWorkGroupsRender(workGroupsRender);
                              } else {
                                 SMCLog.severe("Invalid workGroupsRender: " + line);
                              }
                           } else if (sl.isConstBoolSuffix("MipmapEnabled", true)) {
                              String name = StrUtils.removeSuffix(sl.getName(), "MipmapEnabled");
                              int bufferindex = getBufferIndex(name);
                              if (bufferindex >= 0) {
                                 int compositeMipmapSetting = program.getCompositeMipmapSetting();
                                 compositeMipmapSetting |= 1 << bufferindex;
                                 program.setCompositeMipmapSetting(compositeMipmapSetting);
                                 SMCLog.info("%s mipmap enabled", name);
                              }
                           }
                        }
                     }
                  }
               } catch (Exception var14) {
                  SMCLog.severe("Couldn't read " + filename + "!");
                  var14.printStackTrace();
                  GL43.glDeleteShader(compShader);
                  return 0;
               }
            }

            String compCode = lines.toString();
            if (saveFinalShaders) {
               saveShader(filename, compCode);
            }

            if (program.getLocalSize() == null) {
               SMCLog.severe("Missing local size: " + filename);
               GL43.glDeleteShader(compShader);
               return 0;
            } else {
               shaderSource(compShader, compCode);
               GL43.glCompileShader(compShader);
               if (GL43.glGetShaderi(compShader, 35713) != 1) {
                  SMCLog.severe("Error compiling compute shader: " + filename);
               }

               printShaderLogInfo(compShader, filename, listFiles);
               return compShader;
            }
         }
      }
   }

   private static int createVertShader(Program program, String filename) {
      InputStream is = shaderPack.getResourceAsStream(filename);
      if (is == null) {
         is = DefaultShaders.getResourceAsStream(filename);
      }

      if (is == null) {
         return 0;
      } else {
         int vertShader = GL43.glCreateShader(35633);
         if (vertShader == 0) {
            return 0;
         } else {
            ShaderOption[] activeOptions = getChangedOptions(shaderPackOptions);
            List<String> listFiles = new ArrayList();
            LineBuffer lines = new LineBuffer();
            if (is != null) {
               try {
                  lines = ShaderPackParser.loadShader(program, ShaderType.VERTEX, is, filename, shaderPack, listFiles, activeOptions);
                  MacroState macroState = new MacroState();

                  for (String line : lines) {
                     if (macroState.processLine(line)) {
                        ShaderLine sl = ShaderParser.parseLine(line, ShaderType.VERTEX);
                        if (sl != null) {
                           if (sl.isAttribute("mc_Entity")) {
                              useEntityAttrib = true;
                              progUseEntityAttrib = true;
                           } else if (sl.isAttribute("mc_midTexCoord")) {
                              useMidTexCoordAttrib = true;
                              progUseMidTexCoordAttrib = true;
                           } else if (sl.isAttribute("at_tangent")) {
                              useTangentAttrib = true;
                              progUseTangentAttrib = true;
                           } else if (sl.isAttribute("at_velocity")) {
                              useVelocityAttrib = true;
                              progUseVelocityAttrib = true;
                           } else if (sl.isAttribute("at_midBlock")) {
                              useMidBlockAttrib = true;
                              progUseMidBlockAttrib = true;
                           }

                           if (sl.isConstInt("countInstances")) {
                              program.setCountInstances(sl.getValueInt());
                              SMCLog.info("countInstances: " + program.getCountInstances());
                           }
                        }
                     }
                  }
               } catch (Exception var11) {
                  SMCLog.severe("Couldn't read " + filename + "!");
                  var11.printStackTrace();
                  GL43.glDeleteShader(vertShader);
                  return 0;
               }
            }

            String vertexCode = lines.toString();
            if (saveFinalShaders) {
               saveShader(filename, vertexCode);
            }

            shaderSource(vertShader, vertexCode);
            GL43.glCompileShader(vertShader);
            if (GL43.glGetShaderi(vertShader, 35713) != 1) {
               SMCLog.severe("Error compiling vertex shader: " + filename);
            }

            printShaderLogInfo(vertShader, filename, listFiles);
            return vertShader;
         }
      }
   }

   private static int createGeomShader(Program program, String filename) {
      InputStream is = shaderPack.getResourceAsStream(filename);
      if (is == null) {
         return 0;
      } else {
         int geomShader = GL43.glCreateShader(36313);
         if (geomShader == 0) {
            return 0;
         } else {
            ShaderOption[] activeOptions = getChangedOptions(shaderPackOptions);
            List<String> listFiles = new ArrayList();
            progArbGeometryShader4 = false;
            progExtGeometryShader4 = false;
            progMaxVerticesOut = 3;
            LineBuffer lines = new LineBuffer();
            if (is != null) {
               try {
                  lines = ShaderPackParser.loadShader(program, ShaderType.GEOMETRY, is, filename, shaderPack, listFiles, activeOptions);
                  MacroState macroState = new MacroState();

                  for (String line : lines) {
                     if (macroState.processLine(line)) {
                        ShaderLine sl = ShaderParser.parseLine(line, ShaderType.GEOMETRY);
                        if (sl != null) {
                           if (sl.isExtension("GL_ARB_geometry_shader4")) {
                              String val = Config.normalize(sl.getValue());
                              if (val.equals("enable") || val.equals("require") || val.equals("warn")) {
                                 progArbGeometryShader4 = true;
                              }
                           }

                           if (sl.isExtension("GL_EXT_geometry_shader4")) {
                              String val = Config.normalize(sl.getValue());
                              if (val.equals("enable") || val.equals("require") || val.equals("warn")) {
                                 progExtGeometryShader4 = true;
                              }
                           }

                           if (sl.isConstInt("maxVerticesOut")) {
                              progMaxVerticesOut = sl.getValueInt();
                           }
                        }
                     }
                  }
               } catch (Exception var12) {
                  SMCLog.severe("Couldn't read " + filename + "!");
                  var12.printStackTrace();
                  GL43.glDeleteShader(geomShader);
                  return 0;
               }
            }

            String geomCode = lines.toString();
            if (saveFinalShaders) {
               saveShader(filename, geomCode);
            }

            shaderSource(geomShader, geomCode);
            GL43.glCompileShader(geomShader);
            if (GL43.glGetShaderi(geomShader, 35713) != 1) {
               SMCLog.severe("Error compiling geometry shader: " + filename);
            }

            printShaderLogInfo(geomShader, filename, listFiles);
            return geomShader;
         }
      }
   }

   private static int createFragShader(Program program, String filename) {
      InputStream is = shaderPack.getResourceAsStream(filename);
      if (is == null) {
         is = DefaultShaders.getResourceAsStream(filename);
      }

      if (is == null) {
         return 0;
      } else {
         int fragShader = GL43.glCreateShader(35632);
         if (fragShader == 0) {
            return 0;
         } else {
            ShaderOption[] activeOptions = getChangedOptions(shaderPackOptions);
            List<String> listFiles = new ArrayList();
            LineBuffer lines = new LineBuffer();
            if (is != null) {
               try {
                  lines = ShaderPackParser.loadShader(program, ShaderType.FRAGMENT, is, filename, shaderPack, listFiles, activeOptions);
                  MacroState macroState = new MacroState();

                  for (String line : lines) {
                     if (macroState.processLine(line)) {
                        ShaderLine sl = ShaderParser.parseLine(line, ShaderType.FRAGMENT);
                        if (sl != null) {
                           if (sl.isUniform()) {
                              String uniform = sl.getName();
                              int index;
                              if ((index = ShaderParser.getShadowDepthIndex(uniform)) >= 0) {
                                 usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, index + 1);
                              } else if ((index = ShaderParser.getShadowColorIndex(uniform)) >= 0) {
                                 usedShadowColorBuffers = Math.max(usedShadowColorBuffers, index + 1);
                              } else if ((index = ShaderParser.getShadowColorImageIndex(uniform)) >= 0) {
                                 usedShadowColorBuffers = Math.max(usedShadowColorBuffers, index + 1);
                                 bindImageTextures = true;
                              } else if ((index = ShaderParser.getDepthIndex(uniform)) >= 0) {
                                 usedDepthBuffers = Math.max(usedDepthBuffers, index + 1);
                              } else if (uniform.equals("gdepth") && gbuffersFormat[1] == 6408) {
                                 gbuffersFormat[1] = 34836;
                              } else if ((index = ShaderParser.getColorIndex(uniform)) >= 0) {
                                 usedColorBuffers = Math.max(usedColorBuffers, index + 1);
                              } else if ((index = ShaderParser.getColorImageIndex(uniform)) >= 0) {
                                 usedColorBuffers = Math.max(usedColorBuffers, index + 1);
                                 bindImageTextures = true;
                              } else if (uniform.equals("centerDepthSmooth")) {
                                 centerDepthSmoothEnabled = true;
                              }
                           } else if (sl.isConstInt("shadowMapResolution") || sl.isProperty("SHADOWRES")) {
                              spShadowMapWidth = spShadowMapHeight = sl.getValueInt();
                              shadowMapWidth = shadowMapHeight = Math.round((float)spShadowMapWidth * configShadowResMul);
                              SMCLog.info("Shadow map resolution: " + spShadowMapWidth);
                           } else if (sl.isConstFloat("shadowMapFov") || sl.isProperty("SHADOWFOV")) {
                              shadowMapFOV = sl.getValueFloat();
                              shadowMapIsOrtho = false;
                              SMCLog.info("Shadow map field of view: " + shadowMapFOV);
                           } else if (sl.isConstFloat("shadowDistance") || sl.isProperty("SHADOWHPL")) {
                              shadowMapHalfPlane = sl.getValueFloat();
                              shadowMapIsOrtho = true;
                              SMCLog.info("Shadow map distance: " + shadowMapHalfPlane);
                           } else if (sl.isConstFloat("shadowDistanceRenderMul")) {
                              shadowDistanceRenderMul = sl.getValueFloat();
                              SMCLog.info("Shadow distance render mul: " + shadowDistanceRenderMul);
                           } else if (sl.isConstFloat("shadowIntervalSize")) {
                              shadowIntervalSize = sl.getValueFloat();
                              SMCLog.info("Shadow map interval size: " + shadowIntervalSize);
                           } else if (sl.isConstBool("generateShadowMipmap", true)) {
                              Arrays.fill(shadowMipmapEnabled, true);
                              SMCLog.info("Generate shadow mipmap");
                           } else if (sl.isConstBool("generateShadowColorMipmap", true)) {
                              Arrays.fill(shadowColorMipmapEnabled, true);
                              SMCLog.info("Generate shadow color mipmap");
                           } else if (sl.isConstBool("shadowHardwareFiltering", true)) {
                              Arrays.fill(shadowHardwareFilteringEnabled, true);
                              SMCLog.info("Hardware shadow filtering enabled.");
                           } else if (sl.isConstBool("shadowHardwareFiltering0", true)) {
                              shadowHardwareFilteringEnabled[0] = true;
                              SMCLog.info("shadowHardwareFiltering0");
                           } else if (sl.isConstBool("shadowHardwareFiltering1", true)) {
                              shadowHardwareFilteringEnabled[1] = true;
                              SMCLog.info("shadowHardwareFiltering1");
                           } else if (sl.isConstBool("shadowtex0Mipmap", "shadowtexMipmap", true)) {
                              shadowMipmapEnabled[0] = true;
                              SMCLog.info("shadowtex0Mipmap");
                           } else if (sl.isConstBool("shadowtex1Mipmap", true)) {
                              shadowMipmapEnabled[1] = true;
                              SMCLog.info("shadowtex1Mipmap");
                           } else if (sl.isConstBool("shadowcolor0Mipmap", "shadowColor0Mipmap", true)) {
                              shadowColorMipmapEnabled[0] = true;
                              SMCLog.info("shadowcolor0Mipmap");
                           } else if (sl.isConstBool("shadowcolor1Mipmap", "shadowColor1Mipmap", true)) {
                              shadowColorMipmapEnabled[1] = true;
                              SMCLog.info("shadowcolor1Mipmap");
                           } else if (sl.isConstBool("shadowtex0Nearest", "shadowtexNearest", "shadow0MinMagNearest", true)) {
                              shadowFilterNearest[0] = true;
                              SMCLog.info("shadowtex0Nearest");
                           } else if (sl.isConstBool("shadowtex1Nearest", "shadow1MinMagNearest", true)) {
                              shadowFilterNearest[1] = true;
                              SMCLog.info("shadowtex1Nearest");
                           } else if (sl.isConstBool("shadowcolor0Nearest", "shadowColor0Nearest", "shadowColor0MinMagNearest", true)) {
                              shadowColorFilterNearest[0] = true;
                              SMCLog.info("shadowcolor0Nearest");
                           } else if (sl.isConstBool("shadowcolor1Nearest", "shadowColor1Nearest", "shadowColor1MinMagNearest", true)) {
                              shadowColorFilterNearest[1] = true;
                              SMCLog.info("shadowcolor1Nearest");
                           } else if (sl.isConstFloat("wetnessHalflife") || sl.isProperty("WETNESSHL")) {
                              wetnessHalfLife = sl.getValueFloat();
                              SMCLog.info("Wetness halflife: " + wetnessHalfLife);
                           } else if (sl.isConstFloat("drynessHalflife") || sl.isProperty("DRYNESSHL")) {
                              drynessHalfLife = sl.getValueFloat();
                              SMCLog.info("Dryness halflife: " + drynessHalfLife);
                           } else if (sl.isConstFloat("eyeBrightnessHalflife")) {
                              eyeBrightnessHalflife = sl.getValueFloat();
                              SMCLog.info("Eye brightness halflife: " + eyeBrightnessHalflife);
                           } else if (sl.isConstFloat("centerDepthHalflife")) {
                              centerDepthSmoothHalflife = sl.getValueFloat();
                              SMCLog.info("Center depth halflife: " + centerDepthSmoothHalflife);
                           } else if (sl.isConstFloat("sunPathRotation")) {
                              sunPathRotation = sl.getValueFloat();
                              SMCLog.info("Sun path rotation: " + sunPathRotation);
                           } else if (sl.isConstFloat("ambientOcclusionLevel")) {
                              aoLevel = Config.limit(sl.getValueFloat(), 0.0F, 1.0F);
                              SMCLog.info("AO Level: " + aoLevel);
                           } else if (sl.isConstInt("superSamplingLevel")) {
                              int ssaa = sl.getValueInt();
                              if (ssaa > 1) {
                                 SMCLog.info("Super sampling level: " + ssaa + "x");
                                 superSamplingLevel = ssaa;
                              } else {
                                 superSamplingLevel = 1;
                              }
                           } else if (sl.isConstInt("noiseTextureResolution")) {
                              noiseTextureResolution = sl.getValueInt();
                              noiseTextureEnabled = true;
                              SMCLog.info("Noise texture enabled");
                              SMCLog.info("Noise texture resolution: " + noiseTextureResolution);
                           } else if (sl.isConstIntSuffix("Format")) {
                              String name = StrUtils.removeSuffix(sl.getName(), "Format");
                              String value = sl.getValue();
                              int format = getTextureFormatFromString(value);
                              if (format != 0) {
                                 int bufferindex = getBufferIndex(name);
                                 if (bufferindex >= 0) {
                                    gbuffersFormat[bufferindex] = format;
                                    SMCLog.info("%s format: %s", name, value);
                                 }

                                 int shadowColorIndex = ShaderParser.getShadowColorIndex(name);
                                 if (shadowColorIndex >= 0) {
                                    shadowBuffersFormat[shadowColorIndex] = format;
                                    SMCLog.info("%s format: %s", name, value);
                                 }
                              }
                           } else if (sl.isConstBoolSuffix("Clear", false)) {
                              if (program.getProgramStage().isAnyComposite()) {
                                 String name = StrUtils.removeSuffix(sl.getName(), "Clear");
                                 int bufferindexx = getBufferIndex(name);
                                 if (bufferindexx >= 0) {
                                    gbuffersClear[bufferindexx] = false;
                                    SMCLog.info("%s clear disabled", name);
                                 }

                                 int shadowColorIndex = ShaderParser.getShadowColorIndex(name);
                                 if (shadowColorIndex >= 0) {
                                    shadowBuffersClear[shadowColorIndex] = false;
                                    SMCLog.info("%s clear disabled", name);
                                 }
                              }
                           } else if (sl.isConstVec4Suffix("ClearColor")) {
                              if (program.getProgramStage().isAnyComposite()) {
                                 String namex = StrUtils.removeSuffix(sl.getName(), "ClearColor");
                                 Vector4f col = sl.getValueVec4();
                                 if (col != null) {
                                    int bufferindexxx = getBufferIndex(namex);
                                    if (bufferindexxx >= 0) {
                                       gbuffersClearColor[bufferindexxx] = col;
                                       SMCLog.info("%s clear color: %s %s %s %s", namex, col.m_305649_(), col.m_306225_(), col.m_240700_(), col.m_245239_());
                                    }

                                    int shadowColorIndex = ShaderParser.getShadowColorIndex(namex);
                                    if (shadowColorIndex >= 0) {
                                       shadowBuffersClearColor[shadowColorIndex] = col;
                                       SMCLog.info("%s clear color: %s %s %s %s", namex, col.m_305649_(), col.m_306225_(), col.m_240700_(), col.m_245239_());
                                    }
                                 } else {
                                    SMCLog.warning("Invalid color value: " + sl.getValue());
                                 }
                              }
                           } else if (sl.isProperty("GAUX4FORMAT", "RGBA32F")) {
                              gbuffersFormat[7] = 34836;
                              SMCLog.info("gaux4 format : RGB32AF");
                           } else if (sl.isProperty("GAUX4FORMAT", "RGB32F")) {
                              gbuffersFormat[7] = 34837;
                              SMCLog.info("gaux4 format : RGB32F");
                           } else if (sl.isProperty("GAUX4FORMAT", "RGB16")) {
                              gbuffersFormat[7] = 32852;
                              SMCLog.info("gaux4 format : RGB16");
                           } else if (sl.isConstBoolSuffix("MipmapEnabled", true)) {
                              if (program.getProgramStage().isAnyComposite()) {
                                 String namex = StrUtils.removeSuffix(sl.getName(), "MipmapEnabled");
                                 int bufferindexxxx = getBufferIndex(namex);
                                 if (bufferindexxxx >= 0) {
                                    int compositeMipmapSetting = program.getCompositeMipmapSetting();
                                    compositeMipmapSetting |= 1 << bufferindexxxx;
                                    program.setCompositeMipmapSetting(compositeMipmapSetting);
                                    SMCLog.info("%s mipmap enabled", namex);
                                 }
                              }
                           } else if (sl.isProperty("DRAWBUFFERS")) {
                              String val = sl.getValue();
                              String[] dbs = ShaderParser.parseDrawBuffers(val);
                              if (dbs != null) {
                                 program.setDrawBufSettings(dbs);
                              } else {
                                 SMCLog.warning("Invalid draw buffers: " + val);
                              }
                           } else if (sl.isProperty("RENDERTARGETS")) {
                              String val = sl.getValue();
                              String[] dbs = ShaderParser.parseRenderTargets(val);
                              if (dbs != null) {
                                 program.setDrawBufSettings(dbs);
                              } else {
                                 SMCLog.warning("Invalid render targets: " + val);
                              }
                           }
                        }
                     }
                  }
               } catch (Exception var16) {
                  SMCLog.severe("Couldn't read " + filename + "!");
                  var16.printStackTrace();
                  GL43.glDeleteShader(fragShader);
                  return 0;
               }
            }

            String fragCode = lines.toString();
            if (saveFinalShaders) {
               saveShader(filename, fragCode);
            }

            shaderSource(fragShader, fragCode);
            GL43.glCompileShader(fragShader);
            if (GL43.glGetShaderi(fragShader, 35713) != 1) {
               SMCLog.severe("Error compiling fragment shader: " + filename);
            }

            printShaderLogInfo(fragShader, filename, listFiles);
            return fragShader;
         }
      }
   }

   private static void shaderSource(int shader, String code) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         ByteBuffer sourceBuffer = MemoryUtil.memUTF8(code, true);
         PointerBuffer pointers = stack.mallocPointer(1);
         pointers.put(sourceBuffer);
         GL43.nglShaderSource(shader, 1, pointers.address0(), 0L);
         APIUtil.apiArrayFree(pointers.address0(), 1);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void saveShader(String filename, String code) {
      try {
         File file = new File(shaderPacksDir, "debug/" + filename);
         file.getParentFile().mkdirs();
         Config.writeFile(file, code);
      } catch (IOException var3) {
         Config.warn("Error saving: " + filename);
         var3.printStackTrace();
      }
   }

   private static void clearDirectory(File dir) {
      if (dir.exists()) {
         if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
               for (int i = 0; i < files.length; i++) {
                  File file = files[i];
                  if (file.isDirectory()) {
                     clearDirectory(file);
                  }

                  file.delete();
               }
            }
         }
      }
   }

   private static boolean printProgramLogInfo(int obj, String name) {
      IntBuffer iVal = BufferUtils.createIntBuffer(1);
      GL43.glGetProgramiv(obj, 35716, iVal);
      int length = iVal.get();
      if (length > 1) {
         ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
         iVal.flip();
         GL43.glGetProgramInfoLog(obj, iVal, infoLog);
         byte[] infoBytes = new byte[length];
         infoLog.get(infoBytes);
         if (infoBytes[length - 1] == 0) {
            infoBytes[length - 1] = 10;
         }

         String out = new String(infoBytes, StandardCharsets.US_ASCII);
         out = StrUtils.trim(out, " \n\r\t");
         SMCLog.info("Program info log: " + name + "\n" + out);
         return false;
      } else {
         return true;
      }
   }

   private static boolean printShaderLogInfo(int shader, String name, List<String> listFiles) {
      IntBuffer iVal = BufferUtils.createIntBuffer(1);
      int length = GL43.glGetShaderi(shader, 35716);
      if (length <= 1) {
         return true;
      } else {
         for (int i = 0; i < listFiles.size(); i++) {
            String path = (String)listFiles.get(i);
            SMCLog.info("File: " + (i + 1) + " = " + path);
         }

         String log = GL43.glGetShaderInfoLog(shader, length);
         log = StrUtils.trim(log, " \n\r\t");
         SMCLog.info("Shader info log: " + name + "\n" + log);
         return false;
      }
   }

   public static void useProgram(Program program) {
      checkGLError("pre-useProgram");
      if (isShadowPass) {
         program = ProgramShadow;
      }

      if (activeProgram != program) {
         flushRenderBuffers();
         updateAlphaBlend(activeProgram, program);
         if (glDebugGroups && glDebugGroupProgram) {
            KHRDebug.glPopDebugGroup();
         }

         activeProgram = program;
         if (glDebugGroups) {
            KHRDebug.glPushDebugGroup(33354, 0, activeProgram.getRealProgramName());
            glDebugGroupProgram = true;
         }

         int programID = program.getId();
         activeProgramID = programID;
         GlStateManager._glUseProgram(programID);
         if (checkGLError("useProgram") != 0) {
            program.setId(0);
            programID = program.getId();
            activeProgramID = programID;
            GlStateManager._glUseProgram(programID);
         }

         shaderUniforms.setProgram(programID);
         if (customUniforms != null) {
            customUniforms.setProgram(programID);
         }

         if (programID == 0) {
            ShaderInstance.useVanillaProgram();
         } else {
            DrawBuffers drawBuffers = program.getDrawBuffers();
            if (isRenderingDfb) {
               GlState.setDrawBuffers(drawBuffers);
            }

            setProgramUniforms(program.getProgramStage());
            setImageUniforms();
            checkGLError("end useProgram");
         }
      }
   }

   private static void setProgramUniforms(ProgramStage programStage) {
      switch (<unrepresentable>.$SwitchMap$net$optifine$shaders$ProgramStage[programStage.ordinal()]) {
         case 1:
            setProgramUniform1i(uniform_gtexture, 0);
            setProgramUniform1i(uniform_lightmap, 2);
            setProgramUniform1i(uniform_normals, 1);
            setProgramUniform1i(uniform_specular, 3);
            setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
            setProgramUniform1i(uniform_watershadow, 4);
            setProgramUniform1i(uniform_shadowtex0, 4);
            setProgramUniform1i(uniform_shadowtex1, 5);
            setProgramUniform1i(uniform_depthtex0, 6);
            if (customTexturesGbuffers != null || hasDeferredPrograms) {
               setProgramUniform1i(uniform_gaux1, 7);
               setProgramUniform1i(uniform_gaux2, 8);
               setProgramUniform1i(uniform_gaux3, 9);
               setProgramUniform1i(uniform_gaux4, 10);
               setProgramUniform1i(uniform_colortex4, 7);
               setProgramUniform1i(uniform_colortex5, 8);
               setProgramUniform1i(uniform_colortex6, 9);
               setProgramUniform1i(uniform_colortex7, 10);
               if (usedColorBuffers > 8) {
                  setProgramUniform1i(uniform_colortex8, 16);
                  setProgramUniform1i(uniform_colortex9, 17);
                  setProgramUniform1i(uniform_colortex10, 18);
                  setProgramUniform1i(uniform_colortex11, 19);
                  setProgramUniform1i(uniform_colortex12, 20);
                  setProgramUniform1i(uniform_colortex13, 21);
                  setProgramUniform1i(uniform_colortex14, 22);
                  setProgramUniform1i(uniform_colortex15, 23);
               }
            }

            setProgramUniform1i(uniform_depthtex1, 11);
            setProgramUniform1i(uniform_shadowcolor, 13);
            setProgramUniform1i(uniform_shadowcolor0, 13);
            setProgramUniform1i(uniform_shadowcolor1, 14);
            setProgramUniform1i(uniform_noisetex, 15);
            break;
         case 2:
         case 3:
         case 4:
         case 5:
            setProgramUniform1i(uniform_gcolor, 0);
            setProgramUniform1i(uniform_gdepth, 1);
            setProgramUniform1i(uniform_gnormal, 2);
            setProgramUniform1i(uniform_composite, 3);
            setProgramUniform1i(uniform_gaux1, 7);
            setProgramUniform1i(uniform_gaux2, 8);
            setProgramUniform1i(uniform_gaux3, 9);
            setProgramUniform1i(uniform_gaux4, 10);
            setProgramUniform1i(uniform_colortex0, 0);
            setProgramUniform1i(uniform_colortex1, 1);
            setProgramUniform1i(uniform_colortex2, 2);
            setProgramUniform1i(uniform_colortex3, 3);
            setProgramUniform1i(uniform_colortex4, 7);
            setProgramUniform1i(uniform_colortex5, 8);
            setProgramUniform1i(uniform_colortex6, 9);
            setProgramUniform1i(uniform_colortex7, 10);
            if (usedColorBuffers > 8) {
               setProgramUniform1i(uniform_colortex8, 16);
               setProgramUniform1i(uniform_colortex9, 17);
               setProgramUniform1i(uniform_colortex10, 18);
               setProgramUniform1i(uniform_colortex11, 19);
               setProgramUniform1i(uniform_colortex12, 20);
               setProgramUniform1i(uniform_colortex13, 21);
               setProgramUniform1i(uniform_colortex14, 22);
               setProgramUniform1i(uniform_colortex15, 23);
            }

            setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
            setProgramUniform1i(uniform_watershadow, 4);
            setProgramUniform1i(uniform_shadowtex0, 4);
            setProgramUniform1i(uniform_shadowtex1, 5);
            setProgramUniform1i(uniform_gdepthtex, 6);
            setProgramUniform1i(uniform_depthtex0, 6);
            setProgramUniform1i(uniform_depthtex1, 11);
            setProgramUniform1i(uniform_depthtex2, 12);
            setProgramUniform1i(uniform_shadowcolor, 13);
            setProgramUniform1i(uniform_shadowcolor0, 13);
            setProgramUniform1i(uniform_shadowcolor1, 14);
            setProgramUniform1i(uniform_noisetex, 15);
            break;
         case 6:
            setProgramUniform1i(uniform_tex, 0);
            setProgramUniform1i(uniform_gtexture, 0);
            setProgramUniform1i(uniform_lightmap, 2);
            setProgramUniform1i(uniform_normals, 1);
            setProgramUniform1i(uniform_specular, 3);
            setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
            setProgramUniform1i(uniform_watershadow, 4);
            setProgramUniform1i(uniform_shadowtex0, 4);
            setProgramUniform1i(uniform_shadowtex1, 5);
            if (customTexturesGbuffers != null) {
               setProgramUniform1i(uniform_gaux1, 7);
               setProgramUniform1i(uniform_gaux2, 8);
               setProgramUniform1i(uniform_gaux3, 9);
               setProgramUniform1i(uniform_gaux4, 10);
               setProgramUniform1i(uniform_colortex4, 7);
               setProgramUniform1i(uniform_colortex5, 8);
               setProgramUniform1i(uniform_colortex6, 9);
               setProgramUniform1i(uniform_colortex7, 10);
               if (usedColorBuffers > 8) {
                  setProgramUniform1i(uniform_colortex8, 16);
                  setProgramUniform1i(uniform_colortex9, 17);
                  setProgramUniform1i(uniform_colortex10, 18);
                  setProgramUniform1i(uniform_colortex11, 19);
                  setProgramUniform1i(uniform_colortex12, 20);
                  setProgramUniform1i(uniform_colortex13, 21);
                  setProgramUniform1i(uniform_colortex14, 22);
                  setProgramUniform1i(uniform_colortex15, 23);
               }
            }

            setProgramUniform1i(uniform_shadowcolor, 13);
            setProgramUniform1i(uniform_shadowcolor0, 13);
            setProgramUniform1i(uniform_shadowcolor1, 14);
            setProgramUniform1i(uniform_noisetex, 15);
      }

      ItemStack stack = f_303183_.f_91074_ != null ? f_303183_.f_91074_.m_21205_() : null;
      Item item = stack != null ? stack.m_41720_() : null;
      int itemID = -1;
      Block block = null;
      if (item != null) {
         itemID = BuiltInRegistries.f_257033_.m_7447_(item);
         if (item instanceof BlockItem) {
            block = ((BlockItem)item).m_40614_();
         }

         itemID = ItemAliases.getItemAliasId(itemID);
      }

      int blockLight = block != null ? block.m_49966_().m_60791_() : 0;
      ItemStack stack2 = f_303183_.f_91074_ != null ? f_303183_.f_91074_.m_21206_() : null;
      Item item2 = stack2 != null ? stack2.m_41720_() : null;
      int itemID2 = -1;
      Block block2 = null;
      if (item2 != null) {
         itemID2 = BuiltInRegistries.f_257033_.m_7447_(item2);
         if (item2 instanceof BlockItem) {
            block2 = ((BlockItem)item2).m_40614_();
         }

         itemID2 = ItemAliases.getItemAliasId(itemID2);
      }

      int blockLight2 = block2 != null ? block2.m_49966_().m_60791_() : 0;
      if (isOldHandLight() && blockLight2 > blockLight) {
         itemID = itemID2;
         blockLight = blockLight2;
      }

      float playerMood = f_303183_.f_91074_ != null ? f_303183_.f_91074_.m_108762_() : 0.0F;
      setProgramUniform1i(uniform_heldItemId, itemID);
      setProgramUniform1i(uniform_heldBlockLightValue, blockLight);
      setProgramUniform1i(uniform_heldItemId2, itemID2);
      setProgramUniform1i(uniform_heldBlockLightValue2, blockLight2);
      setProgramUniform1i(uniform_fogMode, fogEnabled && fogAllowed ? fogMode : 0);
      setProgramUniform1i(uniform_fogShape, fogShape);
      setProgramUniform1f(uniform_fogDensity, fogEnabled && fogAllowed ? fogDensity : 0.0F);
      setProgramUniform1f(uniform_fogStart, fogEnabled && fogAllowed ? fogStart : 1.7014117E38F);
      setProgramUniform1f(uniform_fogEnd, fogEnabled && fogAllowed ? fogEnd : Float.MAX_VALUE);
      setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
      setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
      setProgramUniform1i(uniform_worldTime, (int)(worldTime % 24000L));
      setProgramUniform1i(uniform_worldDay, (int)(worldTime / 24000L));
      setProgramUniform1i(uniform_moonPhase, moonPhase);
      setProgramUniform1i(uniform_frameCounter, frameCounter);
      setProgramUniform1f(uniform_frameTime, frameTime);
      setProgramUniform1f(uniform_frameTimeCounter, frameTimeCounter);
      setProgramUniform1f(uniform_sunAngle, sunAngle);
      setProgramUniform1f(uniform_shadowAngle, shadowAngle);
      setProgramUniform1f(uniform_rainStrength, rainStrength);
      setProgramUniform1f(uniform_aspectRatio, (float)renderWidth / (float)renderHeight);
      setProgramUniform1f(uniform_viewWidth, (float)renderWidth);
      setProgramUniform1f(uniform_viewHeight, (float)renderHeight);
      setProgramUniform1f(uniform_near, 0.05F);
      setProgramUniform1f(uniform_far, (float)(f_303183_.f_91066_.m_231984_().m_231551_() * 16));
      setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
      setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
      setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
      setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
      setProgramUniform3f(uniform_previousCameraPosition, (float)previousCameraPositionX, (float)previousCameraPositionY, (float)previousCameraPositionZ);
      setProgramUniform3f(uniform_cameraPosition, (float)cameraPositionX, (float)cameraPositionY, (float)cameraPositionZ);
      setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
      setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
      setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
      setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
      setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
      setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
      if (hasShadowMap) {
         setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
         setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
         setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
         setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
      }

      setProgramUniform1f(uniform_wetness, wetness);
      setProgramUniform1f(uniform_eyeAltitude, eyePosY);
      setProgramUniform2i(uniform_eyeBrightness, eyeBrightness & 65535, eyeBrightness >> 16);
      setProgramUniform2i(uniform_eyeBrightnessSmooth, Math.round(eyeBrightnessFadeX), Math.round(eyeBrightnessFadeY));
      setProgramUniform2i(uniform_terrainTextureSize, terrainTextureSize[0], terrainTextureSize[1]);
      setProgramUniform1i(uniform_terrainIconSize, terrainIconSize);
      setProgramUniform1i(uniform_isEyeInWater, isEyeInWater);
      setProgramUniform1f(uniform_nightVision, nightVision);
      setProgramUniform1f(uniform_blindness, blindness);
      setProgramUniform1f(uniform_screenBrightness, (float)f_303183_.f_91066_.m_231927_().m_231551_().doubleValue());
      setProgramUniform1i(uniform_hideGUI, f_303183_.f_91066_.f_92062_ ? 1 : 0);
      setProgramUniform1f(uniform_centerDepthSmooth, centerDepthSmooth);
      setProgramUniform2i(uniform_atlasSize, atlasSizeX, atlasSizeY);
      setProgramUniform1f(uniform_playerMood, playerMood);
      setProgramUniform1i(uniform_renderStage, renderStage.ordinal());
      setProgramUniform1i(uniform_bossBattle, bossBattle);
      setProgramUniform1f(uniform_darknessFactor, darknessFactor);
      setProgramUniform1f(uniform_darknessLightFactor, darknessLightFactor);
      GlStateManager.applyAlphaTest();
      if (customUniforms != null) {
         customUniforms.m_252999_();
      }
   }

   private static void setImageUniforms() {
      if (bindImageTextures) {
         uniform_colorimg0.setValue(colorImageUnit[0]);
         uniform_colorimg1.setValue(colorImageUnit[1]);
         uniform_colorimg2.setValue(colorImageUnit[2]);
         uniform_colorimg3.setValue(colorImageUnit[3]);
         uniform_colorimg4.setValue(colorImageUnit[4]);
         uniform_colorimg5.setValue(colorImageUnit[5]);
         uniform_shadowcolorimg0.setValue(shadowColorImageUnit[0]);
         uniform_shadowcolorimg1.setValue(shadowColorImageUnit[1]);
      }
   }

   private static void updateAlphaBlend(Program programOld, Program programNew) {
      if (programOld.getAlphaState() != null) {
         GlStateManager.unlockAlpha();
      }

      if (programOld.getBlendState() != null) {
         GlStateManager.unlockBlend();
      }

      if (programOld.getBlendStatesIndexed() != null) {
         GlStateManager.applyCurrentBlend();
      }

      GlAlphaState alphaNew = programNew.getAlphaState();
      if (alphaNew != null) {
         GlStateManager.lockAlpha(alphaNew);
      }

      GlBlendState blendNew = programNew.getBlendState();
      if (blendNew != null) {
         GlStateManager.lockBlend(blendNew);
      }

      if (programNew.getBlendStatesIndexed() != null) {
         GlStateManager.setBlendsIndexed(programNew.getBlendStatesIndexed());
      }
   }

   private static void setProgramUniform1i(ShaderUniform1i su, int value) {
      su.setValue(value);
   }

   private static void setProgramUniform2i(ShaderUniform2i su, int i0, int i1) {
      su.setValue(i0, i1);
   }

   private static void setProgramUniform1f(ShaderUniform1f su, float value) {
      su.setValue(value);
   }

   private static void setProgramUniform3f(ShaderUniform3f su, float f0, float f1, float f2) {
      su.setValue(f0, f1, f2);
   }

   private static void setProgramUniformMatrix4ARB(ShaderUniformM4 su, boolean transpose, FloatBuffer matrix) {
      su.setValue(transpose, matrix);
   }

   public static int getBufferIndex(String name) {
      int colortexIndex = ShaderParser.getIndex(name, "colortex", 0, 15);
      if (colortexIndex >= 0) {
         return colortexIndex;
      } else {
         int colorimgIndex = ShaderParser.getIndex(name, "colorimg", 0, 15);
         if (colorimgIndex >= 0) {
            return colorimgIndex;
         } else if (name.equals("gcolor")) {
            return 0;
         } else if (name.equals("gdepth")) {
            return 1;
         } else if (name.equals("gnormal")) {
            return 2;
         } else if (name.equals("composite")) {
            return 3;
         } else if (name.equals("gaux1")) {
            return 4;
         } else if (name.equals("gaux2")) {
            return 5;
         } else if (name.equals("gaux3")) {
            return 6;
         } else {
            return name.equals("gaux4") ? 7 : -1;
         }
      }
   }

   private static int getTextureFormatFromString(String par) {
      par = par.trim();

      for (int i = 0; i < formatNames.length; i++) {
         String name = formatNames[i];
         if (par.equals(name)) {
            return formatIds[i];
         }
      }

      return 0;
   }

   public static int getImageFormat(int textureFormat) {
      switch (textureFormat) {
         case 6407:
            return 32849;
         case 6408:
            return 32856;
         case 8194:
            return 33321;
         case 10768:
            return 32849;
         case 32855:
            return 32856;
         case 33319:
            return 33323;
         case 35901:
            return 32852;
         default:
            return textureFormat;
      }
   }

   private static void setupNoiseTexture() {
      if (noiseTexture == null && noiseTexturePath != null) {
         noiseTexture = loadCustomTexture(15, noiseTexturePath);
      }

      if (noiseTexture == null) {
         noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution);
      }
   }

   private static void loadEntityDataMap() {
      mapBlockToEntityData = new IdentityHashMap(300);
      if (mapBlockToEntityData.isEmpty()) {
         for (ResourceLocation key : BuiltInRegistries.f_256975_.m_6566_()) {
            Block block = (Block)BuiltInRegistries.f_256975_.m_7745_(key);
            int id = BuiltInRegistries.f_256975_.m_7447_(block);
            mapBlockToEntityData.put(block, id);
         }
      }

      BufferedReader reader = null;

      try {
         reader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
      } catch (Exception var9) {
      }

      if (reader != null) {
         String line;
         try {
            while ((line = reader.readLine()) != null) {
               Matcher m = patternLoadEntityDataMap.matcher(line);
               if (m.matches()) {
                  String name = m.group(1);
                  String value = m.group(2);
                  int id = Integer.parseInt(value);
                  ResourceLocation loc = new ResourceLocation(name);
                  if (BuiltInRegistries.f_256975_.m_7804_(loc)) {
                     Block block = (Block)BuiltInRegistries.f_256975_.m_7745_(loc);
                     mapBlockToEntityData.put(block, id);
                  } else {
                     SMCLog.warning("Unknown block name %s", name);
                  }
               } else {
                  SMCLog.warning("unmatched %s\n", line);
               }
            }
         } catch (Exception var10) {
            SMCLog.warning("Error parsing mc_Entity_x.txt");
         }
      }

      if (reader != null) {
         try {
            reader.close();
         } catch (Exception var8) {
         }
      }
   }

   private static IntBuffer fillIntBufferZero(IntBuffer buf) {
      int limit = buf.limit();

      for (int i = buf.position(); i < limit; i++) {
         buf.put(i, 0);
      }

      return buf;
   }

   private static DrawBuffers fillIntBufferZero(DrawBuffers buf) {
      int limit = buf.limit();

      for (int i = buf.position(); i < limit; i++) {
         buf.put(i, 0);
      }

      return buf;
   }

   public static void uninit() {
      if (isShaderPackInitialized) {
         checkGLError("Shaders.uninit pre");

         for (int i = 0; i < ProgramsAll.length; i++) {
            Program pi = ProgramsAll[i];
            if (pi.getRef() != 0) {
               GL43.glDeleteProgram(pi.getRef());
               checkGLError("del programRef");
            }

            pi.setRef(0);
            pi.setId(0);
            pi.setDrawBufSettings(null);
            pi.setDrawBuffers(null);
            pi.setCompositeMipmapSetting(0);
            ComputeProgram[] cps = pi.getComputePrograms();

            for (int c = 0; c < cps.length; c++) {
               ComputeProgram cp = cps[c];
               if (cp.getRef() != 0) {
                  GL43.glDeleteProgram(cp.getRef());
                  checkGLError("del programRef");
               }

               cp.setRef(0);
               cp.setId(0);
            }

            pi.setComputePrograms(new ComputeProgram[0]);
         }

         hasDeferredPrograms = false;
         hasShadowcompPrograms = false;
         hasPreparePrograms = false;
         if (dfb != null) {
            dfb.delete();
            dfb = null;
            checkGLError("del dfb");
         }

         if (sfb != null) {
            sfb.delete();
            sfb = null;
            checkGLError("del sfb");
         }

         if (dfbDrawBuffers != null) {
            fillIntBufferZero(dfbDrawBuffers);
         }

         if (sfbDrawBuffers != null) {
            fillIntBufferZero(sfbDrawBuffers);
         }

         if (noiseTexture != null) {
            noiseTexture.deleteTexture();
            noiseTexture = null;
         }

         for (int i = 0; i < colorImageUnit.length; i++) {
            GlStateManager.bindImageTexture(colorImageUnit[i], 0, 0, false, 0, 35000, 32856);
         }

         SMCLog.info("Uninit");
         hasShadowMap = false;
         shouldSkipDefaultShadow = false;
         isShaderPackInitialized = false;
         checkGLError("Shaders.uninit");
      }
   }

   public static void scheduleResize() {
      renderDisplayHeight = 0;
   }

   public static void scheduleResizeShadow() {
      needResizeShadow = true;
   }

   private static void resize() {
      renderDisplayWidth = f_303183_.m_91268_().m_85441_();
      renderDisplayHeight = f_303183_.m_91268_().m_85442_();
      renderWidth = Math.round((float)renderDisplayWidth * configRenderResMul);
      renderHeight = Math.round((float)renderDisplayHeight * configRenderResMul);
      setupFrameBuffer();
   }

   private static void resizeShadow() {
      needResizeShadow = false;
      shadowMapWidth = Math.round((float)spShadowMapWidth * configShadowResMul);
      shadowMapHeight = Math.round((float)spShadowMapHeight * configShadowResMul);
      setupShadowFrameBuffer();
   }

   private static void setupFrameBuffer() {
      if (dfb != null) {
         dfb.delete();
      }

      boolean[] depthFilterNearest = ArrayUtils.newBoolean(usedDepthBuffers, true);
      boolean[] depthFilterHardware = new boolean[usedDepthBuffers];
      boolean[] colorFilterNearest = new boolean[usedColorBuffers];
      int[] colorImageUnits = bindImageTextures ? colorImageUnit : null;
      dfb = new ShadersFramebuffer(
         "dfb",
         renderWidth,
         renderHeight,
         usedColorBuffers,
         usedDepthBuffers,
         8,
         depthFilterNearest,
         depthFilterHardware,
         colorFilterNearest,
         colorBufferSizes,
         gbuffersFormat,
         colorTextureImageUnit,
         depthTextureImageUnit,
         colorImageUnits,
         dfbDrawBuffers
      );
      dfb.setup();
   }

   public static int getPixelFormat(int internalFormat) {
      switch (internalFormat) {
         case 33329:
         case 33335:
         case 36238:
         case 36239:
            return 36251;
         case 33330:
         case 33336:
         case 36220:
         case 36221:
            return 36251;
         case 33331:
         case 33337:
         case 36232:
         case 36233:
            return 36251;
         case 33332:
         case 33338:
         case 36214:
         case 36215:
            return 36251;
         case 33333:
         case 33339:
         case 36226:
         case 36227:
            return 36251;
         case 33334:
         case 33340:
         case 36208:
         case 36209:
            return 36251;
         default:
            return 32993;
      }
   }

   private static void setupShadowFrameBuffer() {
      if (hasShadowMap) {
         isShadowPass = true;
         if (sfb != null) {
            sfb.delete();
         }

         DynamicDimension[] shadowColorBufferSizes = new DynamicDimension[2];
         int[] shadowColorImageUnits = bindImageTextures ? shadowColorImageUnit : null;
         sfb = new ShadersFramebuffer(
            "sfb",
            shadowMapWidth,
            shadowMapHeight,
            usedShadowColorBuffers,
            usedShadowDepthBuffers,
            8,
            shadowFilterNearest,
            shadowHardwareFilteringEnabled,
            shadowColorFilterNearest,
            shadowColorBufferSizes,
            shadowBuffersFormat,
            shadowColorTextureImageUnit,
            shadowDepthTextureImageUnit,
            shadowColorImageUnits,
            sfbDrawBuffers
         );
         sfb.setup();
         isShadowPass = false;
      }
   }

   public static void beginRender(Minecraft minecraft, Camera activeRenderInfo, float partialTicks) {
      checkGLError("pre beginRender");
      checkWorldChanged(f_303183_.f_91073_);
      f_303183_ = minecraft;
      f_303183_.m_91307_().m_6180_("init");
      entityRenderer = f_303183_.f_91063_;
      if (!isShaderPackInitialized) {
         try {
            init();
         } catch (IllegalStateException var12) {
            if (Config.normalize(var12.getMessage()).equals("Function is not supported")) {
               printChatAndLogError("[Shaders] Error: " + var12.getMessage());
               var12.printStackTrace();
               setShaderPack("OFF");
               return;
            }
         }
      }

      if (f_303183_.m_91268_().m_85441_() != renderDisplayWidth || f_303183_.m_91268_().m_85442_() != renderDisplayHeight) {
         resize();
      }

      if (needResizeShadow) {
         resizeShadow();
      }

      frameCounter++;
      if (frameCounter >= 720720) {
         frameCounter = 0;
      }

      systemTime = System.currentTimeMillis();
      if (lastSystemTime == 0L) {
         lastSystemTime = systemTime;
      }

      diffSystemTime = systemTime - lastSystemTime;
      lastSystemTime = systemTime;
      frameTime = (float)diffSystemTime / 1000.0F;
      frameTimeCounter = frameTimeCounter + frameTime;
      frameTimeCounter %= 3600.0F;
      pointOfViewChanged = pointOfView != f_303183_.f_91066_.m_92176_();
      pointOfView = f_303183_.f_91066_.m_92176_();
      ShadersRender.updateActiveRenderInfo(activeRenderInfo, minecraft, partialTicks);
      ClientLevel world = f_303183_.f_91073_;
      if (world != null) {
         worldTime = world.m_46468_();
         diffWorldTime = (worldTime - lastWorldTime) % 24000L;
         if (diffWorldTime < 0L) {
            diffWorldTime += 24000L;
         }

         lastWorldTime = worldTime;
         moonPhase = world.m_46941_();
         rainStrength = world.m_46722_(partialTicks);
         float fadeScalarRain = (float)diffSystemTime * 0.01F;
         float tempRain = (float)Math.exp(Math.m_260877_(0.5) * (double)fadeScalarRain / (double)(wetness < rainStrength ? drynessHalfLife : wetnessHalfLife));
         wetness = wetness * tempRain + rainStrength * (1.0F - tempRain);
         bossBattle = getBossBattle();
         Entity renderViewEntity = activeRenderInfo.m_90592_();
         if (renderViewEntity != null) {
            isSleeping = renderViewEntity instanceof LivingEntity && ((LivingEntity)renderViewEntity).m_5803_();
            eyePosY = (float)activeRenderInfo.m_90583_().m_7098_();
            eyeBrightness = f_303183_.m_91290_().m_114394_(renderViewEntity, partialTicks);
            float fadeScalarBrightness = (float)diffSystemTime * 0.01F;
            float tempBrightness = (float)Math.exp(Math.m_260877_(0.5) * (double)fadeScalarBrightness / (double)eyeBrightnessHalflife);
            eyeBrightnessFadeX = eyeBrightnessFadeX * tempBrightness + (float)(eyeBrightness & 65535) * (1.0F - tempBrightness);
            eyeBrightnessFadeY = eyeBrightnessFadeY * tempBrightness + (float)(eyeBrightness >> 16) * (1.0F - tempBrightness);
            FogType cameraFogType = activeRenderInfo.m_167685_();
            if (cameraFogType == FogType.WATER) {
               isEyeInWater = 1;
            } else if (cameraFogType == FogType.LAVA) {
               isEyeInWater = 2;
            } else if (cameraFogType == FogType.POWDER_SNOW) {
               isEyeInWater = 3;
            } else {
               isEyeInWater = 0;
            }

            if (renderViewEntity instanceof LivingEntity player) {
               nightVision = 0.0F;
               if (player.m_21023_(MobEffects.f_19611_)) {
                  nightVision = GameRenderer.m_109108_(player, partialTicks);
               }

               blindness = 0.0F;
               if (player.m_21023_(MobEffects.f_19610_)) {
                  int blindnessTicks = player.m_21124_(MobEffects.f_19610_).m_19557_();
                  blindness = Config.limit((float)blindnessTicks / 20.0F, 0.0F, 1.0F);
               }
            }

            Vec3 skyColorV = world.m_171660_(activeRenderInfo.m_90583_(), partialTicks);
            skyColorV = CustomColors.getWorldSkyColor(skyColorV, world, renderViewEntity, partialTicks);
            skyColorR = (float)skyColorV.f_82479_;
            skyColorG = (float)skyColorV.f_82480_;
            skyColorB = (float)skyColorV.f_82481_;
         }
      }

      isRenderingWorld = true;
      isCompositeRendered = false;
      isShadowPass = false;
      isHandRenderedMain = false;
      isHandRenderedOff = false;
      skipRenderHandMain = false;
      skipRenderHandOff = false;
      dfb.setColorBuffersFiltering(9729, 9729);
      bindGbuffersTextures();
      dfb.bindColorImages(true);
      if (sfb != null) {
         sfb.bindColorImages(true);
      }

      previousCameraPositionX = cameraPositionX;
      previousCameraPositionY = cameraPositionY;
      previousCameraPositionZ = cameraPositionZ;
      previousProjection.position(0);
      projection.position(0);
      previousProjection.put(projection);
      previousProjection.position(0);
      projection.position(0);
      previousModelView.position(0);
      modelView.position(0);
      previousModelView.put(modelView);
      previousModelView.position(0);
      modelView.position(0);
      lastModelView.identity();
      lastProjection.identity();
      checkGLError("beginRender");
      GlStateManager.enableAlphaTest();
      GlStateManager.alphaFunc(516, 0.1F);
      setDefaultAttribColor();
      setDefaultAttribLightmap();
      setDefaultAttribNormal();
      ShadersRender.renderShadowMap(entityRenderer, activeRenderInfo, 0, partialTicks);
      f_303183_.m_91307_().m_7238_();
      dfb.setColorTextures(true);
      setRenderStage(RenderStage.NONE);
      checkGLError("end beginRender");
   }

   private static void bindGbuffersTextures() {
      bindTextures(4, customTexturesGbuffers);
   }

   private static void bindTextures(int startColorBuffer, ICustomTexture[] customTextures) {
      if (sfb != null) {
         sfb.bindColorTextures(0);
         sfb.bindDepthTextures(shadowDepthTextureImageUnit);
      }

      dfb.bindColorTextures(startColorBuffer);
      dfb.bindDepthTextures(depthTextureImageUnit);
      if (noiseTextureEnabled) {
         GlStateManager._activeTexture(33984 + noiseTexture.getTextureUnit());
         GlStateManager._bindTexture(noiseTexture.getTextureId());
         GlStateManager._activeTexture(33984);
      }

      bindCustomTextures(customTextures);
   }

   public static void checkWorldChanged(ClientLevel world) {
      if (currentWorld != world) {
         Level oldWorld = currentWorld;
         currentWorld = world;
         if (currentWorld == null) {
            cameraPositionX = 0.0;
            cameraPositionY = 0.0;
            cameraPositionZ = 0.0;
            previousCameraPositionX = 0.0;
            previousCameraPositionY = 0.0;
            previousCameraPositionZ = 0.0;
         }

         setCameraOffset(f_303183_.m_91288_());
         int dimIdOld = WorldUtils.getDimensionId(oldWorld);
         int dimIdNew = WorldUtils.getDimensionId(world);
         if (dimIdNew != dimIdOld) {
            boolean dimShadersOld = shaderPackDimensions.m_274455_(dimIdOld);
            boolean dimShadersNew = shaderPackDimensions.m_274455_(dimIdNew);
            if (dimShadersOld || dimShadersNew) {
               uninit();
            }
         }

         Smoother.resetValues();
      }
   }

   public static void beginRenderPass(float partialTicks) {
      if (!isShadowPass) {
         dfb.bindFramebuffer();
         GL11.glViewport(0, 0, renderWidth, renderHeight);
         GlState.setDrawBuffers(null);
         ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
         useProgram(ProgramTextured);
         checkGLError("end beginRenderPass");
      }
   }

   public static void setViewport(int vx, int vy, int vw, int vh) {
      GlStateManager._colorMask(true, true, true, true);
      if (isShadowPass) {
         GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
      } else {
         GL11.glViewport(0, 0, renderWidth, renderHeight);
         dfb.bindFramebuffer();
         isRenderingDfb = true;
         GlStateManager._enableCull();
         GlStateManager._enableDepthTest();
         GlState.setDrawBuffers(drawBuffersNone);
         useProgram(ProgramTextured);
         checkGLError("beginRenderPass");
      }
   }

   public static void setFogMode(int value) {
      fogMode = value;
      if (fogEnabled && fogAllowed) {
         setProgramUniform1i(uniform_fogMode, value);
      }
   }

   public static void setFogShape(int value) {
      fogShape = value;
      if (fogEnabled && fogAllowed) {
         setProgramUniform1i(uniform_fogShape, value);
      }
   }

   public static void setFogColor(float r, float g, float b) {
      fogColorR = r;
      fogColorG = g;
      fogColorB = b;
      setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
   }

   public static void setClearColor(float red, float green, float blue, float alpha) {
      clearColor.set(red, green, blue, 1.0F);
   }

   public static void clearRenderBuffer() {
      if (isShadowPass) {
         checkGLError("shadow clear pre");
         sfb.clearDepthBuffer(new Vector4f(1.0F, 1.0F, 1.0F, 1.0F));
         checkGLError("shadow clear");
      } else {
         checkGLError("clear pre");
         Vector4f[] clearColors = new Vector4f[usedColorBuffers];

         for (int i = 0; i < clearColors.length; i++) {
            clearColors[i] = getBufferClearColor(i);
         }

         dfb.clearColorBuffers(gbuffersClear, clearColors);
         dfb.setDrawBuffers();
         checkFramebufferStatus("clear");
         checkGLError("clear");
      }
   }

   public static void renderPrepare() {
      if (hasPreparePrograms) {
         renderPrepareComposites();
         bindGbuffersTextures();
         dfb.setDrawBuffers();
         dfb.setColorTextures(true);
      }
   }

   private static Vector4f getBufferClearColor(int buffer) {
      Vector4f col = gbuffersClearColor[buffer];
      if (col != null) {
         return col;
      } else if (buffer == 0) {
         return clearColor;
      } else {
         return buffer == 1 ? CLEAR_COLOR_1 : CLEAR_COLOR_0;
      }
   }

   public static void setCamera(Matrix4f viewMatrixIn, Camera activeRenderInfo, float partialTicks) {
      Entity viewEntity = activeRenderInfo.m_90592_();
      Vec3 cameraPos = activeRenderInfo.m_90583_();
      double x = cameraPos.f_82479_;
      double y = cameraPos.f_82480_;
      double z = cameraPos.f_82481_;
      updateCameraOffset(viewEntity);
      cameraPositionX = x - (double)cameraOffsetX;
      cameraPositionY = y;
      cameraPositionZ = z - (double)cameraOffsetZ;
      updateProjectionMatrix();
      setModelView(viewMatrixIn);
      checkGLError("setCamera");
   }

   public static void updateProjectionMatrix() {
      setProjection(RenderSystem.getProjectionMatrix());
   }

   private static void updateShadowProjectionMatrix() {
      MathUtils.write(RenderSystem.getProjectionMatrix(), shadowProjection);
      SMath.invertMat4FBFA(shadowProjectionInverse.position(0), shadowProjection.position(0), faShadowProjectionInverse, faShadowProjection);
      shadowProjection.position(0);
      shadowProjectionInverse.position(0);
   }

   private static void updateCameraOffset(Entity viewEntity) {
      double adx = Math.abs(cameraPositionX - previousCameraPositionX);
      double adz = Math.abs(cameraPositionZ - previousCameraPositionZ);
      double apx = Math.abs(cameraPositionX);
      double apz = Math.abs(cameraPositionZ);
      if (adx > 1000.0 || adz > 1000.0 || apx > 1000000.0 || apz > 1000000.0) {
         setCameraOffset(viewEntity);
      }
   }

   private static void setCameraOffset(Entity viewEntity) {
      if (viewEntity == null) {
         cameraOffsetX = 0;
         cameraOffsetZ = 0;
      } else {
         cameraOffsetX = (int)viewEntity.m_20185_() / 1000 * 1000;
         cameraOffsetZ = (int)viewEntity.m_20189_() / 1000 * 1000;
      }
   }

   public static void setCameraShadow(PoseStack matrixStack, Camera activeRenderInfo, float partialTicks) {
      Entity viewEntity = activeRenderInfo.m_90592_();
      Vec3 cameraPos = activeRenderInfo.m_90583_();
      double x = cameraPos.f_82479_;
      double y = cameraPos.f_82480_;
      double z = cameraPos.f_82481_;
      updateCameraOffset(viewEntity);
      cameraPositionX = x - (double)cameraOffsetX;
      cameraPositionY = y;
      cameraPositionZ = z - (double)cameraOffsetZ;
      GlStateManager._viewport(0, 0, shadowMapWidth, shadowMapHeight);
      if (shadowMapIsOrtho) {
         Matrix4f projectionMatrix = MathUtils.makeOrtho4f(-shadowMapHalfPlane, shadowMapHalfPlane, shadowMapHalfPlane, -shadowMapHalfPlane, 0.05F, 256.0F);
         RenderSystem.setProjectionMatrix(projectionMatrix, VertexSorting.f_276633_);
      } else {
         Matrix4f projectionMatrix = new Matrix4f().perspective(shadowMapFOV, (float)shadowMapWidth / (float)shadowMapHeight, 0.05F, 256.0F);
         RenderSystem.setProjectionMatrix(projectionMatrix, VertexSorting.f_276450_);
      }

      matrixStack.m_252880_(0.0F, 0.0F, -100.0F);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(90.0F));
      celestialAngle = f_303183_.f_91073_.m_46942_(partialTicks);
      sunAngle = celestialAngle < 0.75F ? celestialAngle + 0.25F : celestialAngle - 0.75F;
      float angle = celestialAngle * -360.0F;
      float angleInterval = shadowAngleInterval > 0.0F ? angle % shadowAngleInterval - shadowAngleInterval * 0.5F : 0.0F;
      if ((double)sunAngle <= 0.5) {
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(angle - angleInterval));
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(sunPathRotation));
         shadowAngle = sunAngle;
      } else {
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(angle + 180.0F - angleInterval));
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(sunPathRotation));
         shadowAngle = sunAngle - 0.5F;
      }

      if (shadowMapIsOrtho) {
         float trans = shadowIntervalSize;
         float trans2 = trans / 2.0F;
         matrixStack.m_252880_((float)x % trans - trans2, (float)y % trans - trans2, (float)z % trans - trans2);
      }

      float raSun = sunAngle * (float) (Math.PI * 2);
      float x1 = (float)Math.cos((double)raSun);
      float y1 = (float)Math.sin((double)raSun);
      float raTilt = sunPathRotation * (float) (Math.PI * 2);
      float x2 = x1;
      float y2 = y1 * (float)Math.cos((double)raTilt);
      float z2 = y1 * (float)Math.sin((double)raTilt);
      if ((double)sunAngle > 0.5) {
         x2 = -x1;
         y2 = -y2;
         z2 = -z2;
      }

      shadowLightPositionVector[0] = x2;
      shadowLightPositionVector[1] = y2;
      shadowLightPositionVector[2] = z2;
      shadowLightPositionVector[3] = 0.0F;
      updateShadowProjectionMatrix();
      Matrix4f shadowModelViewMat4 = matrixStack.m_85850_().m_252922_();
      MathUtils.write(shadowModelViewMat4, shadowModelView.position(0));
      SMath.invertMat4FBFA(shadowModelViewInverse.position(0), shadowModelView.position(0), faShadowModelViewInverse, faShadowModelView);
      shadowModelView.position(0);
      shadowModelViewInverse.position(0);
      setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
      setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
      setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
      setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
      setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
      setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
      setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
      setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
      setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
      setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
      checkGLError("setCamera");
   }

   public static void preCelestialRotate(PoseStack matrixStackIn) {
      matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(sunPathRotation * 1.0F));
      checkGLError("preCelestialRotate");
   }

   public static void postCelestialRotate(PoseStack matrixStackIn) {
      Matrix4f modelViewMat4 = matrixStackIn.m_85850_().m_252922_();
      Matrix4f modelViewMat4T = new Matrix4f(modelViewMat4);
      modelViewMat4T.transpose();
      MathUtils.write(modelViewMat4T, tempMat);
      SMath.multiplyMat4xVec4(sunPosition, tempMat, sunPosModelView);
      SMath.multiplyMat4xVec4(moonPosition, tempMat, moonPosModelView);
      System.arraycopy(shadowAngle == sunAngle ? sunPosition : moonPosition, 0, shadowLightPosition, 0, 3);
      setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
      setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
      setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
      if (customUniforms != null) {
         customUniforms.m_252999_();
      }

      checkGLError("postCelestialRotate");
   }

   public static void setUpPosition(PoseStack matrixStackIn) {
      Matrix4f modelViewMat4 = matrixStackIn.m_85850_().m_252922_();
      Matrix4f modelViewMat4T = new Matrix4f(modelViewMat4);
      modelViewMat4T.transpose();
      MathUtils.write(modelViewMat4T, tempMat);
      SMath.multiplyMat4xVec4(upPosition, tempMat, upPosModelView);
      setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
      if (customUniforms != null) {
         customUniforms.m_252999_();
      }
   }

   public static void drawComposite() {
      Matrix4f mv = MATRIX_IDENTITY;
      Matrix4f mp = MathUtils.makeOrtho4f(0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F);
      Matrix4f mt = MATRIX_IDENTITY;
      setModelViewMatrix(mv);
      setProjectionMatrix(mp);
      setTextureMatrix(mt);
      drawCompositeQuad();
      int countInstances = activeProgram.getCountInstances();
      if (countInstances > 1) {
         for (int i = 1; i < countInstances; i++) {
            uniform_instanceId.setValue(i);
            drawCompositeQuad();
         }

         uniform_instanceId.setValue(0);
      }
   }

   private static void drawCompositeQuad() {
      Tesselator tesselator = RenderSystem.renderThreadTesselator();
      BufferBuilder bufferbuilder = tesselator.m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
      bufferbuilder.m_167146_(0.0F, 0.0F, 0.0F).m_167083_(0.0F, 0.0F);
      bufferbuilder.m_167146_(1.0F, 0.0F, 0.0F).m_167083_(1.0F, 0.0F);
      bufferbuilder.m_167146_(1.0F, 1.0F, 0.0F).m_167083_(1.0F, 1.0F);
      bufferbuilder.m_167146_(0.0F, 1.0F, 0.0F).m_167083_(0.0F, 1.0F);
      BufferUploader.m_231209_(bufferbuilder.m_339970_());
   }

   public static void renderDeferred() {
      if (!isShadowPass) {
         boolean buffersChanged = checkBufferFlip(dfb, ProgramDeferredPre);
         if (hasDeferredPrograms) {
            checkGLError("pre-render Deferred");
            renderDeferredComposites();
            buffersChanged = true;
         }

         if (buffersChanged) {
            bindGbuffersTextures();
            dfb.setColorTextures(true);
            DrawBuffers drawBuffersWater = ProgramWater.getDrawBuffers() != null ? ProgramWater.getDrawBuffers() : dfb.getDrawBuffers();
            GlState.setDrawBuffers(drawBuffersWater);
            GlStateManager._activeTexture(33984);
            f_303183_.m_91097_().m_174784_(TextureAtlas.f_118259_);
         }
      }
   }

   public static void renderCompositeFinal() {
      if (!isShadowPass) {
         checkBufferFlip(dfb, ProgramCompositePre);
         checkGLError("pre-render CompositeFinal");
         renderComposites();
      }
   }

   private static boolean checkBufferFlip(ShadersFramebuffer framebuffer, Program program) {
      boolean flipped = false;
      Boolean[] buffersFlip = program.getBuffersFlip();

      for (int i = 0; i < usedColorBuffers; i++) {
         if (Config.isTrue(buffersFlip[i])) {
            framebuffer.flipColorTexture(i);
            flipped = true;
         }
      }

      return flipped;
   }

   private static void renderComposites() {
      if (!isShadowPass) {
         renderComposites(ProgramsComposite, true, customTexturesComposite);
      }
   }

   private static void renderDeferredComposites() {
      if (!isShadowPass) {
         renderComposites(ProgramsDeferred, false, customTexturesDeferred);
      }
   }

   public static void renderPrepareComposites() {
      renderComposites(ProgramsPrepare, false, customTexturesPrepare);
   }

   private static void renderComposites(Program[] ps, boolean renderFinal, ICustomTexture[] customTextures) {
      renderComposites(dfb, ps, renderFinal, customTextures);
   }

   public static void renderShadowComposites() {
      renderComposites(sfb, ProgramsShadowcomp, false, customTexturesShadowcomp);
   }

   private static void renderComposites(ShadersFramebuffer framebuffer, Program[] ps, boolean renderFinal, ICustomTexture[] customTextures) {
      GlStateManager.enableTexture();
      GlStateManager.disableAlphaTest();
      GlStateManager._disableBlend();
      GlStateManager._enableDepthTest();
      GlStateManager._depthFunc(519);
      GlStateManager._depthMask(false);
      bindTextures(0, customTextures);
      framebuffer.bindColorImages(true);
      framebuffer.setColorTextures(false);
      framebuffer.setDepthTexture();
      framebuffer.setDrawBuffers();
      checkGLError("pre-composite");

      for (int i = 0; i < ps.length; i++) {
         Program program = ps[i];
         dispatchComputes(framebuffer, program.getComputePrograms());
         if (program.getId() != 0) {
            useProgram(program);
            checkGLError(program.getName());
            if (program.hasCompositeMipmaps()) {
               framebuffer.genCompositeMipmap(program.getCompositeMipmapSetting());
            }

            preDrawComposite(framebuffer, program);
            drawComposite();
            postDrawComposite(framebuffer, program);
            framebuffer.flipColorTextures(program.getToggleColorTextures());
         }
      }

      checkGLError("composite");
      if (renderFinal) {
         renderFinal();
         isCompositeRendered = true;
      }

      GlStateManager.enableTexture();
      GlStateManager.enableAlphaTest();
      GlStateManager._enableBlend();
      GlStateManager._depthFunc(515);
      GlStateManager._depthMask(true);
      useProgram(ProgramNone);
   }

   private static void preDrawComposite(ShadersFramebuffer framebuffer, Program program) {
      int drawWidth = framebuffer.m_92515_();
      int drawHeight = framebuffer.getHeight();
      if (program.getDrawSize() != null) {
         Dimension dim = program.getDrawSize().getDimension(drawWidth, drawHeight);
         drawWidth = dim.width;
         drawHeight = dim.height;
         FixedFramebuffer ff = framebuffer.getFixedFramebuffer(drawWidth, drawHeight, program.getDrawBuffers(), false);
         ff.bindFramebuffer();
         GL43.glViewport(0, 0, drawWidth, drawHeight);
      }

      RenderScale rs = program.getRenderScale();
      if (rs != null) {
         int x = (int)((float)drawWidth * rs.getOffsetX());
         int y = (int)((float)drawHeight * rs.getOffsetY());
         int w = (int)((float)drawWidth * rs.getScale());
         int h = (int)((float)drawHeight * rs.getScale());
         GL43.glViewport(x, y, w, h);
      }
   }

   private static void postDrawComposite(ShadersFramebuffer framebuffer, Program program) {
      if (program.getDrawSize() != null) {
         framebuffer.bindFramebuffer();
         GL43.glViewport(0, 0, framebuffer.m_92515_(), framebuffer.getHeight());
      }

      RenderScale rs = activeProgram.getRenderScale();
      if (rs != null) {
         GL43.glViewport(0, 0, framebuffer.m_92515_(), framebuffer.getHeight());
      }
   }

   public static void dispatchComputes(ShadersFramebuffer framebuffer, ComputeProgram[] cps) {
      for (int i = 0; i < cps.length; i++) {
         ComputeProgram cp = cps[i];
         dispatchCompute(cp);
         if (cp.hasCompositeMipmaps()) {
            framebuffer.genCompositeMipmap(cp.getCompositeMipmapSetting());
         }
      }
   }

   public static void useComputeProgram(ComputeProgram cp) {
      GlStateManager._glUseProgram(cp.getId());
      if (checkGLError("useComputeProgram") != 0) {
         cp.setId(0);
      } else {
         shaderUniforms.setProgram(cp.getId());
         if (customUniforms != null) {
            customUniforms.setProgram(cp.getId());
         }

         setProgramUniforms(cp.getProgramStage());
         setImageUniforms();
         if (dfb != null) {
            dfb.bindColorImages(true);
         }
      }
   }

   public static void dispatchCompute(ComputeProgram cp) {
      if (dfb != null) {
         useComputeProgram(cp);
         Vec3i workGroups = cp.getWorkGroups();
         if (workGroups == null) {
            Vec2 workGroupsRender = cp.getWorkGroupsRender();
            if (workGroupsRender == null) {
               workGroupsRender = new Vec2(1.0F, 1.0F);
            }

            int computeWidth = (int)Math.ceil((double)((float)renderWidth * workGroupsRender.f_82470_));
            int computeHeight = (int)Math.ceil((double)((float)renderHeight * workGroupsRender.f_82471_));
            Vec3i localSize = cp.getLocalSize();
            int groupsX = (int)Math.ceil(1.0 * (double)computeWidth / (double)localSize.m_123341_());
            int groupsY = (int)Math.ceil(1.0 * (double)computeHeight / (double)localSize.m_123342_());
            workGroups = new Vec3i(groupsX, groupsY, 1);
         }

         GL43.glMemoryBarrier(40);
         GL43.glDispatchCompute(workGroups.m_123341_(), workGroups.m_123342_(), workGroups.m_123343_());
         GL43.glMemoryBarrier(40);
         checkGLError("compute");
      }
   }

   private static void renderFinal() {
      dispatchComputes(dfb, ProgramFinal.getComputePrograms());
      isRenderingDfb = false;
      f_303183_.m_91385_().m_83947_(true);
      GlStateManager._glFramebufferTexture2D(GLConst.GL_FRAMEBUFFER, GLConst.GL_COLOR_ATTACHMENT0, 3553, f_303183_.m_91385_().m_83975_(), 0);
      GL43.glViewport(0, 0, f_303183_.m_91268_().m_85441_(), f_303183_.m_91268_().m_85442_());
      GlStateManager._depthMask(true);
      GL43.glClearColor(clearColor.m_305649_(), clearColor.m_306225_(), clearColor.m_240700_(), 1.0F);
      GL43.glClear(16640);
      GlStateManager.enableTexture();
      GlStateManager.disableAlphaTest();
      GlStateManager._disableBlend();
      GlStateManager._enableDepthTest();
      GlStateManager._depthFunc(519);
      GlStateManager._depthMask(false);
      checkGLError("pre-final");
      useProgram(ProgramFinal);
      checkGLError("final");
      if (ProgramFinal.hasCompositeMipmaps()) {
         dfb.genCompositeMipmap(ProgramFinal.getCompositeMipmapSetting());
      }

      drawComposite();
      checkGLError("renderCompositeFinal");
   }

   public static void endRender() {
      if (isShadowPass) {
         checkGLError("shadow endRender");
      } else {
         if (!isCompositeRendered) {
            renderCompositeFinal();
         }

         isRenderingWorld = false;
         GlStateManager._colorMask(true, true, true, true);
         useProgram(ProgramNone);
         setRenderStage(RenderStage.NONE);
         checkGLError("endRender end");
      }
   }

   public static void beginSky() {
      isRenderingSky = true;
      fogEnabled = true;
      useProgram(ProgramSkyTextured);
      pushEntity(-2, 0);
      setRenderStage(RenderStage.SKY);
   }

   public static void setSkyColor(Vec3 v3color) {
      skyColorR = (float)v3color.f_82479_;
      skyColorG = (float)v3color.f_82480_;
      skyColorB = (float)v3color.f_82481_;
      setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
   }

   public static void drawHorizon(PoseStack matrixStackIn) {
      double top = 16.0;
      double bot = -cameraPositionY + currentWorld.m_6106_().m_171687_(currentWorld) + 12.0 - 16.0;
      if (cameraPositionY < currentWorld.m_6106_().m_171687_(currentWorld)) {
         bot = -4.0;
      }

      Matrix4f matrix = matrixStackIn.m_85850_().m_252922_();
      BufferBuilder tess = Tesselator.m_85913_().m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
      int dist = 512;

      for (int i = 0; i < 8; i++) {
         double x = (double)((float)dist * Mth.m_14089_((float)i * (float) (Math.PI / 4)));
         double z = (double)((float)dist * Mth.m_14031_((float)i * (float) (Math.PI / 4)));
         int iN = i + 1;
         double xN = (double)((float)dist * Mth.m_14089_((float)iN * (float) (Math.PI / 4)));
         double zN = (double)((float)dist * Mth.m_14031_((float)iN * (float) (Math.PI / 4)));
         addVertex(tess, matrix, x, bot, z);
         addVertex(tess, matrix, xN, bot, zN);
         addVertex(tess, matrix, xN, top, zN);
         addVertex(tess, matrix, x, top, z);
         addVertex(tess, matrix, 0.0, bot, 0.0);
         addVertex(tess, matrix, 0.0, bot, 0.0);
         addVertex(tess, matrix, xN, bot, zN);
         addVertex(tess, matrix, x, bot, z);
      }

      BufferUploader.m_231202_(tess.m_339970_());
   }

   private static void addVertex(BufferBuilder buffer, Matrix4f matrix, double x, double y, double z) {
      float xt = MathUtils.getTransformX(matrix, (float)x, (float)y, (float)z);
      float yt = MathUtils.getTransformY(matrix, (float)x, (float)y, (float)z);
      float zt = MathUtils.getTransformZ(matrix, (float)x, (float)y, (float)z);
      buffer.m_167146_(xt, yt, zt);
   }

   public static void preSkyList(PoseStack matrixStackIn) {
      setUpPosition(matrixStackIn);
      RenderSystem.setShaderColor(fogColorR, fogColorG, fogColorB, 1.0F);
      drawHorizon(matrixStackIn);
      RenderSystem.setShaderColor(skyColorR, skyColorG, skyColorB, 1.0F);
   }

   public static void endSky() {
      isRenderingSky = false;
      useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
      popEntity();
      setRenderStage(RenderStage.NONE);
   }

   public static void beginUpdateChunks() {
      checkGLError("beginUpdateChunks1");
      checkFramebufferStatus("beginUpdateChunks1");
      if (!isShadowPass) {
         useProgram(ProgramTerrain);
      }

      checkGLError("beginUpdateChunks2");
      checkFramebufferStatus("beginUpdateChunks2");
   }

   public static void endUpdateChunks() {
      checkGLError("endUpdateChunks1");
      checkFramebufferStatus("endUpdateChunks1");
      if (!isShadowPass) {
         useProgram(ProgramTerrain);
      }

      checkGLError("endUpdateChunks2");
      checkFramebufferStatus("endUpdateChunks2");
   }

   public static boolean shouldRenderClouds(Options gs) {
      if (!shaderPackLoaded) {
         return true;
      } else {
         checkGLError("shouldRenderClouds");
         return isShadowPass ? configCloudShadow : gs.m_92174_() != CloudStatus.OFF;
      }
   }

   public static void beginClouds() {
      fogEnabled = true;
      pushEntity(-3, 0);
      useProgram(ProgramClouds);
      setRenderStage(RenderStage.CLOUDS);
   }

   public static void endClouds() {
      disableFog();
      popEntity();
      useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
      setRenderStage(RenderStage.NONE);
   }

   public static void beginEntities() {
      if (isRenderingWorld) {
         useProgram(ProgramEntities);
         setRenderStage(RenderStage.ENTITIES);
      }
   }

   public static void nextEntity(Entity entity) {
      if (isRenderingWorld) {
         if (f_303183_.m_91314_(entity)) {
            useProgram(ProgramEntitiesGlowing);
         } else {
            useProgram(ProgramEntities);
         }

         setEntityId(entity);
      }
   }

   public static void setEntityId(Entity entity) {
      if (uniform_entityId.isDefined()) {
         int id = EntityUtils.getEntityIdByClass(entity);
         int idAlias = EntityAliases.getEntityAliasId(id);
         uniform_entityId.setValue(idAlias);
      }
   }

   public static void beginSpiderEyes() {
      if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
         useProgram(ProgramSpiderEyes);
         GlStateManager._blendFunc(770, 771);
      }
   }

   public static void endSpiderEyes() {
      if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
         useProgram(ProgramEntities);
      }
   }

   public static void endEntities() {
      if (isRenderingWorld) {
         setEntityId(null);
         useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
      }
   }

   public static void setEntityColor(float r, float g, float b, float a) {
      if (isRenderingWorld && !isShadowPass) {
         uniform_entityColor.setValue(r, g, b, a);
      }
   }

   public static void beginLivingDamage() {
      if (isRenderingWorld) {
         ShadersTex.bindTexture(defaultTexture);
         if (!isShadowPass) {
            GlState.setDrawBuffers(drawBuffersColorAtt[0]);
         }
      }
   }

   public static void endLivingDamage() {
      if (isRenderingWorld && !isShadowPass) {
         GlState.setDrawBuffers(ProgramEntities.getDrawBuffers());
      }
   }

   public static void beginBlockEntities() {
      if (isRenderingWorld) {
         checkGLError("beginBlockEntities");
         useProgram(ProgramBlock);
         setRenderStage(RenderStage.BLOCK_ENTITIES);
      }
   }

   public static void nextBlockEntity(BlockEntity tileEntity) {
      if (isRenderingWorld) {
         checkGLError("nextBlockEntity");
         useProgram(ProgramBlock);
         setBlockEntityId(tileEntity);
      }
   }

   public static void setBlockEntityId(BlockEntity tileEntity) {
      if (uniform_blockEntityId.isDefined()) {
         int blockId = getBlockEntityId(tileEntity);
         uniform_blockEntityId.setValue(blockId);
      }
   }

   private static int getBlockEntityId(BlockEntity tileEntity) {
      if (tileEntity == null) {
         return -1;
      } else {
         BlockState blockState = tileEntity.m_58900_();
         return blockState == null ? -1 : BlockAliases.getAliasBlockId(blockState);
      }
   }

   public static void endBlockEntities() {
      if (isRenderingWorld) {
         checkGLError("endBlockEntities");
         setBlockEntityId(null);
         useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
         ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
      }
   }

   public static void beginLitParticles() {
      useProgram(ProgramTexturedLit);
   }

   public static void beginParticles() {
      useProgram(ProgramTextured);
      setRenderStage(RenderStage.PARTICLES);
   }

   public static void endParticles() {
      useProgram(ProgramTexturedLit);
      setRenderStage(RenderStage.NONE);
   }

   public static void readCenterDepth() {
      if (!isShadowPass && centerDepthSmoothEnabled) {
         tempDirectFloatBuffer.clear();
         GL43.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, 6402, 5126, tempDirectFloatBuffer);
         centerDepth = tempDirectFloatBuffer.get(0);
         float fadeScalar = (float)diffSystemTime * 0.01F;
         float fadeFactor = (float)Math.exp(Math.m_260877_(0.5) * (double)fadeScalar / (double)centerDepthSmoothHalflife);
         centerDepthSmooth = centerDepthSmooth * fadeFactor + centerDepth * (1.0F - fadeFactor);
      }
   }

   public static void beginWeather() {
      if (!isShadowPass) {
         GlStateManager._enableDepthTest();
         GlStateManager._enableBlend();
         GlStateManager._blendFunc(770, 771);
         GlStateManager.enableAlphaTest();
         useProgram(ProgramWeather);
         setRenderStage(RenderStage.RAIN_SNOW);
      }
   }

   public static void endWeather() {
      GlStateManager._disableBlend();
      useProgram(ProgramTexturedLit);
      setRenderStage(RenderStage.NONE);
   }

   public static void preRenderHand() {
      if (!isShadowPass && usedDepthBuffers >= 3) {
         GlStateManager._activeTexture(33996);
         GL43.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
         GlStateManager._activeTexture(33984);
      }
   }

   public static void preWater() {
      if (usedDepthBuffers >= 2) {
         GlStateManager._activeTexture(33995);
         checkGLError("pre copy depth");
         GL43.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
         checkGLError("copy depth");
         GlStateManager._activeTexture(33984);
      }

      ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
   }

   public static void beginWater() {
      if (isRenderingWorld) {
         if (!isShadowPass) {
            renderDeferred();
            useProgram(ProgramWater);
            GlStateManager._enableBlend();
            GlStateManager._depthMask(true);
         } else {
            GlStateManager._depthMask(true);
         }
      }
   }

   public static void endWater() {
      if (isRenderingWorld) {
         if (isShadowPass) {
         }

         useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
      }
   }

   public static void applyHandDepth(Matrix4f viewIn) {
      if ((double)configHandDepthMul != 1.0) {
         viewIn.scale(1.0F, 1.0F, configHandDepthMul);
      }
   }

   public static void beginHand(boolean translucent) {
      if (translucent) {
         useProgram(ProgramHandWater);
      } else {
         useProgram(ProgramHand);
      }

      checkGLError("beginHand");
      checkFramebufferStatus("beginHand");
   }

   public static void endHand() {
      checkGLError("pre endHand");
      checkFramebufferStatus("pre endHand");
      GlStateManager._blendFunc(770, 771);
      checkGLError("endHand");
   }

   public static void beginFPOverlay() {
      GlStateManager._disableBlend();
   }

   public static void endFPOverlay() {
   }

   public static void glEnableWrapper(int cap) {
      GL43.glEnable(cap);
      if (cap == 3553) {
         enableTexture2D();
      } else if (cap == 2912) {
         enableFog();
      }
   }

   public static void glDisableWrapper(int cap) {
      GL43.glDisable(cap);
      if (cap == 3553) {
         disableTexture2D();
      } else if (cap == 2912) {
         disableFog();
      }
   }

   public static void sglEnableT2D(int cap) {
      GL43.glEnable(cap);
      enableTexture2D();
   }

   public static void sglDisableT2D(int cap) {
      GL43.glDisable(cap);
      disableTexture2D();
   }

   public static void sglEnableFog(int cap) {
      GL43.glEnable(cap);
      enableFog();
   }

   public static void sglDisableFog(int cap) {
      GL43.glDisable(cap);
      disableFog();
   }

   public static void enableTexture2D() {
      if (isRenderingSky) {
         useProgram(ProgramSkyTextured);
      } else if (activeProgram == ProgramBasic) {
         useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
      }
   }

   public static void disableTexture2D() {
      if (isRenderingSky) {
         useProgram(ProgramSkyBasic);
      } else if (activeProgram == ProgramTextured || activeProgram == ProgramTexturedLit) {
         useProgram(ProgramBasic);
      }
   }

   public static void pushProgram() {
      programStack.push(activeProgram);
   }

   public static void pushUseProgram(Program program) {
      pushProgram();
      useProgram(program);
   }

   public static void popProgram() {
      Program program = programStack.pop();
      useProgram(program);
   }

   public static void beginLeash() {
      pushProgram();
      useProgram(ProgramBasic);
   }

   public static void endLeash() {
      popProgram();
   }

   public static void beginLines() {
      pushProgram();
      useProgram(ProgramLine);
      setRenderStage(RenderStage.OUTLINE);
   }

   public static void endLines() {
      popProgram();
      setRenderStage(RenderStage.NONE);
   }

   public static void beginWaterMask() {
      GlStateManager.disableAlphaTest();
   }

   public static void endWaterMask() {
      GlStateManager.enableAlphaTest();
   }

   public static void enableFog() {
      fogEnabled = true;
      if (fogAllowed) {
         setProgramUniform1i(uniform_fogMode, fogMode);
         setProgramUniform1i(uniform_fogShape, fogShape);
         setProgramUniform1f(uniform_fogDensity, fogDensity);
         setProgramUniform1f(uniform_fogStart, fogStart);
         setProgramUniform1f(uniform_fogEnd, fogEnd);
         setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
      } else {
         setProgramUniform1f(uniform_fogDensity, 0.0F);
         setProgramUniform1f(uniform_fogStart, 1.7014117E38F);
         setProgramUniform1f(uniform_fogEnd, Float.MAX_VALUE);
      }
   }

   public static void disableFog() {
      fogEnabled = false;
      setProgramUniform1i(uniform_fogMode, 0);
      setProgramUniform1i(uniform_fogShape, 0);
      setProgramUniform1f(uniform_fogDensity, 0.0F);
      setProgramUniform1f(uniform_fogStart, 1.7014117E38F);
      setProgramUniform1f(uniform_fogEnd, Float.MAX_VALUE);
   }

   public static void setFogDensity(float value) {
      fogDensity = value;
      if (fogEnabled && fogAllowed) {
         setProgramUniform1f(uniform_fogDensity, value);
      }
   }

   public static void setFogStart(float value) {
      fogStart = value;
      if (fogEnabled && fogAllowed) {
         setProgramUniform1f(uniform_fogStart, value);
      }
   }

   public static void setFogEnd(float value) {
      fogEnd = value;
      if (fogEnabled && fogAllowed) {
         setProgramUniform1f(uniform_fogEnd, value);
      }
   }

   public static void sglFogi(int pname, int param) {
      GL11.glFogi(pname, param);
      if (pname == 2917) {
         fogMode = param;
         if (fogEnabled && fogAllowed) {
            setProgramUniform1i(uniform_fogMode, fogMode);
         }
      }
   }

   public static void enableLightmap() {
      lightmapEnabled = true;
      if (activeProgram == ProgramTextured) {
         useProgram(ProgramTexturedLit);
      }
   }

   public static void disableLightmap() {
      lightmapEnabled = false;
      if (activeProgram == ProgramTexturedLit) {
         useProgram(ProgramTextured);
      }
   }

   public static int getEntityData() {
      return entityData[entityDataIndex * 2];
   }

   public static int getEntityData2() {
      return entityData[entityDataIndex * 2 + 1];
   }

   public static int setEntityData1(int data1) {
      entityData[entityDataIndex * 2] = entityData[entityDataIndex * 2] & 65535 | data1 << 16;
      return data1;
   }

   public static int setEntityData2(int data2) {
      entityData[entityDataIndex * 2 + 1] = entityData[entityDataIndex * 2 + 1] & -65536 | data2 & 65535;
      return data2;
   }

   public static void pushEntity(int data0, int data1) {
      entityDataIndex++;
      entityData[entityDataIndex * 2] = data0 & 65535 | data1 << 16;
      entityData[entityDataIndex * 2 + 1] = 0;
   }

   public static void pushEntity(int data0) {
      entityDataIndex++;
      entityData[entityDataIndex * 2] = data0 & 65535;
      entityData[entityDataIndex * 2 + 1] = 0;
   }

   public static void pushEntity(Block block) {
      entityDataIndex++;
      int blockRenderType = block.m_49966_().m_60799_().ordinal();
      entityData[entityDataIndex * 2] = BuiltInRegistries.f_256975_.m_7447_(block) & 65535 | blockRenderType << 16;
      entityData[entityDataIndex * 2 + 1] = 0;
   }

   public static void popEntity() {
      entityData[entityDataIndex * 2] = 0;
      entityData[entityDataIndex * 2 + 1] = 0;
      entityDataIndex--;
   }

   public static void mcProfilerEndSection() {
      f_303183_.m_91307_().m_7238_();
   }

   public static String getShaderPackName() {
      if (shaderPack == null) {
         return null;
      } else {
         return shaderPack instanceof ShaderPackNone ? null : shaderPack.getName();
      }
   }

   public static InputStream getShaderPackResourceStream(String path) {
      return shaderPack == null ? null : shaderPack.getResourceAsStream(path);
   }

   public static void nextAntialiasingLevel(boolean forward) {
      if (forward) {
         configAntialiasingLevel += 2;
         if (configAntialiasingLevel > 4) {
            configAntialiasingLevel = 0;
         }
      } else {
         configAntialiasingLevel -= 2;
         if (configAntialiasingLevel < 0) {
            configAntialiasingLevel = 4;
         }
      }

      configAntialiasingLevel = configAntialiasingLevel / 2 * 2;
      configAntialiasingLevel = Config.limit(configAntialiasingLevel, 0, 4);
   }

   public static void checkShadersModInstalled() {
      try {
         Class e = Class.forName("shadersmod.transform.SMCClassTransformer");
      } catch (Throwable var1) {
         return;
      }

      throw new RuntimeException("Shaders Mod detected. Please remove it, OptiFine has built-in support for shaders.");
   }

   public static void resourcesReloaded() {
      loadShaderPackResources();
      reloadCustomTexturesLocation(customTexturesGbuffers);
      reloadCustomTexturesLocation(customTexturesComposite);
      reloadCustomTexturesLocation(customTexturesDeferred);
      reloadCustomTexturesLocation(customTexturesShadowcomp);
      reloadCustomTexturesLocation(customTexturesPrepare);
      if (shaderPackLoaded) {
         BlockAliases.resourcesReloaded();
         ItemAliases.resourcesReloaded();
         EntityAliases.resourcesReloaded();
      }
   }

   private static void loadShaderPackResources() {
      shaderPackResources = new HashMap();
      if (shaderPackLoaded) {
         List<String> listFiles = new ArrayList();
         String PREFIX = "/shaders/lang/";
         String EN_US = "en_us";
         String SUFFIX = ".lang";
         listFiles.add(PREFIX + EN_US + SUFFIX);
         listFiles.add(PREFIX + getLocaleUppercase(EN_US) + SUFFIX);
         if (!Config.getGameSettings().f_92075_.equals(EN_US)) {
            String language = Config.getGameSettings().f_92075_;
            listFiles.add(PREFIX + language + SUFFIX);
            listFiles.add(PREFIX + getLocaleUppercase(language) + SUFFIX);
         }

         try {
            for (String file : listFiles) {
               InputStream in = shaderPack.getResourceAsStream(file);
               if (in != null) {
                  Properties props = new PropertiesOrdered();
                  Lang.loadLocaleData(in, props);
                  in.close();

                  for (String key : props.keySet()) {
                     String value = props.getProperty(key);
                     shaderPackResources.put(key, value);
                  }
               }
            }
         } catch (IOException var12) {
            var12.printStackTrace();
         }
      }
   }

   private static String getLocaleUppercase(String name) {
      int pos = name.indexOf(95);
      return pos < 0 ? name : name.substring(0, pos) + name.substring(pos).toUpperCase(Locale.ROOT);
   }

   public static String translate(String key, String def) {
      String str = (String)shaderPackResources.get(key);
      return str == null ? def : str;
   }

   public static boolean isProgramPath(String path) {
      if (path == null) {
         return false;
      } else if (path.length() <= 0) {
         return false;
      } else {
         int pos = path.lastIndexOf("/");
         if (pos >= 0) {
            path = path.substring(pos + 1);
         }

         Program p = getProgram(path);
         return p != null;
      }
   }

   public static Program getProgram(String name) {
      return programs.getProgram(name);
   }

   public static void setItemToRenderMain(ItemStack itemToRenderMain) {
      itemToRenderMainTranslucent = isTranslucentBlock(itemToRenderMain);
   }

   public static void setItemToRenderOff(ItemStack itemToRenderOff) {
      itemToRenderOffTranslucent = isTranslucentBlock(itemToRenderOff);
   }

   public static boolean isItemToRenderMainTranslucent() {
      return itemToRenderMainTranslucent;
   }

   public static boolean isItemToRenderOffTranslucent() {
      return itemToRenderOffTranslucent;
   }

   public static boolean isBothHandsRendered() {
      return isHandRenderedMain && isHandRenderedOff;
   }

   private static boolean isTranslucentBlock(ItemStack stack) {
      if (stack == null) {
         return false;
      } else {
         Item item = stack.m_41720_();
         if (item == null) {
            return false;
         } else if (!(item instanceof BlockItem itemBlock)) {
            return false;
         } else {
            Block block = itemBlock.m_40614_();
            if (block == null) {
               return false;
            } else {
               RenderType blockRenderLayer = ItemBlockRenderTypes.m_109282_(block.m_49966_());
               return blockRenderLayer == RenderTypes.TRANSLUCENT;
            }
         }
      }
   }

   public static boolean isSkipRenderHand(InteractionHand hand) {
      return hand == InteractionHand.MAIN_HAND && skipRenderHandMain ? true : hand == InteractionHand.OFF_HAND && skipRenderHandOff;
   }

   public static boolean isRenderBothHands() {
      return !skipRenderHandMain && !skipRenderHandOff;
   }

   public static void setSkipRenderHands(boolean skipMain, boolean skipOff) {
      skipRenderHandMain = skipMain;
      skipRenderHandOff = skipOff;
   }

   public static void setHandsRendered(boolean handMain, boolean handOff) {
      isHandRenderedMain = handMain;
      isHandRenderedOff = handOff;
   }

   public static boolean isHandRenderedMain() {
      return isHandRenderedMain;
   }

   public static boolean isHandRenderedOff() {
      return isHandRenderedOff;
   }

   public static float getShadowRenderDistance() {
      return shadowDistanceRenderMul < 0.0F ? -1.0F : shadowMapHalfPlane * shadowDistanceRenderMul;
   }

   public static void beginRenderFirstPersonHand(boolean translucent) {
      isRenderingFirstPersonHand = true;
      if (translucent) {
         setRenderStage(RenderStage.HAND_TRANSLUCENT);
      } else {
         setRenderStage(RenderStage.HAND_SOLID);
      }
   }

   public static void endRenderFirstPersonHand() {
      isRenderingFirstPersonHand = false;
      setRenderStage(RenderStage.NONE);
   }

   public static boolean isRenderingFirstPersonHand() {
      return isRenderingFirstPersonHand;
   }

   public static void beginBeacon() {
      if (isRenderingWorld) {
         useProgram(ProgramBeaconBeam);
      }
   }

   public static void endBeacon() {
      if (isRenderingWorld) {
         useProgram(ProgramBlock);
      }
   }

   public static ClientLevel getCurrentWorld() {
      return currentWorld;
   }

   public static BlockPos getWorldCameraPosition() {
      return BlockPos.m_274561_(cameraPositionX + (double)cameraOffsetX, cameraPositionY, cameraPositionZ + (double)cameraOffsetZ);
   }

   public static boolean isCustomUniforms() {
      return customUniforms != null;
   }

   public static boolean canRenderQuads() {
      return hasGeometryShaders ? capabilities.GL_NV_geometry_shader4 : true;
   }

   public static boolean isOverlayDisabled() {
      return shaderPackLoaded;
   }

   public static boolean isRemapLightmap() {
      return shaderPackLoaded;
   }

   public static boolean isEffectsModelView() {
      return shaderPackLoaded;
   }

   public static void flushRenderBuffers() {
      RenderUtils.flushRenderBuffers();
   }

   public static void setRenderStage(RenderStage stage) {
      if (shaderPackLoaded) {
         renderStage = stage;
         uniform_renderStage.setValue(stage.ordinal());
      }
   }

   public static void setModelView(Matrix4f matrixIn) {
      if (!matrixIn.equals(lastModelView)) {
         lastModelView.set(matrixIn);
         MathUtils.write(matrixIn, modelView);
         SMath.invertMat4FBFA(modelViewInverse.position(0), modelView.position(0), faModelViewInverse, faModelView);
         modelView.position(0);
         modelViewInverse.position(0);
         setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
         setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
      }
   }

   public static void setProjection(Matrix4f matrixIn) {
      if (!matrixIn.equals(lastProjection)) {
         lastProjection.set(matrixIn);
         MathUtils.write(matrixIn, projection);
         SMath.invertMat4FBFA(projectionInverse.position(0), projection.position(0), faProjectionInverse, faProjection);
         projection.position(0);
         projectionInverse.position(0);
         setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
         setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
      }
   }

   public static void setModelViewMatrix(Matrix4f matrixIn) {
      uniform_modelViewMatrix.setValue(matrixIn);
      if (uniform_modelViewMatrixInverse.isDefined()) {
         Matrix4f matInv = new Matrix4f(matrixIn);
         matInv.m_81807_();
         uniform_modelViewMatrixInverse.setValue(matInv);
      }

      if (uniform_normalMatrix.isDefined()) {
         Matrix3f normalMat3 = new Matrix3f(matrixIn);
         normalMat3.m_81807_();
         normalMat3.transpose();
         uniform_normalMatrix.setValue(normalMat3);
      }
   }

   public static void setProjectionMatrix(Matrix4f matrixIn) {
      uniform_projectionMatrix.setValue(matrixIn);
      if (uniform_projectionMatrixInverse.isDefined()) {
         Matrix4f matInv = new Matrix4f(matrixIn);
         matInv.m_81807_();
         uniform_projectionMatrixInverse.setValue(matInv);
      }
   }

   public static void setTextureMatrix(Matrix4f matrixIn) {
      uniform_textureMatrix.setValue(matrixIn);
   }

   public static void setColorModulator(float[] cols) {
      if (cols != null && cols.length >= 4) {
         uniform_colorModulator.setValue(cols[0], cols[1], cols[2], cols[3]);
      }
   }

   public static void setFogAllowed(boolean fogAllowed) {
      Shaders.fogAllowed = fogAllowed;
   }

   public static void setDefaultAttribColor() {
      setDefaultAttribColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private static void setDefaultAttribLightmap() {
      setDefaultAttribLightmap((short)240, (short)240);
   }

   private static void setDefaultAttribNormal() {
      setDefaultAttribNormal(0.0F, 1.0F, 0.0F);
   }

   public static void setDefaultAttribColor(float r, float g, float b, float a) {
      GL30.glVertexAttrib4f(VertexFormatElement.f_336914_.getAttributeIndex(), r, g, b, a);
   }

   private static void setDefaultAttribLightmap(short su, short sv) {
      GL30.glVertexAttrib2s(VertexFormatElement.f_337050_.getAttributeIndex(), su, sv);
   }

   private static void setDefaultAttribNormal(float x, float y, float z) {
      GL30.glVertexAttrib3f(VertexFormatElement.f_336839_.getAttributeIndex(), x, y, z);
   }

   private static int getBossBattle() {
      String bossName = f_303183_.f_91065_.m_93090_().getBossName();
      if (bossName == null) {
         return 0;
      } else if (bossName.equals("entity.minecraft.ender_dragon")) {
         return 2;
      } else if (bossName.equals("entity.minecraft.wither")) {
         return 3;
      } else {
         return bossName.equals("event.minecraft.raid") ? 4 : 1;
      }
   }

   public static void setDarknessFactor(float darknessFactor) {
      Shaders.darknessFactor = darknessFactor;
   }

   public static void setDarknessLightFactor(float darknessLightFactor) {
      Shaders.darknessLightFactor = darknessLightFactor;
   }
}
