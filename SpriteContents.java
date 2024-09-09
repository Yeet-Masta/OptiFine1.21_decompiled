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
import net.minecraft.src.C_212950_;
import net.minecraft.src.C_243504_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_5204_;
import net.minecraftforge.client.textures.ForgeTextureMetadata;
import net.optifine.SmartAnimations;
import net.optifine.texture.ColorBlenderKeepAlpha;
import net.optifine.texture.IColorBlender;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class SpriteContents implements Stitcher.a, AutoCloseable {
   private static final Logger a = LogUtils.getLogger();
   private final ResourceLocation b;
   int c;
   int d;
   private NativeImage e;
   NativeImage[] f;
   @Nullable
   private final SpriteContents.a g;
   private final C_212950_ h;
   private double scaleFactor = 1.0;
   private TextureAtlasSprite sprite;
   public final ForgeTextureMetadata forgeMeta;

   public SpriteContents(ResourceLocation nameIn, C_243504_ sizeIn, NativeImage imageIn, C_212950_ metadataIn) {
      this(nameIn, sizeIn, imageIn, metadataIn, null);
   }

   public SpriteContents(ResourceLocation nameIn, C_243504_ sizeIn, NativeImage imageIn, C_212950_ metadataIn, ForgeTextureMetadata forgeMeta) {
      this.b = nameIn;
      this.c = sizeIn.f_244129_();
      this.d = sizeIn.f_244503_();
      this.h = metadataIn;
      AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)metadataIn.m_214059_(AnimationMetadataSection.a)
         .orElse(AnimationMetadataSection.e);
      this.g = this.a(sizeIn, imageIn.a(), imageIn.b(), animationmetadatasection);
      this.e = imageIn;
      this.f = new NativeImage[]{this.e};
      this.forgeMeta = forgeMeta;
   }

   public void a(int mipmapLevelIn) {
      IColorBlender colorBlender = null;
      if (this.sprite != null) {
         colorBlender = this.sprite.getTextureAtlas().getShadersColorBlender(this.sprite.spriteShadersType);
         if (this.sprite.spriteShadersType == null) {
            if (!this.b.a().endsWith("_leaves")) {
               TextureAtlasSprite.fixTransparentColor(this.e);
               this.f[0] = this.e;
            }

            if (colorBlender == null && this.b.a().endsWith("glass_pane_top")) {
               colorBlender = new ColorBlenderKeepAlpha();
            }
         }
      }

      try {
         this.f = MipmapGenerator.generateMipLevels(this.f, mipmapLevelIn, colorBlender);
      } catch (Throwable var7) {
         CrashReport crashreport = CrashReport.a(var7, "Generating mipmaps for frame");
         C_4909_ crashreportcategory = crashreport.a("Sprite being mipmapped");
         crashreportcategory.m_128165_("First frame", () -> {
            StringBuilder stringbuilder = new StringBuilder();
            if (stringbuilder.length() > 0) {
               stringbuilder.append(", ");
            }

            stringbuilder.append(this.e.a()).append("x").append(this.e.b());
            return stringbuilder.toString();
         });
         C_4909_ crashreportcategory1 = crashreport.a("Frame being iterated");
         crashreportcategory1.m_128159_("Sprite name", this.b);
         crashreportcategory1.m_128165_("Sprite size", () -> this.c + " x " + this.d);
         crashreportcategory1.m_128165_("Sprite frames", () -> this.g() + " frames");
         crashreportcategory1.m_128159_("Mipmap levels", mipmapLevelIn);
         throw new C_5204_(crashreport);
      }
   }

   private int g() {
      return this.g != null ? this.g.b.size() : 1;
   }

   @Nullable
   private SpriteContents.a a(C_243504_ frameSizeIn, int widthIn, int heightIn, AnimationMetadataSection metadataIn) {
      int i = widthIn / frameSizeIn.f_244129_();
      int j = heightIn / frameSizeIn.f_244503_();
      int k = i * j;
      List<SpriteContents.b> list = new ArrayList();
      metadataIn.a((indexIn, timeIn) -> list.add(new SpriteContents.b(indexIn, timeIn)));
      if (list.isEmpty()) {
         for (int l = 0; l < k; l++) {
            list.add(new SpriteContents.b(l, metadataIn.a()));
         }
      } else {
         int i1 = 0;
         IntSet intset = new IntOpenHashSet();

         for (Iterator<SpriteContents.b> iterator = list.iterator(); iterator.hasNext(); i1++) {
            SpriteContents.b spritecontents$frameinfo = (SpriteContents.b)iterator.next();
            boolean flag = true;
            if (spritecontents$frameinfo.b <= 0) {
               a.warn("Invalid frame duration on sprite {} frame {}: {}", new Object[]{this.b, i1, spritecontents$frameinfo.b});
               flag = false;
            }

            if (spritecontents$frameinfo.a < 0 || spritecontents$frameinfo.a >= k) {
               a.warn("Invalid frame index on sprite {} frame {}: {}", new Object[]{this.b, i1, spritecontents$frameinfo.a});
               flag = false;
            }

            if (flag) {
               intset.add(spritecontents$frameinfo.a);
            } else {
               iterator.remove();
            }
         }

         int[] aint = IntStream.range(0, k).filter(indexIn -> !intset.contains(indexIn)).toArray();
         if (aint.length > 0) {
            a.warn("Unused frames in sprite {}: {}", this.b, Arrays.toString(aint));
         }
      }

      return list.size() <= 1 ? null : new SpriteContents.a(ImmutableList.copyOf(list), i, metadataIn.b());
   }

   void a(int xIn, int yIn, int xOffsetIn, int yOffsetIn, NativeImage[] framesIn) {
      for (int i = 0; i < this.f.length && this.c >> i > 0 && this.d >> i > 0; i++) {
         framesIn[i].a(i, xIn >> i, yIn >> i, xOffsetIn >> i, yOffsetIn >> i, this.c >> i, this.d >> i, this.f.length > 1, false);
      }
   }

   @Override
   public int a() {
      return this.c;
   }

   @Override
   public int b() {
      return this.d;
   }

   @Override
   public ResourceLocation c() {
      return this.b;
   }

   public IntStream d() {
      return this.g != null ? this.g.b() : IntStream.of(1);
   }

   @Nullable
   public SpriteTicker e() {
      return this.g != null ? this.g.a() : null;
   }

   public C_212950_ f() {
      return this.h;
   }

   public void close() {
      for (NativeImage nativeimage : this.f) {
         nativeimage.close();
      }
   }

   public String toString() {
      return "SpriteContents{name=" + this.b + ", frameCount=" + this.g() + ", height=" + this.d + ", width=" + this.c + "}";
   }

   public boolean a(int frameIndexIn, int xIn, int yIn) {
      int i = xIn;
      int j = yIn;
      if (this.g != null) {
         i = xIn + this.g.a(frameIndexIn) * this.c;
         j = yIn + this.g.b(frameIndexIn) * this.d;
      }

      return (this.e.a(i, j) >> 24 & 0xFF) == 0;
   }

   public void a(int xIn, int yIn) {
      if (this.g != null) {
         this.g.a(xIn, yIn);
      } else {
         this.a(xIn, yIn, 0, 0, this.f);
      }
   }

   public int getSpriteWidth() {
      return this.c;
   }

   public int getSpriteHeight() {
      return this.d;
   }

   public ResourceLocation getSpriteLocation() {
      return this.b;
   }

   public void setSpriteWidth(int spriteWidth) {
      this.c = spriteWidth;
   }

   public void setSpriteHeight(int spriteHeight) {
      this.d = spriteHeight;
   }

   public double getScaleFactor() {
      return this.scaleFactor;
   }

   public void setScaleFactor(double scaleFactor) {
      this.scaleFactor = scaleFactor;
   }

   public void rescale() {
      if (this.scaleFactor > 1.0) {
         int widthScaled = (int)Math.round((double)this.e.a() * this.scaleFactor);
         NativeImage imageScaled = TextureUtils.scaleImage(this.e, widthScaled);
         if (imageScaled != this.e) {
            this.e.close();
            this.e = imageScaled;
            this.f[0] = this.e;
         }
      }
   }

   public SpriteContents.a getAnimatedTexture() {
      return this.g;
   }

   public NativeImage getOriginalImage() {
      return this.e;
   }

   public TextureAtlasSprite getSprite() {
      return this.sprite;
   }

   public void setSprite(TextureAtlasSprite sprite) {
      this.sprite = sprite;
   }

   class a {
      final List<SpriteContents.b> b;
      final int c;
      final boolean d;

      a(final List<SpriteContents.b> framesIn, final int rowSizeIn, final boolean interpolateIn) {
         this.b = framesIn;
         this.c = rowSizeIn;
         this.d = interpolateIn;
      }

      int a(int frameIndexIn) {
         return frameIndexIn % this.c;
      }

      int b(int frameIndexIn) {
         return frameIndexIn / this.c;
      }

      void a(int xIn, int yIn, int indexIn) {
         int i = this.a(indexIn) * SpriteContents.this.c;
         int j = this.b(indexIn) * SpriteContents.this.d;
         SpriteContents.this.a(xIn, yIn, i, j, SpriteContents.this.f);
      }

      public SpriteTicker a() {
         return SpriteContents.this.new d(this, this.d ? SpriteContents.this.new c() : null);
      }

      public void a(int xIn, int yIn) {
         this.a(xIn, yIn, ((SpriteContents.b)this.b.get(0)).a);
      }

      public IntStream b() {
         return this.b.stream().mapToInt(infoIn -> infoIn.a).distinct();
      }
   }

   static class b {
      final int a;
      final int b;

      b(int indexIn, int timeIn) {
         this.a = indexIn;
         this.b = timeIn;
      }
   }

   final class c implements AutoCloseable {
      private final NativeImage[] b = new NativeImage[SpriteContents.this.f.length];

      c() {
         for (int i = 0; i < this.b.length; i++) {
            int j = SpriteContents.this.c >> i;
            int k = SpriteContents.this.d >> i;
            j = Math.max(1, j);
            k = Math.max(1, k);
            this.b[i] = new NativeImage(j, k, false);
         }
      }

      void a(int xIn, int yIn, SpriteContents.d tickerIn) {
         SpriteContents.a spritecontents$animatedtexture = tickerIn.c;
         List<SpriteContents.b> list = spritecontents$animatedtexture.b;
         SpriteContents.b spritecontents$frameinfo = (SpriteContents.b)list.get(tickerIn.a);
         double d0 = 1.0 - (double)tickerIn.b / (double)spritecontents$frameinfo.b;
         int i = spritecontents$frameinfo.a;
         int j = ((SpriteContents.b)list.get((tickerIn.a + 1) % list.size())).a;
         if (i != j) {
            for (int k = 0; k < this.b.length; k++) {
               int l = SpriteContents.this.c >> k;
               int i1 = SpriteContents.this.d >> k;
               if (l >= 1 && i1 >= 1) {
                  for (int j1 = 0; j1 < i1; j1++) {
                     for (int k1 = 0; k1 < l; k1++) {
                        int l1 = this.a(spritecontents$animatedtexture, i, k, k1, j1);
                        int i2 = this.a(spritecontents$animatedtexture, j, k, k1, j1);
                        int j2 = this.a(d0, l1 >> 16 & 0xFF, i2 >> 16 & 0xFF);
                        int k2 = this.a(d0, l1 >> 8 & 0xFF, i2 >> 8 & 0xFF);
                        int l2 = this.a(d0, l1 & 0xFF, i2 & 0xFF);
                        this.b[k].a(k1, j1, l1 & 0xFF000000 | j2 << 16 | k2 << 8 | l2);
                     }
                  }
               }
            }

            SpriteContents.this.a(xIn, yIn, 0, 0, this.b);
         }
      }

      private int a(SpriteContents.a textureIn, int frameIndexIn, int mipmapLevelIn, int xIn, int yIn) {
         return SpriteContents.this.f[mipmapLevelIn]
            .a(
               xIn + (textureIn.a(frameIndexIn) * SpriteContents.this.c >> mipmapLevelIn),
               yIn + (textureIn.b(frameIndexIn) * SpriteContents.this.d >> mipmapLevelIn)
            );
      }

      private int a(double k, int a, int b) {
         return (int)(k * (double)a + (1.0 - k) * (double)b);
      }

      public void close() {
         for (NativeImage nativeimage : this.b) {
            nativeimage.close();
         }
      }
   }

   class d implements SpriteTicker {
      int a;
      int b;
      final SpriteContents.a c;
      @Nullable
      private final SpriteContents.c d;
      protected boolean animationActive = false;
      protected TextureAtlasSprite sprite;

      d(@Nullable final SpriteContents.a infoIn, final SpriteContents.c dataIn) {
         this.c = infoIn;
         this.d = dataIn;
      }

      @Override
      public void a(int xIn, int yIn) {
         this.animationActive = SmartAnimations.isActive() ? SmartAnimations.isSpriteRendered(this.sprite) : true;
         if (this.c.b.size() <= 1) {
            this.animationActive = false;
         }

         this.b++;
         SpriteContents.b spritecontents$frameinfo = (SpriteContents.b)this.c.b.get(this.a);
         if (this.b >= spritecontents$frameinfo.b) {
            int i = spritecontents$frameinfo.a;
            this.a = (this.a + 1) % this.c.b.size();
            this.b = 0;
            int j = ((SpriteContents.b)this.c.b.get(this.a)).a;
            if (!this.animationActive) {
               return;
            }

            if (i != j) {
               this.c.a(xIn, yIn, j);
            }
         } else if (this.d != null) {
            if (!this.animationActive) {
               return;
            }

            if (!RenderSystem.isOnRenderThread()) {
               RenderSystem.recordRenderCall(() -> this.d.a(xIn, yIn, this));
            } else {
               this.d.a(xIn, yIn, this);
            }
         }
      }

      @Override
      public void close() {
         if (this.d != null) {
            this.d.close();
         }
      }

      @Override
      public TextureAtlasSprite getSprite() {
         return this.sprite;
      }

      @Override
      public void setSprite(TextureAtlasSprite sprite) {
         this.sprite = sprite;
      }

      public String toString() {
         return "animation:" + SpriteContents.this.toString();
      }
   }
}
