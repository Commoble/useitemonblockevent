package commoble.useitemonblockevent.api;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Event that provides fine-grained control over what happens when an item is used on a block.<br>
 * This event called on both the client and server threads when an item is used on a block.<br>
 * Cancelling the event with {@link cancelWithResult} will prevent the item/block's use-item-on-block
 * logic from running.<br>
 */
@Cancelable
public final class UseItemOnBlockEvent extends Event
{
	private final UseOnContext context;
	private final UsePhase usePhase;
	private InteractionResult cancellationResult = InteractionResult.PASS;
	
	public UseItemOnBlockEvent(UseOnContext context, UsePhase usePhase)
	{
		this.context = context;
		this.usePhase = usePhase;
	}
	
	/**
	 * @return context
	 */
	public UseOnContext getUseOnContext()
	{
		return this.context;
	}
	
	/**
	 * @return The Use Phase of the interaction, see {@link UsePhase} for semantics
	 */
	public UsePhase getUsePhase()
	{
		return this.usePhase;
	}
	
	/**
	 * Cancels the use interaction (preventing the item from being used) and provides the
	 * specified result to the interaction logic instead.
	 * @param result The interaction result to return to the interaction logic.
	 * <ul>
	 * <li>SUCCESS, CONSUME, CONSUME_PARTIAL, and FAIL will prevent further types of interaction attempts
	 * when provided from the PRE_BLOCK phase.
	 * <li>SUCCESS, CONSUME, and CONSUME_PARTIAL will trigger advancements on the server (except in the PRE_BLOCK phase),
	 * and will also prevent the POST_BLOCK item interaction from occurring if provided during the BLOCK phase.
	 * <li>SUCCESS will trigger the arm-swinging mechanic.
	 * <li>PASS will always allow proceeding to the next phase.
	 * </ul>
	 */
	public void cancelWithResult(InteractionResult result)
	{
		this.cancellationResult = result;
		this.setCanceled(true);
	}
	
	/**
	 * @return The result that the canceller cancelled this event with.<br>
	 * Not meaningful unless and until this event has been cancelled; intended to be
	 * used by the event firer.
	 */
	public InteractionResult getCancellationResult()
	{
		return this.cancellationResult;
	}
	
	public static enum UsePhase
	{
		/**
		 * The item's pre-block use-item-on-block interaction (this is a forge extension and not used by vanilla items).
		 * This is noop/PASS for most items but some mods' items have interactions here.
		 */
		PRE_BLOCK,
		
		/**
		 * The block's right-click-block interaction. Skipped if the player is sneaking
		 * and holding an item that skips the block while sneaking (most items).
		 */
		BLOCK,
		
		/**
		 * The item's standard use-item-on-block interaction.
		 */
		POST_BLOCK
	}
	
}
