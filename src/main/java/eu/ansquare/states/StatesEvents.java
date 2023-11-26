package eu.ansquare.states;

import eu.ansquare.states.api.StatePermission;
import eu.ansquare.states.block.StateBlockEntity;
import eu.ansquare.states.block.StatesBlocks;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.item.BlockItem;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class StatesEvents {
	public static void init(){
		AttackEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {
			if(!world.isClient()){
				if(!StatePermission.permissionAt(world.getChunk(entity.getBlockPos()), player).maybuild){
					player.sendMessage(Text.translatable("state.deny.attack"), true);
					return ActionResult.FAIL;
				}
			}
			return ActionResult.PASS;
		}));
		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			if(!world.isClient()){
				if(!StatePermission.permissionAt(world.getChunk(pos), player).maybuild){
					player.sendMessage(Text.translatable("state.deny.break"), true);
					return ActionResult.FAIL;
				}
			}
			return ActionResult.PASS;
		});
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if(!world.isClient()){
				if(!StatePermission.permissionAt(world.getChunk(hitResult.getBlockPos().offset(hitResult.getSide())), player).maybuild){
					if(player.getStackInHand(hand).getItem() instanceof BlockItem && !player.isSneaking()){
						player.sendMessage(Text.translatable("state.deny.build"), true);
					}else {
						player.sendMessage(Text.translatable("state.deny.interact"), true);
					}

					return ActionResult.FAIL;
				} else if (StatePermission.isClaimed(world, hitResult.getBlockPos().offset(hitResult.getSide())) && player.getStackInHand(hand).isIn(States.STATEMAKERS)) {
					player.sendMessage(Text.translatable("state.deny.state"), true);

					return ActionResult.FAIL;
				}
			}
			return ActionResult.PASS;
		});
	}
}
