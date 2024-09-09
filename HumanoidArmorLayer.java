import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.src.C_1313_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_140_;
import net.minecraft.src.C_203228_;
import net.minecraft.src.C_265826_;
import net.minecraft.src.C_265827_;
import net.minecraft.src.C_313616_;
import net.minecraft.src.C_313801_;
import net.minecraft.src.C_3829_;
import net.minecraft.src.C_4177_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4535_;
import net.minecraft.src.C_516_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.util.TextureUtils;

public class HumanoidArmorLayer<T extends C_524_, M extends C_3829_<T>, A extends C_3829_<T>> extends RenderLayer<T, M> {
   private static final Map<String, ResourceLocation> a = Maps.newHashMap();
   private final A b;
   private final A c;
   private final TextureAtlas d;

   public HumanoidArmorLayer(C_4382_<T, M> rendererIn, A modelLeggingsIn, A modelArmorIn, C_4535_ modelManagerIn) {
      super(rendererIn);
      this.b = modelLeggingsIn;
      this.c = modelArmorIn;
      this.d = modelManagerIn.a(C_4177_.g);
   }

   public void a(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      this.a(matrixStackIn, bufferIn, entitylivingbaseIn, C_516_.CHEST, packedLightIn, this.a(C_516_.CHEST));
      this.a(matrixStackIn, bufferIn, entitylivingbaseIn, C_516_.LEGS, packedLightIn, this.a(C_516_.LEGS));
      this.a(matrixStackIn, bufferIn, entitylivingbaseIn, C_516_.FEET, packedLightIn, this.a(C_516_.FEET));
      this.a(matrixStackIn, bufferIn, entitylivingbaseIn, C_516_.HEAD, packedLightIn, this.a(C_516_.HEAD));
   }

   private void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, T livingEntityIn, C_516_ slotIn, int packedLightIn, A modelIn) {
      C_1391_ itemstack = livingEntityIn.m_6844_(slotIn);
      if (itemstack.m_41720_() instanceof C_1313_ armoritem && armoritem.m_40402_() == slotIn) {
         this.c().m_102872_(modelIn);
         this.a(modelIn, slotIn);
         boolean flag = this.b(slotIn);
         ArmorMaterial armormaterial = (ArmorMaterial)armoritem.m_40401_().m_203334_();
         int i = itemstack.m_204117_(C_140_.f_314020_) ? C_175_.m_321570_(C_313801_.m_322889_(itemstack, -6265536)) : -1;

         for (ArmorMaterial.a armormaterial$layer : armormaterial.e()) {
            int j = armormaterial$layer.a() ? i : -1;
            this.a(matrixStackIn, bufferIn, packedLightIn, modelIn, j, armormaterial$layer.a(flag));
         }

         C_265827_ armortrim = (C_265827_)itemstack.m_323252_(C_313616_.f_315199_);
         if (armortrim != null) {
            this.a(armoritem.m_40401_(), matrixStackIn, bufferIn, packedLightIn, armortrim, modelIn, flag);
         }

         if (itemstack.m_41790_()) {
            this.a(matrixStackIn, bufferIn, packedLightIn, modelIn);
         }
      }
   }

   protected void a(A modelIn, C_516_ slotIn) {
      modelIn.m_8009_(false);
      switch (slotIn) {
         case HEAD:
            modelIn.k.k = true;
            modelIn.l.k = true;
            break;
         case CHEST:
            modelIn.m.k = true;
            modelIn.n.k = true;
            modelIn.o.k = true;
            break;
         case LEGS:
            modelIn.m.k = true;
            modelIn.p.k = true;
            modelIn.q.k = true;
            break;
         case FEET:
            modelIn.p.k = true;
            modelIn.q.k = true;
      }
   }

   private void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, A bipedModelIn, int colorIn, ResourceLocation suffixIn) {
      VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.a(suffixIn));
      bipedModelIn.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_, colorIn);
   }

   private void a(
      C_203228_<ArmorMaterial> armorMaterialIn,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      C_265827_ trimIn,
      A bipedModelIn,
      boolean isLegSlot
   ) {
      TextureAtlasSprite textureatlassprite = this.d.a(isLegSlot ? trimIn.a(armorMaterialIn) : trimIn.b(armorMaterialIn));
      textureatlassprite = TextureUtils.getCustomSprite(textureatlassprite);
      VertexConsumer vertexconsumer = textureatlassprite.a(bufferIn.getBuffer(C_4177_.a(((C_265826_)trimIn.m_266429_().m_203334_()).f_290976_())));
      bipedModelIn.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
   }

   private void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, A bipedModelIn) {
      bipedModelIn.a(matrixStackIn, bufferIn.getBuffer(RenderType.j()), packedLightIn, C_4474_.f_118083_);
   }

   private A a(C_516_ slotIn) {
      return this.b(slotIn) ? this.b : this.c;
   }

   private boolean b(C_516_ slotIn) {
      return slotIn == C_516_.LEGS;
   }
}
