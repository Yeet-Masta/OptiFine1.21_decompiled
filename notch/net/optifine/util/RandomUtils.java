package net.optifine.util;

import java.util.Random;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_213140_;

public class RandomUtils {
   private static final Random random = new Random();

   public static Random getRandom() {
      return random;
   }

   public static byte[] getRandomBytes(int length) {
      byte[] bytes = new byte[length];
      random.nextBytes(bytes);
      return bytes;
   }

   public static int getRandomInt(int bound) {
      return random.nextInt(bound);
   }

   public static C_212974_ makeThreadSafeRandomSource(int seed) {
      return new C_213140_((long)seed);
   }
}
