package fr.steakmans.futurcraft.packets;

import fr.steakmans.futurcraft.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class AnnihilationExplosionPacket {

    private final double x;
    private final double y;
    private final double z;
    private final int radius;

    public AnnihilationExplosionPacket(double x, double y, double z, int radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeInt(radius);
    }

    public AnnihilationExplosionPacket(FriendlyByteBuf buf) {
        this(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            Level level = ctx.get().getSender().getLevel();
            ServerPlayer player = ctx.get().getSender();
            for (int i = 0; i < level.players().size(); i++) {
                Player p = level.players().get(i);
                double distance = p.getOnPos().distSqr(new Vec3i(x, y, z));
                if(distance <= radius) {
                    p.hurt(Main.ANNIHILATION_DAMAGE_SOURCE, 2000f);
                }
            }
            for(int i = 0; i <= radius; i++) {
                Stream<BlockPos> posStream = BlockPos.betweenClosedStream(new BlockPos(x + i, y + i, z + i), new BlockPos(x - i, y - i, z - i));
                for (int j = 0; j <= posStream.count(); j++) {
                    BlockPos pos = posStream.toList().get(j);
                    double dist = pos.distSqr(new Vec3i(x, y, z));
                    BlockState state = level.getBlockState(pos);
                    if(dist <= radius && (state.isAir() || state.is(Blocks.BEDROCK))) {
                        level.destroyBlock(pos, false, player);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
        return success.get();
    }

}
