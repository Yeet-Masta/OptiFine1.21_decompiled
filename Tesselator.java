import javax.annotation.Nullable;

public class Tesselator {
   private static final int a = 786432;
   private final ByteBufferBuilder b;
   @Nullable
   private static Tesselator c;

   public static void a() {
      if (c != null) {
         throw new IllegalStateException("Tesselator has already been initialized");
      } else {
         c = new Tesselator();
      }
   }

   public static Tesselator b() {
      if (c == null) {
         throw new IllegalStateException("Tesselator has not been initialized");
      } else {
         return c;
      }
   }

   public Tesselator(int bufferSize) {
      this.b = new ByteBufferBuilder(bufferSize);
   }

   public Tesselator() {
      this(786432);
   }

   public BufferBuilder a(VertexFormat.c modeIn, VertexFormat formatIn) {
      return new BufferBuilder(this.b, modeIn, formatIn);
   }

   public void c() {
      this.b.b();
   }

   public void draw(BufferBuilder bufferIn) {
      BufferUploader.a(bufferIn.b());
   }
}
