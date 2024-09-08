package net.minecraft.src;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
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
import net.minecraft.src.C_69_.C_70_;
import net.optifine.Config;
import net.optifine.EmissiveTextures;
import net.optifine.RandomEntities;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.ShadersTex;
import org.slf4j.Logger;

public class C_4490_ implements C_69_, C_4491_, AutoCloseable {
   private static final Logger f_118467_ = LogUtils.getLogger();
   public static final C_5265_ f_118466_ = C_5265_.m_340282_("");
   private final Map<C_5265_, C_4468_> f_118468_ = Maps.newHashMap();
   private final Set<C_4491_> f_118469_ = Sets.newHashSet();
   private final Map<String, Integer> f_118470_ = Maps.newHashMap();
   private final C_77_ f_118471_;
   private Int2ObjectMap<C_4468_> mapTexturesById = new Int2ObjectOpenHashMap();
   private C_4468_ mojangLogoTexture;

   public C_4490_(C_77_ resourceManager) {
      this.f_118471_ = resourceManager;
   }

   public void m_174784_(C_5265_ resource) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.m_118519_(resource));
      } else {
         this.m_118519_(resource);
      }
   }

   private void m_118519_(C_5265_ resource) {
      C_4468_ abstracttexture = (C_4468_)this.f_118468_.get(resource);
      if (abstracttexture == null) {
         abstracttexture = new C_4476_(resource);
         this.m_118495_(resource, abstracttexture);
      }

      if (Config.isShaders()) {
         ShadersTex.bindTexture(abstracttexture);
      } else {
         abstracttexture.m_117966_();
      }
   }

   public void m_118495_(C_5265_ textureLocation, C_4468_ textureObj) {
      if (Reflector.MinecraftForge.exists() && this.mojangLogoTexture == null && textureLocation.equals(C_3565_.f_96160_)) {
         f_118467_.info("Keep logo texture for ForgeLoadingOverlay: " + textureObj);
         this.mojangLogoTexture = textureObj;
      }

      textureObj = this.m_118515_(textureLocation, textureObj);
      C_4468_ abstracttexture = (C_4468_)this.f_118468_.put(textureLocation, textureObj);
      if (abstracttexture != textureObj) {
         if (abstracttexture != null && abstracttexture != C_4473_.m_118080_() && abstracttexture != this.mojangLogoTexture) {
            this.m_118508_(textureLocation, abstracttexture);
         }

         if (textureObj instanceof C_4491_) {
            this.f_118469_.add((C_4491_)textureObj);
         }
      }

      int textureId = textureObj.m_117963_();
      if (textureId > 0) {
         this.mapTexturesById.put(textureId, textureObj);
      }
   }

   private void m_118508_(C_5265_ locationIn, C_4468_ textureIn) {
      if (textureIn != C_4473_.m_118080_()) {
         this.f_118469_.remove(textureIn);

         try {
            textureIn.close();
         } catch (Exception var4) {
            f_118467_.warn("Failed to close texture {}", locationIn, var4);
         }
      }

      textureIn.m_117964_();
   }

   private C_4468_ m_118515_(C_5265_ locationIn, C_4468_ textureIn) {
      try {
         textureIn.m_6704_(this.f_118471_);
         return textureIn;
      } catch (IOException var6) {
         if (locationIn != f_118466_) {
            f_118467_.warn("Failed to load texture: {}", locationIn);
            f_118467_.warn(var6.getClass().getName() + ": " + var6.getMessage());
         }

         return C_4473_.m_118080_();
      } catch (Throwable var7) {
         C_4883_ crashreport = C_4883_.m_127521_(var7, "Registering texture");
         C_4909_ crashreportcategory = crashreport.m_127514_("Resource location being registered");
         crashreportcategory.m_128159_("Resource location", locationIn);
         crashreportcategory.m_128165_("Texture object class", () -> textureIn.getClass().getName());
         throw new C_5204_(crashreport);
      }
   }

   public C_4468_ m_118506_(C_5265_ textureLocation) {
      C_4468_ abstracttexture = (C_4468_)this.f_118468_.get(textureLocation);
      if (abstracttexture == null) {
         abstracttexture = new C_4476_(textureLocation);
         this.m_118495_(textureLocation, abstracttexture);
      }

      return abstracttexture;
   }

   public C_4468_ m_174786_(C_5265_ textureLocation, C_4468_ textureDefault) {
      return (C_4468_)this.f_118468_.getOrDefault(textureLocation, textureDefault);
   }

   public C_5265_ m_118490_(String name, C_4470_ texture) {
      Integer integer = (Integer)this.f_118470_.get(name);
      if (integer == null) {
         integer = 1;
      } else {
         integer = integer + 1;
      }

      this.f_118470_.put(name, integer);
      C_5265_ resourcelocation = C_5265_.m_340282_(String.format(Locale.ROOT, "dynamic/%s_%d", name, integer));
      this.m_118495_(resourcelocation, texture);
      return resourcelocation;
   }

   public CompletableFuture<Void> m_118501_(C_5265_ textureLocation, Executor executor) {
      if (!this.f_118468_.containsKey(textureLocation)) {
         C_4475_ preloadedtexture = new C_4475_(this.f_118471_, textureLocation, executor);
         this.f_118468_.put(textureLocation, preloadedtexture);
         return preloadedtexture.m_118105_().thenRunAsync(() -> this.m_118495_(textureLocation, preloadedtexture), C_4490_::m_118488_);
      } else {
         return CompletableFuture.completedFuture(null);
      }
   }

   private static void m_118488_(Runnable runnableIn) {
      C_3391_.m_91087_().execute(() -> RenderSystem.recordRenderCall(runnableIn::run));
   }

   public void m_7673_() {
      for (C_4491_ tickable : this.f_118469_) {
         tickable.m_7673_();
      }
   }

   public void m_118513_(C_5265_ textureLocation) {
      C_4468_ abstracttexture = (C_4468_)this.f_118468_.remove(textureLocation);
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
      C_70_ stage, C_77_ resourceManager, C_442_ preparationsProfiler, C_442_ reloadProfiler, Executor backgroundExecutor, Executor gameExecutor
   ) {
      Config.dbg("*** Reloading textures ***");
      Config.log("Resource packs: " + Config.getResourcePackNames());
      Iterator it = this.f_118468_.keySet().iterator();

      while (it.hasNext()) {
         C_5265_ loc = (C_5265_)it.next();
         String path = loc.m_135815_();
         if (path.startsWith("optifine/") || EmissiveTextures.isEmissive(loc)) {
            C_4468_ tex = (C_4468_)this.f_118468_.get(loc);
            if (tex instanceof C_4468_) {
               tex.m_117964_();
            }

            it.remove();
         }
      }

      RandomEntities.update();
      EmissiveTextures.update();
      CompletableFuture<Void> completablefuture = new CompletableFuture();
      C_3588_.m_96754_(this, backgroundExecutor).thenCompose(stage::m_6769_).thenAcceptAsync(voidIn -> {
         C_4473_.m_118080_();
         C_336481_.m_338814_(this.f_118471_);
         Set<Entry<C_5265_, C_4468_>> entries = new HashSet(this.f_118468_.entrySet());
         Iterator<Entry<C_5265_, C_4468_>> iterator = entries.iterator();

         while (iterator.hasNext()) {
            Entry<C_5265_, C_4468_> entry = (Entry<C_5265_, C_4468_>)iterator.next();
            C_5265_ resourcelocation = (C_5265_)entry.getKey();
            C_4468_ abstracttexture = (C_4468_)entry.getValue();
            if (abstracttexture == C_4473_.m_118080_() && !resourcelocation.equals(C_4473_.m_118071_())) {
               iterator.remove();
            } else {
               abstracttexture.m_6479_(this, resourceManager, resourcelocation, gameExecutor);
            }
         }

         C_3391_.m_91087_().i(() -> completablefuture.complete(null));
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
         if (texIn instanceof C_276066_ dumpable) {
            try {
               dumpable.m_276079_(locIn, pathIn);
            } catch (IOException var5) {
               f_118467_.error("Failed to dump texture {}", locIn, var5);
            }
         }
      });
   }

   public C_4468_ getTextureById(int id) {
      C_4468_ tex = (C_4468_)this.mapTexturesById.get(id);
      if (tex != null && tex.m_117963_() != id) {
         this.mapTexturesById.remove(id);
         this.mapTexturesById.put(tex.m_117963_(), tex);
         tex = null;
      }

      return tex;
   }

   public Collection<C_4468_> getTextures() {
      return this.f_118468_.values();
   }

   public Collection<C_5265_> getTextureLocations() {
      return this.f_118468_.keySet();
   }
}
