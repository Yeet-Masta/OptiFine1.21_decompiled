package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.Shaders;

public class LiquidBlockRenderer {
   private static final float f_173402_ = 0.8888889F;
   private final TextureAtlasSprite[] f_110940_ = new TextureAtlasSprite[2];
   private final TextureAtlasSprite[] f_110941_ = new TextureAtlasSprite[2];
   private TextureAtlasSprite f_110942_;

   protected void m_110944_() {
      this.f_110940_[0] = Minecraft.m_91087_().m_91304_().m_119430_().m_110893_(Blocks.f_49991_.m_49966_()).m_6160_();
      this.f_110940_[1] = ModelBakery.f_119221_.m_119204_();
      this.f_110941_[0] = Minecraft.m_91087_().m_91304_().m_119430_().m_110893_(Blocks.f_49990_.m_49966_()).m_6160_();
      this.f_110941_[1] = ModelBakery.f_119222_.m_119204_();
      this.f_110942_ = ModelBakery.f_119223_.m_119204_();
   }

   private static boolean m_203185_(FluidState fluidState1, FluidState fluidState2) {
      return fluidState2.m_76152_().m_6212_(fluidState1.m_76152_());
   }

   private static boolean m_110978_(BlockGetter reader, Direction face, float heightIn, BlockPos pos, BlockState blockState) {
      if (blockState.m_60815_()) {
         VoxelShape voxelshape = Shapes.m_83048_(0.0, 0.0, 0.0, 1.0, (double)heightIn, 1.0);
         VoxelShape voxelshape1 = blockState.m_60768_(reader, pos);
         return Shapes.m_83117_(voxelshape, voxelshape1, face);
      } else {
         return false;
      }
   }

   private static boolean m_203179_(BlockGetter reader, BlockPos pos, Direction face, float heightIn, BlockState blockStateIn) {
      return m_110978_(reader, face, heightIn, pos.m_121945_(face), blockStateIn);
   }

   private static boolean m_110959_(BlockGetter reader, BlockPos pos, BlockState blockState, Direction face) {
      return m_110978_(reader, face.m_122424_(), 1.0F, pos, blockState);
   }

   public static boolean m_203166_(BlockAndTintGetter reader, BlockPos pos, FluidState fluidStateIn, BlockState blockStateIn, Direction sideIn, FluidState fluidState2In) {
      return !m_110959_(reader, pos, blockStateIn, sideIn) && !m_203185_(fluidStateIn, fluidState2In);
   }

   public void m_234369_(BlockAndTintGetter lightReaderIn, BlockPos posIn, VertexConsumer vertexBuilderIn, BlockState blockStateIn, FluidState fluidStateIn) {
      BlockState blockStateFluid = fluidStateIn.m_76188_();

      try {
         if (Config.isShaders()) {
            SVertexBuilder.pushEntity(blockStateFluid, vertexBuilderIn);
         }

         boolean flag = fluidStateIn.m_205070_(FluidTags.f_13132_);
         TextureAtlasSprite[] atextureatlassprite = flag ? this.f_110940_ : this.f_110941_;
         if (Reflector.ForgeHooksClient_getFluidSprites.exists()) {
            TextureAtlasSprite[] forgeFluidSprites = (TextureAtlasSprite[])Reflector.call(Reflector.ForgeHooksClient_getFluidSprites, lightReaderIn, posIn, fluidStateIn);
            if (forgeFluidSprites != null) {
               atextureatlassprite = forgeFluidSprites;
            }
         }

         RenderEnv renderEnv = vertexBuilderIn.getRenderEnv(blockStateFluid, posIn);
         boolean smoothLighting = !flag && Minecraft.m_91086_();
         int i = -1;
         float alpha = 1.0F;
         if (Reflector.ForgeHooksClient.exists()) {
            i = IClientFluidTypeExtensions.of(fluidStateIn).getTintColor(fluidStateIn, lightReaderIn, posIn);
            alpha = (float)(i >> 24 & 255) / 255.0F;
         }

         BlockState blockstate = lightReaderIn.m_8055_(posIn.m_121945_(Direction.DOWN));
         FluidState fluidstate = blockstate.m_60819_();
         BlockState blockstate1 = lightReaderIn.m_8055_(posIn.m_121945_(Direction.field_61));
         FluidState fluidstate1 = blockstate1.m_60819_();
         BlockState blockstate2 = lightReaderIn.m_8055_(posIn.m_121945_(Direction.NORTH));
         FluidState fluidstate2 = blockstate2.m_60819_();
         BlockState blockstate3 = lightReaderIn.m_8055_(posIn.m_121945_(Direction.SOUTH));
         FluidState fluidstate3 = blockstate3.m_60819_();
         BlockState blockstate4 = lightReaderIn.m_8055_(posIn.m_121945_(Direction.WEST));
         FluidState fluidstate4 = blockstate4.m_60819_();
         BlockState blockstate5 = lightReaderIn.m_8055_(posIn.m_121945_(Direction.EAST));
         FluidState fluidstate5 = blockstate5.m_60819_();
         boolean flag1 = !m_203185_(fluidStateIn, fluidstate1);
         boolean flag2 = m_203166_(lightReaderIn, posIn, fluidStateIn, blockStateIn, Direction.DOWN, fluidstate) && !m_203179_(lightReaderIn, posIn, Direction.DOWN, 0.8888889F, blockstate);
         boolean flag3 = m_203166_(lightReaderIn, posIn, fluidStateIn, blockStateIn, Direction.NORTH, fluidstate2);
         boolean flag4 = m_203166_(lightReaderIn, posIn, fluidStateIn, blockStateIn, Direction.SOUTH, fluidstate3);
         boolean flag5 = m_203166_(lightReaderIn, posIn, fluidStateIn, blockStateIn, Direction.WEST, fluidstate4);
         boolean flag6 = m_203166_(lightReaderIn, posIn, fluidStateIn, blockStateIn, Direction.EAST, fluidstate5);
         if (flag1 || flag2 || flag6 || flag5 || flag3 || flag4) {
            if (i < 0) {
               i = CustomColors.getFluidColor(lightReaderIn, blockStateFluid, posIn, renderEnv);
            }

            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            float f3 = lightReaderIn.m_7717_(Direction.DOWN, true);
            float f4 = lightReaderIn.m_7717_(Direction.field_61, true);
            float f5 = lightReaderIn.m_7717_(Direction.NORTH, true);
            float f6 = lightReaderIn.m_7717_(Direction.WEST, true);
            Fluid fluid = fluidStateIn.m_76152_();
            float f11 = this.m_203160_(lightReaderIn, fluid, posIn, blockStateIn, fluidStateIn);
            float f7;
            float f8;
            float f9;
            float f10;
            float f36;
            float f37;
            float f38;
            float f39;
            if (f11 >= 1.0F) {
               f7 = 1.0F;
               f8 = 1.0F;
               f9 = 1.0F;
               f10 = 1.0F;
            } else {
               f36 = this.m_203160_(lightReaderIn, fluid, posIn.m_122012_(), blockstate2, fluidstate2);
               f37 = this.m_203160_(lightReaderIn, fluid, posIn.m_122019_(), blockstate3, fluidstate3);
               f38 = this.m_203160_(lightReaderIn, fluid, posIn.m_122029_(), blockstate5, fluidstate5);
               f39 = this.m_203160_(lightReaderIn, fluid, posIn.m_122024_(), blockstate4, fluidstate4);
               f7 = this.m_203149_(lightReaderIn, fluid, f11, f36, f38, posIn.m_121945_(Direction.NORTH).m_121945_(Direction.EAST));
               f8 = this.m_203149_(lightReaderIn, fluid, f11, f36, f39, posIn.m_121945_(Direction.NORTH).m_121945_(Direction.WEST));
               f9 = this.m_203149_(lightReaderIn, fluid, f11, f37, f38, posIn.m_121945_(Direction.SOUTH).m_121945_(Direction.EAST));
               f10 = this.m_203149_(lightReaderIn, fluid, f11, f37, f39, posIn.m_121945_(Direction.SOUTH).m_121945_(Direction.WEST));
            }

            f36 = (float)(posIn.m_123341_() & 15);
            f37 = (float)(posIn.m_123342_() & 15);
            f38 = (float)(posIn.m_123343_() & 15);
            int j;
            int k;
            if (Config.isRenderRegions()) {
               int chunkX = posIn.m_123341_() >> 4 << 4;
               int chunkY = posIn.m_123342_() >> 4 << 4;
               j = posIn.m_123343_() >> 4 << 4;
               int bits = 8;
               int regionX = chunkX >> bits << bits;
               int regionZ = j >> bits << bits;
               k = chunkX - regionX;
               int dz = j - regionZ;
               f36 += (float)k;
               f37 += (float)chunkY;
               f38 += (float)dz;
            }

            if (Config.isShaders() && Shaders.useMidBlockAttrib) {
               vertexBuilderIn.setMidBlock((float)((double)f36 + 0.5), (float)((double)f37 + 0.5), (float)((double)f38 + 0.5));
            }

            f39 = 0.001F;
            float f16 = flag2 ? 0.001F : 0.0F;
            float f47;
            float f51;
            float f52;
            float yMin1;
            float yMin2;
            float f17;
            float f18;
            float f44;
            float f45;
            float f49;
            float f58;
            float f59;
            float f60;
            if (flag1 && !m_203179_(lightReaderIn, posIn, Direction.field_61, Math.min(Math.min(f8, f10), Math.min(f9, f7)), blockstate1)) {
               f8 -= 0.001F;
               f10 -= 0.001F;
               f9 -= 0.001F;
               f7 -= 0.001F;
               Vec3 vec3 = fluidStateIn.m_76179_(lightReaderIn, posIn);
               TextureAtlasSprite textureatlassprite;
               float f54;
               float f55;
               if (vec3.f_82479_ == 0.0 && vec3.f_82481_ == 0.0) {
                  textureatlassprite = atextureatlassprite[0];
                  vertexBuilderIn.setSprite(textureatlassprite);
                  f17 = textureatlassprite.m_118367_(0.0F);
                  f47 = textureatlassprite.m_118393_(0.0F);
                  f18 = f17;
                  f49 = textureatlassprite.m_118393_(1.0F);
                  f44 = textureatlassprite.m_118367_(1.0F);
                  f51 = f49;
                  f45 = f44;
                  f52 = f47;
               } else {
                  textureatlassprite = atextureatlassprite[1];
                  vertexBuilderIn.setSprite(textureatlassprite);
                  f54 = (float)Mth.m_14136_(vec3.f_82481_, vec3.f_82479_) - 1.5707964F;
                  f55 = Mth.m_14031_(f54) * 0.25F;
                  yMin1 = Mth.m_14089_(f54) * 0.25F;
                  yMin2 = 0.5F;
                  f17 = textureatlassprite.m_118367_(0.5F + (-yMin1 - f55));
                  f47 = textureatlassprite.m_118393_(0.5F + -yMin1 + f55);
                  f18 = textureatlassprite.m_118367_(0.5F + -yMin1 + f55);
                  f49 = textureatlassprite.m_118393_(0.5F + yMin1 + f55);
                  f44 = textureatlassprite.m_118367_(0.5F + yMin1 + f55);
                  f51 = textureatlassprite.m_118393_(0.5F + (yMin1 - f55));
                  f45 = textureatlassprite.m_118367_(0.5F + (yMin1 - f55));
                  f52 = textureatlassprite.m_118393_(0.5F + (-yMin1 - f55));
               }

               float f53 = (f17 + f18 + f44 + f45) / 4.0F;
               f54 = (f47 + f49 + f51 + f52) / 4.0F;
               f55 = atextureatlassprite[0].m_118417_();
               f17 = Mth.m_14179_(f55, f17, f53);
               f18 = Mth.m_14179_(f55, f18, f53);
               f44 = Mth.m_14179_(f55, f44, f53);
               f45 = Mth.m_14179_(f55, f45, f53);
               f47 = Mth.m_14179_(f55, f47, f54);
               f49 = Mth.m_14179_(f55, f49, f54);
               f51 = Mth.m_14179_(f55, f51, f54);
               f52 = Mth.m_14179_(f55, f52, f54);
               int l = this.m_110945_(lightReaderIn, posIn);
               int combinedLightNW = l;
               int combinedLightSW = l;
               int combinedLightSE = l;
               int combinedLightNE = l;
               if (smoothLighting) {
                  BlockPos posN = posIn.m_122012_();
                  BlockPos posS = posIn.m_122019_();
                  BlockPos posE = posIn.m_122029_();
                  BlockPos posW = posIn.m_122024_();
                  int lightN = this.m_110945_(lightReaderIn, posN);
                  int lightS = this.m_110945_(lightReaderIn, posS);
                  int lightE = this.m_110945_(lightReaderIn, posE);
                  int lightW = this.m_110945_(lightReaderIn, posW);
                  int lightNW = this.m_110945_(lightReaderIn, posN.m_122024_());
                  int lightSW = this.m_110945_(lightReaderIn, posS.m_122024_());
                  int lightSE = this.m_110945_(lightReaderIn, posS.m_122029_());
                  int lightNE = this.m_110945_(lightReaderIn, posN.m_122029_());
                  combinedLightNW = ModelBlockRenderer.AmbientOcclusionFace.m_111153_(lightN, lightNW, lightW, l);
                  combinedLightSW = ModelBlockRenderer.AmbientOcclusionFace.m_111153_(lightS, lightSW, lightW, l);
                  combinedLightSE = ModelBlockRenderer.AmbientOcclusionFace.m_111153_(lightS, lightSE, lightE, l);
                  combinedLightNE = ModelBlockRenderer.AmbientOcclusionFace.m_111153_(lightN, lightNE, lightE, l);
               }

               f58 = f4 * f;
               f59 = f4 * f1;
               f60 = f4 * f2;
               this.vertexVanilla(vertexBuilderIn, f36 + 0.0F, f37 + f8, f38 + 0.0F, f58, f59, f60, f17, f47, combinedLightNW, alpha);
               this.vertexVanilla(vertexBuilderIn, f36 + 0.0F, f37 + f10, f38 + 1.0F, f58, f59, f60, f18, f49, combinedLightSW, alpha);
               this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f9, f38 + 1.0F, f58, f59, f60, f44, f51, combinedLightSE, alpha);
               this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f7, f38 + 0.0F, f58, f59, f60, f45, f52, combinedLightNE, alpha);
               if (fluidStateIn.m_76171_(lightReaderIn, posIn.m_7494_())) {
                  this.vertexVanilla(vertexBuilderIn, f36 + 0.0F, f37 + f8, f38 + 0.0F, f58, f59, f60, f17, f47, combinedLightNW, alpha);
                  this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f7, f38 + 0.0F, f58, f59, f60, f45, f52, combinedLightNE, alpha);
                  this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f9, f38 + 1.0F, f58, f59, f60, f44, f51, combinedLightSE, alpha);
                  this.vertexVanilla(vertexBuilderIn, f36 + 0.0F, f37 + f10, f38 + 1.0F, f58, f59, f60, f18, f49, combinedLightSW, alpha);
               }
            }

            if (flag2) {
               vertexBuilderIn.setSprite(atextureatlassprite[0]);
               float f40 = atextureatlassprite[0].m_118409_();
               f17 = atextureatlassprite[0].m_118410_();
               f18 = atextureatlassprite[0].m_118411_();
               f44 = atextureatlassprite[0].m_118412_();
               k = this.m_110945_(lightReaderIn, posIn.m_7495_());
               f47 = lightReaderIn.m_7717_(Direction.DOWN, true);
               f49 = f47 * f;
               f51 = f47 * f1;
               f52 = f47 * f2;
               this.vertexVanilla(vertexBuilderIn, f36, f37 + f16, f38 + 1.0F, f49, f51, f52, f40, f44, k, alpha);
               this.vertexVanilla(vertexBuilderIn, f36, f37 + f16, f38, f49, f51, f52, f40, f18, k, alpha);
               this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f16, f38, f49, f51, f52, f17, f18, k, alpha);
               this.vertexVanilla(vertexBuilderIn, f36 + 1.0F, f37 + f16, f38 + 1.0F, f49, f51, f52, f17, f44, k, alpha);
            }

            j = this.m_110945_(lightReaderIn, posIn);
            Iterator var89 = Direction.Plane.HORIZONTAL.iterator();

            while(true) {
               Direction direction;
               TextureAtlasSprite textureatlassprite2;
               do {
                  boolean flag7;
                  do {
                     do {
                        if (!var89.hasNext()) {
                           vertexBuilderIn.setSprite((TextureAtlasSprite)null);
                           return;
                        }

                        direction = (Direction)var89.next();
                        switch (direction) {
                           case NORTH:
                              f44 = f8;
                              f45 = f7;
                              f47 = f36;
                              f51 = f36 + 1.0F;
                              f49 = f38 + 0.001F;
                              f52 = f38 + 0.001F;
                              flag7 = flag3;
                              break;
                           case SOUTH:
                              f44 = f9;
                              f45 = f10;
                              f47 = f36 + 1.0F;
                              f51 = f36;
                              f49 = f38 + 1.0F - 0.001F;
                              f52 = f38 + 1.0F - 0.001F;
                              flag7 = flag4;
                              break;
                           case WEST:
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
                     } while(!flag7);
                  } while(m_203179_(lightReaderIn, posIn, direction, Math.max(f44, f45), lightReaderIn.m_8055_(posIn.m_121945_(direction))));

                  BlockPos blockpos = posIn.m_121945_(direction);
                  textureatlassprite2 = atextureatlassprite[1];
                  yMin1 = 0.0F;
                  yMin2 = 0.0F;
                  boolean notLava = !flag;
                  if (Reflector.IForgeBlockState_shouldDisplayFluidOverlay.exists()) {
                     notLava = atextureatlassprite[2] != null;
                  }

                  if (notLava) {
                     BlockState blockState = lightReaderIn.m_8055_(blockpos);
                     Block block = blockState.m_60734_();
                     boolean forgeFluidOverlay = false;
                     if (Reflector.IForgeBlockState_shouldDisplayFluidOverlay.exists()) {
                        forgeFluidOverlay = Reflector.callBoolean(blockState, Reflector.IForgeBlockState_shouldDisplayFluidOverlay, lightReaderIn, blockpos, fluidStateIn);
                     }

                     if (forgeFluidOverlay || block instanceof HalfTransparentBlock || block instanceof LeavesBlock || block == Blocks.f_50273_) {
                        textureatlassprite2 = this.f_110942_;
                     }

                     if (block == Blocks.f_50093_ || block == Blocks.f_152481_) {
                        yMin1 = 0.9375F;
                        yMin2 = 0.9375F;
                     }

                     if (block instanceof SlabBlock) {
                        SlabBlock blockSlab = (SlabBlock)block;
                        if (blockState.m_61143_(SlabBlock.f_56353_) == SlabType.BOTTOM) {
                           yMin1 = 0.5F;
                           yMin2 = 0.5F;
                        }
                     }
                  }

                  vertexBuilderIn.setSprite(textureatlassprite2);
               } while(f44 <= yMin1 && f45 <= yMin2);

               yMin1 = Math.min(yMin1, f44);
               yMin2 = Math.min(yMin2, f45);
               if (yMin1 > f39) {
                  yMin1 -= f39;
               }

               if (yMin2 > f39) {
                  yMin2 -= f39;
               }

               float vMin1 = textureatlassprite2.m_118393_((1.0F - yMin1) * 0.5F);
               float vMin2 = textureatlassprite2.m_118393_((1.0F - yMin2) * 0.5F);
               float f56 = textureatlassprite2.m_118367_(0.0F);
               f58 = textureatlassprite2.m_118367_(0.5F);
               f59 = textureatlassprite2.m_118393_((1.0F - f44) * 0.5F);
               f60 = textureatlassprite2.m_118393_((1.0F - f45) * 0.5F);
               float f31 = textureatlassprite2.m_118393_(0.5F);
               float f32 = direction != Direction.NORTH && direction != Direction.SOUTH ? lightReaderIn.m_7717_(Direction.WEST, true) : lightReaderIn.m_7717_(Direction.NORTH, true);
               float f33 = f4 * f32 * f;
               float f34 = f4 * f32 * f1;
               float f35 = f4 * f32 * f2;
               this.vertexVanilla(vertexBuilderIn, f47, f37 + f44, f49, f33, f34, f35, f56, f59, j, alpha);
               this.vertexVanilla(vertexBuilderIn, f51, f37 + f45, f52, f33, f34, f35, f58, f60, j, alpha);
               this.vertexVanilla(vertexBuilderIn, f51, f37 + f16, f52, f33, f34, f35, f58, vMin2, j, alpha);
               this.vertexVanilla(vertexBuilderIn, f47, f37 + f16, f49, f33, f34, f35, f56, vMin1, j, alpha);
               if (textureatlassprite2 != this.f_110942_) {
                  this.vertexVanilla(vertexBuilderIn, f47, f37 + f16, f49, f33, f34, f35, f56, vMin1, j, alpha);
                  this.vertexVanilla(vertexBuilderIn, f51, f37 + f16, f52, f33, f34, f35, f58, vMin2, j, alpha);
                  this.vertexVanilla(vertexBuilderIn, f51, f37 + f45, f52, f33, f34, f35, f58, f60, j, alpha);
                  this.vertexVanilla(vertexBuilderIn, f47, f37 + f44, f49, f33, f34, f35, f56, f59, j, alpha);
               }
            }
         }
      } finally {
         if (Config.isShaders()) {
            SVertexBuilder.popEntity(vertexBuilderIn);
         }

      }

   }

   private float m_203149_(BlockAndTintGetter readerIn, Fluid fluidIn, float heightOwn, float height1, float height2, BlockPos blockPosIn) {
      if (!(height2 >= 1.0F) && !(height1 >= 1.0F)) {
         float[] afloat = new float[2];
         if (height2 > 0.0F || height1 > 0.0F) {
            float f = this.m_203156_(readerIn, fluidIn, blockPosIn);
            if (f >= 1.0F) {
               return 1.0F;
            }

            this.m_203188_(afloat, f);
         }

         this.m_203188_(afloat, heightOwn);
         this.m_203188_(afloat, height2);
         this.m_203188_(afloat, height1);
         return afloat[0] / afloat[1];
      } else {
         return 1.0F;
      }
   }

   private void m_203188_(float[] heightsIn, float heightIn) {
      if (heightIn >= 0.8F) {
         heightsIn[0] += heightIn * 10.0F;
         heightsIn[1] += 10.0F;
      } else if (heightIn >= 0.0F) {
         heightsIn[0] += heightIn;
         int var10002 = heightsIn[1]++;
      }

   }

   private float m_203156_(BlockAndTintGetter readerIn, Fluid fluidIn, BlockPos posIn) {
      BlockState blockstate = readerIn.m_8055_(posIn);
      return this.m_203160_(readerIn, fluidIn, posIn, blockstate, blockstate.m_60819_());
   }

   private float m_203160_(BlockAndTintGetter readerIn, Fluid fluidIn, BlockPos posIn, BlockState blockStateIn, FluidState fluidStateIn) {
      if (fluidIn.m_6212_(fluidStateIn.m_76152_())) {
         BlockState blockstate = readerIn.m_8055_(posIn.m_7494_());
         return fluidIn.m_6212_(blockstate.m_60819_().m_76152_()) ? 1.0F : fluidStateIn.m_76182_();
      } else {
         return !blockStateIn.m_280296_() ? 0.0F : -1.0F;
      }
   }

   private void m_110984_(VertexConsumer vertexBuilderIn, float x, float y, float z, float red, float green, float blue, float u, float v, int packedLight) {
      vertexBuilderIn.m_167146_(x, y, z).m_340057_(red, green, blue, 1.0F).m_167083_(u, v).m_338973_(packedLight).m_338525_(0.0F, 1.0F, 0.0F);
   }

   private void vertexVanilla(VertexConsumer buffer, float x, float y, float z, float red, float green, float blue, float u, float v, int combinedLight, float alpha) {
      buffer.m_167146_(x, y, z).m_340057_(red, green, blue, alpha).m_167083_(u, v).m_338973_(combinedLight).m_338525_(0.0F, 1.0F, 0.0F);
   }

   private int m_110945_(BlockAndTintGetter lightReaderIn, BlockPos posIn) {
      int i = LevelRenderer.m_109541_(lightReaderIn, posIn);
      int j = LevelRenderer.m_109541_(lightReaderIn, posIn.m_7494_());
      int k = i & 255;
      int l = j & 255;
      int i1 = i >> 16 & 255;
      int j1 = j >> 16 & 255;
      return (k > l ? k : l) | (i1 > j1 ? i1 : j1) << 16;
   }
}
