package net.optifine.util;

public enum BiomeCategory {
   NONE("none"),
   TAIGA("taiga"),
   EXTREME_HILLS("extreme_hills"),
   JUNGLE("jungle"),
   MESA("mesa"),
   PLAINS("plains"),
   SAVANNA("savanna"),
   ICY("icy"),
   THEEND("the_end"),
   BEACH("beach"),
   FOREST("forest"),
   OCEAN("ocean"),
   DESERT("desert"),
   RIVER("river"),
   SWAMP("swamp"),
   MUSHROOM("mushroom"),
   NETHER("nether"),
   UNDERGROUND("underground"),
   MOUNTAIN("mountain");

   private String name;

   private BiomeCategory(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   // $FF: synthetic method
   private static BiomeCategory[] $values() {
      return new BiomeCategory[]{NONE, TAIGA, EXTREME_HILLS, JUNGLE, MESA, PLAINS, SAVANNA, ICY, THEEND, BEACH, FOREST, OCEAN, DESERT, RIVER, SWAMP, MUSHROOM, NETHER, UNDERGROUND, MOUNTAIN};
   }
}
