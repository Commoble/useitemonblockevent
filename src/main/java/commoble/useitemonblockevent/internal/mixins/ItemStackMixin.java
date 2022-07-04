package commoble.useitemonblockevent.internal.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import commoble.useitemonblockevent.internal.MixinCallbacks;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

@Mixin(ItemStack.class)
public class ItemStackMixin
{
	@Inject(method="onItemUseFirst", at=@At(value="HEAD"), cancellable=true, remap=false)
	private void useitemonblockevent_onItemUseFirst_injectHead(UseOnContext context, CallbackInfoReturnable<InteractionResult> callbackInfo)
	{
		 MixinCallbacks.onItemUseFirst((ItemStack)(Object)this, context, callbackInfo);
	}
	
	@Inject(method="useOn", at=@At(value="HEAD"), cancellable=true)
	private void useitemonblockevent_useOn_injectHead(UseOnContext context, CallbackInfoReturnable<InteractionResult> callbackInfo)
	{
		MixinCallbacks.onUseItemOnBlock((ItemStack)(Object)this, context, callbackInfo);
	}
}
