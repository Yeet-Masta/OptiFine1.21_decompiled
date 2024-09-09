package net.optifine.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.optifine.BetterGrass;
import net.optifine.Config;
import net.optifine.ConnectedTextures;
import net.optifine.NaturalTextures;
import net.optifine.SmartLeaves;
import net.optifine.render.RenderEnv;
import net.optifine.render.RenderTypes;

public class BlockModelCustomizer {
   private static final List<net.minecraft.client.renderer.block.model.BakedQuad> NO_QUADS = ImmutableList.of();

   public static net.minecraft.client.resources.model.BakedModel getRenderModel(
      net.minecraft.client.resources.model.BakedModel modelIn, net.minecraft.world.level.block.state.BlockState stateIn, RenderEnv renderEnv
   ) {
      if (renderEnv.isSmartLeaves()) {
         modelIn = SmartLeaves.getLeavesModel(modelIn, stateIn);
      }

      return modelIn;
   }

   public static List<net.minecraft.client.renderer.block.model.BakedQuad> getRenderQuads(
      List<net.minecraft.client.renderer.block.model.BakedQuad> quads,
      BlockAndTintGetter worldIn,
      net.minecraft.world.level.block.state.BlockState stateIn,
      BlockPos posIn,
      net.minecraft.core.Direction enumfacing,
      net.minecraft.client.renderer.RenderType layer,
      long rand,
      RenderEnv renderEnv
   ) {
      if (enumfacing != null) {
         if (renderEnv.isSmartLeaves() && SmartLeaves.isSameLeaves(worldIn.m_8055_(posIn.m_121945_(enumfacing)), stateIn)) {
            return NO_QUADS;
         }

         if (!renderEnv.isBreakingAnimation(quads) && Config.isBetterGrass()) {
            quads = BetterGrass.getFaceQuads(worldIn, stateIn, posIn, enumfacing, quads);
         }
      }

      List<net.minecraft.client.renderer.block.model.BakedQuad> quadsNew = renderEnv.getListQuadsCustomizer();
      quadsNew.clear();

      for (int i = 0; i < quads.size(); i++) {
         net.minecraft.client.renderer.block.model.BakedQuad quad = (net.minecraft.client.renderer.block.model.BakedQuad)quads.get(i);
         net.minecraft.client.renderer.block.model.BakedQuad[] quadArr = getRenderQuads(quad, worldIn, stateIn, posIn, enumfacing, rand, renderEnv);
         if (i == 0 && quads.size() == 1 && quadArr.length == 1 && quadArr[0] == quad && quad.getQuadEmissive() == null) {
            return quads;
         }

         for (int q = 0; q < quadArr.length; q++) {
            net.minecraft.client.renderer.block.model.BakedQuad quadSingle = quadArr[q];
            quadsNew.add(quadSingle);
            if (quadSingle.getQuadEmissive() != null) {
               renderEnv.getListQuadsOverlay(getEmissiveLayer(layer)).addQuad(quadSingle.getQuadEmissive(), stateIn);
               renderEnv.setOverlaysRendered(true);
            }
         }
      }

      return quadsNew;
   }

   private static net.minecraft.client.renderer.RenderType getEmissiveLayer(net.minecraft.client.renderer.RenderType layer) {
      return layer != null && layer != RenderTypes.SOLID ? layer : RenderTypes.CUTOUT_MIPPED;
   }

   private static net.minecraft.client.renderer.block.model.BakedQuad[] getRenderQuads(
      net.minecraft.client.renderer.block.model.BakedQuad quad,
      BlockAndTintGetter worldIn,
      net.minecraft.world.level.block.state.BlockState stateIn,
      BlockPos posIn,
      net.minecraft.core.Direction enumfacing,
      long rand,
      RenderEnv renderEnv
   ) {
      if (renderEnv.isBreakingAnimation(quad)) {
         return renderEnv.getArrayQuadsCtm(quad);
      } else {
         net.minecraft.client.renderer.block.model.BakedQuad quadOriginal = quad;
         if (Config.isConnectedTextures()) {
            net.minecraft.client.renderer.block.model.BakedQuad[] quads = ConnectedTextures.getConnectedTexture(worldIn, stateIn, posIn, quad, renderEnv);
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
