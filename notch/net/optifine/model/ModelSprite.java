package net.optifine.model;

import net.minecraft.src.C_188_;
import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_3181_.C_3183_;
import net.optifine.util.MathUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class ModelSprite {
   private C_3889_ modelRenderer = null;
   private float textureOffsetX = 0.0F;
   private float textureOffsetY = 0.0F;
   private float posX = 0.0F;
   private float posY = 0.0F;
   private float posZ = 0.0F;
   private int sizeX = 0;
   private int sizeY = 0;
   private int sizeZ = 0;
   private float sizeAdd = 0.0F;
   private float minU = 0.0F;
   private float minV = 0.0F;
   private float maxU = 0.0F;
   private float maxV = 0.0F;

   public ModelSprite(
      C_3889_ modelRenderer, float textureOffsetX, float textureOffsetY, float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, float sizeAdd
   ) {
      this.modelRenderer = modelRenderer;
      this.textureOffsetX = textureOffsetX;
      this.textureOffsetY = textureOffsetY;
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.sizeX = sizeX;
      this.sizeY = sizeY;
      this.sizeZ = sizeZ;
      this.sizeAdd = sizeAdd;
      this.minU = textureOffsetX / modelRenderer.textureWidth;
      this.minV = textureOffsetY / modelRenderer.textureHeight;
      this.maxU = (textureOffsetX + (float)sizeX) / modelRenderer.textureWidth;
      this.maxV = (textureOffsetY + (float)sizeY) / modelRenderer.textureHeight;
   }

   public void render(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
      float scale = 0.0625F;
      matrixStackIn.m_252880_(this.posX * scale, this.posY * scale, this.posZ * scale);
      float rMinU = this.minU;
      float rMaxU = this.maxU;
      float rMinV = this.minV;
      float rMaxV = this.maxV;
      if (this.modelRenderer.mirror) {
         rMinU = this.maxU;
         rMaxU = this.minU;
      }

      if (this.modelRenderer.mirrorV) {
         rMinV = this.maxV;
         rMaxV = this.minV;
      }

      renderItemIn2D(
         matrixStackIn,
         bufferIn,
         rMinU,
         rMinV,
         rMaxU,
         rMaxV,
         this.sizeX,
         this.sizeY,
         scale * (float)this.sizeZ,
         this.modelRenderer.textureWidth,
         this.modelRenderer.textureHeight,
         packedLightIn,
         packedOverlayIn,
         colorIn
      );
      matrixStackIn.m_252880_(-this.posX * scale, -this.posY * scale, -this.posZ * scale);
   }

   public static void renderItemIn2D(
      C_3181_ matrixStackIn,
      C_3187_ bufferIn,
      float minU,
      float minV,
      float maxU,
      float maxV,
      int sizeX,
      int sizeY,
      float width,
      float texWidth,
      float texHeight,
      int packedLightIn,
      int packedOverlayIn,
      int colorIn
   ) {
      if (width < 6.25E-4F) {
         width = 6.25E-4F;
      }

      float dU = maxU - minU;
      float dV = maxV - minV;
      float dimX = C_188_.m_14154_(dU) * (texWidth / 16.0F);
      float dimY = C_188_.m_14154_(dV) * (texHeight / 16.0F);
      float normX = 0.0F;
      float normY = 0.0F;
      float normZ = -1.0F;
      addVertex(matrixStackIn, bufferIn, 0.0F, dimY, 0.0F, colorIn, minU, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      addVertex(matrixStackIn, bufferIn, dimX, dimY, 0.0F, colorIn, maxU, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      addVertex(matrixStackIn, bufferIn, dimX, 0.0F, 0.0F, colorIn, maxU, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      addVertex(matrixStackIn, bufferIn, 0.0F, 0.0F, 0.0F, colorIn, minU, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      normX = 0.0F;
      normY = 0.0F;
      normZ = 1.0F;
      addVertex(matrixStackIn, bufferIn, 0.0F, 0.0F, width, colorIn, minU, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      addVertex(matrixStackIn, bufferIn, dimX, 0.0F, width, colorIn, maxU, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      addVertex(matrixStackIn, bufferIn, dimX, dimY, width, colorIn, maxU, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      addVertex(matrixStackIn, bufferIn, 0.0F, dimY, width, colorIn, minU, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      float var8x = 0.5F * dU / (float)sizeX;
      float var9x = 0.5F * dV / (float)sizeY;
      normX = -1.0F;
      normY = 0.0F;
      normZ = 0.0F;

      for (int var10x = 0; var10x < sizeX; var10x++) {
         float var11x = (float)var10x / (float)sizeX;
         float var12x = minU + dU * var11x + var8x;
         addVertex(matrixStackIn, bufferIn, var11x * dimX, dimY, width, colorIn, var12x, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var11x * dimX, dimY, 0.0F, colorIn, var12x, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var11x * dimX, 0.0F, 0.0F, colorIn, var12x, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var11x * dimX, 0.0F, width, colorIn, var12x, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      }

      normX = 1.0F;
      normY = 0.0F;
      normZ = 0.0F;

      for (int var42 = 0; var42 < sizeX; var42++) {
         float var11x = (float)var42 / (float)sizeX;
         float var12x = minU + dU * var11x + var8x;
         float var13x = var11x + 1.0F / (float)sizeX;
         addVertex(matrixStackIn, bufferIn, var13x * dimX, 0.0F, width, colorIn, var12x, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var13x * dimX, 0.0F, 0.0F, colorIn, var12x, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var13x * dimX, dimY, 0.0F, colorIn, var12x, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var13x * dimX, dimY, width, colorIn, var12x, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      }

      normX = 0.0F;
      normY = 1.0F;
      normZ = 0.0F;

      for (int var43 = 0; var43 < sizeY; var43++) {
         float var11x = (float)var43 / (float)sizeY;
         float var12x = minV + dV * var11x + var9x;
         float var13x = var11x + 1.0F / (float)sizeY;
         addVertex(matrixStackIn, bufferIn, 0.0F, var13x * dimY, width, colorIn, minU, var12x, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, dimX, var13x * dimY, width, colorIn, maxU, var12x, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, dimX, var13x * dimY, 0.0F, colorIn, maxU, var12x, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, 0.0F, var13x * dimY, 0.0F, colorIn, minU, var12x, packedOverlayIn, packedLightIn, normX, normY, normZ);
      }

      normX = 0.0F;
      normY = -1.0F;
      normZ = 0.0F;

      for (int var44 = 0; var44 < sizeY; var44++) {
         float var11x = (float)var44 / (float)sizeY;
         float var12x = minV + dV * var11x + var9x;
         addVertex(matrixStackIn, bufferIn, dimX, var11x * dimY, width, colorIn, maxU, var12x, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, 0.0F, var11x * dimY, width, colorIn, minU, var12x, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, 0.0F, var11x * dimY, 0.0F, colorIn, minU, var12x, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, dimX, var11x * dimY, 0.0F, colorIn, maxU, var12x, packedOverlayIn, packedLightIn, normX, normY, normZ);
      }
   }

   static void addVertex(
      C_3181_ matrixStackIn,
      C_3187_ bufferIn,
      float x,
      float y,
      float z,
      int colorIn,
      float texU,
      float texV,
      int overlayUV,
      int lightmapUV,
      float normalX,
      float normalY,
      float normalZ
   ) {
      C_3183_ matrixEntry = matrixStackIn.m_85850_();
      Matrix4f matrix4f = matrixEntry.m_252922_();
      Matrix3f matrixNormal = matrixEntry.m_252943_();
      float xn = MathUtils.getTransformX(matrixNormal, normalX, normalY, normalZ);
      float yn = MathUtils.getTransformY(matrixNormal, normalX, normalY, normalZ);
      float zn = MathUtils.getTransformZ(matrixNormal, normalX, normalY, normalZ);
      float xt = MathUtils.getTransformX(matrix4f, x, y, z);
      float yt = MathUtils.getTransformY(matrix4f, x, y, z);
      float zt = MathUtils.getTransformZ(matrix4f, x, y, z);
      bufferIn.m_338367_(xt, yt, zt, colorIn, texU, texV, overlayUV, lightmapUV, xn, yn, zn);
   }
}
