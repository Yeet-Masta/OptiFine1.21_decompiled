package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterZombieVillager extends ModelAdapterBiped {
   public ModelAdapterZombieVillager() {
      super(EntityType.f_20530_, "zombie_villager", 0.5F);
   }

   public Model makeModel() {
      return new ZombieVillagerModel(bakeModelLayer(ModelLayers.f_171228_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      ReloadableResourceManager resourceManager = (ReloadableResourceManager)Minecraft.m_91087_().m_91098_();
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ZombieVillagerRenderer render = new ZombieVillagerRenderer(renderManager.getContext());
      render.f_115290_ = (ZombieVillagerModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
