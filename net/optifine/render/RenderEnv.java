package net.optifine.render;

import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.model.BakedQuadRetextured;
import net.optifine.model.ListQuadsOverlay;

public class RenderEnv {
   private BlockState blockState;
   private BlockPos blockPos;
   private int blockId = -1;
   private int metadata = -1;
   private int breakingAnimation = -1;
   private int smartLeaves = -1;
   private float[] quadBounds;
   private BitSet boundsFlags;
   private ModelBlockRenderer.AmbientOcclusionFace aoFace;
   private BlockPosM colorizerBlockPosM;
   private BlockPos.MutableBlockPos renderMutableBlockPos;
   private boolean[] borderFlags;
   private boolean[] borderFlags2;
   private boolean[] borderFlags3;
   private Direction[] borderDirections;
   private List listQuadsCustomizer;
   private List listQuadsCtmMultipass;
   private BakedQuad[] arrayQuadsCtm1;
   private BakedQuad[] arrayQuadsCtm2;
   private BakedQuad[] arrayQuadsCtm3;
   private BakedQuad[] arrayQuadsCtm4;
   private SectionCompiler sectionCompiler;
   private Map bufferBuilderMap;
   private SectionBufferBuilderPack sectionBufferBuilderPack;
   private ListQuadsOverlay[] listsQuadsOverlay;
   private boolean overlaysRendered;
   private Long2ByteLinkedOpenHashMap renderSideMap;
   private static final int UNKNOWN = -1;
   private static final int FALSE = 0;
   private static final int TRUE = 1;

   public RenderEnv(BlockState blockState, BlockPos blockPos) {
      this.quadBounds = new float[Direction.f_122346_.length * 2];
      this.boundsFlags = new BitSet(3);
      this.aoFace = new ModelBlockRenderer.AmbientOcclusionFace();
      this.colorizerBlockPosM = null;
      this.renderMutableBlockPos = null;
      this.borderFlags = null;
      this.borderFlags2 = null;
      this.borderFlags3 = null;
      this.borderDirections = null;
      this.listQuadsCustomizer = new ArrayList();
      this.listQuadsCtmMultipass = new ArrayList();
      this.arrayQuadsCtm1 = new BakedQuad[1];
      this.arrayQuadsCtm2 = new BakedQuad[2];
      this.arrayQuadsCtm3 = new BakedQuad[3];
      this.arrayQuadsCtm4 = new BakedQuad[4];
      this.listsQuadsOverlay = new ListQuadsOverlay[RenderType.CHUNK_RENDER_TYPES.length];
      this.overlaysRendered = false;
      this.renderSideMap = new Long2ByteLinkedOpenHashMap();
      this.blockState = blockState;
      this.blockPos = blockPos;
   }

   public void reset(BlockState blockStateIn, BlockPos blockPosIn) {
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

   public ModelBlockRenderer.AmbientOcclusionFace getAoFace() {
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

   public boolean isBreakingAnimation(BakedQuad quad) {
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

   public BlockState getBlockState() {
      return this.blockState;
   }

   public BlockPosM getColorizerBlockPosM() {
      if (this.colorizerBlockPosM == null) {
         this.colorizerBlockPosM = new BlockPosM(0, 0, 0);
      }

      return this.colorizerBlockPosM;
   }

   public BlockPos.MutableBlockPos getRenderMutableBlockPos() {
      if (this.renderMutableBlockPos == null) {
         this.renderMutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);
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

   public Direction[] getBorderDirections() {
      if (this.borderDirections == null) {
         this.borderDirections = new Direction[4];
      }

      return this.borderDirections;
   }

   public Direction[] getBorderDirections(Direction dir0, Direction dir1, Direction dir2, Direction dir3) {
      Direction[] dirs = this.getBorderDirections();
      dirs[0] = dir0;
      dirs[1] = dir1;
      dirs[2] = dir2;
      dirs[3] = dir3;
      return dirs;
   }

   public boolean isSmartLeaves() {
      if (this.smartLeaves == -1) {
         if (Config.isTreesSmart() && this.blockState.m_60734_() instanceof LeavesBlock) {
            this.smartLeaves = 1;
         } else {
            this.smartLeaves = 0;
         }
      }

      return this.smartLeaves == 1;
   }

   public List getListQuadsCustomizer() {
      return this.listQuadsCustomizer;
   }

   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad) {
      this.arrayQuadsCtm1[0] = quad;
      return this.arrayQuadsCtm1;
   }

   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1) {
      this.arrayQuadsCtm2[0] = quad0;
      this.arrayQuadsCtm2[1] = quad1;
      return this.arrayQuadsCtm2;
   }

   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1, BakedQuad quad2) {
      this.arrayQuadsCtm3[0] = quad0;
      this.arrayQuadsCtm3[1] = quad1;
      this.arrayQuadsCtm3[2] = quad2;
      return this.arrayQuadsCtm3;
   }

   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1, BakedQuad quad2, BakedQuad quad3) {
      this.arrayQuadsCtm4[0] = quad0;
      this.arrayQuadsCtm4[1] = quad1;
      this.arrayQuadsCtm4[2] = quad2;
      this.arrayQuadsCtm4[3] = quad3;
      return this.arrayQuadsCtm4;
   }

   public List getListQuadsCtmMultipass(BakedQuad[] quads) {
      this.listQuadsCtmMultipass.clear();
      if (quads != null) {
         for(int i = 0; i < quads.length; ++i) {
            BakedQuad quad = quads[i];
            this.listQuadsCtmMultipass.add(quad);
         }
      }

      return this.listQuadsCtmMultipass;
   }

   public void setCompileParams(SectionCompiler sectionCompiler, Map bufferBuilderMap, SectionBufferBuilderPack sectionBufferBuilderPack) {
      this.sectionCompiler = sectionCompiler;
      this.bufferBuilderMap = bufferBuilderMap;
      this.sectionBufferBuilderPack = sectionBufferBuilderPack;
   }

   public SectionCompiler getSectionCompiler() {
      return this.sectionCompiler;
   }

   public Map getBufferBuilderMap() {
      return this.bufferBuilderMap;
   }

   public SectionBufferBuilderPack getSectionBufferBuilderPack() {
      return this.sectionBufferBuilderPack;
   }

   public ListQuadsOverlay getListQuadsOverlay(RenderType layer) {
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
