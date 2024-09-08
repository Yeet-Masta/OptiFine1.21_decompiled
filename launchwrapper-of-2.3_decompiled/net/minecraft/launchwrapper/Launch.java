package net.minecraft.launchwrapper;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.apache.logging.log4j.Level;

public class Launch {
   private static final String DEFAULT_TWEAK = "net.minecraft.launchwrapper.VanillaTweaker";
   public static File minecraftHome;
   public static File assetsDir;
   public static Map<String, Object> blackboard;
   public static LaunchClassLoader classLoader;

   public static void main(String[] args) {
      new Launch().launch(args);
   }

   private Launch() {
      if (this.getClass().getClassLoader() instanceof URLClassLoader) {
         URLClassLoader ucl = (URLClassLoader)this.getClass().getClassLoader();
         classLoader = new LaunchClassLoader(ucl.getURLs());
      } else {
         classLoader = new LaunchClassLoader(this.getURLs());
      }

      blackboard = new HashMap();
      Thread.currentThread().setContextClassLoader(classLoader);
   }

   private URL[] getURLs() {
      String cp = System.getProperty("java.class.path");
      String[] elements = cp.split(File.pathSeparator);
      if (elements.length == 0) {
         elements = new String[]{""};
      }

      URL[] urls = new URL[elements.length];

      for (int i = 0; i < elements.length; i++) {
         try {
            URL url = new File(elements[i]).toURI().toURL();
            urls[i] = url;
         } catch (MalformedURLException var6) {
         }
      }

      return urls;
   }

   private void launch(String[] args) {
      OptionParser parser = new OptionParser();
      parser.allowsUnrecognizedOptions();
      OptionSpec<String> profileOption = parser.accepts("version", "The version we launched with").withRequiredArg();
      OptionSpec<File> gameDirOption = parser.accepts("gameDir", "Alternative game directory").withRequiredArg().ofType(File.class);
      OptionSpec<File> assetsDirOption = parser.accepts("assetsDir", "Assets directory").withRequiredArg().ofType(File.class);
      OptionSpec<String> tweakClassOption = parser.accepts("tweakClass", "Tweak class(es) to load")
         .withRequiredArg()
         .defaultsTo("net.minecraft.launchwrapper.VanillaTweaker", new String[0]);
      OptionSpec<String> nonOption = parser.nonOptions();
      OptionSet options = parser.parse(args);
      minecraftHome = (File)options.valueOf(gameDirOption);
      assetsDir = (File)options.valueOf(assetsDirOption);
      String profileName = (String)options.valueOf(profileOption);
      List<String> tweakClassNames = new ArrayList(options.valuesOf(tweakClassOption));
      List<String> argumentList = new ArrayList();
      blackboard.put("TweakClasses", tweakClassNames);
      blackboard.put("ArgumentList", argumentList);
      Set<String> allTweakerNames = new HashSet();
      List<ITweaker> allTweakers = new ArrayList();

      try {
         List<ITweaker> tweakers = new ArrayList(tweakClassNames.size() + 1);
         blackboard.put("Tweaks", tweakers);
         ITweaker primaryTweaker = null;

         do {
            Iterator<String> it = tweakClassNames.iterator();

            while (it.hasNext()) {
               String tweakName = (String)it.next();
               if (allTweakerNames.contains(tweakName)) {
                  LogWrapper.log(Level.WARN, "Tweak class name %s has already been visited -- skipping", tweakName);
                  it.remove();
               } else {
                  allTweakerNames.add(tweakName);
                  LogWrapper.log(Level.INFO, "Loading tweak class name %s", tweakName);
                  classLoader.addClassLoaderExclusion(tweakName.substring(0, tweakName.lastIndexOf(46)));
                  ITweaker tweaker = (ITweaker)Class.forName(tweakName, true, classLoader).newInstance();
                  tweakers.add(tweaker);
                  it.remove();
                  if (primaryTweaker == null) {
                     LogWrapper.log(Level.INFO, "Using primary tweak class name %s", tweakName);
                     primaryTweaker = tweaker;
                  }
               }
            }

            it = tweakers.iterator();

            while (it.hasNext()) {
               ITweaker tweaker = (ITweaker)it.next();
               LogWrapper.log(Level.INFO, "Calling tweak class %s", tweaker.getClass().getName());
               tweaker.acceptOptions(options.valuesOf(nonOption), minecraftHome, assetsDir, profileName);
               tweaker.injectIntoClassLoader(classLoader);
               allTweakers.add(tweaker);
               it.remove();
            }
         } while (!tweakClassNames.isEmpty());

         for (ITweaker tweaker : allTweakers) {
            argumentList.addAll(Arrays.asList(tweaker.getLaunchArguments()));
         }

         String launchTarget = primaryTweaker.getLaunchTarget();
         Class<?> clazz = Class.forName(launchTarget, false, classLoader);
         Method mainMethod = clazz.getMethod("main", String[].class);
         LogWrapper.info("Launching wrapped minecraft {%s}", launchTarget);
         mainMethod.invoke(null, (Object)argumentList.toArray(new String[argumentList.size()]));
      } catch (Exception var19) {
         LogWrapper.log(Level.ERROR, var19, "Unable to launch");
         System.exit(1);
      }
   }
}
