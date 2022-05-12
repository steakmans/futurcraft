package fr.steakmans.futurcraft.blocks;

import fr.steakmans.futurcraft.blocks.tileentities.TileEntityElectricFurnace;
import fr.steakmans.futurcraft.blocks.tileentities.ModTileEntities;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityFurnaceGenerator;
import fr.steakmans.futurcraft.screen.container.ElectricFurnaceContainer;
import fr.steakmans.futurcraft.screen.container.FurnaceGeneratorContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
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
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockElectricFurnace extends Block implements EntityBlock {

    public BlockElectricFurnace() {
        super(BlockBehaviour.Properties.of(Material.METAL));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModTileEntities.ELECTRIC_FURNACE_TILE_ENTITY.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModTileEntities.ELECTRIC_FURNACE_TILE_ENTITY.get() ? TileEntityElectricFurnace::tick : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player p, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide && level.getBlockEntity(pos) instanceof TileEntityElectricFurnace) {
            final TileEntityElectricFurnace generator = (TileEntityElectricFurnace) level.getBlockEntity(pos);
            MenuProvider container = new SimpleMenuProvider(ElectricFurnaceContainer.getServerContainer(generator, pos), TileEntityElectricFurnace.TITLE);
            NetworkHooks.openGui((ServerPlayer) p, container, pos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

}
