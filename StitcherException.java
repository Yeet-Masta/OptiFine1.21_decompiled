import java.util.Collection;
import java.util.Locale;

public class StitcherException extends RuntimeException {
   private final Collection<Stitcher.a> a;

   public StitcherException(Stitcher.a entryIn, Collection<Stitcher.a> entriesIn) {
      super(String.format(Locale.ROOT, "Unable to fit: %s - size: %dx%d - Maybe try a lower resolution resourcepack?", entryIn.c(), entryIn.a(), entryIn.b()));
      this.a = entriesIn;
   }

   public Collection<Stitcher.a> a() {
      return this.a;
   }

   public StitcherException(Stitcher.a entryIn, Collection<Stitcher.a> entriesIn, int atlasWidth, int atlasHeight, int maxWidth, int maxHeight) {
      super(
         String.format(
            Locale.ROOT,
            "Unable to fit: %s - size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?",
            entryIn.c() + "",
            entryIn.a(),
            entryIn.b(),
            atlasWidth,
            atlasHeight,
            maxWidth,
            maxHeight
         )
      );
      this.a = entriesIn;
   }
}
