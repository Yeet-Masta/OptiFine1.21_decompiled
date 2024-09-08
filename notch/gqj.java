package net.minecraft.src;

import java.util.Collection;
import java.util.Locale;

public class C_4483_ extends RuntimeException {
   private final Collection<C_4478_.C_243583_> f_118254_;

   public C_4483_(C_4478_.C_243583_ entryIn, Collection<C_4478_.C_243583_> entriesIn) {
      super(
         String.format(
            Locale.ROOT,
            "Unable to fit: %s - size: %dx%d - Maybe try a lower resolution resourcepack?",
            entryIn.m_246162_(),
            entryIn.m_246492_(),
            entryIn.m_245330_()
         )
      );
      this.f_118254_ = entriesIn;
   }

   public Collection<C_4478_.C_243583_> m_118258_() {
      return this.f_118254_;
   }

   public C_4483_(C_4478_.C_243583_ entryIn, Collection<C_4478_.C_243583_> entriesIn, int atlasWidth, int atlasHeight, int maxWidth, int maxHeight) {
      super(
         String.format(
            Locale.ROOT,
            "Unable to fit: %s - size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?",
            entryIn.m_246162_() + "",
            entryIn.m_246492_(),
            entryIn.m_245330_(),
            atlasWidth,
            atlasHeight,
            maxWidth,
            maxHeight
         )
      );
      this.f_118254_ = entriesIn;
   }
}
