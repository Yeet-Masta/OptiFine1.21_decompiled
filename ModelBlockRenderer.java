import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_290184_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3423_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_4675_.C_4681_;
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
   private static final int a = 0;
   private static final int b = 1;
   static final Direction[] c = Direction.values();
   private final C_3423_ d;
   private static final int e = 100;
   static final ThreadLocal<ModelBlockRenderer.d> f = ThreadLocal.withInitial(ModelBlockRenderer.d::new);
   private static float aoLightValueOpaque = 0.2F;
   private static boolean separateAoLightValue = false;
   private static final LightCacheOF LIGHT_CACHE_OF = new LightCacheOF();
   private static final RenderType[] OVERLAY_LAYERS = new RenderType[]{RenderTypes.CUTOUT, RenderTypes.CUTOUT_MIPPED, RenderTypes.TRANSLUCENT};
   private boolean forge = Reflector.ForgeHooksClient.exists();

   public ModelBlockRenderer(C_3423_ blockColorsIn) {
      this.d = blockColorsIn;
   }

   public void a(
      C_1557_ worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      C_4675_ posIn,
      PoseStack matrixIn,
      VertexConsumer buffer,
      boolean checkSides,
      C_212974_ randomIn,
      long rand,
      int combinedOverlayIn
   ) {
      this.tesselateBlock(worldIn, modelIn, stateIn, posIn, matrixIn, buffer, checkSides, randomIn, rand, combinedOverlayIn, ModelData.EMPTY, null);
   }

   public void tesselateBlock(
      C_1557_ worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      C_4675_ posIn,
      PoseStack matrixIn,
      VertexConsumer buffer,
      boolean checkSides,
      C_212974_ randomIn,
      long rand,
      int combinedOverlayIn,
      ModelData modelData,
      RenderType renderType
   ) {
      boolean flag = C_3391_.m_91086_() && stateIn.getLightEmission(worldIn, posIn) == 0 && modelIn.useAmbientOcclusion(stateIn, renderType);
      Vec3 vec3 = stateIn.n(worldIn, posIn);
      matrixIn.a(vec3.c, vec3.d, vec3.e);

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
         CrashReport crashreport = CrashReport.a(var19, "Tesselating block model");
         C_4909_ crashreportcategory = crashreport.a("Block model being tesselated");
         C_4909_.a(crashreportcategory, worldIn, posIn, stateIn);
         crashreportcategory.m_128159_("Using AO", flag);
         throw new C_5204_(crashreport);
      }
   }

   public void b(
      C_1557_ worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      C_4675_ posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      boolean checkSides,
      C_212974_ randomIn,
      long rand,
      int combinedOverlayIn
   ) {
      this.renderModelSmooth(worldIn, modelIn, stateIn, posIn, matrixStackIn, buffer, checkSides, randomIn, rand, combinedOverlayIn, ModelData.EMPTY, null);
   }

   public void renderModelSmooth(
      C_1557_ worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      C_4675_ posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      boolean checkSides,
      C_212974_ randomIn,
      long rand,
      int combinedOverlayIn,
      ModelData modelData,
      RenderType renderType
   ) {
      RenderEnv renderEnv = buffer.getRenderEnv(stateIn, posIn);
      RenderType layer = buffer.getRenderType();

      for (Direction direction : c) {
         if (!checkSides || BlockUtils.shouldSideBeRendered(stateIn, worldIn, posIn, direction, renderEnv)) {
            randomIn.m_188584_(rand);
            List<BakedQuad> list = this.forge ? modelIn.getQuads(stateIn, direction, randomIn, modelData, renderType) : modelIn.a(stateIn, direction, randomIn);
            list = BlockModelCustomizer.getRenderQuads(list, worldIn, stateIn, posIn, direction, layer, rand, renderEnv);
            if (!list.isEmpty()) {
               this.renderQuadsSmooth(worldIn, stateIn, posIn, matrixStackIn, buffer, list, combinedOverlayIn, renderEnv);
            }
         }
      }

      randomIn.m_188584_(rand);
      List<BakedQuad> list1 = this.forge ? modelIn.getQuads(stateIn, null, randomIn, modelData, renderType) : modelIn.a(stateIn, null, randomIn);
      if (!list1.isEmpty()) {
         list1 = BlockModelCustomizer.getRenderQuads(list1, worldIn, stateIn, posIn, null, layer, rand, renderEnv);
         this.renderQuadsSmooth(worldIn, stateIn, posIn, matrixStackIn, buffer, list1, combinedOverlayIn, renderEnv);
      }
   }

   public void c(
      C_1557_ worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      C_4675_ posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      boolean checkSides,
      C_212974_ randomIn,
      long rand,
      int combinedOverlayIn
   ) {
      this.renderModelFlat(worldIn, modelIn, stateIn, posIn, matrixStackIn, buffer, checkSides, randomIn, rand, combinedOverlayIn, ModelData.EMPTY, null);
   }

   public void renderModelFlat(
      C_1557_ worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      C_4675_ posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      boolean checkSides,
      C_212974_ randomIn,
      long rand,
      int combinedOverlayIn,
      ModelData modelData,
      RenderType renderType
   ) {
      RenderEnv renderEnv = buffer.getRenderEnv(stateIn, posIn);
      RenderType layer = buffer.getRenderType();

      for (Direction direction : c) {
         if (!checkSides || BlockUtils.shouldSideBeRendered(stateIn, worldIn, posIn, direction, renderEnv)) {
            randomIn.m_188584_(rand);
            List<BakedQuad> list = this.forge ? modelIn.getQuads(stateIn, direction, randomIn, modelData, renderType) : modelIn.a(stateIn, direction, randomIn);
            list = BlockModelCustomizer.getRenderQuads(list, worldIn, stateIn, posIn, direction, layer, rand, renderEnv);
            if (!list.isEmpty()) {
               C_4681_ blockpos$mutableblockpos = renderEnv.getRenderMutableBlockPos();
               blockpos$mutableblockpos.a(posIn, direction);
               int i = LevelRenderer.a(worldIn, stateIn, blockpos$mutableblockpos);
               this.renderQuadsFlat(worldIn, stateIn, posIn, i, combinedOverlayIn, false, matrixStackIn, buffer, list, renderEnv);
            }
         }
      }

      randomIn.m_188584_(rand);
      List<BakedQuad> list1 = this.forge ? modelIn.getQuads(stateIn, null, randomIn, modelData, renderType) : modelIn.a(stateIn, null, randomIn);
      if (!list1.isEmpty()) {
         list1 = BlockModelCustomizer.getRenderQuads(list1, worldIn, stateIn, posIn, null, layer, rand, renderEnv);
         this.renderQuadsFlat(worldIn, stateIn, posIn, -1, combinedOverlayIn, true, matrixStackIn, buffer, list1, renderEnv);
      }
   }

   private void renderQuadsSmooth(
      C_1557_ blockAccessIn,
      BlockState stateIn,
      C_4675_ posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      List<BakedQuad> list,
      int combinedOverlayIn,
      RenderEnv renderEnv
   ) {
      float[] quadBounds = renderEnv.getQuadBounds();
      BitSet bitSet = renderEnv.getBoundsFlags();
      ModelBlockRenderer.b aoFace = renderEnv.getAoFace();
      int listSize = list.size();

      for (int ix = 0; ix < listSize; ix++) {
         BakedQuad bakedquad = (BakedQuad)list.get(ix);
         this.a(blockAccessIn, stateIn, posIn, bakedquad.b(), bakedquad.e(), quadBounds, bitSet);
         if (bakedquad.hasAmbientOcclusion()
            || !ReflectorForge.calculateFaceWithoutAO(blockAccessIn, stateIn, posIn, bakedquad, bitSet.get(0), aoFace.a, aoFace.b)) {
            aoFace.a(blockAccessIn, stateIn, posIn, bakedquad.e(), quadBounds, bitSet, bakedquad.f());
         }

         if (bakedquad.a().isSpriteEmissive) {
            aoFace.setMaxBlockLight();
         }

         this.renderQuadSmooth(
            blockAccessIn,
            stateIn,
            posIn,
            buffer,
            matrixStackIn.c(),
            bakedquad,
            aoFace.a[0],
            aoFace.a[1],
            aoFace.a[2],
            aoFace.a[3],
            aoFace.b[0],
            aoFace.b[1],
            aoFace.b[2],
            aoFace.b[3],
            combinedOverlayIn,
            renderEnv
         );
      }
   }

   private void renderQuadSmooth(
      C_1557_ blockAccessIn,
      BlockState stateIn,
      C_4675_ posIn,
      VertexConsumer buffer,
      PoseStack.a matrixEntry,
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
      if (!quadIn.c() && colorMultiplier == -1) {
         f = 1.0F;
         f1 = 1.0F;
         f2 = 1.0F;
      } else {
         int i = colorMultiplier != -1 ? colorMultiplier : this.d.a(stateIn, blockAccessIn, posIn, quadIn.d());
         f = (float)(i >> 16 & 0xFF) / 255.0F;
         f1 = (float)(i >> 8 & 0xFF) / 255.0F;
         f2 = (float)(i & 0xFF) / 255.0F;
      }

      buffer.a(
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

   private void a(C_1557_ blockReaderIn, BlockState stateIn, C_4675_ posIn, int[] vertexData, Direction face, @Nullable float[] quadBounds, BitSet boundsFlags) {
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
         quadBounds[Direction.e.d()] = f;
         quadBounds[Direction.f.d()] = f3;
         quadBounds[Direction.a.d()] = f1;
         quadBounds[Direction.b.d()] = f4;
         quadBounds[Direction.c.d()] = f2;
         quadBounds[Direction.d.d()] = f5;
         int j = c.length;
         quadBounds[Direction.e.d() + j] = 1.0F - f;
         quadBounds[Direction.f.d() + j] = 1.0F - f3;
         quadBounds[Direction.a.d() + j] = 1.0F - f1;
         quadBounds[Direction.b.d() + j] = 1.0F - f4;
         quadBounds[Direction.c.d() + j] = 1.0F - f2;
         quadBounds[Direction.d.d() + j] = 1.0F - f5;
      }

      float f9 = 1.0E-4F;
      float f10 = 0.9999F;
      switch (face) {
         case a:
            boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, f1 == f4 && (f1 < 1.0E-4F || stateIn.m_60838_(blockReaderIn, posIn)));
            break;
         case b:
            boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, f1 == f4 && (f4 > 0.9999F || stateIn.m_60838_(blockReaderIn, posIn)));
            break;
         case c:
            boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
            boundsFlags.set(0, f2 == f5 && (f2 < 1.0E-4F || stateIn.m_60838_(blockReaderIn, posIn)));
            break;
         case d:
            boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
            boundsFlags.set(0, f2 == f5 && (f5 > 0.9999F || stateIn.m_60838_(blockReaderIn, posIn)));
            break;
         case e:
            boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, f == f3 && (f < 1.0E-4F || stateIn.m_60838_(blockReaderIn, posIn)));
            break;
         case f:
            boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, f == f3 && (f3 > 0.9999F || stateIn.m_60838_(blockReaderIn, posIn)));
      }
   }

   private void renderQuadsFlat(
      C_1557_ blockAccessIn,
      BlockState stateIn,
      C_4675_ posIn,
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
            this.a(blockAccessIn, stateIn, posIn, bakedquad.b(), bakedquad.e(), null, bitSet);
            C_4675_ blockpos = bitSet.get(0) ? posIn.a(bakedquad.e()) : posIn;
            brightnessIn = LevelRenderer.a(blockAccessIn, stateIn, blockpos);
         }

         if (bakedquad.a().isSpriteEmissive) {
            brightnessIn = LightTexture.MAX_BRIGHTNESS;
         }

         float f = blockAccessIn.a(bakedquad.e(), bakedquad.f());
         this.renderQuadSmooth(
            blockAccessIn,
            stateIn,
            posIn,
            buffer,
            matrixStackIn.c(),
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

   public void a(
      PoseStack.a matrixEntry,
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
      PoseStack.a matrixEntry,
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
      C_212974_ randomsource = C_212974_.m_216327_();
      long i = 42L;

      for (Direction direction : c) {
         randomsource.m_188584_(42L);
         if (this.forge) {
            a(
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
            a(matrixEntry, buffer, red, green, blue, modelIn.a(state, direction, randomsource), combinedLightIn, combinedOverlayIn);
         }
      }

      randomsource.m_188584_(42L);
      if (this.forge) {
         a(
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
         a(matrixEntry, buffer, red, green, blue, modelIn.a(state, null, randomsource), combinedLightIn, combinedOverlayIn);
      }
   }

   private static void a(
      PoseStack.a matrixEntry, VertexConsumer buffer, float red, float green, float blue, List<BakedQuad> listQuads, int combinedLightIn, int combinedOverlayIn
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
         if (bakedquad.c()) {
            f = Mth.a(red, 0.0F, 1.0F);
            f1 = Mth.a(green, 0.0F, 1.0F);
            f2 = Mth.a(blue, 0.0F, 1.0F);
         } else {
            f = 1.0F;
            f1 = 1.0F;
            f2 = 1.0F;
         }

         buffer.a(matrixEntry, bakedquad, f, f1, f2, 1.0F, combinedLightIn, combinedOverlayIn);
      }
   }

   public static void a() {
      ((ModelBlockRenderer.d)f.get()).a();
   }

   public static void b() {
      ((ModelBlockRenderer.d)f.get()).b();
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
      C_1557_ worldIn,
      BakedModel modelIn,
      BlockState stateIn,
      C_4675_ posIn,
      PoseStack matrixStackIn,
      VertexConsumer buffer,
      int combinedOverlayIn,
      boolean checkSides,
      C_212974_ random,
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
               C_290184_ sbbp = renderEnv.getSectionBufferBuilderPack();
               if (sc != null && bbMap != null && sbbp != null) {
                  BufferBuilder overlayBuffer = sc.a(bbMap, sbbp, layer);

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
                        int col = LevelRenderer.a(worldIn, quadBlockState, posIn.a(quad.e()));
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
         matrixStackIn.a(-renderOffset.c, -renderOffset.d, -renderOffset.e);
         this.a(worldIn, modelSnow, stateSnow, posIn, matrixStackIn, buffer, checkSides, random, rand, combinedOverlayIn);
      }
   }

   protected static enum a {
      a(
         new Direction[]{Direction.e, Direction.f, Direction.c, Direction.d},
         0.5F,
         true,
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.d,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.d
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.c,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.c
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.c,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.c
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.d,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.d
         }
      ),
      b(
         new Direction[]{Direction.f, Direction.e, Direction.c, Direction.d},
         1.0F,
         true,
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.d,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.d
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.c,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.c
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.c,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.c
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.d,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.d
         }
      ),
      c(
         new Direction[]{Direction.b, Direction.a, Direction.f, Direction.e},
         0.8F,
         true,
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.k
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.l
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.l
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.k
         }
      ),
      d(
         new Direction[]{Direction.e, Direction.f, Direction.a, Direction.b},
         0.8F,
         true,
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.e
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.k,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.e,
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.e
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.f
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.l,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.f,
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.f
         }
      ),
      e(
         new Direction[]{Direction.b, Direction.a, Direction.c, Direction.d},
         0.6F,
         true,
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.d,
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.d
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.c,
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.c
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.c,
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.c
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.d,
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.d
         }
      ),
      f(
         new Direction[]{Direction.a, Direction.b, Direction.c, Direction.d},
         0.6F,
         true,
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.d,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.d
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.c,
            ModelBlockRenderer.e.g,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.a,
            ModelBlockRenderer.e.c
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.c,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.i,
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.c
         },
         new ModelBlockRenderer.e[]{
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.d,
            ModelBlockRenderer.e.h,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.j,
            ModelBlockRenderer.e.b,
            ModelBlockRenderer.e.d
         }
      );

      final Direction[] g;
      final boolean h;
      final ModelBlockRenderer.e[] i;
      final ModelBlockRenderer.e[] j;
      final ModelBlockRenderer.e[] k;
      final ModelBlockRenderer.e[] l;
      private static final ModelBlockRenderer.a[] m = Util.a(new ModelBlockRenderer.a[6], infoIn -> {
         infoIn[Direction.a.d()] = a;
         infoIn[Direction.b.d()] = b;
         infoIn[Direction.c.d()] = c;
         infoIn[Direction.d.d()] = d;
         infoIn[Direction.e.d()] = e;
         infoIn[Direction.f.d()] = f;
      });

      private a(
         final Direction[] cornersIn,
         final float brightness,
         final boolean doNonCubicWeightIn,
         final ModelBlockRenderer.e[] vert0WeightsIn,
         final ModelBlockRenderer.e[] vert1WeightsIn,
         final ModelBlockRenderer.e[] vert2WeightsIn,
         final ModelBlockRenderer.e[] vert3WeightsIn
      ) {
         this.g = cornersIn;
         this.h = doNonCubicWeightIn;
         this.i = vert0WeightsIn;
         this.j = vert1WeightsIn;
         this.k = vert2WeightsIn;
         this.l = vert3WeightsIn;
      }

      public static ModelBlockRenderer.a a(Direction facing) {
         return m[facing.d()];
      }
   }

   public static class b {
      final float[] a = new float[4];
      final int[] b = new int[4];
      private BlockPosM blockPos = new BlockPosM();

      public b() {
         this(null);
      }

      public b(ModelBlockRenderer bmr) {
      }

      public void setMaxBlockLight() {
         int maxBlockLight = LightTexture.MAX_BRIGHTNESS;
         this.b[0] = maxBlockLight;
         this.b[1] = maxBlockLight;
         this.b[2] = maxBlockLight;
         this.b[3] = maxBlockLight;
         this.a[0] = 1.0F;
         this.a[1] = 1.0F;
         this.a[2] = 1.0F;
         this.a[3] = 1.0F;
      }

      public void a(C_1557_ worldIn, BlockState state, C_4675_ centerPos, Direction directionIn, float[] faceShape, BitSet shapeState, boolean shadeIn) {
         C_4675_ blockpos = shapeState.get(0) ? centerPos.a(directionIn) : centerPos;
         ModelBlockRenderer.a modelblockrenderer$adjacencyinfo = ModelBlockRenderer.a.a(directionIn);
         BlockPosM blockpos$mutableblockpos = this.blockPos;
         LightCacheOF modelblockrenderer$cache = ModelBlockRenderer.LIGHT_CACHE_OF;
         blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.g[0]);
         BlockState blockstate = worldIn.a_(blockpos$mutableblockpos);
         int i = LightCacheOF.getPackedLight(blockstate, worldIn, blockpos$mutableblockpos);
         float f = LightCacheOF.getBrightness(blockstate, worldIn, blockpos$mutableblockpos);
         blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.g[1]);
         BlockState blockstate1 = worldIn.a_(blockpos$mutableblockpos);
         int j = LightCacheOF.getPackedLight(blockstate1, worldIn, blockpos$mutableblockpos);
         float f1 = LightCacheOF.getBrightness(blockstate1, worldIn, blockpos$mutableblockpos);
         blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.g[2]);
         BlockState blockstate2 = worldIn.a_(blockpos$mutableblockpos);
         int k = LightCacheOF.getPackedLight(blockstate2, worldIn, blockpos$mutableblockpos);
         float f2 = LightCacheOF.getBrightness(blockstate2, worldIn, blockpos$mutableblockpos);
         blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.g[3]);
         BlockState blockstate3 = worldIn.a_(blockpos$mutableblockpos);
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
            i1 = a(i, k, 0, 0);
         } else {
            blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.g[0], modelblockrenderer$adjacencyinfo.g[2]);
            BlockState blockstate8 = worldIn.a_(blockpos$mutableblockpos);
            f4 = LightCacheOF.getBrightness(blockstate8, worldIn, blockpos$mutableblockpos);
            i1 = LightCacheOF.getPackedLight(blockstate8, worldIn, blockpos$mutableblockpos);
         }

         int j1;
         float f5;
         if (!flag3 && !flag) {
            f5 = (f + f3) / 2.0F;
            j1 = a(i, l, 0, 0);
         } else {
            blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.g[0], modelblockrenderer$adjacencyinfo.g[3]);
            BlockState blockstate10 = worldIn.a_(blockpos$mutableblockpos);
            f5 = LightCacheOF.getBrightness(blockstate10, worldIn, blockpos$mutableblockpos);
            j1 = LightCacheOF.getPackedLight(blockstate10, worldIn, blockpos$mutableblockpos);
         }

         int k1;
         float f6;
         if (!flag2 && !flag1) {
            f6 = (f1 + f2) / 2.0F;
            k1 = a(j, k, 0, 0);
         } else {
            blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.g[1], modelblockrenderer$adjacencyinfo.g[2]);
            BlockState blockstate11 = worldIn.a_(blockpos$mutableblockpos);
            f6 = LightCacheOF.getBrightness(blockstate11, worldIn, blockpos$mutableblockpos);
            k1 = LightCacheOF.getPackedLight(blockstate11, worldIn, blockpos$mutableblockpos);
         }

         int l1;
         float f7;
         if (!flag3 && !flag1) {
            f7 = (f1 + f3) / 2.0F;
            l1 = a(j, l, 0, 0);
         } else {
            blockpos$mutableblockpos.setPosOffset(blockpos, modelblockrenderer$adjacencyinfo.g[1], modelblockrenderer$adjacencyinfo.g[3]);
            BlockState blockstate12 = worldIn.a_(blockpos$mutableblockpos);
            f7 = LightCacheOF.getBrightness(blockstate12, worldIn, blockpos$mutableblockpos);
            l1 = LightCacheOF.getPackedLight(blockstate12, worldIn, blockpos$mutableblockpos);
         }

         int i3 = LightCacheOF.getPackedLight(state, worldIn, centerPos);
         blockpos$mutableblockpos.setPosOffset(centerPos, directionIn);
         BlockState blockstate9 = worldIn.a_(blockpos$mutableblockpos);
         if (shapeState.get(0) || !blockstate9.m_60804_(worldIn, blockpos$mutableblockpos)) {
            i3 = LightCacheOF.getPackedLight(blockstate9, worldIn, blockpos$mutableblockpos);
         }

         float f8 = shapeState.get(0)
            ? LightCacheOF.getBrightness(worldIn.a_(blockpos), worldIn, blockpos)
            : LightCacheOF.getBrightness(worldIn.a_(centerPos), worldIn, centerPos);
         ModelBlockRenderer.c modelblockrenderer$ambientvertexremap = ModelBlockRenderer.c.a(directionIn);
         if (shapeState.get(1) && modelblockrenderer$adjacencyinfo.h) {
            float f29 = (f3 + f + f5 + f8) * 0.25F;
            float f31 = (f2 + f + f4 + f8) * 0.25F;
            float f32 = (f2 + f1 + f6 + f8) * 0.25F;
            float f33 = (f3 + f1 + f7 + f8) * 0.25F;
            float f13 = faceShape[modelblockrenderer$adjacencyinfo.i[0].m] * faceShape[modelblockrenderer$adjacencyinfo.i[1].m];
            float f14 = faceShape[modelblockrenderer$adjacencyinfo.i[2].m] * faceShape[modelblockrenderer$adjacencyinfo.i[3].m];
            float f15 = faceShape[modelblockrenderer$adjacencyinfo.i[4].m] * faceShape[modelblockrenderer$adjacencyinfo.i[5].m];
            float f16 = faceShape[modelblockrenderer$adjacencyinfo.i[6].m] * faceShape[modelblockrenderer$adjacencyinfo.i[7].m];
            float f17 = faceShape[modelblockrenderer$adjacencyinfo.j[0].m] * faceShape[modelblockrenderer$adjacencyinfo.j[1].m];
            float f18 = faceShape[modelblockrenderer$adjacencyinfo.j[2].m] * faceShape[modelblockrenderer$adjacencyinfo.j[3].m];
            float f19 = faceShape[modelblockrenderer$adjacencyinfo.j[4].m] * faceShape[modelblockrenderer$adjacencyinfo.j[5].m];
            float f20 = faceShape[modelblockrenderer$adjacencyinfo.j[6].m] * faceShape[modelblockrenderer$adjacencyinfo.j[7].m];
            float f21 = faceShape[modelblockrenderer$adjacencyinfo.k[0].m] * faceShape[modelblockrenderer$adjacencyinfo.k[1].m];
            float f22 = faceShape[modelblockrenderer$adjacencyinfo.k[2].m] * faceShape[modelblockrenderer$adjacencyinfo.k[3].m];
            float f23 = faceShape[modelblockrenderer$adjacencyinfo.k[4].m] * faceShape[modelblockrenderer$adjacencyinfo.k[5].m];
            float f24 = faceShape[modelblockrenderer$adjacencyinfo.k[6].m] * faceShape[modelblockrenderer$adjacencyinfo.k[7].m];
            float f25 = faceShape[modelblockrenderer$adjacencyinfo.l[0].m] * faceShape[modelblockrenderer$adjacencyinfo.l[1].m];
            float f26 = faceShape[modelblockrenderer$adjacencyinfo.l[2].m] * faceShape[modelblockrenderer$adjacencyinfo.l[3].m];
            float f27 = faceShape[modelblockrenderer$adjacencyinfo.l[4].m] * faceShape[modelblockrenderer$adjacencyinfo.l[5].m];
            float f28 = faceShape[modelblockrenderer$adjacencyinfo.l[6].m] * faceShape[modelblockrenderer$adjacencyinfo.l[7].m];
            this.a[modelblockrenderer$ambientvertexremap.g] = f29 * f13 + f31 * f14 + f32 * f15 + f33 * f16;
            this.a[modelblockrenderer$ambientvertexremap.h] = f29 * f17 + f31 * f18 + f32 * f19 + f33 * f20;
            this.a[modelblockrenderer$ambientvertexremap.i] = f29 * f21 + f31 * f22 + f32 * f23 + f33 * f24;
            this.a[modelblockrenderer$ambientvertexremap.j] = f29 * f25 + f31 * f26 + f32 * f27 + f33 * f28;
            int i2 = a(l, i, j1, i3);
            int j2 = a(k, i, i1, i3);
            int k2 = a(k, j, k1, i3);
            int l2 = a(l, j, l1, i3);
            this.b[modelblockrenderer$ambientvertexremap.g] = this.a(i2, j2, k2, l2, f13, f14, f15, f16);
            this.b[modelblockrenderer$ambientvertexremap.h] = this.a(i2, j2, k2, l2, f17, f18, f19, f20);
            this.b[modelblockrenderer$ambientvertexremap.i] = this.a(i2, j2, k2, l2, f21, f22, f23, f24);
            this.b[modelblockrenderer$ambientvertexremap.j] = this.a(i2, j2, k2, l2, f25, f26, f27, f28);
         } else {
            float f9 = (f3 + f + f5 + f8) * 0.25F;
            float f10 = (f2 + f + f4 + f8) * 0.25F;
            float f11 = (f2 + f1 + f6 + f8) * 0.25F;
            float f12 = (f3 + f1 + f7 + f8) * 0.25F;
            this.b[modelblockrenderer$ambientvertexremap.g] = a(l, i, j1, i3);
            this.b[modelblockrenderer$ambientvertexremap.h] = a(k, i, i1, i3);
            this.b[modelblockrenderer$ambientvertexremap.i] = a(k, j, k1, i3);
            this.b[modelblockrenderer$ambientvertexremap.j] = a(l, j, l1, i3);
            this.a[modelblockrenderer$ambientvertexremap.g] = f9;
            this.a[modelblockrenderer$ambientvertexremap.h] = f10;
            this.a[modelblockrenderer$ambientvertexremap.i] = f11;
            this.a[modelblockrenderer$ambientvertexremap.j] = f12;
         }

         float f30 = worldIn.a(directionIn, shadeIn);

         for (int j3 = 0; j3 < this.a.length; j3++) {
            this.a[j3] = this.a[j3] * f30;
         }
      }

      public static int a(int br1, int br2, int br3, int br4) {
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

      private int a(int b1, int b2, int b3, int b4, float w1, float w2, float w3, float w4) {
         int i = (int)((float)(b1 >> 16 & 0xFF) * w1 + (float)(b2 >> 16 & 0xFF) * w2 + (float)(b3 >> 16 & 0xFF) * w3 + (float)(b4 >> 16 & 0xFF) * w4) & 0xFF;
         int j = (int)((float)(b1 & 0xFF) * w1 + (float)(b2 & 0xFF) * w2 + (float)(b3 & 0xFF) * w3 + (float)(b4 & 0xFF) * w4) & 0xFF;
         return i << 16 | j;
      }
   }

   static enum c {
      a(0, 1, 2, 3),
      b(2, 3, 0, 1),
      c(3, 0, 1, 2),
      d(0, 1, 2, 3),
      e(3, 0, 1, 2),
      f(1, 2, 3, 0);

      final int g;
      final int h;
      final int i;
      final int j;
      private static final ModelBlockRenderer.c[] k = Util.a(new ModelBlockRenderer.c[6], remapIn -> {
         remapIn[Direction.a.d()] = a;
         remapIn[Direction.b.d()] = b;
         remapIn[Direction.c.d()] = c;
         remapIn[Direction.d.d()] = d;
         remapIn[Direction.e.d()] = e;
         remapIn[Direction.f.d()] = f;
      });

      private c(final int vert0In, final int vert1In, final int vert2In, final int vert3In) {
         this.g = vert0In;
         this.h = vert1In;
         this.i = vert2In;
         this.j = vert3In;
      }

      public static ModelBlockRenderer.c a(Direction facingIn) {
         return k[facingIn.d()];
      }
   }

   static class d {
      private boolean a;
      private final Long2IntLinkedOpenHashMap b = Util.a((Supplier<Long2IntLinkedOpenHashMap>)(() -> {
         Long2IntLinkedOpenHashMap long2intlinkedopenhashmap = new Long2IntLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int p_rehash_1_) {
            }
         };
         long2intlinkedopenhashmap.defaultReturnValue(Integer.MAX_VALUE);
         return long2intlinkedopenhashmap;
      }));
      private final Long2FloatLinkedOpenHashMap c = Util.a((Supplier<Long2FloatLinkedOpenHashMap>)(() -> {
         Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = new Long2FloatLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int p_rehash_1_) {
            }
         };
         long2floatlinkedopenhashmap.defaultReturnValue(Float.NaN);
         return long2floatlinkedopenhashmap;
      }));

      private d() {
      }

      public void a() {
         this.a = true;
      }

      public void b() {
         this.a = false;
         this.b.clear();
         this.c.clear();
      }

      public int a(BlockState blockStateIn, C_1557_ lightReaderIn, C_4675_ blockPosIn) {
         long i = blockPosIn.m_121878_();
         if (this.a) {
            int j = this.b.get(i);
            if (j != Integer.MAX_VALUE) {
               return j;
            }
         }

         int k = LevelRenderer.a(lightReaderIn, blockStateIn, blockPosIn);
         if (this.a) {
            if (this.b.size() == 100) {
               this.b.removeFirstInt();
            }

            this.b.put(i, k);
         }

         return k;
      }

      public float b(BlockState blockStateIn, C_1557_ lightReaderIn, C_4675_ blockPosIn) {
         long i = blockPosIn.m_121878_();
         if (this.a) {
            float f = this.c.get(i);
            if (!Float.isNaN(f)) {
               return f;
            }
         }

         float f1 = blockStateIn.m_60792_(lightReaderIn, blockPosIn);
         if (this.a) {
            if (this.c.size() == 100) {
               this.c.removeFirstFloat();
            }

            this.c.put(i, f1);
         }

         return f1;
      }
   }

   protected static enum e {
      a(Direction.a, false),
      b(Direction.b, false),
      c(Direction.c, false),
      d(Direction.d, false),
      e(Direction.e, false),
      f(Direction.f, false),
      g(Direction.a, true),
      h(Direction.b, true),
      i(Direction.c, true),
      j(Direction.d, true),
      k(Direction.e, true),
      l(Direction.f, true);

      final int m;

      private e(final Direction facingIn, final boolean flip) {
         this.m = facingIn.d() + (flip ? ModelBlockRenderer.c.length : 0);
      }
   }
}
