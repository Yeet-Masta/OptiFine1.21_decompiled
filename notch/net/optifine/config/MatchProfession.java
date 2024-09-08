package net.optifine.config;

import net.minecraft.src.C_1121_;
import net.optifine.Config;

public class MatchProfession {
   private C_1121_ profession;
   private int[] levels;

   public MatchProfession(C_1121_ profession) {
      this(profession, null);
   }

   public MatchProfession(C_1121_ profession, int level) {
      this(profession, new int[]{level});
   }

   public MatchProfession(C_1121_ profession, int[] levels) {
      this.profession = profession;
      this.levels = levels;
   }

   public boolean matches(C_1121_ prof, int lev) {
      return this.profession != prof ? false : this.levels == null || Config.equalsOne(lev, this.levels);
   }

   private boolean hasLevel(int lev) {
      return this.levels == null ? false : Config.equalsOne(lev, this.levels);
   }

   public boolean addLevel(int lev) {
      if (this.levels == null) {
         this.levels = new int[]{lev};
         return true;
      } else if (this.hasLevel(lev)) {
         return false;
      } else {
         this.levels = Config.addIntToArray(this.levels, lev);
         return true;
      }
   }

   public C_1121_ getProfession() {
      return this.profession;
   }

   public int[] getLevels() {
      return this.levels;
   }

   public static boolean matchesOne(C_1121_ prof, int level, MatchProfession[] mps) {
      if (mps == null) {
         return false;
      } else {
         for (int i = 0; i < mps.length; i++) {
            MatchProfession mp = mps[i];
            if (mp.matches(prof, level)) {
               return true;
            }
         }

         return false;
      }
   }

   public String toString() {
      return this.levels == null ? this.profession + "" : this.profession + ":" + Config.arrayToString(this.levels);
   }
}
