package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Triple;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

public abstract class RenderStateShard {
   private static final float f_173089_ = 0.99975586F;
   public static final double f_267492_ = 8.0;
   protected final String f_110133_;
   private final Runnable f_110131_;
   private final Runnable f_110132_;
   protected static final TransparencyStateShard f_110134_ = new TransparencyStateShard("no_transparency", () -> {
      RenderSystem.disableBlend();
   }, () -> {
   });
   protected static final TransparencyStateShard f_110135_ = new TransparencyStateShard("additive_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final TransparencyStateShard f_110136_ = new TransparencyStateShard("lightning_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final TransparencyStateShard f_110137_ = new TransparencyStateShard("glint_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final TransparencyStateShard f_110138_ = new TransparencyStateShard("crumbling_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final TransparencyStateShard f_110139_ = new TransparencyStateShard("translucent_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final ShaderStateShard f_173096_ = new ShaderStateShard();
   protected static final ShaderStateShard f_173099_ = new ShaderStateShard(GameRenderer::m_172832_);
   protected static final ShaderStateShard f_173100_ = new ShaderStateShard(GameRenderer::m_172808_);
   protected static final ShaderStateShard f_173102_ = new ShaderStateShard(GameRenderer::m_172817_);
   protected static final ShaderStateShard f_173103_ = new ShaderStateShard(GameRenderer::m_172835_);
   protected static final ShaderStateShard f_173104_ = new ShaderStateShard(GameRenderer::m_172811_);
   protected static final ShaderStateShard f_173105_ = new ShaderStateShard(GameRenderer::m_172640_);
   protected static final ShaderStateShard f_173106_ = new ShaderStateShard(GameRenderer::m_172643_);
   protected static final ShaderStateShard f_173107_ = new ShaderStateShard(GameRenderer::m_172646_);
   protected static final ShaderStateShard f_173108_ = new ShaderStateShard(GameRenderer::m_172649_);
   protected static final ShaderStateShard f_173109_ = new ShaderStateShard(GameRenderer::m_172652_);
   protected static final ShaderStateShard f_173111_ = new ShaderStateShard(GameRenderer::m_172658_);
   protected static final ShaderStateShard f_173112_ = new ShaderStateShard(GameRenderer::m_172661_);
   protected static final ShaderStateShard f_173113_ = new ShaderStateShard(GameRenderer::m_172664_);
   protected static final ShaderStateShard f_173114_ = new ShaderStateShard(GameRenderer::m_172667_);
   protected static final ShaderStateShard f_173063_ = new ShaderStateShard(GameRenderer::m_172670_);
   protected static final ShaderStateShard f_173064_ = new ShaderStateShard(GameRenderer::m_172673_);
   protected static final ShaderStateShard f_173065_ = new ShaderStateShard(GameRenderer::m_172676_);
   protected static final ShaderStateShard f_173066_ = new ShaderStateShard(GameRenderer::m_172679_);
   protected static final ShaderStateShard f_234323_ = new ShaderStateShard(GameRenderer::m_234223_);
   protected static final ShaderStateShard f_173067_ = new ShaderStateShard(GameRenderer::m_172682_);
   protected static final ShaderStateShard f_173068_ = new ShaderStateShard(GameRenderer::m_172685_);
   protected static final ShaderStateShard f_173069_ = new ShaderStateShard(GameRenderer::m_172688_);
   protected static final ShaderStateShard f_173070_ = new ShaderStateShard(GameRenderer::m_172691_);
   protected static final ShaderStateShard f_173071_ = new ShaderStateShard(GameRenderer::m_172694_);
   protected static final ShaderStateShard f_173072_ = new ShaderStateShard(GameRenderer::m_172697_);
   protected static final ShaderStateShard f_173073_ = new ShaderStateShard(GameRenderer::m_172700_);
   protected static final ShaderStateShard f_173074_ = new ShaderStateShard(GameRenderer::m_172703_);
   protected static final ShaderStateShard f_173075_ = new ShaderStateShard(GameRenderer::m_172706_);
   protected static final ShaderStateShard f_173076_ = new ShaderStateShard(GameRenderer::m_172709_);
   protected static final ShaderStateShard f_173077_ = new ShaderStateShard(GameRenderer::m_172712_);
   protected static final ShaderStateShard f_173079_ = new ShaderStateShard(GameRenderer::m_172741_);
   protected static final ShaderStateShard f_173080_ = new ShaderStateShard(GameRenderer::m_172744_);
   protected static final ShaderStateShard f_173081_ = new ShaderStateShard(GameRenderer::m_172745_);
   protected static final ShaderStateShard f_173083_ = new ShaderStateShard(GameRenderer::m_172747_);
   protected static final ShaderStateShard f_173084_ = new ShaderStateShard(GameRenderer::m_172748_);
   protected static final ShaderStateShard f_173085_ = new ShaderStateShard(GameRenderer::m_172758_);
   protected static final ShaderStateShard f_173086_ = new ShaderStateShard(GameRenderer::m_172749_);
   protected static final ShaderStateShard f_268568_ = new ShaderStateShard(GameRenderer::m_269563_);
   protected static final ShaderStateShard f_173087_ = new ShaderStateShard(GameRenderer::m_172750_);
   protected static final ShaderStateShard f_173088_ = new ShaderStateShard(GameRenderer::m_172751_);
   protected static final ShaderStateShard f_268491_ = new ShaderStateShard(GameRenderer::m_269511_);
   protected static final ShaderStateShard f_173090_ = new ShaderStateShard(GameRenderer::m_172752_);
   protected static final ShaderStateShard f_173091_ = new ShaderStateShard(GameRenderer::m_172753_);
   protected static final ShaderStateShard f_173092_ = new ShaderStateShard(GameRenderer::m_172754_);
   protected static final ShaderStateShard f_173093_ = new ShaderStateShard(GameRenderer::m_172755_);
   protected static final ShaderStateShard f_173094_ = new ShaderStateShard(GameRenderer::m_172756_);
   protected static final ShaderStateShard f_316212_ = new ShaderStateShard(GameRenderer::m_320329_);
   protected static final ShaderStateShard f_173095_ = new ShaderStateShard(GameRenderer::m_172757_);
   protected static final ShaderStateShard f_285573_ = new ShaderStateShard(GameRenderer::m_285858_);
   protected static final ShaderStateShard f_285619_ = new ShaderStateShard(GameRenderer::m_285975_);
   protected static final ShaderStateShard f_285642_ = new ShaderStateShard(GameRenderer::m_285738_);
   protected static final ShaderStateShard f_285582_ = new ShaderStateShard(GameRenderer::m_285862_);
   protected static final ShaderStateShard f_303180_ = new ShaderStateShard(GameRenderer::m_307576_);
   protected static final TextureStateShard f_110145_;
   protected static final TextureStateShard f_110146_;
   protected static final EmptyTextureStateShard f_110147_;
   protected static final TexturingStateShard f_110148_;
   protected static final TexturingStateShard f_110150_;
   protected static final TexturingStateShard f_110151_;
   protected static final LightmapStateShard f_110152_;
   protected static final LightmapStateShard f_110153_;
   protected static final OverlayStateShard f_110154_;
   protected static final OverlayStateShard f_110155_;
   protected static final CullStateShard f_110158_;
   protected static final CullStateShard f_110110_;
   protected static final DepthTestStateShard f_110111_;
   protected static final DepthTestStateShard f_110112_;
   protected static final DepthTestStateShard f_110113_;
   protected static final DepthTestStateShard f_285579_;
   protected static final WriteMaskStateShard f_110114_;
   protected static final WriteMaskStateShard f_110115_;
   protected static final WriteMaskStateShard f_110116_;
   protected static final LayeringStateShard f_110117_;
   protected static final LayeringStateShard f_110118_;
   protected static final LayeringStateShard f_110119_;
   protected static final OutputStateShard f_110123_;
   protected static final OutputStateShard f_110124_;
   protected static final OutputStateShard f_110125_;
   protected static final OutputStateShard f_110126_;
   protected static final OutputStateShard f_110127_;
   protected static final OutputStateShard f_110128_;
   protected static final OutputStateShard f_110129_;
   protected static final LineStateShard f_110130_;
   protected static final ColorLogicStateShard f_285585_;
   protected static final ColorLogicStateShard f_285603_;

   public RenderStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
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
      long i = (long)((double)Util.m_137550_() * (Double)Minecraft.m_91087_().f_91066_.m_267805_().m_231551_() * 8.0);
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
      f_110145_ = new TextureStateShard(TextureAtlas.f_118259_, false, true);
      f_110146_ = new TextureStateShard(TextureAtlas.f_118259_, false, false);
      f_110147_ = new EmptyTextureStateShard();
      f_110148_ = new TexturingStateShard("default_texturing", () -> {
      }, () -> {
      });
      f_110150_ = new TexturingStateShard("glint_texturing", () -> {
         m_110186_(8.0F);
      }, () -> {
         RenderSystem.resetTextureMatrix();
      });
      f_110151_ = new TexturingStateShard("entity_glint_texturing", () -> {
         m_110186_(0.16F);
      }, () -> {
         RenderSystem.resetTextureMatrix();
      });
      f_110152_ = new LightmapStateShard(true);
      f_110153_ = new LightmapStateShard(false);
      f_110154_ = new OverlayStateShard(true);
      f_110155_ = new OverlayStateShard(false);
      f_110158_ = new CullStateShard(true);
      f_110110_ = new CullStateShard(false);
      f_110111_ = new DepthTestStateShard("always", 519);
      f_110112_ = new DepthTestStateShard("==", 514);
      f_110113_ = new DepthTestStateShard("<=", 515);
      f_285579_ = new DepthTestStateShard(">", 516);
      f_110114_ = new WriteMaskStateShard(true, true);
      f_110115_ = new WriteMaskStateShard(true, false);
      f_110116_ = new WriteMaskStateShard(false, true);
      f_110117_ = new LayeringStateShard("no_layering", () -> {
      }, () -> {
      });
      f_110118_ = new LayeringStateShard("polygon_offset_layering", () -> {
         RenderSystem.polygonOffset(-1.0F, -10.0F);
         RenderSystem.enablePolygonOffset();
      }, () -> {
         RenderSystem.polygonOffset(0.0F, 0.0F);
         RenderSystem.disablePolygonOffset();
      });
      f_110119_ = new LayeringStateShard("view_offset_z_layering", () -> {
         Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
         matrix4fstack.pushMatrix();
         matrix4fstack.scale(0.99975586F, 0.99975586F, 0.99975586F);
         RenderSystem.applyModelViewMatrix();
      }, () -> {
         Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
         matrix4fstack.popMatrix();
         RenderSystem.applyModelViewMatrix();
      });
      f_110123_ = new OutputStateShard("main_target", () -> {
      }, () -> {
      });
      f_110124_ = new OutputStateShard("outline_target", () -> {
         Minecraft.m_91087_().f_91060_.m_109827_().m_83947_(false);
      }, () -> {
         Minecraft.m_91087_().m_91385_().m_83947_(false);
      });
      f_110125_ = new OutputStateShard("translucent_target", () -> {
         if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().f_91060_.m_109828_().m_83947_(false);
         }

      }, () -> {
         if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().m_91385_().m_83947_(false);
         }

      });
      f_110126_ = new OutputStateShard("particles_target", () -> {
         if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().f_91060_.m_109830_().m_83947_(false);
         }

      }, () -> {
         if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().m_91385_().m_83947_(false);
         }

      });
      f_110127_ = new OutputStateShard("weather_target", () -> {
         if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().f_91060_.m_109831_().m_83947_(false);
         }

      }, () -> {
         if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().m_91385_().m_83947_(false);
         }

      });
      f_110128_ = new OutputStateShard("clouds_target", () -> {
         if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().f_91060_.m_109832_().m_83947_(false);
         }

      }, () -> {
         if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().m_91385_().m_83947_(false);
         }

      });
      f_110129_ = new OutputStateShard("item_entity_target", () -> {
         if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().f_91060_.m_109829_().m_83947_(false);
         }

      }, () -> {
         if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().m_91385_().m_83947_(false);
         }

      });
      f_110130_ = new LineStateShard(OptionalDouble.of(1.0));
      f_285585_ = new ColorLogicStateShard("no_color_logic", () -> {
         RenderSystem.disableColorLogicOp();
      }, () -> {
      });
      f_285603_ = new ColorLogicStateShard("or_reverse", () -> {
         RenderSystem.enableColorLogicOp();
         RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
      }, () -> {
         RenderSystem.disableColorLogicOp();
      });
   }

   protected static class TransparencyStateShard extends RenderStateShard {
      public TransparencyStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class ShaderStateShard extends RenderStateShard {
      private final Optional f_173136_;

      public ShaderStateShard(Supplier shaderIn) {
         super("shader", () -> {
            RenderSystem.setShader(shaderIn);
         }, () -> {
         });
         this.f_173136_ = Optional.of(shaderIn);
      }

      public ShaderStateShard() {
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

   protected static class TextureStateShard extends EmptyTextureStateShard {
      private final Optional f_110328_;
      private final boolean f_110329_;
      private final boolean f_110330_;

      public TextureStateShard(ResourceLocation locationIn, boolean blurIn, boolean mipmapIn) {
         super(() -> {
            TextureManager texturemanager = Minecraft.m_91087_().m_91097_();
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

   protected static class EmptyTextureStateShard extends RenderStateShard {
      public EmptyTextureStateShard(Runnable setupTaskIn, Runnable clearTaskIn) {
         super("texture", setupTaskIn, clearTaskIn);
      }

      EmptyTextureStateShard() {
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

   protected static class TexturingStateShard extends RenderStateShard {
      public TexturingStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class LightmapStateShard extends BooleanStateShard {
      public LightmapStateShard(boolean enabledIn) {
         super("lightmap", () -> {
            if (enabledIn) {
               Minecraft.m_91087_().f_91063_.m_109154_().m_109896_();
            }

         }, () -> {
            if (enabledIn) {
               Minecraft.m_91087_().f_91063_.m_109154_().m_109891_();
            }

         }, enabledIn);
      }
   }

   protected static class OverlayStateShard extends BooleanStateShard {
      public OverlayStateShard(boolean enabledIn) {
         super("overlay", () -> {
            if (enabledIn) {
               Minecraft.m_91087_().f_91063_.m_109155_().m_118087_();
            }

         }, () -> {
            if (enabledIn) {
               Minecraft.m_91087_().f_91063_.m_109155_().m_118098_();
            }

         }, enabledIn);
      }
   }

   protected static class CullStateShard extends BooleanStateShard {
      public CullStateShard(boolean enabledIn) {
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

   protected static class DepthTestStateShard extends RenderStateShard {
      private final String f_110243_;

      public DepthTestStateShard(String funcNameIn, int funcIn) {
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

   protected static class WriteMaskStateShard extends RenderStateShard {
      private final boolean f_110356_;
      private final boolean f_110357_;

      public WriteMaskStateShard(boolean colorMaskIn, boolean depthMaskIn) {
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

   protected static class LayeringStateShard extends RenderStateShard {
      public LayeringStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class OutputStateShard extends RenderStateShard {
      public OutputStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class LineStateShard extends RenderStateShard {
      private final OptionalDouble f_110276_;

      public LineStateShard(OptionalDouble widthIn) {
         super("line_width", () -> {
            if (!Objects.equals(widthIn, OptionalDouble.of(1.0))) {
               if (widthIn.isPresent()) {
                  RenderSystem.lineWidth((float)widthIn.getAsDouble());
               } else {
                  RenderSystem.lineWidth(Math.max(2.5F, (float)Minecraft.m_91087_().m_91268_().m_85441_() / 1920.0F * 2.5F));
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

   protected static class ColorLogicStateShard extends RenderStateShard {
      public ColorLogicStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static final class OffsetTexturingStateShard extends TexturingStateShard {
      public OffsetTexturingStateShard(float offsetUIn, float offsetVIn) {
         super("offset_texturing", () -> {
            RenderSystem.setTextureMatrix((new Matrix4f()).translation(offsetUIn, offsetVIn, 0.0F));
         }, () -> {
            RenderSystem.resetTextureMatrix();
         });
      }
   }

   protected static class MultiTextureStateShard extends EmptyTextureStateShard {
      private final Optional f_173121_;

      MultiTextureStateShard(ImmutableList triplesIn) {
         super(() -> {
            int i = 0;
            UnmodifiableIterator var2 = triplesIn.iterator();

            while(var2.hasNext()) {
               Triple triple = (Triple)var2.next();
               TextureManager texturemanager = Minecraft.m_91087_().m_91097_();
               texturemanager.m_118506_((ResourceLocation)triple.getLeft()).m_117960_((Boolean)triple.getMiddle(), (Boolean)triple.getRight());
               RenderSystem.setShaderTexture(i++, (ResourceLocation)triple.getLeft());
            }

         }, () -> {
         });
         this.f_173121_ = triplesIn.stream().findFirst().map(Triple::getLeft);
      }

      protected Optional m_142706_() {
         return this.f_173121_;
      }

      public static Builder m_173127_() {
         return new Builder();
      }

      public static final class Builder {
         private final ImmutableList.Builder f_173129_ = new ImmutableList.Builder();

         public Builder m_173132_(ResourceLocation locationIn, boolean blurIn, boolean mipmapIn) {
            this.f_173129_.add(Triple.of(locationIn, blurIn, mipmapIn));
            return this;
         }

         public MultiTextureStateShard m_173131_() {
            return new MultiTextureStateShard(this.f_173129_.build());
         }
      }
   }

   static class BooleanStateShard extends RenderStateShard {
      private final boolean f_110227_;

      public BooleanStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn, boolean enabledIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
         this.f_110227_ = enabledIn;
      }

      public String toString() {
         return this.f_110133_ + "[" + this.f_110227_ + "]";
      }
   }
}
