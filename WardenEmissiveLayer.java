import java.util.List;
import java.util.Map.Entry;
import net.minecraft.src.C_213061_;
import net.minecraft.src.C_213401_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class WardenEmissiveLayer<T extends C_213061_, M extends C_213401_<T>> extends RenderLayer<T, M> {
   private final ResourceLocation a;
   private final WardenEmissiveLayer.a<T> b;
   private final WardenEmissiveLayer.b<T, M> c;

   public WardenEmissiveLayer(
      C_4382_<T, M> p_i234884_1_, ResourceLocation p_i234884_2_, WardenEmissiveLayer.a<T> p_i234884_3_, WardenEmissiveLayer.b<T, M> p_i234884_4_
   ) {
      super(p_i234884_1_);
      this.a = p_i234884_2_;
      this.b = p_i234884_3_;
      this.c = p_i234884_4_;
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
      if (!entitylivingbaseIn.ci()) {
         if (Config.isShaders()) {
            Shaders.beginSpiderEyes();
         }

         Config.getRenderGlobal().renderOverlayEyes = true;
         this.a();
         VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.j(this.a));
         float f = this.b.apply(entitylivingbaseIn, partialTicks, ageInTicks);
         int i = C_175_.m_13660_(Mth.d(f * 255.0F), 255, 255, 255);
         this.c().a(matrixStackIn, vertexconsumer, packedLightIn, LivingEntityRenderer.c(entitylivingbaseIn, 0.0F), i);
         this.b();
         Config.getRenderGlobal().renderOverlayEyes = false;
         if (Config.isShaders()) {
            Shaders.endSpiderEyes();
         }
      }
   }

   private void a() {
      List<ModelPart> list = this.c.getPartsToDraw(this.c());
      this.c().a().e().forEach(partIn -> partIn.l = true);
      list.forEach(partIn -> partIn.l = false);
      list.forEach(partIn -> {
         for (Entry<String, ModelPart> entry : partIn.n.entrySet()) {
            if (((String)entry.getKey()).startsWith("CEM-")) {
               ((ModelPart)entry.getValue()).l = false;
            }
         }
      });
   }

   private void b() {
      this.c().a().e().forEach(partIn -> partIn.l = false);
   }

   public interface a<T extends C_213061_> {
      float apply(T var1, float var2, float var3);
   }

   public interface b<T extends C_213061_, M extends C_3819_<T>> {
      List<ModelPart> getPartsToDraw(M var1);
   }
}
