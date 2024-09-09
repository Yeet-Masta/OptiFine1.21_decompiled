package net.optifine.util;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
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

      for (int x = 0; x < 4; x++) {
         for (int y = 0; y < 4; y++) {
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

      for (int i = 0; i < fs.length; i++) {
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

   public static void debugVboMemory(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection[] renderChunks) {
      if (TimedEvent.isActive("DbgVbos", 3000L)) {
         int sum = 0;
         int countChunks = 0;
         int countVbos = 0;
         int countLayers = 0;

         for (int i = 0; i < renderChunks.length; i++) {
            net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection renderChunk = renderChunks[i];
            int sumPre = sum;

            for (net.minecraft.client.renderer.RenderType rt : net.minecraft.client.renderer.chunk.SectionRenderDispatcher.BLOCK_RENDER_LAYERS) {
               com.mojang.blaze3d.vertex.VertexBuffer vb = renderChunk.m_294581_(rt);
               if (vb.getIndexCount() > 0) {
                  sum += vb.getIndexCount() * vb.m_166892_().m_86020_();
                  countVbos++;
               }

               if (renderChunk.m_293175_().isLayerUsed(rt)) {
                  countLayers++;
               }
            }

            if (sum > sumPre) {
               countChunks++;
            }
         }

         Config.dbg("VRAM: " + sum / 1048576 + " MB, vbos: " + countVbos + ", layers: " + countLayers + ", chunks: " + countChunks);
         Config.dbg("VBOs: " + GpuMemory.getBufferAllocated() / 1048576L + " MB");
      }
   }

   public static void debugTextures() {
      Config.dbg(" *** TEXTURES ***");
      net.minecraft.client.renderer.texture.TextureManager textureManager = Minecraft.m_91087_().m_91097_();
      long sum = 0L;
      Collection<net.minecraft.resources.ResourceLocation> locations = textureManager.getTextureLocations();
      List<net.minecraft.resources.ResourceLocation> list = new ArrayList(locations);
      Collections.sort(list);

      for (net.minecraft.resources.ResourceLocation loc : list) {
         net.minecraft.client.renderer.texture.AbstractTexture texture = textureManager.m_118506_(loc);
         long size = GpuMemory.getTextureSize(texture);
         if (Config.isShaders()) {
            size *= 3L;
         }

         Config.dbg(loc + " = " + size);
         sum += size;
      }

      Config.dbg("All: " + sum);
   }
}
