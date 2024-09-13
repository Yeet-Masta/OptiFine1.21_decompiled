package net.optifine.shaders;

import java.util.Arrays;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.config.RenderScale;
import net.optifine.util.DynamicDimension;

public class Program {
   private int index;
   private String name;
   private ProgramStage programStage;
   private Program programBackup;
   private ComputeProgram[] computePrograms;
   private GlAlphaState alphaState;
   private GlBlendState blendState;
   private GlBlendState[] blendStatesColorIndexed;
   private RenderScale renderScale;
   private Boolean[] buffersFlip = new Boolean[16];
   private int f_11893_;
   private int ref;
   private String[] drawBufSettings;
   private DrawBuffers drawBuffers;
   private DrawBuffers drawBuffersCustom;
   private int compositeMipmapSetting;
   private int countInstances;
   private boolean[] toggleColorTextures = new boolean[16];
   private DynamicDimension drawSize;
   private GlBlendState[] blendStatesIndexed;

   public Program(int index, String name, ProgramStage programStage, Program programBackup) {
      this.index = index;
      this.name = name;
      this.programStage = programStage;
      this.programBackup = programBackup;
      this.computePrograms = new ComputeProgram[0];
   }

   public Program(int index, String name, ProgramStage programStage, boolean ownBackup) {
      this.index = index;
      this.name = name;
      this.programStage = programStage;
      this.programBackup = ownBackup ? this : null;
      this.computePrograms = new ComputeProgram[0];
   }

   public void resetProperties() {
      this.alphaState = null;
      this.blendState = null;
      this.blendStatesColorIndexed = null;
      this.renderScale = null;
      Arrays.fill(this.buffersFlip, null);
   }

   public void resetId() {
      this.f_11893_ = 0;
      this.ref = 0;
   }

   public void resetConfiguration() {
      this.drawBufSettings = null;
      this.compositeMipmapSetting = 0;
      this.countInstances = 0;
      Arrays.fill(this.toggleColorTextures, false);
      this.drawSize = null;
      this.blendStatesIndexed = null;
      if (this.drawBuffersCustom == null) {
         this.drawBuffersCustom = new DrawBuffers(this.name, 16, 8);
      }
   }

   public void copyFrom(Program p) {
      this.f_11893_ = p.getId();
      this.alphaState = p.getAlphaState();
      this.blendState = p.getBlendState();
      this.blendStatesColorIndexed = p.blendStatesColorIndexed;
      this.renderScale = p.getRenderScale();
      System.arraycopy(p.getBuffersFlip(), 0, this.buffersFlip, 0, this.buffersFlip.length);
      this.drawBufSettings = p.getDrawBufSettings();
      this.drawBuffers = p.getDrawBuffers();
      this.compositeMipmapSetting = p.getCompositeMipmapSetting();
      this.countInstances = p.getCountInstances();
      System.arraycopy(p.getToggleColorTextures(), 0, this.toggleColorTextures, 0, this.toggleColorTextures.length);
      this.blendStatesIndexed = p.blendStatesIndexed;
   }

   public int getIndex() {
      return this.index;
   }

   public String getName() {
      return this.name;
   }

   public ProgramStage getProgramStage() {
      return this.programStage;
   }

   public Program getProgramBackup() {
      return this.programBackup;
   }

   public int getId() {
      return this.f_11893_;
   }

   public int getRef() {
      return this.ref;
   }

   public String[] getDrawBufSettings() {
      return this.drawBufSettings;
   }

   public DrawBuffers getDrawBuffers() {
      return this.drawBuffers;
   }

   public DrawBuffers getDrawBuffersCustom() {
      return this.drawBuffersCustom;
   }

   public int getCompositeMipmapSetting() {
      return this.compositeMipmapSetting;
   }

   public int getCountInstances() {
      return this.countInstances;
   }

   public GlAlphaState getAlphaState() {
      return this.alphaState;
   }

   public GlBlendState getBlendState() {
      return this.blendState;
   }

   public GlBlendState[] getBlendStatesColorIndexed() {
      return this.blendStatesColorIndexed;
   }

   public GlBlendState[] getBlendStatesIndexed() {
      return this.blendStatesIndexed;
   }

   public RenderScale getRenderScale() {
      return this.renderScale;
   }

   public Boolean[] getBuffersFlip() {
      return this.buffersFlip;
   }

   public boolean[] getToggleColorTextures() {
      return this.toggleColorTextures;
   }

   public void setId(int id) {
      this.f_11893_ = id;
   }

   public void setRef(int ref) {
      this.ref = ref;
   }

   public void setDrawBufSettings(String[] drawBufSettings) {
      this.drawBufSettings = drawBufSettings;
   }

   public void setDrawBuffers(DrawBuffers drawBuffers) {
      this.drawBuffers = drawBuffers;
   }

   public void setCompositeMipmapSetting(int compositeMipmapSetting) {
      this.compositeMipmapSetting = compositeMipmapSetting;
   }

   public void setCountInstances(int countInstances) {
      this.countInstances = countInstances;
   }

   public void setAlphaState(GlAlphaState alphaState) {
      this.alphaState = alphaState;
   }

   public void setBlendState(GlBlendState blendState) {
      this.blendState = blendState;
   }

   public void setBlendStateColorIndexed(int index, GlBlendState blendState) {
      if (this.blendStatesColorIndexed == null) {
         this.blendStatesColorIndexed = new GlBlendState[index + 1];
      }

      if (this.blendStatesColorIndexed.length < index + 1) {
         GlBlendState[] bss = new GlBlendState[index + 1];
         System.arraycopy(this.blendStatesColorIndexed, 0, bss, 0, this.blendStatesColorIndexed.length);
         this.blendStatesColorIndexed = bss;
      }

      this.blendStatesColorIndexed[index] = blendState;
   }

   public void setBlendStateIndexed(int index, GlBlendState blendState) {
      if (this.blendStatesIndexed == null) {
         this.blendStatesIndexed = new GlBlendState[index + 1];
      }

      if (this.blendStatesIndexed.length < index + 1) {
         GlBlendState[] bss = new GlBlendState[index + 1];
         System.arraycopy(this.blendStatesIndexed, 0, bss, 0, this.blendStatesIndexed.length);
         this.blendStatesIndexed = bss;
      }

      this.blendStatesIndexed[index] = blendState;
   }

   public void setRenderScale(RenderScale renderScale) {
      this.renderScale = renderScale;
   }

   public String getRealProgramName() {
      if (this.f_11893_ == 0) {
         return "none";
      } else {
         Program p;
         for (p = this; p.getRef() != this.f_11893_; p = p.getProgramBackup()) {
            if (p.getProgramBackup() == null || p.getProgramBackup() == p) {
               return "unknown";
            }
         }

         return p.getName();
      }
   }

   public boolean hasCompositeMipmaps() {
      return this.compositeMipmapSetting != 0;
   }

   public DynamicDimension getDrawSize() {
      return this.drawSize;
   }

   public void setDrawSize(DynamicDimension drawSize) {
      this.drawSize = drawSize;
   }

   public ComputeProgram[] getComputePrograms() {
      return this.computePrograms;
   }

   public void setComputePrograms(ComputeProgram[] computePrograms) {
      this.computePrograms = computePrograms;
   }

   public String toString() {
      return "name: " + this.name + ", id: " + this.f_11893_ + ", ref: " + this.ref + ", real: " + this.getRealProgramName();
   }
}
