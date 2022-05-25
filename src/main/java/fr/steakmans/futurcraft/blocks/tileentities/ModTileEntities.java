package fr.steakmans.futurcraft.blocks.tileentities;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Main.MODID);

    public static final RegistryObject<BlockEntityType<TileEntityRocketConstructor>> ROCKET_CONSTRUCTOR_TILE_ENTITY = TILE_ENTITIES.register("rocket_constructor_tile_entity", () -> BlockEntityType.Builder.of(TileEntityRocketConstructor::new, ModBlocks.ROCKET_CONSTRUCTOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<TileEntityFurnaceGenerator>> FURNACE_GENERATOR_TILE_ENTITY = TILE_ENTITIES.register("furnace_generator_tile_entity", () -> BlockEntityType.Builder.of(TileEntityFurnaceGenerator::new, ModBlocks.FURNACE_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<TileEntityBasicPowerUnit>> BASIC_POWER_UNIT_TILE_ENTITY = TILE_ENTITIES.register("basic_power_unit_tile_entity", () -> BlockEntityType.Builder.of(TileEntityBasicPowerUnit::new, ModBlocks.BASIC_POWER_UNIT.get()).build(null));
    public static final RegistryObject<BlockEntityType<TileEntityElectricFurnace>> ELECTRIC_FURNACE_TILE_ENTITY = TILE_ENTITIES.register("electric_furnace_tile_entity", () -> BlockEntityType.Builder.of(TileEntityElectricFurnace::new, ModBlocks.ELECTRIC_FURNACE.get()).build(null));
    public static final RegistryObject<BlockEntityType<TileEntityLauncherPanel>> LAUNCHER_PANEL_TILE_ENTITY = TILE_ENTITIES.register("launcher_panel_tile_entity", () -> BlockEntityType.Builder.<TileEntityLauncherPanel>of(TileEntityLauncherPanel::new, ModBlocks.LAUNCHER_PANEL.get()).build(null));
}
