package eu.ansquare.states.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CitizenComponent implements Component {
	public List<Integer> allow;
	@Override
	public void readFromNbt(NbtCompound tag) {
		allow = Arrays.stream(tag.getIntArray("allow")).boxed().toList();
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putIntArray("allow", allow);
	}
}
