import java.util.function.Consumer;
import net.optifine.render.VertexBuilderWrapper;

public class VertexMultiConsumer {
   public static VertexConsumer a() {
      throw new IllegalArgumentException();
   }

   public static VertexConsumer a(VertexConsumer vertexConsumerIn) {
      return vertexConsumerIn;
   }

   public static VertexConsumer a(VertexConsumer consumer1, VertexConsumer consumer2) {
      return new VertexMultiConsumer.a(consumer1, consumer2);
   }

   public static VertexConsumer a(VertexConsumer... vertexConsumersIn) {
      return new VertexMultiConsumer.b(vertexConsumersIn);
   }

   static class a extends VertexBuilderWrapper implements VertexConsumer {
      private final VertexConsumer a;
      private final VertexConsumer b;
      private boolean fixMultitextureUV;

      public a(VertexConsumer firstIn, VertexConsumer secondIn) {
         super(secondIn);
         if (firstIn == secondIn) {
            throw new IllegalArgumentException("Duplicate delegates");
         } else {
            this.a = firstIn;
            this.b = secondIn;
            this.updateFixMultitextureUv();
         }
      }

      @Override
      public VertexConsumer a(float x, float y, float z) {
         this.a.a(x, y, z);
         this.b.a(x, y, z);
         return this;
      }

      @Override
      public VertexConsumer a(int red, int green, int blue, int alpha) {
         this.a.a(red, green, blue, alpha);
         this.b.a(red, green, blue, alpha);
         return this;
      }

      @Override
      public VertexConsumer a(float u, float v) {
         this.a.a(u, v);
         this.b.a(u, v);
         return this;
      }

      @Override
      public VertexConsumer a(int u, int v) {
         this.a.a(u, v);
         this.b.a(u, v);
         return this;
      }

      @Override
      public VertexConsumer b(int u, int v) {
         this.a.b(u, v);
         this.b.b(u, v);
         return this;
      }

      @Override
      public VertexConsumer b(float x, float y, float z) {
         this.a.b(x, y, z);
         this.b.b(x, y, z);
         return this;
      }

      @Override
      public void a(float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
         if (this.fixMultitextureUV) {
            this.a.a(x, y, z, argb, texU / 32.0F, texV / 32.0F, overlayUV, lightmapUV, normalX, normalY, normalZ);
         } else {
            this.a.a(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
         }

         this.b.a(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
      }

      private void updateFixMultitextureUv() {
         this.fixMultitextureUV = !this.a.isMultiTexture() && this.b.isMultiTexture();
      }

      @Override
      public VertexConsumer getSecondaryBuilder() {
         return this.a;
      }
   }

   static class b extends VertexBuilderWrapper implements VertexConsumer {
      private VertexConsumer[] a;

      b(VertexConsumer[] delegates) {
         super(delegates.length > 0 ? delegates[0] : null);
         this.a = delegates;

         for (int i = 0; i < delegates.length; i++) {
            for (int j = i + 1; j < delegates.length; j++) {
               if (delegates[i] == delegates[j]) {
                  throw new IllegalArgumentException("Duplicate delegates");
               }
            }
         }

         this.a = delegates;
      }

      private void a(Consumer<VertexConsumer> consumerIn) {
         for (VertexConsumer vertexconsumer : this.a) {
            consumerIn.accept(vertexconsumer);
         }
      }

      @Override
      public VertexConsumer a(float x, float y, float z) {
         this.a(consumerIn -> consumerIn.a(x, y, z));
         return this;
      }

      @Override
      public VertexConsumer a(int red, int green, int blue, int alpha) {
         this.a(consumerIn -> consumerIn.a(red, green, blue, alpha));
         return this;
      }

      @Override
      public VertexConsumer a(float u, float v) {
         this.a(consumerIn -> consumerIn.a(u, v));
         return this;
      }

      @Override
      public VertexConsumer a(int u, int v) {
         this.a(consumerIn -> consumerIn.a(u, v));
         return this;
      }

      @Override
      public VertexConsumer b(int u, int v) {
         this.a(consumerIn -> consumerIn.b(u, v));
         return this;
      }

      @Override
      public VertexConsumer b(float x, float y, float z) {
         this.a(consumerIn -> consumerIn.b(x, y, z));
         return this;
      }

      @Override
      public void a(float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
         this.a(consumerIn -> consumerIn.a(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ));
      }
   }
}
