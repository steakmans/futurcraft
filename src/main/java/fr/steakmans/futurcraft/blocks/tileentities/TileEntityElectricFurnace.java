package fr.steakmans.futurcraft.blocks.tileentities;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.utils.CustomEnergyStorage;
import fr.steakmans.futurcraft.utils.InventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileEntityElectricFurnace extends InventoryBlockEntity {

    public static final Component TITLE = new TranslatableComponent("container." + Main.MODID + ".electric_furnace");

    public final CustomEnergyStorage energyStorage;

    private int capacity = 10000, maxExtract = 0, maxReceive = 500;
    private ItemStackHandler tocook = new ItemStackHandler();
    private LazyOptional<CustomEnergyStorage> energy;
    private int progress, maxProgress = 0;

    public TileEntityElectricFurnace(BlockPos pos, BlockState state) {
        super(ModTileEntities.ELECTRIC_FURNACE_TILE_ENTITY.get(), pos, state, 3);
        this.energyStorage = createEnergyStorage();
        this.energy = LazyOptional.of(() -> this.energyStorage);
        this.maxProgress = 100;
    }

    public static void tick(Level world, BlockPos pos, BlockState state, BlockEntity block) {
        TileEntityElectricFurnace be = (TileEntityElectricFurnace) block;
        be.tick();
    }

    private ItemStack getRecipeResult(ItemStack item, Level level) {
        tocook.insertItem(1, item, false);
        return null;
    }

    public void tick() {
        ItemStack stack = getItemInSlot(2);
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
    }

    private CustomEnergyStorage createEnergyStorage() {
        return new CustomEnergyStorage(this, capacity, maxReceive, maxExtract, 0);
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

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityEnergy.ENERGY ? this.energy.cast() : super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.energy.invalidate();
    }
}
