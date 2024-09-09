package net.minecraft.src;

import javax.annotation.Nullable;
import net.minecraftforge.client.model.data.ModelDataManager;
import net.optifine.override.ChunkCacheOF;

public class C_4269_ implements C_1557_ {
   public static final int f_337396_ = 1;
   public static final int f_337092_ = 3;
   private final int f_336971_;
   private final int f_336993_;
   protected final C_200008_[] f_112905_;
   protected final C_1596_ f_112908_;
   private C_4710_ sectionPos;

   C_4269_(C_1596_ worldIn, int chunkStartXIn, int chunkStartYIn, C_200008_[] chunksIn) {
      this(worldIn, chunkStartXIn, chunkStartYIn, chunksIn, (C_4710_)null);
   }

   C_4269_(C_1596_ worldIn, int chunkStartXIn, int chunkStartYIn, C_200008_[] chunksIn, C_4710_ sectionPosIn) {
      this.f_112908_ = worldIn;
      this.f_336971_ = chunkStartXIn;
      this.f_336993_ = chunkStartYIn;
      this.f_112905_ = chunksIn;
      this.sectionPos = sectionPosIn;
   }

   public C_2064_ m_8055_(C_4675_ pos) {
      return this.m_340417_(C_4710_.m_123171_(pos.u()), C_4710_.m_123171_(pos.w())).m_200453_(pos);
   }

   public C_2691_ m_6425_(C_4675_ pos) {
      return this.m_340417_(C_4710_.m_123171_(pos.u()), C_4710_.m_123171_(pos.w())).m_200453_(pos).m_60819_();
   }

   public float m_7717_(C_4687_ directionIn, boolean shadeIn) {
      return this.f_112908_.a(directionIn, shadeIn);
   }

   public C_2681_ m_5518_() {
      return this.f_112908_.m_5518_();
   }

   @Nullable
   public C_1991_ m_7702_(C_4675_ pos) {
      return this.m_340417_(C_4710_.m_123171_(pos.u()), C_4710_.m_123171_(pos.w())).m_200451_(pos);
   }

   public C_200008_ m_340417_(int x, int z) {
      return this.f_112905_[m_339116_(this.f_336971_, this.f_336993_, x, z)];
   }

   public int m_6171_(C_4675_ blockPosIn, C_4982_ colorResolverIn) {
      return this.f_112908_.a(blockPosIn, colorResolverIn);
   }

   public int m_141937_() {
      return this.f_112908_.I_();
   }

   public int m_141928_() {
      return this.f_112908_.J_();
   }

   public static int m_339116_(int xMin, int zMin, int x, int z) {
      return x - xMin + (z - zMin) * 3;
   }

   public C_1629_ getBiome(C_4675_ pos) {
      return (C_1629_)this.f_112908_.t(pos).m_203334_();
   }

   public C_2137_ getLevelChunk(int cx, int cz) {
      return this.m_340417_(cx, cz).getChunk();
   }

   public void finish() {
      for(int i = 0; i < this.f_112905_.length; ++i) {
         C_200008_ rc = this.f_112905_[i];
         rc.finish();
      }

   }

   public ChunkCacheOF makeChunkCacheOF() {
      return this.sectionPos == null ? null : new ChunkCacheOF(this, this.sectionPos);
   }

   public int getMinChunkX() {
      return this.f_336971_;
   }

   public int getMinChunkZ() {
      return this.f_336993_;
   }

   public float getShade(float normalX, float normalY, float normalZ, boolean shade) {
      if (this.f_112908_ instanceof C_3899_) {
         C_3899_ clientWorld = (C_3899_)this.f_112908_;
         return clientWorld.getShade(normalX, normalY, normalZ, shade);
      } else {
         return 1.0F;
      }
   }

   public ModelDataManager getModelDataManager() {
      if (this.f_112908_ instanceof C_3899_) {
         C_3899_ clientWorld = (C_3899_)this.f_112908_;
         return clientWorld.getModelDataManager();
      } else {
         return null;
      }
   }
}
