package eu.ansquare.states.block;

import eu.ansquare.states.StatemakerScreenHandler;
import eu.ansquare.states.States;
import eu.ansquare.states.cca.StatesChunkComponents;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.networking.api.PacketByteBufs;

import java.util.*;

public class StateBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, Inventory {
	public static void itemStackLoad(ServerPlayerEntity player, BlockPos pos, int i){
		BlockEntity entity = player.getServerWorld().getBlockEntity(pos);
		if(entity instanceof StateBlockEntity stateBlock){
			stateBlock.loadFromStack(i);
		}
	}
	public Set<ChunkPos> list = new HashSet<>();
	public UUID uuid = UUID.randomUUID();
	public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
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
		Inventories.writeNbt(nbt, inventory);
		super.writeNbt(nbt);
	}
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
		Inventories.readNbt(nbt, this.inventory);
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
			ChunkPos pos = new ChunkPos(array[0], array[1]);
			StatesChunkComponents.CLAIMED_CHUNK_COMPONENT.maybeGet(world.getChunk(pos.x, pos.z)).ifPresent(claimedChunkComponent -> {
				if(claimedChunkComponent.claim(this).print(pos).valid){
					list.add(pos);
				}
			});
		}
	}
	public void destroy(){
		States.LOGGER.info("destroying");
		list.forEach(chunkPos -> StatesChunkComponents.CLAIMED_CHUNK_COMPONENT.maybeGet(world.getChunk(chunkPos.x, chunkPos.z)).ifPresent(claimedChunkComponent -> claimedChunkComponent.unclaim()));
		list.clear();
	}
	public void loadFromStack(int stackint){
		ItemStack stack = inventory.get(stackint);
		if(stackint == 0){
			NbtList list1 = stack.getOrCreateNbt().getList("chunks", 11);
			addFromNbtList(list1);
		}
	}

	@Override
	public Text getDisplayName() {
		return Text.translatable("sreen.states.title");
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new StatemakerScreenHandler(i, playerInventory, uuid, this, this.getPos());
	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
		buf.writeUuid(uuid).writeBlockPos(this.getPos());
	}
	@Override
	public int size() {
		return 4;
	}

	@Override
	public boolean isEmpty() {
		return this.inventory.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getStack(int slot) {
		return inventory.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack itemStack = Inventories.splitStack(this.inventory, slot, amount);
		if (!itemStack.isEmpty()) {
			this.markDirty();
		}

		return itemStack;
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(inventory, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		this.inventory.set(slot, stack);
		if (stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}

		this.markDirty();

	}

	public boolean canPlayerUse(PlayerEntity player) {
		return Inventory.canPlayerUse(this, player);
	}


	@Override
	public void clear() {
		inventory.clear();
	}
}
