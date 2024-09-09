package net.optifine.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
   public static List collectFiles(File folder, boolean recursive) {
      List files = new ArrayList();
      collectFiles(folder, "", files, recursive);
      return files;
   }

   public static void collectFiles(File folder, String basePath, List list, boolean recursive) {
      File[] files = folder.listFiles();
      if (files != null) {
         for(int i = 0; i < files.length; ++i) {
            File file = files[i];
            String dirPath;
            if (file.isFile()) {
               dirPath = basePath + file.getName();
               list.add(dirPath);
            } else if (recursive && file.isDirectory()) {
               dirPath = basePath + file.getName() + "/";
               collectFiles(file, dirPath, list, recursive);
            }
         }

      }
   }
}
