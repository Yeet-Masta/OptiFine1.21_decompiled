package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.NativeImage;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.metadata.animation.AnimationFrame;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.server.packs.resources.ResourceMetadata.Builder;

public class MissingTextureAtlasSprite {
   private static int f_174688_;
   private static int f_174689_;
   private static String f_174690_;
   private static ResourceLocation f_118059_ = ResourceLocation.m_340282_("missingno");
   private static ResourceMetadata f_290987_ = new Builder()
      .m_294003_(AnimationMetadataSection.f_119011_, new AnimationMetadataSection(ImmutableList.m_253057_(new AnimationFrame(0, -1)), 16, 16, 1, false))
      .m_293106_();
   @Nullable
   private static DynamicTexture f_118060_;

   private static NativeImage m_245315_(int widthIn, int heightIn) {
      NativeImage nativeimage = new NativeImage(widthIn, heightIn, false);
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

   public static SpriteContents m_246104_() {
      NativeImage nativeimage = m_245315_(16, 16);
      return new SpriteContents(f_118059_, new FrameSize(16, 16), nativeimage, f_290987_);
   }

   public static ResourceLocation m_118071_() {
      return f_118059_;
   }

   public static DynamicTexture m_118080_() {
      if (f_118060_ == null) {
         NativeImage nativeimage = m_245315_(16, 16);
         nativeimage.m_85123_();
         f_118060_ = new DynamicTexture(nativeimage);
         Minecraft.m_91087_().m_91097_().m_118495_(f_118059_, f_118060_);
      }

      return f_118060_;
   }

   public static boolean isMisingSprite(TextureAtlasSprite sprite) {
      return sprite.getName() == f_118059_;
   }
}
