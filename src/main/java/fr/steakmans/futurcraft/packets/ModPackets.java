package fr.steakmans.futurcraft.packets;

import fr.steakmans.futurcraft.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(Main.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void init() {
        int index = 0;
        NETWORK.messageBuilder(SpawnMissilePacket.class, index++, NetworkDirection.PLAY_TO_SERVER).encoder(SpawnMissilePacket::encode).decoder(SpawnMissilePacket::new).consumer(SpawnMissilePacket::handle).add();
    }

}
