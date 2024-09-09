import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.src.C_203206_;
import net.minecraft.src.C_203207_;
import net.minecraft.src.C_204_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_50_;
import net.minecraft.src.C_51_;
import net.minecraft.src.C_69_;
import net.minecraft.src.C_74_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_82_;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class ReloadableResourceManager implements C_77_, AutoCloseable {
   private static final Logger a = LogUtils.getLogger();
   private C_203206_ c;
   private final List<C_69_> d = Lists.newArrayList();
   private final C_51_ e;

   public ReloadableResourceManager(C_51_ p_i203819_1_) {
      this.e = p_i203819_1_;
      this.c = new C_203207_(p_i203819_1_, List.of());
   }

   public void close() {
      this.c.close();
   }

   public void a(C_69_ listenerIn) {
      this.d.add(listenerIn);
   }

   public C_74_ a(Executor p_142463_1_, Executor p_142463_2_, CompletableFuture<C_204_> p_142463_3_, List<C_50_> p_142463_4_) {
      a.info("Reloading ResourceManager: {}", LogUtils.defer(() -> p_142463_4_.stream().map(C_50_::m_5542_).collect(Collectors.joining(", "))));
      this.c.close();
      this.c = new C_203207_(this.e, p_142463_4_);
      if (C_3391_.m_91087_().m_91098_() == this) {
         TextureUtils.resourcesPreReload(this);
      }

      return C_82_.m_203834_(this.c, this.d, p_142463_1_, p_142463_2_, p_142463_3_, a.isDebugEnabled());
   }

   public Optional<C_76_> getResource(ResourceLocation locIn) {
      return this.c.getResource(locIn);
   }

   public Set<String> m_7187_() {
      return this.c.m_7187_();
   }

   public List<C_76_> a(ResourceLocation locationIn) {
      return this.c.a(locationIn);
   }

   public Map<ResourceLocation, C_76_> m_214159_(String prefixIn, Predicate<ResourceLocation> checkIn) {
      return this.c.m_214159_(prefixIn, checkIn);
   }

   public Map<ResourceLocation, List<C_76_>> m_214160_(String prefixIn, Predicate<ResourceLocation> checkIn) {
      return this.c.m_214160_(prefixIn, checkIn);
   }

   public Stream<C_50_> m_7536_() {
      return this.c.m_7536_();
   }

   public void registerReloadListenerIfNotPresent(C_69_ listener) {
      if (!this.d.contains(listener)) {
         this.a(listener);
      }
   }
}
