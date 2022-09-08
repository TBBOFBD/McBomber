package atomic.mcbomber.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import atomic.mcbomber.Common;

@Mixin(TitleScreen.class)
public class MainMixin {
    @Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		Common.LOG.info("Mixins loaded");
	}
}
