package net.optifine;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class ChunkOF extends LevelChunk {
   private boolean hasEntitiesOF;
   private boolean loadedOF;

   public ChunkOF(Level worldIn, net.minecraft.world.level.ChunkPos chunkPosIn) {
      super(worldIn, chunkPosIn);
   }

   public void m_6286_(Entity entityIn) {
      this.hasEntitiesOF = true;
      super.m_6286_(entityIn);
   }

   public boolean hasEntities() {
      return this.hasEntitiesOF;
   }

   public void m_62913_(boolean loaded) {
      this.loadedOF = loaded;
      super.m_62913_(loaded);
   }

   public boolean isLoaded() {
      return this.loadedOF;
   }
}
