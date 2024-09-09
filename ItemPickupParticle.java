import net.minecraft.src.C_4029_;
import net.minecraft.src.C_4148_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_976_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class ItemPickupParticle extends Particle {
   private static final int a = 3;
   private final C_4148_ b;
   private final C_507_ D;
   private final C_507_ E;
   private int F;
   private final EntityRenderDispatcher G;
   private double H;
   private double I;
   private double J;
   private double K;
   private double L;
   private double M;

   public ItemPickupParticle(EntityRenderDispatcher p_i107022_1_, C_4148_ p_i107022_2_, ClientLevel p_i107022_3_, C_507_ p_i107022_4_, C_507_ p_i107022_5_) {
      this(p_i107022_1_, p_i107022_2_, p_i107022_3_, p_i107022_4_, p_i107022_5_, p_i107022_4_.dr());
   }

   private ItemPickupParticle(
      EntityRenderDispatcher renderManagerIn, C_4148_ renderTypeBuffersIn, ClientLevel worldIn, C_507_ entityIn, C_507_ targetEntityIn, Vec3 movementIn
   ) {
      super(worldIn, entityIn.m_20185_(), entityIn.m_20186_(), entityIn.m_20189_(), movementIn.c, movementIn.d, movementIn.e);
      this.b = renderTypeBuffersIn;
      this.D = this.a(entityIn);
      this.E = targetEntityIn;
      this.G = renderManagerIn;
      this.c();
      this.d();
   }

   private C_507_ a(C_507_ entityIn) {
      return (C_507_)(!(entityIn instanceof C_976_) ? entityIn : ((C_976_)entityIn).m_32066_());
   }

   @Override
   public C_4029_ b() {
      return C_4029_.f_107433_;
   }

   @Override
   public void a(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      net.optifine.shaders.Program oldShadersProgram = null;
      if (Config.isShaders()) {
         oldShadersProgram = Shaders.activeProgram;
         Shaders.nextEntity(this.D);
      }

      float f = ((float)this.F + partialTicks) / 3.0F;
      f *= f;
      double d0 = Mth.d((double)partialTicks, this.K, this.H);
      double d1 = Mth.d((double)partialTicks, this.L, this.I);
      double d2 = Mth.d((double)partialTicks, this.M, this.J);
      double d3 = Mth.d((double)f, this.D.m_20185_(), d0);
      double d4 = Mth.d((double)f, this.D.m_20186_(), d1);
      double d5 = Mth.d((double)f, this.D.m_20189_(), d2);
      MultiBufferSource.a multibuffersource$buffersource = this.b.c();
      Vec3 vec3 = renderInfo.b();
      this.G
         .a(
            this.D,
            d3 - vec3.m_7096_(),
            d4 - vec3.m_7098_(),
            d5 - vec3.m_7094_(),
            this.D.m_146908_(),
            partialTicks,
            new PoseStack(),
            multibuffersource$buffersource,
            this.G.a(this.D, partialTicks)
         );
      multibuffersource$buffersource.b();
      if (Config.isShaders()) {
         Shaders.setEntityId(null);
         Shaders.useProgram(oldShadersProgram);
      }
   }

   @Override
   public void a() {
      this.F++;
      if (this.F == 3) {
         this.k();
      }

      this.d();
      this.c();
   }

   private void c() {
      this.H = this.E.m_20185_();
      this.I = (this.E.m_20186_() + this.E.m_20188_()) / 2.0;
      this.J = this.E.m_20189_();
   }

   private void d() {
      this.K = this.H;
      this.L = this.I;
      this.M = this.J;
   }
}
