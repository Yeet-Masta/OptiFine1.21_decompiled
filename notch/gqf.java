package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraftforge.client.textures.ForgeTextureMetadata;
import net.optifine.SmartAnimations;
import net.optifine.texture.ColorBlenderKeepAlpha;
import net.optifine.texture.IColorBlender;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class C_243582_ implements C_4478_.C_243583_, AutoCloseable {
   private static final Logger f_243663_ = LogUtils.getLogger();
   private final C_5265_ f_243877_;
   int f_244302_;
   int f_244600_;
   private C_3148_ f_243904_;
   C_3148_[] f_243731_;
   @Nullable
   private final C_243582_.C_243458_ f_244575_;
   private final C_212950_ f_290652_;
   private double scaleFactor = 1.0;
   private C_4486_ sprite;
   public final ForgeTextureMetadata forgeMeta;

   public C_243582_(C_5265_ nameIn, C_243504_ sizeIn, C_3148_ imageIn, C_212950_ metadataIn) {
      this(nameIn, sizeIn, imageIn, metadataIn, null);
   }

   public C_243582_(C_5265_ nameIn, C_243504_ sizeIn, C_3148_ imageIn, C_212950_ metadataIn, ForgeTextureMetadata forgeMeta) {
      this.f_243877_ = nameIn;
      this.f_244302_ = sizeIn.f_244129_();
      this.f_244600_ = sizeIn.f_244503_();
      this.f_290652_ = metadataIn;
      C_4518_ animationmetadatasection = (C_4518_)metadataIn.m_214059_(C_4518_.f_119011_).orElse(C_4518_.f_119012_);
      this.f_244575_ = this.m_247391_(sizeIn, imageIn.m_84982_(), imageIn.m_85084_(), animationmetadatasection);
      this.f_243904_ = imageIn;
      this.f_243731_ = new C_3148_[]{this.f_243904_};
      this.forgeMeta = forgeMeta;
   }

   public void m_246368_(int mipmapLevelIn) {
      IColorBlender colorBlender = null;
      if (this.sprite != null) {
         colorBlender = this.sprite.getTextureAtlas().getShadersColorBlender(this.sprite.spriteShadersType);
         if (this.sprite.spriteShadersType == null) {
            if (!this.f_243877_.m_135815_().endsWith("_leaves")) {
               C_4486_.fixTransparentColor(this.f_243904_);
               this.f_243731_[0] = this.f_243904_;
            }

            if (colorBlender == null && this.f_243877_.m_135815_().endsWith("glass_pane_top")) {
               colorBlender = new ColorBlenderKeepAlpha();
            }
         }
      }

      try {
         this.f_243731_ = C_4472_.generateMipLevels(this.f_243731_, mipmapLevelIn, colorBlender);
      } catch (Throwable var7) {
         C_4883_ crashreport = C_4883_.m_127521_(var7, "Generating mipmaps for frame");
         C_4909_ crashreportcategory = crashreport.m_127514_("Sprite being mipmapped");
         crashreportcategory.m_128165_("First frame", () -> {
            StringBuilder stringbuilder = new StringBuilder();
            if (stringbuilder.length() > 0) {
               stringbuilder.append(", ");
            }

            stringbuilder.append(this.f_243904_.m_84982_()).append("x").append(this.f_243904_.m_85084_());
            return stringbuilder.toString();
         });
         C_4909_ crashreportcategory1 = crashreport.m_127514_("Frame being iterated");
         crashreportcategory1.m_128159_("Sprite name", this.f_243877_);
         crashreportcategory1.m_128165_("Sprite size", () -> this.f_244302_ + " x " + this.f_244600_);
         crashreportcategory1.m_128165_("Sprite frames", () -> this.m_245088_() + " frames");
         crashreportcategory1.m_128159_("Mipmap levels", mipmapLevelIn);
         throw new C_5204_(crashreport);
      }
   }

   private int m_245088_() {
      return this.f_244575_ != null ? this.f_244575_.f_243714_.size() : 1;
   }

   @Nullable
   private C_243582_.C_243458_ m_247391_(C_243504_ frameSizeIn, int widthIn, int heightIn, C_4518_ metadataIn) {
      int i = widthIn / frameSizeIn.f_244129_();
      int j = heightIn / frameSizeIn.f_244503_();
      int k = i * j;
      List<C_243582_.C_243413_> list = new ArrayList();
      metadataIn.m_174861_((indexIn, timeIn) -> list.add(new C_243582_.C_243413_(indexIn, timeIn)));
      if (list.isEmpty()) {
         for (int l = 0; l < k; l++) {
            list.add(new C_243582_.C_243413_(l, metadataIn.m_119030_()));
         }
      } else {
         int i1 = 0;
         IntSet intset = new IntOpenHashSet();

         for (Iterator<C_243582_.C_243413_> iterator = list.iterator(); iterator.hasNext(); i1++) {
            C_243582_.C_243413_ spritecontents$frameinfo = (C_243582_.C_243413_)iterator.next();
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

         int[] aint = IntStream.range(0, k).filter(indexIn -> !intset.contains(indexIn)).toArray();
         if (aint.length > 0) {
            f_243663_.warn("Unused frames in sprite {}: {}", this.f_243877_, Arrays.toString(aint));
         }
      }

      return list.size() <= 1 ? null : new C_243582_.C_243458_(ImmutableList.copyOf(list), i, metadataIn.m_119036_());
   }

   void m_247381_(int xIn, int yIn, int xOffsetIn, int yOffsetIn, C_3148_[] framesIn) {
      for (int i = 0; i < this.f_243731_.length && this.f_244302_ >> i > 0 && this.f_244600_ >> i > 0; i++) {
         framesIn[i]
            .m_85003_(i, xIn >> i, yIn >> i, xOffsetIn >> i, yOffsetIn >> i, this.f_244302_ >> i, this.f_244600_ >> i, this.f_243731_.length > 1, false);
      }
   }

   @Override
   public int m_246492_() {
      return this.f_244302_;
   }

   @Override
   public int m_245330_() {
      return this.f_244600_;
   }

   @Override
   public C_5265_ m_246162_() {
      return this.f_243877_;
   }

   public IntStream m_245638_() {
      return this.f_244575_ != null ? this.f_244575_.m_246130_() : IntStream.of(1);
   }

   @Nullable
   public C_243576_ m_246786_() {
      return this.f_244575_ != null ? this.f_244575_.m_246690_() : null;
   }

   public C_212950_ m_293312_() {
      return this.f_290652_;
   }

   public void close() {
      for (C_3148_ nativeimage : this.f_243731_) {
         nativeimage.close();
      }
   }

   public String toString() {
      return "SpriteContents{name=" + this.f_243877_ + ", frameCount=" + this.m_245088_() + ", height=" + this.f_244600_ + ", width=" + this.f_244302_ + "}";
   }

   public boolean m_245970_(int frameIndexIn, int xIn, int yIn) {
      int i = xIn;
      int j = yIn;
      if (this.f_244575_ != null) {
         i = xIn + this.f_244575_.m_245080_(frameIndexIn) * this.f_244302_;
         j = yIn + this.f_244575_.m_246436_(frameIndexIn) * this.f_244600_;
      }

      return (this.f_243904_.m_84985_(i, j) >> 24 & 0xFF) == 0;
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

   public C_5265_ getSpriteLocation() {
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
         C_3148_ imageScaled = TextureUtils.scaleImage(this.f_243904_, widthScaled);
         if (imageScaled != this.f_243904_) {
            this.f_243904_.close();
            this.f_243904_ = imageScaled;
            this.f_243731_[0] = this.f_243904_;
         }
      }
   }

   public C_243582_.C_243458_ getAnimatedTexture() {
      return this.f_244575_;
   }

   public C_3148_ getOriginalImage() {
      return this.f_243904_;
   }

   public C_4486_ getSprite() {
      return this.sprite;
   }

   public void setSprite(C_4486_ sprite) {
      this.sprite = sprite;
   }

   static class C_243413_ {
      final int f_243751_;
      final int f_244553_;

      C_243413_(int indexIn, int timeIn) {
         this.f_243751_ = indexIn;
         this.f_244553_ = timeIn;
      }
   }

   class C_243426_ implements C_243576_ {
      int f_244631_;
      int f_244511_;
      final C_243582_.C_243458_ f_243921_;
      @Nullable
      private final C_243582_.C_243617_ f_244570_;
      protected boolean animationActive = false;
      protected C_4486_ sprite;

      C_243426_(@Nullable final C_243582_.C_243458_ infoIn, final C_243582_.C_243617_ dataIn) {
         this.f_243921_ = infoIn;
         this.f_244570_ = dataIn;
      }

      @Override
      public void m_247697_(int xIn, int yIn) {
         this.animationActive = SmartAnimations.isActive() ? SmartAnimations.isSpriteRendered(this.sprite) : true;
         if (this.f_243921_.f_243714_.size() <= 1) {
            this.animationActive = false;
         }

         this.f_244511_++;
         C_243582_.C_243413_ spritecontents$frameinfo = (C_243582_.C_243413_)this.f_243921_.f_243714_.get(this.f_244631_);
         if (this.f_244511_ >= spritecontents$frameinfo.f_244553_) {
            int i = spritecontents$frameinfo.f_243751_;
            this.f_244631_ = (this.f_244631_ + 1) % this.f_243921_.f_243714_.size();
            this.f_244511_ = 0;
            int j = ((C_243582_.C_243413_)this.f_243921_.f_243714_.get(this.f_244631_)).f_243751_;
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
               RenderSystem.recordRenderCall(() -> this.f_244570_.m_245152_(xIn, yIn, this));
            } else {
               this.f_244570_.m_245152_(xIn, yIn, this);
            }
         }
      }

      @Override
      public void close() {
         if (this.f_244570_ != null) {
            this.f_244570_.close();
         }
      }

      @Override
      public C_4486_ getSprite() {
         return this.sprite;
      }

      @Override
      public void setSprite(C_4486_ sprite) {
         this.sprite = sprite;
      }

      public String toString() {
         return "animation:" + C_243582_.this.toString();
      }
   }

   class C_243458_ {
      final List<C_243582_.C_243413_> f_243714_;
      final int f_244229_;
      final boolean f_244317_;

      C_243458_(final List<C_243582_.C_243413_> framesIn, final int rowSizeIn, final boolean interpolateIn) {
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
         int i = this.m_245080_(indexIn) * C_243582_.this.f_244302_;
         int j = this.m_246436_(indexIn) * C_243582_.this.f_244600_;
         C_243582_.this.m_247381_(xIn, yIn, i, j, C_243582_.this.f_243731_);
      }

      public C_243576_ m_246690_() {
         return C_243582_.this.new C_243426_(this, this.f_244317_ ? C_243582_.this.new C_243617_() : null);
      }

      public void m_247129_(int xIn, int yIn) {
         this.m_245074_(xIn, yIn, ((C_243582_.C_243413_)this.f_243714_.get(0)).f_243751_);
      }

      public IntStream m_246130_() {
         return this.f_243714_.stream().mapToInt(infoIn -> infoIn.f_243751_).distinct();
      }
   }

   final class C_243617_ implements AutoCloseable {
      private final C_3148_[] f_244527_ = new C_3148_[C_243582_.this.f_243731_.length];

      C_243617_() {
         for (int i = 0; i < this.f_244527_.length; i++) {
            int j = C_243582_.this.f_244302_ >> i;
            int k = C_243582_.this.f_244600_ >> i;
            j = Math.max(1, j);
            k = Math.max(1, k);
            this.f_244527_[i] = new C_3148_(j, k, false);
         }
      }

      void m_245152_(int xIn, int yIn, C_243582_.C_243426_ tickerIn) {
         C_243582_.C_243458_ spritecontents$animatedtexture = tickerIn.f_243921_;
         List<C_243582_.C_243413_> list = spritecontents$animatedtexture.f_243714_;
         C_243582_.C_243413_ spritecontents$frameinfo = (C_243582_.C_243413_)list.get(tickerIn.f_244631_);
         double d0 = 1.0 - (double)tickerIn.f_244511_ / (double)spritecontents$frameinfo.f_244553_;
         int i = spritecontents$frameinfo.f_243751_;
         int j = ((C_243582_.C_243413_)list.get((tickerIn.f_244631_ + 1) % list.size())).f_243751_;
         if (i != j) {
            for (int k = 0; k < this.f_244527_.length; k++) {
               int l = C_243582_.this.f_244302_ >> k;
               int i1 = C_243582_.this.f_244600_ >> k;
               if (l >= 1 && i1 >= 1) {
                  for (int j1 = 0; j1 < i1; j1++) {
                     for (int k1 = 0; k1 < l; k1++) {
                        int l1 = this.m_246491_(spritecontents$animatedtexture, i, k, k1, j1);
                        int i2 = this.m_246491_(spritecontents$animatedtexture, j, k, k1, j1);
                        int j2 = this.m_247111_(d0, l1 >> 16 & 0xFF, i2 >> 16 & 0xFF);
                        int k2 = this.m_247111_(d0, l1 >> 8 & 0xFF, i2 >> 8 & 0xFF);
                        int l2 = this.m_247111_(d0, l1 & 0xFF, i2 & 0xFF);
                        this.f_244527_[k].m_84988_(k1, j1, l1 & 0xFF000000 | j2 << 16 | k2 << 8 | l2);
                     }
                  }
               }
            }

            C_243582_.this.m_247381_(xIn, yIn, 0, 0, this.f_244527_);
         }
      }

      private int m_246491_(C_243582_.C_243458_ textureIn, int frameIndexIn, int mipmapLevelIn, int xIn, int yIn) {
         return C_243582_.this.f_243731_[mipmapLevelIn]
            .m_84985_(
               xIn + (textureIn.m_245080_(frameIndexIn) * C_243582_.this.f_244302_ >> mipmapLevelIn),
               yIn + (textureIn.m_246436_(frameIndexIn) * C_243582_.this.f_244600_ >> mipmapLevelIn)
            );
      }

      private int m_247111_(double k, int a, int b) {
         return (int)(k * (double)a + (1.0 - k) * (double)b);
      }

      public void close() {
         for (C_3148_ nativeimage : this.f_244527_) {
            nativeimage.close();
         }
      }
   }
}
