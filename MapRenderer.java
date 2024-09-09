import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_2767_;
import net.minecraft.src.C_2771_;
import net.minecraft.src.C_313551_;
import net.minecraft.src.C_313617_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4996_;
import org.joml.Matrix4f;

public class MapRenderer implements AutoCloseable {
   private static final int a = 128;
   private static final int b = 128;
   final TextureManager c;
   final C_313551_ d;
   private final Int2ObjectMap<MapRenderer.a> e = new Int2ObjectOpenHashMap();

   public MapRenderer(TextureManager textureManagerIn, C_313551_ decoTexIn) {
      this.c = textureManagerIn;
      this.d = decoTexIn;
   }

   public void a(C_313617_ mapIdIn, C_2771_ dataIn) {
      this.b(mapIdIn, dataIn).a();
   }

   public void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, C_313617_ idIn, C_2771_ dataIn, boolean skipDecorationsIn, int combinedLightIn) {
      this.b(idIn, dataIn).a(matrixStackIn, bufferIn, skipDecorationsIn, combinedLightIn);
   }

   private MapRenderer.a b(C_313617_ idIn, C_2771_ dataIn) {
      return (MapRenderer.a)this.e.compute(idIn.f_315413_(), (idIn2, mapIn2) -> {
         if (mapIn2 == null) {
            return new MapRenderer.a(idIn2, dataIn);
         } else {
            mapIn2.a(dataIn);
            return mapIn2;
         }
      });
   }

   public void a() {
      ObjectIterator var1 = this.e.values().iterator();

      while (var1.hasNext()) {
         MapRenderer.a maprenderer$mapinstance = (MapRenderer.a)var1.next();
         maprenderer$mapinstance.close();
      }

      this.e.clear();
   }

   public void close() {
      this.a();
   }

   class a implements AutoCloseable {
      private C_2771_ b;
      private final DynamicTexture c;
      private final RenderType d;
      private boolean e = true;

      a(final int idIn, final C_2771_ dataIn) {
         this.b = dataIn;
         this.c = new DynamicTexture(128, 128, true);
         ResourceLocation resourcelocation = MapRenderer.this.c.a("map/" + idIn, this.c);
         this.d = RenderType.d(resourcelocation);
      }

      void a(C_2771_ dataIn) {
         boolean flag = this.b != dataIn;
         this.b = dataIn;
         this.e |= flag;
      }

      public void a() {
         this.e = true;
      }

      private void b() {
         for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
               int k = j + i * 128;
               this.c.e().a(j, i, MapColor.b(this.b.f_77891_[k]));
            }
         }

         this.c.d();
      }

      void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, boolean skipDecorationsIn, int lightmapIn) {
         if (this.e) {
            this.b();
            this.e = false;
         }

         int i = 0;
         int j = 0;
         float f = 0.0F;
         Matrix4f matrix4f = matrixStackIn.c().a();
         VertexConsumer vertexconsumer = bufferIn.getBuffer(this.d);
         vertexconsumer.a(matrix4f, 0.0F, 128.0F, -0.01F).a(-1).a(0.0F, 1.0F).b(C_4474_.f_118083_).c(lightmapIn).b(0.0F, 1.0F, 0.0F);
         vertexconsumer.a(matrix4f, 128.0F, 128.0F, -0.01F).a(-1).a(1.0F, 1.0F).b(C_4474_.f_118083_).c(lightmapIn).b(0.0F, 1.0F, 0.0F);
         vertexconsumer.a(matrix4f, 128.0F, 0.0F, -0.01F).a(-1).a(1.0F, 0.0F).b(C_4474_.f_118083_).c(lightmapIn).b(0.0F, 1.0F, 0.0F);
         vertexconsumer.a(matrix4f, 0.0F, 0.0F, -0.01F).a(-1).a(0.0F, 0.0F).b(C_4474_.f_118083_).c(lightmapIn).b(0.0F, 1.0F, 0.0F);
         int k = 0;

         for (C_2767_ mapdecoration : this.b.m_164811_()) {
            if (!skipDecorationsIn || mapdecoration.m_77809_()) {
               matrixStackIn.a();
               matrixStackIn.a(0.0F + (float)mapdecoration.f_77792_() / 2.0F + 64.0F, 0.0F + (float)mapdecoration.f_77793_() / 2.0F + 64.0F, -0.02F);
               matrixStackIn.a(C_252363_.f_252403_.m_252977_((float)(mapdecoration.f_77794_() * 360) / 16.0F));
               matrixStackIn.b(4.0F, 4.0F, 3.0F);
               matrixStackIn.a(-0.125F, 0.125F, 0.0F);
               Matrix4f matrix4f1 = matrixStackIn.c().a();
               float f1 = -0.001F;
               TextureAtlasSprite textureatlassprite = MapRenderer.this.d.a(mapdecoration);
               float f2 = textureatlassprite.c();
               float f3 = textureatlassprite.g();
               float f4 = textureatlassprite.d();
               float f5 = textureatlassprite.h();
               VertexConsumer vertexconsumer1 = bufferIn.getBuffer(RenderType.t(textureatlassprite.i()));
               vertexconsumer1.a(matrix4f1, -1.0F, 1.0F, (float)k * -0.001F).a(-1).a(f2, f3).b(C_4474_.f_118083_).c(lightmapIn).b(0.0F, 1.0F, 0.0F);
               vertexconsumer1.a(matrix4f1, 1.0F, 1.0F, (float)k * -0.001F).a(-1).a(f4, f3).b(C_4474_.f_118083_).c(lightmapIn).b(0.0F, 1.0F, 0.0F);
               vertexconsumer1.a(matrix4f1, 1.0F, -1.0F, (float)k * -0.001F).a(-1).a(f4, f5).b(C_4474_.f_118083_).c(lightmapIn).b(0.0F, 1.0F, 0.0F);
               vertexconsumer1.a(matrix4f1, -1.0F, -1.0F, (float)k * -0.001F).a(-1).a(f2, f5).b(C_4474_.f_118083_).c(lightmapIn).b(0.0F, 1.0F, 0.0F);
               matrixStackIn.b();
               if (mapdecoration.f_77795_().isPresent()) {
                  Font font = C_3391_.m_91087_().h;
                  C_4996_ component = (C_4996_)mapdecoration.f_77795_().get();
                  float f6 = (float)font.a(component);
                  float f7 = Mth.a(25.0F / f6, 0.0F, 0.6666667F);
                  matrixStackIn.a();
                  matrixStackIn.a(
                     0.0F + (float)mapdecoration.f_77792_() / 2.0F + 64.0F - f6 * f7 / 2.0F,
                     0.0F + (float)mapdecoration.f_77793_() / 2.0F + 64.0F + 4.0F,
                     -0.025F
                  );
                  matrixStackIn.b(f7, f7, 1.0F);
                  matrixStackIn.a(0.0F, 0.0F, -0.1F);
                  font.a(component, 0.0F, 0.0F, -1, false, matrixStackIn.c().a(), bufferIn, Font.a.a, Integer.MIN_VALUE, lightmapIn);
                  matrixStackIn.b();
               }

               k++;
            }
         }
      }

      public void close() {
         this.c.close();
      }
   }
}
