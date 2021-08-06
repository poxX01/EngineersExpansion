package poxx.engineersexpansion.data.client;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import poxx.engineersexpansion.EngineersExpansion;
import poxx.engineersexpansion.common.PXContent;

final class PXItemModelProvider extends ItemModelProvider {
    PXItemModelProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper){
        super(dataGenerator, EngineersExpansion.MODID, existingFileHelper);
    }

    public void registerModels(){
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        for (RegistryObject<Block> registryObject : PXContent.PXBlocks.BLOCK_REGISTER.getEntries()){
            Block currentBlock = registryObject.get();
            if (currentBlock instanceof AbstractRailBlock){
                String currentPath = currentBlock.getRegistryName().getPath();
                getBuilder(currentPath).parent(itemGenerated).texture("layer0", "block/" + currentPath);
            }
        }
    }
}
