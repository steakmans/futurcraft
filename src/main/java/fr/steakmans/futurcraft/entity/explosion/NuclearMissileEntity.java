package fr.steakmans.futurcraft.entity.explosion;

import fr.steakmans.futurcraft.entity.ModEntities;
import fr.steakmans.futurcraft.packets.ModPackets;
import fr.steakmans.futurcraft.packets.NuclearExplosionPacket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class NuclearMissileEntity extends BasicMissileEntity{

    public NuclearMissileEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public NuclearMissileEntity(Level level, double x, double y, double z, @Nullable LivingEntity entity, int targetX, int targetY, int targetZ, int lockHeight) {
        super(ModEntities.NUCLEAR_MISSILE.get(), level, x, y, z, entity, targetX, targetY, targetZ, lockHeight);
    }

    @Override
    public void explode() {
        ModPackets.NETWORK.sendToServer(new NuclearExplosionPacket(getX(), getY(), getZ()));
    }
}
