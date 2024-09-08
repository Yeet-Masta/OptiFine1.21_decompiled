package net.minecraft.src;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.Config;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.config.ShaderPackParser;
import net.optifine.util.PropertiesOrdered;

public class C_3565_ extends C_3573_ {
   public static final C_5265_ f_96160_ = C_5265_.m_340282_("textures/gui/title/mojangstudios.png");
   private static final int f_169316_ = C_175_.m_13660_(255, 239, 50, 61);
   private static final int f_169317_ = C_175_.m_13660_(255, 0, 0, 0);
   private static final IntSupplier f_96161_ = () -> C_3391_.m_91087_().f_91066_.m_231838_().m_231551_() ? f_169317_ : f_169316_;
   private static final int f_169318_ = 240;
   private static final float f_169319_ = 60.0F;
   private static final int f_169320_ = 60;
   private static final int f_169321_ = 120;
   private static final float f_169322_ = 0.0625F;
   private static final float f_169323_ = 0.95F;
   public static final long f_169314_ = 1000L;
   public static final long f_169315_ = 500L;
   private final C_3391_ f_96163_;
   private final C_74_ f_96164_;
   private final Consumer<Optional<Throwable>> f_96165_;
   private final boolean f_96166_;
   private float f_96167_;
   private long f_96168_ = -1L;
   private long f_96169_ = -1L;
   private int colorBackground = f_96161_.getAsInt();
   private int colorBar = f_96161_.getAsInt();
   private int colorOutline = 16777215;
   private int colorProgress = 16777215;
   private GlBlendState blendState = null;
   private boolean fadeOut = false;

   public C_3565_(C_3391_ mcIn, C_74_ reloaderIn, Consumer<Optional<Throwable>> completedIn, boolean reloadingIn) {
      this.f_96163_ = mcIn;
      this.f_96164_ = reloaderIn;
      this.f_96165_ = completedIn;
      this.f_96166_ = false;
   }

   public static void m_96189_(C_3391_ mc) {
      mc.m_91097_().m_118495_(f_96160_, new C_3565_.C_3566_());
   }

   private static int m_169324_(int colorIn, int alphaIn) {
      return colorIn & 16777215 | alphaIn << 24;
   }

   public void m_88315_(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
      int i = graphicsIn.m_280182_();
      int j = graphicsIn.m_280206_();
      long k = C_5322_.m_137550_();
      if (this.f_96166_ && this.f_96169_ == -1L) {
         this.f_96169_ = k;
      }

      float f = this.f_96168_ > -1L ? (float)(k - this.f_96168_) / 1000.0F : -1.0F;
      float f1 = this.f_96169_ > -1L ? (float)(k - this.f_96169_) / 500.0F : -1.0F;
      float f2;
      if (f >= 1.0F) {
         this.fadeOut = true;
         if (this.f_96163_.f_91080_ != null) {
            this.f_96163_.f_91080_.m_88315_(graphicsIn, 0, 0, partialTicks);
         }

         int l = C_188_.m_14167_((1.0F - C_188_.m_14036_(f - 1.0F, 0.0F, 1.0F)) * 255.0F);
         graphicsIn.m_285944_(C_4168_.m_286086_(), 0, 0, i, j, m_169324_(this.colorBackground, l));
         f2 = 1.0F - C_188_.m_14036_(f - 1.0F, 0.0F, 1.0F);
      } else if (this.f_96166_) {
         if (this.f_96163_.f_91080_ != null && f1 < 1.0F) {
            this.f_96163_.f_91080_.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
         }

         int l1 = C_188_.m_14165_(C_188_.m_14008_((double)f1, 0.15, 1.0) * 255.0);
         graphicsIn.m_285944_(C_4168_.m_286086_(), 0, 0, i, j, m_169324_(this.colorBackground, l1));
         f2 = C_188_.m_14036_(f1, 0.0F, 1.0F);
      } else {
         int i2 = this.colorBackground;
         float f3 = (float)(i2 >> 16 & 0xFF) / 255.0F;
         float f4 = (float)(i2 >> 8 & 0xFF) / 255.0F;
         float f5 = (float)(i2 & 0xFF) / 255.0F;
         GlStateManager._clearColor(f3, f4, f5, 1.0F);
         GlStateManager._clear(16384, C_3391_.f_91002_);
         f2 = 1.0F;
      }

      int j2 = (int)((double)graphicsIn.m_280182_() * 0.5);
      int k2 = (int)((double)graphicsIn.m_280206_() * 0.5);
      double d1 = Math.min((double)graphicsIn.m_280182_() * 0.75, (double)graphicsIn.m_280206_()) * 0.25;
      int i1 = (int)(d1 * 0.5);
      double d0 = d1 * 4.0;
      int j1 = (int)(d0 * 0.5);
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(770, 1);
      graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, f2);
      boolean renderLogo = true;
      if (this.blendState != null) {
         this.blendState.apply();
         if (!this.blendState.isEnabled() && this.fadeOut) {
            renderLogo = false;
         }
      }

      if (renderLogo) {
         graphicsIn.m_280411_(f_96160_, j2 - j1, k2 - i1, j1, (int)d1, -0.0625F, 0.0F, 120, 60, 120, 120);
         graphicsIn.m_280411_(f_96160_, j2, k2 - i1, j1, (int)d1, 0.0625F, 60.0F, 120, 60, 120, 120);
      }

      graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      int k1 = (int)((double)graphicsIn.m_280206_() * 0.8325);
      float f6 = this.f_96164_.m_7750_();
      this.f_96167_ = C_188_.m_14036_(this.f_96167_ * 0.95F + f6 * 0.050000012F, 0.0F, 1.0F);
      if (f < 1.0F) {
         this.m_96182_(graphicsIn, i / 2 - j1, k1 - 5, i / 2 + j1, k1 + 5, 1.0F - C_188_.m_14036_(f, 0.0F, 1.0F));
      }

      if (f >= 2.0F) {
         this.f_96163_.m_91150_(null);
      }

      if (this.f_96168_ == -1L && this.f_96164_.m_7746_() && (!this.f_96166_ || f1 >= 2.0F)) {
         this.f_96168_ = C_5322_.m_137550_();

         try {
            this.f_96164_.m_7748_();
            this.f_96165_.accept(Optional.empty());
         } catch (Throwable var24) {
            this.f_96165_.accept(Optional.of(var24));
         }

         if (this.f_96163_.f_91080_ != null) {
            this.f_96163_.f_91080_.m_6575_(this.f_96163_, graphicsIn.m_280182_(), graphicsIn.m_280206_());
         }
      }
   }

   private void m_96182_(C_279497_ graphicsIn, int left, int top, int right, int bottom, float alpha) {
      int i = C_188_.m_14167_((float)(right - left - 2) * this.f_96167_);
      int j = Math.round(alpha * 255.0F);
      if (this.colorBar != this.colorBackground) {
         int colBgR = this.colorBar >> 16 & 0xFF;
         int colBgG = this.colorBar >> 8 & 0xFF;
         int colBgB = this.colorBar & 0xFF;
         int colBg = C_175_.m_13660_(j, colBgR, colBgG, colBgB);
         graphicsIn.m_280509_(left, top, right, bottom, colBg);
      }

      int colProgR = this.colorProgress >> 16 & 0xFF;
      int colProgG = this.colorProgress >> 8 & 0xFF;
      int colProgB = this.colorProgress & 0xFF;
      int k = C_175_.m_13660_(j, colProgR, colProgG, colProgB);
      graphicsIn.m_280509_(left + 2, top + 2, left + i, bottom - 2, k);
      int colOutR = this.colorOutline >> 16 & 0xFF;
      int colOutG = this.colorOutline >> 8 & 0xFF;
      int colOutB = this.colorOutline & 0xFF;
      k = C_175_.m_13660_(j, colOutR, colOutG, colOutB);
      graphicsIn.m_280509_(left + 1, top, right - 1, top + 1, k);
      graphicsIn.m_280509_(left + 1, bottom, right - 1, bottom - 1, k);
      graphicsIn.m_280509_(left, top, left + 1, bottom, k);
      graphicsIn.m_280509_(right, top, right - 1, bottom, k);
   }

   public boolean m_7859_() {
      return true;
   }

   public void update() {
      this.colorBackground = f_96161_.getAsInt();
      this.colorBar = f_96161_.getAsInt();
      this.colorOutline = 16777215;
      this.colorProgress = 16777215;
      if (Config.isCustomColors()) {
         try {
            String fileName = "optifine/color.properties";
            C_5265_ loc = new C_5265_(fileName);
            if (!Config.hasResource(loc)) {
               return;
            }

            InputStream in = Config.getResourceStream(loc);
            Config.dbg("Loading " + fileName);
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            this.colorBackground = readColor(props, "screen.loading", this.colorBackground);
            this.colorOutline = readColor(props, "screen.loading.outline", this.colorOutline);
            this.colorBar = readColor(props, "screen.loading.bar", this.colorBar);
            this.colorProgress = readColor(props, "screen.loading.progress", this.colorProgress);
            this.blendState = ShaderPackParser.parseBlendState(props.getProperty("screen.loading.blend"));
         } catch (Exception var5) {
            Config.warn(var5.getClass().getName() + ": " + var5.getMessage());
         }
      }
   }

   private static int readColor(Properties props, String name, int colDef) {
      String str = props.getProperty(name);
      if (str == null) {
         return colDef;
      } else {
         str = str.trim();
         int color = parseColor(str, colDef);
         if (color < 0) {
            Config.warn("Invalid color: " + name + " = " + str);
            return color;
         } else {
            Config.dbg(name + " = " + str);
            return color;
         }
      }
   }

   private static int parseColor(String str, int colDef) {
      if (str == null) {
         return colDef;
      } else {
         str = str.trim();

         try {
            return Integer.parseInt(str, 16) & 16777215;
         } catch (NumberFormatException var3) {
            return colDef;
         }
      }
   }

   public boolean isFadeOut() {
      return this.fadeOut;
   }

   public static String getGuiChatText(C_3538_ guiChat) {
      return guiChat.f_95573_.m_94155_();
   }

   static class C_3566_ extends C_4476_ {
      public C_3566_() {
         super(C_3565_.f_96160_);
      }

      @Override
      protected C_4476_.C_4477_ m_6335_(C_77_ resourceManager) {
         C_53_ vanillapackresources = C_3391_.m_91087_().m_246804_();
         C_243587_<InputStream> iosupplier = vanillapackresources.m_214146_(C_51_.CLIENT_RESOURCES, C_3565_.f_96160_);
         if (iosupplier == null) {
            return new C_4476_.C_4477_(new FileNotFoundException(C_3565_.f_96160_.toString()));
         } else {
            try {
               InputStream inputstream = getLogoInputStream(resourceManager, iosupplier);

               C_4476_.C_4477_ simpletexture$textureimage;
               try {
                  simpletexture$textureimage = new C_4476_.C_4477_(new C_4526_(true, true), C_3148_.m_85058_(inputstream));
               } catch (Throwable var9) {
                  if (inputstream != null) {
                     try {
                        inputstream.close();
                     } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                     }
                  }

                  throw var9;
               }

               if (inputstream != null) {
                  inputstream.close();
               }

               return simpletexture$textureimage;
            } catch (IOException var10) {
               return new C_4476_.C_4477_(var10);
            }
         }
      }

      private static InputStream getLogoInputStream(C_77_ resourceManager, C_243587_<InputStream> inputStream) throws IOException {
         return resourceManager.getResource(C_3565_.f_96160_).isPresent()
            ? ((C_76_)resourceManager.getResource(C_3565_.f_96160_).get()).m_215507_()
            : (InputStream)inputStream.m_247737_();
      }
   }
}
