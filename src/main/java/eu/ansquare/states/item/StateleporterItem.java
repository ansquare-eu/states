package eu.ansquare.states.item;

import eu.ansquare.states.States;
import eu.ansquare.states.api.StatePermission;
import eu.ansquare.states.block.StateBlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StateleporterItem extends Item {
	public StateleporterItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
	if (!world.isClient && playerEntity.isSneaking()){
		NbtCompound nbt = playerEntity.getStackInHand(hand).getOrCreateNbt();
		World world1 = playerEntity.getServer().getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(nbt.getString("world"))));

		if(world1 != null){

			BlockPos pos = NbtHelper.toBlockPos(nbt.getCompound("pos"));
			Chunk chunk = world1.getChunk(pos);
			boolean bool = StatePermission.mayTp(chunk, playerEntity);
			if(bool && !world1.isClient){
				if(world1.getRegistryKey() == world.getRegistryKey()){
					playerEntity.teleport(pos.getX(), pos.getY(), pos.getZ());

				}
				return TypedActionResult.success(playerEntity.getStackInHand(hand));

			}
		}
		return TypedActionResult.fail(playerEntity.getStackInHand(hand));
	}
	return TypedActionResult.pass(playerEntity.getStackInHand(hand));
	}
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		NbtCompound nbt = stack.getOrCreateNbt();
		BlockPos pos = NbtHelper.toBlockPos(nbt.getCompound("pos"));
		tooltip.add(Text.translatable("item.states.stateleporter.tooltip", pos.getX(), pos.getY(), pos.getZ()));
	}
}
