import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.src.C_212950_;
import net.minecraft.src.C_243504_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.util.CounterInt;
import net.optifine.util.TextureUtils;

public class TextureAtlasSprite {
   private ResourceLocation a;
   private SpriteContents b;
   int c;
   int d;
   private float e;
   private float f;
   private float g;
   private float h;
   private int indexInMap = -1;
   public float baseU;
   public float baseV;
   public int sheetWidth;
   public int sheetHeight;
   private final ResourceLocation name;
   public int glSpriteTextureId = -1;
   public TextureAtlasSprite spriteSingle = null;
   public boolean isSpriteSingle = false;
   public static final String SUFFIX_SPRITE_SINGLE = ".sprite_single";
   public TextureAtlasSprite spriteNormal = null;
   public TextureAtlasSprite spriteSpecular = null;
   public ShadersTextureType spriteShadersType = null;
   public TextureAtlasSprite spriteEmissive = null;
   public boolean isSpriteEmissive = false;
   protected int animationIndex = -1;
   private boolean terrain;
   private boolean shaders;
   private boolean multiTexture;
   private C_77_ resourceManager;
   private int imageWidth;
   private int imageHeight;
   private TextureAtlas atlasTexture;
   private SpriteContents.d spriteContentsTicker;
   private TextureAtlasSprite parentSprite;
   protected boolean usesParentAnimationTime = false;

   public TextureAtlasSprite(ResourceLocation atlasLocation, ResourceLocation name) {
      this.a = atlasLocation;
      this.name = name;
      this.b = null;
      this.atlasTexture = null;
      this.c = 0;
      this.d = 0;
      this.e = 0.0F;
      this.f = 0.0F;
      this.g = 0.0F;
      this.h = 0.0F;
      this.imageWidth = 0;
      this.imageHeight = 0;
   }

   private TextureAtlasSprite(TextureAtlasSprite parent) {
      this.atlasTexture = parent.atlasTexture;
      this.name = parent.getName();
      SpriteContents parentContents = parent.b;
      this.b = new SpriteContents(parentContents.c(), new C_243504_(parentContents.c, parentContents.d), parentContents.getOriginalImage(), parentContents.f());
      this.b.setSprite(this);
      this.b.setScaleFactor(parentContents.getScaleFactor());
      this.imageWidth = parent.imageWidth;
      this.imageHeight = parent.imageHeight;
      this.usesParentAnimationTime = true;
      this.c = 0;
      this.d = 0;
      this.e = 0.0F;
      this.f = 1.0F;
      this.g = 0.0F;
      this.h = 1.0F;
      this.baseU = Math.min(this.e, this.f);
      this.baseV = Math.min(this.g, this.h);
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

   public void init(ResourceLocation locationIn, SpriteContents contentsIn, int atlasWidthIn, int atlasHeightIn, int xIn, int yIn) {
      this.a = locationIn;
      this.b = contentsIn;
      this.b.setSprite(this);
      this.sheetWidth = atlasWidthIn;
      this.sheetHeight = atlasHeightIn;
      this.imageWidth = this.b.c;
      this.imageHeight = this.b.d;
      this.c = xIn;
      this.d = yIn;
      this.e = (float)xIn / (float)atlasWidthIn;
      this.f = (float)(xIn + contentsIn.a()) / (float)atlasWidthIn;
      this.g = (float)yIn / (float)atlasHeightIn;
      this.h = (float)(yIn + contentsIn.b()) / (float)atlasHeightIn;
      this.baseU = Math.min(this.e, this.f);
      this.baseV = Math.min(this.g, this.h);
   }

   protected TextureAtlasSprite(ResourceLocation locationIn, SpriteContents contentsIn, int atlasWidthIn, int atlasHeightIn, int xIn, int yIn) {
      this(locationIn, contentsIn, atlasWidthIn, atlasHeightIn, xIn, yIn, null, null);
   }

   protected TextureAtlasSprite(
      ResourceLocation locationIn,
      SpriteContents contentsIn,
      int atlasWidthIn,
      int atlasHeightIn,
      int xIn,
      int yIn,
      TextureAtlas atlas,
      ShadersTextureType spriteShadersTypeIn
   ) {
      this.atlasTexture = atlas;
      this.spriteShadersType = spriteShadersTypeIn;
      this.a = locationIn;
      this.b = contentsIn;
      this.name = contentsIn.c();
      this.imageWidth = this.b.c;
      this.imageHeight = this.b.d;
      this.c = xIn;
      this.d = yIn;
      this.e = (float)xIn / (float)atlasWidthIn;
      this.f = (float)(xIn + contentsIn.a()) / (float)atlasWidthIn;
      this.g = (float)yIn / (float)atlasHeightIn;
      this.h = (float)(yIn + contentsIn.b()) / (float)atlasHeightIn;
      this.baseU = Math.min(this.e, this.f);
      this.baseV = Math.min(this.g, this.h);
      this.sheetWidth = atlasWidthIn;
      this.sheetHeight = atlasHeightIn;
      this.b.setSprite(this);
   }

   public int a() {
      return this.c;
   }

   public int b() {
      return this.d;
   }

   public float c() {
      return this.e;
   }

   public float d() {
      return this.f;
   }

   public SpriteContents e() {
      return this.b;
   }

   @Nullable
   public TextureAtlasSprite.a f() {
      final SpriteTicker spriteticker = this.b.e();
      if (spriteticker != null) {
         spriteticker.setSprite(this);
      }

      return spriteticker != null ? new TextureAtlasSprite.a() {
         @Override
         public void a() {
            spriteticker.a(TextureAtlasSprite.this.c, TextureAtlasSprite.this.d);
         }

         @Override
         public void close() {
            spriteticker.close();
         }

         @Override
         public TextureAtlasSprite getSprite() {
            return TextureAtlasSprite.this;
         }

         @Override
         public SpriteTicker getSpriteTicker() {
            return spriteticker;
         }
      } : null;
   }

   public float a(float u) {
      float f = this.f - this.e;
      return this.e + f * u;
   }

   public float b(float u) {
      float f = this.f - this.e;
      return (u - this.e) / f;
   }

   public float g() {
      return this.g;
   }

   public float h() {
      return this.h;
   }

   public float c(float v) {
      float f = this.h - this.g;
      return this.g + f * v;
   }

   public float d(float v) {
      float f = this.h - this.g;
      return (v - this.g) / f;
   }

   public ResourceLocation i() {
      return this.a;
   }

   public String toString() {
      return "TextureAtlasSprite{name= "
         + this.name
         + ", contents='"
         + this.b
         + "', u0="
         + this.e
         + ", u1="
         + this.f
         + ", v0="
         + this.g
         + ", v1="
         + this.h
         + "}";
   }

   public void j() {
      this.b.a(this.c, this.d);
   }

   private float l() {
      float f = (float)this.b.a() / (this.f - this.e);
      float f1 = (float)this.b.b() / (this.h - this.g);
      return Math.max(f1, f);
   }

   public float k() {
      return 4.0F / this.l();
   }

   public VertexConsumer a(VertexConsumer bufferIn) {
      return new SpriteCoordinateExpander(bufferIn, this);
   }

   public int getIndexInMap() {
      return this.indexInMap;
   }

   public void updateIndexInMap(CounterInt counterInt) {
      if (this.indexInMap < 0) {
         if (this.atlasTexture != null) {
            TextureAtlasSprite registeredSprite = this.atlasTexture.getRegisteredSprite(this.getName());
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

   public static void fixTransparentColor(NativeImage ni) {
      int[] data = new int[ni.a() * ni.b()];
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
      float dU = this.f - this.e;
      return (double)((atlasU - this.e) / dU * 16.0F);
   }

   public double getSpriteV16(float atlasV) {
      float dV = this.h - this.g;
      return (double)((atlasV - this.g) / dV * 16.0F);
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

   public NativeImage[] getMipmapImages() {
      return this.b.f;
   }

   public int getMipmapLevels() {
      return this.b.f.length - 1;
   }

   public int getOriginX() {
      return this.c;
   }

   public int getOriginY() {
      return this.d;
   }

   public float getUnInterpolatedU16(float u) {
      float f = this.f - this.e;
      return (u - this.e) / f * 16.0F;
   }

   public float getUnInterpolatedV16(float v) {
      float f = this.h - this.g;
      return (v - this.g) / f * 16.0F;
   }

   public float getInterpolatedU16(double u16) {
      float f = this.f - this.e;
      return this.e + f * (float)u16 / 16.0F;
   }

   public float getInterpolatedV16(double v16) {
      float f = this.h - this.g;
      return this.g + f * (float)v16 / 16.0F;
   }

   public ResourceLocation getName() {
      return this.name;
   }

   public TextureAtlas getTextureAtlas() {
      return this.atlasTexture;
   }

   public void setTextureAtlas(TextureAtlas atlas) {
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
      return this.b.getSpriteWidth();
   }

   public int getHeight() {
      return this.b.getSpriteHeight();
   }

   public TextureAtlasSprite makeSpriteSingle() {
      TextureAtlasSprite ss = new TextureAtlasSprite(this);
      ss.isSpriteSingle = true;
      return ss;
   }

   public TextureAtlasSprite makeSpriteShaders(ShadersTextureType type, int colDef, SpriteContents.a parentAnimatedTexture) {
      String suffix = type.getSuffix();
      ResourceLocation loc = new ResourceLocation(this.getName().b(), this.getName().a() + suffix);
      ResourceLocation locPng = this.atlasTexture.getSpritePath(loc);
      TextureAtlasSprite ss = null;
      Optional<C_76_> optRes = this.resourceManager.getResource(locPng);
      if (optRes.isPresent()) {
         try {
            C_76_ iresource = (C_76_)optRes.get();
            C_76_ resPngSize = this.resourceManager.getResourceOrThrow(locPng);
            NativeImage image = NativeImage.a(iresource.m_215507_());
            C_212950_ resMeta = iresource.m_215509_();
            AnimationMetadataSection animMeta = (AnimationMetadataSection)resMeta.m_214059_(AnimationMetadataSection.a).orElse(AnimationMetadataSection.e);
            C_243504_ frameSize = animMeta.a(image.a(), image.b());
            if (image.a() != this.getWidth()) {
               NativeImage imageScaled = TextureUtils.scaleImage(image, this.getWidth());
               if (imageScaled != image) {
                  double scaleFactor = 1.0 * (double)this.getWidth() / (double)image.a();
                  image.close();
                  image = imageScaled;
                  frameSize = new C_243504_((int)((double)frameSize.f_244129_() * scaleFactor), (int)((double)frameSize.f_244503_() * scaleFactor));
               }
            }

            SpriteContents contentsShaders = new SpriteContents(loc, frameSize, image, resMeta);
            ss = new TextureAtlasSprite(this.a, contentsShaders, this.sheetWidth, this.sheetHeight, this.c, this.d, this.atlasTexture, type);
            ss.parentSprite = this;
         } catch (IOException var18) {
         }
      }

      if (ss == null) {
         NativeImage image = new NativeImage(this.getWidth(), this.getHeight(), false);
         int colAbgr = TextureUtils.toAbgr(colDef);
         image.a(0, 0, image.a(), image.b(), colAbgr);
         SpriteContents contentsShaders = new SpriteContents(loc, new C_243504_(this.getWidth(), this.getHeight()), image, C_212950_.f_215577_);
         ss = new TextureAtlasSprite(this.a, contentsShaders, this.sheetWidth, this.sheetHeight, this.c, this.d, this.atlasTexture, type);
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

         this.spriteNormal.e().close();
         this.spriteNormal = null;
      }

      if (this.spriteSpecular != null) {
         if (this.spriteSpecular.spriteSingle != null) {
            this.spriteSpecular.deleteSpriteTexture();
         }

         this.spriteSpecular.e().close();
         this.spriteSpecular = null;
      }

      this.multiTexture = Config.isMultiTexture();
      this.shaders = Config.isShaders();
      if (this.terrain && this.multiTexture && !this.isSpriteSingle) {
         this.spriteSingle = this.makeSpriteSingle();
      }

      if (this.shaders && !this.isSpriteSingle) {
         if (this.spriteNormal == null && Shaders.configNormalMap) {
            this.spriteNormal = this.makeSpriteShaders(ShadersTextureType.NORMAL, -8421377, this.b.getAnimatedTexture());
         }

         if (this.spriteSpecular == null && Shaders.configSpecularMap) {
            this.spriteSpecular = this.makeSpriteShaders(ShadersTextureType.SPECULAR, 0, this.b.getAnimatedTexture());
         }
      }
   }

   private static boolean matchesTiming(SpriteContents.a at1, SpriteContents.a at2) {
      if (at1 == null || at2 == null) {
         return false;
      } else if (at1 == at2) {
         return true;
      } else {
         boolean ip1 = at1.d;
         boolean ip2 = at2.d;
         if (ip1 != ip2) {
            return false;
         } else {
            List<SpriteContents.b> frames1 = at1.b;
            List<SpriteContents.b> frames2 = at2.b;
            if (frames1 != null && frames2 != null) {
               if (frames1.size() != frames2.size()) {
                  return false;
               } else {
                  for (int i = 0; i < frames1.size(); i++) {
                     SpriteContents.b fi1 = (SpriteContents.b)frames1.get(i);
                     SpriteContents.b fi2 = (SpriteContents.b)frames2.get(i);
                     if (fi1 == null || fi2 == null) {
                        return false;
                     }

                     if (fi1.a != fi2.a) {
                        return false;
                     }

                     if (fi1.b != fi2.b) {
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

   public void update(C_77_ resourceManager) {
      this.resourceManager = resourceManager;
      this.updateIndexInMap(this.atlasTexture.getCounterIndexInMap());
      this.setTerrain(this.atlasTexture.isTerrain());
   }

   public void updateAnimation() {
      if (this.spriteContentsTicker != null) {
         this.spriteContentsTicker.a(this.c, this.d);
      }
   }

   public void preTick() {
      if (this.spriteContentsTicker != null) {
         if (this.spriteSingle != null && this.spriteSingle.spriteContentsTicker != null && this.spriteSingle.usesParentAnimationTime) {
            this.spriteSingle.spriteContentsTicker.a = this.spriteContentsTicker.a;
            this.spriteSingle.spriteContentsTicker.b = this.spriteContentsTicker.b;
         }

         if (this.spriteNormal != null && this.spriteNormal.spriteContentsTicker != null && this.spriteNormal.usesParentAnimationTime) {
            this.spriteNormal.spriteContentsTicker.a = this.spriteContentsTicker.a;
            this.spriteNormal.spriteContentsTicker.b = this.spriteContentsTicker.b;
         }

         if (this.spriteSpecular != null && this.spriteSpecular.spriteContentsTicker != null && this.spriteSpecular.usesParentAnimationTime) {
            this.spriteSpecular.spriteContentsTicker.a = this.spriteContentsTicker.a;
            this.spriteSpecular.spriteContentsTicker.b = this.spriteContentsTicker.b;
         }
      }
   }

   public int getPixelRGBA(int frameIndex, int x, int y) {
      if (this.b.getAnimatedTexture() != null) {
         x += this.b.getAnimatedTexture().a(frameIndex) * this.b.c;
         y += this.b.getAnimatedTexture().b(frameIndex) * this.b.d;
      }

      return this.b.getOriginalImage().a(x, y);
   }

   public SpriteContents.d getSpriteContentsTicker() {
      return this.spriteContentsTicker;
   }

   public void setSpriteContentsTicker(SpriteContents.d spriteContentsTicker) {
      if (this.spriteContentsTicker != null) {
         this.spriteContentsTicker.close();
      }

      this.spriteContentsTicker = spriteContentsTicker;
      if (this.spriteContentsTicker != null && this.parentSprite != null && this.parentSprite.b != null) {
         this.usesParentAnimationTime = matchesTiming(this.b.getAnimatedTexture(), this.parentSprite.b.getAnimatedTexture());
      }
   }

   public void setTicker(TextureAtlasSprite.a ticker) {
      if (ticker.getSpriteTicker() instanceof SpriteContents.d spriteContentsTicker) {
         this.setSpriteContentsTicker(spriteContentsTicker);
      }
   }

   public void increaseMipLevel(int mipLevelIn) {
      this.b.a(mipLevelIn);
      if (this.spriteNormal != null) {
         this.spriteNormal.increaseMipLevel(mipLevelIn);
      }

      if (this.spriteSpecular != null) {
         this.spriteSpecular.increaseMipLevel(mipLevelIn);
      }
   }

   public interface a extends AutoCloseable {
      void a();

      void close();

      default TextureAtlasSprite getSprite() {
         return null;
      }

      default SpriteTicker getSpriteTicker() {
         return null;
      }
   }
}
