package net.optifine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.src.C_1607_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_200_;
import net.minecraft.src.C_203228_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_2690_;
import net.minecraft.src.C_2692_;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3041_;
import net.minecraft.src.C_3043_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_3429_;
import net.minecraft.src.C_3899_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5264_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_3043_.C_3044_;
import net.optifine.config.Option;
import net.optifine.render.RenderCache;
import net.optifine.util.GpuFrameTimer;
import net.optifine.util.GpuMemory;
import net.optifine.util.MemoryMonitor;
import net.optifine.util.NativeMemory;
import net.optifine.util.NumUtils;

public class QuickInfo {
   private static RenderCache renderCache = new RenderCache(100L);
   private static C_3391_ minecraft = Config.getMinecraft();
   private static C_3429_ font = minecraft.f_91062_;
   private static double gpuLoadSmooth = 0.0;
   private static McDebugInfo gpuDebugInfo = new McDebugInfo();
   private static int gpuPercCached;

   public static void render(C_279497_ graphicsIn) {
      if (!renderCache.drawCached(graphicsIn)) {
         renderCache.startRender(graphicsIn);
         renderLeft(graphicsIn);
         renderRight(graphicsIn);
         renderCache.stopRender(graphicsIn);
      }
   }

   private static void renderLeft(C_279497_ graphicsIn) {
      List<String> lines = new ArrayList();
      StringBuilder sb = new StringBuilder();
      C_3401_ opts = Config.getGameSettings();
      boolean fullLabel = !Option.isCompact(opts.ofQuickInfoLabels);
      boolean detailedCoords = Option.isDetailed(opts.ofQuickInfoLabels);
      boolean fps = !Option.isOff(opts.ofQuickInfoFps);
      boolean fpsMin = Option.isFull(opts.ofQuickInfoFps);
      boolean chunks = opts.ofQuickInfoChunks;
      boolean entities = opts.ofQuickInfoEntities;
      boolean particles = opts.ofQuickInfoParticles;
      boolean updates = opts.ofQuickInfoUpdates;
      boolean gpu = opts.ofQuickInfoGpu;
      if (fps) {
         addFps(next(sb), fullLabel, fpsMin);
      }

      if (chunks) {
         addChunks(next(sb), fullLabel);
      }

      if (entities) {
         addEntities(next(sb), fullLabel);
      }

      if (fullLabel) {
         newLine(lines, sb);
      }

      if (particles) {
         addParticles(next(sb), fullLabel);
      }

      if (updates) {
         addUpdates(next(sb), fullLabel);
      }

      if (gpu) {
         addGpu(next(sb), fullLabel);
      }

      newLine(lines, sb);
      boolean pos = !Option.isOff(opts.ofQuickInfoPos);
      boolean posRel = Option.isFull(opts.ofQuickInfoPos);
      boolean facing = !Option.isOff(opts.ofQuickInfoFacing);
      boolean yawPitch = Option.isFull(opts.ofQuickInfoFacing);
      if (pos) {
         addPos(next(sb), fullLabel, detailedCoords, posRel);
      }

      newLine(lines, sb);
      if (facing) {
         addFacing(next(sb), fullLabel, detailedCoords, facing, yawPitch);
      }

      newLine(lines, sb);
      boolean biome = opts.ofQuickInfoBiome;
      boolean light = opts.ofQuickInfoLight;
      if (biome) {
         addBiome(next(sb), fullLabel);
      }

      if (light) {
         addLight(next(sb), fullLabel);
      }

      newLine(lines, sb);
      render(graphicsIn, lines, false);
   }

   private static void renderRight(C_279497_ graphicsIn) {
      List<String> lines = new ArrayList();
      StringBuilder sb = new StringBuilder();
      C_3401_ opts = Config.getGameSettings();
      boolean fullLabel = !Option.isCompact(opts.ofQuickInfoLabels);
      boolean detailedCoords = Option.isDetailed(opts.ofQuickInfoLabels);
      boolean mem = !Option.isOff(opts.ofQuickInfoMemory);
      boolean memAlloc = Option.isFull(opts.ofQuickInfoMemory);
      boolean memNative = !Option.isOff(opts.ofQuickInfoNativeMemory);
      boolean memGpu = Option.isFull(opts.ofQuickInfoNativeMemory);
      if (mem) {
         addMem(next(sb), fullLabel);
      }

      if (memAlloc) {
         addMemAlloc(next(sb), fullLabel);
      }

      newLine(lines, sb);
      if (memNative) {
         addMemNative(next(sb), fullLabel);
      }

      if (memGpu) {
         addMemGpu(next(sb), fullLabel);
      }

      newLine(lines, sb);
      boolean targetBlock = !Option.isOff(opts.ofQuickInfoTargetBlock);
      boolean targetBlockPos = Option.isFull(opts.ofQuickInfoTargetBlock);
      if (targetBlock) {
         addTargetBlock(next(sb), fullLabel, targetBlockPos);
      }

      newLine(lines, sb);
      boolean targetFluid = !Option.isOff(opts.ofQuickInfoTargetFluid);
      boolean targetFluidPos = Option.isFull(opts.ofQuickInfoTargetFluid);
      if (targetFluid) {
         addTargetFluid(next(sb), fullLabel, targetFluidPos);
      }

      newLine(lines, sb);
      boolean targetEntity = !Option.isOff(opts.ofQuickInfoTargetEntity);
      boolean targetEntityPos = Option.isFull(opts.ofQuickInfoTargetEntity);
      if (targetEntity) {
         addTargetEntity(next(sb), fullLabel, detailedCoords, targetEntityPos);
      }

      newLine(lines, sb);
      render(graphicsIn, lines, true);
   }

   private static StringBuilder next(StringBuilder sb) {
      if (!sb.isEmpty()) {
         sb.append(", ");
      }

      return sb;
   }

   private static void newLine(List<String> lines, StringBuilder sb) {
      if (!sb.isEmpty()) {
         lines.add(sb.toString());
         sb.setLength(0);
      }
   }

   private static void render(C_279497_ graphicsIn, List<String> linesIn, boolean rightIn) {
      if (!linesIn.isEmpty()) {
         C_3401_ opts = Config.getGameSettings();
         boolean background = opts.ofQuickInfoBackground;
         boolean shadow = false;
         if (rightIn) {
            graphicsIn.m_280168_().m_85836_();
            graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, -10.0F);
         }

         int lineHeight = 9;
         if (background) {
            int lineY = 2;

            for (int i = 0; i < linesIn.size(); i++) {
               String s = (String)linesIn.get(i);
               if (!C_200_.m_14408_(s)) {
                  int lineWidth = font.m_92895_(s);
                  int x = rightIn ? graphicsIn.m_280182_() - 2 - lineWidth : 2;
                  graphicsIn.m_280509_(x - 1, lineY - 1, x + lineWidth + 1, lineY + lineHeight - 1, -1873784752);
                  lineY += lineHeight;
               }
            }
         }

         int lineY = 2;

         for (int ix = 0; ix < linesIn.size(); ix++) {
            String s = (String)linesIn.get(ix);
            if (!C_200_.m_14408_(s)) {
               int lineWidth = font.m_92895_(s);
               int x = rightIn ? graphicsIn.m_280182_() - 2 - lineWidth : 2;
               graphicsIn.m_280056_(font, s, x, lineY, -2039584, shadow);
               lineY += lineHeight;
            }
         }

         if (rightIn) {
            graphicsIn.m_280168_().m_85849_();
         }
      }
   }

   private static String getName(C_5265_ loc) {
      if (loc == null) {
         return "";
      } else {
         return loc.isDefaultNamespace() ? loc.m_135815_() : loc.toString();
      }
   }

   private static void addFps(StringBuilder sb, boolean fullLabel, boolean addFpsMin) {
      if (Config.isShowFrameTime()) {
         int fpsAvg = Config.getFpsAverage();
         appendDouble1(sb, 1000.0 / (double)Config.limit(fpsAvg, 1, Integer.MAX_VALUE));
         if (addFpsMin) {
            int fpsMin = Config.getFpsMin();
            sb.append('/');
            appendDouble1(sb, 1000.0 / (double)Config.limit(fpsMin, 1, Integer.MAX_VALUE));
         }

         sb.append(" ms");
      } else {
         int fpsAvg = Config.getFpsAverage();
         sb.append(Integer.toString(fpsAvg));
         if (addFpsMin) {
            int fpsMin = Config.getFpsMin();
            sb.append('/');
            sb.append(Integer.toString(fpsMin));
         }

         sb.append(" fps");
      }
   }

   private static void addChunks(StringBuilder sb, boolean fullLabel) {
      int chunks = minecraft.f_91060_.m_294574_();
      sb.append(fullLabel ? "Chunks: " : "C: ");
      sb.append(Integer.toString(chunks));
   }

   private static void addEntities(StringBuilder sb, boolean fullLabel) {
      int entities = minecraft.f_91060_.getCountEntitiesRendered();
      sb.append(fullLabel ? "Entities: " : "E: ");
      sb.append(Integer.toString(entities));
      int blockEntities = minecraft.f_91060_.getCountTileEntitiesRendered();
      sb.append('+');
      sb.append(Integer.toString(blockEntities));
   }

   private static void addParticles(StringBuilder sb, boolean fullLabel) {
      int particles = minecraft.f_91061_.getCountParticles();
      sb.append(fullLabel ? "Particles: " : "P: ");
      sb.append(Integer.toString(particles));
   }

   private static void addUpdates(StringBuilder sb, boolean fullLabel) {
      int updates = Config.getChunkUpdates();
      sb.append(fullLabel ? "Updates: " : "U: ");
      sb.append(Integer.toString(updates));
   }

   private static void addGpu(StringBuilder sb, boolean fullLabel) {
      double gpuLoad = GpuFrameTimer.getGpuLoad();
      gpuLoadSmooth = (gpuLoadSmooth * 4.0 + gpuLoad) / 5.0;
      int gpuPerc = (int)Math.round(gpuLoadSmooth * 100.0);
      gpuPerc = NumUtils.limit(gpuPerc, 0, 100);
      if (gpuPercCached <= 0 || gpuDebugInfo.isChanged()) {
         gpuPercCached = gpuPerc;
      }

      sb.append(fullLabel ? "GPU: " : "G: ");
      sb.append(Integer.toString(gpuPercCached));
      sb.append("%");
   }

   private static void addPos(StringBuilder sb, boolean fullLabel, boolean detailedCoords, boolean posRel) {
      C_507_ entity = minecraft.m_91288_();
      C_4675_ pos = entity.m_20183_();
      sb.append(fullLabel ? "Position: " : "Pos: ");
      if (detailedCoords) {
         sb.append(" (");
         appendDouble3(sb, entity.m_20185_());
         sb.append(", ");
         appendDouble3(sb, entity.m_20186_());
         sb.append(", ");
         appendDouble3(sb, entity.m_20189_());
         sb.append(")");
      } else {
         sb.append(Integer.toString(pos.u()));
         sb.append(", ");
         sb.append(Integer.toString(pos.v()));
         sb.append(", ");
         sb.append(Integer.toString(pos.w()));
      }

      if (posRel) {
         sb.append(" [");
         sb.append(Integer.toString(pos.u() & 15));
         sb.append(", ");
         sb.append(Integer.toString(pos.v() & 15));
         sb.append(", ");
         sb.append(Integer.toString(pos.w() & 15));
         sb.append("]");
      }
   }

   private static void addFacing(StringBuilder sb, boolean fullLabel, boolean detailedCoords, boolean facingXyz, boolean yawPitch) {
      C_507_ entity = minecraft.m_91288_();
      C_4687_ dir = entity.m_6350_();
      sb.append(fullLabel ? "Facing: " : "F: ");
      sb.append(dir.toString());
      if (facingXyz) {
         String dirXyz = getXyz(dir);
         sb.append(" [");
         sb.append(dirXyz);
         sb.append("]");
      }

      if (yawPitch) {
         float yaw = C_188_.m_14177_(entity.m_146908_());
         float pitch = C_188_.m_14177_(entity.m_146909_());
         if (detailedCoords) {
            sb.append(" (");
            appendDouble1(sb, (double)yaw);
            sb.append('/');
            appendDouble1(sb, (double)pitch);
            sb.append(')');
         } else {
            sb.append(" (");
            sb.append(Integer.toString(Math.round(yaw)));
            sb.append('/');
            sb.append(Integer.toString(Math.round(pitch)));
            sb.append(')');
         }
      }
   }

   private static String getXyz(C_4687_ dir) {
      switch (dir) {
         case NORTH:
            return "Z-";
         case SOUTH:
            return "Z+";
         case WEST:
            return "X-";
         case EAST:
            return "X+";
         case UP:
            return "Y+";
         case DOWN:
            return "Y-";
         default:
            return "?";
      }
   }

   private static void addBiome(StringBuilder sb, boolean fullLabel) {
      C_507_ entity = minecraft.m_91288_();
      C_4675_ pos = entity.m_20183_();
      C_203228_<C_1629_> biome = minecraft.f_91073_.t(pos);
      Optional<C_5264_<C_1629_>> key = biome.m_203543_();
      String name = getBiomeName(key);
      sb.append(fullLabel ? "Biome: " : "B: ");
      sb.append(name);
   }

   private static String getBiomeName(Optional<C_5264_<C_1629_>> key) {
      if (!key.isPresent()) {
         return "[unregistered]";
      } else {
         C_5265_ loc = ((C_5264_)key.get()).m_135782_();
         return loc.isDefaultNamespace() ? loc.m_135815_() : loc.toString();
      }
   }

   private static void addLight(StringBuilder sb, boolean fullLabel) {
      C_507_ entity = minecraft.m_91288_();
      C_4675_ pos = entity.m_20183_();
      C_3899_ world = minecraft.f_91073_;
      int lightSky = world.a(C_1607_.SKY, pos);
      int lightBlock = world.a(C_1607_.BLOCK, pos);
      if (fullLabel) {
         sb.append("Light: ");
         sb.append(Integer.toString(lightSky));
         sb.append(" sky, ");
         sb.append(Integer.toString(lightBlock));
         sb.append(" block");
      } else {
         sb.append("L: ");
         sb.append(Integer.toString(lightSky));
         sb.append("/");
         sb.append(Integer.toString(lightBlock));
      }
   }

   private static void addMem(StringBuilder sb, boolean fullLabel) {
      long max = Runtime.getRuntime().maxMemory();
      long total = Runtime.getRuntime().totalMemory();
      long free = Runtime.getRuntime().freeMemory();
      long used = total - free;
      sb.append(fullLabel ? "Memory: " : "M: ");
      sb.append(Integer.toString(bytesToMb(used)));
      sb.append("/");
      sb.append(Integer.toString(bytesToMb(max)));
      if (fullLabel) {
         sb.append(" MB");
      }
   }

   private static void addMemAlloc(StringBuilder sb, boolean fullLabel) {
      int allocMb = (int)MemoryMonitor.getAllocationRateAvgMb();
      sb.append(fullLabel ? "Allocation: " : "A: ");
      sb.append(Integer.toString(allocMb));
      if (fullLabel) {
         sb.append(" MB/s");
      }
   }

   private static void addMemNative(StringBuilder sb, boolean fullLabel) {
      long bufferAllocated = NativeMemory.getBufferAllocated();
      long bufferMaximum = NativeMemory.getBufferMaximum();
      long imageAllocated = NativeMemory.getImageAllocated();
      sb.append(fullLabel ? "Native: " : "N: ");
      sb.append(Integer.toString(bytesToMb(bufferAllocated)));
      sb.append("/");
      sb.append(Integer.toString(bytesToMb(bufferMaximum)));
      sb.append("+");
      sb.append(Integer.toString(bytesToMb(imageAllocated)));
      if (fullLabel) {
         sb.append(" MB");
      }
   }

   private static void addMemGpu(StringBuilder sb, boolean fullLabel) {
      long gpuBufferAllocated = GpuMemory.getBufferAllocated();
      long gpuTextureAllocated = GpuMemory.getTextureAllocated();
      sb.append(fullLabel ? "GPU: " : "G: ");
      sb.append(Integer.toString(bytesToMb(gpuBufferAllocated)));
      sb.append("+");
      sb.append(Integer.toString(bytesToMb(gpuTextureAllocated)));
      if (fullLabel) {
         sb.append(" MB");
      }
   }

   private static int bytesToMb(long bytes) {
      return (int)(bytes / 1024L / 1024L);
   }

   private static void addTargetBlock(StringBuilder sb, boolean fullLabel, boolean showPos) {
      C_507_ entity = minecraft.m_91288_();
      double reachDist = minecraft.f_91074_.gy();
      C_3043_ rayTrace = entity.m_19907_(reachDist, 0.0F, false);
      if (rayTrace.m_6662_() == C_3044_.BLOCK) {
         C_4675_ pos = ((C_3041_)rayTrace).m_82425_();
         C_2064_ state = minecraft.f_91073_.m_8055_(pos);
         sb.append(fullLabel ? "Target Block: " : "TB: ");
         sb.append(getName(state.getBlockLocation()));
         if (showPos) {
            sb.append(" (");
            sb.append(Integer.toString(pos.u()));
            sb.append(", ");
            sb.append(Integer.toString(pos.v()));
            sb.append(", ");
            sb.append(Integer.toString(pos.w()));
            sb.append(")");
         }
      }
   }

   private static void addTargetFluid(StringBuilder sb, boolean fullLabel, boolean showPos) {
      C_507_ entity = minecraft.m_91288_();
      double reachDist = minecraft.f_91074_.gy();
      C_3043_ rayTrace = entity.m_19907_(reachDist, 0.0F, true);
      if (rayTrace.m_6662_() == C_3044_.BLOCK) {
         C_4675_ pos = ((C_3041_)rayTrace).m_82425_();
         C_2064_ state = minecraft.f_91073_.m_8055_(pos);
         C_2690_ fluid = state.m_60819_().m_76152_();
         if (fluid == C_2692_.f_76191_) {
            return;
         }

         C_5265_ loc = C_256712_.f_257020_.m_7981_(fluid);
         String name = getName(loc);
         sb.append(fullLabel ? "Target Fluid: " : "TF: ");
         sb.append(name);
         if (showPos) {
            sb.append(" (");
            sb.append(Integer.toString(pos.u()));
            sb.append(", ");
            sb.append(Integer.toString(pos.v()));
            sb.append(", ");
            sb.append(Integer.toString(pos.w()));
            sb.append(")");
         }
      }
   }

   private static void addTargetEntity(StringBuilder sb, boolean fullLabel, boolean detailedCoords, boolean showPos) {
      C_507_ entity = minecraft.f_91076_;
      if (entity != null) {
         C_4675_ pos = entity.m_20183_();
         C_5265_ loc = C_256712_.f_256780_.m_7981_(entity.m_6095_());
         String name = getName(loc);
         if (loc != null) {
            sb.append(fullLabel ? "Target Entity: " : "TE: ");
            sb.append(name);
            if (showPos) {
               if (detailedCoords) {
                  sb.append(" (");
                  appendDouble3(sb, entity.m_20185_());
                  sb.append(", ");
                  appendDouble3(sb, entity.m_20186_());
                  sb.append(", ");
                  appendDouble3(sb, entity.m_20189_());
                  sb.append(")");
               } else {
                  sb.append(" (");
                  sb.append(Integer.toString(pos.u()));
                  sb.append(", ");
                  sb.append(Integer.toString(pos.v()));
                  sb.append(", ");
                  sb.append(Integer.toString(pos.w()));
                  sb.append(")");
               }
            }
         }
      }
   }

   private static void appendDouble1(StringBuilder sb, double num) {
      num = (double)Math.round(num * 10.0) / 10.0;
      sb.append(num);
   }

   private static void appendDouble3(StringBuilder sb, double num) {
      num = (double)Math.round(num * 1000.0) / 1000.0;
      sb.append(num);
      if (sb.charAt(sb.length() - 2) == '.') {
         sb.append('0');
      }

      if (sb.charAt(sb.length() - 3) == '.') {
         sb.append('0');
      }
   }
}
