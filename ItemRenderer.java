import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.src.C_1325_;
import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1394_;
import net.minecraft.src.C_140_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1809_;
import net.minecraft.src.C_1919_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_252379_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_268388_;
import net.minecraft.src.C_3184_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3428_;
import net.minecraft.src.C_4130_;
import net.minecraft.src.C_4133_;
import net.minecraft.src.C_4177_;
import net.minecraft.src.C_4535_;
import net.minecraft.src.C_4536_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_79_;
import net.minecraft.src.C_174_.C_175_;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomItems;
import net.optifine.EmissiveTextures;
import net.optifine.reflect.Reflector;
import net.optifine.render.VertexBuilderWrapper;
import net.optifine.shaders.Shaders;
import net.optifine.util.SingleIterable;

public class ItemRenderer implements C_79_ {
   public static final ResourceLocation a = ResourceLocation.b("textures/misc/enchanted_glint_entity.png");
   public static final ResourceLocation b = ResourceLocation.b("textures/misc/enchanted_glint_item.png");
   private static final Set<C_1381_> k = Sets.newHashSet(new C_1381_[]{C_1394_.f_41852_});
   public static final int c = 8;
   public static final int d = 8;
   public static final int e = 200;
   public static final float f = 0.5F;
   public static final float g = 0.75F;
   public static final float h = 0.0078125F;
   private static final C_4536_ l = C_4536_.a(ResourceLocation.b("trident"));
   public static final C_4536_ i = C_4536_.a(ResourceLocation.b("trident_in_hand"));
   private static final C_4536_ m = C_4536_.a(ResourceLocation.b("spyglass"));
   public static final C_4536_ j = C_4536_.a(ResourceLocation.b("spyglass_in_hand"));
   private final C_3391_ n;
   private final C_4133_ o;
   private final TextureManager p;
   private final C_3428_ q;
   private final BlockEntityWithoutLevelRenderer r;
   public C_4535_ modelManager = null;
   private static boolean renderItemGui = false;

   public ItemRenderer(
      C_3391_ minecraftIn, TextureManager textureManagerIn, C_4535_ modelManagerIn, C_3428_ itemColorsIn, BlockEntityWithoutLevelRenderer blockEntityRendererIn
   ) {
      this.n = minecraftIn;
      this.p = textureManagerIn;
      this.modelManager = modelManagerIn;
      if (Reflector.ForgeItemModelShaper_Constructor.exists()) {
         this.o = (C_4133_)Reflector.newInstance(Reflector.ForgeItemModelShaper_Constructor, this.modelManager);
      } else {
         this.o = new C_4133_(modelManagerIn);
      }

      this.r = blockEntityRendererIn;

      for (C_1381_ item : C_256712_.f_257033_) {
         if (!k.contains(item)) {
            this.o.m_109396_(item, C_4536_.a(C_256712_.f_257033_.b(item)));
         }
      }

      this.q = itemColorsIn;
   }

   public C_4133_ a() {
      return this.o;
   }

   public void a(BakedModel modelIn, C_1391_ stack, int combinedLightIn, int combinedOverlayIn, PoseStack matrixStackIn, VertexConsumer bufferIn) {
      C_212974_ randomsource = C_212974_.m_216327_();
      long i = 42L;

      for (Direction direction : Direction.r) {
         randomsource.m_188584_(42L);
         this.a(matrixStackIn, bufferIn, modelIn.a(null, direction, randomsource), stack, combinedLightIn, combinedOverlayIn);
      }

      randomsource.m_188584_(42L);
      this.a(matrixStackIn, bufferIn, modelIn.a(null, null, randomsource), stack, combinedLightIn, combinedOverlayIn);
   }

   public void a(
      C_1391_ itemStackIn,
      C_268388_ transformTypeIn,
      boolean leftHand,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int combinedLightIn,
      int combinedOverlayIn,
      BakedModel modelIn
   ) {
      if (!itemStackIn.m_41619_()) {
         matrixStackIn.a();
         boolean flag = transformTypeIn == C_268388_.GUI || transformTypeIn == C_268388_.GROUND || transformTypeIn == C_268388_.FIXED;
         if (flag) {
            if (itemStackIn.m_150930_(C_1394_.f_42713_)) {
               modelIn = this.o.m_109393_().a(l);
            } else if (itemStackIn.m_150930_(C_1394_.f_151059_)) {
               modelIn = this.o.m_109393_().a(m);
            }
         }

         if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
            modelIn = (BakedModel)Reflector.ForgeHooksClient_handleCameraTransforms.call(matrixStackIn, modelIn, transformTypeIn, leftHand);
         } else {
            modelIn.f().m_269404_(transformTypeIn).a(leftHand, matrixStackIn);
         }

         matrixStackIn.a(-0.5F, -0.5F, -0.5F);
         if (!modelIn.d() && (!itemStackIn.m_150930_(C_1394_.f_42713_) || flag)) {
            boolean flag1;
            if (transformTypeIn != C_268388_.GUI && !transformTypeIn.m_269069_() && itemStackIn.m_41720_() instanceof C_1325_ blockitem) {
               C_1706_ block = blockitem.m_40614_();
               flag1 = !(block instanceof C_1809_) && !(block instanceof C_1919_);
            } else {
               flag1 = true;
            }

            boolean forge = Reflector.ForgeHooksClient.exists();
            Iterable<BakedModel> renderPassModels = (Iterable<BakedModel>)(forge ? modelIn.getRenderPasses(itemStackIn, flag1) : new SingleIterable<>(modelIn));
            Iterable<RenderType> renderTypes = (Iterable<RenderType>)(forge
               ? modelIn.getRenderTypes(itemStackIn, flag1)
               : new SingleIterable<>(C_4130_.a(itemStackIn, flag1)));

            for (BakedModel modelForge : renderPassModels) {
               modelIn = modelForge;

               for (RenderType rendertype : renderTypes) {
                  VertexConsumer vertexconsumer;
                  if (a(itemStackIn) && itemStackIn.m_41790_()) {
                     PoseStack.a posestack$pose = matrixStackIn.c().c();
                     if (transformTypeIn == C_268388_.GUI) {
                        C_252379_.m_253023_(posestack$pose.a(), 0.5F);
                     } else if (transformTypeIn.m_269069_()) {
                        C_252379_.m_253023_(posestack$pose.a(), 0.75F);
                     }

                     vertexconsumer = a(bufferIn, rendertype, posestack$pose);
                  } else if (flag1) {
                     vertexconsumer = b(bufferIn, rendertype, true, itemStackIn.m_41790_());
                  } else {
                     vertexconsumer = a(bufferIn, rendertype, true, itemStackIn.m_41790_());
                  }

                  if (Config.isCustomItems()) {
                     modelIn = CustomItems.getCustomItemModel(itemStackIn, modelIn, ItemOverrides.lastModelLocation, false);
                     ItemOverrides.lastModelLocation = null;
                  }

                  if (EmissiveTextures.isActive()) {
                     EmissiveTextures.beginRender();
                  }

                  this.a(modelIn, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, vertexconsumer);
                  if (EmissiveTextures.isActive()) {
                     if (EmissiveTextures.hasEmissive()) {
                        EmissiveTextures.beginRenderEmissive();
                        VertexConsumer vertexBuilderEmissive = vertexconsumer instanceof VertexBuilderWrapper
                           ? ((VertexBuilderWrapper)vertexconsumer).getVertexBuilder()
                           : vertexconsumer;
                        this.a(modelIn, itemStackIn, LightTexture.MAX_BRIGHTNESS, combinedOverlayIn, matrixStackIn, vertexBuilderEmissive);
                        EmissiveTextures.endRenderEmissive();
                     }

                     EmissiveTextures.endRender();
                  }
               }
            }
         } else if (Reflector.MinecraftForge.exists()) {
            IClientItemExtensions.of(itemStackIn)
               .getCustomRenderer()
               .a(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
         } else {
            this.r.a(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
         }

         matrixStackIn.b();
      }
   }

   private static boolean a(C_1391_ itemStackIn) {
      return itemStackIn.m_204117_(C_140_.f_215866_) || itemStackIn.m_150930_(C_1394_.f_42524_);
   }

   public static VertexConsumer a(MultiBufferSource bufferIn, RenderType renderTypeIn, boolean hasEffectIn) {
      if (Shaders.isShadowPass) {
         hasEffectIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         hasEffectIn = false;
      }

      return hasEffectIn ? VertexMultiConsumer.a(bufferIn.getBuffer(RenderType.j()), bufferIn.getBuffer(renderTypeIn)) : bufferIn.getBuffer(renderTypeIn);
   }

   public static VertexConsumer a(MultiBufferSource bufferIn, RenderType renderTypeIn, PoseStack.a entryIn) {
      return VertexMultiConsumer.a(new C_3184_(bufferIn.getBuffer(RenderType.l()), entryIn, 0.0078125F), bufferIn.getBuffer(renderTypeIn));
   }

   public static VertexConsumer a(MultiBufferSource bufferIn, RenderType renderTypeIn, boolean isItemIn, boolean glintIn) {
      if (Shaders.isShadowPass) {
         glintIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         glintIn = false;
      }

      if (!glintIn) {
         return bufferIn.getBuffer(renderTypeIn);
      } else {
         return C_3391_.m_91085_() && renderTypeIn == C_4177_.j()
            ? VertexMultiConsumer.a(bufferIn.getBuffer(RenderType.k()), bufferIn.getBuffer(renderTypeIn))
            : VertexMultiConsumer.a(bufferIn.getBuffer(isItemIn ? RenderType.l() : RenderType.m()), bufferIn.getBuffer(renderTypeIn));
      }
   }

   public static VertexConsumer b(MultiBufferSource bufferIn, RenderType renderTypeIn, boolean isItemIn, boolean hasEffectIn) {
      if (Shaders.isShadowPass) {
         hasEffectIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         hasEffectIn = false;
      }

      return hasEffectIn
         ? VertexMultiConsumer.a(bufferIn.getBuffer(isItemIn ? RenderType.l() : RenderType.n()), bufferIn.getBuffer(renderTypeIn))
         : bufferIn.getBuffer(renderTypeIn);
   }

   private void a(PoseStack matrixStackIn, VertexConsumer bufferIn, List<BakedQuad> quadsIn, C_1391_ itemStackIn, int combinedLightIn, int combinedOverlayIn) {
      boolean flag = !itemStackIn.m_41619_();
      PoseStack.a posestack$pose = matrixStackIn.c();
      boolean emissiveActive = EmissiveTextures.isActive();
      int listSize = quadsIn.size();
      int baseColorMul = listSize > 0 && Config.isCustomColors() ? CustomColors.getColorFromItemStack(itemStackIn, -1, -1) : -1;

      for (int ix = 0; ix < listSize; ix++) {
         BakedQuad bakedquad = (BakedQuad)quadsIn.get(ix);
         if (emissiveActive) {
            bakedquad = EmissiveTextures.getEmissiveQuad(bakedquad);
            if (bakedquad == null) {
               continue;
            }
         }

         int i = baseColorMul;
         if (flag && bakedquad.c()) {
            i = this.q.m_92676_(itemStackIn, bakedquad.d());
            if (Config.isCustomColors()) {
               i = CustomColors.getColorFromItemStack(itemStackIn, bakedquad.d(), i);
            }
         }

         float f = (float)C_175_.m_13655_(i) / 255.0F;
         float f1 = (float)C_175_.m_13665_(i) / 255.0F;
         float f2 = (float)C_175_.m_13667_(i) / 255.0F;
         float f3 = (float)C_175_.m_13669_(i) / 255.0F;
         if (Reflector.ForgeHooksClient.exists()) {
            bufferIn.putBulkData(posestack$pose, bakedquad, f1, f2, f3, f, combinedLightIn, combinedOverlayIn, true);
         } else {
            bufferIn.a(posestack$pose, bakedquad, f1, f2, f3, f, combinedLightIn, combinedOverlayIn);
         }
      }
   }

   public BakedModel a(C_1391_ stack, @Nullable C_1596_ worldIn, @Nullable C_524_ entityIn, int seedIn) {
      BakedModel bakedmodel;
      if (stack.m_150930_(C_1394_.f_42713_)) {
         bakedmodel = this.o.m_109393_().a(i);
      } else if (stack.m_150930_(C_1394_.f_151059_)) {
         bakedmodel = this.o.m_109393_().a(j);
      } else {
         bakedmodel = this.o.a(stack);
      }

      ClientLevel clientlevel = worldIn instanceof ClientLevel ? (ClientLevel)worldIn : null;
      ItemOverrides.lastModelLocation = null;
      BakedModel bakedmodel1 = bakedmodel.g().a(bakedmodel, stack, clientlevel, entityIn, seedIn);
      if (Config.isCustomItems()) {
         bakedmodel1 = CustomItems.getCustomItemModel(stack, bakedmodel1, ItemOverrides.lastModelLocation, true);
      }

      return bakedmodel1 == null ? this.o.m_109393_().a() : bakedmodel1;
   }

   public void a(
      C_1391_ itemStackIn,
      C_268388_ contextIn,
      int combinedLightIn,
      int combinedOverlayIn,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      @Nullable C_1596_ worldIn,
      int seedIn
   ) {
      this.a(null, itemStackIn, contextIn, false, matrixStackIn, bufferIn, worldIn, combinedLightIn, combinedOverlayIn, seedIn);
   }

   public void a(
      @Nullable C_524_ livingEntityIn,
      C_1391_ itemStackIn,
      C_268388_ contextIn,
      boolean leftHand,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      @Nullable C_1596_ worldIn,
      int combinedLightIn,
      int combinedOverlayIn,
      int seedIn
   ) {
      if (!itemStackIn.m_41619_()) {
         BakedModel bakedmodel = this.a(itemStackIn, worldIn, livingEntityIn, seedIn);
         this.a(itemStackIn, contextIn, leftHand, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, bakedmodel);
      }
   }

   public void m_6213_(C_77_ resourceManager) {
      this.o.m_109403_();
   }

   public static boolean isRenderItemGui() {
      return renderItemGui;
   }

   public static void setRenderItemGui(boolean renderItemGui) {
      ItemRenderer.renderItemGui = renderItemGui;
   }

   public BlockEntityWithoutLevelRenderer getBlockEntityRenderer() {
      return this.r;
   }
}
