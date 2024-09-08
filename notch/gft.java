package net.minecraft.src;

import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.model.data.ModelData;
import net.optifine.reflect.Reflector;

public class C_4183_ implements C_79_ {
   private final C_4182_ f_110899_;
   private final C_4186_ f_110900_;
   private final C_4109_ f_173397_;
   private final C_4185_ f_110901_;
   private final C_212974_ f_110902_ = C_212974_.m_216327_();
   private final C_3423_ f_110903_;

   public C_4183_(C_4182_ shapes, C_4109_ renderer, C_3423_ colors) {
      this.f_110899_ = shapes;
      this.f_173397_ = renderer;
      this.f_110903_ = colors;
      if (Reflector.ForgeModelBlockRenderer_Constructor.exists()) {
         this.f_110900_ = (C_4186_)Reflector.newInstance(Reflector.ForgeModelBlockRenderer_Constructor, this.f_110903_);
      } else {
         this.f_110900_ = new C_4186_(this.f_110903_);
      }

      this.f_110901_ = new C_4185_();
   }

   public C_4182_ m_110907_() {
      return this.f_110899_;
   }

   public void m_110918_(C_2064_ blockStateIn, C_4675_ posIn, C_1557_ lightReaderIn, C_3181_ matrixStackIn, C_3187_ vertexBuilderIn) {
      this.renderBreakingTexture(blockStateIn, posIn, lightReaderIn, matrixStackIn, vertexBuilderIn, ModelData.EMPTY);
   }

   public void renderBreakingTexture(
      C_2064_ blockStateIn, C_4675_ posIn, C_1557_ lightReaderIn, C_3181_ matrixStackIn, C_3187_ vertexBuilderIn, ModelData modelData
   ) {
      if (blockStateIn.m_60799_() == C_1879_.MODEL) {
         C_4528_ bakedmodel = this.f_110899_.m_110893_(blockStateIn);
         long i = blockStateIn.m_60726_(posIn);
         this.f_110900_
            .tesselateBlock(
               lightReaderIn, bakedmodel, blockStateIn, posIn, matrixStackIn, vertexBuilderIn, true, this.f_110902_, i, C_4474_.f_118083_, modelData, null
            );
      }
   }

   public void m_234355_(
      C_2064_ blockStateIn, C_4675_ posIn, C_1557_ lightReaderIn, C_3181_ matrixStackIn, C_3187_ vertexBuilderIn, boolean checkSides, C_212974_ rand
   ) {
      this.renderBatched(blockStateIn, posIn, lightReaderIn, matrixStackIn, vertexBuilderIn, checkSides, rand, ModelData.EMPTY, null);
   }

   public void renderBatched(
      C_2064_ blockStateIn,
      C_4675_ posIn,
      C_1557_ lightReaderIn,
      C_3181_ matrixStackIn,
      C_3187_ vertexBuilderIn,
      boolean checkSides,
      C_212974_ rand,
      ModelData modelData,
      C_4168_ renderType
   ) {
      try {
         this.f_110900_
            .tesselateBlock(
               lightReaderIn,
               this.m_110910_(blockStateIn),
               blockStateIn,
               posIn,
               matrixStackIn,
               vertexBuilderIn,
               checkSides,
               rand,
               blockStateIn.m_60726_(posIn),
               C_4474_.f_118083_,
               modelData,
               renderType
            );
      } catch (Throwable var13) {
         C_4883_ crashreport = C_4883_.m_127521_(var13, "Tesselating block in world");
         C_4909_ crashreportcategory = crashreport.m_127514_("Block being tesselated");
         C_4909_.m_178950_(crashreportcategory, lightReaderIn, posIn, blockStateIn);
         throw new C_5204_(crashreport);
      }
   }

   public void m_234363_(C_4675_ posIn, C_1557_ lightReaderIn, C_3187_ vertexBuilderIn, C_2064_ blockStateIn, C_2691_ fluidStateIn) {
      try {
         this.f_110901_.m_234369_(lightReaderIn, posIn, vertexBuilderIn, blockStateIn, fluidStateIn);
      } catch (Throwable var9) {
         C_4883_ crashreport = C_4883_.m_127521_(var9, "Tesselating liquid in world");
         C_4909_ crashreportcategory = crashreport.m_127514_("Block being tesselated");
         C_4909_.m_178950_(crashreportcategory, lightReaderIn, posIn, null);
         throw new C_5204_(crashreport);
      }
   }

   public C_4186_ m_110937_() {
      return this.f_110900_;
   }

   public C_4528_ m_110910_(C_2064_ state) {
      return this.f_110899_.m_110893_(state);
   }

   public void m_110912_(C_2064_ blockStateIn, C_3181_ matrixStackIn, C_4139_ bufferTypeIn, int combinedLightIn, int combinedOverlayIn) {
      this.renderSingleBlock(blockStateIn, matrixStackIn, bufferTypeIn, combinedLightIn, combinedOverlayIn, ModelData.EMPTY, null);
   }

   public void renderSingleBlock(
      C_2064_ blockStateIn, C_3181_ matrixStackIn, C_4139_ bufferTypeIn, int combinedLightIn, int combinedOverlayIn, ModelData modelData, C_4168_ renderType
   ) {
      C_1879_ rendershape = blockStateIn.m_60799_();
      if (rendershape != C_1879_.INVISIBLE) {
         switch (rendershape) {
            case MODEL:
               C_4528_ bakedmodel = this.m_110910_(blockStateIn);
               int i = this.f_110903_.m_92577_(blockStateIn, null, null, 0);
               float f = (float)(i >> 16 & 0xFF) / 255.0F;
               float f1 = (float)(i >> 8 & 0xFF) / 255.0F;
               float f2 = (float)(i & 0xFF) / 255.0F;
               if (Reflector.ForgeHooksClient.exists()) {
                  for (C_4168_ rt : bakedmodel.getRenderTypes(blockStateIn, C_212974_.m_216335_(42L), modelData)) {
                     this.f_110900_
                        .renderModel(
                           matrixStackIn.m_85850_(),
                           bufferTypeIn.m_6299_(renderType != null ? renderType : RenderTypeHelper.getEntityRenderType(rt, false)),
                           blockStateIn,
                           bakedmodel,
                           f,
                           f1,
                           f2,
                           combinedLightIn,
                           combinedOverlayIn,
                           modelData,
                           rt
                        );
                  }
               } else {
                  this.f_110900_
                     .m_111067_(
                        matrixStackIn.m_85850_(),
                        bufferTypeIn.m_6299_(C_4130_.m_109284_(blockStateIn, false)),
                        blockStateIn,
                        bakedmodel,
                        f,
                        f1,
                        f2,
                        combinedLightIn,
                        combinedOverlayIn
                     );
               }
               break;
            case ENTITYBLOCK_ANIMATED:
               if (Reflector.MinecraftForge.exists()) {
                  C_1391_ stack = new C_1391_(blockStateIn.m_60734_());
                  IClientItemExtensions irp = IClientItemExtensions.of(stack);
                  C_4109_ teisr = irp.getCustomRenderer();
                  teisr.m_108829_(stack, C_268388_.NONE, matrixStackIn, bufferTypeIn, combinedLightIn, combinedOverlayIn);
               } else {
                  this.f_173397_
                     .m_108829_(new C_1391_(blockStateIn.m_60734_()), C_268388_.NONE, matrixStackIn, bufferTypeIn, combinedLightIn, combinedOverlayIn);
               }
         }
      }
   }

   public void m_6213_(C_77_ resourceManager) {
      this.f_110901_.m_110944_();
   }
}
