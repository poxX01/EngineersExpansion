package poxx.engineersexpansion.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import poxx.engineersexpansion.common.PXContent;
import poxx.engineersexpansion.data.client.PXLanguageProvider;
import poxx.engineersexpansion.server.capabilities.powereddevice.CapabilityPoweredDevice;
import poxx.engineersexpansion.server.capabilities.powereddevice.CapabilityProviderPoweredDevice;

import javax.annotation.Nullable;
import java.util.List;

public class Tachometer extends Item {
    //time in mintutes for max power to last, assuming a constant consumption of 1unit/tick
    private static final int minutesUntilNoCharge = 8;
    private static final int maxEnergy = minutesUntilNoCharge*20*60; //20 ticks per second, 60 seconds per minute
    private static final int maxIdleTicks = 200; //ticks after which the item turns off when not in hand

    public Tachometer(){
        super(new Item.Properties().tab(PXContent.ITEMGROUP).stacksTo(1));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(final ItemStack itemStack, CompoundNBT nbt) {
        return !itemStack.isEmpty() ? new CapabilityProviderPoweredDevice(maxEnergy, maxIdleTicks) : super.initCapabilities(itemStack, nbt);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        int currentEnergy = itemStack.getShareTag() != null ? itemStack.getShareTag().getCompound("PoweredDevice").getInt("energyStored") : 0;
        list.add(new TranslationTextComponent(PXLanguageProvider.infoEnergyStored,
                currentEnergy,
                maxEnergy));
    }
    @Override
    public CompoundNBT getShareTag(ItemStack itemStack){
        CompoundNBT currentNBT = itemStack.hasTag() ?  itemStack.getTag() : new CompoundNBT();
        itemStack.getCapability(CapabilityPoweredDevice.POWERED_DEVICE).ifPresent(consumer -> {
            if (CapabilityPoweredDevice.POWERED_DEVICE != null) currentNBT.put("PoweredDevice", CapabilityPoweredDevice.POWERED_DEVICE.writeNBT(consumer, null));
        });
        return currentNBT;
    }
    @Override
    public void readShareTag(ItemStack itemStack, @Nullable CompoundNBT nbt)
    {
        itemStack.setTag(nbt);
        if (nbt == null) return;
        itemStack.getCapability(CapabilityPoweredDevice.POWERED_DEVICE).ifPresent(
                consumer -> CapabilityPoweredDevice.POWERED_DEVICE.readNBT(consumer, null, nbt.getCompound("PoweredDevice")));
    }
    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity holder, int itemSlot, boolean isSelected){
        if (!world.isClientSide){
            itemStack.getCapability(CapabilityPoweredDevice.POWERED_DEVICE).ifPresent(consumer -> {
                        consumer.useTick(1);
                        if (!isSelected) consumer.advanceIdleTick();});
        }
    }
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (!world.isClientSide){
            itemStack.getCapability(CapabilityPoweredDevice.POWERED_DEVICE).ifPresent(consumer -> {
            if (user.isShiftKeyDown()) consumer.toggleAutoOff();
            else consumer.toggleIsOn();
            });
            return ActionResult.consume(itemStack);
        }
        return ActionResult.pass(itemStack);
    }
}
