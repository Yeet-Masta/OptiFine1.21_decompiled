package net.optifine.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.optifine.Config;

public class HttpUtils {
   private static String playerItemsUrl = null;
   public static final String SERVER_URL = "http://s.optifine.net";
   public static final String POST_URL = "http://optifine.net";

   public static byte[] get(String urlStr) throws IOException {
      HttpURLConnection conn = null;

      byte[] var10;
      try {
         URL url = new URL(urlStr);
         conn = (HttpURLConnection)url.openConnection(Minecraft.m_91087_().m_91096_());
         conn.setDoInput(true);
         conn.setDoOutput(false);
         conn.connect();
         if (conn.getResponseCode() / 100 != 2) {
            if (conn.getErrorStream() != null) {
               Config.readAll(conn.getErrorStream());
            }

            throw new IOException("HTTP response: " + conn.getResponseCode());
         }

         InputStream in = conn.getInputStream();
         byte[] bytes = new byte[conn.getContentLength()];
         int pos = 0;

         do {
            int len = in.read(bytes, pos, bytes.length - pos);
            if (len < 0) {
               throw new IOException("Input stream closed: " + urlStr);
            }

            pos += len;
         } while (pos < bytes.length);

         var10 = bytes;
      } finally {
         if (conn != null) {
            conn.disconnect();
         }
      }

      return var10;
   }

   public static String post(String urlStr, Map headers, byte[] content) throws IOException {
      HttpURLConnection conn = null;

      String var11;
      try {
         URL url = new URL(urlStr);
         conn = (HttpURLConnection)url.openConnection(Minecraft.m_91087_().m_91096_());
         conn.setRequestMethod("POST");
         if (headers != null) {
            for (String key : headers.keySet()) {
               String val = headers.get(key) + "";
               conn.setRequestProperty(key, val);
            }
         }

         conn.setRequestProperty("Content-Type", "text/plain");
         conn.setRequestProperty("Content-Length", content.length + "");
         conn.setRequestProperty("Content-Language", "en-US");
         conn.setUseCaches(false);
         conn.setDoInput(true);
         conn.setDoOutput(true);
         OutputStream os = conn.getOutputStream();
         os.write(content);
         os.flush();
         os.close();
         InputStream in = conn.getInputStream();
         InputStreamReader isr = new InputStreamReader(in, "ASCII");
         BufferedReader br = new BufferedReader(isr);
         StringBuffer sb = new StringBuffer();

         String line;
         while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append('\r');
         }

         br.close();
         var11 = sb.toString();
      } finally {
         if (conn != null) {
            conn.disconnect();
         }
      }

      return var11;
   }

   public static synchronized String getPlayerItemsUrl() {
      if (playerItemsUrl == null) {
         try {
            boolean local = Config.parseBoolean(System.getProperty("player.models.local"), false);
            if (local) {
               File dirMc = Minecraft.m_91087_().f_91069_;
               File dirModels = new File(dirMc, "playermodels");
               playerItemsUrl = dirModels.toURI().toURL().toExternalForm();
            }
         } catch (Exception var3) {
            Config.warn(var3.getClass().getName() + ": " + var3.getMessage());
         }

         if (playerItemsUrl == null) {
            playerItemsUrl = "http://s.optifine.net";
         }
      }

      return playerItemsUrl;
   }
}
