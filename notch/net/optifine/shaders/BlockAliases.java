package net.optifine.shaders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1833_;
import net.minecraft.src.C_1879_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_2097_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;
import net.optifine.ConnectedProperties;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.util.BlockUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class BlockAliases {
   private static BlockAlias[][] blockAliases = null;
   private static boolean hasAliasMetadata = false;
   private static PropertiesOrdered blockLayerPropertes = null;
   private static boolean updateOnResourcesReloaded;
   private static List<List<BlockAlias>> legacyAliases;

   public static int getAliasBlockId(C_2064_ blockState) {
      int blockId = blockState.getBlockId();
      int metadata = blockState.getMetadata();
      BlockAlias alias = getBlockAlias(blockId, metadata);
      return alias != null ? alias.getAliasBlockId() : -1;
   }

   public static boolean hasAliasMetadata() {
      return hasAliasMetadata;
   }

   public static int getAliasMetadata(C_2064_ blockState) {
      if (!hasAliasMetadata) {
         return 0;
      } else {
         int blockId = blockState.getBlockId();
         int metadata = blockState.getMetadata();
         BlockAlias alias = getBlockAlias(blockId, metadata);
         return alias != null ? alias.getAliasMetadata() : 0;
      }
   }

   public static BlockAlias getBlockAlias(int blockId, int metadata) {
      if (blockAliases == null) {
         return null;
      } else if (blockId >= 0 && blockId < blockAliases.length) {
         BlockAlias[] aliases = blockAliases[blockId];
         if (aliases == null) {
            return null;
         } else {
            for (int i = 0; i < aliases.length; i++) {
               BlockAlias ba = aliases[i];
               if (ba.matches(blockId, metadata)) {
                  return ba;
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   public static BlockAlias[] getBlockAliases(int blockId) {
      if (blockAliases == null) {
         return null;
      } else {
         return blockId >= 0 && blockId < blockAliases.length ? blockAliases[blockId] : null;
      }
   }

   public static void resourcesReloaded() {
      if (updateOnResourcesReloaded) {
         updateOnResourcesReloaded = false;
         update(Shaders.getShaderPack());
      }
   }

   public static void update(IShaderPack shaderPack) {
      reset();
      if (shaderPack != null) {
         if (!(shaderPack instanceof ShaderPackNone)) {
            if (Reflector.ModList.exists() && C_3391_.m_91087_().m_91098_() == null) {
               Config.dbg("[Shaders] Delayed loading of block mappings after resources are loaded");
               updateOnResourcesReloaded = true;
            } else {
               List<List<BlockAlias>> listBlockAliases = new ArrayList();
               String path = "/shaders/block.properties";
               InputStream in = shaderPack.getResourceAsStream(path);
               if (in != null) {
                  loadBlockAliases(in, path, listBlockAliases);
               }

               loadModBlockAliases(listBlockAliases);
               if (listBlockAliases.size() <= 0) {
                  listBlockAliases = getLegacyAliases();
                  hasAliasMetadata = true;
               }

               blockAliases = toBlockAliasArrays(listBlockAliases);
            }
         }
      }
   }

   private static void loadModBlockAliases(List<List<BlockAlias>> listBlockAliases) {
      String[] modIds = ReflectorForge.getForgeModIds();

      for (int i = 0; i < modIds.length; i++) {
         String modId = modIds[i];

         try {
            C_5265_ loc = new C_5265_(modId, "shaders/block.properties");
            InputStream in = Config.getResourceStream(loc);
            loadBlockAliases(in, loc.toString(), listBlockAliases);
         } catch (IOException var6) {
         }
      }
   }

   private static void loadBlockAliases(InputStream in, String path, List<List<BlockAlias>> listBlockAliases) {
      if (in != null) {
         try {
            in = MacroProcessor.process(in, path, true);
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            Config.dbg("[Shaders] Parsing block mappings: " + path);
            ConnectedParser cp = new ConnectedParser("Shaders");

            for (String key : props.keySet()) {
               String val = props.getProperty(key);
               if (key.startsWith("layer.")) {
                  if (blockLayerPropertes == null) {
                     blockLayerPropertes = new PropertiesOrdered();
                  }

                  blockLayerPropertes.put(key, val);
               } else {
                  String prefix = "block.";
                  if (!key.startsWith(prefix)) {
                     Config.warn("[Shaders] Invalid block ID: " + key);
                  } else {
                     String blockIdStr = StrUtils.removePrefix(key, prefix);
                     int blockId = Config.parseInt(blockIdStr, -1);
                     if (blockId < 0) {
                        Config.warn("[Shaders] Invalid block ID: " + key);
                     } else {
                        MatchBlock[] matchBlocks = cp.parseMatchBlocks(val);
                        if (matchBlocks != null && matchBlocks.length >= 1) {
                           BlockAlias ba = new BlockAlias(blockId, matchBlocks);
                           addToList(listBlockAliases, ba);
                        } else {
                           Config.warn("[Shaders] Invalid block ID mapping: " + key + "=" + val);
                        }
                     }
                  }
               }
            }
         } catch (IOException var14) {
            Config.warn("[Shaders] Error reading: " + path);
         }
      }
   }

   private static void addToList(List<List<BlockAlias>> blocksAliases, BlockAlias ba) {
      int[] blockIds = ba.getMatchBlockIds();

      for (int i = 0; i < blockIds.length; i++) {
         int blockId = blockIds[i];

         while (blockId >= blocksAliases.size()) {
            blocksAliases.add(null);
         }

         List<BlockAlias> blockAliases = (List<BlockAlias>)blocksAliases.get(blockId);
         if (blockAliases == null) {
            blockAliases = new ArrayList();
            blocksAliases.set(blockId, blockAliases);
         }

         BlockAlias baBlock = new BlockAlias(ba.getAliasBlockId(), ba.getMatchBlocks(blockId));
         blockAliases.add(baBlock);
      }
   }

   private static BlockAlias[][] toBlockAliasArrays(List<List<BlockAlias>> listBlocksAliases) {
      BlockAlias[][] bas = new BlockAlias[listBlocksAliases.size()][];

      for (int i = 0; i < bas.length; i++) {
         List<BlockAlias> listBlockAliases = (List<BlockAlias>)listBlocksAliases.get(i);
         if (listBlockAliases != null) {
            bas[i] = (BlockAlias[])listBlockAliases.toArray(new BlockAlias[listBlockAliases.size()]);
         }
      }

      return bas;
   }

   private static List<List<BlockAlias>> getLegacyAliases() {
      if (legacyAliases == null) {
         legacyAliases = makeLegacyAliases();
      }

      return legacyAliases;
   }

   private static List<List<BlockAlias>> makeLegacyAliases() {
      try {
         String path = "flattening_ids.txt";
         Config.dbg("Using legacy block aliases: " + path);
         List<List<BlockAlias>> listAliases = new ArrayList();
         List<String> listLines = new ArrayList();
         int countAliases = 0;
         InputStream in = Config.getOptiFineResourceStream("/" + path);
         if (in == null) {
            return listAliases;
         } else {
            String[] lines = Config.readLines(in);

            for (int i = 0; i < lines.length; i++) {
               int lineNum = i + 1;
               String line = lines[i];
               if (line.trim().length() > 0) {
                  listLines.add(line);
                  if (!line.startsWith("#")) {
                     if (line.startsWith("alias")) {
                        String[] parts = Config.tokenize(line, " ");
                        String blockAliasStr = parts[1];
                        String blockStr = parts[2];
                        String prefix = "{Name:'" + blockStr + "'";
                        List<String> blockLines = (List<String>)listLines.stream().filter(s -> s.startsWith(prefix)).collect(Collectors.toList());
                        if (blockLines.size() <= 0) {
                           Config.warn("Block not processed: " + line);
                        } else {
                           for (String lineBlock : blockLines) {
                              String prefixNew = "{Name:'" + blockAliasStr + "'";
                              String lineNew = lineBlock.replace(prefix, prefixNew);
                              listLines.add(lineNew);
                              addLegacyAlias(lineNew, lineNum, listAliases);
                              countAliases++;
                           }
                        }
                     } else {
                        addLegacyAlias(line, lineNum, listAliases);
                        countAliases++;
                     }
                  }
               }
            }

            Config.dbg("Legacy block aliases: " + countAliases);
            return listAliases;
         }
      } catch (IOException var18) {
         Config.warn("Error loading legacy block aliases: " + var18.getClass().getName() + ": " + var18.getMessage());
         return new ArrayList();
      }
   }

   private static void addLegacyAlias(String line, int lineNum, List<List<BlockAlias>> listAliases) {
      String[] parts = Config.tokenize(line, " ");
      if (parts.length != 4) {
         Config.warn("Invalid flattening line: " + line);
      } else {
         String partNew = parts[0];
         String partOld = parts[1];
         int blockIdOld = Config.parseInt(parts[2], Integer.MIN_VALUE);
         int metadataOld = Config.parseInt(parts[3], Integer.MIN_VALUE);
         if (blockIdOld >= 0 && metadataOld >= 0) {
            try {
               JsonParser jp = new JsonParser();
               JsonObject jo = jp.parse(partNew).getAsJsonObject();
               String name = jo.get("Name").getAsString();
               C_5265_ loc = new C_5265_(name);
               C_1706_ block = BlockUtils.getBlock(loc);
               if (block == null) {
                  Config.warn("Invalid block name (" + lineNum + "): " + name);
                  return;
               }

               C_2064_ blockState = block.m_49966_();
               Collection<C_2097_<?>> stateProperties = blockState.B();
               Map<C_2097_, Comparable> mapProperties = new LinkedHashMap();
               JsonObject properties = (JsonObject)jo.get("Properties");
               if (properties != null) {
                  for (Entry<String, JsonElement> entry : properties.entrySet()) {
                     String key = (String)entry.getKey();
                     String value = ((JsonElement)entry.getValue()).getAsString();
                     C_2097_ prop = ConnectedProperties.getProperty(key, stateProperties);
                     if (prop == null) {
                        Config.warn("Invalid property (" + lineNum + "): " + key);
                     } else {
                        Comparable propVal = ConnectedParser.parsePropertyValue(prop, value);
                        if (propVal == null) {
                           Config.warn("Invalid property value (" + lineNum + "): " + value);
                        } else {
                           mapProperties.put(prop, propVal);
                        }
                     }
                  }
               }

               int blockId = blockState.getBlockId();

               while (listAliases.size() <= blockId) {
                  listAliases.add(null);
               }

               List<BlockAlias> las = (List<BlockAlias>)listAliases.get(blockId);
               if (las == null) {
                  las = new ArrayList(BlockUtils.getMetadataCount(block));
                  listAliases.set(blockId, las);
               }

               MatchBlock mb = getMatchBlock(blockState.b(), blockState.getBlockId(), mapProperties);
               addBlockAlias(las, blockIdOld, metadataOld, mb);
            } catch (Exception var24) {
               Config.warn("Error parsing: " + line);
            }
         } else {
            Config.warn("Invalid blockID or metadata (" + lineNum + "): " + blockIdOld + ":" + metadataOld);
         }
      }
   }

   private static void addBlockAlias(List<BlockAlias> listBlockAliases, int aliasBlockId, int aliasMetadata, MatchBlock matchBlock) {
      for (BlockAlias ba : listBlockAliases) {
         if (ba.getAliasBlockId() == aliasBlockId && ba.getAliasMetadata() == aliasMetadata) {
            MatchBlock[] mbs = ba.getMatchBlocks();

            for (int i = 0; i < mbs.length; i++) {
               MatchBlock mb = mbs[i];
               if (mb.getBlockId() == matchBlock.getBlockId()) {
                  mb.addMetadatas(matchBlock.getMetadatas());
                  return;
               }
            }
         }
      }

      BlockAlias bax = new BlockAlias(aliasBlockId, aliasMetadata, new MatchBlock[]{matchBlock});
      listBlockAliases.add(bax);
   }

   private static MatchBlock getMatchBlock(C_1706_ block, int blockId, Map<C_2097_, Comparable> mapProperties) {
      List<C_2064_> matchingStates = new ArrayList();
      Collection<C_2097_> props = mapProperties.keySet();

      for (C_2064_ bs : BlockUtils.getBlockStates(block)) {
         boolean match = true;

         for (C_2097_ prop : props) {
            if (!bs.b(prop)) {
               match = false;
               break;
            }

            Comparable val1 = (Comparable)mapProperties.get(prop);
            Comparable val2 = bs.c(prop);
            if (!val1.equals(val2)) {
               match = false;
               break;
            }
         }

         if (match) {
            matchingStates.add(bs);
         }
      }

      Set<Integer> setMetadatas = new LinkedHashSet();

      for (C_2064_ bs : matchingStates) {
         setMetadatas.add(bs.getMetadata());
      }

      Integer[] metadatas = (Integer[])setMetadatas.toArray(new Integer[setMetadatas.size()]);
      int[] mds = Config.toPrimitive(metadatas);
      return new MatchBlock(blockId, mds);
   }

   private static void checkLegacyAliases() {
      for (C_5265_ loc : C_256712_.f_256975_.f()) {
         C_1706_ block = (C_1706_)C_256712_.f_256975_.m_7745_(loc);
         int blockId = block.m_49966_().getBlockId();
         BlockAlias[] bas = getBlockAliases(blockId);
         if (bas == null) {
            Config.warn("Block has no alias: " + block);
         } else {
            for (C_2064_ state : BlockUtils.getBlockStates(block)) {
               int metadata = state.getMetadata();
               BlockAlias ba = getBlockAlias(blockId, metadata);
               if (ba == null) {
                  Config.warn("State has no alias: " + state);
               }
            }
         }
      }
   }

   public static PropertiesOrdered getBlockLayerPropertes() {
      return blockLayerPropertes;
   }

   public static void reset() {
      blockAliases = null;
      hasAliasMetadata = false;
      blockLayerPropertes = null;
   }

   public static int getRenderType(C_2064_ blockState) {
      if (hasAliasMetadata) {
         C_1706_ block = blockState.b();
         if (block instanceof C_1833_) {
            return 1;
         } else {
            C_1879_ brt = blockState.l();
            return brt != C_1879_.ENTITYBLOCK_ANIMATED && brt != C_1879_.MODEL ? brt.ordinal() : brt.ordinal() + 1;
         }
      } else {
         return blockState.l().ordinal();
      }
   }
}
