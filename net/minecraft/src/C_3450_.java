package net.minecraft.src;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.src.C_459_.C_461_;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorMethod;

public class C_3450_ {
   private static final int f_168805_ = 182;
   private static final int f_168806_ = 5;
   private static final C_5265_[] f_290930_ = new C_5265_[]{C_5265_.m_340282_("boss_bar/pink_background"), C_5265_.m_340282_("boss_bar/blue_background"), C_5265_.m_340282_("boss_bar/red_background"), C_5265_.m_340282_("boss_bar/green_background"), C_5265_.m_340282_("boss_bar/yellow_background"), C_5265_.m_340282_("boss_bar/purple_background"), C_5265_.m_340282_("boss_bar/white_background")};
   private static final C_5265_[] f_290575_ = new C_5265_[]{C_5265_.m_340282_("boss_bar/pink_progress"), C_5265_.m_340282_("boss_bar/blue_progress"), C_5265_.m_340282_("boss_bar/red_progress"), C_5265_.m_340282_("boss_bar/green_progress"), C_5265_.m_340282_("boss_bar/yellow_progress"), C_5265_.m_340282_("boss_bar/purple_progress"), C_5265_.m_340282_("boss_bar/white_progress")};
   private static final C_5265_[] f_291445_ = new C_5265_[]{C_5265_.m_340282_("boss_bar/notched_6_background"), C_5265_.m_340282_("boss_bar/notched_10_background"), C_5265_.m_340282_("boss_bar/notched_12_background"), C_5265_.m_340282_("boss_bar/notched_20_background")};
   private static final C_5265_[] f_290640_ = new C_5265_[]{C_5265_.m_340282_("boss_bar/notched_6_progress"), C_5265_.m_340282_("boss_bar/notched_10_progress"), C_5265_.m_340282_("boss_bar/notched_12_progress"), C_5265_.m_340282_("boss_bar/notched_20_progress")};
   private final C_3391_ f_93698_;
   final Map f_93699_ = Maps.newLinkedHashMap();

   public C_3450_(C_3391_ clientIn) {
      this.f_93698_ = clientIn;
   }

   public void m_280652_(C_279497_ graphicsIn) {
      if (!this.f_93699_.isEmpty()) {
         this.f_93698_.m_91307_().m_6180_("bossHealth");
         int i = graphicsIn.m_280182_();
         int j = 12;
         Iterator var4 = this.f_93699_.values().iterator();

         while(var4.hasNext()) {
            C_3466_ lerpingbossevent = (C_3466_)var4.next();
            int k = i / 2 - 91;
            boolean render = true;
            int increment = 19;
            if (Reflector.ForgeHooksClient_onCustomizeBossEventProgress.exists()) {
               ReflectorMethod var10000 = Reflector.ForgeHooksClient_onCustomizeBossEventProgress;
               Object[] var10001 = new Object[]{graphicsIn, this.f_93698_.m_91268_(), lerpingbossevent, k, j, null};
               Objects.requireNonNull(this.f_93698_.f_91062_);
               var10001[5] = 10 + 9;
               Object event = var10000.call(var10001);
               render = !Reflector.callBoolean(event, Reflector.Event_isCanceled);
               increment = Reflector.callInt(event, Reflector.CustomizeGuiOverlayEvent_BossEventProgress_getIncrement);
            }

            if (render) {
               this.m_280106_(graphicsIn, k, j, lerpingbossevent);
               C_4996_ component = lerpingbossevent.i();
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

   private void m_280106_(C_279497_ graphicsIn, int xIn, int yIn, C_459_ eventIn) {
      this.m_280093_(graphicsIn, xIn, yIn, eventIn, 182, f_290930_, f_291445_);
      int i = C_188_.m_295919_(eventIn.m_142717_(), 0, 182);
      if (i > 0) {
         this.m_280093_(graphicsIn, xIn, yIn, eventIn, i, f_290575_, f_290640_);
      }

   }

   private void m_280093_(C_279497_ graphicsIn, int xIn, int yIn, C_459_ eventIn, int widthIn, C_5265_[] barLocationsIn, C_5265_[] overlayLocationsIn) {
      RenderSystem.enableBlend();
      graphicsIn.m_294122_(barLocationsIn[eventIn.m_18862_().ordinal()], 182, 5, 0, 0, xIn, yIn, widthIn, 5);
      if (eventIn.m_18863_() != C_461_.PROGRESS) {
         graphicsIn.m_294122_(overlayLocationsIn[eventIn.m_18863_().ordinal() - 1], 182, 5, 0, 0, xIn, yIn, widthIn, 5);
      }

      RenderSystem.disableBlend();
   }

   public void m_93711_(C_5044_ packetIn) {
      packetIn.m_178643_(new C_5044_.C_141845_() {
         public void m_142107_(UUID p_142107_1_, C_4996_ p_142107_2_, float p_142107_3_, C_459_.C_460_ p_142107_4_, C_459_.C_461_ p_142107_5_, boolean p_142107_6_, boolean p_142107_7_, boolean p_142107_8_) {
            C_3450_.this.f_93699_.put(p_142107_1_, new C_3466_(p_142107_1_, p_142107_2_, p_142107_3_, p_142107_4_, p_142107_5_, p_142107_6_, p_142107_7_, p_142107_8_));
         }

         public void m_142751_(UUID p_142751_1_) {
            C_3450_.this.f_93699_.remove(p_142751_1_);
         }

         public void m_142653_(UUID p_142653_1_, float p_142653_2_) {
            ((C_3466_)C_3450_.this.f_93699_.get(p_142653_1_)).m_142711_(p_142653_2_);
         }

         public void m_142366_(UUID p_142366_1_, C_4996_ p_142366_2_) {
            ((C_3466_)C_3450_.this.f_93699_.get(p_142366_1_)).a(p_142366_2_);
         }

         public void m_142358_(UUID p_142358_1_, C_459_.C_460_ p_142358_2_, C_459_.C_461_ p_142358_3_) {
            C_3466_ lerpingbossevent = (C_3466_)C_3450_.this.f_93699_.get(p_142358_1_);
            lerpingbossevent.a(p_142358_2_);
            lerpingbossevent.a(p_142358_3_);
         }

         public void m_142513_(UUID p_142513_1_, boolean p_142513_2_, boolean p_142513_3_, boolean p_142513_4_) {
            C_3466_ lerpingbossevent = (C_3466_)C_3450_.this.f_93699_.get(p_142513_1_);
            lerpingbossevent.a(p_142513_2_);
            lerpingbossevent.b(p_142513_3_);
            lerpingbossevent.c(p_142513_4_);
         }
      });
   }

   public void m_93703_() {
      this.f_93699_.clear();
   }

   public boolean m_93713_() {
      if (!this.f_93699_.isEmpty()) {
         Iterator var1 = this.f_93699_.values().iterator();

         while(var1.hasNext()) {
            C_459_ bossevent = (C_459_)var1.next();
            if (bossevent.m_18865_()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean m_93714_() {
      if (!this.f_93699_.isEmpty()) {
         Iterator var1 = this.f_93699_.values().iterator();

         while(var1.hasNext()) {
            C_459_ bossevent = (C_459_)var1.next();
            if (bossevent.m_18864_()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean m_93715_() {
      if (!this.f_93699_.isEmpty()) {
         Iterator var1 = this.f_93699_.values().iterator();

         while(var1.hasNext()) {
            C_459_ bossevent = (C_459_)var1.next();
            if (bossevent.m_18866_()) {
               return true;
            }
         }
      }

      return false;
   }

   public String getBossName() {
      if (!this.f_93699_.isEmpty()) {
         Iterator var1 = this.f_93699_.values().iterator();

         while(var1.hasNext()) {
            C_459_ bossevent = (C_459_)var1.next();
            C_4996_ name = bossevent.m_18861_();
            if (name != null) {
               C_213506_ cont = name.m_214077_();
               if (cont instanceof C_213523_) {
                  C_213523_ tranCont = (C_213523_)cont;
                  return tranCont.m_237508_();
               }
            }
         }
      }

      return null;
   }
}
