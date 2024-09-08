package net.optifine.config;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import net.minecraft.src.C_1121_;
import net.minecraft.src.C_1353_;
import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_197_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_2097_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_4705_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5250_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;
import net.optifine.ConnectedProperties;
import net.optifine.util.BiomeUtils;
import net.optifine.util.BlockUtils;
import net.optifine.util.EntityTypeUtils;
import net.optifine.util.ItemUtils;

public class ConnectedParser {
   private String context = null;
   public static final MatchProfession[] PROFESSIONS_INVALID = new MatchProfession[0];
   public static final C_1353_[] DYE_COLORS_INVALID = new C_1353_[0];
   private static Map<C_5265_, BiomeId> MAP_BIOMES_COMPACT = null;
   private static final INameGetter<Enum> NAME_GETTER_ENUM = new INameGetter<Enum>() {
      public String getName(Enum en) {
         return en.name();
      }
   };
   private static final INameGetter<C_1353_> NAME_GETTER_DYE_COLOR = new INameGetter<C_1353_>() {
      public String getName(C_1353_ col) {
         return col.m_7912_();
      }
   };
   private static final Pattern PATTERN_RANGE_SEPARATOR = Pattern.compile("(\\d|\\))-(\\d|\\()");

   public ConnectedParser(String context) {
      this.context = context;
   }

   public String parseName(String path) {
      String str = path;
      int pos = path.lastIndexOf(47);
      if (pos >= 0) {
         str = path.substring(pos + 1);
      }

      int pos2 = str.lastIndexOf(46);
      if (pos2 >= 0) {
         str = str.substring(0, pos2);
      }

      return str;
   }

   public String parseBasePath(String path) {
      int pos = path.lastIndexOf(47);
      return pos < 0 ? "" : path.substring(0, pos);
   }

   public MatchBlock[] parseMatchBlocks(String propMatchBlocks) {
      if (propMatchBlocks == null) {
         return null;
      } else {
         List list = new ArrayList();
         String[] blockStrs = Config.tokenize(propMatchBlocks, " ");

         for (int i = 0; i < blockStrs.length; i++) {
            String blockStr = blockStrs[i];
            MatchBlock[] mbs = this.parseMatchBlock(blockStr);
            if (mbs != null) {
               list.addAll(Arrays.asList(mbs));
            }
         }

         return (MatchBlock[])list.toArray(new MatchBlock[list.size()]);
      }
   }

   public C_2064_ parseBlockState(String str, C_2064_ def) {
      MatchBlock[] mbs = this.parseMatchBlock(str);
      if (mbs == null) {
         return def;
      } else if (mbs.length != 1) {
         return def;
      } else {
         MatchBlock mb = mbs[0];
         int blockId = mb.getBlockId();
         C_1706_ block = (C_1706_)C_256712_.f_256975_.m_7942_(blockId);
         return block.m_49966_();
      }
   }

   public MatchBlock[] parseMatchBlock(String blockStr) {
      if (blockStr == null) {
         return null;
      } else {
         blockStr = blockStr.trim();
         if (blockStr.length() <= 0) {
            return null;
         } else {
            String[] parts = Config.tokenize(blockStr, ":");
            String domain = "minecraft";
            int blockIndex = 0;
            byte var16;
            if (parts.length > 1 && this.isFullBlockName(parts)) {
               domain = parts[0];
               var16 = 1;
            } else {
               domain = "minecraft";
               var16 = 0;
            }

            String blockPart = parts[var16];
            String[] params = (String[])Arrays.copyOfRange(parts, var16 + 1, parts.length);
            C_1706_[] blocks = this.parseBlockPart(domain, blockPart);
            if (blocks == null) {
               return null;
            } else {
               MatchBlock[] datas = new MatchBlock[blocks.length];

               for (int i = 0; i < blocks.length; i++) {
                  C_1706_ block = blocks[i];
                  int blockId = C_256712_.f_256975_.a(block);
                  int[] metadatas = null;
                  if (params.length > 0) {
                     metadatas = this.parseBlockMetadatas(block, params);
                     if (metadatas == null) {
                        return null;
                     }
                  }

                  MatchBlock bd = new MatchBlock(blockId, metadatas);
                  datas[i] = bd;
               }

               return datas;
            }
         }
      }
   }

   public boolean isFullBlockName(String[] parts) {
      if (parts.length <= 1) {
         return false;
      } else {
         String part1 = parts[1];
         return part1.length() < 1 ? false : !part1.contains("=");
      }
   }

   public boolean startsWithDigit(String str) {
      if (str == null) {
         return false;
      } else if (str.length() < 1) {
         return false;
      } else {
         char ch = str.charAt(0);
         return Character.isDigit(ch);
      }
   }

   public C_1706_[] parseBlockPart(String domain, String blockPart) {
      String fullName = domain + ":" + blockPart;
      C_5265_ fullLoc = this.makeResourceLocation(fullName);
      if (fullLoc == null) {
         return null;
      } else {
         C_1706_ block = BlockUtils.getBlock(fullLoc);
         if (block == null) {
            this.warn("Block not found for name: " + fullName);
            return null;
         } else {
            return new C_1706_[]{block};
         }
      }
   }

   private C_5265_ makeResourceLocation(String str) {
      try {
         return new C_5265_(str);
      } catch (C_5250_ var3) {
         this.warn("Invalid resource location: " + var3.getMessage());
         return null;
      }
   }

   private C_5265_ makeResourceLocation(String namespace, String path) {
      try {
         return new C_5265_(namespace, path);
      } catch (C_5250_ var4) {
         this.warn("Invalid resource location: " + var4.getMessage());
         return null;
      }
   }

   public int[] parseBlockMetadatas(C_1706_ block, String[] params) {
      if (params.length <= 0) {
         return null;
      } else {
         C_2064_ stateDefault = block.m_49966_();
         Collection properties = stateDefault.B();
         Map<C_2097_, List<Comparable>> mapPropValues = new HashMap();

         for (int i = 0; i < params.length; i++) {
            String param = params[i];
            if (param.length() > 0) {
               String[] parts = Config.tokenize(param, "=");
               if (parts.length != 2) {
                  this.warn("Invalid block property: " + param);
                  return null;
               }

               String key = parts[0];
               String valStr = parts[1];
               C_2097_ prop = ConnectedProperties.getProperty(key, properties);
               if (prop == null) {
                  this.warn("Property not found: " + key + ", block: " + block);
                  return null;
               }

               List<Comparable> list = (List<Comparable>)mapPropValues.get(key);
               if (list == null) {
                  list = new ArrayList();
                  mapPropValues.put(prop, list);
               }

               String[] vals = Config.tokenize(valStr, ",");

               for (int v = 0; v < vals.length; v++) {
                  String val = vals[v];
                  Comparable propVal = parsePropertyValue(prop, val);
                  if (propVal == null) {
                     this.warn("Property value not found: " + val + ", property: " + key + ", block: " + block);
                     return null;
                  }

                  list.add(propVal);
               }
            }
         }

         if (mapPropValues.isEmpty()) {
            return null;
         } else {
            List<Integer> listMetadatas = new ArrayList();
            int metaCount = BlockUtils.getMetadataCount(block);

            for (int md = 0; md < metaCount; md++) {
               try {
                  C_2064_ bs = BlockUtils.getBlockState(block, md);
                  if (this.matchState(bs, mapPropValues)) {
                     listMetadatas.add(md);
                  }
               } catch (IllegalArgumentException var17) {
               }
            }

            if (listMetadatas.size() == metaCount) {
               return null;
            } else {
               int[] metadatas = new int[listMetadatas.size()];

               for (int ix = 0; ix < metadatas.length; ix++) {
                  metadatas[ix] = (Integer)listMetadatas.get(ix);
               }

               return metadatas;
            }
         }
      }
   }

   public static Comparable parsePropertyValue(C_2097_ prop, String valStr) {
      Class valueClass = prop.m_61709_();
      Comparable valueObj = parseValue(valStr, valueClass);
      if (valueObj == null) {
         Collection propertyValues = prop.m_6908_();
         valueObj = getPropertyValue(valStr, propertyValues);
      }

      return valueObj;
   }

   public static Comparable getPropertyValue(String value, Collection propertyValues) {
      for (Comparable obj : propertyValues) {
         if (getValueName(obj).equals(value)) {
            return obj;
         }
      }

      return null;
   }

   private static Object getValueName(Comparable obj) {
      return obj instanceof C_197_ iss ? iss.m_7912_() : obj.toString();
   }

   public static Comparable parseValue(String str, Class cls) {
      if (cls == String.class) {
         return str;
      } else if (cls == Boolean.class) {
         return Boolean.valueOf(str);
      } else if (cls == Float.class) {
         return Float.valueOf(str);
      } else if (cls == Double.class) {
         return Double.valueOf(str);
      } else if (cls == Integer.class) {
         return Integer.valueOf(str);
      } else {
         return cls == Long.class ? Long.valueOf(str) : null;
      }
   }

   public boolean matchState(C_2064_ bs, Map<C_2097_, List<Comparable>> mapPropValues) {
      for (C_2097_ prop : mapPropValues.keySet()) {
         List<Comparable> vals = (List<Comparable>)mapPropValues.get(prop);
         Comparable bsVal = bs.c(prop);
         if (bsVal == null) {
            return false;
         }

         if (!vals.contains(bsVal)) {
            return false;
         }
      }

      return true;
   }

   public BiomeId[] parseBiomes(String str) {
      if (str == null) {
         return null;
      } else {
         str = str.trim();
         boolean negative = false;
         if (str.startsWith("!")) {
            negative = true;
            str = str.substring(1);
         }

         String[] biomeNames = Config.tokenize(str, " ");
         List<BiomeId> list = new ArrayList();

         for (int i = 0; i < biomeNames.length; i++) {
            String biomeName = biomeNames[i];
            BiomeId biome = this.getBiomeId(biomeName);
            if (biome == null) {
               this.warn("Biome not found: " + biomeName);
            } else {
               list.add(biome);
            }
         }

         if (negative) {
            Set<C_5265_> allBiomes = new HashSet(BiomeUtils.getLocations());

            for (BiomeId bi : list) {
               allBiomes.remove(bi.getResourceLocation());
            }

            list = BiomeUtils.getBiomeIds(allBiomes);
         }

         return (BiomeId[])list.toArray(new BiomeId[list.size()]);
      }
   }

   public BiomeId getBiomeId(String biomeName) {
      biomeName = biomeName.toLowerCase();
      C_5265_ biomeLoc = this.makeResourceLocation(biomeName);
      if (biomeLoc != null) {
         BiomeId biome = BiomeUtils.getBiomeId(biomeLoc);
         if (biome != null) {
            return biome;
         }
      }

      String biomeNameCompact = biomeName.replace(" ", "").replace("_", "");
      C_5265_ biomeLocCompact = this.makeResourceLocation(biomeNameCompact);
      if (MAP_BIOMES_COMPACT == null) {
         MAP_BIOMES_COMPACT = new HashMap();

         for (C_5265_ loc : BiomeUtils.getLocations()) {
            BiomeId biomeCompact = BiomeUtils.getBiomeId(loc);
            if (biomeCompact != null) {
               String pathCompact = loc.m_135815_().replace(" ", "").replace("_", "").toLowerCase();
               C_5265_ locCompact = this.makeResourceLocation(loc.m_135827_(), pathCompact);
               if (locCompact != null) {
                  MAP_BIOMES_COMPACT.put(locCompact, biomeCompact);
               }
            }
         }
      }

      BiomeId biome = (BiomeId)MAP_BIOMES_COMPACT.get(biomeLocCompact);
      return biome != null ? biome : null;
   }

   public int parseInt(String str, int defVal) {
      if (str == null) {
         return defVal;
      } else {
         str = str.trim();
         int num = Config.parseInt(str, -1);
         if (num < 0) {
            this.warn("Invalid number: " + str);
            return defVal;
         } else {
            return num;
         }
      }
   }

   public int parseIntNeg(String str, int defVal) {
      if (str == null) {
         return defVal;
      } else {
         str = str.trim();
         int num = Config.parseInt(str, Integer.MIN_VALUE);
         if (num == Integer.MIN_VALUE) {
            this.warn("Invalid number: " + str);
            return defVal;
         } else {
            return num;
         }
      }
   }

   public int[] parseIntList(String str) {
      if (str == null) {
         return null;
      } else {
         List<Integer> list = new ArrayList();
         String[] intStrs = Config.tokenize(str, " ,");

         for (int i = 0; i < intStrs.length; i++) {
            String intStr = intStrs[i];
            if (intStr.contains("-")) {
               String[] subStrs = Config.tokenize(intStr, "-");
               if (subStrs.length != 2) {
                  this.warn("Invalid interval: " + intStr + ", when parsing: " + str);
               } else {
                  int min = Config.parseInt(subStrs[0], -1);
                  int max = Config.parseInt(subStrs[1], -1);
                  if (min >= 0 && max >= 0 && min <= max) {
                     for (int n = min; n <= max; n++) {
                        list.add(n);
                     }
                  } else {
                     this.warn("Invalid interval: " + intStr + ", when parsing: " + str);
                  }
               }
            } else {
               int val = Config.parseInt(intStr, -1);
               if (val < 0) {
                  this.warn("Invalid number: " + intStr + ", when parsing: " + str);
               } else {
                  list.add(val);
               }
            }
         }

         int[] ints = new int[list.size()];

         for (int ix = 0; ix < ints.length; ix++) {
            ints[ix] = (Integer)list.get(ix);
         }

         return ints;
      }
   }

   public boolean[] parseFaces(String str, boolean[] defVal) {
      if (str == null) {
         return defVal;
      } else {
         EnumSet setFaces = EnumSet.allOf(C_4687_.class);
         String[] faceStrs = Config.tokenize(str, " ,");

         for (int i = 0; i < faceStrs.length; i++) {
            String faceStr = faceStrs[i];
            if (faceStr.equals("sides")) {
               setFaces.add(C_4687_.NORTH);
               setFaces.add(C_4687_.SOUTH);
               setFaces.add(C_4687_.WEST);
               setFaces.add(C_4687_.EAST);
            } else if (faceStr.equals("all")) {
               setFaces.addAll(Arrays.asList(C_4687_.f_122346_));
            } else {
               C_4687_ face = this.parseFace(faceStr);
               if (face != null) {
                  setFaces.add(face);
               }
            }
         }

         boolean[] faces = new boolean[C_4687_.f_122346_.length];

         for (int ix = 0; ix < faces.length; ix++) {
            faces[ix] = setFaces.contains(C_4687_.f_122346_[ix]);
         }

         return faces;
      }
   }

   public C_4687_ parseFace(String str) {
      str = str.toLowerCase();
      if (str.equals("bottom") || str.equals("down")) {
         return C_4687_.DOWN;
      } else if (str.equals("top") || str.equals("up")) {
         return C_4687_.UP;
      } else if (str.equals("north")) {
         return C_4687_.NORTH;
      } else if (str.equals("south")) {
         return C_4687_.SOUTH;
      } else if (str.equals("east")) {
         return C_4687_.EAST;
      } else if (str.equals("west")) {
         return C_4687_.WEST;
      } else {
         Config.warn("Unknown face: " + str);
         return null;
      }
   }

   public void dbg(String str) {
      Config.dbg(this.context + ": " + str);
   }

   public void warn(String str) {
      Config.warn(this.context + ": " + str);
   }

   public RangeListInt parseRangeListInt(String str) {
      if (str == null) {
         return null;
      } else {
         RangeListInt list = new RangeListInt();
         String[] parts = Config.tokenize(str, " ,");

         for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            RangeInt ri = this.parseRangeInt(part);
            if (ri == null) {
               return null;
            }

            list.addRange(ri);
         }

         return list;
      }
   }

   public RangeListInt parseRangeListIntNeg(String str) {
      if (str == null) {
         return null;
      } else {
         RangeListInt list = new RangeListInt();
         String[] parts = Config.tokenize(str, " ,");

         for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            RangeInt ri = this.parseRangeIntNeg(part);
            if (ri == null) {
               return null;
            }

            list.addRange(ri);
         }

         return list;
      }
   }

   private RangeInt parseRangeInt(String str) {
      if (str == null) {
         return null;
      } else if (str.indexOf(45) >= 0) {
         String[] parts = Config.tokenize(str, "-");
         if (parts.length != 2) {
            this.warn("Invalid range: " + str);
            return null;
         } else {
            int min = Config.parseInt(parts[0], -1);
            int max = Config.parseInt(parts[1], -1);
            if (min >= 0 && max >= 0) {
               return new RangeInt(min, max);
            } else {
               this.warn("Invalid range: " + str);
               return null;
            }
         }
      } else {
         int val = Config.parseInt(str, -1);
         if (val < 0) {
            this.warn("Invalid integer: " + str);
            return null;
         } else {
            return new RangeInt(val, val);
         }
      }
   }

   private RangeInt parseRangeIntNeg(String str) {
      if (str == null) {
         return null;
      } else if (str.indexOf("=") >= 0) {
         this.warn("Invalid range: " + str);
         return null;
      } else {
         String strEq = PATTERN_RANGE_SEPARATOR.matcher(str).replaceAll("$1=$2");
         if (strEq.indexOf(61) >= 0) {
            String[] parts = Config.tokenize(strEq, "=");
            if (parts.length != 2) {
               this.warn("Invalid range: " + str);
               return null;
            } else {
               int min = Config.parseInt(stripBrackets(parts[0]), Integer.MIN_VALUE);
               int max = Config.parseInt(stripBrackets(parts[1]), Integer.MIN_VALUE);
               if (min != Integer.MIN_VALUE && max != Integer.MIN_VALUE) {
                  return new RangeInt(min, max);
               } else {
                  this.warn("Invalid range: " + str);
                  return null;
               }
            }
         } else {
            int val = Config.parseInt(stripBrackets(str), Integer.MIN_VALUE);
            if (val == Integer.MIN_VALUE) {
               this.warn("Invalid integer: " + str);
               return null;
            } else {
               return new RangeInt(val, val);
            }
         }
      }
   }

   private static String stripBrackets(String str) {
      return str.startsWith("(") && str.endsWith(")") ? str.substring(1, str.length() - 1) : str;
   }

   public boolean parseBoolean(String str, boolean defVal) {
      if (str == null) {
         return defVal;
      } else {
         String strLower = str.toLowerCase().trim();
         if (strLower.equals("true")) {
            return true;
         } else if (strLower.equals("false")) {
            return false;
         } else {
            this.warn("Invalid boolean: " + str);
            return defVal;
         }
      }
   }

   public Boolean parseBooleanObject(String str) {
      if (str == null) {
         return null;
      } else {
         String strLower = str.toLowerCase().trim();
         if (strLower.equals("true")) {
            return Boolean.TRUE;
         } else if (strLower.equals("false")) {
            return Boolean.FALSE;
         } else {
            this.warn("Invalid boolean: " + str);
            return null;
         }
      }
   }

   public static int parseColor(String str, int defVal) {
      if (str == null) {
         return defVal;
      } else {
         str = str.trim();

         try {
            return Integer.parseInt(str, 16) & 16777215;
         } catch (NumberFormatException var3) {
            return defVal;
         }
      }
   }

   public static int parseColor4(String str, int defVal) {
      if (str == null) {
         return defVal;
      } else {
         str = str.trim();

         try {
            return (int)(Long.parseLong(str, 16) & -1L);
         } catch (NumberFormatException var3) {
            return defVal;
         }
      }
   }

   public C_4168_ parseBlockRenderLayer(String str, C_4168_ def) {
      if (str == null) {
         return def;
      } else {
         str = str.toLowerCase().trim();
         C_4168_[] layers = C_4168_.CHUNK_RENDER_TYPES;

         for (int i = 0; i < layers.length; i++) {
            C_4168_ layer = layers[i];
            if (str.equals(layer.getName().toLowerCase())) {
               return layer;
            }
         }

         return def;
      }
   }

   public <T> T parseObject(String str, T[] objs, INameGetter nameGetter, String property) {
      if (str == null) {
         return null;
      } else {
         String strLower = str.toLowerCase().trim();

         for (int i = 0; i < objs.length; i++) {
            T obj = objs[i];
            String name = nameGetter.getName(obj);
            if (name != null && name.toLowerCase().equals(strLower)) {
               return obj;
            }
         }

         this.warn("Invalid " + property + ": " + str);
         return null;
      }
   }

   public <T> T[] parseObjects(String str, T[] objs, INameGetter nameGetter, String property, T[] errValue) {
      if (str == null) {
         return null;
      } else {
         str = str.toLowerCase().trim();
         String[] parts = Config.tokenize(str, " ");
         T[] arr = (T[])Array.newInstance(objs.getClass().getComponentType(), parts.length);

         for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            T obj = this.parseObject(part, objs, nameGetter, property);
            if (obj == null) {
               return errValue;
            }

            arr[i] = obj;
         }

         return arr;
      }
   }

   public Enum parseEnum(String str, Enum[] enums, String property) {
      return this.parseObject(str, enums, NAME_GETTER_ENUM, property);
   }

   public Enum[] parseEnums(String str, Enum[] enums, String property, Enum[] errValue) {
      return this.parseObjects(str, enums, NAME_GETTER_ENUM, property, errValue);
   }

   public C_1353_[] parseDyeColors(String str, String property, C_1353_[] errValue) {
      return this.parseObjects(str, C_1353_.values(), NAME_GETTER_DYE_COLOR, property, errValue);
   }

   public Weather[] parseWeather(String str, String property, Weather[] errValue) {
      return this.parseObjects(str, Weather.values(), NAME_GETTER_ENUM, property, errValue);
   }

   public NbtTagValue[] parseNbtTagValues(Properties props, String prefix) {
      List<NbtTagValue> listNbts = new ArrayList();

      for (String key : props.keySet()) {
         if (key.startsWith(prefix)) {
            String val = (String)props.get(key);
            String id = key.substring(prefix.length());
            NbtTagValue nbt = new NbtTagValue(id, val);
            listNbts.add(nbt);
         }
      }

      return listNbts.isEmpty() ? null : (NbtTagValue[])listNbts.toArray(new NbtTagValue[listNbts.size()]);
   }

   public NbtTagValue parseNbtTagValue(String path, String value) {
      return path != null && value != null ? new NbtTagValue(path, value) : null;
   }

   public MatchProfession[] parseProfessions(String profStr) {
      if (profStr == null) {
         return null;
      } else {
         List<MatchProfession> list = new ArrayList();
         String[] tokens = Config.tokenize(profStr, " ");

         for (int i = 0; i < tokens.length; i++) {
            String str = tokens[i];
            MatchProfession prof = this.parseProfession(str);
            if (prof == null) {
               this.warn("Invalid profession: " + str);
               return PROFESSIONS_INVALID;
            }

            list.add(prof);
         }

         return list.isEmpty() ? null : (MatchProfession[])list.toArray(new MatchProfession[list.size()]);
      }
   }

   private MatchProfession parseProfession(String str) {
      String strProf = str;
      String strLevels = null;
      int pos = str.lastIndexOf(58);
      if (pos >= 0) {
         String part1 = str.substring(0, pos);
         String part2 = str.substring(pos + 1);
         if (part2.isEmpty() || part2.matches("[0-9].*")) {
            strProf = part1;
            strLevels = part2;
         }
      }

      C_1121_ prof = this.parseVillagerProfession(strProf);
      if (prof == null) {
         return null;
      } else {
         int[] levels = this.parseIntList(strLevels);
         return new MatchProfession(prof, levels);
      }
   }

   private C_1121_ parseVillagerProfession(String str) {
      if (str == null) {
         return null;
      } else {
         str = str.toLowerCase();
         C_5265_ loc = this.makeResourceLocation(str);
         if (loc == null) {
            return null;
         } else {
            C_4705_<C_1121_> registry = C_256712_.f_256735_;
            return !registry.m_7804_(loc) ? null : (C_1121_)registry.m_7745_(loc);
         }
      }
   }

   public int[] parseItems(String str) {
      str = str.trim();
      Set<Integer> setIds = new TreeSet();
      String[] tokens = Config.tokenize(str, " ");

      for (int i = 0; i < tokens.length; i++) {
         String token = tokens[i];
         C_5265_ loc = this.makeResourceLocation(token);
         if (loc != null) {
            C_1381_ item = ItemUtils.getItem(loc);
            if (item == null) {
               this.warn("Item not found: " + token);
            } else {
               int id = ItemUtils.getId(item);
               if (id < 0) {
                  this.warn("Item has no ID: " + item + ", name: " + token);
               } else {
                  setIds.add(new Integer(id));
               }
            }
         }
      }

      Integer[] integers = (Integer[])setIds.toArray(new Integer[setIds.size()]);
      return Config.toPrimitive(integers);
   }

   public int[] parseEntities(String str) {
      str = str.trim();
      Set<Integer> setIds = new TreeSet();
      String[] tokens = Config.tokenize(str, " ");

      for (int i = 0; i < tokens.length; i++) {
         String token = tokens[i];
         C_5265_ loc = this.makeResourceLocation(token);
         if (loc != null) {
            C_513_ type = EntityTypeUtils.getEntityType(loc);
            if (type == null) {
               this.warn("Entity not found: " + token);
            } else {
               int id = C_256712_.f_256780_.a(type);
               if (id < 0) {
                  this.warn("Entity has no ID: " + type + ", name: " + token);
               } else {
                  setIds.add(new Integer(id));
               }
            }
         }
      }

      Integer[] integers = (Integer[])setIds.toArray(new Integer[setIds.size()]);
      return Config.toPrimitive(integers);
   }
}
