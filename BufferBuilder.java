import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.BitSet;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_174_.C_265822_;
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
   private static final long a = -1L;
   private static final long b = -1L;
   private static final boolean c = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
   private final ByteBufferBuilder d;
   private long e = -1L;
   private int f;
   private final VertexFormat g;
   private final VertexFormat.c h;
   private final boolean i;
   private final boolean j;
   private final int k;
   private int l;
   private final int[] m;
   private int n;
   private boolean o = true;
   private RenderType renderType;
   private BufferBuilderCache cache;
   protected TextureAtlasSprite[] quadSprites;
   private TextureAtlasSprite quadSprite;
   private MultiTextureBuilder multiTextureBuilder;
   public SVertexBuilder sVertexBuilder;
   public RenderEnv renderEnv;
   public BitSet animatedSprites;
   private VertexPosition[] quadVertexPositions;
   protected MultiBufferSource.a renderTypeBuffer;
   private Vector3f midBlock;

   public BufferBuilder(ByteBufferBuilder byteBufferIn, VertexFormat.c drawModeIn, VertexFormat vertexFormatIn) {
      this(byteBufferIn, drawModeIn, vertexFormatIn, null);
   }

   public BufferBuilder(ByteBufferBuilder byteBufferIn, VertexFormat.c drawModeIn, VertexFormat vertexFormatIn, RenderType renderTypeIn) {
      if (!vertexFormatIn.b(VertexFormatElement.b)) {
         throw new IllegalArgumentException("Cannot build mesh with no position element");
      } else {
         this.d = byteBufferIn;
         this.h = drawModeIn;
         this.g = vertexFormatIn;
         this.k = vertexFormatIn.b();
         this.l = vertexFormatIn.f() & ~VertexFormatElement.b.a();
         if (this.g.isExtended()) {
            this.l = SVertexFormat.removeExtendedElements(this.l);
         }

         this.m = vertexFormatIn.e();
         boolean flag = vertexFormatIn == DefaultVertexFormat.c;
         boolean flag1 = vertexFormatIn == DefaultVertexFormat.b;
         this.i = flag || flag1;
         this.j = flag;
         this.renderType = renderTypeIn;
         this.cache = this.d.getBufferBuilderCache();
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
   public MeshData a() {
      this.c();
      this.f();
      if (this.animatedSprites != null) {
         SmartAnimations.spritesRendered(this.animatedSprites);
      }

      MeshData meshdata = this.d();
      this.o = true;
      this.f = 0;
      this.e = -1L;
      if (this.quadSprites != null) {
         this.cache.setQuadSpritesPrev(this.quadSprites);
      }

      this.quadSprites = null;
      this.quadSprite = null;
      return meshdata;
   }

   public MeshData b() {
      MeshData meshdata = this.a();
      if (meshdata == null) {
         throw new IllegalStateException("BufferBuilder was empty");
      } else {
         return meshdata;
      }
   }

   private void c() {
      if (!this.o) {
         throw new IllegalStateException("Not building!");
      }
   }

   @Nullable
   private MeshData d() {
      if (this.f == 0) {
         return null;
      } else {
         ByteBufferBuilder.a bytebufferbuilder$result = this.d.a();
         if (bytebufferbuilder$result == null) {
            return null;
         } else {
            int i = this.h.a(this.f);
            VertexFormat.b vertexformat$indextype = VertexFormat.b.a(this.f);
            MultiTextureData mtd = this.multiTextureBuilder.build(this.f, this.renderType, this.quadSprites, null);
            return new MeshData(bytebufferbuilder$result, new MeshData.a(this.g, this.f, i, this.h, vertexformat$indextype), mtd);
         }
      }
   }

   private long e() {
      this.c();
      this.f();
      this.f++;
      long i = this.d.a(this.k);
      this.checkCapacity();
      this.e = i;
      return i;
   }

   private long beginVertices(int countIn) {
      this.c();
      this.f();
      this.f += countIn;
      long i = this.d.a(this.k * countIn);
      this.checkCapacity();
      this.e = i;
      return i;
   }

   private long a(VertexFormatElement elementIn) {
      int i = this.n;
      int j = i & ~elementIn.a();
      if (j == i) {
         return -1L;
      } else {
         this.n = j;
         long k = this.e;
         if (k == -1L) {
            throw new IllegalArgumentException("Not currently building vertex");
         } else {
            return k + (long)this.m[elementIn.c()];
         }
      }
   }

   private void f() {
      if (this.f != 0) {
         if (this.n != 0) {
            String s = (String)VertexFormatElement.b(this.n).map(this.g::c).collect(Collectors.joining(", "));
            throw new IllegalStateException("Missing elements in vertex: " + s);
         }

         if (this.h == VertexFormat.c.a || this.h == VertexFormat.c.b) {
            long i = this.d.a(this.k);
            MemoryUtil.memCopy(i - (long)this.k, i, (long)this.k);
            this.f++;
         }
      }
   }

   private static void a(long ptrIn, int argbIn) {
      int i = C_265822_.m_340609_(argbIn);
      MemoryUtil.memPutInt(ptrIn, c ? i : Integer.reverseBytes(i));
   }

   private static void b(long ptrIn, int uvIn) {
      if (c) {
         MemoryUtil.memPutInt(ptrIn, uvIn);
      } else {
         MemoryUtil.memPutShort(ptrIn, (short)(uvIn & 65535));
         MemoryUtil.memPutShort(ptrIn + 2L, (short)(uvIn >> 16 & 65535));
      }
   }

   @Override
   public VertexConsumer a(float x, float y, float z) {
      long i = this.e() + (long)this.m[VertexFormatElement.b.c()];
      this.n = this.l;
      MemoryUtil.memPutFloat(i, x);
      MemoryUtil.memPutFloat(i + 4L, y);
      MemoryUtil.memPutFloat(i + 8L, z);
      return this;
   }

   @Override
   public VertexConsumer a(int red, int green, int blue, int alpha) {
      long i = this.a(VertexFormatElement.c);
      if (i != -1L) {
         MemoryUtil.memPutByte(i, (byte)red);
         MemoryUtil.memPutByte(i + 1L, (byte)green);
         MemoryUtil.memPutByte(i + 2L, (byte)blue);
         MemoryUtil.memPutByte(i + 3L, (byte)alpha);
      }

      if (Config.isShaders() && this.n == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   @Override
   public VertexConsumer a(int argb) {
      long i = this.a(VertexFormatElement.c);
      if (i != -1L) {
         a(i, argb);
      }

      if (Config.isShaders() && this.n == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   @Override
   public VertexConsumer a(float u, float v) {
      if (this.quadSprite != null && this.quadSprites != null) {
         u = this.quadSprite.toSingleU(u);
         v = this.quadSprite.toSingleV(v);
         this.quadSprites[this.f / 4] = this.quadSprite;
      }

      long i = this.a(VertexFormatElement.d);
      if (i != -1L) {
         MemoryUtil.memPutFloat(i, u);
         MemoryUtil.memPutFloat(i + 4L, v);
      }

      if (Config.isShaders() && this.n == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   @Override
   public VertexConsumer a(int u, int v) {
      return this.a((short)u, (short)v, VertexFormatElement.f);
   }

   @Override
   public VertexConsumer b(int overlayUV) {
      long i = this.a(VertexFormatElement.f);
      if (i != -1L) {
         b(i, overlayUV);
      }

      if (Config.isShaders() && this.n == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   @Override
   public VertexConsumer b(int u, int v) {
      return this.a((short)u, (short)v, VertexFormatElement.g);
   }

   @Override
   public VertexConsumer c(int lightmapUV) {
      long i = this.a(VertexFormatElement.g);
      if (i != -1L) {
         b(i, lightmapUV);
      }

      if (Config.isShaders() && this.n == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   private VertexConsumer a(short u, short v, VertexFormatElement elementIn) {
      long i = this.a(elementIn);
      if (i != -1L) {
         MemoryUtil.memPutShort(i, u);
         MemoryUtil.memPutShort(i + 2L, v);
      }

      if (Config.isShaders() && this.n == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   @Override
   public VertexConsumer b(float x, float y, float z) {
      long i = this.a(VertexFormatElement.h);
      if (i != -1L) {
         MemoryUtil.memPutByte(i, a(x));
         MemoryUtil.memPutByte(i + 1L, a(y));
         MemoryUtil.memPutByte(i + 2L, a(z));
      }

      if (Config.isShaders() && this.n == 0) {
         SVertexBuilder.endAddVertex(this);
      }

      return this;
   }

   public static byte a(float val) {
      return (byte)((int)(Mth.a(val, -1.0F, 1.0F) * 127.0F) & 0xFF);
   }

   @Override
   public void a(float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
      if (this.i) {
         long i = this.e();
         MemoryUtil.memPutFloat(i + 0L, x);
         MemoryUtil.memPutFloat(i + 4L, y);
         MemoryUtil.memPutFloat(i + 8L, z);
         a(i + 12L, argb);
         MemoryUtil.memPutFloat(i + 16L, texU);
         MemoryUtil.memPutFloat(i + 20L, texV);
         long j;
         if (this.j) {
            b(i + 24L, overlayUV);
            j = i + 28L;
         } else {
            j = i + 24L;
         }

         b(j + 0L, lightmapUV);
         MemoryUtil.memPutByte(j + 4L, a(normalX));
         MemoryUtil.memPutByte(j + 5L, a(normalY));
         MemoryUtil.memPutByte(j + 6L, a(normalZ));
         if (Config.isShaders()) {
            SVertexBuilder.endAddVertex(this);
         }
      } else {
         VertexConsumer.super.a(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
      }
   }

   @Override
   public void putSprite(TextureAtlasSprite sprite) {
      if (this.animatedSprites != null && sprite != null && sprite.isTerrain() && sprite.getAnimationIndex() >= 0) {
         this.animatedSprites.set(sprite.getAnimationIndex());
      }

      if (this.quadSprites != null) {
         int countQuads = this.f / 4;
         this.quadSprites[countQuads] = sprite;
      }
   }

   @Override
   public void setSprite(TextureAtlasSprite sprite) {
      if (this.animatedSprites != null && sprite != null && sprite.isTerrain() && sprite.getAnimationIndex() >= 0) {
         this.animatedSprites.set(sprite.getAnimationIndex());
      }

      if (this.quadSprites != null) {
         this.quadSprite = sprite;
      }
   }

   @Override
   public boolean isMultiTexture() {
      return this.quadSprites != null;
   }

   @Override
   public RenderType getRenderType() {
      return this.renderType;
   }

   private void initQuadSprites() {
      if (this.renderType != null) {
         if (this.renderType.isAtlasTextureBlocks()) {
            if (this.quadSprites == null) {
               if (this.o) {
                  if (this.f > 0) {
                     MeshData data = this.a();
                     if (data != null) {
                        this.renderType.a(data);
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
      int vertexSize = this.d.getCapacity() / this.g.b();
      return vertexSize / 4;
   }

   @Override
   public RenderEnv getRenderEnv(BlockState blockStateIn, C_4675_ blockPosIn) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: Constructor net/optifine/render/RenderEnv.<init>(LBlockState;Lnet/minecraft/src/C_4675_;)V not found
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:49)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:957)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:460)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1018)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.AssignmentExprent.toJava(AssignmentExprent.java:154)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:241)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield BufferBuilder.renderEnv Lnet/optifine/render/RenderEnv;
      // 04: ifnonnull 19
      // 07: aload 0
      // 08: new net/optifine/render/RenderEnv
      // 0b: dup
      // 0c: aload 1
      // 0d: aload 2
      // 0e: invokespecial net/optifine/render/RenderEnv.<init> (LBlockState;Lnet/minecraft/src/C_4675_;)V
      // 11: putfield BufferBuilder.renderEnv Lnet/optifine/render/RenderEnv;
      // 14: aload 0
      // 15: getfield BufferBuilder.renderEnv Lnet/optifine/render/RenderEnv;
      // 18: areturn
      // 19: aload 0
      // 1a: getfield BufferBuilder.renderEnv Lnet/optifine/render/RenderEnv;
      // 1d: aload 1
      // 1e: aload 2
      // 1f: invokevirtual net/optifine/render/RenderEnv.reset (LBlockState;Lnet/minecraft/src/C_4675_;)V
      // 22: aload 0
      // 23: getfield BufferBuilder.renderEnv Lnet/optifine/render/RenderEnv;
      // 26: areturn
   }

   private static void quadsToTriangles(ByteBuffer byteBuffer, VertexFormat vertexFormat, int vertexCount, ByteBuffer byteBufferTriangles) {
      int vertexSize = vertexFormat.b();
      int limit = byteBuffer.limit();
      byteBuffer.rewind();
      byteBufferTriangles.clear();

      for (int v = 0; v < vertexCount; v += 4) {
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

   public VertexFormat.c getDrawMode() {
      return this.h;
   }

   @Override
   public int getVertexCount() {
      return this.f;
   }

   @Override
   public Vector3f getTempVec3f() {
      return this.cache.getTempVec3f();
   }

   @Override
   public float[] getTempFloat4(float f1, float f2, float f3, float f4) {
      float[] tempFloat4 = this.cache.getTempFloat4();
      tempFloat4[0] = f1;
      tempFloat4[1] = f2;
      tempFloat4[2] = f3;
      tempFloat4[3] = f4;
      return tempFloat4;
   }

   @Override
   public int[] getTempInt4(int i1, int i2, int i3, int i4) {
      int[] tempInt4 = this.cache.getTempInt4();
      tempInt4[0] = i1;
      tempInt4[1] = i2;
      tempInt4[2] = i3;
      tempInt4[3] = i4;
      return tempInt4;
   }

   public int getBufferIntSize() {
      return this.f * this.g.getIntegerSize();
   }

   @Override
   public MultiBufferSource.a getRenderTypeBuffer() {
      return this.renderTypeBuffer;
   }

   public void setRenderTypeBuffer(MultiBufferSource.a renderTypeBuffer) {
      this.renderTypeBuffer = renderTypeBuffer;
   }

   public boolean canAddVertexText() {
      return this.g.b() != DefaultVertexFormat.k.b() ? false : this.n == 0;
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

      long i = this.e();
      MemoryUtil.memPutFloat(i + 0L, x);
      MemoryUtil.memPutFloat(i + 4L, y);
      MemoryUtil.memPutFloat(i + 8L, z);
      MemoryUtil.memPutInt(i + 12L, col);
      MemoryUtil.memPutFloat(i + 16L, texU);
      MemoryUtil.memPutFloat(i + 20L, texV);
      b(i + 24L, lightmapUV);
      if (Config.isShaders()) {
         SVertexBuilder.endAddVertex(this);
      }
   }

   @Override
   public boolean canAddVertexFast() {
      return this.i && this.n == 0 && this.j;
   }

   @Override
   public void addVertexFast(float x, float y, float z, int color, float texU, float texV, int overlayUV, int lightmapUV, int normals) {
      long i = this.e();
      MemoryUtil.memPutFloat(i + 0L, x);
      MemoryUtil.memPutFloat(i + 4L, y);
      MemoryUtil.memPutFloat(i + 8L, z);
      a(i + 12L, color);
      MemoryUtil.memPutFloat(i + 16L, texU);
      MemoryUtil.memPutFloat(i + 20L, texV);
      b(i + 24L, overlayUV);
      b(i + 28L, lightmapUV);
      MemoryUtil.memPutInt(i + 32L, normals);
      if (Config.isShaders()) {
         SVertexBuilder.endAddVertex(this);
      }
   }

   @Override
   public void setQuadVertexPositions(VertexPosition[] vps) {
      this.quadVertexPositions = vps;
   }

   public VertexPosition[] getQuadVertexPositions() {
      return this.quadVertexPositions;
   }

   @Override
   public void setMidBlock(float mx, float my, float mz) {
      this.midBlock.set(mx, my, mz);
   }

   public Vector3f getMidBlock() {
      return this.midBlock;
   }

   @Override
   public void putBulkData(ByteBuffer bufferIn) {
      if (Config.isShaders()) {
         SVertexBuilder.beginAddVertexData(this, bufferIn);
      }

      int addCount = bufferIn.limit() / this.g.b();
      this.beginVertices(addCount);
      ByteBuffer bb = this.getByteBuffer();
      bb.position(this.d.getNextResultOffset());
      bb.put(bufferIn);
      bb.position(0);
      if (Config.isShaders()) {
         SVertexBuilder.endAddVertexData(this);
      }
   }

   @Override
   public void getBulkData(ByteBuffer bufferIn) {
      ByteBuffer bb = this.getByteBuffer();
      bb.position(this.d.getNextResultOffset());
      bb.limit(this.d.getWriteOffset());
      bufferIn.put(bb);
      bb.clear();
   }

   public VertexFormat getVertexFormat() {
      return this.g;
   }

   public int getStartPosition() {
      return this.d.getNextResultOffset();
   }

   public int getIntStartPosition() {
      return this.getStartPosition() / 4;
   }

   private int getNextElementBytes() {
      return this.f * this.g.b();
   }

   public ByteBuffer getByteBuffer() {
      return this.d.getByteBuffer();
   }

   public FloatBuffer getFloatBuffer() {
      return this.d.getFloatBuffer();
   }

   public IntBuffer getIntBuffer() {
      return this.d.getIntBuffer();
   }

   private void checkCapacity() {
      if (this.quadSprites != null) {
         TextureAtlasSprite[] sprites = this.quadSprites;
         int quadSize = this.getBufferQuadSize() + 1;
         if (this.quadSprites.length < quadSize) {
            this.quadSprites = new TextureAtlasSprite[quadSize];
            System.arraycopy(sprites, 0, this.quadSprites, 0, Math.min(sprites.length, this.quadSprites.length));
            this.cache.setQuadSpritesPrev(null);
         }
      }
   }

   public boolean isDrawing() {
      return this.o;
   }

   public String toString() {
      return "renderType: "
         + (this.renderType != null ? this.renderType.getName() : this.renderType)
         + ", vertexFormat: "
         + this.g.getName()
         + ", vertexSize: "
         + this.k
         + ", drawMode: "
         + this.h
         + ", vertexCount: "
         + this.f
         + ", elementsLeft: "
         + Integer.bitCount(this.n)
         + "/"
         + Integer.bitCount(this.l)
         + ", byteBuffer: ("
         + this.d
         + ")";
   }
}
