package net.minecraft.client.renderer.block.model;

import com.mojang.datafixers.util.Either;
import com.mojang.math.Transformation;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.FaceInfo.Constants;
import net.minecraft.client.renderer.FaceInfo.VertexInfo;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockMath;
import net.minecraft.core.Vec3i;
import net.minecraftforge.client.model.ForgeFaceData;
import net.optifine.Config;
import net.optifine.model.BlockModelUtils;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class FaceBakery {
   public static final int f_173433_ = 8;
   private static final float f_111569_ = 1.0F / (float)Math.cos((float) (Math.PI / 8)) - 1.0F;
   private static final float f_111570_ = 1.0F / (float)Math.cos((float) (Math.PI / 4)) - 1.0F;
   public static final int f_173434_ = 4;
   private static final int f_173436_ = 3;
   public static final int f_173435_ = 4;

   public net.minecraft.client.renderer.block.model.BakedQuad m_111600_(
      Vector3f posFrom,
      Vector3f posTo,
      BlockElementFace face,
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite,
      net.minecraft.core.Direction facing,
      ModelState modelStateIn,
      @Nullable BlockElementRotation partRotation,
      boolean shade
   ) {
      BlockFaceUV blockfaceuv = face.f_111357_();
      if (modelStateIn.m_7538_()) {
         blockfaceuv = m_111581_(face.f_111357_(), facing, modelStateIn.m_6189_());
      }

      float[] afloat = new float[blockfaceuv.f_111387_.length];
      System.arraycopy(blockfaceuv.f_111387_, 0, afloat, 0, afloat.length);
      float f = sprite.m_118417_();
      float f1 = (blockfaceuv.f_111387_[0] + blockfaceuv.f_111387_[0] + blockfaceuv.f_111387_[2] + blockfaceuv.f_111387_[2]) / 4.0F;
      float f2 = (blockfaceuv.f_111387_[1] + blockfaceuv.f_111387_[1] + blockfaceuv.f_111387_[3] + blockfaceuv.f_111387_[3]) / 4.0F;
      blockfaceuv.f_111387_[0] = net.minecraft.util.Mth.m_14179_(f, blockfaceuv.f_111387_[0], f1);
      blockfaceuv.f_111387_[2] = net.minecraft.util.Mth.m_14179_(f, blockfaceuv.f_111387_[2], f1);
      blockfaceuv.f_111387_[1] = net.minecraft.util.Mth.m_14179_(f, blockfaceuv.f_111387_[1], f2);
      blockfaceuv.f_111387_[3] = net.minecraft.util.Mth.m_14179_(f, blockfaceuv.f_111387_[3], f2);
      boolean quadShade = Reflector.ForgeHooksClient_fillNormal.exists() ? false : shade;
      int[] aint = this.m_111573_(blockfaceuv, sprite, facing, this.m_111592_(posFrom, posTo), modelStateIn.m_6189_(), partRotation, quadShade);
      net.minecraft.core.Direction direction = m_111612_(aint);
      System.arraycopy(afloat, 0, blockfaceuv.f_111387_, 0, afloat.length);
      if (partRotation == null) {
         this.m_111630_(aint, direction);
      }

      if (Reflector.ForgeHooksClient_fillNormal.exists() && Reflector.ForgeBlockElementFace_getFaceData.exists()) {
         ForgeFaceData data = (ForgeFaceData)Reflector.call(face, Reflector.ForgeBlockElementFace_getFaceData);
         ReflectorForge.fillNormal(aint, direction, data);
         net.minecraft.client.renderer.block.model.BakedQuad quad = new net.minecraft.client.renderer.block.model.BakedQuad(
            aint, face.f_111355_(), direction, sprite, shade, data.ambientOcclusion()
         );
         if (!ForgeFaceData.DEFAULT.equals(data)) {
            Object iQuadTransformerLM = Reflector.QuadTransformers_applyingLightmap.call(data.blockLight(), data.skyLight());
            if (iQuadTransformerLM != null) {
               Reflector.call(iQuadTransformerLM, Reflector.IQuadTransformer_processInPlace, quad);
            }

            Object iQuadTransformerCol = Reflector.QuadTransformers_applyingColor.call(data.color());
            if (iQuadTransformerCol != null) {
               Reflector.call(iQuadTransformerCol, Reflector.IQuadTransformer_processInPlace, quad);
            }
         }

         return quad;
      } else {
         return new net.minecraft.client.renderer.block.model.BakedQuad(aint, face.f_111355_(), direction, sprite, shade);
      }
   }

   public static BlockFaceUV m_111581_(BlockFaceUV blockFaceUVIn, net.minecraft.core.Direction facing, Transformation modelRotationIn) {
      Matrix4f matrix4f = BlockMath.m_121844_(modelRotationIn, facing).m_252783_();
      float f = blockFaceUVIn.m_111392_(blockFaceUVIn.m_111398_(0));
      float f1 = blockFaceUVIn.m_111396_(blockFaceUVIn.m_111398_(0));
      Vector4f vector4f = matrix4f.transform(new Vector4f(f / 16.0F, f1 / 16.0F, 0.0F, 1.0F));
      float f2 = 16.0F * vector4f.x();
      float f3 = 16.0F * vector4f.y();
      float f4 = blockFaceUVIn.m_111392_(blockFaceUVIn.m_111398_(2));
      float f5 = blockFaceUVIn.m_111396_(blockFaceUVIn.m_111398_(2));
      Vector4f vector4f1 = matrix4f.transform(new Vector4f(f4 / 16.0F, f5 / 16.0F, 0.0F, 1.0F));
      float f6 = 16.0F * vector4f1.x();
      float f7 = 16.0F * vector4f1.y();
      float f8;
      float f9;
      if (Math.signum(f4 - f) == Math.signum(f6 - f2)) {
         f8 = f2;
         f9 = f6;
      } else {
         f8 = f6;
         f9 = f2;
      }

      float f10;
      float f11;
      if (Math.signum(f5 - f1) == Math.signum(f7 - f3)) {
         f10 = f3;
         f11 = f7;
      } else {
         f10 = f7;
         f11 = f3;
      }

      float f12 = (float)Math.toRadians((double)blockFaceUVIn.f_111388_);
      Matrix3f matrix3f = new Matrix3f(matrix4f);
      Vector3f vector3f = matrix3f.transform(new Vector3f(net.minecraft.util.Mth.m_14089_(f12), net.minecraft.util.Mth.m_14031_(f12), 0.0F));
      int i = Math.floorMod(-((int)Math.round(Math.toDegrees(Math.atan2((double)vector3f.y(), (double)vector3f.x())) / 90.0)) * 90, 360);
      return new BlockFaceUV(new float[]{f8, f10, f9, f11}, i);
   }

   private int[] m_111573_(
      BlockFaceUV uvs,
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite,
      net.minecraft.core.Direction orientation,
      float[] posDiv16,
      Transformation rotationIn,
      @Nullable BlockElementRotation partRotation,
      boolean shade
   ) {
      int vertexSize = Config.isShaders()
         ? com.mojang.blaze3d.vertex.DefaultVertexFormat.BLOCK_SHADERS_SIZE
         : com.mojang.blaze3d.vertex.DefaultVertexFormat.BLOCK_VANILLA_SIZE;
      int[] aint = new int[vertexSize];

      for (int i = 0; i < 4; i++) {
         this.m_111620_(aint, i, orientation, uvs, posDiv16, sprite, rotationIn, partRotation, shade);
      }

      return aint;
   }

   private float[] m_111592_(Vector3f pos1, Vector3f pos2) {
      float[] afloat = new float[net.minecraft.core.Direction.values().length];
      afloat[Constants.f_108996_] = pos1.x() / 16.0F;
      afloat[Constants.f_108995_] = pos1.y() / 16.0F;
      afloat[Constants.f_108994_] = pos1.z() / 16.0F;
      afloat[Constants.f_108993_] = pos2.x() / 16.0F;
      afloat[Constants.f_108992_] = pos2.y() / 16.0F;
      afloat[Constants.f_108991_] = pos2.z() / 16.0F;
      return afloat;
   }

   private void m_111620_(
      int[] vertexData,
      int vertexIndex,
      net.minecraft.core.Direction facing,
      BlockFaceUV blockFaceUVIn,
      float[] posDiv16,
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite,
      Transformation rotationIn,
      @Nullable BlockElementRotation partRotation,
      boolean shade
   ) {
      VertexInfo faceinfo$vertexinfo = FaceInfo.m_108984_(facing).m_108982_(vertexIndex);
      Vector3f vector3f = new Vector3f(
         posDiv16[faceinfo$vertexinfo.f_108998_], posDiv16[faceinfo$vertexinfo.f_108999_], posDiv16[faceinfo$vertexinfo.f_109000_]
      );
      this.m_252985_(vector3f, partRotation);
      this.m_253132_(vector3f, rotationIn);
      BlockModelUtils.snapVertexPosition(vector3f);
      this.m_111614_(vertexData, vertexIndex, vector3f, sprite, blockFaceUVIn);
   }

   private void m_111614_(
      int[] faceData, int storeIndex, Vector3f positionIn, net.minecraft.client.renderer.texture.TextureAtlasSprite sprite, BlockFaceUV faceUV
   ) {
      int step = faceData.length / 4;
      int i = storeIndex * step;
      faceData[i] = Float.floatToRawIntBits(positionIn.x());
      faceData[i + 1] = Float.floatToRawIntBits(positionIn.y());
      faceData[i + 2] = Float.floatToRawIntBits(positionIn.z());
      faceData[i + 3] = -1;
      faceData[i + 4] = Float.floatToRawIntBits(sprite.m_118367_(faceUV.m_111392_(storeIndex) / 16.0F));
      faceData[i + 4 + 1] = Float.floatToRawIntBits(sprite.m_118393_(faceUV.m_111396_(storeIndex) / 16.0F));
   }

   private void m_252985_(Vector3f vec, @Nullable BlockElementRotation partRotation) {
      if (partRotation != null) {
         Vector3f vector3f;
         Vector3f vector3f1;
         switch (partRotation.f_111379_()) {
            case X:
               vector3f = new Vector3f(1.0F, 0.0F, 0.0F);
               vector3f1 = new Vector3f(0.0F, 1.0F, 1.0F);
               break;
            case Y:
               vector3f = new Vector3f(0.0F, 1.0F, 0.0F);
               vector3f1 = new Vector3f(1.0F, 0.0F, 1.0F);
               break;
            case Z:
               vector3f = new Vector3f(0.0F, 0.0F, 1.0F);
               vector3f1 = new Vector3f(1.0F, 1.0F, 0.0F);
               break;
            default:
               throw new IllegalArgumentException("There are only 3 axes");
         }

         Quaternionf quaternionf = new Quaternionf().rotationAxis(partRotation.f_111380_() * (float) (Math.PI / 180.0), vector3f);
         if (partRotation.f_111381_()) {
            if (Math.abs(partRotation.f_111380_()) == 22.5F) {
               vector3f1.mul(f_111569_);
            } else {
               vector3f1.mul(f_111570_);
            }

            vector3f1.add(1.0F, 1.0F, 1.0F);
         } else {
            vector3f1.set(1.0F, 1.0F, 1.0F);
         }

         this.m_252821_(vec, new Vector3f(partRotation.f_111378_()), new Matrix4f().rotation(quaternionf), vector3f1);
      }
   }

   public void m_253132_(Vector3f posIn, Transformation transformIn) {
      if (transformIn != Transformation.m_121093_()) {
         this.m_252821_(posIn, new Vector3f(0.5F, 0.5F, 0.5F), transformIn.m_252783_(), new Vector3f(1.0F, 1.0F, 1.0F));
      }
   }

   private void m_252821_(Vector3f posIn, Vector3f originIn, Matrix4f transformIn, Vector3f scaleIn) {
      Vector4f vector4f = transformIn.transform(new Vector4f(posIn.x() - originIn.x(), posIn.y() - originIn.y(), posIn.z() - originIn.z(), 1.0F));
      vector4f.mul(new Vector4f(scaleIn, 1.0F));
      posIn.set(vector4f.x() + originIn.x(), vector4f.y() + originIn.y(), vector4f.z() + originIn.z());
   }

   public static net.minecraft.core.Direction m_111612_(int[] faceData) {
      int step = faceData.length / 4;
      int step2 = step * 2;
      Vector3f vector3f = new Vector3f(Float.intBitsToFloat(faceData[0]), Float.intBitsToFloat(faceData[1]), Float.intBitsToFloat(faceData[2]));
      Vector3f vector3f1 = new Vector3f(
         Float.intBitsToFloat(faceData[step]), Float.intBitsToFloat(faceData[step + 1]), Float.intBitsToFloat(faceData[step + 2])
      );
      Vector3f vector3f2 = new Vector3f(
         Float.intBitsToFloat(faceData[step2]), Float.intBitsToFloat(faceData[step2 + 1]), Float.intBitsToFloat(faceData[step2 + 2])
      );
      Vector3f vector3f3 = new Vector3f(vector3f).sub(vector3f1);
      Vector3f vector3f4 = new Vector3f(vector3f2).sub(vector3f1);
      Vector3f vector3f5 = new Vector3f(vector3f4).cross(vector3f3).normalize();
      if (!vector3f5.isFinite()) {
         return net.minecraft.core.Direction.UP;
      } else {
         net.minecraft.core.Direction direction = null;
         float f = 0.0F;

         for (net.minecraft.core.Direction direction1 : net.minecraft.core.Direction.values()) {
            Vec3i vec3i = direction1.m_122436_();
            Vector3f vector3f6 = new Vector3f((float)vec3i.m_123341_(), (float)vec3i.m_123342_(), (float)vec3i.m_123343_());
            float f1 = vector3f5.dot(vector3f6);
            if (f1 >= 0.0F && f1 > f) {
               f = f1;
               direction = direction1;
            }
         }

         return direction == null ? net.minecraft.core.Direction.UP : direction;
      }
   }

   private void m_111630_(int[] vertexData, net.minecraft.core.Direction directionIn) {
      int[] aint = new int[vertexData.length];
      System.arraycopy(vertexData, 0, aint, 0, vertexData.length);
      float[] afloat = new float[net.minecraft.core.Direction.values().length];
      afloat[Constants.f_108996_] = 999.0F;
      afloat[Constants.f_108995_] = 999.0F;
      afloat[Constants.f_108994_] = 999.0F;
      afloat[Constants.f_108993_] = -999.0F;
      afloat[Constants.f_108992_] = -999.0F;
      afloat[Constants.f_108991_] = -999.0F;
      int step = vertexData.length / 4;

      for (int i = 0; i < 4; i++) {
         int j = step * i;
         float f = Float.intBitsToFloat(aint[j]);
         float f1 = Float.intBitsToFloat(aint[j + 1]);
         float f2 = Float.intBitsToFloat(aint[j + 2]);
         if (f < afloat[Constants.f_108996_]) {
            afloat[Constants.f_108996_] = f;
         }

         if (f1 < afloat[Constants.f_108995_]) {
            afloat[Constants.f_108995_] = f1;
         }

         if (f2 < afloat[Constants.f_108994_]) {
            afloat[Constants.f_108994_] = f2;
         }

         if (f > afloat[Constants.f_108993_]) {
            afloat[Constants.f_108993_] = f;
         }

         if (f1 > afloat[Constants.f_108992_]) {
            afloat[Constants.f_108992_] = f1;
         }

         if (f2 > afloat[Constants.f_108991_]) {
            afloat[Constants.f_108991_] = f2;
         }
      }

      FaceInfo faceinfo = FaceInfo.m_108984_(directionIn);

      for (int i1 = 0; i1 < 4; i1++) {
         int j1 = step * i1;
         VertexInfo faceinfo$vertexinfo = faceinfo.m_108982_(i1);
         float f8 = afloat[faceinfo$vertexinfo.f_108998_];
         float f3 = afloat[faceinfo$vertexinfo.f_108999_];
         float f4 = afloat[faceinfo$vertexinfo.f_109000_];
         vertexData[j1] = Float.floatToRawIntBits(f8);
         vertexData[j1 + 1] = Float.floatToRawIntBits(f3);
         vertexData[j1 + 2] = Float.floatToRawIntBits(f4);

         for (int k = 0; k < 4; k++) {
            int l = step * k;
            float f5 = Float.intBitsToFloat(aint[l]);
            float f6 = Float.intBitsToFloat(aint[l + 1]);
            float f7 = Float.intBitsToFloat(aint[l + 2]);
            if (net.minecraft.util.Mth.m_14033_(f8, f5) && net.minecraft.util.Mth.m_14033_(f3, f6) && net.minecraft.util.Mth.m_14033_(f4, f7)) {
               vertexData[j1 + 4] = aint[l + 4];
               vertexData[j1 + 4 + 1] = aint[l + 4 + 1];
            }
         }
      }
   }

   public static net.minecraft.resources.ResourceLocation getParentLocation(BlockModel blockModel) {
      return blockModel.f_111419_;
   }

   public static void setParentLocation(BlockModel blockModel, net.minecraft.resources.ResourceLocation location) {
      blockModel.f_111419_ = location;
   }

   public static Map<String, Either<net.minecraft.client.resources.model.Material, String>> getTextures(BlockModel blockModel) {
      return blockModel.f_111417_;
   }
}
