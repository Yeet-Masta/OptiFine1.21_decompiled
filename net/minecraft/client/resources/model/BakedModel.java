package net.minecraft.client.resources.model;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.IForgeBakedModel;

public interface BakedModel extends IForgeBakedModel {
   List m_213637_(@Nullable BlockState var1, @Nullable Direction var2, RandomSource var3);

   boolean m_7541_();

   boolean m_7539_();

   boolean m_7547_();

   boolean m_7521_();

   TextureAtlasSprite m_6160_();

   default ItemTransforms m_7442_() {
      return ItemTransforms.f_111786_;
   }

   ItemOverrides m_7343_();
}
