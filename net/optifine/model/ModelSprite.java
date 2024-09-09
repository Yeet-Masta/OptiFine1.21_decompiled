package net.optifine.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.optifine.util.MathUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class ModelSprite {
   private ModelPart modelRenderer = null;
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

   public ModelSprite(ModelPart modelRenderer, float textureOffsetX, float textureOffsetY, float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, float sizeAdd) {
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

   public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
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

      renderItemIn2D(matrixStackIn, bufferIn, rMinU, rMinV, rMaxU, rMaxV, this.sizeX, this.sizeY, scale * (float)this.sizeZ, this.modelRenderer.textureWidth, this.modelRenderer.textureHeight, packedLightIn, packedOverlayIn, colorIn);
      matrixStackIn.m_252880_(-this.posX * scale, -this.posY * scale, -this.posZ * scale);
   }

   public static void renderItemIn2D(PoseStack matrixStackIn, VertexConsumer bufferIn, float minU, float minV, float maxU, float maxV, int sizeX, int sizeY, float width, float texWidth, float texHeight, int packedLightIn, int packedOverlayIn, int colorIn) {
      if (width < 6.25E-4F) {
         width = 6.25E-4F;
      }

      float dU = maxU - minU;
      float dV = maxV - minV;
      float dimX = Mth.m_14154_(dU) * (texWidth / 16.0F);
      float dimY = Mth.m_14154_(dV) * (texHeight / 16.0F);
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
      float var8 = 0.5F * dU / (float)sizeX;
      float var9 = 0.5F * dV / (float)sizeY;
      normX = -1.0F;
      normY = 0.0F;
      normZ = 0.0F;

      int var10;
      float var11;
      float var12;
      for(var10 = 0; var10 < sizeX; ++var10) {
         var11 = (float)var10 / (float)sizeX;
         var12 = minU + dU * var11 + var8;
         addVertex(matrixStackIn, bufferIn, var11 * dimX, dimY, width, colorIn, var12, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var11 * dimX, dimY, 0.0F, colorIn, var12, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var11 * dimX, 0.0F, 0.0F, colorIn, var12, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var11 * dimX, 0.0F, width, colorIn, var12, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      }

      normX = 1.0F;
      normY = 0.0F;
      normZ = 0.0F;

      float var13;
      for(var10 = 0; var10 < sizeX; ++var10) {
         var11 = (float)var10 / (float)sizeX;
         var12 = minU + dU * var11 + var8;
         var13 = var11 + 1.0F / (float)sizeX;
         addVertex(matrixStackIn, bufferIn, var13 * dimX, 0.0F, width, colorIn, var12, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var13 * dimX, 0.0F, 0.0F, colorIn, var12, minV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var13 * dimX, dimY, 0.0F, colorIn, var12, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, var13 * dimX, dimY, width, colorIn, var12, maxV, packedOverlayIn, packedLightIn, normX, normY, normZ);
      }

      normX = 0.0F;
      normY = 1.0F;
      normZ = 0.0F;

      for(var10 = 0; var10 < sizeY; ++var10) {
         var11 = (float)var10 / (float)sizeY;
         var12 = minV + dV * var11 + var9;
         var13 = var11 + 1.0F / (float)sizeY;
         addVertex(matrixStackIn, bufferIn, 0.0F, var13 * dimY, width, colorIn, minU, var12, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, dimX, var13 * dimY, width, colorIn, maxU, var12, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, dimX, var13 * dimY, 0.0F, colorIn, maxU, var12, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, 0.0F, var13 * dimY, 0.0F, colorIn, minU, var12, packedOverlayIn, packedLightIn, normX, normY, normZ);
      }

      normX = 0.0F;
      normY = -1.0F;
      normZ = 0.0F;

      for(var10 = 0; var10 < sizeY; ++var10) {
         var11 = (float)var10 / (float)sizeY;
         var12 = minV + dV * var11 + var9;
         addVertex(matrixStackIn, bufferIn, dimX, var11 * dimY, width, colorIn, maxU, var12, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, 0.0F, var11 * dimY, width, colorIn, minU, var12, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, 0.0F, var11 * dimY, 0.0F, colorIn, minU, var12, packedOverlayIn, packedLightIn, normX, normY, normZ);
         addVertex(matrixStackIn, bufferIn, dimX, var11 * dimY, 0.0F, colorIn, maxU, var12, packedOverlayIn, packedLightIn, normX, normY, normZ);
      }

   }

   static void addVertex(PoseStack matrixStackIn, VertexConsumer bufferIn, float x, float y, float z, int colorIn, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
      PoseStack.Pose matrixEntry = matrixStackIn.m_85850_();
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
