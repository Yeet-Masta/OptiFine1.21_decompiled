package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraftforge.client.textures.ForgeTextureMetadata;
import net.optifine.SmartAnimations;
import net.optifine.texture.ColorBlenderKeepAlpha;
import net.optifine.texture.IColorBlender;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class SpriteContents implements Stitcher.Entry, AutoCloseable {
   private static final Logger f_243663_ = LogUtils.getLogger();
   private final ResourceLocation f_243877_;
   int f_244302_;
   int f_244600_;
   private NativeImage f_243904_;
   NativeImage[] f_243731_;
   @Nullable
   private final AnimatedTexture f_244575_;
   private final ResourceMetadata f_290652_;
   private double scaleFactor;
   private TextureAtlasSprite sprite;
   public final ForgeTextureMetadata forgeMeta;

   public SpriteContents(ResourceLocation nameIn, FrameSize sizeIn, NativeImage imageIn, ResourceMetadata metadataIn) {
      this(nameIn, sizeIn, imageIn, metadataIn, (ForgeTextureMetadata)null);
   }

   public SpriteContents(ResourceLocation nameIn, FrameSize sizeIn, NativeImage imageIn, ResourceMetadata metadataIn, ForgeTextureMetadata forgeMeta) {
      this.scaleFactor = 1.0;
      this.f_243877_ = nameIn;
      this.f_244302_ = sizeIn.f_244129_();
      this.f_244600_ = sizeIn.f_244503_();
      this.f_290652_ = metadataIn;
      AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)metadataIn.m_214059_(AnimationMetadataSection.f_119011_).orElse(AnimationMetadataSection.f_119012_);
      this.f_244575_ = this.m_247391_(sizeIn, imageIn.m_84982_(), imageIn.m_85084_(), animationmetadatasection);
      this.f_243904_ = imageIn;
      this.f_243731_ = new NativeImage[]{this.f_243904_};
      this.forgeMeta = forgeMeta;
   }

   public void m_246368_(int mipmapLevelIn) {
      IColorBlender colorBlender = null;
      if (this.sprite != null) {
         colorBlender = this.sprite.getTextureAtlas().getShadersColorBlender(this.sprite.spriteShadersType);
         if (this.sprite.spriteShadersType == null) {
            if (!this.f_243877_.m_135815_().endsWith("_leaves")) {
               TextureAtlasSprite.fixTransparentColor(this.f_243904_);
               this.f_243731_[0] = this.f_243904_;
            }

            if (colorBlender == null && this.f_243877_.m_135815_().endsWith("glass_pane_top")) {
               colorBlender = new ColorBlenderKeepAlpha();
            }
         }
      }

      try {
         this.f_243731_ = MipmapGenerator.generateMipLevels(this.f_243731_, mipmapLevelIn, (IColorBlender)colorBlender);
      } catch (Throwable var7) {
         CrashReport crashreport = CrashReport.m_127521_(var7, "Generating mipmaps for frame");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Sprite being mipmapped");
         crashreportcategory.m_128165_("First frame", () -> {
            StringBuilder stringbuilder = new StringBuilder();
            if (stringbuilder.length() > 0) {
               stringbuilder.append(", ");
            }

            stringbuilder.append(this.f_243904_.m_84982_()).append("x").append(this.f_243904_.m_85084_());
            return stringbuilder.toString();
         });
         CrashReportCategory crashreportcategory1 = crashreport.m_127514_("Frame being iterated");
         crashreportcategory1.m_128159_("Sprite name", this.f_243877_);
         crashreportcategory1.m_128165_("Sprite size", () -> {
            return this.f_244302_ + " x " + this.f_244600_;
         });
         crashreportcategory1.m_128165_("Sprite frames", () -> {
            return this.m_245088_() + " frames";
         });
         crashreportcategory1.m_128159_("Mipmap levels", mipmapLevelIn);
         throw new ReportedException(crashreport);
      }
   }

   private int m_245088_() {
      return this.f_244575_ != null ? this.f_244575_.f_243714_.size() : 1;
   }

   @Nullable
   private AnimatedTexture m_247391_(FrameSize frameSizeIn, int widthIn, int heightIn, AnimationMetadataSection metadataIn) {
      int i = widthIn / frameSizeIn.f_244129_();
      int j = heightIn / frameSizeIn.f_244503_();
      int k = i * j;
      List list = new ArrayList();
      metadataIn.m_174861_((indexIn, timeIn) -> {
         list.add(new FrameInfo(indexIn, timeIn));
      });
      int i1;
      if (list.isEmpty()) {
         for(i1 = 0; i1 < k; ++i1) {
            list.add(new FrameInfo(i1, metadataIn.m_119030_()));
         }
      } else {
         i1 = 0;
         IntSet intset = new IntOpenHashSet();

         for(Iterator iterator = list.iterator(); iterator.hasNext(); ++i1) {
            FrameInfo spritecontents$frameinfo = (FrameInfo)iterator.next();
            boolean flag = true;
            if (spritecontents$frameinfo.f_244553_ <= 0) {
               f_243663_.warn("Invalid frame duration on sprite {} frame {}: {}", new Object[]{this.f_243877_, i1, spritecontents$frameinfo.f_244553_});
               flag = false;
            }

            if (spritecontents$frameinfo.f_243751_ < 0 || spritecontents$frameinfo.f_243751_ >= k) {
               f_243663_.warn("Invalid frame index on sprite {} frame {}: {}", new Object[]{this.f_243877_, i1, spritecontents$frameinfo.f_243751_});
               flag = false;
            }

            if (flag) {
               intset.add(spritecontents$frameinfo.f_243751_);
            } else {
               iterator.remove();
            }
         }

         int[] aint = IntStream.range(0, k).filter((indexIn) -> {
            return !intset.contains(indexIn);
         }).toArray();
         if (aint.length > 0) {
            f_243663_.warn("Unused frames in sprite {}: {}", this.f_243877_, Arrays.toString(aint));
         }
      }

      return list.size() <= 1 ? null : new AnimatedTexture(ImmutableList.copyOf(list), i, metadataIn.m_119036_());
   }

   void m_247381_(int xIn, int yIn, int xOffsetIn, int yOffsetIn, NativeImage[] framesIn) {
      for(int i = 0; i < this.f_243731_.length && this.f_244302_ >> i > 0 && this.f_244600_ >> i > 0; ++i) {
         framesIn[i].m_85003_(i, xIn >> i, yIn >> i, xOffsetIn >> i, yOffsetIn >> i, this.f_244302_ >> i, this.f_244600_ >> i, this.f_243731_.length > 1, false);
      }

   }

   public int m_246492_() {
      return this.f_244302_;
   }

   public int m_245330_() {
      return this.f_244600_;
   }

   public ResourceLocation m_246162_() {
      return this.f_243877_;
   }

   public IntStream m_245638_() {
      return this.f_244575_ != null ? this.f_244575_.m_246130_() : IntStream.of(1);
   }

   @Nullable
   public SpriteTicker m_246786_() {
      return this.f_244575_ != null ? this.f_244575_.m_246690_() : null;
   }

   public ResourceMetadata m_293312_() {
      return this.f_290652_;
   }

   public void close() {
      NativeImage[] var1 = this.f_243731_;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         NativeImage nativeimage = var1[var3];
         nativeimage.close();
      }

   }

   public String toString() {
      String var10000 = String.valueOf(this.f_243877_);
      return "SpriteContents{name=" + var10000 + ", frameCount=" + this.m_245088_() + ", height=" + this.f_244600_ + ", width=" + this.f_244302_ + "}";
   }

   public boolean m_245970_(int frameIndexIn, int xIn, int yIn) {
      int i = xIn;
      int j = yIn;
      if (this.f_244575_ != null) {
         i = xIn + this.f_244575_.m_245080_(frameIndexIn) * this.f_244302_;
         j = yIn + this.f_244575_.m_246436_(frameIndexIn) * this.f_244600_;
      }

      return (this.f_243904_.m_84985_(i, j) >> 24 & 255) == 0;
   }

   public void m_246850_(int xIn, int yIn) {
      if (this.f_244575_ != null) {
         this.f_244575_.m_247129_(xIn, yIn);
      } else {
         this.m_247381_(xIn, yIn, 0, 0, this.f_243731_);
      }

   }

   public int getSpriteWidth() {
      return this.f_244302_;
   }

   public int getSpriteHeight() {
      return this.f_244600_;
   }

   public ResourceLocation getSpriteLocation() {
      return this.f_243877_;
   }

   public void setSpriteWidth(int spriteWidth) {
      this.f_244302_ = spriteWidth;
   }

   public void setSpriteHeight(int spriteHeight) {
      this.f_244600_ = spriteHeight;
   }

   public double getScaleFactor() {
      return this.scaleFactor;
   }

   public void setScaleFactor(double scaleFactor) {
      this.scaleFactor = scaleFactor;
   }

   public void rescale() {
      if (this.scaleFactor > 1.0) {
         int widthScaled = (int)Math.round((double)this.f_243904_.m_84982_() * this.scaleFactor);
         NativeImage imageScaled = TextureUtils.scaleImage(this.f_243904_, widthScaled);
         if (imageScaled != this.f_243904_) {
            this.f_243904_.close();
            this.f_243904_ = imageScaled;
            this.f_243731_[0] = this.f_243904_;
         }
      }

   }

   public AnimatedTexture getAnimatedTexture() {
      return this.f_244575_;
   }

   public NativeImage getOriginalImage() {
      return this.f_243904_;
   }

   public TextureAtlasSprite getSprite() {
      return this.sprite;
   }

   public void setSprite(TextureAtlasSprite sprite) {
      this.sprite = sprite;
   }

   class AnimatedTexture {
      final List f_243714_;
      final int f_244229_;
      final boolean f_244317_;

      AnimatedTexture(final List framesIn, final int rowSizeIn, final boolean interpolateIn) {
         this.f_243714_ = framesIn;
         this.f_244229_ = rowSizeIn;
         this.f_244317_ = interpolateIn;
      }

      int m_245080_(int frameIndexIn) {
         return frameIndexIn % this.f_244229_;
      }

      int m_246436_(int frameIndexIn) {
         return frameIndexIn / this.f_244229_;
      }

      void m_245074_(int xIn, int yIn, int indexIn) {
         int i = this.m_245080_(indexIn) * SpriteContents.this.f_244302_;
         int j = this.m_246436_(indexIn) * SpriteContents.this.f_244600_;
         SpriteContents.this.m_247381_(xIn, yIn, i, j, SpriteContents.this.f_243731_);
      }

      public SpriteTicker m_246690_() {
         Ticker var10000 = new Ticker;
         SpriteContents var10002 = SpriteContents.this;
         Objects.requireNonNull(var10002);
         InterpolationData var10004;
         if (this.f_244317_) {
            SpriteContents var10006 = SpriteContents.this;
            Objects.requireNonNull(var10006);
            var10004 = var10006.new InterpolationData();
         } else {
            var10004 = null;
         }

         var10000.<init>(this, var10004);
         return var10000;
      }

      public void m_247129_(int xIn, int yIn) {
         this.m_245074_(xIn, yIn, ((FrameInfo)this.f_243714_.get(0)).f_243751_);
      }

      public IntStream m_246130_() {
         return this.f_243714_.stream().mapToInt((infoIn) -> {
            return infoIn.f_243751_;
         }).distinct();
      }
   }

   static class FrameInfo {
      final int f_243751_;
      final int f_244553_;

      FrameInfo(int indexIn, int timeIn) {
         this.f_243751_ = indexIn;
         this.f_244553_ = timeIn;
      }
   }

   class Ticker implements SpriteTicker {
      int f_244631_;
      int f_244511_;
      final AnimatedTexture f_243921_;
      @Nullable
      private final InterpolationData f_244570_;
      protected boolean animationActive = false;
      protected TextureAtlasSprite sprite;

      Ticker(@Nullable final AnimatedTexture infoIn, final InterpolationData dataIn) {
         this.f_243921_ = infoIn;
         this.f_244570_ = dataIn;
      }

      public void m_247697_(int xIn, int yIn) {
         this.animationActive = SmartAnimations.isActive() ? SmartAnimations.isSpriteRendered(this.sprite) : true;
         if (this.f_243921_.f_243714_.size() <= 1) {
            this.animationActive = false;
         }

         ++this.f_244511_;
         FrameInfo spritecontents$frameinfo = (FrameInfo)this.f_243921_.f_243714_.get(this.f_244631_);
         if (this.f_244511_ >= spritecontents$frameinfo.f_244553_) {
            int i = spritecontents$frameinfo.f_243751_;
            this.f_244631_ = (this.f_244631_ + 1) % this.f_243921_.f_243714_.size();
            this.f_244511_ = 0;
            int j = ((FrameInfo)this.f_243921_.f_243714_.get(this.f_244631_)).f_243751_;
            if (!this.animationActive) {
               return;
            }

            if (i != j) {
               this.f_243921_.m_245074_(xIn, yIn, j);
            }
         } else if (this.f_244570_ != null) {
            if (!this.animationActive) {
               return;
            }

            if (!RenderSystem.isOnRenderThread()) {
               RenderSystem.recordRenderCall(() -> {
                  this.f_244570_.m_245152_(xIn, yIn, this);
               });
            } else {
               this.f_244570_.m_245152_(xIn, yIn, this);
            }
         }

      }

      public void close() {
         if (this.f_244570_ != null) {
            this.f_244570_.close();
         }

      }

      public TextureAtlasSprite getSprite() {
         return this.sprite;
      }

      public void setSprite(TextureAtlasSprite sprite) {
         this.sprite = sprite;
      }

      public String toString() {
         return "animation:" + SpriteContents.this.toString();
      }
   }

   final class InterpolationData implements AutoCloseable {
      private final NativeImage[] f_244527_;

      InterpolationData() {
         this.f_244527_ = new NativeImage[SpriteContents.this.f_243731_.length];

         for(int i = 0; i < this.f_244527_.length; ++i) {
            int j = SpriteContents.this.f_244302_ >> i;
            int k = SpriteContents.this.f_244600_ >> i;
            j = Math.max(1, j);
            k = Math.max(1, k);
            this.f_244527_[i] = new NativeImage(j, k, false);
         }

      }

      void m_245152_(int xIn, int yIn, Ticker tickerIn) {
         AnimatedTexture spritecontents$animatedtexture = tickerIn.f_243921_;
         List list = spritecontents$animatedtexture.f_243714_;
         FrameInfo spritecontents$frameinfo = (FrameInfo)list.get(tickerIn.f_244631_);
         double d0 = 1.0 - (double)tickerIn.f_244511_ / (double)spritecontents$frameinfo.f_244553_;
         int i = spritecontents$frameinfo.f_243751_;
         int j = ((FrameInfo)list.get((tickerIn.f_244631_ + 1) % list.size())).f_243751_;
         if (i != j) {
            for(int k = 0; k < this.f_244527_.length; ++k) {
               int l = SpriteContents.this.f_244302_ >> k;
               int i1 = SpriteContents.this.f_244600_ >> k;
               if (l >= 1 && i1 >= 1) {
                  for(int j1 = 0; j1 < i1; ++j1) {
                     for(int k1 = 0; k1 < l; ++k1) {
                        int l1 = this.m_246491_(spritecontents$animatedtexture, i, k, k1, j1);
                        int i2 = this.m_246491_(spritecontents$animatedtexture, j, k, k1, j1);
                        int j2 = this.m_247111_(d0, l1 >> 16 & 255, i2 >> 16 & 255);
                        int k2 = this.m_247111_(d0, l1 >> 8 & 255, i2 >> 8 & 255);
                        int l2 = this.m_247111_(d0, l1 & 255, i2 & 255);
                        this.f_244527_[k].m_84988_(k1, j1, l1 & -16777216 | j2 << 16 | k2 << 8 | l2);
                     }
                  }
               }
            }

            SpriteContents.this.m_247381_(xIn, yIn, 0, 0, this.f_244527_);
         }

      }

      private int m_246491_(AnimatedTexture textureIn, int frameIndexIn, int mipmapLevelIn, int xIn, int yIn) {
         return SpriteContents.this.f_243731_[mipmapLevelIn].m_84985_(xIn + (textureIn.m_245080_(frameIndexIn) * SpriteContents.this.f_244302_ >> mipmapLevelIn), yIn + (textureIn.m_246436_(frameIndexIn) * SpriteContents.this.f_244600_ >> mipmapLevelIn));
      }

      private int m_247111_(double k, int a, int b) {
         return (int)(k * (double)a + (1.0 - k) * (double)b);
      }

      public void close() {
         NativeImage[] var1 = this.f_244527_;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            NativeImage nativeimage = var1[var3];
            nativeimage.close();
         }

      }
   }
}
