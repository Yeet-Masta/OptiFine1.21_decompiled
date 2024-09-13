package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.AnimalArmorItem.BodyType;
import net.minecraft.world.item.component.DyedItemColor;

public class HorseArmorLayer extends RenderLayer<Horse, HorseModel<Horse>> {
   public HorseModel<Horse> f_117017_;
   public ResourceLocation customTextureLocation;

   public HorseArmorLayer(RenderLayerParent<Horse, HorseModel<Horse>> p_i174495_1_, EntityModelSet p_i174495_2_) {
      super(p_i174495_1_);
      this.f_117017_ = new HorseModel(p_i174495_2_.m_171103_(ModelLayers.f_171187_));
   }

   public void m_6494_(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Horse entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      ItemStack itemstack = entitylivingbaseIn.m_319275_();
      if (itemstack.m_41720_() instanceof AnimalArmorItem animalarmoritem && animalarmoritem.m_319458_() == BodyType.EQUESTRIAN) {
         this.m_117386_().m_102624_(this.f_117017_);
         this.f_117017_.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.f_117017_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         int i;
         if (itemstack.m_204117_(ItemTags.f_314020_)) {
            i = ARGB32.m_321570_(DyedItemColor.m_322889_(itemstack, -6265536));
         } else {
            i = -1;
         }

         ResourceLocation armorTexture = this.customTextureLocation != null ? this.customTextureLocation : animalarmoritem.m_320881_();
         VertexConsumer vertexconsumer = bufferIn.m_6299_(RenderType.m_110458_(armorTexture));
         this.f_117017_.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, i);
         return;
      }
   }
}
