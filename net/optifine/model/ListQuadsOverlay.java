package net.optifine.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ListQuadsOverlay {
   private List listQuads = new ArrayList();
   private List listBlockStates = new ArrayList();
   private List listQuadsSingle = Arrays.asList();

   public void addQuad(BakedQuad quad, BlockState blockState) {
      if (quad != null) {
         this.listQuads.add(quad);
         this.listBlockStates.add(blockState);
      }
   }

   public int size() {
      return this.listQuads.size();
   }

   public BakedQuad getQuad(int index) {
      return (BakedQuad)this.listQuads.get(index);
   }

   public BlockState getBlockState(int index) {
      return index >= 0 && index < this.listBlockStates.size() ? (BlockState)this.listBlockStates.get(index) : Blocks.f_50016_.m_49966_();
   }

   public List getListQuadsSingle(BakedQuad quad) {
      this.listQuadsSingle.set(0, quad);
      return this.listQuadsSingle;
   }

   public void clear() {
      this.listQuads.clear();
      this.listBlockStates.clear();
   }
}
