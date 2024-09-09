package net.minecraft.src;

public class C_302024_ extends C_4447_ {
   private C_4168_ f_316997_ = C_4168_.m_305574_(C_5265_.m_340282_("textures/entity/breeze/breeze_eyes.png"));
   private C_302185_ customModel;

   public C_302024_(C_4382_ p_i306560_1_) {
      super(p_i306560_1_);
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_301969_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      C_3187_ vertexconsumer = bufferIn.m_6299_(this.f_316997_);
      C_302185_ breezemodel = this.getEntityModel();
      C_302054_.m_323838_(breezemodel, new C_3889_[]{breezemodel.m_319970_(), breezemodel.m_323648_()}).a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
   }

   public void setModel(C_302185_ model) {
      this.customModel = model;
   }

   public void setTextureLocation(C_5265_ textureLocation) {
      this.f_316997_ = C_4168_.m_305574_(textureLocation);
   }

   public C_302185_ getEntityModel() {
      return this.customModel != null ? this.customModel : (C_302185_)super.m_117386_();
   }
}
