package commoble.useitemonblockevent.internal;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import commoble.useitemonblockevent.api.UseItemOnBlockEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;

public class MixinCallbacks
{
	public static void onItemUseFirst(ItemStack stack, UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> callbackInfo)
	{
		fireUseItemOnBlock(useOnContext, UseItemOnBlockEvent.UsePhase.PRE_BLOCK, callbackInfo);
	}
	
	public static void onBlockUse(BlockStateBase state, Level level, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> callbackInfo)
	{
		UseOnContext useOnContext = new UseOnContext(level, player, hand, player.getItemInHand(hand).copy(), hitResult);
		fireUseItemOnBlock(useOnContext, UseItemOnBlockEvent.UsePhase.BLOCK, callbackInfo);
	}

	public static void onUseItemOnBlock(ItemStack stack, UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> callbackInfo)
	{
		fireUseItemOnBlock(useOnContext, UseItemOnBlockEvent.UsePhase.POST_BLOCK, callbackInfo);
	}
	
	/**
	 * @param useOnContext context
	 * @param usePhase phase
	 * @param defaultCallback The interaction method to use if event is *not* cancelled, e.g. () -> itemStack.useOn()
	 * @return result
	 */
	private static void fireUseItemOnBlock(UseOnContext useOnContext, UseItemOnBlockEvent.UsePhase usePhase, CallbackInfoReturnable<InteractionResult> callbackInfo)
	{
		UseItemOnBlockEvent event = new UseItemOnBlockEvent(useOnContext, usePhase);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			callbackInfo.setReturnValue(event.getCancellationResult());
		}
	}
}
