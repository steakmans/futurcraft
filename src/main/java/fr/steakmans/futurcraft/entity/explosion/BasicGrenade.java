package fr.steakmans.futurcraft.entity.explosion;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BasicGrenade extends IncendiaryGrenade{
    public BasicGrenade(EntityType<?> p_32076_, Level p_32077_) {
        super(p_32076_, p_32077_);
    }

    public BasicGrenade(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_, double rot1, double rot2, double rot3) {
        super(p_32079_, p_32080_, p_32081_, p_32082_, p_32083_, rot1, rot2, rot3);
    }

    @Override
    protected void explode() {
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, Explosion.BlockInteraction.BREAK);
    }
}
