package optifine.json;

public class Yytoken {
   public static int TYPE_VALUE;
   public static int TYPE_LEFT_BRACE;
   public static int TYPE_RIGHT_BRACE;
   public static int TYPE_LEFT_SQUARE;
   public static int TYPE_RIGHT_SQUARE;
   public static int TYPE_COMMA;
   public static int TYPE_COLON;
   public static int TYPE_EOF;
   public int type = 0;
   public Object value = null;

   public Yytoken(int type, Object value) {
      this.type = type;
      this.value = value;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      switch (this.type) {
         case -1:
            sb.append("END OF FILE");
            break;
         case 0:
            sb.append("VALUE(").append(this.value).append(")");
            break;
         case 1:
            sb.append("LEFT BRACE({)");
            break;
         case 2:
            sb.append("RIGHT BRACE(})");
            break;
         case 3:
            sb.append("LEFT SQUARE([)");
            break;
         case 4:
            sb.append("RIGHT SQUARE(])");
            break;
         case 5:
            sb.append("COMMA(,)");
            break;
         case 6:
            sb.append("COLON(:)");
      }

      return sb.toString();
   }
}
