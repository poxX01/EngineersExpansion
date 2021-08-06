package poxx.engineersexpansion.data.common;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import poxx.engineersexpansion.EngineersExpansion;
import poxx.engineersexpansion.common.PXContent;

final class PXBlockTagsProvider extends BlockTagsProvider {
    PXBlockTagsProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper){
        super(dataGenerator, EngineersExpansion.MODID, existingFileHelper);
    }

    public void addTags(){
        for (RegistryObject<Block> registryObject : PXContent.PXBlocks.BLOCK_REGISTER.getEntries()){
            Block currentBlock = registryObject.get();
            //Good performance to do an elseif chain, despite the looks: https://stackoverflow.com/questions/50946488/java-instanceof-vs-class-name-switch-performance
            if (currentBlock instanceof AbstractRailBlock){
                this.tag(BlockTags.RAILS).add(currentBlock);
            }
        }
    }
}
