package poxx.engineersexpansion;

import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import poxx.engineersexpansion.client.MinecartSpeedPropertyGetter;
import poxx.engineersexpansion.client.OnOffPropertyGetter;
import poxx.engineersexpansion.common.PXContent;
import poxx.engineersexpansion.server.capabilities.powereddevice.CapabilityPoweredDevice;

@Mod(EngineersExpansion.MODID)
public final class EngineersExpansion {
    public static final String MODID = "engineersexpansion";
    public static final String MODDISPLAYNAME = "Engineer's Expansion";
    public static final String PROJECTDIRNAME = "EngineersExpansion";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public EngineersExpansion(){
        PXContent.construct();

        final IEventBus loadingEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        loadingEventBus.addListener(this::setupCommon);
        loadingEventBus.addListener(this::setupClient);

        MinecraftForge.EVENT_BUS.register(this);
    }
    private void setupCommon(FMLCommonSetupEvent event){
        CapabilityPoweredDevice.register();
    }

    private void setupClient(FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            ItemModelsProperties.register(PXContent.PXItems.TACHOMETER.get(),
                    new ResourceLocation(MODID, "minecart_speed"),
                    new MinecartSpeedPropertyGetter());
            ItemModelsProperties.register(PXContent.PXItems.TACHOMETER.get(),
                    new ResourceLocation(MODID, "is_on"),
                    new OnOffPropertyGetter());
        });
    }
}
