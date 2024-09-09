package net.minecraft.src;

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
import net.optifine.util.BiomeUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

public class C_5247_ {
   private static final Logger f_135342_ = LogUtils.getLogger();
   private static final int f_179843_ = 254;
   static final C_313305_ f_316642_ = new C_313305_();
   private final C_313530_ f_135344_;
   private final C_5248_[] f_135345_;
   private boolean f_135348_;
   public C_1629_ spawnBiome;
   public C_4675_ spawnPosition;
   public C_2064_ blockStateOn;
   public long blockStateOnUpdateMs;
   public Map modelVariables;
   public C_4917_ nbtTag;
   public long nbtTagUpdateMs;

   C_5247_(C_313530_ entityIn, C_5248_[] entriesIn) {
      this.spawnBiome = BiomeUtils.PLAINS;
      this.spawnPosition = C_4675_.f_121853_;
      this.blockStateOn = C_1710_.f_50016_.m_49966_();
      this.blockStateOnUpdateMs = 0L;
      this.nbtTagUpdateMs = 0L;
      this.f_135344_ = entityIn;
      this.f_135345_ = entriesIn;
   }

   public static C_5225_ m_135353_(Class clazz, C_5226_ serializer) {
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

   private C_5248_ m_135379_(C_5225_ key) {
      return this.f_135345_[key.f_135010_()];
   }

   public Object m_135370_(C_5225_ key) {
      return this.m_135379_(key).m_135403_();
   }

   public void m_135381_(C_5225_ key, Object value) {
      this.m_276349_(key, value, false);
   }

   public void m_276349_(C_5225_ accessorIn, Object valueIn, boolean forcedIn) {
      C_5248_ dataitem = this.m_135379_(accessorIn);
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
         C_5248_[] var2 = this.f_135345_;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            C_5248_ dataitem = var2[var4];
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
      C_5248_[] var2 = this.f_135345_;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         C_5248_ dataitem = var2[var4];
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
         C_252362_ datavalue = (C_252362_)var2.next();
         C_5248_ dataitem = this.f_135345_[datavalue.f_252469_];
         this.m_135375_(dataitem, datavalue);
         this.f_135344_.m_7350_(dataitem.m_135396_());
      }

      this.f_135344_.m_269505_(entriesIn);
   }

   private void m_135375_(C_5248_ target, C_252362_ source) {
      if (!Objects.equals(source.f_252511_(), target.f_135390_.f_135011_())) {
         throw new IllegalStateException(String.format(Locale.ROOT, "Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)", target.f_135390_.f_135010_(), this.f_135344_, target.f_135391_, target.f_135391_.getClass(), source.f_252525_, source.f_252525_.getClass()));
      } else {
         target.m_135397_(source.f_252525_);
      }
   }

   public static class C_5248_ {
      final C_5225_ f_135390_;
      Object f_135391_;
      private final Object f_252454_;
      private boolean f_135392_;

      public C_5248_(C_5225_ keyIn, Object valueIn) {
         this.f_135390_ = keyIn;
         this.f_252454_ = valueIn;
         this.f_135391_ = valueIn;
      }

      public C_5225_ m_135396_() {
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

      public C_252362_ m_253123_() {
         return C_5247_.C_252362_.m_252847_(this.f_135390_, this.f_135391_);
      }
   }

   public static record C_252362_(int f_252469_, C_5226_ f_252511_, Object f_252525_) {
      public C_252362_(int id, C_5226_ serializer, Object value) {
         this.f_252469_ = id;
         this.f_252511_ = serializer;
         this.f_252525_ = value;
      }

      public static C_252362_ m_252847_(C_5225_ accessorIn, Object dataIn) {
         C_5226_ entitydataserializer = accessorIn.f_135011_();
         return new C_252362_(accessorIn.f_135010_(), entitydataserializer, entitydataserializer.m_7020_(dataIn));
      }

      public void m_252897_(C_313350_ byteBufIn) {
         int i = C_5227_.m_135052_(this.f_252511_);
         if (i < 0) {
            throw new EncoderException("Unknown serializer type " + String.valueOf(this.f_252511_));
         } else {
            byteBufIn.k(this.f_252469_);
            byteBufIn.c(i);
            this.f_252511_.m_321181_().encode(byteBufIn, this.f_252525_);
         }
      }

      public static C_252362_ m_252860_(C_313350_ byteBufIn, int lengthIn) {
         int i = byteBufIn.l();
         C_5226_ entitydataserializer = C_5227_.m_135048_(i);
         if (entitydataserializer == null) {
            throw new DecoderException("Unknown serializer type " + i);
         } else {
            return m_252951_(byteBufIn, lengthIn, entitydataserializer);
         }
      }

      private static C_252362_ m_252951_(C_313350_ byteBufIn, int idIn, C_5226_ serializerIn) {
         return new C_252362_(idIn, serializerIn, serializerIn.m_321181_().decode(byteBufIn));
      }

      public int f_252469_() {
         return this.f_252469_;
      }

      public C_5226_ f_252511_() {
         return this.f_252511_;
      }

      public Object f_252525_() {
         return this.f_252525_;
      }
   }

   public static class C_313487_ {
      private final C_313530_ f_314721_;
      private final C_5248_[] f_313958_;

      public C_313487_(C_313530_ p_i319630_1_) {
         this.f_314721_ = p_i319630_1_;
         this.f_313958_ = new C_5248_[C_5247_.f_316642_.m_321486_(p_i319630_1_.getClass())];
      }

      public C_313487_ m_318949_(C_5225_ accessorIn, Object valueIn) {
         int i = accessorIn.f_135010_();
         if (i > this.f_313958_.length) {
            throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + this.f_313958_.length + ")");
         } else if (this.f_313958_[i] != null) {
            throw new IllegalArgumentException("Duplicate id value for " + i + "!");
         } else if (C_5227_.m_135052_(accessorIn.f_135011_()) < 0) {
            String var10002 = String.valueOf(accessorIn.f_135011_());
            throw new IllegalArgumentException("Unregistered serializer " + var10002 + " for " + i + "!");
         } else {
            this.f_313958_[accessorIn.f_135010_()] = new C_5248_(accessorIn, valueIn);
            return this;
         }
      }

      public C_5247_ m_320942_() {
         for(int i = 0; i < this.f_313958_.length; ++i) {
            if (this.f_313958_[i] == null) {
               String var10002 = String.valueOf(this.f_314721_.getClass());
               throw new IllegalStateException("Entity " + var10002 + " has not defined synched data value " + i);
            }
         }

         return new C_5247_(this.f_314721_, this.f_313958_);
      }
   }
}
