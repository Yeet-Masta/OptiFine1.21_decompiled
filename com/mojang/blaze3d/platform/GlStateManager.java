package com.mojang.blaze3d.platform;

import com.google.common.base.Charsets;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.render.GlCullState;
import net.optifine.shaders.Shaders;
import net.optifine.util.LockCounter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBDrawBuffersBlend;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32C;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

@DontObfuscate
public class GlStateManager {
   private static boolean ON_LINUX = Util.m_137581_() == Util.OS.LINUX;
   public static int TEXTURE_COUNT;
   private static GlStateManager.BlendState BLEND = new GlStateManager.BlendState();
   private static GlStateManager.DepthState DEPTH = new GlStateManager.DepthState();
   private static GlStateManager.CullState CULL = new GlStateManager.CullState();
   private static GlStateManager.PolygonOffsetState POLY_OFFSET = new GlStateManager.PolygonOffsetState();
   private static GlStateManager.ColorLogicState COLOR_LOGIC = new GlStateManager.ColorLogicState();
   private static GlStateManager.StencilState STENCIL = new GlStateManager.StencilState();
   private static GlStateManager.ScissorState SCISSOR = new GlStateManager.ScissorState();
   private static int activeTexture;
   private static GlStateManager.TextureState[] TEXTURES = (GlStateManager.TextureState[])IntStream.range(0, 32)
      .mapToObj(indexIn -> new GlStateManager.TextureState())
      .toArray(GlStateManager.TextureState[]::new);
   private static GlStateManager.ColorMask COLOR_MASK = new GlStateManager.ColorMask();
   private static boolean alphaTest = false;
   private static int alphaTestFunc = 519;
   private static float alphaTestRef = 0.0F;
   private static LockCounter alphaLock = new LockCounter();
   private static GlAlphaState alphaLockState = new GlAlphaState();
   private static LockCounter blendLock = new LockCounter();
   private static GlBlendState blendLockState = new GlBlendState();
   private static LockCounter cullLock = new LockCounter();
   private static GlCullState cullLockState = new GlCullState();
   public static boolean vboRegions;
   public static int GL_COPY_READ_BUFFER;
   public static int GL_COPY_WRITE_BUFFER;
   public static int GL_ARRAY_BUFFER;
   public static int GL_STATIC_DRAW;
   public static int GL_QUADS;
   public static int GL_TRIANGLES;
   public static int GL_TEXTURE0;
   public static int GL_TEXTURE1;
   public static int GL_TEXTURE2;
   private static int framebufferRead;
   private static int framebufferDraw;
   private static int[] IMAGE_TEXTURES = new int[8];
   private static int glProgram = 0;
   public static float lastBrightnessX = 0.0F;
   public static float lastBrightnessY = 0.0F;

   public static void disableAlphaTest() {
      if (alphaLock.isLocked()) {
         alphaLockState.setDisabled();
      } else {
         alphaTest = false;
         applyAlphaTest();
      }
   }

   public static void enableAlphaTest() {
      if (alphaLock.isLocked()) {
         alphaLockState.setEnabled();
      } else {
         alphaTest = true;
         applyAlphaTest();
      }
   }

   public static void alphaFunc(int func, float ref) {
      if (alphaLock.isLocked()) {
         alphaLockState.setFuncRef(func, ref);
      } else {
         alphaTestFunc = func;
         alphaTestRef = ref;
         applyAlphaTest();
      }
   }

   public static void applyAlphaTest() {
      if (Config.isShaders()) {
         if (alphaTest && alphaTestFunc == 516) {
            Shaders.uniform_alphaTestRef.setValue(alphaTestRef);
         } else {
            Shaders.uniform_alphaTestRef.setValue(0.0F);
         }
      }
   }

   public static void _disableScissorTest() {
      RenderSystem.assertOnRenderThreadOrInit();
      SCISSOR.f_84732_.m_84589_();
   }

   public static void _enableScissorTest() {
      RenderSystem.assertOnRenderThreadOrInit();
      SCISSOR.f_84732_.m_84592_();
   }

   public static void _scissorBox(int x, int y, int width, int height) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL20.glScissor(x, y, width, height);
   }

   public static void _disableDepthTest() {
      RenderSystem.assertOnRenderThreadOrInit();
      DEPTH.f_84626_.m_84589_();
   }

   public static void _enableDepthTest() {
      RenderSystem.assertOnRenderThreadOrInit();
      DEPTH.f_84626_.m_84592_();
   }

   public static void _depthFunc(int depthFunc) {
      RenderSystem.assertOnRenderThreadOrInit();
      if (depthFunc != DEPTH.f_84628_) {
         DEPTH.f_84628_ = depthFunc;
         GL11.glDepthFunc(depthFunc);
      }
   }

   public static void _depthMask(boolean flagIn) {
      RenderSystem.assertOnRenderThread();
      if (flagIn != DEPTH.f_84627_) {
         DEPTH.f_84627_ = flagIn;
         GL11.glDepthMask(flagIn);
      }
   }

   public static void _disableBlend() {
      RenderSystem.assertOnRenderThread();
      if (blendLock.isLocked()) {
         blendLockState.setDisabled();
      } else {
         BLEND.f_84577_.m_84589_();
      }
   }

   public static void _enableBlend() {
      RenderSystem.assertOnRenderThread();
      if (blendLock.isLocked()) {
         blendLockState.setEnabled();
      } else {
         BLEND.f_84577_.m_84592_();
      }
   }

   public static void _blendFunc(int srcFactor, int dstFactor) {
      RenderSystem.assertOnRenderThread();
      if (blendLock.isLocked()) {
         blendLockState.setFactors(srcFactor, dstFactor);
      } else {
         if (srcFactor != BLEND.f_84578_ || dstFactor != BLEND.f_84579_ || srcFactor != BLEND.f_84580_ || dstFactor != BLEND.f_84581_) {
            BLEND.f_84578_ = srcFactor;
            BLEND.f_84579_ = dstFactor;
            BLEND.f_84580_ = srcFactor;
            BLEND.f_84581_ = dstFactor;
            if (Config.isShaders()) {
               Shaders.uniform_blendFunc.setValue(srcFactor, dstFactor, srcFactor, dstFactor);
            }

            GL11.glBlendFunc(srcFactor, dstFactor);
         }
      }
   }

   public static void _blendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
      RenderSystem.assertOnRenderThread();
      if (blendLock.isLocked()) {
         blendLockState.setFactors(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
      } else {
         if (srcFactor != BLEND.f_84578_ || dstFactor != BLEND.f_84579_ || srcFactorAlpha != BLEND.f_84580_ || dstFactorAlpha != BLEND.f_84581_) {
            BLEND.f_84578_ = srcFactor;
            BLEND.f_84579_ = dstFactor;
            BLEND.f_84580_ = srcFactorAlpha;
            BLEND.f_84581_ = dstFactorAlpha;
            if (Config.isShaders()) {
               Shaders.uniform_blendFunc.setValue(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
            }

            glBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
         }
      }
   }

   public static void _blendEquation(int blendEquation) {
      RenderSystem.assertOnRenderThread();
      GL14.glBlendEquation(blendEquation);
   }

   public static void init(GLCapabilities glCapabilities) {
      RenderSystem.assertOnRenderThreadOrInit();
      Config.initDisplay();
      GL_COPY_READ_BUFFER = 36662;
      GL_COPY_WRITE_BUFFER = 36663;
      GL_ARRAY_BUFFER = 34962;
      GL_STATIC_DRAW = 35044;
      boolean copyBuffer = true;
      boolean multiDrawArrays = true;
      vboRegions = copyBuffer && multiDrawArrays;
      if (!vboRegions) {
         List<String> list = new ArrayList();
         if (!copyBuffer) {
            list.add("OpenGL 1.3, ARB_copy_buffer");
         }

         if (!multiDrawArrays) {
            list.add("OpenGL 1.4");
         }

         String vboRegionWarn = "VboRegions not supported, missing: " + Config.listToString(list);
         Config.dbg(vboRegionWarn);
      }
   }

   public static int glGetProgrami(int program, int pname) {
      RenderSystem.assertOnRenderThread();
      return GL20.glGetProgrami(program, pname);
   }

   public static void glAttachShader(int program, int shaderIn) {
      RenderSystem.assertOnRenderThread();
      GL20.glAttachShader(program, shaderIn);
   }

   public static void glDeleteShader(int shaderIn) {
      RenderSystem.assertOnRenderThread();
      GL20.glDeleteShader(shaderIn);
   }

   public static int glCreateShader(int type) {
      RenderSystem.assertOnRenderThread();
      return GL20.glCreateShader(type);
   }

   public static void glShaderSource(int shaderIn, List<String> source) {
      RenderSystem.assertOnRenderThread();
      StringBuilder stringbuilder = new StringBuilder();

      for (String s : source) {
         stringbuilder.append(s);
      }

      byte[] abyte = stringbuilder.toString().getBytes(Charsets.UTF_8);
      ByteBuffer bytebuffer = MemoryUtil.memAlloc(abyte.length + 1);
      bytebuffer.put(abyte);
      bytebuffer.put((byte)0);
      bytebuffer.flip();

      try {
         MemoryStack memorystack = MemoryStack.stackPush();

         try {
            PointerBuffer pointerbuffer = memorystack.mallocPointer(1);
            pointerbuffer.put(bytebuffer);
            GL20C.nglShaderSource(shaderIn, 1, pointerbuffer.address0(), 0L);
         } catch (Throwable var13) {
            if (memorystack != null) {
               try {
                  memorystack.close();
               } catch (Throwable var12) {
                  var13.addSuppressed(var12);
               }
            }

            throw var13;
         }

         if (memorystack != null) {
            memorystack.close();
         }
      } finally {
         MemoryUtil.memFree(bytebuffer);
      }
   }

   public static void glCompileShader(int shaderIn) {
      RenderSystem.assertOnRenderThread();
      GL20.glCompileShader(shaderIn);
   }

   public static int glGetShaderi(int shaderIn, int pname) {
      RenderSystem.assertOnRenderThread();
      return GL20.glGetShaderi(shaderIn, pname);
   }

   public static void _glUseProgram(int program) {
      RenderSystem.assertOnRenderThread();
      if (glProgram != program) {
         GL20.glUseProgram(program);
         glProgram = program;
      }
   }

   public static int glCreateProgram() {
      RenderSystem.assertOnRenderThread();
      return GL20.glCreateProgram();
   }

   public static void glDeleteProgram(int program) {
      RenderSystem.assertOnRenderThread();
      GL20.glDeleteProgram(program);
   }

   public static void glLinkProgram(int program) {
      RenderSystem.assertOnRenderThread();
      GL20.glLinkProgram(program);
   }

   public static int _glGetUniformLocation(int program, CharSequence name) {
      RenderSystem.assertOnRenderThread();
      return GL20.glGetUniformLocation(program, name);
   }

   public static void _glUniform1(int location, IntBuffer value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniform1iv(location, value);
   }

   public static void _glUniform1i(int location, int value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniform1i(location, value);
   }

   public static void _glUniform1(int location, FloatBuffer value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniform1fv(location, value);
   }

   public static void _glUniform2(int location, IntBuffer value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniform2iv(location, value);
   }

   public static void _glUniform2(int location, FloatBuffer value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniform2fv(location, value);
   }

   public static void _glUniform3(int location, IntBuffer value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniform3iv(location, value);
   }

   public static void _glUniform3(int location, FloatBuffer value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniform3fv(location, value);
   }

   public static void _glUniform4(int location, IntBuffer value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniform4iv(location, value);
   }

   public static void _glUniform4(int location, FloatBuffer value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniform4fv(location, value);
   }

   public static void _glUniformMatrix2(int location, boolean transpose, FloatBuffer value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniformMatrix2fv(location, transpose, value);
   }

   public static void _glUniformMatrix3(int location, boolean transpose, FloatBuffer value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniformMatrix3fv(location, transpose, value);
   }

   public static void _glUniformMatrix4(int location, boolean transpose, FloatBuffer value) {
      RenderSystem.assertOnRenderThread();
      GL20.glUniformMatrix4fv(location, transpose, value);
   }

   public static int _glGetAttribLocation(int program, CharSequence name) {
      RenderSystem.assertOnRenderThread();
      return GL20.glGetAttribLocation(program, name);
   }

   public static void _glBindAttribLocation(int program, int index, CharSequence name) {
      RenderSystem.assertOnRenderThread();
      GL20.glBindAttribLocation(program, index, name);
   }

   public static int _glGenBuffers() {
      RenderSystem.assertOnRenderThreadOrInit();
      return GL15.glGenBuffers();
   }

   public static int _glGenVertexArrays() {
      RenderSystem.assertOnRenderThreadOrInit();
      return GL30.glGenVertexArrays();
   }

   public static void _glBindBuffer(int target, int buffer) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL15.glBindBuffer(target, buffer);
   }

   public static void _glBindVertexArray(int array) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL30.glBindVertexArray(array);
   }

   public static void _glBufferData(int target, ByteBuffer data, int usage) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL15.glBufferData(target, data, usage);
   }

   public static void _glBufferData(int targetIn, long dataIn, int usageIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL15.glBufferData(targetIn, dataIn, usageIn);
   }

   @Nullable
   public static ByteBuffer _glMapBuffer(int targetIn, int accessIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      return GL15.glMapBuffer(targetIn, accessIn);
   }

   public static void _glUnmapBuffer(int targetIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL15.glUnmapBuffer(targetIn);
   }

   public static void _glDeleteBuffers(int buffer) {
      RenderSystem.assertOnRenderThread();
      if (ON_LINUX) {
         GL32C.glBindBuffer(34962, buffer);
         GL32C.glBufferData(34962, 0L, 35048);
         GL32C.glBindBuffer(34962, 0);
      }

      GL15.glDeleteBuffers(buffer);
   }

   public static void _glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL20.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
   }

   public static void _glDeleteVertexArrays(int array) {
      RenderSystem.assertOnRenderThread();
      GL30.glDeleteVertexArrays(array);
   }

   public static void _glBindFramebuffer(int target, int framebufferIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      if (target == 36160) {
         if (framebufferRead == framebufferIn && framebufferDraw == framebufferIn) {
            return;
         }

         framebufferRead = framebufferIn;
         framebufferDraw = framebufferIn;
      } else if (target == 36008) {
         if (framebufferRead == framebufferIn) {
            return;
         }

         framebufferRead = framebufferIn;
      }

      if (target == 36009) {
         if (framebufferDraw == framebufferIn) {
            return;
         }

         framebufferDraw = framebufferIn;
      }

      GL30.glBindFramebuffer(target, framebufferIn);
   }

   public static void _glBlitFrameBuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
   }

   public static void _glBindRenderbuffer(int target, int renderbuffer) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL30.glBindRenderbuffer(target, renderbuffer);
   }

   public static void _glDeleteRenderbuffers(int renderbuffer) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL30.glDeleteRenderbuffers(renderbuffer);
   }

   public static void _glDeleteFramebuffers(int framebufferIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL30.glDeleteFramebuffers(framebufferIn);
   }

   public static int glGenFramebuffers() {
      RenderSystem.assertOnRenderThreadOrInit();
      return GL30.glGenFramebuffers();
   }

   public static int glGenRenderbuffers() {
      RenderSystem.assertOnRenderThreadOrInit();
      return GL30.glGenRenderbuffers();
   }

   public static void _glRenderbufferStorage(int target, int internalFormat, int width, int height) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL30.glRenderbufferStorage(target, internalFormat, width, height);
   }

   public static void _glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL30.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
   }

   public static int glCheckFramebufferStatus(int framebufferIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      return GL30.glCheckFramebufferStatus(framebufferIn);
   }

   public static void _glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
   }

   public static int getBoundFramebuffer() {
      RenderSystem.assertOnRenderThread();
      return _getInteger(36006);
   }

   public static void glActiveTexture(int textureIn) {
      RenderSystem.assertOnRenderThread();
      GL13.glActiveTexture(textureIn);
   }

   public static void glBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
      RenderSystem.assertOnRenderThread();
      GL14.glBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
   }

   public static String glGetShaderInfoLog(int shader, int maxLen) {
      RenderSystem.assertOnRenderThread();
      return GL20.glGetShaderInfoLog(shader, maxLen);
   }

   public static String glGetProgramInfoLog(int program, int maxLen) {
      RenderSystem.assertOnRenderThread();
      return GL20.glGetProgramInfoLog(program, maxLen);
   }

   public static void setupLevelDiffuseLighting(Vector3f vec1, Vector3f vec2, Matrix4f matrixIn) {
      RenderSystem.assertOnRenderThread();
      RenderSystem.setShaderLights(matrixIn.transformDirection(vec1, new Vector3f()), matrixIn.transformDirection(vec2, new Vector3f()));
   }

   public static void setupGuiFlatDiffuseLighting(Vector3f vec1, Vector3f vec2) {
      RenderSystem.assertOnRenderThread();
      Matrix4f matrix4f = new Matrix4f().rotationY((float) (-Math.PI / 8)).rotateX((float) (Math.PI * 3.0 / 4.0));
      setupLevelDiffuseLighting(vec1, vec2, matrix4f);
   }

   public static void setupGui3DDiffuseLighting(Vector3f vec1, Vector3f vec2) {
      RenderSystem.assertOnRenderThread();
      Matrix4f matrix4f = new Matrix4f()
         .scaling(1.0F, -1.0F, 1.0F)
         .rotateYXZ(1.0821041F, 3.2375858F, 0.0F)
         .rotateYXZ((float) (-Math.PI / 8), (float) (Math.PI * 3.0 / 4.0), 0.0F);
      setupLevelDiffuseLighting(vec1, vec2, matrix4f);
   }

   public static void _enableCull() {
      RenderSystem.assertOnRenderThread();
      if (cullLock.isLocked()) {
         cullLockState.setEnabled();
      } else {
         CULL.f_84621_.m_84592_();
      }
   }

   public static void _disableCull() {
      RenderSystem.assertOnRenderThread();
      if (cullLock.isLocked()) {
         cullLockState.setDisabled();
      } else {
         CULL.f_84621_.m_84589_();
      }
   }

   public static void _polygonMode(int face, int mode) {
      RenderSystem.assertOnRenderThread();
      GL11.glPolygonMode(face, mode);
   }

   public static void _enablePolygonOffset() {
      RenderSystem.assertOnRenderThread();
      POLY_OFFSET.f_84725_.m_84592_();
   }

   public static void _disablePolygonOffset() {
      RenderSystem.assertOnRenderThread();
      POLY_OFFSET.f_84725_.m_84589_();
   }

   public static void _polygonOffset(float factor, float units) {
      RenderSystem.assertOnRenderThread();
      if (factor != POLY_OFFSET.f_84727_ || units != POLY_OFFSET.f_84728_) {
         POLY_OFFSET.f_84727_ = factor;
         POLY_OFFSET.f_84728_ = units;
         GL11.glPolygonOffset(factor, units);
      }
   }

   public static void _enableColorLogicOp() {
      RenderSystem.assertOnRenderThread();
      COLOR_LOGIC.f_84603_.m_84592_();
   }

   public static void _disableColorLogicOp() {
      RenderSystem.assertOnRenderThread();
      COLOR_LOGIC.f_84603_.m_84589_();
   }

   public static void _logicOp(int logicOperation) {
      RenderSystem.assertOnRenderThread();
      if (logicOperation != COLOR_LOGIC.f_84604_) {
         COLOR_LOGIC.f_84604_ = logicOperation;
         GL11.glLogicOp(logicOperation);
      }
   }

   public static void _activeTexture(int textureIn) {
      RenderSystem.assertOnRenderThread();
      if (activeTexture != textureIn - 33984) {
         activeTexture = textureIn - 33984;
         glActiveTexture(textureIn);
      }
   }

   public static void enableTexture() {
      RenderSystem.assertOnRenderThreadOrInit();
      TEXTURES[activeTexture].texture2DState = true;
   }

   public static void disableTexture() {
      RenderSystem.assertOnRenderThread();
      TEXTURES[activeTexture].texture2DState = false;
   }

   public static void _texParameter(int target, int parameterName, float parameter) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL11.glTexParameterf(target, parameterName, parameter);
   }

   public static void _texParameter(int target, int parameterName, int parameter) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL11.glTexParameteri(target, parameterName, parameter);
   }

   public static int _getTexLevelParameter(int target, int level, int parameterName) {
      return GL11.glGetTexLevelParameteri(target, level, parameterName);
   }

   public static int _genTexture() {
      RenderSystem.assertOnRenderThreadOrInit();
      return GL11.glGenTextures();
   }

   public static void _genTextures(int[] texturesIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL11.glGenTextures(texturesIn);
   }

   public static void _deleteTexture(int textureIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      if (textureIn != 0) {
         for (int i = 0; i < IMAGE_TEXTURES.length; i++) {
            if (IMAGE_TEXTURES[i] == textureIn) {
               IMAGE_TEXTURES[i] = 0;
            }
         }

         GL11.glDeleteTextures(textureIn);

         for (GlStateManager.TextureState glstatemanager$texturestate : TEXTURES) {
            if (glstatemanager$texturestate.f_84801_ == textureIn) {
               glstatemanager$texturestate.f_84801_ = 0;
            }
         }
      }
   }

   public static void _deleteTextures(int[] texturesIn) {
      RenderSystem.assertOnRenderThreadOrInit();

      for (GlStateManager.TextureState glstatemanager$texturestate : TEXTURES) {
         for (int i : texturesIn) {
            if (glstatemanager$texturestate.f_84801_ == i) {
               glstatemanager$texturestate.f_84801_ = -1;
            }
         }
      }

      GL11.glDeleteTextures(texturesIn);
   }

   public static void _bindTexture(int textureIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      if (textureIn != TEXTURES[activeTexture].f_84801_) {
         TEXTURES[activeTexture].f_84801_ = textureIn;
         GL11.glBindTexture(3553, textureIn);
         if (SmartAnimations.isActive()) {
            SmartAnimations.textureRendered(textureIn);
         }
      }
   }

   public static int _getActiveTexture() {
      return activeTexture + 33984;
   }

   public static void _texImage2D(
      int target, int level, int internalFormat, int width, int height, int border, int format, int type, @Nullable IntBuffer pixels
   ) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL11.glTexImage2D(target, level, internalFormat, width, height, border, format, type, pixels);
   }

   public static void _texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, long pixels) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
   }

   public static void upload(
      int level, int xOffset, int yOffset, int width, int height, NativeImage.Format imageFormat, IntBuffer pixels, Consumer<IntBuffer> bufferConsumer
   ) {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> _upload(level, xOffset, yOffset, width, height, imageFormat, pixels, bufferConsumer));
      } else {
         _upload(level, xOffset, yOffset, width, height, imageFormat, pixels, bufferConsumer);
      }
   }

   private static void _upload(
      int level, int xOffset, int yOffset, int width, int height, NativeImage.Format imageFormat, IntBuffer pixels, Consumer<IntBuffer> bufferConsumer
   ) {
      try {
         RenderSystem.assertOnRenderThreadOrInit();
         _pixelStore(3314, width);
         _pixelStore(3316, 0);
         _pixelStore(3315, 0);
         imageFormat.m_85169_();
         GL11.glTexSubImage2D(3553, level, xOffset, yOffset, width, height, imageFormat.m_85170_(), 5121, pixels);
      } finally {
         bufferConsumer.m_340568_(pixels);
      }
   }

   public static void _getTexImage(int tex, int level, int format, int type, long pixels) {
      RenderSystem.assertOnRenderThread();
      GL11.glGetTexImage(tex, level, format, type, pixels);
   }

   public static void _viewport(int x, int y, int width, int height) {
      RenderSystem.assertOnRenderThreadOrInit();
      GlStateManager.Viewport.INSTANCE.f_84806_ = x;
      GlStateManager.Viewport.INSTANCE.f_84807_ = y;
      GlStateManager.Viewport.INSTANCE.f_84808_ = width;
      GlStateManager.Viewport.INSTANCE.f_84809_ = height;
      GL11.glViewport(x, y, width, height);
   }

   public static void _colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
      RenderSystem.assertOnRenderThread();
      if (red != COLOR_MASK.f_84608_ || green != COLOR_MASK.f_84609_ || blue != COLOR_MASK.f_84610_ || alpha != COLOR_MASK.f_84611_) {
         COLOR_MASK.f_84608_ = red;
         COLOR_MASK.f_84609_ = green;
         COLOR_MASK.f_84610_ = blue;
         COLOR_MASK.f_84611_ = alpha;
         GL11.glColorMask(red, green, blue, alpha);
      }
   }

   public static void _stencilFunc(int func, int ref, int mask) {
      RenderSystem.assertOnRenderThread();
      if (func != STENCIL.f_84767_.f_84761_ || func != STENCIL.f_84767_.f_84762_ || func != STENCIL.f_84767_.f_84763_) {
         STENCIL.f_84767_.f_84761_ = func;
         STENCIL.f_84767_.f_84762_ = ref;
         STENCIL.f_84767_.f_84763_ = mask;
         GL11.glStencilFunc(func, ref, mask);
      }
   }

   public static void _stencilMask(int mask) {
      RenderSystem.assertOnRenderThread();
      if (mask != STENCIL.f_84768_) {
         STENCIL.f_84768_ = mask;
         GL11.glStencilMask(mask);
      }
   }

   public static void _stencilOp(int sfail, int dpfail, int dppass) {
      RenderSystem.assertOnRenderThread();
      if (sfail != STENCIL.f_84769_ || dpfail != STENCIL.f_84770_ || dppass != STENCIL.f_84771_) {
         STENCIL.f_84769_ = sfail;
         STENCIL.f_84770_ = dpfail;
         STENCIL.f_84771_ = dppass;
         GL11.glStencilOp(sfail, dpfail, dppass);
      }
   }

   public static void _clearDepth(double depth) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL11.glClearDepth(depth);
   }

   public static void _clearColor(float red, float green, float blue, float alpha) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL11.glClearColor(red, green, blue, alpha);
   }

   public static void _clearStencil(int index) {
      RenderSystem.assertOnRenderThread();
      GL11.glClearStencil(index);
   }

   public static void _clear(int mask, boolean checkError) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL11.glClear(mask);
      if (checkError) {
         _getError();
      }
   }

   public static void _glDrawPixels(int width, int height, int format, int type, long pixels) {
      RenderSystem.assertOnRenderThread();
      GL11.glDrawPixels(width, height, format, type, pixels);
   }

   public static void _vertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long pointer) {
      RenderSystem.assertOnRenderThread();
      GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
   }

   public static void _vertexAttribIPointer(int index, int size, int type, int stride, long pointer) {
      RenderSystem.assertOnRenderThread();
      GL30.glVertexAttribIPointer(index, size, type, stride, pointer);
   }

   public static void _enableVertexAttribArray(int index) {
      RenderSystem.assertOnRenderThread();
      GL20.glEnableVertexAttribArray(index);
   }

   public static void _disableVertexAttribArray(int index) {
      RenderSystem.assertOnRenderThread();
      GL20.glDisableVertexAttribArray(index);
   }

   public static void _drawElements(int modeIn, int countIn, int typeIn, long indicesIn) {
      RenderSystem.assertOnRenderThread();
      GL11.glDrawElements(modeIn, countIn, typeIn, indicesIn);
      if (Config.isShaders() && Shaders.isRenderingWorld) {
         int countInstances = Shaders.activeProgram.getCountInstances();
         if (countInstances > 1) {
            for (int i = 1; i < countInstances; i++) {
               Shaders.uniform_instanceId.setValue(i);
               GL11.glDrawElements(modeIn, countIn, typeIn, indicesIn);
            }

            Shaders.uniform_instanceId.setValue(0);
         }
      }
   }

   public static void drawArrays(int mode, int first, int count) {
      RenderSystem.assertOnRenderThread();
      GL11.glDrawArrays(mode, first, count);
      if (Config.isShaders() && Shaders.isRenderingWorld) {
         int countInstances = Shaders.activeProgram.getCountInstances();
         if (countInstances > 1) {
            for (int i = 1; i < countInstances; i++) {
               Shaders.uniform_instanceId.setValue(i);
               GL11.glDrawArrays(mode, first, count);
            }

            Shaders.uniform_instanceId.setValue(0);
         }
      }
   }

   public static void _pixelStore(int pname, int param) {
      RenderSystem.assertOnRenderThreadOrInit();
      GL11.glPixelStorei(pname, param);
   }

   public static void _readPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) {
      RenderSystem.assertOnRenderThread();
      GL11.glReadPixels(x, y, width, height, format, type, pixels);
   }

   public static void _readPixels(int x, int y, int width, int height, int format, int type, long pixels) {
      RenderSystem.assertOnRenderThread();
      GL11.glReadPixels(x, y, width, height, format, type, pixels);
   }

   public static int _getError() {
      RenderSystem.assertOnRenderThread();
      return GL11.glGetError();
   }

   public static String _getString(int name) {
      RenderSystem.assertOnRenderThread();
      return GL11.glGetString(name);
   }

   public static int _getInteger(int pname) {
      RenderSystem.assertOnRenderThreadOrInit();
      return GL11.glGetInteger(pname);
   }

   public static void color4f(float red, float green, float blue, float alpha) {
      RenderSystem.setShaderColor(red, green, blue, alpha);
   }

   public static int getActiveTextureUnit() {
      return 33984 + activeTexture;
   }

   public static void bindCurrentTexture() {
      GL11.glBindTexture(3553, TEXTURES[activeTexture].f_84801_);
   }

   public static int getBoundTexture() {
      return TEXTURES[activeTexture].f_84801_;
   }

   public static int getBoundTexture(int textureUnit) {
      return TEXTURES[textureUnit].f_84801_;
   }

   public static void checkBoundTexture() {
      if (Config.isMinecraftThread()) {
         int glAct = GL11.glGetInteger(34016);
         int glTex = GL11.glGetInteger(32873);
         int act = getActiveTextureUnit();
         int tex = getBoundTexture();
         if (tex > 0) {
            if (glAct != act || glTex != tex) {
               Config.dbg("checkTexture: act: " + act + ", glAct: " + glAct + ", tex: " + tex + ", glTex: " + glTex);
            }
         }
      }
   }

   public static void genTextures(IntBuffer buf) {
      GL11.glGenTextures(buf);
   }

   public static void deleteTextures(IntBuffer buf) {
      buf.rewind();

      while (buf.position() < buf.limit()) {
         int texId = buf.get();
         _deleteTexture(texId);
      }

      buf.rewind();
   }

   public static void lockAlpha(GlAlphaState stateNew) {
      if (!alphaLock.isLocked()) {
         getAlphaState(alphaLockState);
         setAlphaState(stateNew);
         alphaLock.lock();
      }
   }

   public static void unlockAlpha() {
      if (alphaLock.unlock()) {
         setAlphaState(alphaLockState);
      }
   }

   public static void getAlphaState(GlAlphaState state) {
      if (alphaLock.isLocked()) {
         state.setState(alphaLockState);
      } else {
         state.setState(alphaTest, alphaTestFunc, alphaTestRef);
      }
   }

   public static void setAlphaState(GlAlphaState state) {
      if (alphaLock.isLocked()) {
         alphaLockState.setState(state);
      } else {
         alphaTest = state.isEnabled();
         alphaFunc(state.getFunc(), state.getRef());
      }
   }

   public static void lockBlend(GlBlendState stateNew) {
      if (!blendLock.isLocked()) {
         getBlendState(blendLockState);
         setBlendState(stateNew);
         blendLock.lock();
      }
   }

   public static void unlockBlend() {
      if (blendLock.unlock()) {
         setBlendState(blendLockState);
      }
   }

   public static void getBlendState(GlBlendState gbs) {
      if (blendLock.isLocked()) {
         gbs.setState(blendLockState);
      } else {
         gbs.setState(BLEND.f_84577_.f_84586_, BLEND.f_84578_, BLEND.f_84579_, BLEND.f_84580_, BLEND.f_84581_);
      }
   }

   public static void setBlendState(GlBlendState gbs) {
      if (blendLock.isLocked()) {
         blendLockState.setState(gbs);
      } else {
         BLEND.f_84577_.m_84590_(gbs.isEnabled());
         if (!gbs.isSeparate()) {
            _blendFunc(gbs.getSrcFactor(), gbs.getDstFactor());
         } else {
            _blendFuncSeparate(gbs.getSrcFactor(), gbs.getDstFactor(), gbs.getSrcFactorAlpha(), gbs.getDstFactorAlpha());
         }
      }
   }

   public static void lockCull(GlCullState stateNew) {
      if (!cullLock.isLocked()) {
         getCullState(cullLockState);
         setCullState(stateNew);
         cullLock.lock();
      }
   }

   public static void unlockCull() {
      if (cullLock.unlock()) {
         setCullState(cullLockState);
      }
   }

   public static void getCullState(GlCullState state) {
      if (cullLock.isLocked()) {
         state.setState(cullLockState);
      } else {
         state.setState(CULL.f_84621_.f_84586_, CULL.f_84622_);
      }
   }

   public static void setCullState(GlCullState state) {
      if (cullLock.isLocked()) {
         cullLockState.setState(state);
      } else {
         CULL.f_84621_.m_84590_(state.isEnabled());
         CULL.f_84622_ = state.getMode();
      }
   }

   public static void glMultiDrawArrays(int mode, IntBuffer bFirst, IntBuffer bCount) {
      GL14.glMultiDrawArrays(mode, bFirst, bCount);
      if (Config.isShaders()) {
         int countInstances = Shaders.activeProgram.getCountInstances();
         if (countInstances > 1) {
            for (int i = 1; i < countInstances; i++) {
               Shaders.uniform_instanceId.setValue(i);
               GL14.glMultiDrawArrays(mode, bFirst, bCount);
            }

            Shaders.uniform_instanceId.setValue(0);
         }
      }
   }

   public static void glMultiDrawElements(int modeIn, IntBuffer countsIn, int typeIn, PointerBuffer indicesIn) {
      RenderSystem.assertOnRenderThread();
      GL14.glMultiDrawElements(modeIn, countsIn, typeIn, indicesIn);
      if (Config.isShaders() && Shaders.isRenderingWorld) {
         int countInstances = Shaders.activeProgram.getCountInstances();
         if (countInstances > 1) {
            for (int i = 1; i < countInstances; i++) {
               Shaders.uniform_instanceId.setValue(i);
               GL14.glMultiDrawElements(modeIn, countsIn, typeIn, indicesIn);
            }

            Shaders.uniform_instanceId.setValue(0);
         }
      }
   }

   public static void clear(int mask) {
      _clear(mask, false);
   }

   public static void bufferSubData(int target, long offset, ByteBuffer data) {
      GL15.glBufferSubData(target, offset, data);
   }

   public static void copyBufferSubData(int readTarget, int writeTarget, long readOffset, long writeOffset, long size) {
      GL31.glCopyBufferSubData(readTarget, writeTarget, readOffset, writeOffset, size);
   }

   public static void readPixels(int x, int y, int width, int height, int format, int type, long pixels) {
      GL11.glReadPixels(x, y, width, height, format, type, pixels);
   }

   public static int getFramebufferRead() {
      return framebufferRead;
   }

   public static int getFramebufferDraw() {
      return framebufferDraw;
   }

   public static void applyCurrentBlend() {
      if (BLEND.f_84577_.f_84586_) {
         GL11.glEnable(3042);
      } else {
         GL11.glDisable(3042);
      }

      GL14.glBlendFuncSeparate(BLEND.f_84578_, BLEND.f_84579_, BLEND.f_84580_, BLEND.f_84581_);
   }

   public static void setBlendsIndexed(GlBlendState[] blends) {
      if (blends != null) {
         for (int i = 0; i < blends.length; i++) {
            GlBlendState bs = blends[i];
            if (bs != null) {
               if (bs.isEnabled()) {
                  GL30.glEnablei(3042, i);
               } else {
                  GL30.glDisablei(3042, i);
               }

               ARBDrawBuffersBlend.glBlendFuncSeparateiARB(i, bs.getSrcFactor(), bs.getDstFactor(), bs.getSrcFactorAlpha(), bs.getDstFactorAlpha());
            }
         }
      }
   }

   public static void bindImageTexture(int unit, int texture, int level, boolean layered, int layer, int access, int format) {
      if (unit >= 0 && unit < IMAGE_TEXTURES.length) {
         if (IMAGE_TEXTURES[unit] == texture) {
            return;
         }

         IMAGE_TEXTURES[unit] = texture;
      }

      GL42.glBindImageTexture(unit, texture, level, layered, layer, access, format);
   }

   public static int getProgram() {
      return glProgram;
   }

   static class BlendState {
      public GlStateManager.BooleanState f_84577_ = new GlStateManager.BooleanState(3042);
      public int f_84578_ = 1;
      public int f_84579_ = 0;
      public int f_84580_ = 1;
      public int f_84581_ = 0;
   }

   static class BooleanState {
      private int f_84585_;
      private boolean f_84586_;

      public BooleanState(int capabilityIn) {
         this.f_84585_ = capabilityIn;
      }

      public void m_84589_() {
         this.m_84590_(false);
      }

      public void m_84592_() {
         this.m_84590_(true);
      }

      public void m_84590_(boolean enabled) {
         RenderSystem.assertOnRenderThreadOrInit();
         if (enabled != this.f_84586_) {
            this.f_84586_ = enabled;
            if (enabled) {
               GL11.glEnable(this.f_84585_);
            } else {
               GL11.glDisable(this.f_84585_);
            }
         }
      }
   }

   static class ColorLogicState {
      public GlStateManager.BooleanState f_84603_ = new GlStateManager.BooleanState(3058);
      public int f_84604_ = 5379;
   }

   static class ColorMask {
      public boolean f_84608_ = true;
      public boolean f_84609_ = true;
      public boolean f_84610_ = true;
      public boolean f_84611_ = true;
   }

   static class CullState {
      public GlStateManager.BooleanState f_84621_ = new GlStateManager.BooleanState(2884);
      public int f_84622_ = 1029;
   }

   static class DepthState {
      public GlStateManager.BooleanState f_84626_ = new GlStateManager.BooleanState(2929);
      public boolean f_84627_ = true;
      public int f_84628_ = 513;
   }

   @DontObfuscate
   public static enum DestFactor {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_COLOR(768),
      ZERO(0);

      public int value;

      private DestFactor(final int valueIn) {
         this.value = valueIn;
      }
   }

   public static enum LogicOp {
      AND(5377),
      AND_INVERTED(5380),
      AND_REVERSE(5378),
      CLEAR(5376),
      COPY(5379),
      COPY_INVERTED(5388),
      EQUIV(5385),
      INVERT(5386),
      NAND(5390),
      NOOP(5381),
      NOR(5384),
      OR(5383),
      OR_INVERTED(5389),
      OR_REVERSE(5387),
      SET(5391),
      XOR(5382);

      public int f_84715_;

      private LogicOp(final int opcodeIn) {
         this.f_84715_ = opcodeIn;
      }
   }

   static class PolygonOffsetState {
      public GlStateManager.BooleanState f_84725_ = new GlStateManager.BooleanState(32823);
      public GlStateManager.BooleanState f_84726_ = new GlStateManager.BooleanState(10754);
      public float f_84727_;
      public float f_84728_;
   }

   static class ScissorState {
      public GlStateManager.BooleanState f_84732_ = new GlStateManager.BooleanState(3089);
   }

   @DontObfuscate
   public static enum SourceFactor {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_ALPHA_SATURATE(776),
      SRC_COLOR(768),
      ZERO(0);

      public int value;

      private SourceFactor(final int valueIn) {
         this.value = valueIn;
      }
   }

   static class StencilFunc {
      public int f_84761_ = 519;
      public int f_84762_;
      public int f_84763_ = -1;
   }

   static class StencilState {
      public GlStateManager.StencilFunc f_84767_ = new GlStateManager.StencilFunc();
      public int f_84768_ = -1;
      public int f_84769_ = 7680;
      public int f_84770_ = 7680;
      public int f_84771_ = 7680;
   }

   static class TextureState {
      public boolean texture2DState;
      public int f_84801_;
   }

   public static enum Viewport {
      INSTANCE;

      protected int f_84806_;
      protected int f_84807_;
      protected int f_84808_;
      protected int f_84809_;

      public static int m_157126_() {
         return INSTANCE.f_84806_;
      }

      public static int m_157127_() {
         return INSTANCE.f_84807_;
      }

      public static int m_157128_() {
         return INSTANCE.f_84808_;
      }

      public static int m_157129_() {
         return INSTANCE.f_84809_;
      }
   }
}
