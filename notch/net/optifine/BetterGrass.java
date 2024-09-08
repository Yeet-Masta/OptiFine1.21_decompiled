package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import net.minecraft.src.C_141203_;
import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_1801_;
import net.minecraft.src.C_1841_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_4473_;
import net.minecraft.src.C_4484_;
import net.minecraft.src.C_4486_;
import net.minecraft.src.C_4528_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_5265_;
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
   private static C_4486_ spriteGrass = null;
   private static C_4486_ spriteGrassSide = null;
   private static C_4486_ spriteDirtPath = null;
   private static C_4486_ spriteDirtPathSide = null;
   private static C_4486_ spriteMycelium = null;
   private static C_4486_ spritePodzol = null;
   private static C_4486_ spriteCrimsonNylium = null;
   private static C_4486_ spriteWarpedNylium = null;
   private static C_4486_ spriteSnow = null;
   private static boolean spritesLoaded = false;
   private static C_4528_ modelCubeGrass = null;
   private static C_4528_ modelDirtPath = null;
   private static C_4528_ modelCubeDirtPath = null;
   private static C_4528_ modelCubeMycelium = null;
   private static C_4528_ modelCubePodzol = null;
   private static C_4528_ modelCubeCrimsonNylium = null;
   private static C_4528_ modelCubeWarpedNylium = null;
   private static C_4528_ modelCubeSnow = null;
   private static boolean modelsLoaded = false;
   private static final String TEXTURE_GRASS_DEFAULT = "block/grass_block_top";
   private static final String TEXTURE_GRASS_SIDE_DEFAULT = "block/grass_block_side";
   private static final String TEXTURE_DIRT_PATH_DEFAULT = "block/dirt_path_top";
   private static final String TEXTURE_DIRT_PATH_SIDE_DEFAULT = "block/dirt_path_side";
   private static final String TEXTURE_MYCELIUM_DEFAULT = "block/mycelium_top";
   private static final String TEXTURE_PODZOL_DEFAULT = "block/podzol_top";
   private static final String TEXTURE_CRIMSON_NYLIUM = "block/crimson_nylium";
   private static final String TEXTURE_WARPED_NYLIUM = "block/warped_nylium";
   private static final String TEXTURE_SNOW_DEFAULT = "block/snow";
   private static final C_212974_ RANDOM = RandomUtils.makeThreadSafeRandomSource(0);

   public static void updateIcons(C_4484_ textureMap) {
      spritesLoaded = false;
      modelsLoaded = false;
      loadProperties(textureMap);
   }

   public static void update() {
      if (spritesLoaded) {
         modelCubeGrass = BlockModelUtils.makeModelCube(spriteGrass, 0);
         if (grassMultilayer) {
            C_4528_ modelCubeGrassSide = BlockModelUtils.makeModelCube(spriteGrassSide, -1);
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

   private static void loadProperties(C_4484_ textureMap) {
      betterGrass = true;
      betterDirtPath = true;
      betterMycelium = true;
      betterPodzol = true;
      betterCrimsonNylium = true;
      betterWarpedNylium = true;
      betterGrassSnow = true;
      betterMyceliumSnow = true;
      betterPodzolSnow = true;
      spriteGrass = textureMap.registerSprite(new C_5265_("block/grass_block_top"));
      spriteGrassSide = textureMap.registerSprite(new C_5265_("block/grass_block_side"));
      spriteDirtPath = textureMap.registerSprite(new C_5265_("block/dirt_path_top"));
      spriteDirtPathSide = textureMap.registerSprite(new C_5265_("block/dirt_path_side"));
      spriteMycelium = textureMap.registerSprite(new C_5265_("block/mycelium_top"));
      spritePodzol = textureMap.registerSprite(new C_5265_("block/podzol_top"));
      spriteCrimsonNylium = textureMap.registerSprite(new C_5265_("block/crimson_nylium"));
      spriteWarpedNylium = textureMap.registerSprite(new C_5265_("block/warped_nylium"));
      spriteSnow = textureMap.registerSprite(new C_5265_("block/snow"));
      spritesLoaded = true;
      String name = "optifine/bettergrass.properties";

      try {
         C_5265_ locFile = new C_5265_(name);
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

   public static void refreshIcons(C_4484_ textureMap) {
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

   private static C_4486_ getSprite(C_4484_ textureMap, C_5265_ loc) {
      C_4486_ sprite = textureMap.m_118316_(loc);
      if (sprite == null || C_4473_.isMisingSprite(sprite)) {
         Config.warn("Missing BetterGrass sprite: " + loc);
      }

      return sprite;
   }

   private static C_4486_ registerSprite(Properties props, String key, String textureDefault, C_4484_ textureMap) {
      String texture = props.getProperty(key);
      if (texture == null) {
         texture = textureDefault;
      }

      C_5265_ locPng = new C_5265_("textures/" + texture + ".png");
      if (!Config.hasResource(locPng)) {
         Config.warn("BetterGrass texture not found: " + locPng);
         texture = textureDefault;
      }

      C_5265_ locSprite = new C_5265_(texture);
      return textureMap.registerSprite(locSprite);
   }

   public static List getFaceQuads(C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4687_ facing, List quads) {
      if (facing == C_4687_.UP || facing == C_4687_.DOWN) {
         return quads;
      } else if (!modelsLoaded) {
         return quads;
      } else {
         C_1706_ block = blockState.b();
         if (block instanceof C_1841_) {
            return getFaceQuadsMycelium(blockAccess, blockState, blockPos, facing, quads);
         } else if (block instanceof C_141203_) {
            return getFaceQuadsDirtPath(blockAccess, blockState, blockPos, facing, quads);
         } else if (block == C_1710_.f_50599_) {
            return getFaceQuadsPodzol(blockAccess, blockState, blockPos, facing, quads);
         } else if (block == C_1710_.f_50699_) {
            return getFaceQuadsCrimsonNylium(blockAccess, blockState, blockPos, facing, quads);
         } else if (block == C_1710_.f_50690_) {
            return getFaceQuadsWarpedNylium(blockAccess, blockState, blockPos, facing, quads);
         } else if (block == C_1710_.f_50493_) {
            return getFaceQuadsDirt(blockAccess, blockState, blockPos, facing, quads);
         } else {
            return block instanceof C_1801_ ? getFaceQuadsGrass(blockAccess, blockState, blockPos, facing, quads) : quads;
         }
      }
   }

   private static List getFaceQuadsMycelium(C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4687_ facing, List quads) {
      C_1706_ blockUp = blockAccess.m_8055_(blockPos.m_7494_()).b();
      boolean snowy = blockUp == C_1710_.f_50127_ || blockUp == C_1710_.f_50125_;
      if (Config.isBetterGrassFancy()) {
         if (snowy) {
            if (betterMyceliumSnow && getBlockAt(blockPos, facing, blockAccess) == C_1710_.f_50125_) {
               return modelCubeSnow.m_213637_(blockState, facing, RANDOM);
            }
         } else if (betterMycelium && getBlockAt(blockPos.m_7495_(), facing, blockAccess) == C_1710_.f_50195_) {
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

   private static List getFaceQuadsDirtPath(C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4687_ facing, List quads) {
      if (!betterDirtPath) {
         return quads;
      } else if (Config.isBetterGrassFancy()) {
         return getBlockAt(blockPos.m_7495_(), facing, blockAccess) == C_1710_.f_152481_ ? modelDirtPath.m_213637_(blockState, facing, RANDOM) : quads;
      } else {
         return modelDirtPath.m_213637_(blockState, facing, RANDOM);
      }
   }

   private static List getFaceQuadsPodzol(C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4687_ facing, List quads) {
      C_1706_ blockTop = getBlockAt(blockPos, C_4687_.UP, blockAccess);
      boolean snowy = blockTop == C_1710_.f_50127_ || blockTop == C_1710_.f_50125_;
      if (Config.isBetterGrassFancy()) {
         if (snowy) {
            if (betterPodzolSnow && getBlockAt(blockPos, facing, blockAccess) == C_1710_.f_50125_) {
               return modelCubeSnow.m_213637_(blockState, facing, RANDOM);
            }
         } else if (betterPodzol) {
            C_4675_ posSide = blockPos.m_7495_().m_121945_(facing);
            C_2064_ stateSide = blockAccess.m_8055_(posSide);
            if (stateSide.b() == C_1710_.f_50599_) {
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

   private static List getFaceQuadsCrimsonNylium(C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4687_ facing, List quads) {
      if (!betterCrimsonNylium) {
         return quads;
      } else if (Config.isBetterGrassFancy()) {
         return getBlockAt(blockPos.m_7495_(), facing, blockAccess) == C_1710_.f_50699_ ? modelCubeCrimsonNylium.m_213637_(blockState, facing, RANDOM) : quads;
      } else {
         return modelCubeCrimsonNylium.m_213637_(blockState, facing, RANDOM);
      }
   }

   private static List getFaceQuadsWarpedNylium(C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4687_ facing, List quads) {
      if (!betterWarpedNylium) {
         return quads;
      } else if (Config.isBetterGrassFancy()) {
         return getBlockAt(blockPos.m_7495_(), facing, blockAccess) == C_1710_.f_50690_ ? modelCubeWarpedNylium.m_213637_(blockState, facing, RANDOM) : quads;
      } else {
         return modelCubeWarpedNylium.m_213637_(blockState, facing, RANDOM);
      }
   }

   private static List getFaceQuadsDirt(C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4687_ facing, List quads) {
      C_1706_ blockTop = getBlockAt(blockPos, C_4687_.UP, blockAccess);
      return blockTop == C_1710_.f_152481_ && betterDirtPath && getBlockAt(blockPos, facing, blockAccess) == C_1710_.f_152481_
         ? modelCubeDirtPath.m_213637_(blockState, facing, RANDOM)
         : quads;
   }

   private static List getFaceQuadsGrass(C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos, C_4687_ facing, List quads) {
      C_1706_ blockUp = blockAccess.m_8055_(blockPos.m_7494_()).b();
      boolean snowy = blockUp == C_1710_.f_50127_ || blockUp == C_1710_.f_50125_;
      if (Config.isBetterGrassFancy()) {
         if (snowy) {
            if (betterGrassSnow && getBlockAt(blockPos, facing, blockAccess) == C_1710_.f_50125_) {
               return modelCubeSnow.m_213637_(blockState, facing, RANDOM);
            }
         } else if (betterGrass && getBlockAt(blockPos.m_7495_(), facing, blockAccess) == C_1710_.f_50440_) {
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

   private static C_1706_ getBlockAt(C_4675_ blockPos, C_4687_ facing, C_1559_ blockAccess) {
      C_4675_ pos = blockPos.m_121945_(facing);
      return blockAccess.m_8055_(pos).b();
   }

   private static boolean getBoolean(Properties props, String key, boolean def) {
      String str = props.getProperty(key);
      return str == null ? def : Boolean.parseBoolean(str);
   }
}
