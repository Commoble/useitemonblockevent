package commoble.useitemonblockevent.internal.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import commoble.useitemonblockevent.internal.MixinCallbacks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(BlockStateBase.class)
public class BlockStateBaseMixin
{
	@Inject(method="use", at=@At(value="HEAD"), cancellable=true)
	private void useitemonblockevent_use_injectHead(Level level, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> callbackInfo)
	{
		MixinCallbacks.onBlockUse((BlockStateBase)(Object)this, level, player, hand, hitResult, callbackInfo);
	}
}
