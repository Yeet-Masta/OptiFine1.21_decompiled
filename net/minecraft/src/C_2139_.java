package net.minecraft.src;

import java.util.function.Predicate;

public class C_2139_ {
   public static final int f_156455_ = 16;
   public static final int f_156456_ = 16;
   public static final int f_156457_ = 4096;
   public static final int f_187994_ = 2;
   private short f_62969_;
   private short f_62970_;
   private short f_62971_;
   private final C_2145_ f_62972_;
   private C_238161_ f_187995_;

   public C_2139_(C_2145_ dataIn, C_238161_ biomesIn) {
      this.f_62972_ = dataIn;
      this.f_187995_ = biomesIn;
      this.m_63018_();
   }

   public C_2139_(C_4705_ registryIn) {
      this.f_62972_ = new C_2145_(C_1706_.f_49791_, C_1710_.f_50016_.m_49966_(), C_2145_.C_182882_.f_188137_);
      this.f_187995_ = new C_2145_(registryIn.m_206115_(), registryIn.m_246971_(C_1655_.f_48202_), C_2145_.C_182882_.f_188138_);
   }

   public C_2064_ m_62982_(int x, int y, int z) {
      return (C_2064_)this.f_62972_.m_63087_(x, y, z);
   }

   public C_2691_ m_63007_(int x, int y, int z) {
      return ((C_2064_)this.f_62972_.m_63087_(x, y, z)).m_60819_();
   }

   public void m_62981_() {
      this.f_62972_.m_63084_();
   }

   public void m_63006_() {
      this.f_62972_.m_63120_();
   }

   public C_2064_ m_62986_(int x, int y, int z, C_2064_ blockStateIn) {
      return this.m_62991_(x, y, z, blockStateIn, true);
   }

   public C_2064_ m_62991_(int x, int y, int z, C_2064_ state, boolean useLocks) {
      C_2064_ blockstate;
      if (useLocks) {
         blockstate = (C_2064_)this.f_62972_.m_63091_(x, y, z, state);
      } else {
         blockstate = (C_2064_)this.f_62972_.m_63127_(x, y, z, state);
      }

      C_2691_ fluidstate = blockstate.m_60819_();
      C_2691_ fluidstate1 = state.m_60819_();
      if (!blockstate.m_60795_()) {
         --this.f_62969_;
         if (blockstate.m_60823_()) {
            --this.f_62970_;
         }
      }

      if (!fluidstate.m_76178_()) {
         --this.f_62971_;
      }

      if (!state.m_60795_()) {
         ++this.f_62969_;
         if (state.m_60823_()) {
            ++this.f_62970_;
         }
      }

      if (!fluidstate1.m_76178_()) {
         ++this.f_62971_;
      }

      return blockstate;
   }

   public boolean m_188008_() {
      return this.f_62969_ == 0;
   }

   public boolean m_63014_() {
      return this.m_63015_() || this.m_63016_();
   }

   public boolean m_63015_() {
      return this.f_62970_ > 0;
   }

   public boolean m_63016_() {
      return this.f_62971_ > 0;
   }

   public void m_63018_() {
      C_203216_ levelchunksection$blockcounter = new C_203216_(this);
      this.f_62972_.m_63099_(levelchunksection$blockcounter);
      this.f_62969_ = (short)levelchunksection$blockcounter.f_204437_;
      this.f_62970_ = (short)levelchunksection$blockcounter.f_204438_;
      this.f_62971_ = (short)levelchunksection$blockcounter.f_204439_;
   }

   public C_2145_ m_63019_() {
      return this.f_62972_;
   }

   public C_238161_ m_187996_() {
      return this.f_187995_;
   }

   public void m_63004_(C_4983_ packetBufferIn) {
      this.f_62969_ = packetBufferIn.readShort();
      this.f_62972_.m_63118_(packetBufferIn);
      C_2145_ palettedcontainer = this.f_187995_.m_238334_();
      palettedcontainer.m_63118_(packetBufferIn);
      this.f_187995_ = palettedcontainer;
   }

   public void m_274599_(C_4983_ packetBufferIn) {
      C_2145_ palettedcontainer = this.f_187995_.m_238334_();
      palettedcontainer.m_63118_(packetBufferIn);
      this.f_187995_ = palettedcontainer;
   }

   public void m_63011_(C_4983_ packetBufferIn) {
      packetBufferIn.writeShort(this.f_62969_);
      this.f_62972_.m_63135_(packetBufferIn);
      this.f_187995_.m_63135_(packetBufferIn);
   }

   public int m_63020_() {
      return 2 + this.f_62972_.m_63137_() + this.f_187995_.m_63137_();
   }

   public boolean m_63002_(Predicate state) {
      return this.f_62972_.m_63109_(state);
   }

   public C_203228_ m_204433_(int x, int y, int z) {
      return (C_203228_)this.f_187995_.m_63087_(x, y, z);
   }

   public void m_280631_(C_182855_ resolverIn, C_182857_.C_182866_ samplerIn, int x, int y, int z) {
      C_2145_ palettedcontainer = this.f_187995_.m_238334_();
      int i = true;

      for(int j = 0; j < 4; ++j) {
         for(int k = 0; k < 4; ++k) {
            for(int l = 0; l < 4; ++l) {
               palettedcontainer.m_63127_(j, k, l, resolverIn.m_203407_(x + j, y + k, z + l, samplerIn));
            }
         }
      }

      this.f_187995_ = palettedcontainer;
   }

   public short getBlockRefCount() {
      return this.f_62969_;
   }

   class C_203216_ implements C_2145_.C_2146_ {
      public int f_204437_;
      public int f_204438_;
      public int f_204439_;

      C_203216_(final C_2139_ this$0) {
      }

      public void m_63144_(C_2064_ blockStateIn, int countIn) {
         C_2691_ fluidstate = blockStateIn.m_60819_();
         if (!blockStateIn.m_60795_()) {
            this.f_204437_ += countIn;
            if (blockStateIn.m_60823_()) {
               this.f_204438_ += countIn;
            }
         }

         if (!fluidstate.m_76178_()) {
            this.f_204437_ += countIn;
            if (fluidstate.m_76187_()) {
               this.f_204439_ += countIn;
            }
         }

      }
   }
}
