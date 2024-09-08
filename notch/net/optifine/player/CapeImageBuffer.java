package net.optifine.player;

import net.minecraft.src.C_3148_;
import net.minecraft.src.C_4102_;
import net.minecraft.src.C_5265_;

public class CapeImageBuffer implements Runnable {
   private C_4102_ player;
   private C_5265_ resourceLocation;
   private boolean elytraOfCape;

   public CapeImageBuffer(C_4102_ player, C_5265_ resourceLocation) {
      this.player = player;
      this.resourceLocation = resourceLocation;
   }

   public void run() {
   }

   public C_3148_ parseUserSkin(C_3148_ imageRaw) {
      C_3148_ image = CapeUtils.parseCape(imageRaw);
      this.elytraOfCape = CapeUtils.isElytraCape(imageRaw, image);
      return image;
   }

   public void skinAvailable() {
      if (this.player != null) {
         this.player.setLocationOfCape(this.resourceLocation);
         this.player.setElytraOfCape(this.elytraOfCape);
      }

      this.cleanup();
   }

   public void cleanup() {
      this.player = null;
   }

   public boolean isElytraOfCape() {
      return this.elytraOfCape;
   }
}
