package net.optifine.shaders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.IOUtils;

public class SimpleShaderTexture extends AbstractTexture {
   private String texturePath;
   private long size = 0L;

   public SimpleShaderTexture(String texturePath) {
      this.texturePath = texturePath;
   }

   public void m_6704_(ResourceManager resourceManager) throws IOException {
      this.m_117964_();
      InputStream inputStream = Shaders.getShaderPackResourceStream(this.texturePath);
      if (inputStream == null) {
         throw new FileNotFoundException("Shader texture not found: " + this.texturePath);
      } else {
         try {
            NativeImage nativeImage = NativeImage.m_85058_(inputStream);
            this.size = nativeImage.getSize();
            TextureMetadataSection tms = loadTextureMetadataSection(this.texturePath, new TextureMetadataSection(false, false));
            TextureUtil.prepareImage(this.m_117963_(), nativeImage.m_84982_(), nativeImage.m_85084_());
            nativeImage.m_85013_(0, 0, 0, 0, 0, nativeImage.m_84982_(), nativeImage.m_85084_(), tms.m_119115_(), tms.m_119116_(), false, true);
         } finally {
            IOUtils.closeQuietly(inputStream);
         }

      }
   }

   public static TextureMetadataSection loadTextureMetadataSection(String texturePath, TextureMetadataSection def) {
      String pathMeta = texturePath + ".mcmeta";
      String sectionName = "texture";
      InputStream inMeta = Shaders.getShaderPackResourceStream(pathMeta);
      if (inMeta != null) {
         BufferedReader brMeta = new BufferedReader(new InputStreamReader(inMeta));

         try {
            JsonObject jsonMeta = (new JsonParser()).parse(brMeta).getAsJsonObject();
            JsonObject jsonMetaTexture = jsonMeta.getAsJsonObject(sectionName);
            if (jsonMetaTexture != null) {
               TextureMetadataSection meta = TextureMetadataSection.f_119108_.m_6322_(jsonMetaTexture);
               if (meta != null) {
                  TextureMetadataSection var9 = meta;
                  return var9;
               }
            }
         } catch (RuntimeException var13) {
            SMCLog.warning("Error reading metadata: " + pathMeta);
            String var10000 = var13.getClass().getName();
            SMCLog.warning(var10000 + ": " + var13.getMessage());
         } finally {
            IOUtils.closeQuietly(brMeta);
            IOUtils.closeQuietly(inMeta);
         }
      }

      return def;
   }

   public long getSize() {
      return this.size;
   }
}
