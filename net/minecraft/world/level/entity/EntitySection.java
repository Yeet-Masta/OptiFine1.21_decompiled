package net.minecraft.world.level.entity;

import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.util.ClassInstanceMultiMap;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.AbortableIterationConsumer.Continuation;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

public class EntitySection {
   private static final Logger f_156826_ = LogUtils.getLogger();
   private final ClassInstanceMultiMap f_156827_;
   private Visibility f_156828_;

   public EntitySection(Class classIn, Visibility visibilityIn) {
      this.f_156828_ = visibilityIn;
      this.f_156827_ = new ClassInstanceMultiMap(classIn);
   }

   public void m_188346_(EntityAccess accessIn) {
      this.f_156827_.add(accessIn);
   }

   public boolean m_188355_(EntityAccess accessIn) {
      return this.f_156827_.remove(accessIn);
   }

   public AbortableIterationConsumer.Continuation m_260830_(AABB aabbIn, AbortableIterationConsumer consumerIn) {
      Iterator var3 = this.f_156827_.iterator();

      EntityAccess t;
      do {
         if (!var3.hasNext()) {
            return Continuation.CONTINUE;
         }

         t = (EntityAccess)var3.next();
      } while(!t.m_20191_().m_82381_(aabbIn) || !consumerIn.m_260972_(t).m_261146_());

      return Continuation.ABORT;
   }

   public AbortableIterationConsumer.Continuation m_188348_(EntityTypeTest typeTestIn, AABB aabbIn, AbortableIterationConsumer consumerIn) {
      Collection collection = this.f_156827_.m_13533_(typeTestIn.m_142225_());
      if (collection.isEmpty()) {
         return Continuation.CONTINUE;
      } else {
         Iterator var5 = collection.iterator();

         EntityAccess t;
         EntityAccess u;
         do {
            if (!var5.hasNext()) {
               return Continuation.CONTINUE;
            }

            t = (EntityAccess)var5.next();
            u = (EntityAccess)typeTestIn.m_141992_(t);
         } while(u == null || !t.m_20191_().m_82381_(aabbIn) || !consumerIn.m_260972_(u).m_261146_());

         return Continuation.ABORT;
      }
   }

   public boolean m_156833_() {
      return this.f_156827_.isEmpty();
   }

   public Stream m_156845_() {
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

   public List getEntityList() {
      return this.f_156827_.getValues();
   }

   public static EntitySectionStorage getSectionStorage(TransientEntitySectionManager tesm) {
      return tesm.f_157638_;
   }
}
