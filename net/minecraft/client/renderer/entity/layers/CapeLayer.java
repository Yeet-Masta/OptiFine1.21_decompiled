package net.minecraft.client.renderer.entity.layers;

import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.optifine.Config;

public class CapeLayer
   extends net.minecraft.client.renderer.entity.layers.RenderLayer<net.minecraft.client.player.AbstractClientPlayer, PlayerModel<net.minecraft.client.player.AbstractClientPlayer>> {
   public CapeLayer(
      RenderLayerParent<net.minecraft.client.player.AbstractClientPlayer, PlayerModel<net.minecraft.client.player.AbstractClientPlayer>> playerModelIn
   ) {
      super(playerModelIn);
   }

   public void m_6494_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn,
      net.minecraft.client.player.AbstractClientPlayer entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!entitylivingbaseIn.m_20145_() && entitylivingbaseIn.m_36170_(PlayerModelPart.CAPE)) {
         net.minecraft.client.resources.PlayerSkin playerskin = entitylivingbaseIn.m_294544_();
         if (entitylivingbaseIn.getLocationCape() != null) {
            ItemStack itemstack = entitylivingbaseIn.m_6844_(EquipmentSlot.CHEST);
            if (!itemstack.m_150930_(Items.f_42741_)) {
               matrixStackIn.m_85836_();
               matrixStackIn.m_252880_(0.0F, 0.0F, 0.125F);
               double d0 = net.minecraft.util.Mth.m_14139_((double)partialTicks, entitylivingbaseIn.f_36102_, entitylivingbaseIn.f_36105_)
                  - net.minecraft.util.Mth.m_14139_((double)partialTicks, entitylivingbaseIn.f_19854_, entitylivingbaseIn.m_20185_());
               double d1 = net.minecraft.util.Mth.m_14139_((double)partialTicks, entitylivingbaseIn.f_36103_, entitylivingbaseIn.f_36106_)
                  - net.minecraft.util.Mth.m_14139_((double)partialTicks, entitylivingbaseIn.f_19855_, entitylivingbaseIn.m_20186_());
               double d2 = net.minecraft.util.Mth.m_14139_((double)partialTicks, entitylivingbaseIn.f_36104_, entitylivingbaseIn.f_36075_)
                  - net.minecraft.util.Mth.m_14139_((double)partialTicks, entitylivingbaseIn.f_19856_, entitylivingbaseIn.m_20189_());
               float f = net.minecraft.util.Mth.m_14189_(partialTicks, entitylivingbaseIn.f_20884_, entitylivingbaseIn.f_20883_);
               double d3 = (double)net.minecraft.util.Mth.m_14031_(f * (float) (Math.PI / 180.0));
               double d4 = (double)(-net.minecraft.util.Mth.m_14089_(f * (float) (Math.PI / 180.0)));
               float f1 = (float)d1 * 10.0F;
               f1 = net.minecraft.util.Mth.m_14036_(f1, -6.0F, 32.0F);
               float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
               f2 = net.minecraft.util.Mth.m_14036_(f2, 0.0F, 150.0F);
               float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
               f3 = net.minecraft.util.Mth.m_14036_(f3, -20.0F, 20.0F);
               if (f2 < 0.0F) {
                  f2 = 0.0F;
               }

               if (f2 > 165.0F) {
                  f2 = 165.0F;
               }

               if (f1 < -5.0F) {
                  f1 = -5.0F;
               }

               float f4 = net.minecraft.util.Mth.m_14179_(partialTicks, entitylivingbaseIn.f_36099_, entitylivingbaseIn.f_36100_);
               f1 += net.minecraft.util.Mth.m_14031_(
                     net.minecraft.util.Mth.m_14179_(partialTicks, entitylivingbaseIn.f_19867_, entitylivingbaseIn.f_19787_) * 6.0F
                  )
                  * 32.0F
                  * f4;
               if (entitylivingbaseIn.m_6047_()) {
                  f1 += 25.0F;
               }

               float pct = Config.getAverageFrameTimeSec() * 20.0F;
               pct = Config.limit(pct, 0.02F, 1.0F);
               entitylivingbaseIn.capeRotateX = net.minecraft.util.Mth.m_14179_(pct, entitylivingbaseIn.capeRotateX, 6.0F + f2 / 2.0F + f1);
               entitylivingbaseIn.capeRotateZ = net.minecraft.util.Mth.m_14179_(pct, entitylivingbaseIn.capeRotateZ, f3 / 2.0F);
               entitylivingbaseIn.capeRotateY = net.minecraft.util.Mth.m_14179_(pct, entitylivingbaseIn.capeRotateY, 180.0F - f3 / 2.0F);
               matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(entitylivingbaseIn.capeRotateX));
               matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(entitylivingbaseIn.capeRotateZ));
               matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(entitylivingbaseIn.capeRotateY));
               com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = bufferIn.m_6299_(
                  net.minecraft.client.renderer.RenderType.m_110446_(entitylivingbaseIn.getLocationCape())
               );
               this.m_117386_().m_103411_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
               matrixStackIn.m_85849_();
            }
         }
      }
   }
}
