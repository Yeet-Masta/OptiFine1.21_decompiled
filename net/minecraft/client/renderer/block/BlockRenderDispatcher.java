package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.model.data.ModelData;
import net.optifine.reflect.Reflector;

public class BlockRenderDispatcher implements ResourceManagerReloadListener {
   private BlockModelShaper f_110899_;
   private ModelBlockRenderer f_110900_;
   private BlockEntityWithoutLevelRenderer f_173397_;
   private LiquidBlockRenderer f_110901_;
   private RandomSource f_110902_ = RandomSource.m_216327_();
   private BlockColors f_110903_;

   public BlockRenderDispatcher(BlockModelShaper shapes, BlockEntityWithoutLevelRenderer renderer, BlockColors colors) {
      this.f_110899_ = shapes;
      this.f_173397_ = renderer;
      this.f_110903_ = colors;
      if (Reflector.ForgeModelBlockRenderer_Constructor.exists()) {
         this.f_110900_ = (ModelBlockRenderer)Reflector.newInstance(Reflector.ForgeModelBlockRenderer_Constructor, this.f_110903_);
      } else {
         this.f_110900_ = new ModelBlockRenderer(this.f_110903_);
      }

      this.f_110901_ = new LiquidBlockRenderer();
   }

   public BlockModelShaper m_110907_() {
      return this.f_110899_;
   }

   public void m_110918_(BlockState blockStateIn, BlockPos posIn, BlockAndTintGetter lightReaderIn, PoseStack matrixStackIn, VertexConsumer vertexBuilderIn) {
      this.renderBreakingTexture(blockStateIn, posIn, lightReaderIn, matrixStackIn, vertexBuilderIn, ModelData.EMPTY);
   }

   public void renderBreakingTexture(
      BlockState blockStateIn, BlockPos posIn, BlockAndTintGetter lightReaderIn, PoseStack matrixStackIn, VertexConsumer vertexBuilderIn, ModelData modelData
   ) {
      if (blockStateIn.m_60799_() == RenderShape.MODEL) {
         BakedModel bakedmodel = this.f_110899_.m_110893_(blockStateIn);
         long i = blockStateIn.m_60726_(posIn);
         this.f_110900_
            .tesselateBlock(
               lightReaderIn,
               bakedmodel,
               blockStateIn,
               posIn,
               matrixStackIn,
               vertexBuilderIn,
               true,
               this.f_110902_,
               i,
               OverlayTexture.f_118083_,
               modelData,
               null
            );
      }
   }

   public void m_234355_(
      BlockState blockStateIn,
      BlockPos posIn,
      BlockAndTintGetter lightReaderIn,
      PoseStack matrixStackIn,
      VertexConsumer vertexBuilderIn,
      boolean checkSides,
      RandomSource rand
   ) {
      this.renderBatched(blockStateIn, posIn, lightReaderIn, matrixStackIn, vertexBuilderIn, checkSides, rand, ModelData.EMPTY, null);
   }

   public void renderBatched(
      BlockState blockStateIn,
      BlockPos posIn,
      BlockAndTintGetter lightReaderIn,
      PoseStack matrixStackIn,
      VertexConsumer vertexBuilderIn,
      boolean checkSides,
      RandomSource rand,
      ModelData modelData,
      RenderType renderType
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
               OverlayTexture.f_118083_,
               modelData,
               renderType
            );
      } catch (Throwable var13) {
         CrashReport crashreport = CrashReport.m_127521_(var13, "Tesselating block in world");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Block being tesselated");
         CrashReportCategory.m_178950_(crashreportcategory, lightReaderIn, posIn, blockStateIn);
         throw new ReportedException(crashreport);
      }
   }

   public void m_234363_(BlockPos posIn, BlockAndTintGetter lightReaderIn, VertexConsumer vertexBuilderIn, BlockState blockStateIn, FluidState fluidStateIn) {
      try {
         this.f_110901_.m_234369_(lightReaderIn, posIn, vertexBuilderIn, blockStateIn, fluidStateIn);
      } catch (Throwable var9) {
         CrashReport crashreport = CrashReport.m_127521_(var9, "Tesselating liquid in world");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Block being tesselated");
         CrashReportCategory.m_178950_(crashreportcategory, lightReaderIn, posIn, null);
         throw new ReportedException(crashreport);
      }
   }

   public ModelBlockRenderer m_110937_() {
      return this.f_110900_;
   }

   public BakedModel m_110910_(BlockState state) {
      return this.f_110899_.m_110893_(state);
   }

   public void m_110912_(BlockState blockStateIn, PoseStack matrixStackIn, MultiBufferSource bufferTypeIn, int combinedLightIn, int combinedOverlayIn) {
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
      RenderShape rendershape = blockStateIn.m_60799_();
      if (rendershape != RenderShape.INVISIBLE) {
         switch (<unrepresentable>.$SwitchMap$net$minecraft$world$level$block$RenderShape[rendershape.ordinal()]) {
            case 1:
               BakedModel bakedmodel = this.m_110910_(blockStateIn);
               int i = this.f_110903_.m_92577_(blockStateIn, null, null, 0);
               float f = (float)(i >> 16 & 0xFF) / 255.0F;
               float f1 = (float)(i >> 8 & 0xFF) / 255.0F;
               float f2 = (float)(i & 0xFF) / 255.0F;
               if (Reflector.ForgeHooksClient.exists()) {
                  for (RenderType rt : bakedmodel.getRenderTypes(blockStateIn, RandomSource.m_216335_(42L), modelData)) {
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
                        bufferTypeIn.m_6299_(ItemBlockRenderTypes.m_109284_(blockStateIn, false)),
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
            case 2:
               if (Reflector.MinecraftForge.exists()) {
                  ItemStack stack = new ItemStack(blockStateIn.m_60734_());
                  IClientItemExtensions irp = IClientItemExtensions.m_253057_(stack);
                  BlockEntityWithoutLevelRenderer teisr = irp.getCustomRenderer();
                  teisr.m_108829_(stack, ItemDisplayContext.NONE, matrixStackIn, bufferTypeIn, combinedLightIn, combinedOverlayIn);
               } else {
                  this.f_173397_
                     .m_108829_(
                        new ItemStack(blockStateIn.m_60734_()), ItemDisplayContext.NONE, matrixStackIn, bufferTypeIn, combinedLightIn, combinedOverlayIn
                     );
               }
         }
      }
   }

   public void m_6213_(ResourceManager resourceManager) {
      this.f_110901_.m_110944_();
   }
}
