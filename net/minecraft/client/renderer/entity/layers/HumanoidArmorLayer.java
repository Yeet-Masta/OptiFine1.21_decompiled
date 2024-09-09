package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.component.DyedItemColor;
import net.optifine.util.TextureUtils;

public class HumanoidArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>>
   extends net.minecraft.client.renderer.entity.layers.RenderLayer<T, M> {
   private static final Map<String, net.minecraft.resources.ResourceLocation> f_117070_ = Maps.newHashMap();
   private final A f_117071_;
   private final A f_117072_;
   private final net.minecraft.client.renderer.texture.TextureAtlas f_266073_;

   public HumanoidArmorLayer(RenderLayerParent<T, M> rendererIn, A modelLeggingsIn, A modelArmorIn, ModelManager modelManagerIn) {
      super(rendererIn);
      this.f_117071_ = modelLeggingsIn;
      this.f_117072_ = modelArmorIn;
      this.f_266073_ = modelManagerIn.m_119428_(Sheets.f_265912_);
   }

   public void m_6494_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlot.CHEST, packedLightIn, this.m_117078_(EquipmentSlot.CHEST));
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlot.LEGS, packedLightIn, this.m_117078_(EquipmentSlot.LEGS));
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlot.FEET, packedLightIn, this.m_117078_(EquipmentSlot.FEET));
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlot.HEAD, packedLightIn, this.m_117078_(EquipmentSlot.HEAD));
   }

   private void m_117118_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      T livingEntityIn,
      EquipmentSlot slotIn,
      int packedLightIn,
      A modelIn
   ) {
      ItemStack itemstack = livingEntityIn.m_6844_(slotIn);
      if (itemstack.m_41720_() instanceof ArmorItem armoritem && armoritem.m_40402_() == slotIn) {
         this.m_117386_().m_102872_(modelIn);
         this.m_117125_(modelIn, slotIn);
         boolean flag = this.m_117128_(slotIn);
         net.minecraft.world.item.ArmorMaterial armormaterial = (net.minecraft.world.item.ArmorMaterial)armoritem.m_40401_().m_203334_();
         int i = itemstack.m_204117_(ItemTags.f_314020_) ? ARGB32.m_321570_(DyedItemColor.m_322889_(itemstack, -6265536)) : -1;

         for (net.minecraft.world.item.ArmorMaterial.Layer armormaterial$layer : armormaterial.f_315892_()) {
            int j = armormaterial$layer.m_324910_() ? i : -1;
            this.m_289609_(matrixStackIn, bufferIn, packedLightIn, modelIn, j, armormaterial$layer.m_318738_(flag));
         }

         ArmorTrim armortrim = (ArmorTrim)itemstack.m_323252_(DataComponents.f_315199_);
         if (armortrim != null) {
            this.m_289604_(armoritem.m_40401_(), matrixStackIn, bufferIn, packedLightIn, armortrim, modelIn, flag);
         }

         if (itemstack.m_41790_()) {
            this.m_289597_(matrixStackIn, bufferIn, packedLightIn, modelIn);
         }
      }
   }

   protected void m_117125_(A modelIn, EquipmentSlot slotIn) {
      modelIn.m_8009_(false);
      switch (slotIn) {
         case HEAD:
            modelIn.f_102808_.f_104207_ = true;
            modelIn.f_102809_.f_104207_ = true;
            break;
         case CHEST:
            modelIn.f_102810_.f_104207_ = true;
            modelIn.f_102811_.f_104207_ = true;
            modelIn.f_102812_.f_104207_ = true;
            break;
         case LEGS:
            modelIn.f_102810_.f_104207_ = true;
            modelIn.f_102813_.f_104207_ = true;
            modelIn.f_102814_.f_104207_ = true;
            break;
         case FEET:
            modelIn.f_102813_.f_104207_ = true;
            modelIn.f_102814_.f_104207_ = true;
      }
   }

   private void m_289609_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn,
      A bipedModelIn,
      int colorIn,
      net.minecraft.resources.ResourceLocation suffixIn
   ) {
      com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = bufferIn.m_6299_(net.minecraft.client.renderer.RenderType.m_110431_(suffixIn));
      bipedModelIn.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, colorIn);
   }

   private void m_289604_(
      Holder<net.minecraft.world.item.ArmorMaterial> armorMaterialIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn,
      ArmorTrim trimIn,
      A bipedModelIn,
      boolean isLegSlot
   ) {
      net.minecraft.client.renderer.texture.TextureAtlasSprite textureatlassprite = this.f_266073_
         .m_118316_(isLegSlot ? trimIn.m_267774_(armorMaterialIn) : trimIn.m_267606_(armorMaterialIn));
      textureatlassprite = TextureUtils.getCustomSprite(textureatlassprite);
      com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = textureatlassprite.m_118381_(
         bufferIn.m_6299_(Sheets.m_266442_(((TrimPattern)trimIn.m_266429_().m_203334_()).f_290976_()))
      );
      bipedModelIn.m_340227_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
   }

   private void m_289597_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, net.minecraft.client.renderer.MultiBufferSource bufferIn, int packedLightIn, A bipedModelIn
   ) {
      bipedModelIn.m_340227_(matrixStackIn, bufferIn.m_6299_(net.minecraft.client.renderer.RenderType.m_110484_()), packedLightIn, OverlayTexture.f_118083_);
   }

   private A m_117078_(EquipmentSlot slotIn) {
      return this.m_117128_(slotIn) ? this.f_117071_ : this.f_117072_;
   }

   private boolean m_117128_(EquipmentSlot slotIn) {
      return slotIn == EquipmentSlot.LEGS;
   }
}
