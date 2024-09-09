package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;

public final class C_4473_ {
   private static final int f_174688_ = 16;
   private static final int f_174689_ = 16;
   private static final String f_174690_ = "missingno";
   private static final C_5265_ f_118059_ = C_5265_.m_340282_("missingno");
   private static final C_212950_ f_290987_;
   @Nullable
   private static C_4470_ f_118060_;

   private static C_3148_ m_245315_(int widthIn, int heightIn) {
      C_3148_ nativeimage = new C_3148_(widthIn, heightIn, false);
      int i = -16777216;
      int j = -524040;

      for(int k = 0; k < heightIn; ++k) {
         for(int l = 0; l < widthIn; ++l) {
            if (k < heightIn / 2 ^ l < widthIn / 2) {
               nativeimage.m_84988_(l, k, -524040);
            } else {
               nativeimage.m_84988_(l, k, -16777216);
            }
         }
      }

      return nativeimage;
   }

   public static C_243582_ m_246104_() {
      C_3148_ nativeimage = m_245315_(16, 16);
      return new C_243582_(f_118059_, new C_243504_(16, 16), nativeimage, f_290987_);
   }

   public static C_5265_ m_118071_() {
      return f_118059_;
   }

   public static C_4470_ m_118080_() {
      if (f_118060_ == null) {
         C_3148_ nativeimage = m_245315_(16, 16);
         nativeimage.m_85123_();
         f_118060_ = new C_4470_(nativeimage);
         C_3391_.m_91087_().m_91097_().m_118495_(f_118059_, f_118060_);
      }

      return f_118060_;
   }

   public static boolean isMisingSprite(C_4486_ sprite) {
      return sprite.getName() == f_118059_;
   }

   static {
      f_290987_ = (new C_212950_.C_290050_()).m_294003_(C_4518_.f_119011_, new C_4518_(ImmutableList.of(new C_4517_(0, -1)), 16, 16, 1, false)).m_293106_();
   }
}
