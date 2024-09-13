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
   private static float f_173089_;
   public static double f_267492_;
   protected String f_110133_;
   private Runnable f_110131_;
   private Runnable f_110132_;
   protected static RenderStateShard.TransparencyStateShard f_110134_ = new RenderStateShard.TransparencyStateShard(
      "no_transparency", () -> RenderSystem.disableBlend(), () -> {
      }
   );
   protected static RenderStateShard.TransparencyStateShard f_110135_ = new RenderStateShard.TransparencyStateShard("additive_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static RenderStateShard.TransparencyStateShard f_110136_ = new RenderStateShard.TransparencyStateShard("lightning_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static RenderStateShard.TransparencyStateShard f_110137_ = new RenderStateShard.TransparencyStateShard(
      "glint_transparency",
      () -> {
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE
         );
      },
      () -> {
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
      }
   );
   protected static RenderStateShard.TransparencyStateShard f_110138_ = new RenderStateShard.TransparencyStateShard(
      "crumbling_transparency",
      () -> {
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
         );
      },
      () -> {
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
      }
   );
   protected static RenderStateShard.TransparencyStateShard f_110139_ = new RenderStateShard.TransparencyStateShard(
      "translucent_transparency",
      () -> {
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
         );
      },
      () -> {
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
      }
   );
   protected static RenderStateShard.ShaderStateShard f_173096_ = new RenderStateShard.ShaderStateShard();
   protected static RenderStateShard.ShaderStateShard f_173099_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172832_);
   protected static RenderStateShard.ShaderStateShard f_173100_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172808_);
   protected static RenderStateShard.ShaderStateShard f_173102_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172817_);
   protected static RenderStateShard.ShaderStateShard f_173103_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172835_);
   protected static RenderStateShard.ShaderStateShard f_173104_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172811_);
   protected static RenderStateShard.ShaderStateShard f_173105_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172640_);
   protected static RenderStateShard.ShaderStateShard f_173106_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172643_);
   protected static RenderStateShard.ShaderStateShard f_173107_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172646_);
   protected static RenderStateShard.ShaderStateShard f_173108_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172649_);
   protected static RenderStateShard.ShaderStateShard f_173109_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172652_);
   protected static RenderStateShard.ShaderStateShard f_173111_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172658_);
   protected static RenderStateShard.ShaderStateShard f_173112_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172661_);
   protected static RenderStateShard.ShaderStateShard f_173113_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172664_);
   protected static RenderStateShard.ShaderStateShard f_173114_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172667_);
   protected static RenderStateShard.ShaderStateShard f_173063_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172670_);
   protected static RenderStateShard.ShaderStateShard f_173064_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172673_);
   protected static RenderStateShard.ShaderStateShard f_173065_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172676_);
   protected static RenderStateShard.ShaderStateShard f_173066_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172679_);
   protected static RenderStateShard.ShaderStateShard f_234323_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_234223_);
   protected static RenderStateShard.ShaderStateShard f_173067_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172682_);
   protected static RenderStateShard.ShaderStateShard f_173068_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172685_);
   protected static RenderStateShard.ShaderStateShard f_173069_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172688_);
   protected static RenderStateShard.ShaderStateShard f_173070_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172691_);
   protected static RenderStateShard.ShaderStateShard f_173071_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172694_);
   protected static RenderStateShard.ShaderStateShard f_173072_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172697_);
   protected static RenderStateShard.ShaderStateShard f_173073_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172700_);
   protected static RenderStateShard.ShaderStateShard f_173074_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172703_);
   protected static RenderStateShard.ShaderStateShard f_173075_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172706_);
   protected static RenderStateShard.ShaderStateShard f_173076_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172709_);
   protected static RenderStateShard.ShaderStateShard f_173077_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172712_);
   protected static RenderStateShard.ShaderStateShard f_173079_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172741_);
   protected static RenderStateShard.ShaderStateShard f_173080_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172744_);
   protected static RenderStateShard.ShaderStateShard f_173081_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172745_);
   protected static RenderStateShard.ShaderStateShard f_173083_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172747_);
   protected static RenderStateShard.ShaderStateShard f_173084_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172748_);
   protected static RenderStateShard.ShaderStateShard f_173085_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172758_);
   protected static RenderStateShard.ShaderStateShard f_173086_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172749_);
   protected static RenderStateShard.ShaderStateShard f_268568_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_269563_);
   protected static RenderStateShard.ShaderStateShard f_173087_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172750_);
   protected static RenderStateShard.ShaderStateShard f_173088_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172751_);
   protected static RenderStateShard.ShaderStateShard f_268491_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_269511_);
   protected static RenderStateShard.ShaderStateShard f_173090_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172752_);
   protected static RenderStateShard.ShaderStateShard f_173091_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172753_);
   protected static RenderStateShard.ShaderStateShard f_173092_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172754_);
   protected static RenderStateShard.ShaderStateShard f_173093_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172755_);
   protected static RenderStateShard.ShaderStateShard f_173094_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172756_);
   protected static RenderStateShard.ShaderStateShard f_316212_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_320329_);
   protected static RenderStateShard.ShaderStateShard f_173095_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_172757_);
   protected static RenderStateShard.ShaderStateShard f_285573_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_285858_);
   protected static RenderStateShard.ShaderStateShard f_285619_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_285975_);
   protected static RenderStateShard.ShaderStateShard f_285642_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_285738_);
   protected static RenderStateShard.ShaderStateShard f_285582_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_285862_);
   protected static RenderStateShard.ShaderStateShard f_303180_ = new RenderStateShard.ShaderStateShard(GameRenderer::m_307576_);
   protected static RenderStateShard.TextureStateShard f_110145_ = new RenderStateShard.TextureStateShard(TextureAtlas.f_118259_, false, true);
   protected static RenderStateShard.TextureStateShard f_110146_ = new RenderStateShard.TextureStateShard(TextureAtlas.f_118259_, false, false);
   protected static RenderStateShard.EmptyTextureStateShard f_110147_ = new RenderStateShard.EmptyTextureStateShard();
   protected static RenderStateShard.TexturingStateShard f_110148_ = new RenderStateShard.TexturingStateShard("default_texturing", () -> {
   }, () -> {
   });
   protected static RenderStateShard.TexturingStateShard f_110150_ = new RenderStateShard.TexturingStateShard(
      "glint_texturing", () -> m_110186_(8.0F), () -> RenderSystem.resetTextureMatrix()
   );
   protected static RenderStateShard.TexturingStateShard f_110151_ = new RenderStateShard.TexturingStateShard(
      "entity_glint_texturing", () -> m_110186_(0.16F), () -> RenderSystem.resetTextureMatrix()
   );
   protected static RenderStateShard.LightmapStateShard f_110152_ = new RenderStateShard.LightmapStateShard(true);
   protected static RenderStateShard.LightmapStateShard f_110153_ = new RenderStateShard.LightmapStateShard(false);
   protected static RenderStateShard.OverlayStateShard f_110154_ = new RenderStateShard.OverlayStateShard(true);
   protected static RenderStateShard.OverlayStateShard f_110155_ = new RenderStateShard.OverlayStateShard(false);
   protected static RenderStateShard.CullStateShard f_110158_ = new RenderStateShard.CullStateShard(true);
   protected static RenderStateShard.CullStateShard f_110110_ = new RenderStateShard.CullStateShard(false);
   protected static RenderStateShard.DepthTestStateShard f_110111_ = new RenderStateShard.DepthTestStateShard("always", 519);
   protected static RenderStateShard.DepthTestStateShard f_110112_ = new RenderStateShard.DepthTestStateShard("==", 514);
   protected static RenderStateShard.DepthTestStateShard f_110113_ = new RenderStateShard.DepthTestStateShard("<=", 515);
   protected static RenderStateShard.DepthTestStateShard f_285579_ = new RenderStateShard.DepthTestStateShard(">", 516);
   protected static RenderStateShard.WriteMaskStateShard f_110114_ = new RenderStateShard.WriteMaskStateShard(true, true);
   protected static RenderStateShard.WriteMaskStateShard f_110115_ = new RenderStateShard.WriteMaskStateShard(true, false);
   protected static RenderStateShard.WriteMaskStateShard f_110116_ = new RenderStateShard.WriteMaskStateShard(false, true);
   protected static RenderStateShard.LayeringStateShard f_110117_ = new RenderStateShard.LayeringStateShard("no_layering", () -> {
   }, () -> {
   });
   protected static RenderStateShard.LayeringStateShard f_110118_ = new RenderStateShard.LayeringStateShard("polygon_offset_layering", () -> {
      RenderSystem.polygonOffset(-1.0F, -10.0F);
      RenderSystem.enablePolygonOffset();
   }, () -> {
      RenderSystem.polygonOffset(0.0F, 0.0F);
      RenderSystem.disablePolygonOffset();
   });
   protected static RenderStateShard.LayeringStateShard f_110119_ = new RenderStateShard.LayeringStateShard("view_offset_z_layering", () -> {
      Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
      matrix4fstack.pushMatrix();
      matrix4fstack.scale(0.99975586F, 0.99975586F, 0.99975586F);
      RenderSystem.applyModelViewMatrix();
   }, () -> {
      Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
      matrix4fstack.popMatrix();
      RenderSystem.applyModelViewMatrix();
   });
   protected static RenderStateShard.OutputStateShard f_110123_ = new RenderStateShard.OutputStateShard("main_target", () -> {
   }, () -> {
   });
   protected static RenderStateShard.OutputStateShard f_110124_ = new RenderStateShard.OutputStateShard(
      "outline_target", () -> Minecraft.m_91087_().f_91060_.m_109827_().m_83947_(false), () -> Minecraft.m_91087_().m_91385_().m_83947_(false)
   );
   protected static RenderStateShard.OutputStateShard f_110125_ = new RenderStateShard.OutputStateShard("translucent_target", () -> {
      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().f_91060_.m_109828_().m_83947_(false);
      }
   }, () -> {
      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().m_91385_().m_83947_(false);
      }
   });
   protected static RenderStateShard.OutputStateShard f_110126_ = new RenderStateShard.OutputStateShard("particles_target", () -> {
      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().f_91060_.m_109830_().m_83947_(false);
      }
   }, () -> {
      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().m_91385_().m_83947_(false);
      }
   });
   protected static RenderStateShard.OutputStateShard f_110127_ = new RenderStateShard.OutputStateShard("weather_target", () -> {
      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().f_91060_.m_109831_().m_83947_(false);
      }
   }, () -> {
      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().m_91385_().m_83947_(false);
      }
   });
   protected static RenderStateShard.OutputStateShard f_110128_ = new RenderStateShard.OutputStateShard("clouds_target", () -> {
      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().f_91060_.m_109832_().m_83947_(false);
      }
   }, () -> {
      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().m_91385_().m_83947_(false);
      }
   });
   protected static RenderStateShard.OutputStateShard f_110129_ = new RenderStateShard.OutputStateShard("item_entity_target", () -> {
      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().f_91060_.m_109829_().m_83947_(false);
      }
   }, () -> {
      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().m_91385_().m_83947_(false);
      }
   });
   protected static RenderStateShard.LineStateShard f_110130_ = new RenderStateShard.LineStateShard(OptionalDouble.m_253057_(1.0));
   protected static RenderStateShard.ColorLogicStateShard f_285585_ = new RenderStateShard.ColorLogicStateShard(
      "no_color_logic", () -> RenderSystem.disableColorLogicOp(), () -> {
      }
   );
   protected static RenderStateShard.ColorLogicStateShard f_285603_ = new RenderStateShard.ColorLogicStateShard("or_reverse", () -> {
      RenderSystem.enableColorLogicOp();
      RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
   }, () -> RenderSystem.disableColorLogicOp());

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
      long i = (long)((double)Util.m_137550_() * Minecraft.m_91087_().f_91066_.m_267805_().m_231551_() * 8.0);
      float f = (float)(i % 110000L) / 110000.0F;
      float f1 = (float)(i % 30000L) / 30000.0F;
      Matrix4f matrix4f = new Matrix4f().translation(-f, f1, 0.0F);
      matrix4f.rotateZ((float) (Math.PI / 18)).scale(scaleIn);
      RenderSystem.setTextureMatrix(matrix4f);
   }

   public String getName() {
      return this.f_110133_;
   }

   static class BooleanStateShard extends RenderStateShard {
      private boolean f_110227_;

      public BooleanStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn, boolean enabledIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
         this.f_110227_ = enabledIn;
      }

      @Override
      public String toString() {
         return this.f_110133_ + "[" + this.f_110227_ + "]";
      }
   }

   protected static class ColorLogicStateShard extends RenderStateShard {
      public ColorLogicStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class CullStateShard extends RenderStateShard.BooleanStateShard {
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
      private String f_110243_;

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

      @Override
      public String toString() {
         return this.f_110133_ + "[" + this.f_110243_ + "]";
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

      protected Optional<ResourceLocation> m_142706_() {
         return Optional.m_274566_();
      }

      public boolean isBlur() {
         return false;
      }

      public boolean isMipmap() {
         return false;
      }
   }

   protected static class LayeringStateShard extends RenderStateShard {
      public LayeringStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class LightmapStateShard extends RenderStateShard.BooleanStateShard {
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

   protected static class LineStateShard extends RenderStateShard {
      private OptionalDouble f_110276_;

      public LineStateShard(OptionalDouble widthIn) {
         super("line_width", () -> {
            if (!Objects.equals(widthIn, OptionalDouble.m_253057_(1.0))) {
               if (widthIn.isPresent()) {
                  RenderSystem.lineWidth((float)widthIn.getAsDouble());
               } else {
                  RenderSystem.lineWidth(Math.max(2.5F, (float)Minecraft.m_91087_().m_91268_().m_85441_() / 1920.0F * 2.5F));
               }
            }
         }, () -> {
            if (!Objects.equals(widthIn, OptionalDouble.m_253057_(1.0))) {
               RenderSystem.lineWidth(1.0F);
            }
         });
         this.f_110276_ = widthIn;
      }

      @Override
      public String toString() {
         return this.f_110133_ + "[" + (this.f_110276_.isPresent() ? this.f_110276_.getAsDouble() : "window_scale") + "]";
      }
   }

   protected static class MultiTextureStateShard extends RenderStateShard.EmptyTextureStateShard {
      private Optional<ResourceLocation> f_173121_;

      MultiTextureStateShard(ImmutableList<Triple<ResourceLocation, Boolean, Boolean>> triplesIn) {
         super(() -> {
            int i = 0;
            UnmodifiableIterator var2 = triplesIn.iterator();

            while (var2.hasNext()) {
               Triple<ResourceLocation, Boolean, Boolean> triple = (Triple<ResourceLocation, Boolean, Boolean>)var2.next();
               TextureManager texturemanager = Minecraft.m_91087_().m_91097_();
               texturemanager.m_118506_((ResourceLocation)triple.getLeft()).m_117960_((Boolean)triple.getMiddle(), (Boolean)triple.getRight());
               RenderSystem.setShaderTexture(i++, (ResourceLocation)triple.getLeft());
            }
         }, () -> {
         });
         this.f_173121_ = triplesIn.stream().findFirst().map(Triple::getLeft);
      }

      @Override
      protected Optional<ResourceLocation> m_142706_() {
         return this.f_173121_;
      }

      public static RenderStateShard.MultiTextureStateShard.Builder m_173127_() {
         return new RenderStateShard.MultiTextureStateShard.Builder();
      }

      public static class Builder {
         private com.google.common.collect.ImmutableList.Builder<Triple<ResourceLocation, Boolean, Boolean>> f_173129_ = new com.google.common.collect.ImmutableList.Builder();

         public RenderStateShard.MultiTextureStateShard.Builder m_173132_(ResourceLocation locationIn, boolean blurIn, boolean mipmapIn) {
            this.f_173129_.add(Triple.m_253057_(locationIn, blurIn, mipmapIn));
            return this;
         }

         public RenderStateShard.MultiTextureStateShard m_173131_() {
            return new RenderStateShard.MultiTextureStateShard(this.f_173129_.build());
         }
      }
   }

   protected static class OffsetTexturingStateShard extends RenderStateShard.TexturingStateShard {
      public OffsetTexturingStateShard(float offsetUIn, float offsetVIn) {
         super(
            "offset_texturing",
            () -> RenderSystem.setTextureMatrix(new Matrix4f().translation(offsetUIn, offsetVIn, 0.0F)),
            () -> RenderSystem.resetTextureMatrix()
         );
      }
   }

   protected static class OutputStateShard extends RenderStateShard {
      public OutputStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class OverlayStateShard extends RenderStateShard.BooleanStateShard {
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

   protected static class ShaderStateShard extends RenderStateShard {
      private Optional<Supplier<ShaderInstance>> f_173136_;

      public ShaderStateShard(Supplier<ShaderInstance> shaderIn) {
         super("shader", () -> RenderSystem.setShader(shaderIn), () -> {
         });
         this.f_173136_ = Optional.m_253057_(shaderIn);
      }

      public ShaderStateShard() {
         super("shader", () -> RenderSystem.setShader(() -> null), () -> {
         });
         this.f_173136_ = Optional.m_274566_();
      }

      @Override
      public String toString() {
         return this.f_110133_ + "[" + this.f_173136_ + "]";
      }
   }

   protected static class TextureStateShard extends RenderStateShard.EmptyTextureStateShard {
      private Optional<ResourceLocation> f_110328_;
      private boolean f_110329_;
      private boolean f_110330_;

      public TextureStateShard(ResourceLocation locationIn, boolean blurIn, boolean mipmapIn) {
         super(() -> {
            TextureManager texturemanager = Minecraft.m_91087_().m_91097_();
            texturemanager.m_118506_(locationIn).m_117960_(blurIn, mipmapIn);
            RenderSystem.setShaderTexture(0, locationIn);
         }, () -> {
         });
         this.f_110328_ = Optional.m_253057_(locationIn);
         this.f_110329_ = blurIn;
         this.f_110330_ = mipmapIn;
      }

      @Override
      public String toString() {
         return this.f_110133_ + "[" + this.f_110328_ + "(blur=" + this.f_110329_ + ", mipmap=" + this.f_110330_ + ")]";
      }

      @Override
      protected Optional<ResourceLocation> m_142706_() {
         return this.f_110328_;
      }

      @Override
      public boolean isBlur() {
         return this.f_110329_;
      }

      @Override
      public boolean isMipmap() {
         return this.f_110330_;
      }
   }

   protected static class TexturingStateShard extends RenderStateShard {
      public TexturingStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class TransparencyStateShard extends RenderStateShard {
      public TransparencyStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class WriteMaskStateShard extends RenderStateShard {
      private boolean f_110356_;
      private boolean f_110357_;

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

      @Override
      public String toString() {
         return this.f_110133_ + "[writeColor=" + this.f_110356_ + ", writeDepth=" + this.f_110357_ + "]";
      }
   }
}
