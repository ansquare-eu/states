package eu.ansquare.states.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;

import java.util.*;

public class CitizenComponent implements AutoSyncedComponent {
	public Set<UUID> allow = new HashSet<>();
	public Set<UUID> deny = new HashSet<>();
	public Set<UUID> tp = new HashSet<>();


	@Override
	public void readFromNbt(NbtCompound tag) {
		tag.getList("allow", 11).forEach(nbtElement -> allow.add(NbtHelper.toUuid(nbtElement)));
		tag.getList("deny", 11).forEach(nbtElement -> allow.add(NbtHelper.toUuid(nbtElement)));
		tag.getList("tp", 11).forEach(nbtElement -> tp.add(NbtHelper.toUuid(nbtElement)));

	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList allowlist = new NbtList();
		allow.forEach(uuid -> allowlist.add(NbtHelper.fromUuid(uuid)));
		tag.put("allow", allowlist);
		NbtList denylist = new NbtList();
		deny.forEach(uuid -> denylist.add(NbtHelper.fromUuid(uuid)));
		tag.put("deny", denylist);
		NbtList tplist = new NbtList();
		tp.forEach(uuid -> tplist.add(NbtHelper.fromUuid(uuid)));
		tag.put("tp", tplist);
	}
	public void addAllow(UUID state){
		allow.add(state);
	}
	public void removeAllow(UUID state){
		allow.remove(state);
	}
	public void addDeny(UUID state){
		deny.add(state);
	}
	public void removeDeny(UUID state){
		deny.remove(state);
	}
	public void addTp(UUID state){
		tp.add(state);
	}
	public void removeTp(UUID state){
		tp.remove(state);
	}

}
