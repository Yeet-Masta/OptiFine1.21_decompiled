import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import net.minecraft.src.C_1325_;
import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1394_;
import net.minecraft.src.C_141651_;
import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_1673_;
import net.minecraft.src.C_1677_;
import net.minecraft.src.C_1699_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_1893_;
import net.minecraft.src.C_1976_;
import net.minecraft.src.C_1984_;
import net.minecraft.src.C_1997_;
import net.minecraft.src.C_2002_;
import net.minecraft.src.C_2007_;
import net.minecraft.src.C_2021_;
import net.minecraft.src.C_2035_;
import net.minecraft.src.C_268388_;
import net.minecraft.src.C_271080_;
import net.minecraft.src.C_313616_;
import net.minecraft.src.C_313626_;
import net.minecraft.src.C_313774_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3864_;
import net.minecraft.src.C_3875_;
import net.minecraft.src.C_4238_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_79_;
import net.minecraft.src.C_1897_.C_1898_;
import net.optifine.EmissiveTextures;

public class BlockEntityWithoutLevelRenderer implements C_79_ {
   private static final C_2021_[] a = (C_2021_[])Arrays.stream(DyeColor.values())
      .sorted(Comparator.comparingInt(DyeColor::a))
      .map(dyeColorIn -> new C_2021_(dyeColorIn, C_4675_.f_121853_, C_1710_.f_50456_.o()))
      .toArray(C_2021_[]::new);
   private static final C_2021_ b = new C_2021_(C_4675_.f_121853_, C_1710_.f_50456_.o());
   private final C_1997_ c = new C_1997_(C_4675_.f_121853_, C_1710_.f_50087_.o());
   private final C_1997_ d = new C_2035_(C_4675_.f_121853_, C_1710_.f_50325_.o());
   private final C_2007_ e = new C_2007_(C_4675_.f_121853_, C_1710_.f_50265_.o());
   private final C_1976_ f = new C_1976_(C_4675_.f_121853_, C_1710_.f_50414_.o());
   private final C_1984_ g = new C_1984_(C_4675_.f_121853_, C_1710_.f_50028_.o());
   private final C_2002_ h = new C_2002_(C_4675_.f_121853_, C_1710_.f_50569_.o());
   private final C_271080_ i = new C_271080_(C_4675_.f_121853_, C_1710_.f_271197_.o());
   public C_3864_ j;
   public C_3875_ k;
   private Map<C_1898_, C_141651_> l;
   private final BlockEntityRenderDispatcher m;
   private final C_141653_ n;

   public BlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher dispatcherIn, C_141653_ modelSetIn) {
      this.m = dispatcherIn;
      this.n = modelSetIn;
   }

   public void m_6213_(C_77_ resourceManager) {
      this.j = new C_3864_(this.n.a(C_141656_.f_171179_));
      this.k = new C_3875_(this.n.a(C_141656_.f_171255_));
      this.l = SkullBlockRenderer.a(this.n);
   }

   public void a(C_1391_ itemStackIn, C_268388_ transformIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
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

   public void renderRaw(C_1391_ itemStackIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      C_1381_ item = itemStackIn.m_41720_();
      if (item instanceof C_1325_) {
         C_1706_ block = ((C_1325_)item).m_40614_();
         if (block instanceof C_1677_ abstractskullblock) {
            C_313626_ resolvableprofile = (C_313626_)itemStackIn.m_323252_(C_313616_.f_315901_);
            if (resolvableprofile != null && !resolvableprofile.m_320408_()) {
               itemStackIn.m_319322_(C_313616_.f_315901_);
               resolvableprofile.m_322305_().thenAcceptAsync(profileIn -> itemStackIn.m_322496_(C_313616_.f_315901_, profileIn), C_3391_.m_91087_());
               resolvableprofile = null;
            }

            C_141651_ skullmodelbase = (C_141651_)this.l.get(abstractskullblock.m_48754_());
            RenderType rendertype = SkullBlockRenderer.a(abstractskullblock.m_48754_(), resolvableprofile);
            SkullBlockRenderer.a(null, 180.0F, 0.0F, matrixStackIn, bufferIn, combinedLightIn, skullmodelbase, rendertype);
         } else {
            BlockState blockstate = block.o();
            BlockEntity blockentity;
            if (block instanceof C_1673_) {
               this.f.a(itemStackIn, ((C_1673_)block).b());
               blockentity = this.f;
            } else if (block instanceof C_1699_) {
               this.g.a(((C_1699_)block).b());
               blockentity = this.g;
            } else if (blockstate.m_60713_(C_1710_.f_50569_)) {
               blockentity = this.h;
            } else if (blockstate.m_60713_(C_1710_.f_50087_)) {
               blockentity = this.c;
            } else if (blockstate.m_60713_(C_1710_.f_50265_)) {
               blockentity = this.e;
            } else if (blockstate.m_60713_(C_1710_.f_50325_)) {
               blockentity = this.d;
            } else if (blockstate.m_60713_(C_1710_.f_271197_)) {
               this.i.m_271870_(itemStackIn);
               blockentity = this.i;
            } else {
               if (!(block instanceof C_1893_)) {
                  return;
               }

               DyeColor dyecolor1 = C_1893_.b(item);
               if (dyecolor1 == null) {
                  blockentity = b;
               } else {
                  blockentity = a[dyecolor1.a()];
               }
            }

            this.m.a(blockentity, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
         }
      } else if (itemStackIn.m_150930_(C_1394_.f_42740_)) {
         C_313774_ bannerpatternlayers = (C_313774_)itemStackIn.m_322304_(C_313616_.f_314522_, C_313774_.f_316086_);
         DyeColor dyecolor = (DyeColor)itemStackIn.m_323252_(C_313616_.f_315952_);
         boolean flag = !bannerpatternlayers.f_315710_().isEmpty() || dyecolor != null;
         matrixStackIn.a();
         matrixStackIn.b(1.0F, -1.0F, -1.0F);
         Material material = flag ? ModelBakery.g : ModelBakery.h;
         VertexConsumer vertexconsumer = material.c().a(ItemRenderer.b(bufferIn, this.j.a(material.a()), true, itemStackIn.m_41790_()));
         this.j.c().a(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn);
         if (flag) {
            C_4238_.a(
               matrixStackIn,
               bufferIn,
               combinedLightIn,
               combinedOverlayIn,
               this.j.b(),
               material,
               false,
               (DyeColor)Objects.requireNonNullElse(dyecolor, DyeColor.a),
               bannerpatternlayers,
               itemStackIn.m_41790_()
            );
         } else {
            this.j.b().a(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn);
         }

         matrixStackIn.b();
      } else if (itemStackIn.m_150930_(C_1394_.f_42713_)) {
         matrixStackIn.a();
         matrixStackIn.b(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer1 = ItemRenderer.b(bufferIn, this.k.a(C_3875_.a), false, itemStackIn.m_41790_());
         this.k.a(matrixStackIn, vertexconsumer1, combinedLightIn, combinedOverlayIn);
         matrixStackIn.b();
      }
   }
}
