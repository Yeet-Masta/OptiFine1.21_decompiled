package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixUtils;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.Entity;

public class ComponentUtils {
   public static final String f_178419_ = ", ";
   public static final Component f_178420_ = Component.m_237113_(", ").m_130940_(ChatFormatting.GRAY);
   public static final Component f_178421_ = Component.m_237113_(", ");

   public static MutableComponent m_130750_(MutableComponent p_130750_0_, Style p_130750_1_) {
      if (p_130750_1_.m_131179_()) {
         return p_130750_0_;
      } else {
         Style style = p_130750_0_.m_7383_();
         if (style.m_131179_()) {
            return p_130750_0_.m_6270_(p_130750_1_);
         } else {
            return style.equals(p_130750_1_) ? p_130750_0_ : p_130750_0_.m_6270_(style.m_131146_(p_130750_1_));
         }
      }
   }

   public static Optional<MutableComponent> m_178424_(
      @Nullable CommandSourceStack p_178424_0_, Optional<Component> p_178424_1_, @Nullable Entity p_178424_2_, int p_178424_3_
   ) throws CommandSyntaxException {
      return p_178424_1_.isPresent() ? Optional.of(m_130731_(p_178424_0_, (Component)p_178424_1_.get(), p_178424_2_, p_178424_3_)) : Optional.empty();
   }

   public static MutableComponent m_130731_(@Nullable CommandSourceStack p_130731_0_, Component p_130731_1_, @Nullable Entity p_130731_2_, int p_130731_3_) throws CommandSyntaxException {
      if (p_130731_3_ > 100) {
         return p_130731_1_.m_6881_();
      } else {
         MutableComponent mutablecomponent = p_130731_1_.m_214077_().m_213698_(p_130731_0_, p_130731_2_, p_130731_3_ + 1);

         for (Component component : p_130731_1_.m_7360_()) {
            mutablecomponent.m_7220_(m_130731_(p_130731_0_, component, p_130731_2_, p_130731_3_ + 1));
         }

         return mutablecomponent.m_130948_(m_130736_(p_130731_0_, p_130731_1_.m_7383_(), p_130731_2_, p_130731_3_));
      }
   }

   private static Style m_130736_(@Nullable CommandSourceStack p_130736_0_, Style p_130736_1_, @Nullable Entity p_130736_2_, int p_130736_3_) throws CommandSyntaxException {
      HoverEvent hoverevent = p_130736_1_.m_131186_();
      if (hoverevent != null) {
         Component component = (Component)hoverevent.m_130823_(Action.f_130831_);
         if (component != null) {
            HoverEvent hoverevent1 = new HoverEvent(Action.f_130831_, m_130731_(p_130736_0_, component, p_130736_2_, p_130736_3_ + 1));
            return p_130736_1_.m_131144_(hoverevent1);
         }
      }

      return p_130736_1_;
   }

   public static Component m_130743_(Collection<String> collection) {
      return m_130745_(collection, p_130741_0_ -> Component.m_237113_(p_130741_0_).m_130940_(ChatFormatting.GREEN));
   }

   public static <T extends Comparable<T>> Component m_130745_(Collection<T> collection, Function<T, Component> toTextComponent) {
      if (collection.isEmpty()) {
         return CommonComponents.f_237098_;
      } else if (collection.size() == 1) {
         return (Component)toTextComponent.apply((Comparable)collection.iterator().next());
      } else {
         List<T> list = Lists.newArrayList(collection);
         list.sort(Comparable::compareTo);
         return m_178440_(list, toTextComponent);
      }
   }

   public static <T> Component m_178440_(Collection<? extends T> p_178440_0_, Function<T, Component> p_178440_1_) {
      return m_178436_(p_178440_0_, f_178420_, p_178440_1_);
   }

   public static <T> MutableComponent m_178429_(
      Collection<? extends T> p_178429_0_, Optional<? extends Component> p_178429_1_, Function<T, Component> p_178429_2_
   ) {
      return m_178436_(p_178429_0_, (Component)DataFixUtils.orElse(p_178429_1_, f_178420_), p_178429_2_);
   }

   public static Component m_178433_(Collection<? extends Component> p_178433_0_, Component p_178433_1_) {
      return m_178436_(p_178433_0_, p_178433_1_, Function.identity());
   }

   public static <T> MutableComponent m_178436_(Collection<? extends T> p_178436_0_, Component p_178436_1_, Function<T, Component> p_178436_2_) {
      if (p_178436_0_.isEmpty()) {
         return Component.m_237119_();
      } else if (p_178436_0_.size() == 1) {
         return ((Component)p_178436_2_.apply(p_178436_0_.iterator().next())).m_6881_();
      } else {
         MutableComponent mutablecomponent = Component.m_237119_();
         boolean flag = true;

         for (T t : p_178436_0_) {
            if (!flag) {
               mutablecomponent.m_7220_(p_178436_1_);
            }

            mutablecomponent.m_7220_((Component)p_178436_2_.apply(t));
            flag = false;
         }

         return mutablecomponent;
      }
   }

   public static MutableComponent m_130748_(Component p_130748_0_) {
      return Component.m_237110_("chat.square_brackets", new Object[]{p_130748_0_});
   }

   public static Component m_130729_(Message message) {
      return (Component)(message instanceof Component ? (Component)message : Component.m_237113_(message.getString()));
   }

   public static boolean m_237134_(@Nullable Component p_237134_0_) {
      if (p_237134_0_ != null && p_237134_0_.m_214077_() instanceof TranslatableContents translatablecontents) {
         String s1 = translatablecontents.m_237508_();
         String s = translatablecontents.m_264577_();
         return s != null || Language.m_128107_().m_6722_(s1);
      } else {
         return true;
      }
   }

   public static MutableComponent m_258024_(String p_258024_0_) {
      return m_130748_(
         Component.m_237113_(p_258024_0_)
            .m_130938_(
               p_257121_1_ -> p_257121_1_.m_131140_(ChatFormatting.GREEN)
                     .m_131142_(new ClickEvent(net.minecraft.network.chat.ClickEvent.Action.COPY_TO_CLIPBOARD, p_258024_0_))
                     .m_131144_(new HoverEvent(Action.f_130831_, Component.m_237115_("chat.copy.click")))
                     .m_131138_(p_258024_0_)
            )
      );
   }
}
