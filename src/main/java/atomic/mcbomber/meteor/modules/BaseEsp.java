package atomic.mcbomber.meteor.modules;

import meteordevelopment.meteorclient.utils.player.ChatUtils;
import atomic.mcbomber.meteor.Addon;
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