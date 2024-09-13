package net.optifine.model;

import java.lang.invoke.StringConcatFactory;
import net.optifine.Config;

public enum AttachmentType {
   LEFT_HANDHELD_ITEM("left_handheld_item"),
   RIGHT_HANDHELD_ITEM("right_handheld_item"),
   HANDHELD_ITEM("handheld_item"),
   HEAD("head"),
   LEAD("lead");

   private String name;

   private AttachmentType(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return StringConcatFactory.makeConcatWithConstants<"makeConcatWithConstants","\u0001">(this.name);
   }

   public static AttachmentType m_82160_(String str) {
      if (str == null) {
         return null;
      } else {
         for (AttachmentType type : values()) {
            if (Config.equals(type.getName(), str)) {
               return type;
            }
         }

         return null;
      }
   }
}
