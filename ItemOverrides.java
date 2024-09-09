import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_4205_;
import net.minecraft.src.C_4217_;
import net.minecraft.src.C_4463_;
import net.minecraft.src.C_4467_;
import net.minecraft.src.C_4529_;
import net.minecraft.src.C_4542_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_4217_.C_141725_;
import net.optifine.Config;
import net.optifine.ItemOverrideCache;

public class ItemOverrides {
   public static final ItemOverrides a = new ItemOverrides();
   public static final float b = Float.NEGATIVE_INFINITY;
   private final ItemOverrides.a[] c;
   private final ResourceLocation[] d;
   private ItemOverrideCache itemOverrideCache;
   public static ResourceLocation lastModelLocation = null;

   private ItemOverrides() {
      this.c = new ItemOverrides.a[0];
      this.d = new ResourceLocation[0];
   }

   public ItemOverrides(ModelBaker modelBakeryIn, C_4205_ blockModelIn, List<C_4217_> itemOverridesIn) {
      this(modelBakeryIn, blockModelIn, itemOverridesIn, modelBakeryIn.getModelTextureGetter());
   }

   public ItemOverrides(ModelBaker modelBakeryIn, C_4542_ blockModelIn, List<C_4217_> itemOverridesIn, Function<Material, TextureAtlasSprite> spriteGetter) {
      this.d = (ResourceLocation[])itemOverridesIn.stream().flatMap(C_4217_::m_173449_).map(C_141725_::a).distinct().toArray(ResourceLocation[]::new);
      Object2IntMap<ResourceLocation> object2intmap = new Object2IntOpenHashMap();

      for (int i = 0; i < this.d.length; i++) {
         object2intmap.put(this.d[i], i);
      }

      List<ItemOverrides.a> list = Lists.newArrayList();

      for (int j = itemOverridesIn.size() - 1; j >= 0; j--) {
         C_4217_ itemoverride = (C_4217_)itemOverridesIn.get(j);
         BakedModel bakedmodel = this.bakeModel(modelBakeryIn, blockModelIn, itemoverride, spriteGetter);
         ItemOverrides.b[] aitemoverrides$propertymatcher = (ItemOverrides.b[])itemoverride.m_173449_().map(checkIn -> {
            int k = object2intmap.getInt(checkIn.a());
            return new ItemOverrides.b(k, checkIn.m_173460_());
         }).toArray(ItemOverrides.b[]::new);
         list.add(new ItemOverrides.a(aitemoverrides$propertymatcher, bakedmodel));
         ItemOverrides.a bo = (ItemOverrides.a)list.get(list.size() - 1);
         bo.location = itemoverride.a();
      }

      this.c = (ItemOverrides.a[])list.toArray(new ItemOverrides.a[0]);
      if (itemOverridesIn.size() > 65) {
         this.itemOverrideCache = ItemOverrideCache.make(itemOverridesIn);
      }
   }

   @Nullable
   private BakedModel bakeModel(ModelBaker modelBakeryIn, C_4542_ blockModelIn, C_4217_ itemOverrideIn, Function<Material, TextureAtlasSprite> spriteGetter) {
      C_4542_ unbakedmodel = modelBakeryIn.a(itemOverrideIn.a());
      return Objects.equals(unbakedmodel, blockModelIn) ? null : modelBakeryIn.bake(itemOverrideIn.a(), C_4529_.X0_Y0, spriteGetter);
   }

   @Nullable
   public BakedModel a(BakedModel modelIn, C_1391_ stackIn, @Nullable ClientLevel worldIn, @Nullable C_524_ entityIn, int seedIn) {
      boolean customItems = Config.isCustomItems();
      if (customItems) {
         lastModelLocation = null;
      }

      if (this.c.length != 0) {
         if (this.itemOverrideCache != null) {
            Integer modelIndex = this.itemOverrideCache.getModelIndex(stackIn, worldIn, entityIn);
            if (modelIndex != null) {
               int indexCached = modelIndex;
               if (indexCached >= 0 && indexCached < this.c.length) {
                  if (customItems) {
                     lastModelLocation = this.c[indexCached].location;
                  }

                  BakedModel modelCached = this.c[indexCached].b;
                  if (modelCached != null) {
                     return modelCached;
                  }
               }

               return modelIn;
            }
         }

         int i = this.d.length;
         float[] afloat = new float[i];

         for (int j = 0; j < i; j++) {
            ResourceLocation resourcelocation = this.d[j];
            C_4467_ itempropertyfunction = C_4463_.a(stackIn, resourcelocation);
            if (itempropertyfunction != null) {
               afloat[j] = itempropertyfunction.call(stackIn, worldIn, entityIn, seedIn);
            } else {
               afloat[j] = Float.NEGATIVE_INFINITY;
            }
         }

         for (int ix = 0; ix < this.c.length; ix++) {
            ItemOverrides.a itemoverrides$bakedoverride = this.c[ix];
            if (itemoverrides$bakedoverride.a(afloat)) {
               BakedModel bakedmodel = itemoverrides$bakedoverride.b;
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

   public ImmutableList<ItemOverrides.a> getOverrides() {
      return ImmutableList.copyOf(this.c);
   }

   static class a {
      private final ItemOverrides.b[] a;
      @Nullable
      final BakedModel b;
      private ResourceLocation location;

      a(ItemOverrides.b[] matchersIn, @Nullable BakedModel modelIn) {
         this.a = matchersIn;
         this.b = modelIn;
      }

      boolean a(float[] valuesIn) {
         for (ItemOverrides.b itemoverrides$propertymatcher : this.a) {
            float f = valuesIn[itemoverrides$propertymatcher.a];
            if (f < itemoverrides$propertymatcher.b) {
               return false;
            }
         }

         return true;
      }
   }

   static class b {
      public final int a;
      public final float b;

      b(int indexIn, float valueIn) {
         this.a = indexIn;
         this.b = valueIn;
      }
   }
}
