package net.minecraft.src;

import com.mojang.logging.LogUtils;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.optifine.util.BiomeUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

public class C_5247_ {
   private static final Logger f_135342_ = LogUtils.getLogger();
   private static final int f_179843_ = 254;
   static final C_313305_ f_316642_ = new C_313305_();
   private final C_313530_ f_135344_;
   private final C_5247_.C_5248_<?>[] f_135345_;
   private boolean f_135348_;
   public C_1629_ spawnBiome = BiomeUtils.PLAINS;
   public C_4675_ spawnPosition = C_4675_.f_121853_;
   public C_2064_ blockStateOn = C_1710_.f_50016_.m_49966_();
   public long blockStateOnUpdateMs = 0L;
   public Map<String, Object> modelVariables;
   public C_4917_ nbtTag;
   public long nbtTagUpdateMs = 0L;

   C_5247_(C_313530_ entityIn, C_5247_.C_5248_<?>[] entriesIn) {
      this.f_135344_ = entityIn;
      this.f_135345_ = entriesIn;
   }

   public static <T> C_5225_<T> m_135353_(Class<? extends C_313530_> clazz, C_5226_<T> serializer) {
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

   private <T> C_5247_.C_5248_<T> m_135379_(C_5225_<T> key) {
      return (C_5247_.C_5248_<T>)this.f_135345_[key.f_135010_()];
   }

   public <T> T m_135370_(C_5225_<T> key) {
      return this.<T>m_135379_(key).m_135403_();
   }

   public <T> void m_135381_(C_5225_<T> key, T value) {
      this.m_276349_(key, value, false);
   }

   public <T> void m_276349_(C_5225_<T> accessorIn, T valueIn, boolean forcedIn) {
      C_5247_.C_5248_<T> dataitem = this.m_135379_(accessorIn);
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
   public List<C_5247_.C_252362_<?>> m_135378_() {
      if (!this.f_135348_) {
         return null;
      } else {
         this.f_135348_ = false;
         List<C_5247_.C_252362_<?>> list = new ArrayList();

         for (C_5247_.C_5248_<?> dataitem : this.f_135345_) {
            if (dataitem.m_135406_()) {
               dataitem.m_135401_(false);
               list.add(dataitem.m_253123_());
            }
         }

         return list;
      }
   }

   @Nullable
   public List<C_5247_.C_252362_<?>> m_252804_() {
      List<C_5247_.C_252362_<?>> list = null;

      for (C_5247_.C_5248_<?> dataitem : this.f_135345_) {
         if (!dataitem.m_252838_()) {
            if (list == null) {
               list = new ArrayList();
            }

            list.add(dataitem.m_253123_());
         }
      }

      return list;
   }

   public void m_135356_(List<C_5247_.C_252362_<?>> entriesIn) {
      for (C_5247_.C_252362_<?> datavalue : entriesIn) {
         C_5247_.C_5248_<?> dataitem = this.f_135345_[datavalue.f_252469_];
         this.m_135375_(dataitem, datavalue);
         this.f_135344_.m_7350_(dataitem.m_135396_());
         this.nbtTag = null;
      }

      this.f_135344_.m_269505_(entriesIn);
   }

   private <T> void m_135375_(C_5247_.C_5248_<T> target, C_5247_.C_252362_<?> source) {
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

   public static record C_252362_<T>(int f_252469_, C_5226_<T> f_252511_, T f_252525_) {
      public static <T> C_5247_.C_252362_<T> m_252847_(C_5225_<T> accessorIn, T dataIn) {
         C_5226_<T> entitydataserializer = accessorIn.f_135011_();
         return new C_5247_.C_252362_<>(accessorIn.f_135010_(), entitydataserializer, (T)entitydataserializer.m_7020_(dataIn));
      }

      public void m_252897_(C_313350_ byteBufIn) {
         int i = C_5227_.m_135052_(this.f_252511_);
         if (i < 0) {
            throw new EncoderException("Unknown serializer type " + this.f_252511_);
         } else {
            byteBufIn.k(this.f_252469_);
            byteBufIn.c(i);
            this.f_252511_.m_321181_().encode(byteBufIn, this.f_252525_);
         }
      }

      public static C_5247_.C_252362_<?> m_252860_(C_313350_ byteBufIn, int lengthIn) {
         int i = byteBufIn.l();
         C_5226_<?> entitydataserializer = C_5227_.m_135048_(i);
         if (entitydataserializer == null) {
            throw new DecoderException("Unknown serializer type " + i);
         } else {
            return m_252951_(byteBufIn, lengthIn, (C_5226_<T>)entitydataserializer);
         }
      }

      private static <T> C_5247_.C_252362_<T> m_252951_(C_313350_ byteBufIn, int idIn, C_5226_<T> serializerIn) {
         return new C_5247_.C_252362_<>(idIn, serializerIn, (T)serializerIn.m_321181_().decode(byteBufIn));
      }
   }

   public static class C_313487_ {
      private final C_313530_ f_314721_;
      private final C_5247_.C_5248_<?>[] f_313958_;

      public C_313487_(C_313530_ p_i319630_1_) {
         this.f_314721_ = p_i319630_1_;
         this.f_313958_ = new C_5247_.C_5248_[C_5247_.f_316642_.m_321486_(p_i319630_1_.getClass())];
      }

      public <T> C_5247_.C_313487_ m_318949_(C_5225_<T> accessorIn, T valueIn) {
         int i = accessorIn.f_135010_();
         if (i > this.f_313958_.length) {
            throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + this.f_313958_.length + ")");
         } else if (this.f_313958_[i] != null) {
            throw new IllegalArgumentException("Duplicate id value for " + i + "!");
         } else if (C_5227_.m_135052_(accessorIn.f_135011_()) < 0) {
            throw new IllegalArgumentException("Unregistered serializer " + accessorIn.f_135011_() + " for " + i + "!");
         } else {
            this.f_313958_[accessorIn.f_135010_()] = new C_5247_.C_5248_(accessorIn, valueIn);
            return this;
         }
      }

      public C_5247_ m_320942_() {
         for (int i = 0; i < this.f_313958_.length; i++) {
            if (this.f_313958_[i] == null) {
               throw new IllegalStateException("Entity " + this.f_314721_.getClass() + " has not defined synched data value " + i);
            }
         }

         return new C_5247_(this.f_314721_, this.f_313958_);
      }
   }

   public static class C_5248_<T> {
      final C_5225_<T> f_135390_;
      T f_135391_;
      private final T f_252454_;
      private boolean f_135392_;

      public C_5248_(C_5225_<T> keyIn, T valueIn) {
         this.f_135390_ = keyIn;
         this.f_252454_ = valueIn;
         this.f_135391_ = valueIn;
      }

      public C_5225_<T> m_135396_() {
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

      public C_5247_.C_252362_<T> m_253123_() {
         return C_5247_.C_252362_.m_252847_(this.f_135390_, this.f_135391_);
      }
   }
}
