import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.src.C_140994_;
import net.minecraft.src.C_141279_;
import net.minecraft.src.C_141287_;
import net.minecraft.src.C_141298_;
import net.minecraft.src.C_141300_;
import net.minecraft.src.C_260372_;
import net.minecraft.src.C_3040_;
import net.minecraft.src.C_260372_.C_260376_;
import org.slf4j.Logger;

public class EntitySection<T extends C_141279_> {
   private static final Logger a = LogUtils.getLogger();
   private final ClassInstanceMultiMap<T> b;
   private C_141300_ c;

   public EntitySection(Class<T> classIn, C_141300_ visibilityIn) {
      this.c = visibilityIn;
      this.b = new ClassInstanceMultiMap<>(classIn);
   }

   public void a(T accessIn) {
      this.b.add(accessIn);
   }

   public boolean b(T accessIn) {
      return this.b.remove(accessIn);
   }

   public C_260376_ a(C_3040_ aabbIn, C_260372_<T> consumerIn) {
      for (T t : this.b) {
         if (t.m_20191_().m_82381_(aabbIn) && consumerIn.m_260972_(t).m_261146_()) {
            return C_260376_.ABORT;
         }
      }

      return C_260376_.CONTINUE;
   }

   public <U extends T> C_260376_ a(C_141287_<T, U> typeTestIn, C_3040_ aabbIn, C_260372_<? super U> consumerIn) {
      Collection<? extends T> collection = this.b.a(typeTestIn.m_142225_());
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

   public boolean a() {
      return this.b.isEmpty();
   }

   public Stream<T> b() {
      return this.b.stream();
   }

   public C_141300_ c() {
      return this.c;
   }

   public C_141300_ a(C_141300_ visibilityIn) {
      C_141300_ visibility = this.c;
      this.c = visibilityIn;
      return visibility;
   }

   @C_140994_
   public int d() {
      return this.b.size();
   }

   public List<T> getEntityList() {
      return this.b.getValues();
   }

   public static EntitySectionStorage getSectionStorage(C_141298_ tesm) {
      return tesm.d;
   }
}
