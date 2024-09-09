package net.optifine.util;

import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;

public class BufferUtil {
   public static String getBufferHex(BufferBuilder bb) {
      VertexFormat.Mode drawMode = bb.getDrawMode();
      String primitiveName = "";
      int vertexPerPrimitive = true;
      byte vertexPerPrimitive;
      if (drawMode == VertexFormat.Mode.QUADS) {
         primitiveName = "quad";
         vertexPerPrimitive = 4;
      } else {
         if (drawMode != VertexFormat.Mode.TRIANGLES) {
            return "Invalid draw mode: " + String.valueOf(drawMode);
         }

         primitiveName = "triangle";
         vertexPerPrimitive = 3;
      }

      StringBuffer sb = new StringBuffer();
      int vertexCount = bb.getVertexCount();

      for(int v = 0; v < vertexCount; ++v) {
         if (v % vertexPerPrimitive == 0) {
            sb.append(primitiveName + " " + v / vertexPerPrimitive + "\n");
         }

         String vs = getVertexHex(v, bb);
         sb.append(vs);
         sb.append("\n");
      }

      return sb.toString();
   }

   private static String getVertexHex(int vertex, BufferBuilder bb) {
      StringBuffer sb = new StringBuffer();
      ByteBuffer buf = bb.getByteBuffer();
      VertexFormat vf = bb.getVertexFormat();
      int pos = bb.getStartPosition() + vertex * vf.m_86020_();
      Iterator var6 = vf.m_86023_().iterator();

      while(var6.hasNext()) {
         VertexFormatElement vfe = (VertexFormatElement)var6.next();
         if (vfe.getElementCount() > 0) {
            sb.append("(");
         }

         for(int i = 0; i < vfe.getElementCount(); ++i) {
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
         int var10001 = buf.position();
         sb.append("(pos=" + var10001 + " lim=" + buf.limit() + " cap=" + buf.capacity() + ")");
         sb.append("[");
         int len = Math.min(buf.limit(), 1024);

         for(int i = 0; i < len; ++i) {
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

      for(int i = 0; i < arr.length; ++i) {
         arr[i] = buf.get(i);
      }

      return arr;
   }

   public static FloatBuffer createDirectFloatBuffer(int capacity) {
      return GlUtil.m_166247_(capacity << 2).asFloatBuffer();
   }

   public static void fill(FloatBuffer buf, float val) {
      buf.clear();

      for(int i = 0; i < buf.capacity(); ++i) {
         buf.put(i, val);
      }

      buf.clear();
   }
}
