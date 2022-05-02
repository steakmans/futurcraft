package fr.steakmans.futurcraft;

import fr.steakmans.futurcraft.blocks.ModBlocks;
import fr.steakmans.futurcraft.blocks.tileentities.ModTileEntities;
import fr.steakmans.futurcraft.items.ModItems;
import fr.steakmans.futurcraft.oregen.OreFeature;
import fr.steakmans.futurcraft.screen.BasicPowerUnitScreen;
import fr.steakmans.futurcraft.screen.FurnaceGeneratorScreen;
import fr.steakmans.futurcraft.screen.container.ModContainers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "futurcraft";
    public static final CreativeModeTab TAB = new CreativeModeTab("futurcraft") {
        @Override
        public ItemStack makeIcon() {
            return ModItems.ALUMINUM_INGOT.get().getDefaultInstance();
        }
    };

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModTileEntities.TILE_ENTITIES.register(bus);
        ModContainers.CONTAINERS.register(bus);

        MinecraftForge.EVENT_BUS.addListener(OreFeature::onBiomeLoadingEvent);
    }

    private void setup(FMLCommonSetupEvent e) {
        e.enqueueWork(() -> {
            OreFeature.registerOreFeatures();
        });
    }

    private void clientSetup(FMLClientSetupEvent e) {
        MenuScreens.register(ModContainers.FURNACE_GENERATOR.get(), FurnaceGeneratorScreen::new);
        MenuScreens.register(ModContainers.BASIC_POWER_UNIT.get(), BasicPowerUnitScreen::new);
    }

}
