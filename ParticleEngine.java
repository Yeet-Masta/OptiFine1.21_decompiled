import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.src.C_141436_;
import net.minecraft.src.C_141791_;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_181_;
import net.minecraft.src.C_1879_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_243621_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_276402_;
import net.minecraft.src.C_3040_;
import net.minecraft.src.C_3041_;
import net.minecraft.src.C_3072_;
import net.minecraft.src.C_3953_;
import net.minecraft.src.C_4023_;
import net.minecraft.src.C_4028_;
import net.minecraft.src.C_4029_;
import net.minecraft.src.C_4066_;
import net.minecraft.src.C_4070_;
import net.minecraft.src.C_4085_;
import net.minecraft.src.C_4087_;
import net.minecraft.src.C_4091_;
import net.minecraft.src.C_442_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4756_;
import net.minecraft.src.C_4758_;
import net.minecraft.src.C_4759_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_69_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_141690_.C_141691_;
import net.minecraft.src.C_141694_.C_141695_;
import net.minecraft.src.C_141694_.C_141696_;
import net.minecraft.src.C_141694_.C_141697_;
import net.minecraft.src.C_141694_.C_141698_;
import net.minecraft.src.C_141694_.C_141699_;
import net.minecraft.src.C_141700_.C_141701_;
import net.minecraft.src.C_141708_.C_141709_;
import net.minecraft.src.C_183071_.C_183072_;
import net.minecraft.src.C_213409_.C_213410_;
import net.minecraft.src.C_213411_.C_213412_;
import net.minecraft.src.C_213413_.C_213414_;
import net.minecraft.src.C_213415_.C_213416_;
import net.minecraft.src.C_301921_.C_302007_;
import net.minecraft.src.C_301961_.C_301950_;
import net.minecraft.src.C_302056_.C_302131_;
import net.minecraft.src.C_302074_.C_301941_;
import net.minecraft.src.C_302074_.C_313479_;
import net.minecraft.src.C_302147_.C_301928_;
import net.minecraft.src.C_313322_.C_313804_;
import net.minecraft.src.C_313463_.C_313270_;
import net.minecraft.src.C_313463_.C_313385_;
import net.minecraft.src.C_313463_.C_313776_;
import net.minecraft.src.C_3918_.C_3919_;
import net.minecraft.src.C_3920_.C_3922_;
import net.minecraft.src.C_3927_.C_313333_;
import net.minecraft.src.C_3927_.C_3929_;
import net.minecraft.src.C_3927_.C_3930_;
import net.minecraft.src.C_3927_.C_3931_;
import net.minecraft.src.C_3932_.C_3934_;
import net.minecraft.src.C_3935_.C_3937_;
import net.minecraft.src.C_3938_.C_3940_;
import net.minecraft.src.C_3941_.C_3943_;
import net.minecraft.src.C_3941_.C_3944_;
import net.minecraft.src.C_3945_.C_3947_;
import net.minecraft.src.C_3945_.C_3948_;
import net.minecraft.src.C_3945_.C_3949_;
import net.minecraft.src.C_3950_.C_3952_;
import net.minecraft.src.C_3973_.C_3975_;
import net.minecraft.src.C_3980_.C_3982_;
import net.minecraft.src.C_3983_.C_3984_;
import net.minecraft.src.C_3985_.C_3987_;
import net.minecraft.src.C_3988_.C_3990_;
import net.minecraft.src.C_3988_.C_3992_;
import net.minecraft.src.C_3988_.C_3993_;
import net.minecraft.src.C_3995_.C_141693_;
import net.minecraft.src.C_3995_.C_3997_;
import net.minecraft.src.C_3998_.C_4000_;
import net.minecraft.src.C_3998_.C_4001_;
import net.minecraft.src.C_4002_.C_4004_;
import net.minecraft.src.C_4005_.C_4007_;
import net.minecraft.src.C_4010_.C_4011_;
import net.minecraft.src.C_4012_.C_4014_;
import net.minecraft.src.C_4015_.C_4017_;
import net.minecraft.src.C_4019_.C_4021_;
import net.minecraft.src.C_4028_.C_271042_;
import net.minecraft.src.C_4036_.C_4038_;
import net.minecraft.src.C_4036_.C_4039_;
import net.minecraft.src.C_4040_.C_4041_;
import net.minecraft.src.C_4042_.C_4044_;
import net.minecraft.src.C_4048_.C_4049_;
import net.minecraft.src.C_4050_.C_213418_;
import net.minecraft.src.C_4050_.C_4052_;
import net.minecraft.src.C_4053_.C_313402_;
import net.minecraft.src.C_4053_.C_4056_;
import net.minecraft.src.C_4053_.C_4058_;
import net.minecraft.src.C_4053_.C_4059_;
import net.minecraft.src.C_4060_.C_4062_;
import net.minecraft.src.C_4063_.C_4065_;
import net.minecraft.src.C_4067_.C_141702_;
import net.minecraft.src.C_4067_.C_4069_;
import net.minecraft.src.C_4070_.C_141706_;
import net.minecraft.src.C_4070_.C_4072_;
import net.minecraft.src.C_4070_.C_4073_;
import net.minecraft.src.C_4070_.C_4074_;
import net.minecraft.src.C_4075_.C_276412_;
import net.minecraft.src.C_4075_.C_4077_;
import net.minecraft.src.C_4075_.C_4078_;
import net.minecraft.src.C_4075_.C_4079_;
import net.minecraft.src.C_4075_.C_4080_;
import net.minecraft.src.C_4085_.C_313417_;
import net.minecraft.src.C_4085_.C_4086_;
import net.minecraft.src.C_4088_.C_4090_;
import net.minecraft.src.C_4092_.C_4094_;
import net.minecraft.src.C_4095_.C_4097_;
import net.minecraft.src.C_4098_.C_4099_;
import net.minecraft.src.C_4100_.C_4101_;
import net.minecraft.src.C_69_.C_70_;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderEnv;
import org.slf4j.Logger;

public class ParticleEngine implements C_69_ {
   private static final Logger b = LogUtils.getLogger();
   private static final C_243621_ c = C_243621_.m_246568_("particles");
   private static final ResourceLocation d = ResourceLocation.b("particles");
   private static final int e = 16384;
   private static final List<C_4029_> f = ImmutableList.of(C_4029_.f_107429_, C_4029_.f_107430_, C_4029_.f_107432_, C_4029_.f_107431_, C_4029_.f_107433_);
   protected ClientLevel a;
   private Map<C_4029_, Queue<Particle>> g = Maps.newIdentityHashMap();
   private final Queue<C_4091_> h = Queues.newArrayDeque();
   private final TextureManager i;
   private final C_212974_ j = C_212974_.m_216327_();
   private final Map<ResourceLocation, C_4028_<?>> k = new HashMap();
   private final Queue<Particle> l = Queues.newArrayDeque();
   private final Map<ResourceLocation, ParticleEngine.b> m = Maps.newHashMap();
   private final TextureAtlas n;
   private final Object2IntOpenHashMap<C_141791_> o = new Object2IntOpenHashMap();
   private RenderEnv renderEnv = new RenderEnv(null, null);

   public ParticleEngine(ClientLevel worldIn, TextureManager rendererIn) {
      if (Reflector.ForgeHooksClient_makeParticleRenderTypeComparator.exists()) {
         Comparator comp = (Comparator)Reflector.ForgeHooksClient_makeParticleRenderTypeComparator.call(f);
         if (comp != null) {
            this.g = Maps.newTreeMap(comp);
         }
      }

      this.n = new TextureAtlas(TextureAtlas.f);
      rendererIn.a(this.n.g(), this.n);
      this.a = worldIn;
      this.i = rendererIn;
      this.e();
   }

   private void e() {
      this.a(C_4759_.f_123792_, C_4000_::new);
      this.a(C_4759_.f_194652_, new C_183072_());
      this.a(C_4759_.f_123794_, new C_4086_());
      this.a(C_4759_.f_123795_, C_3937_::new);
      this.a(C_4759_.f_123774_, C_3934_::new);
      this.a(C_4759_.f_123772_, C_3940_::new);
      this.a(C_4759_.f_123777_, C_3943_::new);
      this.a(C_4759_.f_123778_, C_3944_::new);
      this.a(C_4759_.f_123796_, C_4038_::new);
      this.a(C_4759_.f_123749_, C_4077_::new);
      this.a(C_4759_.f_123797_, C_3949_::new);
      this.a(C_4759_.f_123773_, C_4097_::new);
      this.a(C_4759_.f_123798_, C_3947_::new);
      this.a(C_4759_.f_123799_, C_3952_::new);
      this.a(C_4759_.f_123776_, C_4078_::new);
      this.a(C_4759_.f_123800_, C_3953_::c);
      this.a(C_4759_.f_123801_, C_3953_::d);
      this.a(C_4759_.f_123802_, C_3953_::e);
      this.a(C_4759_.f_123803_, C_3953_::a);
      this.a(C_4759_.f_123804_, C_3953_::b);
      this.a(C_4759_.f_123805_, C_3975_::new);
      this.a(C_4759_.f_175836_, C_141691_::new);
      this.a(C_4759_.f_123806_, C_4058_::new);
      this.a(C_4759_.f_123807_, new C_4017_());
      this.a(C_4759_.f_123808_, C_3948_::new);
      this.a(C_4759_.f_123809_, C_313776_::new);
      this.a(C_4759_.f_123810_, C_3982_::new);
      this.a(C_4759_.f_123811_, C_313402_::new);
      this.a(C_4759_.f_123812_, new C_4007_());
      this.a(C_4759_.f_123813_, C_4004_::new);
      this.a(C_4759_.f_235902_, C_213416_::new);
      this.a(C_4759_.f_123814_, C_3987_::new);
      this.a(C_4759_.f_302334_, C_301941_::new);
      this.a(C_4759_.f_314220_, C_313479_::new);
      this.a(C_4759_.f_316181_, new C_301950_(3.0, 7, 0));
      this.a(C_4759_.f_315099_, new C_301950_(1.0, 3, 2));
      this.a(C_4759_.f_123815_, C_3993_::new);
      this.a(C_4759_.f_123816_, C_4094_::new);
      this.a(C_4759_.f_123744_, C_3997_::new);
      this.a(C_4759_.f_314307_, C_4058_::new);
      this.a(C_4759_.f_235898_, C_213418_::new);
      this.a(C_4759_.f_235899_, C_213410_::new);
      this.a(C_4759_.f_235900_, C_213412_::new);
      this.a(C_4759_.f_123746_, C_4052_::new);
      this.a(C_4759_.f_123745_, C_3997_::new);
      this.a(C_4759_.f_123747_, C_3990_::new);
      this.a(C_4759_.f_123748_, C_4079_::new);
      this.a(C_4759_.f_123750_, C_4001_::new);
      this.a(C_4759_.f_123751_, C_4056_::new);
      this.a(C_4759_.f_123752_, new C_3929_());
      this.a(C_4759_.f_123753_, new C_3930_());
      this.a(C_4759_.f_315496_, new C_313333_());
      this.a(C_4759_.f_123754_, new C_3931_());
      this.a(C_4759_.f_123755_, C_4011_::new);
      this.a(C_4759_.f_123756_, C_4014_::new);
      this.a(C_4759_.f_123757_, C_4080_::new);
      this.a(C_4759_.f_123775_, C_313270_::new);
      this.a(C_4759_.f_123758_, C_4021_::new);
      this.a(C_4759_.f_123759_, C_3984_::new);
      this.a(C_4759_.f_123760_, C_4041_::new);
      this.a(C_4759_.f_123761_, C_4099_::new);
      this.a(C_4759_.f_123762_, C_4049_::new);
      this.a(C_4759_.f_302345_, C_302007_::new);
      this.a(C_4759_.f_123763_, C_4039_::new);
      this.a(C_4759_.f_175821_, C_141701_::new);
      this.a(C_4759_.f_123764_, C_4062_::new);
      this.a(C_4759_.f_123766_, C_3922_::new);
      this.a(C_4759_.f_123767_, C_4090_::new);
      this.a(C_4759_.f_123765_, C_4069_::new);
      this.a(C_4759_.f_123768_, C_4073_::new);
      this.a(C_4759_.f_123769_, C_4065_::new);
      this.a(C_4759_.f_123771_, C_4059_::new);
      this.a(C_4759_.f_123779_, C_3953_::f);
      this.a(C_4759_.f_123780_, C_3953_::g);
      this.a(C_4759_.f_123781_, C_3953_::h);
      this.a(C_4759_.f_123782_, C_3953_::m);
      this.a(C_4759_.f_175832_, C_3953_::n);
      this.a(C_4759_.f_175833_, C_141706_::new);
      this.a(C_4759_.f_123783_, C_3919_::new);
      this.a(C_4759_.f_123784_, C_4072_::new);
      this.a(C_4759_.f_123785_, C_4074_::new);
      this.a(C_4759_.f_123786_, C_3953_::o);
      this.a(C_4759_.f_123787_, C_3953_::p);
      this.a(C_4759_.f_123788_, C_3953_::q);
      this.a(C_4759_.f_123789_, C_4044_::new);
      this.a(C_4759_.f_123790_, C_4101_::new);
      this.a(C_4759_.f_175834_, C_141693_::new);
      this.a(C_4759_.f_175824_, C_3953_::i);
      this.a(C_4759_.f_175825_, C_3953_::j);
      this.a(
         C_4759_.f_276452_,
         p_276702_0_ -> (p_276703_1_, p_276703_2_, p_276703_3_, p_276703_5_, p_276703_7_, p_276703_9_, p_276703_11_, p_276703_13_) -> new C_276402_(
                  p_276703_2_, p_276703_3_, p_276703_5_, p_276703_7_, p_276702_0_
               )
      );
      this.a(C_4759_.f_175822_, C_3953_::k);
      this.a(C_4759_.f_175823_, C_3953_::l);
      this.a(C_4759_.f_175820_, C_141709_::new);
      this.a(C_4759_.f_175826_, C_141702_::new);
      this.a(C_4759_.f_175827_, C_141696_::new);
      this.a(C_4759_.f_175828_, C_141699_::new);
      this.a(C_4759_.f_175829_, C_141698_::new);
      this.a(C_4759_.f_175830_, C_141695_::new);
      this.a(C_4759_.f_175831_, C_141697_::new);
      this.a(C_4759_.f_235901_, C_213414_::new);
      this.a(C_4759_.f_276512_, C_276412_::new);
      this.a(C_4759_.f_303068_, C_301928_::new);
      this.a(C_4759_.f_314928_, C_302131_::new);
      this.a(C_4759_.f_314692_, C_302131_::new);
      this.a(C_4759_.f_314380_, C_313385_::new);
      this.a(C_4759_.f_314186_, new C_313417_());
      this.a(C_4759_.f_314966_, C_4058_::new);
      this.a(C_4759_.f_317125_, C_4058_::new);
      this.a(C_4759_.f_314395_, C_313804_::new);
   }

   private <T extends C_4756_> void a(C_4758_<T> particleTypeIn, C_4028_<T> particleFactoryIn) {
      this.k.put(C_256712_.f_257034_.b(particleTypeIn), particleFactoryIn);
   }

   private <T extends C_4756_> void a(C_4758_<T> p_272137_1_, C_271042_<T> p_272137_2_) {
      this.a(
         p_272137_1_,
         (ParticleEngine.c<T>)(p_271560_1_ -> (p_271561_2_, p_271561_3_, p_271561_4_, p_271561_6_, p_271561_8_, p_271561_10_, p_271561_12_, p_271561_14_) -> {
               C_4087_ texturesheetparticle = p_272137_2_.createParticle(
                  p_271561_2_, p_271561_3_, p_271561_4_, p_271561_6_, p_271561_8_, p_271561_10_, p_271561_12_, p_271561_14_
               );
               if (texturesheetparticle != null) {
                  texturesheetparticle.m_108335_(p_271560_1_);
               }

               return texturesheetparticle;
            })
      );
   }

   private <T extends C_4756_> void a(C_4758_<T> particleTypeIn, ParticleEngine.c<T> particleMetaFactoryIn) {
      ParticleEngine.b particleengine$mutablespriteset = new ParticleEngine.b();
      this.m.put(C_256712_.f_257034_.b(particleTypeIn), particleengine$mutablespriteset);
      this.k.put(C_256712_.f_257034_.b(particleTypeIn), particleMetaFactoryIn.create(particleengine$mutablespriteset));
   }

   public CompletableFuture<Void> m_5540_(
      C_70_ stage, C_77_ resourceManager, C_442_ preparationsProfiler, C_442_ reloadProfiler, Executor backgroundExecutor, Executor gameExecutor
   ) {
      CompletableFuture<List<ParticleEngine.a>> completablefuture = CompletableFuture.supplyAsync(() -> c.m_247457_(resourceManager), backgroundExecutor)
         .thenCompose(mapIn -> {
            List<CompletableFuture<ParticleEngine.a>> list = new ArrayList(mapIn.size());
            mapIn.forEach((locIn, resIn) -> {
               ResourceLocation resourcelocation = c.b(locIn);
               list.add(CompletableFuture.supplyAsync(() -> new ParticleEngine.a(resourcelocation, this.a(resourcelocation, resIn)), backgroundExecutor));
            });
            return Util.d(list);
         });
      CompletableFuture<SpriteLoader.a> completablefuture1 = SpriteLoader.a(this.n).a(resourceManager, d, 0, backgroundExecutor).thenCompose(SpriteLoader.a::a);
      return CompletableFuture.allOf(completablefuture1, completablefuture).thenCompose(stage::m_6769_).thenAcceptAsync(voidIn -> {
         this.f();
         reloadProfiler.m_7242_();
         reloadProfiler.m_6180_("upload");
         SpriteLoader.a spriteloader$preparations = (SpriteLoader.a)completablefuture1.join();
         this.n.a(spriteloader$preparations);
         reloadProfiler.m_6182_("bindSpriteSets");
         Set<ResourceLocation> set = new HashSet();
         TextureAtlasSprite textureatlassprite = spriteloader$preparations.e();
         ((List)completablefuture.join()).forEach(defIn -> {
            Optional<List<ResourceLocation>> optional = defIn.b();
            if (!optional.isEmpty()) {
               List<TextureAtlasSprite> list = new ArrayList();

               for (ResourceLocation resourcelocation : (List)optional.get()) {
                  TextureAtlasSprite textureatlassprite1 = (TextureAtlasSprite)spriteloader$preparations.f().get(resourcelocation);
                  if (textureatlassprite1 == null) {
                     set.add(resourcelocation);
                     list.add(textureatlassprite);
                  } else {
                     list.add(textureatlassprite1);
                  }
               }

               if (list.isEmpty()) {
                  list.add(textureatlassprite);
               }

               ((ParticleEngine.b)this.m.get(defIn.a())).a(list);
            }
         });
         if (!set.isEmpty()) {
            b.warn("Missing particle sprites: {}", set.stream().sorted().map(ResourceLocation::toString).collect(Collectors.joining(",")));
         }

         reloadProfiler.m_7238_();
         reloadProfiler.m_7241_();
      }, gameExecutor);
   }

   public void a() {
      this.n.f();
   }

   private Optional<List<ResourceLocation>> a(ResourceLocation locationIn, C_76_ resourceIn) {
      if (!this.m.containsKey(locationIn)) {
         b.debug("Redundant texture list for particle: {}", locationIn);
         return Optional.empty();
      } else {
         try {
            Reader reader = resourceIn.m_215508_();

            Optional optional;
            try {
               C_4023_ particledescription = C_4023_.m_107285_(C_181_.m_13859_(reader));
               optional = Optional.of(particledescription.m_107282_());
            } catch (Throwable var8) {
               if (reader != null) {
                  try {
                     reader.close();
                  } catch (Throwable var7) {
                     var8.addSuppressed(var7);
                  }
               }

               throw var8;
            }

            if (reader != null) {
               reader.close();
            }

            return optional;
         } catch (IOException var9) {
            throw new IllegalStateException("Failed to load description for particle " + locationIn, var9);
         }
      }
   }

   public void a(C_507_ entityIn, C_4756_ particleData) {
      this.h.add(new C_4091_(this.a, entityIn, particleData));
   }

   public void a(C_507_ entityIn, C_4756_ dataIn, int lifetimeIn) {
      this.h.add(new C_4091_(this.a, entityIn, dataIn, lifetimeIn));
   }

   @Nullable
   public Particle a(C_4756_ particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      Particle particle = this.b(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
      if (particle != null) {
         this.a(particle);
         return particle;
      } else {
         return null;
      }
   }

   @Nullable
   private <T extends C_4756_> Particle b(T particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      C_4028_<T> particleprovider = (C_4028_<T>)this.k.get(C_256712_.f_257034_.b(particleData.m_6012_()));
      return particleprovider == null ? null : particleprovider.createParticle(particleData, this.a, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void a(Particle effect) {
      if (effect != null) {
         if (!(effect instanceof C_3992_) || Config.isFireworkParticles()) {
            Optional<C_141791_> optional = effect.o();
            if (optional.isPresent()) {
               if (this.a((C_141791_)optional.get())) {
                  this.l.add(effect);
                  this.a((C_141791_)optional.get(), 1);
               }
            } else {
               this.l.add(effect);
            }
         }
      }
   }

   public void b() {
      this.g.forEach((typeIn, listIn) -> {
         this.a.m_46473_().m_6180_(typeIn.toString());
         this.a(listIn);
         this.a.m_46473_().m_7238_();
      });
      if (!this.h.isEmpty()) {
         List<C_4091_> list = Lists.newArrayList();

         for (C_4091_ trackingemitter : this.h) {
            trackingemitter.m_5989_();
            if (!trackingemitter.m()) {
               list.add(trackingemitter);
            }
         }

         this.h.removeAll(list);
      }

      Particle particle;
      if (!this.l.isEmpty()) {
         while ((particle = (Particle)this.l.poll()) != null) {
            Queue<Particle> queue = (Queue<Particle>)this.g.computeIfAbsent(particle.b(), renderTypeIn -> EvictingQueue.create(16384));
            queue.add(particle);
         }
      }
   }

   private void a(Collection<Particle> particlesIn) {
      if (!particlesIn.isEmpty()) {
         long timeStartMs = System.currentTimeMillis();
         int countLeft = particlesIn.size();
         Iterator<Particle> iterator = particlesIn.iterator();

         while (iterator.hasNext()) {
            Particle particle = (Particle)iterator.next();
            this.b(particle);
            if (!particle.m()) {
               particle.o().ifPresent(groupIn -> this.a(groupIn, -1));
               iterator.remove();
            }

            countLeft--;
            if (System.currentTimeMillis() > timeStartMs + 20L) {
               break;
            }
         }

         if (countLeft > 0) {
            int countToRemove = countLeft;

            for (Iterator it = particlesIn.iterator(); it.hasNext() && countToRemove > 0; countToRemove--) {
               Particle particlex = (Particle)it.next();
               particlex.k();
               it.remove();
            }
         }
      }
   }

   private void a(C_141791_ groupIn, int countIn) {
      this.o.addTo(groupIn, countIn);
   }

   private void b(Particle particle) {
      try {
         particle.a();
      } catch (Throwable var5) {
         CrashReport crashreport = CrashReport.a(var5, "Ticking Particle");
         C_4909_ crashreportcategory = crashreport.a("Particle being ticked");
         crashreportcategory.m_128165_("Particle", particle::toString);
         crashreportcategory.m_128165_("Particle Type", particle.b()::toString);
         throw new C_5204_(crashreport);
      }
   }

   public void a(LightTexture lightTextureIn, Camera cameraIn, float partialTicks) {
      this.renderParticles(lightTextureIn, cameraIn, partialTicks, null);
   }

   public void renderParticles(LightTexture lightTextureIn, Camera cameraIn, float partialTicks, Frustum clippingHelper) {
      lightTextureIn.c();
      RenderSystem.enableDepthTest();
      RenderSystem.activeTexture(33986);
      RenderSystem.activeTexture(33984);
      C_141436_ cameraFogType = cameraIn.k();
      boolean isEyeInWater = cameraFogType == C_141436_.WATER;
      Collection<C_4029_> renderTypes = f;
      if (Reflector.ForgeHooksClient.exists()) {
         renderTypes = this.g.keySet();
      }

      for (C_4029_ particlerendertype : renderTypes) {
         if (particlerendertype != C_4029_.f_107434_) {
            Queue<Particle> queue = (Queue<Particle>)this.g.get(particlerendertype);
            if (queue != null && !queue.isEmpty()) {
               RenderSystem.setShader(GameRenderer::s);
               Tesselator tesselator = Tesselator.b();
               BufferBuilder bufferbuilder = particlerendertype.a(tesselator, this.i);
               if (bufferbuilder != null) {
                  for (Particle particle : queue) {
                     if ((clippingHelper == null || !particle.shouldCull() || clippingHelper.a(particle.n()))
                        && (isEyeInWater || !(particle instanceof C_4070_) || particle.j != 0.0 || particle.k != 0.0 || particle.l != 0.0)) {
                        try {
                           particle.a(bufferbuilder, cameraIn, partialTicks);
                        } catch (Throwable var18) {
                           CrashReport crashreport = CrashReport.a(var18, "Rendering Particle");
                           C_4909_ crashreportcategory = crashreport.a("Particle being rendered");
                           crashreportcategory.m_128165_("Particle", particle::toString);
                           crashreportcategory.m_128165_("Particle Type", particlerendertype::toString);
                           throw new C_5204_(crashreport);
                        }
                     }
                  }

                  MeshData meshdata = bufferbuilder.a();
                  if (meshdata != null) {
                     BufferUploader.a(meshdata);
                  }
               }
            }
         }
      }

      RenderSystem.depthMask(true);
      RenderSystem.disableBlend();
      lightTextureIn.b();
      RenderSystem.enableDepthTest();
      GlStateManager._glUseProgram(0);
   }

   public void a(@Nullable ClientLevel worldIn) {
      this.a = worldIn;
      this.f();
      this.h.clear();
   }

   public void a(C_4675_ pos, BlockState state) {
      boolean forgeAddDestroyEffects = false;
      IClientBlockExtensions cbe = IClientBlockExtensions.of(state);
      if (cbe != null) {
         forgeAddDestroyEffects = cbe.addDestroyEffects(state, this.a, pos, this);
      }

      if (!state.m_60795_() && state.m_295777_() && !forgeAddDestroyEffects) {
         C_3072_ voxelshape = state.m_60808_(this.a, pos);
         double d0 = 0.25;
         voxelshape.m_83286_(
            (p_172270_3_, p_172270_5_, p_172270_7_, p_172270_9_, p_172270_11_, p_172270_13_) -> {
               double d1 = Math.min(1.0, p_172270_9_ - p_172270_3_);
               double d2 = Math.min(1.0, p_172270_11_ - p_172270_5_);
               double d3 = Math.min(1.0, p_172270_13_ - p_172270_7_);
               int i = Math.max(2, Mth.c(d1 / 0.25));
               int j = Math.max(2, Mth.c(d2 / 0.25));
               int k = Math.max(2, Mth.c(d3 / 0.25));

               for (int l = 0; l < i; l++) {
                  for (int i1 = 0; i1 < j; i1++) {
                     for (int j1 = 0; j1 < k; j1++) {
                        double d4 = ((double)l + 0.5) / (double)i;
                        double d5 = ((double)i1 + 0.5) / (double)j;
                        double d6 = ((double)j1 + 0.5) / (double)k;
                        double d7 = d4 * d1 + p_172270_3_;
                        double d8 = d5 * d2 + p_172270_5_;
                        double d9 = d6 * d3 + p_172270_7_;
                        Particle tp = new C_4085_(
                           this.a,
                           (double)pos.m_123341_() + d7,
                           (double)pos.m_123342_() + d8,
                           (double)pos.m_123343_() + d9,
                           d4 - 0.5,
                           d5 - 0.5,
                           d6 - 0.5,
                           state,
                           pos
                        );
                        if (Reflector.TerrainParticle_updateSprite.exists()) {
                           Reflector.call(tp, Reflector.TerrainParticle_updateSprite, state, pos);
                        }

                        if (Config.isCustomColors()) {
                           updateTerrainParticleColor(tp, state, this.a, pos, this.renderEnv);
                        }

                        this.a(tp);
                     }
                  }
               }
            }
         );
      }
   }

   public void a(C_4675_ pos, Direction side) {
      BlockState blockstate = this.a.a_(pos);
      if (blockstate.m_60799_() != C_1879_.INVISIBLE && blockstate.m_295777_()) {
         int i = pos.m_123341_();
         int j = pos.m_123342_();
         int k = pos.m_123343_();
         float f = 0.1F;
         C_3040_ aabb = blockstate.m_60808_(this.a, pos).m_83215_();
         double d0 = (double)i + this.j.m_188500_() * (aabb.f_82291_ - aabb.f_82288_ - 0.2F) + 0.1F + aabb.f_82288_;
         double d1 = (double)j + this.j.m_188500_() * (aabb.f_82292_ - aabb.f_82289_ - 0.2F) + 0.1F + aabb.f_82289_;
         double d2 = (double)k + this.j.m_188500_() * (aabb.f_82293_ - aabb.f_82290_ - 0.2F) + 0.1F + aabb.f_82290_;
         if (side == Direction.a) {
            d1 = (double)j + aabb.f_82289_ - 0.1F;
         }

         if (side == Direction.b) {
            d1 = (double)j + aabb.f_82292_ + 0.1F;
         }

         if (side == Direction.c) {
            d2 = (double)k + aabb.f_82290_ - 0.1F;
         }

         if (side == Direction.d) {
            d2 = (double)k + aabb.f_82293_ + 0.1F;
         }

         if (side == Direction.e) {
            d0 = (double)i + aabb.f_82288_ - 0.1F;
         }

         if (side == Direction.f) {
            d0 = (double)i + aabb.f_82291_ + 0.1F;
         }

         Particle tp = new C_4085_(this.a, d0, d1, d2, 0.0, 0.0, 0.0, blockstate, pos).c(0.2F).d(0.6F);
         if (Reflector.TerrainParticle_updateSprite.exists()) {
            Reflector.call(tp, Reflector.TerrainParticle_updateSprite, blockstate, pos);
         }

         if (Config.isCustomColors()) {
            updateTerrainParticleColor(tp, blockstate, this.a, pos, this.renderEnv);
         }

         this.a(tp);
      }
   }

   public String d() {
      return String.valueOf(this.g.values().stream().mapToInt(Collection::size).sum());
   }

   private boolean a(C_141791_ groupIn) {
      return this.o.getInt(groupIn) < groupIn.m_175819_();
   }

   private void f() {
      this.g.clear();
      this.l.clear();
      this.h.clear();
      this.o.clear();
   }

   private boolean reuseBarrierParticle(Particle entityfx, Queue<Particle> deque) {
      for (Particle var4 : deque) {
         ;
      }

      return false;
   }

   public static void updateTerrainParticleColor(Particle particle, BlockState state, C_1557_ world, C_4675_ pos, RenderEnv renderEnv) {
      renderEnv.reset(state, pos);
      int col = CustomColors.getColorMultiplier(true, state, world, pos, renderEnv);
      if (col != -1) {
         particle.v = 0.6F * (float)(col >> 16 & 0xFF) / 255.0F;
         particle.w = 0.6F * (float)(col >> 8 & 0xFF) / 255.0F;
         particle.x = 0.6F * (float)(col & 0xFF) / 255.0F;
      }
   }

   public int getCountParticles() {
      int sum = 0;

      for (Queue queue : this.g.values()) {
         sum += queue.size();
      }

      return sum;
   }

   public void addBlockHitEffects(C_4675_ pos, C_3041_ target) {
      BlockState state = this.a.a_(pos);
      if (!IClientBlockExtensions.of(state).addHitEffects(state, this.a, target, this)) {
         this.a(pos, target.b());
      }
   }

   static record a(ResourceLocation a, Optional<List<ResourceLocation>> b) {
   }

   static class b implements C_4066_ {
      private List<TextureAtlasSprite> a;

      public TextureAtlasSprite a(int particleAge, int particleMaxAge) {
         return (TextureAtlasSprite)this.a.get(particleAge * (this.a.size() - 1) / particleMaxAge);
      }

      public TextureAtlasSprite a(C_212974_ randomIn) {
         return (TextureAtlasSprite)this.a.get(randomIn.m_188503_(this.a.size()));
      }

      public void a(List<TextureAtlasSprite> spritesIn) {
         this.a = ImmutableList.copyOf(spritesIn);
      }
   }

   @FunctionalInterface
   interface c<T extends C_4756_> {
      C_4028_<T> create(C_4066_ var1);
   }
}
