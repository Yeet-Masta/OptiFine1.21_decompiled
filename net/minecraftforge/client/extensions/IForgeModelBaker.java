package net.minecraftforge.client.extensions;

import java.util.function.Function;
import net.minecraft.src.C_4537_;

public interface IForgeModelBaker {
   BakedModel bake(ResourceLocation var1, C_4537_ var2, Function<Material, TextureAtlasSprite> var3);

   Function<Material, TextureAtlasSprite> getModelTextureGetter();
}
