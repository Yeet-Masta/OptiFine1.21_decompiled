import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import java.util.Objects;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_1349_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1394_;
import net.minecraft.src.C_1398_;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_268388_;
import net.minecraft.src.C_2771_;
import net.minecraft.src.C_313616_;
import net.minecraft.src.C_313617_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4105_;
import net.minecraft.src.C_4462_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_470_;
import net.minecraft.src.C_520_;
import net.minecraft.src.C_524_;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.optifine.Config;
import net.optifine.CustomItems;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.joml.Matrix4f;

public class ItemInHandRenderer {
   private static final RenderType a = RenderType.d(ResourceLocation.b("textures/map/map_background.png"));
   private static final RenderType b = RenderType.d(ResourceLocation.b("textures/map/map_background_checkerboard.png"));
   private static final float c = -0.4F;
   private static final float d = 0.2F;
   private static final float e = -0.2F;
   private static final float f = -0.6F;
   private static final float g = 0.56F;
   private static final float h = -0.52F;
   private static final float i = -0.72F;
   private static final float j = 45.0F;
   private static final float k = -80.0F;
   private static final float l = -20.0F;
   private static final float m = -20.0F;
   private static final float n = 10.0F;
   private static final float o = 90.0F;
   private static final float p = 30.0F;
   private static final float q = 0.6F;
   private static final float r = -0.5F;
   private static final float s = 0.0F;
   private static final double t = 27.0;
   private static final float u = 0.8F;
   private static final float v = 0.1F;
   private static final float w = -0.3F;
   private static final float x = 0.4F;
   private static final float y = -0.4F;
   private static final float z = 70.0F;
   private static final float A = -20.0F;
   private static final float B = -0.6F;
   private static final float C = 0.8F;
   private static final float D = 0.8F;
   private static final float E = -0.75F;
   private static final float F = -0.9F;
   private static final float G = 45.0F;
   private static final float H = -1.0F;
   private static final float I = 3.6F;
   private static final float J = 3.5F;
   private static final float K = 5.6F;
   private static final int L = 200;
   private static final int M = -135;
   private static final int N = 120;
   private static final float O = -0.4F;
   private static final float P = -0.2F;
   private static final float Q = 0.0F;
   private static final float R = 0.04F;
   private static final float S = -0.72F;
   private static final float T = -1.2F;
   private static final float U = -0.5F;
   private static final float V = 45.0F;
   private static final float W = -85.0F;
   private static final float X = 45.0F;
   private static final float Y = 92.0F;
   private static final float Z = -41.0F;
   private static final float aa = 0.3F;
   private static final float ab = -1.1F;
   private static final float ac = 0.45F;
   private static final float ad = 20.0F;
   private static final float ae = 0.38F;
   private static final float af = -0.5F;
   private static final float ag = -0.5F;
   private static final float ah = 0.0F;
   private static final float ai = 0.0078125F;
   private static final int aj = 7;
   private static final int ak = 128;
   private static final int al = 128;
   private static final float am = 0.0F;
   private static final float an = 0.0F;
   private static final float ao = 0.04F;
   private static final float ap = 0.0F;
   private static final float aq = 0.004F;
   private static final float ar = 0.0F;
   private static final float as = 0.2F;
   private static final float at = 0.1F;
   private final C_3391_ au;
   private C_1391_ av = C_1391_.f_41583_;
   private C_1391_ aw = C_1391_.f_41583_;
   private float ax;
   private float ay;
   private float az;
   private float aA;
   private final EntityRenderDispatcher aB;
   private final ItemRenderer aC;
   private static boolean renderItemHand = false;

   public ItemInHandRenderer(C_3391_ mcIn, EntityRenderDispatcher renderManagerIn, ItemRenderer itemRendererIn) {
      this.au = mcIn;
      this.aB = renderManagerIn;
      this.aC = itemRendererIn;
   }

   public void a(
      C_524_ livingEntityIn,
      C_1391_ itemStackIn,
      C_268388_ contextIn,
      boolean leftHand,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int combinedLightIn
   ) {
      CustomItems.setRenderOffHand(leftHand);
      renderItemHand = true;
      if (!itemStackIn.m_41619_()) {
         this.aC
            .a(
               livingEntityIn,
               itemStackIn,
               contextIn,
               leftHand,
               matrixStackIn,
               bufferIn,
               livingEntityIn.m_9236_(),
               combinedLightIn,
               C_4474_.f_118083_,
               livingEntityIn.m_19879_() + contextIn.ordinal()
            );
      }

      renderItemHand = false;
      CustomItems.setRenderOffHand(false);
   }

   private float a(float pitch) {
      float f = 1.0F - pitch / 45.0F + 0.1F;
      f = Mth.a(f, 0.0F, 1.0F);
      return -Mth.b(f * (float) Math.PI) * 0.5F + 0.5F;
   }

   private void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, C_520_ side) {
      C_4462_ playerrenderer = (C_4462_)this.aB.a(this.au.f_91074_);
      matrixStackIn.a();
      float f = side == C_520_.RIGHT ? 1.0F : -1.0F;
      matrixStackIn.a(C_252363_.f_252436_.m_252977_(92.0F));
      matrixStackIn.a(C_252363_.f_252529_.m_252977_(45.0F));
      matrixStackIn.a(C_252363_.f_252403_.m_252977_(f * -41.0F));
      matrixStackIn.a(f * 0.3F, -1.1F, 0.45F);
      if (side == C_520_.RIGHT) {
         playerrenderer.a(matrixStackIn, bufferIn, combinedLightIn, this.au.f_91074_);
      } else {
         playerrenderer.b(matrixStackIn, bufferIn, combinedLightIn, this.au.f_91074_);
      }

      matrixStackIn.b();
   }

   private void a(
      PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, float equippedProgress, C_520_ handIn, float swingProgress, C_1391_ stack
   ) {
      float f = handIn == C_520_.RIGHT ? 1.0F : -1.0F;
      matrixStackIn.a(f * 0.125F, -0.125F, 0.0F);
      if (!this.au.f_91074_.ci()) {
         matrixStackIn.a();
         matrixStackIn.a(C_252363_.f_252403_.m_252977_(f * 10.0F));
         this.a(matrixStackIn, bufferIn, combinedLightIn, equippedProgress, swingProgress, handIn);
         matrixStackIn.b();
      }

      matrixStackIn.a();
      matrixStackIn.a(f * 0.51F, -0.08F + equippedProgress * -1.2F, -0.75F);
      float f1 = Mth.c(swingProgress);
      float f2 = Mth.a(f1 * (float) Math.PI);
      float f3 = -0.5F * f2;
      float f4 = 0.4F * Mth.a(f1 * (float) (Math.PI * 2));
      float f5 = -0.3F * Mth.a(swingProgress * (float) Math.PI);
      matrixStackIn.a(f * f3, f4 - 0.3F * f2, f5);
      matrixStackIn.a(C_252363_.f_252529_.m_252977_(f2 * -45.0F));
      matrixStackIn.a(C_252363_.f_252436_.m_252977_(f * f2 * -30.0F));
      this.a(matrixStackIn, bufferIn, combinedLightIn, stack);
      matrixStackIn.b();
   }

   private void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, float pitch, float equippedProgress, float swingProgress) {
      float f = Mth.c(swingProgress);
      float f1 = -0.2F * Mth.a(swingProgress * (float) Math.PI);
      float f2 = -0.4F * Mth.a(f * (float) Math.PI);
      matrixStackIn.a(0.0F, -f1 / 2.0F, f2);
      float f3 = this.a(pitch);
      matrixStackIn.a(0.0F, 0.04F + equippedProgress * -1.2F + f3 * -0.5F, -0.72F);
      matrixStackIn.a(C_252363_.f_252529_.m_252977_(f3 * -85.0F));
      if (!this.au.f_91074_.ci()) {
         matrixStackIn.a();
         matrixStackIn.a(C_252363_.f_252436_.m_252977_(90.0F));
         this.a(matrixStackIn, bufferIn, combinedLightIn, C_520_.RIGHT);
         this.a(matrixStackIn, bufferIn, combinedLightIn, C_520_.LEFT);
         matrixStackIn.b();
      }

      float f4 = Mth.a(f * (float) Math.PI);
      matrixStackIn.a(C_252363_.f_252529_.m_252977_(f4 * 20.0F));
      matrixStackIn.b(2.0F, 2.0F, 2.0F);
      this.a(matrixStackIn, bufferIn, combinedLightIn, this.av);
   }

   private void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, C_1391_ stack) {
      matrixStackIn.a(C_252363_.f_252436_.m_252977_(180.0F));
      matrixStackIn.a(C_252363_.f_252403_.m_252977_(180.0F));
      matrixStackIn.b(0.38F, 0.38F, 0.38F);
      matrixStackIn.a(-0.5F, -0.5F, 0.0F);
      matrixStackIn.b(0.0078125F, 0.0078125F, 0.0078125F);
      C_313617_ mapid = (C_313617_)stack.m_323252_(C_313616_.f_315230_);
      C_2771_ mapitemsaveddata = C_1398_.m_151128_(mapid, this.au.r);
      VertexConsumer vertexconsumer = bufferIn.getBuffer(mapitemsaveddata == null ? a : b);
      Matrix4f matrix4f = matrixStackIn.c().a();
      vertexconsumer.a(matrix4f, -7.0F, 135.0F, 0.0F).a(-1).a(0.0F, 1.0F).b(C_4474_.f_118083_).c(combinedLightIn).b(0.0F, 1.0F, 0.0F);
      vertexconsumer.a(matrix4f, 135.0F, 135.0F, 0.0F).a(-1).a(1.0F, 1.0F).b(C_4474_.f_118083_).c(combinedLightIn).b(0.0F, 1.0F, 0.0F);
      vertexconsumer.a(matrix4f, 135.0F, -7.0F, 0.0F).a(-1).a(1.0F, 0.0F).b(C_4474_.f_118083_).c(combinedLightIn).b(0.0F, 1.0F, 0.0F);
      vertexconsumer.a(matrix4f, -7.0F, -7.0F, 0.0F).a(-1).a(0.0F, 0.0F).b(C_4474_.f_118083_).c(combinedLightIn).b(0.0F, 1.0F, 0.0F);
      if (mapitemsaveddata != null) {
         this.au.j.i().a(matrixStackIn, bufferIn, mapid, mapitemsaveddata, false, combinedLightIn);
      }
   }

   private void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, float equippedProgress, float swingProgress, C_520_ side) {
      boolean flag = side != C_520_.LEFT;
      float f = flag ? 1.0F : -1.0F;
      float f1 = Mth.c(swingProgress);
      float f2 = -0.3F * Mth.a(f1 * (float) Math.PI);
      float f3 = 0.4F * Mth.a(f1 * (float) (Math.PI * 2));
      float f4 = -0.4F * Mth.a(swingProgress * (float) Math.PI);
      matrixStackIn.a(f * (f2 + 0.64000005F), f3 + -0.6F + equippedProgress * -0.6F, f4 + -0.71999997F);
      matrixStackIn.a(C_252363_.f_252436_.m_252977_(f * 45.0F));
      float f5 = Mth.a(swingProgress * swingProgress * (float) Math.PI);
      float f6 = Mth.a(f1 * (float) Math.PI);
      matrixStackIn.a(C_252363_.f_252436_.m_252977_(f * f6 * 70.0F));
      matrixStackIn.a(C_252363_.f_252403_.m_252977_(f * f5 * -20.0F));
      AbstractClientPlayer abstractclientplayer = this.au.f_91074_;
      matrixStackIn.a(f * -1.0F, 3.6F, 3.5F);
      matrixStackIn.a(C_252363_.f_252403_.m_252977_(f * 120.0F));
      matrixStackIn.a(C_252363_.f_252529_.m_252977_(200.0F));
      matrixStackIn.a(C_252363_.f_252436_.m_252977_(f * -135.0F));
      matrixStackIn.a(f * 5.6F, 0.0F, 0.0F);
      C_4462_ playerrenderer = (C_4462_)this.aB.a(abstractclientplayer);
      if (flag) {
         playerrenderer.a(matrixStackIn, bufferIn, combinedLightIn, abstractclientplayer);
      } else {
         playerrenderer.b(matrixStackIn, bufferIn, combinedLightIn, abstractclientplayer);
      }
   }

   private void a(PoseStack matrixStackIn, float partialTicks, C_520_ handIn, C_1391_ stack, C_1141_ playerIn) {
      float f = (float)playerIn.m_21212_() - partialTicks + 1.0F;
      float f1 = f / (float)stack.m_41779_(playerIn);
      if (f1 < 0.8F) {
         float f2 = Mth.e(Mth.b(f / 4.0F * (float) Math.PI) * 0.1F);
         matrixStackIn.a(0.0F, f2, 0.0F);
      }

      float f3 = 1.0F - (float)Math.pow((double)f1, 27.0);
      int i = handIn == C_520_.RIGHT ? 1 : -1;
      matrixStackIn.a(f3 * 0.6F * (float)i, f3 * -0.5F, f3 * 0.0F);
      matrixStackIn.a(C_252363_.f_252436_.m_252977_((float)i * f3 * 90.0F));
      matrixStackIn.a(C_252363_.f_252529_.m_252977_(f3 * 10.0F));
      matrixStackIn.a(C_252363_.f_252403_.m_252977_((float)i * f3 * 30.0F));
   }

   private void a(PoseStack matrixStackIn, float partialTicks, C_520_ handIn, C_1391_ itemStackIn, C_1141_ playerIn, float equippedProg) {
      this.b(matrixStackIn, handIn, equippedProg);
      float f = (float)(playerIn.m_21212_() % 10);
      float f1 = f - partialTicks + 1.0F;
      float f2 = 1.0F - f1 / 10.0F;
      float f3 = -90.0F;
      float f4 = 60.0F;
      float f5 = 150.0F;
      float f6 = -15.0F;
      int i = 2;
      float f7 = -15.0F + 75.0F * Mth.b(f2 * 2.0F * (float) Math.PI);
      if (handIn != C_520_.RIGHT) {
         matrixStackIn.a(0.1, 0.83, 0.35);
         matrixStackIn.a(C_252363_.f_252529_.m_252977_(-80.0F));
         matrixStackIn.a(C_252363_.f_252436_.m_252977_(-90.0F));
         matrixStackIn.a(C_252363_.f_252529_.m_252977_(f7));
         matrixStackIn.a(-0.3, 0.22, 0.35);
      } else {
         matrixStackIn.a(-0.25, 0.22, 0.35);
         matrixStackIn.a(C_252363_.f_252529_.m_252977_(-80.0F));
         matrixStackIn.a(C_252363_.f_252436_.m_252977_(90.0F));
         matrixStackIn.a(C_252363_.f_252403_.m_252977_(0.0F));
         matrixStackIn.a(C_252363_.f_252529_.m_252977_(f7));
      }
   }

   private void a(PoseStack matrixStackIn, C_520_ handIn, float swingProgress) {
      int i = handIn == C_520_.RIGHT ? 1 : -1;
      float f = Mth.a(swingProgress * swingProgress * (float) Math.PI);
      matrixStackIn.a(C_252363_.f_252436_.m_252977_((float)i * (45.0F + f * -20.0F)));
      float f1 = Mth.a(Mth.c(swingProgress) * (float) Math.PI);
      matrixStackIn.a(C_252363_.f_252403_.m_252977_((float)i * f1 * -20.0F));
      matrixStackIn.a(C_252363_.f_252529_.m_252977_(f1 * -80.0F));
      matrixStackIn.a(C_252363_.f_252436_.m_252977_((float)i * -45.0F));
   }

   private void b(PoseStack matrixStackIn, C_520_ handIn, float equippedProg) {
      int i = handIn == C_520_.RIGHT ? 1 : -1;
      matrixStackIn.a((float)i * 0.56F, -0.52F + equippedProg * -0.6F, -0.72F);
   }

   public void a(float partialTicks, PoseStack matrixStackIn, MultiBufferSource.a bufferIn, C_4105_ playerEntityIn, int combinedLightIn) {
      float f = playerEntityIn.B(partialTicks);
      C_470_ interactionhand = (C_470_)MoreObjects.firstNonNull(playerEntityIn.aK, C_470_.MAIN_HAND);
      float f1 = Mth.i(partialTicks, playerEntityIn.P, playerEntityIn.dG());
      ItemInHandRenderer.a iteminhandrenderer$handrenderselection = a(playerEntityIn);
      float f2 = Mth.i(partialTicks, playerEntityIn.f_108588_, playerEntityIn.f_108586_);
      float f3 = Mth.i(partialTicks, playerEntityIn.f_108587_, playerEntityIn.f_108585_);
      matrixStackIn.a(C_252363_.f_252529_.m_252977_((playerEntityIn.m_5686_(partialTicks) - f2) * 0.1F));
      matrixStackIn.a(C_252363_.f_252436_.m_252977_((playerEntityIn.m_5675_(partialTicks) - f3) * 0.1F));
      if (iteminhandrenderer$handrenderselection.d) {
         float f4 = interactionhand == C_470_.MAIN_HAND ? f : 0.0F;
         float f5 = 1.0F - Mth.i(partialTicks, this.ay, this.ax);
         if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists()
            || !Reflector.callBoolean(
               Reflector.ForgeHooksClient_renderSpecificFirstPersonHand,
               C_470_.MAIN_HAND,
               matrixStackIn,
               bufferIn,
               combinedLightIn,
               partialTicks,
               f1,
               f4,
               f5,
               this.av
            )) {
            this.a(playerEntityIn, partialTicks, f1, C_470_.MAIN_HAND, f4, this.av, f5, matrixStackIn, bufferIn, combinedLightIn);
         }
      }

      if (iteminhandrenderer$handrenderselection.e) {
         float f6 = interactionhand == C_470_.OFF_HAND ? f : 0.0F;
         float f7 = 1.0F - Mth.i(partialTicks, this.aA, this.az);
         if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists()
            || !Reflector.callBoolean(
               Reflector.ForgeHooksClient_renderSpecificFirstPersonHand,
               C_470_.OFF_HAND,
               matrixStackIn,
               bufferIn,
               combinedLightIn,
               partialTicks,
               f1,
               f6,
               f7,
               this.aw
            )) {
            this.a(playerEntityIn, partialTicks, f1, C_470_.OFF_HAND, f6, this.aw, f7, matrixStackIn, bufferIn, combinedLightIn);
         }
      }

      bufferIn.b();
   }

   @VisibleForTesting
   static ItemInHandRenderer.a a(C_4105_ playerIn) {
      C_1391_ itemstack = playerIn.eT();
      C_1391_ itemstack1 = playerIn.eU();
      boolean flag = itemstack.m_150930_(C_1394_.f_42411_) || itemstack1.m_150930_(C_1394_.f_42411_);
      boolean flag1 = itemstack.m_150930_(C_1394_.f_42717_) || itemstack1.m_150930_(C_1394_.f_42717_);
      if (!flag && !flag1) {
         return ItemInHandRenderer.a.a;
      } else if (playerIn.m_6117_()) {
         return b(playerIn);
      } else {
         return a(itemstack) ? ItemInHandRenderer.a.b : ItemInHandRenderer.a.a;
      }
   }

   private static ItemInHandRenderer.a b(C_4105_ playerIn) {
      C_1391_ itemstack = playerIn.ft();
      C_470_ interactionhand = playerIn.m_7655_();
      if (!itemstack.m_150930_(C_1394_.f_42411_) && !itemstack.m_150930_(C_1394_.f_42717_)) {
         return interactionhand == C_470_.MAIN_HAND && a(playerIn.eU()) ? ItemInHandRenderer.a.b : ItemInHandRenderer.a.a;
      } else {
         return ItemInHandRenderer.a.a(interactionhand);
      }
   }

   private static boolean a(C_1391_ stackIn) {
      return stackIn.m_150930_(C_1394_.f_42717_) && C_1349_.m_40932_(stackIn);
   }

   private void a(
      AbstractClientPlayer player,
      float partialTicks,
      float pitch,
      C_470_ handIn,
      float swingProgress,
      C_1391_ stack,
      float equippedProgress,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int combinedLightIn
   ) {
      if (!Config.isShaders() || !Shaders.isSkipRenderHand(handIn)) {
         if (!player.m_150108_()) {
            boolean flag = handIn == C_470_.MAIN_HAND;
            C_520_ humanoidarm = flag ? player.m_5737_() : player.m_5737_().m_20828_();
            matrixStackIn.a();
            if (stack.m_41619_()) {
               if (flag && !player.m_20145_()) {
                  this.a(matrixStackIn, bufferIn, combinedLightIn, equippedProgress, swingProgress, humanoidarm);
               }
            } else if (stack.m_41720_() instanceof C_1398_) {
               if (flag && this.aw.m_41619_()) {
                  this.a(matrixStackIn, bufferIn, combinedLightIn, pitch, equippedProgress, swingProgress);
               } else {
                  this.a(matrixStackIn, bufferIn, combinedLightIn, equippedProgress, humanoidarm, swingProgress, stack);
               }
            } else if (stack.m_41720_() instanceof C_1349_) {
               boolean flag1 = C_1349_.m_40932_(stack);
               boolean flag2 = humanoidarm == C_520_.RIGHT;
               int i = flag2 ? 1 : -1;
               if (player.m_6117_() && player.m_21212_() > 0 && player.m_7655_() == handIn) {
                  this.b(matrixStackIn, humanoidarm, equippedProgress);
                  matrixStackIn.a((float)i * -0.4785682F, -0.094387F, 0.05731531F);
                  matrixStackIn.a(C_252363_.f_252529_.m_252977_(-11.935F));
                  matrixStackIn.a(C_252363_.f_252436_.m_252977_((float)i * 65.3F));
                  matrixStackIn.a(C_252363_.f_252403_.m_252977_((float)i * -9.785F));
                  float f9 = (float)stack.m_41779_(player) - ((float)player.m_21212_() - partialTicks + 1.0F);
                  float f13 = f9 / (float)C_1349_.m_40939_(stack, player);
                  if (f13 > 1.0F) {
                     f13 = 1.0F;
                  }

                  if (f13 > 0.1F) {
                     float f16 = Mth.a((f9 - 0.1F) * 1.3F);
                     float f3 = f13 - 0.1F;
                     float f4 = f16 * f3;
                     matrixStackIn.a(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
                  }

                  matrixStackIn.a(f13 * 0.0F, f13 * 0.0F, f13 * 0.04F);
                  matrixStackIn.b(1.0F, 1.0F, 1.0F + f13 * 0.2F);
                  matrixStackIn.a(C_252363_.f_252392_.m_252977_((float)i * 45.0F));
               } else {
                  float f = -0.4F * Mth.a(Mth.c(swingProgress) * (float) Math.PI);
                  float f1 = 0.2F * Mth.a(Mth.c(swingProgress) * (float) (Math.PI * 2));
                  float f2 = -0.2F * Mth.a(swingProgress * (float) Math.PI);
                  matrixStackIn.a((float)i * f, f1, f2);
                  this.b(matrixStackIn, humanoidarm, equippedProgress);
                  this.a(matrixStackIn, humanoidarm, swingProgress);
                  if (flag1 && swingProgress < 0.001F && flag) {
                     matrixStackIn.a((float)i * -0.641864F, 0.0F, 0.0F);
                     matrixStackIn.a(C_252363_.f_252436_.m_252977_((float)i * 10.0F));
                  }
               }

               this.a(
                  player, stack, flag2 ? C_268388_.FIRST_PERSON_RIGHT_HAND : C_268388_.FIRST_PERSON_LEFT_HAND, !flag2, matrixStackIn, bufferIn, combinedLightIn
               );
            } else {
               boolean flag3 = humanoidarm == C_520_.RIGHT;
               if (!IClientItemExtensions.of(stack)
                  .applyForgeHandTransform(matrixStackIn, this.au.f_91074_, humanoidarm, stack, partialTicks, equippedProgress, swingProgress)) {
                  if (player.m_6117_() && player.m_21212_() > 0 && player.m_7655_() == handIn) {
                     int k = flag3 ? 1 : -1;
                     switch (stack.m_41780_()) {
                        case NONE:
                           this.b(matrixStackIn, humanoidarm, equippedProgress);
                           break;
                        case EAT:
                        case DRINK:
                           this.a(matrixStackIn, partialTicks, humanoidarm, stack, player);
                           this.b(matrixStackIn, humanoidarm, equippedProgress);
                           break;
                        case BLOCK:
                           this.b(matrixStackIn, humanoidarm, equippedProgress);
                           break;
                        case BOW:
                           this.b(matrixStackIn, humanoidarm, equippedProgress);
                           matrixStackIn.a((float)k * -0.2785682F, 0.18344387F, 0.15731531F);
                           matrixStackIn.a(C_252363_.f_252529_.m_252977_(-13.935F));
                           matrixStackIn.a(C_252363_.f_252436_.m_252977_((float)k * 35.3F));
                           matrixStackIn.a(C_252363_.f_252403_.m_252977_((float)k * -9.785F));
                           float f8 = (float)stack.m_41779_(player) - ((float)player.m_21212_() - partialTicks + 1.0F);
                           float f12 = f8 / 20.0F;
                           f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
                           if (f12 > 1.0F) {
                              f12 = 1.0F;
                           }

                           if (f12 > 0.1F) {
                              float f15 = Mth.a((f8 - 0.1F) * 1.3F);
                              float f18 = f12 - 0.1F;
                              float f20 = f15 * f18;
                              matrixStackIn.a(f20 * 0.0F, f20 * 0.004F, f20 * 0.0F);
                           }

                           matrixStackIn.a(f12 * 0.0F, f12 * 0.0F, f12 * 0.04F);
                           matrixStackIn.b(1.0F, 1.0F, 1.0F + f12 * 0.2F);
                           matrixStackIn.a(C_252363_.f_252392_.m_252977_((float)k * 45.0F));
                           break;
                        case SPEAR:
                           this.b(matrixStackIn, humanoidarm, equippedProgress);
                           matrixStackIn.a((float)k * -0.5F, 0.7F, 0.1F);
                           matrixStackIn.a(C_252363_.f_252529_.m_252977_(-55.0F));
                           matrixStackIn.a(C_252363_.f_252436_.m_252977_((float)k * 35.3F));
                           matrixStackIn.a(C_252363_.f_252403_.m_252977_((float)k * -9.785F));
                           float f7 = (float)stack.m_41779_(player) - ((float)player.m_21212_() - partialTicks + 1.0F);
                           float f11 = f7 / 10.0F;
                           if (f11 > 1.0F) {
                              f11 = 1.0F;
                           }

                           if (f11 > 0.1F) {
                              float f14 = Mth.a((f7 - 0.1F) * 1.3F);
                              float f17 = f11 - 0.1F;
                              float f19 = f14 * f17;
                              matrixStackIn.a(f19 * 0.0F, f19 * 0.004F, f19 * 0.0F);
                           }

                           matrixStackIn.a(0.0F, 0.0F, f11 * 0.2F);
                           matrixStackIn.b(1.0F, 1.0F, 1.0F + f11 * 0.2F);
                           matrixStackIn.a(C_252363_.f_252392_.m_252977_((float)k * 45.0F));
                           break;
                        case BRUSH:
                           this.a(matrixStackIn, partialTicks, humanoidarm, stack, player, equippedProgress);
                     }
                  } else if (player.m_21209_()) {
                     this.b(matrixStackIn, humanoidarm, equippedProgress);
                     int j = flag3 ? 1 : -1;
                     matrixStackIn.a((float)j * -0.4F, 0.8F, 0.3F);
                     matrixStackIn.a(C_252363_.f_252436_.m_252977_((float)j * 65.0F));
                     matrixStackIn.a(C_252363_.f_252403_.m_252977_((float)j * -85.0F));
                  } else {
                     float f5 = -0.4F * Mth.a(Mth.c(swingProgress) * (float) Math.PI);
                     float f6 = 0.2F * Mth.a(Mth.c(swingProgress) * (float) (Math.PI * 2));
                     float f10 = -0.2F * Mth.a(swingProgress * (float) Math.PI);
                     int l = flag3 ? 1 : -1;
                     matrixStackIn.a((float)l * f5, f6, f10);
                     this.b(matrixStackIn, humanoidarm, equippedProgress);
                     this.a(matrixStackIn, humanoidarm, swingProgress);
                  }
               }

               this.a(
                  player, stack, flag3 ? C_268388_.FIRST_PERSON_RIGHT_HAND : C_268388_.FIRST_PERSON_LEFT_HAND, !flag3, matrixStackIn, bufferIn, combinedLightIn
               );
            }

            matrixStackIn.b();
         }
      }
   }

   public void a() {
      this.ay = this.ax;
      this.aA = this.az;
      C_4105_ localplayer = this.au.f_91074_;
      C_1391_ itemstack = localplayer.eT();
      C_1391_ itemstack1 = localplayer.eU();
      if (C_1391_.m_41728_(this.av, itemstack)) {
         this.av = itemstack;
      }

      if (C_1391_.m_41728_(this.aw, itemstack1)) {
         this.aw = itemstack1;
      }

      if (localplayer.m_108637_()) {
         this.ax = Mth.a(this.ax - 0.4F, 0.0F, 1.0F);
         this.az = Mth.a(this.az - 0.4F, 0.0F, 1.0F);
      } else {
         float f = localplayer.F(1.0F);
         if (Reflector.ForgeHooksClient_shouldCauseReequipAnimation.exists()) {
            boolean requipM = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, this.av, itemstack, localplayer.fY().f_35977_);
            boolean requipO = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, this.aw, itemstack1, -1);
            if (!requipM && !Objects.equals(this.av, itemstack)) {
               this.av = itemstack;
            }

            if (!requipO && !Objects.equals(this.aw, itemstack1)) {
               this.aw = itemstack1;
            }
         }

         this.ax = this.ax + Mth.a((this.av == itemstack ? f * f * f : 0.0F) - this.ax, -0.4F, 0.4F);
         this.az = this.az + Mth.a((float)(this.aw == itemstack1 ? 1 : 0) - this.az, -0.4F, 0.4F);
      }

      if (this.ax < 0.1F) {
         this.av = itemstack;
         if (Config.isShaders()) {
            Shaders.setItemToRenderMain(this.av);
         }
      }

      if (this.az < 0.1F) {
         this.aw = itemstack1;
         if (Config.isShaders()) {
            Shaders.setItemToRenderOff(this.aw);
         }
      }
   }

   public void a(C_470_ hand) {
      if (hand == C_470_.MAIN_HAND) {
         this.ax = 0.0F;
      } else {
         this.az = 0.0F;
      }
   }

   public static boolean isRenderItemHand() {
      return renderItemHand;
   }

   @VisibleForTesting
   static enum a {
      a(true, true),
      b(true, false),
      c(false, true);

      final boolean d;
      final boolean e;

      private a(final boolean mainHandIn, final boolean offHandIn) {
         this.d = mainHandIn;
         this.e = offHandIn;
      }

      public static ItemInHandRenderer.a a(C_470_ handIn) {
         return handIn == C_470_.MAIN_HAND ? b : c;
      }
   }
}
