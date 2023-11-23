package eu.ansquare.states.network;

import eu.ansquare.states.States;
import eu.ansquare.states.block.StateBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.quiltmc.qsl.networking.api.PacketSender;

import java.util.logging.Logger;

public class StateActionPacket {
	public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
		int[] ints = buf.readIntArray();
		server.execute(() -> {
			BlockPos pos = new BlockPos(ints[0], ints[1], ints[2]);
			BlockEntity entity = player.getServerWorld().getBlockEntity(pos);
			States.LOGGER.info(pos.toString());
			if(entity instanceof StateBlockEntity stateBlock) {
				stateBlock.loadFromStack(ints[3]);
				//BlockEntity entity = player.getServerWorld().getBlockEntity(buf.readBlockPos());
				//if(entity instanceof StateBlockEntity stateBlock){
				//	stateBlock.loadFromStack(buf.readInt());
				//}
			}});

	}
}

