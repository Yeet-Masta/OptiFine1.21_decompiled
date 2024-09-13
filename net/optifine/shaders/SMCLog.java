package net.optifine.shaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SMCLog {
   private static Logger LOGGER = LogManager.getLogger();
   private static String PREFIX;

   public static void severe(String message) {
      LOGGER.error("[Shaders] " + message);
   }

   public static void warning(String message) {
      LOGGER.warn("[Shaders] " + message);
   }

   public static void info(String message) {
      LOGGER.info("[Shaders] " + message);
   }

   public static void fine(String message) {
      LOGGER.debug("[Shaders] " + message);
   }

   public static void severe(String format, Object... args) {
      String message = String.m_12886_(format, args);
      LOGGER.error("[Shaders] " + message);
   }

   public static void warning(String format, Object... args) {
      String message = String.m_12886_(format, args);
      LOGGER.warn("[Shaders] " + message);
   }

   public static void info(String format, Object... args) {
      String message = String.m_12886_(format, args);
      LOGGER.info("[Shaders] " + message);
   }

   public static void fine(String format, Object... args) {
      String message = String.m_12886_(format, args);
      LOGGER.debug("[Shaders] " + message);
   }
}
