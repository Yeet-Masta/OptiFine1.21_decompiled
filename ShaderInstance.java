import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.src.C_140974_;
import net.minecraft.src.C_141538_;
import net.minecraft.src.C_141543_;
import net.minecraft.src.C_181_;
import net.minecraft.src.C_3166_;
import net.minecraft.src.C_3171_;
import net.minecraft.src.C_3172_;
import net.minecraft.src.C_5144_;
import net.minecraft.src.C_5269_;
import net.minecraft.src.C_76_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderUtils;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix4f;
import org.slf4j.Logger;

public class ShaderInstance implements C_141543_, AutoCloseable {
   public static final String a = "shaders";
   private static final String q = "shaders/core/";
   private static final String r = "shaders/include/";
   static final Logger s = LogUtils.getLogger();
   private static final C_3166_ t = new C_3166_();
   private static final boolean u = true;
   private static ShaderInstance v;
   private static int w = -1;
   private final Map<String, Object> x = Maps.newHashMap();
   private final List<String> y = Lists.newArrayList();
   private final List<Integer> z = Lists.newArrayList();
   private final List<C_3172_> A = Lists.newArrayList();
   private final List<Integer> B = Lists.newArrayList();
   private final Map<String, C_3172_> C = Maps.newHashMap();
   private final int D;
   private final String E;
   private boolean F;
   private final Program G;
   private final Program H;
   private final VertexFormat I;
   @Nullable
   public final C_3172_ b;
   @Nullable
   public final C_3172_ c;
   @Nullable
   public final C_3172_ d;
   @Nullable
   public final C_3172_ e;
   @Nullable
   public final C_3172_ f;
   @Nullable
   public final C_3172_ g;
   @Nullable
   public final C_3172_ h;
   @Nullable
   public final C_3172_ i;
   @Nullable
   public final C_3172_ j;
   @Nullable
   public final C_3172_ k;
   @Nullable
   public final C_3172_ l;
   @Nullable
   public final C_3172_ m;
   @Nullable
   public final C_3172_ n;
   @Nullable
   public final C_3172_ o;
   @Nullable
   public final C_3172_ p;

   public ShaderInstance(C_140974_ providerIn, String nameIn, VertexFormat formatIn) throws IOException {
      this(providerIn, new ResourceLocation(nameIn), formatIn);
   }

   public ShaderInstance(C_140974_ providerIn, ResourceLocation shaderLocation, VertexFormat formatIn) throws IOException {
      this.E = shaderLocation.b().equals("minecraft") ? shaderLocation.a() : shaderLocation.toString();
      this.I = formatIn;
      ResourceLocation resourcelocation = ResourceLocation.a(shaderLocation.b(), "shaders/core/" + shaderLocation.a() + ".json");

      try {
         Reader reader = providerIn.openAsReader(resourcelocation);

         try {
            JsonObject jsonobject = C_181_.m_13859_(reader);
            String s1 = C_181_.m_13906_(jsonobject, "vertex");
            String s = C_181_.m_13906_(jsonobject, "fragment");
            JsonArray jsonarray = C_181_.m_13832_(jsonobject, "samplers", null);
            if (jsonarray != null) {
               int i = 0;

               for (JsonElement jsonelement : jsonarray) {
                  try {
                     this.a(jsonelement);
                  } catch (Exception var18) {
                     C_5269_ chainedjsonexception1 = C_5269_.m_135906_(var18);
                     chainedjsonexception1.m_135908_("samplers[" + i + "]");
                     throw chainedjsonexception1;
                  }

                  i++;
               }
            }

            JsonArray jsonarray1 = C_181_.m_13832_(jsonobject, "uniforms", null);
            if (jsonarray1 != null) {
               int j = 0;

               for (JsonElement jsonelement1 : jsonarray1) {
                  try {
                     this.b(jsonelement1);
                  } catch (Exception var17) {
                     C_5269_ chainedjsonexception2 = C_5269_.m_135906_(var17);
                     chainedjsonexception2.m_135908_("uniforms[" + j + "]");
                     throw chainedjsonexception2;
                  }

                  j++;
               }
            }

            this.G = a(providerIn, Program.a.a, s1);
            this.H = a(providerIn, Program.a.b, s);
            this.D = C_3171_.m_85577_();
            int k = 0;

            for (String s2 : formatIn.d()) {
               VertexFormatElement vfe = (VertexFormatElement)this.I.getElementMapping().get(s2);
               k = vfe.getAttributeIndex();
               if (k >= 0) {
                  C_3172_.m_166710_(this.D, k, s2);
                  k++;
               }
            }

            C_3171_.m_166623_(this);
            this.j();
         } catch (Throwable var19) {
            if (reader != null) {
               try {
                  reader.close();
               } catch (Throwable var16) {
                  var19.addSuppressed(var16);
               }
            }

            throw var19;
         }

         if (reader != null) {
            reader.close();
         }
      } catch (Exception var20) {
         C_5269_ chainedjsonexception = C_5269_.m_135906_(var20);
         chainedjsonexception.m_135910_(resourcelocation.a());
         throw chainedjsonexception;
      }

      this.m_108957_();
      this.b = this.a("ModelViewMat");
      this.c = this.a("ProjMat");
      this.d = this.a("TextureMat");
      this.e = this.a("ScreenSize");
      this.f = this.a("ColorModulator");
      this.g = this.a("Light0_Direction");
      this.h = this.a("Light1_Direction");
      this.i = this.a("GlintAlpha");
      this.j = this.a("FogStart");
      this.k = this.a("FogEnd");
      this.l = this.a("FogColor");
      this.m = this.a("FogShape");
      this.n = this.a("LineWidth");
      this.o = this.a("GameTime");
      this.p = this.a("ChunkOffset");
   }

   private static Program a(final C_140974_ providerIn, Program.a typeIn, String nameIn) throws IOException {
      Program program1 = (Program)typeIn.c().get(nameIn);
      Program program;
      if (program1 == null) {
         ResourceLocation loc = ResourceLocation.b(nameIn);
         String s = "shaders/core/" + loc.a() + typeIn.b();
         C_76_ resource = providerIn.getResourceOrThrow(ResourceLocation.a(loc.b(), s));
         InputStream inputstream = resource.m_215507_();

         try {
            final String s1 = C_5144_.m_179922_(s);
            program = Program.a(typeIn, nameIn, inputstream, resource.m_215506_(), new C_141538_() {
               private final Set<String> c = Sets.newHashSet();

               public String m_142138_(boolean localIn, String fileIn) {
                  if (Reflector.ForgeHooksClient_getShaderImportLocation.exists()) {
                     ResourceLocation resLocForge = (ResourceLocation)Reflector.ForgeHooksClient_getShaderImportLocation.call(s1, localIn, fileIn);
                     fileIn = resLocForge.toString();
                  } else {
                     fileIn = C_5144_.m_179924_((localIn ? s1 : "shaders/include/") + fileIn);
                  }

                  if (!this.c.add(fileIn)) {
                     return null;
                  } else {
                     ResourceLocation resourcelocation = ResourceLocation.a(fileIn);

                     try {
                        Reader reader = providerIn.openAsReader(resourcelocation);

                        String s2;
                        try {
                           s2 = IOUtils.toString(reader);
                        } catch (Throwable var9) {
                           if (reader != null) {
                              try {
                                 reader.close();
                              } catch (Throwable var8) {
                                 var9.addSuppressed(var8);
                              }
                           }

                           throw var9;
                        }

                        if (reader != null) {
                           reader.close();
                        }

                        return s2;
                     } catch (IOException var10) {
                        ShaderInstance.s.error("Could not open GLSL import {}: {}", fileIn, var10.getMessage());
                        return "#error " + var10.getMessage();
                     }
                  }
               }
            });
         } catch (Throwable var12) {
            if (inputstream != null) {
               try {
                  inputstream.close();
               } catch (Throwable var11) {
                  var12.addSuppressed(var11);
               }
            }

            throw var12;
         }

         if (inputstream != null) {
            inputstream.close();
         }
      } else {
         program = program1;
      }

      return program;
   }

   public void close() {
      for (C_3172_ uniform : this.A) {
         uniform.close();
      }

      C_3171_.m_166621_(this);
   }

   public void f() {
      RenderSystem.assertOnRenderThread();
      w = -1;
      v = null;
      int i = GlStateManager._getActiveTexture();
      if (Boolean.FALSE) {
         for (int j = 0; j < this.z.size(); j++) {
            if (this.x.get(this.y.get(j)) != null) {
               int textureUnit = this.getTextureUnit((String)this.y.get(j), j);
               GlStateManager._activeTexture(33984 + textureUnit);
               GlStateManager._bindTexture(0);
            }
         }
      }

      GlStateManager._activeTexture(i);
   }

   public void g() {
      RenderSystem.assertOnRenderThread();
      this.F = false;
      v = this;
      if (this.D != GlStateManager.getProgram()) {
         C_3171_.m_85578_(this.D);
         w = this.D;
      }

      int i = GlStateManager._getActiveTexture();

      for (int j = 0; j < this.z.size(); j++) {
         String s = (String)this.y.get(j);
         if (this.x.get(s) != null) {
            int k = C_3172_.m_85624_(this.D, s);
            int textureUnit = this.getTextureUnit(s, j);
            C_3172_.m_85616_(k, textureUnit);
            RenderSystem.activeTexture(33984 + textureUnit);
            Object object = this.x.get(s);
            int l = -1;
            if (object instanceof RenderTarget) {
               l = ((RenderTarget)object).f();
            } else if (object instanceof AbstractTexture) {
               l = ((AbstractTexture)object).a();
            } else if (object instanceof Integer) {
               l = (Integer)object;
            }

            if ((textureUnit != 1 || Shaders.activeProgramID <= 0 || !Shaders.isOverlayDisabled()) && l != -1) {
               if (Config.isShaders()) {
                  ShadersTex.bindTexture(l);
               } else {
                  RenderSystem.bindTexture(l);
               }
            }
         }
      }

      GlStateManager._activeTexture(i);

      for (C_3172_ uniform : this.A) {
         uniform.m_85633_();
      }

      if (Config.isShaders() && Shaders.activeProgramID > 0) {
         GlStateManager._glUseProgram(Shaders.activeProgramID);
         boolean oldFlush = RenderUtils.setFlushRenderBuffers(false);
         Shaders.uniform_atlasSize.setValue(Shaders.atlasSizeX, Shaders.atlasSizeY);
         RenderUtils.setFlushRenderBuffers(oldFlush);
      }
   }

   public void m_108957_() {
      this.F = true;
   }

   @Nullable
   public C_3172_ a(String nameIn) {
      RenderSystem.assertOnRenderThread();
      return (C_3172_)this.C.get(nameIn);
   }

   public C_3166_ b(String nameIn) {
      C_3172_ uniform = this.a(nameIn);
      return (C_3166_)(uniform == null ? t : uniform);
   }

   private void j() {
      RenderSystem.assertOnRenderThread();
      IntList intlist = new IntArrayList();

      for (int i = 0; i < this.y.size(); i++) {
         String s = (String)this.y.get(i);
         int j = C_3172_.m_85624_(this.D, s);
         if (j == -1) {
            ShaderInstance.s.warn("Shader {} could not find sampler named {} in the specified shader program.", this.E, s);
            this.x.remove(s);
            intlist.add(i);
         } else {
            this.z.add(j);
         }
      }

      for (int l = intlist.size() - 1; l >= 0; l--) {
         int i1 = intlist.getInt(l);
         this.y.remove(i1);
      }

      for (C_3172_ uniform : this.A) {
         String s1 = uniform.m_85599_();
         int k = C_3172_.m_85624_(this.D, s1);
         if (k == -1) {
            ShaderInstance.s.warn("Shader {} could not find uniform named {} in the specified shader program.", this.E, s1);
         } else {
            this.B.add(k);
            uniform.m_85614_(k);
            this.C.put(s1, uniform);
         }
      }
   }

   private void a(JsonElement jsonElementIn) {
      JsonObject jsonobject = C_181_.m_13918_(jsonElementIn, "sampler");
      String s = C_181_.m_13906_(jsonobject, "name");
      if (!C_181_.m_13813_(jsonobject, "file")) {
         this.x.put(s, null);
         this.y.add(s);
      } else {
         this.y.add(s);
      }
   }

   public void a(String nameIn, Object samplerIn) {
      this.x.put(nameIn, samplerIn);
      this.m_108957_();
   }

   private void b(JsonElement jsonElementIn) throws C_5269_ {
      JsonObject jsonobject = C_181_.m_13918_(jsonElementIn, "uniform");
      String s = C_181_.m_13906_(jsonobject, "name");
      int i = C_3172_.m_85629_(C_181_.m_13906_(jsonobject, "type"));
      int j = C_181_.m_13927_(jsonobject, "count");
      float[] afloat = new float[Math.max(j, 16)];
      JsonArray jsonarray = C_181_.m_13933_(jsonobject, "values");
      if (jsonarray.size() != j && jsonarray.size() > 1) {
         throw new C_5269_("Invalid amount of values specified (expected " + j + ", found " + jsonarray.size() + ")");
      } else {
         int k = 0;

         for (JsonElement jsonelement : jsonarray) {
            try {
               afloat[k] = C_181_.m_13888_(jsonelement, "value");
            } catch (Exception var13) {
               C_5269_ chainedjsonexception = C_5269_.m_135906_(var13);
               chainedjsonexception.m_135908_("values[" + k + "]");
               throw chainedjsonexception;
            }

            k++;
         }

         if (j > 1 && jsonarray.size() == 1) {
            while (k < j) {
               afloat[k] = afloat[0];
               k++;
            }
         }

         int l = j > 1 && j <= 4 && i < 8 ? j - 1 : 0;
         C_3172_ uniform = new C_3172_(s, i + l, j, this);
         if (i <= 3) {
            uniform.m_7401_((int)afloat[0], (int)afloat[1], (int)afloat[2], (int)afloat[3]);
         } else if (i <= 7) {
            uniform.m_5808_(afloat[0], afloat[1], afloat[2], afloat[3]);
         } else {
            uniform.m_5941_(Arrays.copyOfRange(afloat, 0, j));
         }

         this.A.add(uniform);
      }
   }

   public Program c() {
      return this.G;
   }

   public Program d() {
      return this.H;
   }

   public void m_142662_() {
      this.H.a(this);
      this.G.a(this);
   }

   public VertexFormat h() {
      return this.I;
   }

   public String i() {
      return this.E;
   }

   public int m_108943_() {
      return this.D;
   }

   public void a(VertexFormat.c modeIn, Matrix4f viewIn, Matrix4f projectionIn, Window windowIn) {
      for (int i = 0; i < 12; i++) {
         int j = RenderSystem.getShaderTexture(i);
         this.setSampler(i, j);
      }

      if (this.b != null) {
         this.b.m_5679_(viewIn);
      }

      if (this.c != null) {
         this.c.m_5679_(projectionIn);
      }

      if (this.f != null) {
         this.f.m_5941_(RenderSystem.getShaderColor());
      }

      if (this.i != null) {
         this.i.m_5985_(RenderSystem.getShaderGlintAlpha());
      }

      if (this.j != null) {
         this.j.m_5985_(RenderSystem.getShaderFogStart());
      }

      if (this.k != null) {
         this.k.m_5985_(RenderSystem.getShaderFogEnd());
      }

      if (this.l != null) {
         this.l.m_5941_(RenderSystem.getShaderFogColor());
      }

      if (this.m != null) {
         this.m.m_142617_(RenderSystem.getShaderFogShape().m_202324_());
      }

      if (this.d != null) {
         this.d.m_5679_(RenderSystem.getTextureMatrix());
      }

      if (this.o != null) {
         this.o.m_5985_(RenderSystem.getShaderGameTime());
      }

      if (this.e != null) {
         this.e.m_7971_((float)windowIn.l(), (float)windowIn.m());
      }

      if (this.n != null && (modeIn == VertexFormat.c.a || modeIn == VertexFormat.c.b)) {
         this.n.m_5985_(RenderSystem.getShaderLineWidth());
      }

      RenderSystem.setupShaderLights(this);
   }

   public static void useVanillaProgram() {
      if (w > 0) {
         GlStateManager._glUseProgram(w);
      }
   }

   private int getTextureUnit(String sampler, int index) {
      if (sampler.equals("Sampler0")) {
         return 0;
      } else if (sampler.equals("Sampler1")) {
         return 1;
      } else {
         return sampler.equals("Sampler2") ? 2 : index;
      }
   }

   public void setSampler(int indexIn, Object samplerIn) {
      String samplerName = getSamplerName(indexIn);
      this.a(samplerName, samplerIn);
   }

   public static String getSamplerName(int indexIn) {
      switch (indexIn) {
         case 0:
            return "Sampler0";
         case 1:
            return "Sampler1";
         case 2:
            return "Sampler2";
         case 3:
            return "Sampler3";
         case 4:
            return "Sampler4";
         case 5:
            return "Sampler5";
         case 6:
            return "Sampler6";
         case 7:
            return "Sampler7";
         case 8:
            return "Sampler8";
         case 9:
            return "Sampler9";
         case 10:
            return "Sampler10";
         case 11:
            return "Sampler11";
         default:
            return "Sampler" + indexIn;
      }
   }
}
