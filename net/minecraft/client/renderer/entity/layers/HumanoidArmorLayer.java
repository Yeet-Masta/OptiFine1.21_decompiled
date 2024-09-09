package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.component.DyedItemColor;
import net.optifine.util.TextureUtils;

public class HumanoidArmorLayer extends RenderLayer {
   private static final Map f_117070_ = Maps.newHashMap();
   private final HumanoidModel f_117071_;
   private final HumanoidModel f_117072_;
   private final TextureAtlas f_266073_;

   public HumanoidArmorLayer(RenderLayerParent rendererIn, HumanoidModel modelLeggingsIn, HumanoidModel modelArmorIn, ModelManager modelManagerIn) {
      super(rendererIn);
      this.f_117071_ = modelLeggingsIn;
      this.f_117072_ = modelArmorIn;
      this.f_266073_ = modelManagerIn.m_119428_(Sheets.f_265912_);
   }

   public void m_6494_(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, LivingEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlot.CHEST, packedLightIn, this.m_117078_(EquipmentSlot.CHEST));
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlot.LEGS, packedLightIn, this.m_117078_(EquipmentSlot.LEGS));
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlot.FEET, packedLightIn, this.m_117078_(EquipmentSlot.FEET));
      this.m_117118_(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlot.HEAD, packedLightIn, this.m_117078_(EquipmentSlot.HEAD));
   }

   private void m_117118_(PoseStack matrixStackIn, MultiBufferSource bufferIn, LivingEntity livingEntityIn, EquipmentSlot slotIn, int packedLightIn, HumanoidModel modelIn) {
      ItemStack itemstack = livingEntityIn.m_6844_(slotIn);
      Item var9 = itemstack.m_41720_();
      if (var9 instanceof ArmorItem armoritem) {
         if (armoritem.m_40402_() == slotIn) {
            ((HumanoidModel)this.m_117386_()).m_102872_(modelIn);
            this.m_117125_(modelIn, slotIn);
            boolean flag = this.m_117128_(slotIn);
            ArmorMaterial armormaterial = (ArmorMaterial)armoritem.m_40401_().m_203334_();
            int i = itemstack.m_204117_(ItemTags.f_314020_) ? ARGB32.m_321570_(DyedItemColor.m_322889_(itemstack, -6265536)) : -1;
            Iterator var12 = armormaterial.f_315892_().iterator();

            while(var12.hasNext()) {
               ArmorMaterial.Layer armormaterial$layer = (ArmorMaterial.Layer)var12.next();
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

   }

   protected void m_117125_(HumanoidModel modelIn, EquipmentSlot slotIn) {
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

   private void m_289609_(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, HumanoidModel bipedModelIn, int colorIn, ResourceLocation suffixIn) {
      VertexConsumer vertexconsumer = bufferIn.m_6299_(RenderType.m_110431_(suffixIn));
      bipedModelIn.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, colorIn);
   }

   private void m_289604_(Holder armorMaterialIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, ArmorTrim trimIn, HumanoidModel bipedModelIn, boolean isLegSlot) {
      TextureAtlasSprite textureatlassprite = this.f_266073_.m_118316_(isLegSlot ? trimIn.m_267774_(armorMaterialIn) : trimIn.m_267606_(armorMaterialIn));
      textureatlassprite = TextureUtils.getCustomSprite(textureatlassprite);
      VertexConsumer vertexconsumer = textureatlassprite.m_118381_(bufferIn.m_6299_(Sheets.m_266442_(((TrimPattern)trimIn.m_266429_().m_203334_()).f_290976_())));
      bipedModelIn.m_340227_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
   }

   private void m_289597_(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, HumanoidModel bipedModelIn) {
      bipedModelIn.m_340227_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110484_()), packedLightIn, OverlayTexture.f_118083_);
   }

   private HumanoidModel m_117078_(EquipmentSlot slotIn) {
      return this.m_117128_(slotIn) ? this.f_117071_ : this.f_117072_;
   }

   private boolean m_117128_(EquipmentSlot slotIn) {
      return slotIn == EquipmentSlot.LEGS;
   }
}
