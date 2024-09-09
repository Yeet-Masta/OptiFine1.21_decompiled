package net.optifine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.util.RandomSource;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.RandomUtils;

public class ModelUtils {
   private static final RandomSource RANDOM = RandomUtils.makeThreadSafeRandomSource(0);

   public static void dbgModel(net.minecraft.client.resources.model.BakedModel model) {
      if (model != null) {
         Config.dbg(
            "Model: " + model + ", ao: " + model.m_7541_() + ", gui3d: " + model.m_7539_() + ", builtIn: " + model.m_7521_() + ", particle: " + model.m_6160_()
         );
         net.minecraft.core.Direction[] faces = net.minecraft.core.Direction.f_122346_;

         for (int i = 0; i < faces.length; i++) {
            net.minecraft.core.Direction face = faces[i];
            List faceQuads = model.m_213637_(null, face, RANDOM);
            dbgQuads(face.m_7912_(), faceQuads, "  ");
         }

         List generalQuads = model.m_213637_(null, null, RANDOM);
         dbgQuads("General", generalQuads, "  ");
      }
   }

   private static void dbgQuads(String name, List quads, String prefix) {
      for (net.minecraft.client.renderer.block.model.BakedQuad quad : quads) {
         dbgQuad(name, quad, prefix);
      }
   }

   public static void dbgQuad(String name, net.minecraft.client.renderer.block.model.BakedQuad quad, String prefix) {
      Config.dbg(
         prefix
            + "Quad: "
            + quad.getClass().getName()
            + ", type: "
            + name
            + ", face: "
            + quad.m_111306_()
            + ", tint: "
            + quad.m_111305_()
            + ", sprite: "
            + quad.m_173410_()
      );
      dbgVertexData(quad.m_111303_(), "  " + prefix);
   }

   public static void dbgVertexData(int[] vd, String prefix) {
      int step = vd.length / 4;
      Config.dbg(prefix + "Length: " + vd.length + ", step: " + step);

      for (int i = 0; i < 4; i++) {
         int pos = i * step;
         float x = Float.intBitsToFloat(vd[pos + 0]);
         float y = Float.intBitsToFloat(vd[pos + 1]);
         float z = Float.intBitsToFloat(vd[pos + 2]);
         int col = vd[pos + 3];
         float u = Float.intBitsToFloat(vd[pos + 4]);
         float v = Float.intBitsToFloat(vd[pos + 5]);
         Config.dbg(prefix + i + " xyz: " + x + "," + y + "," + z + " col: " + col + " u,v: " + u + "," + v);
      }
   }

   public static net.minecraft.client.resources.model.BakedModel duplicateModel(net.minecraft.client.resources.model.BakedModel model) {
      List<net.minecraft.client.renderer.block.model.BakedQuad> generalQuads2 = duplicateQuadList(model.m_213637_(null, null, RANDOM));
      net.minecraft.core.Direction[] faces = net.minecraft.core.Direction.f_122346_;
      Map<net.minecraft.core.Direction, List<net.minecraft.client.renderer.block.model.BakedQuad>> faceQuads2 = new HashMap();

      for (int i = 0; i < faces.length; i++) {
         net.minecraft.core.Direction face = faces[i];
         List quads = model.m_213637_(null, face, RANDOM);
         List quads2 = duplicateQuadList(quads);
         faceQuads2.put(face, quads2);
      }

      List<net.minecraft.client.renderer.block.model.BakedQuad> generalQuads2Copy = new ArrayList(generalQuads2);
      Map<net.minecraft.core.Direction, List<net.minecraft.client.renderer.block.model.BakedQuad>> faceQuads2Copy = new HashMap(faceQuads2);
      SimpleBakedModel model2 = new SimpleBakedModel(
         generalQuads2Copy, faceQuads2Copy, model.m_7541_(), model.m_7539_(), true, model.m_6160_(), model.m_7442_(), model.m_7343_()
      );
      Reflector.SimpleBakedModel_generalQuads.setValue(model2, generalQuads2);
      Reflector.SimpleBakedModel_faceQuads.setValue(model2, faceQuads2);
      return model2;
   }

   public static List duplicateQuadList(List<net.minecraft.client.renderer.block.model.BakedQuad> list) {
      List<net.minecraft.client.renderer.block.model.BakedQuad> list2 = new ArrayList();

      for (net.minecraft.client.renderer.block.model.BakedQuad quad : list) {
         net.minecraft.client.renderer.block.model.BakedQuad quad2 = duplicateQuad(quad);
         list2.add(quad2);
      }

      return list2;
   }

   public static net.minecraft.client.renderer.block.model.BakedQuad duplicateQuad(net.minecraft.client.renderer.block.model.BakedQuad quad) {
      return new net.minecraft.client.renderer.block.model.BakedQuad(
         (int[])quad.m_111303_().clone(), quad.m_111305_(), quad.m_111306_(), quad.m_173410_(), quad.m_111307_()
      );
   }
}
