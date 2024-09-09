package net.minecraft.client.renderer.texture.atlas.sources;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

public class SingleFile implements SpriteSource {
   private static final Logger f_260566_ = LogUtils.getLogger();
   public static final MapCodec f_260609_ = RecordCodecBuilder.mapCodec((instanceIn) -> {
      return instanceIn.group(ResourceLocation.f_135803_.fieldOf("resource").forGetter((fileIn) -> {
         return fileIn.f_260456_;
      }), ResourceLocation.f_135803_.optionalFieldOf("sprite").forGetter((fileIn) -> {
         return fileIn.f_260731_;
      })).apply(instanceIn, SingleFile::new);
   });
   private final ResourceLocation f_260456_;
   private final Optional f_260731_;

   public SingleFile(ResourceLocation resourceIn, Optional spriteIn) {
      this.f_260456_ = resourceIn;
      this.f_260731_ = spriteIn;
   }

   public void m_260891_(ResourceManager resourceManagerIn, SpriteSource.Output outputIn) {
      ResourceLocation resourcelocation = f_266012_.m_245698_(this.f_260456_);
      if (TextureAtlas.isAbsoluteLocation(this.f_260456_)) {
         resourcelocation = new ResourceLocation(this.f_260456_.m_135827_(), this.f_260456_.m_135815_() + ".png");
      }

      Optional optional = resourceManagerIn.m_213713_(resourcelocation);
      if (optional.isPresent()) {
         outputIn.m_261028_((ResourceLocation)this.f_260731_.orElse(this.f_260456_), (Resource)optional.get());
      } else {
         f_260566_.warn("Missing sprite: {}", resourcelocation);
      }

   }

   public SpriteSourceType m_260850_() {
      return SpriteSources.f_260457_;
   }
}
