import net.minecraft.src.C_1144_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1394_;
import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3815_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_516_;
import net.minecraft.src.C_524_;
import net.optifine.Config;
import net.optifine.CustomItems;

public class ElytraLayer<T extends C_524_, M extends C_3819_<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation a = ResourceLocation.b("textures/entity/elytra.png");
   private final C_3815_<T> b;

   public ElytraLayer(C_4382_<T, M> parentIn, C_141653_ modelSetIn) {
      super(parentIn);
      this.b = new C_3815_(modelSetIn.a(C_141656_.f_171141_));
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
      C_1391_ itemstack = entitylivingbaseIn.m_6844_(C_516_.CHEST);
      if (this.shouldRender(itemstack, entitylivingbaseIn)) {
         ResourceLocation resourcelocation;
         if (entitylivingbaseIn instanceof AbstractClientPlayer abstractclientplayer) {
            PlayerSkin playerskin = abstractclientplayer.b();
            if (abstractclientplayer.getLocationElytra() != null) {
               resourcelocation = abstractclientplayer.getLocationElytra();
            } else if (playerskin.c() != null && abstractclientplayer.m_36170_(C_1144_.CAPE)) {
               resourcelocation = playerskin.c();
            } else {
               resourcelocation = this.getElytraTexture(itemstack, entitylivingbaseIn);
               if (Config.isCustomItems()) {
                  resourcelocation = CustomItems.getCustomElytraTexture(itemstack, resourcelocation);
               }
            }
         } else {
            resourcelocation = this.getElytraTexture(itemstack, entitylivingbaseIn);
            if (Config.isCustomItems()) {
               resourcelocation = CustomItems.getCustomElytraTexture(itemstack, resourcelocation);
            }
         }

         matrixStackIn.a();
         matrixStackIn.a(0.0F, 0.0F, 0.125F);
         this.c().m_102624_(this.b);
         this.b.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         VertexConsumer vertexconsumer = ItemRenderer.a(bufferIn, RenderType.a(resourcelocation), itemstack.m_41790_());
         this.b.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
         matrixStackIn.b();
      }
   }

   public boolean shouldRender(C_1391_ stack, T entity) {
      return stack.m_150930_(C_1394_.f_42741_);
   }

   public ResourceLocation getElytraTexture(C_1391_ stack, T entity) {
      return a;
   }
}
