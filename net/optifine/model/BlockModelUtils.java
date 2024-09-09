package net.optifine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.phys.AABB;
import net.optifine.Config;
import net.optifine.util.RandomUtils;
import org.joml.Vector3f;

public class BlockModelUtils {
   private static final float VERTEX_COORD_ACCURACY = 1.0E-6F;
   private static final RandomSource RANDOM = RandomUtils.makeThreadSafeRandomSource(0);

   public static net.minecraft.client.resources.model.BakedModel makeModelCube(String spriteName, int tintIndex) {
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = Config.getTextureMap().getUploadedSprite(spriteName);
      return makeModelCube(sprite, tintIndex);
   }

   public static net.minecraft.client.resources.model.BakedModel makeModelCube(net.minecraft.client.renderer.texture.TextureAtlasSprite sprite, int tintIndex) {
      List generalQuads = new ArrayList();
      net.minecraft.core.Direction[] facings = net.minecraft.core.Direction.f_122346_;
      Map<net.minecraft.core.Direction, List<net.minecraft.client.renderer.block.model.BakedQuad>> faceQuads = new HashMap();

      for (int i = 0; i < facings.length; i++) {
         net.minecraft.core.Direction facing = facings[i];
         List quads = new ArrayList();
         quads.add(makeBakedQuad(facing, sprite, tintIndex));
         faceQuads.put(facing, quads);
      }

      net.minecraft.client.renderer.block.model.ItemOverrides itemOverrideList = net.minecraft.client.renderer.block.model.ItemOverrides.f_111734_;
      net.minecraft.client.resources.model.BakedModel bakedModel = new SimpleBakedModel(
         generalQuads, faceQuads, true, true, true, sprite, ItemTransforms.f_111786_, itemOverrideList
      );
      return bakedModel;
   }

   public static net.minecraft.client.resources.model.BakedModel joinModelsCube(
      net.minecraft.client.resources.model.BakedModel modelBase, net.minecraft.client.resources.model.BakedModel modelAdd
   ) {
      List<net.minecraft.client.renderer.block.model.BakedQuad> generalQuads = new ArrayList();
      generalQuads.addAll(modelBase.m_213637_(null, null, RANDOM));
      generalQuads.addAll(modelAdd.m_213637_(null, null, RANDOM));
      net.minecraft.core.Direction[] facings = net.minecraft.core.Direction.f_122346_;
      Map<net.minecraft.core.Direction, List<net.minecraft.client.renderer.block.model.BakedQuad>> faceQuads = new HashMap();

      for (int i = 0; i < facings.length; i++) {
         net.minecraft.core.Direction facing = facings[i];
         List quads = new ArrayList();
         quads.addAll(modelBase.m_213637_(null, facing, RANDOM));
         quads.addAll(modelAdd.m_213637_(null, facing, RANDOM));
         faceQuads.put(facing, quads);
      }

      boolean ao = modelBase.m_7541_();
      boolean builtIn = modelBase.m_7521_();
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = modelBase.m_6160_();
      ItemTransforms transforms = modelBase.m_7442_();
      net.minecraft.client.renderer.block.model.ItemOverrides itemOverrideList = modelBase.m_7343_();
      net.minecraft.client.resources.model.BakedModel bakedModel = new SimpleBakedModel(
         generalQuads, faceQuads, ao, builtIn, true, sprite, transforms, itemOverrideList
      );
      return bakedModel;
   }

   public static net.minecraft.client.renderer.block.model.BakedQuad makeBakedQuad(
      net.minecraft.core.Direction facing, net.minecraft.client.renderer.texture.TextureAtlasSprite sprite, int tintIndex
   ) {
      Vector3f posFrom = new Vector3f(0.0F, 0.0F, 0.0F);
      Vector3f posTo = new Vector3f(16.0F, 16.0F, 16.0F);
      BlockFaceUV uv = new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0);
      BlockElementFace face = new BlockElementFace(facing, tintIndex, "#" + facing.m_7912_(), uv);
      BlockModelRotation modelRotation = BlockModelRotation.X0_Y0;
      BlockElementRotation partRotation = null;
      boolean shade = true;
      net.minecraft.resources.ResourceLocation modelLoc = sprite.getName();
      net.minecraft.client.renderer.block.model.FaceBakery faceBakery = new net.minecraft.client.renderer.block.model.FaceBakery();
      return faceBakery.m_111600_(posFrom, posTo, face, sprite, facing, modelRotation, partRotation, shade);
   }

   public static net.minecraft.client.resources.model.BakedModel makeModel(String modelName, String spriteOldName, String spriteNewName) {
      net.minecraft.client.renderer.texture.TextureAtlas textureMap = Config.getTextureMap();
      net.minecraft.client.renderer.texture.TextureAtlasSprite spriteOld = textureMap.getUploadedSprite(spriteOldName);
      net.minecraft.client.renderer.texture.TextureAtlasSprite spriteNew = textureMap.getUploadedSprite(spriteNewName);
      return makeModel(modelName, spriteOld, spriteNew);
   }

   public static net.minecraft.client.resources.model.BakedModel makeModel(
      String modelName, net.minecraft.client.renderer.texture.TextureAtlasSprite spriteOld, net.minecraft.client.renderer.texture.TextureAtlasSprite spriteNew
   ) {
      if (spriteOld != null && spriteNew != null) {
         ModelManager modelManager = Config.getModelManager();
         if (modelManager == null) {
            return null;
         } else {
            ModelResourceLocation mrl = ModelResourceLocation.m_245263_(modelName, "");
            net.minecraft.client.resources.model.BakedModel model = modelManager.m_119422_(mrl);
            if (model != null && model != modelManager.m_119409_()) {
               net.minecraft.client.resources.model.BakedModel modelNew = net.optifine.model.ModelUtils.duplicateModel(model);
               net.minecraft.core.Direction[] faces = net.minecraft.core.Direction.f_122346_;

               for (int i = 0; i < faces.length; i++) {
                  net.minecraft.core.Direction face = faces[i];
                  List<net.minecraft.client.renderer.block.model.BakedQuad> quads = modelNew.m_213637_(null, face, RANDOM);
                  replaceTexture(quads, spriteOld, spriteNew);
               }

               List<net.minecraft.client.renderer.block.model.BakedQuad> quadsGeneral = modelNew.m_213637_(null, null, RANDOM);
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

   private static void replaceTexture(
      List<net.minecraft.client.renderer.block.model.BakedQuad> quads,
      net.minecraft.client.renderer.texture.TextureAtlasSprite spriteOld,
      net.minecraft.client.renderer.texture.TextureAtlasSprite spriteNew
   ) {
      List<net.minecraft.client.renderer.block.model.BakedQuad> quadsNew = new ArrayList();

      for (net.minecraft.client.renderer.block.model.BakedQuad quad : quads) {
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

   public static AABB getOffsetBoundingBox(AABB aabb, OffsetType offsetType, BlockPos pos) {
      int x = pos.m_123341_();
      int z = pos.m_123343_();
      long k = (long)(x * 3129871) ^ (long)z * 116129781L;
      k = k * k * 42317861L + k * 11L;
      double dx = ((double)((float)(k >> 16 & 15L) / 15.0F) - 0.5) * 0.5;
      double dz = ((double)((float)(k >> 24 & 15L) / 15.0F) - 0.5) * 0.5;
      double dy = 0.0;
      if (offsetType == OffsetType.XYZ) {
         dy = ((double)((float)(k >> 20 & 15L) / 15.0F) - 1.0) * 0.2;
      }

      return aabb.m_82386_(dx, dy, dz);
   }
}
