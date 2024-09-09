import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import net.minecraft.src.C_243587_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3538_;
import net.minecraft.src.C_3573_;
import net.minecraft.src.C_4526_;
import net.minecraft.src.C_51_;
import net.minecraft.src.C_74_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.Config;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.config.ShaderPackParser;
import net.optifine.util.PropertiesOrdered;

public class LoadingOverlay extends C_3573_ {
   public static final ResourceLocation c = ResourceLocation.b("textures/gui/title/mojangstudios.png");
   private static final int d = C_175_.m_13660_(255, 239, 50, 61);
   private static final int e = C_175_.m_13660_(255, 0, 0, 0);
   private static final IntSupplier f = () -> C_3391_.m_91087_().m.a().c() ? e : d;
   private static final int g = 240;
   private static final float h = 60.0F;
   private static final int i = 60;
   private static final int j = 120;
   private static final float k = 0.0625F;
   private static final float l = 0.95F;
   public static final long a = 1000L;
   public static final long b = 500L;
   private final C_3391_ m;
   private final C_74_ n;
   private final Consumer<Optional<Throwable>> o;
   private final boolean p;
   private float q;
   private long r = -1L;
   private long s = -1L;
   private int colorBackground = f.getAsInt();
   private int colorBar = f.getAsInt();
   private int colorOutline = 16777215;
   private int colorProgress = 16777215;
   private GlBlendState blendState = null;
   private boolean fadeOut = false;

   public LoadingOverlay(C_3391_ mcIn, C_74_ reloaderIn, Consumer<Optional<Throwable>> completedIn, boolean reloadingIn) {
      this.m = mcIn;
      this.n = reloaderIn;
      this.o = completedIn;
      this.p = false;
   }

   public static void a(C_3391_ mc) {
      mc.aa().a(c, new LoadingOverlay.a());
   }

   private static int a(int colorIn, int alphaIn) {
      return colorIn & 16777215 | alphaIn << 24;
   }

   public void a(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      int i = graphicsIn.a();
      int j = graphicsIn.b();
      long k = Util.c();
      if (this.p && this.s == -1L) {
         this.s = k;
      }

      float f = this.r > -1L ? (float)(k - this.r) / 1000.0F : -1.0F;
      float f1 = this.s > -1L ? (float)(k - this.s) / 500.0F : -1.0F;
      float f2;
      if (f >= 1.0F) {
         this.fadeOut = true;
         if (this.m.f_91080_ != null) {
            this.m.f_91080_.a(graphicsIn, 0, 0, partialTicks);
         }

         int l = Mth.f((1.0F - Mth.a(f - 1.0F, 0.0F, 1.0F)) * 255.0F);
         graphicsIn.a(RenderType.F(), 0, 0, i, j, a(this.colorBackground, l));
         f2 = 1.0F - Mth.a(f - 1.0F, 0.0F, 1.0F);
      } else if (this.p) {
         if (this.m.f_91080_ != null && f1 < 1.0F) {
            this.m.f_91080_.a(graphicsIn, mouseX, mouseY, partialTicks);
         }

         int l1 = Mth.c(Mth.a((double)f1, 0.15, 1.0) * 255.0);
         graphicsIn.a(RenderType.F(), 0, 0, i, j, a(this.colorBackground, l1));
         f2 = Mth.a(f1, 0.0F, 1.0F);
      } else {
         int i2 = this.colorBackground;
         float f3 = (float)(i2 >> 16 & 0xFF) / 255.0F;
         float f4 = (float)(i2 >> 8 & 0xFF) / 255.0F;
         float f5 = (float)(i2 & 0xFF) / 255.0F;
         GlStateManager._clearColor(f3, f4, f5, 1.0F);
         GlStateManager._clear(16384, C_3391_.f_91002_);
         f2 = 1.0F;
      }

      int j2 = (int)((double)graphicsIn.a() * 0.5);
      int k2 = (int)((double)graphicsIn.b() * 0.5);
      double d1 = Math.min((double)graphicsIn.a() * 0.75, (double)graphicsIn.b()) * 0.25;
      int i1 = (int)(d1 * 0.5);
      double d0 = d1 * 4.0;
      int j1 = (int)(d0 * 0.5);
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(770, 1);
      graphicsIn.a(1.0F, 1.0F, 1.0F, f2);
      boolean renderLogo = true;
      if (this.blendState != null) {
         this.blendState.apply();
         if (!this.blendState.isEnabled() && this.fadeOut) {
            renderLogo = false;
         }
      }

      if (renderLogo) {
         graphicsIn.a(c, j2 - j1, k2 - i1, j1, (int)d1, -0.0625F, 0.0F, 120, 60, 120, 120);
         graphicsIn.a(c, j2, k2 - i1, j1, (int)d1, 0.0625F, 60.0F, 120, 60, 120, 120);
      }

      graphicsIn.a(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      int k1 = (int)((double)graphicsIn.b() * 0.8325);
      float f6 = this.n.m_7750_();
      this.q = Mth.a(this.q * 0.95F + f6 * 0.050000012F, 0.0F, 1.0F);
      if (f < 1.0F) {
         this.a(graphicsIn, i / 2 - j1, k1 - 5, i / 2 + j1, k1 + 5, 1.0F - Mth.a(f, 0.0F, 1.0F));
      }

      if (f >= 2.0F) {
         this.m.m_91150_(null);
      }

      if (this.r == -1L && this.n.m_7746_() && (!this.p || f1 >= 2.0F)) {
         this.r = Util.c();

         try {
            this.n.m_7748_();
            this.o.accept(Optional.empty());
         } catch (Throwable var24) {
            this.o.accept(Optional.of(var24));
         }

         if (this.m.f_91080_ != null) {
            this.m.f_91080_.m_6575_(this.m, graphicsIn.a(), graphicsIn.b());
         }
      }
   }

   private void a(GuiGraphics graphicsIn, int left, int top, int right, int bottom, float alpha) {
      int i = Mth.f((float)(right - left - 2) * this.q);
      int j = Math.round(alpha * 255.0F);
      if (this.colorBar != this.colorBackground) {
         int colBgR = this.colorBar >> 16 & 0xFF;
         int colBgG = this.colorBar >> 8 & 0xFF;
         int colBgB = this.colorBar & 0xFF;
         int colBg = C_175_.m_13660_(j, colBgR, colBgG, colBgB);
         graphicsIn.a(left, top, right, bottom, colBg);
      }

      int colProgR = this.colorProgress >> 16 & 0xFF;
      int colProgG = this.colorProgress >> 8 & 0xFF;
      int colProgB = this.colorProgress & 0xFF;
      int k = C_175_.m_13660_(j, colProgR, colProgG, colProgB);
      graphicsIn.a(left + 2, top + 2, left + i, bottom - 2, k);
      int colOutR = this.colorOutline >> 16 & 0xFF;
      int colOutG = this.colorOutline >> 8 & 0xFF;
      int colOutB = this.colorOutline & 0xFF;
      k = C_175_.m_13660_(j, colOutR, colOutG, colOutB);
      graphicsIn.a(left + 1, top, right - 1, top + 1, k);
      graphicsIn.a(left + 1, bottom, right - 1, bottom - 1, k);
      graphicsIn.a(left, top, left + 1, bottom, k);
      graphicsIn.a(right, top, right - 1, bottom, k);
   }

   public boolean m_7859_() {
      return true;
   }

   public void update() {
      this.colorBackground = f.getAsInt();
      this.colorBar = f.getAsInt();
      this.colorOutline = 16777215;
      this.colorProgress = 16777215;
      if (Config.isCustomColors()) {
         try {
            String fileName = "optifine/color.properties";
            ResourceLocation loc = new ResourceLocation(fileName);
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

   static class a extends SimpleTexture {
      public a() {
         super(LoadingOverlay.c);
      }

      @Override
      protected SimpleTexture.a b(C_77_ resourceManager) {
         VanillaPackResources vanillapackresources = C_3391_.m_91087_().ad();
         C_243587_<InputStream> iosupplier = vanillapackresources.a(C_51_.CLIENT_RESOURCES, LoadingOverlay.c);
         if (iosupplier == null) {
            return new SimpleTexture.a(new FileNotFoundException(LoadingOverlay.c.toString()));
         } else {
            try {
               InputStream inputstream = getLogoInputStream(resourceManager, iosupplier);

               SimpleTexture.a simpletexture$textureimage;
               try {
                  simpletexture$textureimage = new SimpleTexture.a(new C_4526_(true, true), NativeImage.a(inputstream));
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
               return new SimpleTexture.a(var10);
            }
         }
      }

      private static InputStream getLogoInputStream(C_77_ resourceManager, C_243587_<InputStream> inputStream) throws IOException {
         return resourceManager.getResource(LoadingOverlay.c).isPresent()
            ? ((C_76_)resourceManager.getResource(LoadingOverlay.c).get()).m_215507_()
            : (InputStream)inputStream.m_247737_();
      }
   }
}
