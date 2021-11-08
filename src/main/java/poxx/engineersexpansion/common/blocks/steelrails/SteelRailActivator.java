package poxx.engineersexpansion.common.blocks.steelrails;

import blusunrize.immersiveengineering.common.items.HammerItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import poxx.engineersexpansion.common.blocks.PXBlockProperties;

import java.util.function.Predicate;

public class SteelRailActivator extends PoweredRailBlock {
    public SteelRailActivator(){
        super(PXBlockProperties.STEEL_RAIL_PROPERTIES, false);
    }
    @Override
    public void attack(BlockState blockState, World level, BlockPos blockPos, PlayerEntity player) {
        Predicate<Item> itemOfHammerType = item -> item.getToolTypes(new ItemStack(item)).contains(HammerItem.HAMMER_TOOL);
        if (!level.isClientSide && player.isHolding(itemOfHammerType)){
            boolean hasAddedToPlayerInventory = !player.inventory.add(new ItemStack(this.asItem()));
            level.destroyBlock(blockPos, hasAddedToPlayerInventory);
        }
    }
    @Override
    public float getRailMaxSpeed(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart)
    {
        return 1.2f;
    }

}
