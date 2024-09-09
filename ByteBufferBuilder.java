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
   private static final Logger a = LogUtils.getLogger();
   private static final MemoryAllocator b = MemoryUtil.getAllocator(false);
   private static final int c = 2097152;
   private static final int d = -1;
   long e;
   private int f;
   private int g;
   private int h;
   private int i;
   private int j;
   private BufferBuilderCache bufferBuilderCache = new BufferBuilderCache();
   private ByteBuffer byteBuffer;
   private IntBuffer intBuffer;
   private FloatBuffer floatBuffer;

   public ByteBufferBuilder(int capacityIn) {
      this.f = capacityIn;
      this.e = b.malloc((long)capacityIn);
      if (this.e == 0L) {
         throw new OutOfMemoryError("Failed to allocate " + capacityIn + " bytes");
      } else {
         this.byteBuffer = MemoryUtil.memByteBuffer(this.e, this.f);
         this.intBuffer = this.byteBuffer.asIntBuffer();
         this.floatBuffer = this.byteBuffer.asFloatBuffer();
      }
   }

   public long a(int sizeIn) {
      int i = this.g;
      int j = i + sizeIn;
      this.b(j);
      this.g = j;
      return this.e + (long)i;
   }

   private void b(int capacityIn) {
      if (capacityIn > this.f) {
         int i = Math.min(this.f, 2097152);
         int j = Math.max(this.f + i, capacityIn);
         this.c(j);
      }
   }

   private void c(int capacityIn) {
      this.e = b.realloc(this.e, (long)capacityIn);
      a.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", this.f, capacityIn);
      if (this.e == 0L) {
         throw new OutOfMemoryError("Failed to resize buffer from " + this.f + " bytes to " + capacityIn + " bytes");
      } else {
         this.f = capacityIn;
         this.byteBuffer = MemoryUtil.memByteBuffer(this.e, this.f);
         this.intBuffer = this.byteBuffer.asIntBuffer();
         this.floatBuffer = this.byteBuffer.asFloatBuffer();
      }
   }

   @Nullable
   public ByteBufferBuilder.a a() {
      this.f();
      int i = this.h;
      int j = this.g - i;
      if (j == 0) {
         return null;
      } else {
         this.h = this.g;
         this.i++;
         return new ByteBufferBuilder.a(i, j, this.j);
      }
   }

   public void b() {
      if (this.i > 0) {
         a.warn("Clearing BufferBuilder with unused batches");
      }

      this.c();
   }

   public void c() {
      this.f();
      if (this.i > 0) {
         this.e();
         this.i = 0;
      }
   }

   boolean d(int generationIn) {
      return generationIn == this.j;
   }

   void d() {
      if (--this.i <= 0) {
         this.e();
      }
   }

   private void e() {
      int i = this.g - this.h;
      if (i > 0) {
         MemoryUtil.memCopy(this.e + (long)this.h, this.e, (long)i);
      }

      this.g = i;
      this.h = 0;
      this.j++;
   }

   public void close() {
      if (this.e != 0L) {
         b.free(this.e);
         this.e = 0L;
         this.j = -1;
         this.byteBuffer = null;
         this.intBuffer = null;
         this.floatBuffer = null;
      }
   }

   private void f() {
      if (this.e == 0L) {
         throw new IllegalStateException("Buffer has been freed");
      }
   }

   public int getCapacity() {
      return this.f;
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
      return this.h;
   }

   public int getWriteOffset() {
      return this.g;
   }

   public BufferBuilderCache getBufferBuilderCache() {
      return this.bufferBuilderCache;
   }

   public String toString() {
      return "resultOffset: " + this.h + ", writeOffset: " + this.g + ", capacity: " + this.f;
   }

   public class a implements AutoCloseable {
      private final int b;
      private final int c;
      private final int d;
      private boolean e;

      a(final int offsetIn, final int capacityIn, final int generationIn) {
         this.b = offsetIn;
         this.c = capacityIn;
         this.d = generationIn;
      }

      public ByteBuffer a() {
         if (!ByteBufferBuilder.this.d(this.d)) {
            throw new IllegalStateException("Buffer is no longer valid");
         } else {
            return MemoryUtil.memByteBuffer(ByteBufferBuilder.this.e + (long)this.b, this.c);
         }
      }

      public void close() {
         if (!this.e) {
            this.e = true;
            if (ByteBufferBuilder.this.d(this.d)) {
               ByteBufferBuilder.this.d();
            }
         }
      }

      public String toString() {
         return "offset: " + this.b + ", capacity: " + this.c + ", generation: " + this.d + ", closed: " + this.e;
      }
   }
}
