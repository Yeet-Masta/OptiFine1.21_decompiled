package com.mojang.blaze3d.vertex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.BitSet;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ABGR32;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.render.BufferBuilderCache;
import net.optifine.render.MultiTextureBuilder;
import net.optifine.render.MultiTextureData;
import net.optifine.render.RenderEnv;
import net.optifine.render.VertexPosition;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.SVertexFormat;
import net.optifine.util.MathUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class BufferBuilder implements VertexConsumer {
   private static final long f_337720_ = -1L;
   private static final long f_337398_ = -1L;
   private static final boolean f_337242_;
   private final ByteBufferBuilder f_85648_;
   private long f_337311_;
   private int f_85654_;
   private final VertexFormat f_85658_;
   private final VertexFormat.Mode f_85657_;
   private final boolean f_85659_;
   private final boolean f_85660_;
   private final int f_337268_;
   private int f_336837_;
   private final int[] f_336980_;
   private int f_337476_;
   private boolean f_85661_;
   private RenderType renderType;
   private BufferBuilderCache cache;
   protected TextureAtlasSprite[] quadSprites;
   private TextureAtlasSprite quadSprite;
   private MultiTextureBuilder multiTextureBuilder;
   public SVertexBuilder sVertexBuilder;
   public RenderEnv renderEnv;
   public BitSet animatedSprites;
   private VertexPosition[] quadVertexPositions;
   protected MultiBufferSource.BufferSource renderTypeBuffer;
   private Vector3f midBlock;

   public BufferBuilder(ByteBufferBuilder byteBufferIn, VertexFormat.Mode drawModeIn, VertexFormat vertexFormatIn) {
      this(byteBufferIn, drawModeIn, vertexFormatIn, (RenderType)null);
   }

   public BufferBuilder(ByteBufferBuilder byteBufferIn, VertexFormat.Mode drawModeIn, VertexFormat vertexFormatIn, RenderType renderTypeIn) {
      this.f_337311_ = -1L;
      this.f_85661_ = true;
      if (!vertexFormatIn.m_339292_(VertexFormatElement.f_336661_)) {
         throw new IllegalArgumentException("Cannot build mesh with no position element");
      } else {
         this.f_85648_ = byteBufferIn;
         this.f_85657_ = drawModeIn;
         this.f_85658_ = vertexFormatIn;
         this.f_337268_ = vertexFormatIn.m_86020_();
         this.f_336837_ = vertexFormatIn.m_340128_() & ~VertexFormatElement.f_336661_.m_339950_();
         if (this.f_85658_.isExtended()) {
            this.f_336837_ = SVertexFormat.removeExtendedElements(this.f_336837_);
         }

         this.f_336980_ = vertexFormatIn.m_338562_();
         boolean flag = vertexFormatIn == DefaultVertexFormat.f_85812_;
         boolean flag1 = vertexFormatIn == DefaultVertexFormat.f_85811_;
         this.f_85659_ = flag || flag1;
         this.f_85660_ = flag;
         this.renderType = renderTypeIn;
         this.cache = this.f_85648_.getBufferBuilderCache();
         this.multiTextureBuilder = this.cache.getMultiTextureBuilder();
         this.sVertexBuilder = this.cache.getSVertexBuilder();
         this.renderEnv = this.cache.getRenderEnv();
         this.midBlock = this.cache.getMidBlock();
         if (Config.isShaders()) {
            SVertexBuilder.endSetVertexFormat(this);
         }

         if (Config.isMultiTexture()) {
            this.initQuadSprites();
         }

         if (SmartAnimations.isActive()) {
            if (this.animatedSprites == null) {
               this.animatedSprites = this.cache.getAnimatedSpritesCached();
            }

            this.animatedSprites.clear();
         } else if (this.animatedSprites != null) {
            this.animatedSprites = null;
         }

      }
   }

   @Nullable
   public MeshData m_339970_() {
      this.m_231176_();
      this.m_339377_();
      if (this.animatedSprites != null) {
         SmartAnimations.spritesRendered(this.animatedSprites);
      }

      MeshData meshdata = this.m_339394_();
      this.f_85661_ = true;
      this.f_85654_ = 0;
      this.f_337311_ = -1L;
      if (this.quadSprites != null) {
         this.cache.setQuadSpritesPrev(this.quadSprites);
      }

      this.quadSprites = null;
      this.quadSprite = null;
      return meshdata;
   }

   public MeshData m_339905_() {
      MeshData meshdata = this.m_339970_();
      if (meshdata == null) {
         throw new IllegalStateException("BufferBuilder was empty");
      } else {
         return meshdata;
      }
   }

   private void m_231176_() {
      if (!this.f_85661_) {
         throw new IllegalStateException("Not building!");
      }
   }

   @Nullable
   private MeshData m_339394_() {
      if (this.f_85654_ == 0) {
         return null;
      } else {
         ByteBufferBuilder.Result bytebufferbuilder$result = this.f_85648_.m_339207_();
         if (bytebufferbuilder$result == null) {
            return null;
         } else {
            int i = this.f_85657_.m_166958_(this.f_85654_);
            VertexFormat.IndexType vertexformat$indextype = VertexFormat.IndexType.m_166933_(this.f_85654_);
            MultiTextureData mtd = this.multiTextureBuilder.build(this.f_85654_, this.renderType, this.quadSprites, (int[])null);
            return new MeshData(bytebufferbuilder$result, new MeshData.DrawState(this.f_85658_, this.f_85654_, i, this.f_85657_, vertexformat$indextype), mtd);
         }
      }
   }

   private long m_340494_() {
      this.m_231176_();
      this.m_339377_();
      ++this.f_85654_;
      long i = this.f_85648_.m_338881_(this.f_337268_);
      this.checkCapacity();
      this.f_337311_ = i;
      return i;
   }

   private long beginVertices(int countIn) {
      this.m_231176_();
      this.m_339377_();
      this.f_85654_ += countIn;
      long i = this.f_85648_.m_338881_(this.f_337268_ * countIn);
      this.checkCapacity();
      this.f_337311_ = i;
      return i;
   }

   private long m_339847_(VertexFormatElement elementIn) {
      int i = this.f_337476_;
      int j = i & ~elementIn.m_339950_();
      if (j == i) {
         return -1L;
      } else {
         this.f_337476_ = j;
         long k = this.f_337311_;
         if (k == -1L) {
            throw new IllegalArgumentException("Not currently building vertex");
         } else {
            return k + (long)this.f_336980_[elementIn.f_337730_()];
         }
      }
   }

   private void m_339377_() {
      if (this.f_85654_ != 0) {
         if (this.f_337476_ != 0) {
            Stream var10000 = VertexFormatElement.m_339640_(this.f_337476_);
            VertexFormat var10001 = this.f_85658_;
            Objects.requireNonNull(var10001);
            String s = (String)var10000.map(var10001::m_340604_).collect(Collectors.joining(", "));
            throw new IllegalStateException("Missing elements in vertex: " + s);
         }

         if (this.f_85657_ == VertexFormat.Mode.LINES || this.f_85657_ == VertexFormat.Mode.LINE_STRIP) {
            long i = this.f_85648_.m_338881_(this.f_337268_);
            MemoryUtil.memCopy(i - (long)this.f_337268_, i, (long)this.f_337268_);
            ++this.f_85654_;
         }
      }

   }

   private static void m_340259_(long ptrIn, int argbIn) {
      int i = ABGR32.m_340609_(argbIn);
      MemoryUtil.memPutInt(ptrIn, f_337242_ ? i : Integer.reverseBytes(i));
   }

   private static void m_338383_(long ptrIn, int uvIn) {
      if (f_337242_) {
         MemoryUtil.memPutInt(ptrIn, uvIn);
      } else {
         MemoryUtil.memPutShort(ptrIn, (short)(uvIn & '\uffff'));
         MemoryUtil.memPutShort(ptrIn + 2L, (short)(uvIn >> 16 & '\uffff'));
      }

   }

   public VertexConsumer m_167146_(float x, float y, float z) {
      long i = this.m_340494_() + (long)this.f_336980_[VertexFormatElement.f_336661_.f_337730_()];
      this.f_337476_ = this.f_336837_;
      MemoryUtil.memPutFloat(i, x);
      MemoryUtil.memPutFloat(i + 4L, y);
      MemoryUtil.memPutFloat(i + 8L, z);
      return this;
   }

   public VertexConsumer m_167129_(int red, int green, int blue, int alpha) {
      long i = this.m_339847_(VertexFormatElement.f_336914_);
      if (i != -1L) {
         MemoryUtil.memPutByte(i, (byte)red);
         MemoryUtil.memPutByte(i + 1L, (byte)green);
         MemoryUtil.memPutByte(i + 2L, (byte)blue);
         MemoryUtil.memPutByte(i + 3L, (byte)alpha);
      }

      if (Config.isShaders() && this.f_337476_ == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   public VertexConsumer m_338399_(int argb) {
      long i = this.m_339847_(VertexFormatElement.f_336914_);
      if (i != -1L) {
         m_340259_(i, argb);
      }

      if (Config.isShaders() && this.f_337476_ == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   public VertexConsumer m_167083_(float u, float v) {
      if (this.quadSprite != null && this.quadSprites != null) {
         u = this.quadSprite.toSingleU(u);
         v = this.quadSprite.toSingleV(v);
         this.quadSprites[this.f_85654_ / 4] = this.quadSprite;
      }

      long i = this.m_339847_(VertexFormatElement.f_336642_);
      if (i != -1L) {
         MemoryUtil.memPutFloat(i, u);
         MemoryUtil.memPutFloat(i + 4L, v);
      }

      if (Config.isShaders() && this.f_337476_ == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   public VertexConsumer m_338369_(int u, int v) {
      return this.m_338494_((short)u, (short)v, VertexFormatElement.f_337543_);
   }

   public VertexConsumer m_338943_(int overlayUV) {
      long i = this.m_339847_(VertexFormatElement.f_337543_);
      if (i != -1L) {
         m_338383_(i, overlayUV);
      }

      if (Config.isShaders() && this.f_337476_ == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   public VertexConsumer m_338813_(int u, int v) {
      return this.m_338494_((short)u, (short)v, VertexFormatElement.f_337050_);
   }

   public VertexConsumer m_338973_(int lightmapUV) {
      long i = this.m_339847_(VertexFormatElement.f_337050_);
      if (i != -1L) {
         m_338383_(i, lightmapUV);
      }

      if (Config.isShaders() && this.f_337476_ == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   private VertexConsumer m_338494_(short u, short v, VertexFormatElement elementIn) {
      long i = this.m_339847_(elementIn);
      if (i != -1L) {
         MemoryUtil.memPutShort(i, u);
         MemoryUtil.memPutShort(i + 2L, v);
      }

      if (Config.isShaders() && this.f_337476_ == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   public VertexConsumer m_338525_(float x, float y, float z) {
      long i = this.m_339847_(VertexFormatElement.f_336839_);
      if (i != -1L) {
         MemoryUtil.memPutByte(i, m_338914_(x));
         MemoryUtil.memPutByte(i + 1L, m_338914_(y));
         MemoryUtil.memPutByte(i + 2L, m_338914_(z));
      }

      if (Config.isShaders() && this.f_337476_ == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   public static byte m_338914_(float val) {
      return (byte)((int)(Mth.m_14036_(val, -1.0F, 1.0F) * 127.0F) & 255);
   }

   public void m_338367_(float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
      if (this.f_85659_) {
         long i = this.m_340494_();
         MemoryUtil.memPutFloat(i + 0L, x);
         MemoryUtil.memPutFloat(i + 4L, y);
         MemoryUtil.memPutFloat(i + 8L, z);
         m_340259_(i + 12L, argb);
         MemoryUtil.memPutFloat(i + 16L, texU);
         MemoryUtil.memPutFloat(i + 20L, texV);
         long j;
         if (this.f_85660_) {
            m_338383_(i + 24L, overlayUV);
            j = i + 28L;
         } else {
            j = i + 24L;
         }

         m_338383_(j + 0L, lightmapUV);
         MemoryUtil.memPutByte(j + 4L, m_338914_(normalX));
         MemoryUtil.memPutByte(j + 5L, m_338914_(normalY));
         MemoryUtil.memPutByte(j + 6L, m_338914_(normalZ));
         if (Config.isShaders()) {
            SVertexBuilder.endAddVertex(this);
         }
      } else {
         VertexConsumer.super.m_338367_(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
      }

   }

   public void putSprite(TextureAtlasSprite sprite) {
      if (this.animatedSprites != null && sprite != null && sprite.isTerrain() && sprite.getAnimationIndex() >= 0) {
         this.animatedSprites.set(sprite.getAnimationIndex());
      }

      if (this.quadSprites != null) {
         int countQuads = this.f_85654_ / 4;
         this.quadSprites[countQuads] = sprite;
      }

   }

   public void setSprite(TextureAtlasSprite sprite) {
      if (this.animatedSprites != null && sprite != null && sprite.isTerrain() && sprite.getAnimationIndex() >= 0) {
         this.animatedSprites.set(sprite.getAnimationIndex());
      }

      if (this.quadSprites != null) {
         this.quadSprite = sprite;
      }

   }

   public boolean isMultiTexture() {
      return this.quadSprites != null;
   }

   public RenderType getRenderType() {
      return this.renderType;
   }

   private void initQuadSprites() {
      if (this.renderType != null) {
         if (this.renderType.isAtlasTextureBlocks()) {
            if (this.quadSprites == null) {
               if (this.f_85661_) {
                  if (this.f_85654_ > 0) {
                     MeshData data = this.m_339970_();
                     if (data != null) {
                        this.renderType.m_339876_(data);
                     }
                  }

                  this.quadSprites = this.cache.getQuadSpritesPrev();
                  if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
                     this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
                  }

               }
            }
         }
      }
   }

   private int getBufferQuadSize() {
      int vertexSize = this.f_85648_.getCapacity() / this.f_85658_.m_86020_();
      int quadSize = vertexSize / 4;
      return quadSize;
   }

   public RenderEnv getRenderEnv(BlockState blockStateIn, BlockPos blockPosIn) {
      if (this.renderEnv == null) {
         this.renderEnv = new RenderEnv(blockStateIn, blockPosIn);
         return this.renderEnv;
      } else {
         this.renderEnv.reset(blockStateIn, blockPosIn);
         return this.renderEnv;
      }
   }

   private static void quadsToTriangles(ByteBuffer byteBuffer, VertexFormat vertexFormat, int vertexCount, ByteBuffer byteBufferTriangles) {
      int vertexSize = vertexFormat.m_86020_();
      int limit = byteBuffer.limit();
      byteBuffer.rewind();
      byteBufferTriangles.clear();

      for(int v = 0; v < vertexCount; v += 4) {
         byteBuffer.limit((v + 3) * vertexSize);
         byteBuffer.position(v * vertexSize);
         byteBufferTriangles.put(byteBuffer);
         byteBuffer.limit((v + 1) * vertexSize);
         byteBuffer.position(v * vertexSize);
         byteBufferTriangles.put(byteBuffer);
         byteBuffer.limit((v + 2 + 2) * vertexSize);
         byteBuffer.position((v + 2) * vertexSize);
         byteBufferTriangles.put(byteBuffer);
      }

      byteBuffer.limit(limit);
      byteBuffer.rewind();
      byteBufferTriangles.flip();
   }

   public VertexFormat.Mode getDrawMode() {
      return this.f_85657_;
   }

   public int getVertexCount() {
      return this.f_85654_;
   }

   public Vector3f getTempVec3f() {
      return this.cache.getTempVec3f();
   }

   public float[] getTempFloat4(float f1, float f2, float f3, float f4) {
      float[] tempFloat4 = this.cache.getTempFloat4();
      tempFloat4[0] = f1;
      tempFloat4[1] = f2;
      tempFloat4[2] = f3;
      tempFloat4[3] = f4;
      return tempFloat4;
   }

   public int[] getTempInt4(int i1, int i2, int i3, int i4) {
      int[] tempInt4 = this.cache.getTempInt4();
      tempInt4[0] = i1;
      tempInt4[1] = i2;
      tempInt4[2] = i3;
      tempInt4[3] = i4;
      return tempInt4;
   }

   public int getBufferIntSize() {
      return this.f_85654_ * this.f_85658_.getIntegerSize();
   }

   public MultiBufferSource.BufferSource getRenderTypeBuffer() {
      return this.renderTypeBuffer;
   }

   public void setRenderTypeBuffer(MultiBufferSource.BufferSource renderTypeBuffer) {
      this.renderTypeBuffer = renderTypeBuffer;
   }

   public boolean canAddVertexText() {
      if (this.f_85658_.m_86020_() != DefaultVertexFormat.f_85820_.m_86020_()) {
         return false;
      } else {
         return this.f_337476_ == 0;
      }
   }

   public void addVertexText(Matrix4f mat4, float x, float y, float z, int col, float texU, float texV, int lightmapUV) {
      if (mat4 != null) {
         float xt = MathUtils.getTransformX(mat4, x, y, z);
         float yt = MathUtils.getTransformY(mat4, x, y, z);
         float zt = MathUtils.getTransformZ(mat4, x, y, z);
         x = xt;
         y = yt;
         z = zt;
      }

      long i = this.m_340494_();
      MemoryUtil.memPutFloat(i + 0L, x);
      MemoryUtil.memPutFloat(i + 4L, y);
      MemoryUtil.memPutFloat(i + 8L, z);
      MemoryUtil.memPutInt(i + 12L, col);
      MemoryUtil.memPutFloat(i + 16L, texU);
      MemoryUtil.memPutFloat(i + 20L, texV);
      m_338383_(i + 24L, lightmapUV);
      if (Config.isShaders()) {
         SVertexBuilder.endAddVertex(this);
      }

   }

   public boolean canAddVertexFast() {
      return this.f_85659_ && this.f_337476_ == 0 && this.f_85660_;
   }

   public void addVertexFast(float x, float y, float z, int color, float texU, float texV, int overlayUV, int lightmapUV, int normals) {
      long i = this.m_340494_();
      MemoryUtil.memPutFloat(i + 0L, x);
      MemoryUtil.memPutFloat(i + 4L, y);
      MemoryUtil.memPutFloat(i + 8L, z);
      m_340259_(i + 12L, color);
      MemoryUtil.memPutFloat(i + 16L, texU);
      MemoryUtil.memPutFloat(i + 20L, texV);
      m_338383_(i + 24L, overlayUV);
      m_338383_(i + 28L, lightmapUV);
      MemoryUtil.memPutInt(i + 32L, normals);
      if (Config.isShaders()) {
         SVertexBuilder.endAddVertex(this);
      }

   }

   public void setQuadVertexPositions(VertexPosition[] vps) {
      this.quadVertexPositions = vps;
   }

   public VertexPosition[] getQuadVertexPositions() {
      return this.quadVertexPositions;
   }

   public void setMidBlock(float mx, float my, float mz) {
      this.midBlock.set(mx, my, mz);
   }

   public Vector3f getMidBlock() {
      return this.midBlock;
   }

   public void putBulkData(ByteBuffer bufferIn) {
      if (Config.isShaders()) {
         SVertexBuilder.beginAddVertexData(this, bufferIn);
      }

      int addCount = bufferIn.limit() / this.f_85658_.m_86020_();
      this.beginVertices(addCount);
      ByteBuffer bb = this.getByteBuffer();
      bb.position(this.f_85648_.getNextResultOffset());
      bb.put(bufferIn);
      bb.position(0);
      if (Config.isShaders()) {
         SVertexBuilder.endAddVertexData(this);
      }

   }

   public void getBulkData(ByteBuffer bufferIn) {
      ByteBuffer bb = this.getByteBuffer();
      bb.position(this.f_85648_.getNextResultOffset());
      bb.limit(this.f_85648_.getWriteOffset());
      bufferIn.put(bb);
      bb.clear();
   }

   public VertexFormat getVertexFormat() {
      return this.f_85658_;
   }

   public int getStartPosition() {
      return this.f_85648_.getNextResultOffset();
   }

   public int getIntStartPosition() {
      return this.getStartPosition() / 4;
   }

   private int getNextElementBytes() {
      return this.f_85654_ * this.f_85658_.m_86020_();
   }

   public ByteBuffer getByteBuffer() {
      return this.f_85648_.getByteBuffer();
   }

   public FloatBuffer getFloatBuffer() {
      return this.f_85648_.getFloatBuffer();
   }

   public IntBuffer getIntBuffer() {
      return this.f_85648_.getIntBuffer();
   }

   private void checkCapacity() {
      if (this.quadSprites != null) {
         TextureAtlasSprite[] sprites = this.quadSprites;
         int quadSize = this.getBufferQuadSize() + 1;
         if (this.quadSprites.length < quadSize) {
            this.quadSprites = new TextureAtlasSprite[quadSize];
            System.arraycopy(sprites, 0, this.quadSprites, 0, Math.min(sprites.length, this.quadSprites.length));
            this.cache.setQuadSpritesPrev((TextureAtlasSprite[])null);
         }
      }

   }

   public boolean isDrawing() {
      return this.f_85661_;
   }

   public String toString() {
      String var10000 = String.valueOf(this.renderType != null ? this.renderType.getName() : this.renderType);
      return "renderType: " + var10000 + ", vertexFormat: " + this.f_85658_.getName() + ", vertexSize: " + this.f_337268_ + ", drawMode: " + String.valueOf(this.f_85657_) + ", vertexCount: " + this.f_85654_ + ", elementsLeft: " + Integer.bitCount(this.f_337476_) + "/" + Integer.bitCount(this.f_336837_) + ", byteBuffer: (" + String.valueOf(this.f_85648_) + ")";
   }

   static {
      f_337242_ = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
   }
}
