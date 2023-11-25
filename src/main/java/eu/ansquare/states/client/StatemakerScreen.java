package eu.ansquare.states.client;

import com.mojang.blaze3d.systems.RenderSystem;
import eu.ansquare.states.StatemakerScreenHandler;
import eu.ansquare.states.States;
import eu.ansquare.states.network.StatesNetwork;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.HashMap;

public class StatemakerScreen extends HandledScreen<StatemakerScreenHandler> {

	private static final Identifier TEXTURE = new Identifier(States.MODID, "textures/screen/statemaker.png");
	public BlockPos pos;
	public int[] sizes;
	ButtonWidget button_load;
	ButtonWidget button_allows;
	ButtonWidget button_denys;
	ButtonWidget button_tps;



	public StatemakerScreen(StatemakerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.pos = handler.pos;
		this.sizes = handler.sizes;
		this.backgroundWidth = 176;
		this.backgroundHeight = 166;
	}

	@Override
	protected void drawBackground(GuiGraphics matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderTexture(0, TEXTURE);
		matrices.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, this.backgroundWidth, this.backgroundHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public void render(GuiGraphics matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void init() {
		super.init();
		// Center the title
		titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
		button_load = ButtonWidget.builder(Text.literal(String.valueOf(sizes[0])), buttonWidget -> ClientPlayNetworking.send(StatesNetwork.TOGGLE_PLAYER_STATE_PACKET_ID, PacketByteBufs.create().writeIntArray(new int[]{pos.getX(), pos.getY(), pos.getZ(), 0}))).positionAndSize(this.x + 45, this.y + 34, 18, 18).tooltip(Tooltip.create(Text.translatable("screen.gui.load.chunk"))).build();
		button_allows = ButtonWidget.builder(Text.literal(String.valueOf(sizes[1])), buttonWidget -> ClientPlayNetworking.send(StatesNetwork.TOGGLE_PLAYER_STATE_PACKET_ID, PacketByteBufs.create().writeIntArray(new int[]{pos.getX(), pos.getY(), pos.getZ(), 1}))).positionAndSize(this.x + 113, this.y + 16, 18, 18).tooltip(Tooltip.create(Text.translatable("screen.gui.load.allow"))).build();
		this.addDrawableChild(button_load);
		this.addDrawableChild(button_allows);
		button_denys = ButtonWidget.builder(Text.literal(String.valueOf(sizes[2])), buttonWidget -> ClientPlayNetworking.send(StatesNetwork.TOGGLE_PLAYER_STATE_PACKET_ID, PacketByteBufs.create().writeIntArray(new int[]{pos.getX(), pos.getY(), pos.getZ(), 2}))).positionAndSize(this.x + 113, this.y + 34, 18, 18).tooltip(Tooltip.create(Text.translatable("screen.gui.load.deny"))).build();
		button_tps = ButtonWidget.builder(Text.literal(String.valueOf(sizes[3])), buttonWidget -> ClientPlayNetworking.send(StatesNetwork.TOGGLE_PLAYER_STATE_PACKET_ID, PacketByteBufs.create().writeIntArray(new int[]{pos.getX(), pos.getY(), pos.getZ(), 3}))).positionAndSize(this.x + 113, this.y + 52, 18, 18).tooltip(Tooltip.create(Text.translatable("screen.gui.load.tp"))).build();
		this.addDrawableChild(button_denys);
		this.addDrawableChild(button_tps);

	}

}
