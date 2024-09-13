package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeaderLineFunction extends HeaderLine {
   private String name;
   private String text;
   private Pattern patternLine;

   public HeaderLineFunction(String name, String text) {
      this.name = name;
      this.text = text;
      this.patternLine = Pattern.m_289905_("^\\s*\\w+\\s+" + name + "\\s*\\(.*\\).*$", 32);
   }

   @Override
   public String getText() {
      return this.text;
   }

   @Override
   public boolean matches(String line) {
      if (!line.m_274455_(this.name)) {
         return false;
      } else {
         Matcher m = this.patternLine.matcher(line);
         return m.matches();
      }
   }

   @Override
   public String removeFrom(String line) {
      return null;
   }
}
