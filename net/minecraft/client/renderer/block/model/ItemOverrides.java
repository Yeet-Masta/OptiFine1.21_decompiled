package net.minecraft.client.renderer.block.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverride.Predicate;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.optifine.Config;
import net.optifine.ItemOverrideCache;

public class ItemOverrides {
   public static ItemOverrides f_111734_ = new ItemOverrides();
   public static float f_265997_;
   private ItemOverrides.BakedOverride[] f_111735_;
   private ResourceLocation[] f_173461_;
   private ItemOverrideCache itemOverrideCache;
   public static ResourceLocation lastModelLocation = null;

   private ItemOverrides() {
      this.f_111735_ = new ItemOverrides.BakedOverride[0];
      this.f_173461_ = new ResourceLocation[0];
   }

   public ItemOverrides(ModelBaker modelBakeryIn, BlockModel blockModelIn, List<ItemOverride> itemOverridesIn) {
      this(modelBakeryIn, blockModelIn, itemOverridesIn, modelBakeryIn.getModelTextureGetter());
   }

   public ItemOverrides(
      ModelBaker modelBakeryIn, UnbakedModel blockModelIn, List<ItemOverride> itemOverridesIn, Function<Material, TextureAtlasSprite> spriteGetter
   ) {
      this.f_173461_ = (ResourceLocation[])itemOverridesIn.stream()
         .flatMap(ItemOverride::m_173449_)
         .map(Predicate::m_173459_)
         .distinct()
         .toArray(ResourceLocation[]::new);
      Object2IntMap<ResourceLocation> object2intmap = new Object2IntOpenHashMap();

      for (int i = 0; i < this.f_173461_.length; i++) {
         object2intmap.put(this.f_173461_[i], i);
      }

      List<ItemOverrides.BakedOverride> list = Lists.newArrayList();

      for (int j = itemOverridesIn.size() - 1; j >= 0; j--) {
         ItemOverride itemoverride = (ItemOverride)itemOverridesIn.get(j);
         BakedModel bakedmodel = this.bakeModel(modelBakeryIn, blockModelIn, itemoverride, spriteGetter);
         ItemOverrides.PropertyMatcher[] aitemoverrides$propertymatcher = (ItemOverrides.PropertyMatcher[])itemoverride.m_173449_().map(checkIn -> {
            int k = object2intmap.getInt(checkIn.m_173459_());
            return new ItemOverrides.PropertyMatcher(k, checkIn.m_173460_());
         }).toArray(ItemOverrides.PropertyMatcher[]::new);
         list.add(new ItemOverrides.BakedOverride(aitemoverrides$propertymatcher, bakedmodel));
         ItemOverrides.BakedOverride bo = (ItemOverrides.BakedOverride)list.get(list.size() - 1);
         bo.location = itemoverride.m_111718_();
      }

      this.f_111735_ = (ItemOverrides.BakedOverride[])list.toArray(new ItemOverrides.BakedOverride[0]);
      if (itemOverridesIn.size() > 65) {
         this.itemOverrideCache = ItemOverrideCache.make(itemOverridesIn);
      }
   }

   @Nullable
   private BakedModel bakeModel(
      ModelBaker modelBakeryIn, UnbakedModel blockModelIn, ItemOverride itemOverrideIn, Function<Material, TextureAtlasSprite> spriteGetter
   ) {
      UnbakedModel unbakedmodel = modelBakeryIn.m_245361_(itemOverrideIn.m_111718_());
      return Objects.equals(unbakedmodel, blockModelIn) ? null : modelBakeryIn.m_213604_(itemOverrideIn.m_111718_(), BlockModelRotation.X0_Y0, spriteGetter);
   }

   @Nullable
   public BakedModel m_173464_(BakedModel modelIn, ItemStack stackIn, @Nullable ClientLevel worldIn, @Nullable LivingEntity entityIn, int seedIn) {
      boolean customItems = Config.isCustomItems();
      if (customItems) {
         lastModelLocation = null;
      }

      if (this.f_111735_.length != 0) {
         if (this.itemOverrideCache != null) {
            Integer modelIndex = this.itemOverrideCache.getModelIndex(stackIn, worldIn, entityIn);
            if (modelIndex != null) {
               int indexCached = modelIndex;
               if (indexCached >= 0 && indexCached < this.f_111735_.length) {
                  if (customItems) {
                     lastModelLocation = this.f_111735_[indexCached].location;
                  }

                  BakedModel modelCached = this.f_111735_[indexCached].f_173481_;
                  if (modelCached != null) {
                     return modelCached;
                  }
               }

               return modelIn;
            }
         }

         int i = this.f_173461_.length;
         float[] afloat = new float[i];

         for (int j = 0; j < i; j++) {
            ResourceLocation resourcelocation = this.f_173461_[j];
            ItemPropertyFunction itempropertyfunction = ItemProperties.m_117829_(stackIn, resourcelocation);
            if (itempropertyfunction != null) {
               afloat[j] = itempropertyfunction.m_141951_(stackIn, worldIn, entityIn, seedIn);
            } else {
               afloat[j] = Float.NEGATIVE_INFINITY;
            }
         }

         for (int ix = 0; ix < this.f_111735_.length; ix++) {
            ItemOverrides.BakedOverride itemoverrides$bakedoverride = this.f_111735_[ix];
            if (itemoverrides$bakedoverride.m_173485_(afloat)) {
               BakedModel bakedmodel = itemoverrides$bakedoverride.f_173481_;
               if (customItems) {
                  lastModelLocation = itemoverrides$bakedoverride.location;
               }

               if (this.itemOverrideCache != null) {
                  this.itemOverrideCache.putModelIndex(stackIn, worldIn, entityIn, ix);
               }

               if (bakedmodel == null) {
                  return modelIn;
               }

               return bakedmodel;
            }
         }
      }

      return modelIn;
   }

   public ImmutableList<ItemOverrides.BakedOverride> getOverrides() {
      return ImmutableList.copyOf(this.f_111735_);
   }

   static class BakedOverride {
      private ItemOverrides.PropertyMatcher[] f_173480_;
      @Nullable
      BakedModel f_173481_;
      private ResourceLocation location;

      BakedOverride(ItemOverrides.PropertyMatcher[] matchersIn, @Nullable BakedModel modelIn) {
         this.f_173480_ = matchersIn;
         this.f_173481_ = modelIn;
      }

      boolean m_173485_(float[] valuesIn) {
         for (ItemOverrides.PropertyMatcher itemoverrides$propertymatcher : this.f_173480_) {
            float f = valuesIn[itemoverrides$propertymatcher.f_173487_];
            if (f < itemoverrides$propertymatcher.f_173488_) {
               return false;
            }
         }

         return true;
      }
   }

   static class PropertyMatcher {
      public int f_173487_;
      public float f_173488_;

      PropertyMatcher(int indexIn, float valueIn) {
         this.f_173487_ = indexIn;
         this.f_173488_ = valueIn;
      }
   }
}
