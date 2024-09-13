package net.optifine.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.NativeImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.optifine.Config;
import net.optifine.http.HttpPipeline;
import net.optifine.http.HttpUtils;
import net.optifine.util.Json;

public class PlayerConfigurationParser {
   private String player = null;
   public static String CONFIG_ITEMS;
   public static String ITEM_TYPE;
   public static String ITEM_ACTIVE;

   public PlayerConfigurationParser(String player) {
      this.player = player;
   }

   public PlayerConfiguration parsePlayerConfiguration(JsonElement je) {
      if (je == null) {
         throw new JsonParseException("JSON object is null, player: " + this.player);
      } else {
         JsonObject jo = (JsonObject)je;
         PlayerConfiguration pc = new PlayerConfiguration();
         JsonArray items = (JsonArray)jo.get("items");
         if (items != null) {
            for (int i = 0; i < items.size(); i++) {
               JsonObject item = (JsonObject)items.get(i);
               boolean active = Json.getBoolean(item, "active", true);
               if (active) {
                  String type = Json.getString(item, "type");
                  if (type == null) {
                     Config.warn("Item type is null, player: " + this.player);
                  } else {
                     String modelPath = Json.getString(item, "model");
                     if (modelPath == null) {
                        modelPath = "items/" + type + "/model.cfg";
                     }

                     PlayerItemModel model = this.downloadModel(modelPath);
                     if (model != null) {
                        if (!model.isUsePlayerTexture()) {
                           String texturePath = Json.getString(item, "texture");
                           if (texturePath == null) {
                              texturePath = "items/" + type + "/users/" + this.player + ".png";
                           }

                           NativeImage image = this.downloadTextureImage(texturePath);
                           if (image == null) {
                              continue;
                           }

                           model.setTextureImage(image);
                           ResourceLocation loc = new ResourceLocation("optifine.net", texturePath);
                           model.setTextureLocation(loc);
                        }

                        pc.addPlayerItemModel(model);
                     }
                  }
               }
            }
         }

         return pc;
      }
   }

   private NativeImage downloadTextureImage(String texturePath) {
      String textureUrl = HttpUtils.getPlayerItemsUrl() + "/" + texturePath;

      try {
         byte[] body = HttpPipeline.get(textureUrl, Minecraft.m_91087_().m_91096_());
         return NativeImage.m_85058_(new ByteArrayInputStream(body));
      } catch (IOException var5) {
         Config.warn("Error loading item texture " + texturePath + ": " + var5.getClass().getName() + ": " + var5.getMessage());
         return null;
      }
   }

   private PlayerItemModel downloadModel(String modelPath) {
      String modelUrl = HttpUtils.getPlayerItemsUrl() + "/" + modelPath;

      try {
         byte[] bytes = HttpPipeline.get(modelUrl, Minecraft.m_91087_().m_91096_());
         String jsonStr = new String(bytes, "ASCII");
         JsonParser jp = new JsonParser();
         JsonObject jo = (JsonObject)jp.m_82160_(jsonStr);
         return PlayerItemParser.parseItemModel(jo);
      } catch (Exception var8) {
         Config.warn("Error loading item model " + modelPath + ": " + var8.getClass().getName() + ": " + var8.getMessage());
         return null;
      }
   }
}
