package fr.steakmans.futurcraft.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class BasicExplosionPacket {

    private final double x;
    private final double y;
    private final double z;

    public BasicExplosionPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public BasicExplosionPacket(FriendlyByteBuf buf) {
        this(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            Level level = ctx.get().getSender().getLevel();
            level.explode(ctx.get().getSender(), x, y, z, 4.0f, Explosion.BlockInteraction.DESTROY);
        });
        ctx.get().setPacketHandled(true);
        return success.get();
    }

}
