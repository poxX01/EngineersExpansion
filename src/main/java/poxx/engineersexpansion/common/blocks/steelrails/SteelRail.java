package poxx.engineersexpansion.common.blocks.steelrails;

import blusunrize.immersiveengineering.common.items.HammerItem;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import poxx.engineersexpansion.common.blocks.PXBlockProperties;

import java.util.function.Predicate;

public class SteelRail extends AbstractRailBlock {
    protected static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;

    public SteelRail() {
        super(true, PXBlockProperties.STEEL_RAIL_PROPERTIES);
        this.registerDefaultState(this.stateDefinition.any().setValue(SHAPE, RailShape.NORTH_SOUTH));
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
    public float getRailMaxSpeed(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
        return 1.2f;
    }
    //Default IE Hammer behaviour relies on this method
    @Override
    public BlockState rotate(BlockState blockState, Rotation direction) {
        RailShape railshape = blockState.getValue(SHAPE);
        switch(direction) {
            case CLOCKWISE_180:
                switch(railshape) {
                    case ASCENDING_EAST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                }
            case COUNTERCLOCKWISE_90:
                switch(railshape) {
                    case ASCENDING_EAST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_WEST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_NORTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_SOUTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case NORTH_SOUTH:
                        return blockState.setValue(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return blockState.setValue(SHAPE, RailShape.NORTH_SOUTH);
                }
            case CLOCKWISE_90:
                switch(railshape) {
                    case ASCENDING_EAST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_WEST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_NORTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_SOUTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case NORTH_SOUTH:
                        return blockState.setValue(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return blockState.setValue(SHAPE, RailShape.NORTH_SOUTH);
                }
            default:
                return blockState;
        }
    }
    //Not sure if needed, might be used by IEs turntable or the like
    @Override
    public BlockState mirror(BlockState blockState, Mirror direction) {
        RailShape railshape = blockState.getValue(SHAPE);
        switch(direction) {
            case LEFT_RIGHT:
                switch(railshape) {
                    case ASCENDING_NORTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                }
            case FRONT_BACK:
                switch(railshape) {
                    case ASCENDING_EAST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return blockState.setValue(SHAPE, RailShape.ASCENDING_EAST);
                }
        }
        return super.mirror(blockState, direction);
    }

    @Deprecated
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SHAPE);
    }
}
