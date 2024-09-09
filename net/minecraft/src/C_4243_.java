package net.minecraft.src;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.optifine.EmissiveTextures;
import net.optifine.entity.model.CustomEntityModels;

public class C_4243_ implements C_79_ {
   private Map f_112251_ = ImmutableMap.of();
   private final C_3429_ f_112253_;
   private final C_141653_ f_173556_;
   public C_1596_ f_112248_;
   public C_3373_ f_112249_;
   public C_3043_ f_112250_;
   private final Supplier f_173557_;
   private final Supplier f_234429_;
   private final Supplier f_234430_;
   public static C_1991_ tileEntityRendered;
   private C_141731_.C_141732_ context;

   public C_4243_(C_3429_ fontIn, C_141653_ modelSetIn, Supplier blockRenderDispatcherIn, Supplier itemRendererIn, Supplier entityRendererIn) {
      this.f_234429_ = itemRendererIn;
      this.f_234430_ = entityRendererIn;
      this.f_112253_ = fontIn;
      this.f_173556_ = modelSetIn;
      this.f_173557_ = blockRenderDispatcherIn;
   }

   @Nullable
   public C_4244_ m_112265_(C_1991_ tileEntityIn) {
      return (C_4244_)this.f_112251_.get(tileEntityIn.m_58903_());
   }

   public void m_173564_(C_1596_ worldIn, C_3373_ cameraIn, C_3043_ hitResultIn) {
      if (this.f_112248_ != worldIn) {
         this.m_112257_(worldIn);
      }

      this.f_112249_ = cameraIn;
      this.f_112250_ = hitResultIn;
   }

   public void m_112267_(C_1991_ tileEntityIn, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn) {
      C_4244_ blockentityrenderer = this.m_112265_(tileEntityIn);
      if (blockentityrenderer != null && tileEntityIn.m_58898_() && tileEntityIn.m_58903_().m_155262_(tileEntityIn.m_58900_()) && blockentityrenderer.m_142756_(tileEntityIn, this.f_112249_.m_90583_())) {
         m_112278_(tileEntityIn, () -> {
            m_112284_(blockentityrenderer, tileEntityIn, partialTicks, matrixStackIn, bufferIn);
         });
      }

   }

   private static void m_112284_(C_4244_ rendererIn, C_1991_ tileEntityIn, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn) {
      C_1596_ level = tileEntityIn.m_58904_();
      int i;
      if (level != null) {
         i = C_4134_.m_109541_(level, tileEntityIn.m_58899_());
      } else {
         i = 15728880;
      }

      C_1991_ tileEntityRenderedPrev = tileEntityRendered;
      tileEntityRendered = tileEntityIn;
      rendererIn = CustomEntityModels.getBlockEntityRenderer(tileEntityIn, rendererIn);
      if (EmissiveTextures.isActive()) {
         EmissiveTextures.beginRender();
      }

      rendererIn.m_6922_(tileEntityIn, partialTicks, matrixStackIn, bufferIn, i, C_4474_.f_118083_);
      if (EmissiveTextures.isActive()) {
         if (EmissiveTextures.hasEmissive()) {
            EmissiveTextures.beginRenderEmissive();
            rendererIn.m_6922_(tileEntityIn, partialTicks, matrixStackIn, bufferIn, C_4138_.MAX_BRIGHTNESS, C_4474_.f_118083_);
            EmissiveTextures.endRenderEmissive();
         }

         EmissiveTextures.endRender();
      }

      tileEntityRendered = tileEntityRenderedPrev;
   }

   public boolean m_112272_(C_1991_ tileEntityIn, C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, int combinedOverlayIn) {
      C_4244_ blockentityrenderer = this.m_112265_(tileEntityIn);
      if (blockentityrenderer == null) {
         return true;
      } else {
         m_112278_(tileEntityIn, () -> {
            C_1991_ tileEntityRenderedPrev = tileEntityRendered;
            tileEntityRendered = tileEntityIn;
            blockentityrenderer.m_6922_(tileEntityIn, 0.0F, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            tileEntityRendered = tileEntityRenderedPrev;
         });
         return false;
      }
   }

   private static void m_112278_(C_1991_ tileEntityIn, Runnable runnableIn) {
      try {
         runnableIn.run();
      } catch (Throwable var5) {
         C_4883_ crashreport = C_4883_.m_127521_(var5, "Rendering Block Entity");
         C_4909_ crashreportcategory = crashreport.m_127514_("Block Entity Details");
         tileEntityIn.m_58886_(crashreportcategory);
         throw new C_5204_(crashreport);
      }
   }

   public void m_112257_(@Nullable C_1596_ worldIn) {
      this.f_112248_ = worldIn;
      if (worldIn == null) {
         this.f_112249_ = null;
      }

   }

   public void m_6213_(C_77_ resourceManager) {
      C_141731_.C_141732_ blockentityrendererprovider$context = new C_141731_.C_141732_(this, (C_4183_)this.f_173557_.get(), (C_4354_)this.f_234429_.get(), (C_4330_)this.f_234430_.get(), this.f_173556_, this.f_112253_);
      this.context = blockentityrendererprovider$context;
      this.f_112251_ = C_141733_.m_173598_(blockentityrendererprovider$context);
   }

   public C_4244_ getRenderer(C_1992_ type) {
      return (C_4244_)this.f_112251_.get(type);
   }

   public C_141731_.C_141732_ getContext() {
      return this.context;
   }

   public Map getBlockEntityRenderMap() {
      if (this.f_112251_ instanceof ImmutableMap) {
         this.f_112251_ = new HashMap(this.f_112251_);
      }

      return this.f_112251_;
   }

   public synchronized void setSpecialRendererInternal(C_1992_ tileEntityType, C_4244_ specialRenderer) {
      this.f_112251_.put(tileEntityType, specialRenderer);
   }
}
