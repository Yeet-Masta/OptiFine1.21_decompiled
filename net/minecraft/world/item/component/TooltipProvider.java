package net.minecraft.world.item.component;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;

public interface TooltipProvider {
   void m_319025_(TooltipContext var1, Consumer<Component> var2, TooltipFlag var3);
}
