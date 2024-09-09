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
      switch (this.ordinal()) {
         case 0:
            return mr.f_104200_;
         case 1:
            return mr.f_104201_;
         case 2:
            return mr.f_104202_;
         case 3:
            return mr.f_104203_;
         case 4:
            return mr.f_104204_;
         case 5:
            return mr.f_104205_;
         case 6:
            return mr.f_233553_;
         case 7:
            return mr.f_233554_;
         case 8:
            return mr.f_233555_;
         default:
            Config.warn("GetFloat not supported for: " + String.valueOf(this));
            return 0.0F;
      }
   }

   public void setFloat(ModelPart mr, float val) {
      switch (this.ordinal()) {
         case 0:
            mr.f_104200_ = val;
            return;
         case 1:
            mr.f_104201_ = val;
            return;
         case 2:
            mr.f_104202_ = val;
            return;
         case 3:
            mr.f_104203_ = val;
            return;
         case 4:
            mr.f_104204_ = val;
            return;
         case 5:
            mr.f_104205_ = val;
            return;
         case 6:
            mr.f_233553_ = val;
            return;
         case 7:
            mr.f_233554_ = val;
            return;
         case 8:
            mr.f_233555_ = val;
            return;
         default:
            Config.warn("SetFloat not supported for: " + String.valueOf(this));
      }
   }

   public boolean getBool(ModelPart mr) {
      switch (this.ordinal()) {
         case 9:
            return mr.f_104207_;
         case 10:
            return !mr.f_233556_;
         default:
            Config.warn("GetBool not supported for: " + String.valueOf(this));
            return false;
      }
   }

   public void setBool(ModelPart mr, boolean val) {
      switch (this.ordinal()) {
         case 9:
            mr.f_104207_ = val;
            return;
         case 10:
            mr.f_233556_ = !val;
            return;
         default:
            Config.warn("SetBool not supported for: " + String.valueOf(this));
      }
   }

   public IModelVariable makeModelVariable(String name, ModelPart mr) {
      if (this.type == ExpressionType.FLOAT) {
         return new ModelVariableFloat(name, mr, this);
      } else if (this.type == ExpressionType.BOOL) {
         return new ModelVariableBool(name, mr, this);
      } else {
         Config.warn("Unknown model variable type: " + String.valueOf(this.type));
         return null;
      }
   }

   public static ModelVariableType parse(String str) {
      for(int i = 0; i < VALUES.length; ++i) {
         ModelVariableType var = VALUES[i];
         if (var.getName().equals(str)) {
            return var;
         }
      }

      return null;
   }

   // $FF: synthetic method
   private static ModelVariableType[] $values() {
      return new ModelVariableType[]{POS_X, POS_Y, POS_Z, ANGLE_X, ANGLE_Y, ANGLE_Z, SCALE_X, SCALE_Y, SCALE_Z, VISIBLE, VISIBLE_BOXES};
   }
}
