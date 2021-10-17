package poxx.engineersexpansion.server.capabilities.powereddevice;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import poxx.engineersexpansion.EngineersExpansion;

public class CapabilityPoweredDevice {
    @CapabilityInject(PoweredDevice.class)
    public static Capability<PoweredDevice> POWERED_DEVICE = null;
    public static void register()
    {
        CapabilityManager.INSTANCE.register(PoweredDevice.class, new Capability.IStorage<PoweredDevice>()
                {
                    @Override
                    public INBT writeNBT(Capability<PoweredDevice> capability, PoweredDevice instance, Direction side)
                    {
                        CompoundNBT compoundNBT = new CompoundNBT();
                        compoundNBT.putBoolean("isOn", instance.getIsOn());
                        compoundNBT.putBoolean("autoOff", instance.getAutoOff());
                        compoundNBT.putInt("ticksSinceIdle", instance.getTicksSinceIdle());
                        EngineersExpansion.LOGGER.debug(compoundNBT.getAsString());
                        return compoundNBT;
                    }
                    @Override
                    public void readNBT(Capability<PoweredDevice> capability, PoweredDevice instance, Direction side, INBT nbt)
                    {
                        CompoundNBT compoundNBT = (CompoundNBT) nbt;
                        instance.isOn = compoundNBT.getBoolean("isOn");
                        instance.autoOff = compoundNBT.getBoolean("autoOff");
                        instance.ticksSinceIdle = compoundNBT.getInt("ticksSinceIdle");
                    }
                },
                () -> new PoweredDevice(200));
    }
}
