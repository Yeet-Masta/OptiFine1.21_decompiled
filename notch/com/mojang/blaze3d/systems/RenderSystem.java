package com.mojang.blaze3d.systems;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.logging.LogUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.src.C_141524_;
import net.minecraft.src.C_141721_;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_201410_;
import net.minecraft.src.C_213334_;
import net.minecraft.src.C_276405_;
import net.minecraft.src.C_3104_;
import net.minecraft.src.C_3185_;
import net.minecraft.src.C_3188_;
import net.minecraft.src.C_3383_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4468_;
import net.minecraft.src.C_4490_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_5322_;
import net.minecraft.src.C_238509_.C_238510_;
import net.optifine.Config;
import net.optifine.CustomGuis;
import net.optifine.shaders.Shaders;
import net.optifine.util.TextureUtils;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.slf4j.Logger;

@C_141524_
public class RenderSystem {
   static final Logger LOGGER = LogUtils.getLogger();
   private static final ConcurrentLinkedQueue<C_3104_> recordingQueue = Queues.newConcurrentLinkedQueue();
   private static final C_3185_ RENDER_THREAD_TESSELATOR = new C_3185_(1536);
   private static final int MINIMUM_ATLAS_TEXTURE_SIZE = 1024;
   @Nullable
   private static Thread renderThread;
   private static int MAX_SUPPORTED_TEXTURE_SIZE = -1;
   private static boolean isInInit;
   private static double lastDrawTime = Double.MIN_VALUE;
   private static final RenderSystem.C_141293_ sharedSequential = new RenderSystem.C_141293_(1, 1, IntConsumer::accept);
   private static final RenderSystem.C_141293_ sharedSequentialQuad = new RenderSystem.C_141293_(4, 6, (consumerIn, positionIn) -> {
      consumerIn.accept(positionIn + 0);
      consumerIn.accept(positionIn + 1);
      consumerIn.accept(positionIn + 2);
      consumerIn.accept(positionIn + 2);
      consumerIn.accept(positionIn + 3);
      consumerIn.accept(positionIn + 0);
   });
   private static final RenderSystem.C_141293_ sharedSequentialLines = new RenderSystem.C_141293_(4, 6, (consumerIn, positionIn) -> {
      consumerIn.accept(positionIn + 0);
      consumerIn.accept(positionIn + 1);
      consumerIn.accept(positionIn + 2);
      consumerIn.accept(positionIn + 3);
      consumerIn.accept(positionIn + 2);
      consumerIn.accept(positionIn + 1);
   });
   private static Matrix4f projectionMatrix = new Matrix4f();
   private static Matrix4f savedProjectionMatrix = new Matrix4f();
   private static C_276405_ vertexSorting = C_276405_.f_276450_;
   private static C_276405_ savedVertexSorting = C_276405_.f_276450_;
   private static final Matrix4fStack modelViewStack = new Matrix4fStack(16);
   private static Matrix4f modelViewMatrix = new Matrix4f();
   private static Matrix4f textureMatrix = new Matrix4f();
   private static final int[] shaderTextures = new int[12];
   private static final float[] shaderColor = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
   private static float shaderGlintAlpha = 1.0F;
   private static float shaderFogStart;
   private static float shaderFogEnd = 1.0F;
   private static final float[] shaderFogColor = new float[]{0.0F, 0.0F, 0.0F, 0.0F};
   private static C_201410_ shaderFogShape = C_201410_.SPHERE;
   private static final Vector3f[] shaderLightDirections = new Vector3f[2];
   private static float shaderGameTime;
   private static float shaderLineWidth = 1.0F;
   private static String apiDescription = "Unknown";
   @Nullable
   private static C_141721_ shader;
   private static final AtomicLong pollEventsWaitStart = new AtomicLong();
   private static final AtomicBoolean pollingEvents = new AtomicBoolean(false);
   private static boolean fogAllowed = true;
   private static boolean colorToAttribute = false;

   public static void initRenderThread() {
      if (renderThread != null) {
         throw new IllegalStateException("Could not initialize render thread");
      } else {
         renderThread = Thread.currentThread();
      }
   }

   public static boolean isOnRenderThread() {
      return Thread.currentThread() == renderThread;
   }

   public static boolean isOnRenderThreadOrInit() {
      return isInInit || isOnRenderThread();
   }

   public static void assertOnRenderThreadOrInit() {
      if (!isInInit && !isOnRenderThread()) {
         throw constructThreadException();
      }
   }

   public static void assertOnRenderThread() {
      if (!isOnRenderThread()) {
         throw constructThreadException();
      }
   }

   private static IllegalStateException constructThreadException() {
      return new IllegalStateException("Rendersystem called from wrong thread");
   }

   public static void recordRenderCall(C_3104_ renderCallIn) {
      recordingQueue.add(renderCallIn);
   }

   private static void pollEvents() {
      pollEventsWaitStart.set(C_5322_.m_137550_());
      pollingEvents.set(true);
      GLFW.glfwPollEvents();
      pollingEvents.set(false);
   }

   public static boolean isFrozenAtPollEvents() {
      return pollingEvents.get() && C_5322_.m_137550_() - pollEventsWaitStart.get() > 200L;
   }

   public static void flipFrame(long handleIn) {
      pollEvents();
      replayQueue();
      C_3185_.m_85913_().m_339098_();
      GLFW.glfwSwapBuffers(handleIn);
      pollEvents();
   }

   public static void replayQueue() {
      while (!recordingQueue.isEmpty()) {
         C_3104_ rendercall = (C_3104_)recordingQueue.poll();
         rendercall.m_83909_();
      }
   }

   public static void limitDisplayFPS(int fpsLimitIn) {
      double d0 = lastDrawTime + 1.0 / (double)fpsLimitIn;

      double d1;
      for (d1 = GLFW.glfwGetTime(); d1 < d0; d1 = GLFW.glfwGetTime()) {
         GLFW.glfwWaitEventsTimeout(d0 - d1);
      }

      lastDrawTime = d1;
   }

   public static void disableDepthTest() {
      assertOnRenderThread();
      GlStateManager._disableDepthTest();
   }

   public static void enableDepthTest() {
      GlStateManager._enableDepthTest();
   }

   public static void enableScissor(int x, int y, int width, int height) {
      GlStateManager._enableScissorTest();
      GlStateManager._scissorBox(x, y, width, height);
   }

   public static void disableScissor() {
      GlStateManager._disableScissorTest();
   }

   public static void depthFunc(int depthFunc) {
      assertOnRenderThread();
      GlStateManager._depthFunc(depthFunc);
   }

   public static void depthMask(boolean depthMask) {
      assertOnRenderThread();
      GlStateManager._depthMask(depthMask);
   }

   public static void enableBlend() {
      assertOnRenderThread();
      GlStateManager._enableBlend();
   }

   public static void disableBlend() {
      assertOnRenderThread();
      GlStateManager._disableBlend();
   }

   public static void blendFunc(GlStateManager.SourceFactor srcFactor, GlStateManager.DestFactor dstFactor) {
      assertOnRenderThread();
      GlStateManager._blendFunc(srcFactor.value, dstFactor.value);
   }

   public static void blendFunc(int srcFactor, int dstFactor) {
      assertOnRenderThread();
      GlStateManager._blendFunc(srcFactor, dstFactor);
   }

   public static void blendFuncSeparate(
      GlStateManager.SourceFactor srcFactor,
      GlStateManager.DestFactor dstFactor,
      GlStateManager.SourceFactor srcFactorAlpha,
      GlStateManager.DestFactor dstFactorAlpha
   ) {
      assertOnRenderThread();
      GlStateManager._blendFuncSeparate(srcFactor.value, dstFactor.value, srcFactorAlpha.value, dstFactorAlpha.value);
   }

   public static void blendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
      assertOnRenderThread();
      GlStateManager._blendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
   }

   public static void blendEquation(int blendEquation) {
      assertOnRenderThread();
      GlStateManager._blendEquation(blendEquation);
   }

   public static void enableCull() {
      assertOnRenderThread();
      GlStateManager._enableCull();
   }

   public static void disableCull() {
      assertOnRenderThread();
      GlStateManager._disableCull();
   }

   public static void polygonMode(int face, int mode) {
      assertOnRenderThread();
      GlStateManager._polygonMode(face, mode);
   }

   public static void enablePolygonOffset() {
      assertOnRenderThread();
      GlStateManager._enablePolygonOffset();
   }

   public static void disablePolygonOffset() {
      assertOnRenderThread();
      GlStateManager._disablePolygonOffset();
   }

   public static void polygonOffset(float factor, float units) {
      assertOnRenderThread();
      GlStateManager._polygonOffset(factor, units);
   }

   public static void enableColorLogicOp() {
      assertOnRenderThread();
      GlStateManager._enableColorLogicOp();
   }

   public static void disableColorLogicOp() {
      assertOnRenderThread();
      GlStateManager._disableColorLogicOp();
   }

   public static void logicOp(GlStateManager.C_3128_ logicOp) {
      assertOnRenderThread();
      GlStateManager._logicOp(logicOp.f_84715_);
   }

   public static void activeTexture(int textureIn) {
      assertOnRenderThread();
      GlStateManager._activeTexture(textureIn);
   }

   public static void enableTexture() {
      assertOnRenderThread();
      GlStateManager.enableTexture();
   }

   public static void disableTexture() {
      assertOnRenderThread();
      GlStateManager.disableTexture();
   }

   public static void texParameter(int target, int parameterName, int parameter) {
      GlStateManager._texParameter(target, parameterName, parameter);
   }

   public static void deleteTexture(int textureIn) {
      GlStateManager._deleteTexture(textureIn);
   }

   public static void bindTextureForSetup(int textureIn) {
      bindTexture(textureIn);
   }

   public static void bindTexture(int textureIn) {
      GlStateManager._bindTexture(textureIn);
   }

   public static void viewport(int x, int y, int width, int height) {
      GlStateManager._viewport(x, y, width, height);
   }

   public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
      assertOnRenderThread();
      GlStateManager._colorMask(red, green, blue, alpha);
   }

   public static void stencilFunc(int func, int ref, int mask) {
      assertOnRenderThread();
      GlStateManager._stencilFunc(func, ref, mask);
   }

   public static void stencilMask(int mask) {
      assertOnRenderThread();
      GlStateManager._stencilMask(mask);
   }

   public static void stencilOp(int sfail, int dpfail, int dppass) {
      assertOnRenderThread();
      GlStateManager._stencilOp(sfail, dpfail, dppass);
   }

   public static void clearDepth(double depth) {
      GlStateManager._clearDepth(depth);
   }

   public static void clearColor(float red, float green, float blue, float alpha) {
      GlStateManager._clearColor(red, green, blue, alpha);
   }

   public static void clearStencil(int index) {
      assertOnRenderThread();
      GlStateManager._clearStencil(index);
   }

   public static void clear(int mask, boolean checkError) {
      GlStateManager._clear(mask, checkError);
   }

   public static void setShaderFogStart(float fogStartIn) {
      assertOnRenderThread();
      shaderFogStart = fogStartIn;
   }

   public static float getShaderFogStart() {
      if (!fogAllowed) {
         return Float.MAX_VALUE;
      } else {
         assertOnRenderThread();
         return shaderFogStart;
      }
   }

   public static void setShaderGlintAlpha(double alphaIn) {
      setShaderGlintAlpha((float)alphaIn);
   }

   public static void setShaderGlintAlpha(float alphaIn) {
      assertOnRenderThread();
      shaderGlintAlpha = alphaIn;
   }

   public static float getShaderGlintAlpha() {
      assertOnRenderThread();
      return shaderGlintAlpha;
   }

   public static void setShaderFogEnd(float fogEndIn) {
      assertOnRenderThread();
      shaderFogEnd = fogEndIn;
   }

   public static float getShaderFogEnd() {
      if (!fogAllowed) {
         return Float.MAX_VALUE;
      } else {
         assertOnRenderThread();
         return shaderFogEnd;
      }
   }

   public static void setShaderFogColor(float red, float green, float blue, float alpha) {
      assertOnRenderThread();
      shaderFogColor[0] = red;
      shaderFogColor[1] = green;
      shaderFogColor[2] = blue;
      shaderFogColor[3] = alpha;
   }

   public static void setShaderFogColor(float red, float green, float blue) {
      setShaderFogColor(red, green, blue, 1.0F);
   }

   public static float[] getShaderFogColor() {
      assertOnRenderThread();
      return shaderFogColor;
   }

   public static void setShaderFogShape(C_201410_ shapeIn) {
      assertOnRenderThread();
      shaderFogShape = shapeIn;
   }

   public static C_201410_ getShaderFogShape() {
      assertOnRenderThread();
      return shaderFogShape;
   }

   public static void setShaderLights(Vector3f dir1, Vector3f dir2) {
      assertOnRenderThread();
      shaderLightDirections[0] = dir1;
      shaderLightDirections[1] = dir2;
   }

   public static void setupShaderLights(C_141721_ shaderInstanceIn) {
      assertOnRenderThread();
      if (shaderInstanceIn.f_173313_ != null) {
         shaderInstanceIn.f_173313_.m_142276_(shaderLightDirections[0]);
      }

      if (shaderInstanceIn.f_173314_ != null) {
         shaderInstanceIn.f_173314_.m_142276_(shaderLightDirections[1]);
      }
   }

   public static void setShaderColor(float red, float green, float blue, float alpha) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> _setShaderColor(red, green, blue, alpha));
      } else {
         _setShaderColor(red, green, blue, alpha);
      }
   }

   private static void _setShaderColor(float red, float green, float blue, float alpha) {
      shaderColor[0] = red;
      shaderColor[1] = green;
      shaderColor[2] = blue;
      shaderColor[3] = alpha;
      if (colorToAttribute) {
         Shaders.setDefaultAttribColor(red, green, blue, alpha);
      }
   }

   public static float[] getShaderColor() {
      assertOnRenderThread();
      return shaderColor;
   }

   public static void drawElements(int mode, int count, int type) {
      assertOnRenderThread();
      GlStateManager._drawElements(mode, count, type, 0L);
   }

   public static void lineWidth(float widthIn) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> shaderLineWidth = widthIn);
      } else {
         shaderLineWidth = widthIn;
      }
   }

   public static float getShaderLineWidth() {
      assertOnRenderThread();
      return shaderLineWidth;
   }

   public static void pixelStore(int pname, int param) {
      GlStateManager._pixelStore(pname, param);
   }

   public static void readPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) {
      assertOnRenderThread();
      GlStateManager._readPixels(x, y, width, height, format, type, pixels);
   }

   public static void getString(int nameIn, Consumer<String> consumerIn) {
      assertOnRenderThread();
      consumerIn.accept(GlStateManager._getString(nameIn));
   }

   public static String getBackendDescription() {
      return String.format(Locale.ROOT, "LWJGL version %s", GLX._getLWJGLVersion());
   }

   public static String getApiDescription() {
      return apiDescription;
   }

   public static C_238510_ initBackendSystem() {
      return GLX._initGlfw()::getAsLong;
   }

   public static void initRenderer(int debugVerbosityIn, boolean debugSyncIn) {
      GLX._init(debugVerbosityIn, debugSyncIn);
      apiDescription = GLX.getOpenGLVersionString();
   }

   public static void setErrorCallback(GLFWErrorCallbackI errorCallbackIn) {
      GLX._setGlfwErrorCallback(errorCallbackIn);
   }

   public static void renderCrosshair(int heightIn) {
      assertOnRenderThread();
      GLX._renderCrosshair(heightIn, true, true, true);
   }

   public static String getCapsString() {
      assertOnRenderThread();
      return "Using framebuffer using OpenGL 3.2";
   }

   public static void setupDefaultState(int x, int y, int width, int height) {
      GlStateManager._clearDepth(1.0);
      GlStateManager._enableDepthTest();
      GlStateManager._depthFunc(515);
      projectionMatrix.identity();
      savedProjectionMatrix.identity();
      modelViewMatrix.identity();
      textureMatrix.identity();
      GlStateManager._viewport(x, y, width, height);
   }

   public static int maxSupportedTextureSize() {
      if (MAX_SUPPORTED_TEXTURE_SIZE == -1) {
         assertOnRenderThreadOrInit();
         int maxSize = TextureUtils.getGLMaximumTextureSize();
         if (maxSize > 0) {
            MAX_SUPPORTED_TEXTURE_SIZE = maxSize;
            return MAX_SUPPORTED_TEXTURE_SIZE;
         }

         int i = GlStateManager._getInteger(3379);

         for (int j = Math.max(32768, i); j >= 1024; j >>= 1) {
            GlStateManager._texImage2D(32868, 0, 6408, j, j, 0, 6408, 5121, null);
            int k = GlStateManager._getTexLevelParameter(32868, 0, 4096);
            if (k != 0) {
               MAX_SUPPORTED_TEXTURE_SIZE = j;
               return j;
            }
         }

         MAX_SUPPORTED_TEXTURE_SIZE = Math.max(i, 1024);
         LOGGER.info("Failed to determine maximum texture size by probing, trying GL_MAX_TEXTURE_SIZE = {}", MAX_SUPPORTED_TEXTURE_SIZE);
      }

      return MAX_SUPPORTED_TEXTURE_SIZE;
   }

   public static void glBindBuffer(int target, int buffer) {
      GlStateManager._glBindBuffer(target, buffer);
   }

   public static void glBindVertexArray(int arrayIn) {
      GlStateManager._glBindVertexArray(arrayIn);
   }

   public static void glBufferData(int target, ByteBuffer data, int usage) {
      assertOnRenderThreadOrInit();
      GlStateManager._glBufferData(target, data, usage);
   }

   public static void glDeleteBuffers(int buffer) {
      assertOnRenderThread();
      GlStateManager._glDeleteBuffers(buffer);
   }

   public static void glDeleteVertexArrays(int array) {
      assertOnRenderThread();
      GlStateManager._glDeleteVertexArrays(array);
   }

   public static void glUniform1i(int location, int value) {
      assertOnRenderThread();
      GlStateManager._glUniform1i(location, value);
   }

   public static void glUniform1(int location, IntBuffer value) {
      assertOnRenderThread();
      GlStateManager._glUniform1(location, value);
   }

   public static void glUniform2(int location, IntBuffer value) {
      assertOnRenderThread();
      GlStateManager._glUniform2(location, value);
   }

   public static void glUniform3(int location, IntBuffer value) {
      assertOnRenderThread();
      GlStateManager._glUniform3(location, value);
   }

   public static void glUniform4(int location, IntBuffer value) {
      assertOnRenderThread();
      GlStateManager._glUniform4(location, value);
   }

   public static void glUniform1(int location, FloatBuffer value) {
      assertOnRenderThread();
      GlStateManager._glUniform1(location, value);
   }

   public static void glUniform2(int location, FloatBuffer value) {
      assertOnRenderThread();
      GlStateManager._glUniform2(location, value);
   }

   public static void glUniform3(int location, FloatBuffer value) {
      assertOnRenderThread();
      GlStateManager._glUniform3(location, value);
   }

   public static void glUniform4(int location, FloatBuffer value) {
      assertOnRenderThread();
      GlStateManager._glUniform4(location, value);
   }

   public static void glUniformMatrix2(int location, boolean transpose, FloatBuffer value) {
      assertOnRenderThread();
      GlStateManager._glUniformMatrix2(location, transpose, value);
   }

   public static void glUniformMatrix3(int location, boolean transpose, FloatBuffer value) {
      assertOnRenderThread();
      GlStateManager._glUniformMatrix3(location, transpose, value);
   }

   public static void glUniformMatrix4(int location, boolean transpose, FloatBuffer value) {
      assertOnRenderThread();
      GlStateManager._glUniformMatrix4(location, transpose, value);
   }

   public static void setupOverlayColor(int textureIn, int colorIn) {
      assertOnRenderThread();
      setShaderTexture(1, textureIn);
   }

   public static void teardownOverlayColor() {
      assertOnRenderThread();
      setShaderTexture(1, 0);
   }

   public static void setupLevelDiffuseLighting(Vector3f vec1, Vector3f vec2) {
      assertOnRenderThread();
      setShaderLights(vec1, vec2);
   }

   public static void setupGuiFlatDiffuseLighting(Vector3f vec1, Vector3f vec2) {
      assertOnRenderThread();
      GlStateManager.setupGuiFlatDiffuseLighting(vec1, vec2);
   }

   public static void setupGui3DDiffuseLighting(Vector3f vec1, Vector3f vec2) {
      assertOnRenderThread();
      GlStateManager.setupGui3DDiffuseLighting(vec1, vec2);
   }

   public static void beginInitialization() {
      isInInit = true;
   }

   public static void finishInitialization() {
      isInInit = false;
      if (!recordingQueue.isEmpty()) {
         replayQueue();
      }

      if (!recordingQueue.isEmpty()) {
         throw new IllegalStateException("Recorded to render queue during initialization");
      }
   }

   public static void glGenBuffers(Consumer<Integer> consumerIn) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> consumerIn.accept(GlStateManager._glGenBuffers()));
      } else {
         consumerIn.accept(GlStateManager._glGenBuffers());
      }
   }

   public static void glGenVertexArrays(Consumer<Integer> consumerIn) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> consumerIn.accept(GlStateManager._glGenVertexArrays()));
      } else {
         consumerIn.accept(GlStateManager._glGenVertexArrays());
      }
   }

   public static C_3185_ renderThreadTesselator() {
      assertOnRenderThread();
      return RENDER_THREAD_TESSELATOR;
   }

   public static void defaultBlendFunc() {
      blendFuncSeparate(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
   }

   @Deprecated
   public static void runAsFancy(Runnable runnableIn) {
      boolean flag = C_3391_.m_91085_();
      if (!flag) {
         runnableIn.run();
      } else {
         C_213334_<C_3383_> optioninstance = C_3391_.m_91087_().f_91066_.m_232060_();
         C_3383_ graphicsstatus = optioninstance.m_231551_();
         optioninstance.m_231514_(C_3383_.FANCY);
         runnableIn.run();
         optioninstance.m_231514_(graphicsstatus);
      }
   }

   public static void setShader(Supplier<C_141721_> shaderIn) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> shader = (C_141721_)shaderIn.get());
      } else {
         shader = (C_141721_)shaderIn.get();
      }
   }

   @Nullable
   public static C_141721_ getShader() {
      assertOnRenderThread();
      return shader;
   }

   public static void setShaderTexture(int textureUnitIn, C_5265_ locIn) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> _setShaderTexture(textureUnitIn, locIn));
      } else {
         _setShaderTexture(textureUnitIn, locIn);
      }
   }

   public static void _setShaderTexture(int textureUnitIn, C_5265_ locIn) {
      if (Config.isCustomGuis()) {
         locIn = CustomGuis.getTextureLocation(locIn);
      }

      if (textureUnitIn >= 0 && textureUnitIn < shaderTextures.length) {
         C_4490_ texturemanager = C_3391_.m_91087_().m_91097_();
         C_4468_ abstracttexture = texturemanager.m_118506_(locIn);
         shaderTextures[textureUnitIn] = abstracttexture.m_117963_();
      }
   }

   public static void setShaderTexture(int textureUnitIn, int textureIn) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> _setShaderTexture(textureUnitIn, textureIn));
      } else {
         _setShaderTexture(textureUnitIn, textureIn);
      }
   }

   public static void _setShaderTexture(int textureUnitIn, int textureIn) {
      if (textureUnitIn >= 0 && textureUnitIn < shaderTextures.length) {
         shaderTextures[textureUnitIn] = textureIn;
      }
   }

   public static int getShaderTexture(int textureUnitIn) {
      assertOnRenderThread();
      return textureUnitIn >= 0 && textureUnitIn < shaderTextures.length ? shaderTextures[textureUnitIn] : 0;
   }

   public static void setProjectionMatrix(Matrix4f matrixIn, C_276405_ sortingIn) {
      Matrix4f matrix4f = new Matrix4f(matrixIn);
      if (!isOnRenderThread()) {
         recordRenderCall(() -> {
            projectionMatrix = matrix4f;
            vertexSorting = sortingIn;
         });
      } else {
         projectionMatrix = matrix4f;
         vertexSorting = sortingIn;
      }
   }

   public static void setTextureMatrix(Matrix4f matrixIn) {
      Matrix4f matrix4f = new Matrix4f(matrixIn);
      if (!isOnRenderThread()) {
         recordRenderCall(() -> textureMatrix = matrix4f);
      } else {
         textureMatrix = matrix4f;
      }
   }

   public static void resetTextureMatrix() {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> textureMatrix.identity());
      } else {
         textureMatrix.identity();
      }
   }

   public static void applyModelViewMatrix() {
      Matrix4f matrix4f = modelViewStack;
      if (!isOnRenderThread()) {
         recordRenderCall(() -> modelViewMatrix.set(matrix4f));
      } else {
         modelViewMatrix.set(matrix4f);
      }
   }

   public static void backupProjectionMatrix() {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> _backupProjectionMatrix());
      } else {
         _backupProjectionMatrix();
      }
   }

   private static void _backupProjectionMatrix() {
      savedProjectionMatrix = projectionMatrix;
      savedVertexSorting = vertexSorting;
   }

   public static void restoreProjectionMatrix() {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> _restoreProjectionMatrix());
      } else {
         _restoreProjectionMatrix();
      }
   }

   private static void _restoreProjectionMatrix() {
      projectionMatrix = savedProjectionMatrix;
      vertexSorting = savedVertexSorting;
   }

   public static Matrix4f getProjectionMatrix() {
      assertOnRenderThread();
      return projectionMatrix;
   }

   public static Matrix4f getModelViewMatrix() {
      assertOnRenderThread();
      return modelViewMatrix;
   }

   public static Matrix4fStack getModelViewStack() {
      return modelViewStack;
   }

   public static Matrix4f getTextureMatrix() {
      assertOnRenderThread();
      return textureMatrix;
   }

   public static RenderSystem.C_141293_ getSequentialBuffer(C_3188_.C_141549_ modeIn) {
      assertOnRenderThread();

      return switch (modeIn) {
         case QUADS -> sharedSequentialQuad;
         case LINES -> sharedSequentialLines;
         default -> sharedSequential;
      };
   }

   public static void setShaderGameTime(long gameTimeIn, float partialTicks) {
      float f = ((float)(gameTimeIn % 24000L) + partialTicks) / 24000.0F;
      if (!isOnRenderThread()) {
         recordRenderCall(() -> shaderGameTime = f);
      } else {
         shaderGameTime = f;
      }
   }

   public static float getShaderGameTime() {
      assertOnRenderThread();
      return shaderGameTime;
   }

   public static C_276405_ getVertexSorting() {
      assertOnRenderThread();
      return vertexSorting;
   }

   public static void setFogAllowed(boolean fogAllowed) {
      RenderSystem.fogAllowed = fogAllowed;
      if (Config.isShaders()) {
         Shaders.setFogAllowed(fogAllowed);
      }
   }

   public static boolean isFogAllowed() {
      return fogAllowed;
   }

   public static void setColorToAttribute(boolean colorToAttribute) {
      if (Config.isShaders()) {
         if (RenderSystem.colorToAttribute != colorToAttribute) {
            RenderSystem.colorToAttribute = colorToAttribute;
            if (colorToAttribute) {
               Shaders.setDefaultAttribColor(shaderColor[0], shaderColor[1], shaderColor[2], shaderColor[3]);
            } else {
               Shaders.setDefaultAttribColor();
            }
         }
      }
   }

   public static final class C_141293_ {
      private final int f_157465_;
      private final int f_157466_;
      private final RenderSystem.C_141293_.C_141294_ f_157467_;
      private int f_157468_;
      private C_3188_.C_141548_ f_157469_ = C_3188_.C_141548_.SHORT;
      private int f_157470_;

      C_141293_(int vertexStrideIn, int indexStrideIn, RenderSystem.C_141293_.C_141294_ generatorIn) {
         this.f_157465_ = vertexStrideIn;
         this.f_157466_ = indexStrideIn;
         this.f_157467_ = generatorIn;
      }

      public boolean m_221944_(int indexCountIn) {
         return indexCountIn <= this.f_157470_;
      }

      public void m_221946_(int indexCountIn) {
         if (this.f_157468_ == 0) {
            this.f_157468_ = GlStateManager._glGenBuffers();
         }

         GlStateManager._glBindBuffer(34963, this.f_157468_);
         this.m_157476_(indexCountIn);
      }

      public void m_157476_(int indexCountIn) {
         if (!this.m_221944_(indexCountIn)) {
            indexCountIn = C_188_.m_144941_(indexCountIn * 2, this.f_157466_);
            RenderSystem.LOGGER.debug("Growing IndexBuffer: Old limit {}, new limit {}.", this.f_157470_, indexCountIn);
            int i = indexCountIn / this.f_157466_;
            int j = i * this.f_157465_;
            C_3188_.C_141548_ vertexformat$indextype = C_3188_.C_141548_.m_166933_(j);
            int k = C_188_.m_144941_(indexCountIn * vertexformat$indextype.f_166924_, 4);
            GlStateManager._glBufferData(34963, (long)k, 35048);
            ByteBuffer bytebuffer = GlStateManager._glMapBuffer(34963, 35001);
            if (bytebuffer == null) {
               throw new RuntimeException("Failed to map GL buffer");
            }

            this.f_157469_ = vertexformat$indextype;
            it.unimi.dsi.fastutil.ints.IntConsumer intconsumer = this.m_157478_(bytebuffer);

            for (int l = 0; l < indexCountIn; l += this.f_157466_) {
               this.f_157467_.m_157487_(intconsumer, l * this.f_157465_ / this.f_157466_);
            }

            GlStateManager._glUnmapBuffer(34963);
            this.f_157470_ = indexCountIn;
         }
      }

      private it.unimi.dsi.fastutil.ints.IntConsumer m_157478_(ByteBuffer byteBufferIn) {
         switch (this.f_157469_) {
            case SHORT:
               return valueIn -> byteBufferIn.putShort((short)valueIn);
            case INT:
            default:
               return byteBufferIn::putInt;
         }
      }

      public C_3188_.C_141548_ m_157483_() {
         return this.f_157469_;
      }

      interface C_141294_ {
         void m_157487_(it.unimi.dsi.fastutil.ints.IntConsumer var1, int var2);
      }
   }
}
