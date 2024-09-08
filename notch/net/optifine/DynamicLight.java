package net.optifine;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_290152_;
import net.minecraft.src.C_4134_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_4675_.C_4681_;

public class DynamicLight {
   private C_507_ entity = null;
   private double offsetY = 0.0;
   private double lastPosX = -2.1474836E9F;
   private double lastPosY = -2.1474836E9F;
   private double lastPosZ = -2.1474836E9F;
   private int lastLightLevel = 0;
   private long timeCheckMs = 0L;
   private Set<C_4675_> setLitChunkPos = new HashSet();
   private C_4681_ blockPosMutable = new C_4681_();

   public DynamicLight(C_507_ entity) {
      this.entity = entity;
      this.offsetY = (double)entity.m_20192_();
   }

   public void update(C_4134_ renderGlobal) {
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
         Set<C_4675_> setNewPos = new HashSet();
         if (lightLevel > 0) {
            C_4687_ dirX = (C_188_.m_14107_(posX) & 15) >= 8 ? C_4687_.EAST : C_4687_.WEST;
            C_4687_ dirY = (C_188_.m_14107_(posY) & 15) >= 8 ? C_4687_.UP : C_4687_.DOWN;
            C_4687_ dirZ = (C_188_.m_14107_(posZ) & 15) >= 8 ? C_4687_.SOUTH : C_4687_.NORTH;
            C_4675_ chunkPos = C_4675_.m_274561_(posX, posY, posZ);
            C_290152_.C_290138_ chunk = renderGlobal.getRenderChunk(chunkPos);
            C_4675_ chunkPosX = this.getChunkPos(chunk, chunkPos, dirX);
            C_290152_.C_290138_ chunkX = renderGlobal.getRenderChunk(chunkPosX);
            C_4675_ chunkPosZ = this.getChunkPos(chunk, chunkPos, dirZ);
            C_290152_.C_290138_ chunkZ = renderGlobal.getRenderChunk(chunkPosZ);
            C_4675_ chunkPosXZ = this.getChunkPos(chunkX, chunkPosX, dirZ);
            C_290152_.C_290138_ chunkXZ = renderGlobal.getRenderChunk(chunkPosXZ);
            C_4675_ chunkPosY = this.getChunkPos(chunk, chunkPos, dirY);
            C_290152_.C_290138_ chunkY = renderGlobal.getRenderChunk(chunkPosY);
            C_4675_ chunkPosYX = this.getChunkPos(chunkY, chunkPosY, dirX);
            C_290152_.C_290138_ chunkYX = renderGlobal.getRenderChunk(chunkPosYX);
            C_4675_ chunkPosYZ = this.getChunkPos(chunkY, chunkPosY, dirZ);
            C_290152_.C_290138_ chunkYZ = renderGlobal.getRenderChunk(chunkPosYZ);
            C_4675_ chunkPosYXZ = this.getChunkPos(chunkYX, chunkPosYX, dirZ);
            C_290152_.C_290138_ chunkYXZ = renderGlobal.getRenderChunk(chunkPosYXZ);
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

   private C_4675_ getChunkPos(C_290152_.C_290138_ renderChunk, C_4675_ pos, C_4687_ facing) {
      return renderChunk != null ? renderChunk.m_292593_(facing) : pos.m_5484_(facing, 16);
   }

   private void updateChunkLight(C_290152_.C_290138_ renderChunk, Set<C_4675_> setPrevPos, Set<C_4675_> setNewPos) {
      if (renderChunk != null) {
         C_290152_.C_290185_ compiledChunk = renderChunk.m_293175_();
         if (compiledChunk != null && !compiledChunk.m_295467_()) {
            renderChunk.m_292780_(false);
            renderChunk.setNeedsBackgroundPriorityUpdate(true);
         }

         C_4675_ pos = renderChunk.m_295500_().m_7949_();
         if (setPrevPos != null) {
            setPrevPos.remove(pos);
         }

         if (setNewPos != null) {
            setNewPos.add(pos);
         }
      }
   }

   public void updateLitChunks(C_4134_ renderGlobal) {
      for (C_4675_ posOld : this.setLitChunkPos) {
         C_290152_.C_290138_ chunkOld = renderGlobal.getRenderChunk(posOld);
         this.updateChunkLight(chunkOld, null, null);
      }
   }

   public C_507_ getEntity() {
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
      return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
   }
}
