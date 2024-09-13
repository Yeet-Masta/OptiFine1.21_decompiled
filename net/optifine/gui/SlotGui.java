package net.optifine.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.optifine.util.TextureUtils;

public abstract class SlotGui extends AbstractContainerEventHandler implements NarratableEntry {
   public static ResourceLocation WHITE_TEXTURE_LOCATION = TextureUtils.WHITE_TEXTURE_LOCATION;
   public static ResourceLocation MENU_LIST_BACKGROUND = new ResourceLocation("textures/gui/menu_list_background.png");
   public static ResourceLocation INWORLD_MENU_LIST_BACKGROUND = new ResourceLocation("textures/gui/inworld_menu_list_background.png");
   protected static int NO_DRAG;
   protected static int DRAG_OUTSIDE;
   protected Minecraft minecraft;
   protected int width;
   protected int height;
   protected int y0;
   protected int y1;
   protected int x1;
   protected int x0;
   protected int itemHeight;
   protected boolean centerListVertically = true;
   protected int yDrag = -2;
   protected double f_271520_;
   protected boolean visible = true;
   protected boolean renderSelection = true;
   protected boolean renderHeader;
   protected int headerHeight;
   private boolean scrolling;

   public SlotGui(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
      this.minecraft = mcIn;
      this.width = width;
      this.height = height;
      this.y0 = topIn;
      this.y1 = bottomIn;
      this.itemHeight = slotHeightIn;
      this.x0 = 0;
      this.x1 = width;
   }

   public void setRenderSelection(boolean p_setRenderSelection_1_) {
      this.renderSelection = p_setRenderSelection_1_;
   }

   protected void setRenderHeader(boolean p_setRenderHeader_1_, int p_setRenderHeader_2_) {
      this.renderHeader = p_setRenderHeader_1_;
      this.headerHeight = p_setRenderHeader_2_;
      if (!p_setRenderHeader_1_) {
         this.headerHeight = 0;
      }
   }

   public void setVisible(boolean p_setVisible_1_) {
      this.visible = p_setVisible_1_;
   }

   public boolean isVisible() {
      return this.visible;
   }

   protected abstract int getItemCount();

   public List<? extends GuiEventListener> m_6702_() {
      return Collections.emptyList();
   }

   protected boolean selectItem(int p_selectItem_1_, int p_selectItem_2_, double p_selectItem_3_, double p_selectItem_5_) {
      return true;
   }

   protected abstract boolean isSelectedItem(int var1);

   protected int getMaxPosition() {
      return this.getItemCount() * this.itemHeight + this.headerHeight;
   }

   protected abstract void renderBackground();

   protected void updateItemPosition(int p_updateItemPosition_1_, int p_updateItemPosition_2_, int p_updateItemPosition_3_, float p_updateItemPosition_4_) {
   }

   protected abstract void renderItem(GuiGraphics var1, int var2, int var3, int var4, int var5, int var6, int var7, float var8);

   protected void renderHeader(int p_renderHeader_1_, int p_renderHeader_2_, Tesselator p_renderHeader_3_) {
   }

   protected void clickedHeader(int p_clickedHeader_1_, int p_clickedHeader_2_) {
   }

   protected void renderDecorations(int p_renderDecorations_1_, int p_renderDecorations_2_) {
   }

   public int getItemAtPosition(double p_getItemAtPosition_1_, double p_getItemAtPosition_3_) {
      int i = this.x0 + this.width / 2 - this.getRowWidth() / 2;
      int j = this.x0 + this.width / 2 + this.getRowWidth() / 2;
      int k = Mth.m_14107_(p_getItemAtPosition_3_ - (double)this.y0) - this.headerHeight + (int)this.f_271520_ - 4;
      int l = k / this.itemHeight;
      return p_getItemAtPosition_1_ < (double)this.getScrollbarPosition()
            && p_getItemAtPosition_1_ >= (double)i
            && p_getItemAtPosition_1_ <= (double)j
            && l >= 0
            && k >= 0
            && l < this.getItemCount()
         ? l
         : -1;
   }

   protected void capYPosition() {
      this.f_271520_ = Mth.m_14008_(this.f_271520_, 0.0, (double)this.getMaxScroll());
   }

   public int getMaxScroll() {
      return Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4));
   }

   public void centerScrollOn(int p_centerScrollOn_1_) {
      this.f_271520_ = (double)(p_centerScrollOn_1_ * this.itemHeight + this.itemHeight / 2 - (this.y1 - this.y0) / 2);
      this.capYPosition();
   }

   public int getScroll() {
      return (int)this.f_271520_;
   }

   public boolean isMouseInList(double p_isMouseInList_1_, double p_isMouseInList_3_) {
      return p_isMouseInList_3_ >= (double)this.y0
         && p_isMouseInList_3_ <= (double)this.y1
         && p_isMouseInList_1_ >= (double)this.x0
         && p_isMouseInList_1_ <= (double)this.x1;
   }

   public int getScrollBottom() {
      return (int)this.f_271520_ - this.height - this.headerHeight;
   }

   public void scroll(int p_scroll_1_) {
      this.f_271520_ += (double)p_scroll_1_;
      this.capYPosition();
      this.yDrag = -2;
   }

   public void m_324219_(GuiGraphics graphicsIn, int p_render_1_, int p_render_2_, float p_render_3_) {
      if (this.visible) {
         this.renderBackground();
         int i = this.getScrollbarPosition();
         int j = i + 6;
         this.capYPosition();
         Tesselator tessellator = Tesselator.m_85913_();
         RenderSystem.setShader(GameRenderer::m_172820_);
         ResourceLocation menuListBackground = this.minecraft.f_91073_ != null ? INWORLD_MENU_LIST_BACKGROUND : MENU_LIST_BACKGROUND;
         RenderSystem.setShaderTexture(0, menuListBackground);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.enableBlend();
         float f = 32.0F;
         BufferBuilder bufferbuilder = tessellator.m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
         bufferbuilder.m_167146_((float)this.x0, (float)this.y1, 0.0F)
            .m_167083_((float)this.x0 / 32.0F, (float)(this.y1 + (int)this.f_271520_) / 32.0F)
            .m_167129_(32, 32, 32, 255);
         bufferbuilder.m_167146_((float)this.x1, (float)this.y1, 0.0F)
            .m_167083_((float)this.x1 / 32.0F, (float)(this.y1 + (int)this.f_271520_) / 32.0F)
            .m_167129_(32, 32, 32, 255);
         bufferbuilder.m_167146_((float)this.x1, (float)this.y0, 0.0F)
            .m_167083_((float)this.x1 / 32.0F, (float)(this.y0 + (int)this.f_271520_) / 32.0F)
            .m_167129_(32, 32, 32, 255);
         bufferbuilder.m_167146_((float)this.x0, (float)this.y0, 0.0F)
            .m_167083_((float)this.x0 / 32.0F, (float)(this.y0 + (int)this.f_271520_) / 32.0F)
            .m_167129_(32, 32, 32, 255);
         tessellator.draw(bufferbuilder);
         RenderSystem.disableBlend();
         int k = this.x0 + this.width / 2 - this.getRowWidth() / 2 + 2;
         int l = this.y0 + 4 - (int)this.f_271520_;
         if (this.renderHeader) {
            this.renderHeader(k, l, tessellator);
         }

         this.renderList(graphicsIn, k, l, p_render_1_, p_render_2_, p_render_3_);
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ZERO,
            GlStateManager.DestFactor.ONE
         );
         RenderSystem.setShader(GameRenderer::m_172820_);
         RenderSystem.setShaderTexture(0, WHITE_TEXTURE_LOCATION);
         RenderSystem.disableTexture();
         int delta = 2;
         int j1 = this.getMaxScroll();
         if (j1 > 0) {
            int k1 = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
            k1 = Mth.m_14045_(k1, 32, this.y1 - this.y0 - 8);
            int l1 = (int)this.f_271520_ * (this.y1 - this.y0 - k1) / j1 + this.y0;
            if (l1 < this.y0) {
               l1 = this.y0;
            }

            bufferbuilder = tessellator.m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
            bufferbuilder.m_167146_((float)i, (float)this.y1, 0.0F).m_167083_(0.0F, 1.0F).m_167129_(0, 0, 0, 255);
            bufferbuilder.m_167146_((float)j, (float)this.y1, 0.0F).m_167083_(1.0F, 1.0F).m_167129_(0, 0, 0, 255);
            bufferbuilder.m_167146_((float)j, (float)this.y0, 0.0F).m_167083_(1.0F, 0.0F).m_167129_(0, 0, 0, 255);
            bufferbuilder.m_167146_((float)i, (float)this.y0, 0.0F).m_167083_(0.0F, 0.0F).m_167129_(0, 0, 0, 255);
            tessellator.draw(bufferbuilder);
            bufferbuilder = tessellator.m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
            bufferbuilder.m_167146_((float)i, (float)(l1 + k1), 0.0F).m_167083_(0.0F, 1.0F).m_167129_(128, 128, 128, 255);
            bufferbuilder.m_167146_((float)j, (float)(l1 + k1), 0.0F).m_167083_(1.0F, 1.0F).m_167129_(128, 128, 128, 255);
            bufferbuilder.m_167146_((float)j, (float)l1, 0.0F).m_167083_(1.0F, 0.0F).m_167129_(128, 128, 128, 255);
            bufferbuilder.m_167146_((float)i, (float)l1, 0.0F).m_167083_(0.0F, 0.0F).m_167129_(128, 128, 128, 255);
            tessellator.draw(bufferbuilder);
            bufferbuilder = tessellator.m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
            bufferbuilder.m_167146_((float)i, (float)(l1 + k1 - 1), 0.0F).m_167083_(0.0F, 1.0F).m_167129_(192, 192, 192, 255);
            bufferbuilder.m_167146_((float)(j - 1), (float)(l1 + k1 - 1), 0.0F).m_167083_(1.0F, 1.0F).m_167129_(192, 192, 192, 255);
            bufferbuilder.m_167146_((float)(j - 1), (float)l1, 0.0F).m_167083_(1.0F, 0.0F).m_167129_(192, 192, 192, 255);
            bufferbuilder.m_167146_((float)i, (float)l1, 0.0F).m_167083_(0.0F, 0.0F).m_167129_(192, 192, 192, 255);
            tessellator.draw(bufferbuilder);
         }

         this.renderDecorations(p_render_1_, p_render_2_);
         RenderSystem.enableTexture();
         RenderSystem.disableBlend();
      }
   }

   protected void updateScrollingState(double p_updateScrollingState_1_, double p_updateScrollingState_3_, int p_updateScrollingState_5_) {
      this.scrolling = p_updateScrollingState_5_ == 0
         && p_updateScrollingState_1_ >= (double)this.getScrollbarPosition()
         && p_updateScrollingState_1_ < (double)(this.getScrollbarPosition() + 6);
   }

   public boolean m_6375_(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      this.updateScrollingState(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
      if (this.isVisible() && this.isMouseInList(p_mouseClicked_1_, p_mouseClicked_3_)) {
         int i = this.getItemAtPosition(p_mouseClicked_1_, p_mouseClicked_3_);
         if (i == -1 && p_mouseClicked_5_ == 0) {
            this.clickedHeader(
               (int)(p_mouseClicked_1_ - (double)(this.x0 + this.width / 2 - this.getRowWidth() / 2)),
               (int)(p_mouseClicked_3_ - (double)this.y0) + (int)this.f_271520_ - 4
            );
            return true;
         } else if (i != -1 && this.selectItem(i, p_mouseClicked_5_, p_mouseClicked_1_, p_mouseClicked_3_)) {
            if (this.m_6702_().size() > i) {
               this.m_7522_((GuiEventListener)this.m_6702_().get(i));
            }

            this.m_7897_(true);
            return true;
         } else {
            return this.scrolling;
         }
      } else {
         return false;
      }
   }

   public boolean m_6348_(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      if (this.m_7222_() != null) {
         this.m_7222_().m_6348_(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
      }

      return false;
   }

   public boolean m_7979_(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
      if (super.m_7979_(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_)) {
         return true;
      } else if (this.isVisible() && p_mouseDragged_5_ == 0 && this.scrolling) {
         if (p_mouseDragged_3_ < (double)this.y0) {
            this.f_271520_ = 0.0;
         } else if (p_mouseDragged_3_ > (double)this.y1) {
            this.f_271520_ = (double)this.getMaxScroll();
         } else {
            double d0 = (double)this.getMaxScroll();
            if (d0 < 1.0) {
               d0 = 1.0;
            }

            int i = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
            i = Mth.m_14045_(i, 32, this.y1 - this.y0 - 8);
            double d1 = d0 / (double)(this.y1 - this.y0 - i);
            if (d1 < 1.0) {
               d1 = 1.0;
            }

            this.f_271520_ += p_mouseDragged_8_ * d1;
            this.capYPosition();
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean m_6050_(double mouseX, double mouseY, double deltaH, double deltaV) {
      if (!this.isVisible()) {
         return false;
      } else {
         this.f_271520_ = this.f_271520_ - deltaV * (double)this.itemHeight / 2.0;
         return true;
      }
   }

   public boolean m_7933_(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      if (!this.isVisible()) {
         return false;
      } else if (super.m_7933_(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
         return true;
      } else if (p_keyPressed_1_ == 264) {
         this.moveSelection(1);
         return true;
      } else if (p_keyPressed_1_ == 265) {
         this.moveSelection(-1);
         return true;
      } else {
         return false;
      }
   }

   protected void moveSelection(int p_moveSelection_1_) {
   }

   public boolean m_5534_(char p_charTyped_1_, int p_charTyped_2_) {
      return !this.isVisible() ? false : super.m_5534_(p_charTyped_1_, p_charTyped_2_);
   }

   public boolean m_5953_(double p_isMouseOver_1_, double p_isMouseOver_3_) {
      return this.isMouseInList(p_isMouseOver_1_, p_isMouseOver_3_);
   }

   public int getRowWidth() {
      return 220;
   }

   protected void renderList(GuiGraphics graphicsIn, int p_renderList_1_, int p_renderList_2_, int p_renderList_3_, int p_renderList_4_, float p_renderList_5_) {
      int i = this.getItemCount();
      Tesselator tessellator = Tesselator.m_85913_();
      graphicsIn.m_280588_(this.x0, this.y0, this.x1, this.y1);

      for (int j = 0; j < i; j++) {
         int k = p_renderList_2_ + j * this.itemHeight + this.headerHeight;
         int l = this.itemHeight - 4;
         if (k > this.y1 || k + l < this.y0) {
            this.updateItemPosition(j, p_renderList_1_, k, p_renderList_5_);
         }

         if (this.renderSelection && this.isSelectedItem(j)) {
            int i1 = this.x0 + this.width / 2 - this.getRowWidth() / 2;
            int j1 = this.x0 + this.width / 2 + this.getRowWidth() / 2;
            RenderSystem.disableTexture();
            RenderSystem.setShader(GameRenderer::m_172808_);
            float f = this.isFocusedNow() ? 1.0F : 0.5F;
            RenderSystem.setShaderColor(f, f, f, 1.0F);
            BufferBuilder bufferbuilder = tessellator.m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
            bufferbuilder.m_167146_((float)i1, (float)(k + l + 2), 0.0F);
            bufferbuilder.m_167146_((float)j1, (float)(k + l + 2), 0.0F);
            bufferbuilder.m_167146_((float)j1, (float)(k - 2), 0.0F);
            bufferbuilder.m_167146_((float)i1, (float)(k - 2), 0.0F);
            tessellator.draw(bufferbuilder);
            RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
            bufferbuilder = tessellator.m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
            bufferbuilder.m_167146_((float)(i1 + 1), (float)(k + l + 1), 0.0F);
            bufferbuilder.m_167146_((float)(j1 - 1), (float)(k + l + 1), 0.0F);
            bufferbuilder.m_167146_((float)(j1 - 1), (float)(k - 1), 0.0F);
            bufferbuilder.m_167146_((float)(i1 + 1), (float)(k - 1), 0.0F);
            tessellator.draw(bufferbuilder);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableTexture();
         }

         if (k + this.itemHeight >= this.y0 && k <= this.y1) {
            this.renderItem(graphicsIn, j, p_renderList_1_, k, l, p_renderList_3_, p_renderList_4_, p_renderList_5_);
         }
      }

      graphicsIn.m_280618_();
   }

   protected boolean isFocusedNow() {
      return false;
   }

   protected int getScrollbarPosition() {
      return this.width / 2 + 124;
   }

   protected void renderHoleBackground(
      int p_renderHoleBackground_1_, int p_renderHoleBackground_2_, int p_renderHoleBackground_3_, int p_renderHoleBackground_4_
   ) {
      Tesselator tessellator = Tesselator.m_85913_();
      RenderSystem.setShader(GameRenderer::m_172820_);
      RenderSystem.setShaderTexture(0, Screen.f_315252_);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      float f = 32.0F;
      BufferBuilder bufferbuilder = tessellator.m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
      bufferbuilder.m_167146_((float)this.x0, (float)p_renderHoleBackground_2_, 0.0F)
         .m_167083_(0.0F, (float)p_renderHoleBackground_2_ / 32.0F)
         .m_167129_(64, 64, 64, p_renderHoleBackground_4_);
      bufferbuilder.m_167146_((float)(this.x0 + this.width), (float)p_renderHoleBackground_2_, 0.0F)
         .m_167083_((float)this.width / 32.0F, (float)p_renderHoleBackground_2_ / 32.0F)
         .m_167129_(64, 64, 64, p_renderHoleBackground_4_);
      bufferbuilder.m_167146_((float)(this.x0 + this.width), (float)p_renderHoleBackground_1_, 0.0F)
         .m_167083_((float)this.width / 32.0F, (float)p_renderHoleBackground_1_ / 32.0F)
         .m_167129_(64, 64, 64, p_renderHoleBackground_3_);
      bufferbuilder.m_167146_((float)this.x0, (float)p_renderHoleBackground_1_, 0.0F)
         .m_167083_(0.0F, (float)p_renderHoleBackground_1_ / 32.0F)
         .m_167129_(64, 64, 64, p_renderHoleBackground_3_);
      tessellator.draw(bufferbuilder);
   }

   public void setLeftPos(int p_setLeftPos_1_) {
      this.x0 = p_setLeftPos_1_;
      this.x1 = p_setLeftPos_1_ + this.width;
   }

   public int getItemHeight() {
      return this.itemHeight;
   }

   public NarrationPriority m_142684_() {
      return NarrationPriority.HOVERED;
   }

   public void m_142291_(NarrationElementOutput output) {
   }
}
