import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_2132_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_2184_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_5204_;

class RenderChunk {
   private final Map<C_4675_, BlockEntity> a;
   @Nullable
   private final List<PalettedContainer<BlockState>> b;
   private final boolean c;
   private final C_2137_ d;

   RenderChunk(C_2137_ chunkIn) {
      this.d = chunkIn;
      this.c = chunkIn.m_62953_().m_46659_();
      this.a = ImmutableMap.copyOf(chunkIn.m_62954_());
      if (chunkIn instanceof C_2132_) {
         this.b = null;
      } else {
         LevelChunkSection[] alevelchunksection = chunkIn.d();
         this.b = new ArrayList(alevelchunksection.length);

         for (LevelChunkSection levelchunksection : alevelchunksection) {
            this.b.add(levelchunksection.c() ? null : levelchunksection.h().d());
         }
      }
   }

   @Nullable
   public BlockEntity a(C_4675_ posIn) {
      return (BlockEntity)this.a.get(posIn);
   }

   public BlockState b(C_4675_ posIn) {
      int i = posIn.m_123341_();
      int j = posIn.m_123342_();
      int k = posIn.m_123343_();
      if (this.c) {
         BlockState blockstate = null;
         if (j == 60) {
            blockstate = C_1710_.f_50375_.o();
         }

         if (j == 70) {
            blockstate = C_2184_.a(i, k);
         }

         return blockstate == null ? C_1710_.f_50016_.o() : blockstate;
      } else if (this.b == null) {
         return C_1710_.f_50016_.o();
      } else {
         try {
            int l = this.d.m_151564_(j);
            if (l >= 0 && l < this.b.size()) {
               PalettedContainer<BlockState> palettedcontainer = (PalettedContainer<BlockState>)this.b.get(l);
               if (palettedcontainer != null) {
                  return palettedcontainer.m_63087_(i & 15, j & 15, k & 15);
               }
            }

            return C_1710_.f_50016_.o();
         } catch (Throwable var8) {
            CrashReport crashreport = CrashReport.a(var8, "Getting block state");
            C_4909_ crashreportcategory = crashreport.a("Block being got");
            crashreportcategory.m_128165_("Location", () -> C_4909_.m_178942_(this.d, i, j, k));
            throw new C_5204_(crashreport);
         }
      }
   }

   public C_2137_ getChunk() {
      return this.d;
   }

   public void finish() {
      if (this.b != null) {
         for (int i = 0; i < this.b.size(); i++) {
            PalettedContainer<BlockState> section = (PalettedContainer<BlockState>)this.b.get(i);
            if (section != null) {
               section.finish();
            }
         }
      }
   }
}
