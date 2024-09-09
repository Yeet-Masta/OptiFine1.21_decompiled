package net.minecraft.src;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.util.TextureUtils;

public class C_4437_ extends C_4447_ {
   private static final Map f_117070_ = Maps.newHashMap();
   private final C_3829_ f_117071_;
   private final C_3829_ f_117072_;
   private final C_4484_ f_266073_;

   public C_4437_(C_4382_ rendererIn, C_3829_ modelLeggingsIn, C_3829_ modelArmorIn, C_4535_ modelManagerIn) {
      super(rendererIn);
      this.f_117071_ = modelLeggingsIn;
      this.f_117072_ = modelArmorIn;
      this.f_266073_ = modelManagerIn.m_119428_(C_4177_.f_265912_);
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_524_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, C_516_.CHEST, packedLightIn, this.m_117078_(C_516_.CHEST));
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, C_516_.LEGS, packedLightIn, this.m_117078_(C_516_.LEGS));
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, C_516_.FEET, packedLightIn, this.m_117078_(C_516_.FEET));
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, C_516_.HEAD, packedLightIn, this.m_117078_(C_516_.HEAD));
   }

   private void m_117118_(C_3181_ matrixStackIn, C_4139_ bufferIn, C_524_ livingEntityIn, C_516_ slotIn, int packedLightIn, C_3829_ modelIn) {
      C_1391_ itemstack = livingEntityIn.m_6844_(slotIn);
      C_1381_ var9 = itemstack.m_41720_();
      if (var9 instanceof C_1313_ armoritem) {
         if (armoritem.m_40402_() == slotIn) {
            ((C_3829_)this.m_117386_()).m_102872_(modelIn);
            this.m_117125_(modelIn, slotIn);
            boolean flag = this.m_117128_(slotIn);
            C_1315_ armormaterial = (C_1315_)armoritem.m_40401_().m_203334_();
            int i = itemstack.m_204117_(C_140_.f_314020_) ? C_175_.m_321570_(C_313801_.m_322889_(itemstack, -6265536)) : -1;
            Iterator var12 = armormaterial.f_315892_().iterator();

            while(var12.hasNext()) {
               C_1315_.C_313715_ armormaterial$layer = (C_1315_.C_313715_)var12.next();
               int j = armormaterial$layer.m_324910_() ? i : -1;
               this.m_289609_(matrixStackIn, bufferIn, packedLightIn, modelIn, j, armormaterial$layer.m_318738_(flag));
            }

            C_265827_ armortrim = (C_265827_)itemstack.a(C_313616_.f_315199_);
            if (armortrim != null) {
               this.m_289604_(armoritem.m_40401_(), matrixStackIn, bufferIn, packedLightIn, armortrim, modelIn, flag);
            }

            if (itemstack.m_41790_()) {
               this.m_289597_(matrixStackIn, bufferIn, packedLightIn, modelIn);
            }
         }
      }

   }

   protected void m_117125_(C_3829_ modelIn, C_516_ slotIn) {
      modelIn.m_8009_(false);
      switch (slotIn) {
         case HEAD:
            modelIn.f_102808_.f_104207_ = true;
            modelIn.f_102809_.f_104207_ = true;
            break;
         case CHEST:
            modelIn.f_102810_.f_104207_ = true;
            modelIn.f_102811_.f_104207_ = true;
            modelIn.f_102812_.f_104207_ = true;
            break;
         case LEGS:
            modelIn.f_102810_.f_104207_ = true;
            modelIn.f_102813_.f_104207_ = true;
            modelIn.f_102814_.f_104207_ = true;
            break;
         case FEET:
            modelIn.f_102813_.f_104207_ = true;
            modelIn.f_102814_.f_104207_ = true;
      }

   }

   private void m_289609_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_3829_ bipedModelIn, int colorIn, C_5265_ suffixIn) {
      C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_110431_(suffixIn));
      bipedModelIn.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_, colorIn);
   }

   private void m_289604_(C_203228_ armorMaterialIn, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_265827_ trimIn, C_3829_ bipedModelIn, boolean isLegSlot) {
      C_4486_ textureatlassprite = this.f_266073_.m_118316_(isLegSlot ? trimIn.m_267774_(armorMaterialIn) : trimIn.m_267606_(armorMaterialIn));
      textureatlassprite = TextureUtils.getCustomSprite(textureatlassprite);
      C_3187_ vertexconsumer = textureatlassprite.m_118381_(bufferIn.m_6299_(C_4177_.m_266442_(((C_265826_)trimIn.m_266429_().m_203334_()).f_290976_())));
      bipedModelIn.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
   }

   private void m_289597_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_3829_ bipedModelIn) {
      bipedModelIn.a(matrixStackIn, bufferIn.m_6299_(C_4168_.m_110484_()), packedLightIn, C_4474_.f_118083_);
   }

   private C_3829_ m_117078_(C_516_ slotIn) {
      return this.m_117128_(slotIn) ? this.f_117071_ : this.f_117072_;
   }

   private boolean m_117128_(C_516_ slotIn) {
      return slotIn == C_516_.LEGS;
   }
}
