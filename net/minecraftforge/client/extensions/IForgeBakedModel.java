package net.minecraftforge.client.extensions;

import java.util.List;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_268388_;
import net.minecraft.src.C_4675_;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;

public interface IForgeBakedModel {
   default BakedModel getBakedModel() {
      return (BakedModel)this;
   }

   default List<BakedQuad> getQuads(BlockState state, Direction side, C_212974_ rand, ModelData extraData) {
      return this.getBakedModel().a(state, side, rand);
   }

   default List<BakedQuad> getQuads(BlockState state, Direction side, C_212974_ rand, ModelData data, RenderType renderType) {
      return this.getBakedModel().a(state, side, rand);
   }

   default boolean isAmbientOcclusion(BlockState state) {
      return this.getBakedModel().a();
   }

   default boolean useAmbientOcclusion(BlockState state) {
      return this.getBakedModel().a();
   }

   default boolean useAmbientOcclusion(BlockState state, RenderType renderType) {
      return this.isAmbientOcclusion(state);
   }

   default ModelData getModelData(C_1557_ world, C_4675_ pos, BlockState state, ModelData tileData) {
      return tileData;
   }

   default TextureAtlasSprite getParticleTexture(ModelData data) {
      return this.getBakedModel().e();
   }

   default TextureAtlasSprite getParticleIcon(ModelData data) {
      return this.self().e();
   }

   default List<BakedModel> getRenderPasses(C_1391_ itemStack, boolean fabulous) {
      return List.of(this.self());
   }

   default List<RenderType> getRenderTypes(C_1391_ itemStack, boolean fabulous) {
      return List.of();
   }

   private BakedModel self() {
      return (BakedModel)this;
   }

   default ChunkRenderTypeSet getRenderTypes(BlockState state, C_212974_ rand, ModelData data) {
      return null;
   }

   default BakedModel applyTransform(C_268388_ transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
      this.self().f().m_269404_(transformType).a(applyLeftHandTransform, poseStack);
      return this.self();
   }
}
