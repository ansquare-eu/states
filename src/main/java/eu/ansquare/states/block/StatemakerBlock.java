package eu.ansquare.states.block;

import eu.ansquare.states.States;
import eu.ansquare.states.item.StatesItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StatemakerBlock extends BlockWithEntity {
	public StatemakerBlock(Settings settings) {
		super(settings);
	}
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new StateBlockEntity(pos, state);
	}
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if(!world.isClient) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof StateBlockEntity stateBlock) {
				stateBlock.init(placer.getUuid(), world.getChunk(pos).getPos());
			}
		}

	}
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient){
			NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

			if (screenHandlerFactory != null) {
				//With this call the server will request the client to open the appropriate Screenhandler
				player.openHandledScreen(screenHandlerFactory);
			}
		}

		return ActionResult.PASS;
	}

	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBreak(world, pos, state, player);
		BlockEntity blockEntity1 = world.getBlockEntity(pos);
		if(blockEntity1 instanceof StateBlockEntity stateBlockEntity){
			stateBlockEntity.destroy();
		}
	}

}
