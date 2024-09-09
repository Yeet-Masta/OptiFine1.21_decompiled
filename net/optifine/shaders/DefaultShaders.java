package net.optifine.shaders;

import com.google.common.base.Charsets;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultShaders {
   public static InputStream getResourceAsStream(String filename) {
      if (filename.endsWith("/final.vsh")) {
         return getFinalVsh();
      } else {
         return filename.endsWith("/final.fsh") ? getFinalFsh() : null;
      }
   }

   private static InputStream getFinalVsh() {
      List<String> lines = new ArrayList();
      lines.add("#version 150");
      lines.add("uniform mat4 modelViewMatrix;");
      lines.add("uniform mat4 projectionMatrix;");
      lines.add("in vec2 vaUV0;");
      lines.add("in vec3 vaPosition;");
      lines.add("out vec2 texcoord;");
      lines.add("void main()");
      lines.add("{");
      lines.add("  gl_Position = (projectionMatrix * modelViewMatrix * vec4(vaPosition, 1.0));");
      lines.add("  texcoord = vec4(vaUV0, 0.0, 1.0).st;");
      lines.add("}");
      String str = (String)lines.stream().collect(Collectors.joining("\n"));
      return getInputStream(str);
   }

   private static InputStream getFinalFsh() {
      List<String> lines = new ArrayList();
      lines.add("#version 150");
      lines.add("uniform sampler2D colortex0;");
      lines.add("in vec2 texcoord;");
      lines.add("/* DRAWBUFFERS:0 */");
      lines.add("out vec4 outColor0;");
      lines.add("void main()");
      lines.add("{");
      lines.add("  outColor0 = texture(colortex0, texcoord);");
      lines.add("}");
      String str = (String)lines.stream().collect(Collectors.joining("\n"));
      return getInputStream(str);
   }

   private static InputStream getInputStream(String str) {
      byte[] bytes = str.getBytes(Charsets.US_ASCII);
      return new ByteArrayInputStream(bytes);
   }
}
