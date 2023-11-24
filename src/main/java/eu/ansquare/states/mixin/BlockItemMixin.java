package eu.ansquare.states.mixin;

import eu.ansquare.states.api.StatePermission;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {
	public BlockItemMixin(Settings settings) {
		super(settings);
	}
	@Inject(method = "place", at = @At("HEAD"), cancellable = true)
	public void onPlace(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir) {
		if(!context.getWorld().isClient()){
			if(!StatePermission.permissionAt(context.getWorld().getChunk(context.getBlockPos()), context.getPlayer()).maybuild){
				cir.setReturnValue(ActionResult.FAIL);
			}
		}
	}
}
