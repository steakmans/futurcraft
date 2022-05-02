package fr.steakmans.futurcraft.blocks;

import fr.steakmans.futurcraft.blocks.tileentities.ModTileEntities;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityRocketConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlockRocketConstructor extends Block implements EntityBlock {

    public BlockRocketConstructor() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(3.5f, 4.5f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModTileEntities.ROCKET_CONSTRUCTOR_TILE_ENTITY.get().create(pos, state);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> type) {
        return type == ModTileEntities.ROCKET_CONSTRUCTOR_TILE_ENTITY.get() ? TileEntityRocketConstructor::tick : null;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {

        if(level.getBlockEntity(blockPos) instanceof TileEntityRocketConstructor) {
            TileEntityRocketConstructor blockEntity = (TileEntityRocketConstructor) level.getBlockEntity(blockPos);
            player.displayClientMessage(new TextComponent("Counter : " + blockEntity.getCounter()), true);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
