package net.minecraft.world.level.material;

import com.google.common.base.Preconditions;

public class MapColor {
   public static MapColor[] f_283862_ = new MapColor[64];
   public static MapColor f_283808_ = new MapColor(0, 0);
   public static MapColor f_283824_ = new MapColor(1, 8368696);
   public static MapColor f_283761_ = new MapColor(2, 16247203);
   public static MapColor f_283930_ = new MapColor(3, 13092807);
   public static MapColor f_283816_ = new MapColor(4, 16711680);
   public static MapColor f_283828_ = new MapColor(5, 10526975);
   public static MapColor f_283906_ = new MapColor(6, 10987431);
   public static MapColor f_283915_ = new MapColor(7, 31744);
   public static MapColor f_283811_ = new MapColor(8, 16777215);
   public static MapColor f_283744_ = new MapColor(9, 10791096);
   public static MapColor f_283762_ = new MapColor(10, 9923917);
   public static MapColor f_283947_ = new MapColor(11, 7368816);
   public static MapColor f_283864_ = new MapColor(12, 4210943);
   public static MapColor f_283825_ = new MapColor(13, 9402184);
   public static MapColor f_283942_ = new MapColor(14, 16776437);
   public static MapColor f_283750_ = new MapColor(15, 14188339);
   public static MapColor f_283931_ = new MapColor(16, 11685080);
   public static MapColor f_283869_ = new MapColor(17, 6724056);
   public static MapColor f_283832_ = new MapColor(18, 15066419);
   public static MapColor f_283916_ = new MapColor(19, 8375321);
   public static MapColor f_283765_ = new MapColor(20, 15892389);
   public static MapColor f_283818_ = new MapColor(21, 5000268);
   public static MapColor f_283779_ = new MapColor(22, 10066329);
   public static MapColor f_283772_ = new MapColor(23, 5013401);
   public static MapColor f_283889_ = new MapColor(24, 8339378);
   public static MapColor f_283743_ = new MapColor(25, 3361970);
   public static MapColor f_283748_ = new MapColor(26, 6704179);
   public static MapColor f_283784_ = new MapColor(27, 6717235);
   public static MapColor f_283913_ = new MapColor(28, 10040115);
   public static MapColor f_283927_ = new MapColor(29, 1644825);
   public static MapColor f_283757_ = new MapColor(30, 16445005);
   public static MapColor f_283821_ = new MapColor(31, 6085589);
   public static MapColor f_283933_ = new MapColor(32, 4882687);
   public static MapColor f_283812_ = new MapColor(33, 55610);
   public static MapColor f_283819_ = new MapColor(34, 8476209);
   public static MapColor f_283820_ = new MapColor(35, 7340544);
   public static MapColor f_283919_ = new MapColor(36, 13742497);
   public static MapColor f_283895_ = new MapColor(37, 10441252);
   public static MapColor f_283850_ = new MapColor(38, 9787244);
   public static MapColor f_283791_ = new MapColor(39, 7367818);
   public static MapColor f_283843_ = new MapColor(40, 12223780);
   public static MapColor f_283778_ = new MapColor(41, 6780213);
   public static MapColor f_283870_ = new MapColor(42, 10505550);
   public static MapColor f_283861_ = new MapColor(43, 3746083);
   public static MapColor f_283907_ = new MapColor(44, 8874850);
   public static MapColor f_283846_ = new MapColor(45, 5725276);
   public static MapColor f_283892_ = new MapColor(46, 8014168);
   public static MapColor f_283908_ = new MapColor(47, 4996700);
   public static MapColor f_283774_ = new MapColor(48, 4993571);
   public static MapColor f_283856_ = new MapColor(49, 5001770);
   public static MapColor f_283798_ = new MapColor(50, 9321518);
   public static MapColor f_283771_ = new MapColor(51, 2430480);
   public static MapColor f_283909_ = new MapColor(52, 12398641);
   public static MapColor f_283804_ = new MapColor(53, 9715553);
   public static MapColor f_283883_ = new MapColor(54, 6035741);
   public static MapColor f_283745_ = new MapColor(55, 1474182);
   public static MapColor f_283749_ = new MapColor(56, 3837580);
   public static MapColor f_283807_ = new MapColor(57, 5647422);
   public static MapColor f_283898_ = new MapColor(58, 1356933);
   public static MapColor f_283875_ = new MapColor(59, 6579300);
   public static MapColor f_283877_ = new MapColor(60, 14200723);
   public static MapColor f_283769_ = new MapColor(61, 8365974);
   public int f_283871_;
   public int f_283805_;

   private MapColor(int index, int color) {
      if (index >= 0 && index <= 63) {
         this.f_283805_ = index;
         this.f_283871_ = color;
         f_283862_[index] = this;
      } else {
         throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
      }
   }

   public int m_284280_(MapColor.Brightness brightnessIn) {
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

   public static MapColor m_284175_(int idIn) {
      Preconditions.checkPositionIndex(idIn, f_283862_.length, "material id");
      return m_284381_(idIn);
   }

   private static MapColor m_284381_(int idIn) {
      MapColor mapcolor = f_283862_[idIn];
      return mapcolor != null ? mapcolor : f_283808_;
   }

   public static int m_284315_(int packedIdIn) {
      int i = packedIdIn & 0xFF;
      return m_284381_(i >> 2).m_284280_(MapColor.Brightness.m_284389_(i & 3));
   }

   public byte m_284523_(MapColor.Brightness brightnessIn) {
      return (byte)(this.f_283805_ << 2 | brightnessIn.f_283941_ & 3);
   }

   public static enum Brightness {
      LOW(0, 180),
      NORMAL(1, 220),
      HIGH(2, 255),
      LOWEST(3, 135);

      private static MapColor.Brightness[] f_283939_ = new MapColor.Brightness[]{LOW, NORMAL, HIGH, LOWEST};
      public int f_283941_;
      public int f_283785_;

      private Brightness(final int idIn, final int modifierIn) {
         this.f_283941_ = idIn;
         this.f_283785_ = modifierIn;
      }

      public static MapColor.Brightness m_284351_(int idIn) {
         Preconditions.checkPositionIndex(idIn, f_283939_.length, "brightness id");
         return m_284389_(idIn);
      }

      static MapColor.Brightness m_284389_(int idIn) {
         return f_283939_[idIn];
      }
   }
}
