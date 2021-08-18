package poxx.engineersexpansion.data.client;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.RailShape;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import poxx.engineersexpansion.EngineersExpansion;
import poxx.engineersexpansion.common.PXContent.PXBlocks;
import poxx.engineersexpansion.common.blocks.steelrails.SteelRail;

final class PXBlockStateProvider extends BlockStateProvider {
    final Logger logger = LogManager.getLogger(EngineersExpansion.MODID);

    PXBlockStateProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper){
        super(dataGenerator, EngineersExpansion.MODID, existingFileHelper);
    }
    public void registerStatesAndModels(){
        ModelFile itemGenerated = itemModels().getExistingFile(mcLoc("item/generated"));

        //Steel Rail
        for (RailShape railShape : SteelRail.SHAPE.getPossibleValues()) {
            ConfiguredModel.Builder currentBuilder = ConfiguredModel.builder();
            if (!railShape.isAscending()) {
                currentBuilder.modelFile(models().getExistingFile(modLoc("block/steel_rail_flat")));
            } else {
                currentBuilder.modelFile(models().getExistingFile(modLoc("block/steel_rail_raised")));
            }
            switch (railShape) {
                case EAST_WEST:
                case ASCENDING_EAST: currentBuilder.rotationY(90); break;
                case ASCENDING_SOUTH: currentBuilder.rotationY(180); break;
                case ASCENDING_WEST: currentBuilder.rotationY(270); break;
            }
            getVariantBuilder(PXBlocks.STEEL_RAIL.get()).partialState().with(SteelRail.SHAPE, railShape).setModels(currentBuilder.build());
        }

        getVariantBuilder(PXBlocks.STEEL_RAIL_INTERSECTION.get()).forAllStates(blockState ->
                new ConfiguredModel[] {new ConfiguredModel(models().getExistingFile(modLoc("block/steel_rail_intersection")))});

        //Powered Steel Rail
//        for (Boolean isPowered : SteelRailPowered.POWERED.getPossibleValues()) {
//            String powerStatus = isPowered ? "_on" : "_off";
//            for (RailShape railShape : SteelRailPowered.SHAPE.getPossibleValues()) {
//                ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder> currentBuilder = getMultipartBuilder(PXBlocks.STEEL_RAIL.get()).part();
//                if (!railShape.isAscending()) {
//                    currentBuilder.modelFile(models().getExistingFile(modLoc("block/powered_steel_rail_flat" + powerStatus)));
//                } else {
//                    currentBuilder.modelFile(models().getExistingFile(modLoc("block/powered_steel_rail_raised" + powerStatus)));
//                }
//                switch (railShape) {
//                    case EAST_WEST:
//                    case ASCENDING_EAST: currentBuilder.rotationY(90); break;
//                    case ASCENDING_SOUTH: currentBuilder.rotationY(180); break;
//                    case ASCENDING_WEST: currentBuilder.rotationY(270); break;
//                }
//                currentBuilder.addModel().condition(SteelRailPowered.SHAPE, railShape).condition(SteelRailPowered.POWERED, isPowered);
//            }
//        }



        for (RegistryObject<Block> registryObject : PXBlocks.BLOCK_REGISTER.getEntries()){
            Block currentBlock = registryObject.get();
            if (currentBlock instanceof AbstractRailBlock){
                String currentPath = currentBlock.getRegistryName().getPath();
                itemModels().getBuilder(currentPath).parent(itemGenerated).texture("layer0", "block/" + currentPath);
            }
        }
    }
//    private ConfiguredModel getRailModel(BlockState blockState){
//        String path = blockState.getBlock().getRegistryName().getPath();
//        RailShape railShape = blockState.getValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT);
//        if (!railShape.isAscending()) {
//            currentBuilder.modelFile(models().getExistingFile(modLoc("block/steel_rail_flat")));
//        } else {
//            currentBuilder.modelFile(models().getExistingFile(modLoc("block/steel_rail_raised")));
//        }
//        switch (railShape) {
//            case EAST_WEST:
//            case ASCENDING_EAST: currentBuilder.rotationX(90); break;
//            case ASCENDING_SOUTH: currentBuilder.rotationX(180); break;
//            case ASCENDING_WEST: currentBuilder.rotationX(270); break;
//        }
//        return
//    }
}
