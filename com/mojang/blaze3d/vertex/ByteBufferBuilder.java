package com.mojang.blaze3d.vertex;

import com.mojang.logging.LogUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import net.optifine.render.BufferBuilderCache;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.MemoryUtil.MemoryAllocator;
import org.slf4j.Logger;

public class ByteBufferBuilder implements AutoCloseable {
   private static Logger f_337525_ = LogUtils.getLogger();
   private static MemoryAllocator f_337626_ = MemoryUtil.getAllocator(false);
   private static int f_337594_;
   private static int f_336759_;
   long f_337511_;
   private int f_336824_;
   private int f_337065_;
   private int f_337195_;
   private int f_337608_;
   private int f_337664_;
   private BufferBuilderCache bufferBuilderCache = new BufferBuilderCache();
   private ByteBuffer byteBuffer;
   private IntBuffer intBuffer;
   private FloatBuffer floatBuffer;

   public ByteBufferBuilder(int capacityIn) {
      this.f_336824_ = capacityIn;
      this.f_337511_ = f_337626_.malloc((long)capacityIn);
      if (this.f_337511_ == 0L) {
         throw new OutOfMemoryError("Failed to allocate " + capacityIn + " bytes");
      } else {
         this.byteBuffer = MemoryUtil.memByteBuffer(this.f_337511_, this.f_336824_);
         this.intBuffer = this.byteBuffer.asIntBuffer();
         this.floatBuffer = this.byteBuffer.asFloatBuffer();
      }
   }

   public long m_338881_(int sizeIn) {
      int i = this.f_337065_;
      int j = i + sizeIn;
      this.m_339252_(j);
      this.f_337065_ = j;
      return this.f_337511_ + (long)i;
   }

   private void m_339252_(int capacityIn) {
      if (capacityIn > this.f_336824_) {
         int i = Math.min(this.f_336824_, 2097152);
         int j = Math.max(this.f_336824_ + i, capacityIn);
         this.m_338423_(j);
      }
   }

   private void m_338423_(int capacityIn) {
      this.f_337511_ = f_337626_.realloc(this.f_337511_, (long)capacityIn);
      f_337525_.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", this.f_336824_, capacityIn);
      if (this.f_337511_ == 0L) {
         throw new OutOfMemoryError("Failed to resize buffer from " + this.f_336824_ + " bytes to " + capacityIn + " bytes");
      } else {
         this.f_336824_ = capacityIn;
         this.byteBuffer = MemoryUtil.memByteBuffer(this.f_337511_, this.f_336824_);
         this.intBuffer = this.byteBuffer.asIntBuffer();
         this.floatBuffer = this.byteBuffer.asFloatBuffer();
      }
   }

   @Nullable
   public ByteBufferBuilder.Result m_339207_() {
      this.m_339060_();
      int i = this.f_337195_;
      int j = this.f_337065_ - i;
      if (j == 0) {
         return null;
      } else {
         this.f_337195_ = this.f_337065_;
         this.f_337608_++;
         return new ByteBufferBuilder.Result(i, j, this.f_337664_);
      }
   }

   public void m_340278_() {
      if (this.f_337608_ > 0) {
         f_337525_.warn("Clearing BufferBuilder with unused batches");
      }

      this.m_339056_();
   }

   public void m_339056_() {
      this.m_339060_();
      if (this.f_337608_ > 0) {
         this.m_339343_();
         this.f_337608_ = 0;
      }
   }

   boolean m_338446_(int generationIn) {
      return generationIn == this.f_337664_;
   }

   void m_340122_() {
      if (--this.f_337608_ <= 0) {
         this.m_339343_();
      }
   }

   private void m_339343_() {
      int i = this.f_337065_ - this.f_337195_;
      if (i > 0) {
         MemoryUtil.memCopy(this.f_337511_ + (long)this.f_337195_, this.f_337511_, (long)i);
      }

      this.f_337065_ = i;
      this.f_337195_ = 0;
      this.f_337664_++;
   }

   public void close() {
      if (this.f_337511_ != 0L) {
         f_337626_.free(this.f_337511_);
         this.f_337511_ = 0L;
         this.f_337664_ = -1;
         this.byteBuffer = null;
         this.intBuffer = null;
         this.floatBuffer = null;
      }
   }

   private void m_339060_() {
      if (this.f_337511_ == 0L) {
         throw new IllegalStateException("Buffer has been freed");
      }
   }

   public int getCapacity() {
      return this.f_336824_;
   }

   public ByteBuffer getByteBuffer() {
      return this.byteBuffer;
   }

   public IntBuffer getIntBuffer() {
      return this.intBuffer;
   }

   public FloatBuffer getFloatBuffer() {
      return this.floatBuffer;
   }

   public int getNextResultOffset() {
      return this.f_337195_;
   }

   public int getWriteOffset() {
      return this.f_337065_;
   }

   public BufferBuilderCache getBufferBuilderCache() {
      return this.bufferBuilderCache;
   }

   public String toString() {
      return "resultOffset: " + this.f_337195_ + ", writeOffset: " + this.f_337065_ + ", capacity: " + this.f_336824_;
   }

   public class Result implements AutoCloseable {
      private int f_337012_;
      private int f_337459_;
      private int f_337017_;
      private boolean f_337698_;

      Result(final int offsetIn, final int capacityIn, final int generationIn) {
         this.f_337012_ = offsetIn;
         this.f_337459_ = capacityIn;
         this.f_337017_ = generationIn;
      }

      public ByteBuffer m_338393_() {
         if (!ByteBufferBuilder.this.m_338446_(this.f_337017_)) {
            throw new IllegalStateException("Buffer is no longer valid");
         } else {
            return MemoryUtil.memByteBuffer(ByteBufferBuilder.this.f_337511_ + (long)this.f_337012_, this.f_337459_);
         }
      }

      public void close() {
         if (!this.f_337698_) {
            this.f_337698_ = true;
            if (ByteBufferBuilder.this.m_338446_(this.f_337017_)) {
               ByteBufferBuilder.this.m_340122_();
            }
         }
      }

      public String toString() {
         return "offset: " + this.f_337012_ + ", capacity: " + this.f_337459_ + ", generation: " + this.f_337017_ + ", closed: " + this.f_337698_;
      }
   }
}
