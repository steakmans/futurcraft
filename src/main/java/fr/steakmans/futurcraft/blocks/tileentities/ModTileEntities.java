package fr.steakmans.futurcraft.blocks.tileentities;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Main.MODID);

    public static final RegistryObject<BlockEntityType<?>> ROCKET_CONSTRUCTOR_TILE_ENTITY = TILE_ENTITIES.register("rocket_constructor_tile_entity", () -> BlockEntityType.Builder.of(TileEntityRocketConstructor::new, ModBlocks.ROCKET_CONSTRUCTOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> FURNACE_GENERATOR_TILE_ENTITY = TILE_ENTITIES.register("furnace_generator_tile_entity", () -> BlockEntityType.Builder.of(TileEntityFurnaceGenerator::new, ModBlocks.FURNACE_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> BASIC_POWER_UNIT_TILE_ENTITY = TILE_ENTITIES.register("basic_power_unit_tile_entity", () -> BlockEntityType.Builder.of(TileEntityBasicPowerUnit::new, ModBlocks.BASIC_POWER_UNIT.get()).build(null));

}
