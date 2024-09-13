package net.optifine.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class CustomEntityModel extends Model {
   public CustomEntityModel(Function<ResourceLocation, RenderType> renderTypeIn) {
      super(renderTypeIn);
   }

   @Override
   public void m_7695_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }
}
