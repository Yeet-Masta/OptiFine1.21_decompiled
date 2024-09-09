package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.util.Either;

public abstract class ModelAdapter {
   private Either type;
   private String name;
   private float shadowSize;
   private String[] aliases;

   public ModelAdapter(EntityType entityType, String name, float shadowSize) {
      this((Either)Either.makeLeft(entityType), name, shadowSize, (String[])null);
   }

   public ModelAdapter(EntityType entityType, String name, float shadowSize, String[] aliases) {
      this(Either.makeLeft(entityType), name, shadowSize, aliases);
   }

   public ModelAdapter(BlockEntityType tileEntityType, String name, float shadowSize) {
      this((Either)Either.makeRight(tileEntityType), name, shadowSize, (String[])null);
   }

   public ModelAdapter(BlockEntityType tileEntityType, String name, float shadowSize, String[] aliases) {
      this(Either.makeRight(tileEntityType), name, shadowSize, aliases);
   }

   public ModelAdapter(Either type, String name, float shadowSize, String[] aliases) {
      this.type = type;
      this.name = name;
      this.shadowSize = shadowSize;
      this.aliases = aliases;
   }

   public Either getType() {
      return this.type;
   }

   public String getName() {
      return this.name;
   }

   public String[] getAliases() {
      return this.aliases;
   }

   public float getShadowSize() {
      return this.shadowSize;
   }

   public abstract Model makeModel();

   public abstract ModelPart getModelRenderer(Model var1, String var2);

   public abstract String[] getModelRendererNames();

   public abstract IEntityRenderer makeEntityRender(Model var1, float var2, RendererCache var3, int var4);

   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      return false;
   }

   public ModelPart[] getModelRenderers(Model model) {
      String[] names = this.getModelRendererNames();
      List list = new ArrayList();

      for(int i = 0; i < names.length; ++i) {
         String name = names[i];
         ModelPart mr = this.getModelRenderer(model, name);
         if (mr != null) {
            list.add(mr);
         }
      }

      ModelPart[] mrs = (ModelPart[])list.toArray(new ModelPart[list.size()]);
      return mrs;
   }

   public static ModelPart bakeModelLayer(ModelLayerLocation loc) {
      return Minecraft.m_91087_().m_91290_().getContext().m_174023_(loc);
   }
}
