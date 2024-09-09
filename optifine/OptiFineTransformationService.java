package optifine;

import cpw.mods.jarhandling.SecureJar;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
import cpw.mods.modlauncher.api.IModuleLayerManager.Layer;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OptiFineTransformationService implements ITransformationService {
   private static final Logger LOGGER = LogManager.getLogger();
   private static URL ofZipFileUrl;
   private static ZipFile ofZipFile;
   private static Path ofZipFilePath;
   private static OptiFineTransformer transformer;

   public String name() {
      return "OptiFine";
   }

   public void initialize(IEnvironment environment) {
      LOGGER.info("OptiFineTransformationService.initialize");
   }

   public List beginScanning(IEnvironment environment) {
      return List.of();
   }

   public List completeScan(IModuleLayerManager layerManager) {
      List list = new ArrayList();
      new ArrayList();
      URL urlSrg = this.getClass().getResource("/srg");
      Path pathSrg = Paths.get(URI.create(urlSrg.toString()));
      ITransformationService.Resource resSrg = new ITransformationService.Resource(Layer.GAME, List.of(SecureJar.from(new Path[]{pathSrg})));
      list.add(resSrg);
      return list;
   }

   public void onLoad(IEnvironment env, Set otherServices) throws IncompatibleEnvironmentException {
      LOGGER.info("OptiFineTransformationService.onLoad");
      ofZipFileUrl = OptiFineTransformer.class.getProtectionDomain().getCodeSource().getLocation();
      LOGGER.info("OptiFine ZIP file URL: " + ofZipFileUrl);

      try {
         URI uri = URI.create(ofZipFileUrl.getPath().replace("file:", ""));
         File file = toFile(uri);
         if (file.getPath().endsWith("!")) {
            file = new File(file.getPath().replace("!", ""));
         }

         ofZipFile = new ZipFile(file);
         LOGGER.info("OptiFine ZIP file: " + file);
         ofZipFilePath = file.toPath();
         transformer = new OptiFineTransformer(ofZipFile, env);
         OptiFineResourceLocator.setResourceLocator(transformer);
      } catch (Exception var5) {
         LOGGER.error("Error loading OptiFine ZIP file: " + ofZipFileUrl, var5);
         throw new IncompatibleEnvironmentException("Error loading OptiFine ZIP file: " + ofZipFileUrl);
      }
   }

   public static File toFile(URI uri) {
      if (!"union".equals(uri.getScheme())) {
         return new File(uri.getPath());
      } else {
         try {
            String path = uri.getPath();
            if (path.contains("#")) {
               path = path.substring(0, path.lastIndexOf("#"));
            }

            File file = new File(path);
            ofZipFileUrl = file.toURI().toURL();
            Map map = new HashMap();
            map.put("create", "true");
            FileSystems.newFileSystem(URI.create("jar:" + ofZipFileUrl + "!/"), map);
            return file;
         } catch (Exception var4) {
            var4.printStackTrace();
            return null;
         }
      }
   }

   public static Optional getResourceUrl(String name) {
      if (name.endsWith(".class") && !name.startsWith("optifine/")) {
         name = "srg/" + name;
      }

      if (transformer == null) {
         return Optional.empty();
      } else {
         ZipEntry ze = ofZipFile.getEntry(name);
         if (ze == null) {
            return Optional.empty();
         } else {
            try {
               String ofZipUrlStr = ofZipFileUrl.toExternalForm();
               URL urlJar = new URL(ofZipUrlStr + name);
               return Optional.of(urlJar);
            } catch (IOException var4) {
               LOGGER.error(var4);
               return Optional.empty();
            }
         }
      }
   }

   public List transformers() {
      LOGGER.info("OptiFineTransformationService.transformers");
      List list = new ArrayList();
      if (transformer != null) {
         list.add(transformer);
      }

      return list;
   }

   public static OptiFineTransformer getTransformer() {
      return transformer;
   }

   public static ZipFile getOfZipFile() {
      return ofZipFile;
   }
}
