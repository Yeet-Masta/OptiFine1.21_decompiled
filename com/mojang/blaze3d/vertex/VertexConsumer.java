package com.mojang.blaze3d.vertex;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.IForgeVertexConsumer;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntities;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderEnv;
import net.optifine.render.VertexPosition;
import net.optifine.shaders.Shaders;
import net.optifine.util.MathUtils;
import org.joml.Math;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface VertexConsumer extends IForgeVertexConsumer {
   ThreadLocal RENDER_ENV = ThreadLocal.withInitial(() -> {
      return new RenderEnv(Blocks.f_50016_.m_49966_(), new BlockPos(0, 0, 0));
   });
   boolean FORGE = Reflector.ForgeHooksClient.exists();

   default RenderEnv getRenderEnv(BlockState blockState, BlockPos blockPos) {
      RenderEnv renderEnv = (RenderEnv)RENDER_ENV.get();
      renderEnv.reset(blockState, blockPos);
      return renderEnv;
   }

   VertexConsumer m_167146_(float var1, float var2, float var3);

   VertexConsumer m_167129_(int var1, int var2, int var3, int var4);

   VertexConsumer m_167083_(float var1, float var2);

   VertexConsumer m_338369_(int var1, int var2);

   VertexConsumer m_338813_(int var1, int var2);

   VertexConsumer m_338525_(float var1, float var2, float var3);

   default void m_338367_(float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
      this.m_167146_(x, y, z);
      this.m_338399_(argb);
      this.m_167083_(texU, texV);
      this.m_338943_(overlayUV);
      this.m_338973_(lightmapUV);
      this.m_338525_(normalX, normalY, normalZ);
   }

   default VertexConsumer m_340057_(float red, float green, float blue, float alpha) {
      return this.m_167129_((int)(red * 255.0F), (int)(green * 255.0F), (int)(blue * 255.0F), (int)(alpha * 255.0F));
   }

   default VertexConsumer m_338399_(int argb) {
      return this.m_167129_(ARGB32.m_13665_(argb), ARGB32.m_13667_(argb), ARGB32.m_13669_(argb), ARGB32.m_13655_(argb));
   }

   default VertexConsumer m_338954_(int alpha) {
      return this.m_338399_(ARGB32.m_320289_(alpha, -1));
   }

   default VertexConsumer m_338973_(int lightmapUV) {
      return this.m_338813_(lightmapUV & '\uffff', lightmapUV >> 16 & '\uffff');
   }

   default VertexConsumer m_338943_(int overlayUV) {
      return this.m_338369_(overlayUV & '\uffff', overlayUV >> 16 & '\uffff');
   }

   default void m_85995_(PoseStack.Pose matrixEntryIn, BakedQuad quadIn, float redIn, float greenIn, float blueIn, float alphaIn, int combinedLightIn, int combinedOverlayIn) {
      this.m_85987_(matrixEntryIn, quadIn, this.getTempFloat4(1.0F, 1.0F, 1.0F, 1.0F), redIn, greenIn, blueIn, alphaIn, this.getTempInt4(combinedLightIn, combinedLightIn, combinedLightIn, combinedLightIn), combinedOverlayIn, false);
   }

   default void putBulkData(PoseStack.Pose matrixEntry, BakedQuad bakedQuad, float[] baseBrightness, float red, float green, float blue, float alpha, int[] lightmapCoords, int overlayCoords, boolean readExistingColor) {
      this.m_85987_(matrixEntry, bakedQuad, baseBrightness, red, green, blue, alpha, lightmapCoords, overlayCoords, readExistingColor);
   }

   default void putBulkData(PoseStack.Pose matrixEntry, BakedQuad bakedQuad, float red, float green, float blue, float alpha, int packedLight, int packedOverlay, boolean readExistingColor) {
      this.m_85987_(matrixEntry, bakedQuad, this.getTempFloat4(1.0F, 1.0F, 1.0F, 1.0F), red, green, blue, alpha, this.getTempInt4(packedLight, packedLight, packedLight, packedLight), packedOverlay, readExistingColor);
   }

   default void m_85987_(PoseStack.Pose matrixEntryIn, BakedQuad quadIn, float[] colorMuls, float redIn, float greenIn, float blueIn, float alphaIn, int[] combinedLightsIn, int combinedOverlayIn, boolean mulColor) {
      int[] aint = this.isMultiTexture() ? quadIn.getVertexDataSingle() : quadIn.m_111303_();
      this.putSprite(quadIn.m_173410_());
      boolean separateAoInAlpha = ModelBlockRenderer.isSeparateAoLightValue();
      Vec3i vec3i = quadIn.m_111306_().m_122436_();
      Matrix4f matrix4f = matrixEntryIn.m_252922_();
      Vector3f vector3f = matrixEntryIn.m_323822_((float)vec3i.m_123341_(), (float)vec3i.m_123342_(), (float)vec3i.m_123343_(), this.getTempVec3f());
      float xn = vector3f.x;
      float yn = vector3f.y;
      float zn = vector3f.z;
      int i = true;
      int vertexIntSize = DefaultVertexFormat.f_85811_.getIntegerSize();
      int j = aint.length / vertexIntSize;
      int k = (int)(alphaIn * 255.0F);
      boolean shadersVelocity = Config.isShaders() && Shaders.useVelocityAttrib && Config.isMinecraftThread();
      if (shadersVelocity) {
         IRandomEntity entity = RandomEntities.getRandomEntityRendered();
         if (entity != null) {
            VertexPosition[] quadPos = quadIn.getVertexPositions(entity.getId());
            this.setQuadVertexPositions(quadPos);
         }
      }

      for(int l = 0; l < j; ++l) {
         int pos = l * vertexIntSize;
         float f = Float.intBitsToFloat(aint[pos + 0]);
         float f1 = Float.intBitsToFloat(aint[pos + 1]);
         float f2 = Float.intBitsToFloat(aint[pos + 2]);
         float colorMulAo = separateAoInAlpha ? 1.0F : colorMuls[l];
         float f3;
         float f4;
         float f5;
         int i1;
         float f10;
         float f9;
         if (mulColor) {
            i1 = aint[pos + 3];
            float f6 = (float)(i1 & 255);
            f10 = (float)(i1 >> 8 & 255);
            f9 = (float)(i1 >> 16 & 255);
            f3 = f6 * colorMulAo * redIn;
            f4 = f10 * colorMulAo * greenIn;
            f5 = f9 * colorMulAo * blueIn;
         } else {
            f3 = colorMulAo * redIn * 255.0F;
            f4 = colorMulAo * greenIn * 255.0F;
            f5 = colorMulAo * blueIn * 255.0F;
         }

         if (separateAoInAlpha) {
            k = (int)(colorMuls[l] * 255.0F);
         }

         i1 = ARGB32.m_13660_(k, (int)f3, (int)f4, (int)f5);
         int j1 = combinedLightsIn[l];
         if (FORGE) {
            j1 = this.applyBakedLighting(aint[l], aint, pos);
         }

         f10 = Float.intBitsToFloat(aint[pos + 4]);
         f9 = Float.intBitsToFloat(aint[pos + 5]);
         float xt = MathUtils.getTransformX(matrix4f, f, f1, f2);
         float yt = MathUtils.getTransformY(matrix4f, f, f1, f2);
         float zt = MathUtils.getTransformZ(matrix4f, f, f1, f2);
         if (FORGE) {
            Vector3f forgeNormals = this.applyBakedNormals(aint, pos, matrixEntryIn.m_252943_());
            if (forgeNormals != null) {
               xn = forgeNormals.x();
               yn = forgeNormals.y();
               zn = forgeNormals.z();
            }
         }

         this.m_338367_(xt, yt, zt, i1, f10, f9, combinedOverlayIn, j1, xn, yn, zn);
      }

   }

   default VertexConsumer m_340435_(Vector3f vecIn) {
      return this.m_167146_(vecIn.x(), vecIn.y(), vecIn.z());
   }

   default VertexConsumer m_340301_(PoseStack.Pose poseIn, Vector3f vecIn) {
      return this.m_338370_(poseIn, vecIn.x(), vecIn.y(), vecIn.z());
   }

   default VertexConsumer m_338370_(PoseStack.Pose poseIn, float x, float y, float z) {
      return this.m_339083_(poseIn.m_252922_(), x, y, z);
   }

   default VertexConsumer m_339083_(Matrix4f matrixIn, float x, float y, float z) {
      Vector3f vector3f = matrixIn.transformPosition(x, y, z, this.getTempVec3f());
      return this.m_167146_(vector3f.x(), vector3f.y(), vector3f.z());
   }

   default VertexConsumer m_339200_(PoseStack.Pose poseIn, float x, float y, float z) {
      Vector3f vector3f = poseIn.m_323822_(x, y, z, this.getTempVec3f());
      return this.m_338525_(vector3f.x(), vector3f.y(), vector3f.z());
   }

   default void putSprite(TextureAtlasSprite sprite) {
   }

   default void setSprite(TextureAtlasSprite sprite) {
   }

   default boolean isMultiTexture() {
      return false;
   }

   default RenderType getRenderType() {
      return null;
   }

   default Vector3f getTempVec3f() {
      return new Vector3f();
   }

   default Vector3f getTempVec3f(float x, float y, float z) {
      return this.getTempVec3f().set(x, y, z);
   }

   default Vector3f getTempVec3f(Vector3f vec) {
      return this.getTempVec3f().set(vec);
   }

   default float[] getTempFloat4(float f1, float f2, float f3, float f4) {
      return new float[]{f1, f2, f3, f4};
   }

   default int[] getTempInt4(int i1, int i2, int i3, int i4) {
      return new int[]{i1, i2, i3, i4};
   }

   default MultiBufferSource.BufferSource getRenderTypeBuffer() {
      return null;
   }

   default void setQuadVertexPositions(VertexPosition[] vps) {
   }

   default void setMidBlock(float mbx, float mby, float mbz) {
   }

   default VertexConsumer getSecondaryBuilder() {
      return null;
   }

   default int getVertexCount() {
      return 0;
   }

   default int applyBakedLighting(int lightmapCoord, int[] data, int pos) {
      int offsetInt = getLightOffset(0);
      int blBaked = LightTexture.m_109883_(data[pos + offsetInt]);
      int slBaked = LightTexture.m_109894_(data[pos + offsetInt]);
      if (blBaked == 0 && slBaked == 0) {
         return lightmapCoord;
      } else {
         int bl = LightTexture.m_109883_(lightmapCoord);
         int sl = LightTexture.m_109894_(lightmapCoord);
         bl = Math.max(bl, blBaked);
         sl = Math.max(sl, slBaked);
         return LightTexture.m_109885_(bl, sl);
      }
   }

   static int getLightOffset(int v) {
      return v * 8 + 6;
   }

   default Vector3f applyBakedNormals(int[] data, int pos, Matrix3f normalTransform) {
      int offsetNormal = 7;
      int val = data[pos + offsetNormal];
      byte nx = (byte)(val >> 0 & 255);
      byte ny = (byte)(val >> 8 & 255);
      byte nz = (byte)(val >> 16 & 255);
      if (nx == 0 && ny == 0 && nz == 0) {
         return null;
      } else {
         Vector3f generated = this.getTempVec3f((float)nx / 127.0F, (float)ny / 127.0F, (float)nz / 127.0F);
         MathUtils.transform(generated, normalTransform);
         return generated;
      }
   }

   default void getBulkData(ByteBuffer buffer) {
   }

   default void putBulkData(ByteBuffer buffer) {
   }

   default boolean canAddVertexFast() {
      return false;
   }

   default void addVertexFast(float x, float y, float z, int color, float texU, float texV, int overlayUV, int lightmapUV, int normals) {
   }
}
