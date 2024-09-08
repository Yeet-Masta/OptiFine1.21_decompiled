package net.minecraft.client.renderer.texture;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.gui.screens.AddRealmPopupScreen;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.PreparableReloadListener.PreparationBarrier;
import net.minecraft.util.profiling.ProfilerFiller;
import net.optifine.Config;
import net.optifine.EmissiveTextures;
import net.optifine.RandomEntities;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.ShadersTex;
import org.slf4j.Logger;

public class TextureManager implements PreparableReloadListener, Tickable, AutoCloseable {
   private static final Logger f_118467_ = LogUtils.getLogger();
   public static final ResourceLocation f_118466_ = ResourceLocation.m_340282_("");
   private final Map<ResourceLocation, AbstractTexture> f_118468_ = Maps.newHashMap();
   private final Set<Tickable> f_118469_ = Sets.newHashSet();
   private final Map<String, Integer> f_118470_ = Maps.newHashMap();
   private final ResourceManager f_118471_;
   private Int2ObjectMap<AbstractTexture> mapTexturesById = new Int2ObjectOpenHashMap();
   private AbstractTexture mojangLogoTexture;

   public TextureManager(ResourceManager resourceManager) {
      this.f_118471_ = resourceManager;
   }

   public void m_174784_(ResourceLocation resource) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.m_118519_(resource));
      } else {
         this.m_118519_(resource);
      }
   }

   private void m_118519_(ResourceLocation resource) {
      AbstractTexture abstracttexture = (AbstractTexture)this.f_118468_.get(resource);
      if (abstracttexture == null) {
         abstracttexture = new SimpleTexture(resource);
         this.m_118495_(resource, abstracttexture);
      }

      if (Config.isShaders()) {
         ShadersTex.bindTexture(abstracttexture);
      } else {
         abstracttexture.m_117966_();
      }
   }

   public void m_118495_(ResourceLocation textureLocation, AbstractTexture textureObj) {
      if (Reflector.MinecraftForge.exists() && this.mojangLogoTexture == null && textureLocation.equals(LoadingOverlay.f_96160_)) {
         f_118467_.info("Keep logo texture for ForgeLoadingOverlay: " + textureObj);
         this.mojangLogoTexture = textureObj;
      }

      textureObj = this.m_118515_(textureLocation, textureObj);
      AbstractTexture abstracttexture = (AbstractTexture)this.f_118468_.put(textureLocation, textureObj);
      if (abstracttexture != textureObj) {
         if (abstracttexture != null && abstracttexture != MissingTextureAtlasSprite.m_118080_() && abstracttexture != this.mojangLogoTexture) {
            this.m_118508_(textureLocation, abstracttexture);
         }

         if (textureObj instanceof Tickable) {
            this.f_118469_.add((Tickable)textureObj);
         }
      }

      int textureId = textureObj.m_117963_();
      if (textureId > 0) {
         this.mapTexturesById.put(textureId, textureObj);
      }
   }

   private void m_118508_(ResourceLocation locationIn, AbstractTexture textureIn) {
      if (textureIn != MissingTextureAtlasSprite.m_118080_()) {
         this.f_118469_.remove(textureIn);

         try {
            textureIn.close();
         } catch (Exception var4) {
            f_118467_.warn("Failed to close texture {}", locationIn, var4);
         }
      }

      textureIn.m_117964_();
   }

   private AbstractTexture m_118515_(ResourceLocation locationIn, AbstractTexture textureIn) {
      try {
         textureIn.m_6704_(this.f_118471_);
         return textureIn;
      } catch (IOException var6) {
         if (locationIn != f_118466_) {
            f_118467_.warn("Failed to load texture: {}", locationIn);
            f_118467_.warn(var6.getClass().getName() + ": " + var6.getMessage());
         }

         return MissingTextureAtlasSprite.m_118080_();
      } catch (Throwable var7) {
         CrashReport crashreport = CrashReport.m_127521_(var7, "Registering texture");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Resource location being registered");
         crashreportcategory.m_128159_("Resource location", locationIn);
         crashreportcategory.m_128165_("Texture object class", () -> textureIn.getClass().getName());
         throw new ReportedException(crashreport);
      }
   }

   public AbstractTexture m_118506_(ResourceLocation textureLocation) {
      AbstractTexture abstracttexture = (AbstractTexture)this.f_118468_.get(textureLocation);
      if (abstracttexture == null) {
         abstracttexture = new SimpleTexture(textureLocation);
         this.m_118495_(textureLocation, abstracttexture);
      }

      return abstracttexture;
   }

   public AbstractTexture m_174786_(ResourceLocation textureLocation, AbstractTexture textureDefault) {
      return (AbstractTexture)this.f_118468_.getOrDefault(textureLocation, textureDefault);
   }

   public ResourceLocation m_118490_(String name, DynamicTexture texture) {
      Integer integer = (Integer)this.f_118470_.get(name);
      if (integer == null) {
         integer = 1;
      } else {
         integer = integer + 1;
      }

      this.f_118470_.put(name, integer);
      ResourceLocation resourcelocation = ResourceLocation.m_340282_(String.format(Locale.ROOT, "dynamic/%s_%d", name, integer));
      this.m_118495_(resourcelocation, texture);
      return resourcelocation;
   }

   public CompletableFuture<Void> m_118501_(ResourceLocation textureLocation, Executor executor) {
      if (!this.f_118468_.containsKey(textureLocation)) {
         PreloadedTexture preloadedtexture = new PreloadedTexture(this.f_118471_, textureLocation, executor);
         this.f_118468_.put(textureLocation, preloadedtexture);
         return preloadedtexture.m_118105_().thenRunAsync(() -> this.m_118495_(textureLocation, preloadedtexture), TextureManager::m_118488_);
      } else {
         return CompletableFuture.completedFuture(null);
      }
   }

   private static void m_118488_(Runnable runnableIn) {
      Minecraft.m_91087_().execute(() -> RenderSystem.recordRenderCall(runnableIn::run));
   }

   public void m_7673_() {
      for (Tickable tickable : this.f_118469_) {
         tickable.m_7673_();
      }
   }

   public void m_118513_(ResourceLocation textureLocation) {
      AbstractTexture abstracttexture = (AbstractTexture)this.f_118468_.remove(textureLocation);
      if (abstracttexture != null) {
         this.f_118468_.remove(textureLocation);
         this.m_118508_(textureLocation, abstracttexture);
      }
   }

   public void close() {
      this.f_118468_.forEach(this::m_118508_);
      this.f_118468_.clear();
      this.f_118469_.clear();
      this.f_118470_.clear();
      this.mapTexturesById.clear();
   }

   public CompletableFuture<Void> m_5540_(
      PreparationBarrier stage,
      ResourceManager resourceManager,
      ProfilerFiller preparationsProfiler,
      ProfilerFiller reloadProfiler,
      Executor backgroundExecutor,
      Executor gameExecutor
   ) {
      Config.dbg("*** Reloading textures ***");
      Config.log("Resource packs: " + Config.getResourcePackNames());
      Iterator it = this.f_118468_.keySet().iterator();

      while (it.hasNext()) {
         ResourceLocation loc = (ResourceLocation)it.next();
         String path = loc.m_135815_();
         if (path.startsWith("optifine/") || EmissiveTextures.isEmissive(loc)) {
            AbstractTexture tex = (AbstractTexture)this.f_118468_.get(loc);
            if (tex instanceof AbstractTexture) {
               tex.m_117964_();
            }

            it.remove();
         }
      }

      RandomEntities.update();
      EmissiveTextures.update();
      CompletableFuture<Void> completablefuture = new CompletableFuture();
      TitleScreen.m_96754_(this, backgroundExecutor).thenCompose(stage::m_6769_).thenAcceptAsync(voidIn -> {
         MissingTextureAtlasSprite.m_118080_();
         AddRealmPopupScreen.m_338814_(this.f_118471_);
         Set<Entry<ResourceLocation, AbstractTexture>> entries = new HashSet(this.f_118468_.entrySet());
         Iterator<Entry<ResourceLocation, AbstractTexture>> iterator = entries.iterator();

         while (iterator.hasNext()) {
            Entry<ResourceLocation, AbstractTexture> entry = (Entry<ResourceLocation, AbstractTexture>)iterator.next();
            ResourceLocation resourcelocation = (ResourceLocation)entry.getKey();
            AbstractTexture abstracttexture = (AbstractTexture)entry.getValue();
            if (abstracttexture == MissingTextureAtlasSprite.m_118080_() && !resourcelocation.equals(MissingTextureAtlasSprite.m_118071_())) {
               iterator.remove();
            } else {
               abstracttexture.m_6479_(this, resourceManager, resourcelocation, gameExecutor);
            }
         }

         Minecraft.m_91087_().m_6937_(() -> completablefuture.complete(null));
      }, runnableIn -> RenderSystem.recordRenderCall(runnableIn::run));
      return completablefuture;
   }

   public void m_276085_(Path pathIn) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.m_276083_(pathIn));
      } else {
         this.m_276083_(pathIn);
      }
   }

   private void m_276083_(Path pathIn) {
      try {
         Files.createDirectories(pathIn);
      } catch (IOException var3) {
         f_118467_.error("Failed to create directory {}", pathIn, var3);
         return;
      }

      this.f_118468_.forEach((locIn, texIn) -> {
         if (texIn instanceof Dumpable dumpable) {
            try {
               dumpable.m_276079_(locIn, pathIn);
            } catch (IOException var5) {
               f_118467_.error("Failed to dump texture {}", locIn, var5);
            }
         }
      });
   }

   public AbstractTexture getTextureById(int id) {
      AbstractTexture tex = (AbstractTexture)this.mapTexturesById.get(id);
      if (tex != null && tex.m_117963_() != id) {
         this.mapTexturesById.remove(id);
         this.mapTexturesById.put(tex.m_117963_(), tex);
         tex = null;
      }

      return tex;
   }

   public Collection<AbstractTexture> getTextures() {
      return this.f_118468_.values();
   }

   public Collection<ResourceLocation> getTextureLocations() {
      return this.f_118468_.keySet();
   }
}
