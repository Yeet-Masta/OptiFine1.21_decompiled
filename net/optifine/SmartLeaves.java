package net.optifine;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.optifine.util.RandomUtils;

public class SmartLeaves {
   private static net.minecraft.client.resources.model.BakedModel modelLeavesCullAcacia = null;
   private static net.minecraft.client.resources.model.BakedModel modelLeavesCullBirch = null;
   private static net.minecraft.client.resources.model.BakedModel modelLeavesCullDarkOak = null;
   private static net.minecraft.client.resources.model.BakedModel modelLeavesCullJungle = null;
   private static net.minecraft.client.resources.model.BakedModel modelLeavesCullOak = null;
   private static net.minecraft.client.resources.model.BakedModel modelLeavesCullSpruce = null;
   private static List generalQuadsCullAcacia = null;
   private static List generalQuadsCullBirch = null;
   private static List generalQuadsCullDarkOak = null;
   private static List generalQuadsCullJungle = null;
   private static List generalQuadsCullOak = null;
   private static List generalQuadsCullSpruce = null;
   private static net.minecraft.client.resources.model.BakedModel modelLeavesDoubleAcacia = null;
   private static net.minecraft.client.resources.model.BakedModel modelLeavesDoubleBirch = null;
   private static net.minecraft.client.resources.model.BakedModel modelLeavesDoubleDarkOak = null;
   private static net.minecraft.client.resources.model.BakedModel modelLeavesDoubleJungle = null;
   private static net.minecraft.client.resources.model.BakedModel modelLeavesDoubleOak = null;
   private static net.minecraft.client.resources.model.BakedModel modelLeavesDoubleSpruce = null;
   private static final RandomSource RANDOM = RandomUtils.makeThreadSafeRandomSource(0);

   public static net.minecraft.client.resources.model.BakedModel getLeavesModel(
      net.minecraft.client.resources.model.BakedModel model, net.minecraft.world.level.block.state.BlockState stateIn
   ) {
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

   public static boolean isSameLeaves(net.minecraft.world.level.block.state.BlockState state1, net.minecraft.world.level.block.state.BlockState state2) {
      if (state1 == state2) {
         return true;
      } else {
         Block block1 = state1.m_60734_();
         Block block2 = state2.m_60734_();
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

   private static List getGeneralQuadsSafe(net.minecraft.client.resources.model.BakedModel model) {
      return model == null ? null : model.m_213637_(null, null, RANDOM);
   }

   static net.minecraft.client.resources.model.BakedModel getModelCull(String type, List updatedTypes) {
      ModelManager modelManager = Config.getModelManager();
      if (modelManager == null) {
         return null;
      } else {
         net.minecraft.resources.ResourceLocation locState = new net.minecraft.resources.ResourceLocation("blockstates/" + type + "_leaves.json");
         if (!Config.isFromDefaultResourcePack(locState)) {
            return null;
         } else {
            net.minecraft.resources.ResourceLocation locModel = new net.minecraft.resources.ResourceLocation("models/block/" + type + "_leaves.json");
            if (!Config.isFromDefaultResourcePack(locModel)) {
               return null;
            } else {
               ModelResourceLocation mrl = ModelResourceLocation.m_245263_(type + "_leaves", "normal");
               net.minecraft.client.resources.model.BakedModel model = modelManager.m_119422_(mrl);
               if (model != null && model != modelManager.m_119409_()) {
                  List listGeneral = model.m_213637_(null, null, RANDOM);
                  if (listGeneral.size() == 0) {
                     return model;
                  } else if (listGeneral.size() != 6) {
                     return null;
                  } else {
                     for (net.minecraft.client.renderer.block.model.BakedQuad quad : listGeneral) {
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

   private static net.minecraft.client.resources.model.BakedModel getModelDoubleFace(net.minecraft.client.resources.model.BakedModel model) {
      if (model == null) {
         return null;
      } else if (model.m_213637_(null, null, RANDOM).size() > 0) {
         Config.warn("SmartLeaves: Model is not cube, general quads: " + model.m_213637_(null, null, RANDOM).size() + ", model: " + model);
         return model;
      } else {
         net.minecraft.core.Direction[] faces = net.minecraft.core.Direction.f_122346_;

         for (int i = 0; i < faces.length; i++) {
            net.minecraft.core.Direction face = faces[i];
            List<net.minecraft.client.renderer.block.model.BakedQuad> quads = model.m_213637_(null, face, RANDOM);
            if (quads.size() != 1) {
               Config.warn("SmartLeaves: Model is not cube, side: " + face + ", quads: " + quads.size() + ", model: " + model);
               return model;
            }
         }

         net.minecraft.client.resources.model.BakedModel model2 = net.optifine.model.ModelUtils.duplicateModel(model);
         List[] faceQuads = new List[faces.length];

         for (int ix = 0; ix < faces.length; ix++) {
            net.minecraft.core.Direction face = faces[ix];
            List<net.minecraft.client.renderer.block.model.BakedQuad> quads = model2.m_213637_(null, face, RANDOM);
            net.minecraft.client.renderer.block.model.BakedQuad quad = (net.minecraft.client.renderer.block.model.BakedQuad)quads.get(0);
            net.minecraft.client.renderer.block.model.BakedQuad quad2 = new net.minecraft.client.renderer.block.model.BakedQuad(
               (int[])quad.m_111303_().clone(), quad.m_111305_(), quad.m_111306_(), quad.m_173410_(), quad.m_111307_()
            );
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
