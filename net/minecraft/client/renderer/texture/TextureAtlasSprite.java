package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.util.CounterInt;
import net.optifine.util.TextureUtils;

public class TextureAtlasSprite {
   private net.minecraft.resources.ResourceLocation f_244141_;
   private net.minecraft.client.renderer.texture.SpriteContents f_244165_;
   int f_118349_;
   int f_118350_;
   private float f_118351_;
   private float f_118352_;
   private float f_118353_;
   private float f_118354_;
   private int indexInMap = -1;
   public float baseU;
   public float baseV;
   public int sheetWidth;
   public int sheetHeight;
   private final net.minecraft.resources.ResourceLocation name;
   public int glSpriteTextureId = -1;
   public net.minecraft.client.renderer.texture.TextureAtlasSprite spriteSingle = null;
   public boolean isSpriteSingle = false;
   public static final String SUFFIX_SPRITE_SINGLE = ".sprite_single";
   public net.minecraft.client.renderer.texture.TextureAtlasSprite spriteNormal = null;
   public net.minecraft.client.renderer.texture.TextureAtlasSprite spriteSpecular = null;
   public ShadersTextureType spriteShadersType = null;
   public net.minecraft.client.renderer.texture.TextureAtlasSprite spriteEmissive = null;
   public boolean isSpriteEmissive = false;
   protected int animationIndex = -1;
   private boolean terrain;
   private boolean shaders;
   private boolean multiTexture;
   private ResourceManager resourceManager;
   private int imageWidth;
   private int imageHeight;
   private net.minecraft.client.renderer.texture.TextureAtlas atlasTexture;
   private net.minecraft.client.renderer.texture.SpriteContents.Ticker spriteContentsTicker;
   private net.minecraft.client.renderer.texture.TextureAtlasSprite parentSprite;
   protected boolean usesParentAnimationTime = false;

   public TextureAtlasSprite(net.minecraft.resources.ResourceLocation atlasLocation, net.minecraft.resources.ResourceLocation name) {
      this.f_244141_ = atlasLocation;
      this.name = name;
      this.f_244165_ = null;
      this.atlasTexture = null;
      this.f_118349_ = 0;
      this.f_118350_ = 0;
      this.f_118351_ = 0.0F;
      this.f_118352_ = 0.0F;
      this.f_118353_ = 0.0F;
      this.f_118354_ = 0.0F;
      this.imageWidth = 0;
      this.imageHeight = 0;
   }

   private TextureAtlasSprite(net.minecraft.client.renderer.texture.TextureAtlasSprite parent) {
      this.atlasTexture = parent.atlasTexture;
      this.name = parent.getName();
      net.minecraft.client.renderer.texture.SpriteContents parentContents = parent.f_244165_;
      this.f_244165_ = new net.minecraft.client.renderer.texture.SpriteContents(
         parentContents.m_246162_(),
         new FrameSize(parentContents.f_244302_, parentContents.f_244600_),
         parentContents.getOriginalImage(),
         parentContents.m_293312_()
      );
      this.f_244165_.setSprite(this);
      this.f_244165_.setScaleFactor(parentContents.getScaleFactor());
      this.imageWidth = parent.imageWidth;
      this.imageHeight = parent.imageHeight;
      this.usesParentAnimationTime = true;
      this.f_118349_ = 0;
      this.f_118350_ = 0;
      this.f_118351_ = 0.0F;
      this.f_118352_ = 1.0F;
      this.f_118353_ = 0.0F;
      this.f_118354_ = 1.0F;
      this.baseU = Math.min(this.f_118351_, this.f_118352_);
      this.baseV = Math.min(this.f_118353_, this.f_118354_);
      this.indexInMap = parent.indexInMap;
      this.baseU = parent.baseU;
      this.baseV = parent.baseV;
      this.sheetWidth = parent.sheetWidth;
      this.sheetHeight = parent.sheetHeight;
      this.isSpriteSingle = true;
      this.animationIndex = parent.animationIndex;
      if (this.spriteContentsTicker != null && parent.spriteContentsTicker != null) {
         this.spriteContentsTicker.animationActive = parent.spriteContentsTicker.animationActive;
      }
   }

   public void init(
      net.minecraft.resources.ResourceLocation locationIn,
      net.minecraft.client.renderer.texture.SpriteContents contentsIn,
      int atlasWidthIn,
      int atlasHeightIn,
      int xIn,
      int yIn
   ) {
      this.f_244141_ = locationIn;
      this.f_244165_ = contentsIn;
      this.f_244165_.setSprite(this);
      this.sheetWidth = atlasWidthIn;
      this.sheetHeight = atlasHeightIn;
      this.imageWidth = this.f_244165_.f_244302_;
      this.imageHeight = this.f_244165_.f_244600_;
      this.f_118349_ = xIn;
      this.f_118350_ = yIn;
      this.f_118351_ = (float)xIn / (float)atlasWidthIn;
      this.f_118352_ = (float)(xIn + contentsIn.m_246492_()) / (float)atlasWidthIn;
      this.f_118353_ = (float)yIn / (float)atlasHeightIn;
      this.f_118354_ = (float)(yIn + contentsIn.m_245330_()) / (float)atlasHeightIn;
      this.baseU = Math.min(this.f_118351_, this.f_118352_);
      this.baseV = Math.min(this.f_118353_, this.f_118354_);
   }

   protected TextureAtlasSprite(
      net.minecraft.resources.ResourceLocation locationIn,
      net.minecraft.client.renderer.texture.SpriteContents contentsIn,
      int atlasWidthIn,
      int atlasHeightIn,
      int xIn,
      int yIn
   ) {
      this(locationIn, contentsIn, atlasWidthIn, atlasHeightIn, xIn, yIn, null, null);
   }

   protected TextureAtlasSprite(
      net.minecraft.resources.ResourceLocation locationIn,
      net.minecraft.client.renderer.texture.SpriteContents contentsIn,
      int atlasWidthIn,
      int atlasHeightIn,
      int xIn,
      int yIn,
      net.minecraft.client.renderer.texture.TextureAtlas atlas,
      ShadersTextureType spriteShadersTypeIn
   ) {
      this.atlasTexture = atlas;
      this.spriteShadersType = spriteShadersTypeIn;
      this.f_244141_ = locationIn;
      this.f_244165_ = contentsIn;
      this.name = contentsIn.m_246162_();
      this.imageWidth = this.f_244165_.f_244302_;
      this.imageHeight = this.f_244165_.f_244600_;
      this.f_118349_ = xIn;
      this.f_118350_ = yIn;
      this.f_118351_ = (float)xIn / (float)atlasWidthIn;
      this.f_118352_ = (float)(xIn + contentsIn.m_246492_()) / (float)atlasWidthIn;
      this.f_118353_ = (float)yIn / (float)atlasHeightIn;
      this.f_118354_ = (float)(yIn + contentsIn.m_245330_()) / (float)atlasHeightIn;
      this.baseU = Math.min(this.f_118351_, this.f_118352_);
      this.baseV = Math.min(this.f_118353_, this.f_118354_);
      this.sheetWidth = atlasWidthIn;
      this.sheetHeight = atlasHeightIn;
      this.f_244165_.setSprite(this);
   }

   public int m_174743_() {
      return this.f_118349_;
   }

   public int m_174744_() {
      return this.f_118350_;
   }

   public float m_118409_() {
      return this.f_118351_;
   }

   public float m_118410_() {
      return this.f_118352_;
   }

   public net.minecraft.client.renderer.texture.SpriteContents m_245424_() {
      return this.f_244165_;
   }

   @Nullable
   public net.minecraft.client.renderer.texture.TextureAtlasSprite.Ticker m_247406_() {
      final net.minecraft.client.renderer.texture.SpriteTicker spriteticker = this.f_244165_.m_246786_();
      if (spriteticker != null) {
         spriteticker.setSprite(this);
      }

      return spriteticker != null ? new net.minecraft.client.renderer.texture.TextureAtlasSprite.Ticker() {
         @Override
         public void m_245385_() {
            spriteticker.m_247697_(TextureAtlasSprite.this.f_118349_, TextureAtlasSprite.this.f_118350_);
         }

         @Override
         public void close() {
            spriteticker.close();
         }

         @Override
         public net.minecraft.client.renderer.texture.TextureAtlasSprite getSprite() {
            return TextureAtlasSprite.this;
         }

         @Override
         public net.minecraft.client.renderer.texture.SpriteTicker getSpriteTicker() {
            return spriteticker;
         }
      } : null;
   }

   public float m_118367_(float u) {
      float f = this.f_118352_ - this.f_118351_;
      return this.f_118351_ + f * u;
   }

   public float m_174727_(float u) {
      float f = this.f_118352_ - this.f_118351_;
      return (u - this.f_118351_) / f;
   }

   public float m_118411_() {
      return this.f_118353_;
   }

   public float m_118412_() {
      return this.f_118354_;
   }

   public float m_118393_(float v) {
      float f = this.f_118354_ - this.f_118353_;
      return this.f_118353_ + f * v;
   }

   public float m_174741_(float v) {
      float f = this.f_118354_ - this.f_118353_;
      return (v - this.f_118353_) / f;
   }

   public net.minecraft.resources.ResourceLocation m_247685_() {
      return this.f_244141_;
   }

   public String toString() {
      return "TextureAtlasSprite{name= "
         + this.name
         + ", contents='"
         + this.f_244165_
         + "', u0="
         + this.f_118351_
         + ", u1="
         + this.f_118352_
         + ", v0="
         + this.f_118353_
         + ", v1="
         + this.f_118354_
         + "}";
   }

   public void m_118416_() {
      this.f_244165_.m_246850_(this.f_118349_, this.f_118350_);
   }

   private float m_118366_() {
      float f = (float)this.f_244165_.m_246492_() / (this.f_118352_ - this.f_118351_);
      float f1 = (float)this.f_244165_.m_245330_() / (this.f_118354_ - this.f_118353_);
      return Math.max(f1, f);
   }

   public float m_118417_() {
      return 4.0F / this.m_118366_();
   }

   public com.mojang.blaze3d.vertex.VertexConsumer m_118381_(com.mojang.blaze3d.vertex.VertexConsumer bufferIn) {
      return new net.minecraft.client.renderer.SpriteCoordinateExpander(bufferIn, this);
   }

   public int getIndexInMap() {
      return this.indexInMap;
   }

   public void updateIndexInMap(CounterInt counterInt) {
      if (this.indexInMap < 0) {
         if (this.atlasTexture != null) {
            net.minecraft.client.renderer.texture.TextureAtlasSprite registeredSprite = this.atlasTexture.getRegisteredSprite(this.getName());
            if (registeredSprite != null) {
               this.indexInMap = registeredSprite.getIndexInMap();
            }
         }

         if (this.indexInMap < 0) {
            this.indexInMap = counterInt.nextValue();
         }
      }
   }

   public int getAnimationIndex() {
      return this.animationIndex;
   }

   public void setAnimationIndex(int animationIndex) {
      this.animationIndex = animationIndex;
      if (this.spriteSingle != null) {
         this.spriteSingle.setAnimationIndex(animationIndex);
      }

      if (this.spriteNormal != null) {
         this.spriteNormal.setAnimationIndex(animationIndex);
      }

      if (this.spriteSpecular != null) {
         this.spriteSpecular.setAnimationIndex(animationIndex);
      }
   }

   public boolean isAnimationActive() {
      return this.spriteContentsTicker == null ? false : this.spriteContentsTicker.animationActive;
   }

   public static void fixTransparentColor(com.mojang.blaze3d.platform.NativeImage ni) {
      int[] data = new int[ni.m_84982_() * ni.m_85084_()];
      ni.getBufferRGBA().get(data);
      fixTransparentColor(data);
      ni.getBufferRGBA().put(data);
   }

   private static void fixTransparentColor(int[] data) {
      if (data != null) {
         long redSum = 0L;
         long greenSum = 0L;
         long blueSum = 0L;
         long count = 0L;

         for (int i = 0; i < data.length; i++) {
            int col = data[i];
            int alpha = col >> 24 & 0xFF;
            if (alpha >= 16) {
               int red = col >> 16 & 0xFF;
               int green = col >> 8 & 0xFF;
               int blue = col & 0xFF;
               redSum += (long)red;
               greenSum += (long)green;
               blueSum += (long)blue;
               count++;
            }
         }

         if (count > 0L) {
            int redAvg = (int)(redSum / count);
            int greenAvg = (int)(greenSum / count);
            int blueAvg = (int)(blueSum / count);
            int colAvg = redAvg << 16 | greenAvg << 8 | blueAvg;

            for (int ix = 0; ix < data.length; ix++) {
               int col = data[ix];
               int alpha = col >> 24 & 0xFF;
               if (alpha <= 16) {
                  data[ix] = colAvg;
               }
            }
         }
      }
   }

   public double getSpriteU16(float atlasU) {
      float dU = this.f_118352_ - this.f_118351_;
      return (double)((atlasU - this.f_118351_) / dU * 16.0F);
   }

   public double getSpriteV16(float atlasV) {
      float dV = this.f_118354_ - this.f_118353_;
      return (double)((atlasV - this.f_118353_) / dV * 16.0F);
   }

   public void bindSpriteTexture() {
      if (this.glSpriteTextureId < 0) {
         this.glSpriteTextureId = TextureUtil.generateTextureId();
         int mipmapLevels = this.getMipmapLevels();
         TextureUtil.prepareImage(this.glSpriteTextureId, mipmapLevels, this.getWidth(), this.getHeight());
         boolean blend = this.atlasTexture.isTextureBlend(this.spriteShadersType);
         if (blend) {
            TextureUtils.applyAnisotropicLevel();
         } else {
            GlStateManager._texParameter(3553, 34046, 1.0F);
            int minFilter = mipmapLevels > 0 ? 9984 : 9728;
            GlStateManager._texParameter(3553, 10241, minFilter);
            GlStateManager._texParameter(3553, 10240, 9728);
         }
      }

      TextureUtils.bindTexture(this.glSpriteTextureId);
   }

   public void deleteSpriteTexture() {
      if (this.glSpriteTextureId >= 0) {
         TextureUtil.releaseTextureId(this.glSpriteTextureId);
         this.glSpriteTextureId = -1;
      }
   }

   public float toSingleU(float u) {
      u -= this.baseU;
      float ku = (float)this.sheetWidth / (float)this.getWidth();
      return u * ku;
   }

   public float toSingleV(float v) {
      v -= this.baseV;
      float kv = (float)this.sheetHeight / (float)this.getHeight();
      return v * kv;
   }

   public com.mojang.blaze3d.platform.NativeImage[] getMipmapImages() {
      return this.f_244165_.f_243731_;
   }

   public int getMipmapLevels() {
      return this.f_244165_.f_243731_.length - 1;
   }

   public int getOriginX() {
      return this.f_118349_;
   }

   public int getOriginY() {
      return this.f_118350_;
   }

   public float getUnInterpolatedU16(float u) {
      float f = this.f_118352_ - this.f_118351_;
      return (u - this.f_118351_) / f * 16.0F;
   }

   public float getUnInterpolatedV16(float v) {
      float f = this.f_118354_ - this.f_118353_;
      return (v - this.f_118353_) / f * 16.0F;
   }

   public float getInterpolatedU16(double u16) {
      float f = this.f_118352_ - this.f_118351_;
      return this.f_118351_ + f * (float)u16 / 16.0F;
   }

   public float getInterpolatedV16(double v16) {
      float f = this.f_118354_ - this.f_118353_;
      return this.f_118353_ + f * (float)v16 / 16.0F;
   }

   public net.minecraft.resources.ResourceLocation getName() {
      return this.name;
   }

   public net.minecraft.client.renderer.texture.TextureAtlas getTextureAtlas() {
      return this.atlasTexture;
   }

   public void setTextureAtlas(net.minecraft.client.renderer.texture.TextureAtlas atlas) {
      this.atlasTexture = atlas;
      if (this.spriteSingle != null) {
         this.spriteSingle.setTextureAtlas(atlas);
      }

      if (this.spriteNormal != null) {
         this.spriteNormal.setTextureAtlas(atlas);
      }

      if (this.spriteSpecular != null) {
         this.spriteSpecular.setTextureAtlas(atlas);
      }
   }

   public int getWidth() {
      return this.f_244165_.getSpriteWidth();
   }

   public int getHeight() {
      return this.f_244165_.getSpriteHeight();
   }

   public net.minecraft.client.renderer.texture.TextureAtlasSprite makeSpriteSingle() {
      net.minecraft.client.renderer.texture.TextureAtlasSprite ss = new net.minecraft.client.renderer.texture.TextureAtlasSprite(this);
      ss.isSpriteSingle = true;
      return ss;
   }

   public net.minecraft.client.renderer.texture.TextureAtlasSprite makeSpriteShaders(
      ShadersTextureType type, int colDef, net.minecraft.client.renderer.texture.SpriteContents.AnimatedTexture parentAnimatedTexture
   ) {
      String suffix = type.getSuffix();
      net.minecraft.resources.ResourceLocation loc = new net.minecraft.resources.ResourceLocation(
         this.getName().m_135827_(), this.getName().m_135815_() + suffix
      );
      net.minecraft.resources.ResourceLocation locPng = this.atlasTexture.getSpritePath(loc);
      net.minecraft.client.renderer.texture.TextureAtlasSprite ss = null;
      Optional<Resource> optRes = this.resourceManager.m_213713_(locPng);
      if (optRes.isPresent()) {
         try {
            Resource iresource = (Resource)optRes.get();
            Resource resPngSize = this.resourceManager.m_215593_(locPng);
            com.mojang.blaze3d.platform.NativeImage image = com.mojang.blaze3d.platform.NativeImage.m_85058_(iresource.m_215507_());
            ResourceMetadata resMeta = iresource.m_215509_();
            net.minecraft.client.resources.metadata.animation.AnimationMetadataSection animMeta = (net.minecraft.client.resources.metadata.animation.AnimationMetadataSection)resMeta.m_214059_(
                  net.minecraft.client.resources.metadata.animation.AnimationMetadataSection.f_119011_
               )
               .orElse(net.minecraft.client.resources.metadata.animation.AnimationMetadataSection.f_119012_);
            FrameSize frameSize = animMeta.m_245821_(image.m_84982_(), image.m_85084_());
            if (image.m_84982_() != this.getWidth()) {
               com.mojang.blaze3d.platform.NativeImage imageScaled = TextureUtils.scaleImage(image, this.getWidth());
               if (imageScaled != image) {
                  double scaleFactor = 1.0 * (double)this.getWidth() / (double)image.m_84982_();
                  image.close();
                  image = imageScaled;
                  frameSize = new FrameSize((int)((double)frameSize.f_244129_() * scaleFactor), (int)((double)frameSize.f_244503_() * scaleFactor));
               }
            }

            net.minecraft.client.renderer.texture.SpriteContents contentsShaders = new net.minecraft.client.renderer.texture.SpriteContents(
               loc, frameSize, image, resMeta
            );
            ss = new net.minecraft.client.renderer.texture.TextureAtlasSprite(
               this.f_244141_, contentsShaders, this.sheetWidth, this.sheetHeight, this.f_118349_, this.f_118350_, this.atlasTexture, type
            );
            ss.parentSprite = this;
         } catch (IOException var18) {
         }
      }

      if (ss == null) {
         com.mojang.blaze3d.platform.NativeImage image = new com.mojang.blaze3d.platform.NativeImage(this.getWidth(), this.getHeight(), false);
         int colAbgr = TextureUtils.toAbgr(colDef);
         image.m_84997_(0, 0, image.m_84982_(), image.m_85084_(), colAbgr);
         net.minecraft.client.renderer.texture.SpriteContents contentsShaders = new net.minecraft.client.renderer.texture.SpriteContents(
            loc, new FrameSize(this.getWidth(), this.getHeight()), image, ResourceMetadata.f_215577_
         );
         ss = new net.minecraft.client.renderer.texture.TextureAtlasSprite(
            this.f_244141_, contentsShaders, this.sheetWidth, this.sheetHeight, this.f_118349_, this.f_118350_, this.atlasTexture, type
         );
      }

      if (this.terrain && this.multiTexture && !this.isSpriteSingle) {
         ss.spriteSingle = ss.makeSpriteSingle();
      }

      return ss;
   }

   public boolean isTerrain() {
      return this.terrain;
   }

   private void setTerrain(boolean terrainIn) {
      this.terrain = terrainIn;
      this.multiTexture = false;
      this.shaders = false;
      if (this.spriteSingle != null) {
         this.deleteSpriteTexture();
         this.spriteSingle = null;
      }

      if (this.spriteNormal != null) {
         if (this.spriteNormal.spriteSingle != null) {
            this.spriteNormal.deleteSpriteTexture();
         }

         this.spriteNormal.m_245424_().close();
         this.spriteNormal = null;
      }

      if (this.spriteSpecular != null) {
         if (this.spriteSpecular.spriteSingle != null) {
            this.spriteSpecular.deleteSpriteTexture();
         }

         this.spriteSpecular.m_245424_().close();
         this.spriteSpecular = null;
      }

      this.multiTexture = Config.isMultiTexture();
      this.shaders = Config.isShaders();
      if (this.terrain && this.multiTexture && !this.isSpriteSingle) {
         this.spriteSingle = this.makeSpriteSingle();
      }

      if (this.shaders && !this.isSpriteSingle) {
         if (this.spriteNormal == null && Shaders.configNormalMap) {
            this.spriteNormal = this.makeSpriteShaders(ShadersTextureType.NORMAL, -8421377, this.f_244165_.getAnimatedTexture());
         }

         if (this.spriteSpecular == null && Shaders.configSpecularMap) {
            this.spriteSpecular = this.makeSpriteShaders(ShadersTextureType.SPECULAR, 0, this.f_244165_.getAnimatedTexture());
         }
      }
   }

   private static boolean matchesTiming(
      net.minecraft.client.renderer.texture.SpriteContents.AnimatedTexture at1, net.minecraft.client.renderer.texture.SpriteContents.AnimatedTexture at2
   ) {
      if (at1 == null || at2 == null) {
         return false;
      } else if (at1 == at2) {
         return true;
      } else {
         boolean ip1 = at1.f_244317_;
         boolean ip2 = at2.f_244317_;
         if (ip1 != ip2) {
            return false;
         } else {
            List<net.minecraft.client.renderer.texture.SpriteContents.FrameInfo> frames1 = at1.f_243714_;
            List<net.minecraft.client.renderer.texture.SpriteContents.FrameInfo> frames2 = at2.f_243714_;
            if (frames1 != null && frames2 != null) {
               if (frames1.size() != frames2.size()) {
                  return false;
               } else {
                  for (int i = 0; i < frames1.size(); i++) {
                     net.minecraft.client.renderer.texture.SpriteContents.FrameInfo fi1 = (net.minecraft.client.renderer.texture.SpriteContents.FrameInfo)frames1.get(
                        i
                     );
                     net.minecraft.client.renderer.texture.SpriteContents.FrameInfo fi2 = (net.minecraft.client.renderer.texture.SpriteContents.FrameInfo)frames2.get(
                        i
                     );
                     if (fi1 == null || fi2 == null) {
                        return false;
                     }

                     if (fi1.f_243751_ != fi2.f_243751_) {
                        return false;
                     }

                     if (fi1.f_244553_ != fi2.f_244553_) {
                        return false;
                     }
                  }

                  return true;
               }
            } else {
               return false;
            }
         }
      }
   }

   public void update(ResourceManager resourceManager) {
      this.resourceManager = resourceManager;
      this.updateIndexInMap(this.atlasTexture.getCounterIndexInMap());
      this.setTerrain(this.atlasTexture.isTerrain());
   }

   public void updateAnimation() {
      if (this.spriteContentsTicker != null) {
         this.spriteContentsTicker.m_247697_(this.f_118349_, this.f_118350_);
      }
   }

   public void preTick() {
      if (this.spriteContentsTicker != null) {
         if (this.spriteSingle != null && this.spriteSingle.spriteContentsTicker != null && this.spriteSingle.usesParentAnimationTime) {
            this.spriteSingle.spriteContentsTicker.f_244631_ = this.spriteContentsTicker.f_244631_;
            this.spriteSingle.spriteContentsTicker.f_244511_ = this.spriteContentsTicker.f_244511_;
         }

         if (this.spriteNormal != null && this.spriteNormal.spriteContentsTicker != null && this.spriteNormal.usesParentAnimationTime) {
            this.spriteNormal.spriteContentsTicker.f_244631_ = this.spriteContentsTicker.f_244631_;
            this.spriteNormal.spriteContentsTicker.f_244511_ = this.spriteContentsTicker.f_244511_;
         }

         if (this.spriteSpecular != null && this.spriteSpecular.spriteContentsTicker != null && this.spriteSpecular.usesParentAnimationTime) {
            this.spriteSpecular.spriteContentsTicker.f_244631_ = this.spriteContentsTicker.f_244631_;
            this.spriteSpecular.spriteContentsTicker.f_244511_ = this.spriteContentsTicker.f_244511_;
         }
      }
   }

   public int getPixelRGBA(int frameIndex, int x, int y) {
      if (this.f_244165_.getAnimatedTexture() != null) {
         x += this.f_244165_.getAnimatedTexture().m_245080_(frameIndex) * this.f_244165_.f_244302_;
         y += this.f_244165_.getAnimatedTexture().m_246436_(frameIndex) * this.f_244165_.f_244600_;
      }

      return this.f_244165_.getOriginalImage().m_84985_(x, y);
   }

   public net.minecraft.client.renderer.texture.SpriteContents.Ticker getSpriteContentsTicker() {
      return this.spriteContentsTicker;
   }

   public void setSpriteContentsTicker(net.minecraft.client.renderer.texture.SpriteContents.Ticker spriteContentsTicker) {
      if (this.spriteContentsTicker != null) {
         this.spriteContentsTicker.close();
      }

      this.spriteContentsTicker = spriteContentsTicker;
      if (this.spriteContentsTicker != null && this.parentSprite != null && this.parentSprite.f_244165_ != null) {
         this.usesParentAnimationTime = matchesTiming(this.f_244165_.getAnimatedTexture(), this.parentSprite.f_244165_.getAnimatedTexture());
      }
   }

   public void setTicker(net.minecraft.client.renderer.texture.TextureAtlasSprite.Ticker ticker) {
      if (ticker.getSpriteTicker() instanceof net.minecraft.client.renderer.texture.SpriteContents.Ticker spriteContentsTicker) {
         this.setSpriteContentsTicker(spriteContentsTicker);
      }
   }

   public void increaseMipLevel(int mipLevelIn) {
      this.f_244165_.m_246368_(mipLevelIn);
      if (this.spriteNormal != null) {
         this.spriteNormal.increaseMipLevel(mipLevelIn);
      }

      if (this.spriteSpecular != null) {
         this.spriteSpecular.increaseMipLevel(mipLevelIn);
      }
   }

   public interface Ticker extends AutoCloseable {
      void m_245385_();

      void close();

      default net.minecraft.client.renderer.texture.TextureAtlasSprite getSprite() {
         return null;
      }

      default net.minecraft.client.renderer.texture.SpriteTicker getSpriteTicker() {
         return null;
      }
   }
}
