package net.minecraft.client.renderer.chunk;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.levelgen.DebugLevelSource;

class RenderChunk {
   private final Map f_200441_;
   @Nullable
   private final List f_200442_;
   private final boolean f_200443_;
   private final LevelChunk f_200444_;

   RenderChunk(LevelChunk chunkIn) {
      this.f_200444_ = chunkIn;
      this.f_200443_ = chunkIn.m_62953_().m_46659_();
      this.f_200441_ = ImmutableMap.copyOf(chunkIn.m_62954_());
      if (chunkIn instanceof EmptyLevelChunk) {
         this.f_200442_ = null;
      } else {
         LevelChunkSection[] alevelchunksection = chunkIn.m_7103_();
         this.f_200442_ = new ArrayList(alevelchunksection.length);
         LevelChunkSection[] var3 = alevelchunksection;
         int var4 = alevelchunksection.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            LevelChunkSection levelchunksection = var3[var5];
            this.f_200442_.add(levelchunksection.m_188008_() ? null : levelchunksection.m_63019_().m_199931_());
         }
      }

   }

   @Nullable
   public BlockEntity m_200451_(BlockPos posIn) {
      return (BlockEntity)this.f_200441_.get(posIn);
   }

   public BlockState m_200453_(BlockPos posIn) {
      int i = posIn.m_123341_();
      int j = posIn.m_123342_();
      int k = posIn.m_123343_();
      if (this.f_200443_) {
         BlockState blockstate = null;
         if (j == 60) {
            blockstate = Blocks.f_50375_.m_49966_();
         }

         if (j == 70) {
            blockstate = DebugLevelSource.m_64148_(i, k);
         }

         return blockstate == null ? Blocks.f_50016_.m_49966_() : blockstate;
      } else if (this.f_200442_ == null) {
         return Blocks.f_50016_.m_49966_();
      } else {
         try {
            int l = this.f_200444_.m_151564_(j);
            if (l >= 0 && l < this.f_200442_.size()) {
               PalettedContainer palettedcontainer = (PalettedContainer)this.f_200442_.get(l);
               if (palettedcontainer != null) {
                  return (BlockState)palettedcontainer.m_63087_(i & 15, j & 15, k & 15);
               }
            }

            return Blocks.f_50016_.m_49966_();
         } catch (Throwable var8) {
            CrashReport crashreport = CrashReport.m_127521_(var8, "Getting block state");
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Block being got");
            crashreportcategory.m_128165_("Location", () -> {
               return CrashReportCategory.m_178942_(this.f_200444_, i, j, k);
            });
            throw new ReportedException(crashreport);
         }
      }
   }

   public LevelChunk getChunk() {
      return this.f_200444_;
   }

   public void finish() {
      if (this.f_200442_ != null) {
         for(int i = 0; i < this.f_200442_.size(); ++i) {
            PalettedContainer section = (PalettedContainer)this.f_200442_.get(i);
            if (section != null) {
               section.finish();
            }
         }

      }
   }
}
