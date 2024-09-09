package net.minecraft.src;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class C_3169_ {
   private static final int f_166598_ = 32768;
   private final C_3170_ f_85535_;
   private final String f_85536_;
   private int f_85537_;

   protected C_3169_(C_3170_ type, int shaderId, String filename) {
      this.f_85535_ = type;
      this.f_85537_ = shaderId;
      this.f_85536_ = filename;
   }

   public void m_166610_(C_141543_ shaderIn) {
      RenderSystem.assertOnRenderThread();
      GlStateManager.glAttachShader(shaderIn.m_108943_(), this.m_166618_());
   }

   public void m_85543_() {
      if (this.f_85537_ != -1) {
         RenderSystem.assertOnRenderThread();
         GlStateManager.glDeleteShader(this.f_85537_);
         this.f_85537_ = -1;
         this.f_85535_.m_85570_().remove(this.f_85536_);
      }

   }

   public String m_85551_() {
      return this.f_85536_;
   }

   public static C_3169_ m_166604_(C_3170_ typeIn, String nameIn, InputStream inputStreamIn, String packNameIn, C_141538_ preprocessorIn) throws IOException {
      RenderSystem.assertOnRenderThread();
      int i = m_166612_(typeIn, nameIn, inputStreamIn, packNameIn, preprocessorIn);
      C_3169_ program = new C_3169_(typeIn, i, nameIn);
      typeIn.m_85570_().put(nameIn, program);
      return program;
   }

   protected static int m_166612_(C_3170_ typeIn, String nameIn, InputStream inputStreamIn, String packNameIn, C_141538_ preprocessorIn) throws IOException {
      String s = IOUtils.toString(inputStreamIn, StandardCharsets.UTF_8);
      if (typeIn == C_3169_.C_3170_.VERTEX) {
         s = s.replace("texelFetch(Sampler2, UV2 / 16, 0)", "texture(Sampler2, (UV2 / 256.0) + (0.5 / 16.0))");
         s = s.replace("minecraft_sample_lightmap(Sampler2, UV2)", "texture(Sampler2, (UV2 / 256.0) + (0.5 / 16.0))");
      }

      if (typeIn == C_3169_.C_3170_.FRAGMENT) {
         s = s.replace("(color.a < 0.5)", "(color.a < 0.1)");
      }

      if (s == null) {
         throw new IOException("Could not load program " + typeIn.m_85566_());
      } else {
         int i = GlStateManager.glCreateShader(typeIn.m_85571_());
         GlStateManager.glShaderSource(i, preprocessorIn.m_166461_(s));
         GlStateManager.glCompileShader(i);
         if (GlStateManager.glGetShaderi(i, 35713) == 0) {
            String s1 = StringUtils.trim(GlStateManager.glGetShaderInfoLog(i, 32768));
            throw new IOException("Couldn't compile " + typeIn.m_85566_() + " program (" + packNameIn + ", " + nameIn + ") : " + s1);
         } else {
            return i;
         }
      }
   }

   protected int m_166618_() {
      return this.f_85537_;
   }

   public static enum C_3170_ {
      VERTEX("vertex", ".vsh", 35633),
      FRAGMENT("fragment", ".fsh", 35632);

      private final String f_85554_;
      private final String f_85555_;
      private final int f_85556_;
      private final Map f_85557_ = Maps.newHashMap();

      private C_3170_(final String shaderNameIn, final String shaderExtensionIn, final int shaderModeIn) {
         this.f_85554_ = shaderNameIn;
         this.f_85555_ = shaderExtensionIn;
         this.f_85556_ = shaderModeIn;
      }

      public String m_85566_() {
         return this.f_85554_;
      }

      public String m_85569_() {
         return this.f_85555_;
      }

      int m_85571_() {
         return this.f_85556_;
      }

      public Map m_85570_() {
         return this.f_85557_;
      }

      // $FF: synthetic method
      private static C_3170_[] $values() {
         return new C_3170_[]{VERTEX, FRAGMENT};
      }
   }
}
