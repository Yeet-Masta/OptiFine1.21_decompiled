package net.minecraft.src;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.src.C_4996_.C_4997_;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import org.slf4j.Logger;

public abstract class C_1991_ extends CapabilityProvider implements IForgeBlockEntity {
   private static final Logger f_58854_ = LogUtils.getLogger();
   private final C_1992_ f_58855_;
   @Nullable
   protected C_1596_ f_58857_;
   protected final C_4675_ f_58858_;
   protected boolean f_58859_;
   private C_2064_ f_58856_;
   private C_313470_ f_314183_;
   private C_4917_ customPersistentData;
   public C_4917_ nbtTag;
   public long nbtTagUpdateMs;

   public C_1991_(C_1992_ typeIn, C_4675_ posIn, C_2064_ stateIn) {
      super(C_1991_.class);
      this.f_314183_ = C_313470_.f_314291_;
      this.nbtTagUpdateMs = 0L;
      this.f_58855_ = typeIn;
      this.f_58858_ = posIn.m_7949_();
      this.f_58856_ = stateIn;
      this.gatherCapabilities();
   }

   public static C_4675_ m_187472_(C_4917_ tagIn) {
      return new C_4675_(tagIn.m_128451_("x"), tagIn.m_128451_("y"), tagIn.m_128451_("z"));
   }

   @Nullable
   public C_1596_ m_58904_() {
      return this.f_58857_;
   }

   public void m_142339_(C_1596_ worldIn) {
      this.f_58857_ = worldIn;
   }

   public boolean m_58898_() {
      return this.f_58857_ != null;
   }

   protected void m_318667_(C_4917_ tagIn, C_213466_.C_254607_ providerIn) {
      if (tagIn.m_128441_("ForgeData")) {
         this.customPersistentData = tagIn.m_128469_("ForgeData");
      }

      if (this.getCapabilities() != null && tagIn.m_128441_("ForgeCaps")) {
         this.deserializeCaps(tagIn.m_128469_("ForgeCaps"));
      }

   }

   public final void m_320998_(C_4917_ tagIn, C_213466_.C_254607_ providerIn) {
      this.m_318667_(tagIn, providerIn);
      C_1991_.C_313263_.f_316981_.parse(providerIn.m_318927_(C_4940_.f_128958_), tagIn).resultOrPartial((p_318380_0_) -> {
         f_58854_.warn("Failed to load components: {}", p_318380_0_);
      }).ifPresent((p_318382_1_) -> {
         this.f_314183_ = p_318382_1_;
      });
   }

   public final void m_324273_(C_4917_ tagIn, C_213466_.C_254607_ providerIn) {
      this.m_318667_(tagIn, providerIn);
   }

   protected void m_183515_(C_4917_ tagIn, C_213466_.C_254607_ providerIn) {
      if (this.customPersistentData != null) {
         tagIn.m_128365_("ForgeData", this.customPersistentData.m_6426_());
      }

      if (this.getCapabilities() != null) {
         tagIn.m_128365_("ForgeCaps", this.serializeCaps());
      }

   }

   public final C_4917_ m_187480_(C_213466_.C_254607_ providerIn) {
      C_4917_ compoundtag = this.m_187482_(providerIn);
      this.m_187478_(compoundtag);
      return compoundtag;
   }

   public final C_4917_ m_187481_(C_213466_.C_254607_ providerIn) {
      C_4917_ compoundtag = this.m_187482_(providerIn);
      this.m_187474_(compoundtag);
      return compoundtag;
   }

   public final C_4917_ m_187482_(C_213466_.C_254607_ providerIn) {
      C_4917_ compoundtag = new C_4917_();
      this.m_183515_(compoundtag, providerIn);
      C_1991_.C_313263_.f_316981_.encodeStart(providerIn.m_318927_(C_4940_.f_128958_), this.f_314183_).resultOrPartial((p_318379_0_) -> {
         f_58854_.warn("Failed to save components: {}", p_318379_0_);
      }).ifPresent((p_318384_1_) -> {
         compoundtag.m_128391_((C_4917_)p_318384_1_);
      });
      return compoundtag;
   }

   public final C_4917_ m_320696_(C_213466_.C_254607_ providerIn) {
      C_4917_ compoundtag = new C_4917_();
      this.m_183515_(compoundtag, providerIn);
      return compoundtag;
   }

   public final C_4917_ m_319785_(C_213466_.C_254607_ providerIn) {
      C_4917_ compoundtag = this.m_320696_(providerIn);
      this.m_187478_(compoundtag);
      return compoundtag;
   }

   private void m_187474_(C_4917_ tagIn) {
      C_5265_ resourcelocation = C_1992_.m_58954_(this.m_58903_());
      if (resourcelocation == null) {
         throw new RuntimeException(String.valueOf(this.getClass()) + " is missing a mapping! This is a bug!");
      } else {
         tagIn.m_128359_("id", resourcelocation.toString());
      }
   }

   public static void m_187468_(C_4917_ tagIn, C_1992_ typeIn) {
      tagIn.m_128359_("id", C_1992_.m_58954_(typeIn).toString());
   }

   public void m_187476_(C_1391_ stackIn, C_213466_.C_254607_ providerIn) {
      C_4917_ compoundtag = this.m_320696_(providerIn);
      this.m_318942_(compoundtag);
      C_1325_.m_186338_(stackIn, this.m_58903_(), compoundtag);
      stackIn.m_323474_(this.m_321843_());
   }

   private void m_187478_(C_4917_ tagIn) {
      this.m_187474_(tagIn);
      tagIn.m_128405_("x", this.f_58858_.u());
      tagIn.m_128405_("y", this.f_58858_.v());
      tagIn.m_128405_("z", this.f_58858_.w());
   }

   @Nullable
   public static C_1991_ m_155241_(C_4675_ posIn, C_2064_ stateIn, C_4917_ tagIn, C_213466_.C_254607_ providerIn) {
      String s = tagIn.m_128461_("id");
      C_5265_ resourcelocation = C_5265_.m_135820_(s);
      if (resourcelocation == null) {
         f_58854_.error("Block entity has invalid type: {}", s);
         return null;
      } else {
         return (C_1991_)C_256712_.f_257049_.m_6612_(resourcelocation).map((p_155236_3_) -> {
            try {
               return p_155236_3_.m_155264_(posIn, stateIn);
            } catch (Throwable var5) {
               f_58854_.error("Failed to create block entity {}", s, var5);
               return null;
            }
         }).map((p_318381_3_) -> {
            try {
               p_318381_3_.m_320998_(tagIn, providerIn);
               return p_318381_3_;
            } catch (Throwable var5) {
               f_58854_.error("Failed to load data for block entity {}", s, var5);
               return null;
            }
         }).orElseGet(() -> {
            f_58854_.warn("Skipping BlockEntity with id {}", s);
            return null;
         });
      }
   }

   public void m_6596_() {
      if (this.f_58857_ != null) {
         m_155232_(this.f_58857_, this.f_58858_, this.f_58856_);
      }

      this.nbtTag = null;
   }

   protected static void m_155232_(C_1596_ worldIn, C_4675_ posIn, C_2064_ stateIn) {
      worldIn.m_151543_(posIn);
      if (!stateIn.m_60795_()) {
         worldIn.m_46717_(posIn, stateIn.m_60734_());
      }

   }

   public C_4675_ m_58899_() {
      return this.f_58858_;
   }

   public C_2064_ m_58900_() {
      return this.f_58856_;
   }

   @Nullable
   public C_5028_ m_58483_() {
      return null;
   }

   public C_4917_ m_5995_(C_213466_.C_254607_ providerIn) {
      return new C_4917_();
   }

   public boolean m_58901_() {
      return this.f_58859_;
   }

   public void m_7651_() {
      this.f_58859_ = true;
      this.invalidateCaps();
      this.requestModelDataUpdate();
   }

   public void onChunkUnloaded() {
      this.invalidateCaps();
   }

   public C_4917_ getPersistentData() {
      if (this.customPersistentData == null) {
         this.customPersistentData = new C_4917_();
      }

      return this.customPersistentData;
   }

   public void m_6339_() {
      this.f_58859_ = false;
   }

   public boolean m_7531_(int id, int type) {
      return false;
   }

   public void m_58886_(C_4909_ reportCategory) {
      reportCategory.m_128165_("Name", () -> {
         String var10000 = String.valueOf(C_256712_.f_257049_.m_7981_(this.m_58903_()));
         return var10000 + " // " + this.getClass().getCanonicalName();
      });
      if (this.f_58857_ != null) {
         C_4909_.m_178950_(reportCategory, this.f_58857_, this.f_58858_, this.m_58900_());
         C_4909_.m_178950_(reportCategory, this.f_58857_, this.f_58858_, this.f_58857_.m_8055_(this.f_58858_));
      }

   }

   public boolean m_6326_() {
      return false;
   }

   public C_1992_ m_58903_() {
      return this.f_58855_;
   }

   /** @deprecated */
   @Deprecated
   public void m_155250_(C_2064_ stateIn) {
      this.f_58856_ = stateIn;
   }

   protected void m_318741_(C_313341_ inputIn) {
   }

   public final void m_322533_(C_1391_ stackIn) {
      this.m_322221_(stackIn.m_322741_(), stackIn.m_324277_());
   }

   public final void m_322221_(C_313470_ mapIn, C_313461_ patchIn) {
      final Set set = new HashSet();
      set.add(C_313616_.f_316520_);
      final C_313470_ datacomponentmap = C_313555_.m_322493_(mapIn, patchIn);
      this.m_318741_(new C_313341_(this) {
         @Nullable
         public Object m_319293_(C_313543_ typeIn) {
            set.add(typeIn);
            return datacomponentmap.m_318834_(typeIn);
         }

         public Object m_319031_(C_313543_ typeIn, Object defIn) {
            set.add(typeIn);
            return datacomponentmap.m_322806_(typeIn, defIn);
         }
      });
      Objects.requireNonNull(set);
      C_313461_ datacomponentpatch = patchIn.m_318691_(set::contains);
      this.f_314183_ = datacomponentpatch.m_324808_().f_314173_();
   }

   protected void m_318837_(C_313470_.C_313535_ builderIn) {
   }

   /** @deprecated */
   @Deprecated
   public void m_318942_(C_4917_ tagIn) {
   }

   public final C_313470_ m_321843_() {
      C_313470_.C_313535_ datacomponentmap$builder = C_313470_.m_323371_();
      datacomponentmap$builder.m_321974_(this.f_314183_);
      this.m_318837_(datacomponentmap$builder);
      return datacomponentmap$builder.m_318826_();
   }

   public C_313470_ m_324356_() {
      return this.f_314183_;
   }

   public void m_323608_(C_313470_ mapIn) {
      this.f_314183_ = mapIn;
   }

   @Nullable
   public static C_4996_ m_336414_(String nameIn, C_213466_.C_254607_ providerIn) {
      try {
         return C_4997_.m_130691_(nameIn, providerIn);
      } catch (Exception var3) {
         f_58854_.warn("Failed to parse custom name from string '{}', discarding", nameIn, var3);
         return null;
      }
   }

   static class C_313263_ {
      public static final Codec f_316981_;

      private C_313263_() {
      }

      static {
         f_316981_ = C_313470_.f_315283_.optionalFieldOf("components", C_313470_.f_314291_).codec();
      }
   }

   protected interface C_313341_ {
      @Nullable
      Object m_319293_(C_313543_ var1);

      Object m_319031_(C_313543_ var1, Object var2);
   }
}
