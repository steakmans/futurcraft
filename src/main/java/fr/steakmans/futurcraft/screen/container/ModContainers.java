package fr.steakmans.futurcraft.screen.container;

import fr.steakmans.futurcraft.Main;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MODID);

    public static final RegistryObject<MenuType<FurnaceGeneratorContainer>> FURNACE_GENERATOR = CONTAINERS.register("furnace_generator", () -> new MenuType<>(FurnaceGeneratorContainer::new));
    public static final RegistryObject<MenuType<BasicPowerUnitContainer>> BASIC_POWER_UNIT = CONTAINERS.register("basic_power_unit", () -> new MenuType<>(BasicPowerUnitContainer::new));
    public static final RegistryObject<MenuType<ElectricFurnaceContainer>> ELECTRIC_FURNACE = CONTAINERS.register("electric_furnace", () -> new MenuType<>(ElectricFurnaceContainer::new));

}
