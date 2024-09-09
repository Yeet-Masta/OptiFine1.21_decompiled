package net.minecraft.client.renderer;

import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import javax.annotation.Nullable;
import org.slf4j.Logger;

public class SectionBufferBuilderPool {
   private static final Logger f_303160_ = LogUtils.getLogger();
   private final Queue<SectionBufferBuilderPack> f_302413_;
   private volatile int f_303717_;

   private SectionBufferBuilderPool(List<SectionBufferBuilderPack> builderPacksIn) {
      this.f_302413_ = Queues.newConcurrentLinkedQueue(builderPacksIn);
      this.f_303717_ = this.f_302413_.size();
   }

   public static net.minecraft.client.renderer.SectionBufferBuilderPool m_306138_(int sizeIn) {
      int i = Math.max(1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3) / SectionBufferBuilderPack.f_303427_);
      int j = Math.max(1, Math.min(sizeIn, i));
      List<SectionBufferBuilderPack> list = new ArrayList(j);

      try {
         for (int k = 0; k < j; k++) {
            list.add(new SectionBufferBuilderPack());
         }
      } catch (OutOfMemoryError var7) {
         f_303160_.warn("Allocated only {}/{} buffers", list.size(), j);
         int l = Math.min(list.size() * 2 / 3, list.size() - 1);

         for (int i1 = 0; i1 < l; i1++) {
            ((SectionBufferBuilderPack)list.remove(list.size() - 1)).close();
         }
      }

      return new net.minecraft.client.renderer.SectionBufferBuilderPool(list);
   }

   @Nullable
   public SectionBufferBuilderPack m_307873_() {
      SectionBufferBuilderPack sectionbufferbuilderpack = (SectionBufferBuilderPack)this.f_302413_.poll();
      if (sectionbufferbuilderpack != null) {
         this.f_303717_ = this.f_302413_.size();
         return sectionbufferbuilderpack;
      } else {
         return null;
      }
   }

   public void m_306477_(SectionBufferBuilderPack builderPackIn) {
      this.f_302413_.add(builderPackIn);
      this.f_303717_ = this.f_302413_.size();
   }

   public boolean m_307681_() {
      return this.f_302413_.isEmpty();
   }

   public int m_306121_() {
      return this.f_303717_;
   }
}
