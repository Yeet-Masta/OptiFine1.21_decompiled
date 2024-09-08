package net.minecraft.src;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.src.C_141037_.C_141039_;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class C_4543_ implements C_4528_, IDynamicBakedModel {
   private final int f_119540_;
   private final List<C_141039_<C_4528_>> f_119541_;
   private final C_4528_ f_119542_;

   public C_4543_(List<C_141039_<C_4528_>> modelsIn) {
      this.f_119541_ = modelsIn;
      this.f_119540_ = C_141040_.m_146312_(modelsIn);
      this.f_119542_ = (C_4528_)((C_141039_)modelsIn.get(0)).f_146299_();
   }

   @Override
   public List<C_4196_> m_213637_(@Nullable C_2064_ state, @Nullable C_4687_ side, C_212974_ rand) {
      C_141039_<C_4528_> wbm = getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
      return wbm == null ? Collections.emptyList() : ((C_4528_)wbm.f_146299_()).m_213637_(state, side, rand);
   }

   public static <T extends C_141037_> T getWeightedItem(List<T> items, int targetWeight) {
      for (int i = 0; i < items.size(); i++) {
         T t = (T)items.get(i);
         targetWeight -= t.m_142631_().m_146281_();
         if (targetWeight < 0) {
            return t;
         }
      }

      return null;
   }

   @Override
   public List<C_4196_> getQuads(C_2064_ state, C_4687_ side, C_212974_ rand, ModelData modelData, C_4168_ renderType) {
      C_141039_<C_4528_> wbm = getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
      return wbm == null ? Collections.emptyList() : ((C_4528_)wbm.f_146299_()).getQuads(state, side, rand, modelData, renderType);
   }

   @Override
   public boolean useAmbientOcclusion(C_2064_ state) {
      return this.f_119542_.useAmbientOcclusion(state);
   }

   @Override
   public boolean useAmbientOcclusion(C_2064_ state, C_4168_ renderType) {
      return this.f_119542_.useAmbientOcclusion(state, renderType);
   }

   @Override
   public C_4486_ getParticleIcon(ModelData modelData) {
      return this.f_119542_.getParticleIcon(modelData);
   }

   @Override
   public C_4528_ applyTransform(C_268388_ transformType, C_3181_ poseStack, boolean applyLeftHandTransform) {
      return this.f_119542_.applyTransform(transformType, poseStack, applyLeftHandTransform);
   }

   @Override
   public ChunkRenderTypeSet getRenderTypes(@NotNull C_2064_ state, @NotNull C_212974_ rand, @NotNull ModelData data) {
      C_141039_<C_4528_> wbm = getWeightedItem(this.f_119541_, Math.abs((int)rand.m_188505_()) % this.f_119540_);
      return wbm == null ? ChunkRenderTypeSet.none() : ((C_4528_)wbm.f_146299_()).getRenderTypes(state, rand, data);
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
   public C_4486_ m_6160_() {
      return this.f_119542_.m_6160_();
   }

   @Override
   public C_4222_ m_7442_() {
      return this.f_119542_.m_7442_();
   }

   @Override
   public C_4219_ m_7343_() {
      return this.f_119542_.m_7343_();
   }

   public static class C_4544_ {
      private final List<C_141039_<C_4528_>> f_119556_ = Lists.newArrayList();

      public C_4543_.C_4544_ m_119559_(@Nullable C_4528_ model, int weight) {
         if (model != null) {
            this.f_119556_.add(C_141037_.m_146290_(model, weight));
         }

         return this;
      }

      @Nullable
      public C_4528_ m_119558_() {
         if (this.f_119556_.isEmpty()) {
            return null;
         } else {
            return (C_4528_)(this.f_119556_.size() == 1 ? (C_4528_)((C_141039_)this.f_119556_.get(0)).f_146299_() : new C_4543_(this.f_119556_));
         }
      }
   }
}
