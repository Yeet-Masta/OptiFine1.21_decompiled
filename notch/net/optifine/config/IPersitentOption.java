package net.optifine.config;

import net.minecraft.src.C_3401_;

public interface IPersitentOption {
   String getSaveKey();

   void loadValue(C_3401_ var1, String var2);

   String getSaveText(C_3401_ var1);
}
