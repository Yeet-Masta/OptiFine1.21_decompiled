package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.metadata.animation.AnimationFrame;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.server.packs.resources.ResourceMetadata.Builder;

public final class MissingTextureAtlasSprite {
   private static final int f_174688_ = 16;
   private static final int f_174689_ = 16;
   private static final String f_174690_ = "missingno";
   private static final net.minecraft.resources.ResourceLocation f_118059_ = net.minecraft.resources.ResourceLocation.m_340282_("missingno");
   private static final ResourceMetadata f_290987_ = new Builder()
      .m_294003_(
         net.minecraft.client.resources.metadata.animation.AnimationMetadataSection.f_119011_,
         new net.minecraft.client.resources.metadata.animation.AnimationMetadataSection(ImmutableList.of(new AnimationFrame(0, -1)), 16, 16, 1, false)
      )
      .m_293106_();
   @Nullable
   private static net.minecraft.client.renderer.texture.DynamicTexture f_118060_;

   private static com.mojang.blaze3d.platform.NativeImage m_245315_(int widthIn, int heightIn) {
      com.mojang.blaze3d.platform.NativeImage nativeimage = new com.mojang.blaze3d.platform.NativeImage(widthIn, heightIn, false);
      int i = -16777216;
      int j = -524040;

      for (int k = 0; k < heightIn; k++) {
         for (int l = 0; l < widthIn; l++) {
            if (k < heightIn / 2 ^ l < widthIn / 2) {
               nativeimage.m_84988_(l, k, -524040);
            } else {
               nativeimage.m_84988_(l, k, -16777216);
            }
         }
      }

      return nativeimage;
   }

   public static net.minecraft.client.renderer.texture.SpriteContents m_246104_() {
      com.mojang.blaze3d.platform.NativeImage nativeimage = m_245315_(16, 16);
      return new net.minecraft.client.renderer.texture.SpriteContents(f_118059_, new FrameSize(16, 16), nativeimage, f_290987_);
   }

   public static net.minecraft.resources.ResourceLocation m_118071_() {
      return f_118059_;
   }

   public static net.minecraft.client.renderer.texture.DynamicTexture m_118080_() {
      if (f_118060_ == null) {
         com.mojang.blaze3d.platform.NativeImage nativeimage = m_245315_(16, 16);
         nativeimage.m_85123_();
         f_118060_ = new net.minecraft.client.renderer.texture.DynamicTexture(nativeimage);
         Minecraft.m_91087_().m_91097_().m_118495_(f_118059_, f_118060_);
      }

      return f_118060_;
   }

   public static boolean isMisingSprite(net.minecraft.client.renderer.texture.TextureAtlasSprite sprite) {
      return sprite.getName() == f_118059_;
   }
}
