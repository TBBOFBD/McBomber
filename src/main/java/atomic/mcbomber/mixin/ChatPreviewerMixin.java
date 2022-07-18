package atomic.mcbomber.mixin;

import atomic.mcbomber.Addon;
import net.minecraft.client.network.ChatPreviewer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatPreviewer.class)
public class ChatPreviewerMixin {
    @Inject(at=@At("HEAD"),method = "tryRequest",cancellable = true)
    public void noRequest(String message, CallbackInfo ci) {
        Addon.LOG.info("Client tried to request chatpreview from server");
        ci.cancel();
    }
    @Inject(at=@At("HEAD"),method = "onResponse",cancellable = true)
    public void noModify(int id, Text text, CallbackInfo ci) {
        Addon.LOG.info("Server tried to modify chatpreview to "+text.toString());
        ci.cancel();
    }
}