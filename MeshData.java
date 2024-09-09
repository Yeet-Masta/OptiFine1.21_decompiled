import it.unimi.dsi.fastutil.ints.IntConsumer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import javax.annotation.Nullable;
import net.minecraft.src.C_276405_;
import net.optifine.render.MultiTextureBuilder;
import net.optifine.render.MultiTextureData;
import org.apache.commons.lang3.mutable.MutableLong;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class MeshData implements AutoCloseable {
   private final ByteBufferBuilder.a a;
   @Nullable
   private ByteBufferBuilder.a b;
   private final MeshData.a c;
   private MultiTextureData multiTextureData;

   public MeshData(ByteBufferBuilder.a vertexBufferIn, MeshData.a drawStateIn) {
      this(vertexBufferIn, drawStateIn, null);
   }

   public MeshData(ByteBufferBuilder.a vertexBufferIn, MeshData.a drawStateIn, MultiTextureData multiTextureDataIn) {
      this.a = vertexBufferIn;
      this.c = drawStateIn;
      this.multiTextureData = multiTextureDataIn;
   }

   private static Vector3f[] a(ByteBuffer bufferIn, int vertexCountIn, VertexFormat vertexFormatIn) {
      int i = vertexFormatIn.a(VertexFormatElement.b);
      if (i == -1) {
         throw new IllegalArgumentException("Cannot identify quad centers with no position element");
      } else {
         FloatBuffer floatbuffer = bufferIn.asFloatBuffer();
         int j = vertexFormatIn.b() / 4;
         int k = j * 4;
         int l = vertexCountIn / 4;
         Vector3f[] avector3f = new Vector3f[l];

         for (int i1 = 0; i1 < l; i1++) {
            int j1 = i1 * k + i;
            int k1 = j1 + j * 2;
            float f = floatbuffer.get(j1 + 0);
            float f1 = floatbuffer.get(j1 + 1);
            float f2 = floatbuffer.get(j1 + 2);
            float f3 = floatbuffer.get(k1 + 0);
            float f4 = floatbuffer.get(k1 + 1);
            float f5 = floatbuffer.get(k1 + 2);
            avector3f[i1] = new Vector3f((f + f3) / 2.0F, (f1 + f4) / 2.0F, (f2 + f5) / 2.0F);
         }

         return avector3f;
      }
   }

   public ByteBuffer a() {
      return this.a.a();
   }

   @Nullable
   public ByteBuffer b() {
      return this.b != null ? this.b.a() : null;
   }

   public MeshData.a c() {
      return this.c;
   }

   @Nullable
   public MeshData.b a(ByteBufferBuilder builderIn, C_276405_ sortingIn) {
      if (this.c.d() != VertexFormat.c.h) {
         return null;
      } else {
         Vector3f[] avector3f = a(this.a.a(), this.c.b(), this.c.a());
         MeshData.b meshdata$sortstate = new MeshData.b(avector3f, this.c.e(), this.multiTextureData);
         this.b = meshdata$sortstate.a(builderIn, sortingIn);
         return meshdata$sortstate;
      }
   }

   public void close() {
      this.a.close();
      if (this.b != null) {
         this.b.close();
      }
   }

   public MultiTextureData getMultiTextureData() {
      return this.multiTextureData;
   }

   public String toString() {
      return "vertexBuffer: (" + this.a + "), indexBuffer: (" + this.b + "), drawState: (" + this.c + ")";
   }

   public static record a(VertexFormat a, int b, int c, VertexFormat.c d, VertexFormat.b e) {
      public int getVertexBufferSize() {
         return this.b * this.a.b();
      }
   }

   public static record b(Vector3f[] a, VertexFormat.b b, MultiTextureData multiTextureData) {
      public b(Vector3f[] centroids, VertexFormat.b indexType) {
         this(centroids, indexType, null);
      }

      @Nullable
      public ByteBufferBuilder.a a(ByteBufferBuilder builderIn, C_276405_ sortingIn) {
         int[] aint = sortingIn.m_277065_(this.a);
         long i = builderIn.a(aint.length * 6 * this.b.d);
         IntConsumer intconsumer = this.a(i, this.b);

         for (int j : aint) {
            intconsumer.accept(j * 4 + 0);
            intconsumer.accept(j * 4 + 1);
            intconsumer.accept(j * 4 + 2);
            intconsumer.accept(j * 4 + 2);
            intconsumer.accept(j * 4 + 3);
            intconsumer.accept(j * 4 + 0);
         }

         if (this.multiTextureData != null) {
            MultiTextureBuilder mtb = builderIn.getBufferBuilderCache().getMultiTextureBuilder();
            this.multiTextureData.prepareSort(mtb, aint);
         }

         return builderIn.a();
      }

      private IntConsumer a(long ptrIn, VertexFormat.b typeIn) {
         MutableLong mutablelong = new MutableLong(ptrIn);

         return switch (typeIn) {
            case a -> valIn -> MemoryUtil.memPutShort(mutablelong.getAndAdd(2L), (short)valIn);
            case b -> valIn -> MemoryUtil.memPutInt(mutablelong.getAndAdd(4L), valIn);
         };
      }
   }
}
