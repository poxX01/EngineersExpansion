package poxx.engineersexpansion.server.capabilities.powereddevice;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderPoweredDevice implements ICapabilitySerializable<INBT> {
    private final PoweredDevice poweredDevice;
    private final EnergyStorage energyStorage; //Provide EnergyStorage capability with it for compability with other mods
    private final LazyOptional<PoweredDevice> capabilityInstancePoweredDevice;
    private final LazyOptional<EnergyStorage> capabilityInstanceEnergyStorage;

    public CapabilityProviderPoweredDevice(int maxEnergy, int maxTicksIdleThreshold){
        poweredDevice = new PoweredDevice(maxTicksIdleThreshold);
        energyStorage = new EnergyStorage(maxEnergy);
        capabilityInstanceEnergyStorage = LazyOptional.of(() -> energyStorage);
        capabilityInstancePoweredDevice = LazyOptional.of(() -> poweredDevice);
    }
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        LazyOptional<T> returnValue;
        if (capability == null) returnValue = LazyOptional.empty(); //required, otherwise it returns CapabilityEnergy despite being null
        else if (capability == CapabilityEnergy.ENERGY) returnValue = this.capabilityInstanceEnergyStorage.cast();
        else if (capability == CapabilityPoweredDevice.POWERED_DEVICE) returnValue = this.capabilityInstancePoweredDevice.cast();
        else returnValue = LazyOptional.empty();
        return returnValue;
    }
    @Override
    public INBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("energyStored", CapabilityEnergy.ENERGY.writeNBT(energyStorage, null));
        nbt.put("PoweredDevice", CapabilityPoweredDevice.POWERED_DEVICE.writeNBT(poweredDevice, null));
        return nbt;
    }
    @Override
    public void deserializeNBT(INBT inbt) {
        CompoundNBT nbt = (CompoundNBT) inbt;
        CapabilityEnergy.ENERGY.readNBT(energyStorage, null, nbt.get("energyStored"));
        CapabilityPoweredDevice.POWERED_DEVICE.readNBT(poweredDevice, null, nbt.get("PoweredDevice"));
    }
}