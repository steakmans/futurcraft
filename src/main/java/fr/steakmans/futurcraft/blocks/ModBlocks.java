package fr.steakmans.futurcraft.blocks;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

    public static final RegistryObject<Block> TITANIUM_BLOCK = createBlock("titanium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(6.0F, 7.5F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ALUMINUM_BLOCK = createBlock("aluminum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.5f, 7f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COBALT_BLOCK = createBlock("cobalt_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.5f, 7f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> NICKEL_BLOCK = createBlock("nickel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.5f, 7f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SILVER_BLOCK = createBlock("silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5f, 6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ALUMINUM_ORE = createBlock("aluminum_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0f, 4.5f)));
    public static final RegistryObject<Block> DEEPSLATE_ALUMINUM_ORE = createBlock("deepslate_aluminum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(3.5f, 6.0f)));
    public static final RegistryObject<Block> SILVER_ORE = createBlock("silver_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.5f, 4.0f)));
    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE = createBlock("deepslate_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0f, 6.0f)));
    public static final RegistryObject<Block> TITANIUM_ORE = createBlock("titanium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4.0f, 5.5f)));
    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = createBlock("deepslate_titanium_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 7.0f)));
    public static final RegistryObject<Block> NICKEL_ORE = createBlock("nickel_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0f, 4.5f)));
    public static final RegistryObject<Block> DEEPSLATE_NICKEL_ORE = createBlock("deepslate_nickel_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(3.5f, 6.0f)));
    public static final RegistryObject<Block> COBALT_ORE = createBlock("cobalt_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0f, 4.5f)));
    public static final RegistryObject<Block> DEEPSLATE_COBALT_ORE = createBlock("deepslate_cobalt_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(3.5f, 6.0f)));

    //machines
    public static final RegistryObject<Block> ROCKET_CONSTRUCTOR = createBlock("rocket_constructor", BlockRocketConstructor::new);
    public static final RegistryObject<Block> FURNACE_GENERATOR = createBlock("smoke_generator", BlockFurnaceGenerator::new);
    public static final RegistryObject<Block> BASIC_POWER_UNIT = createBlock("basic_power_unit", BlockBasicPowerUnit::new);

    public static RegistryObject<Block> createBlock(String name, Supplier<? extends Block> supplier) {
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(Main.TAB)));
        return block;
    }

}
