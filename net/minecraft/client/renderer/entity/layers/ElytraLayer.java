package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.optifine.Config;
import net.optifine.CustomItems;

public class ElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends net.minecraft.client.renderer.entity.layers.RenderLayer<T, M> {
   private static final net.minecraft.resources.ResourceLocation f_116934_ = net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/elytra.png");
   private final ElytraModel<T> f_116935_;

   public ElytraLayer(RenderLayerParent<T, M> parentIn, EntityModelSet modelSetIn) {
      super(parentIn);
      this.f_116935_ = new ElytraModel(modelSetIn.m_171103_(ModelLayers.f_171141_));
   }

   public void m_6494_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      ItemStack itemstack = entitylivingbaseIn.m_6844_(EquipmentSlot.CHEST);
      if (this.shouldRender(itemstack, entitylivingbaseIn)) {
         net.minecraft.resources.ResourceLocation resourcelocation;
         if (entitylivingbaseIn instanceof net.minecraft.client.player.AbstractClientPlayer abstractclientplayer) {
            net.minecraft.client.resources.PlayerSkin playerskin = abstractclientplayer.m_294544_();
            if (abstractclientplayer.getLocationElytra() != null) {
               resourcelocation = abstractclientplayer.getLocationElytra();
            } else if (playerskin.f_291348_() != null && abstractclientplayer.m_36170_(PlayerModelPart.CAPE)) {
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
         com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = net.minecraft.client.renderer.entity.ItemRenderer.m_115184_(
            bufferIn, net.minecraft.client.renderer.RenderType.m_110431_(resourcelocation), itemstack.m_41790_()
         );
         this.f_116935_.m_340227_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
         matrixStackIn.m_85849_();
      }
   }

   public boolean shouldRender(ItemStack stack, T entity) {
      return stack.m_150930_(Items.f_42741_);
   }

   public net.minecraft.resources.ResourceLocation getElytraTexture(ItemStack stack, T entity) {
      return f_116934_;
   }
}
