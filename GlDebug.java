import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.logging.LogUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import javax.annotation.Nullable;
import net.minecraft.src.C_3108_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4996_;
import net.optifine.Config;
import net.optifine.GlErrors;
import net.optifine.util.ArrayUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TimedEvent;
import org.lwjgl.opengl.ARBDebugOutput;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageARBCallback;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.KHRDebug;
import org.slf4j.Logger;

public class GlDebug {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = 10;
   private static final Queue<GlDebug.a> c = EvictingQueue.create(10);
   @Nullable
   private static volatile GlDebug.a d;
   private static final List<Integer> e = ImmutableList.of(37190, 37191, 37192, 33387);
   private static final List<Integer> f = ImmutableList.of(37190, 37191, 37192);
   private static boolean g;
   private static int[] ignoredErrors = makeIgnoredErrors();

   private static int[] makeIgnoredErrors() {
      String prop = System.getProperty("gl.ignore.errors");
      if (prop == null) {
         return new int[0];
      } else {
         String[] parts = Config.tokenize(prop, ",");
         int[] ids = new int[0];

         for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            int id = part.startsWith("0x") ? Config.parseHexInt(part, -1) : Config.parseInt(part, -1);
            if (id < 0) {
               Config.warn("Invalid error id: " + part);
            } else {
               Config.log("Ignore OpenGL error: " + id);
               ids = ArrayUtils.addIntToArray(ids, id);
            }
         }

         return ids;
      }
   }

   private static String d(int valueIn) {
      return "Unknown (0x" + Integer.toHexString(valueIn).toUpperCase() + ")";
   }

   public static String a(int sourceIn) {
      switch (sourceIn) {
         case 33350:
            return "API";
         case 33351:
            return "WINDOW SYSTEM";
         case 33352:
            return "SHADER COMPILER";
         case 33353:
            return "THIRD PARTY";
         case 33354:
            return "APPLICATION";
         case 33355:
            return "OTHER";
         default:
            return d(sourceIn);
      }
   }

   public static String b(int typeIn) {
      switch (typeIn) {
         case 33356:
            return "ERROR";
         case 33357:
            return "DEPRECATED BEHAVIOR";
         case 33358:
            return "UNDEFINED BEHAVIOR";
         case 33359:
            return "PORTABILITY";
         case 33360:
            return "PERFORMANCE";
         case 33361:
            return "OTHER";
         case 33384:
            return "MARKER";
         default:
            return d(typeIn);
      }
   }

   public static String c(int severityIn) {
      switch (severityIn) {
         case 33387:
            return "NOTIFICATION";
         case 37190:
            return "HIGH";
         case 37191:
            return "MEDIUM";
         case 37192:
            return "LOW";
         default:
            return d(severityIn);
      }
   }

   private static void a(int source, int type, int id, int severity, int messageLength, long message, long param) {
      if (type != 33385 && type != 33386) {
         if (!ArrayUtils.contains(ignoredErrors, id)) {
            if (!Config.isShaders() || source != 33352) {
               C_3391_ mc = C_3391_.m_91087_();
               if (mc == null || mc.aM() == null || !mc.aM().isClosed()) {
                  if (GlErrors.isEnabled(id)) {
                     String sourceStr = a(source);
                     String typeStr = b(type);
                     String severityStr = c(severity);
                     String messageStr = GLDebugMessageCallback.getMessage(messageLength, message);
                     messageStr = StrUtils.trim(messageStr, " \n\r\t");
                     String log = String.format("OpenGL %s %s: %s (%s)", sourceStr, typeStr, id, messageStr);
                     Exception exc = new Exception("Stack trace");
                     StackTraceElement[] stes = exc.getStackTrace();
                     StackTraceElement[] stes2 = stes.length > 2 ? (StackTraceElement[])Arrays.copyOfRange(stes, 2, stes.length) : stes;
                     exc.setStackTrace(stes2);
                     if (type == 33356) {
                        a.error(log, exc);
                     } else {
                        a.info(log, exc);
                     }

                     if (Config.isShowGlErrors() && TimedEvent.isActive("ShowGlErrorDebug", 10000L) && mc.r != null) {
                        String errorText = Config.getGlErrorString(id);
                        if (id == 0 || Config.equals(errorText, "Unknown")) {
                           errorText = messageStr;
                        }

                        String messageChat = C_4513_.m_118938_("of.message.openglError", new Object[]{id, errorText});
                        mc.l.d().a(C_4996_.m_237113_(messageChat));
                     }

                     String s = GLDebugMessageCallback.getMessage(messageLength, message);
                     synchronized (c) {
                        GlDebug.a gldebug$logentry = d;
                        if (gldebug$logentry != null && gldebug$logentry.a(source, type, id, severity, s)) {
                           gldebug$logentry.f++;
                        } else {
                           gldebug$logentry = new GlDebug.a(source, type, id, severity, s);
                           c.add(gldebug$logentry);
                           d = gldebug$logentry;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static List<String> a() {
      synchronized (c) {
         List<String> list = Lists.newArrayListWithCapacity(c.size());

         for (GlDebug.a gldebug$logentry : c) {
            list.add(gldebug$logentry + " x " + gldebug$logentry.f);
         }

         return list;
      }
   }

   public static boolean b() {
      return g;
   }

   public static void a(int debugVerbosity, boolean synchronous) {
      if (debugVerbosity > 0) {
         GLCapabilities glcapabilities = GL.getCapabilities();
         if (glcapabilities.GL_KHR_debug) {
            g = true;
            GL11.glEnable(37600);
            if (synchronous) {
               GL11.glEnable(33346);
            }

            for (int i = 0; i < e.size(); i++) {
               boolean flag = i < debugVerbosity;
               KHRDebug.glDebugMessageControl(4352, 4352, (Integer)e.get(i), (int[])null, flag);
            }

            KHRDebug.glDebugMessageCallback(GLX.make(GLDebugMessageCallback.create(GlDebug::a), C_3108_::m_84003_), 0L);
         } else if (glcapabilities.GL_ARB_debug_output) {
            g = true;
            if (synchronous) {
               GL11.glEnable(33346);
            }

            for (int j = 0; j < f.size(); j++) {
               boolean flag1 = j < debugVerbosity;
               ARBDebugOutput.glDebugMessageControlARB(4352, 4352, (Integer)f.get(j), (int[])null, flag1);
            }

            ARBDebugOutput.glDebugMessageCallbackARB(GLX.make(GLDebugMessageARBCallback.create(GlDebug::a), C_3108_::m_84003_), 0L);
         }
      }
   }

   static class a {
      private final int a;
      private final int b;
      private final int c;
      private final int d;
      private final String e;
      int f = 1;

      a(int sourceIn, int typeIn, int idIn, int severityIn, String messageIn) {
         this.a = idIn;
         this.b = sourceIn;
         this.c = typeIn;
         this.d = severityIn;
         this.e = messageIn;
      }

      boolean a(int sourceIn, int typeIn, int idIn, int severityIn, String messageIn) {
         return typeIn == this.c && sourceIn == this.b && idIn == this.a && severityIn == this.d && messageIn.equals(this.e);
      }

      public String toString() {
         return "id="
            + this.a
            + ", source="
            + GlDebug.a(this.b)
            + ", type="
            + GlDebug.b(this.c)
            + ", severity="
            + GlDebug.c(this.d)
            + ", message='"
            + this.e
            + "'";
      }
   }
}
