package net.optifine.shaders.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class MacroProcessor {
   public static InputStream process(InputStream in, String path, boolean useShaderOptions) throws IOException {
      String str = Config.readInputStream(in, "ASCII");
      String strMacroHeader = getMacroHeader(str, useShaderOptions);
      String filePath;
      if (!strMacroHeader.isEmpty()) {
         str = strMacroHeader + str;
         if (Shaders.saveFinalShaders) {
            filePath = path.replace(':', '/') + ".pre";
            Shaders.saveShader(filePath, str);
         }

         str = process(str);
      }

      if (Shaders.saveFinalShaders) {
         filePath = path.replace(':', '/');
         Shaders.saveShader(filePath, str);
      }

      byte[] bytes = str.getBytes("ASCII");
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      return bais;
   }

   public static String process(String strIn) throws IOException {
      StringReader sr = new StringReader(strIn);
      BufferedReader br = new BufferedReader(sr);
      MacroState macroState = new MacroState();
      StringBuilder sb = new StringBuilder();

      while(true) {
         String line = br.readLine();
         if (line == null) {
            line = sb.toString();
            return line;
         }

         if (macroState.processLine(line) && !MacroState.isMacroLine(line)) {
            sb.append(line);
            sb.append("\n");
         }
      }
   }

   private static String getMacroHeader(String str, boolean useShaderOptions) throws IOException {
      StringBuilder sb = new StringBuilder();
      List sos = null;
      List sms = null;
      StringReader sr = new StringReader(str);
      BufferedReader br = new BufferedReader(sr);

      while(true) {
         String line;
         do {
            line = br.readLine();
            if (line == null) {
               return sb.toString();
            }
         } while(!MacroState.isMacroLine(line));

         if (sb.length() == 0) {
            sb.append(ShaderMacros.getFixedMacroLines());
         }

         Iterator it;
         if (useShaderOptions) {
            if (sos == null) {
               sos = getMacroOptions();
            }

            it = sos.iterator();

            while(it.hasNext()) {
               ShaderOption so = (ShaderOption)it.next();
               if (line.contains(so.getName())) {
                  sb.append(so.getSourceLine());
                  sb.append("\n");
                  it.remove();
               }
            }
         }

         if (sms == null) {
            sms = new ArrayList(Arrays.asList(ShaderMacros.getExtensions()));
         }

         it = sms.iterator();

         while(it.hasNext()) {
            ShaderMacro sm = (ShaderMacro)it.next();
            if (line.contains(sm.getName())) {
               sb.append(sm.getSourceLine());
               sb.append("\n");
               it.remove();
            }
         }
      }
   }

   private static List getMacroOptions() {
      List list = new ArrayList();
      ShaderOption[] sos = Shaders.getShaderPackOptions();

      for(int i = 0; i < sos.length; ++i) {
         ShaderOption so = sos[i];
         String sourceLine = so.getSourceLine();
         if (sourceLine != null && sourceLine.startsWith("#")) {
            list.add(so);
         }
      }

      return list;
   }
}
