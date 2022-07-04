package atomic.mcbomber.modules;

import atomic.mcbomber.Addon;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.systems.modules.Module;

public class BaseEsp extends Module {
    public BaseEsp() {
        super(Addon.CATEGORY, "base-esp", "highlights player-placed blocks");
    }

    @Override
    public void onActivate() {
        ChatUtils.info("BaseEsp Enabled");
    }

    @Override
    public void onDeactivate() {
        ChatUtils.info("BaseEsp Disabled");
    }
}