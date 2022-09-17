package atomic.mcbomber;

import net.minecraft.client.MinecraftClient;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Common {
    public static final String PACKAGE = "atomic";
    public static final String MOD_ID = "mcbomber";
    public static final String MOD_NAME = "McBomber";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOG.info("Initialized " + Common.MOD_NAME + "\nInit type: " + INIT_TYPE);

        if(INIT_TYPE.equals(Initialiser.HYPERNITE)){
            LOG.error("Loaded with Hypernite");
        } else {
            LOG.error("Loaded WITHOUT Hypernite, it will still work, just not completely");
        }
    }



    public static MinecraftClient mc;
    public static Initialiser INIT_TYPE = Initialiser.OTHER;
    enum Initialiser {
        HYPERNITE,
        FABRIC,
        OTHER;
        static void init(Object obj) {
            if(obj.getClass().getName().equals(Addon.class.getName())){
                Common.INIT_TYPE = Common.Initialiser.HYPERNITE;
            }
            if(obj.getClass().getName().equals(Client.class.getName())){
                if(INIT_TYPE.equals(Initialiser.OTHER)){
                    Common.INIT_TYPE = Common.Initialiser.FABRIC;
                }
            }
        }
    }
}
