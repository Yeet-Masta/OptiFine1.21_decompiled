package net.minecraft.src;

import net.minecraft.src.C_174_.C_175_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import org.joml.Matrix4f;

public class C_4282_ implements C_4287_.C_4288_ {
   private final C_3391_ f_113354_;
   private static final int f_194450_ = C_175_.m_13660_(255, 0, 155, 155);
   private static final int f_194451_ = C_175_.m_13660_(255, 255, 255, 0);

   public C_4282_(C_3391_ minecraftIn) {
      this.f_113354_ = minecraftIn;
   }

   public void m_7790_(C_3181_ matrixStackIn, C_4139_ bufferIn, double camX, double camY, double camZ) {
      if (!Shaders.isShadowPass) {
         if (Config.isShaders()) {
            Shaders.beginLeash();
         }

         C_507_ entity = this.f_113354_.f_91063_.m_109153_().m_90592_();
         float f = (float)((double)this.f_113354_.f_91073_.I_() - camY);
         float f1 = (float)((double)this.f_113354_.f_91073_.am() - camY);
         C_1560_ chunkpos = entity.m_146902_();
         float f2 = (float)((double)chunkpos.m_45604_() - camX);
         float f3 = (float)((double)chunkpos.m_45605_() - camZ);
         C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_269399_(1.0));
         Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();

         int l1;
         int k2;
         for(l1 = -16; l1 <= 32; l1 += 16) {
            for(k2 = -16; k2 <= 32; k2 += 16) {
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f, f3 + (float)k2).m_340057_(1.0F, 0.0F, 0.0F, 0.0F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f, f3 + (float)k2).m_340057_(1.0F, 0.0F, 0.0F, 0.5F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f1, f3 + (float)k2).m_340057_(1.0F, 0.0F, 0.0F, 0.5F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f1, f3 + (float)k2).m_340057_(1.0F, 0.0F, 0.0F, 0.0F);
            }
         }

         for(l1 = 2; l1 < 16; l1 += 2) {
            k2 = l1 % 4 == 0 ? f_194450_ : f_194451_;
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f, f3).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f, f3).m_338399_(k2);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f1, f3).m_338399_(k2);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f1, f3).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f, f3 + 16.0F).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f, f3 + 16.0F).m_338399_(k2);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f1, f3 + 16.0F).m_338399_(k2);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f1, f3 + 16.0F).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
         }

         for(l1 = 2; l1 < 16; l1 += 2) {
            k2 = l1 % 4 == 0 ? f_194450_ : f_194451_;
            vertexconsumer.m_339083_(matrix4f, f2, f, f3 + (float)l1).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2, f, f3 + (float)l1).m_338399_(k2);
            vertexconsumer.m_339083_(matrix4f, f2, f1, f3 + (float)l1).m_338399_(k2);
            vertexconsumer.m_339083_(matrix4f, f2, f1, f3 + (float)l1).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f, f3 + (float)l1).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f, f3 + (float)l1).m_338399_(k2);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f1, f3 + (float)l1).m_338399_(k2);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f1, f3 + (float)l1).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
         }

         float f5;
         for(l1 = this.f_113354_.f_91073_.I_(); l1 <= this.f_113354_.f_91073_.am(); l1 += 2) {
            f5 = (float)((double)l1 - camY);
            int k = l1 % 8 == 0 ? f_194450_ : f_194451_;
            vertexconsumer.m_339083_(matrix4f, f2, f5, f3).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2, f5, f3).m_338399_(k);
            vertexconsumer.m_339083_(matrix4f, f2, f5, f3 + 16.0F).m_338399_(k);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f5, f3 + 16.0F).m_338399_(k);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f5, f3).m_338399_(k);
            vertexconsumer.m_339083_(matrix4f, f2, f5, f3).m_338399_(k);
            vertexconsumer.m_339083_(matrix4f, f2, f5, f3).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
         }

         vertexconsumer = bufferIn.m_6299_(C_4168_.m_269399_(2.0));

         for(l1 = 0; l1 <= 16; l1 += 16) {
            for(k2 = 0; k2 <= 16; k2 += 16) {
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 0.0F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 1.0F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f1, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 1.0F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f1, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 0.0F);
            }
         }

         for(l1 = this.f_113354_.f_91073_.I_(); l1 <= this.f_113354_.f_91073_.am(); l1 += 16) {
            f5 = (float)((double)l1 - camY);
            vertexconsumer.m_339083_(matrix4f, f2, f5, f3).m_340057_(0.25F, 0.25F, 1.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2, f5, f3).m_340057_(0.25F, 0.25F, 1.0F, 1.0F);
            vertexconsumer.m_339083_(matrix4f, f2, f5, f3 + 16.0F).m_340057_(0.25F, 0.25F, 1.0F, 1.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f5, f3 + 16.0F).m_340057_(0.25F, 0.25F, 1.0F, 1.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f5, f3).m_340057_(0.25F, 0.25F, 1.0F, 1.0F);
            vertexconsumer.m_339083_(matrix4f, f2, f5, f3).m_340057_(0.25F, 0.25F, 1.0F, 1.0F);
            vertexconsumer.m_339083_(matrix4f, f2, f5, f3).m_340057_(0.25F, 0.25F, 1.0F, 0.0F);
         }

         if (Config.isShaders()) {
            Shaders.endLeash();
         }

      }
   }
}
