package net.minecraft.src;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Type;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;

public final class C_5265_ implements Comparable<C_5265_> {
   public static final Codec<C_5265_> f_135803_ = Codec.STRING.comapFlatMap(C_5265_::m_135837_, C_5265_::toString).stable();
   public static final C_313866_<ByteBuf, C_5265_> f_314488_ = C_313613_.f_315450_.m_323038_(C_5265_::m_338530_, C_5265_::toString);
   public static final SimpleCommandExceptionType f_135806_ = new SimpleCommandExceptionType(C_4996_.m_237115_("argument.id.invalid"));
   public static final char f_179907_ = ':';
   public static final String f_179908_ = "minecraft";
   public static final String f_179909_ = "realms";
   private final String f_135804_;
   private final String f_135805_;
   private final boolean defaultNamespace;

   public C_5265_(String str) {
      this(getNamespace(str), getPath(str));
   }

   private static String getNamespace(String str) {
      int pos = str.indexOf(58);
      return pos >= 0 ? str.substring(0, pos) : "minecraft";
   }

   private static String getPath(String str) {
      int pos = str.indexOf(58);
      return pos >= 0 ? str.substring(pos + 1) : str;
   }

   public C_5265_(String namespaceIn, String pathIn) {
      this.f_135804_ = namespaceIn;
      this.f_135805_ = pathIn;
      this.defaultNamespace = "minecraft".equals(namespaceIn);
   }

   private static C_5265_ m_339033_(String namespaceIn, String pathIn) {
      return new C_5265_(m_245413_(namespaceIn, pathIn), m_245185_(namespaceIn, pathIn));
   }

   public static C_5265_ m_339182_(String namespaceIn, String pathIn) {
      return m_339033_(namespaceIn, pathIn);
   }

   public static C_5265_ m_338530_(String stringIn) {
      return m_339830_(stringIn, ':');
   }

   public static C_5265_ m_340282_(String pathIn) {
      return new C_5265_("minecraft", m_245185_("minecraft", pathIn));
   }

   @Nullable
   public static C_5265_ m_135820_(String string) {
      return m_338964_(string, ':');
   }

   @Nullable
   public static C_5265_ m_214293_(String namespaceIn, String pathIn) {
      return m_135843_(namespaceIn) && m_135841_(pathIn) ? new C_5265_(namespaceIn, pathIn) : null;
   }

   public static C_5265_ m_339830_(String stringIn, char separatorIn) {
      int i = stringIn.indexOf(separatorIn);
      if (i >= 0) {
         String s = stringIn.substring(i + 1);
         if (i != 0) {
            String s1 = stringIn.substring(0, i);
            return m_339033_(s1, s);
         } else {
            return m_340282_(s);
         }
      } else {
         return m_340282_(stringIn);
      }
   }

   @Nullable
   public static C_5265_ m_338964_(String stringIn, char separatorIn) {
      int i = stringIn.indexOf(separatorIn);
      if (i >= 0) {
         String s = stringIn.substring(i + 1);
         if (!m_135841_(s)) {
            return null;
         } else if (i != 0) {
            String s1 = stringIn.substring(0, i);
            return m_135843_(s1) ? new C_5265_(s1, s) : null;
         } else {
            return new C_5265_("minecraft", s);
         }
      } else {
         return m_135841_(stringIn) ? new C_5265_("minecraft", stringIn) : null;
      }
   }

   public static DataResult<C_5265_> m_135837_(String stringIn) {
      try {
         return DataResult.success(m_338530_(stringIn));
      } catch (C_5250_ var2) {
         return DataResult.error(() -> "Not a valid resource location: " + stringIn + " " + var2.getMessage());
      }
   }

   public String m_135815_() {
      return this.f_135805_;
   }

   public String m_135827_() {
      return this.f_135804_;
   }

   public C_5265_ m_247449_(String pathIn) {
      return new C_5265_(this.f_135804_, m_245185_(this.f_135804_, pathIn));
   }

   public C_5265_ m_247266_(UnaryOperator<String> pathIn) {
      return this.m_247449_((String)pathIn.apply(this.f_135805_));
   }

   public C_5265_ m_246208_(String prefixIn) {
      return this.m_247449_(prefixIn + this.f_135805_);
   }

   public C_5265_ m_266382_(String suffixIn) {
      return this.m_247449_(this.f_135805_ + suffixIn);
   }

   public String toString() {
      return this.f_135804_ + ":" + this.f_135805_;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         return p_equals_1_ instanceof C_5265_ resourcelocation
            ? this.f_135804_.equals(resourcelocation.f_135804_) && this.f_135805_.equals(resourcelocation.f_135805_)
            : false;
      }
   }

   public int hashCode() {
      return 31 * this.f_135804_.hashCode() + this.f_135805_.hashCode();
   }

   public int compareTo(C_5265_ p_compareTo_1_) {
      int i = this.f_135805_.compareTo(p_compareTo_1_.f_135805_);
      if (i == 0) {
         i = this.f_135804_.compareTo(p_compareTo_1_.f_135804_);
      }

      return i;
   }

   public String m_179910_() {
      return this.toString().replace('/', '_').replace(':', '_');
   }

   public String m_214298_() {
      return this.f_135804_ + "." + this.f_135805_;
   }

   public String m_214299_() {
      return this.f_135804_.equals("minecraft") ? this.f_135805_ : this.m_214298_();
   }

   public String m_214296_(String prefixIn) {
      return prefixIn + "." + this.m_214298_();
   }

   public String m_269108_(String sectionIn, String variantIn) {
      return sectionIn + "." + this.m_214298_() + "." + variantIn;
   }

   private static String m_324283_(StringReader readerIn) {
      int i = readerIn.getCursor();

      while (readerIn.canRead() && m_135816_(readerIn.peek())) {
         readerIn.skip();
      }

      return readerIn.getString().substring(i, readerIn.getCursor());
   }

   public static C_5265_ m_135818_(StringReader reader) throws CommandSyntaxException {
      int i = reader.getCursor();
      String s = m_324283_(reader);

      try {
         return m_338530_(s);
      } catch (C_5250_ var4) {
         reader.setCursor(i);
         throw f_135806_.createWithContext(reader);
      }
   }

   public static C_5265_ m_320588_(StringReader readerIn) throws CommandSyntaxException {
      int i = readerIn.getCursor();
      String s = m_324283_(readerIn);
      if (s.isEmpty()) {
         throw f_135806_.createWithContext(readerIn);
      } else {
         try {
            return m_338530_(s);
         } catch (C_5250_ var4) {
            readerIn.setCursor(i);
            throw f_135806_.createWithContext(readerIn);
         }
      }
   }

   public static boolean m_135816_(char charIn) {
      return charIn >= '0' && charIn <= '9'
         || charIn >= 'a' && charIn <= 'z'
         || charIn == '_'
         || charIn == ':'
         || charIn == '/'
         || charIn == '.'
         || charIn == '-';
   }

   public static boolean m_135841_(String pathIn) {
      for (int i = 0; i < pathIn.length(); i++) {
         if (!m_135828_(pathIn.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   public static boolean m_135843_(String namespaceIn) {
      for (int i = 0; i < namespaceIn.length(); i++) {
         if (!m_135835_(namespaceIn.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   private static String m_245413_(String namespaceIn, String pathIn) {
      if (!m_135843_(namespaceIn)) {
         throw new C_5250_("Non [a-z0-9_.-] character in namespace of location: " + namespaceIn + ":" + pathIn);
      } else {
         return namespaceIn;
      }
   }

   public static boolean m_135828_(char charIn) {
      return charIn == '_' || charIn == '-' || charIn >= 'a' && charIn <= 'z' || charIn >= '0' && charIn <= '9' || charIn == '/' || charIn == '.';
   }

   private static boolean m_135835_(char charIn) {
      return charIn == '_' || charIn == '-' || charIn >= 'a' && charIn <= 'z' || charIn >= '0' && charIn <= '9' || charIn == '.';
   }

   private static String m_245185_(String namespaceIn, String pathIn) {
      if (!m_135841_(pathIn)) {
         throw new C_5250_("Non [a-z0-9/._-] character in path of location: " + namespaceIn + ":" + pathIn);
      } else {
         return pathIn;
      }
   }

   public boolean isDefaultNamespace() {
      return this.defaultNamespace;
   }

   public int compareNamespaced(C_5265_ o) {
      int ret = this.f_135804_.compareTo(o.f_135804_);
      return ret != 0 ? ret : this.f_135805_.compareTo(o.f_135805_);
   }

   public static class C_5266_ implements JsonDeserializer<C_5265_>, JsonSerializer<C_5265_> {
      public C_5265_ deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         return C_5265_.m_338530_(C_181_.m_13805_(p_deserialize_1_, "location"));
      }

      public JsonElement serialize(C_5265_ p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         return new JsonPrimitive(p_serialize_1_.toString());
      }
   }
}
