package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.EmissiveTextures;

public class BlockEntityWithoutLevelRenderer implements ResourceManagerReloadListener {
   private static final ShulkerBoxBlockEntity[] f_108815_ = (ShulkerBoxBlockEntity[])Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::m_41060_)).map((dyeColorIn) -> {
      return new ShulkerBoxBlockEntity(dyeColorIn, BlockPos.f_121853_, Blocks.f_50456_.m_49966_());
   }).toArray((x$0) -> {
      return new ShulkerBoxBlockEntity[x$0];
   });
   private static final ShulkerBoxBlockEntity f_108816_;
   private final ChestBlockEntity f_108817_;
   private final ChestBlockEntity f_108818_;
   private final EnderChestBlockEntity f_108819_;
   private final BannerBlockEntity f_108820_;
   private final BedBlockEntity f_108821_;
   private final ConduitBlockEntity f_108822_;
   private final DecoratedPotBlockEntity f_271254_;
   public ShieldModel f_108823_;
   public TridentModel f_108824_;
   private Map f_172546_;
   private final BlockEntityRenderDispatcher f_172547_;
   private final EntityModelSet f_172548_;

   public BlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher dispatcherIn, EntityModelSet modelSetIn) {
      this.f_108817_ = new ChestBlockEntity(BlockPos.f_121853_, Blocks.f_50087_.m_49966_());
      this.f_108818_ = new TrappedChestBlockEntity(BlockPos.f_121853_, Blocks.f_50325_.m_49966_());
      this.f_108819_ = new EnderChestBlockEntity(BlockPos.f_121853_, Blocks.f_50265_.m_49966_());
      this.f_108820_ = new BannerBlockEntity(BlockPos.f_121853_, Blocks.f_50414_.m_49966_());
      this.f_108821_ = new BedBlockEntity(BlockPos.f_121853_, Blocks.f_50028_.m_49966_());
      this.f_108822_ = new ConduitBlockEntity(BlockPos.f_121853_, Blocks.f_50569_.m_49966_());
      this.f_271254_ = new DecoratedPotBlockEntity(BlockPos.f_121853_, Blocks.f_271197_.m_49966_());
      this.f_172547_ = dispatcherIn;
      this.f_172548_ = modelSetIn;
   }

   public void m_6213_(ResourceManager resourceManager) {
      this.f_108823_ = new ShieldModel(this.f_172548_.m_171103_(ModelLayers.f_171179_));
      this.f_108824_ = new TridentModel(this.f_172548_.m_171103_(ModelLayers.f_171255_));
      this.f_172546_ = SkullBlockRenderer.m_173661_(this.f_172548_);
   }

   public void m_108829_(ItemStack itemStackIn, ItemDisplayContext transformIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      if (EmissiveTextures.isActive()) {
         EmissiveTextures.beginRender();
      }

      this.renderRaw(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
      if (EmissiveTextures.isActive()) {
         if (EmissiveTextures.hasEmissive()) {
            EmissiveTextures.beginRenderEmissive();
            this.renderRaw(itemStackIn, matrixStackIn, bufferIn, LightTexture.MAX_BRIGHTNESS, combinedOverlayIn);
            EmissiveTextures.endRenderEmissive();
         }

         EmissiveTextures.endRender();
      }

   }

   public void renderRaw(ItemStack itemStackIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      Item item = itemStackIn.m_41720_();
      if (item instanceof BlockItem) {
         Block block = ((BlockItem)item).m_40614_();
         if (block instanceof AbstractSkullBlock) {
            AbstractSkullBlock abstractskullblock = (AbstractSkullBlock)block;
            ResolvableProfile resolvableprofile = (ResolvableProfile)itemStackIn.m_323252_(DataComponents.f_315901_);
            if (resolvableprofile != null && !resolvableprofile.m_320408_()) {
               itemStackIn.m_319322_(DataComponents.f_315901_);
               resolvableprofile.m_322305_().thenAcceptAsync((profileIn) -> {
                  itemStackIn.m_322496_(DataComponents.f_315901_, profileIn);
               }, Minecraft.m_91087_());
               resolvableprofile = null;
            }

            SkullModelBase skullmodelbase = (SkullModelBase)this.f_172546_.get(abstractskullblock.m_48754_());
            RenderType rendertype = SkullBlockRenderer.m_112523_(abstractskullblock.m_48754_(), resolvableprofile);
            SkullBlockRenderer.m_173663_((Direction)null, 180.0F, 0.0F, matrixStackIn, bufferIn, combinedLightIn, skullmodelbase, rendertype);
         } else {
            BlockState blockstate = block.m_49966_();
            Object blockentity;
            if (block instanceof AbstractBannerBlock) {
               this.f_108820_.m_58489_(itemStackIn, ((AbstractBannerBlock)block).m_48674_());
               blockentity = this.f_108820_;
            } else if (block instanceof BedBlock) {
               this.f_108821_.m_58729_(((BedBlock)block).m_49554_());
               blockentity = this.f_108821_;
            } else if (blockstate.m_60713_(Blocks.f_50569_)) {
               blockentity = this.f_108822_;
            } else if (blockstate.m_60713_(Blocks.f_50087_)) {
               blockentity = this.f_108817_;
            } else if (blockstate.m_60713_(Blocks.f_50265_)) {
               blockentity = this.f_108819_;
            } else if (blockstate.m_60713_(Blocks.f_50325_)) {
               blockentity = this.f_108818_;
            } else if (blockstate.m_60713_(Blocks.f_271197_)) {
               this.f_271254_.m_271870_(itemStackIn);
               blockentity = this.f_271254_;
            } else {
               if (!(block instanceof ShulkerBoxBlock)) {
                  return;
               }

               DyeColor dyecolor1 = ShulkerBoxBlock.m_56252_(item);
               if (dyecolor1 == null) {
                  blockentity = f_108816_;
               } else {
                  blockentity = f_108815_[dyecolor1.m_41060_()];
               }
            }

            this.f_172547_.m_112272_((BlockEntity)blockentity, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
         }
      } else if (itemStackIn.m_150930_(Items.f_42740_)) {
         BannerPatternLayers bannerpatternlayers = (BannerPatternLayers)itemStackIn.m_322304_(DataComponents.f_314522_, BannerPatternLayers.f_316086_);
         DyeColor dyecolor = (DyeColor)itemStackIn.m_323252_(DataComponents.f_315952_);
         boolean flag = !bannerpatternlayers.f_315710_().isEmpty() || dyecolor != null;
         matrixStackIn.m_85836_();
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         Material material = flag ? ModelBakery.f_119225_ : ModelBakery.f_119226_;
         VertexConsumer vertexconsumer = material.m_119204_().m_118381_(ItemRenderer.m_115222_(bufferIn, this.f_108823_.m_103119_(material.m_119193_()), true, itemStackIn.m_41790_()));
         this.f_108823_.m_103711_().m_104301_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn);
         if (flag) {
            BannerRenderer.m_112074_(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, this.f_108823_.m_103701_(), material, false, (DyeColor)Objects.requireNonNullElse(dyecolor, DyeColor.WHITE), bannerpatternlayers, itemStackIn.m_41790_());
         } else {
            this.f_108823_.m_103701_().m_104301_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn);
         }

         matrixStackIn.m_85849_();
      } else if (itemStackIn.m_150930_(Items.f_42713_)) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer1 = ItemRenderer.m_115222_(bufferIn, this.f_108824_.m_103119_(TridentModel.f_103914_), false, itemStackIn.m_41790_());
         this.f_108824_.m_340227_(matrixStackIn, vertexconsumer1, combinedLightIn, combinedOverlayIn);
         matrixStackIn.m_85849_();
      }

   }

   static {
      f_108816_ = new ShulkerBoxBlockEntity(BlockPos.f_121853_, Blocks.f_50456_.m_49966_());
   }
}
