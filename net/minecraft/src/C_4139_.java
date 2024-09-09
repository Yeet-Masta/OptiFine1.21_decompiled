package net.minecraft.src;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMaps;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;
import javax.annotation.Nullable;
import net.optifine.SmartAnimations;
import net.optifine.render.IBufferSourceListener;
import net.optifine.render.VertexBuilderDummy;
import net.optifine.util.TextureUtils;

public interface C_4139_ {
   static C_4140_ m_109898_(C_336589_ builderIn) {
      return m_109900_(Object2ObjectSortedMaps.emptyMap(), builderIn);
   }

   static C_4140_ m_109900_(SequencedMap mapBuildersIn, C_336589_ builderIn) {
      return new C_4140_(builderIn, mapBuildersIn);
   }

   C_3187_ m_6299_(C_4168_ var1);

   default void flushRenderBuffers() {
   }

   default void flushCache() {
   }

   public static class C_4140_ implements C_4139_ {
      protected final C_336589_ f_336798_;
      protected final SequencedMap f_109905_;
      protected final Map f_337045_ = new HashMap();
      @Nullable
      protected C_4168_ f_336667_;
      private final C_3187_ DUMMY_BUFFER = new VertexBuilderDummy(this);
      private List listeners = new ArrayList(4);
      private int maxCachedBuffers = 0;
      private Object2ObjectLinkedOpenHashMap cachedBuffers = new Object2ObjectLinkedOpenHashMap();
      private Deque freeBufferBuilders = new ArrayDeque();

      protected C_4140_(C_336589_ bufferIn, SequencedMap fixedBuffersIn) {
         this.f_336798_ = bufferIn;
         this.f_109905_ = new Object2ObjectLinkedOpenHashMap(fixedBuffersIn);
      }

      public C_3187_ m_6299_(C_4168_ renderTypeIn) {
         this.addCachedBuffer(renderTypeIn);
         C_3173_ bufferbuilder = (C_3173_)this.f_337045_.get(renderTypeIn);
         if (bufferbuilder != null && !renderTypeIn.m_234326_()) {
            this.m_338789_(renderTypeIn, bufferbuilder);
            bufferbuilder = null;
         }

         if (bufferbuilder != null) {
            return (C_3187_)(renderTypeIn.getTextureLocation() == TextureUtils.LOCATION_TEXTURE_EMPTY ? this.DUMMY_BUFFER : bufferbuilder);
         } else {
            C_336589_ bytebufferbuilder = (C_336589_)this.f_109905_.get(renderTypeIn);
            if (bytebufferbuilder != null) {
               bufferbuilder = new C_3173_(bytebufferbuilder, renderTypeIn.m_173186_(), renderTypeIn.m_110508_(), renderTypeIn);
            } else {
               if (this.f_336667_ != null) {
                  this.m_109912_(this.f_336667_);
               }

               bufferbuilder = new C_3173_(this.f_336798_, renderTypeIn.m_173186_(), renderTypeIn.m_110508_(), renderTypeIn);
               this.f_336667_ = renderTypeIn;
            }

            this.f_337045_.put(renderTypeIn, bufferbuilder);
            bufferbuilder.setRenderTypeBuffer(this);
            return (C_3187_)(renderTypeIn.getTextureLocation() == TextureUtils.LOCATION_TEXTURE_EMPTY ? this.DUMMY_BUFFER : bufferbuilder);
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
               Iterator var1 = this.f_109905_.keySet().iterator();

               while(var1.hasNext()) {
                  C_4168_ rendertype = (C_4168_)var1.next();
                  this.m_109912_(rendertype);
                  if (this.f_337045_.isEmpty()) {
                     break;
                  }
               }

            }
         }
      }

      public void m_109912_(C_4168_ renderTypeIn) {
         C_3173_ bufferbuilder = (C_3173_)this.f_337045_.remove(renderTypeIn);
         if (bufferbuilder != null) {
            this.m_338789_(renderTypeIn, bufferbuilder);
         }

      }

      private void m_338789_(C_4168_ renderTypeIn, C_3173_ bufferIn) {
         this.fireFinish(renderTypeIn, bufferIn);
         C_336471_ meshdata = bufferIn.m_339970_();
         if (meshdata != null) {
            if (renderTypeIn.m_340332_()) {
               C_336589_ bytebufferbuilder = (C_336589_)this.f_109905_.getOrDefault(renderTypeIn, this.f_336798_);
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

      public C_3187_ getBuffer(C_5265_ textureLocation, C_3187_ bufferIn) {
         C_4168_ renderType = bufferIn.getRenderType();
         if (!(renderType instanceof C_4168_.C_4170_)) {
            return bufferIn;
         } else {
            textureLocation = C_4168_.getCustomTexture(textureLocation);
            C_4168_.C_4170_ type = (C_4168_.C_4170_)renderType;
            C_4168_.C_4170_ typeTex = type.getTextured(textureLocation);
            C_3187_ buffer = this.m_6299_(typeTex);
            return buffer;
         }
      }

      public C_4168_ getLastRenderType() {
         return this.f_336667_;
      }

      public C_3173_ getStartedBuffer(C_4168_ renderType) {
         return (C_3173_)this.f_337045_.get(renderType);
      }

      public void flushRenderBuffers() {
         C_4168_ oldRenderType = this.f_336667_;
         C_3173_ oldBufferBuilder = (C_3173_)this.f_337045_.get(oldRenderType);
         this.m_109911_();
         this.restoreRenderState(oldRenderType, oldBufferBuilder);
      }

      public void restoreRenderState(C_4168_ renderTypeIn, C_3173_ bufferBuilderIn) {
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

      private void fireFinish(C_4168_ renderTypeIn, C_3173_ bufferIn) {
         for(int i = 0; i < this.listeners.size(); ++i) {
            IBufferSourceListener bsl = (IBufferSourceListener)this.listeners.get(i);
            bsl.finish(renderTypeIn, bufferIn);
         }

      }

      public C_3187_ getDummyBuffer() {
         return this.DUMMY_BUFFER;
      }

      public void enableCache() {
      }

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

      private void addCachedBuffer(C_4168_ rt) {
         if (this.maxCachedBuffers > 0) {
            this.cachedBuffers.getAndMoveToLast(rt);
            if (!this.f_109905_.containsKey(rt)) {
               if (this.shouldCache(rt)) {
                  this.trimCachedBuffers();
                  C_3173_ bb = (C_3173_)this.freeBufferBuilders.pollLast();
                  this.cachedBuffers.put(rt, bb);
               }
            }
         }
      }

      private boolean shouldCache(C_4168_ rt) {
         C_5265_ locTex = rt.getTextureLocation();
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
            } else if (path.startsWith("textures/entity/villager/")) {
               return false;
            } else {
               return !path.startsWith("textures/entity/warden/");
            }
         }
      }

      private void trimCachedBuffers() {
         while(this.cachedBuffers.size() > this.maxCachedBuffers) {
            C_4168_ rt = (C_4168_)this.cachedBuffers.firstKey();
            if (rt == this.f_336667_) {
               return;
            }

            this.removeCachedBuffer(rt);
         }

      }

      private void removeCachedBuffer(C_4168_ rt) {
      }
   }
}
