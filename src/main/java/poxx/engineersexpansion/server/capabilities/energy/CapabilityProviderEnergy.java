package poxx.engineersexpansion.server.capabilities.energy;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderEnergy implements ICapabilitySerializable<INBT> {
    private EnergyStorage energyStorage;
    private LazyOptional<EnergyStorage> energyStorageLazyOptional;

    public CapabilityProviderEnergy(int maxEnergy){
        energyStorage = new EnergyStorage(maxEnergy);
        energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
    }

    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        return capability == CapabilityEnergy.ENERGY ? this.energyStorageLazyOptional.cast() : LazyOptional.empty();
    }
    @Override
    public INBT serializeNBT() {
        return CapabilityEnergy.ENERGY.writeNBT(energyStorage, null);
    }
    @Override
    public void deserializeNBT(INBT inbt) {
        CapabilityEnergy.ENERGY.readNBT(energyStorage, null, inbt);
    }
}
