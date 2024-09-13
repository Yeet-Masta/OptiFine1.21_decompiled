package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.optifine.Config;
import net.optifine.CustomColors;

public class SheepFurLayer extends RenderLayer<Sheep, SheepModel<Sheep>> {
   private static ResourceLocation f_117404_ = ResourceLocation.m_340282_("textures/entity/sheep/sheep_fur.png");
   public SheepFurModel<Sheep> f_117405_;

   public SheepFurLayer(RenderLayerParent<Sheep, SheepModel<Sheep>> parentIn, EntityModelSet modelSetIn) {
      super(parentIn);
      this.f_117405_ = new SheepFurModel(modelSetIn.m_171103_(ModelLayers.f_171178_));
   }

   public void m_6494_(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Sheep entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!entitylivingbaseIn.m_29875_()) {
         if (entitylivingbaseIn.m_20145_()) {
            Minecraft minecraft = Minecraft.m_91087_();
            boolean flag = minecraft.m_91314_(entitylivingbaseIn);
            if (flag) {
               this.m_117386_().m_102624_(this.f_117405_);
               this.f_117405_.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
               this.f_117405_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
               VertexConsumer vertexconsumer = bufferIn.m_6299_(RenderType.m_110491_(f_117404_));
               this.f_117405_.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, LivingEntityRenderer.m_115338_(entitylivingbaseIn, 0.0F), -16777216);
            }
         } else {
            int i;
            if (entitylivingbaseIn.m_8077_() && "jeb_".equals(entitylivingbaseIn.m_7755_().getString())) {
               int j = 25;
               int k = entitylivingbaseIn.f_19797_ / 25 + entitylivingbaseIn.m_19879_();
               int l = DyeColor.values().length;
               int i1 = k % l;
               int j1 = (k + 1) % l;
               float f = ((float)(entitylivingbaseIn.f_19797_ % 25) + partialTicks) / 25.0F;
               int k1 = Sheep.m_339153_(DyeColor.m_41053_(i1));
               int l1 = Sheep.m_339153_(DyeColor.m_41053_(j1));
               if (Config.isCustomColors()) {
                  k1 = CustomColors.getSheepColors(DyeColor.m_41053_(i1), k1);
                  l1 = CustomColors.getSheepColors(DyeColor.m_41053_(j1), l1);
               }

               i = ARGB32.m_269105_(f, k1, l1);
            } else {
               i = Sheep.m_339153_(entitylivingbaseIn.m_29874_());
               if (Config.isCustomColors()) {
                  i = CustomColors.getSheepColors(entitylivingbaseIn.m_29874_(), i);
               }
            }

            m_117359_(
               this.m_117386_(),
               this.f_117405_,
               f_117404_,
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
               i
            );
         }
      }
   }
}
