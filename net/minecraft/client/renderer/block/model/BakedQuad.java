package net.minecraft.client.renderer.block.model;

import net.optifine.Config;
import net.optifine.model.BakedQuadRetextured;
import net.optifine.model.QuadBounds;
import net.optifine.render.QuadVertexPositions;
import net.optifine.render.VertexPosition;

public class BakedQuad {
   protected int[] f_111292_;
   protected final int f_111293_;
   protected net.minecraft.core.Direction f_111294_;
   protected net.minecraft.client.renderer.texture.TextureAtlasSprite f_111295_;
   private final boolean f_111296_;
   private boolean hasAmbientOcclusion = true;
   private int[] vertexDataSingle = null;
   private QuadBounds quadBounds;
   private boolean quadEmissiveChecked;
   private net.minecraft.client.renderer.block.model.BakedQuad quadEmissive;
   private QuadVertexPositions quadVertexPositions;

   public BakedQuad(
      int[] vertexDataIn,
      int tintIndexIn,
      net.minecraft.core.Direction faceIn,
      net.minecraft.client.renderer.texture.TextureAtlasSprite spriteIn,
      boolean shadeIn
   ) {
      this.f_111292_ = vertexDataIn;
      this.f_111293_ = tintIndexIn;
      this.f_111294_ = faceIn;
      this.f_111295_ = spriteIn;
      this.f_111296_ = shadeIn;
      this.fixVertexData();
   }

   public BakedQuad(
      int[] vertexDataIn,
      int tintIndexIn,
      net.minecraft.core.Direction faceIn,
      net.minecraft.client.renderer.texture.TextureAtlasSprite spriteIn,
      boolean shadeIn,
      boolean hasAmbientOcclusion
   ) {
      this(vertexDataIn, tintIndexIn, faceIn, spriteIn, shadeIn);
      this.hasAmbientOcclusion = hasAmbientOcclusion;
   }

   public net.minecraft.client.renderer.texture.TextureAtlasSprite m_173410_() {
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

   public net.minecraft.core.Direction m_111306_() {
      if (this.f_111294_ == null) {
         this.f_111294_ = net.minecraft.client.renderer.block.model.FaceBakery.m_111612_(this.m_111303_());
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

   private static int[] makeVertexDataSingle(int[] vd, net.minecraft.client.renderer.texture.TextureAtlasSprite sprite) {
      int[] vdSingle = (int[])vd.clone();
      int step = vdSingle.length / 4;

      for (int i = 0; i < 4; i++) {
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

   private static net.minecraft.client.renderer.texture.TextureAtlasSprite getSpriteByUv(int[] vertexData) {
      float uMin = 1.0F;
      float vMin = 1.0F;
      float uMax = 0.0F;
      float vMax = 0.0F;
      int step = vertexData.length / 4;

      for (int i = 0; i < 4; i++) {
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
      return Config.getTextureMap().getIconByUV((double)uMid, (double)vMid);
   }

   protected void fixVertexData() {
      if (Config.isShaders()) {
         if (this.f_111292_.length == com.mojang.blaze3d.vertex.DefaultVertexFormat.BLOCK_VANILLA_SIZE) {
            this.f_111292_ = fixVertexDataSize(this.f_111292_, com.mojang.blaze3d.vertex.DefaultVertexFormat.BLOCK_SHADERS_SIZE);
         }
      } else if (this.f_111292_.length == com.mojang.blaze3d.vertex.DefaultVertexFormat.BLOCK_SHADERS_SIZE) {
         this.f_111292_ = fixVertexDataSize(this.f_111292_, com.mojang.blaze3d.vertex.DefaultVertexFormat.BLOCK_VANILLA_SIZE);
      }
   }

   private static int[] fixVertexDataSize(int[] vd, int sizeNew) {
      int step = vd.length / 4;
      int stepNew = sizeNew / 4;
      int[] vdNew = new int[stepNew * 4];

      for (int i = 0; i < 4; i++) {
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

   public net.minecraft.client.renderer.block.model.BakedQuad getQuadEmissive() {
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

      return this.quadVertexPositions.get(key);
   }

   public boolean hasAmbientOcclusion() {
      return this.hasAmbientOcclusion;
   }

   public String toString() {
      return "vertexData: " + this.f_111292_.length + ", tint: " + this.f_111293_ + ", facing: " + this.f_111294_ + ", sprite: " + this.f_111295_;
   }
}
