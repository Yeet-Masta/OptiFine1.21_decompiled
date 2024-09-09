package net.minecraft.client.renderer.entity;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.mojang.math.MatrixUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.block.state.BlockState;
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
   public static final ResourceLocation f_273897_ = ResourceLocation.m_340282_("textures/misc/enchanted_glint_entity.png");
   public static final ResourceLocation f_273833_ = ResourceLocation.m_340282_("textures/misc/enchanted_glint_item.png");
   private static final Set f_115094_;
   public static final int f_174221_ = 8;
   public static final int f_174222_ = 8;
   public static final int f_174218_ = 200;
   public static final float f_174219_ = 0.5F;
   public static final float f_174220_ = 0.75F;
   public static final float f_256734_ = 0.0078125F;
   private static final ModelResourceLocation f_244324_;
   public static final ModelResourceLocation f_244055_;
   private static final ModelResourceLocation f_244537_;
   public static final ModelResourceLocation f_243706_;
   private final Minecraft f_265848_;
   private final ItemModelShaper f_115095_;
   private final TextureManager f_115096_;
   private final ItemColors f_115097_;
   private final BlockEntityWithoutLevelRenderer f_174223_;
   public ModelManager modelManager = null;
   private static boolean renderItemGui;

   public ItemRenderer(Minecraft minecraftIn, TextureManager textureManagerIn, ModelManager modelManagerIn, ItemColors itemColorsIn, BlockEntityWithoutLevelRenderer blockEntityRendererIn) {
      this.f_265848_ = minecraftIn;
      this.f_115096_ = textureManagerIn;
      this.modelManager = modelManagerIn;
      if (Reflector.ForgeItemModelShaper_Constructor.exists()) {
         this.f_115095_ = (ItemModelShaper)Reflector.newInstance(Reflector.ForgeItemModelShaper_Constructor, this.modelManager);
      } else {
         this.f_115095_ = new ItemModelShaper(modelManagerIn);
      }

      this.f_174223_ = blockEntityRendererIn;
      Iterator var6 = BuiltInRegistries.f_257033_.iterator();

      while(var6.hasNext()) {
         Item item = (Item)var6.next();
         if (!f_115094_.contains(item)) {
            this.f_115095_.m_109396_(item, ModelResourceLocation.m_340229_(BuiltInRegistries.f_257033_.m_7981_(item)));
         }
      }

      this.f_115097_ = itemColorsIn;
   }

   public ItemModelShaper m_115103_() {
      return this.f_115095_;
   }

   public void m_115189_(BakedModel modelIn, ItemStack stack, int combinedLightIn, int combinedOverlayIn, PoseStack matrixStackIn, VertexConsumer bufferIn) {
      RandomSource randomsource = RandomSource.m_216327_();
      long i = 42L;
      Direction[] var10 = Direction.f_122346_;
      int var11 = var10.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         Direction direction = var10[var12];
         randomsource.m_188584_(42L);
         this.m_115162_(matrixStackIn, bufferIn, modelIn.m_213637_((BlockState)null, direction, randomsource), stack, combinedLightIn, combinedOverlayIn);
      }

      randomsource.m_188584_(42L);
      this.m_115162_(matrixStackIn, bufferIn, modelIn.m_213637_((BlockState)null, (Direction)null, randomsource), stack, combinedLightIn, combinedOverlayIn);
   }

   public void m_115143_(ItemStack itemStackIn, ItemDisplayContext transformTypeIn, boolean leftHand, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, BakedModel modelIn) {
      if (!itemStackIn.m_41619_()) {
         matrixStackIn.m_85836_();
         boolean flag = transformTypeIn == ItemDisplayContext.GUI || transformTypeIn == ItemDisplayContext.GROUND || transformTypeIn == ItemDisplayContext.FIXED;
         if (flag) {
            if (itemStackIn.m_150930_(Items.f_42713_)) {
               modelIn = this.f_115095_.m_109393_().m_119422_(f_244324_);
            } else if (itemStackIn.m_150930_(Items.f_151059_)) {
               modelIn = this.f_115095_.m_109393_().m_119422_(f_244537_);
            }
         }

         if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
            modelIn = (BakedModel)Reflector.ForgeHooksClient_handleCameraTransforms.call(matrixStackIn, modelIn, transformTypeIn, leftHand);
         } else {
            modelIn.m_7442_().m_269404_(transformTypeIn).m_111763_(leftHand, matrixStackIn);
         }

         matrixStackIn.m_252880_(-0.5F, -0.5F, -0.5F);
         if (modelIn.m_7521_() || itemStackIn.m_150930_(Items.f_42713_) && !flag) {
            if (Reflector.MinecraftForge.exists()) {
               IClientItemExtensions.of(itemStackIn).getCustomRenderer().m_108829_(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            } else {
               this.f_174223_.m_108829_(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            }
         } else {
            boolean flag1;
            label114: {
               if (transformTypeIn != ItemDisplayContext.GUI && !transformTypeIn.m_269069_()) {
                  Item var12 = itemStackIn.m_41720_();
                  if (var12 instanceof BlockItem) {
                     BlockItem blockitem = (BlockItem)var12;
                     Block block = blockitem.m_40614_();
                     flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                     break label114;
                  }
               }

               flag1 = true;
            }

            boolean forge = Reflector.ForgeHooksClient.exists();
            Iterable renderPassModels = forge ? modelIn.getRenderPasses(itemStackIn, flag1) : new SingleIterable(modelIn);
            Iterable renderTypes = forge ? modelIn.getRenderTypes(itemStackIn, flag1) : new SingleIterable(ItemBlockRenderTypes.m_109279_(itemStackIn, flag1));
            Iterator var14 = ((Iterable)renderPassModels).iterator();

            while(var14.hasNext()) {
               BakedModel modelForge = (BakedModel)var14.next();
               modelIn = modelForge;
               Iterator var16 = ((Iterable)renderTypes).iterator();

               while(var16.hasNext()) {
                  RenderType rendertype = (RenderType)var16.next();
                  VertexConsumer vertexconsumer;
                  if (m_285827_(itemStackIn) && itemStackIn.m_41790_()) {
                     PoseStack.Pose posestack$pose = matrixStackIn.m_85850_().m_323639_();
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
                     modelIn = CustomItems.getCustomItemModel(itemStackIn, modelIn, ItemOverrides.lastModelLocation, false);
                     ItemOverrides.lastModelLocation = null;
                  }

                  if (EmissiveTextures.isActive()) {
                     EmissiveTextures.beginRender();
                  }

                  this.m_115189_(modelIn, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, vertexconsumer);
                  if (EmissiveTextures.isActive()) {
                     if (EmissiveTextures.hasEmissive()) {
                        EmissiveTextures.beginRenderEmissive();
                        VertexConsumer vertexBuilderEmissive = vertexconsumer instanceof VertexBuilderWrapper ? ((VertexBuilderWrapper)vertexconsumer).getVertexBuilder() : vertexconsumer;
                        this.m_115189_(modelIn, itemStackIn, LightTexture.MAX_BRIGHTNESS, combinedOverlayIn, matrixStackIn, vertexBuilderEmissive);
                        EmissiveTextures.endRenderEmissive();
                     }

                     EmissiveTextures.endRender();
                  }
               }
            }
         }

         matrixStackIn.m_85849_();
      }

   }

   private static boolean m_285827_(ItemStack itemStackIn) {
      return itemStackIn.m_204117_(ItemTags.f_215866_) || itemStackIn.m_150930_(Items.f_42524_);
   }

   public static VertexConsumer m_115184_(MultiBufferSource bufferIn, RenderType renderTypeIn, boolean hasEffectIn) {
      if (Shaders.isShadowPass) {
         hasEffectIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         hasEffectIn = false;
      }

      return hasEffectIn ? VertexMultiConsumer.m_86168_(bufferIn.m_6299_(RenderType.m_110484_()), bufferIn.m_6299_(renderTypeIn)) : bufferIn.m_6299_(renderTypeIn);
   }

   public static VertexConsumer m_115180_(MultiBufferSource bufferIn, RenderType renderTypeIn, PoseStack.Pose entryIn) {
      return VertexMultiConsumer.m_86168_(new SheetedDecalTextureGenerator(bufferIn.m_6299_(RenderType.m_110490_()), entryIn, 0.0078125F), bufferIn.m_6299_(renderTypeIn));
   }

   public static VertexConsumer m_115211_(MultiBufferSource bufferIn, RenderType renderTypeIn, boolean isItemIn, boolean glintIn) {
      if (Shaders.isShadowPass) {
         glintIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         glintIn = false;
      }

      if (!glintIn) {
         return bufferIn.m_6299_(renderTypeIn);
      } else {
         return Minecraft.m_91085_() && renderTypeIn == Sheets.m_110791_() ? VertexMultiConsumer.m_86168_(bufferIn.m_6299_(RenderType.m_110487_()), bufferIn.m_6299_(renderTypeIn)) : VertexMultiConsumer.m_86168_(bufferIn.m_6299_(isItemIn ? RenderType.m_110490_() : RenderType.m_110496_()), bufferIn.m_6299_(renderTypeIn));
      }
   }

   public static VertexConsumer m_115222_(MultiBufferSource bufferIn, RenderType renderTypeIn, boolean isItemIn, boolean hasEffectIn) {
      if (Shaders.isShadowPass) {
         hasEffectIn = false;
      }

      if (EmissiveTextures.isRenderEmissive()) {
         hasEffectIn = false;
      }

      return hasEffectIn ? VertexMultiConsumer.m_86168_(bufferIn.m_6299_(isItemIn ? RenderType.m_110490_() : RenderType.m_110499_()), bufferIn.m_6299_(renderTypeIn)) : bufferIn.m_6299_(renderTypeIn);
   }

   private void m_115162_(PoseStack matrixStackIn, VertexConsumer bufferIn, List quadsIn, ItemStack itemStackIn, int combinedLightIn, int combinedOverlayIn) {
      boolean flag = !itemStackIn.m_41619_();
      PoseStack.Pose posestack$pose = matrixStackIn.m_85850_();
      boolean emissiveActive = EmissiveTextures.isActive();
      int listSize = quadsIn.size();
      int baseColorMul = listSize > 0 && Config.isCustomColors() ? CustomColors.getColorFromItemStack(itemStackIn, -1, -1) : -1;

      for(int ix = 0; ix < listSize; ++ix) {
         BakedQuad bakedquad = (BakedQuad)quadsIn.get(ix);
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

   public BakedModel m_174264_(ItemStack stack, @Nullable Level worldIn, @Nullable LivingEntity entityIn, int seedIn) {
      BakedModel bakedmodel;
      if (stack.m_150930_(Items.f_42713_)) {
         bakedmodel = this.f_115095_.m_109393_().m_119422_(f_244055_);
      } else if (stack.m_150930_(Items.f_151059_)) {
         bakedmodel = this.f_115095_.m_109393_().m_119422_(f_243706_);
      } else {
         bakedmodel = this.f_115095_.m_109406_(stack);
      }

      ClientLevel clientlevel = worldIn instanceof ClientLevel ? (ClientLevel)worldIn : null;
      ItemOverrides.lastModelLocation = null;
      BakedModel bakedmodel1 = bakedmodel.m_7343_().m_173464_(bakedmodel, stack, clientlevel, entityIn, seedIn);
      if (Config.isCustomItems()) {
         bakedmodel1 = CustomItems.getCustomItemModel(stack, bakedmodel1, ItemOverrides.lastModelLocation, true);
      }

      return bakedmodel1 == null ? this.f_115095_.m_109393_().m_119409_() : bakedmodel1;
   }

   public void m_269128_(ItemStack itemStackIn, ItemDisplayContext contextIn, int combinedLightIn, int combinedOverlayIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, @Nullable Level worldIn, int seedIn) {
      this.m_269491_((LivingEntity)null, itemStackIn, contextIn, false, matrixStackIn, bufferIn, worldIn, combinedLightIn, combinedOverlayIn, seedIn);
   }

   public void m_269491_(@Nullable LivingEntity livingEntityIn, ItemStack itemStackIn, ItemDisplayContext contextIn, boolean leftHand, PoseStack matrixStackIn, MultiBufferSource bufferIn, @Nullable Level worldIn, int combinedLightIn, int combinedOverlayIn, int seedIn) {
      if (!itemStackIn.m_41619_()) {
         BakedModel bakedmodel = this.m_174264_(itemStackIn, worldIn, livingEntityIn, seedIn);
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
      ItemRenderer.renderItemGui = renderItemGui;
   }

   public BlockEntityWithoutLevelRenderer getBlockEntityRenderer() {
      return this.f_174223_;
   }

   static {
      f_115094_ = Sets.newHashSet(new Item[]{Items.f_41852_});
      f_244324_ = ModelResourceLocation.m_340229_(ResourceLocation.m_340282_("trident"));
      f_244055_ = ModelResourceLocation.m_340229_(ResourceLocation.m_340282_("trident_in_hand"));
      f_244537_ = ModelResourceLocation.m_340229_(ResourceLocation.m_340282_("spyglass"));
      f_243706_ = ModelResourceLocation.m_340229_(ResourceLocation.m_340282_("spyglass_in_hand"));
      renderItemGui = false;
   }
}
