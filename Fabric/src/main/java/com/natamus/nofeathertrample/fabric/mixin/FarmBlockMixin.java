package com.natamus.nofeathertrample.fabric.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FarmBlock.class, priority = 1001)
public abstract class FarmBlockMixin extends Block {
	public FarmBlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "fallOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/FarmBlock;turnToDirt(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"), cancellable = true)
	public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float f, CallbackInfo ci) {
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity)entity;
			if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, livingEntity) > 0 || livingEntity.hasEffect(MobEffects.SLOW_FALLING)) {
				ci.cancel();
				super.fallOn(level, blockState, blockPos, entity, f);
			}
		}
	}
}