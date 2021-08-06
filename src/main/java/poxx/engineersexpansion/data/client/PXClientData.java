package poxx.engineersexpansion.data.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import poxx.engineersexpansion.EngineersExpansion;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = EngineersExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
final class PXClientData {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        dataGenerator.addProvider(new PXLanguageProvider(dataGenerator));
        dataGenerator.addProvider(new PXBlockStateProvider(dataGenerator, existingFileHelper));
        dataGenerator.addProvider(new PXItemModelProvider(dataGenerator, existingFileHelper));
    }
}
