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
import net.minecraft.src.C_181_;
import net.minecraft.src.C_313613_;
import net.minecraft.src.C_313866_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5250_;

public final class ResourceLocation implements Comparable<ResourceLocation> {
   public static final Codec<ResourceLocation> a = Codec.STRING.comapFlatMap(ResourceLocation::d, ResourceLocation::toString).stable();
   public static final C_313866_<ByteBuf, ResourceLocation> b = C_313613_.f_315450_.m_323038_(ResourceLocation::a, ResourceLocation::toString);
   public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(C_4996_.m_237115_("argument.id.invalid"));
   public static final char d = ':';
   public static final String e = "minecraft";
   public static final String f = "realms";
   private final String h;
   private final String i;
   private final boolean defaultNamespace;

   public ResourceLocation(String str) {
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

   public ResourceLocation(String namespaceIn, String pathIn) {
      this.h = namespaceIn;
      this.i = pathIn;
      this.defaultNamespace = "minecraft".equals(namespaceIn);
   }

   private static ResourceLocation d(String namespaceIn, String pathIn) {
      return new ResourceLocation(e(namespaceIn, pathIn), f(namespaceIn, pathIn));
   }

   public static ResourceLocation a(String namespaceIn, String pathIn) {
      return d(namespaceIn, pathIn);
   }

   public static ResourceLocation a(String stringIn) {
      return a(stringIn, ':');
   }

   public static ResourceLocation b(String pathIn) {
      return new ResourceLocation("minecraft", f("minecraft", pathIn));
   }

   @Nullable
   public static ResourceLocation c(String string) {
      return b(string, ':');
   }

   @Nullable
   public static ResourceLocation b(String namespaceIn, String pathIn) {
      return j(namespaceIn) && i(pathIn) ? new ResourceLocation(namespaceIn, pathIn) : null;
   }

   public static ResourceLocation a(String stringIn, char separatorIn) {
      int i = stringIn.indexOf(separatorIn);
      if (i >= 0) {
         String s = stringIn.substring(i + 1);
         if (i != 0) {
            String s1 = stringIn.substring(0, i);
            return d(s1, s);
         } else {
            return b(s);
         }
      } else {
         return b(stringIn);
      }
   }

   @Nullable
   public static ResourceLocation b(String stringIn, char separatorIn) {
      int i = stringIn.indexOf(separatorIn);
      if (i >= 0) {
         String s = stringIn.substring(i + 1);
         if (!i(s)) {
            return null;
         } else if (i != 0) {
            String s1 = stringIn.substring(0, i);
            return j(s1) ? new ResourceLocation(s1, s) : null;
         } else {
            return new ResourceLocation("minecraft", s);
         }
      } else {
         return i(stringIn) ? new ResourceLocation("minecraft", stringIn) : null;
      }
   }

   public static DataResult<ResourceLocation> d(String stringIn) {
      try {
         return DataResult.success(a(stringIn));
      } catch (C_5250_ var2) {
         return DataResult.error(() -> "Not a valid resource location: " + stringIn + " " + var2.getMessage());
      }
   }

   public String a() {
      return this.i;
   }

   public String b() {
      return this.h;
   }

   public ResourceLocation e(String pathIn) {
      return new ResourceLocation(this.h, f(this.h, pathIn));
   }

   public ResourceLocation a(UnaryOperator<String> pathIn) {
      return this.e((String)pathIn.apply(this.i));
   }

   public ResourceLocation f(String prefixIn) {
      return this.e(prefixIn + this.i);
   }

   public ResourceLocation g(String suffixIn) {
      return this.e(this.i + suffixIn);
   }

   public String toString() {
      return this.h + ":" + this.i;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         return p_equals_1_ instanceof ResourceLocation resourcelocation ? this.h.equals(resourcelocation.h) && this.i.equals(resourcelocation.i) : false;
      }
   }

   public int hashCode() {
      return 31 * this.h.hashCode() + this.i.hashCode();
   }

   public int a(ResourceLocation p_compareTo_1_) {
      int i = this.i.compareTo(p_compareTo_1_.i);
      if (i == 0) {
         i = this.h.compareTo(p_compareTo_1_.h);
      }

      return i;
   }

   public String c() {
      return this.toString().replace('/', '_').replace(':', '_');
   }

   public String d() {
      return this.h + "." + this.i;
   }

   public String e() {
      return this.h.equals("minecraft") ? this.i : this.d();
   }

   public String h(String prefixIn) {
      return prefixIn + "." + this.d();
   }

   public String c(String sectionIn, String variantIn) {
      return sectionIn + "." + this.d() + "." + variantIn;
   }

   private static String c(StringReader readerIn) {
      int i = readerIn.getCursor();

      while (readerIn.canRead() && a(readerIn.peek())) {
         readerIn.skip();
      }

      return readerIn.getString().substring(i, readerIn.getCursor());
   }

   public static ResourceLocation a(StringReader reader) throws CommandSyntaxException {
      int i = reader.getCursor();
      String s = c(reader);

      try {
         return a(s);
      } catch (C_5250_ var4) {
         reader.setCursor(i);
         throw c.createWithContext(reader);
      }
   }

   public static ResourceLocation b(StringReader readerIn) throws CommandSyntaxException {
      int i = readerIn.getCursor();
      String s = c(readerIn);
      if (s.isEmpty()) {
         throw c.createWithContext(readerIn);
      } else {
         try {
            return a(s);
         } catch (C_5250_ var4) {
            readerIn.setCursor(i);
            throw c.createWithContext(readerIn);
         }
      }
   }

   public static boolean a(char charIn) {
      return charIn >= '0' && charIn <= '9'
         || charIn >= 'a' && charIn <= 'z'
         || charIn == '_'
         || charIn == ':'
         || charIn == '/'
         || charIn == '.'
         || charIn == '-';
   }

   public static boolean i(String pathIn) {
      for (int i = 0; i < pathIn.length(); i++) {
         if (!b(pathIn.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   public static boolean j(String namespaceIn) {
      for (int i = 0; i < namespaceIn.length(); i++) {
         if (!c(namespaceIn.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   private static String e(String namespaceIn, String pathIn) {
      if (!j(namespaceIn)) {
         throw new C_5250_("Non [a-z0-9_.-] character in namespace of location: " + namespaceIn + ":" + pathIn);
      } else {
         return namespaceIn;
      }
   }

   public static boolean b(char charIn) {
      return charIn == '_' || charIn == '-' || charIn >= 'a' && charIn <= 'z' || charIn >= '0' && charIn <= '9' || charIn == '/' || charIn == '.';
   }

   private static boolean c(char charIn) {
      return charIn == '_' || charIn == '-' || charIn >= 'a' && charIn <= 'z' || charIn >= '0' && charIn <= '9' || charIn == '.';
   }

   private static String f(String namespaceIn, String pathIn) {
      if (!i(pathIn)) {
         throw new C_5250_("Non [a-z0-9/._-] character in path of location: " + namespaceIn + ":" + pathIn);
      } else {
         return pathIn;
      }
   }

   public boolean isDefaultNamespace() {
      return this.defaultNamespace;
   }

   public int compareNamespaced(ResourceLocation o) {
      int ret = this.h.compareTo(o.h);
      return ret != 0 ? ret : this.i.compareTo(o.i);
   }

   public static class a implements JsonDeserializer<ResourceLocation>, JsonSerializer<ResourceLocation> {
      public ResourceLocation a(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         return ResourceLocation.a(C_181_.m_13805_(p_deserialize_1_, "location"));
      }

      public JsonElement a(ResourceLocation p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         return new JsonPrimitive(p_serialize_1_.toString());
      }
   }
}
