import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_1879_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_268388_;
import net.minecraft.src.C_2691_;
import net.minecraft.src.C_3423_;
import net.minecraft.src.C_4130_;
import net.minecraft.src.C_4182_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_79_;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.model.data.ModelData;
import net.optifine.reflect.Reflector;

public class BlockRenderDispatcher implements C_79_ {
   private final C_4182_ a;
   private final ModelBlockRenderer b;
   private final BlockEntityWithoutLevelRenderer c;
   private final LiquidBlockRenderer d;
   private final C_212974_ e = C_212974_.m_216327_();
   private final C_3423_ f;

   public BlockRenderDispatcher(C_4182_ shapes, BlockEntityWithoutLevelRenderer renderer, C_3423_ colors) {
      this.a = shapes;
      this.c = renderer;
      this.f = colors;
      if (Reflector.ForgeModelBlockRenderer_Constructor.exists()) {
         this.b = (ModelBlockRenderer)Reflector.newInstance(Reflector.ForgeModelBlockRenderer_Constructor, this.f);
      } else {
         this.b = new ModelBlockRenderer(this.f);
      }

      this.d = new LiquidBlockRenderer();
   }

   public C_4182_ a() {
      return this.a;
   }

   public void a(BlockState blockStateIn, C_4675_ posIn, C_1557_ lightReaderIn, PoseStack matrixStackIn, VertexConsumer vertexBuilderIn) {
      this.renderBreakingTexture(blockStateIn, posIn, lightReaderIn, matrixStackIn, vertexBuilderIn, ModelData.EMPTY);
   }

   public void renderBreakingTexture(
      BlockState blockStateIn, C_4675_ posIn, C_1557_ lightReaderIn, PoseStack matrixStackIn, VertexConsumer vertexBuilderIn, ModelData modelData
   ) {
      if (blockStateIn.m_60799_() == C_1879_.MODEL) {
         BakedModel bakedmodel = this.a.b(blockStateIn);
         long i = blockStateIn.m_60726_(posIn);
         this.b
            .tesselateBlock(lightReaderIn, bakedmodel, blockStateIn, posIn, matrixStackIn, vertexBuilderIn, true, this.e, i, C_4474_.f_118083_, modelData, null);
      }
   }

   public void a(
      BlockState blockStateIn,
      C_4675_ posIn,
      C_1557_ lightReaderIn,
      PoseStack matrixStackIn,
      VertexConsumer vertexBuilderIn,
      boolean checkSides,
      C_212974_ rand
   ) {
      this.renderBatched(blockStateIn, posIn, lightReaderIn, matrixStackIn, vertexBuilderIn, checkSides, rand, ModelData.EMPTY, null);
   }

   public void renderBatched(
      BlockState blockStateIn,
      C_4675_ posIn,
      C_1557_ lightReaderIn,
      PoseStack matrixStackIn,
      VertexConsumer vertexBuilderIn,
      boolean checkSides,
      C_212974_ rand,
      ModelData modelData,
      RenderType renderType
   ) {
      try {
         this.b
            .tesselateBlock(
               lightReaderIn,
               this.a(blockStateIn),
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
         CrashReport crashreport = CrashReport.a(var13, "Tesselating block in world");
         C_4909_ crashreportcategory = crashreport.a("Block being tesselated");
         C_4909_.a(crashreportcategory, lightReaderIn, posIn, blockStateIn);
         throw new C_5204_(crashreport);
      }
   }

   public void a(C_4675_ posIn, C_1557_ lightReaderIn, VertexConsumer vertexBuilderIn, BlockState blockStateIn, C_2691_ fluidStateIn) {
      try {
         this.d.a(lightReaderIn, posIn, vertexBuilderIn, blockStateIn, fluidStateIn);
      } catch (Throwable var9) {
         CrashReport crashreport = CrashReport.a(var9, "Tesselating liquid in world");
         C_4909_ crashreportcategory = crashreport.a("Block being tesselated");
         C_4909_.a(crashreportcategory, lightReaderIn, posIn, null);
         throw new C_5204_(crashreport);
      }
   }

   public ModelBlockRenderer b() {
      return this.b;
   }

   public BakedModel a(BlockState state) {
      return this.a.b(state);
   }

   public void a(BlockState blockStateIn, PoseStack matrixStackIn, MultiBufferSource bufferTypeIn, int combinedLightIn, int combinedOverlayIn) {
      this.renderSingleBlock(blockStateIn, matrixStackIn, bufferTypeIn, combinedLightIn, combinedOverlayIn, ModelData.EMPTY, null);
   }

   public void renderSingleBlock(
      BlockState blockStateIn,
      PoseStack matrixStackIn,
      MultiBufferSource bufferTypeIn,
      int combinedLightIn,
      int combinedOverlayIn,
      ModelData modelData,
      RenderType renderType
   ) {
      C_1879_ rendershape = blockStateIn.m_60799_();
      if (rendershape != C_1879_.INVISIBLE) {
         switch (rendershape) {
            case MODEL:
               BakedModel bakedmodel = this.a(blockStateIn);
               int i = this.f.a(blockStateIn, null, null, 0);
               float f = (float)(i >> 16 & 0xFF) / 255.0F;
               float f1 = (float)(i >> 8 & 0xFF) / 255.0F;
               float f2 = (float)(i & 0xFF) / 255.0F;
               if (Reflector.ForgeHooksClient.exists()) {
                  for (RenderType rt : bakedmodel.getRenderTypes(blockStateIn, C_212974_.m_216335_(42L), modelData)) {
                     this.b
                        .renderModel(
                           matrixStackIn.c(),
                           bufferTypeIn.getBuffer(renderType != null ? renderType : RenderTypeHelper.getEntityRenderType(rt, false)),
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
                  this.b
                     .a(
                        matrixStackIn.c(),
                        bufferTypeIn.getBuffer(C_4130_.a(blockStateIn, false)),
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
                  BlockEntityWithoutLevelRenderer teisr = irp.getCustomRenderer();
                  teisr.a(stack, C_268388_.NONE, matrixStackIn, bufferTypeIn, combinedLightIn, combinedOverlayIn);
               } else {
                  this.c.a(new C_1391_(blockStateIn.m_60734_()), C_268388_.NONE, matrixStackIn, bufferTypeIn, combinedLightIn, combinedOverlayIn);
               }
         }
      }
   }

   public void m_6213_(C_77_ resourceManager) {
      this.d.a();
   }
}
