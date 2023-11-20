package eu.ansquare.states.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;

import java.util.*;

public class CitizenComponent implements Component {
	public Set<UUID> allow = new HashSet<>();
	@Override
	public void readFromNbt(NbtCompound tag) {
		tag.getList("allow", 11).forEach(nbtElement -> allow.add(NbtHelper.toUuid(nbtElement)));
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList list = new NbtList();
		allow.forEach(uuid -> list.add(NbtHelper.fromUuid(uuid)));
		tag.put("allow", list);
	}
}
