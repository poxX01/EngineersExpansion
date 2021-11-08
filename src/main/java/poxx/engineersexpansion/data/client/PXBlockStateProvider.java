package poxx.engineersexpansion.data.client;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.ResourceLocation;
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

final class PXBlockStateProvider extends BlockStateProvider {
    final Logger logger = LogManager.getLogger(EngineersExpansion.MODID);

    PXBlockStateProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper){
        super(dataGenerator, EngineersExpansion.MODID, existingFileHelper);
    }
    public void registerStatesAndModels(){
        ModelFile itemGenerated = itemModels().getExistingFile(mcLoc("item/generated"));

        //Generate block models for basic Steel Rail & the intersection
        getVariantBuilder(PXBlocks.STEEL_RAIL.get()).forAllStates(this::getBasicRailModel);
        getVariantBuilder(PXBlocks.STEEL_RAIL_INTERSECTION.get()).forAllStates(blockState ->
                new ConfiguredModel[] {new ConfiguredModel(models().getExistingFile(modLoc("block/steel_rail_intersection")))});

        //Generate block models for powered rails and their blockstates
        PXBlocks.BLOCK_REGISTER.getEntries().stream()
                .filter(registryObject -> registryObject.get().getStateDefinition()
                        .getProperties().contains(BlockStateProperties.POWERED))
                .forEach(this::buildPoweredRailStates);

        //Generate 2D item models for all registered AbstractRailBlocks
        PXBlocks.BLOCK_REGISTER.getEntries().stream()
                .filter(registryObject -> registryObject.get() instanceof AbstractRailBlock)
                .forEach(registryObject -> itemModels().withExistingParent(registryObject.getId().getPath(), itemGenerated.getLocation())
                        .texture("layer0", "block/" + registryObject.getId().getPath()));

        //TODO Add generation of predicate PoweredDevice.isOn and combine them (might need a PropertyGetter class, in which case):
        //TODO Obtain Capability via Tachometer.getShareTag(itemstack)
        //Generate Tachometer speed readout models and assign predicates to the base model
        ResourceLocation tachometerOnBasePath = modLoc("item/tachometer_base_on");
        DecimalFormat decimalFormat = new DecimalFormat("00.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        for (float f = 0F; f < 24.1F; f += 0.1F){
            String formattedString = decimalFormat.format(f);
            char[] formatChars = formattedString.toCharArray(); //fChars[2] is the decimal point
            ModelFile currentModel = itemModels().withExistingParent("tachometer/tachometer_" +
                            formattedString.replace('.', '_'), tachometerOnBasePath)
                    .texture("decimalDot", "digit/decimal_dot")
                    .texture("unit", "digit/unit_blocks_per_second")
                    .texture("digit2", "digit/digit_"+formatChars[0])
                    .texture("digit1", "digit/digit_"+formatChars[1])
                    .texture("decimalDigit1", "digit/digit_"+formatChars[3]);
            itemModels()
                    .withExistingParent("item/tachometer", tachometerOnBasePath)
                    .override().predicate(modLoc("minecart_speed"), f/24.1F)
                    .predicate(modLoc("is_on"), 1F)
                    .model(currentModel);
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
