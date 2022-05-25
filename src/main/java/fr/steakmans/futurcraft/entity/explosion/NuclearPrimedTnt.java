package fr.steakmans.futurcraft.entity.explosion;

import fr.steakmans.futurcraft.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class NuclearPrimedTnt extends Entity {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(PrimedTnt.class, EntityDataSerializers.INT);
    private static final int DEFAULT_FUSE_TIME = 80;
    @Nullable
    private LivingEntity owner;

    public NuclearPrimedTnt(EntityType<?> p_32076_, Level p_32077_) {
        super(p_32076_, p_32077_);
        this.blocksBuilding = true;
    }

    public NuclearPrimedTnt(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_, EntityType<?> type) {
        this(type, p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        init(level);
        this.setFuse(80);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
    }

    protected void init(Level level) {
        double d0 = level.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 80);
    }

    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

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

    protected void explode() {
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 90f, Explosion.BlockInteraction.DESTROY);
        this.level.explode(this, this.getX() + 20, this.getY(0.0625D), this.getZ(), 90f, Explosion.BlockInteraction.DESTROY);
        this.level.explode(this, this.getX() - 20, this.getY(0.0625D), this.getZ(), 90f, Explosion.BlockInteraction.DESTROY);
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ() + 20, 90f, Explosion.BlockInteraction.DESTROY);
        this.level.explode(this, this.getX() + 15, this.getY(0.0625D), this.getZ() + 15, 30f, Explosion.BlockInteraction.DESTROY);
        this.level.explode(this, this.getX() - 15, this.getY(0.0625D), this.getZ() + 15, 30f, Explosion.BlockInteraction.DESTROY);
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ() - 20, 90f, Explosion.BlockInteraction.DESTROY);
        this.level.explode(this, this.getX() + 15, this.getY(0.0625D), this.getZ() - 15, 30f, Explosion.BlockInteraction.DESTROY);
        this.level.explode(this, this.getX() - 15, this.getY(0.0625D), this.getZ() - 15, 30f, Explosion.BlockInteraction.DESTROY);
    }


    protected void addAdditionalSaveData(CompoundTag p_32097_) {
        p_32097_.putShort("Fuse", (short)this.getFuse());
    }

    protected void readAdditionalSaveData(CompoundTag p_32091_) {
        this.setFuse(p_32091_.getShort("Fuse"));
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    protected float getEyeHeight(Pose p_32088_, EntityDimensions p_32089_) {
        return 0.15F;
    }

    public void setFuse(int p_32086_) {
        this.entityData.set(DATA_FUSE_ID, p_32086_);
    }

    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }

    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
