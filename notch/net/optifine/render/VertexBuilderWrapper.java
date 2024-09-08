package net.optifine.render;

import net.minecraft.src.C_3187_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4486_;
import net.minecraft.src.C_4139_.C_4140_;
import org.joml.Vector3f;

public abstract class VertexBuilderWrapper implements C_3187_ {
   private C_3187_ vertexBuilder;

   public VertexBuilderWrapper(C_3187_ vertexBuilder) {
      this.vertexBuilder = vertexBuilder;
   }

   public C_3187_ getVertexBuilder() {
      return this.vertexBuilder;
   }

   public void putSprite(C_4486_ sprite) {
      this.vertexBuilder.putSprite(sprite);
   }

   public void setSprite(C_4486_ sprite) {
      this.vertexBuilder.setSprite(sprite);
   }

   public boolean isMultiTexture() {
      return this.vertexBuilder.isMultiTexture();
   }

   public C_4168_ getRenderType() {
      return this.vertexBuilder.getRenderType();
   }

   public Vector3f getTempVec3f() {
      return this.vertexBuilder.getTempVec3f();
   }

   public float[] getTempFloat4(float f1, float f2, float f3, float f4) {
      return this.vertexBuilder.getTempFloat4(f1, f2, f3, f4);
   }

   public int[] getTempInt4(int i1, int i2, int i3, int i4) {
      return this.vertexBuilder.getTempInt4(i1, i2, i3, i4);
   }

   public C_4140_ getRenderTypeBuffer() {
      return this.vertexBuilder.getRenderTypeBuffer();
   }

   public void setQuadVertexPositions(VertexPosition[] vps) {
      this.vertexBuilder.setQuadVertexPositions(vps);
   }

   public void setMidBlock(float mbx, float mby, float mbz) {
      this.vertexBuilder.setMidBlock(mbx, mby, mbz);
   }

   public int getVertexCount() {
      return this.vertexBuilder.getVertexCount();
   }
}
