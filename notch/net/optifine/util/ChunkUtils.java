package net.optifine.util;

import net.minecraft.src.C_2137_;
import net.optifine.ChunkOF;

public class ChunkUtils {
   public static boolean hasEntities(C_2137_ chunk) {
      return chunk instanceof ChunkOF chunkOF ? chunkOF.hasEntities() : true;
   }

   public static boolean isLoaded(C_2137_ chunk) {
      return chunk instanceof ChunkOF chunkOF ? chunkOF.isLoaded() : false;
   }
}
