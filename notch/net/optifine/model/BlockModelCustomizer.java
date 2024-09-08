package net.optifine.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4196_;
import net.minecraft.src.C_4528_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.optifine.BetterGrass;
import net.optifine.Config;
import net.optifine.ConnectedTextures;
import net.optifine.NaturalTextures;
import net.optifine.SmartLeaves;
import net.optifine.render.RenderEnv;
import net.optifine.render.RenderTypes;

public class BlockModelCustomizer {
   private static final List<C_4196_> NO_QUADS = ImmutableList.of();

   public static C_4528_ getRenderModel(C_4528_ modelIn, C_2064_ stateIn, RenderEnv renderEnv) {
      if (renderEnv.isSmartLeaves()) {
         modelIn = SmartLeaves.getLeavesModel(modelIn, stateIn);
      }

      return modelIn;
   }

   public static List<C_4196_> getRenderQuads(
      List<C_4196_> quads, C_1557_ worldIn, C_2064_ stateIn, C_4675_ posIn, C_4687_ enumfacing, C_4168_ layer, long rand, RenderEnv renderEnv
   ) {
      if (enumfacing != null) {
         if (renderEnv.isSmartLeaves() && SmartLeaves.isSameLeaves(worldIn.a_(posIn.m_121945_(enumfacing)), stateIn)) {
            return NO_QUADS;
         }

         if (!renderEnv.isBreakingAnimation(quads) && Config.isBetterGrass()) {
            quads = BetterGrass.getFaceQuads(worldIn, stateIn, posIn, enumfacing, quads);
         }
      }

      List<C_4196_> quadsNew = renderEnv.getListQuadsCustomizer();
      quadsNew.clear();

      for (int i = 0; i < quads.size(); i++) {
         C_4196_ quad = (C_4196_)quads.get(i);
         C_4196_[] quadArr = getRenderQuads(quad, worldIn, stateIn, posIn, enumfacing, rand, renderEnv);
         if (i == 0 && quads.size() == 1 && quadArr.length == 1 && quadArr[0] == quad && quad.getQuadEmissive() == null) {
            return quads;
         }

         for (int q = 0; q < quadArr.length; q++) {
            C_4196_ quadSingle = quadArr[q];
            quadsNew.add(quadSingle);
            if (quadSingle.getQuadEmissive() != null) {
               renderEnv.getListQuadsOverlay(getEmissiveLayer(layer)).addQuad(quadSingle.getQuadEmissive(), stateIn);
               renderEnv.setOverlaysRendered(true);
            }
         }
      }

      return quadsNew;
   }

   private static C_4168_ getEmissiveLayer(C_4168_ layer) {
      return layer != null && layer != RenderTypes.SOLID ? layer : RenderTypes.CUTOUT_MIPPED;
   }

   private static C_4196_[] getRenderQuads(C_4196_ quad, C_1557_ worldIn, C_2064_ stateIn, C_4675_ posIn, C_4687_ enumfacing, long rand, RenderEnv renderEnv) {
      if (renderEnv.isBreakingAnimation(quad)) {
         return renderEnv.getArrayQuadsCtm(quad);
      } else {
         C_4196_ quadOriginal = quad;
         if (Config.isConnectedTextures()) {
            C_4196_[] quads = ConnectedTextures.getConnectedTexture(worldIn, stateIn, posIn, quad, renderEnv);
            if (quads.length != 1 || quads[0] != quad) {
               return quads;
            }
         }

         if (Config.isNaturalTextures()) {
            quad = NaturalTextures.getNaturalTexture(stateIn, posIn, quad);
            if (quad != quadOriginal) {
               return renderEnv.getArrayQuadsCtm(quad);
            }
         }

         return renderEnv.getArrayQuadsCtm(quad);
      }
   }
}
