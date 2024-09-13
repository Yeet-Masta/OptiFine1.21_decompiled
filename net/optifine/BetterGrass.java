package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.model.BlockModelUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.RandomUtils;

public class BetterGrass {
   private static boolean betterGrass = true;
   private static boolean betterDirtPath = true;
   private static boolean betterMycelium = true;
   private static boolean betterPodzol = true;
   private static boolean betterCrimsonNylium = true;
   private static boolean betterWarpedNylium = true;
   private static boolean betterGrassSnow = true;
   private static boolean betterMyceliumSnow = true;
   private static boolean betterPodzolSnow = true;
   private static boolean grassMultilayer = false;
   private static TextureAtlasSprite spriteGrass = null;
   private static TextureAtlasSprite spriteGrassSide = null;
   private static TextureAtlasSprite spriteDirtPath = null;
   private static TextureAtlasSprite spriteDirtPathSide = null;
   private static TextureAtlasSprite spriteMycelium = null;
   private static TextureAtlasSprite spritePodzol = null;
   private static TextureAtlasSprite spriteCrimsonNylium = null;
   private static TextureAtlasSprite spriteWarpedNylium = null;
   private static TextureAtlasSprite spriteSnow = null;
   private static boolean spritesLoaded = false;
   private static BakedModel modelCubeGrass = null;
   private static BakedModel modelDirtPath = null;
   private static BakedModel modelCubeDirtPath = null;
   private static BakedModel modelCubeMycelium = null;
   private static BakedModel modelCubePodzol = null;
   private static BakedModel modelCubeCrimsonNylium = null;
   private static BakedModel modelCubeWarpedNylium = null;
   private static BakedModel modelCubeSnow = null;
   private static boolean modelsLoaded = false;
   private static String TEXTURE_GRASS_DEFAULT;
   private static String TEXTURE_GRASS_SIDE_DEFAULT;
   private static String TEXTURE_DIRT_PATH_DEFAULT;
   private static String TEXTURE_DIRT_PATH_SIDE_DEFAULT;
   private static String TEXTURE_MYCELIUM_DEFAULT;
   private static String TEXTURE_PODZOL_DEFAULT;
   private static String TEXTURE_CRIMSON_NYLIUM;
   private static String TEXTURE_WARPED_NYLIUM;
   private static String TEXTURE_SNOW_DEFAULT;
   private static RandomSource RANDOM = RandomUtils.makeThreadSafeRandomSource(0);

   public static void updateIcons(TextureAtlas textureMap) {
      spritesLoaded = false;
      modelsLoaded = false;
      loadProperties(textureMap);
   }

   public static void m_252999_() {
      if (spritesLoaded) {
         modelCubeGrass = BlockModelUtils.makeModelCube(spriteGrass, 0);
         if (grassMultilayer) {
            BakedModel modelCubeGrassSide = BlockModelUtils.makeModelCube(spriteGrassSide, -1);
            modelCubeGrass = BlockModelUtils.joinModelsCube(modelCubeGrassSide, modelCubeGrass);
         }

         modelDirtPath = BlockModelUtils.makeModel("dirt_path", spriteDirtPathSide, spriteDirtPath);
         modelCubeDirtPath = BlockModelUtils.makeModelCube(spriteDirtPath, -1);
         modelCubeMycelium = BlockModelUtils.makeModelCube(spriteMycelium, -1);
         modelCubePodzol = BlockModelUtils.makeModelCube(spritePodzol, 0);
         modelCubeCrimsonNylium = BlockModelUtils.makeModelCube(spriteCrimsonNylium, -1);
         modelCubeWarpedNylium = BlockModelUtils.makeModelCube(spriteWarpedNylium, -1);
         modelCubeSnow = BlockModelUtils.makeModelCube(spriteSnow, -1);
         modelsLoaded = true;
      }
   }

   private static void loadProperties(TextureAtlas textureMap) {
      betterGrass = true;
      betterDirtPath = true;
      betterMycelium = true;
      betterPodzol = true;
      betterCrimsonNylium = true;
      betterWarpedNylium = true;
      betterGrassSnow = true;
      betterMyceliumSnow = true;
      betterPodzolSnow = true;
      spriteGrass = textureMap.registerSprite(new ResourceLocation("block/grass_block_top"));
      spriteGrassSide = textureMap.registerSprite(new ResourceLocation("block/grass_block_side"));
      spriteDirtPath = textureMap.registerSprite(new ResourceLocation("block/dirt_path_top"));
      spriteDirtPathSide = textureMap.registerSprite(new ResourceLocation("block/dirt_path_side"));
      spriteMycelium = textureMap.registerSprite(new ResourceLocation("block/mycelium_top"));
      spritePodzol = textureMap.registerSprite(new ResourceLocation("block/podzol_top"));
      spriteCrimsonNylium = textureMap.registerSprite(new ResourceLocation("block/crimson_nylium"));
      spriteWarpedNylium = textureMap.registerSprite(new ResourceLocation("block/warped_nylium"));
      spriteSnow = textureMap.registerSprite(new ResourceLocation("block/snow"));
      spritesLoaded = true;
      String name = "optifine/bettergrass.properties";

      try {
         ResourceLocation locFile = new ResourceLocation(name);
         if (!Config.hasResource(locFile)) {
            return;
         }

         InputStream in = Config.getResourceStream(locFile);
         if (in == null) {
            return;
         }

         boolean defaultConfig = Config.isFromDefaultResourcePack(locFile);
         if (defaultConfig) {
            Config.dbg("BetterGrass: Parsing default configuration " + name);
         } else {
            Config.dbg("BetterGrass: Parsing configuration " + name);
         }

         Properties props = new PropertiesOrdered();
         props.load(in);
         in.close();
         betterGrass = getBoolean(props, "grass", true);
         betterDirtPath = getBoolean(props, "dirt_path", true);
         betterMycelium = getBoolean(props, "mycelium", true);
         betterPodzol = getBoolean(props, "podzol", true);
         betterCrimsonNylium = getBoolean(props, "crimson_nylium", true);
         betterWarpedNylium = getBoolean(props, "warped_nylium", true);
         betterGrassSnow = getBoolean(props, "grass.snow", true);
         betterMyceliumSnow = getBoolean(props, "mycelium.snow", true);
         betterPodzolSnow = getBoolean(props, "podzol.snow", true);
         grassMultilayer = getBoolean(props, "grass.multilayer", false);
         spriteGrass = registerSprite(props, "texture.grass", "block/grass_block_top", textureMap);
         spriteGrassSide = registerSprite(props, "texture.grass_side", "block/grass_block_side", textureMap);
         spriteDirtPath = registerSprite(props, "texture.dirt_path", "block/dirt_path_top", textureMap);
         spriteDirtPathSide = registerSprite(props, "texture.dirt_path_side", "block/dirt_path_side", textureMap);
         spriteMycelium = registerSprite(props, "texture.mycelium", "block/mycelium_top", textureMap);
         spritePodzol = registerSprite(props, "texture.podzol", "block/podzol_top", textureMap);
         spriteCrimsonNylium = registerSprite(props, "texture.crimson_nylium", "block/crimson_nylium", textureMap);
         spriteWarpedNylium = registerSprite(props, "texture.warped_nylium", "block/warped_nylium", textureMap);
         spriteSnow = registerSprite(props, "texture.snow", "block/snow", textureMap);
      } catch (IOException var6) {
         Config.warn("Error reading: " + name + ", " + var6.getClass().getName() + ": " + var6.getMessage());
      }
   }

   public static void refreshIcons(TextureAtlas textureMap) {
      spriteGrass = getSprite(textureMap, spriteGrass.getName());
      spriteGrassSide = getSprite(textureMap, spriteGrassSide.getName());
      spriteDirtPath = getSprite(textureMap, spriteDirtPath.getName());
      spriteDirtPathSide = getSprite(textureMap, spriteDirtPathSide.getName());
      spriteMycelium = getSprite(textureMap, spriteMycelium.getName());
      spritePodzol = getSprite(textureMap, spritePodzol.getName());
      spriteCrimsonNylium = getSprite(textureMap, spriteCrimsonNylium.getName());
      spriteWarpedNylium = getSprite(textureMap, spriteWarpedNylium.getName());
      spriteSnow = getSprite(textureMap, spriteSnow.getName());
   }

   private static TextureAtlasSprite getSprite(TextureAtlas textureMap, ResourceLocation loc) {
      TextureAtlasSprite sprite = textureMap.m_118316_(loc);
      if (sprite == null || MissingTextureAtlasSprite.isMisingSprite(sprite)) {
         Config.warn("Missing BetterGrass sprite: " + loc);
      }

      return sprite;
   }

   private static TextureAtlasSprite registerSprite(Properties props, String key, String textureDefault, TextureAtlas textureMap) {
      String texture = props.getProperty(key);
      if (texture == null) {
         texture = textureDefault;
      }

      ResourceLocation locPng = new ResourceLocation("textures/" + texture + ".png");
      if (!Config.hasResource(locPng)) {
         Config.warn("BetterGrass texture not found: " + locPng);
         texture = textureDefault;
      }

      ResourceLocation locSprite = new ResourceLocation(texture);
      return textureMap.registerSprite(locSprite);
   }

   public static List getFaceQuads(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
      if (facing == Direction.UP || facing == Direction.DOWN) {
         return quads;
      } else if (!modelsLoaded) {
         return quads;
      } else {
         Block block = blockState.m_60734_();
         if (block instanceof MyceliumBlock) {
            return getFaceQuadsMycelium(blockAccess, blockState, blockPos, facing, quads);
         } else if (block instanceof DirtPathBlock) {
            return getFaceQuadsDirtPath(blockAccess, blockState, blockPos, facing, quads);
         } else if (block == Blocks.f_50599_) {
            return getFaceQuadsPodzol(blockAccess, blockState, blockPos, facing, quads);
         } else if (block == Blocks.f_50699_) {
            return getFaceQuadsCrimsonNylium(blockAccess, blockState, blockPos, facing, quads);
         } else if (block == Blocks.f_50690_) {
            return getFaceQuadsWarpedNylium(blockAccess, blockState, blockPos, facing, quads);
         } else if (block == Blocks.f_50493_) {
            return getFaceQuadsDirt(blockAccess, blockState, blockPos, facing, quads);
         } else {
            return block instanceof GrassBlock ? getFaceQuadsGrass(blockAccess, blockState, blockPos, facing, quads) : quads;
         }
      }
   }

   private static List getFaceQuadsMycelium(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
      Block blockUp = blockAccess.m_8055_(blockPos.m_7494_()).m_60734_();
      boolean snowy = blockUp == Blocks.f_50127_ || blockUp == Blocks.f_50125_;
      if (Config.isBetterGrassFancy()) {
         if (snowy) {
            if (betterMyceliumSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.f_50125_) {
               return modelCubeSnow.m_213637_(blockState, facing, RANDOM);
            }
         } else if (betterMycelium && getBlockAt(blockPos.m_7495_(), facing, blockAccess) == Blocks.f_50195_) {
            return modelCubeMycelium.m_213637_(blockState, facing, RANDOM);
         }
      } else if (snowy) {
         if (betterMyceliumSnow) {
            return modelCubeSnow.m_213637_(blockState, facing, RANDOM);
         }
      } else if (betterMycelium) {
         return modelCubeMycelium.m_213637_(blockState, facing, RANDOM);
      }

      return quads;
   }

   private static List getFaceQuadsDirtPath(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
      if (!betterDirtPath) {
         return quads;
      } else if (Config.isBetterGrassFancy()) {
         return getBlockAt(blockPos.m_7495_(), facing, blockAccess) == Blocks.f_152481_ ? modelDirtPath.m_213637_(blockState, facing, RANDOM) : quads;
      } else {
         return modelDirtPath.m_213637_(blockState, facing, RANDOM);
      }
   }

   private static List getFaceQuadsPodzol(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
      Block blockTop = getBlockAt(blockPos, Direction.UP, blockAccess);
      boolean snowy = blockTop == Blocks.f_50127_ || blockTop == Blocks.f_50125_;
      if (Config.isBetterGrassFancy()) {
         if (snowy) {
            if (betterPodzolSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.f_50125_) {
               return modelCubeSnow.m_213637_(blockState, facing, RANDOM);
            }
         } else if (betterPodzol) {
            BlockPos posSide = blockPos.m_7495_().m_121945_(facing);
            BlockState stateSide = blockAccess.m_8055_(posSide);
            if (stateSide.m_60734_() == Blocks.f_50599_) {
               return modelCubePodzol.m_213637_(blockState, facing, RANDOM);
            }
         }
      } else if (snowy) {
         if (betterPodzolSnow) {
            return modelCubeSnow.m_213637_(blockState, facing, RANDOM);
         }
      } else if (betterPodzol) {
         return modelCubePodzol.m_213637_(blockState, facing, RANDOM);
      }

      return quads;
   }

   private static List getFaceQuadsCrimsonNylium(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
      if (!betterCrimsonNylium) {
         return quads;
      } else if (Config.isBetterGrassFancy()) {
         return getBlockAt(blockPos.m_7495_(), facing, blockAccess) == Blocks.f_50699_ ? modelCubeCrimsonNylium.m_213637_(blockState, facing, RANDOM) : quads;
      } else {
         return modelCubeCrimsonNylium.m_213637_(blockState, facing, RANDOM);
      }
   }

   private static List getFaceQuadsWarpedNylium(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
      if (!betterWarpedNylium) {
         return quads;
      } else if (Config.isBetterGrassFancy()) {
         return getBlockAt(blockPos.m_7495_(), facing, blockAccess) == Blocks.f_50690_ ? modelCubeWarpedNylium.m_213637_(blockState, facing, RANDOM) : quads;
      } else {
         return modelCubeWarpedNylium.m_213637_(blockState, facing, RANDOM);
      }
   }

   private static List getFaceQuadsDirt(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
      Block blockTop = getBlockAt(blockPos, Direction.UP, blockAccess);
      return blockTop == Blocks.f_152481_ && betterDirtPath && getBlockAt(blockPos, facing, blockAccess) == Blocks.f_152481_
         ? modelCubeDirtPath.m_213637_(blockState, facing, RANDOM)
         : quads;
   }

   private static List getFaceQuadsGrass(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos, Direction facing, List quads) {
      Block blockUp = blockAccess.m_8055_(blockPos.m_7494_()).m_60734_();
      boolean snowy = blockUp == Blocks.f_50127_ || blockUp == Blocks.f_50125_;
      if (Config.isBetterGrassFancy()) {
         if (snowy) {
            if (betterGrassSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.f_50125_) {
               return modelCubeSnow.m_213637_(blockState, facing, RANDOM);
            }
         } else if (betterGrass && getBlockAt(blockPos.m_7495_(), facing, blockAccess) == Blocks.f_50440_) {
            return modelCubeGrass.m_213637_(blockState, facing, RANDOM);
         }
      } else if (snowy) {
         if (betterGrassSnow) {
            return modelCubeSnow.m_213637_(blockState, facing, RANDOM);
         }
      } else if (betterGrass) {
         return modelCubeGrass.m_213637_(blockState, facing, RANDOM);
      }

      return quads;
   }

   private static Block getBlockAt(BlockPos blockPos, Direction facing, BlockGetter blockAccess) {
      BlockPos pos = blockPos.m_121945_(facing);
      return blockAccess.m_8055_(pos).m_60734_();
   }

   private static boolean getBoolean(Properties props, String key, boolean def) {
      String str = props.getProperty(key);
      return str == null ? def : Boolean.parseBoolean(str);
   }
}
