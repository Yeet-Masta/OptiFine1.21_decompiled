import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import java.util.UUID;
import net.minecraft.src.C_213523_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3466_;
import net.minecraft.src.C_459_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5044_;
import net.minecraft.src.C_459_.C_460_;
import net.minecraft.src.C_459_.C_461_;
import net.minecraft.src.C_5044_.C_141845_;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;

public class BossHealthOverlay {
   private static final int a = 182;
   private static final int b = 5;
   private static final ResourceLocation[] c = new ResourceLocation[]{
      ResourceLocation.b("boss_bar/pink_background"),
      ResourceLocation.b("boss_bar/blue_background"),
      ResourceLocation.b("boss_bar/red_background"),
      ResourceLocation.b("boss_bar/green_background"),
      ResourceLocation.b("boss_bar/yellow_background"),
      ResourceLocation.b("boss_bar/purple_background"),
      ResourceLocation.b("boss_bar/white_background")
   };
   private static final ResourceLocation[] d = new ResourceLocation[]{
      ResourceLocation.b("boss_bar/pink_progress"),
      ResourceLocation.b("boss_bar/blue_progress"),
      ResourceLocation.b("boss_bar/red_progress"),
      ResourceLocation.b("boss_bar/green_progress"),
      ResourceLocation.b("boss_bar/yellow_progress"),
      ResourceLocation.b("boss_bar/purple_progress"),
      ResourceLocation.b("boss_bar/white_progress")
   };
   private static final ResourceLocation[] e = new ResourceLocation[]{
      ResourceLocation.b("boss_bar/notched_6_background"),
      ResourceLocation.b("boss_bar/notched_10_background"),
      ResourceLocation.b("boss_bar/notched_12_background"),
      ResourceLocation.b("boss_bar/notched_20_background")
   };
   private static final ResourceLocation[] f = new ResourceLocation[]{
      ResourceLocation.b("boss_bar/notched_6_progress"),
      ResourceLocation.b("boss_bar/notched_10_progress"),
      ResourceLocation.b("boss_bar/notched_12_progress"),
      ResourceLocation.b("boss_bar/notched_20_progress")
   };
   private final C_3391_ g;
   final Map<UUID, C_3466_> h = Maps.newLinkedHashMap();

   public BossHealthOverlay(C_3391_ clientIn) {
      this.g = clientIn;
   }

   public void a(GuiGraphics graphicsIn) {
      if (!this.h.isEmpty()) {
         this.g.m_91307_().m_6180_("bossHealth");
         int i = graphicsIn.a();
         int j = 12;

         for (C_3466_ lerpingbossevent : this.h.values()) {
            int k = i / 2 - 91;
            boolean render = true;
            int increment = 19;
            if (Reflector.ForgeHooksClient_onCustomizeBossEventProgress.exists()) {
               Object event = Reflector.ForgeHooksClient_onCustomizeBossEventProgress.call(graphicsIn, this.g.aM(), lerpingbossevent, k, j, 10 + 9);
               render = !Reflector.callBoolean(event, Reflector.Event_isCanceled);
               increment = Reflector.callInt(event, Reflector.CustomizeGuiOverlayEvent_BossEventProgress_getIncrement);
            }

            if (render) {
               this.a(graphicsIn, k, j, lerpingbossevent);
               C_4996_ component = lerpingbossevent.m_18861_();
               int l = this.g.h.a(component);
               int i1 = i / 2 - l / 2;
               int j1 = j - 9;
               int col = 16777215;
               if (Config.isCustomColors()) {
                  col = CustomColors.getBossTextColor(col);
               }

               graphicsIn.b(this.g.h, component, i1, j1, col);
            }

            j += increment;
            if (j >= graphicsIn.b() / 3) {
               break;
            }
         }

         this.g.m_91307_().m_7238_();
      }
   }

   private void a(GuiGraphics graphicsIn, int xIn, int yIn, C_459_ eventIn) {
      this.a(graphicsIn, xIn, yIn, eventIn, 182, c, e);
      int i = Mth.b(eventIn.m_142717_(), 0, 182);
      if (i > 0) {
         this.a(graphicsIn, xIn, yIn, eventIn, i, d, f);
      }
   }

   private void a(
      GuiGraphics graphicsIn, int xIn, int yIn, C_459_ eventIn, int widthIn, ResourceLocation[] barLocationsIn, ResourceLocation[] overlayLocationsIn
   ) {
      RenderSystem.enableBlend();
      graphicsIn.a(barLocationsIn[eventIn.m_18862_().ordinal()], 182, 5, 0, 0, xIn, yIn, widthIn, 5);
      if (eventIn.m_18863_() != C_461_.PROGRESS) {
         graphicsIn.a(overlayLocationsIn[eventIn.m_18863_().ordinal() - 1], 182, 5, 0, 0, xIn, yIn, widthIn, 5);
      }

      RenderSystem.disableBlend();
   }

   public void a(C_5044_ packetIn) {
      packetIn.m_178643_(
         new C_141845_() {
            public void m_142107_(
               UUID p_142107_1_,
               C_4996_ p_142107_2_,
               float p_142107_3_,
               C_460_ p_142107_4_,
               C_461_ p_142107_5_,
               boolean p_142107_6_,
               boolean p_142107_7_,
               boolean p_142107_8_
            ) {
               BossHealthOverlay.this.h
                  .put(p_142107_1_, new C_3466_(p_142107_1_, p_142107_2_, p_142107_3_, p_142107_4_, p_142107_5_, p_142107_6_, p_142107_7_, p_142107_8_));
            }

            public void m_142751_(UUID p_142751_1_) {
               BossHealthOverlay.this.h.remove(p_142751_1_);
            }

            public void m_142653_(UUID p_142653_1_, float p_142653_2_) {
               ((C_3466_)BossHealthOverlay.this.h.get(p_142653_1_)).m_142711_(p_142653_2_);
            }

            public void m_142366_(UUID p_142366_1_, C_4996_ p_142366_2_) {
               ((C_3466_)BossHealthOverlay.this.h.get(p_142366_1_)).m_6456_(p_142366_2_);
            }

            public void m_142358_(UUID p_142358_1_, C_460_ p_142358_2_, C_461_ p_142358_3_) {
               C_3466_ lerpingbossevent = (C_3466_)BossHealthOverlay.this.h.get(p_142358_1_);
               lerpingbossevent.m_6451_(p_142358_2_);
               lerpingbossevent.m_5648_(p_142358_3_);
            }

            public void m_142513_(UUID p_142513_1_, boolean p_142513_2_, boolean p_142513_3_, boolean p_142513_4_) {
               C_3466_ lerpingbossevent = (C_3466_)BossHealthOverlay.this.h.get(p_142513_1_);
               lerpingbossevent.m_7003_(p_142513_2_);
               lerpingbossevent.m_7005_(p_142513_3_);
               lerpingbossevent.m_7006_(p_142513_4_);
            }
         }
      );
   }

   public void a() {
      this.h.clear();
   }

   public boolean b() {
      if (!this.h.isEmpty()) {
         for (C_459_ bossevent : this.h.values()) {
            if (bossevent.m_18865_()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean c() {
      if (!this.h.isEmpty()) {
         for (C_459_ bossevent : this.h.values()) {
            if (bossevent.m_18864_()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean d() {
      if (!this.h.isEmpty()) {
         for (C_459_ bossevent : this.h.values()) {
            if (bossevent.m_18866_()) {
               return true;
            }
         }
      }

      return false;
   }

   public String getBossName() {
      if (!this.h.isEmpty()) {
         for (C_459_ bossevent : this.h.values()) {
            C_4996_ name = bossevent.m_18861_();
            if (name != null && name.m_214077_() instanceof C_213523_ tranCont) {
               return tranCont.m_237508_();
            }
         }
      }

      return null;
   }
}
