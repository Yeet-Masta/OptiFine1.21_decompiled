package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;
import net.optifine.BetterSnow;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.EmissiveTextures;
import net.optifine.model.BlockModelCustomizer;
import net.optifine.model.ListQuadsOverlay;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.render.LightCacheOF;
import net.optifine.render.RenderEnv;
import net.optifine.render.RenderTypes;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.Shaders;
import net.optifine.util.BlockUtils;

public class ModelBlockRenderer {
   private static int f_173403_;
   private static int f_173404_;
   static Direction[] f_173405_ = Direction.values();
   private BlockColors f_110995_;
   private static int f_173406_;
   static ThreadLocal<ModelBlockRenderer.Cache> f_110996_ = ThreadLocal.withInitial(ModelBlockRenderer.Cache::new);
   private static float aoLightValueOpaque = 0.2F;
   private static boolean separateAoLightValue = false;
   private static LightCacheOF LIGHT_CACHE_OF = new LightCacheOF();
   private static RenderType[] OVERLAY_LAYERS = new RenderType[]{RenderTypes.CUTOUT, RenderTypes.CUTOUT_MIPPED, RenderTypes.TRANSLUCENT};
   private boolean forge = Reflector.ForgeHooksClient.exists();

   public ModelBlockRenderer(BlockColors blockColorsIn) {
      this.f_110995_ = blockColorsIn;
   }

   public void m_234379_(
      BlockAndTintGetter worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      BlockPos posIn,
      PoseStack matrixIn,
      VertexConsumer buffer,
      boolean checkSides,
      RandomSource randomIn,
      long rand,
      int combinedOverlayIn
   ) {
      this.tesselateBlock(worldIn, modelIn, stateIn, posIn, matrixIn, buffer, checkSides, randomIn, rand, combinedOverlayIn, ModelData.EMPTY, null);
   }

   public void tesselateBlock(
      BlockAndTintGetter worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      BlockPos posIn,
      PoseStack matrixIn,
      VertexConsumer buffer,
      boolean checkSides,
      RandomSource randomIn,
      long rand,
      int combinedOverlayIn,
      ModelData modelData,
      RenderType renderType
   ) {
      boolean flag = Minecraft.m_91086_() && stateIn.getLightEmission(worldIn, posIn) == 0 && modelIn.useAmbientOcclusion(stateIn, renderType);
      Vec3 vec3 = stateIn.m_60824_(worldIn, posIn);
      matrixIn.m_85837_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);

      try {
         if (Config.isShaders()) {
            SVertexBuilder.pushEntity(stateIn, buffer);
         }

         if (!Config.isAlternateBlocks()) {
            rand = 0L;
         }

         RenderEnv renderEnv = buffer.getRenderEnv(stateIn, posIn);
         modelIn = BlockModelCustomizer.getRenderModel(modelIn, stateIn, renderEnv);
         int prevVertexCount = buffer.getVertexCount();
         if (flag) {
            this.renderModelSmooth(worldIn, modelIn, stateIn, posIn, matrixIn, buffer, checkSides, randomIn, rand, combinedOverlayIn, modelData, renderType);
         } else {
            this.renderModelFlat(worldIn, modelIn, stateIn, posIn, matrixIn, buffer, checkSides, randomIn, rand, combinedOverlayIn, modelData, renderType);
         }

         if (buffer.getVertexCount() != prevVertexCount) {
            this.renderOverlayModels(worldIn, modelIn, stateIn, posIn, matrixIn, buffer, combinedOverlayIn, checkSides, randomIn, rand, renderEnv, flag, vec3);
         }

         if (Config.isShaders()) {
            SVertexBuilder.popEntity(buffer);
         }
      } catch (Throwable var19) {
         CrashReport crashreport = CrashReport.m_127521_(var19, "Tesselating block model");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Block model being tesselated");
         CrashReportCategory.m_178950_(crashreportcategory, worldIn, posIn, stateIn);
         crashreportcategory.m_128159_("Using AO", flag);
         throw new ReportedException(crashreport);
      }
   }

   public void m_234390_(
      BlockAndTintGetter worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      BlockPos posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      boolean checkSides,
      RandomSource randomIn,
      long rand,
      int combinedOverlayIn
   ) {
      this.renderModelSmooth(worldIn, modelIn, stateIn, posIn, matrixStackIn, buffer, checkSides, randomIn, rand, combinedOverlayIn, ModelData.EMPTY, null);
   }

   public void renderModelSmooth(
      BlockAndTintGetter worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      BlockPos posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      boolean checkSides,
      RandomSource randomIn,
      long rand,
      int combinedOverlayIn,
      ModelData modelData,
      RenderType renderType
   ) {
      RenderEnv renderEnv = buffer.getRenderEnv(stateIn, posIn);
      RenderType layer = buffer.getRenderType();

      for (Direction direction : f_173405_) {
         if (!checkSides || BlockUtils.shouldSideBeRendered(stateIn, worldIn, posIn, direction, renderEnv)) {
            randomIn.m_188584_(rand);
            List<BakedQuad> list = this.forge
               ? modelIn.getQuads(stateIn, direction, randomIn, modelData, renderType)
               : modelIn.m_213637_(stateIn, direction, randomIn);
            list = BlockModelCustomizer.getRenderQuads(list, worldIn, stateIn, posIn, direction, layer, rand, renderEnv);
            if (!list.isEmpty()) {
               this.renderQuadsSmooth(worldIn, stateIn, posIn, matrixStackIn, buffer, list, combinedOverlayIn, renderEnv);
            }
         }
      }

      randomIn.m_188584_(rand);
      List<BakedQuad> list1 = this.forge ? modelIn.getQuads(stateIn, null, randomIn, modelData, renderType) : modelIn.m_213637_(stateIn, null, randomIn);
      if (!list1.isEmpty()) {
         list1 = BlockModelCustomizer.getRenderQuads(list1, worldIn, stateIn, posIn, null, layer, rand, renderEnv);
         this.renderQuadsSmooth(worldIn, stateIn, posIn, matrixStackIn, buffer, list1, combinedOverlayIn, renderEnv);
      }
   }

   public void m_234401_(
      BlockAndTintGetter worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      BlockPos posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      boolean checkSides,
      RandomSource randomIn,
      long rand,
      int combinedOverlayIn
   ) {
      this.renderModelFlat(worldIn, modelIn, stateIn, posIn, matrixStackIn, buffer, checkSides, randomIn, rand, combinedOverlayIn, ModelData.EMPTY, null);
   }

   public void renderModelFlat(
      BlockAndTintGetter worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      BlockPos posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      boolean checkSides,
      RandomSource randomIn,
      long rand,
      int combinedOverlayIn,
      ModelData modelData,
      RenderType renderType
   ) {
      RenderEnv renderEnv = buffer.getRenderEnv(stateIn, posIn);
      RenderType layer = buffer.getRenderType();

      for (Direction direction : f_173405_) {
         if (!checkSides || BlockUtils.shouldSideBeRendered(stateIn, worldIn, posIn, direction, renderEnv)) {
            randomIn.m_188584_(rand);
            List<BakedQuad> list = this.forge
               ? modelIn.getQuads(stateIn, direction, randomIn, modelData, renderType)
               : modelIn.m_213637_(stateIn, direction, randomIn);
            list = BlockModelCustomizer.getRenderQuads(list, worldIn, stateIn, posIn, direction, layer, rand, renderEnv);
            if (!list.isEmpty()) {
               MutableBlockPos blockpos$mutableblockpos = renderEnv.getRenderMutableBlockPos();
               blockpos$mutableblockpos.m_122159_(posIn, direction);
               int i = LevelRenderer.m_109537_(worldIn, stateIn, blockpos$mutableblockpos);
               this.renderQuadsFlat(worldIn, stateIn, posIn, i, combinedOverlayIn, false, matrixStackIn, buffer, list, renderEnv);
            }
         }
      }

      randomIn.m_188584_(rand);
      List<BakedQuad> list1 = this.forge ? modelIn.getQuads(stateIn, null, randomIn, modelData, renderType) : modelIn.m_213637_(stateIn, null, randomIn);
      if (!list1.isEmpty()) {
         list1 = BlockModelCustomizer.getRenderQuads(list1, worldIn, stateIn, posIn, null, layer, rand, renderEnv);
         this.renderQuadsFlat(worldIn, stateIn, posIn, -1, combinedOverlayIn, true, matrixStackIn, buffer, list1, renderEnv);
      }
   }

   private void renderQuadsSmooth(
      BlockAndTintGetter blockAccessIn,
      BlockState stateIn,
      BlockPos posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      List<BakedQuad> list,
      int combinedOverlayIn,
      RenderEnv renderEnv
   ) {
      float[] quadBounds = renderEnv.getQuadBounds();
      BitSet bitSet = renderEnv.getBoundsFlags();
      ModelBlockRenderer.AmbientOcclusionFace aoFace = renderEnv.getAoFace();
      int listSize = list.size();

      for (int ix = 0; ix < listSize; ix++) {
         BakedQuad bakedquad = (BakedQuad)list.get(ix);
         this.m_111039_(blockAccessIn, stateIn, posIn, bakedquad.m_111303_(), bakedquad.m_111306_(), quadBounds, bitSet);
         if (bakedquad.hasAmbientOcclusion()
            || !ReflectorForge.calculateFaceWithoutAO(blockAccessIn, stateIn, posIn, bakedquad, bitSet.get(0), aoFace.f_111149_, aoFace.f_111150_)) {
            aoFace.m_111167_(blockAccessIn, stateIn, posIn, bakedquad.m_111306_(), quadBounds, bitSet, bakedquad.m_111307_());
         }

         if (bakedquad.m_173410_().isSpriteEmissive) {
            aoFace.setMaxBlockLight();
         }

         this.renderQuadSmooth(
            blockAccessIn,
            stateIn,
            posIn,
            buffer,
            matrixStackIn.m_85850_(),
            bakedquad,
            aoFace.f_111149_[0],
            aoFace.f_111149_[1],
            aoFace.f_111149_[2],
            aoFace.f_111149_[3],
            aoFace.f_111150_[0],
            aoFace.f_111150_[1],
            aoFace.f_111150_[2],
            aoFace.f_111150_[3],
            combinedOverlayIn,
            renderEnv
         );
      }
   }

   private void renderQuadSmooth(
      BlockAndTintGetter blockAccessIn,
      BlockState stateIn,
      BlockPos posIn,
      VertexConsumer buffer,
      PoseStack.Pose matrixEntry,
      BakedQuad quadIn,
      float colorMul0,
      float colorMul1,
      float colorMul2,
      float colorMul3,
      int brightness0,
      int brightness1,
      int brightness2,
      int brightness3,
      int combinedOverlayIn,
      RenderEnv renderEnv
   ) {
      int colorMultiplier = CustomColors.getColorMultiplier(quadIn, stateIn, blockAccessIn, posIn, renderEnv);
      float f;
      float f1;
      float f2;
      if (!quadIn.m_111304_() && colorMultiplier == -1) {
         f = 1.0F;
         f1 = 1.0F;
         f2 = 1.0F;
      } else {
         int i = colorMultiplier != -1 ? colorMultiplier : this.f_110995_.m_92577_(stateIn, blockAccessIn, posIn, quadIn.m_111305_());
         f = (float)(i >> 16 & 0xFF) / 255.0F;
         f1 = (float)(i >> 8 & 0xFF) / 255.0F;
         f2 = (float)(i & 0xFF) / 255.0F;
      }

      buffer.m_85987_(
         matrixEntry,
         quadIn,
         buffer.getTempFloat4(colorMul0, colorMul1, colorMul2, colorMul3),
         f,
         f1,
         f2,
         1.0F,
         buffer.getTempInt4(brightness0, brightness1, brightness2, brightness3),
         combinedOverlayIn,
         true
      );
   }

   private void m_111039_(
      BlockAndTintGetter blockReaderIn, BlockState stateIn, BlockPos posIn, int[] vertexData, Direction face, @Nullable float[] quadBounds, BitSet boundsFlags
   ) {
      float f = 32.0F;
      float f1 = 32.0F;
      float f2 = 32.0F;
      float f3 = -32.0F;
      float f4 = -32.0F;
      float f5 = -32.0F;
      int step = vertexData.length / 4;

      for (int i = 0; i < 4; i++) {
         float f6 = Float.intBitsToFloat(vertexData[i * step]);
         float f7 = Float.intBitsToFloat(vertexData[i * step + 1]);
         float f8 = Float.intBitsToFloat(vertexData[i * step + 2]);
         f = Math.min(f, f6);
         f1 = Math.min(f1, f7);
         f2 = Math.min(f2, f8);
         f3 = Math.max(f3, f6);
         f4 = Math.max(f4, f7);
         f5 = Math.max(f5, f8);
      }

      if (quadBounds != null) {
         quadBounds[Direction.WEST.m_122411_()] = f;
         quadBounds[Direction.EAST.m_122411_()] = f3;
         quadBounds[Direction.DOWN.m_122411_()] = f1;
         quadBounds[Direction.UP.m_122411_()] = f4;
         quadBounds[Direction.NORTH.m_122411_()] = f2;
         quadBounds[Direction.SOUTH.m_122411_()] = f5;
         int j = f_173405_.length;
         quadBounds[Direction.WEST.m_122411_() + j] = 1.0F - f;
         quadBounds[Direction.EAST.m_122411_() + j] = 1.0F - f3;
         quadBounds[Direction.DOWN.m_122411_() + j] = 1.0F - f1;
         quadBounds[Direction.UP.m_122411_() + j] = 1.0F - f4;
         quadBounds[Direction.NORTH.m_122411_() + j] = 1.0F - f2;
         quadBounds[Direction.SOUTH.m_122411_() + j] = 1.0F - f5;
      }

      float f9 = 1.0E-4F;
      float f10 = 0.9999F;
      switch (<unrepresentable>.$SwitchMap$net$minecraft$core$Direction[face.ordinal()]) {
         case 1:
            boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, f1 == f4 && (f1 < 1.0E-4F || stateIn.m_60838_(blockReaderIn, posIn)));
            break;
         case 2:
            boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, f1 == f4 && (f4 > 0.9999F || stateIn.m_60838_(blockReaderIn, posIn)));
            break;
         case 3:
            boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
            boundsFlags.set(0, f2 == f5 && (f2 < 1.0E-4F || stateIn.m_60838_(blockReaderIn, posIn)));
            break;
         case 4:
            boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
            boundsFlags.set(0, f2 == f5 && (f5 > 0.9999F || stateIn.m_60838_(blockReaderIn, posIn)));
            break;
         case 5:
            boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, f == f3 && (f < 1.0E-4F || stateIn.m_60838_(blockReaderIn, posIn)));
            break;
         case 6:
            boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, f == f3 && (f3 > 0.9999F || stateIn.m_60838_(blockReaderIn, posIn)));
      }
   }

   private void renderQuadsFlat(
      BlockAndTintGetter blockAccessIn,
      BlockState stateIn,
      BlockPos posIn,
      int brightnessIn,
      int combinedOverlayIn,
      boolean ownBrightness,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      List<BakedQuad> list,
      RenderEnv renderEnv
   ) {
      BitSet bitSet = renderEnv.getBoundsFlags();
      int listSize = list.size();

      for (int ix = 0; ix < listSize; ix++) {
         BakedQuad bakedquad = (BakedQuad)list.get(ix);
         if (ownBrightness) {
            this.m_111039_(blockAccessIn, stateIn, posIn, bakedquad.m_111303_(), bakedquad.m_111306_(), null, bitSet);
            BlockPos blockpos = bitSet.get(0) ? posIn.m_121945_(bakedquad.m_111306_()) : posIn;
            brightnessIn = LevelRenderer.m_109537_(blockAccessIn, stateIn, blockpos);
         }

         if (bakedquad.m_173410_().isSpriteEmissive) {
            brightnessIn = LightTexture.MAX_BRIGHTNESS;
         }

         float f = blockAccessIn.m_7717_(bakedquad.m_111306_(), bakedquad.m_111307_());
         this.renderQuadSmooth(
            blockAccessIn,
            stateIn,
            posIn,
            buffer,
            matrixStackIn.m_85850_(),
            bakedquad,
            f,
            f,
            f,
            f,
            brightnessIn,
            brightnessIn,
            brightnessIn,
            brightnessIn,
            combinedOverlayIn,
            renderEnv
         );
      }
   }

   public void m_111067_(
      PoseStack.Pose matrixEntry,
      VertexConsumer buffer,
      @Nullable BlockState state,
      BakedModel modelIn,
      float red,
      float green,
      float blue,
      int combinedLightIn,
      int combinedOverlayIn
   ) {
      this.renderModel(matrixEntry, buffer, state, modelIn, red, green, blue, combinedLightIn, combinedOverlayIn, ModelData.EMPTY, null);
   }

   public void renderModel(
      PoseStack.Pose matrixEntry,
      VertexConsumer buffer,
      @Nullable BlockState state,
      BakedModel modelIn,
      float red,
      float green,
      float blue,
      int combinedLightIn,
      int combinedOverlayIn,
      ModelData modelData,
      RenderType renderType
   ) {
      RandomSource randomsource = RandomSource.m_216327_();
      long i = 42L;

      for (Direction direction : f_173405_) {
         randomsource.m_188584_(42L);
         if (this.forge) {
            m_111058_(
               matrixEntry,
               buffer,
               red,
               green,
               blue,
               modelIn.getQuads(state, direction, randomsource, modelData, renderType),
               combinedLightIn,
               combinedOverlayIn
            );
         } else {
            m_111058_(matrixEntry, buffer, red, green, blue, modelIn.m_213637_(state, direction, randomsource), combinedLightIn, combinedOverlayIn);
         }
      }

      randomsource.m_188584_(42L);
      if (this.forge) {
         m_111058_(
            matrixEntry,
            buffer,
            red,
            green,
            blue,
            modelIn.getQuads(state, (Direction)null, randomsource, modelData, renderType),
            combinedLightIn,
            combinedOverlayIn
         );
      } else {
         m_111058_(matrixEntry, buffer, red, green, blue, modelIn.m_213637_(state, null, randomsource), combinedLightIn, combinedOverlayIn);
      }
   }

   private static void m_111058_(
      PoseStack.Pose matrixEntry,
      VertexConsumer buffer,
      float red,
      float green,
      float blue,
      List<BakedQuad> listQuads,
      int combinedLightIn,
      int combinedOverlayIn
   ) {
      boolean emissive = EmissiveTextures.isActive();

      for (BakedQuad bakedquad : listQuads) {
         if (emissive) {
            bakedquad = EmissiveTextures.getEmissiveQuad(bakedquad);
            if (bakedquad == null) {
               continue;
            }
         }

         float f;
         float f1;
         float f2;
         if (bakedquad.m_111304_()) {
            f = Mth.m_14036_(red, 0.0F, 1.0F);
            f1 = Mth.m_14036_(green, 0.0F, 1.0F);
            f2 = Mth.m_14036_(blue, 0.0F, 1.0F);
         } else {
            f = 1.0F;
            f1 = 1.0F;
            f2 = 1.0F;
         }

         buffer.m_85995_(matrixEntry, bakedquad, f, f1, f2, 1.0F, combinedLightIn, combinedOverlayIn);
      }
   }

   public static void m_111000_() {
      ((ModelBlockRenderer.Cache)f_110996_.get()).m_111220_();
   }

   public static void m_111077_() {
      ((ModelBlockRenderer.Cache)f_110996_.get()).m_111225_();
   }

   public static float fixAoLightValue(float val) {
      return val == 0.2F ? aoLightValueOpaque : val;
   }

   public static void updateAoLightValue() {
      aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
      separateAoLightValue = Config.isShaders() && Shaders.isSeparateAo();
   }

   public static boolean isSeparateAoLightValue() {
      return separateAoLightValue;
   }

   private void renderOverlayModels(
      BlockAndTintGetter worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      BlockPos posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      int combinedOverlayIn,
      boolean checkSides,
      RandomSource random,
      long rand,
      RenderEnv renderEnv,
      boolean smooth,
      Vec3 renderOffset
   ) {
      if (renderEnv.isOverlaysRendered()) {
         renderEnv.setOverlaysRendered(false);

         for (int l = 0; l < OVERLAY_LAYERS.length; l++) {
            RenderType layer = OVERLAY_LAYERS[l];
            ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(layer);
            if (listQuadsOverlay.size() > 0) {
               SectionCompiler sc = renderEnv.getSectionCompiler();
               Map<RenderType, BufferBuilder> bbMap = renderEnv.getBufferBuilderMap();
               SectionBufferBuilderPack sbbp = renderEnv.getSectionBufferBuilderPack();
               if (sc != null && bbMap != null && sbbp != null) {
                  BufferBuilder overlayBuffer = sc.m_340252_(bbMap, sbbp, layer);

                  for (int q = 0; q < listQuadsOverlay.size(); q++) {
                     BakedQuad quad = listQuadsOverlay.getQuad(q);
                     List<BakedQuad> listQuadSingle = listQuadsOverlay.getListQuadsSingle(quad);
                     BlockState quadBlockState = listQuadsOverlay.getBlockState(q);
                     if (quad.getQuadEmissive() != null) {
                        listQuadsOverlay.addQuad(quad.getQuadEmissive(), quadBlockState);
                     }

                     renderEnv.reset(quadBlockState, posIn);
                     if (smooth) {
                        this.renderQuadsSmooth(worldIn, quadBlockState, posIn, matrixStackIn, overlayBuffer, listQuadSingle, combinedOverlayIn, renderEnv);
                     } else {
                        int col = LevelRenderer.m_109537_(worldIn, quadBlockState, posIn.m_121945_(quad.m_111306_()));
                        this.renderQuadsFlat(
                           worldIn, quadBlockState, posIn, col, combinedOverlayIn, false, matrixStackIn, overlayBuffer, listQuadSingle, renderEnv
                        );
                     }
                  }
               }

               listQuadsOverlay.clear();
            }
         }
      }

      if (Config.isBetterSnow() && !renderEnv.isBreakingAnimation() && BetterSnow.shouldRender(worldIn, stateIn, posIn)) {
         BakedModel modelSnow = BetterSnow.getModelSnowLayer();
         BlockState stateSnow = BetterSnow.getStateSnowLayer();
         matrixStackIn.m_85837_(-renderOffset.f_82479_, -renderOffset.f_82480_, -renderOffset.f_82481_);
         this.m_234379_(worldIn, modelSnow, stateSnow, posIn, matrixStackIn, buffer, checkSides, random, rand, combinedOverlayIn);
      }
   }

   protected static enum AdjacencyInfo {
      DOWN(
         new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH},
         0.5F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.SOUTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.SOUTH
         }
      ),
      UP(
         new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH},
         1.0F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.SOUTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.SOUTH
         }
      ),
      NORTH(
         new Direction[]{Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST},
         0.8F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_WEST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_EAST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_EAST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_WEST
         }
      ),
      SOUTH(
         new Direction[]{Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP},
         0.8F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.WEST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.WEST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.EAST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.EAST
         }
      ),
      WEST(
         new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH},
         0.6F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.SOUTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.SOUTH
         }
      ),
      EAST(
         new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH},
         0.6F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.SOUTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.SOUTH
         }
      );

      Direction[] f_111110_;
      boolean f_111111_;
      ModelBlockRenderer.SizeInfo[] f_111112_;
      ModelBlockRenderer.SizeInfo[] f_111113_;
      ModelBlockRenderer.SizeInfo[] f_111114_;
      ModelBlockRenderer.SizeInfo[] f_111115_;
      private static ModelBlockRenderer.AdjacencyInfo[] f_111116_ = Util.m_137469_(new ModelBlockRenderer.AdjacencyInfo[6], infoIn -> {
         infoIn[Direction.DOWN.m_122411_()] = DOWN;
         infoIn[Direction.UP.m_122411_()] = UP;
         infoIn[Direction.NORTH.m_122411_()] = NORTH;
         infoIn[Direction.SOUTH.m_122411_()] = SOUTH;
         infoIn[Direction.WEST.m_122411_()] = WEST;
         infoIn[Direction.EAST.m_122411_()] = EAST;
      });

      private AdjacencyInfo(
         final Direction[] cornersIn,
         final float brightness,
         final boolean doNonCubicWeightIn,
         final ModelBlockRenderer.SizeInfo[] vert0WeightsIn,
         final ModelBlockRenderer.SizeInfo[] vert1WeightsIn,
         final ModelBlockRenderer.SizeInfo[] vert2WeightsIn,
         final ModelBlockRenderer.SizeInfo[] vert3WeightsIn
      ) {
         this.f_111110_ = cornersIn;
         this.f_111111_ = doNonCubicWeightIn;
         this.f_111112_ = vert0WeightsIn;
         this.f_111113_ = vert1WeightsIn;
         this.f_111114_ = vert2WeightsIn;
         this.f_111115_ = vert3WeightsIn;
      }

      public static ModelBlockRenderer.AdjacencyInfo m_111131_(Direction facing) {
         return f_111116_[facing.m_122411_()];
      }
   }

   public static class AmbientOcclusionFace {
      float[] f_111149_ = new float[4];
      int[] f_111150_ = new int[4];
      private BlockPosM blockPos = new BlockPosM();

      public AmbientOcclusionFace() {
         this(null);
      }

      public AmbientOcclusionFace(ModelBlockRenderer bmr) {
      }

      public void setMaxBlockLight() {
         int maxBlockLight = LightTexture.MAX_BRIGHTNESS;
         this.f_111150_[0] = maxBlockLight;
         this.f_111150_[1] = maxBlockLight;
         this.f_111150_[2] = maxBlockLight;
         this.f_111150_[3] = maxBlockLight;
         this.f_111149_[0] = 1.0F;
         this.f_111149_[1] = 1.0F;
         this.f_111149_[2] = 1.0F;
         this.f_111149_[3] = 1.0F;
      }

      public void m_111167_(
         BlockAndTintGetter worldIn, BlockState state, BlockPos centerPos, Direction directionIn, float[] faceShape, BitSet shapeState, boolean shadeIn
      ) {
         BlockPos blockpos = shapeState.get(0) ? centerPos.m_121945_(directionIn) : centerPos;
         ModelBlockRenderer.AdjacencyInfo modelblockrenderer$adjacencyinfo = ModelBlockRenderer.AdjacencyInfo.m_111131_(directionIn);
         BlockPosM blockpos$mutableblockpos = this.blockPos;
         LightCacheOF modelblockrenderer$cache = ModelBlockRenderer.LIGHT_CACHE_OF;
         blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[0]);
         BlockState blockstate = worldIn.m_8055_(blockpos$mutableblockpos);
         int i = LightCacheOF.getPackedLight(blockstate, worldIn, blockpos$mutableblockpos);
         float f = LightCacheOF.getBrightness(blockstate, worldIn, blockpos$mutableblockpos);
         blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[1]);
         BlockState blockstate1 = worldIn.m_8055_(blockpos$mutableblockpos);
         int j = LightCacheOF.getPackedLight(blockstate1, worldIn, blockpos$mutableblockpos);
         float f1 = LightCacheOF.getBrightness(blockstate1, worldIn, blockpos$mutableblockpos);
         blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[2]);
         BlockState blockstate2 = worldIn.m_8055_(blockpos$mutableblockpos);
         int k = LightCacheOF.getPackedLight(blockstate2, worldIn, blockpos$mutableblockpos);
         float f2 = LightCacheOF.getBrightness(blockstate2, worldIn, blockpos$mutableblockpos);
         blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[3]);
         BlockState blockstate3 = worldIn.m_8055_(blockpos$mutableblockpos);
         int l = LightCacheOF.getPackedLight(blockstate3, worldIn, blockpos$mutableblockpos);
         float f3 = LightCacheOF.getBrightness(blockstate3, worldIn, blockpos$mutableblockpos);
         boolean flag = !blockstate.m_60831_(worldIn, blockpos$mutableblockpos) || blockstate.m_60739_(worldIn, blockpos$mutableblockpos) == 0;
         boolean flag1 = !blockstate1.m_60831_(worldIn, blockpos$mutableblockpos) || blockstate1.m_60739_(worldIn, blockpos$mutableblockpos) == 0;
         boolean flag2 = !blockstate2.m_60831_(worldIn, blockpos$mutableblockpos) || blockstate2.m_60739_(worldIn, blockpos$mutableblockpos) == 0;
         boolean flag3 = !blockstate3.m_60831_(worldIn, blockpos$mutableblockpos) || blockstate3.m_60739_(worldIn, blockpos$mutableblockpos) == 0;
         float f4;
         int i1;
         if (!flag2 && !flag) {
            f4 = (f + f2) / 2.0F;
            i1 = m_111153_(i, k, 0, 0);
         } else {
            blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[0], modelblockrenderer$adjacencyinfo.f_111110_[2]);
            BlockState blockstate8 = worldIn.m_8055_(blockpos$mutableblockpos);
            f4 = LightCacheOF.getBrightness(blockstate8, worldIn, blockpos$mutableblockpos);
            i1 = LightCacheOF.getPackedLight(blockstate8, worldIn, blockpos$mutableblockpos);
         }

         int j1;
         float f5;
         if (!flag3 && !flag) {
            f5 = (f + f3) / 2.0F;
            j1 = m_111153_(i, l, 0, 0);
         } else {
            blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[0], modelblockrenderer$adjacencyinfo.f_111110_[3]);
            BlockState blockstate10 = worldIn.m_8055_(blockpos$mutableblockpos);
            f5 = LightCacheOF.getBrightness(blockstate10, worldIn, blockpos$mutableblockpos);
            j1 = LightCacheOF.getPackedLight(blockstate10, worldIn, blockpos$mutableblockpos);
         }

         int k1;
         float f6;
         if (!flag2 && !flag1) {
            f6 = (f1 + f2) / 2.0F;
            k1 = m_111153_(j, k, 0, 0);
         } else {
            blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[1], modelblockrenderer$adjacencyinfo.f_111110_[2]);
            BlockState blockstate11 = worldIn.m_8055_(blockpos$mutableblockpos);
            f6 = LightCacheOF.getBrightness(blockstate11, worldIn, blockpos$mutableblockpos);
            k1 = LightCacheOF.getPackedLight(blockstate11, worldIn, blockpos$mutableblockpos);
         }

         int l1;
         float f7;
         if (!flag3 && !flag1) {
            f7 = (f1 + f3) / 2.0F;
            l1 = m_111153_(j, l, 0, 0);
         } else {
            blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[1], modelblockrenderer$adjacencyinfo.f_111110_[3]);
            BlockState blockstate12 = worldIn.m_8055_(blockpos$mutableblockpos);
            f7 = LightCacheOF.getBrightness(blockstate12, worldIn, blockpos$mutableblockpos);
            l1 = LightCacheOF.getPackedLight(blockstate12, worldIn, blockpos$mutableblockpos);
         }

         int i3 = LightCacheOF.getPackedLight(state, worldIn, centerPos);
         blockpos$mutableblockpos.setPosOffset(centerPos, directionIn);
         BlockState blockstate9 = worldIn.m_8055_(blockpos$mutableblockpos);
         if (shapeState.get(0) || !blockstate9.m_60804_(worldIn, blockpos$mutableblockpos)) {
            i3 = LightCacheOF.getPackedLight(blockstate9, worldIn, blockpos$mutableblockpos);
         }

         float f8 = shapeState.get(0)
            ? LightCacheOF.getBrightness(worldIn.m_8055_(blockpos), worldIn, blockpos)
            : LightCacheOF.getBrightness(worldIn.m_8055_(centerPos), worldIn, centerPos);
         ModelBlockRenderer.AmbientVertexRemap modelblockrenderer$ambientvertexremap = ModelBlockRenderer.AmbientVertexRemap.m_111201_(directionIn);
         if (shapeState.get(1) && modelblockrenderer$adjacencyinfo.f_111111_) {
            float f29 = (f3 + f + f5 + f8) * 0.25F;
            float f31 = (f2 + f + f4 + f8) * 0.25F;
            float f32 = (f2 + f1 + f6 + f8) * 0.25F;
            float f33 = (f3 + f1 + f7 + f8) * 0.25F;
            float f13 = faceShape[modelblockrenderer$adjacencyinfo.f_111112_[0].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111112_[1].f_111258_];
            float f14 = faceShape[modelblockrenderer$adjacencyinfo.f_111112_[2].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111112_[3].f_111258_];
            float f15 = faceShape[modelblockrenderer$adjacencyinfo.f_111112_[4].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111112_[5].f_111258_];
            float f16 = faceShape[modelblockrenderer$adjacencyinfo.f_111112_[6].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111112_[7].f_111258_];
            float f17 = faceShape[modelblockrenderer$adjacencyinfo.f_111113_[0].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111113_[1].f_111258_];
            float f18 = faceShape[modelblockrenderer$adjacencyinfo.f_111113_[2].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111113_[3].f_111258_];
            float f19 = faceShape[modelblockrenderer$adjacencyinfo.f_111113_[4].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111113_[5].f_111258_];
            float f20 = faceShape[modelblockrenderer$adjacencyinfo.f_111113_[6].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111113_[7].f_111258_];
            float f21 = faceShape[modelblockrenderer$adjacencyinfo.f_111114_[0].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111114_[1].f_111258_];
            float f22 = faceShape[modelblockrenderer$adjacencyinfo.f_111114_[2].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111114_[3].f_111258_];
            float f23 = faceShape[modelblockrenderer$adjacencyinfo.f_111114_[4].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111114_[5].f_111258_];
            float f24 = faceShape[modelblockrenderer$adjacencyinfo.f_111114_[6].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111114_[7].f_111258_];
            float f25 = faceShape[modelblockrenderer$adjacencyinfo.f_111115_[0].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111115_[1].f_111258_];
            float f26 = faceShape[modelblockrenderer$adjacencyinfo.f_111115_[2].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111115_[3].f_111258_];
            float f27 = faceShape[modelblockrenderer$adjacencyinfo.f_111115_[4].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111115_[5].f_111258_];
            float f28 = faceShape[modelblockrenderer$adjacencyinfo.f_111115_[6].f_111258_] * faceShape[modelblockrenderer$adjacencyinfo.f_111115_[7].f_111258_];
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111185_] = f29 * f13 + f31 * f14 + f32 * f15 + f33 * f16;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111186_] = f29 * f17 + f31 * f18 + f32 * f19 + f33 * f20;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111187_] = f29 * f21 + f31 * f22 + f32 * f23 + f33 * f24;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111188_] = f29 * f25 + f31 * f26 + f32 * f27 + f33 * f28;
            int i2 = m_111153_(l, i, j1, i3);
            int j2 = m_111153_(k, i, i1, i3);
            int k2 = m_111153_(k, j, k1, i3);
            int l2 = m_111153_(l, j, l1, i3);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111185_] = this.m_111158_(i2, j2, k2, l2, f13, f14, f15, f16);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111186_] = this.m_111158_(i2, j2, k2, l2, f17, f18, f19, f20);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111187_] = this.m_111158_(i2, j2, k2, l2, f21, f22, f23, f24);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111188_] = this.m_111158_(i2, j2, k2, l2, f25, f26, f27, f28);
         } else {
            float f9 = (f3 + f + f5 + f8) * 0.25F;
            float f10 = (f2 + f + f4 + f8) * 0.25F;
            float f11 = (f2 + f1 + f6 + f8) * 0.25F;
            float f12 = (f3 + f1 + f7 + f8) * 0.25F;
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111185_] = m_111153_(l, i, j1, i3);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111186_] = m_111153_(k, i, i1, i3);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111187_] = m_111153_(k, j, k1, i3);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111188_] = m_111153_(l, j, l1, i3);
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111185_] = f9;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111186_] = f10;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111187_] = f11;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111188_] = f12;
         }

         float f30 = worldIn.m_7717_(directionIn, shadeIn);

         for (int j3 = 0; j3 < this.f_111149_.length; j3++) {
            this.f_111149_[j3] = this.f_111149_[j3] * f30;
         }
      }

      public static int m_111153_(int br1, int br2, int br3, int br4) {
         if (br1 != 15794417 && br2 != 15794417 && br3 != 15794417 && br4 != 15794417) {
            int sum = br1 + br2 + br3 + br4;
            int count = 4;
            if (br1 == 0) {
               count--;
            }

            if (br2 == 0) {
               count--;
            }

            if (br3 == 0) {
               count--;
            }

            if (br4 == 0) {
               count--;
            }

            switch (count) {
               case 0:
               case 1:
                  return sum;
               case 2:
                  return sum >> 1 & 16711935;
               case 3:
                  return sum / 3 & 0xFF0000 | (sum & 65535) / 3;
               default:
                  return sum >> 2 & 16711935;
            }
         } else {
            return br1 + br2 + br3 + br4 >> 2 & 16711935;
         }
      }

      private int m_111158_(int b1, int b2, int b3, int b4, float w1, float w2, float w3, float w4) {
         int i = (int)((float)(b1 >> 16 & 0xFF) * w1 + (float)(b2 >> 16 & 0xFF) * w2 + (float)(b3 >> 16 & 0xFF) * w3 + (float)(b4 >> 16 & 0xFF) * w4) & 0xFF;
         int j = (int)((float)(b1 & 0xFF) * w1 + (float)(b2 & 0xFF) * w2 + (float)(b3 & 0xFF) * w3 + (float)(b4 & 0xFF) * w4) & 0xFF;
         return i << 16 | j;
      }
   }

   static enum AmbientVertexRemap {
      DOWN(0, 1, 2, 3),
      UP(2, 3, 0, 1),
      NORTH(3, 0, 1, 2),
      SOUTH(0, 1, 2, 3),
      WEST(3, 0, 1, 2),
      EAST(1, 2, 3, 0);

      int f_111185_;
      int f_111186_;
      int f_111187_;
      int f_111188_;
      private static ModelBlockRenderer.AmbientVertexRemap[] f_111189_ = Util.m_137469_(new ModelBlockRenderer.AmbientVertexRemap[6], remapIn -> {
         remapIn[Direction.DOWN.m_122411_()] = DOWN;
         remapIn[Direction.UP.m_122411_()] = UP;
         remapIn[Direction.NORTH.m_122411_()] = NORTH;
         remapIn[Direction.SOUTH.m_122411_()] = SOUTH;
         remapIn[Direction.WEST.m_122411_()] = WEST;
         remapIn[Direction.EAST.m_122411_()] = EAST;
      });

      private AmbientVertexRemap(final int vert0In, final int vert1In, final int vert2In, final int vert3In) {
         this.f_111185_ = vert0In;
         this.f_111186_ = vert1In;
         this.f_111187_ = vert2In;
         this.f_111188_ = vert3In;
      }

      public static ModelBlockRenderer.AmbientVertexRemap m_111201_(Direction facingIn) {
         return f_111189_[facingIn.m_122411_()];
      }
   }

   static class Cache {
      private boolean f_111214_;
      private Long2IntLinkedOpenHashMap f_111215_ = Util.m_137537_(() -> {
         Long2IntLinkedOpenHashMap long2intlinkedopenhashmap = new Long2IntLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int p_rehash_1_) {
            }
         };
         long2intlinkedopenhashmap.defaultReturnValue(Integer.MAX_VALUE);
         return long2intlinkedopenhashmap;
      });
      private Long2FloatLinkedOpenHashMap f_111216_ = Util.m_137537_(() -> {
         Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = new Long2FloatLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int p_rehash_1_) {
            }
         };
         long2floatlinkedopenhashmap.defaultReturnValue(Float.NaN);
         return long2floatlinkedopenhashmap;
      });

      private Cache() {
      }

      public void m_111220_() {
         this.f_111214_ = true;
      }

      public void m_111225_() {
         this.f_111214_ = false;
         this.f_111215_.clear();
         this.f_111216_.clear();
      }

      public int m_111221_(BlockState blockStateIn, BlockAndTintGetter lightReaderIn, BlockPos blockPosIn) {
         long i = blockPosIn.m_121878_();
         if (this.f_111214_) {
            int j = this.f_111215_.get(i);
            if (j != Integer.MAX_VALUE) {
               return j;
            }
         }

         int k = LevelRenderer.m_109537_(lightReaderIn, blockStateIn, blockPosIn);
         if (this.f_111214_) {
            if (this.f_111215_.size() == 100) {
               this.f_111215_.removeFirstInt();
            }

            this.f_111215_.put(i, k);
         }

         return k;
      }

      public float m_111226_(BlockState blockStateIn, BlockAndTintGetter lightReaderIn, BlockPos blockPosIn) {
         long i = blockPosIn.m_121878_();
         if (this.f_111214_) {
            float f = this.f_111216_.get(i);
            if (!Float.isNaN(f)) {
               return f;
            }
         }

         float f1 = blockStateIn.m_60792_(lightReaderIn, blockPosIn);
         if (this.f_111214_) {
            if (this.f_111216_.size() == 100) {
               this.f_111216_.removeFirstFloat();
            }

            this.f_111216_.put(i, f1);
         }

         return f1;
      }
   }

   protected static enum SizeInfo {
      DOWN(Direction.DOWN, false),
      UP(Direction.UP, false),
      NORTH(Direction.NORTH, false),
      SOUTH(Direction.SOUTH, false),
      WEST(Direction.WEST, false),
      EAST(Direction.EAST, false),
      FLIP_DOWN(Direction.DOWN, true),
      FLIP_UP(Direction.UP, true),
      FLIP_NORTH(Direction.NORTH, true),
      FLIP_SOUTH(Direction.SOUTH, true),
      FLIP_WEST(Direction.WEST, true),
      FLIP_EAST(Direction.EAST, true);

      int f_111258_;

      private SizeInfo(final Direction facingIn, final boolean flip) {
         this.f_111258_ = facingIn.m_122411_() + (flip ? ModelBlockRenderer.f_173405_.length : 0);
      }
   }
}
