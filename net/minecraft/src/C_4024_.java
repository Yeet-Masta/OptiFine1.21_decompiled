package net.minecraft.src;

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
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderEnv;
import org.slf4j.Logger;

public class C_4024_ implements C_69_ {
   private static final Logger f_243727_ = LogUtils.getLogger();
   private static final C_243621_ f_243929_ = C_243621_.m_246568_("particles");
   private static final C_5265_ f_260634_ = C_5265_.m_340282_("particles");
   private static final int f_172264_ = 16384;
   private static final List f_107288_;
   protected C_3899_ f_107287_;
   private Map f_107289_ = Maps.newIdentityHashMap();
   private final Queue f_107290_ = Queues.newArrayDeque();
   private final C_4490_ f_107291_;
   private final C_212974_ f_107292_ = C_212974_.m_216327_();
   private final Map f_107293_ = new HashMap();
   private final Queue f_107294_ = Queues.newArrayDeque();
   private final Map f_107295_ = Maps.newHashMap();
   private final C_4484_ f_107296_;
   private final Object2IntOpenHashMap f_172265_ = new Object2IntOpenHashMap();
   private RenderEnv renderEnv = new RenderEnv((C_2064_)null, (C_4675_)null);

   public C_4024_(C_3899_ worldIn, C_4490_ rendererIn) {
      if (Reflector.ForgeHooksClient_makeParticleRenderTypeComparator.exists()) {
         Comparator comp = (Comparator)Reflector.ForgeHooksClient_makeParticleRenderTypeComparator.call((Object)f_107288_);
         if (comp != null) {
            this.f_107289_ = Maps.newTreeMap(comp);
         }
      }

      this.f_107296_ = new C_4484_(C_4484_.f_118260_);
      rendererIn.m_118495_(this.f_107296_.m_118330_(), this.f_107296_);
      this.f_107287_ = worldIn;
      this.f_107291_ = rendererIn;
      this.m_107404_();
   }

   private void m_107404_() {
      this.m_107378_(C_4759_.f_123792_, C_3998_.C_4000_::new);
      this.m_107381_(C_4759_.f_194652_, new C_183071_.C_183072_());
      this.m_107381_(C_4759_.f_123794_, new C_4085_.C_4086_());
      this.m_107378_(C_4759_.f_123795_, C_3935_.C_3937_::new);
      this.m_107378_(C_4759_.f_123774_, C_3932_.C_3934_::new);
      this.m_107378_(C_4759_.f_123772_, C_3938_.C_3940_::new);
      this.m_107378_(C_4759_.f_123777_, C_3941_.C_3943_::new);
      this.m_107378_(C_4759_.f_123778_, C_3941_.C_3944_::new);
      this.m_107378_(C_4759_.f_123796_, C_4036_.C_4038_::new);
      this.m_107378_(C_4759_.f_123749_, C_4075_.C_4077_::new);
      this.m_107378_(C_4759_.f_123797_, C_3945_.C_3949_::new);
      this.m_107378_(C_4759_.f_123773_, C_4095_.C_4097_::new);
      this.m_107378_(C_4759_.f_123798_, C_3945_.C_3947_::new);
      this.m_107378_(C_4759_.f_123799_, C_3950_.C_3952_::new);
      this.m_107378_(C_4759_.f_123776_, C_4075_.C_4078_::new);
      this.m_272137_(C_4759_.f_123800_, C_3953_::m_272109_);
      this.m_272137_(C_4759_.f_123801_, C_3953_::m_272026_);
      this.m_272137_(C_4759_.f_123802_, C_3953_::m_271885_);
      this.m_272137_(C_4759_.f_123803_, C_3953_::m_272020_);
      this.m_272137_(C_4759_.f_123804_, C_3953_::m_271915_);
      this.m_107378_(C_4759_.f_123805_, C_3973_.C_3975_::new);
      this.m_107378_(C_4759_.f_175836_, C_141690_.C_141691_::new);
      this.m_107378_(C_4759_.f_123806_, C_4053_.C_4058_::new);
      this.m_107381_(C_4759_.f_123807_, new C_4015_.C_4017_());
      this.m_107378_(C_4759_.f_123808_, C_3945_.C_3948_::new);
      this.m_107378_(C_4759_.f_123809_, C_313463_.C_313776_::new);
      this.m_107378_(C_4759_.f_123810_, C_3980_.C_3982_::new);
      this.m_107378_(C_4759_.f_123811_, C_4053_.C_313402_::new);
      this.m_107381_(C_4759_.f_123812_, new C_4005_.C_4007_());
      this.m_107378_(C_4759_.f_123813_, C_4002_.C_4004_::new);
      this.m_107378_(C_4759_.f_235902_, C_213415_.C_213416_::new);
      this.m_107378_(C_4759_.f_123814_, C_3985_.C_3987_::new);
      this.m_107378_(C_4759_.f_302334_, C_302074_.C_301941_::new);
      this.m_107378_(C_4759_.f_314220_, C_302074_.C_313479_::new);
      this.m_107381_(C_4759_.f_316181_, new C_301961_.C_301950_(3.0, 7, 0));
      this.m_107381_(C_4759_.f_315099_, new C_301961_.C_301950_(1.0, 3, 2));
      this.m_107378_(C_4759_.f_123815_, C_3988_.C_3993_::new);
      this.m_107378_(C_4759_.f_123816_, C_4092_.C_4094_::new);
      this.m_107378_(C_4759_.f_123744_, C_3995_.C_3997_::new);
      this.m_107378_(C_4759_.f_314307_, C_4053_.C_4058_::new);
      this.m_107378_(C_4759_.f_235898_, C_4050_.C_213418_::new);
      this.m_107378_(C_4759_.f_235899_, C_213409_.C_213410_::new);
      this.m_107378_(C_4759_.f_235900_, C_213411_.C_213412_::new);
      this.m_107378_(C_4759_.f_123746_, C_4050_.C_4052_::new);
      this.m_107378_(C_4759_.f_123745_, C_3995_.C_3997_::new);
      this.m_107378_(C_4759_.f_123747_, C_3988_.C_3990_::new);
      this.m_107378_(C_4759_.f_123748_, C_4075_.C_4079_::new);
      this.m_107378_(C_4759_.f_123750_, C_3998_.C_4001_::new);
      this.m_107378_(C_4759_.f_123751_, C_4053_.C_4056_::new);
      this.m_107381_(C_4759_.f_123752_, new C_3927_.C_3929_());
      this.m_107381_(C_4759_.f_123753_, new C_3927_.C_3930_());
      this.m_107381_(C_4759_.f_315496_, new C_3927_.C_313333_());
      this.m_107381_(C_4759_.f_123754_, new C_3927_.C_3931_());
      this.m_107378_(C_4759_.f_123755_, C_4010_.C_4011_::new);
      this.m_107378_(C_4759_.f_123756_, C_4012_.C_4014_::new);
      this.m_107378_(C_4759_.f_123757_, C_4075_.C_4080_::new);
      this.m_107378_(C_4759_.f_123775_, C_313463_.C_313270_::new);
      this.m_107378_(C_4759_.f_123758_, C_4019_.C_4021_::new);
      this.m_107378_(C_4759_.f_123759_, C_3983_.C_3984_::new);
      this.m_107378_(C_4759_.f_123760_, C_4040_.C_4041_::new);
      this.m_107378_(C_4759_.f_123761_, C_4098_.C_4099_::new);
      this.m_107378_(C_4759_.f_123762_, C_4048_.C_4049_::new);
      this.m_107378_(C_4759_.f_302345_, C_301921_.C_302007_::new);
      this.m_107378_(C_4759_.f_123763_, C_4036_.C_4039_::new);
      this.m_107378_(C_4759_.f_175821_, C_141700_.C_141701_::new);
      this.m_107378_(C_4759_.f_123764_, C_4060_.C_4062_::new);
      this.m_107378_(C_4759_.f_123766_, C_3920_.C_3922_::new);
      this.m_107378_(C_4759_.f_123767_, C_4088_.C_4090_::new);
      this.m_107378_(C_4759_.f_123765_, C_4067_.C_4069_::new);
      this.m_107378_(C_4759_.f_123768_, C_4070_.C_4073_::new);
      this.m_107378_(C_4759_.f_123769_, C_4063_.C_4065_::new);
      this.m_107378_(C_4759_.f_123771_, C_4053_.C_4059_::new);
      this.m_272137_(C_4759_.f_123779_, C_3953_::m_272107_);
      this.m_272137_(C_4759_.f_123780_, C_3953_::m_272030_);
      this.m_272137_(C_4759_.f_123781_, C_3953_::m_271744_);
      this.m_272137_(C_4759_.f_123782_, C_3953_::m_272129_);
      this.m_272137_(C_4759_.f_175832_, C_3953_::m_272261_);
      this.m_107378_(C_4759_.f_175833_, C_4070_.C_141706_::new);
      this.m_107378_(C_4759_.f_123783_, C_3918_.C_3919_::new);
      this.m_107378_(C_4759_.f_123784_, C_4070_.C_4072_::new);
      this.m_107378_(C_4759_.f_123785_, C_4070_.C_4074_::new);
      this.m_272137_(C_4759_.f_123786_, C_3953_::m_271935_);
      this.m_272137_(C_4759_.f_123787_, C_3953_::m_271941_);
      this.m_272137_(C_4759_.f_123788_, C_3953_::m_272251_);
      this.m_107378_(C_4759_.f_123789_, C_4042_.C_4044_::new);
      this.m_107378_(C_4759_.f_123790_, C_4100_.C_4101_::new);
      this.m_107378_(C_4759_.f_175834_, C_3995_.C_141693_::new);
      this.m_272137_(C_4759_.f_175824_, C_3953_::m_272002_);
      this.m_272137_(C_4759_.f_175825_, C_3953_::m_271993_);
      this.m_107378_(C_4759_.f_276452_, (p_276702_0_) -> {
         return (p_276703_1_, p_276703_2_, p_276703_3_, p_276703_5_, p_276703_7_, p_276703_9_, p_276703_11_, p_276703_13_) -> {
            return new C_276402_(p_276703_2_, p_276703_3_, p_276703_5_, p_276703_7_, p_276702_0_);
         };
      });
      this.m_272137_(C_4759_.f_175822_, C_3953_::m_271789_);
      this.m_272137_(C_4759_.f_175823_, C_3953_::m_271760_);
      this.m_107378_(C_4759_.f_175820_, C_141708_.C_141709_::new);
      this.m_107378_(C_4759_.f_175826_, C_4067_.C_141702_::new);
      this.m_107378_(C_4759_.f_175827_, C_141694_.C_141696_::new);
      this.m_107378_(C_4759_.f_175828_, C_141694_.C_141699_::new);
      this.m_107378_(C_4759_.f_175829_, C_141694_.C_141698_::new);
      this.m_107378_(C_4759_.f_175830_, C_141694_.C_141695_::new);
      this.m_107378_(C_4759_.f_175831_, C_141694_.C_141697_::new);
      this.m_107378_(C_4759_.f_235901_, C_213413_.C_213414_::new);
      this.m_107378_(C_4759_.f_276512_, C_4075_.C_276412_::new);
      this.m_107378_(C_4759_.f_303068_, C_302147_.C_301928_::new);
      this.m_107378_(C_4759_.f_314928_, C_302056_.C_302131_::new);
      this.m_107378_(C_4759_.f_314692_, C_302056_.C_302131_::new);
      this.m_107378_(C_4759_.f_314380_, C_313463_.C_313385_::new);
      this.m_107381_(C_4759_.f_314186_, new C_4085_.C_313417_());
      this.m_107378_(C_4759_.f_314966_, C_4053_.C_4058_::new);
      this.m_107378_(C_4759_.f_317125_, C_4053_.C_4058_::new);
      this.m_107378_(C_4759_.f_314395_, C_313322_.C_313804_::new);
   }

   private void m_107381_(C_4758_ particleTypeIn, C_4028_ particleFactoryIn) {
      this.f_107293_.put(C_256712_.f_257034_.m_7981_(particleTypeIn), particleFactoryIn);
   }

   private void m_272137_(C_4758_ p_272137_1_, C_4028_.C_271042_ p_272137_2_) {
      this.m_107378_(p_272137_1_, (p_271560_1_) -> {
         return (p_271561_2_, p_271561_3_, p_271561_4_, p_271561_6_, p_271561_8_, p_271561_10_, p_271561_12_, p_271561_14_) -> {
            C_4087_ texturesheetparticle = p_272137_2_.m_272232_(p_271561_2_, p_271561_3_, p_271561_4_, p_271561_6_, p_271561_8_, p_271561_10_, p_271561_12_, p_271561_14_);
            if (texturesheetparticle != null) {
               texturesheetparticle.m_108335_(p_271560_1_);
            }

            return texturesheetparticle;
         };
      });
   }

   private void m_107378_(C_4758_ particleTypeIn, C_4027_ particleMetaFactoryIn) {
      C_4026_ particleengine$mutablespriteset = new C_4026_();
      this.f_107295_.put(C_256712_.f_257034_.m_7981_(particleTypeIn), particleengine$mutablespriteset);
      this.f_107293_.put(C_256712_.f_257034_.m_7981_(particleTypeIn), particleMetaFactoryIn.m_107419_(particleengine$mutablespriteset));
   }

   public CompletableFuture m_5540_(C_69_.C_70_ stage, C_77_ resourceManager, C_442_ preparationsProfiler, C_442_ reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
      CompletableFuture completablefuture = CompletableFuture.supplyAsync(() -> {
         return f_243929_.m_247457_(resourceManager);
      }, backgroundExecutor).thenCompose((mapIn) -> {
         List list = new ArrayList(mapIn.size());
         mapIn.forEach((locIn, resIn) -> {
            C_5265_ resourcelocation = f_243929_.m_245273_(locIn);
            list.add(CompletableFuture.supplyAsync(() -> {
               return new C_243491_(resourcelocation, this.m_245118_(resourcelocation, resIn));
            }, backgroundExecutor));
         });
         return C_5322_.m_137567_(list);
      });
      CompletableFuture completablefuture1 = C_243537_.m_245483_(this.f_107296_).m_260881_(resourceManager, f_260634_, 0, backgroundExecutor).thenCompose(C_243537_.C_243503_::m_246429_);
      CompletableFuture var10000 = CompletableFuture.allOf(completablefuture1, completablefuture);
      Objects.requireNonNull(stage);
      return var10000.thenCompose(stage::m_6769_).thenAcceptAsync((voidIn) -> {
         this.m_263560_();
         reloadProfiler.m_7242_();
         reloadProfiler.m_6180_("upload");
         C_243537_.C_243503_ spriteloader$preparations = (C_243537_.C_243503_)completablefuture1.join();
         this.f_107296_.m_247065_(spriteloader$preparations);
         reloadProfiler.m_6182_("bindSpriteSets");
         Set set = new HashSet();
         C_4486_ textureatlassprite = spriteloader$preparations.f_243912_();
         ((List)completablefuture.join()).forEach((defIn) -> {
            Optional optional = defIn.f_243741_();
            if (!optional.isEmpty()) {
               List list = new ArrayList();
               Iterator var7 = ((List)optional.get()).iterator();

               while(var7.hasNext()) {
                  C_5265_ resourcelocation = (C_5265_)var7.next();
                  C_4486_ textureatlassprite1 = (C_4486_)spriteloader$preparations.f_243807_().get(resourcelocation);
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

               ((C_4026_)this.f_107295_.get(defIn.f_244103_())).m_107415_(list);
            }

         });
         if (!set.isEmpty()) {
            f_243727_.warn("Missing particle sprites: {}", set.stream().sorted().map(C_5265_::toString).collect(Collectors.joining(",")));
         }

         reloadProfiler.m_7238_();
         reloadProfiler.m_7241_();
      }, gameExecutor);
   }

   public void m_107301_() {
      this.f_107296_.m_118329_();
   }

   private Optional m_245118_(C_5265_ locationIn, C_76_ resourceIn) {
      if (!this.f_107295_.containsKey(locationIn)) {
         f_243727_.debug("Redundant texture list for particle: {}", locationIn);
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
            throw new IllegalStateException("Failed to load description for particle " + String.valueOf(locationIn), var9);
         }
      }
   }

   public void m_107329_(C_507_ entityIn, C_4756_ particleData) {
      this.f_107290_.add(new C_4091_(this.f_107287_, entityIn, particleData));
   }

   public void m_107332_(C_507_ entityIn, C_4756_ dataIn, int lifetimeIn) {
      this.f_107290_.add(new C_4091_(this.f_107287_, entityIn, dataIn, lifetimeIn));
   }

   @Nullable
   public C_4022_ m_107370_(C_4756_ particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      C_4022_ particle = this.m_107395_(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
      if (particle != null) {
         this.m_107344_(particle);
         return particle;
      } else {
         return null;
      }
   }

   @Nullable
   private C_4022_ m_107395_(C_4756_ particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      C_4028_ particleprovider = (C_4028_)this.f_107293_.get(C_256712_.f_257034_.m_7981_(particleData.m_6012_()));
      return particleprovider == null ? null : particleprovider.m_6966_(particleData, this.f_107287_, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void m_107344_(C_4022_ effect) {
      if (effect != null) {
         if (!(effect instanceof C_3988_.C_3992_) || Config.isFireworkParticles()) {
            Optional optional = effect.m_142654_();
            if (optional.isPresent()) {
               if (this.m_172279_((C_141791_)optional.get())) {
                  this.f_107294_.add(effect);
                  this.m_172281_((C_141791_)optional.get(), 1);
               }
            } else {
               this.f_107294_.add(effect);
            }

         }
      }
   }

   public void m_107388_() {
      this.f_107289_.forEach((typeIn, listIn) -> {
         this.f_107287_.m_46473_().m_6180_(typeIn.toString());
         this.m_107384_(listIn);
         this.f_107287_.m_46473_().m_7238_();
      });
      if (!this.f_107290_.isEmpty()) {
         List list = Lists.newArrayList();
         Iterator var2 = this.f_107290_.iterator();

         while(var2.hasNext()) {
            C_4091_ trackingemitter = (C_4091_)var2.next();
            trackingemitter.m_5989_();
            if (!trackingemitter.m()) {
               list.add(trackingemitter);
            }
         }

         this.f_107290_.removeAll(list);
      }

      C_4022_ particle;
      if (!this.f_107294_.isEmpty()) {
         while((particle = (C_4022_)this.f_107294_.poll()) != null) {
            Queue queue = (Queue)this.f_107289_.computeIfAbsent(particle.m_7556_(), (renderTypeIn) -> {
               return EvictingQueue.create(16384);
            });
            queue.add(particle);
         }
      }

   }

   private void m_107384_(Collection particlesIn) {
      if (!particlesIn.isEmpty()) {
         long timeStartMs = System.currentTimeMillis();
         int countLeft = particlesIn.size();
         Iterator iterator = particlesIn.iterator();

         while(iterator.hasNext()) {
            C_4022_ particle = (C_4022_)iterator.next();
            this.m_107393_(particle);
            if (!particle.m_107276_()) {
               particle.m_142654_().ifPresent((groupIn) -> {
                  this.m_172281_(groupIn, -1);
               });
               iterator.remove();
            }

            --countLeft;
            if (System.currentTimeMillis() > timeStartMs + 20L) {
               break;
            }
         }

         if (countLeft > 0) {
            int countToRemove = countLeft;

            for(Iterator it = particlesIn.iterator(); it.hasNext() && countToRemove > 0; --countToRemove) {
               C_4022_ particle = (C_4022_)it.next();
               particle.m_107274_();
               it.remove();
            }
         }
      }

   }

   private void m_172281_(C_141791_ groupIn, int countIn) {
      this.f_172265_.addTo(groupIn, countIn);
   }

   private void m_107393_(C_4022_ particle) {
      try {
         particle.m_5989_();
      } catch (Throwable var5) {
         C_4883_ crashreport = C_4883_.m_127521_(var5, "Ticking Particle");
         C_4909_ crashreportcategory = crashreport.m_127514_("Particle being ticked");
         Objects.requireNonNull(particle);
         crashreportcategory.m_128165_("Particle", particle::toString);
         C_4029_ var10002 = particle.m_7556_();
         Objects.requireNonNull(var10002);
         crashreportcategory.m_128165_("Particle Type", var10002::toString);
         throw new C_5204_(crashreport);
      }
   }

   public void m_107336_(C_4138_ lightTextureIn, C_3373_ cameraIn, float partialTicks) {
      this.renderParticles(lightTextureIn, cameraIn, partialTicks, (C_4273_)null);
   }

   public void renderParticles(C_4138_ lightTextureIn, C_3373_ cameraIn, float partialTicks, C_4273_ clippingHelper) {
      lightTextureIn.m_109896_();
      RenderSystem.enableDepthTest();
      RenderSystem.activeTexture(33986);
      RenderSystem.activeTexture(33984);
      C_141436_ cameraFogType = cameraIn.m_167685_();
      boolean isEyeInWater = cameraFogType == C_141436_.WATER;
      Collection renderTypes = f_107288_;
      if (Reflector.ForgeHooksClient.exists()) {
         renderTypes = this.f_107289_.keySet();
      }

      Iterator var8 = ((Collection)renderTypes).iterator();

      label88:
      while(true) {
         C_4029_ particlerendertype;
         Queue queue;
         C_3173_ bufferbuilder;
         do {
            do {
               do {
                  do {
                     if (!var8.hasNext()) {
                        RenderSystem.depthMask(true);
                        RenderSystem.disableBlend();
                        lightTextureIn.m_109891_();
                        RenderSystem.enableDepthTest();
                        GlStateManager._glUseProgram(0);
                        return;
                     }

                     particlerendertype = (C_4029_)var8.next();
                  } while(particlerendertype == C_4029_.f_107434_);

                  queue = (Queue)this.f_107289_.get(particlerendertype);
               } while(queue == null);
            } while(queue.isEmpty());

            RenderSystem.setShader(C_4124_::m_172829_);
            C_3185_ tesselator = C_3185_.m_85913_();
            bufferbuilder = particlerendertype.m_6505_(tesselator, this.f_107291_);
         } while(bufferbuilder == null);

         Iterator var13 = queue.iterator();

         while(true) {
            C_4022_ particle;
            do {
               do {
                  if (!var13.hasNext()) {
                     C_336471_ meshdata = bufferbuilder.m_339970_();
                     if (meshdata != null) {
                        C_3177_.m_231202_(meshdata);
                     }
                     continue label88;
                  }

                  particle = (C_4022_)var13.next();
               } while(clippingHelper != null && particle.shouldCull() && !clippingHelper.m_113029_(particle.m_107277_()));
            } while(!isEyeInWater && particle instanceof C_4070_ && particle.f_107215_ == 0.0 && particle.f_107216_ == 0.0 && particle.f_107217_ == 0.0);

            try {
               particle.m_5744_(bufferbuilder, cameraIn, partialTicks);
            } catch (Throwable var18) {
               C_4883_ crashreport = C_4883_.m_127521_(var18, "Rendering Particle");
               C_4909_ crashreportcategory = crashreport.m_127514_("Particle being rendered");
               Objects.requireNonNull(particle);
               crashreportcategory.m_128165_("Particle", particle::toString);
               Objects.requireNonNull(particlerendertype);
               crashreportcategory.m_128165_("Particle Type", particlerendertype::toString);
               throw new C_5204_(crashreport);
            }
         }
      }
   }

   public void m_107342_(@Nullable C_3899_ worldIn) {
      this.f_107287_ = worldIn;
      this.m_263560_();
      this.f_107290_.clear();
   }

   public void m_107355_(C_4675_ pos, C_2064_ state) {
      boolean forgeAddDestroyEffects = false;
      IClientBlockExtensions cbe = IClientBlockExtensions.method_2(state);
      if (cbe != null) {
         forgeAddDestroyEffects = cbe.addDestroyEffects(state, this.f_107287_, pos, this);
      }

      if (!state.m_60795_() && state.m_295777_() && !forgeAddDestroyEffects) {
         C_3072_ voxelshape = state.m_60808_(this.f_107287_, pos);
         double d0 = 0.25;
         voxelshape.m_83286_((p_172270_3_, p_172270_5_, p_172270_7_, p_172270_9_, p_172270_11_, p_172270_13_) -> {
            double d1 = Math.min(1.0, p_172270_9_ - p_172270_3_);
            double d2 = Math.min(1.0, p_172270_11_ - p_172270_5_);
            double d3 = Math.min(1.0, p_172270_13_ - p_172270_7_);
            int i = Math.max(2, C_188_.m_14165_(d1 / 0.25));
            int j = Math.max(2, C_188_.m_14165_(d2 / 0.25));
            int k = Math.max(2, C_188_.m_14165_(d3 / 0.25));

            for(int l = 0; l < i; ++l) {
               for(int i1 = 0; i1 < j; ++i1) {
                  for(int j1 = 0; j1 < k; ++j1) {
                     double d4 = ((double)l + 0.5) / (double)i;
                     double d5 = ((double)i1 + 0.5) / (double)j;
                     double d6 = ((double)j1 + 0.5) / (double)k;
                     double d7 = d4 * d1 + p_172270_3_;
                     double d8 = d5 * d2 + p_172270_5_;
                     double d9 = d6 * d3 + p_172270_7_;
                     C_4022_ tp = new C_4085_(this.f_107287_, (double)pos.u() + d7, (double)pos.v() + d8, (double)pos.w() + d9, d4 - 0.5, d5 - 0.5, d6 - 0.5, state, pos);
                     if (Reflector.TerrainParticle_updateSprite.exists()) {
                        Reflector.call(tp, Reflector.TerrainParticle_updateSprite, state, pos);
                     }

                     if (Config.isCustomColors()) {
                        updateTerrainParticleColor(tp, state, this.f_107287_, pos, this.renderEnv);
                     }

                     this.m_107344_(tp);
                  }
               }
            }

         });
      }

   }

   public void m_107367_(C_4675_ pos, C_4687_ side) {
      C_2064_ blockstate = this.f_107287_.m_8055_(pos);
      if (blockstate.m_60799_() != C_1879_.INVISIBLE && blockstate.m_295777_()) {
         int i = pos.u();
         int j = pos.v();
         int k = pos.w();
         float f = 0.1F;
         C_3040_ aabb = blockstate.m_60808_(this.f_107287_, pos).m_83215_();
         double d0 = (double)i + this.f_107292_.m_188500_() * (aabb.f_82291_ - aabb.f_82288_ - 0.20000000298023224) + 0.10000000149011612 + aabb.f_82288_;
         double d1 = (double)j + this.f_107292_.m_188500_() * (aabb.f_82292_ - aabb.f_82289_ - 0.20000000298023224) + 0.10000000149011612 + aabb.f_82289_;
         double d2 = (double)k + this.f_107292_.m_188500_() * (aabb.f_82293_ - aabb.f_82290_ - 0.20000000298023224) + 0.10000000149011612 + aabb.f_82290_;
         if (side == C_4687_.DOWN) {
            d1 = (double)j + aabb.f_82289_ - 0.10000000149011612;
         }

         if (side == C_4687_.field_50) {
            d1 = (double)j + aabb.f_82292_ + 0.10000000149011612;
         }

         if (side == C_4687_.NORTH) {
            d2 = (double)k + aabb.f_82290_ - 0.10000000149011612;
         }

         if (side == C_4687_.SOUTH) {
            d2 = (double)k + aabb.f_82293_ + 0.10000000149011612;
         }

         if (side == C_4687_.WEST) {
            d0 = (double)i + aabb.f_82288_ - 0.10000000149011612;
         }

         if (side == C_4687_.EAST) {
            d0 = (double)i + aabb.f_82291_ + 0.10000000149011612;
         }

         C_4022_ tp = (new C_4085_(this.f_107287_, d0, d1, d2, 0.0, 0.0, 0.0, blockstate, pos)).c(0.2F).m_6569_(0.6F);
         if (Reflector.TerrainParticle_updateSprite.exists()) {
            Reflector.call(tp, Reflector.TerrainParticle_updateSprite, blockstate, pos);
         }

         if (Config.isCustomColors()) {
            updateTerrainParticleColor(tp, blockstate, this.f_107287_, pos, this.renderEnv);
         }

         this.m_107344_(tp);
      }

   }

   public String m_107403_() {
      return String.valueOf(this.f_107289_.values().stream().mapToInt(Collection::size).sum());
   }

   private boolean m_172279_(C_141791_ groupIn) {
      return this.f_172265_.getInt(groupIn) < groupIn.m_175819_();
   }

   private void m_263560_() {
      this.f_107289_.clear();
      this.f_107294_.clear();
      this.f_107290_.clear();
      this.f_172265_.clear();
   }

   private boolean reuseBarrierParticle(C_4022_ entityfx, Queue deque) {
      C_4022_ var4;
      for(Iterator it = deque.iterator(); it.hasNext(); var4 = (C_4022_)it.next()) {
      }

      return false;
   }

   public static void updateTerrainParticleColor(C_4022_ particle, C_2064_ state, C_1557_ world, C_4675_ pos, RenderEnv renderEnv) {
      renderEnv.reset(state, pos);
      int col = CustomColors.getColorMultiplier(true, state, world, pos, renderEnv);
      if (col != -1) {
         particle.f_107227_ = 0.6F * (float)(col >> 16 & 255) / 255.0F;
         particle.f_107228_ = 0.6F * (float)(col >> 8 & 255) / 255.0F;
         particle.f_107229_ = 0.6F * (float)(col & 255) / 255.0F;
      }

   }

   public int getCountParticles() {
      int sum = 0;

      Queue queue;
      for(Iterator var2 = this.f_107289_.values().iterator(); var2.hasNext(); sum += queue.size()) {
         queue = (Queue)var2.next();
      }

      return sum;
   }

   public void addBlockHitEffects(C_4675_ pos, C_3041_ target) {
      C_2064_ state = this.f_107287_.m_8055_(pos);
      if (!IClientBlockExtensions.method_2(state).addHitEffects(state, this.f_107287_, target, this)) {
         this.m_107367_(pos, target.m_82434_());
      }

   }

   static {
      f_107288_ = ImmutableList.of(C_4029_.f_107429_, C_4029_.f_107430_, C_4029_.f_107432_, C_4029_.f_107431_, C_4029_.f_107433_);
   }

   @FunctionalInterface
   interface C_4027_ {
      C_4028_ m_107419_(C_4066_ var1);
   }

   static class C_4026_ implements C_4066_ {
      private List f_107406_;

      public C_4486_ m_5819_(int particleAge, int particleMaxAge) {
         return (C_4486_)this.f_107406_.get(particleAge * (this.f_107406_.size() - 1) / particleMaxAge);
      }

      public C_4486_ m_213979_(C_212974_ randomIn) {
         return (C_4486_)this.f_107406_.get(randomIn.m_188503_(this.f_107406_.size()));
      }

      public void m_107415_(List spritesIn) {
         this.f_107406_ = ImmutableList.copyOf(spritesIn);
      }
   }

   static record C_243491_(C_5265_ f_244103_, Optional f_243741_) {
      C_243491_(C_5265_ id, Optional sprites) {
         this.f_244103_ = id;
         this.f_243741_ = sprites;
      }

      public C_5265_ f_244103_() {
         return this.f_244103_;
      }

      public Optional f_243741_() {
         return this.f_243741_;
      }
   }
}
