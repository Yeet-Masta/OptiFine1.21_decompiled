package net.optifine;

import java.util.concurrent.ExecutorService;
import net.minecraft.client.Minecraft;
import net.optifine.util.ExecutorProxy;

public class SmartExecutorService extends ExecutorProxy {
   private ExecutorService executor;

   public SmartExecutorService(ExecutorService executor) {
      this.executor = executor;
   }

   @Override
   protected ExecutorService delegate() {
      return this.executor;
   }

   @Override
   public void m_305380_(final Runnable command) {
      Runnable smartCommand = new Runnable() {
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
      super.m_305380_(smartCommand);
   }
}
