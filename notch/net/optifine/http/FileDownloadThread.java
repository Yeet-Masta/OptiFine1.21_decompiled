package net.optifine.http;

import net.minecraft.src.C_3391_;

public class FileDownloadThread extends Thread {
   private String urlString = null;
   private IFileDownloadListener listener = null;

   public FileDownloadThread(String urlString, IFileDownloadListener listener) {
      this.urlString = urlString;
      this.listener = listener;
   }

   public void run() {
      try {
         byte[] bytes = HttpPipeline.get(this.urlString, C_3391_.m_91087_().m_91096_());
         this.listener.fileDownloadFinished(this.urlString, bytes, null);
      } catch (Exception var2) {
         this.listener.fileDownloadFinished(this.urlString, null, var2);
      }
   }

   public String getUrlString() {
      return this.urlString;
   }

   public IFileDownloadListener getListener() {
      return this.listener;
   }
}
