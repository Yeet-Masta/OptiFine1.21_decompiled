package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.optifine.Config;
import net.optifine.CustomItems;

public class ElytraLayer extends RenderLayer {
   private static final ResourceLocation f_116934_ = ResourceLocation.m_340282_("textures/entity/elytra.png");
   private final ElytraModel f_116935_;

   public ElytraLayer(RenderLayerParent parentIn, EntityModelSet modelSetIn) {
      super(parentIn);
      this.f_116935_ = new ElytraModel(modelSetIn.m_171103_(ModelLayers.f_171141_));
   }

   public void m_6494_(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, LivingEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      ItemStack itemstack = entitylivingbaseIn.m_6844_(EquipmentSlot.CHEST);
      if (this.shouldRender(itemstack, entitylivingbaseIn)) {
         ResourceLocation resourcelocation;
         if (entitylivingbaseIn instanceof AbstractClientPlayer) {
            AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entitylivingbaseIn;
            PlayerSkin playerskin = abstractclientplayer.m_294544_();
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
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(resourcelocation), itemstack.m_41790_());
         this.f_116935_.m_340227_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
         matrixStackIn.m_85849_();
      }

   }

   public boolean shouldRender(ItemStack stack, LivingEntity entity) {
      return stack.m_150930_(Items.f_42741_);
   }

   public ResourceLocation getElytraTexture(ItemStack stack, LivingEntity entity) {
      return f_116934_;
   }
}
