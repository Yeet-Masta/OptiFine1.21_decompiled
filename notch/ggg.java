package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.src.C_4217_.C_141725_;
import net.optifine.Config;
import net.optifine.ItemOverrideCache;

public class C_4219_ {
   public static final C_4219_ f_111734_ = new C_4219_();
   public static final float f_265997_ = Float.NEGATIVE_INFINITY;
   private final C_4219_.C_141726_[] f_111735_;
   private final C_5265_[] f_173461_;
   private ItemOverrideCache itemOverrideCache;
   public static C_5265_ lastModelLocation = null;

   private C_4219_() {
      this.f_111735_ = new C_4219_.C_141726_[0];
      this.f_173461_ = new C_5265_[0];
   }

   public C_4219_(C_243643_ modelBakeryIn, C_4205_ blockModelIn, List<C_4217_> itemOverridesIn) {
      this(modelBakeryIn, blockModelIn, itemOverridesIn, modelBakeryIn.getModelTextureGetter());
   }

   public C_4219_(C_243643_ modelBakeryIn, C_4542_ blockModelIn, List<C_4217_> itemOverridesIn, Function<C_4531_, C_4486_> spriteGetter) {
      this.f_173461_ = (C_5265_[])itemOverridesIn.stream().flatMap(C_4217_::m_173449_).map(C_141725_::m_173459_).distinct().toArray(C_5265_[]::new);
      Object2IntMap<C_5265_> object2intmap = new Object2IntOpenHashMap();

      for (int i = 0; i < this.f_173461_.length; i++) {
         object2intmap.put(this.f_173461_[i], i);
      }

      List<C_4219_.C_141726_> list = Lists.newArrayList();

      for (int j = itemOverridesIn.size() - 1; j >= 0; j--) {
         C_4217_ itemoverride = (C_4217_)itemOverridesIn.get(j);
         C_4528_ bakedmodel = this.bakeModel(modelBakeryIn, blockModelIn, itemoverride, spriteGetter);
         C_4219_.C_141727_[] aitemoverrides$propertymatcher = (C_4219_.C_141727_[])itemoverride.m_173449_().map(checkIn -> {
            int k = object2intmap.getInt(checkIn.m_173459_());
            return new C_4219_.C_141727_(k, checkIn.m_173460_());
         }).toArray(C_4219_.C_141727_[]::new);
         list.add(new C_4219_.C_141726_(aitemoverrides$propertymatcher, bakedmodel));
         C_4219_.C_141726_ bo = (C_4219_.C_141726_)list.get(list.size() - 1);
         bo.location = itemoverride.m_111718_();
      }

      this.f_111735_ = (C_4219_.C_141726_[])list.toArray(new C_4219_.C_141726_[0]);
      if (itemOverridesIn.size() > 65) {
         this.itemOverrideCache = ItemOverrideCache.make(itemOverridesIn);
      }
   }

   @Nullable
   private C_4528_ bakeModel(C_243643_ modelBakeryIn, C_4542_ blockModelIn, C_4217_ itemOverrideIn, Function<C_4531_, C_4486_> spriteGetter) {
      C_4542_ unbakedmodel = modelBakeryIn.m_245361_(itemOverrideIn.m_111718_());
      return Objects.equals(unbakedmodel, blockModelIn) ? null : modelBakeryIn.bake(itemOverrideIn.m_111718_(), C_4529_.X0_Y0, spriteGetter);
   }

   @Nullable
   public C_4528_ m_173464_(C_4528_ modelIn, C_1391_ stackIn, @Nullable C_3899_ worldIn, @Nullable C_524_ entityIn, int seedIn) {
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

                  C_4528_ modelCached = this.f_111735_[indexCached].f_173481_;
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
            C_5265_ resourcelocation = this.f_173461_[j];
            C_4467_ itempropertyfunction = C_4463_.m_117829_(stackIn, resourcelocation);
            if (itempropertyfunction != null) {
               afloat[j] = itempropertyfunction.m_141951_(stackIn, worldIn, entityIn, seedIn);
            } else {
               afloat[j] = Float.NEGATIVE_INFINITY;
            }
         }

         for (int ix = 0; ix < this.f_111735_.length; ix++) {
            C_4219_.C_141726_ itemoverrides$bakedoverride = this.f_111735_[ix];
            if (itemoverrides$bakedoverride.m_173485_(afloat)) {
               C_4528_ bakedmodel = itemoverrides$bakedoverride.f_173481_;
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

   public ImmutableList<C_4219_.C_141726_> getOverrides() {
      return ImmutableList.copyOf(this.f_111735_);
   }

   static class C_141726_ {
      private final C_4219_.C_141727_[] f_173480_;
      @Nullable
      final C_4528_ f_173481_;
      private C_5265_ location;

      C_141726_(C_4219_.C_141727_[] matchersIn, @Nullable C_4528_ modelIn) {
         this.f_173480_ = matchersIn;
         this.f_173481_ = modelIn;
      }

      boolean m_173485_(float[] valuesIn) {
         for (C_4219_.C_141727_ itemoverrides$propertymatcher : this.f_173480_) {
            float f = valuesIn[itemoverrides$propertymatcher.f_173487_];
            if (f < itemoverrides$propertymatcher.f_173488_) {
               return false;
            }
         }

         return true;
      }
   }

   static class C_141727_ {
      public final int f_173487_;
      public final float f_173488_;

      C_141727_(int indexIn, float valueIn) {
         this.f_173487_ = indexIn;
         this.f_173488_ = valueIn;
      }
   }
}
