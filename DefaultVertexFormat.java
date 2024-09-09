import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.SVertexFormat;

public class DefaultVertexFormat {
   public static final VertexFormat a = VertexFormat.a().a("Position", VertexFormatElement.b).a();
   public static final VertexFormat b = VertexFormat.a()
      .a("Position", VertexFormatElement.b)
      .a("Color", VertexFormatElement.c)
      .a("UV0", VertexFormatElement.d)
      .a("UV2", VertexFormatElement.g)
      .a("Normal", VertexFormatElement.h)
      .a(1)
      .a();
   public static final VertexFormat c = VertexFormat.a()
      .a("Position", VertexFormatElement.b)
      .a("Color", VertexFormatElement.c)
      .a("UV0", VertexFormatElement.d)
      .a("UV1", VertexFormatElement.f)
      .a("UV2", VertexFormatElement.g)
      .a("Normal", VertexFormatElement.h)
      .a(1)
      .a();
   public static final VertexFormat d = VertexFormat.a()
      .a("Position", VertexFormatElement.b)
      .a("UV0", VertexFormatElement.d)
      .a("Color", VertexFormatElement.c)
      .a("UV2", VertexFormatElement.g)
      .a();
   public static final VertexFormat e = VertexFormat.a().a("Position", VertexFormatElement.b).a();
   public static final VertexFormat f = VertexFormat.a().a("Position", VertexFormatElement.b).a("Color", VertexFormatElement.c).a();
   public static final VertexFormat g = VertexFormat.a()
      .a("Position", VertexFormatElement.b)
      .a("Color", VertexFormatElement.c)
      .a("Normal", VertexFormatElement.h)
      .a(1)
      .a();
   public static final VertexFormat h = VertexFormat.a()
      .a("Position", VertexFormatElement.b)
      .a("Color", VertexFormatElement.c)
      .a("UV2", VertexFormatElement.g)
      .a();
   public static final VertexFormat i = VertexFormat.a().a("Position", VertexFormatElement.b).a("UV0", VertexFormatElement.d).a();
   public static final VertexFormat j = VertexFormat.a()
      .a("Position", VertexFormatElement.b)
      .a("UV0", VertexFormatElement.d)
      .a("Color", VertexFormatElement.c)
      .a();
   public static final VertexFormat k = VertexFormat.a()
      .a("Position", VertexFormatElement.b)
      .a("Color", VertexFormatElement.c)
      .a("UV0", VertexFormatElement.d)
      .a("UV2", VertexFormatElement.g)
      .a();
   public static final VertexFormat l = VertexFormat.a()
      .a("Position", VertexFormatElement.b)
      .a("UV0", VertexFormatElement.d)
      .a("UV2", VertexFormatElement.g)
      .a("Color", VertexFormatElement.c)
      .a();
   public static final VertexFormat m = VertexFormat.a()
      .a("Position", VertexFormatElement.b)
      .a("UV0", VertexFormatElement.d)
      .a("Color", VertexFormatElement.c)
      .a("Normal", VertexFormatElement.h)
      .a(1)
      .a();
   public static final VertexFormat BLOCK_VANILLA = b.duplicate();
   public static final VertexFormat BLOCK_SHADERS = SVertexFormat.makeExtendedFormatBlock(BLOCK_VANILLA);
   public static final int BLOCK_VANILLA_SIZE = BLOCK_VANILLA.b();
   public static final int BLOCK_SHADERS_SIZE = BLOCK_SHADERS.b();
   public static final VertexFormat ENTITY_VANILLA = c.duplicate();
   public static final VertexFormat ENTITY_SHADERS = SVertexFormat.makeExtendedFormatEntity(ENTITY_VANILLA);
   public static final int ENTITY_VANILLA_SIZE = ENTITY_VANILLA.b();
   public static final int ENTITY_SHADERS_SIZE = ENTITY_SHADERS.b();

   public static void updateVertexFormats() {
      if (Config.isShaders()) {
         b.copyFrom(BLOCK_SHADERS);
         c.copyFrom(ENTITY_SHADERS);
      } else {
         b.copyFrom(BLOCK_VANILLA);
         c.copyFrom(ENTITY_VANILLA);
      }

      if (Reflector.IQuadTransformer.exists()) {
         int stride = b.getIntegerSize();
         Reflector.IQuadTransformer_STRIDE.setStaticIntUnsafe(stride);
         Reflector.QuadBakingVertexConsumer_QUAD_DATA_SIZE.setStaticIntUnsafe(stride * 4);
      }
   }

   static {
      a.setName("BLIT_SCREEN");
      b.setName("BLOCK");
      c.setName("ENTITY");
      d.setName("PARTICLE_POSITION_TEX_COLOR_LMAP");
      e.setName("POSITION");
      f.setName("POSITION_COLOR");
      g.setName("POSITION_COLOR_NORMAL");
      h.setName("POSITION_COLOR_LIGHTMAP");
      i.setName("POSITION_TEX");
      j.setName("POSITION_TEX_COLOR");
      k.setName("POSITION_COLOR_TEX_LIGHTMAP");
      l.setName("POSITION_TEX_LIGHTMAP_COLOR");
      m.setName("POSITION_TEX_COLOR_NORMAL");
      BLOCK_VANILLA.setName("BLOCK");
      ENTITY_VANILLA.setName("ENTITY");
      BLOCK_SHADERS.setName("BLOCK_SHADERS");
      ENTITY_SHADERS.setName("ENTITY_SHADERS");
   }
}
