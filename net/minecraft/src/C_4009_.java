package net.minecraft.src;

import net.optifine.Config;
import net.optifine.shaders.Program;
import net.optifine.shaders.Shaders;

public class C_4009_ extends C_4022_ {
   private static final int f_172257_ = 3;
   private final C_4148_ f_107020_;
   private final C_507_ f_107021_;
   private final C_507_ f_107017_;
   private int f_107018_;
   private final C_4330_ f_107019_;
   private double f_302522_;
   private double f_302758_;
   private double f_303670_;
   private double f_303041_;
   private double f_302502_;
   private double f_302216_;

   public C_4009_(C_4330_ p_i107022_1_, C_4148_ p_i107022_2_, C_3899_ p_i107022_3_, C_507_ p_i107022_4_, C_507_ p_i107022_5_) {
      this(p_i107022_1_, p_i107022_2_, p_i107022_3_, p_i107022_4_, p_i107022_5_, p_i107022_4_.m_20184_());
   }

   private C_4009_(C_4330_ renderManagerIn, C_4148_ renderTypeBuffersIn, C_3899_ worldIn, C_507_ entityIn, C_507_ targetEntityIn, C_3046_ movementIn) {
      super(worldIn, entityIn.m_20185_(), entityIn.m_20186_(), entityIn.m_20189_(), movementIn.f_82479_, movementIn.f_82480_, movementIn.f_82481_);
      this.f_107020_ = renderTypeBuffersIn;
      this.f_107021_ = this.m_107036_(entityIn);
      this.f_107017_ = targetEntityIn;
      this.f_107019_ = renderManagerIn;
      this.m_306961_();
      this.m_307839_();
   }

   private C_507_ m_107036_(C_507_ entityIn) {
      return (C_507_)(!(entityIn instanceof C_976_) ? entityIn : ((C_976_)entityIn).m_32066_());
   }

   public C_4029_ m_7556_() {
      return C_4029_.f_107433_;
   }

   public void m_5744_(C_3187_ buffer, C_3373_ renderInfo, float partialTicks) {
      Program oldShadersProgram = null;
      if (Config.isShaders()) {
         oldShadersProgram = Shaders.activeProgram;
         Shaders.nextEntity(this.f_107021_);
      }

      float f = ((float)this.f_107018_ + partialTicks) / 3.0F;
      f *= f;
      double d0 = C_188_.m_14139_((double)partialTicks, this.f_303041_, this.f_302522_);
      double d1 = C_188_.m_14139_((double)partialTicks, this.f_302502_, this.f_302758_);
      double d2 = C_188_.m_14139_((double)partialTicks, this.f_302216_, this.f_303670_);
      double d3 = C_188_.m_14139_((double)f, this.f_107021_.m_20185_(), d0);
      double d4 = C_188_.m_14139_((double)f, this.f_107021_.m_20186_(), d1);
      double d5 = C_188_.m_14139_((double)f, this.f_107021_.m_20189_(), d2);
      C_4139_.C_4140_ multibuffersource$buffersource = this.f_107020_.m_110104_();
      C_3046_ vec3 = renderInfo.m_90583_();
      this.f_107019_.m_114384_(this.f_107021_, d3 - vec3.m_7096_(), d4 - vec3.m_7098_(), d5 - vec3.m_7094_(), this.f_107021_.m_146908_(), partialTicks, new C_3181_(), multibuffersource$buffersource, this.f_107019_.m_114394_(this.f_107021_, partialTicks));
      multibuffersource$buffersource.m_109911_();
      if (Config.isShaders()) {
         Shaders.setEntityId((C_507_)null);
         Shaders.useProgram(oldShadersProgram);
      }

   }

   public void m_5989_() {
      ++this.f_107018_;
      if (this.f_107018_ == 3) {
         this.m_107274_();
      }

      this.m_307839_();
      this.m_306961_();
   }

   private void m_306961_() {
      this.f_302522_ = this.f_107017_.m_20185_();
      this.f_302758_ = (this.f_107017_.m_20186_() + this.f_107017_.m_20188_()) / 2.0;
      this.f_303670_ = this.f_107017_.m_20189_();
   }

   private void m_307839_() {
      this.f_303041_ = this.f_302522_;
      this.f_302502_ = this.f_302758_;
      this.f_302216_ = this.f_303670_;
   }
}
