package fr.steakmans.futurcraft.utils;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.entity.ModEntities;
import fr.steakmans.futurcraft.entity.explosion.IncendiaryPrimedTnt;
import fr.steakmans.futurcraft.entity.explosion.renderer.IncendiaryGrenadeRenderer;
import fr.steakmans.futurcraft.entity.explosion.renderer.IncendiaryTntRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e) {

    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers e) {
        e.registerEntityRenderer(ModEntities.PRIMED_INCENDIARY_TNT.get(), IncendiaryTntRenderer::new);
        e.registerEntityRenderer(ModEntities.INCENDIARY_GRENADE.get(), IncendiaryGrenadeRenderer::new);
    }

}
