package net.optifine.entity.model.anim;

import net.minecraft.client.model.geom.ModelPart;
import net.optifine.Config;
import net.optifine.expr.ExpressionType;

public enum ModelVariableType {
   POS_X("tx"),
   POS_Y("ty"),
   POS_Z("tz"),
   ANGLE_X("rx"),
   ANGLE_Y("ry"),
   ANGLE_Z("rz"),
   SCALE_X("sx"),
   SCALE_Y("sy"),
   SCALE_Z("sz"),
   VISIBLE("visible", ExpressionType.BOOL),
   VISIBLE_BOXES("visible_boxes", ExpressionType.BOOL);

   private String name;
   private ExpressionType type;
   public static ModelVariableType[] VALUES = values();

   private ModelVariableType(String name) {
      this(name, ExpressionType.FLOAT);
   }

   private ModelVariableType(String name, ExpressionType type) {
      this.name = name;
      this.type = type;
   }

   public String getName() {
      return this.name;
   }

   public ExpressionType getType() {
      return this.type;
   }

   public float getFloat(ModelPart mr) {
      switch (this) {
         case POS_X:
            return mr.f_104200_;
         case POS_Y:
            return mr.f_104201_;
         case POS_Z:
            return mr.f_104202_;
         case ANGLE_X:
            return mr.f_104203_;
         case ANGLE_Y:
            return mr.f_104204_;
         case ANGLE_Z:
            return mr.f_104205_;
         case SCALE_X:
            return mr.f_233553_;
         case SCALE_Y:
            return mr.f_233554_;
         case SCALE_Z:
            return mr.f_233555_;
         default:
            Config.warn("GetFloat not supported for: " + this);
            return 0.0F;
      }
   }

   public void setFloat(ModelPart mr, float val) {
      switch (this) {
         case POS_X:
            mr.f_104200_ = val;
            return;
         case POS_Y:
            mr.f_104201_ = val;
            return;
         case POS_Z:
            mr.f_104202_ = val;
            return;
         case ANGLE_X:
            mr.f_104203_ = val;
            return;
         case ANGLE_Y:
            mr.f_104204_ = val;
            return;
         case ANGLE_Z:
            mr.f_104205_ = val;
            return;
         case SCALE_X:
            mr.f_233553_ = val;
            return;
         case SCALE_Y:
            mr.f_233554_ = val;
            return;
         case SCALE_Z:
            mr.f_233555_ = val;
            return;
         default:
            Config.warn("SetFloat not supported for: " + this);
      }
   }

   public boolean getBool(ModelPart mr) {
      switch (this) {
         case VISIBLE:
            return mr.f_104207_;
         case VISIBLE_BOXES:
            return !mr.f_233556_;
         default:
            Config.warn("GetBool not supported for: " + this);
            return false;
      }
   }

   public void setBool(ModelPart mr, boolean val) {
      switch (this) {
         case VISIBLE:
            mr.f_104207_ = val;
            return;
         case VISIBLE_BOXES:
            mr.f_233556_ = !val;
            return;
         default:
            Config.warn("SetBool not supported for: " + this);
      }
   }

   public IModelVariable makeModelVariable(String name, ModelPart mr) {
      if (this.type == ExpressionType.FLOAT) {
         return new ModelVariableFloat(name, mr, this);
      } else if (this.type == ExpressionType.BOOL) {
         return new ModelVariableBool(name, mr, this);
      } else {
         Config.warn("Unknown model variable type: " + this.type);
         return null;
      }
   }

   public static ModelVariableType m_82160_(String str) {
      for (int i = 0; i < VALUES.length; i++) {
         ModelVariableType var = VALUES[i];
         if (var.getName().equals(str)) {
            return var;
         }
      }

      return null;
   }
}
