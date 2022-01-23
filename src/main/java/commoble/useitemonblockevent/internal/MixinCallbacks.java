package commoble.useitemonblockevent.internal;

import java.util.function.Supplier;

import commoble.useitemonblockevent.api.UseItemOnBlockEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;

public class MixinCallbacks
{
	public static InteractionResult onItemUseFirst(ItemStack stack, UseOnContext useOnContext)
	{
		return fireUseItemOnBlock(useOnContext, UseItemOnBlockEvent.UsePhase.PRE_BLOCK, () -> stack.onItemUseFirst(useOnContext));
	}
	
	public static InteractionResult onBlockUse(BlockState state, Level level, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		UseOnContext useOnContext = new UseOnContext(level, player, hand, player.getItemInHand(hand).copy(), hitResult);
		return fireUseItemOnBlock(useOnContext, UseItemOnBlockEvent.UsePhase.BLOCK, () -> state.use(level, player, hand, hitResult));
	}

	public static InteractionResult onUseItemOnBlock(ItemStack stack, UseOnContext useOnContext)
	{
		return fireUseItemOnBlock(useOnContext, UseItemOnBlockEvent.UsePhase.POST_BLOCK, () -> stack.useOn(useOnContext));
	}
	
	/**
	 * @param useOnContext context
	 * @param usePhase phase
	 * @param defaultCallback The interaction method to use if event is *not* cancelled, e.g. () -> itemStack.useOn()
	 * @return result
	 */
	private static InteractionResult fireUseItemOnBlock(UseOnContext useOnContext, UseItemOnBlockEvent.UsePhase usePhase, Supplier<InteractionResult> defaultCallback)
	{
		UseItemOnBlockEvent event = new UseItemOnBlockEvent(useOnContext, usePhase);
		MinecraftForge.EVENT_BUS.post(event);
		return event.isCanceled() ? event.getCancellationResult() : defaultCallback.get();
	}
}
