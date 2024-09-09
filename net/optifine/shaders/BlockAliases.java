package net.optifine.shaders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
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
   private static List legacyAliases;

   public static int getAliasBlockId(BlockState blockState) {
      int blockId = blockState.getBlockId();
      int metadata = blockState.getMetadata();
      BlockAlias alias = getBlockAlias(blockId, metadata);
      return alias != null ? alias.getAliasBlockId() : -1;
   }

   public static boolean hasAliasMetadata() {
      return hasAliasMetadata;
   }

   public static int getAliasMetadata(BlockState blockState) {
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
            for(int i = 0; i < aliases.length; ++i) {
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
      } else if (blockId >= 0 && blockId < blockAliases.length) {
         BlockAlias[] aliases = blockAliases[blockId];
         return aliases;
      } else {
         return null;
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
            if (Reflector.ModList.exists() && Minecraft.m_91087_().m_91098_() == null) {
               Config.dbg("[Shaders] Delayed loading of block mappings after resources are loaded");
               updateOnResourcesReloaded = true;
            } else {
               List listBlockAliases = new ArrayList();
               String path = "/shaders/block.properties";
               InputStream in = shaderPack.getResourceAsStream(path);
               if (in != null) {
                  loadBlockAliases(in, path, (List)listBlockAliases);
               }

               loadModBlockAliases((List)listBlockAliases);
               if (((List)listBlockAliases).size() <= 0) {
                  listBlockAliases = getLegacyAliases();
                  hasAliasMetadata = true;
               }

               blockAliases = toBlockAliasArrays((List)listBlockAliases);
            }
         }
      }
   }

   private static void loadModBlockAliases(List listBlockAliases) {
      String[] modIds = ReflectorForge.getForgeModIds();

      for(int i = 0; i < modIds.length; ++i) {
         String modId = modIds[i];

         try {
            ResourceLocation loc = new ResourceLocation(modId, "shaders/block.properties");
            InputStream in = Config.getResourceStream(loc);
            loadBlockAliases(in, loc.toString(), listBlockAliases);
         } catch (IOException var6) {
         }
      }

   }

   private static void loadBlockAliases(InputStream in, String path, List listBlockAliases) {
      if (in != null) {
         try {
            in = MacroProcessor.process(in, path, true);
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            Config.dbg("[Shaders] Parsing block mappings: " + path);
            ConnectedParser cp = new ConnectedParser("Shaders");
            Set keys = props.keySet();
            Iterator it = keys.iterator();

            while(true) {
               while(it.hasNext()) {
                  String key = (String)it.next();
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

               return;
            }
         } catch (IOException var14) {
            Config.warn("[Shaders] Error reading: " + path);
         }
      }
   }

   private static void addToList(List blocksAliases, BlockAlias ba) {
      int[] blockIds = ba.getMatchBlockIds();

      for(int i = 0; i < blockIds.length; ++i) {
         int blockId = blockIds[i];

         while(blockId >= blocksAliases.size()) {
            blocksAliases.add((Object)null);
         }

         List blockAliases = (List)blocksAliases.get(blockId);
         if (blockAliases == null) {
            blockAliases = new ArrayList();
            blocksAliases.set(blockId, blockAliases);
         }

         BlockAlias baBlock = new BlockAlias(ba.getAliasBlockId(), ba.getMatchBlocks(blockId));
         ((List)blockAliases).add(baBlock);
      }

   }

   private static BlockAlias[][] toBlockAliasArrays(List listBlocksAliases) {
      BlockAlias[][] bas = new BlockAlias[listBlocksAliases.size()][];

      for(int i = 0; i < bas.length; ++i) {
         List listBlockAliases = (List)listBlocksAliases.get(i);
         if (listBlockAliases != null) {
            bas[i] = (BlockAlias[])listBlockAliases.toArray(new BlockAlias[listBlockAliases.size()]);
         }
      }

      return bas;
   }

   private static List getLegacyAliases() {
      if (legacyAliases == null) {
         legacyAliases = makeLegacyAliases();
      }

      return legacyAliases;
   }

   private static List makeLegacyAliases() {
      try {
         String path = "flattening_ids.txt";
         Config.dbg("Using legacy block aliases: " + path);
         List listAliases = new ArrayList();
         List listLines = new ArrayList();
         int countAliases = 0;
         InputStream in = Config.getOptiFineResourceStream("/" + path);
         if (in == null) {
            return listAliases;
         } else {
            String[] lines = Config.readLines(in);

            for(int i = 0; i < lines.length; ++i) {
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
                        List blockLines = (List)listLines.stream().filter((s) -> {
                           return s.startsWith(prefix);
                        }).collect(Collectors.toList());
                        if (blockLines.size() <= 0) {
                           Config.warn("Block not processed: " + line);
                        } else {
                           for(Iterator var14 = blockLines.iterator(); var14.hasNext(); ++countAliases) {
                              String lineBlock = (String)var14.next();
                              String prefixNew = "{Name:'" + blockAliasStr + "'";
                              String lineNew = lineBlock.replace(prefix, prefixNew);
                              listLines.add(lineNew);
                              addLegacyAlias(lineNew, lineNum, listAliases);
                           }
                        }
                     } else {
                        addLegacyAlias(line, lineNum, listAliases);
                        ++countAliases;
                     }
                  }
               }
            }

            Config.dbg("Legacy block aliases: " + countAliases);
            return listAliases;
         }
      } catch (IOException var18) {
         String var10000 = var18.getClass().getName();
         Config.warn("Error loading legacy block aliases: " + var10000 + ": " + var18.getMessage());
         return new ArrayList();
      }
   }

   private static void addLegacyAlias(String line, int lineNum, List listAliases) {
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
               ResourceLocation loc = new ResourceLocation(name);
               Block block = BlockUtils.getBlock(loc);
               if (block == null) {
                  Config.warn("Invalid block name (" + lineNum + "): " + name);
                  return;
               }

               BlockState blockState = block.m_49966_();
               Collection stateProperties = blockState.m_61147_();
               Map mapProperties = new LinkedHashMap();
               JsonObject properties = (JsonObject)jo.get("Properties");
               if (properties != null) {
                  Set entries = properties.entrySet();
                  Iterator var18 = entries.iterator();

                  while(var18.hasNext()) {
                     Map.Entry entry = (Map.Entry)var18.next();
                     String key = (String)entry.getKey();
                     String value = ((JsonElement)entry.getValue()).getAsString();
                     Property prop = ConnectedProperties.getProperty(key, stateProperties);
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

               while(listAliases.size() <= blockId) {
                  listAliases.add((Object)null);
               }

               List las = (List)listAliases.get(blockId);
               if (las == null) {
                  las = new ArrayList(BlockUtils.getMetadataCount(block));
                  listAliases.set(blockId, las);
               }

               MatchBlock mb = getMatchBlock(blockState.m_60734_(), blockState.getBlockId(), mapProperties);
               addBlockAlias((List)las, blockIdOld, metadataOld, mb);
            } catch (Exception var24) {
               Config.warn("Error parsing: " + line);
            }

         } else {
            Config.warn("Invalid blockID or metadata (" + lineNum + "): " + blockIdOld + ":" + metadataOld);
         }
      }
   }

   private static void addBlockAlias(List listBlockAliases, int aliasBlockId, int aliasMetadata, MatchBlock matchBlock) {
      Iterator var4 = listBlockAliases.iterator();

      while(true) {
         BlockAlias ba;
         do {
            do {
               if (!var4.hasNext()) {
                  BlockAlias ba = new BlockAlias(aliasBlockId, aliasMetadata, new MatchBlock[]{matchBlock});
                  listBlockAliases.add(ba);
                  return;
               }

               ba = (BlockAlias)var4.next();
            } while(ba.getAliasBlockId() != aliasBlockId);
         } while(ba.getAliasMetadata() != aliasMetadata);

         MatchBlock[] mbs = ba.getMatchBlocks();

         for(int i = 0; i < mbs.length; ++i) {
            MatchBlock mb = mbs[i];
            if (mb.getBlockId() == matchBlock.getBlockId()) {
               mb.addMetadatas(matchBlock.getMetadatas());
               return;
            }
         }
      }
   }

   private static MatchBlock getMatchBlock(Block block, int blockId, Map mapProperties) {
      List matchingStates = new ArrayList();
      Collection props = mapProperties.keySet();
      List states = BlockUtils.getBlockStates(block);
      Iterator var6 = states.iterator();

      while(var6.hasNext()) {
         BlockState bs = (BlockState)var6.next();
         boolean match = true;
         Iterator var9 = props.iterator();

         while(var9.hasNext()) {
            Property prop = (Property)var9.next();
            if (!bs.m_61138_(prop)) {
               match = false;
               break;
            }

            Comparable val1 = (Comparable)mapProperties.get(prop);
            Comparable val2 = bs.m_61143_(prop);
            if (!val1.equals(val2)) {
               match = false;
               break;
            }
         }

         if (match) {
            matchingStates.add(bs);
         }
      }

      Set setMetadatas = new LinkedHashSet();
      Iterator var14 = matchingStates.iterator();

      while(var14.hasNext()) {
         BlockState bs = (BlockState)var14.next();
         setMetadatas.add(bs.getMetadata());
      }

      Integer[] metadatas = (Integer[])setMetadatas.toArray(new Integer[setMetadatas.size()]);
      int[] mds = Config.toPrimitive(metadatas);
      MatchBlock mb = new MatchBlock(blockId, mds);
      return mb;
   }

   private static void checkLegacyAliases() {
      Set locs = BuiltInRegistries.f_256975_.m_6566_();
      Iterator var1 = locs.iterator();

      while(true) {
         while(var1.hasNext()) {
            ResourceLocation loc = (ResourceLocation)var1.next();
            Block block = (Block)BuiltInRegistries.f_256975_.m_7745_(loc);
            int blockId = block.m_49966_().getBlockId();
            BlockAlias[] bas = getBlockAliases(blockId);
            if (bas == null) {
               Config.warn("Block has no alias: " + String.valueOf(block));
            } else {
               List states = BlockUtils.getBlockStates(block);
               Iterator var7 = states.iterator();

               while(var7.hasNext()) {
                  BlockState state = (BlockState)var7.next();
                  int metadata = state.getMetadata();
                  BlockAlias ba = getBlockAlias(blockId, metadata);
                  if (ba == null) {
                     Config.warn("State has no alias: " + String.valueOf(state));
                  }
               }
            }
         }

         return;
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

   public static int getRenderType(BlockState blockState) {
      if (hasAliasMetadata) {
         Block block = blockState.m_60734_();
         if (block instanceof LiquidBlock) {
            return 1;
         } else {
            RenderShape brt = blockState.m_60799_();
            return brt != RenderShape.ENTITYBLOCK_ANIMATED && brt != RenderShape.MODEL ? brt.ordinal() : brt.ordinal() + 1;
         }
      } else {
         return blockState.m_60799_().ordinal();
      }
   }
}
