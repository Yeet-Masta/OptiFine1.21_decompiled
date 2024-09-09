package net.optifine.render;

import java.util.Arrays;
import java.util.List;

public class RenderStateManager {
   private static boolean cacheEnabled;
   private static final net.minecraft.client.renderer.RenderStateShard[] PENDING_CLEAR_STATES = new net.minecraft.client.renderer.RenderStateShard[net.minecraft.client.renderer.RenderType.getCountRenderStates()];

   public static void setupRenderStates(List<net.minecraft.client.renderer.RenderStateShard> renderStates) {
      if (cacheEnabled) {
         setupCached(renderStates);
      } else {
         for (int i = 0; i < renderStates.size(); i++) {
            net.minecraft.client.renderer.RenderStateShard renderState = (net.minecraft.client.renderer.RenderStateShard)renderStates.get(i);
            renderState.m_110185_();
         }
      }
   }

   public static void clearRenderStates(List<net.minecraft.client.renderer.RenderStateShard> renderStates) {
      if (cacheEnabled) {
         clearCached(renderStates);
      } else {
         for (int i = 0; i < renderStates.size(); i++) {
            net.minecraft.client.renderer.RenderStateShard renderState = (net.minecraft.client.renderer.RenderStateShard)renderStates.get(i);
            renderState.m_110188_();
         }
      }
   }

   private static void setupCached(List<net.minecraft.client.renderer.RenderStateShard> renderStates) {
      for (int i = 0; i < renderStates.size(); i++) {
         net.minecraft.client.renderer.RenderStateShard state = (net.minecraft.client.renderer.RenderStateShard)renderStates.get(i);
         setupCached(state, i);
      }
   }

   private static void clearCached(List<net.minecraft.client.renderer.RenderStateShard> renderStates) {
      for (int i = 0; i < renderStates.size(); i++) {
         net.minecraft.client.renderer.RenderStateShard state = (net.minecraft.client.renderer.RenderStateShard)renderStates.get(i);
         clearCached(state, i);
      }
   }

   private static void setupCached(net.minecraft.client.renderer.RenderStateShard state, int index) {
      net.minecraft.client.renderer.RenderStateShard pendingClearState = PENDING_CLEAR_STATES[index];
      if (pendingClearState != null) {
         if (state == pendingClearState) {
            PENDING_CLEAR_STATES[index] = null;
            return;
         }

         pendingClearState.m_110188_();
         PENDING_CLEAR_STATES[index] = null;
      }

      state.m_110185_();
   }

   private static void clearCached(net.minecraft.client.renderer.RenderStateShard state, int index) {
      net.minecraft.client.renderer.RenderStateShard pendingClearState = PENDING_CLEAR_STATES[index];
      if (pendingClearState != null) {
         pendingClearState.m_110188_();
      }

      PENDING_CLEAR_STATES[index] = state;
   }

   public static void enableCache() {
      if (!cacheEnabled) {
         cacheEnabled = true;
         Arrays.fill(PENDING_CLEAR_STATES, null);
      }
   }

   public static void flushCache() {
      if (cacheEnabled) {
         disableCache();
         enableCache();
      }
   }

   public static void disableCache() {
      if (cacheEnabled) {
         cacheEnabled = false;

         for (int i = 0; i < PENDING_CLEAR_STATES.length; i++) {
            net.minecraft.client.renderer.RenderStateShard pendingClearState = PENDING_CLEAR_STATES[i];
            if (pendingClearState != null) {
               pendingClearState.m_110188_();
            }
         }

         Arrays.fill(PENDING_CLEAR_STATES, null);
      }
   }
}
