package net.minecraft.src;

import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import javax.annotation.Nullable;
import org.slf4j.Logger;

public class C_302100_ {
   private static final Logger f_303160_ = LogUtils.getLogger();
   private final Queue<C_290184_> f_302413_;
   private volatile int f_303717_;

   private C_302100_(List<C_290184_> builderPacksIn) {
      this.f_302413_ = Queues.newConcurrentLinkedQueue(builderPacksIn);
      this.f_303717_ = this.f_302413_.size();
   }

   public static C_302100_ m_306138_(int sizeIn) {
      int i = Math.max(1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3) / C_290184_.f_303427_);
      int j = Math.max(1, Math.min(sizeIn, i));
      List<C_290184_> list = new ArrayList(j);

      try {
         for (int k = 0; k < j; k++) {
            list.add(new C_290184_());
         }
      } catch (OutOfMemoryError var7) {
         f_303160_.warn("Allocated only {}/{} buffers", list.size(), j);
         int l = Math.min(list.size() * 2 / 3, list.size() - 1);

         for (int i1 = 0; i1 < l; i1++) {
            ((C_290184_)list.remove(list.size() - 1)).close();
         }
      }

      return new C_302100_(list);
   }

   @Nullable
   public C_290184_ m_307873_() {
      C_290184_ sectionbufferbuilderpack = (C_290184_)this.f_302413_.poll();
      if (sectionbufferbuilderpack != null) {
         this.f_303717_ = this.f_302413_.size();
         return sectionbufferbuilderpack;
      } else {
         return null;
      }
   }

   public void m_306477_(C_290184_ builderPackIn) {
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
