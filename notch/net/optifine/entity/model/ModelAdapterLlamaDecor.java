package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3837_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4359_;
import net.minecraft.src.C_4442_;
import net.minecraft.src.C_4447_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlamaDecor extends ModelAdapterLlama {
   public ModelAdapterLlamaDecor() {
      super(C_513_.f_20466_, "llama_decor", 0.7F);
   }

   protected ModelAdapterLlamaDecor(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3837_(bakeModelLayer(C_141656_.f_171195_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4359_ customRenderer = new C_4359_(renderManager.getContext(), C_141656_.f_171195_);
      customRenderer.g = new C_3837_(bakeModelLayer(C_141656_.f_171195_));
      customRenderer.e = 0.7F;
      C_513_ entityType = (C_513_)this.getType().getLeft().get();
      C_4331_ render = rendererCache.get(entityType, index, () -> customRenderer);
      if (!(render instanceof C_4359_ renderLlama)) {
         Config.warn("Not a RenderLlama: " + render);
         return null;
      } else {
         C_4442_ layer = new C_4442_(renderLlama, renderManager.getContext().m_174027_());
         if (!Reflector.LayerLlamaDecor_model.exists()) {
            Config.warn("Field not found: LayerLlamaDecor.model");
            return null;
         } else {
            Reflector.LayerLlamaDecor_model.setValue(layer, modelBase);
            renderLlama.removeLayers(C_4442_.class);
            renderLlama.a(layer);
            return renderLlama;
         }
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4359_ llamaRenderer = (C_4359_)er;

      for (C_4447_ layer : llamaRenderer.getLayers(C_4442_.class)) {
         C_3840_ model = (C_3840_)Reflector.LayerLlamaDecor_model.getValue(layer);
         if (model != null) {
            model.locationTextureCustom = textureLocation;
         }
      }

      return true;
   }
}
