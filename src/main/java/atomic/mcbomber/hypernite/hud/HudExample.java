package atomic.mcbomber.hypernite.hud;

import atomic.mcbomber.hypernite.Systems;
import atomicgamer.hypernite.systems.hud.HudElement;
import atomicgamer.hypernite.systems.hud.HudElementInfo;
import atomicgamer.hypernite.systems.hud.HudRenderer;
import atomicgamer.hypernite.utils.render.color.Color;

public class HudExample extends HudElement {
    private static final String name = "example";
    private static final String title = "Example";
    private static final String description = "HUD element example.";

    public static final HudElementInfo<?> INFO = HEG.build(Systems.HUD_GROUP,name,title,description,
        // class name here
        HudExample
    ::new);


    public HudExample() {
        super(INFO);
    }

    @Override
    public void render(HudRenderer renderer) {
        setSize(renderer.textWidth("Example element", true), renderer.textHeight(true));

        renderer.text("Example element", x, y, Color.WHITE, true);
    }
}
