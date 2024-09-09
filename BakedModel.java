import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_4222_;
import net.minecraftforge.client.extensions.IForgeBakedModel;

public interface BakedModel extends IForgeBakedModel {
   List<BakedQuad> a(@Nullable BlockState var1, @Nullable Direction var2, C_212974_ var3);

   boolean a();

   boolean b();

   boolean c();

   boolean d();

   TextureAtlasSprite e();

   default C_4222_ f() {
      return C_4222_.f_111786_;
   }

   ItemOverrides g();
}
