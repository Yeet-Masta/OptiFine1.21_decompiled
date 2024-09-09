package net.minecraft.src;

public abstract class C_4447_ {
   private final C_4382_ f_117344_;
   public boolean custom = false;

   public C_4447_(C_4382_ entityRendererIn) {
      this.f_117344_ = entityRendererIn;
   }

   protected static void m_117359_(C_3819_ modelParentIn, C_3819_ modelIn, C_5265_ textureLocationIn, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_524_ entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTicks, int colorIn) {
      if (!entityIn.ci()) {
         modelParentIn.m_102624_(modelIn);
         modelIn.m_6839_(entityIn, limbSwing, limbSwingAmount, partialTicks);
         modelIn.m_6973_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         m_117376_(modelIn, textureLocationIn, matrixStackIn, bufferIn, packedLightIn, entityIn, colorIn);
      }

   }

   protected static void m_117376_(C_3819_ modelIn, C_5265_ textureLocationIn, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_524_ entityIn, int colorIn) {
      if (modelIn.locationTextureCustom != null) {
         textureLocationIn = modelIn.locationTextureCustom;
      }

      C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_110458_(textureLocationIn));
      modelIn.a(matrixStackIn, vertexconsumer, packedLightIn, C_4357_.m_115338_(entityIn, 0.0F), colorIn);
   }

   public C_3819_ m_117386_() {
      return this.f_117344_.m_7200_();
   }

   protected C_5265_ m_117347_(C_507_ entityIn) {
      return this.f_117344_.m_5478_(entityIn);
   }

   public abstract void m_6494_(C_3181_ var1, C_4139_ var2, int var3, C_507_ var4, float var5, float var6, float var7, float var8, float var9, float var10);
}
