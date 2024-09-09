package net.minecraft.client.gui.components;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket.Handler;
import net.minecraft.world.BossEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;

public class BossHealthOverlay {
   private static final int f_168805_ = 182;
   private static final int f_168806_ = 5;
   private static final net.minecraft.resources.ResourceLocation[] f_290930_ = new net.minecraft.resources.ResourceLocation[]{
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/pink_background"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/blue_background"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/red_background"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/green_background"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/yellow_background"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/purple_background"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/white_background")
   };
   private static final net.minecraft.resources.ResourceLocation[] f_290575_ = new net.minecraft.resources.ResourceLocation[]{
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/pink_progress"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/blue_progress"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/red_progress"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/green_progress"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/yellow_progress"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/purple_progress"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/white_progress")
   };
   private static final net.minecraft.resources.ResourceLocation[] f_291445_ = new net.minecraft.resources.ResourceLocation[]{
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/notched_6_background"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/notched_10_background"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/notched_12_background"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/notched_20_background")
   };
   private static final net.minecraft.resources.ResourceLocation[] f_290640_ = new net.minecraft.resources.ResourceLocation[]{
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/notched_6_progress"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/notched_10_progress"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/notched_12_progress"),
      net.minecraft.resources.ResourceLocation.m_340282_("boss_bar/notched_20_progress")
   };
   private final Minecraft f_93698_;
   final Map<UUID, LerpingBossEvent> f_93699_ = Maps.newLinkedHashMap();

   public BossHealthOverlay(Minecraft clientIn) {
      this.f_93698_ = clientIn;
   }

   public void m_280652_(net.minecraft.client.gui.GuiGraphics graphicsIn) {
      if (!this.f_93699_.isEmpty()) {
         this.f_93698_.m_91307_().m_6180_("bossHealth");
         int i = graphicsIn.m_280182_();
         int j = 12;

         for (LerpingBossEvent lerpingbossevent : this.f_93699_.values()) {
            int k = i / 2 - 91;
            boolean render = true;
            int increment = 19;
            if (Reflector.ForgeHooksClient_onCustomizeBossEventProgress.exists()) {
               Object event = Reflector.ForgeHooksClient_onCustomizeBossEventProgress
                  .call(graphicsIn, this.f_93698_.m_91268_(), lerpingbossevent, k, j, 10 + 9);
               render = !Reflector.callBoolean(event, Reflector.Event_isCanceled);
               increment = Reflector.callInt(event, Reflector.CustomizeGuiOverlayEvent_BossEventProgress_getIncrement);
            }

            if (render) {
               this.m_280106_(graphicsIn, k, j, lerpingbossevent);
               Component component = lerpingbossevent.m_18861_();
               int l = this.f_93698_.f_91062_.m_92852_(component);
               int i1 = i / 2 - l / 2;
               int j1 = j - 9;
               int col = 16777215;
               if (Config.isCustomColors()) {
                  col = CustomColors.getBossTextColor(col);
               }

               graphicsIn.m_280430_(this.f_93698_.f_91062_, component, i1, j1, col);
            }

            j += increment;
            if (j >= graphicsIn.m_280206_() / 3) {
               break;
            }
         }

         this.f_93698_.m_91307_().m_7238_();
      }
   }

   private void m_280106_(net.minecraft.client.gui.GuiGraphics graphicsIn, int xIn, int yIn, BossEvent eventIn) {
      this.m_280093_(graphicsIn, xIn, yIn, eventIn, 182, f_290930_, f_291445_);
      int i = net.minecraft.util.Mth.m_295919_(eventIn.m_142717_(), 0, 182);
      if (i > 0) {
         this.m_280093_(graphicsIn, xIn, yIn, eventIn, i, f_290575_, f_290640_);
      }
   }

   private void m_280093_(
      net.minecraft.client.gui.GuiGraphics graphicsIn,
      int xIn,
      int yIn,
      BossEvent eventIn,
      int widthIn,
      net.minecraft.resources.ResourceLocation[] barLocationsIn,
      net.minecraft.resources.ResourceLocation[] overlayLocationsIn
   ) {
      RenderSystem.enableBlend();
      graphicsIn.m_294122_(barLocationsIn[eventIn.m_18862_().ordinal()], 182, 5, 0, 0, xIn, yIn, widthIn, 5);
      if (eventIn.m_18863_() != BossBarOverlay.PROGRESS) {
         graphicsIn.m_294122_(overlayLocationsIn[eventIn.m_18863_().ordinal() - 1], 182, 5, 0, 0, xIn, yIn, widthIn, 5);
      }

      RenderSystem.disableBlend();
   }

   public void m_93711_(ClientboundBossEventPacket packetIn) {
      packetIn.m_178643_(
         new Handler() {
            public void m_142107_(
               UUID p_142107_1_,
               Component p_142107_2_,
               float p_142107_3_,
               BossBarColor p_142107_4_,
               BossBarOverlay p_142107_5_,
               boolean p_142107_6_,
               boolean p_142107_7_,
               boolean p_142107_8_
            ) {
               BossHealthOverlay.this.f_93699_
                  .put(
                     p_142107_1_, new LerpingBossEvent(p_142107_1_, p_142107_2_, p_142107_3_, p_142107_4_, p_142107_5_, p_142107_6_, p_142107_7_, p_142107_8_)
                  );
            }

            public void m_142751_(UUID p_142751_1_) {
               BossHealthOverlay.this.f_93699_.remove(p_142751_1_);
            }

            public void m_142653_(UUID p_142653_1_, float p_142653_2_) {
               ((LerpingBossEvent)BossHealthOverlay.this.f_93699_.get(p_142653_1_)).m_142711_(p_142653_2_);
            }

            public void m_142366_(UUID p_142366_1_, Component p_142366_2_) {
               ((LerpingBossEvent)BossHealthOverlay.this.f_93699_.get(p_142366_1_)).m_6456_(p_142366_2_);
            }

            public void m_142358_(UUID p_142358_1_, BossBarColor p_142358_2_, BossBarOverlay p_142358_3_) {
               LerpingBossEvent lerpingbossevent = (LerpingBossEvent)BossHealthOverlay.this.f_93699_.get(p_142358_1_);
               lerpingbossevent.m_6451_(p_142358_2_);
               lerpingbossevent.m_5648_(p_142358_3_);
            }

            public void m_142513_(UUID p_142513_1_, boolean p_142513_2_, boolean p_142513_3_, boolean p_142513_4_) {
               LerpingBossEvent lerpingbossevent = (LerpingBossEvent)BossHealthOverlay.this.f_93699_.get(p_142513_1_);
               lerpingbossevent.m_7003_(p_142513_2_);
               lerpingbossevent.m_7005_(p_142513_3_);
               lerpingbossevent.m_7006_(p_142513_4_);
            }
         }
      );
   }

   public void m_93703_() {
      this.f_93699_.clear();
   }

   public boolean m_93713_() {
      if (!this.f_93699_.isEmpty()) {
         for (BossEvent bossevent : this.f_93699_.values()) {
            if (bossevent.m_18865_()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean m_93714_() {
      if (!this.f_93699_.isEmpty()) {
         for (BossEvent bossevent : this.f_93699_.values()) {
            if (bossevent.m_18864_()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean m_93715_() {
      if (!this.f_93699_.isEmpty()) {
         for (BossEvent bossevent : this.f_93699_.values()) {
            if (bossevent.m_18866_()) {
               return true;
            }
         }
      }

      return false;
   }

   public String getBossName() {
      if (!this.f_93699_.isEmpty()) {
         for (BossEvent bossevent : this.f_93699_.values()) {
            Component name = bossevent.m_18861_();
            if (name != null && name.m_214077_() instanceof TranslatableContents tranCont) {
               return tranCont.m_237508_();
            }
         }
      }

      return null;
   }
}
