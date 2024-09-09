package net.minecraft.client.renderer.texture;

import java.util.Collection;
import java.util.Locale;

public class StitcherException extends RuntimeException {
   private final Collection<net.minecraft.client.renderer.texture.Stitcher.Entry> f_118254_;

   public StitcherException(
      net.minecraft.client.renderer.texture.Stitcher.Entry entryIn, Collection<net.minecraft.client.renderer.texture.Stitcher.Entry> entriesIn
   ) {
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

   public Collection<net.minecraft.client.renderer.texture.Stitcher.Entry> m_118258_() {
      return this.f_118254_;
   }

   public StitcherException(
      net.minecraft.client.renderer.texture.Stitcher.Entry entryIn,
      Collection<net.minecraft.client.renderer.texture.Stitcher.Entry> entriesIn,
      int atlasWidth,
      int atlasHeight,
      int maxWidth,
      int maxHeight
   ) {
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
