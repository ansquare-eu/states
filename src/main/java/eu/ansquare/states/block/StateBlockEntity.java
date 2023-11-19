package eu.ansquare.states.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StateBlockEntity extends BlockEntity {
	public List<ChunkPos> list;
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
		super.writeNbt(nbt);
	}
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		list = new ArrayList<>();
		int[] xs = nbt.getIntArray("xs");
		int[] zs = nbt.getIntArray("zs");
		for (int i = 0; i < xs.length; i++){
			list.add(new ChunkPos(xs[i], zs[i]));
		}
	}
}
