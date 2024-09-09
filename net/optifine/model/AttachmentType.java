package net.optifine.model;

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
      return "" + this.name;
   }

   public static AttachmentType parse(String str) {
      if (str == null) {
         return null;
      } else {
         AttachmentType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            AttachmentType type = var1[var3];
            if (Config.equals(type.getName(), str)) {
               return type;
            }
         }

         return null;
      }
   }

   // $FF: synthetic method
   private static AttachmentType[] $values() {
      return new AttachmentType[]{LEFT_HANDHELD_ITEM, RIGHT_HANDHELD_ITEM, HANDHELD_ITEM, HEAD, LEAD};
   }
}
