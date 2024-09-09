package net.optifine.player;

public class CapeImageBuffer implements Runnable {
   private net.minecraft.client.player.AbstractClientPlayer player;
   private net.minecraft.resources.ResourceLocation resourceLocation;
   private boolean elytraOfCape;

   public CapeImageBuffer(net.minecraft.client.player.AbstractClientPlayer player, net.minecraft.resources.ResourceLocation resourceLocation) {
      this.player = player;
      this.resourceLocation = resourceLocation;
   }

   public void run() {
   }

   public com.mojang.blaze3d.platform.NativeImage parseUserSkin(com.mojang.blaze3d.platform.NativeImage imageRaw) {
      com.mojang.blaze3d.platform.NativeImage image = CapeUtils.parseCape(imageRaw);
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
