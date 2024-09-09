package net.minecraft.src;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import org.slf4j.Logger;

public class C_260420_ implements C_260369_ {
   private static final Logger f_260566_ = LogUtils.getLogger();
   public static final MapCodec f_260609_ = RecordCodecBuilder.mapCodec((instanceIn) -> {
      return instanceIn.group(C_5265_.f_135803_.fieldOf("resource").forGetter((fileIn) -> {
         return fileIn.f_260456_;
      }), C_5265_.f_135803_.optionalFieldOf("sprite").forGetter((fileIn) -> {
         return fileIn.f_260731_;
      })).apply(instanceIn, C_260420_::new);
   });
   private final C_5265_ f_260456_;
   private final Optional f_260731_;

   public C_260420_(C_5265_ resourceIn, Optional spriteIn) {
      this.f_260456_ = resourceIn;
      this.f_260731_ = spriteIn;
   }

   public void m_260891_(C_77_ resourceManagerIn, C_260369_.C_260371_ outputIn) {
      C_5265_ resourcelocation = f_266012_.m_245698_(this.f_260456_);
      if (C_4484_.isAbsoluteLocation(this.f_260456_)) {
         resourcelocation = new C_5265_(this.f_260456_.m_135827_(), this.f_260456_.m_135815_() + ".png");
      }

      Optional optional = resourceManagerIn.getResource(resourcelocation);
      if (optional.isPresent()) {
         outputIn.m_261028_((C_5265_)this.f_260731_.orElse(this.f_260456_), (C_76_)optional.get());
      } else {
         f_260566_.warn("Missing sprite: {}", resourcelocation);
      }

   }

   public C_260409_ m_260850_() {
      return C_260366_.f_260457_;
   }
}
