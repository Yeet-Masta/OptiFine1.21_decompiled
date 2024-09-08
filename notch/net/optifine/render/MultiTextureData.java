package net.optifine.render;

import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4486_;
import net.optifine.util.ArrayUtils;

public class MultiTextureData {
   private SpriteRenderData[] spriteRenderDatas;
   private int vertexCount;
   private C_4168_ blockLayer;
   private C_4486_[] quadSprites;
   private SpriteRenderData[] spriteRenderDatasSorted;

   public MultiTextureData(SpriteRenderData[] spriteRenderDatas) {
      this.spriteRenderDatas = spriteRenderDatas;
   }

   public SpriteRenderData[] getSpriteRenderDatas() {
      return this.spriteRenderDatas;
   }

   public void setResortParameters(int vertexCountIn, C_4168_ blockLayerIn, C_4486_[] quadSpritesIn) {
      this.vertexCount = vertexCountIn;
      this.blockLayer = blockLayerIn;
      this.quadSprites = quadSpritesIn;
   }

   public void prepareSort(MultiTextureBuilder multiTextureBuilder, int[] quadOrdering) {
      this.spriteRenderDatasSorted = multiTextureBuilder.buildRenderDatas(this.vertexCount, this.blockLayer, this.quadSprites, quadOrdering);
   }

   public void applySort() {
      if (this.spriteRenderDatasSorted != null) {
         this.spriteRenderDatas = this.spriteRenderDatasSorted;
         this.spriteRenderDatasSorted = null;
      }
   }

   public String toString() {
      return ArrayUtils.arrayToString((Object[])this.spriteRenderDatas);
   }
}
