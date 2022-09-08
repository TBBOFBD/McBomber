package atomic.mcbomber.hypernite.modules;

import atomic.mcbomber.hypernite.Systems;
import atomicgamer.hypernite.systems.modules.Module;

public class ModuleExample extends Module {
    public ModuleExample() {
        super(Systems.CATEGORY, "example", "An example module in a custom category.");
    }
}
