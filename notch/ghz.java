package net.minecraft.src;

import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraftforge.client.model.data.ModelData;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.override.ChunkCacheOF;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.Shaders;
import net.optifine.util.SingleIterable;

public class C_336511_ {
   private final C_4183_ f_337337_;
   private final C_4243_ f_337625_;
   protected C_290152_ sectionRenderDispatcher;
   public static final boolean FORGE = C_290152_.FORGE;

   public C_336511_(C_4183_ p_i340324_1_, C_4243_ p_i340324_2_) {
      this.f_337337_ = p_i340324_1_;
      this.f_337625_ = p_i340324_2_;
   }

   public C_336511_.C_336499_ m_340354_(C_4710_ sectionPosIn, C_4269_ regionIn, C_276405_ sortingIn, C_290184_ builderIn) {
      return this.compile(sectionPosIn, regionIn, sortingIn, builderIn, 0, 0, 0);
   }

   public C_336511_.C_336499_ compile(
      C_4710_ sectionPosIn, C_4269_ regionIn, C_276405_ sortingIn, C_290184_ builderIn, int regionDX, int regionDY, int regionDZ
   ) {
      Map<C_4675_, ModelData> modelDataMap = FORGE ? C_3391_.m_91087_().f_91073_.getModelDataManager().getAt(sectionPosIn) : null;
      C_336511_.C_336499_ sectioncompiler$results = new C_336511_.C_336499_();
      C_4675_ blockpos = sectionPosIn.m_123249_();
      C_4675_ blockpos1 = blockpos.m_7918_(15, 15, 15);
      C_4270_ visgraph = new C_4270_();
      C_3181_ posestack = new C_3181_();
      C_290152_.renderChunksUpdated++;
      ChunkCacheOF renderchunkregion = regionIn.makeChunkCacheOF();
      renderchunkregion.renderStart();
      SingleIterable<C_4168_> singleLayer = new SingleIterable<>();
      boolean shaders = Config.isShaders();
      boolean shadersMidBlock = shaders && Shaders.useMidBlockAttrib;
      C_4186_.m_111000_();
      Map<C_4168_, C_3173_> map = new Reference2ObjectArrayMap(C_4168_.m_110506_().size());
      C_212974_ randomsource = C_212974_.m_216327_();
      C_4183_ blockrenderdispatcher = C_3391_.m_91087_().m_91289_();

      for (BlockPosM blockpos2 : BlockPosM.getAllInBoxMutableM(blockpos, blockpos1)) {
         C_2064_ blockstate = regionIn.m_8055_(blockpos2);
         if (!blockstate.m_60795_()) {
            if (blockstate.m_60804_(regionIn, blockpos2)) {
               visgraph.m_112971_(blockpos2);
            }

            if (blockstate.m_155947_()) {
               C_1991_ blockentity = regionIn.m_7702_(blockpos2);
               if (blockentity != null) {
                  this.m_339434_(sectioncompiler$results, blockentity);
               }
            }

            C_2691_ fluidstate = blockstate.m_60819_();
            if (!fluidstate.m_76178_()) {
               C_4168_ rendertype = C_4130_.m_109287_(fluidstate);
               C_3173_ bufferbuilder = this.m_340252_(map, builderIn, rendertype);
               RenderEnv renderEnv = bufferbuilder.getRenderEnv(blockstate, blockpos2);
               renderEnv.setCompileParams(this, map, builderIn);
               renderchunkregion.setRenderEnv(renderEnv);
               this.f_337337_.m_234363_(blockpos2, regionIn, bufferbuilder, blockstate, fluidstate);
            }

            if (blockstate.m_60799_() == C_1879_.MODEL) {
               C_4528_ model = blockrenderdispatcher.m_110910_(blockstate);
               ModelData modelData = FORGE
                  ? model.getModelData(renderchunkregion, blockpos2, blockstate, (ModelData)modelDataMap.getOrDefault(blockpos2, ModelData.EMPTY))
                  : null;

               for (C_4168_ renderTypeForge : getBlockRenderLayers(model, blockstate, blockpos2, randomsource, modelData, singleLayer)) {
                  C_4168_ rendertype2 = C_4130_.m_109282_(blockstate);
                  C_3173_ bufferbuilder1 = this.m_340252_(map, builderIn, rendertype2);
                  RenderEnv renderEnv = bufferbuilder1.getRenderEnv(blockstate, blockpos2);
                  renderEnv.setCompileParams(this, map, builderIn);
                  renderchunkregion.setRenderEnv(renderEnv);
                  posestack.m_85836_();
                  posestack.m_252880_(
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

                  this.f_337337_.renderBatched(blockstate, blockpos2, regionIn, posestack, bufferbuilder1, true, randomsource, modelData, renderTypeForge);
                  posestack.m_85849_();
               }
            }
         }
      }

      for (C_4168_ blockrenderlayer : C_290152_.BLOCK_RENDER_LAYERS) {
         sectioncompiler$results.setAnimatedSprites(blockrenderlayer, null);
      }

      for (Entry<C_4168_, C_3173_> entry : map.entrySet()) {
         C_4168_ rendertype1 = (C_4168_)entry.getKey();
         C_3173_ bufferBuilder = (C_3173_)entry.getValue();
         if (Config.isShaders()) {
            SVertexBuilder.calcNormalChunkLayer(bufferBuilder);
         }

         if (bufferBuilder.animatedSprites != null && !bufferBuilder.animatedSprites.isEmpty()) {
            sectioncompiler$results.setAnimatedSprites(rendertype1, (BitSet)bufferBuilder.animatedSprites.clone());
         }

         C_336471_ meshdata = bufferBuilder.m_339970_();
         if (meshdata != null) {
            if (rendertype1 == C_4168_.m_110466_()) {
               sectioncompiler$results.f_337382_ = meshdata.m_338666_(builderIn.m_339320_(C_4168_.m_110466_()), sortingIn);
            }

            sectioncompiler$results.f_336965_.put(rendertype1, meshdata);
         }
      }

      renderchunkregion.renderFinish();
      C_4186_.m_111077_();
      sectioncompiler$results.f_337613_ = visgraph.m_112958_();
      return sectioncompiler$results;
   }

   public C_3173_ m_340252_(Map<C_4168_, C_3173_> mapBuffersIn, C_290184_ builderIn, C_4168_ renderTypeIn) {
      C_3173_ bufferbuilder = (C_3173_)mapBuffersIn.get(renderTypeIn);
      if (bufferbuilder == null) {
         C_336589_ bytebufferbuilder = builderIn.m_339320_(renderTypeIn);
         bufferbuilder = new C_3173_(bytebufferbuilder, C_3188_.C_141549_.QUADS, C_3179_.f_85811_, renderTypeIn);
         mapBuffersIn.put(renderTypeIn, bufferbuilder);
      }

      return bufferbuilder;
   }

   private <E extends C_1991_> void m_339434_(C_336511_.C_336499_ resultIn, E blockEntityIn) {
      C_4244_<E> blockentityrenderer = this.f_337625_.m_112265_(blockEntityIn);
      if (blockentityrenderer != null) {
         if (blockentityrenderer.m_5932_(blockEntityIn)) {
            resultIn.f_337223_.add(blockEntityIn);
         } else {
            resultIn.f_337248_.add(blockEntityIn);
         }
      }
   }

   public static Iterable<C_4168_> getBlockRenderLayers(
      C_4528_ model, C_2064_ blockState, C_4675_ blockPos, C_212974_ randomsource, ModelData modelData, SingleIterable<C_4168_> singleLayer
   ) {
      if (FORGE) {
         randomsource.m_188584_(blockState.m_60726_(blockPos));
         return model.getRenderTypes(blockState, randomsource, modelData);
      } else {
         singleLayer.setValue(C_4130_.m_109282_(blockState));
         return singleLayer;
      }
   }

   public static final class C_336499_ {
      public final List<C_1991_> f_337223_ = new ArrayList();
      public final List<C_1991_> f_337248_ = new ArrayList();
      public final Map<C_4168_, C_336471_> f_336965_ = new Reference2ObjectArrayMap();
      public C_4272_ f_337613_ = new C_4272_();
      @Nullable
      public C_336471_.C_336506_ f_337382_;
      public BitSet[] animatedSprites = new BitSet[C_4168_.CHUNK_RENDER_TYPES.length];

      public void setAnimatedSprites(C_4168_ layer, BitSet animatedSprites) {
         this.animatedSprites[layer.ordinal()] = animatedSprites;
      }

      public void m_340536_() {
         this.f_336965_.values().forEach(C_336471_::close);
      }
   }
}
