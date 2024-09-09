package net.minecraft.src;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.optifine.Config;
import net.optifine.util.BlockUtils;

public class C_2064_ extends C_2056_.C_2058_ implements IForgeBlockState {
   public static final Codec f_61039_;
   private int blockId = -1;
   private int metadata = -1;
   private C_5265_ blockLocation;
   private int blockStateId = -1;
   private static final AtomicInteger blockStateIdCounter;

   public int getBlockId() {
      if (this.blockId < 0) {
         this.blockId = C_256712_.f_256975_.a(this.m_60734_());
      }

      return this.blockId;
   }

   public int getMetadata() {
      if (this.metadata < 0) {
         this.metadata = BlockUtils.getMetadata(this);
         if (this.metadata < 0) {
            Config.warn("Metadata not found, block: " + String.valueOf(this.getBlockLocation()));
            this.metadata = 0;
         }
      }

      return this.metadata;
   }

   public C_5265_ getBlockLocation() {
      if (this.blockLocation == null) {
         this.blockLocation = C_256712_.f_256975_.m_7981_(this.m_60734_());
      }

      return this.blockLocation;
   }

   public int getBlockStateId() {
      if (this.blockStateId < 0) {
         this.blockStateId = blockStateIdCounter.incrementAndGet();
      }

      return this.blockStateId;
   }

   public int getLightValue(C_1559_ world, C_4675_ pos) {
      return this.m_60791_();
   }

   public boolean isCacheOpaqueCube() {
      return this.f_60593_ != null && this.f_60593_.f_60841_;
   }

   public boolean isCacheOpaqueCollisionShape() {
      return this.f_60593_ != null && this.f_60593_.f_60844_;
   }

   public C_2064_(C_1706_ blockIn, Reference2ObjectArrayMap propertiesIn, MapCodec codecIn) {
      super(blockIn, propertiesIn, codecIn);
   }

   protected C_2064_ m_7160_() {
      return this;
   }

   static {
      f_61039_ = a(C_256712_.f_256975_.r(), C_1706_::m_49966_).stable();
      blockStateIdCounter = new AtomicInteger(0);
   }
}
