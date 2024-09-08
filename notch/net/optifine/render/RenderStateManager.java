package net.optifine.render;

import java.util.Arrays;
import java.util.List;
import net.minecraft.src.C_4149_;
import net.minecraft.src.C_4168_;

public class RenderStateManager {
   private static boolean cacheEnabled;
   private static final C_4149_[] PENDING_CLEAR_STATES = new C_4149_[C_4168_.getCountRenderStates()];

   public static void setupRenderStates(List<C_4149_> renderStates) {
      if (cacheEnabled) {
         setupCached(renderStates);
      } else {
         for (int i = 0; i < renderStates.size(); i++) {
            C_4149_ renderState = (C_4149_)renderStates.get(i);
            renderState.m_110185_();
         }
      }
   }

   public static void clearRenderStates(List<C_4149_> renderStates) {
      if (cacheEnabled) {
         clearCached(renderStates);
      } else {
         for (int i = 0; i < renderStates.size(); i++) {
            C_4149_ renderState = (C_4149_)renderStates.get(i);
            renderState.m_110188_();
         }
      }
   }

   private static void setupCached(List<C_4149_> renderStates) {
      for (int i = 0; i < renderStates.size(); i++) {
         C_4149_ state = (C_4149_)renderStates.get(i);
         setupCached(state, i);
      }
   }

   private static void clearCached(List<C_4149_> renderStates) {
      for (int i = 0; i < renderStates.size(); i++) {
         C_4149_ state = (C_4149_)renderStates.get(i);
         clearCached(state, i);
      }
   }

   private static void setupCached(C_4149_ state, int index) {
      C_4149_ pendingClearState = PENDING_CLEAR_STATES[index];
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

   private static void clearCached(C_4149_ state, int index) {
      C_4149_ pendingClearState = PENDING_CLEAR_STATES[index];
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
            C_4149_ pendingClearState = PENDING_CLEAR_STATES[i];
            if (pendingClearState != null) {
               pendingClearState.m_110188_();
            }
         }

         Arrays.fill(PENDING_CLEAR_STATES, null);
      }
   }
}
