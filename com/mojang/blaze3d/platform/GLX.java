package com.mojang.blaze3d.platform;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import net.minecraft.client.renderer.GameRenderer;
import net.optifine.Config;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

@DontObfuscate
public class GLX {
   private static Logger LOGGER = LogUtils.getLogger();
   private static String cpuInfo;

   public static String getOpenGLVersionString() {
      RenderSystem.assertOnRenderThread();
      return GLFW.glfwGetCurrentContext() == 0L
         ? "NO CONTEXT"
         : GlStateManager._getString(7937) + " GL version " + GlStateManager._getString(7938) + ", " + GlStateManager._getString(7936);
   }

   public static int _getRefreshRate(Window windowIn) {
      RenderSystem.assertOnRenderThread();
      long i = GLFW.glfwGetWindowMonitor(windowIn.m_85439_());
      if (i == 0L) {
         i = GLFW.glfwGetPrimaryMonitor();
      }

      GLFWVidMode glfwvidmode = i == 0L ? null : GLFW.glfwGetVideoMode(i);
      return glfwvidmode == null ? 0 : glfwvidmode.refreshRate();
   }

   public static String _getLWJGLVersion() {
      return Version.getVersion();
   }

   public static LongSupplier _initGlfw() {
      Window.m_85407_((codeIn, textIn) -> {
         throw new IllegalStateException(String.m_12886_(Locale.ROOT, "GLFW error before init: [0x%X]%s", new Object[]{codeIn, textIn}));
      });
      List<String> list = Lists.newArrayList();
      GLFWErrorCallback glfwerrorcallback = GLFW.glfwSetErrorCallback((codeIn, pointerIn) -> {
         String s1 = pointerIn == 0L ? "" : MemoryUtil.memUTF8(pointerIn);
         list.add(String.m_12886_(Locale.ROOT, "GLFW error during init: [0x%X]%s", new Object[]{codeIn, s1}));
      });
      if (!GLFW.glfwInit()) {
         throw new IllegalStateException("Failed to initialize GLFW, errors: " + Joiner.on(",").join(list));
      } else {
         LongSupplier longsupplier = () -> (long)(GLFW.glfwGetTime() * 1.0E9);

         for (String s : list) {
            LOGGER.error("GLFW error collected during initialization: {}", s);
         }

         RenderSystem.setErrorCallback(glfwerrorcallback);
         return longsupplier;
      }
   }

   public static void _setGlfwErrorCallback(GLFWErrorCallbackI errorCallbackIn) {
      GLFWErrorCallback glfwerrorcallback = GLFW.glfwSetErrorCallback(errorCallbackIn);
      if (glfwerrorcallback != null) {
         glfwerrorcallback.free();
      }
   }

   public static boolean _shouldClose(Window windowIn) {
      return GLFW.glfwWindowShouldClose(windowIn.m_85439_());
   }

   public static void _init(int debugVerbosityIn, boolean debugSyncIn) {
      GLCapabilities glcapabilities = GL.getCapabilities();
      GlStateManager.init(glcapabilities);

      try {
         CentralProcessor centralprocessor = new SystemInfo().getHardware().getProcessor();
         cpuInfo = String.m_12886_(
               Locale.ROOT, "%dx %s", new Object[]{centralprocessor.getLogicalProcessorCount(), centralprocessor.getProcessorIdentifier().getName()}
            )
            .replaceAll("\\s+", " ");
      } catch (Throwable var4) {
      }

      GlDebug.m_84049_(debugVerbosityIn, debugSyncIn);
   }

   public static String _getCpuInfo() {
      return cpuInfo == null ? "<unknown>" : cpuInfo;
   }

   public static void _renderCrosshair(int sizeIn, boolean drawX, boolean drawY, boolean drawZ) {
      if (drawX || drawY || drawZ) {
         RenderSystem.assertOnRenderThread();
         GlStateManager._depthMask(false);
         GlStateManager._disableCull();
         RenderSystem.setShader(GameRenderer::m_172757_);
         Tesselator tesselator = RenderSystem.renderThreadTesselator();
         BufferBuilder bufferbuilder = tesselator.m_339075_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_166851_);
         RenderSystem.lineWidth(4.0F);
         if (drawX) {
            bufferbuilder.m_167146_(0.0F, 0.0F, 0.0F).m_338399_(-16777216).m_338525_(1.0F, 0.0F, 0.0F);
            bufferbuilder.m_167146_((float)sizeIn, 0.0F, 0.0F).m_338399_(-16777216).m_338525_(1.0F, 0.0F, 0.0F);
         }

         if (drawY) {
            bufferbuilder.m_167146_(0.0F, 0.0F, 0.0F).m_338399_(-16777216).m_338525_(0.0F, 1.0F, 0.0F);
            bufferbuilder.m_167146_(0.0F, (float)sizeIn, 0.0F).m_338399_(-16777216).m_338525_(0.0F, 1.0F, 0.0F);
         }

         if (drawZ) {
            bufferbuilder.m_167146_(0.0F, 0.0F, 0.0F).m_338399_(-16777216).m_338525_(0.0F, 0.0F, 1.0F);
            bufferbuilder.m_167146_(0.0F, 0.0F, (float)sizeIn).m_338399_(-16777216).m_338525_(0.0F, 0.0F, 1.0F);
         }

         BufferUploader.m_231202_(bufferbuilder.m_339905_());
         RenderSystem.lineWidth(2.0F);
         bufferbuilder = tesselator.m_339075_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_166851_);
         if (drawX) {
            bufferbuilder.m_167146_(0.0F, 0.0F, 0.0F).m_338399_(-65536).m_338525_(1.0F, 0.0F, 0.0F);
            bufferbuilder.m_167146_((float)sizeIn, 0.0F, 0.0F).m_338399_(-65536).m_338525_(1.0F, 0.0F, 0.0F);
         }

         if (drawY) {
            bufferbuilder.m_167146_(0.0F, 0.0F, 0.0F).m_338399_(-16711936).m_338525_(0.0F, 1.0F, 0.0F);
            bufferbuilder.m_167146_(0.0F, (float)sizeIn, 0.0F).m_338399_(-16711936).m_338525_(0.0F, 1.0F, 0.0F);
         }

         if (drawZ) {
            bufferbuilder.m_167146_(0.0F, 0.0F, 0.0F).m_338399_(-8421377).m_338525_(0.0F, 0.0F, 1.0F);
            bufferbuilder.m_167146_(0.0F, 0.0F, (float)sizeIn).m_338399_(-8421377).m_338525_(0.0F, 0.0F, 1.0F);
         }

         BufferUploader.m_231202_(bufferbuilder.m_339905_());
         RenderSystem.lineWidth(1.0F);
         GlStateManager._enableCull();
         GlStateManager._depthMask(true);
      }
   }

   public static <T> T make(Supplier<T> supplierIn) {
      return (T)supplierIn.get();
   }

   public static <T> T make(T objIn, Consumer<T> consumerIn) {
      consumerIn.m_340568_(objIn);
      return objIn;
   }

   public static boolean isUsingFBOs() {
      return !Config.isAntialiasing();
   }

   public static boolean useVbo() {
      return true;
   }
}
