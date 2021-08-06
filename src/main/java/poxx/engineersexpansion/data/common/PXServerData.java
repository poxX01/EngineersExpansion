package poxx.engineersexpansion.data.common;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import poxx.engineersexpansion.EngineersExpansion;

@Mod.EventBusSubscriber(modid = EngineersExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
final class PXServerData {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        dataGenerator.addProvider(new PXLootTableProvider(dataGenerator));
        dataGenerator.addProvider(new PXBlockTagsProvider(dataGenerator, existingFileHelper));
    }
}
