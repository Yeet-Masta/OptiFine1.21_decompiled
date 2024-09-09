package net.optifine.shaders.uniform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.optifine.expr.ConstantFloat;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;
import net.optifine.shaders.SMCLog;
import net.optifine.util.BiomeCategory;
import net.optifine.util.BiomeUtils;

public class ShaderExpressionResolver implements IExpressionResolver {
   private Map mapExpressions = new HashMap();

   public ShaderExpressionResolver(Map map) {
      this.registerExpressions();
      Set keys = map.keySet();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         String name = (String)it.next();
         IExpression expr = (IExpression)map.get(name);
         this.registerExpression(name, expr);
      }

   }

   private void registerExpressions() {
      ShaderParameterFloat[] spfs = ShaderParameterFloat.values();

      for(int i = 0; i < spfs.length; ++i) {
         ShaderParameterFloat spf = spfs[i];
         this.addParameterFloat(this.mapExpressions, spf);
      }

      ShaderParameterBool[] spbs = ShaderParameterBool.values();

      for(int i = 0; i < spbs.length; ++i) {
         ShaderParameterBool spb = spbs[i];
         this.mapExpressions.put(spb.getName(), spb);
      }

      Set biomeLocations = BiomeUtils.getLocations();
      Iterator var14 = biomeLocations.iterator();

      while(var14.hasNext()) {
         ResourceLocation loc = (ResourceLocation)var14.next();
         String name = loc.m_135815_().trim();
         String var10000 = name.toUpperCase();
         name = "BIOME_" + var10000.replace(' ', '_');
         int id = BiomeUtils.getId(loc);
         IExpression expr = new ConstantFloat((float)id);
         this.registerExpression(name, expr);
      }

      BiomeCategory[] biomeCats = BiomeCategory.values();

      for(int i = 0; i < biomeCats.length; ++i) {
         BiomeCategory bc = biomeCats[i];
         String name = "CAT_" + bc.getName().toUpperCase();
         IExpression expr = new ConstantFloat((float)i);
         this.registerExpression(name, expr);
      }

      Biome.Precipitation[] biomePpts = Precipitation.values();

      for(int i = 0; i < biomePpts.length; ++i) {
         Biome.Precipitation bp = biomePpts[i];
         String name = "PPT_" + bp.name().toUpperCase();
         IExpression expr = new ConstantFloat((float)i);
         this.registerExpression(name, expr);
      }

   }

   private void addParameterFloat(Map map, ShaderParameterFloat spf) {
      String[] indexNames1 = spf.getIndexNames1();
      if (indexNames1 == null) {
         map.put(spf.getName(), new ShaderParameterIndexed(spf));
      } else {
         for(int i1 = 0; i1 < indexNames1.length; ++i1) {
            String indexName1 = indexNames1[i1];
            String[] indexNames2 = spf.getIndexNames2();
            if (indexNames2 == null) {
               map.put(spf.getName() + "." + indexName1, new ShaderParameterIndexed(spf, i1));
            } else {
               for(int i2 = 0; i2 < indexNames2.length; ++i2) {
                  String indexName2 = indexNames2[i2];
                  map.put(spf.getName() + "." + indexName1 + "." + indexName2, new ShaderParameterIndexed(spf, i1, i2));
               }
            }
         }

      }
   }

   public boolean registerExpression(String name, IExpression expr) {
      if (this.mapExpressions.containsKey(name)) {
         SMCLog.warning("Expression already defined: " + name);
         return false;
      } else {
         this.mapExpressions.put(name, expr);
         return true;
      }
   }

   public IExpression getExpression(String name) {
      return (IExpression)this.mapExpressions.get(name);
   }

   public boolean hasExpression(String name) {
      return this.mapExpressions.containsKey(name);
   }
}
