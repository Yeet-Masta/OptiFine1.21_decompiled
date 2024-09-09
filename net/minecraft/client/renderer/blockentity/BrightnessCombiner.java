package net.minecraft.client.renderer.blockentity;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.optifine.EmissiveTextures;

public class BrightnessCombiner implements DoubleBlockCombiner.Combiner {
   public Int2IntFunction m_6959_(BlockEntity blockEntity1, BlockEntity blockEntity2) {
      return (valIn) -> {
         if (EmissiveTextures.isRenderEmissive()) {
            return LightTexture.MAX_BRIGHTNESS;
         } else {
            int i = LevelRenderer.m_109541_(blockEntity1.m_58904_(), blockEntity1.m_58899_());
            int j = LevelRenderer.m_109541_(blockEntity2.m_58904_(), blockEntity2.m_58899_());
            int k = LightTexture.m_109883_(i);
            int l = LightTexture.m_109883_(j);
            int i1 = LightTexture.m_109894_(i);
            int j1 = LightTexture.m_109894_(j);
            return LightTexture.m_109885_(Math.max(k, l), Math.max(i1, j1));
         }
      };
   }

   public Int2IntFunction m_7693_(BlockEntity blockEntityIn) {
      return (valIn) -> {
         return valIn;
      };
   }

   public Int2IntFunction m_6502_() {
      return (valIn) -> {
         return valIn;
      };
   }
}
