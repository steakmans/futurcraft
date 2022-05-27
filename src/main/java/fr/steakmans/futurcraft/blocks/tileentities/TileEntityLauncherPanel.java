package fr.steakmans.futurcraft.blocks.tileentities;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.items.ModItems;
import fr.steakmans.futurcraft.utils.CustomEnergyStorage;
import fr.steakmans.futurcraft.utils.InventoryBlockEntity;
import fr.steakmans.futurcraft.utils.MissileIdEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileEntityLauncherPanel extends InventoryBlockEntity {

    public static final ModelProperty<String> MISSILE_ID = new ModelProperty<String>();

    public static final Component TITLE = new TranslatableComponent("container." + Main.MODID + ".launcher_panel");
    public static int hasItem = 0;
    public int missileId = 0;
    public final CustomEnergyStorage energyStorage;
    private int capacity = 10000, maxExtract = 0, maxReceive = 500;
    private LazyOptional<CustomEnergyStorage> energy;

    public TileEntityLauncherPanel(BlockPos pos, BlockState state) {
        super(ModTileEntities.LAUNCHER_PANEL_TILE_ENTITY.get(), pos, state, 2);
        this.energyStorage = createEnergyStorage();
        this.energy = LazyOptional.of(() -> this.energyStorage);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityEnergy.ENERGY ? this.energy.cast() : super.getCapability(cap, side);
    }

    private CustomEnergyStorage createEnergyStorage() {
        return new CustomEnergyStorage(this, capacity, maxReceive, maxExtract, 0);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.energy.invalidate();
    }

    public static void tick(Level world, BlockPos pos, BlockState state, BlockEntity block) {
        TileEntityLauncherPanel be = (TileEntityLauncherPanel) block;
        be.tick();
    }

    public void tick() {
        this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        if(getItemInSlot(0).is(ModItems.BASIC_MISSILE.get())) missileId = MissileIdEnum.BASIC.getId();
        else if(getItemInSlot(0).is(ModItems.NUCLEAR_MISSILE.get())) missileId = MissileIdEnum.NUCLEAR.getId();
        else if (getItemInSlot(0).is(ModItems.ANNIHILATION_MISSILE.get())) missileId = MissileIdEnum.ANNIHILATION.getId();
        else missileId = -1;
    }

    public int isMissileSlotEmpty() {
        if(this.getItemInSlot(0).isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getMissileId() {
        return missileId;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Energy", this.energyStorage.getEnergyStored());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.energyStorage.setEnergy(tag.getInt("Energy"));
    }

}
