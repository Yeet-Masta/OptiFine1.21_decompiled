package net.minecraft.src;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import java.util.Objects;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.optifine.Config;
import net.optifine.CustomItems;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.joml.Matrix4f;

public class C_4131_ {
   private static final C_4168_ f_109297_ = C_4168_.m_110452_(C_5265_.m_340282_("textures/map/map_background.png"));
   private static final C_4168_ f_109298_ = C_4168_.m_110452_(C_5265_.m_340282_("textures/map/map_background_checkerboard.png"));
   private static final float f_172888_ = -0.4F;
   private static final float f_172889_ = 0.2F;
   private static final float f_172890_ = -0.2F;
   private static final float f_172891_ = -0.6F;
   private static final float f_172892_ = 0.56F;
   private static final float f_172893_ = -0.52F;
   private static final float f_172894_ = -0.72F;
   private static final float f_172895_ = 45.0F;
   private static final float f_172896_ = -80.0F;
   private static final float f_172897_ = -20.0F;
   private static final float f_172898_ = -20.0F;
   private static final float f_172899_ = 10.0F;
   private static final float f_172900_ = 90.0F;
   private static final float f_172901_ = 30.0F;
   private static final float f_172902_ = 0.6F;
   private static final float f_172903_ = -0.5F;
   private static final float f_172904_ = 0.0F;
   private static final double f_172905_ = 27.0;
   private static final float f_172906_ = 0.8F;
   private static final float f_172907_ = 0.1F;
   private static final float f_172908_ = -0.3F;
   private static final float f_172909_ = 0.4F;
   private static final float f_172910_ = -0.4F;
   private static final float f_172911_ = 70.0F;
   private static final float f_172842_ = -20.0F;
   private static final float f_172843_ = -0.6F;
   private static final float f_172844_ = 0.8F;
   private static final float f_172845_ = 0.8F;
   private static final float f_172846_ = -0.75F;
   private static final float f_172847_ = -0.9F;
   private static final float f_172848_ = 45.0F;
   private static final float f_172849_ = -1.0F;
   private static final float f_172850_ = 3.6F;
   private static final float f_172851_ = 3.5F;
   private static final float f_172852_ = 5.6F;
   private static final int f_172853_ = 200;
   private static final int f_172854_ = -135;
   private static final int f_172855_ = 120;
   private static final float f_172856_ = -0.4F;
   private static final float f_172857_ = -0.2F;
   private static final float f_172858_ = 0.0F;
   private static final float f_172859_ = 0.04F;
   private static final float f_172860_ = -0.72F;
   private static final float f_172861_ = -1.2F;
   private static final float f_172862_ = -0.5F;
   private static final float f_172863_ = 45.0F;
   private static final float f_172864_ = -85.0F;
   private static final float f_172865_ = 45.0F;
   private static final float f_172866_ = 92.0F;
   private static final float f_172867_ = -41.0F;
   private static final float f_172868_ = 0.3F;
   private static final float f_172869_ = -1.1F;
   private static final float f_172870_ = 0.45F;
   private static final float f_172871_ = 20.0F;
   private static final float f_172872_ = 0.38F;
   private static final float f_172873_ = -0.5F;
   private static final float f_172874_ = -0.5F;
   private static final float f_172875_ = 0.0F;
   private static final float f_172876_ = 0.0078125F;
   private static final int f_172877_ = 7;
   private static final int f_172878_ = 128;
   private static final int f_172879_ = 128;
   private static final float f_172880_ = 0.0F;
   private static final float f_172881_ = 0.0F;
   private static final float f_172882_ = 0.04F;
   private static final float f_172883_ = 0.0F;
   private static final float f_172884_ = 0.004F;
   private static final float f_172885_ = 0.0F;
   private static final float f_172886_ = 0.2F;
   private static final float f_172887_ = 0.1F;
   private final C_3391_ f_109299_;
   private C_1391_ f_109300_ = C_1391_.f_41583_;
   private C_1391_ f_109301_ = C_1391_.f_41583_;
   private float f_109302_;
   private float f_109303_;
   private float f_109304_;
   private float f_109305_;
   private final C_4330_ f_109306_;
   private final C_4354_ f_109307_;
   private static boolean renderItemHand = false;

   public C_4131_(C_3391_ mcIn, C_4330_ renderManagerIn, C_4354_ itemRendererIn) {
      this.f_109299_ = mcIn;
      this.f_109306_ = renderManagerIn;
      this.f_109307_ = itemRendererIn;
   }

   public void m_269530_(
      C_524_ livingEntityIn, C_1391_ itemStackIn, C_268388_ contextIn, boolean leftHand, C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn
   ) {
      CustomItems.setRenderOffHand(leftHand);
      renderItemHand = true;
      if (!itemStackIn.m_41619_()) {
         this.f_109307_
            .m_269491_(
               livingEntityIn,
               itemStackIn,
               contextIn,
               leftHand,
               matrixStackIn,
               bufferIn,
               livingEntityIn.dO(),
               combinedLightIn,
               C_4474_.f_118083_,
               livingEntityIn.an() + contextIn.ordinal()
            );
      }

      renderItemHand = false;
      CustomItems.setRenderOffHand(false);
   }

   private float m_109312_(float pitch) {
      float f = 1.0F - pitch / 45.0F + 0.1F;
      f = C_188_.m_14036_(f, 0.0F, 1.0F);
      return -C_188_.m_14089_(f * (float) Math.PI) * 0.5F + 0.5F;
   }

   private void m_109361_(C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, C_520_ side) {
      C_4462_ playerrenderer = (C_4462_)this.f_109306_.m_114382_(this.f_109299_.f_91074_);
      matrixStackIn.m_85836_();
      float f = side == C_520_.RIGHT ? 1.0F : -1.0F;
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(92.0F));
      matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(45.0F));
      matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(f * -41.0F));
      matrixStackIn.m_252880_(f * 0.3F, -1.1F, 0.45F);
      if (side == C_520_.RIGHT) {
         playerrenderer.m_117770_(matrixStackIn, bufferIn, combinedLightIn, this.f_109299_.f_91074_);
      } else {
         playerrenderer.m_117813_(matrixStackIn, bufferIn, combinedLightIn, this.f_109299_.f_91074_);
      }

      matrixStackIn.m_85849_();
   }

   private void m_109353_(
      C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, float equippedProgress, C_520_ handIn, float swingProgress, C_1391_ stack
   ) {
      float f = handIn == C_520_.RIGHT ? 1.0F : -1.0F;
      matrixStackIn.m_252880_(f * 0.125F, -0.125F, 0.0F);
      if (!this.f_109299_.f_91074_.ci()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(f * 10.0F));
         this.m_109346_(matrixStackIn, bufferIn, combinedLightIn, equippedProgress, swingProgress, handIn);
         matrixStackIn.m_85849_();
      }

      matrixStackIn.m_85836_();
      matrixStackIn.m_252880_(f * 0.51F, -0.08F + equippedProgress * -1.2F, -0.75F);
      float f1 = C_188_.m_14116_(swingProgress);
      float f2 = C_188_.m_14031_(f1 * (float) Math.PI);
      float f3 = -0.5F * f2;
      float f4 = 0.4F * C_188_.m_14031_(f1 * (float) (Math.PI * 2));
      float f5 = -0.3F * C_188_.m_14031_(swingProgress * (float) Math.PI);
      matrixStackIn.m_252880_(f * f3, f4 - 0.3F * f2, f5);
      matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(f2 * -45.0F));
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(f * f2 * -30.0F));
      this.m_109366_(matrixStackIn, bufferIn, combinedLightIn, stack);
      matrixStackIn.m_85849_();
   }

   private void m_109339_(C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, float pitch, float equippedProgress, float swingProgress) {
      float f = C_188_.m_14116_(swingProgress);
      float f1 = -0.2F * C_188_.m_14031_(swingProgress * (float) Math.PI);
      float f2 = -0.4F * C_188_.m_14031_(f * (float) Math.PI);
      matrixStackIn.m_252880_(0.0F, -f1 / 2.0F, f2);
      float f3 = this.m_109312_(pitch);
      matrixStackIn.m_252880_(0.0F, 0.04F + equippedProgress * -1.2F + f3 * -0.5F, -0.72F);
      matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(f3 * -85.0F));
      if (!this.f_109299_.f_91074_.ci()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(90.0F));
         this.m_109361_(matrixStackIn, bufferIn, combinedLightIn, C_520_.RIGHT);
         this.m_109361_(matrixStackIn, bufferIn, combinedLightIn, C_520_.LEFT);
         matrixStackIn.m_85849_();
      }

      float f4 = C_188_.m_14031_(f * (float) Math.PI);
      matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(f4 * 20.0F));
      matrixStackIn.m_85841_(2.0F, 2.0F, 2.0F);
      this.m_109366_(matrixStackIn, bufferIn, combinedLightIn, this.f_109300_);
   }

   private void m_109366_(C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, C_1391_ stack) {
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(180.0F));
      matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(180.0F));
      matrixStackIn.m_85841_(0.38F, 0.38F, 0.38F);
      matrixStackIn.m_252880_(-0.5F, -0.5F, 0.0F);
      matrixStackIn.m_85841_(0.0078125F, 0.0078125F, 0.0078125F);
      C_313617_ mapid = (C_313617_)stack.a(C_313616_.f_315230_);
      C_2771_ mapitemsaveddata = C_1398_.m_151128_(mapid, this.f_109299_.f_91073_);
      C_3187_ vertexconsumer = bufferIn.m_6299_(mapitemsaveddata == null ? f_109297_ : f_109298_);
      Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
      vertexconsumer.m_339083_(matrix4f, -7.0F, 135.0F, 0.0F)
         .m_338399_(-1)
         .m_167083_(0.0F, 1.0F)
         .m_338943_(C_4474_.f_118083_)
         .m_338973_(combinedLightIn)
         .m_338525_(0.0F, 1.0F, 0.0F);
      vertexconsumer.m_339083_(matrix4f, 135.0F, 135.0F, 0.0F)
         .m_338399_(-1)
         .m_167083_(1.0F, 1.0F)
         .m_338943_(C_4474_.f_118083_)
         .m_338973_(combinedLightIn)
         .m_338525_(0.0F, 1.0F, 0.0F);
      vertexconsumer.m_339083_(matrix4f, 135.0F, -7.0F, 0.0F)
         .m_338399_(-1)
         .m_167083_(1.0F, 0.0F)
         .m_338943_(C_4474_.f_118083_)
         .m_338973_(combinedLightIn)
         .m_338525_(0.0F, 1.0F, 0.0F);
      vertexconsumer.m_339083_(matrix4f, -7.0F, -7.0F, 0.0F)
         .m_338399_(-1)
         .m_167083_(0.0F, 0.0F)
         .m_338943_(C_4474_.f_118083_)
         .m_338973_(combinedLightIn)
         .m_338525_(0.0F, 1.0F, 0.0F);
      if (mapitemsaveddata != null) {
         this.f_109299_.f_91063_.m_109151_().m_168771_(matrixStackIn, bufferIn, mapid, mapitemsaveddata, false, combinedLightIn);
      }
   }

   private void m_109346_(C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, float equippedProgress, float swingProgress, C_520_ side) {
      boolean flag = side != C_520_.LEFT;
      float f = flag ? 1.0F : -1.0F;
      float f1 = C_188_.m_14116_(swingProgress);
      float f2 = -0.3F * C_188_.m_14031_(f1 * (float) Math.PI);
      float f3 = 0.4F * C_188_.m_14031_(f1 * (float) (Math.PI * 2));
      float f4 = -0.4F * C_188_.m_14031_(swingProgress * (float) Math.PI);
      matrixStackIn.m_252880_(f * (f2 + 0.64000005F), f3 + -0.6F + equippedProgress * -0.6F, f4 + -0.71999997F);
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(f * 45.0F));
      float f5 = C_188_.m_14031_(swingProgress * swingProgress * (float) Math.PI);
      float f6 = C_188_.m_14031_(f1 * (float) Math.PI);
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(f * f6 * 70.0F));
      matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(f * f5 * -20.0F));
      C_4102_ abstractclientplayer = this.f_109299_.f_91074_;
      matrixStackIn.m_252880_(f * -1.0F, 3.6F, 3.5F);
      matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(f * 120.0F));
      matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(200.0F));
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(f * -135.0F));
      matrixStackIn.m_252880_(f * 5.6F, 0.0F, 0.0F);
      C_4462_ playerrenderer = (C_4462_)this.f_109306_.m_114382_(abstractclientplayer);
      if (flag) {
         playerrenderer.m_117770_(matrixStackIn, bufferIn, combinedLightIn, abstractclientplayer);
      } else {
         playerrenderer.m_117813_(matrixStackIn, bufferIn, combinedLightIn, abstractclientplayer);
      }
   }

   private void m_109330_(C_3181_ matrixStackIn, float partialTicks, C_520_ handIn, C_1391_ stack, C_1141_ playerIn) {
      float f = (float)playerIn.fu() - partialTicks + 1.0F;
      float f1 = f / (float)stack.m_41779_(playerIn);
      if (f1 < 0.8F) {
         float f2 = C_188_.m_14154_(C_188_.m_14089_(f / 4.0F * (float) Math.PI) * 0.1F);
         matrixStackIn.m_252880_(0.0F, f2, 0.0F);
      }

      float f3 = 1.0F - (float)Math.pow((double)f1, 27.0);
      int i = handIn == C_520_.RIGHT ? 1 : -1;
      matrixStackIn.m_252880_(f3 * 0.6F * (float)i, f3 * -0.5F, f3 * 0.0F);
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_((float)i * f3 * 90.0F));
      matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(f3 * 10.0F));
      matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_((float)i * f3 * 30.0F));
   }

   private void m_271982_(C_3181_ matrixStackIn, float partialTicks, C_520_ handIn, C_1391_ itemStackIn, C_1141_ playerIn, float equippedProg) {
      this.m_109382_(matrixStackIn, handIn, equippedProg);
      float f = (float)(playerIn.fu() % 10);
      float f1 = f - partialTicks + 1.0F;
      float f2 = 1.0F - f1 / 10.0F;
      float f3 = -90.0F;
      float f4 = 60.0F;
      float f5 = 150.0F;
      float f6 = -15.0F;
      int i = 2;
      float f7 = -15.0F + 75.0F * C_188_.m_14089_(f2 * 2.0F * (float) Math.PI);
      if (handIn != C_520_.RIGHT) {
         matrixStackIn.m_85837_(0.1, 0.83, 0.35);
         matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(-80.0F));
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(-90.0F));
         matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(f7));
         matrixStackIn.m_85837_(-0.3, 0.22, 0.35);
      } else {
         matrixStackIn.m_85837_(-0.25, 0.22, 0.35);
         matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(-80.0F));
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(90.0F));
         matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(0.0F));
         matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(f7));
      }
   }

   private void m_109335_(C_3181_ matrixStackIn, C_520_ handIn, float swingProgress) {
      int i = handIn == C_520_.RIGHT ? 1 : -1;
      float f = C_188_.m_14031_(swingProgress * swingProgress * (float) Math.PI);
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_((float)i * (45.0F + f * -20.0F)));
      float f1 = C_188_.m_14031_(C_188_.m_14116_(swingProgress) * (float) Math.PI);
      matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_((float)i * f1 * -20.0F));
      matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(f1 * -80.0F));
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_((float)i * -45.0F));
   }

   private void m_109382_(C_3181_ matrixStackIn, C_520_ handIn, float equippedProg) {
      int i = handIn == C_520_.RIGHT ? 1 : -1;
      matrixStackIn.m_252880_((float)i * 0.56F, -0.52F + equippedProg * -0.6F, -0.72F);
   }

   public void m_109314_(float partialTicks, C_3181_ matrixStackIn, C_4139_.C_4140_ bufferIn, C_4105_ playerEntityIn, int combinedLightIn) {
      float f = playerEntityIn.B(partialTicks);
      C_470_ interactionhand = (C_470_)MoreObjects.firstNonNull(playerEntityIn.aK, C_470_.MAIN_HAND);
      float f1 = C_188_.m_14179_(partialTicks, playerEntityIn.P, playerEntityIn.dG());
      C_4131_.C_141715_ iteminhandrenderer$handrenderselection = m_172914_(playerEntityIn);
      float f2 = C_188_.m_14179_(partialTicks, playerEntityIn.f_108588_, playerEntityIn.f_108586_);
      float f3 = C_188_.m_14179_(partialTicks, playerEntityIn.f_108587_, playerEntityIn.f_108585_);
      matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_((playerEntityIn.m_5686_(partialTicks) - f2) * 0.1F));
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_((playerEntityIn.m_5675_(partialTicks) - f3) * 0.1F));
      if (iteminhandrenderer$handrenderselection.f_172921_) {
         float f4 = interactionhand == C_470_.MAIN_HAND ? f : 0.0F;
         float f5 = 1.0F - C_188_.m_14179_(partialTicks, this.f_109303_, this.f_109302_);
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
               this.f_109300_
            )) {
            this.m_109371_(playerEntityIn, partialTicks, f1, C_470_.MAIN_HAND, f4, this.f_109300_, f5, matrixStackIn, bufferIn, combinedLightIn);
         }
      }

      if (iteminhandrenderer$handrenderselection.f_172922_) {
         float f6 = interactionhand == C_470_.OFF_HAND ? f : 0.0F;
         float f7 = 1.0F - C_188_.m_14179_(partialTicks, this.f_109305_, this.f_109304_);
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
               this.f_109301_
            )) {
            this.m_109371_(playerEntityIn, partialTicks, f1, C_470_.OFF_HAND, f6, this.f_109301_, f7, matrixStackIn, bufferIn, combinedLightIn);
         }
      }

      bufferIn.m_109911_();
   }

   @VisibleForTesting
   static C_4131_.C_141715_ m_172914_(C_4105_ playerIn) {
      C_1391_ itemstack = playerIn.eT();
      C_1391_ itemstack1 = playerIn.eU();
      boolean flag = itemstack.m_150930_(C_1394_.f_42411_) || itemstack1.m_150930_(C_1394_.f_42411_);
      boolean flag1 = itemstack.m_150930_(C_1394_.f_42717_) || itemstack1.m_150930_(C_1394_.f_42717_);
      if (!flag && !flag1) {
         return C_4131_.C_141715_.RENDER_BOTH_HANDS;
      } else if (playerIn.m_6117_()) {
         return m_172916_(playerIn);
      } else {
         return m_172912_(itemstack) ? C_4131_.C_141715_.RENDER_MAIN_HAND_ONLY : C_4131_.C_141715_.RENDER_BOTH_HANDS;
      }
   }

   private static C_4131_.C_141715_ m_172916_(C_4105_ playerIn) {
      C_1391_ itemstack = playerIn.ft();
      C_470_ interactionhand = playerIn.m_7655_();
      if (!itemstack.m_150930_(C_1394_.f_42411_) && !itemstack.m_150930_(C_1394_.f_42717_)) {
         return interactionhand == C_470_.MAIN_HAND && m_172912_(playerIn.eU()) ? C_4131_.C_141715_.RENDER_MAIN_HAND_ONLY : C_4131_.C_141715_.RENDER_BOTH_HANDS;
      } else {
         return C_4131_.C_141715_.m_172931_(interactionhand);
      }
   }

   private static boolean m_172912_(C_1391_ stackIn) {
      return stackIn.m_150930_(C_1394_.f_42717_) && C_1349_.m_40932_(stackIn);
   }

   private void m_109371_(
      C_4102_ player,
      float partialTicks,
      float pitch,
      C_470_ handIn,
      float swingProgress,
      C_1391_ stack,
      float equippedProgress,
      C_3181_ matrixStackIn,
      C_4139_ bufferIn,
      int combinedLightIn
   ) {
      if (!Config.isShaders() || !Shaders.isSkipRenderHand(handIn)) {
         if (!player.m_150108_()) {
            boolean flag = handIn == C_470_.MAIN_HAND;
            C_520_ humanoidarm = flag ? player.m_5737_() : player.m_5737_().m_20828_();
            matrixStackIn.m_85836_();
            if (stack.m_41619_()) {
               if (flag && !player.ci()) {
                  this.m_109346_(matrixStackIn, bufferIn, combinedLightIn, equippedProgress, swingProgress, humanoidarm);
               }
            } else if (stack.m_41720_() instanceof C_1398_) {
               if (flag && this.f_109301_.m_41619_()) {
                  this.m_109339_(matrixStackIn, bufferIn, combinedLightIn, pitch, equippedProgress, swingProgress);
               } else {
                  this.m_109353_(matrixStackIn, bufferIn, combinedLightIn, equippedProgress, humanoidarm, swingProgress, stack);
               }
            } else if (stack.m_41720_() instanceof C_1349_) {
               boolean flag1 = C_1349_.m_40932_(stack);
               boolean flag2 = humanoidarm == C_520_.RIGHT;
               int i = flag2 ? 1 : -1;
               if (player.fr() && player.fu() > 0 && player.fs() == handIn) {
                  this.m_109382_(matrixStackIn, humanoidarm, equippedProgress);
                  matrixStackIn.m_252880_((float)i * -0.4785682F, -0.094387F, 0.05731531F);
                  matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(-11.935F));
                  matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_((float)i * 65.3F));
                  matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_((float)i * -9.785F));
                  float f9 = (float)stack.m_41779_(player) - ((float)player.fu() - partialTicks + 1.0F);
                  float f13 = f9 / (float)C_1349_.m_40939_(stack, player);
                  if (f13 > 1.0F) {
                     f13 = 1.0F;
                  }

                  if (f13 > 0.1F) {
                     float f16 = C_188_.m_14031_((f9 - 0.1F) * 1.3F);
                     float f3 = f13 - 0.1F;
                     float f4 = f16 * f3;
                     matrixStackIn.m_252880_(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
                  }

                  matrixStackIn.m_252880_(f13 * 0.0F, f13 * 0.0F, f13 * 0.04F);
                  matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F + f13 * 0.2F);
                  matrixStackIn.m_252781_(C_252363_.f_252392_.m_252977_((float)i * 45.0F));
               } else {
                  float f = -0.4F * C_188_.m_14031_(C_188_.m_14116_(swingProgress) * (float) Math.PI);
                  float f1 = 0.2F * C_188_.m_14031_(C_188_.m_14116_(swingProgress) * (float) (Math.PI * 2));
                  float f2 = -0.2F * C_188_.m_14031_(swingProgress * (float) Math.PI);
                  matrixStackIn.m_252880_((float)i * f, f1, f2);
                  this.m_109382_(matrixStackIn, humanoidarm, equippedProgress);
                  this.m_109335_(matrixStackIn, humanoidarm, swingProgress);
                  if (flag1 && swingProgress < 0.001F && flag) {
                     matrixStackIn.m_252880_((float)i * -0.641864F, 0.0F, 0.0F);
                     matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_((float)i * 10.0F));
                  }
               }

               this.m_269530_(
                  player, stack, flag2 ? C_268388_.FIRST_PERSON_RIGHT_HAND : C_268388_.FIRST_PERSON_LEFT_HAND, !flag2, matrixStackIn, bufferIn, combinedLightIn
               );
            } else {
               boolean flag3 = humanoidarm == C_520_.RIGHT;
               if (!IClientItemExtensions.of(stack)
                  .applyForgeHandTransform(matrixStackIn, this.f_109299_.f_91074_, humanoidarm, stack, partialTicks, equippedProgress, swingProgress)) {
                  if (player.fr() && player.fu() > 0 && player.fs() == handIn) {
                     int k = flag3 ? 1 : -1;
                     switch (stack.m_41780_()) {
                        case NONE:
                           this.m_109382_(matrixStackIn, humanoidarm, equippedProgress);
                           break;
                        case EAT:
                        case DRINK:
                           this.m_109330_(matrixStackIn, partialTicks, humanoidarm, stack, player);
                           this.m_109382_(matrixStackIn, humanoidarm, equippedProgress);
                           break;
                        case BLOCK:
                           this.m_109382_(matrixStackIn, humanoidarm, equippedProgress);
                           break;
                        case BOW:
                           this.m_109382_(matrixStackIn, humanoidarm, equippedProgress);
                           matrixStackIn.m_252880_((float)k * -0.2785682F, 0.18344387F, 0.15731531F);
                           matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(-13.935F));
                           matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_((float)k * 35.3F));
                           matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_((float)k * -9.785F));
                           float f8 = (float)stack.m_41779_(player) - ((float)player.fu() - partialTicks + 1.0F);
                           float f12 = f8 / 20.0F;
                           f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
                           if (f12 > 1.0F) {
                              f12 = 1.0F;
                           }

                           if (f12 > 0.1F) {
                              float f15 = C_188_.m_14031_((f8 - 0.1F) * 1.3F);
                              float f18 = f12 - 0.1F;
                              float f20 = f15 * f18;
                              matrixStackIn.m_252880_(f20 * 0.0F, f20 * 0.004F, f20 * 0.0F);
                           }

                           matrixStackIn.m_252880_(f12 * 0.0F, f12 * 0.0F, f12 * 0.04F);
                           matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F + f12 * 0.2F);
                           matrixStackIn.m_252781_(C_252363_.f_252392_.m_252977_((float)k * 45.0F));
                           break;
                        case SPEAR:
                           this.m_109382_(matrixStackIn, humanoidarm, equippedProgress);
                           matrixStackIn.m_252880_((float)k * -0.5F, 0.7F, 0.1F);
                           matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(-55.0F));
                           matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_((float)k * 35.3F));
                           matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_((float)k * -9.785F));
                           float f7 = (float)stack.m_41779_(player) - ((float)player.fu() - partialTicks + 1.0F);
                           float f11 = f7 / 10.0F;
                           if (f11 > 1.0F) {
                              f11 = 1.0F;
                           }

                           if (f11 > 0.1F) {
                              float f14 = C_188_.m_14031_((f7 - 0.1F) * 1.3F);
                              float f17 = f11 - 0.1F;
                              float f19 = f14 * f17;
                              matrixStackIn.m_252880_(f19 * 0.0F, f19 * 0.004F, f19 * 0.0F);
                           }

                           matrixStackIn.m_252880_(0.0F, 0.0F, f11 * 0.2F);
                           matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F + f11 * 0.2F);
                           matrixStackIn.m_252781_(C_252363_.f_252392_.m_252977_((float)k * 45.0F));
                           break;
                        case BRUSH:
                           this.m_271982_(matrixStackIn, partialTicks, humanoidarm, stack, player, equippedProgress);
                     }
                  } else if (player.fn()) {
                     this.m_109382_(matrixStackIn, humanoidarm, equippedProgress);
                     int j = flag3 ? 1 : -1;
                     matrixStackIn.m_252880_((float)j * -0.4F, 0.8F, 0.3F);
                     matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_((float)j * 65.0F));
                     matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_((float)j * -85.0F));
                  } else {
                     float f5 = -0.4F * C_188_.m_14031_(C_188_.m_14116_(swingProgress) * (float) Math.PI);
                     float f6 = 0.2F * C_188_.m_14031_(C_188_.m_14116_(swingProgress) * (float) (Math.PI * 2));
                     float f10 = -0.2F * C_188_.m_14031_(swingProgress * (float) Math.PI);
                     int l = flag3 ? 1 : -1;
                     matrixStackIn.m_252880_((float)l * f5, f6, f10);
                     this.m_109382_(matrixStackIn, humanoidarm, equippedProgress);
                     this.m_109335_(matrixStackIn, humanoidarm, swingProgress);
                  }
               }

               this.m_269530_(
                  player, stack, flag3 ? C_268388_.FIRST_PERSON_RIGHT_HAND : C_268388_.FIRST_PERSON_LEFT_HAND, !flag3, matrixStackIn, bufferIn, combinedLightIn
               );
            }

            matrixStackIn.m_85849_();
         }
      }
   }

   public void m_109311_() {
      this.f_109303_ = this.f_109302_;
      this.f_109305_ = this.f_109304_;
      C_4105_ localplayer = this.f_109299_.f_91074_;
      C_1391_ itemstack = localplayer.eT();
      C_1391_ itemstack1 = localplayer.eU();
      if (C_1391_.m_41728_(this.f_109300_, itemstack)) {
         this.f_109300_ = itemstack;
      }

      if (C_1391_.m_41728_(this.f_109301_, itemstack1)) {
         this.f_109301_ = itemstack1;
      }

      if (localplayer.m_108637_()) {
         this.f_109302_ = C_188_.m_14036_(this.f_109302_ - 0.4F, 0.0F, 1.0F);
         this.f_109304_ = C_188_.m_14036_(this.f_109304_ - 0.4F, 0.0F, 1.0F);
      } else {
         float f = localplayer.F(1.0F);
         if (Reflector.ForgeHooksClient_shouldCauseReequipAnimation.exists()) {
            boolean requipM = Reflector.callBoolean(
               Reflector.ForgeHooksClient_shouldCauseReequipAnimation, this.f_109300_, itemstack, localplayer.fY().f_35977_
            );
            boolean requipO = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, this.f_109301_, itemstack1, -1);
            if (!requipM && !Objects.equals(this.f_109300_, itemstack)) {
               this.f_109300_ = itemstack;
            }

            if (!requipO && !Objects.equals(this.f_109301_, itemstack1)) {
               this.f_109301_ = itemstack1;
            }
         }

         this.f_109302_ = this.f_109302_ + C_188_.m_14036_((this.f_109300_ == itemstack ? f * f * f : 0.0F) - this.f_109302_, -0.4F, 0.4F);
         this.f_109304_ = this.f_109304_ + C_188_.m_14036_((float)(this.f_109301_ == itemstack1 ? 1 : 0) - this.f_109304_, -0.4F, 0.4F);
      }

      if (this.f_109302_ < 0.1F) {
         this.f_109300_ = itemstack;
         if (Config.isShaders()) {
            Shaders.setItemToRenderMain(this.f_109300_);
         }
      }

      if (this.f_109304_ < 0.1F) {
         this.f_109301_ = itemstack1;
         if (Config.isShaders()) {
            Shaders.setItemToRenderOff(this.f_109301_);
         }
      }
   }

   public void m_109320_(C_470_ hand) {
      if (hand == C_470_.MAIN_HAND) {
         this.f_109302_ = 0.0F;
      } else {
         this.f_109304_ = 0.0F;
      }
   }

   public static boolean isRenderItemHand() {
      return renderItemHand;
   }

   @VisibleForTesting
   static enum C_141715_ {
      RENDER_BOTH_HANDS(true, true),
      RENDER_MAIN_HAND_ONLY(true, false),
      RENDER_OFF_HAND_ONLY(false, true);

      final boolean f_172921_;
      final boolean f_172922_;

      private C_141715_(final boolean mainHandIn, final boolean offHandIn) {
         this.f_172921_ = mainHandIn;
         this.f_172922_ = offHandIn;
      }

      public static C_4131_.C_141715_ m_172931_(C_470_ handIn) {
         return handIn == C_470_.MAIN_HAND ? RENDER_MAIN_HAND_ONLY : RENDER_OFF_HAND_ONLY;
      }
   }
}
