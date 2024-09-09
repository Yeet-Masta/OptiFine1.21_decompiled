package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.IForgeGuiGraphics;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import org.joml.Matrix4f;
import org.joml.Vector2ic;

public class GuiGraphics implements IForgeGuiGraphics {
   public static final float f_289044_ = 10000.0F;
   public static final float f_289038_ = -10000.0F;
   private static final int f_279564_ = 2;
   private final Minecraft f_279544_;
   private final PoseStack f_279612_;
   private final MultiBufferSource.BufferSource f_279627_;
   private final ScissorStack f_279587_;
   private final GuiSpriteManager f_291876_;
   private boolean f_285610_;
   private ItemStack tooltipStack;

   private GuiGraphics(Minecraft minecraftIn, PoseStack matrixStackIn, MultiBufferSource.BufferSource bufferSourceIn) {
      this.f_279587_ = new ScissorStack();
      this.tooltipStack = ItemStack.f_41583_;
      this.f_279544_ = minecraftIn;
      this.f_279612_ = matrixStackIn;
      this.f_279627_ = bufferSourceIn;
      this.f_291876_ = minecraftIn.m_292761_();
   }

   public GuiGraphics(Minecraft minecraftIn, MultiBufferSource.BufferSource bufferSourceIn) {
      this(minecraftIn, new PoseStack(), bufferSourceIn);
   }

   /** @deprecated */
   @Deprecated
   public void m_286007_(Runnable runnableIn) {
      this.m_280262_();
      this.f_285610_ = true;
      runnableIn.run();
      this.f_285610_ = false;
      this.m_280262_();
   }

   /** @deprecated */
   @Deprecated
   private void m_286081_() {
      if (!this.f_285610_) {
         this.m_280262_();
      }

   }

   /** @deprecated */
   @Deprecated
   private void m_287246_() {
      if (this.f_285610_) {
         this.m_280262_();
      }

   }

   public int m_280182_() {
      return this.f_279544_.m_91268_().m_85445_();
   }

   public int m_280206_() {
      return this.f_279544_.m_91268_().m_85446_();
   }

   public PoseStack m_280168_() {
      return this.f_279612_;
   }

   public MultiBufferSource.BufferSource m_280091_() {
      return this.f_279627_;
   }

   public void m_280262_() {
      RenderSystem.disableDepthTest();
      this.f_279627_.m_109911_();
      RenderSystem.enableDepthTest();
   }

   public void m_280656_(int startX, int endX, int y, int colorIn) {
      this.m_285844_(RenderType.m_285907_(), startX, endX, y, colorIn);
   }

   public void m_285844_(RenderType renderTypeIn, int startX, int endX, int y, int colorIn) {
      if (endX < startX) {
         int i = startX;
         startX = endX;
         endX = i;
      }

      this.m_285944_(renderTypeIn, startX, y, endX + 1, y + 1, colorIn);
   }

   public void m_280315_(int x, int startY, int endY, int colorIn) {
      this.m_285886_(RenderType.m_285907_(), x, startY, endY, colorIn);
   }

   public void m_285886_(RenderType renderTypeIn, int x, int startY, int endY, int colorIn) {
      if (endY < startY) {
         int i = startY;
         startY = endY;
         endY = i;
      }

      this.m_285944_(renderTypeIn, x, startY + 1, x + 1, endY, colorIn);
   }

   public void m_280588_(int left, int top, int right, int bottom) {
      this.m_280185_(this.f_279587_.m_280318_(new ScreenRectangle(left, top, right - left, bottom - top)));
   }

   public void m_280618_() {
      this.m_280185_(this.f_279587_.m_280462_());
   }

   public boolean m_322958_(int x, int y) {
      return this.f_279587_.m_319411_(x, y);
   }

   private void m_280185_(@Nullable ScreenRectangle rectIn) {
      this.m_287246_();
      if (rectIn != null) {
         Window window = Minecraft.m_91087_().m_91268_();
         int i = window.m_85442_();
         double d0 = window.m_85449_();
         double d1 = (double)rectIn.m_274563_() * d0;
         double d2 = (double)i - (double)rectIn.m_274349_() * d0;
         double d3 = (double)rectIn.f_263770_() * d0;
         double d4 = (double)rectIn.f_263800_() * d0;
         RenderSystem.enableScissor((int)d1, (int)d2, Math.max(0, (int)d3), Math.max(0, (int)d4));
      } else {
         RenderSystem.disableScissor();
      }

   }

   public void m_280246_(float red, float green, float blue, float alpha) {
      this.m_287246_();
      RenderSystem.setShaderColor(red, green, blue, alpha);
   }

   public void m_280509_(int left, int top, int right, int bottom, int colorIn) {
      this.m_280046_(left, top, right, bottom, 0, colorIn);
   }

   public void m_280046_(int left, int top, int right, int bottom, int z, int colorIn) {
      this.m_285795_(RenderType.m_285907_(), left, top, right, bottom, z, colorIn);
   }

   public void m_285944_(RenderType renderTypeIn, int left, int top, int right, int bottom, int colorIn) {
      this.m_285795_(renderTypeIn, left, top, right, bottom, 0, colorIn);
   }

   public void m_285795_(RenderType renderTypeIn, int left, int top, int right, int bottom, int z, int colorIn) {
      Matrix4f matrix4f = this.f_279612_.m_85850_().m_252922_();
      int j;
      if (left < right) {
         j = left;
         left = right;
         right = j;
      }

      if (top < bottom) {
         j = top;
         top = bottom;
         bottom = j;
      }

      VertexConsumer vertexconsumer = this.f_279627_.m_6299_(renderTypeIn);
      vertexconsumer.m_339083_(matrix4f, (float)left, (float)top, (float)z).m_338399_(colorIn);
      vertexconsumer.m_339083_(matrix4f, (float)left, (float)bottom, (float)z).m_338399_(colorIn);
      vertexconsumer.m_339083_(matrix4f, (float)right, (float)bottom, (float)z).m_338399_(colorIn);
      vertexconsumer.m_339083_(matrix4f, (float)right, (float)top, (float)z).m_338399_(colorIn);
      this.m_286081_();
   }

   public void m_280024_(int left, int top, int right, int bottom, int startColor, int endColor) {
      this.m_280120_(left, top, right, bottom, 0, startColor, endColor);
   }

   public void m_280120_(int left, int top, int right, int bottom, int z, int startColor, int endColor) {
      this.m_285978_(RenderType.m_285907_(), left, top, right, bottom, startColor, endColor, z);
   }

   public void m_285978_(RenderType renderTypeIn, int left, int top, int right, int bottom, int startColor, int endColor, int z) {
      VertexConsumer vertexconsumer = this.f_279627_.m_6299_(renderTypeIn);
      this.m_280584_(vertexconsumer, left, top, right, bottom, z, startColor, endColor);
      this.m_286081_();
   }

   private void m_280584_(VertexConsumer bufferIn, int left, int top, int right, int bottom, int z, int startColor, int endColor) {
      Matrix4f matrix4f = this.f_279612_.m_85850_().m_252922_();
      bufferIn.m_339083_(matrix4f, (float)left, (float)top, (float)z).m_338399_(startColor);
      bufferIn.m_339083_(matrix4f, (float)left, (float)bottom, (float)z).m_338399_(endColor);
      bufferIn.m_339083_(matrix4f, (float)right, (float)bottom, (float)z).m_338399_(endColor);
      bufferIn.m_339083_(matrix4f, (float)right, (float)top, (float)z).m_338399_(startColor);
   }

   public void m_319756_(RenderType renderTypeIn, int x, int y, int width, int height, int z) {
      Matrix4f matrix4f = this.f_279612_.m_85850_().m_252922_();
      VertexConsumer vertexconsumer = this.f_279627_.m_6299_(renderTypeIn);
      vertexconsumer.m_339083_(matrix4f, (float)x, (float)y, (float)z);
      vertexconsumer.m_339083_(matrix4f, (float)x, (float)height, (float)z);
      vertexconsumer.m_339083_(matrix4f, (float)width, (float)height, (float)z);
      vertexconsumer.m_339083_(matrix4f, (float)width, (float)y, (float)z);
      this.m_286081_();
   }

   public void m_280137_(Font font, String text, int x, int y, int color) {
      this.m_280488_(font, text, x - font.m_92895_(text) / 2, y, color);
   }

   public void m_280653_(Font font, Component text, int x, int y, int color) {
      FormattedCharSequence formattedcharsequence = text.m_7532_();
      this.m_280648_(font, formattedcharsequence, x - font.m_92724_(formattedcharsequence) / 2, y, color);
   }

   public void m_280364_(Font font, FormattedCharSequence text, int x, int y, int color) {
      this.m_280648_(font, text, x - font.m_92724_(text) / 2, y, color);
   }

   public int m_280488_(Font font, @Nullable String text, int x, int y, int color) {
      return this.m_280056_(font, text, x, y, color, true);
   }

   public int m_280056_(Font font, @Nullable String text, int x, int y, int color, boolean shadow) {
      return this.drawString(font, text, (float)x, (float)y, color, shadow);
   }

   public int drawString(Font font, @Nullable String text, float x, float y, int color, boolean shadow) {
      if (text == null) {
         return 0;
      } else {
         int i = font.m_272078_(text, x, y, color, shadow, this.f_279612_.m_85850_().m_252922_(), this.f_279627_, Font.DisplayMode.NORMAL, 0, 15728880, font.m_92718_());
         this.m_286081_();
         return i;
      }
   }

   public int m_280648_(Font font, FormattedCharSequence text, int x, int y, int color) {
      return this.m_280649_(font, text, x, y, color, true);
   }

   public int m_280649_(Font font, FormattedCharSequence text, int x, int y, int color, boolean shadow) {
      return this.drawString(font, text, (float)x, (float)y, color, shadow);
   }

   public int drawString(Font font, FormattedCharSequence text, float x, float y, int color, boolean shadow) {
      int i = font.m_272191_(text, x, y, color, shadow, this.f_279612_.m_85850_().m_252922_(), this.f_279627_, Font.DisplayMode.NORMAL, 0, 15728880);
      this.m_286081_();
      return i;
   }

   public int m_280430_(Font font, Component component, int x, int y, int color) {
      return this.m_280614_(font, component, x, y, color, true);
   }

   public int m_280614_(Font font, Component component, int x, int y, int color, boolean shadow) {
      return this.m_280649_(font, component.m_7532_(), x, y, color, shadow);
   }

   public void m_280554_(Font font, FormattedText text, int x, int y, int width, int color) {
      for(Iterator var7 = font.m_92923_(text, width).iterator(); var7.hasNext(); y += 9) {
         FormattedCharSequence formattedcharsequence = (FormattedCharSequence)var7.next();
         this.m_280649_(font, formattedcharsequence, x, y, color, false);
      }

   }

   public int m_339210_(Font font, Component component, int x, int y, int width, int color) {
      int i = this.f_279544_.f_91066_.m_92170_(0.0F);
      if (i != 0) {
         int j = true;
         this.m_280509_(x - 2, y - 2, x + width + 2, y + 9 + 2, ARGB32.m_13657_(i, color));
      }

      return this.m_280614_(font, component, x, y, color, true);
   }

   public void m_280159_(int x, int y, int depth, int width, int height, TextureAtlasSprite spriteIn) {
      this.m_294769_(spriteIn, x, y, depth, width, height);
   }

   public void m_280565_(int x, int y, int z, int width, int height, TextureAtlasSprite spriteIn, float red, float green, float blue, float alpha) {
      this.m_280479_(spriteIn.m_247685_(), x, x + width, y, y + height, z, spriteIn.m_118409_(), spriteIn.m_118410_(), spriteIn.m_118411_(), spriteIn.m_118412_(), red, green, blue, alpha);
   }

   public void m_280637_(int left, int top, int width, int height, int colorIn) {
      this.m_280509_(left, top, left + width, top + 1, colorIn);
      this.m_280509_(left, top + height - 1, left + width, top + height, colorIn);
      this.m_280509_(left, top + 1, left + 1, top + height - 1, colorIn);
      this.m_280509_(left + width - 1, top + 1, left + width, top + height - 1, colorIn);
   }

   public void m_292816_(ResourceLocation locationIn, int x, int y, int width, int height) {
      this.m_295520_(locationIn, x, y, 0, width, height);
   }

   public void m_295520_(ResourceLocation locationIn, int x, int y, int depth, int width, int height) {
      TextureAtlasSprite textureatlassprite = this.f_291876_.m_118901_(locationIn);
      GuiSpriteScaling guispritescaling = this.f_291876_.m_292877_(textureatlassprite);
      if (guispritescaling instanceof GuiSpriteScaling.Stretch) {
         this.m_294769_(textureatlassprite, x, y, depth, width, height);
      } else if (guispritescaling instanceof GuiSpriteScaling.Tile) {
         GuiSpriteScaling.Tile guispritescaling$tile = (GuiSpriteScaling.Tile)guispritescaling;
         this.m_295409_(textureatlassprite, x, y, depth, width, height, 0, 0, guispritescaling$tile.f_290745_(), guispritescaling$tile.f_290706_(), guispritescaling$tile.f_290745_(), guispritescaling$tile.f_290706_());
      } else if (guispritescaling instanceof GuiSpriteScaling.NineSlice) {
         GuiSpriteScaling.NineSlice guispritescaling$nineslice = (GuiSpriteScaling.NineSlice)guispritescaling;
         this.m_293720_(textureatlassprite, guispritescaling$nineslice, x, y, depth, width, height);
      }

   }

   public void m_294122_(ResourceLocation locationIn, int sliceWidth, int sliceHeight, int sliceX, int sliceY, int x, int y, int width, int height) {
      this.m_293068_(locationIn, sliceWidth, sliceHeight, sliceX, sliceY, x, y, 0, width, height);
   }

   public void m_293068_(ResourceLocation locationIn, int sliceWidth, int sliceHeight, int sliceX, int sliceY, int x, int y, int depth, int width, int height) {
      TextureAtlasSprite textureatlassprite = this.f_291876_.m_118901_(locationIn);
      GuiSpriteScaling guispritescaling = this.f_291876_.m_292877_(textureatlassprite);
      if (guispritescaling instanceof GuiSpriteScaling.Stretch) {
         this.m_293540_(textureatlassprite, sliceWidth, sliceHeight, sliceX, sliceY, x, y, depth, width, height);
      } else {
         this.m_294769_(textureatlassprite, x, y, depth, width, height);
      }

   }

   private void m_293540_(TextureAtlasSprite spriteIn, int sliceWidth, int sliceHeight, int sliceX, int sliceY, int x, int y, int depth, int width, int height) {
      if (width != 0 && height != 0) {
         this.m_280444_(spriteIn.m_247685_(), x, x + width, y, y + height, depth, spriteIn.m_118367_((float)sliceX / (float)sliceWidth), spriteIn.m_118367_((float)(sliceX + width) / (float)sliceWidth), spriteIn.m_118393_((float)sliceY / (float)sliceHeight), spriteIn.m_118393_((float)(sliceY + height) / (float)sliceHeight));
      }

   }

   private void m_294769_(TextureAtlasSprite spriteIn, int x, int y, int depth, int width, int height) {
      if (width != 0 && height != 0) {
         this.m_280444_(spriteIn.m_247685_(), x, x + width, y, y + height, depth, spriteIn.m_118409_(), spriteIn.m_118410_(), spriteIn.m_118411_(), spriteIn.m_118412_());
      }

   }

   public void m_280218_(ResourceLocation locationIn, int x, int y, int rectX, int rectY, int width, int height) {
      this.m_280398_(locationIn, x, y, 0, (float)rectX, (float)rectY, width, height, 256, 256);
   }

   public void m_280398_(ResourceLocation locationIn, int x, int y, int depth, float rectX, float rectY, int width, int height, int texWidth, int texHeight) {
      this.m_280312_(locationIn, x, x + width, y, y + height, depth, width, height, rectX, rectY, texWidth, texHeight);
   }

   public void m_280411_(ResourceLocation locationIn, int x, int y, int width, int height, float rectX, float rectY, int rectWidth, int rectHeight, int texWidth, int texHeight) {
      this.m_280312_(locationIn, x, x + width, y, y + height, 0, rectWidth, rectHeight, rectX, rectY, texWidth, texHeight);
   }

   public void m_280163_(ResourceLocation locationIn, int x, int y, float rectX, float rectY, int width, int height, int texWidth, int texHeight) {
      this.m_280411_(locationIn, x, y, width, height, rectX, rectY, width, height, texWidth, texHeight);
   }

   void m_280312_(ResourceLocation locationIn, int startX, int endX, int startY, int endY, int depth, int rectWidth, int rectHeight, float rectX, float rectY, int texWidth, int texHeight) {
      this.m_280444_(locationIn, startX, endX, startY, endY, depth, (rectX + 0.0F) / (float)texWidth, (rectX + (float)rectWidth) / (float)texWidth, (rectY + 0.0F) / (float)texHeight, (rectY + (float)rectHeight) / (float)texHeight);
   }

   void m_280444_(ResourceLocation locationIn, int startX, int endX, int startY, int endY, int depth, float minU, float maxU, float minV, float maxV) {
      RenderSystem.setShaderTexture(0, locationIn);
      RenderSystem.setShader(GameRenderer::m_172817_);
      Matrix4f matrix4f = this.f_279612_.m_85850_().m_252922_();
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
      bufferbuilder.m_339083_(matrix4f, (float)startX, (float)startY, (float)depth).m_167083_(minU, minV);
      bufferbuilder.m_339083_(matrix4f, (float)startX, (float)endY, (float)depth).m_167083_(minU, maxV);
      bufferbuilder.m_339083_(matrix4f, (float)endX, (float)endY, (float)depth).m_167083_(maxU, maxV);
      bufferbuilder.m_339083_(matrix4f, (float)endX, (float)startY, (float)depth).m_167083_(maxU, minV);
      BufferUploader.m_231202_(bufferbuilder.m_339905_());
   }

   void m_280479_(ResourceLocation locationIn, int startX, int endX, int startY, int endY, int depth, float minU, float maxU, float minV, float maxV, float red, float green, float blue, float alpha) {
      RenderSystem.setShaderTexture(0, locationIn);
      RenderSystem.setShader(GameRenderer::m_172820_);
      RenderSystem.enableBlend();
      Matrix4f matrix4f = this.f_279612_.m_85850_().m_252922_();
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
      bufferbuilder.m_339083_(matrix4f, (float)startX, (float)startY, (float)depth).m_167083_(minU, minV).m_340057_(red, green, blue, alpha);
      bufferbuilder.m_339083_(matrix4f, (float)startX, (float)endY, (float)depth).m_167083_(minU, maxV).m_340057_(red, green, blue, alpha);
      bufferbuilder.m_339083_(matrix4f, (float)endX, (float)endY, (float)depth).m_167083_(maxU, maxV).m_340057_(red, green, blue, alpha);
      bufferbuilder.m_339083_(matrix4f, (float)endX, (float)startY, (float)depth).m_167083_(maxU, minV).m_340057_(red, green, blue, alpha);
      BufferUploader.m_231202_(bufferbuilder.m_339905_());
      RenderSystem.disableBlend();
   }

   private void m_293720_(TextureAtlasSprite spriteIn, GuiSpriteScaling.NineSlice sliceIn, int x, int y, int depth, int width, int height) {
      GuiSpriteScaling.NineSlice.Border guispritescaling$nineslice$border = sliceIn.f_291546_();
      int i = Math.min(guispritescaling$nineslice$border.f_291443_(), width / 2);
      int j = Math.min(guispritescaling$nineslice$border.f_290714_(), width / 2);
      int k = Math.min(guispritescaling$nineslice$border.f_291022_(), height / 2);
      int l = Math.min(guispritescaling$nineslice$border.f_290453_(), height / 2);
      if (width == sliceIn.f_291615_() && height == sliceIn.f_291235_()) {
         this.m_293540_(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, 0, x, y, depth, width, height);
      } else if (height == sliceIn.f_291235_()) {
         this.m_293540_(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, 0, x, y, depth, i, height);
         this.m_295409_(spriteIn, x + i, y, depth, width - j - i, height, i, 0, sliceIn.f_291615_() - j - i, sliceIn.f_291235_(), sliceIn.f_291615_(), sliceIn.f_291235_());
         this.m_293540_(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), sliceIn.f_291615_() - j, 0, x + width - j, y, depth, j, height);
      } else if (width == sliceIn.f_291615_()) {
         this.m_293540_(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, 0, x, y, depth, width, k);
         this.m_295409_(spriteIn, x, y + k, depth, width, height - l - k, 0, k, sliceIn.f_291615_(), sliceIn.f_291235_() - l - k, sliceIn.f_291615_(), sliceIn.f_291235_());
         this.m_293540_(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, sliceIn.f_291235_() - l, x, y + height - l, depth, width, l);
      } else {
         this.m_293540_(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, 0, x, y, depth, i, k);
         this.m_295409_(spriteIn, x + i, y, depth, width - j - i, k, i, 0, sliceIn.f_291615_() - j - i, k, sliceIn.f_291615_(), sliceIn.f_291235_());
         this.m_293540_(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), sliceIn.f_291615_() - j, 0, x + width - j, y, depth, j, k);
         this.m_293540_(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), 0, sliceIn.f_291235_() - l, x, y + height - l, depth, i, l);
         this.m_295409_(spriteIn, x + i, y + height - l, depth, width - j - i, l, i, sliceIn.f_291235_() - l, sliceIn.f_291615_() - j - i, l, sliceIn.f_291615_(), sliceIn.f_291235_());
         this.m_293540_(spriteIn, sliceIn.f_291615_(), sliceIn.f_291235_(), sliceIn.f_291615_() - j, sliceIn.f_291235_() - l, x + width - j, y + height - l, depth, j, l);
         this.m_295409_(spriteIn, x, y + k, depth, i, height - l - k, 0, k, i, sliceIn.f_291235_() - l - k, sliceIn.f_291615_(), sliceIn.f_291235_());
         this.m_295409_(spriteIn, x + i, y + k, depth, width - j - i, height - l - k, i, k, sliceIn.f_291615_() - j - i, sliceIn.f_291235_() - l - k, sliceIn.f_291615_(), sliceIn.f_291235_());
         this.m_295409_(spriteIn, x + width - j, y + k, depth, i, height - l - k, sliceIn.f_291615_() - j, k, j, sliceIn.f_291235_() - l - k, sliceIn.f_291615_(), sliceIn.f_291235_());
      }

   }

   private void m_295409_(TextureAtlasSprite spriteIn, int x, int y, int depth, int width, int height, int sliceX, int sliceY, int texWidth, int texHeight, int sliceWidth, int sliceHeight) {
      if (width > 0 && height > 0) {
         if (texWidth <= 0 || texHeight <= 0) {
            throw new IllegalArgumentException("Tiled sprite texture size must be positive, got " + texWidth + "x" + texHeight);
         }

         for(int i = 0; i < width; i += texWidth) {
            int j = Math.min(texWidth, width - i);

            for(int k = 0; k < height; k += texHeight) {
               int l = Math.min(texHeight, height - k);
               this.m_293540_(spriteIn, sliceWidth, sliceHeight, sliceX, sliceY, x + i, y + k, depth, j, l);
            }
         }
      }

   }

   public void m_280480_(ItemStack stackIn, int xIn, int yIn) {
      this.m_280053_(this.f_279544_.f_91074_, this.f_279544_.f_91073_, stackIn, xIn, yIn, 0);
   }

   public void m_280256_(ItemStack stackIn, int xIn, int yIn, int seedIn) {
      this.m_280053_(this.f_279544_.f_91074_, this.f_279544_.f_91073_, stackIn, xIn, yIn, seedIn);
   }

   public void m_280064_(ItemStack stackIn, int xIn, int yIn, int seedIn, int depthIn) {
      this.m_280405_(this.f_279544_.f_91074_, this.f_279544_.f_91073_, stackIn, xIn, yIn, seedIn, depthIn);
   }

   public void m_280203_(ItemStack stackIn, int xIn, int yIn) {
      this.m_306951_(stackIn, xIn, yIn, 0);
   }

   public void m_306951_(ItemStack stackIn, int xIn, int yIn, int seedIn) {
      this.m_280053_((LivingEntity)null, this.f_279544_.f_91073_, stackIn, xIn, yIn, seedIn);
   }

   public void m_280638_(LivingEntity entityIn, ItemStack stackIn, int xIn, int yIn, int seedIn) {
      this.m_280053_(entityIn, entityIn.m_9236_(), stackIn, xIn, yIn, seedIn);
   }

   private void m_280053_(@Nullable LivingEntity entityIn, @Nullable Level worldIn, ItemStack stackIn, int xIn, int yIn, int seedIn) {
      this.m_280405_(entityIn, worldIn, stackIn, xIn, yIn, seedIn, 0);
   }

   private void m_280405_(@Nullable LivingEntity entityIn, @Nullable Level worldIn, ItemStack stackIn, int xIn, int yIn, int seedIn, int depthIn) {
      ItemRenderer.setRenderItemGui(true);
      if (!stackIn.m_41619_()) {
         BakedModel bakedmodel = this.f_279544_.m_91291_().m_174264_(stackIn, worldIn, entityIn, seedIn);
         this.f_279612_.m_85836_();
         this.f_279612_.m_252880_((float)(xIn + 8), (float)(yIn + 8), (float)(150 + (bakedmodel.m_7539_() ? depthIn : 0)));

         try {
            this.f_279612_.m_85841_(16.0F, -16.0F, 16.0F);
            boolean flag = !bakedmodel.m_7547_();
            if (flag) {
               Lighting.m_84930_();
            }

            this.f_279544_.m_91291_().m_115143_(stackIn, ItemDisplayContext.GUI, false, this.f_279612_, this.m_280091_(), 15728880, OverlayTexture.f_118083_, bakedmodel);
            this.m_280262_();
            if (flag) {
               Lighting.m_84931_();
            }
         } catch (Throwable var12) {
            CrashReport crashreport = CrashReport.m_127521_(var12, "Rendering item");
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Item being rendered");
            crashreportcategory.m_128165_("Item Type", () -> {
               return String.valueOf(stackIn.m_41720_());
            });
            crashreportcategory.m_128165_("Item Components", () -> {
               return String.valueOf(stackIn.m_318732_());
            });
            crashreportcategory.m_128165_("Item Foil", () -> {
               return String.valueOf(stackIn.m_41790_());
            });
            throw new ReportedException(crashreport);
         }

         this.f_279612_.m_85849_();
      }

      ItemRenderer.setRenderItemGui(false);
   }

   public void m_280370_(Font fontIn, ItemStack stackIn, int xIn, int yIn) {
      this.m_280302_(fontIn, stackIn, xIn, yIn, (String)null);
   }

   public void m_280302_(Font fontIn, ItemStack stackIn, int xIn, int yIn, @Nullable String countStringIn) {
      if (!stackIn.m_41619_()) {
         this.f_279612_.m_85836_();
         if (stackIn.m_41613_() != 1 || countStringIn != null) {
            String s = countStringIn == null ? String.valueOf(stackIn.m_41613_()) : countStringIn;
            this.f_279612_.m_252880_(0.0F, 0.0F, 200.0F);
            this.m_280056_(fontIn, s, xIn + 19 - 2 - fontIn.m_92895_(s), yIn + 6 + 3, 16777215, true);
         }

         int i1;
         int j1;
         if (stackIn.m_150947_()) {
            int l = stackIn.m_150948_();
            int i = stackIn.m_150949_();
            if (Config.isCustomColors()) {
               float dmg = (float)stackIn.m_41773_();
               float maxDmg = (float)stackIn.m_41776_();
               float durability = Math.max(0.0F, (maxDmg - dmg) / maxDmg);
               i = CustomColors.getDurabilityColor(durability, i);
            }

            i1 = xIn + 2;
            j1 = yIn + 13;
            this.m_285944_(RenderType.m_286086_(), i1, j1, i1 + 13, j1 + 2, -16777216);
            this.m_285944_(RenderType.m_286086_(), i1, j1, i1 + l, j1 + 1, i | -16777216);
         }

         LocalPlayer localplayer = this.f_279544_.f_91074_;
         float f = localplayer == null ? 0.0F : localplayer.m_36335_().m_41521_(stackIn.m_41720_(), this.f_279544_.m_338668_().m_338527_(true));
         if (f > 0.0F) {
            i1 = yIn + Mth.m_14143_(16.0F * (1.0F - f));
            j1 = i1 + Mth.m_14167_(16.0F * f);
            this.m_285944_(RenderType.m_286086_(), xIn, i1, xIn + 16, j1, Integer.MAX_VALUE);
         }

         this.f_279612_.m_85849_();
         if (Reflector.ItemDecoratorHandler_render.exists()) {
            Object idh = Reflector.call(Reflector.ItemDecoratorHandler_of, stackIn);
            Reflector.call(idh, Reflector.ItemDecoratorHandler_render, this, fontIn, stackIn, xIn, yIn);
         }
      }

   }

   public void m_280153_(Font fontIn, ItemStack stackIn, int xIn, int yIn) {
      this.tooltipStack = stackIn;
      this.m_280677_(fontIn, Screen.m_280152_(this.f_279544_, stackIn), stackIn.m_150921_(), xIn, yIn);
      this.tooltipStack = ItemStack.f_41583_;
   }

   public void renderTooltip(Font font, List textComponents, Optional tooltipComponent, ItemStack stack, int mouseX, int mouseY) {
      this.tooltipStack = stack;
      this.m_280677_(font, textComponents, tooltipComponent, mouseX, mouseY);
      this.tooltipStack = ItemStack.f_41583_;
   }

   public void m_280677_(Font fontIn, List listIn, Optional componentIn, int xIn, int yIn) {
      List list = (List)listIn.stream().map(Component::m_7532_).map(ClientTooltipComponent::m_169948_).collect(Util.m_323807_());
      componentIn.ifPresent((compIn) -> {
         list.add(list.isEmpty() ? 0 : 1, ClientTooltipComponent.m_169950_(compIn));
      });
      if (Reflector.ForgeHooksClient_gatherTooltipComponents7.exists()) {
         List listForge = (List)Reflector.ForgeHooksClient_gatherTooltipComponents7.call(this.tooltipStack, listIn, componentIn, xIn, this.m_280182_(), this.m_280206_(), fontIn);
         list.clear();
         list.addAll(listForge);
      }

      this.m_280497_(fontIn, list, xIn, yIn, DefaultTooltipPositioner.f_262752_);
   }

   public void m_280557_(Font fontIn, Component componentIn, int xIn, int yIn) {
      this.m_280245_(fontIn, List.of(componentIn.m_7532_()), xIn, yIn);
   }

   public void m_280666_(Font fontIn, List componentsIn, int xIn, int yIn) {
      if (Reflector.ForgeHooksClient_gatherTooltipComponents6.exists()) {
         List components = (List)Reflector.ForgeHooksClient_gatherTooltipComponents6.call(this.tooltipStack, componentsIn, xIn, this.m_280182_(), this.m_280206_(), fontIn);
         this.m_280497_(fontIn, components, xIn, yIn, DefaultTooltipPositioner.f_262752_);
      } else {
         this.m_280245_(fontIn, Lists.transform(componentsIn, Component::m_7532_), xIn, yIn);
      }

   }

   public void renderComponentTooltip(Font font, List tooltips, int mouseX, int mouseY, ItemStack stack) {
      this.tooltipStack = stack;
      List components = (List)Reflector.ForgeHooksClient_gatherTooltipComponents6.call(stack, tooltips, mouseX, this.m_280182_(), this.m_280206_(), font);
      this.m_280497_(font, components, mouseX, mouseY, DefaultTooltipPositioner.f_262752_);
      this.tooltipStack = ItemStack.f_41583_;
   }

   public void m_280245_(Font fontIn, List listIn, int xIn, int yIn) {
      this.m_280497_(fontIn, (List)listIn.stream().map(ClientTooltipComponent::m_169948_).collect(Collectors.toList()), xIn, yIn, DefaultTooltipPositioner.f_262752_);
   }

   public void m_280547_(Font fontIn, List listIn, ClientTooltipPositioner positionerIn, int xIn, int yIn) {
      this.m_280497_(fontIn, (List)listIn.stream().map(ClientTooltipComponent::m_169948_).collect(Collectors.toList()), xIn, yIn, positionerIn);
   }

   private void m_280497_(Font fontIn, List listIn, int xIn, int yIn, ClientTooltipPositioner positionerIn) {
      if (!listIn.isEmpty()) {
         Object preEvent = null;
         if (Reflector.ForgeHooksClient_onRenderTooltipPre.exists()) {
            preEvent = Reflector.ForgeHooksClient_onRenderTooltipPre.call(this.tooltipStack, this, xIn, yIn, this.m_280182_(), this.m_280206_(), listIn, fontIn, positionerIn);
            if (Reflector.callBoolean(preEvent, Reflector.Event_isCanceled)) {
               return;
            }
         }

         int i = 0;
         int j = listIn.size() == 1 ? -2 : 0;

         ClientTooltipComponent clienttooltipcomponent;
         for(Iterator var9 = listIn.iterator(); var9.hasNext(); j += clienttooltipcomponent.m_142103_()) {
            clienttooltipcomponent = (ClientTooltipComponent)var9.next();
            if (preEvent != null) {
               fontIn = (Font)Reflector.call(preEvent, Reflector.RenderTooltipEvent_getFont);
            }

            int k = clienttooltipcomponent.m_142069_(fontIn);
            if (k > i) {
               i = k;
            }
         }

         if (preEvent != null) {
            xIn = Reflector.callInt(preEvent, Reflector.RenderTooltipEvent_getX);
            yIn = Reflector.callInt(preEvent, Reflector.RenderTooltipEvent_getY);
         }

         Vector2ic vector2ic = positionerIn.m_262814_(this.m_280182_(), this.m_280206_(), xIn, yIn, i, j);
         int l = vector2ic.x();
         int i1 = vector2ic.y();
         this.f_279612_.m_85836_();
         int j1 = true;
         this.m_286007_(() -> {
            if (Reflector.ForgeHooksClient_onRenderTooltipColor.exists()) {
               Object colorEvent = Reflector.ForgeHooksClient_onRenderTooltipColor.call(this.tooltipStack, this, l, i1, fontIn, listIn);
               int backStart = Reflector.callInt(colorEvent, Reflector.RenderTooltipEvent_Color_getBackgroundStart);
               int backEnd = Reflector.callInt(colorEvent, Reflector.RenderTooltipEvent_Color_getBackgroundEnd);
               int borderStart = Reflector.callInt(colorEvent, Reflector.RenderTooltipEvent_Color_getBorderStart);
               int borderEnd = Reflector.callInt(colorEvent, Reflector.RenderTooltipEvent_Color_getBorderEnd);
               Reflector.TooltipRenderUtil_renderTooltipBackground10.call(this, l, i1, i, j, 400, backStart, backEnd, borderStart, borderEnd);
            } else {
               TooltipRenderUtil.m_280205_(this, l, i1, i, j, 400);
            }

         });
         this.f_279612_.m_252880_(0.0F, 0.0F, 400.0F);
         int k1 = i1;

         int k2;
         ClientTooltipComponent clienttooltipcomponent2;
         for(k2 = 0; k2 < listIn.size(); ++k2) {
            clienttooltipcomponent2 = (ClientTooltipComponent)listIn.get(k2);
            clienttooltipcomponent2.m_142440_(fontIn, l, k1, this.f_279612_.m_85850_().m_252922_(), this.f_279627_);
            k1 += clienttooltipcomponent2.m_142103_() + (k2 == 0 ? 2 : 0);
         }

         k1 = i1;

         for(k2 = 0; k2 < listIn.size(); ++k2) {
            clienttooltipcomponent2 = (ClientTooltipComponent)listIn.get(k2);
            clienttooltipcomponent2.m_183452_(fontIn, l, k1, this);
            k1 += clienttooltipcomponent2.m_142103_() + (k2 == 0 ? 2 : 0);
         }

         this.f_279612_.m_85849_();
      }

   }

   public void m_280304_(Font fontIn, @Nullable Style styleIn, int xIn, int yIn) {
      if (styleIn != null && styleIn.m_131186_() != null) {
         HoverEvent hoverevent = styleIn.m_131186_();
         HoverEvent.ItemStackInfo hoverevent$itemstackinfo = (HoverEvent.ItemStackInfo)hoverevent.m_130823_(Action.f_130832_);
         if (hoverevent$itemstackinfo != null) {
            this.m_280153_(fontIn, hoverevent$itemstackinfo.m_130898_(), xIn, yIn);
         } else {
            HoverEvent.EntityTooltipInfo hoverevent$entitytooltipinfo = (HoverEvent.EntityTooltipInfo)hoverevent.m_130823_(Action.f_130833_);
            if (hoverevent$entitytooltipinfo != null) {
               if (this.f_279544_.f_91066_.f_92125_) {
                  this.m_280666_(fontIn, hoverevent$entitytooltipinfo.m_130884_(), xIn, yIn);
               }
            } else {
               Component component = (Component)hoverevent.m_130823_(Action.f_130831_);
               if (component != null) {
                  this.m_280245_(fontIn, fontIn.m_92923_(component, Math.max(this.m_280182_() / 2, 200)), xIn, yIn);
               }
            }
         }
      }

   }

   public void getBulkData(RenderType renderType, ByteBuffer buffer) {
      if (renderType != null) {
         VertexConsumer builder = this.f_279627_.m_6299_(renderType);
         if (builder.getVertexCount() > 0) {
            builder.getBulkData(buffer);
         }
      }
   }

   public void putBulkData(RenderType renderType, ByteBuffer buffer) {
      if (renderType != null) {
         if (buffer.position() < buffer.limit()) {
            VertexConsumer builder = this.f_279627_.m_6299_(renderType);
            builder.putBulkData(buffer);
         }
      }
   }

   static class ScissorStack {
      private final Deque f_279656_ = new ArrayDeque();

      public ScreenRectangle m_280318_(ScreenRectangle rectIn) {
         ScreenRectangle screenrectangle = (ScreenRectangle)this.f_279656_.peekLast();
         if (screenrectangle != null) {
            ScreenRectangle screenrectangle1 = (ScreenRectangle)Objects.requireNonNullElse(rectIn.m_275842_(screenrectangle), ScreenRectangle.m_264427_());
            this.f_279656_.addLast(screenrectangle1);
            return screenrectangle1;
         } else {
            this.f_279656_.addLast(rectIn);
            return rectIn;
         }
      }

      @Nullable
      public ScreenRectangle m_280462_() {
         if (this.f_279656_.isEmpty()) {
            throw new IllegalStateException("Scissor stack underflow");
         } else {
            this.f_279656_.removeLast();
            return (ScreenRectangle)this.f_279656_.peekLast();
         }
      }

      public boolean m_319411_(int x, int y) {
         return this.f_279656_.isEmpty() ? true : ((ScreenRectangle)this.f_279656_.peek()).m_319431_(x, y);
      }
   }
}
