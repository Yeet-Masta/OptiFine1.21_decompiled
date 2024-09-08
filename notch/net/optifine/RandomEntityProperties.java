package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import net.minecraft.src.C_5265_;
import net.optifine.config.ConnectedParser;
import net.optifine.util.PropertiesOrdered;

public class RandomEntityProperties<T> {
   private String name = null;
   private String basePath = null;
   private RandomEntityContext<T> context;
   private T[] resources = null;
   private RandomEntityRule<T>[] rules = null;
   private int matchingRuleIndex = -1;

   public RandomEntityProperties(String path, C_5265_ baseLoc, int[] variants, RandomEntityContext<T> context) {
      ConnectedParser cp = new ConnectedParser(context.getName());
      this.name = cp.parseName(path);
      this.basePath = cp.parseBasePath(path);
      this.context = context;
      this.resources = (T[])(new Object[variants.length]);

      for (int i = 0; i < variants.length; i++) {
         int index = variants[i];
         this.resources[i] = (T)context.makeResource(baseLoc, index);
      }
   }

   public RandomEntityProperties(Properties props, String path, C_5265_ baseResLoc, RandomEntityContext<T> context) {
      ConnectedParser cp = context.getConnectedParser();
      this.name = cp.parseName(path);
      this.basePath = cp.parseBasePath(path);
      this.context = context;
      this.rules = this.parseRules(props, path, baseResLoc);
   }

   public String getName() {
      return this.name;
   }

   public String getBasePath() {
      return this.basePath;
   }

   public T[] getResources() {
      return this.resources;
   }

   public List<T> getAllResources() {
      List<T> list = new ArrayList();
      if (this.resources != null) {
         list.addAll(Arrays.asList(this.resources));
      }

      if (this.rules != null) {
         for (int i = 0; i < this.rules.length; i++) {
            RandomEntityRule<T> rule = this.rules[i];
            if (rule.getResources() != null) {
               list.addAll(Arrays.asList(rule.getResources()));
            }
         }
      }

      return list;
   }

   public T getResource(IRandomEntity randomEntity, T resDef) {
      this.matchingRuleIndex = 0;
      if (this.rules != null) {
         for (int i = 0; i < this.rules.length; i++) {
            RandomEntityRule<T> rule = this.rules[i];
            if (rule.matches(randomEntity)) {
               this.matchingRuleIndex = rule.getIndex();
               return rule.getResource(randomEntity.getId(), resDef);
            }
         }
      }

      if (this.resources != null) {
         int randomId = randomEntity.getId();
         int index = randomId % this.resources.length;
         return this.resources[index];
      } else {
         return resDef;
      }
   }

   private RandomEntityRule<T>[] parseRules(Properties props, String pathProps, C_5265_ baseResLoc) {
      List list = new ArrayList();
      int maxIndex = 10;

      for (int i = 0; i < maxIndex; i++) {
         int index = i + 1;
         String valTextures = null;
         String[] keys = this.context.getResourceKeys();

         for (String key : keys) {
            valTextures = props.getProperty(key + "." + index);
            if (valTextures != null) {
               break;
            }
         }

         if (valTextures != null) {
            RandomEntityRule<T> rule = new RandomEntityRule(props, pathProps, baseResLoc, index, valTextures, this.context);
            list.add(rule);
            maxIndex = index + 10;
         }
      }

      return (RandomEntityRule<T>[])list.toArray(new RandomEntityRule[list.size()]);
   }

   public boolean isValid(String path) {
      String resNamePlural = this.context.getResourceNamePlural();
      if (this.resources == null && this.rules == null) {
         Config.warn("No " + resNamePlural + " specified: " + path);
         return false;
      } else {
         if (this.rules != null) {
            for (int i = 0; i < this.rules.length; i++) {
               RandomEntityRule rule = this.rules[i];
               if (!rule.isValid(path)) {
                  return false;
               }
            }
         }

         if (this.resources != null) {
            for (int ix = 0; ix < this.resources.length; ix++) {
               T res = this.resources[ix];
               if (res == null) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public boolean isDefault() {
      return this.rules != null ? false : this.resources == null;
   }

   public int getMatchingRuleIndex() {
      return this.matchingRuleIndex;
   }

   public static RandomEntityProperties parse(C_5265_ propLoc, C_5265_ resLoc, RandomEntityContext context) {
      String contextName = context.getName();

      try {
         String path = propLoc.m_135815_();
         Config.dbg(contextName + ": " + resLoc.m_135815_() + ", properties: " + path);
         InputStream in = Config.getResourceStream(propLoc);
         if (in == null) {
            Config.warn(contextName + ": Properties not found: " + path);
            return null;
         } else {
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            RandomEntityProperties rep = new RandomEntityProperties(props, path, resLoc, context);
            return !rep.isValid(path) ? null : rep;
         }
      } catch (FileNotFoundException var8) {
         Config.warn(contextName + ": File not found: " + propLoc.m_135815_());
         return null;
      } catch (IOException var9) {
         var9.printStackTrace();
         return null;
      }
   }

   public String toString() {
      return this.name + ", path: " + this.basePath;
   }
}
