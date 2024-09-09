import com.mojang.logging.LogUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1607_;
import net.minecraft.src.C_2119_;
import net.minecraft.src.C_2132_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_256686_;
import net.minecraft.src.C_2681_;
import net.minecraft.src.C_313554_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4710_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_4983_;
import net.minecraft.src.C_183117_.C_183119_;
import net.optifine.ChunkOF;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public class ClientChunkCache extends C_2119_ {
   static final Logger a = LogUtils.getLogger();
   private final C_2137_ b;
   private final C_2681_ c;
   volatile ClientChunkCache.a d;
   final ClientLevel e;

   public ClientChunkCache(ClientLevel clientWorldIn, int viewDistance) {
      this.e = clientWorldIn;
      this.b = new C_2132_(clientWorldIn, new ChunkPos(0, 0), clientWorldIn.m_9598_().m_175515_(C_256686_.f_256952_).m_246971_(Biomes.b));
      this.c = new C_2681_(this, true, clientWorldIn.m_6042_().f_223549_());
      this.d = new ClientChunkCache.a(b(viewDistance));
   }

   public C_2681_ m_7827_() {
      return this.c;
   }

   private static boolean a(@Nullable C_2137_ chunkIn, int x, int z) {
      if (chunkIn == null) {
         return false;
      } else {
         ChunkPos chunkpos = chunkIn.f();
         return chunkpos.e == x && chunkpos.f == z;
      }
   }

   public void a(ChunkPos x) {
      if (this.d.b(x.e, x.f)) {
         int i = this.d.a(x.e, x.f);
         C_2137_ levelchunk = this.d.a(i);
         if (a(levelchunk, x.e, x.f)) {
            if (Reflector.ChunkEvent_Unload_Constructor.exists()) {
               Reflector.postForgeBusEvent(Reflector.ChunkEvent_Unload_Constructor, levelchunk);
            }

            levelchunk.m_62913_(false);
            this.d.a(i, levelchunk, null);
         }
      }
   }

   @Nullable
   public C_2137_ b(int chunkX, int chunkZ, C_313554_ requiredStatus, boolean load) {
      if (this.d.b(chunkX, chunkZ)) {
         C_2137_ levelchunk = this.d.a(this.d.a(chunkX, chunkZ));
         if (a(levelchunk, chunkX, chunkZ)) {
            return levelchunk;
         }
      }

      return load ? this.b : null;
   }

   public C_1559_ m_7653_() {
      return this.e;
   }

   public void a(int cxIn, int czIn, C_4983_ bufIn) {
      if (!this.d.b(cxIn, czIn)) {
         a.warn("Ignoring chunk since it's not in the view range: {}, {}", cxIn, czIn);
      } else {
         int i = this.d.a(cxIn, czIn);
         C_2137_ levelchunk = (C_2137_)this.d.b.get(i);
         if (!a(levelchunk, cxIn, czIn)) {
            a.warn("Ignoring chunk since it's not present: {}, {}", cxIn, czIn);
         } else {
            levelchunk.m_274381_(bufIn);
         }
      }
   }

   @Nullable
   public C_2137_ a(int xIn, int zIn, C_4983_ bufIn, C_4917_ tagIn, Consumer<C_183119_> consumerIn) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: Constructor net/optifine/ChunkOF.<init>(Lnet/minecraft/src/C_1596_;LChunkPos;)V not found
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:49)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:957)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:460)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1018)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.AssignmentExprent.toJava(AssignmentExprent.java:154)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.SequenceStatement.toJava(SequenceStatement.java:107)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:241)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.SequenceStatement.toJava(SequenceStatement.java:107)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:261)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield ClientChunkCache.d LClientChunkCache$a;
      // 04: iload 1
      // 05: iload 2
      // 06: invokevirtual ClientChunkCache$a.b (II)Z
      // 09: ifne 20
      // 0c: getstatic ClientChunkCache.a Lorg/slf4j/Logger;
      // 0f: ldc "Ignoring chunk since it's not in the view range: {}, {}"
      // 11: iload 1
      // 12: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 15: iload 2
      // 16: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 19: invokeinterface org/slf4j/Logger.warn (Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V 4
      // 1e: aconst_null
      // 1f: areturn
      // 20: aload 0
      // 21: getfield ClientChunkCache.d LClientChunkCache$a;
      // 24: iload 1
      // 25: iload 2
      // 26: invokevirtual ClientChunkCache$a.a (II)I
      // 29: istore 6
      // 2b: aload 0
      // 2c: getfield ClientChunkCache.d LClientChunkCache$a;
      // 2f: getfield ClientChunkCache$a.b Ljava/util/concurrent/atomic/AtomicReferenceArray;
      // 32: iload 6
      // 34: invokevirtual java/util/concurrent/atomic/AtomicReferenceArray.get (I)Ljava/lang/Object;
      // 37: checkcast net/minecraft/src/C_2137_
      // 3a: astore 7
      // 3c: new ChunkPos
      // 3f: dup
      // 40: iload 1
      // 41: iload 2
      // 42: invokespecial ChunkPos.<init> (II)V
      // 45: astore 8
      // 47: aload 7
      // 49: iload 1
      // 4a: iload 2
      // 4b: invokestatic ClientChunkCache.a (Lnet/minecraft/src/C_2137_;II)Z
      // 4e: ifne 83
      // 51: aload 7
      // 53: ifnull 5c
      // 56: aload 7
      // 58: bipush 0
      // 59: invokevirtual net/minecraft/src/C_2137_.m_62913_ (Z)V
      // 5c: new net/optifine/ChunkOF
      // 5f: dup
      // 60: aload 0
      // 61: getfield ClientChunkCache.e LClientLevel;
      // 64: aload 8
      // 66: invokespecial net/optifine/ChunkOF.<init> (Lnet/minecraft/src/C_1596_;LChunkPos;)V
      // 69: astore 7
      // 6b: aload 7
      // 6d: aload 3
      // 6e: aload 4
      // 70: aload 5
      // 72: invokevirtual net/minecraft/src/C_2137_.m_187971_ (Lnet/minecraft/src/C_4983_;Lnet/minecraft/src/C_4917_;Ljava/util/function/Consumer;)V
      // 75: aload 0
      // 76: getfield ClientChunkCache.d LClientChunkCache$a;
      // 79: iload 6
      // 7b: aload 7
      // 7d: invokevirtual ClientChunkCache$a.a (ILnet/minecraft/src/C_2137_;)V
      // 80: goto 8d
      // 83: aload 7
      // 85: aload 3
      // 86: aload 4
      // 88: aload 5
      // 8a: invokevirtual net/minecraft/src/C_2137_.m_187971_ (Lnet/minecraft/src/C_4983_;Lnet/minecraft/src/C_4917_;Ljava/util/function/Consumer;)V
      // 8d: aload 0
      // 8e: getfield ClientChunkCache.e LClientLevel;
      // 91: aload 8
      // 93: invokevirtual ClientLevel.a (LChunkPos;)V
      // 96: getstatic net/optifine/reflect/Reflector.ChunkEvent_Load_Constructor Lnet/optifine/reflect/ReflectorConstructor;
      // 99: invokevirtual net/optifine/reflect/ReflectorConstructor.exists ()Z
      // 9c: ifeq b6
      // 9f: getstatic net/optifine/reflect/Reflector.ChunkEvent_Load_Constructor Lnet/optifine/reflect/ReflectorConstructor;
      // a2: bipush 2
      // a3: anewarray 153
      // a6: dup
      // a7: bipush 0
      // a8: aload 7
      // aa: aastore
      // ab: dup
      // ac: bipush 1
      // ad: bipush 0
      // ae: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // b1: aastore
      // b2: invokestatic net/optifine/reflect/Reflector.postForgeBusEvent (Lnet/optifine/reflect/ReflectorConstructor;[Ljava/lang/Object;)Z
      // b5: pop
      // b6: aload 7
      // b8: bipush 1
      // b9: invokevirtual net/minecraft/src/C_2137_.m_62913_ (Z)V
      // bc: aload 7
      // be: areturn
   }

   public void m_201698_(BooleanSupplier p_201698_1_, boolean p_201698_2_) {
   }

   public void d(int x, int z) {
      this.d.e = x;
      this.d.f = z;
   }

   public void a(int viewDistance) {
      int i = this.d.c;
      int j = b(viewDistance);
      if (i != j) {
         ClientChunkCache.a clientchunkcache$storage = new ClientChunkCache.a(j);
         clientchunkcache$storage.e = this.d.e;
         clientchunkcache$storage.f = this.d.f;

         for (int k = 0; k < this.d.b.length(); k++) {
            C_2137_ levelchunk = (C_2137_)this.d.b.get(k);
            if (levelchunk != null) {
               ChunkPos chunkpos = levelchunk.f();
               if (clientchunkcache$storage.b(chunkpos.e, chunkpos.f)) {
                  clientchunkcache$storage.a(clientchunkcache$storage.a(chunkpos.e, chunkpos.f), levelchunk);
               }
            }
         }

         this.d = clientchunkcache$storage;
      }
   }

   private static int b(int distanceIn) {
      return Math.max(2, distanceIn) + 3;
   }

   public String m_6754_() {
      return this.d.b.length() + ", " + this.m_8482_();
   }

   public int m_8482_() {
      return this.d.g;
   }

   public void m_6506_(C_1607_ type, C_4710_ pos) {
      C_3391_.m_91087_().f.b(pos.m_123170_(), pos.m_123206_(), pos.m_123222_());
   }

   final class a {
      final AtomicReferenceArray<C_2137_> b;
      final int c;
      private final int d;
      volatile int e;
      volatile int f;
      int g;

      a(final int viewDistanceIn) {
         this.c = viewDistanceIn;
         this.d = viewDistanceIn * 2 + 1;
         this.b = new AtomicReferenceArray(this.d * this.d);
      }

      int a(int x, int z) {
         return Math.floorMod(z, this.d) * this.d + Math.floorMod(x, this.d);
      }

      protected void a(int chunkIndex, @Nullable C_2137_ chunkIn) {
         C_2137_ levelchunk = (C_2137_)this.b.getAndSet(chunkIndex, chunkIn);
         if (levelchunk != null) {
            this.g--;
            ClientChunkCache.this.e.a(levelchunk);
         }

         if (chunkIn != null) {
            this.g++;
         }
      }

      protected C_2137_ a(int chunkIndex, C_2137_ chunkIn, @Nullable C_2137_ replaceWith) {
         if (this.b.compareAndSet(chunkIndex, chunkIn, replaceWith) && replaceWith == null) {
            this.g--;
         }

         ClientChunkCache.this.e.a(chunkIn);
         return chunkIn;
      }

      boolean b(int x, int z) {
         return Math.abs(x - this.e) <= this.c && Math.abs(z - this.f) <= this.c;
      }

      @Nullable
      protected C_2137_ a(int chunkIndex) {
         return (C_2137_)this.b.get(chunkIndex);
      }

      private void a(String fileNameIn) {
         try {
            FileOutputStream fileoutputstream = new FileOutputStream(fileNameIn);

            try {
               int i = ClientChunkCache.this.d.c;

               for (int j = this.f - i; j <= this.f + i; j++) {
                  for (int k = this.e - i; k <= this.e + i; k++) {
                     C_2137_ levelchunk = (C_2137_)ClientChunkCache.this.d.b.get(ClientChunkCache.this.d.a(k, j));
                     if (levelchunk != null) {
                        ChunkPos chunkpos = levelchunk.f();
                        fileoutputstream.write((chunkpos.e + "\t" + chunkpos.f + "\t" + levelchunk.m_6430_() + "\n").getBytes(StandardCharsets.UTF_8));
                     }
                  }
               }
            } catch (Throwable var9) {
               try {
                  fileoutputstream.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }

               throw var9;
            }

            fileoutputstream.close();
         } catch (IOException var10) {
            ClientChunkCache.a.error("Failed to dump chunks to file {}", fileNameIn, var10);
         }
      }
   }
}
