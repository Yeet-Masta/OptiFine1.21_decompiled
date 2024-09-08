package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import net.optifine.Config;

public class C_4443_<T extends C_859_> extends C_4447_<T, C_3811_<T>> {
   private C_3889_ modelRendererMushroom;
   private static final C_5265_ LOCATION_MUSHROOM_RED = new C_5265_("textures/entity/cow/red_mushroom.png");
   private static final C_5265_ LOCATION_MUSHROOM_BROWN = new C_5265_("textures/entity/cow/brown_mushroom.png");
   private static boolean hasTextureMushroomRed = false;
   private static boolean hasTextureMushroomBrown = false;
   private final C_4183_ f_234848_;

   public C_4443_(C_4382_<T, C_3811_<T>> p_i234849_1_, C_4183_ p_i234849_2_) {
      super(p_i234849_1_);
      this.f_234848_ = p_i234849_2_;
      this.modelRendererMushroom = new C_3889_(new ArrayList(), new HashMap());
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
      C_3181_ matrixStackIn,
      C_4139_ bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!entitylivingbaseIn.o_()) {
         C_3391_ minecraft = C_3391_.m_91087_();
         boolean flag = minecraft.m_91314_(entitylivingbaseIn) && entitylivingbaseIn.ci();
         if (!entitylivingbaseIn.ci() || flag) {
            C_2064_ blockstate = entitylivingbaseIn.m_28554_().m_28969_();
            C_5265_ locMushroom = this.getCustomMushroom(blockstate);
            C_3187_ bufferMushroom = null;
            if (locMushroom != null) {
               bufferMushroom = bufferIn.m_6299_(C_4168_.m_110452_(locMushroom));
            }

            int i = C_4357_.m_115338_(entitylivingbaseIn, 0.0F);
            C_4528_ bakedmodel = this.f_234848_.m_110910_(blockstate);
            matrixStackIn.m_85836_();
            matrixStackIn.m_252880_(0.2F, -0.35F, 0.5F);
            matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(-48.0F));
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
            matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(42.0F));
            matrixStackIn.m_252880_(0.1F, 0.0F, -0.6F);
            matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(-48.0F));
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
            matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(-78.0F));
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
      C_3181_ matrixStackIn, C_4139_ renderTypeBuffer, int packedLightIn, boolean renderBrightness, C_2064_ blockStateIn, int packedOverlayIn, C_4528_ modelIn
   ) {
      if (renderBrightness) {
         this.f_234848_
            .m_110937_()
            .m_111067_(
               matrixStackIn.m_85850_(),
               renderTypeBuffer.m_6299_(C_4168_.m_110491_(C_4484_.f_118259_)),
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

   private C_5265_ getCustomMushroom(C_2064_ iblockstate) {
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
