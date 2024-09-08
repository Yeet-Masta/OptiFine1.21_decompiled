package net.optifine.util;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4134_;

public class FrameEvent {
   private static Map<String, Integer> mapEventFrames = new HashMap();

   public static boolean isActive(String name, int frameInterval) {
      synchronized (mapEventFrames) {
         C_4134_ worldRenderer = C_3391_.m_91087_().f_91060_;
         if (worldRenderer == null) {
            return false;
         } else {
            int frameCount = worldRenderer.getFrameCount();
            if (frameCount <= 0) {
               return false;
            } else {
               Integer frameCountLastObj = (Integer)mapEventFrames.get(name);
               if (frameCountLastObj == null) {
                  frameCountLastObj = new Integer(frameCount);
                  mapEventFrames.put(name, frameCountLastObj);
               }

               int frameCountLast = frameCountLastObj;
               if (frameCount > frameCountLast && frameCount < frameCountLast + frameInterval) {
                  return false;
               } else {
                  mapEventFrames.put(name, new Integer(frameCount));
                  return true;
               }
            }
         }
      }
   }
}
