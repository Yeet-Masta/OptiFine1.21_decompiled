package net.minecraft.src;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;

public class C_200009_ {
   private final Long2ObjectMap<C_200009_.C_200010_> f_200460_ = new Long2ObjectOpenHashMap();

   @Nullable
   public C_4269_ m_200465_(C_1596_ worldIn, C_4710_ sectionPosIn) {
      C_200009_.C_200010_ renderregioncache$chunkinfo = this.m_340552_(worldIn, sectionPosIn.m_123170_(), sectionPosIn.m_123222_());
      if (renderregioncache$chunkinfo.m_200480_().c(sectionPosIn.m_123206_())) {
         return null;
      } else {
         int i = sectionPosIn.m_123170_() - 1;
         int j = sectionPosIn.m_123222_() - 1;
         int k = sectionPosIn.m_123170_() + 1;
         int l = sectionPosIn.m_123222_() + 1;
         C_200008_[] arenderchunk = new C_200008_[9];

         for (int i1 = j; i1 <= l; i1++) {
            for (int j1 = i; j1 <= k; j1++) {
               int k1 = C_4269_.m_339116_(i, j, j1, i1);
               C_200009_.C_200010_ renderregioncache$chunkinfo1 = j1 == sectionPosIn.m_123170_() && i1 == sectionPosIn.m_123222_()
                  ? renderregioncache$chunkinfo
                  : this.m_340552_(worldIn, j1, i1);
               arenderchunk[k1] = renderregioncache$chunkinfo1.m_200481_();
            }
         }

         return new C_4269_(worldIn, i, j, arenderchunk, sectionPosIn);
      }
   }

   private C_200009_.C_200010_ m_340552_(C_1596_ worldIn, int x, int z) {
      return (C_200009_.C_200010_)this.f_200460_
         .computeIfAbsent(
            C_1560_.m_45589_(x, z), longPosIn -> new C_200009_.C_200010_(worldIn.m_6325_(C_1560_.m_45592_(longPosIn), C_1560_.m_45602_(longPosIn)))
         );
   }

   static final class C_200010_ {
      private final C_2137_ f_200476_;
      @Nullable
      private C_200008_ f_200477_;

      C_200010_(C_2137_ chunkIn) {
         this.f_200476_ = chunkIn;
      }

      public C_2137_ m_200480_() {
         return this.f_200476_;
      }

      public C_200008_ m_200481_() {
         if (this.f_200477_ == null) {
            this.f_200477_ = new C_200008_(this.f_200476_);
         }

         return this.f_200477_;
      }
   }
}
