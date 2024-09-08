package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;

public class CreeperPowerLayer extends EnergySwirlLayer<Creeper, CreeperModel<Creeper>> {
   private static final ResourceLocation f_116676_ = ResourceLocation.m_340282_("textures/entity/creeper/creeper_armor.png");
   public CreeperModel<Creeper> f_116677_;
   public ResourceLocation customTextureLocation;

   public CreeperPowerLayer(RenderLayerParent<Creeper, CreeperModel<Creeper>> p_i174470_1_, EntityModelSet p_i174470_2_) {
      super(p_i174470_1_);
      this.f_116677_ = new CreeperModel(p_i174470_2_.m_171103_(ModelLayers.f_171129_));
   }

   protected float m_7631_(float ticksIn) {
      return ticksIn * 0.01F;
   }

   protected ResourceLocation m_7029_() {
      return this.customTextureLocation != null ? this.customTextureLocation : f_116676_;
   }

   protected EntityModel<Creeper> m_7193_() {
      return this.f_116677_;
   }
}
