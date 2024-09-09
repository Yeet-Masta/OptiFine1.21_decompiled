package net.optifine.shaders.uniform;

import java.nio.FloatBuffer;
import net.optifine.util.BufferUtil;
import net.optifine.util.MathUtils;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

public class ShaderUniformM4 extends ShaderUniformBase {
   private boolean transpose;
   private FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(16);
   private FloatBuffer tempBuffer = MemoryUtil.memAllocFloat(16);

   public ShaderUniformM4(String name) {
      super(name);
   }

   public void setValue(Matrix4f matrixIn) {
      this.transpose = false;
      this.tempBuffer.clear();
      MathUtils.write(matrixIn, this.tempBuffer);
      this.setValue(false, this.tempBuffer);
   }

   public void setValue(boolean transpose, FloatBuffer matrix) {
      this.transpose = transpose;
      matrix.mark();
      this.matrixBuffer.clear();
      this.matrixBuffer.put(matrix);
      this.matrixBuffer.rewind();
      matrix.reset();
      int location = this.getLocation();
      if (location >= 0) {
         flushRenderBuffers();
         GL20.glUniformMatrix4fv(location, transpose, this.matrixBuffer);
         this.checkGLError();
      }
   }

   public float getValue(int row, int col) {
      int index = this.transpose ? col * 4 + row : row * 4 + col;
      float value = this.matrixBuffer.get(index);
      return value;
   }

   protected void onProgramSet(int program) {
   }

   protected void resetValue() {
      BufferUtil.fill(this.matrixBuffer, 0.0F);
   }
}
