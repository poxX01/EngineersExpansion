package poxx.engineersexpansion.data.client;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import poxx.engineersexpansion.EngineersExpansion;
import poxx.engineersexpansion.common.PXContent.PXBlocks;
import poxx.engineersexpansion.common.blocks.steelrails.SteelRailPowered;

final class PXBlockStateProvider extends BlockStateProvider {
    final Logger logger = LogManager.getLogger(EngineersExpansion.MODID);

    PXBlockStateProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper){
        super(dataGenerator, EngineersExpansion.MODID, existingFileHelper);
    }
    public void registerStatesAndModels(){
        ModelFile itemGenerated = itemModels().getExistingFile(mcLoc("item/generated"));

        //Steel Rail
        getVariantBuilder(PXBlocks.STEEL_RAIL.get()).forAllStates(this::getBasicRailModel);
        getVariantBuilder(PXBlocks.STEEL_RAIL_INTERSECTION.get()).forAllStates(blockState ->
                new ConfiguredModel[] {new ConfiguredModel(models().getExistingFile(modLoc("block/steel_rail_intersection")))});

        PXBlocks.BLOCK_REGISTER.getEntries().stream()
                .filter(registryObject -> registryObject.get().getStateDefinition()
                        .getProperties().contains(BlockStateProperties.POWERED))
                .forEach(this::buildPoweredRailStates);
        //Powered Steel Rail
//        for (Boolean isPowered : SteelRailPowered.POWERED.getPossibleValues()) {
//            ModelFile flatModel = !isPowered ? models().getExistingFile(modLoc("block/powered_steel_rail_flat")) :
//                    models().withExistingParent("powered_steel_rail_flat_on", modLoc(myPath+"_flat"))
//                            .texture("0", modLoc("block/"+myPath+"_on"))
//                            .texture("particle", modLoc("block/"+myPath+"_on"));
//            ModelFile raisedModel = !isPowered ? models().getExistingFile(modLoc("block/powered_steel_rail_raised")) :
//                    models().withExistingParent("powered_steel_rail_raised_on", modLoc(myPath+"_raised"))
//                            .texture("0", modLoc("block/"+myPath+"_on"))
//                            .texture("particle", modLoc("block/"+myPath+"_on"));
//            for (RailShape railShape : SteelRailPowered.SHAPE.getPossibleValues()) {
//                ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder> currentBuilder = getMultipartBuilder(PXBlocks.STEEL_RAIL_POWERED.get()).part();
//                if (!railShape.isAscending()) {
//                    currentBuilder.modelFile(flatModel);
//                } else {
//                    currentBuilder.modelFile(raisedModel);
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
    private ConfiguredModel[] getBasicRailModel(BlockState blockState){
        String path = blockState.getBlock().getRegistryName().getPath();
        RailShape railShape = blockState.getValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT);
        ConfiguredModel.Builder<?> currentBuilder = ConfiguredModel.builder();
        currentBuilder.modelFile(!railShape.isAscending() ? getExistingModBlockModel(path+"_flat") :
                getExistingModBlockModel(path+"_raised"));
        switch (railShape) {
            case EAST_WEST:
            case ASCENDING_EAST: currentBuilder.rotationY(90); break;
            case ASCENDING_SOUTH: currentBuilder.rotationY(180); break;
            case ASCENDING_WEST: currentBuilder.rotationY(270); break;
        }
        return currentBuilder.build();
    }
    private void buildPoweredRailStates(RegistryObject<Block> registryObject){
        String path = registryObject.getId().getPath();
        for (Boolean isPowered : BlockStateProperties.POWERED.getPossibleValues()) {
            ModelFile flatModel = !isPowered ? getExistingModBlockModel(path+"_flat") :
                    models().withExistingParent(path+"_flat_on", modLoc(path+"_flat"))
                            .texture("0", modLoc("block/"+path+"_on"))
                            .texture("particle", modLoc("block/"+path+"_on"));
            ModelFile raisedModel = !isPowered ? getExistingModBlockModel(path+"_raised") :
                    models().withExistingParent(path+"_raised_on", modLoc(path+"_raised"))
                            .texture("0", modLoc("block/"+path+"_on"))
                            .texture("particle", modLoc("block/"+path+"_on"));
            for (RailShape railShape : BlockStateProperties.RAIL_SHAPE_STRAIGHT.getPossibleValues()) {
                ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder> currentBuilder = getMultipartBuilder(registryObject.get()).part();
                if (!railShape.isAscending()) {
                    currentBuilder.modelFile(flatModel);
                } else {
                    currentBuilder.modelFile(raisedModel);
                }
                switch (railShape) {
                    case EAST_WEST:
                    case ASCENDING_EAST: currentBuilder.rotationY(90); break;
                    case ASCENDING_SOUTH: currentBuilder.rotationY(180); break;
                    case ASCENDING_WEST: currentBuilder.rotationY(270); break;
                }
                currentBuilder.addModel().condition(SteelRailPowered.SHAPE, railShape).condition(SteelRailPowered.POWERED, isPowered);
            }
        }
    }

    private ModelFile getExistingModBlockModel(String path){
        return models().getExistingFile(modLoc("block/"+path));
    }
}
