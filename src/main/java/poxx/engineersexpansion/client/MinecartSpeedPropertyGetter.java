package poxx.engineersexpansion.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public final class MinecartSpeedPropertyGetter implements IItemPropertyGetter {
    @Override
    public float call(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity){
        if (clientWorld != null && livingEntity instanceof PlayerEntity && livingEntity.getRootVehicle() instanceof MinecartEntity){
            MinecartEntity minecart = (MinecartEntity) livingEntity.getRootVehicle();
            Double currentSpeed = Math.min(Math.abs(minecart.getDeltaMovement().x), minecart.getMaxSpeedWithRail()) +
                    Math.min(Math.abs(minecart.getDeltaMovement().z), minecart.getMaxSpeedWithRail());
            return (currentSpeed.floatValue()/1.2F);
        }
        return 0F;
    }
}
