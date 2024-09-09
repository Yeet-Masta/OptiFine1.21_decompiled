package net.optifine.shaders.uniform;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;
import net.optifine.shaders.SMCLog;
import net.optifine.util.BiomeCategory;
import net.optifine.util.BiomeUtils;

public class ShaderExpressionResolver implements IExpressionResolver {
   private Map<String, IExpression> mapExpressions = new HashMap();

   public ShaderExpressionResolver(Map<String, IExpression> map) {
      this.registerExpressions();

      for (String name : map.keySet()) {
         IExpression expr = (IExpression)map.get(name);
         this.registerExpression(name, expr);
      }
   }

   private void registerExpressions() {
      ShaderParameterFloat[] spfs = ShaderParameterFloat.values();

      for (int i = 0; i < spfs.length; i++) {
         ShaderParameterFloat spf = spfs[i];
         this.addParameterFloat(this.mapExpressions, spf);
      }

      ShaderParameterBool[] spbs = ShaderParameterBool.values();

      for (int i = 0; i < spbs.length; i++) {
         ShaderParameterBool spb = spbs[i];
         this.mapExpressions.put(spb.getName(), spb);
      }

      for (net.minecraft.resources.ResourceLocation loc : BiomeUtils.getLocations()) {
         String name = loc.m_135815_().trim();
         name = "BIOME_" + name.toUpperCase().replace(' ', '_');
         int id = BiomeUtils.getId(loc);
         IExpression expr = new net.optifine.expr.ConstantFloat((float)id);
         this.registerExpression(name, expr);
      }

      BiomeCategory[] biomeCats = BiomeCategory.values();

      for (int i = 0; i < biomeCats.length; i++) {
         BiomeCategory bc = biomeCats[i];
         String name = "CAT_" + bc.getName().toUpperCase();
         IExpression expr = new net.optifine.expr.ConstantFloat((float)i);
         this.registerExpression(name, expr);
      }

      Precipitation[] biomePpts = Precipitation.values();

      for (int i = 0; i < biomePpts.length; i++) {
         Precipitation bp = biomePpts[i];
         String name = "PPT_" + bp.name().toUpperCase();
         IExpression expr = new net.optifine.expr.ConstantFloat((float)i);
         this.registerExpression(name, expr);
      }
   }

   private void addParameterFloat(Map<String, IExpression> map, ShaderParameterFloat spf) {
      String[] indexNames1 = spf.getIndexNames1();
      if (indexNames1 == null) {
         map.put(spf.getName(), new ShaderParameterIndexed(spf));
      } else {
         for (int i1 = 0; i1 < indexNames1.length; i1++) {
            String indexName1 = indexNames1[i1];
            String[] indexNames2 = spf.getIndexNames2();
            if (indexNames2 == null) {
               map.put(spf.getName() + "." + indexName1, new ShaderParameterIndexed(spf, i1));
            } else {
               for (int i2 = 0; i2 < indexNames2.length; i2++) {
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

   @Override
   public IExpression getExpression(String name) {
      return (IExpression)this.mapExpressions.get(name);
   }

   public boolean hasExpression(String name) {
      return this.mapExpressions.containsKey(name);
   }
}
