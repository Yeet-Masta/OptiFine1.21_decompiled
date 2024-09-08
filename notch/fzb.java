package net.minecraft.src;

import com.mojang.logging.LogUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.src.C_183117_.C_183119_;
import net.optifine.ChunkOF;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public class C_3895_ extends C_2119_ {
   static final Logger f_104407_ = LogUtils.getLogger();
   private final C_2137_ f_104408_;
   private final C_2681_ f_104409_;
   volatile C_3895_.C_3897_ f_104410_;
   final C_3899_ f_104411_;

   public C_3895_(C_3899_ clientWorldIn, int viewDistance) {
      this.f_104411_ = clientWorldIn;
      this.f_104408_ = new C_2132_(clientWorldIn, new C_1560_(0, 0), clientWorldIn.m_9598_().m_175515_(C_256686_.f_256952_).m_246971_(C_1655_.f_48202_));
      this.f_104409_ = new C_2681_(this, true, clientWorldIn.m_6042_().f_223549_());
      this.f_104410_ = new C_3895_.C_3897_(m_104448_(viewDistance));
   }

   public C_2681_ m_7827_() {
      return this.f_104409_;
   }

   private static boolean m_104438_(@Nullable C_2137_ chunkIn, int x, int z) {
      if (chunkIn == null) {
         return false;
      } else {
         C_1560_ chunkpos = chunkIn.f();
         return chunkpos.f_45578_ == x && chunkpos.f_45579_ == z;
      }
   }

   public void m_104455_(C_1560_ x) {
      if (this.f_104410_.m_104500_(x.f_45578_, x.f_45579_)) {
         int i = this.f_104410_.m_104481_(x.f_45578_, x.f_45579_);
         C_2137_ levelchunk = this.f_104410_.m_104479_(i);
         if (m_104438_(levelchunk, x.f_45578_, x.f_45579_)) {
            if (Reflector.ChunkEvent_Unload_Constructor.exists()) {
               Reflector.postForgeBusEvent(Reflector.ChunkEvent_Unload_Constructor, levelchunk);
            }

            levelchunk.m_62913_(false);
            this.f_104410_.m_104487_(i, levelchunk, null);
         }
      }
   }

   @Nullable
   public C_2137_ m_7587_(int chunkX, int chunkZ, C_313554_ requiredStatus, boolean load) {
      if (this.f_104410_.m_104500_(chunkX, chunkZ)) {
         C_2137_ levelchunk = this.f_104410_.m_104479_(this.f_104410_.m_104481_(chunkX, chunkZ));
         if (m_104438_(levelchunk, chunkX, chunkZ)) {
            return levelchunk;
         }
      }

      return load ? this.f_104408_ : null;
   }

   public C_1559_ m_7653_() {
      return this.f_104411_;
   }

   public void m_274444_(int cxIn, int czIn, C_4983_ bufIn) {
      if (!this.f_104410_.m_104500_(cxIn, czIn)) {
         f_104407_.warn("Ignoring chunk since it's not in the view range: {}, {}", cxIn, czIn);
      } else {
         int i = this.f_104410_.m_104481_(cxIn, czIn);
         C_2137_ levelchunk = (C_2137_)this.f_104410_.f_104466_.get(i);
         if (!m_104438_(levelchunk, cxIn, czIn)) {
            f_104407_.warn("Ignoring chunk since it's not present: {}, {}", cxIn, czIn);
         } else {
            levelchunk.m_274381_(bufIn);
         }
      }
   }

   @Nullable
   public C_2137_ m_194116_(int xIn, int zIn, C_4983_ bufIn, C_4917_ tagIn, Consumer<C_183119_> consumerIn) {
      if (!this.f_104410_.m_104500_(xIn, zIn)) {
         f_104407_.warn("Ignoring chunk since it's not in the view range: {}, {}", xIn, zIn);
         return null;
      } else {
         int i = this.f_104410_.m_104481_(xIn, zIn);
         C_2137_ levelchunk = (C_2137_)this.f_104410_.f_104466_.get(i);
         C_1560_ chunkpos = new C_1560_(xIn, zIn);
         if (!m_104438_(levelchunk, xIn, zIn)) {
            if (levelchunk != null) {
               levelchunk.m_62913_(false);
            }

            levelchunk = new ChunkOF(this.f_104411_, chunkpos);
            levelchunk.m_187971_(bufIn, tagIn, consumerIn);
            this.f_104410_.m_104484_(i, levelchunk);
         } else {
            levelchunk.m_187971_(bufIn, tagIn, consumerIn);
         }

         this.f_104411_.m_171649_(chunkpos);
         if (Reflector.ChunkEvent_Load_Constructor.exists()) {
            Reflector.postForgeBusEvent(Reflector.ChunkEvent_Load_Constructor, levelchunk, false);
         }

         levelchunk.m_62913_(true);
         return levelchunk;
      }
   }

   public void m_201698_(BooleanSupplier p_201698_1_, boolean p_201698_2_) {
   }

   public void m_104459_(int x, int z) {
      this.f_104410_.f_104469_ = x;
      this.f_104410_.f_104470_ = z;
   }

   public void m_104416_(int viewDistance) {
      int i = this.f_104410_.f_104467_;
      int j = m_104448_(viewDistance);
      if (i != j) {
         C_3895_.C_3897_ clientchunkcache$storage = new C_3895_.C_3897_(j);
         clientchunkcache$storage.f_104469_ = this.f_104410_.f_104469_;
         clientchunkcache$storage.f_104470_ = this.f_104410_.f_104470_;

         for (int k = 0; k < this.f_104410_.f_104466_.length(); k++) {
            C_2137_ levelchunk = (C_2137_)this.f_104410_.f_104466_.get(k);
            if (levelchunk != null) {
               C_1560_ chunkpos = levelchunk.f();
               if (clientchunkcache$storage.m_104500_(chunkpos.f_45578_, chunkpos.f_45579_)) {
                  clientchunkcache$storage.m_104484_(clientchunkcache$storage.m_104481_(chunkpos.f_45578_, chunkpos.f_45579_), levelchunk);
               }
            }
         }

         this.f_104410_ = clientchunkcache$storage;
      }
   }

   private static int m_104448_(int distanceIn) {
      return Math.max(2, distanceIn) + 3;
   }

   public String m_6754_() {
      return this.f_104410_.f_104466_.length() + ", " + this.m_8482_();
   }

   public int m_8482_() {
      return this.f_104410_.f_104471_;
   }

   public void m_6506_(C_1607_ type, C_4710_ pos) {
      C_3391_.m_91087_().f_91060_.m_109770_(pos.m_123170_(), pos.m_123206_(), pos.m_123222_());
   }

   final class C_3897_ {
      final AtomicReferenceArray<C_2137_> f_104466_;
      final int f_104467_;
      private final int f_104468_;
      volatile int f_104469_;
      volatile int f_104470_;
      int f_104471_;

      C_3897_(final int viewDistanceIn) {
         this.f_104467_ = viewDistanceIn;
         this.f_104468_ = viewDistanceIn * 2 + 1;
         this.f_104466_ = new AtomicReferenceArray(this.f_104468_ * this.f_104468_);
      }

      int m_104481_(int x, int z) {
         return Math.floorMod(z, this.f_104468_) * this.f_104468_ + Math.floorMod(x, this.f_104468_);
      }

      protected void m_104484_(int chunkIndex, @Nullable C_2137_ chunkIn) {
         C_2137_ levelchunk = (C_2137_)this.f_104466_.getAndSet(chunkIndex, chunkIn);
         if (levelchunk != null) {
            this.f_104471_--;
            C_3895_.this.f_104411_.m_104665_(levelchunk);
         }

         if (chunkIn != null) {
            this.f_104471_++;
         }
      }

      protected C_2137_ m_104487_(int chunkIndex, C_2137_ chunkIn, @Nullable C_2137_ replaceWith) {
         if (this.f_104466_.compareAndSet(chunkIndex, chunkIn, replaceWith) && replaceWith == null) {
            this.f_104471_--;
         }

         C_3895_.this.f_104411_.m_104665_(chunkIn);
         return chunkIn;
      }

      boolean m_104500_(int x, int z) {
         return Math.abs(x - this.f_104469_) <= this.f_104467_ && Math.abs(z - this.f_104470_) <= this.f_104467_;
      }

      @Nullable
      protected C_2137_ m_104479_(int chunkIndex) {
         return (C_2137_)this.f_104466_.get(chunkIndex);
      }

      private void m_171622_(String fileNameIn) {
         try {
            FileOutputStream fileoutputstream = new FileOutputStream(fileNameIn);

            try {
               int i = C_3895_.this.f_104410_.f_104467_;

               for (int j = this.f_104470_ - i; j <= this.f_104470_ + i; j++) {
                  for (int k = this.f_104469_ - i; k <= this.f_104469_ + i; k++) {
                     C_2137_ levelchunk = (C_2137_)C_3895_.this.f_104410_.f_104466_.get(C_3895_.this.f_104410_.m_104481_(k, j));
                     if (levelchunk != null) {
                        C_1560_ chunkpos = levelchunk.f();
                        fileoutputstream.write(
                           (chunkpos.f_45578_ + "\t" + chunkpos.f_45579_ + "\t" + levelchunk.m_6430_() + "\n").getBytes(StandardCharsets.UTF_8)
                        );
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
            C_3895_.f_104407_.error("Failed to dump chunks to file {}", fileNameIn, var10);
         }
      }
   }
}
