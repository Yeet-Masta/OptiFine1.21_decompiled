package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMaps;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.optifine.SmartAnimations;
import net.optifine.render.IBufferSourceListener;
import net.optifine.render.VertexBuilderDummy;
import net.optifine.util.TextureUtils;

public interface MultiBufferSource {
   static MultiBufferSource.BufferSource m_109898_(ByteBufferBuilder builderIn) {
      return m_109900_(Object2ObjectSortedMaps.emptyMap(), builderIn);
   }

   static MultiBufferSource.BufferSource m_109900_(SequencedMap<RenderType, ByteBufferBuilder> mapBuildersIn, ByteBufferBuilder builderIn) {
      return new MultiBufferSource.BufferSource(builderIn, mapBuildersIn);
   }

   VertexConsumer m_6299_(RenderType var1);

   default void flushRenderBuffers() {
   }

   default void flushCache() {
   }

   public static class BufferSource implements MultiBufferSource {
      protected ByteBufferBuilder f_336798_;
      protected SequencedMap<RenderType, ByteBufferBuilder> f_109905_;
      protected Map<RenderType, BufferBuilder> f_337045_ = new HashMap();
      @Nullable
      protected RenderType f_336667_;
      private VertexConsumer DUMMY_BUFFER = new VertexBuilderDummy(this);
      private List<IBufferSourceListener> listeners = new ArrayList(4);
      private int maxCachedBuffers = 0;
      private Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> cachedBuffers = new Object2ObjectLinkedOpenHashMap();
      private Deque<BufferBuilder> freeBufferBuilders = new ArrayDeque();

      protected BufferSource(ByteBufferBuilder bufferIn, SequencedMap<RenderType, ByteBufferBuilder> fixedBuffersIn) {
         this.f_336798_ = bufferIn;
         this.f_109905_ = new Object2ObjectLinkedOpenHashMap(fixedBuffersIn);
      }

      @Override
      public VertexConsumer m_6299_(RenderType renderTypeIn) {
         this.addCachedBuffer(renderTypeIn);
         BufferBuilder bufferbuilder = (BufferBuilder)this.f_337045_.get(renderTypeIn);
         if (bufferbuilder != null && !renderTypeIn.m_234326_()) {
            this.m_338789_(renderTypeIn, bufferbuilder);
            bufferbuilder = null;
         }

         if (bufferbuilder != null) {
            return (VertexConsumer)(renderTypeIn.getTextureLocation() == TextureUtils.LOCATION_TEXTURE_EMPTY ? this.DUMMY_BUFFER : bufferbuilder);
         } else {
            ByteBufferBuilder bytebufferbuilder = (ByteBufferBuilder)this.f_109905_.get(renderTypeIn);
            if (bytebufferbuilder != null) {
               bufferbuilder = new BufferBuilder(bytebufferbuilder, renderTypeIn.m_173186_(), renderTypeIn.m_110508_(), renderTypeIn);
            } else {
               if (this.f_336667_ != null) {
                  this.m_109912_(this.f_336667_);
               }

               bufferbuilder = new BufferBuilder(this.f_336798_, renderTypeIn.m_173186_(), renderTypeIn.m_110508_(), renderTypeIn);
               this.f_336667_ = renderTypeIn;
            }

            this.f_337045_.put(renderTypeIn, bufferbuilder);
            bufferbuilder.setRenderTypeBuffer(this);
            return (VertexConsumer)(renderTypeIn.getTextureLocation() == TextureUtils.LOCATION_TEXTURE_EMPTY ? this.DUMMY_BUFFER : bufferbuilder);
         }
      }

      public void m_173043_() {
         if (this.f_336667_ != null) {
            this.m_109912_(this.f_336667_);
            this.f_336667_ = null;
         }
      }

      public void m_109911_() {
         if (!this.f_337045_.isEmpty()) {
            this.m_173043_();
            if (!this.f_337045_.isEmpty()) {
               for (RenderType rendertype : this.f_109905_.keySet()) {
                  this.m_109912_(rendertype);
                  if (this.f_337045_.isEmpty()) {
                     break;
                  }
               }
            }
         }
      }

      public void m_109912_(RenderType renderTypeIn) {
         BufferBuilder bufferbuilder = (BufferBuilder)this.f_337045_.remove(renderTypeIn);
         if (bufferbuilder != null) {
            this.m_338789_(renderTypeIn, bufferbuilder);
         }
      }

      private void m_338789_(RenderType renderTypeIn, BufferBuilder bufferIn) {
         this.fireFinish(renderTypeIn, bufferIn);
         MeshData meshdata = bufferIn.m_339970_();
         if (meshdata != null) {
            if (renderTypeIn.m_340332_()) {
               ByteBufferBuilder bytebufferbuilder = (ByteBufferBuilder)this.f_109905_.getOrDefault(renderTypeIn, this.f_336798_);
               meshdata.m_338666_(bytebufferbuilder, RenderSystem.getVertexSorting());
            }

            if (bufferIn.animatedSprites != null) {
               SmartAnimations.spritesRendered(bufferIn.animatedSprites);
            }

            renderTypeIn.m_339876_(meshdata);
         }

         if (renderTypeIn.equals(this.f_336667_)) {
            this.f_336667_ = null;
         }
      }

      public VertexConsumer m_6299_(ResourceLocation textureLocation, VertexConsumer bufferIn) {
         RenderType renderType = bufferIn.getRenderType();
         if (!(renderType instanceof RenderType.CompositeRenderType)) {
            return bufferIn;
         } else {
            textureLocation = RenderType.getCustomTexture(textureLocation);
            RenderType.CompositeRenderType type = (RenderType.CompositeRenderType)renderType;
            RenderType.CompositeRenderType typeTex = type.getTextured(textureLocation);
            return this.m_6299_(typeTex);
         }
      }

      public RenderType getLastRenderType() {
         return this.f_336667_;
      }

      public BufferBuilder getStartedBuffer(RenderType renderType) {
         return (BufferBuilder)this.f_337045_.get(renderType);
      }

      @Override
      public void flushRenderBuffers() {
         RenderType oldRenderType = this.f_336667_;
         BufferBuilder oldBufferBuilder = (BufferBuilder)this.f_337045_.get(oldRenderType);
         this.m_109911_();
         this.restoreRenderState(oldRenderType, oldBufferBuilder);
      }

      public void restoreRenderState(RenderType renderTypeIn, BufferBuilder bufferBuilderIn) {
         if (renderTypeIn != null) {
            this.m_173043_();
            this.f_336667_ = renderTypeIn;
            if (bufferBuilderIn != null) {
               this.f_337045_.put(renderTypeIn, bufferBuilderIn);
            }
         }
      }

      public void addListener(IBufferSourceListener bsl) {
         this.listeners.add(bsl);
      }

      public boolean removeListener(IBufferSourceListener bsl) {
         return this.listeners.remove(bsl);
      }

      private void fireFinish(RenderType renderTypeIn, BufferBuilder bufferIn) {
         for (int i = 0; i < this.listeners.size(); i++) {
            IBufferSourceListener bsl = (IBufferSourceListener)this.listeners.get(i);
            bsl.m_185413_(renderTypeIn, bufferIn);
         }
      }

      public VertexConsumer getDummyBuffer() {
         return this.DUMMY_BUFFER;
      }

      public void enableCache() {
      }

      @Override
      public void flushCache() {
         int maxPrev = this.maxCachedBuffers;
         this.setMaxCachedBuffers(0);
         this.setMaxCachedBuffers(maxPrev);
      }

      public void disableCache() {
         this.setMaxCachedBuffers(0);
      }

      private void setMaxCachedBuffers(int maxCachedBuffers) {
         this.maxCachedBuffers = Math.max(maxCachedBuffers, 0);
         this.trimCachedBuffers();
      }

      private void addCachedBuffer(RenderType rt) {
         if (this.maxCachedBuffers > 0) {
            this.cachedBuffers.getAndMoveToLast(rt);
            if (!this.f_109905_.containsKey(rt)) {
               if (this.shouldCache(rt)) {
                  this.trimCachedBuffers();
                  BufferBuilder bb = (BufferBuilder)this.freeBufferBuilders.pollLast();
                  this.cachedBuffers.put(rt, bb);
               }
            }
         }
      }

      private boolean shouldCache(RenderType rt) {
         ResourceLocation locTex = rt.getTextureLocation();
         if (locTex == null) {
            return false;
         } else if (!rt.m_234326_()) {
            return false;
         } else {
            String path = locTex.m_135815_();
            if (path.startsWith("skins/")) {
               return false;
            } else if (path.startsWith("capes/")) {
               return false;
            } else if (path.startsWith("capeof/")) {
               return false;
            } else if (path.startsWith("textures/entity/horse/")) {
               return false;
            } else {
               return path.startsWith("textures/entity/villager/") ? false : !path.startsWith("textures/entity/warden/");
            }
         }
      }

      private void trimCachedBuffers() {
         while (this.cachedBuffers.size() > this.maxCachedBuffers) {
            RenderType rt = (RenderType)this.cachedBuffers.firstKey();
            if (rt == this.f_336667_) {
               return;
            }

            this.removeCachedBuffer(rt);
         }
      }

      private void removeCachedBuffer(RenderType rt) {
      }
   }
}
