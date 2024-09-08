package net.minecraftforge.client.extensions;

import java.util.function.Function;
import net.minecraft.src.C_4486_;
import net.minecraft.src.C_4528_;
import net.minecraft.src.C_4531_;
import net.minecraft.src.C_4537_;
import net.minecraft.src.C_5265_;

public interface IForgeModelBaker {
   C_4528_ bake(C_5265_ var1, C_4537_ var2, Function<C_4531_, C_4486_> var3);

   Function<C_4531_, C_4486_> getModelTextureGetter();
}
