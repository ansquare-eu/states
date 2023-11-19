package eu.ansquare.states.block;

import eu.ansquare.states.States;
import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatesBlocks {
	private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	private static final Map<Item, Identifier> BLOCKITEMS = new LinkedHashMap<>();
	private static final Map<BlockEntityType, Identifier> BLOCKENTITIES = new LinkedHashMap<>();

	public static final Block STATEMAKER = createBlockAndItem("statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(Blocks.CRAFTING_TABLE)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final BlockEntityType STATE_BLOCK_ENTITY = createBlockEntity("state_block_entity", QuiltBlockEntityTypeBuilder.create(StateBlockEntity::new, STATEMAKER).build());
	private static <T extends Block> T createBlockAndItem(String name, T block, RegistryKey<ItemGroup> itemGroup){
		BLOCKS.put(block, new Identifier(States.MODID, name));
		BLOCKITEMS.put(new BlockItem(block, new QuiltItemSettings()), new Identifier(States.MODID, name));
		ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
			content.addItem(block);
		});
		return block;
	}
	private static BlockEntityType<? extends BlockEntity> createBlockEntity(String name, BlockEntityType type){
		BLOCKENTITIES.put(type, new Identifier(States.MODID, name));
		return type;
	}
	public static void init() {
		BLOCKS.keySet().forEach(item -> Registry.register(Registries.BLOCK, BLOCKS.get(item), item));
		BLOCKITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, BLOCKITEMS.get(item), item));
		BLOCKENTITIES.keySet().forEach(item -> Registry.register(Registries.BLOCK_ENTITY_TYPE, BLOCKENTITIES.get(item), item));

	}
}
