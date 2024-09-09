package net.minecraftforge.client.model;

public record ForgeFaceData(int color, int blockLight, int skyLight, boolean ambientOcclusion) {
   public static final ForgeFaceData DEFAULT = new ForgeFaceData(-1, 0, 0, true);

   public ForgeFaceData(int color, int blockLight, int skyLight, boolean ambientOcclusion) {
      this.color = color;
      this.blockLight = blockLight;
      this.skyLight = skyLight;
      this.ambientOcclusion = ambientOcclusion;
   }

   public int color() {
      return this.color;
   }

   public int blockLight() {
      return this.blockLight;
   }

   public int skyLight() {
      return this.skyLight;
   }

   public boolean ambientOcclusion() {
      return this.ambientOcclusion;
   }
}
