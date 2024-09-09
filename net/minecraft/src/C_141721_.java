package net.minecraft.src;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderUtils;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix4f;
import org.slf4j.Logger;

public class C_141721_ implements C_141543_, AutoCloseable {
   public static final String f_173321_ = "shaders";
   private static final String f_244364_ = "shaders/core/";
   private static final String f_173322_ = "shaders/include/";
   static final Logger f_173323_ = LogUtils.getLogger();
   private static final C_3166_ f_173324_ = new C_3166_();
   private static final boolean f_173325_ = true;
   private static C_141721_ f_173326_;
   private static int f_173327_ = -1;
   private final Map f_173328_;
   private final List f_173329_;
   private final List f_173330_;
   private final List f_173331_;
   private final List f_173332_;
   private final Map f_173333_;
   private final int f_173299_;
   private final String f_173300_;
   private boolean f_173301_;
   private final C_3169_ f_173305_;
   private final C_3169_ f_173306_;
   private final C_3188_ f_173307_;
   @Nullable
   public final C_3172_ f_173308_;
   @Nullable
   public final C_3172_ f_173309_;
   @Nullable
   public final C_3172_ f_173310_;
   @Nullable
   public final C_3172_ f_173311_;
   @Nullable
   public final C_3172_ f_173312_;
   @Nullable
   public final C_3172_ f_173313_;
   @Nullable
   public final C_3172_ f_173314_;
   @Nullable
   public final C_3172_ f_267422_;
   @Nullable
   public final C_3172_ f_173315_;
   @Nullable
   public final C_3172_ f_173316_;
   @Nullable
   public final C_3172_ f_173317_;
   @Nullable
   public final C_3172_ f_202432_;
   @Nullable
   public final C_3172_ f_173318_;
   @Nullable
   public final C_3172_ f_173319_;
   @Nullable
   public final C_3172_ f_173320_;

   public C_141721_(C_140974_ providerIn, String nameIn, C_3188_ formatIn) throws IOException {
      this(providerIn, new C_5265_(nameIn), formatIn);
   }

   public C_141721_(C_140974_ providerIn, C_5265_ shaderLocation, C_3188_ formatIn) throws IOException {
      this.f_173328_ = Maps.newHashMap();
      this.f_173329_ = Lists.newArrayList();
      this.f_173330_ = Lists.newArrayList();
      this.f_173331_ = Lists.newArrayList();
      this.f_173332_ = Lists.newArrayList();
      this.f_173333_ = Maps.newHashMap();
      this.f_173300_ = shaderLocation.m_135827_().equals("minecraft") ? shaderLocation.m_135815_() : shaderLocation.toString();
      this.f_173307_ = formatIn;
      C_5265_ resourcelocation = C_5265_.m_339182_(shaderLocation.m_135827_(), "shaders/core/" + shaderLocation.m_135815_() + ".json");

      try {
         Reader reader = providerIn.m_215597_(resourcelocation);

         try {
            JsonObject jsonobject = C_181_.m_13859_(reader);
            String s1 = C_181_.m_13906_(jsonobject, "vertex");
            String s = C_181_.m_13906_(jsonobject, "fragment");
            JsonArray jsonarray = C_181_.m_13832_(jsonobject, "samplers", (JsonArray)null);
            if (jsonarray != null) {
               int i = 0;

               for(Iterator var11 = jsonarray.iterator(); var11.hasNext(); ++i) {
                  JsonElement jsonelement = (JsonElement)var11.next();

                  try {
                     this.m_173344_(jsonelement);
                  } catch (Exception var18) {
                     C_5269_ chainedjsonexception1 = C_5269_.m_135906_(var18);
                     chainedjsonexception1.m_135908_("samplers[" + i + "]");
                     throw chainedjsonexception1;
                  }
               }
            }

            JsonArray jsonarray1 = C_181_.m_13832_(jsonobject, "uniforms", (JsonArray)null);
            int j;
            Iterator var25;
            if (jsonarray1 != null) {
               j = 0;

               for(var25 = jsonarray1.iterator(); var25.hasNext(); ++j) {
                  JsonElement jsonelement1 = (JsonElement)var25.next();

                  try {
                     this.m_173354_(jsonelement1);
                  } catch (Exception var17) {
                     C_5269_ chainedjsonexception2 = C_5269_.m_135906_(var17);
                     chainedjsonexception2.m_135908_("uniforms[" + j + "]");
                     throw chainedjsonexception2;
                  }
               }
            }

            this.f_173305_ = m_173340_(providerIn, C_3169_.C_3170_.VERTEX, s1);
            this.f_173306_ = m_173340_(providerIn, C_3169_.C_3170_.FRAGMENT, s);
            this.f_173299_ = C_3171_.m_85577_();
            int k = false;
            var25 = formatIn.m_166911_().iterator();

            while(var25.hasNext()) {
               String s2 = (String)var25.next();
               C_3189_ vfe = (C_3189_)this.f_173307_.getElementMapping().get(s2);
               j = vfe.getAttributeIndex();
               if (j >= 0) {
                  C_3172_.m_166710_(this.f_173299_, j, s2);
                  ++j;
               }
            }

            C_3171_.m_166623_(this);
            this.m_173366_();
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
         chainedjsonexception.m_135910_(resourcelocation.m_135815_());
         throw chainedjsonexception;
      }

      this.m_108957_();
      this.f_173308_ = this.m_173348_("ModelViewMat");
      this.f_173309_ = this.m_173348_("ProjMat");
      this.f_173310_ = this.m_173348_("TextureMat");
      this.f_173311_ = this.m_173348_("ScreenSize");
      this.f_173312_ = this.m_173348_("ColorModulator");
      this.f_173313_ = this.m_173348_("Light0_Direction");
      this.f_173314_ = this.m_173348_("Light1_Direction");
      this.f_267422_ = this.m_173348_("GlintAlpha");
      this.f_173315_ = this.m_173348_("FogStart");
      this.f_173316_ = this.m_173348_("FogEnd");
      this.f_173317_ = this.m_173348_("FogColor");
      this.f_202432_ = this.m_173348_("FogShape");
      this.f_173318_ = this.m_173348_("LineWidth");
      this.f_173319_ = this.m_173348_("GameTime");
      this.f_173320_ = this.m_173348_("ChunkOffset");
   }

   private static C_3169_ m_173340_(final C_140974_ providerIn, C_3169_.C_3170_ typeIn, String nameIn) throws IOException {
      C_3169_ program1 = (C_3169_)typeIn.m_85570_().get(nameIn);
      C_3169_ program;
      if (program1 == null) {
         C_5265_ loc = C_5265_.m_340282_(nameIn);
         String var10000 = loc.m_135815_();
         String s = "shaders/core/" + var10000 + typeIn.m_85569_();
         C_76_ resource = providerIn.m_215593_(C_5265_.m_339182_(loc.m_135827_(), s));
         InputStream inputstream = resource.m_215507_();

         try {
            final String s1 = C_5144_.m_179922_(s);
            program = C_3169_.m_166604_(typeIn, nameIn, inputstream, resource.m_215506_(), new C_141538_() {
               private final Set f_173369_ = Sets.newHashSet();

               public String m_142138_(boolean localIn, String fileIn) {
                  C_5265_ resourcelocation;
                  if (Reflector.ForgeHooksClient_getShaderImportLocation.exists()) {
                     resourcelocation = (C_5265_)Reflector.ForgeHooksClient_getShaderImportLocation.call(s1, localIn, fileIn);
                     fileIn = resourcelocation.toString();
                  } else {
                     String var10000 = localIn ? s1 : "shaders/include/";
                     fileIn = C_5144_.m_179924_(var10000 + fileIn);
                  }

                  if (!this.f_173369_.add(fileIn)) {
                     return null;
                  } else {
                     resourcelocation = C_5265_.m_338530_(fileIn);

                     try {
                        Reader reader = providerIn.m_215597_(resourcelocation);

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
                        C_141721_.f_173323_.error("Could not open GLSL import {}: {}", fileIn, var10.getMessage());
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
      Iterator var1 = this.f_173331_.iterator();

      while(var1.hasNext()) {
         C_3172_ uniform = (C_3172_)var1.next();
         uniform.close();
      }

      C_3171_.m_166621_(this);
   }

   public void m_173362_() {
      RenderSystem.assertOnRenderThread();
      f_173327_ = -1;
      f_173326_ = null;
      int i = GlStateManager._getActiveTexture();
      if (Boolean.FALSE) {
         for(int j = 0; j < this.f_173330_.size(); ++j) {
            if (this.f_173328_.get(this.f_173329_.get(j)) != null) {
               int textureUnit = this.getTextureUnit((String)this.f_173329_.get(j), j);
               GlStateManager._activeTexture('蓀' + textureUnit);
               GlStateManager._bindTexture(0);
            }
         }
      }

      GlStateManager._activeTexture(i);
   }

   public void m_173363_() {
      RenderSystem.assertOnRenderThread();
      this.f_173301_ = false;
      f_173326_ = this;
      if (this.f_173299_ != GlStateManager.getProgram()) {
         C_3171_.m_85578_(this.f_173299_);
         f_173327_ = this.f_173299_;
      }

      int i = GlStateManager._getActiveTexture();

      for(int j = 0; j < this.f_173330_.size(); ++j) {
         String s = (String)this.f_173329_.get(j);
         if (this.f_173328_.get(s) != null) {
            int k = C_3172_.m_85624_(this.f_173299_, s);
            int textureUnit = this.getTextureUnit(s, j);
            C_3172_.m_85616_(k, textureUnit);
            RenderSystem.activeTexture('蓀' + textureUnit);
            Object object = this.f_173328_.get(s);
            int l = -1;
            if (object instanceof C_3106_) {
               l = ((C_3106_)object).m_83975_();
            } else if (object instanceof C_4468_) {
               l = ((C_4468_)object).m_117963_();
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
      Iterator var8 = this.f_173331_.iterator();

      while(var8.hasNext()) {
         C_3172_ uniform = (C_3172_)var8.next();
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
      this.f_173301_ = true;
   }

   @Nullable
   public C_3172_ m_173348_(String nameIn) {
      RenderSystem.assertOnRenderThread();
      return (C_3172_)this.f_173333_.get(nameIn);
   }

   public C_3166_ m_173356_(String nameIn) {
      C_3172_ uniform = this.m_173348_(nameIn);
      return (C_3166_)(uniform == null ? f_173324_ : uniform);
   }

   private void m_173366_() {
      RenderSystem.assertOnRenderThread();
      IntList intlist = new IntArrayList();

      int l;
      for(l = 0; l < this.f_173329_.size(); ++l) {
         String s = (String)this.f_173329_.get(l);
         int j = C_3172_.m_85624_(this.f_173299_, s);
         if (j == -1) {
            f_173323_.warn("Shader {} could not find sampler named {} in the specified shader program.", this.f_173300_, s);
            this.f_173328_.remove(s);
            intlist.add(l);
         } else {
            this.f_173330_.add(j);
         }
      }

      for(l = intlist.size() - 1; l >= 0; --l) {
         int i1 = intlist.getInt(l);
         this.f_173329_.remove(i1);
      }

      Iterator var6 = this.f_173331_.iterator();

      while(var6.hasNext()) {
         C_3172_ uniform = (C_3172_)var6.next();
         String s1 = uniform.m_85599_();
         int k = C_3172_.m_85624_(this.f_173299_, s1);
         if (k == -1) {
            f_173323_.warn("Shader {} could not find uniform named {} in the specified shader program.", this.f_173300_, s1);
         } else {
            this.f_173332_.add(k);
            uniform.m_85614_(k);
            this.f_173333_.put(s1, uniform);
         }
      }

   }

   private void m_173344_(JsonElement jsonElementIn) {
      JsonObject jsonobject = C_181_.m_13918_(jsonElementIn, "sampler");
      String s = C_181_.m_13906_(jsonobject, "name");
      if (!C_181_.m_13813_(jsonobject, "file")) {
         this.f_173328_.put(s, (Object)null);
         this.f_173329_.add(s);
      } else {
         this.f_173329_.add(s);
      }

   }

   public void m_173350_(String nameIn, Object samplerIn) {
      this.f_173328_.put(nameIn, samplerIn);
      this.m_108957_();
   }

   private void m_173354_(JsonElement jsonElementIn) throws C_5269_ {
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

         for(Iterator var9 = jsonarray.iterator(); var9.hasNext(); ++k) {
            JsonElement jsonelement = (JsonElement)var9.next();

            try {
               afloat[k] = C_181_.m_13888_(jsonelement, "value");
            } catch (Exception var13) {
               C_5269_ chainedjsonexception = C_5269_.m_135906_(var13);
               chainedjsonexception.m_135908_("values[" + k + "]");
               throw chainedjsonexception;
            }
         }

         if (j > 1 && jsonarray.size() == 1) {
            while(k < j) {
               afloat[k] = afloat[0];
               ++k;
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

         this.f_173331_.add(uniform);
      }
   }

   public C_3169_ m_108962_() {
      return this.f_173305_;
   }

   public C_3169_ m_108964_() {
      return this.f_173306_;
   }

   public void m_142662_() {
      this.f_173306_.m_166610_(this);
      this.f_173305_.m_166610_(this);
   }

   public C_3188_ m_173364_() {
      return this.f_173307_;
   }

   public String m_173365_() {
      return this.f_173300_;
   }

   public int m_108943_() {
      return this.f_173299_;
   }

   public void m_340471_(C_3188_.C_141549_ modeIn, Matrix4f viewIn, Matrix4f projectionIn, C_3161_ windowIn) {
      for(int i = 0; i < 12; ++i) {
         int j = RenderSystem.getShaderTexture(i);
         this.setSampler(i, j);
      }

      if (this.f_173308_ != null) {
         this.f_173308_.m_5679_(viewIn);
      }

      if (this.f_173309_ != null) {
         this.f_173309_.m_5679_(projectionIn);
      }

      if (this.f_173312_ != null) {
         this.f_173312_.m_5941_(RenderSystem.getShaderColor());
      }

      if (this.f_267422_ != null) {
         this.f_267422_.m_5985_(RenderSystem.getShaderGlintAlpha());
      }

      if (this.f_173315_ != null) {
         this.f_173315_.m_5985_(RenderSystem.getShaderFogStart());
      }

      if (this.f_173316_ != null) {
         this.f_173316_.m_5985_(RenderSystem.getShaderFogEnd());
      }

      if (this.f_173317_ != null) {
         this.f_173317_.m_5941_(RenderSystem.getShaderFogColor());
      }

      if (this.f_202432_ != null) {
         this.f_202432_.m_142617_(RenderSystem.getShaderFogShape().m_202324_());
      }

      if (this.f_173310_ != null) {
         this.f_173310_.m_5679_(RenderSystem.getTextureMatrix());
      }

      if (this.f_173319_ != null) {
         this.f_173319_.m_5985_(RenderSystem.getShaderGameTime());
      }

      if (this.f_173311_ != null) {
         this.f_173311_.m_7971_((float)windowIn.m_85441_(), (float)windowIn.m_85442_());
      }

      if (this.f_173318_ != null && (modeIn == C_3188_.C_141549_.LINES || modeIn == C_3188_.C_141549_.LINE_STRIP)) {
         this.f_173318_.m_5985_(RenderSystem.getShaderLineWidth());
      }

      RenderSystem.setupShaderLights(this);
   }

   public static void useVanillaProgram() {
      if (f_173327_ > 0) {
         GlStateManager._glUseProgram(f_173327_);
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
      this.m_173350_(samplerName, samplerIn);
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
