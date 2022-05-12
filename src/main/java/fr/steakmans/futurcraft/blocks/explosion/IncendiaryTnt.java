package fr.steakmans.futurcraft.blocks.explosion;

import fr.steakmans.futurcraft.entity.ModEntities;
import fr.steakmans.futurcraft.entity.explosion.IncendiaryPrimedTnt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class IncendiaryTnt extends Block {
    public IncendiaryTnt() {
        super(BlockBehaviour.Properties.copy(Blocks.TNT));
    }

    public static void explode(Level level, BlockPos pos, @javax.annotation.Nullable LivingEntity p_57439_) {
        if (!level.isClientSide) {
            IncendiaryPrimedTnt primedtnt = new IncendiaryPrimedTnt(level, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, p_57439_, ModEntities.PRIMED_INCENDIARY_TNT.get());
            level.addFreshEntity(primedtnt);
            level.playSound((Player)null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(p_57439_, GameEvent.PRIME_FUSE, pos);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean bool) {
        if (!state2.is(state.getBlock())) {
            if (level.hasNeighborSignal(pos)) {
                onCaughtFire(state, level, pos, null, null);
                level.removeBlock(pos, false);
            }
        }
    }

    @Override
    public void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction direction, @Nullable LivingEntity igniter) {
        explode(level, pos, igniter);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos2, boolean bool) {
        if (level.hasNeighborSignal(pos)) {
            onCaughtFire(state, level, pos, null, null);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        if (!level.isClientSide) {
            IncendiaryPrimedTnt primedtnt = new IncendiaryPrimedTnt(level, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, explosion.getSourceMob(), ModEntities.PRIMED_INCENDIARY_TNT.get());
            int i = primedtnt.getFuse();
            primedtnt.setFuse((short)(level.random.nextInt(i / 4) + i / 8));
            level.addFreshEntity(primedtnt);
        }
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return false;
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult result, Projectile projectile) {
        if (!level.isClientSide) {
            BlockPos blockpos = result.getBlockPos();
            Entity entity = projectile.getOwner();
            if (projectile.isOnFire() && projectile.mayInteract(level, blockpos)) {
                onCaughtFire(state, level, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
                level.removeBlock(blockpos, false);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player p, InteractionHand hand, BlockHitResult result) {
        ItemStack itemstack = p.getItemInHand(hand);
        if (!itemstack.is(Items.FLINT_AND_STEEL) && !itemstack.is(Items.FIRE_CHARGE)) {
            return super.use(state, level, pos, p, hand, result);
        } else {
            onCaughtFire(state, level, pos, result.getDirection(), p);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            Item item = itemstack.getItem();
            if (!p.isCreative()) {
                if (itemstack.is(Items.FLINT_AND_STEEL)) {
                    itemstack.hurtAndBreak(1, p, (p_57425_) -> {
                        p_57425_.broadcastBreakEvent(hand);
                    });
                } else {
                    itemstack.shrink(1);
                }
            }

            p.awardStat(Stats.ITEM_USED.get(item));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

}
