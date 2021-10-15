package poxx.engineersexpansion.server.capabilities.powereddevice;

import net.minecraftforge.energy.EnergyStorage;

public class PoweredDevice extends EnergyStorage {
    public int energy;
    protected boolean isOn;
    protected boolean autoOff;
    protected int ticksSinceIdle;
    protected int maxTicksIdleThreshold;

    public PoweredDevice(int maxEnergy, int maxTicksIdleThreshold){
        super(maxEnergy);
        this.isOn = false;
        this.autoOff = true;
        this.ticksSinceIdle = 0;
        this.maxTicksIdleThreshold = maxTicksIdleThreshold;
        this.energy = maxEnergy;
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
    public void useTick(int maxExtract){
        if (this.isOn){
            if (this.extractEnergy(maxExtract, false) == 0) toggleIsOn();
        }
    }
}
