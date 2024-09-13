package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class SkeletonClothingLayer<T extends Mob & RangedAttackMob, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public SkeletonModel<T> f_314940_;
   public ResourceLocation f_316006_;

   public SkeletonClothingLayer(
      RenderLayerParent<T, M> p_i323834_1_, EntityModelSet p_i323834_2_, ModelLayerLocation p_i323834_3_, ResourceLocation p_i323834_4_
   ) {
      super(p_i323834_1_);
      this.f_316006_ = p_i323834_4_;
      this.f_314940_ = new SkeletonModel(p_i323834_2_.m_171103_(p_i323834_3_));
   }

   public void m_6494_(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      m_117359_(
         this.m_117386_(),
         this.f_314940_,
         this.f_316006_,
         matrixStackIn,
         bufferIn,
         packedLightIn,
         entitylivingbaseIn,
         limbSwing,
         limbSwingAmount,
         ageInTicks,
         netHeadYaw,
         headPitch,
         partialTicks,
         -1
      );
   }
}
