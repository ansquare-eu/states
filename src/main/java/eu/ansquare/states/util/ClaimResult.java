package eu.ansquare.states.util;

import eu.ansquare.states.States;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class ClaimResult {
	private final String output;
	public final boolean valid;
	private ClaimResult(String string, boolean valid){
		output = string;
		this.valid = valid;
	}
	public static ClaimResult success(){
		return new ClaimResult("Succesfully claimed chunk", true);
	}
	public static ClaimResult self(){
		return new ClaimResult("You already own this chunks", false);
	}
	public static ClaimResult fail(BlockPos pos){
		return new ClaimResult("This chunk is owned by state at " + pos.toString(), false);
	}
	public ClaimResult print(ChunkPos pos){
		States.LOGGER.info("Chunk " + pos.toString() + ": " + output);
		return this;
	}
}
