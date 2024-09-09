import java.util.List;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_201_;
import net.minecraft.src.C_507_;

public class TickRateManager {
   public static final float a = 1.0F;
   protected float b = 20.0F;
   protected long c = C_201_.f_145016_ / 20L;
   protected int d = 0;
   protected boolean e = true;
   protected boolean f = false;

   public void a(float rateIn) {
      this.b = Math.max(rateIn, 1.0F);
      this.c = (long)((double)C_201_.f_145016_ / (double)this.b);
   }

   public float f() {
      return this.b;
   }

   public float g() {
      return (float)this.c / (float)C_201_.f_145017_;
   }

   public long h() {
      return this.c;
   }

   public boolean i() {
      return this.e;
   }

   public boolean j() {
      return this.d > 0;
   }

   public void c(int ticksIn) {
      this.d = ticksIn;
   }

   public int k() {
      return this.d;
   }

   public void a(boolean frozenIn) {
      this.f = frozenIn;
   }

   public boolean l() {
      return this.f;
   }

   public void m() {
      this.e = !this.f || this.d > 0;
      if (this.d > 0) {
         this.d--;
      }
   }

   public boolean a(C_507_ entityIn) {
      return !this.i() && !(entityIn instanceof C_1141_) && !this.hasPlayerPassengers(entityIn);
   }

   private boolean hasPlayerPassengers(C_507_ entity) {
      List<C_507_> passengers = entity.m_20197_();

      for (int i = 0; i < passengers.size(); i++) {
         C_507_ passenger = (C_507_)passengers.get(i);
         if (passenger instanceof C_1141_) {
            return true;
         }

         if (this.hasPlayerPassengers(passenger)) {
            return true;
         }
      }

      return false;
   }
}
