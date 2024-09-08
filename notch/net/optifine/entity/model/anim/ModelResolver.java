package net.optifine.entity.model.anim;

import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.entity.model.CustomModelRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.expr.IExpression;
import net.optifine.util.Either;

public class ModelResolver implements IModelResolver {
   private ModelAdapter modelAdapter;
   private C_3840_ model;
   private CustomModelRenderer[] customModelRenderers;
   private C_3889_ thisModelRenderer;
   private C_3889_ partModelRenderer;
   private IRenderResolver renderResolver;

   public ModelResolver(ModelAdapter modelAdapter, C_3840_ model, CustomModelRenderer[] customModelRenderers) {
      this.modelAdapter = modelAdapter;
      this.model = model;
      this.customModelRenderers = customModelRenderers;
      Either<C_513_, C_1992_> type = modelAdapter.getType();
      if (type != null && type.getRight().isPresent()) {
         this.renderResolver = new RenderResolverTileEntity();
      }

      if (type != null && type.getLeft().isPresent()) {
         this.renderResolver = new RenderResolverEntity();
      }
   }

   @Override
   public IExpression getExpression(String name) {
      IExpression mv = this.getModelVariable(name);
      if (mv != null) {
         return mv;
      } else {
         IExpression param = this.renderResolver.getParameter(name);
         return param != null ? param : null;
      }
   }

   public C_3889_ getModelRenderer(String name) {
      if (name == null) {
         return null;
      } else if (name.indexOf(":") >= 0) {
         String[] parts = Config.tokenize(name, ":");
         C_3889_ mr = this.getModelRenderer(parts[0]);

         for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            C_3889_ mrSub = mr.getChildDeep(part);
            if (mrSub == null) {
               return null;
            }

            mr = mrSub;
         }

         return mr;
      } else if (this.thisModelRenderer != null && name.equals("this")) {
         return this.thisModelRenderer;
      } else if (this.partModelRenderer != null && name.equals("part")) {
         return this.partModelRenderer;
      } else {
         C_3889_ mrPart = this.modelAdapter.getModelRenderer(this.model, name);
         if (mrPart != null) {
            return mrPart;
         } else {
            for (int i = 0; i < this.customModelRenderers.length; i++) {
               CustomModelRenderer cmr = this.customModelRenderers[i];
               C_3889_ mr = cmr.getModelRenderer();
               if (name.equals(mr.getId())) {
                  return mr;
               }

               C_3889_ mrChild = mr.getChildDeep(name);
               if (mrChild != null) {
                  return mrChild;
               }
            }

            return null;
         }
      }
   }

   @Override
   public IModelVariable getModelVariable(String name) {
      String[] parts = Config.tokenize(name, ".");
      if (parts.length != 2) {
         return null;
      } else {
         String modelName = parts[0];
         String varName = parts[1];
         if (modelName.equals("var") && !this.renderResolver.isTileEntity()) {
            return new EntityVariableFloat(name);
         } else if (modelName.equals("varb") && !this.renderResolver.isTileEntity()) {
            return new EntityVariableBool(name);
         } else if (modelName.equals("render")) {
            return !this.renderResolver.isTileEntity() ? RendererVariableFloat.parse(varName) : null;
         } else {
            C_3889_ mr = this.getModelRenderer(modelName);
            if (mr == null) {
               return null;
            } else {
               ModelVariableType varType = ModelVariableType.parse(varName);
               return varType == null ? null : varType.makeModelVariable(name, mr);
            }
         }
      }
   }

   public void setPartModelRenderer(C_3889_ partModelRenderer) {
      this.partModelRenderer = partModelRenderer;
   }

   public void setThisModelRenderer(C_3889_ thisModelRenderer) {
      this.thisModelRenderer = thisModelRenderer;
   }
}
