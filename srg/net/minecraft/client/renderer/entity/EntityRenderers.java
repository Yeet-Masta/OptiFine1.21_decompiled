package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.DisplayRenderer.BlockDisplayRenderer;
import net.minecraft.client.renderer.entity.DisplayRenderer.ItemDisplayRenderer;
import net.minecraft.client.renderer.entity.DisplayRenderer.TextDisplayRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.optifine.player.PlayerItemsLayer;
import org.slf4j.Logger;

public class EntityRenderers {
   private static final Logger f_174030_ = LogUtils.getLogger();
   private static final Map<EntityType<?>, EntityRendererProvider<?>> f_174031_ = new Object2ObjectOpenHashMap();
   private static final Map<PlayerSkin.Model, EntityRendererProvider<AbstractClientPlayer>> f_174032_ = Map.of(
      PlayerSkin.Model.WIDE, (EntityRendererProvider)contextIn -> {
         PlayerRenderer playerRenderer = new PlayerRenderer(contextIn, false);
         playerRenderer.m_115326_(new PlayerItemsLayer(playerRenderer));
         return playerRenderer;
      }, PlayerSkin.Model.SLIM, (EntityRendererProvider)context2In -> {
         PlayerRenderer playerRenderer = new PlayerRenderer(context2In, true);
         playerRenderer.m_115326_(new PlayerItemsLayer(playerRenderer));
         return playerRenderer;
      }
   );

   private static <T extends Entity> void m_174036_(EntityType<? extends T> typeIn, EntityRendererProvider<T> providerIn) {
      f_174031_.put(typeIn, providerIn);
   }

   public static Map<EntityType<?>, EntityRenderer<?>> m_174049_(Context contextIn) {
      Builder<EntityType<?>, EntityRenderer<?>> builder = ImmutableMap.builder();
      f_174031_.forEach((typeIn, providerIn) -> {
         try {
            builder.put(typeIn, providerIn.m_174009_(contextIn));
         } catch (Exception var5) {
            throw new IllegalArgumentException("Failed to create model for " + BuiltInRegistries.f_256780_.m_7981_(typeIn), var5);
         }
      });
      return builder.build();
   }

   public static Map<PlayerSkin.Model, EntityRenderer<? extends Player>> m_174051_(Context contextIn) {
      Builder<PlayerSkin.Model, EntityRenderer<? extends Player>> builder = ImmutableMap.builder();
      f_174032_.forEach((modelIn, providerIn) -> {
         try {
            builder.put(modelIn, providerIn.m_174009_(contextIn));
         } catch (Exception var5) {
            throw new IllegalArgumentException("Failed to create player model for " + modelIn, var5);
         }
      });
      return builder.build();
   }

   public static boolean m_174035_() {
      boolean flag = true;

      for (EntityType<?> entitytype : BuiltInRegistries.f_256780_) {
         if (entitytype != EntityType.f_20532_ && !f_174031_.containsKey(entitytype)) {
            f_174030_.warn("No renderer registered for {}", BuiltInRegistries.f_256780_.m_7981_(entitytype));
            flag = false;
         }
      }

      return !flag;
   }

   static {
      m_174036_(EntityType.f_217014_, AllayRenderer::new);
      m_174036_(EntityType.f_20476_, NoopRenderer::new);
      m_174036_(EntityType.f_316265_, ArmadilloRenderer::new);
      m_174036_(EntityType.f_20529_, ArmorStandRenderer::new);
      m_174036_(EntityType.f_20548_, TippableArrowRenderer::new);
      m_174036_(EntityType.f_147039_, AxolotlRenderer::new);
      m_174036_(EntityType.f_20549_, BatRenderer::new);
      m_174036_(EntityType.f_20550_, BeeRenderer::new);
      m_174036_(EntityType.f_20551_, BlazeRenderer::new);
      m_174036_(EntityType.f_268573_, BlockDisplayRenderer::new);
      m_174036_(EntityType.f_20552_, p_174093_0_ -> new BoatRenderer(p_174093_0_, false));
      m_174036_(EntityType.f_316281_, BoggedRenderer::new);
      m_174036_(EntityType.f_302782_, BreezeRenderer::new);
      m_174036_(EntityType.f_315936_, WindChargeRenderer::new);
      m_174036_(EntityType.f_20553_, CatRenderer::new);
      m_174036_(EntityType.f_243976_, contextIn -> new CamelRenderer(contextIn, ModelLayers.f_244030_));
      m_174036_(EntityType.f_20554_, CaveSpiderRenderer::new);
      m_174036_(EntityType.f_217016_, p_174091_0_ -> new BoatRenderer(p_174091_0_, true));
      m_174036_(EntityType.f_20470_, p_174089_0_ -> new MinecartRenderer(p_174089_0_, ModelLayers.f_171276_));
      m_174036_(EntityType.f_20555_, ChickenRenderer::new);
      m_174036_(EntityType.f_20556_, CodRenderer::new);
      m_174036_(EntityType.f_20471_, p_174087_0_ -> new MinecartRenderer(p_174087_0_, ModelLayers.f_171279_));
      m_174036_(EntityType.f_20557_, CowRenderer::new);
      m_174036_(EntityType.f_20558_, CreeperRenderer::new);
      m_174036_(EntityType.f_20559_, DolphinRenderer::new);
      m_174036_(EntityType.f_20560_, p_174085_0_ -> new ChestedHorseRenderer(p_174085_0_, 0.87F, ModelLayers.f_171132_));
      m_174036_(EntityType.f_20561_, DragonFireballRenderer::new);
      m_174036_(EntityType.f_20562_, DrownedRenderer::new);
      m_174036_(EntityType.f_20483_, ThrownItemRenderer::new);
      m_174036_(EntityType.f_20563_, ElderGuardianRenderer::new);
      m_174036_(EntityType.f_20566_, EndermanRenderer::new);
      m_174036_(EntityType.f_20567_, EndermiteRenderer::new);
      m_174036_(EntityType.f_20565_, EnderDragonRenderer::new);
      m_174036_(EntityType.f_20484_, ThrownItemRenderer::new);
      m_174036_(EntityType.f_20564_, EndCrystalRenderer::new);
      m_174036_(EntityType.f_20568_, EvokerRenderer::new);
      m_174036_(EntityType.f_20569_, EvokerFangsRenderer::new);
      m_174036_(EntityType.f_20485_, ThrownItemRenderer::new);
      m_174036_(EntityType.f_20570_, ExperienceOrbRenderer::new);
      m_174036_(EntityType.f_20571_, p_174083_0_ -> new ThrownItemRenderer(p_174083_0_, 1.0F, true));
      m_174036_(EntityType.f_20450_, FallingBlockRenderer::new);
      m_174036_(EntityType.f_20463_, p_174081_0_ -> new ThrownItemRenderer(p_174081_0_, 3.0F, true));
      m_174036_(EntityType.f_20451_, FireworkEntityRenderer::new);
      m_174036_(EntityType.f_20533_, FishingHookRenderer::new);
      m_174036_(EntityType.f_20452_, FoxRenderer::new);
      m_174036_(EntityType.f_217012_, FrogRenderer::new);
      m_174036_(EntityType.f_20472_, p_174079_0_ -> new MinecartRenderer(p_174079_0_, ModelLayers.f_171149_));
      m_174036_(EntityType.f_20453_, GhastRenderer::new);
      m_174036_(EntityType.f_20454_, p_174077_0_ -> new GiantMobRenderer(p_174077_0_, 6.0F));
      m_174036_(EntityType.f_147033_, ItemFrameRenderer::new);
      m_174036_(EntityType.f_147034_, p_174075_0_ -> new GlowSquidRenderer(p_174075_0_, new SquidModel(p_174075_0_.m_174023_(ModelLayers.f_171154_))));
      m_174036_(EntityType.f_147035_, GoatRenderer::new);
      m_174036_(EntityType.f_20455_, GuardianRenderer::new);
      m_174036_(EntityType.f_20456_, HoglinRenderer::new);
      m_174036_(EntityType.f_20473_, p_174073_0_ -> new MinecartRenderer(p_174073_0_, ModelLayers.f_171185_));
      m_174036_(EntityType.f_20457_, HorseRenderer::new);
      m_174036_(EntityType.f_20458_, HuskRenderer::new);
      m_174036_(EntityType.f_20459_, IllusionerRenderer::new);
      m_174036_(EntityType.f_271243_, NoopRenderer::new);
      m_174036_(EntityType.f_20460_, IronGolemRenderer::new);
      m_174036_(EntityType.f_20461_, ItemEntityRenderer::new);
      m_174036_(EntityType.f_268643_, ItemDisplayRenderer::new);
      m_174036_(EntityType.f_20462_, ItemFrameRenderer::new);
      m_174036_(EntityType.f_314497_, OminousItemSpawnerRenderer::new);
      m_174036_(EntityType.f_20464_, LeashKnotRenderer::new);
      m_174036_(EntityType.f_20465_, LightningBoltRenderer::new);
      m_174036_(EntityType.f_20466_, p_174071_0_ -> new LlamaRenderer(p_174071_0_, ModelLayers.f_171194_));
      m_174036_(EntityType.f_20467_, LlamaSpitRenderer::new);
      m_174036_(EntityType.f_20468_, MagmaCubeRenderer::new);
      m_174036_(EntityType.f_147036_, NoopRenderer::new);
      m_174036_(EntityType.f_20469_, p_174069_0_ -> new MinecartRenderer(p_174069_0_, ModelLayers.f_171198_));
      m_174036_(EntityType.f_20504_, MushroomCowRenderer::new);
      m_174036_(EntityType.f_20503_, p_174067_0_ -> new ChestedHorseRenderer(p_174067_0_, 0.92F, ModelLayers.f_171200_));
      m_174036_(EntityType.f_20505_, OcelotRenderer::new);
      m_174036_(EntityType.f_20506_, PaintingRenderer::new);
      m_174036_(EntityType.f_20507_, PandaRenderer::new);
      m_174036_(EntityType.f_20508_, ParrotRenderer::new);
      m_174036_(EntityType.f_20509_, PhantomRenderer::new);
      m_174036_(EntityType.f_20510_, PigRenderer::new);
      m_174036_(EntityType.f_20511_, p_174065_0_ -> new PiglinRenderer(p_174065_0_, ModelLayers.f_171206_, ModelLayers.f_171158_, ModelLayers.f_171159_, false));
      m_174036_(EntityType.f_20512_, p_174063_0_ -> new PiglinRenderer(p_174063_0_, ModelLayers.f_171207_, ModelLayers.f_171156_, ModelLayers.f_171157_, false));
      m_174036_(EntityType.f_20513_, PillagerRenderer::new);
      m_174036_(EntityType.f_20514_, PolarBearRenderer::new);
      m_174036_(EntityType.f_20486_, ThrownItemRenderer::new);
      m_174036_(EntityType.f_20516_, PufferfishRenderer::new);
      m_174036_(EntityType.f_20517_, RabbitRenderer::new);
      m_174036_(EntityType.f_20518_, RavagerRenderer::new);
      m_174036_(EntityType.f_20519_, SalmonRenderer::new);
      m_174036_(EntityType.f_20520_, SheepRenderer::new);
      m_174036_(EntityType.f_20521_, ShulkerRenderer::new);
      m_174036_(EntityType.f_20522_, ShulkerBulletRenderer::new);
      m_174036_(EntityType.f_20523_, SilverfishRenderer::new);
      m_174036_(EntityType.f_20524_, SkeletonRenderer::new);
      m_174036_(EntityType.f_20525_, p_174061_0_ -> new UndeadHorseRenderer(p_174061_0_, ModelLayers.f_171237_));
      m_174036_(EntityType.f_20526_, SlimeRenderer::new);
      m_174036_(EntityType.f_20527_, p_174059_0_ -> new ThrownItemRenderer(p_174059_0_, 0.75F, true));
      m_174036_(EntityType.f_271264_, SnifferRenderer::new);
      m_174036_(EntityType.f_20477_, ThrownItemRenderer::new);
      m_174036_(EntityType.f_20528_, SnowGolemRenderer::new);
      m_174036_(EntityType.f_20474_, p_174057_0_ -> new MinecartRenderer(p_174057_0_, ModelLayers.f_171244_));
      m_174036_(EntityType.f_20478_, SpectralArrowRenderer::new);
      m_174036_(EntityType.f_20479_, SpiderRenderer::new);
      m_174036_(EntityType.f_20480_, p_174055_0_ -> new SquidRenderer(p_174055_0_, new SquidModel(p_174055_0_.m_174023_(ModelLayers.f_171246_))));
      m_174036_(EntityType.f_20481_, StrayRenderer::new);
      m_174036_(EntityType.f_20482_, StriderRenderer::new);
      m_174036_(EntityType.f_217013_, TadpoleRenderer::new);
      m_174036_(EntityType.f_268607_, TextDisplayRenderer::new);
      m_174036_(EntityType.f_20515_, TntRenderer::new);
      m_174036_(EntityType.f_20475_, TntMinecartRenderer::new);
      m_174036_(EntityType.f_20488_, p_174053_0_ -> new LlamaRenderer(p_174053_0_, ModelLayers.f_171254_));
      m_174036_(EntityType.f_20487_, ThrownTridentRenderer::new);
      m_174036_(EntityType.f_20489_, TropicalFishRenderer::new);
      m_174036_(EntityType.f_20490_, TurtleRenderer::new);
      m_174036_(EntityType.f_20491_, VexRenderer::new);
      m_174036_(EntityType.f_20492_, VillagerRenderer::new);
      m_174036_(EntityType.f_20493_, VindicatorRenderer::new);
      m_174036_(EntityType.f_217015_, WardenRenderer::new);
      m_174036_(EntityType.f_20494_, WanderingTraderRenderer::new);
      m_174036_(EntityType.f_303421_, WindChargeRenderer::new);
      m_174036_(EntityType.f_20495_, WitchRenderer::new);
      m_174036_(EntityType.f_20496_, WitherBossRenderer::new);
      m_174036_(EntityType.f_20497_, WitherSkeletonRenderer::new);
      m_174036_(EntityType.f_20498_, WitherSkullRenderer::new);
      m_174036_(EntityType.f_20499_, WolfRenderer::new);
      m_174036_(EntityType.f_20500_, ZoglinRenderer::new);
      m_174036_(EntityType.f_20501_, ZombieRenderer::new);
      m_174036_(EntityType.f_20502_, p_234611_0_ -> new UndeadHorseRenderer(p_234611_0_, ModelLayers.f_171225_));
      m_174036_(EntityType.f_20530_, ZombieVillagerRenderer::new);
      m_174036_(EntityType.f_20531_, p_234609_0_ -> new PiglinRenderer(p_234609_0_, ModelLayers.f_171231_, ModelLayers.f_171232_, ModelLayers.f_171233_, true));
   }
}
