package fr.steakmans.futurcraft.entity.explosion;

import fr.steakmans.futurcraft.entity.ModEntities;
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
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class IncendiaryPrimedTnt extends Entity {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(PrimedTnt.class, EntityDataSerializers.INT);
    private static final int DEFAULT_FUSE_TIME = 80;
    @javax.annotation.Nullable
    private LivingEntity owner;

    public IncendiaryPrimedTnt(EntityType<?> p_32076_, Level p_32077_) {
        super(p_32076_, p_32077_);
        this.blocksBuilding = true;
    }

    public IncendiaryPrimedTnt(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @javax.annotation.Nullable LivingEntity p_32083_, EntityType<?> type) {
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

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
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
        float f = 4.0F;
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 2.0F, Explosion.BlockInteraction.DESTROY);
        BlockPos pos1 = new BlockPos(getBlockX() - 5, getBlockY(), getBlockZ());
        BlockState blockstate1 = BaseFireBlock.getState(level, pos1);
        setTnt(pos1, blockstate1);
        this.setTnt(pos1.offset(0, 0, 1), blockstate1);
        this.setTnt(pos1.offset(0, 0, -1), blockstate1);
        this.setTnt(pos1.offset(1, 0, 2), blockstate1);
        this.setTnt(pos1.offset(1, 0, -2), blockstate1);
        this.setTnt(pos1.offset(1, 0, 1), blockstate1);
        this.setTnt(pos1.offset(1, 0, -1), blockstate1);
        this.setTnt(pos1.offset(1, 0, 3), blockstate1);
        this.setTnt(pos1.offset(1, 0, -3), blockstate1);
        this.setTnt(pos1.offset(1, 0, 0), blockstate1);
        this.setTnt(pos1.offset(2, 0, 2), blockstate1);
        this.setTnt(pos1.offset(2, 0, -2), blockstate1);
        this.setTnt(pos1.offset(2, 0, 1), blockstate1);
        this.setTnt(pos1.offset(2, 0, -1), blockstate1);
        this.setTnt(pos1.offset(2, 0, 3), blockstate1);
        this.setTnt(pos1.offset(2, 0, -3), blockstate1);
        this.setTnt(pos1.offset(2, 0, 4), blockstate1);
        this.setTnt(pos1.offset(2, 0, -4), blockstate1);
        this.setTnt(pos1.offset(2, 0, 0), blockstate1);
        this.setTnt(pos1.offset(3, 0, 2), blockstate1);
        this.setTnt(pos1.offset(3, 0, -2), blockstate1);
        this.setTnt(pos1.offset(3, 0, 1), blockstate1);
        this.setTnt(pos1.offset(3, 0, -1), blockstate1);
        this.setTnt(pos1.offset(3, 0, 3), blockstate1);
        this.setTnt(pos1.offset(3, 0, -3), blockstate1);
        this.setTnt(pos1.offset(3, 0, 4), blockstate1);
        this.setTnt(pos1.offset(3, 0, -4), blockstate1);
        this.setTnt(pos1.offset(3, 0, 0), blockstate1);
        this.setTnt(pos1.offset(4, 0, 2), blockstate1);
        this.setTnt(pos1.offset(4, 0, -2), blockstate1);
        this.setTnt(pos1.offset(4, 0, 1), blockstate1);
        this.setTnt(pos1.offset(4, 0, -1), blockstate1);
        this.setTnt(pos1.offset(4, 0, 3), blockstate1);
        this.setTnt(pos1.offset(4, 0, -3), blockstate1);
        this.setTnt(pos1.offset(4, 0, 4), blockstate1);
        this.setTnt(pos1.offset(4, 0, -4), blockstate1);
        this.setTnt(pos1.offset(4, 0, 5), blockstate1);
        this.setTnt(pos1.offset(4, 0, -5), blockstate1);
        this.setTnt(pos1.offset(4, 0, 0), blockstate1);
        this.setTnt(pos1.offset(5, 0, 2), blockstate1);
        this.setTnt(pos1.offset(5, 0, -2), blockstate1);
        this.setTnt(pos1.offset(5, 0, 1), blockstate1);
        this.setTnt(pos1.offset(5, 0, -1), blockstate1);
        this.setTnt(pos1.offset(5, 0, 3), blockstate1);
        this.setTnt(pos1.offset(5, 0, -3), blockstate1);
        this.setTnt(pos1.offset(5, 0, 4), blockstate1);
        this.setTnt(pos1.offset(5, 0, -4), blockstate1);
        this.setTnt(pos1.offset(5, 0, 5), blockstate1);
        this.setTnt(pos1.offset(5, 0, -5), blockstate1);
        this.setTnt(pos1.offset(5, 0, 0), blockstate1);
        this.setTnt(pos1.offset(6, 0, 2), blockstate1);
        this.setTnt(pos1.offset(6, 0, -2), blockstate1);
        this.setTnt(pos1.offset(6, 0, 1), blockstate1);
        this.setTnt(pos1.offset(6, 0, -1), blockstate1);
        this.setTnt(pos1.offset(6, 0, 3), blockstate1);
        this.setTnt(pos1.offset(6, 0, -3), blockstate1);
        this.setTnt(pos1.offset(6, 0, 4), blockstate1);
        this.setTnt(pos1.offset(6, 0, -4), blockstate1);
        this.setTnt(pos1.offset(6, 0, 5), blockstate1);
        this.setTnt(pos1.offset(6, 0, -5), blockstate1);
        this.setTnt(pos1.offset(6, 0, 0), blockstate1);
        this.setTnt(pos1.offset(10, 0, 1), blockstate1);
        this.setTnt(pos1.offset(10, 0, -1), blockstate1);
        this.setTnt(pos1.offset(10, 0, 0), blockstate1);
        this.setTnt(pos1.offset(9, 0, 2), blockstate1);
        this.setTnt(pos1.offset(9, 0, -2), blockstate1);
        this.setTnt(pos1.offset(9, 0, 1), blockstate1);
        this.setTnt(pos1.offset(9, 0, -1), blockstate1);
        this.setTnt(pos1.offset(9, 0, 3), blockstate1);
        this.setTnt(pos1.offset(9, 0, -3), blockstate1);
        this.setTnt(pos1.offset(9, 0, 0), blockstate1);
        this.setTnt(pos1.offset(8, 0, 2), blockstate1);
        this.setTnt(pos1.offset(8, 0, -2), blockstate1);
        this.setTnt(pos1.offset(8, 0, 1), blockstate1);
        this.setTnt(pos1.offset(8, 0, -1), blockstate1);
        this.setTnt(pos1.offset(8, 0, 3), blockstate1);
        this.setTnt(pos1.offset(8, 0, -3), blockstate1);
        this.setTnt(pos1.offset(8, 0, 4), blockstate1);
        this.setTnt(pos1.offset(8, 0, -4), blockstate1);
        this.setTnt(pos1.offset(8, 0, 0), blockstate1);
        this.setTnt(pos1.offset(7, 0, 2), blockstate1);
        this.setTnt(pos1.offset(7, 0, -2), blockstate1);
        this.setTnt(pos1.offset(7, 0, 1), blockstate1);
        this.setTnt(pos1.offset(7, 0, -1), blockstate1);
        this.setTnt(pos1.offset(7, 0, 3), blockstate1);
        this.setTnt(pos1.offset(7, 0, -3), blockstate1);
        this.setTnt(pos1.offset(7, 0, 4), blockstate1);
        this.setTnt(pos1.offset(7, 0, -4), blockstate1);
        this.setTnt(pos1.offset(7, 0, 0), blockstate1);
    }

    private void setTnt(BlockPos pos, BlockState state) {
        boolean stop = false;
        if(level.getBlockState(pos).isAir() && level.getBlockState(pos.below()).isAir() && !level.getBlockState(pos.below(2)).isAir()) {
            int rand = level.random.nextInt(0, 32);
            if(rand == 0) return;
            level.setBlock(pos.offset(0, -1, 0), state, 11);
        } else if(!level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir()) {
            int rand = level.random.nextInt(0, 32);
            if(rand == 0) return;
            level.setBlock(pos.offset(0, 1, 0), state, 11);
        } else if (level.getBlockState(pos).isAir() && !level.getBlockState(pos.below()).isAir()) {
            int rand = level.random.nextInt(0, 32);
            if(rand == 0) return;
            level.setBlock(pos, state, 11);
        }
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
