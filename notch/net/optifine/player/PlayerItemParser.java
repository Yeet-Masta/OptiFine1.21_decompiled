package net.optifine.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_3889_.C_3890_;
import net.optifine.Config;
import net.optifine.entity.model.CustomEntityModelParser;
import net.optifine.model.Attachment;
import net.optifine.model.AttachmentType;
import net.optifine.util.Json;

public class PlayerItemParser {
   private static JsonParser jsonParser = new JsonParser();
   public static final String ITEM_TYPE = "type";
   public static final String ITEM_TEXTURE_SIZE = "textureSize";
   public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
   public static final String ITEM_MODELS = "models";
   public static final String MODEL_ID = "id";
   public static final String MODEL_BASE_ID = "baseId";
   public static final String MODEL_TYPE = "type";
   public static final String MODEL_TEXTURE = "texture";
   public static final String MODEL_TEXTURE_SIZE = "textureSize";
   public static final String MODEL_ATTACH_TO = "attachTo";
   public static final String MODEL_INVERT_AXIS = "invertAxis";
   public static final String MODEL_MIRROR_TEXTURE = "mirrorTexture";
   public static final String MODEL_TRANSLATE = "translate";
   public static final String MODEL_ROTATE = "rotate";
   public static final String MODEL_SCALE = "scale";
   public static final String MODEL_ATTACHMENTS = "attachments";
   public static final String MODEL_BOXES = "boxes";
   public static final String MODEL_SPRITES = "sprites";
   public static final String MODEL_SUBMODEL = "submodel";
   public static final String MODEL_SUBMODELS = "submodels";
   public static final String BOX_TEXTURE_OFFSET = "textureOffset";
   public static final String BOX_COORDINATES = "coordinates";
   public static final String BOX_SIZE_ADD = "sizeAdd";
   public static final String BOX_UV_DOWN = "uvDown";
   public static final String BOX_UV_UP = "uvUp";
   public static final String BOX_UV_NORTH = "uvNorth";
   public static final String BOX_UV_SOUTH = "uvSouth";
   public static final String BOX_UV_WEST = "uvWest";
   public static final String BOX_UV_EAST = "uvEast";
   public static final String BOX_UV_FRONT = "uvFront";
   public static final String BOX_UV_BACK = "uvBack";
   public static final String BOX_UV_LEFT = "uvLeft";
   public static final String BOX_UV_RIGHT = "uvRight";
   public static final String ITEM_TYPE_MODEL = "PlayerItem";
   public static final String MODEL_TYPE_BOX = "ModelBox";
   private static AtomicInteger counter = new AtomicInteger();

   private PlayerItemParser() {
   }

   public static PlayerItemModel parseItemModel(JsonObject obj) {
      String type = Json.getString(obj, "type");
      if (!Config.equals(type, "PlayerItem")) {
         throw new JsonParseException("Unknown model type: " + type);
      } else {
         int[] textureSize = Json.parseIntArray(obj.get("textureSize"), 2);
         checkNull(textureSize, "Missing texture size");
         Dimension textureDim = new Dimension(textureSize[0], textureSize[1]);
         boolean usePlayerTexture = Json.getBoolean(obj, "usePlayerTexture", false);
         JsonArray models = (JsonArray)obj.get("models");
         checkNull(models, "Missing elements");
         Map mapModelJsons = new HashMap();
         List listModels = new ArrayList();
         new ArrayList();

         for (int i = 0; i < models.size(); i++) {
            JsonObject elem = (JsonObject)models.get(i);
            String baseId = Json.getString(elem, "baseId");
            if (baseId != null) {
               JsonObject baseObj = (JsonObject)mapModelJsons.get(baseId);
               if (baseObj == null) {
                  Config.warn("BaseID not found: " + baseId);
                  continue;
               }

               for (Entry<String, JsonElement> entry : baseObj.entrySet()) {
                  if (!elem.has((String)entry.getKey())) {
                     elem.add((String)entry.getKey(), (JsonElement)entry.getValue());
                  }
               }
            }

            String id = Json.getString(elem, "id");
            if (id != null) {
               if (!mapModelJsons.containsKey(id)) {
                  mapModelJsons.put(id, elem);
               } else {
                  Config.warn("Duplicate model ID: " + id);
               }
            }

            PlayerItemRenderer mr = parseItemRenderer(elem, textureDim);
            if (mr != null) {
               listModels.add(mr);
            }
         }

         PlayerItemRenderer[] modelRenderers = (PlayerItemRenderer[])listModels.toArray(new PlayerItemRenderer[listModels.size()]);
         return new PlayerItemModel(textureDim, usePlayerTexture, modelRenderers);
      }
   }

   private static void checkNull(Object obj, String msg) {
      if (obj == null) {
         throw new JsonParseException(msg);
      }
   }

   private static C_5265_ makeResourceLocation(String texture) {
      int pos = texture.indexOf(58);
      if (pos < 0) {
         return new C_5265_(texture);
      } else {
         String domain = texture.substring(0, pos);
         String path = texture.substring(pos + 1);
         return new C_5265_(domain, path);
      }
   }

   private static int parseAttachModel(String attachModelStr) {
      if (attachModelStr == null) {
         return 0;
      } else if (attachModelStr.equals("body")) {
         return 0;
      } else if (attachModelStr.equals("head")) {
         return 1;
      } else if (attachModelStr.equals("leftArm")) {
         return 2;
      } else if (attachModelStr.equals("rightArm")) {
         return 3;
      } else if (attachModelStr.equals("leftLeg")) {
         return 4;
      } else if (attachModelStr.equals("rightLeg")) {
         return 5;
      } else if (attachModelStr.equals("cape")) {
         return 6;
      } else {
         Config.warn("Unknown attachModel: " + attachModelStr);
         return 0;
      }
   }

   public static PlayerItemRenderer parseItemRenderer(JsonObject elem, Dimension textureDim) {
      String type = Json.getString(elem, "type");
      if (!Config.equals(type, "ModelBox")) {
         Config.warn("Unknown model type: " + type);
         return null;
      } else {
         String attachToStr = Json.getString(elem, "attachTo");
         int attachTo = parseAttachModel(attachToStr);
         C_3840_ modelBase = new ModelPlayerItem(C_4168_::m_110458_);
         modelBase.textureWidth = textureDim.width;
         modelBase.textureHeight = textureDim.height;
         C_3889_ mr = parseModelRenderer(elem, modelBase, null, null);
         return new PlayerItemRenderer(attachTo, mr);
      }
   }

   public static C_3889_ parseModelRenderer(JsonObject elem, C_3840_ modelBase, int[] parentTextureSize, String basePath) {
      List<C_3890_> cubeList = new ArrayList();
      Map<String, C_3889_> childModels = new HashMap();
      C_3889_ mr = new C_3889_(cubeList, childModels);
      mr.setCustom(true);
      mr.setTextureSize(modelBase.textureWidth, modelBase.textureHeight);
      String id = Json.getString(elem, "id");
      mr.setId(id);
      float scale = Json.getFloat(elem, "scale", 1.0F);
      mr.f_233553_ = scale;
      mr.f_233554_ = scale;
      mr.f_233555_ = scale;
      String texture = Json.getString(elem, "texture");
      if (texture != null) {
         mr.setTextureLocation(CustomEntityModelParser.getResourceLocation(basePath, texture, ".png"));
      }

      int[] textureSize = Json.parseIntArray(elem.get("textureSize"), 2);
      if (textureSize == null) {
         textureSize = parentTextureSize;
      }

      if (textureSize != null) {
         mr.setTextureSize(textureSize[0], textureSize[1]);
      }

      String invertAxis = Json.getString(elem, "invertAxis", "").toLowerCase();
      boolean invertX = invertAxis.contains("x");
      boolean invertY = invertAxis.contains("y");
      boolean invertZ = invertAxis.contains("z");
      float[] translate = Json.parseFloatArray(elem.get("translate"), 3, new float[3]);
      if (invertX) {
         translate[0] = -translate[0];
      }

      if (invertY) {
         translate[1] = -translate[1];
      }

      if (invertZ) {
         translate[2] = -translate[2];
      }

      float[] rotateAngles = Json.parseFloatArray(elem.get("rotate"), 3, new float[3]);

      for (int i = 0; i < rotateAngles.length; i++) {
         rotateAngles[i] = rotateAngles[i] / 180.0F * (float) Math.PI;
      }

      if (invertX) {
         rotateAngles[0] = -rotateAngles[0];
      }

      if (invertY) {
         rotateAngles[1] = -rotateAngles[1];
      }

      if (invertZ) {
         rotateAngles[2] = -rotateAngles[2];
      }

      mr.m_104227_(translate[0], translate[1], translate[2]);
      mr.f_104203_ = rotateAngles[0];
      mr.f_104204_ = rotateAngles[1];
      mr.f_104205_ = rotateAngles[2];
      String mirrorTexture = Json.getString(elem, "mirrorTexture", "").toLowerCase();
      boolean invertU = mirrorTexture.contains("u");
      boolean invertV = mirrorTexture.contains("v");
      if (invertU) {
         mr.mirror = true;
      }

      if (invertV) {
         mr.mirrorV = true;
      }

      Attachment[] attachments = parseAttachments(elem.getAsJsonObject("attachments"));
      mr.setAttachments(attachments);
      JsonArray boxes = elem.getAsJsonArray("boxes");
      if (boxes != null) {
         for (int i = 0; i < boxes.size(); i++) {
            JsonObject box = boxes.get(i).getAsJsonObject();
            float[] textureOffset = Json.parseFloatArray(box.get("textureOffset"), 2);
            float[][] faceUvs = parseFaceUvs(box);
            if (textureOffset == null && faceUvs == null) {
               throw new JsonParseException("Texture offset not specified");
            }

            float[] coordinates = Json.parseFloatArray(box.get("coordinates"), 6);
            if (coordinates == null) {
               throw new JsonParseException("Coordinates not specified");
            }

            if (invertX) {
               coordinates[0] = -coordinates[0] - coordinates[3];
            }

            if (invertY) {
               coordinates[1] = -coordinates[1] - coordinates[4];
            }

            if (invertZ) {
               coordinates[2] = -coordinates[2] - coordinates[5];
            }

            float sizeAdd = Json.getFloat(box, "sizeAdd", 0.0F);
            if (faceUvs != null) {
               mr.addBox(faceUvs, coordinates[0], coordinates[1], coordinates[2], coordinates[3], coordinates[4], coordinates[5], sizeAdd);
            } else {
               mr.setTextureOffset(textureOffset[0], textureOffset[1]);
               mr.addBox(
                  coordinates[0],
                  coordinates[1],
                  coordinates[2],
                  (float)((int)coordinates[3]),
                  (float)((int)coordinates[4]),
                  (float)((int)coordinates[5]),
                  sizeAdd
               );
            }
         }
      }

      JsonArray sprites = elem.getAsJsonArray("sprites");
      if (sprites != null) {
         for (int i = 0; i < sprites.size(); i++) {
            JsonObject sprite = sprites.get(i).getAsJsonObject();
            int[] textureOffsetx = Json.parseIntArray(sprite.get("textureOffset"), 2);
            if (textureOffsetx == null) {
               throw new JsonParseException("Texture offset not specified");
            }

            float[] coordinatesx = Json.parseFloatArray(sprite.get("coordinates"), 6);
            if (coordinatesx == null) {
               throw new JsonParseException("Coordinates not specified");
            }

            if (invertX) {
               coordinatesx[0] = -coordinatesx[0] - coordinatesx[3];
            }

            if (invertY) {
               coordinatesx[1] = -coordinatesx[1] - coordinatesx[4];
            }

            if (invertZ) {
               coordinatesx[2] = -coordinatesx[2] - coordinatesx[5];
            }

            float sizeAdd = Json.getFloat(sprite, "sizeAdd", 0.0F);
            mr.setTextureOffset((float)textureOffsetx[0], (float)textureOffsetx[1]);
            mr.addSprite(coordinatesx[0], coordinatesx[1], coordinatesx[2], (int)coordinatesx[3], (int)coordinatesx[4], (int)coordinatesx[5], sizeAdd);
         }
      }

      JsonObject submodel = (JsonObject)elem.get("submodel");
      if (submodel != null) {
         C_3889_ subMr = parseModelRenderer(submodel, modelBase, textureSize, basePath);
         mr.addChildModel(getNextModelId(), subMr);
      }

      JsonArray submodels = (JsonArray)elem.get("submodels");
      if (submodels != null) {
         for (int i = 0; i < submodels.size(); i++) {
            JsonObject sm = (JsonObject)submodels.get(i);
            C_3889_ subMr = parseModelRenderer(sm, modelBase, textureSize, basePath);
            if (subMr.getId() != null) {
               C_3889_ subMrId = mr.getChild(subMr.getId());
               if (subMrId != null) {
                  Config.warn("Duplicate model ID: " + subMr.getId());
               }
            }

            mr.addChildModel(getNextModelId(), subMr);
         }
      }

      return mr;
   }

   private static Attachment[] parseAttachments(JsonObject jo) {
      List<Attachment> list = new ArrayList();

      for (AttachmentType type : AttachmentType.values()) {
         Attachment at = Attachment.parse(jo, type);
         if (at != null) {
            list.add(at);
         }
      }

      return list.isEmpty() ? null : (Attachment[])list.toArray(new Attachment[list.size()]);
   }

   public static String getNextModelId() {
      return "MR-" + counter.getAndIncrement();
   }

   private static float[][] parseFaceUvs(JsonObject box) {
      float[][] uvs = new float[][]{
         Json.parseFloatArray(box.get("uvDown"), 4),
         Json.parseFloatArray(box.get("uvUp"), 4),
         Json.parseFloatArray(box.get("uvNorth"), 4),
         Json.parseFloatArray(box.get("uvSouth"), 4),
         Json.parseFloatArray(box.get("uvWest"), 4),
         Json.parseFloatArray(box.get("uvEast"), 4)
      };
      if (uvs[2] == null) {
         uvs[2] = Json.parseFloatArray(box.get("uvFront"), 4);
      }

      if (uvs[3] == null) {
         uvs[3] = Json.parseFloatArray(box.get("uvBack"), 4);
      }

      if (uvs[4] == null) {
         uvs[4] = Json.parseFloatArray(box.get("uvLeft"), 4);
      }

      if (uvs[5] == null) {
         uvs[5] = Json.parseFloatArray(box.get("uvRight"), 4);
      }

      boolean defined = false;

      for (int i = 0; i < uvs.length; i++) {
         if (uvs[i] != null) {
            defined = true;
         }
      }

      return !defined ? null : uvs;
   }
}
