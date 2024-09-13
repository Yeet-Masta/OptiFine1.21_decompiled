package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import net.optifine.Config;
import net.optifine.shaders.Program;
import net.optifine.shaders.Shaders;

public class ItemPickupParticle extends Particle {
   private static int f_172257_;
   private RenderBuffers f_107020_;
   private Entity f_107021_;
   private Entity f_107017_;
   private int f_107018_;
   private EntityRenderDispatcher f_107019_;
   private double f_302522_;
   private double f_302758_;
   private double f_303670_;
   private double f_303041_;
   private double f_302502_;
   private double f_302216_;

   public ItemPickupParticle(
      EntityRenderDispatcher p_i107022_1_, RenderBuffers p_i107022_2_, ClientLevel p_i107022_3_, Entity p_i107022_4_, Entity p_i107022_5_
   ) {
      this(p_i107022_1_, p_i107022_2_, p_i107022_3_, p_i107022_4_, p_i107022_5_, p_i107022_4_.m_20184_());
   }

   private ItemPickupParticle(
      EntityRenderDispatcher renderManagerIn, RenderBuffers renderTypeBuffersIn, ClientLevel worldIn, Entity entityIn, Entity targetEntityIn, Vec3 movementIn
   ) {
      super(worldIn, entityIn.m_20185_(), entityIn.m_20186_(), entityIn.m_20189_(), movementIn.f_82479_, movementIn.f_82480_, movementIn.f_82481_);
      this.f_107020_ = renderTypeBuffersIn;
      this.f_107021_ = this.m_107036_(entityIn);
      this.f_107017_ = targetEntityIn;
      this.f_107019_ = renderManagerIn;
      this.m_306961_();
      this.m_307839_();
   }

   private Entity m_107036_(Entity entityIn) {
      return (Entity)(!(entityIn instanceof ItemEntity) ? entityIn : ((ItemEntity)entityIn).m_32066_());
   }

   @Override
   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }

   @Override
   public void m_5744_(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      Program oldShadersProgram = null;
      if (Config.isShaders()) {
         oldShadersProgram = Shaders.activeProgram;
         Shaders.nextEntity(this.f_107021_);
      }

      float f = ((float)this.f_107018_ + partialTicks) / 3.0F;
      f *= f;
      double d0 = Mth.m_14139_((double)partialTicks, this.f_303041_, this.f_302522_);
      double d1 = Mth.m_14139_((double)partialTicks, this.f_302502_, this.f_302758_);
      double d2 = Mth.m_14139_((double)partialTicks, this.f_302216_, this.f_303670_);
      double d3 = Mth.m_14139_((double)f, this.f_107021_.m_20185_(), d0);
      double d4 = Mth.m_14139_((double)f, this.f_107021_.m_20186_(), d1);
      double d5 = Mth.m_14139_((double)f, this.f_107021_.m_20189_(), d2);
      MultiBufferSource.BufferSource multibuffersource$buffersource = this.f_107020_.m_110104_();
      Vec3 vec3 = renderInfo.m_90583_();
      this.f_107019_
         .m_114384_(
            this.f_107021_,
            d3 - vec3.m_7096_(),
            d4 - vec3.m_7098_(),
            d5 - vec3.m_7094_(),
            this.f_107021_.m_146908_(),
            partialTicks,
            new PoseStack(),
            multibuffersource$buffersource,
            this.f_107019_.m_114394_(this.f_107021_, partialTicks)
         );
      multibuffersource$buffersource.m_109911_();
      if (Config.isShaders()) {
         Shaders.setEntityId(null);
         Shaders.useProgram(oldShadersProgram);
      }
   }

   @Override
   public void m_5989_() {
      this.f_107018_++;
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
