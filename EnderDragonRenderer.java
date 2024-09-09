import javax.annotation.Nullable;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_141659_;
import net.minecraft.src.C_141662_;
import net.minecraft.src.C_141663_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_4325_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_945_;
import net.minecraft.src.C_141742_.C_141743_;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class EnderDragonRenderer extends EntityRenderer<C_945_> {
   public static final ResourceLocation a = ResourceLocation.b("textures/entity/end_crystal/end_crystal_beam.png");
   private static final ResourceLocation g = ResourceLocation.b("textures/entity/enderdragon/dragon_exploding.png");
   private static final ResourceLocation h = ResourceLocation.b("textures/entity/enderdragon/dragon.png");
   private static final ResourceLocation i = ResourceLocation.b("textures/entity/enderdragon/dragon_eyes.png");
   private static final RenderType j = RenderType.e(h);
   private static final RenderType k = RenderType.l(h);
   private static final RenderType l = RenderType.p(i);
   private static final RenderType m = RenderType.k(a);
   private static final float n = (float)(Math.sqrt(3.0) / 2.0);
   private final EnderDragonRenderer.a o;

   public EnderDragonRenderer(C_141743_ contextIn) {
      super(contextIn);
      this.e = 0.5F;
      this.o = new EnderDragonRenderer.a(contextIn.a(C_141656_.f_171144_));
   }

   public void a(C_945_ entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.a();
      float f = (float)entityIn.m_31101_(7, partialTicks)[0];
      float f1 = (float)(entityIn.m_31101_(5, partialTicks)[1] - entityIn.m_31101_(10, partialTicks)[1]);
      matrixStackIn.a(C_252363_.f_252436_.m_252977_(-f));
      matrixStackIn.a(C_252363_.f_252529_.m_252977_(f1 * 10.0F));
      matrixStackIn.a(0.0F, 0.0F, 1.0F);
      matrixStackIn.b(-1.0F, -1.0F, 1.0F);
      matrixStackIn.a(0.0F, -1.501F, 0.0F);
      boolean flag = entityIn.aO > 0;
      this.o.a(entityIn, 0.0F, 0.0F, partialTicks);
      if (entityIn.f_31084_ > 0) {
         float f2 = (float)entityIn.f_31084_ / 200.0F;
         int i = C_175_.m_320289_(Mth.d(f2 * 255.0F), -1);
         VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.o(g));
         this.o.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_, i);
         VertexConsumer vertexconsumer1 = bufferIn.getBuffer(k);
         this.o.a(matrixStackIn, vertexconsumer1, packedLightIn, C_4474_.m_118090_(0.0F, flag));
      } else {
         VertexConsumer vertexconsumer2 = bufferIn.getBuffer(j);
         this.o.a(matrixStackIn, vertexconsumer2, packedLightIn, C_4474_.m_118090_(0.0F, flag));
      }

      VertexConsumer vertexconsumer3 = bufferIn.getBuffer(l);
      if (Config.isShaders()) {
         Shaders.beginSpiderEyes();
      }

      Config.getRenderGlobal().renderOverlayEyes = true;
      this.o.a(matrixStackIn, vertexconsumer3, packedLightIn, C_4474_.f_118083_);
      Config.getRenderGlobal().renderOverlayEyes = false;
      if (Config.isShaders()) {
         Shaders.endSpiderEyes();
      }

      if (entityIn.f_31084_ > 0) {
         float f3 = ((float)entityIn.f_31084_ + partialTicks) / 200.0F;
         matrixStackIn.a();
         matrixStackIn.a(0.0F, -1.0F, -2.0F);
         a(matrixStackIn, f3, bufferIn.getBuffer(RenderType.r()));
         a(matrixStackIn, f3, bufferIn.getBuffer(RenderType.s()));
         matrixStackIn.b();
      }

      matrixStackIn.b();
      if (entityIn.f_31086_ != null) {
         matrixStackIn.a();
         float f4 = (float)(entityIn.f_31086_.m_20185_() - Mth.d((double)partialTicks, entityIn.L, entityIn.dt()));
         float f5 = (float)(entityIn.f_31086_.m_20186_() - Mth.d((double)partialTicks, entityIn.M, entityIn.dv()));
         float f6 = (float)(entityIn.f_31086_.m_20189_() - Mth.d((double)partialTicks, entityIn.N, entityIn.dz()));
         a(f4, f5 + C_4325_.m_114158_(entityIn.f_31086_, partialTicks), f6, partialTicks, entityIn.ai, matrixStackIn, bufferIn, packedLightIn);
         matrixStackIn.b();
      }

      super.a(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   private static void a(PoseStack p_338930_0_, float p_338930_1_, VertexConsumer p_338930_2_) {
      p_338930_0_.a();
      float f = Math.min(p_338930_1_ > 0.8F ? (p_338930_1_ - 0.8F) / 0.2F : 0.0F, 1.0F);
      int i = C_175_.m_323842_(1.0F - f, 1.0F, 1.0F, 1.0F);
      int j = 16711935;
      C_212974_ randomsource = C_212974_.m_216335_(432L);
      Vector3f vector3f = new Vector3f();
      Vector3f vector3f1 = new Vector3f();
      Vector3f vector3f2 = new Vector3f();
      Vector3f vector3f3 = new Vector3f();
      Quaternionf quaternionf = new Quaternionf();
      int k = Mth.d((p_338930_1_ + p_338930_1_ * p_338930_1_) / 2.0F * 60.0F);

      for (int l = 0; l < k; l++) {
         quaternionf.rotationXYZ(
               randomsource.m_188501_() * (float) (Math.PI * 2),
               randomsource.m_188501_() * (float) (Math.PI * 2),
               randomsource.m_188501_() * (float) (Math.PI * 2)
            )
            .rotateXYZ(
               randomsource.m_188501_() * (float) (Math.PI * 2),
               randomsource.m_188501_() * (float) (Math.PI * 2),
               randomsource.m_188501_() * (float) (Math.PI * 2) + p_338930_1_ * (float) (Math.PI / 2)
            );
         p_338930_0_.a(quaternionf);
         float f1 = randomsource.m_188501_() * 20.0F + 5.0F + f * 10.0F;
         float f2 = randomsource.m_188501_() * 2.0F + 1.0F + f * 2.0F;
         vector3f1.set(-n * f2, f1, -0.5F * f2);
         vector3f2.set(n * f2, f1, -0.5F * f2);
         vector3f3.set(0.0F, f1, f2);
         PoseStack.a posestack$pose = p_338930_0_.c();
         p_338930_2_.a(posestack$pose, vector3f).a(i);
         p_338930_2_.a(posestack$pose, vector3f1).a(16711935);
         p_338930_2_.a(posestack$pose, vector3f2).a(16711935);
         p_338930_2_.a(posestack$pose, vector3f).a(i);
         p_338930_2_.a(posestack$pose, vector3f2).a(16711935);
         p_338930_2_.a(posestack$pose, vector3f3).a(16711935);
         p_338930_2_.a(posestack$pose, vector3f).a(i);
         p_338930_2_.a(posestack$pose, vector3f3).a(16711935);
         p_338930_2_.a(posestack$pose, vector3f1).a(16711935);
      }

      p_338930_0_.b();
   }

   public static void a(
      float p_114187_0_,
      float p_114187_1_,
      float p_114187_2_,
      float p_114187_3_,
      int p_114187_4_,
      PoseStack p_114187_5_,
      MultiBufferSource p_114187_6_,
      int p_114187_7_
   ) {
      float f = Mth.c(p_114187_0_ * p_114187_0_ + p_114187_2_ * p_114187_2_);
      float f1 = Mth.c(p_114187_0_ * p_114187_0_ + p_114187_1_ * p_114187_1_ + p_114187_2_ * p_114187_2_);
      p_114187_5_.a();
      p_114187_5_.a(0.0F, 2.0F, 0.0F);
      p_114187_5_.a(C_252363_.f_252436_.m_252961_((float)(-Math.atan2((double)p_114187_2_, (double)p_114187_0_)) - (float) (Math.PI / 2)));
      p_114187_5_.a(C_252363_.f_252529_.m_252961_((float)(-Math.atan2((double)f, (double)p_114187_1_)) - (float) (Math.PI / 2)));
      VertexConsumer vertexconsumer = p_114187_6_.getBuffer(m);
      float f2 = 0.0F - ((float)p_114187_4_ + p_114187_3_) * 0.01F;
      float f3 = Mth.c(p_114187_0_ * p_114187_0_ + p_114187_1_ * p_114187_1_ + p_114187_2_ * p_114187_2_) / 32.0F - ((float)p_114187_4_ + p_114187_3_) * 0.01F;
      int i = 8;
      float f4 = 0.0F;
      float f5 = 0.75F;
      float f6 = 0.0F;
      PoseStack.a posestack$pose = p_114187_5_.c();

      for (int j = 1; j <= 8; j++) {
         float f7 = Mth.a((float)j * (float) (Math.PI * 2) / 8.0F) * 0.75F;
         float f8 = Mth.b((float)j * (float) (Math.PI * 2) / 8.0F) * 0.75F;
         float f9 = (float)j / 8.0F;
         vertexconsumer.a(posestack$pose, f4 * 0.2F, f5 * 0.2F, 0.0F)
            .a(-16777216)
            .a(f6, f2)
            .b(C_4474_.f_118083_)
            .c(p_114187_7_)
            .b(posestack$pose, 0.0F, -1.0F, 0.0F);
         vertexconsumer.a(posestack$pose, f4, f5, f1).a(-1).a(f6, f3).b(C_4474_.f_118083_).c(p_114187_7_).b(posestack$pose, 0.0F, -1.0F, 0.0F);
         vertexconsumer.a(posestack$pose, f7, f8, f1).a(-1).a(f9, f3).b(C_4474_.f_118083_).c(p_114187_7_).b(posestack$pose, 0.0F, -1.0F, 0.0F);
         vertexconsumer.a(posestack$pose, f7 * 0.2F, f8 * 0.2F, 0.0F)
            .a(-16777216)
            .a(f9, f2)
            .b(C_4474_.f_118083_)
            .c(p_114187_7_)
            .b(posestack$pose, 0.0F, -1.0F, 0.0F);
         f4 = f7;
         f5 = f8;
         f6 = f9;
      }

      p_114187_5_.b();
   }

   public ResourceLocation a(C_945_ entity) {
      return h;
   }

   public static C_141663_ a() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.a();
      float f = -16.0F;
      PartDefinition partdefinition1 = partdefinition.a(
         "head",
         C_141662_.m_171558_()
            .m_171534_("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16, 176, 44)
            .m_171534_("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16, 112, 30)
            .m_171480_()
            .m_171534_("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0)
            .m_171534_("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0)
            .m_171480_()
            .m_171534_("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0)
            .m_171534_("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0),
         C_141659_.f_171404_
      );
      partdefinition1.a("jaw", C_141662_.m_171558_().m_171534_("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16, 176, 65), C_141659_.m_171419_(0.0F, 4.0F, -8.0F));
      partdefinition.a(
         "neck",
         C_141662_.m_171558_().m_171534_("box", -5.0F, -5.0F, -5.0F, 10, 10, 10, 192, 104).m_171534_("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6, 48, 0),
         C_141659_.f_171404_
      );
      partdefinition.a(
         "body",
         C_141662_.m_171558_()
            .m_171534_("body", -12.0F, 0.0F, -16.0F, 24, 24, 64, 0, 0)
            .m_171534_("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12, 220, 53)
            .m_171534_("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12, 220, 53)
            .m_171534_("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12, 220, 53),
         C_141659_.m_171419_(0.0F, 4.0F, 8.0F)
      );
      PartDefinition partdefinition2 = partdefinition.a(
         "left_wing",
         C_141662_.m_171558_().m_171480_().m_171534_("bone", 0.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88).m_171534_("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88),
         C_141659_.m_171419_(12.0F, 5.0F, 2.0F)
      );
      partdefinition2.a(
         "left_wing_tip",
         C_141662_.m_171558_().m_171480_().m_171534_("bone", 0.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136).m_171534_("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144),
         C_141659_.m_171419_(56.0F, 0.0F, 0.0F)
      );
      PartDefinition partdefinition3 = partdefinition.a(
         "left_front_leg", C_141662_.m_171558_().m_171534_("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), C_141659_.m_171419_(12.0F, 20.0F, 2.0F)
      );
      PartDefinition partdefinition4 = partdefinition3.a(
         "left_front_leg_tip", C_141662_.m_171558_().m_171534_("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), C_141659_.m_171419_(0.0F, 20.0F, -1.0F)
      );
      partdefinition4.a(
         "left_front_foot", C_141662_.m_171558_().m_171534_("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), C_141659_.m_171419_(0.0F, 23.0F, 0.0F)
      );
      PartDefinition partdefinition5 = partdefinition.a(
         "left_hind_leg", C_141662_.m_171558_().m_171534_("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), C_141659_.m_171419_(16.0F, 16.0F, 42.0F)
      );
      PartDefinition partdefinition6 = partdefinition5.a(
         "left_hind_leg_tip", C_141662_.m_171558_().m_171534_("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), C_141659_.m_171419_(0.0F, 32.0F, -4.0F)
      );
      partdefinition6.a(
         "left_hind_foot", C_141662_.m_171558_().m_171534_("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), C_141659_.m_171419_(0.0F, 31.0F, 4.0F)
      );
      PartDefinition partdefinition7 = partdefinition.a(
         "right_wing",
         C_141662_.m_171558_().m_171534_("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88).m_171534_("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88),
         C_141659_.m_171419_(-12.0F, 5.0F, 2.0F)
      );
      partdefinition7.a(
         "right_wing_tip",
         C_141662_.m_171558_().m_171534_("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136).m_171534_("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144),
         C_141659_.m_171419_(-56.0F, 0.0F, 0.0F)
      );
      PartDefinition partdefinition8 = partdefinition.a(
         "right_front_leg", C_141662_.m_171558_().m_171534_("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), C_141659_.m_171419_(-12.0F, 20.0F, 2.0F)
      );
      PartDefinition partdefinition9 = partdefinition8.a(
         "right_front_leg_tip", C_141662_.m_171558_().m_171534_("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), C_141659_.m_171419_(0.0F, 20.0F, -1.0F)
      );
      partdefinition9.a(
         "right_front_foot", C_141662_.m_171558_().m_171534_("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), C_141659_.m_171419_(0.0F, 23.0F, 0.0F)
      );
      PartDefinition partdefinition10 = partdefinition.a(
         "right_hind_leg", C_141662_.m_171558_().m_171534_("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), C_141659_.m_171419_(-16.0F, 16.0F, 42.0F)
      );
      PartDefinition partdefinition11 = partdefinition10.a(
         "right_hind_leg_tip", C_141662_.m_171558_().m_171534_("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), C_141659_.m_171419_(0.0F, 32.0F, -4.0F)
      );
      partdefinition11.a(
         "right_hind_foot", C_141662_.m_171558_().m_171534_("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), C_141659_.m_171419_(0.0F, 31.0F, 4.0F)
      );
      return C_141663_.a(meshdefinition, 256, 256);
   }

   public static class a extends C_3819_<C_945_> {
      private final ModelPart a;
      private final ModelPart b;
      private final ModelPart f;
      private final ModelPart g;
      private final ModelPart h;
      private final ModelPart i;
      private final ModelPart j;
      private final ModelPart k;
      private final ModelPart l;
      private final ModelPart m;
      private final ModelPart n;
      private final ModelPart o;
      private final ModelPart p;
      private final ModelPart q;
      private final ModelPart r;
      private final ModelPart s;
      private final ModelPart t;
      private final ModelPart u;
      private final ModelPart w;
      private final ModelPart x;
      @Nullable
      private C_945_ y;
      private float z;

      public a(ModelPart partIn) {
         this.a = partIn.b("head");
         this.f = this.a.b("jaw");
         this.b = partIn.b("neck");
         this.g = partIn.b("body");
         this.h = partIn.b("left_wing");
         this.i = this.h.b("left_wing_tip");
         this.j = partIn.b("left_front_leg");
         this.k = this.j.b("left_front_leg_tip");
         this.l = this.k.b("left_front_foot");
         this.m = partIn.b("left_hind_leg");
         this.n = this.m.b("left_hind_leg_tip");
         this.o = this.n.b("left_hind_foot");
         this.p = partIn.b("right_wing");
         this.q = this.p.b("right_wing_tip");
         this.r = partIn.b("right_front_leg");
         this.s = this.r.b("right_front_leg_tip");
         this.t = this.s.b("right_front_foot");
         this.u = partIn.b("right_hind_leg");
         this.w = this.u.b("right_hind_leg_tip");
         this.x = this.w.b("right_hind_foot");
      }

      public void a(C_945_ entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
         this.y = entityIn;
         this.z = partialTick;
      }

      public void a(C_945_ entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      }

      public void a(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
         matrixStackIn.a();
         float f = Mth.i(this.z, this.y.f_31081_, this.y.f_31082_);
         this.f.e = (float)(Math.sin((double)(f * (float) (Math.PI * 2))) + 1.0) * 0.2F;
         float f1 = (float)(Math.sin((double)(f * (float) (Math.PI * 2) - 1.0F)) + 1.0);
         f1 = (f1 * f1 + f1 * 2.0F) * 0.05F;
         matrixStackIn.a(0.0F, f1 - 2.0F, -3.0F);
         matrixStackIn.a(C_252363_.f_252529_.m_252977_(f1 * 2.0F));
         float f2 = 0.0F;
         float f3 = 20.0F;
         float f4 = -12.0F;
         float f5 = 1.5F;
         double[] adouble = this.y.m_31101_(6, this.z);
         float f6 = Mth.g((float)(this.y.m_31101_(5, this.z)[0] - this.y.m_31101_(10, this.z)[0]));
         float f7 = Mth.g((float)(this.y.m_31101_(5, this.z)[0] + (double)(f6 / 2.0F)));
         float f8 = f * (float) (Math.PI * 2);

         for (int i = 0; i < 5; i++) {
            double[] adouble1 = this.y.m_31101_(5 - i, this.z);
            float f9 = (float)Math.cos((double)((float)i * 0.45F + f8)) * 0.15F;
            this.b.f = Mth.g((float)(adouble1[0] - adouble[0])) * (float) (Math.PI / 180.0) * 1.5F;
            this.b.e = f9 + this.y.m_31108_(i, adouble, adouble1) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
            this.b.g = -Mth.g((float)(adouble1[0] - (double)f7)) * (float) (Math.PI / 180.0) * 1.5F;
            this.b.c = f3;
            this.b.d = f4;
            this.b.b = f2;
            f3 += Mth.a(this.b.e) * 10.0F;
            f4 -= Mth.b(this.b.f) * Mth.b(this.b.e) * 10.0F;
            f2 -= Mth.a(this.b.f) * Mth.b(this.b.e) * 10.0F;
            this.b.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
         }

         this.a.c = f3;
         this.a.d = f4;
         this.a.b = f2;
         double[] adouble2 = this.y.m_31101_(0, this.z);
         this.a.f = Mth.g((float)(adouble2[0] - adouble[0])) * (float) (Math.PI / 180.0);
         this.a.e = Mth.g(this.y.m_31108_(6, adouble, adouble2)) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
         this.a.g = -Mth.g((float)(adouble2[0] - (double)f7)) * (float) (Math.PI / 180.0);
         this.a.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
         matrixStackIn.a();
         matrixStackIn.a(0.0F, 1.0F, 0.0F);
         matrixStackIn.a(C_252363_.f_252403_.m_252977_(-f6 * 1.5F));
         matrixStackIn.a(0.0F, -1.0F, 0.0F);
         this.g.g = 0.0F;
         this.g.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
         float f10 = f * (float) (Math.PI * 2);
         this.h.e = 0.125F - (float)Math.cos((double)f10) * 0.2F;
         this.h.f = -0.25F;
         this.h.g = -((float)(Math.sin((double)f10) + 0.125)) * 0.8F;
         this.i.g = (float)(Math.sin((double)(f10 + 2.0F)) + 0.5) * 0.75F;
         this.p.e = this.h.e;
         this.p.f = -this.h.f;
         this.p.g = -this.h.g;
         this.q.g = -this.i.g;
         this.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, f1, this.h, this.j, this.k, this.l, this.m, this.n, this.o, colorIn);
         this.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, f1, this.p, this.r, this.s, this.t, this.u, this.w, this.x, colorIn);
         matrixStackIn.b();
         float f11 = -Mth.a(f * (float) (Math.PI * 2)) * 0.0F;
         f8 = f * (float) (Math.PI * 2);
         f3 = 10.0F;
         f4 = 60.0F;
         f2 = 0.0F;
         adouble = this.y.m_31101_(11, this.z);

         for (int j = 0; j < 12; j++) {
            adouble2 = this.y.m_31101_(12 + j, this.z);
            f11 += Mth.a((float)j * 0.45F + f8) * 0.05F;
            this.b.f = (Mth.g((float)(adouble2[0] - adouble[0])) * 1.5F + 180.0F) * (float) (Math.PI / 180.0);
            this.b.e = f11 + (float)(adouble2[1] - adouble[1]) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
            this.b.g = Mth.g((float)(adouble2[0] - (double)f7)) * (float) (Math.PI / 180.0) * 1.5F;
            this.b.c = f3;
            this.b.d = f4;
            this.b.b = f2;
            f3 += Mth.a(this.b.e) * 10.0F;
            f4 -= Mth.b(this.b.f) * Mth.b(this.b.e) * 10.0F;
            f2 -= Mth.a(this.b.f) * Mth.b(this.b.e) * 10.0F;
            this.b.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
         }

         matrixStackIn.b();
      }

      private void a(
         PoseStack p_173977_1_,
         VertexConsumer p_173977_2_,
         int p_173977_3_,
         int p_173977_4_,
         float p_173977_5_,
         ModelPart p_173977_6_,
         ModelPart p_173977_7_,
         ModelPart p_173977_8_,
         ModelPart p_173977_9_,
         ModelPart p_173977_10_,
         ModelPart p_173977_11_,
         ModelPart p_173977_12_,
         int p_173977_13_
      ) {
         p_173977_10_.e = 1.0F + p_173977_5_ * 0.1F;
         p_173977_11_.e = 0.5F + p_173977_5_ * 0.1F;
         p_173977_12_.e = 0.75F + p_173977_5_ * 0.1F;
         p_173977_7_.e = 1.3F + p_173977_5_ * 0.1F;
         p_173977_8_.e = -0.5F - p_173977_5_ * 0.1F;
         p_173977_9_.e = 0.75F + p_173977_5_ * 0.1F;
         p_173977_6_.a(p_173977_1_, p_173977_2_, p_173977_3_, p_173977_4_, p_173977_13_);
         p_173977_7_.a(p_173977_1_, p_173977_2_, p_173977_3_, p_173977_4_, p_173977_13_);
         p_173977_10_.a(p_173977_1_, p_173977_2_, p_173977_3_, p_173977_4_, p_173977_13_);
      }
   }
}
