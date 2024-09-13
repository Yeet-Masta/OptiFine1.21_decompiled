package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.debug.DebugRenderer.SimpleDebugRenderer;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import org.joml.Matrix4f;

public class ChunkBorderRenderer implements SimpleDebugRenderer {
   private Minecraft f_113354_;
   private static int f_194450_ = ARGB32.m_13660_(255, 0, 155, 155);
   private static int f_194451_ = ARGB32.m_13660_(255, 255, 255, 0);

   public ChunkBorderRenderer(Minecraft minecraftIn) {
      this.f_113354_ = minecraftIn;
   }

   public void m_7790_(PoseStack matrixStackIn, MultiBufferSource bufferIn, double camX, double camY, double camZ) {
      if (!Shaders.isShadowPass) {
         if (Config.isShaders()) {
            Shaders.beginLeash();
         }

         Entity entity = this.f_113354_.f_91063_.m_109153_().m_90592_();
         float f = (float)((double)this.f_113354_.f_91073_.m_141937_() - camY);
         float f1 = (float)((double)this.f_113354_.f_91073_.m_151558_() - camY);
         ChunkPos chunkpos = entity.m_146902_();
         float f2 = (float)((double)chunkpos.m_45604_() - camX);
         float f3 = (float)((double)chunkpos.m_45605_() - camZ);
         VertexConsumer vertexconsumer = bufferIn.m_6299_(RenderType.m_269399_(1.0));
         Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();

         for (int i = -16; i <= 32; i += 16) {
            for (int j = -16; j <= 32; j += 16) {
               vertexconsumer.m_339083_(matrix4f, f2 + (float)i, f, f3 + (float)j).m_340057_(1.0F, 0.0F, 0.0F, 0.0F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)i, f, f3 + (float)j).m_340057_(1.0F, 0.0F, 0.0F, 0.5F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)i, f1, f3 + (float)j).m_340057_(1.0F, 0.0F, 0.0F, 0.5F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)i, f1, f3 + (float)j).m_340057_(1.0F, 0.0F, 0.0F, 0.0F);
            }
         }

         for (int l = 2; l < 16; l += 2) {
            int i2 = l % 4 == 0 ? f_194450_ : f_194451_;
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l, f, f3).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l, f, f3).m_338399_(i2);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l, f1, f3).m_338399_(i2);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l, f1, f3).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l, f, f3 + 16.0F).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l, f, f3 + 16.0F).m_338399_(i2);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l, f1, f3 + 16.0F).m_338399_(i2);
            vertexconsumer.m_339083_(matrix4f, f2 + (float)l, f1, f3 + 16.0F).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
         }

         for (int i1 = 2; i1 < 16; i1 += 2) {
            int j2 = i1 % 4 == 0 ? f_194450_ : f_194451_;
            vertexconsumer.m_339083_(matrix4f, f2, f, f3 + (float)i1).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2, f, f3 + (float)i1).m_338399_(j2);
            vertexconsumer.m_339083_(matrix4f, f2, f1, f3 + (float)i1).m_338399_(j2);
            vertexconsumer.m_339083_(matrix4f, f2, f1, f3 + (float)i1).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f, f3 + (float)i1).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f, f3 + (float)i1).m_338399_(j2);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f1, f3 + (float)i1).m_338399_(j2);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f1, f3 + (float)i1).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
         }

         for (int j1 = this.f_113354_.f_91073_.m_141937_(); j1 <= this.f_113354_.f_91073_.m_151558_(); j1 += 2) {
            float f4 = (float)((double)j1 - camY);
            int k = j1 % 8 == 0 ? f_194450_ : f_194451_;
            vertexconsumer.m_339083_(matrix4f, f2, f4, f3).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
            vertexconsumer.m_339083_(matrix4f, f2, f4, f3).m_338399_(k);
            vertexconsumer.m_339083_(matrix4f, f2, f4, f3 + 16.0F).m_338399_(k);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f4, f3 + 16.0F).m_338399_(k);
            vertexconsumer.m_339083_(matrix4f, f2 + 16.0F, f4, f3).m_338399_(k);
            vertexconsumer.m_339083_(matrix4f, f2, f4, f3).m_338399_(k);
            vertexconsumer.m_339083_(matrix4f, f2, f4, f3).m_340057_(1.0F, 1.0F, 0.0F, 0.0F);
         }

         vertexconsumer = bufferIn.m_6299_(RenderType.m_269399_(2.0));

         for (int k1 = 0; k1 <= 16; k1 += 16) {
            for (int k2 = 0; k2 <= 16; k2 += 16) {
               vertexconsumer.m_339083_(matrix4f, f2 + (float)k1, f, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 0.0F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)k1, f, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 1.0F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)k1, f1, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 1.0F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)k1, f1, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 0.0F);
            }
         }

         for (int l1 = this.f_113354_.f_91073_.m_141937_(); l1 <= this.f_113354_.f_91073_.m_151558_(); l1 += 16) {
            float f5 = (float)((double)l1 - camY);
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
