package eu.ansquare.states.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class StatemakerBlock extends Block implements BlockEntityProvider {
	public StatemakerBlock(Settings settings) {
		super(settings);
	}
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new StateBlockEntity(pos, state);
	}

}
