import java.util.Optional;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_3844_;
import net.minecraft.src.C_3853_;
import net.minecraft.src.C_4373_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_899_;
import net.minecraft.src.C_877_.C_262188_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class ParrotOnShoulderLayer<T extends C_1141_> extends RenderLayer<T, C_3853_<T>> {
   private final C_3844_ a;
   public static C_3844_ customParrotModel;

   public ParrotOnShoulderLayer(C_4382_<T, C_3853_<T>> parentIn, C_141653_ modelSetIn) {
      super(parentIn);
      this.a = new C_3844_(modelSetIn.a(C_141656_.f_171203_));
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
      this.a(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, true);
      this.a(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, false);
   }

   private void a(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float netHeadYaw,
      float headPitch,
      boolean leftShoulderIn
   ) {
      C_4917_ compoundtag = leftShoulderIn ? entitylivingbaseIn.m_36331_() : entitylivingbaseIn.m_36332_();
      C_513_.m_20632_(compoundtag.m_128461_("id"))
         .filter(entityTypeIn -> entityTypeIn == C_513_.f_20508_)
         .ifPresent(
            entityTypeIn -> {
               C_507_ renderedEntityOld = Config.getEntityRenderDispatcher().getRenderedEntity();
               if (entitylivingbaseIn instanceof AbstractClientPlayer acp) {
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
                     entityShoulder.f_19854_ = renderedEntityOld.f_19854_;
                     entityShoulder.f_19855_ = renderedEntityOld.f_19855_;
                     entityShoulder.f_19856_ = renderedEntityOld.f_19856_;
                     entityShoulder.m_20343_(renderedEntityOld.m_20185_(), renderedEntityOld.m_20186_(), renderedEntityOld.m_20189_());
                     entityShoulder.f_19860_ = renderedEntityOld.f_19860_;
                     entityShoulder.f_19859_ = renderedEntityOld.f_19859_;
                     entityShoulder.m_146926_(renderedEntityOld.m_146909_());
                     entityShoulder.m_146922_(renderedEntityOld.m_146908_());
                     if (entityShoulder instanceof C_524_ && renderedEntityOld instanceof C_524_) {
                        ((C_524_)entityShoulder).f_20884_ = ((C_524_)renderedEntityOld).f_20884_;
                        ((C_524_)entityShoulder).f_20883_ = ((C_524_)renderedEntityOld).f_20883_;
                     }

                     Config.getEntityRenderDispatcher().setRenderedEntity(entityShoulder);
                     if (Config.isShaders()) {
                        Shaders.nextEntity(entityShoulder);
                     }
                  }
               }

               matrixStackIn.a();
               matrixStackIn.a(leftShoulderIn ? 0.4F : -0.4F, entitylivingbaseIn.m_6047_() ? -1.3F : -1.5F, 0.0F);
               C_262188_ parrot$variant = C_262188_.m_262398_(compoundtag.m_128451_("Variant"));
               VertexConsumer vertexconsumer = bufferIn.getBuffer(this.a.a(C_4373_.a(parrot$variant)));
               this.getParrotModel()
                  .a(
                     matrixStackIn,
                     vertexconsumer,
                     packedLightIn,
                     C_4474_.f_118083_,
                     limbSwing,
                     limbSwingAmount,
                     netHeadYaw,
                     headPitch,
                     entitylivingbaseIn.f_19797_
                  );
               matrixStackIn.b();
               Config.getEntityRenderDispatcher().setRenderedEntity(renderedEntityOld);
               if (Config.isShaders()) {
                  Shaders.nextEntity(renderedEntityOld);
               }
            }
         );
   }

   private C_507_ makeEntity(C_4917_ compoundtag, C_1141_ player) {
      Optional<C_513_<?>> type = C_513_.m_20637_(compoundtag);
      if (!type.isPresent()) {
         return null;
      } else {
         C_507_ entity = ((C_513_)type.get()).m_20615_(player.m_9236_());
         if (entity == null) {
            return null;
         } else {
            entity.m_20258_(compoundtag);
            SynchedEntityData edm = entity.ar();
            if (edm != null) {
               edm.spawnPosition = player.m_20183_();
               edm.spawnBiome = (C_1629_)player.m_9236_().m_204166_(edm.spawnPosition).m_203334_();
            }

            return entity;
         }
      }
   }

   private C_3844_ getParrotModel() {
      return customParrotModel != null ? customParrotModel : this.a;
   }
}
