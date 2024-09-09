import java.util.function.Function;
import net.optifine.EmissiveTextures;

public abstract class Model {
   protected final Function<ResourceLocation, RenderType> v;
   public int textureWidth = 64;
   public int textureHeight = 32;
   public ResourceLocation locationTextureCustom;

   public Model(Function<ResourceLocation, RenderType> renderTypeIn) {
      this.v = renderTypeIn;
   }

   public final RenderType a(ResourceLocation locationIn) {
      RenderType type = (RenderType)this.v.apply(locationIn);
      if (EmissiveTextures.isRenderEmissive() && type.isEntitySolid()) {
         type = RenderType.d(locationIn);
      }

      return type;
   }

   public abstract void a(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5);

   public final void a(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn) {
      this.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, -1);
   }
}
