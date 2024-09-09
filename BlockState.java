import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_2056_.C_2058_;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.optifine.Config;
import net.optifine.util.BlockUtils;

public class BlockState extends C_2058_ implements IForgeBlockState {
   public static final Codec<BlockState> b = m_61127_(C_256712_.f_256975_.m_194605_(), C_1706_::o).stable();
   private int blockId = -1;
   private int metadata = -1;
   private ResourceLocation blockLocation;
   private int blockStateId = -1;
   private static final AtomicInteger blockStateIdCounter = new AtomicInteger(0);

   public int getBlockId() {
      if (this.blockId < 0) {
         this.blockId = C_256712_.f_256975_.m_7447_(this.m_60734_());
      }

      return this.blockId;
   }

   public int getMetadata() {
      if (this.metadata < 0) {
         this.metadata = BlockUtils.getMetadata(this);
         if (this.metadata < 0) {
            Config.warn("Metadata not found, block: " + this.getBlockLocation());
            this.metadata = 0;
         }
      }

      return this.metadata;
   }

   public ResourceLocation getBlockLocation() {
      if (this.blockLocation == null) {
         this.blockLocation = C_256712_.f_256975_.b(this.m_60734_());
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

   public BlockState(C_1706_ blockIn, Reference2ObjectArrayMap<Property<?>, Comparable<?>> propertiesIn, MapCodec<BlockState> codecIn) {
      super(blockIn, propertiesIn, codecIn);
   }

   protected BlockState x() {
      return this;
   }
}
