package net.minecraft.src;

import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.src.C_260372_.C_260376_;
import org.slf4j.Logger;

public class C_141284_<T extends C_141279_> {
   private static final Logger f_156826_ = LogUtils.getLogger();
   private final C_164_<T> f_156827_;
   private C_141300_ f_156828_;

   public C_141284_(Class<T> classIn, C_141300_ visibilityIn) {
      this.f_156828_ = visibilityIn;
      this.f_156827_ = new C_164_<>(classIn);
   }

   public void m_188346_(T accessIn) {
      this.f_156827_.add(accessIn);
   }

   public boolean m_188355_(T accessIn) {
      return this.f_156827_.remove(accessIn);
   }

   public C_260376_ m_260830_(C_3040_ aabbIn, C_260372_<T> consumerIn) {
      for (T t : this.f_156827_) {
         if (t.m_20191_().m_82381_(aabbIn) && consumerIn.m_260972_(t).m_261146_()) {
            return C_260376_.ABORT;
         }
      }

      return C_260376_.CONTINUE;
   }

   public <U extends T> C_260376_ m_188348_(C_141287_<T, U> typeTestIn, C_3040_ aabbIn, C_260372_<? super U> consumerIn) {
      Collection<? extends T> collection = this.f_156827_.m_13533_(typeTestIn.m_142225_());
      if (collection.isEmpty()) {
         return C_260376_.CONTINUE;
      } else {
         for (T t : collection) {
            U u = (U)typeTestIn.m_141992_(t);
            if (u != null && t.m_20191_().m_82381_(aabbIn) && consumerIn.m_260972_(u).m_261146_()) {
               return C_260376_.ABORT;
            }
         }

         return C_260376_.CONTINUE;
      }
   }

   public boolean m_156833_() {
      return this.f_156827_.isEmpty();
   }

   public Stream<T> m_156845_() {
      return this.f_156827_.stream();
   }

   public C_141300_ m_156848_() {
      return this.f_156828_;
   }

   public C_141300_ m_156838_(C_141300_ visibilityIn) {
      C_141300_ visibility = this.f_156828_;
      this.f_156828_ = visibilityIn;
      return visibility;
   }

   @C_140994_
   public int m_156849_() {
      return this.f_156827_.size();
   }

   public List<T> getEntityList() {
      return this.f_156827_.getValues();
   }

   public static C_141285_ getSectionStorage(C_141298_ tesm) {
      return tesm.f_157638_;
   }
}
