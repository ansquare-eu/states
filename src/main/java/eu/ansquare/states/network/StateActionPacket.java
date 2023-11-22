package eu.ansquare.states.network;

import eu.ansquare.states.States;
import eu.ansquare.states.block.StateBlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.quiltmc.qsl.networking.api.PacketSender;

import java.util.logging.Logger;

public class StateActionPacket {
	public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){

		//if(player.getServerWorld().getBlockEntity(buf.readBlockPos()) instanceof StateBlockEntity stateBlock){
			//stateBlock.loadFromStack(buf.readInt());
		StateBlockEntity.itemStackLoad(player, buf.readBlockPos(), buf.readInt());
		//States.LOGGER.info(buf.readBlockPos().toString());
	}
}

