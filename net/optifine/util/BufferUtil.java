package net.optifine.util;

import com.mojang.blaze3d.platform.GlUtil;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {
   public static String getBufferHex(com.mojang.blaze3d.vertex.BufferBuilder bb) {
      com.mojang.blaze3d.vertex.VertexFormat.Mode drawMode = bb.getDrawMode();
      String primitiveName = "";
      int vertexPerPrimitive = -1;
      byte var9;
      if (drawMode == com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS) {
         primitiveName = "quad";
         var9 = 4;
      } else {
         if (drawMode != com.mojang.blaze3d.vertex.VertexFormat.Mode.TRIANGLES) {
            return "Invalid draw mode: " + drawMode;
         }

         primitiveName = "triangle";
         var9 = 3;
      }

      StringBuffer sb = new StringBuffer();
      int vertexCount = bb.getVertexCount();

      for (int v = 0; v < vertexCount; v++) {
         if (v % var9 == 0) {
            sb.append(primitiveName + " " + v / var9 + "\n");
         }

         String vs = getVertexHex(v, bb);
         sb.append(vs);
         sb.append("\n");
      }

      return sb.toString();
   }

   private static String getVertexHex(int vertex, com.mojang.blaze3d.vertex.BufferBuilder bb) {
      StringBuffer sb = new StringBuffer();
      ByteBuffer buf = bb.getByteBuffer();
      com.mojang.blaze3d.vertex.VertexFormat vf = bb.getVertexFormat();
      int pos = bb.getStartPosition() + vertex * vf.m_86020_();

      for (com.mojang.blaze3d.vertex.VertexFormatElement vfe : vf.m_86023_()) {
         if (vfe.getElementCount() > 0) {
            sb.append("(");
         }

         for (int i = 0; i < vfe.getElementCount(); i++) {
            if (i > 0) {
               sb.append(" ");
            }

            switch (vfe.f_86030_()) {
               case FLOAT:
                  sb.append(buf.getFloat(pos));
                  break;
               case UBYTE:
               case BYTE:
                  sb.append(buf.get(pos));
                  break;
               case USHORT:
               case SHORT:
                  sb.append(buf.getShort(pos));
                  break;
               case UINT:
               case INT:
                  sb.append(buf.getShort(pos));
                  break;
               default:
                  sb.append("??");
            }

            pos += vfe.f_86030_().m_86074_();
         }

         if (vfe.getElementCount() > 0) {
            sb.append(")");
         }
      }

      return sb.toString();
   }

   public static String getBufferString(IntBuffer buf) {
      if (buf == null) {
         return "null";
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append("(pos=" + buf.position() + " lim=" + buf.limit() + " cap=" + buf.capacity() + ")");
         sb.append("[");
         int len = Math.min(buf.limit(), 1024);

         for (int i = 0; i < len; i++) {
            if (i > 0) {
               sb.append(", ");
            }

            sb.append(buf.get(i));
         }

         sb.append("]");
         return sb.toString();
      }
   }

   public static int[] toArray(IntBuffer buf) {
      int[] arr = new int[buf.limit()];

      for (int i = 0; i < arr.length; i++) {
         arr[i] = buf.get(i);
      }

      return arr;
   }

   public static FloatBuffer createDirectFloatBuffer(int capacity) {
      return GlUtil.m_166247_(capacity << 2).asFloatBuffer();
   }

   public static void fill(FloatBuffer buf, float val) {
      buf.clear();

      for (int i = 0; i < buf.capacity(); i++) {
         buf.put(i, val);
      }

      buf.clear();
   }
}
