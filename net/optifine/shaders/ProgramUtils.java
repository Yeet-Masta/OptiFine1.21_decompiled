package net.optifine.shaders;

public class ProgramUtils {
   public static boolean hasActive(net.optifine.shaders.Program[] programs) {
      for (int i = 0; i < programs.length; i++) {
         net.optifine.shaders.Program program = programs[i];
         if (program.getId() != 0) {
            return true;
         }

         if (program.getComputePrograms().length > 0) {
            return true;
         }
      }

      return false;
   }
}
