package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager$C_3128_;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import org.apache.commons.lang3.tuple.Triple;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

public abstract class C_4149_ {
   private static final float f_173089_ = 0.99975586F;
   public static final double f_267492_ = 8.0;
   protected final String f_110133_;
   private final Runnable f_110131_;
   private final Runnable f_110132_;
   protected static final C_4166_ f_110134_ = new C_4166_("no_transparency", () -> {
      RenderSystem.disableBlend();
   }, () -> {
   });
   protected static final C_4166_ f_110135_ = new C_4166_("additive_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final C_4166_ f_110136_ = new C_4166_("lightning_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final C_4166_ f_110137_ = new C_4166_("glint_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final C_4166_ f_110138_ = new C_4166_("crumbling_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final C_4166_ f_110139_ = new C_4166_("translucent_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final C_141720_ f_173096_ = new C_141720_();
   protected static final C_141720_ f_173099_ = new C_141720_(C_4124_::m_172832_);
   protected static final C_141720_ f_173100_ = new C_141720_(C_4124_::m_172808_);
   protected static final C_141720_ f_173102_ = new C_141720_(C_4124_::m_172817_);
   protected static final C_141720_ f_173103_ = new C_141720_(C_4124_::m_172835_);
   protected static final C_141720_ f_173104_ = new C_141720_(C_4124_::m_172811_);
   protected static final C_141720_ f_173105_ = new C_141720_(C_4124_::m_172640_);
   protected static final C_141720_ f_173106_ = new C_141720_(C_4124_::m_172643_);
   protected static final C_141720_ f_173107_ = new C_141720_(C_4124_::m_172646_);
   protected static final C_141720_ f_173108_ = new C_141720_(C_4124_::m_172649_);
   protected static final C_141720_ f_173109_ = new C_141720_(C_4124_::m_172652_);
   protected static final C_141720_ f_173111_ = new C_141720_(C_4124_::m_172658_);
   protected static final C_141720_ f_173112_ = new C_141720_(C_4124_::m_172661_);
   protected static final C_141720_ f_173113_ = new C_141720_(C_4124_::m_172664_);
   protected static final C_141720_ f_173114_ = new C_141720_(C_4124_::m_172667_);
   protected static final C_141720_ f_173063_ = new C_141720_(C_4124_::m_172670_);
   protected static final C_141720_ f_173064_ = new C_141720_(C_4124_::m_172673_);
   protected static final C_141720_ f_173065_ = new C_141720_(C_4124_::m_172676_);
   protected static final C_141720_ f_173066_ = new C_141720_(C_4124_::m_172679_);
   protected static final C_141720_ f_234323_ = new C_141720_(C_4124_::m_234223_);
   protected static final C_141720_ f_173067_ = new C_141720_(C_4124_::m_172682_);
   protected static final C_141720_ f_173068_ = new C_141720_(C_4124_::m_172685_);
   protected static final C_141720_ f_173069_ = new C_141720_(C_4124_::m_172688_);
   protected static final C_141720_ f_173070_ = new C_141720_(C_4124_::m_172691_);
   protected static final C_141720_ f_173071_ = new C_141720_(C_4124_::m_172694_);
   protected static final C_141720_ f_173072_ = new C_141720_(C_4124_::m_172697_);
   protected static final C_141720_ f_173073_ = new C_141720_(C_4124_::m_172700_);
   protected static final C_141720_ f_173074_ = new C_141720_(C_4124_::m_172703_);
   protected static final C_141720_ f_173075_ = new C_141720_(C_4124_::m_172706_);
   protected static final C_141720_ f_173076_ = new C_141720_(C_4124_::m_172709_);
   protected static final C_141720_ f_173077_ = new C_141720_(C_4124_::m_172712_);
   protected static final C_141720_ f_173079_ = new C_141720_(C_4124_::m_172741_);
   protected static final C_141720_ f_173080_ = new C_141720_(C_4124_::m_172744_);
   protected static final C_141720_ f_173081_ = new C_141720_(C_4124_::m_172745_);
   protected static final C_141720_ f_173083_ = new C_141720_(C_4124_::m_172747_);
   protected static final C_141720_ f_173084_ = new C_141720_(C_4124_::m_172748_);
   protected static final C_141720_ f_173085_ = new C_141720_(C_4124_::m_172758_);
   protected static final C_141720_ f_173086_ = new C_141720_(C_4124_::m_172749_);
   protected static final C_141720_ f_268568_ = new C_141720_(C_4124_::m_269563_);
   protected static final C_141720_ f_173087_ = new C_141720_(C_4124_::m_172750_);
   protected static final C_141720_ f_173088_ = new C_141720_(C_4124_::m_172751_);
   protected static final C_141720_ f_268491_ = new C_141720_(C_4124_::m_269511_);
   protected static final C_141720_ f_173090_ = new C_141720_(C_4124_::m_172752_);
   protected static final C_141720_ f_173091_ = new C_141720_(C_4124_::m_172753_);
   protected static final C_141720_ f_173092_ = new C_141720_(C_4124_::m_172754_);
   protected static final C_141720_ f_173093_ = new C_141720_(C_4124_::m_172755_);
   protected static final C_141720_ f_173094_ = new C_141720_(C_4124_::m_172756_);
   protected static final C_141720_ f_316212_ = new C_141720_(C_4124_::m_320329_);
   protected static final C_141720_ f_173095_ = new C_141720_(C_4124_::m_172757_);
   protected static final C_141720_ f_285573_ = new C_141720_(C_4124_::m_285858_);
   protected static final C_141720_ f_285619_ = new C_141720_(C_4124_::m_285975_);
   protected static final C_141720_ f_285642_ = new C_141720_(C_4124_::m_285738_);
   protected static final C_141720_ f_285582_ = new C_141720_(C_4124_::m_285862_);
   protected static final C_141720_ f_303180_ = new C_141720_(C_4124_::m_307576_);
   protected static final C_4164_ f_110145_;
   protected static final C_4164_ f_110146_;
   protected static final C_141717_ f_110147_;
   protected static final C_4165_ f_110148_;
   protected static final C_4165_ f_110150_;
   protected static final C_4165_ f_110151_;
   protected static final C_4157_ f_110152_;
   protected static final C_4157_ f_110153_;
   protected static final C_4161_ f_110154_;
   protected static final C_4161_ f_110155_;
   protected static final C_4152_ f_110158_;
   protected static final C_4152_ f_110110_;
   protected static final C_4153_ f_110111_;
   protected static final C_4153_ f_110112_;
   protected static final C_4153_ f_110113_;
   protected static final C_4153_ f_285579_;
   protected static final C_4167_ f_110114_;
   protected static final C_4167_ f_110115_;
   protected static final C_4167_ f_110116_;
   protected static final C_4156_ f_110117_;
   protected static final C_4156_ f_110118_;
   protected static final C_4156_ f_110119_;
   protected static final C_4160_ f_110123_;
   protected static final C_4160_ f_110124_;
   protected static final C_4160_ f_110125_;
   protected static final C_4160_ f_110126_;
   protected static final C_4160_ f_110127_;
   protected static final C_4160_ f_110128_;
   protected static final C_4160_ f_110129_;
   protected static final C_4158_ f_110130_;
   protected static final C_285538_ f_285585_;
   protected static final C_285538_ f_285603_;

   public C_4149_(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
      this.f_110133_ = nameIn;
      this.f_110131_ = setupTaskIn;
      this.f_110132_ = clearTaskIn;
   }

   public void m_110185_() {
      this.f_110131_.run();
   }

   public void m_110188_() {
      this.f_110132_.run();
   }

   public String toString() {
      return this.f_110133_;
   }

   private static void m_110186_(float scaleIn) {
      long i = (long)((double)C_5322_.m_137550_() * (Double)C_3391_.m_91087_().f_91066_.m_267805_().m_231551_() * 8.0);
      float f = (float)(i % 110000L) / 110000.0F;
      float f1 = (float)(i % 30000L) / 30000.0F;
      Matrix4f matrix4f = (new Matrix4f()).translation(-f, f1, 0.0F);
      matrix4f.rotateZ(0.17453292F).scale(scaleIn);
      RenderSystem.setTextureMatrix(matrix4f);
   }

   public String getName() {
      return this.f_110133_;
   }

   static {
      f_110145_ = new C_4164_(C_4484_.f_118259_, false, true);
      f_110146_ = new C_4164_(C_4484_.f_118259_, false, false);
      f_110147_ = new C_141717_();
      f_110148_ = new C_4165_("default_texturing", () -> {
      }, () -> {
      });
      f_110150_ = new C_4165_("glint_texturing", () -> {
         m_110186_(8.0F);
      }, () -> {
         RenderSystem.resetTextureMatrix();
      });
      f_110151_ = new C_4165_("entity_glint_texturing", () -> {
         m_110186_(0.16F);
      }, () -> {
         RenderSystem.resetTextureMatrix();
      });
      f_110152_ = new C_4157_(true);
      f_110153_ = new C_4157_(false);
      f_110154_ = new C_4161_(true);
      f_110155_ = new C_4161_(false);
      f_110158_ = new C_4152_(true);
      f_110110_ = new C_4152_(false);
      f_110111_ = new C_4153_("always", 519);
      f_110112_ = new C_4153_("==", 514);
      f_110113_ = new C_4153_("<=", 515);
      f_285579_ = new C_4153_(">", 516);
      f_110114_ = new C_4167_(true, true);
      f_110115_ = new C_4167_(true, false);
      f_110116_ = new C_4167_(false, true);
      f_110117_ = new C_4156_("no_layering", () -> {
      }, () -> {
      });
      f_110118_ = new C_4156_("polygon_offset_layering", () -> {
         RenderSystem.polygonOffset(-1.0F, -10.0F);
         RenderSystem.enablePolygonOffset();
      }, () -> {
         RenderSystem.polygonOffset(0.0F, 0.0F);
         RenderSystem.disablePolygonOffset();
      });
      f_110119_ = new C_4156_("view_offset_z_layering", () -> {
         Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
         matrix4fstack.pushMatrix();
         matrix4fstack.scale(0.99975586F, 0.99975586F, 0.99975586F);
         RenderSystem.applyModelViewMatrix();
      }, () -> {
         Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
         matrix4fstack.popMatrix();
         RenderSystem.applyModelViewMatrix();
      });
      f_110123_ = new C_4160_("main_target", () -> {
      }, () -> {
      });
      f_110124_ = new C_4160_("outline_target", () -> {
         C_3391_.m_91087_().f_91060_.m_109827_().m_83947_(false);
      }, () -> {
         C_3391_.m_91087_().m_91385_().m_83947_(false);
      });
      f_110125_ = new C_4160_("translucent_target", () -> {
         if (C_3391_.m_91085_()) {
            C_3391_.m_91087_().f_91060_.m_109828_().m_83947_(false);
         }

      }, () -> {
         if (C_3391_.m_91085_()) {
            C_3391_.m_91087_().m_91385_().m_83947_(false);
         }

      });
      f_110126_ = new C_4160_("particles_target", () -> {
         if (C_3391_.m_91085_()) {
            C_3391_.m_91087_().f_91060_.m_109830_().m_83947_(false);
         }

      }, () -> {
         if (C_3391_.m_91085_()) {
            C_3391_.m_91087_().m_91385_().m_83947_(false);
         }

      });
      f_110127_ = new C_4160_("weather_target", () -> {
         if (C_3391_.m_91085_()) {
            C_3391_.m_91087_().f_91060_.m_109831_().m_83947_(false);
         }

      }, () -> {
         if (C_3391_.m_91085_()) {
            C_3391_.m_91087_().m_91385_().m_83947_(false);
         }

      });
      f_110128_ = new C_4160_("clouds_target", () -> {
         if (C_3391_.m_91085_()) {
            C_3391_.m_91087_().f_91060_.m_109832_().m_83947_(false);
         }

      }, () -> {
         if (C_3391_.m_91085_()) {
            C_3391_.m_91087_().m_91385_().m_83947_(false);
         }

      });
      f_110129_ = new C_4160_("item_entity_target", () -> {
         if (C_3391_.m_91085_()) {
            C_3391_.m_91087_().f_91060_.m_109829_().m_83947_(false);
         }

      }, () -> {
         if (C_3391_.m_91085_()) {
            C_3391_.m_91087_().m_91385_().m_83947_(false);
         }

      });
      f_110130_ = new C_4158_(OptionalDouble.of(1.0));
      f_285585_ = new C_285538_("no_color_logic", () -> {
         RenderSystem.disableColorLogicOp();
      }, () -> {
      });
      f_285603_ = new C_285538_("or_reverse", () -> {
         RenderSystem.enableColorLogicOp();
         RenderSystem.logicOp(GlStateManager$C_3128_.OR_REVERSE);
      }, () -> {
         RenderSystem.disableColorLogicOp();
      });
   }

   protected static class C_4166_ extends C_4149_ {
      public C_4166_(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class C_141720_ extends C_4149_ {
      private final Optional f_173136_;

      public C_141720_(Supplier shaderIn) {
         super("shader", () -> {
            RenderSystem.setShader(shaderIn);
         }, () -> {
         });
         this.f_173136_ = Optional.of(shaderIn);
      }

      public C_141720_() {
         super("shader", () -> {
            RenderSystem.setShader(() -> {
               return null;
            });
         }, () -> {
         });
         this.f_173136_ = Optional.empty();
      }

      public String toString() {
         String var10000 = this.f_110133_;
         return var10000 + "[" + String.valueOf(this.f_173136_) + "]";
      }
   }

   protected static class C_4164_ extends C_141717_ {
      private final Optional f_110328_;
      private final boolean f_110329_;
      private final boolean f_110330_;

      public C_4164_(C_5265_ locationIn, boolean blurIn, boolean mipmapIn) {
         super(() -> {
            C_4490_ texturemanager = C_3391_.m_91087_().m_91097_();
            texturemanager.m_118506_(locationIn).m_117960_(blurIn, mipmapIn);
            RenderSystem.setShaderTexture(0, locationIn);
         }, () -> {
         });
         this.f_110328_ = Optional.of(locationIn);
         this.f_110329_ = blurIn;
         this.f_110330_ = mipmapIn;
      }

      public String toString() {
         String var10000 = this.f_110133_;
         return var10000 + "[" + String.valueOf(this.f_110328_) + "(blur=" + this.f_110329_ + ", mipmap=" + this.f_110330_ + ")]";
      }

      protected Optional m_142706_() {
         return this.f_110328_;
      }

      public boolean isBlur() {
         return this.f_110329_;
      }

      public boolean isMipmap() {
         return this.f_110330_;
      }
   }

   protected static class C_141717_ extends C_4149_ {
      public C_141717_(Runnable setupTaskIn, Runnable clearTaskIn) {
         super("texture", setupTaskIn, clearTaskIn);
      }

      C_141717_() {
         super("texture", () -> {
         }, () -> {
         });
      }

      protected Optional m_142706_() {
         return Optional.empty();
      }

      public boolean isBlur() {
         return false;
      }

      public boolean isMipmap() {
         return false;
      }
   }

   protected static class C_4165_ extends C_4149_ {
      public C_4165_(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class C_4157_ extends C_4151_ {
      public C_4157_(boolean enabledIn) {
         super("lightmap", () -> {
            if (enabledIn) {
               C_3391_.m_91087_().f_91063_.m_109154_().m_109896_();
            }

         }, () -> {
            if (enabledIn) {
               C_3391_.m_91087_().f_91063_.m_109154_().m_109891_();
            }

         }, enabledIn);
      }
   }

   protected static class C_4161_ extends C_4151_ {
      public C_4161_(boolean enabledIn) {
         super("overlay", () -> {
            if (enabledIn) {
               C_3391_.m_91087_().f_91063_.m_109155_().m_118087_();
            }

         }, () -> {
            if (enabledIn) {
               C_3391_.m_91087_().f_91063_.m_109155_().m_118098_();
            }

         }, enabledIn);
      }
   }

   protected static class C_4152_ extends C_4151_ {
      public C_4152_(boolean enabledIn) {
         super("cull", () -> {
            if (!enabledIn) {
               RenderSystem.disableCull();
            }

         }, () -> {
            if (!enabledIn) {
               RenderSystem.enableCull();
            }

         }, enabledIn);
      }
   }

   protected static class C_4153_ extends C_4149_ {
      private final String f_110243_;

      public C_4153_(String funcNameIn, int funcIn) {
         super("depth_test", () -> {
            if (funcIn != 519) {
               RenderSystem.enableDepthTest();
               RenderSystem.depthFunc(funcIn);
            }

         }, () -> {
            if (funcIn != 519) {
               RenderSystem.disableDepthTest();
               RenderSystem.depthFunc(515);
            }

         });
         this.f_110243_ = funcNameIn;
      }

      public String toString() {
         return this.f_110133_ + "[" + this.f_110243_ + "]";
      }
   }

   protected static class C_4167_ extends C_4149_ {
      private final boolean f_110356_;
      private final boolean f_110357_;

      public C_4167_(boolean colorMaskIn, boolean depthMaskIn) {
         super("write_mask_state", () -> {
            if (!depthMaskIn) {
               RenderSystem.depthMask(depthMaskIn);
            }

            if (!colorMaskIn) {
               RenderSystem.colorMask(colorMaskIn, colorMaskIn, colorMaskIn, colorMaskIn);
            }

         }, () -> {
            if (!depthMaskIn) {
               RenderSystem.depthMask(true);
            }

            if (!colorMaskIn) {
               RenderSystem.colorMask(true, true, true, true);
            }

         });
         this.f_110356_ = colorMaskIn;
         this.f_110357_ = depthMaskIn;
      }

      public String toString() {
         return this.f_110133_ + "[writeColor=" + this.f_110356_ + ", writeDepth=" + this.f_110357_ + "]";
      }
   }

   protected static class C_4156_ extends C_4149_ {
      public C_4156_(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class C_4160_ extends C_4149_ {
      public C_4160_(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class C_4158_ extends C_4149_ {
      private final OptionalDouble f_110276_;

      public C_4158_(OptionalDouble widthIn) {
         super("line_width", () -> {
            if (!Objects.equals(widthIn, OptionalDouble.of(1.0))) {
               if (widthIn.isPresent()) {
                  RenderSystem.lineWidth((float)widthIn.getAsDouble());
               } else {
                  RenderSystem.lineWidth(Math.max(2.5F, (float)C_3391_.m_91087_().m_91268_().m_85441_() / 1920.0F * 2.5F));
               }
            }

         }, () -> {
            if (!Objects.equals(widthIn, OptionalDouble.of(1.0))) {
               RenderSystem.lineWidth(1.0F);
            }

         });
         this.f_110276_ = widthIn;
      }

      public String toString() {
         String var10000 = this.f_110133_;
         return var10000 + "[" + String.valueOf(this.f_110276_.isPresent() ? this.f_110276_.getAsDouble() : "window_scale") + "]";
      }
   }

   protected static class C_285538_ extends C_4149_ {
      public C_285538_(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static final class C_4159_ extends C_4165_ {
      public C_4159_(float offsetUIn, float offsetVIn) {
         super("offset_texturing", () -> {
            RenderSystem.setTextureMatrix((new Matrix4f()).translation(offsetUIn, offsetVIn, 0.0F));
         }, () -> {
            RenderSystem.resetTextureMatrix();
         });
      }
   }

   protected static class C_141718_ extends C_141717_ {
      private final Optional f_173121_;

      C_141718_(ImmutableList triplesIn) {
         super(() -> {
            int i = 0;
            UnmodifiableIterator var2 = triplesIn.iterator();

            while(var2.hasNext()) {
               Triple triple = (Triple)var2.next();
               C_4490_ texturemanager = C_3391_.m_91087_().m_91097_();
               texturemanager.m_118506_((C_5265_)triple.getLeft()).m_117960_((Boolean)triple.getMiddle(), (Boolean)triple.getRight());
               RenderSystem.setShaderTexture(i++, (C_5265_)triple.getLeft());
            }

         }, () -> {
         });
         this.f_173121_ = triplesIn.stream().findFirst().map(Triple::getLeft);
      }

      protected Optional m_142706_() {
         return this.f_173121_;
      }

      public static C_141719_ m_173127_() {
         return new C_141719_();
      }

      public static final class C_141719_ {
         private final ImmutableList.Builder f_173129_ = new ImmutableList.Builder();

         public C_141719_ m_173132_(C_5265_ locationIn, boolean blurIn, boolean mipmapIn) {
            this.f_173129_.add(Triple.of(locationIn, blurIn, mipmapIn));
            return this;
         }

         public C_141718_ m_173131_() {
            return new C_141718_(this.f_173129_.build());
         }
      }
   }

   static class C_4151_ extends C_4149_ {
      private final boolean f_110227_;

      public C_4151_(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn, boolean enabledIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
         this.f_110227_ = enabledIn;
      }

      public String toString() {
         return this.f_110133_ + "[" + this.f_110227_ + "]";
      }
   }
}
