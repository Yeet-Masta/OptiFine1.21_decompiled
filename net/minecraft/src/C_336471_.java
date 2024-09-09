package net.minecraft.src;

import it.unimi.dsi.fastutil.ints.IntConsumer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import javax.annotation.Nullable;
import net.optifine.render.MultiTextureBuilder;
import net.optifine.render.MultiTextureData;
import org.apache.commons.lang3.mutable.MutableLong;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class C_336471_ implements AutoCloseable {
   private final C_336589_.C_336543_ f_337070_;
   @Nullable
   private C_336589_.C_336543_ f_337083_;
   private final C_336447_ f_337640_;
   private MultiTextureData multiTextureData;

   public C_336471_(C_336589_.C_336543_ vertexBufferIn, C_336447_ drawStateIn) {
      this(vertexBufferIn, drawStateIn, (MultiTextureData)null);
   }

   public C_336471_(C_336589_.C_336543_ vertexBufferIn, C_336447_ drawStateIn, MultiTextureData multiTextureDataIn) {
      this.f_337070_ = vertexBufferIn;
      this.f_337640_ = drawStateIn;
      this.multiTextureData = multiTextureDataIn;
   }

   private static Vector3f[] m_340126_(ByteBuffer bufferIn, int vertexCountIn, C_3188_ vertexFormatIn) {
      int i = vertexFormatIn.m_338798_(C_3189_.f_336661_);
      if (i == -1) {
         throw new IllegalArgumentException("Cannot identify quad centers with no position element");
      } else {
         FloatBuffer floatbuffer = bufferIn.asFloatBuffer();
         int j = vertexFormatIn.m_86020_() / 4;
         int k = j * 4;
         int l = vertexCountIn / 4;
         Vector3f[] avector3f = new Vector3f[l];

         for(int i1 = 0; i1 < l; ++i1) {
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

   public ByteBuffer m_340620_() {
      return this.f_337070_.m_338393_();
   }

   @Nullable
   public ByteBuffer m_339370_() {
      return this.f_337083_ != null ? this.f_337083_.m_338393_() : null;
   }

   public C_336447_ m_339246_() {
      return this.f_337640_;
   }

   @Nullable
   public C_336506_ m_338666_(C_336589_ builderIn, C_276405_ sortingIn) {
      if (this.f_337640_.f_336934_() != C_3188_.C_141549_.QUADS) {
         return null;
      } else {
         Vector3f[] avector3f = m_340126_(this.f_337070_.m_338393_(), this.f_337640_.f_336624_(), this.f_337640_.f_336748_());
         C_336506_ meshdata$sortstate = new C_336506_(avector3f, this.f_337640_.f_337180_(), this.multiTextureData);
         this.f_337083_ = meshdata$sortstate.m_340180_(builderIn, sortingIn);
         return meshdata$sortstate;
      }
   }

   public void close() {
      this.f_337070_.close();
      if (this.f_337083_ != null) {
         this.f_337083_.close();
      }

   }

   public MultiTextureData getMultiTextureData() {
      return this.multiTextureData;
   }

   public String toString() {
      String var10000 = String.valueOf(this.f_337070_);
      return "vertexBuffer: (" + var10000 + "), indexBuffer: (" + String.valueOf(this.f_337083_) + "), drawState: (" + String.valueOf(this.f_337640_) + ")";
   }

   public static record C_336447_(C_3188_ f_336748_, int f_336624_, int f_337456_, C_3188_.C_141549_ f_336934_, C_3188_.C_141548_ f_337180_) {
      public C_336447_(C_3188_ format, int vertexCount, int indexCount, C_3188_.C_141549_ mode, C_3188_.C_141548_ indexType) {
         this.f_336748_ = format;
         this.f_336624_ = vertexCount;
         this.f_337456_ = indexCount;
         this.f_336934_ = mode;
         this.f_337180_ = indexType;
      }

      public int getVertexBufferSize() {
         return this.f_336624_ * this.f_336748_.m_86020_();
      }

      public C_3188_ f_336748_() {
         return this.f_336748_;
      }

      public int f_336624_() {
         return this.f_336624_;
      }

      public int f_337456_() {
         return this.f_337456_;
      }

      public C_3188_.C_141549_ f_336934_() {
         return this.f_336934_;
      }

      public C_3188_.C_141548_ f_337180_() {
         return this.f_337180_;
      }
   }

   public static record C_336506_(Vector3f[] f_336944_, C_3188_.C_141548_ f_337501_, MultiTextureData multiTextureData) {
      public C_336506_(Vector3f[] centroids, C_3188_.C_141548_ indexType) {
         this(centroids, indexType, (MultiTextureData)null);
      }

      public C_336506_(Vector3f[] centroids, C_3188_.C_141548_ indexType, MultiTextureData multiTextureData) {
         this.f_336944_ = centroids;
         this.f_337501_ = indexType;
         this.multiTextureData = multiTextureData;
      }

      @Nullable
      public C_336589_.C_336543_ m_340180_(C_336589_ builderIn, C_276405_ sortingIn) {
         int[] aint = sortingIn.m_277065_(this.f_336944_);
         long i = builderIn.m_338881_(aint.length * 6 * this.f_337501_.f_166924_);
         IntConsumer intconsumer = this.m_338675_(i, this.f_337501_);
         int[] var7 = aint;
         int var8 = aint.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            int j = var7[var9];
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

         return builderIn.m_339207_();
      }

      private IntConsumer m_338675_(long ptrIn, C_3188_.C_141548_ typeIn) {
         MutableLong mutablelong = new MutableLong(ptrIn);
         IntConsumer var10000;
         switch (typeIn) {
            case SHORT:
               var10000 = (valIn) -> {
                  MemoryUtil.memPutShort(mutablelong.getAndAdd(2L), (short)valIn);
               };
               break;
            case INT:
               var10000 = (valIn) -> {
                  MemoryUtil.memPutInt(mutablelong.getAndAdd(4L), valIn);
               };
               break;
            default:
               throw new MatchException((String)null, (Throwable)null);
         }

         return var10000;
      }

      public Vector3f[] f_336944_() {
         return this.f_336944_;
      }

      public C_3188_.C_141548_ f_337501_() {
         return this.f_337501_;
      }

      public MultiTextureData multiTextureData() {
         return this.multiTextureData;
      }
   }
}
