package poxx.engineersexpansion;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import poxx.engineersexpansion.common.PXContent;

@Mod(EngineersExpansion.MODID)
public final class EngineersExpansion {
    public static final String MODID = "engineersexpansion";
    public static final String MODDISPLAYNAME = "Engineer's Expansion";
    public static final String PROJECTDIRNAME = "EngineersExpansion";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public EngineersExpansion(){
        PXContent.construct();
    }
}
