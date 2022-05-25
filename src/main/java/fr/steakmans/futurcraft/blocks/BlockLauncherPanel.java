package fr.steakmans.futurcraft.blocks;

import fr.steakmans.futurcraft.blocks.tileentities.ModTileEntities;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityFurnaceGenerator;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityLauncherPanel;
import fr.steakmans.futurcraft.screen.container.FurnaceGeneratorContainer;
import fr.steakmans.futurcraft.screen.container.LauncherPanelContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockLauncherPanel extends Block implements EntityBlock {

    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape SHAPE_NORTH = Shapes.or(Block.box(1, 0, 0, 15, 2, 11), Block.box(2, 0, 11, 14, 31, 16));
    protected static final VoxelShape SHAPE_EAST = Shapes.or(Block.box(5, 0, 1, 16, 2, 15), Block.box(0, 0, 2, 5, 31, 14));
    protected static final VoxelShape SHAPE_WEST = Shapes.or(Block.box(0, 0, 1, 11, 2, 15), Block.box(11, 0, 2, 16, 31, 14));
    protected static final VoxelShape SHAPE_SOUTH = Shapes.or(Block.box(1, 0, 5, 15, 2, 16), Block.box(2, 0, 0, 14, 31, 5));

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

    public BlockLauncherPanel() {
        super(BlockBehaviour.Properties.copy(ModBlocks.ELECTRIC_FURNACE.get()));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModTileEntities.LAUNCHER_PANEL_TILE_ENTITY.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModTileEntities.LAUNCHER_PANEL_TILE_ENTITY.get() ? TileEntityLauncherPanel::tick : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player p, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide && level.getBlockEntity(pos) instanceof TileEntityLauncherPanel) {
            final TileEntityLauncherPanel generator = (TileEntityLauncherPanel) level.getBlockEntity(pos);
            MenuProvider container = new SimpleMenuProvider(LauncherPanelContainer.getServerContainer(generator, pos), TileEntityLauncherPanel.TITLE);
            NetworkHooks.openGui((ServerPlayer) p, container, pos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        switch (state.getValue(HORIZONTAL_FACING)) {
            case EAST:
                return SHAPE_EAST;
            case SOUTH:
                return SHAPE_SOUTH;
            case WEST:
                return SHAPE_WEST;
            default:
                return SHAPE_NORTH;
        }
    }
}
