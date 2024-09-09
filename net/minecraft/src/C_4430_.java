package net.minecraft.src;

import net.optifine.Config;
import net.optifine.CustomItems;

public class C_4430_ extends C_4447_ {
   private static final C_5265_ f_116934_ = C_5265_.m_340282_("textures/entity/elytra.png");
   private final C_3815_ f_116935_;

   public C_4430_(C_4382_ parentIn, C_141653_ modelSetIn) {
      super(parentIn);
      this.f_116935_ = new C_3815_(modelSetIn.m_171103_(C_141656_.f_171141_));
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_524_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      C_1391_ itemstack = entitylivingbaseIn.m_6844_(C_516_.CHEST);
      if (this.shouldRender(itemstack, entitylivingbaseIn)) {
         C_5265_ resourcelocation;
         if (entitylivingbaseIn instanceof C_4102_) {
            C_4102_ abstractclientplayer = (C_4102_)entitylivingbaseIn;
            C_290287_ playerskin = abstractclientplayer.m_294544_();
            if (abstractclientplayer.getLocationElytra() != null) {
               resourcelocation = abstractclientplayer.getLocationElytra();
            } else if (playerskin.f_291348_() != null && abstractclientplayer.m_36170_(C_1144_.CAPE)) {
               resourcelocation = playerskin.f_291348_();
            } else {
               resourcelocation = this.getElytraTexture(itemstack, entitylivingbaseIn);
               if (Config.isCustomItems()) {
                  resourcelocation = CustomItems.getCustomElytraTexture(itemstack, resourcelocation);
               }
            }
         } else {
            resourcelocation = this.getElytraTexture(itemstack, entitylivingbaseIn);
            if (Config.isCustomItems()) {
               resourcelocation = CustomItems.getCustomElytraTexture(itemstack, resourcelocation);
            }
         }

         matrixStackIn.m_85836_();
         matrixStackIn.m_252880_(0.0F, 0.0F, 0.125F);
         this.m_117386_().m_102624_(this.f_116935_);
         this.f_116935_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         C_3187_ vertexconsumer = C_4354_.m_115184_(bufferIn, C_4168_.m_110431_(resourcelocation), itemstack.m_41790_());
         this.f_116935_.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
         matrixStackIn.m_85849_();
      }

   }

   public boolean shouldRender(C_1391_ stack, C_524_ entity) {
      return stack.m_150930_(C_1394_.f_42741_);
   }

   public C_5265_ getElytraTexture(C_1391_ stack, C_524_ entity) {
      return f_116934_;
   }
}
