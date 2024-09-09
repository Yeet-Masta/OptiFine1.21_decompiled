import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.src.C_141037_;
import net.minecraft.src.C_141040_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_268388_;
import net.minecraft.src.C_4222_;
import net.minecraft.src.C_141037_.C_141039_;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class WeightedBakedModel implements BakedModel, IDynamicBakedModel {
   private final int a;
   private final List<C_141039_<BakedModel>> b;
   private final BakedModel c;

   public WeightedBakedModel(List<C_141039_<BakedModel>> modelsIn) {
      this.b = modelsIn;
      this.a = C_141040_.m_146312_(modelsIn);
      this.c = (BakedModel)((C_141039_)modelsIn.get(0)).f_146299_();
   }

   @Override
   public List<BakedQuad> a(@Nullable BlockState state, @Nullable Direction side, C_212974_ rand) {
      C_141039_<BakedModel> wbm = getWeightedItem(this.b, Math.abs((int)rand.m_188505_()) % this.a);
      return wbm == null ? Collections.emptyList() : ((BakedModel)wbm.f_146299_()).a(state, side, rand);
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
   public List<BakedQuad> getQuads(BlockState state, Direction side, C_212974_ rand, ModelData modelData, RenderType renderType) {
      C_141039_<BakedModel> wbm = getWeightedItem(this.b, Math.abs((int)rand.m_188505_()) % this.a);
      return wbm == null ? Collections.emptyList() : ((BakedModel)wbm.f_146299_()).getQuads(state, side, rand, modelData, renderType);
   }

   @Override
   public boolean useAmbientOcclusion(BlockState state) {
      return this.c.useAmbientOcclusion(state);
   }

   @Override
   public boolean useAmbientOcclusion(BlockState state, RenderType renderType) {
      return this.c.useAmbientOcclusion(state, renderType);
   }

   @Override
   public TextureAtlasSprite getParticleIcon(ModelData modelData) {
      return this.c.getParticleIcon(modelData);
   }

   @Override
   public BakedModel applyTransform(C_268388_ transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
      return this.c.applyTransform(transformType, poseStack, applyLeftHandTransform);
   }

   @Override
   public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull C_212974_ rand, @NotNull ModelData data) {
      C_141039_<BakedModel> wbm = getWeightedItem(this.b, Math.abs((int)rand.m_188505_()) % this.a);
      return wbm == null ? ChunkRenderTypeSet.none() : ((BakedModel)wbm.f_146299_()).getRenderTypes(state, rand, data);
   }

   @Override
   public boolean a() {
      return this.c.a();
   }

   @Override
   public boolean b() {
      return this.c.b();
   }

   @Override
   public boolean c() {
      return this.c.c();
   }

   @Override
   public boolean d() {
      return this.c.d();
   }

   @Override
   public TextureAtlasSprite e() {
      return this.c.e();
   }

   @Override
   public C_4222_ f() {
      return this.c.f();
   }

   @Override
   public ItemOverrides g() {
      return this.c.g();
   }

   public static class a {
      private final List<C_141039_<BakedModel>> a = Lists.newArrayList();

      public WeightedBakedModel.a a(@Nullable BakedModel model, int weight) {
         if (model != null) {
            this.a.add(C_141037_.m_146290_(model, weight));
         }

         return this;
      }

      @Nullable
      public BakedModel a() {
         if (this.a.isEmpty()) {
            return null;
         } else {
            return (BakedModel)(this.a.size() == 1 ? (BakedModel)((C_141039_)this.a.get(0)).f_146299_() : new WeightedBakedModel(this.a));
         }
      }
   }
}
