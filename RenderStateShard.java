import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager$C_3128_;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import net.minecraft.src.C_3391_;
import org.apache.commons.lang3.tuple.Triple;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

public abstract class RenderStateShard {
   private static final float aQ = 0.99975586F;
   public static final double a = 8.0;
   protected final String b;
   private final Runnable aR;
   private final Runnable aS;
   protected static final RenderStateShard.p c = new RenderStateShard.p("no_transparency", () -> RenderSystem.disableBlend(), () -> {
   });
   protected static final RenderStateShard.p d = new RenderStateShard.p("additive_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final RenderStateShard.p e = new RenderStateShard.p("lightning_transparency", () -> {
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
   }, () -> {
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
   });
   protected static final RenderStateShard.p f = new RenderStateShard.p(
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
   protected static final RenderStateShard.p g = new RenderStateShard.p(
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
   protected static final RenderStateShard.p h = new RenderStateShard.p(
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
   protected static final RenderStateShard.m i = new RenderStateShard.m();
   protected static final RenderStateShard.m j = new RenderStateShard.m(GameRenderer::t);
   protected static final RenderStateShard.m k = new RenderStateShard.m(GameRenderer::o);
   protected static final RenderStateShard.m l = new RenderStateShard.m(GameRenderer::q);
   protected static final RenderStateShard.m m = new RenderStateShard.m(GameRenderer::u);
   protected static final RenderStateShard.m n = new RenderStateShard.m(GameRenderer::p);
   protected static final RenderStateShard.m o = new RenderStateShard.m(GameRenderer::v);
   protected static final RenderStateShard.m p = new RenderStateShard.m(GameRenderer::w);
   protected static final RenderStateShard.m q = new RenderStateShard.m(GameRenderer::x);
   protected static final RenderStateShard.m r = new RenderStateShard.m(GameRenderer::y);
   protected static final RenderStateShard.m s = new RenderStateShard.m(GameRenderer::z);
   protected static final RenderStateShard.m t = new RenderStateShard.m(GameRenderer::A);
   protected static final RenderStateShard.m u = new RenderStateShard.m(GameRenderer::B);
   protected static final RenderStateShard.m v = new RenderStateShard.m(GameRenderer::C);
   protected static final RenderStateShard.m w = new RenderStateShard.m(GameRenderer::D);
   protected static final RenderStateShard.m x = new RenderStateShard.m(GameRenderer::E);
   protected static final RenderStateShard.m y = new RenderStateShard.m(GameRenderer::F);
   protected static final RenderStateShard.m z = new RenderStateShard.m(GameRenderer::G);
   protected static final RenderStateShard.m A = new RenderStateShard.m(GameRenderer::H);
   protected static final RenderStateShard.m B = new RenderStateShard.m(GameRenderer::I);
   protected static final RenderStateShard.m C = new RenderStateShard.m(GameRenderer::J);
   protected static final RenderStateShard.m D = new RenderStateShard.m(GameRenderer::K);
   protected static final RenderStateShard.m E = new RenderStateShard.m(GameRenderer::L);
   protected static final RenderStateShard.m F = new RenderStateShard.m(GameRenderer::M);
   protected static final RenderStateShard.m G = new RenderStateShard.m(GameRenderer::N);
   protected static final RenderStateShard.m H = new RenderStateShard.m(GameRenderer::O);
   protected static final RenderStateShard.m I = new RenderStateShard.m(GameRenderer::P);
   protected static final RenderStateShard.m J = new RenderStateShard.m(GameRenderer::Q);
   protected static final RenderStateShard.m K = new RenderStateShard.m(GameRenderer::S);
   protected static final RenderStateShard.m L = new RenderStateShard.m(GameRenderer::T);
   protected static final RenderStateShard.m M = new RenderStateShard.m(GameRenderer::U);
   protected static final RenderStateShard.m N = new RenderStateShard.m(GameRenderer::W);
   protected static final RenderStateShard.m O = new RenderStateShard.m(GameRenderer::X);
   protected static final RenderStateShard.m P = new RenderStateShard.m(GameRenderer::Y);
   protected static final RenderStateShard.m Q = new RenderStateShard.m(GameRenderer::aa);
   protected static final RenderStateShard.m R = new RenderStateShard.m(GameRenderer::ab);
   protected static final RenderStateShard.m S = new RenderStateShard.m(GameRenderer::ao);
   protected static final RenderStateShard.m T = new RenderStateShard.m(GameRenderer::ac);
   protected static final RenderStateShard.m U = new RenderStateShard.m(GameRenderer::ad);
   protected static final RenderStateShard.m V = new RenderStateShard.m(GameRenderer::ae);
   protected static final RenderStateShard.m W = new RenderStateShard.m(GameRenderer::af);
   protected static final RenderStateShard.m X = new RenderStateShard.m(GameRenderer::ag);
   protected static final RenderStateShard.m Y = new RenderStateShard.m(GameRenderer::ah);
   protected static final RenderStateShard.m Z = new RenderStateShard.m(GameRenderer::ai);
   protected static final RenderStateShard.m aa = new RenderStateShard.m(GameRenderer::aj);
   protected static final RenderStateShard.m ab = new RenderStateShard.m(GameRenderer::ak);
   protected static final RenderStateShard.m ac = new RenderStateShard.m(GameRenderer::al);
   protected static final RenderStateShard.m ad = new RenderStateShard.m(GameRenderer::am);
   protected static final RenderStateShard.m ae = new RenderStateShard.m(GameRenderer::an);
   protected static final RenderStateShard.m af = new RenderStateShard.m(GameRenderer::ap);
   protected static final RenderStateShard.m ag = new RenderStateShard.m(GameRenderer::aq);
   protected static final RenderStateShard.m ah = new RenderStateShard.m(GameRenderer::ar);
   protected static final RenderStateShard.m ai = new RenderStateShard.m(GameRenderer::as);
   protected static final RenderStateShard.m aj = new RenderStateShard.m(GameRenderer::R);
   protected static final RenderStateShard.n ak = new RenderStateShard.n(TextureAtlas.e, false, true);
   protected static final RenderStateShard.n al = new RenderStateShard.n(TextureAtlas.e, false, false);
   protected static final RenderStateShard.e am = new RenderStateShard.e();
   protected static final RenderStateShard.o an = new RenderStateShard.o("default_texturing", () -> {
   }, () -> {
   });
   protected static final RenderStateShard.o ao = new RenderStateShard.o("glint_texturing", () -> a(8.0F), () -> RenderSystem.resetTextureMatrix());
   protected static final RenderStateShard.o ap = new RenderStateShard.o("entity_glint_texturing", () -> a(0.16F), () -> RenderSystem.resetTextureMatrix());
   protected static final RenderStateShard.g aq = new RenderStateShard.g(true);
   protected static final RenderStateShard.g ar = new RenderStateShard.g(false);
   protected static final RenderStateShard.l as = new RenderStateShard.l(true);
   protected static final RenderStateShard.l at = new RenderStateShard.l(false);
   protected static final RenderStateShard.c au = new RenderStateShard.c(true);
   protected static final RenderStateShard.c av = new RenderStateShard.c(false);
   protected static final RenderStateShard.d aw = new RenderStateShard.d("always", 519);
   protected static final RenderStateShard.d ax = new RenderStateShard.d("==", 514);
   protected static final RenderStateShard.d ay = new RenderStateShard.d("<=", 515);
   protected static final RenderStateShard.d az = new RenderStateShard.d(">", 516);
   protected static final RenderStateShard.q aA = new RenderStateShard.q(true, true);
   protected static final RenderStateShard.q aB = new RenderStateShard.q(true, false);
   protected static final RenderStateShard.q aC = new RenderStateShard.q(false, true);
   protected static final RenderStateShard.f aD = new RenderStateShard.f("no_layering", () -> {
   }, () -> {
   });
   protected static final RenderStateShard.f aE = new RenderStateShard.f("polygon_offset_layering", () -> {
      RenderSystem.polygonOffset(-1.0F, -10.0F);
      RenderSystem.enablePolygonOffset();
   }, () -> {
      RenderSystem.polygonOffset(0.0F, 0.0F);
      RenderSystem.disablePolygonOffset();
   });
   protected static final RenderStateShard.f aF = new RenderStateShard.f("view_offset_z_layering", () -> {
      Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
      matrix4fstack.pushMatrix();
      matrix4fstack.scale(0.99975586F, 0.99975586F, 0.99975586F);
      RenderSystem.applyModelViewMatrix();
   }, () -> {
      Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
      matrix4fstack.popMatrix();
      RenderSystem.applyModelViewMatrix();
   });
   protected static final RenderStateShard.k aG = new RenderStateShard.k("main_target", () -> {
   }, () -> {
   });
   protected static final RenderStateShard.k aH = new RenderStateShard.k(
      "outline_target", () -> C_3391_.m_91087_().f.s().a(false), () -> C_3391_.m_91087_().h().a(false)
   );
   protected static final RenderStateShard.k aI = new RenderStateShard.k("translucent_target", () -> {
      if (C_3391_.m_91085_()) {
         C_3391_.m_91087_().f.t().a(false);
      }
   }, () -> {
      if (C_3391_.m_91085_()) {
         C_3391_.m_91087_().h().a(false);
      }
   });
   protected static final RenderStateShard.k aJ = new RenderStateShard.k("particles_target", () -> {
      if (C_3391_.m_91085_()) {
         C_3391_.m_91087_().f.v().a(false);
      }
   }, () -> {
      if (C_3391_.m_91085_()) {
         C_3391_.m_91087_().h().a(false);
      }
   });
   protected static final RenderStateShard.k aK = new RenderStateShard.k("weather_target", () -> {
      if (C_3391_.m_91085_()) {
         C_3391_.m_91087_().f.w().a(false);
      }
   }, () -> {
      if (C_3391_.m_91085_()) {
         C_3391_.m_91087_().h().a(false);
      }
   });
   protected static final RenderStateShard.k aL = new RenderStateShard.k("clouds_target", () -> {
      if (C_3391_.m_91085_()) {
         C_3391_.m_91087_().f.x().a(false);
      }
   }, () -> {
      if (C_3391_.m_91085_()) {
         C_3391_.m_91087_().h().a(false);
      }
   });
   protected static final RenderStateShard.k aM = new RenderStateShard.k("item_entity_target", () -> {
      if (C_3391_.m_91085_()) {
         C_3391_.m_91087_().f.u().a(false);
      }
   }, () -> {
      if (C_3391_.m_91085_()) {
         C_3391_.m_91087_().h().a(false);
      }
   });
   protected static final RenderStateShard.h aN = new RenderStateShard.h(OptionalDouble.of(1.0));
   protected static final RenderStateShard.b aO = new RenderStateShard.b("no_color_logic", () -> RenderSystem.disableColorLogicOp(), () -> {
   });
   protected static final RenderStateShard.b aP = new RenderStateShard.b("or_reverse", () -> {
      RenderSystem.enableColorLogicOp();
      RenderSystem.logicOp(GlStateManager$C_3128_.OR_REVERSE);
   }, () -> RenderSystem.disableColorLogicOp());

   public RenderStateShard(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
      this.b = nameIn;
      this.aR = setupTaskIn;
      this.aS = clearTaskIn;
   }

   public void a() {
      this.aR.run();
   }

   public void b() {
      this.aS.run();
   }

   public String toString() {
      return this.b;
   }

   private static void a(float scaleIn) {
      long i = (long)((double)Util.c() * C_3391_.m_91087_().m.am().c() * 8.0);
      float f = (float)(i % 110000L) / 110000.0F;
      float f1 = (float)(i % 30000L) / 30000.0F;
      Matrix4f matrix4f = new Matrix4f().translation(-f, f1, 0.0F);
      matrix4f.rotateZ((float) (Math.PI / 18)).scale(scaleIn);
      RenderSystem.setTextureMatrix(matrix4f);
   }

   public String getName() {
      return this.b;
   }

   static class a extends RenderStateShard {
      private final boolean aQ;

      public a(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn, boolean enabledIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
         this.aQ = enabledIn;
      }

      @Override
      public String toString() {
         return this.b + "[" + this.aQ + "]";
      }
   }

   protected static class b extends RenderStateShard {
      public b(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class c extends RenderStateShard.a {
      public c(boolean enabledIn) {
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

   protected static class d extends RenderStateShard {
      private final String aQ;

      public d(String funcNameIn, int funcIn) {
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
         this.aQ = funcNameIn;
      }

      @Override
      public String toString() {
         return this.b + "[" + this.aQ + "]";
      }
   }

   protected static class e extends RenderStateShard {
      public e(Runnable setupTaskIn, Runnable clearTaskIn) {
         super("texture", setupTaskIn, clearTaskIn);
      }

      e() {
         super("texture", () -> {
         }, () -> {
         });
      }

      protected Optional<ResourceLocation> c() {
         return Optional.empty();
      }

      public boolean isBlur() {
         return false;
      }

      public boolean isMipmap() {
         return false;
      }
   }

   protected static class f extends RenderStateShard {
      public f(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class g extends RenderStateShard.a {
      public g(boolean enabledIn) {
         super("lightmap", () -> {
            if (enabledIn) {
               C_3391_.m_91087_().j.m().c();
            }
         }, () -> {
            if (enabledIn) {
               C_3391_.m_91087_().j.m().b();
            }
         }, enabledIn);
      }
   }

   protected static class h extends RenderStateShard {
      private final OptionalDouble aQ;

      public h(OptionalDouble widthIn) {
         super("line_width", () -> {
            if (!Objects.equals(widthIn, OptionalDouble.of(1.0))) {
               if (widthIn.isPresent()) {
                  RenderSystem.lineWidth((float)widthIn.getAsDouble());
               } else {
                  RenderSystem.lineWidth(Math.max(2.5F, (float)C_3391_.m_91087_().aM().l() / 1920.0F * 2.5F));
               }
            }
         }, () -> {
            if (!Objects.equals(widthIn, OptionalDouble.of(1.0))) {
               RenderSystem.lineWidth(1.0F);
            }
         });
         this.aQ = widthIn;
      }

      @Override
      public String toString() {
         return this.b + "[" + (this.aQ.isPresent() ? this.aQ.getAsDouble() : "window_scale") + "]";
      }
   }

   protected static class i extends RenderStateShard.e {
      private final Optional<ResourceLocation> aQ;

      i(ImmutableList<Triple<ResourceLocation, Boolean, Boolean>> triplesIn) {
         super(() -> {
            int i = 0;
            UnmodifiableIterator var2 = triplesIn.iterator();

            while (var2.hasNext()) {
               Triple<ResourceLocation, Boolean, Boolean> triple = (Triple<ResourceLocation, Boolean, Boolean>)var2.next();
               TextureManager texturemanager = C_3391_.m_91087_().aa();
               texturemanager.b((ResourceLocation)triple.getLeft()).a((Boolean)triple.getMiddle(), (Boolean)triple.getRight());
               RenderSystem.setShaderTexture(i++, (ResourceLocation)triple.getLeft());
            }
         }, () -> {
         });
         this.aQ = triplesIn.stream().findFirst().map(Triple::getLeft);
      }

      @Override
      protected Optional<ResourceLocation> c() {
         return this.aQ;
      }

      public static RenderStateShard.i.a d() {
         return new RenderStateShard.i.a();
      }

      public static final class a {
         private final Builder<Triple<ResourceLocation, Boolean, Boolean>> a = new Builder();

         public RenderStateShard.i.a a(ResourceLocation locationIn, boolean blurIn, boolean mipmapIn) {
            this.a.add(Triple.of(locationIn, blurIn, mipmapIn));
            return this;
         }

         public RenderStateShard.i a() {
            return new RenderStateShard.i(this.a.build());
         }
      }
   }

   protected static final class j extends RenderStateShard.o {
      public j(float offsetUIn, float offsetVIn) {
         super(
            "offset_texturing",
            () -> RenderSystem.setTextureMatrix(new Matrix4f().translation(offsetUIn, offsetVIn, 0.0F)),
            () -> RenderSystem.resetTextureMatrix()
         );
      }
   }

   protected static class k extends RenderStateShard {
      public k(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class l extends RenderStateShard.a {
      public l(boolean enabledIn) {
         super("overlay", () -> {
            if (enabledIn) {
               C_3391_.m_91087_().j.n().m_118087_();
            }
         }, () -> {
            if (enabledIn) {
               C_3391_.m_91087_().j.n().m_118098_();
            }
         }, enabledIn);
      }
   }

   protected static class m extends RenderStateShard {
      private final Optional<Supplier<ShaderInstance>> aQ;

      public m(Supplier<ShaderInstance> shaderIn) {
         super("shader", () -> RenderSystem.setShader(shaderIn), () -> {
         });
         this.aQ = Optional.of(shaderIn);
      }

      public m() {
         super("shader", () -> RenderSystem.setShader(() -> null), () -> {
         });
         this.aQ = Optional.empty();
      }

      @Override
      public String toString() {
         return this.b + "[" + this.aQ + "]";
      }
   }

   protected static class n extends RenderStateShard.e {
      private final Optional<ResourceLocation> aQ;
      private final boolean aR;
      private final boolean aS;

      public n(ResourceLocation locationIn, boolean blurIn, boolean mipmapIn) {
         super(() -> {
            TextureManager texturemanager = C_3391_.m_91087_().aa();
            texturemanager.b(locationIn).a(blurIn, mipmapIn);
            RenderSystem.setShaderTexture(0, locationIn);
         }, () -> {
         });
         this.aQ = Optional.of(locationIn);
         this.aR = blurIn;
         this.aS = mipmapIn;
      }

      @Override
      public String toString() {
         return this.b + "[" + this.aQ + "(blur=" + this.aR + ", mipmap=" + this.aS + ")]";
      }

      @Override
      protected Optional<ResourceLocation> c() {
         return this.aQ;
      }

      @Override
      public boolean isBlur() {
         return this.aR;
      }

      @Override
      public boolean isMipmap() {
         return this.aS;
      }
   }

   protected static class o extends RenderStateShard {
      public o(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class p extends RenderStateShard {
      public p(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, setupTaskIn, clearTaskIn);
      }
   }

   protected static class q extends RenderStateShard {
      private final boolean aQ;
      private final boolean aR;

      public q(boolean colorMaskIn, boolean depthMaskIn) {
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
         this.aQ = colorMaskIn;
         this.aR = depthMaskIn;
      }

      @Override
      public String toString() {
         return this.b + "[writeColor=" + this.aQ + ", writeDepth=" + this.aR + "]";
      }
   }
}
