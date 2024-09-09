import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import javax.annotation.Nullable;
import net.minecraft.src.C_290184_;
import org.slf4j.Logger;

public class SectionBufferBuilderPool {
   private static final Logger a = LogUtils.getLogger();
   private final Queue<C_290184_> b;
   private volatile int c;

   private SectionBufferBuilderPool(List<C_290184_> builderPacksIn) {
      this.b = Queues.newConcurrentLinkedQueue(builderPacksIn);
      this.c = this.b.size();
   }

   public static SectionBufferBuilderPool a(int sizeIn) {
      int i = Math.max(1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3) / C_290184_.f_303427_);
      int j = Math.max(1, Math.min(sizeIn, i));
      List<C_290184_> list = new ArrayList(j);

      try {
         for (int k = 0; k < j; k++) {
            list.add(new C_290184_());
         }
      } catch (OutOfMemoryError var7) {
         a.warn("Allocated only {}/{} buffers", list.size(), j);
         int l = Math.min(list.size() * 2 / 3, list.size() - 1);

         for (int i1 = 0; i1 < l; i1++) {
            ((C_290184_)list.remove(list.size() - 1)).close();
         }
      }

      return new SectionBufferBuilderPool(list);
   }

   @Nullable
   public C_290184_ a() {
      C_290184_ sectionbufferbuilderpack = (C_290184_)this.b.poll();
      if (sectionbufferbuilderpack != null) {
         this.c = this.b.size();
         return sectionbufferbuilderpack;
      } else {
         return null;
      }
   }

   public void a(C_290184_ builderPackIn) {
      this.b.add(builderPackIn);
      this.c = this.b.size();
   }

   public boolean b() {
      return this.b.isEmpty();
   }

   public int c() {
      return this.c;
   }
}
