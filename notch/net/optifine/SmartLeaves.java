package net.optifine;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_4196_;
import net.minecraft.src.C_4528_;
import net.minecraft.src.C_4535_;
import net.minecraft.src.C_4536_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_5265_;
import net.optifine.model.ModelUtils;
import net.optifine.util.RandomUtils;

public class SmartLeaves {
   private static C_4528_ modelLeavesCullAcacia = null;
   private static C_4528_ modelLeavesCullBirch = null;
   private static C_4528_ modelLeavesCullDarkOak = null;
   private static C_4528_ modelLeavesCullJungle = null;
   private static C_4528_ modelLeavesCullOak = null;
   private static C_4528_ modelLeavesCullSpruce = null;
   private static List generalQuadsCullAcacia = null;
   private static List generalQuadsCullBirch = null;
   private static List generalQuadsCullDarkOak = null;
   private static List generalQuadsCullJungle = null;
   private static List generalQuadsCullOak = null;
   private static List generalQuadsCullSpruce = null;
   private static C_4528_ modelLeavesDoubleAcacia = null;
   private static C_4528_ modelLeavesDoubleBirch = null;
   private static C_4528_ modelLeavesDoubleDarkOak = null;
   private static C_4528_ modelLeavesDoubleJungle = null;
   private static C_4528_ modelLeavesDoubleOak = null;
   private static C_4528_ modelLeavesDoubleSpruce = null;
   private static final C_212974_ RANDOM = RandomUtils.makeThreadSafeRandomSource(0);

   public static C_4528_ getLeavesModel(C_4528_ model, C_2064_ stateIn) {
      if (!Config.isTreesSmart()) {
         return model;
      } else {
         List generalQuads = model.m_213637_(stateIn, null, RANDOM);
         if (generalQuads == generalQuadsCullAcacia) {
            return modelLeavesDoubleAcacia;
         } else if (generalQuads == generalQuadsCullBirch) {
            return modelLeavesDoubleBirch;
         } else if (generalQuads == generalQuadsCullDarkOak) {
            return modelLeavesDoubleDarkOak;
         } else if (generalQuads == generalQuadsCullJungle) {
            return modelLeavesDoubleJungle;
         } else if (generalQuads == generalQuadsCullOak) {
            return modelLeavesDoubleOak;
         } else {
            return generalQuads == generalQuadsCullSpruce ? modelLeavesDoubleSpruce : model;
         }
      }
   }

   public static boolean isSameLeaves(C_2064_ state1, C_2064_ state2) {
      if (state1 == state2) {
         return true;
      } else {
         C_1706_ block1 = state1.b();
         C_1706_ block2 = state2.b();
         return block1 == block2;
      }
   }

   public static void updateLeavesModels() {
      List updatedTypes = new ArrayList();
      modelLeavesCullAcacia = getModelCull("acacia", updatedTypes);
      modelLeavesCullBirch = getModelCull("birch", updatedTypes);
      modelLeavesCullDarkOak = getModelCull("dark_oak", updatedTypes);
      modelLeavesCullJungle = getModelCull("jungle", updatedTypes);
      modelLeavesCullOak = getModelCull("oak", updatedTypes);
      modelLeavesCullSpruce = getModelCull("spruce", updatedTypes);
      generalQuadsCullAcacia = getGeneralQuadsSafe(modelLeavesCullAcacia);
      generalQuadsCullBirch = getGeneralQuadsSafe(modelLeavesCullBirch);
      generalQuadsCullDarkOak = getGeneralQuadsSafe(modelLeavesCullDarkOak);
      generalQuadsCullJungle = getGeneralQuadsSafe(modelLeavesCullJungle);
      generalQuadsCullOak = getGeneralQuadsSafe(modelLeavesCullOak);
      generalQuadsCullSpruce = getGeneralQuadsSafe(modelLeavesCullSpruce);
      modelLeavesDoubleAcacia = getModelDoubleFace(modelLeavesCullAcacia);
      modelLeavesDoubleBirch = getModelDoubleFace(modelLeavesCullBirch);
      modelLeavesDoubleDarkOak = getModelDoubleFace(modelLeavesCullDarkOak);
      modelLeavesDoubleJungle = getModelDoubleFace(modelLeavesCullJungle);
      modelLeavesDoubleOak = getModelDoubleFace(modelLeavesCullOak);
      modelLeavesDoubleSpruce = getModelDoubleFace(modelLeavesCullSpruce);
      if (updatedTypes.size() > 0) {
         Config.dbg("Enable face culling: " + Config.arrayToString(updatedTypes.toArray()));
      }
   }

   private static List getGeneralQuadsSafe(C_4528_ model) {
      return model == null ? null : model.m_213637_(null, null, RANDOM);
   }

   static C_4528_ getModelCull(String type, List updatedTypes) {
      C_4535_ modelManager = Config.getModelManager();
      if (modelManager == null) {
         return null;
      } else {
         C_5265_ locState = new C_5265_("blockstates/" + type + "_leaves.json");
         if (!Config.isFromDefaultResourcePack(locState)) {
            return null;
         } else {
            C_5265_ locModel = new C_5265_("models/block/" + type + "_leaves.json");
            if (!Config.isFromDefaultResourcePack(locModel)) {
               return null;
            } else {
               C_4536_ mrl = C_4536_.m_245263_(type + "_leaves", "normal");
               C_4528_ model = modelManager.m_119422_(mrl);
               if (model != null && model != modelManager.m_119409_()) {
                  List listGeneral = model.m_213637_(null, null, RANDOM);
                  if (listGeneral.size() == 0) {
                     return model;
                  } else if (listGeneral.size() != 6) {
                     return null;
                  } else {
                     for (C_4196_ quad : listGeneral) {
                        List listFace = model.m_213637_(null, quad.m_111306_(), RANDOM);
                        if (listFace.size() > 0) {
                           return null;
                        }

                        listFace.add(quad);
                     }

                     listGeneral.clear();
                     updatedTypes.add(type + "_leaves");
                     return model;
                  }
               } else {
                  return null;
               }
            }
         }
      }
   }

   private static C_4528_ getModelDoubleFace(C_4528_ model) {
      if (model == null) {
         return null;
      } else if (model.m_213637_(null, null, RANDOM).size() > 0) {
         Config.warn("SmartLeaves: Model is not cube, general quads: " + model.m_213637_(null, null, RANDOM).size() + ", model: " + model);
         return model;
      } else {
         C_4687_[] faces = C_4687_.f_122346_;

         for (int i = 0; i < faces.length; i++) {
            C_4687_ face = faces[i];
            List<C_4196_> quads = model.m_213637_(null, face, RANDOM);
            if (quads.size() != 1) {
               Config.warn("SmartLeaves: Model is not cube, side: " + face + ", quads: " + quads.size() + ", model: " + model);
               return model;
            }
         }

         C_4528_ model2 = ModelUtils.duplicateModel(model);
         List[] faceQuads = new List[faces.length];

         for (int ix = 0; ix < faces.length; ix++) {
            C_4687_ face = faces[ix];
            List<C_4196_> quads = model2.m_213637_(null, face, RANDOM);
            C_4196_ quad = (C_4196_)quads.get(0);
            C_4196_ quad2 = new C_4196_((int[])quad.m_111303_().clone(), quad.m_111305_(), quad.m_111306_(), quad.m_173410_(), quad.m_111307_());
            int[] vd = quad2.m_111303_();
            int[] vd2 = (int[])vd.clone();
            int step = vd.length / 4;
            System.arraycopy(vd, 0 * step, vd2, 3 * step, step);
            System.arraycopy(vd, 1 * step, vd2, 2 * step, step);
            System.arraycopy(vd, 2 * step, vd2, 1 * step, step);
            System.arraycopy(vd, 3 * step, vd2, 0 * step, step);
            System.arraycopy(vd2, 0, vd, 0, vd2.length);
            quads.add(quad2);
         }

         return model2;
      }
   }
}
