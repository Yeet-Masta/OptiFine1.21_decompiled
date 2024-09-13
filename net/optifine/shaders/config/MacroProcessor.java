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
   public static InputStream m_288227_(InputStream in, String path, boolean useShaderOptions) throws IOException {
      String str = Config.readInputStream(in, "ASCII");
      String strMacroHeader = getMacroHeader(str, useShaderOptions);
      if (!strMacroHeader.isEmpty()) {
         str = strMacroHeader + str;
         if (Shaders.saveFinalShaders) {
            String filePath = path.replace(':', '/') + ".pre";
            Shaders.saveShader(filePath, str);
         }

         str = m_288227_(str);
      }

      if (Shaders.saveFinalShaders) {
         String filePath = path.replace(':', '/');
         Shaders.saveShader(filePath, str);
      }

      byte[] bytes = str.getBytes("ASCII");
      return new ByteArrayInputStream(bytes);
   }

   public static String m_288227_(String strIn) throws IOException {
      StringReader sr = new StringReader(strIn);
      BufferedReader br = new BufferedReader(sr);
      MacroState macroState = new MacroState();
      StringBuilder sb = new StringBuilder();

      while (true) {
         String line = br.readLine();
         if (line == null) {
            return sb.toString();
         }

         if (macroState.processLine(line) && !MacroState.isMacroLine(line)) {
            sb.append(line);
            sb.append("\n");
         }
      }
   }

   private static String getMacroHeader(String str, boolean useShaderOptions) throws IOException {
      StringBuilder sb = new StringBuilder();
      List<ShaderOption> sos = null;
      List<ShaderMacro> sms = null;
      StringReader sr = new StringReader(str);
      BufferedReader br = new BufferedReader(sr);

      while (true) {
         String line = br.readLine();
         if (line == null) {
            return sb.toString();
         }

         if (MacroState.isMacroLine(line)) {
            if (sb.length() == 0) {
               sb.append(ShaderMacros.getFixedMacroLines());
            }

            if (useShaderOptions) {
               if (sos == null) {
                  sos = getMacroOptions();
               }

               Iterator it = sos.iterator();

               while (it.hasNext()) {
                  ShaderOption so = (ShaderOption)it.next();
                  if (line.m_274455_(so.getName())) {
                     sb.append(so.getSourceLine());
                     sb.append("\n");
                     it.remove();
                  }
               }
            }

            if (sms == null) {
               sms = new ArrayList(Arrays.asList(ShaderMacros.getExtensions()));
            }

            Iterator it = sms.iterator();

            while (it.hasNext()) {
               ShaderMacro sm = (ShaderMacro)it.next();
               if (line.m_274455_(sm.getName())) {
                  sb.append(sm.getSourceLine());
                  sb.append("\n");
                  it.remove();
               }
            }
         }
      }
   }

   private static List<ShaderOption> getMacroOptions() {
      List<ShaderOption> list = new ArrayList();
      ShaderOption[] sos = Shaders.getShaderPackOptions();

      for (int i = 0; i < sos.length; i++) {
         ShaderOption so = sos[i];
         String sourceLine = so.getSourceLine();
         if (sourceLine != null && sourceLine.startsWith("#")) {
            list.add(so);
         }
      }

      return list;
   }
}
