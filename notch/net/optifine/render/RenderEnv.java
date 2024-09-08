package net.optifine.render;

import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import net.minecraft.src.C_1826_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_290184_;
import net.minecraft.src.C_3173_;
import net.minecraft.src.C_336511_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4186_;
import net.minecraft.src.C_4196_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_4675_.C_4681_;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.model.BakedQuadRetextured;
import net.optifine.model.ListQuadsOverlay;

public class RenderEnv {
   private C_2064_ blockState;
   private C_4675_ blockPos;
   private int blockId = -1;
   private int metadata = -1;
   private int breakingAnimation = -1;
   private int smartLeaves = -1;
   private float[] quadBounds = new float[C_4687_.f_122346_.length * 2];
   private BitSet boundsFlags = new BitSet(3);
   private C_4186_.C_4189_ aoFace = new C_4186_.C_4189_();
   private BlockPosM colorizerBlockPosM = null;
   private C_4681_ renderMutableBlockPos = null;
   private boolean[] borderFlags = null;
   private boolean[] borderFlags2 = null;
   private boolean[] borderFlags3 = null;
   private C_4687_[] borderDirections = null;
   private List<C_4196_> listQuadsCustomizer = new ArrayList();
   private List<C_4196_> listQuadsCtmMultipass = new ArrayList();
   private C_4196_[] arrayQuadsCtm1 = new C_4196_[1];
   private C_4196_[] arrayQuadsCtm2 = new C_4196_[2];
   private C_4196_[] arrayQuadsCtm3 = new C_4196_[3];
   private C_4196_[] arrayQuadsCtm4 = new C_4196_[4];
   private C_336511_ sectionCompiler;
   private Map<C_4168_, C_3173_> bufferBuilderMap;
   private C_290184_ sectionBufferBuilderPack;
   private ListQuadsOverlay[] listsQuadsOverlay = new ListQuadsOverlay[C_4168_.CHUNK_RENDER_TYPES.length];
   private boolean overlaysRendered = false;
   private Long2ByteLinkedOpenHashMap renderSideMap = new Long2ByteLinkedOpenHashMap();
   private static final int UNKNOWN = -1;
   private static final int FALSE = 0;
   private static final int TRUE = 1;

   public RenderEnv(C_2064_ blockState, C_4675_ blockPos) {
      this.blockState = blockState;
      this.blockPos = blockPos;
   }

   public void reset(C_2064_ blockStateIn, C_4675_ blockPosIn) {
      if (this.blockState != blockStateIn || this.blockPos != blockPosIn) {
         this.blockState = blockStateIn;
         this.blockPos = blockPosIn;
         this.blockId = -1;
         this.metadata = -1;
         this.breakingAnimation = -1;
         this.smartLeaves = -1;
         this.boundsFlags.clear();
      }
   }

   public int getBlockId() {
      if (this.blockId < 0) {
         this.blockId = this.blockState.getBlockId();
      }

      return this.blockId;
   }

   public int getMetadata() {
      if (this.metadata < 0) {
         this.metadata = this.blockState.getMetadata();
      }

      return this.metadata;
   }

   public float[] getQuadBounds() {
      return this.quadBounds;
   }

   public BitSet getBoundsFlags() {
      return this.boundsFlags;
   }

   public C_4186_.C_4189_ getAoFace() {
      return this.aoFace;
   }

   public boolean isBreakingAnimation(List listQuads) {
      if (this.breakingAnimation == -1 && listQuads.size() > 0) {
         if (listQuads.get(0) instanceof BakedQuadRetextured) {
            this.breakingAnimation = 1;
         } else {
            this.breakingAnimation = 0;
         }
      }

      return this.breakingAnimation == 1;
   }

   public boolean isBreakingAnimation(C_4196_ quad) {
      if (this.breakingAnimation < 0) {
         if (quad instanceof BakedQuadRetextured) {
            this.breakingAnimation = 1;
         } else {
            this.breakingAnimation = 0;
         }
      }

      return this.breakingAnimation == 1;
   }

   public boolean isBreakingAnimation() {
      return this.breakingAnimation == 1;
   }

   public C_2064_ getBlockState() {
      return this.blockState;
   }

   public BlockPosM getColorizerBlockPosM() {
      if (this.colorizerBlockPosM == null) {
         this.colorizerBlockPosM = new BlockPosM(0, 0, 0);
      }

      return this.colorizerBlockPosM;
   }

   public C_4681_ getRenderMutableBlockPos() {
      if (this.renderMutableBlockPos == null) {
         this.renderMutableBlockPos = new C_4681_(0, 0, 0);
      }

      return this.renderMutableBlockPos;
   }

   public boolean[] getBorderFlags() {
      if (this.borderFlags == null) {
         this.borderFlags = new boolean[4];
      }

      return this.borderFlags;
   }

   public boolean[] getBorderFlags2() {
      if (this.borderFlags2 == null) {
         this.borderFlags2 = new boolean[4];
      }

      return this.borderFlags2;
   }

   public boolean[] getBorderFlags3() {
      if (this.borderFlags3 == null) {
         this.borderFlags3 = new boolean[4];
      }

      return this.borderFlags3;
   }

   public C_4687_[] getBorderDirections() {
      if (this.borderDirections == null) {
         this.borderDirections = new C_4687_[4];
      }

      return this.borderDirections;
   }

   public C_4687_[] getBorderDirections(C_4687_ dir0, C_4687_ dir1, C_4687_ dir2, C_4687_ dir3) {
      C_4687_[] dirs = this.getBorderDirections();
      dirs[0] = dir0;
      dirs[1] = dir1;
      dirs[2] = dir2;
      dirs[3] = dir3;
      return dirs;
   }

   public boolean isSmartLeaves() {
      if (this.smartLeaves == -1) {
         if (Config.isTreesSmart() && this.blockState.m_60734_() instanceof C_1826_) {
            this.smartLeaves = 1;
         } else {
            this.smartLeaves = 0;
         }
      }

      return this.smartLeaves == 1;
   }

   public List<C_4196_> getListQuadsCustomizer() {
      return this.listQuadsCustomizer;
   }

   public C_4196_[] getArrayQuadsCtm(C_4196_ quad) {
      this.arrayQuadsCtm1[0] = quad;
      return this.arrayQuadsCtm1;
   }

   public C_4196_[] getArrayQuadsCtm(C_4196_ quad0, C_4196_ quad1) {
      this.arrayQuadsCtm2[0] = quad0;
      this.arrayQuadsCtm2[1] = quad1;
      return this.arrayQuadsCtm2;
   }

   public C_4196_[] getArrayQuadsCtm(C_4196_ quad0, C_4196_ quad1, C_4196_ quad2) {
      this.arrayQuadsCtm3[0] = quad0;
      this.arrayQuadsCtm3[1] = quad1;
      this.arrayQuadsCtm3[2] = quad2;
      return this.arrayQuadsCtm3;
   }

   public C_4196_[] getArrayQuadsCtm(C_4196_ quad0, C_4196_ quad1, C_4196_ quad2, C_4196_ quad3) {
      this.arrayQuadsCtm4[0] = quad0;
      this.arrayQuadsCtm4[1] = quad1;
      this.arrayQuadsCtm4[2] = quad2;
      this.arrayQuadsCtm4[3] = quad3;
      return this.arrayQuadsCtm4;
   }

   public List<C_4196_> getListQuadsCtmMultipass(C_4196_[] quads) {
      this.listQuadsCtmMultipass.clear();
      if (quads != null) {
         for (int i = 0; i < quads.length; i++) {
            C_4196_ quad = quads[i];
            this.listQuadsCtmMultipass.add(quad);
         }
      }

      return this.listQuadsCtmMultipass;
   }

   public void setCompileParams(C_336511_ sectionCompiler, Map<C_4168_, C_3173_> bufferBuilderMap, C_290184_ sectionBufferBuilderPack) {
      this.sectionCompiler = sectionCompiler;
      this.bufferBuilderMap = bufferBuilderMap;
      this.sectionBufferBuilderPack = sectionBufferBuilderPack;
   }

   public C_336511_ getSectionCompiler() {
      return this.sectionCompiler;
   }

   public Map<C_4168_, C_3173_> getBufferBuilderMap() {
      return this.bufferBuilderMap;
   }

   public C_290184_ getSectionBufferBuilderPack() {
      return this.sectionBufferBuilderPack;
   }

   public ListQuadsOverlay getListQuadsOverlay(C_4168_ layer) {
      ListQuadsOverlay list = this.listsQuadsOverlay[layer.ordinal()];
      if (list == null) {
         list = new ListQuadsOverlay();
         this.listsQuadsOverlay[layer.ordinal()] = list;
      }

      return list;
   }

   public boolean isOverlaysRendered() {
      return this.overlaysRendered;
   }

   public void setOverlaysRendered(boolean overlaysRendered) {
      this.overlaysRendered = overlaysRendered;
   }

   public Long2ByteLinkedOpenHashMap getRenderSideMap() {
      return this.renderSideMap;
   }
}
