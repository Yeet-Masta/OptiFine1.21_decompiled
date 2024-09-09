package net.minecraft.client.renderer.block.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.ItemOverride.Predicate;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.optifine.Config;
import net.optifine.ItemOverrideCache;

public class ItemOverrides {
   public static final net.minecraft.client.renderer.block.model.ItemOverrides f_111734_ = new net.minecraft.client.renderer.block.model.ItemOverrides();
   public static final float f_265997_ = Float.NEGATIVE_INFINITY;
   private final net.minecraft.client.renderer.block.model.ItemOverrides.BakedOverride[] f_111735_;
   private final net.minecraft.resources.ResourceLocation[] f_173461_;
   private ItemOverrideCache itemOverrideCache;
   public static net.minecraft.resources.ResourceLocation lastModelLocation = null;

   private ItemOverrides() {
      this.f_111735_ = new net.minecraft.client.renderer.block.model.ItemOverrides.BakedOverride[0];
      this.f_173461_ = new net.minecraft.resources.ResourceLocation[0];
   }

   public ItemOverrides(net.minecraft.client.resources.model.ModelBaker modelBakeryIn, BlockModel blockModelIn, List<ItemOverride> itemOverridesIn) {
      this(modelBakeryIn, blockModelIn, itemOverridesIn, modelBakeryIn.getModelTextureGetter());
   }

   public ItemOverrides(
      net.minecraft.client.resources.model.ModelBaker modelBakeryIn,
      UnbakedModel blockModelIn,
      List<ItemOverride> itemOverridesIn,
      Function<net.minecraft.client.resources.model.Material, net.minecraft.client.renderer.texture.TextureAtlasSprite> spriteGetter
   ) {
      this.f_173461_ = (net.minecraft.resources.ResourceLocation[])itemOverridesIn.stream()
         .flatMap(ItemOverride::m_173449_)
         .map(Predicate::m_173459_)
         .distinct()
         .toArray(net.minecraft.resources.ResourceLocation[]::new);
      Object2IntMap<net.minecraft.resources.ResourceLocation> object2intmap = new Object2IntOpenHashMap();

      for (int i = 0; i < this.f_173461_.length; i++) {
         object2intmap.put(this.f_173461_[i], i);
      }

      List<net.minecraft.client.renderer.block.model.ItemOverrides.BakedOverride> list = Lists.newArrayList();

      for (int j = itemOverridesIn.size() - 1; j >= 0; j--) {
         ItemOverride itemoverride = (ItemOverride)itemOverridesIn.get(j);
         net.minecraft.client.resources.model.BakedModel bakedmodel = this.bakeModel(modelBakeryIn, blockModelIn, itemoverride, spriteGetter);
         net.minecraft.client.renderer.block.model.ItemOverrides.PropertyMatcher[] aitemoverrides$propertymatcher = (net.minecraft.client.renderer.block.model.ItemOverrides.PropertyMatcher[])itemoverride.m_173449_()
            .map(checkIn -> {
               int k = object2intmap.getInt(checkIn.m_173459_());
               return new net.minecraft.client.renderer.block.model.ItemOverrides.PropertyMatcher(k, checkIn.m_173460_());
            })
            .toArray(net.minecraft.client.renderer.block.model.ItemOverrides.PropertyMatcher[]::new);
         list.add(new net.minecraft.client.renderer.block.model.ItemOverrides.BakedOverride(aitemoverrides$propertymatcher, bakedmodel));
         net.minecraft.client.renderer.block.model.ItemOverrides.BakedOverride bo = (net.minecraft.client.renderer.block.model.ItemOverrides.BakedOverride)list.get(
            list.size() - 1
         );
         bo.location = itemoverride.m_111718_();
      }

      this.f_111735_ = (net.minecraft.client.renderer.block.model.ItemOverrides.BakedOverride[])list.toArray(
         new net.minecraft.client.renderer.block.model.ItemOverrides.BakedOverride[0]
      );
      if (itemOverridesIn.size() > 65) {
         this.itemOverrideCache = ItemOverrideCache.make(itemOverridesIn);
      }
   }

   @Nullable
   private net.minecraft.client.resources.model.BakedModel bakeModel(
      net.minecraft.client.resources.model.ModelBaker modelBakeryIn,
      UnbakedModel blockModelIn,
      ItemOverride itemOverrideIn,
      Function<net.minecraft.client.resources.model.Material, net.minecraft.client.renderer.texture.TextureAtlasSprite> spriteGetter
   ) {
      UnbakedModel unbakedmodel = modelBakeryIn.m_245361_(itemOverrideIn.m_111718_());
      return Objects.equals(unbakedmodel, blockModelIn) ? null : modelBakeryIn.bake(itemOverrideIn.m_111718_(), BlockModelRotation.X0_Y0, spriteGetter);
   }

   @Nullable
   public net.minecraft.client.resources.model.BakedModel m_173464_(
      net.minecraft.client.resources.model.BakedModel modelIn,
      ItemStack stackIn,
      @Nullable net.minecraft.client.multiplayer.ClientLevel worldIn,
      @Nullable LivingEntity entityIn,
      int seedIn
   ) {
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

                  net.minecraft.client.resources.model.BakedModel modelCached = this.f_111735_[indexCached].f_173481_;
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
            net.minecraft.resources.ResourceLocation resourcelocation = this.f_173461_[j];
            ItemPropertyFunction itempropertyfunction = ItemProperties.m_117829_(stackIn, resourcelocation);
            if (itempropertyfunction != null) {
               afloat[j] = itempropertyfunction.m_141951_(stackIn, worldIn, entityIn, seedIn);
            } else {
               afloat[j] = Float.NEGATIVE_INFINITY;
            }
         }

         for (int ix = 0; ix < this.f_111735_.length; ix++) {
            net.minecraft.client.renderer.block.model.ItemOverrides.BakedOverride itemoverrides$bakedoverride = this.f_111735_[ix];
            if (itemoverrides$bakedoverride.m_173485_(afloat)) {
               net.minecraft.client.resources.model.BakedModel bakedmodel = itemoverrides$bakedoverride.f_173481_;
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

   public ImmutableList<net.minecraft.client.renderer.block.model.ItemOverrides.BakedOverride> getOverrides() {
      return ImmutableList.copyOf(this.f_111735_);
   }

   static class BakedOverride {
      private final net.minecraft.client.renderer.block.model.ItemOverrides.PropertyMatcher[] f_173480_;
      @Nullable
      final net.minecraft.client.resources.model.BakedModel f_173481_;
      private net.minecraft.resources.ResourceLocation location;

      BakedOverride(
         net.minecraft.client.renderer.block.model.ItemOverrides.PropertyMatcher[] matchersIn,
         @Nullable net.minecraft.client.resources.model.BakedModel modelIn
      ) {
         this.f_173480_ = matchersIn;
         this.f_173481_ = modelIn;
      }

      boolean m_173485_(float[] valuesIn) {
         for (net.minecraft.client.renderer.block.model.ItemOverrides.PropertyMatcher itemoverrides$propertymatcher : this.f_173480_) {
            float f = valuesIn[itemoverrides$propertymatcher.f_173487_];
            if (f < itemoverrides$propertymatcher.f_173488_) {
               return false;
            }
         }

         return true;
      }
   }

   static class PropertyMatcher {
      public final int f_173487_;
      public final float f_173488_;

      PropertyMatcher(int indexIn, float valueIn) {
         this.f_173487_ = indexIn;
         this.f_173488_ = valueIn;
      }
   }
}
