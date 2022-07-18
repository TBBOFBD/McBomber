package atomic.mcbomber;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Iterator;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import net.minecraft.item.Items;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.HUD;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.Module;




import atomic.mcbomber.modules.BaseEsp;

import atomic.mcbomber.commands.bind;
import atomic.mcbomber.commands.home;

import atomic.mcbomber.hud.Hud;



public class Addon extends MeteorAddon {
	public static final File FOLDER = MeteorClient.FOLDER;
	public static final Logger LOG = LoggerFactory.getLogger(Addon.class);
	public static final Category CATEGORY = new Category("McBomber", Items.TNT_MINECART.getDefaultStack());
	public static final HttpClient HTTPCLIENT = HttpClient.newHttpClient();

	@Override
	public void onInitialize() {
		LOG.info("Initializing Mcbomber MeteorAddon");

		// Required when using @EventHandler
		MeteorClient.EVENT_BUS.registerLambdaFactory("meteordevelopment.addons.template", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
		LOG.trace("Registered Lambda");

		// Modules
		Modules.get().add(new BaseEsp());
		LOG.trace("Loaded Modules");

		// Commands
		Commands.get().add(new bind());
		Commands.get().add(new home());
		LOG.trace("Loaded Commands");

		// HUD
		HUD.get().elements.add(new Hud());
		LOG.trace("Loaded Hud");
		
		LOG.trace("connecting to localhost:6969");

		// Broadcasting 
		var resmodules = new HashMap<String,String>(){{}};
		Iterator<Module> modules = Modules.get().getAll().iterator();
		while(modules.hasNext()){
			Module nxtmodule = modules.next();
			resmodules.put(nxtmodule.name, nxtmodule.toString());
		}

		if(broadcast("TEST") != "OK"){
			LOG.error("McBomber Failed To Connect");
		}
	}

	@Override
	public void onRegisterCategories() {
		Modules.registerCategory(CATEGORY);
	}


	public static String broadcast(){
		return broadcast("/");
	}

	public static String broadcast(String path){
        try {
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:6969" + path))
				.build();
			HttpResponse<String> response = HTTPCLIENT.send(request,
			    HttpResponse.BodyHandlers.ofString());

			return response.body();
		} catch (IOException _e) {
			return "ERROR";
		} catch (InterruptedException _e) {
			return "ERROR";
		}
	}
}