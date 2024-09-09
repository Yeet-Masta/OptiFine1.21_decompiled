package net.optifine.render;

import org.joml.Vector3f;

public class RenderPositions {
   private Vector3f positionDiv16;
   private Vector3f positionRender;

   public RenderPositions(Vector3f position) {
      this.positionDiv16 = (new Vector3f(position)).div(16.0F);
      this.positionRender = new Vector3f(this.positionDiv16);
   }

   public Vector3f getPositionDiv16() {
      return this.positionDiv16;
   }

   public Vector3f getPositionRender() {
      return this.positionRender;
   }

   public String toString() {
      String var10000 = String.valueOf(this.positionDiv16);
      return var10000 + ", " + String.valueOf(this.positionRender);
   }
}
