package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Matrix4f;

public class ScreenEffectRenderer {
   private static ResourceLocation f_110714_ = ResourceLocation.m_340282_("textures/misc/underwater.png");

   public static void m_110718_(Minecraft minecraftIn, PoseStack matrixStackIn) {
      Player player = minecraftIn.f_91074_;
      if (!player.f_19794_) {
         if (Reflector.ForgeHooksClient_renderBlockOverlay.exists() && Reflector.ForgeBlockModelShapes_getTexture3.exists()) {
            Pair<BlockState, BlockPos> overlay = getOverlayBlock(player);
            if (overlay != null) {
               Object overlayType = Reflector.getFieldValue(Reflector.RenderBlockScreenEffectEvent_OverlayType_BLOCK);
               if (!Reflector.ForgeHooksClient_renderBlockOverlay.callBoolean(player, matrixStackIn, overlayType, overlay.getLeft(), overlay.getRight())) {
                  TextureAtlasSprite sprite = (TextureAtlasSprite)Reflector.m_46374_(
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
            BlockState blockstate = m_110716_(player);
            if (blockstate != null) {
               m_173296_(minecraftIn.m_91289_().m_110907_().m_110882_(blockstate), matrixStackIn);
            }
         }
      }

      if (!minecraftIn.f_91074_.m_5833_()) {
         if (minecraftIn.f_91074_.m_204029_(FluidTags.f_13131_)) {
            if (!Reflector.ForgeHooksClient_renderWaterOverlay.callBoolean(player, matrixStackIn)) {
               m_110725_(minecraftIn, matrixStackIn);
            }
         } else if (Reflector.IForgeEntity_getEyeInFluidType.exists()) {
            FluidType eyeFluidType = (FluidType)Reflector.m_46374_(player, Reflector.IForgeEntity_getEyeInFluidType);
            if (!eyeFluidType.isAir()) {
               IClientFluidTypeExtensions.m_253057_(eyeFluidType).renderOverlay(minecraftIn, matrixStackIn);
            }
         }

         if (minecraftIn.f_91074_.m_6060_() && !Reflector.ForgeHooksClient_renderFireOverlay.callBoolean(player, matrixStackIn)) {
            m_110728_(minecraftIn, matrixStackIn);
         }
      }
   }

   @Nullable
   private static BlockState m_110716_(Player playerIn) {
      Pair<BlockState, BlockPos> overlay = getOverlayBlock(playerIn);
      return overlay == null ? null : (BlockState)overlay.getLeft();
   }

   private static Pair<BlockState, BlockPos> getOverlayBlock(Player playerIn) {
      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();

      for (int i = 0; i < 8; i++) {
         double d0 = playerIn.m_20185_() + (double)(((float)((i >> 0) % 2) - 0.5F) * playerIn.m_20205_() * 0.8F);
         double d1 = playerIn.m_20188_() + (double)(((float)((i >> 1) % 2) - 0.5F) * 0.1F * playerIn.m_6134_());
         double d2 = playerIn.m_20189_() + (double)(((float)((i >> 2) % 2) - 0.5F) * playerIn.m_20205_() * 0.8F);
         blockpos$mutableblockpos.m_122169_(d0, d1, d2);
         BlockState blockstate = playerIn.m_9236_().m_8055_(blockpos$mutableblockpos);
         if (blockstate.m_60799_() != RenderShape.INVISIBLE && blockstate.m_60831_(playerIn.m_9236_(), blockpos$mutableblockpos)) {
            return Pair.m_253057_(blockstate, blockpos$mutableblockpos.m_7949_());
         }
      }

      return null;
   }

   private static void m_173296_(TextureAtlasSprite spriteIn, PoseStack matrixStackIn) {
      if (SmartAnimations.isActive()) {
         SmartAnimations.spriteRendered(spriteIn);
      }

      RenderSystem.setShaderTexture(0, spriteIn.m_247685_());
      RenderSystem.setShader(GameRenderer::m_172820_);
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
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
      bufferbuilder.m_339083_(matrix4f, -1.0F, -1.0F, -0.5F).m_167083_(f7, f9).m_340057_(0.1F, 0.1F, 0.1F, 1.0F);
      bufferbuilder.m_339083_(matrix4f, 1.0F, -1.0F, -0.5F).m_167083_(f6, f9).m_340057_(0.1F, 0.1F, 0.1F, 1.0F);
      bufferbuilder.m_339083_(matrix4f, 1.0F, 1.0F, -0.5F).m_167083_(f6, f8).m_340057_(0.1F, 0.1F, 0.1F, 1.0F);
      bufferbuilder.m_339083_(matrix4f, -1.0F, 1.0F, -0.5F).m_167083_(f7, f8).m_340057_(0.1F, 0.1F, 0.1F, 1.0F);
      BufferUploader.m_231202_(bufferbuilder.m_339905_());
   }

   private static void m_110725_(Minecraft minecraftIn, PoseStack matrixStackIn) {
      renderFluid(minecraftIn, matrixStackIn, f_110714_);
   }

   public static void renderFluid(Minecraft minecraftIn, PoseStack matrixStackIn, ResourceLocation textureIn) {
      if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
         RenderSystem.setShader(GameRenderer::m_172817_);
         RenderSystem.setShaderTexture(0, textureIn);
         if (SmartAnimations.isActive()) {
            SmartAnimations.textureRendered(minecraftIn.m_91097_().m_118506_(f_110714_).m_117963_());
         }

         BlockPos blockpos = BlockPos.m_274561_(minecraftIn.f_91074_.m_20185_(), minecraftIn.f_91074_.m_20188_(), minecraftIn.f_91074_.m_20189_());
         float f = LightTexture.m_234316_(minecraftIn.f_91074_.m_9236_().m_6042_(), minecraftIn.f_91074_.m_9236_().m_46803_(blockpos));
         RenderSystem.enableBlend();
         RenderSystem.setShaderColor(f, f, f, 0.1F);
         float f1 = 4.0F;
         float f2 = -1.0F;
         float f3 = 1.0F;
         float f4 = -1.0F;
         float f5 = 1.0F;
         float f6 = -0.5F;
         float f7 = -minecraftIn.f_91074_.m_146908_() / 64.0F;
         float f8 = minecraftIn.f_91074_.m_146909_() / 64.0F;
         Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
         BufferBuilder bufferbuilder = Tesselator.m_85913_().m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
         bufferbuilder.m_339083_(matrix4f, -1.0F, -1.0F, -0.5F).m_167083_(4.0F + f7, 4.0F + f8);
         bufferbuilder.m_339083_(matrix4f, 1.0F, -1.0F, -0.5F).m_167083_(0.0F + f7, 4.0F + f8);
         bufferbuilder.m_339083_(matrix4f, 1.0F, 1.0F, -0.5F).m_167083_(0.0F + f7, 0.0F + f8);
         bufferbuilder.m_339083_(matrix4f, -1.0F, 1.0F, -0.5F).m_167083_(4.0F + f7, 0.0F + f8);
         BufferUploader.m_231202_(bufferbuilder.m_339905_());
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
      }
   }

   private static void m_110728_(Minecraft minecraftIn, PoseStack matrixStackIn) {
      RenderSystem.setShader(GameRenderer::m_172820_);
      RenderSystem.depthFunc(519);
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      TextureAtlasSprite textureatlassprite = ModelBakery.f_119220_.m_119204_();
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
      float f7 = Mth.m_14179_(f6, f, f2);
      float f8 = Mth.m_14179_(f6, f1, f2);
      float f9 = Mth.m_14179_(f6, f3, f5);
      float f10 = Mth.m_14179_(f6, f4, f5);
      float f11 = 1.0F;

      for (int i = 0; i < 2; i++) {
         matrixStackIn.m_85836_();
         float f12 = -0.5F;
         float f13 = 0.5F;
         float f14 = -0.5F;
         float f15 = 0.5F;
         float f16 = -0.5F;
         matrixStackIn.m_252880_((float)(-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_((float)(i * 2 - 1) * 10.0F));
         Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
         BufferBuilder bufferbuilder = Tesselator.m_85913_().m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
         bufferbuilder.m_339083_(matrix4f, -0.5F, -0.5F, -0.5F).m_167083_(f8, f10).m_340057_(1.0F, 1.0F, 1.0F, 0.9F);
         bufferbuilder.m_339083_(matrix4f, 0.5F, -0.5F, -0.5F).m_167083_(f7, f10).m_340057_(1.0F, 1.0F, 1.0F, 0.9F);
         bufferbuilder.m_339083_(matrix4f, 0.5F, 0.5F, -0.5F).m_167083_(f7, f9).m_340057_(1.0F, 1.0F, 1.0F, 0.9F);
         bufferbuilder.m_339083_(matrix4f, -0.5F, 0.5F, -0.5F).m_167083_(f8, f9).m_340057_(1.0F, 1.0F, 1.0F, 0.9F);
         BufferUploader.m_231202_(bufferbuilder.m_339905_());
         matrixStackIn.m_85849_();
      }

      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.depthFunc(515);
   }
}
