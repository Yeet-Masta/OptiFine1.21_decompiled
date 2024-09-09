package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.mojang.math.Axis;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.shaders.Shaders;

public class SignRenderer implements net.minecraft.client.renderer.blockentity.BlockEntityRenderer<SignBlockEntity> {
   private static final String f_173629_ = "stick";
   private static final int f_173630_ = -988212;
   private static final int f_173631_ = net.minecraft.util.Mth.m_144944_(16);
   private static final float f_278501_ = 0.6666667F;
   private static final net.minecraft.world.phys.Vec3 f_278459_ = new net.minecraft.world.phys.Vec3(0.0, 0.33333334F, 0.046666667F);
   private final Map<WoodType, net.minecraft.client.renderer.blockentity.SignRenderer.SignModel> f_173632_;
   private final net.minecraft.client.gui.Font f_173633_;
   private static double textRenderDistanceSq = 4096.0;

   public SignRenderer(Context contextIn) {
      this.f_173632_ = (Map<WoodType, net.minecraft.client.renderer.blockentity.SignRenderer.SignModel>)WoodType.m_61843_()
         .collect(
            ImmutableMap.toImmutableMap(
               woodTypeIn -> woodTypeIn,
               woodTypeIn -> new net.minecraft.client.renderer.blockentity.SignRenderer.SignModel(contextIn.m_173582_(ModelLayers.m_171291_(woodTypeIn)))
            )
         );
      this.f_173633_ = contextIn.m_173586_();
   }

   public void m_6922_(
      SignBlockEntity tileEntityIn,
      float partialTicks,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int combinedLightIn,
      int combinedOverlayIn
   ) {
      net.minecraft.world.level.block.state.BlockState blockstate = tileEntityIn.m_58900_();
      SignBlock signblock = (SignBlock)blockstate.m_60734_();
      WoodType woodtype = SignBlock.m_247329_(signblock);
      net.minecraft.client.renderer.blockentity.SignRenderer.SignModel signrenderer$signmodel = (net.minecraft.client.renderer.blockentity.SignRenderer.SignModel)this.f_173632_
         .get(woodtype);
      signrenderer$signmodel.f_112507_.f_104207_ = blockstate.m_60734_() instanceof StandingSignBlock;
      this.m_278756_(tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, blockstate, signblock, woodtype, signrenderer$signmodel);
   }

   public float m_278770_() {
      return 0.6666667F;
   }

   public float m_278631_() {
      return 0.6666667F;
   }

   void m_278756_(
      SignBlockEntity tileEntityIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int combinedLightIn,
      int combinedOverlayIn,
      net.minecraft.world.level.block.state.BlockState stateIn,
      SignBlock blockIn,
      WoodType woodTypeIn,
      net.minecraft.client.model.Model modelIn
   ) {
      matrixStackIn.m_85836_();
      this.m_276777_(matrixStackIn, -blockIn.m_276903_(stateIn), stateIn);
      this.m_278784_(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, woodTypeIn, modelIn);
      this.m_278841_(
         tileEntityIn.m_58899_(), tileEntityIn.m_277142_(), matrixStackIn, bufferIn, combinedLightIn, tileEntityIn.m_245065_(), tileEntityIn.m_245123_(), true
      );
      this.m_278841_(
         tileEntityIn.m_58899_(), tileEntityIn.m_277159_(), matrixStackIn, bufferIn, combinedLightIn, tileEntityIn.m_245065_(), tileEntityIn.m_245123_(), false
      );
      matrixStackIn.m_85849_();
   }

   void m_276777_(com.mojang.blaze3d.vertex.PoseStack matrixStackIn, float rotationIn, net.minecraft.world.level.block.state.BlockState stateIn) {
      matrixStackIn.m_252880_(0.5F, 0.75F * this.m_278770_(), 0.5F);
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(rotationIn));
      if (!(stateIn.m_60734_() instanceof StandingSignBlock)) {
         matrixStackIn.m_252880_(0.0F, -0.3125F, -0.4375F);
      }
   }

   void m_278784_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int combinedLightIn,
      int combinedOverlayIn,
      WoodType woodTypeIn,
      net.minecraft.client.model.Model modelIn
   ) {
      matrixStackIn.m_85836_();
      float f = this.m_278770_();
      matrixStackIn.m_85841_(f, -f, -f);
      net.minecraft.client.resources.model.Material material = this.m_245629_(woodTypeIn);
      com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = material.m_119194_(bufferIn, modelIn::m_103119_);
      this.m_245885_(matrixStackIn, combinedLightIn, combinedOverlayIn, modelIn, vertexconsumer);
      matrixStackIn.m_85849_();
   }

   void m_245885_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      int packedLightIn,
      int packedOverlayIn,
      net.minecraft.client.model.Model modelIn,
      com.mojang.blaze3d.vertex.VertexConsumer bufferIn
   ) {
      net.minecraft.client.renderer.blockentity.SignRenderer.SignModel signrenderer$signmodel = (net.minecraft.client.renderer.blockentity.SignRenderer.SignModel)modelIn;
      signrenderer$signmodel.f_173655_.m_104301_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
   }

   net.minecraft.client.resources.model.Material m_245629_(WoodType typeIn) {
      return Sheets.m_173381_(typeIn);
   }

   void m_278841_(
      BlockPos posIn,
      SignText textIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int combinedLightIn,
      int lineHeight,
      int lineWidth,
      boolean frontIn
   ) {
      if (isRenderText(posIn)) {
         matrixStackIn.m_85836_();
         this.m_278823_(matrixStackIn, frontIn, this.m_278725_());
         int i = m_173639_(textIn);
         int j = 4 * lineHeight / 2;
         FormattedCharSequence[] aformattedcharsequence = textIn.m_277130_(Minecraft.m_91087_().m_167974_(), componentIn -> {
            List<FormattedCharSequence> list = this.f_173633_.m_92923_(componentIn, lineWidth);
            return list.isEmpty() ? FormattedCharSequence.f_13691_ : (FormattedCharSequence)list.get(0);
         });
         int k;
         boolean flag;
         int l;
         if (textIn.m_276843_()) {
            k = textIn.m_276773_().m_41071_();
            if (Config.isCustomColors()) {
               k = CustomColors.getSignTextColor(k);
            }

            flag = m_277119_(posIn, k);
            l = 15728880;
         } else {
            k = i;
            flag = false;
            l = combinedLightIn;
         }

         for (int i1 = 0; i1 < 4; i1++) {
            FormattedCharSequence formattedcharsequence = aformattedcharsequence[i1];
            float f = (float)(-this.f_173633_.m_92724_(formattedcharsequence) / 2);
            if (flag) {
               this.f_173633_.m_168645_(formattedcharsequence, f, (float)(i1 * lineHeight - j), k, i, matrixStackIn.m_85850_().m_252922_(), bufferIn, l);
            } else {
               this.f_173633_
                  .m_272191_(
                     formattedcharsequence,
                     f,
                     (float)(i1 * lineHeight - j),
                     k,
                     false,
                     matrixStackIn.m_85850_().m_252922_(),
                     bufferIn,
                     net.minecraft.client.gui.Font.DisplayMode.POLYGON_OFFSET,
                     0,
                     l
                  );
            }
         }

         matrixStackIn.m_85849_();
      }
   }

   private void m_278823_(com.mojang.blaze3d.vertex.PoseStack matrixStackIn, boolean frontIn, net.minecraft.world.phys.Vec3 offsetIn) {
      if (!frontIn) {
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(180.0F));
      }

      float f = 0.015625F * this.m_278631_();
      matrixStackIn.m_85837_(offsetIn.f_82479_, offsetIn.f_82480_, offsetIn.f_82481_);
      matrixStackIn.m_85841_(f, -f, f);
   }

   net.minecraft.world.phys.Vec3 m_278725_() {
      return f_278459_;
   }

   static boolean m_277119_(BlockPos posIn, int colorIn) {
      if (colorIn == net.minecraft.world.item.DyeColor.BLACK.m_41071_()) {
         return true;
      } else {
         Minecraft minecraft = Minecraft.m_91087_();
         LocalPlayer localplayer = minecraft.f_91074_;
         if (localplayer != null && minecraft.f_91066_.m_92176_().m_90612_() && localplayer.m_150108_()) {
            return true;
         } else {
            Entity entity = minecraft.m_91288_();
            return entity != null && entity.m_20238_(net.minecraft.world.phys.Vec3.m_82512_(posIn)) < (double)f_173631_;
         }
      }
   }

   public static int m_173639_(SignText entityIn) {
      int i = entityIn.m_276773_().m_41071_();
      if (Config.isCustomColors()) {
         i = CustomColors.getSignTextColor(i);
      }

      if (i == net.minecraft.world.item.DyeColor.BLACK.m_41071_() && entityIn.m_276843_()) {
         return -988212;
      } else {
         double d0 = 0.4;
         int j = (int)((double)ARGB32.m_13665_(i) * 0.4);
         int k = (int)((double)ARGB32.m_13667_(i) * 0.4);
         int l = (int)((double)ARGB32.m_13669_(i) * 0.4);
         return ARGB32.m_13660_(0, j, k, l);
      }
   }

   public static net.minecraft.client.renderer.blockentity.SignRenderer.SignModel m_173646_(EntityModelSet modelSetIn, WoodType woodTypeIn) {
      return new net.minecraft.client.renderer.blockentity.SignRenderer.SignModel(modelSetIn.m_171103_(ModelLayers.m_171291_(woodTypeIn)));
   }

   public static LayerDefinition m_173654_() {
      net.minecraft.client.model.geom.builders.MeshDefinition meshdefinition = new net.minecraft.client.model.geom.builders.MeshDefinition();
      net.minecraft.client.model.geom.builders.PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("sign", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F), PartPose.f_171404_);
      partdefinition.m_171599_("stick", CubeListBuilder.m_171558_().m_171514_(0, 14).m_171481_(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   private static boolean isRenderText(BlockPos tileEntityPos) {
      if (Shaders.isShadowPass) {
         return false;
      } else {
         if (!Config.zoomMode) {
            Entity viewEntity = Minecraft.m_91087_().m_91288_();
            double distSq = viewEntity.m_20275_((double)tileEntityPos.m_123341_(), (double)tileEntityPos.m_123342_(), (double)tileEntityPos.m_123343_());
            if (distSq > textRenderDistanceSq) {
               return false;
            }
         }

         return true;
      }
   }

   public static void updateTextRenderDistance() {
      Minecraft mc = Minecraft.m_91087_();
      double fov = (double)Config.limit(mc.f_91066_.m_231837_().m_231551_(), 1, 120);
      double textRenderDistance = Math.max(1.5 * (double)mc.m_91268_().m_85444_() / fov, 16.0);
      textRenderDistanceSq = textRenderDistance * textRenderDistance;
   }

   public static final class SignModel extends net.minecraft.client.model.Model {
      public final net.minecraft.client.model.geom.ModelPart f_173655_;
      public final net.minecraft.client.model.geom.ModelPart f_112507_;

      public SignModel(net.minecraft.client.model.geom.ModelPart partIn) {
         super(net.minecraft.client.renderer.RenderType::m_110458_);
         this.f_173655_ = partIn;
         this.f_112507_ = partIn.m_171324_("stick");
      }

      @Override
      public void m_7695_(
         com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
         com.mojang.blaze3d.vertex.VertexConsumer bufferIn,
         int packedLightIn,
         int packedOverlayIn,
         int colorIn
      ) {
         this.f_173655_.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
      }
   }
}
