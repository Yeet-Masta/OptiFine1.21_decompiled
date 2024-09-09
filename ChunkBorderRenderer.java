import net.minecraft.src.C_3391_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_174_.C_175_;
import net.minecraft.src.C_4287_.C_4288_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import org.joml.Matrix4f;

public class ChunkBorderRenderer implements C_4288_ {
   private final C_3391_ a;
   private static final int b = C_175_.m_13660_(255, 0, 155, 155);
   private static final int c = C_175_.m_13660_(255, 255, 255, 0);

   public ChunkBorderRenderer(C_3391_ minecraftIn) {
      this.a = minecraftIn;
   }

   public void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, double camX, double camY, double camZ) {
      if (!Shaders.isShadowPass) {
         if (Config.isShaders()) {
            Shaders.beginLeash();
         }

         C_507_ entity = this.a.j.l().g();
         float f = (float)((double)this.a.r.m_141937_() - camY);
         float f1 = (float)((double)this.a.r.m_151558_() - camY);
         ChunkPos chunkpos = entity.dq();
         float f2 = (float)((double)chunkpos.d() - camX);
         float f3 = (float)((double)chunkpos.e() - camZ);
         VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.a(1.0));
         Matrix4f matrix4f = matrixStackIn.c().a();

         for (int i = -16; i <= 32; i += 16) {
            for (int j = -16; j <= 32; j += 16) {
               vertexconsumer.a(matrix4f, f2 + (float)i, f, f3 + (float)j).a(1.0F, 0.0F, 0.0F, 0.0F);
               vertexconsumer.a(matrix4f, f2 + (float)i, f, f3 + (float)j).a(1.0F, 0.0F, 0.0F, 0.5F);
               vertexconsumer.a(matrix4f, f2 + (float)i, f1, f3 + (float)j).a(1.0F, 0.0F, 0.0F, 0.5F);
               vertexconsumer.a(matrix4f, f2 + (float)i, f1, f3 + (float)j).a(1.0F, 0.0F, 0.0F, 0.0F);
            }
         }

         for (int l = 2; l < 16; l += 2) {
            int i2 = l % 4 == 0 ? b : c;
            vertexconsumer.a(matrix4f, f2 + (float)l, f, f3).a(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.a(matrix4f, f2 + (float)l, f, f3).a(i2);
            vertexconsumer.a(matrix4f, f2 + (float)l, f1, f3).a(i2);
            vertexconsumer.a(matrix4f, f2 + (float)l, f1, f3).a(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.a(matrix4f, f2 + (float)l, f, f3 + 16.0F).a(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.a(matrix4f, f2 + (float)l, f, f3 + 16.0F).a(i2);
            vertexconsumer.a(matrix4f, f2 + (float)l, f1, f3 + 16.0F).a(i2);
            vertexconsumer.a(matrix4f, f2 + (float)l, f1, f3 + 16.0F).a(1.0F, 1.0F, 0.0F, 0.0F);
         }

         for (int i1 = 2; i1 < 16; i1 += 2) {
            int j2 = i1 % 4 == 0 ? b : c;
            vertexconsumer.a(matrix4f, f2, f, f3 + (float)i1).a(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.a(matrix4f, f2, f, f3 + (float)i1).a(j2);
            vertexconsumer.a(matrix4f, f2, f1, f3 + (float)i1).a(j2);
            vertexconsumer.a(matrix4f, f2, f1, f3 + (float)i1).a(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.a(matrix4f, f2 + 16.0F, f, f3 + (float)i1).a(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.a(matrix4f, f2 + 16.0F, f, f3 + (float)i1).a(j2);
            vertexconsumer.a(matrix4f, f2 + 16.0F, f1, f3 + (float)i1).a(j2);
            vertexconsumer.a(matrix4f, f2 + 16.0F, f1, f3 + (float)i1).a(1.0F, 1.0F, 0.0F, 0.0F);
         }

         for (int j1 = this.a.r.m_141937_(); j1 <= this.a.r.m_151558_(); j1 += 2) {
            float f4 = (float)((double)j1 - camY);
            int k = j1 % 8 == 0 ? b : c;
            vertexconsumer.a(matrix4f, f2, f4, f3).a(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.a(matrix4f, f2, f4, f3).a(k);
            vertexconsumer.a(matrix4f, f2, f4, f3 + 16.0F).a(k);
            vertexconsumer.a(matrix4f, f2 + 16.0F, f4, f3 + 16.0F).a(k);
            vertexconsumer.a(matrix4f, f2 + 16.0F, f4, f3).a(k);
            vertexconsumer.a(matrix4f, f2, f4, f3).a(k);
            vertexconsumer.a(matrix4f, f2, f4, f3).a(1.0F, 1.0F, 0.0F, 0.0F);
         }

         vertexconsumer = bufferIn.getBuffer(RenderType.a(2.0));

         for (int k1 = 0; k1 <= 16; k1 += 16) {
            for (int k2 = 0; k2 <= 16; k2 += 16) {
               vertexconsumer.a(matrix4f, f2 + (float)k1, f, f3 + (float)k2).a(0.25F, 0.25F, 1.0F, 0.0F);
               vertexconsumer.a(matrix4f, f2 + (float)k1, f, f3 + (float)k2).a(0.25F, 0.25F, 1.0F, 1.0F);
               vertexconsumer.a(matrix4f, f2 + (float)k1, f1, f3 + (float)k2).a(0.25F, 0.25F, 1.0F, 1.0F);
               vertexconsumer.a(matrix4f, f2 + (float)k1, f1, f3 + (float)k2).a(0.25F, 0.25F, 1.0F, 0.0F);
            }
         }

         for (int l1 = this.a.r.m_141937_(); l1 <= this.a.r.m_151558_(); l1 += 16) {
            float f5 = (float)((double)l1 - camY);
            vertexconsumer.a(matrix4f, f2, f5, f3).a(0.25F, 0.25F, 1.0F, 0.0F);
            vertexconsumer.a(matrix4f, f2, f5, f3).a(0.25F, 0.25F, 1.0F, 1.0F);
            vertexconsumer.a(matrix4f, f2, f5, f3 + 16.0F).a(0.25F, 0.25F, 1.0F, 1.0F);
            vertexconsumer.a(matrix4f, f2 + 16.0F, f5, f3 + 16.0F).a(0.25F, 0.25F, 1.0F, 1.0F);
            vertexconsumer.a(matrix4f, f2 + 16.0F, f5, f3).a(0.25F, 0.25F, 1.0F, 1.0F);
            vertexconsumer.a(matrix4f, f2, f5, f3).a(0.25F, 0.25F, 1.0F, 1.0F);
            vertexconsumer.a(matrix4f, f2, f5, f3).a(0.25F, 0.25F, 1.0F, 0.0F);
         }

         if (Config.isShaders()) {
            Shaders.endLeash();
         }
      }
   }
}
