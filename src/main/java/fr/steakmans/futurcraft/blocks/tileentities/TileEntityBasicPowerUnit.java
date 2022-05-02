package fr.steakmans.futurcraft.blocks.tileentities;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.utils.CustomEnergyStorage;
import fr.steakmans.futurcraft.utils.InventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileEntityBasicPowerUnit extends InventoryBlockEntity {

    public static final Component TITLE = new TranslatableComponent("container." + Main.MODID + ".basic_power_unit");

    public final CustomEnergyStorage energyStorage;

    private int capacity = 20000, maxExtract = 500, maxReceive = 500;
    private LazyOptional<CustomEnergyStorage> energy;

    public TileEntityBasicPowerUnit(BlockPos pos, BlockState state) {
        super(ModTileEntities.BASIC_POWER_UNIT_TILE_ENTITY.get(), pos, state, 2);
        this.energyStorage = createEnergyStorage();
        this.energy = LazyOptional.of(() -> this.energyStorage);
    }

    private CustomEnergyStorage createEnergyStorage() {
        return new CustomEnergyStorage(this, capacity, maxReceive, maxExtract, 0);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityEnergy.ENERGY ? this.energy.cast() : super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.energy.invalidate();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.energyStorage.setEnergy(tag.getInt("Energy"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Energy", this.energyStorage.getEnergyStored());
    }

    public static void tick(Level world, BlockPos pos, BlockState state, BlockEntity block) {
        TileEntityBasicPowerUnit be = (TileEntityBasicPowerUnit) block;
        be.tick();
    }

    public void tick() {
        ItemStack stack = getItemInSlot(1);
        if(stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if(tag.contains("Energy") && tag.contains("MaxEnergy")) {
                int count = tag.getInt("Energy") + 10;
                if(count <= tag.getInt("MaxEnergy") && energyStorage.getEnergyStored() >= 10) {
                    energyStorage.setEnergy(energyStorage.getEnergyStored() - 10);
                    tag.putInt("Energy", count);
                    stack.setTag(tag);
                }
            }
        }
        stack = getItemInSlot(0);
        if(stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if(tag.contains("Energy") && tag.contains("MaxEnergy")) {
                int count = tag.getInt("Energy") - 10;
                if(count >= 0 && energyStorage.getEnergyStored() <= energyStorage.getMaxEnergyStored() - 10) {
                    energyStorage.setEnergy(energyStorage.getEnergyStored() + 10);
                    tag.putInt("Energy", count);
                    stack.setTag(tag);
                }
            }
        }
    }

}
