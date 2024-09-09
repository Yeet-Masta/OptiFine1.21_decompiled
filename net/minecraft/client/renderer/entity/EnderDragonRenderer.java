package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.Nullable;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class EnderDragonRenderer extends EntityRenderer {
   public static final ResourceLocation f_114174_ = ResourceLocation.m_340282_("textures/entity/end_crystal/end_crystal_beam.png");
   private static final ResourceLocation f_114175_ = ResourceLocation.m_340282_("textures/entity/enderdragon/dragon_exploding.png");
   private static final ResourceLocation f_114176_ = ResourceLocation.m_340282_("textures/entity/enderdragon/dragon.png");
   private static final ResourceLocation f_114177_ = ResourceLocation.m_340282_("textures/entity/enderdragon/dragon_eyes.png");
   private static final RenderType f_114178_;
   private static final RenderType f_114179_;
   private static final RenderType f_114180_;
   private static final RenderType f_114181_;
   private static final float f_114182_;
   private final DragonModel f_114183_;

   public EnderDragonRenderer(EntityRendererProvider.Context contextIn) {
      super(contextIn);
      this.f_114477_ = 0.5F;
      this.f_114183_ = new DragonModel(contextIn.m_174023_(ModelLayers.f_171144_));
   }

   public void m_7392_(EnderDragon entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      float f = (float)entityIn.m_31101_(7, partialTicks)[0];
      float f1 = (float)(entityIn.m_31101_(5, partialTicks)[1] - entityIn.m_31101_(10, partialTicks)[1]);
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(-f));
      matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(f1 * 10.0F));
      matrixStackIn.m_252880_(0.0F, 0.0F, 1.0F);
      matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
      matrixStackIn.m_252880_(0.0F, -1.501F, 0.0F);
      boolean flag = entityIn.f_20916_ > 0;
      this.f_114183_.m_6839_(entityIn, 0.0F, 0.0F, partialTicks);
      VertexConsumer vertexconsumer3;
      if (entityIn.f_31084_ > 0) {
         float f2 = (float)entityIn.f_31084_ / 200.0F;
         int i = ARGB32.m_320289_(Mth.m_14143_(f2 * 255.0F), -1);
         VertexConsumer vertexconsumer = bufferIn.m_6299_(RenderType.m_173235_(f_114175_));
         this.f_114183_.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, i);
         VertexConsumer vertexconsumer1 = bufferIn.m_6299_(f_114179_);
         this.f_114183_.m_340227_(matrixStackIn, vertexconsumer1, packedLightIn, OverlayTexture.m_118090_(0.0F, flag));
      } else {
         vertexconsumer3 = bufferIn.m_6299_(f_114178_);
         this.f_114183_.m_340227_(matrixStackIn, vertexconsumer3, packedLightIn, OverlayTexture.m_118090_(0.0F, flag));
      }

      vertexconsumer3 = bufferIn.m_6299_(f_114180_);
      if (Config.isShaders()) {
         Shaders.beginSpiderEyes();
      }

      Config.getRenderGlobal().renderOverlayEyes = true;
      this.f_114183_.m_340227_(matrixStackIn, vertexconsumer3, packedLightIn, OverlayTexture.f_118083_);
      Config.getRenderGlobal().renderOverlayEyes = false;
      if (Config.isShaders()) {
         Shaders.endSpiderEyes();
      }

      float f4;
      if (entityIn.f_31084_ > 0) {
         f4 = ((float)entityIn.f_31084_ + partialTicks) / 200.0F;
         matrixStackIn.m_85836_();
         matrixStackIn.m_252880_(0.0F, -1.0F, -2.0F);
         m_338930_(matrixStackIn, f4, bufferIn.m_6299_(RenderType.m_339127_()));
         m_338930_(matrixStackIn, f4, bufferIn.m_6299_(RenderType.m_339251_()));
         matrixStackIn.m_85849_();
      }

      matrixStackIn.m_85849_();
      if (entityIn.f_31086_ != null) {
         matrixStackIn.m_85836_();
         f4 = (float)(entityIn.f_31086_.m_20185_() - Mth.m_14139_((double)partialTicks, entityIn.f_19854_, entityIn.m_20185_()));
         float f5 = (float)(entityIn.f_31086_.m_20186_() - Mth.m_14139_((double)partialTicks, entityIn.f_19855_, entityIn.m_20186_()));
         float f6 = (float)(entityIn.f_31086_.m_20189_() - Mth.m_14139_((double)partialTicks, entityIn.f_19856_, entityIn.m_20189_()));
         m_114187_(f4, f5 + EndCrystalRenderer.m_114158_(entityIn.f_31086_, partialTicks), f6, partialTicks, entityIn.f_19797_, matrixStackIn, bufferIn, packedLightIn);
         matrixStackIn.m_85849_();
      }

      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   private static void m_338930_(PoseStack p_338930_0_, float p_338930_1_, VertexConsumer p_338930_2_) {
      p_338930_0_.m_85836_();
      float f = Math.min(p_338930_1_ > 0.8F ? (p_338930_1_ - 0.8F) / 0.2F : 0.0F, 1.0F);
      int i = ARGB32.m_323842_(1.0F - f, 1.0F, 1.0F, 1.0F);
      int j = 16711935;
      RandomSource randomsource = RandomSource.m_216335_(432L);
      Vector3f vector3f = new Vector3f();
      Vector3f vector3f1 = new Vector3f();
      Vector3f vector3f2 = new Vector3f();
      Vector3f vector3f3 = new Vector3f();
      Quaternionf quaternionf = new Quaternionf();
      int k = Mth.m_14143_((p_338930_1_ + p_338930_1_ * p_338930_1_) / 2.0F * 60.0F);

      for(int l = 0; l < k; ++l) {
         quaternionf.rotationXYZ(randomsource.m_188501_() * 6.2831855F, randomsource.m_188501_() * 6.2831855F, randomsource.m_188501_() * 6.2831855F).rotateXYZ(randomsource.m_188501_() * 6.2831855F, randomsource.m_188501_() * 6.2831855F, randomsource.m_188501_() * 6.2831855F + p_338930_1_ * 1.5707964F);
         p_338930_0_.m_252781_(quaternionf);
         float f1 = randomsource.m_188501_() * 20.0F + 5.0F + f * 10.0F;
         float f2 = randomsource.m_188501_() * 2.0F + 1.0F + f * 2.0F;
         vector3f1.set(-f_114182_ * f2, f1, -0.5F * f2);
         vector3f2.set(f_114182_ * f2, f1, -0.5F * f2);
         vector3f3.set(0.0F, f1, f2);
         PoseStack.Pose posestack$pose = p_338930_0_.m_85850_();
         p_338930_2_.m_340301_(posestack$pose, vector3f).m_338399_(i);
         p_338930_2_.m_340301_(posestack$pose, vector3f1).m_338399_(16711935);
         p_338930_2_.m_340301_(posestack$pose, vector3f2).m_338399_(16711935);
         p_338930_2_.m_340301_(posestack$pose, vector3f).m_338399_(i);
         p_338930_2_.m_340301_(posestack$pose, vector3f2).m_338399_(16711935);
         p_338930_2_.m_340301_(posestack$pose, vector3f3).m_338399_(16711935);
         p_338930_2_.m_340301_(posestack$pose, vector3f).m_338399_(i);
         p_338930_2_.m_340301_(posestack$pose, vector3f3).m_338399_(16711935);
         p_338930_2_.m_340301_(posestack$pose, vector3f1).m_338399_(16711935);
      }

      p_338930_0_.m_85849_();
   }

   public static void m_114187_(float p_114187_0_, float p_114187_1_, float p_114187_2_, float p_114187_3_, int p_114187_4_, PoseStack p_114187_5_, MultiBufferSource p_114187_6_, int p_114187_7_) {
      float f = Mth.m_14116_(p_114187_0_ * p_114187_0_ + p_114187_2_ * p_114187_2_);
      float f1 = Mth.m_14116_(p_114187_0_ * p_114187_0_ + p_114187_1_ * p_114187_1_ + p_114187_2_ * p_114187_2_);
      p_114187_5_.m_85836_();
      p_114187_5_.m_252880_(0.0F, 2.0F, 0.0F);
      p_114187_5_.m_252781_(Axis.f_252436_.m_252961_((float)(-Math.atan2((double)p_114187_2_, (double)p_114187_0_)) - 1.5707964F));
      p_114187_5_.m_252781_(Axis.f_252529_.m_252961_((float)(-Math.atan2((double)f, (double)p_114187_1_)) - 1.5707964F));
      VertexConsumer vertexconsumer = p_114187_6_.m_6299_(f_114181_);
      float f2 = 0.0F - ((float)p_114187_4_ + p_114187_3_) * 0.01F;
      float f3 = Mth.m_14116_(p_114187_0_ * p_114187_0_ + p_114187_1_ * p_114187_1_ + p_114187_2_ * p_114187_2_) / 32.0F - ((float)p_114187_4_ + p_114187_3_) * 0.01F;
      int i = true;
      float f4 = 0.0F;
      float f5 = 0.75F;
      float f6 = 0.0F;
      PoseStack.Pose posestack$pose = p_114187_5_.m_85850_();

      for(int j = 1; j <= 8; ++j) {
         float f7 = Mth.m_14031_((float)j * 6.2831855F / 8.0F) * 0.75F;
         float f8 = Mth.m_14089_((float)j * 6.2831855F / 8.0F) * 0.75F;
         float f9 = (float)j / 8.0F;
         vertexconsumer.m_338370_(posestack$pose, f4 * 0.2F, f5 * 0.2F, 0.0F).m_338399_(-16777216).m_167083_(f6, f2).m_338943_(OverlayTexture.f_118083_).m_338973_(p_114187_7_).m_339200_(posestack$pose, 0.0F, -1.0F, 0.0F);
         vertexconsumer.m_338370_(posestack$pose, f4, f5, f1).m_338399_(-1).m_167083_(f6, f3).m_338943_(OverlayTexture.f_118083_).m_338973_(p_114187_7_).m_339200_(posestack$pose, 0.0F, -1.0F, 0.0F);
         vertexconsumer.m_338370_(posestack$pose, f7, f8, f1).m_338399_(-1).m_167083_(f9, f3).m_338943_(OverlayTexture.f_118083_).m_338973_(p_114187_7_).m_339200_(posestack$pose, 0.0F, -1.0F, 0.0F);
         vertexconsumer.m_338370_(posestack$pose, f7 * 0.2F, f8 * 0.2F, 0.0F).m_338399_(-16777216).m_167083_(f9, f2).m_338943_(OverlayTexture.f_118083_).m_338973_(p_114187_7_).m_339200_(posestack$pose, 0.0F, -1.0F, 0.0F);
         f4 = f7;
         f5 = f8;
         f6 = f9;
      }

      p_114187_5_.m_85849_();
   }

   public ResourceLocation m_5478_(EnderDragon entity) {
      return f_114176_;
   }

   public static LayerDefinition m_173974_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      float f = -16.0F;
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171534_("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16, 176, 44).m_171534_("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16, 112, 30).m_171480_().m_171534_("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0).m_171534_("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0).m_171480_().m_171534_("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0).m_171534_("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0), PartPose.f_171404_);
      partdefinition1.m_171599_("jaw", CubeListBuilder.m_171558_().m_171534_("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16, 176, 65), PartPose.m_171419_(0.0F, 4.0F, -8.0F));
      partdefinition.m_171599_("neck", CubeListBuilder.m_171558_().m_171534_("box", -5.0F, -5.0F, -5.0F, 10, 10, 10, 192, 104).m_171534_("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6, 48, 0), PartPose.f_171404_);
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171534_("body", -12.0F, 0.0F, -16.0F, 24, 24, 64, 0, 0).m_171534_("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12, 220, 53).m_171534_("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12, 220, 53).m_171534_("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12, 220, 53), PartPose.m_171419_(0.0F, 4.0F, 8.0F));
      PartDefinition partdefinition2 = partdefinition.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171480_().m_171534_("bone", 0.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88).m_171534_("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88), PartPose.m_171419_(12.0F, 5.0F, 2.0F));
      partdefinition2.m_171599_("left_wing_tip", CubeListBuilder.m_171558_().m_171480_().m_171534_("bone", 0.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136).m_171534_("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144), PartPose.m_171419_(56.0F, 0.0F, 0.0F));
      PartDefinition partdefinition3 = partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171534_("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), PartPose.m_171419_(12.0F, 20.0F, 2.0F));
      PartDefinition partdefinition4 = partdefinition3.m_171599_("left_front_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), PartPose.m_171419_(0.0F, 20.0F, -1.0F));
      partdefinition4.m_171599_("left_front_foot", CubeListBuilder.m_171558_().m_171534_("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), PartPose.m_171419_(0.0F, 23.0F, 0.0F));
      PartDefinition partdefinition5 = partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171534_("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), PartPose.m_171419_(16.0F, 16.0F, 42.0F));
      PartDefinition partdefinition6 = partdefinition5.m_171599_("left_hind_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), PartPose.m_171419_(0.0F, 32.0F, -4.0F));
      partdefinition6.m_171599_("left_hind_foot", CubeListBuilder.m_171558_().m_171534_("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), PartPose.m_171419_(0.0F, 31.0F, 4.0F));
      PartDefinition partdefinition7 = partdefinition.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171534_("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88).m_171534_("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88), PartPose.m_171419_(-12.0F, 5.0F, 2.0F));
      partdefinition7.m_171599_("right_wing_tip", CubeListBuilder.m_171558_().m_171534_("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136).m_171534_("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144), PartPose.m_171419_(-56.0F, 0.0F, 0.0F));
      PartDefinition partdefinition8 = partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171534_("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), PartPose.m_171419_(-12.0F, 20.0F, 2.0F));
      PartDefinition partdefinition9 = partdefinition8.m_171599_("right_front_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), PartPose.m_171419_(0.0F, 20.0F, -1.0F));
      partdefinition9.m_171599_("right_front_foot", CubeListBuilder.m_171558_().m_171534_("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), PartPose.m_171419_(0.0F, 23.0F, 0.0F));
      PartDefinition partdefinition10 = partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171534_("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), PartPose.m_171419_(-16.0F, 16.0F, 42.0F));
      PartDefinition partdefinition11 = partdefinition10.m_171599_("right_hind_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), PartPose.m_171419_(0.0F, 32.0F, -4.0F));
      partdefinition11.m_171599_("right_hind_foot", CubeListBuilder.m_171558_().m_171534_("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), PartPose.m_171419_(0.0F, 31.0F, 4.0F));
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   static {
      f_114178_ = RenderType.m_110458_(f_114176_);
      f_114179_ = RenderType.m_110479_(f_114176_);
      f_114180_ = RenderType.m_110488_(f_114177_);
      f_114181_ = RenderType.m_110476_(f_114174_);
      f_114182_ = (float)(Math.sqrt(3.0) / 2.0);
   }

   public static class DragonModel extends EntityModel {
      private final ModelPart f_114235_;
      private final ModelPart f_114236_;
      private final ModelPart f_114237_;
      private final ModelPart f_114238_;
      private final ModelPart f_114239_;
      private final ModelPart f_114240_;
      private final ModelPart f_114241_;
      private final ModelPart f_114242_;
      private final ModelPart f_114243_;
      private final ModelPart f_114244_;
      private final ModelPart f_114245_;
      private final ModelPart f_114246_;
      private final ModelPart f_114247_;
      private final ModelPart f_114248_;
      private final ModelPart f_114249_;
      private final ModelPart f_114250_;
      private final ModelPart f_114251_;
      private final ModelPart f_114252_;
      private final ModelPart f_114253_;
      private final ModelPart f_114254_;
      @Nullable
      private EnderDragon f_114233_;
      private float f_114234_;

      public DragonModel(ModelPart partIn) {
         this.f_114235_ = partIn.m_171324_("head");
         this.f_114237_ = this.f_114235_.m_171324_("jaw");
         this.f_114236_ = partIn.m_171324_("neck");
         this.f_114238_ = partIn.m_171324_("body");
         this.f_114239_ = partIn.m_171324_("left_wing");
         this.f_114240_ = this.f_114239_.m_171324_("left_wing_tip");
         this.f_114241_ = partIn.m_171324_("left_front_leg");
         this.f_114242_ = this.f_114241_.m_171324_("left_front_leg_tip");
         this.f_114243_ = this.f_114242_.m_171324_("left_front_foot");
         this.f_114244_ = partIn.m_171324_("left_hind_leg");
         this.f_114245_ = this.f_114244_.m_171324_("left_hind_leg_tip");
         this.f_114246_ = this.f_114245_.m_171324_("left_hind_foot");
         this.f_114247_ = partIn.m_171324_("right_wing");
         this.f_114248_ = this.f_114247_.m_171324_("right_wing_tip");
         this.f_114249_ = partIn.m_171324_("right_front_leg");
         this.f_114250_ = this.f_114249_.m_171324_("right_front_leg_tip");
         this.f_114251_ = this.f_114250_.m_171324_("right_front_foot");
         this.f_114252_ = partIn.m_171324_("right_hind_leg");
         this.f_114253_ = this.f_114252_.m_171324_("right_hind_leg_tip");
         this.f_114254_ = this.f_114253_.m_171324_("right_hind_foot");
      }

      public void m_6839_(EnderDragon entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
         this.f_114233_ = entityIn;
         this.f_114234_ = partialTick;
      }

      public void m_6973_(EnderDragon entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      }

      public void m_7695_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
         matrixStackIn.m_85836_();
         float f = Mth.m_14179_(this.f_114234_, this.f_114233_.f_31081_, this.f_114233_.f_31082_);
         this.f_114237_.f_104203_ = (float)(Math.sin((double)(f * 6.2831855F)) + 1.0) * 0.2F;
         float f1 = (float)(Math.sin((double)(f * 6.2831855F - 1.0F)) + 1.0);
         f1 = (f1 * f1 + f1 * 2.0F) * 0.05F;
         matrixStackIn.m_252880_(0.0F, f1 - 2.0F, -3.0F);
         matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(f1 * 2.0F));
         float f2 = 0.0F;
         float f3 = 20.0F;
         float f4 = -12.0F;
         float f5 = 1.5F;
         double[] adouble = this.f_114233_.m_31101_(6, this.f_114234_);
         float f6 = Mth.m_14177_((float)(this.f_114233_.m_31101_(5, this.f_114234_)[0] - this.f_114233_.m_31101_(10, this.f_114234_)[0]));
         float f7 = Mth.m_14177_((float)(this.f_114233_.m_31101_(5, this.f_114234_)[0] + (double)(f6 / 2.0F)));
         float f8 = f * 6.2831855F;

         float f11;
         for(int i = 0; i < 5; ++i) {
            double[] adouble1 = this.f_114233_.m_31101_(5 - i, this.f_114234_);
            f11 = (float)Math.cos((double)((float)i * 0.45F + f8)) * 0.15F;
            this.f_114236_.f_104204_ = Mth.m_14177_((float)(adouble1[0] - adouble[0])) * 0.017453292F * 1.5F;
            this.f_114236_.f_104203_ = f11 + this.f_114233_.m_31108_(i, adouble, adouble1) * 0.017453292F * 1.5F * 5.0F;
            this.f_114236_.f_104205_ = -Mth.m_14177_((float)(adouble1[0] - (double)f7)) * 0.017453292F * 1.5F;
            this.f_114236_.f_104201_ = f3;
            this.f_114236_.f_104202_ = f4;
            this.f_114236_.f_104200_ = f2;
            f3 += Mth.m_14031_(this.f_114236_.f_104203_) * 10.0F;
            f4 -= Mth.m_14089_(this.f_114236_.f_104204_) * Mth.m_14089_(this.f_114236_.f_104203_) * 10.0F;
            f2 -= Mth.m_14031_(this.f_114236_.f_104204_) * Mth.m_14089_(this.f_114236_.f_104203_) * 10.0F;
            this.f_114236_.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
         }

         this.f_114235_.f_104201_ = f3;
         this.f_114235_.f_104202_ = f4;
         this.f_114235_.f_104200_ = f2;
         double[] adouble2 = this.f_114233_.m_31101_(0, this.f_114234_);
         this.f_114235_.f_104204_ = Mth.m_14177_((float)(adouble2[0] - adouble[0])) * 0.017453292F;
         this.f_114235_.f_104203_ = Mth.m_14177_(this.f_114233_.m_31108_(6, adouble, adouble2)) * 0.017453292F * 1.5F * 5.0F;
         this.f_114235_.f_104205_ = -Mth.m_14177_((float)(adouble2[0] - (double)f7)) * 0.017453292F;
         this.f_114235_.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
         matrixStackIn.m_85836_();
         matrixStackIn.m_252880_(0.0F, 1.0F, 0.0F);
         matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(-f6 * 1.5F));
         matrixStackIn.m_252880_(0.0F, -1.0F, 0.0F);
         this.f_114238_.f_104205_ = 0.0F;
         this.f_114238_.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
         float f10 = f * 6.2831855F;
         this.f_114239_.f_104203_ = 0.125F - (float)Math.cos((double)f10) * 0.2F;
         this.f_114239_.f_104204_ = -0.25F;
         this.f_114239_.f_104205_ = -((float)(Math.sin((double)f10) + 0.125)) * 0.8F;
         this.f_114240_.f_104205_ = (float)(Math.sin((double)(f10 + 2.0F)) + 0.5) * 0.75F;
         this.f_114247_.f_104203_ = this.f_114239_.f_104203_;
         this.f_114247_.f_104204_ = -this.f_114239_.f_104204_;
         this.f_114247_.f_104205_ = -this.f_114239_.f_104205_;
         this.f_114248_.f_104205_ = -this.f_114240_.f_104205_;
         this.m_173977_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, f1, this.f_114239_, this.f_114241_, this.f_114242_, this.f_114243_, this.f_114244_, this.f_114245_, this.f_114246_, colorIn);
         this.m_173977_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, f1, this.f_114247_, this.f_114249_, this.f_114250_, this.f_114251_, this.f_114252_, this.f_114253_, this.f_114254_, colorIn);
         matrixStackIn.m_85849_();
         f11 = -Mth.m_14031_(f * 6.2831855F) * 0.0F;
         f8 = f * 6.2831855F;
         f3 = 10.0F;
         f4 = 60.0F;
         f2 = 0.0F;
         adouble = this.f_114233_.m_31101_(11, this.f_114234_);

         for(int j = 0; j < 12; ++j) {
            adouble2 = this.f_114233_.m_31101_(12 + j, this.f_114234_);
            f11 += Mth.m_14031_((float)j * 0.45F + f8) * 0.05F;
            this.f_114236_.f_104204_ = (Mth.m_14177_((float)(adouble2[0] - adouble[0])) * 1.5F + 180.0F) * 0.017453292F;
            this.f_114236_.f_104203_ = f11 + (float)(adouble2[1] - adouble[1]) * 0.017453292F * 1.5F * 5.0F;
            this.f_114236_.f_104205_ = Mth.m_14177_((float)(adouble2[0] - (double)f7)) * 0.017453292F * 1.5F;
            this.f_114236_.f_104201_ = f3;
            this.f_114236_.f_104202_ = f4;
            this.f_114236_.f_104200_ = f2;
            f3 += Mth.m_14031_(this.f_114236_.f_104203_) * 10.0F;
            f4 -= Mth.m_14089_(this.f_114236_.f_104204_) * Mth.m_14089_(this.f_114236_.f_104203_) * 10.0F;
            f2 -= Mth.m_14031_(this.f_114236_.f_104204_) * Mth.m_14089_(this.f_114236_.f_104203_) * 10.0F;
            this.f_114236_.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
         }

         matrixStackIn.m_85849_();
      }

      private void m_173977_(PoseStack p_173977_1_, VertexConsumer p_173977_2_, int p_173977_3_, int p_173977_4_, float p_173977_5_, ModelPart p_173977_6_, ModelPart p_173977_7_, ModelPart p_173977_8_, ModelPart p_173977_9_, ModelPart p_173977_10_, ModelPart p_173977_11_, ModelPart p_173977_12_, int p_173977_13_) {
         p_173977_10_.f_104203_ = 1.0F + p_173977_5_ * 0.1F;
         p_173977_11_.f_104203_ = 0.5F + p_173977_5_ * 0.1F;
         p_173977_12_.f_104203_ = 0.75F + p_173977_5_ * 0.1F;
         p_173977_7_.f_104203_ = 1.3F + p_173977_5_ * 0.1F;
         p_173977_8_.f_104203_ = -0.5F - p_173977_5_ * 0.1F;
         p_173977_9_.f_104203_ = 0.75F + p_173977_5_ * 0.1F;
         p_173977_6_.m_104306_(p_173977_1_, p_173977_2_, p_173977_3_, p_173977_4_, p_173977_13_);
         p_173977_7_.m_104306_(p_173977_1_, p_173977_2_, p_173977_3_, p_173977_4_, p_173977_13_);
         p_173977_10_.m_104306_(p_173977_1_, p_173977_2_, p_173977_3_, p_173977_4_, p_173977_13_);
      }
   }
}
