package eu.ansquare.states.client;

import eu.ansquare.states.States;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class StatesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		HandledScreens.register(States.STATEMAKER_SCREEN_HANDLER, StatemakerScreen::new);

	}
}
