package net.optifine.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.level.block.Blocks;

public class ListQuadsOverlay {
   private List<net.minecraft.client.renderer.block.model.BakedQuad> listQuads = new ArrayList();
   private List<net.minecraft.world.level.block.state.BlockState> listBlockStates = new ArrayList();
   private List<net.minecraft.client.renderer.block.model.BakedQuad> listQuadsSingle = Arrays.asList();

   public void addQuad(net.minecraft.client.renderer.block.model.BakedQuad quad, net.minecraft.world.level.block.state.BlockState blockState) {
      if (quad != null) {
         this.listQuads.add(quad);
         this.listBlockStates.add(blockState);
      }
   }

   public int size() {
      return this.listQuads.size();
   }

   public net.minecraft.client.renderer.block.model.BakedQuad getQuad(int index) {
      return (net.minecraft.client.renderer.block.model.BakedQuad)this.listQuads.get(index);
   }

   public net.minecraft.world.level.block.state.BlockState getBlockState(int index) {
      return index >= 0 && index < this.listBlockStates.size()
         ? (net.minecraft.world.level.block.state.BlockState)this.listBlockStates.get(index)
         : Blocks.f_50016_.m_49966_();
   }

   public List<net.minecraft.client.renderer.block.model.BakedQuad> getListQuadsSingle(net.minecraft.client.renderer.block.model.BakedQuad quad) {
      this.listQuadsSingle.set(0, quad);
      return this.listQuadsSingle;
   }

   public void clear() {
      this.listQuads.clear();
      this.listBlockStates.clear();
   }
}
