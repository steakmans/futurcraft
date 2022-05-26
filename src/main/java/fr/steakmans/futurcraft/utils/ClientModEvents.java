package fr.steakmans.futurcraft.utils;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.blocks.tileentities.ModTileEntities;
import fr.steakmans.futurcraft.blocks.tileentities.renderer.LauncherPanelBER;
import fr.steakmans.futurcraft.entity.ModEntities;
import fr.steakmans.futurcraft.entity.explosion.renderer.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers e) {
        e.registerEntityRenderer(ModEntities.PRIMED_INCENDIARY_TNT.get(), IncendiaryTntRenderer::new);
        e.registerEntityRenderer(ModEntities.INCENDIARY_GRENADE.get(), IncendiaryGrenadeRenderer::new);
        e.registerEntityRenderer(ModEntities.BASIC_GRENADE.get(), BasicGrenadeRenderer::new);
        e.registerEntityRenderer(ModEntities.NUCLEAR_PRIMED_TNT.get(), NuclearTntRenderer::new);
        e.registerEntityRenderer(ModEntities.BASIC_MISSILE.get(), BasicMissileRenderer::new);
        e.registerEntityRenderer(ModEntities.NUCLEAR_MISSILE.get(), NuclearMissileRenderer::new);

        e.registerBlockEntityRenderer(ModTileEntities.LAUNCHER_PANEL_TILE_ENTITY.get(), LauncherPanelBER::new);
    }
}