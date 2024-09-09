package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WitherArmorLayer extends EnergySwirlLayer {
   private static final ResourceLocation f_117695_ = ResourceLocation.m_340282_("textures/entity/wither/wither_armor.png");
   public WitherBossModel f_117696_;
   public ResourceLocation customTextureLocation;

   public WitherArmorLayer(RenderLayerParent p_i174553_1_, EntityModelSet p_i174553_2_) {
      super(p_i174553_1_);
      this.f_117696_ = new WitherBossModel(p_i174553_2_.m_171103_(ModelLayers.f_171215_));
   }

   protected float m_7631_(float ticksIn) {
      return Mth.m_14089_(ticksIn * 0.02F) * 3.0F;
   }

   protected ResourceLocation m_7029_() {
      return this.customTextureLocation != null ? this.customTextureLocation : f_117695_;
   }

   protected EntityModel m_7193_() {
      return this.f_117696_;
   }
}
