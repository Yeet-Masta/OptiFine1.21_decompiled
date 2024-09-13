package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.Config;

public class MushroomCowMushroomLayer<T extends MushroomCow> extends RenderLayer<T, CowModel<T>> {
   private ModelPart modelRendererMushroom;
   private static ResourceLocation LOCATION_MUSHROOM_RED = new ResourceLocation("textures/entity/cow/red_mushroom.png");
   private static ResourceLocation LOCATION_MUSHROOM_BROWN = new ResourceLocation("textures/entity/cow/brown_mushroom.png");
   private static boolean hasTextureMushroomRed = false;
   private static boolean hasTextureMushroomBrown = false;
   private BlockRenderDispatcher f_234848_;

   public MushroomCowMushroomLayer(RenderLayerParent<T, CowModel<T>> p_i234849_1_, BlockRenderDispatcher p_i234849_2_) {
      super(p_i234849_1_);
      this.f_234848_ = p_i234849_2_;
      this.modelRendererMushroom = new ModelPart(new ArrayList(), new HashMap());
      this.modelRendererMushroom.setTextureSize(16, 16);
      this.modelRendererMushroom.f_104200_ = 8.0F;
      this.modelRendererMushroom.f_104202_ = 8.0F;
      this.modelRendererMushroom.f_104204_ = (float) (Math.PI / 4);
      float[][] faceUvs = new float[][]{null, null, {16.0F, 16.0F, 0.0F, 0.0F}, {16.0F, 16.0F, 0.0F, 0.0F}, null, null};
      this.modelRendererMushroom.addBox(faceUvs, -10.0F, 0.0F, 0.0F, 20.0F, 16.0F, 0.0F, 0.0F);
      float[][] faceUvs2 = new float[][]{null, null, null, null, {16.0F, 16.0F, 0.0F, 0.0F}, {16.0F, 16.0F, 0.0F, 0.0F}};
      this.modelRendererMushroom.addBox(faceUvs2, 0.0F, 0.0F, -10.0F, 0.0F, 16.0F, 20.0F, 0.0F);
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
      if (!entitylivingbaseIn.m_6162_()) {
         Minecraft minecraft = Minecraft.m_91087_();
         boolean flag = minecraft.m_91314_(entitylivingbaseIn) && entitylivingbaseIn.m_20145_();
         if (!entitylivingbaseIn.m_20145_() || flag) {
            BlockState blockstate = entitylivingbaseIn.m_28554_().m_28969_();
            ResourceLocation locMushroom = this.getCustomMushroom(blockstate);
            VertexConsumer bufferMushroom = null;
            if (locMushroom != null) {
               bufferMushroom = bufferIn.m_6299_(RenderType.m_110452_(locMushroom));
            }

            int i = LivingEntityRenderer.m_115338_(entitylivingbaseIn, 0.0F);
            BakedModel bakedmodel = this.f_234848_.m_110910_(blockstate);
            matrixStackIn.m_85836_();
            matrixStackIn.m_252880_(0.2F, -0.35F, 0.5F);
            matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(-48.0F));
            matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
            matrixStackIn.m_252880_(-0.5F, -0.5F, -0.5F);
            if (locMushroom != null) {
               this.modelRendererMushroom.m_104301_(matrixStackIn, bufferMushroom, packedLightIn, i);
            } else {
               this.m_234852_(matrixStackIn, bufferIn, packedLightIn, flag, blockstate, i, bakedmodel);
            }

            matrixStackIn.m_85849_();
            matrixStackIn.m_85836_();
            matrixStackIn.m_252880_(0.2F, -0.35F, 0.5F);
            matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(42.0F));
            matrixStackIn.m_252880_(0.1F, 0.0F, -0.6F);
            matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(-48.0F));
            matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
            matrixStackIn.m_252880_(-0.5F, -0.5F, -0.5F);
            if (locMushroom != null) {
               this.modelRendererMushroom.m_104301_(matrixStackIn, bufferMushroom, packedLightIn, i);
            } else {
               this.m_234852_(matrixStackIn, bufferIn, packedLightIn, flag, blockstate, i, bakedmodel);
            }

            matrixStackIn.m_85849_();
            matrixStackIn.m_85836_();
            this.m_117386_().m_102450_().m_104299_(matrixStackIn);
            matrixStackIn.m_252880_(0.0F, -0.7F, -0.2F);
            matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(-78.0F));
            matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
            matrixStackIn.m_252880_(-0.5F, -0.5F, -0.5F);
            if (locMushroom != null) {
               this.modelRendererMushroom.m_104301_(matrixStackIn, bufferMushroom, packedLightIn, i);
            } else {
               this.m_234852_(matrixStackIn, bufferIn, packedLightIn, flag, blockstate, i, bakedmodel);
            }

            matrixStackIn.m_85849_();
         }
      }
   }

   private void m_234852_(
      PoseStack matrixStackIn,
      MultiBufferSource renderTypeBuffer,
      int packedLightIn,
      boolean renderBrightness,
      BlockState blockStateIn,
      int packedOverlayIn,
      BakedModel modelIn
   ) {
      if (renderBrightness) {
         this.f_234848_
            .m_110937_()
            .m_111067_(
               matrixStackIn.m_85850_(),
               renderTypeBuffer.m_6299_(RenderType.m_110491_(TextureAtlas.f_118259_)),
               blockStateIn,
               modelIn,
               0.0F,
               0.0F,
               0.0F,
               packedLightIn,
               packedOverlayIn
            );
      } else {
         this.f_234848_.m_110912_(blockStateIn, matrixStackIn, renderTypeBuffer, packedLightIn, packedOverlayIn);
      }
   }

   private ResourceLocation getCustomMushroom(BlockState iblockstate) {
      Block block = iblockstate.m_60734_();
      if (block == Blocks.f_50073_ && hasTextureMushroomRed) {
         return LOCATION_MUSHROOM_RED;
      } else {
         return block == Blocks.f_50072_ && hasTextureMushroomBrown ? LOCATION_MUSHROOM_BROWN : null;
      }
   }

   public static void m_252999_() {
      hasTextureMushroomRed = Config.hasResource(LOCATION_MUSHROOM_RED);
      hasTextureMushroomBrown = Config.hasResource(LOCATION_MUSHROOM_BROWN);
   }
}
