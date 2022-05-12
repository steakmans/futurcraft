package fr.steakmans.futurcraft.blocks.tileentities;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.blocks.ModBlocks;
import fr.steakmans.futurcraft.items.ModItems;
import fr.steakmans.futurcraft.utils.CustomEnergyStorage;
import fr.steakmans.futurcraft.utils.InventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileEntityFurnaceGenerator extends InventoryBlockEntity{

    public static final Component TITLE = new TranslatableComponent("container." + Main.MODID + ".furnace_generator");

    public final CustomEnergyStorage energyStorage;

    private int capacity = 2000, maxExtract = 100, maxReceive = 0;
    private LazyOptional<CustomEnergyStorage> energy;
    private int progress, maxProgress = 0;

    public TileEntityFurnaceGenerator(BlockPos pos, BlockState state) {
        super(ModTileEntities.FURNACE_GENERATOR_TILE_ENTITY.get(), pos, state, 2);
        this.energyStorage = createEnergyStorage();
        this.energy = LazyOptional.of(() -> this.energyStorage);
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

    public int getEnergyForStack(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.progress = tag.getInt("Progress");
        this.energyStorage.setEnergy(tag.getInt("Energy"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Progress", this.progress);
        tag.putInt("Energy", this.energyStorage.getEnergyStored());
    }

    public void outputEnergy() {
        if(this.energyStorage.getEnergyStored() >= this.maxExtract && this.energyStorage.canExtract()) {
            for(final var direction : Direction.values()) {
                final BlockEntity be = this.level.getBlockEntity(this.worldPosition.relative(direction));
                if(be == null) continue;

                be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).ifPresent(storage -> {
                    if((be != this) && (storage.getEnergyStored() < storage.getMaxEnergyStored())) {
                        final int toSend = TileEntityFurnaceGenerator.this.energyStorage.extractEnergy(this.maxExtract, false);
                        final int received = storage.receiveEnergy(toSend, false);
                        TileEntityFurnaceGenerator.this.energyStorage.setEnergy(TileEntityFurnaceGenerator.this.energyStorage.getEnergyStored() + toSend - received);
                    }
                });
            }
        }
    }

    private CustomEnergyStorage createEnergyStorage() {
        return new CustomEnergyStorage(this, capacity, 0, maxExtract, 0);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, BlockEntity block) {
        TileEntityFurnaceGenerator be = (TileEntityFurnaceGenerator) block;
        be.tick();
    }

    public void tick() {
        if(energyStorage.getEnergyStored() <= energyStorage.getMaxEnergyStored() - 100) {
            if(!getItemInSlot(0).isEmpty() && (progress <= 0 ||progress > maxProgress)) {
                if(getEnergyForStack(getItemInSlot(0)) <= 0) return;
                maxProgress = getEnergyForStack(getItemInSlot(0));
                inventory.extractItem(0, 1, false);
                progress++;
            } else if (progress > 0) {
                progress++;
                if(progress >= maxProgress) {
                    progress = 0;
                    energyStorage.setEnergy(energyStorage.getEnergyStored() + maxProgress / 10);
                }
            } else {
                progress = 0;
                maxProgress = 0;
            }
        }
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
        outputEnergy();
    }
}
