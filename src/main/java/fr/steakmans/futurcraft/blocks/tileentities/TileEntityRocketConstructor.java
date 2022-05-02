package fr.steakmans.futurcraft.blocks.tileentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityRocketConstructor extends BlockEntity  {

    private int counter = 0;

    public TileEntityRocketConstructor(BlockPos pos, BlockState state) {
        super(ModTileEntities.ROCKET_CONSTRUCTOR_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        this.setCounter(compound.getInt("counter"));
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        compound.putInt("counter", getCounter());
        super.saveAdditional(compound);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public static void tick(Level level, BlockPos pos, BlockState bs, BlockEntity be) {
        TileEntityRocketConstructor tile = (TileEntityRocketConstructor) be;
        tile.setCounter(tile.getCounter() + 1);
    }
}
