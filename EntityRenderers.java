import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_141741_;
import net.minecraft.src.C_141742_;
import net.minecraft.src.C_141745_;
import net.minecraft.src.C_141746_;
import net.minecraft.src.C_141748_;
import net.minecraft.src.C_213426_;
import net.minecraft.src.C_213427_;
import net.minecraft.src.C_213428_;
import net.minecraft.src.C_213431_;
import net.minecraft.src.C_243510_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_271083_;
import net.minecraft.src.C_301983_;
import net.minecraft.src.C_302054_;
import net.minecraft.src.C_313557_;
import net.minecraft.src.C_313588_;
import net.minecraft.src.C_313885_;
import net.minecraft.src.C_3873_;
import net.minecraft.src.C_4307_;
import net.minecraft.src.C_4310_;
import net.minecraft.src.C_4311_;
import net.minecraft.src.C_4312_;
import net.minecraft.src.C_4313_;
import net.minecraft.src.C_4314_;
import net.minecraft.src.C_4315_;
import net.minecraft.src.C_4316_;
import net.minecraft.src.C_4317_;
import net.minecraft.src.C_4318_;
import net.minecraft.src.C_4319_;
import net.minecraft.src.C_4320_;
import net.minecraft.src.C_4321_;
import net.minecraft.src.C_4322_;
import net.minecraft.src.C_4323_;
import net.minecraft.src.C_4324_;
import net.minecraft.src.C_4325_;
import net.minecraft.src.C_4328_;
import net.minecraft.src.C_4329_;
import net.minecraft.src.C_4332_;
import net.minecraft.src.C_4333_;
import net.minecraft.src.C_4336_;
import net.minecraft.src.C_4337_;
import net.minecraft.src.C_4339_;
import net.minecraft.src.C_4340_;
import net.minecraft.src.C_4341_;
import net.minecraft.src.C_4342_;
import net.minecraft.src.C_4343_;
import net.minecraft.src.C_4344_;
import net.minecraft.src.C_4345_;
import net.minecraft.src.C_4347_;
import net.minecraft.src.C_4349_;
import net.minecraft.src.C_4351_;
import net.minecraft.src.C_4352_;
import net.minecraft.src.C_4355_;
import net.minecraft.src.C_4356_;
import net.minecraft.src.C_4359_;
import net.minecraft.src.C_4360_;
import net.minecraft.src.C_4361_;
import net.minecraft.src.C_4362_;
import net.minecraft.src.C_4364_;
import net.minecraft.src.C_4365_;
import net.minecraft.src.C_4366_;
import net.minecraft.src.C_4372_;
import net.minecraft.src.C_4373_;
import net.minecraft.src.C_4374_;
import net.minecraft.src.C_4375_;
import net.minecraft.src.C_4376_;
import net.minecraft.src.C_4377_;
import net.minecraft.src.C_4378_;
import net.minecraft.src.C_4379_;
import net.minecraft.src.C_4380_;
import net.minecraft.src.C_4381_;
import net.minecraft.src.C_4383_;
import net.minecraft.src.C_4384_;
import net.minecraft.src.C_4385_;
import net.minecraft.src.C_4386_;
import net.minecraft.src.C_4387_;
import net.minecraft.src.C_4388_;
import net.minecraft.src.C_4389_;
import net.minecraft.src.C_4390_;
import net.minecraft.src.C_4391_;
import net.minecraft.src.C_4392_;
import net.minecraft.src.C_4393_;
import net.minecraft.src.C_4394_;
import net.minecraft.src.C_4395_;
import net.minecraft.src.C_4396_;
import net.minecraft.src.C_4397_;
import net.minecraft.src.C_4399_;
import net.minecraft.src.C_4401_;
import net.minecraft.src.C_4402_;
import net.minecraft.src.C_4403_;
import net.minecraft.src.C_4404_;
import net.minecraft.src.C_4405_;
import net.minecraft.src.C_4406_;
import net.minecraft.src.C_4407_;
import net.minecraft.src.C_4409_;
import net.minecraft.src.C_4410_;
import net.minecraft.src.C_4411_;
import net.minecraft.src.C_4412_;
import net.minecraft.src.C_4413_;
import net.minecraft.src.C_4414_;
import net.minecraft.src.C_4415_;
import net.minecraft.src.C_4416_;
import net.minecraft.src.C_4417_;
import net.minecraft.src.C_4462_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_141742_.C_141743_;
import net.minecraft.src.C_268412_.C_268365_;
import net.minecraft.src.C_268412_.C_268379_;
import net.minecraft.src.C_268412_.C_268401_;
import net.optifine.player.PlayerItemsLayer;
import org.slf4j.Logger;

public class EntityRenderers {
   private static final Logger a = LogUtils.getLogger();
   private static final Map<C_513_<?>, C_141742_<?>> b = new Object2ObjectOpenHashMap();
   private static final Map<PlayerSkin.a, C_141742_<AbstractClientPlayer>> c = Map.of(
      PlayerSkin.a.b,
      (C_141742_)contextIn -> {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.RuntimeException: Constructor net/optifine/player/PlayerItemsLayer.<init>(Lnet/minecraft/src/C_4462_;)V not found
         //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:49)
         //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:957)
         //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:460)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1018)
         //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:1153)
         //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.toJava(InvocationExprent.java:902)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
         //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
         //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
         //   at org.jetbrains.java.decompiler.main.ClassWriter.methodLambdaToJava(ClassWriter.java:949)
         //
         // Bytecode:
         // 00: new net/minecraft/src/C_4462_
         // 03: dup
         // 04: aload 0
         // 05: bipush 0
         // 06: invokespecial net/minecraft/src/C_4462_.<init> (Lnet/minecraft/src/C_141742_$C_141743_;Z)V
         // 09: astore 1
         // 0a: aload 1
         // 0b: new net/optifine/player/PlayerItemsLayer
         // 0e: dup
         // 0f: aload 1
         // 10: invokespecial net/optifine/player/PlayerItemsLayer.<init> (Lnet/minecraft/src/C_4462_;)V
         // 13: invokevirtual net/minecraft/src/C_4462_.a (LRenderLayer;)Z
         // 16: pop
         // 17: aload 1
         // 18: areturn
      },
      PlayerSkin.a.a,
      (C_141742_)context2In -> {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.RuntimeException: Constructor net/optifine/player/PlayerItemsLayer.<init>(Lnet/minecraft/src/C_4462_;)V not found
         //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:49)
         //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:957)
         //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:460)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1018)
         //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:1153)
         //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.toJava(InvocationExprent.java:902)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
         //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
         //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
         //   at org.jetbrains.java.decompiler.main.ClassWriter.methodLambdaToJava(ClassWriter.java:949)
         //
         // Bytecode:
         // 00: new net/minecraft/src/C_4462_
         // 03: dup
         // 04: aload 0
         // 05: bipush 1
         // 06: invokespecial net/minecraft/src/C_4462_.<init> (Lnet/minecraft/src/C_141742_$C_141743_;Z)V
         // 09: astore 1
         // 0a: aload 1
         // 0b: new net/optifine/player/PlayerItemsLayer
         // 0e: dup
         // 0f: aload 1
         // 10: invokespecial net/optifine/player/PlayerItemsLayer.<init> (Lnet/minecraft/src/C_4462_;)V
         // 13: invokevirtual net/minecraft/src/C_4462_.a (LRenderLayer;)Z
         // 16: pop
         // 17: aload 1
         // 18: areturn
      }
   );

   private static <T extends C_507_> void a(C_513_<? extends T> typeIn, C_141742_<T> providerIn) {
      b.put(typeIn, providerIn);
   }

   public static Map<C_513_<?>, EntityRenderer<?>> a(C_141743_ contextIn) {
      Builder<C_513_<?>, EntityRenderer<?>> builder = ImmutableMap.builder();
      b.forEach((typeIn, providerIn) -> {
         try {
            builder.put(typeIn, providerIn.create(contextIn));
         } catch (Exception var5) {
            throw new IllegalArgumentException("Failed to create model for " + C_256712_.f_256780_.b(typeIn), var5);
         }
      });
      return builder.build();
   }

   public static Map<PlayerSkin.a, EntityRenderer<? extends C_1141_>> b(C_141743_ contextIn) {
      Builder<PlayerSkin.a, EntityRenderer<? extends C_1141_>> builder = ImmutableMap.builder();
      c.forEach((modelIn, providerIn) -> {
         try {
            builder.put(modelIn, providerIn.create(contextIn));
         } catch (Exception var5) {
            throw new IllegalArgumentException("Failed to create player model for " + modelIn, var5);
         }
      });
      return builder.build();
   }

   public static boolean a() {
      boolean flag = true;

      for (C_513_<?> entitytype : C_256712_.f_256780_) {
         if (entitytype != C_513_.f_20532_ && !b.containsKey(entitytype)) {
            a.warn("No renderer registered for {}", C_256712_.f_256780_.b(entitytype));
            flag = false;
         }
      }

      return !flag;
   }

   static {
      a(C_513_.f_217014_, C_213426_::new);
      a(C_513_.f_20476_, C_141748_::new);
      a(C_513_.f_316265_, C_313885_::new);
      a(C_513_.f_20529_, C_4307_::new);
      a(C_513_.f_20548_, C_4399_::new);
      a(C_513_.f_147039_, C_141741_::new);
      a(C_513_.f_20549_, C_4310_::new);
      a(C_513_.f_20550_, C_4311_::new);
      a(C_513_.f_20551_, C_4312_::new);
      a(C_513_.f_268573_, C_268401_::new);
      a(C_513_.f_20552_, p_174093_0_ -> new C_4313_(p_174093_0_, false));
      a(C_513_.f_316281_, C_313557_::new);
      a(C_513_.f_302782_, C_302054_::new);
      a(C_513_.f_315936_, C_301983_::new);
      a(C_513_.f_20553_, C_4314_::new);
      a(C_513_.f_243976_, contextIn -> new C_243510_(contextIn, C_141656_.f_244030_));
      a(C_513_.f_20554_, C_4315_::new);
      a(C_513_.f_217016_, p_174091_0_ -> new C_4313_(p_174091_0_, true));
      a(C_513_.f_20470_, p_174089_0_ -> new C_4362_(p_174089_0_, C_141656_.f_171276_));
      a(C_513_.f_20555_, C_4317_::new);
      a(C_513_.f_20556_, C_4318_::new);
      a(C_513_.f_20471_, p_174087_0_ -> new C_4362_(p_174087_0_, C_141656_.f_171279_));
      a(C_513_.f_20557_, C_4319_::new);
      a(C_513_.f_20558_, C_4320_::new);
      a(C_513_.f_20559_, C_4321_::new);
      a(C_513_.f_20560_, p_174085_0_ -> new C_4316_(p_174085_0_, 0.87F, C_141656_.f_171132_));
      a(C_513_.f_20561_, C_4322_::new);
      a(C_513_.f_20562_, C_4323_::new);
      a(C_513_.f_20483_, C_4396_::new);
      a(C_513_.f_20563_, C_4324_::new);
      a(C_513_.f_20566_, C_4328_::new);
      a(C_513_.f_20567_, C_4329_::new);
      a(C_513_.f_20565_, EnderDragonRenderer::new);
      a(C_513_.f_20484_, C_4396_::new);
      a(C_513_.f_20564_, C_4325_::new);
      a(C_513_.f_20568_, C_4333_::new);
      a(C_513_.f_20569_, C_4332_::new);
      a(C_513_.f_20485_, C_4396_::new);
      a(C_513_.f_20570_, ExperienceOrbRenderer::new);
      a(C_513_.f_20571_, p_174083_0_ -> new C_4396_(p_174083_0_, 1.0F, true));
      a(C_513_.f_20450_, C_4336_::new);
      a(C_513_.f_20463_, p_174081_0_ -> new C_4396_(p_174081_0_, 3.0F, true));
      a(C_513_.f_20451_, C_4337_::new);
      a(C_513_.f_20533_, C_4339_::new);
      a(C_513_.f_20452_, C_4340_::new);
      a(C_513_.f_217012_, C_213427_::new);
      a(C_513_.f_20472_, p_174079_0_ -> new C_4362_(p_174079_0_, C_141656_.f_171149_));
      a(C_513_.f_20453_, C_4341_::new);
      a(C_513_.f_20454_, p_174077_0_ -> new C_4342_(p_174077_0_, 6.0F));
      a(C_513_.f_147033_, ItemFrameRenderer::new);
      a(C_513_.f_147034_, p_174075_0_ -> new C_141745_(p_174075_0_, new C_3873_(p_174075_0_.a(C_141656_.f_171154_))));
      a(C_513_.f_147035_, C_141746_::new);
      a(C_513_.f_20455_, C_4343_::new);
      a(C_513_.f_20456_, C_4344_::new);
      a(C_513_.f_20473_, p_174073_0_ -> new C_4362_(p_174073_0_, C_141656_.f_171185_));
      a(C_513_.f_20457_, C_4345_::new);
      a(C_513_.f_20458_, C_4347_::new);
      a(C_513_.f_20459_, C_4349_::new);
      a(C_513_.f_271243_, C_141748_::new);
      a(C_513_.f_20460_, C_4351_::new);
      a(C_513_.f_20461_, C_4352_::new);
      a(C_513_.f_268643_, C_268379_::new);
      a(C_513_.f_20462_, ItemFrameRenderer::new);
      a(C_513_.f_314497_, C_313588_::new);
      a(C_513_.f_20464_, C_4355_::new);
      a(C_513_.f_20465_, C_4356_::new);
      a(C_513_.f_20466_, p_174071_0_ -> new C_4359_(p_174071_0_, C_141656_.f_171194_));
      a(C_513_.f_20467_, C_4360_::new);
      a(C_513_.f_20468_, C_4361_::new);
      a(C_513_.f_147036_, C_141748_::new);
      a(C_513_.f_20469_, p_174069_0_ -> new C_4362_(p_174069_0_, C_141656_.f_171198_));
      a(C_513_.f_20504_, C_4364_::new);
      a(C_513_.f_20503_, p_174067_0_ -> new C_4316_(p_174067_0_, 0.92F, C_141656_.f_171200_));
      a(C_513_.f_20505_, C_4365_::new);
      a(C_513_.f_20506_, C_4366_::new);
      a(C_513_.f_20507_, C_4372_::new);
      a(C_513_.f_20508_, C_4373_::new);
      a(C_513_.f_20509_, C_4374_::new);
      a(C_513_.f_20510_, C_4375_::new);
      a(C_513_.f_20511_, p_174065_0_ -> new C_4376_(p_174065_0_, C_141656_.f_171206_, C_141656_.f_171158_, C_141656_.f_171159_, false));
      a(C_513_.f_20512_, p_174063_0_ -> new C_4376_(p_174063_0_, C_141656_.f_171207_, C_141656_.f_171156_, C_141656_.f_171157_, false));
      a(C_513_.f_20513_, C_4377_::new);
      a(C_513_.f_20514_, C_4378_::new);
      a(C_513_.f_20486_, C_4396_::new);
      a(C_513_.f_20516_, C_4379_::new);
      a(C_513_.f_20517_, C_4380_::new);
      a(C_513_.f_20518_, C_4381_::new);
      a(C_513_.f_20519_, C_4383_::new);
      a(C_513_.f_20520_, C_4384_::new);
      a(C_513_.f_20521_, C_4386_::new);
      a(C_513_.f_20522_, C_4385_::new);
      a(C_513_.f_20523_, C_4387_::new);
      a(C_513_.f_20524_, C_4388_::new);
      a(C_513_.f_20525_, p_174061_0_ -> new C_4404_(p_174061_0_, C_141656_.f_171237_));
      a(C_513_.f_20526_, C_4389_::new);
      a(C_513_.f_20527_, p_174059_0_ -> new C_4396_(p_174059_0_, 0.75F, true));
      a(C_513_.f_271264_, C_271083_::new);
      a(C_513_.f_20477_, C_4396_::new);
      a(C_513_.f_20528_, C_4390_::new);
      a(C_513_.f_20474_, p_174057_0_ -> new C_4362_(p_174057_0_, C_141656_.f_171244_));
      a(C_513_.f_20478_, C_4391_::new);
      a(C_513_.f_20479_, C_4392_::new);
      a(C_513_.f_20480_, p_174055_0_ -> new C_4393_(p_174055_0_, new C_3873_(p_174055_0_.a(C_141656_.f_171246_))));
      a(C_513_.f_20481_, C_4394_::new);
      a(C_513_.f_20482_, C_4395_::new);
      a(C_513_.f_217013_, C_213428_::new);
      a(C_513_.f_268607_, C_268365_::new);
      a(C_513_.f_20515_, C_4401_::new);
      a(C_513_.f_20475_, TntMinecartRenderer::new);
      a(C_513_.f_20488_, p_174053_0_ -> new C_4359_(p_174053_0_, C_141656_.f_171254_));
      a(C_513_.f_20487_, C_4397_::new);
      a(C_513_.f_20489_, C_4402_::new);
      a(C_513_.f_20490_, C_4403_::new);
      a(C_513_.f_20491_, C_4405_::new);
      a(C_513_.f_20492_, C_4406_::new);
      a(C_513_.f_20493_, C_4407_::new);
      a(C_513_.f_217015_, C_213431_::new);
      a(C_513_.f_20494_, C_4409_::new);
      a(C_513_.f_303421_, C_301983_::new);
      a(C_513_.f_20495_, C_4410_::new);
      a(C_513_.f_20496_, C_4411_::new);
      a(C_513_.f_20497_, C_4412_::new);
      a(C_513_.f_20498_, C_4413_::new);
      a(C_513_.f_20499_, C_4414_::new);
      a(C_513_.f_20500_, C_4415_::new);
      a(C_513_.f_20501_, C_4416_::new);
      a(C_513_.f_20502_, p_234611_0_ -> new C_4404_(p_234611_0_, C_141656_.f_171225_));
      a(C_513_.f_20530_, C_4417_::new);
      a(C_513_.f_20531_, p_234609_0_ -> new C_4376_(p_234609_0_, C_141656_.f_171231_, C_141656_.f_171232_, C_141656_.f_171233_, true));
   }
}
