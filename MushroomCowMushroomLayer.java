import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3811_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_859_;
import net.optifine.Config;

public class MushroomCowMushroomLayer<T extends C_859_> extends RenderLayer<T, C_3811_<T>> {
   private ModelPart modelRendererMushroom;
   private static final ResourceLocation LOCATION_MUSHROOM_RED = new ResourceLocation("textures/entity/cow/red_mushroom.png");
   private static final ResourceLocation LOCATION_MUSHROOM_BROWN = new ResourceLocation("textures/entity/cow/brown_mushroom.png");
   private static boolean hasTextureMushroomRed = false;
   private static boolean hasTextureMushroomBrown = false;
   private final BlockRenderDispatcher a;

   public MushroomCowMushroomLayer(C_4382_<T, C_3811_<T>> p_i234849_1_, BlockRenderDispatcher p_i234849_2_) {
      super(p_i234849_1_);
      this.a = p_i234849_2_;
      this.modelRendererMushroom = new ModelPart(new ArrayList(), new HashMap());
      this.modelRendererMushroom.setTextureSize(16, 16);
      this.modelRendererMushroom.b = 8.0F;
      this.modelRendererMushroom.d = 8.0F;
      this.modelRendererMushroom.f = (float) (Math.PI / 4);
      float[][] faceUvs = new float[][]{null, null, {16.0F, 16.0F, 0.0F, 0.0F}, {16.0F, 16.0F, 0.0F, 0.0F}, null, null};
      this.modelRendererMushroom.addBox(faceUvs, -10.0F, 0.0F, 0.0F, 20.0F, 16.0F, 0.0F, 0.0F);
      float[][] faceUvs2 = new float[][]{null, null, null, null, {16.0F, 16.0F, 0.0F, 0.0F}, {16.0F, 16.0F, 0.0F, 0.0F}};
      this.modelRendererMushroom.addBox(faceUvs2, 0.0F, 0.0F, -10.0F, 0.0F, 16.0F, 20.0F, 0.0F);
   }

   public void a(
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
         C_3391_ minecraft = C_3391_.m_91087_();
         boolean flag = minecraft.m_91314_(entitylivingbaseIn) && entitylivingbaseIn.ci();
         if (!entitylivingbaseIn.ci() || flag) {
            BlockState blockstate = entitylivingbaseIn.m_28554_().a();
            ResourceLocation locMushroom = this.getCustomMushroom(blockstate);
            VertexConsumer bufferMushroom = null;
            if (locMushroom != null) {
               bufferMushroom = bufferIn.getBuffer(RenderType.d(locMushroom));
            }

            int i = LivingEntityRenderer.c(entitylivingbaseIn, 0.0F);
            BakedModel bakedmodel = this.a.a(blockstate);
            matrixStackIn.a();
            matrixStackIn.a(0.2F, -0.35F, 0.5F);
            matrixStackIn.a(C_252363_.f_252436_.m_252977_(-48.0F));
            matrixStackIn.b(-1.0F, -1.0F, 1.0F);
            matrixStackIn.a(-0.5F, -0.5F, -0.5F);
            if (locMushroom != null) {
               this.modelRendererMushroom.a(matrixStackIn, bufferMushroom, packedLightIn, i);
            } else {
               this.a(matrixStackIn, bufferIn, packedLightIn, flag, blockstate, i, bakedmodel);
            }

            matrixStackIn.b();
            matrixStackIn.a();
            matrixStackIn.a(0.2F, -0.35F, 0.5F);
            matrixStackIn.a(C_252363_.f_252436_.m_252977_(42.0F));
            matrixStackIn.a(0.1F, 0.0F, -0.6F);
            matrixStackIn.a(C_252363_.f_252436_.m_252977_(-48.0F));
            matrixStackIn.b(-1.0F, -1.0F, 1.0F);
            matrixStackIn.a(-0.5F, -0.5F, -0.5F);
            if (locMushroom != null) {
               this.modelRendererMushroom.a(matrixStackIn, bufferMushroom, packedLightIn, i);
            } else {
               this.a(matrixStackIn, bufferIn, packedLightIn, flag, blockstate, i, bakedmodel);
            }

            matrixStackIn.b();
            matrixStackIn.a();
            this.c().d().a(matrixStackIn);
            matrixStackIn.a(0.0F, -0.7F, -0.2F);
            matrixStackIn.a(C_252363_.f_252436_.m_252977_(-78.0F));
            matrixStackIn.b(-1.0F, -1.0F, 1.0F);
            matrixStackIn.a(-0.5F, -0.5F, -0.5F);
            if (locMushroom != null) {
               this.modelRendererMushroom.a(matrixStackIn, bufferMushroom, packedLightIn, i);
            } else {
               this.a(matrixStackIn, bufferIn, packedLightIn, flag, blockstate, i, bakedmodel);
            }

            matrixStackIn.b();
         }
      }
   }

   private void a(
      PoseStack matrixStackIn,
      MultiBufferSource renderTypeBuffer,
      int packedLightIn,
      boolean renderBrightness,
      BlockState blockStateIn,
      int packedOverlayIn,
      BakedModel modelIn
   ) {
      if (renderBrightness) {
         this.a
            .b()
            .a(
               matrixStackIn.c(),
               renderTypeBuffer.getBuffer(RenderType.r(TextureAtlas.e)),
               blockStateIn,
               modelIn,
               0.0F,
               0.0F,
               0.0F,
               packedLightIn,
               packedOverlayIn
            );
      } else {
         this.a.a(blockStateIn, matrixStackIn, renderTypeBuffer, packedLightIn, packedOverlayIn);
      }
   }

   private ResourceLocation getCustomMushroom(BlockState iblockstate) {
      C_1706_ block = iblockstate.m_60734_();
      if (block == C_1710_.f_50073_ && hasTextureMushroomRed) {
         return LOCATION_MUSHROOM_RED;
      } else {
         return block == C_1710_.f_50072_ && hasTextureMushroomBrown ? LOCATION_MUSHROOM_BROWN : null;
      }
   }

   public static void update() {
      hasTextureMushroomRed = Config.hasResource(LOCATION_MUSHROOM_RED);
      hasTextureMushroomBrown = Config.hasResource(LOCATION_MUSHROOM_BROWN);
   }
}
