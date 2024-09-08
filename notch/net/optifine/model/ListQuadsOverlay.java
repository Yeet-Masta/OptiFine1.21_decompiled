package net.optifine.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_4196_;

public class ListQuadsOverlay {
   private List<C_4196_> listQuads = new ArrayList();
   private List<C_2064_> listBlockStates = new ArrayList();
   private List<C_4196_> listQuadsSingle = Arrays.asList();

   public void addQuad(C_4196_ quad, C_2064_ blockState) {
      if (quad != null) {
         this.listQuads.add(quad);
         this.listBlockStates.add(blockState);
      }
   }

   public int size() {
      return this.listQuads.size();
   }

   public C_4196_ getQuad(int index) {
      return (C_4196_)this.listQuads.get(index);
   }

   public C_2064_ getBlockState(int index) {
      return index >= 0 && index < this.listBlockStates.size() ? (C_2064_)this.listBlockStates.get(index) : C_1710_.f_50016_.m_49966_();
   }

   public List<C_4196_> getListQuadsSingle(C_4196_ quad) {
      this.listQuadsSingle.set(0, quad);
      return this.listQuadsSingle;
   }

   public void clear() {
      this.listQuads.clear();
      this.listBlockStates.clear();
   }
}
