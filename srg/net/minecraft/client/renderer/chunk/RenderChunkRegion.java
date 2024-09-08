package net.minecraft.client.renderer.chunk;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.model.data.ModelDataManager;
import net.optifine.override.ChunkCacheOF;

public class RenderChunkRegion implements BlockAndTintGetter {
   public static final int f_337396_ = 1;
   public static final int f_337092_ = 3;
   private final int f_336971_;
   private final int f_336993_;
   protected final RenderChunk[] f_112905_;
   protected final Level f_112908_;
   private SectionPos sectionPos;

   RenderChunkRegion(Level worldIn, int chunkStartXIn, int chunkStartYIn, RenderChunk[] chunksIn) {
      this(worldIn, chunkStartXIn, chunkStartYIn, chunksIn, null);
   }

   RenderChunkRegion(Level worldIn, int chunkStartXIn, int chunkStartYIn, RenderChunk[] chunksIn, SectionPos sectionPosIn) {
      this.f_112908_ = worldIn;
      this.f_336971_ = chunkStartXIn;
      this.f_336993_ = chunkStartYIn;
      this.f_112905_ = chunksIn;
      this.sectionPos = sectionPosIn;
   }

   public BlockState m_8055_(BlockPos pos) {
      return this.m_340417_(SectionPos.m_123171_(pos.m_123341_()), SectionPos.m_123171_(pos.m_123343_())).m_200453_(pos);
   }

   public FluidState m_6425_(BlockPos pos) {
      return this.m_340417_(SectionPos.m_123171_(pos.m_123341_()), SectionPos.m_123171_(pos.m_123343_())).m_200453_(pos).m_60819_();
   }

   public float m_7717_(Direction directionIn, boolean shadeIn) {
      return this.f_112908_.m_7717_(directionIn, shadeIn);
   }

   public LevelLightEngine m_5518_() {
      return this.f_112908_.m_5518_();
   }

   @Nullable
   public BlockEntity m_7702_(BlockPos pos) {
      return this.m_340417_(SectionPos.m_123171_(pos.m_123341_()), SectionPos.m_123171_(pos.m_123343_())).m_200451_(pos);
   }

   public RenderChunk m_340417_(int x, int z) {
      return this.f_112905_[m_339116_(this.f_336971_, this.f_336993_, x, z)];
   }

   public int m_6171_(BlockPos blockPosIn, ColorResolver colorResolverIn) {
      return this.f_112908_.m_6171_(blockPosIn, colorResolverIn);
   }

   public int m_141937_() {
      return this.f_112908_.m_141937_();
   }

   public int m_141928_() {
      return this.f_112908_.m_141928_();
   }

   public static int m_339116_(int xMin, int zMin, int x, int z) {
      return x - xMin + (z - zMin) * 3;
   }

   public Biome getBiome(BlockPos pos) {
      return (Biome)this.f_112908_.m_204166_(pos).m_203334_();
   }

   public LevelChunk getLevelChunk(int cx, int cz) {
      return this.m_340417_(cx, cz).getChunk();
   }

   public void finish() {
      for (int i = 0; i < this.f_112905_.length; i++) {
         RenderChunk rc = this.f_112905_[i];
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
      return this.f_112908_ instanceof ClientLevel clientWorld ? clientWorld.getShade(normalX, normalY, normalZ, shade) : 1.0F;
   }

   public ModelDataManager getModelDataManager() {
      return this.f_112908_ instanceof ClientLevel clientWorld ? clientWorld.getModelDataManager() : null;
   }
}
