package net.optifine.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.Config;

public class HttpPipeline {
   private static Map mapConnections = new HashMap();
   public static String HEADER_USER_AGENT;
   public static String HEADER_HOST;
   public static String HEADER_ACCEPT;
   public static String HEADER_LOCATION;
   public static String HEADER_KEEP_ALIVE;
   public static String HEADER_CONNECTION;
   public static String HEADER_VALUE_KEEP_ALIVE;
   public static String HEADER_TRANSFER_ENCODING;
   public static String HEADER_VALUE_CHUNKED;

   private HttpPipeline() {
   }

   public static void addRequest(String urlStr, HttpListener listener) throws IOException {
      addRequest(urlStr, listener, Proxy.NO_PROXY);
   }

   public static void addRequest(String urlStr, HttpListener listener, Proxy proxy) throws IOException {
      HttpRequest hr = makeRequest(urlStr, proxy);
      HttpPipelineRequest hpr = new HttpPipelineRequest(hr, listener);
      addRequest(hpr);
   }

   public static HttpRequest makeRequest(String urlStr, Proxy proxy) throws IOException {
      URL url = new URL(urlStr);
      if (!url.getProtocol().equals("http")) {
         throw new IOException("Only protocol http is supported: " + url);
      } else {
         String file = url.getFile();
         String host = url.getHost();
         int port = url.getPort();
         if (port <= 0) {
            port = 80;
         }

         String method = "GET";
         String http = "HTTP/1.1";
         Map<String, String> headers = new LinkedHashMap();
         headers.put("User-Agent", "Java/" + System.getProperty("java.version"));
         headers.put("Host", host);
         headers.put("Accept", "text/html, image/gif, image/png");
         headers.put("Connection", "keep-alive");
         byte[] body = new byte[0];
         return new HttpRequest(host, port, proxy, method, file, http, headers, body);
      }
   }

   public static void addRequest(HttpPipelineRequest pr) {
      HttpRequest hr = pr.getHttpRequest();

      for (HttpPipelineConnection conn = getConnection(hr.getHost(), hr.getPort(), hr.getProxy());
         !conn.addRequest(pr);
         conn = getConnection(hr.getHost(), hr.getPort(), hr.getProxy())
      ) {
         removeConnection(hr.getHost(), hr.getPort(), hr.getProxy(), conn);
      }
   }

   private static synchronized HttpPipelineConnection getConnection(String host, int port, Proxy proxy) {
      String key = makeConnectionKey(host, port, proxy);
      HttpPipelineConnection conn = (HttpPipelineConnection)mapConnections.get(key);
      if (conn == null) {
         conn = new HttpPipelineConnection(host, port, proxy);
         mapConnections.put(key, conn);
      }

      return conn;
   }

   private static synchronized void removeConnection(String host, int port, Proxy proxy, HttpPipelineConnection hpc) {
      String key = makeConnectionKey(host, port, proxy);
      HttpPipelineConnection conn = (HttpPipelineConnection)mapConnections.get(key);
      if (conn == hpc) {
         mapConnections.remove(key);
      }
   }

   private static String makeConnectionKey(String host, int port, Proxy proxy) {
      return host + ":" + port + "-" + proxy;
   }

   public static byte[] get(String urlStr) throws IOException {
      return get(urlStr, Proxy.NO_PROXY);
   }

   public static byte[] get(String urlStr, Proxy proxy) throws IOException {
      if (urlStr.startsWith("file:")) {
         URL urlFile = new URL(urlStr);
         InputStream in = urlFile.openStream();
         return Config.readAll(in);
      } else {
         HttpRequest req = makeRequest(urlStr, proxy);
         HttpResponse resp = executeRequest(req);
         if (resp.getStatus() / 100 != 2) {
            throw new IOException("HTTP response: " + resp.getStatus());
         } else {
            return resp.getBody();
         }
      }
   }

   public static HttpResponse executeRequest(HttpRequest req) throws IOException {
      final Map<String, Object> map = new HashMap();
      String KEY_RESPONSE = "Response";
      String KEY_EXCEPTION = "Exception";
      HttpListener l = new HttpListener() {
         @Override
         public void finished(HttpRequest req, HttpResponse resp) {
            synchronized (map) {
               map.put("Response", resp);
               map.notifyAll();
            }
         }

         @Override
         public void failed(HttpRequest req, Exception e) {
            synchronized (map) {
               map.put("Exception", e);
               map.notifyAll();
            }
         }
      };
      synchronized (map) {
         HttpPipelineRequest hpr = new HttpPipelineRequest(req, l);
         addRequest(hpr);

         try {
            map.wait();
         } catch (InterruptedException var10) {
            throw new InterruptedIOException("Interrupted");
         }

         Exception e = (Exception)map.get("Exception");
         if (e != null) {
            if (e instanceof IOException) {
               throw (IOException)e;
            } else if (e instanceof RuntimeException) {
               throw (RuntimeException)e;
            } else {
               throw new RuntimeException(e.getMessage(), e);
            }
         } else {
            HttpResponse resp = (HttpResponse)map.get("Response");
            if (resp == null) {
               throw new IOException("Response is null");
            } else {
               return resp;
            }
         }
      }
   }

   public static boolean hasActiveRequests() {
      for (HttpPipelineConnection conn : mapConnections.values()) {
         if (conn.hasActiveRequests()) {
            return true;
         }
      }

      return false;
   }
}
