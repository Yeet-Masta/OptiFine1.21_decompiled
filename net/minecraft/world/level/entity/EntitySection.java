package net.minecraft.world.level.entity;

import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.AbortableIterationConsumer.Continuation;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

public class EntitySection<T extends EntityAccess> {
   private static final Logger f_156826_ = LogUtils.getLogger();
   private final net.minecraft.util.ClassInstanceMultiMap<T> f_156827_;
   private Visibility f_156828_;

   public EntitySection(Class<T> classIn, Visibility visibilityIn) {
      this.f_156828_ = visibilityIn;
      this.f_156827_ = new net.minecraft.util.ClassInstanceMultiMap<>(classIn);
   }

   public void m_188346_(T accessIn) {
      this.f_156827_.add(accessIn);
   }

   public boolean m_188355_(T accessIn) {
      return this.f_156827_.remove(accessIn);
   }

   public Continuation m_260830_(AABB aabbIn, AbortableIterationConsumer<T> consumerIn) {
      for (T t : this.f_156827_) {
         if (t.m_20191_().m_82381_(aabbIn) && consumerIn.m_260972_(t).m_261146_()) {
            return Continuation.ABORT;
         }
      }

      return Continuation.CONTINUE;
   }

   public <U extends T> Continuation m_188348_(EntityTypeTest<T, U> typeTestIn, AABB aabbIn, AbortableIterationConsumer<? super U> consumerIn) {
      Collection<? extends T> collection = this.f_156827_.m_13533_(typeTestIn.m_142225_());
      if (collection.isEmpty()) {
         return Continuation.CONTINUE;
      } else {
         for (T t : collection) {
            U u = (U)typeTestIn.m_141992_(t);
            if (u != null && t.m_20191_().m_82381_(aabbIn) && consumerIn.m_260972_(u).m_261146_()) {
               return Continuation.ABORT;
            }
         }

         return Continuation.CONTINUE;
      }
   }

   public boolean m_156833_() {
      return this.f_156827_.isEmpty();
   }

   public Stream<T> m_156845_() {
      return this.f_156827_.stream();
   }

   public Visibility m_156848_() {
      return this.f_156828_;
   }

   public Visibility m_156838_(Visibility visibilityIn) {
      Visibility visibility = this.f_156828_;
      this.f_156828_ = visibilityIn;
      return visibility;
   }

   @VisibleForDebug
   public int m_156849_() {
      return this.f_156827_.size();
   }

   public List<T> getEntityList() {
      return this.f_156827_.getValues();
   }

   public static net.minecraft.world.level.entity.EntitySectionStorage getSectionStorage(TransientEntitySectionManager tesm) {
      return tesm.f_157638_;
   }
}
