package net.minecraft.src;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
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

public class C_4354_ implements C_79_ {
   public static final C_5265_ f_273897_ = C_5265_.m_340282_("textures/misc/enchanted_glint_entity.png");
   public static final C_5265_ f_273833_ = C_5265_.m_340282_("textures/misc/enchanted_glint_item.png");
   private static final Set<C_1381_> f_115094_ = Sets.newHashSet(new C_1381_[]{C_1394_.f_41852_});
   public static final int f_174221_ = 8;
   public static final int f_174222_ = 8;
   public static final int f_174218_ = 200;
   public static final float f_174219_ = 0.5F;
   public static final float f_174220_ = 0.75F;
   public static final float f_256734_ = 0.0078125F;
   private static final C_4536_ f_244324_ = C_4536_.m_340229_(C_5265_.m_340282_("trident"));
   public static final C_4536_ f_244055_ = C_4536_.m_340229_(C_5265_.m_340282_("trident_in_hand"));
   private static final C_4536_ f_244537_ = C_4536_.m_340229_(C_5265_.m_340282_("spyglass"));
   public static final C_4536_ f_243706_ = C_4536_.m_340229_(C_5265_.m_340282_("spyglass_in_hand"));
   private final C_3391_ f_265848_;
   private final C_4133_ f_115095_;
   private final C_4490_ f_115096_;
   private final C_3428_ f_115097_;
   private final C_4109_ f_174223_;
   public C_4535_ modelManager = null;
   private static boolean renderItemGui = false;

   public C_4354_(C_3391_ minecraftIn, C_4490_ textureManagerIn, C_4535_ modelManagerIn, C_3428_ itemColorsIn, C_4109_ blockEntityRendererIn) {
      this.f_265848_ = minecraftIn;
      this.f_115096_ = textureManagerIn;
      this.modelManager = modelManagerIn;
      if (Reflector.ForgeItemModelShaper_Constructor.exists()) {
         this.f_115095_ = (C_4133_)Reflector.newInstance(Reflector.ForgeItemModelShaper_Constructor, this.modelManager);
      } else {
         this.f_115095_ = new C_4133_(modelManagerIn);
      }

      this.f_174223_ = blockEntityRendererIn;

      for (C_1381_ item : C_256712_.f_257033_) {
         if (!f_115094_.contains(item)) {
            this.f_115095_.m_109396_(item, C_4536_.m_340229_(C_256712_.f_257033_.m_7981_(item)));
         }
      }

      this.f_115097_ = itemColorsIn;
   }

   public C_4133_ m_115103_() {
      return this.f_115095_;
   }

   public void m_115189_(C_4528_ modelIn, C_1391_ stack, int combinedLightIn, int combinedOverlayIn, C_3181_ matrixStackIn, C_3187_ bufferIn) {
      C_212974_ randomsource = C_212974_.m_216327_();
      long i = 42L;

      for (C_4687_ direction : C_4687_.f_122346_) {
         randomsource.m_188584_(42L);
         this.m_115162_(matrixStackIn, bufferIn, modelIn.m_213637_(null, direction, randomsource), stack, combinedLightIn, combinedOverlayIn);
      }

      randomsource.m_188584_(42L);
      this.m_115162_(matrixStackIn, bufferIn, modelIn.m_213637_(null, null, randomsource), stack, combinedLightIn, combinedOverlayIn);
   }

   public void m_115143_(
      C_1391_ itemStackIn,
      C_268388_ transformTypeIn,
      boolean leftHand,
      C_3181_ matrixStackIn,
      C_4139_ bufferIn,
      int combinedLightIn,
      int combinedOverlayIn,
      C_4528_ modelIn
   ) {
      if (!itemStackIn.m_41619_()) {
         matrixStackIn.m_85836_();
         boolean flag = transformTypeIn == C_268388_.GUI || transformTypeIn == C_268388_.GROUND || transformTypeIn == C_268388_.FIXED;
         if (flag) {
            if (itemStackIn.m_150930_(C_1394_.f_42713_)) {
               modelIn = this.f_115095_.m_109393_().m_119422_(f_244324_);
            } else if (itemStackIn.m_150930_(C_1394_.f_151059_)) {
               modelIn = this.f_115095_.m_109393_().m_119422_(f_244537_);
            }
         }

         if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
            modelIn = (C_4528_)Reflector.ForgeHooksClient_handleCameraTransforms.call(matrixStackIn, modelIn, transformTypeIn, leftHand);
         } else {
            modelIn.m_7442_().m_269404_(transformTypeIn).m_111763_(leftHand, matrixStackIn);
         }

         matrixStackIn.m_252880_(-0.5F, -0.5F, -0.5F);
         if (!modelIn.m_7521_() && (!itemStackIn.m_150930_(C_1394_.f_42713_) || flag)) {
            boolean flag1;
            if (transformTypeIn != C_268388_.GUI && !transformTypeIn.m_269069_() && itemStackIn.m_41720_() instanceof C_1325_ blockitem) {
               C_1706_ block = blockitem.m_40614_();
               flag1 = !(block instanceof C_1809_) && !(block instanceof C_1919_);
            } else {
               flag1 = true;
            }

            boolean forge = Reflector.ForgeHooksClient.exists();
            Iterable<C_4528_> renderPassModels = (Iterable<C_4528_>)(forge ? modelIn.getRenderPasses(itemStackIn, flag1) : new SingleIterable<>(modelIn));
            Iterable<C_4168_> renderTypes = (Iterable<C_4168_>)(forge
               ? modelIn.getRenderTypes(itemStackIn, flag1)
               : new SingleIterable<>(C_4130_.m_109279_(itemStackIn, flag1)));

            for (C_4528_ modelForge : renderPassModels) {
               modelIn = modelForge;

               for (C_4168_ rendertype : renderTypes) {
                  C_3187_ vertexconsumer;
                  if (m_285827_(itemStackIn) && itemStackIn.m_41790_()) {
                     C_3181_.C_3183_ posestack$pose = matrixStackIn.m_85850_().m_323639_();
                     if (transformTypeIn == C_268388_.GUI) {
                        C_252379_.m_253023_(posestack$pose.m_252922_(), 0.5F);
                     } else if (transformTypeIn.m_269069_()) {
                        C_252379_.m_253023_(posestack$pose.m_252922_(), 0.75F);
                     }

                     vertexconsumer = m_115180_(bufferIn, rendertype, posestack$pose);
                  } else if (flag1) {
                     vertexconsumer = m_115222_(bufferIn, rendertype, true, itemStackIn.m_41790_());
                  } else {
                     vertexconsumer = m_115211_(bufferIn, rendertype, true, itemStackIn.m_41790_());
                  }

                  if (Config.isCustomItems()) {
                     modelIn = CustomItems.getCustomItemModel(itemStackIn, modelIn, C_4219_.lastModelLocation, false);
                     C_4219_.lastModelLocation = null;
                  }

                  if (EmissiveTextures.isActive()) {
                     EmissiveTextures.beginRender();
                  }

                  this.m_115189_(modelIn, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, vertexconsumer);
                  if (EmissiveTextures.isActive()) {
                     if (EmissiveTextures.hasEmissive()) {
                        EmissiveTextures.beginRenderEmissive();
                        C_3187_ vertexBuilderEmissive = vertexconsumer instanceof VertexBuilderWrapper
                           ? ((VertexBuilderWrapper)vertexconsumer).getVertexBuilder()
                           : vertexconsumer;
                        this.m_115189_(modelIn, itemStackIn, C_4138_.MAX_BRIGHTNESS, combinedOverlayIn, matrixStackIn, vertexBuilderEmissive);
                        EmissiveTextures.endRenderEmissive();
                     }

                     EmissiveTextures.endRender();
                  }
               }
            }
         } else if (Reflector.MinecraftForge.exists()) {
            IClientItemExtensions.of(itemStackIn)
               .getCustomRenderer()
               .m_108829_(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
         } else {
            this.f_174223_.m_108829_(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
         }

         matrixStackIn.m_85849_();
      }
   }

   private static boolean m_285827_(C_1391_ itemStackIn) {
      return itemStackIn.m_204117_(C_140_.f_215866_) || itemStackIn.m_150930_(C_1394_.f_42524_);
   }

   public static C_3187_ m_115184_(C_4139_ bufferIn, C_4168_ renderTypeIn, boolean hasEffectIn) {
      if (Shaders.isShadowPass) {
         hasEffectIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         hasEffectIn = false;
      }

      return hasEffectIn ? C_3193_.m_86168_(bufferIn.m_6299_(C_4168_.m_110484_()), bufferIn.m_6299_(renderTypeIn)) : bufferIn.m_6299_(renderTypeIn);
   }

   public static C_3187_ m_115180_(C_4139_ bufferIn, C_4168_ renderTypeIn, C_3181_.C_3183_ entryIn) {
      return C_3193_.m_86168_(new C_3184_(bufferIn.m_6299_(C_4168_.m_110490_()), entryIn, 0.0078125F), bufferIn.m_6299_(renderTypeIn));
   }

   public static C_3187_ m_115211_(C_4139_ bufferIn, C_4168_ renderTypeIn, boolean isItemIn, boolean glintIn) {
      if (Shaders.isShadowPass) {
         glintIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         glintIn = false;
      }

      if (!glintIn) {
         return bufferIn.m_6299_(renderTypeIn);
      } else {
         return C_3391_.m_91085_() && renderTypeIn == C_4177_.m_110791_()
            ? C_3193_.m_86168_(bufferIn.m_6299_(C_4168_.m_110487_()), bufferIn.m_6299_(renderTypeIn))
            : C_3193_.m_86168_(bufferIn.m_6299_(isItemIn ? C_4168_.m_110490_() : C_4168_.m_110496_()), bufferIn.m_6299_(renderTypeIn));
      }
   }

   public static C_3187_ m_115222_(C_4139_ bufferIn, C_4168_ renderTypeIn, boolean isItemIn, boolean hasEffectIn) {
      if (Shaders.isShadowPass) {
         hasEffectIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         hasEffectIn = false;
      }

      return hasEffectIn
         ? C_3193_.m_86168_(bufferIn.m_6299_(isItemIn ? C_4168_.m_110490_() : C_4168_.m_110499_()), bufferIn.m_6299_(renderTypeIn))
         : bufferIn.m_6299_(renderTypeIn);
   }

   private void m_115162_(C_3181_ matrixStackIn, C_3187_ bufferIn, List<C_4196_> quadsIn, C_1391_ itemStackIn, int combinedLightIn, int combinedOverlayIn) {
      boolean flag = !itemStackIn.m_41619_();
      C_3181_.C_3183_ posestack$pose = matrixStackIn.m_85850_();
      boolean emissiveActive = EmissiveTextures.isActive();
      int listSize = quadsIn.size();
      int baseColorMul = listSize > 0 && Config.isCustomColors() ? CustomColors.getColorFromItemStack(itemStackIn, -1, -1) : -1;

      for (int ix = 0; ix < listSize; ix++) {
         C_4196_ bakedquad = (C_4196_)quadsIn.get(ix);
         if (emissiveActive) {
            bakedquad = EmissiveTextures.getEmissiveQuad(bakedquad);
            if (bakedquad == null) {
               continue;
            }
         }

         int i = baseColorMul;
         if (flag && bakedquad.m_111304_()) {
            i = this.f_115097_.m_92676_(itemStackIn, bakedquad.m_111305_());
            if (Config.isCustomColors()) {
               i = CustomColors.getColorFromItemStack(itemStackIn, bakedquad.m_111305_(), i);
            }
         }

         float f = (float)C_175_.m_13655_(i) / 255.0F;
         float f1 = (float)C_175_.m_13665_(i) / 255.0F;
         float f2 = (float)C_175_.m_13667_(i) / 255.0F;
         float f3 = (float)C_175_.m_13669_(i) / 255.0F;
         if (Reflector.ForgeHooksClient.exists()) {
            bufferIn.putBulkData(posestack$pose, bakedquad, f1, f2, f3, f, combinedLightIn, combinedOverlayIn, true);
         } else {
            bufferIn.m_85995_(posestack$pose, bakedquad, f1, f2, f3, f, combinedLightIn, combinedOverlayIn);
         }
      }
   }

   public C_4528_ m_174264_(C_1391_ stack, @Nullable C_1596_ worldIn, @Nullable C_524_ entityIn, int seedIn) {
      C_4528_ bakedmodel;
      if (stack.m_150930_(C_1394_.f_42713_)) {
         bakedmodel = this.f_115095_.m_109393_().m_119422_(f_244055_);
      } else if (stack.m_150930_(C_1394_.f_151059_)) {
         bakedmodel = this.f_115095_.m_109393_().m_119422_(f_243706_);
      } else {
         bakedmodel = this.f_115095_.m_109406_(stack);
      }

      C_3899_ clientlevel = worldIn instanceof C_3899_ ? (C_3899_)worldIn : null;
      C_4219_.lastModelLocation = null;
      C_4528_ bakedmodel1 = bakedmodel.m_7343_().m_173464_(bakedmodel, stack, clientlevel, entityIn, seedIn);
      if (Config.isCustomItems()) {
         bakedmodel1 = CustomItems.getCustomItemModel(stack, bakedmodel1, C_4219_.lastModelLocation, true);
      }

      return bakedmodel1 == null ? this.f_115095_.m_109393_().m_119409_() : bakedmodel1;
   }

   public void m_269128_(
      C_1391_ itemStackIn,
      C_268388_ contextIn,
      int combinedLightIn,
      int combinedOverlayIn,
      C_3181_ matrixStackIn,
      C_4139_ bufferIn,
      @Nullable C_1596_ worldIn,
      int seedIn
   ) {
      this.m_269491_(null, itemStackIn, contextIn, false, matrixStackIn, bufferIn, worldIn, combinedLightIn, combinedOverlayIn, seedIn);
   }

   public void m_269491_(
      @Nullable C_524_ livingEntityIn,
      C_1391_ itemStackIn,
      C_268388_ contextIn,
      boolean leftHand,
      C_3181_ matrixStackIn,
      C_4139_ bufferIn,
      @Nullable C_1596_ worldIn,
      int combinedLightIn,
      int combinedOverlayIn,
      int seedIn
   ) {
      if (!itemStackIn.m_41619_()) {
         C_4528_ bakedmodel = this.m_174264_(itemStackIn, worldIn, livingEntityIn, seedIn);
         this.m_115143_(itemStackIn, contextIn, leftHand, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, bakedmodel);
      }
   }

   public void m_6213_(C_77_ resourceManager) {
      this.f_115095_.m_109403_();
   }

   public static boolean isRenderItemGui() {
      return renderItemGui;
   }

   public static void setRenderItemGui(boolean renderItemGui) {
      C_4354_.renderItemGui = renderItemGui;
   }

   public C_4109_ getBlockEntityRenderer() {
      return this.f_174223_;
   }
}
