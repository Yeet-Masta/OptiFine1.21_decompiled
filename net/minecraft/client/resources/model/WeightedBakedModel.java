package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class WeightedBakedModel implements BakedModel, IDynamicBakedModel {
   private final int f_119540_;
   private final List f_119541_;
   private final BakedModel f_119542_;

   public WeightedBakedModel(List modelsIn) {
      this.f_119541_ = modelsIn;
      this.f_119540_ = WeightedRandom.m_146312_(modelsIn);
      this.f_119542_ = (BakedModel)((WeightedEntry.Wrapper)modelsIn.get(0)).f_146299_();
   }

   public List m_213637_(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
      WeightedEntry.Wrapper wbm = (WeightedEntry.Wrapper)getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
      return wbm == null ? Collections.emptyList() : ((BakedModel)wbm.f_146299_()).m_213637_(state, side, rand);
   }

   public static WeightedEntry getWeightedItem(List items, int targetWeight) {
      for(int i = 0; i < items.size(); ++i) {
         WeightedEntry t = (WeightedEntry)items.get(i);
         targetWeight -= t.m_142631_().m_146281_();
         if (targetWeight < 0) {
            return t;
         }
      }

      return null;
   }

   public List getQuads(BlockState state, Direction side, RandomSource rand, ModelData modelData, RenderType renderType) {
      WeightedEntry.Wrapper wbm = (WeightedEntry.Wrapper)getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
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
      WeightedEntry.Wrapper wbm = (WeightedEntry.Wrapper)getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
      return wbm == null ? ChunkRenderTypeSet.none() : ((BakedModel)wbm.f_146299_()).getRenderTypes(state, rand, data);
   }

   public boolean m_7541_() {
      return this.f_119542_.m_7541_();
   }

   public boolean m_7539_() {
      return this.f_119542_.m_7539_();
   }

   public boolean m_7547_() {
      return this.f_119542_.m_7547_();
   }

   public boolean m_7521_() {
      return this.f_119542_.m_7521_();
   }

   public TextureAtlasSprite m_6160_() {
      return this.f_119542_.m_6160_();
   }

   public ItemTransforms m_7442_() {
      return this.f_119542_.m_7442_();
   }

   public ItemOverrides m_7343_() {
      return this.f_119542_.m_7343_();
   }

   public static class Builder {
      private final List f_119556_ = Lists.newArrayList();

      public Builder m_119559_(@Nullable BakedModel model, int weight) {
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
            return (BakedModel)(this.f_119556_.size() == 1 ? (BakedModel)((WeightedEntry.Wrapper)this.f_119556_.get(0)).f_146299_() : new WeightedBakedModel(this.f_119556_));
         }
      }
   }
}
