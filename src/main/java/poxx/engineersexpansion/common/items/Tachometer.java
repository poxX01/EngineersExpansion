package poxx.engineersexpansion.common.items;

import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import poxx.engineersexpansion.EngineersExpansion;
import poxx.engineersexpansion.common.PXContent;

public class Tachometer extends Item {

    public Tachometer(){
        super(new Item.Properties().tab(PXContent.ITEMGROUP));
    }

//    @Override
//    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int num, boolean p_77663_5_){
//
//    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand){
        if (world.isClientSide()){
            if (playerEntity.isPassenger() && playerEntity.getRootVehicle() instanceof MinecartEntity)
                EngineersExpansion.LOGGER.debug("Tachometer used in a Minecart by a player! Speed: "
                        +(Math.abs(MathHelper.clamp(playerEntity.getRootVehicle().getDeltaMovement().x,
                        -((MinecartEntity) playerEntity.getRootVehicle()).getMaxSpeedWithRail(),
                        ((MinecartEntity) playerEntity.getRootVehicle()).getMaxSpeedWithRail()))
                        +
                        +Math.abs(MathHelper.clamp(playerEntity.getRootVehicle().getDeltaMovement().z,
                        -((MinecartEntity) playerEntity.getRootVehicle()).getMaxSpeedWithRail(),
                        ((MinecartEntity) playerEntity.getRootVehicle()).getMaxSpeedWithRail())))*20);
        }
        return ActionResult.pass(playerEntity.getItemInHand(hand));
    }
}
