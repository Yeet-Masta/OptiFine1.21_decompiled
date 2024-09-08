package net.optifine.shaders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.TextureUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.src.C_3148_;
import net.minecraft.src.C_4468_;
import net.minecraft.src.C_4526_;
import net.minecraft.src.C_77_;
import org.apache.commons.io.IOUtils;

public class SimpleShaderTexture extends C_4468_ {
   private String texturePath;
   private long size = 0L;

   public SimpleShaderTexture(String texturePath) {
      this.texturePath = texturePath;
   }

   @Override
   public void m_6704_(C_77_ resourceManager) throws IOException {
      this.m_117964_();
      InputStream inputStream = Shaders.getShaderPackResourceStream(this.texturePath);
      if (inputStream == null) {
         throw new FileNotFoundException("Shader texture not found: " + this.texturePath);
      } else {
         try {
            C_3148_ nativeImage = C_3148_.m_85058_(inputStream);
            this.size = nativeImage.getSize();
            C_4526_ tms = loadTextureMetadataSection(this.texturePath, new C_4526_(false, false));
            TextureUtil.prepareImage(this.m_117963_(), nativeImage.m_84982_(), nativeImage.m_85084_());
            nativeImage.m_85013_(0, 0, 0, 0, 0, nativeImage.m_84982_(), nativeImage.m_85084_(), tms.m_119115_(), tms.m_119116_(), false, true);
         } finally {
            IOUtils.closeQuietly(inputStream);
         }
      }
   }

   public static C_4526_ loadTextureMetadataSection(String texturePath, C_4526_ def) {
      String pathMeta = texturePath + ".mcmeta";
      String sectionName = "texture";
      InputStream inMeta = Shaders.getShaderPackResourceStream(pathMeta);
      if (inMeta != null) {
         BufferedReader brMeta = new BufferedReader(new InputStreamReader(inMeta));

         C_4526_ var9;
         try {
            JsonObject jsonMeta = new JsonParser().parse(brMeta).getAsJsonObject();
            JsonObject jsonMetaTexture = jsonMeta.getAsJsonObject(sectionName);
            if (jsonMetaTexture == null) {
               return def;
            }

            C_4526_ meta = C_4526_.f_119108_.m_6322_(jsonMetaTexture);
            if (meta == null) {
               return def;
            }

            var9 = meta;
         } catch (RuntimeException var13) {
            SMCLog.warning("Error reading metadata: " + pathMeta);
            SMCLog.warning(var13.getClass().getName() + ": " + var13.getMessage());
            return def;
         } finally {
            IOUtils.closeQuietly(brMeta);
            IOUtils.closeQuietly(inMeta);
         }

         return var9;
      } else {
         return def;
      }
   }

   public long getSize() {
      return this.size;
   }
}
