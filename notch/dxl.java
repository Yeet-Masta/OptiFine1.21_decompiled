package net.minecraft.src;

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

public class C_141285_<T extends C_141279_> {
   private final Class<T> f_156850_;
   private final Long2ObjectFunction<C_141300_> f_156851_;
   private final Long2ObjectMap<C_141284_<T>> f_156852_ = new Long2ObjectOpenHashMap();
   private final LongSortedSet f_156853_ = new LongAVLTreeSet();
   private boolean updated;

   public C_141285_(Class<T> classIn, Long2ObjectFunction<C_141300_> visibilityIn) {
      this.f_156850_ = classIn;
      this.f_156851_ = visibilityIn;
   }

   public void m_188362_(C_3040_ aabbIn, C_260372_<C_141284_<T>> consumerIn) {
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
         LongIterator longiterator = this.f_156853_.subSet(i2, j2 + 1L).iterator();

         while (longiterator.hasNext()) {
            long k2 = longiterator.nextLong();
            int l2 = C_4710_.m_123225_(k2);
            int i3 = C_4710_.m_123230_(k2);
            if (l2 >= k && l2 <= j1 && i3 >= l && i3 <= k1) {
               C_141284_<T> entitysection = (C_141284_<T>)this.f_156852_.get(k2);
               if (entitysection != null
                  && !entitysection.m_156833_()
                  && entitysection.m_156848_().m_157694_()
                  && consumerIn.m_260972_(entitysection).m_261146_()) {
                  return;
               }
            }
         }
      }
   }

   public LongStream m_156861_(long longPosIn) {
      int i = C_1560_.m_45592_(longPosIn);
      int j = C_1560_.m_45602_(longPosIn);
      LongSortedSet longsortedset = this.m_156858_(i, j);
      if (longsortedset.isEmpty()) {
         return LongStream.empty();
      } else {
         OfLong oflong = longsortedset.iterator();
         return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(oflong, 1301), false);
      }
   }

   private LongSortedSet m_156858_(int x, int z) {
      long i = C_4710_.m_123209_(x, 0, z);
      long j = C_4710_.m_123209_(x, -1, z);
      return this.f_156853_.subSet(i, j + 1L);
   }

   public Stream<C_141284_<T>> m_156888_(long longPosIn) {
      return this.m_156861_(longPosIn).mapToObj(this.f_156852_::get).filter(Objects::nonNull);
   }

   private static long m_156899_(long longPosIn) {
      return C_1560_.m_45589_(C_4710_.m_123213_(longPosIn), C_4710_.m_123230_(longPosIn));
   }

   public C_141284_<T> m_156893_(long longPosIn) {
      int sizePre = this.f_156852_.size();

      C_141284_ var4;
      try {
         var4 = (C_141284_)this.f_156852_.computeIfAbsent(longPosIn, this::m_156901_);
      } finally {
         if (this.f_156852_.size() != sizePre) {
            this.updated = true;
         }
      }

      return var4;
   }

   @Nullable
   public C_141284_<T> m_156895_(long longPosIn) {
      return (C_141284_<T>)this.f_156852_.get(longPosIn);
   }

   private C_141284_<T> m_156901_(long longPosIn) {
      long i = m_156899_(longPosIn);
      C_141300_ visibility = (C_141300_)this.f_156851_.get(i);
      this.f_156853_.add(longPosIn);
      return new C_141284_<>(this.f_156850_, visibility);
   }

   public LongSet m_156857_() {
      LongSet longset = new LongOpenHashSet();
      this.f_156852_.keySet().forEach(longPosIn -> longset.add(m_156899_(longPosIn)));
      return longset;
   }

   public void m_261111_(C_3040_ aabbIn, C_260372_<T> consumerIn) {
      this.m_188362_(aabbIn, sectionIn -> sectionIn.m_260830_(aabbIn, consumerIn));
   }

   public <U extends T> void m_261191_(C_141287_<T, U> typeTestIn, C_3040_ aabbIn, C_260372_<U> consumerIn) {
      this.m_188362_(aabbIn, sectionIn -> sectionIn.m_188348_(typeTestIn, aabbIn, consumerIn));
   }

   public void m_156897_(long longPosIn) {
      int sizePre = this.f_156852_.size();
      this.f_156852_.remove(longPosIn);
      this.f_156853_.remove(longPosIn);
      if (this.f_156852_.size() != sizePre) {
         this.updated = true;
      }
   }

   @C_140994_
   public int m_156887_() {
      return this.f_156853_.size();
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
      return this.f_156852_.keySet();
   }
}
