import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.src.C_141659_;
import net.minecraft.src.C_212974_;
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
   public static final float a = 1.0F;
   public float b;
   public float c;
   public float d;
   public float e;
   public float f;
   public float g;
   public float h = 1.0F;
   public float i = 1.0F;
   public float j = 1.0F;
   public boolean k = true;
   public boolean l;
   public final List<ModelPart.a> m;
   public final Map<String, ModelPart> n;
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
   private C_141659_ o = C_141659_.f_171404_;

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

   public ModelPart(List<ModelPart.a> cubeListIn, Map<String, ModelPart> childModelsIn) {
      if (cubeListIn instanceof ImmutableList) {
         cubeListIn = new ArrayList(cubeListIn);
      }

      this.m = cubeListIn;
      this.n = childModelsIn;
      this.childModelsList = new ArrayList(this.n.values());

      for (ModelPart child : this.childModelsList) {
         child.setParent(this);
      }
   }

   public C_141659_ a() {
      return C_141659_.m_171423_(this.b, this.c, this.d, this.e, this.f, this.g);
   }

   public C_141659_ b() {
      return this.o;
   }

   public void a(C_141659_ partPoseIn) {
      this.o = partPoseIn;
   }

   public void c() {
      this.b(this.o);
   }

   public void b(C_141659_ partPoseIn) {
      if (!this.custom) {
         this.b = partPoseIn.f_171405_;
         this.c = partPoseIn.f_171406_;
         this.d = partPoseIn.f_171407_;
         this.e = partPoseIn.f_171408_;
         this.f = partPoseIn.f_171409_;
         this.g = partPoseIn.f_171410_;
         this.h = 1.0F;
         this.i = 1.0F;
         this.j = 1.0F;
      }
   }

   public void a(ModelPart modelRendererIn) {
      this.h = modelRendererIn.h;
      this.i = modelRendererIn.i;
      this.j = modelRendererIn.j;
      this.e = modelRendererIn.e;
      this.f = modelRendererIn.f;
      this.g = modelRendererIn.g;
      this.b = modelRendererIn.b;
      this.c = modelRendererIn.c;
      this.d = modelRendererIn.d;
   }

   public boolean a(String keyIn) {
      return this.n.containsKey(keyIn);
   }

   public ModelPart b(String nameIn) {
      ModelPart modelpart = (ModelPart)this.n.get(nameIn);
      if (modelpart == null) {
         throw new NoSuchElementException("Can't find part " + nameIn);
      } else {
         return modelpart;
      }
   }

   public void a(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn) {
      this.b = rotationPointXIn;
      this.c = rotationPointYIn;
      this.d = rotationPointZIn;
   }

   public void b(float xRotIn, float yRotIn, float zRotIn) {
      this.e = xRotIn;
      this.f = yRotIn;
      this.g = zRotIn;
   }

   public void a(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn) {
      this.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, -1);
   }

   public void a(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
      this.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn, true);
   }

   public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn, boolean updateModel) {
      if (this.k && (!this.m.isEmpty() || !this.n.isEmpty() || !this.spriteList.isEmpty())) {
         RenderType lastRenderType = null;
         BufferBuilder lastBufferBuilder = null;
         MultiBufferSource.a renderTypeBuffer = null;
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
                  bufferIn = VertexMultiConsumer.a(secondaryBuilder, bufferIn);
               }
            }
         }

         if (updateModel && CustomEntityModels.isActive()) {
            this.updateModel();
         }

         matrixStackIn.a();
         this.a(matrixStackIn);
         if (!this.l) {
            this.a(matrixStackIn.c(), bufferIn, packedLightIn, packedOverlayIn, colorIn);
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

         matrixStackIn.b();
         if (renderTypeBuffer != null) {
            renderTypeBuffer.restoreRenderState(lastRenderType, lastBufferBuilder);
         }
      }
   }

   public void a(PoseStack matrixStackIn, ModelPart.d visitorIn) {
      this.a(matrixStackIn, visitorIn, "");
   }

   private void a(PoseStack matrixStackIn, ModelPart.d visitorIn, String pathIn) {
      if (!this.m.isEmpty() || !this.n.isEmpty()) {
         matrixStackIn.a();
         this.a(matrixStackIn);
         PoseStack.a posestack$pose = matrixStackIn.c();

         for (int i = 0; i < this.m.size(); i++) {
            visitorIn.visit(posestack$pose, pathIn, i, (ModelPart.a)this.m.get(i));
         }

         String s = pathIn + "/";
         this.n.forEach((nameIn, partIn) -> partIn.a(matrixStackIn, visitorIn, s + nameIn));
         matrixStackIn.b();
      }
   }

   public void a(PoseStack matrixStackIn) {
      matrixStackIn.a(this.b / 16.0F, this.c / 16.0F, this.d / 16.0F);
      if (this.e != 0.0F || this.f != 0.0F || this.g != 0.0F) {
         matrixStackIn.a(new Quaternionf().rotationZYX(this.g, this.f, this.e));
      }

      if (this.h != 1.0F || this.i != 1.0F || this.j != 1.0F) {
         matrixStackIn.b(this.h, this.i, this.j);
      }
   }

   private void a(PoseStack.a matrixEntryIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
      boolean shadersVelocity = Config.isShaders() && Shaders.useVelocityAttrib && Config.isMinecraftThread();
      int cubeListSize = this.m.size();

      for (int ic = 0; ic < cubeListSize; ic++) {
         ModelPart.a modelpart$cube = (ModelPart.a)this.m.get(ic);
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

   public ModelPart.a a(C_212974_ randomIn) {
      return (ModelPart.a)this.m.get(randomIn.m_188503_(this.m.size()));
   }

   public boolean d() {
      return this.m.isEmpty();
   }

   public void a(Vector3f posIn) {
      this.b = this.b + posIn.x();
      this.c = this.c + posIn.y();
      this.d = this.d + posIn.z();
   }

   public void b(Vector3f rotIn) {
      this.e = this.e + rotIn.x();
      this.f = this.f + rotIn.y();
      this.g = this.g + rotIn.z();
   }

   public void c(Vector3f scaleIn) {
      this.h = this.h + scaleIn.x();
      this.i = this.i + scaleIn.y();
      this.j = this.j + scaleIn.z();
   }

   public Stream<ModelPart> e() {
      return Stream.concat(Stream.of(this), this.n.values().stream().flatMap(ModelPart::e));
   }

   public void addSprite(float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, float sizeAdd) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: Constructor net/optifine/model/ModelSprite.<init>(LModelPart;FFFFFIIIF)V not found
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:49)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:957)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:460)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1018)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:1153)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.toJava(InvocationExprent.java:902)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield ModelPart.spriteList Ljava/util/List;
      // 04: new net/optifine/model/ModelSprite
      // 07: dup
      // 08: aload 0
      // 09: aload 0
      // 0a: getfield ModelPart.textureOffsetX F
      // 0d: aload 0
      // 0e: getfield ModelPart.textureOffsetY F
      // 11: fload 1
      // 12: fload 2
      // 13: fload 3
      // 14: iload 4
      // 16: iload 5
      // 18: iload 6
      // 1a: fload 7
      // 1c: invokespecial net/optifine/model/ModelSprite.<init> (LModelPart;FFFFFIIIF)V
      // 1f: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 24: pop
      // 25: return
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
      this.m.add(new ModelPart.a(faceUvs, x, y, z, dx, dy, dz, delta, delta, delta, this.mirror, this.textureWidth, this.textureHeight));
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
      this.m
         .add(
            new ModelPart.a(texOffX, texOffY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, mirror, this.textureWidth, this.textureHeight, ALL_VISIBLE)
         );
   }

   public ModelPart getChildModelDeep(String name) {
      if (name == null) {
         return null;
      } else if (this.n.containsKey(name)) {
         return this.b(name);
      } else {
         if (this.n != null) {
            for (String key : this.n.keySet()) {
               ModelPart child = (ModelPart)this.n.get(key);
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
         if (this.n != null) {
            for (String key : this.n.keySet()) {
               ModelPart child = (ModelPart)this.n.get(key);
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
            if (this.n != null) {
               for (String key : this.n.keySet()) {
                  ModelPart child = (ModelPart)this.n.get(key);
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
         this.n.put(name, part);
         this.childModelsList = new ArrayList(this.n.values());
         part.setParent(this);
         if (part.getName() == null) {
            part.setName(name);
         }
      }
   }

   public String getUniqueChildModelName(String name) {
      String baseName = name;

      for (int counter = 2; this.n.containsKey(name); counter++) {
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
         + (this.m != null ? this.m.size() : null)
         + ", submodels: "
         + (this.n != null ? this.n.size() : null)
         + ", custom: "
         + this.custom;
   }

   public static class a {
      private final ModelPart.b[] g;
      public final float a;
      public final float b;
      public final float c;
      public final float d;
      public final float e;
      public final float f;
      private BoxVertexPositions boxVertexPositions;
      private RenderPositions[] renderPositions;

      public a(
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

      public a(
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
         this.a = x;
         this.b = y;
         this.c = z;
         this.d = x + width;
         this.e = y + height;
         this.f = z + depth;
         this.g = new ModelPart.b[directionsIn.size()];
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

         ModelPart.c modelpart$vertex7 = new ModelPart.c(x, y, z, 0.0F, 0.0F);
         ModelPart.c modelpart$vertex = new ModelPart.c(f, y, z, 0.0F, 8.0F);
         ModelPart.c modelpart$vertex1 = new ModelPart.c(f, f1, z, 8.0F, 8.0F);
         ModelPart.c modelpart$vertex2 = new ModelPart.c(x, f1, z, 8.0F, 0.0F);
         ModelPart.c modelpart$vertex3 = new ModelPart.c(x, y, f2, 0.0F, 0.0F);
         ModelPart.c modelpart$vertex4 = new ModelPart.c(f, y, f2, 0.0F, 8.0F);
         ModelPart.c modelpart$vertex5 = new ModelPart.c(f, f1, f2, 8.0F, 8.0F);
         ModelPart.c modelpart$vertex6 = new ModelPart.c(x, f1, f2, 8.0F, 0.0F);
         float f5 = texOffX + depth;
         float f6 = texOffX + depth + width;
         float f7 = texOffX + depth + width + width;
         float f8 = texOffX + depth + width + depth;
         float f9 = texOffX + depth + width + depth + width;
         float f11 = texOffY + depth;
         float f12 = texOffY + depth + height;
         int i = 0;
         if (directionsIn.contains(Direction.a)) {
            this.g[i++] = new ModelPart.b(
               new ModelPart.c[]{modelpart$vertex4, modelpart$vertex3, modelpart$vertex7, modelpart$vertex},
               f5,
               texOffY,
               f6,
               f11,
               texWidth,
               texHeight,
               mirror,
               Direction.a
            );
         }

         if (directionsIn.contains(Direction.b)) {
            this.g[i++] = new ModelPart.b(
               new ModelPart.c[]{modelpart$vertex1, modelpart$vertex2, modelpart$vertex6, modelpart$vertex5},
               f6,
               f11,
               f7,
               texOffY,
               texWidth,
               texHeight,
               mirror,
               Direction.b
            );
         }

         if (directionsIn.contains(Direction.e)) {
            this.g[i++] = new ModelPart.b(
               new ModelPart.c[]{modelpart$vertex7, modelpart$vertex3, modelpart$vertex6, modelpart$vertex2},
               texOffX,
               f11,
               f5,
               f12,
               texWidth,
               texHeight,
               mirror,
               Direction.e
            );
         }

         if (directionsIn.contains(Direction.c)) {
            this.g[i++] = new ModelPart.b(
               new ModelPart.c[]{modelpart$vertex, modelpart$vertex7, modelpart$vertex2, modelpart$vertex1},
               f5,
               f11,
               f6,
               f12,
               texWidth,
               texHeight,
               mirror,
               Direction.c
            );
         }

         if (directionsIn.contains(Direction.f)) {
            this.g[i++] = new ModelPart.b(
               new ModelPart.c[]{modelpart$vertex4, modelpart$vertex, modelpart$vertex1, modelpart$vertex5},
               f6,
               f11,
               f8,
               f12,
               texWidth,
               texHeight,
               mirror,
               Direction.f
            );
         }

         if (directionsIn.contains(Direction.d)) {
            this.g[i] = new ModelPart.b(
               new ModelPart.c[]{modelpart$vertex3, modelpart$vertex4, modelpart$vertex5, modelpart$vertex6},
               f8,
               f11,
               f9,
               f12,
               texWidth,
               texHeight,
               mirror,
               Direction.d
            );
         }

         this.renderPositions = collectRenderPositions(this.g);
      }

      public a(
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
         this.a = x;
         this.b = y;
         this.c = z;
         this.d = x + width;
         this.e = y + height;
         this.f = z + depth;
         this.g = new ModelPart.b[6];
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

         ModelPart.c pos0 = new ModelPart.c(x, y, z, 0.0F, 0.0F);
         ModelPart.c pos1 = new ModelPart.c(f, y, z, 0.0F, 8.0F);
         ModelPart.c pos2 = new ModelPart.c(f, f1, z, 8.0F, 8.0F);
         ModelPart.c pos3 = new ModelPart.c(x, f1, z, 8.0F, 0.0F);
         ModelPart.c pos4 = new ModelPart.c(x, y, f2, 0.0F, 0.0F);
         ModelPart.c pos5 = new ModelPart.c(f, y, f2, 0.0F, 8.0F);
         ModelPart.c pos6 = new ModelPart.c(f, f1, f2, 8.0F, 8.0F);
         ModelPart.c pos7 = new ModelPart.c(x, f1, f2, 8.0F, 0.0F);
         this.g[2] = this.makeTexturedQuad(new ModelPart.c[]{pos5, pos4, pos0, pos1}, faceUvs[1], true, texWidth, texHeight, mirorIn, Direction.a);
         this.g[3] = this.makeTexturedQuad(new ModelPart.c[]{pos2, pos3, pos7, pos6}, faceUvs[0], true, texWidth, texHeight, mirorIn, Direction.b);
         this.g[1] = this.makeTexturedQuad(new ModelPart.c[]{pos0, pos4, pos7, pos3}, faceUvs[5], false, texWidth, texHeight, mirorIn, Direction.e);
         this.g[4] = this.makeTexturedQuad(new ModelPart.c[]{pos1, pos0, pos3, pos2}, faceUvs[2], false, texWidth, texHeight, mirorIn, Direction.c);
         this.g[0] = this.makeTexturedQuad(new ModelPart.c[]{pos5, pos1, pos2, pos6}, faceUvs[4], false, texWidth, texHeight, mirorIn, Direction.f);
         this.g[5] = this.makeTexturedQuad(new ModelPart.c[]{pos4, pos5, pos6, pos7}, faceUvs[3], false, texWidth, texHeight, mirorIn, Direction.d);
         this.renderPositions = collectRenderPositions(this.g);
      }

      private static RenderPositions[] collectRenderPositions(ModelPart.b[] quads) {
         Map<Vector3f, RenderPositions> map = new LinkedHashMap();

         for (int q = 0; q < quads.length; q++) {
            ModelPart.b quad = quads[q];
            if (quad != null) {
               for (int v = 0; v < quad.a.length; v++) {
                  ModelPart.c vert = quad.a[v];
                  RenderPositions rp = (RenderPositions)map.get(vert.a);
                  if (rp == null) {
                     rp = new RenderPositions(vert.a);
                     map.put(vert.a, rp);
                  }

                  vert.renderPositions = rp;
               }
            }
         }

         return (RenderPositions[])map.values().toArray(new RenderPositions[map.size()]);
      }

      private ModelPart.b makeTexturedQuad(
         ModelPart.c[] positionTextureVertexs,
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
               ? new ModelPart.b(positionTextureVertexs, faceUvs[2], faceUvs[3], faceUvs[0], faceUvs[1], textureWidth, textureHeight, mirrorIn, directionIn)
               : new ModelPart.b(positionTextureVertexs, faceUvs[0], faceUvs[1], faceUvs[2], faceUvs[3], textureWidth, textureHeight, mirrorIn, directionIn);
         }
      }

      public VertexPosition[][] getBoxVertexPositions(int key) {
         if (this.boxVertexPositions == null) {
            this.boxVertexPositions = new BoxVertexPositions();
         }

         return this.boxVertexPositions.get(key);
      }

      public void a(PoseStack.a matrixEntryIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
         this.compile(matrixEntryIn, bufferIn, packedLightIn, packedOverlayIn, colorIn, null);
      }

      public void compile(PoseStack.a matrixEntryIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn, VertexPosition[][] boxPos) {
         Matrix4f matrix4f = matrixEntryIn.a();
         Vector3f vector3f = bufferIn.getTempVec3f();

         for (RenderPositions rp : this.renderPositions) {
            MathUtils.transform(matrix4f, rp.getPositionDiv16(), rp.getPositionRender());
         }

         boolean fastRender = bufferIn.canAddVertexFast();
         int quadsSize = this.g.length;

         for (int iq = 0; iq < quadsSize; iq++) {
            ModelPart.b modelpart$polygon = this.g[iq];
            if (modelpart$polygon != null) {
               if (boxPos != null) {
                  bufferIn.setQuadVertexPositions(boxPos[iq]);
               }

               Vector3f vector3f1 = matrixEntryIn.a(modelpart$polygon.b, vector3f);
               float f = vector3f1.x();
               float f1 = vector3f1.y();
               float f2 = vector3f1.z();
               if (fastRender) {
                  int color = colorIn;
                  byte nx = BufferBuilder.a(f);
                  byte ny = BufferBuilder.a(f1);
                  byte nz = BufferBuilder.a(f2);
                  int normals = (nz & 255) << 16 | (ny & 255) << 8 | nx & 255;

                  for (ModelPart.c modelpart$vertex : modelpart$polygon.a) {
                     Vector3f posRender = modelpart$vertex.renderPositions.getPositionRender();
                     bufferIn.addVertexFast(
                        posRender.x, posRender.y, posRender.z, color, modelpart$vertex.b, modelpart$vertex.c, packedOverlayIn, packedLightIn, normals
                     );
                  }
               } else {
                  for (ModelPart.c modelpart$vertex : modelpart$polygon.a) {
                     Vector3f posRender = modelpart$vertex.renderPositions.getPositionRender();
                     bufferIn.a(
                        posRender.x, posRender.y, posRender.z, colorIn, modelpart$vertex.b, modelpart$vertex.c, packedOverlayIn, packedLightIn, f, f1, f2
                     );
                  }
               }
            }
         }
      }
   }

   static class b {
      public final ModelPart.c[] a;
      public final Vector3f b;

      public b(ModelPart.c[] positionsIn, float u1, float v1, float u2, float v2, float texWidth, float texHeight, boolean mirrorIn, Direction directionIn) {
         this.a = positionsIn;
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

         positionsIn[0] = positionsIn[0].a(u2 / texWidth - f, v1 / texHeight + f1);
         positionsIn[1] = positionsIn[1].a(u1 / texWidth + f, v1 / texHeight + f1);
         positionsIn[2] = positionsIn[2].a(u1 / texWidth + f, v2 / texHeight - f1);
         positionsIn[3] = positionsIn[3].a(u2 / texWidth - f, v2 / texHeight - f1);
         if (mirrorIn) {
            int i = positionsIn.length;

            for (int j = 0; j < i / 2; j++) {
               ModelPart.c modelpart$vertex = positionsIn[j];
               positionsIn[j] = positionsIn[i - 1 - j];
               positionsIn[i - 1 - j] = modelpart$vertex;
            }
         }

         this.b = directionIn.m();
         if (mirrorIn) {
            this.b.mul(-1.0F, 1.0F, 1.0F);
         }
      }
   }

   static class c {
      public final Vector3f a;
      public final float b;
      public final float c;
      public RenderPositions renderPositions;

      public c(float x, float y, float z, float texU, float texV) {
         this(new Vector3f(x, y, z), texU, texV);
      }

      public ModelPart.c a(float texU, float texV) {
         return new ModelPart.c(this.a, texU, texV);
      }

      public c(Vector3f p_i253082_1_, float p_i253082_2_, float p_i253082_3_) {
         this.a = p_i253082_1_;
         this.b = p_i253082_2_;
         this.c = p_i253082_3_;
      }
   }

   @FunctionalInterface
   public interface d {
      void visit(PoseStack.a var1, String var2, int var3, ModelPart.a var4);
   }
}
