package net.minecraft.src;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.src.C_4675_.C_4681_;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Matrix4f;

public class C_4176_ {
   private static final C_5265_ f_110714_ = C_5265_.m_340282_("textures/misc/underwater.png");

   public static void m_110718_(C_3391_ minecraftIn, C_3181_ matrixStackIn) {
      C_1141_ player = minecraftIn.f_91074_;
      if (!player.ag) {
         if (Reflector.ForgeHooksClient_renderBlockOverlay.exists() && Reflector.ForgeBlockModelShapes_getTexture3.exists()) {
            Pair<C_2064_, C_4675_> overlay = getOverlayBlock(player);
            if (overlay != null) {
               Object overlayType = Reflector.getFieldValue(Reflector.RenderBlockScreenEffectEvent_OverlayType_BLOCK);
               if (!Reflector.ForgeHooksClient_renderBlockOverlay.callBoolean(player, matrixStackIn, overlayType, overlay.getLeft(), overlay.getRight())) {
                  C_4486_ sprite = (C_4486_)Reflector.call(
                     minecraftIn.m_91289_().m_110907_(),
                     Reflector.ForgeBlockModelShapes_getTexture3,
                     overlay.getLeft(),
                     minecraftIn.f_91073_,
                     overlay.getRight()
                  );
                  m_173296_(sprite, matrixStackIn);
               }
            }
         } else {
            C_2064_ blockstate = m_110716_(player);
            if (blockstate != null) {
               m_173296_(minecraftIn.m_91289_().m_110907_().m_110882_(blockstate), matrixStackIn);
            }
         }
      }

      if (!minecraftIn.f_91074_.R_()) {
         if (minecraftIn.f_91074_.a(C_139_.f_13131_)) {
            if (!Reflector.ForgeHooksClient_renderWaterOverlay.callBoolean(player, matrixStackIn)) {
               m_110725_(minecraftIn, matrixStackIn);
            }
         } else if (Reflector.IForgeEntity_getEyeInFluidType.exists()) {
            FluidType eyeFluidType = (FluidType)Reflector.call(player, Reflector.IForgeEntity_getEyeInFluidType);
            if (!eyeFluidType.isAir()) {
               IClientFluidTypeExtensions.of(eyeFluidType).renderOverlay(minecraftIn, matrixStackIn);
            }
         }

         if (minecraftIn.f_91074_.bR() && !Reflector.ForgeHooksClient_renderFireOverlay.callBoolean(player, matrixStackIn)) {
            m_110728_(minecraftIn, matrixStackIn);
         }
      }
   }

   @Nullable
   private static C_2064_ m_110716_(C_1141_ playerIn) {
      Pair<C_2064_, C_4675_> overlay = getOverlayBlock(playerIn);
      return overlay == null ? null : (C_2064_)overlay.getLeft();
   }

   private static Pair<C_2064_, C_4675_> getOverlayBlock(C_1141_ playerIn) {
      C_4681_ blockpos$mutableblockpos = new C_4681_();

      for (int i = 0; i < 8; i++) {
         double d0 = playerIn.dt() + (double)(((float)((i >> 0) % 2) - 0.5F) * playerIn.dj() * 0.8F);
         double d1 = playerIn.dx() + (double)(((float)((i >> 1) % 2) - 0.5F) * 0.1F * playerIn.eb());
         double d2 = playerIn.dz() + (double)(((float)((i >> 2) % 2) - 0.5F) * playerIn.dj() * 0.8F);
         blockpos$mutableblockpos.m_122169_(d0, d1, d2);
         C_2064_ blockstate = playerIn.dO().m_8055_(blockpos$mutableblockpos);
         if (blockstate.m_60799_() != C_1879_.INVISIBLE && blockstate.m_60831_(playerIn.dO(), blockpos$mutableblockpos)) {
            return Pair.of(blockstate, blockpos$mutableblockpos.m_7949_());
         }
      }

      return null;
   }

   private static void m_173296_(C_4486_ spriteIn, C_3181_ matrixStackIn) {
      if (SmartAnimations.isActive()) {
         SmartAnimations.spriteRendered(spriteIn);
      }

      RenderSystem.setShaderTexture(0, spriteIn.m_247685_());
      RenderSystem.setShader(C_4124_::m_172820_);
      float f = 0.1F;
      float f1 = -1.0F;
      float f2 = 1.0F;
      float f3 = -1.0F;
      float f4 = 1.0F;
      float f5 = -0.5F;
      float f6 = spriteIn.m_118409_();
      float f7 = spriteIn.m_118410_();
      float f8 = spriteIn.m_118411_();
      float f9 = spriteIn.m_118412_();
      Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
      C_3173_ bufferbuilder = C_3185_.m_85913_().m_339075_(C_3188_.C_141549_.QUADS, C_3179_.f_85819_);
      bufferbuilder.m_339083_(matrix4f, -1.0F, -1.0F, -0.5F).m_167083_(f7, f9).m_340057_(0.1F, 0.1F, 0.1F, 1.0F);
      bufferbuilder.m_339083_(matrix4f, 1.0F, -1.0F, -0.5F).m_167083_(f6, f9).m_340057_(0.1F, 0.1F, 0.1F, 1.0F);
      bufferbuilder.m_339083_(matrix4f, 1.0F, 1.0F, -0.5F).m_167083_(f6, f8).m_340057_(0.1F, 0.1F, 0.1F, 1.0F);
      bufferbuilder.m_339083_(matrix4f, -1.0F, 1.0F, -0.5F).m_167083_(f7, f8).m_340057_(0.1F, 0.1F, 0.1F, 1.0F);
      C_3177_.m_231202_(bufferbuilder.m_339905_());
   }

   private static void m_110725_(C_3391_ minecraftIn, C_3181_ matrixStackIn) {
      renderFluid(minecraftIn, matrixStackIn, f_110714_);
   }

   public static void renderFluid(C_3391_ minecraftIn, C_3181_ matrixStackIn, C_5265_ textureIn) {
      if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
         RenderSystem.setShader(C_4124_::m_172817_);
         RenderSystem.setShaderTexture(0, textureIn);
         if (SmartAnimations.isActive()) {
            SmartAnimations.textureRendered(minecraftIn.m_91097_().m_118506_(f_110714_).m_117963_());
         }

         C_4675_ blockpos = C_4675_.m_274561_(minecraftIn.f_91074_.dt(), minecraftIn.f_91074_.dx(), minecraftIn.f_91074_.dz());
         float f = C_4138_.m_234316_(minecraftIn.f_91074_.dO().m_6042_(), minecraftIn.f_91074_.dO().A(blockpos));
         RenderSystem.enableBlend();
         RenderSystem.setShaderColor(f, f, f, 0.1F);
         float f1 = 4.0F;
         float f2 = -1.0F;
         float f3 = 1.0F;
         float f4 = -1.0F;
         float f5 = 1.0F;
         float f6 = -0.5F;
         float f7 = -minecraftIn.f_91074_.dE() / 64.0F;
         float f8 = minecraftIn.f_91074_.dG() / 64.0F;
         Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
         C_3173_ bufferbuilder = C_3185_.m_85913_().m_339075_(C_3188_.C_141549_.QUADS, C_3179_.f_85817_);
         bufferbuilder.m_339083_(matrix4f, -1.0F, -1.0F, -0.5F).m_167083_(4.0F + f7, 4.0F + f8);
         bufferbuilder.m_339083_(matrix4f, 1.0F, -1.0F, -0.5F).m_167083_(0.0F + f7, 4.0F + f8);
         bufferbuilder.m_339083_(matrix4f, 1.0F, 1.0F, -0.5F).m_167083_(0.0F + f7, 0.0F + f8);
         bufferbuilder.m_339083_(matrix4f, -1.0F, 1.0F, -0.5F).m_167083_(4.0F + f7, 0.0F + f8);
         C_3177_.m_231202_(bufferbuilder.m_339905_());
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
      }
   }

   private static void m_110728_(C_3391_ minecraftIn, C_3181_ matrixStackIn) {
      RenderSystem.setShader(C_4124_::m_172820_);
      RenderSystem.depthFunc(519);
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      C_4486_ textureatlassprite = C_4532_.f_119220_.m_119204_();
      if (SmartAnimations.isActive()) {
         SmartAnimations.spriteRendered(textureatlassprite);
      }

      RenderSystem.setShaderTexture(0, textureatlassprite.m_247685_());
      float f = textureatlassprite.m_118409_();
      float f1 = textureatlassprite.m_118410_();
      float f2 = (f + f1) / 2.0F;
      float f3 = textureatlassprite.m_118411_();
      float f4 = textureatlassprite.m_118412_();
      float f5 = (f3 + f4) / 2.0F;
      float f6 = textureatlassprite.m_118417_();
      float f7 = C_188_.m_14179_(f6, f, f2);
      float f8 = C_188_.m_14179_(f6, f1, f2);
      float f9 = C_188_.m_14179_(f6, f3, f5);
      float f10 = C_188_.m_14179_(f6, f4, f5);
      float f11 = 1.0F;

      for (int i = 0; i < 2; i++) {
         matrixStackIn.m_85836_();
         float f12 = -0.5F;
         float f13 = 0.5F;
         float f14 = -0.5F;
         float f15 = 0.5F;
         float f16 = -0.5F;
         matrixStackIn.m_252880_((float)(-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_((float)(i * 2 - 1) * 10.0F));
         Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
         C_3173_ bufferbuilder = C_3185_.m_85913_().m_339075_(C_3188_.C_141549_.QUADS, C_3179_.f_85819_);
         bufferbuilder.m_339083_(matrix4f, -0.5F, -0.5F, -0.5F).m_167083_(f8, f10).m_340057_(1.0F, 1.0F, 1.0F, 0.9F);
         bufferbuilder.m_339083_(matrix4f, 0.5F, -0.5F, -0.5F).m_167083_(f7, f10).m_340057_(1.0F, 1.0F, 1.0F, 0.9F);
         bufferbuilder.m_339083_(matrix4f, 0.5F, 0.5F, -0.5F).m_167083_(f7, f9).m_340057_(1.0F, 1.0F, 1.0F, 0.9F);
         bufferbuilder.m_339083_(matrix4f, -0.5F, 0.5F, -0.5F).m_167083_(f8, f9).m_340057_(1.0F, 1.0F, 1.0F, 0.9F);
         C_3177_.m_231202_(bufferbuilder.m_339905_());
         matrixStackIn.m_85849_();
      }

      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.depthFunc(515);
   }
}
