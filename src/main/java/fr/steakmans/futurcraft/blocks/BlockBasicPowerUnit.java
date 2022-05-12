package fr.steakmans.futurcraft.blocks;

import fr.steakmans.futurcraft.blocks.tileentities.ModTileEntities;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityBasicPowerUnit;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityFurnaceGenerator;
import fr.steakmans.futurcraft.screen.container.BasicPowerUnitContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockBasicPowerUnit extends Block implements EntityBlock {


    public BlockBasicPowerUnit() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(3.0f, 4.0f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModTileEntities.BASIC_POWER_UNIT_TILE_ENTITY.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> type) {
        return type == ModTileEntities.BASIC_POWER_UNIT_TILE_ENTITY.get() ? TileEntityBasicPowerUnit::tick : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player p, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide && level.getBlockEntity(pos) instanceof TileEntityBasicPowerUnit) {
            final TileEntityBasicPowerUnit generator = (TileEntityBasicPowerUnit) level.getBlockEntity(pos);
            MenuProvider container = new SimpleMenuProvider(BasicPowerUnitContainer.getServerContainer(generator, pos), TileEntityBasicPowerUnit.TITLE);
            NetworkHooks.openGui((ServerPlayer) p, container, pos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        TileEntityBasicPowerUnit be = (TileEntityBasicPowerUnit) level.getBlockEntity(pos);
        if(!be.getItemInSlot(0).isEmpty()) {
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), be.getItemInSlot(0)));
        }
        if(!be.getItemInSlot(1).isEmpty()) {
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), be.getItemInSlot(1)));
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        TileEntityBasicPowerUnit be = (TileEntityBasicPowerUnit) level.getBlockEntity(pos);
        if(!be.getItemInSlot(0).isEmpty()) {
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), be.getItemInSlot(0)));
        }
        if(!be.getItemInSlot(1).isEmpty()) {
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), be.getItemInSlot(1)));
        }
        super.onBlockExploded(state, level, pos, explosion);
    }
}
