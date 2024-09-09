package net.optifine.util;

import com.mojang.blaze3d.vertex.VertexBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.optifine.Config;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class DebugUtils {
   private static FloatBuffer floatBuffer16 = BufferUtils.createFloatBuffer(16);
   private static float[] floatArray16 = new float[16];

   public static String getGlModelView() {
      floatBuffer16.clear();
      GL11.glGetFloatv(2982, floatBuffer16);
      floatBuffer16.get(floatArray16);
      float[] floatArray16T = transposeMat4(floatArray16);
      return getMatrix4(floatArray16T);
   }

   public static String getGlProjection() {
      floatBuffer16.clear();
      GL11.glGetFloatv(2983, floatBuffer16);
      floatBuffer16.get(floatArray16);
      float[] floatArray16T = transposeMat4(floatArray16);
      return getMatrix4(floatArray16T);
   }

   private static float[] transposeMat4(float[] arr) {
      float[] arrT = new float[16];

      for(int x = 0; x < 4; ++x) {
         for(int y = 0; y < 4; ++y) {
            arrT[x * 4 + y] = arr[y * 4 + x];
         }
      }

      return arrT;
   }

   public static String getMatrix4(Matrix4f mat) {
      MathUtils.write(mat, floatArray16);
      return getMatrix4(floatArray16);
   }

   private static String getMatrix4(float[] fs) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < fs.length; ++i) {
         String str = String.format("%.2f", fs[i]);
         if (i > 0) {
            if (i % 4 == 0) {
               sb.append("\n");
            } else {
               sb.append(", ");
            }
         }

         str = StrUtils.fillLeft(str, 5, ' ');
         sb.append(str);
      }

      return sb.toString();
   }

   public static void debugVboMemory(SectionRenderDispatcher.RenderSection[] renderChunks) {
      if (TimedEvent.isActive("DbgVbos", 3000L)) {
         int sum = 0;
         int countChunks = 0;
         int countVbos = 0;
         int countLayers = 0;

         for(int i = 0; i < renderChunks.length; ++i) {
            SectionRenderDispatcher.RenderSection renderChunk = renderChunks[i];
            int sumPre = sum;
            RenderType[] var8 = SectionRenderDispatcher.BLOCK_RENDER_LAYERS;
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               RenderType rt = var8[var10];
               VertexBuffer vb = renderChunk.m_294581_(rt);
               if (vb.getIndexCount() > 0) {
                  sum += vb.getIndexCount() * vb.m_166892_().m_86020_();
                  ++countVbos;
               }

               if (renderChunk.m_293175_().isLayerUsed(rt)) {
                  ++countLayers;
               }
            }

            if (sum > sumPre) {
               ++countChunks;
            }
         }

         Config.dbg("VRAM: " + sum / 1048576 + " MB, vbos: " + countVbos + ", layers: " + countLayers + ", chunks: " + countChunks);
         long var10000 = GpuMemory.getBufferAllocated();
         Config.dbg("VBOs: " + var10000 / 1048576L + " MB");
      }
   }

   public static void debugTextures() {
      Config.dbg(" *** TEXTURES ***");
      TextureManager textureManager = Minecraft.m_91087_().m_91097_();
      long sum = 0L;
      Collection locations = textureManager.getTextureLocations();
      List list = new ArrayList(locations);
      Collections.sort(list);

      long size;
      for(Iterator var5 = list.iterator(); var5.hasNext(); sum += size) {
         ResourceLocation loc = (ResourceLocation)var5.next();
         AbstractTexture texture = textureManager.m_118506_(loc);
         size = GpuMemory.getTextureSize(texture);
         if (Config.isShaders()) {
            size *= 3L;
         }

         String var10000 = String.valueOf(loc);
         Config.dbg(var10000 + " = " + size);
      }

      Config.dbg("All: " + sum);
   }
}
