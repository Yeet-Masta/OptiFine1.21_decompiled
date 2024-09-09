import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.RenderSystem$C_141293_;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.minecraft.src.C_3139_;
import net.minecraft.src.C_3391_;
import net.optifine.Config;
import net.optifine.render.MultiTextureData;
import net.optifine.render.MultiTextureRenderer;
import net.optifine.render.VboRange;
import net.optifine.render.VboRegion;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.GpuMemory;
import org.joml.Matrix4f;

public class VertexBuffer implements AutoCloseable {
   private final VertexBuffer.a a;
   private int b;
   private int c;
   private int d;
   @Nullable
   private VertexFormat e;
   @Nullable
   private RenderSystem$C_141293_ f;
   private VertexFormat.b g;
   private int h;
   private VertexFormat.c i;
   private VboRegion vboRegion;
   private VboRange vboRange;
   private MultiTextureData multiTextureData;
   private static ByteBuffer emptyBuffer = C_3139_.m_166247_(0);

   public VertexBuffer(VertexBuffer.a usageIn) {
      this.a = usageIn;
      RenderSystem.assertOnRenderThread();
      this.b = GlStateManager._glGenBuffers();
      this.c = GlStateManager._glGenBuffers();
      this.d = GlStateManager._glGenVertexArrays();
   }

   public void a(MeshData bufferIn) {
      MeshData meshdata = bufferIn;

      label48: {
         try {
            if (this.e()) {
               break label48;
            }

            RenderSystem.assertOnRenderThread();
            GpuMemory.bufferFreed((long)this.getVertexBufferSize());
            GpuMemory.bufferFreed((long)this.getIndexBufferSize());
            MeshData.a meshdata$drawstate = bufferIn.c();
            this.e = this.a(meshdata$drawstate, bufferIn.a());
            this.f = this.b(meshdata$drawstate, bufferIn.b());
            this.h = meshdata$drawstate.c();
            this.g = meshdata$drawstate.e();
            this.i = meshdata$drawstate.d();
            GpuMemory.bufferAllocated((long)this.getVertexBufferSize());
            GpuMemory.bufferAllocated((long)this.getIndexBufferSize());
            if (this.vboRegion != null) {
               ByteBuffer data = bufferIn.a();
               data.position(0);
               data.limit(meshdata$drawstate.getVertexBufferSize());
               this.vboRegion.bufferData(data, this.vboRange);
               data.position(0);
               data.limit(meshdata$drawstate.getVertexBufferSize());
            }

            this.multiTextureData = bufferIn.getMultiTextureData();
            this.updateMultiTextureData();
         } catch (Throwable var7) {
            if (bufferIn != null) {
               try {
                  meshdata.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (bufferIn != null) {
            bufferIn.close();
         }

         return;
      }

      if (bufferIn != null) {
         bufferIn.close();
      }
   }

   public void a(ByteBufferBuilder.a resultIn) {
      ByteBufferBuilder.a bytebufferbuilder$result = resultIn;

      label42: {
         try {
            if (this.e()) {
               break label42;
            }

            RenderSystem.assertOnRenderThread();
            GlStateManager._glBindBuffer(34963, this.c);
            RenderSystem.glBufferData(34963, resultIn.a(), this.a.c);
            this.f = null;
            this.updateMultiTextureData();
         } catch (Throwable var6) {
            if (resultIn != null) {
               try {
                  bytebufferbuilder$result.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (resultIn != null) {
            resultIn.close();
         }

         return;
      }

      if (resultIn != null) {
         resultIn.close();
      }
   }

   private VertexFormat a(MeshData.a drawStateIn, @Nullable ByteBuffer bufferIn) {
      if (this.vboRegion != null) {
         return drawStateIn.a();
      } else {
         boolean flag = false;
         if (!drawStateIn.a().equals(this.e)) {
            if (this.e != null) {
               this.e.h();
            }

            GlStateManager._glBindBuffer(34962, this.b);
            drawStateIn.a().g();
            if (Config.isShaders()) {
               ShadersRender.setupArrayPointersVbo();
            }

            flag = true;
         }

         if (bufferIn != null) {
            if (!flag) {
               GlStateManager._glBindBuffer(34962, this.b);
            }

            RenderSystem.glBufferData(34962, bufferIn, this.a.c);
         }

         return drawStateIn.a();
      }
   }

   @Nullable
   private RenderSystem$C_141293_ b(MeshData.a drawStateIn, @Nullable ByteBuffer bufferIn) {
      if (bufferIn != null) {
         if (this.vboRegion != null) {
            return null;
         } else {
            GlStateManager._glBindBuffer(34963, this.c);
            RenderSystem.glBufferData(34963, bufferIn, this.a.c);
            return null;
         }
      } else {
         RenderSystem$C_141293_ rendersystem$autostorageindexbuffer = RenderSystem.getSequentialBuffer(drawStateIn.d());
         int indexCount = drawStateIn.c();
         if (this.vboRegion != null && drawStateIn.d() == VertexFormat.c.h) {
            indexCount = 65536;
         }

         if (rendersystem$autostorageindexbuffer != this.f || !rendersystem$autostorageindexbuffer.m_221944_(indexCount)) {
            rendersystem$autostorageindexbuffer.m_221946_(indexCount);
         }

         return rendersystem$autostorageindexbuffer;
      }
   }

   public void a() {
      BufferUploader.b();
      if (this.d >= 0) {
         GlStateManager._glBindVertexArray(this.d);
      }
   }

   public static void b() {
      BufferUploader.b();
      GlStateManager._glBindVertexArray(0);
   }

   public void c() {
      if (this.vboRegion != null) {
         this.vboRegion.drawArrays(VertexFormat.c.h, this.vboRange);
      } else if (this.multiTextureData != null) {
         MultiTextureRenderer.draw(this.i, this.f().c, this.multiTextureData);
      } else {
         RenderSystem.drawElements(this.i.i, this.h, this.f().c);
      }
   }

   private VertexFormat.b f() {
      RenderSystem$C_141293_ rendersystem$autostorageindexbuffer = this.f;
      return rendersystem$autostorageindexbuffer != null ? rendersystem$autostorageindexbuffer.a() : this.g;
   }

   public void a(Matrix4f matrixIn, Matrix4f projectionIn, ShaderInstance shaderIn) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.b(new Matrix4f(matrixIn), new Matrix4f(projectionIn), shaderIn));
      } else {
         this.b(matrixIn, projectionIn, shaderIn);
      }
   }

   private void b(Matrix4f matrixIn, Matrix4f projectionIn, ShaderInstance shaderIn) {
      shaderIn.a(this.i, matrixIn, projectionIn, C_3391_.m_91087_().aM());
      shaderIn.g();
      boolean isShaders = Config.isShaders() && Shaders.isRenderingWorld;
      boolean isShaderArrays = isShaders && SVertexBuilder.preDrawArrays(this.e);
      if (isShaders) {
         Shaders.setModelViewMatrix(matrixIn);
         Shaders.setProjectionMatrix(projectionIn);
         Shaders.setTextureMatrix(RenderSystem.getTextureMatrix());
         Shaders.setColorModulator(RenderSystem.getShaderColor());
      }

      this.c();
      if (isShaderArrays) {
         SVertexBuilder.postDrawArrays();
      }

      shaderIn.f();
   }

   public void close() {
      if (this.b >= 0) {
         RenderSystem.glDeleteBuffers(this.b);
         this.b = -1;
         GpuMemory.bufferFreed((long)this.getVertexBufferSize());
      }

      if (this.c >= 0) {
         RenderSystem.glDeleteBuffers(this.c);
         this.c = -1;
         GpuMemory.bufferFreed((long)this.getIndexBufferSize());
      }

      if (this.d >= 0) {
         RenderSystem.glDeleteVertexArrays(this.d);
         this.d = -1;
      }

      this.h = 0;
   }

   public VertexFormat d() {
      return this.e;
   }

   public boolean e() {
      return this.vboRegion != null ? false : this.d == -1;
   }

   public void setVboRegion(VboRegion vboRegion) {
      if (vboRegion != null) {
         this.close();
         this.vboRegion = vboRegion;
         this.vboRange = new VboRange();
      }
   }

   public VboRegion getVboRegion() {
      return this.vboRegion;
   }

   public boolean isEmpty() {
      return this.h <= 0;
   }

   public void clearData() {
      if (this.h > 0) {
         if (this.vboRegion != null) {
            this.vboRegion.bufferData(emptyBuffer, this.vboRange);
            this.h = 0;
         } else {
            this.a();
            if (this.b >= 0) {
               GlStateManager._glBindBuffer(34962, this.b);
               GlStateManager._glBufferData(34962, 0L, this.a.c);
               GpuMemory.bufferFreed((long)this.getVertexBufferSize());
            }

            if (this.c >= 0 && this.f == null) {
               GlStateManager._glBindBuffer(34963, this.c);
               GlStateManager._glBufferData(34963, 0L, this.a.c);
               GpuMemory.bufferFreed((long)this.getIndexBufferSize());
            }

            b();
            this.h = 0;
         }
      }
   }

   public int getIndexCount() {
      return this.h;
   }

   private int getVertexBufferSize() {
      return this.e == null ? 0 : this.h * this.e.b();
   }

   private int getIndexBufferSize() {
      if (this.f != null) {
         return 0;
      } else {
         return this.g == null ? 0 : this.h * this.g.d;
      }
   }

   public void updateMultiTextureData() {
      if (this.multiTextureData != null) {
         this.multiTextureData.applySort();
      }
   }

   public static enum a {
      a(35044),
      b(35048);

      final int c;

      private a(final int idIn) {
         this.c = idIn;
      }
   }
}
