package net.minecraft.src;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraftforge.client.extensions.IForgeBakedModel;

public interface C_4528_ extends IForgeBakedModel {
   List<C_4196_> m_213637_(@Nullable C_2064_ var1, @Nullable C_4687_ var2, C_212974_ var3);

   boolean m_7541_();

   boolean m_7539_();

   boolean m_7547_();

   boolean m_7521_();

   C_4486_ m_6160_();

   default C_4222_ m_7442_() {
      return C_4222_.f_111786_;
   }

   C_4219_ m_7343_();
}
