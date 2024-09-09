import java.nio.ByteBuffer;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4713_;
import net.minecraft.src.C_174_.C_175_;
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
   ThreadLocal<RenderEnv> RENDER_ENV = ThreadLocal.withInitial(() -> new RenderEnv(C_1710_.f_50016_.o(), new C_4675_(0, 0, 0)));
   boolean FORGE = Reflector.ForgeHooksClient.exists();

   default RenderEnv getRenderEnv(BlockState blockState, C_4675_ blockPos) {
      RenderEnv renderEnv = (RenderEnv)RENDER_ENV.get();
      renderEnv.reset(blockState, blockPos);
      return renderEnv;
   }

   VertexConsumer a(float var1, float var2, float var3);

   VertexConsumer a(int var1, int var2, int var3, int var4);

   VertexConsumer a(float var1, float var2);

   VertexConsumer a(int var1, int var2);

   VertexConsumer b(int var1, int var2);

   VertexConsumer b(float var1, float var2, float var3);

   default void a(float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
      this.a(x, y, z);
      this.a(argb);
      this.a(texU, texV);
      this.b(overlayUV);
      this.c(lightmapUV);
      this.b(normalX, normalY, normalZ);
   }

   default VertexConsumer a(float red, float green, float blue, float alpha) {
      return this.a((int)(red * 255.0F), (int)(green * 255.0F), (int)(blue * 255.0F), (int)(alpha * 255.0F));
   }

   default VertexConsumer a(int argb) {
      return this.a(C_175_.m_13665_(argb), C_175_.m_13667_(argb), C_175_.m_13669_(argb), C_175_.m_13655_(argb));
   }

   default VertexConsumer d(int alpha) {
      return this.a(C_175_.m_320289_(alpha, -1));
   }

   default VertexConsumer c(int lightmapUV) {
      return this.b(lightmapUV & 65535, lightmapUV >> 16 & 65535);
   }

   default VertexConsumer b(int overlayUV) {
      return this.a(overlayUV & 65535, overlayUV >> 16 & 65535);
   }

   default void a(
      PoseStack.a matrixEntryIn, BakedQuad quadIn, float redIn, float greenIn, float blueIn, float alphaIn, int combinedLightIn, int combinedOverlayIn
   ) {
      this.a(
         matrixEntryIn,
         quadIn,
         this.getTempFloat4(1.0F, 1.0F, 1.0F, 1.0F),
         redIn,
         greenIn,
         blueIn,
         alphaIn,
         this.getTempInt4(combinedLightIn, combinedLightIn, combinedLightIn, combinedLightIn),
         combinedOverlayIn,
         false
      );
   }

   default void putBulkData(
      PoseStack.a matrixEntry,
      BakedQuad bakedQuad,
      float[] baseBrightness,
      float red,
      float green,
      float blue,
      float alpha,
      int[] lightmapCoords,
      int overlayCoords,
      boolean readExistingColor
   ) {
      this.a(matrixEntry, bakedQuad, baseBrightness, red, green, blue, alpha, lightmapCoords, overlayCoords, readExistingColor);
   }

   @Override
   default void putBulkData(
      PoseStack.a matrixEntry,
      BakedQuad bakedQuad,
      float red,
      float green,
      float blue,
      float alpha,
      int packedLight,
      int packedOverlay,
      boolean readExistingColor
   ) {
      this.a(
         matrixEntry,
         bakedQuad,
         this.getTempFloat4(1.0F, 1.0F, 1.0F, 1.0F),
         red,
         green,
         blue,
         alpha,
         this.getTempInt4(packedLight, packedLight, packedLight, packedLight),
         packedOverlay,
         readExistingColor
      );
   }

   default void a(
      PoseStack.a matrixEntryIn,
      BakedQuad quadIn,
      float[] colorMuls,
      float redIn,
      float greenIn,
      float blueIn,
      float alphaIn,
      int[] combinedLightsIn,
      int combinedOverlayIn,
      boolean mulColor
   ) {
      int[] aint = this.isMultiTexture() ? quadIn.getVertexDataSingle() : quadIn.b();
      this.putSprite(quadIn.a());
      boolean separateAoInAlpha = ModelBlockRenderer.isSeparateAoLightValue();
      C_4713_ vec3i = quadIn.e().q();
      Matrix4f matrix4f = matrixEntryIn.a();
      Vector3f vector3f = matrixEntryIn.a((float)vec3i.m_123341_(), (float)vec3i.m_123342_(), (float)vec3i.m_123343_(), this.getTempVec3f());
      float xn = vector3f.x;
      float yn = vector3f.y;
      float zn = vector3f.z;
      int i = 8;
      int vertexIntSize = DefaultVertexFormat.b.getIntegerSize();
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

      for (int l = 0; l < j; l++) {
         int pos = l * vertexIntSize;
         float f = Float.intBitsToFloat(aint[pos + 0]);
         float f1 = Float.intBitsToFloat(aint[pos + 1]);
         float f2 = Float.intBitsToFloat(aint[pos + 2]);
         float colorMulAo = separateAoInAlpha ? 1.0F : colorMuls[l];
         float f3;
         float f4;
         float f5;
         if (mulColor) {
            int col = aint[pos + 3];
            float f6 = (float)(col & 0xFF);
            float f7 = (float)(col >> 8 & 0xFF);
            float f8 = (float)(col >> 16 & 0xFF);
            f3 = f6 * colorMulAo * redIn;
            f4 = f7 * colorMulAo * greenIn;
            f5 = f8 * colorMulAo * blueIn;
         } else {
            f3 = colorMulAo * redIn * 255.0F;
            f4 = colorMulAo * greenIn * 255.0F;
            f5 = colorMulAo * blueIn * 255.0F;
         }

         if (separateAoInAlpha) {
            k = (int)(colorMuls[l] * 255.0F);
         }

         int i1 = C_175_.m_13660_(k, (int)f3, (int)f4, (int)f5);
         int j1 = combinedLightsIn[l];
         if (FORGE) {
            j1 = this.applyBakedLighting(aint[l], aint, pos);
         }

         float f10 = Float.intBitsToFloat(aint[pos + 4]);
         float f9 = Float.intBitsToFloat(aint[pos + 5]);
         float xt = MathUtils.getTransformX(matrix4f, f, f1, f2);
         float yt = MathUtils.getTransformY(matrix4f, f, f1, f2);
         float zt = MathUtils.getTransformZ(matrix4f, f, f1, f2);
         if (FORGE) {
            Vector3f forgeNormals = this.applyBakedNormals(aint, pos, matrixEntryIn.b());
            if (forgeNormals != null) {
               xn = forgeNormals.x();
               yn = forgeNormals.y();
               zn = forgeNormals.z();
            }
         }

         this.a(xt, yt, zt, i1, f10, f9, combinedOverlayIn, j1, xn, yn, zn);
      }
   }

   default VertexConsumer a(Vector3f vecIn) {
      return this.a(vecIn.x(), vecIn.y(), vecIn.z());
   }

   default VertexConsumer a(PoseStack.a poseIn, Vector3f vecIn) {
      return this.a(poseIn, vecIn.x(), vecIn.y(), vecIn.z());
   }

   default VertexConsumer a(PoseStack.a poseIn, float x, float y, float z) {
      return this.a(poseIn.a(), x, y, z);
   }

   default VertexConsumer a(Matrix4f matrixIn, float x, float y, float z) {
      Vector3f vector3f = matrixIn.transformPosition(x, y, z, this.getTempVec3f());
      return this.a(vector3f.x(), vector3f.y(), vector3f.z());
   }

   default VertexConsumer b(PoseStack.a poseIn, float x, float y, float z) {
      Vector3f vector3f = poseIn.a(x, y, z, this.getTempVec3f());
      return this.b(vector3f.x(), vector3f.y(), vector3f.z());
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

   default MultiBufferSource.a getRenderTypeBuffer() {
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
      int blBaked = LightTexture.a(data[pos + offsetInt]);
      int slBaked = LightTexture.b(data[pos + offsetInt]);
      if (blBaked == 0 && slBaked == 0) {
         return lightmapCoord;
      } else {
         int bl = LightTexture.a(lightmapCoord);
         int sl = LightTexture.b(lightmapCoord);
         bl = Math.max(bl, blBaked);
         sl = Math.max(sl, slBaked);
         return LightTexture.a(bl, sl);
      }
   }

   static int getLightOffset(int v) {
      return v * 8 + 6;
   }

   default Vector3f applyBakedNormals(int[] data, int pos, Matrix3f normalTransform) {
      int offsetNormal = 7;
      int val = data[pos + offsetNormal];
      byte nx = (byte)(val >> 0 & 0xFF);
      byte ny = (byte)(val >> 8 & 0xFF);
      byte nz = (byte)(val >> 16 & 0xFF);
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
