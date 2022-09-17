package atomic.mcbomber;

import atomic.mcbomber.hypernite.Systems;
import atomicgamer.hypernite.addons.HyperniteAddon;

public class Addon extends HyperniteAddon {
    @Override
    public void onInitialize() {
        Common.Initialiser.init(this);
        Systems.init();
    }

    @Override
    public void onRegisterCategories() {
        Systems.registerCategories();
    }

    @Override
    public String getPackage() {
        return String.format(Common.PACKAGE + "." + Common.MOD_ID);
    }
}
