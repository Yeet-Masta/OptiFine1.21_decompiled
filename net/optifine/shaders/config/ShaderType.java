package net.optifine.shaders.config;

public enum ShaderType {
   VERTEX,
   FRAGMENT,
   GEOMETRY,
   COMPUTE;

   // $FF: synthetic method
   private static ShaderType[] $values() {
      return new ShaderType[]{VERTEX, FRAGMENT, GEOMETRY, COMPUTE};
   }
}
