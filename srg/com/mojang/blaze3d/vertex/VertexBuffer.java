package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.RenderSystem$AutoStorageIndexBuffer;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
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
   private final VertexBuffer.Usage f_285574_;
   private int f_231217_;
   private int f_166860_;
   private int f_166862_;
   @Nullable
   private VertexFormat f_85917_;
   @Nullable
   private RenderSystem$AutoStorageIndexBuffer f_166865_;
   private VertexFormat.IndexType f_166861_;
   private int f_166863_;
   private VertexFormat.Mode f_166864_;
   private VboRegion vboRegion;
   private VboRange vboRange;
   private MultiTextureData multiTextureData;
   private static ByteBuffer emptyBuffer = GlUtil.m_166247_(0);

   public VertexBuffer(VertexBuffer.Usage usageIn) {
      this.f_285574_ = usageIn;
      RenderSystem.assertOnRenderThread();
      this.f_231217_ = GlStateManager._glGenBuffers();
      this.f_166860_ = GlStateManager._glGenBuffers();
      this.f_166862_ = GlStateManager._glGenVertexArrays();
   }

   public void m_231221_(MeshData bufferIn) {
      MeshData meshdata = bufferIn;

      label48: {
         try {
            if (this.m_231230_()) {
               break label48;
            }

            RenderSystem.assertOnRenderThread();
            GpuMemory.bufferFreed((long)this.getVertexBufferSize());
            GpuMemory.bufferFreed((long)this.getIndexBufferSize());
            MeshData.DrawState meshdata$drawstate = bufferIn.m_339246_();
            this.f_85917_ = this.m_231218_(meshdata$drawstate, bufferIn.m_340620_());
            this.f_166865_ = this.m_231223_(meshdata$drawstate, bufferIn.m_339370_());
            this.f_166863_ = meshdata$drawstate.f_337456_();
            this.f_166861_ = meshdata$drawstate.f_337180_();
            this.f_166864_ = meshdata$drawstate.f_336934_();
            GpuMemory.bufferAllocated((long)this.getVertexBufferSize());
            GpuMemory.bufferAllocated((long)this.getIndexBufferSize());
            if (this.vboRegion != null) {
               ByteBuffer data = bufferIn.m_340620_();
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

   public void m_338802_(ByteBufferBuilder.Result resultIn) {
      ByteBufferBuilder.Result bytebufferbuilder$result = resultIn;

      label42: {
         try {
            if (this.m_231230_()) {
               break label42;
            }

            RenderSystem.assertOnRenderThread();
            GlStateManager._glBindBuffer(34963, this.f_166860_);
            RenderSystem.glBufferData(34963, resultIn.m_338393_(), this.f_285574_.f_285654_);
            this.f_166865_ = null;
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

   private VertexFormat m_231218_(MeshData.DrawState drawStateIn, @Nullable ByteBuffer bufferIn) {
      if (this.vboRegion != null) {
         return drawStateIn.f_336748_();
      } else {
         boolean flag = false;
         if (!drawStateIn.f_336748_().equals(this.f_85917_)) {
            if (this.f_85917_ != null) {
               this.f_85917_.m_86024_();
            }

            GlStateManager._glBindBuffer(34962, this.f_231217_);
            drawStateIn.f_336748_().m_166912_();
            if (Config.isShaders()) {
               ShadersRender.setupArrayPointersVbo();
            }

            flag = true;
         }

         if (bufferIn != null) {
            if (!flag) {
               GlStateManager._glBindBuffer(34962, this.f_231217_);
            }

            RenderSystem.glBufferData(34962, bufferIn, this.f_285574_.f_285654_);
         }

         return drawStateIn.f_336748_();
      }
   }

   @Nullable
   private RenderSystem$AutoStorageIndexBuffer m_231223_(MeshData.DrawState drawStateIn, @Nullable ByteBuffer bufferIn) {
      if (bufferIn != null) {
         if (this.vboRegion != null) {
            return null;
         } else {
            GlStateManager._glBindBuffer(34963, this.f_166860_);
            RenderSystem.glBufferData(34963, bufferIn, this.f_285574_.f_285654_);
            return null;
         }
      } else {
         RenderSystem$AutoStorageIndexBuffer rendersystem$autostorageindexbuffer = RenderSystem.getSequentialBuffer(drawStateIn.f_336934_());
         int indexCount = drawStateIn.f_337456_();
         if (this.vboRegion != null && drawStateIn.f_336934_() == VertexFormat.Mode.QUADS) {
            indexCount = 65536;
         }

         if (rendersystem$autostorageindexbuffer != this.f_166865_ || !rendersystem$autostorageindexbuffer.m_221944_(indexCount)) {
            rendersystem$autostorageindexbuffer.m_221946_(indexCount);
         }

         return rendersystem$autostorageindexbuffer;
      }
   }

   public void m_85921_() {
      BufferUploader.m_231208_();
      if (this.f_166862_ >= 0) {
         GlStateManager._glBindVertexArray(this.f_166862_);
      }
   }

   public static void m_85931_() {
      BufferUploader.m_231208_();
      GlStateManager._glBindVertexArray(0);
   }

   public void m_166882_() {
      if (this.vboRegion != null) {
         this.vboRegion.drawArrays(VertexFormat.Mode.QUADS, this.vboRange);
      } else if (this.multiTextureData != null) {
         MultiTextureRenderer.draw(this.f_166864_, this.m_231231_().f_166923_, this.multiTextureData);
      } else {
         RenderSystem.drawElements(this.f_166864_.f_166946_, this.f_166863_, this.m_231231_().f_166923_);
      }
   }

   private VertexFormat.IndexType m_231231_() {
      RenderSystem$AutoStorageIndexBuffer rendersystem$autostorageindexbuffer = this.f_166865_;
      return rendersystem$autostorageindexbuffer != null ? rendersystem$autostorageindexbuffer.m_157483_() : this.f_166861_;
   }

   public void m_253207_(Matrix4f matrixIn, Matrix4f projectionIn, ShaderInstance shaderIn) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.m_166876_(new Matrix4f(matrixIn), new Matrix4f(projectionIn), shaderIn));
      } else {
         this.m_166876_(matrixIn, projectionIn, shaderIn);
      }
   }

   private void m_166876_(Matrix4f matrixIn, Matrix4f projectionIn, ShaderInstance shaderIn) {
      shaderIn.m_340471_(this.f_166864_, matrixIn, projectionIn, Minecraft.m_91087_().m_91268_());
      shaderIn.m_173363_();
      boolean isShaders = Config.isShaders() && Shaders.isRenderingWorld;
      boolean isShaderArrays = isShaders && SVertexBuilder.preDrawArrays(this.f_85917_);
      if (isShaders) {
         Shaders.setModelViewMatrix(matrixIn);
         Shaders.setProjectionMatrix(projectionIn);
         Shaders.setTextureMatrix(RenderSystem.getTextureMatrix());
         Shaders.setColorModulator(RenderSystem.getShaderColor());
      }

      this.m_166882_();
      if (isShaderArrays) {
         SVertexBuilder.postDrawArrays();
      }

      shaderIn.m_173362_();
   }

   public void close() {
      if (this.f_231217_ >= 0) {
         RenderSystem.glDeleteBuffers(this.f_231217_);
         this.f_231217_ = -1;
         GpuMemory.bufferFreed((long)this.getVertexBufferSize());
      }

      if (this.f_166860_ >= 0) {
         RenderSystem.glDeleteBuffers(this.f_166860_);
         this.f_166860_ = -1;
         GpuMemory.bufferFreed((long)this.getIndexBufferSize());
      }

      if (this.f_166862_ >= 0) {
         RenderSystem.glDeleteVertexArrays(this.f_166862_);
         this.f_166862_ = -1;
      }

      this.f_166863_ = 0;
   }

   public VertexFormat m_166892_() {
      return this.f_85917_;
   }

   public boolean m_231230_() {
      return this.vboRegion != null ? false : this.f_166862_ == -1;
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
      return this.f_166863_ <= 0;
   }

   public void clearData() {
      if (this.f_166863_ > 0) {
         if (this.vboRegion != null) {
            this.vboRegion.bufferData(emptyBuffer, this.vboRange);
            this.f_166863_ = 0;
         } else {
            this.m_85921_();
            if (this.f_231217_ >= 0) {
               GlStateManager._glBindBuffer(34962, this.f_231217_);
               GlStateManager._glBufferData(34962, 0L, this.f_285574_.f_285654_);
               GpuMemory.bufferFreed((long)this.getVertexBufferSize());
            }

            if (this.f_166860_ >= 0 && this.f_166865_ == null) {
               GlStateManager._glBindBuffer(34963, this.f_166860_);
               GlStateManager._glBufferData(34963, 0L, this.f_285574_.f_285654_);
               GpuMemory.bufferFreed((long)this.getIndexBufferSize());
            }

            m_85931_();
            this.f_166863_ = 0;
         }
      }
   }

   public int getIndexCount() {
      return this.f_166863_;
   }

   private int getVertexBufferSize() {
      return this.f_85917_ == null ? 0 : this.f_166863_ * this.f_85917_.m_86020_();
   }

   private int getIndexBufferSize() {
      if (this.f_166865_ != null) {
         return 0;
      } else {
         return this.f_166861_ == null ? 0 : this.f_166863_ * this.f_166861_.f_166924_;
      }
   }

   public void updateMultiTextureData() {
      if (this.multiTextureData != null) {
         this.multiTextureData.applySort();
      }
   }

   public static enum Usage {
      STATIC(35044),
      DYNAMIC(35048);

      final int f_285654_;

      private Usage(final int idIn) {
         this.f_285654_ = idIn;
      }
   }
}
