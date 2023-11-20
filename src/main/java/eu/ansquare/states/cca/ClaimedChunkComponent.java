package eu.ansquare.states.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class ClaimedChunkComponent implements Component {
	public BlockPos ownerBlock;
	public int stateId;
	@Override
	public void readFromNbt(NbtCompound tag) {
		//ownerBlock = new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
		//stateId = tag.getInt("id");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		//tag.putInt("x", ownerBlock.getX());
		//tag.putInt("y", ownerBlock.getY());
		//tag.putInt("z", ownerBlock.getZ());
		//tag.putInt("id", stateId);
	}
}
