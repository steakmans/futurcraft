package fr.steakmans.futurcraft.blocks;

import fr.steakmans.futurcraft.blocks.tileentities.ModTileEntities;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityFurnaceGenerator;
import fr.steakmans.futurcraft.screen.container.FurnaceGeneratorContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockFurnaceGenerator extends Block implements EntityBlock{

    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BlockFurnaceGenerator() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(3.0f, 4.0f));
        this.registerDefaultState(this.getStateDefinition().any().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(HORIZONTAL_FACING, direction.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HORIZONTAL_FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModTileEntities.FURNACE_GENERATOR_TILE_ENTITY.get().create(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModTileEntities.FURNACE_GENERATOR_TILE_ENTITY.get() ? TileEntityFurnaceGenerator::tick : null;
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player p, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide && level.getBlockEntity(pos) instanceof TileEntityFurnaceGenerator) {
            final TileEntityFurnaceGenerator generator = (TileEntityFurnaceGenerator) level.getBlockEntity(pos);
            MenuProvider container = new SimpleMenuProvider(FurnaceGeneratorContainer.getServerContainer(generator, pos), TileEntityFurnaceGenerator.TITLE);
            NetworkHooks.openGui((ServerPlayer) p, container, pos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;

    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        TileEntityFurnaceGenerator be = (TileEntityFurnaceGenerator) level.getBlockEntity(pos);
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
        TileEntityFurnaceGenerator be = (TileEntityFurnaceGenerator) level.getBlockEntity(pos);
        if(!be.getItemInSlot(0).isEmpty()) {
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), be.getItemInSlot(0)));
        }
        if(!be.getItemInSlot(1).isEmpty()) {
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), be.getItemInSlot(1)));
        }
        super.onBlockExploded(state, level, pos, explosion);
    }
}
