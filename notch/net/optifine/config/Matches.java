package net.optifine.config;

import net.minecraft.src.C_1629_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_4486_;

public class Matches {
   public static boolean block(C_2064_ blockStateBase, MatchBlock[] matchBlocks) {
      if (matchBlocks == null) {
         return true;
      } else {
         for (int i = 0; i < matchBlocks.length; i++) {
            MatchBlock mb = matchBlocks[i];
            if (mb.matches(blockStateBase)) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean block(int blockId, int metadata, MatchBlock[] matchBlocks) {
      if (matchBlocks == null) {
         return true;
      } else {
         for (int i = 0; i < matchBlocks.length; i++) {
            MatchBlock mb = matchBlocks[i];
            if (mb.matches(blockId, metadata)) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean blockId(int blockId, MatchBlock[] matchBlocks) {
      if (matchBlocks == null) {
         return true;
      } else {
         for (int i = 0; i < matchBlocks.length; i++) {
            MatchBlock mb = matchBlocks[i];
            if (mb.getBlockId() == blockId) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean metadata(int metadata, int[] metadatas) {
      if (metadatas == null) {
         return true;
      } else {
         for (int i = 0; i < metadatas.length; i++) {
            if (metadatas[i] == metadata) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean sprite(C_4486_ sprite, C_4486_[] sprites) {
      if (sprites == null) {
         return true;
      } else {
         for (int i = 0; i < sprites.length; i++) {
            if (sprites[i] == sprite) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean biome(C_1629_ biome, BiomeId[] biomes) {
      if (biomes == null) {
         return true;
      } else {
         for (int i = 0; i < biomes.length; i++) {
            BiomeId bi = biomes[i];
            if (bi != null && bi.getBiome() == biome) {
               return true;
            }
         }

         return false;
      }
   }
}
