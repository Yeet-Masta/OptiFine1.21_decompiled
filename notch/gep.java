package net.minecraft.src;

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
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public class C_4117_ implements C_3168_, AutoCloseable {
   private static final String f_172564_ = "shaders/program/";
   private static final Logger f_108921_ = LogUtils.getLogger();
   private static final C_3166_ f_108922_ = new C_3166_();
   private static final boolean f_172565_ = true;
   private static C_4117_ f_108923_;
   private static int f_108924_ = -1;
   private final Map<String, IntSupplier> f_108925_ = Maps.newHashMap();
   private final List<String> f_108926_ = Lists.newArrayList();
   private final List<Integer> f_108927_ = Lists.newArrayList();
   private final List<C_3172_> f_108928_ = Lists.newArrayList();
   private final List<Integer> f_108929_ = Lists.newArrayList();
   private final Map<String, C_3172_> f_108930_ = Maps.newHashMap();
   private final int f_108931_;
   private final String f_108932_;
   private boolean f_108933_;
   private final C_3167_ f_108934_;
   private final List<Integer> f_108935_;
   private final List<String> f_108936_;
   private final C_141541_ f_108937_;
   private final C_141541_ f_108938_;

   public C_4117_(C_140974_ resourceManagerIn, String nameIn) throws IOException {
      C_5265_ resourcelocation = C_5265_.m_340282_("shaders/program/" + nameIn + ".json");
      if (Reflector.MinecraftForge.exists()) {
         C_5265_ rl = C_5265_.m_135820_(nameIn);
         resourcelocation = new C_5265_(rl.m_135827_(), "shaders/program/" + rl.m_135815_() + ".json");
      }

      this.f_108932_ = nameIn;
      C_76_ resource = resourceManagerIn.m_215593_(resourcelocation);

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
                     this.m_108948_(jsonelement);
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
               this.f_108935_ = Lists.newArrayListWithCapacity(jsonarray1.size());
               this.f_108936_ = Lists.newArrayListWithCapacity(jsonarray1.size());

               for (JsonElement jsonelement1 : jsonarray1) {
                  try {
                     this.f_108936_.add(C_181_.m_13805_(jsonelement1, "attribute"));
                  } catch (Exception var19) {
                     C_5269_ chainedjsonexception2 = C_5269_.m_135906_(var19);
                     chainedjsonexception2.m_135908_("attributes[" + j + "]");
                     throw chainedjsonexception2;
                  }

                  j++;
               }
            } else {
               this.f_108935_ = null;
               this.f_108936_ = null;
            }

            JsonArray jsonarray2 = C_181_.m_13832_(jsonobject, "uniforms", null);
            if (jsonarray2 != null) {
               int k = 0;

               for (JsonElement jsonelement2 : jsonarray2) {
                  try {
                     this.m_108958_(jsonelement2);
                  } catch (Exception var18) {
                     C_5269_ chainedjsonexception3 = C_5269_.m_135906_(var18);
                     chainedjsonexception3.m_135908_("uniforms[" + k + "]");
                     throw chainedjsonexception3;
                  }

                  k++;
               }
            }

            this.f_108934_ = m_108950_(C_181_.m_13841_(jsonobject, "blend", null));
            this.f_108937_ = m_172566_(resourceManagerIn, C_3169_.C_3170_.VERTEX, s);
            this.f_108938_ = m_172566_(resourceManagerIn, C_3169_.C_3170_.FRAGMENT, s1);
            this.f_108931_ = C_3171_.m_85577_();
            C_3171_.m_166623_(this);
            this.m_108967_();
            if (this.f_108936_ != null) {
               for (String s2 : this.f_108936_) {
                  int l = C_3172_.m_85639_(this.f_108931_, s2);
                  this.f_108935_.add(l);
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
         chainedjsonexception.m_135910_(resourcelocation.m_135815_() + " (" + resource.m_215506_() + ")");
         throw chainedjsonexception;
      }

      this.m_108957_();
   }

   public static C_141541_ m_172566_(C_140974_ resourceManagerIn, C_3169_.C_3170_ typeIn, String nameIn) throws IOException {
      C_3169_ program = (C_3169_)typeIn.m_85570_().get(nameIn);
      if (program != null && !(program instanceof C_141541_)) {
         throw new InvalidClassException("Program is not of type EffectProgram");
      } else {
         C_141541_ effectprogram;
         if (program == null) {
            C_5265_ resourcelocation = C_5265_.m_340282_("shaders/program/" + nameIn + typeIn.m_85569_());
            if (Reflector.MinecraftForge.exists()) {
               C_5265_ rl = C_5265_.m_135820_(nameIn);
               resourcelocation = new C_5265_(rl.m_135827_(), "shaders/program/" + rl.m_135815_() + typeIn.m_85569_());
            }

            C_76_ resource = resourceManagerIn.m_215593_(resourcelocation);
            InputStream inputstream = resource.m_215507_();

            try {
               effectprogram = C_141541_.m_166588_(typeIn, nameIn, inputstream, resource.m_215506_());
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

   public static C_3167_ m_108950_(@Nullable JsonObject jsonIn) {
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
      for (C_3172_ uniform : this.f_108928_) {
         uniform.close();
      }

      C_3171_.m_166621_(this);
   }

   public void m_108965_() {
      RenderSystem.assertOnRenderThread();
      f_108924_ = -1;
      f_108923_ = null;

      for (int i = 0; i < this.f_108927_.size(); i++) {
         if (this.f_108925_.get(this.f_108926_.get(i)) != null) {
            GlStateManager._activeTexture(33984 + i);
            GlStateManager._bindTexture(0);
         }
      }
   }

   public void m_108966_() {
      this.f_108933_ = false;
      f_108923_ = this;
      this.f_108934_.m_85526_();
      if (this.f_108931_ != GlStateManager.getProgram()) {
         C_3171_.m_85578_(this.f_108931_);
         f_108924_ = this.f_108931_;
      }

      for (int i = 0; i < this.f_108927_.size(); i++) {
         String s = (String)this.f_108926_.get(i);
         IntSupplier intsupplier = (IntSupplier)this.f_108925_.get(s);
         if (intsupplier != null) {
            RenderSystem.activeTexture(33984 + i);
            int j = intsupplier.getAsInt();
            if (j != -1) {
               RenderSystem.bindTexture(j);
               C_3172_.m_85616_((Integer)this.f_108927_.get(i), i);
            }
         }
      }

      for (C_3172_ uniform : this.f_108928_) {
         uniform.m_85633_();
      }
   }

   public void m_108957_() {
      this.f_108933_ = true;
   }

   @Nullable
   public C_3172_ m_108952_(String nameIn) {
      RenderSystem.assertOnRenderThread();
      return (C_3172_)this.f_108930_.get(nameIn);
   }

   public C_3166_ m_108960_(String nameIn) {
      C_3172_ uniform = this.m_108952_(nameIn);
      return (C_3166_)(uniform == null ? f_108922_ : uniform);
   }

   private void m_108967_() {
      RenderSystem.assertOnRenderThread();
      IntList intlist = new IntArrayList();

      for (int i = 0; i < this.f_108926_.size(); i++) {
         String s = (String)this.f_108926_.get(i);
         int j = C_3172_.m_85624_(this.f_108931_, s);
         if (j == -1) {
            f_108921_.warn("Shader {} could not find sampler named {} in the specified shader program.", this.f_108932_, s);
            this.f_108925_.remove(s);
            intlist.add(i);
         } else {
            this.f_108927_.add(j);
         }
      }

      for (int l = intlist.size() - 1; l >= 0; l--) {
         this.f_108926_.remove(intlist.getInt(l));
      }

      for (C_3172_ uniform : this.f_108928_) {
         String s1 = uniform.m_85599_();
         int k = C_3172_.m_85624_(this.f_108931_, s1);
         if (k == -1) {
            f_108921_.warn("Shader {} could not find uniform named {} in the specified shader program.", this.f_108932_, s1);
         } else {
            this.f_108929_.add(k);
            uniform.m_85614_(k);
            this.f_108930_.put(s1, uniform);
         }
      }
   }

   private void m_108948_(JsonElement jsonElementIn) {
      JsonObject jsonobject = C_181_.m_13918_(jsonElementIn, "sampler");
      String s = C_181_.m_13906_(jsonobject, "name");
      if (!C_181_.m_13813_(jsonobject, "file")) {
         this.f_108925_.put(s, null);
         this.f_108926_.add(s);
      } else {
         this.f_108926_.add(s);
      }
   }

   public void m_108954_(String nameIn, IntSupplier samplerIn) {
      if (this.f_108925_.containsKey(nameIn)) {
         this.f_108925_.remove(nameIn);
      }

      this.f_108925_.put(nameIn, samplerIn);
      this.m_108957_();
   }

   private void m_108958_(JsonElement jsonElementIn) throws C_5269_ {
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

         this.f_108928_.add(uniform);
      }
   }

   public C_3169_ m_108962_() {
      return this.f_108937_;
   }

   public C_3169_ m_108964_() {
      return this.f_108938_;
   }

   public void m_142662_() {
      this.f_108938_.m_166586_(this);
      this.f_108937_.m_166586_(this);
   }

   public String m_172571_() {
      return this.f_108932_;
   }

   public int m_108943_() {
      return this.f_108931_;
   }
}
