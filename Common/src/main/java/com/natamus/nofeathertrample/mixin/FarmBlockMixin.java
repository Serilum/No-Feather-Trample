package com.natamus.nofeathertrample.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FarmBlock.class, priority = 1001)
public class FarmBlockMixin {
	@Inject(method = "turnToDirt(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V", at = @At(value = "HEAD"), cancellable = true)
	private static void turnToDirt(Entity entity, BlockState blockState, Level level, BlockPos blockPos, CallbackInfo ci) {
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity)entity;
			if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, livingEntity) > 0 || livingEntity.hasEffect(MobEffects.SLOW_FALLING)) {
				ci.cancel();
			}
		}
	}
}
