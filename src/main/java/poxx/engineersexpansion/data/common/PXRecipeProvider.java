package poxx.engineersexpansion.data.common;

import blusunrize.immersiveengineering.common.items.IEItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import poxx.engineersexpansion.common.PXContent;

import java.util.function.Consumer;

public class PXRecipeProvider extends RecipeProvider {
    PXRecipeProvider(DataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    public void buildShapelessRecipes(Consumer<IFinishedRecipe> recipeConsumer){
        ITag<Item> steelRod = ItemTags.createOptional(new ResourceLocation("forge", "rods/steel"));
        ITag<Item> copperIngot = ItemTags.createOptional(new ResourceLocation("forge", "ingots/copper"));

        ShapedRecipeBuilder.shaped(PXContent.PXBlocks.STEEL_RAIL.get(), 16)
                .define('S', steelRod)
                .define('-', IEItems.Ingredients.stickTreated)
                .pattern("S S")
                .pattern("S-S")
                .pattern("S S")
                .unlockedBy("has_item", has(steelRod))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(PXContent.PXBlocks.STEEL_RAIL_INTERSECTION.get(), 4)
                .define('S', steelRod)
                .define('R', PXContent.PXBlocks.STEEL_RAIL.get())
                .pattern(" R ")
                .pattern("RSR")
                .pattern(" R ")
                .unlockedBy("has_item", has(steelRod))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(PXContent.PXBlocks.STEEL_RAIL_POWERED.get())
                .define('C', copperIngot)
                .define('R', PXContent.PXBlocks.STEEL_RAIL.get())
                .define('D', Tags.Items.DUSTS_REDSTONE)
                .pattern(" C ")
                .pattern(" R ")
                .pattern(" D ")
                .unlockedBy("has_item", has(steelRod))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(PXContent.PXBlocks.STEEL_RAIL_ACTIVATOR.get())
                .define('T', Blocks.REDSTONE_TORCH)
                .define('R', PXContent.PXBlocks.STEEL_RAIL.get())
                .define('D', Tags.Items.DUSTS_REDSTONE)
                .pattern(" T ")
                .pattern(" R ")
                .pattern(" D ")
                .unlockedBy("has_item", has(steelRod))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(PXContent.PXBlocks.STEEL_RAIL_DETECTOR.get())
                .define('P', Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE)
                .define('R', PXContent.PXBlocks.STEEL_RAIL.get())
                .define('D', Tags.Items.DUSTS_REDSTONE)
                .pattern(" P ")
                .pattern(" R ")
                .pattern(" D ")
                .unlockedBy("has_item", has(steelRod))
                .save(recipeConsumer);
    }
}