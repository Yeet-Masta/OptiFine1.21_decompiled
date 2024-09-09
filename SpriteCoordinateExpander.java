import net.optifine.SmartAnimations;
import net.optifine.render.VertexBuilderWrapper;

public class SpriteCoordinateExpander extends VertexBuilderWrapper implements VertexConsumer {
   private final VertexConsumer a;
   private final TextureAtlasSprite b;

   public SpriteCoordinateExpander(VertexConsumer bufferIn, TextureAtlasSprite spriteIn) {
      super(bufferIn);
      if (SmartAnimations.isActive()) {
         SmartAnimations.spriteRendered(spriteIn);
      }

      this.a = bufferIn;
      this.b = spriteIn;
   }

   @Override
   public VertexConsumer a(float x, float y, float z) {
      this.a.a(x, y, z);
      return this;
   }

   @Override
   public VertexConsumer a(int red, int green, int blue, int alpha) {
      this.a.a(red, green, blue, alpha);
      return this;
   }

   @Override
   public VertexConsumer a(float u, float v) {
      this.a.a(this.b.a(u), this.b.c(v));
      return this;
   }

   @Override
   public VertexConsumer a(int u, int v) {
      this.a.a(u, v);
      return this;
   }

   @Override
   public VertexConsumer b(int u, int v) {
      this.a.b(u, v);
      return this;
   }

   @Override
   public VertexConsumer b(float x, float y, float z) {
      this.a.b(x, y, z);
      return this;
   }

   @Override
   public void a(float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
      if (this.a.isMultiTexture()) {
         this.a.putSprite(this.b);
         this.a.a(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
      } else {
         this.a.a(x, y, z, argb, this.b.a(texU), this.b.c(texV), overlayUV, lightmapUV, normalX, normalY, normalZ);
      }
   }

   @Override
   public boolean canAddVertexFast() {
      return this.a.canAddVertexFast();
   }

   @Override
   public void addVertexFast(float x, float y, float z, int color, float texU, float texV, int overlayUV, int lightmapUV, int normals) {
      if (this.a.isMultiTexture()) {
         this.a.putSprite(this.b);
         this.a.addVertexFast(x, y, z, color, texU, texV, overlayUV, lightmapUV, normals);
      } else {
         float tu = this.b.a(texU);
         float tv = this.b.c(texV);
         this.a.addVertexFast(x, y, z, color, tu, tv, overlayUV, lightmapUV, normals);
      }
   }
}
