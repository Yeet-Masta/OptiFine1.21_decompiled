package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterZombieVillager extends ModelAdapterBiped {
   public ModelAdapterZombieVillager() {
      super(EntityType.f_20530_, "zombie_villager", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ZombieVillagerModel(bakeModelLayer(ModelLayers.f_171228_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.server.packs.resources.ReloadableResourceManager resourceManager = (net.minecraft.server.packs.resources.ReloadableResourceManager)Minecraft.m_91087_()
         .m_91098_();
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ZombieVillagerRenderer render = new ZombieVillagerRenderer(renderManager.getContext());
      render.f_115290_ = (ZombieVillagerModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
