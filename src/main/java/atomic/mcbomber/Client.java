package atomic.mcbomber;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Common.LOG.error("Loading Fabric Metadata");
        Common.init(this);
        Common.mc = MinecraftClient.getInstance();
    }
}
