import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.src.C_212950_;
import net.minecraft.src.C_243504_;
import net.minecraft.src.C_54_;
import net.minecraft.src.C_76_;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

@FunctionalInterface
public interface SpriteResourceLoader {
   Logger a = LogUtils.getLogger();

   static SpriteResourceLoader create(Collection<C_54_<?>> serializersIn) {
      return (locIn, resIn) -> {
         C_212950_ resourcemetadata;
         try {
            resourcemetadata = resIn.m_215509_().m_293991_(serializersIn);
         } catch (Exception var9) {
            a.error("Unable to parse metadata from {}", locIn, var9);
            return null;
         }

         NativeImage nativeimage;
         try {
            InputStream inputstream = resIn.m_215507_();

            try {
               nativeimage = NativeImage.a(inputstream);
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
            a.error("Using missing texture, unable to load {}", locIn, var11);
            return null;
         }

         AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)resourcemetadata.m_214059_(AnimationMetadataSection.a)
            .orElse(AnimationMetadataSection.e);
         C_243504_ framesize = animationmetadatasection.a(nativeimage.a(), nativeimage.b());
         if (Mth.c(nativeimage.a(), framesize.f_244129_()) && Mth.c(nativeimage.b(), framesize.f_244503_())) {
            if (Reflector.ForgeHooksClient_loadSpriteContents.exists()) {
               SpriteContents contents = (SpriteContents)Reflector.ForgeHooksClient_loadSpriteContents
                  .call(locIn, resIn, framesize, nativeimage, resourcemetadata);
               if (contents != null) {
                  return contents;
               }
            }

            return new SpriteContents(locIn, framesize, nativeimage, resourcemetadata);
         } else {
            a.error(
               "Image {} size {},{} is not multiple of frame size {},{}",
               new Object[]{locIn, nativeimage.a(), nativeimage.b(), framesize.f_244129_(), framesize.f_244503_()}
            );
            nativeimage.close();
            return null;
         }
      };
   }

   @Nullable
   SpriteContents loadSprite(ResourceLocation var1, C_76_ var2);
}
