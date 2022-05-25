package fr.steakmans.futurcraft;

import fr.steakmans.futurcraft.blocks.ModBlocks;
import fr.steakmans.futurcraft.blocks.tileentities.ModTileEntities;
import fr.steakmans.futurcraft.entity.ModEntities;
import fr.steakmans.futurcraft.fluids.ModFluids;
import fr.steakmans.futurcraft.items.ModItems;
import fr.steakmans.futurcraft.oregen.OreFeature;
import fr.steakmans.futurcraft.packets.ModPackets;
import fr.steakmans.futurcraft.screen.BasicPowerUnitScreen;
import fr.steakmans.futurcraft.screen.ElectricFurnaceScreen;
import fr.steakmans.futurcraft.screen.FurnaceGeneratorScreen;
import fr.steakmans.futurcraft.screen.LauncherPanelScreen;
import fr.steakmans.futurcraft.screen.container.ModContainers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
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
        ModEntities.ENTITIES.register(bus);
        ModFluids.FLUIDS.register(bus);
        MinecraftForge.EVENT_BUS.addListener(OreFeature::onBiomeLoadingEvent);
    }

    private void setup(FMLCommonSetupEvent e) {
        e.enqueueWork(() -> {
            OreFeature.registerOreFeatures();
            ModPackets.init();
        });
    }

    private void clientSetup(FMLClientSetupEvent e) {
        e.enqueueWork(() -> {
            MenuScreens.register(ModContainers.FURNACE_GENERATOR.get(), FurnaceGeneratorScreen::new);
            MenuScreens.register(ModContainers.BASIC_POWER_UNIT.get(), BasicPowerUnitScreen::new);
            MenuScreens.register(ModContainers.ELECTRIC_FURNACE.get(), ElectricFurnaceScreen::new);
            MenuScreens.register(ModContainers.LAUNCHER_PANEL.get(), LauncherPanelScreen::new);

            ItemBlockRenderTypes.setRenderLayer(ModFluids.PETROL_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.PETROL_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.PETROL_FLOWING.get(), RenderType.translucent());
        });
    }

}
