package net.minecraft.src;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import javax.annotation.Nullable;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

@FunctionalInterface
public interface C_260391_ {
   Logger f_260482_ = LogUtils.getLogger();

   static C_260391_ m_292996_(Collection serializersIn) {
      return (locIn, resIn) -> {
         C_212950_ resourcemetadata;
         try {
            resourcemetadata = resIn.m_215509_().m_293991_(serializersIn);
         } catch (Exception var9) {
            f_260482_.error("Unable to parse metadata from {}", locIn, var9);
            return null;
         }

         C_3148_ nativeimage;
         try {
            InputStream inputstream = resIn.m_215507_();

            try {
               nativeimage = C_3148_.m_85058_(inputstream);
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

         C_4518_ animationmetadatasection = (C_4518_)resourcemetadata.m_214059_(C_4518_.f_119011_).orElse(C_4518_.f_119012_);
         C_243504_ framesize = animationmetadatasection.m_245821_(nativeimage.m_84982_(), nativeimage.m_85084_());
         if (C_188_.m_264612_(nativeimage.m_84982_(), framesize.f_244129_()) && C_188_.m_264612_(nativeimage.m_85084_(), framesize.f_244503_())) {
            if (Reflector.ForgeHooksClient_loadSpriteContents.exists()) {
               C_243582_ contents = (C_243582_)Reflector.ForgeHooksClient_loadSpriteContents.call(locIn, resIn, framesize, nativeimage, resourcemetadata);
               if (contents != null) {
                  return contents;
               }
            }

            return new C_243582_(locIn, framesize, nativeimage, resourcemetadata);
         } else {
            f_260482_.error("Image {} size {},{} is not multiple of frame size {},{}", new Object[]{locIn, nativeimage.m_84982_(), nativeimage.m_85084_(), framesize.f_244129_(), framesize.f_244503_()});
            nativeimage.close();
            return null;
         }
      };
   }

   @Nullable
   C_243582_ m_294584_(C_5265_ var1, C_76_ var2);
}
