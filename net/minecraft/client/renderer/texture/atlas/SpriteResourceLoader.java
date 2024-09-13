package net.minecraft.client.renderer.texture.atlas;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.Mth;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

@FunctionalInterface
public interface SpriteResourceLoader {
   Logger f_260482_ = LogUtils.getLogger();

   static SpriteResourceLoader m_292996_(Collection<MetadataSectionSerializer<?>> serializersIn) {
      return (locIn, resIn) -> {
         ResourceMetadata resourcemetadata;
         try {
            resourcemetadata = resIn.m_215509_().m_293991_(serializersIn);
         } catch (Exception var9) {
            f_260482_.error("Unable to parse metadata from {}", locIn, var9);
            return null;
         }

         NativeImage nativeimage;
         try {
            InputStream inputstream = resIn.m_215507_();

            try {
               nativeimage = NativeImage.m_85058_(inputstream);
            } catch (Throwable var10) {
               if (inputstream != null) {
                  try {
                     inputstream.close();
                  } catch (Throwable var8) {
                     var10.addSuppressed(var8);
                  }
               }

               throw var10;
            }

            if (inputstream != null) {
               inputstream.close();
            }
         } catch (IOException var11) {
            f_260482_.error("Using missing texture, unable to load {}", locIn, var11);
            return null;
         }

         AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)resourcemetadata.m_214059_(AnimationMetadataSection.f_119011_)
            .orElse(AnimationMetadataSection.f_119012_);
         FrameSize framesize = animationmetadatasection.m_245821_(nativeimage.m_84982_(), nativeimage.m_85084_());
         if (Mth.m_264612_(nativeimage.m_84982_(), framesize.f_244129_()) && Mth.m_264612_(nativeimage.m_85084_(), framesize.f_244503_())) {
            if (Reflector.ForgeHooksClient_loadSpriteContents.exists()) {
               SpriteContents contents = (SpriteContents)Reflector.ForgeHooksClient_loadSpriteContents
                  .m_46374_(locIn, resIn, framesize, nativeimage, resourcemetadata);
               if (contents != null) {
                  return contents;
               }
            }

            return new SpriteContents(locIn, framesize, nativeimage, resourcemetadata);
         } else {
            f_260482_.error(
               "Image {} size {},{} is not multiple of frame size {},{}",
               new Object[]{locIn, nativeimage.m_84982_(), nativeimage.m_85084_(), framesize.f_244129_(), framesize.f_244503_()}
            );
            nativeimage.close();
            return null;
         }
      };
   }

   @Nullable
   SpriteContents m_294584_(ResourceLocation var1, Resource var2);
}
