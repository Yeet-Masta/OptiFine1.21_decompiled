import com.mojang.datafixers.util.Either;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.src.C_4118_;
import net.minecraft.src.C_4200_;
import net.minecraft.src.C_4202_;
import net.minecraft.src.C_4203_;
import net.minecraft.src.C_4205_;
import net.minecraft.src.C_4537_;
import net.minecraft.src.C_4649_;
import net.minecraft.src.C_4674_;
import net.minecraft.src.C_4713_;
import net.minecraft.src.C_4118_.C_4120_;
import net.minecraft.src.C_4118_.C_4121_;
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
   public static final int a = 8;
   private static final float d = 1.0F / (float)Math.cos((float) (Math.PI / 8)) - 1.0F;
   private static final float e = 1.0F / (float)Math.cos((float) (Math.PI / 4)) - 1.0F;
   public static final int b = 4;
   private static final int f = 3;
   public static final int c = 4;

   public BakedQuad a(
      Vector3f posFrom,
      Vector3f posTo,
      C_4200_ face,
      TextureAtlasSprite sprite,
      Direction facing,
      C_4537_ modelStateIn,
      @Nullable C_4202_ partRotation,
      boolean shade
   ) {
      C_4203_ blockfaceuv = face.f_111357_();
      if (modelStateIn.m_7538_()) {
         blockfaceuv = a(face.f_111357_(), facing, modelStateIn.m_6189_());
      }

      float[] afloat = new float[blockfaceuv.f_111387_.length];
      System.arraycopy(blockfaceuv.f_111387_, 0, afloat, 0, afloat.length);
      float f = sprite.k();
      float f1 = (blockfaceuv.f_111387_[0] + blockfaceuv.f_111387_[0] + blockfaceuv.f_111387_[2] + blockfaceuv.f_111387_[2]) / 4.0F;
      float f2 = (blockfaceuv.f_111387_[1] + blockfaceuv.f_111387_[1] + blockfaceuv.f_111387_[3] + blockfaceuv.f_111387_[3]) / 4.0F;
      blockfaceuv.f_111387_[0] = Mth.i(f, blockfaceuv.f_111387_[0], f1);
      blockfaceuv.f_111387_[2] = Mth.i(f, blockfaceuv.f_111387_[2], f1);
      blockfaceuv.f_111387_[1] = Mth.i(f, blockfaceuv.f_111387_[1], f2);
      blockfaceuv.f_111387_[3] = Mth.i(f, blockfaceuv.f_111387_[3], f2);
      boolean quadShade = Reflector.ForgeHooksClient_fillNormal.exists() ? false : shade;
      int[] aint = this.a(blockfaceuv, sprite, facing, this.a(posFrom, posTo), modelStateIn.m_6189_(), partRotation, quadShade);
      Direction direction = a(aint);
      System.arraycopy(afloat, 0, blockfaceuv.f_111387_, 0, afloat.length);
      if (partRotation == null) {
         this.a(aint, direction);
      }

      if (Reflector.ForgeHooksClient_fillNormal.exists() && Reflector.ForgeBlockElementFace_getFaceData.exists()) {
         ForgeFaceData data = (ForgeFaceData)Reflector.call(face, Reflector.ForgeBlockElementFace_getFaceData);
         ReflectorForge.fillNormal(aint, direction, data);
         BakedQuad quad = new BakedQuad(aint, face.f_111355_(), direction, sprite, shade, data.ambientOcclusion());
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
         return new BakedQuad(aint, face.f_111355_(), direction, sprite, shade);
      }
   }

   public static C_4203_ a(C_4203_ blockFaceUVIn, Direction facing, C_4649_ modelRotationIn) {
      Matrix4f matrix4f = C_4674_.a(modelRotationIn, facing).m_252783_();
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
      Vector3f vector3f = matrix3f.transform(new Vector3f(Mth.b(f12), Mth.a(f12), 0.0F));
      int i = Math.floorMod(-((int)Math.round(Math.toDegrees(Math.atan2((double)vector3f.y(), (double)vector3f.x())) / 90.0)) * 90, 360);
      return new C_4203_(new float[]{f8, f10, f9, f11}, i);
   }

   private int[] a(
      C_4203_ uvs, TextureAtlasSprite sprite, Direction orientation, float[] posDiv16, C_4649_ rotationIn, @Nullable C_4202_ partRotation, boolean shade
   ) {
      int vertexSize = Config.isShaders() ? DefaultVertexFormat.BLOCK_SHADERS_SIZE : DefaultVertexFormat.BLOCK_VANILLA_SIZE;
      int[] aint = new int[vertexSize];

      for (int i = 0; i < 4; i++) {
         this.a(aint, i, orientation, uvs, posDiv16, sprite, rotationIn, partRotation, shade);
      }

      return aint;
   }

   private float[] a(Vector3f pos1, Vector3f pos2) {
      float[] afloat = new float[Direction.values().length];
      afloat[C_4120_.f_108996_] = pos1.x() / 16.0F;
      afloat[C_4120_.f_108995_] = pos1.y() / 16.0F;
      afloat[C_4120_.f_108994_] = pos1.z() / 16.0F;
      afloat[C_4120_.f_108993_] = pos2.x() / 16.0F;
      afloat[C_4120_.f_108992_] = pos2.y() / 16.0F;
      afloat[C_4120_.f_108991_] = pos2.z() / 16.0F;
      return afloat;
   }

   private void a(
      int[] vertexData,
      int vertexIndex,
      Direction facing,
      C_4203_ blockFaceUVIn,
      float[] posDiv16,
      TextureAtlasSprite sprite,
      C_4649_ rotationIn,
      @Nullable C_4202_ partRotation,
      boolean shade
   ) {
      C_4121_ faceinfo$vertexinfo = C_4118_.a(facing).m_108982_(vertexIndex);
      Vector3f vector3f = new Vector3f(
         posDiv16[faceinfo$vertexinfo.f_108998_], posDiv16[faceinfo$vertexinfo.f_108999_], posDiv16[faceinfo$vertexinfo.f_109000_]
      );
      this.a(vector3f, partRotation);
      this.a(vector3f, rotationIn);
      BlockModelUtils.snapVertexPosition(vector3f);
      this.a(vertexData, vertexIndex, vector3f, sprite, blockFaceUVIn);
   }

   private void a(int[] faceData, int storeIndex, Vector3f positionIn, TextureAtlasSprite sprite, C_4203_ faceUV) {
      int step = faceData.length / 4;
      int i = storeIndex * step;
      faceData[i] = Float.floatToRawIntBits(positionIn.x());
      faceData[i + 1] = Float.floatToRawIntBits(positionIn.y());
      faceData[i + 2] = Float.floatToRawIntBits(positionIn.z());
      faceData[i + 3] = -1;
      faceData[i + 4] = Float.floatToRawIntBits(sprite.a(faceUV.m_111392_(storeIndex) / 16.0F));
      faceData[i + 4 + 1] = Float.floatToRawIntBits(sprite.c(faceUV.m_111396_(storeIndex) / 16.0F));
   }

   private void a(Vector3f vec, @Nullable C_4202_ partRotation) {
      if (partRotation != null) {
         Vector3f vector3f;
         Vector3f vector3f1;
         switch (partRotation.b()) {
            case a:
               vector3f = new Vector3f(1.0F, 0.0F, 0.0F);
               vector3f1 = new Vector3f(0.0F, 1.0F, 1.0F);
               break;
            case b:
               vector3f = new Vector3f(0.0F, 1.0F, 0.0F);
               vector3f1 = new Vector3f(1.0F, 0.0F, 1.0F);
               break;
            case c:
               vector3f = new Vector3f(0.0F, 0.0F, 1.0F);
               vector3f1 = new Vector3f(1.0F, 1.0F, 0.0F);
               break;
            default:
               throw new IllegalArgumentException("There are only 3 axes");
         }

         Quaternionf quaternionf = new Quaternionf().rotationAxis(partRotation.f_111380_() * (float) (Math.PI / 180.0), vector3f);
         if (partRotation.f_111381_()) {
            if (Math.abs(partRotation.f_111380_()) == 22.5F) {
               vector3f1.mul(d);
            } else {
               vector3f1.mul(e);
            }

            vector3f1.add(1.0F, 1.0F, 1.0F);
         } else {
            vector3f1.set(1.0F, 1.0F, 1.0F);
         }

         this.a(vec, new Vector3f(partRotation.f_111378_()), new Matrix4f().rotation(quaternionf), vector3f1);
      }
   }

   public void a(Vector3f posIn, C_4649_ transformIn) {
      if (transformIn != C_4649_.m_121093_()) {
         this.a(posIn, new Vector3f(0.5F, 0.5F, 0.5F), transformIn.m_252783_(), new Vector3f(1.0F, 1.0F, 1.0F));
      }
   }

   private void a(Vector3f posIn, Vector3f originIn, Matrix4f transformIn, Vector3f scaleIn) {
      Vector4f vector4f = transformIn.transform(new Vector4f(posIn.x() - originIn.x(), posIn.y() - originIn.y(), posIn.z() - originIn.z(), 1.0F));
      vector4f.mul(new Vector4f(scaleIn, 1.0F));
      posIn.set(vector4f.x() + originIn.x(), vector4f.y() + originIn.y(), vector4f.z() + originIn.z());
   }

   public static Direction a(int[] faceData) {
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
         return Direction.b;
      } else {
         Direction direction = null;
         float f = 0.0F;

         for (Direction direction1 : Direction.values()) {
            C_4713_ vec3i = direction1.q();
            Vector3f vector3f6 = new Vector3f((float)vec3i.m_123341_(), (float)vec3i.m_123342_(), (float)vec3i.m_123343_());
            float f1 = vector3f5.dot(vector3f6);
            if (f1 >= 0.0F && f1 > f) {
               f = f1;
               direction = direction1;
            }
         }

         return direction == null ? Direction.b : direction;
      }
   }

   private void a(int[] vertexData, Direction directionIn) {
      int[] aint = new int[vertexData.length];
      System.arraycopy(vertexData, 0, aint, 0, vertexData.length);
      float[] afloat = new float[Direction.values().length];
      afloat[C_4120_.f_108996_] = 999.0F;
      afloat[C_4120_.f_108995_] = 999.0F;
      afloat[C_4120_.f_108994_] = 999.0F;
      afloat[C_4120_.f_108993_] = -999.0F;
      afloat[C_4120_.f_108992_] = -999.0F;
      afloat[C_4120_.f_108991_] = -999.0F;
      int step = vertexData.length / 4;

      for (int i = 0; i < 4; i++) {
         int j = step * i;
         float f = Float.intBitsToFloat(aint[j]);
         float f1 = Float.intBitsToFloat(aint[j + 1]);
         float f2 = Float.intBitsToFloat(aint[j + 2]);
         if (f < afloat[C_4120_.f_108996_]) {
            afloat[C_4120_.f_108996_] = f;
         }

         if (f1 < afloat[C_4120_.f_108995_]) {
            afloat[C_4120_.f_108995_] = f1;
         }

         if (f2 < afloat[C_4120_.f_108994_]) {
            afloat[C_4120_.f_108994_] = f2;
         }

         if (f > afloat[C_4120_.f_108993_]) {
            afloat[C_4120_.f_108993_] = f;
         }

         if (f1 > afloat[C_4120_.f_108992_]) {
            afloat[C_4120_.f_108992_] = f1;
         }

         if (f2 > afloat[C_4120_.f_108991_]) {
            afloat[C_4120_.f_108991_] = f2;
         }
      }

      C_4118_ faceinfo = C_4118_.a(directionIn);

      for (int i1 = 0; i1 < 4; i1++) {
         int j1 = step * i1;
         C_4121_ faceinfo$vertexinfo = faceinfo.m_108982_(i1);
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
            if (Mth.a(f8, f5) && Mth.a(f3, f6) && Mth.a(f4, f7)) {
               vertexData[j1 + 4] = aint[l + 4];
               vertexData[j1 + 4 + 1] = aint[l + 4 + 1];
            }
         }
      }
   }

   public static ResourceLocation getParentLocation(C_4205_ blockModel) {
      return blockModel.f;
   }

   public static void setParentLocation(C_4205_ blockModel, ResourceLocation location) {
      blockModel.f = location;
   }

   public static Map<String, Either<Material, String>> getTextures(C_4205_ blockModel) {
      return blockModel.f_111417_;
   }
}
