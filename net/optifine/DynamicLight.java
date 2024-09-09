package net.optifine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class DynamicLight {
   private Entity entity = null;
   private double offsetY = 0.0;
   private double lastPosX = -2.147483648E9;
   private double lastPosY = -2.147483648E9;
   private double lastPosZ = -2.147483648E9;
   private int lastLightLevel = 0;
   private long timeCheckMs = 0L;
   private Set setLitChunkPos = new HashSet();
   private BlockPos.MutableBlockPos blockPosMutable = new BlockPos.MutableBlockPos();

   public DynamicLight(Entity entity) {
      this.entity = entity;
      this.offsetY = (double)entity.m_20192_();
   }

   public void update(LevelRenderer renderGlobal) {
      if (Config.isDynamicLightsFast()) {
         long timeNowMs = System.currentTimeMillis();
         if (timeNowMs < this.timeCheckMs + 500L) {
            return;
         }

         this.timeCheckMs = timeNowMs;
      }

      double posX = this.entity.m_20185_() - 0.5;
      double posY = this.entity.m_20186_() - 0.5 + this.offsetY;
      double posZ = this.entity.m_20189_() - 0.5;
      int lightLevel = DynamicLights.getLightLevel(this.entity);
      double dx = posX - this.lastPosX;
      double dy = posY - this.lastPosY;
      double dz = posZ - this.lastPosZ;
      double delta = 0.1;
      if (!(Math.abs(dx) <= delta) || !(Math.abs(dy) <= delta) || !(Math.abs(dz) <= delta) || this.lastLightLevel != lightLevel) {
         this.lastPosX = posX;
         this.lastPosY = posY;
         this.lastPosZ = posZ;
         this.lastLightLevel = lightLevel;
         Set setNewPos = new HashSet();
         if (lightLevel > 0) {
            Direction dirX = (Mth.m_14107_(posX) & 15) >= 8 ? Direction.EAST : Direction.WEST;
            Direction dirY = (Mth.m_14107_(posY) & 15) >= 8 ? Direction.field_61 : Direction.DOWN;
            Direction dirZ = (Mth.m_14107_(posZ) & 15) >= 8 ? Direction.SOUTH : Direction.NORTH;
            BlockPos chunkPos = BlockPos.m_274561_(posX, posY, posZ);
            SectionRenderDispatcher.RenderSection chunk = renderGlobal.getRenderChunk(chunkPos);
            BlockPos chunkPosX = this.getChunkPos(chunk, chunkPos, dirX);
            SectionRenderDispatcher.RenderSection chunkX = renderGlobal.getRenderChunk(chunkPosX);
            BlockPos chunkPosZ = this.getChunkPos(chunk, chunkPos, dirZ);
            SectionRenderDispatcher.RenderSection chunkZ = renderGlobal.getRenderChunk(chunkPosZ);
            BlockPos chunkPosXZ = this.getChunkPos(chunkX, chunkPosX, dirZ);
            SectionRenderDispatcher.RenderSection chunkXZ = renderGlobal.getRenderChunk(chunkPosXZ);
            BlockPos chunkPosY = this.getChunkPos(chunk, chunkPos, dirY);
            SectionRenderDispatcher.RenderSection chunkY = renderGlobal.getRenderChunk(chunkPosY);
            BlockPos chunkPosYX = this.getChunkPos(chunkY, chunkPosY, dirX);
            SectionRenderDispatcher.RenderSection chunkYX = renderGlobal.getRenderChunk(chunkPosYX);
            BlockPos chunkPosYZ = this.getChunkPos(chunkY, chunkPosY, dirZ);
            SectionRenderDispatcher.RenderSection chunkYZ = renderGlobal.getRenderChunk(chunkPosYZ);
            BlockPos chunkPosYXZ = this.getChunkPos(chunkYX, chunkPosYX, dirZ);
            SectionRenderDispatcher.RenderSection chunkYXZ = renderGlobal.getRenderChunk(chunkPosYXZ);
            this.updateChunkLight(chunk, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkX, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkZ, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkXZ, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkY, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkYX, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkYZ, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkYXZ, this.setLitChunkPos, setNewPos);
         }

         this.updateLitChunks(renderGlobal);
         this.setLitChunkPos = setNewPos;
      }
   }

   private BlockPos getChunkPos(SectionRenderDispatcher.RenderSection renderChunk, BlockPos pos, Direction facing) {
      return renderChunk != null ? renderChunk.m_292593_(facing) : pos.m_5484_(facing, 16);
   }

   private void updateChunkLight(SectionRenderDispatcher.RenderSection renderChunk, Set setPrevPos, Set setNewPos) {
      if (renderChunk != null) {
         SectionRenderDispatcher.CompiledSection compiledChunk = renderChunk.m_293175_();
         if (compiledChunk != null && !compiledChunk.m_295467_()) {
            renderChunk.m_292780_(false);
            renderChunk.setNeedsBackgroundPriorityUpdate(true);
         }

         BlockPos pos = renderChunk.m_295500_().m_7949_();
         if (setPrevPos != null) {
            setPrevPos.remove(pos);
         }

         if (setNewPos != null) {
            setNewPos.add(pos);
         }

      }
   }

   public void updateLitChunks(LevelRenderer renderGlobal) {
      Iterator it = this.setLitChunkPos.iterator();

      while(it.hasNext()) {
         BlockPos posOld = (BlockPos)it.next();
         SectionRenderDispatcher.RenderSection chunkOld = renderGlobal.getRenderChunk(posOld);
         this.updateChunkLight(chunkOld, (Set)null, (Set)null);
      }

   }

   public Entity getEntity() {
      return this.entity;
   }

   public double getLastPosX() {
      return this.lastPosX;
   }

   public double getLastPosY() {
      return this.lastPosY;
   }

   public double getLastPosZ() {
      return this.lastPosZ;
   }

   public int getLastLightLevel() {
      return this.lastLightLevel;
   }

   public double getOffsetY() {
      return this.offsetY;
   }

   public String toString() {
      String var10000 = String.valueOf(this.entity);
      return "Entity: " + var10000 + ", offsetY: " + this.offsetY;
   }
}
