package poxx.engineersexpansion;

import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import poxx.engineersexpansion.client.MinecartSpeedPropertyGetter;
import poxx.engineersexpansion.common.PXContent;

@Mod(EngineersExpansion.MODID)
public final class EngineersExpansion {
    public static final String MODID = "engineersexpansion";
    public static final String MODDISPLAYNAME = "Engineer's Expansion";
    public static final String PROJECTDIRNAME = "EngineersExpansion";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public EngineersExpansion(){
        PXContent.construct();

        IEventBus loadingEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        loadingEventBus.addListener(this::setupClient);
    }

    private void setupClient(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            ItemModelsProperties.register(PXContent.PXItems.TACHOMETER.get(),
                    new ResourceLocation(MODID, "minecart_speed"),
                    new MinecartSpeedPropertyGetter());
        });
    }
}
