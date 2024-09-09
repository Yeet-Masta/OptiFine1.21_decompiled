package net.minecraftforge.client.extensions;

import java.util.List;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_268388_;
import net.minecraft.src.C_3181_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4486_;
import net.minecraft.src.C_4528_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;

public interface IForgeBakedModel {
   default C_4528_ getBakedModel() {
      return (C_4528_)this;
   }

   default List getQuads(C_2064_ state, C_4687_ side, C_212974_ rand, ModelData extraData) {
      return this.getBakedModel().m_213637_(state, side, rand);
   }

   default List getQuads(C_2064_ state, C_4687_ side, C_212974_ rand, ModelData data, C_4168_ renderType) {
      return this.getBakedModel().m_213637_(state, side, rand);
   }

   default boolean isAmbientOcclusion(C_2064_ state) {
      return this.getBakedModel().m_7541_();
   }

   default boolean useAmbientOcclusion(C_2064_ state) {
      return this.getBakedModel().m_7541_();
   }

   default boolean useAmbientOcclusion(C_2064_ state, C_4168_ renderType) {
      return this.isAmbientOcclusion(state);
   }

   default ModelData getModelData(C_1557_ world, C_4675_ pos, C_2064_ state, ModelData tileData) {
      return tileData;
   }

   default C_4486_ getParticleTexture(ModelData data) {
      return this.getBakedModel().m_6160_();
   }

   default C_4486_ getParticleIcon(ModelData data) {
      return this.self().m_6160_();
   }

   default List getRenderPasses(C_1391_ itemStack, boolean fabulous) {
      return List.of(this.self());
   }

   default List getRenderTypes(C_1391_ itemStack, boolean fabulous) {
      return List.of();
   }

   private C_4528_ self() {
      return (C_4528_)this;
   }

   default ChunkRenderTypeSet getRenderTypes(C_2064_ state, C_212974_ rand, ModelData data) {
      return null;
   }

   default C_4528_ applyTransform(C_268388_ transformType, C_3181_ poseStack, boolean applyLeftHandTransform) {
      this.self().m_7442_().m_269404_(transformType).m_111763_(applyLeftHandTransform, poseStack);
      return this.self();
   }
}
