package net.optifine.render;

import net.minecraft.src.C_3187_;
import net.minecraft.src.C_4139_.C_4140_;

public class VertexBuilderDummy implements C_3187_ {
   private C_4140_ renderTypeBuffer = null;

   public VertexBuilderDummy(C_4140_ renderTypeBuffer) {
      this.renderTypeBuffer = renderTypeBuffer;
   }

   public C_4140_ getRenderTypeBuffer() {
      return this.renderTypeBuffer;
   }

   public C_3187_ m_167146_(float x, float y, float z) {
      return this;
   }

   public C_3187_ m_167129_(int red, int green, int blue, int alpha) {
      return this;
   }

   public C_3187_ m_167083_(float u, float v) {
      return this;
   }

   public C_3187_ m_338369_(int u, int v) {
      return this;
   }

   public C_3187_ m_338813_(int u, int v) {
      return this;
   }

   public C_3187_ m_338525_(float x, float y, float z) {
      return this;
   }
}
