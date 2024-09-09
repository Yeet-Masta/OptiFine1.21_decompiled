package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class WeightedBakedModel implements net.minecraft.client.resources.model.BakedModel, IDynamicBakedModel {
   private final int f_119540_;
   private final List<Wrapper<net.minecraft.client.resources.model.BakedModel>> f_119541_;
   private final net.minecraft.client.resources.model.BakedModel f_119542_;

   public WeightedBakedModel(List<Wrapper<net.minecraft.client.resources.model.BakedModel>> modelsIn) {
      this.f_119541_ = modelsIn;
      this.f_119540_ = WeightedRandom.m_146312_(modelsIn);
      this.f_119542_ = (net.minecraft.client.resources.model.BakedModel)((Wrapper)modelsIn.get(0)).f_146299_();
   }

   @Override
   public List<net.minecraft.client.renderer.block.model.BakedQuad> m_213637_(
      @Nullable net.minecraft.world.level.block.state.BlockState state, @Nullable net.minecraft.core.Direction side, RandomSource rand
   ) {
      Wrapper<net.minecraft.client.resources.model.BakedModel> wbm = getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
      return wbm == null ? Collections.emptyList() : ((net.minecraft.client.resources.model.BakedModel)wbm.f_146299_()).m_213637_(state, side, rand);
   }

   public static <T extends WeightedEntry> T getWeightedItem(List<T> items, int targetWeight) {
      for (int i = 0; i < items.size(); i++) {
         T t = (T)items.get(i);
         targetWeight -= t.m_142631_().m_146281_();
         if (targetWeight < 0) {
            return t;
         }
      }

      return null;
   }

   public List<net.minecraft.client.renderer.block.model.BakedQuad> getQuads(
      net.minecraft.world.level.block.state.BlockState state,
      net.minecraft.core.Direction side,
      RandomSource rand,
      ModelData modelData,
      net.minecraft.client.renderer.RenderType renderType
   ) {
      Wrapper<net.minecraft.client.resources.model.BakedModel> wbm = getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
      return wbm == null
         ? Collections.emptyList()
         : ((net.minecraft.client.resources.model.BakedModel)wbm.f_146299_()).getQuads(state, side, rand, modelData, renderType);
   }

   public boolean useAmbientOcclusion(net.minecraft.world.level.block.state.BlockState state) {
      return this.f_119542_.useAmbientOcclusion(state);
   }

   public boolean useAmbientOcclusion(net.minecraft.world.level.block.state.BlockState state, net.minecraft.client.renderer.RenderType renderType) {
      return this.f_119542_.useAmbientOcclusion(state, renderType);
   }

   public net.minecraft.client.renderer.texture.TextureAtlasSprite getParticleIcon(ModelData modelData) {
      return this.f_119542_.getParticleIcon(modelData);
   }

   public net.minecraft.client.resources.model.BakedModel applyTransform(
      ItemDisplayContext transformType, com.mojang.blaze3d.vertex.PoseStack poseStack, boolean applyLeftHandTransform
   ) {
      return this.f_119542_.applyTransform(transformType, poseStack, applyLeftHandTransform);
   }

   public ChunkRenderTypeSet getRenderTypes(
      @NotNull net.minecraft.world.level.block.state.BlockState state, @NotNull RandomSource rand, @NotNull ModelData data
   ) {
      Wrapper<net.minecraft.client.resources.model.BakedModel> wbm = getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
      return wbm == null ? ChunkRenderTypeSet.none() : ((net.minecraft.client.resources.model.BakedModel)wbm.f_146299_()).getRenderTypes(state, rand, data);
   }

   @Override
   public boolean m_7541_() {
      return this.f_119542_.m_7541_();
   }

   @Override
   public boolean m_7539_() {
      return this.f_119542_.m_7539_();
   }

   @Override
   public boolean m_7547_() {
      return this.f_119542_.m_7547_();
   }

   @Override
   public boolean m_7521_() {
      return this.f_119542_.m_7521_();
   }

   @Override
   public net.minecraft.client.renderer.texture.TextureAtlasSprite m_6160_() {
      return this.f_119542_.m_6160_();
   }

   @Override
   public ItemTransforms m_7442_() {
      return this.f_119542_.m_7442_();
   }

   @Override
   public net.minecraft.client.renderer.block.model.ItemOverrides m_7343_() {
      return this.f_119542_.m_7343_();
   }

   public static class Builder {
      private final List<Wrapper<net.minecraft.client.resources.model.BakedModel>> f_119556_ = Lists.newArrayList();

      public net.minecraft.client.resources.model.WeightedBakedModel.Builder m_119559_(
         @Nullable net.minecraft.client.resources.model.BakedModel model, int weight
      ) {
         if (model != null) {
            this.f_119556_.add(WeightedEntry.m_146290_(model, weight));
         }

         return this;
      }

      @Nullable
      public net.minecraft.client.resources.model.BakedModel m_119558_() {
         if (this.f_119556_.isEmpty()) {
            return null;
         } else {
            return (net.minecraft.client.resources.model.BakedModel)(this.f_119556_.size() == 1
               ? (net.minecraft.client.resources.model.BakedModel)((Wrapper)this.f_119556_.get(0)).f_146299_()
               : new net.minecraft.client.resources.model.WeightedBakedModel(this.f_119556_));
         }
      }
   }
}
