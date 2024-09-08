package net.minecraft.src;

import net.minecraft.src.C_141742_.C_141743_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;

public class C_4353_<T extends C_970_> extends C_4331_<T> {
   private static final C_4536_ f_115044_ = C_4536_.m_245263_("item_frame", "map=false");
   private static final C_4536_ f_115045_ = C_4536_.m_245263_("item_frame", "map=true");
   private static final C_4536_ f_174201_ = C_4536_.m_245263_("glow_item_frame", "map=false");
   private static final C_4536_ f_174202_ = C_4536_.m_245263_("glow_item_frame", "map=true");
   public static final int f_174199_ = 5;
   public static final int f_174200_ = 30;
   private final C_4354_ f_115047_;
   private final C_4183_ f_234645_;
   private static double itemRenderDistanceSq = 4096.0;
   private static boolean renderItemFrame = false;
   private static C_3391_ mc = C_3391_.m_91087_();

   public C_4353_(C_141743_ contextIn) {
      super(contextIn);
      this.f_115047_ = contextIn.m_174025_();
      this.f_234645_ = contextIn.m_234597_();
   }

   protected int m_6086_(T entityIn, C_4675_ partialTicks) {
      return entityIn.am() == C_513_.f_147033_ ? Math.max(5, super.m_6086_(entityIn, partialTicks)) : super.m_6086_(entityIn, partialTicks);
   }

   public void m_7392_(T entityIn, float entityYaw, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn) {
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      matrixStackIn.m_85836_();
      C_4687_ direction = entityIn.cH();
      C_3046_ vec3 = this.m_7860_(entityIn, partialTicks);
      matrixStackIn.m_85837_(-vec3.m_7096_(), -vec3.m_7098_(), -vec3.m_7094_());
      double d0 = 0.46875;
      matrixStackIn.m_85837_((double)direction.m_122429_() * 0.46875, (double)direction.m_122430_() * 0.46875, (double)direction.m_122431_() * 0.46875);
      matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(entityIn.dG()));
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(180.0F - entityIn.dE()));
      boolean flag = entityIn.ci();
      C_1391_ itemstack = entityIn.m_31822_();
      if (!flag) {
         C_4535_ modelmanager = this.f_234645_.m_110907_().m_110881_();
         C_4536_ modelresourcelocation = this.m_174212_(entityIn, itemstack);
         matrixStackIn.m_85836_();
         matrixStackIn.m_252880_(-0.5F, -0.5F, -0.5F);
         this.f_234645_
            .m_110937_()
            .m_111067_(
               matrixStackIn.m_85850_(),
               bufferIn.m_6299_(C_4177_.m_110789_()),
               null,
               modelmanager.m_119422_(modelresourcelocation),
               1.0F,
               1.0F,
               1.0F,
               packedLightIn,
               C_4474_.f_118083_
            );
         matrixStackIn.m_85849_();
      }

      if (!itemstack.m_41619_()) {
         C_313617_ mapid = entityIn.m_218868_(itemstack);
         if (flag) {
            matrixStackIn.m_252880_(0.0F, 0.0F, 0.5F);
         } else {
            matrixStackIn.m_252880_(0.0F, 0.0F, 0.4375F);
         }

         int j = mapid != null ? entityIn.m_31823_() % 4 * 2 : entityIn.m_31823_();
         matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_((float)j * 360.0F / 8.0F));
         if (!Reflector.postForgeBusEvent(Reflector.RenderItemInFrameEvent_Constructor, entityIn, this, matrixStackIn, bufferIn, packedLightIn)) {
            if (mapid != null) {
               matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(180.0F));
               float f = 0.0078125F;
               matrixStackIn.m_85841_(0.0078125F, 0.0078125F, 0.0078125F);
               matrixStackIn.m_252880_(-64.0F, -64.0F, 0.0F);
               C_2771_ mapitemsaveddata = C_1398_.m_151128_(mapid, entityIn.dO());
               matrixStackIn.m_252880_(0.0F, 0.0F, -1.0F);
               if (mapitemsaveddata != null) {
                  int i = this.m_174208_(entityIn, 15728850, packedLightIn);
                  C_3391_.m_91087_().f_91063_.m_109151_().m_168771_(matrixStackIn, bufferIn, mapid, mapitemsaveddata, true, i);
               }
            } else {
               int k = this.m_174208_(entityIn, 15728880, packedLightIn);
               matrixStackIn.m_85841_(0.5F, 0.5F, 0.5F);
               renderItemFrame = true;
               if (this.isRenderItem(entityIn)) {
                  this.f_115047_.m_269128_(itemstack, C_268388_.FIXED, k, C_4474_.f_118083_, matrixStackIn, bufferIn, entityIn.dO(), entityIn.an());
               }

               renderItemFrame = false;
            }
         }
      }

      matrixStackIn.m_85849_();
   }

   private int m_174208_(T itemFrameIn, int lightGlowIn, int lightIn) {
      return itemFrameIn.am() == C_513_.f_147033_ ? lightGlowIn : lightIn;
   }

   private C_4536_ m_174212_(T itemFrameIn, C_1391_ itemStackIn) {
      boolean flag = itemFrameIn.am() == C_513_.f_147033_;
      if (itemStackIn.m_41720_() instanceof C_1398_) {
         return flag ? f_174202_ : f_115045_;
      } else {
         return flag ? f_174201_ : f_115044_;
      }
   }

   public C_3046_ m_7860_(T entityIn, float partialTicks) {
      return new C_3046_((double)((float)entityIn.cH().m_122429_() * 0.3F), -0.25, (double)((float)entityIn.cH().m_122431_() * 0.3F));
   }

   public C_5265_ m_5478_(T entity) {
      return C_4484_.f_118259_;
   }

   protected boolean m_6512_(T entity) {
      if (C_3391_.m_91404_() && !entity.m_31822_().m_41619_() && entity.m_31822_().b(C_313616_.f_316016_) && this.f_114476_.f_114359_ == entity) {
         double d0 = this.f_114476_.m_114471_(entity);
         float f = entity.bZ() ? 32.0F : 64.0F;
         return d0 < (double)(f * f);
      } else {
         return false;
      }
   }

   protected void m_7649_(T entityIn, C_4996_ displayNameIn, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, float partialTicks) {
      super.m_7649_(entityIn, entityIn.m_31822_().m_41786_(), matrixStackIn, bufferIn, packedLightIn, partialTicks);
   }

   private boolean isRenderItem(C_970_ itemFrame) {
      if (Shaders.isShadowPass) {
         return false;
      } else {
         if (!Config.zoomMode) {
            C_507_ viewEntity = mc.m_91288_();
            double distSq = itemFrame.i(viewEntity.m_20185_(), viewEntity.m_20186_(), viewEntity.m_20189_());
            if (distSq > itemRenderDistanceSq) {
               return false;
            }
         }

         return true;
      }
   }

   public static void updateItemRenderDistance() {
      C_3391_ mc = C_3391_.m_91087_();
      double fov = (double)Config.limit(mc.f_91066_.m_231837_().m_231551_(), 1, 120);
      double itemRenderDistance = Math.max(6.0 * (double)mc.m_91268_().m_85444_() / fov, 16.0);
      itemRenderDistanceSq = itemRenderDistance * itemRenderDistance;
   }

   public static boolean isRenderItemFrame() {
      return renderItemFrame;
   }
}
