package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.platform.GlStateManager;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import java.nio.ByteBuffer;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_3188_;

public final class RenderSystem$C_141293_ {
   private final int f_157465_;
   private final int f_157466_;
   private final RenderSystem$C_141293_$C_141294_ f_157467_;
   private int f_157468_;
   private C_3188_.C_141548_ f_157469_;
   private int f_157470_;

   RenderSystem$C_141293_(int vertexStrideIn, int indexStrideIn, RenderSystem$C_141293_$C_141294_ generatorIn) {
      this.f_157469_ = C_3188_.C_141548_.SHORT;
      this.f_157465_ = vertexStrideIn;
      this.f_157466_ = indexStrideIn;
      this.f_157467_ = generatorIn;
   }

   public boolean m_221944_(int indexCountIn) {
      return indexCountIn <= this.f_157470_;
   }

   public void m_221946_(int indexCountIn) {
      if (this.f_157468_ == 0) {
         this.f_157468_ = GlStateManager._glGenBuffers();
      }

      GlStateManager._glBindBuffer(34963, this.f_157468_);
      this.m_157476_(indexCountIn);
   }

   public void m_157476_(int indexCountIn) {
      if (!this.m_221944_(indexCountIn)) {
         indexCountIn = C_188_.m_144941_(indexCountIn * 2, this.f_157466_);
         RenderSystem.LOGGER.debug("Growing IndexBuffer: Old limit {}, new limit {}.", this.f_157470_, indexCountIn);
         int i = indexCountIn / this.f_157466_;
         int j = i * this.f_157465_;
         C_3188_.C_141548_ vertexformat$indextype = C_3188_.C_141548_.m_166933_(j);
         int k = C_188_.m_144941_(indexCountIn * vertexformat$indextype.f_166924_, 4);
         GlStateManager._glBufferData(34963, (long)k, 35048);
         ByteBuffer bytebuffer = GlStateManager._glMapBuffer(34963, 35001);
         if (bytebuffer == null) {
            throw new RuntimeException("Failed to map GL buffer");
         }

         this.f_157469_ = vertexformat$indextype;
         IntConsumer intconsumer = this.m_157478_(bytebuffer);

         for(int l = 0; l < indexCountIn; l += this.f_157466_) {
            this.f_157467_.m_157487_(intconsumer, l * this.f_157465_ / this.f_157466_);
         }

         GlStateManager._glUnmapBuffer(34963);
         this.f_157470_ = indexCountIn;
      }

   }

   private IntConsumer m_157478_(ByteBuffer byteBufferIn) {
      // $FF: Couldn't be decompiled
   }

   public C_3188_.C_141548_ m_157483_() {
      return this.f_157469_;
   }
}
