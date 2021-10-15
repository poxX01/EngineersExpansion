package poxx.engineersexpansion.server.capabilities.powereddevice;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import poxx.engineersexpansion.EngineersExpansion;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderPoweredDevice implements ICapabilitySerializable<INBT> {
    private PoweredDevice poweredDevice;
    private LazyOptional<PoweredDevice> capabilityInstance;

    public CapabilityProviderPoweredDevice(int maxEnergy, int maxTicksIdleThreshold){
        poweredDevice = new PoweredDevice(maxEnergy, maxTicksIdleThreshold);
        capabilityInstance = LazyOptional.of(() -> poweredDevice);
    }

    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability != null) EngineersExpansion.LOGGER.debug(capability.getName());
        return capability == CapabilityPoweredDevice.POWERED_DEVICE ? this.capabilityInstance.cast() : LazyOptional.empty();
    }
    @Override
    public INBT serializeNBT() {
        return CapabilityPoweredDevice.POWERED_DEVICE.writeNBT(poweredDevice, null);
    }
    @Override
    public void deserializeNBT(INBT inbt) {
        CapabilityPoweredDevice.POWERED_DEVICE.readNBT(poweredDevice, null, inbt);
    }
}