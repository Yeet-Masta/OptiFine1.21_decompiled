package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import org.joml.Matrix4f;

public class ChunkBorderRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft f_113354_;
   private static final int f_194450_ = ARGB32.m_13660_(255, 0, 155, 155);
   private static final int f_194451_ = ARGB32.m_13660_(255, 255, 255, 0);

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
         for(l1 = this.f_113354_.f_91073_.m_141937_(); l1 <= this.f_113354_.f_91073_.m_151558_(); l1 += 2) {
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

         vertexconsumer = bufferIn.m_6299_(RenderType.m_269399_(2.0));

         for(l1 = 0; l1 <= 16; l1 += 16) {
            for(k2 = 0; k2 <= 16; k2 += 16) {
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 0.0F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 1.0F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f1, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 1.0F);
               vertexconsumer.m_339083_(matrix4f, f2 + (float)l1, f1, f3 + (float)k2).m_340057_(0.25F, 0.25F, 1.0F, 0.0F);
            }
         }

         for(l1 = this.f_113354_.f_91073_.m_141937_(); l1 <= this.f_113354_.f_91073_.m_151558_(); l1 += 16) {
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
