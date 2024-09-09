import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141733_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3043_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_79_;
import net.minecraft.src.C_141731_.C_141732_;
import net.optifine.EmissiveTextures;
import net.optifine.entity.model.CustomEntityModels;

public class BlockEntityRenderDispatcher implements C_79_ {
   private Map<C_1992_<?>, BlockEntityRenderer<?>> d = ImmutableMap.of();
   private final Font e;
   private final C_141653_ f;
   public C_1596_ a;
   public Camera b;
   public C_3043_ c;
   private final Supplier<BlockRenderDispatcher> g;
   private final Supplier<ItemRenderer> h;
   private final Supplier<EntityRenderDispatcher> i;
   public static BlockEntity tileEntityRendered;
   private C_141732_ context;

   public BlockEntityRenderDispatcher(
      Font fontIn,
      C_141653_ modelSetIn,
      Supplier<BlockRenderDispatcher> blockRenderDispatcherIn,
      Supplier<ItemRenderer> itemRendererIn,
      Supplier<EntityRenderDispatcher> entityRendererIn
   ) {
      this.h = itemRendererIn;
      this.i = entityRendererIn;
      this.e = fontIn;
      this.f = modelSetIn;
      this.g = blockRenderDispatcherIn;
   }

   @Nullable
   public <E extends BlockEntity> BlockEntityRenderer<E> a(E tileEntityIn) {
      return (BlockEntityRenderer<E>)this.d.get(tileEntityIn.r());
   }

   public void a(C_1596_ worldIn, Camera cameraIn, C_3043_ hitResultIn) {
      if (this.a != worldIn) {
         this.a(worldIn);
      }

      this.b = cameraIn;
      this.c = hitResultIn;
   }

   public <E extends BlockEntity> void a(E tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn) {
      BlockEntityRenderer<E> blockentityrenderer = this.a(tileEntityIn);
      if (blockentityrenderer != null && tileEntityIn.m() && tileEntityIn.r().a(tileEntityIn.n()) && blockentityrenderer.a(tileEntityIn, this.b.b())) {
         a(tileEntityIn, () -> a(blockentityrenderer, tileEntityIn, partialTicks, matrixStackIn, bufferIn));
      }
   }

   private static <T extends BlockEntity> void a(
      BlockEntityRenderer<T> rendererIn, T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn
   ) {
      C_1596_ level = tileEntityIn.i();
      int i;
      if (level != null) {
         i = LevelRenderer.a(level, tileEntityIn.aD_());
      } else {
         i = 15728880;
      }

      BlockEntity tileEntityRenderedPrev = tileEntityRendered;
      tileEntityRendered = tileEntityIn;
      rendererIn = CustomEntityModels.getBlockEntityRenderer(tileEntityIn, rendererIn);
      if (EmissiveTextures.isActive()) {
         EmissiveTextures.beginRender();
      }

      rendererIn.a(tileEntityIn, partialTicks, matrixStackIn, bufferIn, i, C_4474_.f_118083_);
      if (EmissiveTextures.isActive()) {
         if (EmissiveTextures.hasEmissive()) {
            EmissiveTextures.beginRenderEmissive();
            rendererIn.a(tileEntityIn, partialTicks, matrixStackIn, bufferIn, LightTexture.MAX_BRIGHTNESS, C_4474_.f_118083_);
            EmissiveTextures.endRenderEmissive();
         }

         EmissiveTextures.endRender();
      }

      tileEntityRendered = tileEntityRenderedPrev;
   }

   public <E extends BlockEntity> boolean a(E tileEntityIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      BlockEntityRenderer<E> blockentityrenderer = this.a(tileEntityIn);
      if (blockentityrenderer == null) {
         return true;
      } else {
         a(tileEntityIn, () -> {
            BlockEntity tileEntityRenderedPrev = tileEntityRendered;
            tileEntityRendered = tileEntityIn;
            blockentityrenderer.a(tileEntityIn, 0.0F, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            tileEntityRendered = tileEntityRenderedPrev;
         });
         return false;
      }
   }

   private static void a(BlockEntity tileEntityIn, Runnable runnableIn) {
      try {
         runnableIn.run();
      } catch (Throwable var5) {
         CrashReport crashreport = CrashReport.a(var5, "Rendering Block Entity");
         C_4909_ crashreportcategory = crashreport.a("Block Entity Details");
         tileEntityIn.a(crashreportcategory);
         throw new C_5204_(crashreport);
      }
   }

   public void a(@Nullable C_1596_ worldIn) {
      this.a = worldIn;
      if (worldIn == null) {
         this.b = null;
      }
   }

   public void m_6213_(C_77_ resourceManager) {
      C_141732_ blockentityrendererprovider$context = new C_141732_(
         this, (BlockRenderDispatcher)this.g.get(), (ItemRenderer)this.h.get(), (EntityRenderDispatcher)this.i.get(), this.f, this.e
      );
      this.context = blockentityrendererprovider$context;
      this.d = C_141733_.m_173598_(blockentityrendererprovider$context);
   }

   public BlockEntityRenderer getRenderer(C_1992_ type) {
      return (BlockEntityRenderer)this.d.get(type);
   }

   public C_141732_ getContext() {
      return this.context;
   }

   public Map<C_1992_, BlockEntityRenderer> getBlockEntityRenderMap() {
      if (this.d instanceof ImmutableMap) {
         this.d = new HashMap(this.d);
      }

      return this.d;
   }

   public synchronized <T extends BlockEntity> void setSpecialRendererInternal(C_1992_<T> tileEntityType, BlockEntityRenderer<? super T> specialRenderer) {
      this.d.put(tileEntityType, specialRenderer);
   }
}
