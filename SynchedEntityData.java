import com.mojang.logging.LogUtils;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_313305_;
import net.minecraft.src.C_313350_;
import net.minecraft.src.C_313530_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_5225_;
import net.minecraft.src.C_5226_;
import net.minecraft.src.C_5227_;
import net.optifine.util.BiomeUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

public class SynchedEntityData {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = 254;
   static final C_313305_ c = new C_313305_();
   private final C_313530_ d;
   private final SynchedEntityData.b<?>[] e;
   private boolean f;
   public C_1629_ spawnBiome = BiomeUtils.PLAINS;
   public C_4675_ spawnPosition = C_4675_.f_121853_;
   public BlockState blockStateOn = C_1710_.f_50016_.o();
   public long blockStateOnUpdateMs = 0L;
   public Map<String, Object> modelVariables;
   public C_4917_ nbtTag;
   public long nbtTagUpdateMs = 0L;

   SynchedEntityData(C_313530_ entityIn, SynchedEntityData.b<?>[] entriesIn) {
      this.d = entityIn;
      this.e = entriesIn;
   }

   public static <T> C_5225_<T> a(Class<? extends C_313530_> clazz, C_5226_<T> serializer) {
      if (a.isDebugEnabled()) {
         try {
            Class<?> oclass = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            if (!oclass.equals(clazz)) {
               a.debug("defineId called for: {} from {}", new Object[]{clazz, oclass, new RuntimeException()});
            }
         } catch (ClassNotFoundException var3) {
         }
      }

      int i = c.m_321864_(clazz);
      if (i > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is 254)");
      } else {
         return serializer.m_135021_(i);
      }
   }

   private <T> SynchedEntityData.b<T> b(C_5225_<T> key) {
      return (SynchedEntityData.b<T>)this.e[key.f_135010_()];
   }

   public <T> T a(C_5225_<T> key) {
      return this.<T>b(key).b();
   }

   public <T> void a(C_5225_<T> key, T value) {
      this.a(key, value, false);
   }

   public <T> void a(C_5225_<T> accessorIn, T valueIn, boolean forcedIn) {
      SynchedEntityData.b<T> dataitem = this.b(accessorIn);
      if (forcedIn || ObjectUtils.notEqual(valueIn, dataitem.b())) {
         dataitem.a(valueIn);
         this.d.m_7350_(accessorIn);
         dataitem.a(true);
         this.f = true;
         this.nbtTag = null;
      }
   }

   public boolean a() {
      return this.f;
   }

   @Nullable
   public List<SynchedEntityData.c<?>> b() {
      if (!this.f) {
         return null;
      } else {
         this.f = false;
         List<SynchedEntityData.c<?>> list = new ArrayList();

         for (SynchedEntityData.b<?> dataitem : this.e) {
            if (dataitem.c()) {
               dataitem.a(false);
               list.add(dataitem.e());
            }
         }

         return list;
      }
   }

   @Nullable
   public List<SynchedEntityData.c<?>> c() {
      List<SynchedEntityData.c<?>> list = null;

      for (SynchedEntityData.b<?> dataitem : this.e) {
         if (!dataitem.d()) {
            if (list == null) {
               list = new ArrayList();
            }

            list.add(dataitem.e());
         }
      }

      return list;
   }

   public void a(List<SynchedEntityData.c<?>> entriesIn) {
      for (SynchedEntityData.c<?> datavalue : entriesIn) {
         SynchedEntityData.b<?> dataitem = this.e[datavalue.a];
         this.a(dataitem, datavalue);
         this.d.m_7350_(dataitem.a());
         this.nbtTag = null;
      }

      this.d.m_269505_(entriesIn);
   }

   private <T> void a(SynchedEntityData.b<T> target, SynchedEntityData.c<?> source) {
      if (!Objects.equals(source.b(), target.a.f_135011_())) {
         throw new IllegalStateException(
            String.format(
               Locale.ROOT,
               "Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)",
               target.a.f_135010_(),
               this.d,
               target.b,
               target.b.getClass(),
               source.c,
               source.c.getClass()
            )
         );
      } else {
         target.a((T)source.c);
      }
   }

   public static class a {
      private final C_313530_ a;
      private final SynchedEntityData.b<?>[] b;

      public a(C_313530_ p_i319630_1_) {
         this.a = p_i319630_1_;
         this.b = new SynchedEntityData.b[SynchedEntityData.c.m_321486_(p_i319630_1_.getClass())];
      }

      public <T> SynchedEntityData.a a(C_5225_<T> accessorIn, T valueIn) {
         int i = accessorIn.f_135010_();
         if (i > this.b.length) {
            throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + this.b.length + ")");
         } else if (this.b[i] != null) {
            throw new IllegalArgumentException("Duplicate id value for " + i + "!");
         } else if (C_5227_.m_135052_(accessorIn.f_135011_()) < 0) {
            throw new IllegalArgumentException("Unregistered serializer " + accessorIn.f_135011_() + " for " + i + "!");
         } else {
            this.b[accessorIn.f_135010_()] = new SynchedEntityData.b(accessorIn, valueIn);
            return this;
         }
      }

      public SynchedEntityData a() {
         for (int i = 0; i < this.b.length; i++) {
            if (this.b[i] == null) {
               throw new IllegalStateException("Entity " + this.a.getClass() + " has not defined synched data value " + i);
            }
         }

         return new SynchedEntityData(this.a, this.b);
      }
   }

   public static class b<T> {
      final C_5225_<T> a;
      T b;
      private final T c;
      private boolean d;

      public b(C_5225_<T> keyIn, T valueIn) {
         this.a = keyIn;
         this.c = valueIn;
         this.b = valueIn;
      }

      public C_5225_<T> a() {
         return this.a;
      }

      public void a(T valueIn) {
         this.b = valueIn;
      }

      public T b() {
         return this.b;
      }

      public boolean c() {
         return this.d;
      }

      public void a(boolean dirtyIn) {
         this.d = dirtyIn;
      }

      public boolean d() {
         return this.c.equals(this.b);
      }

      public SynchedEntityData.c<T> e() {
         return SynchedEntityData.c.a(this.a, this.b);
      }
   }

   public static record c<T>(int a, C_5226_<T> b, T c) {
      public static <T> SynchedEntityData.c<T> a(C_5225_<T> accessorIn, T dataIn) {
         C_5226_<T> entitydataserializer = accessorIn.f_135011_();
         return new SynchedEntityData.c<>(accessorIn.f_135010_(), entitydataserializer, (T)entitydataserializer.m_7020_(dataIn));
      }

      public void a(C_313350_ byteBufIn) {
         int i = C_5227_.m_135052_(this.b);
         if (i < 0) {
            throw new EncoderException("Unknown serializer type " + this.b);
         } else {
            byteBufIn.writeByte(this.a);
            byteBufIn.m_130130_(i);
            this.b.m_321181_().m_318638_(byteBufIn, this.c);
         }
      }

      public static SynchedEntityData.c<?> a(C_313350_ byteBufIn, int lengthIn) {
         int i = byteBufIn.m_130242_();
         C_5226_<?> entitydataserializer = C_5227_.m_135048_(i);
         if (entitydataserializer == null) {
            throw new DecoderException("Unknown serializer type " + i);
         } else {
            return a(byteBufIn, lengthIn, (C_5226_<T>)entitydataserializer);
         }
      }

      private static <T> SynchedEntityData.c<T> a(C_313350_ byteBufIn, int idIn, C_5226_<T> serializerIn) {
         return new SynchedEntityData.c<>(idIn, serializerIn, (T)serializerIn.m_321181_().m_318688_(byteBufIn));
      }
   }
}
