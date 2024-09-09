package net.optifine.override;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class PlayerControllerOF extends MultiPlayerGameMode {
   private boolean acting = false;
   private BlockPos lastClickBlockPos = null;
   private Entity lastClickEntity = null;

   public PlayerControllerOF(Minecraft mcIn, ClientPacketListener netHandler) {
      super(mcIn, netHandler);
   }

   public boolean m_105269_(BlockPos loc, net.minecraft.core.Direction face) {
      this.acting = true;
      this.lastClickBlockPos = loc;
      boolean res = super.m_105269_(loc, face);
      this.acting = false;
      return res;
   }

   public boolean m_105283_(BlockPos posBlock, net.minecraft.core.Direction directionFacing) {
      this.acting = true;
      this.lastClickBlockPos = posBlock;
      boolean res = super.m_105283_(posBlock, directionFacing);
      this.acting = false;
      return res;
   }

   public InteractionResult m_233721_(Player player, InteractionHand hand) {
      this.acting = true;
      InteractionResult res = super.m_233721_(player, hand);
      this.acting = false;
      return res;
   }

   public InteractionResult m_233732_(LocalPlayer player, InteractionHand hand, BlockHitResult rayTrace) {
      this.acting = true;
      this.lastClickBlockPos = rayTrace.m_82425_();
      InteractionResult res = super.m_233732_(player, hand, rayTrace);
      this.acting = false;
      return res;
   }

   public InteractionResult m_105226_(Player player, Entity target, InteractionHand hand) {
      this.lastClickEntity = target;
      return super.m_105226_(player, target, hand);
   }

   public InteractionResult m_105230_(Player player, Entity target, EntityHitResult ray, InteractionHand hand) {
      this.lastClickEntity = target;
      return super.m_105230_(player, target, ray, hand);
   }

   public boolean isActing() {
      return this.acting;
   }

   public BlockPos getLastClickBlockPos() {
      return this.lastClickBlockPos;
   }

   public Entity getLastClickEntity() {
      return this.lastClickEntity;
   }
}
