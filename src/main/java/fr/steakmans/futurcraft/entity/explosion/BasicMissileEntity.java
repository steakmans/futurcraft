package fr.steakmans.futurcraft.entity.explosion;

import fr.steakmans.futurcraft.entity.ModEntities;
import fr.steakmans.futurcraft.packets.BasicExplosionPacket;
import fr.steakmans.futurcraft.packets.ModPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class BasicMissileEntity extends Entity {

    private int targetX;
    private int targetY;
    private int targetZ;
    private int lockHeight;
    private int state;
    private double lockX;
    private double lockZ;
    private double baseY;

    @javax.annotation.Nullable
    private LivingEntity owner;

    private int ticks;

    public BasicMissileEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public BasicMissileEntity(EntityType<?> type, Level level, double x, double y, double z, @javax.annotation.Nullable LivingEntity entity, int targetX, int targetY, int targetZ, int lockHeight) {
        super(type, level);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.baseY = y;
        setPos(x, y, z);
        this.owner = entity;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.lockHeight = lockHeight;
        this.state = 0;
        ticks = 0;
        lockX = (targetX - getBlockX()) / 4;
        lockZ = (targetZ - getBlockZ()) / 4;
        init();
    }

    public void init() {

    }

    public void explode() {
        ModPackets.NETWORK.sendToServer(new BasicExplosionPacket(this.getX(), this.getY(), this.getZ()));
    }

    @Override
    public void tick() {
        if(isOnGround() && state == 4) {
            explode();
            discard();
        } else if(state == 4 && targetY > getY()) {
            explode();
            discard();
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
        if((state == 2 || state == 3 || state == 4) && !isOnGround()) {
            if (level.isClientSide()) launchParticle();
            this.setDeltaMovement(this.getDeltaMovement().add(0, -0.1D, 0));
        }
        if(state == 3) {
            this.setDeltaMovement(this.getDeltaMovement().add(-lockX / 3, 0, -lockZ / 3));
            if(ticks >= 3) {
                this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
                this.setPos(targetX + 0.5d, getY(), targetZ + 0.5d);
                ticks = -1;
                state = 4;
            }
        }
        if(state == 2) {
            this.setDeltaMovement(this.getDeltaMovement().add(lockX / 3, 0, lockZ / 3));
            if(ticks >= 3) {
                ticks = -1;
                state = 3;
            }
        }
        if(state == 1) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0d, 0.1d, 0.0d));
            if (level.isClientSide()) launchParticle();
            if(this.getY() >= baseY + lockHeight / 2) {
                state = 2;
                ticks = -1;
            }
        }

        if(ticks <= 100 && state == 0) {
            if(this.level.isClientSide()) {
                startParticle();
            }
            if(ticks == 100) {
                state = 1;
            }
        }
        ticks++;
    }

    public void startParticle() {
        this.level.addParticle(ParticleTypes.FLAME, getX(), getY() - 0.5d, getZ(), 0.05d, 0, 0.05d);
        this.level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY() - 1d, getZ(), 0.02d, 0, 0.02d);
        this.level.addParticle(ParticleTypes.FLAME, getX(), getY() - 0.5d, getZ(), -0.05d, 0, 0.05d);
        this.level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY() - 1d, getZ(), -0.02d, 0, 0.02d);
        this.level.addParticle(ParticleTypes.FLAME, getX(), getY() - 0.5d, getZ(), 0.05d, 0, -0.05d);
        this.level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY() - 1d, getZ(), 0.02d, 0, -0.02d);
        this.level.addParticle(ParticleTypes.FLAME, getX(), getY() - 0.5d, getZ(), -0.05d, 0, -0.05d);
        this.level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY() - 1d, getZ(), -0.02d, 0, -0.02d);
    }

    public void launchParticle() {
        this.level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY() - 0.5D, getZ(), 0, 0.05, 0);
        this.level.addParticle(ParticleTypes.FLAME, getX(), getY() - 0.5D, getZ(), 0, 0.05, 0);
        this.level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY() - 0.5D, getZ(), 0, 0.05, 0);
        this.level.addParticle(ParticleTypes.FLAME, getX(), getY() - 0.5D, getZ(), 0, 0.05, 0);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Nullable
    public LivingEntity getOwner() {
        return owner;
    }
}
