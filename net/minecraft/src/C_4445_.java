package net.minecraft.src;

import java.util.Optional;
import net.minecraft.src.C_877_.C_262188_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class C_4445_ extends C_4447_ {
   private final C_3844_ f_117290_;
   public static C_3844_ customParrotModel;

   public C_4445_(C_4382_ parentIn, C_141653_ modelSetIn) {
      super(parentIn);
      this.f_117290_ = new C_3844_(modelSetIn.m_171103_(C_141656_.f_171203_));
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_1141_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_117317_(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, true);
      this.m_117317_(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, false);
   }

   private void m_117317_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_1141_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch, boolean leftShoulderIn) {
      C_4917_ compoundtag = leftShoulderIn ? entitylivingbaseIn.m_36331_() : entitylivingbaseIn.m_36332_();
      C_513_.m_20632_(compoundtag.m_128461_("id")).filter((entityTypeIn) -> {
         return entityTypeIn == C_513_.f_20508_;
      }).ifPresent((entityTypeIn) -> {
         C_507_ renderedEntityOld = Config.getEntityRenderDispatcher().getRenderedEntity();
         if (entitylivingbaseIn instanceof C_4102_ acp) {
            C_507_ entityShoulder = leftShoulderIn ? acp.entityShoulderLeft : acp.entityShoulderRight;
            if (entityShoulder == null) {
               entityShoulder = this.makeEntity(compoundtag, entitylivingbaseIn);
               if (entityShoulder instanceof C_899_) {
                  if (leftShoulderIn) {
                     acp.entityShoulderLeft = (C_899_)entityShoulder;
                  } else {
                     acp.entityShoulderRight = (C_899_)entityShoulder;
                  }
               }
            }

            if (entityShoulder != null) {
               ((C_507_)entityShoulder).f_19854_ = renderedEntityOld.f_19854_;
               ((C_507_)entityShoulder).f_19855_ = renderedEntityOld.f_19855_;
               ((C_507_)entityShoulder).f_19856_ = renderedEntityOld.f_19856_;
               ((C_507_)entityShoulder).m_20343_(renderedEntityOld.m_20185_(), renderedEntityOld.m_20186_(), renderedEntityOld.m_20189_());
               ((C_507_)entityShoulder).f_19860_ = renderedEntityOld.f_19860_;
               ((C_507_)entityShoulder).f_19859_ = renderedEntityOld.f_19859_;
               ((C_507_)entityShoulder).m_146926_(renderedEntityOld.m_146909_());
               ((C_507_)entityShoulder).m_146922_(renderedEntityOld.m_146908_());
               if (entityShoulder instanceof C_524_ && renderedEntityOld instanceof C_524_) {
                  ((C_524_)entityShoulder).f_20884_ = ((C_524_)renderedEntityOld).f_20884_;
                  ((C_524_)entityShoulder).f_20883_ = ((C_524_)renderedEntityOld).f_20883_;
               }

               Config.getEntityRenderDispatcher().setRenderedEntity((C_507_)entityShoulder);
               if (Config.isShaders()) {
                  Shaders.nextEntity((C_507_)entityShoulder);
               }
            }
         }

         matrixStackIn.m_85836_();
         matrixStackIn.m_252880_(leftShoulderIn ? 0.4F : -0.4F, entitylivingbaseIn.cb() ? -1.3F : -1.5F, 0.0F);
         C_877_.C_262188_ parrot$variant = C_262188_.m_262398_(compoundtag.m_128451_("Variant"));
         C_3187_ vertexconsumer = bufferIn.m_6299_(this.f_117290_.a(C_4373_.m_262360_(parrot$variant)));
         this.getParrotModel().m_103223_(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_, limbSwing, limbSwingAmount, netHeadYaw, headPitch, entitylivingbaseIn.ai);
         matrixStackIn.m_85849_();
         Config.getEntityRenderDispatcher().setRenderedEntity(renderedEntityOld);
         if (Config.isShaders()) {
            Shaders.nextEntity(renderedEntityOld);
         }

      });
   }

   private C_507_ makeEntity(C_4917_ compoundtag, C_1141_ player) {
      Optional type = C_513_.m_20637_(compoundtag);
      if (!type.isPresent()) {
         return null;
      } else {
         C_507_ entity = ((C_513_)type.get()).m_20615_(player.dO());
         if (entity == null) {
            return null;
         } else {
            entity.m_20258_(compoundtag);
            C_5247_ edm = entity.m_20088_();
            if (edm != null) {
               edm.spawnPosition = player.do();
               edm.spawnBiome = (C_1629_)player.dO().t(edm.spawnPosition).m_203334_();
            }

            return entity;
         }
      }
   }

   private C_3844_ getParrotModel() {
      return customParrotModel != null ? customParrotModel : this.f_117290_;
   }
}
