import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import net.minecraft.src.C_141538_;
import net.minecraft.src.C_141543_;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class Program {
   private static final int a = 32768;
   private final Program.a b;
   private final String c;
   private int d;

   protected Program(Program.a type, int shaderId, String filename) {
      this.b = type;
      this.d = shaderId;
      this.c = filename;
   }

   public void a(C_141543_ shaderIn) {
      RenderSystem.assertOnRenderThread();
      GlStateManager.glAttachShader(shaderIn.m_108943_(), this.c());
   }

   public void a() {
      if (this.d != -1) {
         RenderSystem.assertOnRenderThread();
         GlStateManager.glDeleteShader(this.d);
         this.d = -1;
         this.b.c().remove(this.c);
      }
   }

   public String b() {
      return this.c;
   }

   public static Program a(Program.a typeIn, String nameIn, InputStream inputStreamIn, String packNameIn, C_141538_ preprocessorIn) throws IOException {
      RenderSystem.assertOnRenderThread();
      int i = b(typeIn, nameIn, inputStreamIn, packNameIn, preprocessorIn);
      Program program = new Program(typeIn, i, nameIn);
      typeIn.c().put(nameIn, program);
      return program;
   }

   protected static int b(Program.a typeIn, String nameIn, InputStream inputStreamIn, String packNameIn, C_141538_ preprocessorIn) throws IOException {
      String s = IOUtils.toString(inputStreamIn, StandardCharsets.UTF_8);
      if (typeIn == Program.a.a) {
         s = s.replace("texelFetch(Sampler2, UV2 / 16, 0)", "texture(Sampler2, (UV2 / 256.0) + (0.5 / 16.0))");
         s = s.replace("minecraft_sample_lightmap(Sampler2, UV2)", "texture(Sampler2, (UV2 / 256.0) + (0.5 / 16.0))");
      }

      if (typeIn == Program.a.b) {
         s = s.replace("(color.a < 0.5)", "(color.a < 0.1)");
      }

      if (s == null) {
         throw new IOException("Could not load program " + typeIn.a());
      } else {
         int i = GlStateManager.glCreateShader(typeIn.d());
         GlStateManager.glShaderSource(i, preprocessorIn.m_166461_(s));
         GlStateManager.glCompileShader(i);
         if (GlStateManager.glGetShaderi(i, 35713) == 0) {
            String s1 = StringUtils.trim(GlStateManager.glGetShaderInfoLog(i, 32768));
            throw new IOException("Couldn't compile " + typeIn.a() + " program (" + packNameIn + ", " + nameIn + ") : " + s1);
         } else {
            return i;
         }
      }
   }

   protected int c() {
      return this.d;
   }

   public static enum a {
      a("vertex", ".vsh", 35633),
      b("fragment", ".fsh", 35632);

      private final String c;
      private final String d;
      private final int e;
      private final Map<String, Program> f = Maps.newHashMap();

      private a(final String shaderNameIn, final String shaderExtensionIn, final int shaderModeIn) {
         this.c = shaderNameIn;
         this.d = shaderExtensionIn;
         this.e = shaderModeIn;
      }

      public String a() {
         return this.c;
      }

      public String b() {
         return this.d;
      }

      int d() {
         return this.e;
      }

      public Map<String, Program> c() {
         return this.f;
      }
   }
}
