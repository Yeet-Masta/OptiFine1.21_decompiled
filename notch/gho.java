package net.minecraft.src;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.src.C_141731_.C_141732_;
import net.minecraft.src.C_1897_.C_1898_;
import net.minecraft.src.C_1897_.C_1899_;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;

public class C_4255_ implements C_4244_<C_2025_> {
   private final Map<C_1898_, C_141651_> f_173658_;
   private static final Map<C_1898_, C_5265_> f_112519_ = C_5322_.m_137469_(Maps.newHashMap(), p_337859_0_ -> {
      p_337859_0_.put(C_1899_.SKELETON, C_5265_.m_340282_("textures/entity/skeleton/skeleton.png"));
      p_337859_0_.put(C_1899_.WITHER_SKELETON, C_5265_.m_340282_("textures/entity/skeleton/wither_skeleton.png"));
      p_337859_0_.put(C_1899_.ZOMBIE, C_5265_.m_340282_("textures/entity/zombie/zombie.png"));
      p_337859_0_.put(C_1899_.CREEPER, C_5265_.m_340282_("textures/entity/creeper/creeper.png"));
      p_337859_0_.put(C_1899_.DRAGON, C_5265_.m_340282_("textures/entity/enderdragon/dragon.png"));
      p_337859_0_.put(C_1899_.PIGLIN, C_5265_.m_340282_("textures/entity/piglin/piglin.png"));
      p_337859_0_.put(C_1899_.PLAYER, C_4497_.m_293779_());
   });
   private static C_141653_ modelSet;
   public static Map<C_1898_, C_141651_> models;

   public static Map<C_1898_, C_141651_> m_173661_(C_141653_ modelSetIn) {
      if (modelSetIn == modelSet) {
         return models;
      } else {
         Builder<C_1898_, C_141651_> builder = ImmutableMap.builder();
         builder.put(C_1899_.SKELETON, new C_3869_(modelSetIn.m_171103_(C_141656_.f_171240_)));
         builder.put(C_1899_.WITHER_SKELETON, new C_3869_(modelSetIn.m_171103_(C_141656_.f_171219_)));
         builder.put(C_1899_.PLAYER, new C_3869_(modelSetIn.m_171103_(C_141656_.f_171163_)));
         builder.put(C_1899_.ZOMBIE, new C_3869_(modelSetIn.m_171103_(C_141656_.f_171224_)));
         builder.put(C_1899_.CREEPER, new C_3869_(modelSetIn.m_171103_(C_141656_.f_171130_)));
         builder.put(C_1899_.DRAGON, new C_3888_(modelSetIn.m_171103_(C_141656_.f_171135_)));
         builder.put(C_1899_.PIGLIN, new C_260365_(modelSetIn.m_171103_(C_141656_.f_260668_)));
         ReflectorForge.postModLoaderEvent(Reflector.EntityRenderersEvent_CreateSkullModels_Constructor, builder, modelSetIn);
         Map<C_1898_, C_141651_> map = new HashMap(builder.build());
         modelSet = modelSetIn;
         models = map;
         return map;
      }
   }

   public C_4255_(C_141732_ contextIn) {
      this.f_173658_ = m_173661_(contextIn.m_173585_());
   }

   public void m_6922_(C_2025_ tileEntityIn, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, int combinedOverlayIn) {
      float f = tileEntityIn.m_261082_(partialTicks);
      C_2064_ blockstate = tileEntityIn.n();
      boolean flag = blockstate.m_60734_() instanceof C_1959_;
      C_4687_ direction = flag ? (C_4687_)blockstate.c(C_1959_.f_58097_) : null;
      int i = flag ? C_243468_.m_245225_(direction.m_122424_()) : (Integer)blockstate.c(C_1897_.f_56314_);
      float f1 = C_243468_.m_245107_(i);
      C_1898_ skullblock$type = ((C_1677_)blockstate.m_60734_()).m_48754_();
      C_141651_ skullmodelbase = (C_141651_)this.f_173658_.get(skullblock$type);
      C_4168_ rendertype = m_112523_(skullblock$type, tileEntityIn.m_59779_());
      m_173663_(direction, f1, f, matrixStackIn, bufferIn, combinedLightIn, skullmodelbase, rendertype);
   }

   public static void m_173663_(
      @Nullable C_4687_ directionIn,
      float p_173663_1_,
      float animationProgress,
      C_3181_ matrixStackIn,
      C_4139_ bufferSourceIn,
      int packedLightIn,
      C_141651_ modelBaseIn,
      C_4168_ renderTypeIn
   ) {
      matrixStackIn.m_85836_();
      if (directionIn == null) {
         matrixStackIn.m_252880_(0.5F, 0.0F, 0.5F);
      } else {
         float f = 0.25F;
         matrixStackIn.m_252880_(0.5F - (float)directionIn.m_122429_() * 0.25F, 0.25F, 0.5F - (float)directionIn.m_122431_() * 0.25F);
      }

      matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
      C_3187_ vertexconsumer = bufferSourceIn.m_6299_(renderTypeIn);
      modelBaseIn.m_6251_(animationProgress, p_173663_1_, 0.0F);
      modelBaseIn.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
      matrixStackIn.m_85849_();
   }

   public static C_4168_ m_112523_(C_1898_ skullType, @Nullable C_313626_ gameProfileIn) {
      C_5265_ resourcelocation = (C_5265_)f_112519_.get(skullType);
      if (skullType == C_1899_.PLAYER && gameProfileIn != null) {
         C_4506_ skinmanager = C_3391_.m_91087_().m_91109_();
         return C_4168_.m_110473_(skinmanager.m_293307_(gameProfileIn.f_316880_()).f_290339_());
      } else {
         return C_4168_.m_110464_(resourcelocation);
      }
   }
}
