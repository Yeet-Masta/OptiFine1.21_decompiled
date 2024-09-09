import javax.annotation.Nullable;
import net.minecraft.src.C_4537_;
import net.minecraft.src.C_4542_;
import net.minecraftforge.client.extensions.IForgeModelBaker;

public interface ModelBaker extends IForgeModelBaker {
   C_4542_ a(ResourceLocation var1);

   @Nullable
   BakedModel a(ResourceLocation var1, C_4537_ var2);
}
