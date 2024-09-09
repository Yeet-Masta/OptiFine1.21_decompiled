package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.BlendMode;
import com.mojang.blaze3d.shaders.Effect;
import com.mojang.blaze3d.shaders.EffectProgram;
import com.mojang.blaze3d.shaders.ProgramManager;
import com.mojang.blaze3d.shaders.Uniform;
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
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.GsonHelper;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public class EffectInstance implements Effect, AutoCloseable {
   private static final String f_172564_ = "shaders/program/";
   private static final Logger f_108921_ = LogUtils.getLogger();
   private static final AbstractUniform f_108922_ = new AbstractUniform();
   private static final boolean f_172565_ = true;
   private static net.minecraft.client.renderer.EffectInstance f_108923_;
   private static int f_108924_ = -1;
   private final Map<String, IntSupplier> f_108925_ = Maps.newHashMap();
   private final List<String> f_108926_ = Lists.newArrayList();
   private final List<Integer> f_108927_ = Lists.newArrayList();
   private final List<Uniform> f_108928_ = Lists.newArrayList();
   private final List<Integer> f_108929_ = Lists.newArrayList();
   private final Map<String, Uniform> f_108930_ = Maps.newHashMap();
   private final int f_108931_;
   private final String f_108932_;
   private boolean f_108933_;
   private final BlendMode f_108934_;
   private final List<Integer> f_108935_;
   private final List<String> f_108936_;
   private final EffectProgram f_108937_;
   private final EffectProgram f_108938_;

   public EffectInstance(ResourceProvider resourceManagerIn, String nameIn) throws IOException {
      net.minecraft.resources.ResourceLocation resourcelocation = net.minecraft.resources.ResourceLocation.m_340282_("shaders/program/" + nameIn + ".json");
      if (Reflector.MinecraftForge.exists()) {
         net.minecraft.resources.ResourceLocation rl = net.minecraft.resources.ResourceLocation.m_135820_(nameIn);
         resourcelocation = new net.minecraft.resources.ResourceLocation(rl.m_135827_(), "shaders/program/" + rl.m_135815_() + ".json");
      }

      this.f_108932_ = nameIn;
      Resource resource = resourceManagerIn.m_215593_(resourcelocation);

      try {
         Reader reader = resource.m_215508_();

         try {
            JsonObject jsonobject = GsonHelper.m_13859_(reader);
            String s = GsonHelper.m_13906_(jsonobject, "vertex");
            String s1 = GsonHelper.m_13906_(jsonobject, "fragment");
            JsonArray jsonarray = GsonHelper.m_13832_(jsonobject, "samplers", null);
            if (jsonarray != null) {
               int i = 0;

               for (JsonElement jsonelement : jsonarray) {
                  try {
                     this.m_108948_(jsonelement);
                  } catch (Exception var20) {
                     ChainedJsonException chainedjsonexception1 = ChainedJsonException.m_135906_(var20);
                     chainedjsonexception1.m_135908_("samplers[" + i + "]");
                     throw chainedjsonexception1;
                  }

                  i++;
               }
            }

            JsonArray jsonarray1 = GsonHelper.m_13832_(jsonobject, "attributes", null);
            if (jsonarray1 != null) {
               int j = 0;
               this.f_108935_ = Lists.newArrayListWithCapacity(jsonarray1.size());
               this.f_108936_ = Lists.newArrayListWithCapacity(jsonarray1.size());

               for (JsonElement jsonelement1 : jsonarray1) {
                  try {
                     this.f_108936_.add(GsonHelper.m_13805_(jsonelement1, "attribute"));
                  } catch (Exception var19) {
                     ChainedJsonException chainedjsonexception2 = ChainedJsonException.m_135906_(var19);
                     chainedjsonexception2.m_135908_("attributes[" + j + "]");
                     throw chainedjsonexception2;
                  }

                  j++;
               }
            } else {
               this.f_108935_ = null;
               this.f_108936_ = null;
            }

            JsonArray jsonarray2 = GsonHelper.m_13832_(jsonobject, "uniforms", null);
            if (jsonarray2 != null) {
               int k = 0;

               for (JsonElement jsonelement2 : jsonarray2) {
                  try {
                     this.m_108958_(jsonelement2);
                  } catch (Exception var18) {
                     ChainedJsonException chainedjsonexception3 = ChainedJsonException.m_135906_(var18);
                     chainedjsonexception3.m_135908_("uniforms[" + k + "]");
                     throw chainedjsonexception3;
                  }

                  k++;
               }
            }

            this.f_108934_ = m_108950_(GsonHelper.m_13841_(jsonobject, "blend", null));
            this.f_108937_ = m_172566_(resourceManagerIn, com.mojang.blaze3d.shaders.Program.Type.VERTEX, s);
            this.f_108938_ = m_172566_(resourceManagerIn, com.mojang.blaze3d.shaders.Program.Type.FRAGMENT, s1);
            this.f_108931_ = ProgramManager.m_85577_();
            ProgramManager.m_166623_(this);
            this.m_108967_();
            if (this.f_108936_ != null) {
               for (String s2 : this.f_108936_) {
                  int l = Uniform.m_85639_(this.f_108931_, s2);
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
         ChainedJsonException chainedjsonexception = ChainedJsonException.m_135906_(var22);
         chainedjsonexception.m_135910_(resourcelocation.m_135815_() + " (" + resource.m_215506_() + ")");
         throw chainedjsonexception;
      }

      this.m_108957_();
   }

   public static EffectProgram m_172566_(ResourceProvider resourceManagerIn, com.mojang.blaze3d.shaders.Program.Type typeIn, String nameIn) throws IOException {
      com.mojang.blaze3d.shaders.Program program = (com.mojang.blaze3d.shaders.Program)typeIn.m_85570_().get(nameIn);
      if (program != null && !(program instanceof EffectProgram)) {
         throw new InvalidClassException("Program is not of type EffectProgram");
      } else {
         EffectProgram effectprogram;
         if (program == null) {
            net.minecraft.resources.ResourceLocation resourcelocation = net.minecraft.resources.ResourceLocation.m_340282_(
               "shaders/program/" + nameIn + typeIn.m_85569_()
            );
            if (Reflector.MinecraftForge.exists()) {
               net.minecraft.resources.ResourceLocation rl = net.minecraft.resources.ResourceLocation.m_135820_(nameIn);
               resourcelocation = new net.minecraft.resources.ResourceLocation(rl.m_135827_(), "shaders/program/" + rl.m_135815_() + typeIn.m_85569_());
            }

            Resource resource = resourceManagerIn.m_215593_(resourcelocation);
            InputStream inputstream = resource.m_215507_();

            try {
               effectprogram = EffectProgram.m_166588_(typeIn, nameIn, inputstream, resource.m_215506_());
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
            effectprogram = (EffectProgram)program;
         }

         return effectprogram;
      }
   }

   public static BlendMode m_108950_(@Nullable JsonObject jsonIn) {
      if (jsonIn == null) {
         return new BlendMode();
      } else {
         int i = 32774;
         int j = 1;
         int k = 0;
         int l = 1;
         int i1 = 0;
         boolean flag = true;
         boolean flag1 = false;
         if (GsonHelper.m_13813_(jsonIn, "func")) {
            i = BlendMode.m_85527_(jsonIn.get("func").getAsString());
            if (i != 32774) {
               flag = false;
            }
         }

         if (GsonHelper.m_13813_(jsonIn, "srcrgb")) {
            j = BlendMode.m_85530_(jsonIn.get("srcrgb").getAsString());
            if (j != 1) {
               flag = false;
            }
         }

         if (GsonHelper.m_13813_(jsonIn, "dstrgb")) {
            k = BlendMode.m_85530_(jsonIn.get("dstrgb").getAsString());
            if (k != 0) {
               flag = false;
            }
         }

         if (GsonHelper.m_13813_(jsonIn, "srcalpha")) {
            l = BlendMode.m_85530_(jsonIn.get("srcalpha").getAsString());
            if (l != 1) {
               flag = false;
            }

            flag1 = true;
         }

         if (GsonHelper.m_13813_(jsonIn, "dstalpha")) {
            i1 = BlendMode.m_85530_(jsonIn.get("dstalpha").getAsString());
            if (i1 != 0) {
               flag = false;
            }

            flag1 = true;
         }

         if (flag) {
            return new BlendMode();
         } else {
            return flag1 ? new BlendMode(j, k, l, i1, i) : new BlendMode(j, k, i);
         }
      }
   }

   public void close() {
      for (Uniform uniform : this.f_108928_) {
         uniform.close();
      }

      ProgramManager.m_166621_(this);
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
         ProgramManager.m_85578_(this.f_108931_);
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
               Uniform.m_85616_((Integer)this.f_108927_.get(i), i);
            }
         }
      }

      for (Uniform uniform : this.f_108928_) {
         uniform.m_85633_();
      }
   }

   public void m_108957_() {
      this.f_108933_ = true;
   }

   @Nullable
   public Uniform m_108952_(String nameIn) {
      RenderSystem.assertOnRenderThread();
      return (Uniform)this.f_108930_.get(nameIn);
   }

   public AbstractUniform m_108960_(String nameIn) {
      Uniform uniform = this.m_108952_(nameIn);
      return (AbstractUniform)(uniform == null ? f_108922_ : uniform);
   }

   private void m_108967_() {
      RenderSystem.assertOnRenderThread();
      IntList intlist = new IntArrayList();

      for (int i = 0; i < this.f_108926_.size(); i++) {
         String s = (String)this.f_108926_.get(i);
         int j = Uniform.m_85624_(this.f_108931_, s);
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

      for (Uniform uniform : this.f_108928_) {
         String s1 = uniform.m_85599_();
         int k = Uniform.m_85624_(this.f_108931_, s1);
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
      JsonObject jsonobject = GsonHelper.m_13918_(jsonElementIn, "sampler");
      String s = GsonHelper.m_13906_(jsonobject, "name");
      if (!GsonHelper.m_13813_(jsonobject, "file")) {
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

   private void m_108958_(JsonElement jsonElementIn) throws ChainedJsonException {
      JsonObject jsonobject = GsonHelper.m_13918_(jsonElementIn, "uniform");
      String s = GsonHelper.m_13906_(jsonobject, "name");
      int i = Uniform.m_85629_(GsonHelper.m_13906_(jsonobject, "type"));
      int j = GsonHelper.m_13927_(jsonobject, "count");
      float[] afloat = new float[Math.max(j, 16)];
      JsonArray jsonarray = GsonHelper.m_13933_(jsonobject, "values");
      if (jsonarray.size() != j && jsonarray.size() > 1) {
         throw new ChainedJsonException("Invalid amount of values specified (expected " + j + ", found " + jsonarray.size() + ")");
      } else {
         int k = 0;

         for (JsonElement jsonelement : jsonarray) {
            try {
               afloat[k] = GsonHelper.m_13888_(jsonelement, "value");
            } catch (Exception var13) {
               ChainedJsonException chainedjsonexception = ChainedJsonException.m_135906_(var13);
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
         Uniform uniform = new Uniform(s, i + l, j, this);
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

   public com.mojang.blaze3d.shaders.Program m_108962_() {
      return this.f_108937_;
   }

   public com.mojang.blaze3d.shaders.Program m_108964_() {
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
