package net.optifine.render;

import java.util.BitSet;
import net.optifine.shaders.SVertexBuilder;
import org.joml.Vector3f;

public class BufferBuilderCache {
   private net.minecraft.client.renderer.texture.TextureAtlasSprite[] quadSpritesPrev = null;
   private MultiTextureBuilder multiTextureBuilder = new MultiTextureBuilder();
   private SVertexBuilder sVertexBuilder = new SVertexBuilder();
   private RenderEnv renderEnv = new RenderEnv(null, null);
   private BitSet animatedSpritesCached = new BitSet();
   protected Vector3f tempVec3f = new Vector3f();
   protected float[] tempFloat4 = new float[4];
   protected int[] tempInt4 = new int[4];
   protected Vector3f midBlock = new Vector3f();

   public net.minecraft.client.renderer.texture.TextureAtlasSprite[] getQuadSpritesPrev() {
      return this.quadSpritesPrev;
   }

   public void setQuadSpritesPrev(net.minecraft.client.renderer.texture.TextureAtlasSprite[] quadSpritesPrev) {
      this.quadSpritesPrev = quadSpritesPrev;
   }

   public MultiTextureBuilder getMultiTextureBuilder() {
      return this.multiTextureBuilder;
   }

   public SVertexBuilder getSVertexBuilder() {
      return this.sVertexBuilder;
   }

   public RenderEnv getRenderEnv() {
      return this.renderEnv;
   }

   public BitSet getAnimatedSpritesCached() {
      return this.animatedSpritesCached;
   }

   public Vector3f getTempVec3f() {
      return this.tempVec3f;
   }

   public float[] getTempFloat4() {
      return this.tempFloat4;
   }

   public int[] getTempInt4() {
      return this.tempInt4;
   }

   public Vector3f getMidBlock() {
      return this.midBlock;
   }
}
