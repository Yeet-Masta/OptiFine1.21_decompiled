package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Window;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Optionull;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ArrayListDeque;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.ChatVisiblity;
import org.slf4j.Logger;

public class ChatComponent {
   private static final Logger f_93757_ = LogUtils.getLogger();
   private static final int f_168843_ = 100;
   private static final int f_240336_ = -1;
   private static final int f_240385_ = 4;
   private static final int f_240337_ = 4;
   private static final int f_244226_ = 40;
   private static final int f_243892_ = 60;
   private static final Component f_244190_;
   private final Minecraft f_93758_;
   private final ArrayListDeque f_93759_ = new ArrayListDeque(100);
   private final List f_93760_ = Lists.newArrayList();
   private final List f_93761_ = Lists.newArrayList();
   private int f_93763_;
   private boolean f_93764_;
   private final List f_244052_ = new ArrayList();
   private int lastChatWidth = 0;

   public ChatComponent(Minecraft mcIn) {
      this.f_93758_ = mcIn;
      this.f_93759_.addAll(mcIn.m_294504_().m_295381_());
   }

   public void m_246602_() {
      if (!this.f_244052_.isEmpty()) {
         this.m_246025_();
      }

   }

   public void m_280165_(GuiGraphics graphicsIn, int updateCounter, int xIn, int yIn, boolean noFadeIn) {
      int chatWidth = this.m_93813_();
      if (this.lastChatWidth != chatWidth) {
         this.lastChatWidth = chatWidth;
         this.m_93769_();
      }

      if (!this.m_93817_()) {
         int i = this.m_93816_();
         int j = this.f_93761_.size();
         if (j > 0) {
            this.f_93758_.m_91307_().m_6180_("chat");
            float f = (float)this.m_93815_();
            int k = Mth.m_14167_((float)this.m_93813_() / f);
            int l = graphicsIn.m_280206_();
            graphicsIn.m_280168_().m_85836_();
            graphicsIn.m_280168_().m_85841_(f, f, 1.0F);
            graphicsIn.m_280168_().m_252880_(4.0F, 0.0F, 0.0F);
            int i1 = Mth.m_14143_((float)(l - 40) / f);
            int j1 = this.m_246107_(this.m_240491_((double)xIn), this.m_240485_((double)yIn));
            double d0 = (Double)this.f_93758_.f_91066_.m_232098_().m_231551_() * 0.8999999761581421 + 0.10000000149011612;
            double d1 = (Double)this.f_93758_.f_91066_.m_232104_().m_231551_();
            double d2 = (Double)this.f_93758_.f_91066_.m_232101_().m_231551_();
            int k1 = this.m_240691_();
            int l1 = (int)Math.round(-8.0 * (d2 + 1.0) + 4.0 * d2);
            int i2 = 0;

            int j6;
            int j3;
            int k3;
            int i4;
            for(int j2 = 0; j2 + this.f_93763_ < this.f_93761_.size() && j2 < i; ++j2) {
               int k2 = j2 + this.f_93763_;
               GuiMessage.Line guimessage$line = (GuiMessage.Line)this.f_93761_.get(k2);
               if (guimessage$line != null) {
                  j6 = updateCounter - guimessage$line.f_240350_();
                  if (j6 < 200 || noFadeIn) {
                     double d3 = noFadeIn ? 1.0 : m_93775_(j6);
                     j3 = (int)(255.0 * d3 * d0);
                     k3 = (int)(255.0 * d3 * d1);
                     ++i2;
                     if (j3 > 3) {
                        int l3 = false;
                        i4 = i1 - j2 * k1;
                        int j4 = i4 + l1;
                        if (this.f_93758_.f_91066_.ofChatBackground == 5) {
                           k = this.f_93758_.f_91062_.m_92724_(guimessage$line.f_240339_()) - 2;
                        }

                        if (this.f_93758_.f_91066_.ofChatBackground != 3) {
                           graphicsIn.m_280509_(-4, i4 - k1, 0 + k + 4 + 4, i4, k3 << 24);
                        }

                        GuiMessageTag guimessagetag = guimessage$line.f_240351_();
                        if (guimessagetag != null) {
                           int k4 = guimessagetag.f_240386_() | j3 << 24;
                           graphicsIn.m_280509_(-4, i4 - k1, -2, i4, k4);
                           if (k2 == j1 && guimessagetag.f_240355_() != null) {
                              int l4 = this.m_240495_(guimessage$line);
                              int i5 = j4 + 9;
                              this.m_280134_(graphicsIn, l4, i5, guimessagetag.f_240355_());
                           }
                        }

                        graphicsIn.m_280168_().m_85836_();
                        graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, 50.0F);
                        if (!this.f_93758_.f_91066_.ofChatShadow) {
                           graphicsIn.m_280648_(this.f_93758_.f_91062_, guimessage$line.f_240339_(), 0, j4, 16777215 + (j3 << 24));
                        } else {
                           graphicsIn.m_280648_(this.f_93758_.f_91062_, guimessage$line.f_240339_(), 0, j4, 16777215 + (j3 << 24));
                        }

                        graphicsIn.m_280168_().m_85849_();
                     }
                  }
               }
            }

            long j5 = this.f_93758_.m_240442_().m_242024_();
            int l5;
            if (j5 > 0L) {
               l5 = (int)(128.0 * d0);
               j6 = (int)(255.0 * d1);
               graphicsIn.m_280168_().m_85836_();
               graphicsIn.m_280168_().m_252880_(0.0F, (float)i1, 0.0F);
               graphicsIn.m_280509_(-2, 0, k + 4, 9, j6 << 24);
               graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, 50.0F);
               graphicsIn.m_280430_(this.f_93758_.f_91062_, Component.m_237110_("chat.queue", new Object[]{j5}), 0, 1, 16777215 + (l5 << 24));
               graphicsIn.m_280168_().m_85849_();
            }

            if (noFadeIn) {
               l5 = this.m_240691_();
               j6 = j * l5;
               int k6 = i2 * l5;
               int i3 = this.f_93763_ * k6 / j - i1;
               j3 = k6 * k6 / j6;
               if (j6 != k6) {
                  k3 = i3 > 0 ? 170 : 96;
                  int j7 = this.f_93764_ ? 13382451 : 3355562;
                  i4 = k + 4;
                  graphicsIn.m_280046_(i4, -i3, i4 + 2, -i3 - j3, 100, j7 + (k3 << 24));
                  graphicsIn.m_280046_(i4 + 2, -i3, i4 + 1, -i3 - j3, 100, 13421772 + (k3 << 24));
               }
            }

            graphicsIn.m_280168_().m_85849_();
            this.f_93758_.m_91307_().m_7238_();
         }
      }

   }

   private void m_280134_(GuiGraphics p_280134_1_, int p_280134_2_, int p_280134_3_, GuiMessageTag.Icon p_280134_4_) {
      int i = p_280134_3_ - p_280134_4_.f_240372_ - 1;
      p_280134_4_.m_280252_(p_280134_1_, p_280134_2_, i);
   }

   private int m_240495_(GuiMessage.Line p_240495_1_) {
      return this.f_93758_.f_91062_.m_92724_(p_240495_1_.f_240339_()) + 4;
   }

   private boolean m_93817_() {
      return this.f_93758_.f_91066_.m_232090_().m_231551_() == ChatVisiblity.HIDDEN;
   }

   private static double m_93775_(int counterIn) {
      double d0 = (double)counterIn / 200.0;
      d0 = 1.0 - d0;
      d0 *= 10.0;
      d0 = Mth.m_14008_(d0, 0.0, 1.0);
      return d0 * d0;
   }

   public void m_93795_(boolean clearSentMsgHistory) {
      this.f_93758_.m_240442_().m_241954_();
      this.f_244052_.clear();
      this.f_93761_.clear();
      this.f_93760_.clear();
      if (clearSentMsgHistory) {
         this.f_93759_.clear();
         this.f_93759_.addAll(this.f_93758_.m_294504_().m_295381_());
      }

   }

   public void m_93785_(Component chatComponent) {
      this.m_240964_(chatComponent, (MessageSignature)null, this.f_93758_.m_257720_() ? GuiMessageTag.m_257673_() : GuiMessageTag.m_240701_());
   }

   public void m_240964_(Component p_240964_1_, @Nullable MessageSignature p_240964_2_, @Nullable GuiMessageTag p_240964_3_) {
      GuiMessage guimessage = new GuiMessage(this.f_93758_.f_91065_.m_93079_(), p_240964_1_, p_240964_2_, p_240964_3_);
      this.m_242648_(guimessage);
      this.m_320310_(guimessage);
      this.m_319022_(guimessage);
   }

   private void m_242648_(GuiMessage messageIn) {
      String s = messageIn.f_240363_().getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n");
      String s1 = (String)Optionull.m_269382_(messageIn.f_240352_(), GuiMessageTag::f_240342_);
      if (s1 != null) {
         f_93757_.info("[{}] [CHAT] {}", s1, s);
      } else {
         f_93757_.info("[CHAT] {}", s);
      }

   }

   private void m_320310_(GuiMessage messageIn) {
      int i = Mth.m_14107_((double)this.m_93813_() / this.m_93815_());
      GuiMessageTag.Icon guimessagetag$icon = messageIn.m_324870_();
      if (guimessagetag$icon != null) {
         i -= guimessagetag$icon.f_240358_ + 4 + 2;
      }

      List list = ComponentRenderUtils.m_94005_(messageIn.f_240363_(), i, this.f_93758_.f_91062_);
      boolean flag = this.m_93818_();

      for(int j = 0; j < list.size(); ++j) {
         FormattedCharSequence formattedcharsequence = (FormattedCharSequence)list.get(j);
         if (flag && this.f_93763_ > 0) {
            this.f_93764_ = true;
            this.m_205360_(1);
         }

         boolean flag1 = j == list.size() - 1;
         this.f_93761_.add(0, new GuiMessage.Line(messageIn.f_90786_(), formattedcharsequence, messageIn.f_240352_(), flag1));
      }

      while(this.f_93761_.size() > 100) {
         this.f_93761_.remove(this.f_93761_.size() - 1);
      }

   }

   private void m_319022_(GuiMessage messageIn) {
      this.f_93760_.add(0, messageIn);

      while(this.f_93760_.size() > 100) {
         this.f_93760_.remove(this.f_93760_.size() - 1);
      }

   }

   private void m_246025_() {
      int i = this.f_93758_.f_91065_.m_93079_();
      this.f_244052_.removeIf((p_245406_2_) -> {
         return i >= p_245406_2_.f_244411_() ? this.m_245423_(p_245406_2_.f_244186_()) == null : false;
      });
   }

   public void m_240953_(MessageSignature p_240953_1_) {
      DelayedMessageDeletion chatcomponent$delayedmessagedeletion = this.m_245423_(p_240953_1_);
      if (chatcomponent$delayedmessagedeletion != null) {
         this.f_244052_.add(chatcomponent$delayedmessagedeletion);
      }

   }

   @Nullable
   private DelayedMessageDeletion m_245423_(MessageSignature signatureIn) {
      int i = this.f_93758_.f_91065_.m_93079_();
      ListIterator listiterator = this.f_93760_.listIterator();

      GuiMessage guimessage;
      do {
         if (!listiterator.hasNext()) {
            return null;
         }

         guimessage = (GuiMessage)listiterator.next();
      } while(!signatureIn.equals(guimessage.f_240905_()));

      if (signatureIn.equals(LevelRenderer.loadVisibleChunksMessageId)) {
         listiterator.remove();
         this.m_324364_();
         return null;
      } else {
         int j = guimessage.f_90786_() + 60;
         if (i >= j) {
            listiterator.set(this.m_246885_(guimessage));
            this.m_324364_();
            return null;
         } else {
            return new DelayedMessageDeletion(signatureIn, j);
         }
      }
   }

   private GuiMessage m_246885_(GuiMessage messageIn) {
      return new GuiMessage(messageIn.f_90786_(), f_244190_, (MessageSignature)null, GuiMessageTag.m_240701_());
   }

   public void m_93769_() {
      this.m_93810_();
      this.m_324364_();
   }

   private void m_324364_() {
      this.f_93761_.clear();
      Iterator var1 = Lists.reverse(this.f_93760_).iterator();

      while(var1.hasNext()) {
         GuiMessage guimessage = (GuiMessage)var1.next();
         this.m_320310_(guimessage);
      }

   }

   public ArrayListDeque m_93797_() {
      return this.f_93759_;
   }

   public void m_93783_(String message) {
      if (!message.equals(this.f_93759_.peekLast())) {
         if (this.f_93759_.size() >= 100) {
            this.f_93759_.removeFirst();
         }

         this.f_93759_.addLast(message);
      }

      if (message.startsWith("/")) {
         this.f_93758_.m_294504_().m_294229_(message);
      }

   }

   public void m_93810_() {
      this.f_93763_ = 0;
      this.f_93764_ = false;
   }

   public void m_205360_(int p_205360_1_) {
      this.f_93763_ += p_205360_1_;
      int i = this.f_93761_.size();
      if (this.f_93763_ > i - this.m_93816_()) {
         this.f_93763_ = i - this.m_93816_();
      }

      if (this.f_93763_ <= 0) {
         this.f_93763_ = 0;
         this.f_93764_ = false;
      }

   }

   public boolean m_93772_(double mouseX, double mouseY) {
      if (this.m_93818_() && !this.f_93758_.f_91066_.f_92062_ && !this.m_93817_()) {
         ChatListener chatlistener = this.f_93758_.m_240442_();
         if (chatlistener.m_242024_() == 0L) {
            return false;
         } else {
            double d0 = mouseX - 2.0;
            double d1 = (double)this.f_93758_.m_91268_().m_85446_() - mouseY - 40.0;
            if (d0 <= (double)Mth.m_14107_((double)this.m_93813_() / this.m_93815_()) && d1 < 0.0 && d1 > (double)Mth.m_14107_(-9.0 * this.m_93815_())) {
               chatlistener.m_240711_();
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   @Nullable
   public Style m_93800_(double mouseX, double mouseY) {
      double d0 = this.m_240491_(mouseX);
      double d1 = this.m_240485_(mouseY);
      int i = this.m_247428_(d0, d1);
      if (i >= 0 && i < this.f_93761_.size()) {
         GuiMessage.Line guimessage$line = (GuiMessage.Line)this.f_93761_.get(i);
         return this.f_93758_.f_91062_.m_92865_().m_92338_(guimessage$line.f_240339_(), Mth.m_14107_(d0));
      } else {
         return null;
      }
   }

   @Nullable
   public GuiMessageTag m_240463_(double p_240463_1_, double p_240463_3_) {
      double d0 = this.m_240491_(p_240463_1_);
      double d1 = this.m_240485_(p_240463_3_);
      int i = this.m_246107_(d0, d1);
      if (i >= 0 && i < this.f_93761_.size()) {
         GuiMessage.Line guimessage$line = (GuiMessage.Line)this.f_93761_.get(i);
         GuiMessageTag guimessagetag = guimessage$line.f_240351_();
         if (guimessagetag != null && this.m_240447_(d0, guimessage$line, guimessagetag)) {
            return guimessagetag;
         }
      }

      return null;
   }

   private boolean m_240447_(double p_240447_1_, GuiMessage.Line p_240447_3_, GuiMessageTag p_240447_4_) {
      if (p_240447_1_ < 0.0) {
         return true;
      } else {
         GuiMessageTag.Icon guimessagetag$icon = p_240447_4_.f_240355_();
         if (guimessagetag$icon == null) {
            return false;
         } else {
            int i = this.m_240495_(p_240447_3_);
            int j = i + guimessagetag$icon.f_240358_;
            return p_240447_1_ >= (double)i && p_240447_1_ <= (double)j;
         }
      }
   }

   private double m_240491_(double p_240491_1_) {
      return p_240491_1_ / this.m_93815_() - 4.0;
   }

   private double m_240485_(double p_240485_1_) {
      double d0 = (double)this.f_93758_.m_91268_().m_85446_() - p_240485_1_ - 40.0;
      return d0 / (this.m_93815_() * (double)this.m_240691_());
   }

   private int m_246107_(double p_246107_1_, double p_246107_3_) {
      int i = this.m_247428_(p_246107_1_, p_246107_3_);
      if (i == -1) {
         return -1;
      } else {
         while(i >= 0) {
            if (((GuiMessage.Line)this.f_93761_.get(i)).f_240367_()) {
               return i;
            }

            --i;
         }

         return i;
      }
   }

   private int m_247428_(double p_247428_1_, double p_247428_3_) {
      if (this.m_93818_() && !this.m_93817_()) {
         if (!(p_247428_1_ < -4.0) && !(p_247428_1_ > (double)Mth.m_14107_((double)this.m_93813_() / this.m_93815_()))) {
            int i = Math.min(this.m_93816_(), this.f_93761_.size());
            if (p_247428_3_ >= 0.0 && p_247428_3_ < (double)i) {
               int j = Mth.m_14107_(p_247428_3_ + (double)this.f_93763_);
               if (j >= 0 && j < this.f_93761_.size()) {
                  return j;
               }
            }

            return -1;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public boolean m_93818_() {
      return this.f_93758_.f_91080_ instanceof ChatScreen;
   }

   public int m_93813_() {
      int width = m_93798_((Double)this.f_93758_.f_91066_.m_232113_().m_231551_());
      Window win = Minecraft.m_91087_().m_91268_();
      int widthWindow = (int)((double)(win.m_85441_() - 3) / win.m_85449_());
      return Mth.m_14045_(width, 0, widthWindow);
   }

   public int m_93814_() {
      return m_93811_(this.m_93818_() ? (Double)this.f_93758_.f_91066_.m_232117_().m_231551_() : (Double)this.f_93758_.f_91066_.m_232116_().m_231551_());
   }

   public double m_93815_() {
      return (Double)this.f_93758_.f_91066_.m_232110_().m_231551_();
   }

   public static int m_93798_(double valueIn) {
      int i = true;
      int j = true;
      return Mth.m_14107_(valueIn * 280.0 + 40.0);
   }

   public static int m_93811_(double valueIn) {
      int i = true;
      int j = true;
      return Mth.m_14107_(valueIn * 160.0 + 20.0);
   }

   public static double m_232477_() {
      int i = true;
      int j = true;
      return 70.0 / (double)(m_93811_(1.0) - 20);
   }

   public int m_93816_() {
      return this.m_93814_() / this.m_240691_();
   }

   private int m_240691_() {
      return (int)(9.0 * ((Double)this.f_93758_.f_91066_.m_232101_().m_231551_() + 1.0));
   }

   public State m_322825_() {
      return new State(List.copyOf(this.f_93760_), List.copyOf(this.f_93759_), List.copyOf(this.f_244052_));
   }

   public void m_324317_(State stateIn) {
      this.f_93759_.clear();
      this.f_93759_.addAll(stateIn.f_313945_);
      this.f_244052_.clear();
      this.f_244052_.addAll(stateIn.f_314978_);
      this.f_93760_.clear();
      this.f_93760_.addAll(stateIn.f_315793_);
      this.m_324364_();
   }

   static {
      f_244190_ = Component.m_237115_("chat.deleted_marker").m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC});
   }

   static record DelayedMessageDeletion(MessageSignature f_244186_, int f_244411_) {
      DelayedMessageDeletion(MessageSignature signature, int deletableAfter) {
         this.f_244186_ = signature;
         this.f_244411_ = deletableAfter;
      }

      public MessageSignature f_244186_() {
         return this.f_244186_;
      }

      public int f_244411_() {
         return this.f_244411_;
      }
   }

   public static class State {
      final List f_315793_;
      final List f_313945_;
      final List f_314978_;

      public State(List messagesIn, List historyIn, List deletionsIn) {
         this.f_315793_ = messagesIn;
         this.f_313945_ = historyIn;
         this.f_314978_ = deletionsIn;
      }
   }
}
