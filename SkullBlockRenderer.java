import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.src.C_141651_;
import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_1677_;
import net.minecraft.src.C_1897_;
import net.minecraft.src.C_1959_;
import net.minecraft.src.C_2025_;
import net.minecraft.src.C_243468_;
import net.minecraft.src.C_260365_;
import net.minecraft.src.C_313626_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3869_;
import net.minecraft.src.C_3888_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4497_;
import net.minecraft.src.C_4506_;
import net.minecraft.src.C_141731_.C_141732_;
import net.minecraft.src.C_1897_.C_1898_;
import net.minecraft.src.C_1897_.C_1899_;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;

public class SkullBlockRenderer implements BlockEntityRenderer<C_2025_> {
   private final Map<C_1898_, C_141651_> a;
   private static final Map<C_1898_, ResourceLocation> b = Util.a(Maps.newHashMap(), p_337859_0_ -> {
      p_337859_0_.put(C_1899_.SKELETON, ResourceLocation.b("textures/entity/skeleton/skeleton.png"));
      p_337859_0_.put(C_1899_.WITHER_SKELETON, ResourceLocation.b("textures/entity/skeleton/wither_skeleton.png"));
      p_337859_0_.put(C_1899_.ZOMBIE, ResourceLocation.b("textures/entity/zombie/zombie.png"));
      p_337859_0_.put(C_1899_.CREEPER, ResourceLocation.b("textures/entity/creeper/creeper.png"));
      p_337859_0_.put(C_1899_.DRAGON, ResourceLocation.b("textures/entity/enderdragon/dragon.png"));
      p_337859_0_.put(C_1899_.PIGLIN, ResourceLocation.b("textures/entity/piglin/piglin.png"));
      p_337859_0_.put(C_1899_.PLAYER, C_4497_.a());
   });
   private static C_141653_ modelSet;
   public static Map<C_1898_, C_141651_> models;

   public static Map<C_1898_, C_141651_> a(C_141653_ modelSetIn) {
      if (modelSetIn == modelSet) {
         return models;
      } else {
         Builder<C_1898_, C_141651_> builder = ImmutableMap.builder();
         builder.put(C_1899_.SKELETON, new C_3869_(modelSetIn.a(C_141656_.f_171240_)));
         builder.put(C_1899_.WITHER_SKELETON, new C_3869_(modelSetIn.a(C_141656_.f_171219_)));
         builder.put(C_1899_.PLAYER, new C_3869_(modelSetIn.a(C_141656_.f_171163_)));
         builder.put(C_1899_.ZOMBIE, new C_3869_(modelSetIn.a(C_141656_.f_171224_)));
         builder.put(C_1899_.CREEPER, new C_3869_(modelSetIn.a(C_141656_.f_171130_)));
         builder.put(C_1899_.DRAGON, new C_3888_(modelSetIn.a(C_141656_.f_171135_)));
         builder.put(C_1899_.PIGLIN, new C_260365_(modelSetIn.a(C_141656_.f_260668_)));
         ReflectorForge.postModLoaderEvent(Reflector.EntityRenderersEvent_CreateSkullModels_Constructor, builder, modelSetIn);
         Map<C_1898_, C_141651_> map = new HashMap(builder.build());
         modelSet = modelSetIn;
         models = map;
         return map;
      }
   }

   public SkullBlockRenderer(C_141732_ contextIn) {
      this.a = a(contextIn.m_173585_());
   }

   public void a(C_2025_ tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      float f = tileEntityIn.m_261082_(partialTicks);
      BlockState blockstate = tileEntityIn.n();
      boolean flag = blockstate.m_60734_() instanceof C_1959_;
      Direction direction = flag ? (Direction)blockstate.c(C_1959_.f_58097_) : null;
      int i = flag ? C_243468_.a(direction.g()) : (Integer)blockstate.c(C_1897_.f_56314_);
      float f1 = C_243468_.m_245107_(i);
      C_1898_ skullblock$type = ((C_1677_)blockstate.m_60734_()).m_48754_();
      C_141651_ skullmodelbase = (C_141651_)this.a.get(skullblock$type);
      RenderType rendertype = a(skullblock$type, tileEntityIn.m_59779_());
      a(direction, f1, f, matrixStackIn, bufferIn, combinedLightIn, skullmodelbase, rendertype);
   }

   public static void a(
      @Nullable Direction directionIn,
      float p_173663_1_,
      float animationProgress,
      PoseStack matrixStackIn,
      MultiBufferSource bufferSourceIn,
      int packedLightIn,
      C_141651_ modelBaseIn,
      RenderType renderTypeIn
   ) {
      matrixStackIn.a();
      if (directionIn == null) {
         matrixStackIn.a(0.5F, 0.0F, 0.5F);
      } else {
         float f = 0.25F;
         matrixStackIn.a(0.5F - (float)directionIn.j() * 0.25F, 0.25F, 0.5F - (float)directionIn.l() * 0.25F);
      }

      matrixStackIn.b(-1.0F, -1.0F, 1.0F);
      VertexConsumer vertexconsumer = bufferSourceIn.getBuffer(renderTypeIn);
      modelBaseIn.m_6251_(animationProgress, p_173663_1_, 0.0F);
      modelBaseIn.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
      matrixStackIn.b();
   }

   public static RenderType a(C_1898_ skullType, @Nullable C_313626_ gameProfileIn) {
      ResourceLocation resourcelocation = (ResourceLocation)b.get(skullType);
      if (skullType == C_1899_.PLAYER && gameProfileIn != null) {
         C_4506_ skinmanager = C_3391_.m_91087_().m_91109_();
         return RenderType.i(skinmanager.b(gameProfileIn.f_316880_()).a());
      } else {
         return RenderType.f(resourcelocation);
      }
   }
}
