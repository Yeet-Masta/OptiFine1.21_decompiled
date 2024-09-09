package net.minecraft.client.resources.model;

import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.IForgeModelBaker;

public interface ModelBaker extends IForgeModelBaker {
   UnbakedModel m_245361_(ResourceLocation var1);

   @Nullable
   BakedModel m_245240_(ResourceLocation var1, ModelState var2);
}
