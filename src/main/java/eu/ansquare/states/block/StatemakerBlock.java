package eu.ansquare.states.block;

import eu.ansquare.states.item.StatesItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StatemakerBlock extends Block implements BlockEntityProvider {
	public StatemakerBlock(Settings settings) {
		super(settings);
	}
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new StateBlockEntity(pos, state);
	}
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		// With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
		return BlockRenderType.MODEL;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient){
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof StateBlockEntity stateBlockEntity){
				ItemStack stack = player.getStackInHand(hand);
				if(stack.isOf(StatesItems.NOTEPAD)){
				stateBlockEntity.addFromNbtList(stack.getOrCreateNbt().getList("chunks", 11));
				return ActionResult.SUCCESS;
			}}
		}

		return ActionResult.PASS;
	}

}
