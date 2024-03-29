package poxx.engineersexpansion.common;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import poxx.engineersexpansion.common.blocks.steelrails.*;
import poxx.engineersexpansion.common.items.Tachometer;

import static poxx.engineersexpansion.EngineersExpansion.MODID;

public final class PXContent {
    public static final ItemGroup ITEMGROUP = new ItemGroup(MODID) {
        public ItemStack makeIcon() { return new ItemStack(PXBlocks.STEEL_RAIL.get());}
    };

    public static final class PXBlocks {
        public static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
        public static final RegistryObject<Block> STEEL_RAIL = BLOCK_REGISTER.register("steel_rail", SteelRail::new);
        public static final RegistryObject<Block> STEEL_RAIL_INTERSECTION = BLOCK_REGISTER.register("steel_rail_intersection", SteelRailIntersection::new);
        public static final RegistryObject<Block> STEEL_RAIL_POWERED = BLOCK_REGISTER.register("powered_steel_rail", SteelRailPowered::new);
        public static final RegistryObject<Block> STEEL_RAIL_ACTIVATOR = BLOCK_REGISTER.register("activator_steel_rail", SteelRailActivator::new);
        public static final RegistryObject<Block> STEEL_RAIL_DETECTOR = BLOCK_REGISTER.register("detector_steel_rail", SteelRailDetector::new);

        static void buildBlockItems(){
            for (RegistryObject<Block> block : BLOCK_REGISTER.getEntries()){
                PXItems.ITEM_REGISTER.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(ITEMGROUP)));
            }
        }
    }

    public static abstract class PXItems {
        public static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

        public static final RegistryObject<Item> TACHOMETER = ITEM_REGISTER.register("tachometer", Tachometer::new);
    }

    public static void construct() {
        PXBlocks.buildBlockItems();
        PXBlocks.BLOCK_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        PXItems.ITEM_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
