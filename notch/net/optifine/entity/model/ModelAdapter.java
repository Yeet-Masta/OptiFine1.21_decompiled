package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.C_141655_;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.util.Either;

public abstract class ModelAdapter {
   private Either<C_513_, C_1992_> type;
   private String name;
   private float shadowSize;
   private String[] aliases;

   public ModelAdapter(C_513_ entityType, String name, float shadowSize) {
      this(Either.makeLeft(entityType), name, shadowSize, null);
   }

   public ModelAdapter(C_513_ entityType, String name, float shadowSize, String[] aliases) {
      this(Either.makeLeft(entityType), name, shadowSize, aliases);
   }

   public ModelAdapter(C_1992_ tileEntityType, String name, float shadowSize) {
      this(Either.makeRight(tileEntityType), name, shadowSize, null);
   }

   public ModelAdapter(C_1992_ tileEntityType, String name, float shadowSize, String[] aliases) {
      this(Either.makeRight(tileEntityType), name, shadowSize, aliases);
   }

   public ModelAdapter(Either<C_513_, C_1992_> type, String name, float shadowSize, String[] aliases) {
      this.type = type;
      this.name = name;
      this.shadowSize = shadowSize;
      this.aliases = aliases;
   }

   public Either<C_513_, C_1992_> getType() {
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

   public abstract C_3840_ makeModel();

   public abstract C_3889_ getModelRenderer(C_3840_ var1, String var2);

   public abstract String[] getModelRendererNames();

   public abstract IEntityRenderer makeEntityRender(C_3840_ var1, float var2, RendererCache var3, int var4);

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      return false;
   }

   public C_3889_[] getModelRenderers(C_3840_ model) {
      String[] names = this.getModelRendererNames();
      List<C_3889_> list = new ArrayList();

      for (int i = 0; i < names.length; i++) {
         String name = names[i];
         C_3889_ mr = this.getModelRenderer(model, name);
         if (mr != null) {
            list.add(mr);
         }
      }

      return (C_3889_[])list.toArray(new C_3889_[list.size()]);
   }

   public static C_3889_ bakeModelLayer(C_141655_ loc) {
      return C_3391_.m_91087_().m_91290_().getContext().m_174023_(loc);
   }
}
