package eu.ansquare.states.util;

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

public class NbtSet extends NbtList {
	public boolean secureAdd(int i, NbtElement nbtElement) {
		if(this.contains(nbtElement)){
			return false;
		}
		super.addElement(i, nbtElement);
		return true;
	}
}
