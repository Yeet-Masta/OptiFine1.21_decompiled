package net.optifine.entity.model.anim;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.entity.model.CustomModelRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.expr.IExpression;
import net.optifine.util.Either;

public class ModelResolver implements IModelResolver {
   private ModelAdapter modelAdapter;
   private Model model;
   private CustomModelRenderer[] customModelRenderers;
   private ModelPart thisModelRenderer;
   private ModelPart partModelRenderer;
   private IRenderResolver renderResolver;

   public ModelResolver(ModelAdapter modelAdapter, Model model, CustomModelRenderer[] customModelRenderers) {
      this.modelAdapter = modelAdapter;
      this.model = model;
      this.customModelRenderers = customModelRenderers;
      Either<EntityType, BlockEntityType> type = modelAdapter.getType();
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

   @Override
   public ModelPart getModelRenderer(String name) {
      if (name == null) {
         return null;
      } else if (name.indexOf(":") >= 0) {
         String[] parts = Config.tokenize(name, ":");
         ModelPart mr = this.getModelRenderer(parts[0]);

         for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            ModelPart mrSub = mr.getChildDeep(part);
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
         ModelPart mrPart = this.modelAdapter.getModelRenderer(this.model, name);
         if (mrPart != null) {
            return mrPart;
         } else {
            for (int i = 0; i < this.customModelRenderers.length; i++) {
               CustomModelRenderer cmr = this.customModelRenderers[i];
               ModelPart mr = cmr.getModelRenderer();
               if (name.equals(mr.getId())) {
                  return mr;
               }

               ModelPart mrChild = mr.getChildDeep(name);
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
            return !this.renderResolver.isTileEntity() ? RendererVariableFloat.m_82160_(varName) : null;
         } else {
            ModelPart mr = this.getModelRenderer(modelName);
            if (mr == null) {
               return null;
            } else {
               ModelVariableType varType = ModelVariableType.m_82160_(varName);
               return varType == null ? null : varType.makeModelVariable(name, mr);
            }
         }
      }
   }

   public void setPartModelRenderer(ModelPart partModelRenderer) {
      this.partModelRenderer = partModelRenderer;
   }

   public void setThisModelRenderer(ModelPart thisModelRenderer) {
      this.thisModelRenderer = thisModelRenderer;
   }
}
