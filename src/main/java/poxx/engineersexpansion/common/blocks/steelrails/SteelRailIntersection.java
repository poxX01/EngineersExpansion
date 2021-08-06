package poxx.engineersexpansion.common.blocks.steelrails;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public final class SteelRailIntersection extends SteelRail{
    public SteelRailIntersection(){
        super();
    }

    @Override
    public RailShape getRailDirection(BlockState state, IBlockReader world, BlockPos pos, @Nullable AbstractMinecartEntity cart){
        if (cart != null) {
            final Vector3f motionDirectionVector = cart.getMotionDirection().step();
            if (Math.abs(motionDirectionVector.x()) > Math.abs(motionDirectionVector.z())){
                return RailShape.EAST_WEST;
            }
            else{
                return RailShape.NORTH_SOUTH;
            }
        }
        return super.getRailDirection(state, world, pos, null);
    }

    @Override
    public boolean canMakeSlopes(BlockState state, IBlockReader world, BlockPos pos)
    {
        return false;
    }
}
