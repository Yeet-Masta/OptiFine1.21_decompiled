import com.google.common.base.Preconditions;

public class MapColor {
   public static final MapColor[] am = new MapColor[64];
   public static final MapColor a = new MapColor(0, 0);
   public static final MapColor b = new MapColor(1, 8368696);
   public static final MapColor c = new MapColor(2, 16247203);
   public static final MapColor d = new MapColor(3, 13092807);
   public static final MapColor e = new MapColor(4, 16711680);
   public static final MapColor f = new MapColor(5, 10526975);
   public static final MapColor g = new MapColor(6, 10987431);
   public static final MapColor h = new MapColor(7, 31744);
   public static final MapColor i = new MapColor(8, 16777215);
   public static final MapColor j = new MapColor(9, 10791096);
   public static final MapColor k = new MapColor(10, 9923917);
   public static final MapColor l = new MapColor(11, 7368816);
   public static final MapColor m = new MapColor(12, 4210943);
   public static final MapColor n = new MapColor(13, 9402184);
   public static final MapColor o = new MapColor(14, 16776437);
   public static final MapColor p = new MapColor(15, 14188339);
   public static final MapColor q = new MapColor(16, 11685080);
   public static final MapColor r = new MapColor(17, 6724056);
   public static final MapColor s = new MapColor(18, 15066419);
   public static final MapColor t = new MapColor(19, 8375321);
   public static final MapColor u = new MapColor(20, 15892389);
   public static final MapColor v = new MapColor(21, 5000268);
   public static final MapColor w = new MapColor(22, 10066329);
   public static final MapColor x = new MapColor(23, 5013401);
   public static final MapColor y = new MapColor(24, 8339378);
   public static final MapColor z = new MapColor(25, 3361970);
   public static final MapColor A = new MapColor(26, 6704179);
   public static final MapColor B = new MapColor(27, 6717235);
   public static final MapColor C = new MapColor(28, 10040115);
   public static final MapColor D = new MapColor(29, 1644825);
   public static final MapColor E = new MapColor(30, 16445005);
   public static final MapColor F = new MapColor(31, 6085589);
   public static final MapColor G = new MapColor(32, 4882687);
   public static final MapColor H = new MapColor(33, 55610);
   public static final MapColor I = new MapColor(34, 8476209);
   public static final MapColor J = new MapColor(35, 7340544);
   public static final MapColor K = new MapColor(36, 13742497);
   public static final MapColor L = new MapColor(37, 10441252);
   public static final MapColor M = new MapColor(38, 9787244);
   public static final MapColor N = new MapColor(39, 7367818);
   public static final MapColor O = new MapColor(40, 12223780);
   public static final MapColor P = new MapColor(41, 6780213);
   public static final MapColor Q = new MapColor(42, 10505550);
   public static final MapColor R = new MapColor(43, 3746083);
   public static final MapColor S = new MapColor(44, 8874850);
   public static final MapColor T = new MapColor(45, 5725276);
   public static final MapColor U = new MapColor(46, 8014168);
   public static final MapColor V = new MapColor(47, 4996700);
   public static final MapColor W = new MapColor(48, 4993571);
   public static final MapColor X = new MapColor(49, 5001770);
   public static final MapColor Y = new MapColor(50, 9321518);
   public static final MapColor Z = new MapColor(51, 2430480);
   public static final MapColor aa = new MapColor(52, 12398641);
   public static final MapColor ab = new MapColor(53, 9715553);
   public static final MapColor ac = new MapColor(54, 6035741);
   public static final MapColor ad = new MapColor(55, 1474182);
   public static final MapColor ae = new MapColor(56, 3837580);
   public static final MapColor af = new MapColor(57, 5647422);
   public static final MapColor ag = new MapColor(58, 1356933);
   public static final MapColor ah = new MapColor(59, 6579300);
   public static final MapColor ai = new MapColor(60, 14200723);
   public static final MapColor aj = new MapColor(61, 8365974);
   public int ak;
   public final int al;

   private MapColor(int index, int color) {
      if (index >= 0 && index <= 63) {
         this.al = index;
         this.ak = color;
         am[index] = this;
      } else {
         throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
      }
   }

   public int a(MapColor.a brightnessIn) {
      if (this == a) {
         return 0;
      } else {
         int i = brightnessIn.f;
         int j = (this.ak >> 16 & 0xFF) * i / 255;
         int k = (this.ak >> 8 & 0xFF) * i / 255;
         int l = (this.ak & 0xFF) * i / 255;
         return 0xFF000000 | l << 16 | k << 8 | j;
      }
   }

   public static MapColor a(int idIn) {
      Preconditions.checkPositionIndex(idIn, am.length, "material id");
      return c(idIn);
   }

   private static MapColor c(int idIn) {
      MapColor mapcolor = am[idIn];
      return mapcolor != null ? mapcolor : a;
   }

   public static int b(int packedIdIn) {
      int i = packedIdIn & 0xFF;
      return c(i >> 2).a(MapColor.a.b(i & 3));
   }

   public byte b(MapColor.a brightnessIn) {
      return (byte)(this.al << 2 | brightnessIn.e & 3);
   }

   public static enum a {
      a(0, 180),
      b(1, 220),
      c(2, 255),
      d(3, 135);

      private static final MapColor.a[] g = new MapColor.a[]{a, b, c, d};
      public final int e;
      public final int f;

      private a(final int idIn, final int modifierIn) {
         this.e = idIn;
         this.f = modifierIn;
      }

      public static MapColor.a a(int idIn) {
         Preconditions.checkPositionIndex(idIn, g.length, "brightness id");
         return b(idIn);
      }

      static MapColor.a b(int idIn) {
         return g[idIn];
      }
   }
}
