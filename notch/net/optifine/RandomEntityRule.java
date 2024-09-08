package net.optifine;

import java.util.Properties;
import net.minecraft.src.C_1030_;
import net.minecraft.src.C_1059_;
import net.minecraft.src.C_1118_;
import net.minecraft.src.C_1119_;
import net.minecraft.src.C_1121_;
import net.minecraft.src.C_1353_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1984_;
import net.minecraft.src.C_1991_;
import net.minecraft.src.C_2021_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_819_;
import net.minecraft.src.C_896_;
import net.minecraft.src.C_922_;
import net.minecraft.src.C_930_;
import net.optifine.config.BiomeId;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.config.MatchProfession;
import net.optifine.config.Matches;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import net.optifine.config.Weather;
import net.optifine.util.ArrayUtils;
import net.optifine.util.MathUtils;

public class RandomEntityRule<T> {
   private String pathProps = null;
   private C_5265_ baseResLoc = null;
   private int index;
   private RandomEntityContext<T> context;
   private int[] textures = null;
   private T[] resources = null;
   private int[] weights = null;
   private BiomeId[] biomes = null;
   private RangeListInt heights = null;
   private RangeListInt healthRange = null;
   private boolean healthPercent = false;
   private NbtTagValue nbtName = null;
   public int[] sumWeights = null;
   public int sumAllWeights = 1;
   private MatchProfession[] professions = null;
   private C_1353_[] colors = null;
   private Boolean baby = null;
   private RangeListInt moonPhases = null;
   private RangeListInt dayTimes = null;
   private Weather[] weatherList = null;
   private RangeListInt sizes = null;
   public NbtTagValue[] nbtTagValues = null;
   public MatchBlock[] blocks = null;

   public RandomEntityRule(Properties props, String pathProps, C_5265_ baseResLoc, int index, String valTextures, RandomEntityContext<T> context) {
      this.pathProps = pathProps;
      this.baseResLoc = baseResLoc;
      this.index = index;
      this.context = context;
      ConnectedParser cp = context.getConnectedParser();
      this.textures = cp.parseIntList(valTextures);
      this.weights = cp.parseIntList(props.getProperty("weights." + index));
      this.biomes = cp.parseBiomes(props.getProperty("biomes." + index));
      this.heights = cp.parseRangeListIntNeg(props.getProperty("heights." + index));
      if (this.heights == null) {
         this.heights = this.parseMinMaxHeight(props, index);
      }

      String healthStr = props.getProperty("health." + index);
      if (healthStr != null) {
         this.healthPercent = healthStr.contains("%");
         healthStr = healthStr.replace("%", "");
         this.healthRange = cp.parseRangeListInt(healthStr);
      }

      this.nbtName = cp.parseNbtTagValue("name", props.getProperty("name." + index));
      this.professions = cp.parseProfessions(props.getProperty("professions." + index));
      this.colors = cp.parseDyeColors(props.getProperty("colors." + index), "color", ConnectedParser.DYE_COLORS_INVALID);
      if (this.colors == null) {
         this.colors = cp.parseDyeColors(props.getProperty("collarColors." + index), "collar color", ConnectedParser.DYE_COLORS_INVALID);
      }

      this.baby = cp.parseBooleanObject(props.getProperty("baby." + index));
      this.moonPhases = cp.parseRangeListInt(props.getProperty("moonPhase." + index));
      this.dayTimes = cp.parseRangeListInt(props.getProperty("dayTime." + index));
      this.weatherList = cp.parseWeather(props.getProperty("weather." + index), "weather." + index, null);
      this.sizes = cp.parseRangeListInt(props.getProperty("sizes." + index));
      this.nbtTagValues = cp.parseNbtTagValues(props, "nbt." + index + ".");
      this.blocks = cp.parseMatchBlocks(props.getProperty("blocks." + index));
   }

   public int getIndex() {
      return this.index;
   }

   private RangeListInt parseMinMaxHeight(Properties props, int index) {
      String minHeightStr = props.getProperty("minHeight." + index);
      String maxHeightStr = props.getProperty("maxHeight." + index);
      if (minHeightStr == null && maxHeightStr == null) {
         return null;
      } else {
         int minHeight = 0;
         if (minHeightStr != null) {
            minHeight = Config.parseInt(minHeightStr, -1);
            if (minHeight < 0) {
               Config.warn("Invalid minHeight: " + minHeightStr);
               return null;
            }
         }

         int maxHeight = 256;
         if (maxHeightStr != null) {
            maxHeight = Config.parseInt(maxHeightStr, -1);
            if (maxHeight < 0) {
               Config.warn("Invalid maxHeight: " + maxHeightStr);
               return null;
            }
         }

         if (maxHeight < 0) {
            Config.warn("Invalid minHeight, maxHeight: " + minHeightStr + ", " + maxHeightStr);
            return null;
         } else {
            RangeListInt list = new RangeListInt();
            list.addRange(new RangeInt(minHeight, maxHeight));
            return list;
         }
      }
   }

   public boolean isValid(String path) {
      String resourceName = this.context.getResourceName();
      String resourceNamePlural = this.context.getResourceNamePlural();
      if (this.textures != null && this.textures.length != 0) {
         this.resources = (T[])(new Object[this.textures.length]);

         for (int i = 0; i < this.textures.length; i++) {
            int index = this.textures[i];
            T res = (T)this.context.makeResource(this.baseResLoc, index);
            if (res == null) {
               return false;
            }

            this.resources[i] = res;
         }

         if (this.weights != null) {
            if (this.weights.length > this.resources.length) {
               Config.warn("More weights defined than " + resourceNamePlural + ", trimming weights: " + path);
               int[] weights2 = new int[this.resources.length];
               System.arraycopy(this.weights, 0, weights2, 0, weights2.length);
               this.weights = weights2;
            }

            if (this.weights.length < this.resources.length) {
               Config.warn("Less weights defined than " + resourceNamePlural + ", expanding weights: " + path);
               int[] weights2 = new int[this.resources.length];
               System.arraycopy(this.weights, 0, weights2, 0, this.weights.length);
               int avgWeight = MathUtils.getAverage(this.weights);

               for (int i = this.weights.length; i < weights2.length; i++) {
                  weights2[i] = avgWeight;
               }

               this.weights = weights2;
            }

            this.sumWeights = new int[this.weights.length];
            int sum = 0;

            for (int i = 0; i < this.weights.length; i++) {
               if (this.weights[i] < 0) {
                  Config.warn("Invalid weight: " + this.weights[i]);
                  return false;
               }

               sum += this.weights[i];
               this.sumWeights[i] = sum;
            }

            this.sumAllWeights = sum;
            if (this.sumAllWeights <= 0) {
               Config.warn("Invalid sum of all weights: " + sum);
               this.sumAllWeights = 1;
            }
         }

         if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
            Config.warn("Invalid professions or careers: " + path);
            return false;
         } else if (this.colors == ConnectedParser.DYE_COLORS_INVALID) {
            Config.warn("Invalid colors: " + path);
            return false;
         } else {
            return true;
         }
      } else {
         Config.warn("Invalid " + resourceNamePlural + " for rule: " + this.index);
         return false;
      }
   }

   public boolean matches(IRandomEntity randomEntity) {
      if (this.biomes != null && !Matches.biome(randomEntity.getSpawnBiome(), this.biomes)) {
         return false;
      } else {
         if (this.heights != null) {
            C_4675_ pos = randomEntity.getSpawnPosition();
            if (pos != null && !this.heights.isInRange(pos.v())) {
               return false;
            }
         }

         if (this.healthRange != null) {
            int health = randomEntity.getHealth();
            if (this.healthPercent) {
               int healthMax = randomEntity.getMaxHealth();
               if (healthMax > 0) {
                  health = (int)((double)(health * 100) / (double)healthMax);
               }
            }

            if (!this.healthRange.isInRange(health)) {
               return false;
            }
         }

         if (this.nbtName != null) {
            String name = randomEntity.getName();
            if (!this.nbtName.matchesValue(name)) {
               return false;
            }
         }

         if (this.professions != null && randomEntity instanceof RandomEntity rme && rme.getEntity() instanceof C_1118_ entityVillager) {
            C_1119_ vd = entityVillager.m_7141_();
            C_1121_ vp = vd.m_35571_();
            int level = vd.m_35576_();
            if (!MatchProfession.matchesOne(vp, level, this.professions)) {
               return false;
            }
         }

         if (this.colors != null) {
            C_1353_ col = randomEntity.getColor();
            if (col != null && !Config.equalsOne(col, this.colors)) {
               return false;
            }
         }

         if (this.baby != null
            && randomEntity instanceof RandomEntity rmex
            && rmex.getEntity() instanceof C_524_ livingEntity
            && livingEntity.m_6162_() != this.baby) {
            return false;
         }

         if (this.moonPhases != null) {
            C_1596_ world = Config.getMinecraft().f_91073_;
            if (world != null) {
               int moonPhase = world.ar();
               if (!this.moonPhases.isInRange(moonPhase)) {
                  return false;
               }
            }
         }

         if (this.dayTimes != null) {
            C_1596_ world = Config.getMinecraft().f_91073_;
            if (world != null) {
               int dayTime = (int)(world.m_46468_() % 24000L);
               if (!this.dayTimes.isInRange(dayTime)) {
                  return false;
               }
            }
         }

         if (this.weatherList != null) {
            C_1596_ world = Config.getMinecraft().f_91073_;
            if (world != null) {
               Weather weather = Weather.getWeather(world, 0.0F);
               if (!ArrayUtils.contains(this.weatherList, weather)) {
                  return false;
               }
            }
         }

         if (this.sizes != null && randomEntity instanceof RandomEntity rmex) {
            C_507_ entity = rmex.getEntity();
            int size = this.getEntitySize(entity);
            if (size >= 0 && !this.sizes.isInRange(size)) {
               return false;
            }
         }

         if (this.nbtTagValues != null) {
            C_4917_ nbt = randomEntity.getNbtTag();
            if (nbt != null) {
               for (int i = 0; i < this.nbtTagValues.length; i++) {
                  NbtTagValue ntv = this.nbtTagValues[i];
                  if (!ntv.matches(nbt)) {
                     return false;
                  }
               }
            }
         }

         if (this.blocks != null) {
            C_2064_ blockState = randomEntity.getBlockState();
            if (blockState != null && !Matches.block(blockState, this.blocks)) {
               return false;
            }
         }

         return true;
      }
   }

   public static C_1353_ getEntityColor(C_507_ entity) {
      if (entity instanceof C_922_ entityWolf) {
         return !entityWolf.s() ? null : entityWolf.m_30428_();
      } else if (entity instanceof C_819_ entityCat) {
         return !entityCat.s() ? null : entityCat.m_28166_();
      } else if (entity instanceof C_896_ entitySheep) {
         return entitySheep.m_29874_();
      } else {
         return entity instanceof C_930_ entityLlama ? entityLlama.m_30826_() : null;
      }
   }

   public static C_1353_ getBlockEntityColor(C_1991_ entity) {
      if (entity instanceof C_1984_ entityBed) {
         return entityBed.m_58731_();
      } else {
         return entity instanceof C_2021_ entityShulkerBox ? entityShulkerBox.m_59701_() : null;
      }
   }

   private int getEntitySize(C_507_ entity) {
      if (entity instanceof C_1059_ entitySlime) {
         return entitySlime.m_33632_() - 1;
      } else {
         return entity instanceof C_1030_ entityPhantom ? entityPhantom.m_33172_() : -1;
      }
   }

   public T getResource(int randomId, T resDef) {
      if (this.resources != null && this.resources.length != 0) {
         int index = this.getResourceIndex(randomId);
         return this.resources[index];
      } else {
         return resDef;
      }
   }

   private int getResourceIndex(int randomId) {
      int index = 0;
      if (this.weights == null) {
         index = randomId % this.resources.length;
      } else {
         int randWeight = randomId % this.sumAllWeights;

         for (int i = 0; i < this.sumWeights.length; i++) {
            if (this.sumWeights[i] > randWeight) {
               index = i;
               break;
            }
         }
      }

      return index;
   }

   public T[] getResources() {
      return this.resources;
   }
}
