package com.mojang.blaze3d.platform;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
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
   private static final Logger f_84028_ = LogUtils.getLogger();
   private static final int f_166220_ = 10;
   private static final Queue<GlDebug.LogEntry> f_166221_ = EvictingQueue.create(10);
   @Nullable
   private static volatile GlDebug.LogEntry f_166222_;
   private static final List<Integer> f_84032_ = ImmutableList.of(37190, 37191, 37192, 33387);
   private static final List<Integer> f_84033_ = ImmutableList.of(37190, 37191, 37192);
   private static boolean f_166223_;
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

   private static String m_84036_(int valueIn) {
      return "Unknown (0x" + Integer.toHexString(valueIn).toUpperCase() + ")";
   }

   public static String m_84055_(int sourceIn) {
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
            return m_84036_(sourceIn);
      }
   }

   public static String m_84057_(int typeIn) {
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
            return m_84036_(typeIn);
      }
   }

   public static String m_84059_(int severityIn) {
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
            return m_84036_(severityIn);
      }
   }

   private static void m_84038_(int source, int type, int id, int severity, int messageLength, long message, long param) {
      if (type != 33385 && type != 33386) {
         if (!ArrayUtils.contains(ignoredErrors, id)) {
            if (!Config.isShaders() || source != 33352) {
               Minecraft mc = Minecraft.m_91087_();
               if (mc == null || mc.m_91268_() == null || !mc.m_91268_().isClosed()) {
                  if (GlErrors.isEnabled(id)) {
                     String sourceStr = m_84055_(source);
                     String typeStr = m_84057_(type);
                     String severityStr = m_84059_(severity);
                     String messageStr = GLDebugMessageCallback.getMessage(messageLength, message);
                     messageStr = StrUtils.trim(messageStr, " \n\r\t");
                     String log = String.format("OpenGL %s %s: %s (%s)", sourceStr, typeStr, id, messageStr);
                     Exception exc = new Exception("Stack trace");
                     StackTraceElement[] stes = exc.getStackTrace();
                     StackTraceElement[] stes2 = stes.length > 2 ? (StackTraceElement[])Arrays.copyOfRange(stes, 2, stes.length) : stes;
                     exc.setStackTrace(stes2);
                     if (type == 33356) {
                        f_84028_.error(log, exc);
                     } else {
                        f_84028_.info(log, exc);
                     }

                     if (Config.isShowGlErrors() && TimedEvent.isActive("ShowGlErrorDebug", 10000L) && mc.f_91073_ != null) {
                        String errorText = Config.getGlErrorString(id);
                        if (id == 0 || Config.equals(errorText, "Unknown")) {
                           errorText = messageStr;
                        }

                        String messageChat = I18n.m_118938_("of.message.openglError", new Object[]{id, errorText});
                        mc.f_91065_.m_93076_().m_93785_(Component.m_237113_(messageChat));
                     }

                     String s = GLDebugMessageCallback.getMessage(messageLength, message);
                     synchronized (f_166221_) {
                        GlDebug.LogEntry gldebug$logentry = f_166222_;
                        if (gldebug$logentry != null && gldebug$logentry.m_166239_(source, type, id, severity, s)) {
                           gldebug$logentry.f_166232_++;
                        } else {
                           gldebug$logentry = new GlDebug.LogEntry(source, type, id, severity, s);
                           f_166221_.add(gldebug$logentry);
                           f_166222_ = gldebug$logentry;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static List<String> m_166225_() {
      synchronized (f_166221_) {
         List<String> list = Lists.newArrayListWithCapacity(f_166221_.size());

         for (GlDebug.LogEntry gldebug$logentry : f_166221_) {
            list.add(gldebug$logentry + " x " + gldebug$logentry.f_166232_);
         }

         return list;
      }
   }

   public static boolean m_166226_() {
      return f_166223_;
   }

   public static void m_84049_(int debugVerbosity, boolean synchronous) {
      if (debugVerbosity > 0) {
         GLCapabilities glcapabilities = GL.getCapabilities();
         if (glcapabilities.GL_KHR_debug) {
            f_166223_ = true;
            GL11.glEnable(37600);
            if (synchronous) {
               GL11.glEnable(33346);
            }

            for (int i = 0; i < f_84032_.size(); i++) {
               boolean flag = i < debugVerbosity;
               KHRDebug.glDebugMessageControl(4352, 4352, (Integer)f_84032_.get(i), (int[])null, flag);
            }

            KHRDebug.glDebugMessageCallback(GLX.make(GLDebugMessageCallback.create(GlDebug::m_84038_), DebugMemoryUntracker::m_84003_), 0L);
         } else if (glcapabilities.GL_ARB_debug_output) {
            f_166223_ = true;
            if (synchronous) {
               GL11.glEnable(33346);
            }

            for (int j = 0; j < f_84033_.size(); j++) {
               boolean flag1 = j < debugVerbosity;
               ARBDebugOutput.glDebugMessageControlARB(4352, 4352, (Integer)f_84033_.get(j), (int[])null, flag1);
            }

            ARBDebugOutput.glDebugMessageCallbackARB(GLX.make(GLDebugMessageARBCallback.create(GlDebug::m_84038_), DebugMemoryUntracker::m_84003_), 0L);
         }
      }
   }

   static class LogEntry {
      private final int f_166227_;
      private final int f_166228_;
      private final int f_166229_;
      private final int f_166230_;
      private final String f_166231_;
      int f_166232_ = 1;

      LogEntry(int sourceIn, int typeIn, int idIn, int severityIn, String messageIn) {
         this.f_166227_ = idIn;
         this.f_166228_ = sourceIn;
         this.f_166229_ = typeIn;
         this.f_166230_ = severityIn;
         this.f_166231_ = messageIn;
      }

      boolean m_166239_(int sourceIn, int typeIn, int idIn, int severityIn, String messageIn) {
         return typeIn == this.f_166229_
            && sourceIn == this.f_166228_
            && idIn == this.f_166227_
            && severityIn == this.f_166230_
            && messageIn.equals(this.f_166231_);
      }

      public String toString() {
         return "id="
            + this.f_166227_
            + ", source="
            + GlDebug.m_84055_(this.f_166228_)
            + ", type="
            + GlDebug.m_84057_(this.f_166229_)
            + ", severity="
            + GlDebug.m_84059_(this.f_166230_)
            + ", message='"
            + this.f_166231_
            + "'";
      }
   }
}
