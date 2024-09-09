import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.src.C_3391_;
import net.optifine.render.RenderUtils;
import net.optifine.util.TextureUtils;

public class Material {
   public static final Comparator<Material> a = Comparator.comparing(Material::a).thenComparing(Material::b);
   private final ResourceLocation b;
   private final ResourceLocation c;
   @Nullable
   private RenderType d;

   public Material(ResourceLocation atlasLocationIn, ResourceLocation textureLocationIn) {
      this.b = atlasLocationIn;
      this.c = textureLocationIn;
   }

   public ResourceLocation a() {
      return this.b;
   }

   public ResourceLocation b() {
      return this.c;
   }

   public TextureAtlasSprite c() {
      TextureAtlasSprite sprite = (TextureAtlasSprite)C_3391_.m_91087_().a(this.a()).apply(this.b());
      return TextureUtils.getCustomSprite(sprite);
   }

   public RenderType a(Function<ResourceLocation, RenderType> renderTypeGetter) {
      if (this.d == null) {
         this.d = (RenderType)renderTypeGetter.apply(this.b);
      }

      return this.d;
   }

   public VertexConsumer a(MultiBufferSource bufferIn, Function<ResourceLocation, RenderType> renderTypeGetter) {
      TextureAtlasSprite sprite = this.c();
      RenderType renderType = this.a(renderTypeGetter);
      if (sprite.isSpriteEmissive && renderType.isEntitySolid()) {
         RenderUtils.flushRenderBuffers();
         renderType = RenderType.d(this.b);
      }

      return sprite.a(bufferIn.getBuffer(renderType));
   }

   public VertexConsumer a(MultiBufferSource bufferIn, Function<ResourceLocation, RenderType> renderTypeGetter, boolean hasEffectIn) {
      return this.c().a(ItemRenderer.b(bufferIn, this.a(renderTypeGetter), true, hasEffectIn));
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         Material material = (Material)p_equals_1_;
         return this.b.equals(material.b) && this.c.equals(material.c);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.b, this.c});
   }

   public String toString() {
      return "Material{atlasLocation=" + this.b + ", texture=" + this.c + "}";
   }
}
