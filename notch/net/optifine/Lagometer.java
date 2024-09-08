package net.optifine;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_276405_;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3173_;
import net.minecraft.src.C_3179_;
import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3185_;
import net.minecraft.src.C_3188_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_4124_;
import net.minecraft.src.C_442_;
import net.optifine.reflect.Reflector;
import net.optifine.util.MathUtils;
import net.optifine.util.MemoryMonitor;
import org.joml.Matrix4f;

public class Lagometer {
   private static C_3391_ mc;
   private static C_3401_ gameSettings;
   private static C_442_ profiler;
   public static boolean active = false;
   public static Lagometer.TimerNano timerTick = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerScheduledExecutables = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerChunkUpload = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerChunkUpdate = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerVisibility = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerTerrain = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerServer = new Lagometer.TimerNano();
   private static long[] timesFrame = new long[512];
   private static long[] timesTick = new long[512];
   private static long[] timesScheduledExecutables = new long[512];
   private static long[] timesChunkUpload = new long[512];
   private static long[] timesChunkUpdate = new long[512];
   private static long[] timesVisibility = new long[512];
   private static long[] timesTerrain = new long[512];
   private static long[] timesServer = new long[512];
   private static boolean[] gcs = new boolean[512];
   private static int numRecordedFrameTimes = 0;
   private static long prevFrameTimeNano = -1L;
   private static long renderTimeNano = 0L;

   public static void updateLagometer() {
      if (mc == null) {
         mc = C_3391_.m_91087_();
         gameSettings = mc.f_91066_;
         profiler = mc.m_91307_();
      }

      if (mc.m_293199_().f_291101_ && mc.m_293199_().f_290551_) {
         active = true;
         long timeNowNano = System.nanoTime();
         if (prevFrameTimeNano == -1L) {
            prevFrameTimeNano = timeNowNano;
         } else {
            int frameIndex = numRecordedFrameTimes & timesFrame.length - 1;
            numRecordedFrameTimes++;
            boolean gc = MemoryMonitor.isGcEvent();
            timesFrame[frameIndex] = timeNowNano - prevFrameTimeNano - renderTimeNano;
            timesTick[frameIndex] = timerTick.timeNano;
            timesScheduledExecutables[frameIndex] = timerScheduledExecutables.timeNano;
            timesChunkUpload[frameIndex] = timerChunkUpload.timeNano;
            timesChunkUpdate[frameIndex] = timerChunkUpdate.timeNano;
            timesVisibility[frameIndex] = timerVisibility.timeNano;
            timesTerrain[frameIndex] = timerTerrain.timeNano;
            timesServer[frameIndex] = timerServer.timeNano;
            gcs[frameIndex] = gc;
            timerTick.reset();
            timerScheduledExecutables.reset();
            timerVisibility.reset();
            timerChunkUpdate.reset();
            timerChunkUpload.reset();
            timerTerrain.reset();
            timerServer.reset();
            prevFrameTimeNano = System.nanoTime();
         }
      } else {
         active = false;
         prevFrameTimeNano = -1L;
      }
   }

   public static void renderLagometer(C_279497_ graphicsIn, int scaleFactor) {
      long timeRenderStartNano = System.nanoTime();
      GlStateManager.clear(256);
      RenderSystem.backupProjectionMatrix();
      int displayWidth = mc.m_91268_().m_85441_();
      int displayHeight = mc.m_91268_().m_85442_();
      float guiFarPlane = Reflector.ForgeHooksClient_getGuiFarPlane.exists() ? Reflector.ForgeHooksClient_getGuiFarPlane.callFloat() : 21000.0F;
      Matrix4f matrix4f = MathUtils.makeOrtho4f(0.0F, (float)displayWidth, 0.0F, (float)displayHeight, 1000.0F, guiFarPlane);
      RenderSystem.setProjectionMatrix(matrix4f, C_276405_.f_276633_);
      C_3181_ matrixStackIn = graphicsIn.m_280168_();
      matrixStackIn.m_85836_();
      matrixStackIn.m_252880_(0.0F, 0.0F, 10000.0F - guiFarPlane);
      GlStateManager.disableTexture();
      GlStateManager._depthMask(false);
      GlStateManager._disableCull();
      RenderSystem.setShader(C_4124_::m_172757_);
      RenderSystem.lineWidth(1.0F);
      C_3185_ tess = C_3185_.m_85913_();
      C_3173_ tessellator = tess.m_339075_(C_3188_.C_141549_.LINES, C_3179_.f_166851_);

      for (int frameNum = 0; frameNum < timesFrame.length; frameNum++) {
         int lum = (frameNum - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
         lum += 155;
         float baseHeight = (float)displayHeight;
         long heightFrame = 0L;
         if (gcs[frameNum]) {
            heightFrame = renderTime(frameNum, timesFrame[frameNum], lum, lum / 2, 0, baseHeight, tessellator);
         } else {
            heightFrame = renderTime(frameNum, timesFrame[frameNum], lum, lum, lum, baseHeight, tessellator);
            baseHeight -= (float)renderTime(frameNum, timesServer[frameNum], lum / 2, lum / 2, lum / 2, baseHeight, tessellator);
            baseHeight -= (float)renderTime(frameNum, timesTerrain[frameNum], 0, lum, 0, baseHeight, tessellator);
            baseHeight -= (float)renderTime(frameNum, timesVisibility[frameNum], lum, lum, 0, baseHeight, tessellator);
            baseHeight -= (float)renderTime(frameNum, timesChunkUpdate[frameNum], lum, 0, 0, baseHeight, tessellator);
            baseHeight -= (float)renderTime(frameNum, timesChunkUpload[frameNum], lum, 0, lum, baseHeight, tessellator);
            baseHeight -= (float)renderTime(frameNum, timesScheduledExecutables[frameNum], 0, 0, lum, baseHeight, tessellator);
            baseHeight -= (float)renderTime(frameNum, timesTick[frameNum], 0, lum, lum, baseHeight, tessellator);
         }
      }

      renderTimeDivider(0, timesFrame.length, 33333333L, 196, 196, 196, (float)displayHeight, tessellator);
      renderTimeDivider(0, timesFrame.length, 16666666L, 196, 196, 196, (float)displayHeight, tessellator);
      tess.draw(tessellator);
      GlStateManager._enableCull();
      GlStateManager._depthMask(true);
      GlStateManager.enableTexture();
      matrixStackIn.m_85849_();
      int y60 = displayHeight - 80;
      int y30 = displayHeight - 160;
      String str30 = Config.isShowFrameTime() ? "33" : "30";
      String str60 = Config.isShowFrameTime() ? "17" : "60";
      graphicsIn.m_280056_(mc.f_91062_, str30, 1, y30, -3881788, false);
      graphicsIn.m_280056_(mc.f_91062_, str60, 1, y60, -3881788, false);
      RenderSystem.restoreProjectionMatrix();
      float lumMem = 1.0F - (float)((double)(System.currentTimeMillis() - MemoryMonitor.getStartTimeMs()) / 1000.0);
      lumMem = Config.limit(lumMem, 0.0F, 1.0F);
      int memColR = (int)C_188_.m_14179_(lumMem, 180.0F, 255.0F);
      int memColG = (int)C_188_.m_14179_(lumMem, 110.0F, 155.0F);
      int memColB = (int)C_188_.m_14179_(lumMem, 15.0F, 20.0F);
      int colMem = memColR << 16 | memColG << 8 | memColB;
      int posX = 512 / scaleFactor + 2;
      int posY = displayHeight / scaleFactor - 8;
      graphicsIn.m_280509_(posX - 1, posY - 1, posX + 50, posY + 10, -1605349296);
      graphicsIn.m_280488_(mc.f_91062_, " " + MemoryMonitor.getGcRateMb() + " MB/s", posX, posY, colMem);
      renderTimeNano = System.nanoTime() - timeRenderStartNano;
   }

   private static long renderTime(int frameNum, long time, int r, int g, int b, float baseHeight, C_3173_ tessellator) {
      long heightTime = time / 200000L;
      if (heightTime < 3L) {
         return 0L;
      } else {
         tessellator.m_167146_((float)frameNum + 0.5F, baseHeight - (float)heightTime + 0.5F, 0.0F).m_167129_(r, g, b, 255).m_338525_(0.0F, 1.0F, 0.0F);
         tessellator.m_167146_((float)frameNum + 0.5F, baseHeight + 0.5F, 0.0F).m_167129_(r, g, b, 255).m_338525_(0.0F, 1.0F, 0.0F);
         return heightTime;
      }
   }

   private static long renderTimeDivider(int frameStart, int frameEnd, long time, int r, int g, int b, float baseHeight, C_3173_ tessellator) {
      long heightTime = time / 200000L;
      if (heightTime < 3L) {
         return 0L;
      } else {
         tessellator.m_167146_((float)frameStart + 0.5F, baseHeight - (float)heightTime + 0.5F, 0.0F).m_167129_(r, g, b, 255).m_338525_(1.0F, 0.0F, 0.0F);
         tessellator.m_167146_((float)frameEnd + 0.5F, baseHeight - (float)heightTime + 0.5F, 0.0F).m_167129_(r, g, b, 255).m_338525_(1.0F, 0.0F, 0.0F);
         return heightTime;
      }
   }

   public static boolean isActive() {
      return active;
   }

   public static class TimerNano {
      public long timeStartNano = 0L;
      public long timeNano = 0L;

      public void start() {
         if (Lagometer.active) {
            if (this.timeStartNano == 0L) {
               this.timeStartNano = System.nanoTime();
            }
         }
      }

      public void end() {
         if (Lagometer.active) {
            if (this.timeStartNano != 0L) {
               this.timeNano = this.timeNano + (System.nanoTime() - this.timeStartNano);
               this.timeStartNano = 0L;
            }
         }
      }

      private void reset() {
         this.timeNano = 0L;
         this.timeStartNano = 0L;
      }
   }
}
