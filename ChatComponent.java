import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;
import net.minecraft.src.C_1139_;
import net.minecraft.src.C_178_;
import net.minecraft.src.C_213508_;
import net.minecraft.src.C_240332_;
import net.minecraft.src.C_240334_;
import net.minecraft.src.C_268411_;
import net.minecraft.src.C_290101_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3459_;
import net.minecraft.src.C_3538_;
import net.minecraft.src.C_4856_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5020_;
import net.minecraft.src.C_240334_.C_240333_;
import org.slf4j.Logger;

public class ChatComponent {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = 100;
   private static final int c = -1;
   private static final int d = 4;
   private static final int e = 4;
   private static final int f = 40;
   private static final int g = 60;
   private static final C_4996_ h = C_4996_.m_237115_("chat.deleted_marker").m_130944_(new C_4856_[]{C_4856_.GRAY, C_4856_.ITALIC});
   private final C_3391_ i;
   private final C_290101_<String> j = new C_290101_(100);
   private final List<GuiMessage> k = Lists.newArrayList();
   private final List<GuiMessage.a> l = Lists.newArrayList();
   private int m;
   private boolean n;
   private final List<ChatComponent.a> o = new ArrayList();
   private int lastChatWidth = 0;

   public ChatComponent(C_3391_ mcIn) {
      this.i = mcIn;
      this.j.addAll(mcIn.m_294504_().m_295381_());
   }

   public void a() {
      if (!this.o.isEmpty()) {
         this.m();
      }
   }

   public void a(GuiGraphics graphicsIn, int updateCounter, int xIn, int yIn, boolean noFadeIn) {
      int chatWidth = this.f();
      if (this.lastChatWidth != chatWidth) {
         this.lastChatWidth = chatWidth;
         this.b();
      }

      if (!this.l()) {
         int i = this.j();
         int j = this.l.size();
         if (j > 0) {
            this.i.m_91307_().m_6180_("chat");
            float f = (float)this.h();
            int k = Mth.f((float)this.f() / f);
            int l = graphicsIn.b();
            graphicsIn.c().a();
            graphicsIn.c().b(f, f, 1.0F);
            graphicsIn.c().a(4.0F, 0.0F, 0.0F);
            int i1 = Mth.d((float)(l - 40) / f);
            int j1 = this.d(this.c((double)xIn), this.d((double)yIn));
            double d0 = this.i.m.n().c() * 0.9F + 0.1F;
            double d1 = this.i.m.r().c();
            double d2 = this.i.m.o().c();
            int k1 = this.o();
            int l1 = (int)Math.round(-8.0 * (d2 + 1.0) + 4.0 * d2);
            int i2 = 0;

            for (int j2 = 0; j2 + this.m < this.l.size() && j2 < i; j2++) {
               int k2 = j2 + this.m;
               GuiMessage.a guimessage$line = (GuiMessage.a)this.l.get(k2);
               if (guimessage$line != null) {
                  int l2 = updateCounter - guimessage$line.a();
                  if (l2 < 200 || noFadeIn) {
                     double d3 = noFadeIn ? 1.0 : b(l2);
                     int j3 = (int)(255.0 * d3 * d0);
                     int k3 = (int)(255.0 * d3 * d1);
                     i2++;
                     if (j3 > 3) {
                        int l3 = 0;
                        int i4 = i1 - j2 * k1;
                        int j4 = i4 + l1;
                        if (this.i.m.ofChatBackground == 5) {
                           k = this.i.h.a(guimessage$line.b()) - 2;
                        }

                        if (this.i.m.ofChatBackground != 3) {
                           graphicsIn.a(-4, i4 - k1, 0 + k + 4 + 4, i4, k3 << 24);
                        }

                        C_240334_ guimessagetag = guimessage$line.c();
                        if (guimessagetag != null) {
                           int k4 = guimessagetag.f_240386_() | j3 << 24;
                           graphicsIn.a(-4, i4 - k1, -2, i4, k4);
                           if (k2 == j1 && guimessagetag.f_240355_() != null) {
                              int l4 = this.a(guimessage$line);
                              int i5 = j4 + 9;
                              this.a(graphicsIn, l4, i5, guimessagetag.f_240355_());
                           }
                        }

                        graphicsIn.c().a();
                        graphicsIn.c().a(0.0F, 0.0F, 50.0F);
                        if (!this.i.m.ofChatShadow) {
                           graphicsIn.b(this.i.h, guimessage$line.b(), 0, j4, 16777215 + (j3 << 24));
                        } else {
                           graphicsIn.b(this.i.h, guimessage$line.b(), 0, j4, 16777215 + (j3 << 24));
                        }

                        graphicsIn.c().b();
                     }
                  }
               }
            }

            long j5 = this.i.m_240442_().m_242024_();
            if (j5 > 0L) {
               int k5 = (int)(128.0 * d0);
               int i6 = (int)(255.0 * d1);
               graphicsIn.c().a();
               graphicsIn.c().a(0.0F, (float)i1, 0.0F);
               graphicsIn.a(-2, 0, k + 4, 9, i6 << 24);
               graphicsIn.c().a(0.0F, 0.0F, 50.0F);
               graphicsIn.b(this.i.h, C_4996_.m_237110_("chat.queue", new Object[]{j5}), 0, 1, 16777215 + (k5 << 24));
               graphicsIn.c().b();
            }

            if (noFadeIn) {
               int l5 = this.o();
               int j6 = j * l5;
               int k6 = i2 * l5;
               int i3 = this.m * k6 / j - i1;
               int l6 = k6 * k6 / j6;
               if (j6 != k6) {
                  int i7 = i3 > 0 ? 170 : 96;
                  int j7 = this.n ? 13382451 : 3355562;
                  int k7 = k + 4;
                  graphicsIn.a(k7, -i3, k7 + 2, -i3 - l6, 100, j7 + (i7 << 24));
                  graphicsIn.a(k7 + 2, -i3, k7 + 1, -i3 - l6, 100, 13421772 + (i7 << 24));
               }
            }

            graphicsIn.c().b();
            this.i.m_91307_().m_7238_();
         }
      }
   }

   private void a(GuiGraphics p_280134_1_, int p_280134_2_, int p_280134_3_, C_240333_ p_280134_4_) {
      int i = p_280134_3_ - p_280134_4_.f_240372_ - 1;
      p_280134_4_.a(p_280134_1_, p_280134_2_, i);
   }

   private int a(GuiMessage.a p_240495_1_) {
      return this.i.h.a(p_240495_1_.b()) + 4;
   }

   private boolean l() {
      return this.i.m.m().c() == C_1139_.HIDDEN;
   }

   private static double b(int counterIn) {
      double d0 = (double)counterIn / 200.0;
      d0 = 1.0 - d0;
      d0 *= 10.0;
      d0 = Mth.a(d0, 0.0, 1.0);
      return d0 * d0;
   }

   public void a(boolean clearSentMsgHistory) {
      this.i.m_240442_().m_241954_();
      this.o.clear();
      this.l.clear();
      this.k.clear();
      if (clearSentMsgHistory) {
         this.j.clear();
         this.j.addAll(this.i.m_294504_().m_295381_());
      }
   }

   public void a(C_4996_ chatComponent) {
      this.a(chatComponent, null, this.i.m_257720_() ? C_240334_.m_257673_() : C_240334_.m_240701_());
   }

   public void a(C_4996_ p_240964_1_, @Nullable C_213508_ p_240964_2_, @Nullable C_240334_ p_240964_3_) {
      GuiMessage guimessage = new GuiMessage(this.i.l.e(), p_240964_1_, p_240964_2_, p_240964_3_);
      this.a(guimessage);
      this.b(guimessage);
      this.c(guimessage);
   }

   private void a(GuiMessage messageIn) {
      String s = messageIn.c().getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n");
      String s1 = (String)C_268411_.m_269382_(messageIn.e(), C_240334_::f_240342_);
      if (s1 != null) {
         a.info("[{}] [CHAT] {}", s1, s);
      } else {
         a.info("[CHAT] {}", s);
      }
   }

   private void b(GuiMessage messageIn) {
      int i = Mth.a((double)this.f() / this.h());
      C_240333_ guimessagetag$icon = messageIn.a();
      if (guimessagetag$icon != null) {
         i -= guimessagetag$icon.f_240358_ + 4 + 2;
      }

      List<C_178_> list = C_3459_.a(messageIn.c(), i, this.i.h);
      boolean flag = this.e();

      for (int j = 0; j < list.size(); j++) {
         C_178_ formattedcharsequence = (C_178_)list.get(j);
         if (flag && this.m > 0) {
            this.n = true;
            this.a(1);
         }

         boolean flag1 = j == list.size() - 1;
         this.l.add(0, new GuiMessage.a(messageIn.b(), formattedcharsequence, messageIn.e(), flag1));
      }

      while (this.l.size() > 100) {
         this.l.remove(this.l.size() - 1);
      }
   }

   private void c(GuiMessage messageIn) {
      this.k.add(0, messageIn);

      while (this.k.size() > 100) {
         this.k.remove(this.k.size() - 1);
      }
   }

   private void m() {
      int i = this.i.l.e();
      this.o.removeIf(p_245406_2_ -> i >= p_245406_2_.b() ? this.b(p_245406_2_.a()) == null : false);
   }

   public void a(C_213508_ p_240953_1_) {
      ChatComponent.a chatcomponent$delayedmessagedeletion = this.b(p_240953_1_);
      if (chatcomponent$delayedmessagedeletion != null) {
         this.o.add(chatcomponent$delayedmessagedeletion);
      }
   }

   @Nullable
   private ChatComponent.a b(C_213508_ signatureIn) {
      int i = this.i.l.e();
      ListIterator<GuiMessage> listiterator = this.k.listIterator();

      while (listiterator.hasNext()) {
         GuiMessage guimessage = (GuiMessage)listiterator.next();
         if (signatureIn.equals(guimessage.d())) {
            if (signatureIn.equals(LevelRenderer.loadVisibleChunksMessageId)) {
               listiterator.remove();
               this.n();
               return null;
            }

            int j = guimessage.b() + 60;
            if (i >= j) {
               listiterator.set(this.d(guimessage));
               this.n();
               return null;
            }

            return new ChatComponent.a(signatureIn, j);
         }
      }

      return null;
   }

   private GuiMessage d(GuiMessage messageIn) {
      return new GuiMessage(messageIn.b(), h, null, C_240334_.m_240701_());
   }

   public void b() {
      this.d();
      this.n();
   }

   private void n() {
      this.l.clear();

      for (GuiMessage guimessage : Lists.reverse(this.k)) {
         this.b(guimessage);
      }
   }

   public C_290101_<String> c() {
      return this.j;
   }

   public void a(String message) {
      if (!message.equals(this.j.peekLast())) {
         if (this.j.size() >= 100) {
            this.j.removeFirst();
         }

         this.j.addLast(message);
      }

      if (message.startsWith("/")) {
         this.i.m_294504_().m_294229_(message);
      }
   }

   public void d() {
      this.m = 0;
      this.n = false;
   }

   public void a(int p_205360_1_) {
      this.m += p_205360_1_;
      int i = this.l.size();
      if (this.m > i - this.j()) {
         this.m = i - this.j();
      }

      if (this.m <= 0) {
         this.m = 0;
         this.n = false;
      }
   }

   public boolean a(double mouseX, double mouseY) {
      if (this.e() && !this.i.m.Y && !this.l()) {
         C_240332_ chatlistener = this.i.m_240442_();
         if (chatlistener.m_242024_() == 0L) {
            return false;
         } else {
            double d0 = mouseX - 2.0;
            double d1 = (double)this.i.aM().q() - mouseY - 40.0;
            if (d0 <= (double)Mth.a((double)this.f() / this.h()) && d1 < 0.0 && d1 > (double)Mth.a(-9.0 * this.h())) {
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
   public C_5020_ b(double mouseX, double mouseY) {
      double d0 = this.c(mouseX);
      double d1 = this.d(mouseY);
      int i = this.e(d0, d1);
      if (i >= 0 && i < this.l.size()) {
         GuiMessage.a guimessage$line = (GuiMessage.a)this.l.get(i);
         return this.i.h.b().m_92338_(guimessage$line.b(), Mth.a(d0));
      } else {
         return null;
      }
   }

   @Nullable
   public C_240334_ c(double p_240463_1_, double p_240463_3_) {
      double d0 = this.c(p_240463_1_);
      double d1 = this.d(p_240463_3_);
      int i = this.d(d0, d1);
      if (i >= 0 && i < this.l.size()) {
         GuiMessage.a guimessage$line = (GuiMessage.a)this.l.get(i);
         C_240334_ guimessagetag = guimessage$line.c();
         if (guimessagetag != null && this.a(d0, guimessage$line, guimessagetag)) {
            return guimessagetag;
         }
      }

      return null;
   }

   private boolean a(double p_240447_1_, GuiMessage.a p_240447_3_, C_240334_ p_240447_4_) {
      if (p_240447_1_ < 0.0) {
         return true;
      } else {
         C_240333_ guimessagetag$icon = p_240447_4_.f_240355_();
         if (guimessagetag$icon == null) {
            return false;
         } else {
            int i = this.a(p_240447_3_);
            int j = i + guimessagetag$icon.f_240358_;
            return p_240447_1_ >= (double)i && p_240447_1_ <= (double)j;
         }
      }
   }

   private double c(double p_240491_1_) {
      return p_240491_1_ / this.h() - 4.0;
   }

   private double d(double p_240485_1_) {
      double d0 = (double)this.i.aM().q() - p_240485_1_ - 40.0;
      return d0 / (this.h() * (double)this.o());
   }

   private int d(double p_246107_1_, double p_246107_3_) {
      int i = this.e(p_246107_1_, p_246107_3_);
      if (i == -1) {
         return -1;
      } else {
         while (i >= 0) {
            if (((GuiMessage.a)this.l.get(i)).d()) {
               return i;
            }

            i--;
         }

         return i;
      }
   }

   private int e(double p_247428_1_, double p_247428_3_) {
      if (this.e() && !this.l()) {
         if (!(p_247428_1_ < -4.0) && !(p_247428_1_ > (double)Mth.a((double)this.f() / this.h()))) {
            int i = Math.min(this.j(), this.l.size());
            if (p_247428_3_ >= 0.0 && p_247428_3_ < (double)i) {
               int j = Mth.a(p_247428_3_ + (double)this.m);
               if (j >= 0 && j < this.l.size()) {
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

   public boolean e() {
      return this.i.f_91080_ instanceof C_3538_;
   }

   public int f() {
      int width = a(this.i.m.x().c());
      Window win = C_3391_.m_91087_().aM();
      int widthWindow = (int)((double)(win.l() - 3) / win.t());
      return Mth.a(width, 0, widthWindow);
   }

   public int g() {
      return b(this.e() ? this.i.m.z().c() : this.i.m.y().c());
   }

   public double h() {
      return this.i.m.w().c();
   }

   public static int a(double valueIn) {
      int i = 320;
      int j = 40;
      return Mth.a(valueIn * 280.0 + 40.0);
   }

   public static int b(double valueIn) {
      int i = 180;
      int j = 20;
      return Mth.a(valueIn * 160.0 + 20.0);
   }

   public static double i() {
      int i = 180;
      int j = 20;
      return 70.0 / (double)(b(1.0) - 20);
   }

   public int j() {
      return this.g() / this.o();
   }

   private int o() {
      return (int)(9.0 * (this.i.m.o().c() + 1.0));
   }

   public ChatComponent.b k() {
      return new ChatComponent.b(List.copyOf(this.k), List.copyOf(this.j), List.copyOf(this.o));
   }

   public void a(ChatComponent.b stateIn) {
      this.j.clear();
      this.j.addAll(stateIn.b);
      this.o.clear();
      this.o.addAll(stateIn.c);
      this.k.clear();
      this.k.addAll(stateIn.a);
      this.n();
   }

   static record a(C_213508_ a, int b) {
   }

   public static class b {
      final List<GuiMessage> a;
      final List<String> b;
      final List<ChatComponent.a> c;

      public b(List<GuiMessage> messagesIn, List<String> historyIn, List<ChatComponent.a> deletionsIn) {
         this.a = messagesIn;
         this.b = historyIn;
         this.c = deletionsIn;
      }
   }
}
