package net.minecraft.server.packs.resources;

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
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.Unit;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class ReloadableResourceManager implements ResourceManager, AutoCloseable {
   private static final Logger f_203814_ = LogUtils.getLogger();
   private CloseableResourceManager f_203815_;
   private final List<PreparableReloadListener> f_203816_ = Lists.newArrayList();
   private final PackType f_203817_;

   public ReloadableResourceManager(PackType p_i203819_1_) {
      this.f_203817_ = p_i203819_1_;
      this.f_203815_ = new MultiPackResourceManager(p_i203819_1_, List.of());
   }

   public void close() {
      this.f_203815_.close();
   }

   public void m_7217_(PreparableReloadListener listenerIn) {
      this.f_203816_.add(listenerIn);
   }

   public ReloadInstance m_142463_(Executor p_142463_1_, Executor p_142463_2_, CompletableFuture<Unit> p_142463_3_, List<PackResources> p_142463_4_) {
      f_203814_.info("Reloading ResourceManager: {}", LogUtils.defer(() -> p_142463_4_.stream().map(PackResources::m_5542_).collect(Collectors.joining(", "))));
      this.f_203815_.close();
      this.f_203815_ = new MultiPackResourceManager(this.f_203817_, p_142463_4_);
      if (Minecraft.m_91087_().m_91098_() == this) {
         TextureUtils.resourcesPreReload(this);
      }

      return SimpleReloadInstance.m_203834_(this.f_203815_, this.f_203816_, p_142463_1_, p_142463_2_, p_142463_3_, f_203814_.isDebugEnabled());
   }

   public Optional<Resource> m_213713_(net.minecraft.resources.ResourceLocation locIn) {
      return this.f_203815_.m_213713_(locIn);
   }

   public Set<String> m_7187_() {
      return this.f_203815_.m_7187_();
   }

   public List<Resource> m_213829_(net.minecraft.resources.ResourceLocation locationIn) {
      return this.f_203815_.m_213829_(locationIn);
   }

   public Map<net.minecraft.resources.ResourceLocation, Resource> m_214159_(String prefixIn, Predicate<net.minecraft.resources.ResourceLocation> checkIn) {
      return this.f_203815_.m_214159_(prefixIn, checkIn);
   }

   public Map<net.minecraft.resources.ResourceLocation, List<Resource>> m_214160_(String prefixIn, Predicate<net.minecraft.resources.ResourceLocation> checkIn) {
      return this.f_203815_.m_214160_(prefixIn, checkIn);
   }

   public Stream<PackResources> m_7536_() {
      return this.f_203815_.m_7536_();
   }

   public void registerReloadListenerIfNotPresent(PreparableReloadListener listener) {
      if (!this.f_203816_.contains(listener)) {
         this.m_7217_(listener);
      }
   }
}
