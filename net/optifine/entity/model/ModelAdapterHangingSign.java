package net.optifine.entity.model;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterHangingSign extends ModelAdapter {
   private static Map mapParts = makeMapParts();

   public ModelAdapterHangingSign() {
      super(BlockEntityType.f_244529_, "hanging_sign", 0.0F);
   }

   public Model makeModel() {
      return new HangingSignRenderer.HangingSignModel(bakeModelLayer(ModelLayers.m_247439_(WoodType.f_61830_)));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof HangingSignRenderer.HangingSignModel modelHangingSign)) {
         return null;
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelHangingSign.f_244554_.getChildModelDeep(name);
      } else {
         return null;
      }
   }

   public String[] getModelRendererNames() {
      String[] names = (String[])mapParts.keySet().toArray(new String[0]);
      return names;
   }

   private static Map makeMapParts() {
      Map map = new LinkedHashMap();
      map.put("board", "board");
      map.put("plank", "plank");
      map.put("chains", "normalChains");
      map.put("chain_left1", "chainL1");
      map.put("chain_left2", "chainL2");
      map.put("chain_right1", "chainR1");
      map.put("chain_right2", "chainR2");
      map.put("chains_v", "vChains");
      return map;
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.f_244529_, index, () -> {
         return new HangingSignRenderer(dispatcher.getContext());
      });
      if (!(renderer instanceof HangingSignRenderer)) {
         return null;
      } else if (!Reflector.TileEntityHangingSignRenderer_hangingSignModels.exists()) {
         Config.warn("Field not found: TileEntityHangingSignRenderer.hangingSignModels");
         return null;
      } else {
         Map hangingSignModels = (Map)Reflector.getFieldValue(renderer, Reflector.TileEntityHangingSignRenderer_hangingSignModels);
         if (hangingSignModels == null) {
            Config.warn("Field not found: TileEntityHangingSignRenderer.hangingSignModels");
            return null;
         } else {
            if (hangingSignModels instanceof ImmutableMap) {
               hangingSignModels = new HashMap((Map)hangingSignModels);
               Reflector.TileEntityHangingSignRenderer_hangingSignModels.setValue(renderer, hangingSignModels);
            }

            Collection types = new HashSet(((Map)hangingSignModels).keySet());
            Iterator var9 = types.iterator();

            while(var9.hasNext()) {
               WoodType type = (WoodType)var9.next();
               ((Map)hangingSignModels).put(type, (HangingSignRenderer.HangingSignModel)modelBase);
            }

            return renderer;
         }
      }
   }
}
