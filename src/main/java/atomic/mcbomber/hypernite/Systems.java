package atomic.mcbomber.hypernite;

import atomicgamer.hypernite.systems.commands.Commands;
import atomicgamer.hypernite.systems.modules.Category;
import atomicgamer.hypernite.systems.modules.Modules;
import atomicgamer.hypernite.systems.hud.HudGroup;
import atomicgamer.hypernite.systems.hud.Hud;
import atomic.mcbomber.Common;
import atomic.mcbomber.hypernite.commands.*;
import atomic.mcbomber.hypernite.hud.*;
import atomic.mcbomber.hypernite.modules.*;

public class Systems {
    public static final Category CATEGORY = new Category(Common.MOD_NAME, net.minecraft.item.Items.TNT_MINECART.getDefaultStack());
    public static final HudGroup HUD_GROUP = new HudGroup(Common.MOD_NAME);


    private static void initModules() {
        Modules.get().add(new ModuleExample());
    }


    private static void initCommands() {
        Commands.get().add(new CommandExample());
    }


    private static void initHud() {
        Hud.get().register(HudExample.INFO);
    }



    public static void init() {
        initCommands();
        initModules();
        initHud();
    }

    public static void registerCategories() {
        Modules.registerCategory(CATEGORY);
    }
}
