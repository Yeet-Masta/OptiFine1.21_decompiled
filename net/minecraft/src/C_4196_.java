package net.minecraft.src;

import net.optifine.Config;
import net.optifine.model.BakedQuadRetextured;
import net.optifine.model.QuadBounds;
import net.optifine.render.QuadVertexPositions;
import net.optifine.render.VertexPosition;

public class C_4196_ {
   protected int[] f_111292_;
   protected final int f_111293_;
   protected C_4687_ f_111294_;
   protected C_4486_ f_111295_;
   private final boolean f_111296_;
   private boolean hasAmbientOcclusion;
   private int[] vertexDataSingle;
   private QuadBounds quadBounds;
   private boolean quadEmissiveChecked;
   private C_4196_ quadEmissive;
   private QuadVertexPositions quadVertexPositions;

   public C_4196_(int[] vertexDataIn, int tintIndexIn, C_4687_ faceIn, C_4486_ spriteIn, boolean shadeIn) {
      this.hasAmbientOcclusion = true;
      this.vertexDataSingle = null;
      this.f_111292_ = vertexDataIn;
      this.f_111293_ = tintIndexIn;
      this.f_111294_ = faceIn;
      this.f_111295_ = spriteIn;
      this.f_111296_ = shadeIn;
      this.fixVertexData();
   }

   public C_4196_(int[] vertexDataIn, int tintIndexIn, C_4687_ faceIn, C_4486_ spriteIn, boolean shadeIn, boolean hasAmbientOcclusion) {
      this(vertexDataIn, tintIndexIn, faceIn, spriteIn, shadeIn);
      this.hasAmbientOcclusion = hasAmbientOcclusion;
   }

   public C_4486_ m_173410_() {
      if (this.f_111295_ == null) {
         this.f_111295_ = getSpriteByUv(this.m_111303_());
      }

      return this.f_111295_;
   }

   public int[] m_111303_() {
      this.fixVertexData();
      return this.f_111292_;
   }

   public boolean m_111304_() {
      return this.f_111293_ != -1;
   }

   public int m_111305_() {
      return this.f_111293_;
   }

   public C_4687_ m_111306_() {
      if (this.f_111294_ == null) {
         this.f_111294_ = C_4211_.m_111612_(this.m_111303_());
      }

      return this.f_111294_;
   }

   public boolean m_111307_() {
      return this.f_111296_;
   }

   public int[] getVertexDataSingle() {
      if (this.vertexDataSingle == null) {
         this.vertexDataSingle = makeVertexDataSingle(this.m_111303_(), this.m_173410_());
      }

      if (this.vertexDataSingle.length != this.m_111303_().length) {
         this.vertexDataSingle = makeVertexDataSingle(this.m_111303_(), this.m_173410_());
      }

      return this.vertexDataSingle;
   }

   private static int[] makeVertexDataSingle(int[] vd, C_4486_ sprite) {
      int[] vdSingle = (int[])vd.clone();
      int step = vdSingle.length / 4;

      for(int i = 0; i < 4; ++i) {
         int pos = i * step;
         float tu = Float.intBitsToFloat(vdSingle[pos + 4]);
         float tv = Float.intBitsToFloat(vdSingle[pos + 4 + 1]);
         float u = sprite.toSingleU(tu);
         float v = sprite.toSingleV(tv);
         vdSingle[pos + 4] = Float.floatToRawIntBits(u);
         vdSingle[pos + 4 + 1] = Float.floatToRawIntBits(v);
      }

      return vdSingle;
   }

   private static C_4486_ getSpriteByUv(int[] vertexData) {
      float uMin = 1.0F;
      float vMin = 1.0F;
      float uMax = 0.0F;
      float vMax = 0.0F;
      int step = vertexData.length / 4;

      for(int i = 0; i < 4; ++i) {
         int pos = i * step;
         float tu = Float.intBitsToFloat(vertexData[pos + 4]);
         float tv = Float.intBitsToFloat(vertexData[pos + 4 + 1]);
         uMin = Math.min(uMin, tu);
         vMin = Math.min(vMin, tv);
         uMax = Math.max(uMax, tu);
         vMax = Math.max(vMax, tv);
      }

      float uMid = (uMin + uMax) / 2.0F;
      float vMid = (vMin + vMax) / 2.0F;
      C_4486_ spriteUv = Config.getTextureMap().getIconByUV((double)uMid, (double)vMid);
      return spriteUv;
   }

   protected void fixVertexData() {
      if (Config.isShaders()) {
         if (this.f_111292_.length == C_3179_.BLOCK_VANILLA_SIZE) {
            this.f_111292_ = fixVertexDataSize(this.f_111292_, C_3179_.BLOCK_SHADERS_SIZE);
         }
      } else if (this.f_111292_.length == C_3179_.BLOCK_SHADERS_SIZE) {
         this.f_111292_ = fixVertexDataSize(this.f_111292_, C_3179_.BLOCK_VANILLA_SIZE);
      }

   }

   private static int[] fixVertexDataSize(int[] vd, int sizeNew) {
      int step = vd.length / 4;
      int stepNew = sizeNew / 4;
      int[] vdNew = new int[stepNew * 4];

      for(int i = 0; i < 4; ++i) {
         int len = Math.min(step, stepNew);
         System.arraycopy(vd, i * step, vdNew, i * stepNew, len);
      }

      return vdNew;
   }

   public QuadBounds getQuadBounds() {
      if (this.quadBounds == null) {
         this.quadBounds = new QuadBounds(this.m_111303_());
      }

      return this.quadBounds;
   }

   public float getMidX() {
      QuadBounds qb = this.getQuadBounds();
      return (qb.getMaxX() + qb.getMinX()) / 2.0F;
   }

   public double getMidY() {
      QuadBounds qb = this.getQuadBounds();
      return (double)((qb.getMaxY() + qb.getMinY()) / 2.0F);
   }

   public double getMidZ() {
      QuadBounds qb = this.getQuadBounds();
      return (double)((qb.getMaxZ() + qb.getMinZ()) / 2.0F);
   }

   public boolean isFaceQuad() {
      QuadBounds qb = this.getQuadBounds();
      return qb.isFaceQuad(this.f_111294_);
   }

   public boolean isFullQuad() {
      QuadBounds qb = this.getQuadBounds();
      return qb.isFullQuad(this.f_111294_);
   }

   public boolean isFullFaceQuad() {
      return this.isFullQuad() && this.isFaceQuad();
   }

   public C_4196_ getQuadEmissive() {
      if (this.quadEmissiveChecked) {
         return this.quadEmissive;
      } else {
         if (this.quadEmissive == null && this.f_111295_ != null && this.f_111295_.spriteEmissive != null) {
            this.quadEmissive = new BakedQuadRetextured(this, this.f_111295_.spriteEmissive);
         }

         this.quadEmissiveChecked = true;
         return this.quadEmissive;
      }
   }

   public VertexPosition[] getVertexPositions(int key) {
      if (this.quadVertexPositions == null) {
         this.quadVertexPositions = new QuadVertexPositions();
      }

      return (VertexPosition[])this.quadVertexPositions.get(key);
   }

   public boolean hasAmbientOcclusion() {
      return this.hasAmbientOcclusion;
   }

   public String toString() {
      int var10000 = this.f_111292_.length;
      return "vertexData: " + var10000 + ", tint: " + this.f_111293_ + ", facing: " + String.valueOf(this.f_111294_) + ", sprite: " + String.valueOf(this.f_111295_);
   }
}
