import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;

public class BufferUploader {
   @Nullable
   private static VertexBuffer a;

   public static void a() {
      if (a != null) {
         b();
         VertexBuffer.b();
      }
   }

   public static void b() {
      a = null;
   }

   public static void a(MeshData bufferIn) {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> c(bufferIn));
      } else {
         c(bufferIn);
      }
   }

   private static void c(MeshData bufferIn) {
      VertexBuffer vertexbuffer = d(bufferIn);
      vertexbuffer.a(RenderSystem.getModelViewMatrix(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
   }

   public static void b(MeshData bufferIn) {
      VertexBuffer vertexbuffer = d(bufferIn);
      vertexbuffer.c();
   }

   private static VertexBuffer d(MeshData bufferIn) {
      RenderSystem.assertOnRenderThread();
      VertexBuffer vertexbuffer = a(bufferIn.c().a());
      vertexbuffer.a(bufferIn);
      return vertexbuffer;
   }

   private static VertexBuffer a(VertexFormat formatIn) {
      VertexBuffer vertexbuffer = formatIn.i();
      a(vertexbuffer);
      return vertexbuffer;
   }

   private static void a(VertexBuffer bufferIn) {
      if (bufferIn != a) {
         bufferIn.a();
         a = bufferIn;
      }
   }
}
