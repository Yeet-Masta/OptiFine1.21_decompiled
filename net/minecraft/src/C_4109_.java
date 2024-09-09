package net.minecraft.src;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import net.optifine.EmissiveTextures;

public class C_4109_ implements C_79_ {
   private static final C_2021_[] f_108815_ = (C_2021_[])Arrays.stream(C_1353_.values()).sorted(Comparator.comparingInt(C_1353_::m_41060_)).map((dyeColorIn) -> {
      return new C_2021_(dyeColorIn, C_4675_.f_121853_, C_1710_.f_50456_.m_49966_());
   }).toArray((x$0) -> {
      return new C_2021_[x$0];
   });
   private static final C_2021_ f_108816_;
   private final C_1997_ f_108817_;
   private final C_1997_ f_108818_;
   private final C_2007_ f_108819_;
   private final C_1976_ f_108820_;
   private final C_1984_ f_108821_;
   private final C_2002_ f_108822_;
   private final C_271080_ f_271254_;
   public C_3864_ f_108823_;
   public C_3875_ f_108824_;
   private Map f_172546_;
   private final C_4243_ f_172547_;
   private final C_141653_ f_172548_;

   public C_4109_(C_4243_ dispatcherIn, C_141653_ modelSetIn) {
      this.f_108817_ = new C_1997_(C_4675_.f_121853_, C_1710_.f_50087_.m_49966_());
      this.f_108818_ = new C_2035_(C_4675_.f_121853_, C_1710_.f_50325_.m_49966_());
      this.f_108819_ = new C_2007_(C_4675_.f_121853_, C_1710_.f_50265_.m_49966_());
      this.f_108820_ = new C_1976_(C_4675_.f_121853_, C_1710_.f_50414_.m_49966_());
      this.f_108821_ = new C_1984_(C_4675_.f_121853_, C_1710_.f_50028_.m_49966_());
      this.f_108822_ = new C_2002_(C_4675_.f_121853_, C_1710_.f_50569_.m_49966_());
      this.f_271254_ = new C_271080_(C_4675_.f_121853_, C_1710_.f_271197_.m_49966_());
      this.f_172547_ = dispatcherIn;
      this.f_172548_ = modelSetIn;
   }

   public void m_6213_(C_77_ resourceManager) {
      this.f_108823_ = new C_3864_(this.f_172548_.m_171103_(C_141656_.f_171179_));
      this.f_108824_ = new C_3875_(this.f_172548_.m_171103_(C_141656_.f_171255_));
      this.f_172546_ = C_4255_.m_173661_(this.f_172548_);
   }

   public void m_108829_(C_1391_ itemStackIn, C_268388_ transformIn, C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, int combinedOverlayIn) {
      if (EmissiveTextures.isActive()) {
         EmissiveTextures.beginRender();
      }

      this.renderRaw(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
      if (EmissiveTextures.isActive()) {
         if (EmissiveTextures.hasEmissive()) {
            EmissiveTextures.beginRenderEmissive();
            this.renderRaw(itemStackIn, matrixStackIn, bufferIn, C_4138_.MAX_BRIGHTNESS, combinedOverlayIn);
            EmissiveTextures.endRenderEmissive();
         }

         EmissiveTextures.endRender();
      }

   }

   public void renderRaw(C_1391_ itemStackIn, C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, int combinedOverlayIn) {
      C_1381_ item = itemStackIn.m_41720_();
      if (item instanceof C_1325_) {
         C_1706_ block = ((C_1325_)item).m_40614_();
         if (block instanceof C_1677_) {
            C_1677_ abstractskullblock = (C_1677_)block;
            C_313626_ resolvableprofile = (C_313626_)itemStackIn.a(C_313616_.f_315901_);
            if (resolvableprofile != null && !resolvableprofile.m_320408_()) {
               itemStackIn.m_319322_(C_313616_.f_315901_);
               resolvableprofile.m_322305_().thenAcceptAsync((profileIn) -> {
                  itemStackIn.m_322496_(C_313616_.f_315901_, profileIn);
               }, C_3391_.m_91087_());
               resolvableprofile = null;
            }

            C_141651_ skullmodelbase = (C_141651_)this.f_172546_.get(abstractskullblock.m_48754_());
            C_4168_ rendertype = C_4255_.m_112523_(abstractskullblock.m_48754_(), resolvableprofile);
            C_4255_.m_173663_((C_4687_)null, 180.0F, 0.0F, matrixStackIn, bufferIn, combinedLightIn, skullmodelbase, rendertype);
         } else {
            C_2064_ blockstate = block.m_49966_();
            Object blockentity;
            if (block instanceof C_1673_) {
               this.f_108820_.m_58489_(itemStackIn, ((C_1673_)block).m_48674_());
               blockentity = this.f_108820_;
            } else if (block instanceof C_1699_) {
               this.f_108821_.m_58729_(((C_1699_)block).m_49554_());
               blockentity = this.f_108821_;
            } else if (blockstate.m_60713_(C_1710_.f_50569_)) {
               blockentity = this.f_108822_;
            } else if (blockstate.m_60713_(C_1710_.f_50087_)) {
               blockentity = this.f_108817_;
            } else if (blockstate.m_60713_(C_1710_.f_50265_)) {
               blockentity = this.f_108819_;
            } else if (blockstate.m_60713_(C_1710_.f_50325_)) {
               blockentity = this.f_108818_;
            } else if (blockstate.m_60713_(C_1710_.f_271197_)) {
               this.f_271254_.m_271870_(itemStackIn);
               blockentity = this.f_271254_;
            } else {
               if (!(block instanceof C_1893_)) {
                  return;
               }

               C_1353_ dyecolor1 = C_1893_.m_56252_(item);
               if (dyecolor1 == null) {
                  blockentity = f_108816_;
               } else {
                  blockentity = f_108815_[dyecolor1.m_41060_()];
               }
            }

            this.f_172547_.m_112272_((C_1991_)blockentity, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
         }
      } else if (itemStackIn.m_150930_(C_1394_.f_42740_)) {
         C_313774_ bannerpatternlayers = (C_313774_)itemStackIn.a(C_313616_.f_314522_, C_313774_.f_316086_);
         C_1353_ dyecolor = (C_1353_)itemStackIn.a(C_313616_.f_315952_);
         boolean flag = !bannerpatternlayers.f_315710_().isEmpty() || dyecolor != null;
         matrixStackIn.m_85836_();
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         C_4531_ material = flag ? C_4532_.f_119225_ : C_4532_.f_119226_;
         C_3187_ vertexconsumer = material.m_119204_().m_118381_(C_4354_.m_115222_(bufferIn, this.f_108823_.a(material.m_119193_()), true, itemStackIn.m_41790_()));
         this.f_108823_.m_103711_().m_104301_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn);
         if (flag) {
            C_4238_.m_112074_(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, this.f_108823_.m_103701_(), material, false, (C_1353_)Objects.requireNonNullElse(dyecolor, C_1353_.WHITE), bannerpatternlayers, itemStackIn.m_41790_());
         } else {
            this.f_108823_.m_103701_().m_104301_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn);
         }

         matrixStackIn.m_85849_();
      } else if (itemStackIn.m_150930_(C_1394_.f_42713_)) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         C_3187_ vertexconsumer1 = C_4354_.m_115222_(bufferIn, this.f_108824_.a(C_3875_.f_103914_), false, itemStackIn.m_41790_());
         this.f_108824_.a(matrixStackIn, vertexconsumer1, combinedLightIn, combinedOverlayIn);
         matrixStackIn.m_85849_();
      }

   }

   static {
      f_108816_ = new C_2021_(C_4675_.f_121853_, C_1710_.f_50456_.m_49966_());
   }
}
