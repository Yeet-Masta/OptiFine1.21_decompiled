import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.src.C_1879_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_2691_;
import net.minecraft.src.C_276405_;
import net.minecraft.src.C_290184_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4130_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4710_;
import net.minecraftforge.client.model.data.ModelData;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.override.ChunkCacheOF;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.Shaders;
import net.optifine.util.SingleIterable;

public class SectionCompiler {
   private final BlockRenderDispatcher a;
   private final BlockEntityRenderDispatcher b;
   protected SectionRenderDispatcher sectionRenderDispatcher;
   public static final boolean FORGE = SectionRenderDispatcher.FORGE;

   public SectionCompiler(BlockRenderDispatcher p_i340324_1_, BlockEntityRenderDispatcher p_i340324_2_) {
      this.a = p_i340324_1_;
      this.b = p_i340324_2_;
   }

   public SectionCompiler.a a(C_4710_ sectionPosIn, RenderChunkRegion regionIn, C_276405_ sortingIn, C_290184_ builderIn) {
      return this.compile(sectionPosIn, regionIn, sortingIn, builderIn, 0, 0, 0);
   }

   public SectionCompiler.a compile(
      C_4710_ sectionPosIn, RenderChunkRegion regionIn, C_276405_ sortingIn, C_290184_ builderIn, int regionDX, int regionDY, int regionDZ
   ) {
      Map<C_4675_, ModelData> modelDataMap = FORGE ? C_3391_.m_91087_().r.getModelDataManager().getAt(sectionPosIn) : null;
      SectionCompiler.a sectioncompiler$results = new SectionCompiler.a();
      C_4675_ blockpos = sectionPosIn.m_123249_();
      C_4675_ blockpos1 = blockpos.m_7918_(15, 15, 15);
      VisGraph visgraph = new VisGraph();
      PoseStack posestack = new PoseStack();
      SectionRenderDispatcher.renderChunksUpdated++;
      ChunkCacheOF renderchunkregion = regionIn.makeChunkCacheOF();
      renderchunkregion.renderStart();
      SingleIterable<RenderType> singleLayer = new SingleIterable<>();
      boolean shaders = Config.isShaders();
      boolean shadersMidBlock = shaders && Shaders.useMidBlockAttrib;
      ModelBlockRenderer.a();
      Map<RenderType, BufferBuilder> map = new Reference2ObjectArrayMap(RenderType.I().size());
      C_212974_ randomsource = C_212974_.m_216327_();
      BlockRenderDispatcher blockrenderdispatcher = C_3391_.m_91087_().ao();

      for (BlockPosM blockpos2 : BlockPosM.getAllInBoxMutableM(blockpos, blockpos1)) {
         BlockState blockstate = regionIn.a_(blockpos2);
         if (!blockstate.m_60795_()) {
            if (blockstate.m_60804_(regionIn, blockpos2)) {
               visgraph.a(blockpos2);
            }

            if (blockstate.m_155947_()) {
               BlockEntity blockentity = regionIn.c_(blockpos2);
               if (blockentity != null) {
                  this.a(sectioncompiler$results, blockentity);
               }
            }

            C_2691_ fluidstate = blockstate.m_60819_();
            if (!fluidstate.m_76178_()) {
               RenderType rendertype = C_4130_.a(fluidstate);
               BufferBuilder bufferbuilder = this.a(map, builderIn, rendertype);
               RenderEnv renderEnv = bufferbuilder.getRenderEnv(blockstate, blockpos2);
               renderEnv.setCompileParams(this, map, builderIn);
               renderchunkregion.setRenderEnv(renderEnv);
               this.a.a(blockpos2, regionIn, bufferbuilder, blockstate, fluidstate);
            }

            if (blockstate.m_60799_() == C_1879_.MODEL) {
               BakedModel model = blockrenderdispatcher.a(blockstate);
               ModelData modelData = FORGE
                  ? model.getModelData(renderchunkregion, blockpos2, blockstate, (ModelData)modelDataMap.getOrDefault(blockpos2, ModelData.EMPTY))
                  : null;

               for (RenderType renderTypeForge : getBlockRenderLayers(model, blockstate, blockpos2, randomsource, modelData, singleLayer)) {
                  RenderType rendertype2 = C_4130_.a(blockstate);
                  BufferBuilder bufferbuilder1 = this.a(map, builderIn, rendertype2);
                  RenderEnv renderEnv = bufferbuilder1.getRenderEnv(blockstate, blockpos2);
                  renderEnv.setCompileParams(this, map, builderIn);
                  renderchunkregion.setRenderEnv(renderEnv);
                  posestack.a();
                  posestack.a(
                     (float)regionDX + (float)C_4710_.m_123207_(blockpos2.u()),
                     (float)regionDY + (float)C_4710_.m_123207_(blockpos2.v()),
                     (float)regionDZ + (float)C_4710_.m_123207_(blockpos2.w())
                  );
                  if (shadersMidBlock) {
                     bufferbuilder1.setMidBlock(
                        0.5F + (float)regionDX + (float)C_4710_.m_123207_(blockpos2.u()),
                        0.5F + (float)regionDY + (float)C_4710_.m_123207_(blockpos2.v()),
                        0.5F + (float)regionDZ + (float)C_4710_.m_123207_(blockpos2.w())
                     );
                  }

                  this.a.renderBatched(blockstate, blockpos2, regionIn, posestack, bufferbuilder1, true, randomsource, modelData, renderTypeForge);
                  posestack.b();
               }
            }
         }
      }

      for (RenderType blockrenderlayer : SectionRenderDispatcher.BLOCK_RENDER_LAYERS) {
         sectioncompiler$results.setAnimatedSprites(blockrenderlayer, null);
      }

      for (Entry<RenderType, BufferBuilder> entry : map.entrySet()) {
         RenderType rendertype1 = (RenderType)entry.getKey();
         BufferBuilder bufferBuilder = (BufferBuilder)entry.getValue();
         if (Config.isShaders()) {
            SVertexBuilder.calcNormalChunkLayer(bufferBuilder);
         }

         if (bufferBuilder.animatedSprites != null && !bufferBuilder.animatedSprites.isEmpty()) {
            sectioncompiler$results.setAnimatedSprites(rendertype1, (BitSet)bufferBuilder.animatedSprites.clone());
         }

         MeshData meshdata = bufferBuilder.a();
         if (meshdata != null) {
            if (rendertype1 == RenderType.f()) {
               sectioncompiler$results.e = meshdata.a(builderIn.a(RenderType.f()), sortingIn);
            }

            sectioncompiler$results.c.put(rendertype1, meshdata);
         }
      }

      renderchunkregion.renderFinish();
      ModelBlockRenderer.b();
      sectioncompiler$results.d = visgraph.a();
      return sectioncompiler$results;
   }

   public BufferBuilder a(Map<RenderType, BufferBuilder> mapBuffersIn, C_290184_ builderIn, RenderType renderTypeIn) {
      BufferBuilder bufferbuilder = (BufferBuilder)mapBuffersIn.get(renderTypeIn);
      if (bufferbuilder == null) {
         ByteBufferBuilder bytebufferbuilder = builderIn.a(renderTypeIn);
         bufferbuilder = new BufferBuilder(bytebufferbuilder, VertexFormat.c.h, DefaultVertexFormat.b, renderTypeIn);
         mapBuffersIn.put(renderTypeIn, bufferbuilder);
      }

      return bufferbuilder;
   }

   private <E extends BlockEntity> void a(SectionCompiler.a resultIn, E blockEntityIn) {
      BlockEntityRenderer<E> blockentityrenderer = this.b.a(blockEntityIn);
      if (blockentityrenderer != null) {
         if (blockentityrenderer.a(blockEntityIn)) {
            resultIn.a.add(blockEntityIn);
         } else {
            resultIn.b.add(blockEntityIn);
         }
      }
   }

   public static Iterable<RenderType> getBlockRenderLayers(
      BakedModel model, BlockState blockState, C_4675_ blockPos, C_212974_ randomsource, ModelData modelData, SingleIterable<RenderType> singleLayer
   ) {
      if (FORGE) {
         randomsource.m_188584_(blockState.m_60726_(blockPos));
         return model.getRenderTypes(blockState, randomsource, modelData);
      } else {
         singleLayer.setValue(C_4130_.a(blockState));
         return singleLayer;
      }
   }

   public static final class a {
      public final List<BlockEntity> a = new ArrayList();
      public final List<BlockEntity> b = new ArrayList();
      public final Map<RenderType, MeshData> c = new Reference2ObjectArrayMap();
      public VisibilitySet d = new VisibilitySet();
      @Nullable
      public MeshData.b e;
      public BitSet[] animatedSprites = new BitSet[RenderType.CHUNK_RENDER_TYPES.length];

      public void setAnimatedSprites(RenderType layer, BitSet animatedSprites) {
         this.animatedSprites[layer.ordinal()] = animatedSprites;
      }

      public void a() {
         this.c.values().forEach(MeshData::close);
      }
   }
}
