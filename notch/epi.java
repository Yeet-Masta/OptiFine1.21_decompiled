package net.minecraft.src;

import com.google.common.base.Preconditions;

public class C_283734_ {
   public static final C_283734_[] f_283862_ = new C_283734_[64];
   public static final C_283734_ f_283808_ = new C_283734_(0, 0);
   public static final C_283734_ f_283824_ = new C_283734_(1, 8368696);
   public static final C_283734_ f_283761_ = new C_283734_(2, 16247203);
   public static final C_283734_ f_283930_ = new C_283734_(3, 13092807);
   public static final C_283734_ f_283816_ = new C_283734_(4, 16711680);
   public static final C_283734_ f_283828_ = new C_283734_(5, 10526975);
   public static final C_283734_ f_283906_ = new C_283734_(6, 10987431);
   public static final C_283734_ f_283915_ = new C_283734_(7, 31744);
   public static final C_283734_ f_283811_ = new C_283734_(8, 16777215);
   public static final C_283734_ f_283744_ = new C_283734_(9, 10791096);
   public static final C_283734_ f_283762_ = new C_283734_(10, 9923917);
   public static final C_283734_ f_283947_ = new C_283734_(11, 7368816);
   public static final C_283734_ f_283864_ = new C_283734_(12, 4210943);
   public static final C_283734_ f_283825_ = new C_283734_(13, 9402184);
   public static final C_283734_ f_283942_ = new C_283734_(14, 16776437);
   public static final C_283734_ f_283750_ = new C_283734_(15, 14188339);
   public static final C_283734_ f_283931_ = new C_283734_(16, 11685080);
   public static final C_283734_ f_283869_ = new C_283734_(17, 6724056);
   public static final C_283734_ f_283832_ = new C_283734_(18, 15066419);
   public static final C_283734_ f_283916_ = new C_283734_(19, 8375321);
   public static final C_283734_ f_283765_ = new C_283734_(20, 15892389);
   public static final C_283734_ f_283818_ = new C_283734_(21, 5000268);
   public static final C_283734_ f_283779_ = new C_283734_(22, 10066329);
   public static final C_283734_ f_283772_ = new C_283734_(23, 5013401);
   public static final C_283734_ f_283889_ = new C_283734_(24, 8339378);
   public static final C_283734_ f_283743_ = new C_283734_(25, 3361970);
   public static final C_283734_ f_283748_ = new C_283734_(26, 6704179);
   public static final C_283734_ f_283784_ = new C_283734_(27, 6717235);
   public static final C_283734_ f_283913_ = new C_283734_(28, 10040115);
   public static final C_283734_ f_283927_ = new C_283734_(29, 1644825);
   public static final C_283734_ f_283757_ = new C_283734_(30, 16445005);
   public static final C_283734_ f_283821_ = new C_283734_(31, 6085589);
   public static final C_283734_ f_283933_ = new C_283734_(32, 4882687);
   public static final C_283734_ f_283812_ = new C_283734_(33, 55610);
   public static final C_283734_ f_283819_ = new C_283734_(34, 8476209);
   public static final C_283734_ f_283820_ = new C_283734_(35, 7340544);
   public static final C_283734_ f_283919_ = new C_283734_(36, 13742497);
   public static final C_283734_ f_283895_ = new C_283734_(37, 10441252);
   public static final C_283734_ f_283850_ = new C_283734_(38, 9787244);
   public static final C_283734_ f_283791_ = new C_283734_(39, 7367818);
   public static final C_283734_ f_283843_ = new C_283734_(40, 12223780);
   public static final C_283734_ f_283778_ = new C_283734_(41, 6780213);
   public static final C_283734_ f_283870_ = new C_283734_(42, 10505550);
   public static final C_283734_ f_283861_ = new C_283734_(43, 3746083);
   public static final C_283734_ f_283907_ = new C_283734_(44, 8874850);
   public static final C_283734_ f_283846_ = new C_283734_(45, 5725276);
   public static final C_283734_ f_283892_ = new C_283734_(46, 8014168);
   public static final C_283734_ f_283908_ = new C_283734_(47, 4996700);
   public static final C_283734_ f_283774_ = new C_283734_(48, 4993571);
   public static final C_283734_ f_283856_ = new C_283734_(49, 5001770);
   public static final C_283734_ f_283798_ = new C_283734_(50, 9321518);
   public static final C_283734_ f_283771_ = new C_283734_(51, 2430480);
   public static final C_283734_ f_283909_ = new C_283734_(52, 12398641);
   public static final C_283734_ f_283804_ = new C_283734_(53, 9715553);
   public static final C_283734_ f_283883_ = new C_283734_(54, 6035741);
   public static final C_283734_ f_283745_ = new C_283734_(55, 1474182);
   public static final C_283734_ f_283749_ = new C_283734_(56, 3837580);
   public static final C_283734_ f_283807_ = new C_283734_(57, 5647422);
   public static final C_283734_ f_283898_ = new C_283734_(58, 1356933);
   public static final C_283734_ f_283875_ = new C_283734_(59, 6579300);
   public static final C_283734_ f_283877_ = new C_283734_(60, 14200723);
   public static final C_283734_ f_283769_ = new C_283734_(61, 8365974);
   public int f_283871_;
   public final int f_283805_;

   private C_283734_(int index, int color) {
      if (index >= 0 && index <= 63) {
         this.f_283805_ = index;
         this.f_283871_ = color;
         f_283862_[index] = this;
      } else {
         throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
      }
   }

   public int m_284280_(C_283734_.C_283729_ brightnessIn) {
      if (this == f_283808_) {
         return 0;
      } else {
         int i = brightnessIn.f_283785_;
         int j = (this.f_283871_ >> 16 & 0xFF) * i / 255;
         int k = (this.f_283871_ >> 8 & 0xFF) * i / 255;
         int l = (this.f_283871_ & 0xFF) * i / 255;
         return 0xFF000000 | l << 16 | k << 8 | j;
      }
   }

   public static C_283734_ m_284175_(int idIn) {
      Preconditions.checkPositionIndex(idIn, f_283862_.length, "material id");
      return m_284381_(idIn);
   }

   private static C_283734_ m_284381_(int idIn) {
      C_283734_ mapcolor = f_283862_[idIn];
      return mapcolor != null ? mapcolor : f_283808_;
   }

   public static int m_284315_(int packedIdIn) {
      int i = packedIdIn & 0xFF;
      return m_284381_(i >> 2).m_284280_(C_283734_.C_283729_.m_284389_(i & 3));
   }

   public byte m_284523_(C_283734_.C_283729_ brightnessIn) {
      return (byte)(this.f_283805_ << 2 | brightnessIn.f_283941_ & 3);
   }

   public static enum C_283729_ {
      LOW(0, 180),
      NORMAL(1, 220),
      HIGH(2, 255),
      LOWEST(3, 135);

      private static final C_283734_.C_283729_[] f_283939_ = new C_283734_.C_283729_[]{LOW, NORMAL, HIGH, LOWEST};
      public final int f_283941_;
      public final int f_283785_;

      private C_283729_(final int idIn, final int modifierIn) {
         this.f_283941_ = idIn;
         this.f_283785_ = modifierIn;
      }

      public static C_283734_.C_283729_ m_284351_(int idIn) {
         Preconditions.checkPositionIndex(idIn, f_283939_.length, "brightness id");
         return m_284389_(idIn);
      }

      static C_283734_.C_283729_ m_284389_(int idIn) {
         return f_283939_[idIn];
      }
   }
}
