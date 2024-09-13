package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class TntMinecartRenderer extends MinecartRenderer<MinecartTNT> {
   private BlockRenderDispatcher f_234660_;

   public TntMinecartRenderer(Context contextIn) {
      super(contextIn, ModelLayers.f_171253_);
      this.f_234660_ = contextIn.m_234597_();
   }

   protected void m_7002_(MinecartTNT entityIn, float partialTicks, BlockState stateIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      int i = entityIn.m_38694_();
      if (i > -1 && (float)i - partialTicks + 1.0F < 10.0F) {
         float f = 1.0F - ((float)i - partialTicks + 1.0F) / 10.0F;
         f = Mth.m_14036_(f, 0.0F, 1.0F);
         f *= f;
         f *= f;
         float f1 = 1.0F + f * 0.3F;
         matrixStackIn.m_85841_(f1, f1, f1);
      }

      m_234661_(this.f_234660_, stateIn, matrixStackIn, bufferIn, packedLightIn, i > -1 && i / 5 % 2 == 0);
   }

   public static void m_234661_(
      BlockRenderDispatcher renderDispatcherIn,
      BlockState blockStateIn,
      PoseStack matrixStackIn,
      MultiBufferSource renderTypeBuffer,
      int combinedLight,
      boolean doFullBright
   ) {
      int i;
      if (doFullBright) {
         i = OverlayTexture.m_118093_(OverlayTexture.m_118088_(1.0F), 10);
      } else {
         i = OverlayTexture.f_118083_;
      }

      if (Config.isShaders() && doFullBright) {
         Shaders.setEntityColor(1.0F, 1.0F, 1.0F, 0.5F);
      }

      renderDispatcherIn.m_110912_(blockStateIn, matrixStackIn, renderTypeBuffer, combinedLight, i);
      if (Config.isShaders()) {
         Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
      }
   }
}
