package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.util.Either;

public abstract class ModelAdapter {
   private Either<EntityType, BlockEntityType> type;
   private String name;
   private float shadowSize;
   private String[] aliases;

   public ModelAdapter(EntityType entityType, String name, float shadowSize) {
      this(Either.makeLeft(entityType), name, shadowSize, null);
   }

   public ModelAdapter(EntityType entityType, String name, float shadowSize, String[] aliases) {
      this(Either.makeLeft(entityType), name, shadowSize, aliases);
   }

   public ModelAdapter(BlockEntityType tileEntityType, String name, float shadowSize) {
      this(Either.makeRight(tileEntityType), name, shadowSize, null);
   }

   public ModelAdapter(BlockEntityType tileEntityType, String name, float shadowSize, String[] aliases) {
      this(Either.makeRight(tileEntityType), name, shadowSize, aliases);
   }

   public ModelAdapter(Either<EntityType, BlockEntityType> type, String name, float shadowSize, String[] aliases) {
      this.type = type;
      this.name = name;
      this.shadowSize = shadowSize;
      this.aliases = aliases;
   }

   public Either<EntityType, BlockEntityType> getType() {
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

   public abstract net.minecraft.client.model.Model makeModel();

   public abstract net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model var1, String var2);

   public abstract String[] getModelRendererNames();

   public abstract IEntityRenderer makeEntityRender(net.minecraft.client.model.Model var1, float var2, RendererCache var3, int var4);

   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      return false;
   }

   public net.minecraft.client.model.geom.ModelPart[] getModelRenderers(net.minecraft.client.model.Model model) {
      String[] names = this.getModelRendererNames();
      List<net.minecraft.client.model.geom.ModelPart> list = new ArrayList();

      for (int i = 0; i < names.length; i++) {
         String name = names[i];
         net.minecraft.client.model.geom.ModelPart mr = this.getModelRenderer(model, name);
         if (mr != null) {
            list.add(mr);
         }
      }

      return (net.minecraft.client.model.geom.ModelPart[])list.toArray(new net.minecraft.client.model.geom.ModelPart[list.size()]);
   }

   public static net.minecraft.client.model.geom.ModelPart bakeModelLayer(ModelLayerLocation loc) {
      return Minecraft.m_91087_().m_91290_().getContext().m_174023_(loc);
   }
}
