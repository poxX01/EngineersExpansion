package poxx.engineersexpansion.common;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import poxx.engineersexpansion.common.blocks.steelrails.SteelRail;
import poxx.engineersexpansion.common.blocks.steelrails.SteelRailIntersection;
import poxx.engineersexpansion.common.blocks.steelrails.SteelRailPowered;
import poxx.engineersexpansion.common.items.Tachometer;

import static poxx.engineersexpansion.EngineersExpansion.MODID;

public final class PXContent {
    public static final ItemGroup ITEMGROUP = new ItemGroup(MODID) {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() { return new ItemStack(PXBlocks.STEEL_RAIL.get());}
    };

    public static final class PXBlocks {
        public static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
        public static final RegistryObject<Block> STEEL_RAIL = BLOCK_REGISTER.register("steel_rail", SteelRail::new);
        public static final RegistryObject<Block> STEEL_RAIL_INTERSECTION = BLOCK_REGISTER.register("steel_rail_intersection", SteelRailIntersection::new);
        public static final RegistryObject<Block> STEEL_RAIL_POWERED = BLOCK_REGISTER.register("powered_steel_rail", SteelRailPowered::new);

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
