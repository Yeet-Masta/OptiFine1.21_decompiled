package net.minecraft.src;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.util.CounterInt;
import net.optifine.util.TextureUtils;

public class C_4486_ {
   private C_5265_ f_244141_;
   private C_243582_ f_244165_;
   int f_118349_;
   int f_118350_;
   private float f_118351_;
   private float f_118352_;
   private float f_118353_;
   private float f_118354_;
   private int indexInMap;
   public float baseU;
   public float baseV;
   public int sheetWidth;
   public int sheetHeight;
   private final C_5265_ name;
   public int glSpriteTextureId;
   public C_4486_ spriteSingle;
   public boolean isSpriteSingle;
   public static final String SUFFIX_SPRITE_SINGLE = ".sprite_single";
   public C_4486_ spriteNormal;
   public C_4486_ spriteSpecular;
   public ShadersTextureType spriteShadersType;
   public C_4486_ spriteEmissive;
   public boolean isSpriteEmissive;
   protected int animationIndex;
   private boolean terrain;
   private boolean shaders;
   private boolean multiTexture;
   private C_77_ resourceManager;
   private int imageWidth;
   private int imageHeight;
   private C_4484_ atlasTexture;
   private C_243582_.C_243426_ spriteContentsTicker;
   private C_4486_ parentSprite;
   protected boolean usesParentAnimationTime;

   public C_4486_(C_5265_ atlasLocation, C_5265_ name) {
      this.indexInMap = -1;
      this.glSpriteTextureId = -1;
      this.spriteSingle = null;
      this.isSpriteSingle = false;
      this.spriteNormal = null;
      this.spriteSpecular = null;
      this.spriteShadersType = null;
      this.spriteEmissive = null;
      this.isSpriteEmissive = false;
      this.animationIndex = -1;
      this.usesParentAnimationTime = false;
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

   private C_4486_(C_4486_ parent) {
      this.indexInMap = -1;
      this.glSpriteTextureId = -1;
      this.spriteSingle = null;
      this.isSpriteSingle = false;
      this.spriteNormal = null;
      this.spriteSpecular = null;
      this.spriteShadersType = null;
      this.spriteEmissive = null;
      this.isSpriteEmissive = false;
      this.animationIndex = -1;
      this.usesParentAnimationTime = false;
      this.atlasTexture = parent.atlasTexture;
      this.name = parent.getName();
      C_243582_ parentContents = parent.f_244165_;
      this.f_244165_ = new C_243582_(parentContents.m_246162_(), new C_243504_(parentContents.f_244302_, parentContents.f_244600_), parentContents.getOriginalImage(), parentContents.m_293312_());
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

   public void init(C_5265_ locationIn, C_243582_ contentsIn, int atlasWidthIn, int atlasHeightIn, int xIn, int yIn) {
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

   protected C_4486_(C_5265_ locationIn, C_243582_ contentsIn, int atlasWidthIn, int atlasHeightIn, int xIn, int yIn) {
      this(locationIn, contentsIn, atlasWidthIn, atlasHeightIn, xIn, yIn, (C_4484_)null, (ShadersTextureType)null);
   }

   protected C_4486_(C_5265_ locationIn, C_243582_ contentsIn, int atlasWidthIn, int atlasHeightIn, int xIn, int yIn, C_4484_ atlas, ShadersTextureType spriteShadersTypeIn) {
      this.indexInMap = -1;
      this.glSpriteTextureId = -1;
      this.spriteSingle = null;
      this.isSpriteSingle = false;
      this.spriteNormal = null;
      this.spriteSpecular = null;
      this.spriteShadersType = null;
      this.spriteEmissive = null;
      this.isSpriteEmissive = false;
      this.animationIndex = -1;
      this.usesParentAnimationTime = false;
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

   public C_243582_ m_245424_() {
      return this.f_244165_;
   }

   @Nullable
   public C_243545_ m_247406_() {
      final C_243576_ spriteticker = this.f_244165_.m_246786_();
      if (spriteticker != null) {
         spriteticker.setSprite(this);
      }

      return spriteticker != null ? new C_243545_() {
         public void m_245385_() {
            spriteticker.m_247697_(C_4486_.this.f_118349_, C_4486_.this.f_118350_);
         }

         public void close() {
            spriteticker.close();
         }

         public C_4486_ getSprite() {
            return C_4486_.this;
         }

         public C_243576_ getSpriteTicker() {
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

   public C_5265_ m_247685_() {
      return this.f_244141_;
   }

   public String toString() {
      String var10000 = String.valueOf(this.name);
      return "TextureAtlasSprite{name= " + var10000 + ", contents='" + String.valueOf(this.f_244165_) + "', u0=" + this.f_118351_ + ", u1=" + this.f_118352_ + ", v0=" + this.f_118353_ + ", v1=" + this.f_118354_ + "}";
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

   public C_3187_ m_118381_(C_3187_ bufferIn) {
      return new C_4179_(bufferIn, this);
   }

   public int getIndexInMap() {
      return this.indexInMap;
   }

   public void updateIndexInMap(CounterInt counterInt) {
      if (this.indexInMap < 0) {
         if (this.atlasTexture != null) {
            C_4486_ registeredSprite = this.atlasTexture.getRegisteredSprite(this.getName());
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

   public static void fixTransparentColor(C_3148_ ni) {
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

         int redAvg;
         int greenAvg;
         int blueAvg;
         int colAvg;
         int i;
         int col;
         for(redAvg = 0; redAvg < data.length; ++redAvg) {
            greenAvg = data[redAvg];
            blueAvg = greenAvg >> 24 & 255;
            if (blueAvg >= 16) {
               colAvg = greenAvg >> 16 & 255;
               i = greenAvg >> 8 & 255;
               col = greenAvg & 255;
               redSum += (long)colAvg;
               greenSum += (long)i;
               blueSum += (long)col;
               ++count;
            }
         }

         if (count > 0L) {
            redAvg = (int)(redSum / count);
            greenAvg = (int)(greenSum / count);
            blueAvg = (int)(blueSum / count);
            colAvg = redAvg << 16 | greenAvg << 8 | blueAvg;

            for(i = 0; i < data.length; ++i) {
               col = data[i];
               int alpha = col >> 24 & 255;
               if (alpha <= 16) {
                  data[i] = colAvg;
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
      u *= ku;
      return u;
   }

   public float toSingleV(float v) {
      v -= this.baseV;
      float kv = (float)this.sheetHeight / (float)this.getHeight();
      v *= kv;
      return v;
   }

   public C_3148_[] getMipmapImages() {
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

   public C_5265_ getName() {
      return this.name;
   }

   public C_4484_ getTextureAtlas() {
      return this.atlasTexture;
   }

   public void setTextureAtlas(C_4484_ atlas) {
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

   public C_4486_ makeSpriteSingle() {
      C_4486_ ss = new C_4486_(this);
      ss.isSpriteSingle = true;
      return ss;
   }

   public C_4486_ makeSpriteShaders(ShadersTextureType type, int colDef, C_243582_.C_243458_ parentAnimatedTexture) {
      String suffix = type.getSuffix();
      String var10002 = this.getName().m_135827_();
      String var10003 = this.getName().m_135815_();
      C_5265_ loc = new C_5265_(var10002, var10003 + suffix);
      C_5265_ locPng = this.atlasTexture.getSpritePath(loc);
      C_4486_ ss = null;
      Optional optRes = this.resourceManager.getResource(locPng);
      if (optRes.isPresent()) {
         try {
            C_76_ iresource = (C_76_)optRes.get();
            this.resourceManager.getResourceOrThrow(locPng);
            C_3148_ image = C_3148_.m_85058_(iresource.m_215507_());
            C_212950_ resMeta = iresource.m_215509_();
            C_4518_ animMeta = (C_4518_)resMeta.m_214059_(C_4518_.f_119011_).orElse(C_4518_.f_119012_);
            C_243504_ frameSize = animMeta.m_245821_(image.m_84982_(), image.m_85084_());
            if (image.m_84982_() != this.getWidth()) {
               C_3148_ imageScaled = TextureUtils.scaleImage((C_3148_)image, this.getWidth());
               if (imageScaled != image) {
                  double scaleFactor = 1.0 * (double)this.getWidth() / (double)image.m_84982_();
                  image.close();
                  image = imageScaled;
                  frameSize = new C_243504_((int)((double)frameSize.f_244129_() * scaleFactor), (int)((double)frameSize.f_244503_() * scaleFactor));
               }
            }

            C_243582_ contentsShaders = new C_243582_(loc, frameSize, image, resMeta);
            ss = new C_4486_(this.f_244141_, contentsShaders, this.sheetWidth, this.sheetHeight, this.f_118349_, this.f_118350_, this.atlasTexture, type);
            ss.parentSprite = this;
         } catch (IOException var18) {
         }
      }

      if (ss == null) {
         C_3148_ image = new C_3148_(this.getWidth(), this.getHeight(), false);
         int colAbgr = TextureUtils.toAbgr(colDef);
         image.m_84997_(0, 0, image.m_84982_(), image.m_85084_(), colAbgr);
         C_243582_ contentsShaders = new C_243582_(loc, new C_243504_(this.getWidth(), this.getHeight()), image, C_212950_.f_215577_);
         ss = new C_4486_(this.f_244141_, contentsShaders, this.sheetWidth, this.sheetHeight, this.f_118349_, this.f_118350_, this.atlasTexture, type);
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

   private static boolean matchesTiming(C_243582_.C_243458_ at1, C_243582_.C_243458_ at2) {
      if (at1 != null && at2 != null) {
         if (at1 == at2) {
            return true;
         } else {
            boolean ip1 = at1.f_244317_;
            boolean ip2 = at2.f_244317_;
            if (ip1 != ip2) {
               return false;
            } else {
               List frames1 = at1.f_243714_;
               List frames2 = at2.f_243714_;
               if (frames1 != null && frames2 != null) {
                  if (frames1.size() != frames2.size()) {
                     return false;
                  } else {
                     for(int i = 0; i < frames1.size(); ++i) {
                        C_243582_.C_243413_ fi1 = (C_243582_.C_243413_)frames1.get(i);
                        C_243582_.C_243413_ fi2 = (C_243582_.C_243413_)frames2.get(i);
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
      } else {
         return false;
      }
   }

   public void update(C_77_ resourceManager) {
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

   public C_243582_.C_243426_ getSpriteContentsTicker() {
      return this.spriteContentsTicker;
   }

   public void setSpriteContentsTicker(C_243582_.C_243426_ spriteContentsTicker) {
      if (this.spriteContentsTicker != null) {
         this.spriteContentsTicker.close();
      }

      this.spriteContentsTicker = spriteContentsTicker;
      if (this.spriteContentsTicker != null && this.parentSprite != null && this.parentSprite.f_244165_ != null) {
         this.usesParentAnimationTime = matchesTiming(this.f_244165_.getAnimatedTexture(), this.parentSprite.f_244165_.getAnimatedTexture());
      }

   }

   public void setTicker(C_243545_ ticker) {
      C_243576_ spriteTicker = ticker.getSpriteTicker();
      if (spriteTicker instanceof C_243582_.C_243426_ spriteContentsTicker) {
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

   public interface C_243545_ extends AutoCloseable {
      void m_245385_();

      void close();

      default C_4486_ getSprite() {
         return null;
      }

      default C_243576_ getSpriteTicker() {
         return null;
      }
   }
}
