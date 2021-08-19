package poxx.engineersexpansion.common.blocks.steelrails;

import net.minecraft.block.BlockState;
import net.minecraft.block.DetectorRailBlock;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import poxx.engineersexpansion.common.blocks.PXBlockProperties;

public class SteelRailDetector extends DetectorRailBlock {
    public SteelRailDetector(){
        super(PXBlockProperties.STEEL_RAIL_PROPERTIES);
    }
    @Override
    public float getRailMaxSpeed(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart)
    {
        return 1.2f;
    }
}
