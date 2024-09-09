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
import net.minecraft.src.C_276066_;
import net.minecraft.src.C_336481_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_442_;
import net.minecraft.src.C_4475_;
import net.minecraft.src.C_4491_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_69_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_69_.C_70_;
import net.optifine.Config;
import net.optifine.EmissiveTextures;
import net.optifine.RandomEntities;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.ShadersTex;
import org.slf4j.Logger;

public class TextureManager implements C_69_, C_4491_, AutoCloseable {
   private static final Logger b = LogUtils.getLogger();
   public static final ResourceLocation a = ResourceLocation.b("");
   private final Map<ResourceLocation, AbstractTexture> c = Maps.newHashMap();
   private final Set<C_4491_> d = Sets.newHashSet();
   private final Map<String, Integer> e = Maps.newHashMap();
   private final C_77_ f;
   private Int2ObjectMap<AbstractTexture> mapTexturesById = new Int2ObjectOpenHashMap();
   private AbstractTexture mojangLogoTexture;

   public TextureManager(C_77_ resourceManager) {
      this.f = resourceManager;
   }

   public void a(ResourceLocation resource) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.d(resource));
      } else {
         this.d(resource);
      }
   }

   private void d(ResourceLocation resource) {
      AbstractTexture abstracttexture = (AbstractTexture)this.c.get(resource);
      if (abstracttexture == null) {
         abstracttexture = new SimpleTexture(resource);
         this.a(resource, abstracttexture);
      }

      if (Config.isShaders()) {
         ShadersTex.bindTexture(abstracttexture);
      } else {
         abstracttexture.c();
      }
   }

   public void a(ResourceLocation textureLocation, AbstractTexture textureObj) {
      if (Reflector.MinecraftForge.exists() && this.mojangLogoTexture == null && textureLocation.equals(LoadingOverlay.c)) {
         b.info("Keep logo texture for ForgeLoadingOverlay: " + textureObj);
         this.mojangLogoTexture = textureObj;
      }

      textureObj = this.d(textureLocation, textureObj);
      AbstractTexture abstracttexture = (AbstractTexture)this.c.put(textureLocation, textureObj);
      if (abstracttexture != textureObj) {
         if (abstracttexture != null && abstracttexture != MissingTextureAtlasSprite.c() && abstracttexture != this.mojangLogoTexture) {
            this.c(textureLocation, abstracttexture);
         }

         if (textureObj instanceof C_4491_) {
            this.d.add((C_4491_)textureObj);
         }
      }

      int textureId = textureObj.a();
      if (textureId > 0) {
         this.mapTexturesById.put(textureId, textureObj);
      }
   }

   private void c(ResourceLocation locationIn, AbstractTexture textureIn) {
      if (textureIn != MissingTextureAtlasSprite.c()) {
         this.d.remove(textureIn);

         try {
            textureIn.close();
         } catch (Exception var4) {
            b.warn("Failed to close texture {}", locationIn, var4);
         }
      }

      textureIn.b();
   }

   private AbstractTexture d(ResourceLocation locationIn, AbstractTexture textureIn) {
      try {
         textureIn.a(this.f);
         return textureIn;
      } catch (IOException var6) {
         if (locationIn != a) {
            b.warn("Failed to load texture: {}", locationIn);
            b.warn(var6.getClass().getName() + ": " + var6.getMessage());
         }

         return MissingTextureAtlasSprite.c();
      } catch (Throwable var7) {
         CrashReport crashreport = CrashReport.a(var7, "Registering texture");
         C_4909_ crashreportcategory = crashreport.a("Resource location being registered");
         crashreportcategory.m_128159_("Resource location", locationIn);
         crashreportcategory.m_128165_("Texture object class", () -> textureIn.getClass().getName());
         throw new C_5204_(crashreport);
      }
   }

   public AbstractTexture b(ResourceLocation textureLocation) {
      AbstractTexture abstracttexture = (AbstractTexture)this.c.get(textureLocation);
      if (abstracttexture == null) {
         abstracttexture = new SimpleTexture(textureLocation);
         this.a(textureLocation, abstracttexture);
      }

      return abstracttexture;
   }

   public AbstractTexture b(ResourceLocation textureLocation, AbstractTexture textureDefault) {
      return (AbstractTexture)this.c.getOrDefault(textureLocation, textureDefault);
   }

   public ResourceLocation a(String name, DynamicTexture texture) {
      Integer integer = (Integer)this.e.get(name);
      if (integer == null) {
         integer = 1;
      } else {
         integer = integer + 1;
      }

      this.e.put(name, integer);
      ResourceLocation resourcelocation = ResourceLocation.b(String.format(Locale.ROOT, "dynamic/%s_%d", name, integer));
      this.a(resourcelocation, texture);
      return resourcelocation;
   }

   public CompletableFuture<Void> a(ResourceLocation textureLocation, Executor executor) {
      if (!this.c.containsKey(textureLocation)) {
         C_4475_ preloadedtexture = new C_4475_(this.f, textureLocation, executor);
         this.c.put(textureLocation, preloadedtexture);
         return preloadedtexture.m_118105_().thenRunAsync(() -> this.a(textureLocation, preloadedtexture), TextureManager::a);
      } else {
         return CompletableFuture.completedFuture(null);
      }
   }

   private static void a(Runnable runnableIn) {
      C_3391_.m_91087_().execute(() -> RenderSystem.recordRenderCall(runnableIn::run));
   }

   public void m_7673_() {
      for (C_4491_ tickable : this.d) {
         tickable.m_7673_();
      }
   }

   public void c(ResourceLocation textureLocation) {
      AbstractTexture abstracttexture = (AbstractTexture)this.c.remove(textureLocation);
      if (abstracttexture != null) {
         this.c.remove(textureLocation);
         this.c(textureLocation, abstracttexture);
      }
   }

   public void close() {
      this.c.forEach(this::c);
      this.c.clear();
      this.d.clear();
      this.e.clear();
      this.mapTexturesById.clear();
   }

   public CompletableFuture<Void> m_5540_(
      C_70_ stage, C_77_ resourceManager, C_442_ preparationsProfiler, C_442_ reloadProfiler, Executor backgroundExecutor, Executor gameExecutor
   ) {
      Config.dbg("*** Reloading textures ***");
      Config.log("Resource packs: " + Config.getResourcePackNames());
      Iterator it = this.c.keySet().iterator();

      while (it.hasNext()) {
         ResourceLocation loc = (ResourceLocation)it.next();
         String path = loc.a();
         if (path.startsWith("optifine/") || EmissiveTextures.isEmissive(loc)) {
            AbstractTexture tex = (AbstractTexture)this.c.get(loc);
            if (tex instanceof AbstractTexture) {
               tex.b();
            }

            it.remove();
         }
      }

      RandomEntities.update();
      EmissiveTextures.update();
      CompletableFuture<Void> completablefuture = new CompletableFuture();
      TitleScreen.a(this, backgroundExecutor).thenCompose(stage::m_6769_).thenAcceptAsync(voidIn -> {
         MissingTextureAtlasSprite.c();
         C_336481_.m_338814_(this.f);
         Set<Entry<ResourceLocation, AbstractTexture>> entries = new HashSet(this.c.entrySet());
         Iterator<Entry<ResourceLocation, AbstractTexture>> iterator = entries.iterator();

         while (iterator.hasNext()) {
            Entry<ResourceLocation, AbstractTexture> entry = (Entry<ResourceLocation, AbstractTexture>)iterator.next();
            ResourceLocation resourcelocation = (ResourceLocation)entry.getKey();
            AbstractTexture abstracttexture = (AbstractTexture)entry.getValue();
            if (abstracttexture == MissingTextureAtlasSprite.c() && !resourcelocation.equals(MissingTextureAtlasSprite.b())) {
               iterator.remove();
            } else {
               abstracttexture.a(this, resourceManager, resourcelocation, gameExecutor);
            }
         }

         C_3391_.m_91087_().i(() -> completablefuture.complete(null));
      }, runnableIn -> RenderSystem.recordRenderCall(runnableIn::run));
      return completablefuture;
   }

   public void a(Path pathIn) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.b(pathIn));
      } else {
         this.b(pathIn);
      }
   }

   private void b(Path pathIn) {
      try {
         Files.createDirectories(pathIn);
      } catch (IOException var3) {
         b.error("Failed to create directory {}", pathIn, var3);
         return;
      }

      this.c.forEach((locIn, texIn) -> {
         if (texIn instanceof C_276066_ dumpable) {
            try {
               dumpable.a(locIn, pathIn);
            } catch (IOException var5) {
               b.error("Failed to dump texture {}", locIn, var5);
            }
         }
      });
   }

   public AbstractTexture getTextureById(int id) {
      AbstractTexture tex = (AbstractTexture)this.mapTexturesById.get(id);
      if (tex != null && tex.a() != id) {
         this.mapTexturesById.remove(id);
         this.mapTexturesById.put(tex.a(), tex);
         tex = null;
      }

      return tex;
   }

   public Collection<AbstractTexture> getTextures() {
      return this.c.values();
   }

   public Collection<ResourceLocation> getTextureLocations() {
      return this.c.keySet();
   }
}
