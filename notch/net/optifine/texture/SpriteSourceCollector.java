package net.optifine.texture;

import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_260369_.C_260371_;
import net.minecraft.src.C_260369_.C_260402_;

public class SpriteSourceCollector implements C_260371_ {
   private Set<C_5265_> spriteNames;

   public SpriteSourceCollector(Set<C_5265_> spriteNames) {
      this.spriteNames = spriteNames;
   }

   public void m_260840_(C_5265_ locIn, C_260402_ supplierIn) {
      this.spriteNames.add(locIn);
   }

   public void m_261187_(Predicate<C_5265_> checkIn) {
   }

   public Set<C_5265_> getSpriteNames() {
      return this.spriteNames;
   }
}
