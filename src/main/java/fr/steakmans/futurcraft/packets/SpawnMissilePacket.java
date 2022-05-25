package fr.steakmans.futurcraft.packets;

import fr.steakmans.futurcraft.blocks.tileentities.TileEntityLauncherPanel;
import fr.steakmans.futurcraft.entity.BasicMissileEntity;
import fr.steakmans.futurcraft.utils.MissileIdEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class SpawnMissilePacket {

    public final BlockPos pos;
    public final int targetX;
    public final int targetY;
    public final int targetZ;
    public final int lockHeight;

    public SpawnMissilePacket(BlockPos pos, int targetX, int targetY, int targetZ, int lockHeight) {
        this.pos = pos;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.lockHeight = lockHeight;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(targetX);
        buf.writeInt(targetY);
        buf.writeInt(targetZ);
        buf.writeInt(lockHeight);
    }

    public SpawnMissilePacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var succes = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            BlockEntity blockEntity = ctx.get().getSender().level.getBlockEntity(this.pos);
            if(blockEntity instanceof TileEntityLauncherPanel) {
                TileEntityLauncherPanel be = (TileEntityLauncherPanel) blockEntity;
                int missileId = be.getMissileId();
                be.extractItem(0);
                Level level = ctx.get().getSender().getLevel();
                if(missileId == MissileIdEnum.BASIC.getId()) {
                    BasicMissileEntity entity = new BasicMissileEntity(level, be.getBlockPos().getX() + 0.5D, be.getBlockPos().getY() + 1d, be.getBlockPos().getZ() + 0.5D, ctx.get().getSender(), targetX, targetY, targetZ, lockHeight);
                    level.addFreshEntity(entity);
                }
            }
        });

        ctx.get().setPacketHandled(true);
        return succes.get();
    }

}
