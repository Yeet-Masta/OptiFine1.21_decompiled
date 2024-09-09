import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_139_;
import net.minecraft.src.C_1879_;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4675_.C_4681_;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Matrix4f;

public class ScreenEffectRenderer {
   private static final ResourceLocation a = ResourceLocation.b("textures/misc/underwater.png");

   public static void a(C_3391_ minecraftIn, PoseStack matrixStackIn) {
      C_1141_ player = minecraftIn.f_91074_;
      if (!player.f_19794_) {
         if (Reflector.ForgeHooksClient_renderBlockOverlay.exists() && Reflector.ForgeBlockModelShapes_getTexture3.exists()) {
            Pair<BlockState, C_4675_> overlay = getOverlayBlock(player);
            if (overlay != null) {
               Object overlayType = Reflector.getFieldValue(Reflector.RenderBlockScreenEffectEvent_OverlayType_BLOCK);
               if (!Reflector.ForgeHooksClient_renderBlockOverlay.callBoolean(player, matrixStackIn, overlayType, overlay.getLeft(), overlay.getRight())) {
                  TextureAtlasSprite sprite = (TextureAtlasSprite)Reflector.call(
                     minecraftIn.ao().a(), Reflector.ForgeBlockModelShapes_getTexture3, overlay.getLeft(), minecraftIn.r, overlay.getRight()
                  );
                  a(sprite, matrixStackIn);
               }
            }
         } else {
            BlockState blockstate = a(player);
            if (blockstate != null) {
               a(minecraftIn.ao().a().a(blockstate), matrixStackIn);
            }
         }
      }

      if (!minecraftIn.f_91074_.R_()) {
         if (minecraftIn.f_91074_.a(C_139_.f_13131_)) {
            if (!Reflector.ForgeHooksClient_renderWaterOverlay.callBoolean(player, matrixStackIn)) {
               b(minecraftIn, matrixStackIn);
            }
         } else if (Reflector.IForgeEntity_getEyeInFluidType.exists()) {
            FluidType eyeFluidType = (FluidType)Reflector.call(player, Reflector.IForgeEntity_getEyeInFluidType);
            if (!eyeFluidType.isAir()) {
               IClientFluidTypeExtensions.of(eyeFluidType).renderOverlay(minecraftIn, matrixStackIn);
            }
         }

         if (minecraftIn.f_91074_.bR() && !Reflector.ForgeHooksClient_renderFireOverlay.callBoolean(player, matrixStackIn)) {
            c(minecraftIn, matrixStackIn);
         }
      }
   }

   @Nullable
   private static BlockState a(C_1141_ playerIn) {
      Pair<BlockState, C_4675_> overlay = getOverlayBlock(playerIn);
      return overlay == null ? null : (BlockState)overlay.getLeft();
   }

   private static Pair<BlockState, C_4675_> getOverlayBlock(C_1141_ playerIn) {
      C_4681_ blockpos$mutableblockpos = new C_4681_();

      for (int i = 0; i < 8; i++) {
         double d0 = playerIn.m_20185_() + (double)(((float)((i >> 0) % 2) - 0.5F) * playerIn.m_20205_() * 0.8F);
         double d1 = playerIn.m_20188_() + (double)(((float)((i >> 1) % 2) - 0.5F) * 0.1F * playerIn.m_6134_());
         double d2 = playerIn.m_20189_() + (double)(((float)((i >> 2) % 2) - 0.5F) * playerIn.m_20205_() * 0.8F);
         blockpos$mutableblockpos.m_122169_(d0, d1, d2);
         BlockState blockstate = playerIn.m_9236_().a_(blockpos$mutableblockpos);
         if (blockstate.m_60799_() != C_1879_.INVISIBLE && blockstate.m_60831_(playerIn.m_9236_(), blockpos$mutableblockpos)) {
            return Pair.of(blockstate, blockpos$mutableblockpos.m_7949_());
         }
      }

      return null;
   }

   private static void a(TextureAtlasSprite spriteIn, PoseStack matrixStackIn) {
      if (SmartAnimations.isActive()) {
         SmartAnimations.spriteRendered(spriteIn);
      }

      RenderSystem.setShaderTexture(0, spriteIn.i());
      RenderSystem.setShader(GameRenderer::r);
      float f = 0.1F;
      float f1 = -1.0F;
      float f2 = 1.0F;
      float f3 = -1.0F;
      float f4 = 1.0F;
      float f5 = -0.5F;
      float f6 = spriteIn.c();
      float f7 = spriteIn.d();
      float f8 = spriteIn.g();
      float f9 = spriteIn.h();
      Matrix4f matrix4f = matrixStackIn.c().a();
      BufferBuilder bufferbuilder = Tesselator.b().a(VertexFormat.c.h, DefaultVertexFormat.j);
      bufferbuilder.a(matrix4f, -1.0F, -1.0F, -0.5F).a(f7, f9).a(0.1F, 0.1F, 0.1F, 1.0F);
      bufferbuilder.a(matrix4f, 1.0F, -1.0F, -0.5F).a(f6, f9).a(0.1F, 0.1F, 0.1F, 1.0F);
      bufferbuilder.a(matrix4f, 1.0F, 1.0F, -0.5F).a(f6, f8).a(0.1F, 0.1F, 0.1F, 1.0F);
      bufferbuilder.a(matrix4f, -1.0F, 1.0F, -0.5F).a(f7, f8).a(0.1F, 0.1F, 0.1F, 1.0F);
      BufferUploader.a(bufferbuilder.b());
   }

   private static void b(C_3391_ minecraftIn, PoseStack matrixStackIn) {
      renderFluid(minecraftIn, matrixStackIn, a);
   }

   public static void renderFluid(C_3391_ minecraftIn, PoseStack matrixStackIn, ResourceLocation textureIn) {
      if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
         RenderSystem.setShader(GameRenderer::q);
         RenderSystem.setShaderTexture(0, textureIn);
         if (SmartAnimations.isActive()) {
            SmartAnimations.textureRendered(minecraftIn.aa().b(a).a());
         }

         C_4675_ blockpos = C_4675_.m_274561_(minecraftIn.f_91074_.dt(), minecraftIn.f_91074_.dx(), minecraftIn.f_91074_.dz());
         float f = LightTexture.a(minecraftIn.f_91074_.dO().m_6042_(), minecraftIn.f_91074_.dO().m_46803_(blockpos));
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
         Matrix4f matrix4f = matrixStackIn.c().a();
         BufferBuilder bufferbuilder = Tesselator.b().a(VertexFormat.c.h, DefaultVertexFormat.i);
         bufferbuilder.a(matrix4f, -1.0F, -1.0F, -0.5F).a(4.0F + f7, 4.0F + f8);
         bufferbuilder.a(matrix4f, 1.0F, -1.0F, -0.5F).a(0.0F + f7, 4.0F + f8);
         bufferbuilder.a(matrix4f, 1.0F, 1.0F, -0.5F).a(0.0F + f7, 0.0F + f8);
         bufferbuilder.a(matrix4f, -1.0F, 1.0F, -0.5F).a(4.0F + f7, 0.0F + f8);
         BufferUploader.a(bufferbuilder.b());
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
      }
   }

   private static void c(C_3391_ minecraftIn, PoseStack matrixStackIn) {
      RenderSystem.setShader(GameRenderer::r);
      RenderSystem.depthFunc(519);
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      TextureAtlasSprite textureatlassprite = ModelBakery.b.c();
      if (SmartAnimations.isActive()) {
         SmartAnimations.spriteRendered(textureatlassprite);
      }

      RenderSystem.setShaderTexture(0, textureatlassprite.i());
      float f = textureatlassprite.c();
      float f1 = textureatlassprite.d();
      float f2 = (f + f1) / 2.0F;
      float f3 = textureatlassprite.g();
      float f4 = textureatlassprite.h();
      float f5 = (f3 + f4) / 2.0F;
      float f6 = textureatlassprite.k();
      float f7 = Mth.i(f6, f, f2);
      float f8 = Mth.i(f6, f1, f2);
      float f9 = Mth.i(f6, f3, f5);
      float f10 = Mth.i(f6, f4, f5);
      float f11 = 1.0F;

      for (int i = 0; i < 2; i++) {
         matrixStackIn.a();
         float f12 = -0.5F;
         float f13 = 0.5F;
         float f14 = -0.5F;
         float f15 = 0.5F;
         float f16 = -0.5F;
         matrixStackIn.a((float)(-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
         matrixStackIn.a(C_252363_.f_252436_.m_252977_((float)(i * 2 - 1) * 10.0F));
         Matrix4f matrix4f = matrixStackIn.c().a();
         BufferBuilder bufferbuilder = Tesselator.b().a(VertexFormat.c.h, DefaultVertexFormat.j);
         bufferbuilder.a(matrix4f, -0.5F, -0.5F, -0.5F).a(f8, f10).a(1.0F, 1.0F, 1.0F, 0.9F);
         bufferbuilder.a(matrix4f, 0.5F, -0.5F, -0.5F).a(f7, f10).a(1.0F, 1.0F, 1.0F, 0.9F);
         bufferbuilder.a(matrix4f, 0.5F, 0.5F, -0.5F).a(f7, f9).a(1.0F, 1.0F, 1.0F, 0.9F);
         bufferbuilder.a(matrix4f, -0.5F, 0.5F, -0.5F).a(f8, f9).a(1.0F, 1.0F, 1.0F, 0.9F);
         BufferUploader.a(bufferbuilder.b());
         matrixStackIn.b();
      }

      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.depthFunc(515);
   }
}
