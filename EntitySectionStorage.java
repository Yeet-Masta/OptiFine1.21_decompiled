import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongAVLTreeSet;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import java.util.Objects;
import java.util.Spliterators;
import java.util.PrimitiveIterator.OfLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.src.C_140994_;
import net.minecraft.src.C_141279_;
import net.minecraft.src.C_141287_;
import net.minecraft.src.C_141300_;
import net.minecraft.src.C_260372_;
import net.minecraft.src.C_3040_;
import net.minecraft.src.C_4710_;

public class EntitySectionStorage<T extends C_141279_> {
   private final Class<T> a;
   private final Long2ObjectFunction<C_141300_> b;
   private final Long2ObjectMap<EntitySection<T>> c = new Long2ObjectOpenHashMap();
   private final LongSortedSet d = new LongAVLTreeSet();
   private boolean updated;

   public EntitySectionStorage(Class<T> classIn, Long2ObjectFunction<C_141300_> visibilityIn) {
      this.a = classIn;
      this.b = visibilityIn;
   }

   public void a(C_3040_ aabbIn, C_260372_<EntitySection<T>> consumerIn) {
      int i = 2;
      int j = C_4710_.m_175552_(aabbIn.f_82288_ - 2.0);
      int k = C_4710_.m_175552_(aabbIn.f_82289_ - 4.0);
      int l = C_4710_.m_175552_(aabbIn.f_82290_ - 2.0);
      int i1 = C_4710_.m_175552_(aabbIn.f_82291_ + 2.0);
      int j1 = C_4710_.m_175552_(aabbIn.f_82292_ + 0.0);
      int k1 = C_4710_.m_175552_(aabbIn.f_82293_ + 2.0);

      for (int l1 = j; l1 <= i1; l1++) {
         long i2 = C_4710_.m_123209_(l1, 0, 0);
         long j2 = C_4710_.m_123209_(l1, -1, -1);
         LongIterator longiterator = this.d.subSet(i2, j2 + 1L).iterator();

         while (longiterator.hasNext()) {
            long k2 = longiterator.nextLong();
            int l2 = C_4710_.m_123225_(k2);
            int i3 = C_4710_.m_123230_(k2);
            if (l2 >= k && l2 <= j1 && i3 >= l && i3 <= k1) {
               EntitySection<T> entitysection = (EntitySection<T>)this.c.get(k2);
               if (entitysection != null && !entitysection.a() && entitysection.c().m_157694_() && consumerIn.m_260972_(entitysection).m_261146_()) {
                  return;
               }
            }
         }
      }
   }

   public LongStream a(long longPosIn) {
      int i = ChunkPos.a(longPosIn);
      int j = ChunkPos.b(longPosIn);
      LongSortedSet longsortedset = this.a(i, j);
      if (longsortedset.isEmpty()) {
         return LongStream.empty();
      } else {
         OfLong oflong = longsortedset.iterator();
         return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(oflong, 1301), false);
      }
   }

   private LongSortedSet a(int x, int z) {
      long i = C_4710_.m_123209_(x, 0, z);
      long j = C_4710_.m_123209_(x, -1, z);
      return this.d.subSet(i, j + 1L);
   }

   public Stream<EntitySection<T>> b(long longPosIn) {
      return this.a(longPosIn).mapToObj(this.c::get).filter(Objects::nonNull);
   }

   private static long f(long longPosIn) {
      return ChunkPos.c(C_4710_.m_123213_(longPosIn), C_4710_.m_123230_(longPosIn));
   }

   public EntitySection<T> c(long longPosIn) {
      int sizePre = this.c.size();

      EntitySection var4;
      try {
         var4 = (EntitySection)this.c.computeIfAbsent(longPosIn, this::g);
      } finally {
         if (this.c.size() != sizePre) {
            this.updated = true;
         }
      }

      return var4;
   }

   @Nullable
   public EntitySection<T> d(long longPosIn) {
      return (EntitySection<T>)this.c.get(longPosIn);
   }

   private EntitySection<T> g(long longPosIn) {
      long i = f(longPosIn);
      C_141300_ visibility = (C_141300_)this.b.get(i);
      this.d.add(longPosIn);
      return new EntitySection<>(this.a, visibility);
   }

   public LongSet a() {
      LongSet longset = new LongOpenHashSet();
      this.c.keySet().forEach(longPosIn -> longset.add(f(longPosIn)));
      return longset;
   }

   public void b(C_3040_ aabbIn, C_260372_<T> consumerIn) {
      this.a(aabbIn, sectionIn -> sectionIn.a(aabbIn, consumerIn));
   }

   public <U extends T> void a(C_141287_<T, U> typeTestIn, C_3040_ aabbIn, C_260372_<U> consumerIn) {
      this.a(aabbIn, sectionIn -> sectionIn.a(typeTestIn, aabbIn, consumerIn));
   }

   public void e(long longPosIn) {
      int sizePre = this.c.size();
      this.c.remove(longPosIn);
      this.d.remove(longPosIn);
      if (this.c.size() != sizePre) {
         this.updated = true;
      }
   }

   @C_140994_
   public int b() {
      return this.d.size();
   }

   public boolean isUpdated() {
      return this.updated;
   }

   public boolean resetUpdated() {
      boolean ret = this.updated;
      this.updated = false;
      return ret;
   }

   public LongSet getSectionKeys() {
      return this.c.keySet();
   }
}
