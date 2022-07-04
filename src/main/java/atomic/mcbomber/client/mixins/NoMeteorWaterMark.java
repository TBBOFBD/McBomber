package atomic.mcbomber.client.mixins;

import meteordevelopment.meteorclient.systems.config.Config;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mixin(SplashTextResourceSupplier.class)
public class NoMeteorWaterMark {
    private boolean override = true;
    private final Random random = new Random();

    private final List<String> Splashes = getSplashes();

    @Inject(method = "get", at = @At("HEAD"), cancellable = false)
    private void onApply(CallbackInfoReturnable<String> cir) {
        if(Config.get() == null || !Config.get().titleScreenSplashes.get()) return;

        if(override){
            cir.setReturnValue(
                Splashes.get(
                    random.nextInt(
                        Splashes.size()
                    )
                )
            );
        }
        override = !override;
    }

    private static List<String> getSplashes() {
        return Arrays.asList(
                "Hypernite on Crack!",
                "Best utility mod.",
                "§6Atomic §fon fire",
                "§4Hypernite Client"
        );
    }

}