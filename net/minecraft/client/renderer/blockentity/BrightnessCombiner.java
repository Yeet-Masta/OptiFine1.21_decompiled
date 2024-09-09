package net.minecraft.client.renderer.blockentity;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.world.level.block.DoubleBlockCombiner.Combiner;
import net.optifine.EmissiveTextures;

public class BrightnessCombiner<S extends net.minecraft.world.level.block.entity.BlockEntity> implements Combiner<S, Int2IntFunction> {
   public Int2IntFunction m_6959_(S blockEntity1, S blockEntity2) {
      return valIn -> {
         if (EmissiveTextures.isRenderEmissive()) {
            return net.minecraft.client.renderer.LightTexture.MAX_BRIGHTNESS;
         } else {
            int i = net.minecraft.client.renderer.LevelRenderer.m_109541_(blockEntity1.m_58904_(), blockEntity1.m_58899_());
            int j = net.minecraft.client.renderer.LevelRenderer.m_109541_(blockEntity2.m_58904_(), blockEntity2.m_58899_());
            int k = net.minecraft.client.renderer.LightTexture.m_109883_(i);
            int l = net.minecraft.client.renderer.LightTexture.m_109883_(j);
            int i1 = net.minecraft.client.renderer.LightTexture.m_109894_(i);
            int j1 = net.minecraft.client.renderer.LightTexture.m_109894_(j);
            return net.minecraft.client.renderer.LightTexture.m_109885_(Math.max(k, l), Math.max(i1, j1));
         }
      };
   }

   public Int2IntFunction m_7693_(S blockEntityIn) {
      return valIn -> valIn;
   }

   public Int2IntFunction m_6502_() {
      return valIn -> valIn;
   }
}
