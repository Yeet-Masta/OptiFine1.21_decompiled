package net.minecraft.client.gui;

import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.MapDecorationTextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.joml.Matrix4f;

public class MapRenderer implements AutoCloseable {
   private static final int f_168763_ = 128;
   private static final int f_168764_ = 128;
   final net.minecraft.client.renderer.texture.TextureManager f_93255_;
   final MapDecorationTextureManager f_316783_;
   private final Int2ObjectMap<net.minecraft.client.gui.MapRenderer.MapInstance> f_93256_ = new Int2ObjectOpenHashMap();

   public MapRenderer(net.minecraft.client.renderer.texture.TextureManager textureManagerIn, MapDecorationTextureManager decoTexIn) {
      this.f_93255_ = textureManagerIn;
      this.f_316783_ = decoTexIn;
   }

   public void m_168765_(MapId mapIdIn, MapItemSavedData dataIn) {
      this.m_168778_(mapIdIn, dataIn).m_182566_();
   }

   public void m_168771_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      MapId idIn,
      MapItemSavedData dataIn,
      boolean skipDecorationsIn,
      int combinedLightIn
   ) {
      this.m_168778_(idIn, dataIn).m_93291_(matrixStackIn, bufferIn, skipDecorationsIn, combinedLightIn);
   }

   private net.minecraft.client.gui.MapRenderer.MapInstance m_168778_(MapId idIn, MapItemSavedData dataIn) {
      return (net.minecraft.client.gui.MapRenderer.MapInstance)this.f_93256_.compute(idIn.f_315413_(), (idIn2, mapIn2) -> {
         if (mapIn2 == null) {
            return new net.minecraft.client.gui.MapRenderer.MapInstance(idIn2, dataIn);
         } else {
            mapIn2.m_182567_(dataIn);
            return mapIn2;
         }
      });
   }

   public void m_93260_() {
      ObjectIterator var1 = this.f_93256_.values().iterator();

      while (var1.hasNext()) {
         net.minecraft.client.gui.MapRenderer.MapInstance maprenderer$mapinstance = (net.minecraft.client.gui.MapRenderer.MapInstance)var1.next();
         maprenderer$mapinstance.close();
      }

      this.f_93256_.clear();
   }

   public void close() {
      this.m_93260_();
   }

   class MapInstance implements AutoCloseable {
      private MapItemSavedData f_93280_;
      private final net.minecraft.client.renderer.texture.DynamicTexture f_93281_;
      private final net.minecraft.client.renderer.RenderType f_93282_;
      private boolean f_182565_ = true;

      MapInstance(final int idIn, final MapItemSavedData dataIn) {
         this.f_93280_ = dataIn;
         this.f_93281_ = new net.minecraft.client.renderer.texture.DynamicTexture(128, 128, true);
         net.minecraft.resources.ResourceLocation resourcelocation = MapRenderer.this.f_93255_.m_118490_("map/" + idIn, this.f_93281_);
         this.f_93282_ = net.minecraft.client.renderer.RenderType.m_110452_(resourcelocation);
      }

      void m_182567_(MapItemSavedData dataIn) {
         boolean flag = this.f_93280_ != dataIn;
         this.f_93280_ = dataIn;
         this.f_182565_ |= flag;
      }

      public void m_182566_() {
         this.f_182565_ = true;
      }

      private void m_93290_() {
         for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
               int k = j + i * 128;
               this.f_93281_.m_117991_().m_84988_(j, i, net.minecraft.world.level.material.MapColor.m_284315_(this.f_93280_.f_77891_[k]));
            }
         }

         this.f_93281_.m_117985_();
      }

      void m_93291_(
         com.mojang.blaze3d.vertex.PoseStack matrixStackIn, net.minecraft.client.renderer.MultiBufferSource bufferIn, boolean skipDecorationsIn, int lightmapIn
      ) {
         if (this.f_182565_) {
            this.m_93290_();
            this.f_182565_ = false;
         }

         int i = 0;
         int j = 0;
         float f = 0.0F;
         Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
         com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = bufferIn.m_6299_(this.f_93282_);
         vertexconsumer.m_339083_(matrix4f, 0.0F, 128.0F, -0.01F)
            .m_338399_(-1)
            .m_167083_(0.0F, 1.0F)
            .m_338943_(OverlayTexture.f_118083_)
            .m_338973_(lightmapIn)
            .m_338525_(0.0F, 1.0F, 0.0F);
         vertexconsumer.m_339083_(matrix4f, 128.0F, 128.0F, -0.01F)
            .m_338399_(-1)
            .m_167083_(1.0F, 1.0F)
            .m_338943_(OverlayTexture.f_118083_)
            .m_338973_(lightmapIn)
            .m_338525_(0.0F, 1.0F, 0.0F);
         vertexconsumer.m_339083_(matrix4f, 128.0F, 0.0F, -0.01F)
            .m_338399_(-1)
            .m_167083_(1.0F, 0.0F)
            .m_338943_(OverlayTexture.f_118083_)
            .m_338973_(lightmapIn)
            .m_338525_(0.0F, 1.0F, 0.0F);
         vertexconsumer.m_339083_(matrix4f, 0.0F, 0.0F, -0.01F)
            .m_338399_(-1)
            .m_167083_(0.0F, 0.0F)
            .m_338943_(OverlayTexture.f_118083_)
            .m_338973_(lightmapIn)
            .m_338525_(0.0F, 1.0F, 0.0F);
         int k = 0;

         for (MapDecoration mapdecoration : this.f_93280_.m_164811_()) {
            if (!skipDecorationsIn || mapdecoration.m_77809_()) {
               matrixStackIn.m_85836_();
               matrixStackIn.m_252880_(0.0F + (float)mapdecoration.f_77792_() / 2.0F + 64.0F, 0.0F + (float)mapdecoration.f_77793_() / 2.0F + 64.0F, -0.02F);
               matrixStackIn.m_252781_(Axis.f_252403_.m_252977_((float)(mapdecoration.f_77794_() * 360) / 16.0F));
               matrixStackIn.m_85841_(4.0F, 4.0F, 3.0F);
               matrixStackIn.m_252880_(-0.125F, 0.125F, 0.0F);
               Matrix4f matrix4f1 = matrixStackIn.m_85850_().m_252922_();
               float f1 = -0.001F;
               net.minecraft.client.renderer.texture.TextureAtlasSprite textureatlassprite = MapRenderer.this.f_316783_.m_319490_(mapdecoration);
               float f2 = textureatlassprite.m_118409_();
               float f3 = textureatlassprite.m_118411_();
               float f4 = textureatlassprite.m_118410_();
               float f5 = textureatlassprite.m_118412_();
               com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer1 = bufferIn.m_6299_(
                  net.minecraft.client.renderer.RenderType.m_110497_(textureatlassprite.m_247685_())
               );
               vertexconsumer1.m_339083_(matrix4f1, -1.0F, 1.0F, (float)k * -0.001F)
                  .m_338399_(-1)
                  .m_167083_(f2, f3)
                  .m_338943_(OverlayTexture.f_118083_)
                  .m_338973_(lightmapIn)
                  .m_338525_(0.0F, 1.0F, 0.0F);
               vertexconsumer1.m_339083_(matrix4f1, 1.0F, 1.0F, (float)k * -0.001F)
                  .m_338399_(-1)
                  .m_167083_(f4, f3)
                  .m_338943_(OverlayTexture.f_118083_)
                  .m_338973_(lightmapIn)
                  .m_338525_(0.0F, 1.0F, 0.0F);
               vertexconsumer1.m_339083_(matrix4f1, 1.0F, -1.0F, (float)k * -0.001F)
                  .m_338399_(-1)
                  .m_167083_(f4, f5)
                  .m_338943_(OverlayTexture.f_118083_)
                  .m_338973_(lightmapIn)
                  .m_338525_(0.0F, 1.0F, 0.0F);
               vertexconsumer1.m_339083_(matrix4f1, -1.0F, -1.0F, (float)k * -0.001F)
                  .m_338399_(-1)
                  .m_167083_(f2, f5)
                  .m_338943_(OverlayTexture.f_118083_)
                  .m_338973_(lightmapIn)
                  .m_338525_(0.0F, 1.0F, 0.0F);
               matrixStackIn.m_85849_();
               if (mapdecoration.f_77795_().isPresent()) {
                  net.minecraft.client.gui.Font font = Minecraft.m_91087_().f_91062_;
                  Component component = (Component)mapdecoration.f_77795_().get();
                  float f6 = (float)font.m_92852_(component);
                  float f7 = net.minecraft.util.Mth.m_14036_(25.0F / f6, 0.0F, 0.6666667F);
                  matrixStackIn.m_85836_();
                  matrixStackIn.m_252880_(
                     0.0F + (float)mapdecoration.f_77792_() / 2.0F + 64.0F - f6 * f7 / 2.0F,
                     0.0F + (float)mapdecoration.f_77793_() / 2.0F + 64.0F + 4.0F,
                     -0.025F
                  );
                  matrixStackIn.m_85841_(f7, f7, 1.0F);
                  matrixStackIn.m_252880_(0.0F, 0.0F, -0.1F);
                  font.m_272077_(
                     component,
                     0.0F,
                     0.0F,
                     -1,
                     false,
                     matrixStackIn.m_85850_().m_252922_(),
                     bufferIn,
                     net.minecraft.client.gui.Font.DisplayMode.NORMAL,
                     Integer.MIN_VALUE,
                     lightmapIn
                  );
                  matrixStackIn.m_85849_();
               }

               k++;
            }
         }
      }

      public void close() {
         this.f_93281_.close();
      }
   }
}
