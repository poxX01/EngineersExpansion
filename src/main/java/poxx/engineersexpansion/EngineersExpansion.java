package poxx.engineersexpansion;

import net.minecraftforge.fml.common.Mod;
import poxx.engineersexpansion.common.PXContent;

@Mod(EngineersExpansion.MODID)
public final class EngineersExpansion {
    public static final String MODID = "engineersexpansion";
    public static final String MODDISPLAYNAME = "Engineer's Expansion";
    public static final String MODVERSION = "";

    public EngineersExpansion(){
        PXContent.construct();
    }
}
