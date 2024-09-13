package net.optifine;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.config.ConnectedParser;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.RendererCache;

public interface RandomEntityContext<T> {
   String getName();

   String[] getResourceKeys();

   String getResourceName();

   T makeResource(ResourceLocation var1, int var2);

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

      public IEntityRenderer makeResource(ResourceLocation locBase, int index) {
         ResourceLocation loc = index <= 1 ? locBase : RandomEntities.getLocationIndexed(locBase, index);
         if (loc == null) {
            Config.warn("Invalid path: " + locBase.m_135815_());
            return null;
         } else {
            IEntityRenderer renderer = CustomEntityModels.parseEntityRender(loc, this.rendererCache, index);
            if (renderer == null) {
               Config.warn("Model not found: " + loc.m_135815_());
               return null;
            } else {
               if (renderer instanceof EntityRenderer) {
                  this.rendererCache.put((EntityType)renderer.getType().getLeft().get(), index, (EntityRenderer)renderer);
               } else if (renderer instanceof BlockEntityRenderer) {
                  this.rendererCache.put((BlockEntityType)renderer.getType().getRight().get(), index, (BlockEntityRenderer)renderer);
               }

               return renderer;
            }
         }
      }

      public RendererCache getRendererCache() {
         return this.rendererCache;
      }
   }

   public static class Textures implements RandomEntityContext<ResourceLocation> {
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

      public ResourceLocation makeResource(ResourceLocation locBase, int index) {
         if (index <= 1) {
            return locBase;
         } else {
            ResourceLocation locOf = RandomEntities.getLocationRandom(locBase, this.legacy);
            if (locOf == null) {
               Config.warn("Invalid path: " + locBase.m_135815_());
               return null;
            } else {
               ResourceLocation locNew = RandomEntities.getLocationIndexed(locOf, index);
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
