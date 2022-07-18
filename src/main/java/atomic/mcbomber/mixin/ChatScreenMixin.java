package atomic.mcbomber.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Inject(at=@At("HEAD"),method = "shouldPreviewChat",cancellable = true)
    public void noBullshitChatPreviewChecks(CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftClient.getInstance().player == null) {
            cir.setReturnValue(false);

        }
        if (MinecraftClient.getInstance().isInSingleplayer()) {
            cir.setReturnValue(true);
        }
    }
}