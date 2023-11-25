package eu.ansquare.states;

import eu.ansquare.states.api.StatePermission;
import eu.ansquare.states.block.StateBlockEntity;
import eu.ansquare.states.block.StatesBlocks;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.ActionResult;

public class StatesEvents {
	public static void init(){
		AttackEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {
			if(!world.isClient()){
				if(!StatePermission.permissionAt(world.getChunk(entity.getBlockPos()), player).maybuild){
					return ActionResult.FAIL;
				}
			}
			return ActionResult.PASS;
		}));
		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			if(!world.isClient()){
				if(!StatePermission.permissionAt(world.getChunk(pos), player).maybuild){
					return ActionResult.FAIL;
				}
			}
			return ActionResult.PASS;
		});
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if(!world.isClient()){
				if(!StatePermission.permissionAt(world.getChunk(hitResult.getBlockPos().offset(hitResult.getSide())), player).maybuild){
					return ActionResult.FAIL;
				} else if (StatePermission.isClaimed(world, hitResult.getBlockPos().offset(hitResult.getSide())) && player.getStackInHand(hand).isIn(States.STATEMAKERS)) {
					return ActionResult.FAIL;
				}
			}
			return ActionResult.PASS;
		});
	}
}
