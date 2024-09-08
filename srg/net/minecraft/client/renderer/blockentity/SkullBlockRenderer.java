package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PiglinHeadModel;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.dragon.DragonHeadModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.SkullBlock.Type;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;

public class SkullBlockRenderer implements BlockEntityRenderer<SkullBlockEntity> {
   private final Map<Type, SkullModelBase> f_173658_;
   private static final Map<Type, ResourceLocation> f_112519_ = Util.m_137469_(Maps.newHashMap(), p_337859_0_ -> {
      p_337859_0_.put(Types.SKELETON, ResourceLocation.m_340282_("textures/entity/skeleton/skeleton.png"));
      p_337859_0_.put(Types.WITHER_SKELETON, ResourceLocation.m_340282_("textures/entity/skeleton/wither_skeleton.png"));
      p_337859_0_.put(Types.ZOMBIE, ResourceLocation.m_340282_("textures/entity/zombie/zombie.png"));
      p_337859_0_.put(Types.CREEPER, ResourceLocation.m_340282_("textures/entity/creeper/creeper.png"));
      p_337859_0_.put(Types.DRAGON, ResourceLocation.m_340282_("textures/entity/enderdragon/dragon.png"));
      p_337859_0_.put(Types.PIGLIN, ResourceLocation.m_340282_("textures/entity/piglin/piglin.png"));
      p_337859_0_.put(Types.PLAYER, DefaultPlayerSkin.m_293779_());
   });
   private static EntityModelSet modelSet;
   public static Map<Type, SkullModelBase> models;

   public static Map<Type, SkullModelBase> m_173661_(EntityModelSet modelSetIn) {
      if (modelSetIn == modelSet) {
         return models;
      } else {
         Builder<Type, SkullModelBase> builder = ImmutableMap.builder();
         builder.put(Types.SKELETON, new SkullModel(modelSetIn.m_171103_(ModelLayers.f_171240_)));
         builder.put(Types.WITHER_SKELETON, new SkullModel(modelSetIn.m_171103_(ModelLayers.f_171219_)));
         builder.put(Types.PLAYER, new SkullModel(modelSetIn.m_171103_(ModelLayers.f_171163_)));
         builder.put(Types.ZOMBIE, new SkullModel(modelSetIn.m_171103_(ModelLayers.f_171224_)));
         builder.put(Types.CREEPER, new SkullModel(modelSetIn.m_171103_(ModelLayers.f_171130_)));
         builder.put(Types.DRAGON, new DragonHeadModel(modelSetIn.m_171103_(ModelLayers.f_171135_)));
         builder.put(Types.PIGLIN, new PiglinHeadModel(modelSetIn.m_171103_(ModelLayers.f_260668_)));
         ReflectorForge.postModLoaderEvent(Reflector.EntityRenderersEvent_CreateSkullModels_Constructor, builder, modelSetIn);
         Map<Type, SkullModelBase> map = new HashMap(builder.build());
         modelSet = modelSetIn;
         models = map;
         return map;
      }
   }

   public SkullBlockRenderer(Context contextIn) {
      this.f_173658_ = m_173661_(contextIn.m_173585_());
   }

   public void m_6922_(
      SkullBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn
   ) {
      float f = tileEntityIn.m_261082_(partialTicks);
      BlockState blockstate = tileEntityIn.m_58900_();
      boolean flag = blockstate.m_60734_() instanceof WallSkullBlock;
      Direction direction = flag ? (Direction)blockstate.m_61143_(WallSkullBlock.f_58097_) : null;
      int i = flag ? RotationSegment.m_245225_(direction.m_122424_()) : (Integer)blockstate.m_61143_(SkullBlock.f_56314_);
      float f1 = RotationSegment.m_245107_(i);
      Type skullblock$type = ((AbstractSkullBlock)blockstate.m_60734_()).m_48754_();
      SkullModelBase skullmodelbase = (SkullModelBase)this.f_173658_.get(skullblock$type);
      RenderType rendertype = m_112523_(skullblock$type, tileEntityIn.m_59779_());
      m_173663_(direction, f1, f, matrixStackIn, bufferIn, combinedLightIn, skullmodelbase, rendertype);
   }

   public static void m_173663_(
      @Nullable Direction directionIn,
      float p_173663_1_,
      float animationProgress,
      PoseStack matrixStackIn,
      MultiBufferSource bufferSourceIn,
      int packedLightIn,
      SkullModelBase modelBaseIn,
      RenderType renderTypeIn
   ) {
      matrixStackIn.m_85836_();
      if (directionIn == null) {
         matrixStackIn.m_252880_(0.5F, 0.0F, 0.5F);
      } else {
         float f = 0.25F;
         matrixStackIn.m_252880_(0.5F - (float)directionIn.m_122429_() * 0.25F, 0.25F, 0.5F - (float)directionIn.m_122431_() * 0.25F);
      }

      matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
      VertexConsumer vertexconsumer = bufferSourceIn.m_6299_(renderTypeIn);
      modelBaseIn.m_6251_(animationProgress, p_173663_1_, 0.0F);
      modelBaseIn.m_340227_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
      matrixStackIn.m_85849_();
   }

   public static RenderType m_112523_(Type skullType, @Nullable ResolvableProfile gameProfileIn) {
      ResourceLocation resourcelocation = (ResourceLocation)f_112519_.get(skullType);
      if (skullType == Types.PLAYER && gameProfileIn != null) {
         SkinManager skinmanager = Minecraft.m_91087_().m_91109_();
         return RenderType.m_110473_(skinmanager.m_293307_(gameProfileIn.f_316880_()).f_290339_());
      } else {
         return RenderType.m_110464_(resourcelocation);
      }
   }
}
