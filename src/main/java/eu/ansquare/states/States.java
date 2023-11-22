package eu.ansquare.states;

import eu.ansquare.states.block.StatesBlocks;
import eu.ansquare.states.item.StatesItems;
import eu.ansquare.states.network.StatesNetwork;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class States implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("States");
	public static final String MODID = "states";
	public static final ExtendedScreenHandlerType<StatemakerScreenHandler> STATEMAKER_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(StatemakerScreenHandler::new);

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		StatesBlocks.init();
		StatesItems.init();
		StatesNetwork.initC2S();
		Registry.register(Registries.SCREEN_HANDLER_TYPE, new Identifier(MODID, "state_screen"), STATEMAKER_SCREEN_HANDLER);
	}
}
