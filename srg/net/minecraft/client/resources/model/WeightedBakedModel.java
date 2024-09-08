package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class WeightedBakedModel implements BakedModel, IDynamicBakedModel {
   private final int f_119540_;
   private final List<Wrapper<BakedModel>> f_119541_;
   private final BakedModel f_119542_;

   public WeightedBakedModel(List<Wrapper<BakedModel>> modelsIn) {
      this.f_119541_ = modelsIn;
      this.f_119540_ = WeightedRandom.m_146312_(modelsIn);
      this.f_119542_ = (BakedModel)((Wrapper)modelsIn.get(0)).f_146299_();
   }

   @Override
   public List<BakedQuad> m_213637_(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
      Wrapper<BakedModel> wbm = getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
      return wbm == null ? Collections.emptyList() : ((BakedModel)wbm.f_146299_()).m_213637_(state, side, rand);
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

   public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData modelData, RenderType renderType) {
      Wrapper<BakedModel> wbm = getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
      return wbm == null ? Collections.emptyList() : ((BakedModel)wbm.f_146299_()).getQuads(state, side, rand, modelData, renderType);
   }

   public boolean useAmbientOcclusion(BlockState state) {
      return this.f_119542_.useAmbientOcclusion(state);
   }

   public boolean useAmbientOcclusion(BlockState state, RenderType renderType) {
      return this.f_119542_.useAmbientOcclusion(state, renderType);
   }

   public TextureAtlasSprite getParticleIcon(ModelData modelData) {
      return this.f_119542_.getParticleIcon(modelData);
   }

   public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
      return this.f_119542_.applyTransform(transformType, poseStack, applyLeftHandTransform);
   }

   public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
      Wrapper<BakedModel> wbm = getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
      return wbm == null ? ChunkRenderTypeSet.none() : ((BakedModel)wbm.f_146299_()).getRenderTypes(state, rand, data);
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
   public TextureAtlasSprite m_6160_() {
      return this.f_119542_.m_6160_();
   }

   @Override
   public ItemTransforms m_7442_() {
      return this.f_119542_.m_7442_();
   }

   @Override
   public ItemOverrides m_7343_() {
      return this.f_119542_.m_7343_();
   }

   public static class Builder {
      private final List<Wrapper<BakedModel>> f_119556_ = Lists.newArrayList();

      public WeightedBakedModel.Builder m_119559_(@Nullable BakedModel model, int weight) {
         if (model != null) {
            this.f_119556_.add(WeightedEntry.m_146290_(model, weight));
         }

         return this;
      }

      @Nullable
      public BakedModel m_119558_() {
         if (this.f_119556_.isEmpty()) {
            return null;
         } else {
            return (BakedModel)(this.f_119556_.size() == 1 ? (BakedModel)((Wrapper)this.f_119556_.get(0)).f_146299_() : new WeightedBakedModel(this.f_119556_));
         }
      }
   }
}
