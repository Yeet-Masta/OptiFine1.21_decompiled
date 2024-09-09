import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.src.C_260366_;
import net.minecraft.src.C_260369_;
import net.minecraft.src.C_260409_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_260369_.C_260371_;
import org.slf4j.Logger;

public class SingleFile implements C_260369_ {
   private static final Logger c = LogUtils.getLogger();
   public static final MapCodec<SingleFile> b = RecordCodecBuilder.mapCodec(
      instanceIn -> instanceIn.group(
               ResourceLocation.a.fieldOf("resource").forGetter(fileIn -> fileIn.d), ResourceLocation.a.optionalFieldOf("sprite").forGetter(fileIn -> fileIn.e)
            )
            .apply(instanceIn, SingleFile::new)
   );
   private final ResourceLocation d;
   private final Optional<ResourceLocation> e;

   public SingleFile(ResourceLocation resourceIn, Optional<ResourceLocation> spriteIn) {
      this.d = resourceIn;
      this.e = spriteIn;
   }

   public void m_260891_(C_77_ resourceManagerIn, C_260371_ outputIn) {
      ResourceLocation resourcelocation = f_266012_.a(this.d);
      if (TextureAtlas.isAbsoluteLocation(this.d)) {
         resourcelocation = new ResourceLocation(this.d.b(), this.d.a() + ".png");
      }

      Optional<C_76_> optional = resourceManagerIn.getResource(resourcelocation);
      if (optional.isPresent()) {
         outputIn.a((ResourceLocation)this.e.orElse(this.d), (C_76_)optional.get());
      } else {
         c.warn("Missing sprite: {}", resourcelocation);
      }
   }

   public C_260409_ m_260850_() {
      return C_260366_.f_260457_;
   }
}
