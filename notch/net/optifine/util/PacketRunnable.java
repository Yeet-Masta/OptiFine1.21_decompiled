package net.optifine.util;

import net.minecraft.src.C_5028_;

public class PacketRunnable implements Runnable {
   private C_5028_ packet;
   private Runnable runnable;

   public PacketRunnable(C_5028_ packet, Runnable runnable) {
      this.packet = packet;
      this.runnable = runnable;
   }

   public void run() {
      this.runnable.run();
   }

   public C_5028_ getPacket() {
      return this.packet;
   }

   public String toString() {
      return "PacketRunnable: " + this.packet;
   }
}
