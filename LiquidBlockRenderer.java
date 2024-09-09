import net.minecraft.src.C_139_;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_1809_;
import net.minecraft.src.C_1826_;
import net.minecraft.src.C_1900_;
import net.minecraft.src.C_2102_;
import net.minecraft.src.C_2690_;
import net.minecraft.src.C_2691_;
import net.minecraft.src.C_3068_;
import net.minecraft.src.C_3072_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4675_;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.Shaders;

public class LiquidBlockRenderer {
   private static final float a = 0.8888889F;
   private final TextureAtlasSprite[] b = new TextureAtlasSprite[2];
   private final TextureAtlasSprite[] c = new TextureAtlasSprite[2];
   private TextureAtlasSprite d;

   protected void a() {
      this.b[0] = C_3391_.m_91087_().m_91304_().m_119430_().b(C_1710_.f_49991_.o()).e();
      this.b[1] = ModelBakery.c.c();
      this.c[0] = C_3391_.m_91087_().m_91304_().m_119430_().b(C_1710_.f_49990_.o()).e();
      this.c[1] = ModelBakery.d.c();
      this.d = ModelBakery.e.c();
   }

   private static boolean a(C_2691_ fluidState1, C_2691_ fluidState2) {
      return fluidState2.m_76152_().m_6212_(fluidState1.m_76152_());
   }

   private static boolean a(C_1559_ reader, Direction face, float heightIn, C_4675_ pos, BlockState blockState) {
      if (blockState.m_60815_()) {
         C_3072_ voxelshape = C_3068_.m_83048_(0.0, 0.0, 0.0, 1.0, (double)heightIn, 1.0);
         C_3072_ voxelshape1 = blockState.m_60768_(reader, pos);
         return C_3068_.a(voxelshape, voxelshape1, face);
      } else {
         return false;
      }
   }

   private static boolean a(C_1559_ reader, C_4675_ pos, Direction face, float heightIn, BlockState blockStateIn) {
      return a(reader, face, heightIn, pos.a(face), blockStateIn);
   }

   private static boolean a(C_1559_ reader, C_4675_ pos, BlockState blockState, Direction face) {
      return a(reader, face.g(), 1.0F, pos, blockState);
   }

   public static boolean a(C_1557_ reader, C_4675_ pos, C_2691_ fluidStateIn, BlockState blockStateIn, Direction sideIn, C_2691_ fluidState2In) {
      return !a(reader, pos, blockStateIn, sideIn) && !a(fluidStateIn, fluidState2In);
   }

   public void a(C_1557_ lightReaderIn, C_4675_ posIn, VertexConsumer vertexBuilderIn, BlockState blockStateIn, C_2691_ fluidStateIn) {
      BlockState blockStateFluid = fluidStateIn.g();

      try {
         if (Config.isShaders()) {
            SVertexBuilder.pushEntity(blockStateFluid, vertexBuilderIn);
         }

         boolean flag = fluidStateIn.m_205070_(C_139_.f_13132_);
         TextureAtlasSprite[] atextureatlassprite = flag ? this.b : this.c;
         if (Reflector.ForgeHooksClient_getFluidSprites.exists()) {
            TextureAtlasSprite[] forgeFluidSprites = (TextureAtlasSprite[])Reflector.call(
               Reflector.ForgeHooksClient_getFluidSprites, lightReaderIn, posIn, fluidStateIn
            );
            if (forgeFluidSprites != null) {
               atextureatlassprite = forgeFluidSprites;
            }
         }

         RenderEnv renderEnv = vertexBuilderIn.getRenderEnv(blockStateFluid, posIn);
         boolean smoothLighting = !flag && C_3391_.m_91086_();
         int i = -1;
         float alpha = 1.0F;
         if (Reflector.ForgeHooksClient.exists()) {
            i = IClientFluidTypeExtensions.of(fluidStateIn).getTintColor(fluidStateIn, lightReaderIn, posIn);
            alpha = (float)(i >> 24 & 0xFF) / 255.0F;
         }

         BlockState blockstate = lightReaderIn.a_(posIn.a(Direction.a));
         C_2691_ fluidstate = blockstate.m_60819_();
         BlockState blockstate1 = lightReaderIn.a_(posIn.a(Direction.b));
         C_2691_ fluidstate1 = blockstate1.m_60819_();
         BlockState blockstate2 = lightReaderIn.a_(posIn.a(Direction.c));
         C_2691_ fluidstate2 = blockstate2.m_60819_();
         BlockState blockstate3 = lightReaderIn.a_(posIn.a(Direction.d));
         C_2691_ fluidstate3 = blockstate3.m_60819_();
         BlockState blockstate4 = lightReaderIn.a_(posIn.a(Direction.e));
         C_2691_ fluidstate4 = blockstate4.m_60819_();
         BlockState blockstate5 = lightReaderIn.a_(posIn.a(Direction.f));
         C_2691_ fluidstate5 = blockstate5.m_60819_();
         boolean flag1 = !a(fluidStateIn, fluidstate1);
         boolean flag2 = a(lightReaderIn, posIn, fluidStateIn, blockStateIn, Direction.a, fluidstate)
            && !a(lightReaderIn, posIn, Direction.a, 0.8888889F, blockstate);
         boolean flag3 = a(lightReaderIn, posIn, fluidStateIn, blockStateIn, Direction.c, fluidstate2);
         boolean flag4 = a(lightReaderIn, posIn, fluidStateIn, blockStateIn, Direction.d, fluidstate3);
         boolean flag5 = a(lightReaderIn, posIn, fluidStateIn, blockStateIn, Direction.e, fluidstate4);
         boolean flag6 = a(lightReaderIn, posIn, fluidStateIn, blockStateIn, Direction.f, fluidstate5);
         if (flag1 || flag2 || flag6 || flag5 || flag3 || flag4) {
            if (i < 0) {
               i = CustomColors.getFluidColor(lightReaderIn, blockStateFluid, posIn, renderEnv);
            }

            float f = (float)(i >> 16 & 0xFF) / 255.0F;
            float f1 = (float)(i >> 8 & 0xFF) / 255.0F;
            float f2 = (float)(i & 0xFF) / 255.0F;
            float f3 = lightReaderIn.a(Direction.a, true);
            float f4 = lightReaderIn.a(Direction.b, true);
            float f5 = lightReaderIn.a(Direction.c, true);
            float f6 = lightReaderIn.a(Direction.e, true);
            C_2690_ fluid = fluidStateIn.m_76152_();
            float f11 = this.a(lightReaderIn, fluid, posIn, blockStateIn, fluidStateIn);
            float f7;
            float f8;
            float f9;
            float f10;
            if (f11 >= 1.0F) {
               f7 = 1.0F;
               f8 = 1.0F;
               f9 = 1.0F;
               f10 = 1.0F;
            } else {
               float f12 = this.a(lightReaderIn, fluid, posIn.m_122012_(), blockstate2, fluidstate2);
               float f13 = this.a(lightReaderIn, fluid, posIn.m_122019_(), blockstate3, fluidstate3);
               float f14 = this.a(lightReaderIn, fluid, posIn.m_122029_(), blockstate5, fluidstate5);
               float f15 = this.a(lightReaderIn, fluid, posIn.m_122024_(), blockstate4, fluidstate4);
               f7 = this.a(lightReaderIn, fluid, f11, f12, f14, posIn.a(Direction.c).a(Direction.f));
               f8 = this.a(lightReaderIn, fluid, f11, f12, f15, posIn.a(Direction.c).a(Direction.e));
               f9 = this.a(lightReaderIn, fluid, f11, f13, f14, posIn.a(Direction.d).a(Direction.f));
               f10 = this.a(lightReaderIn, fluid, f11, f13, f15, posIn.a(Direction.d).a(Direction.e));
            }

            float f36 = (float)(posIn.m_123341_() & 15);
            float f37 = (float)(posIn.m_123342_() & 15);
            float f38 = (float)(posIn.m_123343_() & 15);
            if (Config.isRenderRegions()) {
               int chunkX = posIn.m_123341_() >> 4 << 4;
               int chunkY = posIn.m_123342_() >> 4 << 4;
               int chunkZ = posIn.m_123343_() >> 4 << 4;
               int bits = 8;
               int regionX = chunkX >> bits << bits;
               int regionZ = chunkZ >> bits << bits;
               int dx = chunkX - regionX;
               int dz = chunkZ - regionZ;
               f36 += (float)dx;
               f37 += (float)chunkY;
               f38 += (float)dz;
            }

            if (Config.isShaders() && Shaders.useMidBlockAttrib) {
               vertexBuilderIn.setMidBlock((float)((double)f36 + 0.5), (float)((double)f37 + 0.5), (float)((double)f38 + 0.5));
            }

            float f39 = 0.001F;
            float f16 = flag2 ? 0.001F : 0.0F;
            if (flag1 && !a(lightReaderIn, posIn, Direction.b, Math.min(Math.min(f8, f10), Math.min(f9, f7)), blockstate1)) {
               f8 -= 0.001F;
               f10 -= 0.001F;
               f9 -= 0.001F;
               f7 -= 0.001F;
               Vec3 vec3 = fluidStateIn.c(lightReaderIn, posIn);
               float f21;
               float f23;
               float f24;
               float f17;
               float f18;
               float f19;
               float f20;
               float f22;
               if (vec3.c == 0.0 && vec3.e == 0.0) {
                  TextureAtlasSprite textureatlassprite1 = atextureatlassprite[0];
                  vertexBuilderIn.setSprite(textureatlassprite1);
                  f17 = textureatlassprite1.a(0.0F);
                  f21 = textureatlassprite1.c(0.0F);
                  f18 = f17;
                  f22 = textureatlassprite1.c(1.0F);
                  f19 = textureatlassprite1.a(1.0F);
                  f23 = f22;
                  f20 = f19;
                  f24 = f21;
               } else {
                  TextureAtlasSprite textureatlassprite = atextureatlassprite[1];
                  vertexBuilderIn.setSprite(textureatlassprite);
                  float f25 = (float)Mth.d(vec3.e, vec3.c) - (float) (Math.PI / 2);
                  float f26 = Mth.a(f25) * 0.25F;
                  float f27 = Mth.b(f25) * 0.25F;
                  float f28 = 0.5F;
                  f17 = textureatlassprite.a(0.5F + (-f27 - f26));
                  f21 = textureatlassprite.c(0.5F + -f27 + f26);
                  f18 = textureatlassprite.a(0.5F + -f27 + f26);
                  f22 = textureatlassprite.c(0.5F + f27 + f26);
                  f19 = textureatlassprite.a(0.5F + f27 + f26);
                  f23 = textureatlassprite.c(0.5F + (f27 - f26));
                  f20 = textureatlassprite.a(0.5F + (f27 - f26));
                  f24 = textureatlassprite.c(0.5F + (-f27 - f26));
               }

               float f53 = (f17 + f18 + f19 + f20) / 4.0F;
               float f54 = (f21 + f22 + f23 + f24) / 4.0F;
               float f55 = atextureatlassprite[0].k();
               f17 = Mth.i(f55, f17, f53);
               f18 = Mth.i(f55, f18, f53);
               f19 = Mth.i(f55, f19, f53);
               f20 = Mth.i(f55, f20, f53);
               f21 = Mth.i(f55, f21, f54);
               f22 = Mth.i(f55, f22, f54);
               f23 = Mth.i(f55, f23, f54);
               f24 = Mth.i(f55, f24, f54);
               int l = this.a(lightReaderIn, posIn);
               int combinedLightNW = l;
               int combinedLightSW = l;
               int combinedLightSE = l;
               int combinedLightNE = l;
               if (smoothLighting) {
                  C_4675_ posN = posIn.m_122012_();
                  C_4675_ posS = posIn.m_122019_();
                  C_4675_ posE = posIn.m_122029_();
                  C_4675_ posW = posIn.m_122024_();
                  int lightN = this.a(lightReaderIn, posN);
                  int lightS = this.a(lightReaderIn, posS);
                  int lightE = this.a(lightReaderIn, posE);
                  int lightW = this.a(lightReaderIn, posW);
                  int lightNW = this.a(lightReaderIn, posN.m_122024_());
                  int lightSW = this.a(lightReaderIn, posS.m_122024_());
                  int lightSE = this.a(lightReaderIn, posS.m_122029_());
                  int lightNE = this.a(lightReaderIn, posN.m_122029_());
                  combinedLightNW = ModelBlockRenderer.b.a(lightN, lightNW, lightW, l);
                  combinedLightSW = ModelBlockRenderer.b.a(lightS, lightSW, lightW, l);
                  combinedLightSE = ModelBlockRenderer.b.a(lightS, lightSE, lightE, l);
                  combinedLightNE = ModelBlockRenderer.b.a(lightN, lightNE, lightE, l);
               }

               float f57 = f4 * f;
               float f29 = f4 * f1;
               float f30 = f4 * f2;
               this.vertexVanilla(vertexBuilderIn, f36 + 0.0F, f37 + f8, f38 + 0.0F, f57, f29, f30, f17, f21, combinedLightNW, alpha);
               this.vertexVanilla(vertexBuilderIn, f36 + 0.0F, f37 + f10, f38 + 1.0F, f57, f29, f30, f18, f22, combinedLightSW, alpha);
               this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f9, f38 + 1.0F, f57, f29, f30, f19, f23, combinedLightSE, alpha);
               this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f7, f38 + 0.0F, f57, f29, f30, f20, f24, combinedLightNE, alpha);
               if (fluidStateIn.m_76171_(lightReaderIn, posIn.m_7494_())) {
                  this.vertexVanilla(vertexBuilderIn, f36 + 0.0F, f37 + f8, f38 + 0.0F, f57, f29, f30, f17, f21, combinedLightNW, alpha);
                  this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f7, f38 + 0.0F, f57, f29, f30, f20, f24, combinedLightNE, alpha);
                  this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f9, f38 + 1.0F, f57, f29, f30, f19, f23, combinedLightSE, alpha);
                  this.vertexVanilla(vertexBuilderIn, f36 + 0.0F, f37 + f10, f38 + 1.0F, f57, f29, f30, f18, f22, combinedLightSW, alpha);
               }
            }

            if (flag2) {
               vertexBuilderIn.setSprite(atextureatlassprite[0]);
               float f40 = atextureatlassprite[0].c();
               float f41 = atextureatlassprite[0].d();
               float f42 = atextureatlassprite[0].g();
               float f43 = atextureatlassprite[0].h();
               int k = this.a(lightReaderIn, posIn.m_7495_());
               float fbr = lightReaderIn.a(Direction.a, true);
               float f46 = fbr * f;
               float f48 = fbr * f1;
               float f50 = fbr * f2;
               this.vertexVanilla(vertexBuilderIn, f36, f37 + f16, f38 + 1.0F, f46, f48, f50, f40, f43, k, alpha);
               this.vertexVanilla(vertexBuilderIn, f36, f37 + f16, f38, f46, f48, f50, f40, f42, k, alpha);
               this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f16, f38, f46, f48, f50, f41, f42, k, alpha);
               this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f16, f38 + 1.0F, f46, f48, f50, f41, f43, k, alpha);
            }

            int j = this.a(lightReaderIn, posIn);

            for (Direction direction : Direction.c.a) {
               float f44;
               float f45;
               float f47;
               float f49;
               float f51;
               float f52;
               boolean flag7;
               switch (direction) {
                  case c:
                     f44 = f8;
                     f45 = f7;
                     f47 = f36;
                     f51 = f36 + 1.0F;
                     f49 = f38 + 0.001F;
                     f52 = f38 + 0.001F;
                     flag7 = flag3;
                     break;
                  case d:
                     f44 = f9;
                     f45 = f10;
                     f47 = f36 + 1.0F;
                     f51 = f36;
                     f49 = f38 + 1.0F - 0.001F;
                     f52 = f38 + 1.0F - 0.001F;
                     flag7 = flag4;
                     break;
                  case e:
                     f44 = f10;
                     f45 = f8;
                     f47 = f36 + 0.001F;
                     f51 = f36 + 0.001F;
                     f49 = f38 + 1.0F;
                     f52 = f38;
                     flag7 = flag5;
                     break;
                  default:
                     f44 = f7;
                     f45 = f9;
                     f47 = f36 + 1.0F - 0.001F;
                     f51 = f36 + 1.0F - 0.001F;
                     f49 = f38;
                     f52 = f38 + 1.0F;
                     flag7 = flag6;
               }

               if (flag7 && !a(lightReaderIn, posIn, direction, Math.max(f44, f45), lightReaderIn.a_(posIn.a(direction)))) {
                  C_4675_ blockpos = posIn.a(direction);
                  TextureAtlasSprite textureatlassprite2 = atextureatlassprite[1];
                  float yMin1 = 0.0F;
                  float yMin2 = 0.0F;
                  boolean notLava = !flag;
                  if (Reflector.IForgeBlockState_shouldDisplayFluidOverlay.exists()) {
                     notLava = atextureatlassprite[2] != null;
                  }

                  if (notLava) {
                     BlockState blockState = lightReaderIn.a_(blockpos);
                     C_1706_ block = blockState.m_60734_();
                     boolean forgeFluidOverlay = false;
                     if (Reflector.IForgeBlockState_shouldDisplayFluidOverlay.exists()) {
                        forgeFluidOverlay = Reflector.callBoolean(
                           blockState, Reflector.IForgeBlockState_shouldDisplayFluidOverlay, lightReaderIn, blockpos, fluidStateIn
                        );
                     }

                     if (forgeFluidOverlay || block instanceof C_1809_ || block instanceof C_1826_ || block == C_1710_.f_50273_) {
                        textureatlassprite2 = this.d;
                     }

                     if (block == C_1710_.f_50093_ || block == C_1710_.f_152481_) {
                        yMin1 = 0.9375F;
                        yMin2 = 0.9375F;
                     }

                     if (block instanceof C_1900_ blockSlab && blockState.c(C_1900_.f_56353_) == C_2102_.BOTTOM) {
                        yMin1 = 0.5F;
                        yMin2 = 0.5F;
                     }
                  }

                  vertexBuilderIn.setSprite(textureatlassprite2);
                  if (!(f44 <= yMin1) || !(f45 <= yMin2)) {
                     yMin1 = Math.min(yMin1, f44);
                     yMin2 = Math.min(yMin2, f45);
                     if (yMin1 > f39) {
                        yMin1 -= f39;
                     }

                     if (yMin2 > f39) {
                        yMin2 -= f39;
                     }

                     float vMin1 = textureatlassprite2.c((1.0F - yMin1) * 0.5F);
                     float vMin2 = textureatlassprite2.c((1.0F - yMin2) * 0.5F);
                     float f56 = textureatlassprite2.a(0.0F);
                     float f58 = textureatlassprite2.a(0.5F);
                     float f59 = textureatlassprite2.c((1.0F - f44) * 0.5F);
                     float f60 = textureatlassprite2.c((1.0F - f45) * 0.5F);
                     float f31 = textureatlassprite2.c(0.5F);
                     float f32 = direction != Direction.c && direction != Direction.d ? lightReaderIn.a(Direction.e, true) : lightReaderIn.a(Direction.c, true);
                     float f33 = f4 * f32 * f;
                     float f34 = f4 * f32 * f1;
                     float f35 = f4 * f32 * f2;
                     this.vertexVanilla(vertexBuilderIn, f47, f37 + f44, f49, f33, f34, f35, f56, f59, j, alpha);
                     this.vertexVanilla(vertexBuilderIn, f51, f37 + f45, f52, f33, f34, f35, f58, f60, j, alpha);
                     this.vertexVanilla(vertexBuilderIn, f51, f37 + f16, f52, f33, f34, f35, f58, vMin2, j, alpha);
                     this.vertexVanilla(vertexBuilderIn, f47, f37 + f16, f49, f33, f34, f35, f56, vMin1, j, alpha);
                     if (textureatlassprite2 != this.d) {
                        this.vertexVanilla(vertexBuilderIn, f47, f37 + f16, f49, f33, f34, f35, f56, vMin1, j, alpha);
                        this.vertexVanilla(vertexBuilderIn, f51, f37 + f16, f52, f33, f34, f35, f58, vMin2, j, alpha);
                        this.vertexVanilla(vertexBuilderIn, f51, f37 + f45, f52, f33, f34, f35, f58, f60, j, alpha);
                        this.vertexVanilla(vertexBuilderIn, f47, f37 + f44, f49, f33, f34, f35, f56, f59, j, alpha);
                     }
                  }
               }
            }

            vertexBuilderIn.setSprite(null);
         }
      } finally {
         if (Config.isShaders()) {
            SVertexBuilder.popEntity(vertexBuilderIn);
         }
      }
   }

   private float a(C_1557_ readerIn, C_2690_ fluidIn, float heightOwn, float height1, float height2, C_4675_ blockPosIn) {
      if (!(height2 >= 1.0F) && !(height1 >= 1.0F)) {
         float[] afloat = new float[2];
         if (height2 > 0.0F || height1 > 0.0F) {
            float f = this.a(readerIn, fluidIn, blockPosIn);
            if (f >= 1.0F) {
               return 1.0F;
            }

            this.a(afloat, f);
         }

         this.a(afloat, heightOwn);
         this.a(afloat, height2);
         this.a(afloat, height1);
         return afloat[0] / afloat[1];
      } else {
         return 1.0F;
      }
   }

   private void a(float[] heightsIn, float heightIn) {
      if (heightIn >= 0.8F) {
         heightsIn[0] += heightIn * 10.0F;
         heightsIn[1] += 10.0F;
      } else if (heightIn >= 0.0F) {
         heightsIn[0] += heightIn;
         heightsIn[1]++;
      }
   }

   private float a(C_1557_ readerIn, C_2690_ fluidIn, C_4675_ posIn) {
      BlockState blockstate = readerIn.a_(posIn);
      return this.a(readerIn, fluidIn, posIn, blockstate, blockstate.m_60819_());
   }

   private float a(C_1557_ readerIn, C_2690_ fluidIn, C_4675_ posIn, BlockState blockStateIn, C_2691_ fluidStateIn) {
      if (fluidIn.m_6212_(fluidStateIn.m_76152_())) {
         BlockState blockstate = readerIn.a_(posIn.m_7494_());
         return fluidIn.m_6212_(blockstate.m_60819_().m_76152_()) ? 1.0F : fluidStateIn.m_76182_();
      } else {
         return !blockStateIn.m_280296_() ? 0.0F : -1.0F;
      }
   }

   private void a(VertexConsumer vertexBuilderIn, float x, float y, float z, float red, float green, float blue, float u, float v, int packedLight) {
      vertexBuilderIn.a(x, y, z).a(red, green, blue, 1.0F).a(u, v).c(packedLight).b(0.0F, 1.0F, 0.0F);
   }

   private void vertexVanilla(
      VertexConsumer buffer, float x, float y, float z, float red, float green, float blue, float u, float v, int combinedLight, float alpha
   ) {
      buffer.a(x, y, z).a(red, green, blue, alpha).a(u, v).c(combinedLight).b(0.0F, 1.0F, 0.0F);
   }

   private int a(C_1557_ lightReaderIn, C_4675_ posIn) {
      int i = LevelRenderer.a(lightReaderIn, posIn);
      int j = LevelRenderer.a(lightReaderIn, posIn.m_7494_());
      int k = i & 0xFF;
      int l = j & 0xFF;
      int i1 = i >> 16 & 0xFF;
      int j1 = j >> 16 & 0xFF;
      return (k > l ? k : l) | (i1 > j1 ? i1 : j1) << 16;
   }
}
