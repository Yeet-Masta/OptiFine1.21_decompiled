import java.util.function.Predicate;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_182855_;
import net.minecraft.src.C_203228_;
import net.minecraft.src.C_238161_;
import net.minecraft.src.C_2691_;
import net.minecraft.src.C_4705_;
import net.minecraft.src.C_4983_;
import net.minecraft.src.C_182857_.C_182866_;

public class LevelChunkSection {
   public static final int a = 16;
   public static final int b = 16;
   public static final int c = 4096;
   public static final int d = 2;
   private short e;
   private short f;
   private short g;
   private final PalettedContainer<BlockState> h;
   private C_238161_<C_203228_<C_1629_>> i;

   public LevelChunkSection(PalettedContainer<BlockState> dataIn, C_238161_<C_203228_<C_1629_>> biomesIn) {
      this.h = dataIn;
      this.i = biomesIn;
      this.g();
   }

   public LevelChunkSection(C_4705_<C_1629_> registryIn) {
      this.h = new PalettedContainer<>(C_1706_.f_49791_, C_1710_.f_50016_.o(), PalettedContainer.d.d);
      this.i = new PalettedContainer<>(registryIn.m_206115_(), registryIn.m_246971_(Biomes.b), PalettedContainer.d.e);
   }

   public BlockState a(int x, int y, int z) {
      return this.h.m_63087_(x, y, z);
   }

   public C_2691_ b(int x, int y, int z) {
      return this.h.m_63087_(x, y, z).m_60819_();
   }

   public void a() {
      this.h.a();
   }

   public void b() {
      this.h.b();
   }

   public BlockState a(int x, int y, int z, BlockState blockStateIn) {
      return this.a(x, y, z, blockStateIn, true);
   }

   public BlockState a(int x, int y, int z, BlockState state, boolean useLocks) {
      BlockState blockstate;
      if (useLocks) {
         blockstate = this.h.a(x, y, z, state);
      } else {
         blockstate = this.h.b(x, y, z, state);
      }

      C_2691_ fluidstate = blockstate.m_60819_();
      C_2691_ fluidstate1 = state.m_60819_();
      if (!blockstate.m_60795_()) {
         this.e--;
         if (blockstate.m_60823_()) {
            this.f--;
         }
      }

      if (!fluidstate.m_76178_()) {
         this.g--;
      }

      if (!state.m_60795_()) {
         this.e++;
         if (state.m_60823_()) {
            this.f++;
         }
      }

      if (!fluidstate1.m_76178_()) {
         this.g++;
      }

      return blockstate;
   }

   public boolean c() {
      return this.e == 0;
   }

   public boolean d() {
      return this.e() || this.f();
   }

   public boolean e() {
      return this.f > 0;
   }

   public boolean f() {
      return this.g > 0;
   }

   public void g() {
      LevelChunkSection.a levelchunksection$blockcounter = new LevelChunkSection.a();
      this.h.a(levelchunksection$blockcounter);
      this.e = (short)levelchunksection$blockcounter.a;
      this.f = (short)levelchunksection$blockcounter.b;
      this.g = (short)levelchunksection$blockcounter.c;
   }

   public PalettedContainer<BlockState> h() {
      return this.h;
   }

   public C_238161_<C_203228_<C_1629_>> i() {
      return this.i;
   }

   public void a(C_4983_ packetBufferIn) {
      this.e = packetBufferIn.readShort();
      this.h.a(packetBufferIn);
      PalettedContainer<C_203228_<C_1629_>> palettedcontainer = this.i.e();
      palettedcontainer.a(packetBufferIn);
      this.i = palettedcontainer;
   }

   public void b(C_4983_ packetBufferIn) {
      PalettedContainer<C_203228_<C_1629_>> palettedcontainer = this.i.e();
      palettedcontainer.a(packetBufferIn);
      this.i = palettedcontainer;
   }

   public void c(C_4983_ packetBufferIn) {
      packetBufferIn.writeShort(this.e);
      this.h.m_63135_(packetBufferIn);
      this.i.m_63135_(packetBufferIn);
   }

   public int j() {
      return 2 + this.h.m_63137_() + this.i.m_63137_();
   }

   public boolean a(Predicate<BlockState> state) {
      return this.h.m_63109_(state);
   }

   public C_203228_<C_1629_> c(int x, int y, int z) {
      return (C_203228_<C_1629_>)this.i.m_63087_(x, y, z);
   }

   public void a(C_182855_ resolverIn, C_182866_ samplerIn, int x, int y, int z) {
      PalettedContainer<C_203228_<C_1629_>> palettedcontainer = this.i.e();
      int i = 4;

      for (int j = 0; j < 4; j++) {
         for (int k = 0; k < 4; k++) {
            for (int l = 0; l < 4; l++) {
               palettedcontainer.b(j, k, l, resolverIn.m_203407_(x + j, y + k, z + l, samplerIn));
            }
         }
      }

      this.i = palettedcontainer;
   }

   public short getBlockRefCount() {
      return this.e;
   }

   class a implements PalettedContainer.b<BlockState> {
      public int a;
      public int b;
      public int c;

      public void a(BlockState blockStateIn, int countIn) {
         C_2691_ fluidstate = blockStateIn.m_60819_();
         if (!blockStateIn.m_60795_()) {
            this.a += countIn;
            if (blockStateIn.m_60823_()) {
               this.b += countIn;
            }
         }

         if (!fluidstate.m_76178_()) {
            this.a += countIn;
            if (fluidstate.m_76187_()) {
               this.c += countIn;
            }
         }
      }
   }
}
