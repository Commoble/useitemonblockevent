package commoble.useitemonblockevent.examplemod;

import commoble.useitemonblockevent.api.UseItemOnBlockEvent;
import commoble.useitemonblockevent.api.UseItemOnBlockEvent.UsePhase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(ExampleMod.MODID)
public class ExampleMod
{
	public static final String MODID = "useitemonblockevent_examplemod";
	
	public ExampleMod()
	{
		MinecraftForge.EVENT_BUS.addListener(this::onUseItemOnBlock);
	}
	
	private void onUseItemOnBlock(UseItemOnBlockEvent event)
	{
		UseOnContext context = event.getUseOnContext();
		Level level = context.getLevel();
		System.out.println(String.format("phase=%s; hand=%s; isClient=%s", event.getUsePhase(), event.getUseOnContext().getHand(), level.isClientSide));
		// cancel item logic if dirt is placed on top of grass
		if (event.getUsePhase() == UsePhase.POST_BLOCK)
		{
			ItemStack stack = context.getItemInHand();
			Item item = stack.getItem();
			if (item instanceof BlockItem blockItem && blockItem.getBlock() == Blocks.DIRT)
			{
				BlockPos placePos = context.getClickedPos().relative(context.getClickedFace());
				if (level.getBlockState(placePos.below()).getBlock() == Blocks.GRASS_BLOCK)
				{
					if (!level.isClientSide)
					{
						context.getPlayer().displayClientMessage(new TextComponent("Can't place dirt on grass"), false);
					}
					event.cancelWithResult(InteractionResult.SUCCESS);
				}
			}
		}
	}
}
