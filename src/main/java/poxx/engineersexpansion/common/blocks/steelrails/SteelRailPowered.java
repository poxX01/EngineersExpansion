package poxx.engineersexpansion.common.blocks.steelrails;

import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import poxx.engineersexpansion.common.blocks.PXBlockProperties;

public class SteelRailPowered extends PoweredRailBlock {
    public SteelRailPowered(){
        super(PXBlockProperties.STEEL_RAIL_PROPERTIES, true);
    }

    @Override
    public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart){
        if (state.getValue(PoweredRailBlock.POWERED)){
            Vector3d movementVector = cart.getDeltaMovement();
            double deltaMovement = Math.sqrt(Entity.getHorizontalDistanceSqr(movementVector));
            if (deltaMovement > 0.01D){
                cart.setDeltaMovement(movementVector
                        .add(movementVector.x / deltaMovement * 0.12D, 0.0D, movementVector.z / deltaMovement * 0.12D));
            }
        }
    }

    @Override
    public float getRailMaxSpeed(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart)
    {
        return 1.2f;
    }
}
