package eu.ansquare.states.network;

import eu.ansquare.states.States;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public class StatesNetwork {
	public static final Identifier TOGGLE_PLAYER_STATE_PACKET_ID = new Identifier(States.MODID, "statemakeraction");
	public static final Identifier STATE_UNLOAD_PACKET_ID = new Identifier(States.MODID, "stateunload");



	public static void initC2S(){
		ServerPlayNetworking.registerGlobalReceiver(TOGGLE_PLAYER_STATE_PACKET_ID, StateLoadPacket::receive);
		ServerPlayNetworking.registerGlobalReceiver(STATE_UNLOAD_PACKET_ID, StateUnloadPacket::receive);
	}
	public static void initS2C(){
	}
}
