package eu.ansquare.states.api;

import eu.ansquare.states.cca.CitizenComponent;
import eu.ansquare.states.cca.ClaimedChunkComponent;
import eu.ansquare.states.cca.StatesChunkComponents;
import eu.ansquare.states.cca.StatesEntityComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class StatePermission {
	public Text result;
	public boolean maybuild;
	protected StatePermission(String result, boolean maybuild){
		this.result = Text.literal(result);
		this.maybuild = maybuild;
	}
	public String toString(){
		return result.getString();
	}
	public static boolean isClaimed(World world, BlockPos pos){
		Chunk chunk = world.getChunk(pos);
		if(StatesChunkComponents.CLAIMED_CHUNK_COMPONENT.maybeGet(chunk).isPresent()){
			ClaimedChunkComponent chunkComponent = StatesChunkComponents.CLAIMED_CHUNK_COMPONENT.get(chunk);
			return chunkComponent.claimed;
		}
		return false;
	}
	public static StatePermission permissionAt(Chunk chunk, PlayerEntity player){
		if(StatesEntityComponents.CITIZEN_COMPONENT.maybeGet(player).isPresent() && StatesChunkComponents.CLAIMED_CHUNK_COMPONENT.maybeGet(chunk).isPresent()){
			ClaimedChunkComponent chunkComponent = StatesChunkComponents.CLAIMED_CHUNK_COMPONENT.get(chunk);
			CitizenComponent citizenComponent = StatesEntityComponents.CITIZEN_COMPONENT.get(player);
			if(!chunkComponent.claimed){
				return new StatePermission("Chunk free", true);
			} else if(citizenComponent.allow.contains(chunkComponent.stateId)){
				return new StatePermission("Citizen", true);
			} else if(citizenComponent.deny.contains(chunkComponent.stateId)){
				return new StatePermission("You are banned from here", false);
			} else {
				return new StatePermission("You are a neutral here", false);
			}
		}
		return new StatePermission("Components undiscovered", true);
	}
}
