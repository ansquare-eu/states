package eu.ansquare.states;

import eu.ansquare.states.cca.StatesEntityComponents;
import eu.ansquare.states.item.NotepadItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.stat.Stat;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class StatemakerScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	private UUID uuid;
	public BlockPos pos;
	public StatemakerScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
		this(syncId, playerInventory, buf.readUuid(), new SimpleInventory(4), buf.readBlockPos());
	}
	public StatemakerScreenHandler(int syncId, PlayerInventory playerInventory, UUID uuid, Inventory inventory, BlockPos pos) {
		super(States.STATEMAKER_SCREEN_HANDLER, syncId);
		this.pos = pos;
		this.uuid = uuid;
		this.inventory = inventory;
		//some inventories do custom logic when a player opens it.
		inventory.onOpen(playerInventory.player);
		//This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!basic screen
		//This will not render the background of the slots however, this is the Screens job
		int m;
		int l;
		//Our inventory
		this.addSlot(new Slot(inventory, 0, 26, 35) {
			public boolean canInsert(ItemStack stack) {
				return stack.getItem() instanceof NotepadItem;
			}
		});
		for (l = 0; l < 3; ++l) {
			this.addSlot(new Slot(inventory, l + 1, 134, 17 + l * 18){
				public boolean canInsert(ItemStack stack) {
					return stack.getItem() instanceof NotepadItem;
				}
			});
		}
		//The player inventory
		for (m = 0; m < 3; ++m) {
			for (l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
			}
		}
		//The player Hotbar
		for (m = 0; m < 9; ++m) {
			this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
		}

	}

	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(fromIndex);
		if (slot != null && slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (fromIndex < this.inventory.size()) {
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return newStack;
	}


	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
