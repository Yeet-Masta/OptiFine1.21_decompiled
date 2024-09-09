package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeaderLineVariable extends HeaderLine {
   private String qualifier;
   private String name;
   private String text;
   private Pattern patternLine;
   private Pattern patternName1;
   private Pattern patternName2;

   public HeaderLineVariable(String qualifier, String name, String text) {
      this.qualifier = qualifier;
      this.name = name;
      this.text = text;
      this.patternLine = Pattern.compile("^(.*\\W|)" + qualifier + "\\W.*\\W" + name + "(\\W.*|)$");
      this.patternName1 = Pattern.compile(",\\s*" + name + "(\\W)");
      this.patternName2 = Pattern.compile("(\\W)" + name + "\\s*,");
   }

   public String getText() {
      return this.text;
   }

   public boolean matches(String line) {
      if (!line.contains(this.name)) {
         return false;
      } else {
         Matcher m = this.patternLine.matcher(line);
         return m.matches();
      }
   }

   public String removeFrom(String line) {
      Matcher m1 = this.patternName1.matcher(line);
      String lineNew = m1.replaceAll("$1");
      Matcher m2 = this.patternName2.matcher(line);
      lineNew = m2.replaceAll("$1");
      return !lineNew.equals(line) ? lineNew : null;
   }
}
