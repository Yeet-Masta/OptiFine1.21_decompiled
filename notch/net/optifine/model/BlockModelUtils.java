package net.optifine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_3040_;
import net.minecraft.src.C_4196_;
import net.minecraft.src.C_4200_;
import net.minecraft.src.C_4202_;
import net.minecraft.src.C_4203_;
import net.minecraft.src.C_4211_;
import net.minecraft.src.C_4219_;
import net.minecraft.src.C_4222_;
import net.minecraft.src.C_4484_;
import net.minecraft.src.C_4486_;
import net.minecraft.src.C_4528_;
import net.minecraft.src.C_4529_;
import net.minecraft.src.C_4535_;
import net.minecraft.src.C_4536_;
import net.minecraft.src.C_4540_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_2056_.C_2060_;
import net.optifine.Config;
import net.optifine.util.RandomUtils;
import org.joml.Vector3f;

public class BlockModelUtils {
   private static final float VERTEX_COORD_ACCURACY = 1.0E-6F;
   private static final C_212974_ RANDOM = RandomUtils.makeThreadSafeRandomSource(0);

   public static C_4528_ makeModelCube(String spriteName, int tintIndex) {
      C_4486_ sprite = Config.getTextureMap().getUploadedSprite(spriteName);
      return makeModelCube(sprite, tintIndex);
   }

   public static C_4528_ makeModelCube(C_4486_ sprite, int tintIndex) {
      List generalQuads = new ArrayList();
      C_4687_[] facings = C_4687_.f_122346_;
      Map<C_4687_, List<C_4196_>> faceQuads = new HashMap();

      for (int i = 0; i < facings.length; i++) {
         C_4687_ facing = facings[i];
         List quads = new ArrayList();
         quads.add(makeBakedQuad(facing, sprite, tintIndex));
         faceQuads.put(facing, quads);
      }

      C_4219_ itemOverrideList = C_4219_.f_111734_;
      C_4528_ bakedModel = new C_4540_(generalQuads, faceQuads, true, true, true, sprite, C_4222_.f_111786_, itemOverrideList);
      return bakedModel;
   }

   public static C_4528_ joinModelsCube(C_4528_ modelBase, C_4528_ modelAdd) {
      List<C_4196_> generalQuads = new ArrayList();
      generalQuads.addAll(modelBase.m_213637_(null, null, RANDOM));
      generalQuads.addAll(modelAdd.m_213637_(null, null, RANDOM));
      C_4687_[] facings = C_4687_.f_122346_;
      Map<C_4687_, List<C_4196_>> faceQuads = new HashMap();

      for (int i = 0; i < facings.length; i++) {
         C_4687_ facing = facings[i];
         List quads = new ArrayList();
         quads.addAll(modelBase.m_213637_(null, facing, RANDOM));
         quads.addAll(modelAdd.m_213637_(null, facing, RANDOM));
         faceQuads.put(facing, quads);
      }

      boolean ao = modelBase.m_7541_();
      boolean builtIn = modelBase.m_7521_();
      C_4486_ sprite = modelBase.m_6160_();
      C_4222_ transforms = modelBase.m_7442_();
      C_4219_ itemOverrideList = modelBase.m_7343_();
      C_4528_ bakedModel = new C_4540_(generalQuads, faceQuads, ao, builtIn, true, sprite, transforms, itemOverrideList);
      return bakedModel;
   }

   public static C_4196_ makeBakedQuad(C_4687_ facing, C_4486_ sprite, int tintIndex) {
      Vector3f posFrom = new Vector3f(0.0F, 0.0F, 0.0F);
      Vector3f posTo = new Vector3f(16.0F, 16.0F, 16.0F);
      C_4203_ uv = new C_4203_(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0);
      C_4200_ face = new C_4200_(facing, tintIndex, "#" + facing.m_7912_(), uv);
      C_4529_ modelRotation = C_4529_.X0_Y0;
      C_4202_ partRotation = null;
      boolean shade = true;
      C_5265_ modelLoc = sprite.getName();
      C_4211_ faceBakery = new C_4211_();
      return faceBakery.m_111600_(posFrom, posTo, face, sprite, facing, modelRotation, partRotation, shade);
   }

   public static C_4528_ makeModel(String modelName, String spriteOldName, String spriteNewName) {
      C_4484_ textureMap = Config.getTextureMap();
      C_4486_ spriteOld = textureMap.getUploadedSprite(spriteOldName);
      C_4486_ spriteNew = textureMap.getUploadedSprite(spriteNewName);
      return makeModel(modelName, spriteOld, spriteNew);
   }

   public static C_4528_ makeModel(String modelName, C_4486_ spriteOld, C_4486_ spriteNew) {
      if (spriteOld != null && spriteNew != null) {
         C_4535_ modelManager = Config.getModelManager();
         if (modelManager == null) {
            return null;
         } else {
            C_4536_ mrl = C_4536_.m_245263_(modelName, "");
            C_4528_ model = modelManager.m_119422_(mrl);
            if (model != null && model != modelManager.m_119409_()) {
               C_4528_ modelNew = ModelUtils.duplicateModel(model);
               C_4687_[] faces = C_4687_.f_122346_;

               for (int i = 0; i < faces.length; i++) {
                  C_4687_ face = faces[i];
                  List<C_4196_> quads = modelNew.m_213637_(null, face, RANDOM);
                  replaceTexture(quads, spriteOld, spriteNew);
               }

               List<C_4196_> quadsGeneral = modelNew.m_213637_(null, null, RANDOM);
               replaceTexture(quadsGeneral, spriteOld, spriteNew);
               return modelNew;
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private static void replaceTexture(List<C_4196_> quads, C_4486_ spriteOld, C_4486_ spriteNew) {
      List<C_4196_> quadsNew = new ArrayList();

      for (C_4196_ quad : quads) {
         if (quad.m_173410_() == spriteOld) {
            quad = new BakedQuadRetextured(quad, spriteNew);
         }

         quadsNew.add(quad);
      }

      quads.clear();
      quads.addAll(quadsNew);
   }

   public static void snapVertexPosition(Vector3f pos) {
      pos.set(snapVertexCoord(pos.x()), snapVertexCoord(pos.y()), snapVertexCoord(pos.z()));
   }

   private static float snapVertexCoord(float x) {
      if (x > -1.0E-6F && x < 1.0E-6F) {
         return 0.0F;
      } else {
         return x > 0.999999F && x < 1.000001F ? 1.0F : x;
      }
   }

   public static C_3040_ getOffsetBoundingBox(C_3040_ aabb, C_2060_ offsetType, C_4675_ pos) {
      int x = pos.u();
      int z = pos.w();
      long k = (long)(x * 3129871) ^ (long)z * 116129781L;
      k = k * k * 42317861L + k * 11L;
      double dx = ((double)((float)(k >> 16 & 15L) / 15.0F) - 0.5) * 0.5;
      double dz = ((double)((float)(k >> 24 & 15L) / 15.0F) - 0.5) * 0.5;
      double dy = 0.0;
      if (offsetType == C_2060_.XYZ) {
         dy = ((double)((float)(k >> 20 & 15L) / 15.0F) - 1.0) * 0.2;
      }

      return aabb.m_82386_(dx, dy, dz);
   }
}
