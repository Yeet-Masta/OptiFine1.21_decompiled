package net.minecraft.src;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

class C_200008_ {
   private final Map<C_4675_, C_1991_> f_200441_;
   @Nullable
   private final List<C_2145_<C_2064_>> f_200442_;
   private final boolean f_200443_;
   private final C_2137_ f_200444_;

   C_200008_(C_2137_ chunkIn) {
      this.f_200444_ = chunkIn;
      this.f_200443_ = chunkIn.m_62953_().m_46659_();
      this.f_200441_ = ImmutableMap.copyOf(chunkIn.m_62954_());
      if (chunkIn instanceof C_2132_) {
         this.f_200442_ = null;
      } else {
         C_2139_[] alevelchunksection = chunkIn.d();
         this.f_200442_ = new ArrayList(alevelchunksection.length);

         for (C_2139_ levelchunksection : alevelchunksection) {
            this.f_200442_.add(levelchunksection.m_188008_() ? null : levelchunksection.m_63019_().m_199931_());
         }
      }
   }

   @Nullable
   public C_1991_ m_200451_(C_4675_ posIn) {
      return (C_1991_)this.f_200441_.get(posIn);
   }

   public C_2064_ m_200453_(C_4675_ posIn) {
      int i = posIn.u();
      int j = posIn.v();
      int k = posIn.w();
      if (this.f_200443_) {
         C_2064_ blockstate = null;
         if (j == 60) {
            blockstate = C_1710_.f_50375_.m_49966_();
         }

         if (j == 70) {
            blockstate = C_2184_.m_64148_(i, k);
         }

         return blockstate == null ? C_1710_.f_50016_.m_49966_() : blockstate;
      } else if (this.f_200442_ == null) {
         return C_1710_.f_50016_.m_49966_();
      } else {
         try {
            int l = this.f_200444_.e(j);
            if (l >= 0 && l < this.f_200442_.size()) {
               C_2145_<C_2064_> palettedcontainer = (C_2145_<C_2064_>)this.f_200442_.get(l);
               if (palettedcontainer != null) {
                  return palettedcontainer.m_63087_(i & 15, j & 15, k & 15);
               }
            }

            return C_1710_.f_50016_.m_49966_();
         } catch (Throwable var8) {
            C_4883_ crashreport = C_4883_.m_127521_(var8, "Getting block state");
            C_4909_ crashreportcategory = crashreport.m_127514_("Block being got");
            crashreportcategory.m_128165_("Location", () -> C_4909_.m_178942_(this.f_200444_, i, j, k));
            throw new C_5204_(crashreport);
         }
      }
   }

   public C_2137_ getChunk() {
      return this.f_200444_;
   }

   public void finish() {
      if (this.f_200442_ != null) {
         for (int i = 0; i < this.f_200442_.size(); i++) {
            C_2145_<C_2064_> section = (C_2145_<C_2064_>)this.f_200442_.get(i);
            if (section != null) {
               section.finish();
            }
         }
      }
   }
}
