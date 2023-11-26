package eu.ansquare.states.item;

import eu.ansquare.states.States;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatesItems {
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
	private static <T extends Item> T createItem(String name, T item, RegistryKey<ItemGroup> itemGroup){
		ITEMS.put(item, new Identifier(States.MODID, name));
		ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
			content.addItem(item);
		});
		return item;
	}
	private static <T extends Item> T createGrouplessItem(String name, T item){
		ITEMS.put(item, new Identifier(States.MODID, name));
		return item;
	}
	public static void init() {
		ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));
	}

	public static final Item NOTEPAD = createItem("notepad", new NotepadItem(new QuiltItemSettings().maxCount(1)), ItemGroups.TOOLS_AND_UTILITIES);
	public static final Item STATELEPORTER = createItem("stateleporter", new StateleporterItem(new QuiltItemSettings().maxCount(1)), ItemGroups.TOOLS_AND_UTILITIES);
}
