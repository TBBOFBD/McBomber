package atomic.mcbomber.meteor;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.HUD;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import atomic.mcbomber.meteor.commands.home;
import atomic.mcbomber.meteor.modules.BaseEsp;
import atomic.mcbomber.meteor.modules.PlayerStalker;
import atomic.mcbomber.meteor.modules.hud.Hud;



public class Addon extends MeteorAddon {
	public static final File FOLDER = MeteorClient.FOLDER;
	public static final Logger LOG = LoggerFactory.getLogger(Addon.class);
	public static final Category CATEGORY = new Category("McBomber");

	@Override
	public void onInitialize() {
		LOG.info("Initializing Mcbomber MeteorAddon");

		// Required when using @EventHandler
		MeteorClient.EVENT_BUS.registerLambdaFactory("meteordevelopment.addons.template", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

		// Modules
		Modules.get().add(new BaseEsp());
		Modules.get().add(new PlayerStalker());

		// Commands
		Commands.get().add(new home());

		// HUD
        HUD.get().elements.add(new Hud());
	}

	@Override
	public void onRegisterCategories() {
		Modules.registerCategory(CATEGORY);
	}
}
