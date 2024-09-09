package net.optifine.config;

public interface IPersitentOption {
   String getSaveKey();

   void loadValue(net.minecraft.client.Options var1, String var2);

   String getSaveText(net.minecraft.client.Options var1);
}
