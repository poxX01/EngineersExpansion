package poxx.engineersexpansion.server.capabilities.powereddevice;

import net.minecraftforge.energy.IEnergyStorage;

public class PoweredDevice {
    public int energy;
    protected boolean isOn;
    protected boolean autoOff;
    protected int ticksSinceIdle;
    protected int maxTicksIdleThreshold;

    public PoweredDevice(int maxTicksIdleThreshold){
        this.isOn = false;
        this.autoOff = true;
        this.ticksSinceIdle = 0;
        this.maxTicksIdleThreshold = maxTicksIdleThreshold;
    }
    public boolean getIsOn(){
        return isOn;
    }
    public boolean getAutoOff(){
        return autoOff;
    }
    public int getTicksSinceIdle(){
        return ticksSinceIdle;
    }
    public void toggleIsOn(){
        if (this.isOn) this.ticksSinceIdle = 0;
        this.isOn = !this.isOn;
    }
    public void toggleAutoOff(){
        this.autoOff = !this.autoOff;
    }
    public void advanceIdleTick(){
        if (this.autoOff && this.isOn){
            ticksSinceIdle++;
            if (ticksSinceIdle >= maxTicksIdleThreshold) toggleIsOn();
        }
    }
    public void useTick(int useEnergy, IEnergyStorage energyStorage, boolean isSelected){
        //Consume energy if on, sufficient energy can't be drawn, turn off.
        if (this.isOn){
            if (energyStorage.extractEnergy(useEnergy, false) == 0) toggleIsOn();
            if (isSelected) ticksSinceIdle = 0;
            else advanceIdleTick();
        }
    }
}
