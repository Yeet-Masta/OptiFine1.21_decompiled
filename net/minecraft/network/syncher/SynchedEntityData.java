package net.minecraft.network.syncher;

import com.mojang.logging.LogUtils;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.util.ArrayList;
import java.util.Iterator;
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
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.util.BiomeUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

public class SynchedEntityData {
   private static final Logger f_135342_ = LogUtils.getLogger();
   private static final int f_179843_ = 254;
   static final ClassTreeIdRegistry f_316642_ = new ClassTreeIdRegistry();
   private final SyncedDataHolder f_135344_;
   private final DataItem[] f_135345_;
   private boolean f_135348_;
   public Biome spawnBiome;
   public BlockPos spawnPosition;
   public BlockState blockStateOn;
   public long blockStateOnUpdateMs;
   public Map modelVariables;
   public CompoundTag nbtTag;
   public long nbtTagUpdateMs;

   SynchedEntityData(SyncedDataHolder entityIn, DataItem[] entriesIn) {
      this.spawnBiome = BiomeUtils.PLAINS;
      this.spawnPosition = BlockPos.f_121853_;
      this.blockStateOn = Blocks.f_50016_.m_49966_();
      this.blockStateOnUpdateMs = 0L;
      this.nbtTagUpdateMs = 0L;
      this.f_135344_ = entityIn;
      this.f_135345_ = entriesIn;
   }

   public static EntityDataAccessor m_135353_(Class clazz, EntityDataSerializer serializer) {
      if (f_135342_.isDebugEnabled()) {
         try {
            Class oclass = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
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

   private DataItem m_135379_(EntityDataAccessor key) {
      return this.f_135345_[key.f_135010_()];
   }

   public Object m_135370_(EntityDataAccessor key) {
      return this.m_135379_(key).m_135403_();
   }

   public void m_135381_(EntityDataAccessor key, Object value) {
      this.m_276349_(key, value, false);
   }

   public void m_276349_(EntityDataAccessor accessorIn, Object valueIn, boolean forcedIn) {
      DataItem dataitem = this.m_135379_(accessorIn);
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
   public List m_135378_() {
      if (!this.f_135348_) {
         return null;
      } else {
         this.f_135348_ = false;
         List list = new ArrayList();
         DataItem[] var2 = this.f_135345_;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            DataItem dataitem = var2[var4];
            if (dataitem.m_135406_()) {
               dataitem.m_135401_(false);
               list.add(dataitem.m_253123_());
            }
         }

         return list;
      }
   }

   @Nullable
   public List m_252804_() {
      List list = null;
      DataItem[] var2 = this.f_135345_;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         DataItem dataitem = var2[var4];
         if (!dataitem.m_252838_()) {
            if (list == null) {
               list = new ArrayList();
            }

            list.add(dataitem.m_253123_());
         }
      }

      return list;
   }

   public void m_135356_(List entriesIn) {
      for(Iterator var2 = entriesIn.iterator(); var2.hasNext(); this.nbtTag = null) {
         DataValue datavalue = (DataValue)var2.next();
         DataItem dataitem = this.f_135345_[datavalue.f_252469_];
         this.m_135375_(dataitem, datavalue);
         this.f_135344_.m_7350_(dataitem.m_135396_());
      }

      this.f_135344_.m_269505_(entriesIn);
   }

   private void m_135375_(DataItem target, DataValue source) {
      if (!Objects.equals(source.f_252511_(), target.f_135390_.f_135011_())) {
         throw new IllegalStateException(String.format(Locale.ROOT, "Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)", target.f_135390_.f_135010_(), this.f_135344_, target.f_135391_, target.f_135391_.getClass(), source.f_252525_, source.f_252525_.getClass()));
      } else {
         target.m_135397_(source.f_252525_);
      }
   }

   public static class DataItem {
      final EntityDataAccessor f_135390_;
      Object f_135391_;
      private final Object f_252454_;
      private boolean f_135392_;

      public DataItem(EntityDataAccessor keyIn, Object valueIn) {
         this.f_135390_ = keyIn;
         this.f_252454_ = valueIn;
         this.f_135391_ = valueIn;
      }

      public EntityDataAccessor m_135396_() {
         return this.f_135390_;
      }

      public void m_135397_(Object valueIn) {
         this.f_135391_ = valueIn;
      }

      public Object m_135403_() {
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

      public DataValue m_253123_() {
         return SynchedEntityData.DataValue.m_252847_(this.f_135390_, this.f_135391_);
      }
   }

   public static record DataValue(int f_252469_, EntityDataSerializer f_252511_, Object f_252525_) {
      public DataValue(int id, EntityDataSerializer serializer, Object value) {
         this.f_252469_ = id;
         this.f_252511_ = serializer;
         this.f_252525_ = value;
      }

      public static DataValue m_252847_(EntityDataAccessor accessorIn, Object dataIn) {
         EntityDataSerializer entitydataserializer = accessorIn.f_135011_();
         return new DataValue(accessorIn.f_135010_(), entitydataserializer, entitydataserializer.m_7020_(dataIn));
      }

      public void m_252897_(RegistryFriendlyByteBuf byteBufIn) {
         int i = EntityDataSerializers.m_135052_(this.f_252511_);
         if (i < 0) {
            throw new EncoderException("Unknown serializer type " + String.valueOf(this.f_252511_));
         } else {
            byteBufIn.writeByte(this.f_252469_);
            byteBufIn.m_130130_(i);
            this.f_252511_.m_321181_().m_318638_(byteBufIn, this.f_252525_);
         }
      }

      public static DataValue m_252860_(RegistryFriendlyByteBuf byteBufIn, int lengthIn) {
         int i = byteBufIn.m_130242_();
         EntityDataSerializer entitydataserializer = EntityDataSerializers.m_135048_(i);
         if (entitydataserializer == null) {
            throw new DecoderException("Unknown serializer type " + i);
         } else {
            return m_252951_(byteBufIn, lengthIn, entitydataserializer);
         }
      }

      private static DataValue m_252951_(RegistryFriendlyByteBuf byteBufIn, int idIn, EntityDataSerializer serializerIn) {
         return new DataValue(idIn, serializerIn, serializerIn.m_321181_().m_318688_(byteBufIn));
      }

      public int f_252469_() {
         return this.f_252469_;
      }

      public EntityDataSerializer f_252511_() {
         return this.f_252511_;
      }

      public Object f_252525_() {
         return this.f_252525_;
      }
   }

   public static class Builder {
      private final SyncedDataHolder f_314721_;
      private final DataItem[] f_313958_;

      public Builder(SyncedDataHolder p_i319630_1_) {
         this.f_314721_ = p_i319630_1_;
         this.f_313958_ = new DataItem[SynchedEntityData.f_316642_.m_321486_(p_i319630_1_.getClass())];
      }

      public Builder m_318949_(EntityDataAccessor accessorIn, Object valueIn) {
         int i = accessorIn.f_135010_();
         if (i > this.f_313958_.length) {
            throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + this.f_313958_.length + ")");
         } else if (this.f_313958_[i] != null) {
            throw new IllegalArgumentException("Duplicate id value for " + i + "!");
         } else if (EntityDataSerializers.m_135052_(accessorIn.f_135011_()) < 0) {
            String var10002 = String.valueOf(accessorIn.f_135011_());
            throw new IllegalArgumentException("Unregistered serializer " + var10002 + " for " + i + "!");
         } else {
            this.f_313958_[accessorIn.f_135010_()] = new DataItem(accessorIn, valueIn);
            return this;
         }
      }

      public SynchedEntityData m_320942_() {
         for(int i = 0; i < this.f_313958_.length; ++i) {
            if (this.f_313958_[i] == null) {
               String var10002 = String.valueOf(this.f_314721_.getClass());
               throw new IllegalStateException("Entity " + var10002 + " has not defined synched data value " + i);
            }
         }

         return new SynchedEntityData(this.f_314721_, this.f_313958_);
      }
   }
}
