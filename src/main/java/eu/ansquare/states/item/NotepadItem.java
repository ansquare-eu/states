package eu.ansquare.states.item;

import eu.ansquare.states.block.StateBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class NotepadItem extends Item {
	public NotepadItem(Settings settings) {
		super(settings);
	}
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		ItemStack stack = playerEntity.getStackInHand(hand);

		ChunkPos pos = playerEntity.getChunkPos();
		NbtCompound nbt = stack.getOrCreateNbt();
		NbtList list = nbt.getList("chunks",11);
		list.add(new NbtIntArray(new int[]{pos.x, pos.z}));
		nbt.put("chunks", list);
		return TypedActionResult.success(stack);
	}

}
