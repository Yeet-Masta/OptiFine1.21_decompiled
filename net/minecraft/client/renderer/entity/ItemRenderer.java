package net.minecraft.client.renderer.entity;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.math.MatrixUtil;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomItems;
import net.optifine.EmissiveTextures;
import net.optifine.reflect.Reflector;
import net.optifine.render.VertexBuilderWrapper;
import net.optifine.shaders.Shaders;
import net.optifine.util.SingleIterable;

public class ItemRenderer implements ResourceManagerReloadListener {
   public static final net.minecraft.resources.ResourceLocation f_273897_ = net.minecraft.resources.ResourceLocation.m_340282_(
      "textures/misc/enchanted_glint_entity.png"
   );
   public static final net.minecraft.resources.ResourceLocation f_273833_ = net.minecraft.resources.ResourceLocation.m_340282_(
      "textures/misc/enchanted_glint_item.png"
   );
   private static final Set<Item> f_115094_ = Sets.newHashSet(new Item[]{Items.f_41852_});
   public static final int f_174221_ = 8;
   public static final int f_174222_ = 8;
   public static final int f_174218_ = 200;
   public static final float f_174219_ = 0.5F;
   public static final float f_174220_ = 0.75F;
   public static final float f_256734_ = 0.0078125F;
   private static final ModelResourceLocation f_244324_ = ModelResourceLocation.m_340229_(net.minecraft.resources.ResourceLocation.m_340282_("trident"));
   public static final ModelResourceLocation f_244055_ = ModelResourceLocation.m_340229_(net.minecraft.resources.ResourceLocation.m_340282_("trident_in_hand"));
   private static final ModelResourceLocation f_244537_ = ModelResourceLocation.m_340229_(net.minecraft.resources.ResourceLocation.m_340282_("spyglass"));
   public static final ModelResourceLocation f_243706_ = ModelResourceLocation.m_340229_(net.minecraft.resources.ResourceLocation.m_340282_("spyglass_in_hand"));
   private final Minecraft f_265848_;
   private final ItemModelShaper f_115095_;
   private final net.minecraft.client.renderer.texture.TextureManager f_115096_;
   private final ItemColors f_115097_;
   private final net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer f_174223_;
   public ModelManager modelManager = null;
   private static boolean renderItemGui = false;

   public ItemRenderer(
      Minecraft minecraftIn,
      net.minecraft.client.renderer.texture.TextureManager textureManagerIn,
      ModelManager modelManagerIn,
      ItemColors itemColorsIn,
      net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer blockEntityRendererIn
   ) {
      this.f_265848_ = minecraftIn;
      this.f_115096_ = textureManagerIn;
      this.modelManager = modelManagerIn;
      if (Reflector.ForgeItemModelShaper_Constructor.exists()) {
         this.f_115095_ = (ItemModelShaper)Reflector.newInstance(Reflector.ForgeItemModelShaper_Constructor, this.modelManager);
      } else {
         this.f_115095_ = new ItemModelShaper(modelManagerIn);
      }

      this.f_174223_ = blockEntityRendererIn;

      for (Item item : BuiltInRegistries.f_257033_) {
         if (!f_115094_.contains(item)) {
            this.f_115095_.m_109396_(item, ModelResourceLocation.m_340229_(BuiltInRegistries.f_257033_.m_7981_(item)));
         }
      }

      this.f_115097_ = itemColorsIn;
   }

   public ItemModelShaper m_115103_() {
      return this.f_115095_;
   }

   public void m_115189_(
      net.minecraft.client.resources.model.BakedModel modelIn,
      ItemStack stack,
      int combinedLightIn,
      int combinedOverlayIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      com.mojang.blaze3d.vertex.VertexConsumer bufferIn
   ) {
      RandomSource randomsource = RandomSource.m_216327_();
      long i = 42L;

      for (net.minecraft.core.Direction direction : net.minecraft.core.Direction.f_122346_) {
         randomsource.m_188584_(42L);
         this.m_115162_(matrixStackIn, bufferIn, modelIn.m_213637_(null, direction, randomsource), stack, combinedLightIn, combinedOverlayIn);
      }

      randomsource.m_188584_(42L);
      this.m_115162_(matrixStackIn, bufferIn, modelIn.m_213637_(null, null, randomsource), stack, combinedLightIn, combinedOverlayIn);
   }

   public void m_115143_(
      ItemStack itemStackIn,
      ItemDisplayContext transformTypeIn,
      boolean leftHand,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int combinedLightIn,
      int combinedOverlayIn,
      net.minecraft.client.resources.model.BakedModel modelIn
   ) {
      if (!itemStackIn.m_41619_()) {
         matrixStackIn.m_85836_();
         boolean flag = transformTypeIn == ItemDisplayContext.GUI
            || transformTypeIn == ItemDisplayContext.GROUND
            || transformTypeIn == ItemDisplayContext.FIXED;
         if (flag) {
            if (itemStackIn.m_150930_(Items.f_42713_)) {
               modelIn = this.f_115095_.m_109393_().m_119422_(f_244324_);
            } else if (itemStackIn.m_150930_(Items.f_151059_)) {
               modelIn = this.f_115095_.m_109393_().m_119422_(f_244537_);
            }
         }

         if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
            modelIn = (net.minecraft.client.resources.model.BakedModel)Reflector.ForgeHooksClient_handleCameraTransforms
               .call(matrixStackIn, modelIn, transformTypeIn, leftHand);
         } else {
            modelIn.m_7442_().m_269404_(transformTypeIn).m_111763_(leftHand, matrixStackIn);
         }

         matrixStackIn.m_252880_(-0.5F, -0.5F, -0.5F);
         if (!modelIn.m_7521_() && (!itemStackIn.m_150930_(Items.f_42713_) || flag)) {
            boolean flag1;
            if (transformTypeIn != ItemDisplayContext.GUI && !transformTypeIn.m_269069_() && itemStackIn.m_41720_() instanceof BlockItem blockitem) {
               Block block = blockitem.m_40614_();
               flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
            } else {
               flag1 = true;
            }

            boolean forge = Reflector.ForgeHooksClient.exists();
            Iterable<net.minecraft.client.resources.model.BakedModel> renderPassModels = (Iterable<net.minecraft.client.resources.model.BakedModel>)(forge
               ? modelIn.getRenderPasses(itemStackIn, flag1)
               : new SingleIterable<>(modelIn));
            Iterable<net.minecraft.client.renderer.RenderType> renderTypes = (Iterable<net.minecraft.client.renderer.RenderType>)(forge
               ? modelIn.getRenderTypes(itemStackIn, flag1)
               : new SingleIterable<>(ItemBlockRenderTypes.m_109279_(itemStackIn, flag1)));

            for (net.minecraft.client.resources.model.BakedModel modelForge : renderPassModels) {
               modelIn = modelForge;

               for (net.minecraft.client.renderer.RenderType rendertype : renderTypes) {
                  com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer;
                  if (m_285827_(itemStackIn) && itemStackIn.m_41790_()) {
                     com.mojang.blaze3d.vertex.PoseStack.Pose posestack$pose = matrixStackIn.m_85850_().m_323639_();
                     if (transformTypeIn == ItemDisplayContext.GUI) {
                        MatrixUtil.m_253023_(posestack$pose.m_252922_(), 0.5F);
                     } else if (transformTypeIn.m_269069_()) {
                        MatrixUtil.m_253023_(posestack$pose.m_252922_(), 0.75F);
                     }

                     vertexconsumer = m_115180_(bufferIn, rendertype, posestack$pose);
                  } else if (flag1) {
                     vertexconsumer = m_115222_(bufferIn, rendertype, true, itemStackIn.m_41790_());
                  } else {
                     vertexconsumer = m_115211_(bufferIn, rendertype, true, itemStackIn.m_41790_());
                  }

                  if (Config.isCustomItems()) {
                     modelIn = CustomItems.getCustomItemModel(
                        itemStackIn, modelIn, net.minecraft.client.renderer.block.model.ItemOverrides.lastModelLocation, false
                     );
                     net.minecraft.client.renderer.block.model.ItemOverrides.lastModelLocation = null;
                  }

                  if (EmissiveTextures.isActive()) {
                     EmissiveTextures.beginRender();
                  }

                  this.m_115189_(modelIn, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, vertexconsumer);
                  if (EmissiveTextures.isActive()) {
                     if (EmissiveTextures.hasEmissive()) {
                        EmissiveTextures.beginRenderEmissive();
                        com.mojang.blaze3d.vertex.VertexConsumer vertexBuilderEmissive = vertexconsumer instanceof VertexBuilderWrapper
                           ? ((VertexBuilderWrapper)vertexconsumer).getVertexBuilder()
                           : vertexconsumer;
                        this.m_115189_(
                           modelIn,
                           itemStackIn,
                           net.minecraft.client.renderer.LightTexture.MAX_BRIGHTNESS,
                           combinedOverlayIn,
                           matrixStackIn,
                           vertexBuilderEmissive
                        );
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

   private static boolean m_285827_(ItemStack itemStackIn) {
      return itemStackIn.m_204117_(ItemTags.f_215866_) || itemStackIn.m_150930_(Items.f_42524_);
   }

   public static com.mojang.blaze3d.vertex.VertexConsumer m_115184_(
      net.minecraft.client.renderer.MultiBufferSource bufferIn, net.minecraft.client.renderer.RenderType renderTypeIn, boolean hasEffectIn
   ) {
      if (Shaders.isShadowPass) {
         hasEffectIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         hasEffectIn = false;
      }

      return hasEffectIn
         ? com.mojang.blaze3d.vertex.VertexMultiConsumer.m_86168_(
            bufferIn.m_6299_(net.minecraft.client.renderer.RenderType.m_110484_()), bufferIn.m_6299_(renderTypeIn)
         )
         : bufferIn.m_6299_(renderTypeIn);
   }

   public static com.mojang.blaze3d.vertex.VertexConsumer m_115180_(
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      net.minecraft.client.renderer.RenderType renderTypeIn,
      com.mojang.blaze3d.vertex.PoseStack.Pose entryIn
   ) {
      return com.mojang.blaze3d.vertex.VertexMultiConsumer.m_86168_(
         new SheetedDecalTextureGenerator(bufferIn.m_6299_(net.minecraft.client.renderer.RenderType.m_110490_()), entryIn, 0.0078125F),
         bufferIn.m_6299_(renderTypeIn)
      );
   }

   public static com.mojang.blaze3d.vertex.VertexConsumer m_115211_(
      net.minecraft.client.renderer.MultiBufferSource bufferIn, net.minecraft.client.renderer.RenderType renderTypeIn, boolean isItemIn, boolean glintIn
   ) {
      if (Shaders.isShadowPass) {
         glintIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         glintIn = false;
      }

      if (!glintIn) {
         return bufferIn.m_6299_(renderTypeIn);
      } else {
         return Minecraft.m_91085_() && renderTypeIn == Sheets.m_110791_()
            ? com.mojang.blaze3d.vertex.VertexMultiConsumer.m_86168_(
               bufferIn.m_6299_(net.minecraft.client.renderer.RenderType.m_110487_()), bufferIn.m_6299_(renderTypeIn)
            )
            : com.mojang.blaze3d.vertex.VertexMultiConsumer.m_86168_(
               bufferIn.m_6299_(isItemIn ? net.minecraft.client.renderer.RenderType.m_110490_() : net.minecraft.client.renderer.RenderType.m_110496_()),
               bufferIn.m_6299_(renderTypeIn)
            );
      }
   }

   public static com.mojang.blaze3d.vertex.VertexConsumer m_115222_(
      net.minecraft.client.renderer.MultiBufferSource bufferIn, net.minecraft.client.renderer.RenderType renderTypeIn, boolean isItemIn, boolean hasEffectIn
   ) {
      if (Shaders.isShadowPass) {
         hasEffectIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         hasEffectIn = false;
      }

      return hasEffectIn
         ? com.mojang.blaze3d.vertex.VertexMultiConsumer.m_86168_(
            bufferIn.m_6299_(isItemIn ? net.minecraft.client.renderer.RenderType.m_110490_() : net.minecraft.client.renderer.RenderType.m_110499_()),
            bufferIn.m_6299_(renderTypeIn)
         )
         : bufferIn.m_6299_(renderTypeIn);
   }

   private void m_115162_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      com.mojang.blaze3d.vertex.VertexConsumer bufferIn,
      List<net.minecraft.client.renderer.block.model.BakedQuad> quadsIn,
      ItemStack itemStackIn,
      int combinedLightIn,
      int combinedOverlayIn
   ) {
      boolean flag = !itemStackIn.m_41619_();
      com.mojang.blaze3d.vertex.PoseStack.Pose posestack$pose = matrixStackIn.m_85850_();
      boolean emissiveActive = EmissiveTextures.isActive();
      int listSize = quadsIn.size();
      int baseColorMul = listSize > 0 && Config.isCustomColors() ? CustomColors.getColorFromItemStack(itemStackIn, -1, -1) : -1;

      for (int ix = 0; ix < listSize; ix++) {
         net.minecraft.client.renderer.block.model.BakedQuad bakedquad = (net.minecraft.client.renderer.block.model.BakedQuad)quadsIn.get(ix);
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

         float f = (float)ARGB32.m_13655_(i) / 255.0F;
         float f1 = (float)ARGB32.m_13665_(i) / 255.0F;
         float f2 = (float)ARGB32.m_13667_(i) / 255.0F;
         float f3 = (float)ARGB32.m_13669_(i) / 255.0F;
         if (Reflector.ForgeHooksClient.exists()) {
            bufferIn.putBulkData(posestack$pose, bakedquad, f1, f2, f3, f, combinedLightIn, combinedOverlayIn, true);
         } else {
            bufferIn.m_85995_(posestack$pose, bakedquad, f1, f2, f3, f, combinedLightIn, combinedOverlayIn);
         }
      }
   }

   public net.minecraft.client.resources.model.BakedModel m_174264_(ItemStack stack, @Nullable Level worldIn, @Nullable LivingEntity entityIn, int seedIn) {
      net.minecraft.client.resources.model.BakedModel bakedmodel;
      if (stack.m_150930_(Items.f_42713_)) {
         bakedmodel = this.f_115095_.m_109393_().m_119422_(f_244055_);
      } else if (stack.m_150930_(Items.f_151059_)) {
         bakedmodel = this.f_115095_.m_109393_().m_119422_(f_243706_);
      } else {
         bakedmodel = this.f_115095_.m_109406_(stack);
      }

      net.minecraft.client.multiplayer.ClientLevel clientlevel = worldIn instanceof net.minecraft.client.multiplayer.ClientLevel
         ? (net.minecraft.client.multiplayer.ClientLevel)worldIn
         : null;
      net.minecraft.client.renderer.block.model.ItemOverrides.lastModelLocation = null;
      net.minecraft.client.resources.model.BakedModel bakedmodel1 = bakedmodel.m_7343_().m_173464_(bakedmodel, stack, clientlevel, entityIn, seedIn);
      if (Config.isCustomItems()) {
         bakedmodel1 = CustomItems.getCustomItemModel(stack, bakedmodel1, net.minecraft.client.renderer.block.model.ItemOverrides.lastModelLocation, true);
      }

      return bakedmodel1 == null ? this.f_115095_.m_109393_().m_119409_() : bakedmodel1;
   }

   public void m_269128_(
      ItemStack itemStackIn,
      ItemDisplayContext contextIn,
      int combinedLightIn,
      int combinedOverlayIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      @Nullable Level worldIn,
      int seedIn
   ) {
      this.m_269491_(null, itemStackIn, contextIn, false, matrixStackIn, bufferIn, worldIn, combinedLightIn, combinedOverlayIn, seedIn);
   }

   public void m_269491_(
      @Nullable LivingEntity livingEntityIn,
      ItemStack itemStackIn,
      ItemDisplayContext contextIn,
      boolean leftHand,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      @Nullable Level worldIn,
      int combinedLightIn,
      int combinedOverlayIn,
      int seedIn
   ) {
      if (!itemStackIn.m_41619_()) {
         net.minecraft.client.resources.model.BakedModel bakedmodel = this.m_174264_(itemStackIn, worldIn, livingEntityIn, seedIn);
         this.m_115143_(itemStackIn, contextIn, leftHand, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, bakedmodel);
      }
   }

   public void m_6213_(ResourceManager resourceManager) {
      this.f_115095_.m_109403_();
   }

   public static boolean isRenderItemGui() {
      return renderItemGui;
   }

   public static void setRenderItemGui(boolean renderItemGui) {
      net.minecraft.client.renderer.entity.ItemRenderer.renderItemGui = renderItemGui;
   }

   public net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer getBlockEntityRenderer() {
      return this.f_174223_;
   }
}
