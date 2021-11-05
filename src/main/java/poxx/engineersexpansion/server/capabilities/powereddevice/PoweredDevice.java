package poxx.engineersexpansion.server.capabilities.powereddevice;

import net.minecraftforge.energy.IEnergyStorage;

public class PoweredDevice {
//    public int energy;
    protected boolean isOn;
//    protected boolean autoOff;
//    protected int ticksSinceIdle;
//    protected int maxTicksIdleThreshold;

    public PoweredDevice(int maxTicksIdleThreshold){
        this.isOn = false;
//        this.autoOff = true;
//        this.ticksSinceIdle = 0;
//        this.maxTicksIdleThreshold = maxTicksIdleThreshold;
    }
    public boolean getIsOn(){
        return isOn;
    }
//    public boolean getAutoOff(){
//        return autoOff;
//    }
//    public int getTicksSinceIdle(){
//        return ticksSinceIdle;
//    }
//    public void toggleAutoOff(){
//        this.autoOff = !this.autoOff;
//    }
//    public void advanceIdleTick(){
//        if (this.autoOff && this.isOn){
//            ticksSinceIdle++;
//            if (ticksSinceIdle >= maxTicksIdleThreshold) toggleIsOn();
//        }
//    }
    public void toggleIsOn(IEnergyStorage energyStorage){
//        if (this.isOn) this.ticksSinceIdle = 0;
        if (energyStorage.getEnergyStored() == 0) this.isOn = false;
        else this.isOn = !this.isOn;
    }
    public void useTick(int useEnergy, IEnergyStorage energyStorage){
        //Consume energy if on, if useEnergy can't be drawn, turn off.
        if (this.isOn){
            if (energyStorage.extractEnergy(useEnergy, false) < useEnergy) toggleIsOn(energyStorage);
//            if (isSelected) ticksSinceIdle = 0;
//            else advanceIdleTick();
        }
    }
}
