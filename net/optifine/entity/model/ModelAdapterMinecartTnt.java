package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterMinecartTnt extends ModelAdapterMinecart {
   public ModelAdapterMinecartTnt() {
      super(EntityType.f_20475_, "tnt_minecart", 0.5F);
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      net.minecraft.client.renderer.entity.TntMinecartRenderer render = new net.minecraft.client.renderer.entity.TntMinecartRenderer(renderManager.getContext());
      if (!Reflector.RenderMinecart_modelMinecart.exists()) {
         Config.warn("Field not found: RenderMinecart.modelMinecart");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderMinecart_modelMinecart, modelBase);
         render.f_114477_ = shadowSize;
         return render;
      }
   }
}
