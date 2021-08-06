package poxx.engineersexpansion.data.common;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import poxx.engineersexpansion.common.PXContent;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

final class PXLootTableProvider extends LootTableProvider {
    PXLootTableProvider(DataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(PXBlockLootTables::new, LootParameterSets.BLOCK)
        );
    }
    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((p_218436_2_, p_218436_3_) -> {
            LootTableManager.validate(validationtracker, p_218436_2_, p_218436_3_);
        });
    }

    static final class PXBlockLootTables extends BlockLootTables{
        @Override
        protected void addTables() {
            for (RegistryObject<Block> registryObject : PXContent.PXBlocks.BLOCK_REGISTER.getEntries()) {
                Block currentBlock = registryObject.get();
                dropSelf(currentBlock);
            }
        }
        @Override
        protected Iterable<Block> getKnownBlocks(){
            return PXContent.PXBlocks.BLOCK_REGISTER.getEntries().stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }
    }
}
