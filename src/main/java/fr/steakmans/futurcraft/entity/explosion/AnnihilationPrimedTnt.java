package fr.steakmans.futurcraft.entity.explosion;

import fr.steakmans.futurcraft.entity.ModEntities;
import fr.steakmans.futurcraft.packets.AnnihilationExplosionPacket;
import fr.steakmans.futurcraft.packets.ModPackets;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AnnihilationPrimedTnt extends NuclearPrimedTnt{
    public AnnihilationPrimedTnt(EntityType<?> p_32076_, Level p_32077_) {
        super(p_32076_, p_32077_);
    }

    public AnnihilationPrimedTnt(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
        super(p_32079_, p_32080_, p_32081_, p_32082_, p_32083_, ModEntities.ANNIHILATION_PRIMED_TNT.get());
    }

    @Override
    protected void explode() {
        ModPackets.NETWORK.sendToServer(new AnnihilationExplosionPacket(getX(), getY(), getZ(), 75));
    }
}
