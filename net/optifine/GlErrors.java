package net.optifine;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import java.util.Iterator;

public class GlErrors {
   private static boolean frameStarted = false;
   private static long timeCheckStartMs = -1L;
   private static Int2ObjectOpenHashMap glErrors = new Int2ObjectOpenHashMap();
   private static final long CHECK_INTERVAL_MS = 3000L;
   private static final int CHECK_ERROR_MAX = 10;

   public static void frameStart() {
      frameStarted = true;
      if (!glErrors.isEmpty()) {
         if (timeCheckStartMs < 0L) {
            timeCheckStartMs = System.currentTimeMillis();
         }

         if (System.currentTimeMillis() > timeCheckStartMs + 3000L) {
            ObjectCollection errors = glErrors.values();
            Iterator it = errors.iterator();

            while(it.hasNext()) {
               GlError glError = (GlError)it.next();
               glError.onFrameStart();
            }

            timeCheckStartMs = System.currentTimeMillis();
         }

      }
   }

   public static boolean isEnabled(int error) {
      if (!frameStarted) {
         return true;
      } else {
         GlError glError = getGlError(error);
         return glError.isEnabled();
      }
   }

   private static GlError getGlError(int error) {
      GlError glError = (GlError)glErrors.get(error);
      if (glError == null) {
         glError = new GlError(error);
         glErrors.put(error, glError);
      }

      return glError;
   }

   public static class GlError {
      // $FF: renamed from: id int
      private int field_74;
      private int countErrors = 0;
      private int countErrorsSuppressed = 0;
      private boolean suppressed = false;
      private boolean oneErrorEnabled = false;

      public GlError(int id) {
         this.field_74 = id;
      }

      public void onFrameStart() {
         if (this.countErrorsSuppressed > 0) {
            Config.error("Suppressed " + this.countErrors + " OpenGL errors: " + this.field_74);
         }

         this.suppressed = this.countErrors > 10;
         this.countErrors = 0;
         this.countErrorsSuppressed = 0;
         this.oneErrorEnabled = true;
      }

      public boolean isEnabled() {
         ++this.countErrors;
         if (this.oneErrorEnabled) {
            this.oneErrorEnabled = false;
            return true;
         } else {
            if (this.suppressed) {
               ++this.countErrorsSuppressed;
            }

            return !this.suppressed;
         }
      }
   }
}
