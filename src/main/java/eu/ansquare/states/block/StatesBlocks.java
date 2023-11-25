package eu.ansquare.states.block;

import eu.ansquare.states.States;
import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
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

	public static final Block STONE_STATEMAKER = createBlockAndItem("stone_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(Blocks.STONE)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block DEEPSLATE_STATEMAKER = createBlockAndItem("deepslate_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block AMETHYST_STATEMAKER = createBlockAndItem("amethyst_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block OAK_STATEMAKER = createBlockAndItem("oak_statemaker", new StatemakerBlock(QuiltBlockSettings.create().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block SPRUCE_STATEMAKER = createBlockAndItem("spruce_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(OAK_STATEMAKER)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block BIRCH_STATEMAKER = createBlockAndItem("birch_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(OAK_STATEMAKER)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block JUNGLE_STATEMAKER = createBlockAndItem("jungle_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(OAK_STATEMAKER)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block ACACIA_STATEMAKER = createBlockAndItem("acacia_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(OAK_STATEMAKER)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block DARK_OAK_STATEMAKER = createBlockAndItem("dark_oak_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(OAK_STATEMAKER)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block BAMBOO_STATEMAKER = createBlockAndItem("bamboo_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(OAK_STATEMAKER)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block CHERRY_STATEMAKER = createBlockAndItem("cherry_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(OAK_STATEMAKER)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block CRIMSON_STATEMAKER = createBlockAndItem("crimson_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(OAK_STATEMAKER)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block WARPED_STATEMAKER = createBlockAndItem("warped_statemaker", new StatemakerBlock(QuiltBlockSettings.copyOf(OAK_STATEMAKER)), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final BlockEntityType STATE_BLOCK_ENTITY = createBlockEntity("state_block_entity", QuiltBlockEntityTypeBuilder.create(StateBlockEntity::new, STONE_STATEMAKER, DEEPSLATE_STATEMAKER, AMETHYST_STATEMAKER, OAK_STATEMAKER, SPRUCE_STATEMAKER, BIRCH_STATEMAKER, JUNGLE_STATEMAKER, ACACIA_STATEMAKER, DARK_OAK_STATEMAKER, BAMBOO_STATEMAKER, CHERRY_STATEMAKER, CRIMSON_STATEMAKER, WARPED_STATEMAKER).build());
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
