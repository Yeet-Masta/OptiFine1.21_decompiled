package net.optifine.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class EnderCrystalModel extends Model {
   public ModelPart cube;
   public ModelPart glass;
   public ModelPart base;

   public EnderCrystalModel() {
      super(RenderType::m_110458_);
      EndCrystalRenderer renderer = new EndCrystalRenderer(Minecraft.m_91087_().m_91290_().getContext());
      this.cube = (ModelPart)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 0);
      this.glass = (ModelPart)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 1);
      this.base = (ModelPart)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 2);
   }

   public EndCrystalRenderer updateRenderer(EndCrystalRenderer render) {
      if (!Reflector.RenderEnderCrystal_modelRenderers.exists()) {
         Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystal");
         return null;
      } else {
         Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 0, this.cube);
         Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 1, this.glass);
         Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 2, this.base);
         return render;
      }
   }

   public void m_7695_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }
}
