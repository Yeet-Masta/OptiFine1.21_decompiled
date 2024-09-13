package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.animal.Parrot.Variant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class ParrotOnShoulderLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
   private ParrotModel f_117290_;
   public static ParrotModel customParrotModel;

   public ParrotOnShoulderLayer(RenderLayerParent<T, PlayerModel<T>> parentIn, EntityModelSet modelSetIn) {
      super(parentIn);
      this.f_117290_ = new ParrotModel(modelSetIn.m_171103_(ModelLayers.f_171203_));
   }

   public void m_6494_(
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
      this.m_117317_(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, true);
      this.m_117317_(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, false);
   }

   private void m_117317_(
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
      CompoundTag compoundtag = leftShoulderIn ? entitylivingbaseIn.m_36331_() : entitylivingbaseIn.m_36332_();
      EntityType.m_20632_(compoundtag.m_128461_("id"))
         .m_138619_(entityTypeIn -> entityTypeIn == EntityType.f_20508_)
         .ifPresent(
            entityTypeIn -> {
               Entity renderedEntityOld = Config.getEntityRenderDispatcher().getRenderedEntity();
               if (entitylivingbaseIn instanceof AbstractClientPlayer acp) {
                  Entity entityShoulder = leftShoulderIn ? acp.entityShoulderLeft : acp.entityShoulderRight;
                  if (entityShoulder == null) {
                     entityShoulder = this.makeEntity(compoundtag, entitylivingbaseIn);
                     if (entityShoulder instanceof ShoulderRidingEntity) {
                        if (leftShoulderIn) {
                           acp.entityShoulderLeft = (ShoulderRidingEntity)entityShoulder;
                        } else {
                           acp.entityShoulderRight = (ShoulderRidingEntity)entityShoulder;
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
                     if (entityShoulder instanceof LivingEntity && renderedEntityOld instanceof LivingEntity) {
                        ((LivingEntity)entityShoulder).f_20884_ = ((LivingEntity)renderedEntityOld).f_20884_;
                        ((LivingEntity)entityShoulder).f_20883_ = ((LivingEntity)renderedEntityOld).f_20883_;
                     }

                     Config.getEntityRenderDispatcher().setRenderedEntity(entityShoulder);
                     if (Config.isShaders()) {
                        Shaders.nextEntity(entityShoulder);
                     }
                  }
               }

               matrixStackIn.m_85836_();
               matrixStackIn.m_252880_(leftShoulderIn ? 0.4F : -0.4F, entitylivingbaseIn.m_6047_() ? -1.3F : -1.5F, 0.0F);
               Variant parrot$variant = Variant.m_262398_(compoundtag.m_128451_("Variant"));
               VertexConsumer vertexconsumer = bufferIn.m_6299_(this.f_117290_.m_103119_(ParrotRenderer.m_262360_(parrot$variant)));
               this.getParrotModel()
                  .m_103223_(
                     matrixStackIn,
                     vertexconsumer,
                     packedLightIn,
                     OverlayTexture.f_118083_,
                     limbSwing,
                     limbSwingAmount,
                     netHeadYaw,
                     headPitch,
                     entitylivingbaseIn.f_19797_
                  );
               matrixStackIn.m_85849_();
               Config.getEntityRenderDispatcher().setRenderedEntity(renderedEntityOld);
               if (Config.isShaders()) {
                  Shaders.nextEntity(renderedEntityOld);
               }
            }
         );
   }

   private Entity makeEntity(CompoundTag compoundtag, Player player) {
      Optional<EntityType<?>> type = EntityType.m_20637_(compoundtag);
      if (!type.isPresent()) {
         return null;
      } else {
         Entity entity = ((EntityType)type.get()).m_20615_(player.m_9236_());
         if (entity == null) {
            return null;
         } else {
            entity.m_20258_(compoundtag);
            SynchedEntityData edm = entity.m_20088_();
            if (edm != null) {
               edm.spawnPosition = player.m_20183_();
               edm.spawnBiome = (Biome)player.m_9236_().m_204166_(edm.spawnPosition).m_203334_();
            }

            return entity;
         }
      }
   }

   private ParrotModel getParrotModel() {
      return customParrotModel != null ? customParrotModel : this.f_117290_;
   }
}
