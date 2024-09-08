package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.HitResult;
import net.optifine.EmissiveTextures;
import net.optifine.entity.model.CustomEntityModels;

public class BlockEntityRenderDispatcher implements ResourceManagerReloadListener {
   private Map<BlockEntityType<?>, BlockEntityRenderer<?>> f_112251_ = ImmutableMap.of();
   private final Font f_112253_;
   private final EntityModelSet f_173556_;
   public Level f_112248_;
   public Camera f_112249_;
   public HitResult f_112250_;
   private final Supplier<BlockRenderDispatcher> f_173557_;
   private final Supplier<ItemRenderer> f_234429_;
   private final Supplier<EntityRenderDispatcher> f_234430_;
   public static BlockEntity tileEntityRendered;
   private Context context;

   public BlockEntityRenderDispatcher(
      Font fontIn,
      EntityModelSet modelSetIn,
      Supplier<BlockRenderDispatcher> blockRenderDispatcherIn,
      Supplier<ItemRenderer> itemRendererIn,
      Supplier<EntityRenderDispatcher> entityRendererIn
   ) {
      this.f_234429_ = itemRendererIn;
      this.f_234430_ = entityRendererIn;
      this.f_112253_ = fontIn;
      this.f_173556_ = modelSetIn;
      this.f_173557_ = blockRenderDispatcherIn;
   }

   @Nullable
   public <E extends BlockEntity> BlockEntityRenderer<E> m_112265_(E tileEntityIn) {
      return (BlockEntityRenderer<E>)this.f_112251_.get(tileEntityIn.m_58903_());
   }

   public void m_173564_(Level worldIn, Camera cameraIn, HitResult hitResultIn) {
      if (this.f_112248_ != worldIn) {
         this.m_112257_(worldIn);
      }

      this.f_112249_ = cameraIn;
      this.f_112250_ = hitResultIn;
   }

   public <E extends BlockEntity> void m_112267_(E tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn) {
      BlockEntityRenderer<E> blockentityrenderer = this.m_112265_(tileEntityIn);
      if (blockentityrenderer != null
         && tileEntityIn.m_58898_()
         && tileEntityIn.m_58903_().m_155262_(tileEntityIn.m_58900_())
         && blockentityrenderer.m_142756_(tileEntityIn, this.f_112249_.m_90583_())) {
         m_112278_(tileEntityIn, () -> m_112284_(blockentityrenderer, tileEntityIn, partialTicks, matrixStackIn, bufferIn));
      }
   }

   private static <T extends BlockEntity> void m_112284_(
      BlockEntityRenderer<T> rendererIn, T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn
   ) {
      Level level = tileEntityIn.m_58904_();
      int i;
      if (level != null) {
         i = LevelRenderer.m_109541_(level, tileEntityIn.m_58899_());
      } else {
         i = 15728880;
      }

      BlockEntity tileEntityRenderedPrev = tileEntityRendered;
      tileEntityRendered = tileEntityIn;
      rendererIn = CustomEntityModels.getBlockEntityRenderer(tileEntityIn, rendererIn);
      if (EmissiveTextures.isActive()) {
         EmissiveTextures.beginRender();
      }

      rendererIn.m_6922_(tileEntityIn, partialTicks, matrixStackIn, bufferIn, i, OverlayTexture.f_118083_);
      if (EmissiveTextures.isActive()) {
         if (EmissiveTextures.hasEmissive()) {
            EmissiveTextures.beginRenderEmissive();
            rendererIn.m_6922_(tileEntityIn, partialTicks, matrixStackIn, bufferIn, LightTexture.MAX_BRIGHTNESS, OverlayTexture.f_118083_);
            EmissiveTextures.endRenderEmissive();
         }

         EmissiveTextures.endRender();
      }

      tileEntityRendered = tileEntityRenderedPrev;
   }

   public <E extends BlockEntity> boolean m_112272_(
      E tileEntityIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn
   ) {
      BlockEntityRenderer<E> blockentityrenderer = this.m_112265_(tileEntityIn);
      if (blockentityrenderer == null) {
         return true;
      } else {
         m_112278_(tileEntityIn, () -> {
            BlockEntity tileEntityRenderedPrev = tileEntityRendered;
            tileEntityRendered = tileEntityIn;
            blockentityrenderer.m_6922_(tileEntityIn, 0.0F, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            tileEntityRendered = tileEntityRenderedPrev;
         });
         return false;
      }
   }

   private static void m_112278_(BlockEntity tileEntityIn, Runnable runnableIn) {
      try {
         runnableIn.run();
      } catch (Throwable var5) {
         CrashReport crashreport = CrashReport.m_127521_(var5, "Rendering Block Entity");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Block Entity Details");
         tileEntityIn.m_58886_(crashreportcategory);
         throw new ReportedException(crashreport);
      }
   }

   public void m_112257_(@Nullable Level worldIn) {
      this.f_112248_ = worldIn;
      if (worldIn == null) {
         this.f_112249_ = null;
      }
   }

   public void m_6213_(ResourceManager resourceManager) {
      Context blockentityrendererprovider$context = new Context(
         this,
         (BlockRenderDispatcher)this.f_173557_.get(),
         (ItemRenderer)this.f_234429_.get(),
         (EntityRenderDispatcher)this.f_234430_.get(),
         this.f_173556_,
         this.f_112253_
      );
      this.context = blockentityrendererprovider$context;
      this.f_112251_ = BlockEntityRenderers.m_173598_(blockentityrendererprovider$context);
   }

   public BlockEntityRenderer getRenderer(BlockEntityType type) {
      return (BlockEntityRenderer)this.f_112251_.get(type);
   }

   public Context getContext() {
      return this.context;
   }

   public Map<BlockEntityType, BlockEntityRenderer> getBlockEntityRenderMap() {
      if (this.f_112251_ instanceof ImmutableMap) {
         this.f_112251_ = new HashMap(this.f_112251_);
      }

      return this.f_112251_;
   }

   public synchronized <T extends BlockEntity> void setSpecialRendererInternal(
      BlockEntityType<T> tileEntityType, BlockEntityRenderer<? super T> specialRenderer
   ) {
      this.f_112251_.put(tileEntityType, specialRenderer);
   }
}
