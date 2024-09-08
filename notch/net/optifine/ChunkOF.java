package net.optifine;

import net.minecraft.src.C_1560_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_507_;

public class ChunkOF extends C_2137_ {
   private boolean hasEntitiesOF;
   private boolean loadedOF;

   public ChunkOF(C_1596_ worldIn, C_1560_ chunkPosIn) {
      super(worldIn, chunkPosIn);
   }

   public void a(C_507_ entityIn) {
      this.hasEntitiesOF = true;
      super.m_6286_(entityIn);
   }

   public boolean hasEntities() {
      return this.hasEntitiesOF;
   }

   public void c(boolean loaded) {
      this.loadedOF = loaded;
      super.m_62913_(loaded);
   }

   public boolean isLoaded() {
      return this.loadedOF;
   }
}
