package net.optifine.config;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.optifine.Config;
import net.optifine.util.StrUtils;
import org.apache.commons.lang3.StringEscapeUtils;

public class NbtTagValue {
   private String[] parents = null;
   private String name = null;
   private boolean negative = false;
   private boolean raw = false;
   private int type = 0;
   private String value = null;
   private Pattern valueRegex = null;
   private RangeListInt valueRange = null;
   private int valueFormat = 0;
   private static final int TYPE_INVALID = -1;
   private static final int TYPE_TEXT = 0;
   private static final int TYPE_PATTERN = 1;
   private static final int TYPE_IPATTERN = 2;
   private static final int TYPE_REGEX = 3;
   private static final int TYPE_IREGEX = 4;
   private static final int TYPE_RANGE = 5;
   private static final int TYPE_EXISTS = 6;
   private static final String PREFIX_PATTERN = "pattern:";
   private static final String PREFIX_IPATTERN = "ipattern:";
   private static final String PREFIX_REGEX = "regex:";
   private static final String PREFIX_IREGEX = "iregex:";
   private static final String PREFIX_RAW = "raw:";
   private static final String PREFIX_RANGE = "range:";
   private static final String PREFIX_EXISTS = "exists:";
   private static final int FORMAT_DEFAULT = 0;
   private static final int FORMAT_HEX_COLOR = 1;
   private static final String PREFIX_HEX_COLOR = "#";
   private static final Pattern PATTERN_HEX_COLOR = Pattern.compile("^#[0-9a-f]{6}+$");

   public NbtTagValue(String tag, String value) {
      String[] names = Config.tokenize(tag, ".");
      this.parents = (String[])Arrays.copyOfRange(names, 0, names.length - 1);
      this.name = names[names.length - 1];
      if (value.startsWith("!")) {
         this.negative = true;
         value = value.substring(1);
      }

      if (value.startsWith("raw:")) {
         this.raw = true;
         value = value.substring("raw:".length());
      }

      if (value.startsWith("pattern:")) {
         this.type = 1;
         value = value.substring("pattern:".length());
         if (value.equals("*")) {
            this.type = 6;
         }
      } else if (value.startsWith("ipattern:")) {
         this.type = 2;
         value = value.substring("ipattern:".length()).toLowerCase();
         if (value.equals("*")) {
            this.type = 6;
         }
      } else if (value.startsWith("regex:")) {
         this.type = 3;
         value = value.substring("regex:".length());
         this.valueRegex = Pattern.compile(value);
         if (value.equals(".*")) {
            this.type = 6;
         }
      } else if (value.startsWith("iregex:")) {
         this.type = 4;
         value = value.substring("iregex:".length());
         this.valueRegex = Pattern.compile(value, 2);
         if (value.equals(".*")) {
            this.type = 6;
         }
      } else if (value.startsWith("range:")) {
         this.type = 5;
         value = value.substring("range:".length());
         ConnectedParser cp = new ConnectedParser("NbtTag");
         this.valueRange = cp.parseRangeListIntNeg(value);
         if (this.valueRange == null) {
            Config.warn("Invalid range: " + value);
            this.type = -1;
            this.negative = false;
         }
      } else if (value.startsWith("exists:")) {
         this.type = 6;
         value = value.substring("exists:".length());
         Boolean valB = Config.parseBoolean(value, (Boolean)null);
         if (Config.isFalse(valB)) {
            this.negative = !this.negative;
         }

         if (valB == null) {
            Config.warn("Invalid exists: " + value);
            this.type = -1;
            this.negative = false;
         }
      } else {
         this.type = 0;
      }

      value = StringEscapeUtils.unescapeJava(value);
      if (this.type == 0 && PATTERN_HEX_COLOR.matcher(value).matches()) {
         this.valueFormat = 1;
      }

      this.value = value;
   }

   public boolean matches(CompoundTag nbt) {
      if (this.negative) {
         return !this.matchesCompound(nbt);
      } else {
         return this.matchesCompound(nbt);
      }
   }

   public boolean matchesCompound(CompoundTag nbt) {
      if (nbt == null) {
         return false;
      } else {
         Tag tagBase = nbt;

         for(int i = 0; i < this.parents.length; ++i) {
            String tag = this.parents[i];
            tagBase = getChildTag((Tag)tagBase, tag);
            if (tagBase == null) {
               return false;
            }
         }

         if (this.name.equals("*")) {
            return this.matchesAnyChild((Tag)tagBase);
         } else {
            Tag tagBase = getChildTag((Tag)tagBase, this.name);
            if (tagBase == null) {
               return false;
            } else if (this.matchesBase(tagBase)) {
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private boolean matchesAnyChild(Tag tagBase) {
      if (tagBase instanceof CompoundTag tagCompound) {
         Set nbtKeySet = tagCompound.m_128431_();
         Iterator it = nbtKeySet.iterator();

         while(it.hasNext()) {
            String key = (String)it.next();
            Tag nbtBase = tagCompound.m_128423_(key);
            if (this.matchesBase(nbtBase)) {
               return true;
            }
         }
      }

      if (tagBase instanceof ListTag tagList) {
         int count = tagList.size();

         for(int i = 0; i < count; ++i) {
            Tag nbtBase = tagList.get(i);
            if (this.matchesBase(nbtBase)) {
               return true;
            }
         }
      }

      return false;
   }

   private static Tag getChildTag(Tag tagBase, String tag) {
      if (tagBase instanceof CompoundTag tagCompound) {
         return tagCompound.m_128423_(tag);
      } else if (tagBase instanceof ListTag tagList) {
         if (tag.equals("count")) {
            return IntTag.m_128679_(tagList.size());
         } else {
            int index = Config.parseInt(tag, -1);
            return index >= 0 && index < tagList.size() ? tagList.get(index) : null;
         }
      } else {
         return null;
      }
   }

   public boolean matchesBase(Tag nbtBase) {
      if (nbtBase == null) {
         return false;
      } else {
         switch (this.type) {
            case -1:
               return false;
            case 5:
               int nbtValueInt = getNbtInt(nbtBase, Integer.MIN_VALUE);
               if (nbtValueInt != Integer.MIN_VALUE) {
                  return matchesRange(nbtValueInt, this.valueRange);
               }
            case 6:
               return true;
            default:
               String nbtValue = this.raw ? String.valueOf(nbtBase) : getNbtString(nbtBase, this.valueFormat);
               return this.matchesValue(nbtValue);
         }
      }
   }

   public boolean matchesValue(String nbtValue) {
      if (nbtValue == null) {
         return false;
      } else {
         switch (this.type) {
            case -1:
               return false;
            case 0:
               return nbtValue.equals(this.value);
            case 1:
               return matchesPattern(nbtValue, this.value);
            case 2:
               return matchesPattern(nbtValue.toLowerCase(), this.value);
            case 3:
            case 4:
               return matchesRegex(nbtValue, this.valueRegex);
            case 5:
               return matchesRange(nbtValue, this.valueRange);
            case 6:
               return true;
            default:
               throw new IllegalArgumentException("Unknown NbtTagValue type: " + this.type);
         }
      }
   }

   private static boolean matchesPattern(String str, String pattern) {
      return StrUtils.equalsMask(str, pattern, '*', '?');
   }

   private static boolean matchesRegex(String str, Pattern regex) {
      return regex.matcher(str).matches();
   }

   private static boolean matchesRange(String str, RangeListInt range) {
      if (range == null) {
         return false;
      } else {
         int valInt = Config.parseInt(str, Integer.MIN_VALUE);
         return valInt == Integer.MIN_VALUE ? false : matchesRange(valInt, range);
      }
   }

   private static boolean matchesRange(int valInt, RangeListInt range) {
      return range == null ? false : range.isInRange(valInt);
   }

   private static String getNbtString(Tag nbtBase, int format) {
      if (nbtBase == null) {
         return null;
      } else if (!(nbtBase instanceof StringTag)) {
         if (nbtBase instanceof IntTag) {
            IntTag i = (IntTag)nbtBase;
            if (format == 1) {
               String var10000 = Integer.toHexString(i.m_7047_());
               return "#" + StrUtils.fillLeft(var10000, 6, '0');
            } else {
               return Integer.toString(i.m_7047_());
            }
         } else if (nbtBase instanceof ByteTag) {
            ByteTag b = (ByteTag)nbtBase;
            return Byte.toString(b.m_7063_());
         } else if (nbtBase instanceof ShortTag) {
            ShortTag s = (ShortTag)nbtBase;
            return Short.toString(s.m_7053_());
         } else if (nbtBase instanceof LongTag) {
            LongTag l = (LongTag)nbtBase;
            return Long.toString(l.m_7046_());
         } else if (nbtBase instanceof FloatTag) {
            FloatTag f = (FloatTag)nbtBase;
            return Float.toString(f.m_7057_());
         } else if (nbtBase instanceof DoubleTag) {
            DoubleTag d = (DoubleTag)nbtBase;
            return Double.toString(d.m_7061_());
         } else {
            return nbtBase.toString();
         }
      } else {
         StringTag nbtString = (StringTag)nbtBase;
         String text = nbtString.m_7916_();
         if (text.startsWith("{") && text.endsWith("}")) {
            text = getMergedJsonText(text);
         } else if (text.startsWith("[{") && text.endsWith("}]")) {
            text = getMergedJsonText(text);
         } else if (text.startsWith("\"") && text.endsWith("\"") && text.length() > 1) {
            text = text.substring(1, text.length() - 1);
         }

         return text;
      }
   }

   private static int getNbtInt(Tag nbtBase, int defVal) {
      if (nbtBase == null) {
         return defVal;
      } else if (nbtBase instanceof IntTag) {
         IntTag i = (IntTag)nbtBase;
         return i.m_7047_();
      } else if (nbtBase instanceof ByteTag) {
         ByteTag b = (ByteTag)nbtBase;
         return b.m_7063_();
      } else if (nbtBase instanceof ShortTag) {
         ShortTag s = (ShortTag)nbtBase;
         return s.m_7053_();
      } else if (nbtBase instanceof LongTag) {
         LongTag l = (LongTag)nbtBase;
         return (int)l.m_7046_();
      } else if (nbtBase instanceof FloatTag) {
         FloatTag f = (FloatTag)nbtBase;
         return (int)f.m_7057_();
      } else if (nbtBase instanceof DoubleTag) {
         DoubleTag d = (DoubleTag)nbtBase;
         return (int)d.m_7061_();
      } else {
         return defVal;
      }
   }

   private static String getMergedJsonText(String text) {
      StringBuilder sb = new StringBuilder();
      String TOKEN_TEXT = "\"text\":\"";
      int pos = -1;

      while(true) {
         pos = text.indexOf(TOKEN_TEXT, pos + 1);
         if (pos < 0) {
            return sb.toString();
         }

         String str = parseString(text, pos + TOKEN_TEXT.length());
         if (str != null) {
            sb.append(str);
         }
      }
   }

   private static String parseString(String text, int pos) {
      StringBuilder sb = new StringBuilder();
      boolean escapeMode = false;

      for(int i = pos; i < text.length(); ++i) {
         char ch = text.charAt(i);
         if (escapeMode) {
            if (ch == 'b') {
               sb.append('\b');
            } else if (ch == 'f') {
               sb.append('\f');
            } else if (ch == 'n') {
               sb.append('\n');
            } else if (ch == 'r') {
               sb.append('\r');
            } else if (ch == 't') {
               sb.append('\t');
            } else {
               sb.append(ch);
            }

            escapeMode = false;
         } else if (ch == '\\') {
            escapeMode = true;
         } else {
            if (ch == '"') {
               break;
            }

            sb.append(ch);
         }
      }

      return sb.toString();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.parents.length; ++i) {
         String parent = this.parents[i];
         if (i > 0) {
            sb.append(".");
         }

         sb.append(parent);
      }

      if (sb.length() > 0) {
         sb.append(".");
      }

      sb.append(this.name);
      sb.append(" = ");
      sb.append(this.value);
      return sb.toString();
   }
}
