import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_141167_;
import net.minecraft.src.C_141633_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_178_;
import net.minecraft.src.C_262715_;
import net.minecraft.src.C_262719_;
import net.minecraft.src.C_262721_;
import net.minecraft.src.C_263595_;
import net.minecraft.src.C_268388_;
import net.minecraft.src.C_290054_;
import net.minecraft.src.C_290233_;
import net.minecraft.src.C_3144_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4105_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5000_;
import net.minecraft.src.C_5007_;
import net.minecraft.src.C_5020_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_174_.C_175_;
import net.minecraft.src.C_290233_.C_290163_;
import net.minecraft.src.C_290233_.C_290199_;
import net.minecraft.src.C_290233_.C_290255_;
import net.minecraft.src.C_290233_.C_290255_.C_290040_;
import net.minecraft.src.C_5007_.C_5008_;
import net.minecraft.src.C_5007_.C_5009_;
import net.minecraft.src.C_5007_.C_5010_;
import net.minecraftforge.client.extensions.IForgeGuiGraphics;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import org.joml.Matrix4f;
import org.joml.Vector2ic;

public class GuiGraphics implements IForgeGuiGraphics {
   public static final float a = 10000.0F;
   public static final float b = -10000.0F;
   private static final int c = 2;
   private final C_3391_ d;
   private final PoseStack e;
   private final MultiBufferSource.a f;
   private final GuiGraphics.a g = new GuiGraphics.a();
   private final C_290054_ h;
   private boolean i;
   private C_1391_ tooltipStack = C_1391_.f_41583_;

   private GuiGraphics(C_3391_ minecraftIn, PoseStack matrixStackIn, MultiBufferSource.a bufferSourceIn) {
      this.d = minecraftIn;
      this.e = matrixStackIn;
      this.f = bufferSourceIn;
      this.h = minecraftIn.m_292761_();
   }

   public GuiGraphics(C_3391_ minecraftIn, MultiBufferSource.a bufferSourceIn) {
      this(minecraftIn, new PoseStack(), bufferSourceIn);
   }

   @Deprecated
   public void a(Runnable runnableIn) {
      this.e();
      this.i = true;
      runnableIn.run();
      this.i = false;
      this.e();
   }

   @Deprecated
   private void g() {
      if (!this.i) {
         this.e();
      }
   }

   @Deprecated
   private void h() {
      if (this.i) {
         this.e();
      }
   }

   public int a() {
      return this.d.aM().p();
   }

   public int b() {
      return this.d.aM().q();
   }

   public PoseStack c() {
      return this.e;
   }

   public MultiBufferSource.a d() {
      return this.f;
   }

   public void e() {
      RenderSystem.disableDepthTest();
      this.f.b();
      RenderSystem.enableDepthTest();
   }

   public void a(int startX, int endX, int y, int colorIn) {
      this.a(RenderType.E(), startX, endX, y, colorIn);
   }

   public void a(RenderType renderTypeIn, int startX, int endX, int y, int colorIn) {
      if (endX < startX) {
         int i = startX;
         startX = endX;
         endX = i;
      }

      this.a(renderTypeIn, startX, y, endX + 1, y + 1, colorIn);
   }

   public void b(int x, int startY, int endY, int colorIn) {
      this.b(RenderType.E(), x, startY, endY, colorIn);
   }

   public void b(RenderType renderTypeIn, int x, int startY, int endY, int colorIn) {
      if (endY < startY) {
         int i = startY;
         startY = endY;
         endY = i;
      }

      this.a(renderTypeIn, x, startY + 1, x + 1, endY, colorIn);
   }

   public void c(int left, int top, int right, int bottom) {
      this.a(this.g.a(new C_263595_(left, top, right - left, bottom - top)));
   }

   public void f() {
      this.a(this.g.a());
   }

   public boolean a(int x, int y) {
      return this.g.a(x, y);
   }

   private void a(@Nullable C_263595_ rectIn) {
      this.h();
      if (rectIn != null) {
         Window window = C_3391_.m_91087_().aM();
         int i = window.m();
         double d0 = window.t();
         double d1 = (double)rectIn.m_274563_() * d0;
         double d2 = (double)i - (double)rectIn.m_274349_() * d0;
         double d3 = (double)rectIn.f_263770_() * d0;
         double d4 = (double)rectIn.f_263800_() * d0;
         RenderSystem.enableScissor((int)d1, (int)d2, Math.max(0, (int)d3), Math.max(0, (int)d4));
      } else {
         RenderSystem.disableScissor();
      }
   }

   public void a(float red, float green, float blue, float alpha) {
      this.h();
      RenderSystem.setShaderColor(red, green, blue, alpha);
   }

   public void a(int left, int top, int right, int bottom, int colorIn) {
      this.a(left, top, right, bottom, 0, colorIn);
   }

   public void a(int left, int top, int right, int bottom, int z, int colorIn) {
      this.a(RenderType.E(), left, top, right, bottom, z, colorIn);
   }

   public void a(RenderType renderTypeIn, int left, int top, int right, int bottom, int colorIn) {
      this.a(renderTypeIn, left, top, right, bottom, 0, colorIn);
   }

   public void a(RenderType renderTypeIn, int left, int top, int right, int bottom, int z, int colorIn) {
      Matrix4f matrix4f = this.e.c().a();
      if (left < right) {
         int i = left;
         left = right;
         right = i;
      }

      if (top < bottom) {
         int j = top;
         top = bottom;
         bottom = j;
      }

      VertexConsumer vertexconsumer = this.f.getBuffer(renderTypeIn);
      vertexconsumer.a(matrix4f, (float)left, (float)top, (float)z).a(colorIn);
      vertexconsumer.a(matrix4f, (float)left, (float)bottom, (float)z).a(colorIn);
      vertexconsumer.a(matrix4f, (float)right, (float)bottom, (float)z).a(colorIn);
      vertexconsumer.a(matrix4f, (float)right, (float)top, (float)z).a(colorIn);
      this.g();
   }

   public void b(int left, int top, int right, int bottom, int startColor, int endColor) {
      this.a(left, top, right, bottom, 0, startColor, endColor);
   }

   public void a(int left, int top, int right, int bottom, int z, int startColor, int endColor) {
      this.a(RenderType.E(), left, top, right, bottom, startColor, endColor, z);
   }

   public void a(RenderType renderTypeIn, int left, int top, int right, int bottom, int startColor, int endColor, int z) {
      VertexConsumer vertexconsumer = this.f.getBuffer(renderTypeIn);
      this.a(vertexconsumer, left, top, right, bottom, z, startColor, endColor);
      this.g();
   }

   private void a(VertexConsumer bufferIn, int left, int top, int right, int bottom, int z, int startColor, int endColor) {
      Matrix4f matrix4f = this.e.c().a();
      bufferIn.a(matrix4f, (float)left, (float)top, (float)z).a(startColor);
      bufferIn.a(matrix4f, (float)left, (float)bottom, (float)z).a(endColor);
      bufferIn.a(matrix4f, (float)right, (float)bottom, (float)z).a(endColor);
      bufferIn.a(matrix4f, (float)right, (float)top, (float)z).a(startColor);
   }

   public void b(RenderType renderTypeIn, int x, int y, int width, int height, int z) {
      Matrix4f matrix4f = this.e.c().a();
      VertexConsumer vertexconsumer = this.f.getBuffer(renderTypeIn);
      vertexconsumer.a(matrix4f, (float)x, (float)y, (float)z);
      vertexconsumer.a(matrix4f, (float)x, (float)height, (float)z);
      vertexconsumer.a(matrix4f, (float)width, (float)height, (float)z);
      vertexconsumer.a(matrix4f, (float)width, (float)y, (float)z);
      this.g();
   }

   public void a(Font font, String text, int x, int y, int color) {
      this.b(font, text, x - font.b(text) / 2, y, color);
   }

   public void a(Font font, C_4996_ text, int x, int y, int color) {
      C_178_ formattedcharsequence = text.m_7532_();
      this.b(font, formattedcharsequence, x - font.a(formattedcharsequence) / 2, y, color);
   }

   public void a(Font font, C_178_ text, int x, int y, int color) {
      this.b(font, text, x - font.a(text) / 2, y, color);
   }

   public int b(Font font, @Nullable String text, int x, int y, int color) {
      return this.a(font, text, x, y, color, true);
   }

   public int a(Font font, @Nullable String text, int x, int y, int color, boolean shadow) {
      return this.drawString(font, text, (float)x, (float)y, color, shadow);
   }

   public int drawString(Font font, @Nullable String text, float x, float y, int color, boolean shadow) {
      if (text == null) {
         return 0;
      } else {
         int i = font.a(text, x, y, color, shadow, this.e.c().a(), this.f, Font.a.a, 0, 15728880, font.a());
         this.g();
         return i;
      }
   }

   public int b(Font font, C_178_ text, int x, int y, int color) {
      return this.a(font, text, x, y, color, true);
   }

   public int a(Font font, C_178_ text, int x, int y, int color, boolean shadow) {
      return this.drawString(font, text, (float)x, (float)y, color, shadow);
   }

   public int drawString(Font font, C_178_ text, float x, float y, int color, boolean shadow) {
      int i = font.a(text, x, y, color, shadow, this.e.c().a(), this.f, Font.a.a, 0, 15728880);
      this.g();
      return i;
   }

   public int b(Font font, C_4996_ component, int x, int y, int color) {
      return this.a(font, component, x, y, color, true);
   }

   public int a(Font font, C_4996_ component, int x, int y, int color, boolean shadow) {
      return this.a(font, component.m_7532_(), x, y, color, shadow);
   }

   public void a(Font font, C_5000_ text, int x, int y, int width, int color) {
      for (C_178_ formattedcharsequence : font.c(text, width)) {
         this.a(font, formattedcharsequence, x, y, color, false);
         y += 9;
      }
   }

   public int a(Font font, C_4996_ component, int x, int y, int width, int color) {
      int i = this.d.m.b(0.0F);
      if (i != 0) {
         int j = 2;
         this.a(x - 2, y - 2, x + width + 2, y + 9 + 2, C_175_.m_13657_(i, color));
      }

      return this.a(font, component, x, y, color, true);
   }

   public void a(int x, int y, int depth, int width, int height, TextureAtlasSprite spriteIn) {
      this.a(spriteIn, x, y, depth, width, height);
   }

   public void a(int x, int y, int z, int width, int height, TextureAtlasSprite spriteIn, float red, float green, float blue, float alpha) {
      this.a(spriteIn.i(), x, x + width, y, y + height, z, spriteIn.c(), spriteIn.d(), spriteIn.g(), spriteIn.h(), red, green, blue, alpha);
   }

   public void b(int left, int top, int width, int height, int colorIn) {
      this.a(left, top, left + width, top + 1, colorIn);
      this.a(left, top + height - 1, left + width, top + height, colorIn);
      this.a(left, top + 1, left + 1, top + height - 1, colorIn);
      this.a(left + width - 1, top + 1, left + width, top + height - 1, colorIn);
   }

   public void a(ResourceLocation locationIn, int x, int y, int width, int height) {
      this.a(locationIn, x, y, 0, width, height);
   }

   public void a(ResourceLocation locationIn, int x, int y, int depth, int width, int height) {
      TextureAtlasSprite textureatlassprite = this.h.a(locationIn);
      C_290233_ guispritescaling = this.h.a(textureatlassprite);
      if (guispritescaling instanceof C_290199_) {
         this.a(textureatlassprite, x, y, depth, width, height);
      } else if (guispritescaling instanceof C_290163_ guispritescaling$tile) {
         this.a(
            textureatlassprite,
            x,
            y,
            depth,
            width,
            height,
            0,
            0,
            guispritescaling$tile.f_290745_(),
            guispritescaling$tile.f_290706_(),
            guispritescaling$tile.f_290745_(),
            guispritescaling$tile.f_290706_()
         );
      } else if (guispritescaling instanceof C_290255_ guispritescaling$nineslice) {
         this.a(textureatlassprite, guispritescaling$nineslice, x, y, depth, width, height);
      }
   }

   public void a(ResourceLocation locationIn, int sliceWidth, int sliceHeight, int sliceX, int sliceY, int x, int y, int width, int height) {
      this.a(locationIn, sliceWidth, sliceHeight, sliceX, sliceY, x, y, 0, width, height);
   }

   public void a(ResourceLocation locationIn, int sliceWidth, int sliceHeight, int sliceX, int sliceY, int x, int y, int depth, int width, int height) {
      TextureAtlasSprite textureatlassprite = this.h.a(locationIn);
      C_290233_ guispritescaling = this.h.a(textureatlassprite);
      if (guispritescaling instanceof C_290199_) {
         this.a(textureatlassprite, sliceWidth, sliceHeight, sliceX, sliceY, x, y, depth, width, height);
      } else {
         this.a(textureatlassprite, x, y, depth, width, height);
      }
   }

   private void a(TextureAtlasSprite spriteIn, int sliceWidth, int sliceHeight, int sliceX, int sliceY, int x, int y, int depth, int width, int height) {
      if (width != 0 && height != 0) {
         this.a(
            spriteIn.i(),
            x,
            x + width,
            y,
            y + height,
            depth,
            spriteIn.a((float)sliceX / (float)sliceWidth),
            spriteIn.a((float)(sliceX + width) / (float)sliceWidth),
            spriteIn.c((float)sliceY / (float)sliceHeight),
            spriteIn.c((float)(sliceY + height) / (float)sliceHeight)
         );
      }
   }

   private void a(TextureAtlasSprite spriteIn, int x, int y, int depth, int width, int height) {
      if (width != 0 && height != 0) {
         this.a(spriteIn.i(), x, x + width, y, y + height, depth, spriteIn.c(), spriteIn.d(), spriteIn.g(), spriteIn.h());
      }
   }

   public void a(ResourceLocation locationIn, int x, int y, int rectX, int rectY, int width, int height) {
      this.a(locationIn, x, y, 0, (float)rectX, (float)rectY, width, height, 256, 256);
   }

   public void a(ResourceLocation locationIn, int x, int y, int depth, float rectX, float rectY, int width, int height, int texWidth, int texHeight) {
      this.a(locationIn, x, x + width, y, y + height, depth, width, height, rectX, rectY, texWidth, texHeight);
   }

   public void a(
      ResourceLocation locationIn, int x, int y, int width, int height, float rectX, float rectY, int rectWidth, int rectHeight, int texWidth, int texHeight
   ) {
      this.a(locationIn, x, x + width, y, y + height, 0, rectWidth, rectHeight, rectX, rectY, texWidth, texHeight);
   }

   public void a(ResourceLocation locationIn, int x, int y, float rectX, float rectY, int width, int height, int texWidth, int texHeight) {
      this.a(locationIn, x, y, width, height, rectX, rectY, width, height, texWidth, texHeight);
   }

   void a(
      ResourceLocation locationIn,
      int startX,
      int endX,
      int startY,
      int endY,
      int depth,
      int rectWidth,
      int rectHeight,
      float rectX,
      float rectY,
      int texWidth,
      int texHeight
   ) {
      this.a(
         locationIn,
         startX,
         endX,
         startY,
         endY,
         depth,
         (rectX + 0.0F) / (float)texWidth,
         (rectX + (float)rectWidth) / (float)texWidth,
         (rectY + 0.0F) / (float)texHeight,
         (rectY + (float)rectHeight) / (float)texHeight
      );
   }

   void a(ResourceLocation locationIn, int startX, int endX, int startY, int endY, int depth, float minU, float maxU, float minV, float maxV) {
      RenderSystem.setShaderTexture(0, locationIn);
      RenderSystem.setShader(GameRenderer::q);
      Matrix4f matrix4f = this.e.c().a();
      BufferBuilder bufferbuilder = Tesselator.b().a(VertexFormat.c.h, DefaultVertexFormat.i);
      bufferbuilder.a(matrix4f, (float)startX, (float)startY, (float)depth).a(minU, minV);
      bufferbuilder.a(matrix4f, (float)startX, (float)endY, (float)depth).a(minU, maxV);
      bufferbuilder.a(matrix4f, (float)endX, (float)endY, (float)depth).a(maxU, maxV);
      bufferbuilder.a(matrix4f, (float)endX, (float)startY, (float)depth).a(maxU, minV);
      BufferUploader.a(bufferbuilder.b());
   }

   void a(
      ResourceLocation locationIn,
      int startX,
      int endX,
      int startY,
      int endY,
      int depth,
      float minU,
      float maxU,
      float minV,
      float maxV,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      RenderSystem.setShaderTexture(0, locationIn);
      RenderSystem.setShader(GameRenderer::r);
      RenderSystem.enableBlend();
      Matrix4f matrix4f = this.e.c().a();
      BufferBuilder bufferbuilder = Tesselator.b().a(VertexFormat.c.h, DefaultVertexFormat.j);
      bufferbuilder.a(matrix4f, (float)startX, (float)startY, (float)depth).a(minU, minV).a(red, green, blue, alpha);
      bufferbuilder.a(matrix4f, (float)startX, (float)endY, (float)depth).a(minU, maxV).a(red, green, blue, alpha);
      bufferbuilder.a(matrix4f, (float)endX, (float)endY, (float)depth).a(maxU, maxV).a(red, green, blue, alpha);
      bufferbuilder.a(matrix4f, (float)endX, (float)startY, (float)depth).a(maxU, minV).a(red, green, blue, alpha);
      BufferUploader.a(bufferbuilder.b());
      RenderSystem.disableBlend();
   }

   private void a(TextureAtlasSprite spriteIn, C_290255_ sliceIn, int x, int y, int depth, int width, int height) {
      C_290040_ guispritescaling$nineslice$border = sliceIn.f_291546_();
      int i = Math.min(guispritescaling$nineslice$border.f_291443_(), width / 2);
      int j = Math.min(guispritescaling$nineslice$border.f_290714_(), width / 2);
      int k = Math.min(guispritescaling$nineslice$border.f_291022_(), height / 2);
      int l = Math.min(guispritescaling$nineslice$border.f_290453_(), height / 2);
      if (width == sliceIn.f_291615_() && height == sliceIn.f_291235_()) {
         this.a(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, 0, x, y, depth, width, height);
      } else if (height == sliceIn.f_291235_()) {
         this.a(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, 0, x, y, depth, i, height);
         this.a(
            spriteIn, x + i, y, depth, width - j - i, height, i, 0, sliceIn.f_291615_() - j - i, sliceIn.f_291235_(), sliceIn.f_291615_(), sliceIn.f_291235_()
         );
         this.a(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), sliceIn.f_291615_() - j, 0, x + width - j, y, depth, j, height);
      } else if (width == sliceIn.f_291615_()) {
         this.a(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, 0, x, y, depth, width, k);
         this.a(
            spriteIn, x, y + k, depth, width, height - l - k, 0, k, sliceIn.f_291615_(), sliceIn.f_291235_() - l - k, sliceIn.f_291615_(), sliceIn.f_291235_()
         );
         this.a(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, sliceIn.f_291235_() - l, x, y + height - l, depth, width, l);
      } else {
         this.a(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, 0, x, y, depth, i, k);
         this.a(spriteIn, x + i, y, depth, width - j - i, k, i, 0, sliceIn.f_291615_() - j - i, k, sliceIn.f_291615_(), sliceIn.f_291235_());
         this.a(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), sliceIn.f_291615_() - j, 0, x + width - j, y, depth, j, k);
         this.a(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, sliceIn.f_291235_() - l, x, y + height - l, depth, i, l);
         this.a(
            spriteIn,
            x + i,
            y + height - l,
            depth,
            width - j - i,
            l,
            i,
            sliceIn.f_291235_() - l,
            sliceIn.f_291615_() - j - i,
            l,
            sliceIn.f_291615_(),
            sliceIn.f_291235_()
         );
         this.a(
            spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), sliceIn.f_291615_() - j, sliceIn.f_291235_() - l, x + width - j, y + height - l, depth, j, l
         );
         this.a(spriteIn, x, y + k, depth, i, height - l - k, 0, k, i, sliceIn.f_291235_() - l - k, sliceIn.f_291615_(), sliceIn.f_291235_());
         this.a(
            spriteIn,
            x + i,
            y + k,
            depth,
            width - j - i,
            height - l - k,
            i,
            k,
            sliceIn.f_291615_() - j - i,
            sliceIn.f_291235_() - l - k,
            sliceIn.f_291615_(),
            sliceIn.f_291235_()
         );
         this.a(
            spriteIn,
            x + width - j,
            y + k,
            depth,
            i,
            height - l - k,
            sliceIn.f_291615_() - j,
            k,
            j,
            sliceIn.f_291235_() - l - k,
            sliceIn.f_291615_(),
            sliceIn.f_291235_()
         );
      }
   }

   private void a(
      TextureAtlasSprite spriteIn,
      int x,
      int y,
      int depth,
      int width,
      int height,
      int sliceX,
      int sliceY,
      int texWidth,
      int texHeight,
      int sliceWidth,
      int sliceHeight
   ) {
      if (width > 0 && height > 0) {
         if (texWidth <= 0 || texHeight <= 0) {
            throw new IllegalArgumentException("Tiled sprite texture size must be positive, got " + texWidth + "x" + texHeight);
         }

         for (int i = 0; i < width; i += texWidth) {
            int j = Math.min(texWidth, width - i);

            for (int k = 0; k < height; k += texHeight) {
               int l = Math.min(texHeight, height - k);
               this.a(spriteIn, sliceWidth, sliceHeight, sliceX, sliceY, x + i, y + k, depth, j, l);
            }
         }
      }
   }

   public void a(C_1391_ stackIn, int xIn, int yIn) {
      this.a(this.d.f_91074_, this.d.r, stackIn, xIn, yIn, 0);
   }

   public void a(C_1391_ stackIn, int xIn, int yIn, int seedIn) {
      this.a(this.d.f_91074_, this.d.r, stackIn, xIn, yIn, seedIn);
   }

   public void a(C_1391_ stackIn, int xIn, int yIn, int seedIn, int depthIn) {
      this.a(this.d.f_91074_, this.d.r, stackIn, xIn, yIn, seedIn, depthIn);
   }

   public void b(C_1391_ stackIn, int xIn, int yIn) {
      this.b(stackIn, xIn, yIn, 0);
   }

   public void b(C_1391_ stackIn, int xIn, int yIn, int seedIn) {
      this.a(null, this.d.r, stackIn, xIn, yIn, seedIn);
   }

   public void a(C_524_ entityIn, C_1391_ stackIn, int xIn, int yIn, int seedIn) {
      this.a(entityIn, entityIn.m_9236_(), stackIn, xIn, yIn, seedIn);
   }

   private void a(@Nullable C_524_ entityIn, @Nullable C_1596_ worldIn, C_1391_ stackIn, int xIn, int yIn, int seedIn) {
      this.a(entityIn, worldIn, stackIn, xIn, yIn, seedIn, 0);
   }

   private void a(@Nullable C_524_ entityIn, @Nullable C_1596_ worldIn, C_1391_ stackIn, int xIn, int yIn, int seedIn, int depthIn) {
      ItemRenderer.setRenderItemGui(true);
      if (!stackIn.m_41619_()) {
         BakedModel bakedmodel = this.d.ar().a(stackIn, worldIn, entityIn, seedIn);
         this.e.a();
         this.e.a((float)(xIn + 8), (float)(yIn + 8), (float)(150 + (bakedmodel.b() ? depthIn : 0)));

         try {
            this.e.b(16.0F, -16.0F, 16.0F);
            boolean flag = !bakedmodel.c();
            if (flag) {
               C_3144_.m_84930_();
            }

            this.d.ar().a(stackIn, C_268388_.GUI, false, this.e, this.d(), 15728880, C_4474_.f_118083_, bakedmodel);
            this.e();
            if (flag) {
               C_3144_.m_84931_();
            }
         } catch (Throwable var12) {
            CrashReport crashreport = CrashReport.a(var12, "Rendering item");
            C_4909_ crashreportcategory = crashreport.a("Item being rendered");
            crashreportcategory.m_128165_("Item Type", () -> String.valueOf(stackIn.m_41720_()));
            crashreportcategory.m_128165_("Item Components", () -> String.valueOf(stackIn.m_318732_()));
            crashreportcategory.m_128165_("Item Foil", () -> String.valueOf(stackIn.m_41790_()));
            throw new C_5204_(crashreport);
         }

         this.e.b();
      }

      ItemRenderer.setRenderItemGui(false);
   }

   public void a(Font fontIn, C_1391_ stackIn, int xIn, int yIn) {
      this.a(fontIn, stackIn, xIn, yIn, null);
   }

   public void a(Font fontIn, C_1391_ stackIn, int xIn, int yIn, @Nullable String countStringIn) {
      if (!stackIn.m_41619_()) {
         this.e.a();
         if (stackIn.m_41613_() != 1 || countStringIn != null) {
            String s = countStringIn == null ? String.valueOf(stackIn.m_41613_()) : countStringIn;
            this.e.a(0.0F, 0.0F, 200.0F);
            this.a(fontIn, s, xIn + 19 - 2 - fontIn.b(s), yIn + 6 + 3, 16777215, true);
         }

         if (stackIn.m_150947_()) {
            int l = stackIn.m_150948_();
            int i = stackIn.m_150949_();
            if (Config.isCustomColors()) {
               float dmg = (float)stackIn.m_41773_();
               float maxDmg = (float)stackIn.m_41776_();
               float durability = Math.max(0.0F, (maxDmg - dmg) / maxDmg);
               i = CustomColors.getDurabilityColor(durability, i);
            }

            int j = xIn + 2;
            int k = yIn + 13;
            this.a(RenderType.F(), j, k, j + 13, k + 2, -16777216);
            this.a(RenderType.F(), j, k, j + l, k + 1, i | 0xFF000000);
         }

         C_4105_ localplayer = this.d.f_91074_;
         float f = localplayer == null ? 0.0F : localplayer.gt().m_41521_(stackIn.m_41720_(), this.d.m_338668_().m_338527_(true));
         if (f > 0.0F) {
            int i1 = yIn + Mth.d(16.0F * (1.0F - f));
            int j1 = i1 + Mth.f(16.0F * f);
            this.a(RenderType.F(), xIn, i1, xIn + 16, j1, Integer.MAX_VALUE);
         }

         this.e.b();
         if (Reflector.ItemDecoratorHandler_render.exists()) {
            Object idh = Reflector.call(Reflector.ItemDecoratorHandler_of, stackIn);
            Reflector.call(idh, Reflector.ItemDecoratorHandler_render, this, fontIn, stackIn, xIn, yIn);
         }
      }
   }

   public void b(Font fontIn, C_1391_ stackIn, int xIn, int yIn) {
      this.tooltipStack = stackIn;
      this.a(fontIn, C_3583_.m_280152_(this.d, stackIn), stackIn.m_150921_(), xIn, yIn);
      this.tooltipStack = C_1391_.f_41583_;
   }

   public void renderTooltip(Font font, List<C_4996_> textComponents, Optional<C_141167_> tooltipComponent, C_1391_ stack, int mouseX, int mouseY) {
      this.tooltipStack = stack;
      this.a(font, textComponents, tooltipComponent, mouseX, mouseY);
      this.tooltipStack = C_1391_.f_41583_;
   }

   public void a(Font fontIn, List<C_4996_> listIn, Optional<C_141167_> componentIn, int xIn, int yIn) {
      List<C_141633_> list = (List<C_141633_>)listIn.stream().map(C_4996_::m_7532_).map(C_141633_::m_169948_).collect(Util.b());
      componentIn.ifPresent(compIn -> list.add(list.isEmpty() ? 0 : 1, C_141633_.m_169950_(compIn)));
      if (Reflector.ForgeHooksClient_gatherTooltipComponents7.exists()) {
         List listForge = (List)Reflector.ForgeHooksClient_gatherTooltipComponents7
            .call(this.tooltipStack, listIn, componentIn, xIn, this.a(), this.b(), fontIn);
         list.clear();
         list.addAll(listForge);
      }

      this.a(fontIn, list, xIn, yIn, C_262721_.f_262752_);
   }

   public void a(Font fontIn, C_4996_ componentIn, int xIn, int yIn) {
      this.b(fontIn, List.of(componentIn.m_7532_()), xIn, yIn);
   }

   public void a(Font fontIn, List<C_4996_> componentsIn, int xIn, int yIn) {
      if (Reflector.ForgeHooksClient_gatherTooltipComponents6.exists()) {
         List<C_141633_> components = (List<C_141633_>)Reflector.ForgeHooksClient_gatherTooltipComponents6
            .call(this.tooltipStack, componentsIn, xIn, this.a(), this.b(), fontIn);
         this.a(fontIn, components, xIn, yIn, C_262721_.f_262752_);
      } else {
         this.b(fontIn, Lists.transform(componentsIn, C_4996_::m_7532_), xIn, yIn);
      }
   }

   public void renderComponentTooltip(Font font, List<? extends C_5000_> tooltips, int mouseX, int mouseY, C_1391_ stack) {
      this.tooltipStack = stack;
      List<C_141633_> components = (List<C_141633_>)Reflector.ForgeHooksClient_gatherTooltipComponents6.call(stack, tooltips, mouseX, this.a(), this.b(), font);
      this.a(font, components, mouseX, mouseY, C_262721_.f_262752_);
      this.tooltipStack = C_1391_.f_41583_;
   }

   public void b(Font fontIn, List<? extends C_178_> listIn, int xIn, int yIn) {
      this.a(fontIn, (List<C_141633_>)listIn.stream().map(C_141633_::m_169948_).collect(Collectors.toList()), xIn, yIn, C_262721_.f_262752_);
   }

   public void a(Font fontIn, List<C_178_> listIn, C_262719_ positionerIn, int xIn, int yIn) {
      this.a(fontIn, (List<C_141633_>)listIn.stream().map(C_141633_::m_169948_).collect(Collectors.toList()), xIn, yIn, positionerIn);
   }

   private void a(Font fontIn, List<C_141633_> listIn, int xIn, int yIn, C_262719_ positionerIn) {
      if (!listIn.isEmpty()) {
         Object preEvent = null;
         if (Reflector.ForgeHooksClient_onRenderTooltipPre.exists()) {
            preEvent = Reflector.ForgeHooksClient_onRenderTooltipPre.call(this.tooltipStack, this, xIn, yIn, this.a(), this.b(), listIn, fontIn, positionerIn);
            if (Reflector.callBoolean(preEvent, Reflector.Event_isCanceled)) {
               return;
            }
         }

         int i = 0;
         int j = listIn.size() == 1 ? -2 : 0;

         for (C_141633_ clienttooltipcomponent : listIn) {
            if (preEvent != null) {
               fontIn = (Font)Reflector.call(preEvent, Reflector.RenderTooltipEvent_getFont);
            }

            int k = clienttooltipcomponent.a(fontIn);
            if (k > i) {
               i = k;
            }

            j += clienttooltipcomponent.m_142103_();
         }

         int i2 = i;
         int j2 = j;
         if (preEvent != null) {
            xIn = Reflector.callInt(preEvent, Reflector.RenderTooltipEvent_getX);
            yIn = Reflector.callInt(preEvent, Reflector.RenderTooltipEvent_getY);
         }

         Vector2ic vector2ic = positionerIn.m_262814_(this.a(), this.b(), xIn, yIn, i2, j2);
         int l = vector2ic.x();
         int i1 = vector2ic.y();
         this.e.a();
         int j1 = 400;
         Font forgeFont = fontIn;
         this.a((Runnable)(() -> {
            if (Reflector.ForgeHooksClient_onRenderTooltipColor.exists()) {
               Object colorEvent = Reflector.ForgeHooksClient_onRenderTooltipColor.call(this.tooltipStack, this, l, i1, forgeFont, listIn);
               int backStart = Reflector.callInt(colorEvent, Reflector.RenderTooltipEvent_Color_getBackgroundStart);
               int backEnd = Reflector.callInt(colorEvent, Reflector.RenderTooltipEvent_Color_getBackgroundEnd);
               int borderStart = Reflector.callInt(colorEvent, Reflector.RenderTooltipEvent_Color_getBorderStart);
               int borderEnd = Reflector.callInt(colorEvent, Reflector.RenderTooltipEvent_Color_getBorderEnd);
               Reflector.TooltipRenderUtil_renderTooltipBackground10.call(this, l, i1, i2, j2, 400, backStart, backEnd, borderStart, borderEnd);
            } else {
               C_262715_.a(this, l, i1, i2, j2, 400);
            }
         }));
         this.e.a(0.0F, 0.0F, 400.0F);
         int k1 = i1;

         for (int l1 = 0; l1 < listIn.size(); l1++) {
            C_141633_ clienttooltipcomponent1 = (C_141633_)listIn.get(l1);
            clienttooltipcomponent1.a(fontIn, l, k1, this.e.c().a(), this.f);
            k1 += clienttooltipcomponent1.m_142103_() + (l1 == 0 ? 2 : 0);
         }

         k1 = i1;

         for (int k2 = 0; k2 < listIn.size(); k2++) {
            C_141633_ clienttooltipcomponent2 = (C_141633_)listIn.get(k2);
            clienttooltipcomponent2.a(fontIn, l, k1, this);
            k1 += clienttooltipcomponent2.m_142103_() + (k2 == 0 ? 2 : 0);
         }

         this.e.b();
      }
   }

   public void a(Font fontIn, @Nullable C_5020_ styleIn, int xIn, int yIn) {
      if (styleIn != null && styleIn.m_131186_() != null) {
         C_5007_ hoverevent = styleIn.m_131186_();
         C_5010_ hoverevent$itemstackinfo = (C_5010_)hoverevent.m_130823_(C_5008_.f_130832_);
         if (hoverevent$itemstackinfo != null) {
            this.b(fontIn, hoverevent$itemstackinfo.m_130898_(), xIn, yIn);
         } else {
            C_5009_ hoverevent$entitytooltipinfo = (C_5009_)hoverevent.m_130823_(C_5008_.f_130833_);
            if (hoverevent$entitytooltipinfo != null) {
               if (this.d.m.m) {
                  this.a(fontIn, hoverevent$entitytooltipinfo.m_130884_(), xIn, yIn);
               }
            } else {
               C_4996_ component = (C_4996_)hoverevent.m_130823_(C_5008_.f_130831_);
               if (component != null) {
                  this.b(fontIn, fontIn.c(component, Math.max(this.a() / 2, 200)), xIn, yIn);
               }
            }
         }
      }
   }

   public void getBulkData(RenderType renderType, ByteBuffer buffer) {
      if (renderType != null) {
         VertexConsumer builder = this.f.getBuffer(renderType);
         if (builder.getVertexCount() > 0) {
            builder.getBulkData(buffer);
         }
      }
   }

   public void putBulkData(RenderType renderType, ByteBuffer buffer) {
      if (renderType != null) {
         if (buffer.position() < buffer.limit()) {
            VertexConsumer builder = this.f.getBuffer(renderType);
            builder.putBulkData(buffer);
         }
      }
   }

   static class a {
      private final Deque<C_263595_> a = new ArrayDeque();

      public C_263595_ a(C_263595_ rectIn) {
         C_263595_ screenrectangle = (C_263595_)this.a.peekLast();
         if (screenrectangle != null) {
            C_263595_ screenrectangle1 = (C_263595_)Objects.requireNonNullElse(rectIn.m_275842_(screenrectangle), C_263595_.m_264427_());
            this.a.addLast(screenrectangle1);
            return screenrectangle1;
         } else {
            this.a.addLast(rectIn);
            return rectIn;
         }
      }

      @Nullable
      public C_263595_ a() {
         if (this.a.isEmpty()) {
            throw new IllegalStateException("Scissor stack underflow");
         } else {
            this.a.removeLast();
            return (C_263595_)this.a.peekLast();
         }
      }

      public boolean a(int x, int y) {
         return this.a.isEmpty() ? true : ((C_263595_)this.a.peek()).m_319431_(x, y);
      }
   }
}
