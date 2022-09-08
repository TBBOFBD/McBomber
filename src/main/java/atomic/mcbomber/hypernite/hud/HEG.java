package atomic.mcbomber.hypernite.hud;

import atomicgamer.hypernite.systems.hud.HudGroup;
import atomicgamer.hypernite.systems.hud.HudElement;
import atomicgamer.hypernite.systems.hud.HudElementInfo;

public class HEG<T extends HudElement> {
    public static HudElementInfo<?> build(HudGroup hudGroup, String name, String title, String description, java.util.function.Supplier<?> factory) {
        final HudElementInfo<?> INFO = new HudElementInfo(hudGroup,name,title,description,factory);
        return INFO;
    }
}
