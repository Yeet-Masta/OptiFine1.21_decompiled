package net.minecraft.client.renderer.block;

import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.model.data.ModelData;
import net.optifine.reflect.Reflector;

public class BlockRenderDispatcher implements ResourceManagerReloadListener {
   private final BlockModelShaper f_110899_;
   private final net.minecraft.client.renderer.block.ModelBlockRenderer f_110900_;
   private final net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer f_173397_;
   private final net.minecraft.client.renderer.block.LiquidBlockRenderer f_110901_;
   private final RandomSource f_110902_ = RandomSource.m_216327_();
   private final BlockColors f_110903_;

   public BlockRenderDispatcher(BlockModelShaper shapes, net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer renderer, BlockColors colors) {
      this.f_110899_ = shapes;
      this.f_173397_ = renderer;
      this.f_110903_ = colors;
      if (Reflector.ForgeModelBlockRenderer_Constructor.exists()) {
         this.f_110900_ = (net.minecraft.client.renderer.block.ModelBlockRenderer)Reflector.newInstance(
            Reflector.ForgeModelBlockRenderer_Constructor, this.f_110903_
         );
      } else {
         this.f_110900_ = new net.minecraft.client.renderer.block.ModelBlockRenderer(this.f_110903_);
      }

      this.f_110901_ = new net.minecraft.client.renderer.block.LiquidBlockRenderer();
   }

   public BlockModelShaper m_110907_() {
      return this.f_110899_;
   }

   public void m_110918_(
      net.minecraft.world.level.block.state.BlockState blockStateIn,
      BlockPos posIn,
      BlockAndTintGetter lightReaderIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      com.mojang.blaze3d.vertex.VertexConsumer vertexBuilderIn
   ) {
      this.renderBreakingTexture(blockStateIn, posIn, lightReaderIn, matrixStackIn, vertexBuilderIn, ModelData.EMPTY);
   }

   public void renderBreakingTexture(
      net.minecraft.world.level.block.state.BlockState blockStateIn,
      BlockPos posIn,
      BlockAndTintGetter lightReaderIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      com.mojang.blaze3d.vertex.VertexConsumer vertexBuilderIn,
      ModelData modelData
   ) {
      if (blockStateIn.m_60799_() == RenderShape.MODEL) {
         net.minecraft.client.resources.model.BakedModel bakedmodel = this.f_110899_.m_110893_(blockStateIn);
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
      net.minecraft.world.level.block.state.BlockState blockStateIn,
      BlockPos posIn,
      BlockAndTintGetter lightReaderIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      com.mojang.blaze3d.vertex.VertexConsumer vertexBuilderIn,
      boolean checkSides,
      RandomSource rand
   ) {
      this.renderBatched(blockStateIn, posIn, lightReaderIn, matrixStackIn, vertexBuilderIn, checkSides, rand, ModelData.EMPTY, null);
   }

   public void renderBatched(
      net.minecraft.world.level.block.state.BlockState blockStateIn,
      BlockPos posIn,
      BlockAndTintGetter lightReaderIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      com.mojang.blaze3d.vertex.VertexConsumer vertexBuilderIn,
      boolean checkSides,
      RandomSource rand,
      ModelData modelData,
      net.minecraft.client.renderer.RenderType renderType
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
         net.minecraft.CrashReport crashreport = net.minecraft.CrashReport.m_127521_(var13, "Tesselating block in world");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Block being tesselated");
         CrashReportCategory.m_178950_(crashreportcategory, lightReaderIn, posIn, blockStateIn);
         throw new ReportedException(crashreport);
      }
   }

   public void m_234363_(
      BlockPos posIn,
      BlockAndTintGetter lightReaderIn,
      com.mojang.blaze3d.vertex.VertexConsumer vertexBuilderIn,
      net.minecraft.world.level.block.state.BlockState blockStateIn,
      FluidState fluidStateIn
   ) {
      try {
         this.f_110901_.m_234369_(lightReaderIn, posIn, vertexBuilderIn, blockStateIn, fluidStateIn);
      } catch (Throwable var9) {
         net.minecraft.CrashReport crashreport = net.minecraft.CrashReport.m_127521_(var9, "Tesselating liquid in world");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Block being tesselated");
         CrashReportCategory.m_178950_(crashreportcategory, lightReaderIn, posIn, null);
         throw new ReportedException(crashreport);
      }
   }

   public net.minecraft.client.renderer.block.ModelBlockRenderer m_110937_() {
      return this.f_110900_;
   }

   public net.minecraft.client.resources.model.BakedModel m_110910_(net.minecraft.world.level.block.state.BlockState state) {
      return this.f_110899_.m_110893_(state);
   }

   public void m_110912_(
      net.minecraft.world.level.block.state.BlockState blockStateIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferTypeIn,
      int combinedLightIn,
      int combinedOverlayIn
   ) {
      this.renderSingleBlock(blockStateIn, matrixStackIn, bufferTypeIn, combinedLightIn, combinedOverlayIn, ModelData.EMPTY, null);
   }

   public void renderSingleBlock(
      net.minecraft.world.level.block.state.BlockState blockStateIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferTypeIn,
      int combinedLightIn,
      int combinedOverlayIn,
      ModelData modelData,
      net.minecraft.client.renderer.RenderType renderType
   ) {
      RenderShape rendershape = blockStateIn.m_60799_();
      if (rendershape != RenderShape.INVISIBLE) {
         switch (rendershape) {
            case MODEL:
               net.minecraft.client.resources.model.BakedModel bakedmodel = this.m_110910_(blockStateIn);
               int i = this.f_110903_.m_92577_(blockStateIn, null, null, 0);
               float f = (float)(i >> 16 & 0xFF) / 255.0F;
               float f1 = (float)(i >> 8 & 0xFF) / 255.0F;
               float f2 = (float)(i & 0xFF) / 255.0F;
               if (Reflector.ForgeHooksClient.exists()) {
                  for (net.minecraft.client.renderer.RenderType rt : bakedmodel.getRenderTypes(blockStateIn, RandomSource.m_216335_(42L), modelData)) {
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
            case ENTITYBLOCK_ANIMATED:
               if (Reflector.MinecraftForge.exists()) {
                  ItemStack stack = new ItemStack(blockStateIn.m_60734_());
                  IClientItemExtensions irp = IClientItemExtensions.of(stack);
                  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer teisr = irp.getCustomRenderer();
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
