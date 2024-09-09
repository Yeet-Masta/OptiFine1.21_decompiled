import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.src.C_1325_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_313461_;
import net.minecraft.src.C_313470_;
import net.minecraft.src.C_313543_;
import net.minecraft.src.C_313616_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_4940_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5028_;
import net.minecraft.src.C_5031_;
import net.minecraft.src.C_213466_.C_254607_;
import net.minecraft.src.C_313470_.C_313535_;
import net.minecraft.src.C_4996_.C_4997_;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import org.slf4j.Logger;

public abstract class BlockEntity extends CapabilityProvider<BlockEntity> implements IForgeBlockEntity {
   private static final Logger d = LogUtils.getLogger();
   private final C_1992_<?> e;
   @Nullable
   protected C_1596_ n;
   protected final C_4675_ o;
   protected boolean p;
   private BlockState f;
   private C_313470_ g = C_313470_.f_314291_;
   private C_4917_ customPersistentData;
   public C_4917_ nbtTag;
   public long nbtTagUpdateMs = 0L;

   public BlockEntity(C_1992_<?> typeIn, C_4675_ posIn, BlockState stateIn) {
      super(BlockEntity.class);
      this.e = typeIn;
      this.o = posIn.m_7949_();
      this.f = stateIn;
      this.gatherCapabilities();
   }

   public static C_4675_ b(C_4917_ tagIn) {
      return new C_4675_(tagIn.m_128451_("x"), tagIn.m_128451_("y"), tagIn.m_128451_("z"));
   }

   @Nullable
   public C_1596_ i() {
      return this.n;
   }

   public void a(C_1596_ worldIn) {
      this.n = worldIn;
   }

   public boolean m() {
      return this.n != null;
   }

   protected void a(C_4917_ tagIn, C_254607_ providerIn) {
      if (tagIn.m_128441_("ForgeData")) {
         this.customPersistentData = tagIn.m_128469_("ForgeData");
      }

      if (this.getCapabilities() != null && tagIn.m_128441_("ForgeCaps")) {
         this.deserializeCaps(tagIn.m_128469_("ForgeCaps"));
      }
   }

   public final void c(C_4917_ tagIn, C_254607_ providerIn) {
      this.a(tagIn, providerIn);
      BlockEntity.a.a
         .parse(providerIn.m_318927_(C_4940_.f_128958_), tagIn)
         .resultOrPartial(p_318380_0_ -> d.warn("Failed to load components: {}", p_318380_0_))
         .ifPresent(p_318382_1_ -> this.g = p_318382_1_);
   }

   public final void d(C_4917_ tagIn, C_254607_ providerIn) {
      this.a(tagIn, providerIn);
   }

   protected void b(C_4917_ tagIn, C_254607_ providerIn) {
      if (this.customPersistentData != null) {
         tagIn.m_128365_("ForgeData", this.customPersistentData.m_6426_());
      }

      if (this.getCapabilities() != null) {
         tagIn.m_128365_("ForgeCaps", this.serializeCaps());
      }
   }

   public final C_4917_ b(C_254607_ providerIn) {
      C_4917_ compoundtag = this.d(providerIn);
      this.d(compoundtag);
      return compoundtag;
   }

   public final C_4917_ c(C_254607_ providerIn) {
      C_4917_ compoundtag = this.d(providerIn);
      this.c(compoundtag);
      return compoundtag;
   }

   public final C_4917_ d(C_254607_ providerIn) {
      C_4917_ compoundtag = new C_4917_();
      this.b(compoundtag, providerIn);
      BlockEntity.a.a
         .encodeStart(providerIn.m_318927_(C_4940_.f_128958_), this.g)
         .resultOrPartial(p_318379_0_ -> d.warn("Failed to save components: {}", p_318379_0_))
         .ifPresent(p_318384_1_ -> compoundtag.m_128391_((C_4917_)p_318384_1_));
      return compoundtag;
   }

   public final C_4917_ e(C_254607_ providerIn) {
      C_4917_ compoundtag = new C_4917_();
      this.b(compoundtag, providerIn);
      return compoundtag;
   }

   public final C_4917_ f(C_254607_ providerIn) {
      C_4917_ compoundtag = this.e(providerIn);
      this.d(compoundtag);
      return compoundtag;
   }

   private void c(C_4917_ tagIn) {
      ResourceLocation resourcelocation = C_1992_.a(this.r());
      if (resourcelocation == null) {
         throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
      } else {
         tagIn.m_128359_("id", resourcelocation.toString());
      }
   }

   public static void a(C_4917_ tagIn, C_1992_<?> typeIn) {
      tagIn.m_128359_("id", C_1992_.a(typeIn).toString());
   }

   public void a(C_1391_ stackIn, C_254607_ providerIn) {
      C_4917_ compoundtag = this.e(providerIn);
      this.a(compoundtag);
      C_1325_.m_186338_(stackIn, this.r(), compoundtag);
      stackIn.m_323474_(this.s());
   }

   private void d(C_4917_ tagIn) {
      this.c(tagIn);
      tagIn.m_128405_("x", this.o.m_123341_());
      tagIn.m_128405_("y", this.o.m_123342_());
      tagIn.m_128405_("z", this.o.m_123343_());
   }

   @Nullable
   public static BlockEntity a(C_4675_ posIn, BlockState stateIn, C_4917_ tagIn, C_254607_ providerIn) {
      String s = tagIn.m_128461_("id");
      ResourceLocation resourcelocation = ResourceLocation.c(s);
      if (resourcelocation == null) {
         d.error("Block entity has invalid type: {}", s);
         return null;
      } else {
         return (BlockEntity)C_256712_.f_257049_.b(resourcelocation).map(p_155236_3_ -> {
            try {
               return p_155236_3_.a(posIn, stateIn);
            } catch (Throwable var5x) {
               d.error("Failed to create block entity {}", s, var5x);
               return null;
            }
         }).map(p_318381_3_ -> {
            try {
               p_318381_3_.c(tagIn, providerIn);
               return p_318381_3_;
            } catch (Throwable var5x) {
               d.error("Failed to load data for block entity {}", s, var5x);
               return null;
            }
         }).orElseGet(() -> {
            d.warn("Skipping BlockEntity with id {}", s);
            return null;
         });
      }
   }

   public void e() {
      if (this.n != null) {
         a(this.n, this.o, this.f);
      }

      this.nbtTag = null;
   }

   protected static void a(C_1596_ worldIn, C_4675_ posIn, BlockState stateIn) {
      worldIn.m_151543_(posIn);
      if (!stateIn.m_60795_()) {
         worldIn.m_46717_(posIn, stateIn.m_60734_());
      }
   }

   public C_4675_ aD_() {
      return this.o;
   }

   public BlockState n() {
      return this.f;
   }

   @Nullable
   public C_5028_<C_5031_> az_() {
      return null;
   }

   public C_4917_ a(C_254607_ providerIn) {
      return new C_4917_();
   }

   public boolean o() {
      return this.p;
   }

   public void aA_() {
      this.p = true;
      this.invalidateCaps();
      this.requestModelDataUpdate();
   }

   @Override
   public void onChunkUnloaded() {
      this.invalidateCaps();
   }

   @Override
   public C_4917_ getPersistentData() {
      if (this.customPersistentData == null) {
         this.customPersistentData = new C_4917_();
      }

      return this.customPersistentData;
   }

   public void p() {
      this.p = false;
   }

   public boolean a_(int id, int type) {
      return false;
   }

   public void a(C_4909_ reportCategory) {
      reportCategory.m_128165_("Name", () -> C_256712_.f_257049_.b(this.r()) + " // " + this.getClass().getCanonicalName());
      if (this.n != null) {
         C_4909_.a(reportCategory, this.n, this.o, this.n());
         C_4909_.a(reportCategory, this.n, this.o, this.n.a_(this.o));
      }
   }

   public boolean q() {
      return false;
   }

   public C_1992_<?> r() {
      return this.e;
   }

   @Deprecated
   public void b(BlockState stateIn) {
      this.f = stateIn;
   }

   protected void a(BlockEntity.b inputIn) {
   }

   public final void a(C_1391_ stackIn) {
      this.a(stackIn.m_322741_(), stackIn.m_324277_());
   }

   public final void a(C_313470_ mapIn, C_313461_ patchIn) {
      final Set<C_313543_<?>> set = new HashSet();
      set.add(C_313616_.f_316520_);
      final C_313470_ datacomponentmap = PatchedDataComponentMap.a(mapIn, patchIn);
      this.a(new BlockEntity.b() {
         @Nullable
         @Override
         public <T> T a(C_313543_<T> typeIn) {
            set.add(typeIn);
            return (T)datacomponentmap.m_318834_(typeIn);
         }

         @Override
         public <T> T a(C_313543_<? extends T> typeIn, T defIn) {
            set.add(typeIn);
            return (T)datacomponentmap.m_322806_(typeIn, defIn);
         }
      });
      C_313461_ datacomponentpatch = patchIn.m_318691_(set::contains);
      this.g = datacomponentpatch.m_324808_().f_314173_();
   }

   protected void a(C_313535_ builderIn) {
   }

   @Deprecated
   public void a(C_4917_ tagIn) {
   }

   public final C_313470_ s() {
      C_313535_ datacomponentmap$builder = C_313470_.m_323371_();
      datacomponentmap$builder.m_321974_(this.g);
      this.a(datacomponentmap$builder);
      return datacomponentmap$builder.m_318826_();
   }

   public C_313470_ t() {
      return this.g;
   }

   public void a(C_313470_ mapIn) {
      this.g = mapIn;
   }

   @Nullable
   public static C_4996_ a(String nameIn, C_254607_ providerIn) {
      try {
         return C_4997_.m_130691_(nameIn, providerIn);
      } catch (Exception var3) {
         d.warn("Failed to parse custom name from string '{}', discarding", nameIn, var3);
         return null;
      }
   }

   static class a {
      public static final Codec<C_313470_> a = C_313470_.f_315283_.optionalFieldOf("components", C_313470_.f_314291_).codec();

      private a() {
      }
   }

   protected interface b {
      @Nullable
      <T> T a(C_313543_<T> var1);

      <T> T a(C_313543_<? extends T> var1, T var2);
   }
}
