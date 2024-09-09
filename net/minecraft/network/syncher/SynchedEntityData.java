package net.minecraft.network.syncher;

import com.mojang.logging.LogUtils;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.ClassTreeIdRegistry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.optifine.util.BiomeUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

public class SynchedEntityData {
   private static final Logger f_135342_ = LogUtils.getLogger();
   private static final int f_179843_ = 254;
   static final ClassTreeIdRegistry f_316642_ = new ClassTreeIdRegistry();
   private final SyncedDataHolder f_135344_;
   private final net.minecraft.network.syncher.SynchedEntityData.DataItem<?>[] f_135345_;
   private boolean f_135348_;
   public Biome spawnBiome = BiomeUtils.PLAINS;
   public BlockPos spawnPosition = BlockPos.f_121853_;
   public net.minecraft.world.level.block.state.BlockState blockStateOn = Blocks.f_50016_.m_49966_();
   public long blockStateOnUpdateMs = 0L;
   public Map<String, Object> modelVariables;
   public CompoundTag nbtTag;
   public long nbtTagUpdateMs = 0L;

   SynchedEntityData(SyncedDataHolder entityIn, net.minecraft.network.syncher.SynchedEntityData.DataItem<?>[] entriesIn) {
      this.f_135344_ = entityIn;
      this.f_135345_ = entriesIn;
   }

   public static <T> EntityDataAccessor<T> m_135353_(Class<? extends SyncedDataHolder> clazz, EntityDataSerializer<T> serializer) {
      if (f_135342_.isDebugEnabled()) {
         try {
            Class<?> oclass = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            if (!oclass.equals(clazz)) {
               f_135342_.debug("defineId called for: {} from {}", new Object[]{clazz, oclass, new RuntimeException()});
            }
         } catch (ClassNotFoundException var3) {
         }
      }

      int i = f_316642_.m_321864_(clazz);
      if (i > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is 254)");
      } else {
         return serializer.m_135021_(i);
      }
   }

   private <T> net.minecraft.network.syncher.SynchedEntityData.DataItem<T> m_135379_(EntityDataAccessor<T> key) {
      return (net.minecraft.network.syncher.SynchedEntityData.DataItem<T>)this.f_135345_[key.f_135010_()];
   }

   public <T> T m_135370_(EntityDataAccessor<T> key) {
      return this.<T>m_135379_(key).m_135403_();
   }

   public <T> void m_135381_(EntityDataAccessor<T> key, T value) {
      this.m_276349_(key, value, false);
   }

   public <T> void m_276349_(EntityDataAccessor<T> accessorIn, T valueIn, boolean forcedIn) {
      net.minecraft.network.syncher.SynchedEntityData.DataItem<T> dataitem = this.m_135379_(accessorIn);
      if (forcedIn || ObjectUtils.notEqual(valueIn, dataitem.m_135403_())) {
         dataitem.m_135397_(valueIn);
         this.f_135344_.m_7350_(accessorIn);
         dataitem.m_135401_(true);
         this.f_135348_ = true;
         this.nbtTag = null;
      }
   }

   public boolean m_135352_() {
      return this.f_135348_;
   }

   @Nullable
   public List<net.minecraft.network.syncher.SynchedEntityData.DataValue<?>> m_135378_() {
      if (!this.f_135348_) {
         return null;
      } else {
         this.f_135348_ = false;
         List<net.minecraft.network.syncher.SynchedEntityData.DataValue<?>> list = new ArrayList();

         for (net.minecraft.network.syncher.SynchedEntityData.DataItem<?> dataitem : this.f_135345_) {
            if (dataitem.m_135406_()) {
               dataitem.m_135401_(false);
               list.add(dataitem.m_253123_());
            }
         }

         return list;
      }
   }

   @Nullable
   public List<net.minecraft.network.syncher.SynchedEntityData.DataValue<?>> m_252804_() {
      List<net.minecraft.network.syncher.SynchedEntityData.DataValue<?>> list = null;

      for (net.minecraft.network.syncher.SynchedEntityData.DataItem<?> dataitem : this.f_135345_) {
         if (!dataitem.m_252838_()) {
            if (list == null) {
               list = new ArrayList();
            }

            list.add(dataitem.m_253123_());
         }
      }

      return list;
   }

   public void m_135356_(List<net.minecraft.network.syncher.SynchedEntityData.DataValue<?>> entriesIn) {
      for (net.minecraft.network.syncher.SynchedEntityData.DataValue<?> datavalue : entriesIn) {
         net.minecraft.network.syncher.SynchedEntityData.DataItem<?> dataitem = this.f_135345_[datavalue.f_252469_];
         this.m_135375_(dataitem, datavalue);
         this.f_135344_.m_7350_(dataitem.m_135396_());
         this.nbtTag = null;
      }

      this.f_135344_.m_269505_(entriesIn);
   }

   private <T> void m_135375_(
      net.minecraft.network.syncher.SynchedEntityData.DataItem<T> target, net.minecraft.network.syncher.SynchedEntityData.DataValue<?> source
   ) {
      if (!Objects.equals(source.f_252511_(), target.f_135390_.f_135011_())) {
         throw new IllegalStateException(
            String.format(
               Locale.ROOT,
               "Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)",
               target.f_135390_.f_135010_(),
               this.f_135344_,
               target.f_135391_,
               target.f_135391_.getClass(),
               source.f_252525_,
               source.f_252525_.getClass()
            )
         );
      } else {
         target.m_135397_((T)source.f_252525_);
      }
   }

   public static class Builder {
      private final SyncedDataHolder f_314721_;
      private final net.minecraft.network.syncher.SynchedEntityData.DataItem<?>[] f_313958_;

      public Builder(SyncedDataHolder p_i319630_1_) {
         this.f_314721_ = p_i319630_1_;
         this.f_313958_ = new net.minecraft.network.syncher.SynchedEntityData.DataItem[net.minecraft.network.syncher.SynchedEntityData.f_316642_
            .m_321486_(p_i319630_1_.getClass())];
      }

      public <T> net.minecraft.network.syncher.SynchedEntityData.Builder m_318949_(EntityDataAccessor<T> accessorIn, T valueIn) {
         int i = accessorIn.f_135010_();
         if (i > this.f_313958_.length) {
            throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + this.f_313958_.length + ")");
         } else if (this.f_313958_[i] != null) {
            throw new IllegalArgumentException("Duplicate id value for " + i + "!");
         } else if (EntityDataSerializers.m_135052_(accessorIn.f_135011_()) < 0) {
            throw new IllegalArgumentException("Unregistered serializer " + accessorIn.f_135011_() + " for " + i + "!");
         } else {
            this.f_313958_[accessorIn.f_135010_()] = new net.minecraft.network.syncher.SynchedEntityData.DataItem(accessorIn, valueIn);
            return this;
         }
      }

      public net.minecraft.network.syncher.SynchedEntityData m_320942_() {
         for (int i = 0; i < this.f_313958_.length; i++) {
            if (this.f_313958_[i] == null) {
               throw new IllegalStateException("Entity " + this.f_314721_.getClass() + " has not defined synched data value " + i);
            }
         }

         return new net.minecraft.network.syncher.SynchedEntityData(this.f_314721_, this.f_313958_);
      }
   }

   public static class DataItem<T> {
      final EntityDataAccessor<T> f_135390_;
      T f_135391_;
      private final T f_252454_;
      private boolean f_135392_;

      public DataItem(EntityDataAccessor<T> keyIn, T valueIn) {
         this.f_135390_ = keyIn;
         this.f_252454_ = valueIn;
         this.f_135391_ = valueIn;
      }

      public EntityDataAccessor<T> m_135396_() {
         return this.f_135390_;
      }

      public void m_135397_(T valueIn) {
         this.f_135391_ = valueIn;
      }

      public T m_135403_() {
         return this.f_135391_;
      }

      public boolean m_135406_() {
         return this.f_135392_;
      }

      public void m_135401_(boolean dirtyIn) {
         this.f_135392_ = dirtyIn;
      }

      public boolean m_252838_() {
         return this.f_252454_.equals(this.f_135391_);
      }

      public net.minecraft.network.syncher.SynchedEntityData.DataValue<T> m_253123_() {
         return net.minecraft.network.syncher.SynchedEntityData.DataValue.m_252847_(this.f_135390_, this.f_135391_);
      }
   }

   public static record DataValue<T>(int f_252469_, EntityDataSerializer<T> f_252511_, T f_252525_) {
      public static <T> net.minecraft.network.syncher.SynchedEntityData.DataValue<T> m_252847_(EntityDataAccessor<T> accessorIn, T dataIn) {
         EntityDataSerializer<T> entitydataserializer = accessorIn.f_135011_();
         return new net.minecraft.network.syncher.SynchedEntityData.DataValue<>(
            accessorIn.f_135010_(), entitydataserializer, (T)entitydataserializer.m_7020_(dataIn)
         );
      }

      public void m_252897_(RegistryFriendlyByteBuf byteBufIn) {
         int i = EntityDataSerializers.m_135052_(this.f_252511_);
         if (i < 0) {
            throw new EncoderException("Unknown serializer type " + this.f_252511_);
         } else {
            byteBufIn.writeByte(this.f_252469_);
            byteBufIn.m_130130_(i);
            this.f_252511_.m_321181_().m_318638_(byteBufIn, this.f_252525_);
         }
      }

      public static net.minecraft.network.syncher.SynchedEntityData.DataValue<?> m_252860_(RegistryFriendlyByteBuf byteBufIn, int lengthIn) {
         int i = byteBufIn.m_130242_();
         EntityDataSerializer<?> entitydataserializer = EntityDataSerializers.m_135048_(i);
         if (entitydataserializer == null) {
            throw new DecoderException("Unknown serializer type " + i);
         } else {
            return m_252951_(byteBufIn, lengthIn, (EntityDataSerializer<T>)entitydataserializer);
         }
      }

      private static <T> net.minecraft.network.syncher.SynchedEntityData.DataValue<T> m_252951_(
         RegistryFriendlyByteBuf byteBufIn, int idIn, EntityDataSerializer<T> serializerIn
      ) {
         return new net.minecraft.network.syncher.SynchedEntityData.DataValue<>(idIn, serializerIn, (T)serializerIn.m_321181_().m_318688_(byteBufIn));
      }
   }
}
