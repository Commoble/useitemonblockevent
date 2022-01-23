package commoble.useitemonblockevent.internal.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import commoble.useitemonblockevent.internal.MixinCallbacks;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin
{
	@Redirect(method="useItemOn", at=@At(value="INVOKE", target="onItemUseFirst", remap=false))
	private InteractionResult redirectOnItemUseFirst(ItemStack stack, UseOnContext useOnContext)
	{
		return MixinCallbacks.onItemUseFirst(stack, useOnContext);
	}
	
	@Redirect(method="useItemOn", at=@At(value="INVOKE", target="Lnet/minecraft/world/level/block/state/BlockState;use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"))
	private InteractionResult redirectBlockUse(BlockState state, Level level, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		return MixinCallbacks.onBlockUse(state, level, player, hand, hitResult);
	}
	
	@Redirect(method="useItemOn", at=@At(value="INVOKE", target="Lnet/minecraft/world/item/ItemStack;useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;", ordinal=0))
	private InteractionResult redirectUseOnCreative(ItemStack stack, UseOnContext useOnContext)
	{
		return MixinCallbacks.onUseItemOnBlock(stack, useOnContext);
	}

	@Redirect(method="useItemOn", at=@At(value="INVOKE", target="Lnet/minecraft/world/item/ItemStack;useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;", ordinal=1))
	private InteractionResult redirectUseOnSurvival(ItemStack stack, UseOnContext useOnContext)
	{
		return MixinCallbacks.onUseItemOnBlock(stack, useOnContext);
	}
}
