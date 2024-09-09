package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import org.slf4j.Logger;

public abstract class BlockEntity extends CapabilityProvider<net.minecraft.world.level.block.entity.BlockEntity> implements IForgeBlockEntity {
   private static final Logger f_58854_ = LogUtils.getLogger();
   private final BlockEntityType<?> f_58855_;
   @Nullable
   protected Level f_58857_;
   protected final BlockPos f_58858_;
   protected boolean f_58859_;
   private net.minecraft.world.level.block.state.BlockState f_58856_;
   private DataComponentMap f_314183_ = DataComponentMap.f_314291_;
   private CompoundTag customPersistentData;
   public CompoundTag nbtTag;
   public long nbtTagUpdateMs = 0L;

   public BlockEntity(BlockEntityType<?> typeIn, BlockPos posIn, net.minecraft.world.level.block.state.BlockState stateIn) {
      super(net.minecraft.world.level.block.entity.BlockEntity.class);
      this.f_58855_ = typeIn;
      this.f_58858_ = posIn.m_7949_();
      this.f_58856_ = stateIn;
      this.gatherCapabilities();
   }

   public static BlockPos m_187472_(CompoundTag tagIn) {
      return new BlockPos(tagIn.m_128451_("x"), tagIn.m_128451_("y"), tagIn.m_128451_("z"));
   }

   @Nullable
   public Level m_58904_() {
      return this.f_58857_;
   }

   public void m_142339_(Level worldIn) {
      this.f_58857_ = worldIn;
   }

   public boolean m_58898_() {
      return this.f_58857_ != null;
   }

   protected void m_318667_(CompoundTag tagIn, Provider providerIn) {
      if (tagIn.m_128441_("ForgeData")) {
         this.customPersistentData = tagIn.m_128469_("ForgeData");
      }

      if (this.getCapabilities() != null && tagIn.m_128441_("ForgeCaps")) {
         this.deserializeCaps(tagIn.m_128469_("ForgeCaps"));
      }
   }

   public final void m_320998_(CompoundTag tagIn, Provider providerIn) {
      this.m_318667_(tagIn, providerIn);
      net.minecraft.world.level.block.entity.BlockEntity.ComponentHelper.f_316981_
         .parse(providerIn.m_318927_(NbtOps.f_128958_), tagIn)
         .resultOrPartial(p_318380_0_ -> f_58854_.warn("Failed to load components: {}", p_318380_0_))
         .ifPresent(p_318382_1_ -> this.f_314183_ = p_318382_1_);
   }

   public final void m_324273_(CompoundTag tagIn, Provider providerIn) {
      this.m_318667_(tagIn, providerIn);
   }

   protected void m_183515_(CompoundTag tagIn, Provider providerIn) {
      if (this.customPersistentData != null) {
         tagIn.m_128365_("ForgeData", this.customPersistentData.m_6426_());
      }

      if (this.getCapabilities() != null) {
         tagIn.m_128365_("ForgeCaps", this.serializeCaps());
      }
   }

   public final CompoundTag m_187480_(Provider providerIn) {
      CompoundTag compoundtag = this.m_187482_(providerIn);
      this.m_187478_(compoundtag);
      return compoundtag;
   }

   public final CompoundTag m_187481_(Provider providerIn) {
      CompoundTag compoundtag = this.m_187482_(providerIn);
      this.m_187474_(compoundtag);
      return compoundtag;
   }

   public final CompoundTag m_187482_(Provider providerIn) {
      CompoundTag compoundtag = new CompoundTag();
      this.m_183515_(compoundtag, providerIn);
      net.minecraft.world.level.block.entity.BlockEntity.ComponentHelper.f_316981_
         .encodeStart(providerIn.m_318927_(NbtOps.f_128958_), this.f_314183_)
         .resultOrPartial(p_318379_0_ -> f_58854_.warn("Failed to save components: {}", p_318379_0_))
         .ifPresent(p_318384_1_ -> compoundtag.m_128391_((CompoundTag)p_318384_1_));
      return compoundtag;
   }

   public final CompoundTag m_320696_(Provider providerIn) {
      CompoundTag compoundtag = new CompoundTag();
      this.m_183515_(compoundtag, providerIn);
      return compoundtag;
   }

   public final CompoundTag m_319785_(Provider providerIn) {
      CompoundTag compoundtag = this.m_320696_(providerIn);
      this.m_187478_(compoundtag);
      return compoundtag;
   }

   private void m_187474_(CompoundTag tagIn) {
      net.minecraft.resources.ResourceLocation resourcelocation = BlockEntityType.m_58954_(this.m_58903_());
      if (resourcelocation == null) {
         throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
      } else {
         tagIn.m_128359_("id", resourcelocation.toString());
      }
   }

   public static void m_187468_(CompoundTag tagIn, BlockEntityType<?> typeIn) {
      tagIn.m_128359_("id", BlockEntityType.m_58954_(typeIn).toString());
   }

   public void m_187476_(ItemStack stackIn, Provider providerIn) {
      CompoundTag compoundtag = this.m_320696_(providerIn);
      this.m_318942_(compoundtag);
      BlockItem.m_186338_(stackIn, this.m_58903_(), compoundtag);
      stackIn.m_323474_(this.m_321843_());
   }

   private void m_187478_(CompoundTag tagIn) {
      this.m_187474_(tagIn);
      tagIn.m_128405_("x", this.f_58858_.m_123341_());
      tagIn.m_128405_("y", this.f_58858_.m_123342_());
      tagIn.m_128405_("z", this.f_58858_.m_123343_());
   }

   @Nullable
   public static net.minecraft.world.level.block.entity.BlockEntity m_155241_(
      BlockPos posIn, net.minecraft.world.level.block.state.BlockState stateIn, CompoundTag tagIn, Provider providerIn
   ) {
      String s = tagIn.m_128461_("id");
      net.minecraft.resources.ResourceLocation resourcelocation = net.minecraft.resources.ResourceLocation.m_135820_(s);
      if (resourcelocation == null) {
         f_58854_.error("Block entity has invalid type: {}", s);
         return null;
      } else {
         return (net.minecraft.world.level.block.entity.BlockEntity)BuiltInRegistries.f_257049_.m_6612_(resourcelocation).map(p_155236_3_ -> {
            try {
               return p_155236_3_.m_155264_(posIn, stateIn);
            } catch (Throwable var5x) {
               f_58854_.error("Failed to create block entity {}", s, var5x);
               return null;
            }
         }).map(p_318381_3_ -> {
            try {
               p_318381_3_.m_320998_(tagIn, providerIn);
               return p_318381_3_;
            } catch (Throwable var5x) {
               f_58854_.error("Failed to load data for block entity {}", s, var5x);
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

   protected static void m_155232_(Level worldIn, BlockPos posIn, net.minecraft.world.level.block.state.BlockState stateIn) {
      worldIn.m_151543_(posIn);
      if (!stateIn.m_60795_()) {
         worldIn.m_46717_(posIn, stateIn.m_60734_());
      }
   }

   public BlockPos m_58899_() {
      return this.f_58858_;
   }

   public net.minecraft.world.level.block.state.BlockState m_58900_() {
      return this.f_58856_;
   }

   @Nullable
   public Packet<ClientGamePacketListener> m_58483_() {
      return null;
   }

   public CompoundTag m_5995_(Provider providerIn) {
      return new CompoundTag();
   }

   public boolean m_58901_() {
      return this.f_58859_;
   }

   public void m_7651_() {
      this.f_58859_ = true;
      this.invalidateCaps();
      this.requestModelDataUpdate();
   }

   @Override
   public void onChunkUnloaded() {
      this.invalidateCaps();
   }

   public CompoundTag getPersistentData() {
      if (this.customPersistentData == null) {
         this.customPersistentData = new CompoundTag();
      }

      return this.customPersistentData;
   }

   public void m_6339_() {
      this.f_58859_ = false;
   }

   public boolean m_7531_(int id, int type) {
      return false;
   }

   public void m_58886_(CrashReportCategory reportCategory) {
      reportCategory.m_128165_("Name", () -> BuiltInRegistries.f_257049_.m_7981_(this.m_58903_()) + " // " + this.getClass().getCanonicalName());
      if (this.f_58857_ != null) {
         CrashReportCategory.m_178950_(reportCategory, this.f_58857_, this.f_58858_, this.m_58900_());
         CrashReportCategory.m_178950_(reportCategory, this.f_58857_, this.f_58858_, this.f_58857_.m_8055_(this.f_58858_));
      }
   }

   public boolean m_6326_() {
      return false;
   }

   public BlockEntityType<?> m_58903_() {
      return this.f_58855_;
   }

   @Deprecated
   public void m_155250_(net.minecraft.world.level.block.state.BlockState stateIn) {
      this.f_58856_ = stateIn;
   }

   protected void m_318741_(net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput inputIn) {
   }

   public final void m_322533_(ItemStack stackIn) {
      this.m_322221_(stackIn.m_322741_(), stackIn.m_324277_());
   }

   public final void m_322221_(DataComponentMap mapIn, DataComponentPatch patchIn) {
      final Set<DataComponentType<?>> set = new HashSet();
      set.add(DataComponents.f_316520_);
      final DataComponentMap datacomponentmap = net.minecraft.core.component.PatchedDataComponentMap.m_322493_(mapIn, patchIn);
      this.m_318741_(new net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput() {
         @Nullable
         @Override
         public <T> T m_319293_(DataComponentType<T> typeIn) {
            set.add(typeIn);
            return (T)datacomponentmap.m_318834_(typeIn);
         }

         @Override
         public <T> T m_319031_(DataComponentType<? extends T> typeIn, T defIn) {
            set.add(typeIn);
            return (T)datacomponentmap.m_322806_(typeIn, defIn);
         }
      });
      DataComponentPatch datacomponentpatch = patchIn.m_318691_(set::contains);
      this.f_314183_ = datacomponentpatch.m_324808_().f_314173_();
   }

   protected void m_318837_(Builder builderIn) {
   }

   @Deprecated
   public void m_318942_(CompoundTag tagIn) {
   }

   public final DataComponentMap m_321843_() {
      Builder datacomponentmap$builder = DataComponentMap.m_323371_();
      datacomponentmap$builder.m_321974_(this.f_314183_);
      this.m_318837_(datacomponentmap$builder);
      return datacomponentmap$builder.m_318826_();
   }

   public DataComponentMap m_324356_() {
      return this.f_314183_;
   }

   public void m_323608_(DataComponentMap mapIn) {
      this.f_314183_ = mapIn;
   }

   @Nullable
   public static Component m_336414_(String nameIn, Provider providerIn) {
      try {
         return Serializer.m_130691_(nameIn, providerIn);
      } catch (Exception var3) {
         f_58854_.warn("Failed to parse custom name from string '{}', discarding", nameIn, var3);
         return null;
      }
   }

   static class ComponentHelper {
      public static final Codec<DataComponentMap> f_316981_ = DataComponentMap.f_315283_.optionalFieldOf("components", DataComponentMap.f_314291_).codec();

      private ComponentHelper() {
      }
   }

   protected interface DataComponentInput {
      @Nullable
      <T> T m_319293_(DataComponentType<T> var1);

      <T> T m_319031_(DataComponentType<? extends T> var1, T var2);
   }
}
