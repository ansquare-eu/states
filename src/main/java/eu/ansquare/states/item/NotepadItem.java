package eu.ansquare.states.item;

import eu.ansquare.states.block.StateBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;
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

		playerEntity.sendMessage(Text.literal("yes"), false);
		ChunkPos pos = playerEntity.getChunkPos();
		NbtCompound nbt = stack.getOrCreateNbt();
		NbtList list = nbt.getList("chunks",11);
		list.add(new NbtIntArray(new int[]{pos.x, pos.z}));
		nbt.put("chunks", list);
		return TypedActionResult.success(stack);
	}

}
