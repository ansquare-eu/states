package eu.ansquare.states.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import eu.ansquare.states.block.StateBlockEntity;
import eu.ansquare.states.util.ClaimResult;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.UuidUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import java.util.UUID;

public class ClaimedChunkComponent implements AutoSyncedComponent {

	public boolean claimed = false;
	public BlockPos ownerBlock;
	public UUID stateId;
	public ClaimResult claim(StateBlockEntity state){
		if(claimed){
			if(state.uuid.equals(stateId)){
				return ClaimResult.self();
			} else {
				return ClaimResult.fail(ownerBlock);
			}
		} else {
			stateId = state.uuid;
			ownerBlock = state.getPos();
			claimed = true;
			return ClaimResult.success();
		}
	}
	@Override
	public void readFromNbt(NbtCompound tag) {
		claimed = tag.getBoolean("claimed");
		if(claimed){
			ownerBlock = NbtHelper.toBlockPos(tag.getCompound("stateblock"));
			stateId = tag.getUuid("id");
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		if(claimed){
			tag.put("stateblock", NbtHelper.fromBlockPos(ownerBlock));
			tag.putUuid("id", stateId);
		}
		tag.putBoolean("claimed", claimed);
	}
	public void unclaim(){
		claimed = false;
		stateId = null;
		ownerBlock = null;
	}
}
