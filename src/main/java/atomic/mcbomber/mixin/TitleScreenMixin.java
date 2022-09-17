package atomic.mcbomber.mixin;

import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import atomic.mcbomber.Common;
import atomicgamer.hypernite.utils.render.color.Color;


@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    private static boolean firstTimeTitleScreen = true;
    private static MinecraftClient mc = Common.mc;
    private static int color = new Color(185,208,195).getPacked();


    public TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
            ordinal = 0
        )
    )
    private void onFullLoad(MatrixStack _matrices, int _mouseX, int _mouseY, float _delta, CallbackInfo _info) {
        if(firstTimeTitleScreen) {
            firstTimeTitleScreen = false;
            Common.init();
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        String printHover = Common.MOD_ID;
        String printNonHover = Common.MOD_NAME;

        if(mouseX <=50 && mouseY <= 10){
            mc.textRenderer.drawWithShadow(matrices,printHover,3,3,color);
        } else {
            mc.textRenderer.drawWithShadow(matrices,printNonHover,3,3,color);
        }
    }
}
