package net.minecraft.world.level.entity;

import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongAVLTreeSet;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Spliterators;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.AABB;

public class EntitySectionStorage {
   private final Class f_156850_;
   private final Long2ObjectFunction f_156851_;
   private final Long2ObjectMap f_156852_ = new Long2ObjectOpenHashMap();
   private final LongSortedSet f_156853_ = new LongAVLTreeSet();
   private boolean updated;

   public EntitySectionStorage(Class classIn, Long2ObjectFunction visibilityIn) {
      this.f_156850_ = classIn;
      this.f_156851_ = visibilityIn;
   }

   public void m_188362_(AABB aabbIn, AbortableIterationConsumer consumerIn) {
      int i = true;
      int j = SectionPos.m_175552_(aabbIn.f_82288_ - 2.0);
      int k = SectionPos.m_175552_(aabbIn.f_82289_ - 4.0);
      int l = SectionPos.m_175552_(aabbIn.f_82290_ - 2.0);
      int i1 = SectionPos.m_175552_(aabbIn.f_82291_ + 2.0);
      int j1 = SectionPos.m_175552_(aabbIn.f_82292_ + 0.0);
      int k1 = SectionPos.m_175552_(aabbIn.f_82293_ + 2.0);

      for(int l1 = j; l1 <= i1; ++l1) {
         long i2 = SectionPos.m_123209_(l1, 0, 0);
         long j2 = SectionPos.m_123209_(l1, -1, -1);
         LongIterator longiterator = this.f_156853_.subSet(i2, j2 + 1L).iterator();

         while(longiterator.hasNext()) {
            long k2 = longiterator.nextLong();
            int l2 = SectionPos.m_123225_(k2);
            int i3 = SectionPos.m_123230_(k2);
            if (l2 >= k && l2 <= j1 && i3 >= l && i3 <= k1) {
               EntitySection entitysection = (EntitySection)this.f_156852_.get(k2);
               if (entitysection != null && !entitysection.m_156833_() && entitysection.m_156848_().m_157694_() && consumerIn.m_260972_(entitysection).m_261146_()) {
                  return;
               }
            }
         }
      }

   }

   public LongStream m_156861_(long longPosIn) {
      int i = ChunkPos.m_45592_(longPosIn);
      int j = ChunkPos.m_45602_(longPosIn);
      LongSortedSet longsortedset = this.m_156858_(i, j);
      if (longsortedset.isEmpty()) {
         return LongStream.empty();
      } else {
         PrimitiveIterator.OfLong oflong = longsortedset.iterator();
         return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(oflong, 1301), false);
      }
   }

   private LongSortedSet m_156858_(int x, int z) {
      long i = SectionPos.m_123209_(x, 0, z);
      long j = SectionPos.m_123209_(x, -1, z);
      return this.f_156853_.subSet(i, j + 1L);
   }

   public Stream m_156888_(long longPosIn) {
      LongStream var10000 = this.m_156861_(longPosIn);
      Long2ObjectMap var10001 = this.f_156852_;
      Objects.requireNonNull(var10001);
      return var10000.mapToObj(var10001::get).filter(Objects::nonNull);
   }

   private static long m_156899_(long longPosIn) {
      return ChunkPos.m_45589_(SectionPos.m_123213_(longPosIn), SectionPos.m_123230_(longPosIn));
   }

   public EntitySection m_156893_(long longPosIn) {
      int sizePre = this.f_156852_.size();

      EntitySection var4;
      try {
         var4 = (EntitySection)this.f_156852_.computeIfAbsent(longPosIn, this::m_156901_);
      } finally {
         if (this.f_156852_.size() != sizePre) {
            this.updated = true;
         }

      }

      return var4;
   }

   @Nullable
   public EntitySection m_156895_(long longPosIn) {
      return (EntitySection)this.f_156852_.get(longPosIn);
   }

   private EntitySection m_156901_(long longPosIn) {
      long i = m_156899_(longPosIn);
      Visibility visibility = (Visibility)this.f_156851_.get(i);
      this.f_156853_.add(longPosIn);
      return new EntitySection(this.f_156850_, visibility);
   }

   public LongSet m_156857_() {
      LongSet longset = new LongOpenHashSet();
      this.f_156852_.keySet().forEach((longPosIn) -> {
         longset.add(m_156899_(longPosIn));
      });
      return longset;
   }

   public void m_261111_(AABB aabbIn, AbortableIterationConsumer consumerIn) {
      this.m_188362_(aabbIn, (sectionIn) -> {
         return sectionIn.m_260830_(aabbIn, consumerIn);
      });
   }

   public void m_261191_(EntityTypeTest typeTestIn, AABB aabbIn, AbortableIterationConsumer consumerIn) {
      this.m_188362_(aabbIn, (sectionIn) -> {
         return sectionIn.m_188348_(typeTestIn, aabbIn, consumerIn);
      });
   }

   public void m_156897_(long longPosIn) {
      int sizePre = this.f_156852_.size();
      this.f_156852_.remove(longPosIn);
      this.f_156853_.remove(longPosIn);
      if (this.f_156852_.size() != sizePre) {
         this.updated = true;
      }

   }

   @VisibleForDebug
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
