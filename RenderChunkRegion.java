import javax.annotation.Nullable;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_2681_;
import net.minecraft.src.C_2691_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4710_;
import net.minecraft.src.C_4982_;
import net.minecraftforge.client.model.data.ModelDataManager;
import net.optifine.override.ChunkCacheOF;

public class RenderChunkRegion implements C_1557_ {
   public static final int a = 1;
   public static final int b = 3;
   private final int e;
   private final int f;
   protected final RenderChunk[] c;
   protected final C_1596_ d;
   private C_4710_ sectionPos;

   RenderChunkRegion(C_1596_ worldIn, int chunkStartXIn, int chunkStartYIn, RenderChunk[] chunksIn) {
      this(worldIn, chunkStartXIn, chunkStartYIn, chunksIn, null);
   }

   RenderChunkRegion(C_1596_ worldIn, int chunkStartXIn, int chunkStartYIn, RenderChunk[] chunksIn, C_4710_ sectionPosIn) {
      this.d = worldIn;
      this.e = chunkStartXIn;
      this.f = chunkStartYIn;
      this.c = chunksIn;
      this.sectionPos = sectionPosIn;
   }

   public BlockState a_(C_4675_ pos) {
      return this.a(C_4710_.m_123171_(pos.m_123341_()), C_4710_.m_123171_(pos.m_123343_())).b(pos);
   }

   public C_2691_ m_6425_(C_4675_ pos) {
      return this.a(C_4710_.m_123171_(pos.m_123341_()), C_4710_.m_123171_(pos.m_123343_())).b(pos).m_60819_();
   }

   public float a(Direction directionIn, boolean shadeIn) {
      return this.d.a(directionIn, shadeIn);
   }

   public C_2681_ m_5518_() {
      return this.d.m_5518_();
   }

   @Nullable
   public BlockEntity c_(C_4675_ pos) {
      return this.a(C_4710_.m_123171_(pos.m_123341_()), C_4710_.m_123171_(pos.m_123343_())).a(pos);
   }

   public RenderChunk a(int x, int z) {
      return this.c[a(this.e, this.f, x, z)];
   }

   public int m_6171_(C_4675_ blockPosIn, C_4982_ colorResolverIn) {
      return this.d.m_6171_(blockPosIn, colorResolverIn);
   }

   public int m_141937_() {
      return this.d.m_141937_();
   }

   public int m_141928_() {
      return this.d.m_141928_();
   }

   public static int a(int xMin, int zMin, int x, int z) {
      return x - xMin + (z - zMin) * 3;
   }

   public C_1629_ getBiome(C_4675_ pos) {
      return (C_1629_)this.d.m_204166_(pos).m_203334_();
   }

   public C_2137_ getLevelChunk(int cx, int cz) {
      return this.a(cx, cz).getChunk();
   }

   public void finish() {
      for (int i = 0; i < this.c.length; i++) {
         RenderChunk rc = this.c[i];
         rc.finish();
      }
   }

   public ChunkCacheOF makeChunkCacheOF() {
      return this.sectionPos == null ? null : new ChunkCacheOF(this, this.sectionPos);
   }

   public int getMinChunkX() {
      return this.e;
   }

   public int getMinChunkZ() {
      return this.f;
   }

   public float getShade(float normalX, float normalY, float normalZ, boolean shade) {
      return this.d instanceof ClientLevel clientWorld ? clientWorld.getShade(normalX, normalY, normalZ, shade) : 1.0F;
   }

   public ModelDataManager getModelDataManager() {
      return this.d instanceof ClientLevel clientWorld ? clientWorld.getModelDataManager() : null;
   }
}
