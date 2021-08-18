package poxx.engineersexpansion.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public abstract class PXBlockProperties {
    public static final AbstractBlock.Properties STEEL_RAIL_PROPERTIES = AbstractBlock.Properties
            .of(Material.METAL)
            .sound(SoundType.METAL)
            .strength(0.7F, 5F)
            .harvestLevel(0)
            .noCollission()
            .noOcclusion(); //Only needed when using 3D block models
}
