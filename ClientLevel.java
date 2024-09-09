import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_123_;
import net.minecraft.src.C_125_;
import net.minecraft.src.C_1325_;
import net.minecraft.src.C_137_;
import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1394_;
import net.minecraft.src.C_141183_;
import net.minecraft.src.C_141286_;
import net.minecraft.src.C_141289_;
import net.minecraft.src.C_141290_;
import net.minecraft.src.C_141298_;
import net.minecraft.src.C_141307_;
import net.minecraft.src.C_1441_;
import net.minecraft.src.C_1470_;
import net.minecraft.src.C_1583_;
import net.minecraft.src.C_1593_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1642_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_183037_;
import net.minecraft.src.C_183041_;
import net.minecraft.src.C_203228_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_213404_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_2175_;
import net.minecraft.src.C_243488_;
import net.minecraft.src.C_256686_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_2690_;
import net.minecraft.src.C_2691_;
import net.minecraft.src.C_2771_;
import net.minecraft.src.C_2796_;
import net.minecraft.src.C_3072_;
import net.minecraft.src.C_3076_;
import net.minecraft.src.C_313617_;
import net.minecraft.src.C_313884_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3424_;
import net.minecraft.src.C_3902_;
import net.minecraft.src.C_3905_;
import net.minecraft.src.C_4108_;
import net.minecraft.src.C_4112_;
import net.minecraft.src.C_442_;
import net.minecraft.src.C_4557_;
import net.minecraft.src.C_4561_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4685_;
import net.minecraft.src.C_468_;
import net.minecraft.src.C_4750_;
import net.minecraft.src.C_4756_;
import net.minecraft.src.C_4759_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_4982_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5028_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_5264_;
import net.minecraft.src.C_141307_.C_213130_;
import net.minecraft.src.C_1583_.C_1585_;
import net.minecraft.src.C_3988_.C_3994_;
import net.minecraft.src.C_4675_.C_4681_;
import net.minecraft.src.C_507_.C_141065_;
import net.minecraftforge.client.model.data.ModelDataManager;
import net.minecraftforge.client.model.lighting.QuadLighter;
import net.minecraftforge.entity.PartEntity;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.DynamicLights;
import net.optifine.RandomEntities;
import net.optifine.Vec3M;
import net.optifine.override.PlayerControllerOF;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.Shaders;
import org.slf4j.Logger;

public class ClientLevel extends C_1596_ {
   private static final Logger a = LogUtils.getLogger();
   private static final double b = 0.05;
   private static final int c = 10;
   private static final int d = 1000;
   final C_141286_ e = new C_141286_();
   private final C_141298_<C_507_> f = new C_141298_(C_507_.class, new ClientLevel.b());
   private final C_3902_ D;
   private final LevelRenderer E;
   private final ClientLevel.a F;
   private final C_4112_ G;
   private final TickRateManager H;
   private final C_3391_ I = C_3391_.m_91087_();
   final List<AbstractClientPlayer> J = Lists.newArrayList();
   private final Map<C_313617_, C_2771_> K = Maps.newHashMap();
   private static final long L = 16777215L;
   private int M;
   private final Object2ObjectArrayMap<C_4982_, C_3424_> N = Util.a(new Object2ObjectArrayMap(3), mapIn -> {
      mapIn.put(C_4108_.f_108789_, new C_3424_(posIn -> this.b(posIn, C_4108_.f_108789_)));
      mapIn.put(C_4108_.f_108790_, new C_3424_(posIn -> this.b(posIn, C_4108_.f_108790_)));
      mapIn.put(C_4108_.f_108791_, new C_3424_(posIn -> this.b(posIn, C_4108_.f_108791_)));
      Reflector.ColorResolverManager_registerBlockTintCaches.call(this, mapIn);
   });
   private final ClientChunkCache O;
   private final Deque<Runnable> P = Queues.newArrayDeque();
   private int Q;
   private final C_213404_ R = new C_213404_();
   private static final Set<C_1381_> S = Set.of(C_1394_.f_42127_, C_1394_.f_151033_);
   private final Int2ObjectMap<PartEntity<?>> partEntities = new Int2ObjectOpenHashMap();
   private final ModelDataManager modelDataManager = new ModelDataManager(this);
   private boolean playerUpdate = false;

   public void b(int sequenceIn) {
      this.R.a(sequenceIn, this);
   }

   public void b(C_4675_ posIn, BlockState stateIn, int flagsIn) {
      if (!this.R.a(posIn, stateIn)) {
         super.a(posIn, stateIn, flagsIn, 512);
      }
   }

   public void a(C_4675_ posIn, BlockState stateIn, Vec3 vecIn) {
      BlockState blockstate = this.a_(posIn);
      if (blockstate != stateIn) {
         this.a(posIn, stateIn, 19);
         C_1141_ player = this.I.f_91074_;
         if (this == player.m_9236_() && player.a(posIn, stateIn)) {
            player.m_20248_(vecIn.c, vecIn.d, vecIn.e);
         }
      }
   }

   C_213404_ a() {
      return this.R;
   }

   public boolean a(C_4675_ posIn, BlockState stateIn, int flagsIn, int updateCountIn) {
      if (this.R.m_233872_()) {
         BlockState blockstate = this.a_(posIn);
         boolean flag = super.a(posIn, stateIn, flagsIn, updateCountIn);
         if (flag) {
            this.R.a(posIn, blockstate, this.I.f_91074_);
         }

         return flag;
      } else {
         return super.a(posIn, stateIn, flagsIn, updateCountIn);
      }
   }

   public ClientLevel(
      C_3902_ connectionIn,
      ClientLevel.a worldInfoIn,
      C_5264_<C_1596_> dimIn,
      C_203228_<C_2175_> dimTypeIn,
      int viewDistIn,
      int simDistIn,
      Supplier<C_442_> profilerIn,
      LevelRenderer worldRendererIn,
      boolean debugIn,
      long seedIn
   ) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: Constructor net/optifine/override/PlayerControllerOF.<init>(Lnet/minecraft/src/C_3391_;Lnet/minecraft/src/C_3902_;)V not found
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:49)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:957)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:460)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1018)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.AssignmentExprent.toJava(AssignmentExprent.java:154)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:241)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.SequenceStatement.toJava(SequenceStatement.java:107)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 000: aload 0
      // 001: aload 2
      // 002: aload 3
      // 003: aload 1
      // 004: invokevirtual net/minecraft/src/C_3902_.m_105152_ ()Lnet/minecraft/src/C_4706_$C_203240_;
      // 007: aload 4
      // 009: aload 7
      // 00b: bipush 1
      // 00c: iload 9
      // 00e: lload 10
      // 010: ldc 1000000
      // 012: invokespecial net/minecraft/src/C_1596_.<init> (Lnet/minecraft/src/C_2796_;Lnet/minecraft/src/C_5264_;Lnet/minecraft/src/C_4706_;Lnet/minecraft/src/C_203228_;Ljava/util/function/Supplier;ZZJI)V
      // 015: aload 0
      // 016: new net/minecraft/src/C_141286_
      // 019: dup
      // 01a: invokespecial net/minecraft/src/C_141286_.<init> ()V
      // 01d: putfield ClientLevel.e Lnet/minecraft/src/C_141286_;
      // 020: aload 0
      // 021: new net/minecraft/src/C_141298_
      // 024: dup
      // 025: ldc net/minecraft/src/C_507_
      // 027: new ClientLevel$b
      // 02a: dup
      // 02b: aload 0
      // 02c: invokespecial ClientLevel$b.<init> (LClientLevel;)V
      // 02f: invokespecial net/minecraft/src/C_141298_.<init> (Ljava/lang/Class;Lnet/minecraft/src/C_141289_;)V
      // 032: putfield ClientLevel.f Lnet/minecraft/src/C_141298_;
      // 035: aload 0
      // 036: invokestatic net/minecraft/src/C_3391_.m_91087_ ()Lnet/minecraft/src/C_3391_;
      // 039: putfield ClientLevel.I Lnet/minecraft/src/C_3391_;
      // 03c: aload 0
      // 03d: invokestatic com/google/common/collect/Lists.newArrayList ()Ljava/util/ArrayList;
      // 040: putfield ClientLevel.J Ljava/util/List;
      // 043: aload 0
      // 044: invokestatic com/google/common/collect/Maps.newHashMap ()Ljava/util/HashMap;
      // 047: putfield ClientLevel.K Ljava/util/Map;
      // 04a: aload 0
      // 04b: new it/unimi/dsi/fastutil/objects/Object2ObjectArrayMap
      // 04e: dup
      // 04f: bipush 3
      // 050: invokespecial it/unimi/dsi/fastutil/objects/Object2ObjectArrayMap.<init> (I)V
      // 053: aload 0
      // 054: invokedynamic accept (LClientLevel;)Ljava/util/function/Consumer; bsm=java/lang/invoke/LambdaMetafactory.metafactory (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; args=[ (Ljava/lang/Object;)V, ClientLevel.lambda$new$3 (Lit/unimi/dsi/fastutil/objects/Object2ObjectArrayMap;)V, (Lit/unimi/dsi/fastutil/objects/Object2ObjectArrayMap;)V ]
      // 059: invokestatic Util.a (Ljava/lang/Object;Ljava/util/function/Consumer;)Ljava/lang/Object;
      // 05c: checkcast it/unimi/dsi/fastutil/objects/Object2ObjectArrayMap
      // 05f: putfield ClientLevel.N Lit/unimi/dsi/fastutil/objects/Object2ObjectArrayMap;
      // 062: aload 0
      // 063: invokestatic com/google/common/collect/Queues.newArrayDeque ()Ljava/util/ArrayDeque;
      // 066: putfield ClientLevel.P Ljava/util/Deque;
      // 069: aload 0
      // 06a: new net/minecraft/src/C_213404_
      // 06d: dup
      // 06e: invokespecial net/minecraft/src/C_213404_.<init> ()V
      // 071: putfield ClientLevel.R Lnet/minecraft/src/C_213404_;
      // 074: aload 0
      // 075: new it/unimi/dsi/fastutil/ints/Int2ObjectOpenHashMap
      // 078: dup
      // 079: invokespecial it/unimi/dsi/fastutil/ints/Int2ObjectOpenHashMap.<init> ()V
      // 07c: putfield ClientLevel.partEntities Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;
      // 07f: aload 0
      // 080: new net/minecraftforge/client/model/data/ModelDataManager
      // 083: dup
      // 084: aload 0
      // 085: invokespecial net/minecraftforge/client/model/data/ModelDataManager.<init> (Lnet/minecraft/src/C_1596_;)V
      // 088: putfield ClientLevel.modelDataManager Lnet/minecraftforge/client/model/data/ModelDataManager;
      // 08b: aload 0
      // 08c: bipush 0
      // 08d: putfield ClientLevel.playerUpdate Z
      // 090: aload 0
      // 091: aload 1
      // 092: putfield ClientLevel.D Lnet/minecraft/src/C_3902_;
      // 095: aload 0
      // 096: new ClientChunkCache
      // 099: dup
      // 09a: aload 0
      // 09b: iload 5
      // 09d: invokespecial ClientChunkCache.<init> (LClientLevel;I)V
      // 0a0: putfield ClientLevel.O LClientChunkCache;
      // 0a3: aload 0
      // 0a4: new TickRateManager
      // 0a7: dup
      // 0a8: invokespecial TickRateManager.<init> ()V
      // 0ab: putfield ClientLevel.H LTickRateManager;
      // 0ae: aload 0
      // 0af: aload 2
      // 0b0: putfield ClientLevel.F LClientLevel$a;
      // 0b3: aload 0
      // 0b4: aload 8
      // 0b6: putfield ClientLevel.E LLevelRenderer;
      // 0b9: aload 0
      // 0ba: aload 4
      // 0bc: invokeinterface net/minecraft/src/C_203228_.m_203334_ ()Ljava/lang/Object; 1
      // 0c1: checkcast net/minecraft/src/C_2175_
      // 0c4: invokestatic net/minecraft/src/C_4112_.m_108876_ (Lnet/minecraft/src/C_2175_;)Lnet/minecraft/src/C_4112_;
      // 0c7: putfield ClientLevel.G Lnet/minecraft/src/C_4112_;
      // 0ca: aload 0
      // 0cb: new net/minecraft/src/C_4675_
      // 0ce: dup
      // 0cf: bipush 8
      // 0d1: bipush 64
      // 0d3: bipush 8
      // 0d5: invokespecial net/minecraft/src/C_4675_.<init> (III)V
      // 0d8: fconst_0
      // 0d9: invokevirtual ClientLevel.a (Lnet/minecraft/src/C_4675_;F)V
      // 0dc: aload 0
      // 0dd: iload 6
      // 0df: putfield ClientLevel.Q I
      // 0e2: aload 0
      // 0e3: invokevirtual ClientLevel.m_46465_ ()V
      // 0e6: aload 0
      // 0e7: invokevirtual ClientLevel.m_46466_ ()V
      // 0ea: getstatic net/optifine/reflect/Reflector.CapabilityProvider_gatherCapabilities Lnet/optifine/reflect/ReflectorMethod;
      // 0ed: invokevirtual net/optifine/reflect/ReflectorMethod.exists ()Z
      // 0f0: ifeq 10f
      // 0f3: getstatic net/optifine/reflect/Reflector.CapabilityProvider Lnet/optifine/reflect/ReflectorClass;
      // 0f6: invokevirtual net/optifine/reflect/ReflectorClass.getTargetClass ()Ljava/lang/Class;
      // 0f9: aload 0
      // 0fa: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 0fd: invokevirtual java/lang/Class.isAssignableFrom (Ljava/lang/Class;)Z
      // 100: ifeq 10f
      // 103: aload 0
      // 104: getstatic net/optifine/reflect/Reflector.CapabilityProvider_gatherCapabilities Lnet/optifine/reflect/ReflectorMethod;
      // 107: bipush 0
      // 108: anewarray 369
      // 10b: invokestatic net/optifine/reflect/Reflector.call (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
      // 10e: pop
      // 10f: getstatic net/optifine/reflect/Reflector.LevelEvent_Load_Constructor Lnet/optifine/reflect/ReflectorConstructor;
      // 112: bipush 1
      // 113: anewarray 369
      // 116: dup
      // 117: bipush 0
      // 118: aload 0
      // 119: aastore
      // 11a: invokestatic net/optifine/reflect/Reflector.postForgeBusEvent (Lnet/optifine/reflect/ReflectorConstructor;[Ljava/lang/Object;)Z
      // 11d: pop
      // 11e: aload 0
      // 11f: getfield ClientLevel.I Lnet/minecraft/src/C_3391_;
      // 122: getfield net/minecraft/src/C_3391_.f_91072_ Lnet/minecraft/src/C_3905_;
      // 125: ifnull 15b
      // 128: aload 0
      // 129: getfield ClientLevel.I Lnet/minecraft/src/C_3391_;
      // 12c: getfield net/minecraft/src/C_3391_.f_91072_ Lnet/minecraft/src/C_3905_;
      // 12f: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 132: ldc_w net/minecraft/src/C_3905_
      // 135: if_acmpne 15b
      // 138: aload 0
      // 139: getfield ClientLevel.I Lnet/minecraft/src/C_3391_;
      // 13c: new net/optifine/override/PlayerControllerOF
      // 13f: dup
      // 140: aload 0
      // 141: getfield ClientLevel.I Lnet/minecraft/src/C_3391_;
      // 144: aload 0
      // 145: getfield ClientLevel.D Lnet/minecraft/src/C_3902_;
      // 148: invokespecial net/optifine/override/PlayerControllerOF.<init> (Lnet/minecraft/src/C_3391_;Lnet/minecraft/src/C_3902_;)V
      // 14b: putfield net/minecraft/src/C_3391_.f_91072_ Lnet/minecraft/src/C_3905_;
      // 14e: aload 0
      // 14f: getfield ClientLevel.I Lnet/minecraft/src/C_3391_;
      // 152: getfield net/minecraft/src/C_3391_.f_91072_ Lnet/minecraft/src/C_3905_;
      // 155: checkcast net/optifine/override/PlayerControllerOF
      // 158: invokestatic net/optifine/CustomGuis.setPlayerControllerOF (Lnet/optifine/override/PlayerControllerOF;)V
      // 15b: return
   }

   public void a(Runnable updateIn) {
      this.P.add(updateIn);
   }

   public void b() {
      int i = this.P.size();
      int j = i < 1000 ? Math.max(10, i / 10) : i;

      for (int k = 0; k < j; k++) {
         Runnable runnable = (Runnable)this.P.poll();
         if (runnable == null) {
            break;
         }

         runnable.run();
      }
   }

   public boolean c() {
      return this.P.isEmpty();
   }

   public C_4112_ d() {
      return this.G;
   }

   public void a(BooleanSupplier p_104726_1_) {
      this.m_6857_().m_61969_();
      if (this.s().i()) {
         this.n();
      }

      if (this.M > 0) {
         this.m_6580_(this.M - 1);
      }

      this.m_46473_().m_6180_("blocks");
      this.O.m_201698_(p_104726_1_, true);
      this.m_46473_().m_7238_();
   }

   private void n() {
      this.b(this.f_46442_.m_6793_() + 1L);
      if (this.f_46442_.m_5470_().m_46207_(C_1583_.f_46140_)) {
         this.c(this.f_46442_.m_6792_() + 1L);
      }
   }

   public void b(long timeIn) {
      this.F.a(timeIn);
   }

   public void c(long time) {
      if (time < 0L) {
         time = -time;
         ((C_1585_)this.m_46469_().m_46170_(C_1583_.f_46140_)).m_46246_(false, null);
      } else {
         ((C_1585_)this.m_46469_().m_46170_(C_1583_.f_46140_)).m_46246_(true, null);
      }

      this.F.b(time);
   }

   public Iterable<C_507_> e() {
      return this.m_142646_().m_142273_();
   }

   public void f() {
      C_442_ profilerfiller = this.m_46473_();
      profilerfiller.m_6180_("entities");
      this.e.m_156910_(entityIn -> {
         if (!entityIn.m_213877_() && !entityIn.m_20159_() && !this.H.a(entityIn)) {
            this.m_46653_(this::a, entityIn);
         }
      });
      profilerfiller.m_7238_();
      this.m_46463_();
   }

   public boolean m_183599_(C_507_ entityIn) {
      return entityIn.dq().a(this.I.f_91074_.dq()) <= this.Q;
   }

   public void a(C_507_ entityIn) {
      entityIn.m_146867_();
      entityIn.f_19797_++;
      this.m_46473_().m_6521_(() -> C_256712_.f_256780_.b(entityIn.m_6095_()).toString());
      if (ReflectorForge.canUpdate(entityIn)) {
         entityIn.m_8119_();
      }

      if (entityIn.m_213877_()) {
         this.onEntityRemoved(entityIn);
      }

      this.m_46473_().m_7238_();

      for (C_507_ entity : entityIn.m_20197_()) {
         this.a(entityIn, entity);
      }
   }

   private void a(C_507_ entityIn, C_507_ entityPassangerIn) {
      if (!entityPassangerIn.m_213877_() && entityPassangerIn.m_20202_() == entityIn) {
         if (entityPassangerIn instanceof C_1141_ || this.e.m_156914_(entityPassangerIn)) {
            entityPassangerIn.m_146867_();
            entityPassangerIn.f_19797_++;
            entityPassangerIn.m_6083_();

            for (C_507_ entity : entityPassangerIn.m_20197_()) {
               this.a(entityPassangerIn, entity);
            }
         }
      } else {
         entityPassangerIn.m_8127_();
      }
   }

   public void a(C_2137_ chunkIn) {
      chunkIn.m_187957_();
      this.O.m_7827_().a(chunkIn.f(), false);
      this.f.b(chunkIn.f());
   }

   public void a(ChunkPos chunkPosIn) {
      this.N.forEach((resolverIn, cacheIn) -> cacheIn.m_92655_(chunkPosIn.e, chunkPosIn.f));
      this.f.a(chunkPosIn);
      this.E.a(chunkPosIn);
   }

   public void g() {
      this.N.forEach((resolverIn, cacheIn) -> cacheIn.m_92654_());
   }

   public boolean m_7232_(int chunkX, int chunkZ) {
      return true;
   }

   public int h() {
      return this.f.m_157657_();
   }

   public void c(C_507_ entityIdIn) {
      if (!Reflector.EntityJoinLevelEvent_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.EntityJoinLevelEvent_Constructor, entityIdIn, this)) {
         this.a(entityIdIn.m_19879_(), C_141065_.DISCARDED);
         this.f.m_157653_(entityIdIn);
         if (Reflector.IForgeEntity_onAddedToWorld.exists()) {
            Reflector.call(entityIdIn, Reflector.IForgeEntity_onAddedToWorld);
         }

         this.onEntityAdded(entityIdIn);
      }
   }

   public void a(int entityIdIn, C_141065_ reasonIn) {
      C_507_ entity = (C_507_)this.m_142646_().m_142597_(entityIdIn);
      if (entity != null) {
         entity.m_142467_(reasonIn);
         entity.m_142036_();
      }
   }

   @Nullable
   public C_507_ m_6815_(int id) {
      return (C_507_)this.m_142646_().m_142597_(id);
   }

   public void m_7462_() {
      this.D.m_104910_().m_129507_(C_4996_.m_237115_("multiplayer.status.quitting"));
   }

   public void b(int posX, int posY, int posZ) {
      int i = 32;
      C_212974_ randomsource = C_212974_.m_216327_();
      C_1706_ block = this.p();
      C_4681_ blockpos$mutableblockpos = new C_4681_();

      for (int j = 0; j < 667; j++) {
         this.a(posX, posY, posZ, 16, randomsource, block, blockpos$mutableblockpos);
         this.a(posX, posY, posZ, 32, randomsource, block, blockpos$mutableblockpos);
      }
   }

   @Nullable
   private C_1706_ p() {
      if (this.I.f_91072_.m_105295_() == C_1593_.CREATIVE) {
         C_1391_ itemstack = this.I.f_91074_.eT();
         C_1381_ item = itemstack.m_41720_();
         if (S.contains(item) && item instanceof C_1325_ blockitem) {
            return blockitem.m_40614_();
         }
      }

      return null;
   }

   public void a(int posX, int posY, int posZ, int offsetIn, C_212974_ randomIn, @Nullable C_1706_ blockIn, C_4681_ posIn) {
      int i = posX + this.f_46441_.m_188503_(offsetIn) - this.f_46441_.m_188503_(offsetIn);
      int j = posY + this.f_46441_.m_188503_(offsetIn) - this.f_46441_.m_188503_(offsetIn);
      int k = posZ + this.f_46441_.m_188503_(offsetIn) - this.f_46441_.m_188503_(offsetIn);
      posIn.m_122178_(i, j, k);
      BlockState blockstate = this.a_(posIn);
      blockstate.m_60734_().a(blockstate, this, posIn, randomIn);
      C_2691_ fluidstate = this.m_6425_(posIn);
      if (!fluidstate.m_76178_()) {
         fluidstate.m_230558_(this, posIn, randomIn);
         C_4756_ particleoptions = fluidstate.m_76189_();
         if (particleoptions != null && this.f_46441_.m_188503_(10) == 0) {
            boolean flag = blockstate.d(this, posIn, Direction.a);
            C_4675_ blockpos = posIn.m_7495_();
            this.a(blockpos, this.a_(blockpos), particleoptions, flag);
         }
      }

      if (blockIn == blockstate.m_60734_()) {
         this.m_7106_(new C_4750_(C_4759_.f_194652_, blockstate), (double)i + 0.5, (double)j + 0.5, (double)k + 0.5, 0.0, 0.0, 0.0);
      }

      if (!blockstate.m_60838_(this, posIn)) {
         ((C_1629_)this.m_204166_(posIn).m_203334_())
            .m_47562_()
            .ifPresent(
               settingsIn -> {
                  if (settingsIn.m_220527_(this.f_46441_)) {
                     this.m_7106_(
                        settingsIn.m_47419_(),
                        (double)posIn.m_123341_() + this.f_46441_.m_188500_(),
                        (double)posIn.m_123342_() + this.f_46441_.m_188500_(),
                        (double)posIn.m_123343_() + this.f_46441_.m_188500_(),
                        0.0,
                        0.0,
                        0.0
                     );
                  }
               }
            );
      }
   }

   private void a(C_4675_ blockPosIn, BlockState blockStateIn, C_4756_ particleDataIn, boolean shapeDownSolid) {
      if (blockStateIn.m_60819_().m_76178_()) {
         C_3072_ voxelshape = blockStateIn.m_60812_(this, blockPosIn);
         double d0 = voxelshape.c(Direction.a.b);
         if (d0 < 1.0) {
            if (shapeDownSolid) {
               this.a(
                  (double)blockPosIn.m_123341_(),
                  (double)(blockPosIn.m_123341_() + 1),
                  (double)blockPosIn.m_123343_(),
                  (double)(blockPosIn.m_123343_() + 1),
                  (double)(blockPosIn.m_123342_() + 1) - 0.05,
                  particleDataIn
               );
            }
         } else if (!blockStateIn.m_204336_(C_137_.f_13049_)) {
            double d1 = voxelshape.b(Direction.a.b);
            if (d1 > 0.0) {
               this.a(blockPosIn, particleDataIn, voxelshape, (double)blockPosIn.m_123342_() + d1 - 0.05);
            } else {
               C_4675_ blockpos = blockPosIn.m_7495_();
               BlockState blockstate = this.a_(blockpos);
               C_3072_ voxelshape1 = blockstate.m_60812_(this, blockpos);
               double d2 = voxelshape1.c(Direction.a.b);
               if (d2 < 1.0 && blockstate.m_60819_().m_76178_()) {
                  this.a(blockPosIn, particleDataIn, voxelshape, (double)blockPosIn.m_123342_() - 0.05);
               }
            }
         }
      }
   }

   private void a(C_4675_ posIn, C_4756_ particleDataIn, C_3072_ voxelShapeIn, double y) {
      this.a(
         (double)posIn.m_123341_() + voxelShapeIn.b(Direction.a.a),
         (double)posIn.m_123341_() + voxelShapeIn.c(Direction.a.a),
         (double)posIn.m_123343_() + voxelShapeIn.b(Direction.a.c),
         (double)posIn.m_123343_() + voxelShapeIn.c(Direction.a.c),
         y,
         particleDataIn
      );
   }

   private void a(double xStart, double xEnd, double zStart, double zEnd, double y, C_4756_ particleDataIn) {
      this.m_7106_(particleDataIn, Mth.d(this.f_46441_.m_188500_(), xStart, xEnd), y, Mth.d(this.f_46441_.m_188500_(), zStart, zEnd), 0.0, 0.0, 0.0);
   }

   public C_4909_ a(CrashReport report) {
      C_4909_ crashreportcategory = super.a(report);
      crashreportcategory.m_128165_("Server brand", () -> this.I.f_91074_.f_108617_.m_295034_());
      crashreportcategory.m_128165_("Server type", () -> this.I.V() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server");
      crashreportcategory.m_128165_("Tracked entity count", () -> String.valueOf(this.h()));
      return crashreportcategory;
   }

   public void m_262808_(
      @Nullable C_1141_ player, double x, double y, double z, C_203228_<C_123_> soundIn, C_125_ category, float volume, float pitch, long randomSeedIn
   ) {
      if (Reflector.ForgeEventFactory_onPlaySoundAtPosition.exists()) {
         Object event = Reflector.ForgeEventFactory_onPlaySoundAtPosition.call(this, x, y, z, soundIn, category, volume, pitch);
         if (Reflector.callBoolean(event, Reflector.Event_isCanceled) || Reflector.call(event, Reflector.PlayLevelSoundEvent_getSound) == null) {
            return;
         }

         soundIn = (C_203228_<C_123_>)Reflector.call(event, Reflector.PlayLevelSoundEvent_getSound);
         category = (C_125_)Reflector.call(event, Reflector.PlayLevelSoundEvent_getSource);
         volume = Reflector.callFloat(event, Reflector.PlayLevelSoundEvent_getNewVolume);
         pitch = Reflector.callFloat(event, Reflector.PlayLevelSoundEvent_getNewPitch);
      }

      if (player == this.I.f_91074_) {
         this.a(x, y, z, (C_123_)soundIn.m_203334_(), category, volume, pitch, false, randomSeedIn);
      }
   }

   public void m_213890_(
      @Nullable C_1141_ playerIn, C_507_ entityIn, C_203228_<C_123_> eventIn, C_125_ categoryIn, float volume, float pitch, long randomSeedIn
   ) {
      if (Reflector.ForgeEventFactory_onPlaySoundAtEntity.exists()) {
         Object event = Reflector.ForgeEventFactory_onPlaySoundAtEntity.call(entityIn, eventIn, categoryIn, volume, pitch);
         if (Reflector.callBoolean(event, Reflector.Event_isCanceled) || Reflector.call(event, Reflector.PlayLevelSoundEvent_getSound) == null) {
            return;
         }

         eventIn = (C_203228_<C_123_>)Reflector.call(event, Reflector.PlayLevelSoundEvent_getSound);
         categoryIn = (C_125_)Reflector.call(event, Reflector.PlayLevelSoundEvent_getSource);
         volume = Reflector.callFloat(event, Reflector.PlayLevelSoundEvent_getNewVolume);
         pitch = Reflector.callFloat(event, Reflector.PlayLevelSoundEvent_getNewPitch);
      }

      if (playerIn == this.I.f_91074_) {
         this.I.m_91106_().m_120367_(new C_4557_((C_123_)eventIn.m_203334_(), categoryIn, volume, pitch, entityIn, randomSeedIn));
      }
   }

   public void m_307553_(C_507_ playerIn, C_123_ soundIn, C_125_ sourceIn, float volumeIn, float pitchIn) {
      this.I.m_91106_().m_120367_(new C_4557_(soundIn, sourceIn, volumeIn, pitchIn, playerIn, this.f_46441_.m_188505_()));
   }

   public void m_7785_(double x, double y, double z, C_123_ soundIn, C_125_ category, float volume, float pitch, boolean distanceDelay) {
      this.a(x, y, z, soundIn, category, volume, pitch, distanceDelay, this.f_46441_.m_188505_());
   }

   private void a(double x, double y, double z, C_123_ soundIn, C_125_ category, float volume, float pitch, boolean delayedIn, long randomSeedIn) {
      double d0 = this.I.j.l().b().c(x, y, z);
      C_4561_ simplesoundinstance = new C_4561_(soundIn, category, volume, pitch, C_212974_.m_216335_(randomSeedIn), x, y, z);
      if (delayedIn && d0 > 100.0) {
         double d1 = Math.sqrt(d0) / 40.0;
         this.I.m_91106_().m_120369_(simplesoundinstance, (int)(d1 * 20.0));
      } else {
         this.I.m_91106_().m_120367_(simplesoundinstance);
      }
   }

   public void m_7228_(double x, double y, double z, double motionX, double motionY, double motionZ, List<C_313884_> compound) {
      if (compound.isEmpty()) {
         for (int i = 0; i < this.f_46441_.m_188503_(3) + 2; i++) {
            this.m_7106_(C_4759_.f_123759_, x, y, z, this.f_46441_.m_188583_() * 0.05, 0.005, this.f_46441_.m_188583_() * 0.05);
         }
      } else {
         this.I.g.a(new C_3994_(this, x, y, z, motionX, motionY, motionZ, this.I.g, compound));
      }
   }

   public void m_5503_(C_5028_<?> packetIn) {
      this.D.m_295327_(packetIn);
   }

   public C_1470_ m_7465_() {
      return this.D.m_105141_();
   }

   public TickRateManager s() {
      return this.H;
   }

   public C_183041_<C_1706_> m_183326_() {
      return C_183037_.m_193145_();
   }

   public C_183041_<C_2690_> m_183324_() {
      return C_183037_.m_193145_();
   }

   public ClientChunkCache i() {
      return this.O;
   }

   public boolean a(C_4675_ pos, BlockState newState, int flags) {
      this.playerUpdate = this.isPlayerActing();
      boolean res = super.a(pos, newState, flags);
      this.playerUpdate = false;
      return res;
   }

   private boolean isPlayerActing() {
      return this.I.f_91072_ instanceof PlayerControllerOF pcof ? pcof.isActing() : false;
   }

   public boolean isPlayerUpdate() {
      return this.playerUpdate;
   }

   public void onEntityAdded(C_507_ entityIn) {
      RandomEntities.entityLoaded(entityIn, this);
      if (Config.isDynamicLights()) {
         DynamicLights.entityAdded(entityIn, Config.getRenderGlobal());
      }
   }

   public void onEntityRemoved(C_507_ entityIn) {
      RandomEntities.entityUnloaded(entityIn, this);
      if (Config.isDynamicLights()) {
         DynamicLights.entityRemoved(entityIn, Config.getRenderGlobal());
      }
   }

   @Nullable
   public C_2771_ m_7489_(C_313617_ mapName) {
      return (C_2771_)this.K.get(mapName);
   }

   public void b(C_313617_ keyIn, C_2771_ dataIn) {
      this.K.put(keyIn, dataIn);
   }

   public void m_142325_(C_313617_ nameIn, C_2771_ mapDataIn) {
   }

   public C_313617_ m_7354_() {
      return new C_313617_(0);
   }

   public C_3076_ m_6188_() {
      return this.D.m_323009_();
   }

   public void a(C_4675_ pos, BlockState oldState, BlockState newState, int flags) {
      this.E.a(this, pos, oldState, newState, flags);
   }

   public void b(C_4675_ blockPosIn, BlockState oldState, BlockState newState) {
      this.E.a(blockPosIn, oldState, newState);
   }

   public void c(int sectionX, int sectionY, int sectionZ) {
      this.E.a(sectionX, sectionY, sectionZ);
   }

   public void m_6801_(int breakerId, C_4675_ pos, int progress) {
      this.E.c(breakerId, pos, progress);
   }

   public void m_6798_(int id, C_4675_ pos, int data) {
      this.E.a(id, pos, data);
   }

   public void m_5898_(@Nullable C_1141_ player, int type, C_4675_ pos, int data) {
      try {
         this.E.b(type, pos, data);
      } catch (Throwable var8) {
         CrashReport crashreport = CrashReport.a(var8, "Playing level event");
         C_4909_ crashreportcategory = crashreport.a("Level event being played");
         crashreportcategory.m_128159_("Block coordinates", C_4909_.m_178947_(this, pos));
         crashreportcategory.m_128159_("Event source", player);
         crashreportcategory.m_128159_("Event type", type);
         crashreportcategory.m_128159_("Event data", data);
         throw new C_5204_(crashreport);
      }
   }

   public void m_7106_(C_4756_ particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.E.a(particleData, particleData.m_6012_().m_123742_(), x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void m_6493_(C_4756_ particleData, boolean forceAlwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.E.a(particleData, particleData.m_6012_().m_123742_() || forceAlwaysRender, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void m_7107_(C_4756_ particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.E.a(particleData, false, true, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void m_6485_(C_4756_ particleData, boolean ignoreRange, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.E.a(particleData, particleData.m_6012_().m_123742_() || ignoreRange, true, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public List<AbstractClientPlayer> m_6907_() {
      return this.J;
   }

   public C_203228_<C_1629_> m_203675_(int x, int y, int z) {
      return this.m_9598_().m_175515_(C_256686_.f_256952_).m_246971_(Biomes.b);
   }

   public float g(float partialTicks) {
      float f = this.m_46942_(partialTicks);
      float f1 = 1.0F - (Mth.b(f * (float) (Math.PI * 2)) * 2.0F + 0.2F);
      f1 = Mth.a(f1, 0.0F, 1.0F);
      f1 = 1.0F - f1;
      f1 *= 1.0F - this.m_46722_(partialTicks) * 5.0F / 16.0F;
      f1 *= 1.0F - this.m_46661_(partialTicks) * 5.0F / 16.0F;
      return f1 * 0.8F + 0.2F;
   }

   public Vec3 a(Vec3 posIn, float partialTicksIn) {
      float f = this.m_46942_(partialTicksIn);
      Vec3 vec3 = posIn.a(2.0, 2.0, 2.0).a(0.25);
      C_1642_ biomemanager = this.m_7062_();
      Vec3M vecCol = new Vec3M(0.0, 0.0, 0.0);
      Vec3 vec31 = CubicSampler.sampleM(vec3, (xIn, yIn, zIn) -> vecCol.fromRgbM(((C_1629_)biomemanager.m_204210_(xIn, yIn, zIn).m_203334_()).m_47463_()));
      float f1 = Mth.b(f * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      f1 = Mth.a(f1, 0.0F, 1.0F);
      float f2 = (float)vec31.c * f1;
      float f3 = (float)vec31.d * f1;
      float f4 = (float)vec31.e * f1;
      float f5 = this.m_46722_(partialTicksIn);
      if (f5 > 0.0F) {
         float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.6F;
         float f7 = 1.0F - f5 * 0.75F;
         f2 = f2 * f7 + f6 * (1.0F - f7);
         f3 = f3 * f7 + f6 * (1.0F - f7);
         f4 = f4 * f7 + f6 * (1.0F - f7);
      }

      float f9 = this.m_46661_(partialTicksIn);
      if (f9 > 0.0F) {
         float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.2F;
         float f8 = 1.0F - f9 * 0.75F;
         f2 = f2 * f8 + f10 * (1.0F - f8);
         f3 = f3 * f8 + f10 * (1.0F - f8);
         f4 = f4 * f8 + f10 * (1.0F - f8);
      }

      int i = this.j();
      if (i > 0) {
         float f11 = (float)i - partialTicksIn;
         if (f11 > 1.0F) {
            f11 = 1.0F;
         }

         f11 *= 0.45F;
         f2 = f2 * (1.0F - f11) + 0.8F * f11;
         f3 = f3 * (1.0F - f11) + 0.8F * f11;
         f4 = f4 * (1.0F - f11) + 1.0F * f11;
      }

      return new Vec3((double)f2, (double)f3, (double)f4);
   }

   public Vec3 h(float partialTicks) {
      float f = this.m_46942_(partialTicks);
      float f1 = Mth.b(f * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      f1 = Mth.a(f1, 0.0F, 1.0F);
      float f2 = 1.0F;
      float f3 = 1.0F;
      float f4 = 1.0F;
      float f5 = this.m_46722_(partialTicks);
      if (f5 > 0.0F) {
         float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.6F;
         float f7 = 1.0F - f5 * 0.95F;
         f2 = f2 * f7 + f6 * (1.0F - f7);
         f3 = f3 * f7 + f6 * (1.0F - f7);
         f4 = f4 * f7 + f6 * (1.0F - f7);
      }

      f2 *= f1 * 0.9F + 0.1F;
      f3 *= f1 * 0.9F + 0.1F;
      f4 *= f1 * 0.85F + 0.15F;
      float f9 = this.m_46661_(partialTicks);
      if (f9 > 0.0F) {
         float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.2F;
         float f8 = 1.0F - f9 * 0.95F;
         f2 = f2 * f8 + f10 * (1.0F - f8);
         f3 = f3 * f8 + f10 * (1.0F - f8);
         f4 = f4 * f8 + f10 * (1.0F - f8);
      }

      return new Vec3((double)f2, (double)f3, (double)f4);
   }

   public float i(float partialTicks) {
      float f = this.m_46942_(partialTicks);
      float f1 = 1.0F - (Mth.b(f * (float) (Math.PI * 2)) * 2.0F + 0.25F);
      f1 = Mth.a(f1, 0.0F, 1.0F);
      return f1 * f1 * 0.5F;
   }

   public int j() {
      return this.I.m.b().c() ? 0 : this.M;
   }

   public void m_6580_(int timeFlashIn) {
      this.M = timeFlashIn;
   }

   public float a(Direction directionIn, boolean shadeIn) {
      boolean flag = this.d().m_108885_();
      boolean shaders = Config.isShaders();
      if (!shadeIn) {
         return flag ? 0.9F : 1.0F;
      } else {
         switch (directionIn) {
            case a:
               return flag ? 0.9F : (shaders ? Shaders.blockLightLevel05 : 0.5F);
            case b:
               return flag ? 0.9F : 1.0F;
            case c:
            case d:
               if (Config.isShaders()) {
                  return Shaders.blockLightLevel08;
               }

               return 0.8F;
            case e:
            case f:
               if (Config.isShaders()) {
                  return Shaders.blockLightLevel06;
               }

               return 0.6F;
            default:
               return 1.0F;
         }
      }
   }

   public int m_6171_(C_4675_ blockPosIn, C_4982_ colorResolverIn) {
      C_3424_ blocktintcache = (C_3424_)this.N.get(colorResolverIn);
      return blocktintcache.m_193812_(blockPosIn);
   }

   public int b(C_4675_ blockPosIn, C_4982_ colorResolverIn) {
      int i = C_3391_.m_91087_().m.E().c();
      if (i == 0) {
         return colorResolverIn.m_130045_(
            CustomColors.fixBiome((C_1629_)this.m_204166_(blockPosIn).m_203334_()), (double)blockPosIn.m_123341_(), (double)blockPosIn.m_123343_()
         );
      } else {
         int j = (i * 2 + 1) * (i * 2 + 1);
         int k = 0;
         int l = 0;
         int i1 = 0;
         C_4685_ cursor3d = new C_4685_(
            blockPosIn.m_123341_() - i,
            blockPosIn.m_123342_(),
            blockPosIn.m_123343_() - i,
            blockPosIn.m_123341_() + i,
            blockPosIn.m_123342_(),
            blockPosIn.m_123343_() + i
         );
         C_4681_ blockpos$mutableblockpos = new C_4681_();

         while (cursor3d.m_122304_()) {
            blockpos$mutableblockpos.m_122178_(cursor3d.m_122305_(), cursor3d.m_122306_(), cursor3d.m_122307_());
            int j1 = colorResolverIn.m_130045_(
               CustomColors.fixBiome((C_1629_)this.m_204166_(blockpos$mutableblockpos).m_203334_()),
               (double)blockpos$mutableblockpos.m_123341_(),
               (double)blockpos$mutableblockpos.m_123343_()
            );
            k += (j1 & 0xFF0000) >> 16;
            l += (j1 & 0xFF00) >> 8;
            i1 += j1 & 0xFF;
         }

         return (k / j & 0xFF) << 16 | (l / j & 0xFF) << 8 | i1 / j & 0xFF;
      }
   }

   public void a(C_4675_ blockPosIn, float angleIn) {
      this.f_46442_.m_7250_(blockPosIn, angleIn);
   }

   public String toString() {
      return "ClientLevel";
   }

   public ClientLevel.a k() {
      return this.F;
   }

   public void a(C_203228_<C_141307_> eventIn, Vec3 posIn, C_213130_ contextIn) {
   }

   protected Map<C_313617_, C_2771_> l() {
      return ImmutableMap.copyOf(this.K);
   }

   protected void a(Map<C_313617_, C_2771_> mapDataIn) {
      this.K.putAll(mapDataIn);
   }

   protected C_141290_<C_507_> m_142646_() {
      return this.f.m_157645_();
   }

   public String m_46464_() {
      return "Chunks[C] W: " + this.O.m_6754_() + " E: " + this.f.m_157664_();
   }

   public void a(C_4675_ blockPosIn, BlockState stateIn) {
      this.I.g.a(blockPosIn, stateIn);
   }

   public void h(int distanceIn) {
      this.Q = distanceIn;
   }

   public int m() {
      return this.Q;
   }

   public C_243488_ m_246046_() {
      return this.D.m_247016_();
   }

   public C_1441_ m_319308_() {
      return this.D.m_320887_();
   }

   public C_141298_ getEntityStorage() {
      return this.f;
   }

   public EntitySectionStorage getSectionStorage() {
      return EntitySection.getSectionStorage(this.f);
   }

   public Collection<PartEntity<?>> getPartEntities() {
      return this.partEntities.values();
   }

   public ModelDataManager getModelDataManager() {
      return this.modelDataManager;
   }

   public float getShade(float normalX, float normalY, float normalZ, boolean shade) {
      boolean constantAmbientLight = this.d().m_108885_();
      if (!shade) {
         return constantAmbientLight ? 0.9F : 1.0F;
      } else {
         return QuadLighter.calculateShade(normalX, normalY, normalZ, constantAmbientLight);
      }
   }

   public static class a implements C_2796_ {
      private final boolean a;
      private final C_1583_ b;
      private final boolean c;
      private C_4675_ d;
      private float e;
      private long f;
      private long g;
      private boolean h;
      private C_468_ i;
      private boolean j;

      public a(C_468_ difficultyIn, boolean hardcoreIn, boolean flatIn) {
         this.i = difficultyIn;
         this.a = hardcoreIn;
         this.c = flatIn;
         this.b = new C_1583_();
      }

      public C_4675_ m_318766_() {
         return this.d;
      }

      public float m_6790_() {
         return this.e;
      }

      public long m_6793_() {
         return this.f;
      }

      public long m_6792_() {
         return this.g;
      }

      public void a(long timeIn) {
         this.f = timeIn;
      }

      public void b(long timeIn) {
         this.g = timeIn;
      }

      public void m_7250_(C_4675_ spawnPoint, float angleIn) {
         this.d = spawnPoint.m_7949_();
         this.e = angleIn;
      }

      public boolean m_6534_() {
         return false;
      }

      public boolean m_6533_() {
         return this.h;
      }

      public void m_5565_(boolean isRaining) {
         this.h = isRaining;
      }

      public boolean m_5466_() {
         return this.a;
      }

      public C_1583_ m_5470_() {
         return this.b;
      }

      public C_468_ m_5472_() {
         return this.i;
      }

      public boolean m_5474_() {
         return this.j;
      }

      public void m_142471_(C_4909_ categoryIn, C_141183_ heightIn) {
         super.m_142471_(categoryIn, heightIn);
      }

      public void a(C_468_ difficultyIn) {
         Reflector.ForgeHooks_onDifficultyChange.callVoid(difficultyIn, this.i);
         this.i = difficultyIn;
      }

      public void a(boolean lockedIn) {
         this.j = lockedIn;
      }

      public double a(C_141183_ accessorIn) {
         return this.c ? (double)accessorIn.m_141937_() : 63.0;
      }

      public float e() {
         return this.c ? 1.0F : 0.03125F;
      }
   }

   final class b implements C_141289_<C_507_> {
      public void a(C_507_ entityIn) {
      }

      public void b(C_507_ entityIn) {
      }

      public void c(C_507_ entityIn) {
         ClientLevel.this.e.m_156908_(entityIn);
      }

      public void d(C_507_ entityIn) {
         ClientLevel.this.e.m_156912_(entityIn);
      }

      public void e(C_507_ entityIn) {
         if (entityIn instanceof AbstractClientPlayer) {
            ClientLevel.this.J.add((AbstractClientPlayer)entityIn);
         }

         if (Reflector.IForgeEntity_isMultipartEntity.exists() && Reflector.IForgeEntity_getParts.exists()) {
            boolean multipartEntity = Reflector.callBoolean(entityIn, Reflector.IForgeEntity_isMultipartEntity);
            if (multipartEntity) {
               PartEntity[] parts = (PartEntity[])Reflector.call(entityIn, Reflector.IForgeEntity_getParts);

               for (PartEntity part : parts) {
                  ClientLevel.this.partEntities.put(part.m_19879_(), part);
               }
            }
         }
      }

      public void f(C_507_ entityIn) {
         entityIn.m_19877_();
         ClientLevel.this.J.remove(entityIn);
         if (Reflector.IForgeEntity_onRemovedFromWorld.exists()) {
            Reflector.call(entityIn, Reflector.IForgeEntity_onRemovedFromWorld);
         }

         if (Reflector.EntityLeaveLevelEvent_Constructor.exists()) {
            Reflector.postForgeBusEvent(Reflector.EntityLeaveLevelEvent_Constructor, entityIn, ClientLevel.this);
         }

         if (Reflector.IForgeEntity_isMultipartEntity.exists() && Reflector.IForgeEntity_getParts.exists()) {
            boolean multipartEntity = Reflector.callBoolean(entityIn, Reflector.IForgeEntity_isMultipartEntity);
            if (multipartEntity) {
               PartEntity[] parts = (PartEntity[])Reflector.call(entityIn, Reflector.IForgeEntity_getParts);

               for (PartEntity part : parts) {
                  ClientLevel.this.partEntities.remove(part.m_19879_(), part);
               }
            }
         }

         ClientLevel.this.onEntityRemoved(entityIn);
      }

      public void g(C_507_ entityIn) {
      }
   }
}
