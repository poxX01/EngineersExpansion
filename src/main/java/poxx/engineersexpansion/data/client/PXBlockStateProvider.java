package poxx.engineersexpansion.data.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import poxx.engineersexpansion.EngineersExpansion;

final class PXBlockStateProvider extends BlockStateProvider {
    PXBlockStateProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper){
        super(dataGenerator, EngineersExpansion.MODID, existingFileHelper);
    }
    public void registerStatesAndModels(){

    }

}
