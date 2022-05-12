package fr.steakmans.futurcraft.entity.explosion;

import com.mojang.math.Quaternion;
import fr.steakmans.futurcraft.entity.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class IncendiaryGrenade extends IncendiaryPrimedTnt{

    public IncendiaryGrenade(EntityType<?> p_32076_, Level p_32077_) {
        super(p_32076_, p_32077_);
    }

    public IncendiaryGrenade(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_, double rot1, double rot2, double rot3) {
        super(p_32079_, p_32080_, p_32081_, p_32082_, p_32083_, ModEntities.INCENDIARY_GRENADE.get());
        Vec3 rot = new Vec3(rot1, rot2, rot3);
        this.setDeltaMovement(rot.multiply(0.9d, 0.015d, 0.9d));
        this.setFuse(150);
    }

    @Override
    protected void init(Level level) {
        System.out.println("did");
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }
        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.level.isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
