package net.minecraft.world.item;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.util.StringRepresentable.EnumCodec;
import net.minecraft.world.level.material.MapColor;
import net.optifine.reflect.Reflector;

public enum DyeColor implements StringRepresentable {
   WHITE(0, "white", 16383998, MapColor.f_283811_, 15790320, 16777215),
   ORANGE(1, "orange", 16351261, MapColor.f_283750_, 15435844, 16738335),
   MAGENTA(2, "magenta", 13061821, MapColor.f_283931_, 12801229, 16711935),
   LIGHT_BLUE(3, "light_blue", 3847130, MapColor.f_283869_, 6719955, 10141901),
   YELLOW(4, "yellow", 16701501, MapColor.f_283832_, 14602026, 16776960),
   LIME(5, "lime", 8439583, MapColor.f_283916_, 4312372, 12582656),
   PINK(6, "pink", 15961002, MapColor.f_283765_, 14188952, 16738740),
   GRAY(7, "gray", 4673362, MapColor.f_283818_, 4408131, 8421504),
   LIGHT_GRAY(8, "light_gray", 10329495, MapColor.f_283779_, 11250603, 13882323),
   CYAN(9, "cyan", 1481884, MapColor.f_283772_, 2651799, 65535),
   PURPLE(10, "purple", 8991416, MapColor.f_283889_, 8073150, 10494192),
   BLUE(11, "blue", 3949738, MapColor.f_283743_, 2437522, 255),
   BROWN(12, "brown", 8606770, MapColor.f_283748_, 5320730, 9127187),
   GREEN(13, "green", 6192150, MapColor.f_283784_, 3887386, 65280),
   RED(14, "red", 11546150, MapColor.f_283913_, 11743532, 16711680),
   BLACK(15, "black", 1908001, MapColor.f_283927_, 1973019, 0);

   private static final IntFunction<DyeColor> f_41032_ = ByIdMap.m_262839_(DyeColor::m_41060_, values(), OutOfBoundsStrategy.ZERO);
   private static final Int2ObjectOpenHashMap<DyeColor> f_41033_ = new Int2ObjectOpenHashMap(
      (Map)Arrays.stream(values()).collect(Collectors.toMap(dyeColorIn -> dyeColorIn.f_41040_, p_41055_0_ -> p_41055_0_))
   );
   public static final EnumCodec<DyeColor> f_262211_ = StringRepresentable.m_216439_(DyeColor::values);
   public static final StreamCodec<ByteBuf, DyeColor> f_313960_ = ByteBufCodecs.m_321301_(f_41032_, DyeColor::m_41060_);
   private final int f_41034_;
   private final String f_41035_;
   private final MapColor f_283766_;
   private int f_337674_;
   private final int f_41040_;
   private final TagKey<Item> tag;
   private final int f_41041_;

   private DyeColor(
      final int idIn, final String translationKeyIn, final int colorValueIn, final MapColor mapColorIn, final int fireworkColorIn, final int textColorIn
   ) {
      this.f_41034_ = idIn;
      this.f_41035_ = translationKeyIn;
      this.f_283766_ = mapColorIn;
      this.f_41041_ = textColorIn;
      this.f_337674_ = ARGB32.m_321570_(colorValueIn);
      this.f_41040_ = fireworkColorIn;
      this.tag = (TagKey<Item>)Reflector.ForgeItemTags_create.call(new ResourceLocation("forge", "dyes/" + translationKeyIn));
   }

   public int m_41060_() {
      return this.f_41034_;
   }

   public String m_41065_() {
      return this.f_41035_;
   }

   public int m_340318_() {
      return this.f_337674_;
   }

   public MapColor m_284406_() {
      return this.f_283766_;
   }

   public int m_41070_() {
      return this.f_41040_;
   }

   public int m_41071_() {
      return this.f_41041_;
   }

   public static DyeColor m_41053_(int colorId) {
      return (DyeColor)f_41032_.apply(colorId);
   }

   @Nullable
   public static DyeColor m_41057_(String translationKeyIn, @Nullable DyeColor fallback) {
      DyeColor dyecolor = (DyeColor)f_262211_.m_216455_(translationKeyIn);
      return dyecolor != null ? dyecolor : fallback;
   }

   @Nullable
   public static DyeColor m_41061_(int fireworkColorIn) {
      return (DyeColor)f_41033_.get(fireworkColorIn);
   }

   public String toString() {
      return this.f_41035_;
   }

   public String m_7912_() {
      return this.f_41035_;
   }

   public void setTextureDiffuseColor(int color) {
      this.f_337674_ = color;
   }

   public TagKey<Item> getTag() {
      return this.tag;
   }

   @Nullable
   public static DyeColor getColor(ItemStack stack) {
      if (stack.m_41720_() instanceof DyeItem) {
         return ((DyeItem)stack.m_41720_()).m_41089_();
      } else {
         for (int x = 0; x < BLACK.m_41060_(); x++) {
            DyeColor color = m_41053_(x);
            if (stack.m_204117_(color.getTag())) {
               return color;
            }
         }

         return null;
      }
   }
}
