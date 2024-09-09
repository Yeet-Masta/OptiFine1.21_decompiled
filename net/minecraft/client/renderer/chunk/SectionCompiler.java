package net.minecraft.client.renderer.chunk;

import com.mojang.blaze3d.vertex.VertexSorting;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.model.data.ModelData;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.override.ChunkCacheOF;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.Shaders;
import net.optifine.util.SingleIterable;

public class SectionCompiler {
   private final net.minecraft.client.renderer.block.BlockRenderDispatcher f_337337_;
   private final net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher f_337625_;
   protected net.minecraft.client.renderer.chunk.SectionRenderDispatcher sectionRenderDispatcher;
   public static final boolean FORGE = net.minecraft.client.renderer.chunk.SectionRenderDispatcher.FORGE;

   public SectionCompiler(
      net.minecraft.client.renderer.block.BlockRenderDispatcher p_i340324_1_,
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher p_i340324_2_
   ) {
      this.f_337337_ = p_i340324_1_;
      this.f_337625_ = p_i340324_2_;
   }

   public net.minecraft.client.renderer.chunk.SectionCompiler.Results m_340354_(
      SectionPos sectionPosIn, net.minecraft.client.renderer.chunk.RenderChunkRegion regionIn, VertexSorting sortingIn, SectionBufferBuilderPack builderIn
   ) {
      return this.compile(sectionPosIn, regionIn, sortingIn, builderIn, 0, 0, 0);
   }

   public net.minecraft.client.renderer.chunk.SectionCompiler.Results compile(
      SectionPos sectionPosIn,
      net.minecraft.client.renderer.chunk.RenderChunkRegion regionIn,
      VertexSorting sortingIn,
      SectionBufferBuilderPack builderIn,
      int regionDX,
      int regionDY,
      int regionDZ
   ) {
      Map<BlockPos, ModelData> modelDataMap = FORGE ? Minecraft.m_91087_().f_91073_.getModelDataManager().getAt(sectionPosIn) : null;
      net.minecraft.client.renderer.chunk.SectionCompiler.Results sectioncompiler$results = new net.minecraft.client.renderer.chunk.SectionCompiler.Results();
      BlockPos blockpos = sectionPosIn.m_123249_();
      BlockPos blockpos1 = blockpos.m_7918_(15, 15, 15);
      net.minecraft.client.renderer.chunk.VisGraph visgraph = new net.minecraft.client.renderer.chunk.VisGraph();
      com.mojang.blaze3d.vertex.PoseStack posestack = new com.mojang.blaze3d.vertex.PoseStack();
      net.minecraft.client.renderer.chunk.SectionRenderDispatcher.renderChunksUpdated++;
      ChunkCacheOF renderchunkregion = regionIn.makeChunkCacheOF();
      renderchunkregion.renderStart();
      SingleIterable<net.minecraft.client.renderer.RenderType> singleLayer = new SingleIterable<>();
      boolean shaders = Config.isShaders();
      boolean shadersMidBlock = shaders && Shaders.useMidBlockAttrib;
      net.minecraft.client.renderer.block.ModelBlockRenderer.m_111000_();
      Map<net.minecraft.client.renderer.RenderType, com.mojang.blaze3d.vertex.BufferBuilder> map = new Reference2ObjectArrayMap(
         net.minecraft.client.renderer.RenderType.m_110506_().size()
      );
      RandomSource randomsource = RandomSource.m_216327_();
      net.minecraft.client.renderer.block.BlockRenderDispatcher blockrenderdispatcher = Minecraft.m_91087_().m_91289_();

      for (BlockPosM blockpos2 : BlockPosM.getAllInBoxMutableM(blockpos, blockpos1)) {
         net.minecraft.world.level.block.state.BlockState blockstate = regionIn.m_8055_(blockpos2);
         if (!blockstate.m_60795_()) {
            if (blockstate.m_60804_(regionIn, blockpos2)) {
               visgraph.m_112971_(blockpos2);
            }

            if (blockstate.m_155947_()) {
               net.minecraft.world.level.block.entity.BlockEntity blockentity = regionIn.m_7702_(blockpos2);
               if (blockentity != null) {
                  this.m_339434_(sectioncompiler$results, blockentity);
               }
            }

            FluidState fluidstate = blockstate.m_60819_();
            if (!fluidstate.m_76178_()) {
               net.minecraft.client.renderer.RenderType rendertype = ItemBlockRenderTypes.m_109287_(fluidstate);
               com.mojang.blaze3d.vertex.BufferBuilder bufferbuilder = this.m_340252_(map, builderIn, rendertype);
               RenderEnv renderEnv = bufferbuilder.getRenderEnv(blockstate, blockpos2);
               renderEnv.setCompileParams(this, map, builderIn);
               renderchunkregion.setRenderEnv(renderEnv);
               this.f_337337_.m_234363_(blockpos2, regionIn, bufferbuilder, blockstate, fluidstate);
            }

            if (blockstate.m_60799_() == RenderShape.MODEL) {
               net.minecraft.client.resources.model.BakedModel model = blockrenderdispatcher.m_110910_(blockstate);
               ModelData modelData = FORGE
                  ? model.getModelData(renderchunkregion, blockpos2, blockstate, (ModelData)modelDataMap.getOrDefault(blockpos2, ModelData.EMPTY))
                  : null;

               for (net.minecraft.client.renderer.RenderType renderTypeForge : getBlockRenderLayers(
                  model, blockstate, blockpos2, randomsource, modelData, singleLayer
               )) {
                  net.minecraft.client.renderer.RenderType rendertype2 = ItemBlockRenderTypes.m_109282_(blockstate);
                  com.mojang.blaze3d.vertex.BufferBuilder bufferbuilder1 = this.m_340252_(map, builderIn, rendertype2);
                  RenderEnv renderEnv = bufferbuilder1.getRenderEnv(blockstate, blockpos2);
                  renderEnv.setCompileParams(this, map, builderIn);
                  renderchunkregion.setRenderEnv(renderEnv);
                  posestack.m_85836_();
                  posestack.m_252880_(
                     (float)regionDX + (float)SectionPos.m_123207_(blockpos2.m_123341_()),
                     (float)regionDY + (float)SectionPos.m_123207_(blockpos2.m_123342_()),
                     (float)regionDZ + (float)SectionPos.m_123207_(blockpos2.m_123343_())
                  );
                  if (shadersMidBlock) {
                     bufferbuilder1.setMidBlock(
                        0.5F + (float)regionDX + (float)SectionPos.m_123207_(blockpos2.m_123341_()),
                        0.5F + (float)regionDY + (float)SectionPos.m_123207_(blockpos2.m_123342_()),
                        0.5F + (float)regionDZ + (float)SectionPos.m_123207_(blockpos2.m_123343_())
                     );
                  }

                  this.f_337337_.renderBatched(blockstate, blockpos2, regionIn, posestack, bufferbuilder1, true, randomsource, modelData, renderTypeForge);
                  posestack.m_85849_();
               }
            }
         }
      }

      for (net.minecraft.client.renderer.RenderType blockrenderlayer : net.minecraft.client.renderer.chunk.SectionRenderDispatcher.BLOCK_RENDER_LAYERS) {
         sectioncompiler$results.setAnimatedSprites(blockrenderlayer, null);
      }

      for (Entry<net.minecraft.client.renderer.RenderType, com.mojang.blaze3d.vertex.BufferBuilder> entry : map.entrySet()) {
         net.minecraft.client.renderer.RenderType rendertype1 = (net.minecraft.client.renderer.RenderType)entry.getKey();
         com.mojang.blaze3d.vertex.BufferBuilder bufferBuilder = (com.mojang.blaze3d.vertex.BufferBuilder)entry.getValue();
         if (Config.isShaders()) {
            SVertexBuilder.calcNormalChunkLayer(bufferBuilder);
         }

         if (bufferBuilder.animatedSprites != null && !bufferBuilder.animatedSprites.isEmpty()) {
            sectioncompiler$results.setAnimatedSprites(rendertype1, (BitSet)bufferBuilder.animatedSprites.clone());
         }

         com.mojang.blaze3d.vertex.MeshData meshdata = bufferBuilder.m_339970_();
         if (meshdata != null) {
            if (rendertype1 == net.minecraft.client.renderer.RenderType.m_110466_()) {
               sectioncompiler$results.f_337382_ = meshdata.m_338666_(builderIn.m_339320_(net.minecraft.client.renderer.RenderType.m_110466_()), sortingIn);
            }

            sectioncompiler$results.f_336965_.put(rendertype1, meshdata);
         }
      }

      renderchunkregion.renderFinish();
      net.minecraft.client.renderer.block.ModelBlockRenderer.m_111077_();
      sectioncompiler$results.f_337613_ = visgraph.m_112958_();
      return sectioncompiler$results;
   }

   public com.mojang.blaze3d.vertex.BufferBuilder m_340252_(
      Map<net.minecraft.client.renderer.RenderType, com.mojang.blaze3d.vertex.BufferBuilder> mapBuffersIn,
      SectionBufferBuilderPack builderIn,
      net.minecraft.client.renderer.RenderType renderTypeIn
   ) {
      com.mojang.blaze3d.vertex.BufferBuilder bufferbuilder = (com.mojang.blaze3d.vertex.BufferBuilder)mapBuffersIn.get(renderTypeIn);
      if (bufferbuilder == null) {
         com.mojang.blaze3d.vertex.ByteBufferBuilder bytebufferbuilder = builderIn.m_339320_(renderTypeIn);
         bufferbuilder = new com.mojang.blaze3d.vertex.BufferBuilder(
            bytebufferbuilder, com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS, com.mojang.blaze3d.vertex.DefaultVertexFormat.f_85811_, renderTypeIn
         );
         mapBuffersIn.put(renderTypeIn, bufferbuilder);
      }

      return bufferbuilder;
   }

   private <E extends net.minecraft.world.level.block.entity.BlockEntity> void m_339434_(
      net.minecraft.client.renderer.chunk.SectionCompiler.Results resultIn, E blockEntityIn
   ) {
      net.minecraft.client.renderer.blockentity.BlockEntityRenderer<E> blockentityrenderer = this.f_337625_.m_112265_(blockEntityIn);
      if (blockentityrenderer != null) {
         if (blockentityrenderer.m_5932_(blockEntityIn)) {
            resultIn.f_337223_.add(blockEntityIn);
         } else {
            resultIn.f_337248_.add(blockEntityIn);
         }
      }
   }

   public static Iterable<net.minecraft.client.renderer.RenderType> getBlockRenderLayers(
      net.minecraft.client.resources.model.BakedModel model,
      net.minecraft.world.level.block.state.BlockState blockState,
      BlockPos blockPos,
      RandomSource randomsource,
      ModelData modelData,
      SingleIterable<net.minecraft.client.renderer.RenderType> singleLayer
   ) {
      if (FORGE) {
         randomsource.m_188584_(blockState.m_60726_(blockPos));
         return model.getRenderTypes(blockState, randomsource, modelData);
      } else {
         singleLayer.setValue(ItemBlockRenderTypes.m_109282_(blockState));
         return singleLayer;
      }
   }

   public static final class Results {
      public final List<net.minecraft.world.level.block.entity.BlockEntity> f_337223_ = new ArrayList();
      public final List<net.minecraft.world.level.block.entity.BlockEntity> f_337248_ = new ArrayList();
      public final Map<net.minecraft.client.renderer.RenderType, com.mojang.blaze3d.vertex.MeshData> f_336965_ = new Reference2ObjectArrayMap();
      public net.minecraft.client.renderer.chunk.VisibilitySet f_337613_ = new net.minecraft.client.renderer.chunk.VisibilitySet();
      @Nullable
      public com.mojang.blaze3d.vertex.MeshData.SortState f_337382_;
      public BitSet[] animatedSprites = new BitSet[net.minecraft.client.renderer.RenderType.CHUNK_RENDER_TYPES.length];

      public void setAnimatedSprites(net.minecraft.client.renderer.RenderType layer, BitSet animatedSprites) {
         this.animatedSprites[layer.ordinal()] = animatedSprites;
      }

      public void m_340536_() {
         this.f_336965_.values().forEach(com.mojang.blaze3d.vertex.MeshData::close);
      }
   }
}
