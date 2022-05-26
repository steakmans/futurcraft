package fr.steakmans.futurcraft.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class NuclearExplosionPacket {

    private final double x;
    private final double y;
    private final double z;

    public NuclearExplosionPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public NuclearExplosionPacket(FriendlyByteBuf buf) {
        this(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            Level level = ctx.get().getSender().getLevel();
            level.explode(ctx.get().getSender(), x, y, z, 90f, Explosion.BlockInteraction.DESTROY);
            level.explode(ctx.get().getSender(), x + 20, y, z, 90f, Explosion.BlockInteraction.DESTROY);
            level.explode(ctx.get().getSender(), x - 20, y, z, 90f, Explosion.BlockInteraction.DESTROY);
            level.explode(ctx.get().getSender(), x, y, z + 20, 90f, Explosion.BlockInteraction.DESTROY);
            level.explode(ctx.get().getSender(), x + 15, y, z + 15, 30f, Explosion.BlockInteraction.DESTROY);
            level.explode(ctx.get().getSender(), x - 15, y, z + 15, 30f, Explosion.BlockInteraction.DESTROY);
            level.explode(ctx.get().getSender(), x, y, z - 20, 90f, Explosion.BlockInteraction.DESTROY);
            level.explode(ctx.get().getSender(), x + 15, y, z - 15, 30f, Explosion.BlockInteraction.DESTROY);
            level.explode(ctx.get().getSender(), x - 15, y, z - 15, 30f, Explosion.BlockInteraction.DESTROY);
        });
        ctx.get().setPacketHandled(true);
        return success.get();
    }

}
