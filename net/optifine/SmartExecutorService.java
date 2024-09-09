package net.optifine;

import java.util.concurrent.ExecutorService;
import net.minecraft.client.Minecraft;
import net.optifine.util.ExecutorProxy;

public class SmartExecutorService extends ExecutorProxy {
   private ExecutorService executor;

   public SmartExecutorService(ExecutorService executor) {
      this.executor = executor;
   }

   protected ExecutorService delegate() {
      return this.executor;
   }

   public void execute(final Runnable command) {
      Runnable smartCommand = new Runnable(this) {
         public void run() {
            long timeStartMs = System.currentTimeMillis();
            command.run();
            long timeEndMs = System.currentTimeMillis();
            long runMs = timeEndMs - timeStartMs;
            Minecraft mc = Minecraft.m_91087_();
            if (mc != null && (mc.m_91090_() || mc.f_91073_ != null)) {
               Config.sleep(10L * runMs);
            }

         }
      };
      super.execute(smartCommand);
   }
}
