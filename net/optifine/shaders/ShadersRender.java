package net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.optifine.Config;
import net.optifine.Lagometer;
import net.optifine.reflect.Reflector;
import net.optifine.render.GlBlendState;
import net.optifine.render.GlCullState;
import net.optifine.render.ICamera;
import net.optifine.render.RenderTypes;
import net.optifine.util.MathUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ShadersRender {
   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
   public static boolean frustumTerrainShadowChanged = false;
   public static boolean frustumEntitiesShadowChanged = false;
   public static int countEntitiesRenderedShadow;
   public static int countTileEntitiesRenderedShadow;
   private static Map mapEntityLists = new HashMap();

   public static void setFrustrumPosition(ICamera frustum, double x, double y, double z) {
      frustum.setCameraPosition(x, y, z);
   }

   public static void beginTerrainSolid() {
      if (Shaders.isRenderingWorld) {
         Shaders.fogEnabled = true;
         Shaders.useProgram(Shaders.ProgramTerrain);
         Shaders.setRenderStage(RenderStage.TERRAIN_SOLID);
      }

   }

   public static void beginTerrainCutoutMipped() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramTerrain);
         Shaders.setRenderStage(RenderStage.TERRAIN_CUTOUT_MIPPED);
      }

   }

   public static void beginTerrainCutout() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramTerrain);
         Shaders.setRenderStage(RenderStage.TERRAIN_CUTOUT);
      }

   }

   public static void endTerrain() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramTexturedLit);
         Shaders.setRenderStage(RenderStage.NONE);
      }

   }

   public static void beginTranslucent() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramWater);
         Shaders.setRenderStage(RenderStage.TERRAIN_TRANSLUCENT);
      }

   }

   public static void endTranslucent() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramTexturedLit);
         Shaders.setRenderStage(RenderStage.NONE);
      }

   }

   public static void beginTripwire() {
      if (Shaders.isRenderingWorld) {
         Shaders.setRenderStage(RenderStage.TRIPWIRE);
      }

   }

   public static void endTripwire() {
      if (Shaders.isRenderingWorld) {
         Shaders.setRenderStage(RenderStage.NONE);
      }

   }

   public static void renderHand0(GameRenderer er, Matrix4f viewIn, Camera activeRenderInfo, float partialTicks) {
      if (!Shaders.isShadowPass) {
         boolean blockTranslucentMain = Shaders.isItemToRenderMainTranslucent();
         boolean blockTranslucentOff = Shaders.isItemToRenderOffTranslucent();
         if (!blockTranslucentMain || !blockTranslucentOff) {
            Shaders.readCenterDepth();
            Shaders.beginHand(false);
            Shaders.setSkipRenderHands(blockTranslucentMain, blockTranslucentOff);
            er.renderHand(activeRenderInfo, partialTicks, viewIn, true, false, false);
            Shaders.endHand();
            Shaders.setHandsRendered(!blockTranslucentMain, !blockTranslucentOff);
            Shaders.setSkipRenderHands(false, false);
         }
      }

   }

   public static void renderHand1(GameRenderer er, Matrix4f viewIn, Camera activeRenderInfo, float partialTicks) {
      if (!Shaders.isShadowPass && !Shaders.isBothHandsRendered()) {
         Shaders.readCenterDepth();
         GlStateManager._enableBlend();
         Shaders.beginHand(true);
         Shaders.setSkipRenderHands(Shaders.isHandRenderedMain(), Shaders.isHandRenderedOff());
         er.renderHand(activeRenderInfo, partialTicks, viewIn, true, false, true);
         Shaders.endHand();
         Shaders.setHandsRendered(true, true);
         Shaders.setSkipRenderHands(false, false);
      }

   }

   public static void renderItemFP(ItemInHandRenderer itemRenderer, float partialTicks, PoseStack matrixStackIn, MultiBufferSource.BufferSource bufferIn, LocalPlayer playerEntityIn, int combinedLightIn, boolean renderTranslucent) {
      Config.getEntityRenderDispatcher().setRenderedEntity(playerEntityIn);
      GlStateManager._depthMask(true);
      if (renderTranslucent) {
         GlStateManager._depthFunc(519);
         matrixStackIn.m_85836_();
         DrawBuffers drawBuffers = GlState.getDrawBuffers();
         GlState.setDrawBuffers(Shaders.drawBuffersNone);
         Shaders.renderItemKeepDepthMask = true;
         itemRenderer.m_109314_(partialTicks, matrixStackIn, bufferIn, playerEntityIn, combinedLightIn);
         Shaders.renderItemKeepDepthMask = false;
         GlState.setDrawBuffers(drawBuffers);
         matrixStackIn.m_85849_();
      }

      GlStateManager._depthFunc(515);
      itemRenderer.m_109314_(partialTicks, matrixStackIn, bufferIn, playerEntityIn, combinedLightIn);
      Config.getEntityRenderDispatcher().setRenderedEntity((Entity)null);
   }

   public static void renderFPOverlay(GameRenderer er, Matrix4f viewIn, Camera activeRenderInfo, float partialTicks) {
      if (!Shaders.isShadowPass) {
         Shaders.beginFPOverlay();
         er.renderHand(activeRenderInfo, partialTicks, viewIn, false, true, false);
         Shaders.endFPOverlay();
      }

   }

   public static void beginBlockDamage() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramDamagedBlock);
         Shaders.setRenderStage(RenderStage.DESTROY);
         if (Shaders.ProgramDamagedBlock.getId() == Shaders.ProgramTerrain.getId()) {
            GlState.setDrawBuffers(Shaders.drawBuffersColorAtt[0]);
            GlStateManager._depthMask(false);
         }
      }

   }

   public static void endBlockDamage() {
      if (Shaders.isRenderingWorld) {
         GlStateManager._depthMask(true);
         Shaders.useProgram(Shaders.ProgramTexturedLit);
         Shaders.setRenderStage(RenderStage.NONE);
      }

   }

   public static void beginOutline() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramBasic);
         Shaders.setRenderStage(RenderStage.OUTLINE);
      }

   }

   public static void endOutline() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramTexturedLit);
         Shaders.setRenderStage(RenderStage.NONE);
      }

   }

   public static void beginDebug() {
      if (Shaders.isRenderingWorld) {
         Shaders.setRenderStage(RenderStage.DEBUG);
      }

   }

   public static void endDebug() {
      if (Shaders.isRenderingWorld) {
         Shaders.setRenderStage(RenderStage.NONE);
      }

   }

   public static void renderShadowMap(GameRenderer entityRenderer, Camera activeRenderInfo, int pass, float partialTicks) {
      if (Shaders.hasShadowMap) {
         Minecraft mc = Minecraft.m_91087_();
         mc.m_91307_().m_6182_("shadow pass");
         LevelRenderer renderGlobal = mc.f_91060_;
         Shaders.isShadowPass = true;
         Shaders.updateProjectionMatrix();
         Shaders.checkGLError("pre shadow");
         Matrix4f projectionPrev = RenderSystem.getProjectionMatrix();
         VertexSorting vertexSortingPrev = RenderSystem.getVertexSorting();
         RenderSystem.getModelViewStack().pushMatrix();
         mc.m_91307_().m_6182_("shadow clear");
         Shaders.sfb.bindFramebuffer();
         Shaders.checkGLError("shadow bind sfb");
         mc.m_91307_().m_6182_("shadow camera");
         updateActiveRenderInfo(activeRenderInfo, mc, partialTicks);
         PoseStack matrixStack = new PoseStack();
         Shaders.setCameraShadow(matrixStack, activeRenderInfo, partialTicks);
         Matrix4f projectionMatrix = RenderSystem.getProjectionMatrix();
         Matrix4f viewMatrix = matrixStack.m_85850_().m_252922_();
         Shaders.checkGLError("shadow camera");
         Shaders.dispatchComputes(Shaders.dfb, Shaders.ProgramShadow.getComputePrograms());
         Shaders.useProgram(Shaders.ProgramShadow);
         Shaders.sfb.setDrawBuffers();
         Shaders.checkGLError("shadow drawbuffers");
         GL30.glReadBuffer(0);
         Shaders.checkGLError("shadow readbuffer");
         Shaders.sfb.setDepthTexture();
         Shaders.sfb.setColorTextures(true);
         Shaders.checkFramebufferStatus("shadow fb");
         GlStateManager._clearColor(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.clear(256);

         for(int i = 0; i < Shaders.usedShadowColorBuffers; ++i) {
            if (Shaders.shadowBuffersClear[i]) {
               Vector4f col = Shaders.shadowBuffersClearColor[i];
               if (col != null) {
                  GlStateManager._clearColor(col.x(), col.y(), col.z(), col.w());
               } else {
                  GlStateManager._clearColor(1.0F, 1.0F, 1.0F, 1.0F);
               }

               GlState.setDrawBuffers(Shaders.drawBuffersColorAtt[i]);
               GlStateManager.clear(16384);
            }
         }

         Shaders.sfb.setDrawBuffers();
         Shaders.checkGLError("shadow clear");
         mc.m_91307_().m_6182_("shadow frustum");
         Frustum frustum = makeShadowFrustum(activeRenderInfo, partialTicks);
         mc.m_91307_().m_6182_("shadow culling");
         Vec3 cameraPos = activeRenderInfo.m_90583_();
         frustum.m_113002_(cameraPos.f_82479_, cameraPos.f_82480_, cameraPos.f_82481_);
         GlStateManager._enableDepthTest();
         GlStateManager._depthFunc(515);
         GlStateManager._depthMask(true);
         GlStateManager._colorMask(true, true, true, true);
         GlStateManager.lockCull(new GlCullState(false));
         GlStateManager.lockBlend(new GlBlendState(false));
         mc.m_91307_().m_6182_("shadow prepareterrain");
         mc.m_91097_().m_174784_(TextureAtlas.f_118259_);
         mc.m_91307_().m_6182_("shadow setupterrain");
         renderGlobal.setShadowRenderInfos(true);
         Lagometer.timerVisibility.start();
         if (!renderGlobal.isDebugFrustum()) {
            applyFrustumShadow(renderGlobal, frustum);
         }

         Lagometer.timerVisibility.end();
         mc.m_91307_().m_6182_("shadow updatechunks");
         mc.m_91307_().m_6182_("shadow terrain");
         double x = cameraPos.m_7096_();
         double y = cameraPos.m_7098_();
         double z = cameraPos.m_7094_();
         Lagometer.timerTerrain.start();
         if (Shaders.isRenderShadowTerrain()) {
            GlStateManager.disableAlphaTest();
            renderGlobal.m_293111_(RenderTypes.SOLID, x, y, z, viewMatrix, projectionMatrix);
            Shaders.checkGLError("shadow terrain solid");
            GlStateManager.enableAlphaTest();
            renderGlobal.m_293111_(RenderTypes.CUTOUT_MIPPED, x, y, z, viewMatrix, projectionMatrix);
            Shaders.checkGLError("shadow terrain cutoutmipped");
            mc.m_91097_().m_118506_(TextureAtlas.f_118259_).m_117960_(false, false);
            renderGlobal.m_293111_(RenderTypes.CUTOUT, x, y, z, viewMatrix, projectionMatrix);
            mc.m_91097_().m_118506_(TextureAtlas.f_118259_).restoreLastBlurMipmap();
            Shaders.checkGLError("shadow terrain cutout");
         }

         mc.m_91307_().m_6182_("shadow entities");
         countEntitiesRenderedShadow = 0;
         countTileEntitiesRenderedShadow = 0;
         TickRateManager tickRateManager = mc.f_91073_.m_304826_();
         float frozenPartialTicks = tickRateManager.m_305915_() ? partialTicks : 1.0F;
         LevelRenderer wr = mc.f_91060_;
         EntityRenderDispatcher renderManager = mc.m_91290_();
         MultiBufferSource.BufferSource irendertypebuffer = wr.getRenderTypeTextures().m_110104_();
         boolean playerShadowPass = Shaders.isShadowPass && !mc.f_91074_.m_5833_();
         int minWorldY = mc.f_91073_.m_141937_();
         int maxWorldY = mc.f_91073_.m_151558_();
         Iterable entities = Shaders.isRenderShadowEntities() ? Shaders.getCurrentWorld().m_104735_() : Collections.EMPTY_LIST;
         Iterator var28 = ((Iterable)entities).iterator();

         while(true) {
            Entity entity;
            do {
               do {
                  do {
                     do {
                        if (!var28.hasNext()) {
                           Collection entityLists = mapEntityLists.values();
                           Iterator var42 = entityLists.iterator();

                           while(var42.hasNext()) {
                              List entityList = (List)var42.next();
                              Iterator var46 = entityList.iterator();

                              while(var46.hasNext()) {
                                 Entity entity = (Entity)var46.next();
                                 ++countEntitiesRenderedShadow;
                                 Shaders.nextEntity(entity);
                                 float entityPartialTicks = tickRateManager.m_305579_(entity) ? frozenPartialTicks : partialTicks;
                                 wr.m_109517_(entity, x, y, z, entityPartialTicks, matrixStack, irendertypebuffer);
                              }

                              entityList.clear();
                           }

                           irendertypebuffer.m_173043_();
                           wr.m_109588_(matrixStack);
                           irendertypebuffer.m_109912_(RenderType.m_110446_(TextureAtlas.f_118259_));
                           irendertypebuffer.m_109912_(RenderType.m_110452_(TextureAtlas.f_118259_));
                           irendertypebuffer.m_109912_(RenderType.m_110458_(TextureAtlas.f_118259_));
                           irendertypebuffer.m_109912_(RenderType.m_110476_(TextureAtlas.f_118259_));
                           Shaders.endEntities();
                           Shaders.beginBlockEntities();
                           SignRenderer.updateTextRenderDistance();
                           boolean forgeRenderBoundingBox = Reflector.IForgeBlockEntity_getRenderBoundingBox.exists();
                           Frustum camera = frustum;
                           float blockEntityPartialTicks = tickRateManager.m_306363_() ? frozenPartialTicks : partialTicks;
                           List renderInfosTileEntities = Shaders.isRenderShadowBlockEntities() ? wr.getRenderInfosTileEntities() : Collections.EMPTY_LIST;
                           Iterator var49 = renderInfosTileEntities.iterator();

                           label125:
                           while(true) {
                              List list;
                              do {
                                 if (!var49.hasNext()) {
                                    wr.m_109588_(matrixStack);
                                    irendertypebuffer.m_109912_(RenderType.m_110451_());
                                    irendertypebuffer.m_109912_(Sheets.m_110789_());
                                    irendertypebuffer.m_109912_(Sheets.m_110790_());
                                    irendertypebuffer.m_109912_(Sheets.m_110785_());
                                    irendertypebuffer.m_109912_(Sheets.m_110786_());
                                    irendertypebuffer.m_109912_(Sheets.m_110787_());
                                    irendertypebuffer.m_109912_(Sheets.m_110788_());
                                    irendertypebuffer.m_109911_();
                                    Shaders.endBlockEntities();
                                    Lagometer.timerTerrain.end();
                                    Shaders.checkGLError("shadow entities");
                                    GlStateManager._depthMask(true);
                                    GlStateManager._disableBlend();
                                    GlStateManager.unlockCull();
                                    GlStateManager._enableCull();
                                    GlStateManager._blendFuncSeparate(770, 771, 1, 0);
                                    GlStateManager.alphaFunc(516, 0.1F);
                                    if (Shaders.usedShadowDepthBuffers >= 2) {
                                       GlStateManager._activeTexture(33989);
                                       Shaders.checkGLError("pre copy shadow depth");
                                       GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
                                       Shaders.checkGLError("copy shadow depth");
                                       GlStateManager._activeTexture(33984);
                                    }

                                    GlStateManager._disableBlend();
                                    GlStateManager._depthMask(true);
                                    mc.m_91097_().m_174784_(TextureAtlas.f_118259_);
                                    Shaders.checkGLError("shadow pre-translucent");
                                    Shaders.sfb.setDrawBuffers();
                                    Shaders.checkGLError("shadow drawbuffers pre-translucent");
                                    Shaders.checkFramebufferStatus("shadow pre-translucent");
                                    if (Shaders.isRenderShadowTranslucent()) {
                                       Lagometer.timerTerrain.start();
                                       mc.m_91307_().m_6182_("shadow translucent");
                                       renderGlobal.m_293111_(RenderTypes.TRANSLUCENT, x, y, z, viewMatrix, projectionMatrix);
                                       Shaders.checkGLError("shadow translucent");
                                       Lagometer.timerTerrain.end();
                                    }

                                    GlStateManager.unlockBlend();
                                    GlStateManager._depthMask(true);
                                    GlStateManager._enableCull();
                                    GlStateManager._disableBlend();
                                    GL30.glFlush();
                                    Shaders.checkGLError("shadow flush");
                                    Shaders.isShadowPass = false;
                                    renderGlobal.setShadowRenderInfos(false);
                                    mc.m_91307_().m_6182_("shadow postprocess");
                                    if (Shaders.hasGlGenMipmap) {
                                       Shaders.sfb.generateDepthMipmaps(Shaders.shadowMipmapEnabled);
                                       Shaders.sfb.generateColorMipmaps(true, Shaders.shadowColorMipmapEnabled);
                                    }

                                    Shaders.checkGLError("shadow postprocess");
                                    if (Shaders.hasShadowcompPrograms) {
                                       Shaders.renderShadowComposites();
                                    }

                                    Shaders.dfb.bindFramebuffer();
                                    GlStateManager._viewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
                                    GlState.setDrawBuffers((DrawBuffers)null);
                                    mc.m_91097_().m_174784_(TextureAtlas.f_118259_);
                                    Shaders.useProgram(Shaders.ProgramTerrain);
                                    RenderSystem.getModelViewStack().popMatrix();
                                    RenderSystem.applyModelViewMatrix();
                                    RenderSystem.setProjectionMatrix(projectionPrev, vertexSortingPrev);
                                    Shaders.checkGLError("shadow end");
                                    return;
                                 }

                                 SectionRenderDispatcher.RenderSection worldrenderer$localrenderinformationcontainer = (SectionRenderDispatcher.RenderSection)var49.next();
                                 list = worldrenderer$localrenderinformationcontainer.m_293175_().m_293674_();
                              } while(list.isEmpty());

                              Iterator var36 = list.iterator();

                              while(true) {
                                 BlockEntity tileentity1;
                                 AABB aabb;
                                 do {
                                    if (!var36.hasNext()) {
                                       continue label125;
                                    }

                                    tileentity1 = (BlockEntity)var36.next();
                                    if (!forgeRenderBoundingBox) {
                                       break;
                                    }

                                    aabb = (AABB)Reflector.call(tileentity1, Reflector.IForgeBlockEntity_getRenderBoundingBox);
                                 } while(aabb != null && !camera.m_113029_(aabb));

                                 ++countTileEntitiesRenderedShadow;
                                 Shaders.nextBlockEntity(tileentity1);
                                 BlockPos blockpos3 = tileentity1.m_58899_();
                                 matrixStack.m_85836_();
                                 matrixStack.m_85837_((double)blockpos3.m_123341_() - x, (double)blockpos3.m_123342_() - y, (double)blockpos3.m_123343_() - z);
                                 mc.m_167982_().m_112267_(tileentity1, blockEntityPartialTicks, matrixStack, irendertypebuffer);
                                 matrixStack.m_85849_();
                              }
                           }
                        }

                        entity = (Entity)var28.next();
                     } while(!wr.shouldRenderEntity(entity, minWorldY, maxWorldY));
                  } while(!renderManager.m_114397_(entity, frustum, x, y, z) && !entity.m_20367_(mc.f_91074_));
               } while(entity == activeRenderInfo.m_90592_() && !playerShadowPass && !activeRenderInfo.m_90594_() && (!(activeRenderInfo.m_90592_() instanceof LivingEntity) || !((LivingEntity)activeRenderInfo.m_90592_()).m_5803_()));
            } while(entity instanceof LocalPlayer && activeRenderInfo.m_90592_() != entity);

            String key = entity.getClass().getName();
            List listEntities = (List)mapEntityLists.get(key);
            if (listEntities == null) {
               listEntities = new ArrayList();
               mapEntityLists.put(key, listEntities);
            }

            ((List)listEntities).add(entity);
         }
      }
   }

   public static void applyFrustumShadow(LevelRenderer renderGlobal, Frustum frustum) {
      Minecraft mc = Config.getMinecraft();
      mc.m_91307_().m_6180_("apply_shadow_frustum");
      int shadowRenderDistance = (int)Shaders.getShadowRenderDistance();
      int farPlaneDistance = (int)Config.getGameRenderer().m_109152_();
      boolean checkDistance = shadowRenderDistance > 0 && shadowRenderDistance < farPlaneDistance;
      int maxChunkDist = checkDistance ? shadowRenderDistance : -1;
      if (frustumTerrainShadowChanged || renderGlobal.needsFrustumUpdate()) {
         renderGlobal.applyFrustum(frustum, false, maxChunkDist);
         frustumTerrainShadowChanged = false;
      }

      if (frustumEntitiesShadowChanged || mc.f_91073_.getSectionStorage().isUpdated()) {
         renderGlobal.applyFrustumEntities(frustum, maxChunkDist);
         frustumEntitiesShadowChanged = false;
      }

      mc.m_91307_().m_7238_();
   }

   public static Frustum makeShadowFrustum(Camera camera, float partialTicks) {
      if (!Shaders.isShadowCulling()) {
         return new ClippingHelperDummy();
      } else {
         Minecraft mc = Config.getMinecraft();
         GameRenderer gameRenderer = Config.getGameRenderer();
         PoseStack matrixStackIn = new PoseStack();
         if (Reflector.ForgeHooksClient_onCameraSetup.exists()) {
            Object cameraSetup = Reflector.ForgeHooksClient_onCameraSetup.call(gameRenderer, camera, partialTicks);
            float cameraSetupYaw = Reflector.callFloat(cameraSetup, Reflector.ViewportEvent_ComputeCameraAngles_getYaw);
            float cameraSetupPitch = Reflector.callFloat(cameraSetup, Reflector.ViewportEvent_ComputeCameraAngles_getPitch);
            float cameraSetupRoll = Reflector.callFloat(cameraSetup, Reflector.ViewportEvent_ComputeCameraAngles_getRoll);
            camera.setAnglesInternal(cameraSetupYaw, cameraSetupPitch);
            matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(cameraSetupRoll));
         }

         matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(camera.m_90589_()));
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(camera.m_90590_() + 180.0F));
         double fov = gameRenderer.m_109141_(camera, partialTicks, true);
         double fovProjection = Math.max(fov, (double)(Integer)mc.f_91066_.m_231837_().m_231551_());
         Matrix4f matrixProjection = gameRenderer.m_253088_(fovProjection);
         Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
         Vec3 pos = camera.m_90583_();
         double x = pos.m_7096_();
         double y = pos.m_7098_();
         double z = pos.m_7094_();
         Frustum frustum = new ShadowFrustum(matrix4f, matrixProjection);
         frustum.m_113002_(x, y, z);
         return frustum;
      }
   }

   public static void updateActiveRenderInfo(Camera activeRenderInfo, Minecraft mc, float partialTicks) {
      activeRenderInfo.m_90575_(mc.f_91073_, (Entity)(mc.m_91288_() == null ? mc.f_91074_ : mc.m_91288_()), !mc.f_91066_.m_92176_().m_90612_(), mc.f_91066_.m_92176_().m_90613_(), partialTicks);
   }

   public static void preRenderChunkLayer(RenderType blockLayerIn) {
      if (blockLayerIn == RenderTypes.SOLID) {
         beginTerrainSolid();
      }

      if (blockLayerIn == RenderTypes.CUTOUT_MIPPED) {
         beginTerrainCutoutMipped();
      }

      if (blockLayerIn == RenderTypes.CUTOUT) {
         beginTerrainCutout();
      }

      if (blockLayerIn == RenderTypes.TRANSLUCENT) {
         beginTranslucent();
      }

      if (blockLayerIn == RenderType.m_110503_()) {
         beginTripwire();
      }

      if (Shaders.isRenderBackFace(blockLayerIn)) {
         GlStateManager._disableCull();
      }

   }

   public static void postRenderChunkLayer(RenderType blockLayerIn) {
      if (Shaders.isRenderBackFace(blockLayerIn)) {
         GlStateManager._enableCull();
      }

   }

   public static void preRender(RenderType renderType) {
      if (Shaders.isRenderingWorld) {
         if (!Shaders.isShadowPass) {
            if (renderType.isGlint()) {
               renderEnchantedGlintBegin();
            } else if (renderType.getName().equals("eyes")) {
               Shaders.beginSpiderEyes();
            } else if (renderType.getName().equals("crumbling")) {
               beginBlockDamage();
            } else if (renderType != RenderType.f_110371_ && renderType != RenderType.f_173152_) {
               if (renderType == RenderType.m_110478_()) {
                  Shaders.beginWaterMask();
               } else if (renderType.getName().equals("beacon_beam")) {
                  Shaders.beginBeacon();
               }
            } else {
               Shaders.beginLines();
            }

         }
      }
   }

   public static void postRender(RenderType renderType) {
      if (Shaders.isRenderingWorld) {
         if (!Shaders.isShadowPass) {
            if (renderType.isGlint()) {
               renderEnchantedGlintEnd();
            } else if (renderType.getName().equals("eyes")) {
               Shaders.endSpiderEyes();
            } else if (renderType.getName().equals("crumbling")) {
               endBlockDamage();
            } else if (renderType != RenderType.f_110371_ && renderType != RenderType.f_173152_) {
               if (renderType == RenderType.m_110478_()) {
                  Shaders.endWaterMask();
               } else if (renderType.getName().equals("beacon_beam")) {
                  Shaders.endBeacon();
               }
            } else {
               Shaders.endLines();
            }

         }
      }
   }

   public static void enableArrayPointerVbo() {
      GL20.glEnableVertexAttribArray(Shaders.midBlockAttrib);
      GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
      GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
      GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
   }

   public static void setupArrayPointersVbo() {
      int vertexSizeI = true;
      enableArrayPointerVbo();
      GL20.glVertexAttribPointer(Shaders.midBlockAttrib, 3, 5120, false, 72, 32L);
      GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 72, 36L);
      GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 72, 44L);
      GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 72, 52L);
   }

   public static void beaconBeamBegin() {
      Shaders.useProgram(Shaders.ProgramBeaconBeam);
   }

   public static void beaconBeamStartQuad1() {
   }

   public static void beaconBeamStartQuad2() {
   }

   public static void beaconBeamDraw1() {
   }

   public static void beaconBeamDraw2() {
      GlStateManager._disableBlend();
   }

   public static void renderEnchantedGlintBegin() {
      Shaders.useProgram(Shaders.ProgramArmorGlint);
   }

   public static void renderEnchantedGlintEnd() {
      if (Shaders.isRenderingWorld) {
         if (Shaders.isRenderingFirstPersonHand() && Shaders.isRenderBothHands()) {
            Shaders.useProgram(Shaders.ProgramHand);
         } else {
            Shaders.useProgram(Shaders.ProgramEntities);
         }
      } else {
         Shaders.useProgram(Shaders.ProgramNone);
      }

   }

   public static boolean renderEndPortal(TheEndPortalBlockEntity te, float partialTicks, float offset, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      if (!Shaders.isShadowPass && Shaders.activeProgram.getId() == 0) {
         return false;
      } else {
         PoseStack.Pose matrixEntry = matrixStackIn.m_85850_();
         Matrix4f matrix = matrixEntry.m_252922_();
         Matrix3f matrixNormal = matrixEntry.m_252943_();
         VertexConsumer bufferbuilder = bufferIn.m_6299_(RenderType.m_110446_(END_PORTAL_TEXTURE));
         float col = 0.5F;
         float r = col * 0.15F;
         float g = col * 0.3F;
         float b = col * 0.4F;
         float u0 = 0.0F;
         float u1 = 0.2F;
         float du = (float)(System.currentTimeMillis() % 100000L) / 100000.0F;
         float x = 0.0F;
         float y = 0.0F;
         float z = 0.0F;
         Vec3i vec3i;
         float xv;
         float yv;
         float zv;
         float xn;
         float yn;
         float zn;
         if (te.m_6665_(Direction.SOUTH)) {
            vec3i = Direction.SOUTH.m_122436_();
            xv = (float)vec3i.m_123341_();
            yv = (float)vec3i.m_123342_();
            zv = (float)vec3i.m_123343_();
            xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.m_339083_(matrix, x, y, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y + 1.0F, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x, y + 1.0F, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
         }

         if (te.m_6665_(Direction.NORTH)) {
            vec3i = Direction.NORTH.m_122436_();
            xv = (float)vec3i.m_123341_();
            yv = (float)vec3i.m_123342_();
            zv = (float)vec3i.m_123343_();
            xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.m_339083_(matrix, x, y + 1.0F, z).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y + 1.0F, z).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y, z).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x, y, z).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
         }

         if (te.m_6665_(Direction.EAST)) {
            vec3i = Direction.EAST.m_122436_();
            xv = (float)vec3i.m_123341_();
            yv = (float)vec3i.m_123342_();
            zv = (float)vec3i.m_123343_();
            xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y + 1.0F, z).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y + 1.0F, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y, z).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
         }

         if (te.m_6665_(Direction.WEST)) {
            vec3i = Direction.WEST.m_122436_();
            xv = (float)vec3i.m_123341_();
            yv = (float)vec3i.m_123342_();
            zv = (float)vec3i.m_123343_();
            xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.m_339083_(matrix, x, y, z).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x, y, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x, y + 1.0F, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x, y + 1.0F, z).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
         }

         if (te.m_6665_(Direction.DOWN)) {
            vec3i = Direction.DOWN.m_122436_();
            xv = (float)vec3i.m_123341_();
            yv = (float)vec3i.m_123342_();
            zv = (float)vec3i.m_123343_();
            xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.m_339083_(matrix, x, y, z).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y, z).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x, y, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
         }

         if (te.m_6665_(Direction.field_61)) {
            vec3i = Direction.field_61.m_122436_();
            xv = (float)vec3i.m_123341_();
            yv = (float)vec3i.m_123342_();
            zv = (float)vec3i.m_123343_();
            xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.m_339083_(matrix, x, y + offset, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y + offset, z + 1.0F).m_340057_(r, g, b, 1.0F).m_167083_(u0 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x + 1.0F, y + offset, z).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u1 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
            bufferbuilder.m_339083_(matrix, x, y + offset, z).m_340057_(r, g, b, 1.0F).m_167083_(u1 + du, u0 + du).m_338943_(combinedOverlayIn).m_338973_(combinedLightIn).m_338525_(xn, yn, zn);
         }

         return true;
      }
   }
}
