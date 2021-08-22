package poxx.engineersexpansion.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class TachometerProperty implements IItemPropertyGetter {
    @Override
    public float call(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity){

        return 0F;
    }
}
