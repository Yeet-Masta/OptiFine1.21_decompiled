import javax.annotation.Nullable;
import net.minecraft.src.C_178_;
import net.minecraft.src.C_213508_;
import net.minecraft.src.C_240334_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_240334_.C_240333_;

public record GuiMessage(int a, C_4996_ b, @Nullable C_213508_ c, @Nullable C_240334_ d) {
   @Nullable
   public C_240333_ a() {
      return this.d != null ? this.d.f_240355_() : null;
   }

   public int b() {
      return this.a;
   }

   public C_4996_ c() {
      return this.b;
   }

   @Nullable
   public C_213508_ d() {
      return this.c;
   }

   @Nullable
   public C_240334_ e() {
      return this.d;
   }

   public static record a(int a, C_178_ b, @Nullable C_240334_ c, boolean d) {
   }
}
