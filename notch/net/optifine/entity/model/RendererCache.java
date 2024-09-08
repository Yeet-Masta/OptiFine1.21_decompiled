package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_513_;

public class RendererCache {
   private Map<String, C_4331_> mapEntityRenderers = new HashMap();
   private Map<String, C_4244_> mapBlockEntityRenderers = new HashMap();

   public C_4331_ get(C_513_ type, int index, Supplier<C_4331_> supplier) {
      String key = C_256712_.f_256780_.m_7981_(type) + ":" + index;
      C_4331_ renderer = (C_4331_)this.mapEntityRenderers.get(key);
      if (renderer == null) {
         renderer = (C_4331_)supplier.get();
         this.mapEntityRenderers.put(key, renderer);
      }

      return renderer;
   }

   public C_4244_ get(C_1992_ type, int index, Supplier<C_4244_> supplier) {
      String key = C_256712_.f_257049_.m_7981_(type) + ":" + index;
      C_4244_ renderer = (C_4244_)this.mapBlockEntityRenderers.get(key);
      if (renderer == null) {
         renderer = (C_4244_)supplier.get();
         this.mapBlockEntityRenderers.put(key, renderer);
      }

      return renderer;
   }

   public void put(C_513_ type, int index, C_4331_ renderer) {
      String key = C_256712_.f_256780_.m_7981_(type) + ":" + index;
      this.mapEntityRenderers.put(key, renderer);
   }

   public void put(C_1992_ type, int index, C_4244_ renderer) {
      String key = C_256712_.f_257049_.m_7981_(type) + ":" + index;
      this.mapBlockEntityRenderers.put(key, renderer);
   }

   public void clear() {
      this.mapEntityRenderers.clear();
      this.mapBlockEntityRenderers.clear();
   }
}
