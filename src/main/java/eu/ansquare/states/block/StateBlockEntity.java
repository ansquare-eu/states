package eu.ansquare.states.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.*;

public class StateBlockEntity extends BlockEntity {
	public Set<ChunkPos> list = new HashSet<>();
	public UUID uuid = UUID.randomUUID();
	public StateBlockEntity(BlockPos pos, BlockState state) {
		super(StatesBlocks.STATE_BLOCK_ENTITY, pos, state);
	}
	@Override
	public void writeNbt(NbtCompound nbt) {
		// Save the current value of the number to the nbt
		LinkedList<Integer> xs = new LinkedList<>();
		LinkedList<Integer> zs = new LinkedList<>();
		list.forEach(chunkPos -> {
			xs.addLast(chunkPos.x);
			zs.addLast(chunkPos.z);
		});
		nbt.putIntArray("xs", xs);
		nbt.putIntArray("zs", zs);
		nbt.putUuid("stateid", uuid);
		super.writeNbt(nbt);
	}
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		int[] xs = nbt.getIntArray("xs");
		int[] zs = nbt.getIntArray("zs");
		uuid = nbt.getUuid("stateid");
		for (int i = 0; i < xs.length; i++){
			list.add(new ChunkPos(xs[i], zs[i]));
		}
	}
	public void addFromNbtList(NbtList nbtlist){
		for (int i = 0; i < nbtlist.size(); i++) {
			int[] array = nbtlist.getIntArray(i);
			list.add(new ChunkPos(array[0], array[1]));
		}
	}
}
