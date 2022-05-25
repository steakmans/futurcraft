package fr.steakmans.futurcraft.entity;

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

import javax.annotation.Nullable;

public class BasicMissileEntity extends Entity {

    private int targetX;
    private int targetY;
    private int targetZ;
    private int lockHeight;
    private int state;

    @javax.annotation.Nullable
    private LivingEntity owner;

    public BasicMissileEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public BasicMissileEntity(Level level, double x, double y, double z, @javax.annotation.Nullable LivingEntity entity, int targetX, int targetY, int targetZ, int lockHeight) {
        super(ModEntities.BASIC_MISSILE.get(), level);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        setPos(x, y, z);
        this.owner = entity;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.lockHeight = lockHeight;
        this.state = 0;
        init();
    }

    public void init() {

    }

    public void explode() {

    }

    @Override
    public void tick() {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        this.move(MoverType.SELF, this.getDeltaMovement());

        if(state == 0 && this.level.isClientSide()) {
            int rotx = level.random.nextInt(100) - 50;
            int rotz = level.random.nextInt(100) - 50;
            this.level.addParticle(ParticleTypes.FLAME, getX(), getY() - 0.5d, getZ(), rotx / 75, -0.05d, rotz / 75);
            this.level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY() - 1d, getZ(), -rotx / 75, -0.05d, -rotz / 75);
        }
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
