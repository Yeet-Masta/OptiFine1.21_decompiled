package net.minecraft.src;

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
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class C_75_ implements C_77_, AutoCloseable {
   private static final Logger f_203814_ = LogUtils.getLogger();
   private C_203206_ f_203815_;
   private final List f_203816_ = Lists.newArrayList();
   private final C_51_ f_203817_;

   public C_75_(C_51_ p_i203819_1_) {
      this.f_203817_ = p_i203819_1_;
      this.f_203815_ = new C_203207_(p_i203819_1_, List.of());
   }

   public void close() {
      this.f_203815_.close();
   }

   public void m_7217_(C_69_ listenerIn) {
      this.f_203816_.add(listenerIn);
   }

   public C_74_ m_142463_(Executor p_142463_1_, Executor p_142463_2_, CompletableFuture p_142463_3_, List p_142463_4_) {
      f_203814_.info("Reloading ResourceManager: {}", LogUtils.defer(() -> {
         return p_142463_4_.stream().map(C_50_::m_5542_).collect(Collectors.joining(", "));
      }));
      this.f_203815_.close();
      this.f_203815_ = new C_203207_(this.f_203817_, p_142463_4_);
      if (C_3391_.m_91087_().m_91098_() == this) {
         TextureUtils.resourcesPreReload(this);
      }

      return C_82_.m_203834_(this.f_203815_, this.f_203816_, p_142463_1_, p_142463_2_, p_142463_3_, f_203814_.isDebugEnabled());
   }

   public Optional m_213713_(C_5265_ locIn) {
      return this.f_203815_.getResource(locIn);
   }

   public Set m_7187_() {
      return this.f_203815_.a();
   }

   public List m_213829_(C_5265_ locationIn) {
      return this.f_203815_.a(locationIn);
   }

   public Map m_214159_(String prefixIn, Predicate checkIn) {
      return this.f_203815_.b(prefixIn, checkIn);
   }

   public Map m_214160_(String prefixIn, Predicate checkIn) {
      return this.f_203815_.c(prefixIn, checkIn);
   }

   public Stream m_7536_() {
      return this.f_203815_.b();
   }

   public void registerReloadListenerIfNotPresent(C_69_ listener) {
      if (!this.f_203816_.contains(listener)) {
         this.m_7217_(listener);
      }

   }
}
