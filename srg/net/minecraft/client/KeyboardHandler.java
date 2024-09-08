package net.minecraft.client;

import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.Blaze3D;
import com.mojang.blaze3d.platform.ClipboardManager;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.platform.InputConstants.Key;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.NativeModuleLister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.gui.GuiShaderOptions;
import net.optifine.util.RandomUtils;

public class KeyboardHandler {
   public static final int f_167812_ = 10000;
   private final Minecraft f_90867_;
   private final ClipboardManager f_90869_ = new ClipboardManager();
   private long f_90870_ = -1L;
   private long f_90871_ = -1L;
   private long f_90872_ = -1L;
   private boolean f_90873_;
   private static boolean chunkDebugKeys = Boolean.getBoolean("chunk.debug.keys");

   public KeyboardHandler(Minecraft mcIn) {
      this.f_90867_ = mcIn;
   }

   private boolean m_167813_(int keyIn) {
      switch (keyIn) {
         case 69:
            this.f_90867_.f_291316_ = !this.f_90867_.f_291316_;
            this.m_167837_("SectionPath: {0}", this.f_90867_.f_291316_ ? "shown" : "hidden");
            return true;
         case 76:
            this.f_90867_.f_90980_ = !this.f_90867_.f_90980_;
            this.m_167837_("SmartCull: {0}", this.f_90867_.f_90980_ ? "enabled" : "disabled");
            return true;
         case 85:
            if (Screen.m_96638_()) {
               this.f_90867_.f_91060_.m_173019_();
               this.m_167837_("Killed frustum");
            } else if (Screen.m_96639_()) {
               if (Config.isShadersShadows()) {
                  this.f_90867_.f_91060_.captureFrustumShadow();
                  this.m_167837_("Captured shadow frustum");
               }
            } else {
               this.f_90867_.f_91060_.m_173018_();
               this.m_167837_("Captured frustum");
            }

            return true;
         case 86:
            this.f_90867_.f_291317_ = !this.f_90867_.f_291317_;
            this.m_167837_("SectionVisibility: {0}", this.f_90867_.f_291317_ ? "enabled" : "disabled");
            return true;
         case 87:
            this.f_90867_.f_167842_ = !this.f_90867_.f_167842_;
            this.m_167837_("WireFrame: {0}", this.f_90867_.f_167842_ ? "enabled" : "disabled");
            return true;
         default:
            return false;
      }
   }

   private void m_167824_(ChatFormatting formatIn, Component componentIn) {
      this.f_90867_
         .f_91065_
         .m_93076_()
         .m_93785_(
            Component.m_237119_()
               .m_7220_(Component.m_237115_("debug.prefix").m_130944_(new ChatFormatting[]{formatIn, ChatFormatting.BOLD}))
               .m_7220_(CommonComponents.f_263701_)
               .m_7220_(componentIn)
         );
   }

   private void m_167822_(Component componentIn) {
      this.m_167824_(ChatFormatting.YELLOW, componentIn);
   }

   private void m_90913_(String message, Object... args) {
      this.m_167822_(Component.m_307043_(message, args));
   }

   private void m_90948_(String message, Object... args) {
      this.m_167824_(ChatFormatting.RED, Component.m_307043_(message, args));
   }

   private void m_167837_(String message, Object... params) {
      this.m_167822_(Component.m_237113_(MessageFormat.format(message, params)));
   }

   private boolean m_90932_(int key) {
      if (this.f_90870_ > 0L && this.f_90870_ < Util.m_137550_() - 100L) {
         return true;
      } else if (chunkDebugKeys && this.m_167813_(key)) {
         return true;
      } else {
         switch (key) {
            case 49:
               this.f_90867_.m_293199_().m_293481_();
               return true;
            case 50:
               this.f_90867_.m_293199_().m_294611_();
               return true;
            case 51:
               this.f_90867_.m_293199_().m_295292_();
               return true;
            case 65:
               this.f_90867_.f_91060_.m_109818_();
               this.m_90913_("debug.reload_chunks.message");
               return true;
            case 66:
               boolean flag = !this.f_90867_.m_91290_().m_114377_();
               this.f_90867_.m_91290_().m_114473_(flag);
               this.m_90913_(flag ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off");
               return true;
            case 67:
               if (this.f_90867_.f_91074_.m_36330_()) {
                  return false;
               } else {
                  ClientPacketListener clientpacketlistener = this.f_90867_.f_91074_.f_108617_;
                  if (clientpacketlistener == null) {
                     return false;
                  }

                  this.m_90913_("debug.copy_location.message");
                  this.m_90911_(
                     String.format(
                        Locale.ROOT,
                        "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f",
                        this.f_90867_.f_91074_.m_9236_().m_46472_().m_135782_(),
                        this.f_90867_.f_91074_.m_20185_(),
                        this.f_90867_.f_91074_.m_20186_(),
                        this.f_90867_.f_91074_.m_20189_(),
                        this.f_90867_.f_91074_.m_146908_(),
                        this.f_90867_.f_91074_.m_146909_()
                     )
                  );
                  return true;
               }
            case 68:
               if (this.f_90867_.f_91065_ != null) {
                  this.f_90867_.f_91065_.m_93076_().m_93795_(false);
               }

               return true;
            case 71:
               boolean flag1 = this.f_90867_.f_91064_.m_113506_();
               this.m_90913_(flag1 ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off");
               return true;
            case 72:
               this.f_90867_.f_91066_.f_92125_ = !this.f_90867_.f_91066_.f_92125_;
               this.m_90913_(this.f_90867_.f_91066_.f_92125_ ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off");
               this.f_90867_.f_91066_.m_92169_();
               return true;
            case 73:
               if (!this.f_90867_.f_91074_.m_36330_()) {
                  this.m_90928_(this.f_90867_.f_91074_.m_20310_(2), !Screen.m_96638_());
               }

               return true;
            case 76:
               if (this.f_90867_.m_167946_(this::m_167822_)) {
                  this.m_90913_("debug.profiling.start", 10);
               }

               return true;
            case 78:
               if (!this.f_90867_.f_91074_.m_20310_(2)) {
                  this.m_90913_("debug.creative_spectator.error");
               } else if (!this.f_90867_.f_91074_.m_5833_()) {
                  this.f_90867_.f_91074_.f_108617_.m_246979_("gamemode spectator");
               } else {
                  this.f_90867_
                     .f_91074_
                     .f_108617_
                     .m_246979_("gamemode " + ((GameType)MoreObjects.firstNonNull(this.f_90867_.f_91072_.m_105294_(), GameType.CREATIVE)).m_46405_());
               }

               return true;
            case 79:
               if (Config.isShaders()) {
                  GuiShaderOptions gui = new GuiShaderOptions(null, Config.getGameSettings());
                  Config.getMinecraft().m_91152_(gui);
               }

               return true;
            case 80:
               this.f_90867_.f_91066_.f_92126_ = !this.f_90867_.f_91066_.f_92126_;
               this.f_90867_.f_91066_.m_92169_();
               this.m_90913_(this.f_90867_.f_91066_.f_92126_ ? "debug.pause_focus.on" : "debug.pause_focus.off");
               return true;
            case 81:
               this.m_90913_("debug.help.message");
               ChatComponent chatcomponent = this.f_90867_.f_91065_.m_93076_();
               chatcomponent.m_93785_(Component.m_237115_("debug.reload_chunks.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.show_hitboxes.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.copy_location.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.clear_chat.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.chunk_boundaries.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.advanced_tooltips.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.inspect.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.profiling.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.creative_spectator.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.pause_focus.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.help.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.dump_dynamic_textures.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.reload_resourcepacks.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.pause.help"));
               chatcomponent.m_93785_(Component.m_237115_("debug.gamemodes.help"));
               return true;
            case 82:
               if (Config.isShaders()) {
                  Shaders.uninit();
                  Shaders.loadShaderPack();
               }

               return true;
            case 83:
               Path path = this.f_90867_.f_91069_.toPath().toAbsolutePath();
               Path path1 = TextureUtil.getDebugTexturePath(path);
               this.f_90867_.m_91097_().m_276085_(path1);
               Component component = Component.m_237113_(path.relativize(path1).toString())
                  .m_130940_(ChatFormatting.UNDERLINE)
                  .m_130938_(styleIn -> styleIn.m_131142_(new ClickEvent(Action.OPEN_FILE, path1.toFile().toString())));
               this.m_90913_("debug.dump_dynamic_textures", component);
               return true;
            case 84:
               this.m_90913_("debug.reload_resourcepacks.message");
               this.f_90867_.m_91391_();
               return true;
            case 86:
               Minecraft mc = Config.getMinecraft();
               mc.f_91060_.loadVisibleChunksCounter = 1;
               Component msg = Component.m_237113_(I18n.m_118938_("of.message.loadingVisibleChunks", new Object[0]));
               LevelRenderer.loadVisibleChunksMessageId = new MessageSignature(RandomUtils.getRandomBytes(256));
               mc.f_91065_.m_93076_().m_240964_(msg, LevelRenderer.loadVisibleChunksMessageId, GuiMessageTag.m_240701_());
               return true;
            case 293:
               if (!this.f_90867_.f_91074_.m_20310_(2)) {
                  this.m_90913_("debug.gamemodes.error");
               } else {
                  this.f_90867_.m_91152_(new GameModeSwitcherScreen());
               }

               return true;
            default:
               return false;
         }
      }
   }

   private void m_90928_(boolean privileged, boolean askServer) {
      HitResult hitresult = this.f_90867_.f_91077_;
      if (hitresult != null) {
         switch (hitresult.m_6662_()) {
            case BLOCK:
               BlockPos blockpos = ((BlockHitResult)hitresult).m_82425_();
               Level level = this.f_90867_.f_91074_.m_9236_();
               BlockState blockstate = level.m_8055_(blockpos);
               if (privileged) {
                  if (askServer) {
                     this.f_90867_.f_91074_.f_108617_.m_105149_().m_90708_(blockpos, tagIn -> {
                        this.m_90899_(blockstate, blockpos, tagIn);
                        this.m_90913_("debug.inspect.server.block");
                     });
                  } else {
                     BlockEntity blockentity = level.m_7702_(blockpos);
                     CompoundTag compoundtag1 = blockentity != null ? blockentity.m_187482_(level.m_9598_()) : null;
                     this.m_90899_(blockstate, blockpos, compoundtag1);
                     this.m_90913_("debug.inspect.client.block");
                  }
               } else {
                  this.m_90899_(blockstate, blockpos, null);
                  this.m_90913_("debug.inspect.client.block");
               }
               break;
            case ENTITY:
               Entity entity = ((EntityHitResult)hitresult).m_82443_();
               ResourceLocation resourcelocation = BuiltInRegistries.f_256780_.m_7981_(entity.m_6095_());
               if (privileged) {
                  if (askServer) {
                     this.f_90867_.f_91074_.f_108617_.m_105149_().m_90702_(entity.m_19879_(), tagIn -> {
                        this.m_90922_(resourcelocation, entity.m_20182_(), tagIn);
                        this.m_90913_("debug.inspect.server.entity");
                     });
                  } else {
                     CompoundTag compoundtag = entity.m_20240_(new CompoundTag());
                     this.m_90922_(resourcelocation, entity.m_20182_(), compoundtag);
                     this.m_90913_("debug.inspect.client.entity");
                  }
               } else {
                  this.m_90922_(resourcelocation, entity.m_20182_(), null);
                  this.m_90913_("debug.inspect.client.entity");
               }
         }
      }
   }

   private void m_90899_(BlockState state, BlockPos pos, @Nullable CompoundTag compound) {
      StringBuilder stringbuilder = new StringBuilder(BlockStateParser.m_116769_(state));
      if (compound != null) {
         stringbuilder.append(compound);
      }

      String s = String.format(Locale.ROOT, "/setblock %d %d %d %s", pos.m_123341_(), pos.m_123342_(), pos.m_123343_(), stringbuilder);
      this.m_90911_(s);
   }

   private void m_90922_(ResourceLocation entityIdIn, Vec3 pos, @Nullable CompoundTag compound) {
      String s;
      if (compound != null) {
         compound.m_128473_("UUID");
         compound.m_128473_("Pos");
         compound.m_128473_("Dimension");
         String s1 = NbtUtils.m_178061_(compound).getString();
         s = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f %s", entityIdIn, pos.f_82479_, pos.f_82480_, pos.f_82481_, s1);
      } else {
         s = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f", entityIdIn, pos.f_82479_, pos.f_82480_, pos.f_82481_);
      }

      this.m_90911_(s);
   }

   public void m_90893_(long windowPointer, int key, int scanCode, int action, int modifiers) {
      if (windowPointer == this.f_90867_.m_91268_().m_85439_()) {
         boolean flag = InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 292);
         if (this.f_90870_ > 0L) {
            if (!InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 67) || !flag) {
               this.f_90870_ = -1L;
            }
         } else if (InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 67) && flag) {
            this.f_90873_ = true;
            this.f_90870_ = Util.m_137550_();
            this.f_90871_ = Util.m_137550_();
            this.f_90872_ = 0L;
         }

         Screen screen = this.f_90867_.f_91080_;
         if (screen != null) {
            switch (key) {
               case 258:
                  this.f_90867_.m_264033_(InputType.KEYBOARD_TAB);
               case 259:
               case 260:
               case 261:
               default:
                  break;
               case 262:
               case 263:
               case 264:
               case 265:
                  this.f_90867_.m_264033_(InputType.KEYBOARD_ARROW);
            }
         }

         if (action == 1 && (!(this.f_90867_.f_91080_ instanceof KeyBindsScreen) || ((KeyBindsScreen)screen).f_337497_ <= Util.m_137550_() - 20L)) {
            if (this.f_90867_.f_91066_.f_92105_.m_90832_(key, scanCode)) {
               this.f_90867_.m_91268_().m_85438_();
               this.f_90867_.f_91066_.m_231829_().m_231514_(this.f_90867_.m_91268_().m_85440_());
               return;
            }

            if (this.f_90867_.f_91066_.f_92102_.m_90832_(key, scanCode)) {
               if (Screen.m_96637_()) {
               }

               Screenshot.m_92289_(
                  this.f_90867_.f_91069_,
                  this.f_90867_.m_91385_(),
                  componentIn -> this.f_90867_.execute(() -> this.f_90867_.f_91065_.m_93076_().m_93785_(componentIn))
               );
               return;
            }
         }

         if (action != 0) {
            boolean flag1 = screen == null || !(screen.m_7222_() instanceof EditBox) || !((EditBox)screen.m_7222_()).m_94204_();
            if (flag1) {
               if (Screen.m_96637_() && key == 66 && this.f_90867_.m_240477_().m_93316_() && this.f_90867_.f_91066_.m_292959_().m_231551_()) {
                  boolean flag2 = this.f_90867_.f_91066_.m_231930_().m_231551_() == NarratorStatus.OFF;
                  this.f_90867_.f_91066_.m_231930_().m_231514_(NarratorStatus.m_91619_(this.f_90867_.f_91066_.m_231930_().m_231551_().m_91618_() + 1));
                  this.f_90867_.f_91066_.m_92169_();
                  if (screen != null) {
                     screen.m_340185_(flag2);
                  }
               }

               LocalPlayer var16 = this.f_90867_.f_91074_;
            }
         }

         if (screen != null) {
            boolean[] aboolean = new boolean[]{false};
            Screen.m_96579_(() -> {
               if (action != 1 && action != 2) {
                  if (action == 0) {
                     if (Reflector.ForgeHooksClient_onScreenKeyReleasedPre.exists()) {
                        aboolean[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenKeyReleasedPre, screen, key, scanCode, modifiers);
                        if (aboolean[0]) {
                           return;
                        }
                     }

                     aboolean[0] = screen.m_7920_(key, scanCode, modifiers);
                     if (Reflector.ForgeHooksClient_onScreenKeyReleasedPost.exists() && !aboolean[0]) {
                        aboolean[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenKeyReleasedPost, screen, key, scanCode, modifiers);
                     }
                  }
               } else {
                  if (Reflector.ForgeHooksClient_onScreenKeyPressedPre.exists()) {
                     aboolean[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenKeyPressedPre, screen, key, scanCode, modifiers);
                     if (aboolean[0]) {
                        return;
                     }
                  }

                  screen.m_169416_();
                  aboolean[0] = screen.m_7933_(key, scanCode, modifiers);
                  if (Reflector.ForgeHooksClient_onScreenKeyPressedPost.exists() && !aboolean[0]) {
                     aboolean[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenKeyPressedPost, screen, key, scanCode, modifiers);
                  }
               }
            }, "keyPressed event handler", screen.getClass().getCanonicalName());
            if (aboolean[0]) {
               return;
            }
         }

         Key inputconstants$key = InputConstants.m_84827_(key, scanCode);
         boolean flag4 = this.f_90867_.f_91080_ == null;
         boolean flag6;
         if (flag4 || this.f_90867_.f_91080_ instanceof PauseScreen pausescreen && !pausescreen.m_294488_()) {
            flag6 = true;
         } else {
            flag6 = false;
         }

         if (action == 0) {
            KeyMapping.m_90837_(inputconstants$key, false);
            if (flag6 && key == 292) {
               if (this.f_90873_) {
                  this.f_90873_ = false;
               } else {
                  this.f_90867_.m_293199_().m_293034_();
               }
            }
         } else {
            boolean flag5 = false;
            if (flag6) {
               if (key == 293 && this.f_90867_.f_91063_ != null) {
                  this.f_90867_.f_91063_.m_109130_();
               }

               if (key == 256) {
                  this.f_90867_.m_91358_(flag);
                  flag5 |= flag;
               }

               flag5 |= flag && this.m_90932_(key);
               this.f_90873_ |= flag5;
               if (key == 290) {
                  this.f_90867_.f_91066_.f_92062_ = !this.f_90867_.f_91066_.f_92062_;
               }

               if (this.f_90867_.m_293199_().m_295669_() && !flag && key >= 48 && key <= 57) {
                  this.f_90867_.m_91111_(key - 48);
               }
            }

            if (flag4) {
               if (flag5) {
                  KeyMapping.m_90837_(inputconstants$key, false);
               } else {
                  KeyMapping.m_90837_(inputconstants$key, true);
                  KeyMapping.m_90835_(inputconstants$key);
               }
            }
         }

         Reflector.ForgeHooksClient_onKeyInput.call(key, scanCode, action, modifiers);
      }
   }

   private void m_90889_(long windowPointer, int codePoint, int modifiers) {
      if (windowPointer == this.f_90867_.m_91268_().m_85439_()) {
         Screen guieventlistener = this.f_90867_.f_91080_;
         if (guieventlistener != null && this.f_90867_.m_91265_() == null) {
            if (Character.charCount(codePoint) == 1) {
               Screen.m_96579_(
                  () -> {
                     if (!Reflector.ForgeHooksClient_onScreenCharTypedPre.exists()
                        || !Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenCharTypedPre, guieventlistener, (char)codePoint, modifiers)) {
                        boolean consumed = guieventlistener.m_5534_((char)codePoint, modifiers);
                        if (Reflector.ForgeHooksClient_onScreenCharTypedPost.exists() && !consumed) {
                           Reflector.call(Reflector.ForgeHooksClient_onScreenCharTypedPost, guieventlistener, (char)codePoint, modifiers);
                        }
                     }
                  },
                  "charTyped event handler",
                  guieventlistener.getClass().getCanonicalName()
               );
            } else {
               for (char c0 : Character.toChars(codePoint)) {
                  Screen.m_96579_(
                     () -> {
                        if (!Reflector.ForgeHooksClient_onScreenCharTypedPre.exists()
                           || !Reflector.callBoolean(Reflector.ForgeHooksClient_onScreenCharTypedPre, guieventlistener, c0, modifiers)) {
                           boolean consumed = guieventlistener.m_5534_(c0, modifiers);
                           if (Reflector.ForgeHooksClient_onScreenCharTypedPost.exists() && !consumed) {
                              Reflector.call(Reflector.ForgeHooksClient_onScreenCharTypedPost, guieventlistener, c0, modifiers);
                           }
                        }
                     },
                     "charTyped event handler",
                     guieventlistener.getClass().getCanonicalName()
                  );
               }
            }
         }
      }
   }

   public void m_90887_(long window) {
      InputConstants.m_84844_(
         window,
         (windowPointer, key, scanCode, action, modifiers) -> this.f_90867_.execute(() -> this.m_90893_(windowPointer, key, scanCode, action, modifiers)),
         (windowPointer, codePoint, modifiers) -> this.f_90867_.execute(() -> this.m_90889_(windowPointer, codePoint, modifiers))
      );
   }

   public String m_90876_() {
      return this.f_90869_.m_83995_(this.f_90867_.m_91268_().m_85439_(), (errorIn, descriptionIn) -> {
         if (errorIn != 65545) {
            this.f_90867_.m_91268_().m_85382_(errorIn, descriptionIn);
         }
      });
   }

   public void m_90911_(String string) {
      if (!string.isEmpty()) {
         this.f_90869_.m_83988_(this.f_90867_.m_91268_().m_85439_(), string);
      }
   }

   public void m_90931_() {
      if (this.f_90870_ > 0L) {
         long i = Util.m_137550_();
         long j = 10000L - (i - this.f_90870_);
         long k = i - this.f_90871_;
         if (j < 0L) {
            if (Screen.m_96637_()) {
               Blaze3D.m_83639_();
            }

            String s = "Manually triggered debug crash";
            CrashReport crashreport = new CrashReport("Manually triggered debug crash", new Throwable("Manually triggered debug crash"));
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Manual crash details");
            NativeModuleLister.m_184679_(crashreportcategory);
            throw new ReportedException(crashreport);
         }

         if (k >= 1000L) {
            if (this.f_90872_ == 0L) {
               this.m_90913_("debug.crash.message");
            } else {
               this.m_90948_("debug.crash.warning", Mth.m_14167_((float)j / 1000.0F));
            }

            this.f_90871_ = i;
            this.f_90872_++;
         }
      }
   }
}
