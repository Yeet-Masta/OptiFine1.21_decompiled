package net.minecraft.src;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.src.C_4993_.C_4994_;
import net.minecraft.src.C_5007_.C_5008_;

public class C_4998_ {
   public static final String f_178419_ = ", ";
   public static final C_4996_ f_178420_;
   public static final C_4996_ f_178421_;

   public static C_5012_ m_130750_(C_5012_ p_130750_0_, C_5020_ p_130750_1_) {
      if (p_130750_1_.m_131179_()) {
         return p_130750_0_;
      } else {
         C_5020_ style = p_130750_0_.m_7383_();
         if (style.m_131179_()) {
            return p_130750_0_.m_6270_(p_130750_1_);
         } else {
            return style.equals(p_130750_1_) ? p_130750_0_ : p_130750_0_.m_6270_(style.m_131146_(p_130750_1_));
         }
      }
   }

   public static Optional m_178424_(@Nullable C_2969_ p_178424_0_, Optional p_178424_1_, @Nullable C_507_ p_178424_2_, int p_178424_3_) throws CommandSyntaxException {
      return p_178424_1_.isPresent() ? Optional.of(m_130731_(p_178424_0_, (C_4996_)p_178424_1_.get(), p_178424_2_, p_178424_3_)) : Optional.empty();
   }

   public static C_5012_ m_130731_(@Nullable C_2969_ p_130731_0_, C_4996_ p_130731_1_, @Nullable C_507_ p_130731_2_, int p_130731_3_) throws CommandSyntaxException {
      if (p_130731_3_ > 100) {
         return p_130731_1_.m_6881_();
      } else {
         C_5012_ mutablecomponent = p_130731_1_.m_214077_().m_213698_(p_130731_0_, p_130731_2_, p_130731_3_ + 1);
         Iterator var5 = p_130731_1_.m_7360_().iterator();

         while(var5.hasNext()) {
            C_4996_ component = (C_4996_)var5.next();
            mutablecomponent.m_7220_(m_130731_(p_130731_0_, component, p_130731_2_, p_130731_3_ + 1));
         }

         return mutablecomponent.m_130948_(m_130736_(p_130731_0_, p_130731_1_.m_7383_(), p_130731_2_, p_130731_3_));
      }
   }

   private static C_5020_ m_130736_(@Nullable C_2969_ p_130736_0_, C_5020_ p_130736_1_, @Nullable C_507_ p_130736_2_, int p_130736_3_) throws CommandSyntaxException {
      C_5007_ hoverevent = p_130736_1_.m_131186_();
      if (hoverevent != null) {
         C_4996_ component = (C_4996_)hoverevent.m_130823_(C_5008_.f_130831_);
         if (component != null) {
            C_5007_ hoverevent1 = new C_5007_(C_5008_.f_130831_, m_130731_(p_130736_0_, component, p_130736_2_, p_130736_3_ + 1));
            return p_130736_1_.m_131144_(hoverevent1);
         }
      }

      return p_130736_1_;
   }

   public static C_4996_ m_130743_(Collection collection) {
      return m_130745_(collection, (p_130741_0_) -> {
         return C_4996_.m_237113_(p_130741_0_).m_130940_(C_4856_.GREEN);
      });
   }

   public static C_4996_ m_130745_(Collection collection, Function toTextComponent) {
      if (collection.isEmpty()) {
         return C_4995_.f_237098_;
      } else if (collection.size() == 1) {
         return (C_4996_)toTextComponent.apply((Comparable)collection.iterator().next());
      } else {
         List list = Lists.newArrayList(collection);
         list.sort(Comparable::compareTo);
         return m_178440_(list, toTextComponent);
      }
   }

   public static C_4996_ m_178440_(Collection p_178440_0_, Function p_178440_1_) {
      return m_178436_(p_178440_0_, f_178420_, p_178440_1_);
   }

   public static C_5012_ m_178429_(Collection p_178429_0_, Optional p_178429_1_, Function p_178429_2_) {
      return m_178436_(p_178429_0_, (C_4996_)DataFixUtils.orElse(p_178429_1_, f_178420_), p_178429_2_);
   }

   public static C_4996_ m_178433_(Collection p_178433_0_, C_4996_ p_178433_1_) {
      return m_178436_(p_178433_0_, p_178433_1_, Function.identity());
   }

   public static C_5012_ m_178436_(Collection p_178436_0_, C_4996_ p_178436_1_, Function p_178436_2_) {
      if (p_178436_0_.isEmpty()) {
         return C_4996_.m_237119_();
      } else if (p_178436_0_.size() == 1) {
         return ((C_4996_)p_178436_2_.apply(p_178436_0_.iterator().next())).m_6881_();
      } else {
         C_5012_ mutablecomponent = C_4996_.m_237119_();
         boolean flag = true;

         for(Iterator var5 = p_178436_0_.iterator(); var5.hasNext(); flag = false) {
            Object t = var5.next();
            if (!flag) {
               mutablecomponent.m_7220_(p_178436_1_);
            }

            mutablecomponent.m_7220_((C_4996_)p_178436_2_.apply(t));
         }

         return mutablecomponent;
      }
   }

   public static C_5012_ m_130748_(C_4996_ p_130748_0_) {
      return C_4996_.m_237110_("chat.square_brackets", new Object[]{p_130748_0_});
   }

   public static C_4996_ m_130729_(Message message) {
      return (C_4996_)(message instanceof C_4996_ ? (C_4996_)message : C_4996_.m_237113_(message.getString()));
   }

   public static boolean m_237134_(@Nullable C_4996_ p_237134_0_) {
      if (p_237134_0_ != null) {
         C_213506_ var2 = p_237134_0_.m_214077_();
         if (var2 instanceof C_213523_) {
            C_213523_ translatablecontents = (C_213523_)var2;
            String s1 = translatablecontents.m_237508_();
            String s = translatablecontents.m_264577_();
            return s != null || C_4907_.m_128107_().m_6722_(s1);
         }
      }

      return true;
   }

   public static C_5012_ m_258024_(String p_258024_0_) {
      return m_130748_(C_4996_.m_237113_(p_258024_0_).m_130938_((p_257121_1_) -> {
         return p_257121_1_.m_131140_(C_4856_.GREEN).m_131142_(new C_4993_(C_4994_.COPY_TO_CLIPBOARD, p_258024_0_)).m_131144_(new C_5007_(C_5008_.f_130831_, C_4996_.m_237115_("chat.copy.click"))).m_131138_(p_258024_0_);
      }));
   }

   static {
      f_178420_ = C_4996_.m_237113_(", ").m_130940_(C_4856_.GRAY);
      f_178421_ = C_4996_.m_237113_(", ");
   }
}
