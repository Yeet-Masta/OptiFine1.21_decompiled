import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1398_;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_268388_;
import net.minecraft.src.C_2771_;
import net.minecraft.src.C_313616_;
import net.minecraft.src.C_313617_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4177_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4535_;
import net.minecraft.src.C_4536_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_970_;
import net.minecraft.src.C_141742_.C_141743_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;

public class ItemFrameRenderer<T extends C_970_> extends EntityRenderer<T> {
   private static final C_4536_ h = C_4536_.m_245263_("item_frame", "map=false");
   private static final C_4536_ i = C_4536_.m_245263_("item_frame", "map=true");
   private static final C_4536_ j = C_4536_.m_245263_("glow_item_frame", "map=false");
   private static final C_4536_ k = C_4536_.m_245263_("glow_item_frame", "map=true");
   public static final int a = 5;
   public static final int g = 30;
   private final ItemRenderer l;
   private final BlockRenderDispatcher m;
   private static double itemRenderDistanceSq = 4096.0;
   private static boolean renderItemFrame = false;
   private static C_3391_ mc = C_3391_.m_91087_();

   public ItemFrameRenderer(C_141743_ contextIn) {
      super(contextIn);
      this.l = contextIn.b();
      this.m = contextIn.c();
   }

   protected int a(T entityIn, C_4675_ partialTicks) {
      return entityIn.m_6095_() == C_513_.f_147033_ ? Math.max(5, super.a(entityIn, partialTicks)) : super.a(entityIn, partialTicks);
   }

   public void a(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.a(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      matrixStackIn.a();
      Direction direction = entityIn.cH();
      Vec3 vec3 = this.a(entityIn, partialTicks);
      matrixStackIn.a(-vec3.m_7096_(), -vec3.m_7098_(), -vec3.m_7094_());
      double d0 = 0.46875;
      matrixStackIn.a((double)direction.j() * 0.46875, (double)direction.k() * 0.46875, (double)direction.l() * 0.46875);
      matrixStackIn.a(C_252363_.f_252529_.m_252977_(entityIn.m_146909_()));
      matrixStackIn.a(C_252363_.f_252436_.m_252977_(180.0F - entityIn.m_146908_()));
      boolean flag = entityIn.m_20145_();
      C_1391_ itemstack = entityIn.m_31822_();
      if (!flag) {
         C_4535_ modelmanager = this.m.a().m_110881_();
         C_4536_ modelresourcelocation = this.a(entityIn, itemstack);
         matrixStackIn.a();
         matrixStackIn.a(-0.5F, -0.5F, -0.5F);
         this.m
            .b()
            .a(
               matrixStackIn.c(),
               bufferIn.getBuffer(C_4177_.h()),
               null,
               modelmanager.a(modelresourcelocation),
               1.0F,
               1.0F,
               1.0F,
               packedLightIn,
               C_4474_.f_118083_
            );
         matrixStackIn.b();
      }

      if (!itemstack.m_41619_()) {
         C_313617_ mapid = entityIn.m_218868_(itemstack);
         if (flag) {
            matrixStackIn.a(0.0F, 0.0F, 0.5F);
         } else {
            matrixStackIn.a(0.0F, 0.0F, 0.4375F);
         }

         int j = mapid != null ? entityIn.m_31823_() % 4 * 2 : entityIn.m_31823_();
         matrixStackIn.a(C_252363_.f_252403_.m_252977_((float)j * 360.0F / 8.0F));
         if (!Reflector.postForgeBusEvent(Reflector.RenderItemInFrameEvent_Constructor, entityIn, this, matrixStackIn, bufferIn, packedLightIn)) {
            if (mapid != null) {
               matrixStackIn.a(C_252363_.f_252403_.m_252977_(180.0F));
               float f = 0.0078125F;
               matrixStackIn.b(0.0078125F, 0.0078125F, 0.0078125F);
               matrixStackIn.a(-64.0F, -64.0F, 0.0F);
               C_2771_ mapitemsaveddata = C_1398_.m_151128_(mapid, entityIn.m_9236_());
               matrixStackIn.a(0.0F, 0.0F, -1.0F);
               if (mapitemsaveddata != null) {
                  int i = this.a(entityIn, 15728850, packedLightIn);
                  C_3391_.m_91087_().j.i().a(matrixStackIn, bufferIn, mapid, mapitemsaveddata, true, i);
               }
            } else {
               int k = this.a(entityIn, 15728880, packedLightIn);
               matrixStackIn.b(0.5F, 0.5F, 0.5F);
               renderItemFrame = true;
               if (this.isRenderItem(entityIn)) {
                  this.l.a(itemstack, C_268388_.FIXED, k, C_4474_.f_118083_, matrixStackIn, bufferIn, entityIn.m_9236_(), entityIn.m_19879_());
               }

               renderItemFrame = false;
            }
         }
      }

      matrixStackIn.b();
   }

   private int a(T itemFrameIn, int lightGlowIn, int lightIn) {
      return itemFrameIn.m_6095_() == C_513_.f_147033_ ? lightGlowIn : lightIn;
   }

   private C_4536_ a(T itemFrameIn, C_1391_ itemStackIn) {
      boolean flag = itemFrameIn.m_6095_() == C_513_.f_147033_;
      if (itemStackIn.m_41720_() instanceof C_1398_) {
         return flag ? k : i;
      } else {
         return flag ? j : h;
      }
   }

   public Vec3 a(T entityIn, float partialTicks) {
      return new Vec3((double)((float)entityIn.cH().j() * 0.3F), -0.25, (double)((float)entityIn.cH().l() * 0.3F));
   }

   public ResourceLocation a(T entity) {
      return TextureAtlas.e;
   }

   protected boolean b(T entity) {
      if (C_3391_.m_91404_() && !entity.m_31822_().m_41619_() && entity.m_31822_().m_319951_(C_313616_.f_316016_) && this.d.c == entity) {
         double d0 = this.d.b(entity);
         float f = entity.m_20163_() ? 32.0F : 64.0F;
         return d0 < (double)(f * f);
      } else {
         return false;
      }
   }

   protected void a(T entityIn, C_4996_ displayNameIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, float partialTicks) {
      super.a(entityIn, entityIn.m_31822_().m_41786_(), matrixStackIn, bufferIn, packedLightIn, partialTicks);
   }

   private boolean isRenderItem(C_970_ itemFrame) {
      if (Shaders.isShadowPass) {
         return false;
      } else {
         if (!Config.zoomMode) {
            C_507_ viewEntity = mc.m_91288_();
            double distSq = itemFrame.m_20275_(viewEntity.m_20185_(), viewEntity.m_20186_(), viewEntity.m_20189_());
            if (distSq > itemRenderDistanceSq) {
               return false;
            }
         }

         return true;
      }
   }

   public static void updateItemRenderDistance() {
      C_3391_ mc = C_3391_.m_91087_();
      double fov = (double)Config.limit(mc.m.ah().c(), 1, 120);
      double itemRenderDistance = Math.max(6.0 * (double)mc.aM().o() / fov, 16.0);
      itemRenderDistanceSq = itemRenderDistance * itemRenderDistance;
   }

   public static boolean isRenderItemFrame() {
      return renderItemFrame;
   }
}
