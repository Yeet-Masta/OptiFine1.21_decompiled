package net.optifine;

import net.minecraft.src.C_1992_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.config.ConnectedParser;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.RendererCache;

public interface RandomEntityContext<T> {
   String getName();

   String[] getResourceKeys();

   String getResourceName();

   T makeResource(C_5265_ var1, int var2);

   default String getResourceNameCapital() {
      return this.getResourceName().substring(0, 1).toUpperCase() + this.getResourceName().substring(1);
   }

   default String getResourceNamePlural() {
      return this.getResourceName() + "s";
   }

   default ConnectedParser getConnectedParser() {
      return new ConnectedParser(this.getName());
   }

   public static class Models implements RandomEntityContext<IEntityRenderer> {
      private RendererCache rendererCache = new RendererCache();

      @Override
      public String getName() {
         return "CustomEntityModels";
      }

      @Override
      public String[] getResourceKeys() {
         return new String[]{"models"};
      }

      @Override
      public String getResourceName() {
         return "model";
      }

      public IEntityRenderer makeResource(C_5265_ locBase, int index) {
         C_5265_ loc = index <= 1 ? locBase : RandomEntities.getLocationIndexed(locBase, index);
         if (loc == null) {
            Config.warn("Invalid path: " + locBase.m_135815_());
            return null;
         } else {
            IEntityRenderer renderer = CustomEntityModels.parseEntityRender(loc, this.rendererCache, index);
            if (renderer == null) {
               Config.warn("Model not found: " + loc.m_135815_());
               return null;
            } else {
               if (renderer instanceof C_4331_) {
                  this.rendererCache.put((C_513_)renderer.getType().getLeft().get(), index, (C_4331_)renderer);
               } else if (renderer instanceof C_4244_) {
                  this.rendererCache.put((C_1992_)renderer.getType().getRight().get(), index, (C_4244_)renderer);
               }

               return renderer;
            }
         }
      }

      public RendererCache getRendererCache() {
         return this.rendererCache;
      }
   }

   public static class Textures implements RandomEntityContext<C_5265_> {
      private boolean legacy;

      public Textures(boolean legacy) {
         this.legacy = legacy;
      }

      @Override
      public String getName() {
         return "RandomEntities";
      }

      @Override
      public String[] getResourceKeys() {
         return new String[]{"textures", "skins"};
      }

      @Override
      public String getResourceName() {
         return "texture";
      }

      public C_5265_ makeResource(C_5265_ locBase, int index) {
         if (index <= 1) {
            return locBase;
         } else {
            C_5265_ locOf = RandomEntities.getLocationRandom(locBase, this.legacy);
            if (locOf == null) {
               Config.warn("Invalid path: " + locBase.m_135815_());
               return null;
            } else {
               C_5265_ locNew = RandomEntities.getLocationIndexed(locOf, index);
               if (locNew == null) {
                  Config.warn("Invalid path: " + locBase.m_135815_());
                  return null;
               } else if (!Config.hasResource(locNew)) {
                  Config.warn("Texture not found: " + locNew.m_135815_());
                  return null;
               } else {
                  return locNew;
               }
            }
         }
      }

      public boolean isLegacy() {
         return this.legacy;
      }
   }
}
