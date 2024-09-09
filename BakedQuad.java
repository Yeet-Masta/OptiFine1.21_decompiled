import net.optifine.Config;
import net.optifine.model.BakedQuadRetextured;
import net.optifine.model.QuadBounds;
import net.optifine.render.QuadVertexPositions;
import net.optifine.render.VertexPosition;

public class BakedQuad {
   protected int[] a;
   protected final int b;
   protected Direction c;
   protected TextureAtlasSprite d;
   private final boolean e;
   private boolean hasAmbientOcclusion = true;
   private int[] vertexDataSingle = null;
   private QuadBounds quadBounds;
   private boolean quadEmissiveChecked;
   private BakedQuad quadEmissive;
   private QuadVertexPositions quadVertexPositions;

   public BakedQuad(int[] vertexDataIn, int tintIndexIn, Direction faceIn, TextureAtlasSprite spriteIn, boolean shadeIn) {
      this.a = vertexDataIn;
      this.b = tintIndexIn;
      this.c = faceIn;
      this.d = spriteIn;
      this.e = shadeIn;
      this.fixVertexData();
   }

   public BakedQuad(int[] vertexDataIn, int tintIndexIn, Direction faceIn, TextureAtlasSprite spriteIn, boolean shadeIn, boolean hasAmbientOcclusion) {
      this(vertexDataIn, tintIndexIn, faceIn, spriteIn, shadeIn);
      this.hasAmbientOcclusion = hasAmbientOcclusion;
   }

   public TextureAtlasSprite a() {
      if (this.d == null) {
         this.d = getSpriteByUv(this.b());
      }

      return this.d;
   }

   public int[] b() {
      this.fixVertexData();
      return this.a;
   }

   public boolean c() {
      return this.b != -1;
   }

   public int d() {
      return this.b;
   }

   public Direction e() {
      if (this.c == null) {
         this.c = FaceBakery.a(this.b());
      }

      return this.c;
   }

   public boolean f() {
      return this.e;
   }

   public int[] getVertexDataSingle() {
      if (this.vertexDataSingle == null) {
         this.vertexDataSingle = makeVertexDataSingle(this.b(), this.a());
      }

      if (this.vertexDataSingle.length != this.b().length) {
         this.vertexDataSingle = makeVertexDataSingle(this.b(), this.a());
      }

      return this.vertexDataSingle;
   }

   private static int[] makeVertexDataSingle(int[] vd, TextureAtlasSprite sprite) {
      int[] vdSingle = (int[])vd.clone();
      int step = vdSingle.length / 4;

      for (int i = 0; i < 4; i++) {
         int pos = i * step;
         float tu = Float.intBitsToFloat(vdSingle[pos + 4]);
         float tv = Float.intBitsToFloat(vdSingle[pos + 4 + 1]);
         float u = sprite.toSingleU(tu);
         float v = sprite.toSingleV(tv);
         vdSingle[pos + 4] = Float.floatToRawIntBits(u);
         vdSingle[pos + 4 + 1] = Float.floatToRawIntBits(v);
      }

      return vdSingle;
   }

   private static TextureAtlasSprite getSpriteByUv(int[] vertexData) {
      float uMin = 1.0F;
      float vMin = 1.0F;
      float uMax = 0.0F;
      float vMax = 0.0F;
      int step = vertexData.length / 4;

      for (int i = 0; i < 4; i++) {
         int pos = i * step;
         float tu = Float.intBitsToFloat(vertexData[pos + 4]);
         float tv = Float.intBitsToFloat(vertexData[pos + 4 + 1]);
         uMin = Math.min(uMin, tu);
         vMin = Math.min(vMin, tv);
         uMax = Math.max(uMax, tu);
         vMax = Math.max(vMax, tv);
      }

      float uMid = (uMin + uMax) / 2.0F;
      float vMid = (vMin + vMax) / 2.0F;
      return Config.getTextureMap().getIconByUV((double)uMid, (double)vMid);
   }

   protected void fixVertexData() {
      if (Config.isShaders()) {
         if (this.a.length == DefaultVertexFormat.BLOCK_VANILLA_SIZE) {
            this.a = fixVertexDataSize(this.a, DefaultVertexFormat.BLOCK_SHADERS_SIZE);
         }
      } else if (this.a.length == DefaultVertexFormat.BLOCK_SHADERS_SIZE) {
         this.a = fixVertexDataSize(this.a, DefaultVertexFormat.BLOCK_VANILLA_SIZE);
      }
   }

   private static int[] fixVertexDataSize(int[] vd, int sizeNew) {
      int step = vd.length / 4;
      int stepNew = sizeNew / 4;
      int[] vdNew = new int[stepNew * 4];

      for (int i = 0; i < 4; i++) {
         int len = Math.min(step, stepNew);
         System.arraycopy(vd, i * step, vdNew, i * stepNew, len);
      }

      return vdNew;
   }

   public QuadBounds getQuadBounds() {
      if (this.quadBounds == null) {
         this.quadBounds = new QuadBounds(this.b());
      }

      return this.quadBounds;
   }

   public float getMidX() {
      QuadBounds qb = this.getQuadBounds();
      return (qb.getMaxX() + qb.getMinX()) / 2.0F;
   }

   public double getMidY() {
      QuadBounds qb = this.getQuadBounds();
      return (double)((qb.getMaxY() + qb.getMinY()) / 2.0F);
   }

   public double getMidZ() {
      QuadBounds qb = this.getQuadBounds();
      return (double)((qb.getMaxZ() + qb.getMinZ()) / 2.0F);
   }

   public boolean isFaceQuad() {
      QuadBounds qb = this.getQuadBounds();
      return qb.isFaceQuad(this.c);
   }

   public boolean isFullQuad() {
      QuadBounds qb = this.getQuadBounds();
      return qb.isFullQuad(this.c);
   }

   public boolean isFullFaceQuad() {
      return this.isFullQuad() && this.isFaceQuad();
   }

   public BakedQuad getQuadEmissive() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: Constructor net/optifine/model/BakedQuadRetextured.<init>(LBakedQuad;LTextureAtlasSprite;)V not found
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:49)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:957)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:460)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1018)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.AssignmentExprent.toJava(AssignmentExprent.java:154)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:241)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.SequenceStatement.toJava(SequenceStatement.java:107)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:261)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield BakedQuad.quadEmissiveChecked Z
      // 04: ifeq 0c
      // 07: aload 0
      // 08: getfield BakedQuad.quadEmissive LBakedQuad;
      // 0b: areturn
      // 0c: aload 0
      // 0d: getfield BakedQuad.quadEmissive LBakedQuad;
      // 10: ifnonnull 37
      // 13: aload 0
      // 14: getfield BakedQuad.d LTextureAtlasSprite;
      // 17: ifnull 37
      // 1a: aload 0
      // 1b: getfield BakedQuad.d LTextureAtlasSprite;
      // 1e: getfield TextureAtlasSprite.spriteEmissive LTextureAtlasSprite;
      // 21: ifnull 37
      // 24: aload 0
      // 25: new net/optifine/model/BakedQuadRetextured
      // 28: dup
      // 29: aload 0
      // 2a: aload 0
      // 2b: getfield BakedQuad.d LTextureAtlasSprite;
      // 2e: getfield TextureAtlasSprite.spriteEmissive LTextureAtlasSprite;
      // 31: invokespecial net/optifine/model/BakedQuadRetextured.<init> (LBakedQuad;LTextureAtlasSprite;)V
      // 34: putfield BakedQuad.quadEmissive LBakedQuad;
      // 37: aload 0
      // 38: bipush 1
      // 39: putfield BakedQuad.quadEmissiveChecked Z
      // 3c: aload 0
      // 3d: getfield BakedQuad.quadEmissive LBakedQuad;
      // 40: areturn
   }

   public VertexPosition[] getVertexPositions(int key) {
      if (this.quadVertexPositions == null) {
         this.quadVertexPositions = new QuadVertexPositions();
      }

      return this.quadVertexPositions.get(key);
   }

   public boolean hasAmbientOcclusion() {
      return this.hasAmbientOcclusion;
   }

   public String toString() {
      return "vertexData: " + this.a.length + ", tint: " + this.b + ", facing: " + this.c + ", sprite: " + this.d;
   }
}
