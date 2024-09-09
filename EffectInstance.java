import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.io.InvalidClassException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.src.C_140974_;
import net.minecraft.src.C_141541_;
import net.minecraft.src.C_181_;
import net.minecraft.src.C_3166_;
import net.minecraft.src.C_3167_;
import net.minecraft.src.C_3168_;
import net.minecraft.src.C_3171_;
import net.minecraft.src.C_3172_;
import net.minecraft.src.C_5269_;
import net.minecraft.src.C_76_;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public class EffectInstance implements C_3168_, AutoCloseable {
   private static final String a = "shaders/program/";
   private static final Logger b = LogUtils.getLogger();
   private static final C_3166_ c = new C_3166_();
   private static final boolean d = true;
   private static EffectInstance e;
   private static int f = -1;
   private final Map<String, IntSupplier> g = Maps.newHashMap();
   private final List<String> h = Lists.newArrayList();
   private final List<Integer> i = Lists.newArrayList();
   private final List<C_3172_> j = Lists.newArrayList();
   private final List<Integer> k = Lists.newArrayList();
   private final Map<String, C_3172_> l = Maps.newHashMap();
   private final int m;
   private final String n;
   private boolean o;
   private final C_3167_ p;
   private final List<Integer> q;
   private final List<String> r;
   private final C_141541_ s;
   private final C_141541_ t;

   public EffectInstance(C_140974_ resourceManagerIn, String nameIn) throws IOException {
      ResourceLocation resourcelocation = ResourceLocation.b("shaders/program/" + nameIn + ".json");
      if (Reflector.MinecraftForge.exists()) {
         ResourceLocation rl = ResourceLocation.c(nameIn);
         resourcelocation = new ResourceLocation(rl.b(), "shaders/program/" + rl.a() + ".json");
      }

      this.n = nameIn;
      C_76_ resource = resourceManagerIn.getResourceOrThrow(resourcelocation);

      try {
         Reader reader = resource.m_215508_();

         try {
            JsonObject jsonobject = C_181_.m_13859_(reader);
            String s = C_181_.m_13906_(jsonobject, "vertex");
            String s1 = C_181_.m_13906_(jsonobject, "fragment");
            JsonArray jsonarray = C_181_.m_13832_(jsonobject, "samplers", null);
            if (jsonarray != null) {
               int i = 0;

               for (JsonElement jsonelement : jsonarray) {
                  try {
                     this.a(jsonelement);
                  } catch (Exception var20) {
                     C_5269_ chainedjsonexception1 = C_5269_.m_135906_(var20);
                     chainedjsonexception1.m_135908_("samplers[" + i + "]");
                     throw chainedjsonexception1;
                  }

                  i++;
               }
            }

            JsonArray jsonarray1 = C_181_.m_13832_(jsonobject, "attributes", null);
            if (jsonarray1 != null) {
               int j = 0;
               this.q = Lists.newArrayListWithCapacity(jsonarray1.size());
               this.r = Lists.newArrayListWithCapacity(jsonarray1.size());

               for (JsonElement jsonelement1 : jsonarray1) {
                  try {
                     this.r.add(C_181_.m_13805_(jsonelement1, "attribute"));
                  } catch (Exception var19) {
                     C_5269_ chainedjsonexception2 = C_5269_.m_135906_(var19);
                     chainedjsonexception2.m_135908_("attributes[" + j + "]");
                     throw chainedjsonexception2;
                  }

                  j++;
               }
            } else {
               this.q = null;
               this.r = null;
            }

            JsonArray jsonarray2 = C_181_.m_13832_(jsonobject, "uniforms", null);
            if (jsonarray2 != null) {
               int k = 0;

               for (JsonElement jsonelement2 : jsonarray2) {
                  try {
                     this.b(jsonelement2);
                  } catch (Exception var18) {
                     C_5269_ chainedjsonexception3 = C_5269_.m_135906_(var18);
                     chainedjsonexception3.m_135908_("uniforms[" + k + "]");
                     throw chainedjsonexception3;
                  }

                  k++;
               }
            }

            this.p = a(C_181_.m_13841_(jsonobject, "blend", null));
            this.s = a(resourceManagerIn, Program.a.a, s);
            this.t = a(resourceManagerIn, Program.a.b, s1);
            this.m = C_3171_.m_85577_();
            C_3171_.m_166623_(this);
            this.i();
            if (this.r != null) {
               for (String s2 : this.r) {
                  int l = C_3172_.m_85639_(this.m, s2);
                  this.q.add(l);
               }
            }
         } catch (Throwable var21) {
            if (reader != null) {
               try {
                  reader.close();
               } catch (Throwable var17) {
                  var21.addSuppressed(var17);
               }
            }

            throw var21;
         }

         if (reader != null) {
            reader.close();
         }
      } catch (Exception var22) {
         C_5269_ chainedjsonexception = C_5269_.m_135906_(var22);
         chainedjsonexception.m_135910_(resourcelocation.a() + " (" + resource.m_215506_() + ")");
         throw chainedjsonexception;
      }

      this.m_108957_();
   }

   public static C_141541_ a(C_140974_ resourceManagerIn, Program.a typeIn, String nameIn) throws IOException {
      Program program = (Program)typeIn.c().get(nameIn);
      if (program != null && !(program instanceof C_141541_)) {
         throw new InvalidClassException("Program is not of type EffectProgram");
      } else {
         C_141541_ effectprogram;
         if (program == null) {
            ResourceLocation resourcelocation = ResourceLocation.b("shaders/program/" + nameIn + typeIn.b());
            if (Reflector.MinecraftForge.exists()) {
               ResourceLocation rl = ResourceLocation.c(nameIn);
               resourcelocation = new ResourceLocation(rl.b(), "shaders/program/" + rl.a() + typeIn.b());
            }

            C_76_ resource = resourceManagerIn.getResourceOrThrow(resourcelocation);
            InputStream inputstream = resource.m_215507_();

            try {
               effectprogram = C_141541_.a(typeIn, nameIn, inputstream, resource.m_215506_());
            } catch (Throwable var11) {
               if (inputstream != null) {
                  try {
                     inputstream.close();
                  } catch (Throwable var10) {
                     var11.addSuppressed(var10);
                  }
               }

               throw var11;
            }

            if (inputstream != null) {
               inputstream.close();
            }
         } else {
            effectprogram = (C_141541_)program;
         }

         return effectprogram;
      }
   }

   public static C_3167_ a(@Nullable JsonObject jsonIn) {
      if (jsonIn == null) {
         return new C_3167_();
      } else {
         int i = 32774;
         int j = 1;
         int k = 0;
         int l = 1;
         int i1 = 0;
         boolean flag = true;
         boolean flag1 = false;
         if (C_181_.m_13813_(jsonIn, "func")) {
            i = C_3167_.m_85527_(jsonIn.get("func").getAsString());
            if (i != 32774) {
               flag = false;
            }
         }

         if (C_181_.m_13813_(jsonIn, "srcrgb")) {
            j = C_3167_.m_85530_(jsonIn.get("srcrgb").getAsString());
            if (j != 1) {
               flag = false;
            }
         }

         if (C_181_.m_13813_(jsonIn, "dstrgb")) {
            k = C_3167_.m_85530_(jsonIn.get("dstrgb").getAsString());
            if (k != 0) {
               flag = false;
            }
         }

         if (C_181_.m_13813_(jsonIn, "srcalpha")) {
            l = C_3167_.m_85530_(jsonIn.get("srcalpha").getAsString());
            if (l != 1) {
               flag = false;
            }

            flag1 = true;
         }

         if (C_181_.m_13813_(jsonIn, "dstalpha")) {
            i1 = C_3167_.m_85530_(jsonIn.get("dstalpha").getAsString());
            if (i1 != 0) {
               flag = false;
            }

            flag1 = true;
         }

         if (flag) {
            return new C_3167_();
         } else {
            return flag1 ? new C_3167_(j, k, l, i1, i) : new C_3167_(j, k, i);
         }
      }
   }

   public void close() {
      for (C_3172_ uniform : this.j) {
         uniform.close();
      }

      C_3171_.m_166621_(this);
   }

   public void f() {
      RenderSystem.assertOnRenderThread();
      f = -1;
      e = null;

      for (int i = 0; i < this.i.size(); i++) {
         if (this.g.get(this.h.get(i)) != null) {
            GlStateManager._activeTexture(33984 + i);
            GlStateManager._bindTexture(0);
         }
      }
   }

   public void g() {
      this.o = false;
      e = this;
      this.p.m_85526_();
      if (this.m != GlStateManager.getProgram()) {
         C_3171_.m_85578_(this.m);
         f = this.m;
      }

      for (int i = 0; i < this.i.size(); i++) {
         String s = (String)this.h.get(i);
         IntSupplier intsupplier = (IntSupplier)this.g.get(s);
         if (intsupplier != null) {
            RenderSystem.activeTexture(33984 + i);
            int j = intsupplier.getAsInt();
            if (j != -1) {
               RenderSystem.bindTexture(j);
               C_3172_.m_85616_((Integer)this.i.get(i), i);
            }
         }
      }

      for (C_3172_ uniform : this.j) {
         uniform.m_85633_();
      }
   }

   public void m_108957_() {
      this.o = true;
   }

   @Nullable
   public C_3172_ a(String nameIn) {
      RenderSystem.assertOnRenderThread();
      return (C_3172_)this.l.get(nameIn);
   }

   public C_3166_ b(String nameIn) {
      C_3172_ uniform = this.a(nameIn);
      return (C_3166_)(uniform == null ? c : uniform);
   }

   private void i() {
      RenderSystem.assertOnRenderThread();
      IntList intlist = new IntArrayList();

      for (int i = 0; i < this.h.size(); i++) {
         String s = (String)this.h.get(i);
         int j = C_3172_.m_85624_(this.m, s);
         if (j == -1) {
            b.warn("Shader {} could not find sampler named {} in the specified shader program.", this.n, s);
            this.g.remove(s);
            intlist.add(i);
         } else {
            this.i.add(j);
         }
      }

      for (int l = intlist.size() - 1; l >= 0; l--) {
         this.h.remove(intlist.getInt(l));
      }

      for (C_3172_ uniform : this.j) {
         String s1 = uniform.m_85599_();
         int k = C_3172_.m_85624_(this.m, s1);
         if (k == -1) {
            b.warn("Shader {} could not find uniform named {} in the specified shader program.", this.n, s1);
         } else {
            this.k.add(k);
            uniform.m_85614_(k);
            this.l.put(s1, uniform);
         }
      }
   }

   private void a(JsonElement jsonElementIn) {
      JsonObject jsonobject = C_181_.m_13918_(jsonElementIn, "sampler");
      String s = C_181_.m_13906_(jsonobject, "name");
      if (!C_181_.m_13813_(jsonobject, "file")) {
         this.g.put(s, null);
         this.h.add(s);
      } else {
         this.h.add(s);
      }
   }

   public void a(String nameIn, IntSupplier samplerIn) {
      if (this.g.containsKey(nameIn)) {
         this.g.remove(nameIn);
      }

      this.g.put(nameIn, samplerIn);
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
            uniform.m_5941_(afloat);
         }

         this.j.add(uniform);
      }
   }

   public Program c() {
      return this.s;
   }

   public Program d() {
      return this.t;
   }

   public void m_142662_() {
      this.t.m_166586_(this);
      this.s.m_166586_(this);
   }

   public String h() {
      return this.n;
   }

   public int m_108943_() {
      return this.m;
   }
}
