import com.google.common.base.MoreObjects;
import java.text.MessageFormat;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.src.C_1593_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_182783_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_263626_;
import net.minecraft.src.C_3041_;
import net.minecraft.src.C_3042_;
import net.minecraft.src.C_3043_;
import net.minecraft.src.C_3083_;
import net.minecraft.src.C_3107_;
import net.minecraft.src.C_3140_;
import net.minecraft.src.C_336427_;
import net.minecraft.src.C_3387_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3398_;
import net.minecraft.src.C_3464_;
import net.minecraft.src.C_3574_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_3902_;
import net.minecraft.src.C_4105_;
import net.minecraft.src.C_4426_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4856_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_4943_;
import net.minecraft.src.C_4995_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_3140_.C_3142_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.gui.GuiShaderOptions;

public class KeyboardHandler {
   public static final int a = 10000;
   private final C_3391_ b;
   private final C_3107_ c = new C_3107_();
   private long d = -1L;
   private long e = -1L;
   private long f = -1L;
   private boolean g;
   private static boolean chunkDebugKeys = Boolean.getBoolean("chunk.debug.keys");

   public KeyboardHandler(C_3391_ mcIn) {
      this.b = mcIn;
   }

   private boolean a(int keyIn) {
      switch (keyIn) {
         case 69:
            this.b.f_291316_ = !this.b.f_291316_;
            this.c("SectionPath: {0}", this.b.f_291316_ ? "shown" : "hidden");
            return true;
         case 76:
            this.b.f_90980_ = !this.b.f_90980_;
            this.c("SmartCull: {0}", this.b.f_90980_ ? "enabled" : "disabled");
            return true;
         case 85:
            if (C_3583_.m_96638_()) {
               this.b.f.n();
               this.c("Killed frustum");
            } else if (C_3583_.m_96639_()) {
               if (Config.isShadersShadows()) {
                  this.b.f.captureFrustumShadow();
                  this.c("Captured shadow frustum");
               }
            } else {
               this.b.f.m();
               this.c("Captured frustum");
            }

            return true;
         case 86:
            this.b.f_291317_ = !this.b.f_291317_;
            this.c("SectionVisibility: {0}", this.b.f_291317_ ? "enabled" : "disabled");
            return true;
         case 87:
            this.b.f_167842_ = !this.b.f_167842_;
            this.c("WireFrame: {0}", this.b.f_167842_ ? "enabled" : "disabled");
            return true;
         default:
            return false;
      }
   }

   private void a(C_4856_ formatIn, C_4996_ componentIn) {
      this.b
         .l
         .d()
         .a(
            C_4996_.m_237119_()
               .m_7220_(C_4996_.m_237115_("debug.prefix").m_130944_(new C_4856_[]{formatIn, C_4856_.BOLD}))
               .m_7220_(C_4995_.f_263701_)
               .m_7220_(componentIn)
         );
   }

   private void a(C_4996_ componentIn) {
      this.a(C_4856_.YELLOW, componentIn);
   }

   private void a(String message, Object... args) {
      this.a(C_4996_.m_307043_(message, args));
   }

   private void b(String message, Object... args) {
      this.a(C_4856_.RED, C_4996_.m_307043_(message, args));
   }

   private void c(String message, Object... params) {
      this.a(C_4996_.m_237113_(MessageFormat.format(message, params)));
   }

   private boolean b(int key) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: Constructor net/optifine/shaders/gui/GuiShaderOptions.<init>(Lnet/minecraft/src/C_3583_;LOptions;)V not found
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
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.SwitchStatement.toJava(SwitchStatement.java:185)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:261)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:254)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield KeyboardHandler.d J
      // 004: lconst_0
      // 005: lcmp
      // 006: ifle 01a
      // 009: aload 0
      // 00a: getfield KeyboardHandler.d J
      // 00d: invokestatic Util.c ()J
      // 010: ldc2_w 100
      // 013: lsub
      // 014: lcmp
      // 015: ifge 01a
      // 018: bipush 1
      // 019: ireturn
      // 01a: getstatic KeyboardHandler.chunkDebugKeys Z
      // 01d: ifeq 02a
      // 020: aload 0
      // 021: iload 1
      // 022: invokevirtual KeyboardHandler.a (I)Z
      // 025: ifeq 02a
      // 028: bipush 1
      // 029: ireturn
      // 02a: iload 1
      // 02b: lookupswitch 1168 20 49 169 50 181 51 193 65 205 66 227 67 280 68 442 71 468 72 502 73 572 76 613 78 650 79 1236 80 754 81 824 82 1265 83 1014 84 1103 86 1170 293 1124
      // 0d4: aload 0
      // 0d5: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 0d8: invokevirtual net/minecraft/src/C_3391_.aN ()LDebugScreenOverlay;
      // 0db: invokevirtual DebugScreenOverlay.k ()V
      // 0de: bipush 1
      // 0df: ireturn
      // 0e0: aload 0
      // 0e1: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 0e4: invokevirtual net/minecraft/src/C_3391_.aN ()LDebugScreenOverlay;
      // 0e7: invokevirtual DebugScreenOverlay.j ()V
      // 0ea: bipush 1
      // 0eb: ireturn
      // 0ec: aload 0
      // 0ed: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 0f0: invokevirtual net/minecraft/src/C_3391_.aN ()LDebugScreenOverlay;
      // 0f3: invokevirtual DebugScreenOverlay.i ()V
      // 0f6: bipush 1
      // 0f7: ireturn
      // 0f8: aload 0
      // 0f9: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 0fc: getfield net/minecraft/src/C_3391_.f LLevelRenderer;
      // 0ff: invokevirtual LevelRenderer.f ()V
      // 102: aload 0
      // 103: ldc "debug.reload_chunks.message"
      // 105: bipush 0
      // 106: anewarray 4
      // 109: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 10c: bipush 1
      // 10d: ireturn
      // 10e: aload 0
      // 10f: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 112: invokevirtual net/minecraft/src/C_3391_.ap ()LEntityRenderDispatcher;
      // 115: invokevirtual EntityRenderDispatcher.a ()Z
      // 118: ifne 11f
      // 11b: bipush 1
      // 11c: goto 120
      // 11f: bipush 0
      // 120: istore 2
      // 121: aload 0
      // 122: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 125: invokevirtual net/minecraft/src/C_3391_.ap ()LEntityRenderDispatcher;
      // 128: iload 2
      // 129: invokevirtual EntityRenderDispatcher.b (Z)V
      // 12c: aload 0
      // 12d: iload 2
      // 12e: ifeq 137
      // 131: ldc_w "debug.show_hitboxes.on"
      // 134: goto 13a
      // 137: ldc_w "debug.show_hitboxes.off"
      // 13a: bipush 0
      // 13b: anewarray 4
      // 13e: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 141: bipush 1
      // 142: ireturn
      // 143: aload 0
      // 144: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 147: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 14a: invokevirtual net/minecraft/src/C_4105_.go ()Z
      // 14d: ifeq 152
      // 150: bipush 0
      // 151: ireturn
      // 152: aload 0
      // 153: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 156: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 159: getfield net/minecraft/src/C_4105_.f_108617_ Lnet/minecraft/src/C_3902_;
      // 15c: astore 3
      // 15d: aload 3
      // 15e: ifnonnull 163
      // 161: bipush 0
      // 162: ireturn
      // 163: aload 0
      // 164: ldc_w "debug.copy_location.message"
      // 167: bipush 0
      // 168: anewarray 4
      // 16b: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 16e: aload 0
      // 16f: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 172: ldc_w "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f"
      // 175: bipush 6
      // 177: anewarray 4
      // 17a: dup
      // 17b: bipush 0
      // 17c: aload 0
      // 17d: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 180: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 183: invokevirtual net/minecraft/src/C_4105_.dO ()Lnet/minecraft/src/C_1596_;
      // 186: invokevirtual net/minecraft/src/C_1596_.m_46472_ ()Lnet/minecraft/src/C_5264_;
      // 189: invokevirtual net/minecraft/src/C_5264_.a ()LResourceLocation;
      // 18c: aastore
      // 18d: dup
      // 18e: bipush 1
      // 18f: aload 0
      // 190: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 193: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 196: invokevirtual net/minecraft/src/C_4105_.dt ()D
      // 199: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 19c: aastore
      // 19d: dup
      // 19e: bipush 2
      // 19f: aload 0
      // 1a0: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 1a3: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 1a6: invokevirtual net/minecraft/src/C_4105_.dv ()D
      // 1a9: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 1ac: aastore
      // 1ad: dup
      // 1ae: bipush 3
      // 1af: aload 0
      // 1b0: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 1b3: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 1b6: invokevirtual net/minecraft/src/C_4105_.dz ()D
      // 1b9: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 1bc: aastore
      // 1bd: dup
      // 1be: bipush 4
      // 1bf: aload 0
      // 1c0: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 1c3: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 1c6: invokevirtual net/minecraft/src/C_4105_.dE ()F
      // 1c9: invokestatic java/lang/Float.valueOf (F)Ljava/lang/Float;
      // 1cc: aastore
      // 1cd: dup
      // 1ce: bipush 5
      // 1cf: aload 0
      // 1d0: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 1d3: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 1d6: invokevirtual net/minecraft/src/C_4105_.dG ()F
      // 1d9: invokestatic java/lang/Float.valueOf (F)Ljava/lang/Float;
      // 1dc: aastore
      // 1dd: invokestatic java/lang/String.format (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 1e0: invokevirtual KeyboardHandler.a (Ljava/lang/String;)V
      // 1e3: bipush 1
      // 1e4: ireturn
      // 1e5: aload 0
      // 1e6: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 1e9: getfield net/minecraft/src/C_3391_.l LGui;
      // 1ec: ifnull 1fd
      // 1ef: aload 0
      // 1f0: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 1f3: getfield net/minecraft/src/C_3391_.l LGui;
      // 1f6: invokevirtual Gui.d ()LChatComponent;
      // 1f9: bipush 0
      // 1fa: invokevirtual ChatComponent.a (Z)V
      // 1fd: bipush 1
      // 1fe: ireturn
      // 1ff: aload 0
      // 200: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 203: getfield net/minecraft/src/C_3391_.f_91064_ Lnet/minecraft/src/C_4287_;
      // 206: invokevirtual net/minecraft/src/C_4287_.m_113506_ ()Z
      // 209: istore 3
      // 20a: aload 0
      // 20b: iload 3
      // 20c: ifeq 215
      // 20f: ldc_w "debug.chunk_boundaries.on"
      // 212: goto 218
      // 215: ldc_w "debug.chunk_boundaries.off"
      // 218: bipush 0
      // 219: anewarray 4
      // 21c: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 21f: bipush 1
      // 220: ireturn
      // 221: aload 0
      // 222: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 225: getfield net/minecraft/src/C_3391_.m LOptions;
      // 228: aload 0
      // 229: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 22c: getfield net/minecraft/src/C_3391_.m LOptions;
      // 22f: getfield Options.m Z
      // 232: ifne 239
      // 235: bipush 1
      // 236: goto 23a
      // 239: bipush 0
      // 23a: putfield Options.m Z
      // 23d: aload 0
      // 23e: aload 0
      // 23f: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 242: getfield net/minecraft/src/C_3391_.m LOptions;
      // 245: getfield Options.m Z
      // 248: ifeq 251
      // 24b: ldc_w "debug.advanced_tooltips.on"
      // 24e: goto 254
      // 251: ldc_w "debug.advanced_tooltips.off"
      // 254: bipush 0
      // 255: anewarray 4
      // 258: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 25b: aload 0
      // 25c: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 25f: getfield net/minecraft/src/C_3391_.m LOptions;
      // 262: invokevirtual Options.aw ()V
      // 265: bipush 1
      // 266: ireturn
      // 267: aload 0
      // 268: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 26b: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 26e: invokevirtual net/minecraft/src/C_4105_.go ()Z
      // 271: ifne 28e
      // 274: aload 0
      // 275: aload 0
      // 276: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 279: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 27c: bipush 2
      // 27d: invokevirtual net/minecraft/src/C_4105_.l (I)Z
      // 280: invokestatic net/minecraft/src/C_3583_.m_96638_ ()Z
      // 283: ifne 28a
      // 286: bipush 1
      // 287: goto 28b
      // 28a: bipush 0
      // 28b: invokevirtual KeyboardHandler.a (ZZ)V
      // 28e: bipush 1
      // 28f: ireturn
      // 290: aload 0
      // 291: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 294: aload 0
      // 295: invokedynamic accept (LKeyboardHandler;)Ljava/util/function/Consumer; bsm=java/lang/invoke/LambdaMetafactory.metafactory (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; args=[ (Ljava/lang/Object;)V, KeyboardHandler.a (Lnet/minecraft/src/C_4996_;)V, (Lnet/minecraft/src/C_4996_;)V ]
      // 29a: invokevirtual net/minecraft/src/C_3391_.m_167946_ (Ljava/util/function/Consumer;)Z
      // 29d: ifeq 2b3
      // 2a0: aload 0
      // 2a1: ldc_w "debug.profiling.start"
      // 2a4: bipush 1
      // 2a5: anewarray 4
      // 2a8: dup
      // 2a9: bipush 0
      // 2aa: bipush 10
      // 2ac: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 2af: aastore
      // 2b0: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 2b3: bipush 1
      // 2b4: ireturn
      // 2b5: aload 0
      // 2b6: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 2b9: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 2bc: bipush 2
      // 2bd: invokevirtual net/minecraft/src/C_4105_.l (I)Z
      // 2c0: ifne 2d1
      // 2c3: aload 0
      // 2c4: ldc_w "debug.creative_spectator.error"
      // 2c7: bipush 0
      // 2c8: anewarray 4
      // 2cb: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 2ce: goto 31b
      // 2d1: aload 0
      // 2d2: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 2d5: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 2d8: invokevirtual net/minecraft/src/C_4105_.R_ ()Z
      // 2db: ifne 2f2
      // 2de: aload 0
      // 2df: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 2e2: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 2e5: getfield net/minecraft/src/C_4105_.f_108617_ Lnet/minecraft/src/C_3902_;
      // 2e8: ldc_w "gamemode spectator"
      // 2eb: invokevirtual net/minecraft/src/C_3902_.m_246979_ (Ljava/lang/String;)Z
      // 2ee: pop
      // 2ef: goto 31b
      // 2f2: aload 0
      // 2f3: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 2f6: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 2f9: getfield net/minecraft/src/C_4105_.f_108617_ Lnet/minecraft/src/C_3902_;
      // 2fc: aload 0
      // 2fd: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 300: getfield net/minecraft/src/C_3391_.f_91072_ Lnet/minecraft/src/C_3905_;
      // 303: invokevirtual net/minecraft/src/C_3905_.m_105294_ ()Lnet/minecraft/src/C_1593_;
      // 306: getstatic net/minecraft/src/C_1593_.CREATIVE Lnet/minecraft/src/C_1593_;
      // 309: invokestatic com/google/common/base/MoreObjects.firstNonNull (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      // 30c: checkcast net/minecraft/src/C_1593_
      // 30f: invokevirtual net/minecraft/src/C_1593_.m_46405_ ()Ljava/lang/String;
      // 312: invokedynamic makeConcatWithConstants (Ljava/lang/String;)Ljava/lang/String; bsm=java/lang/invoke/StringConcatFactory.makeConcatWithConstants (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite; args=[ "gamemode \u0001" ]
      // 317: invokevirtual net/minecraft/src/C_3902_.m_246979_ (Ljava/lang/String;)Z
      // 31a: pop
      // 31b: bipush 1
      // 31c: ireturn
      // 31d: aload 0
      // 31e: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 321: getfield net/minecraft/src/C_3391_.m LOptions;
      // 324: aload 0
      // 325: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 328: getfield net/minecraft/src/C_3391_.m LOptions;
      // 32b: getfield Options.n Z
      // 32e: ifne 335
      // 331: bipush 1
      // 332: goto 336
      // 335: bipush 0
      // 336: putfield Options.n Z
      // 339: aload 0
      // 33a: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 33d: getfield net/minecraft/src/C_3391_.m LOptions;
      // 340: invokevirtual Options.aw ()V
      // 343: aload 0
      // 344: aload 0
      // 345: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 348: getfield net/minecraft/src/C_3391_.m LOptions;
      // 34b: getfield Options.n Z
      // 34e: ifeq 357
      // 351: ldc_w "debug.pause_focus.on"
      // 354: goto 35a
      // 357: ldc_w "debug.pause_focus.off"
      // 35a: bipush 0
      // 35b: anewarray 4
      // 35e: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 361: bipush 1
      // 362: ireturn
      // 363: aload 0
      // 364: ldc_w "debug.help.message"
      // 367: bipush 0
      // 368: anewarray 4
      // 36b: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 36e: aload 0
      // 36f: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 372: getfield net/minecraft/src/C_3391_.l LGui;
      // 375: invokevirtual Gui.d ()LChatComponent;
      // 378: astore 4
      // 37a: aload 4
      // 37c: ldc_w "debug.reload_chunks.help"
      // 37f: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 382: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 385: aload 4
      // 387: ldc_w "debug.show_hitboxes.help"
      // 38a: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 38d: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 390: aload 4
      // 392: ldc_w "debug.copy_location.help"
      // 395: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 398: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 39b: aload 4
      // 39d: ldc_w "debug.clear_chat.help"
      // 3a0: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 3a3: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 3a6: aload 4
      // 3a8: ldc_w "debug.chunk_boundaries.help"
      // 3ab: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 3ae: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 3b1: aload 4
      // 3b3: ldc_w "debug.advanced_tooltips.help"
      // 3b6: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 3b9: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 3bc: aload 4
      // 3be: ldc_w "debug.inspect.help"
      // 3c1: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 3c4: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 3c7: aload 4
      // 3c9: ldc_w "debug.profiling.help"
      // 3cc: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 3cf: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 3d2: aload 4
      // 3d4: ldc_w "debug.creative_spectator.help"
      // 3d7: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 3da: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 3dd: aload 4
      // 3df: ldc_w "debug.pause_focus.help"
      // 3e2: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 3e5: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 3e8: aload 4
      // 3ea: ldc_w "debug.help.help"
      // 3ed: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 3f0: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 3f3: aload 4
      // 3f5: ldc_w "debug.dump_dynamic_textures.help"
      // 3f8: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 3fb: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 3fe: aload 4
      // 400: ldc_w "debug.reload_resourcepacks.help"
      // 403: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 406: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 409: aload 4
      // 40b: ldc_w "debug.pause.help"
      // 40e: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 411: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 414: aload 4
      // 416: ldc_w "debug.gamemodes.help"
      // 419: invokestatic net/minecraft/src/C_4996_.m_237115_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 41c: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 41f: bipush 1
      // 420: ireturn
      // 421: aload 0
      // 422: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 425: getfield net/minecraft/src/C_3391_.f_91069_ Ljava/io/File;
      // 428: invokevirtual java/io/File.toPath ()Ljava/nio/file/Path;
      // 42b: invokeinterface java/nio/file/Path.toAbsolutePath ()Ljava/nio/file/Path; 1
      // 430: astore 5
      // 432: aload 5
      // 434: invokestatic com/mojang/blaze3d/platform/TextureUtil.getDebugTexturePath (Ljava/nio/file/Path;)Ljava/nio/file/Path;
      // 437: astore 6
      // 439: aload 0
      // 43a: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 43d: invokevirtual net/minecraft/src/C_3391_.aa ()LTextureManager;
      // 440: aload 6
      // 442: invokevirtual TextureManager.a (Ljava/nio/file/Path;)V
      // 445: aload 5
      // 447: aload 6
      // 449: invokeinterface java/nio/file/Path.relativize (Ljava/nio/file/Path;)Ljava/nio/file/Path; 2
      // 44e: invokeinterface java/nio/file/Path.toString ()Ljava/lang/String; 1
      // 453: invokestatic net/minecraft/src/C_4996_.m_237113_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 456: getstatic net/minecraft/src/C_4856_.UNDERLINE Lnet/minecraft/src/C_4856_;
      // 459: invokevirtual net/minecraft/src/C_5012_.m_130940_ (Lnet/minecraft/src/C_4856_;)Lnet/minecraft/src/C_5012_;
      // 45c: aload 6
      // 45e: invokedynamic apply (Ljava/nio/file/Path;)Ljava/util/function/UnaryOperator; bsm=java/lang/invoke/LambdaMetafactory.metafactory (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; args=[ (Ljava/lang/Object;)Ljava/lang/Object;, KeyboardHandler.lambda$processKeyF3$0 (Ljava/nio/file/Path;Lnet/minecraft/src/C_5020_;)Lnet/minecraft/src/C_5020_;, (Lnet/minecraft/src/C_5020_;)Lnet/minecraft/src/C_5020_; ]
      // 463: invokevirtual net/minecraft/src/C_5012_.m_130938_ (Ljava/util/function/UnaryOperator;)Lnet/minecraft/src/C_5012_;
      // 466: astore 7
      // 468: aload 0
      // 469: ldc_w "debug.dump_dynamic_textures"
      // 46c: bipush 1
      // 46d: anewarray 4
      // 470: dup
      // 471: bipush 0
      // 472: aload 7
      // 474: aastore
      // 475: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 478: bipush 1
      // 479: ireturn
      // 47a: aload 0
      // 47b: ldc_w "debug.reload_resourcepacks.message"
      // 47e: bipush 0
      // 47f: anewarray 4
      // 482: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 485: aload 0
      // 486: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 489: invokevirtual net/minecraft/src/C_3391_.m_91391_ ()Ljava/util/concurrent/CompletableFuture;
      // 48c: pop
      // 48d: bipush 1
      // 48e: ireturn
      // 48f: aload 0
      // 490: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 493: getfield net/minecraft/src/C_3391_.f_91074_ Lnet/minecraft/src/C_4105_;
      // 496: bipush 2
      // 497: invokevirtual net/minecraft/src/C_4105_.l (I)Z
      // 49a: ifne 4ab
      // 49d: aload 0
      // 49e: ldc_w "debug.gamemodes.error"
      // 4a1: bipush 0
      // 4a2: anewarray 4
      // 4a5: invokevirtual KeyboardHandler.a (Ljava/lang/String;[Ljava/lang/Object;)V
      // 4a8: goto 4b9
      // 4ab: aload 0
      // 4ac: getfield KeyboardHandler.b Lnet/minecraft/src/C_3391_;
      // 4af: new net/minecraft/src/C_3618_
      // 4b2: dup
      // 4b3: invokespecial net/minecraft/src/C_3618_.<init> ()V
      // 4b6: invokevirtual net/minecraft/src/C_3391_.m_91152_ (Lnet/minecraft/src/C_3583_;)V
      // 4b9: bipush 1
      // 4ba: ireturn
      // 4bb: bipush 0
      // 4bc: ireturn
      // 4bd: invokestatic net/optifine/Config.getMinecraft ()Lnet/minecraft/src/C_3391_;
      // 4c0: astore 8
      // 4c2: aload 8
      // 4c4: getfield net/minecraft/src/C_3391_.f LLevelRenderer;
      // 4c7: bipush 1
      // 4c8: putfield LevelRenderer.loadVisibleChunksCounter I
      // 4cb: ldc_w "of.message.loadingVisibleChunks"
      // 4ce: bipush 0
      // 4cf: anewarray 4
      // 4d2: invokestatic net/minecraft/src/C_4513_.m_118938_ (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 4d5: invokestatic net/minecraft/src/C_4996_.m_237113_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 4d8: astore 9
      // 4da: new net/minecraft/src/C_213508_
      // 4dd: dup
      // 4de: sipush 256
      // 4e1: invokestatic net/optifine/util/RandomUtils.getRandomBytes (I)[B
      // 4e4: invokespecial net/minecraft/src/C_213508_.<init> ([B)V
      // 4e7: putstatic LevelRenderer.loadVisibleChunksMessageId Lnet/minecraft/src/C_213508_;
      // 4ea: aload 8
      // 4ec: getfield net/minecraft/src/C_3391_.l LGui;
      // 4ef: invokevirtual Gui.d ()LChatComponent;
      // 4f2: aload 9
      // 4f4: getstatic LevelRenderer.loadVisibleChunksMessageId Lnet/minecraft/src/C_213508_;
      // 4f7: invokestatic net/minecraft/src/C_240334_.m_240701_ ()Lnet/minecraft/src/C_240334_;
      // 4fa: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;Lnet/minecraft/src/C_213508_;Lnet/minecraft/src/C_240334_;)V
      // 4fd: bipush 1
      // 4fe: ireturn
      // 4ff: invokestatic net/optifine/Config.isShaders ()Z
      // 502: ifeq 51a
      // 505: new net/optifine/shaders/gui/GuiShaderOptions
      // 508: dup
      // 509: aconst_null
      // 50a: invokestatic net/optifine/Config.getGameSettings ()LOptions;
      // 50d: invokespecial net/optifine/shaders/gui/GuiShaderOptions.<init> (Lnet/minecraft/src/C_3583_;LOptions;)V
      // 510: astore 10
      // 512: invokestatic net/optifine/Config.getMinecraft ()Lnet/minecraft/src/C_3391_;
      // 515: aload 10
      // 517: invokevirtual net/minecraft/src/C_3391_.m_91152_ (Lnet/minecraft/src/C_3583_;)V
      // 51a: bipush 1
      // 51b: ireturn
      // 51c: invokestatic net/optifine/Config.isShaders ()Z
      // 51f: ifeq 528
      // 522: invokestatic net/optifine/shaders/Shaders.uninit ()V
      // 525: invokestatic net/optifine/shaders/Shaders.loadShaderPack ()V
      // 528: bipush 1
      // 529: ireturn
   }

   private void a(boolean privileged, boolean askServer) {
      C_3043_ hitresult = this.b.f_91077_;
      if (hitresult != null) {
         switch (hitresult.m_6662_()) {
            case BLOCK:
               C_4675_ blockpos = ((C_3041_)hitresult).m_82425_();
               C_1596_ level = this.b.f_91074_.dO();
               BlockState blockstate = level.a_(blockpos);
               if (privileged) {
                  if (askServer) {
                     this.b.f_91074_.f_108617_.m_105149_().m_90708_(blockpos, tagIn -> {
                        this.a(blockstate, blockpos, tagIn);
                        this.a("debug.inspect.server.block");
                     });
                  } else {
                     BlockEntity blockentity = level.c_(blockpos);
                     C_4917_ compoundtag1 = blockentity != null ? blockentity.d(level.m_9598_()) : null;
                     this.a(blockstate, blockpos, compoundtag1);
                     this.a("debug.inspect.client.block");
                  }
               } else {
                  this.a(blockstate, blockpos, null);
                  this.a("debug.inspect.client.block");
               }
               break;
            case ENTITY:
               C_507_ entity = ((C_3042_)hitresult).m_82443_();
               ResourceLocation resourcelocation = C_256712_.f_256780_.b(entity.m_6095_());
               if (privileged) {
                  if (askServer) {
                     this.b.f_91074_.f_108617_.m_105149_().m_90702_(entity.m_19879_(), tagIn -> {
                        this.a(resourcelocation, entity.dm(), tagIn);
                        this.a("debug.inspect.server.entity");
                     });
                  } else {
                     C_4917_ compoundtag = entity.m_20240_(new C_4917_());
                     this.a(resourcelocation, entity.dm(), compoundtag);
                     this.a("debug.inspect.client.entity");
                  }
               } else {
                  this.a(resourcelocation, entity.dm(), null);
                  this.a("debug.inspect.client.entity");
               }
         }
      }
   }

   private void a(BlockState state, C_4675_ pos, @Nullable C_4917_ compound) {
      StringBuilder stringbuilder = new StringBuilder(C_4426_.a(state));
      if (compound != null) {
         stringbuilder.append(compound);
      }

      String s = String.format(Locale.ROOT, "/setblock %d %d %d %s", pos.m_123341_(), pos.m_123342_(), pos.m_123343_(), stringbuilder);
      this.a(s);
   }

   private void a(ResourceLocation entityIdIn, Vec3 pos, @Nullable C_4917_ compound) {
      String s;
      if (compound != null) {
         compound.m_128473_("UUID");
         compound.m_128473_("Pos");
         compound.m_128473_("Dimension");
         String s1 = C_4943_.m_178061_(compound).getString();
         s = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f %s", entityIdIn, pos.c, pos.d, pos.e, s1);
      } else {
         s = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f", entityIdIn, pos.c, pos.d, pos.e);
      }

      this.a(s);
   }

   public void a(long windowPointer, int key, int scanCode, int action, int modifiers) {
      if (windowPointer == this.b.aM().j()) {
         boolean flag = C_3140_.m_84830_(C_3391_.m_91087_().aM().j(), 292);
         if (this.d > 0L) {
            if (!C_3140_.m_84830_(C_3391_.m_91087_().aM().j(), 67) || !flag) {
               this.d = -1L;
            }
         } else if (C_3140_.m_84830_(C_3391_.m_91087_().aM().j(), 67) && flag) {
            this.g = true;
            this.d = Util.c();
            this.e = Util.c();
            this.f = 0L;
         }

         C_3583_ screen = this.b.f_91080_;
         if (screen != null) {
            switch (key) {
               case 258:
                  this.b.m_264033_(C_263626_.KEYBOARD_TAB);
               case 259:
               case 260:
               case 261:
               default:
                  break;
               case 262:
               case 263:
               case 264:
               case 265:
                  this.b.m_264033_(C_263626_.KEYBOARD_ARROW);
            }
         }

         if (action == 1 && (!(this.b.f_91080_ instanceof C_336427_) || ((C_336427_)screen).f_337497_ <= Util.c() - 20L)) {
            if (this.b.m.Q.m_90832_(key, scanCode)) {
               this.b.aM().i();
               this.b.m.aa().a(this.b.aM().k());
               return;
            }

            if (this.b.m.N.m_90832_(key, scanCode)) {
               if (C_3583_.m_96637_()) {
               }

               Screenshot.a(this.b.f_91069_, this.b.h(), componentIn -> this.b.execute(() -> this.b.l.d().a(componentIn)));
               return;
            }
         }

         if (action != 0) {
            boolean flag1 = screen == null || !(screen.m_7222_() instanceof C_3464_) || !((C_3464_)screen.m_7222_()).m_94204_();
            if (flag1) {
               if (C_3583_.m_96637_() && key == 66 && this.b.m_240477_().m_93316_() && this.b.m.u().c()) {
                  boolean flag2 = this.b.m.as().c() == C_3398_.OFF;
                  this.b.m.as().a(C_3398_.m_91619_(this.b.m.as().c().m_91618_() + 1));
                  this.b.m.aw();
                  if (screen != null) {
                     screen.m_340185_(flag2);
                  }
               }

               C_4105_ var16 = this.b.f_91074_;
            }
         }

         if (screen != null) {
            boolean[] aboolean = new boolean[]{false};
            C_3583_.m_96579_(() -> {
               if (action != 1 && action != 2) {
                  if (action == 0) {
                     if (Reflector.ForgeHooksClient_onScreenKeyReleasedPre.exists()) {
                        aboolean[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenKeyReleasedPre, screen, key, scanCode, modifiers);
                        if (aboolean[0]) {
                           return;
                        }
                     }

                     aboolean[0] = screen.m_7920_(key, scanCode, modifiers);
                     if (Reflector.ForgeHooksClient_onScreenKeyReleasedPost.exists() && !aboolean[0]) {
                        aboolean[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenKeyReleasedPost, screen, key, scanCode, modifiers);
                     }
                  }
               } else {
                  if (Reflector.ForgeHooksClient_onScreenKeyPressedPre.exists()) {
                     aboolean[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenKeyPressedPre, screen, key, scanCode, modifiers);
                     if (aboolean[0]) {
                        return;
                     }
                  }

                  screen.m_169416_();
                  aboolean[0] = screen.m_7933_(key, scanCode, modifiers);
                  if (Reflector.ForgeHooksClient_onScreenKeyPressedPost.exists() && !aboolean[0]) {
                     aboolean[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenKeyPressedPost, screen, key, scanCode, modifiers);
                  }
               }
            }, "keyPressed event handler", screen.getClass().getCanonicalName());
            if (aboolean[0]) {
               return;
            }
         }

         C_3142_ inputconstants$key = C_3140_.m_84827_(key, scanCode);
         boolean flag4 = this.b.f_91080_ == null;
         boolean flag6;
         if (flag4 || this.b.f_91080_ instanceof C_3574_ pausescreen && !pausescreen.m_294488_()) {
            flag6 = true;
         } else {
            flag6 = false;
         }

         if (action == 0) {
            C_3387_.m_90837_(inputconstants$key, false);
            if (flag6 && key == 292) {
               if (this.g) {
                  this.g = false;
               } else {
                  this.b.aN().h();
               }
            }
         } else {
            boolean flag5 = false;
            if (flag6) {
               if (key == 293 && this.b.j != null) {
                  this.b.j.c();
               }

               if (key == 256) {
                  this.b.m_91358_(flag);
                  flag5 |= flag;
               }

               flag5 |= flag && this.b(key);
               this.g |= flag5;
               if (key == 290) {
                  this.b.m.Y = !this.b.m.Y;
               }

               if (this.b.aN().e() && !flag && key >= 48 && key <= 57) {
                  this.b.m_91111_(key - 48);
               }
            }

            if (flag4) {
               if (flag5) {
                  C_3387_.m_90837_(inputconstants$key, false);
               } else {
                  C_3387_.m_90837_(inputconstants$key, true);
                  C_3387_.m_90835_(inputconstants$key);
               }
            }
         }

         Reflector.ForgeHooksClient_onKeyInput.call(key, scanCode, action, modifiers);
      }
   }

   private void a(long windowPointer, int codePoint, int modifiers) {
      if (windowPointer == this.b.aM().j()) {
         C_3583_ guieventlistener = this.b.f_91080_;
         if (guieventlistener != null && this.b.m_91265_() == null) {
            if (Character.charCount(codePoint) == 1) {
               C_3583_.m_96579_(
                  () -> {
                     if (!Reflector.ForgeHooksClient_onScreenCharTypedPre.exists()
                        || !Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenCharTypedPre, guieventlistener, (char)codePoint, modifiers)) {
                        boolean consumed = guieventlistener.m_5534_((char)codePoint, modifiers);
                        if (Reflector.ForgeHooksClient_onScreenCharTypedPost.exists() && !consumed) {
                           Reflector.call(Reflector.ForgeHooksClient_onScreenCharTypedPost, guieventlistener, (char)codePoint, modifiers);
                        }
                     }
                  },
                  "charTyped event handler",
                  guieventlistener.getClass().getCanonicalName()
               );
            } else {
               for (char c0 : Character.toChars(codePoint)) {
                  C_3583_.m_96579_(
                     () -> {
                        if (!Reflector.ForgeHooksClient_onScreenCharTypedPre.exists()
                           || !Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenCharTypedPre, guieventlistener, c0, modifiers)) {
                           boolean consumed = guieventlistener.m_5534_(c0, modifiers);
                           if (Reflector.ForgeHooksClient_onScreenCharTypedPost.exists() && !consumed) {
                              Reflector.call(Reflector.ForgeHooksClient_onScreenCharTypedPost, guieventlistener, c0, modifiers);
                           }
                        }
                     },
                     "charTyped event handler",
                     guieventlistener.getClass().getCanonicalName()
                  );
               }
            }
         }
      }
   }

   public void a(long window) {
      C_3140_.m_84844_(
         window,
         (windowPointer, key, scanCode, action, modifiers) -> this.b.execute(() -> this.a(windowPointer, key, scanCode, action, modifiers)),
         (windowPointer, codePoint, modifiers) -> this.b.execute(() -> this.a(windowPointer, codePoint, modifiers))
      );
   }

   public String a() {
      return this.c.m_83995_(this.b.aM().j(), (errorIn, descriptionIn) -> {
         if (errorIn != 65545) {
            this.b.aM().a(errorIn, descriptionIn);
         }
      });
   }

   public void a(String string) {
      if (!string.isEmpty()) {
         this.c.m_83988_(this.b.aM().j(), string);
      }
   }

   public void b() {
      if (this.d > 0L) {
         long i = Util.c();
         long j = 10000L - (i - this.d);
         long k = i - this.e;
         if (j < 0L) {
            if (C_3583_.m_96637_()) {
               C_3083_.m_83639_();
            }

            String s = "Manually triggered debug crash";
            CrashReport crashreport = new CrashReport("Manually triggered debug crash", new Throwable("Manually triggered debug crash"));
            C_4909_ crashreportcategory = crashreport.a("Manual crash details");
            C_182783_.m_184679_(crashreportcategory);
            throw new C_5204_(crashreport);
         }

         if (k >= 1000L) {
            if (this.f == 0L) {
               this.a("debug.crash.message");
            } else {
               this.b("debug.crash.warning", Mth.f((float)j / 1000.0F));
            }

            this.e = i;
            this.f++;
         }
      }
   }
}
