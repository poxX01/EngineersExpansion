package poxx.engineersexpansion.common.items;

import blusunrize.immersiveengineering.common.util.EnergyHelper;
import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import poxx.engineersexpansion.common.PXContent;

import javax.annotation.Nullable;

public class Tachometer extends Item implements EnergyHelper.IIEEnergyItem {
    private static int maxEnergy = 15*20*60; //20 ticks per s, 60s per min, lasts 15min when on (at consumption of 1unit/tick)
    private LazyOptional<IEnergyStorage> energyStorage;

    public Tachometer(){
        super(new Item.Properties().tab(PXContent.ITEMGROUP));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        return null;
    }

    public int getMaxEnergyStored(ItemStack var1){
        return maxEnergy;
    }
    @Override
    public int receiveEnergy(ItemStack container, int energy, boolean simulate) {
        //Multiply energy given by 22, to approximately simulate the item having less energy yet still make it last long
        //as energy consumption has to be a minimum of 1
        //TODO this can still consume too much power if it's near max charge and a capacitor is equipped
        return ItemNBTHelper.insertFluxItem(container, energy*22, this.getMaxEnergyStored(container), simulate);
    }
    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected){
        if (isOn(itemStack)){
            this.extractEnergy(itemStack, 1, false);
            if (this.getEnergyStored(itemStack) == 0){
                ItemNBTHelper.putBoolean(itemStack, "isOn",false);
            }
            else if (hasAutoOff(itemStack) && !isSelected){
                advanceAutoOffTick(itemStack);
            }
        }
    }
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand){
        ItemStack useItem = playerEntity.getItemInHand(hand);
        if (!(world.isClientSide())){
            if (playerEntity.getPose() == Pose.CROUCHING) {
                toggleBooleanProperty(useItem, "autoOff");
            }
            else {
                toggleBooleanProperty(useItem, "isOn");
            }
        }
        return ActionResult.pass(useItem);

//        if (world.isClientSide()){
//            if (playerEntity.isPassenger() && playerEntity.getRootVehicle() instanceof MinecartEntity)
//                EngineersExpansion.LOGGER.debug("Tachometer used in a Minecart by a player! Speed: "
//                        +(Math.abs(MathHelper.clamp(playerEntity.getRootVehicle().getDeltaMovement().x,
//                        -((MinecartEntity) playerEntity.getRootVehicle()).getMaxSpeedWithRail(),
//                        ((MinecartEntity) playerEntity.getRootVehicle()).getMaxSpeedWithRail()))
//                        +
//                        +Math.abs(MathHelper.clamp(playerEntity.getRootVehicle().getDeltaMovement().z,
//                        -((MinecartEntity) playerEntity.getRootVehicle()).getMaxSpeedWithRail(),
//                        ((MinecartEntity) playerEntity.getRootVehicle()).getMaxSpeedWithRail())))*20);
//        }
//        return ActionResult.pass(playerEntity.getItemInHand(hand));
    }

    private boolean isOn(ItemStack itemStack) {
        return ItemNBTHelper.getBoolean(itemStack, "isOn");
    }
    private boolean hasAutoOff(ItemStack itemStack){
        return ItemNBTHelper.getBoolean(itemStack, "autoOff");
    }
    private int getAutoOffTick(ItemStack itemStack){
        return ItemNBTHelper.getInt(itemStack, "autoOffTick");
    }
    private void toggleBooleanProperty(ItemStack itemStack, String itemProperty){
        ItemNBTHelper.putBoolean(itemStack, itemProperty, !ItemNBTHelper.getBoolean(itemStack, itemProperty));
    }
    private void advanceAutoOffTick(ItemStack itemStack){
        if (hasAutoOff(itemStack)){
            int autoOffTick = getAutoOffTick(itemStack);
            if (autoOffTick+1 == 300){ItemNBTHelper.putBoolean(itemStack, "isOn", false);}
            ItemNBTHelper.putInt(itemStack, "autoOffTick", autoOffTick+1);
        }
    }
}
