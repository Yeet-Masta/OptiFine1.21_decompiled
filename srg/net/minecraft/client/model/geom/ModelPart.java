package net.minecraft.client.model.geom;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntities;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.entity.model.anim.ModelUpdater;
import net.optifine.model.Attachment;
import net.optifine.model.AttachmentPath;
import net.optifine.model.AttachmentPaths;
import net.optifine.model.AttachmentType;
import net.optifine.model.ModelSprite;
import net.optifine.render.BoxVertexPositions;
import net.optifine.render.RenderPositions;
import net.optifine.render.VertexPosition;
import net.optifine.shaders.Shaders;
import net.optifine.util.MathUtils;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class ModelPart {
   public static final float f_233552_ = 1.0F;
   public float f_104200_;
   public float f_104201_;
   public float f_104202_;
   public float f_104203_;
   public float f_104204_;
   public float f_104205_;
   public float f_233553_ = 1.0F;
   public float f_233554_ = 1.0F;
   public float f_233555_ = 1.0F;
   public boolean f_104207_ = true;
   public boolean f_233556_;
   public final List<ModelPart.Cube> f_104212_;
   public final Map<String, ModelPart> f_104213_;
   private String name;
   public List<ModelPart> childModelsList;
   public List<ModelSprite> spriteList = new ArrayList();
   public boolean mirrorV = false;
   private ResourceLocation textureLocation = null;
   private String id = null;
   private ModelUpdater modelUpdater;
   private LevelRenderer renderGlobal = Config.getRenderGlobal();
   private boolean custom;
   private Attachment[] attachments;
   private AttachmentPaths attachmentPaths;
   private boolean attachmentPathsChecked;
   private ModelPart parent;
   public float textureWidth = 64.0F;
   public float textureHeight = 32.0F;
   public float textureOffsetX;
   public float textureOffsetY;
   public boolean mirror;
   public static final Set<Direction> ALL_VISIBLE = EnumSet.allOf(Direction.class);
   private PartPose f_233557_ = PartPose.f_171404_;

   public ModelPart setTextureOffset(float x, float y) {
      this.textureOffsetX = x;
      this.textureOffsetY = y;
      return this;
   }

   public ModelPart setTextureSize(int textureWidthIn, int textureHeightIn) {
      this.textureWidth = (float)textureWidthIn;
      this.textureHeight = (float)textureHeightIn;
      return this;
   }

   public ModelPart(List<ModelPart.Cube> cubeListIn, Map<String, ModelPart> childModelsIn) {
      if (cubeListIn instanceof ImmutableList) {
         cubeListIn = new ArrayList(cubeListIn);
      }

      this.f_104212_ = cubeListIn;
      this.f_104213_ = childModelsIn;
      this.childModelsList = new ArrayList(this.f_104213_.values());

      for (ModelPart child : this.childModelsList) {
         child.setParent(this);
      }
   }

   public PartPose m_171308_() {
      return PartPose.m_171423_(this.f_104200_, this.f_104201_, this.f_104202_, this.f_104203_, this.f_104204_, this.f_104205_);
   }

   public PartPose m_233566_() {
      return this.f_233557_;
   }

   public void m_233560_(PartPose partPoseIn) {
      this.f_233557_ = partPoseIn;
   }

   public void m_233569_() {
      this.m_171322_(this.f_233557_);
   }

   public void m_171322_(PartPose partPoseIn) {
      if (!this.custom) {
         this.f_104200_ = partPoseIn.f_171405_;
         this.f_104201_ = partPoseIn.f_171406_;
         this.f_104202_ = partPoseIn.f_171407_;
         this.f_104203_ = partPoseIn.f_171408_;
         this.f_104204_ = partPoseIn.f_171409_;
         this.f_104205_ = partPoseIn.f_171410_;
         this.f_233553_ = 1.0F;
         this.f_233554_ = 1.0F;
         this.f_233555_ = 1.0F;
      }
   }

   public void m_104315_(ModelPart modelRendererIn) {
      this.f_233553_ = modelRendererIn.f_233553_;
      this.f_233554_ = modelRendererIn.f_233554_;
      this.f_233555_ = modelRendererIn.f_233555_;
      this.f_104203_ = modelRendererIn.f_104203_;
      this.f_104204_ = modelRendererIn.f_104204_;
      this.f_104205_ = modelRendererIn.f_104205_;
      this.f_104200_ = modelRendererIn.f_104200_;
      this.f_104201_ = modelRendererIn.f_104201_;
      this.f_104202_ = modelRendererIn.f_104202_;
   }

   public boolean m_233562_(String keyIn) {
      return this.f_104213_.containsKey(keyIn);
   }

   public ModelPart m_171324_(String nameIn) {
      ModelPart modelpart = (ModelPart)this.f_104213_.get(nameIn);
      if (modelpart == null) {
         throw new NoSuchElementException("Can't find part " + nameIn);
      } else {
         return modelpart;
      }
   }

   public void m_104227_(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn) {
      this.f_104200_ = rotationPointXIn;
      this.f_104201_ = rotationPointYIn;
      this.f_104202_ = rotationPointZIn;
   }

   public void m_171327_(float xRotIn, float yRotIn, float zRotIn) {
      this.f_104203_ = xRotIn;
      this.f_104204_ = yRotIn;
      this.f_104205_ = zRotIn;
   }

   public void m_104301_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn) {
      this.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, -1);
   }

   public void m_104306_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
      this.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn, true);
   }

   public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn, boolean updateModel) {
      if (this.f_104207_ && (!this.f_104212_.isEmpty() || !this.f_104213_.isEmpty() || !this.spriteList.isEmpty())) {
         RenderType lastRenderType = null;
         BufferBuilder lastBufferBuilder = null;
         MultiBufferSource.BufferSource renderTypeBuffer = null;
         if (this.textureLocation != null) {
            if (this.renderGlobal.renderOverlayEyes) {
               return;
            }

            renderTypeBuffer = bufferIn.getRenderTypeBuffer();
            if (renderTypeBuffer != null) {
               VertexConsumer secondaryBuilder = bufferIn.getSecondaryBuilder();
               lastRenderType = renderTypeBuffer.getLastRenderType();
               lastBufferBuilder = renderTypeBuffer.getStartedBuffer(lastRenderType);
               bufferIn = renderTypeBuffer.getBuffer(this.textureLocation, bufferIn);
               if (secondaryBuilder != null) {
                  bufferIn = VertexMultiConsumer.m_86168_(secondaryBuilder, bufferIn);
               }
            }
         }

         if (updateModel && CustomEntityModels.isActive()) {
            this.updateModel();
         }

         matrixStackIn.m_85836_();
         this.m_104299_(matrixStackIn);
         if (!this.f_233556_) {
            this.m_104290_(matrixStackIn.m_85850_(), bufferIn, packedLightIn, packedOverlayIn, colorIn);
         }

         int childModelsSize = this.childModelsList.size();

         for (int ix = 0; ix < childModelsSize; ix++) {
            ModelPart modelpart = (ModelPart)this.childModelsList.get(ix);
            modelpart.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn, false);
         }

         int spriteListSize = this.spriteList.size();

         for (int ix = 0; ix < spriteListSize; ix++) {
            ModelSprite sprite = (ModelSprite)this.spriteList.get(ix);
            sprite.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
         }

         matrixStackIn.m_85849_();
         if (renderTypeBuffer != null) {
            renderTypeBuffer.restoreRenderState(lastRenderType, lastBufferBuilder);
         }
      }
   }

   public void m_171309_(PoseStack matrixStackIn, ModelPart.Visitor visitorIn) {
      this.m_171312_(matrixStackIn, visitorIn, "");
   }

   private void m_171312_(PoseStack matrixStackIn, ModelPart.Visitor visitorIn, String pathIn) {
      if (!this.f_104212_.isEmpty() || !this.f_104213_.isEmpty()) {
         matrixStackIn.m_85836_();
         this.m_104299_(matrixStackIn);
         PoseStack.Pose posestack$pose = matrixStackIn.m_85850_();

         for (int i = 0; i < this.f_104212_.size(); i++) {
            visitorIn.m_171341_(posestack$pose, pathIn, i, (ModelPart.Cube)this.f_104212_.get(i));
         }

         String s = pathIn + "/";
         this.f_104213_.forEach((nameIn, partIn) -> partIn.m_171312_(matrixStackIn, visitorIn, s + nameIn));
         matrixStackIn.m_85849_();
      }
   }

   public void m_104299_(PoseStack matrixStackIn) {
      matrixStackIn.m_252880_(this.f_104200_ / 16.0F, this.f_104201_ / 16.0F, this.f_104202_ / 16.0F);
      if (this.f_104203_ != 0.0F || this.f_104204_ != 0.0F || this.f_104205_ != 0.0F) {
         matrixStackIn.m_252781_(new Quaternionf().rotationZYX(this.f_104205_, this.f_104204_, this.f_104203_));
      }

      if (this.f_233553_ != 1.0F || this.f_233554_ != 1.0F || this.f_233555_ != 1.0F) {
         matrixStackIn.m_85841_(this.f_233553_, this.f_233554_, this.f_233555_);
      }
   }

   private void m_104290_(PoseStack.Pose matrixEntryIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
      boolean shadersVelocity = Config.isShaders() && Shaders.useVelocityAttrib && Config.isMinecraftThread();
      int cubeListSize = this.f_104212_.size();

      for (int ic = 0; ic < cubeListSize; ic++) {
         ModelPart.Cube modelpart$cube = (ModelPart.Cube)this.f_104212_.get(ic);
         VertexPosition[][] boxPos = null;
         if (shadersVelocity) {
            IRandomEntity entity = RandomEntities.getRandomEntityRendered();
            if (entity != null) {
               boxPos = modelpart$cube.getBoxVertexPositions(entity.getId());
            }
         }

         modelpart$cube.compile(matrixEntryIn, bufferIn, packedLightIn, packedOverlayIn, colorIn, boxPos);
      }
   }

   public ModelPart.Cube m_233558_(RandomSource randomIn) {
      return (ModelPart.Cube)this.f_104212_.get(randomIn.m_188503_(this.f_104212_.size()));
   }

   public boolean m_171326_() {
      return this.f_104212_.isEmpty();
   }

   public void m_252854_(Vector3f posIn) {
      this.f_104200_ = this.f_104200_ + posIn.x();
      this.f_104201_ = this.f_104201_ + posIn.y();
      this.f_104202_ = this.f_104202_ + posIn.z();
   }

   public void m_252899_(Vector3f rotIn) {
      this.f_104203_ = this.f_104203_ + rotIn.x();
      this.f_104204_ = this.f_104204_ + rotIn.y();
      this.f_104205_ = this.f_104205_ + rotIn.z();
   }

   public void m_253072_(Vector3f scaleIn) {
      this.f_233553_ = this.f_233553_ + scaleIn.x();
      this.f_233554_ = this.f_233554_ + scaleIn.y();
      this.f_233555_ = this.f_233555_ + scaleIn.z();
   }

   public Stream<ModelPart> m_171331_() {
      return Stream.concat(Stream.of(this), this.f_104213_.values().stream().flatMap(ModelPart::m_171331_));
   }

   public void addSprite(float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, float sizeAdd) {
      this.spriteList.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, posX, posY, posZ, sizeX, sizeY, sizeZ, sizeAdd));
   }

   public ResourceLocation getTextureLocation() {
      return this.textureLocation;
   }

   public void setTextureLocation(ResourceLocation textureLocation) {
      this.textureLocation = textureLocation;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void addBox(float[][] faceUvs, float x, float y, float z, float dx, float dy, float dz, float delta) {
      this.f_104212_.add(new ModelPart.Cube(faceUvs, x, y, z, dx, dy, dz, delta, delta, delta, this.mirror, this.textureWidth, this.textureHeight));
   }

   public void addBox(float x, float y, float z, float width, float height, float depth, float delta) {
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, this.mirror, false);
   }

   private void addBox(
      float texOffX,
      float texOffY,
      float x,
      float y,
      float z,
      float width,
      float height,
      float depth,
      float deltaX,
      float deltaY,
      float deltaZ,
      boolean mirror,
      boolean dummyIn
   ) {
      this.f_104212_
         .add(
            new ModelPart.Cube(
               texOffX, texOffY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, mirror, this.textureWidth, this.textureHeight, ALL_VISIBLE
            )
         );
   }

   public ModelPart getChildModelDeep(String name) {
      if (name == null) {
         return null;
      } else if (this.f_104213_.containsKey(name)) {
         return this.m_171324_(name);
      } else {
         if (this.f_104213_ != null) {
            for (String key : this.f_104213_.keySet()) {
               ModelPart child = (ModelPart)this.f_104213_.get(key);
               ModelPart mr = child.getChildModelDeep(name);
               if (mr != null) {
                  return mr;
               }
            }
         }

         return null;
      }
   }

   public ModelPart getChild(String id) {
      if (id == null) {
         return null;
      } else {
         if (this.f_104213_ != null) {
            for (String key : this.f_104213_.keySet()) {
               ModelPart child = (ModelPart)this.f_104213_.get(key);
               if (id.equals(child.getId())) {
                  return child;
               }
            }
         }

         return null;
      }
   }

   public ModelPart getChildDeep(String id) {
      if (id == null) {
         return null;
      } else {
         ModelPart mrChild = this.getChild(id);
         if (mrChild != null) {
            return mrChild;
         } else {
            if (this.f_104213_ != null) {
               for (String key : this.f_104213_.keySet()) {
                  ModelPart child = (ModelPart)this.f_104213_.get(key);
                  ModelPart mr = child.getChildDeep(id);
                  if (mr != null) {
                     return mr;
                  }
               }
            }

            return null;
         }
      }
   }

   public ModelUpdater getModelUpdater() {
      return this.modelUpdater;
   }

   public void setModelUpdater(ModelUpdater modelUpdater) {
      this.modelUpdater = modelUpdater;
   }

   public void addChildModel(String name, ModelPart part) {
      if (part != null) {
         this.f_104213_.put(name, part);
         this.childModelsList = new ArrayList(this.f_104213_.values());
         part.setParent(this);
         if (part.getName() == null) {
            part.setName(name);
         }
      }
   }

   public String getUniqueChildModelName(String name) {
      String baseName = name;

      for (int counter = 2; this.f_104213_.containsKey(name); counter++) {
         name = baseName + "-" + counter;
      }

      return name;
   }

   private void updateModel() {
      if (this.modelUpdater != null) {
         this.modelUpdater.update();
      } else {
         int childModelsSize = this.childModelsList.size();

         for (int ix = 0; ix < childModelsSize; ix++) {
            ModelPart modelpart = (ModelPart)this.childModelsList.get(ix);
            modelpart.updateModel();
         }
      }
   }

   public boolean isCustom() {
      return this.custom;
   }

   public void setCustom(boolean custom) {
      this.custom = custom;
   }

   public ModelPart getParent() {
      return this.parent;
   }

   public void setParent(ModelPart parent) {
      this.parent = parent;
   }

   public Attachment[] getAttachments() {
      return this.attachments;
   }

   public void setAttachments(Attachment[] attachments) {
      this.attachments = attachments;
   }

   public boolean applyAttachmentTransform(AttachmentType typeIn, PoseStack matrixStackIn) {
      if (this.attachmentPathsChecked && this.attachmentPaths == null) {
         return false;
      } else {
         AttachmentPath ap = this.getAttachmentPath(typeIn);
         if (ap == null) {
            return false;
         } else {
            ap.applyTransform(matrixStackIn);
            return true;
         }
      }
   }

   private AttachmentPath getAttachmentPath(AttachmentType typeIn) {
      if (!this.attachmentPathsChecked) {
         this.attachmentPathsChecked = true;
         this.attachmentPaths = new AttachmentPaths();
         this.collectAttachmentPaths(new ArrayList(), this.attachmentPaths);
         if (this.attachmentPaths.isEmpty()) {
            this.attachmentPaths = null;
         }
      }

      return this.attachmentPaths == null ? null : this.attachmentPaths.getVisiblePath(typeIn);
   }

   private void collectAttachmentPaths(List<ModelPart> parents, AttachmentPaths paths) {
      parents.add(this);
      if (this.attachments != null) {
         paths.addPaths(parents, this.attachments);
      }

      for (ModelPart mp : this.childModelsList) {
         mp.collectAttachmentPaths(parents, paths);
      }

      parents.remove(parents.size() - 1);
   }

   public String toString() {
      return "name: "
         + this.name
         + ", id: "
         + this.id
         + ", boxes: "
         + (this.f_104212_ != null ? this.f_104212_.size() : null)
         + ", submodels: "
         + (this.f_104213_ != null ? this.f_104213_.size() : null)
         + ", custom: "
         + this.custom;
   }

   public static class Cube {
      private final ModelPart.Polygon[] f_104341_;
      public final float f_104335_;
      public final float f_104336_;
      public final float f_104337_;
      public final float f_104338_;
      public final float f_104339_;
      public final float f_104340_;
      private BoxVertexPositions boxVertexPositions;
      private RenderPositions[] renderPositions;

      public Cube(
         int texOffX,
         int texOffY,
         float x,
         float y,
         float z,
         float width,
         float height,
         float depth,
         float deltaX,
         float deltaY,
         float deltaZ,
         boolean mirror,
         float texWidth,
         float texHeight,
         Set<Direction> directionsIn
      ) {
         this((float)texOffX, (float)texOffY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, mirror, texWidth, texHeight, directionsIn);
      }

      public Cube(
         float texOffX,
         float texOffY,
         float x,
         float y,
         float z,
         float width,
         float height,
         float depth,
         float deltaX,
         float deltaY,
         float deltaZ,
         boolean mirror,
         float texWidth,
         float texHeight,
         Set<Direction> directionsIn
      ) {
         this.f_104335_ = x;
         this.f_104336_ = y;
         this.f_104337_ = z;
         this.f_104338_ = x + width;
         this.f_104339_ = y + height;
         this.f_104340_ = z + depth;
         this.f_104341_ = new ModelPart.Polygon[directionsIn.size()];
         float f = x + width;
         float f1 = y + height;
         float f2 = z + depth;
         x -= deltaX;
         y -= deltaY;
         z -= deltaZ;
         f += deltaX;
         f1 += deltaY;
         f2 += deltaZ;
         if (mirror) {
            float f3 = f;
            f = x;
            x = f3;
         }

         ModelPart.Vertex modelpart$vertex7 = new ModelPart.Vertex(x, y, z, 0.0F, 0.0F);
         ModelPart.Vertex modelpart$vertex = new ModelPart.Vertex(f, y, z, 0.0F, 8.0F);
         ModelPart.Vertex modelpart$vertex1 = new ModelPart.Vertex(f, f1, z, 8.0F, 8.0F);
         ModelPart.Vertex modelpart$vertex2 = new ModelPart.Vertex(x, f1, z, 8.0F, 0.0F);
         ModelPart.Vertex modelpart$vertex3 = new ModelPart.Vertex(x, y, f2, 0.0F, 0.0F);
         ModelPart.Vertex modelpart$vertex4 = new ModelPart.Vertex(f, y, f2, 0.0F, 8.0F);
         ModelPart.Vertex modelpart$vertex5 = new ModelPart.Vertex(f, f1, f2, 8.0F, 8.0F);
         ModelPart.Vertex modelpart$vertex6 = new ModelPart.Vertex(x, f1, f2, 8.0F, 0.0F);
         float f5 = texOffX + depth;
         float f6 = texOffX + depth + width;
         float f7 = texOffX + depth + width + width;
         float f8 = texOffX + depth + width + depth;
         float f9 = texOffX + depth + width + depth + width;
         float f11 = texOffY + depth;
         float f12 = texOffY + depth + height;
         int i = 0;
         if (directionsIn.contains(Direction.DOWN)) {
            this.f_104341_[i++] = new ModelPart.Polygon(
               new ModelPart.Vertex[]{modelpart$vertex4, modelpart$vertex3, modelpart$vertex7, modelpart$vertex},
               f5,
               texOffY,
               f6,
               f11,
               texWidth,
               texHeight,
               mirror,
               Direction.DOWN
            );
         }

         if (directionsIn.contains(Direction.UP)) {
            this.f_104341_[i++] = new ModelPart.Polygon(
               new ModelPart.Vertex[]{modelpart$vertex1, modelpart$vertex2, modelpart$vertex6, modelpart$vertex5},
               f6,
               f11,
               f7,
               texOffY,
               texWidth,
               texHeight,
               mirror,
               Direction.UP
            );
         }

         if (directionsIn.contains(Direction.WEST)) {
            this.f_104341_[i++] = new ModelPart.Polygon(
               new ModelPart.Vertex[]{modelpart$vertex7, modelpart$vertex3, modelpart$vertex6, modelpart$vertex2},
               texOffX,
               f11,
               f5,
               f12,
               texWidth,
               texHeight,
               mirror,
               Direction.WEST
            );
         }

         if (directionsIn.contains(Direction.NORTH)) {
            this.f_104341_[i++] = new ModelPart.Polygon(
               new ModelPart.Vertex[]{modelpart$vertex, modelpart$vertex7, modelpart$vertex2, modelpart$vertex1},
               f5,
               f11,
               f6,
               f12,
               texWidth,
               texHeight,
               mirror,
               Direction.NORTH
            );
         }

         if (directionsIn.contains(Direction.EAST)) {
            this.f_104341_[i++] = new ModelPart.Polygon(
               new ModelPart.Vertex[]{modelpart$vertex4, modelpart$vertex, modelpart$vertex1, modelpart$vertex5},
               f6,
               f11,
               f8,
               f12,
               texWidth,
               texHeight,
               mirror,
               Direction.EAST
            );
         }

         if (directionsIn.contains(Direction.SOUTH)) {
            this.f_104341_[i] = new ModelPart.Polygon(
               new ModelPart.Vertex[]{modelpart$vertex3, modelpart$vertex4, modelpart$vertex5, modelpart$vertex6},
               f8,
               f11,
               f9,
               f12,
               texWidth,
               texHeight,
               mirror,
               Direction.SOUTH
            );
         }

         this.renderPositions = collectRenderPositions(this.f_104341_);
      }

      public Cube(
         float[][] faceUvs,
         float x,
         float y,
         float z,
         float width,
         float height,
         float depth,
         float deltaX,
         float deltaY,
         float deltaZ,
         boolean mirorIn,
         float texWidth,
         float texHeight
      ) {
         this.f_104335_ = x;
         this.f_104336_ = y;
         this.f_104337_ = z;
         this.f_104338_ = x + width;
         this.f_104339_ = y + height;
         this.f_104340_ = z + depth;
         this.f_104341_ = new ModelPart.Polygon[6];
         float f = x + width;
         float f1 = y + height;
         float f2 = z + depth;
         x -= deltaX;
         y -= deltaY;
         z -= deltaZ;
         f += deltaX;
         f1 += deltaY;
         f2 += deltaZ;
         if (mirorIn) {
            float f3 = f;
            f = x;
            x = f3;
         }

         ModelPart.Vertex pos0 = new ModelPart.Vertex(x, y, z, 0.0F, 0.0F);
         ModelPart.Vertex pos1 = new ModelPart.Vertex(f, y, z, 0.0F, 8.0F);
         ModelPart.Vertex pos2 = new ModelPart.Vertex(f, f1, z, 8.0F, 8.0F);
         ModelPart.Vertex pos3 = new ModelPart.Vertex(x, f1, z, 8.0F, 0.0F);
         ModelPart.Vertex pos4 = new ModelPart.Vertex(x, y, f2, 0.0F, 0.0F);
         ModelPart.Vertex pos5 = new ModelPart.Vertex(f, y, f2, 0.0F, 8.0F);
         ModelPart.Vertex pos6 = new ModelPart.Vertex(f, f1, f2, 8.0F, 8.0F);
         ModelPart.Vertex pos7 = new ModelPart.Vertex(x, f1, f2, 8.0F, 0.0F);
         this.f_104341_[2] = this.makeTexturedQuad(
            new ModelPart.Vertex[]{pos5, pos4, pos0, pos1}, faceUvs[1], true, texWidth, texHeight, mirorIn, Direction.DOWN
         );
         this.f_104341_[3] = this.makeTexturedQuad(new ModelPart.Vertex[]{pos2, pos3, pos7, pos6}, faceUvs[0], true, texWidth, texHeight, mirorIn, Direction.UP);
         this.f_104341_[1] = this.makeTexturedQuad(
            new ModelPart.Vertex[]{pos0, pos4, pos7, pos3}, faceUvs[5], false, texWidth, texHeight, mirorIn, Direction.WEST
         );
         this.f_104341_[4] = this.makeTexturedQuad(
            new ModelPart.Vertex[]{pos1, pos0, pos3, pos2}, faceUvs[2], false, texWidth, texHeight, mirorIn, Direction.NORTH
         );
         this.f_104341_[0] = this.makeTexturedQuad(
            new ModelPart.Vertex[]{pos5, pos1, pos2, pos6}, faceUvs[4], false, texWidth, texHeight, mirorIn, Direction.EAST
         );
         this.f_104341_[5] = this.makeTexturedQuad(
            new ModelPart.Vertex[]{pos4, pos5, pos6, pos7}, faceUvs[3], false, texWidth, texHeight, mirorIn, Direction.SOUTH
         );
         this.renderPositions = collectRenderPositions(this.f_104341_);
      }

      private static RenderPositions[] collectRenderPositions(ModelPart.Polygon[] quads) {
         Map<Vector3f, RenderPositions> map = new LinkedHashMap();

         for (int q = 0; q < quads.length; q++) {
            ModelPart.Polygon quad = quads[q];
            if (quad != null) {
               for (int v = 0; v < quad.f_104359_.length; v++) {
                  ModelPart.Vertex vert = quad.f_104359_[v];
                  RenderPositions rp = (RenderPositions)map.get(vert.f_104371_);
                  if (rp == null) {
                     rp = new RenderPositions(vert.f_104371_);
                     map.put(vert.f_104371_, rp);
                  }

                  vert.renderPositions = rp;
               }
            }
         }

         return (RenderPositions[])map.values().toArray(new RenderPositions[map.size()]);
      }

      private ModelPart.Polygon makeTexturedQuad(
         ModelPart.Vertex[] positionTextureVertexs,
         float[] faceUvs,
         boolean reverseUV,
         float textureWidth,
         float textureHeight,
         boolean mirrorIn,
         Direction directionIn
      ) {
         if (faceUvs == null) {
            return null;
         } else {
            return reverseUV
               ? new ModelPart.Polygon(
                  positionTextureVertexs, faceUvs[2], faceUvs[3], faceUvs[0], faceUvs[1], textureWidth, textureHeight, mirrorIn, directionIn
               )
               : new ModelPart.Polygon(
                  positionTextureVertexs, faceUvs[0], faceUvs[1], faceUvs[2], faceUvs[3], textureWidth, textureHeight, mirrorIn, directionIn
               );
         }
      }

      public VertexPosition[][] getBoxVertexPositions(int key) {
         if (this.boxVertexPositions == null) {
            this.boxVertexPositions = new BoxVertexPositions();
         }

         return this.boxVertexPositions.get(key);
      }

      public void m_171332_(PoseStack.Pose matrixEntryIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
         this.compile(matrixEntryIn, bufferIn, packedLightIn, packedOverlayIn, colorIn, null);
      }

      public void compile(PoseStack.Pose matrixEntryIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn, VertexPosition[][] boxPos) {
         Matrix4f matrix4f = matrixEntryIn.m_252922_();
         Vector3f vector3f = bufferIn.getTempVec3f();

         for (RenderPositions rp : this.renderPositions) {
            MathUtils.transform(matrix4f, rp.getPositionDiv16(), rp.getPositionRender());
         }

         boolean fastRender = bufferIn.canAddVertexFast();
         int quadsSize = this.f_104341_.length;

         for (int iq = 0; iq < quadsSize; iq++) {
            ModelPart.Polygon modelpart$polygon = this.f_104341_[iq];
            if (modelpart$polygon != null) {
               if (boxPos != null) {
                  bufferIn.setQuadVertexPositions(boxPos[iq]);
               }

               Vector3f vector3f1 = matrixEntryIn.m_322076_(modelpart$polygon.f_104360_, vector3f);
               float f = vector3f1.x();
               float f1 = vector3f1.y();
               float f2 = vector3f1.z();
               if (fastRender) {
                  int color = colorIn;
                  byte nx = BufferBuilder.m_338914_(f);
                  byte ny = BufferBuilder.m_338914_(f1);
                  byte nz = BufferBuilder.m_338914_(f2);
                  int normals = (nz & 255) << 16 | (ny & 255) << 8 | nx & 255;

                  for (ModelPart.Vertex modelpart$vertex : modelpart$polygon.f_104359_) {
                     Vector3f posRender = modelpart$vertex.renderPositions.getPositionRender();
                     bufferIn.addVertexFast(
                        posRender.x,
                        posRender.y,
                        posRender.z,
                        color,
                        modelpart$vertex.f_104372_,
                        modelpart$vertex.f_104373_,
                        packedOverlayIn,
                        packedLightIn,
                        normals
                     );
                  }
               } else {
                  for (ModelPart.Vertex modelpart$vertex : modelpart$polygon.f_104359_) {
                     Vector3f posRender = modelpart$vertex.renderPositions.getPositionRender();
                     bufferIn.m_338367_(
                        posRender.x,
                        posRender.y,
                        posRender.z,
                        colorIn,
                        modelpart$vertex.f_104372_,
                        modelpart$vertex.f_104373_,
                        packedOverlayIn,
                        packedLightIn,
                        f,
                        f1,
                        f2
                     );
                  }
               }
            }
         }
      }
   }

   static class Polygon {
      public final ModelPart.Vertex[] f_104359_;
      public final Vector3f f_104360_;

      public Polygon(
         ModelPart.Vertex[] positionsIn, float u1, float v1, float u2, float v2, float texWidth, float texHeight, boolean mirrorIn, Direction directionIn
      ) {
         this.f_104359_ = positionsIn;
         float f = 0.0F / texWidth;
         float f1 = 0.0F / texHeight;
         if (Config.isAntialiasing()) {
            f = 0.05F / texWidth;
            f1 = 0.05F / texHeight;
            if (u2 < u1) {
               f = -f;
            }

            if (v2 < v1) {
               f1 = -f1;
            }
         }

         positionsIn[0] = positionsIn[0].m_104384_(u2 / texWidth - f, v1 / texHeight + f1);
         positionsIn[1] = positionsIn[1].m_104384_(u1 / texWidth + f, v1 / texHeight + f1);
         positionsIn[2] = positionsIn[2].m_104384_(u1 / texWidth + f, v2 / texHeight - f1);
         positionsIn[3] = positionsIn[3].m_104384_(u2 / texWidth - f, v2 / texHeight - f1);
         if (mirrorIn) {
            int i = positionsIn.length;

            for (int j = 0; j < i / 2; j++) {
               ModelPart.Vertex modelpart$vertex = positionsIn[j];
               positionsIn[j] = positionsIn[i - 1 - j];
               positionsIn[i - 1 - j] = modelpart$vertex;
            }
         }

         this.f_104360_ = directionIn.m_253071_();
         if (mirrorIn) {
            this.f_104360_.mul(-1.0F, 1.0F, 1.0F);
         }
      }
   }

   static class Vertex {
      public final Vector3f f_104371_;
      public final float f_104372_;
      public final float f_104373_;
      public RenderPositions renderPositions;

      public Vertex(float x, float y, float z, float texU, float texV) {
         this(new Vector3f(x, y, z), texU, texV);
      }

      public ModelPart.Vertex m_104384_(float texU, float texV) {
         return new ModelPart.Vertex(this.f_104371_, texU, texV);
      }

      public Vertex(Vector3f p_i253082_1_, float p_i253082_2_, float p_i253082_3_) {
         this.f_104371_ = p_i253082_1_;
         this.f_104372_ = p_i253082_2_;
         this.f_104373_ = p_i253082_3_;
      }
   }

   @FunctionalInterface
   public interface Visitor {
      void m_171341_(PoseStack.Pose var1, String var2, int var3, ModelPart.Cube var4);
   }
}
